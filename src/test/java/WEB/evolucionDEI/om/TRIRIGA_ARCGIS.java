package WEB.evolucionDEI.om;
 
import java.util.Map;
import org.json.JSONObject;
import io.restassured.http.ContentType;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.concurrent.TimeUnit;
 
public class TRIRIGA_ARCGIS {
	
	/* Metodo que ejecuta peticion POST para generar Token
	 * @author Melba Solis
	 * @date 02/Octubre/2025
	 * @param basicAuth   Cadena de autenticación en base64.
     * @param postPath    URL del servicio de token.
     * @param testCase    Instancia para registrar evidencias.
     * @return String     Token generado (access_token).   */
	
    public static Response obtenerToken(String postPath, String basicAuth) {
       
    	return RestAssured
                .given()
                .relaxedHTTPSValidation()
                .header("Authorization", "Basic " + basicAuth)
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .when()
                .post(postPath);
        
    }
    
    /* Ejecuta la petición GET con reintentos y devuelve la última respuesta obtenida.
	 * @author Melba Solis
     * @date 02/Octubre/2025
     * @param token       Token de autorización.
     * @param getJson     Objeto JSON que contiene el tipo de token (ej. Bearer).
     * @param data        Datos de prueba (parámetros de cabecera y query).
     * @param maxReintentos Número máximo de reintentos permitidos.
     * @return Response   Respuesta del servicio GET.  */
    
        public static Response ejecutarGetConReintentos(String token, JSONObject postJson, Map<String, String> data, int maxReintentos) {
            System.out.println("=== Ejecutando petición GET con reintentos ===");
            
            int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
            String baseGetUrl = data.get("getPath");
            int intento = 0;
            int status = 0;
            Response getResponse = null;
 
            while (intento < maxReintentos) {
                intento++;
                System.out.println("Intento " + intento + " de " + maxReintentos);
 
                String tokenType = postJson.getString("token_type");
                String authHeader = tokenType + " " + token;
 
                getResponse = RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .header("Authorization", authHeader)
                    .header("Tracking-Id", data.get("trackingId"))
                    .header("Channel-Id", data.get("channelId"))
                    .header("Country-Code", data.get("countryCode"))
                    .header("Language", data.get("language"))
                    .header("x-Gateway-APIKey", data.get("apiKey"))
                    .header("referer", data.get("referer"))
                    .accept(ContentType.JSON)
                    .queryParam("where", "OBJECTID = " + data.get("objectId"))
                    .queryParam("spatialRel", "esriSpatialRelIntersects")
                    .queryParam("units", "esriSRUnit_Foot")
                    .queryParam("geometryType", "esriGeometryEnvelope")
                    .queryParam("outFields", "*")
                    .queryParam("returnGeometry", "false")
                    .queryParam("returnDistinctValues", "false")
                    .queryParam("returnIdsOnly", "false")
                    .queryParam("returnCountOnly", "false")
                    .queryParam("returnExtentOnly", "false")
                    .queryParam("returnZ", "false")
                    .queryParam("returnM", "false")
                    .queryParam("multipatchOption", "xyFootprint")
                    .queryParam("returnTrueCurves", "false")
                    .queryParam("returnExceededLimitFeatures", "false")
                    .queryParam("returnCentroid", "false")
                    .queryParam("timeReferenceUnknownClient", "false")
                    .queryParam("sqlFormat", "none")
                    .queryParam("f", "json")
                    .when()
                    .get(baseGetUrl);
 
                status = getResponse.getStatusCode();
                if (status == getExpectedCode) {
                    break;
                }
 
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
 
            return getResponse;
        }
        
        /*  Ejecuta la petición PUT syncPotentialStores y devuelve la respuesta obtenida.
         * @author Melba Solis
         * @date 03/Octubre/2025
         * @param token Token de autorización obtenido previamente.
         * @param data  Datos de prueba que incluyen URL, headers y parámetros necesarios.
         * @return Response Respuesta completa del servicio PUT.
         */
        
        public static Response ejecutarPutSyncPotentialStores(String token, Map<String, String> data) {
            System.out.println("=== Ejecutando petición PUT syncPotentialStores ===");
 
            String putURL = data.get("putPath");
            String featuresJson = data.get("Json").replace("\n", "").replace("\r", "");
            System.out.println("FEATURES JSON: " + featuresJson);
            int maxReintentos = 3;
            int intento = 0;
            Response respuesta = null;
 
            while (intento < maxReintentos) {
                intento++;
                System.out.println("Intento #" + intento + " de " + maxReintentos);
 
                respuesta = RestAssured
                    .given()
                    .relaxedHTTPSValidation()
                    .contentType("application/x-www-form-urlencoded")
                    .accept("application/json")
                    // Headers obligatorios
                    .header("Tracking-Id", data.get("trackingId"))
                    .header("Channel-Id", data.get("channelId"))
                    .header("Country-Code", data.get("countryCode"))
                    .header("Language", data.get("language"))
                    .header("x-Gateway-APIKey", data.get("apiKey"))
                    .header("referer", data.get("referer"))
                    .header("Authorization", "Bearer " + token)
                    // Body como form params
                    .formParam("f", "json")
                    .formParam("features", featuresJson)
                    .when()
                    .put(putURL);
 
                int status = respuesta.getStatusCode();
                System.out.println("Status code obtenido: " + status);
 
                // Si el status es 200 o 400 (casos válidos), o cualquier código esperado definido, salimos
                String expectedCodeStr = data.get("putExpectedCode");
                int expectedCode = expectedCodeStr != null ? Integer.parseInt(expectedCodeStr) : 200;
 
                if (status == expectedCode) {
                    System.out.println("Código esperado recibido (" + status + ").");
                    break;
                }
 
                // Si el código es 5xx, hacemos reintento sin pausa bloqueante
                if (status >= 500 && intento < maxReintentos) {
                    System.out.println("Respuesta " + status + " recibida. Reintentando...");
                    continue; // Reintenta sin detener el hilo
                } else {
                    // Cualquier otro código no esperado rompe el ciclo
                    break;
                }
            }
 
            return respuesta;
        }

}
