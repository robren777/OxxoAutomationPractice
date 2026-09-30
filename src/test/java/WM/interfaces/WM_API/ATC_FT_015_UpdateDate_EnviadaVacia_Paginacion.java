package WM.interfaces.WM_API;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.om.API_OM;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.JsonUtil;
import utils.password.PasswordUtil;

/*
 * MTC-FT-015-Validar paginacion sin enviar parametro de fecha 
 * 
 * Desc: En este escenario se valida que al enviar query parametro updatefecha  vacio, 
 *       En este caso debera de regresar mensaje de error  400
 * 
 * @author Miguel Martinez
 * @date 03/11/2023
 * */


public class ATC_FT_015_UpdateDate_EnviadaVacia_Paginacion extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_015_UpdateDate_EnviadaVacia_Paginacion_test(HashMap<String, String> data) throws Exception {
		
/*************************************** PASO 1 Ingresar la URL de la columna "datos de prueba", Obtener Token para acceder a la Api ***************************************/
		
		//se declara Url como parametro  acceder a este valor dentro de la clase seg�n sea necesario.
		String baseURL = data.get("getUrl");
		String path = data.get("getPath");
		String fecha = data.get("getFecha");
		String limit = data.get("getLimit");
		String page = data.get("getpage"); 
     		
		/*****************************Instancias **************************************************/
		API_OM utilIMA = new API_OM();
		API_OM utilIWM = new API_OM();
		//Ima_Trh utilIMA = new Ima_Trh();
		//Ima_Trh utilWM = new Ima_Trh();
		ApiMethodsUtil api =  new ApiMethodsUtil(baseURL);		
		JsonUtil responseBody;
		JSONObject metadata;
	
		
		
		/*************************************** PASO 2 Ejecutar Peticion para  obtener  Token ***************************************/
		addStep("Se obtiene valor Token para ingresar a los datos de la api WM");
		testCase.addCodeEvidenceCurrentStep("El Token es Secreto:");
		
		String authPreemptivePath = data.get("UrlToken");  
		String user = data.get("UserToken");
		String pass = data.get("PasswordToken");
		String passTokenDesencriptado = PasswordUtil.decryptPassword(pass);// Se Desencripta Contrase�a  para obener Token
		JsonUtil responseBodyApiToken;
		JsonUtil jasonBodyApiWM;
		
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
		
		
		/*************************************** PASO 3 Ejecutar Peticion enviando parametros updatedDate ,page , Limit: se recibe estatus  code y Body de respuesta ***************************************/

					    
		 addStep(" Ingresa el siguiente valor en \"UpdatedDate\": 'agrega el dia de ayer'");
		 testCase.addCodeEvidenceCurrentStep("updatedDateeeeee:" + fecha );
		 
		/*******************Paso 3.1:-Ingresa el siguiente valor en "page": 1***********************************/
		 addStep("Ingresa el siguiente valor en \"page\": 1 ");
		 testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		/*******************Paso 3.2:-Ingresa el siguiente valor en "limit":1***********************************/
		 addStep("Ingresa el siguiente valor en \"limit\":1 ");
		 testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		 
		/*******************Paso 4:- Hacer clic en el boton "SEND"***********************************/
		 addStep("Hacer clic en el botón \"SEND\" ");
		 String pathWithQueryParams = path + "?updatedDate=" + fecha + "&page=" +  page + "&limit=" +  limit;		 		
		 Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		 testCase.addCodeEvidenceCurrentStep( baseURL + data.get("getPath"));
		 
		
		 
	     int getExpectedCode = Integer.parseInt(data.get("getExpectedCode")); 				 	
	     addStep("Validacion del Status Code y Respuesta de la API");	 //Obtener el status code	
	     int getStatusCode = response.getStatusCode();
	     testCase.addCodeEvidenceCurrentStep("statusCode: " + getStatusCode);
	     assertEquals(getStatusCode, getExpectedCode); //Validación		
			
		 
		/*******************Paso 5:- ? Valida los keys and values del array "metadata"  500 registros de  limite ***********************************/
	     addStep("Valida los keys and values del Array \"collaborators\"");
		 //obtener el body
		 responseBody = new JsonUtil(response.getBody().asPrettyString());	
		 testCase.addCodeEvidenceCurrentStep("Resultado: No se obtuvo respuesta ya que no se envio fecha en el parametro updatedate" );
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "En este escenario se valida que al enviar query parametro updatefecha  vacio,"
				+ "En este caso debera de regresar mensaje de error  400";
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
				+ "4 Tener habilitado en POSTMAN el formateado en JSON de la respuesta recibida al ejecutar la petici�n";
	}

	@Override
	public void beforeTest() {
	}

	

}

