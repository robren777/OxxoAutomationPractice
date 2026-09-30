package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelDbValidator;
import IMA.OM_API.OxxoCelParser;
import modelo.BaseExecution;

/**
 * DESCRIPCION: Validar que se registra el timestamp de completado en BD
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_007_ValidarQueSeRegistraElTimestampDeCompletadoEnBD
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 04/Marzo/2026
 */
public class ATC_FT_166_ValidarQueSeRegistraElTimestampDeCompletadoEnBD extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_166_ValidarQueSeRegistraElTimestampDeCompletadoEnBD_test(HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("I25022 OxxoCel");
	
		OxxoCelApiClient api = new OxxoCelApiClient(testCase);
		OxxoCelParser parser = new OxxoCelParser(testCase);
		OxxoCelDbValidator db = new OxxoCelDbValidator(testCase);
	
		
		// Leer Dataprovider
		String camposM 	= data.get("camposMandatorios");
		String jSon 	= data.get("jSon");
		String codeEsperado	 = data.get("Codigo");
		int code 			 = Integer.parseInt(codeEsperado);
		String urlEndpoint 	 = data.get("urlEndpoint");
		String validaHeaders = data.get("validaH");

		// Lista_de_campos
		List<String> listaJsonPaths = Arrays.asList(camposM.split("\\s*,\\s*"));
				
		// Validar Header			
		Assert.assertTrue(api.validateHeaders(validaHeaders), "Header incorrecto en el dataprovider");	
		// Actualizar json
		Assert.assertTrue(parser.updateJSON(jSon), "Error al actualizar el JSON");	
			
		// PASO 1
		addStep("Conectar a la BD Saf Transactions");
		Assert.assertTrue(db.testBDConnection(), "No se pudo conectar a la Base de Datos.");			
		
		// Validar_contra_JSON_actualizado
		addStep("Validar que el Request tenga información en los campos mandatorios.");
		Assert.assertTrue(parser.validaCamposMandatoriosRequest(OxxoCelParser.G_UpdatedJson, listaJsonPaths),"Error al validar los campos mandatorios");

		// Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
		JSONObject json = new JSONObject(OxxoCelParser.G_UpdatedJson);
		testCase.addTextEvidenceCurrentStep(json.toString(4));
		
		// Validamos_que_la_respuesta_de_la_petición_sea_el_código_esperado
		addStep("Validar el Response de la petición, se espera código " + codeEsperado );
		String response = api.validaHttpCode2(urlEndpoint, code, OxxoCelParser.G_UpdatedJson,OxxoCelParser.G_Headers);
		Assert.assertNotNull(response, "Error al validar la petición httpCode "+ codeEsperado);

		// Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
		JSONObject jsonResponse = new JSONObject(response);
		testCase.addTextEvidenceCurrentStep(jsonResponse.toString(4));		
	
		// Extraer_TransactionID_para_validar_eventos_en_tabla_auditoria
	    String idTransaction = parser.extraerIdTransaction(jsonResponse);	
	    Assert.assertNotNull(idTransaction, "No se pudo extraer el ID de la respuesta");	
		
	    // Paso 2
		addStep("Realizar la consulta en BD. Validar que se registre el Timestamp de completado en BD.");	    
	    boolean transacResult = db.consultaStatusPorIdTransaction(idTransaction, "T", "TRN");
	    Assert.assertTrue(transacResult, "No se encontró la transacción en BD");
		
	    boolean eventLogResult = db.consultaStatusPorIdTransaction(idTransaction, "A", "LOG");
	    Assert.assertTrue(eventLogResult, "No se encontró el LOG en BD");	
	    
	    Assert.assertNotNull( OxxoCelParser.G_Transact_Time, "No se registró el Timestamp de completado en BD.");
	    testCase.addBoldTextEvidenceCurrentStep("Registro exitoso de Timestamp: '" + OxxoCelParser.G_Transact_Time + "'");
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_166_ValidarQueSeRegistraElTimestampDeCompletadoEnBD";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se registra el timestamp de completado en BD.";
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
		return "1- Contar con acceso a la BD transactions\r\n"
				 + "2- Tener ambiente en QA con URL de FreedomPOP\r\n"
			     + "3- Contar con la API para enviar transacciones";
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
