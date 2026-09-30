package WM.interfaces.ENRED_IN;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

/* DESCRIPCION : Se mandan los datos a traves de la API INBOUND, documentos de subida.
 * 
 * MTC-FT_005_Funcionamiento_Api_Inbound (Archivos NSP, RTV, ADJ, TSF, REC, INV)
 * 
 * PREREQUISITOS:
 * 	Contar con la mensajeria para invocar la API Inbound.
 * 	Tener instalado Postman
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos disponibles pendiente por procesar.
 *
 * @Author Gustavo Morales
 * @Date 09/Febrero/2025
 */
public class ATC_FT_003_ENREDIN_INBOUND_EnviaDocATienda extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_ENREDIN_INBOUND_EnviaDocATienda_test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Transformacion multiformato");
		/**************************** Datos de conexion *******************************/

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		/* POSUSER */
		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_WM_MEX_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		/* WMLOG */
		Connection dbLog = DriverManager.getConnection(GlobalVariables.DB_HOST_LOG_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_LOG_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_LOG_MEX_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG----------------------------------------------- ");
		/****************************
		 * Variables de datos
		 *******************************/
		String docs_id = "";

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));

		Date fechaInicio;

		fechaInicio = new Date();
		String fechaInicial = formatter.format(fechaInicio);

		String baseURLToken = "https://ipaas-stg.oxxo.io";

		ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);

		System.out.println(
				" --------------------------------OBTENER DATOS DEL PROVIDER----------------------------------------------- ");

		// Obtener datos del data provider
		String getExpectedCode = data.get("getExpectedCode");
		String getPathToken = data.get("getPathToken");
		String departmentId = data.get("departmentId");
		String storeId = data.get("storeId");
		String docName = data.get("docName");
		String fileType = data.get("fileType");
		String version = data.get("version");
		String data1 = data.get("data");
		String Event = data.get("Event_date");
		String mime = data.get("mimeType");

		// Ejecuta request
		RestAssured.baseURI = baseURLToken;

		// Lista de headers
		HashMap<String, String> headersListToken = new HashMap<>();
		headersListToken.put("Accept", "*/*");
		headersListToken.put("Authorization", data.get("token"));

		String reqBodyToken = "";

		System.out.println(
				" --------------------------------INICIA CONSULTA CONFIGURACION DE LA TIENDA----------------------------------------------- ");

		addStep("Validar en base de datos,  que existan las configuraciones de la interfaz");

		String conusltaConfiguracion = "SELECT INTERFASE, ENTIDAD, OPERACION, CATEGORIA, VALOR1, VALOR2, DESCRIPCION "
				+ "FROM POSUSER.POS_INTERFASE_CONFIG WHERE INTERFASE = 'ENRED_IN'";

		PreparedStatement ConfQuery = dbPos.prepareStatement(conusltaConfiguracion);
		ResultSet ConfResult = ConfQuery.executeQuery();

		if (ConfResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(conusltaConfiguracion);
			while (ConfResult.next()) {
				String INTERFASE = ConfResult.getString("INTERFASE");
				String ENTIDAD = ConfResult.getString("ENTIDAD");
				String OPERACION = ConfResult.getString("OPERACION");
				String CATEGORIA = ConfResult.getString("CATEGORIA");
				String VALOR1 = ConfResult.getString("VALOR1");
				String VALOR2 = ConfResult.getString("VALOR2");
				String DESCRIPCION = ConfResult.getString("DESCRIPCION");

				testCase.addTextEvidenceCurrentStep("--> " + "INTERFASE : " + INTERFASE + " | " + "ENTIDAD : " + ENTIDAD
						+ " | " + "OPERACION : " + OPERACION + " | " + "CATEGORIA : " + CATEGORIA + " | " + "VALOR1 : "
						+ VALOR1 + " | " + "VALOR2 : " + VALOR2 + " | " + "DESCRIPCION : " + DESCRIPCION);
			}
		} else {
			assertFalse(ConfResult.isBeforeFirst(), "No existen las configuraciones de la interfaz");
		}

		System.out.println(
				" --------------------------------INICIA INVOCACION y GENERACIÓN DE METODO TOKEN----------------------------------------------- ");

		// Hacemos el send del request
		Response getRespuesta = api.postRequestWithHeaders(baseURLToken + getPathToken, reqBodyToken, headersListToken);

		// Obtener body de respuesta
		String getBodyResponse = getRespuesta.getBody().asString();

		// Validacion de JSON
		JsonPath getJson = new JsonPath(getBodyResponse);
		String access_token = getJson.get("access_token");

		// Definicion de Variables
		String baseURILisp = "https://api-stg.oxxo.io";
		String getPathOutbound = data.get("getPathOutbound");
		String source = data.get("source");

		RestAssured.baseURI = baseURILisp;
		String reqBodyOutbound = "{" + "\"departmentId\":\"" + departmentId + "\"," + "\"storeId\":\"" + storeId + "\","
				+ "\"source\":\"" + source + "\"," + "\"fileType\":\"" + fileType + "\"," + "\"eventDate\":\"" + Event
				+ "\"," + "\"documents\":[" + "{" + "\"mimeType\": \"" + mime + "\"," + "\"name\": \"" + docName + "\","
				+ "\"version\": \"" + version + "\"," + "\"data\": \"" + data1 + "\"" + "}" + "]" + "}";

		// Lista de headers
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("Accept", "*/*");
		headersList.put("content-type", "application/json");
		// se comenta para pruebas
		headersList.put("Authorization", "Bearer " + access_token);
		// headersList.put("Authorization",
		// "fe064423fd8248a7a886f72e9081b7a81b1e9d92c9e8412faef6da0164f3a9c8");
		headersList.put("Tracking-Id", "4bd9e9c2-4989-4f21-8398-ad9d19ef0f66");
		headersList.put("Channel-Id", "WEB");
		headersList.put("Country-Code", "MX");
		headersList.put("Language", "SPA");
		headersList.put("User-Agent", "postman/1.0.0");
		headersList.put("x-Gateway-APIKey", "0da9e8f5-100d-4876-9eda-646697a5a888");

		System.out.println(
				" --------------------------------INICIA INVOCACION DE METODO INBOUND----------------------------------------------- ");

		addStep("Invocar la API INBOUND para conocer la lista de documentos pendientes para la tienda");

		// Hacemos el send del request
		Response getRespuesta2 = api.postRequestWithHeaders(baseURILisp + getPathOutbound, reqBodyOutbound,
				headersList);
		String getBodyResponse2 = getRespuesta2.getBody().asPrettyString();
		System.out.println(getBodyResponse2);

		// Validacion de JSON
		JsonPath getJson2 = new JsonPath(getBodyResponse2);
		System.out.println(getJson2);

		// Se parsea el valor de String a Int
		String codeString = getJson2.getString("code");
		char letra = codeString.charAt(0);
		String numeroStr = codeString.substring(1);
		int numero = Integer.parseInt(numeroStr);
		System.out.println("Letra: " + letra);
		System.out.println("N�mero: " + numero);

		assertEquals(codeString, getExpectedCode); // Validacion

		java.util.List<Map<String, Object>> documentsList = getJson2.getList("documents");

		for (Map<String, Object> document : documentsList) {

			String campo1 = (String) document.get("name");
			String campo2 = (String) document.get("fileType");
			String campo3 = (String) document.get("status");

			testCase.addTextEvidenceCurrentStep("--> " + " Documento: " + campo1 + " | " + "Tipo: " + campo2 + " | "
					+ "Status: " + campo3 + "\r\n");
		}

		System.out.println(
				" --------------------------------INICIA CONSULTA DE LOG----------------------------------------------- ");

		addStep("Validar el registro del inicio y el fin de la ejecucion en la transaccion en base de datos WM_INB_DOC_LOG");

		Date fechaFin;
		fechaFin = new Date();
		String fechaFinal = formatter.format(fechaFin);

		String consultaTablaLog = "SELECT WM_CODE, TO_CHAR(START_DATE, 'dd/mm/yyyy hh24:mi:ss')START_DATE, OPERATION, CR_PLAZA, CR_TIENDA "
				+ "FROM WM_INB_DOC_LOG WHERE START_DATE >= TRUNC(SYSDATE) AND START_DATE BETWEEN TO_DATE('"
				+ fechaInicial + "','dd/mm/yyyy hh24:mi:ss') AND TO_DATE('" + fechaFinal
				+ "','dd/mm/yyyy hh24:mi:ss') AND OPERATION = 'inbound' ORDER BY START_DATE ASC";

		System.out.println(consultaTablaLog);

		PreparedStatement LogQuery = dbLog.prepareStatement(consultaTablaLog);
		ResultSet LogResult = LogQuery.executeQuery();

		if (LogResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(consultaTablaLog);
			while (LogResult.next()) {
				String WM_CODE = LogResult.getString("WM_CODE");
				String START_DATE = LogResult.getString("START_DATE");
				String OPERATION = LogResult.getString("OPERATION");
				String CR_PLAZA = LogResult.getString("CR_PLAZA");
				String CR_TIENDA = LogResult.getString("CR_TIENDA");

				testCase.addTextEvidenceCurrentStep(
						"--> " + "WM_CODE : " + WM_CODE + " | " + "START_DATE : " + START_DATE + " | " + "OPERATION : "
								+ OPERATION + " | " + "CR_PLAZA : " + CR_PLAZA + " | " + "CR_TIENDA : " + CR_TIENDA);
			}
		} else {
			assertFalse(LogResult.isBeforeFirst(), "--> ¡ERROR!, No hay ninguna ejecución de la transacción");
		}
		System.out.println(
				" --------------------------------INICIA CONSULTA DOCUMENTOS PENDIENTES----------------------------------------------- ");

		addStep("Validar en base de datos,  que existan documentos pendientes de procesar por la tienda");

		String consultaDocPtes = "SELECT ID, PV_CR_PLAZA, PV_CR_TIENDA, DOC_TYPE, PV_DOC_NAME, STATUS \r\n"
				+ "FROM POSUSER.pos_inb_doc_rms\r\n" + "WHERE PV_CR_PLAZA = '" + departmentId + "'\r\n"
				+ "AND PV_CR_TIENDA = '" + storeId + "'\r\n" + "AND PV_DOC_NAME = '" + docName + "'\r\n"
				+ "ORDER BY PARTITION_DATE DESC";

		PreparedStatement DocPdtesQuery = dbPos.prepareStatement(consultaDocPtes);
		ResultSet DocPdtesResult = DocPdtesQuery.executeQuery();

		if (DocPdtesResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(consultaDocPtes);
			while (DocPdtesResult.next()) {
				// Se concatenan los ids/plaza/tienda para filtros posteriores
				docs_id += DocPdtesResult.getString("ID") + ",";

				String ID = DocPdtesResult.getString("ID");
				String PV_CR_PLAZA = DocPdtesResult.getString("PV_CR_PLAZA");
				String PV_CR_TIENDA = DocPdtesResult.getString("PV_CR_TIENDA");
				String DOC_TYPE = DocPdtesResult.getString("DOC_TYPE");
				String PV_DOC_NAME = DocPdtesResult.getString("PV_DOC_NAME");
				String STATUS = DocPdtesResult.getString("STATUS");

				testCase.addTextEvidenceCurrentStep("--> " + "ID : " + ID + " | " + "PV_CR_PLAZA : " + PV_CR_PLAZA
						+ " | " + "PV_CR_TIENDA : " + PV_CR_TIENDA + " | " + "DOC_TYPE : " + DOC_TYPE + " | "
						+ "PV_DOC_NAME : " + PV_DOC_NAME + " | " + "STATUS : " + STATUS);
			}
			docs_id = docs_id.substring(0, docs_id.length() - 1);
		} else {
			assertFalse(DocPdtesResult.isBeforeFirst(), "No hay documentos pendientes a procesar");
		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1.- Contar con la mensajeria para invocar la API Inbound.\r\n" + "2.- Tener instalado Postman\r\n"
				+ "3.- Tener VPN activado para conexión con las BD";
	}

	@Override
	public String setTestDescription() {
		return "Se mandan los datos a traves de la API INBOUND, documentos de subida.";
	}

	@Override
	public String setTestDesigner() {
		return "Gustavo Morales";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}