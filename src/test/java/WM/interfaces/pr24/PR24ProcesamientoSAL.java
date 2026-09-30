package WM.interfaces.pr24;

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

public class PR24ProcesamientoSAL extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_pr24_ProcesamientoSAL(HashMap<String, String> data) throws Exception {

		/*
		 * Utiler�as
		 *********************************************************************/

		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA,
				GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		
//		BD NUCLEO
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_fcwmesit,
				GlobalVariables.DB_USER_fcwmesit, GlobalVariables.DB_PASSWORD_fcwmesit);
//		BD ORIGINAL
//		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMQA_NUEVA,
//				GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
	
//	    BD NUCLEO
		utils.sql.SQLUtil dbRms = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCRMSMGR,
				GlobalVariables.DB_USER_FCRMSMGR, GlobalVariables.DB_PASSWORD_FCRMSMGR);
//		BD ORIGINAL
//	    utils.sql.SQLUtil dbRms = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX,
//				GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
	    
		
//		utils.sql.SQLUtil dbServ = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_Puser, GlobalVariables.DB_USER_Puser,
//				GlobalVariables.DB_PASSWORD_Puser);
		
		utils.sql.SQLUtil dbDas = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDASPFR, 
				GlobalVariables.DB_USER_FCDASPFR, GlobalVariables.DB_PASSWORD_FCDASPFR);

		/**
		 * 
		 * Variables
		 * ******************************************************************************************
		 * 
		 * 
		 */

		String tdcQueryDocSAL = "SELECT * FROM WMUSER.WM_INTERFASE_CONFIG WHERE INTERFASE = 'PR24'"; //1
		
		String tdcQuerySAL = " SELECT PV_CR_PLAZA, PV_CR_TIENDA, B.ID"   // 2
				+ " FROM POSUSER.POS_ENVELOPE A, POSUSER.POS_INBOUND_DOCS B"
				+ " WHERE B.PE_ID = A.ID " 
				+ " AND B.DOC_TYPE='SAL' "
				+ " AND B.PARTITION_DATE>=TRUNC(SYSDATE-7) " 
				+ " AND A.PARTITION_DATE>=TRUNC(SYSDATE-7) "
				+ " AND B.STATUS='I' "
			    + " GROUP BY PV_CR_PLAZA, PV_CR_TIENDA, B.ID";
		

		String tdcQueryDocID = "	SELECT B.ID DOC_ID "  // 3
				+ " FROM posuser.POS_INBOUND_DOCS B " 
				+ " WHERE B.DOC_TYPE = 'SAL' "
				+ " AND  B.STATUS =  'I' "
				+ " AND SUBSTR (B.PV_DOC_NAME, 4,10) = '10MON' || '50MCZ' "
				+ " AND B.PARTITION_DATE >= TRUNC (SYSDATE-7)";
		
		String tdcQueryPlazas = "SELECT * FROM WMUSER.WM_OXXO_ROLLOUT_INTERFACES_V";  // 4
		
		String tdcQueryConnRetek  = "SELECT ftp_base_dir, ftp_serverhost, ftp_serverport, ftp_username, ftp_password"
				+ " FROM wmuser.wm_ftp_connections" + " WHERE ftp_conn_id = 'RTKRMS'"; 

		String tdcQueryProceSal = " SELECT DOC_TYPE,RUN_ID,CREATED_DATE,STATUS,CR_PLAZA,CR_TIENDA"
				+ " FROM wmuser.RTK_INBOUND_DOCS " + " WHERE DOC_TYPE = 'SAL'" + " AND STATUS = 'L'"
				+ " AND run_id in ('%s')";

		String tdcQueryProceInboud = "SELECT ID,PE_ID,DOC_TYPE,STATUS,PV_DOC_NAME,TARGET_ID,PARTITION_DATE"
				+ " FROM POSUSER.POS_INBOUND_DOCS" + " WHERE DOC_TYPE = 'SAL'" + " AND STATUS = 'E'"
				+ " AND ID IN (%s)"; 

		String tdcQueryIntegrationServer = "select * from ( SELECT run_id,start_dt,status" + " FROM WMLOG.wm_log_run"
				+ " WHERE interface = '" + data.get("Run_interface") + "'" + " and  start_dt >= TRUNC(SYSDATE)"
				+ " order by start_dt desc)" + " where rownum = 1";

		String tdcQueryStatusThread = "SELECT parent_id,thread_id,name,wm_log_thread.status,att1,att2 "
				+ " FROM WMLOG.wm_log_thread " + " WHERE parent_id = %s"; // FCWMLQA

		String qry_threads2 = "SELECT  ATT1, ATT2, ATT3,ATT4, ATT5,ATT6,ATT7,ATT8 FROM WMLOG.WM_LOG_THREAD "
				+ "WHERE PARENT_ID = '%s'";

		// consultas de error
		String consultaError1 = " select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE "
				+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1"; // dbLog
		String consultaError2 = " select * from (select description,MESSAGE " + "from wmlog.WM_LOG_ERROR "
				+ "where RUN_ID='%s')WHERE rownum <= 1"; // dbLog
		String consultaError3 = " select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
				+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s')WHERE rownum <= 1"; // dbLog

	/*	String tdcQueryStatusLog = "select * from (SELECT run_id,interface,start_dt,status,server "
				+ " FROM WMLOG.wm_log_run" + " WHERE interface = '" + data.get("Run_interface") + "'"
				+ " and start_dt >= trunc(sysdate) " // FCWMLQA
				+ " ORDER BY start_dt DESC)" + " where rownum = 1"; */
		
		String tdcQueryStatusLog = "select * from wmlog.wm_log_run" 
				+ "	--where run_id = 'run_id' " 
				+ "	where interface LIKE '%interface%' " 
				+ "	--and start_dt >= trunc(sysdate-1) " 
				+ "	--and server = 'fcwmqaz2.femcom.net' " 
				+ "	ORDER BY RUN_ID desc";
		
		
		String tdcQueryStatusLogDos = "select " 
				+ "  from wmlog.wm_log_error\n"  
				+ "  where run_id = 'run_id'\n" 
				+ " --and MESSAGE like '%message%'\n"  
				+ " --AND DESCRIPTION LIKE '%10%'\n" ;
				
		

		String status = "S";
		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
		String con = "http://" + user + ":" + ps + "@" + server + ":5555";
		String searchedStatus = "R";
		String run_id;
		String id = "";
		String thread = "";
		
		String tdcQueryInsersion = "SELECT * FROM WMUSER.RTK_INBOUND_DOCS";
		
		String tdcQueryValidarDocuSAL = "SELECT B.ID DOC_ID " 
				+ "FROM posuser.POS_INBOUND_DOCS B "  
				+ "WHERE B.DOC_TYPE = 'SAL' " 
				+ "AND  B.STATUS =  'E' ";
		
		String tdcQueryFTP = "SELECT * FROM wmuser.RTK_INBOUND_DOCS where doc_type = 'SAL' and rownum <= 10; -- DOC_ID  desc";
		

		/**
		 * 
		 * **********************************Pasos del caso de Prueba
		 * *****************************************
		 * 
		 * 
		 */

		/* Paso 1 */
		
		addStep("La Consulta muestra la Informacion que se tiene configurada. ");
		
		SQLResult prm = executeQuery(dbDas, tdcQueryDocSAL);	
		boolean validacionPRM = prm.isEmpty();
		
		if (validacionPRM) {
      	  testCase.addTextEvidenceCurrentStep("No existen registros");
        }
		
		
		
		/* Paso 2 */
		
		addStep("Validamos tiendas disponibles pendientes a procesarcon documentos SAL que esten en estatus I ");

		System.out.println(GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		System.out.println(tdcQuerySAL);

		SQLResult paso1 = executeQuery(dbPos, tdcQuerySAL);
		// Saque la variable
//		id = SQLUtil.getColumn(testCase, dbPos, tdcQuerySAL, "ID");

		boolean reg = paso1.isEmpty();

		if (!reg) {
			id = paso1.getData(0, "ID");
			System.out.println("Paso 1 ID: " + id);
			testCase.addQueryEvidenceCurrentStep(paso1);

		} else if (reg) {
			testCase.addQueryEvidenceCurrentStep(paso1);
			testCase.addTextEvidenceCurrentStep("No encontro registros para procesar en las tablas POS_ENVELOPE, POS_INBOUND_DOCS de POSUSER" );
		}

		System.out.println(reg);

		assertFalse(reg, " No se muestran registros a procesar ");

	
		addStep("Validar los datos de conexi�n FTP del servidor RETEK donde se enviar�n los documentos generados por la ejecucion de la interface.");

		System.out.println(GlobalVariables.DB_HOST_Puser);
		System.out.println(tdcQueryConnRetek);
		SQLResult paso2 = executeQuery(dbPos, tdcQueryConnRetek);

		boolean serv = paso2.isEmpty();

		if (!serv) {

			testCase.addQueryEvidenceCurrentStep(paso2);

		} else if (serv) {
			testCase.addQueryEvidenceCurrentStep(paso2);
			testCase.addTextEvidenceCurrentStep("No encontro datos de conexi�n FTP del servidor RETEK donde se enviar�n los documentos generados por la ejecucion" );
			
		} 

		System.out.println(serv); 

//		assertFalse(serv, "No se tiene conexi�n al servidor FTP");

		/* Paso 3 */
		addStep("Validamos tiendas disponibles pendientes a procesarcon documentos SAL que esten en estatus I ");
		
		SQLResult tiendas = executeQuery(dbPos, tdcQueryDocID);	
		boolean validacionEstatusSAL = tiendas.isEmpty();
		
		if (validacionEstatusSAL) {
      	  testCase.addTextEvidenceCurrentStep("No existen registros");
        }
		
		
		/* Paso 4 */
		addStep("Se valida que la plaza a procesar este activa la bandera ");
		
		SQLResult plaza = executeQuery(dbDas, tdcQueryPlazas);	
		boolean validacionPlaza = plaza.isEmpty();
		
		if (validacionPlaza) {
      	  testCase.addTextEvidenceCurrentStep("No existen registros");
        }
		
		
		
		/* Paso 5 */
		addStep("Ejecutar el servicio PR24.Pub:run");
		// utileria
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		PakageManagment pok = new PakageManagment(u, testCase);

		u.get(con);

		pok.runIntefaceWmOneButton10(data.get("interfase"), data.get("servicio"));

		SQLResult query = executeQuery(dbLog, tdcQueryIntegrationServer);
		String status1 = query.getData(0, "STATUS");
		run_id = query.getData(0, "RUN_ID");

		boolean valuesStatus = status1.equals(searchedStatus);// Valida si se encuentra en estatus R
		while (valuesStatus) {

			query = executeQuery(dbLog, tdcQueryIntegrationServer);
			status1 = query.getData(0, "STATUS");
			run_id = query.getData(0, "RUN_ID");

			u.hardWait(2);

		}

		boolean successRun = status1.equals(status);// Valida si se encuentra en estatus S
		System.out.println(successRun);
		if (!successRun) {

			String error = String.format(consultaError1, run_id);
			String error1 = String.format(consultaError2, run_id);
			String error2 = String.format(consultaError3, run_id);

			SQLResult paso3 = executeQuery(dbLog, error);

			boolean emptyError = paso3.isEmpty();

			if (!emptyError) {

				testCase.addTextEvidenceCurrentStep(
						"Se encontr� un error en la ejecuci�n de la interfaz en la tabla WM_LOG_ERROR");

				testCase.addQueryEvidenceCurrentStep(paso3);

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

//	Paso 6	************************

		addStep("Comprobar que se registra la ejecucion en WMLOG");

		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
		String queryStatusLog = String.format(tdcQueryStatusLog, run_id);
		System.out.println(queryStatusLog);
		SQLResult paso6 = executeQuery(dbLog, queryStatusLog);
		boolean ejecucion = paso6.isEmpty();
		String fcwS = "";

		if (!ejecucion) {

			testCase.addQueryEvidenceCurrentStep(paso6);
			fcwS = paso6.getData(0, "STATUS");

		} else if (ejecucion) {
			testCase.addQueryEvidenceCurrentStep(paso6);
			testCase.addTextEvidenceCurrentStep("No encontro registro la ejecucion en WMLOG" );
			
		}

		boolean validateStatus = status.equals(fcwS);
		System.out.println(validateStatus);

//		assertTrue(validateStatus, "La ejecuci�n de la interfaz no fue exitosa");

		SQLResult log = dbLog.executeQuery(tdcQueryIntegrationServer);

		testCase.addQueryEvidenceCurrentStep(log);



//		addStep("Se valida la generacion de thread");
/*		System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
		String queryStatusThread = String.format(tdcQueryStatusThread, run_id);
		System.out.println(queryStatusThread);
		SQLResult queryStatusThreadResult = dbLog.executeQuery(queryStatusThread);

		boolean threadResult = queryStatusThreadResult.isEmpty();
		if (!threadResult) {

			testCase.addQueryEvidenceCurrentStep(queryStatusThreadResult);
			thread = queryStatusThreadResult.getData(0, "THREAD_ID");
		} else if (threadResult) {
			testCase.addQueryEvidenceCurrentStep(queryStatusThreadResult);
			
		}

		// .-----------Segunda consulta
		String consulta2 = String.format(qry_threads2, run_id);
		SQLResult consultaThreads2 = dbLog.executeQuery(consulta2);
		boolean threads1 = consultaThreads2.isEmpty();
		if (!threads1) {
			testCase.addQueryEvidenceCurrentStep(consultaThreads2);
		} else if (threads1) {
			testCase.addQueryEvidenceCurrentStep(consultaThreads2);
			testCase.addTextEvidenceCurrentStep("No encontro generacion de thread" );
		}
		System.out.println(threads1);
//		assertFalse("No se generaron threads en la tabla", threads1); *?

		/* Paso 7 */
		addStep("En caso de aparecer un error validamos el run_id para ver a mas a detalle el error y reportarlo a Desarrollo.");
		
		String queryStatusLogDos = String.format(tdcQueryStatusLogDos, run_id);
		System.out.println(queryStatusLog);
		SQLResult paso4 = executeQuery(dbLog, queryStatusLogDos);
		boolean ejecucionDos = paso4.isEmpty();
		
		if (ejecucionDos) {
      	  testCase.addTextEvidenceCurrentStep("No existen registros");
        }
		

		/* Paso 8 */
		addStep("Validar la inserci�n de la l�nea con datos de la transacci�n en la BD de RMS10 (Rtk_Inbound_Docs) para hacer un cross referencia posterior.");

		System.out.println(GlobalVariables.DB_HOST_RMS_MEX);

		String queryPrs = String.format(tdcQueryInsersion, thread);
		System.out.println(queryPrs);
		SQLResult paso8 = executeQuery(dbRms, queryPrs);

		boolean regSAL = paso8.isEmpty();

		if (!regSAL) {

			testCase.addQueryEvidenceCurrentStep(paso8);

		} else if (regSAL) {
			testCase.addQueryEvidenceCurrentStep(paso8);
			testCase.addTextEvidenceCurrentStep(" No inserto los registros de los documentos SAL procesados en la tabla RTK_INBOUND_DOCS de RETEK con STATUS = 'L'" );
		}

		System.out.println(regSAL); 

//		assertFalse(regSAL, " No se muestran registros a procesar ");

		/* Paso 9 */
		addStep("Validamos que los documentos SAL se hayan actualizado en estatus E");
		System.out.println(GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		//System.out.println(tdcQueryProceInboud);

		String stSAL = String.format(tdcQueryValidarDocuSAL, id);
		System.out.println(stSAL);
		SQLResult paso7 = executeQuery(dbPos, stSAL);

		boolean valSta = paso7.isEmpty();

		if (!valSta) {

			testCase.addQueryEvidenceCurrentStep(paso7);

		} else if (valSta) {
			testCase.addQueryEvidenceCurrentStep(paso7);
			testCase.addTextEvidenceCurrentStep("No encontro registros que coincidan con lo requerido(No actualizo status)" );
		}
		

		System.out.println(stSAL); 

		//assertFalse(valSta, " No se muestran registros a procesado");
		


		/* Paso 10 */
		addStep("Se consulta la tabla RTK_INBOUND_DOCS de la BD RMS10 para validar los documentos SAL depositados en el servidor FTP de RMS10 para el archivo (ejemplo) POSU10MON50MCZ180615155718.dat generado.V");
		
		SQLResult paso10 = executeQuery(dbRms, tdcQueryFTP);
		boolean ejecucionRTK = paso10.isEmpty();
		
		if (ejecucionRTK) {
      	  testCase.addTextEvidenceCurrentStep("No existen registros");
      	testCase.addTextEvidenceCurrentStep(" No inserto los registros de los documentos SAL procesados en la tabla RTK_INBOUND_DOCS depositados en el servidor FTP de RMS10" );
        }
	
	
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Terminada. Enviar la informaci�n de ventas diarias al sistema RMS";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "QAautomation";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_pr24_ProcesamientoSAL";
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
