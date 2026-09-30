package IMA.Extraccion_ConexionDirecta.LongitudDeCampoLlaveroTAE_51972916;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONArray;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.controlm.JobManagement;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004ValidarEjecucionJOBSgrupoTQABI_MDMD_LLAVEROTAE extends BaseExecution {

	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004ValidarEjecucionJOBSgrupoTQABI_MDMD_LLAVEROTAE_test(HashMap<String, String> data)
			throws Exception {

		/***************************************
		 * PASO 1
		 ***************************************/

		addStep("Acceder a Control M ");

		Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));
		u.hardWait(40);
		u.waitForLoadPage();
		cm.logOn();

		/***************************************
		 * PASO 4
		 ***************************************/

		addStep("Ejecucion del grupo de jobs TQABI_MDMD_LLAVEROTAE ");

		JSONArray array = new JSONArray(data.get("jobs"));

		JobManagement j = new JobManagement(u, testCase, array);
		String resultadoEjecucion = j.jobRunner();
		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");
		u.hardWait(3);
		u.close();
	}

	@Override
	public String setTestFullName() {
// TODO Auto-generated method stub
		return "ATC_FT_004ValidarEjecucionJOBSgrupoTQABI_MDMD_LLAVEROTAE_test";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "CP004 - Validar ejecuci�n de JOBS del grupo TQABI_MDMD_LLAVEROTAE";
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
