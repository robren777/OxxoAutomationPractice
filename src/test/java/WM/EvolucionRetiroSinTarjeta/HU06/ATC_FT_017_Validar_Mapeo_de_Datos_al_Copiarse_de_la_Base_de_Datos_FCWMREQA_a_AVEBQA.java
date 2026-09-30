package WM.EvolucionRetiroSinTarjeta.HU06;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

public class ATC_FT_017_Validar_Mapeo_de_Datos_al_Copiarse_de_la_Base_de_Datos_FCWMREQA_a_AVEBQA extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_017_Validar_Mapeo_de_Datos_al_Copiarse_de_la_Base_de_Datos_FCWMREQA_a_AVEBQA_test(HashMap<String, String> data) throws Exception {

		/**
		 * Job runTPECopiaRST.sh Folder TQA_TRANSAC_ELEC / job runFT_SINTARJ_CNKTA.sh, runFT_SINTARJ_FTECH.sh, runFT_SINTARJ_HPAY.sh folder TQA_BAJO_DEMANDA / job runWO01_MX.sh folder TQA_TRANSAC_ELEC
		 * Desc:
		 * Validar Mapeo de datos al copiarse de la base de datos FCWMREQA a AVEBQA
		 * @author Mónica Soto
		 * @date 09/02/2024
		 **/

        //BD FCTPEQA_S1_MTY
        SQLUtil dbFCTPEQA_S1_MTY = new SQLUtil(GlobalVariables.DB_HOST_FCTPE, GlobalVariables.DB_USER_FCTPE,
                GlobalVariables.DB_PASSWORD_FCTPE);

        //BD FCWMREQA
        SQLUtil dbFCWMREQA = new SQLUtil(GlobalVariables.DB_HOST_FCWMREQA, GlobalVariables.DB_USER_FCWMREQA,
                GlobalVariables.DB_PASSWORD_FCWMREQA);

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

        System.out.println(
                "***************************************** PASO 1 y 2 ************************************************");
		System.out.println(fechaCopiada);

        String queryTPE_FR_TRANSACTION = "SELECT COUNT(*) FROM TPEUSER.TPE_FR_TRANSACTION WHERE APPLICATION IN('MRC','MPC') AND OPERATION = 'TRN03' AND WM_CODE = '101' AND ROWNUM <=10 AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
        SQLResult Execute_dbFCTPEQA_S1_MTY_Query = executeQuery(dbFCTPEQA_S1_MTY, queryTPE_FR_TRANSACTION);

        boolean conexionFCTPEQA_S1_MTY = true;
        conexionFCTPEQA_S1_MTY = Execute_dbFCTPEQA_S1_MTY_Query.isEmpty();

        addStep("Ingresar a la BD de FCTPEQA_S1_MTY");
        testCase.addTextEvidenceCurrentStep("Conexion: FCTPEQA_S1_MTY");
        testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCTPE);

        addStep("Se valida la cantidad de registros que se estarán procesando de la tabla TPE_FR_TRANSACTION");
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCTPEQA_S1_MTY_Query);
        assertFalse(conexionFCTPEQA_S1_MTY, "La conexion no fue exitosa");


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
                "***************************************** PASO 7 y 8 ************************************************");

        String queryTPE_FR_TRANSACTION_RST = "SELECT COUNT (*) FROM TPEREP.TPE_FR_TRANSACTION_RST WHERE OPERATION = 'TRN03' AND WM_CODE = '101' AND WM_STATUS_WO01='L' AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
        SQLResult Execute_dbFCWMREQA_Query = executeQuery(dbFCWMREQA, queryTPE_FR_TRANSACTION_RST);

        boolean conexionFCWMREQA = true;
        conexionFCWMREQA = Execute_dbFCWMREQA_Query.isEmpty();

        addStep("Ingresar a la BD de FCWMREQA");
        testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
        testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);

        addStep("Se ingresa a la Base de datos FCWMREQA para validar la conexion");
        testCase.addQueryEvidenceCurrentStep(Execute_dbFCWMREQA_Query);
        assertFalse(conexionFCWMREQA, "La conexion no fue exitosa");

    	System.out.println(
				"*****************************************PASO 9, 10 y 11************************************************");
		
		System.out.println("runWO01_MX");
		funcControlM("runWO01_MX", data);
		
        System.out.println(
				"*****************************************PASO 12 y 13************************************************");
		
		// Se obtiene la fecha actual.
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaEjecucion = date.format(formatter);
		
		System.out.println(fechaEjecucion);
		
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
		System.out.println(info);

		if (!info) {
			runId = Execute_FCWMLOG_Query.getData(0, "RUN_ID");
		} else {
			System.out.println("No se tiene información");
		}
		
		System.out.println(runId);
		
		String consulta_Thread_FCWMLOG = "SELECT THREAD_ID, PARENT_ID, NAME, START_DT, END_DT, STATUS FROM WMLOG.WM_LOG_THREAD WHERE PARENT_ID= "+runId+" AND rownum <= 10";
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
				"*****************************************PASO 17************************************************");
		
		String consulta_Validar_Mapeo_FCWMREQA = "SELECT SERVICE_ID,PLAZA,TIENDA,TRANS_DT,AMOUNT,TRACK2,FOLIO From TPEREP.TPE_FR_TRANSACTION_RST WHERE  OPERATION = 'TRN03' AND WM_CODE = '101' \r\n"
				+ "AND WM_STATUS_WO01='E' AND RUN_ID_WO01='"+runId+"' AND ROWNUM <= 10 AND TRUNC (CREATION_DATE) = TO_DATE ('"+fechaCopiada+"', 'DD/MM/YY')";
		
		SQLResult Execute_validar_Mapeo_Query = executeQuery(dbFCWMREQA, consulta_Validar_Mapeo_FCWMREQA);
		boolean conexiondbFCWMREQA_ValidarMapeo  = true;
		conexiondbFCWMREQA_ValidarMapeo  = Execute_validar_Mapeo_Query.isEmpty();
		
		addStep("Realizar la siguiente consulta para obtener los datos para validar la mapeo de los campos copiados por la WO01_MX y poder comprarla en pasos siguientes");
		testCase.addQueryEvidenceCurrentStep(Execute_validar_Mapeo_Query);
		
		assertFalse(conexiondbFCWMREQA_ValidarMapeo, "La conexion no fue exitosa");
		
		System.out.println(
				"*****************************************PASO 18, 19 y 20************************************************");
		
		addStep("Ingresar a la BD de AVEBQA");
		testCase.addTextEvidenceCurrentStep("Conexion: AVEBQA ");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_AVEBQA);
		
		addStep("Realizar la siguiente consulta para contabilizar el total de registros copiados a la tabla XXFC_MPC_GL_TPC con los siguientes Campos: ATRIBUTO4: 'WO01' , PID_ID: 'RUN_ID del Paso 13' ");
		
		String consulta_dbAVEBQA = "SELECT COUNT (*) FROM XXFC.XXFC_MPC_GL_TPC WHERE PID_ID='"+runId+"' AND ATRIBUTO4='WO01'";
		System.out.println("Consulta: " + consulta_dbAVEBQA);

		SQLResult Execute_dbAVEBQA_Query = executeQuery(dbAVEBQA, consulta_dbAVEBQA);
		boolean conexiondbAVEBQArunId  = true;
		conexiondbAVEBQArunId  = Execute_dbAVEBQA_Query.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(Execute_dbAVEBQA_Query);
		assertFalse(conexiondbAVEBQArunId, "La conexión no fue exitosa");
		
		addStep("Realizar la siguiente consulta para obtener los datos y validar la mapeo de los campos copiados por la WO01_MX comparándolos con los datos obtenidos en el paso 17");
		
		String consulta_dbAVEBQA_count = "SELECT FORMA_PAGO, CR_PLAZA, CR_TIENDA, ACCOUNTING_DATE, ENTERED_CR, FOLIO_TRANSFER, FOLIO_WM FROM XXFC.XXFC_MPC_GL_TPC WHERE PID_ID='"+runId+"' AND ATRIBUTO4='WO01' AND ROWNUM <= 10";
		System.out.println("Consulta: " + consulta_dbAVEBQA_count);
		
		SQLResult Execute_dbAVEBQA_Query_Count = executeQuery(dbAVEBQA, consulta_dbAVEBQA_count);
		boolean conexiondbAVEBQA_Count  = true;
		conexiondbAVEBQA_Count  = Execute_dbAVEBQA_Query_Count.isEmpty();
		
		testCase.addQueryEvidenceCurrentStep(Execute_dbAVEBQA_Query_Count);
		assertFalse(conexiondbAVEBQA_Count, "La conexión no fue exitosa");

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
				
		job.closeViewpoint(); 
		u.quit();
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {

		return "Validar Mapeo de datos al copiarse de la base de datos FCWMREQA a AVEBQA";
	}

	@Override
	public String setTestDesigner() {
		return "Mónica Soto";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_017_Validar_Mapeo_de_Datos_al_Copiarse_de_la_Base_de_Datos_FCWMREQA_a_AVEBQA_test";
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
