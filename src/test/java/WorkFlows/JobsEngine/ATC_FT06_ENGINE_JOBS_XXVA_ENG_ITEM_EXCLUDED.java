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

public class ATC_FT06_ENGINE_JOBS_XXVA_ENG_ITEM_EXCLUDED extends BaseExecution {
	private SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT06_ENGINE_JOBS_XXVA_ENG_ITEM_EXCLUDE_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Workflows Library: Jobs Engine");

		addStep("Acceder a Control M ");
		String usuarioControlM = GlobalVariables.CtrlM_User_AndreaC;
		String passControlM = GlobalVariables.CtrlM_Pss_AndreaC;

		u = new SeleniumUtil(new MEdgeTest(), true);
		u.getDriver().manage().deleteAllCookies();
		u.get(GlobalVariables.CtrlM_URL);
		u.getDriver().manage().window().maximize();

		Control_mInicio cm = new Control_mInicio(u, usuarioControlM, passControlM);
		u.hardWait(40);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "inicio1");
		cm.logOn();
		testCase.addScreenShotCurrentStep(u, "inicio2");

		addStep("Ejecución del job XXVAENGITEMEXCLUDED");
		JSONObject obj = new JSONObject(data.get("Job"));
		ControlM job = new ControlM(u, testCase, obj);
		u.hardWait(4);
		boolean flag = job.searchJob();
		assertTrue(flag);

		String resultado = job.executeJob();
		u.hardWait(120);
		/*
		 * By statusJob = By.cssSelector("div.ag-cell[col-id=\"JobStatus\"] > span");
		 * String statusJ = u.getDriver().findElement(statusJob).getText();
		 * System.out.println("-> El estatus Actual es: " + statusJ);
		 */
		addStep("Resultado de la ejecución: " + resultado);
		System.out.println("Resultado de la ejecucion -> " + resultado);
		testCase.addBoldTextEvidenceCurrentStep("Output: ");
		System.out.println("Valor de output :" + job.getOutput());
		testCase.addTextEvidenceCurrentStep(job.getOutput());
		u.hardWait(2);
		assertEquals(resultado, "Ended OK");

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
		return "Contar con Acceso a Control -M \nSolicitar el Holdeo del Job: xxvaengitemexcluded Folder: TQA_SIV_PROCESOS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar la correcta Ejecucion del JOB XXVAENGITEMEXCLUDED y que el tiempo de ejecucion nuevo, no supere el tiempo Actual";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT06_ENGINE_JOBS_XXVA_ENG_ITEM_EXCLUDE_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
