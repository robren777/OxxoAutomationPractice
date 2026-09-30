package Mobile.util;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AccessControl {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;

	// Your connection isn't private//
	private By btnAdvance = By.id("details-button");
	private By linkContinue = By.id("proceed-link");

	// LOGIN
	private By locationUserInput = By.id("login:userName");
	private By locationPassInput = By.id("login:userPass");
	private By locationLoginButton = By.id("login:loginButton");

	// INICIO PORTAL APLICACIONES
	private By locationMenuInicio = By.id("menu_cnt");

	// SEMBRADO CARGANDO
	private By modalCargando = By.className("blockUI");

	// CAMBIO DE CONTRASENA
	private By cambiarContrasena = By.cssSelector("#login > div.envelope.mtop10.txt-right > a:nth-child(2)");
	private By userInput = By.cssSelector("#reset\\:userReg");
	private By passInput = By.cssSelector("#reset\\:userPass");
	private By newPassInput = By.cssSelector("#reset\\:userNewPass");
	private By confPassInput = By.cssSelector("#reset\\:userConfirmNewPass");
	private By aceptarCCButton = By.cssSelector("#reset\\:resetButton");
	private By mensajeCambio = By.cssSelector("div.mensaje_error > ul > li");

	/**
	 * Constructor.
	 * 
	 * @param u
	 * @param testCase
	 * @author Esmeralda Garcia Torres
	 */
	public AccessControl(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}

	/**
	 * Login en Access Control con la url
	 * "https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf";
	 * 
	 * @param user
	 * @param pss
	 */
	public void loginAccessControl(String user, String ps) {
		try {
			Boolean bandera = false;
			Integer cont = 0;
			u.hardWait(1);
			u.getDriver().manage().deleteAllCookies();
			String url = GlobalVariables.URL_PORTAL_APPS;
			testCase.addTextEvidenceCurrentStep(url);
			u.get(url);
			u.getDriver().manage().window().maximize();

			if (u.getDriver().findElements(btnAdvance).size() != 0) {
				u.click(btnAdvance);
				u.waitWebElement(linkContinue, 20, 2);
				u.click(linkContinue);
			}

			u.waitWebElement(locationUserInput, 15, 2);
			testCase.addScreenShotCurrentStep("");

			do {
				cont += 1;
				if (cont < 4) {
					wait.until(ExpectedConditions.elementToBeClickable(locationLoginButton));
					u.highLight(locationUserInput);
					clearInput(locationUserInput);
					u.sendKeys(locationUserInput, user);
					u.highLight(locationPassInput);
					clearInput(locationPassInput);
					u.sendKeys(locationPassInput, ps);
					u.highLight(locationLoginButton);
					testCase.addScreenShotCurrentStep(u, "Credenciales");
					u.click(locationLoginButton);
					try {
						wait.until(ExpectedConditions.elementToBeClickable(locationMenuInicio));
						bandera = true;
					} catch (Exception e) {
						bandera = false;
					}
				} else {
					u.getDriver().navigate().refresh();
					cont = 0;
				}
			} while (!bandera);

			testCase.addScreenShotCurrentStep(u, "AccessControl");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo iniciar sesion");
		}

	}

	/**
	 * Login en Access Control
	 * 
	 * @param urlPortal
	 * @param user
	 * @param pass
	 * @author Esmeralda Garcia Torres
	 */
	public void loginAccessControl(String urlPortal, String user, String ps) {
		try {
			Boolean bandera = false;
			Integer cont = 0;
			u.hardWait(1);
			u.getDriver().manage().deleteAllCookies();
			testCase.addTextEvidenceCurrentStep(urlPortal);
			u.get(urlPortal);
			u.getDriver().manage().window().maximize();

			if (u.getDriver().findElements(btnAdvance).size() != 0) {
				u.click(btnAdvance);
				u.waitWebElement(linkContinue, 20, 2);
				u.click(linkContinue);
			}

			u.waitWebElement(locationUserInput, 15, 2);
			testCase.addScreenShotCurrentStep("");

			do {
				cont += 1;
				if (cont < 4) {
					wait.until(ExpectedConditions.elementToBeClickable(locationLoginButton));
					u.highLight(locationUserInput);
					clearInput(locationUserInput);
					u.sendKeys(locationUserInput, user);
					u.highLight(locationPassInput);
					clearInput(locationPassInput);
					u.sendKeys(locationPassInput, ps);
					u.highLight(locationLoginButton);
					testCase.addScreenShotCurrentStep(u, "Credenciales");
					u.click(locationLoginButton);
					try {
						wait.until(ExpectedConditions.elementToBeClickable(locationMenuInicio));
						bandera = true;
					} catch (Exception e) {
						bandera = false;
					}
				} else {
					u.getDriver().navigate().refresh();
					cont = 0;
				}
			} while (!bandera);

			testCase.addScreenShotCurrentStep(u, "AccessControl");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo iniciar sesion");
		}

	}

	/**
	 * Limpia un input seleccionando el texto que tiene y borrandolo.
	 * 
	 * @param inputBy
	 * @author Esmeralda Garcia Torres
	 */
	public void clearInput(By inputBy) {
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
	 * Revisa cada 2 segundos si el modal de Cargando sigue estando visible. Es una
	 * espera dinamica, en cuanto se quite el modal de Cargando, permite avanzar.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void cargandoMsg() {
		try {
			u.hardWait(1);
			while (u.getDriver().findElement(modalCargando).isDisplayed()) {
				u.hardWait(2);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
		}
	}

	/**
	 * Realiza el cambio de contraseña desde el portal de Access control
	 * 
	 * @param user
	 * @param pass
	 * @param newPass
	 * @author Esmeralda Garcia Torres
	 */
	public void cambiarContrasenia(String user, String pass, String newPass) {
		try {
			clickOpcion("CambiarContrasenia", cambiarContrasena);
			try {
				wait.until(ExpectedConditions.elementToBeClickable(aceptarCCButton));
			} catch (Exception e) {
				clickOpcion("CambiarContrasenia", cambiarContrasena);
				wait.until(ExpectedConditions.elementToBeClickable(aceptarCCButton));
			}

			u.highLight(userInput);
			clearInput(userInput);
			u.sendKeys(userInput, user);

			u.highLight(passInput);
			clearInput(passInput);
			u.sendKeys(passInput, pass);

			u.highLight(newPassInput);
			clearInput(newPassInput);
			u.sendKeys(newPassInput, newPass);

			u.highLight(confPassInput);
			clearInput(confPassInput);
			u.sendKeys(confPassInput, newPass);

			clickOpcion("CambioContrasenia", aceptarCCButton);

			u.hardWait(4);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Falló al cambiar la contrasenia");
		}
		// wait.until(ExpectedConditions.visibilityOfElementLocated(mensajeCambio));
		u.hardWait(3);
		String msnCambio = u.getDriver().findElement(mensajeCambio).getAttribute("innerText");
		if (!msnCambio.contains("ha sido modificada satisfactoriamente")) {
			throw new AssertionError("No se pudo cambiar la contrasenia");
		}
		testCase.addScreenShotCurrentStep(u, "MensajeCambio");
	}

	/**
	 * Espera a que el elemento se muestre, lo resalta, toma un screenshot, y le da
	 * click.
	 * 
	 * @param name      -> Nombre del boton
	 * @param elementBy -> By Element
	 * @author Esmeralda Garcia Torres
	 */
	public void clickOpcion(String name, By elementBy) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			testCase.addScreenShotCurrentStep(u, "Dar clic en " + name);
			u.unhighLight(elementBy);
			u.click(elementBy);
			u.hardWait(1);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en: " + name);
		}
	}
}
