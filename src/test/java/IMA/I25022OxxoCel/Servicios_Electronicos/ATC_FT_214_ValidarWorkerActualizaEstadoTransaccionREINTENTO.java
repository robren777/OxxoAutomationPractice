package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
 * DESCRIPCION: Consulta la base de datos de transacciones PENDIENTES
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_017_Validar que el worker actualiza el estado de la transacción a REINTENTO en BD
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 04/Marzo/2026
 */
public class ATC_FT_214_ValidarWorkerActualizaEstadoTransaccionREINTENTO extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_214_ValidarWorkerActualizaEstadoTransaccionREINTENTO_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
		String winiumPath = ConfigReader.get("winium.path");
		WiniumUtil u = new WiniumUtil(winiumPath);
		
		String estatus = data.get("estatusEsperado");
		String camposM = data.get("camposMandatorios");
		String jSon = data.get("jSon");
		String errorEsperado = data.get("errorEsperado");
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
	    
	    addStep("Validamos que se registren en la tabla de auditoría los eventos de la petición enviada");
	    u.hardWait(20);
	    boolean resultado = db.consultaEventosAuditoriaPorIdTransaction(idTransaction);
	    Assert.assertTrue(resultado, "No se encontró la transacción en BD");
	    
	    addStep("Validamos que se muestre el error en la tabla de event_log");
	    boolean validaError = db.validarErroresEsperadosEnBDFreedompop( idTransaction, errorEsperado);
	    Assert.assertTrue(validaError, "No se encontró el error esperado en BD");
	
	    addStep("Validamos que la petición se actualice a estado reintento");
	    boolean reintento = db.consultarReintentoPorIdTransaccion(idTransaction,estatus);
	    Assert.assertTrue(reintento, "No se encontró el registro en la tabla de Reintentos");
	
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_214_ValidarWorkerActualizaEstadoTransaccionREINTENTO";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el worker actualiza el estado de la transacción a REINTENTO en BD";
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
