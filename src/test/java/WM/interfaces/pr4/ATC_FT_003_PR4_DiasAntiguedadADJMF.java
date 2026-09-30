package WM.interfaces.pr4;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/* DESCRIPCION : Validar el no envio de ajustes (ADJ) fuera de fecha de profundidad.
 * 
 * MTC-FT-009- No procesar con documentos ADJ con más de 4 días de antigüedad.
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (ADJ) de los tipos de ajuste 150, 160, 514, 532.
 * 	Tener Ordenado el job de la interfaz PR4
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 	El documento disponible debe tener una fecha mayor a 4 días de antiguedad
 * 
 * INSUMOS:
 * 	Documentos con el ajuste ADJ (Un ajuste por documento) con fecha fuera de la profundidad permitida.
 * 
 * @Author Rafael Alarcón
 * @Date 18/Julio/2024
 */
public class ATC_FT_003_PR4_DiasAntiguedadADJMF extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_PR4_DiasAntiguedadADJ_MF_test(HashMap<String, String> data) throws Exception {

		/******************
		 * Datos de conexion
		 *********************************************************************/

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
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		/* WMLOG */
		Connection dbLog = DriverManager.getConnection(GlobalVariables.DB_HOST_LOG_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_LOG_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_LOG_MEX_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG----------------------------------------------- ");

		testCase.setProject_Name("Transformación Multiformato");
		/*****************
		 * Variables de consulta
		 ************************************************************************************/

		PreparedStatement preparedStatement = null;
		String run_status = "";

		/************************************
		 * Pasos del caso de Prueba
		 *******************************************/

		addStep("Validar que exista informacion de la Plaza y Tienda pendiente de procesar con antiguedad mayor a 4 dias.");

		String tdcInfPlazaTienda = " SELECT DISTINCT B.ID, substr(PV_DOC_NAME,4,5) PV_CR_PLAZA, substr(PV_DOC_NAME,9,5) PV_CR_TIENDA, PV_DOC_NAME, B.PARTITION_DATE"
				+ " FROM POSUSER.POS_ADJ C," + " POSUSER.POS_ADJ_DETL D," + " POSUSER.POS_INB_DOC_RMS B"
				+ " JOIN POSUSER.PLAZAS P ON SUBSTR(B.PV_DOC_NAME,4,5) = P.CR_PLAZA AND P.PAIS = 'MEX'"
				+ " WHERE B.STATUS = 'I'" + " AND   B.DOC_TYPE = 'ADJ'" + " AND   C.PID_ID = B.ID"
				+ " AND   D.PID_ID = B.ID" + " AND   D.ADJ_QTY <> 0" + " AND B.PARTITION_DATE > SYSDATE - 6";

		PreparedStatement posQuery = dbPos.prepareStatement(tdcInfPlazaTienda);
		ResultSet posResult = posQuery.executeQuery();

		java.sql.ResultSetMetaData rsltpos = posResult.getMetaData();
		int columnCount = rsltpos.getColumnCount();

		if (posResult.next()) {
			testCase.addTextEvidenceCurrentStep(tdcInfPlazaTienda);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsltpos.getColumnName(i);
				String columnValue = posResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println();
		} else {
			// Maneja el caso donde no hay más filas
			assertFalse(!posResult.next(), "No hay documentos de tipo ADJ disponibles");
		}

		// Cerrar los recursos
		posResult.close();
		posQuery.close();

		/********** Paso 2 *********/

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

		/********** Paso 3 *********/

		addStep("Comprobar que se registra la ejecucion en WMLOG");

		String tdcQueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR4'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

		PreparedStatement LogQuery = dbLog.prepareStatement(tdcQueryIntegrationServer);
		ResultSet LogResult = LogQuery.executeQuery();

		java.sql.ResultSetMetaData rsltlog = LogResult.getMetaData();
		int columnCountLog = rsltlog.getColumnCount();

		// Procesar los resultados
		if (!LogResult.next() && LogResult.getString("STATUS") != "S") {
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

		/********** Paso 4 *********/

		addStep("Se valida que no haya levantado THREADS");

		String tdcQueryStatusThread = " SELECT PARENT_ID,THREAD_ID,NAME,WM_LOG_THREAD.STATUS,ATT1,ATT2"
				+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_status;

		preparedStatement = dbLog.prepareStatement(tdcQueryStatusThread);

		ResultSet LogThreadResult = preparedStatement.executeQuery();

		java.sql.ResultSetMetaData rsltlogThread = LogThreadResult.getMetaData();
		int columnCountLogThread = rsltlogThread.getColumnCount();

		if (LogThreadResult.next()) {

			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLogThread; i++) {
				String columnName = rsltlogThread.getColumnName(i);
				String columnValue = LogThreadResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			assertFalse(!LogThreadResult.next(), "Error!, Hay registros de threads levantados");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryStatusThread);
			testCase.addTextEvidenceCurrentStep("Success!, no hay registros de threads levantados");
		}
		// Cerrar los recursos
		LogThreadResult.close();
		dbLog.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar el no envio de ajustes (ADJ) fuera de fecha de profundidad";
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
		return "1.- Tener documento ADJ pendiente por procesar en la tabla padre: POS_INB_DOC_RMS y que tengan antigüedad mayor a 4 días.\r\n"
				+ "2.- Tener la información del documento en las tablas hijas: POS_ADJ y POS_ADJ_DETL.\r\n"
				+ "3.- Tener conexión a las Bases de Datos";
	}

}
