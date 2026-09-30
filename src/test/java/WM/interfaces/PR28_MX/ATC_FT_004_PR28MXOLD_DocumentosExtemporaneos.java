package WM.interfaces.PR28_MX;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/* DESCRIPCION : Procesar las ordenes de compra de POS a RMS con Movimientos Extemporaneos..
 * 
 * MTC_FT_18_PR28_MX_Documentos Extemporaneos OLD
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (REC).
 * 	Tener Ordenado el job de la interfaz PR28_MX
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos de tipo REC.
 * 
 * @Author Gustavo Morales
 * @Date 16/Diciembre/2024
 */
import java.util.HashMap;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_PR28MXOLD_DocumentosExtemporaneos extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_004_PR28MXOLD_DocumentosExtemporaneos_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerías pr28_mx para la POS_INB_DOC_RMS
		 *********************************************************************/
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		// POSUSER/
		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WM_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		// WMLOG/
		Connection dbLog = DriverManager.getConnection(GlobalVariables.DB_HOST_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WMLOG_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A MOMPRF----------------------------------------------- ");
		// WMLOG/
		Connection dbPerf = DriverManager.getConnection(GlobalVariables.DB_HOST_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_DASPERF_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A MOMPRF----------------------------------------------- ");

		testCase.setProject_Name("Transformacion multiformato");

		String consultaStatusI = "SELECT ID, STATUS, DOC_TYPE, PV_DOC_NAME, PARTITION_DATE FROM POS_INBOUND_DOCS WHERE DOC_TYPE = 'REC' AND "
				+ "STATUS = 'I' AND SUBSTR(PV_DOC_NAME,4,5) = '" + data.get("plaza")
				+ "' AND SUBSTR(PV_DOC_NAME,9,5) = '" + data.get("tienda") + "'";

		String tdcQueryStatusLog = "SELECT run_id,interface,start_dt,status,server FROM wm_log_run\r\n"
				+ " where interface = 'PR28_MX_EXTEMP_OLD'\r\n" + " and status= 'S'\r\n"
				+ " and start_dt >= trunc(sysdate)\r\n" + " ORDER BY start_dt DESC";

		String Id_doc = "";
		String item = "";
		String itemS = "";
		String run_status = "";

		// ***************************************** Paso 1
		// *****************************************************

		addStep("Validar en la BD de POSUSER los documentos con estatus en I en la tabla POS_INBOUND_DOCS");

		PreparedStatement posQuery = dbPos.prepareStatement(consultaStatusI);
		ResultSet posResult = posQuery.executeQuery();

		java.sql.ResultSetMetaData rsltpos1 = posResult.getMetaData();
		int columnCount = rsltpos1.getColumnCount();

		if (!posResult.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningún documento pendiente");
			assertFalse(!posResult.next(), "Error!, la interfaz no detecto ningún documento pendiente");
		} else {
			testCase.addTextEvidenceCurrentStep(consultaStatusI);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsltpos1.getColumnName(i);
				String columnValue = posResult.getString(i);
				Id_doc = posResult.getString("ID");
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
				System.out.println(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		posResult.close();
		posQuery.close();

		// ***************************************** Paso 2
		// *****************************************************

		addStep("Validar que existan datos en la tabla POS_REC");

		String consultaTablaHeader = "SELECT PID_ID, PV_CVE_MVT, EXT_REF_NO, SUPPLIER, PARTITION_DATE FROM POS_REC WHERE PID_ID = "
				+ Id_doc;

		PreparedStatement posQuery1 = dbPos.prepareStatement(consultaTablaHeader);
		ResultSet posResult1 = posQuery1.executeQuery();

		java.sql.ResultSetMetaData rsltpos2 = posResult1.getMetaData();
		int columnCount1 = rsltpos2.getColumnCount();

		if (!posResult1.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningun detalle de documento");
			assertFalse(!posResult1.next(), "Error!, la interfaz no detecto ningun detalle de documento");
		} else {
			testCase.addTextEvidenceCurrentStep(consultaTablaHeader);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount1; i++) {
				String columnName = rsltpos2.getColumnName(i);
				String columnValue = posResult1.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
				System.out.println(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		posResult1.close();
		posQuery1.close();

		// ***************************************** Paso 3
		// *****************************************************

		addStep("Validar que existan datos en la tabla POS_REC_DETL");

		String consultaTablaDetl = "SELECT PID_ID, ITEM, BOL_NO, PV_RETAIL_PRICE, PARTITION_DATE FROM POS_REC_DETL WHERE PID_ID = "
				+ Id_doc;

		PreparedStatement posQuery2 = dbPos.prepareStatement(consultaTablaDetl);

		ResultSet posResult2 = posQuery2.executeQuery();

		java.sql.ResultSetMetaData rsltpos3 = posResult2.getMetaData();
		int columnCount2 = rsltpos3.getColumnCount();

		if (!posResult2.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningun detalle de documento");
			assertFalse(!posResult2.next(), "Error!, la interfaz no detecto ningun detalle de documento");
		} else {
			testCase.addTextEvidenceCurrentStep(consultaTablaDetl);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount2; i++) {
				String columnName = rsltpos3.getColumnName(i);
				String columnValue = posResult2.getString(i);
				item += posResult2.getString("ITEM") + ",";
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
				System.out.println(columnName + ": " + columnValue + " ");
			}
			itemS = item.substring(0, item.length() - 1);
			System.out.println(itemS); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		posResult2.close();
		posQuery2.close();

		// ***************************************** Paso 4
		// *****************************************************
		addStep("Ejecucion job : PR28_MX_OLD en  control-M");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

		Control_mInicio CM = new Control_mInicio(u, data.get("cm_user"), data.get("cm_ps"));
		u.get(data.get("cm_server"));
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn();

		JSONObject obj = new JSONObject(data.get("cm_jobs"));
		ControlM control = new ControlM(u, testCase, obj);
		boolean flag = control.searchJob();
		assertTrue(flag, "No se encontro el job");
		String resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);
		System.out.println("Valor del output:" + control.getOutput());
		assertEquals(resultado, "Wait for Event");
		u.hardWait(3);
		control.closeViewpoint();
		u.close();

		// ***************************************** Paso 5
		// *****************************************************

		addStep("Validar el cambio de estatus a 'E' en el documento");

		String tdcQueryDocRTVE = " SELECT DISTINCT SUBSTR(PV_DOC_NAME,4,5) PLAZA, SUBSTR(PV_DOC_NAME,9,5) TIENDA, STATUS, ID, TARGET_ID FROM POS_INB_DOC_RMS WHERE STATUS = 'E' AND DOC_TYPE = 'REC' AND ID = "
				+ Id_doc;

		PreparedStatement Query2 = dbPos.prepareStatement(tdcQueryDocRTVE);
		ResultSet posEResult = Query2.executeQuery();

		java.sql.ResultSetMetaData rsltposE = posEResult.getMetaData();
		int columnCountposE = rsltposE.getColumnCount();

		if (!posEResult.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, el documento no cambio de status");
			assertFalse(!posEResult.next(), "Error!, el documento no cambio de status");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryDocRTVE);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountposE; i++) {
				String columnName = rsltposE.getColumnName(i);
				String columnValue = posEResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		posEResult.close();
		dbPos.close();

		// ***************************************** Paso 6
		// *****************************************************
		addStep("Comprobar que se registra la ejecucion en WM_LOG_RUN");

		PreparedStatement LogQuery = dbLog.prepareStatement(tdcQueryStatusLog);
		ResultSet LogResult = LogQuery.executeQuery();

		java.sql.ResultSetMetaData rsltlog = LogResult.getMetaData();
		int columnCountLog = rsltlog.getColumnCount();

		// Procesar los resultados
		if (!LogResult.next() && LogResult.getString("STATUS") != "S") {
			testCase.addTextEvidenceCurrentStep(
					"Error!, la interfaz terminó en Status E o no tienen ejecuciones recientes");
			assertFalse(!LogResult.next(), "Error!, la interfaz terminó en Status E o no tienen ejecuciones recientes");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryStatusLog);
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

		// ***************************************** Paso 7
		// *****************************************************

		addStep("Se valida la generacion de thread");

		String tdcQueryStatusThread = " SELECT PARENT_ID,THREAD_ID,NAME,WM_LOG_THREAD.STATUS,ATT1,ATT2 FROM WMLOG.WM_LOG_THREAD WHERE PARENT_ID = "
				+ run_status;

		PreparedStatement LogQuery1 = dbLog.prepareStatement(tdcQueryStatusThread);

		ResultSet LogThreadResult = LogQuery1.executeQuery();

		java.sql.ResultSetMetaData rsltlogThread = LogThreadResult.getMetaData();
		int columnCountLogThread = rsltlogThread.getColumnCount();

		if (!LogThreadResult.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, no hay registros de threads levantados");
			assertFalse(!LogThreadResult.next(), "Error!, no hay registros de threads levantados");
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

		// ***************************************** Paso 8
		// *****************************************************

		addStep("Se valida que los datos hayan llegado a la tabla POS_INBOUND_MOVS");

		String consultaInbMovs = "SELECT EO_WEB_EXTEMP_MOV_ID_PK, PID_ID, CR_PLAZA, CR_TIENDA, DOC_NAME, DOC_TYPE, MOV_DATE FROM EO_WEB.XPOS_EXTEMP_INBOUND_MOVS WHERE  PID_ID = "
				+ Id_doc;

		PreparedStatement momQuery = dbPerf.prepareStatement(consultaInbMovs);
		ResultSet momResult = momQuery.executeQuery();

		java.sql.ResultSetMetaData rsltmom1 = momResult.getMetaData();
		int columnCountmom = rsltmom1.getColumnCount();

		if (!momResult.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningún documento pendiente");
			assertFalse(!momResult.next(), "Error!, la interfaz no detecto ningún documento pendiente");
		} else {
			testCase.addTextEvidenceCurrentStep(consultaInbMovs);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountmom; i++) {
				String columnName = rsltmom1.getColumnName(i);
				String columnValue = momResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
				System.out.println(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		momResult.close();
		momQuery.close();
	}

	@Override
	public String setTestFullName() {

		return null;

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub

		return "Procesar las ordenes de compra de POS a RMS con Movimientos Extemporaneos.";

	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "GUSTAVO MORALES";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "1- Tener insumos para procesar\r\n" + "2- Acceso a control M y job de la PR28_MX_OLD dado de alta\r\n"
				+ "3- Conexión a la VPN de OXXO\r\n" + "4- Que la fecha sea menor a la de la POS_STORE_MINTRANDATE";
	}

}