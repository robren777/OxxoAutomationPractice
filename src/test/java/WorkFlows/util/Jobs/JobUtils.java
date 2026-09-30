package WorkFlows.util.Jobs;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.sikuli.script.FindFailed;

import WorkFlows.util.GlobalVariables;
import modelo.TestCase;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.controlm.pageObject.Monitoring;
import utils.selenium.SeleniumUtil;


public class JobUtils {
	
private HashMap<String, JSONObject> jobs;
	
	private SeleniumUtil seleniumUtil;
	private TestCase testCase;
	
	private String usuarioControlM;
	private String passControlM;
	
	 private Integer timeOut;
	    private Boolean depend=false;
	    private Boolean release=false;
	    private String currentStatus;
	    private String newStatus;
	    private String output;
	    private  JSONArray actionsList=null;
	    
	private boolean loggedIn;
	
	
	By editViewPoint = By.xpath("//*[@id=\"EditViewPoint\"]");
	By edit = By.cssSelector("#btnViewpointEditorAddFields");
	By chkOrderId = By.xpath("/html/body/modal-container[2]/div/div/viewpoint-editor-add-fields/div/div[2]/div[3]/div[2]/div/div[4]/div/label/span");
	By inputDone = By.cssSelector("#btnViewpointAddFieldsCancel");
	By inputOrderId = By.xpath("//*[@id=\"filter_ORDER_ID\"]/div/div/input");
	By inputOk = By.cssSelector("#btnViewpointEditorOpen");
	By ciclik = By.xpath("/html/body/modal-container[2]/div/div/viewpoint-editor-add-fields/div/div[2]/div[2]/div[2]/div/div[10]/div/label/span");

	By loadCtrlM = By.cssSelector("span.busy-indicator");
	
	
	/**
	 * @param seleniumUtil Objeto para manejar el navegador.
	 * @param testCase Usado por el objeto JobManagment.
	 * @param dataProvider Usado para obtener los jobs y credenciales de Control-M.
	 */
	public JobUtils(SeleniumUtil seleniumUtil, TestCase testCase, HashMap<String, String> dataProvider, String fileName) {
		this.seleniumUtil = seleniumUtil;
		this.testCase = testCase;		
		this.usuarioControlM = GlobalVariables.CtrlM_User_EsmeG;
		this.passControlM = GlobalVariables.CtrlM_Pss_EsmeG;		
		this.jobs = this.crearJobs(fileName);
		this.loggedIn = false;
	}	
	
	/**
	 * Llama JobsLoader.loadJobs() que lee los jobs de un excel 
	 * con la informacion de los jobs. 
	 * @return HashMap<String JSONObject>  
	 * @throws IOException 
	 */
	private HashMap<String, JSONObject> crearJobs(String fileName) {		
		try {
			return JobsLoader.loadJobs(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Inicia sesion en Control-M, cambia this.loggedIn a true para evitar iniciar sesion cada vez que se ejecuta
	 * ejecutar(String nombreJob) 
	 */
	private void iniciarSesionControlM() {
		
		By inputPass = By.id("login-user-password");
		
		testCase.addBoldTextEvidenceCurrentStep("Inicia Sesion en Control-M");
		this.seleniumUtil.getDriver().manage().deleteAllCookies();
		this.seleniumUtil.get(GlobalVariables.CtrlM_URL);
		loadingCtrlM();
		Control_mInicio cm = new Control_mInicio(this.seleniumUtil, this.usuarioControlM, this.passControlM);
		loadingCtrlM();
		this.seleniumUtil.waitWebElement(inputPass, 160, 3);
	
        testCase.addScreenShotCurrentStep("Inicia Sesion");
        cm.logOn(); // Inicia sesion en control-m
        loadingCtrlM();
        testCase.addScreenShotCurrentStep("Pantalla principal Control-M");

        this.loggedIn = true;
	}
	
	/**
	 * Obtiene un job del HashMap.
	 * @param nombreJob Nombre del job a obtener.
	 * @return JSONArray con informacion del job especifico.
	 */
	private JSONObject getJob(String nombreJob) {
		return this.jobs.get(nombreJob.toLowerCase());
	}
	
	/**
	 * Se invoca para le ejecucion de un job.
	 * @param nombreJob Nombre del job a ejecutar.
	 * @return String con estatus del job ejecutado. 
	 */
	public String ejecutar(String nombreJob) throws FindFailed, InterruptedException {
		testCase.addBoldTextEvidenceCurrentStep("Ejecuta Job "+nombreJob);

		if (!this.loggedIn) {
			this.iniciarSesionControlM(); // bandera para que no se logue cada que invocas el metodo.
		}		
		
		Monitoring monitoring = new Monitoring(this.seleniumUtil);
		
		By hold = By.cssSelector("ul.dropdown-menu-right:nth-child(2) > li:nth-child(1) > a:nth-child(1)");
		By rerun = By.cssSelector("ul.dropdown-menu-right:nth-child(2) > li:nth-child(3) > a:nth-child(1)");

		JSONObject job = this.getJob(nombreJob); // obtienes el job
		
		ControlM controlM = new ControlM(this.seleniumUtil, testCase, job);

		if (job == null) { // en caso de no existir en el hashmap (no existia informacion en el data provider)
			return "El job: '" + nombreJob + "' no existe";
		}
		
		controlM.searchJob(); 
		
		
		String resultadoEjecucion = controlM.executeJob();
		

		this.seleniumUtil.hardWait(15);

		Thread.sleep(1000);
			
		monitoring.userClicksActions();
		this.seleniumUtil.hardWait(2);

		boolean encuentra = this.seleniumUtil.getDriver().findElement(rerun).isDisplayed();
		System.out.println("Encuentra: ---> "+encuentra);


		if(encuentra) {
			this.seleniumUtil.click(rerun);

		}else {
			monitoring.userClicksActions();

		}
		
		this.seleniumUtil.hardWait(5);
		monitoring.userClicksActions();
		this.seleniumUtil.hardWait(2);
		this.seleniumUtil.click(hold);	
		
		Thread.sleep(5000); 
		
		
		monitoring.userClosesViewpoint();
		Thread.sleep(2000);

		return resultadoEjecucion;
		
	}
	

	public boolean fueEjecutadoCorrectamente(String resultadoEjecucion) {
		return resultadoEjecucion.equals("Wait Condition") || resultadoEjecucion.equals("Ended OK");
	}
	
	
	/**
	 * Selecciona el filtro order id para que en la vista solo se muestre 
	 * el job que se requiere ejecutar
	 * @param JobName
	 * @author alicia.ceja
	 */

	public void editViewPointOrderId(String JobName, String orderId) {
		
		try {
			By selectJob = By.xpath("//*[contains(@class,'active')]//div/span[text()='"+JobName+"']");

			this.seleniumUtil.hardWait(2);;
			this.seleniumUtil.mouseOverAndClick(editViewPoint);

			this.seleniumUtil.waitWebElement(edit, 120, 4);
			/*this.seleniumUtil.click(edit);
			
			
			this.seleniumUtil.hardWait(2);;
			this.seleniumUtil.scrollToElement(ciclik);
			
			this.seleniumUtil.hardWait(2);
			this.seleniumUtil.highLight(chkOrderId);
			this.seleniumUtil.click(chkOrderId);

			this.seleniumUtil.hardWait(2);;
			this.seleniumUtil.click(inputDone);
			
			this.seleniumUtil.hardWait(2);*/
			this.seleniumUtil.scrollToElement(inputOrderId);
			
			
			this.seleniumUtil.sendKeys(inputOrderId, orderId);

			

			this.seleniumUtil.hardWait(2);;
			this.seleniumUtil.click(inputOk);
			
			this.seleniumUtil.hardWait(2);;
			this.seleniumUtil.click(selectJob);
			
		}catch(Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al seleccionar el filtro Order Id para la vista");
		}
				
	}
	
	
	public void quitarFiltro() {
		
		try {

			this.seleniumUtil.hardWait(2);;
			this.seleniumUtil.mouseOverAndClick(editViewPoint);

			this.seleniumUtil.waitWebElement(edit, 120, 4);
			this.seleniumUtil.click(edit);
			
			
			this.seleniumUtil.hardWait(2);;
			this.seleniumUtil.scrollToElement(ciclik);
			
			this.seleniumUtil.hardWait(2);
			this.seleniumUtil.highLight(chkOrderId);
			this.seleniumUtil.click(chkOrderId);

			this.seleniumUtil.hardWait(2);;
			this.seleniumUtil.click(inputDone);
						

			this.seleniumUtil.hardWait(2);;
			this.seleniumUtil.click(inputOk);
	
			

		}catch(Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Error al quitar el filtro Order Id para la vista");
		}
	}
	
	/**
	 * Se invoca para le ejecucion de un job.
	 * @param nombreJob Nombre del job a ejecutar.
	 * @return String con estatus del job ejecutado. 
	 */
	public String ejecutarOrderId(String nombreJob, String orderId) throws FindFailed, InterruptedException {
		testCase.addBoldTextEvidenceCurrentStep("Ejecuta Job "+nombreJob);

		if (!this.loggedIn) {
			this.iniciarSesionControlM(); // bandera para que no se logue cada que invocas el metodo.
		}		
		
		Monitoring monitoring = new Monitoring(this.seleniumUtil);
		
		By hold = By.cssSelector("ul.dropdown-menu-right:nth-child(2) > li:nth-child(1) > a:nth-child(1)");
		By rerun = By.cssSelector("ul.dropdown-menu-right:nth-child(2) > li:nth-child(3) > a:nth-child(1)");
		By runNow = By.cssSelector("");
		By release = By.cssSelector("");

		JSONObject job = this.getJob(nombreJob); // obtienes el job
		
		ControlM controlM = new ControlM(this.seleniumUtil, testCase, job);

		if (job == null) { // en caso de no existir en el hashmap (no existia informacion en el data provider)
			return "El job: '" + nombreJob + "' no existe";
		}
		
		controlM.searchJob(); 
		
			editViewPointOrderId(nombreJob, orderId);
		
		
		String resultadoEjecucion = controlM.executeJob();
		

		this.seleniumUtil.hardWait(15);

		Thread.sleep(1000);
			
		monitoring.userClicksActions();
		this.seleniumUtil.hardWait(2);

		boolean encuentra = this.seleniumUtil.getDriver().findElement(rerun).isDisplayed();
		System.out.println("Encuentra: ---> "+encuentra);


		if(encuentra) {
			this.seleniumUtil.click(rerun);

		}else {
			monitoring.userClicksActions();

		}
		
		this.seleniumUtil.hardWait(5);
		monitoring.userClicksActions();
		this.seleniumUtil.hardWait(2);
		this.seleniumUtil.click(hold);	
		
		Thread.sleep(5000); 

		
		monitoring.userClosesViewpoint();
		Thread.sleep(2000);

		return resultadoEjecucion;
		
	}
	
	public String ejecutarSinOutput(String nombreJob) throws FindFailed, InterruptedException {
		testCase.addBoldTextEvidenceCurrentStep("Ejecuta Job "+nombreJob);

		if (!this.loggedIn) {
			this.iniciarSesionControlM(); // bandera para que no se logue cada que invocas el metodo.
		}		
		
		Monitoring monitoring = new Monitoring(this.seleniumUtil);
		
		By hold = By.cssSelector("ul.dropdown-menu-right:nth-child(2) > li:nth-child(1) > a:nth-child(1)");
		By rerun = By.cssSelector("ul.dropdown-menu-right:nth-child(2) > li:nth-child(3) > a:nth-child(1)");
		By runNow = By.cssSelector("");
		By release = By.cssSelector("");

		JSONObject job = this.getJob(nombreJob); // obtienes el job
		
		ControlM controlM = new ControlM(this.seleniumUtil, testCase, job);

		if (job == null) { // en caso de no existir en el hashmap (no existia informacion en el data provider)
			return "El job: '" + nombreJob + "' no existe";
		}
		
		String resultado="Exito";
		
		controlM.searchJob(); 
		
		/*
		String resultadoEjecucion = controlM.executeJob();
				
		this.seleniumUtil.hardWait(15);

		Thread.sleep(1000);
			
		monitoring.userClicksActions();
		this.seleniumUtil.hardWait(2);

		boolean encuentra = this.seleniumUtil.getDriver().findElement(rerun).isDisplayed();
		System.out.println("Encuentra: ---> "+encuentra);


		if(encuentra) {
			this.seleniumUtil.click(rerun);

		}else {
			monitoring.userClicksActions();

		}
		
		this.seleniumUtil.hardWait(5);
		monitoring.userClicksActions();
		this.seleniumUtil.hardWait(2);
		this.seleniumUtil.click(hold);	
		
		Thread.sleep(5000); 
		
		
		monitoring.userClosesViewpoint();
		Thread.sleep(2000);*/

		return resultado;
		
	}
	
	public void loadingCtrlM() {
		try {
			this.seleniumUtil.hardWait(2);
			while (this.seleniumUtil.getDriver().findElement(loadCtrlM).isDisplayed()) {
				System.out.println("Loading ...");
				this.seleniumUtil.hardWait(5);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
 
}
