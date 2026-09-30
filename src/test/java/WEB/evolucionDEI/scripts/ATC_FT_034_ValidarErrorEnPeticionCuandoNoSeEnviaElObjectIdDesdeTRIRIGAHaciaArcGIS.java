package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.json.JSONObject;
import org.testng.annotations.Test;
import WEB.evolucionDEI.om.TRIRIGA_ARCGIS;
import io.restassured.response.Response;
import modelo.BaseExecution;
//import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Valida que el cliente reciba un código de respuesta 200 cuando el valor de ObjectId no se pueda actualizar, pero tiene que recibir un false (lanza un codigo 400 en el body)
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-034 Validar petición POST cuando el valor ObjectId no se envia en el request
 * 
 * PREREQUISITOS: URL, credenciales válidas y Token válido
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 01/10/2025
 */

public class ATC_FT_034_ValidarErrorEnPeticionCuandoNoSeEnviaElObjectIdDesdeTRIRIGAHaciaArcGIS extends BaseExecution {

	//@AzureTestInfo(
//	areaPath = "I24073-Evolución DEI",
//	iterationPath = "I24073-Evolución DEI",
//	tags = "Tririga hacia ArcGis;Error",
//	userStoryId = 226345   //)

	@Test(dataProvider = "data-provider")
	public void ATC_FT_034_ValidarErrorEnPeticionCuandoNoSeEnviaElObjectIdDesdeTRIRIGAHaciaArcGIS_test(HashMap<String, String> data) throws Exception {

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

		/**************************** PASO 2: Ejecutar syncPotentialStores ****************************/
		addStep("Ejecutar petición PUT para validar que se recibe el error esperado cuando no se envia el ObjectId");

		int putExpectedCode = Integer.parseInt(data.get("putExpectedCode"));

		Response putRespuesta = TRIRIGA_ARCGIS.ejecutarPutSyncPotentialStores(token, data);

		int putStatusCode = putRespuesta.getStatusCode();
		String putBodyInvalid = putRespuesta.asPrettyString();
		
		System.out.println("PUT Status Code: " + putStatusCode);
		System.out.println("PUT Body: " + putRespuesta.asPrettyString());
		testCase.addCodeEvidenceCurrentStep("statusCode:" + putStatusCode);
		assertEquals(putStatusCode, putExpectedCode);

		String putBodyResponse = putRespuesta.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse:" + putBodyResponse);
		
		// Reemplazar "details": sin valor por "details":[]
		JSONObject bodyJson = new JSONObject(putBodyInvalid);
		JSONObject errorJson = bodyJson.optJSONObject("error");
		assertNotNull(errorJson, "La respuesta no contiene objeto 'error'");
		
		String errorCode = errorJson.optString("code");
		String errorMsg = errorJson.optString("message");
		testCase.addTextEvidenceCurrentStep("Error recibido: " + errorCode + " - " + errorMsg);

		// Validaciones
		assertEquals(errorCode, "X400000", "El código de error no coincide");
		assertTrue(errorMsg.toLowerCase().contains("malformed") || errorMsg.toLowerCase().contains("invalid"),
		        "El mensaje no indica el mensaje esperado");
		
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
		return "ATC_FT_034_ValidarErrorEnPeticionCuandoNoSeEnviaElObjectIdDesdeTRIRIGAHaciaArcGIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Valida que el cliente reciba un código de respuesta 200 cuando el valor de ObjectId no se pueda actualizar, pero tiene que recibir un false (lanza un codigo 400 en el body)";
	}
}
