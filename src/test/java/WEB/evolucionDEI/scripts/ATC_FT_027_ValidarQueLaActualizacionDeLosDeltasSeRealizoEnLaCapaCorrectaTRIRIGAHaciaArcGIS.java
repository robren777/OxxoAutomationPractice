package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;
import java.util.HashMap;
import org.json.JSONObject;
import org.testng.annotations.Test;
import WEB.evolucionDEI.om.TRIRIGA_ARCGIS;
import io.restassured.response.Response;
import modelo.BaseExecution;
//import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Verificar que, al enviarse registros (deltas), únicamente se actualice en la capa correspondiente a 'Puntos Potenciales' en ArcGIS por lo que se valida en las configuraciones de Postman que la capa configurada es la correcta
 * 
 * *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-027 Validar que la actualización de los deltas se realizo en la capa Puntos Potenciales
 * 
 * PREREQUISITOS: URL, credenciales válidas y Token válido
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 26/09/2025
 */

public class ATC_FT_027_ValidarQueLaActualizacionDeLosDeltasSeRealizoEnLaCapaCorrectaTRIRIGAHaciaArcGIS extends BaseExecution {

	//@AzureTestInfo(
//	areaPath = "I24073-Evolución DEI",
//	iterationPath = "I24073-Evolución DEI",
//	tags = "Tririga hacia ArcGis",
//	userStoryId = 226345   //)

	@Test(dataProvider = "data-provider")
	public void ATC_FT_027_ValidarQueLaActualizacionDeLosDeltasSeRealizoEnLaCapaCorrectaTRIRIGAHaciaArcGIS_test(HashMap<String, String> data) throws Exception {

		/** Ejecutar petición Put exitosamente y se valida la capa donde se actualiza delta
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
		JSONObject getJson = new JSONObject(getTokenBodyResponse);
		String token = getJson.optString("access_token", null);
		assertNotNull(token, "No se encontró access_token en la respuesta");
		testCase.addTextEvidenceCurrentStep("Token obtenido: " + token);
		System.out.println("Token extraído: " + token);

		/**************************** PASO 2: Ejecutar syncPotentialStores ****************************/
		addStep("Ejecutar petición PUT syncPotentialStores y validar la Capa donde se actualiza el Delta");

		String putURL = data.get("putPath");
		System.out.println("URL FINAL PUT: " + putURL);

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
		testCase.addCodeEvidenceCurrentStep("statusCode:" + putStatusCode);

		// Si es 503, lo documentamos y marcamos como fallo controlado
		if (putStatusCode == 503) {
		    testCase.addTextEvidenceCurrentStep("El servicio no está disponible (503). Revisa la conexión con ArcGIS/Tririga.");
		    fail("El servicio no está disponible, no se pudo ejecutar la prueba.");
		}

		// Si no fue 503, validamos el esperado
		assertEquals(putStatusCode, putExpectedCode, "El PUT no devolvió el código esperado");
		
		addStep("Se valida la Capa donde se actualiza el Delta es 5");	
	
		// Extraer número de capa desde la URL
		String[] parts = putURL.split("/");
		String capa = parts[parts.length - 1]; // Último elemento de la URL

		//testCase.addTextEvidenceCurrentStep("Ejecutando PUT hacia: " + putURL);
		testCase.addTextEvidenceCurrentStep("La capa de actualización es: " + capa);
		assertTrue(putURL.contains("/sync/" + capa), "No se está usando la capa /sync/" + capa);

		}
		
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
		return "ATC_FT_027_ValidarQueLaActualizacionDeLosDeltasSeRealizoEnLaCapaCorrectaTRIRIGAHaciaArcGIS_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Verificar que, al enviarse registros (deltas), únicamente se actualice en la capa correspondiente a 'Puntos Potenciales' en ArcGIS por lo que se valida en las configuraciones de Postman que la capa configurada es la correcta";
	}
}
