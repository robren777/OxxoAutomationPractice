package IMA.APIS_IMA;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import IMA.OM_API.OM_API;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;


/*
 * MTC-FT-001-VALIDATE CONEXION STATUS CODE 200 
 * 
 * Validar el estatus de la API por medio del codigo de respuesta. Resultado esperado: Al enviar la peticion te debe de regresar un codigo 200 junto con el response correcto (JSON)
 * 
 * @author Guillermo Garcia
 * @date 03/11/2023
 * */

public class ATC_FT_001_VALIDATE_CONEXION_STATUS_CODE extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_001_VALIDATE_CONEXION_STATUS_CODE_test(HashMap<String, String> data) throws Exception {
		/*****************************Variables Data Provider**************************************/
		String baseURL = data.get("getUrl");
		String path = data.get("getPath");
		int expectedStatusCode =  Integer.parseInt(data.get("getExpectedCode"));
		
		/*****************************Instancias **************************************************/
		OM_API utilIMA = new OM_API();
		ApiMethodsUtil api =  new ApiMethodsUtil(baseURL);

		/*************************************** PASO 1 Ingresar la URL de la columna "datos de prueba" dentro de la petición en POSTMAN y seleccionar el Método GET ***************************************/
		addStep("Ingresar la URL de la columna \"datos de prueba\" dentro de la petición en POSTMAN y seleccionar el Método GET");		
		testCase.addCodeEvidenceCurrentStep("URL:" + baseURL + path);
		
		/*************************************** PASO 2 En la pestana Authorization selecciona API Key e ingresa la API Key ***************************************/
		addStep("En la pestana Authorization selecciona API Key e ingresa la API Key");
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("x-functions-key", utilIMA.getKeyApiIma());
		testCase.addCodeEvidenceCurrentStep("Key:" + utilIMA.getKeyApiIma());
		
		/*************************************** PASO 3 Hacer clic en el botón "SEND" ***************************************/
		 addStep("Hacer clic en el botón \"SEND\"");
		 String pathWithQueryParams = path + "?updatedDate=" +  utilIMA.getYesterdayDate();
		 Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);		 
		 testCase.addCodeEvidenceCurrentStep("updatedDate:" + utilIMA.getYesterdayDate());
		 
		/*************************************** PASO 4 Validar codigo de estatus ***************************************/
		 addStep("Validar codigo de estatus");
		 int statusCode = response.getStatusCode();
		 System.out.println(statusCode);
		 assertEquals(statusCode,expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_VALIDATE_CONEXION_STATUS_CODE_200";
	}

	@Override
	public String setTestDescription() {
		return "Validar el estatus de la API por medio del codigo de respuesta. Resultado esperado: Al enviar la peticion te debe de regresar un codigo 200 junto con el response correcto (JSON)";
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
