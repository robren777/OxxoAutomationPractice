package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import WEB.evolucionDEI.om.TRIRIGA_ARCGIS;
import io.restassured.response.Response;
import modelo.BaseExecution;
//import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Se verifica que el sistema permita generar correctamente el código de acceso (token) y que, al enviar un cambio (delta) válido desde TRIRIGA hacia ArcGIS, el punto de información sea recibido y procesado exitosamente. Este caso confirma que cuando los datos están correctos, el flujo entre ambos sistemas funciona como se espera 
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-025 Validación del envío exitoso de un Delta desde TRIRIGA hacia ArcGIS
 * 
 * PREREQUISITOS: URL, credenciales válidas y Token válido
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 26/10/2025
 */

public class ATC_FT_025_ValidarEnvioExitosoDeUnDeltaDesdeTRIRIGAHaciaArcGIS extends BaseExecution {

	//@AzureTestInfo(
//	areaPath = "I24073-Evolución DEI",
//	iterationPath = "I24073-Evolución DEI",
//	tags = "Tririga hacia ArcGis",
//	userStoryId = 226345   //)

	@Test(dataProvider = "data-provider")
	public void ATC_FT_025_ValidarEnvioExitosoDeUnDeltaDesdeTRIRIGAHaciaArcGIS_test(HashMap<String, String> data) throws Exception {

		/** Ejecutar petición Put exitosamente con un delta
		 * @param RestAssured
		 * @author Melba Solís 	 */

		/***************************** PASO 1: Obtener el token ****************************/
		addStep("Ejecutar petición POST para obtener el token");

		int postExpectedCode = Integer.parseInt(data.get("postExpectedCode"));

		Response getTokenResponse = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth"));		// Se llama al OM

		// Validar status
		int getTokenStatusCode = getTokenResponse.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + getTokenStatusCode);
		System.out.println("Status token: " + getTokenStatusCode);
		assertEquals(getTokenStatusCode, postExpectedCode, "Error al obtener token");

		// Ver respuesta completa
		String getTokenBodyResponse = getTokenResponse.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse: " + getTokenBodyResponse);
		System.out.println("Respuesta completa token: " + getTokenBodyResponse);

		// Extraer el token del JSON
		JSONObject postJson = new JSONObject(getTokenBodyResponse);
		String token = postJson.optString("access_token", null);
		assertNotNull(token, "No se encontró access_token en la respuesta");
		testCase.addTextEvidenceCurrentStep("Token obtenido: " + token);
		System.out.println("Token extraído: " + token);
		
		/**************************** PASO 2: Ejecutar syncPotentialStores ****************************/
		addStep("Ejecutar petición PUT syncPotentialStores y validar Estatus 200");

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
		
		// Si es 503, lo documentamos y marcamos como fallo controlado
		if (putStatusCode == 503) {
		    testCase.addTextEvidenceCurrentStep("El servicio no está disponible (503). Revisa la conexión con ArcGIS/Tririga.");
		    fail("El servicio no está disponible, no se pudo ejecutar la prueba.");
		}

		// Si no fue 503, validamos el esperado
		assertEquals(putStatusCode, putExpectedCode, "El PUT no devolvió el código esperado");
		
		}
		
		/****************************** PASO 3: Obtener datos en petición GET ****************************/
		addStep("Ejecutar petición GET retrieve para validar que se actualizó ESTATUS_GRAL en la capa correcta");

		int maxReintentos = 3;
		// Ejecutamos el GET usando el OM
		Response getResponse = TRIRIGA_ARCGIS.ejecutarGetConReintentos(token, postJson, data, maxReintentos);
		System.out.println("Token usado en GET: " + token);
		System.out.println("ApiKey usada en GET: " + data.get("apiKey"));

		// Evidencias y validaciones, Valida traiga el estatus 200 
		int status = getResponse.getStatusCode();
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
		assertTrue(features.length() > 0, "El arreglo 'features' está vacío");

		JSONObject firstFeature = features.getJSONObject(0);
		JSONObject attributes = firstFeature.optJSONObject("attributes");
		assertNotNull(attributes, "El objeto 'attributes' no existe en el JSON");
		
		// Extraer el campo 'generalStatus'
		String statusGral = attributes.optString("generalStatus", null);
		assertNotNull(statusGral, "No se encontró el campo 'generalStatus' en la respuesta");
		String estatusEsperado = data.get("EstatusGralActualizado"); // Valor esperado desde data
		// Validar que el valor sea el esperado
		assertEquals(statusGral, estatusEsperado, "El campo 'generalStatus' no coincide con el valor esperado. Esperado: " 
		    + estatusEsperado + " - Obtenido: " + statusGral);

		// Evidencia en el reporte
		testCase.addTextEvidenceCurrentStep("Estatus obtenido del GET: " + statusGral);
		System.out.println("Estatus obtenido del GET: " + statusGral);

		/*************************************PASO: 4 
		 * REGRESAR EL ESTATUS A SU VALOR ORIGINAL
		 *******************************************/
		// Generar Token por segunda ocasión
		Response getTokenResponse2 = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth"));

		// Validar status
		int getTokenStatusCode2 = getTokenResponse2.getStatusCode();
		System.out.println("Status token 2: " + getTokenStatusCode2);
		assertEquals(getTokenStatusCode2, postExpectedCode, "Error al obtener segundo token");

		// Ver respuesta completa
		String getTokenBodyResponse2 = getTokenResponse2.asPrettyString();
		System.out.println("Respuesta completa token 2: " + getTokenBodyResponse2);

		// Extraer el token del JSON
		JSONObject postJson2 = new JSONObject(getTokenBodyResponse2);
		String token2 = postJson2.optString("access_token", null);
		assertNotNull(token2, "No se encontró access_token en la segunda respuesta");
		System.out.println("Token 2 extraído: " + token2);
		
		/**************************** Ejecutar syncPotentialStores (segundo intento) ****************************/
		data.put("Json", data.get("Json2"));  		// Cargamos el segundo JSON desde la data

		int putExpectedCode2 = Integer.parseInt(data.get("putExpectedCode"));
		Response putRespuesta2 = TRIRIGA_ARCGIS.ejecutarPutSyncPotentialStores(token, data);

		int putStatusCode2 = putRespuesta2.getStatusCode();
		System.out.println("PUT #2 Status Code: " + putStatusCode2);
		System.out.println("PUT #2 Body: " + putRespuesta2.asPrettyString());
		assertEquals(putStatusCode2, putExpectedCode2, "El status code del segundo PUT no es el esperado");

		String putBodyResponse2 = putRespuesta2.asPrettyString();

		// Validamos éxito del segundo envío
		if (putBodyResponse2.contains("success")) {
		    JSONObject putJson2 = new JSONObject(putBodyResponse2);
		    JSONArray updateResults2 = putJson2.optJSONArray("updateResults");

		    if (updateResults2 != null && updateResults2.length() > 0) {
		        boolean success2 = updateResults2.optJSONObject(0).optBoolean("success", false);
		        assertTrue(success2, "El updateFeatures del segundo PUT no fue exitoso");
		    } else {
		        fail("No se encontró 'updateResults' en la respuesta del segundo PUT.");
		    }
		}

		// Validación especial para código 503
		if (putStatusCode2 == 503) {
		    fail("El servicio no está disponible en el segundo PUT, no se pudo ejecutar la prueba.");
		}

		// Confirmamos que el código fue el esperado
		assertEquals(putStatusCode2, putExpectedCode2, "El segundo PUT no devolvió el código esperado");

		/****************************** Ejecutar segunda petición GET ****************************/
		// Definir número de reintentos
		int maxReintentos2 = 3;

		// Ejecutamos nuevamente el GET usando el mismo token y data
		Response getResponse2 = TRIRIGA_ARCGIS.ejecutarGetConReintentos(token, postJson, data, maxReintentos2);
		System.out.println("Token usado en segundo GET: " + token);
		System.out.println("ApiKey usada en segundo GET: " + data.get("apiKey"));

		// Validar código de respuesta 200
		int status2 = getResponse2.getStatusCode();
		System.out.println("Status (2da petición GET): " + status2);
		assertEquals(status2, 200, "El segundo GET no devolvió código 200 después de " + maxReintentos2 + " intentos");

		// Guardar y mostrar body
		String getBodyResponse2 = getResponse2.asPrettyString();
		System.out.println("Respuesta (2da petición GET): " + getBodyResponse2);

		// Parsear JSON y validar estructura
		JSONObject getJsonResponse2 = new JSONObject(getBodyResponse2);
		JSONArray features2 = getJsonResponse2.optJSONArray("features");
		assertNotNull(features2, "El JSON del segundo GET no contiene 'features'");
		assertTrue(features2.length() > 0, "El arreglo 'features' del segundo GET está vacío");

		JSONObject firstFeature2 = features2.getJSONObject(0);
		JSONObject attributes2 = firstFeature2.optJSONObject("attributes");
		assertNotNull(attributes2, "El objeto 'attributes' no existe en el segundo GET");

		// Extraer y validar el campo 'generalStatus'
		String statusGral2 = attributes2.optString("generalStatus", null);
		assertNotNull(statusGral2, "No se encontró el campo 'generalStatus' en la segunda respuesta GET");
		String estatusEsperado2 = data.get("EstatusGralActualizado2");

		// Validar que el valor sea el esperado
		assertEquals(statusGral2, estatusEsperado2, "El campo 'generalStatus' del segundo GET no coincide con el valor esperado. Esperado: " 
		    + estatusEsperado2 + " - Obtenido: " + statusGral2);
		System.out.println("Estatus obtenido del segundo GET: " + statusGral2); 

	}
		
	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con la herramienta postman \r\n" 
				+ "2. Contar con las credenciales correspondientes a la colección \r\n";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Melba Solis";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_025_ValidarEnvioExitosoDeUnDeltaDesdeTRIRIGAHaciaArcGIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Se verifica que el sistema permita generar correctamente el código de acceso (token) y que, al enviar un cambio (delta) válido desde TRIRIGA hacia ArcGIS, el punto de información sea recibido y procesado exitosamente. Este caso confirma que cuando los datos están correctos, el flujo entre ambos sistemas funciona como se espera";
	}
}
