package WM.interfaces.PR11_MX;

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

/* DESCRIPCION : Procesar Ajuste de inventarios para el envío de información a RMS.
 * 
 * MTC_FT_PR11_MX_009 Procesar un documento INV correctamente
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (INV).
 * 	Tener Ordenado el job de la interfaz PR11_MX_OLD
 * 	Tener instalado Chrome
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con el ajuste INV.
 * 
 * @Author Gustavo Morales
 * @Date 11/Diciembre/2024
 */
public class ATC_FT_003_PR11MXOLD_ProcesarDocumentoINV extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_PR11MXOLD_ProcesarDocumentoINV_test(HashMap<String, String> data) throws Exception {
		/*
		 * Datos de conexi n
		 *********************************************************************/
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		/* POSUSER */
		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_NUCLEO_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_WM_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		/* WMLOG */
		Connection dbLog = DriverManager.getConnection(GlobalVariables.DB_HOST_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WMLOG_NUCLEO_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_WMLOG_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG----------------------------------------------- ");
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A MOMPERF----------------------------------------------- ");
		/* MOMPERF */
		Connection dbMomperf = DriverManager.getConnection(GlobalVariables.DB_HOST_MOMPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_MOMPERF_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_MOMPERF_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A MOMPERF-------------------------------------------------- ");
		
		testCase.setProject_Name("Transformación Multiformato");
		/*****************
		 * Variables de consulta
		 ************************************************************************************/

		String run_status = "";
		String Id_doc = "";
		String itemS = "";

		/********** Paso 1 *********/

		addStep("Revisar que exista el documento en la tabla de POS_INB_DOC_RMS");

		String QueryDoc = "select * from (select ID,STATUS,DOC_TYPE,PV_DOC_NAME, PARTITION_DATE from POSUSER.POS_INBOUND_DOCS\r\n"
				+ " where DOC_TYPE='INV'\r\n" + " and  SUBSTR(PV_DOC_NAME,4,5) = '" + data.get("plaza") + "'\r\n"
				+ "and STATUS='I' ORDER BY received_date DESC) where rownum <= 2";

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

				testCase.addTextEvidenceCurrentStep("--> " + "ID : " + ID + " | " + "STATUS : " + STATUS + " | " + "DOC_TYPE : " + DOC_TYPE
						+ " | " + "DOC_NAME : " + DOC_NAME + " | " + " PARTITION_DATE : " + PARTITION);

			}
			Id_doc = Id_doc.substring(0, Id_doc.length() - 1);// Se borra la coma que tienen el último registro
		} else {
			assertFalse(!PosResult.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		PosResult.close();
		PosQuery.close();

		/********** Paso 2 *********/

		addStep("Revisar que exista el encabezado del documento en la tabla POS_INV");

		String QueryDocEnc = "SELECT * FROM POS_INV WHERE PID_ID = " + Id_doc;

		PreparedStatement PosQueryHeader = dbPos.prepareStatement(QueryDocEnc);
		ResultSet PosResultHeader = PosQueryHeader.executeQuery();

		if (PosResultHeader.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocEnc);
			while (PosResultHeader.next()) {

				String PID_ID = PosResultHeader.getString("PID_ID");
				String NO_RECORDS = PosResultHeader.getString("NO_RECORDS");
				String FECHA_ADM = PosResultHeader.getString("FECHA_ADM");
				String PV_INV_FIS = PosResultHeader.getString("PV_INV_FIS");
				String PARTITION_DATE = PosResultHeader.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "NO_RECORDS : " + NO_RECORDS
						+ " | " + "ADJ_DATE : " + FECHA_ADM + " | " + "PV_INV_FIS : " + PV_INV_FIS + " | " + " PARTITION_DATE : " + PARTITION_DATE);
			}
		} else {
			assertFalse(!PosResultHeader.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		// ***************************************** Paso 3
		// *****************************************************

		/********** Paso 3 *********/

		addStep("Revisar que exista el detalle del documento en la tabla POS_INV_DETL");

		String QueryDocDetl = "SELECT * FROM POS_INV_DETL WHERE PID_ID = " + Id_doc;

		PreparedStatement PosQueryDetl = dbPos.prepareStatement(QueryDocDetl);
		ResultSet PosResultDetl = PosQueryDetl.executeQuery();

		if (PosResultDetl.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocDetl);
			while (PosResultDetl.next()) {

				itemS += PosResultDetl.getString("ITEM") + ",";// Se concatenan los items para futuros filtros

				String PID_ID = PosResultDetl.getString("PID_ID");
				String PV_FCH_MVT = PosResultDetl.getString("PV_FCH_MVT");
				String QTY = PosResultDetl.getString("QTY");
				String TOTAL_RETAIL = PosResultDetl.getString("TOTAL_RETAIL");
				String ITEM = PosResultDetl.getString("ITEM");
				String PV_PRECIO = PosResultDetl.getString("PV_PRECIO");
				String FECHA_ADM = PosResultDetl.getString("FECHA_ADM");
				String PARTITION_DATE = PosResultDetl.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "PV_FCH_MVT : " + PV_FCH_MVT + " | "
						+ "QTY : " + QTY + " | " + "TOTAL_RETAIL : " + TOTAL_RETAIL + " | " + "ITEM : " + ITEM + " | "
						+ "PV_PRECIO : " + PV_PRECIO + " | " + "FECHA_ADM : " + FECHA_ADM +  " | " + " PARTITION_DATE : " + PARTITION_DATE);
			}
			itemS = itemS.substring(0, itemS.length() - 1);
		} else {
			assertFalse(!PosResultDetl.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 4 *********/

		addStep("Ejecutar la interfaz runPR11_MX ");

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
				+ " WHERE INTERFACE = 'PR11_MX_OLD'" + " AND  START_DT >= TRUNC(SYSDATE)"
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

		String QueryChageDoc = " SELECT ID, STATUS, DOC_TYPE, PV_DOC_NAME, PARTITION_DATE FROM POS_INBOUND_DOCS"
				+ " WHERE STATUS = 'E'" + " AND DOC_TYPE = 'INV'" + " AND ID IN (" + Id_doc + ")";

		PreparedStatement posChageQuery = dbPos.prepareStatement(QueryChageDoc);
		ResultSet posEResult = posChageQuery.executeQuery();

		if (posEResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryChageDoc);
			while (posEResult.next()) {

				String ID = posEResult.getString("ID");
				String STATUS = posEResult.getString("STATUS");
				String DOC_TYPE = posEResult.getString("DOC_TYPE");
				String PV_DOC_NAME = posEResult.getString("PV_DOC_NAME");
				String PARTITION_DATE = posEResult.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + " ID : " + ID + " STATUS : " + STATUS
						+ " DOC_TYPE : " + DOC_TYPE + " DOC_TYPE : " + PV_DOC_NAME + " PARTITION_DATE : " + PARTITION_DATE);
			}
		} else {
			assertFalse(!posEResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}
		posEResult.close();
		posChageQuery.close();
		
		/********** Paso 7 *********/

		addStep("Revisar que el documento haya llegado a la tabla XXFC_ITEM_LOC_POS de RMS");

		String QueryConsulRMS = " SELECT CR_PLAZA, CR_TIENDA, LOC, ITEM, FECHA_MVT, POS_PRECIO, PROCESSED_DATE FROM XXFC_GA.XXFC_ITEM_LOC_POS\r\n" + 
		"WHERE CR_PLAZA = '10MON'" + "AND CR_TIENDA = '50VID'\r\n"
		+ "AND ITEM = " + itemS + "AND PROCESSED_DATE >= TRUNC(SYSDATE)";

		PreparedStatement rmsChageQuery = dbMomperf.prepareStatement(QueryConsulRMS);
		ResultSet rmsEResult = rmsChageQuery.executeQuery();

		if (rmsEResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryConsulRMS);
			while (rmsEResult.next()) {

				String CR_PLAZA = rmsEResult.getString("CR_PLAZA");
				String CR_TIENDA = rmsEResult.getString("CR_TIENDA");
				String LOC = rmsEResult.getString("LOC");
				String ITEM = rmsEResult.getString("ITEM");
				String FECHA_MVT = rmsEResult.getString("FECHA_MVT");
				String POS_PRECIO = rmsEResult.getString("POS_PRECIO");
				String PROCESSED_DATE = rmsEResult.getString("PROCESSED_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + " CR_PLAZA : " + CR_PLAZA + " CR_TIENDA : " + CR_TIENDA
						+ " LOC : " + LOC + " ITEM : " + ITEM + " FECHA_MVT : " + FECHA_MVT + "POS_PRECIO : " + POS_PRECIO  + 
						"PROCESSED_DATE: " + PROCESSED_DATE );
			}
		} else {
			assertFalse(!rmsEResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}
		rmsEResult.close();
		posChageQuery.close();
		
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return " Procesar Ajuste de inventarios para el envío de información a RMS ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "AutomationQA";
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
		return "1.- Tener documento INV pendiente por procesar en la tabla padre: POS_INB_DOC_RMS y que tengan antigüedad mayor a 2 días.\r\n"
				+ "2.- Tener la información del documento en las tablas hijas: POS_INV y POS_INV_DETL.\r\n"
				+ "3.- Tener conexión a las Bases de Datos";
	}

}