package IMA.APIS_IMA;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.OM_API.OM_API;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;


/*
 * MTC-FT-019-VALIDATE FUTURE DATE
 * 
 * Prueba donde se valida la respuesta ante enviar una fecha a futuro como parametro en updatedDate
 * 
 * @author Guillermo Garcia
 * @date 30/11/2023
 * */

public class ATC_FT_019_VALIDATE_FUTURE_DATE extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_019_VALIDATE_FUTURE_DATE_test(HashMap<String, String> data) throws Exception {
		/*****************************Variables Data Provider**************************************/
		String baseURL = data.get("getUrl");
		String path = data.get("getPath");
		int expectedStatusCode =  Integer.parseInt(data.get("getExpectedCode"));
		String limit = data.get("getLimit");
		String page = data.get("getPage");
		String jsonExpected = data.get("getJson");
		
		/*****************************Instancias **************************************************/
		OM_API utilIMA = new OM_API();
		ApiMethodsUtil api =  new ApiMethodsUtil(baseURL);
		String jsonReal;		
		
		/*************************Paso 1:- Ingresar la URL de la columna "datos de prueba" dentro de la petición en POSTMAN y seleccionar el Método GET*************************************************************************/
		addStep("Ingresar la URL de la columna \"\"datos de prueba\"\" dentro de la petición en POSTMAN y seleccionar el Método GET ");
		testCase.addCodeEvidenceCurrentStep("URL:" + baseURL + path);
		//Hacer la llamada a la api agregandor la api key
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("x-functions-key", utilIMA.getKeyApiIma());
		testCase.addCodeEvidenceCurrentStep("Key:" + utilIMA.getKeyApiIma());				
		
		/*************************Paso 2:-Ingresa el siguiente valor en "UpdatedDate": 'agrega el dia de manana'*************************************************************************/
		addStep(" Ingresa el siguiente valor en \"UpdatedDate\": 'agrega el dia de manana'");
		testCase.addCodeEvidenceCurrentStep("updatedDate:" + utilIMA.getTomorrowDate());
		
		/*************************Paso 3:-Ingresa el siguiente valor en "page": 1*************************************************************************/
		addStep("Ingresa el siguiente valor en \"page\": 1 ");		
		testCase.addCodeEvidenceCurrentStep("page:" + page);
		
		/*************************Paso 4:-Ingresa el siguiente valor en "limit": 10*************************************************************************/
		addStep("Ingresa el siguiente valor en \"limit\": 10 ");		
		testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		
		/*************************Paso 5:- Hacer clic en el botón "SEND"*************************************************************************/
		addStep("Hacer clic en el botón \"SEND\" ");
		String pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getTomorrowDate() + "&page=" +  page + "&limit=" +  limit;		 		
		Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		
		/*************************Paso 6:-? Validar el status code *************************************************************************/
		addStep("Validar el status code ");
		int statusCode = response.getStatusCode();		 
		assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		
		/*************************Paso 7:- ? Valida el Json de respuesta*************************************************************************/
		addStep("Valida el JSON de respuesta");
		//Obtenemos el JSON real
		jsonReal = response.getBody().asString();		
		//Comparamos los JSONS
		assertEquals(jsonReal, jsonExpected,  "Validacion que el body del json de respuesta sea correcto");
		testCase.addCodeEvidenceCurrentStep("El JSON esperado es el siguiente:" + jsonExpected);
		testCase.addCodeEvidenceCurrentStep("El JSON encotrado fue el siguiente:" + jsonReal);						
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_019_VALIDATE_FUTURE_DATE";
	}

	@Override
	public String setTestDescription() {
		return "Prueba donde se valida la respuesta ante enviar una fecha a futuro como parametro en updatedDate";
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
