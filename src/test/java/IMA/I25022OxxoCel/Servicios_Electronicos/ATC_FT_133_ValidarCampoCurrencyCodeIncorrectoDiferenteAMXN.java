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
 * DESCRIPCION: Se valida campo currencyCode incorrecto (Cualquiera diferente a MXN).Solo 3 letras de longitud y mayusculas.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_013_ValidarCampoCurrencyCodeIncorrectoDiferenteAMXN
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_133_ValidarCampoCurrencyCodeIncorrectoDiferenteAMXN extends BaseExecution {

	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_133_ValidarCampoCurrencyCodeIncorrectoDiferenteAMXN_test(HashMap<String, String> data) throws Exception {
		
	    
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
	
		// lista_de_campos
	
		
		addStep("Validar campo currencyCode incorrecto (≠ MXN) con formato válido");
		JsonPath jp = new JsonPath(jsonActualizado);
		String currencyCode = jp.getString("request_payload.currencyCode");
	    boolean esIncorrectoValido = parser.validaCurrencyCodeIncorrectoFormatoValido(jsonActualizado, currencyCode);

	    // Evidencia
	    testCase.addTextEvidenceCurrentStep(
	        "Campo currencyCode: " + currencyCode  +
	        " | Regla: 3 letras MAYÚSCULAS y diferente de MXN" +
	        " | Resultado: " + esIncorrectoValido
	    );

	    // Assert final
	    Assert.assertTrue(esIncorrectoValido, 
	        "El campo currencyCode no cumple: debe ser diferente de MXN y formato válido");
	
		//Esperar_codigo_400_ya_que_el_formato_del_currency_code_es_incorrecto
	    addStep("Validar codigo de respuesta del response, código esperado " + code );
	    String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado, headers);
	    Assert.assertNotNull(response, "Error al validar la petición httpCode " + httpCode);

	    JSONObject json = new JSONObject(response);
	    testCase.addTextEvidenceCurrentStep(json.toString(4));
	    
	    
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_133_ValidarCampoCurrencyCodeIncorrectoDiferenteAMXN";
	}

	@Override
	public String setTestDescription() {
		return "Se valida campo currencyCode incorrecto (Cualquiera diferente a MXN).Solo 3 letras de longitud y mayusculas.";
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
		return "Haber recibido un request con información errónea.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
