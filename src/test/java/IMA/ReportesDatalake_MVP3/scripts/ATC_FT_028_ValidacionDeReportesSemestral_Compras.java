package IMA.ReportesDatalake_MVP3.scripts;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import org.testng.annotations.Test;

import IMA.ReportesDatalake_MVP3.om.UtilsReportesDatalakeMVP3;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;

/**
 * Descripcion: Verificar que el sistema genere correctamente el reporte semestral.
 * 
 * Nombre casos de prueba manual que se abarcan:
 * ATC_FT_028_ValidacionDeReportesSemestral_Compras 
 * 
 * Prerequisitos:
 * 1.-Acceso a la BD de Snowflake.
 * Insumos:
 * -N/A
 * @author Angel Gabriel Ramos Martinez
 * @date 25/08/2025
 */
@AzureTestInfo(
		areaPath = "I24043-Automatización Reportes DataLake",
		iterationPath = "I24043-Automatización Reportes DataLake\\Sprint 2 MVP3",
		tags = "IMA",
		userStoryId = 210858
		) 

public class ATC_FT_028_ValidacionDeReportesSemestral_Compras extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true, testName="")
	public void ATC_FT_028_ValidacionDeReportesSemestral_Compras_test(HashMap<String, String> data) throws Exception {
		
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
		 * Ejecución del SP Semestral
		 ***********************************************/
		addStep("Ejecutar Stored Procedure SP_REPORTE_Compras para el semestre.");
		System.out.println("Ejecutando: " + testName);

		String periodoInicial = "202501";
		String periodoFinal = "202506";
		List<Map<String, Object>> resultadoSP = new ArrayList<>();

		try {
			resultadoSP = UtilsReportesDatalakeMVP3.SPExecutorC.ejecutarSPReporteCompras(periodoInicial, periodoFinal);
			assertFalse(resultadoSP.isEmpty(), "El resultado del SP está vacío para el semestre.");
			testCase.addTextEvidenceCurrentStep("Stored Procedure ejecutado correctamente con datos para el periodo "
					+ periodoInicial + "‑" + periodoFinal);
		} catch (SQLException e) {
			e.printStackTrace();
			fail("Error al ejecutar el Stored Procedure o al consultar los resultados.");
		}

		/*****************************
		 * Depuración: mostrar meses devueltos por el SP
		 *******************************/
		System.out.println("Meses devueltos por el SP:");
		resultadoSP.stream().map(row -> row.get("MES")).filter(Objects::nonNull).map(Object::toString)
				.map(m -> m.replace(".0", "").trim()).distinct().sorted()
				.forEach(mes -> System.out.println("-> MES encontrado: " + mes));

		/*****************************
		 * Conteo de registros por impuesto por mes
		 *******************************/
		addStep("Contar registros por impuesto 'CVE_IEPS' por mes de SP " + periodoInicial + " a " + periodoFinal);

		Map<String, Map<String, Integer>> conteoPorMes = new TreeMap<>(); // mes -> (impuesto -> count)
		Map<String, Integer> conteoTotal = new HashMap<>();

		for (Map<String, Object> row : resultadoSP) {
			Object mesObj = row.get("MES");
			Object impuestoObj = row.get("CVE_IEPS");

			if (mesObj != null && impuestoObj != null) {
				String mesStr = mesObj.toString().replace(".0", "").trim();
				String impuesto = impuestoObj.toString().trim();

				if (!mesStr.matches("\\d{6}"))
					continue;

				try {
					int mesInt = Integer.parseInt(mesStr);
					int inicio = Integer.parseInt(periodoInicial);
					int fin = Integer.parseInt(periodoFinal);

					if (mesInt >= inicio && mesInt <= fin && !impuesto.isEmpty()) {
						// Conteo mensual
						conteoPorMes.computeIfAbsent(mesStr, k -> new HashMap<>()).merge(impuesto, 1, Integer::sum);

						// Conteo total
						conteoTotal.merge(impuesto, 1, Integer::sum);
					}
				} catch (NumberFormatException ex) {
					System.out.println("Error al interpretar MES: " + mesStr);
				}
			}
		}

		/*****************************
		 * Mostrar evidencia por mes (incluyendo meses vacíos)
		 *******************************/
		List<String> mesesEsperados = Arrays.asList("202501", "202502", "202503", "202504", "202505", "202506");

		for (String mes : mesesEsperados) {
			Map<String, Integer> impuestosMes = conteoPorMes.getOrDefault(mes, new HashMap<>());

			if (impuestosMes.isEmpty()) {
				testCase.addTextEvidenceCurrentStep(mes + " no contiene registros.");
			} else {
				for (Map.Entry<String, Integer> entry : impuestosMes.entrySet()) {
					testCase.addTextEvidenceCurrentStep(
							mes + " fueron Impuesto " + entry.getKey() + ": " + entry.getValue() + " registros");
				}
			}
		}

		/*****************************
		 * Mostrar totales del semestre por impuesto
		 *******************************/
		testCase.addTextEvidenceCurrentStep("TOTAL semestre " + periodoInicial + " a " + periodoFinal + " fueron:");
		for (Map.Entry<String, Integer> entry : conteoTotal.entrySet()) {
			testCase.addTextEvidenceCurrentStep("Impuesto " + entry.getKey() + ": " + entry.getValue() + " registros");
		}
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_028_ValidacionDeReportesSemestral_Compras";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que el sistema genere correctamente el reporte semestral.";
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