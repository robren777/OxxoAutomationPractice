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

public class ATC_FT_007_VerificarAPIRequestCuandoSeIncluyeArchivoVacio_RepositorioCentral extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_007_VerificarAPIRequestCuandoSeIncluyeArchivoVacio_RepositorioCentral_test(
			HashMap<String, String> data) throws Exception {
		/**
		 * Repositorio Central:
		 * ATC_FT_007_VerificarAPIRequestCuandoSeIncluyeArchivoVacio_RepositorioCentral
		 * Verificar API request cuando se incluye archivo vac�o Desc: Se ejecuta el
		 * request con archivo vac�o Luis G. Ramirez Martinez 21/03/2024
		 */

		// String baseURLToken = "https://api-dev.oxxo.io";
		String baseURLToken = "https://api-stg.oxxo.io";
		ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);

		// Obtener datos del data provider
		String getPathToken = data.get("getPathToken");
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));

		// Ejecuta request
		RestAssured.baseURI = baseURLToken;

		// Lista de headers
		HashMap<String, String> headersListToken = new HashMap<>();
		headersListToken.put("Accept", "*/*");
		// headersListToken.put("Authorization", "Basic
		// MjY2MGZhMjItMWUwYy00MjUyLTlkMjktZTFhYmM3YTA5NmMwOjI0NTk2ZTAxLWIxODQtNDk2NS05YmM3LTM4MGFjYWJjMWExNg==");
		headersListToken.put("Authorization",
				"Basic OGFmYzBkM2YtOGEwYi00ZGYxLTkxNzktOWU2YmNiOTBkNmFlOmI4MWEzOGJhLTMyN2ItNDU0ZS1hMzg3LTcxYWZjYjQyMTk0Ng==");

		String reqBodyToken = "";

		// Hacemos el send del request
		Response getRespuesta = api.postRequestWithHeaders(baseURLToken + getPathToken, reqBodyToken, headersListToken);

		// Obtener body de respuesta
		String getBodyResponse = getRespuesta.getBody().asPrettyString();

		// Obtener el status code
		int getStatusCode = getRespuesta.getStatusCode();

		// Validaci�n de JSON
		JsonPath getJson = new JsonPath(getBodyResponse);
		String access_token = getJson.get("access_token");

		System.out.println("\nStatusCode: " + getStatusCode);
		System.out.println("Body response: " + getBodyResponse);

		// "----------------------------------------------------------------------PASO
		// 1--------------------------------------------------------------------");
		addStep("Enviar request en formato JSON con m�todo POST con campos requeridos pero se proporciona archivo vac�o ");

		// Definici�n de Variables
		String baseURIApi = "https://api-stg.oxxo.io";
		String getPathApi = data.get("getPathApi");
		LocalDateTime datetime = LocalDateTime.now();
		String fecha = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(datetime);

		// Se env�a como evidencia el request a ejecutar
		testCase.addTextEvidenceCurrentStep("Request: " + baseURIApi + getPathApi);

		// Llamada al API usando el token generado
		RestAssured.baseURI = baseURIApi;

		// Lista de headers
		HashMap<String, String> headersListApi = new HashMap<>();
		headersListApi.put("Accept", "*/*");
		headersListApi.put("Tracking-Id", "710f0083-3218-4ed5-8b72-189f70b23ee4"); // "4bd9e9c2-4989-4f21-8398-ad9d19ef0f66");
		headersListApi.put("Channel-Id", "WEB");
		headersListApi.put("Country-Code", "MX");
		headersListApi.put("Language", "SPA");
		headersListApi.put("Content-Type", "application/json");
		headersListApi.put("x-Gateway-APIKey", "698afef8-0194-4941-9457-8cc4d463456a"); // ce0e4234-6e00-4d9f-b668-1b5b67960bec");
		headersListApi.put("Authorization", "Bearer " + access_token);

		String reqBody = data.get("reqBody");

		// Hacemos el send del request
		Response getRespuestaApi = api.postRequestWithHeaders(baseURIApi + getPathApi, reqBody, headersListApi);

		// Obtener body de respuesta
		String getBodyResponseApi = getRespuestaApi.getBody().asPrettyString();
		System.out.println("getBodyResponse: " + getBodyResponseApi);

		// "----------------------------------------------------------------------PASO
		// 2--------------------------------------------------------------------");
		addStep("Verificar c�digo de respuesta 200 OK");

		// Obtener el status code
		int getStatusCodeApi = getRespuestaApi.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode:" + getStatusCodeApi);
		AssertJUnit.assertEquals(getStatusCodeApi, getExpectedCode); // Validacion

		// "----------------------------------------------------------------------PASO
		// 3--------------------------------------------------------------------");
		addStep("Verificar body de respuesta.");

		// Validaci�n de JSON
		JsonPath getJsonApi = new JsonPath(getBodyResponseApi);
		testCase.addTextEvidenceCurrentStep(getBodyResponseApi);

		System.out.println("statusCode: " + getStatusCodeApi);
		System.out.println("Access Token: " + access_token);

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "\"Postman est� abierto\r\n" + "Se han agregado headers al request:\r\n"
				+ "{Tracking-Id 		4bd9e9c2-4989-4f21-8398-ad9d19ef0f66\r\n" + "Channel-Id		WEB\r\n"
				+ "Country-Code		MX\r\n" + "Language		SPA\r\n" + "Content-Type		application/json\r\n"
				+ "User-Agent		postman/1.0.0\r\n" + "Authorization		Bearer {{accessTokenUX}}\r\n"
				+ "x-Gateway-APIKey 	{{apiKey}}}\r\n" + "Campo \"\"businessName\"\" sea valido\r\n"
				+ "Campo \"\"folderName\"\" no se repita con un folder previamente creado\r\n"
				+ "Access token v�lido disponible\r\n" + "Archivo de prueba vac�o\"";
	}

	@Override
	public String setTestDescription() {
		return " Se ejecuta el request con archivo vac�o";
	}

	@Override
	public String setTestDesigner() {
		return " Luis G. Ramirez Martinez";
	}

	@Override
	public String setTestFullName() {
		return " ATC_FT_007_VerificarAPIRequestCuandoSeIncluyeArchivoVacio_RepositorioCentral";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
