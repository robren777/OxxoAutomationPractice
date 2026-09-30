package WEB.repositorioCentral;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

public class ATC_FT_051_VerificarAPIRequestConURLIncorrecta_RepositorioCentral extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_051_VerificarAPIRequestConURLIncorrecta_RepositorioCentral_test(HashMap<String, String> data)
			throws Exception {
		/**
		 * Repositorio Central:
		 * ATC_FT_051_VerificarAPIRequestConURLIncorrecta_RepositorioCentral Verificar
		 * API request con URL incorrecta Desc: Se ejecuta el request con un endpoint
		 * incorrecto Luis G. Ramirez Martinez 27/03/2024
		 */

		String baseURLToken = "https://ipaas-dev.oxxo.io";
		ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);

		// Obtener datos del data provider
		String getPathToken = data.get("getPathToken");
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
		String getAuthType = data.get("getAuthType");

		// Ejecuta request
		RestAssured.baseURI = baseURLToken;

		// Lista de headers
		HashMap<String, String> headersListToken = new HashMap<>();
		headersListToken.put("Accept", "*/*");
		headersListToken.put("Authorization",
				"Basic YWUwMDMwMTAwZGNhNDE4Zjk5YWI2ZGFmZjM1MzJlMDM6NDNiMjFjMzJiMzYyNGFkMGE0NWY4MzQ4Mzg0NzY1NDM=");

		String reqBodyToken = "";

		// Hacemos el send del request
		Response getRespuesta = api.postRequestWithHeaders(baseURLToken + getPathToken, reqBodyToken, headersListToken);

		// Obtener body de respuesta
		String getBodyResponse = getRespuesta.getBody().asPrettyString();
		System.out.println("getBodyResponse: " + getBodyResponse);

		// Validaci�n de JSON
		JsonPath getJson = new JsonPath(getBodyResponse);
		String access_token = getJson.get("access_token");
		System.out.println("getJson: " + getJson);

		// Obtener el status code
		int getStatusCode = getRespuesta.getStatusCode();

		System.out.println("statusCode: " + getStatusCode);
		System.out.println("body response: " + getBodyResponse);
		System.out.println("Access Token: " + access_token);

		// "----------------------------------------------------------------------PASO
		// 1--------------------------------------------------------------------");
		addStep("Enviar request en formato JSON con m�todo POST con todos los campos requeridos usando un endpoint incorrecto");

		// Definici�n de Variables
		String baseURIApi = "https://ipaas-dev.oxxo.io";
		String getPathApi = data.get("getPathApi"); // endpoint incorrecto
		LocalDateTime datetime = LocalDateTime.now();
		String fecha = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(datetime);

		// Se env�a como evidencia el request a ejecutar
		testCase.addTextEvidenceCurrentStep("Request: \n" + baseURIApi + getPathApi);

		// Llamada al API usando el token generado
		RestAssured.baseURI = baseURIApi;

		// Lista de headers
		HashMap<String, String> headersListApi = new HashMap<>();
		headersListApi.put("Accept", "*/*");
		headersListApi.put("Tracking-Id", "4bd9e9c2-4989-4f21-8398-ad9d19ef0f66");
		headersListApi.put("Channel-Id", "WEB");
		headersListApi.put("Country-Code", "MX");
		headersListApi.put("Language", "SPA");
		headersListApi.put("Content-Type", "application/json");
		headersListApi.put("x-Gateway-APIKey", "ce0e4234-6e00-4d9f-b668-1b5b67960bec");
		headersListApi.put("Authorization", "Bearer " + access_token);

		String reqBody = data.get("reqBody");

		// Hacemos el send del request
		Response getRespuestaApi = api.postRequestWithHeaders(baseURIApi + getPathApi, reqBody, headersListApi);

		// Obtener body de respuesta
		String getBodyResponseApi = getRespuestaApi.getBody().asPrettyString();
		System.out.println("getBodyResponse: " + getBodyResponseApi);

		// "----------------------------------------------------------------------PASO
		// 2--------------------------------------------------------------------");
		addStep("Verificar c�digo de respuesta 404 Not Found");

		// Obtener el status code
		int getStatusCodeApi = getRespuestaApi.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode:" + getStatusCodeApi);
		AssertJUnit.assertEquals(getStatusCodeApi, getExpectedCode); // Validacion

		// "----------------------------------------------------------------------PASO
		// 3--------------------------------------------------------------------");
		addStep("Verificar body de respuesta");

		// Validaci�n de JSON
		JsonPath getJsonApi = new JsonPath(getBodyResponseApi);
		testCase.addTextEvidenceCurrentStep(getBodyResponseApi);

		System.out.println("statusCode: " + getStatusCodeApi);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "\"Postman est� abierto\r\n" + "Se han agregado headers al request\r\n" + "{\r\n"
				+ "Authorization	Bearer {{accessTokenUX}}\r\n" + "}\r\n"
				+ "Campo \"\"businessName\"\" sea valido\r\n"
				+ "Campo \"\"folderName\"\" no se repita con un folder previamente creado\r\n"
				+ "Access token v�lido disponible\r\n" + "Archivo de prueba v�lido disponible\r\n"
				+ "Se env�a peticion a Endpoint inesistente\"";
	}

	@Override
	public String setTestDescription() {
		return " Se ejecuta el request con un endpoint incorrecto";
	}

	@Override
	public String setTestDesigner() {
		return " Luis G. Ramirez Martinez";
	}

	@Override
	public String setTestFullName() {
		return " ATC_FT_051_VerificarAPIRequestConURLIncorrecta_RepositorioCentral";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
