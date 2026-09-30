package WM.interfaces.ENRED_OUT;

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

/* DESCRIPCION : Obtener documentos con OUTBOUND.
 * 
 * MTC-FT-002-OUTBND_EnviaListDocATienda
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles en las tabla POS_OUT_DOC_RMS en Status W.
 * 	Tener instalado Postman
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos disponibles pendiente por procesar.
 * 
 * @Author Rafael Alarcón
 * @Date 18/Julio/2024
 */
public class ATC_FT_003_ENREDOUT_OUTBOUND_EnviaDocATienda extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_OUTBOUND_EnviaDocATienda_test(HashMap<String, String> data) throws Exception {

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
		int getExpectedCode = Integer.parseInt(data.get("getExpectedCode"));
		String getPathToken = data.get("getPathToken");
		String departmentId = data.get("departmentId");
		String storeId = data.get("storeId");
		String docName = data.get("docName");
		String fileType = data.get("fileType");
		String source = data.get("source");

		// Ejecuta request
		RestAssured.baseURI = baseURLToken;

		// Lista de headers
		HashMap<String, String> headersListToken = new HashMap<>();
		headersListToken.put("Accept", "*/*");
		headersListToken.put("Authorization", data.get("token"));

		String reqBodyToken = "";

		System.out.println(
				" --------------------------------INICIA CONSULTA DOCUMENTOS PENDIENTES----------------------------------------------- ");

		addStep("Validar en base de datos,  que existan documentos pendientes de procesar por la tienda");

		String consultaDocPtes = "SELECT ID, PV_CR_PLAZA, PV_CR_TIENDA, DOC_TYPE, DOC_NAME, STATUS \r\n"
				+ "FROM POSUSER.pos_out_doc_rms\r\n" + "WHERE PV_CR_PLAZA = '" + departmentId + "'\r\n"
				+ "AND PV_CR_TIENDA = '" + storeId + "'\r\n" + "AND DOC_NAME = '" + docName + "'\r\n"
				+ "ORDER BY TIMESTAMP_CREATED DESC";

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
				String DOC_NAME = DocPdtesResult.getString("DOC_NAME");
				String STATUS = DocPdtesResult.getString("STATUS");

				testCase.addTextEvidenceCurrentStep("--> " + "ID : " + ID + " | " + "PV_CR_PLAZA : " + PV_CR_PLAZA
						+ " | " + "PV_CR_TIENDA : " + PV_CR_TIENDA + " | " + "DOC_TYPE : " + DOC_TYPE + " | "
						+ "DOC_NAME : " + DOC_NAME + " | " + "STATUS : " + STATUS);
			}
			docs_id = docs_id.substring(0, docs_id.length() - 1);
		} else {
			assertFalse(DocPdtesResult.isBeforeFirst(), "No hay documentos pendientes a procesar");
		}

		System.out.println(
				" --------------------------------INICIA CONSULTA CONFIGURACION DE LA TIENDA----------------------------------------------- ");

		addStep("Validar en base de datos,  que existan las configuraciones de la interfaz");

		String conusltaConfiguracion = "SELECT INTERFASE, ENTIDAD, OPERACION, CATEGORIA, VALOR1, VALOR2, DESCRIPCION "
				+ "FROM POSUSER.POS_INTERFASE_CONFIG WHERE INTERFASE = 'ENRED_OUT'";

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

		Response getRespuesta = api.postRequestWithHeaders(baseURLToken + getPathToken, reqBodyToken, headersListToken);

		System.out.println("RESULTADO_EJECUCION_POSTMAN: " + getRespuesta);

		// Obtener body de respuesta
		String getBodyResponse = getRespuesta.getBody().asString();
		System.out.println("RESPUESTA_TOKEN : " + getBodyResponse);

		// Validacion de JSON
		JsonPath getJson = new JsonPath(getBodyResponse);
		String access_token = getJson.get("access_token");

		System.out.println(
				" --------------------------------INICIA INVOCACION DE METODO OUTBOUND----------------------------------------------- ");

		addStep("Invocar la API Outbound con JSON");

		// Llamada al API OUTBOUND usando el token generado
		String baseURIOutbound = "https://ipaas-stg.oxxo.io";
		String getPathOutbound = data.get("getPathOutbound");

		RestAssured.baseURI = baseURIOutbound;
		String reqBodyOutbound = "{" + "\"departmentId\":\"" + departmentId + "\"," + "\"storeId\":\"" + storeId + "\","
				+ "\"source\":\"" + source + "\"," + "\"documents\":[" + "{" + "\"name\": \"" + docName + "\","
				+ "\"fileType\": \"" + fileType + "\"" + "}" + "]" + "}";

		// Lista de headers
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("Accept", "*/*");
		headersList.put("content-type", "application/json");
		// se comenta para pruebas
		headersList.put("Authorization", "Bearer " + access_token);

		// Hacemos el send del request
		Response getRespuesta3 = api.postRequestWithHeaders(baseURIOutbound + getPathOutbound, reqBodyOutbound,
				headersList);
		String getBodyResponse3 = getRespuesta3.getBody().asPrettyString();
		JsonPath getJson3 = new JsonPath(getBodyResponse3);

		// Se parsea el valor de String a Int
		int codeResp = Integer.parseInt(getJson3.getString("code"));

		assertEquals(codeResp, getExpectedCode); // Validacion

		java.util.List<Map<String, Object>> documentsList = getJson3.getList("documents");

		for (Map<String, Object> document : documentsList) {

			String campo1 = (String) document.get("name");
			String campo2 = (String) document.get("fileType");
			String campo3 = (String) document.get("status");

			testCase.addTextEvidenceCurrentStep("--> " + " Documento: " + campo1 + " | " + "Tipo: " + campo2 + " | "
					+ "Status: " + campo3 + "\r\n");
		}

		System.out.println(
				" --------------------------------INICIA CONSULTA DE LOG----------------------------------------------- ");

		addStep("Validar el registro del inicio y el fin de la ejecucion en la transaccion en base de datos WM_OUT_DOC_LOG");

		Date fechaFin;
		fechaFin = new Date();
		String fechaFinal = formatter.format(fechaFin);

		String consultaTablaLog = "SELECT WM_CODE, TO_CHAR(START_DATE, 'dd/mm/yyyy hh24:mi:ss')START_DATE, OPERATION, CR_PLAZA, CR_TIENDA "
				+ "FROM WM_OUT_DOC_LOG WHERE START_DATE >= TRUNC(SYSDATE) AND START_DATE BETWEEN TO_DATE('"
				+ fechaInicial + "','dd/mm/yyyy hh24:mi:ss') AND TO_DATE('" + fechaFinal
				+ "','dd/mm/yyyy hh24:mi:ss') AND OPERATION = 'OUTBOUND' ORDER BY START_DATE ASC";

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
				" --------------------------------INICIA VALIDACION DE CAMBIO DE ESTATUS----------------------------------------------- ");

		addStep("Validar el cambio de estatus de los documentos obtenidos por la interfaz que se envia a tienda");

		String consultaUpdEstatus = "SELECT ID, PV_CR_PLAZA, PV_CR_TIENDA, DOC_TYPE, DOC_NAME, STATUS "
				+ "FROM POSUSER.pos_out_doc_rms WHERE ID IN (" + docs_id + ") AND PV_CR_PLAZA IN ('" + departmentId
				+ "') AND PV_CR_TIENDA IN ('" + storeId + "') ORDER BY TIMESTAMP_CREATED DESC";

		PreparedStatement PosQuery = dbPos.prepareStatement(consultaUpdEstatus);
		ResultSet PosResult = PosQuery.executeQuery();

		if (PosResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(consultaUpdEstatus);
			while (PosResult.next()) {
				String ID = PosResult.getString("ID");
				String PV_CR_PLAZA = PosResult.getString("PV_CR_PLAZA");
				String PV_CR_TIENDA = PosResult.getString("PV_CR_TIENDA");
				String DOC_TYPE = PosResult.getString("DOC_TYPE");
				String DOC_NAME = PosResult.getString("DOC_NAME");
				String STATUS = PosResult.getString("STATUS");

				testCase.addTextEvidenceCurrentStep("--> " + "ID : " + ID + " | " + "PV_CR_PLAZA : " + PV_CR_PLAZA
						+ " | " + "PV_CR_TIENDA : " + PV_CR_TIENDA + " | " + "DOC_TYPE : " + DOC_TYPE + " | "
						+ "DOC_NAME : " + DOC_NAME + " | " + "STATUS : " + STATUS);
			}
		} else {
			assertFalse(PosResult.next(), "--> ¡ERROR!, No se encuentra información sobre los documentos");
		}

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1.- Validar que los archivos existan en File System";
	}

	@Override
	public String setTestDescription() {
		return "Obtener documentos con OUTBOUND";
	}

	@Override
	public String setTestDesigner() {
		return "Fabrica QA Optasmart";
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