package WEB.estandarizacionHomologacionImpuestos.homologacionAPI2_CHG0048162;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
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
* DESCRIPCIÓN: Validar que al enviar un request válido con parámetros de tipos de envase 
* el endpoint getIeps responda con el catálogo de tipos de envase en packagingType.
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
* @author Angel Del Rio Portales
* @date 02/Octubre/2024
*/

public class ATC_FT_10_RequestExitosoGetIepsConCatalogoTiposEnvase extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_10_RequestExitosoGetIepsConCatalogoTiposEnvase_test(HashMap<String, String> data) throws Exception {

		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
				GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

		String uri = String.format(data.get("uri"));
		String value = data.get("value");
		String catalog = data.get("catalog");

		String alcoholDegrees;
		String calories;
		String packagingType;
		String toxicity;
		String shortDescription;
		String description;
		String from;
		String until;
		String ieps;
		String stateTax;
		String active;
		String tipoEnvaseBD;
		String descripcionBD;

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
			assertTrue(jsonObject.has("alcoholDegrees"), "El formato de respuesta no contiene el campo: alcoholDegrees");
			assertTrue(jsonObject.has("calories"), "El formato de respuesta no contiene el campo: calories");
			assertTrue(jsonObject.has("packagingType"), "El formato de respuesta no contiene el campo: packagingType");
			assertTrue(jsonObject.has("toxicity"), "El formato de respuesta no contiene el campo: toxicity");
			assertTrue(jsonObject.has("shortDescription"), "El formato de respuesta no contiene el campo: shortDescription");
			assertTrue(jsonObject.has("description"), "El formato de respuesta no contiene el campo: description");
			assertTrue(jsonObject.has("from"), "El formato de respuesta no contiene el campo: from");
			assertTrue(jsonObject.has("until"), "El formato de respuesta no contiene el campo: until");
			assertTrue(jsonObject.has("ieps"), "El formato de respuesta no contiene el campo: ieps");
			assertTrue(jsonObject.has("stateTax"), "El formato de respuesta no contiene el campo: stateTax");
			assertTrue(jsonObject.has("active"), "El formato de respuesta no contiene el campo: active");

			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(jsonResponse);

			alcoholDegrees = jsonNode.get("alcoholDegrees").asText();
			calories = jsonNode.get("calories").asText();
			packagingType = jsonNode.get("packagingType").asText();
			toxicity = jsonNode.get("toxicity").asText();
			shortDescription = jsonNode.get("shortDescription").asText();
			description = jsonNode.get("description").asText();
			from = jsonNode.get("from").asText();
			until = jsonNode.get("until").asText();
			ieps = jsonNode.get("ieps").asText();
			stateTax = jsonNode.get("stateTax").asText();
			active = jsonNode.get("active").asText();

			assertEquals(alcoholDegrees, "null");
			assertEquals(calories, "null");
			assertNotEquals(packagingType, "null");
			assertEquals(toxicity, "null");
			assertEquals(shortDescription, "null");
			assertEquals(description, "null");
			assertEquals(from, "null");
			assertEquals(until, "null");
			assertEquals(ieps, "null");
			assertEquals(stateTax, "null");
			assertEquals(active, "null");

		} catch (Exception e) {
			throw new AssertionError("Error al obtener los datos de la respuesta");
		}

		// Paso 2

		addStep("Validar que los datos obtenidos coincidan con la BD");
		
		String envasesArr[] = packagingType.split(";");
		String tipoEnvaseArr[];
		String selectPackageTypes = "select TIPO_ENVASE, DESCRIPCION, ACTIVE, IEPS from XXSAT.XXSAT_CAT_TIPO_ENVASE";

		try {
			utils.sql.SQLResult sqlResult = dbFCPFACQA.executeQuery(selectPackageTypes);
			testCase.addQueryEvidenceCurrentStep(sqlResult);
			assertEquals(sqlResult.getRowCount(), envasesArr.length, "No coincide el numero de registros con la BD");

			for (int i = 0; i < sqlResult.getRowCount(); i++) {
				tipoEnvaseArr = envasesArr[i].split("\\|");
				tipoEnvaseBD = sqlResult.getData(i, "tipo_envase");
				descripcionBD = sqlResult.getData(i, "descripcion");

				assertEquals(tipoEnvaseBD, tipoEnvaseArr[0], "No coincide el campo TIPO_ENVASE con la BD");
				assertEquals(descripcionBD, tipoEnvaseArr[1], "No coincide el description con la BD");
			}

		} catch (Exception e) {
			throw new AssertionError("Error al consultar la BD FCPFACQA \n" + e);
		}

		dbFCPFACQA.close();

	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_10_RequestExitosoGetIepsConCatalogoTiposEnvase_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al enviar un request válido con parámetros de tipos de envase el endpoint getIeps responda con el catálogo de tipos de envase en packagingType.";
	}

	@Override
	public String setTestDesigner() {
		return "Angel Del Río";
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
