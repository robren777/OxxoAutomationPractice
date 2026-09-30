package WM.om;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;

import javax.xml.bind.JAXBException;

import org.apache.http.client.ClientProtocolException;

import WM.util.GetRequestFile;
import exceptions.ReempRequestException;
import modelo.TestCase;
import utils.sql.SQLUtil;
import utils.webmethods.GetRequest;

public class PE4v2 {

	HashMap<String, String> data;
	TestCase testCase;
	SQLUtil db;
	public String runGetCFDi = "PE4v2\\runGetCFDi.txt";
	public String runGetCFIDv3_1 = "PE4V2\\RunGetCFIDv3.1.txt";
	public String runGetCFIDv3_2 = "PE4V2\\RunGetCFIDv3.2.txt";

	//Obtener fecha y hora en curso
	    Date fecha = new Date();	    
	    SimpleDateFormat formato = new SimpleDateFormat("yyyyMMddHHmmss");
	    
	    //
		LocalDateTime now = LocalDateTime.now();
		ZoneId zoneId = ZoneId.systemDefault();
				//systemDefault().normalized();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	    String currentDate = now.atZone(zoneId).format(formatter);
	    

	    
	public PE4v2(HashMap<String, String> data, TestCase testCase, SQLUtil db) {
		this.data = data;
		this.testCase = testCase;
		this.db = db;
	}
	
public String executeRunQueryTdc() throws ReempRequestException, ClientProtocolException, IOException {
		
		String requestUrl = "http://%s/invoke/PE4v2.Pub/runQueryTdc?folio=%s&adminDate=%s&tarjeta=%s";
		
		String url = String.format(requestUrl, 
				data.get("host"),
				data.get("folio"),
				data.get("adminDate"),
				data.get("tarjeta"));
		
		System.out.println(url);
		testCase.addTextEvidenceCurrentStep(url);
		
		String responseRunQueryTdc = GetRequest.executeGetRequest(url);
		System.out.println(responseRunQueryTdc);
		testCase.addTextEvidenceCurrentStep(responseRunQueryTdc);
		
		return responseRunQueryTdc;
	}
	
public String executeRunQueryYaFacturado() throws ReempRequestException, ClientProtocolException, IOException {
	
	String requestUrl = "http://10.80.20.103:8889/invoke/PE4v2.Pub/runGetCFDi?xmlIn= version=\"1.0\"?>" 
	+ " <TPEFacturaInput version=\"3.0\">"
	+ "<OxxoData plaza=\"10MON\" tienda=\"50GIT\" caja=\"1\" adminDate=\"20210427\" noTicket=\"6305293\" notaCredito=\"N\" reattempt=\"Y\" movDate=\"202104271504\" source=\"POS\"/>"
	+ "<Comprobante formaDePago=\"01\" subTotal=\"1.85\" total=\"2.00\" metodoDePago=\"PUE\" lugarExpedicion=\"64000\" tipoDeComprobante=\"I\" moneda=\"MXN\">"
	+ "<Receptor rfc=\"GET130704R74\" nombre=\"GETIC S.A  DE C.V\" usoCFDI=\"P01\"><Domicilio calle=\"PUERTA DEL.  SOL 1227 COLINAS DE SAN JERONIMO\" municipio=\"MONTERREY NUEVO  LEON\" pais=\"MEXICO\" codigoPostal=\"64630\"/><"
	+ "/Receptor>"
	+ "<Conceptos>"
	+ "<Concepto claveProdServ=\"50161813\" cantidad=\"1.000\" claveUnidad=\"EA\" descripcion=\"CHOC A GRANEL KISSES     \" valorUnitario=\"1.85\" importe=\"1.85\">"
	+ "<Impuestos>"
	+ "<Traslados><Traslado base=\"2.00\" impuesto=\"002\" tipoFactor=\"Tasa\" tasaOCuota=\"0.000000\" importe=\"0.00\"/><Traslado base=\"1.85\" impuesto=\"003\" tipoFactor=\"Tasa\" tasaOCuota=\"0.080000\" importe=\"0.15\"/>"
	+ "</Traslados>"
	+ "</Impuestos>"
	+ "</Concepto>"
	+ "</Conceptos>"
	+ "<Impuestos totalImpuestosTrasladados=\"0.15\"><Traslados><Traslado impuesto=\"002\" tasaOCuota=\"0.000000\" importe=\"0.00\" tipoFactor=\"Tasa\"/><Traslado impuesto=\"003\" tasaOCuota=\"0.080000\" importe=\"0.15\" tipoFactor=\"Tasa\"/>"
	+ "</Traslados>"
	+ "</Impuestos>"
	+ "<DatosAdicionales descripcionRegimen=\"Opcional para Grupos de Sociedades\" descripcionTipoComprobante=\"Ingreso\" descripcionUsoCFDI=\"Por definir\" descripcionMetodoPago=\"Pago en una sola exhibición\"/>"
	+ "</Comprobante>"
	+ "</TPEFacturaInput>";
	

	
	String responseRunQueryTdc = GetRequest.executeGetRequest(requestUrl);
	System.out.println(responseRunQueryTdc);
	testCase.addTextEvidenceCurrentStep(requestUrl);
	
	return responseRunQueryTdc;
}


public String executeRunQueryFecha() throws ReempRequestException, ClientProtocolException, IOException {
	
	String requestUrl = "http://10.80.20.103:8889/invoke/PE4v2.Pub/runGetCFDi?xmlIn= version=\"1.0\"?>" 
	+ " <TPEFacturaInput version=\"3.0\"><OxxoData plaza=\"10MON\" tienda=\"50GIT\" caja=\"1\" adminDate=\"20210606\" "
	+ "noTicket=\"6305226\" notaCredito=\"S\" reattempt=\"Y\" movDate=\"202105041005\" source=\"POS\"/><Comprobante formaDePago=\"01\" "
	+ "subTotal=\"1.85\" total=\"2.00\" metodoDePago=\"PUE\" lugarExpedicion=\"64000\" tipoDeComprobante=\"I\" moneda=\"MXN\">"
	+ "<Receptor rfc=\"GET130704R74\" nombre=\"GETIC S.A  DE C.V\" usoCFDI=\"P01\">"
	+ "<Domicilio calle=\"PUERTA DEL.  SOL 1227 COLINAS DE SAN JERONIMO\" "
	+ "municipio=\"MONTERREY NUEVO  LEON\" pais=\"MEXICO\" codigoPostal=\"64630\"/></Receptor><Conceptos><Concepto claveProdServ=\"50161813\" "
	+ "cantidad=\"1.000\" claveUnidad=\"EA\" descripcion=\"CHOC A GRANEL KISSES     \" valorUnitario=\"1.85\" importe=\"1.85\">"
	+ "<Impuestos>"
	+ "<Traslados><Traslado base=\"2.00\" impuesto=\"002\" tipoFactor=\"Tasa\" tasaOCuota=\"0.000000\" importe=\"0.00\"/>"
	+ "<Traslado base=\"1.85\" impuesto=\"003\" tipoFactor=\"Tasa\" tasaOCuota=\"0.080000\" importe=\"0.15\"/>"
	+ "</Traslados></Impuestos></Concepto></Conceptos><Impuestos totalImpuestosTrasladados=\"0.15\">"
	+ "<Traslados>"
	+ "<Traslado impuesto=\"002\" tasaOCuota=\"0.000000\" importe=\"0.00\" tipoFactor=\"Tasa\"/>"
	+ "<Traslado impuesto=\"003\" tasaOCuota=\"0.080000\" importe=\"0.15\" tipoFactor=\"Tasa\"/>"
	+ "</Traslados>"
	+ "</Impuestos>"
	+ "<DatosAdicionales descripcionRegimen=\"Opcional para Grupos de Sociedades\" descripcionTipoComprobante=\"Ingreso\" descripcionUsoCFDI=\"Por definir\" descripcionMetodoPago=\"Pago en una sola exhibición\"/>"
	+ "</Comprobante></TPEFacturaInput>";
	

	
	String responseRunQueryTdc = GetRequest.executeGetRequest(requestUrl);
	System.out.println(responseRunQueryTdc);
	testCase.addTextEvidenceCurrentStep(requestUrl);
	
	return responseRunQueryTdc;
}

public String executeRunGetCFDi() throws ReempRequestException, ClientProtocolException, IOException {
		
	HashMap<String, String> requestData = new HashMap<>();
	requestData.put("host", data.get("host"));
	requestData.put("plaza", data.get("plaza"));
	requestData.put("tienda", data.get("tienda"));
	requestData.put("caja", data.get("caja"));
	requestData.put("adminDate",currentDate.substring(0, 8));
	requestData.put("noTicket", data.get("noTicket"));
	requestData.put("notaCredito", data.get("notaCredito"));
	requestData.put("reattempt", data.get("reattempt"));
	requestData.put("movDate", currentDate);
	requestData.put("source", data.get("source"));
	requestData.put("formaDePago", data.get("formaDePago"));
	requestData.put("subTotal", data.get("subTotal"));
	requestData.put("metodoDePago", data.get("metodoDePago"));
	requestData.put("lugarExpedicion", data.get("lugarExpedicion"));
	requestData.put("tipoDeComprobante", data.get("tipoDeComprobante"));
	requestData.put("moneda", data.get("moneda"));
	requestData.put("rfc", data.get("total"));
	requestData.put("nombre", data.get("nombre"));
	requestData.put("usoCFDI", data.get("usoCFDI"));
	requestData.put("calle", data.get("calle"));
	requestData.put("municipio", data.get("municipio"));
	requestData.put("pais", data.get("pais"));
	requestData.put("codigoPostal", data.get("codigoPostal"));
	requestData.put("claveProdServ", data.get("claveProdServ"));
	requestData.put("cantidad", data.get("cantidad"));
	requestData.put("claveUnidad", data.get("claveUnidad"));	
	requestData.put("descripcion", data.get("descripcion"));
	requestData.put("valorUnitario", data.get("valorUnitario")); 
	requestData.put("importe", data.get("importeConcepto"));	
    requestData.put("base", data.get("base1"));
	requestData.put("impuesto", data.get("impuesto1"));
	requestData.put("tipoFactor", data.get("tipoFactor1"));
	requestData.put("tasaOCuota", data.get("tasaOCuota1"));
	requestData.put("importe", data.get("importeTraslado1"));
	requestData.put("base", data.get("base2"));
	requestData.put("impuesto", data.get("impuesto2"));
	requestData.put("tipoFactor", data.get("tipoFactor2"));
	requestData.put("tasaOCuota", data.get("tasaOCuota2"));
	requestData.put("importe", data.get("importeTraslado2"));
	requestData.put("impuesto", data.get("impuesto11"));
	requestData.put("tipoFactor", data.get("tipoFactor11"));
	requestData.put("tasaOCuota", data.get("tasaOCuota11"));
	requestData.put("importe", data.get("importeTraslado11"));
	requestData.put("impuesto", data.get("impuesto22"));
	requestData.put("tipoFactor", data.get("tipoFactor22"));
	requestData.put("tasaOCuota", data.get("tasaOCuota22"));
	requestData.put("importe", data.get("importeTraslado22"));
	requestData.put("descripcionRegimen", data.get("descripcionRegimen"));
	requestData.put("descripcionTipoComprobante", data.get("descripcionTipoComprobante"));
	requestData.put("descripcionUsoCFDI", data.get("descripcionUsoCFDI"));
	requestData.put("descripcionMetodoPago", data.get("descripcionMetodoPago"));
	
	//Sustituye los datos en el XML
			String requestXML = GetRequestFile.getRequestFile(runGetCFDi, requestData);
			System.out.println("Request runGetCFDi: " + requestXML);
			//Agregar Request a la evidencia
			testCase.addBoldTextEvidenceCurrentStep("Request runGetCFDi: ");
			testCase.addTextEvidenceCurrentStep(requestXML);
			//Se realiza request
			String response = GetRequest.executeGetRequest(requestXML);
			System.out.println("Response runGetCFDi: " + response);
			testCase.addBoldTextEvidenceCurrentStep("Response runGetCFDi:");
			testCase.addTextEvidenceCurrentStep(response);
			
			return response;
}

	public String executeRunQueryTicket()throws ReempRequestException, ClientProtocolException, IOException {
		
		String requestUrl = "http://%s/invoke/PE4v2.Pub/runQueryTicket?plaza=%s&tienda=%s&caja=%s&adminDate=%s&noTicket=%s&source=%s";
		
		String url = String.format(requestUrl, 
				data.get("host"),
				data.get("plaza"),
				data.get("tienda"),
				data.get("caja"),
				data.get("adminDate"),
				data.get("noTicket"),
				data.get("source"));
		
		System.out.println(url);
		testCase.addTextEvidenceCurrentStep(url);
		
		String responseRunQueryTdc = GetRequest.executeGetRequest(url);
		System.out.println(responseRunQueryTdc);
		testCase.addTextEvidenceCurrentStep(responseRunQueryTdc);
		
		return responseRunQueryTdc;
	}
	
	public String executeRunGetCFDI() throws ReempRequestException, ClientProtocolException, IOException, JAXBException {
		
		//String date = formato.format(fecha);
		HashMap<String, String> datosRequest = new HashMap<>();
		
		datosRequest.put("host", data.get("host"));
		datosRequest.put("plaza", data.get("plaza"));
		datosRequest.put("tienda", data.get("tienda"));
		datosRequest.put("caja", data.get("caja"));
		//datosRequest.put("adminDate",  data.get("adminDate"));//currentDate.substring(0, 8));
		datosRequest.put("adminDate", currentDate.substring(0, 8));
		datosRequest.put("noTicket", data.get("noTicket"));
		datosRequest.put("notaCredito", data.get("notaCredito"));
		datosRequest.put("reattempt", data.get("reattempt"));
		//datosRequest.put("movDate",  data.get("movDate"));//currentDate);
		datosRequest.put("movDate",  currentDate);
		datosRequest.put("source", data.get("source"));
		
		datosRequest.put("formaDePago", data.get("formaDePago"));
		datosRequest.put("subTotal", data.get("subTotal"));
		datosRequest.put("total", data.get("total"));
		datosRequest.put("metodoDePago", data.get("metodoDePago"));
		datosRequest.put("lugarExpedicion", data.get("lugarExpedicion"));
		datosRequest.put("tipoDeComprobante", data.get("tipoDeComprobante"));
		datosRequest.put("moneda", data.get("moneda"));
		datosRequest.put("exportacion", data.get("exportacion"));
		
		datosRequest.put("rfcEmisor", data.get("rfcEmisor"));
		datosRequest.put("nombreEmisor", data.get("nombreEmisor"));
		datosRequest.put("regimenEmisor", data.get("regimenEmisor"));
		
		datosRequest.put("rfcReceptor", data.get("rfcReceptor"));
		//datosRequest.put("nombreReceptor", data.get("nombreReceptor"));
		datosRequest.put("usoCfdiReceptor", data.get("usoCfdiReceptor"));
		//datosRequest.put("domicilioFiscalReceptor", data.get("domicilioFiscalReceptor"));
		datosRequest.put("regimenFiscalReceptor", data.get("regimenFiscalReceptor"));
		datosRequest.put("calle", data.get("calle"));
		datosRequest.put("noExterior", data.get("noExterior"));
		datosRequest.put("colonia", data.get("colonia"));
		datosRequest.put("municipio", data.get("municipio"));
		datosRequest.put("estado", data.get("estado"));
		datosRequest.put("pais", data.get("pais"));
		datosRequest.put("codigoPostal", data.get("codigoPostal"));
		
		datosRequest.put("claveProdServ", data.get("claveProdServ"));
		datosRequest.put("cantidad", data.get("cantidad"));
		datosRequest.put("claveUnidad", data.get("claveUnidad"));
		datosRequest.put("descripcion", data.get("descripcion"));
		datosRequest.put("valorUnitario", data.get("valorUnitario"));
		datosRequest.put("importeConcepto", data.get("importeConcepto"));
		datosRequest.put("objetoImp", data.get("objetoImp"));

		datosRequest.put("base1", data.get("base1"));
		datosRequest.put("impuesto1", data.get("impuesto1"));
		datosRequest.put("tipoFactor1", data.get("tipoFactor1"));
		datosRequest.put("tasaOCuota1", data.get("tasaOCuota1"));
		datosRequest.put("importeTraslado1", data.get("importeTraslado1"));
				
		datosRequest.put("base2", data.get("base2"));
		datosRequest.put("impuesto2", data.get("impuesto2"));
		datosRequest.put("tipoFactor2", data.get("tipoFactor2"));
		datosRequest.put("tasaOCuota2", data.get("tasaOCuota2"));
		datosRequest.put("importeTraslado2", data.get("importeTraslado2"));
		
		datosRequest.put("totalImpuestosTrasladados", data.get("totalImpuestosTrasladados"));
		
		datosRequest.put("base3", data.get("base3"));
		datosRequest.put("impuesto3", data.get("impuesto3"));
		datosRequest.put("tipoFactor3", data.get("tipoFactor3"));
		datosRequest.put("tasaOCuota3", data.get("tasaOCuota3"));
		datosRequest.put("importeTraslado3", data.get("importeTraslado3"));
				
		datosRequest.put("base4", data.get("base4"));
		datosRequest.put("impuesto4", data.get("impuesto4"));
		datosRequest.put("tipoFactor4", data.get("tipoFactor4"));
		datosRequest.put("tasaOCuota4", data.get("tasaOCuota4"));
		datosRequest.put("importeTraslado4", data.get("importeTraslado4"));
		
		datosRequest.put("totalDeRetenciones", data.get("totalDeRetenciones"));
		datosRequest.put("totalDeTraslados", data.get("totalDeTraslados"));
		
		datosRequest.put("tasaDeRetencion", data.get("tasaDeRetencion"));
		datosRequest.put("impLocRetenido", data.get("impLocRetenido"));
		datosRequest.put("importeRetencionesLocales", data.get("importeRetencionesLocales"));
		
		datosRequest.put("descripcionRegimen", data.get("descripcionRegimen"));
		datosRequest.put("descripcionTipoComprobante", data.get("descripcionTipoComprobante"));
		datosRequest.put("descripcionUsoCFDI", data.get("descripcionUsoCFDI"));
		datosRequest.put("descripcionMetodoPago", data.get("descripcionMetodoPago"));
		
		String request = GetRequestFile.getRequestFile(runGetCFDi, datosRequest);
		System.out.println(request);
		testCase.addTextEvidenceCurrentStep("\n"+request);
		
		String response = GetRequest.executeGetRequest(request);		
		System.out.println(response);
		
		return response;
	}
	
public String executeRunGetCFDIv3_1() throws ReempRequestException, ClientProtocolException, IOException, JAXBException {
		
		
		HashMap<String, String> datosRequest = new HashMap<>();
		
		datosRequest.put("host", data.get("host"));
//		datosRequest.put("plaza", data.get("plaza"));
//		datosRequest.put("tienda", data.get("tienda"));
		
		
		String request = GetRequestFile.getRequestFile(runGetCFIDv3_1, datosRequest);
		System.out.println(request);
		testCase.addTextEvidenceCurrentStep("Request: \n" + request);
		
		String response = GetRequest.executeGetRequest(request);	
		testCase.addTextEvidenceCurrentStep("Response: \n" + response);
		System.out.println(response);
		
		return response;
	}
	

	
	public String executeRunGetCFDIv3_2() throws ReempRequestException, ClientProtocolException, IOException, JAXBException {
		
		
		HashMap<String, String> datosRequest = new HashMap<>();
		
		datosRequest.put("host", data.get("host"));
		
		
		String request = GetRequestFile.getRequestFile(runGetCFIDv3_2, datosRequest);
		System.out.println(request);
		testCase.addTextEvidenceCurrentStep("Request: \n" + request);
		
		String response = GetRequest.executeGetRequest(request);	
		testCase.addTextEvidenceCurrentStep("Response: \n" + response);
		System.out.println(response);
		
		return response;
	}
	
}
