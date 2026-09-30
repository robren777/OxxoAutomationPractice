package IMA.OM_API;

import java.util.HashMap;
import java.util.Map;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import modelo.TestCase;

public class OxxoCelECA {
	
    protected TestCase testCase;
    public OxxoCelECA(TestCase testCase) {
        this.testCase = testCase;
    }

	/**
	 * Realizar comunicación y validar respuesta del response.
	 * @author hugo_palacios@epamneoris.com
	 * @date 01/julio/2026
	 */
	public String realizarPeticionHTTP(String urlEndpoint, int httpCode, String bodyRequest, Map<String, String> validaHeaders) {
		 RequestSpecification request = RestAssured.given()
		            .contentType(ContentType.JSON);
		 
		  			    if (validaHeaders != null && !validaHeaders.isEmpty()) {
		        request.headers(validaHeaders);
		    }
		    Response response = request
		            .body(bodyRequest)
		            .post(urlEndpoint);
		    int statusCode = response.getStatusCode();
		    String responseBody = response.getBody().asString();
		    System.out.println("Response: " + responseBody);
		    System.out.println("Status: " + statusCode);
		    
		    if (statusCode != httpCode) {

		        String error =
		                "Código HTTP inesperado. Esperado: "
		                        + httpCode
		                        + ", Obtenido: "
		                        + statusCode
		                        + ", Response: "
		                        + responseBody;

		        testCase.addCodeEvidenceCurrentStep(error);

		        throw new RuntimeException(error);
		    		        
		    }
		    return responseBody;
		   
	}
	
	
	/**
	 * Se realiza metodo para obtener datos del Header.
	 * @author hugo_palacios@epamneoris.com
	 * @date 01/julio/2026
	 */	
	 public Map<String, String> obtenerHeaders(String validaHeaders) {

		 Map<String, String> headers = new HashMap<>();

		 if (validaHeaders == null || validaHeaders.trim().isEmpty()) {
	        return headers;
		 }

		 String[] pares = validaHeaders.split("\\r?\\n");
		 for (String par : pares) {
			 String[] keyValue = par.split(":", 2);

			 	if (keyValue.length == 2) {
	            headers.put(
	                    keyValue[0].trim(),
	                    keyValue[1].trim());
	        }
	    }
	    return headers;
	}
}

