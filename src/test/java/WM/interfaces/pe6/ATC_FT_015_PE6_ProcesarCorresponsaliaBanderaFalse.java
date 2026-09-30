package WM.interfaces.pe6;

import static WM.util.RequestUtil.getSimpleDataXml;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.PE6;
import WM.util.GlobalVariables;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.sql.SQLResult;

public class ATC_FT_015_PE6_ProcesarCorresponsaliaBanderaFalse extends BaseExecution{
	@Test(dataProvider= "data-provider")
	public void ATC_FT_015_PE6_ProcesarCorresponsaliaBanderaFalse_test (HashMap <String, String> data) throws Exception{
		/*
		 *Utilerias**********************************************************************/
		utils.sql.SQLUtil dbFCSWQA= new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCSWQA_QRO, GlobalVariables.DB_USER_FCSWQA_QRO, GlobalVariables.DB_PASSWORD_FCSWQA_QRO);
		
		PE6 pe6Params = new PE6(data, testCase, null);
		
		/*
		 * Variables****************************************************************/
		
		String queryError = "SELECT FOLIO, ERROR_DATE, ERROR_CODE, DESCRIPTION, MESSAGE\r\n"
				+ "FROM swuser.WM_LOG_SW_ERROR \r\n"
				+ "WHERE ERROR_DATE >= TRUNC(SYSDATE) \r\n"
				+ "AND FOLIO = '%s'";
		
		String wmCodeToValidate = data.get("wmCode");
		String wmCodeToValidate2 = data.get("wmCode1");
		String token = "c431e5ab630322418c1061b3b46f4a5ef1980a430b741c7cca9bf45f477e7adb";
		
		HashMap <String, String> headersToken = new HashMap <String, String>();
		headersToken.put("Authorization", "Bearer " + token);
		
		/*************************************Paso 1********************************/
		addStep("Realizar solicitud de Folio");
		
		String params = pe6Params.runGetFolioParams();
		String uriFolio = data.get("baseUri") + data.get("getFolio") + params;
		System.out.println(uriFolio);
		ApiMethodsUtil api = new ApiMethodsUtil(uriFolio);
		Response requestFolio = api.getRequestWithHeaders(uriFolio, headersToken);
		
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
		
		
		/**************************************Paso 2************************************/
		addStep("Realizar solicitud de autorizacion");
		
		String paramsAuth = pe6Params.runGetAuthParamsIdAutFalse(folio, creationDate);
		String uriAuth = data.get("baseUri") + data.get("getAuth")+ paramsAuth;
		System.out.println(uriAuth);
		Response requestGetAuth = api.getRequestWithHeaders(uriAuth, headersToken);
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
			
			assertEquals(wmCodeAuth, wmCodeToValidate2, "Se obtiene wmCode diferente al esperado");
		}else {
			assertEquals(requestGetAuth.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
		}
		
		/*******************************Paso 3******************************************/
		addStep("Validar en el siguiente query que el registro del error se haya registrado en BD");
		
		String errorFormat = String.format(queryError, folio);
		System.out.println(errorFormat);
		
		SQLResult errorTransaction = dbFCSWQA.executeQuery(errorFormat);
		boolean validateError = errorTransaction.isEmpty();
		if(!validateError) {
			testCase.addBoldTextEvidenceCurrentStep("Site 1:");
			testCase.addQueryEvidenceCurrentStep(errorTransaction);
		}else {
			testCase.addBoldTextEvidenceCurrentStep("Site 1:");
			testCase.addQueryEvidenceCurrentStep(errorTransaction);
		}
		assertTrue(validateError, "Se obtienen registros de error");
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
		return "Construido. ATC-FT-07-Validaci�n Error al procesar Corresponsal�a con bandera False";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose luis Flores";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_015_PE6_ProcesarCorresponsaliaBanderaFalse_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
