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

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3.ExcelReader;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

/**
 * 
 * Descripcion:
 *Verificar que el sistema genere correctamente el reporte trimestral
 * 
 * Nombre casos de prueba manual que se abarcan:
 * ATC_FT_002_ValidacionDeReportesTrimestral
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de Snowflake.
 * Insumos:
 * -N/A
 * @author Angel Gabriel Ramos Martinez
 * @date 18/08/2025
 * 
 * 
 */
 
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210855
		) 

public class ATC_FT_002_ValidacionDeReportesTrimestral extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_002_ValidacionDeReportesTrimestral_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Automatización Reportes DataLake MVP3");
		/***************************** Variables Data Provider **************************************/
		String tableName = data.get("tableName");
		String owner = data.get("owner");
		String type = data.get("type");
		String run = data.get("run");
		String typeB = data.get("typeB");
		String testName = data.get("testName");

		/***************************** Lectura Excel Trimestral **************************************************/
		addStep("Lectura de archivos Excel del trimestre con la metadata esperada.");
		File folder = Paths.get("Config", "Input", "DataProviders", "Reportes DataLake_Reportes", "ReportesVentas").toFile();
		File[] files = folder.listFiles((dir, name) ->
		    name.endsWith(".xlsx")
		    && (name.contains("Ventas_ieps_042025") 
		        || name.contains("Ventas_ieps_052025")
		        || name.contains("Ventas_ieps_062025"))
		);

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

		/***************************** Ejecución del SP Trimestral ***********************************************/
		addStep("Hacer conexion a la Base de datos de Snowflake.Ejecutar Stored Procedure SP_REPORTE_VENTAS Trimestral.");
		System.out.println("Ejecutando: " + testName);

		// En este caso configurarás periodoInicial y periodoFinal para el trimestre completo
		String periodoInicial = "202504";
		String periodoFinal = "202506";
		List<Map<String, Object>> resultadoSP = new ArrayList<>();

		try {
		    resultadoSP = UtilsReportesDatalakeMVP3.SPExecutorV.ejecutarSPReporteVentas(periodoInicial, periodoFinal);
		    assertFalse(resultadoSP.isEmpty(), "El resultado del SP está vacío.");
		    testCase.addTextEvidenceCurrentStep("Conexion snowflake exitosa, Stored Procedure ejecutado correctamente con datos para el periodo " + periodoInicial + "‑" + periodoFinal);
		} catch (SQLException e) {
		    e.printStackTrace();
		    fail("Error al ejecutar el Stored Procedure o al consultar los resultados.");
		}

		/***************************** Validar impuestos esperados ************************************/
		addStep("Validar existencia de los impuestos IEPS esperados en los resultados del SP.");

		List<String> impuestosEsperados = Arrays.asList("IE1653", "IE1630", "IE1625", "IE0008", "IE1603");

		for (String impuesto : impuestosEsperados) {
		    boolean encontrado = resultadoSP.stream()
		        .anyMatch(row -> {
		            Object imp = row.get("CVE_IEPS");
		            return imp != null && impuesto.equals(imp.toString().trim());
		        });

		    if (encontrado) {
		        testCase.addTextEvidenceCurrentStep("Se encontró el impuesto " + impuesto + " en los resultados del SP.");
		    } else {
		        testCase.addTextEvidenceCurrentStep("No se encontró el impuesto " + impuesto + " en los resultados del SP.");
		        System.out.println("Advertencia: No se encontró el impuesto " + impuesto);
		    }
		}

		/***************************** Validar periodo esperado ********************************************/
		addStep("Validar que el reporte contenga información para el periodo esperado.");
		boolean contienePeriodo = resultadoSP.stream()
		    .anyMatch(row -> {
		        Object mes = row.get("MES");
		        if (mes == null) return false;
		        String mesStr = mes.toString().replace(".0", "").trim();
		        // verificar que mesStr esté dentro del rango 202504 a 202506
		        try {
		            int mesInt = Integer.parseInt(mesStr);
		            int inicio = Integer.parseInt(periodoInicial);
		            int fin = Integer.parseInt(periodoFinal);
		            return (mesInt >= inicio && mesInt <= fin);
		        } catch (NumberFormatException ex) {
		            return false;
		        }
		    });

		assertTrue(contienePeriodo, "El reporte no contiene información para el periodo " + periodoInicial + "-" + periodoFinal);
		testCase.addTextEvidenceCurrentStep("El reporte contiene información para el periodo " + periodoInicial + "-" + periodoFinal + ".");

		/************ Paso: Validar existencia y cantidad de registros por impuesto ****************************/
		addStep("Validar existencia y cantidad de registros por impuesto para el periodo " + periodoInicial + "‑" + periodoFinal);

		Map<String, Integer> registrosExcel = new HashMap<>();
		Map<String, Integer> registrosSP = new HashMap<>();

		// Contar registros por impuesto desde Excel (todos los meses del trimestre)
		for (Map<String, String> row : allRows) {
		    String mesExcel = row.get("MES");
		    if (mesExcel != null) {
		        String mesTrim = mesExcel.trim();
		        try {
		            int mesInt = Integer.parseInt(mesTrim);
		            int inicio = Integer.parseInt(periodoInicial);
		            int fin = Integer.parseInt(periodoFinal);
		            if (mesInt >= inicio && mesInt <= fin) {
		                String impuesto = null;
		                // Normalización / trim de columna clave
		                for (Map.Entry<String, String> entry : row.entrySet()) {
		                    String key = entry.getKey() != null ? entry.getKey().trim() : "";
		                    if ("CVE_IEPS".equalsIgnoreCase(key)) {
		                        impuesto = entry.getValue() != null ? entry.getValue().trim() : null;
		                        break;
		                    }
		                }
		                if (impuesto != null && !impuesto.isEmpty()) {
		                    registrosExcel.merge(impuesto, 1, Integer::sum);
		                }
		            }
		        } catch (NumberFormatException ex) {
		            // no es un mes válido, ignorar
		        }
		    }
		}

		// Contar registros por impuesto desde SP (meses dentro del trimestre)
		for (Map<String, Object> row : resultadoSP) {
		    Object mes = row.get("MES");
		    String mesStr = mes != null ? mes.toString().replace(".0", "").trim() : "";
		    try {
		        int mesInt = Integer.parseInt(mesStr);
		        int inicio = Integer.parseInt(periodoInicial);
		        int fin = Integer.parseInt(periodoFinal);
		        if (mesInt >= inicio && mesInt <= fin) {
		            String impuesto = row.get("CVE_IEPS") != null ? row.get("CVE_IEPS").toString().trim() : null;
		            if (impuesto != null && !impuesto.isEmpty()) {
		                registrosSP.merge(impuesto, 1, Integer::sum);
		            }
		        }
		    } catch (NumberFormatException ex) {
		        // mes no numérico, ignorar
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
		return "ATC_FT_002_ValidacionDeReportesTrimestral";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que el sistema genere correctamente el reporte trimestral.";
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