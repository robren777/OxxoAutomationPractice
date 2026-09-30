package WM.EvolucionRetiroSinTarjeta.HU08;
import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import modelo.BaseExecution;

public class ATC_FT_006_Ejecutar_Jobs_para_llenado_de_tablas_PO21_POS_TPC_DETL_TMPFACT_CONSILIACION_RST extends BaseExecution {
	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_Ejecutar_Jobs_para_llenado_de_tablas_PO21_POS_TPC_DETL_TMPFACT_CONSILIACION_RST_test(HashMap<String, String> data)
	throws Exception{
		/**
		 * Plataforma de Control-M: Se ejecutan los JOBs
		 * Desc: Ejecutar Jobs para llenado de tablas PO21_POS_TPC_DETL_TMPFACT_CONSILIACION_RST
		 * @author Mario Garza
		 * @date 11/03/2024
		 **/
	
		testCase.setTest_Description(data.get("id") + data.get("description"));

		testCase.setPrerequisites("Acceso a base de datos FCWM6QA");
		
		
		System.out.println(
				"*****************************************PASO 1  ************************************************");
		addStep("Ejecutar el JOB ODS_MAP_WM2ODS_POS_TOC_DETL_TMP_010");
		
		/*System.setProperty("webdriver.chrome.driver","C:\\Users\\GTIM\\Documents\\RespaldoMarioGarza\\DOCUMENTOS\\AutomatizaciónRST\\FrameworkAutomatización\\evolucion-rst\\Config\\Drivers\\Chrome\\Windows\\121.exe");  
		WebDriver driver=new ChromeDriver();  
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		u.setDriver(driver);
		Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
		u.get(data.get("server"));*/
		//System.setProperty("webdriver.chrome.driver","D:\\ChromeDriver\\chromedriver.exe");  
		//WebDriver driver=new ChromeDriver(); 
		//--------------------------------------------------------------------------
		//instancia control m desde import utils.controlm.*; del FW
		//la modificacion se hace en el data provider, el excel
		//SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		//Control_mInicio cm = new Control_mInicio(u, data.get("user"), data.get("ps"));
		//u.get(data.get("server"));

		//espera de carga de pagina y login		
		//u.waitForLoadPage();
		//u.hardWait(20);
		//u.waitForLoadPage();
		//cm.logOn();

		//objeto json para pasar los datos para encontrar el job de control-M a utilizar  		  
		//JSONObject obj = new JSONObject(data.get("ODS_MAP_WM2ODS_POS_TOC_DETL_TMP_010")); 
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
				"*****************************************PASO 2  ************************************************");
		addStep("Ejecutar el JOB ODS_MAP_ODS2ODS_FACT_CONS_RST_POS_020 ");
		
		//System.setProperty("webdriver.chrome.driver","C:\\Users\\GTIM\\Documents\\RespaldoMarioGarza\\DOCUMENTOS\\AutomatizaciónRST\\FrameworkAutomatización\\evolucion-rst\\Config\\Drivers\\Chrome\\Windows\\121.exe");  
		//WebDriver driver_ODS = new ChromeDriver();  
		//SeleniumUtil u_ODS = new SeleniumUtil(new ChromeTest(), true);
		//u_ODS.setDriver(driver_ODS);
		//Control_mInicio cm_ODS = new Control_mInicio(u_ODS, data.get("user"), data.get("ps"));
		//u_ODS.get(data.get("server"));
		//System.setProperty("webdriver.chrome.driver","D:\\ChromeDriver\\chromedriver.exe");  
		//WebDriver driver=new ChromeDriver();  
		//---------------------------------------------------------------------------
		//instancia control m desde import utils.controlm.*; del FW
		//la modificacion se hace en el data provider, el excel
		//SeleniumUtil u_ODS = new SeleniumUtil(new ChromeTest(), true);
		//Control_mInicio cm_ODS = new Control_mInicio(u, data.get("user"), data.get("ps"));
		//u_ODS.get(data.get("server"));

		//espera de carga de pagina y login		
		//u_ODS.waitForLoadPage();
		//u_ODS.hardWait(20);
		//u_ODS.waitForLoadPage();
		//cm_ODS.logOn();

		//objeto json para pasar los datos para encontrar el job de control-M a utilizar  		  
		//JSONObject obj_ODS = new JSONObject(data.get("ODS_MAP_ODS2ODS_FACT_CONS_RST_POS_020")); 
		//ControlM job_ODS= new ControlM(u_ODS, testCase, obj_ODS); 
		//u_ODS.hardWait(5);
		//boolean flag_ODS = job_ODS.searchJob(); 
		
		//validar que el job se encontro
		//assertTrue(flag_ODS);
				
		//ejecucion del job y obtencion de resultados de la ejecucion en control-M		  
		//String resultado_ODS = job_ODS.executeJob();
		//System.out.println("Resultado de la ejecucion -> " + resultado_ODS);
		//System.out.println ("Valor de output :" + job_ODS.getOutput());

		//resultado esperado para la finalizacion de ejecucion del job
		//assertEquals(resultado_ODS, "Ended OK");
				
		// los hardwaits son waits
		//u_ODS.hardWait(3); 
		
		// cuando hace el view, y se termina se cierra la pantalla de vista pero no el job, aqui se cierra la consulta q se hizo 
		//job_ODS.closeViewpoint(); 
		//u_ODS.hardWait(15);
		

	}

	@Override
	public void beforeTest() {
	}	

	@Override
	public String setTestDescription() {
		return "Ejecutar Jobs para llenado de tablas PO21_POS_TPC_DETL_TMPFACT_CONSILIACION_RST";
	}

	@Override
	public String setTestDesigner() {
		return "Mario.garza@grupotimexico.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_Ejecutar_Jobs_para_llenado_de_tablas_PO21_POS_TPC_DETL_TMPFACT_CONSILIACION_RST";
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
