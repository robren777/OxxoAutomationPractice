package WM.interfaces.PR28_MX;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/* DESCRIPCION : Procesar las ordenes de compra de POS a RMS con movimiento STG..
 * 
 * MTC_FT_20_PR28_MX_Envio Documento a STG OLD
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
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_PR28MXOLD_DocumentoSTG extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_006_PR28MXOLD_DocumentosSTG_test(HashMap<String, String> data) throws Exception {

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
		Connection dbPerf = DriverManager.getConnection(GlobalVariables.DB_HOST_MOMPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_MOMPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_MOMPERF_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A MOMPRF----------------------------------------------- ");

		testCase.setProject_Name("Transformacion multiformato");

		/****************************
		 * Variables de datos
		 *******************************/

		String d_id = "";
		String run_id = "";
		String items = "";
		String ext_ref = "";
		/**************************** Pasos de la prueba ******************************/

		/********** Paso 1 *********/

		addStep("Revisar que exista el documento en la tabla de POS_INB_DOC_RMS");

		String QueryDoc = "SELECT ID, STATUS, DOC_TYPE, PV_DOC_NAME, PARTITION_DATE \r\n"
				+ "FROM POS_INBOUND_DOCS WHERE DOC_TYPE = 'REC' AND PV_CR_PLAZA = '" + data.get("plaza")
				+ "' AND PV_CR_TIENDA = '" + data.get("tienda") + "' AND STATUS = 'I'";

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
						"--> " + "ID : " + ID + " | " + "STATUS : " + STATUS + " | " + "DOC_TYPE : " + DOC_TYPE + " | "
								+ "DOC_NAME : " + DOC_NAME + " | " + " PARTITION_DATE : " + PARTITION);

			}
			d_id = d_id.substring(0, d_id.length() - 1);// Se borra la coma que tienen el último registro
		} else {
			assertFalse(!PosResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult.close();
		PosQuery.close();

		/********** Paso 2 *********/

		addStep("Revisar que exista el encabezado del documento en la tabla POS_REC");

		String QueryDocEnc = "SELECT PID_ID, NO_RECORDS, PV_CVE_MVT, PV_CR_FROM_LOC, EXT_REF_NO, SUPPLIER, PARTITION_DATE FROM POS_REC WHERE PID_ID = "
				+ d_id;

		PreparedStatement PosQueryHeader = dbPos.prepareStatement(QueryDocEnc);
		ResultSet PosResultHeader = PosQueryHeader.executeQuery();

		if (PosResultHeader.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocEnc);
			while (PosResultHeader.next()) {

				ext_ref = PosResultHeader.getString("EXT_REF_NO");
				String PID_ID = PosResultHeader.getString("PID_ID");
				String NO_RECORDS = PosResultHeader.getString("NO_RECORDS");
				String PV_CVE_MVT = PosResultHeader.getString("PV_CVE_MVT");
				String PV_CR_FROM_LOC = PosResultHeader.getString("PV_CR_FROM_LOC");
				String EXT_REF_NO = PosResultHeader.getString("EXT_REF_NO");
				String SUPPLIER = PosResultHeader.getString("SUPPLIER");
				String PARTITION_DATE = PosResultHeader.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "NO_RECORDS : " + NO_RECORDS
						+ " | " + "PV_CVE_MVT : " + PV_CVE_MVT + " | " + " PV_CR_FROM_LOC : " + PV_CR_FROM_LOC + " | "
						+ " EXT_REF_NO : " + EXT_REF_NO + " | " + " SUPPLIER : " + SUPPLIER + " | "
						+ " PARTITION_DATE : " + PARTITION_DATE);
			}
		} else {
			assertFalse(!PosResultHeader.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 3 *********/

		addStep("Revisar que exista el detalle del documento en la tabla POS_REC_DETL");

		String QueryDocDetl = "SELECT PID_ID, ITEM, RECEIVED_QTY, BOL_NO, PV_RETAIL_PRICE, PARTITION_DATE FROM POS_REC_DETL WHERE PID_ID = "
				+ d_id;

		PreparedStatement PosQueryDetl = dbPos.prepareStatement(QueryDocDetl);
		ResultSet PosResultDetl = PosQueryDetl.executeQuery();

		if (PosResultDetl.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocDetl);
			while (PosResultDetl.next()) {
				items += PosResultDetl.getString("ITEM") + ",";// Se concatenan los items para futuros filtros

				String PID_ID = PosResultDetl.getString("PID_ID");
				String ITEM = PosResultDetl.getString("ITEM");
				String RECEIVED_QTY = PosResultDetl.getString("RECEIVED_QTY");
				String BOL_NO = PosResultDetl.getString("BOL_NO");
				String PV_RETAIL_PRICE = PosResultDetl.getString("PV_RETAIL_PRICE");
				String PARTITION_DATE = PosResultDetl.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "ITEM : " + ITEM + " | "
						+ "RECEIVED_QTY : " + RECEIVED_QTY + " | " + "BOL_NO : " + BOL_NO + " | " + "PV_RETAIL_PRICE : "
						+ PV_RETAIL_PRICE + " | " + " PARTITION_DATE : " + PARTITION_DATE);
			}
			items = items.substring(0, items.length() - 1);
		} else {
			assertFalse(!PosResultDetl.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 4 *********/

		addStep("Ejecutar la interfaz runPR28_MX_OLD ");

		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);

		Control_mInicio cm = new Control_mInicio(u, data.get("cm_user"), data.get("cm_ps"));
		u.get(data.get("cm_server"));
		u.hardWait(30);
		u.waitForLoadPage();
		cm.logOn();
		JSONObject obj = new JSONObject(data.get("cm_jobs"));
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
				+ " WHERE INTERFACE = 'PR28_MX_OLD'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

		PreparedStatement LogQuery = dbLog.prepareStatement(QueryIntegrationServer);
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
				+ " FROM WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_id;

		PreparedStatement LogThreadQuery = dbLog.prepareStatement(QueryStatusThread);
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

		addStep("Revisar que el documento haya cambiado a Status L en POS_INB_DOC_RMS");

		String QueryChageDoc = " SELECT DISTINCT ID, PV_CR_TIENDA, PV_CR_PLAZA, STATUS, TARGET_ID FROM POS_INB_DOC_RMS"
				+ " WHERE STATUS = 'L'" + " AND DOC_TYPE = 'REC'" + " AND ID IN (" + d_id + ")";

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

		addStep("Revisar la información generada en tabla TB_XORDERDESCEXT");

		String DocStgInv = "SELECT * FROM RMS_INT.TB_XORDERDESCEXT WHERE EXT_REF_NO = " + ext_ref;

		PreparedStatement posStgInv = dbPerf.prepareStatement(DocStgInv);
		ResultSet posStgInvResult = posStgInv.executeQuery();

		if (posStgInvResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(DocStgInv);
			while (posStgInvResult.next()) {

				String DESC_SEQ_NO = posStgInvResult.getString("DESC_SEQ_NO");
				String EXT_REF_NO = posStgInvResult.getString("EXT_REF_NO");
				String PO_TYPE = posStgInvResult.getString("PO_TYPE");
				String CREATE_DATE = posStgInvResult.getString("CREATE_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "DESC_SEQ_NO : " + DESC_SEQ_NO + " | " + "EXT_REF_NO : "
						+ EXT_REF_NO + " | " + "PO_TYPE : " + PO_TYPE + " | " + "CREATE_DATE : " + CREATE_DATE);
			}
		} else {
			assertFalse(!posStgInvResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}
	}

	@Override
	public String setTestFullName() {

		return null;

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub

		return "Procesar las ordenes de compra de POS a RMS con movimiento STG.";

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
		return "1- Tener insumos para procesar\r\n" + "2- Acceso a control M y job de la PR28_MX dado de alta\r\n"
				+ "3- Conexión a la VPN de OXXO\r\n"
				+ "4-Tener configurado en la tabla POS_INTERFASE_CONFIG el camino STG";
	}

}