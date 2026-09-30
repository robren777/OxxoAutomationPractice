package WM.interfaces.pb1;

import static org.junit.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;


public class ATC_FT_003_NEO_Pb1_ValidarInfoTickets extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_NEO_Pb1_ValidarInfoTickets_test(HashMap<String, String> data) throws Exception {
		
		
/** UTILERIA *********************************************************************/	
		
		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA, GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMQA_NUEVA, GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		utils.sql.SQLUtil dbIas = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_IAS, GlobalVariables.DB_USER_IAS, GlobalVariables.DB_PASSWORD_IAS);
		utils.sql.SQLUtil dbBi = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_BI, GlobalVariables.DB_USER_BI,GlobalVariables.DB_PASSWORD_BI);

		SeleniumUtil u;
		PakageManagment pok;
		
		String status = "S"; // status exitoso
		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
		String searchedStatus = "R";
		
				
/** VARIABLES *********************************************************************/	
		
		
		String tdcIntegrationServerFormat = "select * from ( SELECT run_id,start_dt,status FROM WMLOG.WM_LOG_RUN Tbl WHERE INTERFACE LIKE '%PB1main%' ORDER BY START_DT DESC ) where rownum <= 5";

		String consultaError1 = "Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE "
				+ "from  wmlog.WM_LOG_ERROR " + "where RUN_ID='%s'";

		String consultaError2 = " select * from (select description,MESSAGE "
				+ "from wmlog.WM_LOG_ERROR where RUN_ID='%s')WHERE rownum <= 1";

		String consultaError3 = " select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
				+ "from wmlog.WM_LOG_ERROR where RUN_ID='%s')WHERE rownum <= 1";
		
	
		String selectTempTicket= "SELECT VT_NUM_CAJA, VT_CVE_ART, VT_CVE_MOV"
				+ " FROM TEMP_TICKET "
				+ "WHERE TRUNC(VT_FCH_MOV) = TRUNC(SYSDATE) "
				+ "AND VT_CR_PLAZ = '" + data.get("plaza") + "'";
		
		String noDatosBI = "SELECT VT_NUM_CAJA, VT_CVE_ART, VT_CVE_MOV \r\n"
				+ "FROM DWADMIN.TEMP_TICKET \r\n"
				+ "WHERE TRUNC(VT_FCH_MOV) = TRUNC(SYSDATE)\r\n"
				+ "AND VT_CR_PLAZ = '" + data.get("plaza") + "'";


/** PASOS DEL CASO DE PRUEBA *********************************************************************/	
			
		
		
		/* PASO 2 *********************************************************************/	

		addStep("Se valida que No existan datos en la tabla TEMP_TICKET de la BD BI");

		System.out.println(noDatosBI);
		SQLResult noDatosBI_result = dbBi.executeQuery(noDatosBI);		
		//String VT_NUM_CAJA = noDatosBI_result.getData(0, "VT_NUM_CAJA");

		boolean noDatosBI_valida = noDatosBI_result.isEmpty(); // checa que el string contenga datos

		if (noDatosBI_valida) {
			
			testCase.addQueryEvidenceCurrentStep(noDatosBI_result); // Si no esta vacio, lo agrega a la evidencia
		}

		System.out.println(noDatosBI_valida);
		assertTrue(noDatosBI_valida, "Se encontraron registros en BI"); // Si esta vacio, imprime mensaje
			
		
		/* PASO 3 *********************************************************************/	

		addStep("Invocar el servicio FEMSA_PB1.Pub:run mediante la ejecuci�n del JOB PB1.");

		u = new SeleniumUtil(new ChromeTest(), true);
		pok = new PakageManagment(u, testCase);
		
		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
		String contra = "http://" + user + ":" + ps + "@" + server + ":5555";
		u.get(contra);

		String dateExecution = pok.runIntefaceWmOneButton10(data.get("interfase"), data.get("servicio"));		
		System.out.println("Respuesta dateExecution " + dateExecution);
		System.out.println(tdcIntegrationServerFormat);
		SQLResult is = dbLog.executeQuery(tdcIntegrationServerFormat);
		String run_id = is.getData(0, "RUN_ID");
		String status1 = is.getData(0, "STATUS");
		System.out.println("RUN_ID = " + run_id + "\t Status: " + status1 );

		boolean valuesStatus = status1.equals(searchedStatus);// Valida si se encuentra en estatus R

		while (valuesStatus) {			
			status1 = is.getData(0, "STATUS");
			run_id = is.getData(0, "RUN_ID");
			valuesStatus = status1.equals(searchedStatus);
			u.hardWait(2);			
		}

		boolean successRun = status1.equals(status);// Valida si se encuentra en estatus S		

		if (!successRun) {
			String error = String.format(consultaError1, run_id);
			String error1 = String.format(consultaError2, run_id);
			String error2 = String.format(consultaError3, run_id);
	
			SQLResult errorr = dbLog.executeQuery(error);
			boolean emptyError = errorr.isEmpty();
			
			if (!emptyError) {
				testCase.addTextEvidenceCurrentStep("Se encontr� un error en la ejecuci�n de la interfaz en la tabla WM_LOG_ERROR");
				testCase.addQueryEvidenceCurrentStep(errorr);
				
				SQLResult errorIS = dbLog.executeQuery(error1);
				testCase.addQueryEvidenceCurrentStep(errorIS);
				
				SQLResult errorIS2 = dbLog.executeQuery(error2);
				testCase.addQueryEvidenceCurrentStep(errorIS2);
			}
		}	
		
		
				
		
		// PASO 7 *********************************************************************	

		addStep("Se valida que los datos se hayan insertado en la tabla TEMP_TICKET de la BD BI");
		System.out.println(selectTempTicket);
		SQLResult paso6_Result = dbIas.executeQuery(selectTempTicket);	

		boolean paso6_valida = paso6_Result.isEmpty(); // checa que el string contenga datos

		if (!paso6_valida) {
			testCase.addQueryEvidenceCurrentStep(paso6_Result); // Si no esta vacio, lo agrega a la evidencia
		}

		System.out.println(paso6_valida);
		assertFalse("No se encontraron registros ", paso6_valida); // Si esta vacio, imprime mensaje	

	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Esta interface permite consultar y procesar la informaci�n de los tickets (TIC) de las tiendas almacenados en el sistema del POS, y enviarla al sistema de BI.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_NEO_Pb1_ValidarInfoTickets_test";
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