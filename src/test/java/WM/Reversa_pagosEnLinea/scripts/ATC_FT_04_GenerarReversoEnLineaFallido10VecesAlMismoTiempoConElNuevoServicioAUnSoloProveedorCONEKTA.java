package WM.Reversa_pagosEnLinea.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.annotations.Test;

import WM.om.Reversa_EnLinea;
import WM.util.GlobalVariables;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
* DESCRIPCIÓN: Caso de prueba que valida el reverso en linea exitoso con proveedor CNKTA
* 
* *NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	MTC-FT-04-Generar reverso en linea fallido 10 veces al mismo tiempo,  con el nuevo servicio a un solo proveedor (CONEKTA) y validar su registro en la bitacora.
* 
* PREREQUISITOS:
* Contar con las credenciales de la base de datos FCTPEQA 
* Contar con el nuevo servicio en ambiente QA
* Contar con el ambiente de pruebas del proveedor
* Contar con algun folio procesado para su reversa valido
* Contar con acceso a la bitacora nueva.
* 
* INSUMOS:
* URL para realizar peticion
* Referencias de los servicios
* 
* @author Gabriel J. Rubio
* @date 21/01/2024
*/

public class ATC_FT_04_GenerarReversoEnLineaFallido10VecesAlMismoTiempoConElNuevoServicioAUnSoloProveedorCONEKTA extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_04_GenerarReversoEnLineaFallido10VecesAlMismoTiempoConElNuevoServicioAUnSoloProveedorCONEKTA_test (HashMap <String, String> data) throws Exception {
		
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
		String[] ids = idClient.split(",");
		HashMap<String, Map<String, String>> mapFolios = new HashMap<>();
	    System.out.println(data.get("Name"));
	    
	    if (baseURL.contains("8")) {
	    	dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
					GlobalVariables.DB_PASSWORD_FCTPE);
	    } else {
	    	dbFCTPE = new SQLUtil(GlobalVariables.DB_HOST_FCTPE_QRO, GlobalVariables.DB_USER_FCTPE_QRO,
					GlobalVariables.DB_PASSWORD_FCTPE_QRO);
	    	System.out.println("DB en QRO");
	    }

		for (int i=0; i<ids.length; i++) {
			/*QRY 01*/
			addStep("Se genera la peticion de tipo GET para generar el QRY01 con el idClient("+ids[i]+")");
			HashMap<String, String> requestDataQRY01 = new HashMap<>();
			requestDataQRY01.put("interface", interfac );
			requestDataQRY01.put("application", application );
			requestDataQRY01.put("entity", entity );
			requestDataQRY01.put("operation",  "QRY01");
			requestDataQRY01.put("source", source );
			requestDataQRY01.put("plaza", plaza );
			requestDataQRY01.put("tienda", tienda );
			requestDataQRY01.put("caja", caja );
			requestDataQRY01.put("adDate", adDate );
			requestDataQRY01.put("pvDate", pvDate );
			requestDataQRY01.put("idItem", idItem );
			requestDataQRY01.put("idClient", ids[i]);
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
			assertEquals(valuesQry01.get("status"), "Pendiente de pago");
			testCase.addTextEvidenceCurrentStep(responseQRY01.getBody().asPrettyString());
			testCase.addBoldTextEvidenceCurrentStep(responseQRY01.getStatusLine());

			/*TRN01 Folio generico*/
			addStep("Se generan las peticiones de tipo GET para generar el TRN01 con el idClient("+ids[i]+")");
			System.out.println("TRN01");
			HashMap<String, String> requestDataTRN01 = new HashMap<>();
			requestDataTRN01.put("interface", interfac );
			requestDataTRN01.put("application", application );
			requestDataTRN01.put("entity", entity );
			requestDataTRN01.put("operation",  "TRN01");
			requestDataTRN01.put("source", source );
			requestDataTRN01.put("plaza", plaza );
			requestDataTRN01.put("tienda", tienda );
			requestDataTRN01.put("caja", caja );
			requestDataTRN01.put("adDate", adDate );
			requestDataTRN01.put("pvDate", pvDate );
			String requestTRN01 = REL.getRequest(requestDataTRN01);
			testCase.addTextEvidenceCurrentStep(requestTRN01);

			addStep("Se Ejecutan los TRN01");
			Response responseTRN01 = REL.getApiResponseXML(baseURL, requestTRN01);
			Map<String, String> headersTRN01 = REL.getValuesTagsResponse(responseTRN01.getBody().asString(), "header");
			Map<String, String> wmCodeTRN01 = REL.getValuesTagsResponse(responseTRN01.getBody().asString(), "wmCode");
			assertTrue(wmCodeTRN01.containsValue("FOLIO PROPORCIONADO"));
			assertNotNull(headersTRN01.get("folio"));
			assertTrue(responseTRN01.getStatusLine().contains("200 OK"));
			testCase.addTextEvidenceCurrentStep(responseTRN01.getBody().asPrettyString());
			testCase.addBoldTextEvidenceCurrentStep(responseTRN01.getStatusLine());
			mapFolios.put(headersTRN01.get("folio"), valuesQry01);
			mapFolios.get(headersTRN01.get("folio")).put("creationDate", headersTRN01.get("creationDate"));
			System.out.println(headersTRN01.get("folio") + " - " + mapFolios.get(headersTRN01.get("folio")));

			/*TRN02 generacion de pago*/
			addStep("Se generan las peticiones de tipo GET para generar el TRN02 con el idClient("+ids[i]+")");
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
			requestDataTRN02.put("adDate", adDate );
			requestDataTRN02.put("pvDate", pvDate );
			requestDataTRN02.put("idItem", idItem );
			requestDataTRN02.put("operator", operator );
			requestDataTRN02.put("ticket", "1500" );
			requestDataTRN02.put("entryMode", "021" );
			requestDataTRN02.put("payType", "T" );
			requestDataTRN02.put("folio", folio );
			requestDataTRN02.put("idClient", ids[i]);
			requestDataTRN02.put("accountId", accountId );
			requestDataTRN02.put("total", total );
			String requestTRN02 = REL.getRequest(requestDataTRN02);
			testCase.addTextEvidenceCurrentStep(requestTRN02);

			addStep("Se Ejecuta TRN02");
			Response responseTRN02 = REL.getApiResponseXML(baseURL, requestTRN02);
			Map<String, String> headerTRN02 = REL.getValuesTagsResponse(responseTRN02.getBody().asString(), "header");
			Map<String, String> wmCodeTRN02 = REL.getValuesTagsResponse(responseTRN02.getBody().asString(), "wmCode");
			Map<String, String> serviceTRN02 = REL.getValuesTagsResponse(responseTRN02.getBody().asString(), "service");
			assertTrue(responseTRN02.getStatusLine().contains("200 OK"));
			assertTrue(wmCodeTRN02.containsValue("TRANSACCION EXITOSA"));
			assertTrue(serviceTRN02.containsKey("auth"));
			assertNotNull(serviceTRN02.get("auth"));
			testCase.addTextEvidenceCurrentStep(responseTRN02.getBody().asPrettyString());
			testCase.addBoldTextEvidenceCurrentStep(responseTRN02.getStatusLine());
			mapFolios.get(headerTRN02.get("folio")).put("auth", serviceTRN02.get("auth"));
			System.out.println(headerTRN02.get("folio") + " - " + mapFolios.get(headerTRN02.get("folio")));

			/*TRN 03 confirmacion de servicios*/
			addStep("Se genera peticion de tipo GET para generar el TRN03 con el idClient("+ids[i]+")");
			System.out.println("TRN03");
			HashMap<String, String> requestDataTRN03 = new HashMap<>();
			requestDataTRN03.put("interface", interfac );
			requestDataTRN03.put("application", application );
			requestDataTRN03.put("entity", entity );
			requestDataTRN03.put("operation",  "TRN03");
			requestDataTRN03.put("ack", "00" );
			requestDataTRN03.put("folio", folio);
			String requestTRN03 = REL.getRequest(requestDataTRN03);
			testCase.addTextEvidenceCurrentStep(requestTRN03);
		
			addStep("Se Ejecuta TRN03");
			Response responseTRN03 = REL.getApiResponseXML(baseURL, requestTRN03);
			Map<String, String> wmCodeTRN03 = REL.getValuesTagsResponse(responseTRN03.getBody().asString(), "wmCode");
			System.out.println(responseTRN03.getBody().asString());
			assertTrue(responseTRN03.getStatusLine().contains("200 OK"));
			assertTrue(wmCodeTRN03.containsValue("TRANSACCION COMPLETADA"));
			testCase.addTextEvidenceCurrentStep(responseTRN03.getBody().asPrettyString());
			testCase.addBoldTextEvidenceCurrentStep(responseTRN03.getStatusLine());
		}

		/*TRN 04 Reversa de servcio*/
		addStep("Se generan las peticiones de tipo GET para generar el TRN04");
		System.out.println("TRN04");
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
		String[] requestTRN04 = new String[ids.length];
		int n=0;
		for (Entry<String, Map<String, String>> folio : mapFolios.entrySet()) {
			requestDataTRN04.put("folio", folio.getKey());
			requestDataTRN04.put("auth", folio.getValue().get("auth"));
			requestDataTRN04.put("creationDate", folio.getValue().get("creationDate"));
			requestTRN04[n] = REL.getRequest(requestDataTRN04);
			testCase.addTextEvidenceCurrentStep(requestTRN04[n]);
			n++;
		}

		addStep("Se Ejecutan todas las peticiones TRN04");
		Response[] responseTRN04 = REL.getApiResponseXML(baseURL, requestTRN04);
		for (int i=0; i<ids.length; i++) {
			Map<String, String> wmCodeTRN04 = REL.getValuesTagsResponse(responseTRN04[i].getBody().asString(), "wmCode");
			System.out.println(responseTRN04[i].getBody().asString());
			System.out.println(mapFolios.get(wmCodeTRN04.get("folio")));
			System.out.println(wmCodeTRN04);
			assertTrue(responseTRN04[i].getStatusLine().contains("200 OK"));
			assertTrue(mapFolios.containsKey(wmCodeTRN04.get("folio")));
			assertEquals(mapFolios.get(wmCodeTRN04.get("folio")).get("auth"), wmCodeTRN04.get("auth"));
			assertEquals(mapFolios.get(wmCodeTRN04.get("folio")).get("creationDate"), wmCodeTRN04.get("creationDate"));
			testCase.addTextEvidenceCurrentStep(responseTRN04[i].getBody().asPrettyString());
			testCase.addBoldTextEvidenceCurrentStep(responseTRN04[i].getStatusLine());
		}
		String folios = String.join(", ", mapFolios.keySet());
		System.out.println(folios);
		Thread.sleep(60000);

		/*Query dbFCTPE MTI 0402 */
		addStep("Se valida en la BD FCTPEQA en la tabla de TPE_FR_TRANSACTION  que la transaccion se encuentre registrada con el campo MTI en 0420");
		String queryValidaTransaccion = "SELECT FOLIO, REVERSED, REV_COUNTER, MTI, OPERATION FROM tpeuser.tpe_fr_transaction"
				+ " WHERE application = 'OLS'"
				+ " AND entity = 'CNKTA'"
				+ " AND creation_date >= SYSDATE - 1"
				+ " AND folio IN (" + folios + ")"
				+ " ORDER BY folio, creation_date ASC";
		SQLResult resultValidaTransaccion = dbFCTPE.executeQuery(queryValidaTransaccion);
		System.out.println(queryValidaTransaccion);
		System.out.println(resultValidaTransaccion.getRowCount());
		System.out.println(resultValidaTransaccion.getColumnNames());
		assertEquals(resultValidaTransaccion.getRowCount(), mapFolios.size()*2);
		for (int i=0; i<resultValidaTransaccion.getRowCount(); i++) {
			System.out.println(resultValidaTransaccion.getRow(i));
			if (resultValidaTransaccion.getData(i, "MTI").equals("0200")) {
				assertEquals(resultValidaTransaccion.getData(i, "REVERSED"), "2");
				assertEquals(resultValidaTransaccion.getData(i, "REV_COUNTER"), "0");
			} else {
				assertEquals(resultValidaTransaccion.getData(i, "MTI"), "0420");
				assertEquals(resultValidaTransaccion.getData(i, "REVERSED"), "0");
				assertEquals(resultValidaTransaccion.getData(i, "REV_COUNTER"), "0");
			}
			assertEquals(resultValidaTransaccion.getData(i, "OPERATION"), "TRN03");
		}
		testCase.addQueryEvidenceCurrentStep(resultValidaTransaccion);
		
		/*Query SAFv2 */
		addStep("Validar en la BD FCTPEQA en la tabla de TPE_FR_SAF que la reversa NO se encuentre registrada en SAFv2	");
		String queryValidaSAFv2 = "SELECT FOLIO_TPE, ENTITY, OPERATION, STATUS, FLAG, COUNTER, MTI FROM tpeuser.tpe_fr_saf "
				+ "WHERE folio_tpe IN ("+ folios +")"
				+ "	AND entity = 'CNKTA'"
				+ " AND application = 'OLS'"
				+ "	ORDER BY creation_date DESC ";
		SQLResult resultValidaSAFv2 = dbFCTPE.executeQuery(queryValidaSAFv2);
		System.out.println(queryValidaSAFv2);
		System.out.println(resultValidaSAFv2.getRowCount());
		System.out.println(resultValidaSAFv2.getColumnNames());
		assertEquals(resultValidaSAFv2.getRowCount(), mapFolios.size());
		for (int i=0; i<resultValidaSAFv2.getRowCount(); i++) {
			assertEquals(resultValidaSAFv2.getData(i, "COUNTER"),"0");
			assertEquals(resultValidaSAFv2.getData(i, "OPERATION"),"TRN04");
			assertEquals(resultValidaSAFv2.getData(i, "MTI"), "0420");
		}
		testCase.addQueryEvidenceCurrentStep(resultValidaSAFv2);
				
		/*Query Bitacora */
		addStep("Realizar una con consulta filtrada en la BD FCTPEQA hacia la tabla de bitacora de registro	");
		String queryBitacora = "SELECT FOLIO_TPE, COUNTER, RESPONSE_TIME FROM TPEUSER.TPE_FR_BITACORA_REV "
				+ "WHERE folio_tpe IN ("+ folios +") "
				+ "ORDER BY CREATION_DATETIME DESC ";
		SQLResult resultBitacora = dbFCTPE.executeQuery(queryBitacora);
		System.out.println(queryBitacora);
		System.out.println(resultBitacora.getRowCount());
		System.out.println(resultBitacora.getColumnNames());
		Time timeValue = Time.valueOf("00:00:00");
		for (int i=0; i<resultBitacora.getRowCount(); i++) {
			System.out.println(resultBitacora.getRow(i));
			assertEquals(resultBitacora.getData(i, "COUNTER"), "1");
			Time qryTme = Time.valueOf(resultBitacora.getData(i, "RESPONSE_TIME"));
			timeValue = Time.valueOf(LocalTime.ofNanoOfDay(timeValue.toLocalTime().toNanoOfDay() + qryTme.toLocalTime().toNanoOfDay()));
		}
		Time tenMin = Time.valueOf("00:10:00");
		assertTrue(timeValue.before(tenMin));
		testCase.addQueryEvidenceCurrentStep(resultBitacora);
		testCase.addBoldTextEvidenceCurrentStep("Tiempo total de ejecucion en Bitacora: " + timeValue);
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
				+ "4.- Contar con algun folio procesado para su reversa valido"
				+ "5.- Contar con acceso a la bitacora nueva.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gabriel J. Rubio";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_04_GenerarReversoEnLineaFallido10VecesAlMismoTiempoConElNuevoServicioAUnSoloProveedorCONEKTA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se realizara reverso en linea con el nuevo servicio hacia el proveedor CNKTA 10 veces de forma fallida";
	}
}