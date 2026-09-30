package WM.interfaces.pr25;

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
import utils.FTPUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/* NOMBRE DEL PROYECTO: Transformacion Multiformato
 * DESCRIPCION : Se envia el archivo POD a traves de Carga Masiva.
 * 
 * MTC-FT-014-Envio de archivo POD a POS(Carga Masiva)
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (POD).
 * 	Tener Ordenado el job de la interfaz PR25_OLD
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos de tipo POD.
 * 
 * @Author Cesar Alanis
 * @Date 06/Diciembre/2024
 */
public class ATC_FT_006_PR25_EnvioDeArchivoPodAPosCargaMasivaOLD extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_PR25_EnvioDeArchivoPodAPosCargaMasivaOLD_test(HashMap<String, String> data)
			throws Exception {

		/*
		 * Datos de conexi n
		 *********************************************************************/
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		/* POSUSER */
		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_WM_MEX_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A RMS10----------------------------------------------- ");
		/* RMS10 */
		Connection dbRms = DriverManager.getConnection(GlobalVariables.DB_HOST_RMS10_MEX_MULTIFORMATO3,
				GlobalVariables.DB_USER_RMS10_MEX_MULTIFORMATO3, GlobalVariables.DB_PASSWORD_RMS10_MEX_MULTIFORMATO3);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A RMS10----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		/* WMLOG */
		Connection dbLog = DriverManager.getConnection(GlobalVariables.DB_HOST_LOG_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_LOG_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_LOG_MEX_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG----------------------------------------------- ");

		testCase.setProject_Name("Transformacion Multiformato");

		/*****************
		 * Variables de consulta
		 ************************************************************************************/

		String tdcQueryInfoOrdHead = "SELECT ORDER_NO FROM WMUSER.ORDHEAD WHERE ORDER_NO IN(" + data.get("order_number")
				+ ")";
		;

		String tdQuerycInfOrdLoc = "SELECT ORDER_NO FROM WMUSER.ORDLOC WHERE ORDER_NO IN(" + data.get("order_number")
				+ ")";
		;

		String tdcQueryWmlog = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR25main'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

		String tdcQueryStatusThread = " SELECT PARENT_ID,THREAD_ID,NAME,WM_LOG_THREAD.STATUS,ATT1,ATT2"
				+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = ?";

		String tdcQueryOrdenesHead = "SELECT ORDER_NUMBER FROM WMUSER.ORDENES_HEAD WHERE ORDER_NUMBER IN("
				+ data.get("order_number") + ")";

		String tdcQueryOrdenesControl = "SELECT ORDER_NUMBER FROM WMUSER.ORDENES_CONTROL WHERE WM_STATUS = 'E' AND ORDER_NUMBER IN("
				+ data.get("order_number") + ")";

		PreparedStatement preparedStatement = null;
		String run_status = "";
		String loadBatchId = "";

		/************************************
		 * Pasos del caso de Prueba
		 *******************************************/

		/********** Paso 1 *********/

		addStep("Valida que se haya creado la orden de compra en la tabla ORDHEAD y la información de la OC en la tabla ORDLOC");

		try {
			// Ejecutar la primera consulta
			PreparedStatement rmsQuery = dbRms.prepareStatement(tdcQueryInfoOrdHead);
			ResultSet rmsResult = rmsQuery.executeQuery();

			java.sql.ResultSetMetaData rsltrms = rmsResult.getMetaData();
			int columnCount = rsltrms.getColumnCount();

			if (rmsResult.next()) {
				// Iterar sobre las columnas y mostrar los valores
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = rmsResult.getString(i);
					testCase.addTextEvidenceCurrentStep(tdcQueryInfoOrdHead);
					testCase.addTextEvidenceCurrentStep("La orden " + columnValue + " existe en la tabla ORDHEAD");
					System.out.println("La orden " + columnValue + " existe en la tabla ORDHEAD");
				}

			} else {
				// Maneja el caso donde no hay más filas
				System.out.println("No se en contro información de la OC en la tabla ORDHEAD");
				testCase.addTextEvidenceCurrentStep("No se en contro información de la OC en la tabla ORDHEAD");
				assertFalse(!rmsResult.next(), "No se en contro información de la OC en la tabla ORDHEAD");

			}
			// Ejecutar la segunda consulta
			PreparedStatement rmsQuery1 = ((Connection) dbRms).prepareStatement(tdQuerycInfOrdLoc);
			ResultSet rmsResult1 = rmsQuery1.executeQuery();

			java.sql.ResultSetMetaData rsltrms1 = rmsResult1.getMetaData();
			int columnCount1 = rsltrms1.getColumnCount();

			if (rmsResult1.next()) {
				// Iterar sobre las columnas y mostrar los valores
				for (int i = 1; i <= columnCount1; i++) {
					String columnValue1 = rmsResult1.getString(i);
					testCase.addTextEvidenceCurrentStep(tdQuerycInfOrdLoc);
					testCase.addTextEvidenceCurrentStep("La orden " + columnValue1 + " existe en la tabla ORDLOC");
					System.out.println("La orden " + columnValue1 + " existe en la tabla ORDLOC");

				}

			} else {
				// Maneja el caso donde no hay más filas
				testCase.addTextEvidenceCurrentStep("No se en contro información de la OC en la tabla ORDLOC");
				assertFalse(!rmsResult1.next(), "No se en contro información de la OC en la tabla ORDLOC");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/********** Paso 2 *********/

		addStep("Ejecutar JOB runPR25_OLD");

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn();

		JSONObject obj = new JSONObject(data.get("job"));
		ControlM control = new ControlM(u, testCase, obj);
		boolean flag = control.searchJob();
		assertTrue(flag, "No se encontro el job");
		String resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);
		System.out.println("Valor del output:" + control.getOutput());
		assertEquals(resultado, "Ended OK");
		u.hardWait(3);
		control.closeViewpoint();
		u.close();

		/********** Paso 3 *********/

		addStep("Comprobar que se registra la ejecucion en WMLOG");

		PreparedStatement LogQuery = dbLog.prepareStatement(tdcQueryWmlog);
		ResultSet LogResult = LogQuery.executeQuery();

		java.sql.ResultSetMetaData rsltlog = LogResult.getMetaData();
		int columnCountLog = rsltlog.getColumnCount();

		// Procesar los resultados
		if (!LogResult.next() && LogResult.getString("STATUS") != "S") {
			testCase.addTextEvidenceCurrentStep(
					"Error!, la interfaz terminó en Status E o no tienen ejecuciones recientes");
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

		preparedStatement = dbLog.prepareStatement(tdcQueryStatusThread);
		preparedStatement.setString(1, run_status);

		ResultSet LogThreadResult = preparedStatement.executeQuery();

		java.sql.ResultSetMetaData rsltlogThread = LogThreadResult.getMetaData();
		int columnCountLogThread = rsltlogThread.getColumnCount();

		if (LogThreadResult.next()) {
			testCase.addTextEvidenceCurrentStep("Hay registros de threads levantados");
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLogThread; i++) {
				String columnName = rsltlogThread.getColumnName(i);
				String columnValue = LogThreadResult.getString(i);
				testCase.addTextEvidenceCurrentStep(tdcQueryStatusThread);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println();
		} else {
			System.out.println("Success!");
			testCase.addTextEvidenceCurrentStep("Success!");
		}

		/********** Paso 5 *********/

		addStep("Valida que se haya insertado la información en las tablas ORDENES_HEAD, ORDENES_CONTROL, ORDENES_DETAIL_SK y ORDENES_DETAIL_LC");

		testCase.addTextEvidenceCurrentStep("ORDENES_HEAD");

		try {
			// Ejecutar la primera consulta
			PreparedStatement rmsQuery = dbRms.prepareStatement(tdcQueryOrdenesHead);
			ResultSet rmsResult = rmsQuery.executeQuery();

			java.sql.ResultSetMetaData rsltrms = rmsResult.getMetaData();
			int columnCount = rsltrms.getColumnCount();

			if (rmsResult.next()) {
				// Iterar sobre las columnas y mostrar los valores
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = rmsResult.getString(i);
					testCase.addTextEvidenceCurrentStep(tdcQueryOrdenesHead);
					testCase.addTextEvidenceCurrentStep(
							"Se encontró información para la OC " + columnValue + " en la tabla ORDENES_HEAD");
					System.out
							.println("Se encontró información para la OC " + columnValue + " en la tabla ORDENES_HEAD");
				}
			} else {
				// Maneja el caso donde no hay más filas
				testCase.addTextEvidenceCurrentStep("No se encontró información de la OC en Ordenes_Head");
				assertFalse(!rmsResult.next(), "No se encontró información de la OC en Ordenes_Head");

			}

			testCase.addTextEvidenceCurrentStep("ORDENES_CONTROL");

			// Ejecutar la segunda consulta
			PreparedStatement rmsQuery1 = dbRms.prepareStatement(tdcQueryOrdenesControl);
			ResultSet rmsResult1 = rmsQuery1.executeQuery();
			java.sql.ResultSetMetaData rsltrms1 = rmsResult1.getMetaData();
			int columnCount5 = rsltrms1.getColumnCount();

			if (rmsResult1.next()) {
				// Iterar sobre las columnas y mostrar los valores
				for (int i = 1; i <= columnCount5; i++) {
					String columnName = rsltrms1.getColumnName(i);
					String columnValue = rmsResult1.getString(i);
					testCase.addTextEvidenceCurrentStep(tdcQueryOrdenesControl);
					testCase.addTextEvidenceCurrentStep(
							"Se encontró información para la OC en la tabla ORDENES_CONTROL con " + columnName + ": "
									+ columnValue + " y status = E");
					System.out.println("Se encontró información para la OC en la tabla ORDENES_CONTROL con "
							+ columnName + ": " + columnValue + " y status = E");
				}
			} else {
				testCase.addTextEvidenceCurrentStep("No se encontró información de la OC en ORDENES_CONTROL");
				assertFalse(!rmsResult1.next(), "No se encontró información de la OC en ORDENES_CONTROL");

			}

			// Obtener LOAD_BATCH_ID
			String queryLoadBatchId = "SELECT LOAD_BATCH_ID FROM WMUSER.ORDENES_CONTROL WHERE ORDER_NUMBER ="
					+ data.get("order_number");
			PreparedStatement loadBatchIdQuery = dbRms.prepareStatement(queryLoadBatchId);
			ResultSet loadBatchIdResult = loadBatchIdQuery.executeQuery();

			if (loadBatchIdResult.next()) {
				loadBatchId = loadBatchIdResult.getString("LOAD_BATCH_ID");
				testCase.addTextEvidenceCurrentStep(queryLoadBatchId);
				System.out.println("LOAD_BATCH_ID obtenido: " + loadBatchId);
				testCase.addTextEvidenceCurrentStep("LOAD_BATCH_ID obtenido: " + loadBatchId);
			} else {
				testCase.addTextEvidenceCurrentStep("No se encontró LOAD_BATCH_ID para la OC");
				assertFalse(!loadBatchIdResult.next(), "No se encontró LOAD_BATCH_ID para la OC");

			}

			// Verificar si loadBatchId tiene valores
			if (loadBatchId != null && !loadBatchId.isEmpty()) {

				String tdcQueryOrdenesDetailSk = "SELECT LOAD_BATCH_ID FROM WMUSER.ORDENES_DETAIL_SK WHERE LOAD_BATCH_ID IN ("
						+ loadBatchId + ")";
				String tdcQueryOrdenesDetailLc = "SELECT LOAD_BATCH_ID FROM WMUSER.ORDENES_DETAIL_LC WHERE LOAD_BATCH_ID IN ("
						+ loadBatchId + ")";

				testCase.addTextEvidenceCurrentStep("ORDENES_DETAIL_SK");

				// Ejecutar la tercera consulta
				PreparedStatement rmsQuery2 = dbRms.prepareStatement(tdcQueryOrdenesDetailSk);
				ResultSet rmsResult2 = rmsQuery2.executeQuery();

				java.sql.ResultSetMetaData rsltrms2 = rmsResult2.getMetaData();
				int columnCount2 = rsltrms2.getColumnCount();

				if (rmsResult2.next()) {
					// Iterar sobre las columnas y mostrar los valores
					for (int i = 1; i <= columnCount2; i++) {

						testCase.addTextEvidenceCurrentStep(tdcQueryOrdenesDetailSk);
						testCase.addTextEvidenceCurrentStep("Se encontró información de la OC en ORDENES_DETAIL_SK");
						System.out.println("Se encontró información de la OC en ORDENES_DETAIL_SK");

					}
				} else {
					// Maneja el caso donde no hay más filas
					testCase.addTextEvidenceCurrentStep("No se encontró información de la OC en ORDENES_DETAIL_SK");
					assertFalse(!rmsResult2.next(), "No se encontró información de la OC en ORDENES_DETAIL_SK");

				}

				testCase.addTextEvidenceCurrentStep("ORDENES_DETAIL_LC");

				// Ejecutar la cuarta consulta
				PreparedStatement rmsQuery3 = dbRms.prepareStatement(tdcQueryOrdenesDetailLc);
				ResultSet rmsResult3 = rmsQuery3.executeQuery();

				java.sql.ResultSetMetaData rsltrms3 = rmsResult3.getMetaData();
				int columnCount8 = rsltrms3.getColumnCount();

				if (rmsResult3.next()) {
					// Iterar sobre las columnas y mostrar los valores
					for (int i = 1; i <= columnCount8; i++) {

						testCase.addTextEvidenceCurrentStep(tdcQueryOrdenesDetailLc);
						System.out.println("Se encontró información de la OC en ORDENES_DETAIL_LC");
						testCase.addTextEvidenceCurrentStep("Se encontró información de la OC en ORDENES_DETAIL_LC");
					}
				} else {
					// Maneja el caso donde no hay más filas
					testCase.addTextEvidenceCurrentStep("No se encontró información de la OC en ORDENES_DETAIL_LC");
					assertFalse(!rmsResult3.next(), "No se encontró información de la OC en ORDENES_DETAIL_LC");

				}
			} else {
				// Manejar el caso donde loadBatchId está vacío
				testCase.addTextEvidenceCurrentStep("No hay valores para LOAD_BATCH_ID");
				assertFalse(!LogResult.next(), "No hay valores para LOAD_BATCH_ID");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		/********** Paso 6 *********/

		addStep("Valida que se haya insertado informacion en la tabla POS_OUTBOUND_DOCS");

		String ObtenerDoc = "SELECT WM_TARGET FROM WMUSER.ORDENES_CONTROL WHERE WM_STATUS = 'E' AND LOAD_BATCH_ID ="
				+ loadBatchId + "  AND ORDER_NUMBER=" + data.get("order_number") + "";

		try {

			PreparedStatement rmsQuery4 = dbRms.prepareStatement(ObtenerDoc);
			ResultSet rmsResult4 = rmsQuery4.executeQuery();

			String DOC_NAME = "";
			String Docs = "";

			while (rmsResult4.next()) {
				DOC_NAME = rmsResult4.getString("WM_TARGET");
				System.out.println("DOC_NAME: " + DOC_NAME);

				DOC_NAME = "'" + DOC_NAME + "',";
				Docs = Docs + DOC_NAME;
				System.out.println("Documento recuperado: " + DOC_NAME);
			}
			Docs = Docs.substring(0, Docs.length() - 1);

			String GetDoc = "SELECT DOC_NAME FROM POSUSER.POS_OUTBOUND_DOCS WHERE STATUS = 'L' AND DOC_NAME =" + Docs
					+ "";

			String DOC_NAME2 = "";

			PreparedStatement rmsQuery5 = dbPos.prepareStatement(GetDoc);
			ResultSet rmsResult5 = rmsQuery5.executeQuery();

			while (rmsResult5.next()) {
				DOC_NAME2 = rmsResult5.getString("DOC_NAME");

				System.out.println("Se encontro el documento " + DOC_NAME2 + " pendiendte por procesar");
				assertFalse(!rmsResult5.next(),
						"Error!, La interfaz no recupero el documento" + DOC_NAME2 + "pendiente");
			}
			testCase.addTextEvidenceCurrentStep(GetDoc);
			testCase.addTextEvidenceCurrentStep("Se encontro el documento " + DOC_NAME2 + " pendiendte por procesar");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/********** Paso 7 *********/

		addStep("Se valida el documento en el repositorio FTP");

		String queryGetWmTarget = "SELECT WM_TARGET FROM WMUSER.ORDENES_CONTROL WHERE WM_STATUS = 'E' AND ORDER_NUMBER="
				+ data.get("order_number") + "";

		PreparedStatement rmsQuery = dbRms.prepareStatement(queryGetWmTarget);
		ResultSet rmsResult = rmsQuery.executeQuery();

		java.sql.ResultSetMetaData rsltrms = rmsResult.getMetaData();
		int columnCount = rsltrms.getColumnCount();

		if (rmsResult.next()) {
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount; i++) {
				String columnValue = rmsResult.getString(i);

				FTPUtil ftp = new FTPUtil("" + data.get("ftpHost") + "", 21, "" + data.get("ftpUser") + "",
						"" + data.get("ftpPass") + ""); // host, puerto, usuario, contrasena
				String ruta = "/FEMSA_OXXO/POS/" + data.get("plaza") + "/" + data.get("tienda") + "/working/"
						+ columnValue; /* Variable con el nombre del documento */

				if (ftp.fileExists(ruta)) {

					testCase.addTextEvidenceCurrentStep(queryGetWmTarget);
					testCase.addFileEvidenceCurrentStep(ruta);
					testCase.addTextEvidenceCurrentStep("Se encontro el documento " + columnValue
							+ " pendiente por procesar en el repositorio FTP");
					testCase.addTextEvidenceCurrentStep(ruta);
					System.out.println("Se encontro el documento " + columnValue
							+ " pendiente por procesar en el repositorio FTP");
				} else {

					testCase.addFileEvidenceCurrentStep(ruta);
					testCase.addTextEvidenceCurrentStep("No se encontro el documento " + columnValue
							+ " pendiente por procesar en el repositorio FTP");
					System.out.println("No se encontro el documento " + columnValue
							+ " pendiente por procesar en el repositorio FTP");
					assertFalse(!rmsResult.next(), "No se encontro el documento " + columnValue
							+ " pendiente por procesar en el repositorio FTP");

				}
			}

		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Validar el envio del Archivo POD a POS ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Cesar Alanis";
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
		return "1. Contar con acceso al Aplicativo de RMS, \n\r" + ""
				+ "2. Contar con acceso a la BD de RMS10, WMLOG y POSUSER, \n\r" + ""
				+ "3. Contar con acceso al repositorio Filezilla, \n\r" + ""
				+ "4. Validar que la Plaza se encuentren migrada en la tabla POS_RULES_DOC_CFG con un valor de N en el campo MIGRATED_FLAG_OUT, \n\r"
				+ ""
				+ "5. Cargar datos de carga masiva pendiente por procesar en XXFC_MODULOS_CARGOS y extraer el campo ORDEN_NO.";
	}

}
