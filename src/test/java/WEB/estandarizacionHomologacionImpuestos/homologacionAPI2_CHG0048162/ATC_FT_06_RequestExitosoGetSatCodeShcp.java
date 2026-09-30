package WEB.estandarizacionHomologacionImpuestos.homologacionAPI2_CHG0048162;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.util.GlobalVariables;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

/**
* DESCRIPCIÓN: Validar que al enviar un request válido con parámetro de código
* SAT el endpoint getSatCodeShcp responda con los datos de su descripción.
* 
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* - No Aplica
* 
* PREREQUISITOS:
* - API creada
* - Acceso a la base de datos FCPFACQA
* 
* INSUMOS:
* - No Aplica
* 
* @author Jorge Hernandez Sanchez
* @date 19/Agosto/2024
*/

public class ATC_FT_06_RequestExitosoGetSatCodeShcp extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_06_RequestExitosoGetSatCodeShcp_test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
				GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

		String uri = String.format(data.get("uri"));
		String sc = data.get("sc");

		String satCodeShcp;
		String description;

		RestAssured.baseURI = uri;
		ApiMethodsUtil api = new ApiMethodsUtil(uri);

		// Paso 1
		addStep("Realizar request GET al endpoint getSatCodeShcps");
		testCase.addCodeEvidenceCurrentStep("Request: " + uri + sc);
		System.out.println("Request: " + uri + sc);
		Response jsonRequest = api.getRequestMethod(uri + sc);

		String jsonResponse = String.valueOf(jsonRequest.asPrettyString());
		String statusCode = String.valueOf(jsonRequest.getStatusCode());

		testCase.addCodeEvidenceCurrentStep("Respuesta JSON: \n" + jsonResponse);
		System.out.println("JsonResponse: \n" + jsonResponse);
		testCase.addCodeEvidenceCurrentStep("Codigo de respuesta: " + statusCode);
		System.out.println("StatusCode: " + statusCode);

		assertEquals(statusCode, "200", "El código de respuesta no es 200");

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(jsonResponse);

		try {

			// Validacion de formato de respuesta
			JSONObject jsonObject = new JSONObject(jsonResponse);
			assertTrue(jsonObject.has("code"), "El formato de respuesta no contiene el campo: code");
			assertTrue(jsonObject.has("message"), "El formato de respuesta no contiene el campo: message");
			assertTrue(jsonObject.has("satCodeShcp"), "El formato de respuesta no contiene el campo: satCodeShcp");
			assertTrue(jsonObject.has("description"), "El formato de respuesta no contiene el campo: description");

			satCodeShcp = jsonNode.get("satCodeShcp").asText();
			assertEquals(satCodeShcp, sc, "No coincide el satCodeShcp con la API");
			description = jsonNode.get("description").asText();
		} catch (Exception e) {
			throw new AssertionError("Error al obtener los datos de la respuesta");
		}

		// Paso 2
		addStep("Validar que lo datos obtenidos coincidan con la BD");

		String selectSatCodeShcp = "SELECT ID_CAT_CLAVE_SAT, CLAVE_SAT, DESCRIPCION, CREATE_DATETIME from XXSAT.XXSAT_CAT_CLAVE_SAT where clave_sat = " + sc;

		try {
			utils.sql.SQLResult sqlResult = dbFCPFACQA.executeQuery(selectSatCodeShcp);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			String satCodeShcpBD = sqlResult.getData(0, "clave_sat");
			String descripcionBD = sqlResult.getData(0, "descripcion");
			assertEquals(satCodeShcpBD, satCodeShcp, "No coincide el SatCodeShcp con la BD");
			assertEquals(descripcionBD, description, "No coincide el description con la BD");
		} catch (Exception e) {
			throw new AssertionError("Error al consultar la BD FCPFACQA");
		}

		dbFCPFACQA.close();
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_06_RequestExitosoGetSatCodeShcp_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al enviar un request válido con parámetro de código SAT el endpoint getSatCodeShcp responda con los datos de su descripción.";
	}

	@Override
	public String setTestDesigner() {
		return "Angel Del Rio";
	}

	@Override
	public String setTestInstanceID() {
		return "";
	}

	@Override
	public String setPrerequisites() {
		return "1. API creada 2. Acceso a la base de datos FCPFACQA.";
	}

	@Override
	public void beforeTest() {

	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}
}