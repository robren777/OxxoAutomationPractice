package WM.interfaces.po9;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class PO9_Nucleo_EnvioDocumentosTPE extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_PO9_003_Nucleo_EnvioDocumentosTPE(HashMap<String, String> data) throws Exception {

		/*
		 * Utiler�as
		 *********************************************************************/

		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA,
				GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_fcwmesit,
				GlobalVariables.DB_USER_fcwmesit, GlobalVariables.DB_PASSWORD_fcwmesit);
		utils.sql.SQLUtil dbEbs = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCEBSSIT,
				GlobalVariables.DB_USER_FCEBSSIT, GlobalVariables.DB_PASSWORD_FCEBSSIT);
		utils.sql.SQLUtil dbRMS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCRMSMGR,
				GlobalVariables.DB_USER_FCRMSMGR, GlobalVariables.DB_PASSWORD_FCRMSMGR);

		/**
		 * Variables
		 * ******************************************************************************************
		 * 
		 * 
		 */

		String ValidConfigCutover = "select APPLICATION,PROC_CODE,CR_PLAZA,CR_TIENDA,DESCRIPTION,CUTOVER_FLAG,CUTOVER_DATE,MODIFIED_DATE "
				+ "from WMUSER.WM_FW_BO_ENCENDIDO " + "where APPLICATION = 'PO9'";

		String tdcQueryTPE = "SELECT ID,PE_ID,PV_DOC_ID,STATUS,DOC_TYPE,PV_DOC_NAME,RECEIVED_DATE,INSERTED_DATE "
				+ "FROM POSUSER.POS_INBOUND_DOCS " + "where STATUS = 'I' and DOC_TYPE = 'TPE'";

		String consultaERROR = " select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE, ERROR_CODE "
				+ "from  wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1";// Consulta para los errores
		String consultaERROR2 = " select * from (select description,MESSAGE " + "from wmlog.WM_LOG_ERROR "
				+ "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
		String consultaERROR3 = " select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
				+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores

		String tdcQueryIntegrationServer = "SELECT * FROM (SELECT run_id, start_dt, status FROM WMLOG.wm_log_run"
				+ " WHERE interface = 'PO9'" + " AND start_dt >= TRUNC(SYSDATE)"
				+ " ORDER BY start_dt desc) WHERE ROWNUM = 1";

		String tdcQueryStatusThread = "SELECT parent_id,thread_id,name,status,att1,att2, START_DT, END_DT "
				+ " FROM WMLOG.wm_log_thread " + " WHERE parent_id = %s"; // FCWMLQA

		String tdcQueryErrorId = " SELECT ERROR_ID,RUN_ID,ERROR_DATE,DESCRIPTION " + " FROM WMLOG.WM_LOG_ERROR "
				+ " where RUN_ID=%s"; // FCWMLQA

		String tdcQueryStatusLog = "select * from (SELECT run_id,interface,start_dt,END_DT,status,server "
				+ " FROM WMLOG.wm_log_run" + " WHERE interface = '" + data.get("Run_interface") + "'"
//				+ " and status= 'S' " 
				+ " and start_dt >= trunc(sysdate) " + "AND run_id = '%s' " + " ORDER BY start_dt DESC)"
				+ " where rownum = 1";

		String tdcQueryTPEDoc = " SELECT ID,PE_ID,PV_DOC_ID,STATUS,DOC_TYPE " + " FROM POSUSER.POS_INBOUND_DOCS "
				+ " WHERE STATUS = 'E'" + "  and DOC_TYPE = 'TPE'" + " AND ID = '%s'";

		String tdcQueryTPEDoc2 = " SELECT PV_DOC_NAME,RECEIVED_DATE,INSERTED_DATE " + " FROM POSUSER.POS_INBOUND_DOCS "
				+ " WHERE STATUS = 'E'" + "  and DOC_TYPE = 'TPE'" + " AND ID = '%s'";

		String tdcQueryInterface = "SELECT status, currency_code, date_created " + "FROM GL.GL_INTERFACE "
				+ "WHERE STATUS='AVANTE' " + "AND ACTUAL_FLAG ='U' " + "AND DATE_CREATED >= trunc(sysdate) ";

		String tdcQueryInterface2 = "SELECT created_by, actual_flag, user_je_category_name " + "FROM GL.GL_INTERFACE "
				+ "WHERE STATUS='AVANTE' " + "AND ACTUAL_FLAG ='U' " + "AND DATE_CREATED >= trunc(sysdate) ";

		String status = "S";
//utileria

		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
		String con = "http://" + user + ":" + ps + "@" + server + ":5555";
		String searchedStatus = "R";
		String run_id;
		String id;
		String thread;

		/**
		 * Script de Pasos
		 * ******************************************************************************************
		 * 
		 * 
		 */

//****************************paso 1*********************************************************************************************

		addStep("Validar la configuraci�n de la bandera de cutover  este en estatus S  encendida en la tabla FEMSA_FW_BO");

		System.out.println(GlobalVariables.DB_HOST_fcwmesit);
		System.out.println(ValidConfigCutover);

		SQLResult ConfigCutover = executeQuery(dbPos, ValidConfigCutover);
		String statusCutover = "";
		boolean ValConfigCutover = ConfigCutover.isEmpty();
		boolean ValidStatus = false;
		System.out.println(ValConfigCutover);
		if (!ValConfigCutover) {
			statusCutover = ConfigCutover.getData(0, "CUTOVER_FLAG");
			ValidStatus = statusCutover != "S";
			if (!ValidStatus) {
				testCase.addTextEvidenceCurrentStep("El status de la bandera de cutover no es S");
			}

		}
		testCase.addQueryEvidenceCurrentStep(ConfigCutover);

		// assertFalse(docTPEI, "No se encuentran resultados");

//***********************************************Paso 2**********************************************

		addStep("Ingresar a la BD de WM y validar que existan registros en las tablas POS_INBOUND_DOCS con STATUS igual  a 'I' y DOC_TYPE = 'TPE'");

		System.out.println(GlobalVariables.DB_HOST_fcwmesit);
		System.out.println(tdcQueryTPE);
		String IdTPE = "";
		SQLResult ValidTPE = executeQuery(dbPos, tdcQueryTPE);

		boolean ValValidTPE = ValidTPE.isEmpty();

		if (!ValValidTPE) {
			IdTPE = ValidTPE.getData(0, "ID");
			System.out.println("IdTPE: " + IdTPE);
		} else {
			testCase.addTextEvidenceCurrentStep(
					"No existen registros en las tablas POS_INBOUND_DOCS con STATUS igual  a 'I' "
							+ "y DOC_TYPE = 'TPE' /n" + tdcQueryTPE);

		}
		testCase.addQueryEvidenceCurrentStep(ValidTPE);

//*******************************************paso 3*********************************************************
		addStep("Ejecutar  el servicio PO9.Pub:run.", "Ejecuci�n de la interfaz sin error ");

		// utileria
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
//*****************************************************Paso 4	************************

		addStep("Validar que la ejecuci�n del JOB PO9 haya terminado exitosamente STATUS = 'S' mediante la siguiente consulta:");

		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);

		String queryStatusLog = String.format(tdcQueryStatusLog, run_id);
		System.out.println(queryStatusLog);

		SQLResult StatusLog = executeQuery(dbLog, queryStatusLog);
		boolean ValidTab = StatusLog.isEmpty();
		String fcwS = "";
		if (!ValidTab) {
			fcwS = StatusLog.getData(0, "STATUS");
		}

		boolean validateStatus = status.equals(fcwS);
		if (!validateStatus) {
			testCase.addTextEvidenceCurrentStep("El estatus de ejecucion no termino en S");
		}

		testCase.addQueryEvidenceCurrentStep(StatusLog);
		System.out.println(validateStatus);

//Paso 5	************************

		addStep("Verificar que se registren las ejecuciones de los threads en WMLOG");

		String queryStatusThread = String.format(tdcQueryStatusThread, run_id);
		System.out.println(queryStatusThread);

		SQLResult StatusThread = executeQuery(dbLog, queryStatusThread);

		boolean statusThread = StatusThread.isEmpty();
		if (statusThread) {
			testCase.addTextEvidenceCurrentStep("No se generaron threads \n" + queryStatusThread);

		}
		testCase.addQueryEvidenceCurrentStep(StatusThread);

		System.out.println(statusThread);

		// assertTrue(statusThread, "El registro de ejecuci�n de la plaza y tienda no
		// fue exitoso");

//************************************Paso 6***************************************************************
		addStep("Comprobar que los documentos fueron actualizados correctamente en la tabla POS_INBOUND_DOCS en la BD WM donde WMSTATUS igual a 'E'. ");

		System.out.println(GlobalVariables.DB_HOST_fcwmesit);

		String TPEDocE = String.format(tdcQueryTPEDoc, IdTPE);
		String TPEDocE2 = String.format(tdcQueryTPEDoc2, IdTPE);
		System.out.println(TPEDocE);
		System.out.println(TPEDocE2);

		SQLResult DocEnviados = executeQuery(dbPos, TPEDocE);
		SQLResult DocEnviados2 = executeQuery(dbPos, TPEDocE2);

		boolean DocSend = DocEnviados.isEmpty();

		if (DocSend) {
			testCase.addTextEvidenceCurrentStep(
					"los documentos NO fueron actualizados correctamente el status no es igual a 'E'.");
		}
		testCase.addQueryEvidenceCurrentStep(DocEnviados);
		testCase.addQueryEvidenceCurrentStep(DocEnviados2);
		// assertFalse(DocSend, "no se encuentran docuemnetos enviados");

//***************************paso 7*************************************************************

		addStep("Comprobar que los registros se insertaron correctamente en la Tabla GL_INTERFACE de la instancia 12.2.4   "
				+ "con es estatus AVANTE y  ACTUAL FLAG en U");

		System.out.println(GlobalVariables.DB_HOST_FCEBSSIT);
		System.out.println(tdcQueryInterface);
		System.out.println(tdcQueryInterface2);
		SQLResult fin = executeQuery(dbEbs, tdcQueryInterface);
		SQLResult fin2 = executeQuery(dbEbs, tdcQueryInterface2);

		boolean finanzas = fin.isEmpty();

		if (!finanzas) {

			testCase.addTextEvidenceCurrentStep(
					"NO se insertaron registros  correctamente en la Tabla GL_INTERFACE /n" + tdcQueryInterface);

		}
		testCase.addQueryEvidenceCurrentStep(fin);
		testCase.addQueryEvidenceCurrentStep(fin2);

		// assertFalse(finanzas, "se encuentra error ");

	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_PO9_003_Nucleo_EnvioDocumentosTPE";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
