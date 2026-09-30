package WM.interfaces.ENRED_OUT;

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
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

public class ATC_001_ValidarApi_LISP_WmCode101 extends BaseExecution {
@Test
(dataProvider = "data-provider")

	public void ATC_001_ValidarApi_LISP_WmCode101_test (HashMap<String, String> data) throws Exception {
	/**
	 * Transformaci�n Multiformato: ATC_001_ValidarApi_LISP_WmCode101
	 * Validar Api LISP WmCode 101
	 * Desc: La API responde con la lista de documentos pendientes para esa tienda
	 * Luis G. Ramirez Martinez
	 * 08/03/2024
	 */
	/**
	 * Prerequisitos: 
	 * Tener accceso a la la VPN de Oxxo.
	 * Tener documentos generados.
	 * Tener acceso a la Base de Datos WM_OUT_DOC_LOG (oxfwm6q00.femcom.net:1535, FCWMLQA.FEMCOM.NET).
	 */
	/*
	 * Utilerias
	 *********************************************************************/
	utils.sql.SQLUtil dbLOG = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_LOG_MEX_MULTIFORMATO,GlobalVariables.DB_USER_LOG_MEX_MULTIFORMATO, GlobalVariables.DB_PASSWORD_LOG_MEX_MULTIFORMATO);
	
	String nameDocument="";
	
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

	System.out.println(" ------------------------------------------------------------------------------- ");
	
	addStep("Invocar la API LISP para conocer la lista de documentos pendientes para la tienda");	
	
	//Definici�n de Variables 	
	String baseURILisp = "https://ipaas-stg.oxxo.io";
	String getPathLisp = data.get("getPathLisp");
	String departmentId = data.get("departmentId");
	String storeId = data.get("storeId");
	String source = data.get("source");
	
	//Se env�a como evidencia el request a ejecutar
	testCase.addTextEvidenceCurrentStep("Request: \n"+ baseURILisp+getPathLisp);
		
	//Llamada al API LISP usando el token generado
	RestAssured.baseURI = baseURILisp;	
	String reqBody = "{"
			+ "\"departmentId\":\""+departmentId+"\","
			+ "\"storeId\":\""+storeId+"\","
			+ "\"source\":\""+source+"\""
	+ "}";
	
	//Lista de headers
	HashMap<String, String> headersList = new HashMap<>();
	headersList.put("Accept", "*/*");
	headersList.put("content-type", "application/json");
	headersList.put("Authorization", "Bearer "+access_token);

	//Hacemos el send del request
	Response getRespuesta2 = api.postRequestWithHeaders(baseURILisp+getPathLisp, reqBody, headersList);
	System.out.print(getRespuesta2);	
									
	//Obtener body de respuesta
	String getBodyResponse2 = getRespuesta2.getBody().asPrettyString();	
	System.out.println("Body: "+getBodyResponse2);
		
	//Validaci�n de JSON
	JsonPath getJson2 = new JsonPath(getBodyResponse2);
	String documents = getJson2.getString("documents.name"); 
	
	//VALIDACION DE CODE
	int codeResp = Integer.parseInt(getJson2.getString("code")); 
	assertEquals(codeResp, getExpectedCode); //Validacion	
	
	// Obtener la lista de documentos OUTBOUND
	java.util.List <Map<String, Object>> documentsList = getJson2.getList("documents");
	System.out.println("documentsList: "+documentsList);
	
	// Obtener el NOMBRE del documento OUTBOUND
	nameDocument = (String) documentsList.get(0).get("name"); //;
	System.out.println("Name del documento Outbound: " + nameDocument);
	
	//Se env�a como evidencia el Code y la Respuesta
	testCase.addTextEvidenceCurrentStep("Code: " +codeResp);
	testCase.addTextEvidenceCurrentStep("Respuesta: " +getBodyResponse2);
	
	addStep("Validar que se registro la ejecuci�n en la tabla de log");
	fechaFin = new Date();
	String fechaFinal = formatter.format(fechaFin);
	
	System.out.println("fechaInicio: "+fechaInicial);
	System.out.println("fechaFin: "+fechaFinal);
		
	 //Consulta de Logs
	String ConsultaLogs = String.format(consultaTablaLog, fechaInicial, fechaFinal);
	
	SQLResult statusTablaLOG = dbLOG.executeQuery(ConsultaLogs);
	
	boolean paso2 = consultaTablaLog.isEmpty();
	if (!paso2) {
		testCase.addQueryEvidenceCurrentStep(statusTablaLOG);
	}
	assertFalse("No se muestra la informacion.", paso2);
	System.out.println("paso2: "+paso2);
	}

	private void assertFalse(String string, boolean paso3) {
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
		return "Validar Api LISP WmCode 101";
	}

	@Override
	public String setTestDesigner() {
		return "Luis Ramirez";
	}

	@Override
	public String setTestFullName() {
		return "ATC_001_ValidarApi_LISP_WmCode101";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
