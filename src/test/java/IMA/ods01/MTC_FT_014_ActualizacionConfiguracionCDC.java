package IMA.ods01;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;

public class MTC_FT_014_ActualizacionConfiguracionCDC extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_014_ActualizacionConfiguracionCDC_(HashMap<String, String> data) throws Exception {
		
		/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI, GlobalVariables.DB_PASSWORD_BI);

		/** VARIABLES *********************************************************************/	
		
		
		/* PASO 1 *********************************************************************/	
				
		addStep("Validar la ejecuci�n del proceso de actualizaci�n de la tabla de control de carga.");
		
		
		/* PASO 2 *********************************************************************/	

		addStep("Validar que el periodo de carga sea actualizado a la fecha = Sysdate -1");
		
		
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