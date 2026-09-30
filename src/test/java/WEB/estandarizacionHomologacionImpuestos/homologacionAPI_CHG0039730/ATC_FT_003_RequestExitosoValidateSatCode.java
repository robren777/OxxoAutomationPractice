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
import modelo.BaseExecution;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.util.GlobalVariables;
import utils.ApiMethodsUtil;

public class ATC_FT_003_RequestExitosoValidateSatCode extends BaseExecution {

	/**
	 * DESCRIPCION: Validar que al enviar un request válido el API responda con los
	 * datos del SAT code correctamente.
	 * 
	 * PREREQUISITOS: 1. API creada 2. Acceso a la base de datos FCPFACQA
	 * 
	 * @author Angel Del Río
	 */

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_RequestExitosoValidateSatCode_test(HashMap<String, String> data) throws Exception {

		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
				GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

		String uri = String.format(data.get("uri"));
		String supergrupo = data.get("supergrupo");
		String categoria = data.get("categoria");
		String subcategoria = data.get("subcategoria");
		String segmento = data.get("segmento");
		String subsegmento = data.get("subsegmento");
		String codigoSAT = data.get("codigoSAT");

		String uriParams = String.format(codigoSAT);

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
			String supergrupoAPI = jsonNode.get("superGroup").asText();
			String categoriaAPI = jsonNode.get("category").asText();
			String subcategoriaAPI = jsonNode.get("subCategory").asText();
			String segmentoAPI = jsonNode.get("segment").asText();
			String subsegmentoAPI = jsonNode.get("subSegment").asText();

			assertEquals(codigoSATAPI, codigoSAT, "No coincide el código SAT con la API");
			assertEquals(supergrupoAPI, supergrupo, "No coincide el supergrupo con la API");
			assertEquals(categoriaAPI, categoria, "No coincide la categoría con la API");
			assertEquals(subcategoriaAPI, subcategoria, "No coincide la subcategoría con la API");
			assertEquals(segmentoAPI, segmento, "No coincide el segmento con la API");
			assertEquals(subsegmentoAPI, subsegmento, "No coincide el subsegmento con la API");
		} catch (Exception e) {
			throw new AssertionError("Error al obtener los datos de la respuesta");
		}

		// ---------- PASO 2
		addStep("Validar que los datos del codigo SAT sean iguales en la BD FCPFACQA");

		String selectValidateSatCode = "select SUPERGRUPO, CATEGORIA, SUB_CATEGORIA, SEGMENTO, SUB_SEGMENTO, CODIGO_SAT from XXSAT.XXSAT_CAT_PROD_SERV "
				+ "WHERE CODIGO_SAT = " + codigoSAT;
		try {
			utils.sql.SQLResult resultadoSQL = dbFCPFACQA.executeQuery(selectValidateSatCode);
			testCase.addQueryEvidenceCurrentStep(resultadoSQL);
			String codigoSATBD = resultadoSQL.getData(0, "codigo_sat");
			String supergrupoBD = resultadoSQL.getData(0, "supergrupo");
			String categoriaBD = resultadoSQL.getData(0, "categoria");
			String subcategoriaBD = resultadoSQL.getData(0, "sub_categoria");
			String segmentoBD = resultadoSQL.getData(0, "segmento");
			String subsegmentoBD = resultadoSQL.getData(0, "sub_segmento");

			assertEquals(codigoSATBD, codigoSAT, "No coincide el codigo SAT con la BD");
			assertEquals(supergrupoBD, supergrupo, "No coincide el supergrupo con la BD");
			assertEquals(categoriaBD, categoria, "No coincide la categoria con la BD");
			assertEquals(subcategoriaBD, subcategoria, "No coincide la subcategoria SAT con la BD");
			assertEquals(segmentoBD, segmento, "No coincide el segmento con la BD");
			assertEquals(subsegmentoBD, subsegmento, "No coincide el subsegmento con la BD");
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
		return "Validar que al enviar un request válido el API responda con los datos del SAT code correctamente.";
	}

	@Override
	public String setTestDesigner() {
		return "Angel Del Río";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_RequestExitosoValidateSatCode_test";
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