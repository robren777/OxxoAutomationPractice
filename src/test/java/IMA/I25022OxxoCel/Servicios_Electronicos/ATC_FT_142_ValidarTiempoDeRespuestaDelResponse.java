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
 * DESCRIPCION:Se valida tiempo de respuesta del response.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_022_ValidarTiempoDeRespuestaDelResponse
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 26/Febrero/2026
 */
public class ATC_FT_142_ValidarTiempoDeRespuestaDelResponse extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_142_ValidarTiempoDeRespuestaDelResponse_test(HashMap<String, String> data) throws Exception {
		
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
		Assert.assertTrue(parser.validaCamposMandatoriosRequest(jsonActualizado, listaJsonPaths),"Error al validar los campos mandatorios");
		//Validamos_que_la_respuesta_de_la_petición_sea_el_código_esperado
		addStep("Se valida respuesta de la petición, se espera código "+ codeEsperado);
		Map<String, Object> resultado = api.ejecutarPeticionConTiempo(urlEnpoint, code, jsonActualizado, headers);

		//Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
		JSONObject json = new JSONObject(resultado);
		testCase.addTextEvidenceCurrentStep(json.toString(4));
		
		long tiempoRespuesta = (long) resultado.get("tiempo");

		addStep("Validar el tiempo de respuesta");
		testCase.addTextEvidenceCurrentStep("Tiempo de respuesta: " + tiempoRespuesta + " ms");

		Assert.assertTrue(tiempoRespuesta >= 1000 || tiempoRespuesta<=20000, "Tiempo excedido");	
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_142_ValidarTiempoDeRespuestaDelResponse";
	}

	@Override
	public String setTestDescription() {
		return "ValidarTiempoDeRespuestaDelResponse";
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
		return null;
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
