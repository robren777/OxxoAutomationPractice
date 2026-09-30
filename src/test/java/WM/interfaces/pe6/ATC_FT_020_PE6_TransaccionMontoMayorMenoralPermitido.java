package WM.interfaces.pe6;

import static WM.util.RequestUtil.getSimpleDataXml;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.PE6;
import WM.util.GlobalVariables;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.sql.SQLResult;

public class ATC_FT_020_PE6_TransaccionMontoMayorMenoralPermitido extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_020_PE6_TransaccionMontoMayorMenoralPermitido_test (HashMap <String, String> data) throws Exception {
		/*
		 * Utileria**************************************************************/
		utils.sql.SQLUtil dbFCTDCQA= new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCTDCQA_QRO,GlobalVariables.DB_USER_FCTDCQA_QRO, GlobalVariables.DB_PASSWORD_FCTDCQA_QRO);
		
		PE6 pe6Params = new PE6(data, testCase, null);
		
		/*
		 * Variables*************************************************************/
		String queryTransaction = "SELECT FOLIO, CREATION_DATE, PLAZA, TIENDA, PV_DATE, ADMIN_DATE, BANK\r\n"
				+ "FROM TPEUSER.TDC_TRANSACTION \r\n"
				+ "WHERE CREATION_DATE = TRUNC(SYSDATE)  \r\n"
				+ "AND FOLIO = '%s'";
		
		String wmCodeToValidate = data.get("wmCode");
		String wmCodeToValidate2 = data.get("wmCode1");
		String token = "c431e5ab630322418c1061b3b46f4a5ef1980a430b741c7cca9bf45f477e7adb";
		
		HashMap <String, String> headersToken = new HashMap <String, String>();
		headersToken.put("Authorization", "Bearer " + token);
		
		testCase.setTest_Description(data.get("testDescription"));
		/*************************************Paso 1********************************/
		addStep("Realizar solicitud de Folio");
		
		String paramsMap = pe6Params.runGetFolioParams();
		String uriFolio = data.get("baseUri") + data.get("folio") + paramsMap;
		ApiMethodsUtil api = new ApiMethodsUtil(uriFolio);
		
//		Response requestFolio = api.getRequestWithHeaders(uriFolio, headersToken);
		Response requestFolio = api.getRequestMethod(uriFolio);
		
		String folio = "";
		String wmCodeFolio = "";
		String creationDate = "";
		if(requestFolio.getStatusCode() == Integer.parseInt(data.get("statusCode"))) {
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
			
			assertEquals(wmCodeFolio, wmCodeToValidate, "Se obtiene wmCode diferente al esperado");
		}else {
			assertEquals(requestFolio.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
		} 
		/**********************************Paso 2****************************************/
		addStep("Realizar solicitud de autorizacion");
		
		String paramsAuth = pe6Params.runGetAuthParams(folio, creationDate);
		String uriAuth = data.get("baseUri") + data.get("getAuth") + paramsAuth;
		
//		Response requestAuth = api.getRequestWithHeaders(uriAuth, headersToken);
		Response requestAuth = api.getRequestMethod(uriAuth);
		
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
			
			assertEquals(wmCodeAuth, wmCodeToValidate2, "Se obtiene wmCode diferente al esperado");
		}else {
			assertEquals(requestAuth.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
		}
		/**********************************Paso 3********************************/
		addStep("Validar que se obtenta wmCode 117 por monto mayor y wmCode 1122 por monto minimo");
		
		String wmCode ="";
		String transactionFormat = String.format(queryTransaction, folio);
		System.out.println(transactionFormat);
		
		SQLResult transaction = dbFCTDCQA.executeQuery(transactionFormat);
		boolean validateTransaction = transaction.isEmpty();
		if(!validateTransaction) {
			wmCode = transaction.getData(0, "WM_CODE");
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCTDCQA");
			testCase.addQueryEvidenceCurrentStep(transaction);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("Consulta en: FCTDCQA");
			testCase.addQueryEvidenceCurrentStep(transaction);
		}
		assertEquals(wmCode, data.get("wmCode"), "Se obtiene wmCode diferente al esperado");
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
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose luis Flores";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_020_PE6_TransaccionMontoMayorMenoralPermitido_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
