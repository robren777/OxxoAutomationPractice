package WM.interfaces.PR25_MX;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/* DESCRIPCION : Validar el envio del Archivo POD a POS.
 * 
 * MTC-FT-02 "Nueva Arquitectura (ENRED_OUT)
 * Ejecutar JOB runPR25_MX para procesar correctamente los archivos POD y depositarlos en el repositorio de POS(Pantalla RMS puchase order)"
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (POD).
 * 	Tener Ordenado el job de la interfaz PR25_MX
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con la ORDEN de compra.
 * 
 * @Author Rafael Alarcón
 * @Date 05/Diciembre/2024
 */
public class ATC_FT_002_PR25MX_EnvioDeArchivoPODaPosPurchaseOrder extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_PR25MX_EnvioDeArchivoPODaPosPurchaseOrder_test(HashMap<String, String> data)
			throws Exception {

		testCase.setProject_Name("Transformacion multiformato");
		/******************
		 * Datos de conexion
		 *********************************************************************/

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		/* POSUSER */
		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WM_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A RMS10----------------------------------------------- ");
		/* RMS10 */
		Connection dbRms = DriverManager.getConnection(GlobalVariables.DB_HOST_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_DASPERF_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A RMS10----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		/* WMLOG */
		Connection dbLog = DriverManager.getConnection(GlobalVariables.DB_HOST_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WMLOG_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG----------------------------------------------- ");

		/****************************
		 * Variables de datos
		 ***********************************/

		PreparedStatement preparedStatement = null;
		String run_status = "";

		/************************************
		 * Pasos del caso de Prueba
		 *******************************************/

		/********** Paso 1 *********/

		addStep("Valida que se haya creado la orden de compra en la tabla ORDHEAD y la información de la OC en la tabla ORDLOC");

		try {
			String tdcQueryInfoOrdHead = "SELECT ORDER_NO FROM RMS.ORDHEAD WHERE ORDER_NO =" + data.get("order_number")
					+ "";
			// Ejecutar la primera consulta
			PreparedStatement rmsQuery = dbRms.prepareStatement(tdcQueryInfoOrdHead);
			ResultSet rmsResult = rmsQuery.executeQuery();

			java.sql.ResultSetMetaData rsltrms = rmsResult.getMetaData();
			int columnCount = rsltrms.getColumnCount();

			if (rmsResult.next()) {
				testCase.addTextEvidenceCurrentStep(tdcQueryInfoOrdHead);
				// Iterar sobre las columnas y mostrar los valores
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = rmsResult.getString(i);
					testCase.addTextEvidenceCurrentStep("La orden " + columnValue + " existe en la tabla ORDHEAD");
				}

			} else {
				// Maneja el caso donde no hay más filas
				assertFalse(!rmsResult.next(), "No se en contro información de la OC en la tabla ORDHEAD");

			}

			String tdQuerycInfOrdLoc = "SELECT ORDER_NO FROM RMS.ORDLOC WHERE ORDER_NO = " + data.get("order_number")
					+ "";
			// Ejecutar la segunda consulta
			PreparedStatement rmsQuery1 = ((Connection) dbRms).prepareStatement(tdQuerycInfOrdLoc);
			ResultSet rmsResult1 = rmsQuery1.executeQuery();

			java.sql.ResultSetMetaData rsltrms1 = rmsResult1.getMetaData();
			int columnCount1 = rsltrms1.getColumnCount();

			if (rmsResult1.next()) {
				testCase.addTextEvidenceCurrentStep(tdQuerycInfOrdLoc);
				// Iterar sobre las columnas y mostrar los valores
				for (int i = 1; i <= columnCount1; i++) {
					String columnValue1 = rmsResult1.getString(i);
					testCase.addTextEvidenceCurrentStep("La orden " + columnValue1 + " existe en la tabla ORDLOC");
				}
			} else {
				// Maneja el caso donde no hay más filas
				assertFalse(!rmsResult1.next(), "No se en contro información de la OC en la tabla ORDLOC");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/********** Paso 2 *********/

		addStep("Ejecutar el job PR25_MX");

		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);

		Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));
		u.hardWait(30);
		u.waitForLoadPage();
		cm.logOn();
		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job = new ControlM(u, testCase, obj);
		boolean flag = job.searchJob();
		assertTrue(flag, "Bandera si encontro el job");
		String resultado = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);
		System.out.println("Valor de output : " + job.getOutput());
		assertEquals(resultado, "Ended OK");
		u.hardWait(3);
		job.closeViewpoint();
		u.close();

		/********** Paso 3 *********/

		addStep("Comprobar que se registra la ejecucion en WMLOG");

		String tdcQueryWmlog = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR25_MX'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

		PreparedStatement LogQuery = dbLog.prepareStatement(tdcQueryWmlog);
		ResultSet LogResult = LogQuery.executeQuery();

		java.sql.ResultSetMetaData rsltlog = LogResult.getMetaData();
		int columnCountLog = rsltlog.getColumnCount();

		// Procesar los resultados
		if (!LogResult.next() && LogResult.getString("STATUS") != "S") {
			assertFalse(!LogResult.next(), "Error!, la interfaz terminó en Status E o no tienen ejecuciones recientes");

		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryWmlog);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLog; i++) {
				String columnName = rsltlog.getColumnName(i);
				String columnValue = LogResult.getString(i);
				run_status = LogResult.getString("RUN_ID");
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}

		/********** Paso 4 *********/

		addStep("Se validan THREADS");

		String tdcQueryStatusThread = " SELECT PARENT_ID,THREAD_ID,NAME,WM_LOG_THREAD.STATUS,ATT1,ATT2"
				+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_status;

		preparedStatement = dbLog.prepareStatement(tdcQueryStatusThread);
		ResultSet LogThreadResult = preparedStatement.executeQuery();

		java.sql.ResultSetMetaData rsltlogThread = LogThreadResult.getMetaData();
		int columnCountLogThread = rsltlogThread.getColumnCount();

		if (LogThreadResult.next()) {
			testCase.addTextEvidenceCurrentStep(tdcQueryStatusThread);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLogThread; i++) {
				String columnName = rsltlogThread.getColumnName(i);
				String columnValue = LogThreadResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryStatusThread);
			assertFalse(!LogThreadResult.next(), "No hay registros de threads levantados");
		}

		/********** Paso 5 *********/

		addStep("Valida que se haya insertado y prcesado la información en las tablas ORDENES_HEAD, ORDENES_DETAIL");

		testCase.addTextEvidenceCurrentStep("ORDENES_HEAD");

		try {

			String tdcQueryOrdenesHead = "SELECT ORDER_NUMBER FROM EO_RMS.ORDENES_HEAD WHERE ORDER_NUMBER IN("
					+ data.get("order_number") + ")";
			// Ejecutar la primera consulta
			PreparedStatement rmsQuery = dbRms.prepareStatement(tdcQueryOrdenesHead);
			ResultSet rmsResult = rmsQuery.executeQuery();

			java.sql.ResultSetMetaData rsltrms = rmsResult.getMetaData();
			int columnCount = rsltrms.getColumnCount();

			if (rmsResult.next()) {
				testCase.addTextEvidenceCurrentStep(tdcQueryOrdenesHead);
				// Iterar sobre las columnas y mostrar los valores
				for (int i = 1; i <= columnCount; i++) {
					String columnName = rsltrms.getColumnName(i);
					String columnValue = rmsResult.getString(i);
					testCase.addTextEvidenceCurrentStep(
							"Se encontró información para la OC en la tabla ORDENES_HEAD con " + columnName + ": "
									+ columnValue + " y status = E");
				}
			} else {
				// Maneja el caso donde no hay más filas
				assertFalse(!rmsResult.next(), "No se encontró información de la OC en Ordenes_Head");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String tdcQueryOrdenesDetail = "SELECT ORDER_NUMBER FROM EO_RMS.ORDENES_DETAIL WHERE ORDER_NUMBER IN('"
				+ data.get("order_number") + "')";

		// Ejecutar la segunda consulta
		PreparedStatement rmsQuery1 = dbRms.prepareStatement(tdcQueryOrdenesDetail);
		// *rmsQuery1.setString(1, data.get("order_number")); // Usar el número de orden
		// del mapa de datos
		ResultSet rmsResult1 = rmsQuery1.executeQuery();
		java.sql.ResultSetMetaData rsltrms1 = rmsResult1.getMetaData();
		int columnCount5 = rsltrms1.getColumnCount();

		if (rmsResult1.next()) {
			testCase.addTextEvidenceCurrentStep("ORDENES_DETAIL");
			testCase.addTextEvidenceCurrentStep(tdcQueryOrdenesDetail);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount5; i++) {
				String columnName = rsltrms1.getColumnName(i);
				String columnValue = rmsResult1.getString(i);
				testCase.addTextEvidenceCurrentStep("Se encontró información para la OC en la tabla ORDENES_DETAIL con "
						+ columnName + ": " + columnValue + " y status = E");
			}
		} else {
			assertFalse(!rmsResult1.next(), "No se encontró información de la OC en ORDENES_CONTROL");
		}

		/********** Paso 6 *********/

		addStep("Valida que se haya insertado informacion en la tabla POS_OUT_DOC_RMS");

		String ObtenerDoc = "SELECT WM_TARGET FROM EO_RMS.ORDENES_HEAD WHERE WM_STATUS = 'E' AND ORDER_NUMBER="
				+ data.get("order_number") + "";

		try {

			PreparedStatement rmsQuery4 = dbRms.prepareStatement(ObtenerDoc);
			ResultSet rmsResult4 = rmsQuery4.executeQuery();

			String DOC_NAME = "";
			String Docs = "";

			testCase.addTextEvidenceCurrentStep(ObtenerDoc);

			while (rmsResult4.next()) {
				DOC_NAME = rmsResult4.getString("WM_TARGET");

				testCase.addTextEvidenceCurrentStep("WM_TARGET :" + rmsResult4.getString("WM_TARGET"));

				DOC_NAME = "'" + DOC_NAME + "',";
				Docs = Docs + DOC_NAME;
			}
			Docs = Docs.substring(0, Docs.length() - 1);

			String GetDoc = "SELECT DOC_NAME FROM POSUSER.POS_OUT_DOC_RMS WHERE STATUS = 'L' AND DOC_NAME =" + Docs;

			String DOC_NAME2 = "";

			testCase.addTextEvidenceCurrentStep(GetDoc);

			PreparedStatement rmsQuery5 = dbPos.prepareStatement(GetDoc);
			ResultSet rmsResult5 = rmsQuery5.executeQuery();

			while (rmsResult5.next()) {
				DOC_NAME2 = rmsResult5.getString("DOC_NAME");
				assertFalse(!rmsResult5.next(),
						"Error!, La interfaz no recupero el documento" + DOC_NAME2 + "pendiente");
			}
			testCase.addTextEvidenceCurrentStep("Se encontro el documento " + DOC_NAME2 + " pendiente por procesar");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar el envio del Archivo POD a POS";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Rafael Alarcon";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1. Contar con acceso al Aplicativo de RMS, \n\r"
				+ "2. Contar con acceso a la BD de RMS16, WMLOG y POSUSER, \n\r"
				+ "3. Contar con acceso al repositorio Filezilla, \n\r"
				+ "4. Validar que la Plaza se encuentren migrada en la tabla POS_RULES_DOC_CFG con un valor de Y en el campo MIGRATED_FLAG_OUT.";
	}
}