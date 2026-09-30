package WM.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_004_Demo extends BaseExecution{
SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_004_Demo_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil dbFCDW4QA = new SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,GlobalVariables.DB_PASSWORD_FCDW4QA);
				
		/*************************************** VARIABLES ***************************************/
		String Query = "select object_name, object_id, object_type, status from user_objects where object_type='PROCEDURE' and object_name = 'QA_TEMP_VENTA_PROMO'";
		
		/*************************************** PASO 1 ***************************************/
			addStep("Acceder a Control M ");
			
			  u.get(data.get("server")); 
			  //u.hardWait(600); 
			  Control_mInicio cm = new Control_mInicio(u, data.get("user"),GlobalVariables.ControlM_TD);
			  u.hardWait(180); u.waitForLoadPage();
			  
			  cm.logOn(); 
			  testCase.addScreenShotCurrentStep(u, "Pagina Login");
			 
		/*************************************** PASO 2 ***************************************/	
		  	addStep("Ejecucion del job");
		  
			
			  JSONObject obj = new JSONObject(data.get("jobs")); ControlM job = new
			  ControlM(u, testCase, obj);
			  
			  Screen s = new Screen();
			  
			  Robot r = new Robot(); r.delay(5000);
			  
			  try { s.click(GlobalVariables.pathImage+"cerrar.PNG"); 
			  r.delay(20000); 
			  }catch (FindFailed e) { 
				  System.out.println("Ventana informativa view points"); 
			  }
			  
			  boolean flag = job.searchJob(); assertTrue(flag);
			  
			  String resultado = job.executeJob();
			  System.out.println("Resultado de la ejecucion -> " + resultado);
			  System.out.println ("Valor de output :" +job.getOutput());
			  assertEquals(resultado, "Failure");
			  
			  u.hardWait(3); job.closeViewpoint(); u.close();
			 
		  /*************************************** PASO 3 ***************************************/	
		  addStep("Ejemplo consulta BD, Seleccionar el procedimiento BIETL.QA_TEMP_VENTA_PROMO");
		  
		  System.out.println(GlobalVariables.DB_HOST_FCDW4QA); System.out.println(Query);
		  
		  SQLResult ExecuteQuery = executeQuery(dbFCDW4QA, Query);
		  
		  boolean StatusQuery = ExecuteQuery.isEmpty();
		  System.out.println(StatusQuery); 
		  if (!StatusQuery) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se encuentra el procedimiento BIETL.QA_TEMP_VENTA_PROMO."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
		  }
		  assertFalse(StatusQuery, "No se encuentra el procedimiento BIETL.QA_TEMP_VENTA_PROMO.");
	}

	@Override
	public String setTestFullName() {
// TODO Auto-generated method stub
		return "ATC_FT_004_Demo_test";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "Ejecucion prueba para Demo";
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
