package WM.interfaces.WM_API;
import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.om.API_OM;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.JsonUtil;
import utils.password.PasswordUtil;


/*
 * ATC_FT_005_Valida_JasonTipo_Dato
 * Desc: Validar  los tipos de datos de los VALUES (Todos seran STRING) en cada KEY en el RESPONSE recibido al correr la petici�n
 * 
 * @author Miguel Martinez
 * @date 03/11/2023
 * */

 
     public class ATC_FT_005_Valida_JasonTipo_Dato extends BaseExecution {
		
	///////////////////////// Metodo  validador de Tipo de Dato/////////////////////////////////
	      public String validarTipo(String jsonString, String key, String tipo) {  
 	
 		      JSONObject jsonObject2 = new JSONObject(jsonString);
 		  
 	               if (jsonObject2.has(key)) {
 	   	               Object valor = jsonObject2.get(key);

 	   	           if(tipo == "String") {
		 	   	      if (valor instanceof String) {
		 	   	        return "La clave'" + key + "'es tipo " + tipo + " (String).";
		 	   	    } else {
		 	   	        return "La clave'" + key + "'no es tipo " + tipo + ".";
		 	   	    }
 	   	    	}
 	   	    	      else if(tipo == "Integer") {
	 	   	      if (valor instanceof Integer) {
		 	   	        return "La clave'" + key + "'es tipo " + tipo + " (Integer).";
		 	   	    } else {
		 	   	        return "La clave'" + key + "'no es tipo " + tipo + ".";
		 	   	    }
 	   	    	}
 	    	        } else {
 	   	                return  "La clave'" + key + "' no esta presente en el JSON.";
 	   	}
 	    
 	                    return "Tipo no detectado verificar";
 	    }			

	@Test(dataProvider = "data-provider", enabled = true)  
	public void ATC_FT_005_Valida_JasonTipo_Dato_test (HashMap<String, String> data) throws Exception {
		
/*************************************** PASO 1 Ingresar la URL de la columna "datos de prueba", Obtener Token para acceder a la Api ***************************************/
		
		//se declara Url como parametro  acceder a este valor dentro de la clase seg�n sea necesario.
		//WM
		String baseURL = data.get("getUrl");
		String path = data.get("getPath");
		String fecha = data.get("getFecha");
		String page = data.get("getpage");
		String limit =  data.get("getLimit");
		int expectedStatusCode =  Integer.parseInt(data.get("getExpectedCode"));

		/*****************************Instancias **************************************************/
		API_OM utilIMA = new API_OM();
		API_OM utilWM = new API_OM();
		ApiMethodsUtil api =  new ApiMethodsUtil(baseURL);		

		
		/*************************************** PASO 2 Ejecutar Peticion para  obtener  Token ***************************************/
		addStep("Se obtiene valor Token para ingresar a los datos de la api WM");
		testCase.addCodeEvidenceCurrentStep("El Token es Secreto:");
		String authPreemptivePath = data.get("UrlToken");  
		String user = data.get("UserToken");
		String pass = data.get("PasswordToken");
		String passTokenDesencriptado = PasswordUtil.decryptPassword(pass);// Se Desencripta Contrase�a  para obener Token
		JsonUtil responseBodyApiToken;
		
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
		addStep("Ingresar la fecha en el valor Updateddate");
		testCase.addCodeEvidenceCurrentStep("updatedDate:" + utilWM.getYesterdayDateWM());
		 		 
		/*************************************** PASO 4 Ingresar la pagina en el valor page ***************************************/
		 addStep("Ingresar la pagina en el valor page");
		 testCase.addCodeEvidenceCurrentStep("page:" + page);
		 
		/*************************************** PASO 5 Ingresar el limite en el valor Limit ***************************************/
		 addStep("Ingresar el limite en el valor Limit");	
		 testCase.addCodeEvidenceCurrentStep("Limit:" + limit);
		 
		/*************************************** PASO 6 Hacer clic en el botón "SEND" ***************************************/
		 
		 addStep("Hacer clic en el boton \"SEND\" obeter  Body de resuesta de la URL enviada");
		 String pathWithQueryParams = path + "?updatedDate=" +  utilWM.getYesterdayDateWM() + "&page=" +  page + "&limit=" +  limit;		 		
		 Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		 testCase.addCodeEvidenceCurrentStep("bodyResponse:" + baseURL + data.get("getPath") );
		 
		/*************************************** PASO 7 Validar el status code ***************************************/
		 addStep("Validar el status code");
		 int statusCode = response.getStatusCode();		 
		 assertEquals(statusCode, expectedStatusCode,  "Validacion del codigo de estatus");
		 testCase.addCodeEvidenceCurrentStep("Estatus esperado:" + expectedStatusCode);
		 testCase.addCodeEvidenceCurrentStep("Estatus encontrado:" + statusCode);
			
	
	    /*************************************** paso 8 Obtener Tipo de dato de campos ***************************************/
	
		 String getBodyResponse = response.getBody().asPrettyString(); // se obtiene el cuerpo de una respuesta HTTP en formato JSON 
		 JSONObject jsonObject1 = new JSONObject(getBodyResponse); // se crea un objeto JSONObject a partir de la cadena getBodyResponse
		 JSONArray dataArray = jsonObject1.getJSONArray("collaborators"); // Se Obtiene areglo de el objeto jsonObject1
	   
				testCase.addCodeEvidenceCurrentStep("bodyResponse:" + getBodyResponse );
			   		    
			    
	        addStep("Validacion de Tipos de Datos Regresados en el Json");  
		    
		    // se validan Datos regresados del Jason
		    String validacion1  = validarTipo(dataArray.get(0).toString(),"birthdate", "String");
		    String validacion2  = validarTipo(dataArray.get(0).toString(),"business", "String");
		    String validacion3  = validarTipo(dataArray.get(0).toString(),"clinic", "String");
		    String validacion4  = validarTipo(dataArray.get(0).toString(),"collaboratorName", "String");
		    String validacion5  = validarTipo(dataArray.get(0).toString(),"collaboratorNumber", "String");
		    String validacion6  = validarTipo(dataArray.get(0).toString(),"collaboratorStatus", "String");
		    String validacion7  = validarTipo(dataArray.get(0).toString(),"collaboratorType", "String");
		    String validacion8  = validarTipo(dataArray.get(0).toString(),"crPlaza", "String");
		    String validacion9  = validarTipo(dataArray.get(0).toString(),"curp", "String");
		    String validacion10 = validarTipo(dataArray.get(0).toString(),"email", "String");
		    String validacion11 = validarTipo(dataArray.get(0).toString(),"functionalArea", "String");
		    String validacion12 = validarTipo(dataArray.get(0).toString(),"hiringDate", "String");
		    String validacion13 = validarTipo(dataArray.get(0).toString(),"managerId", "String");
		    String validacion14 = validarTipo(dataArray.get(0).toString(),"managerName", "String");
		    String validacion15 = validarTipo(dataArray.get(0).toString(),"os", "String");
		    String validacion16 = validarTipo(dataArray.get(0).toString(),"personalDivisionId", "String");
		    String validacion17 = validarTipo(dataArray.get(0).toString(),"personnelArea", "String");
		    String validacion18 = validarTipo(dataArray.get(0).toString(),"personnelDivision", "String");
		    String validacion19 = validarTipo(dataArray.get(0).toString(),"phoneNumber", "String");
		    String validacion20 = validarTipo(dataArray.get(0).toString(),"position", "String");
		    String validacion21 = validarTipo(dataArray.get(0).toString(),"region", "String");
		    String validacion22 = validarTipo(dataArray.get(0).toString(),"rfc", "String");
		    String validacion23 = validarTipo(dataArray.get(0).toString(),"socialSecurityNumber", "String");
		    String validacion24 = validarTipo(dataArray.get(0).toString(),"society", "String");
		    String validacion25 = validarTipo(dataArray.get(0).toString(),"societyId", "String");
		    String validacion26 = validarTipo(dataArray.get(0).toString(),"updatedDate", "String");
		    String validacion27 = validarTipo(dataArray.get(0).toString(),"workplace", "String");
		    String validacion28 = validarTipo(dataArray.get(0).toString(),"zone", "String");
		   
		    
		    testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion1);
		    testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion2);
		    testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion3);
		    testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion4);
		    testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion5);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion6);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion7);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion8);
		    testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion9);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion10);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion11);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion12);
		    testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion13);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion14);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion15);
		    testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion16);
		    testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion17);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion18);
		    testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion19);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion20);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion21);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion22);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion23);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion24);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion25);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion26);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion27);
			testCase.addCodeEvidenceCurrentStep("collaborators : " + validacion28);
		}	

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "ATC_FT_005_Valida_JasonTipo_Dato";
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
		return "1 Tener Conexion hacia ambiente Datalake"
				+ "2 Tener instalada la herramienta POSTMAN para ejecutar las Peticiones\r\n"
				+ "3 Tener configurada la API con los headers, queryparams y tokens necesarios\r\n"
				+ "4 Tener habilitado en POSTMAN el formateado en JSON de la respuesta recibida al ejecutar la petici�n";
	}

	@Override
	public void beforeTest() {
	}
	

}
