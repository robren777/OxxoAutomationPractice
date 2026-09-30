package WM.ruteoDinamico.crearOrdenesPedido;

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
 * DESCRIPCION: Validar campo de RefCliente arroje error de domicilio al enviar dato incorrecto con campo 
 * 				ValidarDomicilioOrden en true
 * @author cristhian.m.gonzalez@accenture.com
 */

public class ATC_FT_004_ValidarErrorDomicilio extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarErrorDomicilio_test(HashMap<String, String> data) throws Exception {
		/*
		 * VARIABLES
		 */
		String postPathAut = data.get("postPathAut");
		String postPathInfaz = data.get("postPathInfaz");
		String postBodyAut = data.get("postBodyAut");
		String postBodyInfaz = data.get("postBodyInfaz");
		String postExpCode = data.get("postExpectedCode");
		String expBodyRes = data.get("expectedBodyResponse");
		String baseURL = data.get("baseUrl");
		//String baseURL = "https://cloud-test.unigis.com";
		String postBodyResponse;
		String jwtKey;
		int expectedBodyResponse = Integer.valueOf(expBodyRes);
		int codigo;
		int postExpectedCode = Integer.parseInt(postExpCode);
		int postStatusCode;
		HashMap<String,String> headersListInfaz = new HashMap<>();
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
		addStep("Obtener llave JWT para autorizaci�n de la interfaz webmethod para crear orden pedido");
		System.out.println("PASO 1");
		
		testCase.addBoldTextEvidenceCurrentStep("- request:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + baseURL + postPathAut);
		testCase.addBoldTextEvidenceCurrentStep("- body:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + postBodyAut + "\n");
		
		//OBTENER LLAVE JWT
		postRespuesta = api.postRequestMethod(baseURL + postPathAut, postBodyAut);
		
		addStep("Respuesta del servicio:");
		
		postStatusCode = postRespuesta.getStatusCode();
		testCase.addBoldTextEvidenceCurrentStep("- statusCode (jwt):\n");
		testCase.addCodeEvidenceCurrentStep("\t" + postStatusCode);
		
		assertEquals(postStatusCode, postExpectedCode);
		
		postBodyResponse = postRespuesta.asPrettyString();
		testCase.addBoldTextEvidenceCurrentStep("- bodyResponse (jwt):\n");
		testCase.addCodeEvidenceCurrentStep("\n" + postBodyResponse);
		
		postjson = new JSONObject(postBodyResponse);
		jwtKey = postjson.getString("JWT");
		testCase.addBoldTextEvidenceCurrentStep("- llave JWT:\n");
		testCase.addCodeEvidenceCurrentStep("\t" + jwtKey);
		
		// PASO 2
		addStep("Ejecutar servicio con datos en formato json con informaci�n con dato vac�o del campo RefCliente");
		System.out.println("PASO 2");
		
		testCase.addBoldTextEvidenceCurrentStep("- request:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + baseURL + postPathInfaz);
		testCase.addBoldTextEvidenceCurrentStep("- body:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + postBodyInfaz + "\n" );
		
		headersListInfaz.put("Accept", "*/*");
		headersListInfaz.put("Content-Type", "application/json");
		headersListInfaz.put("Authorization", "Bearer " + jwtKey);
		
		postRespuesta = api.postRequestWithHeaders(baseURL + postPathInfaz, postBodyInfaz, headersListInfaz);
		
		addStep("Respuesta del servicio:");
		
		postStatusCode = postRespuesta.getStatusCode();
		testCase.addBoldTextEvidenceCurrentStep("- statusCode:\n");
		testCase.addCodeEvidenceCurrentStep("\t" + postStatusCode);
		
		assertEquals(postStatusCode, postExpectedCode);
		
		postBodyResponse = postRespuesta.asPrettyString();
		testCase.addBoldTextEvidenceCurrentStep("- bodyResponse:\n");
		testCase.addCodeEvidenceCurrentStep("\n" + postBodyResponse);
		
		postjson = new JSONObject(postBodyResponse);
		codigo = postjson.getInt("d");
		testCase.addBoldTextEvidenceCurrentStep("- codigoOperacion:");
		testCase.addCodeEvidenceCurrentStep("\t" + codigo);
		
		assertEquals(codigo, expectedBodyResponse);
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Existencia de informaci�n en la Vista XXFC_WS_CREARORDENESPEDIDO_V";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar campo de RefCliente arroje error de domicilio al"
				+ " enviar dato vacio con campo ValidarDomicilioOrden en true";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "cristhian.m.gonzalez@accenture.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_ValidarErrorDomicilio_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
