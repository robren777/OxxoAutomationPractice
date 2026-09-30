package WM.interfaces.pe6;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLUtil;

public class Prueba extends BaseExecution{
	@Test(dataProvider = "data-provider")

	public void Prueba_test(HashMap<String, String> data) throws Exception {
		
		SQLUtil dbFCTDCQA = new SQLUtil(GlobalVariables.DB_HOST_FCTDCQA, GlobalVariables.DB_USER_FCTDCQA,
				GlobalVariables.DB_PASSWORD_FCTDCQA);

		/********************************
		 * VARIABLES *
		 ********************************/  

		// Paso 1 *********************************************************

	addStep("conectar BD");
	
	System.out.println(GlobalVariables.DB_HOST_FCTDCQA);
	testCase.addTextEvidenceCurrentStep("Se ha realizado onexion con la bd "+GlobalVariables.DB_HOST_FCTDCQA);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Automation";
	}

	@Override
	public String setTestFullName() {
		return "Prueba de nodo";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Prueba de nodo";
	}
}
