package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelParser;
import io.restassured.path.json.JsonPath;
import modelo.BaseExecution;


/**
 * DESCRIPCION:Se valida provider_id incorrecto (diferente a freedompop), API rechaza el request con HTTP 400 Bad Request.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_019_ValidarProviderIdIncorrectoFreedompopRechazaRequest
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_139_ValidarProviderIdIncorrectoFreedompopRechazaRequest extends BaseExecution {

	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_139_ValidarProviderIdIncorrectoFreedompopRechazaRequest_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");

		
		String httpCode = data.get("Codigo"); //Ingresar en el dataP el codigo http esperado en la peticion
		int code = Integer.parseInt(httpCode);
		String jSon = data.get("jSon");
		String urlEndpoint = data.get("urlEndpoint");//Ingresar api del endPoint a validar
		String validaHeaders = data.get("validaH");

		
		OxxoCelApiClient api = new OxxoCelApiClient(testCase);
		OxxoCelParser parser = new OxxoCelParser(testCase);
		Map<String, String> headers = api.obtenerHeaders(validaHeaders);
		String jsonActualizado = parser.jsonActualizado(jSon);
	

		addStep("Validamos Provider_Id del request");
		JsonPath jp = new JsonPath(jsonActualizado);
		String provider = jp.getString("provider_id");
		testCase.addTextEvidenceCurrentStep(
			    "Provider_id del request: " + provider);

		addStep("Validar codigo de respuesta HTTP 400 (Bad Request)");
		String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado, headers);
		Assert.assertNotNull(response, "Error al validar la petición HTTP 400");
		JSONObject json = new JSONObject(response);
		testCase.addTextEvidenceCurrentStep(json.toString(4));

		addStep("Validar provider_id incorrecto y rechazo del servicio");
		boolean providerIncorrecto = parser.validaProviderIncorrecto(
		    response,
		    provider,
		    "FREEDOMPOP"
		);

		//Validar rechazo del servicio
		boolean esRechazado = parser.validaRechazoServicio(response, code);

		// Evidencia
		testCase.addTextEvidenceCurrentStep(
		    "provider_id: " + provider+
		    " | Provider incorrecto: " + providerIncorrecto +
		    " | Request rechazado: " + esRechazado 
		);

		// Asserts
		Assert.assertTrue(esRechazado,
		    "El servicio NO rechazó el provider inválido");
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_139_ValidarProviderIdIncorrectoFreedompopRechazaRequest";
	}

	@Override
	public String setTestDescription() {
		return "Se valida provider_id incorrecto (diferente a freedompop), API rechaza el request con HTTP 400 Bad Request.";
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
		return "Haber recibido un request con información errónea en el provider_id.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
