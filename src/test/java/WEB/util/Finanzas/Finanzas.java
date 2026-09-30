package WEB.util.Finanzas;

import java.time.Duration;
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

public class Finanzas {
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
	private By gestionProveedores = By.linkText("Gestión de Proveedores");
	private By Finanzas = By.linkText("Finanzas");
	private By ConsultaFac = By.linkText("Consulta de Facturas");
	private By InformePago = By.linkText("Informe de Pago");

//	Locators 	Consulta de Facturas
	private By locationFinicialInput = By.id("invoice-query-report-form:start-date_input");
	private By locationFinicialInput1 = By.id("form:start-date_input");
	private By locationFfinalInput = By.id("invoice-query-report-form:final-date_input");
	private By locationFfinalInput1 = By.id("form:final-date_input");
	private By locationTipoinput = By.cssSelector("#invoice-query-report-form\\:invoice-type_label");
	private By locationInformeinput = By.cssSelector("#invoice-query-report-form\\:invoice-type_1");
	private By locationEstatusinput = By.cssSelector("#invoice-query-report-form\\:status_label");
	private By locationVendorinput = By.cssSelector("#invoice-query-report-form\\:form-vendor-name");
	private By locationBuscavButton = By.id("invoice-query-report-form:j_idt39");
	private By locationBuscavButton1 = By.id("form:j_idt31");
	private By locationVendnameinput = By.cssSelector("#invoice-query-report-form\\:vendor-name");
	private By locationVendnameinput1 = By.cssSelector("#form\\:vendor-name");
	private By locationFindvButton = By.id("invoice-query-report-form:j_idt48");
	private By locationFindvButton1 = By.id("form:j_idt40");
	private By locationRadioinput = By.cssSelector(
			"#invoice-query-report-form\\:table-proveedores_data > tr > td.ui-selection-column > div > div.ui-radiobutton-box.ui-widget.ui-corner-all.ui-state-default");
	private By locationRadioinput1 = By.cssSelector(
			"#form\\:table-proveedores_data > tr > td.ui-selection-column > div > div.ui-radiobutton-box.ui-widget.ui-corner-all.ui-state-default");
	private By locationAceptarinput = By.id("invoice-query-report-form:j_idt56");
	private By locationAceptarinput1 = By.id("form:j_idt48");
	private By locationQueryinput = By.id("invoice-query-report-form:j_idt60");
	private By locationQueryinput1 = By.id("form:j_idt52");
	private By locationExcelinput = By.id("invoice-query-report-form:j_idt91");// locationExcelinput
	private By locationExcelinput1 = By.id("form:j_idt111");// locationExcelinput

	public Finanzas(SeleniumUtil u, TestCase testCase) {
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
	 * Acceso al link de Proveedores click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickGestionPro() {
		try {

			clickOpcion("GestionProveedores", gestionProveedores);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fall� al dr click en Gestion Proveedores");
		}
	}

	/**
	 * Acceso al link de Consulta de Factura del menu Finanzas sobre menu
	 * desplegable click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickMenuFinanzasReporte() {
		try {

			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(By.linkText("Finanzas"));
			builder.moveToElement(element).build().perform();
			clickOpcion("Consulta de Factura", ConsultaFac);

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Finanzas");
		}

	}

	/**
	 * Acceso al link de Busqueda de Facturas del menu Finanzas sobre menu
	 * desplegable click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void consultaFactura() {

		try {

			u.waitWebElement(By.cssSelector("#contentFrame > iframe"), 20, 2);
			WebElement iframe = this.u.getDriver().findElement(By.cssSelector("#contentFrame > iframe"));

			// Switch to the frame para llenar formulario
			u.getDriver().switchTo().frame(iframe);
			testCase.addScreenShotCurrentStep("u, Llenado de Formulario");
			clearInput(locationFinicialInput);
			u.sendKeys(locationFinicialInput, "01/12/2021");
			u.hardWait(2);
			clearInput(locationFfinalInput);
			u.sendKeys(locationFfinalInput, "27/01/2023");
			u.hardWait(2);
			u.mouseOverAndClick(locationTipoinput);
			clickOpcion("Informe de gastos", locationInformeinput);
			u.hardWait(1);
			u.click(locationVendorinput);
			u.hardWait(1);
			u.click(locationBuscavButton);
			u.hardWait(1);
			u.sendKeys(locationVendnameinput, "BONAFONT SA DE CV");
			u.hardWait(3);
			u.click(locationFindvButton);
			u.hardWait(1);
			u.click(locationRadioinput);
			u.hardWait(1);
			u.click(locationAceptarinput);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Boton de Busqueda");
			u.click(locationQueryinput);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Click para bajar busqueda en Excel");
			u.click(locationExcelinput);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Muestra la busqueda");

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Finanzas");
		}

	}

	/**
	 * Acceso al link de Informe de Pago del menu Finanzas sobre menu desplegable
	 * click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickMenuFinanzasInforme() {
		try {

			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(By.linkText("Finanzas"));
			builder.moveToElement(element).build().perform();
			clickOpcion("Informe de Pago", InformePago);

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Finanzas");
		}

	}

	/**
	 * Acceso al link de Informe de Consulta de Facturas del menu Finanzas sobre
	 * menu desplegable click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void consultaInforme() {

		try {

			u.waitWebElement(By.cssSelector("#contentFrame > iframe"), 20, 2);
			WebElement iframe = this.u.getDriver().findElement(By.cssSelector("#contentFrame > iframe"));

			// Switch to the frame para llenar formulario
			u.getDriver().switchTo().frame(iframe);
			testCase.addScreenShotCurrentStep("u, Llenado de Formulario");
			clearInput(locationFinicialInput1);
			u.sendKeys(locationFinicialInput1, "01/12/2021");
			u.hardWait(2);
			clearInput(locationFfinalInput1);
			u.sendKeys(locationFfinalInput1, "27/01/2023");
			u.hardWait(2);
			u.click(locationBuscavButton1);
			u.hardWait(3);
			u.sendKeys(locationVendnameinput1, "BONAFONT SA DE CV");
			u.hardWait(3);
			u.click(locationFindvButton1);
			u.hardWait(2);
			u.click(locationRadioinput1);
			u.hardWait(2);
			u.click(locationAceptarinput1);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Boton de Busqueda");
			u.click(locationQueryinput1);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Click para bajar busqueda en Excel");
			u.click(locationExcelinput1);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Muestra la busqueda");

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Informe de Pago");
		}

	}

}
