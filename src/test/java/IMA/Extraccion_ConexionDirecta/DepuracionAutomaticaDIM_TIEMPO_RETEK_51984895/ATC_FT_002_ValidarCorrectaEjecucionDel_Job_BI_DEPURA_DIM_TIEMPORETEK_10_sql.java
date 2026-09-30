package IMA.Extraccion_ConexionDirecta.DepuracionAutomaticaDIM_TIEMPO_RETEK_51984895;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_ValidarCorrectaEjecucionDel_Job_BI_DEPURA_DIM_TIEMPORETEK_10_sql extends BaseExecution{

	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarCorrectaEjecucionDel_Job_BI_DEPURA_DIM_TIEMPORETEK_10_sql_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** PASO 1 ***************************************/	
		
			addStep("Acceder a Control M ");
		
			Control_mInicio cm = new Control_mInicio(u, data.get("user"),data.get("ps")); 
			u.get(data.get("server"));
			u.hardWait(40);
			u.waitForLoadPage(); 
			cm.logOn();
		 	
		/*************************************** PASO 4 ***************************************/	
			
			addStep("Ejecuci�n del job BI_DEPURA_DIM_TIEMPORETEK_10.sql"); 	
			
			JSONObject obj = new JSONObject(data.get("jobs"));
			ControlM job = new ControlM(u, testCase, obj); 
			boolean flag = job.searchJob(); 
			assertTrue(flag);
			
			String resultado = job.executeJob();
			System.out.println("Resultado de la ejecucion -> " + resultado);
			System.out.println ("Valor de output :" +job.getOutput());
			assertEquals(resultado, "Ended OK");

			u.hardWait(3);
			job.closeViewpoint();
			u.close();
			
	}

	@Override
	public String setTestFullName() {
// TODO Auto-generated method stub
		return "ATC_FT_002_ValidarCorrectaEjecucionDel_Job_BI_DEPURA_DIM_TIEMPORETEK_10_sql_test";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "MTC_FT-001_CP002 - Validar correcta ejecuci�n del Job BI_DEPURA_DIM_TIEMPORETEK_10.sql";
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
