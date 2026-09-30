package WM.interfaces.ENRED_OUT;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.FTPUtil;

public class ATC_015_MoverDocumentoDeCarpetaWorkingACarpetaBackup_ACKEstatusP extends BaseExecution{
@Test
(dataProvider = "data-provider")

	public void ATC_015_MoverDocumentoDeCarpetaWorkingACarpetaBackup_ACKEstatusP_test (HashMap<String, String> data)throws Exception {
		/**
		 * Transformaci�n Multiformato: ATC_015_MoverDocumentoDeCarpetaWorkingACarpetaBackup_ACKEstatusP
		 * Cuando se recibe el estatus P en ACK, se muevel el documento de la carpeta working a la carpeta Backup, dentro del buzon de la tienda.
		 * Desc: Se muestra el documento en la carpeta "Backup", dentro del buzon de la tienda.
		 * Luis G. Ramirez Martinez
		 * 08/03/2024
		 */
	/**
	 * Prerequisitos: 
	 * Tener accceso a la la VPN de Oxxo.
	 * Tener documentos generados.
	 * Tener acceso al buz�n 'backup' (10.182.92.13:21).
	 */
	String baseURLToken = "https://ipaas-stg.oxxo.io";
	
	ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);
	//Obtener datos del data provider
	String getPathToken = data.get("getPathToken");
	int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
	
    HttpPost httpPost = new HttpPost(baseURLToken+getPathToken);		

	//Ejecuta request
	RestAssured.baseURI = baseURLToken;
	
	//Lista de headers
	HashMap<String, String> headersListToken = new HashMap<>();
	headersListToken.put("Accept", "*/*");
	headersListToken.put("Authorization", "Basic ZTAxNDA4Mjc3NWIyNDQ2NGE1NDcxMDBjMzA3ZjQ1OTE6ZjU4MmZlMDM0YWZhNDlkZDgwMzM1NWQ3N2JlMjE3YmM=");

	String reqBodyToken = "";
									
	//Hacemos el send del request
	Response getRespuesta = api.postRequestWithHeaders(baseURLToken+getPathToken, reqBodyToken, headersListToken);
	
	//Obtener body de respuesta
	String getBodyResponse = getRespuesta.getBody().asString();		
	
	 //Validaci�n de JSON
	JsonPath getJson = new JsonPath(getBodyResponse);
	String access_token = getJson.get("access_token");

	//Obtener el status code
	int getStatusCode = getRespuesta.getStatusCode();	

	
	System.out.println("statusCode: "+getStatusCode);
	System.out.println("body response: "+getBodyResponse);
	System.out.println("Access Token: "+access_token);

	System.out.println(" ----------------------------LISP------------------------------------------- ");
	
	addStep("Invocar la API LISP");	
	/// Llamada al API LISP usando el token generado	
	String baseURILisp = "https://ipaas-stg.oxxo.io";
	String getPathLisp = data.get("getPathLisp");
	String departmentId = data.get("departmentId");
	String storeId = data.get("storeId");
	String source = data.get("source");
	
	//Creamos lista de headers
	HashMap<String, String> headersList = new HashMap<>();
	headersList.put("Accept", "*/*");
	headersList.put("content-type", "application/json");
	headersList.put("Authorization", "Bearer "+access_token);
		
	RestAssured.baseURI = baseURILisp;	
	String reqBodyLisp = "{"
			+ "\"departmentId\":\""+departmentId+"\","
			+ "\"storeId\":\""+storeId+"\","
			+ "\"source\":\""+source+"\""
	+ "}";
	testCase.addTextEvidenceCurrentStep("Body Request: "+baseURILisp+getPathLisp);
	testCase.addTextEvidenceCurrentStep("Body Request: \n"+ reqBodyLisp);
	
	//Hacemos el send del request
	Response getRespuesta2 = api.postRequestWithHeaders(baseURILisp+getPathLisp, reqBodyLisp, headersList);
									
	//Obtener body de respuesta
	String getBodyResponse2 = getRespuesta2.getBody().asPrettyString();
		
	JsonPath getJson2 = new JsonPath(getBodyResponse2);
	String documents = getJson2.getString("documents.name");
	
	// Obtener la lista de documentos LISP
	java.util.List <Map<String, Object>> documentsList = getJson2.getList("documents");
	System.out.println("documentsList: "+documentsList);
	System.out.println("------");				
	System.out.println("Documents: "+documents);
	System.out.println("-----");
	
	// Obtener el nombre del documento
	String name = (String) documentsList.get(0).get("name");
	System.out.println("Name del documento: " + name);
	
	// Obtener el fileType del documento
	String fileType = (String) documentsList.get(0).get("fileType");
	System.out.println("fileType del documento: " + fileType);

	System.out.println(" -------------------------OUTBOUND------------------------------------ ");
	
	// Llamada al API OUTBOUND usando el token generado	
		String baseURIOutbound = "https://ipaas-stg.oxxo.io";
		String getPathOutbound = data.get("getPathOutbound");
		
		RestAssured.baseURI = baseURIOutbound;	
		String reqBodyOutbound = "{"
				+ "\"departmentId\":\""+departmentId+"\","
				+ "\"storeId\":\""+storeId+"\","
				+ "\"source\":\""+source+"\","
				+ "\"documents\":["
						+ "{"
							+ "\"name\": \""+name+"\","
							+ "\"fileType\": \""+fileType+"\""
						+ "}"
					+ "]"
			+ "}";
		
		//Hacemos el send del request
		Response getRespuesta3 = api.postRequestWithHeaders(baseURIOutbound+getPathOutbound, reqBodyOutbound, headersList);
		
		//Obtener body de respuesta
		String getBodyResponse3 = getRespuesta3.asPrettyString();
			
		System.out.println("Body: "+getBodyResponse3);
		
		JsonPath getJson3 = new JsonPath(getBodyResponse3);
		String documentsOutbound = getJson3.getString("documents.name");
		
		// Obtener la lista de documentos OUTBOUND
		java.util.List <Map<String, Object>> documentsListOutbound = getJson3.getList("documents");
		System.out.println("documentsListOutbound: "+documentsListOutbound);
		
		// Obtener el NOMBRE del documento OUTBOUND
		String nameOutbound = (String) documentsListOutbound.get(0).get("name");
		System.out.println("Name del documento Outbound: " + nameOutbound);
		
		// Obtener el FILETYPE del documento OUTBOUND
		String fileTypeOutbound = (String) documentsListOutbound.get(0).get("fileType");
		System.out.println("fileType del documento Outbound: " + fileTypeOutbound);
		
		// Obtener el STATUS del documento OUTBOUND
		String statusOutbound = (String) documentsListOutbound.get(0).get("status");
		System.out.println("Status del documento Outbound: " + statusOutbound);
		
		// Setear EVENTDATE del documento OUTBOUND
		// Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        // Formateador con la sintaxis del eventDate
        DateTimeFormatter formatoEventDate = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String eventDateOutbound = (String) now.format(formatoEventDate);
		System.out.println("eventDate del documento Outbound: " + eventDateOutbound);
		
		System.out.println(" -------------------------ACK------------------------------------ ");

		// Llamada al API ACK usando el token generado	
		String baseURIACK = "https://ipaas-stg.oxxo.io";
		String getPathACK = data.get("getPathACK");
		
		addStep("Invocar la API ACK");	
		//Se env�a como evidencia el request a ejecutar
		testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURIACK+getPathACK);
	
		RestAssured.baseURI = baseURIACK;	
		String reqBodyACK = "{\n" +
		        "    \"departmentId\":\""+departmentId+"\",\n" +
		        "    \"storeId\":\""+storeId+"\",\n" +
		        "    \"source\":\"POS\",\n" +
		        "    \"documents\":[\n" +
		        "       {\n" +
		        "          \"name\":\""+nameOutbound+"\",\n" +
		        "          \"fileType\":\""+fileTypeOutbound+"\",\n" +
		        "          \"status\":\"P\",\n" +
		        "          \"eventDate\":\""+eventDateOutbound+"\"\n" +
		        "       }\n" +
		        "    ]\n" +
		        "}";
		testCase.addTextEvidenceCurrentStep("Body Request: \n"+ reqBodyACK);
		
		//Hacemos el send del request
		Response getRespuestaACK = api.postRequestWithHeaders(baseURIACK+getPathACK, reqBodyACK, headersList);
		
		//Obtener body de respuesta
		String getBodyResponseACK = getRespuestaACK.getBody().asPrettyString();
			
		System.out.println("Body: "+getBodyResponseACK);
		
		JsonPath getJsonACK = new JsonPath(getBodyResponseACK);
		String messageACK = getJsonACK.getString("message");
		String codeACK = getJsonACK.getString("code"); 
		
		//VALIDACION CODE
		int codeResp = Integer.parseInt(getJson2.getString("code")); 
		assertEquals(codeResp, getExpectedCode); 
				
		testCase.addTextEvidenceCurrentStep("Body Response ACK: \n"+ getBodyResponseACK);	
		
		
		addStep(" Se valida el movimiento en Filezilla");
		
		Thread.sleep(20000);
				
		FTPUtil ftp = new FTPUtil("10.182.92.13", 21,"posuser","posuser"); //host, puerto, usuario, contrasena
       
		Thread.sleep(20000);
		String ruta = "/FEMSA_OXXO/POS/"+ data.get("departmentId") +"/"+ data.get("storeId") +"/backup/" + nameOutbound;                                
		
		boolean validaFTP;
       
        if (ftp.fileExists(ruta) ) {
        	validaFTP = true;
            testCase.addFileEvidenceCurrentStep(ruta);
            System.out.println("Existe");
            testCase.addBoldTextEvidenceCurrentStep("El archivo si existe ");
            testCase.addBoldTextEvidenceCurrentStep(ruta);
            
        }else {
        	testCase.addFileEvidenceCurrentStep(ruta);
        	testCase.addBoldTextEvidenceCurrentStep("El archivo no existe ");
            System.out.println("No Existe");
            validaFTP = false;
        }
        
        assertTrue("No se encontro el archivo en POSUSER ", validaFTP);
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return " Cuando se recibe el estatus P en ACK, se mueve el documento de la carpeta working a la carpeta Backup, dentro del buzon de la tienda.";
	}

	@Override
	public String setTestDesigner() {
		return " Luis Ramirez";
	}

	@Override
	public String setTestFullName() {
		return " ATC_015_MoverDocumentoDeCarpetaWorkingACarpetaBackup_ACKEstatusP";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
