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
 * MTC-FT-014-Validar parametros de paginacion fecha enviada sin resultados 
 * 
 * Desc: En este escenario se valida que al enviar una fecha que no obtenga resultados  el areglo Colaboradores no debera de mandar resultados 
 *       El areglo Metadata imprime  total de paginas y total de regitros en cero 
 * 
 * @author Miguel Martinez
 * @date 03/11/2023
 * */


public class ATC_FT_014_UpdateDate_SinResultados_Paginacion extends BaseExecution{
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_014_UpdateDate_SinResultados_Paginacion_test(HashMap<String, String> data) throws Exception {
		
/*************************************** PASO 1 Ingresar la URL de la columna "datos de prueba", Obtener Token para acceder a la Api ***************************************/
		
		//se declara Url como parametro  acceder a este valor dentro de la clase segun sea necesario.
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
		String passTokenDesencriptado = PasswordUtil.decryptPassword(pass);// Se Desencripta Contrasena  para obener Token
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
		 testCase.addCodeEvidenceCurrentStep("updatedDate:" + utilIMA.getTomorrowDate() );
		 
		/*******************Paso 3.1:-Ingresa el siguiente valor en "page": 1***********************************/
		 addStep("Ingresa el siguiente valor en \"page\": 1 ");
		 testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		/*******************Paso 3.2:-Ingresa el siguiente valor en "limit":1***********************************/
		 addStep("Ingresa el siguiente valor en \"limit\":1 ");
		 testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		 
		/*******************Paso 4:- Hacer clic en el botonn "SEND"***********************************/
		 addStep("Hacer clic en el boton \"SEND\" ");
		 String pathWithQueryParams = path + "?updatedDate=" + utilIMA.getTomorrowDate() + "&page=" +  page + "&limit=" +  limit;		 		
		 Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		 testCase.addCodeEvidenceCurrentStep( baseURL + data.get("getPath"));
		 
		
		 
	     int getExpectedCode = Integer.parseInt(data.get("getExpectedCode")); 				 	
	     addStep("Validacion del Status Code y Respuesta de la API");	 //Obtener el status code	
	     int getStatusCode = response.getStatusCode();
	     testCase.addCodeEvidenceCurrentStep("statusCode: " + getStatusCode);
	     assertEquals(getStatusCode, getExpectedCode); //Validación		
			
		 
		/*******************Paso 5:- ? Valida los keys and values del array "metadata"  500 registros de  limite ***********************************/
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
	 
	/*******************Paso 6: Valida que el valor de "totalRecords" y "totalPages" es el mismo: ya que la fecha enviada es invalida***********************************/		 
		 addStep("Valida que el valor de \"totalRecords\" y \"totalPages\" es el mismo " , "No se obtienen registros ya que la fecha es invalida" );
		 assertEquals(metadata.getInt("totalPages"), metadata.getInt("totalRecords"), "Validando el numero de records y pages cuando el limit es 1");
		 testCase.addCodeEvidenceCurrentStep("El valor de totalPages es: " + metadata.getInt("totalPages") + ", El valor de totalRecords es: " + metadata.getInt("totalRecords"));

	
	}

	

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "En este escenario se valida que al enviar una fecha que no obtenga resultados  el areglo Colaboradores no debera de mandar resultados ";
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

