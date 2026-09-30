package WorkFlows.util.ReporteDeAbastoCEDIS;

import java.io.File;
import java.sql.SQLException;
import java.util.Set;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import WorkFlows.util.GlobalVariables;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.controlm.ControlM;
import utils.controlm.pageObject.Control_mInicio;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;


public class PomReporteDeAbastoCEDIS {
	
	private String dbUser = GlobalVariables.DB_USER_PLANOGRAMAS_FCPVEQA;
	private String dbPass = GlobalVariables.DB_PASS_PLANOGRAMAS_FCPVEQA;
	private String dbHost = GlobalVariables.DB_HOST_PLANOGRAMAS_FCPVEQA;
	private String dbUserRMS10 = GlobalVariables.DB_USER_RMS10;
	private String dbPassRMS10 = GlobalVariables.DB_PASS_RMS10;
	private String dbHostRMS10 = GlobalVariables.DB_HOST_RMS10;
	private String dbUserRMS16 = GlobalVariables.DB_USER_RMS16_;
	private String dbPassRMS16 = GlobalVariables.DB_PASS_RMS16;
	private String dbHostRMS16 = GlobalVariables.DB_HOST_RMS16_;
	private String userControlM = GlobalVariables.CTRLM_USER;
	private String passControlM = GlobalVariables.CTRLM_PASS;
	
	private SikuliUtil sikuli = new SikuliUtil();
	private Screen screen = new Screen();
	private SeleniumUtil u;
	private Pattern p1;
	private Select list;
	
	private String folder = "WorkFlows"+File.separator+"ReporteDeAbastoCEDIS";
	private String s1;
	private String userUltimus;
	private String mainTab;
	private String query;

	private String cedisDesc;
	private String superGrupoDesc;
	private String rangoDesc;
	private String codigo;
	
	private String cedisId;
	private String superGrupoId;
	private String rangoId;
	
	private TestCase testCase;
	private Set<String> handles;
	
	
	//Locator de la pagina de inicio de sesión de ultimus
	By vistaCss = By.cssSelector("span.igtr_BlueRoot:nth-child(6)");
	By userIdXpath = By.xpath("//*[@id=\"igtxtUserNameBox\"]");
	By buttonLogOnXpath = By.xpath("//*[@id=\"LoginBtn__5\"]");	
	By tableUltimus = By.cssSelector("table#G_WebSplitter1xtmpl1xUltraWebGrid1 > tbody > tr");
	By cedisXpath = By.xpath("/html/body/form/div[3]/div[1]/div/div[1]/div[5]/div/table/tbody/tr/td[3]/div/table/tbody/tr[3]/td/div/table/tbody/tr[3]/td/div/table/tbody/tr[14]/td[5]/nobr");
	
	//Locators de la pantalla Reporte de Abasto CEDIS ///////////////////////////////////////////////////////////
	By selectCEDISXapth = By.xpath("//select[@id='ContentPlaceHolder1_cphReporteAbasto_ddlCEDIS']");
	By selectSuperGruposXpath = By.xpath("//select[@id='ContentPlaceHolder1_cphReporteAbasto_ddlSuperGrupo']");
	By selectRangoXpath = By.xpath("//select[@id='ddlRango']");
	By txtCodigoXpath = By.xpath("//input[@id='txtCodigo']");
	By btnBuscarXpath = By.xpath("//input[@id='ContentPlaceHolder1_cphReporteAbasto_btnBuscar']");
	By btnGuardarXpath = By.xpath("//input[@id='ContentPlaceHolder1_cphReporteAbasto_BtnGuardar']");
	
	
	/**
	 * @author José Zárate
	 * Constructor de la clase para inicializar los valores
	 * @param u ------------> Objeto selenium con el driver del navegador a utilizar
	 * @param userUltimus --> Usuario para el login en el portal e ultimus
	 */
	public PomReporteDeAbastoCEDIS(SeleniumUtil u, String userUltimus, TestCase testCase) {
		this.u = u;
		this.userUltimus = userUltimus;
		this.testCase = testCase;
	}
	
	/**
	 * @author José Zárate
	 * Elimina las cookies del navegador
	 */
	public void deleteCookies() {
		u.getDriver().manage().deleteAllCookies();
	}
	
	/**
	 * @author José Zárate
	 * Realiza el loguin a la pataforma de ultimus 
	 */
	public void loginUltimus() throws Exception {
		u.clear(userIdXpath);
		u.highLight(userIdXpath);
		u.sendKeys(userIdXpath, userUltimus);
		u.highLight(buttonLogOnXpath);
		testCase.addScreenShotCurrentStep(u, "Click en el botón LogOn");
		u.click(buttonLogOnXpath);	
	}
	
	/**
	 * @author José Zárate
	 * Valida el ingreso de las credenciales en el login de ultimus
	 * @return --> True si se llenaron los campos de forma correcta, False en el caso contrario
	 * @throws FindFailed
	 */
	public Boolean validateAccessLoginUltimus() throws FindFailed {
		if(u.waitWebElement(userIdXpath, 60, 2) != null) {
			testCase.addScreenShotCurrentStep(u,"Se muestra la página de login Ultimus correctamente");
			return true;			
		}
		else{
			testCase.addScreenShotCurrentStep(u,"No se muestra la pagina de login de Ultimus");
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Dentro de ultimus selecciona el botón "Iniciar"
	 * @throws FindFailed
	 */
	public void selectIniciar() throws FindFailed {	
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "inicio.png");
		screen.wait(p1, 30.0);
		screen.click();
	}
	
	/**
	 * @author José Zárate
	 * Dentro del munú de aplicaciones de ultimus, seleciona el portal promoxxoClone y obtiene el Id de la pagina actual 
	 * @throws FindFailed
	 */
	public void accessReporteDeAbasto() throws FindFailed {
		mainTab = u.getDriver().getWindowHandle(); //Obtiene el id de la página actual donde se esta trabajando
		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "btn-reporte-de-abasto.png");		
		screen.wait(p1, 30.0);
		screen.doubleClick(p1);
		testCase.addScreenShotCurrentStep(u, "Click en reporte de absato");
		
		System.out.println("Main Tab: "+ mainTab);		
	}
	
	public void informarCampos() {
		list = new Select(u.getDriver().findElement(selectCEDISXapth));
		list.selectByVisibleText(cedisDesc);
		
		list = new Select(u.getDriver().findElement(selectSuperGruposXpath));
		list.selectByVisibleText(superGrupoDesc);
		
		list = new Select(u.getDriver().findElement(selectRangoXpath));
		list.selectByVisibleText(rangoDesc);
		
		u.highLight(txtCodigoXpath);
		u.sendKeys(txtCodigoXpath, codigo);
		
		u.highLight(btnBuscarXpath);
		u.click(btnBuscarXpath);
	}
	
	public void selectArticulo() {
		
		u.highLight(btnGuardarXpath);
		u.click(btnGuardarXpath);
	}
	
	/**
	 * Ejecuta el job especificado
	 * @param nombreJob --> Nombre del job a ejecutar.
	 * @return --> String con estatus del job ejecutado. 
	 */
	public Boolean ejecutarJob(String nombreJob) throws FindFailed, InterruptedException {
		testCase.addTextEvidenceCurrentStep("Ejecuta Job: "+nombreJob);		
		JSONObject obj = new JSONObject(nombreJob);
		Control_mInicio CM = new Control_mInicio(u, this.userControlM, this.passControlM);
		
		this.u.get(GlobalVariables.CtrlM_URL);
		
		By inputPass = By.id("login-user-password");
		u.waitWebElement(inputPass, 120, 3);
		
		CM.logOn(); 

		ControlM control = new ControlM(u, testCase, obj); 
		control.searchJob(); 
		
		String resultadoEjecucion = control.executeJob(); 		
		System.out.println("Resultado de la ejecucion: " + resultadoEjecucion);
		String output = control.getOutput();
		System.out.println("Output: " +output);
		testCase.addTextEvidenceCurrentStep("Output: " +output);
				
		return true;
	}	
	
	
	public Boolean buscarCambio(BaseExecution bExecution) throws SQLException {
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		query = "SELECT * FROM xxva_siv3.XXVA_ZZ_FEMSA_REPL_SOM_CHANGES WHERE ITEM = I_Item AND LOCATION = I_Loc AND SOM_NEW = I_Som";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			testCase.addTextEvidenceCurrentStep("Se encontró el registro en la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();
			return false;
			
		}else {
			System.out.println("No se registró el cambio");
			testCase.addTextEvidenceCurrentStep("No se encontró el registro en la tabla");
			db.close();
			return true;
		}		
	}
	
	
	public Boolean buscarNuevoRegistro(BaseExecution bExecution) throws SQLException {
		SQLUtil db = new SQLUtil (dbHostRMS16, dbUserRMS16, dbPassRMS16);
		
		query = "SELECT * FROM RMS_INT.STG_REPL_SOM WHERE ITEM = I_Item AND LOC = I_Loc AND SOM = I_Som AND MSG_TYPE = 'REPLSOMCRE' AND ORIGIN_SYSTEM = 'ReporteAbasto' AND PROCESS_IND  = 'N'";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			testCase.addTextEvidenceCurrentStep("Se encontró el registro en la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();
			return false;
			
		}else {
			System.out.println("No se encontro el registro");
			testCase.addTextEvidenceCurrentStep("No se encontró el registro en la tabla");
			db.close();
			return true;
		}		
	}
	
	public Boolean consultarStatus(BaseExecution bExecution) throws SQLException {
		SQLUtil db = new SQLUtil (dbHostRMS16, dbUserRMS16, dbPassRMS16);
		
		query = "SELECT * FROM RMS_EXT.NB_REPL_SOM WHERE ITEM = I_Item AND LOC =I_Loc AND SOM = I_Som AND STATUS = 'N'";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			testCase.addTextEvidenceCurrentStep("Se encontró el registro en la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();
			return false;
			
		}else {
			System.out.println("No se encontro el registro");
			testCase.addTextEvidenceCurrentStep("No se encontró el registro en la tabla");
			db.close();
			return true;
		}		
	}
	
	public Boolean consultaCobertura(BaseExecution bExecution) throws SQLException {
		SQLUtil db = new SQLUtil (dbHostRMS16, dbUserRMS16, dbPassRMS16);
		
		query = "SELECT * FROM RMS.ITEM_LOC WHERE ITEM = I_Item AND LOC = I_Loc AND STORE_ORD_MULT = I_Som";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			testCase.addTextEvidenceCurrentStep("Se encontró el registro en la tabla");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();
			return false;
			
		}else {
			testCase.addTextEvidenceCurrentStep("No se encontró el registro en la tabla");
			System.out.println("No se encontro el registro");
			db.close();
			return true;
		}		
	}
	
	public Boolean buscarComprasCEDIS(BaseExecution bExecution) throws SQLException {
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		query = "Select count(*) from xxva_siv3.xxva_eng_compras_cedis where CEDIS_ID= "+cedisId;
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			if(ExecuteQuery.getRowCount() >= 500000) {
				testCase.addTextEvidenceCurrentStep("Se encontró la totalidad de los registros");
			}
			db.close();
			return false;
			
		}else {
			testCase.addTextEvidenceCurrentStep("No se encontró la totalidad de los registros");
			db.close();
			return true;
		}		
	}
	
	public Boolean validateFechaEjecucionJob(BaseExecution bExecution) throws SQLException {
		SQLUtil db = new SQLUtil (dbHost, dbUser, dbPass);
		
		query = "select value from xxva_siv3.XXVA_ENG_GLOBALS where name = 'TODAY'";
		
		SQLResult ExecuteQuery = bExecution.executeQuery(db, query);
		Boolean exists = ExecuteQuery.isEmpty();
		if(!exists) {
			testCase.addTextEvidenceCurrentStep("Se realizó el cambio de fecha de ejecucion del job");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();
			return false;
			
		}else {
			testCase.addTextEvidenceCurrentStep("No se realizó el cambio de fecha de ejecución del job");
			testCase.addQueryEvidenceCurrentStep(ExecuteQuery);
			db.close();
			return true;
		}		
	}
	
	/**
	 * @author José Zárate
	 * Valida el acceso a la pagina de ultimus
	 * @return --> True si se accedio de forma correcta, False en el caso contrariov
	 * @throws FindFailed
	 */
	public Boolean validateAccessUltimus() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "ultimus.png");
		
		if(screen.wait(p1, 60.0) != null){
			testCase.addScreenShotCurrentStep(u,"Se muestra la página de Ultimus correctamente");
			return true;
		}else {
			testCase.addScreenShotCurrentStep(u,"No muestra la página de Ultimus correctamente");
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida el despliegue de la lista de aplicaciones de ultimus 
	 * @return --> True si se muestran las apps de forma correcta, False en el caso contrario
	 * @throws FindFailed
	 */
	public Boolean validateAppsUltimus() throws FindFailed{
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "apps-ultimus");
		
		if(screen.wait(p1, 60.0) != null){
			testCase.addScreenShotCurrentStep(u,"Se muestra el listado de las aplicaciones de Ultimus");
			return true;
		}else {
			testCase.addScreenShotCurrentStep(u,"No se muestra el listado de las aplicaciones de Ultimus");
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida el acceso al portal de Reporte de Abastos CEDIS
	 * @return --> True si se muestran la pantalla, False en el caso contrariov
	 * @throws FindFailed
	 */
	public Boolean validateAccessReporteDeAbastos() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "pantalla-Reporte-De-Abasto.png");
		
		if(screen.wait(p1, 60.0) != null){	
			handles = u.getDriver().getWindowHandles();
			
			for(String actual : handles) {
				System.out.println("-- Handle ID: "+ actual);
				if(!actual.equalsIgnoreCase(mainTab)) {
					System.out.println("-- Changing Tab --");
					u.getDriver().switchTo().window(actual);
					
					System.out.println("Se realizó el switch");
				}			
			}		
			testCase.addScreenShotCurrentStep(u,"Se despliega la pantalla de Reporte De Abasto Cedis");
			return true;
		}else {
			testCase.addScreenShotCurrentStep(u,"No se despliega la pantalla de Reporte De Abasto Cedis");
			return false;
		}
	}
	
	/**
	 * @author José Zárate
	 * Valida el despliegue de la lista de articulos en el grid 
	 * @return --> True si se muestran las apps de forma correcta, False en el caso contrario
	 * @throws FindFailed
	 */
	public Boolean validateGrid() throws FindFailed{
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "grid");
		
		if(screen.wait(p1, 60.0) != null){
			testCase.addScreenShotCurrentStep(u,"Se muestra el listado de los articulos");
			return true;
		}else {
			testCase.addScreenShotCurrentStep(u,"No se muestra el listado de los articulos");
			return false;
		}
	}
}


