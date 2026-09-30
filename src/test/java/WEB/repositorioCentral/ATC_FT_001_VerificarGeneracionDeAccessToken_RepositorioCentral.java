package WEB.repositorioCentral;

import java.util.HashMap;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

public class ATC_FT_001_VerificarGeneracionDeAccessToken_RepositorioCentral extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_VerificarGeneracionDeAccessToken_RepositorioCentral_test(HashMap<String, String> data)
			throws Exception {
		/**
		 * Repositorio Central:
		 * ATC_FT_001_VerificarGeneracionDeAccessToken_RepositorioCentral Verificar
		 * generacion de Access Token Desc: Se ejecuta el request para generar Access
		 * Token para uso en xAPI Luis G. Ramirez Martinez 21/03/2024
		 */
		// "----------------------------------------------------------------------PASO
		// 1--------------------------------------------------------------------");
		addStep("Enviar request con método POST con todos los campos requeridos");

		String baseURLToken = "https://api-stg.oxxo.io";
		ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);

		// Obtener datos del data provider
		String getPathToken = data.get("getPathToken");
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
		testCase.addTextEvidenceCurrentStep("Url a ejecutar: " + baseURLToken + getPathToken);

		// Ejecuta request
		RestAssured.baseURI = baseURLToken;

		// Lista de headers
		HashMap<String, String> headersListToken = new HashMap<>();
		headersListToken.put("Accept", "*/*");
		headersListToken.put("Authorization",
				"Basic OGFmYzBkM2YtOGEwYi00ZGYxLTkxNzktOWU2YmNiOTBkNmFlOmI4MWEzOGJhLTMyN2ItNDU0ZS1hMzg3LTcxYWZjYjQyMTk0Ng==");

		String reqBodyToken = "";

		// Hacemos el send del request
		Response getRespuesta = api.postRequestWithHeaders(baseURLToken + getPathToken, reqBodyToken, headersListToken);

		// Obtener body de respuesta
		String getBodyResponse = getRespuesta.getBody().asPrettyString();

		// "----------------------------------------------------------------------PASO
		// 2--------------------------------------------------------------------");
		addStep("Verificar código de respuesta 200 OK");

		// Obtener el status code
		int getStatusCode = getRespuesta.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("statusCode:" + getStatusCode);
		AssertJUnit.assertEquals(getStatusCode, getExpectedCode); // Validacion

		// "----------------------------------------------------------------------PASO
		// 3--------------------------------------------------------------------");
		addStep("Verificar body de respuesta:");

		// Validación de JSON
		JsonPath getJson = new JsonPath(getBodyResponse);
		String access_token = getJson.get("access_token");
		testCase.addTextEvidenceCurrentStep(getBodyResponse);

		System.out.println("\nStatusCode: " + getStatusCode);
		System.out.println("Body response: " + getBodyResponse);
		System.out.println("Access Token: " + access_token);
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "Se ejecuta el request para generar Access Token para uso en xAPI";
	}

	@Override
	public String setTestDescription() {
		return " Se ejecuta el request para generar Access Token para uso en xAPI";
	}

	@Override
	public String setTestDesigner() {
		return " Luis G. Ramirez Martinez";
	}

	@Override
	public String setTestFullName() {
		return " ATC_FT_001_VerificarGeneracionDeAccessToken_RepositorioCentral";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
