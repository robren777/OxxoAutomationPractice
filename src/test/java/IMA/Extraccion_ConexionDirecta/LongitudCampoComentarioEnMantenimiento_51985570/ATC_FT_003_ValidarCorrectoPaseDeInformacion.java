package IMA.Extraccion_ConexionDirecta.LongitudCampoComentarioEnMantenimiento_51985570;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_003_ValidarCorrectoPaseDeInformacion extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarCorrectoPaseDeInformacion_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil dbBIMTTOQA = new SQLUtil(GlobalVariables.DB_HOST_BIMTTOQA, GlobalVariables.DB_USER_BIMTTOQA,GlobalVariables.DB_PASSWORD_BIMTTOQA);
				
		/*************************************** VARIABLES ***************************************/
		String Query = "Select COUNT(*) from BIQUERYMTTO.VM_ODS_MTT_ANSWER";
		
		String Query2= "Select COUNT(*) from BIODSMTTO.MTT_ANSWER";
		
		/*************************************** PASO 1 ***************************************/	
		addStep("Verificar que se muestre el total de los registros de la tabla BIQUERYMTTO.VM_ODS_MTT_ANSWER");

		System.out.println(GlobalVariables.DB_HOST_BIMTTOQA);
		System.out.println("\n" + Query);
		
		SQLResult ExecuteQuery = executeQuery(dbBIMTTOQA, Query);
		 
		boolean StatusQuery = ExecuteQuery.isEmpty();
		System.out.println(StatusQuery);
		if (!StatusQuery) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestra el total de los registros de la tabla BIQUERYMTTO.VM_ODS_MTT_ANSWER");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		}			
		assertFalse(StatusQuery, "No se muestra el total de los registros de la tabla BIQUERYMTTO.VM_ODS_MTT_ANSWER");

		/*************************************** PASO 2 ***************************************/	
		addStep("Verificar que se muestre el total de los registros de la tabla BIODSMTTO.MTT_ANSWER");

		System.out.println(GlobalVariables.DB_HOST_BIMTTOQA);
		System.out.println("\n" + Query);
		
		SQLResult ExecuteQuery2 = executeQuery(dbBIMTTOQA, Query2);
		
		boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		System.out.println(StatusQuery2);
		if (!StatusQuery2) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestra el total de los registros de la tabla BIODSMTTO.MTT_ANSWER");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		}			
		assertFalse(StatusQuery2, "No se muestra el total de los registros de la tabla BIODSMTTO.MTT_ANSWER");
				
		/*************************************** PASO 4 ***************************************/	
			addStep("Verfificar que se muestran la misma cantidad� de registros en las ambas consultas");
			SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
			u.close();
			IMATools ima = new IMATools(u,testCase);
			
			String a="COUNT(*)";
			ima.compararCampoTablas(ExecuteQuery, a, 0, ExecuteQuery2, a, 0);	
			Boolean pass;
			
			if (ima.equals("Los registros son diferentes.")) {
				pass=false;
			} 
			else {			
				pass=true;
			}	
			assertFalse(pass,"Se encuentran diferentes registros en ambas consultas");
			
	}

	@Override
	public String setTestFullName() {
// TODO Auto-generated method stub
		return "ATC_FT_003_ValidarCorrectoPaseDeInformacion_test";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "MTC_FT-001_CP003_Validar correcto pase de informaci�n";
	}

	@Override
	public String setTestDesigner() {
// TODO Auto-generated method stub
		return "Team IMA";
	}

	@Override
	public String setTestInstanceID() {
// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
// TODO Auto-generated method stub
		return "Prerequisitos";
	}

	@Override
	public void beforeTest() {
// TODO Auto-generated method stub
	}

	@Override
	public void baseAfterTest() {
	}
}
