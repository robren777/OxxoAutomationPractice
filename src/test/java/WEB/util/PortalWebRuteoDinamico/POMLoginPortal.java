package WEB.util.PortalWebRuteoDinamico;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import WEB.util.GlobalVariables;
import utils.selenium.SeleniumUtil;

public class POMLoginPortal {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	
	private String user = GlobalVariables.usuarioPortalWebRD_QA;
	private String pass = GlobalVariables.contrasenaPortalWebRD_QA;

	public POMLoginPortal(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}
	
	// Your connection isn't private
	private By btnAdvance = By.id("details-button");
	private By linkContinue = By.id("proceed-link");

	// LOGIN
	private By locatorUserInput = By.xpath("//input[@id='email']");
	private By locatorPassInput = By.xpath("//input[@id='password']");
	private By locatorLoginButton = By.xpath("//button[@id='login']");

	// ICONO RUTEO DINAMICO
	private By locatorMenuInicio = By.xpath("//div[@class='container position-relative']");
	private By locatorIconRuteoDinamico = By.xpath("//a[normalize-space()='PORTAL RUTEO DIN�MICO']");
	
	// PAGINA PRINCIPAL RUTEO DINAMICO
	private By locatorMenuInicioPortalRD = By.xpath("//div[@class='container-fluid justify-content-between']");
	private By locatorMenuOpcionesPortalRD = By.xpath("//div[@class='container-fluid justify-content-center']");
	private By locatorVolverButton = By.xpath("//a[normalize-space()='Volver']");
	private By locatorCerrarSesionButton = By.xpath("//a[normalize-space()='Cerrar Sesi�n']");

	/**
	 * Login Portal Web Ruteo Dinamico
	 * 
	 * @param user Usuario de acceso al portal
	 * @param pass Contrasena de acceso al portal
	 * @return Boolean True si existen los elementos al hacer login exitoso, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean loginAccessControl() throws Exception {
		Boolean existencia = false;
		try {
			Integer cont = 0;
			u.hardWait(1);
			u.getDriver().manage().deleteAllCookies();
			String url = GlobalVariables.URL_PORTAL_RD_QA;
			testCase.addTextEvidenceCurrentStep(url);
			u.get(url);
			u.getDriver().manage().window().maximize();

			if (u.getDriver().findElements(btnAdvance).size() != 0) {
				u.click(btnAdvance);
				u.waitWebElement(linkContinue, 20, 2);
				u.click(linkContinue);
			}

			u.waitWebElement(locatorUserInput, 15, 2);
			testCase.addScreenShotCurrentStep("");

			do {
				cont += 1;
				if (cont < 4) {
					wait.until(ExpectedConditions.elementToBeClickable(locatorLoginButton));
					u.highLight(locatorUserInput);
					clearInput(locatorUserInput);
					u.sendKeys(locatorUserInput, user);
					u.highLight(locatorPassInput);
					clearInput(locatorPassInput);
					u.sendKeys(locatorPassInput, pass);
					u.highLight(locatorLoginButton);
					testCase.addScreenShotCurrentStep(u, "Credenciales");
					u.click(locatorLoginButton);
					try {
						existencia = validarIconoPortalRD();
					} catch (Exception e) {
						existencia = false;
					}
				} else {
					u.getDriver().navigate().refresh();
					cont = 0;
				}
			} while (!existencia);
			testCase.addScreenShotCurrentStep(u, "AccessControl");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se pudo iniciar sesion");
		}
		return existencia;
	}
	
	/**
	 * Espera a que el elemento se muestre, lo resalta, toma un screenshot, y le da click
	 * 
	 * @param name Nombre del boton, enlace u opcion
	 * @param elementBy Elemento locator a hacer clic
	 * @param waitTime El tiempo de espera al realizar el clic
	 * @author cristhian.m.gonzalez
	 */
	public void clickOpcion(String name, By elementBy, int waitTime) throws Exception {
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			testCase.addScreenShotCurrentStep(u, "Dar clic en " + name);
			u.unhighLight(elementBy);
			u.click(elementBy);
			u.hardWait(waitTime);
			testCase.addScreenShotCurrentStep("");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar clic en: " + name);
		}
	}
	
	/**
	 * Acceso al Portal Web de Ruteo Dinamico
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickPortalRuteoDinamico() throws Exception {
		try {
			clickOpcion("PORTAL RUTEO DINAMICO", locatorIconRuteoDinamico, 15);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en PORTAL RUTEO DINAMICO");
		}
	}
	
	/**
	 * Volver al menu principal de Access Control
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickVolver() throws Exception {
		try {
			clickOpcion("Volver", locatorVolverButton, 15);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Volver");
		}
	}
	
	/**
	 * Volver al menu principal de Access Control
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickCerrarSesion() throws Exception {
		try {
			clickOpcion("Cerrar Sesion", locatorCerrarSesionButton, 15);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Cerrar Sesion");
		}
	}

	/**
	 * Limpia un campo de texto
	 * 
	 * @param inputBy Es el campo a limpiar contenido
	 * @author cristhian.m.gonzalez
	 */
	public void clearInput(By inputBy) throws Exception {
		try {
			WebElement toClear = u.getDriver().findElement(inputBy);
			toClear.sendKeys(Keys.CONTROL + "a");
			toClear.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al limpiar el campo");
		}
	}
	
	/**
	 * Valida que se vea el icono del portal web ruteo dinamico de la pagina de inicio del access control
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarIconoPortalRD(){
		Boolean existencia = false;
		try {
			u.hardWait(30);
			wait.until(ExpectedConditions.elementToBeClickable(locatorMenuInicio));
			wait.until(ExpectedConditions.elementToBeClickable(locatorIconRuteoDinamico));
			existencia = true;
			u.highLight(locatorMenuInicio);
			u.highLight(locatorIconRuteoDinamico);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorMenuInicio);
			u.unhighLight(locatorIconRuteoDinamico);			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la pagina de inicio");
		}
		return existencia;
	}
	
	/**
	 * Valida elementos principales del portal web ruteo dinamico
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarPortalRuteoDinamico(){
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorMenuInicioPortalRD));
			wait.until(ExpectedConditions.elementToBeClickable(locatorMenuOpcionesPortalRD));
			wait.until(ExpectedConditions.elementToBeClickable(locatorVolverButton));
			wait.until(ExpectedConditions.elementToBeClickable(locatorCerrarSesionButton));
			existencia = true;
			u.highLight(locatorMenuInicioPortalRD);
			u.highLight(locatorMenuOpcionesPortalRD);
			u.highLight(locatorVolverButton);
			u.highLight(locatorCerrarSesionButton);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorMenuInicioPortalRD);
			u.unhighLight(locatorMenuOpcionesPortalRD);
			u.unhighLight(locatorVolverButton);
			u.unhighLight(locatorCerrarSesionButton);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos del portal de inicio de ruteo dinamico");
		}
		return existencia;
	}

}
