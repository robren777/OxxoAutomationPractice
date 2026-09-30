package WEB.util.PreguntasF;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.util.GlobalVariables;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Preguntas {
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

//	private By Administracion de Temas = By.linkText("Administracion de Temas");
	private By preguntasFrec = By.linkText("Preguntas Frecuentes");
//	Titulo
	private By locationTit = By.cssSelector(
			"#homeSubview\\:j_idt61 > div > div.envelope.mtop10 > div:nth-child(3) > div:nth-child(4) > div.envelope.modulo_titulo");

	private By Administracion = By.linkText("Administración");
	private By AdmonTemas = By.linkText("Administrador de Temas");
	private By locationAddTopic = By.cssSelector(
			"#topic > div:nth-child(6) > div > div:nth-child(1) > table > tbody > tr > td:nth-child(1) > table > tbody > tr > td:nth-child(1)");
	private By locationEditTopic = By.cssSelector(
			"#topic > div:nth-child(6) > div > div:nth-child(1) > table > tbody > tr > td:nth-child(1) > table > tbody > tr > td:nth-child(2)");
	private By locationEliminarTopic = By
			.cssSelector("#topic > div:nth-child(6) > div > div:nth-child(1) > table > tbody > tr > td:nth-child(2)");
	private By locationFila = By.cssSelector("#dataGrid > tbody > tr:nth-child(1)");
	private By locationFila1 = By.cssSelector("#dataGrid > tbody > tr:nth-child(5)");
	private By locationNomTema = By.cssSelector("#j_idt88\\:name");
	private By locationApplist = By.cssSelector("#j_idt88\\:app");
	private By locationAplicacioninput = By.cssSelector("#j_idt88\\:app > option:nth-child(10)");
	private By locationAplicacion1input = By.cssSelector("#j_idt88\\:app > option:nth-child(4)");
	private By locationGuardarinput = By.cssSelector("#j_idt88 > span > a.boton.rojo");
	private By locationExito = By.cssSelector("#topic > div:nth-child(6) > div > div.grid5.mensaje_ok > ul > li");
	private By InformePago = By.linkText("Informe de Pago");

	public Preguntas(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}

	/**
	 * Login en Access Control
	 * 
	 * @param user
	 * @param ps
	 */
	public void loginAccessControl(String user, String ps) {
		try {
			Boolean bandera = false;
			Integer cont = 0;
			u.hardWait(1);
			u.getDriver().manage().deleteAllCookies();
			String url = GlobalVariables.URL_PORTAL_APPSG;
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

	/**
	 * Este metodo cambia el atributo "value" de un webElement tipo input. Se puede
	 * usar cuando el input no acepta el sendKeys para asignar un valor.
	 * 
	 * @param elementBy      -> Localizador del input
	 * @param value          -> Valor que se quiere ingresar
	 * @param takeScreenShot -> Si se requiere tomar una screenshot del elemento
	 * @author Esmeralda Garcia
	 */
	public void changeAttributeValue(By locationFinicialInput, String value, Boolean takeScreenShot) {
		try {
			u.highLight(locationFinicialInput);
			WebElement element = u.getDriver().findElement(locationFinicialInput);
			JavascriptExecutor jse = (JavascriptExecutor) u.getDriver();
			jse.executeScript("arguments[0].value='" + value + "';", element);
			if (takeScreenShot) {
				testCase.addScreenShotCurrentStep(u, "Campo");
			}
			u.unhighLight(locationFinicialInput);
		} catch (Exception e) {
//           logS("" + e);
			throw new AssertionError("No se pudo asignar el atributo");
		}
	}

	/**
	 * Acceso al link de Preguntas Frecuentes click.
	 * 
	 * @author David Perez Couto
	 */

	public void clickPregFrec() {
		try {
			u.highLight(preguntasFrec);
			u.click(locationTit);
			clickOpcion("Preguntas Frecuentes", preguntasFrec);
			testCase.addScreenShotCurrentStep("u, Preguntas Frecuentes");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Preguntas Frecuentes");
		}
	}

	/**
	 * Acceso al link de Administracion de Temas del menu Administracion sobre menu
	 * desplegable click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickMenuAdminTemas() {
		try {

			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(By.linkText("Administración"));
			builder.moveToElement(element).build().perform();
			clickOpcion("Admnistrador de Temas", AdmonTemas);
			testCase.addScreenShotCurrentStep("u, Administracion de Temas");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Administración");
		}

	}

	/**
	 * Crear nuevo tema en el Administrador de Temas click.
	 * 
	 * @author David Perez Couto
	 */

	public void crearTema() {

		try {

//			 Activar el boton para crear nuevo tema y poblar datos
			u.highLight(locationAddTopic);
			u.click(locationAddTopic);
			testCase.addScreenShotCurrentStep("u, Agregar Tema");
			u.highLight(locationNomTema);
			u.sendKeys(locationNomTema, "Portales");
			u.hardWait(1);
			u.highLight(locationApplist);
			u.click(locationApplist);
			clickOpcion("Evaluaciones Preventivas", locationAplicacioninput);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("u, Formulario");
			u.highLight(locationGuardarinput);
			u.click(locationGuardarinput);
			u.hardWait(1);
			u.highLight(locationExito);
			testCase.addScreenShotCurrentStep("u, Llenado de Formulario con Exito");

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Formulario");
		}

	}

	/**
	 * Editar tema en el Administrador de Temas click.
	 * 
	 * @author David Perez Couto
	 */

	public void editarTema() {

		try {

//			 Seleciona Fila para editar tema
			u.highLight(locationFila);
			u.click(locationFila);
			testCase.addScreenShotCurrentStep("u, Editar Tema");
//			 Activar el boton para editar tema y poblar datos
			u.highLight(locationEditTopic);
			testCase.addScreenShotCurrentStep("u, Editar Tema");
			u.click(locationEditTopic);
			testCase.addScreenShotCurrentStep("u, Editar Tema");
			u.highLight(locationNomTema);
			clearInput(locationNomTema);
			u.hardWait(1);
			u.sendKeys(locationNomTema, "Portal de Servicios EDITADO");
			u.hardWait(1);
			u.highLight(locationApplist);
			u.click(locationApplist);
			clickOpcion("Portal de Cobro Servicios Colombia", locationAplicacion1input);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("u, Formulario");
			u.highLight(locationGuardarinput);
			u.click(locationGuardarinput);
			u.hardWait(1);
			u.highLight(locationExito);
			u.highLight(locationFila);
			testCase.addScreenShotCurrentStep("u, Llenado de Formulario con Exito");

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Formulario");
		}
	}

	/**
	 * Eliminar tema en el Administrador de Temas click.
	 * 
	 * @author David Perez Couto
	 */

	public void eliminarTema() {

		try {

//			 Seleciona Fila para editar tema
			u.highLight(locationFila1);
			u.click(locationFila1);
			testCase.addScreenShotCurrentStep("u, Eliminar Tema");
//			 Activar el boton para eliminar tema y poblar datos
			u.highLight(locationEliminarTopic);
			testCase.addScreenShotCurrentStep("u, Eliminar Tema");
			u.click(locationEliminarTopic);
			u.hardWait(2);
			Alert alert = u.getDriver().switchTo().alert();
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("u, Eliminar Tema");
			alert.accept();
			u.highLight(locationExito);
			u.highLight(locationFila1);
			testCase.addScreenShotCurrentStep("u, Llenado de Formulario con Exito");

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Formulario");
		}
	}

}
