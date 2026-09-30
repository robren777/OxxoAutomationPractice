package WM.interfaces.PR05_MX;

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

/* DESCRIPCION : Envio de devolucion (RTV) hacía RMS de tipo STG.
 * 
 * MTC_FT_PR05_MX_003-Ejecucion PR05_MX, enviar documentos RTV a STG
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (RTV).
 * 	Tener Ordenado el job de la interfaz PR05_MX
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos RTV (Una devolucion para proveedores).
 * 
 * @Author Gustavo Morales
 * @Date 16/Diciembre/2024
 */
public class ATC_FT_003_PR05MX_DocumentosSTG extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_003_PR05MX_DocumentosSTG_test(HashMap<String, String> data) throws Exception {

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

		testCase.setProject_Name("Transformación Multiformato");

		/*
		 * Consultas para la ejecucion E2E
		 *********************************************************************/

		String run_status = "";
		String Id_doc = "";
		String itemS = "";
		String ext_ref_no = "";

		// ***************************************** Paso 1
		// *****************************************************

		addStep("Revisar que exista el documento en la tabla de POS_INB_DOC_RMS");

		String QueryDoc = "SELECT ID, PV_CR_PLAZA, PV_CR_TIENDA, STATUS, DOC_TYPE, PV_DOC_NAME, PARTITION_DATE \r\n"
				+ "FROM POSUSER.POS_INB_DOC_RMS WHERE DOC_TYPE = 'RTV' AND PV_CR_PLAZA = '" + data.get("plaza")
				+ "' AND PV_CR_TIENDA = '" + data.get("tienda") + "' AND STATUS = 'I'";

		PreparedStatement PosQuery = dbPos.prepareStatement(QueryDoc);
		ResultSet PosResult = PosQuery.executeQuery();

		if (PosResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc);
			while (PosResult.next()) {

				Id_doc += PosResult.getString("ID") + ",";// Se concatenan los ID para proximas consultas/filtros.

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
			Id_doc = Id_doc.substring(0, Id_doc.length() - 1);// Se borra la coma que tienen el último registro
		} else {
			assertFalse(!PosResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult.close();
		PosQuery.close();

		/********** Paso 2 *********/

		addStep("Revisar que exista el encabezado del documento en la tabla POS_RTV");

		String QueryDocEnc = "SELECT * FROM POSUSER.POS_RTV WHERE PID_ID = " + Id_doc;

		PreparedStatement PosQueryHeader = dbPos.prepareStatement(QueryDocEnc);
		ResultSet PosResultHeader = PosQueryHeader.executeQuery();

		if (PosResultHeader.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocEnc);
			while (PosResultHeader.next()) {

				ext_ref_no = PosResultHeader.getString("EXT_REF_NO");
				String PID_ID = PosResultHeader.getString("PID_ID");
				String NO_RECORDS = PosResultHeader.getString("NO_RECORDS");
				String CREATED_DATE = PosResultHeader.getString("CREATED_DATE");
				String PV_CR_FROM_LOC = PosResultHeader.getString("PV_CR_FROM_LOC");
				String EXT_REF_NO = PosResultHeader.getString("EXT_REF_NO");
				String SUPPLIER = PosResultHeader.getString("SUPPLIER");
				String REMISION = PosResultHeader.getString("REMISION");
				String PARTITION_DATE = PosResultHeader.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "NO_RECORDS : " + NO_RECORDS
						+ " | " + "CREATED_DATE : " + CREATED_DATE + " | " + " PV_CR_FROM_LOC : " + PV_CR_FROM_LOC
						+ " | " + " EXT_REF_NO : " + EXT_REF_NO + " | " + " SUPPLIER : " + SUPPLIER + " | "
						+ " REMISION : " + REMISION + " | " + " PARTITION_DATE : " + PARTITION_DATE);
			}
		} else {
			assertFalse(!PosResultHeader.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 3 *********/

		addStep("Revisar que exista el detalle del documento en la tabla POS_RTV_DETL");

		String QueryDocDetl = "SELECT * FROM POSUSER.POS_RTV_DETL WHERE PID_ID = " + Id_doc;

		PreparedStatement PosQueryDetl = dbPos.prepareStatement(QueryDocDetl);
		ResultSet PosResultDetl = PosQueryDetl.executeQuery();

		if (PosResultDetl.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocDetl);
			while (PosResultDetl.next()) {

				itemS += PosResultDetl.getString("ITEM") + ",";// Se concatenan los items para futuros filtros

				String PID_ID = PosResultDetl.getString("PID_ID");
				String ITEM = PosResultDetl.getString("ITEM");
				String QTY_RETURNED = PosResultDetl.getString("QTY_RETURNED");
				String PRICE = PosResultDetl.getString("PV_RETAIL_PRICE");
				String PARTITION_DATE = PosResultDetl.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "ITEM : " + ITEM + " | "
						+ "QTY_RETURNED : " + QTY_RETURNED + " | " + "PV_RETAIL_PRICE : " + PRICE + " | "
						+ " PARTITION_DATE : " + PARTITION_DATE);
			}
			itemS = itemS.substring(0, itemS.length() - 1);
		} else {
			assertFalse(!PosResultDetl.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 4 *********/

		addStep("Ejecucion job : PR05_MX en  control-M");
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

		/********** Paso 5 *********/

		addStep("Revisar la corrida de la interfaz en la tabla de WM_LOG_RUN");

		String QueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR05_MX'" + " AND  START_DT >= TRUNC(SYSDATE)"
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

		addStep("Revisar que el documento haya cambiado a Status E en POS_INB_DOC_RMS");

		String QueryChageDoc = " SELECT DISTINCT ID, PV_CR_TIENDA, PV_CR_PLAZA, STATUS, TARGET_ID FROM POSUSER.POS_INB_DOC_RMS"
				+ " WHERE STATUS = 'E'" + " AND DOC_TYPE = 'RTV'" + " AND ID IN (" + Id_doc + ")";

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

		addStep("Revisar la información generada en tabla NB_RTV_HEAD_CFA_EXT_STG");

		String DocStgInv = "SELECT EXT_REF_NO, DC_DEST_ID, VENDOR_NO, CREATION_TS, FEM_REMISION, WM_TARGET_ID FROM RMS_INT.NB_RTV_HEAD_CFA_EXT_STG\r\n"
				+ "WHERE DC_DEST_ID = '21222'" + " AND VENDOR_NO = '47353'" + "AND EXT_REF_NO = " + ext_ref_no;

		PreparedStatement posStgInv = dbMom.prepareStatement(DocStgInv);
		ResultSet posStgInvResult = posStgInv.executeQuery();

		if (posStgInvResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(DocStgInv);
			while (posStgInvResult.next()) {

				String EXT_REF_NO = posStgInvResult.getString("EXT_REF_NO");
				String DC_DEST_ID = posStgInvResult.getString("DC_DEST_ID");
				String VENDOR_NO = posStgInvResult.getString("VENDOR_NO");
				String CREATION_TS = posStgInvResult.getString("CREATION_TS");
				String FEM_REMISION = posStgInvResult.getString("FEM_REMISION");
				String WM_TARGET_ID = posStgInvResult.getString("WM_TARGET_ID");

				testCase.addTextEvidenceCurrentStep("--> " + "EXT_REF_NO : " + EXT_REF_NO + " | " + "DC_DEST_ID : "
						+ DC_DEST_ID + " | " + "VENDOR_NO : " + VENDOR_NO + " | " + "CREATION_TS : " + CREATION_TS
						+ " | " + "FEM_REMISION : " + FEM_REMISION + " | " + "WM_TARGET_ID : " + WM_TARGET_ID);
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

		return "Envio de devolucion (RTV) hacía RMS de tipo STG";

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
				+ "4. Que el job de la PR5 este dado de alta\r\n" + "5. Acceso al punto de venta\r\n"
				+ "6. Arquitectura FEMSA_ENRED_IN";
	}

}
