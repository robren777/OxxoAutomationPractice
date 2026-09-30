package WM.interfaces.pr26;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import WM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.FTPUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_008_VerificarLosDatosEnLaTablaPOS_OUTBOUND_DOCS_PRM extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_007_VerificarLosDatosEnLaTablaPOS_OUTBOUND_DOCS_PRM_test(HashMap<String, String> data) throws Exception {
		/*
		 * Utilerias
		 *********************************************************************/
		
		/**
		 * Proyecto multiformato
		 * Verificar que despues de correr la interface sean insertados correctamente los datos en la tabla POS_OUTBOUND_DOCS- PRM
		 * Verificar que se actualice el registro con que fue generado el archivo XML, en la tabla POS_ITM_PRM_HEAD en la columna WM_PRM_STATUS  con valor a "E"
		 */
		
		utils.sql.SQLUtil dbPOSUSER = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMQA_NUEVA,GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
	//	utils.sql.SQLUtil dbPOSUSER = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_fcwm6QA,GlobalVariables.DB_USER_fcwm6QA, GlobalVariables.DB_PASSWORD_fcwm6QA);
		SQLUtil dbEbs = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,GlobalVariables.DB_PASSWORD_AVEBQA);
		utils.sql.SQLUtil dbLOG = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMLQA,GlobalVariables.DB_USER_FCWMLQA, GlobalVariables.DB_PASSWORD_FCWMLQA);
		utils.sql.SQLUtil dbRms = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_RMS_MEX,GlobalVariables.DB_USER_RMS_MEX, GlobalVariables.DB_PASSWORD_RMS_MEX);

		
		String POS_ITM_PRM_HEAD_inicial = "select batch_id,location,archivo, wm_prm_status, archivo, LOAD_BATCH_ID\r\n"
				+ "from WMUSER.POS_PRM_HEAD\r\n"
				+ "where WM_PRM_STATUS = 'L'\r\n"
				+ "and rownum <=1\r\n"
				+ "and location = '" + data.get("location") + "'\r\n"
				+ "order by load_date desc";
		 
		String POS_ITM_PRM_HEAD_final = "select batch_id,location,archivo, wm_prm_status, archivo, LOAD_BATCH_ID\r\n"
				+ "from WMUSER.POS_PRM_HEAD \r\n"
				+ "where WM_PRM_STATUS = 'E'\r\n"
				+ "and location = '%s'\r\n"
				+ "and batch_id= '%s'\r\n"
				+ "and wm_target_prm like '%s'"
				+ "order by load_date desc";
		
		//Se obtiene el location y se busca en XXFC_MAESTRO_DE_CRS_V para obtener la plaza y tienda y poder mandarsela a POS_OUTBOUND_DOCS
		//Para poder obtener el nombre del documento insertado y verificarlo
		//Paso 4
		
		String plaza = "SELECT ORACLE_CR,ORACLE_CR_SUPERIOR, ORACLE_CR_TYPE, ESTADO, RETEK_CR, RETEK_ASESOR_NOMBRE\r\n"
				+ "FROM XXFC_MAESTRO_DE_CRS_V\r\n"
				+ "WHERE RETEK_CR ='%s'"; //location.POS_ITM_PRM_HEAD
		
		String POS_OUTBOUND_DOCS = "SELECT id, doc_name ,doc_type, sent_date\r\n"
				+ "FROM POSUSER.POS_OUTBOUND_DOCS\r\n"
				+ "WHERE DOC_TYPE = 'PRM'\r\n"
				+ "AND PV_CR_PLAZA = '%s'\r\n" //ORACLE_CR_SUPERIOR.XXFC_MAESTRO_DE_CRS_V
				+ "AND PV_CR_TIENDA = '%s'\r\n" //ORACLE_CR.XXFC_MAESTRO_DE_CRS_V
				+ "AND STATUS = 'L'\r\n"
				+ "AND DOC_NAME = '%s'" //PRM_TARGET.POS_ITM_PRM_HEAD
				+ "AND PARTITION_DATE >= trunc(SYSDATE)\r\n"
				+ "ORDER BY SENT_DATE DESC";
		
		
		String tdcIntegrationServerFormat = "SELECT run_id,interface,TO_CHAR(start_dt, 'YY-MM-DD HH24:MI:SS') start_dt, TO_CHAR(end_dt, 'YY-MM-DD HH24:MI:SS')end_dt, status, server\r\n"
				+ "FROM WMLOG.WM_LOG_RUN\r\n" 
				+ "WHERE INTERFACE = 'PR26_PRM'\r\n"
				+ "and start_dt >= TRUNC(SYSDATE)\r\n"
				+ "and start_dt  BETWEEN TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n"
				+ "and TO_DATE('%s','dd/mm/yyyy hh24:mi:ss')\r\n"
				+ "and run_id = '%s'\r\n"
				+ "ORDER BY START_DT DESC ";// Consulta para estatus de la ejecucion
		
		String consultaStatusR = "SELECT run_id,interface, TO_CHAR(start_dt, 'YY-MM-DD HH24:MI:SS') start_dt, TO_CHAR(end_dt, 'YY-MM-DD HH24:MI:SS')end_dt, status, server\r\n"
				+ "FROM WMLOG.WM_LOG_RUN\r\n"
				+ "WHERE INTERFACE = 'PR26_PRM'\r\n"
				+ "and start_dt >= TRUNC(SYSDATE)\r\n"
				+ "ORDER BY START_DT DESC";
	
		String consultaError = "select ERROR_ID, description,MESSAGE \r\n" +
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
		
// se obtiene la hora inicial***********************************************************************************
		fechaInicio = new Date();
					
		String fechaInicial = formatter.format(fechaInicio);
					
		System.out.println(fechaInicial);
		
		Thread.sleep(30000);

//*************************** Paso 1 *********************************************************************************
		
		addStep("Verificamos que exista en la tabla POS_ITM_PRM_HEAD con la columna WM_PRM_STATUS o WM_PRM_STATUS en \"L\".");
				
		System.out.println(POS_ITM_PRM_HEAD_inicial);
		SQLResult ITM1 = dbRms.executeQuery(POS_ITM_PRM_HEAD_inicial);
		
		String id = "";
		String loc= "";
		
		boolean  paso1 = ITM1.isEmpty();
		if (!paso1) {

			id = ITM1.getData(0, "BATCH_ID");
			System.out.println("BARCH_ID " + id);
			
			loc = ITM1.getData(0, "LOCATION");
			System.out.println("LOCATION: " + loc);
			
			
		}
		testCase.addQueryEvidenceCurrentStep(ITM1);
		assertFalse("No se  muestra la informacion pendiente por procesar para la tienda ", paso1);
		
//**********************************************************************************************************
		
		addStep("Se obtiene el location y se busca en XXFC_MAESTRO_DE_CRS_V para obtener la plaza y tienda para poder obtener el nombre del documento insertado y verificarlo");
		
		String XXFC_MAESTRO_DE_CRS_V = String.format(plaza, loc);
		System.out.println(XXFC_MAESTRO_DE_CRS_V);
		SQLResult XXFC_MAESTRO_DE_CRS_V_R = dbEbs.executeQuery(XXFC_MAESTRO_DE_CRS_V);
		
		String cr_plaza = "";
		String cr_tienda = "";

		boolean valida_XXFC_MAESTRO_DE_CRS_V = ITM1.isEmpty();
			
		if (!valida_XXFC_MAESTRO_DE_CRS_V) {

			cr_plaza = XXFC_MAESTRO_DE_CRS_V_R.getData(0, "ORACLE_CR_SUPERIOR");
			System.out.println("ORACLE_CR_SUPERIOR " + cr_plaza);
			
			cr_tienda = XXFC_MAESTRO_DE_CRS_V_R.getData(0, "ORACLE_CR");
			System.out.println("ORACLE_CR: " + cr_tienda);
			
			
		}
		
		testCase.addQueryEvidenceCurrentStep(XXFC_MAESTRO_DE_CRS_V_R);
		assertFalse("No se muestra informacion con esa plaza y tienda ", valida_XXFC_MAESTRO_DE_CRS_V);

//*********************** Paso 2 *********************************************************************************
		
		addStep("Ejecutar el servicio "+ data.get("servicio") );
	
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
				
				System.out.println("RUN_ID: "+run_id);

				boolean valuesStatus = status.equals(statusR);// Valida si se encuentra en estatus R
				while (valuesStatus) {
					
					is = executeQuery(dbLOG, consultaStatusR);

					status = is.getData(0, "STATUS");
					run_id = is.getData(0, "RUN_ID");
					valuesStatus = status.equals(statusR);

					u.hardWait(2);

				}
				
				Thread.sleep(30000);
				
				
//Se obtiene la hora final **************************************************************************************************
				
				 fechaFin = new Date();
								
				 String fechaFinal = formatter.format(fechaFin);
								
				 System.out.println(fechaFinal);
				 
//Valida si la ejecucion fue exitosa *******************************************************************************************
				 	
				 String queryFormat = String.format(tdcIntegrationServerFormat,fechaInicial, fechaFinal, run_id );
						
				 SQLResult validaLogs = executeQuery(dbLOG, queryFormat);
						
				 System.out.println(queryFormat);	
					
				 testCase.addQueryEvidenceCurrentStep(validaLogs);
					
				 boolean validateStatus = status.equals(statusEsperado); //Valida si el status es S, sino verifica en la tabla de errores
					
				 boolean validaConsultaError_f = true;
						
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
						
						
						
		assertTrue(validaConsultaError_f, "La ejecucion de la interfaz no fue exitosa");
					
//***********************************************************************************************************************
		
		addStep("Validar que se actualice el estatus y el nombre del archivo generado por la interface de los registros procesados en la tabla POS_ITM_PRM_HEAD de RETEK");
		
		String wm_target_prm = "";
		String ITM_Format = String.format(POS_ITM_PRM_HEAD_final,loc,id , "'%xml'");
		
		System.out.println(ITM_Format);
		ITM1 = dbRms.executeQuery(ITM_Format);
		boolean ValidaITM1 = ITM1.isEmpty();
		testCase.addQueryEvidenceCurrentStep(ITM1);
		
		if(!ValidaITM1) {
			
			wm_target_prm = ITM1.getData(0, "WM_TARGET_PRM");
			
			System.out.println("WM_TARGET_PRM" + wm_target_prm);
						
			}
		
		
		assertFalse("No se actualizo el estatus de los registros procesados en la tabla POS_ITM_PRM_HEAD de RETEK ", ValidaITM1);
				
//********************** Paso 3 ***************************************************************************************************
		
		addStep(" Validar que se inserte el registro del documento ITM generado por la interface en la tabla POS_OUTBOUND_DOCS de POSUSER.");

       String consultaDoc = String.format(POS_OUTBOUND_DOCS, cr_plaza, cr_tienda,wm_target_prm);
		
		System.out.println(consultaDoc);
		
		SQLResult consultaDoc_Res = dbPOSUSER.executeQuery(consultaDoc);
		
   
		boolean pasoL = consultaDoc_Res.isEmpty();
		if (!pasoL) {
			testCase.addQueryEvidenceCurrentStep(consultaDoc_Res);
		}
		testCase.addQueryEvidenceCurrentStep(consultaDoc_Res);
		System.out.println(pasoL);
		
		
		
	assertFalse("No se inserta el detalle del documento generado por la interface en la tabla POS_OUTBOUND_DOCS de POSUSER.", pasoL);
		
//***************************Paso 4 *************************************************************************************

	           String plazaTienda = cr_plaza+ cr_tienda;
	           
		       Thread.sleep(20000);
		
				addStep(" Validar que se realice el envio del archivo ITM generado por la interface en el directorio configurado para la tienda procesada ");
				
		       FTPUtil ftp = new FTPUtil(" 10.182.92.92",7787,plazaTienda,plazaTienda);
		       
		       Thread.sleep(20000);
		       String ruta = "/FEMSA_OXXO/POS/"+ cr_plaza +"/"+ cr_tienda +"/working/" + wm_target_prm;
		                                 //host, puerto, usuario, contrasena
		       ///u01/posuser
		       boolean validaFTP;
		       
		        if (ftp.fileExists(ruta) ) {
		        	
		        	validaFTP = true;
		            testCase.addFileEvidenceCurrentStep(ruta);
		            System.out.println("Existe");
		            testCase.addBoldTextEvidenceCurrentStep("El archivo si existe ");
		            testCase.addBoldTextEvidenceCurrentStep(ruta);
		            
		        }else {
		        	testCase.addFileEvidenceCurrentStep(ruta);
		        	testCase.addBoldTextEvidenceCurrentStep("El archivo no existe ");
		            System.out.println("No Existe");
		            validaFTP = false;
		        }
		        
		        assertTrue("No se encontro el archivo xml en POSUSER ", validaFTP);
		        

	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Verificar que despues de correr la interface sean insertados correctamente los datos en la tabla POS_OUTBOUND_DOCS - PRM";
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
