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
 * DESCRIPCION: Se valida que el request contenga todos los campos llenos (transactionId, phoneNumber, currencyCode, price, country, extra_data (memberid, promotionid, fechaenvio y horaenvio ), provider_id).
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_001-Validar que el request contenga todos los campos llenos (transactionId, phoneNumber, currencyCode, price, country, extra_data (memberid, promotionid, fechaenvio y horaenvio ), provider_id).
 *
 * PREREQUISITOS: - Tener configurado el ambiente, Haber recibido un request con información completa.
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_121_ValidarQueElRequestContengaTodosLosCamposLlenos extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_121_ValidarQueElRequestContengaTodosLosCamposLlenos_test(HashMap<String, String> data) throws Exception {
		
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

		// validar_contra_JSON_actualizado
		addStep("Validamos que el request tenga información en los campos mandatorios");
		Assert.assertTrue(parser.validaCamposMandatoriosRequest(jsonActualizado, listaJsonPaths),"Error al validar los campos mandatorios");

//		Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
		JSONObject json = new JSONObject(jsonActualizado);
		testCase.addTextEvidenceCurrentStep(json.toString(4));
		
//		Validamos_que_la_respuesta_de_la_petición_sea_el_código_esperado
		addStep("Validamos respuesta de la petición, se espera código "+ codeEsperado);
		String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado,headers);
		Assert.assertNotNull(response, "Error al validar la petición httpCode "+ codeEsperado);
		
//		Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
		JSONObject jsonR = new JSONObject(response);
		testCase.addTextEvidenceCurrentStep(jsonR.toString(4));
	
	
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_121_ValidarQueElRequestContengaTodosLosCamposLlenos";
	}

	@Override
	public String setTestDescription() {
		return "Se valida que el request contenga todos los campos llenos (transactionId, phoneNumber, currencyCode, price, extra_data (memberid, promotionid, fechaenvio y horaenvio ), provider_id).";
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
