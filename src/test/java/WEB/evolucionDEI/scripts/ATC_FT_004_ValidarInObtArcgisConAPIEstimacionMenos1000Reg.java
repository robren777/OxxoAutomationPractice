package WEB.evolucionDEI.scripts;


import static org.testng.AssertJUnit.assertTrue;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelo.BaseExecution;
import java.util.concurrent.TimeUnit;

/**
* DESCRIPCIÓN: Validar que el Portal de Workflow tenga la conexión a las tablas staging que se crearán en EBS, para recibir la información necesaria para crear la Factura y la informacion se guarde correctamente
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	-MTC_FT_002_PruebasParaAsegurarQueLosCamposRequeridosLleguenDeManeraCorrectaALasTablasDeStagingDeEBS
* 
* PREREQUISITOS:
* 	"1.- Contar con acceso a la BD de EBS"
*	"2.- Tener Insumos en el bus de Workflow en espera de la ejecucion del primer Microservicio"
*	"3.- Ejecutar el script un momento antes de que el microservicio entre en accion(cada 5 o 15 minutos, depende de la configuracion)";	
* 
* INSUMOS:
* 	-Se requiere tener insumos en workflow
* 
* @author bernardo.mejia@grupotimex.onmicrosoft.com
* @date 08/Mayo/2025
*/

public class ATC_FT_004_ValidarInObtArcgisConAPIEstimacionMenos1000Reg extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
		public void ATC_FT_004_ValidarInObtArcgisConAPIEstimacionMenos1000Reg_test(HashMap<String, String> data)
				throws Exception {

		/** Validar que la información obtenida desde Arcgis sea la misma generada por la api de menos de 1000 registros
		 * @param RestAssured
		 * @author Bernardo Alonso Mejia Ortiz
		 */		

	       /***************************** PASO 1: Obtener el token ****************************/
				addStep("Ejecutar petición POST para obtener el token");
		 
				String baseTokenURL = data.get("postPath");
				int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
		 
				// Ahora usamos solo el header con Basic Auth
				Response getTokenResponse = RestAssured
						.given()
						.relaxedHTTPSValidation()
						.header("Authorization", "Basic " + data.get("basicAuth"))
						.contentType("application/x-www-form-urlencoded")
					    .formParam("grant_type", "client_credentials")
						.when()
						.post(baseTokenURL);
				
				// Validar status
				int getTokenStatusCode = getTokenResponse.getStatusCode();
				testCase.addCodeEvidenceCurrentStep("statusCode: " + getTokenStatusCode);
				System.out.println("Status token: " + getTokenStatusCode);
				assertEquals(getTokenStatusCode, getExpectedCode, "Error al obtener token");
		 
				// Ver respuesta completa
				String getTokenBodyResponse = getTokenResponse.asPrettyString();
				testCase.addCodeEvidenceCurrentStep("bodyResponse: " + getTokenBodyResponse);
				System.out.println("Respuesta completa token: " + getTokenBodyResponse);
		 
				// Extraemos el access_token del JSON
				JSONObject getJson = new JSONObject(getTokenBodyResponse);
				String token = getJson.optString("access_token", null);
				assertNotNull(token, "No se encontró access_token en la respuesta");
				testCase.addTextEvidenceCurrentStep("Token obtenido: " + token);
				System.out.println("Token extraído: " + token);
				
				/***************************** PASO 2: Obtener datos en petición GET ****************************/
				addStep("Ejecutar petición GET retrieve para validar que se actualizó ESTATUS_GRAL en la capa correcta");
				System.out.println("=== Ejecutando petición GET con reintentos ===");

		        // Medir tiempo de inicio
		        long inicio = System.currentTimeMillis();
				
				// Parametrización de URL
				String baseGetUrl = data.get("getPath");
		 
				// Variables de control para retry
				int maxReintentos = 3;
				int intento = 0;
				int status = 0;
				Response getResponse = null;
		 
				// Ciclo de reintentos
				while (intento < maxReintentos) {
					intento++;
					System.out.println("Intento " + intento + " de " + maxReintentos);
					
					String tokenType = getJson.getString("token_type"); // normalmente "Bearer"
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
					    .queryParam("where", " " + data.get("objectId"))
					    //.queryParam("where", "OBJECTID = " + data.get("objectId"))
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
					if (status == 200) {
						break; // Éxito, salimos del ciclo
					}
		 
					// 🔹 Espera controlada entre intentos (1 seg)
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}

		        // Medir tiempo de fin
		        long fin = System.currentTimeMillis();
		        long duracion = fin - inicio;				

				System.out.println("Token usado en GET: " + token);
				System.out.println("ApiKey usada en GET: " + data.get("apiKey"));
		 
				// Validación final después de reintentos
				testCase.addCodeEvidenceCurrentStep("statusCode: " + status);
				System.out.println("Status: " + status);
				int getStatusCode = getResponse.getStatusCode();
				assertEquals(status, getStatusCode, "El GET no devolvió 200 después de " + maxReintentos + " intentos");

				// Obtener la cantidad de registros (asumiendo que es un array JSON)
				int cantidadRegistros = getResponse.jsonPath().getList("features").size();

		        // Mostrar resultados
		        System.out.println("⏱ Tiempo de respuesta: " + duracion/1000 + " ms");
		        System.out.println("📦 Cantidad de registros: " + cantidadRegistros);
		        testCase.addCodeEvidenceCurrentStep("⏱ Tiempo de respuesta: " + duracion/1000 + " ms" + "  📦 Cantidad de registros: " + cantidadRegistros);
		        
				// Guardar y mostrar body completo
				String getBodyResponse = getResponse.asPrettyString();
		 
				// Parsear JSON y validar datos
				JSONObject getJsonResponse = new JSONObject(getBodyResponse);
				JSONArray features = getJsonResponse.optJSONArray("features");
				assertNotNull(features, "El JSON no contiene 'features'");
				//assertTrue(features.length() > 0, "El arreglo 'features' está vacío");
				assertTrue("El arreglo 'features' está vacío",features.length() > 0 );
				//assertTrue(String message, boolean condition);

				JSONObject firstFeature = features.getJSONObject(0);
				JSONObject attributes = firstFeature.optJSONObject("attributes");
				assertNotNull(attributes, "El objeto 'attributes' no existe en el JSON");		

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarInObtArcgisConAPIEstimacionMenos1000Reg";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la información obtenida desde Arcgis sea la misma generada por la api de menos de 1000 registros";
	}

	@Override
	public String setTestDesigner() {
		return "bernardo.mejia@grupotimex.onmicrosoft.com";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.- Se ingresa a Postman \r\n"
		+ "		2.- Se realiza una petición POST para obtener el Token\r\n"
		+ "		3.- Se coloca el Token generado y el JSON con menos de 1000 Deltas\r\n"
		+ "		4.- se realiza petición  POST con menos de 1000 registros Delta\r\n"		
		+ "		5.- Se valida que se hayan actualizado los menos de 1000 Deltas\n";		
	}

	@Override
	public void beforeTest() {

	}
}
