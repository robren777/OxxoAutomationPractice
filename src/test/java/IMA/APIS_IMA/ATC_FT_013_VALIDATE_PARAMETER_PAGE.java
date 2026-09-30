package IMA.APIS_IMA;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import IMA.OM_API.OM_API;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.JsonUtil;


/*
 * MTC-FT-013-VALIDATE PARAMETER PAGE 
 * 
 * Se prueba que el parametro page funcione correctamente, validando el cambio de paginas, el calculo de las mismas.
 * 
 * @author Guillermo Garcia
 * @date 30/11/2023
 * 
 * */

public class ATC_FT_013_VALIDATE_PARAMETER_PAGE extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_013_VALIDATE_PARAMETER_PAGE_test(HashMap<String, String> data) throws Exception {
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
		JSONArray collaboratorFirstPage;
		JSONArray collaboratorSecondPage;
		
		/*******************Paso 1:- Ingresar la URL de la columna ""datos de prueba"" dentro de la petición en POSTMAN y seleccionar el Método GET***********************************/
		 addStep("Ingresar la URL de la columna \"\"datos de prueba\"\" dentro de la petición en POSTMAN y seleccionar el Método GET ");
		testCase.addCodeEvidenceCurrentStep("URL:" + baseURL + path);
		//Hacer la llamada a la api agregandor la api key
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("x-functions-key", utilIMA.getKeyApiIma());
		testCase.addCodeEvidenceCurrentStep("Key:" + utilIMA.getKeyApiIma());	
		
		/*******************Paso 2:-Ingresa el siguiente valor en "UpdatedDate": 'agrega el dia de ayer'***********************************/
		 addStep(" Ingresa el siguiente valor en \"UpdatedDate\": 'agrega el dia de ayer'");
		 testCase.addCodeEvidenceCurrentStep("updatedDate:" + utilIMA.getYesterdayDate());
		 
		/*******************Paso 3:-Ingresa el siguiente valor en "page": 1***********************************/
		 addStep("Ingresa el siguiente valor en \"page\": 1 ");
		 testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		/*******************Paso 4:-Ingresa el siguiente valor en "limit":1***********************************/
		 addStep("Ingresa el siguiente valor en \"limit\":1 ");
		 testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		 
		/*******************Paso 5:- Hacer clic en el botón "SEND"***********************************/
		 addStep("Hacer clic en el botón \"SEND\" ");
		 String pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 		
		 Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		 
		/*******************Paso 6:-? Validar el status code ***********************************/
		 addStep("Validar el status code ");
		 int statusCode = response.getStatusCode();		 
		 assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		 
		/*******************Paso 7:- ? Valida los keys and values del array "metadata"***********************************/
		 addStep("Valida los keys and values del array \"metadata\" ");
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
		 
		/*******************Paso 8:-? Valida si total records es mayor que dos ***********************************/
		 addStep("Valida si total records es mayor que dos ");
		 //obtener y validar si el total records es mayor que dos
		 assertTrue(metadata.getInt("totalRecords")>2,  "validacion total records mayor que dos");
		 testCase.addCodeEvidenceCurrentStep("Total Records:" + metadata.getInt("totalRecords") );
		 
		/*******************Paso 9:- Guarda el tipo de usuario desplegado en "Collaborators"***********************************/
		 addStep("Guarda el tipo de usuario desplegado en \"Collaborators\"");
		 collaboratorFirstPage = responseBody.getJsonAElement("collaborators");
		 testCase.addCodeEvidenceCurrentStep("Collaborator page 1: " + collaboratorFirstPage );
		 
		/*******************Paso 10:- Ingresa el siguiente valor en "page": 20***********************************/
		 addStep("Ingresa el siguiente valor en \"page\": 20 ");
		 page = "20";
		 testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		/*******************Paso 11:- Hacer clic en el botón "SEND"***********************************/
		 addStep("Hacer clic en el botón \"SEND\" ");
		 pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 		
		 response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		 
		/*******************Paso 12:-? Validar el status code ***********************************/
		 addStep("Validar el status code ");
		 statusCode = response.getStatusCode();		 
		 assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		 
		/*******************Paso 13:- ? Valida los keys and values del array "metadata"***********************************/
		 addStep("Valida los keys and values del array \"metadata\" ");
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
		 
		/*******************Paso 14:-? Valida que la data en collaborators es diferente a la previamente guardada"***********************************/
		 addStep("Valida que la data en collaborators es diferente a la previamente guardada\" ");
		 //se obtiene el collaborator de la segunda pagina
		 collaboratorSecondPage = responseBody.getJsonAElement("collaborators");
		 //se comparan los dos collaborators
		 boolean statusArrays = !collaboratorFirstPage.equals(collaboratorSecondPage);
		 assertTrue(statusArrays,"Se valida si los collaboradores son iguales");
		 testCase.addCodeEvidenceCurrentStep("Collab first page: " + collaboratorFirstPage);
		 testCase.addCodeEvidenceCurrentStep("Collab second page: " + collaboratorSecondPage);
		 
		/*******************Paso 15:-? Valida que el valor de "totalRecords" y "totalPages" es el mismo***********************************/
		 addStep("Valida que el valor de \"totalRecords\" y \"totalPages\" es el mismo ");
		 assertEquals(metadata.getInt("totalPages"), metadata.getInt("totalRecords"), "Validando el numero de records y pages cuando el limit es 1");
		 testCase.addCodeEvidenceCurrentStep("El valor de totalPages es: " + metadata.getInt("totalPages") + ", El valor de totalRecords es: " + metadata.getInt("totalRecords"));
		 
		/*******************Paso 16:-Ingresa el siguiente valor en "limit":2***********************************/
		 addStep("Ingresa el siguiente valor en \"limit\":2 ");
		 limit = "2";
		 testCase.addCodeEvidenceCurrentStep("Limit:" + limit);		 
		 
		/*******************Paso 17:- Hacer clic en el botón "SEND"***********************************/
		 addStep("Hacer clic en el botón \"SEND\" ");
		 pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 		
		 response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		 
		/*******************Paso 18:-? Validar el status code ***********************************/
		 addStep("Validar el status code  ");
		 statusCode = response.getStatusCode();		 
		 assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		 
		/*******************Paso 19:- ? Valida los keys and values del array "metadata"***********************************/
		 addStep("Valida los keys and values del array \"metadata\" ");
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
		 
		/*******************Paso 20:-? Valida que el valor de "totalRecords" y "totalPages" es el mismo(no debe ser el mismo)***********************************/
		 addStep("Valida que el valor de \"totalRecords\" y \"totalPages\" es el mismo ");
		 int totalRecords = metadata.getInt("totalRecords");
		 int totalPages = metadata.getInt("totalPages");
		 boolean status = (((totalRecords/2)==totalPages) || ((totalRecords/2)+1==(totalPages))) ? true : false;
		 assertTrue(status,"Se valida que el numero de paginas sea igual a la mitad de los records o mitad de los records mas uno");
		 testCase.addCodeEvidenceCurrentStep("El valor de totalPages es: " + metadata.getInt("totalPages") + ", El valor de totalRecords es: " + metadata.getInt("totalRecords"));
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_013_VALIDATE_PARAMETER_PAGE";
	}

	@Override
	public String setTestDescription() {
		return "Se prueba que el parametro page funcione correctamente, validando el cambio de paginas, el calculo de las mismas.";
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
