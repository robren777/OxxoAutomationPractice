package WorkFlows.util.Panp3;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class PomPanp {
	
	UtilsPanp panp3 = new UtilsPanp();
	
	private SikuliUtil sikuli =  new SikuliUtil();
	private Screen screen = new Screen();
	private SeleniumUtil u;	
	private String s1;
	private Pattern p1;
	private Select list;
	private String user;
	private String mainTab;
	private String folder = "WorkFlows"+File.separator+"Panp3";
	private Boolean statusRefresh = true;
	private int count = 0;
	
	private Set<String> handles;
	private TestCase testCase; 
	
	pomUltimus pomUlt;
	
	By tableUltimus = By.cssSelector("table#G_WebSplitter1xtmpl1xUltraWebGrid1 > tbody > tr");
	By btnRefreshXpath = By.xpath("//td[@id='WebSplitter1xtmpl1xtbRefresh_Item_0']");
	
	//Locators para accesos a Pega /////////////////////////////////////////////////
	By txtUserXpath = By.xpath("//input[@id='txtUserID']");
	By btnInicioXpath = By.xpath("//button[@id='sub']");
	
	//Locators para acceso a ultimus /////////////////////////////////////////////// 
	By txtUserIdXpath = By.xpath("//*[@id=\"igtxtUserNameBox\"]");
	By btnLogOnXpath = By.xpath("//*[@id=\"LoginBtn__5\"]");	
	
	//Locators de la pagina de pega ////////////////////////////////////////////////
	By btnMostrarAppsWebXpath = By.xpath("//button[contains(text(),'Mostrar aplicaciones web')]");
	
	//Locators Seleccion de provedor ///////////////////////////////////////////////
	By radioButtonMercanciasXpath = By.xpath("/html/body/div/div/div[2]/div[2]/form/div[3]/div/div[2]/div/span/dl/dt[1]/div/div[2]");
	By radioButtonCobroServiciosXpath = By.xpath("/html/body/div/div/div[2]/div[2]/form/div[3]/div/div[2]/div/span/dl/dt[2]/div/div[2]");
	By radioButtonFilialesXpath = By.xpath("/html/body/div/div/div[2]/div[2]/form/div[3]/div/div[2]/div/span/dl/dt[3]/div/div[2]");
	By radioButtonPrestadorServiciosXpath = By.xpath("/html/body/div/div/div[2]/div[2]/form/div[3]/div/div[2]/div/span/dl/dt[4]/div/div[2]");
	By radioButtonArrendadoresXpath = By.xpath("/html/body/div/div/div[2]/div[2]/form/div[3]/div/div[2]/div/span/dl/dt[5]/div/div[2]");
	By radioButtonHonorariosXpath = By.xpath("/html/body/div/div/div[2]/div[2]/form/div[3]/div/div[2]/div/span/dl/dt[6]/div/div[2]");
	By radioButtonActivosFijosXpath = By.xpath("/html/body/div/div/div[2]/div[2]/form/div[3]/div/div[2]/div/span/dl/dt[7]/div/div[2]");
	By radioButtonFleterosXpath = By.xpath("/html/body/div/div/div[2]/div[2]/form/div[3]/div/div[2]/div/span/dl/dt[8]/div/div[2]");
	By btnIniciarXpath = By.xpath("//a[@id='seleccionarTipoProveedor-botonEnviar']");
	//By radioButtonMercanciasXpath = By.xpath("//div[@id='seleccionarTipoProveedor-opt1']//div[@class='ui-radiobutton-box ui-widget ui-corner-all ui-radiobutton-relative ui-state-default']");
	By txtRFCXpath = By.xpath("//input[@id='autorizarProveedor-RFC']");
	By btnBuscarRFCXpath = By.xpath("//a[@id='autorizarProveedor-buscarRFC']");
	By txtRazonSocialXpath = By.xpath("//input[@id='autorizarProveedor-razonSocial']");
	By txtEmailXpath = By.xpath("//input[@id='autorizarProveedor-correoElectronico']");
	By selectCategoriaXpath = By.xpath("//select[@id='autorizarProveedor-tipoCategoria']"); //Chicles es la opcion 2
	By selectTipoDistribucionXpath = By.xpath("//select[@id='autorizarProveedor-tipoDistribucion']"); //Directa es la opcion 1
	By selectPlazoXpath = By.xpath("//select[@id='autorizarProveedor-plazoPago']"); //Contado es la opción 1
	By liPlazaXpath = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form/div[4]/div/div[2]/div/div[3]/div[2]/table/tbody/tr/td[1]/ul/li[21]");
	By flechaXpath = By.xpath("//body[@class='app gris tienda']/div[@class='envelope']/div[@class='wrapper center']/div[@id='contenido']/div[@class='envelope']/form[@id='autorizarProveedor']/div[@class='envelope']/div[@class='sitesize center']/div[@id='autorizarProveedor-vendorContainer']/div[@class='grid5 left']/div[@id='autorizarProveedor-plazasHeader']/div[@id='picklistDiv']/table[@id='autorizarProveedor-lstSites']/tbody/tr/td/button[1]");
	By btnEnviarXpath = By.xpath("//*[@id=\"autorizarProveedor-botonGuardar\"]");
	By btnContinuarXpath = By.xpath("//a[@class='boton rojo']");
	
	//Locators Administracion de convenios /////////////////////////////////////////
	By btnBuscarRFCConveniosXpath = By.xpath("//a[@id='conveniosLocal-buscarRfc']");
	By radioButtonSinConvenioXpath = By.xpath("//input[@id='conveniosLocal-tipoConvenio-5']");
	By txtConvenioRazonSocialXpath = By.xpath("//input[@id='conveniosLocal-razonSocial']");
	By btnConvenioEnviarXpath = By.xpath("//a[@id='conveniosLocal-enviar']");
	//By btnContinuarXpath = By.xpath("//a[@class='boton rojo']"); //Es el mismo para todas las pantallas emergentes
	
	//Portal de aplicaciones ////////////////////////////////////////////////////////
	By txtUsuarioXpath = By.xpath("//input[@id='login:userName']");
	By txtPassXpath = By.xpath("//input[@id='login:userPass']");
	By btnLoginButtonXpath = By.xpath("//a[@id='login:loginButton']");
	By txtContraseniaXpath = By.xpath("//input[@id='reset:userNewPass']");
	By txtConfContraseniaXpath = By.xpath("//input[@id='reset:userConfirmNewPass']");
	By btnAceptarXpath = By.xpath("//a[@id='reset:resetButton']");
	By btnAutomatizacionXpath = By.xpath("//a[contains(text(),'Automatización de Nuevos Proveedores v3')]");
	
	//Locators Aviso ////////////////////////////////////////////////////////////////
	By btnAceptarAvisoXpath = By.xpath("//button[@id='registrarInformacion-j_idt61']//span[@class='ui-button-text'][contains(text(),'Acepto')]");
	
	//Locators Información de Proveedor /////////////////////////////////////////////
	By txtCalleNumXpath = By.xpath("//input[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalCalleNumero']");
	By txtEntreCallesXpath = By.xpath("//input[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalEntreCalles']");
	By txtColoniaXpath = By.xpath("//input[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalColonia']");
	By txtCiudadXpath = By.xpath("//input[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalCiudad']");
	By selectEstadoXpath = By.xpath("//select[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalEstado']");
	By txtCodigoPostalXpath = By.xpath("//input[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalCodigoPostal']");
	By selectFronteraXpath = By.xpath("//select[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalFrontera']");
	By txtNombreXpath = By.xpath("//input[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalPrimerNombre']");
	By txtApellidosXpath = By.xpath("//input[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalApellidos']");
	By txtLadaXpath = By.xpath("//input[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalLadaTelefonica']");
	By txtTelefocnoXpath = By.xpath("//input[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalTelefono']");
	By txtCorreoXpath = By.xpath("//input[@id='registrarInformacion-tabsDatosIdentificacion-tabFiscalCorreoElectronico']");
	By selectPaisXpath = By.xpath("//select[@id='registrarInformacion-tabFiscalPais']");
	By selectDivisaXpath = By.xpath("//select[@id='registrarInformacion-tabFiscalDivisa']");
	By selectBancoXpath = By.xpath("//select[@id='registrarInformacion-tabFiscalBanco']");
	By selectSucursalXpath = By.xpath("//select[@id='registrarInformacion-tabFiscalSucursal']");
	By txtCuentaBancaria = By.xpath("//input[@id='registrarInformacion-tabFiscalCuentaBancaria']");
	By txtConfCuentaXpath = By.xpath("//input[@id='registrarInformacion-tabFiscalConfirmarCuentaBancaria']");
	By txtABAXpath = By.xpath("//input[@id='registrarInformacion-tabFiscalConfirmarCuentaBancaria']");
	By txtANombreDeXpath = By.xpath("//input[@id='registrarInformacion-tabFiscalANombreDe']");
	By elementPlazaMtyXpath = By.xpath("//li[@class='ui-picklist-item ui-corner-all ui-state-hover']");
	By btnFlechaPlazaXpath = By.xpath("//span[@class='ui-button-icon-left ui-icon ui-icon ui-icon-arrow-1-e']");
	By btnExaminarXpath = By.xpath("//input[@id='registrarInformacion-fileEdoCta']");
	By checkBoxResponsabilidadXpath = By.xpath("//span[@class='ui-chkbox-icon ui-icon ui-icon-check']");
	By btnAgregarPagoXpath = By.xpath("//button[@id='registrarInformacion-btnAgregarPagoElectronico']");
	
	By btnRegistroInfoXpath = By.xpath("//button[@id='registrarInformacion-j_idt611']");
	By btnOrdenesCompraXpath = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form[1]/div[3]/div/div[2]/div/span[2]/span[2]/div/ul/li[2]/a");
	By btnDevolucionesXpath = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form[1]/div[3]/div/div[2]/div/span[2]/span[2]/div/ul/li[3]");
	By btnCorporativoXpath = By.xpath("/html/body/div[1]/div/div[2]/div[2]/form[1]/div[3]/div/div[2]/div/span[2]/span[2]/div/ul/li[4]");
	By btnDireccionFiscalXpath = By.xpath("//button[@id='registrarInformacion-tabsDatosIdentificacion-j_idt250']");
	By btnArchivoCFDIXpath = By.xpath("//input[@id='registrarInformacion-fileFactura']");
	By btnSubirFacturaXpath = By.xpath("//button[@id='registrarInformacion-j_idt669']");
	By btnArchivoRFCXpath = By.xpath("//input[@id='registrarInformacion-fileRfc']");
	By btnSubirRFCXpath = By.xpath("//button[@id='registrarInformacion-j_idt682']");
	
	By btnEnviarInfoXpath = By.xpath("//*[@id=\"registrarInformacion-j_idt722\"]");
	
	By buttonXpath = By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[3]/center/input[1]");
	
	public PomPanp(SeleniumUtil u, String user, TestCase testCase) {
		super();
		this.u = u;
		this.user = user;
		this.testCase = testCase;
		this.pomUlt = new pomUltimus(u);
	}
	
	public void clickButton() {	
		
		u.waitWebElement(buttonXpath, 30, 2);
		u.highLight(buttonXpath);
		u.click(buttonXpath);
		
		u.hardWait(10);
		
		clickButton();
	}
	
	public void accessPega(BaseExecution bExecution) throws FindFailed {
		System.out.println("TestCrear case "+testCase.getFullTestName());
		
		////////////////////////////////////////////////////////////////////////////
		bExecution.addStep("Acceder a la url http://10.185.176.11:7011/prweb/PRWebLDAP1 o http://10.185.176.11:7011/prweb/");
		testCase.addScreenShotCurrentStep(u, "Se muestra la pantalla de log in de PEGA");
		assertTrue(validateAccessUrlPega(), "No se muestra el acceso a la pantalla ");
		
		///////////////////////////////////////////////////////////////////////////
		bExecution.addStep("Ingresar usuario y contraseña");
		loginPega();
		testCase.addScreenShotCurrentStep(u, "Se accede al portal de aplicaciones");
		assertTrue(validateAccessPega(), "Falló el acceso al portal de aplicaciones");
		
		screen.click();
		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "inicio-apps.png");
		
		screen.wait(p1, 60.0);
		screen.click();
		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "mostrar-apps-web.png");
		
		screen.wait(p1, 60.0);
		screen.click();
		
		u.waitWebElement(btnMostrarAppsWebXpath, 10, 2);
		System.out.println("Se encontro el boton");
		
		///////////////////////////////////////////////////////////////////////////
		bExecution.addStep("Dar clic sobre el botón \"Mostrar aplicaciones web\"");
		u.click(btnMostrarAppsWebXpath);
		testCase.addScreenShotCurrentStep(u, "Se muestra el listado de las aplicaciones a las que tenga permiso el usuario.");
		assertTrue(validateListadoAplicaciones(), "No se muestra el listado de aplicaciones");
	}
	
	
	public void loginUltimus() {
		u.screenShotFull("Login de Ultimus");
		u.clear(txtUserIdXpath);
		u.highLight(txtUserIdXpath);
		u.sendKeys(txtUserIdXpath, user);
		
		u.highLight(btnLogOnXpath);
		testCase.addScreenShotCurrentStep(u, "Click en el botón LogOn");
		u.click(btnLogOnXpath);		
	}
	
	/**
	 * @author José Zárate
	 * Dentro de ultimus seleciona el portal Panp3 y obtiene el Id de la pagina actual 
	 * @throws FindFailed
	 */
	public void accessPanp3() throws FindFailed {
		mainTab = u.getDriver().getWindowHandle();
		
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "panp3.png");		
		screen.wait(p1, 30.0);
		screen.doubleClick(p1);
		
		System.out.println("Pagina actual Ultimus: "+ mainTab);		
	}
	
	public void selectInbox() throws FindFailed {
		u.getDriver().switchTo().frame("tabMain__ctl0_fcovgleen01.comercio.fne-Predeterminado");
		System.out.println("Entra a buscar el botón de inbox");
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "inbox.png");
		screen.wait(p1, 30.0);
		screen.click();
		pomUlt.loadingUltimus();
	}
	
	public void selectRefresh() throws FindFailed {
		u.highLight(btnRefreshXpath);
		u.click(btnRefreshXpath);
		System.out.println("Se actualizó la lista");
		statusRefresh = false;
		pomUlt.loadingUltimus();
	}
	
	public void accessAltaProvedor(String RFC) throws InterruptedException {
			
		handles = u.getDriver().getWindowHandles();
			
		for(String actual : handles) {
			System.out.println("-- Handle ID: "+ actual);
			if(!actual.equalsIgnoreCase(mainTab)) {
				System.out.println("-- Cambiando el focus de pagina --");
				u.getDriver().switchTo().window(actual);
					
				System.out.println("Se realizó el switch");
				if(u.waitWebElement(txtRFCXpath, 20, 2) != null) {
					System.out.println("Se encontro el campo para el Rfc");
					llenarFormulario(RFC);
				}
			}			
		}	
	}
	
	public void llenarFormulario(String RFC) throws InterruptedException {
		
		u.highLight(txtRFCXpath);
		u.sendKeys(txtRFCXpath, RFC);
		
		u.highLight(btnBuscarRFCXpath);
		u.click(btnBuscarRFCXpath);
		
		u.waitWebElement(txtRazonSocialXpath, 30, 2);
		Thread.sleep(3000);
		u.highLight(txtRazonSocialXpath);
		u.sendKeys(txtRazonSocialXpath, RFC);
		
		u.highLight(txtEmailXpath);
		u.sendKeys(txtEmailXpath, "josez4@hexaware.com");
		
		list = new Select(u.getDriver().findElement(selectCategoriaXpath));
		list.selectByIndex(2);
		
		u.hardWait(2);
		list = new Select(u.getDriver().findElement(selectTipoDistribucionXpath));
		list.selectByIndex(1);
		
		u.hardWait(2);
		list = new Select(u.getDriver().findElement(selectPlazoXpath));
		list.selectByIndex(1);
		
		testCase.addScreenShotCurrentStep("Muestra los primeros campos capturados");
		u.hardWait(1);
		
		u.scrollToElement(flechaXpath);
		
		u.click(liPlazaXpath);
		u.highLight(flechaXpath);
		u.click(flechaXpath);		
		u.scrollToElement(btnEnviarXpath);
	}
	
	public void clickEnviar() {
		u.highLight(btnEnviarXpath);
		u.click(btnEnviarXpath);
	}
	
	public void clickContinuar() {
		u.waitWebElement(btnContinuarXpath, 10, 2);
		//u.highLight(btnContinuarXpath);
		//u.click(btnContinuarXpath);
		
		//if(!actual.equalsIgnoreCase(mainTab)) {
		System.out.println("-- Cambiando el focus de página principal --");
		u.getDriver().switchTo().window(mainTab);			
		System.out.println("Se realizó el switch a la página principal");
			/*if(u.waitWebElement(txtRFCXpath, 20, 2) != null) {
				System.out.println("Se encontro el campo para el Rfc");
				llenarFormulario();
			}*/
		//}		
	}
	
	public void buscaRFC(String RFC) {
		searchInTableUltimus(RFC);
	}
	
	/**
	 * Búsca en la tabla de Ultimus el valor esperado. Dependiendo del módulo se
	 * recomienda buscar por: Inicio: Proceso. Inbox: Incidente. Completado:
	 * Incidente.
	 * 
	 * @param dato -> Proceso, Incidente
	 * @return String[] posición 0->fila. posición 1->innerText de la fila.
	 * @author 1000073289
	 */
	public String[] searchInTableUltimus(String dato) {
		String[] results = new String[2];
		results[0] = "";
		results[1] = "";
		Actions act = new Actions(u.getDriver());
		try {
			Boolean aux = false;
			Integer row = 0;
			List<WebElement> elementsTable = u.getDriver().findElements(tableUltimus);
			System.out.println("Elementos en la Tabla: " + elementsTable.size());
			for (int i = 0; i < elementsTable.size(); i++) {
				String element = elementsTable.get(i).getAttribute("innerText");
				//System.out.println("->" + element + " : " + dato);
				if (element.contains(dato)) {
					row = i;
					results[0] = Integer.toString(row);
					results[1] = element;
					System.out.println("Se encontró en row: " + row);
					By selectElement = null;
					selectElement = By.cssSelector("#WebSplitter1xtmpl1xUltraWebGrid1_r_" + row);
					testCase.addScreenShotCurrentStep(u,"");
					
					//mainTab = u.getDriver().getWindowHandle();
					act.doubleClick(u.getDriver().findElement(selectElement)).build().perform();
					//System.out.println("Pagina actual Ultimus: "+ mainTab);	
					
					u.hardWait(1);
					aux = true;
					break;
				}
			}
			if (!aux) {
				//throw new AssertionError("No se encontró coincidencia en la tabla");
				clickSiguiente();
				buscaRFC(dato);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Falló al buscar en la tabla");
		}
		return results;
	}
	
	public void clickSiguiente() throws FindFailed {
		
		/*if(count == 0 && statusRefresh){
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "btn-siguiente.png");
			screen.wait(p1, 30.0);
			screen.click();
			
			count =+1;
			pomUlt.loadingUltimus();
		}else*/ if(count == 1 && statusRefresh) {
			selectRefresh();
			selectListInicio();
			pomUlt.loadingUltimus();
		}/*else if(!statusRefresh){
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "btn-ultimo.png");
			screen.wait(p1, 30.0);
			screen.click();
			
			count =+1;
			pomUlt.loadingUltimus();
		}*/	
		else {
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "btn-siguiente.png");
			screen.wait(p1, 30.0);
			screen.click();
			
			count =+1;
			pomUlt.loadingUltimus();
		}
	}
	
	public void selectListInicio() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "inicio-lista.png");
		screen.wait(p1, 30.0);
		screen.click();
		pomUlt.loadingUltimus();
	}
	
	public void deleteCookies() {
		u.getDriver().manage().deleteAllCookies();
	}
	
	public void accessAdminConvenios() {
		handles = u.getDriver().getWindowHandles();
		List<String> windowlist = new ArrayList<String>(handles);
		mainTab = u.getDriver().getWindowHandle();
		System.out.println("VENTANAS - " + windowlist.size());
		System.out.println("->" + windowlist);
		System.out.println("-> ACTUAL: " + mainTab);
		
		for(String actual : handles) {
			System.out.println("-- Handle ID: "+ actual);
			if(!actual.equalsIgnoreCase(mainTab)) {
				System.out.println("-- Cambiando el focus de pagina --");
				u.getDriver().switchTo().window(windowlist.get(2));
					
				System.out.println("Se realizó el switch");
				System.out.println("La pagina actual es: "+u.getDriver().getWindowHandle());
				/*if(u.waitWebElement(radioButtonSinConvenioXpath, 20, 2) != null) {
					System.out.println("Se encontro el RADIO BUTTON");
				}*/
				break;
			}		
		}	
	}
	
	public void RegistraConvenio(String RFC) {
		u.waitWebElement(btnBuscarRFCConveniosXpath, 10, 2);
		u.highLight(radioButtonSinConvenioXpath);
		u.click(radioButtonSinConvenioXpath);
		
		u.highLight(btnBuscarRFCConveniosXpath);
		u.click(btnBuscarRFCConveniosXpath);
		
		u.waitWebElement(txtConvenioRazonSocialXpath, 10, 2);
		u.highLight(txtConvenioRazonSocialXpath);
		u.sendKeys(txtConvenioRazonSocialXpath, RFC);
		
		u.highLight(btnConvenioEnviarXpath);
		u.click(btnConvenioEnviarXpath);
	}	
	
	public void loginPega() {
		u.clear(txtUserXpath);
		u.highLight(txtUserXpath);
		u.sendKeys(txtUserXpath, user);
		
		u.highLight(btnInicioXpath);
		testCase.addScreenShotCurrentStep(u, "Click en el botón iniciar sesión");
		u.click(btnInicioXpath);
	}
	
	public void loginPortalAplicaciones(String RFC, String pass) {		
		u.highLight(txtUsuarioXpath);
		u.sendKeys(txtUsuarioXpath, RFC);
		
		u.highLight(txtPassXpath);
		u.sendKeys(txtPassXpath, pass);
		
		u.highLight(btnLoginButtonXpath);
		u.click(btnLoginButtonXpath);
	}
	
	public void cambioPass(String newPass) {
		u.highLight(txtContraseniaXpath);
		u.sendKeys(txtConfContraseniaXpath, newPass);
		
		u.highLight(txtConfContraseniaXpath);
		u.sendKeys(txtConfContraseniaXpath, newPass);
		
		u.highLight(btnAceptarXpath);
		u.click(btnAceptarXpath);
	}
	
	public void clickPanpV3() {
		u.highLight(btnAutomatizacionXpath);
		u.click(btnAutomatizacionXpath);
	}
	
	public void aceptarAviso() {
		WebElement moveToelement = u.getDriver().findElement(btnAceptarAvisoXpath);
	    Coordinates coordinate = ((Locatable) moveToelement).getCoordinates();
	    coordinate.onPage();
	    coordinate.inViewPort();
	     
		u.highLight(btnAceptarAvisoXpath);
		u.click(btnAceptarAvisoXpath); 
	}
	
	public void infoProveedor() {		
		u.scrollToElement(txtCalleNumXpath);
		u.highLight(txtCalleNumXpath);
		u.sendKeys(txtCalleNumXpath, "Pico de almanzanzor 1201");
		
		u.highLight(txtEntreCallesXpath);
		u.sendKeys(txtEntreCallesXpath, "San Blas y Magueyes");
		
		u.highLight(txtColoniaXpath);
		u.sendKeys(txtColoniaXpath, "Las puentes");
		
		u.highLight(txtCiudadXpath);
		u.sendKeys(txtCiudadXpath, "San Nicolas");
		
		list = new Select(u.getDriver().findElement(selectEstadoXpath));
		list.selectByIndex(19);
		
		u.highLight(txtCodigoPostalXpath);
		u.sendKeys(txtCodigoPostalXpath, "66460");
		
		list = new Select(u.getDriver().findElement(selectFronteraXpath));
		list.selectByIndex(2);
		
		u.scrollToElement(txtNombreXpath);
		u.highLight(txtNombreXpath);
		u.sendKeys(txtNombreXpath, "Francisco");
		
		u.highLight(txtApellidosXpath);
		u.sendKeys(txtApellidosXpath, "Perez");
		
		u.highLight(txtLadaXpath);
		u.sendKeys(txtLadaXpath, "81");
		
		u.highLight(txtTelefocnoXpath);
		u.sendKeys(txtTelefocnoXpath, "83214568");
		
		u.highLight(txtCorreoXpath);
		u.sendKeys(txtCorreoXpath, "josez4@hexaware.com");
		
		u.scrollToElement(selectPaisXpath);
		list = new Select(u.getDriver().findElement(selectPaisXpath));
		list.selectByValue("MX");
		
		list = new Select(u.getDriver().findElement(selectDivisaXpath));
		list.selectByIndex(1);
		
		list = new Select(u.getDriver().findElement(selectBancoXpath));
		list.selectByValue("6138-0012");
		
		list = new Select(u.getDriver().findElement(selectSucursalXpath));
		list.selectByValue("6765");
		
		u.highLight(txtCuentaBancaria);
		u.sendKeys(txtCuentaBancaria, "012580027864713668");
		
		u.highLight(txtConfCuentaXpath);
		u.sendKeys(txtConfCuentaXpath, "012580027864713668");
		
		u.highLight(txtABAXpath);
		u.sendKeys(txtABAXpath, "012580027864713668");
		
		u.highLight(txtANombreDeXpath);
		u.sendKeys(txtANombreDeXpath, "Francisco Perez");
		
		u.scrollToElement(elementPlazaMtyXpath);
		u.highLight(elementPlazaMtyXpath);
		u.click(elementPlazaMtyXpath);
		
		u.highLight(btnFlechaPlazaXpath);
		u.click(btnFlechaPlazaXpath);
	}
	
	public void cargaEstadoCuenta(String archivo) {
		u.highLight(btnExaminarXpath);
		u.sendKeys(btnExaminarXpath, archivo);
		u.hardWait(4);
		
		u.highLight(checkBoxResponsabilidadXpath);
		u.click(checkBoxResponsabilidadXpath);
		
		u.highLight(btnAgregarPagoXpath);
		u.click(btnAgregarPagoXpath);
	}
	
	public Boolean validateAccessUltimus() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "access-ultimus.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;			
		}
		else{
			return false;
		}
	}
	
	public Boolean validateAccessUrlPega() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "pantalla-pega.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean validateAccessPega() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "menu-apps.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean validateListadoAplicaciones() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "listado-apps.png");
		
		if(screen.wait(p1, 60.0) != null){
			testCase.addScreenShotCurrentStep(u, "");
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean validateAccessAltaProvedor() {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "titulo-alta-provedor.png");
		Boolean status = false;
		
		try {
			if(screen.wait(p1, 60.0) != null){
				testCase.addScreenShotCurrentStep(u, "");
				return true;
			}else {
				return false;
			}
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	public Boolean validateLlenadoDeFormulario() {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "campos-capturados.png");
		Boolean status = false;
		
		try {
			if(screen.wait(p1, 60.0) != null){
				//testCase.addScreenShotCurrentStep(u, "");
				return true;
			}else {
				return false;
			}
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	public Boolean validateGuardado() {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "msn-info-enviada.png");
		Boolean status = false;
		
		try {
			if(screen.wait(p1, 60.0) != null){
				testCase.addScreenShotCurrentStep(u, "Muestra el mensaje esperado");
				return true;
			}else {
				testCase.addScreenShotCurrentStep(u, "No muestra el mensaje esperado");
				return false;
			}
		} catch (FindFailed e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	public Boolean validateAccessInbox() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "bandeja-de-entrada.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}		
	}
	
	public Boolean validateAccessAdminConvenios() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "administracion-de-convenios.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}		
	}
	
	public Boolean validateEnvioConvenio() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "info-guardada.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}		
	}
	
	public Boolean validateAccessPortalAplicaciones() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "portal-aplicaciones.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}		
	}
	
	public Boolean validateAccessCambioPass() {
		if(u.waitWebElement(txtContraseniaXpath, 30, 2) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	public Boolean validateAccessControl() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "access-control.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}	
	}
	
	public Boolean validateShowAviso() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "aviso-importante.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}	
	}
	
	public Boolean validateAccessInfoProveedor() throws FindFailed {
		s1 = sikuli.PatternPath(folder);
		p1 = sikuli.PatternImage(s1 + "informacion-proveedor.png");
		
		if(screen.wait(p1, 60.0) != null){
			return true;
		}else {
			return false;
		}	
	}
}
