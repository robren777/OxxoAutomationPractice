package WM.interfaces.ps1;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.json.JSONObject;
import org.testng.annotations.Test;

import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class PS1_CatalogoPromociones extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_PS1_001_Valida_deposito_de_info_en_buzon(HashMap<String, String> data) throws Exception {

		/*
		 * Utilerias
		 ***********************************************************************************************/

		utils.sql.SQLUtil dbLOG = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA,
				GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMQA_NUEVA,
				GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		SQLUtil dbCNT = new SQLUtil(GlobalVariables.DB_HOST_FCIASQA, GlobalVariables.DB_USER_FCIASQA,
				GlobalVariables.DB_PASSWORD_FCIASQA);

		/**
		 * Variables
		 * ***********************************************************************************************
		 * 
		 * 
		 */

		String plazaActiva = "SELECT id, cr_plaza, fch_arranque,sinergia, cedis, pais \r\n "
				+ "FROM POSUSER.PLAZAS \r\n"
				+ "WHERE SINERGIA = 'Y' \r\n "
				+ "AND CR_PLAZA = '" + data.get("plaza") + "'";

		String infoProcesar = "SELECT cr_plaza, emission_id, emission_start_date, emission_end_date, promotion_id \r\n "
				+ "FROM xxpemp.XXSE_POS_SUMMARY_V \r\n"
				+ "WHERE CR_PLAZA='" + data.get("plaza") + "'";

		String tdcIntegrationServerFormat = "SELECT run_id,interface, TO_CHAR(start_dt, 'YY-MM-DD HH24:MI:SS') start_dt, TO_CHAR(end_dt, 'YY-MM-DD HH24:MI:SS')end_dt, status, server\r\n"
				+ "FROM WMLOG.WM_LOG_RUN\r\n"
				+ "WHERE INTERFACE = 'PS1'\r\n"
				+ "and start_dt >= TRUNC(SYSDATE)\r\n"
				//+ "and start_dt  BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n"
				//+ "and TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n"
				+ "and run_id = '%s'\r\n"
				+ "ORDER BY START_DT DESC";// Consulta para estatus de la ejecucion
		
		String consultaStatusR = "SELECT run_id,interface, TO_CHAR(start_dt, 'YY-MM-DD HH24:MI:SS') start_dt, TO_CHAR(end_dt, 'YY-MM-DD HH24:MI:SS')end_dt, status, server\r\n"
				+ "FROM WMLOG.WM_LOG_RUN\r\n"
				+ "WHERE INTERFACE = 'PS1'\r\n"
				+ "and start_dt >= TRUNC(SYSDATE)\r\n"
				+ "ORDER BY START_DT DESC";
		
		
		String consultaError = " select ERROR_ID, description,MESSAGE \r\n" +
			 "from wmlog.WM_LOG_ERROR \r\n" +
			 "where RUN_ID='%s'";
		
		//Obtener hora
				Date fechaInicio ;
				Date fechaFin;
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
				formatter.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));

						
				String statusEsperado = "S";
				String statusR = "R";
				String status = "";
				String run_id = "";
		
		
		/**
		 * 
		 * **********************************Pasos del caso de Prueba
		 * *****************************************
		 * 
		 * 
		 */
				
// se obtiene la hora inicial**********************************************************************************
		fechaInicio = new Date();
							
		String fechaInicial = formatter.format(fechaInicio);
							
		System.out.println(fechaInicial);
				
		Thread.sleep(20000);
				

//**********************Paso 1**********************************************************************************

		addStep("Tener activa la plaza " + data.get("plaza") + " en la tabla PLAZAS de POSUSER. ");
		System.out.println(GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		System.out.println(plazaActiva);

		SQLResult plazaActivaRes = executeQuery(dbPos, plazaActiva);

		boolean validaplazaActiva = plazaActivaRes.isEmpty();

		if (!validaplazaActiva) {

			testCase.addQueryEvidenceCurrentStep(plazaActivaRes);

		}

		System.out.println(validaplazaActiva);

		assertFalse(validaplazaActiva, "No se encontro la plaza  activa en POSUSER.");

//                Paso 2   ************************

		addStep("Tener informacion para procesar en la vista: XXSE_POS_SUMMARY_V de SINERGIA.");
		System.out.println(GlobalVariables.DB_USER_FCIASQA);
		System.out.println(infoProcesar);

		SQLResult infoProcesarRes = executeQuery(dbCNT, infoProcesar);

		boolean validainfoProcesar = infoProcesarRes.isEmpty();

		if (!validainfoProcesar) {

			testCase.addQueryEvidenceCurrentStep(infoProcesarRes);

		}

		System.out.println(validainfoProcesar);

		assertFalse(validainfoProcesar, "No Hay informacion para la plaza " + data.get("plaza") + " en SINERGIA ");

//             Paso 3 *********************************************
	/*	addStep("Ejecutar la interfaz: PS1.pub:run. ");

		// Utileria

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PakageManagment pok = new PakageManagment(u, testCase);
	

		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");

		

		System.out.println(GlobalVariables.DB_HOST_LOG);
		String contra = "http://" + user + ":" + ps + "@" + server + ":5555";
		u.get(contra);

		String dateExecution = pok.runIntefaceWM2(data.get("interface"), data.get("servicio"), null);
		System.out.println("Respuesta dateExecution" + dateExecution);

		SQLResult is = executeQuery(dbLOG, consultaStatusR);

		status = is.getData(0, "STATUS");
		run_id = is.getData(0, "RUN_ID");
		System.out.println("RUN_ID: " + run_id);

		boolean valuesStatus = status.equals(statusR);// Valida si se encuentra en estatus R
		while (valuesStatus) {
			
			is = executeQuery(dbLOG, consultaStatusR);

			status = is.getData(0, "STATUS");
			run_id = is.getData(0, "RUN_ID");
			valuesStatus = status.equals(statusR);

			u.hardWait(2);

		}
		
		Thread.sleep(20000);*/
		
		//Paso para DEMO
		
 addStep("Ir a Jobs de la carpeta TQA_BAJO_DEMANDA - Ejecutar JOB ProcAclaraciones de Ctrl-M ");
		
		testCase.addTextEvidenceCurrentStep("Ejecucion Job: TQA_BAJO_DEMANDA" );
		
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		
		JSONObject obj = new JSONObject(data.get("job"));

		testCase.addBoldTextEvidenceCurrentStep("Jobs en  Control M ");
		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		//testCase.addPaso("Paso con addPaso");
		testCase.addBoldTextEvidenceCurrentStep("Login");
		u.get(data.get("server"));
		u.hardWait(50);
		u.waitForLoadPage();
		CM.logOn(); 

		//Buscar del job
		testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");
		ControlM control = new ControlM(u, testCase, obj);
		boolean flag = control.searchJob();
		assertTrue(flag);
		
		//Ejecucion
		String resultado = control.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado);

		u.hardWait(40);
		
		//Valor del output 
		String Res2 = control.getNewStatus();
		System.out.println ("Valor de output getNewStatus:" +Res2);
		
		
		String output = control.getOutput();
		System.out.println ("Valor de output control:" +output);
		
		testCase.addTextEvidenceCurrentStep("Status de ejecucion: "+Res2);
		testCase.addTextEvidenceCurrentStep("Output de ejecucion: "+output);
		//Validacion del caso
		Boolean casoPasado = false;
		
		if(Res2.equals("Ended OK")) {
		casoPasado = true;
		}		
		
		control.closeViewpoint();
		u.close();
		assertTrue(casoPasado);
		//assertNotEquals("Failure",resultado);
	
		
//Se obtiene la hora final *****************************************************************
		
		fechaFin = new Date();
				
		String fechaFinal = formatter.format(fechaFin);
				
		System.out.println(fechaFinal);

//********************************Paso 6 ****************************************************************
		
	addStep("Verificar que se muestre error en la ejecucion de la interfaz  dentro de la tabla WM_LOG_ERROR ya que no hay registros a procesar");
	
		
	boolean validaConsultaError_f = true;
	
	String queryFormat = String.format(tdcIntegrationServerFormat, run_id );
		
	SQLResult validaLogs = executeQuery(dbLOG, queryFormat);
		
	System.out.println(queryFormat);	
	
	testCase.addQueryEvidenceCurrentStep(validaLogs);
	
	boolean validateStatus = status.equals(statusEsperado); //Valida si el status es S, sino verifica en la tabla de errores
		
	System.out.println("Status es S = "+ validateStatus );				

	
	if(!validateStatus) { //en caso de haber errores, los consulta
		

		String consultaError_f = String.format(consultaError, run_id);
				
		System.out.println(consultaError_f);
				
		SQLResult consultaError_R = dbLOG.executeQuery(consultaError_f);

		validaConsultaError_f = consultaError_R.isEmpty();
				
		if (validaConsultaError_f) {
			
			testCase.addBoldTextEvidenceCurrentStep("No se registraron errores en la ejecucion");		
			testCase.addQueryEvidenceCurrentStep(consultaError_R);
		} else {
			
			testCase.addQueryEvidenceCurrentStep(consultaError_R);
		}
			
		System.out.println(validaConsultaError_f);
	}
		
				
		assertFalse(validateStatus, "La ejecucion de la interfaz fue exitosa");
		

	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_PS1_001_Valida_deposito_de_info_en_buzon";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminado. Validar que se deposite correctamente la informacion en el buzon de la plaza correspondiente ";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION OXXO";
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
