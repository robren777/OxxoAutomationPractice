package WM.interfaces.PR01_MX;

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
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/* DESCRIPCION : Envio de documento de XPOS a RIB.
 * 
 * MTC-FT-012-Enviar la creaci�n de pedidos de transferencia para los sistemas legados XPOS hacia RMS.(RIB)
 * 
 * PREREQUISITOS:
 * 	1. Acceso a Control-M
	2. Contar con los archivos de las transferencias del POS a WM, hechas por la FEMSA_ENRED_IN.
	3. Obtener el ID de la secuencia para la transferencia
	4. Contar con el PV_CVE_MVT = '34'
	5. Contar con CEDIS.TIPO = 'L' O 'R'
 * 
 * INSUMOS:
 * 	Documentos TSF (Una transferencia).
 * 
 * @Author Gustavo Morales
 * @Date 28/Febrero/2025
 */
public class ATC_FT_012_PR01MXOLD_EnvioDocumentoXPOSaRIB extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_012_PR01MXOLD_EnvioDocumentoXPOSaRIB_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerías pr5 para la POS_INB_DOC_RMS
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
		Connection dbMom = DriverManager.getConnection(GlobalVariables.DB_HOST_MOMPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_MOMPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_MOMPERF_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A MOMPRF----------------------------------------------- ");

		testCase.setProject_Name("Transformacion Multiformato");

		/*
		 * Consultas para la ejecucion E2E
		 *********************************************************************/

		String run_status = "";
		String Id_doc = "";
		String itemS = "";
		String tsf_no = "";

		// ***************************************** Paso 1
		// *****************************************************

		addStep("Revisar que exista el documento en la tabla de POS_INBOUND_DOCS");

		String QueryDoc = "SELECT ID, STATUS, DOC_TYPE, PV_DOC_NAME, PARTITION_DATE FROM POS_INBOUND_DOCS WHERE DOC_TYPE = 'TSF' AND "
				+ "STATUS = 'I' AND SUBSTR(PV_DOC_NAME,4,5) = '" + data.get("plaza")
				+ "' AND SUBSTR(PV_DOC_NAME,9,5) = '" + data.get("tienda") + "'";

		PreparedStatement PosQuery = dbPos.prepareStatement(QueryDoc);
		ResultSet PosResult = PosQuery.executeQuery();

		if (PosResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc);
			while (PosResult.next()) {

				Id_doc += PosResult.getString("ID") + ",";// Se concatenan los ID para proximas consultas/filtros.

				String ID = PosResult.getString("ID");
				String STATUS = PosResult.getString("STATUS");
				String DOC_TYPE = PosResult.getString("DOC_TYPE");
				String DOC_NAME = PosResult.getString("PV_DOC_NAME");
				String PARTITION = PosResult.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep(
						"--> " + "ID : " + ID + " | " + "STATUS : " + STATUS + " | " + "DOC_TYPE : " + DOC_TYPE + " | "
								+ "DOC_NAME : " + DOC_NAME + " | " + " PARTITION_DATE : " + PARTITION);

			}
			Id_doc = Id_doc.substring(0, Id_doc.length() - 1);// Se borra la coma que tienen el último registro
		} else {
			assertFalse(!PosResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult.close();
		PosQuery.close();

		/********** Paso 2 *********/

		addStep("Revisar que exista el encabezado del documento en la tabla POS_TSF");

		String QueryDocEnc = "SELECT * FROM POSUSER.POS_TSF WHERE PID_ID IN (" + Id_doc + ")";

		PreparedStatement PosQueryHeader = dbPos.prepareStatement(QueryDocEnc);
		ResultSet PosResultHeader = PosQueryHeader.executeQuery();

		if (PosResultHeader.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocEnc);
			while (PosResultHeader.next()) {

				String PID_ID = PosResultHeader.getString("PID_ID");
				String NO_RECORDS = PosResultHeader.getString("NO_RECORDS");
				String PV_CVE_MVT = PosResultHeader.getString("PV_CVE_MVT");
				String SHIP_DATE = PosResultHeader.getString("SHIP_DATE");
				String PV_CR_FROM_LOC = PosResultHeader.getString("PV_CR_FROM_LOC");
				String PV_CR_TO_LOC = PosResultHeader.getString("PV_CR_TO_LOC");
				String CEDIS_ID = PosResultHeader.getString("CEDIS_ID");
				String BOL_NO = PosResultHeader.getString("BOL_NO");
				String EXT_REF_NO = PosResultHeader.getString("EXT_REF_NO");
				String PARTITION_DATE = PosResultHeader.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "NO_RECORDS : " + NO_RECORDS
						+ " | " + "PV_CVE_MVT : " + PV_CVE_MVT + " | " + " SHIP_DATE : " + SHIP_DATE + " | "
						+ " PV_CR_FROM_LOC : " + PV_CR_FROM_LOC + " | " + " PV_CR_TO_LOC : " + PV_CR_TO_LOC + " | "
						+ " CEDIS_ID : " + CEDIS_ID + " | " + "BOL_NO : " + BOL_NO + "|" + "EXT_REF_NO : " + EXT_REF_NO
						+ "|" + " PARTITION_DATE : " + PARTITION_DATE);
			}
		} else {
			assertFalse(!PosResultHeader.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 3 *********/

		addStep("Revisar que exista el detalle del documento en la tabla POS_TSF_DETL");

		String QueryDocDetl = "SELECT * FROM POSUSER.POS_TSF_DETL WHERE PID_ID IN (" + Id_doc + ")";

		PreparedStatement PosQueryDetl = dbPos.prepareStatement(QueryDocDetl);
		ResultSet PosResultDetl = PosQueryDetl.executeQuery();

		if (PosResultDetl.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocDetl);
			while (PosResultDetl.next()) {

				itemS += PosResultDetl.getString("ITEM") + ",";// Se concatenan los items para futuros filtros

				String PID_ID = PosResultDetl.getString("PID_ID");
				String ITEM = PosResultDetl.getString("ITEM");
				String SHIP_QTY = PosResultDetl.getString("SHIP_QTY");
				String PV_MOTIVO = PosResultDetl.getString("PV_MOTIVO");
				String PV_RETAIL_PRICE = PosResultDetl.getString("PV_RETAIL_PRICE");
				String PARTITION_DATE = PosResultDetl.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "ITEM : " + ITEM + " | "
						+ "SHIP_QTY : " + SHIP_QTY + " | " + "PV_MOTIVO : " + PV_MOTIVO + " | " + "PV_RETAIL_PRICE : "
						+ PV_RETAIL_PRICE + "|" + " PARTITION_DATE : " + PARTITION_DATE);
			}
			itemS = itemS.substring(0, itemS.length() - 1);
		} else {
			assertFalse(!PosResultDetl.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 4 *********/

		addStep("Ejecucion job : PR01_MX_OLD en  control-M");
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

		/********** Paso 5 *********/

		addStep("Revisar la corrida de la interfaz en la tabla de WM_LOG_RUN");

		String QueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR01_MX_OLD'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

		PreparedStatement LogQuery = dbLog.prepareStatement(QueryIntegrationServer);
		ResultSet LogResult = LogQuery.executeQuery();

		if (LogResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryIntegrationServer);
			while (LogResult.next()) {
				// RUN_ID,START_DT,STATUS
				run_status = LogResult.getString("RUN_ID");
				String START_DT = LogResult.getString("START_DT");
				String STATUS = LogResult.getString("STATUS");

				testCase.addTextEvidenceCurrentStep(
						"--> " + " RUN_ID : " + run_status + " START_DT : " + START_DT + " STATUS : " + STATUS);
			}
		} else {
			assertFalse(!LogResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}
		LogResult.close();
		LogQuery.close();

		/********** Paso 6 *********/

		addStep("Revisar los threads levantados en la tabla WM_LOG_THREADS");

		String QueryStatusThread = " SELECT PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5"
				+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_status;

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

		addStep("Revisar que el documento haya cambiado a Status E en POS_INBOUND_DOCS");

		String QueryChageDoc = " SELECT DISTINCT ID, STATUS, DOC_TYPE, PV_DOC_NAME, PARTITION_DATE FROM POSUSER.POS_INBOUND_DOCS"
				+ " WHERE STATUS = 'E'" + " AND DOC_TYPE = 'TSF'" + " AND ID IN (" + Id_doc + ")";

		PreparedStatement posChageQuery = dbPos.prepareStatement(QueryChageDoc);
		ResultSet posEResult = posChageQuery.executeQuery();

		if (posEResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryChageDoc);
			while (posEResult.next()) {

				String ID = posEResult.getString("ID");
				String DOC_TYPE = posEResult.getString("DOC_TYPE");
				String PV_DOC_NAME = posEResult.getString("PV_DOC_NAME");
				String STATUS = posEResult.getString("STATUS");
				String PARTITION_DATE = posEResult.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep(
						"--> " + " ID : " + ID + " DOC_TYPE : " + DOC_TYPE + " PV_DOC_NAME : " + PV_DOC_NAME
								+ " STATUS : " + STATUS + " PARTITION_DATE : " + PARTITION_DATE);
			}
		} else {
			assertFalse(!posEResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}
		posEResult.close();
		posChageQuery.close();

		/********** Paso 7 *********/

		addStep("Revisar la informacion generada en tabla TSFHEAD");

		String DocStgInv = "SELECT TSF_NO, FROM_LOC, TO_LOC, TSF_TYPE, CREATE_DATE, CONTEXT_TYPE FROM RMS.TSFHEAD\r\n"
				+ "WHERE TO_LOC = '18716'" + "AND CREATE_DATE >=TRUNC(sysdate)" + "AND CONTEXT_TYPE = 'XPOS'";

		PreparedStatement posStgInv = dbMom.prepareStatement(DocStgInv);
		ResultSet posStgInvResult = posStgInv.executeQuery();

		if (posStgInvResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(DocStgInv);
			while (posStgInvResult.next()) {

				tsf_no = posStgInvResult.getString("TSF_NO");
				String TSF_NO = posStgInvResult.getString("TSF_NO");
				String FROM_LOC = posStgInvResult.getString("FROM_LOC");
				String TO_LOC = posStgInvResult.getString("TO_LOC");
				String TSF_TYPE = posStgInvResult.getString("TSF_TYPE");
				String CREATE_DATE = posStgInvResult.getString("CREATE_DATE");
				String CONTEXT_TYPE = posStgInvResult.getString("CONTEXT_TYPE");

				testCase.addTextEvidenceCurrentStep("--> " + "TSF_NO : " + TSF_NO + " | " + "FROM_LOC : " + FROM_LOC
						+ " | " + "TO_LOC : " + TO_LOC + " | " + "TSF_TYPE : " + TSF_TYPE + " | " + "CREATE_DATE: "
						+ CREATE_DATE + " | " + "CONTEXT_TYPE: " + CONTEXT_TYPE);
			}
		} else {
			assertFalse(!posStgInvResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}

		/********** Paso 8 *********/

		addStep("Revisar la informacion generada en tabla TSFDETAIL");

		String DocStgInv1 = "SELECT TSF_NO, TSF_SEQ_NO, ITEM FROM RMS.TSFDETAIL\r\n" + "WHERE TSF_NO = " + "'" + tsf_no
				+ "'";

		PreparedStatement posStgInv1 = dbMom.prepareStatement(DocStgInv1);
		ResultSet posStgInvResult1 = posStgInv1.executeQuery();

		if (posStgInvResult1.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(DocStgInv1);
			while (posStgInvResult1.next()) {

				String TSF_NO = posStgInvResult1.getString("TSF_NO");
				String TSF_SEQ_NO = posStgInvResult1.getString("TSF_SEQ_NO");
				String ITEM = posStgInvResult1.getString("ITEM");

				testCase.addTextEvidenceCurrentStep("--> " + "TSF_NO : " + TSF_NO + " | " + "TSF_SEQ_NO : " + TSF_SEQ_NO
						+ " | " + "ITEM : " + ITEM);
			}
		} else {
			assertFalse(!posStgInvResult1.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}
	}

	@Override
	public String setTestFullName() {

		return null;

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub

		return "Envio de documento de XPOS a RIB";

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
				+ "4. Que el job de la PR01_MX_OLD este dado de alta\r\n" + "5. Acceso al punto de venta";
	}

}
