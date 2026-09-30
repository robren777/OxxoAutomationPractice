package WM.om;

import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.restassured.response.Response;
import utils.ApiMethodsUtil;
import utils.JsonUtil;
import utils.password.PasswordUtil;

public class API_OM {
	/**
	 * Variables locales
	 */
	

	
	/**
	 * Variables globales
	 */
	private String keyApiIma = PasswordUtil.decryptPassword("D12D80D7DF89F9B57140D0D451A9FE28F21920C226B85EFAC8126403204E6C9C7BAD01E2E2828977900EB8692C51C746DA326A302795F531FA93F1C8A53D5F7C");
	
	
	/**
	 * Getters and Setters 
	 * 
	 */
	public String getKeyApiIma() {
		return keyApiIma;
	}
	
	/**
	 * Metodo para obtener la fecha del dia anterior
	 * @author Guillermo Garcia
	 * @return la fecha del dia de ayer con el formato necesario como query param en la API
	 */
	public String getYesterdayDate() {		
		LocalDateTime localDate = LocalDateTime.now().minusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
		return localDate.format(formatter);
	}
	
	/**
	 * Metodo para obtener la fecha del dia anterior pero con el formato de la api de wm
	 * @author Miguel martinez
	 * @return la fecha del dia de ayer con el formato necesario como query param en la API
	 */
	public String getYesterdayDateWM() {		
		LocalDateTime localDate = LocalDateTime.now().minusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");		
		return localDate.format(formatter);
	}
	
	/**
	 * Metodo para obtener la fecha del dia anterior pero con el formato de la api de wm
	 * @author Miguel martinez
	 * @return la fecha del dia de ayer con el formato necesario como query param en la API
	 */
	public String getYesterdayDateIMA() {		
		LocalDateTime localDate = LocalDateTime.now().minusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");		
		return localDate.format(formatter);
}
	
	
	/**
	 * Metodo para obtener la fecha del dia siguiente
	 * @author Guillermo Garcia
	 * @return la fecha del dia de siguiente con el formato necesario como query param en la API
	 */
	public String getTomorrowDate() {		
		LocalDateTime localDate = LocalDateTime.now().plusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");		
		return localDate.format(formatter);
}
		
	
	
	
	/**
	 * Metodo utilizado para separar un string con commas en una lista de string
	 * 
	 * @author Guillermo Garcia
	 * @param un string con todos los valores separados por comas
	 * @return un List<String> con los valores del string separados
	 */		
	public List<String> getKeysArray(String separateCommasKeys) {		 
		 return Arrays.asList(separateCommasKeys.split("[,]", 0));		   
	}
	
	/**
	 * Metodo utilizado para validar los keys de un JSON
	 * 
	 * @author Guillermo Garcia
	 * @param Json es el json real en un string
	 * @param expectedJson es el json que viene del data provider separado por comas
	 * @return un Map donde el valor string es el key y el boolean es true si se encuentra en el json y false si no se encuentra
	 */
	public Map<String,Boolean> compareJsonKeys(JsonUtil json, String expectedJson) {
		//creamos un Map donde guardaremos los valores de los keys con un boolean donde true sera un key que si se encontro y false uno que no
		Map<String,Boolean> report = new HashMap<String, Boolean>();
		//Obtenemos una lista del data provider
		List<String> expectedKeys = this.getKeysArray(expectedJson);
		//Validamos cada key					
		for (String key : expectedKeys) {
			 assertTrue(json.hasJsonElement(key),"Validando que la key :" + key + " esta en el JSON de respuesta");
			 if(json.hasJsonElement(key)) {	 
	    		 report.put(key, true);	
	        	 continue;
	         }
	    	 report.put(key, false);			
		}			
	  return report;		
	}
	
	/**
	 * Metodo utilizado para validar los keys de un array dentro de un JSON
	 * 
	 * @author Guillermo Garcia
	 * @param Json es el json real en un string
	 * @param expectedJson es el json que viene del data provider separado por comas
	 * @param specificKey es el key de tipo array que contiene los keys a validar
	 * @return un map con los keys como key y un boolean donde true es que se valido y false es que no se encontro, null es que el array key no se encontro
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	public Map<String,Boolean> compareJsonKeysForJsonArray(JsonUtil json, String expectedJson, String specificKey) throws JsonMappingException, JsonProcessingException {
		//creamos un Map donde guardaremos los valores de los keys con un boolean donde true sera un key que si se encontro y false uno que no
		Map<String,Boolean> report = new HashMap<String, Boolean>();
		//Obtenemos una lista del data provider
		List<String> expectedKeys = this.getKeysArray(expectedJson);
		//Obtenemos el array
		JsonUtil holder;
		JSONObject jsonArray;
		JSONArray holderArray;
		if(json.hasJsonElement(specificKey)) {
			if(specificKey.equalsIgnoreCase("collaborators")) {
				holderArray = json.getJsonAElement("collaborators");
				holder = new JsonUtil(holderArray.getJSONObject(0).toString());				
				jsonArray = holder.getJsonObject();
			}
			else {
				jsonArray = json.getJsonOElement(specificKey);
			}			
			//iteramos el json array
			for (String expectedKey : expectedKeys) {				
				 assertTrue(jsonArray.has(expectedKey),"Validando que la key :" + expectedKey + " esta en el JSON de respuesta");
				 if(jsonArray.has(expectedKey)) {	 
		    		 report.put(expectedKey, true);	
		        	 continue;
		         }
		    	 report.put(expectedKey, false);					
			}
		}
	  return report;		 
	}
	
	
	/**
	 * Metodo utilizado para validar que los tipos de values son string o arrays en caso de no ser ninguno de estos aventara un error en un assert
	 * 
	 * @author Guillermo Garcia
	 * @param Json es el json real en un string
	 * @return Un Map de dos valores tipo string siendo la key la misma key que en el json y el value el tipo de dato
	 * @throws JsonProcessingException 
	 * @throws JsonMappingException 
	 */
	public Map<String,Boolean> areValuesStrings(JsonUtil json) throws JsonMappingException, JsonProcessingException{
		//creamos un Map donde guardaremos los valores de los keys con un boolean donde true sera un key que si se encontro y false uno que no
		Map<String,Boolean>  report = new HashMap<String,Boolean>();	
		//creamos un json util holder en caso de necesitar iterar dentro de algun array
		JsonUtil holder;
		JSONArray holderArray;
		//Obtenemos un Json
		JSONObject jsonArray = json.getJsonObject();
		Iterator<String> keysFromJson = jsonArray.keys();	
		while (keysFromJson.hasNext()) {
			String key = (String) keysFromJson.next();								
			switch (jsonArray.get(key).getClass().toString()) {
				case "class org.json.JSONObject":				
					report.put(key + " :JsonObject" , true);
					holder = new JsonUtil(json.getJsonOElement(key).toString());
					report.putAll(this.areValuesStrings(holder));				
					break;
					
				case "class org.json.JSONArray":				
					report.put(key + " :JsonArray" , true);
					holderArray = json.getJsonAElement(key);
					holder = new JsonUtil(holderArray.getJSONObject(0).toString());
					report.putAll(this.areValuesStrings(holder));
					break;
					
				case "class java.lang.String":							
					report.put(key + " :String" , true);
					break;
					
				case "class java.lang.Integer":							
					report.put(key + " : Integer" , true);
					break;
	
				default:								
					report.put(key + " :" +jsonArray.get(key).getClass().toString(), false);
					break;
			}
		}
		return report;
	}
	
	/**
	 * Regresa el response del JSON de la api de IMA
	 * 
	 * @author Guillermo Garcia
	 * @param  baseUrl es la url de la api de ima
	 * @param  Path es el path de la url de ima
	 * @param date es el queryParam updatedDate
	 * @param page es el queryParam page
	 * @param limit es el queryParam limit
	 * @return Regresa un JSONObject con la informacion de la api de IMA
	 */
	public JSONObject getApiImaResponse(String baseURL,String path,String date, int page, int limit) {
		ApiMethodsUtil api =  new ApiMethodsUtil(baseURL);
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("x-functions-key", this.getKeyApiIma());
		String pathWithQueryParams = path + "?updatedDate=" +  date + "&page=" +  page + "&limit=" +  limit;
		Response response = api.getRequestWithHeaders(pathWithQueryParams, headersList);
		return new JSONObject(response.asPrettyString());
	}
	
	
}

