package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import modelo.BaseExecution;
import static org.junit.Assert.assertTrue;
import WEB.evolucionDEI.om.TRIRIGA_ARCGIS;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

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

public class ATC_FT_002_PuntEstatusNegociacionSeExponganViaAPI_TRIRIGA extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	
	public void ATC_FT_002_PuntEstatusNegociacionSeExponganViaAPI_TRIRIGA_test(HashMap<String, String> data) throws Exception {
	
		/**
		 * Revisar cada punto para ver si son iguales a  estatus de \"Negociacion\"
		 * @param RestAssured
		 * @author Bernardo Alonso Mejia Ortiz
		 */
 
		/****************************** PASO 1: Obtener el token ****************************/
		addStep("Ejecutar petición POST para obtener el token");
 
		int postExpectedCode = Integer.parseInt(data.get("postExpectedCode"));
		
		// Se llama al OM
		Response postTokenResponse = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth"));		
		
		// Validar status
		int postTokenStatusCode = postTokenResponse.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + postTokenStatusCode);
		System.out.println("Status token: " + postTokenStatusCode);
		assertEquals(postTokenStatusCode, postExpectedCode, "Error al obtener token");
 
		String postTokenBodyResponse = postTokenResponse.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse: " + postTokenBodyResponse);
		System.out.println("Respuesta completa token: " + postTokenBodyResponse);
 
		
		// Ver respuesta completa
		String getTokenBodyResponse = postTokenResponse.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse: " + getTokenBodyResponse);
		System.out.println("Respuesta completa token: " + getTokenBodyResponse);		
		
		// Extraemos el access_token del JSON
		JSONObject postJson = new JSONObject(postTokenBodyResponse);
		
		String token = postJson.getString("access_token");
		assertNotNull(token, "No se encontró access_token en la respuesta");
		testCase.addTextEvidenceCurrentStep("Token obtenido: " + token);
 
		/**************************** PASO 2: Ejecutar syncPotentialStores ****************************/
		addStep("Ejecutar petición PUT syncPotentialStores y validar Estatus 200");
 
		String putURL = data.get("putPath");
		System.out.println("URL FINAL PUT: " + putURL);
 
		int putExpectedCode = Integer.parseInt(data.get("putExpectedCode"));
 
		// Ejecutamos el PUT usando el OM
		Response putRespuesta = TRIRIGA_ARCGIS.ejecutarPutSyncPotentialStores(token, data);

		int putStatusCode = putRespuesta.getStatusCode();
		System.out.println("PUT Status Code: " + putStatusCode);
		System.out.println("PUT Body: " + putRespuesta.asPrettyString());
		testCase.addCodeEvidenceCurrentStep("statusCode:" + putStatusCode);
		assertEquals(putStatusCode, putExpectedCode);
 
		String putBodyResponse = putRespuesta.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse:" + putBodyResponse);
 
		// Validamos éxito
		if (putBodyResponse.contains("success")) {
		    JSONObject putJson = new JSONObject(putBodyResponse);
		    boolean success = putJson.optJSONArray("updateResults")
		                             .optJSONObject(0)
		                             .optBoolean("success", false);
		    testCase.addTextEvidenceCurrentStep("Resultado update: " + success);
		    assertTrue(success, "El updateFeatures no fue exitoso");
		
		System.out.println("PUT Status Code: " + putStatusCode);
		System.out.println("PUT Body: " + putRespuesta.asPrettyString());
		testCase.addCodeEvidenceCurrentStep("statusCode:" + putStatusCode);
 
		// Si es 503, lo documentamos y marcamos como fallo controlado
		if (putStatusCode == 503) {
		    testCase.addTextEvidenceCurrentStep("El servicio no está disponible (503). Revisa la conexión con ArcGIS/Tririga.");
		    fail("El servicio no está disponible, no se pudo ejecutar la prueba.");
		}
 
		// Si no fue 503, validamos el esperado
		assertEquals(putStatusCode, putExpectedCode, "El PUT no devolvió el código esperado");
		}	
		
	       /***************************** PASO 1: Obtener el token ****************************/
			addStep("Ejecutar petición POST para obtener el token");
		 
				//String baseTokenURL = data.get("postPath");
				int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
				
				// Se llama al OM
				Response getTokenResponse = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth"));

				// Validar status
				int getTokenStatusCode = getTokenResponse.getStatusCode();
				testCase.addCodeEvidenceCurrentStep("statusCode: " + getTokenStatusCode);
				System.out.println("Status token: " + getTokenStatusCode);
				assertEquals(getTokenStatusCode, getExpectedCode, "Error al obtener token");
		 
				// Ver respuesta completa
				String getTokenBodyResponse1 = getTokenResponse.asPrettyString();
				testCase.addCodeEvidenceCurrentStep("bodyResponse: " + getTokenBodyResponse1);
				System.out.println("Respuesta completa token: " + getTokenBodyResponse1);
		 
				// Extraemos el access_token del JSON
				JSONObject getJson1 = new JSONObject(getTokenBodyResponse1);
				String token1 = getJson1.optString("access_token", null);
				assertNotNull(token, "No se encontró access_token en la respuesta");
				testCase.addTextEvidenceCurrentStep("Token obtenido: " + token1);
				System.out.println("Token extraído: " + token1);
				
				/***************************** PASO 2: Obtener datos en petición GET ****************************/
				addStep("Ejecutar petición GET retrieve para validar que se actualizó ESTATUS_GRAL en la capa correcta");
				System.out.println("=== Ejecutando petición GET con reintentos ===");
		 
				// Variables de control para retry
				int maxReintentos = 3;
				
				// Ejecutamos el GET usando el OM
				Response getResponse = TRIRIGA_ARCGIS.ejecutarGetConReintentos(token1, getJson1, data, maxReintentos);
				System.out.println("Token usado en GET: " + token1);
				System.out.println("ApiKey usada en GET: " + data.get("apiKey"));
				int status = getResponse.getStatusCode();			
				
				// Validación final después de reintentos
				testCase.addCodeEvidenceCurrentStep("statusCode: " + status);
				System.out.println("Status: " + status);
				int getStatusCode = getResponse.getStatusCode();
				assertEquals(status, getStatusCode, "El GET no devolvió 200 después de " + maxReintentos + " intentos");
		 
				// Guardar y mostrar body completo
				String getBodyResponse = getResponse.asPrettyString();
				testCase.addCodeEvidenceCurrentStep("bodyResponse: " + getBodyResponse);
				System.out.println("Respuesta: " + getBodyResponse);
		 
				// Parsear JSON y validar datos
				JSONObject getJsonResponse = new JSONObject(getBodyResponse);
				JSONArray features = getJsonResponse.optJSONArray("features");
				assertNotNull(features, "El JSON no contiene 'features'");
				assertTrue("El arreglo 'features' está vacío", features.length() > 0);
				
				JSONObject firstFeature = features.getJSONObject(0);
				JSONObject attributes = firstFeature.optJSONObject("attributes");
				assertNotNull(attributes, "El objeto 'attributes' no existe en el JSON");	

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_PuntEstatusNegociacionSeExponganViaAPI_TRIRIGA";
	}

	@Override
	public String setTestDescription() {
		return "Revisar cada punto para ver si son iguales a  estatus de \"Negociacion\"";
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
		+ "		2.- Se realiza una petición POST para obtener el Token \r\n"
		+ "		3.- Se coloca el Token generado y el JSON con 1 Deltas con STATUS= \\\"Negociacion\\\" \r\n"				
		+ "		4.- Se buscan ver si puntos con estatus \"Negociación\" se expongan vía API.\n";		
	}

	@Override
	public void beforeTest() {
	}
}
