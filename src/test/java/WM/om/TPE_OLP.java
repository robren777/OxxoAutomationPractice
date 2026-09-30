package WM.om;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;

import WM.util.GetRequestFile;
import exceptions.ReempRequestException;
import modelo.TestCase;
import utils.sql.SQLUtil;
import utils.webmethods.GetRequest;

public class TPE_OLP {
	public String runGetFolio = "TPE_OLP\\GenerarFolio.txt";
	public String runGetAut = "TPE_OLP\\GenerarAutorizacion.txt";
	public String runGetAck = "TPE_OLP\\GenerarAck.txt";
	public String runGetRev = "TPE_OLP\\SolicitudReversa.txt";
	public String runGetAutRep = "FCP\\GeneraAutorizacionRep.txt";
	public String runConsultaCliente = "TPE_OLP\\ConsultaCliente.txt";
	public String runRedencionCupon = "FCP\\RedencionCupon.txt";
  public String ejecutarTRN02_WU = "TPE_TSF\\AuthDispersionWU.txt";
	public String ejecutarTRN02_MG = "TPE_TSF\\AuthDispersionMG.TXT";
	
	String creationDate, accountID;

	HashMap<String, String> data;
	TestCase testCase;
	SQLUtil db;

	Date fecha = new Date();// obtener fecha del sistema
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); // formato
																			// de
																			// la
																			// fecha

	String date = formatter.format(fecha);

	public TPE_OLP(HashMap<String, String> data, TestCase testCase, SQLUtil db) {
		this.data = data;
		this.testCase = testCase;
		this.db = db;

	}

public String ConsultaCliente() throws ReempRequestException, ClientProtocolException, IOException {
	
	HashMap<String, String> datosRequest = new HashMap<>();

	
	datosRequest.put("host", data.get("host"));
	datosRequest.put("application", data.get("application"));
	datosRequest.put("entity", data.get("entity"));
	datosRequest.put("operation", data.get("operationCon"));
	datosRequest.put("source", data.get("source"));
	datosRequest.put("plaza", data.get("plaza"));
	datosRequest.put("tienda", data.get("tienda"));
	datosRequest.put("caja", data.get("caja"));
	datosRequest.put("ad_date", date.substring(0, 8));
	datosRequest.put("pv_date", date);
	datosRequest.put("serviceId", data.get("serviceId"));
	datosRequest.put("idClient", data.get("idClient"));
	

	String request = GetRequestFile.getRequestFile(runConsultaCliente, datosRequest);
	System.out.println(request);
	testCase.addTextEvidenceCurrentStep("\n" + request);
	testCase.addBoldTextEvidenceCurrentStep("Resultado Actual:");
	testCase.addBoldTextEvidenceCurrentStep("El servicio de solicitud de consulta de cliente de factura TELCEL de la interface TPE_OLP responde con la información del cliente:");
	String response = GetRequest.executeGetRequest(request);
	testCase.addTextEvidenceCurrentStep("\n" + response);

	return response;	
}


public String generacionFolio() throws ReempRequestException, ClientProtocolException, IOException {
		
		HashMap<String, String> datosRequest = new HashMap<>();

		
		datosRequest.put("host", data.get("host"));
		datosRequest.put("application", data.get("application"));
		datosRequest.put("entity", data.get("entity"));
		datosRequest.put("operation", data.get("operationFol"));
		datosRequest.put("source", data.get("source"));
		datosRequest.put("plaza", data.get("plaza"));
		datosRequest.put("tienda", data.get("tienda"));
		datosRequest.put("caja", data.get("caja"));
		datosRequest.put("ad_date", date.substring(0, 8));
		datosRequest.put("pv_date", date);
		
		
		String request = GetRequestFile.getRequestFile(runGetFolio, datosRequest);
		System.out.println(request);
		testCase.addTextEvidenceCurrentStep("\n" + request);
		testCase.addBoldTextEvidenceCurrentStep("Resultado Actual:");
		testCase.addBoldTextEvidenceCurrentStep("El servicio de solicitud de folio de la interface TPE_OLP proporciona un folio de forma exitosa:");
		String response = GetRequest.executeGetRequest(request);
		testCase.addTextEvidenceCurrentStep("\n" + response);

		return response;	
	}


public String generarAutorizacion(String Folio, String CreatioDate, String idClient, String accountId) throws ReempRequestException, ClientProtocolException, IOException {
	
	HashMap<String, String> datosRequest = new HashMap<>();
	
	datosRequest.put("host", data.get("host"));
	datosRequest.put("application", data.get("application"));
	datosRequest.put("entity", data.get("entity"));
	datosRequest.put("operation", data.get("operationAut"));
	datosRequest.put("source", data.get("source"));
	datosRequest.put("folio", Folio);
	datosRequest.put("plaza", data.get("plaza"));
	datosRequest.put("tienda", data.get("tienda"));
	datosRequest.put("caja", data.get("caja"));
	datosRequest.put("ad_date", date.substring(0, 8));
	datosRequest.put("pv_date", date);
	datosRequest.put("creationdate", CreatioDate);
	datosRequest.put("serviceId", data.get("serviceId"));	
	datosRequest.put("idclient", idClient);
	datosRequest.put("accountid", accountId);
	datosRequest.put("ticket",data.get("ticketAut"));
	datosRequest.put("total",data.get("totalAut"));
	datosRequest.put("entryMode",data.get("entryModeAut"));
	datosRequest.put("payType",data.get("payTypeAut"));
	datosRequest.put("payCom",data.get("payComAut"));
	
	String request = GetRequestFile.getRequestFile(runGetAut, datosRequest);
	System.out.println(request);
	testCase.addTextEvidenceCurrentStep("\n" + request);
	testCase.addBoldTextEvidenceCurrentStep("Resultado Actual:");
	testCase.addBoldTextEvidenceCurrentStep("Se muestra la respuesta exitosa de la solicitud de autorización de la interface OLP para el pago de una factura TELCEL: ");
	String response = GetRequest.executeGetRequest(request);
	testCase.addTextEvidenceCurrentStep("\n" + response);

	return response;
		
}

public String generacionACK(String Folio, String idClient, String accountId) throws ReempRequestException, ClientProtocolException, IOException {

	HashMap<String, String> datosRequest = new HashMap<>();
	
	datosRequest.put("host", data.get("host"));
	datosRequest.put("application", data.get("application"));
	datosRequest.put("entity", data.get("entity"));
	datosRequest.put("operation", data.get("operationAck"));
	datosRequest.put("source", data.get("source"));
	datosRequest.put("folio", Folio);
	datosRequest.put("plaza", data.get("plaza"));
	datosRequest.put("tienda", data.get("tienda"));
	datosRequest.put("retekNo", data.get("retekNoAck"));
	datosRequest.put("affiliation", data.get("affiliationAck"));
	datosRequest.put("caja", data.get("caja"));
	datosRequest.put("adDate", date.substring(0, 8));
	datosRequest.put("pvDate", date);
	datosRequest.put("serviceId", data.get("serviceId"));
	datosRequest.put("pvTicket", data.get("pvTicketAck"));
	datosRequest.put("operator", data.get("operatorAck"));
	datosRequest.put("pvcvemvt", data.get("pvcvemvtAck"));
	datosRequest.put("fechatransaccion", data.get("fechatransaccionAck"));
	datosRequest.put("horatransaccion", data.get("horatransaccionAck"));
	datosRequest.put("ref1", idClient);
	datosRequest.put("ref2", accountId);
//	datosRequest.put("ref3", data.get("ref3Ack"));
//	datosRequest.put("ref4", data.get("ref4Ack"));
	datosRequest.put("valor", data.get("valorAck"));
	datosRequest.put("corte", data.get("corteAck"));
	datosRequest.put("foliotransaccion", data.get("foliotransaccionAck"));
	datosRequest.put("comision", data.get("comisionAck"));
	datosRequest.put("idservicio", data.get("id_servicioAck"));
	datosRequest.put("folioticket", data.get("folioticketAck"));
//	datosRequest.put("cancelado", data.get("canceladoAck"));
	datosRequest.put("noauto", data.get("noautoAck"));
	datosRequest.put("consecutivo", data.get("consecutivoAck"));
	datosRequest.put("doctype", data.get("doctypeAck"));
	datosRequest.put("valor1", data.get("valor1Ack"));
	datosRequest.put("valor2", data.get("valor2Ack"));
	datosRequest.put("att1", data.get("att1Ack"));
//	datosRequest.put("att2", data.get("att2Ack"));
//	datosRequest.put("att3", data.get("att3Ack"));
//	datosRequest.put("att4", data.get("att4Ack"));
//	datosRequest.put("att5", data.get("att5Ack"));
//  datosRequest.put("att6", data.get("att6Ack"));
	datosRequest.put("att7", idClient);
	datosRequest.put("att8", accountId);
	datosRequest.put("att9", data.get("att9Ack"));
	datosRequest.put("ackval", data.get("ackvalue"));
	
	String request = GetRequestFile.getRequestFile(runGetAck, datosRequest);
	System.out.println(request);
	testCase.addTextEvidenceCurrentStep("\n" + request);
	testCase.addBoldTextEvidenceCurrentStep("Resultado Actual:");
	testCase.addBoldTextEvidenceCurrentStep("Se muestra la respuesta exitosa de la solicitud de confirmación de ACK de la interface OLP para el cierre de transacción de pago de una factura telcel: ");
	String response = GetRequest.executeGetRequest(request);
	testCase.addTextEvidenceCurrentStep("\n" + response);

	return response;
		
}

public String solicitudReversa(String folio, String CreatioDate) throws ReempRequestException, ClientProtocolException, IOException {

	HashMap<String, String> datosRequest = new HashMap<>();
	
	datosRequest.put("host", data.get("host"));
	datosRequest.put("application", data.get("application"));
	datosRequest.put("entity", data.get("entity"));
	datosRequest.put("operation_rev", data.get("operationRev"));
	datosRequest.put("source", data.get("source"));
	datosRequest.put("folio", folio);
	datosRequest.put("plaza", data.get("plaza"));
	datosRequest.put("tienda", data.get("tienda"));
	datosRequest.put("caja", data.get("caja"));
	datosRequest.put("ad_date", date.substring(0, 8));
	datosRequest.put("pv_date", date);
	datosRequest.put("creationdate", CreatioDate);
	
	String request = GetRequestFile.getRequestFile(runGetRev, datosRequest);
	System.out.println(request);
	testCase.addTextEvidenceCurrentStep("\n" + request);
	testCase.addBoldTextEvidenceCurrentStep("Resultado Actual:");
	testCase.addBoldTextEvidenceCurrentStep("Se muestra la respuesta exitosa de la solicitud de reversa por caso critico de la interface OLP para realizar la reversa de Pago a la factura TELCEL de un cliente: ");
	String response = GetRequest.executeGetRequest(request);
	testCase.addTextEvidenceCurrentStep("\n" + response);

	return response;
		
}
}