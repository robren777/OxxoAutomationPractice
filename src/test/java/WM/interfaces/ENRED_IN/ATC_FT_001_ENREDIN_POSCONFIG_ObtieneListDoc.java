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

/* DESCRIPCION : Obtener documentos con la API POSCONFIG.
 * 
 * MTC_FT_001_Envio_informacion_posConfig
 * 
 * PREREQUISITOS:
 * 	Contar con la mensajeria para invocar la API posConfig.
 * 	Tener instalado Postman
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos disponibles pendiente por procesar.
 * 
 * @Author Gustavo Morales
 * @Date 09/Febrero/2025
 */
public class ATC_FT_001_ENREDIN_POSCONFIG_ObtieneListDoc extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ENREDIN_POSCONFIG_ObtieneListDoc_test(HashMap<String, String> data) throws Exception {

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

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));

		Date fechaInicio;
		fechaInicio = new Date();
		String fechaInicial = formatter.format(fechaInicio);

		String baseURLToken = "https://ipaas-stg.oxxo.io";

		ApiMethodsUtil api = new ApiMethodsUtil(baseURLToken);

		System.out.println(
				" --------------------------------OBTENER DATOS DEL PROVIDER----------------------------------------------- ");

		String getExpectedCode = data.get("getExpectedCode");
		String getPathToken = data.get("getPathToken");
		String departmentId = data.get("departmentId");
		String storeId = data.get("storeId");

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
			assertFalse(!ConfResult.isBeforeFirst(), "No existen las configuraciones de la interfaz");
		}

		System.out.println(
				" -------------------------------- SE GENERA EL TOKEN DE ACCESO Y ENCABEZADO DE LA PETICIÓN ----------------------------------------------- ");
		// Hacemos el send del request
		Response getRespuesta = api.postRequestWithHeaders(baseURLToken + getPathToken, reqBodyToken, headersListToken);

		// Obtener body de respuesta
		String getBodyResponse = getRespuesta.getBody().asString();

		// Validacion de JSON
		JsonPath getJson = new JsonPath(getBodyResponse);
		String access_token = getJson.get("access_token");

		// Definicion de Variables
		String baseURILisp = "https://api-stg.oxxo.io";
		String getPathLisp = data.get("getPathLisp");
		String source = data.get("source");

		// Llamada al API LISP usando el token generado
		RestAssured.baseURI = baseURILisp;
		String reqBody = "{" + "\"departmentId\":\"" + departmentId + "\"," + "\"storeId\":\"" + storeId + "\","
				+ "\"source\":\"" + source + "\"" + "}";

		// Lista de headers
		HashMap<String, String> headersList = new HashMap<>();
		headersList.put("Accept", "*/*");
		headersList.put("content-type", "application/json");
		// se comenta para pruebas
		headersList.put("Authorization", "Bearer " + access_token);
		headersList.put("Tracking-Id", "4bd9e9c2-4989-4f21-8398-ad9d19ef0f66");
		headersList.put("Channel-Id", "WEB");
		headersList.put("Country-Code", "MX");
		headersList.put("Language", "SPA");
		headersList.put("User-Agent", "postman/1.0.0");
		headersList.put("x-Gateway-APIKey", "0da9e8f5-100d-4876-9eda-646697a5a888");
		// headersList.put("webhook_key", "4a6570b0ac0a8bea3b8c58431700590494199");

		System.out.println(
				" --------------------------------INICIA INVOCACION DE METODO CONFIG----------------------------------------------- ");

		addStep("Invocar la API CONFIG para conocer la lista de documentos pendientes para la tienda");

		// Hacemos el send del request
		Response getRespuesta2 = api.postRequestWithHeaders(baseURILisp + getPathLisp, reqBody, headersList);
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

		// Obtener la lista de documentos
		java.util.List<Map<String, Object>> documentsList = getJson2.getList("schedules");

		for (Map<String, Object> document : documentsList) {
			// Obtener campos específicos del mapa
			String campo1 = (String) document.get("priority");
			String campo2 = (String) document.get("fileType");

			// Procesa la clave y el valor según sea necesario
			System.out.println("Tipo: " + campo2 + "\r\n");
			testCase.addTextEvidenceCurrentStep("--> " + " Documento: " + campo1 + " | " + "Tipo: " + campo2 + "\r\n");
		}

		System.out.println(
				" --------------------------------INICIA CONSULTA DE LOG----------------------------------------------- ");

		addStep("Validar el registro del inicio y el fin de la ejecucion en la transaccion en base de datos WM_INB_DOC_LOG");

		Date fechaFin;
		fechaFin = new Date();
		String fechaFinal = formatter.format(fechaFin);

		String consultaTablaLog = "SELECT WM_CODE, TO_CHAR(START_DATE, 'dd/mm/yyyy hh24:mi:ss')START_DATE, OPERATION, CR_PLAZA, CR_TIENDA "
				+ "FROM WM_INB_DOC_LOG\r\n" + "WHERE START_DATE >= TRUNC(SYSDATE) AND START_DATE " + "BETWEEN TO_DATE('"
				+ fechaInicial + "','dd/mm/yyyy hh24:mi:ss') AND TO_DATE('" + fechaFinal
				+ "','dd/mm/yyyy hh24:mi:ss') AND OPERATION = 'posConfig' ORDER BY START_DATE ASC";

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
			assertFalse(!LogResult.isBeforeFirst(), "--> ¡ERROR!, No hay ninguna ejecución de la transacción");
		}
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1.-Contar con la mensajeria para invocar la API posConfig.\r\n" + "2.-Tener instalado Postman\r\n"
				+ "3.-Tener VPN activado para conexión con las BD";
	}

	@Override
	public String setTestDescription() {
		return "Obtener documentos con la API POSCONFIG";
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