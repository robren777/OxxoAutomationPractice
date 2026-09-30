package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

import IMA.OM_API.ConfigReader;
import IMA.OM_API.OxxoCelApiClient;
import IMA.OM_API.OxxoCelDbValidator;
import IMA.OM_API.OxxoCelParser;
import modelo.BaseExecution;
import utils.winium.WiniumUtil;

/**
 * 
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: -
 * MTC_FT_028_Validar que worker registra en tabla de auditoría (logbook) en BD
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Leonardo Leonel Ramírez Escobedo
 * @date 04/Marzo/2026
 */
public class ATC_FT_187_ValidarWorkerRegistraTablaAuditoriaenBD extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_187_ValidarWorkerRegistraTablaAuditoriaenBD_test(HashMap<String, String> data) throws Exception {
		
	testCase.setProject_Name("I25022 OxxoCel");
	String winiumPath = ConfigReader.get("winium.path");
	WiniumUtil u = new WiniumUtil(winiumPath);
	
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

	// validar_contra_JSON_actualizado
	addStep("Validamos que el request tenga información en los campos mandatorios");
	Assert.assertTrue(parser.validaCamposMandatoriosRequest(jsonActualizado, listaJsonPaths),"Error al validar los campos mandatorios");

//	Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
	JSONObject json = new JSONObject(jsonActualizado);
	testCase.addTextEvidenceCurrentStep(json.toString(4));
	
//	Validamos_que_la_respuesta_de_la_petición_sea_el_código_esperado
	addStep("Validamos respuesta de la petición, se espera código " + codeEsperado );
	String response = api.validaHttpCode2(urlEndpoint, code, jsonActualizado,headers);
	Assert.assertNotNull(response, "Error al validar la petición httpCode "+ codeEsperado);

	//	Convertimos_el_String_en_un_Json_y_lo_agregamos_a_la_evidencia
	JSONObject jsonResponse = new JSONObject(response);
	testCase.addTextEvidenceCurrentStep(jsonResponse.toString(4));
	
//	Extraer_TransactionID_para_validar_eventos_en_tabla_auditoria
	String message = jsonResponse.getString("message");
    Pattern pattern = Pattern.compile("ID-([^\\)]+)");
    Matcher matcher = pattern.matcher(message);
    String idTransaction = null;
    if (matcher.find()) {
        idTransaction = matcher.group(1);
    }
    Assert.assertNotNull(idTransaction, "No se pudo extraer el ID de la respuesta");	
    
    addStep("Validamos que se registren en la tabla de auditoría los eventos de la petición enviada");
    u.hardWait(10);
    boolean resultado = db.consultaEventosAuditoriaPorIdTransaction(idTransaction);
    Assert.assertTrue(resultado, "No se encontró la transacción en BD");
	
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_187_ValidarWorkerRegistraTablaAuditoriaenBD";
	}

	@Override
	public String setTestDescription() {
		return "Validar que worker registra en tabla de auditoría (logbook) en BD";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Leonardo Leonel Ramirez Escobedo";
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
