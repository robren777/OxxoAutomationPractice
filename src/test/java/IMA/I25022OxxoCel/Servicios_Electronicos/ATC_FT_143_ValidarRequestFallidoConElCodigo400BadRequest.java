package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelParser;
import modelo.BaseExecution;


/**
 * DESCRIPCION:Se valida request fallido con el codigo 400 Bad Request.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_023-Validar request fallido con el codigo 400 Bad Request.
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 26/Febrero/2026
 */
public class ATC_FT_143_ValidarRequestFallidoConElCodigo400BadRequest extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_143_ValidarRequestFallidoConElCodigo400BadRequest_test(HashMap<String, String> data) throws Exception {
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

		// validar_contra_JSON_actualizado
		addStep("Validar campos mandatorios");
		Assert.assertTrue(parser.validaCamposMandatoriosRequest(jsonActualizado, listaJsonPaths),
				"Error al validar los campos mandatorios");

		//Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
		JSONObject json = new JSONObject(jsonActualizado);
		testCase.addTextEvidenceCurrentStep(json.toString(4));

		//Validamos_que_la_respuesta_de_la_petición_sea_el_código_esperado
		String response = api.validaHttpCode2(urlEnpoint, code, jsonActualizado, headers);
		Assert.assertNotNull(response, "Error al validar la petición httpCode " + codeEsperado);

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
		return "ATC_FT_143_ValidarRequestFallidoConElCodigo400BadRequest";
	}

	@Override
	public String setTestDescription() {
		return "Se valida request fallido con el codigo 400 Bad Request.";
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
