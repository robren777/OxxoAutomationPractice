package WM.interfaces.ol9;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class OL9_Nucleo_Validar_informaciondeservicios_correctos_cancelados extends BaseExecution {
	private char[] tdcQueryStatusLog;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_OL9_Nucleo_Validar_informaciondeservicios_correctos_cancelados(HashMap<String, String> data) throws Exception { 
		
		//BD
		//utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_EBS, GlobalVariables.DB_USER_EBS, GlobalVariables.DB_PASSWORD_EBS);
		utils.sql.SQLUtil dbLog = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA, GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		utils.sql.SQLUtil dbEBS = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCDASPFR, GlobalVariables.DB_USER_FCDASPFR, GlobalVariables.DB_PASSWORD_FCDASPFR);
		
		
		//Utileria
//		String tdcQueryEstatusNull = "SELECT * FROM RMS10_V.V_XXFC_RETEK_ITEM_SERVICIOS; "
//				+ " from  XXFC.XXFC_PAGO_SERVICIOS_PRE" + 
//				" WHERE SERVICIO =" +"'"+ data.get("service_id")+"'"+
//				" AND PLAZA ="+"'" + data.get("PLAZA")+"'"+
//				" AND TIENDA = "+"'"+ data.get("TIENDA")+"'"+
//				" AND ESTATUS IS NULL";
		
		String tdcQueryProveedore = "SELECT ITEM,ITEM_DESC,SUPPLIER,SITE,ES_SERVICIO,TIPO_DE_SERVICIO,SERVICIO_ACEPTA_MPE"
				+ " FROM RMS10_V.V_XXFC_RETEK_ITEM_SERVICIOS ";
	
		String consultaERROR = " select * from (Select ERROR_ID, RUN_ID, ERROR_DATE, SEVERITY,ERROR_TYPE, ERROR_CODE "
				+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s') where rownum <=1";// Consulta para los errores
				String consultaERROR2 = " select * from (select description,MESSAGE " + "from wmlog.WM_LOG_ERROR "
				+ "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
				String consultaERROR3 = " select * from (select PROC_ROLE, PROC_STATUS,CR_PLAZA, THREAD_ID,ATT1 "
				+ "from wmlog.WM_LOG_ERROR " + "where RUN_ID='%s')WHERE rownum <= 1";// Consulta para los errores
				
				String tdcQueryStatusLog = "select * from (SELECT run_id,interface,start_dt,END_DT,status,server "
						+ " FROM WMLOG.wm_log_run" + " WHERE interface = 'OL9'"
						+ " and start_dt >= trunc(sysdate) " // FCWMLQA
						+ " ORDER BY start_dt DESC)" + " where rownum = 1";
				
				String tdcQueryIntegrationServer = "select * from ( SELECT run_id,start_dt,end_dt,status"
						+ " FROM WMLOG.wm_log_run"
						+ " WHERE interface = 'OL9'"
						+" and start_dt >= trunc(SYSDATE)"
						+" ORDER BY START_DT DESC) where rownum <=1";
				
				String tdcQueryStatusThread = "SELECT parent_id,thread_id,name,START_DT, END_DT,status,att1,att2 "
						+ " FROM WMLOG.wm_log_thread "
						+ " WHERE parent_id = '%s'" ; //FCWMLQA
	
		String user = data.get("user");
		String ps = PasswordUtil.decryptPassword(data.get("ps"));
		String server = data.get("server");
		String con = "http://" + user + ":" + ps + "@" + server + ":5555";
		String searchedStatus = "R";
		String run_id;
		String id;
		String thread;
		
		
	
		
		String status = "S";
		
			
		testCase.setProject_Name("Configuracion de ambientes.");
		

		/////////////////// PASOS ///////////////////////////
			addStep("RESUMEN"); 
	
			// *********************************************************************************************************************************
			//** PASO 1 *************************************************************************************************************

			testCase.addBoldTextEvidenceCurrentStep("Conectarse a la base de datos.");
					
					testCase.addTextEvidenceCurrentStep("Conexion a bd correcta: " + GlobalVariables.DB_HOST_FCDASPFR + " usuario: "
							+ GlobalVariables.DB_USER_FCDASPFR);
					testCase.addTextEvidenceCurrentStep("Conexion exitosa: " + dbEBS.getConn());
			
		
	 	//** PASO 2 *************************************************************************************************************			
				
				
				addStep("Se valida que tenga informaci�n de servicios proveedores que correspondan	.");


				System.out.println(GlobalVariables.DB_HOST_FCDASPFR);		
				
			System.out.println(tdcQueryProveedore);
			SQLResult prov = dbEBS.executeQuery(tdcQueryProveedore);
			boolean proveE = prov.isEmpty();
			
				if (!proveE) {
					
					testCase.addQueryEvidenceCurrentStep(prov);    
					
					
				}
			
		
		
	//Paso 3 ***********************************************************************************************
		addStep("Invocar la interface OL9.pub:runAllServices mediante la ejecuci�n del JOB runOL9HH2 para enviar la informaci�n de los pagos nacionales a los Proveedores");

				//utileria

				SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
				PakageManagment pok = new PakageManagment(u, testCase);
				String contra = "http://" + user + ":" + ps + "@" + server + ":5555";
				u.get(contra);
				String dateExecution = pok.runIntefaceWmOneButton10(data.get("interfase"), data.get("servicio"));
				System.out.println("Respuesta dateExecution" + dateExecution);
				SQLResult is = executeQuery(dbLog, tdcQueryIntegrationServer);
				String status1 = is.getData(0, "STATUS");
				run_id = is.getData(0, "RUN_ID");
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
					System.out.println(error);
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
					
	//Paso 4 ***********************************************************************************************
		addStep ("Comprobar que se registra la ejecucion en WMLOG");
					
					System.out.println(GlobalVariables.DB_HOST_FCWMLQA);
					
					System.out.println(tdcQueryStatusLog);
					
					
					SQLResult StatusLog = executeQuery(dbLog, tdcQueryStatusLog);
					
					boolean ValidTab = StatusLog.isEmpty();
	                String fcws ="";
					if (!ValidTab) {
					fcws = StatusLog.getData(0,"STATUS");
					run_id = StatusLog.getData(0,"run id");
					}
					
					boolean validateStatus= status.equals(fcws);
					if(!validateStatus) {
					testCase.addTextEvidenceCurrentStep("El estatus de ejecucion no termino en S");
					}
					
					testCase.addQueryEvidenceCurrentStep(StatusLog);
					System.out.print(validateStatus);
				
	}
				
					
		//Paso 5 ***********************************************************************************************
		
				addStep ("Verificar que se registren las ejecuciones de los threads en WMLOG") ;
				
					System.out.println (GlobalVariables.DB_HOST_FCWMLQA) ;
					
							String queryStatusThread = String.format(tdcQueryStatusThread,run_id);
							System.out.println(queryStatusThread);
								
							SQLResult StatusThread = executeQuery(dbLog, queryStatusThread);
								
							boolean statusThread = StatusThread.isEmpty();
							if(statusThread) {
							testCase.addTextEvidenceCurrentStep("No se generaron threads \n"+queryStatusThread);
							
							}	
							testCase.addQueryEvidenceCurrentStep(StatusThread);
						System.out.println(StatusThread);
	}


	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		return "tbd";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_OL9_OL9PreRequisitos";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
