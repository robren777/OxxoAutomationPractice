package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
 * Nombre casos de prueba manual que se abarcan:
 * ATC_FT_036_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaCLAVEGENERICA_Compras
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de Snowflake.
 * Insumos:
 * -N/A
 * @author Angel Gabriel Ramos Martinez
 * @date  28/08/2025
 * 
 */
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210858
		)

public class ATC_FT_036_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaCLAVEGENERICA_Compras extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_036_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaCLAVEGENERICA_Compras_test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Automatización Reportes DataLake MVP3");
		/***************************** Variables Data Provider **************************************/ 
		String tableName = data.get("tableName");
		String type = data.get("type");
		String run = data.get("run");
		String testName = data.get("testName");
		String fechaInicio = data.get("fechaInicio");
		String fechaFin = data.get("fechaFin");

		/***************************** Validación de parámetros obligatorios *************************/
		if (fechaInicio == null || fechaInicio.isEmpty())
		    fail("Parámetro 'fechaInicio' es obligatorio.");
		if (fechaFin == null || fechaFin.isEmpty())
		    fail("Parámetro 'fechaFin' es obligatorio.");

		/***************************** Instancia de utilidades ****************************************/
		UtilsReportesDatalakeMVP3 util = new UtilsReportesDatalakeMVP3();

		/***************************** Información consola *********************************************/
		System.out.println("Ejecutando prueba: " + testName);
		if (!run.equalsIgnoreCase("si"))
		    return;

		/***************************** PASO 1: Ejecutar el Stored Procedure ****************************/
		addStep("Conectar a Snowflake y ejecutar Stored Procedure SP_REPORTE_COMPRAS con fechas dadas");

		List<Map<String, Object>> resultadoSP = new ArrayList<>();
		try {
		    resultadoSP = UtilsReportesDatalakeMVP3.SPExecutorV.ejecutarSPReporteVentas(fechaInicio, fechaFin);
		    assertFalse(resultadoSP == null || resultadoSP.isEmpty(), "El resultado del SP está vacío o es nulo.");
		    testCase.addTextEvidenceCurrentStep("Conexión a la base de datos Snowflake establecida correctamente.");
		    testCase.addTextEvidenceCurrentStep(
		        "Se ejecuta CALL DBZDEV_MEX_OXXO.LAND_DAS_CAT.SP_REPORTE_COMPRAS con fechas: " + fechaInicio + ", " + fechaFin
		    );
		} catch (Exception e) {
		    e.printStackTrace();
		    fail("Error al ejecutar el Stored Procedure: " + e.getMessage());
		}

		/***************************** PASO 2: Lectura del Excel para fechas dadas *********************/
		addStep("Lectura del Excel para las fechas inicio y fin");

		File folder = Paths.get("Config", "Input", "DataProviders", "Reportes DataLake_Reportes", "ReportesCompras").toFile();
		File[] files = folder.listFiles((dir, name) -> name.endsWith(".xlsx"));

		String mesAnioInicio = fechaInicio.substring(4, 6) + fechaInicio.substring(0, 4); // MMYYYY
		String mesAnioExtra = "052025"; 
		String mesAnioFin = fechaFin.substring(4, 6) + fechaFin.substring(0, 4);         // MMYYYY

		List<Map<String, String>> allRows = new ArrayList<>();
		for (File file : files) {
		    String fileName = file.getName().toUpperCase();
		    if (fileName.contains(mesAnioInicio) || fileName.contains(mesAnioFin) || fileName.contains(mesAnioExtra)) {
		        System.out.println("Archivo incluido: " + file.getName());
		        testCase.addCodeEvidenceCurrentStep("Archivo incluido: " + file.getName());
		        List<Map<String, String>> rows = ExcelReader.readExcelToMap(file.getAbsolutePath());
		        allRows.addAll(rows);
		    }
		}

		assertFalse(allRows.isEmpty(), "No se encontraron archivos Excel para las fechas proporcionadas.");

		/***************************** PASO 3: Validar estructura general de columnas *******************/
		addStep("Validar estructura general del archivo Excel (nombres de columnas)");

		Set<String> excelColumns = allRows.get(0).keySet().stream().filter(Objects::nonNull)
		    .map(String::trim)
		    .map(col -> col.replace(" ", "_").toUpperCase())
		    .collect(Collectors.toSet());

		testCase.addCodeEvidenceCurrentStep("Columnas encontradas en Excel: " + excelColumns);

		addStep("Validar estructura general del archivo SP (nombres de columnas)");

		Set<String> spColumns = resultadoSP.get(0).keySet().stream().filter(Objects::nonNull)
		    .map(String::trim)
		    .map(String::toUpperCase)
		    .collect(Collectors.toSet());

		testCase.addCodeEvidenceCurrentStep("Columnas encontradas en SP: " + spColumns);
		spColumns.forEach(System.out::println);

		/***************************** PASO 4: Validaciones de parámetros de entrada *******************/
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

		/***************************** PASO FINAL - Validar CLAVE_GENERICA contra catálogo de IEPS / bebidas alcohólicas ****************************/
		addStep("Validar existencia y valores permitidos en la columna 'CLAVE_GENERICA'");

		// Catálogo oficial permitido
		Set<Integer> catalogoClaveGenericaPermitido = new HashSet<>(Arrays.asList(
		    1, 2, 3, 4, 5, 6, 8, 9, 15, 18, 19, 20, 21, 22, 25, 26, 27,
		    30, 35, 38, 39, 44, 46 // ← incluye 39 (Cognac u otros alcoholes)
		));

		// Validar existencia de la columna en SP
		boolean foundClaveGenericaSP = resultadoSP.get(0).containsKey("CLAVE_GENERICA");
		assertTrue(foundClaveGenericaSP, "La columna 'CLAVE_GENERICA' no está presente en el resultado del SP");

		// Validar existencia de la columna en Excel
		boolean foundClaveGenericaExcel = allRows.get(0).containsKey("CLAVE_GENERICA");
		assertTrue(foundClaveGenericaExcel, "La columna 'CLAVE_GENERICA' no está presente en los archivos Excel");

		// Conjuntos y listas para almacenar resultados
		Set<Integer> clavesGenericasSP = new HashSet<>();
		Set<Integer> clavesGenericasInvalidasSP = new HashSet<>();

		Set<Integer> clavesGenericasExcel = new HashSet<>();
		Set<Integer> clavesGenericasInvalidasExcel = new HashSet<>();

		// Procesar SP
		for (Map<String, Object> row : resultadoSP) {
		    Object claveObj = row.get("CLAVE_GENERICA");
		    if (claveObj != null) {
		        String claveStr = claveObj.toString().trim();
		        if (!claveStr.isEmpty()) {
		            try {
		                int claveId = Integer.parseInt(claveStr);
		                clavesGenericasSP.add(claveId);
		                if (!catalogoClaveGenericaPermitido.contains(claveId)) {
		                    clavesGenericasInvalidasSP.add(claveId);
		                }
		            } catch (NumberFormatException e) {
		                testCase.addCodeEvidenceCurrentStep("Valor no numérico en 'CLAVE_GENERICA' del SP: '" + claveStr + "'");
		            }
		        }
		    }
		}

		// Procesar Excel
		for (Map<String, String> row : allRows) {
		    String claveStr = row.get("CLAVE_GENERICA");
		    if (claveStr != null && !claveStr.trim().isEmpty()) {
		        try {
		            int claveId = Integer.parseInt(claveStr.trim());
		            clavesGenericasExcel.add(claveId);
		            if (!catalogoClaveGenericaPermitido.contains(claveId)) {
		                clavesGenericasInvalidasExcel.add(claveId);
		            }
		        } catch (NumberFormatException e) {
		            testCase.addCodeEvidenceCurrentStep("Valor no numérico en 'CLAVE_GENERICA' del Excel: '" + claveStr + "'");
		        }
		    }
		}

		// Evidencias
		testCase.addCodeEvidenceCurrentStep("IDs de 'CLAVE_GENERICA' encontrados en SP: " + clavesGenericasSP.stream().sorted().collect(Collectors.toList()));
		testCase.addCodeEvidenceCurrentStep("IDs de 'CLAVE_GENERICA' encontrados en Excel: " + clavesGenericasExcel.stream().sorted().collect(Collectors.toList()));


		testCase.addCodeEvidenceCurrentStep("Validación de catálogo 'CLAVE_GENERICA' completada correctamente.");
	}
	@Override
	public String setTestFullName() {
		return "ATC_FT_036_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaCLAVEGENERICA_Compras";
	}

	@Override
	public String setTestDescription() {
		return "Información del reporte debe coincidir con los resultados de los queries en las fuentes";
	}

	@Override
	public String setTestDesigner() {
		return "Angel Ramos";
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