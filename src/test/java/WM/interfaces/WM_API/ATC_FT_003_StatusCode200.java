package WM.interfaces.WM_API;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.om.API_OM;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.JsonUtil;
import utils.password.PasswordUtil;



/*
 * MTC-FT-003-Validar Status Code 200 al ejecutar la api
 * MTC-FT-001-Validar que la conexion de la API hacia la BD del DATALAKE sea Satisfactoria
 * 
 * Desc: Validar  unicamente que al enviar la peticion te debe de regresar un codigo 200 junto con un response (JSON).
 * 
 * @author Miguel Martinez
 * @date 03/11/2023
 * */


public class ATC_FT_003_StatusCode200 extends BaseExecution{
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_StatusCode200_test(HashMap<String, String> data) throws Exception {
		
		/*************************************** PASO 1 Ingresar la URL de la columna "datos de prueba", Obtener Token para acceder a la Api ***************************************/
		
		//se declara Url como parametro  acceder a este valor dentro de la clase seg�n sea necesario.
		String baseURL = data.get("getUrl");  
        ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
       
		String authPreemptivePath = data.get("UrlToken");  
		String user = data.get("UserToken");
		String pass = data.get("PasswordToken");
		String passTokenDesencriptado = PasswordUtil.decryptPassword(pass);// Se Desencripta Contrase�a  para obener Token
		JsonUtil responseBodyApiToken;
		JsonUtil jasonBodyApiWM;
		API_OM utilIMA = new API_OM();
		
		/*************************************** PASO 2 Ejecutar Peticion Se obtiene Token ***************************************/
		addStep("Ejecutar Peticion GET");
		
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
		
		
		/*************************************** Paso 3 Obtener  Estatus Code y bodyResponse  ***************************************/	
		//Crear el path
		String pathWithQueryParams = data.get("getPath") + "?updatedDate=" +  utilIMA.getYesterdayDateWM() + "&page=" +  data.get("getpage") + "&limit=" +  data.get("getLimit");	
		Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		
		//obtener el response body de la api de WM
		jasonBodyApiWM = new JsonUtil (response.asPrettyString());
		 
	    testCase.addCodeEvidenceCurrentStep("bodyResponse:" + response.asPrettyString() + baseURL + data.get("getPath") );
					
	    int getExpectedCode = Integer.parseInt(data.get("getExpectedCode")); 				 	
	    addStep("Validacion del Status Code y Respuesta de la API");	 //Obtener el status code	
	    int getStatusCode = response.getStatusCode();
	    testCase.addCodeEvidenceCurrentStep("statusCode: " + getStatusCode);
	    assertEquals(getStatusCode, getExpectedCode); //Validacion		
			
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Validar  unicamente que al enviar la peticion te debe de regresar un codigo 200 junto con un response JSON";
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

	

}

