package Mobile.util.TRH;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import org.checkerframework.checker.units.qual.s;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import io.restassured.response.Response;
import utils.ApiMethodsUtil;
import utils.JsonUtil;
import utils.password.PasswordUtil;

public class Ima_Trh {
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
	 * Metodo para obtener la fecha actual menos los dias solicitados
	 * @author GTIM
	 * @param minusDays = es la cantidad de dias para atras que quieres recibir
	 * @return la fecha del dia solicitado con el formato necesario como query param en la API
	 */
	public String getYesterdayDate(int minusDays) {		
		LocalDateTime localDate = LocalDateTime.now().minusDays(minusDays);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
		return localDate.format(formatter);
	}
	
	/**
	 * Metodo para obtener la fecha del dia siguiente
	 * @author GTIM
	 * @return la fecha del dia de siguiente con el formato necesario como query param en la API
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
		
	/** Metodo para obtener la fecha del dia actual
	 * @author GTIM
	 * @return la fecha del dia actual con el formato necesario como query param en la API
	 */
	public String getActualDate() {		
		LocalDateTime localDate = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
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
	 * Metodo para comparar las dos celdas de un archivo de excel
	 * @param cell1 celda del excel
	 * @param cell2 celda del excel
	 * @return el estatus que retorna si es igual o no
	 * @author GTIM
	 */
	public boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2)
    	{
        if((cell1 == null) && (cell2 == null))
        {        	
            return true;
        }
        else if((cell1 == null) || (cell2 == null))
        {        
            return false;
        }
        
        boolean equalCells = false;
        CellType type1 = cell1.getCellType();
        CellType type2 = cell2.getCellType();
        if (type1 == type2)
        {
                switch (cell1.getCellType())
                {
                    case FORMULA:
                        if (cell1.getCellFormula().equals(cell2.getCellFormula()))
                        {
                            equalCells = true;
                        }
                        break;
                    case NUMERIC:
                        if (cell1.getNumericCellValue() == cell2.getNumericCellValue())
                        {
                            equalCells = true;
                        }
                        break;
                    case STRING:
                        if (cell1.getStringCellValue().equals(cell2.getStringCellValue()))
                        {
                            equalCells = true;
                        }
                        break;
                    case BLANK:
                        if (cell2.getCellType() == CellType.BLANK)
                        {
                            equalCells = true;
                        }
                        break;
                    case BOOLEAN:
                        if (cell1.getBooleanCellValue() == cell2.getBooleanCellValue())
                        {
                            equalCells = true;
                        }
                        break;
                    case ERROR:
                        if (cell1.getErrorCellValue() == cell2.getErrorCellValue())
                        {
                            equalCells = true;
                        }
                        break;
                    default:
                        if (cell1.getStringCellValue().equals(cell2.getStringCellValue()))
                        {
                            equalCells = true;
                        }
                        break;
                }

        }
        else
        {        
            return false;
        }
        return equalCells;
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
	
	
	/**
	 * Validar dos diferentes filas y valida si es igual
	 * @param row1
	 * @param row2
	 * @return Se regresa el status si son iguales o no
	 * @author GTIM
	 */
    public boolean compareTwoRows(XSSFRow row1, XSSFRow row2)
    {
        if((row1 == null) && (row2 == null))
        {
            return true;
        }
        else if((row1 == null) || (row2 == null))
        {
            return false;
        }
        int firstCell1 = row1.getFirstCellNum();
        int lastCell1 = row1.getLastCellNum();
        boolean equalRows = true;

        for(int i=firstCell1; i <= lastCell1; i++)
        {
            XSSFCell cell1 = row1.getCell(i);
            XSSFCell cell2 = row2.getCell(i);
            if(!compareTwoCells(cell1, cell2))
            {
                equalRows = false;
                System.err.println("Cell "+i+" | Not Equal");
            }
        }
        return equalRows;
    }
    
    /**
     * Se comparan dos hojas de calcula para validar si son iguales
     * @param sheet1
     * @param sheet2
     * @return el status de si las hojas de calculo son iguales
     * @author GTIM
     */
    public boolean compareTwoSheets(XSSFSheet sheet1, XSSFSheet sheet2)
    {
        int firstRow1 = sheet1.getFirstRowNum();
        int lastRow1 = sheet1.getLastRowNum();
        boolean equalSheets = true;
        for(int i=firstRow1; i <= lastRow1; i++)
        
        {
            XSSFRow row1 = sheet1.getRow(i);
            XSSFRow row2 = sheet2.getRow(i);
            if(!compareTwoRows(row1, row2))
            {
                equalSheets = false;
                System.out.println(" Row "+i+" | Not Equal");
            }
        }
        return equalSheets;        
    }
    
 public boolean compareExcelVsJson() {
	 return false;
 }
	
    
       
}
