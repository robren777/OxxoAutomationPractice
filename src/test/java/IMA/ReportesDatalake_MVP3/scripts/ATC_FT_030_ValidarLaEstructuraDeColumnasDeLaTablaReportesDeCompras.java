package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.nio.file.Paths;
import java.sql.SQLException;
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
 * Verificar que cada columna requerida esté presente y estructurada correctamente.
 * 
 * Nombre casos de prueba manual que se abarcan:
 * ATC_T_005_ValidarLaEstructuraDeColumnasDeLatablaReportesDeVentas
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de Snowflake.
 * 
 * 
 * Insumos:
 * -N/A
 * @author Angel Gabriel Ramos Martinez
 * @date 26/08/2025
 *
 */
 
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210858
		)

public class ATC_FT_030_ValidarLaEstructuraDeColumnasDeLaTablaReportesDeCompras extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_030_ValidarLaEstructuraDeColumnasDeLaTablaReportesDeCompras_test(HashMap<String, String> data) throws Exception {
						
		testCase.setProject_Name("Automatización Reportes DataLake MVP3");
		/*****************************
		 * Variables Data Provider
		 **************************************/
		String tableName = data.get("tableName");
		String owner = data.get("owner");
		String type = data.get("type");
		String run = data.get("run");
		String typeB = data.get("typeB");
		String testName = data.get("testName");

		/*****************************
		 * Instancias
		 **************************************************/
		UtilsReportesDatalakeMVP3 util = new UtilsReportesDatalakeMVP3();

		/*****************************
		 * Lista esperada de columnas (opcional)
		 ***********************/
		Set<String> columnasEsperadas = new HashSet<>(
				Arrays.asList("TIPO_OPERACION", "RFC", "NOMBRE", "MES", "ESTADO", "TIPO_PRODUCTO", "EMPRESA_TABACALERA",
						"CLAVE_GENERICA", "GRADUACION", "UNIDADES", "EMPAQUE", "PRESENTACION", "UNIDAD_DE_MEDIDA",
						"COSTO", "IEPS", "CVE_IVA", "VENTA_IEPS", "VENTA_IVA", "ITEM", "ITEM_DESC", "IEPS_VAT",
						"CVE_IEPS", "VENTA_IEPS_VAT", "DIF_VENTA_IEPS", "RFC_ANT", "RAZON_SOCIAL_ANT"));

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

		/*****************************
		 * Validar si debe correr
		 ***************************************/
		if (!run.equalsIgnoreCase("si"))
			return;

		/*****************************
		 * Consola y descripción del caso
		 ********************************/
		addStep("Caso de prueba : " + testName);
		System.out.println("Ejecutando: " + testName);

		/*****************************
		 * PASO 1: Ejecutar Stored Procedure
		 *****************************/
		addStep("Ejecutar Stored Procedure SP_REPORTE_COMPRAS.");
		String periodoInicial = "202504";
		String periodoFinal = "202504";
		List<Map<String, Object>> resultadoSP = new ArrayList<>();

		try {
			resultadoSP = UtilsReportesDatalakeMVP3.SPExecutorC.ejecutarSPReporteCompras(periodoInicial, periodoFinal);

			assertFalse(resultadoSP == null || resultadoSP.isEmpty(), "El resultado del SP está vacío o es nulo.");

			testCase.addTextEvidenceCurrentStep(
					"se ejecuta CALL DBZDEV_MEX_OXXO.LAND_DAS_CAT.SP_REPORTE_COMPRAS para el mes " + periodoInicial);

			// LOG de diagnóstico del contenido
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
		 * PASO 2: Validación general de columnas
		 ************************/
		addStep("Comparar estructura entre Excel y resultado del SP (solo columnas)");

		// Obtener columnas del Excel
		Set<String> excelColumns = new HashSet<>();
		if (!allRows.isEmpty()) {
			excelColumns = allRows.get(0).keySet().stream().filter(Objects::nonNull).map(String::trim)
					.map(col -> col.replace(" ", "_").toUpperCase()).collect(Collectors.toSet());

			System.out.println("Columnas extraídas del Excel (normalizadas):");
			excelColumns.forEach(System.out::println);
		} else {
			fail("No se pudo leer ningún dato del archivo Excel.");
		}

		// Obtener columnas del resultado del SP
		Set<String> spColumns = new HashSet<>();
		if (!resultadoSP.isEmpty()) {
			spColumns = resultadoSP.get(0).keySet().stream().filter(Objects::nonNull).map(String::trim)
					.map(String::toUpperCase).collect(Collectors.toSet());

			System.out.println("Columnas extraídas del SP (normalizadas):");
			spColumns.forEach(System.out::println);
		} else {
			fail("No se puede obtener columnas del SP: la lista está vacía.");
		}

		// Evidencia para el reporte
		testCase.addCodeEvidenceCurrentStep("Columnas en Excel (normalizadas): " + excelColumns);
		testCase.addCodeEvidenceCurrentStep("Columnas del Stored Procedure (normalizadas): " + spColumns);

		// Mostrar diferencias, pero no fallar por columnas extra
		Set<String> soloEnExcel = new HashSet<>(excelColumns);
		soloEnExcel.removeAll(spColumns);

		Set<String> soloEnSP = new HashSet<>(spColumns);
		soloEnSP.removeAll(excelColumns);

		if (!soloEnExcel.isEmpty() || !soloEnSP.isEmpty()) {
			System.out.println(" Columnas solo en Excel: " + soloEnExcel);
			System.out.println(" Columnas solo en SP: " + soloEnSP);
		}

		// Validación flexible: Solo asegurar que el SP tiene todas las columnas
		// requeridas
		assertTrue(spColumns.containsAll(columnasEsperadas),
				"No todas las columnas esperadas están en el resultado del SP");

		testCase.addBoldTextEvidenceCurrentStep(
				"Estructura validada correctamente. El SP contiene todas las columnas esperadas.");

	}
	@Override
	public String setTestFullName() {
		return "ATC_FT_030_ValidarLaEstructuraDeColumnasDeLaTablaReportesDeCompras";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que cada columna requerida este presente y estructurada correctamente.";
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