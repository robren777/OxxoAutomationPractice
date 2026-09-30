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
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/* DESCRIPCION : Procesar Ajuste de inventarios para el envío de información a RMS.
 * 
 * MTC_FT_PR11_MX_002 Procesar varios documentos de diferentes plazas y tiendas
 * 
 * PREREQUISITOS:
 * 	Tener documentos disponibles (INV).
 * 	Tener Ordenado el job de la interfaz PR11_MX
 * 	Tener instalado Chrome
 * 	Tener VPN activado para conexión con las BD
 * 
 * INSUMOS:
 * 	Documentos con el ajuste INV.
 * 
 * @Author Gustavo Morales
 * @Date 11/Diciembre/2024
 */
public class ATC_FT_002_PR11MX_ProcesarVariosDocumentosINV extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_PR11MX_ProcesarVariosDocumentosINV_test(HashMap<String, String> data) throws Exception {
		/*
		 * Datos de conexi n
		 *********************************************************************/
		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A POSUSER----------------------------------------------- ");
		/* POSUSER */
		Connection dbPos = DriverManager.getConnection(GlobalVariables.DB_HOST_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WM_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WM_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A POSUSER----------------------------------------------- ");

		System.out.println(
				" -------------------------------- INTENTANDO CONECTAR A WMLOG----------------------------------------------- ");
		/* WMLOG */
		Connection dbLog = DriverManager.getConnection(GlobalVariables.DB_HOST_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_USER_WMLOG_NUCLEO_MULTIFORMATO_QA,
				GlobalVariables.DB_PASSWORD_WMLOG_NUCLEO_MULTIFORMATO_QA);
		System.out.println(
				" -------------------------------- CONEXION EXITOSA A WMLOG----------------------------------------------- ");

		testCase.setProject_Name("Transformación Multiformato");
		/*****************
		 * Variables de consulta
		 ************************************************************************************/

		String testfpc = "SELECT ID, PV_CR_PLAZA, PV_CR_TIENDA, STATUS, DOC_TYPE, PV_DOC_NAME, PARTITION_DATE FROM POSUSER.POS_INB_DOC_RMS\r\n"
				+ "WHERE DOC_TYPE = 'INV' AND PV_CR_PLAZA IN ('" + data.get("plaza") + "')" + " AND PV_CR_TIENDA IN ('"
				+ data.get("tienda") + "'" + ',' + "'" + data.get("tienda1") + "'" + ")" + "AND STATUS = 'I'";

		String tdcQueryIntegrationServer = " SELECT * FROM ( SELECT RUN_ID,START_DT,STATUS" + " FROM WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR11_MX'" + " AND  START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) WHERE ROWNUM <=1";

		String run_status = "";
		String Id_doc = "";
		String item = "";
		String itemS = "";

		/************************************
		 * PASO1
		 *******************************************/

		addStep("Validar en la BD de POSUSER los documentos con estatus en I en la tabla POS_INB_DOC_RMS");

		PreparedStatement posQuery = dbPos.prepareStatement(testfpc);
		ResultSet posResult = posQuery.executeQuery();

		if (!posResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningún documento pendiente");
			assertFalse(!posResult.next(), "Error!, la interfaz no detecto ningún documento pendiente");

		} else {
			testCase.addTextEvidenceCurrentStep(testfpc);
			// Iterar sobre las columnas y mostrar los valores
			while (posResult.next()) {
				String ID = posResult.getString("ID");
				String Pv_doc = posResult.getString("PV_DOC_NAME");
				String Plaza = posResult.getString("PV_CR_PLAZA");
				String Tienda = posResult.getString("PV_CR_TIENDA");
				Id_doc += posResult.getString("ID") + ",";// Se concatenan los ID para proximas consultas/filtros.

				testCase.addTextEvidenceCurrentStep("--> " + "ID : " + ID + " | " + "DOC_NAME : " + Pv_doc + " | "
						+ "PV_CR_PLAZA : " + Plaza + " | " + "PV_CR_TIENDA : " + Tienda);
			}

			Id_doc = Id_doc.substring(0, Id_doc.length() - 1);// Se borra la coma que tienen el último registro
			System.out.println(Id_doc); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		posResult.close();
		posQuery.close();

		// ***************************************** Paso 2
		// *****************************************************

		addStep("Validar que existan datos en la tabla POS_INV");

		String TablaHeader = "SELECT * FROM POSUSER.POS_INV WHERE PID_ID IN (" + Id_doc + ")";

		PreparedStatement posQuery1 = dbPos.prepareStatement(TablaHeader);
		ResultSet posResult1 = posQuery1.executeQuery();

		java.sql.ResultSetMetaData rsltpos2 = posResult1.getMetaData();
		int columnCount1 = rsltpos2.getColumnCount();

		if (!posResult1.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningun detalle de documento");
			assertFalse(!posResult1.next(), "Error!, la interfaz no detecto ningun detalle de documento");
		} else {
			testCase.addTextEvidenceCurrentStep(TablaHeader);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount1; i++) {
				String columnName = rsltpos2.getColumnName(i);
				String columnValue = posResult1.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
				System.out.println(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		posResult1.close();
		posQuery1.close();

		// ***************************************** Paso 3
		// *****************************************************

		addStep("Validar que existan datos en la tabla POS_INV_DETL");

		String consultaTablaDetl = "SELECT * FROM POSUSER.POS_INV_DETL WHERE PID_ID IN (" + Id_doc + ")";

		PreparedStatement posQuery2 = dbPos.prepareStatement(consultaTablaDetl);
		ResultSet posResult2 = posQuery2.executeQuery();

		java.sql.ResultSetMetaData rsltpos3 = posResult2.getMetaData();
		int columnCount2 = rsltpos3.getColumnCount();

		if (!posResult2.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, la interfaz no detecto ningun detalle de documento");
			assertFalse(!posResult2.next(), "Error!, la interfaz no detecto ningun detalle de documento");
		} else {
			testCase.addTextEvidenceCurrentStep(consultaTablaDetl);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCount2; i++) {
				String columnName = rsltpos3.getColumnName(i);
				String columnValue = posResult2.getString(i);
				item += posResult2.getString("ITEM") + ",";
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
				System.out.println(columnName + ": " + columnValue + " ");
			}
			itemS = item.substring(0, item.length() - 1);
			System.out.println(itemS); // Salto de línea para el siguiente registro

		}
		// Cerrar los recursos
		posResult2.close();
		posQuery2.close();

		// ***************************************** Paso 4
		// *****************************************************
		addStep("Ejecucion job : runPR11 en  control-M");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);

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

		// ***************************************** Paso 5
		// *****************************************************

		addStep("Validar el cambio de estatus a 'E' en el documento");

		String tdcQueryDocRTVE = " SELECT ID, PV_CR_PLAZA, PV_CR_TIENDA, DOC_TYPE, STATUS, PV_DOC_NAME, PARTITION_DATE FROM POSUSER.POS_INB_DOC_RMS WHERE STATUS = 'E' AND DOC_TYPE = 'INV' AND ID IN ("
				+ Id_doc + ")";

		PreparedStatement Query2 = dbPos.prepareStatement(tdcQueryDocRTVE);
		ResultSet posEResult = Query2.executeQuery();

		java.sql.ResultSetMetaData rsltposE = posEResult.getMetaData();
		int columnCountposE = rsltposE.getColumnCount();

		if (!posEResult.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, el documento no cambio de status");
			assertFalse(!posEResult.next(), "Error!, el documento no cambio de status");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryDocRTVE);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountposE; i++) {
				String columnName = rsltposE.getColumnName(i);
				String columnValue = posEResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		posEResult.close();
		dbPos.close();

		// ***************************************** Paso 6
		// *****************************************************
		addStep("Comprobar que se registra la ejecucion en WM_LOG_RUN");

		PreparedStatement LogQuery = dbLog.prepareStatement(tdcQueryIntegrationServer);
		ResultSet LogResult = LogQuery.executeQuery();

		java.sql.ResultSetMetaData rsltlog = LogResult.getMetaData();
		int columnCountLog = rsltlog.getColumnCount();

		// Procesar los resultados
		if (!LogResult.next() && LogResult.getString("STATUS") != "S") {
			testCase.addTextEvidenceCurrentStep(
					"Error!, la interfaz terminó en Status E o no tienen ejecuciones recientes");
			assertFalse(!LogResult.next(), "Error!, la interfaz terminó en Status E o no tienen ejecuciones recientes");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryIntegrationServer);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLog; i++) {
				String columnName = rsltlog.getColumnName(i);
				String columnValue = LogResult.getString(i);
				run_status = LogResult.getString("RUN_ID");
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		LogResult.close();
		LogQuery.close();

		// ***************************************** Paso 7
		// *****************************************************

		addStep("Se valida la generacion de thread");

		String tdcQueryStatusThread = " SELECT PARENT_ID,THREAD_ID,NAME,WM_LOG_THREAD.STATUS,ATT1,ATT2 FROM WMLOG.WM_LOG_THREAD WHERE PARENT_ID IN ("
				+ run_status + ")";

		PreparedStatement LogQuery1 = dbLog.prepareStatement(tdcQueryStatusThread);

		ResultSet LogThreadResult = LogQuery1.executeQuery();

		java.sql.ResultSetMetaData rsltlogThread = LogThreadResult.getMetaData();
		int columnCountLogThread = rsltlogThread.getColumnCount();

		if (!LogThreadResult.next()) {
			testCase.addTextEvidenceCurrentStep("Error!, no hay registros de threads levantados");
			assertFalse(!LogThreadResult.next(), "Error!, no hay registros de threads levantados");
		} else {
			testCase.addTextEvidenceCurrentStep(tdcQueryStatusThread);
			// Iterar sobre las columnas y mostrar los valores
			for (int i = 1; i <= columnCountLogThread; i++) {
				String columnName = rsltlogThread.getColumnName(i);
				String columnValue = LogThreadResult.getString(i);
				testCase.addTextEvidenceCurrentStep(columnName + ": " + columnValue + " ");
			}
			System.out.println(); // Salto de línea para el siguiente registro
		}
		// Cerrar los recursos
		LogThreadResult.close();
		dbLog.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	///// Documento GEnerado

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
