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

/* DESCRIPCION : Envio de confirmaciones de ordenes de compra.
 * 
 * MTC_FT_PR23_REPLE_MX_010- Envio de confirmaciones de ordenes de compra (REC) por STG
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (REC).
 * 	Tener Ordenado el job de la interfaz PR23REPLE_MX_OLD
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con la confirmación de ORDEN de compra.
 * 
 * @Author Rafael Alarcón
 * @Date 19/Octubre/2024
 */
public class ATC_FT_002_PR23REPLEMXOLD_OrdenCompraSTG extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_PR23REPLEMXOLD_OrdenCompraSTG_test(HashMap<String, String> data) throws Exception {

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

		String c_id = "";

		String pe_id = "";

		String n_order = "";

		String items = "";

		String run_id = "";

		String t_id = "";

		String n_seq = "";

		String d_seq_no = "";

		/**************************** Pasos de la prueba ******************************/

		/********** Paso 1 *********/

		addStep("Validar que exista un documento pendiente por procesar en POS_INBOUND_DOCS");// Revisar los campos
																								// (Cambio necesario)

		String QueryDoc = "SELECT  /*+ INDEX(a,ENV_PK) INDEX(c,REC_PK) INDEX(b,POS_INBOUND_DOCS_IDX_003)*/ B.ID, PE_ID, PV_DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA, B.PARTITION_DATE                                         \r\n"
				+ "  FROM POSUSER.POS_ENVELOPE A, POSUSER.POS_REC C, POSUSER.POS_INBOUND_DOCS B \r\n"
				+ "  JOIN POSUSER.PLAZAS P ON SUBSTR(B.PV_DOC_NAME,4,5) = P.CR_PLAZA AND P.PAIS = 'MEX'\r\n"
				+ "JOIN WMUSER.WM_OXXO_ROLLOUT_CONFIG R ON SUBSTR(B.PV_DOC_NAME,4,5) = R.CR_PLAZA AND R.V16='Y'\r\n"
				+ "  JOIN POSUSER.POS_STORE_MINTRANDATE M ON SUBSTR(B.PV_DOC_NAME,4,5) = M.CR_PLAZA and SUBSTR(B.PV_DOC_NAME,9,5) = M.CR_TIENDA\r\n"
				+ " WHERE B.PE_ID = A.ID          \r\n" + "   AND B.DOC_TYPE = 'REC'                \r\n"
				+ "   AND B.STATUS = 'I'                                                    \r\n"
				+ "   AND C.PID_ID = B.ID\r\n" + "   AND C.PV_CVE_MVT=10\r\n" + "   AND C.ORDER_NO<>0\r\n"
				+ "   and (C.CREATED_DATE>M.MIN_TRANDATE or M.MIN_TRANDATE is null)\r\n"
				+ "   and  B.partition_date > sysdate - 60\r\n"
				+ " GROUP BY PV_CR_PLAZA, PV_CR_TIENDA, B.ID, PV_DOC_NAME, B.PARTITION_DATE, PE_ID";

		PreparedStatement PosQuery = dbPos.prepareStatement(QueryDoc);
		ResultSet PosResult = PosQuery.executeQuery();

		if (PosResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc);
			while (PosResult.next()) {

				c_id += PosResult.getString("ID") + ",";// Se concatenan los ID para proximas consultas/filtros.
				pe_id += PosResult.getString("PE_ID") + ",";

				String ID = PosResult.getString("ID");
				String PE_ID = PosResult.getString("PE_ID");
				String DOC_NAME = PosResult.getString("PV_DOC_NAME");
				String PLAZA = PosResult.getString("PV_CR_PLAZA");
				String TIENDA = PosResult.getString("PV_CR_TIENDA");
				String PARTITION = PosResult.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "ID : " + ID + " | " + "PE_ID : " + PE_ID + " | "
						+ "DOC_NAME : " + DOC_NAME + " | " + "PV_CR_PLAZA : " + PLAZA + " | " + "PV_CR_TIENDA : "
						+ TIENDA + " | " + "PARTITION_DATE : " + PARTITION);

			}
			c_id = c_id.substring(0, c_id.length() - 1);
			pe_id = pe_id.substring(0, pe_id.length() - 1);// Se borra la coma que tienen el último registro
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

		addStep("Revisar que el ORDER_NO tenga relación con un registro de orden de compra en la tabla ORDLOC");

		String QueryOrderNo = "SELECT ORDER_NO, ITEM, LOCATION, UNIT_RETAIL, UNIT_COST, COST_SOURCE FROM RMS.ORDLOC WHERE ORDER_NO IN ("
				+ n_order + ") AND ITEM IN (" + items + ") AND LOCATION = " + data.get("location");

		PreparedStatement MomQuery = dbMomperf.prepareStatement(QueryOrderNo);
		ResultSet MomResult = MomQuery.executeQuery();

		if (MomResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryOrderNo);
			while (MomResult.next()) {

				String ORDER_NO = MomResult.getString("ORDER_NO");
				String ITEM = MomResult.getString("ITEM");
				String LOCATION = MomResult.getString("LOCATION");
				String UNIT_RETAIL = MomResult.getString("UNIT_RETAIL");
				String UNIT_COST = MomResult.getString("UNIT_COST");
				String COST_SOURCE = MomResult.getString("COST_SOURCE");

				testCase.addTextEvidenceCurrentStep("--> " + "ORDER_NO : " + ORDER_NO + " | " + "ITEM : " + ITEM + " | "
						+ "LOCATION : " + LOCATION + " | " + "UNIT_RETAIL : " + UNIT_RETAIL + " | " + "UNIT_COST : "
						+ UNIT_COST + " | " + "COST_SOURCE : " + COST_SOURCE);
			}
		} else {
			assertFalse(!MomResult.next(), "--> ¡ERROR!, No existe información relacionada al ORDER_NO");
		}
		MomResult.close();
		MomQuery.close();

		/********** Paso 5 *********/

		addStep("Revisar que exista relación del documento en la tabla POS_ENVELOPE");

		String QueryEnvelp = "SELECT ID, PV_CR_PLAZA, PV_CR_TIENDA, PV_ENVELOPE_ID, NO_DOCUMENTS, PARTITION_DATE FROM POSUSER.POS_ENVELOPE WHERE ID IN ("
				+ pe_id + ")";

		PreparedStatement PosQueryEnv = dbPos.prepareStatement(QueryEnvelp);
		ResultSet PosResultEnv = PosQueryEnv.executeQuery();

		if (PosResultEnv.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryEnvelp);
			while (PosResultEnv.next()) {

				String ID = PosResultEnv.getString("ID");
				String PV_CR_PLAZA = PosResultEnv.getString("PV_CR_PLAZA");
				String PV_CR_TIENDA = PosResultEnv.getString("PV_CR_TIENDA");
				String PV_ENVELOPE_ID = PosResultEnv.getString("PV_ENVELOPE_ID");
				String NO_DOCUMENTS = PosResultEnv.getString("NO_DOCUMENTS");
				String PARTITION_DATE = PosResultEnv.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "ID : " + ID + " | " + "PV_CR_PLAZA : " + PV_CR_PLAZA
						+ " | " + "PV_CR_TIENDA : " + PV_CR_TIENDA + " | " + "PV_ENVELOPE_ID : " + PV_ENVELOPE_ID
						+ " | " + "NO_DOCUMENTS : " + NO_DOCUMENTS + " | " + "PARTITION_DATE : " + PARTITION_DATE);
			}
		} else {
			assertFalse(!PosResultEnv.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 6 *********/

		addStep("Ejecutar la interfaz runPR23Reple_MX_OLD");

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

		addStep("Revisar la corrida de la interfaz en la tabla de WM_LOG_RUN");

		String QueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR23Reple_MX_OLD'" + " AND  START_DT >= TRUNC(SYSDATE)"
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

		/********** Paso 7 *********/

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
		/********** Paso 9 *********/

		addStep("Revisar que el documento haya cambiado a Status E en POS_INBOUND_DOCS");

		String QueryChageDoc = "SELECT DISTINCT ID, PE_ID, STATUS, PV_DOC_NAME, TARGET_ID FROM POSUSER.POS_INBOUND_DOCS"
				+ " WHERE STATUS = 'E'" + " AND DOC_TYPE = 'REC'" + " AND ID IN (" + c_id + ")";// Cambiar consultaaaa

		PreparedStatement posChageQuery = dbPos.prepareStatement(QueryChageDoc);
		ResultSet posEResult = posChageQuery.executeQuery();

		if (posEResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryChageDoc);
			while (posEResult.next()) {
				// ID, PV_CR_TIENDA, PV_CR_PLAZA, STATUS, TARGET_ALT_ID
				t_id += posEResult.getString("TARGET_ID") + ",";

				String ID = posEResult.getString("ID");
				String PE_ID = posEResult.getString("PE_ID");
				String PV_DOC_NAME = posEResult.getString("PV_DOC_NAME");
				String STATUS = posEResult.getString("STATUS");
				String TARGET_ID = posEResult.getString("TARGET_ID");

				testCase.addTextEvidenceCurrentStep("--> " + " ID : " + ID + " PE_ID : " + PE_ID + " PV_DOC_NAME : "
						+ PV_DOC_NAME + " STATUS : " + STATUS + " TARGET_ID : " + TARGET_ID);
			}
			t_id = t_id.substring(0, t_id.length() - 1);
		} else {
			assertFalse(!posEResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}
		posEResult.close();
		posChageQuery.close();

		/********** Paso 10 *********/

		addStep("Revisar la información generada en las tablas TB_RECEIPTDESC, TB_RECEIPT, TB_RECEIPTDTL, TB_RECEIPTDTLEXT");

		String QueryRmsStg = "SELECT SEQ_NO, STATUS_CTRL, CR_TIENDA, REGION, CREATE_DATE, INTERFACE FROM RMS_INT.TB_RECEIPTDESC WHERE SEQ_NO IN ("
				+ t_id + ")";

		PreparedStatement PosMomStg = dbMomperf.prepareStatement(QueryRmsStg);
		ResultSet PosResultStg = PosMomStg.executeQuery();

		if (PosResultStg.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryRmsStg);
			while (PosResultStg.next()) {

				n_seq += PosResultStg.getString("SEQ_NO") + ",";

				String SEQ_NO = PosResultStg.getString("SEQ_NO");
				String STATUS_CTRL = PosResultStg.getString("STATUS_CTRL");
				String CR_TIENDA = PosResultStg.getString("CR_TIENDA");
				String REGION = PosResultStg.getString("REGION");
				String CREATE_DATE = PosResultStg.getString("CREATE_DATE");
				String INTERFACE = PosResultStg.getString("INTERFACE");

				testCase.addTextEvidenceCurrentStep("--> " + "SEQ_NO : " + SEQ_NO + " | " + "STATUS_CTRL : "
						+ STATUS_CTRL + " | " + "CR_TIENDA : " + CR_TIENDA + " | " + "REGION : " + REGION + " | "
						+ "CREATE_DATE : " + CREATE_DATE + " | " + "INTERFACE : " + INTERFACE);

			}
			n_seq = n_seq.substring(0, n_seq.length() - 1);
		} else {
			assertFalse(!PosResultStg.next(), "--> ¡ERROR!, No hay información relacionado con el TARGET_ID");
		}

		String QueryRmsStg2 = "SELECT DESC_SEQ_NO, SEQ_NO, DC_DEST_ID, PO_NBR, DOCUMENT_TYPE, CREATE_DATE FROM RMS_INT.TB_RECEIPT WHERE DESC_SEQ_NO IN ("
				+ n_seq + ")";

		PreparedStatement PosMomStg2 = dbMomperf.prepareStatement(QueryRmsStg2);
		ResultSet PosResultStg2 = PosMomStg2.executeQuery();

		if (PosResultStg2.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryRmsStg2);
			while (PosResultStg2.next()) {

				d_seq_no += PosResultStg2.getString("DESC_SEQ_NO") + ",";

				String DESC_SEQ_NO = PosResultStg2.getString("DESC_SEQ_NO");
				String SEQ_NO = PosResultStg2.getString("SEQ_NO");
				String DC_DEST_ID = PosResultStg2.getString("DC_DEST_ID");
				String PO_NBR = PosResultStg2.getString("PO_NBR");
				String DOCUMENT_TYPE = PosResultStg2.getString("DOCUMENT_TYPE");
				String CREATE_DATE = PosResultStg2.getString("CREATE_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "DESC_SEQ_NO : " + DESC_SEQ_NO + " | " + "SEQ_NO : "
						+ SEQ_NO + " | " + "DC_DEST_ID : " + DC_DEST_ID + " | " + "PO_NBR : " + PO_NBR + " | "
						+ "DOCUMENT_TYPE : " + DOCUMENT_TYPE + " | " + "CREATE_DATE : " + CREATE_DATE);

			}
			d_seq_no = d_seq_no.substring(0, d_seq_no.length() - 1);
		} else {
			assertFalse(!PosResultStg2.next(), "--> ¡ERROR!, No hay información relacionado con el TARGET_ID");
		}

		String QueryRmsStg3 = "SELECT DESC_SEQ_NO, RECEIPT_SEQ_NO, SEQ_NO, ITEM_ID, RECEIPT_DATE, CREATE_DATE FROM RMS_INT.TB_RECEIPTDTL WHERE DESC_SEQ_NO IN ("
				+ d_seq_no + ") AND ITEM_ID IN (" + items + ") AND TRUNC(CREATE_DATE) = TRUNC(SYSDATE)";

		PreparedStatement PosMomStg3 = dbMomperf.prepareStatement(QueryRmsStg3);
		ResultSet PosResultStg3 = PosMomStg3.executeQuery();

		if (PosResultStg3.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryRmsStg3);
			while (PosResultStg3.next()) {

				String DESC_SEQ_NO = PosResultStg3.getString("DESC_SEQ_NO");
				String SEQ_NO = PosResultStg3.getString("SEQ_NO");
				String RECEIPT_SEQ_NO = PosResultStg3.getString("RECEIPT_SEQ_NO");
				String ITEM_ID = PosResultStg3.getString("ITEM_ID");
				String RECEIPT_DATE = PosResultStg3.getString("RECEIPT_DATE");
				String CREATE_DATE = PosResultStg3.getString("CREATE_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "DESC_SEQ_NO : " + DESC_SEQ_NO + " | " + "SEQ_NO : "
						+ SEQ_NO + " | " + "RECEIPT_SEQ_NO : " + RECEIPT_SEQ_NO + " | " + "ITEM_ID : " + ITEM_ID + " | "
						+ "RECEIPT_DATE : " + RECEIPT_DATE + " | " + "CREATE_DATE : " + CREATE_DATE);

			}
		} else {
			assertFalse(!PosResultStg3.next(), "--> ¡ERROR!, No hay información relacionado con el TARGET_ID");
		}

		String QueryRmsStg4 = "SELECT * FROM RMS_INT.TB_RECEIPTDTLEXT WHERE DESC_SEQ_NO IN (" + d_seq_no + ")";

		PreparedStatement PosMomStg4 = dbMomperf.prepareStatement(QueryRmsStg4);
		ResultSet PosResultStg4 = PosMomStg4.executeQuery();

		if (PosResultStg4.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryRmsStg4);
			while (PosResultStg4.next()) {

				String DESC_SEQ_NO = PosResultStg4.getString("DESC_SEQ_NO");
				String RECEIPT_SEQ_NO = PosResultStg4.getString("RECEIPT_SEQ_NO");
				String RECEIPTDTL_SEQ_NO = PosResultStg4.getString("RECEIPTDTL_SEQ_NO");
				String POS_RETAIL_PRICE = PosResultStg4.getString("POS_RETAIL_PRICE");
				String CREATE_DATE = PosResultStg4.getString("CREATE_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "DESC_SEQ_NO : " + DESC_SEQ_NO + " | "
						+ "RECEIPT_SEQ_NO : " + RECEIPT_SEQ_NO + " | " + "RECEIPT_SEQ_NO : " + RECEIPT_SEQ_NO + " | "
						+ "RECEIPTDTL_SEQ_NO : " + RECEIPTDTL_SEQ_NO + " | " + "POS_RETAIL_PRICE : " + POS_RETAIL_PRICE
						+ " | " + "CREATE_DATE : " + CREATE_DATE);

			}
		} else {
			assertFalse(!PosResultStg4.next(), "--> ¡ERROR!, No hay información relacionado con el TARGET_ID");
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Envio de confirmaciones de ordenes de compra";
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
		return "1.- Tener las configuraciones de la interfaz en las tablas WM_OXXO_ROLLOUT_CONFIG, WM_INTERFASE_CONFIG, WM_TRANCODE_TOPOLOGY_CFG. \n\r"
				+ "2.- Tener confirmaciones de ordenes de compra pendientes a procesar en las tablas de POS_INBOUND_DOCS, POS_REC y POS_REC_DETL. \n\r"
				+ "3.- Tener acceso a las BD (POSUSER, DAS_OPERATIVO, MOMPERF).\n\r"
				+ "4.- Tener la configuración de la topología en STG";
	}

}
