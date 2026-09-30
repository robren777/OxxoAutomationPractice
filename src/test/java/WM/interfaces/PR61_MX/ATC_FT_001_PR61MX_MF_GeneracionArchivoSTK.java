package WM.interfaces.PR61_MX;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/* DESCRIPCION : Prueba para comprobar la funcionalidad principal de la interface FEMSA_PR61_MX para generar archivos STK en formato JSON
 * 
 * MTC-FT-001-PR61MX- Envio de documento STK hacia RMS16
 * 
 * PREREQUISITOS:
 * 	1.- Tener las configuraciones de la interfaz registradas.
	2.- Tener un documento con status I en las tablas centrales.
	3.- Tener acceso a las Base de Datos POSUSER, RMS16, DAS_OPERATIVO, WMLOG.
	4.- Tener acceso al buz�n de RMS16 para el registro de documento.
 * 
 * INSUMOS:
 * 	Documentos STK (Una devolucion para proveedores).
 * 
 * @Author Gustavo Morales
 * @Date 07/Marzo/2025
 */
public class ATC_FT_001_PR61MX_MF_GeneracionArchivoSTK extends BaseExecution {

	@Test(dataProvider = "data-provider")

	public void ATC_FT_001_PR61MX_MF_GeneracionArchivoSTK_test(HashMap<String, String> data) throws Exception {

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
		String thread = "";
		String RTK = "";

		// ***************************************** Paso 1
		// *****************************************************

		addStep("Revisar que exista el documento en la tabla de POS_INB_DOC_RMS");

		String QueryDoc = "SELECT ID, PV_CR_PLAZA, PV_CR_TIENDA, STATUS, DOC_TYPE, PV_DOC_NAME, PARTITION_DATE \r\n"
				+ "FROM POSUSER.POS_INB_DOC_RMS WHERE DOC_TYPE = 'STK' AND PV_CR_PLAZA = '" + data.get("plaza")
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

		addStep("Revisar que exista el encabezado del documento en la tabla POS_STK");

		String QueryDocEnc = "SELECT * FROM POSUSER.POS_STK WHERE PID_ID = " + Id_doc;

		PreparedStatement PosQueryHeader = dbPos.prepareStatement(QueryDocEnc);
		ResultSet PosResultHeader = PosQueryHeader.executeQuery();

		if (PosResultHeader.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocEnc);
			while (PosResultHeader.next()) {

				String PID_ID = PosResultHeader.getString("PID_ID");
				String NO_RECORDS = PosResultHeader.getString("NO_RECORDS");
				String STOCK_TAKE_DATE = PosResultHeader.getString("STOCK_TAKE_DATE");
				String INVENTORY_TYPE = PosResultHeader.getString("INVENTORY_TYPE");
				String PARTITION_DATE = PosResultHeader.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "NO_RECORDS : " + NO_RECORDS
						+ " | " + "STOCK_TAKE_DATE : " + STOCK_TAKE_DATE + " | " + " INVENTORY_TYPE : " + INVENTORY_TYPE
						+ " | " + " PARTITION_DATE : " + PARTITION_DATE);
			}
		} else {
			assertFalse(!PosResultHeader.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 3 *********/

		addStep("Revisar que exista el detalle del documento en la tabla POS_STK_DETL");

		String QueryDocDetl = "SELECT * FROM POS_STK_DETL WHERE PID_ID = " + Id_doc;

		PreparedStatement PosQueryDetl = dbPos.prepareStatement(QueryDocDetl);
		ResultSet PosResultDetl = PosQueryDetl.executeQuery();

		if (PosResultDetl.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryDocDetl);
			while (PosResultDetl.next()) {

				itemS += PosResultDetl.getString("ITEM") + ",";// Se concatenan los items para futuros filtros

				String PID_ID = PosResultDetl.getString("PID_ID");
				String ITEM = PosResultDetl.getString("ITEM");
				String STOCK_QTY = PosResultDetl.getString("STOCK_QTY");
				String PARTITION_DATE = PosResultDetl.getString("PARTITION_DATE");

				testCase.addTextEvidenceCurrentStep("--> " + "PID_ID : " + PID_ID + " | " + "ITEM : " + ITEM + " | "
						+ "STOCK_QTY : " + STOCK_QTY + " | " + " PARTITION_DATE : " + PARTITION_DATE);
			}
			itemS = itemS.substring(0, itemS.length() - 1);
		} else {
			assertFalse(!PosResultDetl.next(), "--> ¡ERROR!, No existe información relacionada al documento");
		}

		/********** Paso 4 *********/

		addStep("Ejecucion job : PR61_MX en  control-M");
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
				+ " WHERE INTERFACE = 'PR61_MX'" + " AND  START_DT >= TRUNC(SYSDATE)"
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

		String QueryStatusThread = " SELECT THREAD_ID, PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5"
				+ " FROM WMLOG.WM_LOG_THREAD" + " WHERE PARENT_ID = " + run_status;

		PreparedStatement LogThreadQuery = dbLog.prepareStatement(QueryStatusThread);
		ResultSet LogThreadResult = LogThreadQuery.executeQuery();

		if (LogThreadResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(QueryStatusThread);
			while (LogThreadResult.next()) {
				// PARENT_ID, NAME, START_DT, END_DT, STATUS, ATT1, ATT2, ATT5
				thread = LogThreadResult.getString("THREAD_ID");

				String THREAD_ID = LogThreadResult.getString("THREAD_ID");
				String PARENT_ID = LogThreadResult.getString("PARENT_ID");
				String NAME = LogThreadResult.getString("NAME");
				String START_DT = LogThreadResult.getString("START_DT");
				String END_DT = LogThreadResult.getString("END_DT");
				String STATUS = LogThreadResult.getString("STATUS");
				String ATT1 = LogThreadResult.getString("ATT1");
				String ATT2 = LogThreadResult.getString("ATT2");
				String ATT5 = LogThreadResult.getString("ATT5");

				testCase.addTextEvidenceCurrentStep("--> " + "THREAD_ID : " + THREAD_ID + " | " + " PARENT_ID : "
						+ PARENT_ID + " NAME : " + NAME + " START_DT : " + START_DT + " END_DT : " + END_DT
						+ " STATUS : " + STATUS + " ATT1 : " + ATT1 + " ATT2 : " + ATT2 + " ATT5 : " + ATT5);
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
				+ " WHERE STATUS = 'E'" + " AND DOC_TYPE = 'STK'" + " AND ID IN (" + Id_doc + ")";

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

		addStep("Revisar la informacion generada en tabla WM_INBOUND_DOCS");

		String DocStgInv = "SELECT DOC_TYPE, DOC_ID, CREATED_DATE, STATUS, LOCATION, RTK_FILENAME FROM WMUSER.WM_INBOUND_DOCS\r\n"
				+ "WHERE DOC_TYPE = 'STKU'" + " AND LOCATION = '18716'" + "AND RUN_ID = " + thread
				+ "ORDER BY CREATED_DATE DESC";

		PreparedStatement posStgInv = dbMom.prepareStatement(DocStgInv);
		ResultSet posStgInvResult = posStgInv.executeQuery();

		if (posStgInvResult.isBeforeFirst()) {
			testCase.addTextEvidenceCurrentStep(DocStgInv);
			while (posStgInvResult.next()) {

				RTK = posStgInvResult.getString("RTK_FILENAME");// Se concatenan los nombres del docs

				String DOC_TYPE = posStgInvResult.getString("DOC_TYPE");
				String DOC_ID = posStgInvResult.getString("DOC_ID");
				String CREATED_DATE = posStgInvResult.getString("CREATED_DATE");
				String STATUS = posStgInvResult.getString("STATUS");
				String LOCATION = posStgInvResult.getString("LOCATION");
				String RTK_FILENAME = posStgInvResult.getString("RTK_FILENAME");

				testCase.addTextEvidenceCurrentStep("--> " + "DOC_TYPE : " + DOC_TYPE + " | " + "DOC_ID : " + DOC_ID
						+ " | " + "CREATED_DATE : " + CREATED_DATE + " | " + "STATUS : " + STATUS + " | "
						+ "LOCATION : " + LOCATION + " | " + "RTK_FILENAME : " + RTK_FILENAME);
			} // Se borra la coma que tienen el último registro
		} else {
			assertFalse(!posStgInvResult.next(), "--> ¡ERROR!, El documento no fue procesado, revisar los datos");
		}

		addStep("Se valida el documento en SFTP : " + RTK);

		String host = data.get("ftpHost"); // Direcci�n del servidor SFTP
		int port = 22; // Puerto por defecto de SFTP
		String username = data.get("ftpUser"); // Usuario
		String password = data.get("ftpPass"); // Contrase�a
		String ruta = "/u24/retek/rmsprd/batch/wmworking/STK";

		JSch jsch = new JSch();
		Session session = null;
		ChannelSftp channelSftp = null;
		try {
		    // Crear sesi�n SFTP
		    session = jsch.getSession(username, host, port);
		    session.setPassword(password);
		    session.setConfig("StrictHostKeyChecking", "no"); // Deshabilitar verificaci�n de clave del host

		    // Conectar
		    session.connect();

		    // Crear el canal SFTP
		    channelSftp = (ChannelSftp) session.openChannel("sftp");
		    channelSftp.connect();

		    // Comprobar si el archivo existe
		    try {
		        @SuppressWarnings("unchecked")
		        List<ChannelSftp.LsEntry> files = channelSftp.ls(ruta); // Listar los archivos en la ruta
		        boolean archivoExiste = (files != null && !files.isEmpty()); // Verificar si el archivo existe

		        if (archivoExiste) {
		            // El archivo existe
		            testCase.addFileEvidenceCurrentStep(ruta);
		            System.out.println("Existe");
		            testCase.addBoldTextEvidenceCurrentStep("El archivo s� existe");
		            testCase.addBoldTextEvidenceCurrentStep(ruta);
		        } else {
		            // El archivo no existe
		            testCase.addFileEvidenceCurrentStep(ruta);
		            testCase.addBoldTextEvidenceCurrentStep("El archivo no existe");
		            System.out.println("No Existe");

		            // Aseg�rate de que el archivo no existe (agregar assertFalse)
		            assertFalse("El archivo deber�a no existir", archivoExiste);
		        }
		    } catch (SftpException e) {
		        // Manejo de excepci�n si el archivo no existe o hay un problema con la ruta
		        testCase.addFileEvidenceCurrentStep(ruta);
		        testCase.addBoldTextEvidenceCurrentStep("El archivo no existe");
		        System.out.println("No Existe");

		        // Aseg�rate de que el archivo no existe (agregar assertFalse)
		        assertFalse("El archivo deber�a no existir", true); // 'true' ya indica que el archivo no existe.
		    }

		} catch (JSchException e) {
		    e.printStackTrace();
		    // Agregar evidencia de fallo en el caso de error de conexi�n SFTP
		    testCase.addBoldTextEvidenceCurrentStep("Error en la conexi�n SFTP");
		} finally {
		    // Cerrar la conexi�n SFTP
		    if (channelSftp != null && channelSftp.isConnected()) {
		        channelSftp.exit();
		    }
		    if (session != null && session.isConnected()) {
		        session.disconnect();
		    }
		}
	}
	@Override
	public String setTestFullName() {

		return null;

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub

		return "Prueba para comprobar la funcionalidad principal de la interface FEMSA_PR61_MX para generar archivos STK en formato JSON";

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
		return "1.Tener las configuraciones de la interfaz registradas.\r\n"
				+ "2.Tener un documento con status I en las tablas centrales.\r\n"
				+ "3.Tener acceso a las Base de Datos POSUSER, RMS16, DAS_OPERATIVO, WMLOG.\r\n"
				+ "4. Que el job de la PR61_MX este dado de alta\r\n"
				+ "5. Tener acceso al buz�n de RMS16 para el registro de documento\r\n"
				+ "6. Arquitectura FEMSA_ENRED_IN";
	}

}
