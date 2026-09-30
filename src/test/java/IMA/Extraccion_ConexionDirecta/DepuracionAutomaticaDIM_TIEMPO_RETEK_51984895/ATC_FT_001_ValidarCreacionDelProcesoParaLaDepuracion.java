package IMA.Extraccion_ConexionDirecta.DepuracionAutomaticaDIM_TIEMPO_RETEK_51984895;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_ValidarCreacionDelProcesoParaLaDepuracion extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarCreacionDelProcesoParaLaDepuracion_test(HashMap<String, String> data) throws Exception {
		/*************************************** BASE DATOS***************************************/
		SQLUtil dbBI = new SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI,GlobalVariables.DB_PASSWORD_BI);
				
		/*************************************** VARIABLES ***************************************/
		String QuerySelecProc = "select object_name,object_id, object_type,status,created,timestamp from user_objects" + 
				" where object_type='PROCEDURE' and object_name='PKG_DEPURA_DIM_TIEMPORETEK'";

		/*************************************** PASO 1 ***************************************/
		addStep("Seleccionar el procedimiento PKG_DEPURA_DIM_TIEMPORETEK.");

		System.out.println(GlobalVariables.DB_HOST_BI);
		System.out.println("\n" + QuerySelecProc);
		
		SQLResult ExecuteQuery = executeQuery(dbBI, QuerySelecProc);
		
		boolean StatusQuery = ExecuteQuery.isEmpty();
		System.out.println(StatusQuery);
		if (!StatusQuery) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		} else {			
			testCase.addTextEvidenceCurrentStep("El procedimiento PKG_DEPURA_DIM_TIEMPORETEK no se encuentra en la base de datos.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		}			
		assertFalse(StatusQuery, "El procedimiento PKG_DEPURA_DIM_TIEMPORETEK no se encuentra en la base de datos.");
		
		/*************************************** PASO 2 ***************************************/
		JSONObject obj = new JSONObject(data.get("jobs"));
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		addStep("Acceder a Control M ");
	
		Control_mInicio cm = new Control_mInicio(u, data.get("user"),
		data.get("ps")); 
		u.get(data.get("server"));
		u.hardWait(40);
		u.waitForLoadPage(); 
		cm.logOn();
	 	
		/*************************************** PASO 3 ***************************************/	
		addStep("Verificar se encuentre grupo TQABI_DEPURA_DIM_TIEMPORETEK con el job BI_DEPURA_DIM_TIEMPORETEK_10.sql."); 
		
		ControlM job = new ControlM(u, testCase, obj);
		boolean flag = job.searchJob(); 
		assertTrue(flag);
	
		job.closeViewpoint();
		u.close();
	}
	
	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_ValidarCreacionDelProcesoParaLaDepuracion_test";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "MTC_FT-001_CP001 - Validar la creaci�n del proceso para la depuraci�n";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
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
}