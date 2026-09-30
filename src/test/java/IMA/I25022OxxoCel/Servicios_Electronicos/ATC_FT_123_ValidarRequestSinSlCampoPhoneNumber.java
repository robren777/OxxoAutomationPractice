package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelParser;

/**
 * DESCRIPCION: Se valida request sin el campo phoneNumber.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_003-Validar request sin el campo phoneNumber.
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_123_ValidarRequestSinSlCampoPhoneNumber extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_123_ValidarRequestSinSlCampoPhoneNumber_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
		
		String camposM = data.get("camposMandatorios");
		String jSon = data.get("jSon");
		
		String codeEsperado = data.get("Codigo");
		int code = Integer.parseInt(codeEsperado);
		String urlEndpoint = data.get("urlEndpoint");
		String validaHeaders = data.get("validaH");
		
		OxxoCelApiClient api = new OxxoCelApiClient(testCase);
		OxxoCelParser parser = new OxxoCelParser(testCase);		
	
		Map<String, String> headers = api.obtenerHeaders(validaHeaders);
		String jsonActualizado = parser.jsonActualizado(jSon);

		// lista_de_campos
		List<String> listaJsonPaths = Arrays.asList(camposM.split("\\s*,\\s*"));

		addStep("Validar que si el request no contiene información en el campo phoneNumber,se rechaze la petición");
		Assert.assertTrue(parser.validaCamposMandatoriosRequest(jsonActualizado,listaJsonPaths), "Error al validar los campos mandatorios");
 
		JSONObject json = new JSONObject(jSon);
		testCase.addTextEvidenceCurrentStep(json.toString(4));
		
		addStep("Se valida respuesta de la petición, código esperado "+codeEsperado);
		String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado, headers);
		Assert.assertNotNull(response, "Error al validar la petición httpCode "+ codeEsperado);
		
		JSONObject jsonR = new JSONObject(response);
		testCase.addTextEvidenceCurrentStep(jsonR.toString(4));
	
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_123_ValidarRequestSinSlCampoPhoneNumber";
	}

	@Override
	public String setTestDescription() {
		return "Se valida request sin el campo phoneNumber.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Leonardo Leonel Ramírez Escobedo";
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
