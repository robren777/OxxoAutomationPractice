package RDM.ruteoDinamico.crearOrdenesPedido;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;

/**
 * PREREQUISITOS: Existencia de informaci�n en la Vista XXFC_WS_CREARORDENESPEDIDO_V
 * 
 * 
 * DESCRIPCION: Validar que se haya recibido el pedido a Unigis con estatus 200
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_001_CrearOrdenesPedido extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_CrearOrdenesPedido_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String postPath = data.get("postPath");
		String postBody = data.get("postBody");
		String postExpCode = data.get("postExpectedCode");
		String expBodyRes = data.get("expectedBodyResponse");
		String baseURL = "https://cloud-test.unigis.com";
		String postBodyResponse;
		int expectedBodyResponse = Integer.valueOf(expBodyRes);
		int codigo;
		int postExpectedCode = Integer.parseInt(postExpCode);
		int postStatusCode;
		/*
		 * UTILERIAS
		 */
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
		Response postRespuesta;
		JSONObject postjson;
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		// PASO 1
		addStep("Ejecutar servicio con datos en formato json con informaci�n de pedido v�lido");
		System.out.println("PASO 1");
		
		testCase.addCodeEvidenceCurrentStep("- request:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + baseURL + postPath);
		testCase.addCodeEvidenceCurrentStep("- body:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + baseURL + "\n" + postBody);
		
		postRespuesta = api.postRequestMethod(postPath, postBody);
		
		addStep("Respuesta del servicio:");
		
		postStatusCode = postRespuesta.getStatusCode();
		testCase.addCodeEvidenceCurrentStep("- statusCode: " + postStatusCode);
		
		assertEquals(postStatusCode, postExpectedCode);
		
		postBodyResponse = postRespuesta.asPrettyString();
		testCase.addCodeEvidenceCurrentStep("- bodyResponse:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + postBodyResponse);
		
		postjson = new JSONObject(postBodyResponse);
		codigo = postjson.getInt("d");
		testCase.addTextEvidenceCurrentStep("- codigoOperacion: " + codigo);
		
		assertEquals(codigo, expectedBodyResponse);
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se haya recibido el pedido a Unigis con estatus 200";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_CrearOrdenesPedido_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
