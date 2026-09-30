package RMS.test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.JobManagement;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_003_Prueba extends BaseExecution {

	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_Prueba_test(HashMap<String, String> data)
			throws Exception {

		/***************************************
		 * PASO 1
		 ***************************************/

		addStep("Acceder a Control M ");

		Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));
		
		u.waitForLoadPage();
		u.hardWait(80);
		u.waitForLoadPage();
		cm.logOn();

		/***************************************
		 * PASO 2
		 * 
		 */
		addStep("darle Release a la cadena");
		  
		JSONObject obj1 = new JSONObject(data.get("job1")); 
		ControlM job1 = new ControlM(u, testCase, obj1); 
		u.hardWait(5);
		boolean flag1 = job1.searchFolder(); 
		assertTrue(flag1);
				  
		job1.accionSinOutput();
				  
		u.hardWait(3); 
		job1.closeViewpoint(); 
		 u.hardWait(15);
		
		 
		 /***************************************/

		addStep("Ejecucion del grupo de jobs AIX_PRECIOS ");

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
		return "ATC_FT_003_Prueba_test";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "ATC_FT_003_Prueba_test";
	}

	@Override
	public String setTestDesigner() {
// TODO Auto-generated method stub
		return "Tania";
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