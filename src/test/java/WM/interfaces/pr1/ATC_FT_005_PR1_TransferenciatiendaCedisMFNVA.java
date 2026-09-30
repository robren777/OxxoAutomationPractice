package WM.interfaces.pr1;

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
import utils.sql.SQLResult;

/* DESCRIPCION : Procesar Transferencias para el envío de información a RMS y EBS.
 * 
 * MTC-FT-009- Transferencia entre tienda-CEDIS
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (TSF).
 * 	Tener Ordenado el job de la interfaz PR1
 * 	Tener instalado Microsoft Edge
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con el ajuste TSF.
 * 
 * @Author Victor Navarro
 * @Date 06/Diciembre/2024
 */
public class ATC_FT_005_PR1_TransferenciatiendaCedisMFNVA extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_PR1_TransferenciatiendaCedisMFNVA_test(HashMap<String, String> data) throws Exception {

		/*
		 * Bases de datos.
		 *********************************************************************/
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");

		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_WM_MEX_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");

		Connection dbLog = DriverManager.getConnection(GlobalVariables.DB_HOST_LOG_MEX_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_LOG_MEX_MULTIFORMATO_QA, GlobalVariables.DB_PASSWORD_LOG_MEX_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG----------------------------------------------- ");
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A RMS10----------------------------------------------- ");

		utils.sql.SQLUtil dbRms = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS10_MEX_MULTIFORMATO,
				GlobalVariables.DB_USER_RMS10_MEX_MULTIFORMATO, GlobalVariables.DB_PASSWORD_RMS10_MEX_MULTIFORMATO);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A RMS10----------------------------------------------- ");

		testCase.setProject_Name("Transformación Multiformato");
		/**
		 * Variables
		 * ******************************************************************************************
		 * 
		 * 
		 */

		String run_status = "";

		String Id_Doc = "";

		String Target_Id = "";

		String tdcQueryConnectionsTSF = "SELECT * from(Select id,STATUS,DOC_TYPE,PV_DOC_NAME,RECEIVED_DATE,target_id "
				+ " FROM posuser.pos_inb_doc_rms" + " where doc_type IN ('TSF')" + " AND STATUS ='I'"
				+ " and SUBSTR(PV_DOC_NAME,4,10)= '" + data.get("plaza") + data.get("tienda") + "'"
				+ " AND partition_date > SYSDATE - 15" + " ORDER BY RECEIVED_DATE  DESC)where rownum=1"; // Funciona

		String tdcQueryRetekSendDoc = "SELECT DOC_TYPE,CR_PLAZA,CR_TIENDA,STATUS,RUN_ID,rtk_filename "
				+ " FROM wmuser.rtk_inbound_docs" + " WHERE doc_type = 'TSF' " + " AND cr_plaza = '" + data.get("plaza")
				+ "'" + " AND cr_tienda = '" + data.get("tienda") + "'" + " AND status = 'L' "
				+ " AND rtk_filename = '%s'"; // Funciona

		// Consultas de error

		// Paso 2

		addStep("Comprobar documentos disponibles en POS_INB_DOC_RMS");

		PreparedStatement PosQuery2 = dbPos.prepareStatement(tdcQueryConnectionsTSF);
		ResultSet PosResult2 = PosQuery2.executeQuery();

		java.sql.ResultSetMetaData rsltlogPos2 = PosResult2.getMetaData();
		int columnCountLog2 = rsltlogPos2.getColumnCount();

		// Procesar los resultados
		if (!PosResult2.next()) {
			System.out.println("Error!, la interfaz no detecto ningún documento pendiente");
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningún documento pendiente");
			assertFalse(!PosResult2.next(), "No hay registros de detalle relacionados al documento TSF");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryConnectionsTSF);///////////////
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLog2; i++) {
				String columnName = rsltlogPos2.getColumnName(i);
				String columnValue = PosResult2.getString(i);
				Id_Doc = PosResult2.getString("ID");
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		PosResult2.close();
		PosQuery2.close();

		// Paso 3

		addStep("Comprobar documentos disponibles en POS_TSF");

		String tdcQueryPosTSF2 = "SELECT PID_ID,NO_RECORDS,PV_CVE_MVT,SHIP_DATE,PV_CR_FROM_LOC,CEDIS_ID  FROM POSUSER.POS_TSF"
				+ " WHERE PID_ID = " + Id_Doc + " ORDER BY PARTITION_DATE DESC"; // Funciona

		PreparedStatement PosQuery3 = dbPos.prepareStatement(tdcQueryPosTSF2);
		ResultSet PosResult3 = PosQuery3.executeQuery();

		java.sql.ResultSetMetaData rsltlogPos3 = PosResult3.getMetaData();
		int columnCountLog3 = rsltlogPos3.getColumnCount();

		// Procesar los resultados
		if (!PosResult3.next()) {
			System.out.println("Error!, la interfaz no detecto ningún documento");
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningún documento");
			assertFalse(!PosResult3.next(), "No hay registros de detalle relacionados al documento TSF");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryPosTSF2);//////////////////////////////////
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLog3; i++) {
				String columnName = rsltlogPos3.getColumnName(i);
				String columnValue = PosResult3.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		PosResult3.close();
		PosQuery3.close();

		// Paso 4

		addStep("Comprobar documentos disponibles en POS_TSF_DETL");
		String tdcQueryPosDETLTSF2 = "SELECT * FROM POSUSER.POS_TSF_DETL" + " WHERE PID_ID = " + Id_Doc; // Funciona
		PreparedStatement PosQuery4 = dbPos.prepareStatement(tdcQueryPosDETLTSF2);
		ResultSet PosResult4 = PosQuery4.executeQuery();

		java.sql.ResultSetMetaData rsltlogPos4 = PosResult4.getMetaData();
		int columnCountLog4 = rsltlogPos4.getColumnCount();

		// Procesar los resultados
		if (!PosResult4.next()) {
			System.out.println("Error!, la interfaz no detecto ningún documento con detalle");
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningún documento con detalle");
			assertFalse(!PosResult4.next(), "No hay registros de detalle relacionados al documento TSF");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryPosDETLTSF2);//////////////////////////////////
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLog4; i++) {
				String columnName = rsltlogPos4.getColumnName(i);
				String columnValue = PosResult4.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		PosResult4.close();
		PosQuery4.close();

		// Paso
		// 5///////////////////////////////////////////////////////////////////////////////////////////////////////////
		addStep("Ejecucion job : PR1 en  control-M");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());

		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn();

		JSONObject obj = new JSONObject(data.get("jobs"));
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

		// Paso 6

		addStep("Comprobar ejecución");
		String tdcIntegrationServerFormat = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR1main'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";// WMLOG STATUS JOB
		PreparedStatement PosQuery5 = dbLog.prepareStatement(tdcIntegrationServerFormat);
		ResultSet PosResult5 = PosQuery5.executeQuery();

		java.sql.ResultSetMetaData rsltlogPos5 = PosResult5.getMetaData();
		int columnCountLog5 = rsltlogPos5.getColumnCount();

		// Procesar los resultados
		if (!PosResult5.next() && PosResult5.getString("STATUS") != "S") {
			System.out.println("Error!, la interfaz terminó en Status E o no tienen ejecuciones recientes");
			testCase.addTextEvidenceCurrentStep(
					"Error!, la interfaz terminó en Status E o no tienen ejecuciones recientes");
			assertFalse(!PosResult5.next(), "No hay registros de detalle relacionados al documento TSF");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcIntegrationServerFormat);//////////////////////////////////
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLog5; i++) {
				String columnName = rsltlogPos5.getColumnName(i);
				String columnValue = PosResult5.getString(i);
				run_status = PosResult5.getString("RUN_ID");
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println("Ejecución exitosa"); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		PosResult5.close();
		PosQuery5.close();

		// Paso 7

		addStep("Comprobar levantamiento de THREADS");
		String tdcQueryStatusThread = " SELECT PARENT_ID,THREAD_ID,NAME,WM_LOG_THREAD.STATUS,ATT1,ATT2\r\n"
				+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_status;
		PreparedStatement PosQuery6 = dbLog.prepareStatement(tdcQueryStatusThread);
		ResultSet PosResult6 = PosQuery6.executeQuery();

		java.sql.ResultSetMetaData rsltlogPos6 = PosResult6.getMetaData();
		int columnCountLog6 = rsltlogPos6.getColumnCount();

		// Procesar los resultados
		if (!PosResult6.next()) {
			System.out.println("Error!, la interfaz no detecto ningun THREAD");
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningun THREAD");
			assertFalse(!PosResult6.next(), "No hay registros de detalle relacionados al documento TSF");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryStatusThread);//////////////////////////////////
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLog6; i++) {
				String columnName = rsltlogPos6.getColumnName(i);
				String columnValue = PosResult6.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");

			}
			System.out.println("La interfaz levanto THREADS"); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		PosResult6.close();
		PosQuery6.close();

		// Paso 8

		addStep("Comprobar errores en THREADS");
		String tdcQueryErrorId = " SELECT ERROR_ID,RUN_ID,ERROR_DATE,DESCRIPTION " + " FROM WMLOG.WM_LOG_ERROR "
				+ " where RUN_ID= " + run_status; // FCWMLQA //Funciona
		PreparedStatement PosQuery7 = dbLog.prepareStatement(tdcQueryErrorId);

		ResultSet PosResult7 = PosQuery7.executeQuery();

		java.sql.ResultSetMetaData rsltlogPos7 = PosResult7.getMetaData();
		int columnCountLog7 = rsltlogPos7.getColumnCount();

		// Procesar los resultados
		if (!PosResult7.next()) {
			System.out.println("La interfaz levanto THREADS correctamente");
			testCase.addTextEvidenceCurrentStep(tdcQueryErrorId);//////////////////////////////////
			testCase.addTextEvidenceCurrentStep("La interfaz levanto THREADS correctamente");

		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryErrorId);//////////////////////////////////
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLog7; i++) {
				String columnName = rsltlogPos7.getColumnName(i);
				String columnValue = PosResult7.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
				assertFalse(!PosResult7.next(), "No hay registros de detalle relacionados al documento TSF");
			}
			System.out.println("Error!, la interfaz detecto error en THREAD"); // Salto de línea para el siguiente
																				// registro
		}
		// Cerrar los recursos
		PosResult7.close();
		PosQuery7.close();

		// Paso 9

		addStep("Comprobar STATUS 'E' del documento, en POS_INB_DOC_RMS");

		String tdcQueryConnectionsTSF4 = "SELECT id,STATUS,DOC_TYPE,PV_DOC_NAME,RECEIVED_DATE,target_id "
				+ " FROM POSUSER.pos_inb_doc_rms" + " WHERE ID = " + Id_Doc + "AND STATUS = 'E' "
				+ " ORDER BY RECEIVED_DATE DESC"; // Funciona
		PreparedStatement PosQuery8 = dbPos.prepareStatement(tdcQueryConnectionsTSF4);
		ResultSet PosResult8 = PosQuery8.executeQuery();

		java.sql.ResultSetMetaData rsltlogPos8 = PosResult8.getMetaData();
		int columnCountLog8 = rsltlogPos8.getColumnCount();

		// Procesar los resultados
		if (!PosResult8.next()) {
			System.out.println("Error!, la interfaz no detecto ningún documento procesado");
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningún documento procesado");
			assertFalse(!PosResult8.next(), "No hay registros de detalle relacionados al documento TSF");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryConnectionsTSF4);////////////////////////////////// sobre las
																			////////////////////////////////// columnas
																			////////////////////////////////// y mostrar
																			////////////////////////////////// los
																			////////////////////////////////// valores
			for (int i = 1; i <= columnCountLog8; i++) {
				String columnName = rsltlogPos8.getColumnName(i);
				String columnValue = PosResult8.getString(i);
				Target_Id = PosResult8.getString("Target_Id");
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println("El documento fue procesado"); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		PosResult8.close();
		PosQuery8.close();

		// Paso 10

		addStep("Validar la inserción del registro del documento enviado en la tabla de RTK_INBOUND_DOCS");

		String RTKresult = String.format(tdcQueryRetekSendDoc, Target_Id);
		System.out.println(RTKresult);

		SQLResult retekQuery = dbRms.executeQuery(RTKresult);

		boolean retek = retekQuery.isEmpty();

		if (!retek) {
			testCase.addQueryEvidenceCurrentStep(retekQuery);
		} else {
			testCase.addBoldTextEvidenceCurrentStep("El archivo no se encuentra dentro de la tabla");
			assertFalse(retek, "El archivo no se encuentra dentro de la tabla");
		}

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "PR1 Transferencia tienda Cedis NVA";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Victor Hugo Navarro";
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
		return "1. Acceso a Control-M.\r\n"
				+ "2. Contar con los archivos de las transferencias del POS a WM, hechas por la FEMSA_ENRED_IN.\r\n"
				+ "3. Tienda / CEDIS configurado.\r\n" + "4. Articulo activo en POS\r\n"
				+ "5. POS_RULES_DOC_CFG (MIGRATED_FLAG = \"Y\")";
	}

}
