package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3.ExcelReader;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

/**
 * Descripcion:
 * Información del reporte debe coincidir con los resultados de los Queries en las fuentes
 * Nombre casos de prueba manual que se abarcan:
 * ATC_FT_032_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaRFC_Compras
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
		userStoryId = 210858
		)

public class ATC_FT_032_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaRFC_Compras extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_032_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaRFC_Compras_test(HashMap<String, String> data) throws Exception {
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

		/*****************************
		 * Lectura Excel
		 **************************************************/
		addStep("Lectura del Excel");
		File folder = Paths.get("Config", "Input", "DataProviders", "Reportes DataLake_Reportes", "ReportesCompras").toFile();
		File[] files = folder.listFiles((dir, name) -> name.endsWith(".xlsx"));

		List<Map<String, String>> allRows = new ArrayList<>();
		for (File file : files) {
			List<Map<String, String>> rows = ExcelReader.readExcelToMap(file.getAbsolutePath());
			allRows.addAll(rows);
		}

		// Validar existencia de columnas requeridas
		boolean foundNombre = allRows.stream().anyMatch(row -> row.containsKey("NOMBRE"));
		boolean foundRFC = allRows.stream().anyMatch(row -> row.containsKey("RFC"));
		assertTrue(foundNombre, "La columna 'NOMBRE' no está presente en los archivos Excel.");
		assertTrue(foundRFC, "La columna 'RFC' no está presente en los archivos Excel.");

		/*****************************
		 * Información Consola
		 *******************************************/
		System.out.println("Ejecutando prueba: " + testName);

		if (run.equalsIgnoreCase("si")) {

			/*****************************
			 * PASO 1: Ejecutar el Stored Procedure
			 ****************************/
			addStep("Conectar a Snowflake y ejecutar Stored Procedure SP_REPORTE_COMPRAS con fechas dadas");

			try {
				resultadoSP = UtilsReportesDatalakeMVP3.SPExecutorC.ejecutarSPReporteCompras(fechaInicio, fechaFin);
				assertFalse(resultadoSP == null || resultadoSP.isEmpty(), "El resultado del SP está vacío o es nulo.");

				testCase.addTextEvidenceCurrentStep("Conexión a Snowflake realizada correctamente.");
				testCase.addTextEvidenceCurrentStep("Se ejecuta SP con fechas: " + fechaInicio + ", " + fechaFin);
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
			String mesAnioExtra = "052025"; // Ajustable si necesitas incluir meses específicos adicionales

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
			 * PASO 3: Agrupar por NOMBRE y RFC del Excel
			 ****************************/
			addStep("Agrupar por nombre y RFC del proveedor y contar registros en Excel");

			Map<String, Long> excelProveedorConteo = allRowsFiltrados.stream()
					.filter(row -> row.get("RFC") != null && !row.get("RFC").trim().isEmpty()
							&& row.get("NOMBRE") != null && !row.get("NOMBRE").trim().isEmpty())
					.map(row -> {
						String rfc = row.get("RFC").trim().toUpperCase();
						String nombre = row.get("NOMBRE").trim().toUpperCase();
						return nombre + "|" + rfc;
					}).collect(Collectors.groupingBy(clave -> clave, Collectors.counting()));

			if (excelProveedorConteo.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No se encontraron registros con 'NOMBRE' y 'RFC' en Excel.");
			} else {
				excelProveedorConteo.forEach((clave, cantidad) -> {
					String[] partes = clave.split("\\|");
					String nombreProveedor = partes[0];
					String rfcProveedor = partes[1];

					testCase.addTextEvidenceCurrentStep("[Excel] Nombre proveedor: " + nombreProveedor + " | RFC: "
							+ rfcProveedor + " => " + cantidad + " registros");
				});
			}

			/*****************************
			 * PASO 4: Agrupar por NOMBRE y RFC del SP
			 ****************************/
			addStep("Agrupar por nombre y RFC del proveedor y contar registros en SP");

			Map<String, Long> spProveedorConteo = resultadoSP.stream()
					.filter(row -> row.get("RFC") != null && row.get("NOMBRE") != null).map(row -> {
						String rfc = row.get("RFC").toString().trim().toUpperCase();
						String nombre = row.get("NOMBRE").toString().trim().toUpperCase();
						return nombre + "|" + rfc;
					}).collect(Collectors.groupingBy(clave -> clave, Collectors.counting()));

			if (spProveedorConteo.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No se encontraron registros con 'NOMBRE' y 'RFC' en SP.");
			} else {
				spProveedorConteo.forEach((clave, cantidad) -> {
					String[] partes = clave.split("\\|");
					String nombreProveedor = partes[0];
					String rfcProveedor = partes[1];

					testCase.addTextEvidenceCurrentStep("[SP] Nombre proveedor: " + nombreProveedor + " | RFC: "
							+ rfcProveedor + " => " + cantidad + " registros");
				});
			}

			/*****************************
			 * Totales generales
			 **************************************************/
			testCase.addTextEvidenceCurrentStep(
					"Total de registros en Excel (filtrados por fechas): " + allRowsFiltrados.size());
			testCase.addTextEvidenceCurrentStep("Total de registros en SP (resultado): " + resultadoSP.size());
		}

	}
	@Override
	public String setTestFullName() {
		return "ATC_FT_032_ComparacionEntreDataLakeVsFuenteOnPremiseColumnaRFC_Compras";
	}

	@Override
	public String setTestDescription() {
		return "Información del reporte debe coincidir con los resultados de los Queries en las fuentes";
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