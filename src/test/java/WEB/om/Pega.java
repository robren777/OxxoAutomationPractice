package WEB.om;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Pega {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

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

	/*
	 * Localizadores Para Configuración Alertas
	 ******************************************************************************************/
	By notificacionesBTN = By.xpath("//span[contains(@class,'explorer_primary')][normalize-space()='Notificaciones']");

	By registrosBTN = By
			.cssSelector("This element is in iframe - div[id='headerlabel1947'] h3[class='layout-group-item-title']");
	By anadirRegistroBTN = By.xpath(
			"/html[1]/body[1]/div[2]/form[1]/div[3]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[8]/div[2]/div[1]/span[1]/div[1]/div[1]/div[1]/span[1]/div[1]/div[1]/span[1]/div[1]/div[1]/div[1]/span[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[1]/div[4]/table[1]/tbody[1]/tr[1]/td[1]/span[1]/a[1]");

	By iframeNotif = By.xpath("//iframe[starts-with(@src,'/prweb/app/notificaciones-automaticas/')]");
	private static final By IFRAME_NOTIF = By.xpath("//iframe[@title='Notificaciones']");
	By registros = By.xpath(
			"//div[contains(@class,'header') and @role='tab' and @data-role='tab' and @aria-label='Registros' and not(contains(@class,'lg-transition-all-linear')) and @aria-selected='false' and @tabindex='-1']");
	By idRegistro = By.xpath("(//input[@id='Id1'])[1]");

	By botonGuardar = By.xpath("//button[normalize-space()='Guardar']");
	By celdasId = By.xpath("//*[normalize-space(.)='Id (required)']/ancestor::table[1]//tbody//tr//td[1]");
	By celdasIdFallback = By.xpath(
			"//*[normalize-space(.)='Id (required)']/following::div[normalize-space()][not(self::th or self::td)][1]/ancestor::*[self::div or self::section][1]"
					+ "//*[normalize-space() and not(.='Id (required)')]");

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
	 * 
	 * @param dato (String): Nombre de usuario
	 * @author Andrea Contreras
	 */
	public void logIn(String dato) {

		u.highLight(xmlUser);
		u.sendKeys(xmlUser, dato);
		u.highLight(xmlLogginButton);
		testCase.addScreenShotCurrentStep(u, "inputs");
		u.click(xmlLogginButton);
		System.out.print("1: " + u.getDriver().getTitle());
		me.explicitWaitVisibilityOfElement(xmlHeader, Duration.ofSeconds(25));
		System.out.print("3: " + u.getDriver().getTitle());
		testCase.addScreenShotCurrentStep(u, "fin");

	}

	/**
	 * Metodo para iniciar sesion, ingresa usuario y contrase�a
	 * 
	 * @param dato1 (String): Nombre de usuario
	 * @param dato2 (String): Contrase�a
	 * @author Andrea Contreras
	 * @date ultima ejecución/utilización : 24/Sep/2025
	 */
	public void logIn(String dato1, String dato2) {

		u.highLight(xmlUser);
		u.sendKeys(xmlUser, dato1);

		u.highLight(xmlPassword);
		u.sendKeys(xmlPassword, dato2);

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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
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
	 * 
	 * @return True o False si existe la opcion 'Portal de SAR'
	 * @author Andrea Contreras
	 */
	public boolean existePortalSAR() {

		boolean existe = me.existElement(xmlPortalDevoluciones);

		return existe;

	}

	/*
	 * Metodos - Submenu Notificaciones
	 ******************************************************************************************/

	/**
	 * Metodo para validar que Exista el btn de Notificaciones y dar click
	 * 
	 * @return True o False si existe la opcion Notificaciones
	 * @author Ernesto Rosas
	 * @date 24/Sep/2025
	 */
	public boolean clickBTNNotificaciones() {
		try {
			u.highLight(notificacionesBTN);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "En pantalla notificaciones");
			u.click(notificacionesBTN);
			u.hardWait(5);

		} catch (NoSuchElementException e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return false;
		}
		return true;
	}

	/**
	 * Metodo "Soporte" para que los demás metodos sean rodeados automáticamente de
	 * Try/catch y
	 * 
	 * Puedan ser ejecutados en el IFRAME que la app PEGA necesite o sea objetivo de
	 * la prueba.
	 * 
	 * 
	 * @param IFRAME - Nombre del IFRAME a testear e ingresar.
	 * @param ACTION - Método y acciones correspondientes a ejecutar dentro del
	 *               IFRAME.
	 * @author Ernesto Rosas
	 * @date 24/Sep/2025
	 */
	public void inFrame(By iframe, Runnable action) {
		WebDriver d = u.getDriver();
		WebDriverWait w = new WebDriverWait(d, Duration.ofSeconds(20));
		d.switchTo().defaultContent();
		try {
			w.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));
			action.run();
		} finally {
			d.switchTo().defaultContent();
		}
	}

	/**
	 * Metodo para validar que Exista el btn de Registros
	 * 
	 * 
	 * @author Ernesto Rosas
	 * @date 24/Sep/2025
	 */
	public void abrirRegistros() {
		inFrame(IFRAME_NOTIF, () -> {
			System.out.println("Intentando dar click en Registros, dentro de IFRAME");
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(registros)).click();
			u.hardWait(5);
			System.out.println("Click exitoso  en Registros, dentro de IFRAME");
		});
	}

	/**
	 * Metodo para dar click en el botón AÑADIR REGISTROS en el IFRAME REGISTROS
	 * 
	 * 
	 * @author Ernesto Rosas
	 * @date 24/Sep/2025
	 */
	public void anadirRegistro() {
		inFrame(IFRAME_NOTIF, () -> {

			System.out.println("Intentando dar click en AÑADIR REGISTRO, dentro de IFRAME");
			u.hardWait(2);
			u.click(anadirRegistroBTN);
			testCase.addScreenShotCurrentStep(u, "Llenando info");
			u.hardWait(2);
			System.out.println("Click exitoso  en AÑADIR REGISTRO, dentro de IFRAME");
		});
	}

	/**
	 * Metodo para Añadir información en el nuevo registro a crear dentro del IFRAME
	 * NOTIFICACIONES - Variante 1 y Validar que se guarda correctamente
	 * 
	 * @param SE NECESITA INGRESAR EL NUM SIG DE REGISTRO A CREAR
	 * 
	 * @author Ernesto Rosas
	 * @date 24/Sep/2025
	 */
	public void registrarInfoNueva1(int numSig, String email, String nombreAlerta, String fechaAlertado,
			String asuntoCorreo, String cuerpoCorreo) {
		inFrame(IFRAME_NOTIF, () -> {
			System.out.println("Añadiendo info en nuevo registro");

			u.hardWait(4);
			Actions act = new Actions(u.getDriver());
			act.sendKeys(String.valueOf(numSig)).sendKeys(Keys.TAB).sendKeys(nombreAlerta).sendKeys(Keys.TAB)
					.sendKeys(fechaAlertado).sendKeys(Keys.TAB).perform();
			u.hardWait(5);

			Actions act2 = new Actions(u.getDriver());
			act2.sendKeys(asuntoCorreo).sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.TAB)
					.sendKeys(Keys.TAB).sendKeys(email).sendKeys(Keys.TAB).sendKeys(cuerpoCorreo).perform();
			u.hardWait(5);

			// Guardar y confirmar prueba
			u.hardWait(2);
			u.highLight(botonGuardar);
			u.scrollToElement(botonGuardar);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Finalizado llenado Info");
			u.click(botonGuardar);
			u.hardWait(2);
			u.scrollToElement(anadirRegistroBTN);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(u, "Captura nuevo registro");
			u.hardWait(5);
		});
	}

	/**
	 * Metodo para Añadir información en el nuevo registro a crear dentro del IFRAME
	 * NOTIFICACIONES - variante 2 y Validar que se guarda correctamente
	 * 
	 * @param SE NECESITA INGRESAR EL NUM SIG DE REGISTRO A CREAR
	 * @author Ernesto Rosas
	 * @date 24/Sep/2025
	 */
	public void registrarInfoNueva2(int numSig, String email, String nombreAlerta, String fechaAlertado,
			String asuntoCorreo, String cuerpoCorreo) {
		inFrame(IFRAME_NOTIF, () -> {
			System.out.println("Añadiendo info en nuevo registro");

			u.hardWait(4);
			Actions act = new Actions(u.getDriver());
			act.sendKeys(String.valueOf(numSig)).sendKeys(Keys.TAB).sendKeys(nombreAlerta)
					.sendKeys(Keys.TAB).sendKeys(fechaAlertado).sendKeys(Keys.TAB).perform();
			u.hardWait(5);

			Actions act2 = new Actions(u.getDriver());
			act2.sendKeys(asuntoCorreo).sendKeys(Keys.TAB).sendKeys(Keys.TAB)
					.sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(Keys.TAB).sendKeys(email).sendKeys(Keys.TAB)
					.sendKeys(cuerpoCorreo).perform();

			u.hardWait(5);

			// Guardar y confirmar prueba
			u.hardWait(2);
			u.highLight(botonGuardar);
			u.scrollToElement(botonGuardar);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Finalizado llenado Info");
			u.click(botonGuardar);
			u.hardWait(2);
			u.scrollToElement(anadirRegistroBTN);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(u, "Captura nuevo registro");
			u.hardWait(5);
		});
	}

	/**
	 * Metodo para OBTENER Numero siguiente de registro dentro del IFRAME
	 * NOTIFICACIONES.
	 * 
	 * @return El número siguiente consecutivamente a ingresar- al registro ANTERIOR
	 * @author Ernesto Rosas
	 * @date 24/Sep/2025
	 */
	public int obtenerSiguienteId() {
		final int[] next = { 1 };
		inFrame(IFRAME_NOTIF, () -> {
			WebDriver d = u.getDriver();

			List<WebElement> celdas = d.findElements(celdasId);
			if (celdas.isEmpty())
				celdas = d.findElements(celdasIdFallback);

			// Espera a tener al menos una fila
			if (celdas.isEmpty())
				throw new NoSuchElementException("No encontré celdas de ID");

			// Extrae enteros, ignora vacíos/no numéricos y calcula el máximo
			int max = celdas.stream().map(WebElement::getText).map(String::trim).filter(s -> s.matches("\\d+"))
					.mapToInt(Integer::parseInt).max().orElse(0);

			next[0] = max + 1;
			testCase.addTextEvidenceCurrentStep("El Siguiente registro a insertar es: " + next[0]);
			System.out.println(next[0]);
		});
		return next[0];
	}

}
