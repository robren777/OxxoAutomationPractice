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

public class ATC_FT_001_PBL18_ValidarDatos_enTabla_MIOXXO_ZD_FILE_BILL_PAYMENTS extends BaseExecution{
	
	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	IMATools ima = new IMATools(u,testCase);  
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_PBL18_ValidarDatos_enTabla_MIOXXO_ZD_FILE_BILL_PAYMENTS_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA,GlobalVariables.DB_PASSWORD_IMA);
		
		/*************************************** VARIABLES ***************************************/	
		String Query = "SELECT * from ( SELECT cr_plaza,cr_tienda,folio_transaccion,item,id_origen "
				+ "FROM mioxxo_zd.file_bill_payments) where rownum <=25";
		
		/*************************************** PASO 1 ***************************************/	
		
		 addStep("Validar que la tabla mioxxo_zd.file_bill_payments se encuentre vacia.");
          
		 System.out.println(GlobalVariables.DB_HOST_FCDW4QA); System.out.println("\n"+ Query);
		  
		  SQLResult ExecuteQuery = executeQuery(db, Query);
		  
		  boolean StatusQuery = ExecuteQuery.isEmpty();
		  System.out.println(StatusQuery); 
		  if (StatusQuery) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
			  testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla mioxxo_zd.file_bill_payments."); 
		  } else { 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
			}
		  assertTrue(StatusQuery,"Se muestran registros en la tabla mioxxo_zd.file_bill_payments.");
	
		/*************************************** PASO 2 ***************************************/
		  
			 addStep("Acceder a Control M ");
			  
				Control_mInicio cm = new Control_mInicio(u, data.get("user"),GlobalVariables.ControlM_TD);
				u.get(data.get("server"));
				u.hardWait(40); 
				u.waitForLoadPage();
				cm.logOn();
				 
		/*************************************** PASO 3 ***************************************/	
				
				 addStep("Correr el Job EB02_MX");
					  
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
				  
			addStep("Validar que despues de correr el job se presente informacion "
					+ "en la tabla mioxxo_zd.file_bill_payments");
		          
			System.out.println(GlobalVariables.DB_HOST_FCDW4QA); System.out.println("\n"+ Query);
					  
			 SQLResult ExecuteQuery3 = executeQuery(db, Query);
					  
			 boolean StatusQuery3 = ExecuteQuery3.isEmpty();
			 System.out.println(StatusQuery3); 
			  if (!StatusQuery3) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery3); 
			  } else { 
			   testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla mioxxo_zd.file_bill_payments."); 
			   testCase.addQueryEvidenceCurrentStep(ExecuteQuery3); 
				}
			  assertFalse(StatusQuery3,"No se muestran registros en la tabla mioxxo_zd.file_bill_payments.");
			  
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
