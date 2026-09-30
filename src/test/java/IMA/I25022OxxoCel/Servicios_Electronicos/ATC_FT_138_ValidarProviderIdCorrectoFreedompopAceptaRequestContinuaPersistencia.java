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
 * DESCRIPCION:Se valida provider_id correcto (freedompop) API acepta el request y continúa con persistencia.
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_018_ValidarProviderIdCorrectoFreedompopAceptaRequestContinúaPersistencia
 *
 * PREREQUISITOS: - Haber recibido un request con información completa.
 *
 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 26/Febrero/2026
 */
public class ATC_FT_138_ValidarProviderIdCorrectoFreedompopAceptaRequestContinuaPersistencia extends BaseExecution {

	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_138_ValidarProviderIdCorrectoFreedompopAceptaRequestContinuaPersistencia_test(HashMap<String, String> data) throws Exception {
		
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
	 
		addStep("Validamos ProviderId del request");
		JsonPath jp = new JsonPath(jsonActualizado);
		String provider = jp.getString("provider_id");
		String nuevoId = jp.getString("request_payload.transactionId");
		testCase.addTextEvidenceCurrentStep(
			    "Provider_id del request: " + provider);
		
		addStep("Validar codigo de respuesta del HttpCode, código esperado " + httpCode);
		String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado, headers);
		Assert.assertNotNull(response, "Error al validar la petición httpCode " + httpCode);
		JSONObject json = new JSONObject(response);
		testCase.addTextEvidenceCurrentStep(json.toString(4));

		addStep("Validar provider_id correcto (freedompop)");

		//Validación clave
		boolean providerYPersistenciaOK = parser.validaProviderCorrectoConPersistencia(
			    response,
			    provider,
			    "FREEDOMPOP"
			);

		// Evidencia
		testCase.addTextEvidenceCurrentStep(
		    "Provider_id: " + provider +
		    " | Provider correcto:  FREEDOMPOP" +
		    " | Persistencia detectada: " + providerYPersistenciaOK
		);

		// Asserts
		Assert.assertTrue(providerYPersistenciaOK,
		    "El provider_id no es freedompop");

	    addStep("Validamos que en BD la transaccion haya llegado");
	    u.hardWait(10);
	    boolean resultado = db.consultaTransacciones(nuevoId);
	    Assert.assertTrue(resultado, "No se encontró la transacción en BD");
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_138_ValidarProviderIdCorrectoFreedompopAceptaRequestContinuaPersistencia";
	}

	@Override
	public String setTestDescription() {
		return "Se valida provider_id correcto (freedompop) API acepta el request y continúa con persistencia.";
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
		return "Haber recibido un request con información completa.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
