package IMA.CHO52050844;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_Ejecucion_satisfactoria_Job_BI_VALIDA_DASHBOARD_sql extends BaseExecution {

	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_002_Ejecucion_satisfactoria_Job_BI_VALIDA_DASHBOARD_sql_test(HashMap<String, String> data)
			throws Exception {

		/***************************************
		 * PASO 1
		 ***************************************/
		addStep("Acceder al servidor ORACLE- FCDW4QA.FEMCOM.NET");

		/***************************************
		 * PASO 2
		 ***************************************/
		addStep("Consulta tabla SP_QA_DASHBOARD_SEMANAL");

		/***************************************
		 * PASO 3
		 ***************************************/

		JSONObject obj = new JSONObject(data.get("jobs"));

		addStep("Acceder a Control M ");

		Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));
		u.hardWait(40);
		u.waitForLoadPage();
		cm.logOn();

		/***************************************
		 * PASO 4
		 ***************************************/

		addStep("Ejecuci�n del job ");

		ControlM job = new ControlM(u, testCase, obj);
		boolean flag = job.searchJob();
		assertTrue(flag);

		String resultado = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);
		System.out.println("Valor de output :" + job.getOutput());
		Boolean casoPasado = true;
		if (resultado.equals("Failure")) {
			casoPasado = false;
		}

		job.closeViewpoint();
		u.close();
		assertTrue(casoPasado);

		/***************************************
		 * PASO 5
		 ***************************************/
		addStep("Acceder al servidor ORACLE- FCDW4QA.FEMCOM.NET");

		/***************************************
		 * PASO 6
		 ***************************************/
		addStep("Consulta a tabla bidmmkt.bitacora_fuentes_dashboard");
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_Ejecucion_satisfactoria_Job_BI_VALIDA_DASHBOARD_sql_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Ejecuci�n de sentencias y job BI_VALIDA_DASHBOARD.sql";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Team IMA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Prerequisitos";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public void baseAfterTest() {
		u.close();
	}
}