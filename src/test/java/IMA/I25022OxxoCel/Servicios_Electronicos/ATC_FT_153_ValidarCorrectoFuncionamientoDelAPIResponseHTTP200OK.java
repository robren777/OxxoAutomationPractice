package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelApiClient;
import modelo.BaseExecution;


/**
 * DESCRIPCION:Se valida correcto funcionamiento del API, response HTTP 200 OK.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * ATC_FT_033_ValidarCorrectoFuncionamientoDelAPIResponseHTTP200OK
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 26/Febrero/2026
 */
public class ATC_FT_153_ValidarCorrectoFuncionamientoDelAPIResponseHTTP200OK extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_153_ValidarCorrectoFuncionamientoDelAPIResponseHTTP200OK_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");

		String codeEsperado = data.get("Codigo");
		String urlEnpoint = data.get("urlEnpoint");
		String validaHeaders = data.get("validaH");
		
		OxxoCelApiClient api = new OxxoCelApiClient(testCase);
				
		Map<String, String> headers = api.obtenerHeaders(validaHeaders);
		//Ejecutar petición GET
		addStep("Se valida respuesta de la petición GET, se espera código " + codeEsperado);
		Map<String, Object> resultado = api.ejecutarPeticionGETConTiempo(
		        urlEnpoint,
		        headers
		);

		//Evidencia
		JSONObject json = new JSONObject(resultado);
		testCase.addTextEvidenceCurrentStep(json.toString(4));

		//Obtener datos de respuesta
		String response = (String) resultado.get("response");
		long tiempoRespuesta = (long) resultado.get("tiempo");


		//Validar tiempo
		addStep("Validar el tiempo de respuesta");
		testCase.addTextEvidenceCurrentStep("Tiempo de respuesta: " + tiempoRespuesta + " ms");
	
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_153_ValidarCorrectoFuncionamientoDelAPIResponseHTTP200OK";
	}

	@Override
	public String setTestDescription() {
		return "Validar correcto funcionamiento del API, response HTTP 200 OK.";
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
