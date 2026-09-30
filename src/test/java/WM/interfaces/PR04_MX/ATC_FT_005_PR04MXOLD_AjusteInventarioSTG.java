package WM.interfaces.PR04_MX;

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

/* DESCRIPCION : Envio de ajustes (ADJ) hacía RMS y EBS.
 * 
 * MTC_FT_PR04_MX_009_Enviar ADJ de POS por STG Arq PR50v2
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (ADJ).
 * 	Tener Ordenado el job de la interfaz PR04_MX_OLD
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con el ajuste ADJ (Un ajuste por documento).
 * 
 * @Author Rafael Alarcón
 * @Date 16/Diciembre/2024
 */
public class ATC_FT_005_PR04MXOLD_AjusteInventarioSTG extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_PR04MXOLD_AjusteInventarioSTG_test(HashMap<String, String> data) throws Exception {

		/**************************** Datos de conexion *******************************/

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		/* POSUSER */
		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WM_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER-------------------------------------------------- ");
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A MOMPERF----------------------------------------------- ");
		/* MOMPERF */
		Connection dbMomperf = DriverManager.getConnection(GlobalVariables.DB_HOST_MOMPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_MOMPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_MOMPERF_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A MOMPERF-------------------------------------------------- ");
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		/* WMLOG */
		Connection dbWmlog = DriverManager.getConnection(GlobalVariables.DB_HOST_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WMLOG_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG-------------------------------------------------- ");

		testCase.setProject_Name("Transformación Multiformato");
		/****************************
		 * Variables de datos
		 *******************************/

		String d_id = "";
		String items = "";
		String run_id = "";

		/**************************** Pasos de la prueba ******************************/

		/********** Paso 1 *********/

		addStep("Revisar que exista el documento en la tabla de POS_INBOUND_DOCS");

		String QueryDoc = "SELECT ID, STATUS, DOC_TYPE, PV_DOC_NAME, PARTITION_DATE FROM POSUSER.POS_INBOUND_DOCS WHERE DOC_TYPE = 'ADJ' AND "
				+ "STATUS = 'I' AND SUBSTR(PV_DOC_NAME,4,5) = '" + data.get("PV_CR_PLAZA")
				+ "' AND SUBSTR(PV_DOC_NAME,9,5) = '" + data.get("PV_CR_TIENDA") + "'";

		PreparedStatement PosQuery = dbPos.prepareStatement(QueryDoc);
		ResultSet PosResult = PosQuery.executeQuery();

		if (PosResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc);
			while (PosResult.next()) {

				d_id += PosResult.getString("ID") + ",";// Se concatenan los ID para proximas consultas/filtros.

				String ID = PosResult.getString("ID");
				String STATUS = PosResult.getString("STATUS");
				String DOC_TYPE = PosResult.getString("DOC_TYPE");
				String DOC_NAME = PosResult.getString("PV_DOC_NAME");
				String PARTITION = PosResult.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep(
						"--> " + "ID : " + ID + " | " + " | " + "STATUS : " + STATUS + " | " + "DOC_TYPE : " + DOC_TYPE
								+ " | " + "DOC_NAME : " + DOC_NAME + " | " + " PARTITION_DATE : " + PARTITION);

			}
			d_id = d_id.substring(0, d_id.length() - 1);// Se borra la coma que tienen el último registro
		} else {
			assertFalse(!PosResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult.close();
		PosQuery.close();

		/********** Paso 2 *********/

		addStep("Revisar que exista el encabezado del documento en la tabla POS_ADJ");

		String QueryDocEnc = "SELECT * FROM POSUSER.POS_ADJ WHERE PID_ID = " + d_id;

		PreparedStatement PosQueryHeader = dbPos.prepareStatement(QueryDocEnc);
		ResultSet PosResultHeader = PosQueryHeader.executeQuery();

		if (PosResultHeader.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocEnc);
			while (PosResultHeader.next()) {

				String PID_ID = PosResultHeader.getString("PID_ID");
				String NO_RECORDS = PosResultHeader.getString("NO_RECORDS");
				String ADJ_DATE = PosResultHeader.getString("ADJ_DATE");
				String PARTITION_DATE = PosResultHeader.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "NO_RECORDS : " + NO_RECORDS
						+ " | " + "ADJ_DATE : " + ADJ_DATE + " | " + " PARTITION_DATE : " + PARTITION_DATE);
			}
		} else {
			assertFalse(!PosResultHeader.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 3 *********/

		addStep("Revisar que exista el detalle del documento en la tabla POS_ADJ_DETL");

		String QueryDocDetl = "SELECT * FROM POSUSER.POS_ADJ_DETL WHERE PID_ID = " + d_id;

		PreparedStatement PosQueryDetl = dbPos.prepareStatement(QueryDocDetl);
		ResultSet PosResultDetl = PosQueryDetl.executeQuery();

		if (PosResultDetl.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocDetl);
			while (PosResultDetl.next()) {

				items += PosResultDetl.getString("ITEM") + ",";// Se concatenan los items para futuros filtros

				String PID_ID = PosResultDetl.getString("PID_ID");
				String ITEM = PosResultDetl.getString("ITEM");
				String ADJ_QTY = PosResultDetl.getString("ADJ_QTY");
				String REASON = PosResultDetl.getString("REASON");
				String USER_ID = PosResultDetl.getString("USER_ID");
				String PRICE = PosResultDetl.getString("PV_RETAIL_PRICE");
				String PARTITION_DATE = PosResultDetl.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "ITEM : " + ITEM + " | "
						+ "ADJ_QTY : " + ADJ_QTY + " | " + "REASON : " + REASON + " | " + "USER_ID : " + USER_ID + " | "
						+ "PV_RETAIL_PRICE : " + PRICE + " | " + " PARTITION_DATE : " + PARTITION_DATE);
			}
			items = items.substring(0, items.length() - 1);
		} else {
			assertFalse(!PosResultDetl.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 4 *********/

		addStep("Ejecutar la interfaz runPR04_MX ");

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
		assertEquals(resultado, "Wait for Event");
		u.hardWait(3);
		job.closeViewpoint();
		u.close();

		/********** Paso 5 *********/

		addStep("Revisar la corrida de la interfaz en la tabla de WM_LOG_RUN");

		String QueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR04_MX_OLD'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

		PreparedStatement LogQuery = dbWmlog.prepareStatement(QueryIntegrationServer);
		ResultSet LogResult = LogQuery.executeQuery();

		if (LogResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryIntegrationServer);
			while (LogResult.next()) {
				// RUN_ID,START_DT,STATUS
				run_id = LogResult.getString("RUN_ID");
				String START_DT = LogResult.getString("START_DT");
				String STATUS = LogResult.getString("STATUS");

				testCase.addTextEvidenceCurrentStep(
						"--> " + " RUN_ID : " + run_id + " START_DT : " + START_DT + " STATUS : " + STATUS);
			}
		} else {
			assertFalse(!LogResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}
		LogResult.close();
		LogQuery.close();

		/********** Paso 6 *********/

		addStep("Revisar los threads levantados en la tabla WM_LOG_THREADS");

		String QueryStatusThread = " SELECT PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5"
				+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_id;

		PreparedStatement LogThreadQuery = dbWmlog.prepareStatement(QueryStatusThread);
		ResultSet LogThreadResult = LogThreadQuery.executeQuery();

		if (LogThreadResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryStatusThread);
			while (LogThreadResult.next()) {
				// PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5
				String PARENT_ID = LogThreadResult.getString("PARENT_ID");
				String NAME = LogThreadResult.getString("NAME");
				String START_DT = LogThreadResult.getString("START_DT");
				String END_DT = LogThreadResult.getString("END_DT");
				String STATUS = LogThreadResult.getString("STATUS");
				String ATT1 = LogThreadResult.getString("ATT1");
				String ATT2 = LogThreadResult.getString("ATT2");
				String ATT5 = LogThreadResult.getString("ATT5");

				testCase.addTextEvidenceCurrentStep("--> " + " PARENT_ID : " + PARENT_ID + " NAME : " + NAME
						+ " START_DT : " + START_DT + " END_DT : " + END_DT + " STATUS : " + STATUS + " ATT1 : " + ATT1
						+ " ATT2 : " + ATT2 + " ATT5 : " + ATT5);
			}
		} else {
			assertFalse(!LogThreadResult.next(),
					"--> ¡ERROR!, No existe información relacionada a Threads de la ejecución");
		}
		LogThreadResult.close();
		LogThreadQuery.close();

		/********** Paso 6 *********/

		addStep("Revisar que el documento haya cambiado a Status E en POS_INBOUND_DOCS");

		String QueryChageDoc = " SELECT DISTINCT ID, STATUS, DOC_TYPE, PV_DOC_NAME, TARGET_ID FROM POSUSER.POS_INBOUND_DOCS"
				+ " WHERE STATUS = 'E'" + " AND DOC_TYPE = 'ADJ'" + " AND ID IN (" + d_id + ")";

		PreparedStatement posChageQuery = dbPos.prepareStatement(QueryChageDoc);
		ResultSet posEResult = posChageQuery.executeQuery();

		if (posEResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryChageDoc);
			while (posEResult.next()) {

				String ID = posEResult.getString("ID");
				String STATUS = posEResult.getString("STATUS");
				String DOC_TYPE = posEResult.getString("DOC_TYPE");
				String PV_DOC_NAME = posEResult.getString("PV_DOC_NAME");
				String TARGET_ID = posEResult.getString("TARGET_ID");

				testCase.addTextEvidenceCurrentStep("--> " + " ID : " + ID + " DOC_TYPE : " + DOC_TYPE
						+ " PV_DOC_NAME : " + PV_DOC_NAME + " STATUS : " + STATUS + " TARGET_ID : " + TARGET_ID);
			}
		} else {
			assertFalse(!posEResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}
		posEResult.close();
		posChageQuery.close();

		/********** Paso 7 *********/

		addStep("Revisar la información generada en tabla STG_INVADJUST");

		String DocStgInv = "SELECT MSG_TYPE, ORIGIN_SYSTEM, REASON_CODE, LOCATION, ITEM, CREATE_DATETIME "
				+ "FROM RMS.STG_INVADJUST WHERE CREATE_DATETIME >= TRUNC(SYSDATE) AND ITEM  IN (" + items
				+ ") ORDER BY CREATE_DATETIME DESC";

		PreparedStatement posStgInv = dbMomperf.prepareStatement(DocStgInv);
		ResultSet posStgInvResult = posStgInv.executeQuery();

		if (posStgInvResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(DocStgInv);
			while (posStgInvResult.next()) {

				String MSG_TYPE = posStgInvResult.getString("MSG_TYPE");
				String ORIGIN_SYSTEM = posStgInvResult.getString("ORIGIN_SYSTEM");
				String REASON_CODE = posStgInvResult.getString("REASON_CODE");
				String LOCATION = posStgInvResult.getString("LOCATION");
				String ITEM = posStgInvResult.getString("ITEM");
				String CREATE_DATETIME = posStgInvResult.getString("CREATE_DATETIME");

				testCase.addTextEvidenceCurrentStep("--> " + "MSG_TYPE : " + MSG_TYPE + " | " + "ORIGIN_SYSTEM : "
						+ ORIGIN_SYSTEM + " | " + "REASON_CODE : " + REASON_CODE + " | " + "LOCATION : " + LOCATION
						+ " | " + "ITEM : " + ITEM + " | " + "CREATE_DATETIME : " + CREATE_DATETIME);
			}
		} else {
			assertFalse(!posStgInvResult.next(), "--> ¡ERROR!, No se encuentran datos en la tabla");
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
		return "1.- Tener las configuraciones necesarias en las tablas de WM_INTERFASE_CONFIG y WM_TRANCODE_TOPOLOGY_CFG.\r\n"
				+ "2.- Tener creado el documento pendiente a procesar en las tablas de POS_INBOUND_DOCS, POS_ENVELOPE, POS_ADJ y POS_ADJ_DETL.\r\n"
				+ "3.- Tener las fechas correctas para cierre de inventario a nivel tienda o plaza en la tabla de POS_STORE_MINTRANDATE.\r\n"
				+ "4.- Tener acceso a las BD de POSUSER, WMLOG, EBS, RMS16 y DAS.";
	}
}