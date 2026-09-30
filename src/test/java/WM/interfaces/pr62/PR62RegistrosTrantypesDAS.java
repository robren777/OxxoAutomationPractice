package WM.interfaces.pr62;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class PR62RegistrosTrantypesDAS extends BaseExecution {
	@Test (dataProvider = "data-Provider")

	public void test (HashMap<String, String> data ) throws Exception {
		/*
		 * Utilerias
		 **************************************************************/
		utils.sql.SQLUtil dbDas = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDASPFR, GlobalVariables.DB_USER_FCDASPFR, GlobalVariables.DB_PASSWORD_FCDASPFR);
		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil( GlobalVariables.DB_HOST_FCWMLQA, GlobalVariables.DB_USER_FCWMLQA,GlobalVariables.DB_PASSWORD_FCWMLQA);
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMQA_NUEVA,GlobalVariables.DB_USER_FCWMQA_NUEVA,GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		
		/*Variables*******************************************************************************/
		
		String getStatus = "select * from WMLOG.WM_LOG_RUN "
							+ "where INTERFACE = 'PR62' order by END_DT DESC";
		
		String mailBoxPath = "select * from WMUSER.WM_FTP_CONNECTIONS;";
		
		String tdcQueryIntegrationServer = "	select * from (SELECT Tbl.run_id,interface, start_dt, end_dt, status, server, CURRENT_DATE AS EXECUTION_DATE \r\n" + 
				"FROM WMLOG.WM_LOG_RUN Tbl WHERE INTERFACE = 'PR62'  \r\n" + 
				"ORDER BY START_DT DESC) where rownum <=1";
		
		//Consultas Error
		String consultaERROR = " select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE, ERROR_CODE "
				+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1";// Consulta para los errores
		
		String consultaERROR2 = " select * from (select description,MESSAGE " + "from wmlog.WM_LOG_ERROR "
				+ "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
		
		String consultaERROR3 = " select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
				+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
		
		//Pasos***********************************************************************************
		
		addStep("Ingresar a Control-M con las credenciales correspondientes y Ejecutar el job: runPR62_MX.");
		
		//utileria
		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
		String con = "http://" + user + ":" + ps + "@" + server + ":5555";
		String searchedStatus = "R";
		String status = "S";
		String run_id;
		String id;
		String thread;

		
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
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return null;
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
}
