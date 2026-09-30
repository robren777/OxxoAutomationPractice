package WM.interfaces.ENRED_OUT;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

public class ATC_014_ValidarOrdenamientoDeDocumentosApi_LISP_WMCode101 extends BaseExecution{
@Test
(dataProvider = "data-provider")
	
public void ATC_014_ValidarOrdenamientoDeDocumentosApi_LISP_WMCode101_test (HashMap<String, String> data) throws Exception {
		/**
		 * Transformaci�n Multiformato: ATC_014_ValidarOrdenamientoDeDocumentosApi_LISP_WMCode101
		 * Validar ordenamiento de documentos
		 * Desc: La API responde con la lista de documentos pendientes para esa tienda
		 * Luis G. Ramirez Martinez
		 * 08/03/2024
		 */
	/**
	 * Prerequisitos: 
	 * Tener accceso a la la VPN de Oxxo.
	 * Tener documentos generados.
	 * Tener acceso a la Base de Datos WM_OUT_DOC_LOG (oxfwm6q00.femcom.net, 1535, FCWMLQA.FEMCOM.NET). 
	 * Tener acceso a la Base de Datos POS_OUT_DOC_RMS (oxfwm6q00.femcom.net, 1535, FCWM6QA.FEMCOM.NET). 
	 */
String baseURLToken = "https://ipaas-stg.oxxo.io";
	
	ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);
	//Obtener datos del data provider
	String getPathToken = data.get("getPathToken");
	int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));		

	//Ejecuta request
	RestAssured.baseURI = baseURLToken;
	
	//Lista de headers
	HashMap<String, String> headersListToken = new HashMap<>();
	headersListToken.put("Accept", "*/*");
	headersListToken.put("Authorization", "Basic ZTAxNDA4Mjc3NWIyNDQ2NGE1NDcxMDBjMzA3ZjQ1OTE6ZjU4MmZlMDM0YWZhNDlkZDgwMzM1NWQ3N2JlMjE3YmM=");

	String reqBodyToken = "";
		
	//Hacemos el send del request
	Response getRespuesta = api.postRequestWithHeaders(baseURLToken+getPathToken, reqBodyToken, headersListToken);
	
	//Obtener body de respuesta
	String getBodyResponse = getRespuesta.getBody().asString();		
	
	 //Validaci�n de JSON
	JsonPath getJson = new JsonPath(getBodyResponse);
	String access_token = getJson.get("access_token");

	//Obtener el status code
	int getStatusCode = getRespuesta.getStatusCode();	

	
	System.out.println("statusCode: "+getStatusCode);
	System.out.println("body response: "+getBodyResponse);
	System.out.println("Access Token: "+access_token);

	System.out.println(" ------------------------------------------------------------------------------- ");
	addStep("Invocar la API LISP");	
	
	/// Llamada al API LISP usando el token generado	
	String baseURILisp = "https://ipaas-stg.oxxo.io";
	String getPathLisp = data.get("getPathLisp");
	String departmentId = data.get("departmentId");
	String storeId = data.get("storeId");
	String source = data.get("source");	
	
	//Se env�a como evidencia el request a ejecutar
	testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURILisp+getPathLisp);
		
	RestAssured.baseURI = baseURILisp;	
	String reqBody = "{"
			+ "\"departmentId\":\""+departmentId+"\","
			+ "\"storeId\":\""+storeId+"\","
			+ "\"source\":\""+source+"\""
	+ "}";
	
	//Lista de headers
	HashMap<String, String> headersList = new HashMap<>();
	headersList.put("Accept", "*/*");
	headersList.put("content-type", "application/json");
	headersList.put("Authorization", "Bearer "+access_token);

	//Hacemos el send del request
	Response getRespuesta2 = api.postRequestWithHeaders(baseURILisp+getPathLisp, reqBody, headersList);
									
	addStep("Validar que la lista desplegada este en orden cronologico.");
	//Obtener body de respuesta
	String getBodyResponse2 = getRespuesta2.getBody().asPrettyString();
	testCase.addTextEvidenceCurrentStep("Respuesta: \n" +getBodyResponse2);
		
	System.out.println("Body: "+getBodyResponse2);
		
	JsonPath getJson2 = new JsonPath(getBodyResponse2);
	
	//VALIDACION DE CODE
	int codeResp = Integer.parseInt(getJson2.getString("code")); 
	assertEquals(codeResp, getExpectedCode); //Validacion
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Validar ordenamiento de documentos api LISP WMCode 101";
	}

	@Override
	public String setTestDesigner() {
		return "Luis Ramirez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_014_ValidarOrdenamientoDeDocumentosApi_LISP_WMCode101";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
