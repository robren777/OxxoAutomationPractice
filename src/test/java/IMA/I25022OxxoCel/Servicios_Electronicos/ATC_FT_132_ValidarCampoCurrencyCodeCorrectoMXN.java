package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import IMA.OM_API.ConfigReader;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelDbValidator;
import IMA.OM_API.OxxoCelParser;
import io.restassured.path.json.JsonPath;
import modelo.BaseExecution;
import utils.winium.WiniumUtil;

/**
 * DESCRIPCION: Se valida campo currencyCode correcto (MXN).
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_012_ValidarCampoCurrencyCodeCorrectoMXN
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_132_ValidarCampoCurrencyCodeCorrectoMXN extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_132_ValidarCampoCurrencyCodeCorrectoMXN_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
		String winiumPath = ConfigReader.get("winium.path");
		WiniumUtil u = new WiniumUtil(winiumPath);
		
		String httpCode = data.get("Codigo"); //Ingresar en el dataP el codigo http esperado en la peticion
		int code = Integer.parseInt(httpCode);
		String jSon = data.get("jSon");
		String urlEndpoint = data.get("urlEndpoint");//Ingresar api del endPoint a validar
		String validaHeaders = data.get("validaH");
		
	
	    OxxoCelApiClient api = new OxxoCelApiClient(testCase);
		OxxoCelParser parser = new OxxoCelParser(testCase);
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);

	    Map<String, String> headers = api.obtenerHeaders(validaHeaders);
		String jsonActualizado = parser.jsonActualizado(jSon);
		
			addStep("Validamos que el campo currencyCode sea MXN");
		JsonPath jp = new JsonPath(jsonActualizado);
		String currencyCode = jp.getString("request_payload.currencyCode");
		String nuevoId = jp.getString("request_payload.transactionId");
	    boolean esValido = parser.validaCurrencyCodeMXN(jsonActualizado, currencyCode);

	    // Agregamos_evidencia
	    testCase.addTextEvidenceCurrentStep(
	        "Campo currencyCode: " + currencyCode +
	        " | Valor esperado: MXN" +
	        " | Resultado: " + esValido
	    );

	    //esperamos_currency_code_(MXN)
	    Assert.assertTrue(esValido, "El campo currencyCode no es MXN");
		
	    addStep("Validar codigo de respuesta del HttpCode, código esperado " + httpCode);
	    String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado, headers);
	    Assert.assertNotNull(response, "Error al validar la petición httpCode " + httpCode);

	    JSONObject json = new JSONObject(response);
	    testCase.addTextEvidenceCurrentStep(json.toString(4));

	    addStep("Validamos que en BD la transaccion haya llegado");
	    u.hardWait(5);
	    boolean resultado = db.consultaTransacciones(nuevoId);
	    Assert.assertTrue(resultado, "No se encontró la transacción en BD");
	        
	    
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_132_ValidarCampoCurrencyCodeCorrectoMXN";
	}

	@Override
	public String setTestDescription() {
		return "Se valida campo currencyCode correcto (MXN).";
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
		return "Haber recibido un request con la información completa.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
