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
import utils.sql.SQLResult;

/* DESCRIPCION : Envio de ajustes (ADJ) hacía RMS y EBS.
 * 
 * MTC_FT_PR04_MX_015_Enviar ADJ Extemporaneo de POS hacía Web
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (ADJ).
 * 	Tener Ordenado el job de la interfaz PR04_MX_EXTEMP
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con el ajuste ADJ (Un ajuste por documento).
 * 
 * @Author Rafael Alarcón
 * @Date 16/Diciembre/2024
 */
public class ATC_FT_003_PR04MX_AjusteInventarioEXT extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_PR04MX_AjusteInventarioEXT_test(HashMap<String, String> data) throws Exception {

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
				" -------------------------------- INTENTANDO CONECTAR A DAS PERF----------------------------------------------- ");
		/* DAS */
		utils.sql.SQLUtil dbDas = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_DAS_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_DAS_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_DAS_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A DAS PERF----------------------------------------------- ");
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

		addStep("Revisar que exista el documento en la tabla de POS_INB_DOC_RMS");

		String QueryDoc = "SELECT ID, PV_CR_PLAZA, PV_CR_TIENDA, STATUS, DOC_TYPE, PV_DOC_NAME, PARTITION_DATE \r\n"
				+ "FROM POSUSER.POS_INB_DOC_RMS WHERE DOC_TYPE = 'ADJ' AND PV_CR_PLAZA = '" + data.get("PV_CR_PLAZA")
				+ "' AND PV_CR_TIENDA = '" + data.get("PV_CR_TIENDA") + "' AND STATUS = 'I'";

		PreparedStatement PosQuery = dbPos.prepareStatement(QueryDoc);
		ResultSet PosResult = PosQuery.executeQuery();

		if (PosResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc);
			while (PosResult.next()) {

				d_id += PosResult.getString("ID") + ",";// Se concatenan los ID para proximas consultas/filtros.

				String ID = PosResult.getString("ID");
				String PLAZA = PosResult.getString("PV_CR_PLAZA");
				String TIENDA = PosResult.getString("PV_CR_TIENDA");
				String STATUS = PosResult.getString("STATUS");
				String DOC_TYPE = PosResult.getString("DOC_TYPE");
				String DOC_NAME = PosResult.getString("PV_DOC_NAME");
				String PARTITION = PosResult.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "ID : " + ID + " | " + "PV_CR_PLAZA : " + PLAZA + " | "
						+ "PV_CR_TIENDA : " + TIENDA + " | " + "STATUS : " + STATUS + " | " + "DOC_TYPE : " + DOC_TYPE
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

		addStep("Ejecutar la interfaz runPR04_MX_EXTEMP");

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
				+ " WHERE INTERFACE = 'PR04_MX_EXTEMP'" + " AND  START_DT >= TRUNC(SYSDATE)"
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

		addStep("Revisar que el documento haya cambiado a Status E en POS_INB_DOC_RMS");

		String QueryChageDoc = " SELECT DISTINCT ID, PV_CR_TIENDA, PV_CR_PLAZA, STATUS, TARGET_ID FROM POSUSER.POS_INB_DOC_RMS"
				+ " WHERE STATUS = 'E'" + " AND DOC_TYPE = 'ADJ'" + " AND ID IN (" + d_id + ")";

		PreparedStatement posChageQuery = dbPos.prepareStatement(QueryChageDoc);
		ResultSet posEResult = posChageQuery.executeQuery();

		if (posEResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryChageDoc);
			while (posEResult.next()) {

				String ID = posEResult.getString("ID");
				String PV_CR_TIENDA = posEResult.getString("PV_CR_TIENDA");
				String PV_CR_PLAZA = posEResult.getString("PV_CR_PLAZA");
				String STATUS = posEResult.getString("STATUS");
				String TARGET_ID = posEResult.getString("TARGET_ID");

				testCase.addTextEvidenceCurrentStep("--> " + " ID : " + ID + " PV_CR_TIENDA : " + PV_CR_TIENDA
						+ " PV_CR_PLAZA : " + PV_CR_PLAZA + " STATUS : " + STATUS + " TARGET_ID : " + TARGET_ID);
			}
		} else {
			assertFalse(!posEResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}
		posEResult.close();
		posChageQuery.close();

		/********** Paso 7 *********/

		addStep("Revisar la información generada en tabla XPOS_EXTEMP_INBOUND_MOVS");

		String QueryXposMovs = "SELECT EO_WEB_EXTEMP_MOV_ID_PK AS MOV_ID, DOC_NAME, STATUS_DESC, ITEM, MVT_QTY, CREATED_DATE "
				+ "FROM EO_WEB.XPOS_EXTEMP_INBOUND_MOVS WHERE PID_ID IN ( " + d_id + " ) AND ITEM IN (" + items
				+ ") AND DOC_TYPE = 'ADJ'";

		String DasQuery = String.format(QueryXposMovs);
		SQLResult DasQueryXpos = dbDas.executeQuery(DasQuery);

		if (!DasQueryXpos.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(DasQueryXpos);
		} else {
			assertFalse(PosResultDetl.next(), "--> ¡ERROR!, No existe información relacionada al documento");
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
				+ "2.- Tener creado el documento pendiente a procesar en las tablas de POS_INB_DOC_RMS, POS_ADJ y POS_ADJ_DETL.\r\n"
				+ "3.- Tener la bandera de MIGRATE_FLAG en Y en el campo de V16 en la tabla WM_OXXO_ROLLOUT_CONFIG .\r\n"
				+ "4.- Tener las fechas correctas para cierre de inventario a nivel tienda o plaza en la tabla de POS_STORE_MINTRANDATE.\r\n"
				+ "5.- Tener acceso a las BD de POSUSER, WMLOG, EBS, RMS16 y DAS.";
	}
}