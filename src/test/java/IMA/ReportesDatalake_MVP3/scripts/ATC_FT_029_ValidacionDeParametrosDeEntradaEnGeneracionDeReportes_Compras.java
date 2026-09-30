package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
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
 * Verificar que el sistema permite ingresar correctamente los siguientes parámetros al generar el reporte: Fecha inicio, Fecha fin, Tipo de reporte
 * 
 * Nombre casos de prueba manual que se abarcan:
 * ATC_FT_029_ValidacionDeParametrosDeEntradaEnGeneracionDeReportes_Compras
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de Snowflake.
 * Insumos:
 * -N/A
 * @author Angel Gabriel Ramos Martinez
 * @date 26/08/2025
 */
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId =210858
		)

public class ATC_FT_029_ValidacionDeParametrosDeEntradaEnGeneracionDeReportes_Compras extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_029_ValidacionDeParametrosDeEntradaEnGeneracionDeReportes_Compras_test(HashMap<String, String> data) throws Exception {
						
		testCase.setProject_Name("Automatización Reportes DataLake MVP3");
		/*****************************
		 * Variables Data Provider
		 **************************************/
		String type = data.get("type");
		String run = data.get("run");
		String testName = data.get("testName");
		String fechaInicio = data.get("fechaInicio");
		String fechaFin = data.get("fechaFin");

		/*****************************
		 * Validación de parámetros obligatorios
		 *************************/
		addStep("Validar parámetros obligatorios: FechaInicio, FechaFin, Tipo de reporte");
		if (fechaInicio == null || fechaInicio.isEmpty())
			fail("Parámetro 'fechaInicio' es obligatorio.");
		if (fechaFin == null || fechaFin.isEmpty())
			fail("Parámetro 'fechaFin' es obligatorio.");

		/*****************************
		 * Instancia de utilidades
		 ****************************************/
		UtilsReportesDatalakeMVP3 util = new UtilsReportesDatalakeMVP3();

		/*****************************
		 * Información consola
		 *********************************************/
		System.out.println("Ejecutando prueba: " + testName);

		// Ejecutar solo si run = "si"
		if (!run.equalsIgnoreCase("si"))
			return;

		/*****************************
		 * PASO 1: Ejecutar SP directamente
		 ********************************/
		addStep("Ejecutar Stored Procedure SP_REPORTE_Compras con fechas dadas.");

		List<Map<String, Object>> resultadoSP = new ArrayList<>();

		try {
			resultadoSP = UtilsReportesDatalakeMVP3.SPExecutorC.ejecutarSPReporteCompras(fechaInicio, fechaFin);

			assertFalse(resultadoSP == null || resultadoSP.isEmpty(), "El resultado del SP está vacío o es nulo.");

			testCase.addTextEvidenceCurrentStep(
					"Se ejecuta CALL DBZDEV_MEX_OXXO.LAND_DAS_CAT.SP_REPORTE_COMPRAS con fechas: " + fechaInicio + ", "
							+ fechaFin);

			System.out.println("Cantidad de filas devueltas por el SP: " + resultadoSP.size());

			Map<String, Object> primeraFila = resultadoSP.get(0);
			System.out.println("Columnas encontradas en la primera fila:");
			for (Map.Entry<String, Object> entry : primeraFila.entrySet()) {
				System.out.println(" → " + entry.getKey() + " = " + entry.getValue());
			}

		} catch (SQLException e) {
			e.printStackTrace();
			fail("Error al ejecutar el Stored Procedure o al consultar los resultados.");
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			fail("El SP devolvió una lista vacía o malformada.");
		}

		/*****************************
		 * PASO 2: Lectura de Excel de fechas dadas
		 ************************/
		addStep("Lectura del Excel para las fechas inicio y fin");

		File folder = Paths.get("Config", "Input", "DataProviders", "Reportes DataLake_Reportes", "ReportesCompras").toFile();
		File[] files = folder.listFiles((dir, name) -> name.endsWith(".xlsx"));

		String mesAnioInicio = fechaInicio.substring(4, 6) + fechaInicio.substring(0, 4); // "042025"
		String mesAnioExtra = "052025"; // Mayo 2025 fijo
		String mesAnioFin = fechaFin.substring(4, 6) + fechaFin.substring(0, 4); // "062025"
		// MMYYYY
		System.out.println("pasa");

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

		/*****************************
		 * PASO 3: Validación estructura columnas
		 ***************************/
		addStep("Comparar estructura entre Excel y resultado del SP (solo columnas)");

		// Columnas del Excel
		Set<String> excelColumns = allRows.get(0).keySet().stream().filter(Objects::nonNull).map(String::trim)
				.map(col -> col.replace(" ", "_").toUpperCase()).collect(Collectors.toSet());

		System.out.println("Columnas del Excel:");
		excelColumns.forEach(System.out::println);

		// Columnas del SP
		Set<String> spColumns = resultadoSP.get(0).keySet().stream().filter(Objects::nonNull).map(String::trim)
				.map(String::toUpperCase).collect(Collectors.toSet());

		System.out.println("Columnas del SP:");
		spColumns.forEach(System.out::println);

		// Evidencia
		testCase.addCodeEvidenceCurrentStep("Columnas en Excel (normalizadas): " + excelColumns);
		testCase.addCodeEvidenceCurrentStep("Columnas del SP (normalizadas): " + spColumns);

		// Comparación de columnas
		if (!excelColumns.equals(spColumns)) {
			Set<String> soloEnExcel = new HashSet<>(excelColumns);
			soloEnExcel.removeAll(spColumns);

			Set<String> soloEnSP = new HashSet<>(spColumns);
			soloEnSP.removeAll(excelColumns);

			System.out.println("Columnas solo en Excel: " + soloEnExcel);
			System.out.println("Columnas solo en SP: " + soloEnSP);
		}

		assertEquals(excelColumns.size(), spColumns.size(), "Validación del número de columnas");
		assertTrue(excelColumns.equals(spColumns), "Validación de coincidencia exacta de columnas");

		testCase.addBoldTextEvidenceCurrentStep("Validación de estructura exitosa. Columnas del SP y Excel coinciden.");

		/*****************************
		 * PASO 4: Validaciones de parámetros
		 ******************************/
		addStep("Validar formato y valores de parámetros");

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

		testCase.addBoldTextEvidenceCurrentStep("Stored Procedure ejecutado y validaciones completadas correctamente.");
	}
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_029_ValidacionDeParametrosDeEntradaEnGeneracionDeReportes_Compras";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que el sistema permite ingresar correctamente los siguientes parámetros al generar el reporte: Fecha inicio, Fecha fin, Tipo de reporte";
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