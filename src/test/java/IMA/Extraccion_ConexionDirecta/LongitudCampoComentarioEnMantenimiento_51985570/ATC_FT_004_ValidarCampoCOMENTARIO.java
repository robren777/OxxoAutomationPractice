package IMA.Extraccion_ConexionDirecta.LongitudCampoComentarioEnMantenimiento_51985570;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_004_ValidarCampoCOMENTARIO extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarCampoCOMENTARIO_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil dbBIMTTOQA = new SQLUtil(GlobalVariables.DB_HOST_BIMTTOQA, GlobalVariables.DB_USER_BIMTTOQA,GlobalVariables.DB_PASSWORD_BIMTTOQA);
				
		/*************************************** VARIABLES ***************************************/
		String Query = "SELECT COMENTARIO, LENGTH (COMENTARIO) FROM BIQUERYMTTO.VM_ODS_MTT_ANSWER WHERE LENGTH (COMENTARIO)>250";
		
		String Query2= "SELECT * FROM (SELECT COMENTARIO, LENGTH (COMENTARIO) FROM BIODSMTTO.MTT_ANSWER WHERE LENGTH (COMENTARIO)=250) WHERE ROWNUM <=15";
		
		/*************************************** PASO 1 ***************************************/	
		
		  addStep("Se muestran resultados de la tabla BIQUERYMTTO.VM_ODS_MTT_ANSWER");
		  
		  System.out.println(GlobalVariables.DB_HOST_BIMTTOQA); System.out.println("\n"
		  + Query);
		   
		  SQLResult ExecuteQuery = executeQuery(dbBIMTTOQA, Query);
		  
		  boolean StatusQuery = ExecuteQuery.isEmpty();
		  System.out.println(StatusQuery); if (!StatusQuery) {
		  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); } else { testCase.
		  addTextEvidenceCurrentStep("No se muestran registros de la tabla BIQUERYMTTO.VM_ODS_MTT_ANSWER"
		  ); testCase.addQueryEvidenceCurrentStep(ExecuteQuery); }
		  assertFalse(StatusQuery, "No se muestran registros de la tabla BIQUERYMTTO.VM_ODS_MTT_ANSWER");
		 
		/*************************************** PASO 2 ***************************************/	
		
		addStep("Se muestran resultados de la tabla BIODSMTTO.MTT_ANSWER");

		System.out.println(GlobalVariables.DB_HOST_BIMTTOQA);
		System.out.println("\n" + Query2);
		
		SQLResult ExecuteQuery2 = executeQuery(dbBIMTTOQA, Query2);
		
		boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		System.out.println(StatusQuery2);
		if (!StatusQuery2) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran registros de la tabla BIODSMTTO.MTT_ANSWER");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		}			
		assertFalse(StatusQuery2, "No se muestran registros de la tabla BIODSMTTO.MTT_ANSWER");

	}

	@Override
	public String setTestFullName() {
// TODO Auto-generated method stub
		return "ATC_FT_004_ValidarCampoCOMENTARIO_test";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "MTC_FT-001_CP004_Validar el campo COMENTARIO";
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
