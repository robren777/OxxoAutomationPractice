package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.util.HashMap;
import org.json.JSONObject;
import org.testng.annotations.Test;
import WEB.evolucionDEI.om.TRIRIGA_ARCGIS;
import io.restassured.response.Response;
import modelo.BaseExecution;
//import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Valida que el cliente reciba un código de respuesta 400 cuando este no envía el header Language necesario en la petición 
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-022 Validar codigo 400 de petición GET cuando no se envia el header Language
 * 
 * PREREQUISITOS: URL, credenciales válidas y Token válido
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 25/09/2025
 */

public class ATC_FT_022_ValidarErrorEnPeticionGETCuandoNoSeEnviaElHeaderLanguage extends BaseExecution {

	//@AzureTestInfo(
//	areaPath = "I24073-Evolución DEI",
//	iterationPath = "I24073-Evolución DEI",
//	tags = "ArcGis hacia Tririga;Error",
//	userStoryId = 226345   //)

	@Test(dataProvider = "data-provider")
	public void ATC_FT_022_ValidarErrorEnPeticionGETCuandoNoSeEnviaElHeaderLanguage_test(HashMap<String, String> data) throws Exception {

		/** Ejecutar petición Get y validar error correspondiente
		 * @param RestAssured
		 * @author Melba Solís
		 */

		/***************************** PASO 1: Obtener el token ****************************/
		addStep("Ejecutar petición POST para obtener el token");

		int postExpectedCode = Integer.parseInt(data.get("postExpectedCode"));

		Response getTokenResponse = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth")); 		// Se llama al OM

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
		JSONObject getJson = new JSONObject(getTokenBodyResponse);
		String token = getJson.optString("access_token", null);
		assertNotNull(token, "No se encontró access_token en la respuesta");
		testCase.addTextEvidenceCurrentStep("Token obtenido: " + token);
		System.out.println("Token extraído: " + token);
		
		/***************************** PASO 2: Obtener datos en petición GET ****************************/
		addStep("Ejecutar petición GET retrieve para validar que se recibe el error esperado cuando no se envia el header Language");
		System.out.println("=== Ejecutando petición GET ===");

		// Declarar código esperado para GET
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));

		// Ejecutar GET con reintentos usando el OM
		Response getResponse = TRIRIGA_ARCGIS.ejecutarGetConReintentos(token, getJson, data, 2); //puedes ajustar el número de reintentos

		// Validación de status
		int status = getResponse.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + status);
		System.out.println("Status: " + status);
		assertEquals(status, getExpectedCode, "El GET no devolvió el código esperado");

		// Guardar y mostrar body completo
		String getBodyResponse = getResponse.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("bodyResponse: " + getBodyResponse);
		System.out.println("Respuesta: " + getBodyResponse);

		// Parsear JSON de error y validar campos básicos
		String safeResponse = getBodyResponse.replace("\"details\":", "\"details\":\"\"");
		JSONObject bodyJson = new JSONObject(safeResponse);
		JSONObject errorJson = bodyJson.optJSONObject("error");
		assertNotNull(errorJson, "La respuesta no contiene objeto 'error'");

		String errorCode = errorJson.optString("code");
		String errorMsg  = errorJson.optString("message");
		testCase.addTextEvidenceCurrentStep("Error recibido: " + errorCode + " - " + errorMsg);

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
		return "ATC_FT_022_ValidarErrorEnPeticionGETCuandoNoSeEnviaElHeaderLanguage_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Valida que el cliente reciba un código de respuesta 400 cuando este no envía el header Language necesario en la petición";
	}
}
