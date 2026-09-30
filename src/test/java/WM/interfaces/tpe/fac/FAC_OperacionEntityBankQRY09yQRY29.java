package WM.interfaces.tpe.fac;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.TPE_FAC;
import WM.util.GlobalVariables;
import WM.util.RequestUtil;
import modelo.BaseExecution;
import utils.sql.SQLResult;

public class FAC_OperacionEntityBankQRY09yQRY29 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_TPE_FAC_OperacionEntityBankQRY09yQRY29(HashMap<String, String> data) throws Exception {

		/*
		 * Utiler�as
		 *********************************************************************/
		// SqlUtil db = new SqlUtil(GlobalVariables.DB_USERPE3,
		// GlobalVariables.DB_PASSWORDPE3, GlobalVariables.DB_HOSTPE3);
//		utils.sql.SQLUtil db = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCACQA,GlobalVariables.DB_USER_FCACQA, GlobalVariables.DB_PASSWORD_FCACQA);
		utils.sql.SQLUtil db = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
				GlobalVariables.DB_PASSWORD_FCTPE);

		TPE_FAC facUtil = new TPE_FAC(data, testCase, db);
	

		/*
		 * Variables
		 *********************************************************************/

		String wmCodeQRY09 = "101";
		String tdcQRY09 = "SELECT wm_code, APPLICATION,ENTITY,OPERATION,FOLIO,CREATION_DATE FROM TPEUSER.TPE_FR_TRANSACTION"
				+ " WHERE APPLICATION = 'FAC'" + " AND ENTITY = 'BANK' " + "AND OPERATION = '" + data.get("operation")
				+ "' " + " AND folio = %s " + " AND CREATION_DATE >= (SYSDATE-1)";

		String tdcQRY09user = "SELECT login_id,user_id,plaza,tienda,modulo,LOGIN_DATE FROM TPEUSER.FAC_LOGIN_USERS"
				+ " WHERE PLAZA =" + data.get("plaza") + " AND TIENDA =" + data.get("tienda") + " AND USER_ID ="
				+ data.get("idusuario") + " AND LOGIN_DATE >= (SYSDATE-1)";

		String wmCodeRequestQRY09;

		String folio;
		String wmCodeRequest;
		String wmCodeDb;

		/**
		 * Se debera probar las consultas a la tabla ya que el servicio no esta
		 * disponible
		 */

		/*
		 * Paso 1
		 *****************************************************************************************/

//Paso 1
		addStep("Llamar al servicio de consulta QRY0");

		String respuestaQRY09 = facUtil.QRY09();

		System.out.println("Respuesta: " + respuestaQRY09);

		folio = RequestUtil.getFolioXml(respuestaQRY09);
		// idusuario = RequestUtil.getFolioXml(respuestaQRY09);

		wmCodeRequestQRY09 = RequestUtil.getWmCodeXml(respuestaQRY09);

		/* Paso 2 *********************************************************/

		addStep("Verificar la respuesta generada por el servicio");

		boolean validationRequest = wmCodeQRY09.equals(wmCodeRequestQRY09);
		System.out.println(validationRequest + " - wmCode request: ");

		assertTrue(validationRequest, "wmCodeQRY09 no es Igual a wmCodeRequestQRY09");

///*Paso 3 *********************************************************/

		addStep("Validar que se ha creado un registro en la tabla TPE_FR_TRANSACTION de TPEUSER");

		String query = String.format(tdcQRY09, folio);
		System.out.println(query);
		Thread.sleep(6000);

		SQLResult Transaction = executeQuery(db, query);

		boolean validationDb = Transaction.isEmpty();
		if (!validationDb) {

			testCase.addQueryEvidenceCurrentStep(Transaction);

		} else if (validationDb) {
			testCase.addTextEvidenceCurrentStep("NO SE CREO REGISTRO EN LA TABLA");
			testCase.addQueryEvidenceCurrentStep(Transaction);
		}

		System.out.println(validationDb);

		assertFalse(validationDb,"NO SE CREO REGISTRO EN LA TABLA");

///*Paso 4 *********************************************************/	

		addStep("Validar la inserci�n del login en la tabla FAC_LOGIN_USERS de TPEUSER.");

		System.out.println(tdcQRY09user);

		SQLResult executequeryUSER = executeQuery(db, tdcQRY09user);

		boolean validationUser = executequeryUSER.isEmpty();

		if (!validationUser) {

			testCase.addQueryEvidenceCurrentStep(executequeryUSER);

		} else if (validationUser) {
			testCase.addTextEvidenceCurrentStep("NO SE CREO REGISTRO EN LA TABLA");
			testCase.addQueryEvidenceCurrentStep(executequeryUSER);
		}

		System.out.println(validationUser);

		assertFalse(validationUser, "NO SE CREO REGISTRO EN LA TABLA");

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar la operaci�n QRY09 y QRY29 entity BANK";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_TPE_FAC_OperacionEntityBankQRY09yQRY29";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
