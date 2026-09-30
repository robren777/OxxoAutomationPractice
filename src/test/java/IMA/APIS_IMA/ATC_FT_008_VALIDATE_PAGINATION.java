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
 * MTC-FT-008-VALIDATE PAGINATION 
 * 
 * Caso de prueba para comprobar que la paginacion funciona
 * 
 * @author Guillermo Garcia
 * @date 30/11/2023
 * */

public class ATC_FT_008_VALIDATE_PAGINATION extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_008_VALIDATE_PAGINATION_test(HashMap<String, String> data) throws Exception {
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
		
		/***Paso 1:- Ingresar la URL de la columna "datos de prueba" dentro de la petición en POSTMAN y seleccionar el Método GET*****/
		addStep("Ingresar la URL de la columna \"datos de prueba\" dentro de la petición en POSTMAN y seleccionar el Método GET");		
		testCase.addCodeEvidenceCurrentStep("URL:" + baseURL + path);
		//Hacer la llamada a la api agregandor la api key
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("x-functions-key", utilIMA.getKeyApiIma());
		testCase.addCodeEvidenceCurrentStep("Key:" + utilIMA.getKeyApiIma());	
		
		/*************************************** PASO 2 Ingresar la fecha en el valor Updated date ***************************************/
		addStep("Ingresar la fecha en el valor Updated date");
		testCase.addCodeEvidenceCurrentStep("updatedDate:" + utilIMA.getYesterdayDate());
		 		 
		/*************************************** PASO 3 Ingresar la pagina en el valor page ***************************************/
		 addStep("Ingresar la pagina en el valor page");
		 testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		/*************************************** PASO 4 Ingresar el limite en el valor Limit ***************************************/
		 addStep("Ingresar el limite en el valor Limit");	
		 testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		 
		/*************************************** PASO 5 Hacer clic en el botón "SEND" ***************************************/
		 String pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 
		 addStep("Hacer clic en el botón \"SEND\"");
		 Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);	
		 
		/*************************************** PASO 6 Validar el status code ***************************************/
		 addStep("Validar el status code");
		 int statusCode = response.getStatusCode();		 
		 assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		 
		/***Paso 7:- ? Valida los keys and values del array "metadata"*****/
		 addStep("Paso 7:- ? Valida los keys and values del array \"metadata\"");
		 //obtener el body
		 responseBody = new JsonUtil(response.getBody().asPrettyString());
		 //obtener el Json del metadata
		 metadata = responseBody.getJsonOElement("metadata");
		 //validar key page
		 assertEquals(metadata.getInt("page"), Integer.parseInt(page),  "primera validacion de page");
		 testCase.addCodeEvidenceCurrentStep("Page esperado:" + page + " Page real:" + metadata.getInt("page") );
		 //validar key limit		 
		 assertEquals(metadata.getInt("limit"), Integer.parseInt(limit),  "primera validacion de limit");
		 testCase.addCodeEvidenceCurrentStep("Limit esperado:" + limit + " Limit real:" + metadata.getInt("limit") );
		 
		/***Paso 8:-Cambia el siguiente valor en "page": 2*****/
		 addStep(" Paso 8:-Cambia el siguiente valor en \"page\": 2");
		 page = "2";
		 testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		/***Paso 9:- Hacer clic en el botón "SEND"*****/
		 addStep("Paso 9:- Hacer clic en el botón \"SEND\" ");
		 pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 		 
		 response = api.getRequestWithHeaders(pathWithQueryParams, headersList);	
		 
		/***Paso 10:-? Validar el status code *****/
		 addStep("Paso 10:-? Validar el status code ");
		 assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		 
		/***Paso 11:- ? Valida los keys and values del array "metadata"*****/
		 addStep("Paso 11:- ? Valida los keys and values del array \"metadata\" ");
		 //obtener el body
		 responseBody = new JsonUtil(response.getBody().asPrettyString());
		 //obtener el Json del metadata
		 metadata = responseBody.getJsonOElement("metadata");
		 //validar key page
		 assertEquals(metadata.getInt("page"), Integer.parseInt(page),  "primera validacion de page");
		 testCase.addCodeEvidenceCurrentStep("Page esperado:" + page + " Page real:" + metadata.getInt("page") );
		 //validar key limit		 
		 assertEquals(metadata.getInt("limit"), Integer.parseInt(limit),  "primera validacion de limit");
		 testCase.addCodeEvidenceCurrentStep("Limit esperado:" + limit + " Limit real:" + metadata.getInt("limit") );
		 
		/***Paso 12:-Cambia el siguiente valor en "page": 1*****/
		 addStep("Paso 12:-Cambia el siguiente valor en \"page\": 1 ");
		 page = "1";
		 testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		/***Paso 13:-Cambia el siguiente valor en "limit": 1*****/
		 addStep("Paso 13:-Cambia el siguiente valor en \"limit\": 2 ");
		 limit = "2";
		 testCase.addCodeEvidenceCurrentStep("limit:" + limit);
		 
		/***Paso 14:- ? Valida los keys and values del array "metadata"*****/
		 addStep("Paso 14:- ? Valida los keys and values del array \"metadata\" ");		 
		 pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 
		 
		 response = api.getRequestWithHeaders(pathWithQueryParams, headersList);	
		 //obtener el body
		 responseBody = new JsonUtil(response.getBody().asPrettyString());
		 //obtener el Json del metadata
		 metadata = responseBody.getJsonOElement("metadata");
		 //validar key page
		 assertEquals(metadata.getInt("page"), Integer.parseInt(page),  "primera validacion de page");
		 testCase.addCodeEvidenceCurrentStep("Page esperado:" + page + " Page real:" + metadata.getInt("page") );
		 //validar key limit		 
		 assertEquals(metadata.getInt("limit"), Integer.parseInt(limit),  "primera validacion de limit");
		 testCase.addCodeEvidenceCurrentStep("Limit esperado:" + limit + " Limit real:" + metadata.getInt("limit") );
		 
		/***Paso 15:-Cambia el siguiente valor en "page": 2*****/
		 addStep(" Paso 15:-Cambia el siguiente valor en \"page\": 2");
		 page = "2";
		 testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		/***Paso 16:-Cambia el siguiente valor en "limit": 1*****/
		 addStep("Paso 16:-Cambia el siguiente valor en \"limit\": 1 ");
		 limit = "1";
		 testCase.addCodeEvidenceCurrentStep("limit:" + limit);
		 
		/***Paso 17:- ? Valida los keys and values del array "metadata"*****/
		 addStep("Paso 17:- ? Valida los keys and values del array \"metadata\" ");
		 pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 
		 
		 response = api.getRequestWithHeaders(pathWithQueryParams, headersList);	
		 //obtener el body
		 responseBody = new JsonUtil(response.getBody().asPrettyString());
		 //obtener el Json del metadata
		 metadata = responseBody.getJsonOElement("metadata");
		 //validar key page
		 assertEquals(metadata.getInt("page"), Integer.parseInt(page),  "primera validacion de page");
		 testCase.addCodeEvidenceCurrentStep("Page esperado:" + page + " Page real:" + metadata.getInt("page") );
		 //validar key limit		 
		 assertEquals(metadata.getInt("limit"), Integer.parseInt(limit),  "primera validacion de limit");
		 testCase.addCodeEvidenceCurrentStep("Limit esperado:" + limit + " Limit real:" + metadata.getInt("limit") );

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_008_VALIDATE_PAGINATION";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba para comprobar que la paginacion funciona";
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
