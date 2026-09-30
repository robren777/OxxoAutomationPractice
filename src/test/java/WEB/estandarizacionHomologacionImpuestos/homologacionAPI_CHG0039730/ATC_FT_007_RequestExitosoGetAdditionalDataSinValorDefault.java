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

public class ATC_FT_007_RequestExitosoGetAdditionalDataSinValorDefault extends BaseExecution {

	/**
	 * DESCRIPCION: Validar que al enviar un request válido para un tipo de dato sin
	 * valor default, el API responda con los datos adicionales correctamente.
	 * 
	 * PREREQUISITOS: 1. API creada 2. Acceso a la base de datos FCPFACQA
	 * 
	 * @author Angel Del Río
	 */

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_RequestExitosoGetAdditionalDataSinValorDefault_test(HashMap<String, String> data) throws Exception {

		utils.sql.SQLUtil dbFCPFACQA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCPFACQA_VIRTUAL,
				GlobalVariables.DB_USER_FCPFACQA_VIRTUAL, GlobalVariables.DB_PASSW_FCPFACQA_VIRTUAL);

		String uri = String.format(data.get("uri"));
		String supergrupo = data.get("supergrupo");
		String categoria = data.get("categoria");
		String subcategoria = data.get("subcategoria");
		String datosAdicionales = data.get("datosAdicionales");

		String uriParams = String.format(supergrupo + "/" + categoria + "/" + subcategoria);

		String datosAdicionalesAPI;
		String[] datosAdicionalesAPIArr;
		
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
			datosAdicionalesAPI = jsonNode.get("catalogValue").asText();
			datosAdicionalesAPIArr = datosAdicionalesAPI.split("\\|");
			assertEquals(datosAdicionalesAPIArr[0], datosAdicionales, "No coinciden los datos adicionales con la API");
		} catch (Exception e) {
			throw new AssertionError("Error al obtener los datos de la respuesta");
		}

		// ---------- PASO 2
		addStep("Validar que los datos adicionales sean iguales en la BD FCPFACQA");

		String selectGetAdditionalData = "select VC.VALOR_CATALOGO as DATO_REQUERIDO, DR.VALOR_DEFAULT "
				+ "FROM XXSAT.XXSAT_DATO_REQUERIDO DR "
				+ "INNER JOIN XXSAT.XXSAT_VALOR_CATALOGO VC ON VC.ID = DR.ID_TIPO_DATO "
				+ "inner join XXSAT.XXSAT_TIPO_CATALOGO TC on TC.id = VC.ID_TIPO_CATALOGO and TC.TIPO_CATALOGO = 'Datos requeridos' "
				+ "WHERE DR.ID_SUPER_GRUPO = " + supergrupo + " AND DR.ID_CATEGORIA = " + categoria
				+ " AND DR.ID_SUB_CATEGORIA = " + subcategoria;
		try {
			utils.sql.SQLResult resultadoSQL = dbFCPFACQA.executeQuery(selectGetAdditionalData);
			testCase.addQueryEvidenceCurrentStep(resultadoSQL);
			String datosAdicionalesBD = resultadoSQL.getData(0, "dato_requerido");
			String valorDefaultBD = resultadoSQL.getData(0, "valor_default");
			if (valorDefaultBD == null) {
				valorDefaultBD = "NA";
			}
			
			assertEquals(datosAdicionalesBD, datosAdicionalesAPIArr[0], "No coinciden los datos adicionales con la BD");
			assertEquals(valorDefaultBD, datosAdicionalesAPIArr[1], "No coincide el valor default con la BD");
		} catch (Exception e) {
			throw new AssertionError("Error al consultar la BD FCPFACQA");
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
		return "Validar que al enviar un request válido para un tipo de dato sin valor default, el API responda con los datos adicionales correctamente.";
	}

	@Override
	public String setTestDesigner() {
		return "Angel Del Río";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_RequestExitosoGetAdditionalDataSinValorDefault_test";
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