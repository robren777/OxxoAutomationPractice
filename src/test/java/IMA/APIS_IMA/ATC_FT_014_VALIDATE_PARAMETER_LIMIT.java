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
 * MTC-FT-014-VALIDATE PARAMETER LIMIT 
 * 
 * Se prueba que el parametro limit funcione correctamente, validando el cambio de limite, que regrese los usuarios correctos por paginas tanto completas como incompletas.
 * 
 * @author Guillermo Garcia
 * @date 30/11/2023
 * */

public class ATC_FT_014_VALIDATE_PARAMETER_LIMIT extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_014_VALIDATE_PARAMETER_LIMIT_test(HashMap<String, String> data) throws Exception {
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
		JSONArray collaborator;		
		
		/***********Paso 1:- Ingresar la URL de la columna "datos de prueba" dentro de la petición en POSTMAN y seleccionar el Método GET***************************/
		addStep("Ingresar la URL de la columna \"\"datos de prueba\"\" dentro de la petición en POSTMAN y seleccionar el Método GET ");
		testCase.addCodeEvidenceCurrentStep("URL:" + baseURL + path);
		//Hacer la llamada a la api agregandor la api key
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("x-functions-key", utilIMA.getKeyApiIma());
		testCase.addCodeEvidenceCurrentStep("Key:" + utilIMA.getKeyApiIma());	
		
		/***********Paso 2:-Ingresa el siguiente valor en "UpdatedDate": 'agrega el dia de ayer'***************************/
		 addStep(" Ingresa el siguiente valor en \"UpdatedDate\": 'agrega el dia de ayer'");
		 testCase.addCodeEvidenceCurrentStep("updatedDate:" + utilIMA.getYesterdayDate());
		 
		 /***********Paso 3:-Ingresa el siguiente valor en "page": 1***************************/
		 addStep("Ingresa el siguiente valor en \"page\": 1 ");
		 testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		 /***********Paso 4:-Ingresa el siguiente valor en "limit":1***************************/
		 addStep("Ingresa el siguiente valor en \"limit\":1 ");
		 testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		 
		 /***********Paso 5:- Hacer clic en el botón "SEND"***************************/
		 addStep("Hacer clic en el botón \"SEND\" ");
		 String pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 		
		 Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		 
		 /***********Paso 6:-? Validar el status code ***************************/
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
		 
		/***********Paso 8:-? Validar que los valores en totalRecords sea mayor a 5***************************/
		 addStep("Valida si total records es mayor que 5 ");
		 //obtener y validar si el total records es mayor que dos
		 assertTrue(metadata.getInt("totalRecords")>5,  "validacion total records mayor que cinco");
		 testCase.addCodeEvidenceCurrentStep("Total Records:" + metadata.getInt("totalRecords") );
		 
		/***********Paso 9:-? Validar el numero de usuarios regresado en el array collaborators***************************/
		 addStep("Validar el numero de usuarios regresado en el array collaborators");
		 collaborator = responseBody.getJsonAElement("collaborators");
		 assertTrue(collaborator.length()==1, "Validacion del numero de colaboradores en el array collaborators");
		 testCase.addCodeEvidenceCurrentStep("numero de colaboradores en el array collaborators " + collaborator.length());
		 
		/***********Paso 10:-Ingresa el siguiente valor en "limit":2***************************/
		 addStep("Ingresa el siguiente valor en \"limit\":2 ");
		 limit = "2";
		 testCase.addCodeEvidenceCurrentStep("Limit:" + limit);	
		 
		/***********Paso 11:- Hacer clic en el botón "SEND"***************************/
		 addStep("Hacer clic en el botón \"SEND\" ");
		 pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 		
		 response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		 
		/***********Paso 12:-? Validar el status code ***************************/
		 addStep("Validar el status code  ");
		 statusCode = response.getStatusCode();		 
		 assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		 
		/***********Paso 13:- ? Valida los keys and values del array "metadata"***************************/
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
		 
		/***********Paso 14:-? Validar el numero de usuarios regresado en el array collaborators***************************/
		 addStep("Validar el numero de usuarios regresado en el array collaborators");
		 collaborator = responseBody.getJsonAElement("collaborators");
		 assertTrue(collaborator.length()==2, "Validacion del numero de colaboradores en el array collaborators");
		 testCase.addCodeEvidenceCurrentStep("numero de colaboradores en el array collaborators " + collaborator.length());
		 
		/***********Paso 15:-Ingresa el siguiente valor en "page": el valor de totalPages***************************/
		 addStep("Ingresa el siguiente valor en \"page\": el valor de totalPages");
		 page =String.valueOf(metadata.getInt("totalPages"));
		 testCase.addCodeEvidenceCurrentStep("Page:" + page);
		  
		/***********Paso 16:- Hacer clic en el botón "SEND"***************************/
		 addStep("Hacer clic en el botón \"SEND\" ");
		 pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 		
		 response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		 
		/***********Paso 17:-? Validar el status code ***************************/
		 addStep("Validar el status code  ");
		 statusCode = response.getStatusCode();		 
		 assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		 
		/***********Paso 18:- ? Valida los keys and values del array "metadata"***************************/
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
		 
		/***********Paso 19:-? Validar el numero de usuarios regresado en el array collaborators***************************/
		 addStep("Validar el numero de usuarios regresado en el array collaborators");
		 collaborator = responseBody.getJsonAElement("collaborators");
		 assertTrue(collaborator.length()==2 || collaborator.length()==1, "Validacion del numero de colaboradores en el array collaborators");
		 testCase.addCodeEvidenceCurrentStep("numero de colaboradores en el array collaborators " + collaborator.length());
}

	@Override
	public String setTestFullName() {
		return "ATC_FT_014_VALIDATE_PARAMETER_LIMIT";
	}

	@Override
	public String setTestDescription() {
		return "Se prueba que el parametro limit funcione correctamente, validando el cambio de limite, que regrese los usuarios correctos por paginas tanto completas como incompletas.";
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
