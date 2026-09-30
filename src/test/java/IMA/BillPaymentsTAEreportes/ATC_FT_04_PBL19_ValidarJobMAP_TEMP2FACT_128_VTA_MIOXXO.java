package IMA.BillPaymentsTAEreportes;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_04_PBL19_ValidarJobMAP_TEMP2FACT_128_VTA_MIOXXO extends BaseExecution{

	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	IMATools ima = new IMATools(u,testCase);  
	@Test(dataProvider = "data-provider")
	public void ATC_FT_04_PBL19_ValidarJobMAP_TEMP2FACT_128_VTA_MIOXXO_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI,GlobalVariables.DB_PASSWORD_BI);
		
		/*************************************** VARIABLES ***************************************/	
		String Query= "select count(*) from BIDMMKT.TEMP_128_VENTA_MIOXXO";
		String Query1 = "select count(*) from BIDMMKT.FACT_128_VENTA_MIOXXO";
		
		/*************************************** PASO 1 ***************************************/	
		
		addStep("Validar el numero de registros en la tabla BIDMMKT.TEMP_128_VENTA_MIOXXO");
        
		System.out.println(GlobalVariables.DB_HOST_BI); System.out.println("\n"+ Query);
				  
		 SQLResult ExecuteQuery = executeQuery(db, Query);
				  
		 boolean StatusQuery = ExecuteQuery.isEmpty();
		 System.out.println(StatusQuery); 
		  if (!StatusQuery) {
		  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
		  } else { 
		   testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIDMMKT.TEMP_128_VENTA_MIOXXO."); 
		   testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
			}
		  assertFalse(StatusQuery,"No se muestran registros en la tabla BIDMMKT.TEMP_128_VENTA_MIOXXO.");
		  
	   /*************************************** PASO 2 ***************************************/	  
			
			  addStep("Acceder a Control M ");
			  
			  Control_mInicio cm = new Control_mInicio(u,
			  data.get("user"),GlobalVariables.ControlM_TD); u.get(data.get("server"));
			  u.hardWait(40); 
			  u.waitForLoadPage(); 
			  cm.logOn();
			 
		/*************************************** PASO 3 ***************************************/	
			
			  addStep("Correr el Job MAP_TEMP2FACT_128_VTA_MIOXXO");
			  
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
			 
		/*************************************** PASO 4 ***************************************/
				  
			addStep("Verificar que la BIDMMKT.FACT_128_VENTA_MIOXXO contenga el mismo numero de registros obtenido en el paso 1");
		          
			System.out.println(GlobalVariables.DB_HOST_BI); System.out.println("\n"+ Query1);
					  
			 SQLResult ExecuteQuery1 = executeQuery(db, Query1);
					  
			 boolean StatusQuery1 = ExecuteQuery1.isEmpty();
			 System.out.println(StatusQuery1); 
			  if (!StatusQuery1) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery1); 
			  } else { 
			   testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIDMMKT.FACT_128_VENTA_MIOXXO."); 
			   testCase.addQueryEvidenceCurrentStep(ExecuteQuery1); 
				}
			  assertFalse(StatusQuery1,"No se muestran registros en la tabla BIDMMKT.FACT_128_VENTA_MIOXXO.");
			  
			  ima.compararCampoTablas(ExecuteQuery, "count(*)", 0, ExecuteQuery1, "count(*)", 0);
			  
			  u.close();		
	}
	
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}
}
