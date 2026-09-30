package WM.interfaces.pr2col;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.PR2_CO;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


public class PR2_CO_ConfirmacionDeTrasferencias extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_PR2_COL_Confirmacion_De_Transferencias(HashMap<String, String> data) throws Exception {
		
/*
 * Utiler�as *********************************************************************/

		SQLUtil dbPos = new SQLUtil(GlobalVariables.DB_HOST_Puser, GlobalVariables.DB_USER_Puser, GlobalVariables.DB_PASSWORD_Puser);
		PR2_CO pr2_coUtil = new PR2_CO(data, testCase, dbPos);
/**
* Variables ******************************************************************************************
* 
* 
*/
		String tdcQueryTSF = "SELECT PV_DOC_ID, SENDER_ID, RECEIVER_ID, NO_RECORDS, STATUS "
				+ " FROM posuser.POS_TSF_ONLINE_HEAD "
				+ " WHERE STATUS = 'I' "
				+ " AND PV_DOC_ID = " + data.get("PV_DOC_ID") +" "
				+ " AND SENDER_ID = '" + data.get("SENDER") +"'"
				+ " AND RECEIVER_ID = '" + data.get("RECEIVER") +"'";
		
		String tdcQueryTransaction = "SELECT ID, OPERATION, START_DT, STATUS " //REQUEST
				+ " FROM POSUSER.TSF_TRANSACTION_COL "
				+ " WHERE START_DT >= TRUNC(SYSDATE) "
				+ " AND STATUS = 'S'"
				+ " AND OPERATION = 'PR2_CO_ACK'";
		
		String tdcQueryE = "SELECT PV_DOC_ID, SENDER_ID, RECEIVER_ID, NO_RECORDS, STATUS, ACK_DATE"
				+ " from posuser.POS_TSF_ONLINE_HEAD "
				+ " WHERE ACK_DATE >= TRUNC(SYSDATE) "
				+ " AND STATUS = 'E'"
				+ " AND PV_DOC_ID = %s ";


		
		
		
		
/**
* 	
* **********************************Pasos del caso de Prueba *****************************************
* 
*/
		
//Paso 1 *************************		
		addStep("Comprobar que existan registros en la tabla POS_TSF_ONLINE_HEAD en la BD POSUSER, con estatus = 'I' (Confirmado).");
		System.out.println(GlobalVariables.DB_HOST_Puser);
		System.out.println(tdcQueryTSF);
		
		SQLResult tsfResult = executeQuery(dbPos, tdcQueryTSF);
		String pvdoc = "";		
		
		boolean tsf = tsfResult.isEmpty();
		
		if (!tsf) {
			pvdoc = tsfResult.getData(0, "PV_DOC_ID");
			testCase.addQueryEvidenceCurrentStep(tsfResult);
		}
		
		System.out.println(tsf);
		
		assertFalse(tsf, "No se obtiene informaci�n de la consulta");
		
//Paso 2 **************************
		addStep("Se env�a una petici�n por HTTP al servicio: PR2_CO.Pub:runACK:");
		
		pr2_coUtil.ejecutarRunAck();
	

		
		
//Paso 3 **************************
		addStep("Comprobar que se registre la transacci�n en la tabla TSF_TRANSACTION_COL de la BD POSUSER para la operaci�n PR2_CO_ACK.");
		System.out.println(GlobalVariables.DB_HOST_Puser);
		System.out.println(tdcQueryTransaction);
		
		SQLResult transactionResult = executeQuery(dbPos, tdcQueryTransaction);
		
		
		boolean transaction = transactionResult.isEmpty();
		
		if (!transaction) {
			
			testCase.addQueryEvidenceCurrentStep(transactionResult);
		}
		
		System.out.println(transaction);
		
		assertFalse(transaction, "No se obtiene informaci�n de la consulta");
		
//paso 4 ***********************
		addStep("Comprobar que se actualicen los registros en la tabla POS_TSF_ONLINE_HEAD de la BD POSUSER con status igual a 'E'.");
		
		System.out.println(GlobalVariables.DB_HOST_Puser);
		String statusFormat = String.format(tdcQueryE, pvdoc);
		SQLResult statusResult = executeQuery(dbPos, statusFormat);
		
		
		boolean statusE = statusResult.isEmpty();
		
		if (!statusE) {
			
			testCase.addQueryEvidenceCurrentStep(statusResult);
		}
		
		System.out.println(statusE);
		
		assertFalse(statusE, "No se obtiene informaci�n de la consulta");
		
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
		return "Construido. Confirmar transferencias recibidas.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_PR2_COL_Confirmacion_De_Transferencias";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
