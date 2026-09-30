package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.json.JSONObject;
import org.testng.annotations.Test;
import WEB.evolucionDEI.om.TRIRIGA_ARCGIS;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelo.BaseExecution;
//import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Verificar que, al realizar una peticion para actualizar un delta utilizando un token de acceso que haya expirado o que sea inválido, el sistema rechace correctamente la petición y no permita la actualización del registro 
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-028 Validación del rechazo ante uso de token expirado o inválido en petición POST
 * 
 * PREREQUISITOS: URL, credenciales válidas y Token válido
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 29/09/2025
 */

public class ATC_FT_028_ValidacionDeRechazoConTokenExpiradoOInvalidoEnPeticionTRIRIGAHaciaArcGIS extends BaseExecution {

	//@AzureTestInfo(
//	areaPath = "I24073-Evolución DEI",
//	iterationPath = "I24073-Evolución DEI",
//	tags = "Tririga hacia ArcGis;Error",
//	userStoryId = 226345   //)

	@Test(dataProvider = "data-provider")
	public void ATC_FT_028_ValidacionDeRechazoConTokenExpiradoOInvalidoEnPeticionTRIRIGAHaciaArcGIS_test(HashMap<String, String> data) throws Exception {

		/** Ejecutar petición Put y validar error correspondiente
		 * @param RestAssured
		 * @author Melba Solís   */

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
		System.out.println("Token extraído: " + token);

		/**************************** PASO 2: Ejecutar syncPotentialStores ****************************/
		addStep("Ejecutar petición PUT para validar que se recibe el error esperado ante el uso de Token Expirado o Invalido");

		String putURL = data.get("putPath");
		System.out.println("URL FINAL PUT: " + putURL);

		int putExpectedCode = Integer.parseInt(data.get("putExpectedCode"));

		// Ajustamos el JSON de features
		String featuresJson = data.get("Json").replace("\n", "").replace("\r", "");
		System.out.println("FEATURES JSON: " + featuresJson);

		Response putRespuesta = RestAssured
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
		        .header("Authorization", "Bearer " + token + "42") 
		        // Body como form params
		        .formParam("f", "json")
		        .formParam("features", featuresJson)
		        .when()
		        .put(putURL);

		int putStatusCode = putRespuesta.getStatusCode();
		String putBodyInvalid = putRespuesta.asPrettyString();
		
		System.out.println("PUT Status Code: " + putStatusCode);
		System.out.println("PUT Body: " + putRespuesta.asPrettyString());
		testCase.addCodeEvidenceCurrentStep("statusCode:" + putStatusCode);
		assertEquals(putStatusCode, putExpectedCode);

		String putBodyResponse = putRespuesta.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse:" + putBodyResponse);
		
		// Reemplazar "details": sin valor por "details":[]
		String safeBody = putBodyInvalid.replace("\"details\":", "\"details\":[]");
		JSONObject bodyJson = new JSONObject(safeBody);
		JSONObject errorJson = bodyJson.optJSONObject("error");
		assertNotNull(errorJson, "La respuesta con token inválido no contiene objeto 'error'");

		String errorCode = errorJson.optString("code");
		String errorMsg = errorJson.optString("message");
		testCase.addTextEvidenceCurrentStep("Error recibido con token inválido: " + errorCode + " - " + errorMsg);

		// Validaciones
		assertEquals(errorCode, "X401000", "El código de error no coincide");
		assertTrue(errorMsg.toLowerCase().contains("expired") || errorMsg.toLowerCase().contains("invalid"),
		        "El mensaje no indica token inválido o expirado");
			
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
		return "ATC_FT_028_ValidacionDeRechazoConTokenExpiradoOInvalidoEnPeticionTRIRIGAHaciaArcGIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Verificar que, al realizar una peticion para actualizar un delta utilizando un token de acceso que haya expirado o que sea inválido, el sistema rechace correctamente la petición y no permita la actualización del registro";
	}
}
