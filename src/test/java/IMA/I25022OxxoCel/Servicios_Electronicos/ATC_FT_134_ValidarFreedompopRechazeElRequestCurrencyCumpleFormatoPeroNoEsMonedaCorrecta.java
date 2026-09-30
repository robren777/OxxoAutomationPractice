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
 * DESCRIPCION:Se valida freedompop rechaze el request cuando el currencyCode
 * cumple con el formato pero no es la moneda correcta.(Ejemplo: USD).
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_014_ValidarFreedompopRechazeElRequestCurrencyCumpleFormatoPeroNoEsMonedaCorrecta
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_134_ValidarFreedompopRechazeElRequestCurrencyCumpleFormatoPeroNoEsMonedaCorrecta
		extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_134_ValidarFreedompopRechazeElRequestCurrencyCumpleFormatoPeroNoEsMonedaCorrecta_test(
			HashMap<String, String> data) throws Exception {

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
	    
	  	// Validar que el servicio rechaza
		boolean esRechazado = parser.validaRechazoPorCurrencyIncorrecto(response, code);

		// Evidencia
		testCase.addTextEvidenceCurrentStep("currencyCode: " + currencyCode 
				+ " | Formato válido ≠ MXN: " + esIncorrectoValido + " | Request rechazado: " + esRechazado);

		// Assert combinado
//		Assert.assertTrue(esIncorrectoValido, "currencyCode no cumple formato válido o es MXN");

		Assert.assertTrue(esRechazado, "El servicio NO rechazó un currencyCode inválido (≠ MXN)");
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_134_ValidarFreedompopRechazeElRequestCurrencyCumpleFormatoPeroNoEsMonedaCorrecta";
	}

	@Override
	public String setTestDescription() {
		return "Se valida freedompop rechaze el request cuando el currencyCode cumple con el formato pero no es la moneda correcta.(Ejemplo: USD).";
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
