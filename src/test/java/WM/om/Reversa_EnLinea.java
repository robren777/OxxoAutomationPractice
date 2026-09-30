package WM.om;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import exceptions.ReempRequestException;
import io.restassured.response.Response;
import utils.ApiMethodsUtil;


public class Reversa_EnLinea {
	
	public Reversa_EnLinea () {
			System.out.println("Todo OK");
		}
	
	/**
	 * Metodo para obtener el path QRY01
	 * 
	 * @author Gabriel de Jesus Rubio
	 * @param requestData es un hashMap con los parametros necesarios de la api
	 * @return Regresa un String con el path y los parametros
	 */
	public String getQRY01(HashMap<String, String> requestData) {
		return "/invoke/TPE."+ requestData.get("interface") +".Pub/request?xmlIn=<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<TPEDoc version=\"1.0\"><header application=\""+ requestData.get("application") +"\""
				+ " entity=\""+ requestData.get("entity") +"\" operation=\""+ requestData.get("operation") 
				+ "\" source=\""+ requestData.get("source") +"\" plaza=\""+ requestData.get("plaza") +"\" tienda=\""
				+ requestData.get("tienda") +"\" caja=\""+ requestData.get("caja") +"\" adDate=\""+ requestData.get("adDate") 
				+"\" pvDate=\""+ requestData.get("pvDate") +"\"/><request><service id=\""+ requestData.get("idItem") 
				+"\" idClient=\""+ requestData.get("idClient") +"\"/></request></TPEDoc>";
	}
	
	/**
	 * Metodo para obtener el path TRN01
	 * 
	 * @author Gabriel de Jesus Rubio
	 * @param requestData es un hashMap con los parametros necesarios de la api
	 * @return Regresa un String con el path y los parametros
	 */
	public String getTRN01(HashMap<String, String> requestData) {
		return "/invoke/TPE."+ requestData.get("interface") +".Pub/request?xmlIn=<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<TPEDoc version=\"1.0\"><header application=\""+ requestData.get("application") +"\""
				+ " entity=\""+ requestData.get("entity") +"\" operation=\""+ requestData.get("operation") 
				+ "\" source=\""+ requestData.get("source") +"\" plaza=\""+ requestData.get("plaza") +"\" tienda=\""
				+ requestData.get("tienda") +"\" caja=\""+ requestData.get("caja") +"\" adDate=\""+ requestData.get("adDate") 
				+"\" pvDate=\""+ requestData.get("pvDate") +"\"/></TPEDoc> ";
	}
	
	/**
	 * Metodo para obtener el path TRN02
	 * 
	 * @author Gabriel de Jesus Rubio
	 * @param requestData es un hashMap con los parametros necesarios de la api
	 * @return Regresa un String con el path y los parametros
	 */
	public String getTRN02(HashMap<String, String> requestData) {
		return "/invoke/TPE."+ requestData.get("interface") +".Pub/request?xmlIn=<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<TPEDoc version=\"1.0\"><header application=\""+ requestData.get("application") +"\""
				+ " entity=\""+ requestData.get("entity") +"\" operation=\""+ requestData.get("operation") 
				+ "\" folio=\""+ requestData.get("folio") +"\" adDate=\""+ requestData.get("adDate") 
				+"\" pvDate=\""+ requestData.get("pvDate") +"\" operator=\""+ requestData.get("operator") +"\"/>"
				+ "<request><service id=\""+ requestData.get("idItem") +"\" accountId=\""+ requestData.get("accountId") +"\""
				+ " idClient=\""+ requestData.get("idClient") +"\" ticket=\""+ requestData.get("ticket") +"\" "
				+ "total=\""+ requestData.get("total") +"\" entryMode=\""+ requestData.get("entryMode") +"\" "
				+ "payType=\""+ requestData.get("payType") +"\"/></request></TPEDoc>";
	}
	
	/**
	 * Metodo para obtener el path TRN03
	 * 
	 * @author Gabriel de Jesus Rubio
	 * @param requestData es un hashMap con los parametros necesarios de la api
	 * @return Regresa un String con el path y los parametros
	 */
	public String getTRN03(HashMap<String, String> requestData) {
		return "/invoke/TPE."+ requestData.get("interface") +".Pub/request?xmlIn=<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<TPEDoc version=\"1.0\"><header application=\""+ requestData.get("application") +"\""
				+ " entity=\""+ requestData.get("entity") +"\" operation=\""+ requestData.get("operation") 
				+ "\" folio=\""+ requestData.get("folio") +"\"/><request><ack value=\""+ requestData.get("ack") +"\" />"
				+ "</request></TPEDoc>";
	}
	
	/**
	 * Metodo para obtener el path  TRN04
	 * 
	 * @author Gabriel de Jesus Rubio
	 * @param requestData es un hashMap con los parametros necesarios de la api
	 * @return Regresa un String con el path y los parametros
	 */
	public String getTRN04(HashMap<String, String> requestData) {
		return "/invoke/TPE."+ requestData.get("interface") +".Pub/request?xmlIn=<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<TPEDoc><header application=\""+ requestData.get("application") +"\""
				+ " entity=\""+ requestData.get("entity") +"\" operation=\""+ requestData.get("operation") 
				+ "\" source=\""+ requestData.get("source") +"\" plaza=\""+ requestData.get("plaza") +"\" tienda=\""
				+ requestData.get("tienda") +"\" caja=\""+ requestData.get("caja") +"\" pvDate=\""+ requestData.get("pvDate") +"\"/>"
				+ "<wmCodeList><wmCode auth=\""+ requestData.get("auth") +"\" folio=\""+ requestData.get("folio") 
				+"\" creationDate=\""+ requestData.get("creationDate") +"\"/></wmCodeList></TPEDoc>";
	}

	/**
	 * Metodo para obtener el path con los parametros necesarios para las api
	 * 	OLS, QRY01, TRN01, TRN02, TRN03, TRN04
	 * 
	 * @author Gabriel de Jesus Rubio
	 * @param requestData es un hashMap con los parametros necesarios de la api
	 * @return Regresa un String con el path y los parametros
	 */
	public String getRequest(HashMap<String, String> requestData) throws ReempRequestException, ClientProtocolException, IOException, Exception{
		String op = requestData.get("operation"); 
		String request;
		
		switch (op) {
		case "TRN01":
			request = getTRN01(requestData);
			break;
		case "TRN02":
			request = getTRN02(requestData);
			break;
		case "TRN03":
			request = getTRN03(requestData);
			break;
		case "TRN04":
			request = getTRN04(requestData);
			break;
		default:
			request = getQRY01(requestData);
			break;
		}
		
		//Sustituye los datos en el XML
		System.out.println("Request "+ op +": " + request);
		
		return request;
	}

	/**
	 * Metodo para obtener el path con los parametros necesarios para las api
	 * 	OLS, QRY01, TRN01, TRN02, TRN03, TRN04
	 * 
	 * @author Gabriel de Jesus Rubio
	 * @param requestData es un hashMap con los parametros necesarios de la api
	 * @return Regresa un String con el path y los parametros
	 */
	public String[] getRequest(HashMap<String, String> requestData, int n) throws ReempRequestException, ClientProtocolException, IOException, Exception{
		String request = "";
		String op = requestData.get("operation"); 
		
		switch (op) {
		case "TRN01":
			request = getTRN01(requestData);
			break;
		case "TRN02":
			request = getTRN02(requestData);
			break;
		case "TRN03":
			request = getTRN03(requestData);
			break;
		case "TRN04":
			request = getTRN04(requestData);
			break;
		default:
			request = getQRY01(requestData);
			break;
		}
		
		//Sustituye los datos en el XML
		String[] requests = new String[n];
		for (int i=0; i<n; i++) {
			System.out.println("Request "+ op +": " + i + " " + request);
			requests[i] = request;
		}
		return requests;
	}

	/**
	 * Metodo para obtener el response de la api a la que se este llamando
	 * 
	 * @author Gabriel de Jesus Rubio
	 * @param request es el path + parametros necesarios de la api
	 * @return Regresa un objeto Respone con la informacion de la api
	 */
	public Response getApiResponseXML(String baseURL, String request) {
		System.out.println(baseURL + request);
		ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("Content-Type", "application/xml");
		Response response = api.getRequestWithHeaders(request , headersList);
		System.out.println("body");
		System.out.println(response.getBody().asPrettyString());
		System.out.println(response.getStatusLine());
		return response;
	}

	/**
	 * Procesa multiples llamadas simultanas y regresa el listado de 
	 *  response XML en String de la api a la que se este llamando
	 * 	en nuestro caso OLS
	 * 
	 * @author Gabriel de Jesus Rubio
	 * @param baseURL es un String con el URL base de la api
	 * @param requests una lista con los path + parametros necesarios de la api
	 * @return Regresa un array String con la informacion de la api
	 */
	public Response[] getApiResponseXML(String baseURL, String[] requests) throws InterruptedException, ExecutionException  {
		int numRequests = requests.length;
		ExecutorService executor = Executors.newFixedThreadPool(numRequests);
		@SuppressWarnings("unchecked")
		Future<Response>[] futures = new Future[numRequests];

		for (int i = 0; i < numRequests; i++) {
		    final Integer innerMi = i;
			futures[i] = (Future<Response>) executor.submit(() -> {
				ApiMethodsUtil api = new ApiMethodsUtil(baseURL);
				HashMap<String, String> headersList = new HashMap<>();
				headersList.put("Content-Type", "application/xml");
				Response response = api.getRequestWithHeaders(requests[innerMi] , headersList);
				return response;
			});
		}
		executor.shutdown();
	    Response[] responses = new Response[numRequests]; 
		for (int i = 0; i < numRequests; i++) {
			responses[i] = futures[i].get(); // Espera la respuesta de cada petición
		}
		return responses;
	}

	/**
	 * Metodo para obtener un Document de un String XML
	 * @author Gabriel de Jesus Rubio
	 * @param xml es un string en formato xml
	 * @return Regresa un Objeto Document con la informacion del xml 
	 */
	public Document xmlToDoc (String xml) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource src = new InputSource();
		src.setCharacterStream(new StringReader(xml));
		Document doc = builder.parse(src);
		return doc;
	}

	/**
	 * Metodo para obtener valores de un tag de un XML
	 * @author Gabriel de Jesus Rubio
	 * @param xml es un string en formato xml
	 * @param tag es un string con el nombre del tag buscado
	 * @return Regresa un Objeto Document con la informacion del xml 
	 */
	public Map<String, String> getValuesTagsResponse(String xml, String tag) throws Exception {
		Document doc = xmlToDoc(xml);
		HashMap<String, String> values = new HashMap<>();
		NamedNodeMap tagAtts = doc.getElementsByTagName(tag).item(0).getAttributes();
		for (int i=0, size=tagAtts.getLength(); i<size; i++) {
			values.put(tagAtts.item(i).getNodeName(),tagAtts.item(i).getNodeValue());
		}
		return values;
	}

	/**
	 * Metodo para obtener unicamente valores necesarios del response de QRY01
	 * @author Gabriel de Jesus Rubio
	 * @param xml es un string en formato xml
	 * @return Regresa un Objeto Document con la informacion del xml 
	 */
	public Map<String, String> getValuesQRY01(String xml) throws Exception {
		Document doc = xmlToDoc(xml);
		Map<String, String> values = getValuesTagsResponse(xml, "service");
		NodeList details = doc.getElementsByTagName("detail");
		for (int i=0, size=details.getLength(); i<size; i++) {
			NamedNodeMap detailAtts = details.item(i).getAttributes();
			String nvalue = detailAtts.getNamedItem("desc").getNodeValue();
			if (nvalue.equals("Total") || nvalue.equals("total") ) {
				values.put("monto", detailAtts.getNamedItem("monto").getNodeValue());
			}
		}
		return values;
	}
}