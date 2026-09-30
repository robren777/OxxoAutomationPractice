package WM.interfaces.OEIMX;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class OEIMX_GenerarFacturaIngresos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_OEIMX_GenerarFacturaIngresos(HashMap<String, String> data) throws Exception {
		/*
		 * Utiler�as
		 *********************************************************************/
	utils.sql.SQLUtil dbAvebqa = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,GlobalVariables.DB_PASSWORD_AVEBQA);
		
		
		utils.sql.SQLUtil dbLOG = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA,
				GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		

		// Paso 2 (Segun la Matriz)
		String ValidInfo = "SELECT ID_FACTURA_DIGITAL,REFID,SERIE,FOLIO,TIPO_COMPROBANTE,WM_STATUS,WM_RUN_ID,VERSION_CFDI "
				+"FROM XXFC.XXFC_CFD_FACTURA_DIGITAL "
				+"WHERE VERSION_CFDI ='4.0'"
				+"AND WM_STATUS ='L' "
				+ "AND ORIGEN IN ('IMX','IMXAR') "
				+ "AND TIPO_COMPROBANTE ='%s'";

		// Paso 3
		String tdcIntegrationServerFormat = "select * from (SELECT Tbl.run_id,interface, start_dt, end_dt, status, server, (END_DT - START_DT)*24*60 "
				+ "FROM WMLOG.WM_LOG_RUN Tbl " + "WHERE INTERFACE = 'OEIMX' "
				+ "ORDER BY START_DT DESC) where rownum <=1";// Consulta para estatus de la ejecucion
		
		String consultaERROR = " select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE "
				+ "from  wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1";// Consulta para los errores
		
		String consultaERROR2 = " select * from (select description,MESSAGE " + "from wmlog.WM_LOG_ERROR "
				+ "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
		
		String consultaERROR3 = " select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
				+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
		
		// Paso 4
		String ValidLog = "SELECT run_id,interface, start_dt, end_dt, status, server  " + "FROM WMLOG.wm_log_run  "
				+ "WHERE interface = 'OEIMX' " + "AND status = 'E' "
				+ "AND trunc(start_dt) >= trunc(SYSDATE) ORDER BY 1 DESC";
		
		// Paso 5
		String consultaTHREAD = "SELECT THREAD_ID, PARENT_ID, NAME,START_DT, END_DT, STATUS  "
				+ "FROM WMLOG.wm_log_thread " + "WHERE parent_id = '%s'";
		
		// Paso 6
		String ValidRecords = "SELECT VERSION_CFDI,WM_RUN_ID, REFID,SERIE,FOLIO,UUID,FECHA_TIMBRE,WM_STATUS "
				+ "FROM XXFC.XXFC_CFD_FACTURA_DIGITAL "
				+ "WHERE VERSION_CFDI ='4.0' "
				+ "AND WM_STATUS ='E' "
				+ "AND WM_RUN_ID = '%s'";
		

//*******************************Paso 2 ********************************************************************************************	

		addStep("Validar si hay registros a procesar por la interfaz");

		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		String comprobante = data.get("comprobantei"); //comprobante i o p
		String ValidInfoI = String.format(ValidInfo,comprobante);
		System.out.println(ValidInfoI);
		SQLResult ValidInfoExec = dbAvebqa.executeQuery(ValidInfoI);
		String ID_FACTURA_DIGITAL = "";

		boolean ValidInfoRes = ValidInfoExec.isEmpty();
		if (!ValidInfoRes) {
			ID_FACTURA_DIGITAL = ValidInfoExec.getData(0, "ID_FACTURA_DIGITAL");
			System.out.println("ID_FACTURA_DIGITAL: " + ID_FACTURA_DIGITAL);
			testCase.addQueryEvidenceCurrentStep(ValidInfoExec);
		} else if (ValidInfoRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta /n" + ValidInfo);
			testCase.addQueryEvidenceCurrentStep(ValidInfoExec);
		}

		System.out.println(ValidInfoRes);
		assertFalse("No se muestra la informaci�n en la consulta", ValidInfoRes);

//****************************Paso 3 ***********************************************************************************************
		addStep("Ejecutamos interfaz desde el servidor .15"); 

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

		SQLResult is = executeQuery(dbLOG, tdcIntegrationServerFormat);

		String status1 = is.getData(0, "STATUS");
		String run_id = is.getData(0, "RUN_ID");

		boolean valuesStatus = status1.equals(searchedStatus);// Valida si se encuentra en estatus R
		while (valuesStatus) {

			is = executeQuery(dbLOG, tdcIntegrationServerFormat);

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

			SQLResult errorr = dbLOG.executeQuery(error);
			boolean emptyError = errorr.isEmpty();

			if (!emptyError) {

				testCase.addTextEvidenceCurrentStep(
						"Se encontro un error en la ejecucion de la interfaz en la tabla WM_LOG_ERROR");

				testCase.addQueryEvidenceCurrentStep(errorr);

			}

			SQLResult errorIS = dbLOG.executeQuery(error1);
			boolean emptyError1 = errorIS.isEmpty();
			if (!emptyError1) {
				testCase.addQueryEvidenceCurrentStep(errorIS);
			}

			SQLResult errorIS2 = dbLOG.executeQuery(error2);
			boolean emptyError2 = errorIS2.isEmpty();

			if (!emptyError2) {

				testCase.addQueryEvidenceCurrentStep(errorIS2);

			}

		} 
		
//****************************Paso 4 ***********************************************************************************************

		addStep("Validar que la interfaz se ejecut� correctamente en WMLOG.");
		System.out.println(GlobalVariables.DB_HOST_LOG);
		System.out.println(ValidLog);
		SQLResult ValidLogExec = executeQuery(dbLOG, ValidLog);
		String StatusLog = "";

		boolean validateStatus = ValidLogExec.isEmpty();

		if (!validateStatus) {
			StatusLog = ValidLogExec.getData(0, "STATUS");
			run_id = ValidLogExec.getData(0, "run_id");
			testCase.addQueryEvidenceCurrentStep(ValidLogExec);
		} else if (validateStatus) {
			testCase.addTextEvidenceCurrentStep("No se encontraron registros con el status esperados /n" + ValidLog);
			testCase.addQueryEvidenceCurrentStep(ValidInfoExec);
		}
		System.out.println(validateStatus);
		assertFalse("La ejecucion de la interfaz no fue exitosa", validateStatus);
		
//************************************Paso 5 ***********************************************************
		addStep("Validar que el registro de ejecuci�n  en la tabla WM_LOG_THREAD.");
		System.out.println(GlobalVariables.DB_HOST_LOG);
		String consultaTH = String.format(consultaTHREAD, run_id);
		System.out.println(consultaTH);
		SQLResult THRE = dbLOG.executeQuery(consultaTH);
		String THREAD_ID = "";

		boolean paso1TH = THRE.isEmpty();

		if (!paso1TH) {
			THREAD_ID = THRE.getData(0, "THREAD_ID");
			System.out.println("THREAD_ID: " + THREAD_ID);
			testCase.addQueryEvidenceCurrentStep(THRE);
		} else if (paso1TH) {
			testCase.addTextEvidenceCurrentStep("No se encontraron registros en thread /n" + consultaTH);
			testCase.addQueryEvidenceCurrentStep(THRE);
		}

		System.out.println(paso1TH);
		assertFalse("No se encontro la ejecuci�n en thread", paso1TH);

//**************************************Paso 6 *************************************************************
		addStep("Validar registros procesados");
		System.out.println(GlobalVariables.DB_HOST_AVEBQA);
		
		String ValidRecordsRun = String.format(ValidRecords, run_id);
		System.out.println(ValidRecordsRun);
		SQLResult ValidInfoRecords = dbAvebqa.executeQuery(ValidRecordsRun);
		String RECORDS = "";

		boolean ValidRecordsRes = ValidInfoRecords.isEmpty();
		if (!ValidRecordsRes) {
			RECORDS = ValidInfoRecords.getData(0, "REFID");
			System.out.println("REFID: " + RECORDS);
			testCase.addQueryEvidenceCurrentStep(ValidInfoRecords);
		} else if (ValidRecordsRes) {
			testCase.addTextEvidenceCurrentStep("No se encontraron datos en la consulta /n" + ValidInfo);
			testCase.addQueryEvidenceCurrentStep(ValidInfoRecords);
		}

		System.out.println(ValidInfoRes);
		assertFalse("No se muestra la informaci�n en la consulta", ValidInfoRes);
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_OEIMX_GenerarFacturaIngresos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Construido. Generar factura de ingresos. Env�o de informaci�n para generaci�n y timbrado de factura de ingresos";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMTIZACION";
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
		return null;
	}
}