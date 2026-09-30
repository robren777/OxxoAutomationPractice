package WEB.evolucionDEI.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.util.HashMap;
import org.json.JSONObject;
import org.testng.annotations.Test;
import WEB.evolucionDEI.om.TRIRIGA_ARCGIS;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import modelo.BaseExecution;
//import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Valida que el cliente reciba un código de respuesta  400 cuando no se envia el QueryParam geometryType 
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-021 Validar codigo 400 de petición GET cuando no se envia el QueryParam geometryType
 * 
 * PREREQUISITOS: URL, credenciales válidas y Token válido
 * 
 * INSUMOS: Colección y sus credenciales
 * 
 * @author Melba Solis
 * @date 25/09/2025
 */

public class ATC_FT_021_ValidarErrorEnPeticionGETCuandoNoSeEnviaElQueryParamGeometryType extends BaseExecution {

	//@AzureTestInfo(
//	areaPath = "I24073-Evolución DEI",
//	iterationPath = "I24073-Evolución DEI",
//	tags = "ArcGis hacia Tririga;Error",
//	userStoryId = 226345   //)

	@Test(dataProvider = "data-provider")
	public void ATC_FT_021_ValidarErrorEnPeticionGETCuandoNoSeEnviaElQueryParamGeometryType_test(HashMap<String, String> data) throws Exception {

		/** Ejecutar petición Get y validar error correspondiente
		 * @param RestAssured
		 * @author Melba Solís
		 */

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
		
		/***************************** PASO 2: Obtener datos en petición GET ****************************/
		addStep("Ejecutar petición GET retrieve para validar que se recibe el error esperado cuando no se envia el Query Param GeometryType");
		System.out.println("=== Ejecutando petición GET ===");

		// Parametrización de URL
		String baseGetUrl = data.get("getPath");

		// Declarar código esperado para GET
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));

		// Construcción de la petición
		String tokenType = getJson.getString("token_type"); // normalmente "Bearer"
		String authHeader = tokenType + " " + token;

		Response getResponse = RestAssured
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
			    .queryParam("where", "OBJECTID = " + data.get("objectId"))
			    .queryParam("spatialRel", "esriSpatialRelIntersects")
			    .queryParam("units", "esriSRUnit_Foot")
			    //.queryParam("geometryType", "esriGeometryEnvelope") Parametro geometryType no se envia
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
		return "ATC_FT_021_ValidarErrorEnPeticionGETCuandoNoSeEnviaElQueryParamGeometryType_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Valida que el cliente reciba un código de respuesta  400 cuando no se envia el QueryParam geometryType";
	}
}
