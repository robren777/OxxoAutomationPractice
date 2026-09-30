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
 * MTC-FT-018-Se ejecute el proceso  Dos o mas dias hacia atr�s del dia actual Simulando que el dia anterior no se ejecuto el proceso por algun error 
 * 
 * Desc: En este escenario se valida ejecutar el proceso con fecha de dos dias hacia atr�s del dia actual  y no un dia atr�s como es normal esto debido a que en el dia anterior hubo algun erorr en la ejecucion de los procesos y no se proceso nada.
 * 
 * @author Miguel Martinez
 * @date 03/11/2023
 * */


public class ATC_FT_018_UpdateDate_MenorDiaActual extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_018_UpdateDate_MenorDiaActual_test(HashMap<String, String> data) throws Exception {
		
		
		/*************************************** PASO 1 Ingresar la URL de la columna "datos de prueba", Obtener Token para acceder a la Api ***************************************/
		
		//se declara Url como parametro  acceder a este valor dentro de la clase seg�n sea necesario.
		
		//WM
		String baseURL = data.get("getUrl");
		String path = data.get("getPath");
		String limit = data.get("getLimit");
		String page = data.get("getpage");  
		String fecha = data.get("getfecha");  
     		
		/*****************************************Instancias**************************************************/
		API_OM utilIMA = new API_OM();
		API_OM utilIWM = new API_OM();
	//	Ima_Trh utilIMA = new Ima_Trh();
	//	Ima_Trh utilWM = new Ima_Trh();
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
		
		
		/***************************************PASO 3 Ejecutar Peticion enviando parametros updatedDate ,page , Limit: se recibe estatus  code y Body de respuesta ***************************************/

					    
		addStep(" Ingresa el siguiente valor en \"UpdatedDate\": 'agrega dos dias antes del dia actual'");
		testCase.addCodeEvidenceCurrentStep("updatedDate:" + fecha );
		 
		/***************************************Paso 4:-Ingresa el siguiente valor en "page": 1***********************************/
		addStep("Ingresa el siguiente valor en \"page\": 1 ");
		testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		/***************************************Paso 5:-Ingresa el siguiente valor en "limit":1***********************************/
		addStep("Ingresa el siguiente valor en \"limit\":1 ");
		testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		 
		/***************************************Paso 6:- Hacer clic en el boton  "SEND"***********************************/
		 addStep("Hacer clic en el boton \"SEND\" obeter  Body de resuesta de la URL enviada");
		 String pathWithQueryParams = path + "?updatedDate=" +  fecha + "&page=" +  page + "&limit=" +  limit;		 		
		 Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		 testCase.addCodeEvidenceCurrentStep("bodyResponse:" + response.asPrettyString() + baseURL + data.get("getPath") );
		 
		
	     int getExpectedCode = Integer.parseInt(data.get("getExpectedCode")); 				 	
	     addStep("Validacion del Status Code y Respuesta de la API");	 //Obtener el status code	
	     int getStatusCode = response.getStatusCode();
	     testCase.addCodeEvidenceCurrentStep("statusCode: " + getStatusCode);
	     assertEquals(getStatusCode, getExpectedCode); //Validación		
		 
		/***************************************Paso 7:- ? Valida los keys and values del array "metadata"***********************************/
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
	 
	/*******************************************Paso 8:-? Valida que el valor de "totalRecords" y "totalPages" es el mismo***********************************/		
		 addStep("Valida que el valor de \"totalRecords\" y \"totalPages\" es el mismo ");
		 assertEquals(metadata.getInt("totalPages"), metadata.getInt("totalRecords"), "Validando el numero de records y pages cuando el limit es 1");
		 testCase.addCodeEvidenceCurrentStep("El valor de totalPages es: " + metadata.getInt("totalPages") + ", El valor de totalRecords es: " + metadata.getInt("totalRecords"));
		 
 
		
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "En este escenario se valida ejecutar el proceso con fecha de dos dias hacia atr�s del dia actual  y no un dia atr�s como es normal esto debido a que en el dia anterior hubo algun erorr en la ejecucion de los procesos y no se proceso nada.";
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
		return "1 Tener conexion hacia ambiente SQL server Lest\r\n"
				+ "2 Tener instalada la herramienta POSTMAN para ejecutar las Peticiones\r\n"
				+ "3 Tener configurada la API con los headers, queryparams y tokens necesarios\r\n"
				+ "4 Tener habilitado en POSTMAN el formateado en JSON de la respuesta recibida al ejecutar la petici�n\r\n"
				+ "5 Tener los parametros de updatedate,page,limit  que se enciaran en lapeticion GET";
	}

	@Override
	public void beforeTest() {
	}

	

}
