package WM.interfaces.pe6;

import static WM.util.RequestUtil.getSimpleDataXml;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.PE6;
import WM.util.GlobalVariables;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.sql.SQLResult;

public class ATC_FT_023_PE6_LogueoDeErroresParametroInvalidoACK extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_023_PE6_LogueoDeErroresParametroInvalidoACK_test(HashMap <String, String>data) throws Exception{
		/*
		 * Utileria********************************************************************/
		utils.sql.SQLUtil dbFCTDCQA= new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTDCQA_QRO,GlobalVariables.DB_USER_FCTDCQA_QRO, GlobalVariables.DB_PASSWORD_FCTDCQA_QRO);
		utils.sql.SQLUtil dbFCSWQA= new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCSWQA_QRO, GlobalVariables.DB_USER_FCSWQA_QRO, GlobalVariables.DB_PASSWORD_FCSWQA_QRO);
		
		PE6 pe6Params = new PE6(data, testCase, null);
		
		
		/*
		 * Variables*******************************************************************/
		String queryTransaction = "SELECT FOLIO, PLAZA, TIENDA, PV_DATE, ADMIN_DATE, BANK, AMOUNT\r\n"
				+ "FROM TPEUSER.TDC_TRANSACTION\r\n"
				+ "WHERE CREATION_DATE = TRUNC(SYSDATE)\r\n"
				+ "AND FOLIO = '%s'";
		
		String queryLog = "SELECT PLAZA, TIENDA, APPLICATION, ADMIN_DT, FOLIO, AMOUNT\r\n"
				+ "FROM swuser.TPE_SW_TLOG\r\n"
				+ "WHERE CREATION_DATE = TRUNC(SYSDATE)\r\n"
				+ "AND APPLICATION = 'TDC'\r\n"
				+ "AND FOLIO = '%s'";
		
		String queryTransactionError = "SELECT FOLIO, CREATION_DATE, PLAZA, TIENDA, BANK, AMOUNT\r\n"
				+ "FROM TPEUSER.TDC_TRANSACTION\r\n"
				+ "WHERE CREATION_DATE = TRUNC(SYSDATE)\r\n"
				+ "AND FOLIO = '%s'";
		
		String wmCodeToValidate = data.get("wmCode");
		String wmCodeToValidate2 = data.get("wmCode1");
		String wmCodeToValidate3 = data.get("wmCode2");
		
		String token = "c431e5ab630322418c1061b3b46f4a5ef1980a430b741c7cca9bf45f477e7adb";
		
		HashMap <String, String> headersToken = new HashMap <String, String>();
		headersToken.put("Authorization", "Bearer " + token);
		
		/*************************************Paso 1********************************/
		addStep("Realizar solicitud de Folio");
		
		String paramsMap = pe6Params.runGetFolioParams();
		String uriFolio = data.get("baseUri") + data.get("getFolio") + paramsMap;
		ApiMethodsUtil api = new ApiMethodsUtil(uriFolio);
		String folio = "";
		String wmCodeFolio = "";
		String creationDate = "";
		Response requestFolio = api.getRequestWithHeaders(uriFolio, headersToken);
		if (requestFolio.getStatusCode() == Integer.parseInt(data.get("statusCode"))) {
			String bodyFolio = requestFolio.getBody().asPrettyString();
			System.out.println(bodyFolio);
			folio = getSimpleDataXml(bodyFolio, "folio");
			wmCodeFolio = getSimpleDataXml(bodyFolio, "wmCode");
			creationDate = getSimpleDataXml(bodyFolio, "creationDate");
			//Agregar Request a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Request:");
			testCase.addBoldTextEvidenceCurrentStep("Metodo: GET");
			testCase.addTextEvidenceCurrentStep(uriFolio + paramsMap);
			//Agregar response a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Response:");
			testCase.addBoldTextEvidenceCurrentStep("Status: " + requestFolio.getStatusLine());
			testCase.addBoldTextEvidenceCurrentStep(bodyFolio);
			
			assertEquals(wmCodeFolio, wmCodeToValidate, "Se obtiene wmCode diferente al esperado");
		}else {
			assertEquals(requestFolio.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
		}
		/**********************************Paso 2****************************************/
		addStep("Realizar solicitud de autorizacion");
		
		String paramsAuth = pe6Params.runGetAuthParams(folio, creationDate);
		String uriAuth = data.get("baseUri") + data.get("getAuth") + paramsAuth;
		System.out.println(uriAuth);
		Response requestAuth = api.getRequestWithHeaders(uriAuth, headersToken);
		if (requestAuth.getStatusCode() == Integer.parseInt(data.get("statusCode"))) {
			String bodyAuth = requestAuth.getBody().asPrettyString();
			System.out.println(bodyAuth);
			String wmCodeAuth = getSimpleDataXml(bodyAuth, "wmCode");
			
			//Agregar Request a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Request:");
			testCase.addBoldTextEvidenceCurrentStep("Metodo: GET");
			testCase.addTextEvidenceCurrentStep(uriAuth);
			//Agregar response a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Response:");
			testCase.addBoldTextEvidenceCurrentStep("Status: " + requestAuth.getStatusLine());
			testCase.addBoldTextEvidenceCurrentStep(bodyAuth);
			
			assertEquals(wmCodeAuth, wmCodeToValidate2, "Se obtiene wmCode diferente al esperado, "
					+ "ya que la transaccion fue realizada dentro de horario");
		}else {
			assertEquals(requestAuth.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
		}
		/************************************Paso 3***********************************/
		addStep("Validar que se registre la informaci�n de la transacci�n en FCTDCQA");
		
		String transactionFormat = String.format(queryTransaction, folio);
		System.out.println(transactionFormat);
		
		SQLResult transaction = dbFCTDCQA.executeQuery(transactionFormat);
		boolean validateTransaction = transaction.isEmpty();
		if(!validateTransaction) {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCTDCQA");
			testCase.addQueryEvidenceCurrentStep(transaction);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCTDCQA");
			testCase.addQueryEvidenceCurrentStep(transaction);
		}
		assertFalse(validateTransaction);
		/*******************************Paso 4*******************************************/
		addStep("Validar que se registre la informaci�n de la transacci�n");
		
		String logFormat = String.format(queryLog, folio);
		System.out.println(logFormat);
		
		SQLResult log = dbFCSWQA.executeQuery(logFormat);
		boolean validateLog = transaction.isEmpty();
		if(!validateLog) {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCSWQA");
			testCase.addQueryEvidenceCurrentStep(log);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCSWQA");
			testCase.addQueryEvidenceCurrentStep(log);
		}
		assertFalse(validateLog);
		/***************************************Paso 5***************************************/
		addStep("Realizar Solicitud de ack");
		
		String paramsAck = pe6Params.runGetAckParams(folio, creationDate);
		String uriAck = data.get("baseUri") + data.get("getAuthAck") + paramsAck;
		System.out.println(uriAck);
		Response requestAck = api.getRequestWithHeaders(uriAck, headersToken);
		if (requestAck.getStatusCode() == Integer.parseInt(data.get("statusCode"))) {
			String bodyAck = requestAck.getBody().asPrettyString();
			System.out.println(bodyAck);
			String wmCodeAck = getSimpleDataXml(bodyAck, "wmCode");
			
			//Agregar Request a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Request:");
			testCase.addBoldTextEvidenceCurrentStep("Metodo: GET");
			testCase.addTextEvidenceCurrentStep(uriAck + paramsAck);
			//Agregar response a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Response:");
			testCase.addBoldTextEvidenceCurrentStep("Status: " + requestAck.getStatusLine());
			testCase.addBoldTextEvidenceCurrentStep(bodyAck);
			
			assertEquals(wmCodeAck, wmCodeToValidate2, "Se obtiene wmCode diferente al esperado");
		}else {
			assertEquals(requestAck.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
		}
		/**************************************Paso 6***************************************/
		addStep("Validar que se registre la informaci�n de la transacci�n con error");
		
		String errorTransFormat = String.format(queryTransactionError, folio);
		System.out.println(errorTransFormat);
		
		SQLResult errorTransaction = dbFCTDCQA.executeQuery(errorTransFormat);
		boolean validateErrorTransaction = transaction.isEmpty();
		if(!validateErrorTransaction) {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCTDCQA");
			testCase.addQueryEvidenceCurrentStep(errorTransaction);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCTDCQA");
			testCase.addQueryEvidenceCurrentStep(errorTransaction);
		}
		assertFalse(validateLog);
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
		return "Construido. MTC-FT-22-Logueo de errores par�metro invalido desde ACK, folio invalido";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose luis Flores";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_023_PE6_LogueoDeErroresParametroInvalidoACK_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
