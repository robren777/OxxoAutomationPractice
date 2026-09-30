package WM.interfaces.WM_API;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import WM.om.API_OM;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.JsonUtil;
import utils.password.PasswordUtil;



/*
 * MTC-FT-004-Validar los nombres de cada KEY
 * Desc: Validar los nombres de los KEYS en el RESPONSE recibido al correr la peticion del Metodo GET
 * 
 * @author Miguel Martinez
 * @date 03/11/2023
 * */


public class ATC_FT_004_ValidaJasonKey extends BaseExecution{
	
	//private static final String responseText = null;

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_004_ValidaJasonKey_test(HashMap<String, String> data) throws Exception {
		
		
/*************************************** PASO 1 Ingresar la URL de la columna "datos de prueba", Obtener Token para acceder a la Api ***************************************/
		
		//Se declara Url como parametro  acceder a este valor dentro de la clase segun sea necesario.
		
		//WM
		String baseURL = data.get("getUrl");  
        ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
    
		String authPreemptivePath = data.get("UrlToken");  
		String user = data.get("UserToken");
		String pass = data.get("PasswordToken");
		String passTokenDesencriptado = PasswordUtil.decryptPassword(pass);// Se Desencripta Contrase�a  para obener Token
		JsonUtil responseBodyApiToken;
		JsonUtil responseBody;
		API_OM utilWM = new API_OM();
		
		String path = data.get("getPath");
		int expectedStatusCode =  Integer.parseInt(data.get("getExpectedCode"));
		String limit = data.get("getLimit");
		String page = data.get("getPage");
		String keysExpected = data.get("getKeysExpected");
		String keysCollaborators = data.get("getKeysCollaborators");
		
		/*************************************** PASO 2 Ejecutar Peticion Se obtiene Token ***************************************/
		addStep("Se obtiene valor Token para ingresar a los datos de la api WM");
		testCase.addCodeEvidenceCurrentStep("El Token es Secreto:");
		
		//obtener el response body de la api del token
		responseBodyApiToken = new JsonUtil(api.PreemptiveBasicAuthRequest(authPreemptivePath, user, passTokenDesencriptado).asPrettyString());
		String accessToken = responseBodyApiToken.getJsonElement("access_token");
		
		//declarar los headers para la llamada del response de la api de WM
		HashMap<String, String> headersList = new HashMap<>();		
		headersList.put("Tracking-Id", "c17a82a9-25ef-4cdd-8f8c-c3bd25dac878");
		headersList.put("Content-Type", "application/json");
		headersList.put("Channel-Id", "WEB");
		headersList.put("Country-Code", "MX");
		headersList.put("Language", "SPA");		
		headersList.put("Authorization",  "Bearer " + accessToken);
		headersList.put("x-Gateway-APIKey", data.get("getGatewayAPIKey"));
			
		/*************************************** PASO 3 Ingresar la fecha en el valor Updated date ***************************************/
		addStep("Ingresar la fecha en el valor  del parametro Updated date");
		testCase.addCodeEvidenceCurrentStep("updatedDate:" + utilWM.getYesterdayDateWM());
		 		 
		/*************************************** PASO 4 Ingresar la pagina en el valor page ***************************************/
		 addStep("Ingresar la pagina en el valor  del parametro page");
		 testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		/*************************************** PASO 5 Ingresar el limite en el valor Limit ***************************************/
		 addStep("Ingresar el limite en el valor  del parametro Limit");	
		 testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		 
		/*************************************** PASO 6 Hacer clic en el boton "SEND" ***************************************/
		 String pathWithQueryParams = path + "?updatedDate=" +  utilWM.getYesterdayDateWM() + "&page=" +  page + "&limit=" +  limit;		 
		 addStep("Hacer clic en el boton \"SEND\"");
		 Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);	
		 
		/*************************************** PASO 7 Validar el status code ***************************************/
		 addStep("Validar el status code");
		 int statusCode = response.getStatusCode();		 
		 assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
		 
		/*************************************** PASO 8 Valida los keys del Json de respuesta ***************************************/
		 addStep("Valida los array obtenidos dentro de objeto JSON");
		 //Creamos el json util
		 responseBody = new JsonUtil(response.getBody().asPrettyString());		 
		 //Obtenemos la comparacion desde los utils
		 Map<String, Boolean> resultadosComparacion = utilWM.compareJsonKeys(responseBody, keysExpected);
		 //presentamos los resultados en el reporte
		 for (Map.Entry elemento : resultadosComparacion.entrySet()) {			 
			 if((boolean) elemento.getValue()) {	        	
	        	 testCase.addCodeEvidenceCurrentStep("Los sig. Array son validos dentro del JSON de respuesta: " + elemento.getKey());
	        	 continue;
	         }
	    	 testCase.addCodeEvidenceCurrentStep("Los sig. Array No son validos dentro del JSON de respuesta: " + elemento.getKey());	        		        
	      }


			/*************************************** PASO 9 Valida los keys del array "colaborators" ***************************************/
		 addStep("Valida los keys del array \"colaborators\"");
		 //Obtenemos la comparacion desde los utils
		 Map<String, Boolean> resultadosComparacionCollaborators = utilWM.compareJsonKeysForJsonArray(responseBody,keysCollaborators,"collaborators");
		 //presentamos los resultados en el reporte
		 for (Map.Entry elemento : resultadosComparacionCollaborators.entrySet()) {			 
			 if((boolean) elemento.getValue()) {	        	
	        	 testCase.addCodeEvidenceCurrentStep("El siguiente key se valido dentro del JSON de respuesta en el array collaborators: " + elemento.getKey());
	        	 continue;
	         }
	    	 testCase.addCodeEvidenceCurrentStep("El siguiente key no se encontro dentro del JSON de respuesta en el array collaborators: " + elemento.getKey());	        		        
	      }
		 
		 
	}

	@Override
	public String setTestDescription() {
		return "Validar los nombres de los KEYS en el RESPONSE recibido al correr la peticion del Metodo GET";
	}

	@Override
	public String setTestDesigner() {
		return "Miguel Martinez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1 Tener conexion hacia ambiente Datalake\r\n"
				+ "2 Tener instalada la herramienta POSTMAN para ejecutar las Peticiones\r\n"
				+ "3 Tener configurada la API con los headers, queryparams y tokens necesarios\r\n"
				+ "4 Tener habilitado en POSTMAN el formateado en JSON de la respuesta recibida al ejecutar la peticion";
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}
	

}