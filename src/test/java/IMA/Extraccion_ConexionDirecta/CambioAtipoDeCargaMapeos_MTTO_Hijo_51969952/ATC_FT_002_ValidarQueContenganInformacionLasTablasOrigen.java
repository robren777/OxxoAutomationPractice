package IMA.Extraccion_ConexionDirecta.CambioAtipoDeCargaMapeos_MTTO_Hijo_51969952;

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

public class ATC_FT_002_ValidarQueContenganInformacionLasTablasOrigen extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarQueContenganInformacionLasTablasOrigen_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil dbBIMTTO = new SQLUtil(GlobalVariables.DB_HOST_BIMTTOQA, GlobalVariables.DB_USER_BIMTTOQA,GlobalVariables.DB_PASSWORD_BIMTTOQA);
				
		/*************************************** VARIABLES ***************************************/
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true); u.close();
		IMATools ima = new IMATools(u,testCase);	
		String Query = "SELECT * FROM (select id_cr_oficial, oracle_cr_oficial,oracle_cr_superior_oficial, loaddate FROM BIODSMTTO.CRS_OFICIALES)"
				+ "where rownum <=25";
		String Query1= "SELECT * FROM (SELECT doc_id, cr_plaza, cr_tienda, loaddate FROM BIODSMTTO.MTT_DOC_CONTROL_VW) where rownum <=25";
		
		String Query2= "SELECT * FROM (SELECT * FROM BIDMMTTO.DIM_RESPONSABLE) where rownum <=25";
		
		String Query3= "SELECT * FROM (SELECT doc_id, question_id, loaddate FROM BIODSMTTO.MTT_ANSWER) where rownum <=25";
		
		String Query4= "SELECT * FROM (SELECT pregunta_id, question_id, desc_falla, loaddate FROM BIODSMTTO.MTT_QUESTION) where rownum <=25";
		
		String Query5 = "SELECT doc_id, cr_plaza, cr_tienda, loaddate FROM BIODSMTTO.MTT_DOC_CONTROL_VW WHERE DOC_ID IN (750164, 749505)";
		
		String Query6 = "SELECT * FROM (SELECT doc_id, question_id, loaddate FROM BIODSMTTO.MTT_ANSWER WHERE DOC_ID IN (750164, 749505)) where rownum <=25";
		
		String Query7 = "SELECT pregunta_id, question_id, desc_falla, loaddate FROM BIODSMTTO.MTT_QUESTION WHERE QUESTION_ID = 13345";
		
		/*************************************** PASO 1 ***************************************/	
		addStep("Validar que las tablas origen contengan informacion");

		System.out.println(GlobalVariables.DB_HOST_BIMTTOQA);
		System.out.println("\n" + Query);
		 
		SQLResult ExecuteQuery = executeQuery(dbBIMTTO, Query);
		
		boolean StatusQuery = ExecuteQuery.isEmpty();
		System.out.println(StatusQuery);
		if (!StatusQuery) {
			ima.validarContenidoTablas(ExecuteQuery, Query);
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIODSMTTO.CRS_OFICIALES.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
		}			
		assertFalse(StatusQuery, "No se muestran registros en la tabla BIODSMTTO.CRS_OFICIALES.");
		
		/*******************************************************************************************************************/
		System.out.println("\n" + Query1);
		 
		SQLResult ExecuteQuery1 = executeQuery(dbBIMTTO, Query1);
		
		boolean StatusQuery1 = ExecuteQuery1.isEmpty();
		System.out.println(StatusQuery1);
		if (!StatusQuery1) {
			ima.validarContenidoTablas(ExecuteQuery1, Query1);
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery1);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIODSMTTO.MTT_DOC_CONTROL_VW.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery1);
		}			
		assertFalse(StatusQuery1, "No se muestran registros en la tabla BIODSMTTO.MTT_DOC_CONTROL_VW.");
		
		/*******************************************************************************************************************/
		System.out.println("\n" + Query2);
		 
		SQLResult ExecuteQuery2 = executeQuery(dbBIMTTO, Query2);
		
		boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		System.out.println(StatusQuery2);
		if (!StatusQuery2) {
			ima.validarContenidoTablas(ExecuteQuery2, Query2);
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIDMMTTO.DIM_RESPONSABLE.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		}			
		assertFalse(StatusQuery2, "No se muestran registros en la tabla BIDMMTTO.DIM_RESPONSABLE.");
		
		/*******************************************************************************************************************/
		System.out.println("\n" + Query3);
		 
		SQLResult ExecuteQuery3 = executeQuery(dbBIMTTO, Query3);
		
		boolean StatusQuery3 = ExecuteQuery3.isEmpty();
		System.out.println(StatusQuery3);
		if (!StatusQuery3) {
			ima.validarContenidoTablas(ExecuteQuery3, Query3);
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIODSMTTO.MTT_ANSWER.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
		}			
		assertFalse(StatusQuery3, "No se muestran registros en la tabla BIODSMTTO.MTT_ANSWER.");
		
		/*******************************************************************************************************************/
		System.out.println("\n" + Query4);
		 
		SQLResult ExecuteQuery4 = executeQuery(dbBIMTTO, Query4);
		
		boolean StatusQuery4 = ExecuteQuery4.isEmpty();
		System.out.println(StatusQuery4);
		if (!StatusQuery4) {
			ima.validarContenidoTablas(ExecuteQuery4, Query4);
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran registros en la tabla BIODSMTTO.MTT_QUESTION.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
		}			
		assertFalse(StatusQuery4, "No se muestran registros en la tabla BIODSMTTO.MTT_QUESTION.");
		
		/*********************************** PASO 2 *******************************************************************/
		addStep("Verificar que la consulta en la tabla BIODSMTTO.MTT_DOC_CONTROL_VW tenga registros");
		System.out.println("\n" + Query5);
		 
		SQLResult ExecuteQuery5 = executeQuery(dbBIMTTO, Query5);
		
		boolean StatusQuery5 = ExecuteQuery5.isEmpty();
		System.out.println(StatusQuery5);
		if (!StatusQuery5) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery5);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran registros de la consulta en la tabla BIODSMTTO.MTT_DOC_CONTROL_VW.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery5);
		}			
		assertFalse(StatusQuery5, "No se muestran registros de la consulta en la tabla BIODSMTTO.MTT_DOC_CONTROL_VW.");

		/*********************************** PASO 3 *******************************************************************/
		addStep("Verificar que la consulta en la tabla BIODSMTTO.MTT_ANSWER tenga registros");
		System.out.println("\n" + Query6);
		 
		SQLResult ExecuteQuery6 = executeQuery(dbBIMTTO, Query6);
		
		boolean StatusQuery6 = ExecuteQuery6.isEmpty();
		System.out.println(StatusQuery6);
		if (!StatusQuery6) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery6);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran registros de la consulta en la tabla BIODSMTTO.MTT_ANSWER.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery6);
		}			
		assertFalse(StatusQuery6, "No se muestran registros de la consulta en la tabla BIODSMTTO.MTT_ANSWER.");

		/*********************************** PASO 4 *******************************************************************/
		addStep("Verificar que la consulta en la tabla BIODSMTTO.MTT_QUESTION tenga registros");
		System.out.println("\n" + Query7);
		 
		SQLResult ExecuteQuery7 = executeQuery(dbBIMTTO, Query7);
		
		boolean StatusQuery7 = ExecuteQuery7.isEmpty();
		System.out.println(StatusQuery7);
		if (!StatusQuery7) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery7);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran registros de la consulta en la tabla BIODSMTTO.MTT_QUESTION.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery7);
		}			
		assertFalse(StatusQuery7, "No se muestran registros de la consulta en la tabla BIODSMTTO.MTT_QUESTION.");

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
		return "CP002 - Validar que contengan información las tablas Origen";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Automatizacion";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
