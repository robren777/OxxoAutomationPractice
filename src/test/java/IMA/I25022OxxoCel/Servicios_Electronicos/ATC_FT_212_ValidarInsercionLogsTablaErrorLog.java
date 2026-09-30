package IMA.I25022OxxoCel.Servicios_Electronicos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
 * MTC_FT_015-Validar inserción de logs en Tabla ErrorLog.
 *
 * PREREQUISITOS: - Contar con acceso a la BD transactions 
 *                 Tener ambiente en QA con FreedomPOP
                    Contar con la API para enviar transacciones

 * INSUMOS: - N/A
 *
 * @author Mario Garza
 * @date 04/Marzo/2026
 */
public class ATC_FT_212_ValidarInsercionLogsTablaErrorLog extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_212_ValidarInsercionLogsTablaErrorLog_test(HashMap<String, String> data) throws Exception {
		
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
		
		JsonPath jp = new JsonPath(jsonActualizado);
		String nuevoId = jp.getString("request_payload.transactionId");


		// Validar campos mandatorios
		List<String> listaJsonPaths =
		        Arrays.asList(camposM.split("\\s*,\\s*"));

		addStep("Validamos que el request tenga información en los campos mandatorios");
		Assert.assertTrue(
		        parser.validaCamposMandatoriosRequest(
		                jsonActualizado,
		                listaJsonPaths),
		        "Error al validar los campos mandatorios");

		// Evidencia Request
		JSONObject json = new JSONObject(jsonActualizado);
		testCase.addTextEvidenceCurrentStep(json.toString(4));

		// Consumir servicio
		addStep("Validamos respuesta de la petición, se espera código " + codeEsperado);

		String response = api.validaHttpCode2(
		        urlEndpoint,
		        code,
		        jsonActualizado,
		        headers);

		Assert.assertNotNull(
		        response,
		        "Error al validar la petición httpCode " + codeEsperado);

		// Evidencia Response
		JSONObject jsonR = new JSONObject(response);
		testCase.addTextEvidenceCurrentStep(jsonR.toString(4));

		// Obtener idTransaction
		String message = jsonR.getString("message");

		Pattern pattern = Pattern.compile("ID-([^\\)]+)");
		Matcher matcher = pattern.matcher(message);

		String idTransaction = null;

		if (matcher.find()) {
		    idTransaction = matcher.group(1);
		   }

		Assert.assertNotNull(idTransaction, "No se pudo extraer el ID de la respuesta");
		u.hardWait(10);
		
		addStep("Actualizar status y retry_count en OXC_SAF_TRANSACTIONS");

		boolean updateOK = db.actualizarStatusRetrySafTransaction(
		                idTransaction,
		                "P",
		                -100000);

		Assert.assertTrue(updateOK,"No fue posible actualizar OXC_SAF_TRANSACTIONS");
		
		testCase.addTextEvidenceCurrentStep(
		        "Registro actualizado correctamente\n" +
		        "ID_TRANSACTION: " + idTransaction +
		        "\nSTATUS: P" +
		        "\nRETRY_COUNT: -100000");


		addStep("Consultar información en ERROR_LOG");
		Assert.assertTrue(
		        db.consultarErrorLog(),
		        "No se encontró registro en ERROR_LOG");
		testCase.addTextEvidenceCurrentStep(
		        "Información encontrada en ERROR_LOG");

		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_212_ValidarInsercionLogsTablaErrorLog";
	}

	@Override
	public String setTestDescription() {
		return "Validar Insercion Logs Tabla Error Log";
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
