package WEB.estandarizacionHomologacionImpuestos.homologacionAPI_CHG0039730;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import modelo.BaseExecution;
import WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo.MetodoGeneral;
import WEB.util.GlobalVariables;
import utils.ApiMethodsUtil;

public class ATC_FT_002_RequestFallidoGetSatCode extends BaseExecution {

	/**
	 * DESCRIPCION: Validar que al enviar un request con datos no existentes el API
	 * responda el mensaje de error esperado.
	 * 
	 * PREREQUISITOS: 1.API creada
	 * 
	 * @author Francisco Torres Delgado
	 */

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_RequestFallidoGetSatCode_test(HashMap<String, String> data) throws Exception {
		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
				GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

		String uri = String.format(data.get("uri"));
		String supergrupo = data.get("supergrupo");
		String categoria = data.get("categoria");
		String subcategoria = data.get("subcategoria");
		String segmento = data.get("segmento");
		String subsegmento = data.get("subsegmento");

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
		assertEquals(jsonResponse, "SAT code not found", "No coincide el mensaje de error");

		// ---------- PASO 2
		addStep("Validar que el codigo SAT no exista en la BD FCPFACQA");

		String selectGetSatCode = "select supergrupo, categoria, sub_categoria, segmento, sub_segmento, codigo_sat "
				+ "from XXSAT.XXSAT_CAT_PROD_SERV " + "WHERE SUPERGRUPO = " + supergrupo + " AND CATEGORIA = "
				+ categoria + " AND SUB_CATEGORIA = " + subcategoria + " AND SEGMENTO = " + segmento
				+ " AND SUB_SEGMENTO = " + subsegmento;
		try {
			utils.sql.SQLResult resultadoSQL = dbFCPFACQA.executeQuery(selectGetSatCode);
			testCase.addQueryEvidenceCurrentStep(resultadoSQL);
			int filas = resultadoSQL.getRowCount();
			assertEquals(filas, 0, "El codigo SAT existe en la BD");
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
		return "Validar que al enviar un request con datos no existentes el API responda el mensaje de error esperado.";
	}

	@Override
	public String setTestDesigner() {
		return "Angel Del Río";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_RequestFallidoGetSatCode_test";
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
