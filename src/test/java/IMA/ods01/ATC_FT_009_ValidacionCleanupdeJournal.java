package IMA.ods01;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;

public class ATC_FT_009_ValidacionCleanupdeJournal extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_ValidacionCleanupdeJournal_test(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);

		/** VARIABLES *********************************************************************/	
		
		
		/* PASO 1 *********************************************************************/	
				
		addStep("Validar que el modelo tenga al menos una tabla inactiva para control de carga.");
		
		
		/* PASO 2 *********************************************************************/	

		addStep("Seleccionar el modelo desde la secci�n de Planes de Carga y Casos, dar clic derecho- Ejecutar");
		
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
		return "ATC_FT_009_ValidacionCleanupdeJournal_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
