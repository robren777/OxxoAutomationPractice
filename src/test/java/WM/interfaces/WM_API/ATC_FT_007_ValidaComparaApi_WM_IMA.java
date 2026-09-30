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
 * MTC-FT-007-Validar  datos Regresados de api  VS  IMA
 * Desc: Verificar que el Response que regresa la API cumpla con el orden de cada KEY  validar VS IMA 
 * @author Miguel Martinez
 * @date 03/11/2023
 * */



public class ATC_FT_007_ValidaComparaApi_WM_IMA extends BaseExecution {


	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_007_ValidaComparaApi_WM_IMA_test (HashMap<String, String> data) throws Exception {
		
		
	
		
		/***************************** PASO 1  Variables Data Provider**************************************/
		//IMA
		String baseURLIMA = data.get("getUrlIMA");
		String pathIMA = data.get("getPathIMA");
	
		
	    //wm
		String baseURL = data.get("getUrl");  
        
		String authPreemptivePath = data.get("UrlToken");  
		String user = data.get("UserToken");
		String pass = data.get("PasswordToken");
		String passTokenDesencriptado = PasswordUtil.decryptPassword(pass);// Se Desencripta Contrase�a  para obener Token
		JsonUtil responseBodyApiToken;
		JSONObject jasonBodyApiWM;
		JSONObject jasonBodyApiIMA;
		
		//utils
		API_OM utilIMA = new API_OM();
		API_OM utilIWM = new API_OM();
							

		
	
	
		/*************************************** PASO  Ejecutar Peticion Se obtiene Token ***************************************/
		addStep("Se obtiene valor Token para ingresar a los datos de la api WM");
		testCase.addCodeEvidenceCurrentStep("El Token es Secreto:");
			ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
			String jsonWM = api.PreemptiveBasicAuthRequest(authPreemptivePath, user, passTokenDesencriptado).asPrettyString();
			System.out.println(jsonWM);
			responseBodyApiToken = new JsonUtil(jsonWM);			
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
			
	
            /*************************************** PASO 3 Ejecutar Peticion enviando parametros updatedDate ,page , Limit: se Recibe estatus  code y Body de respuesta ***************************************/
			
			
			
		    //Llamada api WM
	
			addStep("Ingresar la URL de la columna \"datos de prueba\" dentro de la peticion en POSTMAN y seleccionar el Metodo GET Para Obetener JSON  de WM");		
			testCase.addCodeEvidenceCurrentStep("URL:" + baseURL + data.get("getPath") );
			
			//Crear el path wm
			String pathWithQueryParams = data.get("getPath") + "?updatedDate=" + utilIWM.getYesterdayDateWM () + "&page=" +  data.get("getpageWM") + "&limit=" +  data.get("getLimitWM");	
			Response responseWM = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		
			//obtener el response body de la api de WM
			 jasonBodyApiWM = new JSONObject (responseWM.asPrettyString());
			testCase.addCodeEvidenceCurrentStep("bodyResponseWM:" + jasonBodyApiWM );
			
			
		     //Llamada api IMA

			addStep("Ingresar la URL de la columna \"datos de prueba\" dentro de la peticion en POSTMAN y seleccionar el Metodo GET Para Obetener JSON  de IMA");		
			testCase.addCodeEvidenceCurrentStep("URL:" + baseURLIMA + pathIMA);
			
			//Crear el path IMA				
			jasonBodyApiIMA = utilIWM.getApiImaResponse(baseURLIMA,pathIMA,utilIMA.getYesterdayDate(), 1, 1);
			testCase.addCodeEvidenceCurrentStep("bodyResponseIMA:" + jasonBodyApiIMA);
			
			/*************************************** PASO 4 Imprimir EsatusCode ***************************************/		
		    int getExpectedCode = Integer.parseInt(data.get("getExpectedCode")); 				 	
		    addStep("Validacion del Status Code y Resultado de la comparacion de objetos  de IMA vs WM ");	 //Obtener el status code	
		    int getStatusCode = responseWM.getStatusCode();
		    testCase.addCodeEvidenceCurrentStep("statusCode: " + getStatusCode);
		    assertEquals(getStatusCode, getExpectedCode); //Validacion	
		
            /*************************************** PASO5  Se descomentariza para validar cuando Las api tienen diferente estructura  ***************************************/
		     //jasonBodyApiWM = jasonBodyApiWM.put("clinic", "cliniccccccc");
		    
			    
            /*************************************** PASO 6  se Crean Objetos Jason para compararlos***************************************/			 

		    JSONObject getJsonWM = new JSONObject(jasonBodyApiWM); // Se Crea Objeto getJson  para accedeer a los datos  de JASON
			JSONObject getJsonIMA = new JSONObject(jasonBodyApiIMA); 
		
		
			/***************************************Paso se comparan JSON que  sean iguales ***************************************/			 		 			
			boolean sonIguales = getJsonWM.similar(getJsonIMA);
			     if (sonIguales) {
			    		
	         testCase.addCodeEvidenceCurrentStep("Validacion de JSON: " + "Los objetos JSON son iguales");
								    	 
			  } else {
			 testCase.addCodeEvidenceCurrentStep("Validacion de JSON: " + "Los objetos JSON son diferentes");
			
			 }

	}		
			
					
	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return " Verificar que el Response que regresa la API cumpla con el orden de cada KEY  validar VS IMA ";
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
