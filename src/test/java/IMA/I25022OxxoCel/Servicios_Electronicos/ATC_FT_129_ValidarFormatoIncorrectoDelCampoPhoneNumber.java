package IMA.I25022OxxoCel.Servicios_Electronicos;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelDbValidator;
import IMA.OM_API.OxxoCelParser;
import io.restassured.path.json.JsonPath;
import modelo.BaseExecution;
import static org.awaitility.Awaitility.await;
import java.util.concurrent.TimeUnit;

/**
 * DESCRIPCION: Se valida formato incorrecto del campo phoneNumber (ingresar numero que no empieze con 52/que no contenga la longitud adecuada)..
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_009_ValidarFormatoIncorrectoDelCampoPhoneNumber
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_129_ValidarFormatoIncorrectoDelCampoPhoneNumber extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_129_ValidarFormatoIncorrectoDelCampoPhoneNumber_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
		
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
		

		addStep("Validar formato del campo phoneNumber, se ingreso un valor que no cumple con el formato");
		JsonPath jp = new JsonPath(jsonActualizado);
		String phoneNumber = jp.getString("request_payload.phoneNumber");
		String nuevoId = jp.getString("request_payload.transactionId");

		 //Validamo_exista_valor_en_el_campo_phoneNumber
	    Assert.assertNotNull(phoneNumber, "El campo phoneNumber no existe o es null");

	    //Validamos_que_cumpla_con_el_formato_regex
	    String regex = "^52[0-9]{10}$";
	    boolean cumpleFormato = phoneNumber.matches(regex);
	    //esperamos_que_NO_cumpla_el_formato
	    Assert.assertFalse(cumpleFormato,
	        "ERROR: El campo phoneNumber cumple el formato, pero se esperaba incorrecto: " + phoneNumber);
	    testCase.addTextEvidenceCurrentStep(
	    	    "Validación phoneNumber\n" +
	    	    "Valor: " + phoneNumber + "\n" +
	    	    "Regex: " + regex + "\n" +
	    	    "Resultado: " + (cumpleFormato ? "OK" : "FAIL"));
	    testCase.addTextEvidenceCurrentStep("phoneNumber inválido: " + phoneNumber);
	
		addStep("Validar codigo de respuesta del HttpCode, código esperado "+httpCode);
		String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado,headers);
		Assert.assertNotNull(response, "Error al validar la petición httpCode "+httpCode);
		
		JSONObject json = new JSONObject(response);
		testCase.addTextEvidenceCurrentStep(json.toString(4));

	    addStep("Validamos que en BD la transacción sea rechazada al no cumplir el formato del campo phoneNumber");
	    await()
	    .atMost(30, TimeUnit.SECONDS)
	    .pollInterval(5, TimeUnit.SECONDS)
	    .untilAsserted(() -> 
	        assertTrue(
	            db.consultaErroresPorTransactionId(nuevoId),
	            "No se encontró la transacción en BD"
	        )
	    );
	    
	}
	
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_129_ValidarFormatoIncorrectoDelCampoPhoneNumber";
	}

	@Override
	public String setTestDescription() {
		return "Se valida formato incorrecto del campo phoneNumber (ingresar numero que no empieze con 52/que no contenga la longitud adecuada)";
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
		return "Haber recibido un request con información errónea.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
