package WorkFlows.JobsEngine;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT03_ENGINE_JOBS_XXVA_ENG_PLANOGRAMAMON extends BaseExecution {
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT03_ENGINE_JOBS_XXVA_ENG_PLANOGRAMAMON_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Workflows Library: Jobs Engine");

		addStep("Acceder a Control M ");
		String usuarioControlM = GlobalVariables.CtrlM_User_EsmeG;
		String passControlM = GlobalVariables.CtrlM_Pss_EsmeG;

		//u = new SeleniumUtil(new ChromeTest(), true);
		u = new SeleniumUtil (new MEdgeTest(), true);
		u.getDriver().manage().deleteAllCookies();
		u.get(GlobalVariables.CtrlM_URL);
		u.getDriver().manage().window().maximize();

		u.hardWait(5);
		Control_mInicio cm = new Control_mInicio(u, usuarioControlM, passControlM);
		u.hardWait(40);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "inicio1");
		cm.logOn();
		u.hardWait(10);
		testCase.addScreenShotCurrentStep(u, "inicio2");

		addStep("Ejecución del job XXVAENGPLANOGRAMAMON");
		JSONObject obj = new JSONObject(data.get("Job"));
		ControlM job = new ControlM(u, testCase, obj);
		u.hardWait(4);
		boolean flag = job.searchJob();
		assertTrue(flag);

		String resultado = job.executeJob();
		u.hardWait(600);
		/*
		 * By statusJob = By.cssSelector("div.ag-cell[col-id=\"JobStatus\"] > span");
		 * String statusJ = u.getDriver().findElement(statusJob).getText();
		 * System.out.println("--> El estatus Actual es: " + statusJ);
		 */
		addStep("Resultado de la ejecución: " + resultado);
		System.out.println("Resultado de la ejecucion -> " + resultado);
		testCase.addBoldTextEvidenceCurrentStep("Output: ");
		System.out.println("Valor de output :" + job.getOutput());
		testCase.addTextEvidenceCurrentStep(job.getOutput());
		if (job.getOutput().toLowerCase().contains("error")) {
			throw new AssertionError("Existe un error en el Output");
		}
		assertEquals(resultado, "Wait Condition");

		u.hardWait(3);
		job.closeViewpoint();
		u.close();
	}

	@AfterMethod
	public void afterMethod() {
		// u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con Acceso a Control -M \nSolicitar el Holdeo del Job: xxvaengplanogramamon Folder: TQA_SIV_PLANOG_DIARIA";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la correcta Ejecucion del JOB XXVAENGPLANOGRAMAMON y que el tiempo de ejecucion nuevo, no supere el tiempo Actual";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT03_ENGINE_JOBS_XXVA_ENG_PLANOGRAMAMON_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
