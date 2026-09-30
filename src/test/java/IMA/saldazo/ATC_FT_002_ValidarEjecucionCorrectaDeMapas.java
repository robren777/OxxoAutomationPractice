package IMA.saldazo;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONArray;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.JobManagement;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_ValidarEjecucionCorrectaDeMapas extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarEjecucionCorrectaDeMapas_test(HashMap<String, String> data)
			throws Exception {

		JSONArray array = new JSONArray(data.get("cm_jobs"));
		// testCase.addTextEvidenceCurrentStep("Ejecuci�n Job: " + data.get("cm_jobs"));
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		Control_mInicio cm = new Control_mInicio(u, GlobalVariables.USER_CTRL_M_JH, GlobalVariables.PASSWORD_CTRL_M_JH);
		JobManagement j;
		String resultadoEjecucion;
		
		String server = data.get("cm_server");
		System.out.println("Server " + server);
		// testCase.addTextEvidenceCurrentStep("Login");

		addStep("Login");
		u.get(data.get("cm_server"));
		//u.hardWait(40);
		u.waitForLoadPage();
		cm.logOn();

		// testCase.addTextEvidenceCurrentStep("Inicio de job");
		j = new JobManagement(u, testCase, array);
		resultadoEjecucion = j.jobRunner();

		addStep("Abrir la herramienta de control M para validar que la ejecuci�n del job haya sido exitosa");
		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");
		
		array = new JSONArray(data.get("cm_jobs2"));
		
		// testCase.addTextEvidenceCurrentStep("Inicio de job");
		j = new JobManagement(u, testCase, array);
		resultadoEjecucion = j.jobRunner();

		addStep("Abrir la herramienta de control M para validar que la ejecuci�n del job haya sido exitosa");
		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");
		u.hardWait(3);
		u.close();

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar correcta ejecucion de Job";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_ValidarEjecucionCorrectaDeMapas_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
