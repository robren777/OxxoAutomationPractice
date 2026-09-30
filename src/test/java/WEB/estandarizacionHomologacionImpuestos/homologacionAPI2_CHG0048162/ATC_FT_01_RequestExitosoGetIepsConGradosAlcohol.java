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
* DESCRIPCIÓN: Validar que al enviar un request válido con parámetros de grados
* de alcohol el endpoint getIeps responda con los datos de alcoholDegrees.
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
* @author Isaac Emmanuel García García
* @date 19/Agosto/2024
*/

public class ATC_FT_01_RequestExitosoGetIepsConGradosAlcohol extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_01_RequestExitosoGetIepsConGradosAlcohol_test(HashMap<String, String> data) throws Exception {

		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
				GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

		String uri = String.format(data.get("uri"));
		String value = data.get("value");
		String catalog = data.get("catalog");

		double alcoholDegrees;
		double calories;
		int packagingType;
		int toxicity;
		String shortDescription;
		String description;
		double from;
		double until;
		double ieps;
		String stateTax;
		String active;

		RestAssured.baseURI = uri;
		ApiMethodsUtil api = new ApiMethodsUtil(uri);

		// Paso 1
		addStep("Realizar request GET al endpoint getIeps");
		testCase.addCodeEvidenceCurrentStep("Request: " + uri + value + "/" + catalog);
		System.out.println("Request: " + uri + value + "/" + catalog);
		Response jsonRequest = api.getRequestMethod(uri + value + "/" + catalog);

		String jsonResponse = String.valueOf(jsonRequest.asPrettyString());
		String statusCode = String.valueOf(jsonRequest.getStatusCode());

		testCase.addCodeEvidenceCurrentStep("Respuesta JSON: \n" + jsonResponse);
		System.out.println("JsonResponse: \n" + jsonResponse);
		testCase.addCodeEvidenceCurrentStep("Codigo de respuesta: " + statusCode);
		System.out.println("StatusCode: " + statusCode);

		assertEquals(statusCode, "200", "El código de respuesta no es 200");

		try {

			// Validacion de formato de respuesta
			JSONObject jsonObject = new JSONObject(jsonResponse);
			assertTrue(jsonObject.has("code"), "El formato de respuesta no contiene el campo: code");
			assertTrue(jsonObject.has("message"), "El formato de respuesta no contiene el campo: message");
			assertTrue(jsonObject.has("alcoholDegrees"),
					"El formato de respuesta no contiene el campo: alcoholDegrees");
			assertTrue(jsonObject.has("calories"), "El formato de respuesta no contiene el campo: calories");
			assertTrue(jsonObject.has("packagingType"), "El formato de respuesta no contiene el campo: packagingType");
			assertTrue(jsonObject.has("toxicity"), "El formato de respuesta no contiene el campo: toxicity");
			assertTrue(jsonObject.has("shortDescription"),
					"El formato de respuesta no contiene el campo: shortDescription");
			assertTrue(jsonObject.has("description"), "El formato de respuesta no contiene el campo: description");
			assertTrue(jsonObject.has("from"), "El formato de respuesta no contiene el campo: from");
			assertTrue(jsonObject.has("until"), "El formato de respuesta no contiene el campo: until");
			assertTrue(jsonObject.has("ieps"), "El formato de respuesta no contiene el campo: ieps");
			assertTrue(jsonObject.has("stateTax"), "El formato de respuesta no contiene el campo: stateTax");
			assertTrue(jsonObject.has("active"), "El formato de respuesta no contiene el campo: active");

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);

			alcoholDegrees = jsonNode.get("alcoholDegrees").asDouble();
			calories = jsonNode.get("calories").asDouble();
			packagingType = jsonNode.get("packagingType").asInt();
			toxicity = jsonNode.get("toxicity").asInt();
			shortDescription = jsonNode.get("shortDescription").asText();
			description = jsonNode.get("description").asText();
			from = jsonNode.get("from").asDouble();
			until = jsonNode.get("until").asDouble();
			ieps = jsonNode.get("ieps").asDouble();
			stateTax = jsonNode.get("stateTax").asText();
			active = jsonNode.get("active").asText();

			assertEquals(alcoholDegrees, Double.parseDouble(value));
			assertEquals(calories, 0);
			assertEquals(packagingType, 0);
			assertEquals(toxicity, 0);

		} catch (Exception e) {
			throw new AssertionError("Error al obtener los datos de la respuesta");
		}

		// Paso 2

		addStep("Validar que los datos obtenidos coincidan con la BD");

		String selectIeps = "select GRADOS_ALCOHOL, DESCRIPCION, RANGO_DESDE, RANGO_HASTA, IEPS, IMP_EST, ACTIVE from XXSAT.XXSAT_CAT_GRADOS_ALCOHOL where "
				+ value + " between RANGO_DESDE and RANGO_HASTA";

		try {
			utils.sql.SQLResult sqlResult = dbFCPFACQA.executeQuery(selectIeps);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			String gradosAlcoholBD = sqlResult.getData(0, "grados_alcohol");
			String descripcionBD = sqlResult.getData(0, "descripcion");
			String rangoDesdeBD = sqlResult.getData(0, "rango_desde");
			String rangoHastaBD = sqlResult.getData(0, "rango_hasta");
			String iepsBD = sqlResult.getData(0, "ieps");
			String impEstBD = sqlResult.getData(0, "imp_est");
			if (impEstBD == null) {
				impEstBD = "null";
			}
			String activeBD = sqlResult.getData(0, "active");

			assertEquals(gradosAlcoholBD, shortDescription, "No coincide el shortDescription con la BD");
			assertEquals(descripcionBD, description, "No coincide el description con la BD");
			assertEquals(Double.parseDouble(rangoDesdeBD), from, "No coincide el from con la BD");
			assertEquals(Double.parseDouble(rangoHastaBD), until, "No coincide el until con la BD");
			assertEquals(Double.parseDouble(iepsBD), ieps, "No coincide el ieps con la BD");
			assertEquals(impEstBD, stateTax, "No coincide el stateTax con la BD");
			assertEquals(activeBD, active, "No coincide el active con la BD");
		} catch (Exception e) {
			throw new AssertionError("Error al consultar la BD FCPFACQA \n" + e);
		}

		dbFCPFACQA.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_01_RequestExitosoGetIepsConGradosAlcohol_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al enviar un request válido con parámetros de grados de alcohol el endpoint getIeps responda con los datos de alcoholDegrees.";
	}

	@Override
	public String setTestDesigner() {
		return "Isaac Emmanuel García García";
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
		// Implement any setup needed before test execution
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}
}
