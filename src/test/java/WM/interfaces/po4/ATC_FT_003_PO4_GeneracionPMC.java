package WM.interfaces.po4;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

/* DESCRIPCION : Envio de formas de pago de DAS a POS.
 * 
 * MTC_FT_013_Generacion de PMC de plaza con las 2 carreteras activas  (ambas arquitecturas)
 * 
 * PREREQUISITOS:
 * 	Tener la Bandera en D
 * 	Tener las formas de pago disponibles.
 * 	Tener Ordenado el job de la interfaz PO4_OLD y fem_posc_formas_pago
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con las formas de pago disponibles.
 * 
 * @Author Rafael Alarcón
 * @Date 23/Agosto/2024
 */
public class ATC_FT_003_PO4_GeneracionPMC extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_PO4_GeneracionPMC_test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Transformacion multiformato");
		/****************************
		 * Datos de conexion
		 ************************************/

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
		Connection dbLog = DriverManager.getConnection(GlobalVariables.DB_HOST_LOG_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_LOG_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_LOG_MEX_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		/* POSUSER */
		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_WM_MEX_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER----------------------------------------------- ");

		/****************************
		 * Variables de consultas
		 *******************************/

		String tdcQueryStore = "SELECT SOURCE_ID, POS_CONFIG_ID, RECORD_TYPE, COUPON_DESC, STATUS, TIMESTAMP, COMPLETE_IND FROM EO_RMS.FEM_POS_CFG_STG WHERE TRUNC(TIMESTAMP) = TRUNC(SYSDATE) AND COMPLETE_IND = 'Y'";

		String tdcQueryFPago = "SELECT SOURCE_ID, POS_CONFIG_ID, RECORD_TYPE, STATUS, EXTRACT_DATE, STORE FROM EO_RMS.FEM_POS_CFG_STG_STORE\r\n"
				+ "WHERE POS_CONFIG_ID IN (%s)\r\n" + "AND STORE IN (" + data.get("tiendas")
				+ ") AND SOURCE_ID IN (%s)\r\n" + "AND WM_ID IS NULL\r\n" + "ORDER BY EXTRACT_DATE DESC";

		String tdcQueryFPagoP = "SELECT SOURCE_ID, RECORD_TYPE, WM_ID FROM EO_RMS.FEM_POS_CFG_STG_STORE\r\n"
				+ "WHERE POS_CONFIG_ID IN (%s)\r\n" + "AND STORE IN (" + data.get("tiendas")
				+ ") AND SOURCE_ID IN (%s)\r\n" + "ORDER BY EXTRACT_DATE DESC";

		/****************************
		 * Variables de datos
		 ***********************************/

		String run_id = "";

		// SOURCE
		Set<String> setValores = new HashSet<>();
		StringBuilder result = new StringBuilder();
		// CUPONES
		Set<String> setValores2 = new HashSet<>();
		StringBuilder result2 = new StringBuilder();

		/****************************
		 * Pasos de la prueba
		 ***********************************/

		/********** Paso 1 *********/

		addStep("Ejecutar el job fem_posc_formas_pago");

		SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), true);

		Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));
		u.hardWait(30);
		u.waitForLoadPage();
		cm.logOn();
		JSONObject obj = new JSONObject(data.get("JOB 1"));
		ControlM job = new ControlM(u, testCase, obj);
		boolean flag = job.searchJob();
		assertTrue(flag, "Bandera si encontro el job");
		String resultado = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);
		System.out.println("Valor de output : " + job.getOutput());
		assertEquals(resultado, "Ended OK");
		u.hardWait(3);
		job.closeViewpoint();
		u.close();

		/********** Paso 2 *********/

		addStep("Verificar que exista información en las tablas FEM_POS_CFG_STG, FEM_POS_CFG_STG_STORE con COMPLETE_IND = Y");

		String posStoreResult = String.format(tdcQueryStore);
		SQLResult StoreQuery = dbDas.executeQuery(posStoreResult);

		if (!StoreQuery.isEmpty()) {
			testCase.addQueryEvidenceCurrentStep(StoreQuery);
			for (int i = 0; i < StoreQuery.getRowCount(); i++) {
				// SOURCE_ID
				String SOURCE_ID = StoreQuery.getData(i, "SOURCE_ID");
				String C_SOURCE = "'" + SOURCE_ID + "'";
				if (setValores.add(SOURCE_ID)) { // Se concatena los valores del reference 3 y valida que no se
													// dupliquen valores
					if (result.length() > 0) {
						result.append(", ");
					}
					result.append(C_SOURCE);
				}
				// CUPONES_ID
				String CUPO_ID = StoreQuery.getData(i, "POS_CONFIG_ID");
				String C_CUPO = "'" + CUPO_ID + "'";
				if (setValores2.add(CUPO_ID)) { // Se concatena los valores del reference 3 y valida que no se dupliquen
												// valores
					if (result2.length() > 0) {
						result2.append(", ");
					}
					result2.append(C_CUPO);
				}
			}

		} else {
			assertFalse(StoreQuery.isEmpty(),
					"--> ¡ERROR!, No existen información pendiente por procesar en formas de pago");
		}

		// CUPONES
		String tdcQuerysORES = String.format(tdcQueryFPago, result2.toString(), result.toString());
		SQLResult ORESQuery = dbDas.executeQuery(tdcQuerysORES);

		boolean ORESQueryB = ORESQuery.isEmpty();
		 
		if (ORESQueryB) {
			testCase.addQueryEvidenceCurrentStep(ORESQuery);
			assertFalse(ORESQueryB,  "--> �ERROR!, No existen informaci�n pendiente por procesar");
		} else {
			testCase.addQueryEvidenceCurrentStep(ORESQuery);
		}

		/********** Paso 3 *********/

		addStep("Ejecutar la interface con el servicio PO4.Pub:run_OLD desde Ctrl-M ");

		SeleniumUtil u2 = new SeleniumUtil(new MEdgeTest(), true);

		Control_mInicio cm2 = new Control_mInicio(u2, data.get("user"), data.get("ps"));
		u2.get(data.get("server"));
		u2.hardWait(30);
		u2.waitForLoadPage();
		cm2.logOn();
		JSONObject obj2 = new JSONObject(data.get("JOB 2"));
		ControlM job2 = new ControlM(u2, testCase, obj2);
		boolean flag2 = job2.searchJob();
		assertTrue(flag2, "Bandera si encontro el job");
		String resultado2 = job2.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado2);
		System.out.println("Valor de output : " + job2.getOutput());
		assertEquals(resultado2, "Ended OK");
		u2.hardWait(3);
		job2.closeViewpoint();
		u2.close();

		/********** Paso 4 *********/

		addStep("Verificar que la ejecución de la interface sea registrado en WMLOG con estatus S");

		String tdcQueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PO4_OLD'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

		PreparedStatement LogQuery = dbLog.prepareStatement(tdcQueryIntegrationServer);
		ResultSet LogResult = LogQuery.executeQuery();

		if (LogResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(tdcQueryIntegrationServer);
			while (LogResult.next()) {
				// RUN_ID,START_DT,STATUS
				run_id = LogResult.getString("RUN_ID");
				String START_DT = LogResult.getString("START_DT");
				String STATUS = LogResult.getString("STATUS");

				testCase.addTextEvidenceCurrentStep(
						"--> " + " RUN_ID : " + run_id + " START_DT : " + START_DT + " STATUS : " + STATUS);
			}
		} else {
			assertFalse(!LogResult.next(), "--> ¡ERROR!, No existe información relacionada a la ejecución");
		}
		LogResult.close();
		LogQuery.close();

		/********** Paso 5 *********/

		addStep("Se valida la generacion de thread");

		String tdcQueryStatusThread = " SELECT PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5"
				+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_id + " AND STATUS = 'S'";

		PreparedStatement LogThreadQuery = dbLog.prepareStatement(tdcQueryStatusThread);
		ResultSet LogThreadResult = LogThreadQuery.executeQuery();

		if (LogThreadResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(tdcQueryStatusThread);
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
			assertFalse(LogThreadResult.next(),
					"--> ¡ERROR!, No existe información relacionada a Threads de la ejecución");
		}
		LogThreadResult.close();
		LogThreadQuery.close();

		/********** Paso 6 *********/

		addStep("Validar que el registro del documento fue insertado en la tabla POS_OUTBOUND_DOCS de la BD de WM (Central). Se debe de insertar con estatus L");

		String tdcQueryDocPMC = "SELECT ID, POE_ID, STATUS, TARGET_TYPE, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA, "
				+ "PARTITION_DATE FROM POSUSER.POS_OUTBOUND_DOCS WHERE DOC_TYPE = 'PMC' AND TRUNC(PARTITION_DATE) = TRUNC(SYSDATE) ORDER BY DATE_CREATED DESC";

		PreparedStatement PosQuery = dbPos.prepareStatement(tdcQueryDocPMC);
		ResultSet PosResult = PosQuery.executeQuery();

		if (PosResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(tdcQueryDocPMC);
			while (PosResult.next()) {
				// ID, POE_ID, STATUS, TARGET_TYPE, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA,
				// DATE_CREATED
				String ID = PosResult.getString("ID");
				String POE_ID = PosResult.getString("POE_ID");
				String STATUS = PosResult.getString("STATUS");
				String TARGET_TYPE = PosResult.getString("TARGET_TYPE");
				String DOC_NAME = PosResult.getString("DOC_NAME");
				String PV_CR_PLAZA = PosResult.getString("PV_CR_PLAZA");
				String PV_CR_TIENDA = PosResult.getString("PV_CR_TIENDA");
				String PARTITION_DATE = PosResult.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + " ID : " + ID + " POE_ID : " + POE_ID + " STATUS : "
						+ STATUS + " TARGET_TYPE : " + TARGET_TYPE + " DOC_NAME : " + DOC_NAME + " PV_CR_PLAZA : "
						+ PV_CR_PLAZA + " PV_CR_TIENDA : " + PV_CR_TIENDA + " PARTITION_DATE : " + PARTITION_DATE);
			}
		} else {
			testCase.addTextEvidenceCurrentStep("No hay registros en la tabla");
			assertFalse(PosResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}

		/********** Paso 7 *********/

		addStep("Validar que el registro del documento fue insertado en la tabla POS_OUT_DOC_RMS de la BD de WM (Central). Se debe de insertar con estatus L");

		String tdcQueryDocPMCjson = "SELECT ID, POE_ID, STATUS, TARGET_TYPE, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA, "
				+ "PARTITION_DATE FROM POSUSER.POS_OUT_DOC_RMS WHERE DOC_TYPE = 'PMC' AND TRUNC(PARTITION_DATE) = TRUNC(SYSDATE) ORDER BY DATE_CREATED DESC";

		PreparedStatement PosQueryjson = dbPos.prepareStatement(tdcQueryDocPMCjson);
		ResultSet PosResultjson = PosQueryjson.executeQuery();

		if (PosResultjson.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(tdcQueryDocPMCjson);
			while (PosResultjson.next()) {
				// ID, POE_ID, STATUS, TARGET_TYPE, DOC_NAME, PV_CR_PLAZA, PV_CR_TIENDA,
				// DATE_CREATED
				String ID = PosResultjson.getString("ID");
				String POE_ID = PosResultjson.getString("POE_ID");
				String STATUS = PosResultjson.getString("STATUS");
				String TARGET_TYPE = PosResultjson.getString("TARGET_TYPE");
				String DOC_NAME = PosResultjson.getString("DOC_NAME");
				String PV_CR_PLAZA = PosResultjson.getString("PV_CR_PLAZA");
				String PV_CR_TIENDA = PosResultjson.getString("PV_CR_TIENDA");
				String PARTITION_DATE = PosResultjson.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + " ID : " + ID + " POE_ID : " + POE_ID + " STATUS : "
						+ STATUS + " TARGET_TYPE : " + TARGET_TYPE + " DOC_NAME : " + DOC_NAME + " PV_CR_PLAZA : "
						+ PV_CR_PLAZA + " PV_CR_TIENDA : " + PV_CR_TIENDA + " PARTITION_DATE : " + PARTITION_DATE);
			}
		} else {
			testCase.addTextEvidenceCurrentStep("No hay registros en la tabla");
			assertFalse(PosResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}

		/********** Paso 8 *********/

		addStep("Verificar que los registros que se procesaron de la tabla FEM_POS_CFG_STG_STORE se le actualizaron los campos WM_ID con el "
				+ "nombre del documento que se genero para el registro y el campo SEQ_NO con el numero de secuencia.");

		// CUPONES
		String tdcQuerysCP = String.format(tdcQueryFPagoP, result2.toString(), result.toString());
		SQLResult CPQuery = dbDas.executeQuery(tdcQuerysCP);

		boolean ORESQueryB2 = ORESQuery.isEmpty();
		 
		if (ORESQueryB2) {
			testCase.addQueryEvidenceCurrentStep(CPQuery);
			assertFalse(ORESQueryB2,  "--> �ERROR!, No existen informaci�n pendiente por procesar");
		} else {
			testCase.addQueryEvidenceCurrentStep(CPQuery);
		}
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Envio de formas de pago de DAS a POS";
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
		return "1. Verificar que exista información en las tablas POS_TENDER _TYPE_ HEAD, POS_PAYINOUT_HEAD, POS_COUPON_HEAD.\r\n"
				+ "2. Validar que exista la forma de pago relacionado con la tienda.\r\n"
				+ "3. Validar que existan las configuraciones de la interface en la WM_INTERFASE_CONFIG en la BD WM\r\n"
				+ "4. Validar que exista el registro de configuracion del documento y plaza en POS_RULES_DOC_CFG.MIGRATED_FLAG_OUT='D' en BD WM.\r\n"
				+ "5. Validar que la plaza no este migrada a RMS 16 (WM_OXXO_ROLLOUT_INTERFACE_V)";
	}

}