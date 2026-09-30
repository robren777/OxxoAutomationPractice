package WM.interfaces.pe6;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class ATC_FT_009_PE6_ValidarMotorSASEncendido extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_PE6_ValidarMotorSASEncendido_test (HashMap <String, String> data) throws Exception{
		/*
		 * Utilerias***********************************************************************/
		utils.sql.SQLUtil dbFCTDC = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTDCQA, GlobalVariables.DB_USER_FCTDCQA, GlobalVariables.DB_PASSWORD_FCTDCQA);
		
		
		/*
		 * Variables***********************************************************************/
		String querySasStatus = "SELECT APPLICATION, CATEGORY, STATUS, ENTITY, PROC_CODE, DECRIPTION, CREATION_DATE\r\n"
				+ "FROM tpeuser.tpe_ma_config\r\n"
				+ "WHERE APPLICATION = 'PE6'";
		
		boolean statusToValidate = true;
		
		/*************************************Paso 1***************************************/
		addStep("Validar que las banderas en la tabla MA_CONFIG est�n activas para que lo referente antifraude este cuando se realicen pruebas");
		
		SQLResult getStatusSAS = dbFCTDC.executeQuery(querySasStatus);
		boolean validateGetStatusSAS = getStatusSAS.isEmpty();
		boolean status = false;
		if(!validateGetStatusSAS) {
			for (int i = 0; i < 4; i++) {
				String category = getStatusSAS.getData(i, "CATEGORY");
				  status = Boolean.parseBoolean(getStatusSAS.getData(i, "STATUS"));
				  System.out.println(category + " Status: " + status);
				}
			testCase.addBoldTextEvidenceCurrentStep("Site 1:");
			testCase.addQueryEvidenceCurrentStep(getStatusSAS);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("Site 1:");
			testCase.addQueryEvidenceCurrentStep(getStatusSAS);
		}
		assertEquals(status, statusToValidate, "Se encontro bandera inactiva para SAS");
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
		return "ATC-FT-01-Validar motor l�nea SAS encendido";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose luis Flores";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_009_PE6_ValidarMotorSASEncendido_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
