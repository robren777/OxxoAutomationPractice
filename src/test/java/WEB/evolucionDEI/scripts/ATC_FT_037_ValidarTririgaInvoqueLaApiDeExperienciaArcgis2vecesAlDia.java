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
 * DESCRIPCIÓN: Tririga deberá invocar la Api de Experiencia arcgis 2 veces al día y la frecuencia deberá ser configurable, para obtener la información descrita en la columna campos
*Tririga realiza el envió  de la información 2 veces al dia a las 12:00 a.m. y 12:00 p.m.
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-037 Validar Tririga invoque la Api de Experiencia arcgis 2 veces al día 
 * 
 * PREREQUISITOS: URL, credenciales válidas y Token válido
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 07/10/2025
 */

public class ATC_FT_037_ValidarTririgaInvoqueLaApiDeExperienciaArcgis2vecesAlDia extends BaseExecution {

	//@AzureTestInfo(
//	areaPath = "I24073-Evolución DEI",
//	iterationPath = "I24073-Evolución DEI",
//	tags = "Tririga hacia ArcGis",
//	userStoryId = 226345   //)

	@Test(dataProvider = "data-provider")
	public void ATC_FT_037_ValidarTririgaInvoqueLaApiDeExperienciaArcgis2vecesAlDia_test(HashMap<String, String> data) throws Exception {

		/**
		 * Ejecutar petición Put 2 veces
		 * @param RestAssured
		 * @author Melba Solís 	 */

		/***************************** PASO 1: Obtener el token ****************************/
		addStep("Ejecutar petición POST para obtener el token");

		int postExpectedCode = Integer.parseInt(data.get("postExpectedCode"));
		
		Response getTokenResponse = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth")); // Se llama al OM

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

		/**************************** PASO 2: Ejecutar syncPotentialStores ****************************/
		addStep("Ejecutar petición PUT una vez");

		int putExpectedCode = Integer.parseInt(data.get("putExpectedCode"));

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

		// Si es 503, lo documentamos y marcamos como fallo controlado
		if (putStatusCode == 503) {
		    testCase.addTextEvidenceCurrentStep("El servicio no está disponible (503). Revisa la conexión con ArcGIS/Tririga.");
		    fail("El servicio no está disponible, no se pudo ejecutar la prueba.");
		}

		// Si no fue 503, validamos el esperado
		assertEquals(putStatusCode, putExpectedCode, "El PUT no devolvió el código esperado");
		
		}
		
		/****************************** PASO 3: Obtener el token ****************************/
		addStep("Ejecutar petición POST para obtener el token");
		
		Response postTokenResponse2 = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth"));

		assertEquals(postTokenResponse2.getStatusCode(), postExpectedCode, "Error al obtener token (2)");
		String token2 = new JSONObject(postTokenResponse2.asPrettyString()).getString("access_token");
		String postTokenBodyResponse2 = postTokenResponse2.asPrettyString();
		int postTokenStatusCode2 = postTokenResponse2.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + postTokenStatusCode2);	
		testCase.addCodeEvidenceCurrentStep("bodyResponse: " + postTokenBodyResponse2);
		testCase.addTextEvidenceCurrentStep("Token2: " + token2);
			
		/**************************** PASO 4: Ejecutar syncPotentialStores ****************************/
		addStep("Ejecutar petición PUT segunda vez");
		data.put("Json", data.get("Json2"));  // Cargamos el segundo JSON desde la data
		
		Response putRespuesta2 = TRIRIGA_ARCGIS.ejecutarPutSyncPotentialStores(token, data);

		int putStatusCode2 = putRespuesta2.getStatusCode();
		System.out.println("PUT Status Code: " + putStatusCode2);
		System.out.println("PUT Body: " + putRespuesta2.asPrettyString());
		testCase.addCodeEvidenceCurrentStep("statusCode:" + putStatusCode2);
		assertEquals(putStatusCode2, putExpectedCode, "El status code del segundo PUT no es el esperado");

		String putBodyResponse2 = putRespuesta2.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse:" + putBodyResponse2);

		// Validamos éxito
		if (putBodyResponse2.contains("success")) {
		    JSONObject putJson2 = new JSONObject(putBodyResponse2);
		    boolean success2 = putJson2.optJSONArray("updateResults")
		                             .optJSONObject(0)
		                             .optBoolean("success", false);
		    testCase.addTextEvidenceCurrentStep("Resultado update: " + success2);
		    assertTrue(success2, "El updateFeatures no fue exitoso");
			
		System.out.println("PUT Status Code: " + putStatusCode2);
		System.out.println("PUT Body: " + putRespuesta2.asPrettyString());

		// Si es 503, lo documentamos y marcamos como fallo controlado
		if (putStatusCode2 == 503) {
		    testCase.addTextEvidenceCurrentStep("El servicio no está disponible (503). Revisa la conexión con ArcGIS/Tririga.");
		    fail("El servicio no está disponible, no se pudo ejecutar la prueba.");
		}

		// Si no fue 503, validamos el esperado
		assertEquals(putStatusCode2, putExpectedCode, "El PUT no devolvió el código esperado");
			
		}
		/*************************************PASO: 4 
		 * REGRESAR EL ESTATUS A SU CALOR ORIGINAL
		 *******************************************/
		Response postTokenResponse3 = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth"));

		assertEquals(postTokenResponse3.getStatusCode(), postExpectedCode, "Error al obtener token (2)");
		
		/**************************** Ejecutar syncPotentialStores (segundo intento) ****************************/
		data.put("Json", data.get("Json3"));  // Cargamos el segundo JSON desde la data

		int putExpectedCode3 = Integer.parseInt(data.get("putExpectedCode"));
		Response putRespuesta3 = TRIRIGA_ARCGIS.ejecutarPutSyncPotentialStores(token, data);

		int putStatusCode3 = putRespuesta3.getStatusCode();
		System.out.println("PUT #2 Status Code: " + putStatusCode3);
		System.out.println("PUT #2 Body: " + putRespuesta3.asPrettyString());
		assertEquals(putStatusCode3, putExpectedCode3, "El status code del segundo PUT no es el esperado");

		String putBodyResponse3 = putRespuesta3.asPrettyString();

		// Validamos éxito del segundo envío
		if (putBodyResponse3.contains("success")) {
		    JSONObject putJson3 = new JSONObject(putBodyResponse3);
		    JSONArray updateResults3 = putJson3.optJSONArray("updateResults");

		    if (updateResults3 != null && updateResults3.length() > 0) {
		        boolean success3 = updateResults3.optJSONObject(0).optBoolean("success", false);
		        assertTrue(success3, "El updateFeatures del segundo PUT no fue exitoso");
		    } else {
		        fail("No se encontró 'updateResults' en la respuesta del segundo PUT.");
		    }
		}

		// Validación especial para código 503
		if (putStatusCode3 == 503) {
		    fail("El servicio no está disponible en el segundo PUT, no se pudo ejecutar la prueba.");
		}

		// Confirmamos que el código fue el esperado
		assertEquals(putStatusCode3, putExpectedCode3, "El segundo PUT no devolvió el código esperado");
 
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
		return "ATC_FT_037_ValidarTririgaInvoqueLaApiDeExperienciaArcgis2vecesAlDia_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Tririga deberá invocar la Api de Experiencia arcgis 2 veces al día y la frecuencia deberá ser configurable, para obtener la información descrita en la columna campos Tririga realiza el envió  de la información 2 veces al dia a las 12:00 a.m. y 12:00 p.m.";
	}
}
