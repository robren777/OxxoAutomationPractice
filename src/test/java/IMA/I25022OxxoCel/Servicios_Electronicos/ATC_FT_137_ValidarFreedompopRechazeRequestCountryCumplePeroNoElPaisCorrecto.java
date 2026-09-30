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
 * DESCRIPCION:Se valida freedompop rechaze el request cuando el country cumple con el formato pero no es el país correcto.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_017_ValidarFreedompopRechazeRequestCountryCumplePeroNoElPaisCorrecto
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_137_ValidarFreedompopRechazeRequestCountryCumplePeroNoElPaisCorrecto extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_137_ValidarFreedompopRechazeRequestCountryCumplePeroNoElPaisCorrecto_test(HashMap<String, String> data) throws Exception {
		
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
	
		
		addStep("Validar campo country incorrecto (diferente a MX), pero con formato correcto");
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

		addStep("Validar country con formato correcto pero diferente de MX");

		// Validación de country
		boolean formatoValido = country.matches("^[A-Z]{2}$");
		boolean noEsMX = !country.equals("MX");
		boolean countryValidoPeroIncorrecto = formatoValido && noEsMX;

		// Validar rechazo
		boolean esRechazado = parser.validaRechazoServicio(response, code);

		//Validar mensaje de error
		String mensaje = json.optString("message"); // o "error"

		boolean mensajeCorrecto = mensaje.toLowerCase().contains("country")
		    && mensaje.toLowerCase().contains("not match"); 

		// Evidencia
		testCase.addTextEvidenceCurrentStep(
		    "Country: " + country +
		    " | Formato válido: " + formatoValido +
		    " | No es MX: " + noEsMX +
		    " | Country válido pero incorrecto: " + countryValidoPeroIncorrecto +
		    " | Request rechazado: " + esRechazado +
		    " | Mensaje: " + mensaje +
		    " | Mensaje correcto: " + mensajeCorrecto
		);

		// Asserts
		Assert.assertTrue(countryValidoPeroIncorrecto,
		    "El country no cumple la condición esperada");

		Assert.assertTrue(esRechazado,
		    "El servicio NO rechazó el request");

		Assert.assertTrue(mensajeCorrecto,
		    "El mensaje de error no es el esperado");

		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_137_ValidarFreedompopRechazeRequestCountryCumplePeroNoElPaisCorrecto";
	}

	@Override
	public String setTestDescription() {
		return "Se valida freedompop rechaze el request cuando el country cumple con el formato pero no es el país correcto.";
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
