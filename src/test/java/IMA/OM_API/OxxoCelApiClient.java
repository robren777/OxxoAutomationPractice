
package IMA.OM_API;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import modelo.TestCase;

public class OxxoCelApiClient {

    protected TestCase testCase;

    public static Map<String, String> G_Headers = new HashMap<>();

    public OxxoCelApiClient(TestCase testCase) {
        this.testCase = testCase;
    }

  
	/**
	 * Validar la respuesta de la petición
	 * 
	 * @param jsonBody          - json del request o response
	 * @param pathResulEsperado - paths de los campos del json y su respuesta
	 *                          esperada
	 * @return regresa true si la funcionalidad es exitosa
	 * @author mario.garza@gtim.com
	 * @date 21/Abril/2026
	 */
	public String validaHttpCode2(String url, int code, String body, Map<String, String> headers) {
		 RequestSpecification request = RestAssured.given()
		            .contentType(ContentType.JSON);
		    if (headers != null && !headers.isEmpty()) {
		        request.headers(headers);
		    }
		    Response response = request
		            .body(body)
		            .post(url);
		    int statusCode = response.getStatusCode();
		    String responseBody = response.getBody().asString();
			    if (statusCode != code) {
		        throw new RuntimeException(
		                "Código HTTP inesperado. Esperado: "
		                        + code
		                        + ", Obtenido: "
		                        + statusCode
		                        + ", Response: "
		                        + responseBody);
		    }
		    return responseBody;
	}

/**
	 * Método para realizar una petición Get
	 * @param obtiene url, codeEsperado y body
	 * @author Mario Garza
	 * @return Retorna el response, tiempo
	 * @date 28/04/2026
	 */
	public Map<String, Object> ejecutarPeticionConTiempo(String url, int codeEsperado, String body,
			Map<String, String> headers) {

		long inicio = System.currentTimeMillis();

		String response = validaHttpCode2(url, codeEsperado, body, headers);

		long fin = System.currentTimeMillis();

		long tiempo = fin - inicio;

		Map<String, Object> resultado = new HashMap<>();
		resultado.put("response", response);
		resultado.put("tiempo", tiempo);

		return resultado;
	}

	/**
	 * Método para realizar peticion get
	 * @param url, codeEsperado y hedears
	 * @author Mario Garza
	 * @return Retorna el response, tiempo
	 * @date 06/05/2026
	 */
	public Map<String, Object> ejecutarPeticionGETConTiempo(String url, Map<String, String> headers) {
		long inicio = System.currentTimeMillis();
		Response response = RestAssured.given().headers(headers).when().get(url).then().extract().response();
		long fin = System.currentTimeMillis();
		int statusCode = response.getStatusCode();
		String body = response.getBody().asString();
		Map<String, Object> resultado = new HashMap<>();
		resultado.put("statusCode", statusCode);
		resultado.put("response", body);
		resultado.put("tiempo", (fin - inicio));
		return resultado;
	}

   /**
	 * Método para validar el Header del Dataprovider
	 * 
	 * @author Jazmin Jimenez Torres
	 * @throws Exception
	 * @return true si contiene valor, falso si no contiene valor.
	 * @date 15/05/2026
	 * @param validaHeaders, string con el valor del Header del Dataprovider
	 * 
	 */
	public boolean validateHeaders(String validaHeaders) {

	    int ncount = 0;
	    Map<String, String> headers = new HashMap<>();

	    if (validaHeaders == null || validaHeaders.trim().isEmpty()) {
	        return false;
	    }

	    String[] pares = validaHeaders.split(";");

	    for (String par : pares) {
	        String[] keyValue = par.split(":", 2);

	        if (keyValue.length == 2) {
	            headers.put(keyValue[0].trim(), keyValue[1].trim());
	            ncount++;
	        }
	    }

	    return ncount > 0;
	}

	  /**
		 * Método para ontener el Header 
		 * @author Jazmin Jimenez Torres
		 * @throws Exception
		 * @return true si contiene valor, falso si no contiene valor.
		 * @date 15/05/2026
		 * @param validaHeaders, string con el valor del Header del Dataprovider
		 * 
		 */
	public Map<String, String> obtenerHeaders(String validaHeaders) {

	    if (validaHeaders == null || validaHeaders.trim().isEmpty()) {
	    	return Collections.emptyMap();
	    }

	    Map<String, String> headers = new HashMap<>();

	    String[] pares = validaHeaders.split(";");

	    for (String par : pares) {
	        String[] keyValue = par.split(":", 2);

	        if (keyValue.length == 2
	                && !keyValue[0].trim().isEmpty()
	                && !keyValue[1].trim().isEmpty()) {

	            headers.put(keyValue[0].trim(), keyValue[1].trim());
	        }
	    }

	    return headers;
	}
	  


}
