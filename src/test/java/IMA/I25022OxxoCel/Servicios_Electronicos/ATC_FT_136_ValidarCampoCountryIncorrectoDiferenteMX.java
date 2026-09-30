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
 * DESCRIPCION:Se valida campo country incorrecto (Cualquiera diferente a MX).
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_016_ValidarCampoCountryIncorrectoDiferenteMX
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_136_ValidarCampoCountryIncorrectoDiferenteMX extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_136_ValidarCampoCountryIncorrectoDiferenteMX_test(HashMap<String, String> data) throws Exception {
		
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
	   
		addStep("Validar campo country incorrecto (diferente a MX o formato inválido)");
		JsonPath jp = new JsonPath(jsonActualizado);
		String country = jp.getString("request_payload.country");
		boolean esIncorrecto = parser.validaCatalogo(jsonActualizado,country,"^[A-Z]{2}$","MX",false //esperamos que sea inválido
		);
		// Asserts
		Assert.assertTrue(esIncorrecto,
		    "El campo country NO fue detectado como inválido");
		testCase.addTextEvidenceCurrentStep("Campo country: " + country );

		addStep("Validar codigo de respuesta del HttpCode, código esperado " + httpCode);
		String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado, headers);

		Assert.assertNotNull(response, "Error al validar la petición httpCode " + httpCode);

		JSONObject json = new JSONObject(response);
		testCase.addTextEvidenceCurrentStep(json.toString(4));

		addStep("Se realiza validacion de código de rechazo");
		boolean esRechazado = parser.validaRechazoServicio(response, code);		
		Assert.assertTrue(esRechazado,
			    "El servicio NO rechazó un country inválido");
		
		// Evidencia
		testCase.addTextEvidenceCurrentStep(
		    "Campo country: " + country +
		    " | Regla: inválido si es diferente a MX o formato incorrecto" +
		    " | Resultado validación: " + esIncorrecto +
		    " | Request rechazado: " + esRechazado
		);
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_136_ValidarCampoCountryIncorrectoDiferenteMX";
	}

	@Override
	public String setTestDescription() {
		return "Se valida campo country incorrecto (Cualquiera diferente a MX).";
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
		return "Haber recibido request con información completa, pero con datos no válidos.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
