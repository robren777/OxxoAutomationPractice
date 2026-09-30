package WM.interfaces.ENRED_OUT;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import modelo.BaseExecution;
import utils.ApiMethodsUtil;
import utils.sql.SQLResult;

public class ATC_009_ValidarApi_ACKEstatusP_WmCode101 extends BaseExecution{
	@Test
	(dataProvider = "data-provider")
	
	public void ATC_009_ValidarApi_ACKEstatusP_WmCode101_test (HashMap<String, String> data)throws Exception {
		/**
		 * Transformaci�n Multiformato: ATC_009_ValidarApi_ACKEstatusP_WmCode101
		 * Validar Api ACK Estatus P WmCode 101
		 * Desc: La API responde con el JSON correspondiente con wmCode 101
		 * Luis G. Ramirez Martinez
		 * 08/03/2024
		 */
		/**
		 * Prerequisitos: 
		 * Tener accceso a la la VPN de Oxxo.
		 * Tener documentos generados.
		 * Tener acceso a la Base de Datos WM_OUT_DOC_LOG (oxfwm6q00.femcom.net, 1535, FCWMLQA.FEMCOM.NET). 
		 * Tener acceso a la Base de Datos POS_OUT_DOC_RMS (oxfwm6q00.femcom.net, 1535, FCWM6QA.FEMCOM.NET). 
		 */
		/*
		 * Utilerias
		 *********************************************************************/
		utils.sql.SQLUtil dbPosuser = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_POSUSER_MEX_MULTIFORMATO, GlobalVariables.DB_USER_POSUSER_MEX_MULTIFORMATO, GlobalVariables.DB_PASSWORD_POSUSER_MEX_MULTIFORMATO);
		utils.sql.SQLUtil dbLOG = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_LOG_MEX_MULTIFORMATO,GlobalVariables.DB_USER_LOG_MEX_MULTIFORMATO, GlobalVariables.DB_PASSWORD_LOG_MEX_MULTIFORMATO);
		
		String nameOutbound="";
		
		String consultaTablaPosuser = "SELECT DOC_NAME, STATUS, DOC_TYPE, PV_CR_PLAZA, PV_CR_TIENDA FROM posuser.pos_out_doc_rms WHERE DOC_NAME = '%s' AND STATUS = 'P'";
		
		String consultaTablaLog = "SELECT WM_CODE, TO_CHAR(START_DATE, 'dd/mm/yyyy hh24:mi:ss')START_DATE, OPERATION, CR_PLAZA, CR_TIENDA "
				+ "FROM WM_OUT_DOC_LOG\r\n"
				+ "WHERE START_DATE >= TRUNC(SYSDATE)\r\n"
				+ "AND START_DATE "
				+ "BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss') "
				+ "AND TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n"
				+ "ORDER BY START_DATE \r\n"
				+ "ASC";
		
		Date fechaInicio;
		Date fechaFin;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));
		
		fechaInicio = new Date();
		String fechaInicial = formatter.format(fechaInicio);
		
		String baseURLToken = "https://ipaas-stg.oxxo.io";
		
		ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);
		//Obtener datos del data provider
		String getPathToken = data.get("getPathToken");
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));	

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
		String name = (String) documentsList.get(0).get("name"); //;
		System.out.println("Name del documento: " + name);
		
		// Obtener el fileType del documento
		String fileType = (String) documentsList.get(0).get("fileType"); //;
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
			//testCase.addTextEvidenceCurrentStep("Respuesta: " +getBodyResponse3);
				
			System.out.println("Body: "+getBodyResponse3);
			
			JsonPath getJson3 = new JsonPath(getBodyResponse3);
			String documentsOutbound = getJson3.getString("documents.name");
			
			//VALIDACION CODE
			int codeResp = Integer.parseInt(getJson2.getString("code")); 
			assertEquals(codeResp, getExpectedCode); 		
			
			// Obtener la lista de documentos OUTBOUND
			java.util.List <Map<String, Object>> documentsListOutbound = getJson3.getList("documents");
			System.out.println("documentsListOutbound: "+documentsListOutbound);
			//testCase.addTextEvidenceCurrentStep("Lista Documentos Outbound: \n"+ getBodyResponse3);
			
			// Obtener el NOMBRE del documento OUTBOUND
			nameOutbound = (String) documentsListOutbound.get(0).get("name"); //;
			System.out.println("Name del documento Outbound: " + nameOutbound);
			
			// Obtener el FILETYPE del documento OUTBOUND
			String fileTypeOutbound = (String) documentsListOutbound.get(0).get("fileType"); //;
			System.out.println("fileType del documento Outbound: " + fileTypeOutbound);
			
			// Obtener el STATUS del documento OUTBOUND
			String statusOutbound = (String) documentsListOutbound.get(0).get("status"); //;
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
			
			addStep("Invocar la API ACK para confirmar un documento descargado (Estatus P)");	
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
			testCase.addTextEvidenceCurrentStep("Code ACK: "+ codeACK);
			testCase.addTextEvidenceCurrentStep("Mensaje ACK: "+ messageACK);
			testCase.addTextEvidenceCurrentStep("Body Response ACK: \n"+ getBodyResponseACK);
			
			assertEquals(codeACK, getExpectedCode);
			
			addStep("Validar que se actualice la tabla POS_OUT_DOC_RMS para el documento con estatus P");
			
			String consultaDocumento1_f = String.format(consultaTablaPosuser, nameOutbound);
			
			SQLResult statusTablaRMS = dbPosuser.executeQuery(consultaDocumento1_f);
			String doc_name = statusTablaRMS.getData(0, "doc_name");
			System.out.println("doc_name: " + doc_name);
			
			boolean paso2 = consultaTablaPosuser.isEmpty();
			if (!paso2) {
				testCase.addQueryEvidenceCurrentStep(statusTablaRMS);
			}
			assertFalse("No se muestra la informacion.", paso2);
			System.out.println("paso2: "+paso2);			
			
			addStep("Validar que se registro la ejecuci�n en la tabla de log");
			
			fechaFin = new Date();
			String fechaFinal = formatter.format(fechaFin);
			
			System.out.println("fechaInicio: "+fechaInicial);
			System.out.println("fechaFin: "+fechaFinal);
			
			//Consulta de Logs
			String ConsultaLogs = String.format(consultaTablaLog, fechaInicial, fechaFinal);
			
			SQLResult statusTablaLOG = dbLOG.executeQuery(ConsultaLogs);
			
			boolean paso3 = consultaTablaLog.isEmpty();
			if (!paso3) {
				testCase.addQueryEvidenceCurrentStep(statusTablaLOG);
			}
			assertFalse("No se muestra la informacion.", paso3);
			System.out.println("paso3: "+paso3);
	
	}
	
	private void assertFalse(String string, boolean paso2) {
		// TODO Auto-generated method stub
		
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
		return " Invocar la API ACK para confirmar un documento descargado (Estatus P)";
	}

	@Override
	public String setTestDesigner() {
		return " Luis Ramirez";
	}

	@Override
	public String setTestFullName() {
		return " ATC_009_ValidarApi_ACKEstatusP_WmCode101";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}