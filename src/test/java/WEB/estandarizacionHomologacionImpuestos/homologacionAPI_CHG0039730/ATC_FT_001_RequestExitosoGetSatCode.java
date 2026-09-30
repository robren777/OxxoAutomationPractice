package WEB.estandarizacionHomologacionImpuestos.homologacionAPI_CHG0039730;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelo.BaseExecution; import WEB.util.GlobalVariables;
import WEB.util.MetodoGeneral;
import utils.ApiMethodsUtil;

public class ATC_FT_001_RequestExitosoGetSatCode extends BaseExecution {

	/**
	 * DESCRIPCION: Validar que al enviar un request válido el API responda con el
	 * SAT code correctamente.
	 * 
	 * PREREQUISITOS: 1. API creada 2. Acceso a la base de datos FCPFACQA
	 * 
	 * @author Angel Del Río
	 */

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_RequestExitosoGetSatCode_test(HashMap<String, String> data) throws Exception {

		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
				GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

		String uri = String.format(data.get("uri"));
		String supergrupo = data.get("supergrupo");
		String categoria = data.get("categoria");
		String subcategoria = data.get("subcategoria");
		String segmento = data.get("segmento");
		String subsegmento = data.get("subsegmento");
		String codigoSAT = data.get("codigoSAT");

		String uriParams = String
				.format(supergrupo + "/" + categoria + "/" + subcategoria + "/" + segmento + "/" + subsegmento);

		RestAssured.baseURI = uri;
		ApiMethodsUtil api = new ApiMethodsUtil(uri);

		// ---------- PASO 1
		addStep("Realizar la peticion GET");
		testCase.addCodeEvidenceCurrentStep("Request: " + uri + uriParams);
		System.out.println("Request: " + uri + uriParams);
		Response jsonRequest = api.getRequestMethod(uri + uriParams);

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
			String codigoSATAPI = jsonNode.get("satCode").asText();
			testCase.addCodeEvidenceCurrentStep("satCode: " + codigoSATAPI);
			assertEquals(codigoSATAPI, codigoSAT, "No coincide el código SAT con la API");
			System.out.println("satCode: " + codigoSATAPI);
		} catch (Exception e) {
			throw new AssertionError("Error al obtener los datos de la respuesta");
		}

		// ---------- PASO 2
		addStep("Validar que el codigo SAT sea igual en la BD FCPFACQA");

		String selectGetSatCode = "select supergrupo, categoria, sub_categoria, segmento, sub_segmento, codigo_sat "
				+ "from XXSAT.XXSAT_CAT_PROD_SERV " + "WHERE SUPERGRUPO = " + supergrupo + " AND CATEGORIA = "
				+ categoria + " AND SUB_CATEGORIA = " + subcategoria + " AND SEGMENTO = " + segmento
				+ " AND SUB_SEGMENTO = " + subsegmento;
		try {
			utils.sql.SQLResult resultadoSQL = dbFCPFACQA.executeQuery(selectGetSatCode);
			testCase.addQueryEvidenceCurrentStep(resultadoSQL);
			String codigoSATBD = resultadoSQL.getData(0, "codigo_sat");
			assertEquals(codigoSATBD, codigoSAT, "No coincide el codigo SAT con la BD");
		} catch (Exception e) {
			throw new AssertionError("Error al consultar la BD FCREPQA");
		}

		dbFCPFACQA.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		return "1. API creada 2. Acceso a la base de datos FCPFACQA";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al enviar un request válido el API responda con el SAT code correctamente.";
	}

	@Override
	public String setTestDesigner() {
		return "Angel Del Río";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_RequestExitosoGetSatCode_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		MetodoGeneral.obtenerTiempoDeEjecucion(result, testCase);
	}

}