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
import IMA.OM_API.OxxoCelUtils;
import io.restassured.path.json.JsonPath;
import modelo.BaseExecution;

/**
 * DESCRIPCION: Consulta la base de datos de transacciones PENDIENTES
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_005-Validar Reenvio Exitoso validar actualizacion en campos estado e intentos.
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 04/Marzo/2026
 */
public class ATC_FT_202_ValidarReenvioExitosoActualizacionCampos extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_202_ValidarReenvioExitosoActualizacionCampos_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("I25022 OxxoCel");
		
		String estatus = data.get("estatusEsperado");
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
	    
	    addStep("Validamos que se registren en la tabla de auditoría los eventos de la petición enviada");
	    boolean resultado = db.consultaEventosAuditoriaPorIdTransaction(idTransaction);
	    Assert.assertTrue(resultado, "No se encontró la transacción en BD");
	    
	    addStep("Validamos que la petición se vaya a la tabla de Reintentos");
	    boolean validar = db.consultarReintentoPorIdTransaccion(idTransaction,estatus);
	    Assert.assertTrue(validar, "No se encontró el registro en la tabla de Reintentos");

		addStep("Realizamos reenvio de la peticion");
		String nuevoIdA= OxxoCelUtils.generarTransactionId201();
		db.realizarReintentoExitoso(idTransaction,nuevoId,nuevoIdA);
		
		addStep("Validamos que el reenvío haya sido exitoso, y se encuentre con estatus T");
		boolean exitoso = db.consultaReenvioExitoso(idTransaction);
		Assert.assertTrue(exitoso, "El reenvío no fue exitoso");
		
		addStep("Validamos que los campos retry_count, update_aty next_retry_at se hayan actualizado correctamente");
		boolean actualizacion = db.validarActualizacionCamposReintento(idTransaction);
		Assert.assertTrue(actualizacion, "No se actualizaron los campos");
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_202_ValidarReenvioExitosoActualizacionCampos";
	}

	@Override
	public String setTestDescription() {
		return "Se valide Reenvio Exitoso validar actualizacion en campos estado e intentos.";
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
		return "Contar con acceso a la BD, tener ambiente en QA con FreedomPOP y\r\n"
				+ "contar con la API para enviar transacciones.";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
