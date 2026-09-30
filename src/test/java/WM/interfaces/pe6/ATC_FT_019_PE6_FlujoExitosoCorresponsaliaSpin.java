package WM.interfaces.pe6;

import static WM.util.RequestUtil.getSimpleDataXml;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.om.PE6;
import WM.util.ApiUtil;
import WM.util.GlobalVariables;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.sql.SQLResult;

public class ATC_FT_019_PE6_FlujoExitosoCorresponsaliaSpin extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_019_PE6_FlujoExitosoCorresponsaliaSpin_test (HashMap <String, String> data) throws Exception{
		/*
		 *Utileria******************************************************************/
		utils.sql.SQLUtil dbFCTDCQA= new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTDCQA_QRO,GlobalVariables.DB_USER_FCTDCQA_QRO, GlobalVariables.DB_PASSWORD_FCTDCQA_QRO);
		utils.sql.SQLUtil dbFCSWQA= new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCSWQA_QRO, GlobalVariables.DB_USER_FCSWQA_QRO, GlobalVariables.DB_PASSWORD_FCSWQA_QRO);
		
		PE6 pe6Params = new PE6(data, testCase, null);
		
		/*
		 * Variables*****************************************************************/
		String queryTransaction = "SELECT FOLIO, CREATION_DATE, TIENDA, PV_DATE, ADMIN_DATE, BANK \r\n"
				+ "FROM TPEUSER.TDC_TRANSACTION\r\n"
				+ "WHERE CREATION_DATE >= TRUNC(SYSDATE)\r\n"
				+ "AND FOLIO = '%s'";
		
		String queryLog = "SELECT TIENDA, ADMIN_DT, FOLIO, APPLICATION\r\n"
				+ "FROM swuser.TPE_SW_TLOG\r\n"
				+ "WHERE CREATION_DATE >= TRUNC(SYSDATE)\r\n"
				+ "AND APPLICATION='TDC'\r\n"
				+ "AND FOLIO = '%s'";
		
		String queryAck = "SELECT FOLIO, TIENDA, PV_DATE, ADMIN_DATE, BANK\r\n"
				+ "FROM TPEUSER.TDC_TRANSACTION\r\n"
				+ "WHERE CREATION_DATE = (SYSDATE)\r\n"
				+ "AND FOLIO = '%s'\r\n"
				+ "AND ACK_CODE = 00";
		
		String querySwCode = "SELECT TIENDA, ACQUIRER, ADMIN_DT, FOLIO, AMOUNT, CREATION_DATE\r\n"
				+ "FROM swuser.TPE_SW_TLOG\r\n"
				+ "WHERE  CREATION_DATE  >= TRUNC(SYSDATE)\r\n"
				+ "AND APPLICATION='TDC'\r\n"
				+ "AND FOLIO = '%s'\r\n"
				+ "AND SW_CODE = 00";
		
		String wmCodeToValidate = data.get("wmCode");
		String wmCodeToValidate2 = data.get("wmCode1");
		String ackCodeToValidate = data.get("ackCode");
		String swCodeToValidate = data.get("swCode");
		String token = "c431e5ab630322418c1061b3b46f4a5ef1980a430b741c7cca9bf45f477e7adb";
		
		HashMap <String, String> headersToken = new HashMap <String, String>();
		headersToken.put("Authorization", "Bearer " + token);
		
		/*************************************Paso 1********************************/
		addStep("Realizar solicitud de Folio");
		
		String params = pe6Params.runGetFolioParams();
		
		String uriFolio = data.get("baseUri") + data.get("getFolio") + params;
		System.out.println(uriFolio);
		ApiMethodsUtil api = new ApiMethodsUtil(uriFolio);
		String paramsMap = pe6Params.runGetFolioParams();
		
//		Response requestFolio = api.getRequestWithHeaders(uriFolio, headersToken);
		Response requestFolio = api.getRequestMethod(uriFolio);
		
		String folio = "";
		String wmCodeFolio = "";
		String creationDate = "";
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
			
//			assertEquals(wmCodeFolio, wmCodeToValidate, "Se obtiene wmCode diferente al esperado");
		}else {
//			assertEquals(requestFolio.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
//					"Se obtiene status diferente a 200");
		}

		/**********************************Paso 2****************************************/
		String paramsAuth = "";
		if(Boolean.parseBoolean(data.get("idAut")) == true) {
			
			addStep("Realizar encripcion para el campo integridad");
			
			Map <String, String> headersVoltage = new HashMap <String, String>();
			headersVoltage.put("Content-Type", "application/json");
			headersVoltage.put("Authorization", data.get("vsAuth"));
			
//			int length = 12;
//			char padChar = '0';
//			String formatedFolio = StringUtils.leftPad(folio, length, padChar);
			
//			String jsonFormat = String.format(data.get("voltageJson"), data.get("cardNo") + creationDate.substring(4, 8) + creationDate.substring(8, 14) + folio + data.get("amount"));
			String jsonFormat = String.format(data.get("voltageJson"), data.get("cardNo") + creationDate.substring(4, 14) + folio + data.get("amount"));
			
//			String jsonFormat = String.format(data.get("voltageJson"), "STRING_DATA_VOLTAGE");
//			Response requestEncripcion = api.postRequestWithHeaders(data.get("uriVoltage"), jsonFormat, headersVoltage);
			System.out.println(jsonFormat);
			HttpResponse requestEncripcion = ApiUtil.sendPostDissableSSL(data.get("uriVoltage"), jsonFormat, headersVoltage);
			System.out.println(requestEncripcion.getStatusLine().getStatusCode());
			String integridadEncriptedString = "";
			if(requestEncripcion.getStatusLine().getStatusCode() == Integer.parseInt(data.get("statusCode"))) {
				JSONObject jsonIntegridad = new JSONObject(EntityUtils.toString(requestEncripcion.getEntity()));
				JSONArray integridadJsonArray = jsonIntegridad.getJSONArray("data");
				System.out.println("Data Array: " + integridadJsonArray);
				integridadEncriptedString = integridadJsonArray.toString().substring(2, integridadJsonArray.toString().length() -2);
				System.out.println("Data: " + integridadEncriptedString);
				//Agregar Request a la evidencia
				testCase.addBoldTextEvidenceCurrentStep("Request:");
				testCase.addBoldTextEvidenceCurrentStep("Metodo: POST");
				testCase.addTextEvidenceCurrentStep(data.get("uriVoltage"));
				testCase.addTextEvidenceCurrentStep(headersVoltage.toString());
				testCase.addTextEvidenceCurrentStep(jsonFormat);
				//Agregar response a la evidencia
				testCase.addBoldTextEvidenceCurrentStep("Response:");
				testCase.addBoldTextEvidenceCurrentStep("Status: " + requestEncripcion.getStatusLine());
				testCase.addBoldTextEvidenceCurrentStep(jsonIntegridad.toString(4));
				
				addStep("Realizar solicitud de autorizacion");
				paramsAuth = pe6Params.runGetAuthParamsIntegridad(folio, creationDate, integridadEncriptedString);
				
			}
				assertEquals(requestEncripcion.getStatusLine().getStatusCode(), Integer.parseInt(data.get("statusCode")), 
						"Se obtiene status diferente a 200");
				
		}else {
			/**********************************Paso 3****************************************/
			addStep("Realizar solicitud de autorizacion");
			paramsAuth = pe6Params.runGetAuthParams(folio, creationDate);
		}
		
		/**********************************Paso 3****************************************/
		
		String uriAuth = data.get("baseUri")+ data.get("getAuth") + paramsAuth;
		System.out.println(uriAuth);
		
//		Response requestGetAuth = api.getRequestWithHeaders(uriAuth, headersToken);
		Response requestGetAuth = api.getRequestMethod(uriAuth);
		
		if (requestGetAuth.getStatusCode() == Integer.parseInt(data.get("statusCode"))) {
			String bodyAuth = requestGetAuth.getBody().asPrettyString();
			System.out.println(bodyAuth);
			String wmCodeAuth = getSimpleDataXml(bodyAuth, "wmCode");
			
			//Agregar Request a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Request:");
			testCase.addBoldTextEvidenceCurrentStep("Metodo: GET");
			testCase.addTextEvidenceCurrentStep(uriAuth);
			//Agregar response a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Response:");
			testCase.addBoldTextEvidenceCurrentStep("Status: " + requestGetAuth.getStatusLine());
			testCase.addBoldTextEvidenceCurrentStep(bodyAuth);
			
			assertEquals(wmCodeAuth, wmCodeToValidate2, "Se obtiene wmCode diferente al esperado");
		}else {
			assertEquals(requestGetAuth.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
		}
		
		/********************************Paso 4*******************************************/
		addStep("Validar que se registre transaccion, en base de datos FCTDCQA");
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
		assertFalse(validateTransaction, "La transaccion no fue realizada correctamente en FCTDCQA");
		
		/********************************Paso 5*******************************************/
		addStep("Validar que se registre transaccion, en base de datos FCSWQA");
		String logFormat = String.format(queryLog, folio);
		System.out.println(logFormat);
		
		SQLResult log = dbFCSWQA.executeQuery(logFormat);
		boolean validateLog = log.isEmpty();
		if(!validateLog) {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCSWQA");
			testCase.addQueryEvidenceCurrentStep(log);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCSWQA");
			testCase.addQueryEvidenceCurrentStep(log);
		}
		assertFalse(validateLog, "La transaccion no fue realizada correctamente en FCSWQA");
		
		/******************************************Paso 6***********************************/
		addStep("Realizar Solicitud de ack");
		
		String paramsAck = pe6Params.runGetAckParams(folio, creationDate);
		String uriAck = data.get("baseUri") + data.get("getAuthAck") + paramsAck;
		System.out.println(uriAck);
		
//		Response requestAck = api.getRequestWithHeaders(uriAck, headersToken);
		Response requestAck = api.getRequestMethod(uriAck);
		
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
		
		/*********************************Paso 7******************************************/
		addStep("Validar que al realizar la consulta el ack_code sea igual a 00");
		String ackCode = "";
		String formatAckCode = String.format(queryAck, folio);
		System.out.println(formatAckCode);
		
		SQLResult transactionAck = dbFCTDCQA.executeQuery(formatAckCode);
		boolean validateTransactionAck = transactionAck.isEmpty();
		if(!validateTransactionAck) {
			ackCode = transactionAck.getData(0, "ACK_CODE");
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCTDCQA");
			testCase.addQueryEvidenceCurrentStep(transactionAck);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCTDCQA");
			testCase.addQueryEvidenceCurrentStep(transactionAck);
		}
		assertEquals(ackCode, ackCodeToValidate, "Se obtiene ACK_CODE diferente al esperado");
		/**********************************Paso 8*****************************************/
		addStep("Validar que al realizar la consulta el sw_code sea igual a 00");
		String swCode = "";
		String formatSwCode = String.format(querySwCode, folio);
		System.out.println(folio);
		
		SQLResult transactionSw = dbFCSWQA.executeQuery(formatSwCode);
		boolean validateTransactionSw = transactionSw.isEmpty();
		if(!validateTransactionSw) {
			swCode = transactionSw.getData(0, "SW_CODE");
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCSWQA");
			testCase.addQueryEvidenceCurrentStep(transactionSw);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCSWQA");
			testCase.addQueryEvidenceCurrentStep(transactionSw);
		}
		assertEquals(swCode, swCodeToValidate, "Se obtiene SW_CODE diferente al esperado");
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
		return "Construida. ATC-FT-14-Validar flujo Exitoso corresponsal�a para SPIN";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose luis Flores";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_019_PE6_FlujoExitosoCorresponsaliaSpin_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
