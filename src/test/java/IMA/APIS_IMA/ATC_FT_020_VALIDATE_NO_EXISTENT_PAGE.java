package IMA.APIS_IMA;

import static org.testng.Assert.assertEquals;

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
 * MTC-FT-020-VALIDATE NO EXISTENT PAGE
 * 
 * Prueba donde se envia una pagina inexsitente en el parameto Page
 * 
 * @author Guillermo Garcia
 * @date 30/11/2023
 * */

public class ATC_FT_020_VALIDATE_NO_EXISTENT_PAGE extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_020_VALIDATE_NO_EXISTENT_PAGE_test(HashMap<String, String> data) throws Exception {
		/*****************************Variables Data Provider**************************************/
		String baseURL = data.get("getUrl");
		String path = data.get("getPath");
		int expectedStatusCode =  Integer.parseInt(data.get("getExpectedCode"));
		String limit = data.get("getLimit");
		String page = data.get("getPage");
		
		
		/*****************************Instancias **************************************************/
		OM_API utilIMA = new OM_API();
		ApiMethodsUtil api =  new ApiMethodsUtil(baseURL);
		JSONArray collaborators;
		JsonUtil responseBody;
		JSONObject metadata;
		
		/*********Paso 1:- Ingresar la URL de la columna "datos de prueba" dentro de la petición en POSTMAN y seleccionar el Método GET*************************/
		addStep("Ingresar la URL de la columna \"\"datos de prueba\"\" dentro de la petición en POSTMAN y seleccionar el Método GET ");
		testCase.addCodeEvidenceCurrentStep("URL:" + baseURL + path);
		//Hacer la llamada a la api agregandor la api key
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("x-functions-key", utilIMA.getKeyApiIma());
		testCase.addCodeEvidenceCurrentStep("Key:" + utilIMA.getKeyApiIma());				
		
		/*********Paso 2:-Ingresa el siguiente valor en "UpdatedDate": 'agrega el dia de ayer'*************************/
		addStep(" Ingresa el siguiente valor en \"UpdatedDate\": 'agrega el dia de ayer'");
		testCase.addCodeEvidenceCurrentStep("updatedDate:" + utilIMA.getTomorrowDate());
		
		/*********Paso 3:-Ingresa el siguiente valor en "page": 1*************************/
		addStep("Ingresa el siguiente valor en \"page\": 1 ");		
		testCase.addCodeEvidenceCurrentStep("page:" + page);
		
		/*********Paso 4:-Ingresa el siguiente valor en "limit": 1*************************/
		addStep("Ingresa el siguiente valor en \"limit\": 1 ");		
		testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		
		/*********Paso 5:- Hacer clic en el botón "SEND"*************************/
		addStep("Hacer clic en el botón \"SEND\" ");
		String pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getTomorrowDate() + "&page=" +  page + "&limit=" +  limit;		 		
		Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		
		/*********Paso 6:-? Validar el status code *************************/
		addStep("Validar el status code ");
		int statusCode = response.getStatusCode();		 
		assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		
		/*********Paso 7:- ? Valida los keys and values del array "metadata"*************************/
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
		 
		/*********Paso 8:-Ingresa el siguiente valor en "limit": 'El valor de totalPages + 10"*************************/
		addStep("Ingresa el siguiente valor en \"limit\": 1 ");
		limit = String.valueOf(metadata.getInt("totalPages")+10);
		testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		
		/*********Paso 9:- Hacer clic en el botón "SEND"*************************/
		 addStep("Hacer clic en el botón \"SEND\" ");
		 pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate() + "&page=" +  page + "&limit=" +  limit;		 		
		 response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		 
		/*********Paso 10:-? Validar el status code *************************/
		 addStep("Validar el status code  ");
		 statusCode = response.getStatusCode();		 
		 assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		 
		/*********Paso 11:-?Valida el array collaborators*************************/
		 addStep("Valida el array collaborators");
		 //Obtenemos el array
		 collaborators = responseBody.getJsonAElement("collaborators");		
		 //Comparamos el array collaborators
		 assertEquals(collaborators.length(),0,  "Validacion que collaborators esta vacio con un status code 200");
		 testCase.addCodeEvidenceCurrentStep("El array collaborators es el siguiente:" + collaborators);		 					
			
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_020_VALIDATE_NO_EXISTENT_PAGE";
	}

	@Override
	public String setTestDescription() {
		return "Prueba donde se envia una pagina inexsitente en el parameto Page";
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
