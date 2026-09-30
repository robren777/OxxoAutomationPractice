package WM.interfaces.ro3;

import static org.testng.Assert.assertFalse;
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
import utils.sql.SQLUtil;

public class ATC_FT_002_RO3_VerificarProcesamientoDeTransferencias extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_RO3_VerificarProcesamientoDeTransferencias_test(HashMap<String, String> data) throws Exception {
		
/* Utiler�as *********************************************************************/
		
		SQLUtil dbRms = new SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
		SQLUtil dbLog = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA, GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		SQLUtil dbEbs = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,GlobalVariables.DB_PASSWORD_AVEBQA);		
	
				
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(),true);
		PakageManagment pok = new PakageManagment(u, testCase);
		
		String status = "S";
		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword( data.get("ps"));
		String server = data.get("server");
		String searchedStatus = "R";
		String run_id ;
			
/**
* Variables ******************************************************************************************
* 
* 
*/
		
		String tdcQueryFem1 = "SELECT CR_PLAZA, REFERENCE_2, REFERENCE_7, REFERENCE_8" 
				+ " FROM fem_fif_stg"
//				+ " WHERE cr_plaza = '10AGC'"
				+ " WHERE reference_2 IS NULL" 
				+ " AND reference_7 = -1"
				+ " AND reference_8 IS NULL"
				+ " AND ((from_loc_type = 'W' AND to_loc_type = 'W' AND tran_code = 30)"
				+ " OR (from_loc_type = 'W' AND to_loc_type = 'S' AND tran_code = 30)"
				+ " OR (from_loc_type = 'S' AND to_loc_type = 'W' AND tran_code = 32))"
				+ " AND tran_date >= TRUNC(SYSDATE)";
		
		String tdcQueryFem2 = "SELECT FROM_LOC_TYPE, TO_LOC_TYPE, TRAN_CODE, TRAN_DATE" 
				+ " FROM fem_fif_stg"
//				+ " WHERE cr_plaza = '10AGC'"
				+ " WHERE reference_2 IS NULL" 
				+ " AND reference_7 = -1"
				+ " AND reference_8 IS NULL"
				+ " AND ((from_loc_type = 'W' AND to_loc_type = 'W' AND tran_code = 30)"
				+ " OR (from_loc_type = 'W' AND to_loc_type = 'S' AND tran_code = 30)"
				+ " OR (from_loc_type = 'S' AND to_loc_type = 'W' AND tran_code = 32))"
				+ " AND tran_date >= TRUNC(SYSDATE)";
		
		String tdcQueryIntegrationServer = "select * from ( SELECT run_id, interface, start_dt, end_dt, status, server"
				+ " FROM WMLOG.wm_log_run"
				+ " WHERE interface = '" + data.get("wm_log") +"'"
				+ " and  start_dt >= TRUNC(SYSDATE)"
			    + " order by start_dt desc)"
				+ " where rownum = 1";
		
		String tdcQueryErrorId =" SELECT ERROR_ID,RUN_ID,ERROR_DATE,DESCRIPTION "
				+ " FROM WMLOG.WM_LOG_ERROR "
				+ " where RUN_ID=%s";
				//+ " and rownum = 1"; 
		
		String tdcQueryStatusThread = "SELECT parent_id,thread_id,name,wm_log_thread.status,att1,att2 "
				+ " FROM WMLOG.wm_log_thread "
				+ " WHERE parent_id = %s" ; 
		
		String tdcQueryThreads1 = "SELECT  THREAD_ID, PARENT_ID,NAME, START_DT,END_DT,STATUS "
				+ "FROM WMLOG.WM_LOG_THREAD "
				+ "WHERE PARENT_ID='%s' "
				+ "AND ATT2 = '"+data.get("att2")+"'"
				+ "ORDER BY THREAD_ID DESC";
 
		String tdcQueryThreads2 = "SELECT   ATT1, ATT2, ATT3,ATT4, ATT5,ATT6,ATT7,ATT8"
				+ " FROM WMLOG.WM_LOG_THREAD"
				+ " WHERE PARENT_ID='%s'"
				+ " AND ATT2 = '"+data.get("att2")+"'"
				+ " ORDER BY THREAD_ID DESC";
		
		String tdcQueryfemNotNull1 = "SELECT CR_PLAZA, REFERENCE_2, REFERENCE_7, REFERENCE_8" 
				+ " FROM fem_fif_stg"
				+ " WHERE reference_2 IS NOT NULL"
				+ " AND reference_8 IS NOT NULL"
				+ " AND reference_7 = 0" 
				+ " AND ((from_loc_type = 'W' AND to_loc_type = 'W' AND tran_code = 30)"
				+ " OR (from_loc_type = 'W' AND to_loc_type = 'S' AND tran_code = 30)" 
				+ " OR (from_loc_type = 'S' AND to_loc_type = 'W' AND tran_code = 32))"
				+ " AND tran_date >= TRUNC(SYSDATE)";
		
		String tdcQueryfemNotNull2 = "SELECT FROM_LOC_TYPE, TO_LOC_TYPE, TRAN_CODE, TRAN_DATE" 
				+ " FROM fem_fif_stg"
				+ " WHERE reference_2 IS NOT NULL"
				+ " AND reference_8 IS NOT NULL"
				+ " AND reference_7 = 0" 
				+ " AND ((from_loc_type = 'W' AND to_loc_type = 'W' AND tran_code = 30)"
				+ " OR (from_loc_type = 'W' AND to_loc_type = 'S' AND tran_code = 30)" 
				+ " OR (from_loc_type = 'S' AND to_loc_type = 'W' AND tran_code = 32))"
				+ " AND tran_date >= TRUNC(SYSDATE)";
		
		String tdcQueryHeaders = "SELECT HEADER_ID, RUN_ID, AP_INVOICE_ID, CREATED_DATE "
				+ " FROM wmuser.wm_ap_headers "
				+ " WHERE ap_invoice_id IS NOT NULL "
				+ " AND created_date = TRUNC(SYSDATE) "
				+ " AND run_id = '%s' "
				+ " AND header_id = '%s'";
		
		String tdcQueryapInterface = "SELECT INVOICE_ID, INVOICE_NUM, VENDOR_ID, STATUS "
				+ " FROM ap.ap_invoices_interface "
				+ " WHERE invoice_id = '%s'";
		
		String tdcQueryapLines = "SELECT INVOICE_ID, INVOICE_LINE_ID, LINE_NUMBER, LINE_TYPE_LOOKUP_CODE "
				+ " FROM ap.ap_invoice_lines_interface "
				+ " WHERE invoice_id = '%s'";
		
		

/**
* 	
* **********************************Pasos del caso de Prueba *****************************************
* 
*/
					
//		Paso 1	************************ 
				
		addStep("Validar que exista informaci�n pendiente de procesar para las Transferencias de la plaza en la tabla fem_fif_stg de RETEK..");
		System.out.println(GlobalVariables.DB_HOST_RMS_MEX);
		System.out.println(tdcQueryFem1);
					
		SQLResult femResult1 = executeQuery(dbRms, tdcQueryFem1);
		SQLResult femResult2 = executeQuery(dbRms, tdcQueryFem2);	
		boolean fem = femResult1.isEmpty();
		System.out.println(fem);
		testCase.addQueryEvidenceCurrentStep(femResult1);
		testCase.addQueryEvidenceCurrentStep(femResult2);

		assertFalse(fem, "No se obtiene informacion de la consulta");
		
//		Paso 2	************************ 
		
		addStep("Ejecutar el servicio RO3.Mappings:run.");
		
		String contra =   "http://"+user+":"+ps+"@"+server+":5555";
		u.get(contra);
		
		pok.runIntefaceWmOneButton10(data.get("interfase"), data.get("servicio"));
		//pok.runIntefaceWmTwoButtonsWihtoutInputs10(data.get("interfase"), data.get("servicio"));

		SQLResult query = executeQuery(dbLog, tdcQueryIntegrationServer);	
		String status1 = query.getData(0, "STATUS");
		run_id = query.getData(0, "RUN_ID");
		
//		Paso 3	************************
		
		//comentar sig 3 lineas
		//String status1 = "S";
		//run_id = "2162462273";
		//SQLResult query = executeQuery(dbLog, tdcQueryIntegrationServer);	
		
		addStep("Validar que el registro de la tabla WM_LOG_RUN termine en estatus 'S'.");
		boolean valuesStatus = status1.equals(searchedStatus);//Valida si se encuentra en estatus R
		while (valuesStatus) {
			
			query = executeQuery(dbLog, tdcQueryIntegrationServer);	
			status1 = query.getData(0, "STATUS");
			run_id = query.getData(0, "RUN_ID");
		 
		 u.hardWait(2);
		 
		}
		
		testCase.addQueryEvidenceCurrentStep(query);
		
		boolean successRun = status1.equals(status);//Valida si se encuentra en estatus S
		    if(!successRun){
		   
		   String error = String.format(tdcQueryErrorId, run_id);
		   SQLResult paso2 = executeQuery(dbLog, error);
		   
		   boolean emptyError = paso2.isEmpty();
		   
		   if(!emptyError){  
		   
		    testCase.addTextEvidenceCurrentStep("Se encontr� un error en la ejecuci�n de la interfaz en la tabla WM_LOG_ERROR");
		   
		    testCase.addQueryEvidenceCurrentStep(paso2);
		   
		   }
		}
		    

//		Paso 4	************************
		addStep("Se valida la generacion de thread.");
			
		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
		
		String statusThreadFormat = String.format(tdcQueryThreads1, run_id);
		System.out.println(statusThreadFormat);
		SQLResult threadResult = executeQuery(dbLog, statusThreadFormat);		
		testCase.addQueryEvidenceCurrentStep(threadResult);
		String statusThreadFormat2 = String.format(tdcQueryThreads2, run_id);
		System.out.println(statusThreadFormat2);
		SQLResult threadResult2 = executeQuery(dbLog, statusThreadFormat2);		
		testCase.addQueryEvidenceCurrentStep(threadResult2);
		
		boolean ST = false;
		String statusThread="", thread_id="";
		
		if (!threadResult.isEmpty()) {
		   statusThread = threadResult.getData(0, "Status");
		   thread_id = threadResult.getData(0, "thread_id");
		   ST = statusThread.equals(status);  
		} 

		System.out.println(ST);

		assertTrue(ST, "No se obtiene informacion o el campo STATUS del Thread es diferente a 'S' en la tabla WM_LOG_THREAD");

//		Paso 5 ***********************
		
		addStep("Verificar que los campos reference_2, reference_7 y reference8 no esten nulos, en la tabla fem_fif_stg de RETEK.");
		System.out.println(GlobalVariables.DB_HOST_RMS_MEX);
		System.out.println(tdcQueryfemNotNull1);
					
		SQLResult femNotNullResult1 = executeQuery(dbRms, tdcQueryfemNotNull1);
		SQLResult femNotNullResult2 = executeQuery(dbRms, tdcQueryfemNotNull2);
		boolean femNotNull = femNotNullResult1.isEmpty();
		System.out.println(femNotNull);
		
		String ref8 = "", ref2 = "";
		if(!femNotNull) {
			ref8 = femNotNullResult1.getData(0, "REFERENCE_8");
			ref2 = femNotNullResult1.getData(0, "REFERENCE_2");
		}
	
		
		testCase.addQueryEvidenceCurrentStep(femNotNullResult1);
		testCase.addQueryEvidenceCurrentStep(femNotNullResult2);

		assertFalse(femNotNull, "No se obtiene informacion de la consulta");
		
//		Paso 6 ***********************
		
		addStep("Validar la actualizaci�n del campo ap_invoice_id en la tabla wm_ap_headers en RETEK.");
		System.out.println(GlobalVariables.DB_HOST_RMS_MEX);
		
		String consultafor="";
		SQLResult resultfor;
		
		for(int i = 0; i < threadResult.getRowCount(); i++) {
			String p = threadResult.getData(i, "THREAD_ID");	
			consultafor = String.format(tdcQueryHeaders, p, ref8);
			System.out.println(consultafor);
			resultfor = executeQuery(dbRms, consultafor);
			
			boolean foav = resultfor.isEmpty();			
			if (!foav) {
				thread_id = threadResult.getData(i, "THREAD_ID");
			}
		
		}
		
		
		String formatHeaders = String.format(tdcQueryHeaders, thread_id, ref8); //run_id, ref8);
		System.out.println(formatHeaders);
		
		SQLResult headersResult= executeQuery(dbRms, formatHeaders);
		boolean headers = headersResult.isEmpty();
		System.out.println(headers);
    	testCase.addQueryEvidenceCurrentStep(headersResult);		

		assertFalse(headers, "No se obtiene informacion de la consulta");
		
//		Paso 7	************************
		addStep("Verificar que la informaci�n se haya insertado en la tabla ap_invoices_interface en ORAFIN.");
		System.out.println(GlobalVariables.DB_HOST_Ebs);
		String apFormat = String.format(tdcQueryapInterface, ref2);
		System.out.println(apFormat);
		
		SQLResult apResult= executeQuery(dbEbs, apFormat);
    	boolean apInterface = apResult.isEmpty();
		System.out.println(apInterface);
		testCase.addQueryEvidenceCurrentStep(apResult);

		assertFalse(apInterface, "No se obtiene informacion de la consulta");
		
		
//		Paso 8	************************
		
		addStep("Verificar que la informaci�n se haya insertado en la tabla ap_invoice_lines_interface en ORAFIN.");
		System.out.println(GlobalVariables.DB_HOST_Ebs);
		String apLineFormat = String.format(tdcQueryapLines, ref2);
		System.out.println(apLineFormat);
		
		SQLResult apLineResult= executeQuery(dbEbs, apLineFormat);
		boolean apLine = apLineResult.isEmpty();
		System.out.println(apLine);
		testCase.addQueryEvidenceCurrentStep(apLineResult);

		assertFalse(apLine, "No se obtiene informacion de la consulta");
				
				
		
		
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
		return "Verificar el procesamiento de las Tranferencias";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATION";
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
