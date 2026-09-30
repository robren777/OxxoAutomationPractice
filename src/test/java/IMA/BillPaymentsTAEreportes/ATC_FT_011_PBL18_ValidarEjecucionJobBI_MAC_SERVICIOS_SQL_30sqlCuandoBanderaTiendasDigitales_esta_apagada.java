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

public class ATC_FT_011_PBL18_ValidarEjecucionJobBI_MAC_SERVICIOS_SQL_30sqlCuandoBanderaTiendasDigitales_esta_apagada extends BaseExecution{

	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	IMATools ima = new IMATools(u,testCase);  
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_PBL18_ValidarEjecucionJobBI_MAC_SERVICIOS_SQL_30sqlCuandoBanderaTiendasDigitales_esta_apagada_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI,GlobalVariables.DB_PASSWORD_BI);
		SQLUtil db1 = new SQLUtil(GlobalVariables.DB_HOST_IMA, GlobalVariables.DB_USER_IMA,GlobalVariables.DB_PASSWORD_IMA);
		
		/*************************************** VARIABLES ***************************************/	
		String Query = "select tienda_key,zona_id,plaza_id,distrito_id,ciudad_id,asesor_id from DWADMIN.DIM_TIENDA where TIENDA_DIGITAL = 1";
		String Query1 ="select cr_plaza,item,id_ticket,id_servicio from mioxxo_zd.file_bill_payments";
		String Query2= "select * from ( select tienda_key,item,id_origen,id_ticket,id_servicio from "
				+ "BIQUERYMKT.VM_BILLPAYMENT) where rownum <=25";
		String Query3="select trans_id,plaza,tienda,servicio from BIQUERYMKT.TEMP_PAGO_SERVICIOS";
		
		/*************************************** PASO 1 ***************************************/	
		 
		addStep("Validar que la tabla DWADMIN.DIM_TIENDA se encuentre vacia.");
         
		 System.out.println(GlobalVariables.DB_HOST_BI); System.out.println("\n"+ Query);
		  
		  SQLResult ExecuteQuery = executeQuery(db, Query);
		  
		  boolean StatusQuery = ExecuteQuery.isEmpty();
		  System.out.println(StatusQuery); 
		  if (StatusQuery) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
			  testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla DWADMIN.DIM_TIENDA."); 
		  } else { 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
			}
		  assertTrue(StatusQuery,"Se muestran registros en la tabla DWADMIN.DIM_TIENDA.");
	
		/*************************************** PASO 2 ***************************************/
		  
		  addStep("Validar que la tabla mioxxo_zd.file_bill_payments se encuentre vacia.");
	         
			 System.out.println(GlobalVariables.DB_HOST_IMA); System.out.println("\n"+ Query1);
			  
			  SQLResult ExecuteQuery1 = executeQuery(db1, Query1);
			  
			  boolean StatusQuery1 = ExecuteQuery1.isEmpty();
			  System.out.println(StatusQuery1); 
			  if (StatusQuery1) {
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery1); 
				  testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla mioxxo_zd.file_bill_payments."); 
			  } else { 
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery1); 
				}
			  assertTrue(StatusQuery1,"Se muestran registros en la tabla mioxxo_zd.file_bill_payments.");
		
		/*************************************** PASO 3 ***************************************/
			  
		       addStep("Acceder a Control M ");
			  
				Control_mInicio cm = new Control_mInicio(u, data.get("user"),GlobalVariables.ControlM_TD);
				u.get(data.get("server"));
				u.hardWait(40); 
				u.waitForLoadPage();
				cm.logOn();
				 
		/*************************************** PASO 4 ***************************************/	
	
				  addStep("Correr el Job BI_MAC_SERVICIOS_SQL_30.sql y BI_MKT_SERVICIOS_32.sh");
				  
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
				 
		/*************************************** PASO 5 ***************************************/
				  
			 addStep("Validar que la tabla BIQUERYMKT.VM_BILLPAYMENT se encuentre vacia.");
			         
			 System.out.println(GlobalVariables.DB_HOST_BI); System.out.println("\n"+ Query2);
					 
			 SQLResult ExecuteQuery2 = executeQuery(db1, Query2);
					  
			  boolean StatusQuery2 = ExecuteQuery2.isEmpty();
			  System.out.println(StatusQuery2); 
				  if (StatusQuery2) {
					  testCase.addQueryEvidenceCurrentStep(ExecuteQuery2); 
					  testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIQUERYMKT.VM_BILLPAYMENT."); 
				  } else { 
					  testCase.addQueryEvidenceCurrentStep(ExecuteQuery2); 
				  }
		  assertTrue(StatusQuery2,"Se muestran registros en la tabla BIQUERYMKT.VM_BILLPAYMENT.");
		  
		  /*************************************** PASO 6 ***************************************/
		  
			addStep("Consultar la tabla BIQUERYMKT.TEMP_PAGO_SERVICIOS la cual debera contener registros");
		          
			System.out.println(GlobalVariables.DB_HOST_BI); System.out.println("\n"+ Query3);
					  
			 SQLResult ExecuteQuery3 = executeQuery(db, Query3);
					  
			 boolean StatusQuery3 = ExecuteQuery3.isEmpty();
			 System.out.println(StatusQuery3); 
			  if (!StatusQuery3) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery3); 
			  } else { 
			   testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIQUERYMKT.TEMP_PAGO_SERVICIOS."); 
			   testCase.addQueryEvidenceCurrentStep(ExecuteQuery3); 
				}
			  assertFalse(StatusQuery3,"No se muestran registros en la tabla BIQUERYMKT.TEMP_PAGO_SERVICIOS.");
			  	
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