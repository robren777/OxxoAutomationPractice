package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3.ExcelReader;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

/**
 * Descripcion:
 * Verificar que el sistema genere correctamente el reporte mensual..
 * 
 * Nombre casos de prueba manual que se abarcan:
 * ATC_FT_001_ValidacionDeReportesMensual
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de Snowflake.
 * Insumos:
 * -N/A
 * @author Angel Gabriel Ramos Martinez
 * @date 18/08/2025
 * 
 */
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210855
		) 


public class ATC_FT_001_ValidacionDeReportesMensual extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_001_ValidacionDeReportesMensual_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Automatización Reportes DataLake MVP3");
		 
		/***************************** Variables Data Provider **************************************/
		String tableName = data.get("tableName");    
		String owner = data.get("owner");
		String type = data.get("type");
		String run = data.get("run");
		String typeB = data.get("typeB");
		String testName = data.get("testName");

		/***************************** Lectura Excel **************************************************/
		addStep("Lectura del archivo Excel con la metadata esperada.");
		File folder = Paths.get("Config", "Input", "DataProviders", "Reportes DataLake_Reportes", "ReportesVentas").toFile();
		File[] files = folder.listFiles((dir, name) -> name.endsWith(".xlsx"));

		List<Map<String, String>> allRows = new ArrayList<>();
		for (File file : files) {
		    List<Map<String, String>> rows = ExcelReader.readExcelToMap(file.getAbsolutePath());
		    allRows.addAll(rows);
		}

		// Imprimir columnas detectadas para depuración
		System.out.println("Columnas detectadas en la primera fila del Excel:");
		if (!allRows.isEmpty()) {
		    for (String columna : allRows.get(0).keySet()) {
		        System.out.println("-> [" + columna + "]");
		    }
		}

		/***************************** Instancias **************************************************/
		UtilsReportesDatalakeMVP3 util = new UtilsReportesDatalakeMVP3();
		Connection connection = null;
		HashMap<String, String[]> responseSnow = new HashMap<>();

		// Validar existencia de "Reporte Mensual" en el Excel
		boolean foundReporteMesExcel = allRows.stream()
		    .anyMatch(row -> "202504".equalsIgnoreCase(row.get("MES")));

		testCase.addTextEvidenceCurrentStep(foundReporteMesExcel 
		    ? "Se encontró 'MES' en el archivo Excel." 
		    : "No se encontró 'MES' en el archivo Excel.");

		assertTrue(foundReporteMesExcel, "El Excel no contiene 'MES'.");

		/***************************** Ejecución del SP ***********************************************/
		addStep("Hacer conexion a la Base de datos de Snowflake.Llamar al SP dentro de snowflake CALL DBZDEV_MEX_OXXO.LAND_DAS_CAT.SP_REPORTE_VENTAS(202504,202504)");
		System.out.println("Ejecutando: " + testName);

		String periodoInicial = "202504";
		String periodoFinal = "202504";
		List<Map<String, Object>> resultadoSP = new ArrayList<>();

		try {
		    resultadoSP = UtilsReportesDatalakeMVP3.SPExecutorV.ejecutarSPReporteVentas(periodoInicial, periodoFinal);
		    assertFalse(resultadoSP.isEmpty(), "El resultado del SP está vacío.");
		    testCase.addTextEvidenceCurrentStep("conexion snowflake Exitosa, Stored Procedure ejecutado correctamente con datos para el mes " + periodoInicial);
		} catch (SQLException e) {
		    e.printStackTrace();
		    fail("Error al ejecutar el Stored Procedure o al consultar los resultados.");
		}

		/***************************** Validar impuestos esperados ************************************/
		addStep("Validar existencia de los impuestos IEPS esperados en los resultados del SP.");

		List<String> impuestosEsperados = Arrays.asList("IE1653", "IE1630", "IE1625", "IE0008", "IE1603");

		for (String impuesto : impuestosEsperados) {
		    boolean encontrado = resultadoSP.stream()
		        .anyMatch(row -> row.values().stream()
		            .anyMatch(val -> val != null && val.toString().contains(impuesto)));

		    if (encontrado) {
		        testCase.addTextEvidenceCurrentStep("Se encontró el impuesto " + impuesto + " en los resultados del SP.");
		    } else {
		        testCase.addTextEvidenceCurrentStep("No se encontró el impuesto " + impuesto + " en los resultados del SP.");
		        System.out.println("Advertencia: No se encontró el impuesto " + impuesto);
		    }
		}

		/***************************** Validar mes esperado ********************************************/
		addStep("Validar que el reporte contenga información del mes esperado.");
		boolean contieneMes = resultadoSP.stream()
		    .anyMatch(row -> row.values().stream()
		        .filter(Objects::nonNull)
		        .anyMatch(val -> val.toString().contains(periodoInicial)));

		assertTrue(contieneMes, "El reporte no contiene información del mes " + periodoInicial);
		testCase.addTextEvidenceCurrentStep("El reporte contiene información del mes " + periodoInicial + ".");

		/************ Paso: Validar existencia y cantidad de registros por impuesto ****************************/

		addStep("Validar existencia y cantidad de registros por impuesto para el mes " + periodoInicial);

		Map<String, Integer> registrosExcel = new HashMap<>();
		Map<String, Integer> registrosSP = new HashMap<>();

		// Contar registros por impuesto desde Excel, normalizando nombre de columna
		for (Map<String, String> row : allRows) {
		    if (periodoInicial.equals(row.get("MES"))) {
		        // Buscar clave 'CVE_IEPS' con normalización (trim)
		        for (Map.Entry<String, String> entry : row.entrySet()) {
		            String key = entry.getKey() != null ? entry.getKey().trim() : "";
		            String value = entry.getValue();

		            if ("CVE_IEPS".equalsIgnoreCase(key)) {
		                if (value != null && !value.trim().isEmpty()) {
		                    registrosExcel.merge(value.trim(), 1, Integer::sum);
		                }
		            }
		        }
		    }
		}

		// Contar registros por impuesto desde SP
		for (Map<String, Object> row : resultadoSP) {
		    Object mes = row.get("MES");
		    if (mes != null && periodoInicial.equals(mes.toString())) {
		        String impuesto = row.get("CVE_IEPS") != null ? row.get("CVE_IEPS").toString().trim() : null;
		        if (impuesto != null && !impuesto.isEmpty()) {
		            registrosSP.merge(impuesto, 1, Integer::sum);
		        }
		    }
		}

		// Mostrar evidencia por impuesto esperado
		for (String impuesto : impuestosEsperados) {
		    int countExcel = registrosExcel.getOrDefault(impuesto, 0);
		    int countSP = registrosSP.getOrDefault(impuesto, 0);

		    if (countSP > 0) {
		        testCase.addTextEvidenceCurrentStep("Se encontró el impuesto " + impuesto + " en los resultados del SP: " + countSP + " registros");
		    } else {
		        testCase.addTextEvidenceCurrentStep("No se encontró el impuesto " + impuesto + " en los resultados del SP");
		    }

		    if (countExcel > 0) {
		        testCase.addTextEvidenceCurrentStep("Se encontró el impuesto " + impuesto + " en los resultados del Excel: " + countExcel + " registros");
		    } else {
		        testCase.addTextEvidenceCurrentStep("No se encontró el impuesto " + impuesto + " en los resultados del Excel");
		    }
		}
	}
	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidacionDeReportesMensual";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que el sistema genere correctamente el reporte mensual";
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