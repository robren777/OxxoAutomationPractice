package EBS.om;

import java.time.Duration;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Pega {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	
	/*
	 * Localizadores Inicio de sesion
	 ******************************************************************************************/
	
	By xmlUser = By.xpath("//input[@id='txtUserID']");
	By xmlPassword = By.id("txtPassword");
	By xmlLogginButton = By.id("sub");
	
	By xmlHeader = By.xpath("//h1[@class='header-title']");	
	
	/*
	 * Localizadores Menu Principal
	 ******************************************************************************************/
	
	By xmlNavigation = By.xpath("//div[@data-node-id='PortalNavigation']");
	By xmlMisAplicaciones = By.xpath("//li[@title='Mis aplicaciones']//span[2]/span");
	By xmlMisAplicacionesLb = By.xpath("//div[contains(text(),'Mis aplicaciones')]");
	
	By xmlUsuario = By.xpath("//*[@id='pyPortalHarnessWithoutImage']");
	By xmlCerrarSesion = By.xpath("//a[@role='menuitem']//span[contains(text(),'Cerrar sesi�n')]");
		
	/*
	 * Localizadores Mis Aplicaciones
	 ******************************************************************************************/
	By xmlPortalDevoluciones = By.xpath("//table[@title='Portal Devoluciones']");
	By xmlPortalSARv3 = By.xpath("//table[@title='']");

	/**
	 * Constructor
	 ******************************************************************************************/
	
	public Pega(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/**
	 * METODOS 
	 ******************************************************************************************/
	
	/*
	 * Metodos Inicio de Sesi�n
	 ******************************************************************************************/
 
	/**
	 * Metodo para iniciar sesion, ingresa usuario y contrase�a
	 * @param dato (String): Nombre de usuario
	 * @author Andrea Contreras
	 */
	public void logIn(String dato) {
		
		u.highLight(xmlUser);
		u.sendKeys(xmlUser,dato);		
		u.highLight(xmlLogginButton);
		testCase.addScreenShotCurrentStep(u, "inputs");
		u.click(xmlLogginButton);
		System.out.print("1: "+u.getDriver().getTitle());
		me.explicitWaitVisibilityOfElement(xmlHeader, Duration.ofSeconds(25));
		System.out.print("3: "+u.getDriver().getTitle());
		testCase.addScreenShotCurrentStep(u, "fin");
		
	}
	
	
	/**
	 * Metodo para iniciar sesion, ingresa usuario y contrase�a
	 * @param dato1 (String): Nombre de usuario
	 * @param dato2 (String): Contrase�a
	 * @author Andrea Contreras
	 */
	public void logIn(String dato1, String dato2) {
		
		u.highLight(xmlUser);
		u.sendKeys(xmlUser,dato1);
		
		u.highLight(xmlPassword);
		u.sendKeys(xmlPassword,dato2);
		
		u.highLight(xmlLogginButton);
		testCase.addScreenShotCurrentStep(u, "inputs");
		u.click(xmlLogginButton);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "fin");
		
	}
	
	
	
	/*
	 * Metodos - Menu Principal
	 ******************************************************************************************/

	/**
	 * Metodo para desplegar opciones del apartado Navigation
	 * @author Andrea Contreras
	 */
	public void mouseOverNavigation() {
		
		testCase.addScreenShotCurrentStep(u, "pantallaPrincipal");
		me.explicitWaitVisibilityOfElement(xmlNavigation, Duration.ofSeconds(10));	
		u.mouseOver(xmlNavigation);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "ventanaNavigation");
		
	}
	
	/**
	 * Metodo para cerrar sesion en el portal de PEGA
	 * @author Andrea Contreras
	 */
	public void cerrarSesion() {
		
		u.click(xmlUsuario);
		u.highLight(xmlCerrarSesion);
		testCase.addScreenShotCurrentStep(u, "opcionCerrarSesion");
		u.click(xmlCerrarSesion);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "sesionCerrada");
		
	}
	
	/*
	 * Metodos - Submenu Mis Aplicaciones
	 ******************************************************************************************/
	
	
	/**
	 * Metodo para dar click en la opcion 'Mis Aplicaciones'
	 * @author Andrea Contreras
	 */
	public void clickMisAplicaciones() {
		
		me.explicitWaitToBeClickable(xmlMisAplicaciones, Duration.ofSeconds(20));	
		u.highLight(xmlMisAplicaciones);
		testCase.addScreenShotCurrentStep(u, "opcionApps");		
		u.click(xmlMisAplicaciones);
		me.explicitWaitVisibilityOfElement(xmlMisAplicacionesLb, Duration.ofSeconds(10));
		testCase.addScreenShotCurrentStep(u, "clickApps");
		
	}
	
	/**
	 * Metodo para dar click en la opcion 'PortalDevoluciones'
	 * @author Andrea Contreras
	 */
	public void clickPortalDevoluciones() {
		
		u.highLight(xmlPortalDevoluciones);
		testCase.addScreenShotCurrentStep(u, "opcionDev");
		u.click(xmlPortalDevoluciones);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickDev");
		
	}
	
	/**
	 * Metodo para dar click en la opcion 'Portal SAR'
	 * @author Andrea Contreras
	 */
	public void clickPortalSARv3() {
		
		u.highLight(xmlPortalSARv3);
		testCase.addScreenShotCurrentStep(u, "opcionSAR");
		u.click(xmlPortalSARv3);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickSAR");
		
	}
	
	/**
	 * Metodo para validar que exista la opcion para ingresar al Portal de SAR
	 * @return True o False si existe la opcion 'Portal de SAR'
	 * @author Andrea Contreras
	 */
	public boolean existePortalSAR() {
		
		boolean existe = me.existElement(xmlPortalDevoluciones);
		
		return existe;
		
	}
	
	

}
