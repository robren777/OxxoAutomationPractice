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
import modelo.BaseExecution;

/**
 * Descripcion:
 * Información del reporte debe coincidir con los resultados de los queries en las fuentes
 * 
 * Nombre casos de prueba manual que se abarcan: ATC_FT_041_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaUNIDAD_DE_MEDIDA_Compras
 * 
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de Snowflake.
 * 
 * 
 * Insumos:
 * -N/A
 * @author Joaquin Aguilar
 * @date 26/08/2025
 * 
 *//*
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210855
		)*/

public class ATC_FT_041_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaUNIDAD_DE_MEDIDA_Compras extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true, testName = "")
    public void ATC_FT_041_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaUNIDAD_DE_MEDIDA_Compras_test(HashMap<String, String> data) throws Exception {
    	testCase.setProject_Name("Automatización Reportes DataLake MVP3");
    	/*****************************
		 * Variables Data Provider
		 **************************************/
		String tableName = data.get("tableName");
		String type = data.get("type");
		String run = data.get("run");
		String typeB = data.get("typeB");
		String testName = data.get("testName");
		String fechaInicio = data.get("fechaInicio");
		String fechaFin = data.get("fechaFin");

		/*****************************
		 * Instancias
		 **************************************************/
		UtilsReportesDatalakeMVP3 uti = new UtilsReportesDatalakeMVP3();
		Connection connection = null;
		List<Map<String, Object>> resultadoSP = new ArrayList<>();
		int responseSnowFlakeSize = 0;

		/*****************************
		 * Lectura Excel
		 **************************************************/
		addStep("Lectura del excel");
		File folder = Paths.get("Config", "Input", "DataProviders", "Reportes DataLake_Reportes", "ReportesCompras").toFile();
		File[] files = folder.listFiles((dir, name) -> name.endsWith(".xlsx"));

		List<Map<String, String>> allRows = new ArrayList<>();
		for (File file : files) {
			List<Map<String, String>> rows = ExcelReader.readExcelToMap(file.getAbsolutePath());
			allRows.addAll(rows);
		}

		// Validar existencia de UNIDAD_DE_MEDIDA en Excel (como header)
		boolean foundUNIDAD_DE_MEDIDAExcel = allRows.stream().anyMatch(row -> row.containsKey("UNIDAD_DE_MEDIDA"));
		assertTrue(foundUNIDAD_DE_MEDIDAExcel, "La columna UNIDAD_DE_MEDIDA no está presente en los archivos Excel.");

		/*****************************
		 * Información Consola
		 *******************************************/
		System.out.println("Ejecutando prueba: " + testName);

		if (run.equalsIgnoreCase("si")) {

			/*****************************
			 * PASO 1: Ejecutar el Stored Procedure
			 ****************************/
			addStep("(\"Conectar a Snowflake y ejecutar Stored Procedure SP_REPORTE_COMPRAS con fechas dadas\")");

			try {
				resultadoSP = UtilsReportesDatalakeMVP3.SPExecutorC.ejecutarSPReporteCompras(fechaInicio, fechaFin);

				assertFalse(resultadoSP == null || resultadoSP.isEmpty(), "El resultado del SP está vacío o es nulo.");

				testCase.addTextEvidenceCurrentStep("Conexión a la base de datos Snowflake establecida correctamente.");

				testCase.addTextEvidenceCurrentStep(
						"Se ejecuta CALL DBZDEV_MEX_OXXO.LAND_DAS_CAT.SP_REPORTE_COMPRAS con fechas: " + fechaInicio
								+ ", " + fechaFin);

			} catch (Exception e) {
				e.printStackTrace();
				fail("Error al ejecutar el Stored Procedure: " + e.getMessage());
			}

			/*****************************
			 * PASO 2: Filtrar Excel por fechas dadas
			 ****************************/
			addStep("Filtrar archivos del Excel por las fechas inicio y fin");

			String mesAnioInicio = fechaInicio.substring(4, 6) + fechaInicio.substring(0, 4); // MMYYYY
			String mesAnioFin = fechaFin.substring(4, 6) + fechaFin.substring(0, 4); // MMYYYY
			String mesAnioExtra = "052025";

			List<Map<String, String>> allRowsFiltrados = new ArrayList<>();
			for (File file : files) {
				String fileName = file.getName().toUpperCase();
				if (fileName.contains(mesAnioInicio) || fileName.contains(mesAnioFin)
						|| fileName.contains(mesAnioExtra)) {
					System.out.println("Archivo incluido: " + file.getName());
					testCase.addCodeEvidenceCurrentStep("Archivo incluido: " + file.getName());

					List<Map<String, String>> rows = ExcelReader.readExcelToMap(file.getAbsolutePath());
					allRowsFiltrados.addAll(rows);
				}
			}

			assertFalse(allRowsFiltrados.isEmpty(), "No se encontraron archivos Excel para las fechas proporcionadas.");

			/*****************************
			 * PASO 3: Validar existencia de columna UNIDAD_DE_MEDIDA
			 ************/
			boolean foundUnidadDeMedidaExcel = allRowsFiltrados.stream()
					.anyMatch(row -> row.containsKey("UNIDAD_DE_MEDIDA"));
			assertTrue(foundUnidadDeMedidaExcel, "La columna 'UNIDAD_DE_MEDIDA' no está presente en los archivos Excel.");

			/*****************************
			 * PASO 4: Validar estructura general de columnas
			 *******************/
			addStep("Validar estructura general del archivo Excel (nombres de columnas)");

			Set<String> excelColumns = allRowsFiltrados.get(0).keySet().stream().filter(Objects::nonNull)
					.map(String::trim).map(col -> col.replace(" ", "_").toUpperCase()).collect(Collectors.toSet());

			testCase.addCodeEvidenceCurrentStep("Columnas encontradas en Excel: " + excelColumns);

			addStep("Validar estructura general del archivo SP (nombres de columnas)");

			Set<String> spColumns = resultadoSP.get(0).keySet().stream().filter(Objects::nonNull).map(String::trim)
					.map(String::toUpperCase).collect(Collectors.toSet());

			testCase.addCodeEvidenceCurrentStep("Columnas encontradas en SP: " + spColumns);
			spColumns.forEach(System.out::println);

			/*****************************
			 * PASO 5: Validaciones de parámetros de entrada
			 *******************/
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

			/*****************************
			 * PASO 6: Validación específica: campo UNIDAD_DE_MEDIDA
			 **************/
			addStep("Obtener información de la columna UNIDAD_DE_MEDIDA");
			addStep("Validar existencia de datos en la columna 'UNIDAD_DE_MEDIDA'");

			long cantidadUnidadDeMedida = allRowsFiltrados.stream()
					.filter(row -> row.get("UNIDAD_DE_MEDIDA") != null && !row.get("").isBlank()).count();

			testCase.addCodeEvidenceCurrentStep(
					"Cantidad de registros leídos del Excel con 'UNIDAD_DE_MEDIDA': " + cantidadUnidadDeMedida);
			assertTrue(cantidadUnidadDeMedida > 0, "No se encontraron datos en la columna 'UNIDAD_DE_MEDIDA'");

			int cantidadFilasSP = resultadoSP.size();
			testCase.addCodeEvidenceCurrentStep("Cantidad de registros devueltos por el SP: " + cantidadFilasSP);

			/*****************************
			 * PASO 7 - Validar valores en columna UNIDAD_DE_MEDIDA
			 **************/
			addStep("Validar que los valores en la columna 'UNIDAD_DE_MEDIDA' correspondan a los valores permitidos: 0, 1, 2, 3, 4, 5, 6");

		    // Valores válidos permitidos para UNIDAD_DE_MEDIDA
		    Set<Integer> udmValidos = Set.of(0, 1, 2, 3, 4, 5, 6);

		    // Para guardar valores encontrados en SP y Excel
		    Set<Integer> udmSp = new HashSet<>();
		    Set<Integer> udmExcel = new HashSet<>();

		    // Para guardar valores inválidos encontrados
		    List<String> udmInvalidosSp = new ArrayList<>();
		    List<String> udmInvalidosExcel = new ArrayList<>();

		    // --- Validar columna UNIDAD_DE_MEDIDA en resultado del SP ---
		    for (Map<String, Object> row : resultadoSP) {
		        Object udmObj = row.get("UNIDAD_DE_MEDIDA");
		        if (udmObj != null) {
		            String udmStr = udmObj.toString().trim();
		            if (!udmStr.isEmpty()) {
		                try {
		                    int udm = Integer.parseInt(udmStr);
		                    if (udmValidos.contains(udm)) {
		                        udmSp.add(udm);
		                    } else {
		                        udmInvalidosSp.add(udmStr);
		                    }
		                } catch (NumberFormatException e) {
		                    udmInvalidosSp.add(udmStr);
		                }
		            }
		        } else {
		            udmInvalidosSp.add("NULL");
		        }
		    }

		    // --- Validar columna UNIDAD_DE_MEDIDA en el Excel ---
		    for (Map<String, String> row : allRows) {
		        String udmStr = row.get("UNIDAD_DE_MEDIDA");
		        if (udmStr != null && !udmStr.trim().isEmpty()) {
		            try {
		                int udm1 = Integer.parseInt(udmStr.trim());
		                if (udmValidos.contains(udm1)) {
		                    udmExcel.add(udm1);
		                } else {
		                    udmInvalidosExcel.add(udmStr);
		                }
		            } catch (NumberFormatException e) {
		                udmInvalidosExcel.add(udmStr);
		            }
		        } else {
		            udmInvalidosExcel.add("NULL");
		        }
		    }


		    // --- Agregar evidencia ---
		    testCase.addCodeEvidenceCurrentStep("Valores válidos de UNIDAD_DE_MEDIDA encontrados en SP: " + udmSp.stream().sorted().collect(Collectors.toList()));
		    testCase.addCodeEvidenceCurrentStep("Valores válidos de UNIDAD_DE_MEDIDA encontrados en Excel: " + udmExcel.stream().sorted().collect(Collectors.toList()));

		    if (!udmInvalidosSp.isEmpty()) {
		        testCase.addCodeEvidenceCurrentStep("Valores inválidos en columna 'UNIDAD_DE_MEDIDA' del SP: " + udmInvalidosSp);
		        fail("Existen valores inválidos en la columna 'UNIDAD_DE_MEDIDA' del SP.");
		    }

		    if (!udmInvalidosExcel.isEmpty()) {
		        testCase.addCodeEvidenceCurrentStep("Valores inválidos en columna 'UNIDAD_DE_MEDIDA' del Excel: " + udmInvalidosExcel);
		        fail("Existen valores inválidos en la columna 'UNIDAD_DE_MEDIDA' del Excel.");
		    }

		    testCase.addBoldTextEvidenceCurrentStep("Validación exitosa: Todos los valores en 'UNIDAD_DE_MEDIDA' son válidos.");
		}
	}	

		
	@Override
	public String setTestFullName() {
		return "ATC_FT_041_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaUNIDAD_DE_MEDIDA_Compras";
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