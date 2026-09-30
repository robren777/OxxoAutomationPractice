package WM.interfaces.pe6;

import static WM.util.RequestUtil.getSimpleDataXml;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.PE6;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

public class ATC_FT_021_PE6_Transaccion_Fuera_De_Horario extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_PE6_Transaccion_Fuera_De_Horario_test (HashMap <String, String> data) throws Exception{
		/*
		 * Utilileria******************************************************************/
		
		PE6 pe6Params = new PE6(data, testCase, null);
		
		/*
		 * Variales********************************************************************/
		String wmCodeToValidate = data.get("wmCode");
		String wmCodeToValidate2 = data.get("wmCode1");
		
		String token = "c431e5ab630322418c1061b3b46f4a5ef1980a430b741c7cca9bf45f477e7adb";
		
		HashMap <String, String> headersToken = new HashMap <String, String>();
		headersToken.put("Authorization", "Bearer " + token);
		
		/*************************************Paso 1********************************/
		addStep("Realizar solicitud de Folio fuera de horario");
		
		String paramsMap = pe6Params.runGetFolioParams();
		String uriFolio = data.get("baseUri") + data.get("getFolio") + paramsMap;
		System.out.println(uriFolio);
		ApiMethodsUtil api = new ApiMethodsUtil(uriFolio);
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
			
			assertEquals(wmCodeFolio, wmCodeToValidate, "Se obtiene wmCode diferente al esperado");
		}else {
			assertEquals(requestFolio.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
		}
		/**********************************Paso 2****************************************/
		addStep("Realizar solicitud de autorizacion fuera de horario");
		
		
		String paramsAuth = pe6Params.runGetAuthParams(folio, creationDate);
		String uriAuth = data.get("baseUri") + data.get("getAuth") + paramsAuth;
		System.out.println(uriAuth);
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
			
			assertEquals(wmCodeAuth, wmCodeToValidate2, "Se obtiene wmCode diferente al esperado, "
					+ "ya que la transaccion fue realizada dentro de horario");
		}else {
			assertEquals(requestAuth.getStatusCode(), Integer.parseInt(data.get("statusCode")), 
					"Se obtiene status diferente a 200");
		}
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
		return "Construido. ATC-FT-19-Transacci�n fuera de horario";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose luis Flores";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_021_PE6_Transaccion_Fuera_De_Horario_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
