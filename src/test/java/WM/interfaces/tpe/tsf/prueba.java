package WM.interfaces.tpe.tsf;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import modelo.BaseExecution;

public class prueba extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_prueba(HashMap<String, String> data) throws Exception {
		
/** UTILERIA *********************************************************************/	

		//SQLUtil dbFCTPEQA2 = new SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO,GlobalVariables.DB_PASSWORD_FCTPE_QRO);
		
		
		testCase.setProject_Name("Informacion de Remesas en IMA");
		testCase.setTest_Description( data.get("name"));
		
		
		 addStep("Ingresar a SQL Developer y realizar conexion a FCTPEQA1");
			
			boolean conexionFCTPEQA = true;
			
			testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA2 ");
		        testCase.addTextEvidenceCurrentStep("10.184.40.120:1521/FCTPEQA.FEMCOM.NET");
			//testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCTPE_QRO);
			
			assertTrue(conexionFCTPEQA, "La conexion no fue exitosa");
			
		

	}	
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_001_prueba";
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

