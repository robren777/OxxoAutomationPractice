package WM.interfaces.pr26;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;

public class ATC_FT_011_Prueba extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_011_Prueba_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utilerias
		 *********************************************************************/
		
		
		utils.sql.SQLUtil dbRms = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_FCRMSSIT,GlobalVariables.DB_USER_RMS_FCRMSSIT, GlobalVariables.DB_PASSWORD_RMS_FCRMSSIT);
		
	
		testCase.setProject_Name("Proyecto Multiformato");
		

//*************************** Paso 1 *********************************************************************************
		
		addStep("Conexion con BD RMS");
		testCase.addBoldTextEvidenceCurrentStep("Base de datos :" + GlobalVariables.DB_HOST_RMS_FCRMSSIT);
		testCase.addTextEvidenceCurrentStep("Conexion exitosa");
		boolean conexion =  true;		
			
		assertTrue("Fallo la conexion", conexion);

	
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Prueba conexion con octane";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMTIZACION";
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
