package WM.interfaces.PR62_MX;

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

/* DESCRIPCION :  Validar ajustes y creacion de inventarios.
 * 
 * MTC_FT_PR62_MX_001_Generacion de ITM Arq PR50v2
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (ITM).
 * 	Tener Ordenado el job de la interfaz PR62_MX_OLD
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con el Doc_type ITM.
 * 
 * @Author Miguel BAena
 * @Date 23/Diciembre/2024
 */
public class ATC_FT_001_PR62MXOLD_GeneraciondeITM extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_PR62MXOLD_GeneraciondeITM_test(HashMap<String, String> data) throws Exception {

		/**************************** Datos de conexion *******************************/

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A DASPERF----------------------------------------------- ");
		/* DASPERF */
		Connection dbDasPerf = DriverManager.getConnection(GlobalVariables.DB_HOST_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_DASPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_DASPERF_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A DSPERF-------------------------------------------------- ");
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

		addStep("Revisar que exista el documento en la tabla de POS_ITM_PRM_HEAD");

		String QueryDoc = "SELECT * FROM WMUSER.POS_ITM_PRM_HEAD WHERE WM_ITM_STATUS='L'";

		PreparedStatement PosQuery = dbDasPerf.prepareStatement(QueryDoc);
		ResultSet PosResult = PosQuery.executeQuery();

		if (PosResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc);
			while (PosResult.next()) {

				d_id += PosResult.getString("LOAD_BATCH_ID") + ",";// Se concatenan los ID para proximas
																	// consultas/filtros.

				String BATCH_ID = PosResult.getString("BATCH_ID");
				String LOCATION = PosResult.getString("LOCATION");
				String WM_ITM_STATUS = PosResult.getString("WM_ITM_STATUS");
				String LOAD_DATE = PosResult.getString("LOAD_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "BATCH_ID : " + BATCH_ID + " | " + "LOCATION : " + LOCATION
						+ " | " + "WM_ITM_STATUS : " + WM_ITM_STATUS + " | " + " | " + " LOAD_DATE : " + LOAD_DATE);

			}
			d_id = d_id.substring(0, d_id.length() - 1);// Se borra la coma que tienen el último registro
		} else {
			assertFalse(!PosResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult.close();
		PosQuery.close();

		/********** Paso 2 *********/

		addStep("Revisar que exista el detalle del documento en la tabla FEM_POS_MODS_STG");

		String QueryDocDetl = "SELECT * FROM WMUSER.FEM_POS_MODS_STG WHERE LOAD_BATCH_ID = " + d_id;

		PreparedStatement PosQueryDetl = dbDasPerf.prepareStatement(QueryDocDetl);
		ResultSet PosResultDetl = PosQueryDetl.executeQuery();

		if (PosResultDetl.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocDetl);
			while (PosResultDetl.next()) {

				items += PosResultDetl.getString("ITEM") + ",";

				String LOAD_BATCH_ID = PosResultDetl.getString("LOAD_BATCH_ID");
				String STORE = PosResultDetl.getString("STORE");
				String ITEM_SHORT_DESC = PosResultDetl.getString("ITEM_SHORT_DESC");

				testCase.addTextEvidenceCurrentStep("--> " + "LOAD_BATCH_ID : " + LOAD_BATCH_ID + " | " + "STORE : "
						+ STORE + " | " + "ITEM_SHORT_DESC : " + ITEM_SHORT_DESC);
			}
			items = items.substring(0, items.length() - 1);
		} else {
			assertFalse(!PosResultDetl.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 3 *********/

		/* Ejecucion de Jobs */

		addStep("Ejecucion job : PR62_MX_OLD  en  control-M");
		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);

		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn();

		JSONObject obj = new JSONObject(data.get("job1"));
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

		/********** Paso 4 *********/

		addStep("Revisar la corrida de la interfaz en la tabla de WM_LOG_RUN");

		String QueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR62_MX_OLD'" + " AND  START_DT >= TRUNC(SYSDATE)"
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

		/********** Paso 5 *********/

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

		addStep("Revisar que WM_ITM_STATUS en la tabla de POS_ITM_PRM_HEAD Cambie a E");

		String QueryDoc2 = "SELECT * FROM WMUSER.POS_ITM_PRM_HEAD WHERE LOAD_BATCH_ID =" + d_id;

		PreparedStatement PosQuery2 = dbDasPerf.prepareStatement(QueryDoc2);
		ResultSet PosResult2 = PosQuery2.executeQuery();

		if (PosResult2.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDoc2);
			while (PosResult2.next()) {

				String BATCH_ID = PosResult2.getString("BATCH_ID");
				String LOCATION = PosResult2.getString("LOCATION");
				String WM_ITM_STATUS = PosResult2.getString("WM_ITM_STATUS");
				String LOAD_DATE = PosResult2.getString("LOAD_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "BATCH_ID : " + BATCH_ID + " | " + "LOCATION : " + LOCATION
						+ " | " + "WM_ITM_STATUS : " + WM_ITM_STATUS + " | " + " | " + " LOAD_DATE : " + LOAD_DATE);

			}
			d_id = d_id.substring(0, d_id.length() - 1);// Se borra la coma que tienen el último registro
		} else {
			assertFalse(!PosResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult.close();
		PosQuery.close();

	}

	@Override
	public String setTestFullName() {

		return null;

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub

		return "Validar ajustes y creacion de inventarios.";

	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Miguel Baena";
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
		return "1. Verificar que existan registros pendientes de procesar con STATUS “L” de la tabla POS_ITM_PRM_HEAD del esquema WMUSER de la BD DAS_Operativo.\r\n"
				+ "2. Verifircar que existan registros ITM en la tabla POS_ITM_PRM_HEAD del esquema WMUSER de la BD del DAS Operativo, correspondientes a la tienda y batch_Id´s.\r\n"
				+ "3. Validar que existan las configuraciones de la interface en la WM_INTERFASE_CONFIG en la BD WM\r\n"
				+ "4. Validar que exista el registro de configuracion del documento y plaza en POS_RULES_DOC_CGF.MIGRATED_FLAG_OUT='N' en BD WM.\r\n"
				+ "5. Validar que la plaza este migrada a RMS 16 (WM_OXXO_ROLLOUT_INTERFACE_V)";
	}

}
