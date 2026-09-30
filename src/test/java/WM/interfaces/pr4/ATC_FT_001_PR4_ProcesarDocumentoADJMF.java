package WM.interfaces.pr4;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.JobManagement;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/* DESCRIPCION : Procesar Ajustes de inventario para el envío de información a RMS y EBS.
 * 
 * MTC-FT-001- Procesar documentos pendientes de tipo ADJ
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (ADJ) de los tipos de ajuste 150, 160, 514, 532.
 * 	Tener Ordenado el job de la interfaz PR4
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con el ajuste ADJ (Un ajuste por documento).
 * 
 * @Author Rafael Alarcón
 * @Date 18/Julio/2024
 */
public class ATC_FT_001_PR4_ProcesarDocumentoADJMF extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_PR4_ProcesarDocumentoADJMF_test(HashMap<String, String> data) throws Exception {

		/*
		 * Datos de conexion
		 *********************************************************************/
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

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A RMS10----------------------------------------------- ");
		/* RMS10 */
		Connection dbRms = DriverManager.getConnection(GlobalVariables.DB_HOST_RMS10_MEX_MULTIFORMATO3,
				GlobalVariables.DB_USER_RMS10_MEX_MULTIFORMATO3, GlobalVariables.DB_PASSWORD_RMS10_MEX_MULTIFORMATO3);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A RMS10----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A RMS10 v2----------------------------------------------- ");
		/* RMS10 V2 */
		utils.sql.SQLUtil dRms2 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS10_MEX_MULTIFORMATO,
				GlobalVariables.DB_USER_RMS10_MEX_MULTIFORMATO, GlobalVariables.DB_PASSWORD_RMS10_MEX_MULTIFORMATO);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A RMS10 v2----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A EBS----------------------------------------------- ");
		/* EBS */
		utils.sql.SQLUtil dbEbs = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_EBS_MULTIFORMATO,
				GlobalVariables.DB_USER_EBS_MULTIFORMATO, GlobalVariables.DB_PASSWORD_EBS_MULTIFORMATO);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A EBS----------------------------------------------- ");

		testCase.setProject_Name("Transformación Multiformato");
		/**/
		/*****************
		 * Variables de consulta
		 *****************************************************************************************/

		String queryPrueba2 = "SELECT DISTINCT * FROM (SELECT ITEM, ITEM_DESC, TRANSACTION_ID, TRAN_DATE, TRAN_CODE, FROM_LOC, LAST_UPDATE_DATETIME, REFERENCE_3 FROM FEM_FIF_STG WHERE ITEM IN (%s)\r\n"
				+ "AND TRAN_CODE = '22' AND STORE = '44436' ORDER BY LAST_UPDATE_DATETIME DESC) where rownum <=25 ORDER BY LAST_UPDATE_DATETIME DESC";

		String queryEBS = "SELECT STATUS, CREATED_BY, USER_JE_CATEGORY_NAME, REFERENCE1, REFERENCE4, REFERENCE6, REFERENCE10, ATTRIBUTE14 FROM APPS.GL_INTERFACE WHERE REFERENCE6 IN (%s)";

		PreparedStatement preparedStatement = null;

		String id_doc = "";
		String run_status = "";
		String alt_id = "";
		String id_items = "";
		String items = "";
		Set<String> setValores = new HashSet<>();
		StringBuilder result = new StringBuilder();

		/**
		 * 
		 * ********************************** Pasos del caso de Prueba
		 * *****************************************
		 * 
		 * 
		 **/

		/********** Paso 1 *********/

		addStep("Validar que exista informacion de la Plaza y Tienda pendiente de procesar con antiguedad menor a 4 dias.");

		String tdcInfPlazaTienda = " SELECT DISTINCT B.ID, substr(PV_DOC_NAME,4,5) PV_CR_PLAZA, substr(PV_DOC_NAME,9,5) PV_CR_TIENDA, PV_DOC_NAME \r\n"
				+ " FROM POSUSER.POS_ADJ C," + " POSUSER.POS_ADJ_DETL D," + " POSUSER.POS_INB_DOC_RMS B"
				+ " JOIN POSUSER.PLAZAS P ON SUBSTR(B.PV_DOC_NAME,4,5) = P.CR_PLAZA AND P.PAIS = 'MEX'"
				+ " WHERE B.STATUS = 'I'" + " AND   B.DOC_TYPE = 'ADJ'" + " AND   C.PID_ID = B.ID"
				+ " AND   D.PID_ID = B.ID" + " AND   D.ADJ_QTY <> 0" + " AND B.PARTITION_DATE > SYSDATE -4";

		PreparedStatement posQuery = dbPos.prepareStatement(tdcInfPlazaTienda);
		ResultSet posResult = posQuery.executeQuery();

		java.sql.ResultSetMetaData rsltpos = posResult.getMetaData();
		int columnCount = rsltpos.getColumnCount();

		// Procesar los resultados
		if (!posResult.next()) {
			assertFalse(!posResult.next(), "No hay documentos de tipo ADJ disponibles");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcInfPlazaTienda);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsltpos.getColumnName(i);
				String columnValue = posResult.getString(i);
				id_doc = posResult.getString("ID"); // Se recupera el id del documento en una variable global
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		posResult.close();
		posQuery.close();

		/********** Paso 2 *********/

		addStep("Validar que exista informacion relacionada a los documentos pendientes.");

		String tdcInfADj = "SELECT * FROM POSUSER.POS_ADJ WHERE PID_ID = " + id_doc; // + variable

		preparedStatement = dbPos.prepareStatement(tdcInfADj);
		ResultSet posAdjResult = preparedStatement.executeQuery();

		java.sql.ResultSetMetaData rsltposAdj = posAdjResult.getMetaData();
		int columnCountAdj = rsltposAdj.getColumnCount();

		// Procesar los resultados
		if (!posAdjResult.next()) {
			assertFalse(!posAdjResult.next(), "No hay registros relacionados al documento ADJ");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcInfADj);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountAdj; i++) {
				String columnName = rsltposAdj.getColumnName(i);
				String columnValue = posAdjResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		posAdjResult.close();

		/********** Paso 3 *********/

		addStep("Validar que exista informacion relacionada a los documentos pendientes POS_ADJ_DETL.");

		String tdcInfADjDetl = "SELECT * FROM POSUSER.POS_ADJ_DETL WHERE PID_ID = " + id_doc; // + variable

		preparedStatement = dbPos.prepareStatement(tdcInfADjDetl);
		ResultSet posAdjDetlResult = preparedStatement.executeQuery();

		// Procesar los resultados
		if (posAdjDetlResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(tdcInfADjDetl);
			while (posAdjDetlResult.next()) {
				// Concatena los items
				id_items += "'" + posAdjDetlResult.getString("ITEM") + "'" + ",";
				// Recupera los valores de la tabla
				String PID_ID = posAdjDetlResult.getString("PID_ID");
				String ITEM = posAdjDetlResult.getString("ITEM");
				String ADJ_QTY = posAdjDetlResult.getString("ADJ_QTY");
				String REASON = posAdjDetlResult.getString("REASON");
				String PARTITION_DATE = posAdjDetlResult.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("PID_ID :" + PID_ID + " ITEM :" + ITEM + " ADJ_QTY :" + ADJ_QTY
						+ " REASON :" + REASON + " PARTITION_DATE :" + PARTITION_DATE);
			}
			items = id_items.substring(0, id_items.length() - 1);
		} else {
			assertFalse(!posAdjDetlResult.next(), "No hay registros de detalle relacionados al documento ADJ");
		}
		// Cerrar los recursos
		posAdjDetlResult.close();

		/********** Paso 4 *********/

		addStep("Validar que exista el Retek_CR de la tienda en el la vista XXFC_MAESTRO_DE_CRS_V");

		String queryRetekCR = "SELECT ORACLE_CR, ORACLE_CR_DESC, ORACLE_CR_SUPERIOR, RETEK_CR, RETEK_STATUS, RETEK_DISTRITO "
				+ "FROM XXFC.XXFC_MAESTRO_DE_CRS_V WHERE ORACLE_CR IN ('" + data.get("tienda") + "')";// Se saca la
																										// tienda del
																										// provider

		String posEbsResult2 = String.format(queryRetekCR);
		SQLResult EbsQuery2 = dbEbs.executeQuery(posEbsResult2);

		boolean EbsResult2 = EbsQuery2.isEmpty();

		if (!EbsResult2) {
			testCase.addQueryEvidenceCurrentStep(EbsQuery2);
		} else {
			testCase.addQueryEvidenceCurrentStep(EbsQuery2);
			assertFalse(EbsResult2, "No hay registros en la tabla");
		}

		/********** Paso 5 *********/

		addStep("Ejecutar  el servicio de la interfaz PR4");
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);

		Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));
		u.hardWait(30);
		u.waitForLoadPage();
		cm.logOn();
		JSONObject obj = new JSONObject(data.get("job1"));
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

		/********** Paso 6 *********/

		addStep("Comprobar que se registra la ejecucion en WM_LOG_RUN");

		String tdcQueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR4'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

		PreparedStatement LogQuery = dbLog.prepareStatement(tdcQueryIntegrationServer);
		ResultSet LogResult = LogQuery.executeQuery();

		java.sql.ResultSetMetaData rsltlog = LogResult.getMetaData();
		int columnCountLog = rsltlog.getColumnCount();

		// Procesar los resultados
		if (!LogResult.next()) {
			assertFalse(!LogResult.next(), "Error!, la interfaz terminó en Status E o no tienen ejecuciones recientes");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryIntegrationServer);
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

		/********** Paso 7 *********/

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

		/********** Paso 8 *********/

		addStep("Validar la actualización de los documentos ADJ en la tabla POS_INB_DOC_RMS de POSUSER");

		String tdcQueryDocADJ = " SELECT DISTINCT SUBSTR(PV_DOC_NAME,4,5) PLAZA, SUBSTR(PV_DOC_NAME,9,5) TIENDA, STATUS, ID, TARGET_ALT_ID"
				+ " FROM POSUSER.POS_INB_DOC_RMS" + " WHERE STATUS = 'E'" + " AND DOC_TYPE = 'ADJ'" + " AND ID = "
				+ id_doc + " AND partition_date > SYSDATE - 4";

		preparedStatement = dbPos.prepareStatement(tdcQueryDocADJ);
		ResultSet posEResult = preparedStatement.executeQuery();

		java.sql.ResultSetMetaData rsltposE = posEResult.getMetaData();
		int columnCountposE = rsltposE.getColumnCount();

		if (!posEResult.next()) {
			assertFalse(!posEResult.next(), "Error!, el documento no cambio de Status");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryDocADJ);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountposE; i++) {
				String columnName = rsltposE.getColumnName(i);
				String columnValue = posEResult.getString(i);
				alt_id = posEResult.getString("TARGET_ALT_ID");
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		posEResult.close();
		dbPos.close();

		/********** Paso 9 *********/

		addStep("Validar la inserción del registro del documento enviado en la tabla de RTK_INBOUND_DOCS");

		String tdcQueryDocRetek = " SELECT DOC_TYPE, CREATED_DATE, CR_PLAZA, CR_TIENDA, STATUS, RTK_FILENAME FROM WMUSER.RTK_INBOUND_DOCS WHERE DOC_TYPE = 'ADJ'"
				+ " AND RTK_FILENAME = '" + alt_id + "'";

		preparedStatement = dbRms.prepareStatement(tdcQueryDocRetek);
		ResultSet posRtkResult = preparedStatement.executeQuery();

		java.sql.ResultSetMetaData rsltRtk = posRtkResult.getMetaData();
		int columnCountRtk = rsltRtk.getColumnCount();

		// Procesar los resultados
		if (!posRtkResult.next()) {
			assertFalse(!posRtkResult.next(), "Error!, No hay registros relacionados al documento en RTK_INBOUND_DOCS");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryDocRetek);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountRtk; i++) {
				String columnName = rsltRtk.getColumnName(i);
				String columnValue = posRtkResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		posRtkResult.close();

		/********** Paso 11/12 *********/

		addStep("Ejecutar  el servicio Distributor_ADJ/Ciclo_feminvadjup");

		SeleniumUtil u2 = new SeleniumUtil(new MEdgeTest(), true);

		Control_mInicio cm2 = new Control_mInicio(u2, data.get("user"), data.get("ps"));
		u2.get(data.get("server"));
		u2.hardWait(40);
		u2.waitForLoadPage();
		cm2.logOn();
		JSONArray obj2 = new JSONArray(data.get("job2"));
		JobManagement job2 = new JobManagement(u2, testCase, obj2);
		String resultado2 = job2.jobRunner();
		System.out.println("Resultado de la ejecucion -> " + resultado2);

		u2.hardWait(3);
		u2.close();

		/********** Paso 13 *********/

		addStep("Validar que exista información en la TRAN_DATA");

		String queryTranData = "SELECT ITEM, DEPT, TRAN_CODE, TRAN_DATE, REF_NO_1, PGM_NAME, TIMESTAMP FROM TRAN_DATA  WHERE TRAN_CODE = 22 AND PGM_NAME = '"
				+ data.get("plaza") + data.get("tienda") + "'";

		preparedStatement = dbRms.prepareStatement(queryTranData);
		ResultSet posTranResult = preparedStatement.executeQuery();

		java.sql.ResultSetMetaData rsltTran = posTranResult.getMetaData();
		int columnCountTran = rsltTran.getColumnCount();

		// Procesar los resultados
		if (!posTranResult.next()) {
			assertFalse(!posTranResult.next(), "Error!, No hay registros relacionados al documento en TRAN_DATA");
		} else {
			testCase.addTextEvidenceCurrentStep(queryTranData);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountTran; i++) {
				String columnName = rsltTran.getColumnName(i);
				String columnValue = posTranResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		posTranResult.close();

		/********** Paso 14 *********/

		addStep("Ejecutar el servicio STOCK_LEDGER");

		SeleniumUtil u3 = new SeleniumUtil(new MEdgeTest(), true);

		Control_mInicio cm3 = new Control_mInicio(u3, data.get("user"), data.get("ps"));
		u3.get(data.get("server"));
		u3.hardWait(40);
		u3.waitForLoadPage();
		cm3.logOn();
		JSONArray obj3 = new JSONArray(data.get("job3"));
		JobManagement job3 = new JobManagement(u3, testCase, obj3);
		String resultado3 = job3.jobRunner();
		System.out.println("Resultado de la ejecucion -> " + resultado3);

		u3.hardWait(3);
		u3.close();

		/********** Paso 15 *********/

		addStep("Validar que exista información en la FEM_FIF_STG");

		String thr = String.format(queryPrueba2, items);
		SQLResult retekQuery = dRms2.executeQuery(thr);

		boolean retek = retekQuery.isEmpty();

		if (!retek) {
			testCase.addQueryEvidenceCurrentStep(retekQuery);
		} else {
			testCase.addQueryEvidenceCurrentStep(retekQuery);
			assertFalse(retek, "Error!, no existe información en la tabla");
		}
		/********** Paso 16 *********/

		addStep("Ejecutar el servicio RO8_AJUST_MEX y RO8_AJUST_HIST_MEX");

		SeleniumUtil u4 = new SeleniumUtil(new MEdgeTest(), true);

		Control_mInicio cm4 = new Control_mInicio(u4, data.get("user"), data.get("ps"));
		u4.get(data.get("server"));
		u4.hardWait(40);
		u4.waitForLoadPage();
		cm4.logOn();
		JSONArray obj4 = new JSONArray(data.get("job4"));
		JobManagement job4 = new JobManagement(u4, testCase, obj4);
		String resultado4 = job4.jobRunner();
		System.out.println("Resultado de la ejecucion -> " + resultado4);

		u4.hardWait(3);
		u4.close();

		/********** Paso 17 *********/

		addStep("Validar que exista información en la FEM_FIF_STG con el REFERNCE_3");

		String thr2 = String.format(queryPrueba2, items);
		SQLResult retekQuery2 = dRms2.executeQuery(thr2);

		boolean retek2 = retekQuery2.isEmpty();

		if (!retek2) {
			for (int i = 0; i < retekQuery2.getRowCount(); i++) {
				String valor = retekQuery2.getData(i, "REFERENCE_3");
				String valorC = "'" + valor + "'";
				if (setValores.add(valorC)) { // Se concatena los valores del reference 3 y valida que no se dupliquen
												// valores
					if (result.length() > 0) {
						result.append(", ");
					}
					result.append(valorC);
				}
			}
			testCase.addQueryEvidenceCurrentStep(retekQuery2);
		} else {
			testCase.addQueryEvidenceCurrentStep(retekQuery2);
			assertFalse(retek2, "Error!, no existe información en la tabla");
		}

		/********** Paso 18 *********/

		addStep("Validar que exista información en GL_INTERFACE");

		String posEbsResult = String.format(queryEBS, result.toString());
		System.out.println(posEbsResult);
		SQLResult EbsQuery = dbEbs.executeQuery(posEbsResult);

		boolean EbsResult = EbsQuery.isEmpty();

		if (!EbsResult) {
			testCase.addQueryEvidenceCurrentStep(EbsQuery);
		} else {
			testCase.addQueryEvidenceCurrentStep(EbsQuery);
			assertFalse(EbsResult, "Error!, no existe información en la tabla");
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Envio de ajustes (ADJ) hacía RMS y EBS";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jesus Rafael Alarcon Carvajal";
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
		return "1.- Tener documento ADJ pendiente por procesar en la tabla padre: POS_INB_DOC_RMS.\r\n"
				+ "2.- Tener la información del documento en las tablas hijas: POS_ADJ y POS_ADJ_DETL.\r\n"
				+ "3.- Tener conexión a las Bases de Datos.\r\n"
				+ "4.- Tener la tienda habilitada dentro de la tabla POS_RULES_DOC_CFG donde el campo MIGRATE_FLAG esté marcado con Y.\r\n"
				+ "5.- Tener las accesos al buzón de Retek (u04/retek/rmsdes/batch/wmworking)";
	}
}