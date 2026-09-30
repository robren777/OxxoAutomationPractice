package WM.interfaces.ct1;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.apache.commons.net.ftp.FTPClient;
import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class CT1_NEXXO_AltaDirectorioFileSystem extends BaseExecution{

	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_CT1_NEXXO_AltaDirectorioFileSystem(HashMap<String, String> data) throws Exception {

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
		String PerfilBuzon = "select ID,CR_PLAZA,CR_TIENDA,WM_STATUS,CREATION_DATE,WM_RUN_ID "
				+ "from WMUSER.WM_BUZONES_T_TIENDAS " + "WHERE WM_STATUS_BUZON='L' " + "and CR_PLAZA= '" + data.get("plaza")
				+ "' " + "and CR_TIENDA= '" + data.get("tienda") + "' ";

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
				+ "FROM WMLOG.WM_LOG_RUN " + "WHERE INTERFACE LIKE '%CT1%' " + "AND STATUS = 'S' "
				+ " ORDER BY START_DT DESC) where rownum <=1";
//Paso 4		
		String consultaTHREAD = "select * from (SELECT THREAD_ID, PARENT_ID, NAME,START_DT, END_DT, STATUS  "
				+ "FROM wmlog.WM_LOG_THREAD" + " where PARENT_ID= '%s' ) where rownum <=1";

//	Paso 5	      
		String BuzonCread = "select ID,CR_PLAZA,CR_TIENDA,WM_STATUS,CREATION_DATE,WM_RUN_ID "
				+ "from WMUSER.WM_BUZONES_T_TIENDAS " + "WHERE WM_STATUS_BUZON='E' " + "and CR_PLAZA= '" + data.get("plaza")
				+ "' " + "and CR_TIENDA= '" + data.get("tienda") + "' ";

		/**
		 * 
		 * **********************************Pasos del caso de Prueba
		 * *****************************************
		 * 
		 */

//********************************************Paso 1*****************************************************************************

		addStep("Verificar que existan registros de perfiles a crear con WM_STATUS_BUZON = 'L'");

		System.out.println(GlobalVariables.DB_HOST_FCIASQA);
		System.out.println(PerfilBuzon);

		SQLResult ValidRegistro_Res = executeQuery(dbCNT, PerfilBuzon);

		boolean validRegistro = ValidRegistro_Res.isEmpty();

		if (!validRegistro) {

			testCase.addQueryEvidenceCurrentStep(ValidRegistro_Res);

		} else if (validRegistro) {
			testCase.addTextEvidenceCurrentStep("No se encontraron resultados en la busqueda \n" + PerfilBuzon);
			testCase.addQueryEvidenceCurrentStep(ValidRegistro_Res);
		}

		System.out.println(validRegistro);

		assertFalse(validRegistro, "No se encontro registro de la plaza y tienda");

//**********************************************Paso 2	****************************************************************************** 

		addStep("Ejecutar el JOB  para invocar la interface por medio del servicio CT1.Pub:runBuzon ");

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

		addStep("Validar que la interface haya finalizado correctamente en la tabla WM_LOG_RUN.");
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

		assertFalse(validaStatusRes, "El estatus de la ejecucion no es S");

//**************************************************Paso 4 **************************************************************************

		addStep("Validar que el registro de ejecuci�n  en la tabla WM_LOG_THREAD.");

		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
		String consultaTH = String.format(consultaTHREAD, run_id);
		System.out.println(consultaTH);
		String statusWmLog = "S";
		SQLResult THRE = dbLog.executeQuery(consultaTH);
		String THREAD_ID = "";
		String StatusThread = "";

		boolean validathread = THRE.isEmpty();
		boolean validateStatusThr = false;

		if (!validathread) {
			THREAD_ID = THRE.getData(0, "THREAD_ID");
			System.out.println("THREAD_ID: " + THREAD_ID);
			StatusThread = THRE.getData(0, "STATUS");
			System.out.println("Status_thread: " + StatusThread);

			validateStatusThr = statusWmLog.equals(StatusThread);
		}

		if (validateStatusThr) {
			testCase.addQueryEvidenceCurrentStep(THRE);
		} else if (!validateStatusThr) {
			testCase.addQueryEvidenceCurrentStep(THRE);
			testCase.addTextEvidenceCurrentStep(
					"El estatus del thread es diferente a S o no se encontro informacion en WM_LOG_THREAD ");
		}

//**************************************************Paso 5 ***************************************************************************		
		addStep("Verificar que Se muestran registros de perfiles creados (WM_STATUS_BUZON= 'E').");
		
		System.out.println(GlobalVariables.DB_HOST_FCIASQA);
		System.out.println(BuzonCread);

		SQLResult ValidElim_Res = executeQuery(dbCNT, BuzonCread);

		boolean validRegistroElim = ValidElim_Res.isEmpty();

		if (!validRegistroElim) {

			testCase.addQueryEvidenceCurrentStep(ValidElim_Res);

		} else if (validRegistroElim) {
			testCase.addTextEvidenceCurrentStep("No se encontraron resultados en la busqueda \n" + BuzonCread);
			testCase.addQueryEvidenceCurrentStep(ValidElim_Res);
		}

		System.out.println(validRegistroElim);

		assertFalse(validRegistroElim, "No se muestran registros de perfiles eliminados (WM_STATUS= 'E')");
		
//		****************************************Paso 6*****************************************************
		/*
		 * Verificar que el directorio  existe en el filesystem
		 * Ruta: /u01/posuser/FEMSA_OXXO/POS/CR_PLAZA/CR_TIENDA
		
		 */
		addStep("Verificar que el directorio no existe en el filesystem ");

		boolean Res = true;
		FTPClient ftpClient = null;
		(ftpClient = new FTPClient()).connect("10.182.92.13", 21);

		ftpClient.login("posuser", "posuser");

		String Ruta = "/u01/posuser/FEMSA_OXXO/POS/" + data.get("plaza") + "/" + data.get("tienda")+"/working";

//********************VALIDA ruta /u01/posuser/FEMSA_OXXO/POS/plaza/tinda******************************************************

// Si retorna 250 la carpeta existe, si retorna 550 El folder no existe

		if (ftpClient.cwd(Ruta) == 550) {

			System.out.println("La ruta " + Ruta + " no existe");
			testCase.addTextEvidenceCurrentStep("La ruta " + Ruta + " no existe");
			Res =true;

		} else if (ftpClient.cwd(Ruta) == 250) {

			System.out.println("La ruta " + Ruta + " existe");
			testCase.addTextEvidenceCurrentStep("La ruta " + Ruta + " existe");
			Res =false;

		}
		assertFalse(Res, "La ruta " + Ruta + " No existe");

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
		return "Se valida el alta/creaci�n de perfiles en TN,  por medio del servicio runCT1_TN.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_006_CT1_NEXXO_CreacionPerfilesTienda";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}

