package IMA.Extraccion_ConexionDirecta.CorreccionReprocesoFinanzas_51982666;

import modelo.BaseExecution;
import IMA.util.GlobalVariables;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import utils.sql.SQLResult;
import utils.sql.SQLUtil;

import java.util.HashMap;

public class ATC_FT_003_ValidarCargaDeInformacion extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarCargaDeInformacion_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** BASE DATOS***************************************/
		SQLUtil dbBIQN = new SQLUtil(GlobalVariables.DB_HOST_BIQN, GlobalVariables.DB_USER_BIQN,GlobalVariables.DB_PASSWORD_BIQN);
				
		/*************************************** VARIABLES ***************************************/
		String Query = "Select * from (Select * from biodsfin. BITACORA_ODS_FIN_LOG where CONCEPTO_TABLA = 'BIODS_FIN_SALDOS_SQL_10.sql') WHERE ROWNUM <=25";
		
		String Query2= "select * from (select ID_REPORTE,DES_TIPOGRUPO,DES_GRUPO,DES_SUBGRUPO FROM BIQUERYFIN.V_QUERY_ESTRCONTABLE) WHERE ROWNUM <= 25";
		
		String Query3= "select * from (Select APPLICATION_ID,AXIS_SET_ID,AXIS_SEQ,DESCRIPTION from BIQUERYFIN.V_RG_REPORT_AXES) WHERE ROWNUM <= 25";
		
		String Query4="select * from (Select APPLICATION_ID,AXIS_SET_ID,AXIS_SEQ,LAST_UPDATE_DATE from BIQUERYFIN.V_RG_REPORT_AXIS_CONTENTS) WHERE ROWNUM <= 25";
		
		/*************************************** PASO 1 ***************************************/	
		
		  addStep("Verificar que se muestren registros en la tabla biodsfin.BITACORA_ODS_FIN_LOG");
		   
		  System.out.println(GlobalVariables.DB_HOST_BIQN); System.out.println(Query);
		  
		  SQLResult ExecuteQuery = executeQuery(dbBIQN, Query);
		  
		  boolean StatusQuery = ExecuteQuery.isEmpty();
		  System.out.println(StatusQuery); if (!StatusQuery) {
		  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); } else { testCase.
		  addTextEvidenceCurrentStep("No se muestran los registros en la tabla biodsfin.BITACORA_ODS_FIN_LOG."); 
		  testCase.addQueryEvidenceCurrentStep(ExecuteQuery); }
		  assertFalse(StatusQuery, "No se muestran los registros en la tabla biodsfin.BITACORA_ODS_FIN_LOG.");
		 
		/*************************************** PASO 2 ***************************************/	
		
		addStep("Verificar que se muestren registros en la tabla BIQUERYFIN.V_QUERY_ESTRCONTABLE");

		System.out.println(GlobalVariables.DB_HOST_BIQN);
		System.out.println("\n" + Query2);
		
		SQLResult ExecuteQuery2 = executeQuery(dbBIQN, Query2);
		
		boolean StatusQuery2 = ExecuteQuery2.isEmpty();
		System.out.println(StatusQuery2);
		if (!StatusQuery2) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran los registros en la tabla BIQUERYFIN.V_QUERY_ESTRCONTABLE.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery2);
		}			
		assertFalse(StatusQuery2, "No se muestran los registros en la tabla BIQUERYFIN.V_QUERY_ESTRCONTABLE.");

		/*************************************** PASO 3 ***************************************/	
		
		addStep("Verificar que se muestren registros en la tabla BIQUERYFIN.V_RG_REPORT_AXES");

		System.out.println(GlobalVariables.DB_HOST_BIQN);
		System.out.println("\n" + Query3);
		
		SQLResult ExecuteQuery3 = executeQuery(dbBIQN, Query3);
		
		boolean StatusQuery3 = ExecuteQuery3.isEmpty();
		System.out.println(StatusQuery3);
		if (!StatusQuery3) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran los registros en la tabla BIQUERYFIN.V_RG_REPORT_AXES.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery3);
		}			
		assertFalse(StatusQuery3, "No se muestran los registros en la tabla BIQUERYFIN.V_RG_REPORT_AXES.");

		/*************************************** PASO 4 ***************************************/	
		
		addStep("Verificar que se muestren registros en la tabla BIQUERYFIN.V_RG_REPORT_AXIS_CONTENTS");

		System.out.println(GlobalVariables.DB_HOST_BIQN);
		System.out.println("\n" + Query4);
		
		SQLResult ExecuteQuery4 = executeQuery(dbBIQN, Query4);
		
		boolean StatusQuery4 = ExecuteQuery4.isEmpty();
		System.out.println(StatusQuery4);
		if (!StatusQuery4) {
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
		} else {			
			testCase.addTextEvidenceCurrentStep("No se muestran los registros en la tabla BIQUERYFIN.V_RG_REPORT_AXIS_CONTENTS.");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery4);
		}			
		assertFalse(StatusQuery4, "No se muestran los registros en la tabla BIQUERYFIN.V_RG_REPORT_AXIS_CONTENTS.");

		
	}

	@Override
	public String setTestFullName() {
// TODO Auto-generated method stub
		return "ATC_FT_003_ValidarCargaDeInformacion_test";
	}

	@Override
	public String setTestDescription() {
// TODO Auto-generated method stub
		return "MTC_FT-001_CP003_Validar carga de informaci�n";
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