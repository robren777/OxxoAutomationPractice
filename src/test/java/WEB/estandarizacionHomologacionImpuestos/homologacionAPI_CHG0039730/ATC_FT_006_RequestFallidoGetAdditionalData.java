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

public class ATC_FT_006_RequestFallidoGetAdditionalData extends BaseExecution {

	/**
	 * DESCRIPCION: Validar que al enviar un request con datos no existentes el API
	 * responda el mensaje de error esperado.
	 * 
	 * PREREQUISITOS: 1. API creada 2. Acceso a la base de datos FCPFACQA
	 * 
	 * @author Angel Del Río
	 */

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_RequestFallidoGetAdditionalData_test(HashMap<String, String> data) throws Exception {

		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
				GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

		String uri = String.format(data.get("uri"));
		String supergrupo = data.get("supergrupo");
		String categoria = data.get("categoria");
		String subcategoria = data.get("subcategoria");

		String uriParams = String.format(supergrupo + "/" + categoria + "/" + subcategoria);

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
		assertEquals(jsonResponse, "No Additional Data Found", "No coincide el mensaje de error");

		// ---------- PASO 2
		addStep("Validar que no existan datos adicionales en la BD FCPFACQA");

		String selectGetAdditionalData = "select VC.VALOR_CATALOGO as DATO_REQUERIDO "
				+ "FROM XXSAT.XXSAT_DATO_REQUERIDO DR "
				+ "INNER JOIN XXSAT.XXSAT_VALOR_CATALOGO VC ON VC.ID = DR.ID_TIPO_DATO "
				+ "inner join XXSAT.XXSAT_TIPO_CATALOGO TC on TC.id = VC.ID_TIPO_CATALOGO and TC.TIPO_CATALOGO = 'Datos requeridos' "
				+ "WHERE DR.ID_SUPER_GRUPO = " + supergrupo + " AND DR.ID_CATEGORIA = " + categoria
				+ " AND DR.ID_SUB_CATEGORIA = " + subcategoria;
		try {
			utils.sql.SQLResult resultadoSQL = dbFCPFACQA.executeQuery(selectGetAdditionalData);
			testCase.addQueryEvidenceCurrentStep(resultadoSQL);
			int filas = resultadoSQL.getRowCount();
			assertEquals(filas, 0, "Existen datos adicionales en la BD");
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
		return "ATC_FT_006_RequestFallidoGetAdditionalData_test";
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