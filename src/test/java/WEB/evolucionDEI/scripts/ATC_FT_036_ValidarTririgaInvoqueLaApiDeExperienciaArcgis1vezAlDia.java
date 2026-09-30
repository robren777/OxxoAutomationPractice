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
 * DESCRIPCIÓN: Tririga deberá invocar la Api de Experiencia arcgis 1 vez al día y la frecuencia deberá ser configurable, para obtener la información descrita en la columna campos
*Tririga realiza el envió  de la información 1  vez al dia a las 12:00 a.m. y 12:00 p.m.
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-036 Validar Tririga invoque la Api de Experiencia arcgis 1 vez al día 
 * 
 * PREREQUISITOS: URL, credenciales válidas y Token válido
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 07/10/2025
 */

public class ATC_FT_036_ValidarTririgaInvoqueLaApiDeExperienciaArcgis1vezAlDia extends BaseExecution {

	//@AzureTestInfo(
//	areaPath = "I24073-Evolución DEI",
//	iterationPath = "I24073-Evolución DEI",
//	tags = "Tririga hacia ArcGis",
//	userStoryId = 226345   //)

	@Test(dataProvider = "data-provider")
	public void ATC_FT_036_ValidarTririgaInvoqueLaApiDeExperienciaArcgis1vezAlDia_test(HashMap<String, String> data) throws Exception {

		/**
		 * Ejecutar petición Put 1 vez
		 * @param RestAssured
		 * @author Melba Solís
		 */

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
		addStep("Ejecutar petición PUT una vez al dia exitosamente");

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
		    boolean success = putJson.optJSONArray("updateResults").optJSONObject(0).optBoolean("success", false);
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
		
		/*************************************PASO: 4 
		 * REGRESAR EL ESTATUS A SU CALOR ORIGINAL
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
		data.put("Json", data.get("Json2"));  // Cargamos el segundo JSON desde la data

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
		return "ATC_FT_036_ValidarTririgaInvoqueLaApiDeExperienciaArcgis1vezAlDia_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Tririga deberá invocar la Api de Experiencia arcgis 1 vez al día y la frecuencia deberá ser configurable, para obtener la información descrita en la columna campos Tririga realiza el envió  de la información 1  vez al dia a las 12:00 a.m. y 12:00 p.m.";
	}
}
