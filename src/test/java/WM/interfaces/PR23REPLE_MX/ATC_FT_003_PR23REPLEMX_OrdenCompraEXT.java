package WM.interfaces.PR23REPLE_MX;

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

/* DESCRIPCION : Envio de confirmaciones de ordenes de compra de manera extemporanea.
 * 
 * MTC_FT_PR23_REPLE_MX_019- Envio de confirmaciones de ordenes de compra Extemporaneos (REC) de POS por WEB
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (REC).
 * 	Tener Ordenado el job de la interfaz PR23REPLE_MX_EXTEMP
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con la confirmación de ORDEN de compra.
 * 
 * @Author Rafael Alarcón
 * @Date 19/Octubre/2024
 */
public class ATC_FT_003_PR23REPLEMX_OrdenCompraEXT extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_PR23REPLEMX_OrdenCompraEXT_test(HashMap<String, String> data) throws Exception {

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
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		/* WMLOG */
		Connection dbWmlog = DriverManager.getConnection(GlobalVariables.DB_HOST_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WMLOG_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG-------------------------------------------------- ");
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A DAS PERF----------------------------------------------- ");
		/* DAS */
		utils.sql.SQLUtil dbDas = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_DAS_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_DAS_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_DAS_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A DAS PERF----------------------------------------------- ");

		testCase.setProject_Name("Transformación Multiformato");
		/****************************
		 * Variables de datos
		 *******************************/

		String c_id = "";

		String n_order = "";

		String items = "";

		String run_id = "";

		String t_id = "";

		/**************************** Pasos de la prueba ******************************/

		/********** Paso 1 *********/

		addStep("Validar que exista un documento pendiente por procesar en POS_INB_DOC_RMS");

		String QueryDoc = "SELECT A.ID, PV_CR_PLAZA, PV_CR_TIENDA, PV_DOC_NAME, A.PARTITION_DATE\r\n"
				+ "  FROM POSUSER.POS_INB_DOC_RMS A\r\n" + "       JOIN POSUSER.POS_REC C ON C.PID_ID = A.ID\r\n"
				+ "       JOIN POSUSER.PLAZAS P ON A.PV_CR_PLAZA = P.CR_PLAZA AND P.PAIS = 'MEX'\r\n"
				+ "       JOIN WMUSER.WM_OXXO_ROLLOUT_CONFIG R ON A.PV_CR_PLAZA = R.CR_PLAZA AND R.V16 = 'Y'\r\n"
				+ "       JOIN POSUSER.POS_STORE_MINTRANDATE M ON A.PV_CR_PLAZA = M.CR_PLAZA AND A.PV_CR_TIENDA = M.CR_TIENDA\r\n"
				+ " WHERE A.DOC_TYPE = 'REC'\r\n" + "   AND A.STATUS = 'I'\r\n" + "   AND C.PV_CVE_MVT = 10\r\n"
				+ "   AND C.ORDER_NO <> 0\r\n"
				+ "   AND (C.CREATED_DATE <= M.MIN_TRANDATE AND M.MIN_TRANDATE IS NOT NULL) \r\n"
				+ "   AND A.partition_date > SYSDATE - 10\r\n"
				+ "GROUP BY PV_CR_PLAZA, PV_CR_TIENDA, PV_DOC_NAME, A.PARTITION_DATE, A.ID";

		PreparedStatement PosQuery = dbPos.prepareStatement(QueryDoc);
		ResultSet PosResult = PosQuery.executeQuery();

		if (PosResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc);
			while (PosResult.next()) {

				c_id += PosResult.getString("ID") + ",";// Se concatenan los ID para proximas consultas/filtros.

				String ID = PosResult.getString("ID");
				String DOC_NAME = PosResult.getString("PV_DOC_NAME");
				String PLAZA = PosResult.getString("PV_CR_PLAZA");
				String TIENDA = PosResult.getString("PV_CR_TIENDA");
				String PARTITION_DATE = PosResult.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep(
						"--> " + "ID : " + ID + " | " + "DOC_NAME : " + DOC_NAME + " | " + "PV_CR_PLAZA : " + PLAZA
								+ " | " + "PV_CR_TIENDA : " + TIENDA + " | " + "PARTITION_DATE : " + PARTITION_DATE);

			}
			c_id = c_id.substring(0, c_id.length() - 1);// Se borra la coma que tienen el último registro
		} else {
			assertFalse(!PosResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult.close();
		PosQuery.close();

		/********** Paso 2 *********/

		addStep("Revisar que exista el encabezado del documento en la tabla POS_REC y que el campo ORDER_NO sea diferente a 0");

		String QueryDocEnc = "SELECT PID_ID, NO_RECORDS, PV_CVE_MVT, ORDER_NO, SUPPLIER, CREATED_DATE FROM POSUSER.POS_REC WHERE PID_ID IN ("
				+ c_id + ")";// Se le pasan los id para filtrar los encabezados

		PreparedStatement PosQueryHeader = dbPos.prepareStatement(QueryDocEnc);
		ResultSet PosResultHeader = PosQueryHeader.executeQuery();

		if (PosResultHeader.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocEnc);
			while (PosResultHeader.next()) {

				n_order += PosResultHeader.getString("ORDER_NO") + ",";// Se concatenan los ORDER_NO para proximas
				// consultas/filtros.

				String PID_ID = PosResultHeader.getString("PID_ID");
				String NO_RECORDS = PosResultHeader.getString("NO_RECORDS");
				String PV_CVE_MVT = PosResultHeader.getString("PV_CVE_MVT");
				String SUPPLIER = PosResultHeader.getString("SUPPLIER");
				String ORDER_NO = PosResultHeader.getString("ORDER_NO");
				String CREATED_DATE = PosResultHeader.getString("CREATED_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "NO_RECORDS : " + NO_RECORDS
						+ " | " + "PV_CVE_MVT : " + PV_CVE_MVT + " | " + "ORDER_NO : " + ORDER_NO + " | "
						+ "SUPPLIER : " + SUPPLIER + " | " + "CREATED_DATE : " + CREATED_DATE);
			}
			n_order = n_order.substring(0, n_order.length() - 1);
		} else {
			assertFalse(!PosResultHeader.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}
		PosResultHeader.close();
		PosQueryHeader.close();

		/********** Paso 3 *********/

		addStep("Revisar que exista detalle del documento en la tabla POS_REC_DETL");

		String QueryDocDetl = "SELECT PID_ID, ITEM, RECEIVED_QTY, PV_RETAIL_PRICE, REMISION, PARTITION_DATE FROM POSUSER.POS_REC_DETL WHERE PID_ID IN ("
				+ c_id + ")";

		PreparedStatement PosQueryDetl = dbPos.prepareStatement(QueryDocDetl);
		ResultSet PosResultDetl = PosQueryDetl.executeQuery();

		if (PosResultDetl.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocDetl);
			while (PosResultDetl.next()) {

				items += PosResultDetl.getString("ITEM") + ",";// Se concatenan los items para futuros filtros

				String PID_ID = PosResultDetl.getString("PID_ID");
				String ITEM = PosResultDetl.getString("ITEM");
				String RECEIVED_QTY = PosResultDetl.getString("RECEIVED_QTY");
				String PV_RETAIL_PRICE = PosResultDetl.getString("PV_RETAIL_PRICE");
				String REMISION = PosResultDetl.getString("REMISION");
				String PARTITION_DATE = PosResultDetl.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "ITEM : " + ITEM + " | "
						+ "RECEIVED_QTY : " + RECEIVED_QTY + " | " + "PV_RETAIL_PRICE : " + PV_RETAIL_PRICE + " | "
						+ "REMISION : " + REMISION + " | " + "PARTITION_DATE : " + PARTITION_DATE);
			}
			items = items.substring(0, items.length() - 1);
		} else {
			assertFalse(!PosResultDetl.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}
		PosResultDetl.close();
		PosQueryDetl.close();

		/********** Paso 4 *********/

		addStep("Ejecutar la interfaz runPR23Reple_MX_EXTEMP");

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

		/********** Paso 5 *********/

		addStep("Revisar la corrida de la interfaz en la tabla de WM_LOG_RUN");

		String QueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR23Reple_MXext'" + " AND  START_DT >= TRUNC(SYSDATE)"
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

		/********** Paso 7 *********/

		addStep("Revisar que el documento haya cambiado a Status E en POS_INB_DOC_RMS");

		String QueryChageDoc = " SELECT DISTINCT ID, PV_CR_TIENDA, PV_CR_PLAZA, STATUS, TARGET_ID FROM POSUSER.POS_INB_DOC_RMS"
				+ " WHERE STATUS = 'E'" + " AND DOC_TYPE = 'REC'" + " AND ID IN (" + c_id + ")";

		PreparedStatement posChageQuery = dbPos.prepareStatement(QueryChageDoc);
		ResultSet posEResult = posChageQuery.executeQuery();

		if (posEResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryChageDoc);
			while (posEResult.next()) {
				// ID, PV_CR_TIENDA, PV_CR_PLAZA, STATUS, TARGET_ALT_ID
				t_id += posEResult.getString("TARGET_ID") + ",";

				String ID = posEResult.getString("ID");
				String PV_CR_TIENDA = posEResult.getString("PV_CR_TIENDA");
				String PV_CR_PLAZA = posEResult.getString("PV_CR_PLAZA");
				String STATUS = posEResult.getString("STATUS");
				String TARGET_ID = posEResult.getString("TARGET_ID");

				testCase.addTextEvidenceCurrentStep("--> " + " ID : " + ID + " PV_CR_TIENDA : " + PV_CR_TIENDA
						+ " PV_CR_PLAZA : " + PV_CR_PLAZA + " STATUS : " + STATUS + " TARGET_ID : " + TARGET_ID);
			}
			t_id = t_id.substring(0, t_id.length() - 1);
		} else {
			assertFalse(!posEResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}
		posEResult.close();
		posChageQuery.close();

		/********** Paso 8 *********/

		addStep("Revisar la información generada en tabla XPOS_EXTEMP_INBOUND_MOVS");

		String QueryXposMovs = "SELECT EO_WEB_EXTEMP_MOV_ID_PK AS MOV_ID, DOC_NAME, STATUS_DESC, ITEM, EXT_REF_NO, ORDER_NO, CREATED_DATE\r\n"
				+ "FROM EO_WEB.XPOS_EXTEMP_INBOUND_MOVS WHERE PID_ID IN ( " + c_id + " ) AND ITEM IN (" + items
				+ ")";

		String DasQuery = String.format(QueryXposMovs);
		SQLResult DasQueryXpos = dbDas.executeQuery(DasQuery);

		if (!DasQueryXpos.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(DasQueryXpos);
		} else {
			assertFalse(!PosResultDetl.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Envio de confirmaciones de ordenes de compra de manera extemporanea";
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
		return "1.- Tener las configuraciones de la interfaz en las tablas WM_OXXO_ROLLOUT_CONFIG, WM_INTERFASE_CONFIG, WM_TRANCODE_TOPOLOGY_CFG.\r\n"
				+ "2.- Tener ordenes de compra pendientes a procesar en las tablas de POS_INB_DOC_RMS, POS_REC y POS_REC_DETL.\r\n"
				+ "3.- Tener acceso a las BD (POSUSER, DAS_OPERATIVO, MOMPERF).\r\n"
				+ "4.- La fecha del documento debe ser menor a la fecha de cierre de la tienda o plaza.";
	}

}
