package WM.interfaces.pr5;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.JobManagement;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/* DESCRIPCION : Procesar devoluciones de proveedores de POS a RMS y EBS.
 * 
 * MTC-FT-001- Ejecucion exitosa de la PR5OLD
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (RTV).
 * 	Tener Ordenado el job de la interfaz PR5_OLD
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos de tipo RTV.
 * 
 * @Author Gustavo Morales
 * @Date 06/Diciembre/2024
 */
public class ATC_FT_002_PR5_DevolucionesaProveedoresOLD extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_002_DevolucionesaProveedoresOLD_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerías pr5 para la POS_INBOUND_DOCS
		 *********************************************************************/
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		// POSUSER/
		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_WM_MEX_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		// WMLOG/
		Connection dbLog = DriverManager.getConnection(GlobalVariables.DB_HOST_LOG_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_LOG_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_LOG_MEX_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG----------------------------------------------- ");
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A RMS----------------------------------------------- ");
		// RMS/
		Connection dbRms = DriverManager.getConnection(GlobalVariables.DB_HOST_RMS10_MEX_MULTIFORMATO3,
				GlobalVariables.DB_USER_RMS10_MEX_MULTIFORMATO3, GlobalVariables.DB_PASSWORD_RMS10_MEX_MULTIFORMATO3);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A RMS----------------------------------------------- ");

		// RMS1/
		utils.sql.SQLUtil dbRms1 = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS10_MEX_MULTIFORMATO,
				GlobalVariables.DB_USER_RMS10_MEX_MULTIFORMATO, GlobalVariables.DB_PASSWORD_RMS10_MEX_MULTIFORMATO);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A RMS----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A EBS----------------------------------------------- ");

		testCase.setProject_Name("Transformación Multiformato");
		/*
		 * Consultas para la ejecucion E2E
		 *********************************************************************/

		String consultaStatusI = "select * from (select ID,STATUS,DOC_TYPE,PV_DOC_NAME, PARTITION_DATE from POSUSER.POS_INBOUND_DOCS\r\n"
				+ " where DOC_TYPE='RTV'\r\n" + " and  SUBSTR(PV_DOC_NAME,4,5) = '" + data.get("plaza") + "'\r\n"
				+ "and STATUS='I' ORDER BY received_date DESC) where rownum <= 2";

		String tdcQueryStatusLog = "SELECT run_id,interface,start_dt,status,server FROM wm_log_run\r\n"
				+ " where interface = 'PR5_OLD'\r\n" + " and status= 'S'\r\n" + " and start_dt >= trunc(sysdate)\r\n"
				+ " ORDER BY start_dt DESC";

		String consultaTranData = "SELECT ITEM, DEPT, TRAN_CODE, TRAN_DATE, REF_NO_1, PGM_NAME, TIMESTAMP\r\n"
				+ "from tran_data\r\n" + "where tran_code = '24'";

		String consultaFemFif = "SELECT DISTINCT * FROM (SELECT ITEM, ITEM_DESC, TRANSACTION_ID, TRAN_DATE, TRAN_CODE, FROM_LOC, LAST_UPDATE_DATETIME, REFERENCE_3 FROM rms100.FEM_FIF_STG\r\n"
				+ "WHERE ITEM IN (%s)\r\n"
				+ "AND TRAN_CODE = '24' AND STORE = '21222' ORDER BY LAST_UPDATE_DATETIME DESC) where rownum <=25 ORDER BY LAST_UPDATE_DATETIME DESC";

		String run_status = "";

		String Id_doc = "";
		String Ext_Ref = "";
		String item = "";
		String itemS = "";

		// ***************************************** Paso 1
		// *****************************************************

		addStep("Validar en la BD de POSUSER los documentos con estatus en I en la tabla POS_INB_DOC_RMS");

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

		addStep("Validar que existan datos en la tabla POS_RTV");

		String TablaHeader = "SELECT PID_ID, EXT_REF_NO, SUPPLIER, PARTITION_DATE from posuser.pos_rtv where pid_id = "
				+ Id_doc;

		PreparedStatement posQuery1 = dbPos.prepareStatement(TablaHeader);
		ResultSet posResult1 = posQuery1.executeQuery();

		java.sql.ResultSetMetaData rsltpos2 = posResult1.getMetaData();
		int columnCount1 = rsltpos2.getColumnCount();

		if (!posResult1.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningun detalle de documento");
			assertFalse(!posResult1.next(), "Error!, la interfaz no detecto ningun detalle de documento");
		} else {
			testCase.addTextEvidenceCurrentStep(TablaHeader);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount1; i++) {
				String columnName = rsltpos2.getColumnName(i);
				String columnValue = posResult1.getString(i);
				Ext_Ref = posResult1.getString("EXT_REF_NO");
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

		addStep("Validar que existan datos en la tabla POS_RTV_DETL");

		String consultaTablaDetl = "SELECT PID_ID, ITEM,QTY_RETURNED, PV_RETAIL_PRICE, PARTITION_DATE from posuser.pos_rtv_detl where pid_id = "
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
		addStep("Ejecucion job : PR5OLD en  control-M");
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
		assertEquals(resultado, "Ended OK");
		u.hardWait(3);
		control.closeViewpoint();
		u.close();

		// ***************************************** Paso 5
		// *****************************************************

		addStep("Validar el cambio de estatus a 'E' en el documento");

		String tdcQueryDocRTVE = "SELECT DISTINCT SUBSTR(PV_DOC_NAME,4,5) PLAZA, SUBSTR(PV_DOC_NAME,9,5) TIENDA, STATUS, ID, TARGET_ID FROM POSUSER.POS_INBOUND_DOCS WHERE STATUS = 'E' AND DOC_TYPE = 'RTV' AND ID = "
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

		addStep("Ejecucion job : Femrtvupld  en  control-M");
		SeleniumUtil u1 = new SeleniumUtil(new ChromeTest(), true);

		Control_mInicio CM1 = new Control_mInicio(u1, data.get("cm_user"), data.get("cm_ps"));
		u1.get(data.get("cm_server"));
		u1.hardWait(50);
		u1.waitForLoadPage();
		CM1.logOn();

		JSONObject obj1 = new JSONObject(data.get("cm_jobs1"));
		ControlM control1 = new ControlM(u1, testCase, obj1);
		boolean flags = control1.searchJob();
		assertTrue(flags, "No se encontro el job");
		String resultado1 = control1.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado1);
		System.out.println("Valor del output:" + control1.getOutput());
		assertEquals(resultado1, "Wait for Event");
		u1.hardWait(3);
		control1.closeViewpoint();
		u1.close();

		/*************************** paso 9 ************************************/

		addStep("Validar que exista informacion en la TRAN_DATA");

		PreparedStatement rmsQuery = dbRms.prepareStatement(consultaTranData);
		ResultSet rmsResult = rmsQuery.executeQuery();

		java.sql.ResultSetMetaData rsltRms = rmsResult.getMetaData();
		int columnCount3 = rsltRms.getColumnCount();

		if (!rmsResult.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, no existen datos en la TRAN_DATA");
			assertFalse(!rmsResult.next(), "Error!, no existen datos en la TRAN_DATA");
		} else {
			testCase.addTextEvidenceCurrentStep(consultaTranData);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount3; i++) {
				String columnName = rsltRms.getColumnName(i);
				String columnValue = rmsResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		/************************** paso 10 ******************************/
		addStep("Validar que exista informacion en la tabla RTV_HEAD");

		String consultaRtvHead = "SELECT rtv_order_no,supplier,store,created_date, ext_ref_no from rms100.rtv_head where ext_ref_no = "
				+ Ext_Ref + "and store = '21222'";

		PreparedStatement rtvHeadresult = dbRms.prepareStatement(consultaRtvHead);
		ResultSet rtvHeadResult = rtvHeadresult.executeQuery();

		java.sql.ResultSetMetaData rsltrtvhead = rtvHeadResult.getMetaData();
		int columnCount4 = rsltrtvhead.getColumnCount();

		if (!rtvHeadResult.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, no hay datos en la tabla de RTV_HEAD");
			assertFalse(!rtvHeadResult.next(), "Error!, no hay datos en la tabla de RTV_HEAD");

		} else {
			testCase.addTextEvidenceCurrentStep(consultaRtvHead);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount4; i++) {
				String columnName = rsltrtvhead.getColumnName(i);
				String columnValue = rtvHeadResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");

			}
			System.out.println(); // Salto de línea para el siguiente registro
		}

		// ***************************************** Paso 15
		// *****************************************************

		addStep("Ejecutar el servicio STOCK_LEDGER");

		SeleniumUtil u3 = new SeleniumUtil(new ChromeTest(), true);

		Control_mInicio cm3 = new Control_mInicio(u3, data.get("cm_user"), data.get("cm_ps"));
		u3.get(data.get("cm_server"));
		u3.hardWait(40);
		u3.waitForLoadPage();
		cm3.logOn();
		JSONArray obj3 = new JSONArray(data.get("cm_jobs3"));
		JobManagement job3 = new JobManagement(u3, testCase, obj3);
		String resultado3 = job3.jobRunner();
		System.out.println("Resultado de la ejecucion -> " + resultado3);

		u3.hardWait(3);
		u3.close();

		/********** Paso 15 *********/

		addStep("Validar que exista información en la FEM_FIF_STG");

		String thr = String.format(consultaFemFif, itemS);
		SQLResult retekQuery = dbRms1.executeQuery(thr);

		boolean retek = retekQuery.isEmpty();

		if (!retek) {
			testCase.addQueryEvidenceCurrentStep(retekQuery);
		} else {
			testCase.addQueryEvidenceCurrentStep(retekQuery);
			assertFalse(retek, "No hay registros en la tabla FEM_FIF_STG ");
		}

	}

	@Override
	public String setTestFullName() {

		return null;

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub

		return "Construido. Validar la devolución a proveedores directos.";

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
		return "1.Conexion a la VPN de OXXO\r\n" + "2.Acceso a control M\r\n" + "3.Acceso a la BD de POSUSER\r\n"
				+ "4. Que el job de la PR5 este dado de alta\r\n" + "5. Acceso al punto de venta\r\n";
	}

}