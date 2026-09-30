package IMA.Extraccion_ConexionDirecta.CambioAtipoDeCargaMapeos_MTTO_Hijo_51969952;

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

public class ATC_FT_005_ValidarCorrectaEjecucionDelMapaMAP_ODS2FACT_VERIF_CATenElInsert extends BaseExecution{
	
	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarCorrectaEjecucionDelMapaMAP_ODS2FACT_VERIF_CATenElInsert_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil dbBIMTTO = new SQLUtil(GlobalVariables.DB_HOST_BIMTTOQA, GlobalVariables.DB_USER_BIMTTOQA,GlobalVariables.DB_PASSWORD_BIMTTOQA);
				
		/*************************************** VARIABLES ***************************************/	
		String Query = "SELECT COUNT(*) FROM BIDMMTTO.FACT_VERIF_CAT";
		
		String Query1 = "SELECT * FROM (select tienda_key, tiempo_key, encuesta_id, loaddate, calif_causaraiz_num FROM BIDMMTTO.FACT_VERIF_CAT "
				+ "ORDER BY ENCUESTA_ID DESC) where rownum <=25";
		
		/*************************************** PASO 1 ***************************************/	
		
		  addStep("Validar informacion en la tabla destino BIDMMTTO.FACT_VERIF_CAT");
		  
		  System.out.println(GlobalVariables.DB_HOST_BIMTTOQA); 
		  System.out.println("\n"+ Query);
		  
		  SQLResult ExecuteQuery = executeQuery(dbBIMTTO, Query);
		  
		  boolean StatusQuery = ExecuteQuery.isEmpty();
		  System.out.println(StatusQuery); 
		  if (!StatusQuery) {
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se muestran registros con la consulta en la tabla BIDMMTTO.FACT_VERIF_CAT."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
			}
		  assertFalse(StatusQuery,"No se muestran registros con la consulta en la tabla BIDMMTTO.FACT_VERIF_CAT.");
		 
		  /*******************************************************************************************************************/
			
		  System.out.println("\n" + Query1);
		  
		  SQLResult ExecuteQuery1 = executeQuery(dbBIMTTO, Query1); 
		  boolean StatusQuery1 = ExecuteQuery1.isEmpty(); 
		  System.out.println(StatusQuery1);
		  
		  IMATools ima = new IMATools(u,testCase);
		  if (!StatusQuery1) { 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery1);
			  ima.validarContenidoTablas(ExecuteQuery1, Query1); 
		  } else { 
			  testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIDMMTTO.FACT_VERIF_CAT."); 
			  testCase.addQueryEvidenceCurrentStep(ExecuteQuery1); 
		  }
		  assertFalse(StatusQuery1, "No se muestran registros en la tabla BIDMMTTO.FACT_VERIF_CAT.");
		 
		/*************************************** PASO 2 ***************************************/	
		
		  addStep("Acceder a Control M ");
		  
			Control_mInicio cm = new Control_mInicio(u, data.get("user"),data.get("ps"));
			u.get(data.get("server"));
			u.hardWait(40); 
			u.waitForLoadPage();
			cm.logOn();
			 
		/*************************************** PASO 3 ***************************************/	
			
		  addStep("Ejecucion del job MAP_ODS2FACT_VERIF_CAT");
				  
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
			addStep("Validar se haya insertado informacion en la tabla destino BIDMMTTO.FACT_VERIF_CAT");
			  
			  System.out.println(GlobalVariables.DB_HOST_BIMTTOQA); 
			  System.out.println("\n"+ Query);
			  
			  SQLResult ExecuteQuery2 = executeQuery(dbBIMTTO, Query);
			  
			  boolean StatusQuery2 = ExecuteQuery2.isEmpty();
			  System.out.println(StatusQuery2); 
			  if (!StatusQuery2) {
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
			  
				  System.out.println("Ejecutando metodo para hacer la comparacion de registros"); 
			  	  String a= "count(*)"; 
			  	  ima.compararCampoTablas(ExecuteQuery, a, 0, ExecuteQuery2, a, 0); 
			  } else { 
				  testCase.addTextEvidenceCurrentStep("No se muestran registros con la consulta en la tabla BIDMMTTO.FACT_VERIF_CAT."); 
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
				  System.out.println("No se pudo hacer la comparacion de los registros"); 
				}
			  assertFalse(StatusQuery2, "No se muestran registros con la consulta en la tabla BIDMMTTO.FACT_VERIF_CAT."); 
			  assertTrue(StatusQuery2,"Se encuentran los mismos registros, no fueron modificados despues de la ejecucion del job");
			 
			/*******************************************************************************************************************/
			
			  System.out.println("\n" + Query1);
			  
			  SQLResult ExecuteQuery3 = executeQuery(dbBIMTTO, Query1);
			  boolean StatusQuery3 = ExecuteQuery3.isEmpty(); 
			  System.out.println(StatusQuery3);
			  
			  if (!StatusQuery3) { 
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
				  ima.validarContenidoTablas(ExecuteQuery3, Query1);
				  ima.compararContenidoTablas(ExecuteQuery1, ExecuteQuery3); 
			  } else { 
				  testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIDMMTTO.FACT_VERIF_CAT."); 
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery3); 
				}
			  assertFalse(StatusQuery3,"No se muestran registros en la tabla BIDMMTTO.FACT_VERIF_CAT.");
			 
			/*************************************** PASO 5 ***************************************/	
			addStep("Validar que se cargue la columna CALIF_TOTAL_NUM en la tabla BIDMMTTO.FACT_VERIF_CAT");

			System.out.println(GlobalVariables.DB_HOST_BIMTTOQA);
			System.out.println("\n" + Query1);
			 
			SQLResult ExecuteQuery4 = executeQuery(dbBIMTTO, Query1);
			
			boolean StatusQuery4 = ExecuteQuery4.isEmpty();
			System.out.println(StatusQuery4);
			if (!StatusQuery4) {
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
				String columna[] = {"CALIF_TOTAL_NUM"};
				ima.validarColumnasSQL(ExecuteQuery4, Query1, columna);
			} else {			
				testCase.addTextEvidenceCurrentStep("No se muestran registros con la consulta en la tabla BIDMMTTO.FACT_VERIF_CAT.");
				testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
			}			
			assertFalse(StatusQuery4, "No se muestran registros con la consulta en la tabla BIDMMTTO.FACT_VERIF_CAT.");		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
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
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
