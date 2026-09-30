package WM.interfaces.tpe.ols;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLUtil;

public class ATC_FT_005_TPE_OLS_Prueba extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_TPE_OLS_Prueba_test(HashMap<String, String> data) throws Exception {

		
		/*
		 * Utilerias
		 *********************************************************************/
		SQLUtil db = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
				GlobalVariables.DB_PASSWORD_FCTPE);
		

		/*
		 * Variables
		 *********************************************************************/
		addStep("Realizar prueba de conexion.");
		
		
		testCase.addTextEvidenceCurrentStep("Conexion exitosa: "+GlobalVariables.DB_HOST_FCTPE);
		
		System.out.println(GlobalVariables.DB_HOST_FCTPE);
		
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Prueba de nodo";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Equipo de automatizacion";
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
