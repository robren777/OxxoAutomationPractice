package EBS.updateNomina.om;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EBS.util.GlobalVariables;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;

public class UpdateNomina {

	SikuliUtil sk;
	Pattern pathimg;
	ArrayList<String> ListImage;
	HashMap<String, Pattern> ImgPattern;
	ArrayList<Pattern> patterns;
	File Fol;


	//private final TestCase testCase;


	//SeleniumUtil u = new SeleniumUtil(new MEdgeTest(), false);

	TestCase testCase;
	SeleniumUtil u ;
	WebDriverWait wait;
	String crFrm = null;

	
	
	By botonAvanzado = By.xpath("/html/body/div/div[2]/button[3]");
    By linkContinuar = By.xpath("/html/body/div/div[3]/p[2]/a");
    By CONTINUAR = By.xpath("//input[@value=\"CONTINUAR\"]");
	By usuario = By.xpath("//input[@name='p_user']");
	By password = By.xpath("//input[@name='p_pass']");
	By buttonAvanzado = By.xpath("//*[@id='details-button']");
	By aContinuarASitioNoSeguro = By.xpath("//*[@id='proceed-link']");
	By inputNombreDeUsuario = By.xpath("/html/body/div/div[1]/div/form/div[1]/input");
    By inputContrasena = By.xpath("/html/body/div/div[1]/div/form/div[2]/input");
    By botonContinuar =  By.xpath("//input[@value=\"CONTINUAR\"]");
	
	
	//Elementos Control M
	By aServices = By.xpath("//*[@class='ViewpointsHome tab-item--link my-children-show-key-navigation_outline-width-0']");
	

	// seleccion de plaza
	By plazas = By.xpath("//select[@name=\"p_cr_plaza\"]");
	By servidor = By.xpath("//select[@name=\"selectedServidor\"]");
	By CONTINUAR2 = By.xpath("//input[@name=\"CONTINUAR\"]");
	

	protected static final Logger logger = LoggerFactory.getLogger(BaseExecution.class);





	public UpdateNomina(SikuliUtil sk, SeleniumUtil u, TestCase testCase, Pattern pathimg, ArrayList<String> ListImage,
			HashMap<String, Pattern> ImgPattern, ArrayList<Pattern> patterns, File Fol) {
	

		this.u = u;
		this.sk = sk;
		this.pathimg = pathimg;
		this.ListImage = ListImage;
		this.ImgPattern = ImgPattern;
		this.patterns = patterns;
		this.Fol = Fol;

		//this.testCase = testCase;

		this.testCase = testCase;
		this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
		

	}

	public HashMap<String, Pattern> getPatternsImg(File Fol) {
		File[] files = Fol.listFiles();
		for (File s : files) {
			patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
		}
		for (Pattern p : patterns) {
			String fname = p.getFilename().toString();
			fname = fname.replace("\\", "-");
			fname = fname.replace(".png", "");
			fname = fname.replace(".PNG", "");
			String[] afname = fname.split("-");
			ImgPattern.put(afname[afname.length - 1], p);
		}

		return ImgPattern;
	}

	
	
	
	// se ejecuta archivo 
	public void ejecutararchivofrmservlet(File Folder, String compania, String estadoFinanciero, String crSuperior, String  fecha_VI, String prefijo, String descripcionHrs)throws FindFailed {

		ImgPattern = this.getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("btnconservar"), 15).click();
		sk.screen.wait(ImgPattern.get("frmservlet"), 15).click();
		sk.screen.wait(ImgPattern.get("Aceptolosriesgos"), 15).click();
        sk.screen.wait(ImgPattern.get("ejecutarW11"), 15).click();
		sk.screen.wait(ImgPattern.get("lblOrigen"), 35).highlight();
		sk.screen.type(compania);
		sk.screen.type(Key.TAB);
		sk.screen.type(estadoFinanciero);
		sk.screen.type(Key.TAB);
		sk.screen.type(crSuperior);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		sk.screen.type(fecha_VI);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		sk.screen.type(prefijo);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		u.hardWait(2);
		sk.screen.type("c",KeyModifier.CTRL);
		u.hardWait(2);
				
		sk.screen.wait(ImgPattern.get("btngenerarCr"), 15).click();
		sk.screen.wait(ImgPattern.get("btnsi"), 15).click();
		sk.screen.wait(ImgPattern.get("btn_aceptar"), 15).click();
		sk.screen.wait(ImgPattern.get("cerrarW11"),15).click();
		sk.screen.wait(ImgPattern.get("aceptarW11"),15).click();
        testCase.addScreenShotCurrentStep("datos de la tienda");
        
       
		
		//sk.screen.type(Key.TAB);
		
		
	}
	
	public void selecionarbotones(File Folder) throws FindFailed {
		ImgPattern = this.getPatternsImg(Folder);
		
		sk.screen.wait(ImgPattern.get("btnobtenerCrvalido"), 15).click();
		
		sk.screen.wait(ImgPattern.get("btnsi"), 15).click();
		sk.screen.wait(ImgPattern.get("btn_aceptar"), 15).click();
		sk.screen.wait(ImgPattern.get("cerrarventana"), 15).click();
        sk.screen.wait(ImgPattern.get("aceptarcerrar"), 15).click();
 
		
	}
	

	public void selecionarconfiguracion(File Folder) throws FindFailed {
		ImgPattern = this.getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("Configuracion"), 15).click();
		sk.screen.wait(ImgPattern.get("Configuracion1"), 15).click();
		
		
	}
	
	
	// ingresar a potal cnt
	public void loginCNT(String dato, String dato2) throws FindFailed {
		
		//ImgPattern = this.getPatternsImg(Folder);
		//u.highLight(user);
		//u.sendKeys(user, dato);
 
		//u.highLight(pass);
		//u.sendKeys(pass, dato2);
		u.highLight(CONTINUAR);
		testCase.addScreenShotCurrentStep("Login");
		u.click(CONTINUAR);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep("Resultado");
		
	}
	
	
	public void clickSeleccionarPlaza(String plazaE) {
		u.highLight(plazas);
		Select plazasS = new Select(u.getDriver().findElement(plazas));
		plazasS.selectByVisibleText(plazaE);
		testCase.addScreenShotCurrentStep(u, "PlazaE");
		u.click(CONTINUAR);
	}
	
	public void clikcSeleccionarservidor(File Folder) throws FindFailed  {
		ImgPattern = this.getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("ConfiguracionW"), 15).click();
		sk.screen.wait(ImgPattern.get("ConfiguracionW1"), 15).click();
		u.hardWait(5);
		//sk.screen.wait(ImgPattern.get("servidoroxxo"), 15).click();
		sk.screen.wait(ImgPattern.get("ReportesW"), 15).click();
		sk.screen.wait(ImgPattern.get("PolizasEnviadasW"), 15).click();
		testCase.addScreenShotCurrentStep(u, "ServidorInmex");
			
	}
	
	
	public void clickservidor(File Folder) throws FindFailed {
		
		ImgPattern = this.getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("ConfiguracionW"), 15).click();
		sk.screen.wait(ImgPattern.get("ConfiguracionW1"), 15).click();
		sk.screen.wait(ImgPattern.get("clickcombo"), 15).click();
		sk.screen.wait(ImgPattern.get("serverimmex"), 15).click();
		sk.screen.wait(ImgPattern.get("ReportesW"), 15).click();
		sk.screen.wait(ImgPattern.get("PolizasEnviadasW"), 15).click();
		//testCase.addScreenShotCurrentStep(u, "PolizasEnviadasW");
			
		
		
		
	}
	
	
	public void clickReportes(File Folder) throws FindFailed  {
		ImgPattern = this.getPatternsImg(Folder);
		sk.screen.wait(ImgPattern.get("Reportes"), 15).click();
	}
	
	
	public void clickDatosOperacion (File Folder, String asesor) throws FindFailed {
		ImgPattern = this.getPatternsImg(Folder);
		u.click(CONTINUAR);
		sk.screen.wait(ImgPattern.get("frmservlet"), 15).click();
		sk.screen.type(asesor);
		
	}
	
	/*************************** F099 **********************************************/
	
	public String consulta01 () {
		
		String query01 = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  from SAPUSER.SAP_PYR_HEADER SH\r\n"
				+ "inner join SAPUSER.SAP_INBOUND_DOCS SI\r\n"
				+ "on SH.SID_ID = SI.ID\r\n"
				+ "WHERE SID_ID = 1278953 AND SI.STATUS = 'L'";
		return query01;
		
	}
	
	public String consulta02() {
		String query02 = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  from SAPUSER.SAP_PYR_HEADER SH \r\n"
				+ "inner join SAPUSER.SAP_INBOUND_DOCS SI \r\n"
				+ "on SH.SID_ID = SI.ID WHERE SH.SID_ID = 1278953 AND SI.STATUS = 'S'";
		return query02;
		
	}
	
	public String consulta03(String ID) {
		String query03 = "select STATUS, DATE_CREATED,USER_JE_CATEGORY_NAME,USER_JE_SOURCE_NAME,GROUP_ID  from apps.gl_interface where group_id ="+ ID;
		return query03;
	}
	
	/*************************** F110 **********************************************/
	
	public String CF110L() {
		String queryCF110L = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  from SAPUSER.SAP_PYR_HEADER SH\r\n"
				+ "inner join SAPUSER.SAP_INBOUND_DOCS SI\r\n"
				+ "on SH.SID_ID = SI.ID \r\n"
				+ "WHERE SID_ID = 1278933 AND SI.STATUS = 'L'";
		return queryCF110L;		
	}
	
	
	public String CF110S() {
		String queryCF110S = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  from SAPUSER.SAP_PYR_HEADER SH \r\n"
				+ "inner join SAPUSER.SAP_INBOUND_DOCS SI \r\n"
				+ "on SH.SID_ID = SI.ID WHERE SH.SID_ID = 1278933 AND SI.STATUS = 'S'";
		return queryCF110S;		
	}
	
	public SQLResult F110(String ID) throws SQLException {
		
		String query202 = "select STATUS, date_created, user_je_category_name, GROUP_ID from GL.gl_interface where group_id =" + ID;
		
		
		SQLResult res = this.sqlExecute(query202, GlobalVariables.DB_OXXOGAS_TURBO_NOM, GlobalVariables.USER_OXXOGAS_TURBO_NOM, GlobalVariables.PASS_OXXOGAS_TURBO_NOM);
		if (res.getRowCount() > 0) {
			testCase.addQueryEvidenceCurrentStep(res);
		} else {
			throw new AssertionError("La consulta no trajo resultado");
		}
		 return res;	
	}
	
	
	/*************************** F202 **********************************************/
	
	public String CF202L() {
		String queryF202L = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  \r\n"
				+ "from SAPUSER.SAP_PYR_HEADER SH inner join SAPUSER.SAP_INBOUND_DOCS SI on SH.SID_ID = SI.ID \r\n"
				+ "WHERE SH.SID_ID = 1278930 AND SI.STATUS = 'L' ";
		return queryF202L;		
	}
	
	public String CF202S() {
		String queryF202S = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  from SAPUSER.SAP_PYR_HEADER SH \r\n"
				+ "inner join SAPUSER.SAP_INBOUND_DOCS SI \r\n"
				+ "on SH.SID_ID = SI.ID WHERE SH.SID_ID = 1278930 AND SI.STATUS = 'S'";
		return queryF202S;		
	}
	
public SQLResult F202(String ID) throws SQLException {
		
		String query202 = "select STATUS, date_created, user_je_category_name, GROUP_ID from GL.GL_INTERFACE where GROUP_ID =" + ID;
		
		
		SQLResult res = this.sqlExecute(query202, GlobalVariables.DB_OXXOGAS_TURBO_NOM, GlobalVariables.USER_OXXOGAS_TURBO_NOM, GlobalVariables.PASS_OXXOGAS_TURBO_NOM);
		if (res.getRowCount() > 0) {
			testCase.addQueryEvidenceCurrentStep(res);
		} else {
			throw new AssertionError("La consulta no trajo resultado");
		}
		 return res;	
	}
	

/*************************** F480 **********************************************/
    
	public String CF480L() {
		String queryF480L = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  \r\n"
				+ "from SAPUSER.SAP_PYR_HEADER SH inner join SAPUSER.SAP_INBOUND_DOCS SI on SH.SID_ID = SI.ID \r\n"
				+ "WHERE SH.SID_ID = 1278840 AND SI.STATUS = 'L' ";
		return queryF480L;		
	}
	
	public String CF480S() {
		String queryF480S = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  from SAPUSER.SAP_PYR_HEADER SH \r\n"
				+ "inner join SAPUSER.SAP_INBOUND_DOCS SI \r\n"
				+ "on SH.SID_ID = SI.ID WHERE SH.SID_ID = 1278840  AND SI.STATUS = 'S'";
		return queryF480S;		
	}
	
	public SQLResult F480(String ID) throws SQLException {
		
		String query202 = "select STATUS, date_created, user_je_category_name, GROUP_ID from GL.GL_INTERFACE where GROUP_ID = " + ID;
		
		
		SQLResult res = this.sqlExecute(query202, GlobalVariables.DB_OXXOGAS_TURBO_NOM, GlobalVariables.USER_OXXOGAS_TURBO_NOM, GlobalVariables.PASS_OXXOGAS_TURBO_NOM);
		if (res.getRowCount() > 0) {
			testCase.addQueryEvidenceCurrentStep(res);
		} else {
			throw new AssertionError("La consulta no trajo resultado");
		}
		 return res;	
	}

	/*************************** Finiquitos **********************************************/
	
    public String CFNL() {
		
		String queryFN = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  \r\n"
				+ "from SAPUSER.SAP_PYR_HEADER SH inner join SAPUSER.SAP_INBOUND_DOCS SI on SH.SID_ID = SI.ID \r\n"
				+ "WHERE SH.SID_ID = 1278953 AND SI.STATUS = 'L'";
		
		return queryFN;
    }
	
	
	public String CFNS() {
		String queryFN = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  from SAPUSER.SAP_PYR_HEADER SH \r\n"
				+ "inner join SAPUSER.SAP_INBOUND_DOCS SI \r\n"
				+ "on SH.SID_ID = SI.ID WHERE SH.SID_ID = 1278953 AND SI.STATUS = 'S' ";
		return queryFN;		
	}
	
	
	public String CFN(String ID) {
		String queryFN = "select STATUS, DATE_CREATED,USER_JE_CATEGORY_NAME,USER_JE_SOURCE_NAME,GROUP_ID  from apps.gl_interface where group_id ="+ ID;
		return queryFN;		
	}
	
	
	
  
    public String CF110() {
    	
        String queryFN = "select date_created, GROUP_ID  from APPS.GL_INTERFACE order by date_created desc";
		return queryFN;
    }
    
   
	public static SQLResult sqlExecute(String query, String bd, String user, String password) throws SQLException {
		//Crear conexión
		Connection conn = DriverManager.getConnection(bd, user, password);
		//Ejecutar consulta
		Statement statement = conn.createStatement();
		if(statement.execute(query)) {
			SQLResult res = new SQLResult(conn, query);
			return res;
		}else {
			return null;
		}	
	}
	
	
	public void logInControlMyEjecutarJob(Control_mInicio CM, ControlM controlM) throws Exception {
		u.get("http://172.26.177.68:18080/ControlM/?route=UserViews");
		u.click(buttonAvanzado);
		u.click(aContinuarASitioNoSeguro);
		
		CM.logOn();
		testCase.addScreenShotCurrentStep(u, "control m inicio");
		//TERMINA LOGIN
		//INICIA BUSQUEDA Y EJECUCION DEL JOB
		boolean flag = controlM.searchJob();
		assertFalse(!flag, "No se encontro el job");
		System.out.println("Job Status " + controlM.getCurrentStatus());
 
		String resultadoDelJob = controlM.executeJob();
		testCase.addTextEvidenceCurrentStep("Resultado de la ejecucion " + resultadoDelJob);
		System.out.println("Resultado de la ejecucion -->" + resultadoDelJob);
 
		testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
		System.out.println("Output " + controlM.getOutput());
 
		boolean testResult = !resultadoDelJob.equals("Failure");
		if (!testResult) {
			testCase.addTextEvidenceCurrentStep("Fallo la ejecucion del job ");
			System.out.println("Fallo la ejecucion del job ");
		}
 
		assertTrue(testResult, "La ejecucion del job fue exitosa");
		controlM.closeViewpoint();
	}
	
	
	public void ejecutarJob( ControlM controlM) throws Exception {
		//CLICK EN PESTAÑA SERVICES PARA PODER ABRIR UN NUEVO VEWPOINT
		wait.until(ExpectedConditions.elementToBeClickable(aServices));
		u.click(aServices);
		//INICIA BUSQUEDA Y EJECUCION DEL JOB
		boolean flag = controlM.searchJob();
		assertFalse(!flag, "No se encontro el job");
		System.out.println("Job Status " + controlM.getCurrentStatus());
		
		String resultadoDelJob = controlM.executeJob();
		testCase.addTextEvidenceCurrentStep("Resultado de la ejecucion " + resultadoDelJob);
		System.out.println("Resultado de la ejecucion " + resultadoDelJob);
 
		testCase.addTextEvidenceCurrentStep("Output " + controlM.getOutput());
		System.out.println("Output " + controlM.getOutput());
 
		boolean testResult = !resultadoDelJob.equals("Failure");
		if (!testResult) {
			testCase.addTextEvidenceCurrentStep("Fallo la ejecucion del job ");
			System.out.println("Fallo la ejecucion del job ");
		}
 
		assertTrue(testResult, "La ejecucion del job fue exitosa");
		//controlM.closeViewpoint();
	}
	
	/*************************** F097 **********************************************/
	
public String CF097L () {
		
		String query01 = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  \r\n"
				+ "from SAPUSER.SAP_PYR_HEADER SH inner join SAPUSER.SAP_INBOUND_DOCS SI on SH.SID_ID = SI.ID \r\n"
				+ "WHERE SH.SID_ID = 1278904 AND SI.STATUS = 'L'";
		return query01;
		
	}
	
	public String CF097S() {
		String query02 = "select SH.SID_ID, SH.DOCNUM, SH.RUNID, SH.BUKRS, SI.STATUS,SI.TARGET_ID, SI.RUN_ID  \r\n"
				+ "from SAPUSER.SAP_PYR_HEADER SH inner join SAPUSER.SAP_INBOUND_DOCS SI on SH.SID_ID = SI.ID \r\n"
				+ "WHERE SH.SID_ID = 1278904 AND SI.STATUS = 'S'";
		return query02;
		
	}
	
	public String GLF097(String ID) {
		String query03 = "select  STATUS, date_created, user_je_category_name, GROUP_ID from apps.gl_interface where group_id =" + ID ;
		return query03;
	}
	
	
	 public void ConexionCNT(String user, String pass) {
	        try {
	            u.getDriver().manage().deleteAllCookies();
	            String url = "http://fcowlq07.femcom.net:8000/tiendas/html/login.html";
	            testCase.addTextEvidenceCurrentStep(url);

	            u.get(url);
	            u.getDriver().manage().window().maximize();
	           

	            u.getDriver().navigate().refresh();
	            wait.until(ExpectedConditions.elementToBeClickable(usuario));
	            u.sendKeys(usuario, user);
	            u.sendKeys(password, pass);
	            u.click(botonContinuar);
	        } catch (Exception e) {
	            System.out.println(" -> " + e);
	            this.u.close();
	            throw new AssertionError("http://fcowlq07.femcom.net:8000/tiendas/html/login.html");
	        }

	    }
	 
	 
	 public void clicktiendsCR (File Folder) throws FindFailed {
		 ImgPattern = this.getPatternsImg(Folder);
		 
		 String CRTienda = 50+crFrm;
		 System.out.println(CRTienda);
		 
		  sk.screen.wait(ImgPattern.get("cmbCR"), 15).click();
		  sk.screen.type(CRTienda);
		  sk.screen.wait(ImgPattern.get("continuarCR"), 15).click();
		  testCase.addTextEvidenceCurrentStep(CRTienda);
		 
		 
	 }
}

