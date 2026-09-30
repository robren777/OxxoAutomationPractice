package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelDbValidator;
import IMA.OM_API.OxxoCelParser;
import io.restassured.path.json.JsonPath;
import modelo.BaseExecution;

/**
 * DESCRIPCION: Validar codigo 404 no realiza reintento
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_014_Validar codigo 409 no realiza reintento
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 04/Marzo/2026
 */
public class ATC_FT_173_ValidarCodigo409NoRealizaReintento extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_173_ValidarCodigo409NoRealizaReintento_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I25022 OxxoCel");
		
		String camposM = data.get("camposMandatorios");
		String jSon = data.get("jSon");
		String codeEsperado = data.get("Codigo");
		int code = Integer.parseInt(codeEsperado);
		String urlEndpoint = data.get("urlEndpoint");
		String validaHeaders = data.get("validaH");

		OxxoCelApiClient api = new OxxoCelApiClient(testCase);
		OxxoCelParser parser = new OxxoCelParser(testCase);
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
		
		Map<String, String> headers = api.obtenerHeaders(validaHeaders);
		String jsonActualizado = parser.jsonActualizado(jSon);
		
		// lista_de_campos
		List<String> listaJsonPaths = Arrays.asList(camposM.split("\\s*,\\s*"));
		JsonPath jp = new JsonPath(jsonActualizado);
		String nuevoId = jp.getString("request_payload.transactionId");
		// validar_contra_JSON_actualizado
		addStep("Validamos que el request tenga información en los campos mandatorios");
		Assert.assertTrue(parser.validaCamposMandatoriosRequest(jsonActualizado, listaJsonPaths),"Error al validar los campos mandatorios");

		//Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
		JSONObject json = new JSONObject(jsonActualizado);
		testCase.addTextEvidenceCurrentStep(json.toString(4));
		
		//Validamos_que_la_respuesta_de_la_petición_sea_el_código_esperado
		addStep("Validamos respuesta de la petición, se espera código " + codeEsperado );
		String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado,headers);
		Assert.assertNotNull(response, "Error al validar la petición httpCode "+ codeEsperado);
		
		//Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
		JSONObject jsonR = new JSONObject(response);
		testCase.addTextEvidenceCurrentStep(jsonR.toString(4));
		
		// Extraemos TransactionID para validar eventos en tabla auditoria
		String message = jsonR.getString("message");
		String idTransaction = parser.obtenerIdTransaction(message);
	    Assert.assertNotNull(idTransaction, "No se pudo extraer el ID de la respuesta");
	    
	    addStep("Validar codigo 409 no se reintenta");	    
	    boolean validaError = db.consultaEventosAuditoriaPorIdTransaction(idTransaction);
	    Assert.assertTrue(validaError, "No se encontró el error esperado en BD");
	
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_173_ValidarCodigo409NoRealizaReintento";
	}

	@Override
	public String setTestDescription() {
		return "Validar codigo 409 no realiza reintento";
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
		return "Contar con acceso a la BD transactions\r\n"
				+ "Tener ambiente en QA con FreedomPOP\r\n"
				+ "Contar con la API para enviar transacciones";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
