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

/* DESCRIPCION : Envio de documento de DSD a RIB.
 * 
 * MTC-FT-014-Enviar la creaci�n de pedidos de transferencia para los sistemas legados DSD hacia RMS.(RIB)
 * 
 * PREREQUISITOS:
 * 	1. Acceso a Control-M
	2. Contar con los archivos de las transferencias del POS a WM, hechas por la FEMSA_ENRED_IN.
	3. Obtener el ID de la secuencia para la transferencia
	4. Contar con CEDIS.TIPO = 'L' O 'R'
	5. Contar con el to_loc_type='S' de la tabla XXFC_PC_MASTER.
 * 
 * INSUMOS:
 * 	Documentos TSF (Una transferencia).
 * 
 * @Author Gustavo Morales
 * @Date 28/Febrero/2025
 */
public class ATC_FT_014_PR01MXOLD_EnvioDocumentoDSDaRIB extends BaseExecution {
	@Test(dataProvider = "data-provider")

	public void ATC_FT_014_PR01MXOLD_EnvioDocumentoDSDaRIB_test(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerías pr5 para la POS_INB_DOC_RMS
		 *********************************************************************/
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		// POSUSER/
		Connection dbDas = DriverManager.getConnection(GlobalVariables.DB_HOST_DAS_MULTIFORMATO_QA1,
				GlobalVariables.DB_USER_DAS_MULTIFORMATO_QA1, GlobalVariables.DB_PASSWORD_DAS_MULTIFORMATO_QA1);
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
		String target_id = "";

		// ***************************************** Paso 1
		// *****************************************************

		addStep("Revisar que exista el documento en la tabla de XXFC_PC_TSF");

		String QueryDoc = "SELECT PC_ID, STORE, PV_CR_PLAZA, PV_CR_TIENDA, BOL_NO, STATUS, TARGET_ID \r\n"
				+ "FROM XXFC.XXFC_PC_TSF WHERE PV_CR_PLAZA = '" + data.get("plaza") + "' AND PV_CR_TIENDA = '"
				+ data.get("tienda") + "' AND STATUS = 'I'";

		PreparedStatement PosQuery = dbDas.prepareStatement(QueryDoc);
		ResultSet PosResult = PosQuery.executeQuery();

		if (PosResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc);
			while (PosResult.next()) {

				Id_doc += PosResult.getString("PC_ID") + ",";// Se concatenan los ID para proximas consultas/filtros.

				String ID = PosResult.getString("PC_ID");
				String STORE = PosResult.getString("STORE");
				String PLAZA = PosResult.getString("PV_CR_PLAZA");
				String TIENDA = PosResult.getString("PV_CR_TIENDA");
				String BOL = PosResult.getString("BOL_NO");
				String STATUS = PosResult.getString("STATUS");
				String TARGET_ID = PosResult.getString("TARGET_ID");

				testCase.addTextEvidenceCurrentStep("--> " + "PC_ID : " + ID + " | " + "STORE : " + STORE + " | "
						+ "PV_CR_PLAZA : " + PLAZA + " | " + "PV_CR_TIENDA : " + TIENDA + " | " + "BOL_NO : " + BOL
						+ " | " + "STATUS : " + STATUS + " | " + " TARGET_ID : " + TARGET_ID);

			}
			Id_doc = Id_doc.substring(0, Id_doc.length() - 1);// Se borra la coma que tienen el último registro
		} else {
			assertFalse(!PosResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult.close();
		PosQuery.close();

		/********** Paso 2 *********/

		addStep("Revisar que exista el encabezado del documento en la tabla XXFC_PC_MASTER");

		String QueryDocEnc = "SELECT PC_ID, TSF_TYPE, CEDIS_ID, EXT_REF_NO, PC_STATUS FROM XXFC.XXFC_PC_MASTER WHERE PC_ID IN ("
				+ Id_doc + ")";

		PreparedStatement PosQueryHeader = dbDas.prepareStatement(QueryDocEnc);
		ResultSet PosResultHeader = PosQueryHeader.executeQuery();

		if (PosResultHeader.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocEnc);
			while (PosResultHeader.next()) {

				String PC_ID = PosResultHeader.getString("PC_ID");
				String TSF_TYPE = PosResultHeader.getString("TSF_TYPE");
				String CEDIS_ID = PosResultHeader.getString("CEDIS_ID");
				String EXT_REF_NO = PosResultHeader.getString("EXT_REF_NO");
				String PC_STATUS = PosResultHeader.getString("PC_STATUS");

				testCase.addTextEvidenceCurrentStep(
						"--> " + "PC_ID : " + PC_ID + " | " + "TSF_TYPE : " + TSF_TYPE + " CEDIS_ID : " + CEDIS_ID
								+ " | " + "EXT_REF_NO : " + EXT_REF_NO + "|" + "PC_STATUS : " + PC_STATUS);
			}
		} else {
			assertFalse(!PosResultHeader.next(), "--> ¡ERROR!, No existe información relacionada al documento");
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

		addStep("Revisar que el documento haya cambiado a Status E en XXFC_PC_TSF");

		String QueryChageDoc = " SELECT DISTINCT PC_ID, PV_CR_TIENDA, PV_CR_PLAZA, STATUS, TARGET_ID FROM XXFC.XXFC_PC_TSF"
				+ " WHERE STATUS = 'E'" + " AND PC_ID IN (" + Id_doc + ")";

		PreparedStatement posChageQuery = dbDas.prepareStatement(QueryChageDoc);
		ResultSet posEResult = posChageQuery.executeQuery();

		if (posEResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryChageDoc);
			while (posEResult.next()) {

				target_id = posEResult.getString("TARGET_ID");
				
				String PC_ID = posEResult.getString("PC_ID");
				String PV_CR_TIENDA = posEResult.getString("PV_CR_TIENDA");
				String PV_CR_PLAZA = posEResult.getString("PV_CR_PLAZA");
				String STATUS = posEResult.getString("STATUS");
				String TARGET_ID = posEResult.getString("TARGET_ID");

				testCase.addTextEvidenceCurrentStep("--> " + " PC_ID : " + PC_ID + " PV_CR_TIENDA : " + PV_CR_TIENDA
						+ " PV_CR_PLAZA : " + PV_CR_PLAZA + " STATUS : " + STATUS + " TARGET_ID : " + TARGET_ID);
			}
			target_id = target_id.substring(0, target_id.length() - 3);
		} else {
			assertFalse(!posEResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}
		posEResult.close();
		posChageQuery.close();

		/********** Paso 7 *********/

		addStep("Revisar la informacion generada en tabla RIB_MESSAGE");

		String DocStgInv = "SELECT MESSAGE_NUM, ADAPTER_CLASS_LOCATION, FAMILY, TYPE, RIB_MESSAGE_ID, PUBLISH_TIME FROM RMS.RIB_MESSAGE\r\n"
				+ "WHERE RIB_MESSAGE_ID = 'DSD9900021|4001471653|250305103600'" + "ORDER BY PUBLISH_TIME DESC";

		PreparedStatement posStgInv = dbMom.prepareStatement(DocStgInv);
		ResultSet posStgInvResult = posStgInv.executeQuery();

		if (posStgInvResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(DocStgInv);
			while (posStgInvResult.next()) {

				String MESSAGE_NUM = posStgInvResult.getString("MESSAGE_NUM");
				String ADAPTER_CLASS_LOCATION = posStgInvResult.getString("ADAPTER_CLASS_LOCATION");
				String FAMILY = posStgInvResult.getString("FAMILY");
				String TYPE = posStgInvResult.getString("TYPE");
				String RIB_MESSAGE_ID = posStgInvResult.getString("RIB_MESSAGE_ID");
				String PUBLISH = posStgInvResult.getString("PUBLISH_TIME");

				testCase.addTextEvidenceCurrentStep(
						"--> " + "MESSAGE_NUM : " + MESSAGE_NUM + " | " + "ADAPTER_CLASS_LOCATION : "
								+ ADAPTER_CLASS_LOCATION + " | " + "FAMILY : " + FAMILY + " | " + "TYPE: " + TYPE
								+ " | " + "RIB_MESSAGE_ID: " + RIB_MESSAGE_ID + " | " + "PUBLISH_TIME: " + PUBLISH);
			}
		} else {
			assertFalse(posStgInvResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}

	}

	@Override
	public String setTestFullName() {

		return null;

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub

		return "Envio de datos DSD a RIB ";

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
