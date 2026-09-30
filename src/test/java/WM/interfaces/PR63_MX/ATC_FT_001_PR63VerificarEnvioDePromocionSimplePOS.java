package WM.interfaces.PR63_MX;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class ATC_FT_001_PR63VerificarEnvioDePromocionSimplePOS extends BaseExecution{
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_PR63VerificarEnvioDePromocionSimplePOS_test (HashMap<String, String> data ) throws Exception {
		
		/* Utiler�as *********************************************************************/	
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_fcwmesit, GlobalVariables.DB_USER_fcwmesit, GlobalVariables.DB_PASSWORD_fcwmesit);
		utils.sql.SQLUtil db = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDASPFR, GlobalVariables.DB_USER_FCDASPFR, GlobalVariables.DB_PASSWORD_FCDASPFR);
		utils.sql.SQLUtil dbEbs= new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCEBSSIT, GlobalVariables.DB_USER_FCEBSSIT, GlobalVariables.DB_PASSWORD_FCEBSSIT);
		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA,GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		
		
		/**
		 * Variables
		 ******************************************************************************************
		 * 
		 */
		
		//		PASO 2
		
		String query = "SELECT * FROM wmuser.WM_INTERFASE_CONFIG WHERE Tran_Type=31 AND ThresHold_no is NULL AND INTERFASE = 'PR63'"; 
		
		//		PASO 3
		
		String queryDos = "SELECT" 
				+ " distinct batch_id, location" 
				+ " FROM wmuser.pos_prm_head"  
				+ " WHERE wm_prm_status='L'" 
				+ " order by batch_id, location asc";
			
		//		PASO 4
		
		String queryTres = "SELECT FTP_BASE_DIR FROM WMUSER.WM_FTP_CONNECTIONS "
				+ "AND FTP_CONN_ID IN ('%s')"; 
		
		String tdcQueryStatusThread1 = "SELECT thread_id, parent_id, name, start_dt"
				+ " FROM wmlog.wm_log_thread WHERE parent_id = '%s'"; // FCWMLQA
		
		String tdcQueryStatusThread2 = "SELECT end_dt, status, att1, att2"
				+ " FROM wmlog.wm_log_thread WHERE parent_id = '%s'"; // FCWMLQA
		
		// 		PASO 5
		
		String tdcTableMaestros = "SELECT ORACLE_CR_SUPERIOR, ORACLE_CR" + 
				" FROM XXFC.XXFC_MAESTRO_DE_CRS_V" + 
				" WHERE RETEK_CR IN ('%s')";
		
		//		PASO 6
		String tdcQueryStatusLog = "select * from (SELECT RUN_ID, INTERFACE, START_DT, END_DT, STATUS, SERVER"
				+ " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR63_MX' "				
				+ " AND START_DT >= TRUNC(SYSDATE)"
				+ " ORDER BY START_DT DESC) ";
		
		String tdcQueryIntegrationServer = "SELECT * FROM (SELECT run_id, start_dt, end_dt, status FROM WMLOG.wm_log_run"
				+ " WHERE interface = 'PR63_MX'"
				+ " AND start_dt >= TRUNC(SYSDATE)"
				+ " ORDER BY start_dt desc) WHERE ROWNUM = 1";
		
		String consultaERROR = " select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY, ERROR_TYPE, ERROR_CODE "
				+ "from  wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1";// Consulta para los errores
		
		String consultaERROR2 = " select * from (select DESCRIPTION, MESSAGE " + "from wmlog.WM_LOG_ERROR "
				+ "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
		
		String consultaERROR3 = " select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
				+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
		
		//		PASO 7
		String queryCinco = "SELECT * FROM POSUSER.pos_outbound_docs " + 
				"WHERE DOC_TYPE = 'PRM'";
		
		//		PASO 8
		String querySeis = "SELECT WM_PRM_STATUS FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS='P'";
		
		
		//		PASO 9
		String querySiete = "SELECT * FROM WMUSER.POS_PRM_HEAD WHERE WM_PRM_STATUS ='E' AND STATUS ='P'";
		
		/**
		 * 
		 * **********************************Pasos del caso de Prueba
		 * ***************************************** 
		 * 
		 */
		
		testCase.setProject_Name("N�cleo");
		
		
		
		//		PASO   1  
		addStep("Acceso a la BD WM_CENTRAL");// se necesitan agregar loas addstep para que se ejecute correctamente el script y se genere la evidencia
		testCase.addTextEvidenceCurrentStep("Base de Datos: fcwmesit");
		testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_fcwmesit);
		
		//		PASO 2	
		
	/*	addStep("Verificar la existencia de registros (para promociones simples) con valor en Tran_Type=31, ThresHold_no NULL de la tabla WM_INTERFASE_CONFIG del esquema WMUSER");
		
		SQLResult result1 = executeQuery(dbPos, query);	
		boolean validacion = result1.isEmpty();
		
		if (validacion) {
      	  testCase.addTextEvidenceCurrentStep("No existen registros");
        }
		
		testCase.addQueryEvidenceCurrentStep(result1); */
		
		//		PASO 3
		addStep("Consultar las tiendas (location) pendientes de procesar junto con su batchId que cuenten con WM_PRM_STATUS = 'L' en la tabla POS_PRM_HEAD en el esquema WMUSER" + 
				"NOTA: Se utilizaran threads por cada procesamiento por location/BatchId");
		
        String tienda = "";
		String tdcTableMaestrosForm = String.format(queryDos);
		System.out.println(tdcTableMaestrosForm);
		
		SQLResult TableMaestros = executeQuery(db, tdcTableMaestrosForm);
		boolean ma = TableMaestros.isEmpty();
		
		if (!ma) {
				System.out.println("filas: "+TableMaestros.getRowCount());
				for (int i = 0;i < TableMaestros.getRowCount();i++) {
		        	  tienda += TableMaestros.getData(i, "batch_id");
		        	  System.out.println("VALOR DE I: "+i);
				}
	    }else {
	    	testCase.addTextEvidenceCurrentStep("No existen registros");
	    }
		
		//		PASO 4
		addStep("Consultar la ruta base de buzones en la tabla WM_FTP_CONNECTIONS (en el campo FTP_BASE_DIR) en el esquema WMUSER");
		
		String PosOutBoundFormat1 = String.format(queryTres);	
        System.out.println(PosOutBoundFormat1);

        SQLResult PosOutBound1 = executeQuery(dbPos, PosOutBoundFormat1);
        
        
        String queryStatusThread1 = String.format(tdcQueryStatusThread1, tienda);		
        String queryStatusThread2 = String.format(tdcQueryStatusThread2, tienda);	
        System.out.println(queryStatusThread1);

        SQLResult StatusThread1 = executeQuery(dbLog, queryStatusThread1);
        SQLResult StatusThread2 = executeQuery(dbLog, queryStatusThread2);
        
        boolean dir = PosOutBound1.isEmpty();
		
		if (dir) {
      	  testCase.addTextEvidenceCurrentStep("No existen registros");
        }
		
		//		PASO 5
		
		addStep("Consultar el campo ORACLE_CR_SUPERIOR y ORACLE_CR de la vista XXFC_MAESTRO_DE_CRS_V del esquema XXFC, filtrandolo por el  RETEK_CR de la tienda que se usara para la prueba");
		
		SQLResult result = executeQuery(db, query);	// 
		boolean val = result.isEmpty();
		
		if (val) {
      	  testCase.addTextEvidenceCurrentStep("No existen registros");
        }
		
		//		PASO 6
		
		addStep("Se ejecuta el job PR63_MX para ejecutar la interface desde Control-M");
        
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

 		//String dateExecution = pok.runIntefaceWmOneButton10(data.get("interfase"), data.get("servicio"));
 		//System.out.println("Respuesta dateExecution" + dateExecution);

 		SQLResult is = executeQuery(dbLog, tdcQueryIntegrationServer);

 		String status1 = "";
 		String run_id = "";
 		if (!is.isEmpty()) {
 	  		status1 = is.getData(0, "STATUS");
 	  		run_id = is.getData(0, "RUN_ID");
 		}	 		

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

 			SQLResult errorr = dbLog.executeQuery(error);
 			boolean emptyError = errorr.isEmpty();

 			if (!emptyError) {

 				testCase.addTextEvidenceCurrentStep(
 						"Se encontro un error en la ejecucion de la interfaz en la tabla WM_LOG_ERROR");

 				testCase.addTextEvidenceCurrentStep("select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY, ERROR_TYPE, ERROR_CODE, DESCRIPTION, MESSAGE,"
 						+ " PROC_ROLE, PROC_STATUS, CR_PLAZA, THREAD_ID, ATT1 from  WMLOG.WM_LOG_ERROR where RUN_ID='"+ run_id+ "') where rownum <=1");
 				testCase.addQueryEvidenceCurrentStep(errorr, false);

 			}

 			SQLResult errorIS = dbLog.executeQuery(error1);
 			boolean emptyError1 = errorIS.isEmpty();
 			if (!emptyError1) {
 				testCase.addQueryEvidenceCurrentStep(errorIS, false);
 			}

 			SQLResult errorIS2 = dbLog.executeQuery(error2);
 			boolean emptyError2 = errorIS2.isEmpty();

 			if (!emptyError2) {
 				testCase.addQueryEvidenceCurrentStep(errorIS2, false);
 			}

 		 }
		
		

          System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
          String fcwS = "";
          

          SQLResult StatusLog = executeQuery(dbLog, tdcQueryStatusLog);
          
          if (!StatusLog.isEmpty()) {
        	  fcwS = StatusLog.getData(0, "STATUS");
			}
          
          testCase.addQueryEvidenceCurrentStep(StatusLog);
          
		
		//		PASO 7
		
		addStep("Verificar los registros del PRM en la tabla POS_OUTBOUND_DOCS del esquema POSUSER");
		
		SQLResult prm = executeQuery(dbPos, queryCinco);	
		boolean validacionPRM = prm.isEmpty();
		
		if (validacionPRM) {
      	  testCase.addTextEvidenceCurrentStep("No existen registros");
        }
		
		//		PASO 8
		
		addStep("Verificar que se haya actualizado el valor del campo POS_PRM_HEAD.WM_PRM_STATUS = 'P' en el esquema WMUSER");
		
		SQLResult p = executeQuery(db, querySeis);	
		boolean validacionP = p.isEmpty();
		
		if (validacionP) {
      	  testCase.addTextEvidenceCurrentStep("No existen registros");
        }
		
		
		//		PASO 9
		
		addStep("Verificar que los campos de la tabla POS_PRM_HEAD en el esquema WMUSER en la BD DAS_OPERATIVO sean igual a WM_PRM_STATUS ='E' y STATUS ='P'");
		
		SQLResult prm_head = executeQuery(db, querySiete);	
		boolean validacionPRMHEAD = prm_head.isEmpty();
		
		if (validacionPRMHEAD) {
      	  testCase.addTextEvidenceCurrentStep("No existen registros");
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
		return "PR63_MX";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
