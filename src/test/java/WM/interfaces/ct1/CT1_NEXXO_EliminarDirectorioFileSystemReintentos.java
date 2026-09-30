package WM.interfaces.ct1;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class CT1_NEXXO_EliminarDirectorioFileSystemReintentos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_CT1_NEXXO_EliminarDirectorioFileSystemReintentos(HashMap<String, String> data) throws Exception {

		/*
		 * Utiler�as
		 *********************************************************************/

		SQLUtil dbLog = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA, GlobalVariables.DB_USER_FCWMLQA,
				GlobalVariables.DB_PASSWORD_FCWMLQA);
		SQLUtil dbCNT = new SQLUtil(GlobalVariables.DB_HOST_FCIASQA, GlobalVariables.DB_USER_FCIASQA,
				GlobalVariables.DB_PASSWORD_FCIASQA);

		/**
		 * Variables
		 * ******************************************************************************************
		 * 
		 */
//		Paso 1
		String PerfilaElim = "select ID,CR_PLAZA,CR_TIENDA,WM_STATUS,CREATION_DATE,WM_RUN_ID "
				+ "from WMUSER.WM_BUZONES_T_TIENDAS " + "WHERE WM_STATUS_BUZON='P' " + "and CR_PLAZA= '"
				+ data.get("plaza") + "' " + "and CR_TIENDA= '" + data.get("tienda") + "' ";

		// Paso 3

		String tdcIntegrationServerFormat = "select * from (SELECT run_id,interface, start_dt, end_dt, status, server, (END_DT - START_DT)*24*60 "
				+ "FROM WMLOG.WM_LOG_RUN " + "WHERE INTERFACE LIKE '%CT1%' "
				+ " ORDER BY START_DT DESC) where rownum <=1";

		String consultaERROR = " select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE "
				+ "from  wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1";// Consulta para los errores
		String consultaERROR2 = " select * from (select description,MESSAGE " + "from wmlog.WM_LOG_ERROR "
				+ "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
		String consultaERROR3 = " select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
				+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores

		String ValidaLog = "select * from (SELECT run_id,interface, start_dt, end_dt, status, server, (END_DT - START_DT)*24*60 "
				+ "FROM WMLOG.WM_LOG_RUN " + "WHERE INTERFACE LIKE '%CT1%' " + "AND STATUS = 'E' "
				+ " ORDER BY START_DT DESC) where rownum <=1";
//Paso 4		
		String consultaTHREAD = "select * from (SELECT THREAD_ID, PARENT_ID, NAME,START_DT, END_DT, STATUS  "
				+ "FROM wmlog.WM_LOG_THREAD" + " where PARENT_ID= '%s' ) where rownum <=1";

//	Paso 5	    

		String PerfilElim = "select ID,CR_PLAZA,CR_TIENDA,WM_STATUS,CREATION_DATE,WM_RUN_ID "
				+ "from WMUSER.WM_BUZONES_T_TIENDAS " + "WHERE WM_STATUS_BUZON='S' " + "and CR_PLAZA= '"
				+ data.get("plaza") + "' " + "and CR_TIENDA= '" + data.get("tienda") + "' ";

		/**
		 * 
		 * **********************************Pasos del caso de Prueba
		 * *****************************************
		 * 
		 */

//********************************************Paso 1*****************************************************************************
		
		addStep("Verificar que existan registros de directorio a eliminar (WM_STATUS_BUZON= 'P').");

		System.out.println(GlobalVariables.DB_HOST_FCIASQA);
		System.out.println(PerfilaElim);

		SQLResult ValidRegistro_Res = executeQuery(dbCNT, PerfilaElim);

		boolean validRegistro = ValidRegistro_Res.isEmpty();

		if (!validRegistro) {

			testCase.addQueryEvidenceCurrentStep(ValidRegistro_Res);

		} else if (validRegistro) {
			testCase.addTextEvidenceCurrentStep("No se encontraron resultados en la busqueda \n" + PerfilaElim);
			testCase.addQueryEvidenceCurrentStep(ValidRegistro_Res);
		}

		System.out.println(validRegistro);

		assertFalse(validRegistro, "No se encontro registro de la plaza y tienda");

//**********************************************Paso 2	****************************************************************************** 

		addStep("Ejecutar el JOB runCT1_BuzonDelete para invocar la interface por medio del servicio CT1.Pub:runBuzonDelete ");

		// Utileria

		String status = "S";
		// utileria
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PakageManagment pok = new PakageManagment(u, testCase);

		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
		String searchedStatus = "R";

		String contra = "http://" + user + ":" + ps + "@" + server + ":5555";
		u.get(contra);

		String dateExecution = pok.runIntefaceWmOneButton10(data.get("interfase"), data.get("servicio"));
		System.out.println("Respuesta dateExecution" + dateExecution);

		SQLResult is = executeQuery(dbLog, tdcIntegrationServerFormat);

		String status1 = is.getData(0, "STATUS");
		String run_id = is.getData(0, "RUN_ID");

		boolean valuesStatus = status1.equals(searchedStatus);// Valida si se encuentra en estatus R
		while (valuesStatus) {

			is = executeQuery(dbLog, tdcIntegrationServerFormat);

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

//*******************************************************Paso 3************************************************************************

		addStep("Validar que la interface haya finalizado en estatus E en la tabla WM_LOG_RUN.");
		System.out.println(GlobalVariables.DB_HOST_LOG);
		System.out.println(ValidaLog);
		String statusID = "";

		SQLResult ValidaStatus = executeQuery(dbLog, ValidaLog);

		boolean validaStatusRes = ValidaStatus.isEmpty();

		if (!validaStatusRes) {
			run_id = ValidaStatus.getData(0, "RUN_ID");
			System.out.println("RunID_Log: " + run_id);
			statusID = ValidaStatus.getData(0, "STATUS");
			System.out.println("Status_Log: " + statusID);
			testCase.addQueryEvidenceCurrentStep(ValidaStatus);

		} else if (validaStatusRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron resultados en el log \n" + ValidaLog);
			testCase.addQueryEvidenceCurrentStep(ValidaStatus);
		}

		System.out.println(validaStatusRes);

		assertFalse(validaStatusRes, "El estatus de la ejecucion no es E");

//**************************************************Paso 4 **************************************************************************

		addStep("Validar que se muestran los errores de la ejecucion");

		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);

		String error = String.format(consultaERROR, run_id);
		String error1 = String.format(consultaERROR2, run_id);
		String error2 = String.format(consultaERROR3, run_id);

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
		return "Se valida que al solicitar la  baja de buz�n en filesystema por medio servicio runCT1_BuzonDelete, "
				+ "al exceder los reintentos, no se elimine y se actualice estatus a X.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_004_CT1_NEXXO_EliminarDirectorioFileSystemReintentos";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

