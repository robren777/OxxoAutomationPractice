package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.path.json.JsonPath;
import modelo.BaseExecution;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelParser;

/**
 * DESCRIPCION:Se valida que el body del response incluya status,transactionId,
 * id y message
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: - MTC_FT_021-Validar que el
 * body del response incluya status,transactionId, id y message
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 26/Febrero/2026
 */
public class ATC_FT_141_ValidarElBodyDelResponseIncluyaStatusTransactionIdMessage extends BaseExecution {
	
	@Test(dataProvider = "data-provider")

	public void ATC_FT_141_ValidarElBodyDelResponseIncluyaStatusTransactionIdMessage_test(HashMap<String, String> data)
			throws Exception {
		testCase.setProject_Name("I25022 OxxoCel");

		String camposM = data.get("camposMandatorios");
		String jSon = data.get("jSon");

		String codeEsperado = data.get("Codigo");
		int code = Integer.parseInt(codeEsperado);
		String urlEnpoint = data.get("urlEnpoint");
		String validaHeaders = data.get("validaH");
		
		
		OxxoCelApiClient api = new OxxoCelApiClient(testCase);
		OxxoCelParser parser = new OxxoCelParser(testCase);
	
		
		Map<String, String> headers = api.obtenerHeaders(validaHeaders);
		String jsonActualizado = parser.jsonActualizado(jSon);
		// lista_de_campos
		List<String> listaJsonPaths = Arrays.asList(camposM.split("\\s*,\\s*"));
		JsonPath jp = new JsonPath(jsonActualizado);
		String nuevoId = jp.getString("request_payload.transactionId");
		// validar_contra_JSON_actualizado
		addStep("Validar campos mandatorios");
		Assert.assertTrue(parser.validaCamposMandatoriosRequest(jsonActualizado, listaJsonPaths),
				"Error al validar los campos mandatorios");

		//Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
		JSONObject json = new JSONObject(jsonActualizado);
		testCase.addTextEvidenceCurrentStep(json.toString(4));

		//Validamos_que_la_respuesta_de_la_petición_sea_el_código_esperado
		addStep("Se valida respuesta de la petición, se espera código " + codeEsperado);
		String response = api.validaHttpCode2(urlEnpoint, code, jsonActualizado, headers);
		Assert.assertNotNull(response, "Error al validar la petición httpCode " + codeEsperado);

		//addStep("Validar estructura y contenido del response");

		JSONObject jsonR = new JSONObject(response);

		String message = jsonR.optString("message");

		Assert.assertTrue(message.contains("Transaction received and stored successfully"));
		Assert.assertTrue(message.contains(nuevoId));
		Assert.assertTrue(message.contains("ID-"));
		// Agregar evidencia bonita
		testCase.addTextEvidenceCurrentStep(jsonR.toString(4));

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_141_ValidarElBodyDelResponseIncluyaStatusTransactionIdMessage";
	}

	@Override
	public String setTestDescription() {
		return "Se valida que el body del response incluya status,transactionId, id y message";
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
		return "URL Endpoint";
	}

	public void afterTest() {
		// TODO Auto-generated method stub
	}

	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}

}
