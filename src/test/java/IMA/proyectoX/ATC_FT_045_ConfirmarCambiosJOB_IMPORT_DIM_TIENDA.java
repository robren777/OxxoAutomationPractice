package IMA.proyectoX;

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

public class ATC_FT_045_ConfirmarCambiosJOB_IMPORT_DIM_TIENDA extends BaseExecution{

	SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
	IMATools ima = new IMATools(u,testCase);   
	@Test(dataProvider = "data-provider")
	public void ATC_FT_045_ConfirmarCambiosJOB_IMPORT_DIM_TIENDA_test(HashMap<String, String> data) throws Exception {
		/*************************************** BASE DATOS***************************************/
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCDW4QA, GlobalVariables.DB_USER_FCDW4QA,GlobalVariables.DB_PASSWORD_FCDW4QA);
				
		/*************************************** VARIABLES ***************************************/	
		String Query = "select Tienda_key, ID_POTENCIAL_COMIDA, POTENCIAL_COMIDA_DESC,"
				+ " ID_OVERLAY_COMIDA, OVERLAY_COMIDA_DESC from DWADMIN.TEMP_121_SEGMENTO_TDA";
		
		/*************************************** PASO 1 ***************************************/	
		
		 addStep("Acceder a Control M ");
		  
			Control_mInicio cm = new Control_mInicio(u, data.get("user"),GlobalVariables.ControlM_TD);
			u.get(data.get("server"));
			u.hardWait(40); 
			u.waitForLoadPage();
			cm.logOn();
			 
		/*************************************** PASO 2 ***************************************/	
			
		  addStep("Ejecucion del job BI_MAP_121_TEMP2DIM_TIENDA_090");
				  
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
			
			 addStep("Validar la conexion a la base de datos FCDW4QA y confirma que se muestre las "
			 		+ "columnas correspondientes a segmento de tienda y concepto diferenciador dentro de DIM_TIENDA");
			  
			  System.out.println(GlobalVariables.DB_HOST_FCDW4QA); System.out.println("\n"+ Query);
			  
			  SQLResult ExecuteQuery = executeQuery(db, Query);
			  
			  boolean StatusQuery = ExecuteQuery.isEmpty();
			  System.out.println(StatusQuery); 
			  if (!StatusQuery) {
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
			  } else { 
				  testCase.addTextEvidenceCurrentStep("No se muestran registros con la consulta en la tabla DWADMIN.TEMP_121_SEGMENTO_TDA."); 
				  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); 
				}
			  assertFalse(StatusQuery,"No se muestran registros con la consulta en la tabla DWADMIN.TEMP_121_SEGMENTO_TDA.");	
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
