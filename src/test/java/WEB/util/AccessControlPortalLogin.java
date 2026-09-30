package WEB.util;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.selenium.SeleniumUtil;
import modelo.TestCase;

public class AccessControlPortalLogin {

	/**
	 * Se crean todos los metodos invocar a Selenium
	 * 
	 * 
	 * @author Emmanuel Gomez
	 * @return Metodos con clases propias
	 * @date 02/Diciembre/2025
	 */

	private SeleniumUtil u;
	private TestCase testCase;
	private AuxMethods aux;
	private WebDriverWait wait;
	private JavascriptExecutor js;

	// -----------------------------
	// CONSTRUCTOR
	// -----------------------------
	public AccessControlPortalLogin(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
		this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(40));
		this.js = (JavascriptExecutor) u.getDriver();
	}

	// -----------------------------
	// LOCALIZADORES LOGIN NORMAL
	// -----------------------------
	private By inputEmail = By.id("email");
	private By inputPassword = By.id("password");
	private By btnIngresar = By.id("login");

	// -----------------------------
	// LOCALIZADORES ADVERTENCIA
	// -----------------------------
	public By btnAdvance = By.id("details-button");
	public By linkContinue = By.id("proceed-link");

	// -----------------------------
	// LOCALIZADORES CAMBIO CONTRASEÑA
	// -----------------------------
	private By inputUsuarioCC = By.xpath("//input[@formcontrolname='username']");
	private By inputPassActualCC = By.xpath("//input[@formcontrolname='currentPassword']");
	private By inputPassNuevaCC = By.xpath("//input[@formcontrolname='newPassword']");
	private By inputPassConfirmCC = By.xpath("//input[@formcontrolname='passwordConfirmation']");
	private By btnGuardarCC = By.xpath("//button[contains(normalize-space(), 'Aceptar')]");
	private By CerrarModal = By.xpath("//button[@aria-label='Close']");

	// Botón del menú que lleva al cambio de contraseña
	private By linkCambiarPassword = By.xpath("//a[contains(text(),'Cambiar contraseña')]");
	// Navbar y logout (sin ID)
	private By locationNavbarMenu = By.id("dropdownBasic1");
	private By locationLogoutOption = By
			.xpath("//*[contains(text(),'Cerrar sesión') or contains(text(),'Logout') or contains(text(),'Salir')]");
	// Elemento del home
	private By locationMenuInicio = By.xpath("//a[text()='Inicio']");

	// -----------------------------
	// UTILIDAD PARA HIGHLIGHT
	// -----------------------------
	private void highlight(WebElement e, String color) {
		js.executeScript("arguments[0].style.border='3px solid " + color + "'", e);
		js.executeScript("arguments[0].scrollIntoView({behavior:'instant', block:'center'});", e);
	}

	/**
	 * Se crean todos los metodos que se pueden utilizar para el inicio de sesion
	 * 
	 * 
	 * @author Emmanuel Gomez
	 * @return Metodos con clases propias
	 * @date 26/Diciembre/2025
	 */
	// ============================================================
	// 1️ LOGIN NORMAL (SCRIPT 1)
	// ============================================================
	public boolean loginAccessControl1(String user, String ps) {
		try {

			u.hardWait(1);
			u.getDriver().manage().window().maximize();

			int intentos = 0;
			boolean repetirLogin = true;

			// --- ADVERTENCIA DE SEGURIDAD ---
			if (u.getDriver().findElements(btnAdvance).size() != 0) {
				u.click(btnAdvance);
				u.waitWebElement(linkContinue, 40, 2);
				u.click(linkContinue);
			}

			// --- ESPERAR CAMPOS DEL LOGIN ---
			u.waitWebElement(inputEmail, 20, 2);
			testCase.addScreenShotCurrentStep(u, "Pantalla de login lista");

			// ======================================================
			// BUCLE DE LOGIN
			// ======================================================
			while (repetirLogin) {

				intentos++;

				// Limpiar y escribir usuario
				u.highLight(inputEmail);
				clearInput(inputEmail);
				u.waitWebElement(inputEmail, 10, 1);
				u.sendKeys(inputEmail, user);

				// Limpiar y escribir contraseña
				u.highLight(inputPassword);
				clearInput(inputPassword);
				u.waitWebElement(inputPassword, 10, 1);
				u.sendKeys(inputPassword, ps);

				// Esperar que el botón ya esté clickable
				wait.until(ExpectedConditions.elementToBeClickable(btnIngresar));

				// Click en Ingresar
				u.highLight(btnIngresar);
				testCase.addScreenShotCurrentStep(u, "Intento de login #" + intentos);
				u.click(btnIngresar);

				// Validar avance
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(btnIngresar));
					repetirLogin = true;
				} catch (TimeoutException ex) {
					repetirLogin = false;
				}

				// Si falla tres veces
				if (intentos >= 3 && repetirLogin) {
					u.getDriver().navigate().refresh();
					u.waitWebElement(inputEmail, 15, 2);
					intentos = 0;
				}
			}

			testCase.addScreenShotCurrentStep(u, "Inicio de sesión exitoso");
			return true;

		} catch (Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error al intentar iniciar sesión");
			return false;
		}
	}

	/**
	 * Se crean el metodo para que se limpie el input donde se ponen los datos del
	 * login
	 * 
	 * @author Emmanuel Gomez
	 * @date 02/Diciembre/2025
	 */

	public void clearInput(By inputBy) {
		try {
			WebElement toClear = u.getDriver().findElement(inputBy);
			toClear.sendKeys(Keys.CONTROL + "a");
			toClear.sendKeys(Keys.DELETE);
		} catch (Exception e) {
			System.out.println("Fallo al limpiar el campo: " + e.getMessage());
		}
	}

	/**
	 * Se crean todos los metodos para validar que se ingrese al portal y aparezca
	 * el menu de inicio
	 * 
	 * @author Emmanuel Gomez
	 * @return si falla o no el metodo
	 * @date 02/Diciembre/2025
	 */
	public boolean validarAccesoPortal() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locationMenuInicio));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Se crea el metodo para que funcione el boton el cual hace nos redirecciona al
	 * formulario de cambio de contraseña
	 * 
	 * @author Emmanuel Gomez
	 * @return Metodos con clases propias
	 * @date 02/Diciembre/2025
	 */

	// 2️ IR A CAMBIAR CONTRASEÑA (SCRIPT 3 – PRIMER PASO)
	public boolean irACambiarContrasena() {
		try {

			WebElement link = wait.until(ExpectedConditions.elementToBeClickable(linkCambiarPassword));

			highlight(link, "orange");
			testCase.addScreenShotCurrentStep(u, "Botón Cambiar Contraseña localizado");
			link.click();

			return true;

		} catch (Exception e) {
			testCase.addScreenShotCurrentStep(u, "Error al ir a Cambiar Contraseña");
			testCase.addTextEvidenceCurrentStep("No fue posible abrir la pantalla de cambiar contraseña");
			return false;
		}
	}

	/**
	 * Se crean los metodos para que funcione el formulario del cambio de contraseña
	 * y se ingresen los datos, y tambien se crea el metodo del deslogueo
	 * 
	 * @author Emmanuel Gomez
	 * @return Mensajes de error por si logra fallar el cambio de contraseña
	 * @date 02/Diciembre/2025
	 */
	// ============================================================
	// 3️ LLENAR FORMULARIO CAMBIO DE CONTRASEÑA
	// ============================================================
	public boolean cambiarContrasena(String usuario, String passActual, String passNueva) {
		try {

			// --- ADVERTENCIA SEGURIDAD ---
			if (u.getDriver().findElements(btnAdvance).size() != 0) {
				u.click(btnAdvance);
				u.waitWebElement(linkContinue, 40, 2);
				u.click(linkContinue);
			}

			WebDriverWait wait2 = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));

			// Usuario
			WebElement userInput = wait2.until(ExpectedConditions.visibilityOfElementLocated(inputUsuarioCC));
			highlight(userInput, "blue");
			userInput.clear();
			userInput.sendKeys(usuario);

			// Contraseña actual
			WebElement passActualInput = wait2.until(ExpectedConditions.visibilityOfElementLocated(inputPassActualCC));
			highlight(passActualInput, "green");
			passActualInput.clear();
			passActualInput.sendKeys(passActual);

			// Nueva contraseña
			WebElement passNuevaInput = wait2.until(ExpectedConditions.visibilityOfElementLocated(inputPassNuevaCC));
			highlight(passNuevaInput, "purple");
			passNuevaInput.clear();
			passNuevaInput.sendKeys(passNueva);

			// Confirmación
			WebElement passConfirmInput = wait2
					.until(ExpectedConditions.visibilityOfElementLocated(inputPassConfirmCC));
			highlight(passConfirmInput, "orange");
			passConfirmInput.clear();
			passConfirmInput.sendKeys(passNueva);

			testCase.addScreenShotCurrentStep(u, "Formulario de cambio de contraseña completado");

			// Guardar
			WebElement btnGuardar = wait2.until(ExpectedConditions.elementToBeClickable(btnGuardarCC));
			highlight(btnGuardar, "red");
			btnGuardar.click();
			testCase.addScreenShotCurrentStep(u, "Contraseña guardada correctamente");

			// CERRAR MODAL
			try {

				WebElement btnCerrar = wait2.until(ExpectedConditions.elementToBeClickable(CerrarModal));

				highlight(btnCerrar, "yellow");
				testCase.addScreenShotCurrentStep(u, "Mensaje de confirmación visible");

				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", btnCerrar);

				aux.logP("Se dio clic en el botón 'Cerrar'.");

			} catch (TimeoutException e) {
				aux.logP("No apareció el modal dentro del tiempo.");
				testCase.addScreenShotCurrentStep(u, "No se detectó el botón de 'Cerrar'");
			}

			return true;

		} catch (Exception e) {

			testCase.addScreenShotCurrentStep(u, "Error al cambiar contraseña");
			return false;
		}
	}

	/**
	 * Se crea el metodo que se pueden utiliza para que al cargar el login pueda tomar una screen del AC
	 * 
	 * @author Emmanuel Gomez
	 * @date 02/Diciembre/2025
	 */

	public void esperarLoginYTomarScreenshot() {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(50));

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

		testCase.addScreenShotCurrentStep(u, "Redirigido al login");
	}

	/**
	 * Se crean todos los metodos que se pueden utilizar para el deslogueo del
	 * Access Control
	 * 
	 * @author Emmanuel Gomez
	 * @return mensaje de error por si falla el deslogueo
	 * @date 02/Diciembre/2025
	 */

	public void logoutAccessControl() {
		try {
			// Esperar a que aparezca el navbar
			wait.until(ExpectedConditions.elementToBeClickable(locationNavbarMenu));
			u.highLight(locationNavbarMenu);
			u.click(locationNavbarMenu);
			testCase.addScreenShotCurrentStep(u, "Navbar abierto");

			// Esperar y hacer clic en la opción de "Cerrar sesión"
			wait.until(ExpectedConditions.elementToBeClickable(locationLogoutOption));
			u.highLight(locationLogoutOption);
			u.click(locationLogoutOption);
			testCase.addScreenShotCurrentStep(u, "Sesión cerrada correctamente");

			// Esperar que regrese al login
			wait.until(ExpectedConditions.visibilityOfElementLocated(inputUsuarioCC));
		} catch (Exception e) {
			return;
		}
	}

}