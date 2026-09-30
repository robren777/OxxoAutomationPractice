package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import IMA.OM_API.OxxoCelApiClient;

/**
 * DESCRIPCION: Se valida request sin el campo horaenvio.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_039-Validar request sin el campo horaenvio (nulo).
 *
 * PREREQUISITOS: - Haber recibido un request con información incompleta.
 *
 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 26/Febrero/2026
 */
public class ATC_FT_159_ValidarRequestSinElCampoHoraEnvio extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_159_ValidarRequestSinElCampoHoraEnvio_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
		String jSon = data.get("jSon");
		String codeEsperado = data.get("Codigo");
		int code = Integer.parseInt(codeEsperado);
		String urlEnpoint = data.get("urlEnpoint");
		String validaHeaders = data.get("validaH");
		
		OxxoCelApiClient api = new OxxoCelApiClient(testCase);
				
		Map<String, String> headers = api.obtenerHeaders(validaHeaders);
		String response = api.validaHttpCode2(urlEnpoint, code, jSon, headers);
		
		addStep("Validar respuesta de error 400");
		JSONObject jsonR = new JSONObject(response);
		String codeError = jsonR.optString("code");
		Assert.assertTrue(codeError.startsWith("X400"), "El code no corresponde a error 400");
		testCase.addTextEvidenceCurrentStep(jsonR.toString(4));
	
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_159_ValidarRequestSinElCampoHoraEnvio";
	}

	@Override
	public String setTestDescription() {
		return "Se valida request sin el campo horaenvio (nulo).";
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
