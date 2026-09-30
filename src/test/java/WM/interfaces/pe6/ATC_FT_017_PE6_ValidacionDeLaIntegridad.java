package WM.interfaces.pe6;

import static WM.util.RequestUtil.getSimpleDataXml;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import WM.om.PE6;
import WM.util.ApiUtil;
import WM.util.GlobalVariables;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.sql.SQLResult;

public class ATC_FT_017_PE6_ValidacionDeLaIntegridad extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_017_PE6_ValidacionDeLaIntegridad_test (HashMap <String, String> data) throws Exception{
		/*
		 **Utileria********************************************************************/
		utils.sql.SQLUtil dbFCTDCQA= new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTDCQA_QRO,GlobalVariables.DB_USER_FCTDCQA_QRO, GlobalVariables.DB_PASSWORD_FCTDCQA_QRO);
		utils.sql.SQLUtil dbFCSWQA= new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCSWQA_QRO, GlobalVariables.DB_USER_FCSWQA_QRO, GlobalVariables.DB_PASSWORD_FCSWQA_QRO);
		
		PE6 pe6Params = new PE6(data, testCase, null);
		
		/*
		 **Variables*******************************************************************/
		String queryTransaction = "SELECT FOLIO, CREATION_DATE, PLAZA, TIENDA, CAJA, PV_DATE, ADMIN_DATE, BIN, BANK, AMOUNT, INTEGRIDAD\r\n"
				+ "FROM TPEUSER.TDC_TRANSACTION\r\n"
				+ "WHERE CREATION_DATE >=TRUNC(SYSDATE) \r\n"
				+ "AND FOLIO = '%s'";
		
		String queryLog = "SELECT PLAZA, TIENDA, CAJA, FOLIO, AMOUNT, INTEGRIDAD\r\n"
				+ "FROM swuser.TPE_SW_TLOG\r\n"
				+ "WHERE CREATION_DATE >= TRUNC(SYSDATE) \r\n"
				+ "AND APPLICATION='TDC' \r\n"
				+ "AND FOLIO= '%s'";
		
		String wmCodeToValidate = data.get("wmCode");
		String wmCodeToValidate2 = data.get("wmCode1");
		
		String token = "c431e5ab630322418c1061b3b46f4a5ef1980a430b741c7cca9bf45f477e7adb";
		
		HashMap <String, String> headersToken = new HashMap <String, String>();
		headersToken.put("Authorization", "Bearer " + token);
		
		SoftAssert softAssert = new SoftAssert();
		/*************************************Paso 1********************************/
		addStep("Realizar solicitud de Folio");
		
//		String getFolioPath = data.get("getFolio");
		String params = pe6Params.runGetFolioParams();
		String uriFolio = data.get("baseUri") + data.get("getFolio") + params;
		System.out.println(uriFolio);
		ApiMethodsUtil api = new ApiMethodsUtil(uriFolio);
		String folio = "";
		String wmCodeFolio = "";
		String creationDate = "";
		
//		Response requestFolio = api.getRequestWithHeaders(uriFolio, headersToken);
		Response requestFolio = api.getRequestMethod(uriFolio);
		
		if (requestFolio.getStatusCode() == Integer.parseInt(data.get("statusCode"))) {
			String bodyFolio = requestFolio.getBody().asPrettyString();
			System.out.println(bodyFolio);
			folio = getSimpleDataXml(bodyFolio, "folio");
			wmCodeFolio = getSimpleDataXml(bodyFolio, "wmCode");
			creationDate = getSimpleDataXml(bodyFolio, "creationDate");
			//Agregar Request a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Request:");
			testCase.addBoldTextEvidenceCurrentStep("Metodo: GET");
			testCase.addTextEvidenceCurrentStep(uriFolio);
			//Agregar response a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Response:");
			testCase.addBoldTextEvidenceCurrentStep("Status: " + requestFolio.getStatusLine());
			testCase.addBoldTextEvidenceCurrentStep(bodyFolio);
			
			softAssert.assertEquals(wmCodeFolio, wmCodeToValidate, "Se obtiene wmCode diferente al esperado");
		}else {
			assertEquals(requestFolio.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
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
		
		String uriAuth = data.get("baseUri") + data.get("getAuth") + paramsAuth;
		System.out.println(uriAuth);
//		Response requestGetAuth = api.getRequestWithHeaders(uriAuth, headersToken);
		Response requestGetAuth = api.getRequestMethod(uriAuth);
		
		String wmCodeAuth = "";
		if (requestGetAuth.getStatusCode() == Integer.parseInt(data.get("statusCode"))) {
			String bodyAuth = requestGetAuth.getBody().asPrettyString();
			System.out.println(bodyAuth);
			wmCodeAuth = getSimpleDataXml(bodyAuth, "wmCode");
			
			//Agregar Request a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Request:");
			testCase.addBoldTextEvidenceCurrentStep("Metodo: GET");
			testCase.addTextEvidenceCurrentStep(uriAuth);
			//Agregar response a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Response:");
			testCase.addBoldTextEvidenceCurrentStep("Status: " + requestGetAuth.getStatusLine());
			testCase.addBoldTextEvidenceCurrentStep(bodyAuth);
			
			softAssert.assertEquals(wmCodeAuth, wmCodeToValidate2, "Se obtiene wmCode diferente al esperado");
		}else {
			assertEquals(requestGetAuth.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
		}
		/************************************Paso 4***********************************/
		addStep("Comparar  las consultas y validar que el registro  de Integridad, monto, fecha, folio, como el n�mero de tarjeta sean iguales");
		
		String transactionFormat = String.format(queryTransaction, folio);
		String logFormat = String.format(queryLog, folio);
		System.out.println(transactionFormat);
		System.out.println(logFormat);
		
		SQLResult transaction = dbFCTDCQA.executeQuery(transactionFormat);
		Thread.sleep(10000);
		SQLResult log = dbFCSWQA.executeQuery(logFormat);
		boolean validateTransaction = transaction.isEmpty();
		if(!validateTransaction) {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCTDCQA");
			testCase.addQueryEvidenceCurrentStep(transaction);
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCSWQA");
			testCase.addQueryEvidenceCurrentStep(log);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCTDCQA");
			testCase.addQueryEvidenceCurrentStep(transaction);
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCSWQA");
			testCase.addQueryEvidenceCurrentStep(log);
		}
		//Pendiente validacion de datos
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
		return "Construido, MTC-FT-09-Validaci�n de la integridad (compara 1er hash con el 2do Hash";
	}
	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose luis Flores";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_017_PE6_ValidacionDeLaIntegridad_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
