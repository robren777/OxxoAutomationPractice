package WM.Reversa_pagosEnLinea.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import WM.om.Reversa_EnLinea;
import WM.util.GlobalVariables;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

//@AzureTestInfo(
//		areaPath = "Test-POCFrameworkOxxo\\test1",
//		iterationPath = "Test-POCFrameworkOxxo\\Iteration 1",
//		tags = "TestTag1;TestTag2",
//		userStoryId = 17086
//)

/**
* DESCRIPCIÓN: Se realizara la peticion del nuevo servicio enviando en el body-request con un dato de un proveedor no valido, esto debera generar una respuesta mencionando que es un proveedor que no existe en la configuracion.
* 
* *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* ATC_FT_23_GenerarReversoEnLineaDeUnProveedorEItemQueNoSeEncuentreValidoParaElProcesoEnLinea
* 
* PREREQUISITOS:
* 1. Contar con las credenciales de la base de datos FCTPEQA 
*2. Contar con el nuevo servicio en ambiente QA.
*3. Contar con el ambiente de pruebas del proveedor.
*4.- Contar con un proveedor que no este en la lista para el proceso.
*5. Contar con acceso a la bitacora nueva.

* 
* INSUMOS:
* URL para realizar peticion
* 
* @author Karime Rojas
* @date 10/03/2025
*/

public class ATC_FT_23_GenerarReversoEnLineaDeUnProveedorEItemQueNoSeEncuentreValidoParaElProcesoEnLinea extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_23_GenerarReversoEnLineaDeUnProveedorEItemQueNoSeEncuentreValidoParaElProcesoEnLinea_test (HashMap <String, String> data) throws Exception {
		
		/*Utils**************************************************************/
		SQLUtil dbFCTPE;
		Reversa_EnLinea REL = new Reversa_EnLinea();
		String baseURL = data.get("host");
		LocalDateTime now = LocalDateTime.now();
		ZoneId zoneId = ZoneId.systemDefault();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	    String currentDate = now.atZone(zoneId).format(formatter);
	    String interfac = data.get("application");
	    String application = data.get("application");
	    String entity = data.get("entity");
	    String source = data.get("source");
	    String plaza = data.get("plaza");
	    String tienda = data.get("tienda");
	    String caja = data.get("caja");
	    String adDate = currentDate.substring(0, 8);
	    String pvDate = currentDate;
	    String idItem = data.get("idItem");
	    String idClient = data.get("idClient");
	    String operator = data.get("operator");
	    
	    if (baseURL.contains("8")) {
	    	dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
					GlobalVariables.DB_PASSWORD_FCTPE);
	    } else {
	    	dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO,
					GlobalVariables.DB_PASSWORD_FCTPE_QRO);
	    }

		/*QRY 01*/
		addStep("Se genera peticion de tipo GET para generar el QRY01");
		HashMap<String, String> requestDataQRY01 = new HashMap<>();
		requestDataQRY01.put("interface", interfac );
		requestDataQRY01.put("application", application );
		requestDataQRY01.put("entity","TPLAY"  );
		requestDataQRY01.put("operation",  "QRY01");
		requestDataQRY01.put("source", source );
		requestDataQRY01.put("plaza", plaza );
		requestDataQRY01.put("tienda", tienda );
		requestDataQRY01.put("caja", caja );
		requestDataQRY01.put("adDate", adDate );
		requestDataQRY01.put("pvDate", pvDate );
		requestDataQRY01.put("idItem", idItem );
		requestDataQRY01.put("idClient", idClient );
		String requestQRY01 = REL.getRequest(requestDataQRY01);
		testCase.addTextEvidenceCurrentStep(baseURL + requestQRY01);

		addStep("Se Ejecuta QRY01");
		Response responseQRY01 = REL.getApiResponseXML(baseURL, requestQRY01);
		Map<String, String> valuesQry01 = REL.getValuesQRY01(responseQRY01.getBody().asString());
		assertTrue(responseQRY01.getStatusLine().contains("200 OK"));
		assertTrue(valuesQry01.containsKey("idClient"));
		assertTrue(valuesQry01.containsKey("accountId"));
		assertTrue(valuesQry01.containsKey("monto"));
		assertTrue(valuesQry01.containsKey("status"));
		assertEquals(valuesQry01.get("status"), "activo" );
		testCase.addTextEvidenceCurrentStep(responseQRY01.getBody().asPrettyString());
		testCase.addBoldTextEvidenceCurrentStep(responseQRY01.getStatusLine());
		

		/*TRN01*/
		addStep("Se genera peticion de tipo GET para generar el TRN01");
		System.out.println("TRN01");
		HashMap<String, String> requestDataTRN01 = new HashMap<>();
		requestDataTRN01.put("interface", interfac );
		requestDataTRN01.put("application", application );
		requestDataTRN01.put("entity", "TPLAY" );
		requestDataTRN01.put("operation",  "TRN01");
		requestDataTRN01.put("source", source );
		requestDataTRN01.put("plaza", plaza );
		requestDataTRN01.put("tienda", tienda );
		requestDataTRN01.put("caja", caja );
		requestDataTRN01.put("adDate", adDate );
		requestDataTRN01.put("pvDate", pvDate );
		String requestTRN01 = REL.getRequest(requestDataTRN01);
		testCase.addTextEvidenceCurrentStep(baseURL + requestTRN01);
		
		addStep("Se Ejecuta TRN01");
		Response responseTRN01 = REL.getApiResponseXML(baseURL, requestTRN01);
		Map<String, String> headersTRN01 = REL.getValuesTagsResponse(responseTRN01.getBody().asString(), "header");
		Map<String, String> wmCodeTRN01 = REL.getValuesTagsResponse(responseTRN01.getBody().asString(), "wmCode");
		assertTrue(wmCodeTRN01.containsValue("FOLIO PROPORCIONADO"));
		assertTrue(headersTRN01.containsKey("folio")); //que venga el folio
		assertNotNull(headersTRN01.get("folio"));
		assertTrue(responseTRN01.getStatusLine().contains("200 OK"));
		testCase.addTextEvidenceCurrentStep(responseTRN01.getBody().asPrettyString());
		testCase.addBoldTextEvidenceCurrentStep(responseTRN01.getStatusLine());

		/*TRN02*/
		addStep("Se genera peticion de tipo GET para generar el TRN02");
		System.out.println("TRN02");
		String folio = headersTRN01.get("folio");
		String accountId = valuesQry01.get("accountId");
		String total = valuesQry01.get("monto");
		HashMap<String, String> requestDataTRN02 = new HashMap<>();
		requestDataTRN02.put("interface", interfac );
		requestDataTRN02.put("application", application );
		requestDataTRN02.put("entity", entity );
		requestDataTRN02.put("operation",  "TRN02");
		requestDataTRN02.put("source", source );
		requestDataTRN02.put("folio", folio );
		requestDataTRN02.put("adDate", adDate );
		requestDataTRN02.put("pvDate", pvDate );
		requestDataTRN02.put("idItem", idItem );
		requestDataTRN02.put("idClient", idClient );
		requestDataTRN02.put("operator", operator );
		requestDataTRN02.put("accountId", accountId );
		requestDataTRN02.put("total", total );
		requestDataTRN02.put("ticket", "1500" );
		requestDataTRN02.put("entryMode", "021" );
		requestDataTRN02.put("payType", "T" );
		String requestTRN02 = REL.getRequest(requestDataTRN02);
		testCase.addTextEvidenceCurrentStep(baseURL + requestTRN02);

		addStep("Se Ejecuta TRN02");
		Response responseTRN02 = REL.getApiResponseXML(baseURL, requestTRN02);
		Map<String, String> wmCodeTRN02 = REL.getValuesTagsResponse(responseTRN02.getBody().asString(), "wmCode");
		Map<String, String> serviceTRN02 = REL.getValuesTagsResponse(responseTRN02.getBody().asString(), "service");
		assertTrue(responseTRN02.getStatusLine().contains("200 OK"));
		assertTrue(wmCodeTRN02.containsValue("TRANSACCION EXITOSA"));
		assertTrue(serviceTRN02.containsKey("auth"));
		assertNotNull(serviceTRN02.get("auth"));
		testCase.addTextEvidenceCurrentStep(responseTRN02.getBody().asPrettyString());
		testCase.addBoldTextEvidenceCurrentStep(responseTRN02.getStatusLine());

		/*TRN 03*/
		addStep("Se genera peticion de tipo GET para generar el TRN03");
		System.out.println("TRN03");
		HashMap<String, String> requestDataTRN03 = new HashMap<>();
		requestDataTRN03.put("interface", interfac );
		requestDataTRN03.put("application", application );
		requestDataTRN03.put("entity", entity );
		requestDataTRN03.put("operation",  "TRN03");
		requestDataTRN03.put("folio", folio );
		requestDataTRN03.put("ack", "00" );
		String requestTRN03 = REL.getRequest(requestDataTRN03);
		testCase.addTextEvidenceCurrentStep(baseURL + requestTRN03);
		
		addStep("Se Ejecuta TRN03");
		Response responseTRN03 = REL.getApiResponseXML(baseURL, requestTRN03);
		Map<String, String> wmCodeTRN03 = REL.getValuesTagsResponse(responseTRN03.getBody().asString(), "wmCode");
		assertTrue(responseTRN03.getStatusLine().contains("200 OK"));
		assertTrue(wmCodeTRN03.containsValue("TRANSACCION COMPLETADA"));
		testCase.addTextEvidenceCurrentStep(responseTRN03.getBody().asPrettyString());
		testCase.addBoldTextEvidenceCurrentStep(responseTRN03.getStatusLine());

		/*Query dbFCTPE */
		addStep("Se valida en la BD FCTPEQA en la tabla de TPE_FR_TRANSACTION  que la transaccion se encuentre registrada");
		String queryValidaTransaccion = "SELECT FOLIO, OPERATION, REVERSED, REV_COUNTER, MTI FROM tpeuser.tpe_fr_transaction"
				+ " WHERE application = 'OLS'"
				+ " AND entity = 'TPLAY'"
				+ " AND creation_date >= SYSDATE - 1"
				+ " AND folio = '" + folio + "'"
				+ " ORDER BY folio, creation_date ASC";
		SQLResult resultValidaTransaccion = dbFCTPE.executeQuery(queryValidaTransaccion);
		System.out.println(queryValidaTransaccion);
		System.out.println(resultValidaTransaccion.getRowCount());
		System.out.println(resultValidaTransaccion.getColumnNames());
		System.out.println(resultValidaTransaccion.getFirstRow());
		assertEquals(resultValidaTransaccion.getRowCount(), 1);
		String reversed = resultValidaTransaccion.getData(0, "REVERSED");
		String revCounter = resultValidaTransaccion.getData(0, "REV_COUNTER");
		String operation = resultValidaTransaccion.getData(0, "OPERATION");
		String mti = resultValidaTransaccion.getData(0, "MTI");
		assertEquals(mti, "0200");
		assertEquals(folio, resultValidaTransaccion.getData(0, "FOLIO"));
		assertEquals(requestDataTRN03.get("operation"), operation);
		assertEquals(reversed, "0");
		assertEquals(revCounter, "0");
		testCase.addQueryEvidenceCurrentStep(resultValidaTransaccion);

		/*TRN 04*/
		addStep("Se genera peticion de tipo GET para generar el TRN04");
		System.out.println("TRN04");
		String creationDate = headersTRN01.get("creationDate");
		String auth = serviceTRN02.getOrDefault("auth", "739095841");
		HashMap<String, String> requestDataTRN04 = new HashMap<>();
		requestDataTRN04.put("interface", interfac );
		requestDataTRN04.put("application", application );
		requestDataTRN04.put("entity", entity );
		requestDataTRN04.put("operation",  "TRN04");
		requestDataTRN04.put("source", source );
		requestDataTRN04.put("plaza", plaza );
		requestDataTRN04.put("tienda", tienda );
		requestDataTRN04.put("caja", caja );
		requestDataTRN04.put("pvDate", pvDate );
		requestDataTRN04.put("folio", folio );
		requestDataTRN04.put("auth", auth );
		requestDataTRN04.put("creationDate", creationDate );
		String requestTRN04 = REL.getRequest(requestDataTRN04);
		testCase.addTextEvidenceCurrentStep(baseURL + requestTRN04);
		addStep("Se Ejecuta TRN04");
		Response responseTRN04 = REL.getApiResponseXML(baseURL, requestTRN04);
		Map<String, String> wmCodeTRN04 = REL.getValuesTagsResponse(responseTRN04.getBody().asString(), "wmCode");
		assertTrue(responseTRN04.getStatusLine().contains("200 OK"));
		assertEquals(folio, wmCodeTRN04.get("folio"));
		assertEquals(auth, wmCodeTRN04.get("auth"));
		assertEquals(creationDate, wmCodeTRN04.get("creationDate"));
		testCase.addTextEvidenceCurrentStep(responseTRN04.getBody().asPrettyString());
		testCase.addBoldTextEvidenceCurrentStep(responseTRN04.getStatusLine());
		Thread.sleep(60000);

		/*Query dbFCTPE MTI 0420 */
		addStep("Se valida en la BD FCTPEQA en la tabla de TPE_FR_TRANSACTION  que la transaccion se encuentre registrada con el campo MTI en 0420");
		String queryValidaTransaccion2 = "SELECT FOLIO, REVERSED, REV_COUNTER, MTI, OPERATION FROM tpeuser.tpe_fr_transaction"
				+ " WHERE application = 'OLS'"
				+ " AND entity = 'TPLAY'"
				+ " AND creation_date >= SYSDATE - 1"
				+ " AND folio = '" + folio + "'"
				+ " ORDER BY FOLIO, CREATION_DATE, MTI ASC";
		SQLResult resultValidaTransaccion2 = dbFCTPE.executeQuery(queryValidaTransaccion2);
		System.out.println(queryValidaTransaccion2);
		System.out.println(resultValidaTransaccion2.getRowCount());
		System.out.println(resultValidaTransaccion2.getColumnNames());
		assertEquals(resultValidaTransaccion2.getRowCount(), 2);
		assertEquals(resultValidaTransaccion2.getData(0, "MTI"), "0200");
		assertEquals(resultValidaTransaccion2.getData(0, "OPERATION"), requestDataTRN03.get("operation"));
		assertEquals(resultValidaTransaccion2.getData(0, "REVERSED"), "2");
		assertEquals(resultValidaTransaccion2.getData(0, "REV_COUNTER"), "0");
		assertEquals(resultValidaTransaccion2.getData(1, "MTI"), "0420");
		assertEquals(resultValidaTransaccion2.getData(1, "OPERATION"), requestDataTRN03.get("operation"));
		assertEquals(resultValidaTransaccion2.getData(1, "REVERSED"), "0");
		assertEquals(resultValidaTransaccion2.getData(1, "REV_COUNTER"), "0");
		testCase.addQueryEvidenceCurrentStep(resultValidaTransaccion2);
		
		/*Query SAFv2 */
		addStep("Validar en la BD FCTPEQA en la tabla de TPE_FR_SAF que la reversa NO se encuentre registrada en SAFv2	");
		String queryValidaSAFv2 = "SELECT FOLIO_TPE, ENTITY, OPERATION, STATUS, FLAG, COUNTER, MTI FROM tpeuser.tpe_fr_saf "
				+ "WHERE folio_tpe = '"+ folio +"'"
				+ " AND entity = 'TPLAY'"
				+ " AND application = 'OLS'"
				+ "	ORDER BY creation_date DESC ";
		SQLResult resultValidaSAFv2 = dbFCTPE.executeQuery(queryValidaSAFv2);
		System.out.println(queryValidaSAFv2);
		System.out.println(resultValidaSAFv2.getRowCount());
		System.out.println(resultValidaSAFv2.getColumnNames());
		String counter = resultValidaSAFv2.getData(0, "COUNTER");
		operation = resultValidaSAFv2.getData(0, "OPERATION");
		mti = resultValidaSAFv2.getData(0, "MTI");
		assertEquals(resultValidaSAFv2.getRowCount(), 1);
		assertEquals(folio, resultValidaSAFv2.getData(0, "FOLIO_TPE"));
		assertEquals(counter, "0");
		assertEquals(operation, requestDataTRN04.get("operation"));
		assertEquals(mti, "0420");
		testCase.addQueryEvidenceCurrentStep(resultValidaSAFv2);
		
		/*Query Bitacora */
		addStep("Realizar una con consulta filtrada en la BD FCTPEQA hacia la tabla de bitacora de registro	");
		String queryBitacora = "SELECT FOLIO_TPE, COUNTER, RESPONSE_TIME FROM TPEUSER.TPE_FR_BITACORA_REV "
				+ "WHERE folio_tpe='"+ folio +"' "
				+ "ORDER BY CREATION_DATETIME DESC";
		SQLResult resultBitacora = dbFCTPE.executeQuery(queryBitacora);
		System.out.println(queryBitacora);
		System.out.println(resultBitacora.getRowCount());
		System.out.println(resultBitacora.getColumnNames());
		assertEquals(resultBitacora.getRowCount(),0);
		testCase.addQueryEvidenceCurrentStep(resultBitacora);
		

	}
	
	
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con las credenciales de la base de datos FCTPEQA \r\n"
				+ "2. Contar con el nuevo servicio en ambiente QA.\r\n"
				+ "3. Contar con el ambiente de pruebas del proveedor.\r\n"
				+ "4.- Contar con un proveedor que no este en la lista para el proceso.\r\n"
				+ "5. Contar con acceso a la bitacora nueva.\r\n"
				+ "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Karime Rojas Vargas";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_23_GenerarReversoEnLineaDeUnProveedorEItemQueNoSeEncuentreValidoParaElProcesoEnLinea";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se realizara reverso en linea con el nuevo servicio a un proveedor que no exista en la configuracion";
	}
}