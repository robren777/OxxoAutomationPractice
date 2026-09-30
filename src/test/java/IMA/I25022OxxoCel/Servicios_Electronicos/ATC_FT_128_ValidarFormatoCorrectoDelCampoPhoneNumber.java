package IMA.I25022OxxoCel.Servicios_Electronicos;

import static org.awaitility.Awaitility.await;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelDbValidator;
import IMA.OM_API.OxxoCelParser;
import io.restassured.path.json.JsonPath;
import modelo.BaseExecution;


/**
 * DESCRIPCION: Se valida formato correcto del campo phoneNumber (regex: ^52[0-9]{10}$).
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_008_ValidarFormatoCorrectoDelCampoPhoneNumber
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_128_ValidarFormatoCorrectoDelCampoPhoneNumber extends BaseExecution {

	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_128_ValidarFormatoCorrectoDelCampoPhoneNumber_test(HashMap<String, String> data) throws Exception {
		
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

		addStep("Validar formato del campo phoneNumber");
		JsonPath jp = new JsonPath(jsonActualizado);
		String phoneNumber = jp.getString("request_payload.phoneNumber");
		String nuevoId = jp.getString("request_payload.transactionId");

		 //Validamo_exista_valor_en_el_campo_phoneNumber
	    Assert.assertNotNull(phoneNumber, "El campo phoneNumber no existe o es null");

	    //Validamos_que_cumpla_con_el_formato_regex
	    String regex = "^52[0-9]{10}$";
	    boolean cumpleFormato = phoneNumber.matches(regex);
	    Assert.assertTrue(cumpleFormato,
	    	    "Formato inválido para phoneNumber: " 	+ phoneNumber);
	    testCase.addTextEvidenceCurrentStep(
	    	    "Validación phoneNumber\n" +
	    	    "Valor: " + phoneNumber + "\n" +
	    	    "Regex: " + regex + "\n" +
	    	    "Resultado: " + (cumpleFormato ? "OK" : "FAIL")
	    	);
	    testCase.addTextEvidenceCurrentStep("phoneNumber válido: " + phoneNumber);
	
		addStep("Validar codigo de respuesta del HttpCode, código esperado "+httpCode);
		String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado,headers);
		Assert.assertNotNull(response, "Error al validar la petición httpCode "+httpCode);
		
		JSONObject json = new JSONObject(response);
		testCase.addTextEvidenceCurrentStep(json.toString(4));
		
//		System.out.println(nuevoId);
		
	    addStep("Validamos que en BD la transaccion haya llegado");
	    await()
	    .atMost(30, TimeUnit.SECONDS)
	    .pollInterval(5, TimeUnit.SECONDS)
	    .untilAsserted(() -> 
	        assertTrue(
	            db.consultaTransacciones(nuevoId),
	            "No se encontró la transacción en BD"
	        )
	    );

	}
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_128_ValidarFormatoCorrectoDelCampoPhoneNumber";
	}

	@Override
	public String setTestDescription() {
		return "Se valida formato correcto del campo phoneNumber (regex: ^52[0-9]{10}$).";
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
		return "Haber recibido un request con la información completa.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
