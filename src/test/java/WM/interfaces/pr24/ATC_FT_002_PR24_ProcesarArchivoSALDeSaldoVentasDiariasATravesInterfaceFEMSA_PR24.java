package WM.interfaces.pr24;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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

public class ATC_FT_002_PR24_ProcesarArchivoSALDeSaldoVentasDiariasATravesInterfaceFEMSA_PR24 extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_PR24_ProcesarArchivoSALDeSaldoVentasDiariasATravesInterfaceFEMSA_PR24_test(HashMap<String, String> data) throws Exception {
		/**
		 * Proyecto: BackOffice (Regresion Enero 2023)
		 * Caso de prueba: MTC-FT-022-C1 PR24 Procesar archivo SAL de saldo de ventas diarias 
		 * a traves de la interface FEMSA_PR24.
		 * @author edwin.ramirez
		 * @date 2022/Nov/5
		 */
		
		
/*
* Utilerias
*********************************************************************/

		SQLUtil dbFCWM6QA_NUEVA = new utils.sql.SQLUtil(GlobalVariables.DB_HOST_FCWMQA_NUEVA,GlobalVariables.DB_USER_FCWMQA_NUEVA, GlobalVariables.DB_PASSWORD_FCWMQA_NUEVA);
		SQLUtil dbFCWMLQA_WMLOG = new SQLUtil(GlobalVariables.DB_HOST_FCWMLQA_WMLOG, GlobalVariables.DB_USER_FCWMLQA_WMLOG, GlobalVariables.DB_PASSWORD_FCWMLQA_WMLOG);
		SQLUtil dbFCRMSQA = new SQLUtil(GlobalVariables.DB_HOST_RMS_MEX,GlobalVariables.DB_USER_RMS_MEX,GlobalVariables.DB_PASSWORD_RMS_MEX);

/**
* Variables
* ******************************************************************************************
* 
*/
//Paso 2
		String consultaRegistro = "SELECT TO_CHAR(received_date, 'YY-MM-DD HH24:MI:SS') received_date, ID,PV_CR_PLAZA,PV_CR_TIENDA,PV_ENVELOPE_ID,NO_DOCUMENTS\r\n"
				+ "FROM POSUSER.POS_ENVELOPE\r\n"
				+ "WHERE RECEIVED_DATE >= TRUNC(SYSDATE)-3\r\n"
			//	+ "AND ROWNUM = 1"
				+ "AND  PV_CR_PLAZA='"+data.get("plaza")+"' "
			   + "AND PV_CR_TIENDA ='"+data.get("tienda")+"' ";
			//	+ "AND RECEIVED_DATE >= TO_DATE('"+data.get("RECEIVED_DATE")+"','dd/mm/yyyy hh24:mi:ss')"
			//	+ "AND ID='"+data.get("id")+"' \r\n"
	
				
//Paso 3 
		
		String consultaStatusI = "SELECT ID,PE_ID,PV_DOC_ID,STATUS,DOC_TYPE,TARGET_ID FROM POSUSER.POS_INBOUND_DOCS\r\n"
				+ "WHERE STATUS='I'\r\n"
				+ "AND DOC_TYPE='SAL'\r\n "
				+ "AND PE_ID ='%s'";
//Paso 4
		String registrosSAL = "SELECT PID_ID,NO_RECORDS,PV_FCH_MVT,NET_SAL,PARTITION_DATE\r\n"
				+ "FROM POSUSER.POS_SAL\r\n"
				+ "WHERE PID_ID = '%s'";
//Paso 5
		String registrosSAL2 = "SELECT PID_ID,PV_CVE_MVT,QTY,UNIT_RETAIL,ITEM,UOM\r\n"
				+ "FROM POSUSER.POS_SAL_DETL \r\n"
				+ "where PID_ID = '%s'";
//Paso 6
		Date fechaEjecucionInicio;
//Paso 8
		String statusE = "SELECT ID,PE_ID,PV_DOC_ID,STATUS,DOC_TYPE,TO_CHAR(RECEIVED_DATE, 'YY-MM-DD HH24:MI:SS') RECEIVED_DATE\r\n"
				+ "FROM POSUSER.POS_INBOUND_DOCS\r\n"
				+ "WHERE RECEIVED_DATE >=(select CAST( TO_TIMESTAMP ('%s','YYYY-MM-DD HH24:MI:SS.FF') AS DATE) from DUAL)\r\n"
				+ "AND DOC_TYPE='SAL'\r\n"
				+ "AND ID='%s'";
//Paso 10
		String detalleEjecucion = "SELECT RUN_ID,INTERFACE,START_DT,END_DT,STATUS\r\n"
				+ "FROM WMLOG.WM_LOG_RUN\r\n"
				+ "WHERE INTERFACE LIKE '%PR24' \r\n"
				+ "AND start_dt >= TRUNC(SYSDATE)\r\n"
				+ "ORDER BY START_DT DESC";
//Paso 11
		String consultaThread = "SELECT THREAD_ID,PARENT_ID,NAME,START_DT,END_DT,STATUS "
				+ "FROM WM_LOG_THREAD "
				+ "WHERE PARENT_ID = '%s'"
				+ "AND STATUS = S";
//Paso 12
		String consultaErrores = "SELECT ERROR_ID,RUN_ID,ERROR_DATE,SEVERITY,ERROR_TYPE FROM WM_LOG_ERROR "
				+ "WHERE RUN_ID = '%s'";
//Paso 13
		
		String consultaRTK_INBOUND_DOCS = "SELECT * FROM RTK_INBOUND_DOCS"
				+ "WHERE DOC_TYPE = 'SAL' "
				+ "AND STATUS = 'L'"
				+ "AND run_id = '%s'"; //WM_LOG_THREAD.THREAD_ID
/**
* 	
* **********************************Pasos del caso de Prueba *****************************************
* 
*/
				
//***************************************** Paso 1 y 2***************************************************************** 
			
		addStep("Buscar los registros enviados por XPOS para procesar en las tablas POS_ENVELOPE en BD FCWM6QA, para la plaza y tienda.");
		System.out.println("Paso 1 y 2: "+GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		System.out.println(consultaRegistro);
		
		SQLResult exe_consultaRegistro = executeQuery(dbFCWM6QA_NUEVA, consultaRegistro);
		
		boolean validaConsulta = exe_consultaRegistro.isEmpty();
		String id_POS_ENVELOPE = null ;//= data.get("id");
		String plaza = "";
		String tienda = "";
		String POS_ENVELOPE_Date = data.get("received_date");
		
		
		if (!validaConsulta) {
			id_POS_ENVELOPE = exe_consultaRegistro.getData(0, "ID"); //ID.POS_ENVELOPE
			plaza = exe_consultaRegistro.getData(0, "PV_CR_PLAZA");
			tienda = exe_consultaRegistro.getData(0, "PV_CR_TIENDA");
			
			testCase.addQueryEvidenceCurrentStep(exe_consultaRegistro);
		} 
		
		System.out.println("ID: "+  id_POS_ENVELOPE);
		System.out.println("Plaza : "+ plaza);
		System.out.println("Tienda : "+ tienda);
		
		System.out.println(validaConsulta);
		
		assertFalse(validaConsulta, "No existe informacion pendiente de procesar en la tabla."); 
		
//***************************************** Paso 3 ***************************************************************** 
		
		addStep("Validar con el PE_ID muestre el archivo SAL en STATUS='I', listo para procesar en la tabla POS_INBOUND_DOCS de la BD FCWM6QA.");
		System.out.println("Paso 3: "+GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		String consultaStatusIFormat = String.format(consultaStatusI, id_POS_ENVELOPE);
		System.out.println(consultaStatusIFormat);
		
		SQLResult exe_consultaStatusI = executeQuery(dbFCWM6QA_NUEVA, consultaStatusIFormat);
		testCase.addQueryEvidenceCurrentStep(exe_consultaStatusI);
		boolean validaConsultaStatusI = exe_consultaStatusI.isEmpty();
		String id_POS_INBOUND_DOCS = "";
		
		if (!validaConsultaStatusI) {
			id_POS_INBOUND_DOCS = exe_consultaRegistro.getData(0, "ID");
			
		} 
		
		System.out.println(validaConsultaStatusI);
		assertFalse(validaConsultaStatusI, "No existe informacion pendiente de procesar en la tabla."); 
		
//***************************************** Paso 4 ***************************************************************** 
		
		addStep(" Validar en la tabla POS_SAL de la BD FCWM6QA, muestre el numero de registro que contiene el archivo SAL.");
		System.out.println("Paso 4: "+GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		String registroSALFormat = String.format(registrosSAL, id_POS_INBOUND_DOCS);
		System.out.println(registroSALFormat);
		
		SQLResult exe_registroSAL = executeQuery(dbFCWM6QA_NUEVA,registroSALFormat);
		
		boolean validaRegistroSal = exe_registroSAL.isEmpty();
		
		if(!validaRegistroSal) {
			testCase.addQueryEvidenceCurrentStep(exe_registroSAL);
		}
		
		System.out.println(validaRegistroSal);
		assertFalse(validaRegistroSal, "No existe informacion pendiente de procesar en la tabla."); 
		
//***************************************** Paso 5 ***************************************************************** 
		
		addStep("Validar en la tabla POS_SAL_DETL de la BD FCWM6QA, muestre el numero de registro que contiene el archivo SAL");	
		System.out.println("Paso 5: "+GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		String registroSALFormat2 = String.format(registrosSAL2, id_POS_INBOUND_DOCS);
		System.out.println(registroSALFormat2);
		
		SQLResult exe_registroSAL2 = executeQuery(dbFCWM6QA_NUEVA,registroSALFormat2);
		
		boolean validaRegistroSal2 = exe_registroSAL2.isEmpty();
		
		if(!validaRegistroSal2) {
			testCase.addQueryEvidenceCurrentStep(exe_registroSAL2);
		}
		
		System.out.println(validaRegistroSal2);
		assertFalse(validaRegistroSal2, "No existe informacion pendiente de procesar en la tabla."); 
		
//***************************************** Paso 6 y 7 *****************************************************************
		
		   addStep("Ejecucion job : PR24v2 en  control-M");
	
		    SeleniumUtil u = new SeleniumUtil(new ChromeTest());
			
			JSONObject obj = new JSONObject(data.get("job"));

			testCase.addBoldTextEvidenceCurrentStep("Jobs en  Control M ");
			Control_mInicio CM = new Control_mInicio(u, data.get("user"), data.get("ps"));
			
			testCase.addBoldTextEvidenceCurrentStep("Login");
			u.get(data.get("server"));
			u.hardWait(50);
			u.waitForLoadPage();
			CM.logOn(); 

			//Buscar el job
			ControlM control = new ControlM(u, testCase, obj);
			testCase.addBoldTextEvidenceCurrentStep("Inicio de job ");		
			boolean flag = control.searchJob();
			assertFalse(flag, "No se encontro el job");
			
			//Ejecucion
			String resultado = control.executeJob();
			System.out.println("Resultado de la ejecucion -> " + resultado);

			u.hardWait(40);
			
			//Obtener status 
			String statusEjecucion = control.getNewStatus();
			System.out.println ("Valor de output getNewStatus:" +statusEjecucion);
			
			//Obtener output
			String output = control.getOutput();
			System.out.println ("Valor de output control:" +output);
			
			testCase.addTextEvidenceCurrentStep("Status de ejecucion: "+statusEjecucion);
			testCase.addTextEvidenceCurrentStep("Output de ejecucion: "+output);
			
			//Validacion de la ejecucion del job
			Boolean casoPasado = false;
			
			if(statusEjecucion.equals("Ended OK")) {
			casoPasado = true;
			}		
			
			control.closeViewpoint();
			
			assertTrue(casoPasado, "No termino correctamente");
			
			u.close();

		// Se obtiene la cadena de texto del data provider en la columna "jobs"
		// Se asigna a un array para poder manejarlo
	/*	JSONArray array = new JSONArray(data.get("cm_jobs"));

		testCase.addTextEvidenceCurrentStep("Ejecucion Job: " + data.get("cm_jobs"));
		SeleniumUtil u = new SeleniumUtil(new ChromeTest());
		Control_mInicio cm = new Control_mInicio(u, data.get("cm_user"), data.get("cm_ps"));

		testCase.addTextEvidenceCurrentStep("Login");
		addStep("Login");
		u.get(data.get("cm_server"));
		u.hardWait(40);
		u.waitForLoadPage();
		cm.logOn();

		testCase.addTextEvidenceCurrentStep("Inicio de job");
		JobManagement j = new JobManagement(u, testCase, array);
		u.hardWait(5);
		System.out.println(data.get("Espera 5 sec, antes de Resultado Ejecucion"));
		String resultadoEjecucion = j.jobRunner();

		// Abrir la herramienta de control M para validar que la ejecucion del job haya sido exitosa
		testCase.addTextEvidenceCurrentStep("Resultado de la ejecucion: " + resultadoEjecucion);
		System.out.println("Resultado de la ejecucion -> " + resultadoEjecucion);

		assertEquals(resultadoEjecucion, "Ended OK");
		
		u.close();*/
		
		
	//	  EL JOB PR24 ESTA FALLANDO A LA FECHA 30/11/2022
		 
		
		
//***************************************** Paso 8 *****************************************************************
		
		addStep("Validar que se haya actualizado el registro del archivo SAL en la tabla POS_INBOUND_DOCS con STATUS='E' y el nombre del archivo POSU[PLAZA][TIENDA][YYYYMMDDHHMISS].dat en el campo TARGED_ID.");	
		System.out.println("Paso 8: "+ GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		String statusEFormat = String.format(statusE, POS_ENVELOPE_Date, id_POS_ENVELOPE);
		System.out.println(statusEFormat);
		
		SQLResult exe_StatusE = executeQuery(dbFCWM6QA_NUEVA,statusEFormat);
		String status = "";
		System.out.println("El status es: "+status);
		
		boolean validaStatusE = exe_StatusE.isEmpty();
		testCase.addQueryEvidenceCurrentStep(exe_StatusE);
		
		if(!validaStatusE) {
			status = exe_StatusE.getData(0, "STATUS");		
		}
		
		System.out.println(validaStatusE);
		assertFalse(validaStatusE, "El status no fue E");
		
//***************************************** Paso 9 y 10 *****************************************************************
		addStep("Validar que se haya insertado el detalle de la ejecucion de la interface PR24 en la tabla WM_LOG_RUN de WMLOG con STATUS = 'S'");
		System.out.println("Paso 9 y 10: "+GlobalVariables.DB_HOST_FCWMLQA_WMLOG);
		System.out.println(detalleEjecucion);
		
		SQLResult exe_detalleEjecucion = executeQuery(dbFCWMLQA_WMLOG,detalleEjecucion);
		String statusEjecucionLog = "";
		String runID = "";
		System.out.println("El status es: "+ statusEjecucionLog);
		testCase.addQueryEvidenceCurrentStep(exe_detalleEjecucion);
		boolean validaDetalleEjecucion = exe_detalleEjecucion.isEmpty();
		boolean validaStatus = true;
		if(!validaDetalleEjecucion) {
			statusEjecucionLog = exe_detalleEjecucion.getData(0, "STATUS");
			runID = exe_detalleEjecucion.getData(0, "RUN_ID");
			System.out.println("El status es: "+statusEjecucionLog);
			System.out.println("El run id es: "+runID);
			
			if(statusEjecucionLog.equals("S")) {
				validaStatus = true;
				testCase.addTextEvidenceCurrentStep("La ejecucion fue exitosa");
			}
			if(statusEjecucionLog.equals("E"))
				validaStatus = false;
			testCase.addTextEvidenceCurrentStep("Se registro un error en la ejecucion");
			String consultaErroresFormat = String.format(consultaErrores,runID);
			System.out.println(consultaErroresFormat);			
			SQLResult exe_consultaErrores = executeQuery(dbFCWMLQA_WMLOG, consultaErroresFormat);
			testCase.addQueryEvidenceCurrentStep(exe_consultaErrores);
		}
		
		System.out.println(validaDetalleEjecucion);
		assertTrue(validaStatus, "La ejecucion no fue exitosa");
		
//***************************************** Paso 11 *****************************************************************
		addStep("Validar el logeo de los threads en la tabla WM_LOG_THREAD de WMLOG. El nombre del thread debe estar en el "
				+ "formato PR24_CR_PLAZA_CR_TIENDA_RUN_ID. Los thread lanzados deben terminar con STATUS = 'S'.");
		System.out.println("Paso 11 "+GlobalVariables.DB_HOST_FCWMLQA_WMLOG);
		String consultaThreadFormat = String.format(consultaThread,runID);
		System.out.println(consultaThreadFormat);
				
		SQLResult exe_consultaThread = executeQuery(dbFCWMLQA_WMLOG, consultaThreadFormat);
		String THREAD_ID = "";
		boolean validaConsultaThread = exe_consultaThread.isEmpty();
		testCase.addQueryEvidenceCurrentStep(exe_consultaThread);
				
		if (!validaConsultaThread) {	
			THREAD_ID = exe_consultaThread.getData(0, "THREAD_ID");
		    System.out.println("THREAD_ID: "+THREAD_ID);
		} 
				
		System.out.println(validaConsultaThread);
		assertFalse(validaConsultaThread, "No se registraron threads");
//***************************************** Paso 12 *****************************************************************
	/*	addStep("Validar que no haya registro de errores en la tabla WM_LOG_ERROR de FCWMLTAQ. ");
		System.out.println("Paso 12 "+GlobalVariables.DB_HOST_FCWMLQA_WMLOG);
		String consultaErroresFormat = String.format(consultaErrores,runID);
		System.out.println(consultaErroresFormat);
		
		SQLResult exe_consultaErrores = executeQuery(dbFCWMLQA_WMLOG, consultaErroresFormat);
		
		boolean validaConsultaErrores = exe_consultaErrores.isEmpty();
		
		if (!validaConsultaErrores) {
			testCase.addQueryEvidenceCurrentStep(exe_consultaErrores);
			} 
		
		System.out.println(validaConsultaErrores);
		
		assertTrue(validaRegistroSal2, "Existen errores en la tabla");*/
		
//***************************************** Paso 13 y 14 *****************************************************************
		
	addStep(" Se consulta la tabla RTK_INBOUND_DOCS para validar los documentos SAL depositados en el servidor "
				+ "FTP de RMS con STATUS = 'L' y el run id de cada registro es el thread que se lanzo por cada documento. ");
		
		System.out.println("Paso 9: "+ GlobalVariables.DB_HOST_FCWMQA_NUEVA);
		String RTK_INBOUND_DOCSFormat = String.format(consultaRTK_INBOUND_DOCS, runID);
		System.out.println(RTK_INBOUND_DOCSFormat);
		
		SQLResult RTK_INBOUND_DOCS_r = executeQuery(dbFCRMSQA,RTK_INBOUND_DOCSFormat);
		
		boolean ValidaRTK_INBOUND_DOCS = RTK_INBOUND_DOCS_r.isEmpty();
		testCase.addQueryEvidenceCurrentStep(RTK_INBOUND_DOCS_r );
		
		System.out.println(ValidaRTK_INBOUND_DOCS);
		assertFalse(ValidaRTK_INBOUND_DOCS, "No se registraron en RTK_INBOUND_DOCS los documentos SAL depositados en el servidor");
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Equipo automatizacion";
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

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

}
