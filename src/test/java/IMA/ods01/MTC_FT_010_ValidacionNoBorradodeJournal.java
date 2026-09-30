package IMA.ods01;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_010_ValidacionNoBorradodeJournal extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_ValidacionCleanupdeJournal_(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		
		/** VARIABLES *********************************************************************/	
		
		
		/* PASO 1 *********************************************************************/	
				
		addStep("Realizar actualizaciones a las 3 tablas origen que son replicadas en ODS");
		
		
		/* PASO 2 *********************************************************************/	

		addStep("Validar que los cambios realizados sean registrados en los Journal correspondientes.");
		
		/* PASO 3 *********************************************************************/	

		addStep("Ejecutar el job.");
		
		JSONObject obj = new JSONObject(data.get("query"));
		Control_mInicio CM = new Control_mInicio(u, data.get("ctrlM_user"), data.get("ctrlM_passwd"));
	
		addStep("Login");
		u.get(data.get("ctrlM_url"));
		u.hardWait(20);
		u.waitForLoadPage();
		CM.logOn();
	
		addStep("Inicio de job");
		ControlM control = new ControlM(u, testCase, obj);
	
		control.searchJob();	
		String resultado = control.executeJob();
	
		System.out.println("Resultado de la ejecuci�n: "+resultado);
		System.out.println("Valor de output : "+control.getOutput());
		
		/* PASO 4 *********************************************************************/	

		addStep("Validar la actualizaci�n de los datos en la tabla destino correspondiente al job ejecutado en el paso anterior");
		
		/* PASO 5 *********************************************************************/	

		addStep("Validar que permanezcan los registros del journal de las tablas restantes.");
		
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
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "QA Automation";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}