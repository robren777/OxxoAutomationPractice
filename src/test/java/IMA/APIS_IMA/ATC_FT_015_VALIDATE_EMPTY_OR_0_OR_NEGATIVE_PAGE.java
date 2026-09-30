package IMA.APIS_IMA;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import IMA.OM_API.OM_API;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.JsonUtil;


/*
 * MTC-FT-015-VALIDATE EMPTY OR 0 OR NEGATIVE PAGE  
 * 
 * Prueba donde se valida enviar un valor null, 0 y negativo en el parametro page
 * 
 * @author Guillermo Garcia
 * @date 30/11/2023
 * */

public class ATC_FT_015_VALIDATE_EMPTY_OR_0_OR_NEGATIVE_PAGE extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_015_VALIDATE_EMPTY_OR_0_OR_NEGATIVE_PAGE_test(HashMap<String, String> data) throws Exception {
		/*****************************Variables Data Provider**************************************/
		String baseURL = data.get("getUrl");
		String path = data.get("getPath");
		int expectedStatusCode =  Integer.parseInt(data.get("getExpectedCode"));
		String limit = data.get("getLimit");
		String page = data.get("getPage");
				
		/*****************************Instancias **************************************************/
		OM_API utilIMA = new OM_API();
		ApiMethodsUtil api =  new ApiMethodsUtil(baseURL);		
		JsonUtil responseBody;
		JSONObject metadata;
		int defaultPage = 1;
			
		/**********Paso 1:- Ingresar la URL de la columna "datos de prueba" dentro de la petición en POSTMAN y seleccionar el Método GET**************************/
		addStep("Ingresar la URL de la columna \"\"datos de prueba\"\" dentro de la petición en POSTMAN y seleccionar el Método GET ");
		testCase.addCodeEvidenceCurrentStep("URL:" + baseURL + path);
		//Hacer la llamada a la api agregandor la api key
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("x-functions-key", utilIMA.getKeyApiIma());
		testCase.addCodeEvidenceCurrentStep("Key:" + utilIMA.getKeyApiIma());	
		
		/**********Paso 2:-Ingresa el siguiente valor en "UpdatedDate": 'agrega el dia de ayer'**************************/
		addStep(" Ingresa el siguiente valor en \"UpdatedDate\": 'agrega el dia de ayer'");
		testCase.addCodeEvidenceCurrentStep("updatedDate:" + utilIMA.getYesterdayDate());
		
		/**********Paso 3:-Ingresa el siguiente valor en "page": **************************/
		addStep("Ingresa el siguiente valor en \"page\":  ");
		page = "";
		testCase.addCodeEvidenceCurrentStep("page:" + page);
		
		/**********Paso 4:-Ingresa el siguiente valor en "limit":1**************************/
		addStep("Ingresa el siguiente valor en \"limit\":1 ");
		testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		
		/**********Paso 5:- Hacer clic en el botón "SEND"**************************/
		addStep("Hacer clic en el botón \"SEND\" ");
		String pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 		
		Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		
		/**********Paso 6:-? Validar el status code **************************/
		addStep("Validar el status code ");
		int statusCode = response.getStatusCode();		 
		assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		
		/**********Paso 7:- ? Valida los keys and values del array "metadata"**************************/
		addStep("Valida los keys and values del array \"metadata\" ");
		//obtener el body
		responseBody = new JsonUtil(response.getBody().asPrettyString());
		//obtener el Json del metadata
		metadata = responseBody.getJsonOElement("metadata");
		//validar key page
		assertEquals(metadata.getInt("page"), defaultPage,  "validacion de page en page null");
		testCase.addCodeEvidenceCurrentStep("Page esperado:" + defaultPage + " Page real:" + metadata.getInt("page") );
		//validar key limit		 
		assertEquals(metadata.getInt("limit"), Integer.parseInt(limit),  "primera validacion de limit");
		testCase.addCodeEvidenceCurrentStep("Limit esperado:" + limit + " Limit real:" + metadata.getInt("limit") );
		 
		/**********Paso 8:-Ingresa el siguiente valor en "page": 0**************************/
		addStep("Ingresa el siguiente valor en \"page\": 0  ");
		page = "0";
		testCase.addCodeEvidenceCurrentStep("page:" + page);
		
		/**********Paso 9:- Hacer clic en el botón "SEND"**************************/
		addStep("Hacer clic en el botón \"SEND\" ");
		pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 		
		response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		
		/**********Paso 10:-? Validar el status code **************************/
		addStep("Validar el status code ");
		statusCode = response.getStatusCode();		 
		assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		
		/**********Paso 11:- ? Valida los keys and values del array "metadata"**************************/
		addStep("Valida los keys and values del array \"metadata\" ");
		//obtener el body
		responseBody = new JsonUtil(response.getBody().asPrettyString());
		//obtener el Json del metadata
		metadata = responseBody.getJsonOElement("metadata");
		//validar key page
		assertEquals(metadata.getInt("page"), defaultPage,  "validacion de page cuando page = 0");
		testCase.addCodeEvidenceCurrentStep("Page esperado:" + defaultPage + " Page real:" + metadata.getInt("page") );
		//validar key limit		 
		assertEquals(metadata.getInt("limit"), Integer.parseInt(limit),  "primera validacion de limit");
		testCase.addCodeEvidenceCurrentStep("Limit esperado:" + limit + " Limit real:" + metadata.getInt("limit") );
		
		/**********Paso 12:-Ingresa el siguiente valor en "page": -10**************************/
		addStep("Ingresa el siguiente valor en \"page\": -10  ");
		page = "-10";
		testCase.addCodeEvidenceCurrentStep("page:" + page);
		
		/**********Paso 13:- Hacer clic en el botón "SEND"**************************/
		addStep("Hacer clic en el botón \"SEND\" ");
		pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 		
		response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		
		/**********Paso 14:-? Validar el status code **************************/
		addStep("Validar el status code ");
		statusCode = response.getStatusCode();		 
		assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		
		/**********Paso 15:- ? Valida los keys and values del array "metadata"**************************/
		addStep("Valida los keys and values del array \"metadata\" ");
		//obtener el body
		responseBody = new JsonUtil(response.getBody().asPrettyString());
		//obtener el Json del metadata
		metadata = responseBody.getJsonOElement("metadata");
		//validar key page
		assertEquals(metadata.getInt("page"), defaultPage,  "validacion page negativa");
		testCase.addCodeEvidenceCurrentStep("Page esperado:" + defaultPage + " Page real:" + metadata.getInt("page") );
		//validar key limit		 
		assertEquals(metadata.getInt("limit"), Integer.parseInt(limit),  "primera validacion de limit");
		testCase.addCodeEvidenceCurrentStep("Limit esperado:" + limit + " Limit real:" + metadata.getInt("limit") );

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_015_VALIDATE_EMPTY_OR_0_OR_NEGATIVE_PAGE";
	}

	@Override
	public String setTestDescription() {
		return "Prueba donde se valida enviar un valor null, 0 y negativo en el parametro page";
	}

	@Override
	public String setTestDesigner() {
		return "Guillermo A. Garcia G.";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Contar con la configuracion correcta del proyecto(validar el POM)";
	}

	@Override
	public void beforeTest() {
	}

	

}
