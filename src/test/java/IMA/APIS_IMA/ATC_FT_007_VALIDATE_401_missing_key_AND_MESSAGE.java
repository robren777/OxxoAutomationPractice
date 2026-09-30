package IMA.APIS_IMA;


import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.OM_API.OM_API;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;


/*
 * MTC-FT-007-VALIDATE 401 missing key AND MESSAGE 
 * 
 * Caso de prueba para forzar el error numero 401 y validar su mensaje
 * 
 * @author Guillermo Garcia
 * @date 14/11/2023
 * */

public class ATC_FT_007_VALIDATE_401_missing_key_AND_MESSAGE extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_007_VALIDATE_401_missing_key_AND_MESSAGE_test(HashMap<String, String> data) throws Exception {
		/*****************************Variables Data Provider**************************************/
		String baseURL = data.get("getUrl");
		String path = data.get("getPath");
		int expectedStatusCode =  Integer.parseInt(data.get("getExpectedCode"));
		String limit = data.get("getLimit");
		String page = data.get("getPage");
		
		
		/*****************************Instancias **************************************************/
		OM_API utilIMA = new OM_API();
		ApiMethodsUtil api =  new ApiMethodsUtil(baseURL);		
		
		/*************************************** PASO 1 Ingresar la URL de la columna "datos de prueba" dentro de la petición en POSTMAN y seleccionar el Método GET ***************************************/
		addStep("Ingresar la URL de la columna \"datos de prueba\" dentro de la petición en POSTMAN y seleccionar el Método GET");		
		testCase.addCodeEvidenceCurrentStep("URL:" + baseURL + path);
		//Hacer la llamada a la api agregandor la api key
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("x-functions-key", "");
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
		 String pathWithQueryParams = path + "?updatedDate=" +  "affad" + "&page=" +  page + "&limit=" +  limit;		 
		 addStep("Hacer clic en el botón \"SEND\"");
		 Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);	
		 
		/*************************************** PASO 6 Validar el status code ***************************************/
		 addStep("Validar el status code");
		 int statusCode = response.getStatusCode();		 
		 assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_VALIDATE_401_missing_key_AND_MESSAGE_test";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba para forzar el error numero 401 y validar su mensaje";
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
