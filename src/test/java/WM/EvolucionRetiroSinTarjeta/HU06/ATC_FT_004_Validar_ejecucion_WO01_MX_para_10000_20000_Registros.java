package WM.EvolucionRetiroSinTarjeta.HU06;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;
import org.sikuli.script.FindFailed;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_004_Validar_ejecucion_WO01_MX_para_10000_20000_Registros extends BaseExecution{
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_Validar_ejecucion_WO01_MX_para_10000_20000_Registros_test(HashMap<String, String> data)
			throws Exception{

					/**
					 * Job runTPECopiaRST.sh Folder TQA_TRANSAC_ELEC / job runFT_SINTARJ_CNKTA.sh, runFT_SINTARJ_FTECH.sh, runFT_SINTARJ_HPAY.sh folder TQA_BAJO_DEMANDA / job runWO01_MX.sh folder TQA_TRANSAC_ELEC
					 * Desc:
					 * Validar ejecucion WO01_MX para 10000-20000 Registros
					 * @author Mónica Soto
					 * @date 12/02/2024
					 **/

					/*
					 * Utilerias
					 *********************************************************************/
					//BD FCWMREQA
					SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA, GlobalVariables.DB_USER_FCWMREQA,
							GlobalVariables.DB_PASSWORD_FCWMREQA);
					//BD FCTPEQA_S1_MTY
					SQLUtil dbFCTPEQA_S1_MTY = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
							GlobalVariables.DB_PASSWORD_FCTPE);
					
					//BD FCWMLOG
					SQLUtil dbFCWMLOG = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA_WMLOG2, GlobalVariables.DB_USER_FCWMLQA_WMLOG2,
							GlobalVariables.DB_PASSWORD_FCWMLQA_WMLOG2);
					
					//BD AVEBQA
					SQLUtil dbAVEBQA = new SQLUtil(GlobalVariables.DB_HOST_AVEBQA, GlobalVariables.DB_USER_AVEBQA,
												GlobalVariables.DB_PASSWORD_AVEBQA);
					/*
					 * Variables
					 ******************************************************************************************/
			
					/* variable Fecha_copiada */
					//por medio de una consulta, se obtiene el valor actual de la columna FECHA_COPIADA
					String consulta_FechaCopiada = "SELECT FECHA_COPIADA FROM TPEREP.TPE_TABLAS_COPIA WHERE TIPO IN('RST')";
					SQLResult fechaResult = executeQuery(dbFCWMREQA, consulta_FechaCopiada);
					
					//en el string va a guardar el valor obtenido de la consulta 
					String fechaCopiada = null;
					boolean info = fechaResult.isEmpty();
					if (!info) {
						fechaCopiada = fechaResult.getData(0, "FECHA_COPIADA");
					} else {
						System.out.println("No se tiene información");
					}
					
					// estos son los formatos de fecha requeridos, OLD_FORMAT es el formato con el que estoy recibiendo la consulta
					// NEW_FORMAT es el formato que se ocupa para las consultas que ocupan la fecha a copiar
					final String OLD_FORMAT = "yyyy-MM-dd HH:mm:ss";
					final String NEW_FORMAT = "dd/MM/yyyy";
			 
					// en este string se guardara la fecha_copiada anterior
					String oldDateString = fechaCopiada;
			 
					// aqui se le cambiará el formato deseado a fechaCopiada
					SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
					Date d = sdf.parse(oldDateString);
					sdf.applyPattern(NEW_FORMAT);
					fechaCopiada = sdf.format(d);
			 
					//sumatoria de 1 dia para el valor de "fechaCopiada"
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(format.parse(fechaCopiada));
					calendar.add(Calendar.DATE, 1);
					fechaCopiada = format.format(calendar.getTime());
			
					testCase.setTest_Description(data.get("id") + data.get("description"));
					testCase.setPrerequisites("La tabla XXFC_MPC_GL_TPC debe estar creada en la BD AVEBQA");
					
					System.out.println(
							"*****************************************PASO 1 y 2************************************************");
					
					String consulta_RBDTPE_FCTPEQA_S1_MTY = "SELECT COUNT(*) FROM TPEUSER.TPE_FR_TRANSACTION WHERE APPLICATION IN('MRC','MPC') AND OPERATION = 'TRN03' AND WM_CODE = '101'AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
					System.out.println("Consulta: " + consulta_RBDTPE_FCTPEQA_S1_MTY);
			
					SQLResult Execute_RBDTPE_Query = executeQuery(dbFCTPEQA_S1_MTY, consulta_RBDTPE_FCTPEQA_S1_MTY);
					
					boolean conexiondbFCTPEQA_S1_MTY  = true;
					conexiondbFCTPEQA_S1_MTY  = Execute_RBDTPE_Query.isEmpty();
					
					addStep("Ingresar a la BD de FCTPEQA_S1_MTY");
					testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA_S1_MTY ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCTPE);
					
					addStep("Realizar la siguiente Consulta para contabilizar el total de registros de MRC y MPC en Estatus OPERATION = 'TRN03', WM_CODE = '101' (Exitoso) del 'dd/mm/yy' (Fecha a Copiar)");
					testCase.addQueryEvidenceCurrentStep(Execute_RBDTPE_Query);
					assertFalse(conexiondbFCTPEQA_S1_MTY, "La conexion no fue exitosa");
					
					System.out.println(
							"*****************************************PASO 3 y 4************************************************");
					
					System.out.println("runTPECopiaRST");
					funcControlM("runTPECopiaRST", data);
					
					System.out.println(
							"*****************************************PASO 5 y 6************************************************");
					
					System.out.println("runFT_SINTARJ_CNKTA");
					funcControlM("runFT_SINTARJ_CNKTA", data);
							 
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
					System.out.println("runFT_SINTARJ_FTECH");
					funcControlM("runFT_SINTARJ_FTECH", data);
					
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					
					System.out.println("runFT_SINTARJ_HPAY");
					funcControlM("runFT_SINTARJ_HPAY", data);
					
					System.out.println(
							"*****************************************PASO 7 y 8************************************************");
					String consulta_RBDREP_dbFCWMREQA = "SELECT COUNT (*) From TPEREP.TPE_FR_TRANSACTION_RST WHERE  OPERATION = 'TRN03' AND WM_CODE = '101' AND WM_STATUS_WO01='L' AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
					System.out.println("Consulta: " + consulta_RBDREP_dbFCWMREQA);
			
					SQLResult Execute_RDBREP_Query = executeQuery(dbFCWMREQA, consulta_RBDREP_dbFCWMREQA);
					
					boolean conexiondbFCWMREQAREG  = true;
					conexiondbFCWMREQAREG  = Execute_RDBREP_Query.isEmpty();
					
					addStep("Ingresar a la BD de FCWMREQA");
					testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_TPEREP_DEV);
					
					addStep("Realizar la siguiente Consulta para contabilizar el total de registros de MRC y MPC en estatus OPERATION = 'TRN03', WM_CODE = '101' (Exitoso) y WM_STATUS_WO01='L'(Listo) del 'dd/mm/yy' (Fecha a Copiar) los cuales se estarian copiando a la Base de Datos AVEBQA mediante la interfaz WO01_MX.");
					testCase.addQueryEvidenceCurrentStep(Execute_RDBREP_Query);
			
					assertFalse(conexiondbFCWMREQAREG, "La conexion no fue exitosa");
					
					System.out.println(
							"*****************************************PASO 9, 10 y 11************************************************");
					
					System.out.println("runWO01_MX");
					funcControlM("runWO01_MX", data);
					
					System.out.println(
							"*****************************************PASO 12 y 13************************************************");
					
					// Se obtiene la fecha actual.
					SimpleDateFormat dateFormat = new SimpleDateFormat ("dd/MM/yy");				
					System.out.println(dateFormat.format(new Date()));
					String fechaEjecucion = dateFormat.format(new Date());
					
					String consulta_RBDREP_FCWMLOG = "SELECT RUN_ID, INTERFACE, START_DT, END_DT, STATUS, SERVER FROM WMLOG.WM_LOG_RUN WHERE INTERFACE='WO01_MX' AND rownum <= 10 AND TRUNC(START_DT)=TO_DATE ('"+fechaEjecucion+"', 'DD/MM/YY') order by START_DT desc";
					System.out.println("Consulta: " + consulta_RBDREP_FCWMLOG);
			
					SQLResult Execute_FCWMLOG_Query = executeQuery(dbFCWMLOG, consulta_RBDREP_FCWMLOG);
					
					boolean conexiondbFCWMLOG  = true;
					conexiondbFCWMLOG  = Execute_FCWMLOG_Query.isEmpty();
					
					addStep("Ingresar a la BD de FCWMLOG");
					testCase.addTextEvidenceCurrentStep("Conexion: FCWMLOG ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLQA_WMLOG2);
					
					addStep("Realizar la siguiente consulta para validar que la ejecucion del job runWO01_MX.sh se registro correctamente en estatus S(Successful) y obtener el ID del Campo RUN_ID.");
					testCase.addQueryEvidenceCurrentStep(Execute_FCWMLOG_Query);
			
					assertFalse(conexiondbFCWMLOG, "La conexion no fue exitosa");
					
					System.out.println(
							"*****************************************PASO 14************************************************");
					
					//en el string va a guardar el valor obtenido de la consulta 
					String runId = null;
					info = Execute_FCWMLOG_Query.isEmpty();
					if (!info) {
						runId = Execute_FCWMLOG_Query.getData(0, "RUN_ID");
					} else {
						System.out.println("No se tiene información");
					}
					
					System.out.println(runId);
					
					String consulta_Thread_FCWMLOG = "SELECT * FROM WMLOG.WM_LOG_THREAD WHERE PARENT_ID= "+runId+" AND rownum <= 10";
					System.out.println("Consulta: " + consulta_Thread_FCWMLOG);
			
					SQLResult Execute_FCWMLOG_Thread_Query = executeQuery(dbFCWMLOG, consulta_Thread_FCWMLOG);
					
					boolean conexiondbFCWMLOG_Thread  = true;
					conexiondbFCWMLOG_Thread  = Execute_FCWMLOG_Thread_Query.isEmpty();
					
					addStep("Ingresar a la BD de FCWMLOG");
					testCase.addTextEvidenceCurrentStep("Conexion: FCWMLOG ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMLQA_WMLOG2);
					
					addStep("Realizar la siguiente consulta en la tabla WM_LOG_THREAD para validar que se registraron los THREAD de la ejecucion WO01_MX con el ID RUN_ID del paso anterior.");
					testCase.addQueryEvidenceCurrentStep(Execute_FCWMLOG_Thread_Query);
			
					assertFalse(conexiondbFCWMLOG_Thread, "La conexion no fue exitosa");
					
					System.out.println(
							"*****************************************PASO 15 y 16************************************************");
					String consulta_runId_dbFCWMREQA = "SELECT COUNT (*) From TPEREP.TPE_FR_TRANSACTION_RST WHERE  OPERATION = 'TRN03' AND WM_CODE = '101' AND WM_STATUS_WO01='E' AND RUN_ID_WO01='"+runId+"' AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
					System.out.println("Consulta: " + consulta_runId_dbFCWMREQA);
			
					SQLResult Execute_runId_Query = executeQuery(dbFCWMREQA, consulta_runId_dbFCWMREQA);
					
					boolean conexiondbFCWMREQArunId  = true;
					conexiondbFCWMREQArunId  = Execute_runId_Query.isEmpty();
					
					addStep("Ingresar a la BD de FCWMREQA");
					testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_TPEREP_DEV);
					
					addStep("Realizar la siguiente consulta para contabilizar el total de registros en estatus WM_STATUS_WO01='E'(Enviado), RUN_ID_WO01='RUN_ID del Paso 13' del 'dd/mm/yy' (Fecha Copiada) con ello validando que se actualizaron correctamente los 20000-30000 despues de la ejecucion de la WO01_MX.");
					testCase.addQueryEvidenceCurrentStep(Execute_runId_Query);
			
					assertFalse(conexiondbFCWMREQArunId, "La conexion no fue exitosa");
					
					System.out.println(
							"*****************************************PASO 17 y 18************************************************");
					String consulta_dbdbAVEBQA = "SELECT COUNT (*) FROM XXFC.XXFC_MPC_GL_TPC WHERE PID_ID='"+runId+"' AND ATRIBUTO4='WO01'";
					System.out.println("Consulta: " + consulta_dbdbAVEBQA);
			
					SQLResult Execute_AVEBQA_Query = executeQuery(dbAVEBQA, consulta_dbdbAVEBQA);
					
					boolean conexiondbAVEBQA  = true;
					conexiondbAVEBQA  = Execute_AVEBQA_Query.isEmpty();
					
					addStep("Ingresar a la BD de AVEBQA");
					testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
					testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
					
					addStep("Realizar la siguiente consulta para contabilizar el total de registros copiados a la tabla XXFC_MPC_GL_TPC con los siguientes Campos: ATRIBUTO4: 'WO01' , PID_ID: 'RUN_ID del Paso 13' y ACCOUT_TYPE:'NA'.");
					testCase.addQueryEvidenceCurrentStep(Execute_AVEBQA_Query);
			
					assertFalse(conexiondbAVEBQA, "La conexion no fue exitosa");
				
			}

	public void funcControlM(String jobName, HashMap<String, String> data) {

		addStep("Ejecutar el job "+jobName);

		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		String pssDecrypted = PasswordUtil.decryptPassword(data.get("ps"));
		Control_mInicio cm = new Control_mInicio(u, data.get("user"), pssDecrypted);
		u.get(data.get("server"));

		u.waitForLoadPage();
		u.waitForLoadPage();
		cm.logOn();
		  
		JSONObject obj = new JSONObject(data.get(jobName)); 
		ControlM job= new ControlM(u, testCase, obj); 
		boolean flag = false;
		try {
			flag = job.searchJob();
		} catch (FindFailed e) {
			e.printStackTrace();
		} 
		
		assertTrue(flag);
			 
		String resultado1 = job.executeJob();
		System.out.println("Resultado de la ejecucion -> " + resultado1);
		System.out.println ("Valor de output :" +job.getOutput());

		assertEquals(resultado1, "Ended OK");
				
		job.closeViewpoint(); 
		u.quit();
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar ejecucion WO01_MX para 10000-20000 Registros";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_Validar_ejecucion_WO01_MX_para_10000_20000_Registros_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "La tabla XXFC_MPC_GL_CONFIG_COMI debe estar creada en la BD AVEBQA\r\n";
	}

	@AfterMethod
	public void afterMethod() {

	}
}
