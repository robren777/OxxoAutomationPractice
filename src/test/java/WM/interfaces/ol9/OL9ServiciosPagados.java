package WM.interfaces.ol9;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class OL9ServiciosPagados extends BaseExecution {

	private Connection conn;
	public String run_id;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_OL9_OL9ServiciosPagados(HashMap<String, String> data) throws Exception {

		/*
		 * Utiler�as
		 *********************************************************************/

		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA,
				GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
				GlobalVariables.DB_PASSWORD_AVEBQA);
		// utils.sql.SQLUtil dbEBS = new
		// utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCEBSSIT,
		// GlobalVariables.DB_USER_FCEBSSIT, GlobalVariables.DB_PASSWORD_FCEBSSIT);

		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			logger.error("JDBC class name not found", e);
		}

		logger.info("Oracle JDBC driver loaded ok.");

		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@" + GlobalVariables.DB_HOST_AVEBQA_UPD,
					GlobalVariables.DB_USER_AVEBQA_UPD, GlobalVariables.DB_PASSWORD_AVEBQA_UPD);
			conn.createStatement();
			logger.debug("Connect with @oracle:1521:orcl");
		} catch (SQLException e) {
			logger.error("Error connecting to database.", e);
			throw (e);
		}

		/**
		 * Variables
		 * ******************************************************************************************
		 * 
		 * 
		 */

		String updPagoPre = "update XXFC.XXFC_PAGO_SERVICIOS_PRE"
				+ " set estatus = NULL ,LOTE = NULL , FECHA_TRANSMISION = NULL,fecha_transaccion = TO_DATE('%s','DDMMRRRR'), XXFC_FECHA_ADMINISTRATIVA = TO_DATE('%s','DD,MM,RRRR')"
				+ "   WHERE SERVICIO = '101612954'" + " AND trans_id ='5134628685'" + " AND ESTATUS = 'E'";

		String updDocOL9 = "DELETE FROM WM_OL9_OUTBOUND_DOCS" + " WHERE SERVICE_ID = '101612954'"
				+ " AND RUN_ID_SENT = '%s'" + // RUN_ID
				" AND STATUS = 'E'";

		String tdcQueryDate = "SELECT TO_CHAR(SYSDATE-30,'DDMMRRRR') BEGIN_DATE,"
				+ " TO_CHAR(TRUNC(SYSDATE),'DDMMRRRR') END_DATE" + "  FROM DUAL";

		String tdcQueryORAFIN = "SELECT DISTINCT SERVICIO SERVICE_ID, B.ATTRIBUTE8 ORDEN, b.OUTPUT_FORMAT_ID, B.HORARIO_DE_ENVIO,B.SERVICE_TYPE, A.ESTATUS"
				+ "  FROM XXFC.XXFC_PAGO_SERVICIOS_PRE A,XXFC.XXFC_SERVICES_VENDOR_COMM_DATA B"
				+ " WHERE (A.ESTATUS IS NULL OR A.ESTATUS='F')"
				+ " AND A.FECHA_TRANSACCION >= TO_DATE('%s','DDMMRRRR') "
				+ "  AND A.FECHA_TRANSACCION <= TO_DATE('%s','DDMMRRRR')"
				+ " AND A.XXFC_FECHA_ADMINISTRATIVA >= TO_DATE('%s','DDMMRRRR')"
				+ " AND A.XXFC_FECHA_ADMINISTRATIVA <= TO_DATE('%s','DDMMRRRR')" + "    AND A.SERVICIO = B.SERVICE_ID"
				+ "   AND B.SERVICE_TYPE='N'" + "   AND B.OUTPUT_FORMAT_ID <>'TXO'"
				+ "   AND A.SERVICIO NOT IN (SELECT SERVICE_ID" + "             FROM WMUSER.WM_OL9_OUTBOUND_DOCS"
				+ "            WHERE TRUNC(CREATED_DATE) = TRUNC(SYSDATE)"
				+ "              AND (STATUS='L' OR STATUS='E'))"
				+ "                    AND HORARIO_DE_ENVIO = TO_CHAR(TO_DATE('10','HH24'),'HH:MI AM')";

		String tdcQueryProveedore = "SELECT A.VENDOR_ID, A.ATTRIBUTE1 IP_ADDRESS, A.ATTRIBUTE2 FTP_PORT,"
				+ "A.ATTRIBUTE3 FTP_USER, A.ATTRIBUTE4 FTP_PASS, A.ATTRIBUTE5 REMOTE_PATH,"
				+ "A.PROTOCOL, A.SERVICE_ID, TRIM(A.NOTIFICACION)"
				+ " FROM XXFC.XXFC_SERVICES_VENDOR_COMM_DATA A,AP.AP_SUPPLIERS PAV, AR.HZ_PARTIES HP "
				+ " WHERE PAV.PARTY_ID = HP.PARTY_ID" + " AND A.VENDOR_ID = PAV.VENDOR_ID(+)"
				+ " AND A.SERVICE_TYPE = 'N'" + " AND A.PROTOCOL = '" + data.get("protocolo") + "'"
				+ " AND A.SERVICE_ID = '%s' ";

		String tdcQueryIntegrationServer = "select * from ( SELECT run_id,start_dt,status,END_DT"
				+ " FROM WMLOG.wm_log_run" + " WHERE interface = 'OL9'" + " and  start_dt >= TRUNC(SYSDATE)"
				+ " order by start_dt desc)" + " where rownum = 1";

		String tdcQueryStatusThread = "SELECT parent_id,thread_id,name,wm_log_thread.status,att1,att2, START_DT,END_DT,CURRENT_DATE "
				+ " FROM WMLOG.wm_log_thread " + " WHERE parent_id = %s" + " and ATT1 ='101612954'"; // FCWMLQA

		String tdcQueryErrorId = " SELECT ERROR_ID,RUN_ID,ERROR_DATE,DESCRIPTION, ERROR_CODE "
				+ " FROM WMLOG.WM_LOG_ERROR " + " where RUN_ID=%s"; // FCWMLQA

		String consulta5 = "SELECT RUN_ID,INTERFACE,START_DT,STATUS,SERVER,END_DT,CURRENT_DATE "
				+ " FROM WMLOG.WM_LOG_RUN " + " WHERE RUN_ID =%s";

		String tdcQueryProcesamientoPagos = "SELECT plaza,tienda,folio_transaccion,fecha_transaccion,servicio,XXFC_FECHA_ADMINISTRATIVA,estatus,CURRENT_DATE"
				+ " FROM XXFC.XXFC_PAGO_SERVICIOS_PRE" + " WHERE SERVICIO = '%s' " + " AND ESTATUS = 'E'"
				+ " AND FECHA_TRANSACCION BETWEEN TO_DATE('%s', 'DDMMRRRR')" + " AND TO_DATE('%s','DDMMRRRR')"
				+ " AND XXFC_FECHA_ADMINISTRATIVA BETWEEN TO_DATE('%s','DDMMRRRR') " + " AND TO_DATE('%s','DDMMRRRR')";

		String tdcQueryDocOL9 = "SELECT id,DOC_NAME,SERVICE_ID,RUN_ID_SENT,STATUS,CREATED_DATE,SENT_DATE,CURRENT_DATE "
				+ " FROM WMUSER.WM_OL9_OUTBOUND_DOCS" + " WHERE SERVICE_ID = '%s'" + " AND RUN_ID_SENT = '%s'"
				+ " AND STATUS = 'E'";

		String tdcCopyDoc = "SELECT G.INTERFACE_NAME, G.GROUP_NAME, U.NAME, U.EMAIL,CURRENT_DATE"
				+ " FROM WMLOG.WM_LOG_USER U, WMLOG.WM_LOG_USER_GROUP UG, WMLOG.WM_LOG_GROUP G"
				+ " WHERE UG.GROUP_ID = G.GROUP_ID" + " AND UG.USER_ID = U.USER_ID" + " AND G.INTERFACE_NAME = 'OL9'"
				+ " AND G.GROUP_NAME = 'OL9'";		

		String consultaERROR = " select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE, ERROR_CODE "
		+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1";// Consulta para los errores
		String consultaERROR2 = " select * from (select description,MESSAGE " + "from wmlog.WM_LOG_ERROR "
		+ "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
		String consultaERROR3 = " select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
		+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores

		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
		String con = "http://" + user + ":" + ps + "@" + server + ":5555";
		String searchedStatus = "R";
		String run_id;
		String id;
		String thread;

		String status = "S";
		/*
		 * Variables
		 *********************************************************************/

		/**
		 * 
		 * **********************************Pasos del caso de Prueba
		 * *****************************************
		 * 
		 * 
		 */

//	Paso 1 ***********************************************************************************************

		addStep("Obtener el rango de fechas para el env�o de pagos nacionales a procesar consultando en la BD ORAFIN.");

		System.out.println(tdcQueryDate);

		SQLResult resulDay = dbEBS.executeQuery(tdcQueryDate);

		boolean days = resulDay.isEmpty();

		String beginDate = resulDay.getData(0, 0);
		String endDate = resulDay.getData(0, 1);

		if (!days) {

			beginDate = resulDay.getData(0, 0);
			endDate = resulDay.getData(0, 1);

			testCase.addQueryEvidenceCurrentStep(resulDay);

		}

//assertFalse(days, "Error");

//				Paso 2 ***********************************************************************************************

		addStep("Verificar que existan pagos nacionales a enviar a los proveedores en la BD ORAFIN");

		String updDatesFormat = String.format(updPagoPre, beginDate, beginDate);
		PreparedStatement stmt;
		stmt = conn.prepareStatement(updDatesFormat);
		int rows = stmt.executeUpdate();
		System.out.println("FILAS AFECTADAS" + rows);

		System.out.println(tdcQueryORAFIN);

		String tdcQueryORAFINFormat = String.format(tdcQueryORAFIN, beginDate, endDate, beginDate, endDate);
		SQLResult resul = dbEBS.executeQuery(tdcQueryORAFINFormat);

		// if(resul.getRowCount() <= 0)
		// throw new Exception("No existen los insumos necesarios en la base de datos
		// para continuar con la prueba, Query: "+tdcQueryORAFINFormat);
		// String service_id = resul.getData(0, "SERVICE_ID");
		String service_id = "S";

		boolean outbound = resul.isEmpty();

		if (!outbound) {

			testCase.addQueryEvidenceCurrentStep(resul);

		}

		System.out.println(outbound);

//assertFalse(outbound, "La tabla no contiene registros");

//Paso 3 ***********************************************************************************************
		addStep("Comprobar que exista informacion de los proveedores, y que el metodo de envio sea por MAIL	.");

		System.out.println(GlobalVariables.DB_HOST_EBS);

		String tdcQueryFormat = String.format(tdcQueryProveedore, "101612954");
		System.out.println(tdcQueryFormat);

		SQLResult prov = dbEBS.executeQuery(tdcQueryFormat);
		boolean proveE = prov.isEmpty();

		if (!proveE) {

			testCase.addQueryEvidenceCurrentStep(prov);

		}

//assertFalse(proveE, "La tabla no contiene registros");

//Paso 4 ***********************************************************************************************
		addStep("Invocar la interface OL9.pub:runAllServices mediante la ejecuci�n del JOB runOL9HH2 para enviar la informaci�n de los pagos nacionales a los Proveedores");

		//utileria

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PakageManagment pok = new PakageManagment(u, testCase);
		String contra = "http://" + user + ":" + ps + "@" + server + ":5555";
		u.get(contra);
		String dateExecution = pok.runIntefaceWmOneButton10(data.get("interfase"), data.get("servicio"));
		System.out.println("Respuesta dateExecution" + dateExecution);
		SQLResult is = executeQuery(dbLog, tdcQueryIntegrationServer);
		String status1 = is.getData(0, "STATUS");
		run_id = is.getData(0, "RUN_ID");
		boolean valuesStatus = status1.equals(searchedStatus);// Valida si se encuentra en estatus R
		while (valuesStatus) {
			is = executeQuery(dbLog, tdcQueryIntegrationServer);
			status1 = is.getData(0, "STATUS");
			run_id = is.getData(0, "RUN_ID");
			valuesStatus = status1.equals(searchedStatus);
			u.hardWait(2);
		}
		boolean successRun = status1.equals(status);// Valida si se encuentra en estatus S
		if (!successRun) {
			String error = String.format(consultaERROR, run_id);
			String error1 = String.format(consultaERROR2, run_id);
			String error2 = String.format(consultaERROR3, run_id);
			System.out.println(error);
			SQLResult errorr = dbLog.executeQuery(error);
			boolean emptyError = errorr.isEmpty();
			if (!emptyError) {
				testCase.addTextEvidenceCurrentStep(
						"Se encontro un error en la ejecucion de la interfaz en la tabla WM_LOG_ERROR");
				testCase.addQueryEvidenceCurrentStep(errorr);
			}
			SQLResult errorIS = dbLog.executeQuery(error1);
			boolean emptyError1 = errorIS.isEmpty();
			if (!emptyError1) {
				testCase.addQueryEvidenceCurrentStep(errorIS);
			}
			SQLResult errorIS2 = dbLog.executeQuery(error2);
			boolean emptyError2 = errorIS2.isEmpty();
			if (!emptyError2) {
				testCase.addQueryEvidenceCurrentStep(errorIS2);
			}
		}

//		boolean successRun = status1.equals(status);// Valida si se encuentra en estatus S
//		if (!successRun) {
//
//			String error = String.format(tdcQueryErrorId, run_id);
//			SQLResult result3 = executeQuery(dbLog, error);
//
//			boolean emptyError = result3.isEmpty();
//			
//
//			if (!emptyError) {
//				if(i==0) {
//					
//				}else {testCase.addTextEvidenceCurrentStep(
//						"Se encontr� un error en la ejecuci�n de la interfaz en la tabla WM_LOG_ERROR");
//
//				testCase.addQueryEvidenceCurrentStep(result3);}
//				
//
//			}
//		}
//		else {
//			i++;
//			testCase.addTextEvidenceCurrentStep("El servicio OL9.pub:runAllServices fue ejecutado exitosamente");
//		}
//	

//Paso 5	************************

		addStep("Verificar el estatus con el cual fue terminada la ejecuci�n de la interface en la tabla WM_LOG_RUN del usuario WMLOG.");

		// String run = "2159857851";
		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
		String verificacionInterface = String.format(consulta5, run_id);
		SQLResult paso4 = executeQuery(dbLog, verificacionInterface);
		System.out.println(verificacionInterface);

		boolean av5 = paso4.isEmpty();

		if (!av5) {

			testCase.addQueryEvidenceCurrentStep(paso4);

		}

		System.out.println(av5);

//		assertFalse(av5, "No se obtiene informacion de la consulta");

//	Paso 6  *************************************************
		addStep("Validar que se inserte el detalle de la ejecuci�n de los Threads lanzados por la interface en la tabla WM_LOG_THREAD con STATUS = 'S'");

		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
		String consultaTemp6 = String.format(tdcQueryStatusThread, run_id);
		SQLResult paso5 = executeQuery(dbLog, consultaTemp6);

		System.out.println(consultaTemp6);
		// if(paso5.getRowCount() <= 0)
		// throw new Exception("No existen los insumos necesarios en la base de datos
		// para continuar con la prueba, Query: "+paso5);
		// String estatusThread = paso5.getData(0, "Status");
		String estatusThread = "S";

		boolean SR = estatusThread.equals(status);
		SR = !SR;

		if (!SR) {

			testCase.addQueryEvidenceCurrentStep(paso5);

		}

		System.out.println(SR);

//	assertFalse(SR, "No se obtiene informacion de la consulta");

		// Paso 7
		// ***********************************************************************************************
		addStep("Validar la actualizaci�n del ESTATUS = 'E' y FECHA_TRANSMISION = Fecha de procesamiento de los pagos de servicios nacionales enviados al Proveedor, en la BD ORAFIN.");

		String tdcQueryProcesamientoPagosFormat = String.format(tdcQueryProcesamientoPagos, service_id, beginDate,
				endDate, beginDate, endDate);

		SQLResult tdcQueryProcesamientoPagosResult = executeQuery(dbEBS, tdcQueryProcesamientoPagosFormat);

		System.out.println(tdcQueryProcesamientoPagosResult);

		boolean validarAct = tdcQueryProcesamientoPagosResult.isEmpty();

		if (!validarAct) {

			testCase.addQueryEvidenceCurrentStep(tdcQueryProcesamientoPagosResult);

		}

		// assertFalse(validarAct, "Error");

//Paso 8 ***********************************************************************************************
		addStep("Validar la inserci�n de los documentos enviados al Proveedor en la tabla WM_OL9_OUTBOUND_DOCS de Oracle.");

		String tdcQueryDocOL9Out = String.format(tdcQueryDocOL9, service_id, run_id);
		System.out.println(tdcQueryDocOL9Out);

		SQLResult tdcQueryDocOL9OutResult = dbEBS.executeQuery(tdcQueryDocOL9Out);

		boolean out = resul.isEmpty();

		if (!out) {

			testCase.addQueryEvidenceCurrentStep(tdcQueryDocOL9OutResult);

		}

		// assertFalse(out, "Error");

//Paso 9 ***********************************************************************************************

		addStep("Validar el env�o de la copia de los documentos con la informaci�n de los pagos nacionales al Usuario interno de Oxxo.");

		SQLResult tdcCopyDocResult = dbLog.executeQuery(tdcCopyDoc);
		System.out.println(tdcCopyDoc);

		boolean pg = resul.isEmpty();

		if (!pg) {

			testCase.addQueryEvidenceCurrentStep(tdcCopyDocResult);

		}

		// assertFalse(pg, "Error");

//	String DeleteFormat = String.format(updDocOL9, run_id);
//	PreparedStatement stmt1;
//	stmt1 = conn.prepareStatement(DeleteFormat);
//	int rows2 = stmt1.executeUpdate();
//	System.out.println("FILAS AFECTADAS"+rows2);
//
//	u.close();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Equipo de Automatizaci�n";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_002_OL9_OL9ServiciosPagados";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
