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

public class FinanzasBuscar {
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
	private By RecepDevo = By.linkText("Recepciones y Devoluciones");

	// Locators Consulta de Facturas
	private By locationFinicialInput = By.id("invoice-query-report-form:start-date_input");
	private By locationMesInput = By.cssSelector("#ui-datepicker-div > div > a.ui-datepicker-prev.ui-corner-all");
	private By locationDiaInput = By
			.cssSelector("#ui-datepicker-div > table > tbody > tr:nth-child(1) > td:nth-child(3)");
	private By locationMesFinInput = By.cssSelector("#ui-datepicker-div > div > a.ui-datepicker-next.ui-corner-all");
	private By locationDiaFinInput = By
			.cssSelector("#ui-datepicker-div > table > tbody > tr:nth-child(1) > td:nth-child(6)");
	private By locationFfinalInput = By.id("invoice-query-report-form:final-date_input");
	private By locationFfinalInput1 = By.id("form:final-date_input");
	private By locationTipoinput = By.cssSelector("#invoice-query-report-form\\:trxType_label");
	private By locationDevoinput = By.cssSelector("#invoice-query-report-form\\:trxType_1");
	private By locationRecepinput = By.cssSelector("#invoice-query-report-form\\:trxType");
	private By locationTipoRinput = By.cssSelector("#invoice-query-report-form\\:reportType_label");
	private By locationTiendaInput = By.cssSelector("#invoice-query-report-form\\:reportType_2");
	private By locationFaInput = By.cssSelector("#invoice-query-report-form\\:reportType_1");
	private By locationArticuloinput = By.cssSelector("#invoice-query-report-form\\:item");
	private By locationListinput = By
			.cssSelector("#invoice-query-report-form\\:stores > div.ui-selectlistbox-listcontainer > ul > li");
	private By locationListFainput = By
			.cssSelector("#invoice-query-report-form\\:fas > div.ui-selectlistbox-listcontainer > ul > li");
	private By locationVendorinput = By.cssSelector("#invoice-query-report-form\\:form-vendor-name");
	private By locationBuscavButton = By.id("invoice-query-report-form:j_idt56");
	private By locationVendnameinput = By.cssSelector("#invoice-query-report-form\\:vendor-name");
	private By locationFindvButton = By.id("invoice-query-report-form:j_idt65");
	private By locationRadioinput = By.cssSelector(
			"#invoice-query-report-form\\:table-proveedores_data > tr:nth-child(1) > td.ui-selection-column > div > div.ui-radiobutton-box.ui-widget.ui-corner-all.ui-state-default");
	private By locationAceptarinput = By.id("invoice-query-report-form:j_idt73");
	private By locationQueryinput = By.id("invoice-query-report-form:j_idt78");
	private By locationExcelinput = By.id("invoice-query-report-form:j_idt142");

	// ************* *LOCATORS BUSCA FACTURAS* ***********
	private By locationEstatusinput = By.cssSelector("#invoice-query-report-form\\:status_label");
	private By locationBuscavButton1 = By.id("form:j_idt31");
	private By locationVendnameinput1 = By.cssSelector("#form\\:vendor-name");
	private By locationFindvButton1 = By.id("form:j_idt40");
	private By locationRadioinput1 = By.cssSelector(
			"#form\\:table-proveedores_data > tr > td.ui-selection-column > div > div.ui-radiobutton-box.ui-widget.ui-corner-all.ui-state-default");
	private By locationExcelinput1 = By.id("form:j_idt111");// locationExcelinput

	public FinanzasBuscar(SeleniumUtil u, TestCase testCase) {
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
	 * Recepciones y Devoluciones click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickMenuFinanzasRecepDev() {
		try {

			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(By.linkText("Finanzas"));
			builder.moveToElement(element).build().perform();
			clickOpcion("Recepcion y Devoluciones", RecepDevo);

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Finanzas");
		}

	}

	/*
	 * Acceso al link de Busqueda de Facturas del menu Finanzas sobre el concepto
	 * Recepciones click.
	 * 
	 * @param name ->
	 * 
	 * @param elementBy ->
	 * 
	 * @author David Perez Couto
	 */

	public void busquedaProvUsuInt() {

		try {

			u.waitWebElement(By.cssSelector("#contentFrame > iframe"), 20, 2);
			WebElement iframe = this.u.getDriver().findElement(By.cssSelector("#contentFrame > iframe"));

			// Switch to the frame para llenar formulario
			u.getDriver().switchTo().frame(iframe);
			testCase.addScreenShotCurrentStep("u, Llenado de Formulario");
			u.highLight(locationFinicialInput);
			u.click(locationFinicialInput);
			u.hardWait(2);
			// Switch to the frame para fechas del calendario
			for (int i = 1; i <= 33; i++) {
				u.click(locationMesInput);
			}
			u.hardWait(2);
			u.click(locationDiaInput);
			u.hardWait(2);
			u.highLight(locationFfinalInput);
			u.click(locationFfinalInput);
			u.hardWait(2);
			u.click(locationMesFinInput);
			u.hardWait(2);
			u.click(locationDiaFinInput);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Calendario");
			u.hardWait(2);
			// Consulta del Query de pagos
			u.highLight(locationTipoinput);// Opcion Recepciones
			u.hardWait(1);
			u.highLight(locationArticuloinput);
			u.highLight(locationTipoRinput);
			u.mouseOverAndClick(locationTipoRinput);
			clickOpcion("FA", locationFaInput);
			u.hardWait(2);
			u.mouseOverAndClick(locationListFainput);
			u.hardWait(2);
			u.highLight(locationQueryinput);
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

}
