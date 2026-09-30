package WM.interfaces.cp01;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

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

public class CP01_ValidaCambiosColCtrlM extends BaseExecution{
	@Test(dataProvider = "data-provider") 
	public void ATC_FT_003_CP01_ValidaCambiosColCtrlM(HashMap<String, String> data) throws Exception {
		
/* Utiler�as *********************************************************************/		
		
		SQLUtil dbLog = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA, GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		SQLUtil dbCNT = new SQLUtil(GlobalVariables.DB_HOST_FCIASQA, GlobalVariables.DB_USER_FCIASQA, GlobalVariables.DB_PASSWORD_FCIASQA);
		

	
/**
* Variables ******************************************************************************************
* 
*/
	    //Paso 1
		String ValidaPOS_CAMBIOSL = "SELECT PLAZA_CR, TIENDA_CR, WM_STATUS "
				+ "FROM WMUSER.POS_CAMBIOS  WHERE WM_STATUS = 'L' AND PLAZA_CR = '" + data.get("plaza") +"' AND TIENDA_CR = '" + data.get("tienda") +"'";
		
		//Paso 2
		String validaEmail = "SELECT EMAIL, A.GROUP_ID, GROUP_NAME, INTERFACE_NAME "
				+ "FROM WMLOG.WM_LOG_GROUP A, WMLOG.WM_LOG_USER_GROUP B, WMLOG.WM_LOG_USER C "
				+ "WHERE A.GROUP_ID = B.GROUP_ID "
				+ "AND B.USER_ID = C.USER_ID "
				+ "AND A.GROUP_NAME = 'CP01' "
				+ "AND A.INTERFACE_NAME = 'CP01' "
				+ "AND C.PLAZA IS NULL";
		//Paso 3
		// consultas de error
				String consultaError1 = " select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE "
						+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1"; // dbLog
				String consultaError2 = " select * from (select description,MESSAGE " + "from wmlog.WM_LOG_ERROR "
						+ "where RUN_ID='%s')WHERE rownum <= 1"; // dbLog
				String consultaError3 = " select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
						+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s')WHERE rownum <= 1"; // dbLog
			      
	   //Paso 4
				
		      String  tdcIntegrationServerFormat = "select * from (SELECT Tbl.run_id,interface, start_dt, end_dt, status, server, (END_DT - START_DT)*24*60 \r\n" + 
		      		"FROM WMLOG.WM_LOG_RUN Tbl WHERE INTERFACE = 'CP01' AND STATUS='S' " + 
		      		"ORDER BY START_DT DESC) where rownum <=1";
		      
	 //Paso 5
		      
		      String ValidaPOS_CAMBIOSE = "SELECT WM_RUN_ID, PLAZA_CR, TIENDA_CR, WM_STATUS, WM_SEND_DT \r\n" + 
		      		"FROM WMUSER.POS_CAMBIOS \r\n" + 
		      		"WHERE WM_STATUS = 'E' \r\n" + 
		      		"AND TRUNC(WM_SEND_DT) =  to_date('%s', 'dd/MM/yyyy') \r\n" + 
		      		"AND PLAZA_CR = '" + data.get("plaza") +"' \r\n" + 
		      		"AND TIENDA_CR = '" + data.get("tienda") +"' \r\n" + 
		      		"AND WM_RUN_ID = '%s'";
		
		      
		      
/**
* 	
* **********************************Pasos del caso de Prueba *****************************************
* 
*/
		
//		Paso 1	************************ 
		addStep("Que exista registro de la Plaza " + data.get("plaza") +" Y TIENDA  " + data.get("tienda") +" con STATUS L en la tabla POS_CAMBIOS de la BD CNT. ");
		System.out.println(GlobalVariables.DB_HOST_FCIASQA);
		System.out.println(ValidaPOS_CAMBIOSL);
		
		SQLResult ValidaPOS_CAMBIOSL_Res = executeQuery(dbCNT, ValidaPOS_CAMBIOSL);
		
		boolean validaStatusL = ValidaPOS_CAMBIOSL_Res.isEmpty();
		
			if (!validaStatusL) {
		
			testCase.addQueryEvidenceCurrentStep(ValidaPOS_CAMBIOSL_Res);
			
						} 
		
		System.out.println(validaStatusL);

		assertFalse(validaStatusL, "No se encontro registro con las tiendas correspondientes");
		assertTrue(validaStatusL);
//		Paso 2	************************ 
		
		addStep("Comprobar la direcci�n de email para recibir las natificaciones en las tablas WM_LOG_GROUP, WM_LOG_USER_GROUP y WM_LOG_USER para el GROUP_NAME igual a CP01 y INTERFACE_NAME igual a CP01 en la BD WMLOG.");
	
		System.out.println(validaEmail);
		
		SQLResult validaEmail_Res = executeQuery(dbLog, validaEmail);
		
		boolean validaEmailRes = validaEmail_Res.isEmpty();
		
			if (!validaEmailRes) {
		
			testCase.addQueryEvidenceCurrentStep(validaEmail_Res);
			
						} 
		
		System.out.println(validaEmailRes);

		assertFalse(validaEmailRes, "No se conoce la direcci�n de e-mail al cual llegar� el correo de notificaci�n");
		
//**********************************************Paso 3 ****************************************************************************************************************
		
		addStep("Se ejecuta el proceso CP01.Pub:run ");

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		
		JSONObject obj = new JSONObject(data.get("job"));

		testCase.addBoldTextEvidenceCurrentStep("Jobs en  Control M ");
		Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
		//testCase.addPaso("Paso con addPaso");
		testCase.addBoldTextEvidenceCurrentStep("Login");
		u.get(data.get("server"));
		u.hardWait(40);
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

		u.hardWait(30);
		
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
//*******************************************************Paso 4************************************************************************
		
		addStep("Se valida que el STATUS sea igual a S en la tabla WM_LOG_RUN  de la BD WM_LOG  donde INTERFACE = CP01.");
		
		System.out.println(tdcIntegrationServerFormat);
		
		SQLResult ValidaStatusS = executeQuery(dbLog, tdcIntegrationServerFormat);
		String run_id ="";
		
		boolean validaStatus = ValidaStatusS.isEmpty();
		
			if (!validaStatus) {
		
			testCase.addQueryEvidenceCurrentStep(ValidaStatusS);
			run_id = ValidaStatusS.getData(0, "RUN_ID");
						} 
		
		System.out.println(validaStatus);

		assertFalse(validaStatus, "Error en la ejecucion de la interfaz");
		
//**************************************************Paso 5 **************************************************************************
		
addStep("Se valida que WM_STATUS sea igual a E en la tabla POS_CAMBIOS de la BD CNT donde PLAZA_CR 10MON Y TIENDA_CR 50BPX");

          Date fecha = new Date();// obtener fecha del sistema
          
         SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // formato
																		// fecha
        String date = formatter.format(fecha);     

        String formatoFecha = date.substring(0, 10);
        
        System.out.println(formatoFecha);
        
        

         String ValidaPOS_CAMBIOSEFormat = String.format(ValidaPOS_CAMBIOSE, formatoFecha,run_id);
		
		System.out.println(ValidaPOS_CAMBIOSEFormat);
		
		
		
		SQLResult ValidaPOS_CAMBIOSERes = executeQuery(dbCNT,ValidaPOS_CAMBIOSEFormat);
		
		boolean validaCambiosE = ValidaPOS_CAMBIOSERes.isEmpty();
		
			if (!validaCambiosE) {
		
			testCase.addQueryEvidenceCurrentStep(ValidaPOS_CAMBIOSERes);
			
						} 
		
		System.out.println(validaCambiosE);

		assertFalse(validaCambiosE, "Los campos de wm_status, wm_send_dt y wm_run_id no  fueron actualizados.");
				
		
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
		return "Validar que se env�en los cambios al Buzon de la Tienda";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATION";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_003_CP01_ValidaCambiosColCtrlM";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
