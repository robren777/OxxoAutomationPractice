package WM.EvolucionRetiroSinTarjeta.HU08;
import static org.testng.Assert.assertFalse;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
/*@AzureTestInfo(
		areaPath = "I22032-Evolución Retiros sin Tarjeta",
		iterationPath = "I22032-Evolución Retiros sin Tarjeta",
		tags = "AUT",
		userStoryId = 6640
)*/
public class ATC_FT_005_Validar_llenado_de_la_tabla_POS_TPC_DETL_TMP extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_005_Validar_llenado_de_la_tabla_POS_TPC_DETL_TMP_test(HashMap<String, String> data)
	throws Exception{
		/**
		 * Plataforma de Oracle: Tener conexion a la BD FCWM6QA
		 * Desc: Validar llenado de la tabla POS_TPC_DETL_TMP
		 * @author Mario Garza
		 * @date 11/03/2024
		 **/
			
		/*
		 * Utilerias
		 *********************************************************************/
		SQLUtil dbFCWM6QA = new SQLUtil(GlobalVariables.DB_HOST_WM, GlobalVariables.DB_USER_WM,
				GlobalVariables.DB_PASSWORD_WM);
		/*
		 * Variables
		 ******************************************************************************************/

		testCase.setTest_Description(data.get("id") + data.get("description"));

		testCase.setPrerequisites("Acceso a base de datos FCWM6QA");
		
		System.out.println(
				"*****************************************PASO 1 ************************************************");
		//Se realiza la consulta
		addStep("Realizar la siguiente consulta para validar registros exitosos en la tabla POS_INBOUND_DOCS en STATUS=I(Insertado)");
		String consulta_FCWM6QA_POS_INBOUND_DOCS = "SELECT ID, PE_ID, PV_DOC_ID, STATUS, DOC_TYPE, PV_DOC_NAME FROM POSUSER.POS_INBOUND_DOCS WHERE STATUS = 'I' AND DOC_TYPE = 'TPC' and rownum <= 5";
		
		System.out.println("Consulta: " + consulta_FCWM6QA_POS_INBOUND_DOCS);
		//Se guarda en la variable
		SQLResult ExecuteQuery_POS_INBOUND_DOCS = executeQuery(dbFCWM6QA, consulta_FCWM6QA_POS_INBOUND_DOCS);

		boolean conexiondbFCWM6QA_POS_INBOUND_DOCS  = true;
		conexiondbFCWM6QA_POS_INBOUND_DOCS  = ExecuteQuery_POS_INBOUND_DOCS.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_POS_INBOUND_DOCS = ExecuteQuery_POS_INBOUND_DOCS.getRowCount();
		System.out.println(cantidad_Registros_POS_INBOUND_DOCS);
		//Se guarda en una lista de string la fila 1
		List<String> datos_POS_INBOUND_DOCS= ExecuteQuery_POS_INBOUND_DOCS.getRow(1);
		String id_POS_INBOUND_DOCS = datos_POS_INBOUND_DOCS.get(0);
		System.out.println(id_POS_INBOUND_DOCS);
		 
		boolean maximo_Registros = false;
		//Se valida sique la cantidad de regitros este de 1 o 5
		if (cantidad_Registros_POS_INBOUND_DOCS== 1 || cantidad_Registros_POS_INBOUND_DOCS == 5){ 
			maximo_Registros = true;
		}
		//Se toma la evidencia
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_POS_INBOUND_DOCS);
		//Se valida si la conexion  no fue exitosa
		assertFalse(conexiondbFCWM6QA_POS_INBOUND_DOCS, "La conexion no fue exitosa");
		
		System.out.println(
		"*****************************************PASO 2  ************************************************");
				//addStep("Ejecutar la interface PO21_OLD ");
		
		/*System.setProperty("webdriver.chrome.driver","C:\\Users\\GTIM\\Documents\\RespaldoMarioGarza\\DOCUMENTOS\\AutomatizaciónRST\\FrameworkAutomatización\\evolucion-rst\\Config\\Drivers\\Chrome\\Windows\\121.exe");  
		WebDriver driver=new ChromeDriver();  
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		u.setDriver(driver);
		Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));*/
		//System.setProperty("webdriver.chrome.driver","D:\\ChromeDriver\\chromedriver.exe");  
		//WebDriver driver =new ChromeDriver();  
		//instancia control m desde import utils.controlm.*; del FW
		//la modificacion se hace en el data provider, el excel
		
		//Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
		//u.get(data.get("server"));

		//espera de carga de pagina y login		
		//u.waitForLoadPage();
		//u.hardWait(20);
		//u.waitForLoadPage();
		//cm.logOn();

		//objeto json para pasar los datos para encontrar el job de control-M a utilizar  		  
		//JSONObject obj = new JSONObject(data.get("job")); 
		//ControlM job= new ControlM(u, testCase, obj); 
		//u.hardWait(5);
		//boolean flag = job.searchJob(); 
		
		//validar que el job se encontro
		//assertTrue(flag);
				
		//ejecucion del job y obtencion de resultados de la ejecucion en control-M		  
		//String resultado1 = job.executeJob();
		//System.out.println("Resultado de la ejecucion -> " + resultado1);
		//System.out.println ("Valor de output :" +job.getOutput());

		//resultado esperado para la finalizacion de ejecucion del job
		//assertEquals(resultado1, "Ended OK");
				
		// los hardwaits son waits
		//u.hardWait(3); 
		
		// cuando hace el view, y se termina se cierra la pantalla de vista pero no el job, aqui se cierra la consulta q se hizo 
		//job.closeViewpoint(); 
		//u.hardWait(15);
		
		
		System.out.println(
				"***************************************** PASO 3 ************************************************");
		//Se realiza la consulta
	    addStep("Realizar la siguiente consulta para validar el cambio de Status E(Enviado) al los registros después de la ejecución del Job runPO21_OLD");
	    String consulta_runPO21_OLD  = "SELECT ID, PE_ID, PV_DOC_ID, STATUS, DOC_TYPE, PV_DOC_NAME FROM POSUSER.POS_INBOUND_DOCS WHERE STATUS = 'E' AND DOC_TYPE = 'TPC' and rownum <= 5";
	    System.out.println("Consulta: " + consulta_runPO21_OLD);
	  //Se guarda en la variable
		SQLResult ExecuteQuery_runPO21_OLD = executeQuery(dbFCWM6QA, consulta_runPO21_OLD);
		
		boolean conexiondbFCWM6QA1_runPO21_OLD  = true;
		conexiondbFCWM6QA1_runPO21_OLD = ExecuteQuery_runPO21_OLD.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_runPO21_OLD = ExecuteQuery_runPO21_OLD.getRowCount();
		System.out.println(cantidad_Registros_runPO21_OLD);
		//Se guarda en una lista de string la fila 1
		List<String> datos_runPO21_OLD= ExecuteQuery_runPO21_OLD.getFirstRow();
		//Se guarda el id 3
		String id_runPO21_OLD= datos_runPO21_OLD.get(3);
		//String id_POS_TPC = datos_POS_INBOUND_DOCS.get(0);
		System.out.println("Consulta: " + id_runPO21_OLD);
		//Se toma la evidencia
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_runPO21_OLD);
		//Se valida si la conexion  no fue exitosa
		assertFalse(conexiondbFCWM6QA1_runPO21_OLD, "La conexion no fue exitosa");
		
		System.out.println(
				"***************************************** PASO 4 ************************************************");
		//Se realiza la consulta
	    addStep("Validar la tabla con la siguiente consulta que los datos se copiaron a la tabla POS_TPC_DETL_TMP después de la ejecución de la PO21_OLD");
	    String consulta_POS_TPC_DETL_TMP  = "SELECT PID_ID, FORMA_PAGO, FOLIO_WM, ACOUNT_TYPE, EMISOR_TYPE PV_DATE FROM POSUSER.POS_TPC_DETL_TMP WHERE rownum <= 5 ";
	    System.out.println("Consulta: " + consulta_POS_TPC_DETL_TMP);
	  //Se guarda en la variable
		SQLResult ExecuteQuery_POS_TPC_DETL_TMP = executeQuery(dbFCWM6QA, consulta_POS_TPC_DETL_TMP);
		
		boolean conexiondbFCWM6QA1_POS_TPC_DETL_TMP  = true;
		conexiondbFCWM6QA1_POS_TPC_DETL_TMP = ExecuteQuery_POS_TPC_DETL_TMP.isEmpty();
		//Se cuenta los registros de la fila
		int cantidad_Registros_POS_TPC_DETL_TMP = ExecuteQuery_POS_TPC_DETL_TMP.getRowCount();
		System.out.println(cantidad_Registros_POS_TPC_DETL_TMP);
		//Se guarda en una lista de string la fila 1
		List<String> datos_POS_TPC_DETL_TMP= ExecuteQuery_POS_TPC_DETL_TMP.getFirstRow();
		//Se guarda el valor 1 
		String id_POS_TPC_DETL_TMP = datos_POS_TPC_DETL_TMP.get(1);
		//String id_POS_TPC = datos_POS_INBOUND_DOCS.get(3);
		System.out.println("Consulta: " + id_POS_TPC_DETL_TMP);
		//Se toma la evidencia
		testCase.addTextEvidenceCurrentStep("Conexion: FCWMREQA");
		testCase.addTextEvidenceCurrentStep(GlobalVariables.DB_HOST_FCWMREQA);
		testCase.addQueryEvidenceCurrentStep(ExecuteQuery_POS_TPC_DETL_TMP);
		//Se valida si la conexion  no fue exitosa
		assertFalse(conexiondbFCWM6QA1_POS_TPC_DETL_TMP, "La conexion no fue exitosa");

	}

	@Override
	public void beforeTest() {
	}	

	@Override
	public String setTestDescription() {
		return "Validar llenado de la tabla POS_TPC_DETL_TMP";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_Validar_llenado_de_la_tabla_POS_TPC_DETL_TMP";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a base de datos FCWM6QA";
	}

	@AfterMethod
	public void afterMethod() {

	}

}
