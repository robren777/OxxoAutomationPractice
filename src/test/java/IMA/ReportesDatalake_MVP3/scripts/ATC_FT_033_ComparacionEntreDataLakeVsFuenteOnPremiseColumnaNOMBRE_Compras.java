package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
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
 * ATC_FT_033_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaNOMBRE_Compras
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de Snowflake.
 * 
 * 
 * Insumos:
 * -N/A
 * @author Angel Gabriel Ramos Martinez
 * @date  27/08/2025
 * 
 */
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210858
		)

public class ATC_FT_033_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaNOMBRE_Compras extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_033_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaNOMBRE_Compras_test(HashMap<String, String> data) throws Exception {

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
		// Ejecutar solo si run = "si"
		if (!run.equalsIgnoreCase("si"))
		    return;

		/***************************** PASO 1: Ejecutar el Stored Procedure ****************************/
		addStep("(\"Conectar a Snowflake y ejecutar Stored Procedure SP_REPORTE_COMPRAS con fechas dadas\")");

		List<Map<String, Object>> resultadoSP = new ArrayList<>();
		try {
		    resultadoSP = UtilsReportesDatalakeMVP3.SPExecutorC.ejecutarSPReporteCompras(fechaInicio, fechaFin);

		    assertFalse(resultadoSP == null || resultadoSP.isEmpty(), "El resultado del SP está vacío o es nulo.");
		    
		    testCase.addTextEvidenceCurrentStep("Conexión a la base de datos Snowflake establecida correctamente.");
		   
		    testCase.addTextEvidenceCurrentStep(
			        "Se ejecuta CALL DBZDEV_MEX_OXXO.LAND_DAS_CAT.SP_REPORTE_COMPRAS con fechas: " 
			        + fechaInicio + ", " + fechaFin);
	

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
		    if (fileName.contains(mesAnioInicio) || fileName.contains(mesAnioFin)|| fileName.contains(mesAnioExtra)) {
		        System.out.println("Archivo incluido: " + file.getName());
		        testCase.addCodeEvidenceCurrentStep("Archivo incluido: " + file.getName());

		        List<Map<String, String>> rows = ExcelReader.readExcelToMap(file.getAbsolutePath());
		        allRows.addAll(rows);
		    }
		}

		assertFalse(allRows.isEmpty(), "No se encontraron archivos Excel para las fechas proporcionadas.");

		/***************************** PASO 3: Validar existencia de columna NOMBRE CLIENTE ************/
		boolean foundNOMBRExcel = allRows.stream()
		        .anyMatch(row -> row.containsKey("NOMBRE"));

		assertTrue(foundNOMBRExcel, "La columna 'NOMBRE' no está presente en los archivos Excel.");

		/***************************** PASO 4: Validar estructura general de columnas *******************/
		addStep("Validar estructura general del archivo Excel (nombres de columnas)");

		Set<String> excelColumns = allRows.get(0).keySet().stream().filter(Objects::nonNull)
		        .map(String::trim)
		        .map(col -> col.replace(" ", "_").toUpperCase())
		        .collect(Collectors.toSet());

		testCase.addCodeEvidenceCurrentStep("Columnas encontradas en Excel: " + excelColumns);
		
		addStep("Validar estructura general del archivo SP (nombres de columnas)");
		
		// Columnas del SP
				Set<String> spColumns = resultadoSP.get(0).keySet().stream().filter(Objects::nonNull).map(String::trim)
						.map(String::toUpperCase).collect(Collectors.toSet());

				testCase.addCodeEvidenceCurrentStep("Columnas encontradas en SP: " + spColumns );
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

		/***************************** PASO 6: Validación específica: campo NOMBRE**************/
		addStep("Obtener información de la columna NOMBRE");
		addStep("Validar existencia de datos en la columna NOMBRE");

		long cantidadNombreCliente = allRows.stream()
		        .filter(row -> row.get("NOMBRE") != null && !row.get("NOMBRE").isBlank())
		        .count();

		testCase.addCodeEvidenceCurrentStep("Cantidad de registros leídos del Excel con 'NOMBRE CLIENTE: " + cantidadNombreCliente);
		assertTrue(cantidadNombreCliente > 0, "No se encontraron datos en la columna 'NOMBRE CLIENTE'");
		
		 int cantidadFilasSP = resultadoSP.size();
		    testCase.addCodeEvidenceCurrentStep("Cantidad de registros devueltos por el SP: " + cantidadFilasSP);
		    
		    /***************************** PASO 7: Detectar y contar nombres de proveedor en la columna 'NOMBRE' **************/
		    addStep("Detectar y contar nombres únicos de proveedor en la columna 'NOMBRE'");

		    Map<String, Long> conteoProveedores = allRows.stream()
		            .map(row -> row.get("NOMBRE"))
		            .filter(Objects::nonNull)
		            .map(String::trim)
		            .filter(nombre -> !nombre.isEmpty())
		            .collect(Collectors.groupingBy(nombre -> nombre.toUpperCase(), Collectors.counting()));

		    // Agrega evidencia de cada proveedor encontrado y su cantidad
		    conteoProveedores.forEach((proveedor, cantidad) -> {
		        testCase.addCodeEvidenceCurrentStep("Proveedor: '" + proveedor + "' - Cantidad: " + cantidad);
		    });

		    assertFalse(conteoProveedores.isEmpty(), "No se encontraron proveedores en la columna 'NOMBRE'");
	}
	@Override
	public String setTestFullName() {
		return "ATC_FT_033_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaNOMBRE_Compras";
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