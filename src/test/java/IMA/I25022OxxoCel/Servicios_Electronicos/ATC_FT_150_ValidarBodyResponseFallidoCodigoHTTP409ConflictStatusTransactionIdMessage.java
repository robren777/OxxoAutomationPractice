package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelApiClient;
import modelo.BaseExecution;


/**
 * DESCRIPCION:Se valida body del response fallido codigo HTTP 409 Conflict incluya status, transactionId y message.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_030-Validar body del response fallido codigo HTTP 409 Conflict incluya status, transactionId y message.
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 26/Febrero/2026
 */
public class ATC_FT_150_ValidarBodyResponseFallidoCodigoHTTP409ConflictStatusTransactionIdMessage extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_150_ValidarBodyResponseFallidoCodigoHTTP409ConflictStatusTransactionIdMessage_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I25022 OxxoCel");
		
		String jSon = data.get("jSon");
		String codeEsperado = data.get("Codigo");
		int code = Integer.parseInt(codeEsperado);
		String urlEnpoint = data.get("urlEnpoint");
		String validaHeaders = data.get("validaH");	
		
		OxxoCelApiClient api = new OxxoCelApiClient(testCase);
				
		Map<String, String> headers = api.obtenerHeaders(validaHeaders);
		String response = api.validaHttpCode2(urlEnpoint, code, jSon, headers);
		addStep("Se válida que el body del response 'Transaction with this ID already exists'.\"");
		JSONObject jsonR = new JSONObject(response);
		String codeError = jsonR.optString("code");
		Assert.assertTrue(codeError.startsWith("X409"), "Transaction with this ID already exists.");
		testCase.addTextEvidenceCurrentStep(jsonR.toString(4));
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_150_ValidarBodyResponseFallidoCodigoHTTP409ConflictStatusTransactionIdMessage";
	}

	@Override
	public String setTestDescription() {
		return "Se valida body del response fallido codigo HTTP 409 Conflict incluya status, transactionId y message.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Mario Garza";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Url Endpoint";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
