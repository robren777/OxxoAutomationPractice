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

public class TPE_MRC {
	public String runGetFolio = "TPE_MRC\\GenerarFolio.txt";
	public String runGetAut = "TPE_MRC\\GenerarAutorizacion.txt";
	public String runGetAck = "TPE_MRC\\GenerarAck.txt";

	
	String creationDate;

	HashMap<String, String> data;
	TestCase testCase;
	SQLUtil db;

	Date fecha = new Date();// obtener fecha del sistema
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); // formato de la fecha


	String date = formatter.format(fecha);

	public TPE_MRC(HashMap<String, String> data, TestCase testCase) {
		this.data = data;
		this.testCase = testCase;


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
		testCase.addBoldTextEvidenceCurrentStep("El servicio de Solicitud de Folio de la interface TPE_MRC proporciona un folio de forma exitosa:");
		String response = GetRequest.executeGetRequest(request);
		testCase.addTextEvidenceCurrentStep("\n" + response);

		return response;	
	}


public String generarAutorizacion(String Folio, String CreatioDate) throws ReempRequestException, ClientProtocolException, IOException {
	
	HashMap<String, String> datosRequest = new HashMap<>();
	
	//host
	datosRequest.put("host", data.get("host"));
	
	//Header
	datosRequest.put("application", data.get("application"));
	datosRequest.put("operation", data.get("operationAut"));
	datosRequest.put("entity", data.get("entity"));
	datosRequest.put("source", data.get("source"));
	datosRequest.put("plaza", data.get("plaza"));
	datosRequest.put("tienda", data.get("tienda"));
	datosRequest.put("caja", data.get("caja"));
	datosRequest.put("folio", Folio);
	datosRequest.put("operator", data.get("operator"));
	datosRequest.put("creationdate", CreatioDate);
	datosRequest.put("ad_date", date.substring(0, 8));
	datosRequest.put("pv_date", date);
	
	//request
	datosRequest.put("serviceId", data.get("serviceId"));	
	datosRequest.put("idClient", data.get("idClient"));
	datosRequest.put("total",data.get("totalAut"));
	datosRequest.put("branchId", data.get("branchId"));
	datosRequest.put("entryMode",data.get("entryModeAut"));
	datosRequest.put("currency",data.get("currency"));
	datosRequest.put("tiendaFront",data.get("tiendaFront"));
	datosRequest.put("ticket",data.get("ticketAut"));
	datosRequest.put("att1",data.get("att1Aut"));
	datosRequest.put("att2",data.get("att2Aut"));
	datosRequest.put("version_qr",data.get("versionQR"));
	
	String request = GetRequestFile.getRequestFile(runGetAut, datosRequest);
	System.out.println(request);
	testCase.addTextEvidenceCurrentStep("\n" + request);
	testCase.addBoldTextEvidenceCurrentStep("Resultado Actual:");
	testCase.addBoldTextEvidenceCurrentStep("Se muestra la respuesta exitosa de la solicitud de autorización de la interface TPE_MRC (Retiro de efectivo con QR OXXO ): ");
	String response = GetRequest.executeGetRequest(request);
	testCase.addTextEvidenceCurrentStep("\n" + response);

	return response;
		
}

public String generacionACK(String Folio, String CreatioDate) throws ReempRequestException, ClientProtocolException, IOException {

	HashMap<String, String> datosRequest = new HashMap<>();
	//host
	datosRequest.put("host", data.get("host"));
	
	//headers
	datosRequest.put("application", data.get("application"));
	datosRequest.put("entity", data.get("entity"));
	datosRequest.put("operationAck", data.get("operationAck"));
	datosRequest.put("source", data.get("source"));
	datosRequest.put("plaza", data.get("plaza"));
	datosRequest.put("tienda", data.get("tienda"));
	datosRequest.put("caja", data.get("caja"));
	datosRequest.put("folio", Folio);
	datosRequest.put("pv_date", date);
	datosRequest.put("ad_date", date.substring(0, 8));
	datosRequest.put("creationdate", CreatioDate);
	
	//request
	datosRequest.put("ack_value", data.get("ackValue"));
	

	
	String request = GetRequestFile.getRequestFile(runGetAck, datosRequest);
	System.out.println(request);
	testCase.addTextEvidenceCurrentStep("\n" + request);
	testCase.addBoldTextEvidenceCurrentStep("Resultado Actual:");
	testCase.addBoldTextEvidenceCurrentStep("Se muestra la respuesta exitosa de la solicitud de confirmación de ACK de la interface TPE_MRC para el cierre de transacción de pago de una factura telcel: ");
	String response = GetRequest.executeGetRequest(request);
	testCase.addTextEvidenceCurrentStep("\n" + response);

	return response;
		
}

	
}