package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3.ExcelReader;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

/**
 * Descripcion:
 * Información del reporte debe coincidir con los resultados de los queries en las fuentes
 * 
 * Nombre casos de prueba manual que se abarcan: ATC_FT_015_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaEMPAQUE
 * 
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de Snowflake.
 * 
 * 
 * Insumos:
 * -N/A
 * @author Joaquin Aguilar
 * @date 18/08/2025
 * 
 */
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210855
		)

public class ATC_FT_015_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaEMPAQUE extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true, testName = "")
    public void ATC_FT_015_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaEMPAQUE_test(HashMap<String, String> data) throws Exception {
    	testCase.setProject_Name("Automatización Reportes DataLake MVP3");
    	/***************************** Variables Data Provider **************************************/
		String tableName = data.get("tableName");
		String type = data.get("type");
		String run = data.get("run");
		String typeB = data.get("typeB");
		String testName = data.get("testName");
		String fechaInicio = data.get("fechaInicio");
		String fechaFin = data.get("fechaFin");

		/***************************** Instancias **************************************************/
		UtilsReportesDatalakeMVP3 uti = new UtilsReportesDatalakeMVP3();
		Connection connection = null;
		List<Map<String, Object>> resultadoSP = new ArrayList<>();
		int responseSnowFlakeSize = 0;

		/***************************** Lectura Excel **************************************************/
		addStep("Lectura del excel");
		File folder = Paths.get("Config", "Input", "DataProviders", "Reportes DataLake_Reportes", "ReportesVentas").toFile();
		File[] files = folder.listFiles((dir, name) -> name.endsWith(".xlsx"));

		List<Map<String, String>> allRows = new ArrayList<>();
		for (File file : files) {
		    List<Map<String, String>> rows = ExcelReader.readExcelToMap(file.getAbsolutePath());
		    allRows.addAll(rows);
		}

		// Validar existencia de EMPAQUE en Excel (como header)
		boolean foundEMPAQUEExcel = allRows.stream()
		        .anyMatch(row -> row.containsKey("EMPAQUE"));
		assertTrue(foundEMPAQUEExcel, "La columna EMPAQUE no está presente en los archivos Excel.");

		/***************************** Información Consola *******************************************/
		System.out.println("Ejecutando prueba: " + testName);

		if (run.equalsIgnoreCase("si")) {

		    /***************************** PASO 1: Ejecutar el Stored Procedure ****************************/
		    addStep("Conectar a Snowflake y ejecutar Stored Procedure SP_REPORTE_VENTAS con fechas dadas");

		    try {
		        resultadoSP = UtilsReportesDatalakeMVP3.SPExecutorV.ejecutarSPReporteVentas(fechaInicio, fechaFin);

		        assertFalse(resultadoSP == null || resultadoSP.isEmpty(), "El resultado del SP está vacío o es nulo.");

		        testCase.addTextEvidenceCurrentStep("Conexión a la base de datos Snowflake establecida correctamente.");

		        testCase.addTextEvidenceCurrentStep(
		            "Se ejecuta CALL DBZDEV_MEX_OXXO.LAND_DAS_CAT.SP_REPORTE_VENTAS con fechas: " 
		            + fechaInicio + ", " + fechaFin);

		    } catch (Exception e) {
		        e.printStackTrace();
		        fail("Error al ejecutar el Stored Procedure: " + e.getMessage());
		    }

		    /***************************** PASO 2: Filtrar Excel por fechas dadas ****************************/
		    addStep("Filtrar archivos del Excel por las fechas inicio y fin");

		    String mesAnioInicio = fechaInicio.substring(4, 6) + fechaInicio.substring(0, 4); // MMYYYY
		    String mesAnioFin = fechaFin.substring(4, 6) + fechaFin.substring(0, 4);         // MMYYYY
		    String mesAnioExtra = "052025"; 

		    List<Map<String, String>> allRowsFiltrados = new ArrayList<>();
		    for (File file : files) {
		        String fileName = file.getName().toUpperCase();
		        if (fileName.contains(mesAnioInicio) || fileName.contains(mesAnioFin) || fileName.contains(mesAnioExtra)) {
		            System.out.println("Archivo incluido: " + file.getName());
		            testCase.addCodeEvidenceCurrentStep("Archivo incluido: " + file.getName());

		            List<Map<String, String>> rows = ExcelReader.readExcelToMap(file.getAbsolutePath());
		            allRowsFiltrados.addAll(rows);
		        }
		    }

		    assertFalse(allRowsFiltrados.isEmpty(), "No se encontraron archivos Excel para las fechas proporcionadas.");
		   

		    /***************************** PASO 3: Validar existencia de columna EMPAQUE ************/
		    boolean foundEmpaqueExcel = allRowsFiltrados.stream()
		            .anyMatch(row -> row.containsKey("EMPAQUE"));
		    assertTrue(foundEmpaqueExcel, "La columna 'EMPAQUE' no está presente en los archivos Excel.");

		    /***************************** PASO 4: Validar estructura general de columnas *******************/
		    addStep("Validar estructura general del archivo Excel (nombres de columnas)");

		    Set<String> excelColumns = allRowsFiltrados.get(0).keySet().stream().filter(Objects::nonNull)
		            .map(String::trim)
		            .map(col -> col.replace(" ", "_").toUpperCase())
		            .collect(Collectors.toSet());

		    testCase.addCodeEvidenceCurrentStep("Columnas encontradas en Excel: " + excelColumns);

		    addStep("Validar estructura general del archivo SP (nombres de columnas)");

		    Set<String> spColumns = resultadoSP.get(0).keySet().stream()
		            .filter(Objects::nonNull)
		            .map(String::trim)
		            .map(String::toUpperCase)
		            .collect(Collectors.toSet());

		    testCase.addCodeEvidenceCurrentStep("Columnas encontradas en SP: " + spColumns);
		    spColumns.forEach(System.out::println);

		    /***************************** PASO 5: Validaciones de parámetros de entrada *******************/
		    addStep("Validar formato de parámetros de entrada");

		    if (fechaInicio.matches("\\d{6}")) {
		        testCase.addCodeEvidenceCurrentStep("Fecha de inicio válida: " + fechaInicio);
		    } else {
		        fail("Fecha de inicio con formato incorrecto: " + fechaInicio);
		    }

		    if (fechaFin.matches("\\d{6}")) {
		        testCase.addCodeEvidenceCurrentStep("Fecha de fin válida: " + fechaFin);
		    } else {
		        fail("Fecha de fin con formato incorrecto: " + fechaFin);
		    }

		    if ("Ventas".equalsIgnoreCase(type) || "Compras".equalsIgnoreCase(type)) {
		        testCase.addCodeEvidenceCurrentStep("Tipo de reporte válido: " + type);
		    } else {
		        fail("Tipo de reporte no válido: " + type + ". Solo se permite: Ventas o Compras");
		    }

		    /***************************** PASO 6: Validación específica: campo EMPAQUE **************/
		    addStep("Obtener información de la columna EMPAQUE");
		    addStep("Validar existencia de datos en la columna 'EMPAQUE'");

		    long cantidadEmpaque = allRowsFiltrados.stream()
		            .filter(row -> row.get("EMPAQUE") != null && !row.get("EMPAQUE").isBlank())
		            .count();

		    testCase.addCodeEvidenceCurrentStep("Cantidad de registros leídos del Excel con 'EMPAQUE': " + cantidadEmpaque);
		    assertTrue(cantidadEmpaque > 0, "No se encontraron datos en la columna 'EMPAQUE'");

		    int cantidadFilasSP = resultadoSP.size();
		    testCase.addCodeEvidenceCurrentStep("Cantidad de registros devueltos por el SP: " + cantidadFilasSP);

		    /***************************** PASO 7 - Validar valores en columna EMPAQUE ****************************/
		    addStep("Validar que los valores en la columna 'EMPAQUE' correspondan a los valores permitidos: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12");

		    // Valores válidos permitidos para EMPAQUE
		    Set<Integer> idEmpaquesValidos = Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

		    // Para guardar valores encontrados en SP y Excel
		    Set<Integer> idEmpaquesSp = new HashSet<>();
		    Set<Integer> idEmpaquesExcel = new HashSet<>();

		    // Para guardar valores inválidos encontrados
		    List<String> idEmpaquesInvalidosSp = new ArrayList<>();
		    List<String> idEmpaquesInvalidosExcel = new ArrayList<>();

		    // --- Validar columna EMPAQUE en resultado del SP ---
		    for (Map<String, Object> row : resultadoSP) {
		        Object empaqueObj = row.get("EMPAQUE");
		        if (empaqueObj != null) {
		            String empaqueStr = empaqueObj.toString().trim();
		            if (!empaqueStr.isEmpty()) {
		                try {
		                    int empq = Integer.parseInt(empaqueStr);
		                    if (idEmpaquesValidos.contains(empq)) {
		                        idEmpaquesSp.add(empq);
		                    } else {
		                        idEmpaquesInvalidosSp.add(empaqueStr);
		                    }
		                } catch (NumberFormatException e) {
		                    idEmpaquesInvalidosSp.add(empaqueStr);
		                }
		            }
		        } else {
		            idEmpaquesInvalidosSp.add("NULL");
		        }
		    }

		    // --- Validar columna EMPAQUE en el Excel ---
		    for (Map<String, String> row : allRows) {
		        String empaqueStr = row.get("EMPAQUE");
		        if (empaqueStr != null && !empaqueStr.trim().isEmpty()) {
		            try {
		                int empq = Integer.parseInt(empaqueStr.trim());
		                if (idEmpaquesValidos.contains(empq)) {
		                    idEmpaquesExcel.add(empq);
		                } else {
		                    idEmpaquesInvalidosExcel.add(empaqueStr);
		                }
		            } catch (NumberFormatException e) {
		                idEmpaquesInvalidosExcel.add(empaqueStr);
		            }
		        } else {
		            idEmpaquesInvalidosExcel.add("NULL");
		        }
		    }

		    // --- Agregar evidencia ---
		    testCase.addCodeEvidenceCurrentStep("Valores válidos de EMPAQUE encontrados en SP: " + idEmpaquesSp.stream().sorted().collect(Collectors.toList()));
		    testCase.addCodeEvidenceCurrentStep("Valores válidos de EMPAQUE encontrados en Excel: " + idEmpaquesExcel.stream().sorted().collect(Collectors.toList()));

		    if (!idEmpaquesInvalidosSp.isEmpty()) {
		        testCase.addCodeEvidenceCurrentStep("Valores inválidos en columna 'EMPAQUE' del SP: " + idEmpaquesInvalidosSp);
		        fail("Existen valores inválidos en la columna 'EMPAQUE' del SP.");
		    }

		    if (!idEmpaquesInvalidosExcel.isEmpty()) {
		        testCase.addCodeEvidenceCurrentStep("Valores inválidos en columna 'EMPAQUES' del Excel: " + idEmpaquesInvalidosExcel);
		        fail("Existen valores inválidos en la columna 'EMPAQUE' del Excel.");
		    }

		    testCase.addBoldTextEvidenceCurrentStep("Validación exitosa: Todos los valores en 'EMPAQUE' son válidos.");
		            
		}
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_015_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaEMPAQUE";
	}

	@Override
	public String setTestDescription() {
		return "Información del reporte debe coincidir con los resultados de los queries en las fuentes";
	}

	@Override
	public String setTestDesigner() {
		return "Joaquin Aguilar";
	}

	@Override
	public String setTestInstanceID() {
		return "1";
	}

	@Override
	public String setPrerequisites() {
		return "1.-Acceso a la BD de Snowflake.";
		
	}

	@Override
	public void beforeTest() {
		
	}
}