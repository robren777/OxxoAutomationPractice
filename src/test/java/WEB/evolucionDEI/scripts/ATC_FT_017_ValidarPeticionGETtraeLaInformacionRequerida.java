package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import WEB.evolucionDEI.om.TRIRIGA_ARCGIS;
import io.restassured.response.Response;
import modelo.BaseExecution;
//import azure.annotation.AzureTestInfo;

/**DESCRIPCIÓN: Se valida que el cliente reciba un código de respuesta exitoso
 * 200 y el json de los puntos potenciales con información cuando el escenario
 * cumpla con la informacion requerida
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-017 Validar petición GET
 * trae la información requerida
 * 
 * PREREQUISITOS: URL, credenciales válidas y Token válido
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 24/09/2025    */

public class ATC_FT_017_ValidarPeticionGETtraeLaInformacionRequerida extends BaseExecution {

	//@AzureTestInfo(
//	areaPath = "I24073-Evolución DEI",
//	iterationPath = "I24073-Evolución DEI",
//	tags = "ArcGis hacia Tririga",
//	userStoryId = 226345   //)


	@Test(dataProvider = "data-provider")
	public void ATC_FT_017_ValidarPeticionGETtraeLaInformacionRequerida_test(HashMap<String, String> data)
			throws Exception {

		/** Ejecutar petición Get y traiga la información 
		 * @param RestAssured
		 * @author Melba Solís 	 */
		
		/***************************** PASO 1: Obtener el token ****************************/
		addStep("Ejecutar petición POST para obtener el token");

		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));

		Response getTokenResponse = TRIRIGA_ARCGIS.obtenerToken(data.get("postPath"), data.get("basicAuth"));		// Se llama al OM

		// Validar status
		int getTokenStatusCode = getTokenResponse.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + getTokenStatusCode);
		System.out.println("Status token: " + getTokenStatusCode);
		assertEquals(getTokenStatusCode, getExpectedCode, "Error al obtener token");

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
		addStep("Ejecutar petición GET retrieve para validar que se reciba un código de respuesta exitoso 200 cuando cumple con la información requerida en la petición");

		int maxReintentos = 3;
		// Ejecutamos el GET usando el OM
		Response getResponse = TRIRIGA_ARCGIS.ejecutarGetConReintentos(token, getJson, data, maxReintentos);

		// Evidencias y validaciones, Valida traiga el estatus 200 
		int status = getResponse.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode: " + status);
		System.out.println("Status: " + status);
		assertEquals(status, Integer.parseInt(data.get("getExpectedCode")), 
				"El GET no devolvió el código esperado después de " + maxReintentos + " intentos");

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

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con la herramienta postman y la URL \r\n"
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
		return "ATC_FT_017_ValidarPeticionGETtraeLaInformacionRequerida_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Se valida que el cliente reciba un código de respuesta exitoso 200 y el json de los puntos potenciales con información cuando el escenario cumpla con la informacion requerida";
	}
}
