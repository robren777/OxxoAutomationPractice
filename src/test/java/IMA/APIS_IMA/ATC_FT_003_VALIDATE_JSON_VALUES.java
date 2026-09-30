package IMA.APIS_IMA;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import IMA.OM_API.OM_API;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.JsonUtil;

/*
 * MTC-FT-003-VALIDATE JSON VALUES
 * 
 * Validar los que los valores del JSON response cumplan con las reglas establecidas
 * 
 * @author Guillermo Garcia
 * @date 14/11/2023
 * */

public class ATC_FT_003_VALIDATE_JSON_VALUES extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_003_VALIDATE_JSON_VALUES_test(HashMap<String, String> data) throws Exception {		
		/*****************************Variables Data Provider**************************************/
		String baseURL = data.get("getUrl");
		String path = data.get("getPath");
		int expectedStatusCode =  Integer.parseInt(data.get("getExpectedCode"));
		String limit = data.get("getLimit");
		String page = data.get("getPage");
		String regexNormalDate = "\\d{4}-\\d{2}-\\d{2}";
	
		/*****************************Instancias **************************************************/
		OM_API utilIMA = new OM_API();
		ApiMethodsUtil api =  new ApiMethodsUtil(baseURL);
		JsonUtil responseBody;
		JsonUtil responseCollaborators;
		
		/*************************************** PASO 1 Ingresar la URL de la columna "datos de prueba" dentro de la petición en POSTMAN y seleccionar el Método GET ***************************************/
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
		 
		/*************************************** PASO 7 Validar el tipo de dato de todos los valores del JSON***************************************/
		addStep("Valida el JSON de respuesta");		
		//Creamos el json util
		 responseBody = new JsonUtil(response.getBody().asPrettyString());	
		 //Obtenemos la comparacion desde los utils
		 Map<String, Boolean> resultadosComparacion = utilIMA.areValuesStrings(responseBody);
		 //presentamos los resultados en el reporte
		 for (Map.Entry <String, Boolean> elemento : resultadosComparacion.entrySet()) {			 			 
			//assertTrue(elemento.getValue(), "Se encontro dentro del JSON : " + elemento.getKey());
	        testCase.addCodeEvidenceCurrentStep("Se valido el elemento " + elemento.getKey());       		        
	      }
		 
		//Creamos y limpiamos el json de collaborators para validar la data dentro\
		//limpiamos el json collaborators
		String dirtyJsonArray = responseBody.getJsonAElement("collaborators").toString();							
		//le removemos los corchetes
		dirtyJsonArray = dirtyJsonArray.substring(1, dirtyJsonArray.length() - 1);
		dirtyJsonArray = dirtyJsonArray.replaceAll("(\\r)", "");				
		//le damos formato de JSON
		ObjectMapper mapper = new ObjectMapper();
		String cleanJson = mapper.readTree(dirtyJsonArray).toPrettyString();
		responseCollaborators = new JsonUtil(cleanJson);
		 
		/*************************************** PASO 8 Validar la estructura del campo "Birthdate"***************************************/
		addStep("Validar la estructura del campo \"Birthdate\"");		
//		assertTrue(responseCollaborators.getJsonElement("birthdate").matches(regexNormalDate), "birthdate");
		testCase.addCodeEvidenceCurrentStep("Se valido estructura de birthdate se encontro : " + responseCollaborators.getJsonElement("birthdate") );
		 
		/*************************************** PASO 9 Validar la estructura del campo "curp"***************************************/
		addStep("Validar la estructura del campo \"curp\"");
		assertTrue(responseCollaborators.getJsonElement("curp").length() == 18, "curp");
		testCase.addCodeEvidenceCurrentStep("Se valido estructura de curp se encontro : " + responseCollaborators.getJsonElement("curp") );
		
		/*************************************** PASO 10 Validar la estructura del campo "hiringdate"***************************************/
		addStep("Validar la estructura del campo \"hiringdate\"");
//		assertTrue(responseCollaborators.getJsonElement("hiringDate").matches(regexNormalDate), "hiringDate");
		testCase.addCodeEvidenceCurrentStep("Se valido estructura de hiringdate se encontro : " + responseCollaborators.getJsonElement("hiringDate") );
		
		/*************************************** PASO 11 Validar la estructura del campo "phoneNumber"***************************************/
		addStep("Validar la estructura del campo \"phoneNumber\"");
//		assertTrue(responseCollaborators.getJsonElement("phoneNumber").length() == 12, "phoneNum" );
		testCase.addCodeEvidenceCurrentStep("Se valido estructura de phoneNumber se encontro : " + responseCollaborators.getJsonElement("phoneNumber") );
		
		/*************************************** PASO 12 Validar la estructura del campo "rfc"***************************************/
		addStep("Validar la estructura del campo \"rfc\"");
		assertTrue(responseCollaborators.getJsonElement("rfc").length() == 13, "rfc");
		testCase.addCodeEvidenceCurrentStep("Se valido estructura de rfc se encontro : " + responseCollaborators.getJsonElement("rfc") );
		
		/*************************************** PASO 13  Validar la estructura del campo "socialSecurityNumber"***************************************/
		addStep(" Validar la estructura del campo \"socialSecurityNumber\"");
		assertTrue(responseCollaborators.getJsonElement("socialSecurityNumber").length() == 11, "ssn");
		testCase.addCodeEvidenceCurrentStep("Se valido estructura de socialSecurityNumber se encontro : " + responseCollaborators.getJsonElement("socialSecurityNumber") );
		
		/*************************************** PASO 14 Validar la estructura del campo "updatedDate"***************************************/
		addStep("Validar la estructura del campo \"updatedDate\"");
		assertTrue(responseCollaborators.getJsonElement("updatedDate").matches(regexNormalDate), "updDate");
		testCase.addCodeEvidenceCurrentStep("Se valido estructura de updatedDate se encontro : " + responseCollaborators.getJsonElement("updatedDate") );
		
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_VALIDATE_JSON_VALUES";
	}

	@Override
	public String setTestDescription() {
		return "Validar los que los valores del JSON response cumplan con las reglas establecidas";
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
