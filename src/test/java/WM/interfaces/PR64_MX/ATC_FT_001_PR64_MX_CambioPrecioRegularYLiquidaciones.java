package WM.interfaces.PR64_MX;

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
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/* DESCRIPCION : Se hacen los cambios de precios regulares y las liquidaciones.
 * 
 * ATC_FT_001_PR64MX_Ejecución exitosa para los cambios de precio regulares y liquidaciónes 
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (ITM).
 * 	Tener Ordenado el job de la interfaz PR64_MX
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con el Doc_type ITM.
 * 
 * @Author Cesar Alanis
 * @Date 23/Diciembre/2024
 */
public class ATC_FT_001_PR64_MX_CambioPrecioRegularYLiquidaciones extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_PR64_MX_CambioPrecioRegularYLiquidaciones_test(HashMap<String, String> data)
			throws Exception {

		/******************
		 * Datos de conexion
		 *********************************************************************/

		/*
		 * Datos de conexi n
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

		testCase.setProject_Name("Transformación Multiformato");
		/*****************
		 * Variables de consulta
		 ************************************************************************************/

		String tdQueryPrcHead = "SELECT LOAD_BATCH_ID FROM WMUSER.POS_PRC_HEAD WHERE WM_PRC_STATUS = 'L' AND PRICE_EMERGENCY_IND = 0 AND LOCATION = "
				+ data.get("LOCATION") + " AND LOAD_BATCH_ID = " + data.get("LOAD_BATCH_ID") + " AND BATCH_ID = "
				+ data.get("BATCH_ID");

		String tdQueryPrcHead1 = "SELECT LOAD_BATCH_ID FROM WMUSER.POS_PRC_HEAD WHERE WM_PRC_STATUS = 'E' AND PRICE_EMERGENCY_IND = 0 AND LOCATION = "
				+ data.get("LOCATION") + " AND LOAD_BATCH_ID = " + data.get("LOAD_BATCH_ID") + " AND BATCH_ID = "
				+ data.get("BATCH_ID");

		PreparedStatement preparedStatement = null;
		String run_status = "";

		/************************************
		 * Pasos del caso de Prueba
		 *******************************************/

		/********** Paso 1 *********/

		addStep("Valida que se encuentre disponible documentos para procesar de cambio de precio regular y liquidación en la tabla POS_PRC_HEAD");

		try {
			// Ejecutar la primera consulta
			PreparedStatement rmsQuery = dbRms.prepareStatement(tdQueryPrcHead);
			ResultSet rmsResult = rmsQuery.executeQuery();

			java.sql.ResultSetMetaData rsltrms = rmsResult.getMetaData();
			int columnCount = rsltrms.getColumnCount();

			if (rmsResult.next()) {
				// Iterar sobre las columnas y mostrar los valores
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = rmsResult.getString(i);
					testCase.addTextEvidenceCurrentStep(tdQueryPrcHead);
					testCase.addTextEvidenceCurrentStep("El cambio de Precio " + columnValue
							+ " existe en la tabla POS_PRC_HEAD con WM_PRC_STATUS = L");
					System.out.println("El cambio de Precio " + columnValue
							+ " existe en la tabla POS_PRC_HEAD con WM_PRC_STATUS = L");
				}

			} else {
				// Maneja el caso donde no hay más filas
				System.out.println("No se en contro información del Cambio de Precio en la tabla POS_PRC_HEAD");
				testCase.addTextEvidenceCurrentStep(
						"No se en contro información del Cambio de Precio en la tabla POS_PRC_HEAD");
				assertFalse(!rmsResult.next(),
						"No se en contro información del Cambio de Precio en la tabla POS_PRC_HEAD");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/********** Paso 2 *********/

		addStep("Ejecutar JOB PR64_MX");

		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);

		Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));
		u.hardWait(30);
		u.waitForLoadPage();
		cm.logOn();
		JSONObject obj = new JSONObject(data.get("job"));
		ControlM job = new ControlM(u, testCase, obj);
		boolean flag = job.searchJob();
		assertTrue(flag, "lo que seas");
		String resultado = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);
		System.out.println("Valor de output :" + job.getOutput());
		assertEquals(resultado, "Ended OK");
		u.hardWait(3);
		job.closeViewpoint();
		u.close();

		/********** Paso 3 *********/

		addStep("Comprobar que se registra la ejecucion en WM_LOG_RUN");

		String tdcQueryWmlog = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR64_MX'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

		PreparedStatement LogQuery = dbLog.prepareStatement(tdcQueryWmlog);
		ResultSet LogResult = LogQuery.executeQuery();

		java.sql.ResultSetMetaData rsltlog = LogResult.getMetaData();
		int columnCountLog = rsltlog.getColumnCount();

		// Procesar los resultados
		if (!LogResult.next()) {
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
		// Cerrar los recursos
		LogResult.close();
		LogQuery.close();

		/********** Paso 4 *********/

		addStep("Se valida la generacion de thread");

		String tdcQueryStatusThread = " SELECT PARENT_ID,THREAD_ID,NAME,WM_LOG_THREAD.STATUS,ATT1,ATT2"
				+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_status; // + variable

		preparedStatement = dbLog.prepareStatement(tdcQueryStatusThread);
		ResultSet LogThreadResult = preparedStatement.executeQuery();

		java.sql.ResultSetMetaData rsltlogThread = LogThreadResult.getMetaData();
		int columnCountLogThread = rsltlogThread.getColumnCount();

		if (!LogThreadResult.next()) {
			assertFalse(!LogThreadResult.next(),
					"Error!, la interfaz terminó en Status E o no tienen ejecuciones recientes");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryStatusThread);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLogThread; i++) {
				String columnName = rsltlogThread.getColumnName(i);
				String columnValue = LogThreadResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		LogThreadResult.close();
		dbLog.close();

		/********** Paso 5 *********/

		addStep("Valida que se haya actualizado el campo WM_PRC_STATUS del cambio de precio regular y liquidaciónes en la tabla POS_PRC_HEAD");

		try {
			// Ejecutar la primera consulta
			PreparedStatement rmsQuery = dbRms.prepareStatement(tdQueryPrcHead1);
			ResultSet rmsResult = rmsQuery.executeQuery();

			java.sql.ResultSetMetaData rsltrms = rmsResult.getMetaData();
			int columnCount = rsltrms.getColumnCount();

			if (rmsResult.next()) {
				// Iterar sobre las columnas y mostrar los valores
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = rmsResult.getString(i);
					testCase.addTextEvidenceCurrentStep(tdQueryPrcHead1);
					testCase.addTextEvidenceCurrentStep(
							"El cambio de Precio " + columnValue + " se actualizo en la tabla POS_PRC_HEAD");
					System.out.println("El cambio de Precio " + columnValue + " se actualizo en la tabla POS_PRC_HEAD");
				}

			} else {
				// Maneja el caso donde no hay más filas
				System.out.println("No se actualizo el cambio de precio disponible en la tabla POS_PRC_HEAD");
				testCase.addTextEvidenceCurrentStep(
						"No se actualizo el cambio de precio disponible en la tabla POS_PRC_HEAD");
				assertFalse(!rmsResult.next(),
						"No se actualizo el cambio de precio disponible en la tabla POS_PRC_HEAD");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/********** Paso 6 *********/

		addStep("Valida que se haya insertado informacion en la tabla POS_OUT_DOC_RMS");

		String ObtenerTarget = "SELECT WM_TARGET_PRC FROM WMUSER.POS_PRC_HEAD WHERE WM_PRC_STATUS = 'E' AND PRICE_EMERGENCY_IND = 0"
				+ " AND LOCATION = " + data.get("LOCATION") + " AND LOAD_BATCH_ID = " + data.get("LOAD_BATCH_ID")
				+ " AND BATCH_ID = " + data.get("BATCH_ID");

		try {

			PreparedStatement rmsQuery4 = dbRms.prepareStatement(ObtenerTarget);
			ResultSet rmsResult4 = rmsQuery4.executeQuery();

			String DOC_NAME = "";
			String Docs = "";

			while (rmsResult4.next()) {
				DOC_NAME = rmsResult4.getString("WM_TARGET_PRC");
				System.out.println("DOC_NAME: " + DOC_NAME);

				DOC_NAME = "'" + DOC_NAME + "',";
				Docs = Docs + DOC_NAME;
				System.out.println("Documento recuperado: " + DOC_NAME);
			}
			Docs = Docs.substring(0, Docs.length() - 1);

			String GetDoc = "SELECT DOC_NAME FROM POSUSER.POS_OUT_DOC_RMS WHERE STATUS = 'L' AND DOC_NAME =" + Docs
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

			testCase.addTextEvidenceCurrentStep(ObtenerTarget);
			testCase.addTextEvidenceCurrentStep("Se encontro el documento " + DOC_NAME2 + " pendiendte por procesar");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		/********** Paso 7 *********/

		addStep("Se valida el documento en el repositorio FTP");

		String queryGetWmTarget = "SELECT WM_TARGET_PRC FROM WMUSER.POS_PRC_HEAD WHERE WM_PRC_STATUS = 'E' AND PRICE_EMERGENCY_IND = 0"
				+ " AND LOCATION = " + data.get("LOCATION") + " AND LOAD_BATCH_ID = " + data.get("LOAD_BATCH_ID")
				+ " AND BATCH_ID = " + data.get("BATCH_ID");

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
				String ruta = "/u01/posuser/FEMSA_OXXO/POS/" + data.get("plaza") + "/" + data.get("tienda")
						+ "/working/" + columnValue; /* Variable con el nombre del documento */

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
		return "Se hacen los cambios de precios regulares y las liquidaciones";
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
		return "1. Contar con acceso al Aplicativo de RMS, \n\r"
				+ "2. Contar con acceso a la BD de RMS DAS, WMLOG y POSUSER del ambiente de Nucleo, \n\r"
				+ "3. Contar con acceso al repositorio Filezilla, \n\r"
				+ "4. Validar que la Plaza se encuentren migrada en la tabla POS_RULES_DOC_CFG con un valor de Y en el campo MIGRATED_FLAG_OUT.";
	}

}
