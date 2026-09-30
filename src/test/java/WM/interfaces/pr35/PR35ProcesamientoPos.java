package WM.interfaces.pr35;
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
public class PR35ProcesamientoPos extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarQueLaInterfazPR35ProceseElCatalogoDeRecetasCorrectamenteDeRMS10aPOS(HashMap<String, String> data) throws Exception {
	
		/* Utiler�as *********************************************************************/		
	    
		utils.sql.SQLUtil dbRms = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCRMSMGR, GlobalVariables.DB_USER_FCRMSMGR, GlobalVariables.DB_PASSWORD_FCRMSMGR);

		//new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX, GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);
								  
		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA,GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		
		utils.sql.SQLUtil dbPos = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_fcwmesit, GlobalVariables.DB_USER_fcwmesit, GlobalVariables.DB_PASSWORD_fcwmesit);
								  //new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMQA_NUEVA,GlobalVariables.DB_USER_FCWMQA_NUEVA,GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		
		utils.sql.SQLUtil dbEbs= new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCEBSSIT, GlobalVariables.DB_USER_FCEBSSIT, GlobalVariables.DB_PASSWORD_FCEBSSIT);
			
		/**
		 * Variables
		 ******************************************************************************************
		 * 
		 */

		
		

		//														PASO 1		
        String tdcAvailebleDateIn = "SELECT to_char(max(END_DATE), 'DD/MM/YYYY HH:MI:SS AM') LAST_EXEC_DATE "
        		+ "FROM wmuser.WM_DATES_EXEC WHERE INTERFASE = 'PR35'"; // pos user

       //														PASO 2
		String tdcAvailableDateIn2 = "SELECT to_char(sysdate,'DD/MM/YYYY HH:MI:SS AM') CURRENT_DATE FROM DUAL"; //end_date

       //														PASO 2
		
		
		String tdcDatosProc1 = "SELECT * FROM (SELECT DISTINCT il.loc location, SUBSTR(store_name10, 1, 5) plaza"  
				+ " FROM wmuser.item_master im, wmuser.item_loc il, store s" 
				+ " WHERE im.item = il.item" 
				+ " AND ((il.create_datetime <=  TO_DATE ('%s', 'DD/MM/YYYY HH:MI:SS AM'))" 
				+ " OR ( im.last_update_datetime <=  TO_DATE ('%s', 'DD/MM/YYYY HH:MI:SS AM')))" 
				+ " AND im.pack_ind = 'Y'" 
				+ " AND s.store = il.loc" 
				//"AND  il.loc = \r\n" + 
				+ " ORDER BY plaza) WHERE ROWNUM <= 30";
		
		String tdcDatosProc2 = "SELECT * FROM (SELECT DISTINCT il.loc location, SUBSTR(store_name10, 1, 5) plaza"  
				+ " FROM wmuser.item_master im, wmuser.item_loc il, store s" 
				+ " WHERE im.item = il.item" 
				+ " AND im.pack_ind = 'Y'" 
				+ " AND s.store = il.loc" 
				//"AND  il.loc = \r\n" + 
				+ " ORDER BY plaza) WHERE ROWNUM <= 10";
		
		
		String tdcDatosProc = "SELECT DISTINCT il.loc location, SUBSTR(store_name10, 1, 5) plaza"  
				+ " FROM wmuser.item_master im, wmuser.item_loc il, store s" 
				+ " WHERE im.item = il.item" 
				+ " AND ((il.create_datetime >= TO_DATE ('%s', 'DD/MM/YYYY HH:MI:SS AM')" 
				+ " AND il.create_datetime <=  TO_DATE ('%s', 'DD/MM/YYYY HH:MI:SS AM'))" 
				+ " OR (im.last_update_datetime >= TO_DATE ('%s', 'DD/MM/YYYY HH:MI:SS AM')" 
				+ " AND im.last_update_datetime <=  TO_DATE ('%s', 'DD/MM/YYYY HH:MI:SS AM')))" 
				+ " AND im.pack_ind = 'Y'" 
				+ " AND s.store = il.loc" 
				//"AND  il.loc = \r\n" + 
				+ " ORDER BY plaza";
		
		String tdcDatosRecetas= "SELECT ROWNUM LINENUMBER," + 
				" LPAD (IM.ITEM, 16, ' ') ITEM," + 
				" LPAD (PI.ITEM, 16, ' ') PACKITEM," + 
				" PI.PACK_QTY QUANTITY" + 
				" FROM ITEM_LOC IL, ITEM_MASTER IM, PACKITEM PI" + 
				" WHERE IL.ITEM = PI.PACK_NO AND IL.ITEM = IM.ITEM AND IM.PACK_IND = 'Y'" + 
				" AND ( (IL.CREATE_DATETIME > TO_DATE ('%s', 'DD/MM/YYYY HH:MI:SS AM')" + 
				" AND IL.CREATE_DATETIME <=TO_DATE ('%s','DD/MM/YYYY HH:MI:SS AM'))" + 
				" OR (IM.last_update_datetime >TO_DATE ('%s','DD/MM/YYYY HH:MI:SS AM')" + 
				" AND IM.last_update_datetime <=TO_DATE ('%s','DD/MM/YYYY HH:MI:SS AM'))" + 
				" )" + 
				" AND IL.LOC in ('%s')"; //" AND IL.LOC = 'LISTADO DE LOCATIONS";			
		
		
		String tdcTableMaestros ="SELECT ORACLE_CR_SUPERIOR, ORACLE_CR" + 
				" FROM XXFC.XXFC_MAESTRO_DE_CRS_V" + 
				" WHERE RETEK_CR IN ('%s')";
		
		
		
		String tdcTableWmOxxoRollout = " SELECT * " + 
				" FROM WMUSER.WM_OXXO_ROLLOUT_INTERFACES_V" + 
				" WHERE CR_PLAZA IN ('%s')";
                   
		//														PASO 3
		String FTPContention = "SELECT FTP_BASE_DIR, FTP_SERVERHOST, FTP_SERVERPORT, FTP_USERNAME"
				+ " FROM WMUSER.WM_FTP_CONNECTIONS"
				+ " WHERE FTP_CONN_ID = 'PR50POS'";
                
        //														PASO 4-5
		String tdcQueryStatusLog = "select * from (SELECT RUN_ID, INTERFACE, START_DT, END_DT, STATUS, SERVER"
				+ " FROM WMLOG.WM_LOG_RUN"
				+ " WHERE INTERFACE = 'PR35main' "				
				+ " AND START_DT >= TRUNC(SYSDATE)" // FCWMLQA
				+ " ORDER BY START_DT DESC) WHERE ROWNUM = 1";
		

		String tdcQueryIntegrationServer = "SELECT * FROM (SELECT run_id, start_dt, end_dt, status FROM WMLOG.wm_log_run"
				+ " WHERE interface = 'PR35main'"
//				+ " AND status = 'S'"
				+ " AND start_dt >= TRUNC(SYSDATE)"
				+ " ORDER BY start_dt desc) WHERE ROWNUM = 1";
		
		
		String consultaERROR = " select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY, ERROR_TYPE, ERROR_CODE "
				+ "from  wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1";// Consulta para los errores
		
		String consultaERROR2 = " select * from (select DESCRIPTION, MESSAGE " + "from wmlog.WM_LOG_ERROR "
				+ "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
		
		String consultaERROR3 = " select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
				+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
		

		String tdcQueryStatusThread1 = "SELECT thread_id, parent_id, name, start_dt"
				+ " FROM wmlog.wm_log_thread WHERE parent_id = '%s'"; // FCWMLQA
		
		String tdcQueryStatusThread2 = "SELECT end_dt, status, att1, att2"
				+ " FROM wmlog.wm_log_thread WHERE parent_id = '%s'"; // FCWMLQA
                    
		//														PASO 6
		String tdcDocPosOutbound1 = "SELECT ID, POE_ID, DOC_NAME"
				+ " FROM POSUSER.POS_OUTBOUND_DOCS" 
				+ " WHERE DOC_TYPE='PCC'"
				//+ " AND SENT_DATE >= TRUNC(SYSDATE)"
				+ " AND PV_CR_PLAZA IN ('%s')"
				+ " AND PV_CR_TIENDA IN ('%s')"
				+ " AND STATUS = 'L'"
				+ " ORDER BY SENT_DATE DESC ";
		
		String tdcDocPosOutbound2 = "SELECT DOC_TYPE, SENT_DATE, PV_CR_PLAZA, PV_CR_TIENDA, STATUS, SOURCE_ID"
				+ " FROM POSUSER.POS_OUTBOUND_DOCS" 
				+ " WHERE DOC_TYPE='PCC'"
				//+ " AND SENT_DATE >= TRUNC(SYSDATE)"
				+ " AND PV_CR_PLAZA IN ('%s')"
				+ " AND PV_CR_TIENDA IN ('%s')"
				+ " AND STATUS = 'L'"
				+ " ORDER BY SENT_DATE DESC ";

        //														PASO 7
          String RegistroPos = "SELECT * FROM wmuser.WM_DATES_EXEC WHERE INTERFASE = 'PR35'"
          		+ "AND  RUN_ID = '%s' "
          		+ "ORDER BY END_DATE DESC";
          
		/**
		 * 
		 * **********************************Pasos del caso de Prueba
		 * ***************************************** 
		 * 
		 */
          
          testCase.setProject_Name("N�cleo"); 
          
        //															PASO 1	
          
         // addStep("Consulta la ultima fecha de ejecucion (start_date)");

//          System.out.println(GlobalVariables.DB_HOST_fcwmesit);
//          System.out.println(tdcAvailebleDateIn);

          SQLResult AvailableDate = executeQuery(dbPos, tdcAvailebleDateIn);		
          String LAST_EXEC_DATE = "";        
	
          boolean day = AvailableDate != null;
          if (!day) {
        	
        	  LAST_EXEC_DATE = AvailableDate.getData(0, "LAST_EXEC_DATE");
        	  testCase.addQueryEvidenceCurrentStep(AvailableDate);
          }
          
          System.out.println("LAST_EXEC_DATE: " + LAST_EXEC_DATE);
          
          /*if (LAST_EXEC_DATE == null) {
        	  testCase.addTextEvidenceCurrentStep("La consulta no genera registros");
          }
          System.out.println(day);
          testCase.addQueryEvidenceCurrentStep(AvailableDate);*/
          // assertFalse(day, "La consulta no muestra informacion de la ultima ejecucion");
	
          
          //														PASO 2	

        //  addStep("Validar que exista informaci�n para el rango de fechas de ejecucion (end_date)");

          System.out.println(tdcAvailableDateIn2);

          SQLResult AvailableDate2 = executeQuery(dbPos, tdcAvailableDateIn2);		
          String CURRENT_DATE = null;

          boolean days = AvailableDate2.isEmpty();
          if (!days) {
			CURRENT_DATE = AvailableDate2.getData(0, "CURRENT_DATE");
          }
          
          System.out.println("CURRENT_DATE: " + CURRENT_DATE);
          
         /* if (CURRENT_DATE == null) {
        	  testCase.addTextEvidenceCurrentStep("La consulta no genera registros");
          }
          
          System.out.println(days);
          testCase.addQueryEvidenceCurrentStep(AvailableDate2);*/
         // assertFalse(days, "No se muestra la fecha actual");
          
		
          
          //														PASO   1  
  		  addStep("Acceso a la BD de RMS10");// se necesitan agregar loas addstep para que se ejecute correctamente el script y se genere la evidencia
  		  //testCase.addPaso("Acceso a la BD de RMS10");
  		  //testCase.addBoldTextEvidenceCurrentStep("Acceso a la BD de RMS10");
  		  testCase.addTextEvidenceCurrentStep("Base de Datos: FCRMSMGR");
  		  testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_FCRMSMGR);
          
  		 
  		 
          
          //														PASO 2		

          //addStep("Validar que exista informaci�n para el rango de tiempo que designamos en las tablas ITEM_MASTER, ITEM_LOC y STORE");
          
          
          addStep("Validar las plazas/articulos disponibles para enviar mediante la siguiente consulta:");
  		  String location = ""; // Valor inicial, si posteriormente es lcoation = "00000" este paso fall�
          							 // y no actualiza su valor, y habra error en los pasos posteriores
          
          System.out.println("last exec date>"+LAST_EXEC_DATE);
          System.out.println("current date>"+CURRENT_DATE);
          
          if (LAST_EXEC_DATE.isEmpty() && !CURRENT_DATE.isEmpty()) {
        	//  String datos = String.format(tdcDatosProc2);//, LAST_EXEC_DATE, CURRENT_DATE, LAST_EXEC_DATE, CURRENT_DATE);		
        	  String datos = String.format(tdcDatosProc1, CURRENT_DATE, CURRENT_DATE);	
        	  System.out.println(datos);
        	  
        	  SQLResult Date3 = executeQuery(dbRms, datos);		
        	  
        	  boolean date = Date3.isEmpty();
	          if (!date) {
	        	  System.out.println("filas: "+Date3.getRowCount());
	        	  for (int i = 0;i < Date3.getRowCount();i++) {
	        	  location += Date3.getData(i, "LOCATION");
	        	  if(i < Date3.getRowCount()-1){
	        		  location += "','";
	        	  }
	        	  }  
	          } else {
	        	  testCase.addTextEvidenceCurrentStep("No se muestran registros que cumplan con el rango de fechas de ejecuci�n" +
	        			   " anteriores a " + CURRENT_DATE);
	          }
	          System.out.println(date);
	          testCase.addQueryEvidenceCurrentStep(Date3);
	          //assertFalse(date, "No se muestran registros que cumplan con el rango de fechas de ejecucion");

          }else {
          
        	  String datos = String.format(tdcDatosProc, LAST_EXEC_DATE, CURRENT_DATE, LAST_EXEC_DATE, CURRENT_DATE);		
        	  System.out.println(datos);
		
        	  SQLResult Date3 = executeQuery(dbRms, datos);		
	
	          boolean date = Date3.isEmpty();
	          if (!date) {
	        	  System.out.println("filas: "+Date3.getRowCount());
	        	  for (int i = 0;i < Date3.getRowCount();i++) {
	        	  location += Date3.getData(i, "LOCATION");
	        	  if(i < Date3.getRowCount()-1){
	        		  location += "','";
	        	  }
	        	  }  
	          } else {
	        	  testCase.addTextEvidenceCurrentStep("No se muestran registros que cumplan con el rango de fechas de ejecuci�n" 
	        			  + LAST_EXEC_DATE + " " + CURRENT_DATE);
	          }
	
	          System.out.println(date);
	          testCase.addQueryEvidenceCurrentStep(Date3);
	          //assertFalse(date, "No se muestran registros que cumplan con el rango de fechas de ejecucion");
          }
          
          
         
          
          
          //													PASO 3
          
          addStep("Validar la receta de cada articulo a enviar mediante la siguiente consulta (Filtrar por listado de locations obtenidas en el paso anterior:");
		
          System.out.println(GlobalVariables.DB_HOST_FCRMSMGR);
          
          String tdcDatosRecetasForm = String.format(tdcDatosRecetas, LAST_EXEC_DATE, CURRENT_DATE, LAST_EXEC_DATE, CURRENT_DATE, location);		
          System.out.println(tdcDatosRecetas);
	
          SQLResult recetas = executeQuery(dbRms, tdcDatosRecetasForm);

          boolean re = recetas.isEmpty();
          if (re) {
        	  testCase.addTextEvidenceCurrentStep("No existen registros de las recetas de los articulos");
          }

          System.out.println(re);
          testCase.addQueryEvidenceCurrentStep(recetas);
          //assertFalse(re, "La consulta no muestra ningun registro");		
		
		
	
          //													PASO 4
          
          //addStep("Comprobar que exista informacion necesaria para procesar en XXFC_MAESTRO_DE_CRS_V");
          
          addStep("Obtener el CRPlaza y CRTienda de la tabla XXFC.XXFC_MAESTRO_DE_CRS_V mediante la siguiente consulta:");
		
          System.out.println(GlobalVariables.DB_HOST_FCRMSMGR);
          String plaza = "";
          String tienda = "";
          String tdcTableMaestrosForm = String.format(tdcTableMaestros, location);		
          System.out.println(tdcTableMaestrosForm);
	
          SQLResult TableMaestros = executeQuery(dbEbs, tdcTableMaestrosForm);

          boolean ma = TableMaestros.isEmpty();
          if (!ma) {
        	  System.out.println("filas: "+TableMaestros.getRowCount());
        	  for (int i = 0;i < TableMaestros.getRowCount();i++) {
        	  plaza += TableMaestros.getData(i, "ORACLE_CR_SUPERIOR");
        	  tienda += TableMaestros.getData(i, "ORACLE_CR");
        	  System.out.println("VALOR DE I: "+i);
        	  if(i < TableMaestros.getRowCount()-1){
        		  plaza += "','";
        		  tienda +="','";
        	  }
        	  } 
          } else {
        	  testCase.addTextEvidenceCurrentStep("No existe registro en 'XXFC_MAESTRO_DE_CRS_V' con RETEK_CR = '" + location + "'");
          }
          

          System.out.println(ma);
          testCase.addQueryEvidenceCurrentStep(TableMaestros);
          //assertFalse(ma, "La tabla no contiene ningun registro");		
		
          
          
          //														PASO   5   
          
     	 addStep("Acceso a la BD de WM");
      	 testCase.addTextEvidenceCurrentStep("Base de Datos: FCWMESIT");
      	 testCase.addTextEvidenceCurrentStep("Host: " + GlobalVariables.DB_HOST_fcwmesit);
          
		
          //	
      	 
      	 
         //														PASO   6  
         
   	     addStep("Validar que no existan las CRPlaza's (NO MIGRADAS) en la tabla WMUSER.WM_OXXO_ROLLOUT_INTERFACES_V");
   	     
         System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
         
         String tdcTableWmOxxoRolloutForm = String.format(tdcTableWmOxxoRollout, plaza);		
         System.out.println(tdcTableMaestrosForm);
	
         SQLResult TableWmOxxoRollout = executeQuery(dbPos, tdcTableWmOxxoRolloutForm);

         boolean ro = TableWmOxxoRollout.isEmpty();
         if (ro) {
       	  testCase.addTextEvidenceCurrentStep("No existen registros en 'WM_OXXO_ROLLOUT_INTERFACES_V' con CR_PLAZA = '" + location + "'");
         }

         System.out.println(ro);
         testCase.addQueryEvidenceCurrentStep(TableWmOxxoRollout);
         //assertFalse(ro, "La tabla no contiene ningun registro");		
  	
		
         //                                                        PASO 5 
          
          addStep(" Comprobar los datos para conectarse al Buz�n de POS");
          
          System.out.println(FTPContention);

          SQLResult FTPContentionRes = executeQuery(dbPos, FTPContention);
	
          boolean ValidaFTPContention = FTPContentionRes.isEmpty();
          if (!ValidaFTPContention) {
        	  testCase.addQueryEvidenceCurrentStep(FTPContentionRes);
          }

          System.out.println(ValidaFTPContention);

          assertFalse(ValidaFTPContention, "No se encontraron los datos para conectarse al Buz�n de POS");
          

          //														PASO 6			
          
          
         
        
          
        addStep("Ejecutar la interface PR35");
         
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
          
          
          
          // 														PASO 7	

          //addStep("Comprobar que se registra la ejecucion en WMLOG");
          
          addStep("Validar que la ejecuci�n del JOB PR35 haya terminado exitosamente STATUS = 'S' mediante la siguiente consulta:");

          System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
          String fcwS = "";
          String queryStatusLog = String.format(tdcQueryStatusLog, run_id);          
          System.out.println(queryStatusLog);

          SQLResult StatusLog = executeQuery(dbLog, queryStatusLog);
          
          if (!StatusLog.isEmpty()) {
        	  fcwS = StatusLog.getData(0, "STATUS");
			} 
          //boolean validateStatus = status.equals(fcwS);
          
          testCase.addQueryEvidenceCurrentStep(StatusLog);                     
          //System.out.println(validateStatus);

          //assertTrue(validateStatus, "La ejecuci�n de la interfaz no fue exitosa");
          

          //	 													PASO 8

          //addStep("Se valida la generacion de threads");
          addStep("Verificar que se registren las ejecuciones de los threads en WMLOG");

          String queryStatusThread1 = String.format(tdcQueryStatusThread1, run_id);		
          String queryStatusThread2 = String.format(tdcQueryStatusThread2, run_id);	
          System.out.println(queryStatusThread1);

          SQLResult StatusThread1 = executeQuery(dbLog, queryStatusThread1);
          SQLResult StatusThread2 = executeQuery(dbLog, queryStatusThread2);
		
          boolean statusThread = StatusThread1.isEmpty();          
          if (!statusThread) {
        	  testCase.addTextEvidenceCurrentStep("SELECT thread_id, parent_id, name, start_dt, end_dt, status, att1, att2 FROM " + 
        	  		"wmlog.wm_log_thread WHERE parent_id = '2159086349'");
        	  testCase.addQueryEvidenceCurrentStep(StatusThread1, false);		
        	  testCase.addQueryEvidenceCurrentStep(StatusThread2, false);
          }else if(statusThread) {
        	  testCase.addTextEvidenceCurrentStep("No se generaron threads");
        	  testCase.addTextEvidenceCurrentStep("");
        	  testCase.addQueryEvidenceCurrentStep(StatusThread1, false);		
        	  testCase.addQueryEvidenceCurrentStep(StatusThread2, false);
        	  
          }
          
          System.out.println(statusThread);
          
//          assertFalse(statusThread, "No se generaron threads");
          

          //														PASO 9

          //addStep("Validar que se haya insertado los registro en la tabla POS_OUTBOUND_DOCS de los documentos enviados al servidor FTP");
          addStep(" Validar que el nombre del documento fue insertado en la tabla POS_OUTBOUND_DOCS de la BD de WM con STATUS='L' ,  sin la extenci�n del documento: "
          		+ "PCC + CR_PLAZA + CR_TIENDA + yyMMddHHmmss\r\n" + 
          		"Ejemplo:\r\n" + 
          		"PCC10MON50CUA030520183006");

          
          String PosOutBoundFormat1 = String.format(tdcDocPosOutbound1, plaza, tienda);		
          String PosOutBoundFormat2 = String.format(tdcDocPosOutbound2, plaza, tienda);	
          System.out.println(PosOutBoundFormat1);

          SQLResult PosOutBound1 = executeQuery(dbPos, PosOutBoundFormat1);
          SQLResult PosOutBound2 = executeQuery(dbPos, PosOutBoundFormat2);

          boolean doc = PosOutBound1.isEmpty();
          if (doc) { //!
        	  testCase.addTextEvidenceCurrentStep("No existen registros en 'POS_OUTBOUND_DOCS'con las plazas y tiendas procesadas");
          }
          
          testCase.addTextEvidenceCurrentStep("SELECT ID, POE_ID, TARGET_TYPE, DOC_NAME, DOC_TYPE, SENT_DATE, PV_CR_PLAZA, PV_CR_TIENDA, STATUS, SOURCE_ID FROM POSUSER.POS_OUTBOUND_DOCS" 
  				+ " WHERE SENT_DATE >= TRUNC(SYSDATE)"
  				+ " AND DOC_TYPE='PCC'"
  				+ " AND PV_CR_PLAZA IN ('"+plaza+"')"
  				+ " AND PV_CR_TIENDA IN ('"+tienda+"')"
  				+ " AND STATUS = 'L'"
  				+ " ORDER BY SENT_DATE DESC");
          testCase.addQueryEvidenceCurrentStep(PosOutBound1, false);
          testCase.addQueryEvidenceCurrentStep(PosOutBound2, false);
          System.out.println(doc);

          // assertFalse(doc, "No se encuentran registros en la tabla POS_OUTBOUND_DOCS");
	
          
          // 														PASO 10
          
          /*addStep("Validar que se inserto un nuevo registro de la ejecucion en la DB WMINT.");
          String RegistroPosFormat = String.format(RegistroPos, run_id);		
          System.out.println(RegistroPosFormat);
          SQLResult RegistroPosRes = executeQuery(dbPos, RegistroPosFormat);
          boolean validaRegistroPosRes = RegistroPosRes.isEmpty();
          if (validaRegistroPosRes) {
        	  testCase.addTextEvidenceCurrentStep("No existen registros en 'WM_DATES_EXEC' con RUN_ID = " + run_id);
          }
          
          testCase.addQueryEvidenceCurrentStep(RegistroPosRes);
          System.out.println(validaRegistroPosRes);*/
          //assertFalse(validaRegistroPosRes, "No se encuentran registros en la tabla WM_DATES_EXEC ");
	}	

	@Override
	public String setTestFullName() {
		return "PR35";
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "EQUIPO AUTOMATIZACION";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "";
	}

}