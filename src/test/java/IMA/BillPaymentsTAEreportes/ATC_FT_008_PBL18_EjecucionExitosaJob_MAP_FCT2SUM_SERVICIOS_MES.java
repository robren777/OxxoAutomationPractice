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

public class ATC_FT_008_PBL18_EjecucionExitosaJob_MAP_FCT2SUM_SERVICIOS_MES extends BaseExecution{

	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	IMATools ima = new IMATools(u,testCase);  
	@Test(dataProvider = "data-provider")
	public void ATC_FT_008_PBL18_EjecucionExitosaJob_MAP_FCT2SUM_SERVICIOS_MES_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI,GlobalVariables.DB_PASSWORD_BI);
		
		/*************************************** VARIABLES ***************************************/	
		String Query = "SELECT * from ( select tienda_key, producto_key,mes_id,servicio_amt,unidadesservicio_num,id_zona_digital "
				+ "from BIDMMKT.DM_FACT_SERVICIOS_MES) where rownum <=25";
		
		/*************************************** PASO 1 ***************************************/	
		
		        addStep("Acceder a Control M ");
			  
				Control_mInicio cm = new Control_mInicio(u, data.get("user"),GlobalVariables.ControlM_TD);
				u.get(data.get("server"));
				u.hardWait(40); 
				u.waitForLoadPage();
				cm.logOn();
				 
		/*************************************** PASO 2 ***************************************/	
	
				  addStep("Correr el Job MAP_FCT2SUM_SERVICIOS_MES");
				  
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
				 
		/*************************************** PASO 3 ***************************************/
				  
			addStep("Realizar la consulta a la tabla BIDMMKT.DM_FACT_SERVICIOS_MES");
		          
			System.out.println(GlobalVariables.DB_HOST_BI); System.out.println("\n"+ Query);
					  
			 SQLResult ExecuteQuery = executeQuery(db, Query);
					  
			 boolean StatusQuery = ExecuteQuery.isEmpty();
			 System.out.println(StatusQuery); 
			  if (!StatusQuery) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
			  } else { 
			   testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIDMMKT.DM_FACT_SERVICIOS_MES."); 
			   testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
				}
			  assertFalse(StatusQuery,"No se muestran registros en la tabla BIDMMKT.DM_FACT_SERVICIOS_MES.");
			  
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