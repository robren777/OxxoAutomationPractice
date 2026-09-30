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

public class ATC_FT_01_PBL18_QueTablaDM_FACT_SERVICIOS_SEMcontengaCampoID_ZONA_DIGITAL_valor3 extends BaseExecution{

	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	IMATools ima = new IMATools(u,testCase);  
	@Test(dataProvider = "data-provider")
	public void ATC_FT_01_PBL18_QueTablaDM_FACT_SERVICIOS_SEMcontengaCampoID_ZONA_DIGITAL_valor3_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,GlobalVariables.DB_PASSWORD_FCDW4QA);		
		 
		/*************************************** VARIABLES ***************************************/	
		String Query1 = "select * from (select forma_pago,cr_plaza,cr_tienda,folio_transfer,id_original from BIDMMKT.DM_119_FACT_RST where accounting_date BETWEEN to_date('20220801','YYYYMMDD')  "
				+ "AND to_date('20220831','YYYYMMDD')) where rownum <=25";
		String Query2= "select * from BIDMMKT.STATUSCARGADWH where ID_SUJETO BETWEEN 120 AND 121";
		String Query3 = "SELECT * from ( select tienda_key,producto_key,semana_id,id_zona_digital FROM BIDMMKT.dm_fact_servicios_semana WHERE SEMANA_ID BETWEEN 202232 AND 202236 AND  "
				+ "ID_ZONA_DIGITAL =3) where rownum <=25";
		
		/*************************************** PASO 1 ***************************************/	
		
		 addStep("Validar que la tabla BIDMMKT.DM_119_FACT_RST contenga informacion");
          
		 System.out.println(GlobalVariables.DB_HOST_FCDW4QA); System.out.println("\n"+ Query1);
		  
		  SQLResult ExecuteQuery = executeQuery(db, Query1);
		  
		  boolean StatusQuery = ExecuteQuery.isEmpty();
		  System.out.println(StatusQuery); 
		  if (!StatusQuery) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIDMMKT.DM_119_FACT_RST."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
			}
		  assertFalse(StatusQuery,"No se muestran registros en la tabla BIDMMKT.DM_119_FACT_RST.");
	
		/*************************************** PASO 2 ***************************************/
		  
		  addStep("Validar la fecha de la tabla BIDMMKT.STATUSCARGADWH");
          
			 System.out.println(GlobalVariables.DB_HOST_FCDW4QA); System.out.println("\n"+ Query2);
			  
			  SQLResult ExecuteQuery1 = executeQuery(db, Query2);
			  
			  boolean StatusQuery1 = ExecuteQuery1.isEmpty();
			  System.out.println(StatusQuery1); 
			  if (!StatusQuery1) {
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery1); 
			  } else { 
				  testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIDMMKT.STATUSCARGADWH."); 
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery1); 
				}
			  assertFalse(StatusQuery1,"No se muestran registros en la tabla BIDMMKT.STATUSCARGADWH.");
			  
	    /*************************************** PASO 3 ***************************************/	
			  
		 addStep("Consultar que dentro de la tabla BIDMMKT.dm_fact_servicios_semana no se muestre informacion");
		 

		 System.out.println(GlobalVariables.DB_HOST_FCDW4QA); System.out.println("\n"+ Query3);
		  
		  SQLResult ExecuteQuery2 = executeQuery(db, Query3);
		  
		  boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		  System.out.println(StatusQuery2); 
		  if (StatusQuery2) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery2); 
			  testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIDMMKT.dm_fact_servicios_semana."); 
		  } else { 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery2); 
			}
		  assertTrue(StatusQuery2,"Se muestran registros en la tabla BIDMMKT.dm_fact_servicios_semana.");
		
		 /*************************************** PASO 4 ***************************************/	
			 addStep("Acceder a Control M ");
			  
				Control_mInicio cm = new Control_mInicio(u, data.get("user"),GlobalVariables.ControlM_TD);
				u.get(data.get("server"));
				u.hardWait(40); 
				u.waitForLoadPage();
				cm.logOn();
				 
		/*************************************** PASO 5 ***************************************/	
				
				 addStep("Correr el Job Semana BI_MAP_FACT2FACT_SERV_RST_SEM_050_QA");
					  
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
		
		/*************************************** PASO 6 ***************************************/
				  
			addStep("Validar que despues de correr el job de semana se presente informacion con ID_ZONA_DIGITAL = 3 "
					+ "en la tabla BIDMMKT.dm_fact_servicios_semana");
		          
			System.out.println(GlobalVariables.DB_HOST_FCDW4QA); System.out.println("\n"+ Query3);
					  
			 SQLResult ExecuteQuery3 = executeQuery(db, Query3);
					  
			 boolean StatusQuery3 = ExecuteQuery3.isEmpty();
			 System.out.println(StatusQuery3); 
			  if (!StatusQuery3) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery3); 
			  } else { 
			   testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIDMMKT.dm_fact_servicios_semana."); 
			   testCase.addQueryEvidenceCurrentStep(ExecuteQuery3); 
				}
			  assertFalse(StatusQuery3,"No se muestran registros en la tabla BIDMMKT.dm_fact_servicios_semana.");
			  
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
