package WM.interfaces.pr26;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_010_VerificaQueNoSeGenereMensajeErrorsinDatosAProcesarPRM extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_009_VerificaQueNoSeGenereMensajeErrorsinDatosAProcesarPRM_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utilerias
		 *********************************************************************/
		
		/**
		 * Proyecto multiformato:
		 * CP:Verificar que se genere mensaje de error si ya no hay datos a procesar PRM
		 */
		
		utils.sql.SQLUtil dbLOG = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA,GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		utils.sql.SQLUtil dbRms = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX,GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		

	
		String POS_ITM_PRM_HEAD_inicial = "select batch_id,location,archivo, wm_prm_status,archivo,LOAD_BATCH_ID\r\n"
				+ "from WMUSER.POS_PRM_HEAD\r\n"
				+ "where WM_PRM_STATUS = 'L'\r\n"
				+ "and rownum <=1\r\n"
				+ "order by load_date desc";
			
		
		String tdcIntegrationServerFormat = "SELECT run_id,interface, TO_CHAR(start_dt, 'YY-MM-DD HH24:MI:SS') start_dt, TO_CHAR(end_dt, 'YY-MM-DD HH24:MI:SS')end_dt, status, server\r\n"
				+ "FROM WMLOG.WM_LOG_RUN\r\n"
				+ "WHERE INTERFACE = 'PR26_PRM'\r\n"
				+ "and start_dt >= TRUNC(SYSDATE)\r\n"
				//+ "and start_dt  BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n"
				//+ "and TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n"
				+ "and run_id = '%s'\r\n"
				+ "ORDER BY START_DT DESC";// Consulta para estatus de la ejecucion
		
		String consultaStatusR = "SELECT run_id,interface, TO_CHAR(start_dt, 'YY-MM-DD HH24:MI:SS') start_dt, TO_CHAR(end_dt, 'YY-MM-DD HH24:MI:SS')end_dt, status, server\r\n"
				+ "FROM WMLOG.WM_LOG_RUN\r\n"
				+ "WHERE INTERFACE = 'PR26_PRM'\r\n"
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
		
		testCase.setProject_Name("Proyecto Multiformato");
		
// se obtiene la hora inicial*****************************************************************************************
		fechaInicio = new Date();
					
		String fechaInicial = formatter.format(fechaInicio);
					
		System.out.println(fechaInicial);
		
		Thread.sleep(20000);

//*************************** Paso 1 *********************************************************************************
		
		addStep("Verificamos que ya no exista datos en la tabla POS_ITM_PRM_HEAD con la columna WM_ITM_STATUS o WM_PRM_STATUS en \"L\".");
				
		System.out.println(POS_ITM_PRM_HEAD_inicial);
		SQLResult ITM1 = dbRms.executeQuery(POS_ITM_PRM_HEAD_inicial);
		
		String id = "";
		String loc= "";
		
		boolean  paso1 = ITM1.isEmpty();
		if (!paso1) {

			id = ITM1.getData(0, "LOAD_BATCH_ID");
			System.out.println("LOAD_BARCH_ID " + id);
			
			loc = ITM1.getData(0, "LOCATION");
			System.out.println("LOCATION: " + loc);
			
			
		}
		testCase.addQueryEvidenceCurrentStep(ITM1);
		assertTrue("Se  muestra la informacion pendiente por procesar para la tienda ", paso1);

	

//*********************** Paso 3 *********************************************************************************
		
		addStep("Ejecutar el servicio "+ data.get("servicio"));
	
		// utileria
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PakageManagment pok = new PakageManagment(u, testCase);
		u.hardWait(2);
		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
		
		
		String contra = "http://" + user + ":" + ps + "@" + server + ":5555";
		u.get(contra);
	
		String dateExecution = pok.runIntefaceWmOneButton10(data.get("interfase"), data.get("servicio"));
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
		
		Thread.sleep(20000);
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

	
	if(!validateStatus) {
		

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
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar se genere mensaje de error si ya no hay datos a procesar - PRM";
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
