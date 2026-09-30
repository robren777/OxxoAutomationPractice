package WEB.util.Facturacion;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.util.GlobalVariables;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Facturacion {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;

	// Your connection isn't private//
	private By btnAdvance = By.id("details-button");
	private By linkContinue = By.id("proceed-link");

	public Facturacion(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}

	// LOGIN
	private By locationUserInput = By.id("form:usuario");
	private By locationPassInput = By.id("form:pass");
	private By locationLoginButton = By.id("form:j_idt45");

	// Link Guia de Usuario PDF
	private By locationGuiUserButton = By.id("menu:j_idt30");

	// LInk Contactanos
	private By locationContactanosButton = By.id("menu:j_idt31");

	// LInk Ayuda
	private By locationAyudaButton = By.id("menu:j_idt29");

	// LInk Edicion
	private By locationEdicion = By.cssSelector("#form\\:j_idt48_data > tr:nth-child(1)");
	private By locationEdicion1 = By.cssSelector("#form\\:PreRes_data > tr:nth-child(1) > td:nth-child(1)");
	private By locationEdicion2 = By
			.cssSelector("#form\\:PreRes_data > tr.ui-widget-content.ui-datatable-odd > td:nth-child(1)");
	private By locationEdicion3 = By.cssSelector("#form\\:PreRes_data > tr:nth-child(5) > td:nth-child(1)");

	// LInk Facturacion
	private By locationFact = By.cssSelector("#form\\:j_idt48_data > tr:nth-child(2)");
	private By locationFact1 = By.cssSelector("#form\\:PreRes_data > tr:nth-child(1) > td:nth-child(1)");
	private By locationFact2 = By
			.cssSelector("#form\\:PreRes_data > tr.ui-widget-content.ui-datatable-odd > td:nth-child(1)");
	private By locationFact3 = By.cssSelector("#form\\:PreRes_data > tr:nth-child(5) > td:nth-child(1)");

	// LInk Facturacion con registro
	private By locationFactcr = By.cssSelector("#form\\:j_idt48_data > tr:nth-child(3)");
	private By locationFactcr1 = By.cssSelector("#form\\:PreRes_data > tr > td:nth-child(1)");

	// LInk Facturacion sin registro
	private By locationFacsr = By.cssSelector("#form\\:j_idt48_data > tr:nth-child(4)");
	private By locationFacsr1 = By.cssSelector("#form\\:PreRes_data > tr > td:nth-child(1)");

	// LInk Facturacion XML
	private By locationFactxml = By.cssSelector("#form\\:j_idt48_data > tr:nth-child(5)");
	private By locationFactxml1 = By
			.cssSelector("#form\\:PreRes_data > tr.ui-widget-content.ui-datatable-even > td:nth-child(1)");
	private By locationFactxml2 = By
			.cssSelector("#form\\:PreRes_data > tr.ui-widget-content.ui-datatable-odd > td:nth-child(1)");

	// LInk Inicio de sesion
	private By locationInicio = By.cssSelector("#form\\:j_idt48_data > tr:nth-child(6)");
	private By locationInicio1 = By.cssSelector("#form\\:PreRes_data > tr:nth-child(1) > td:nth-child(1)");
	private By locationInicio2 = By
			.cssSelector("#form\\:PreRes_data > tr.ui-widget-content.ui-datatable-odd > td:nth-child(1)");
	private By locationInicio3 = By.cssSelector("#form\\:PreRes_data > tr:nth-child(5) > td:nth-child(1)");

	// LInk Registro
	private By locationReg = By.cssSelector("#form\\:j_idt48_data > tr:nth-child(7)");
	private By locationReg1 = By
			.cssSelector("#form\\:PreRes_data > tr.ui-widget-content.ui-datatable-even > td:nth-child(1)");
	private By locationReg2 = By
			.cssSelector("#form\\:PreRes_data > tr.ui-widget-content.ui-datatable-odd > td:nth-child(1)");

	// LInk Reimpresion de factura
	private By locationReim = By.cssSelector("#form\\:j_idt48_data > tr:nth-child(8)");
	private By locationReim1 = By
			.cssSelector("#form\\:PreRes_data > tr.ui-widget-content.ui-datatable-even > td:nth-child(1)");
	private By locationReim2 = By
			.cssSelector("#form\\:PreRes_data > tr.ui-widget-content.ui-datatable-odd > td:nth-child(1)");

	// LInk Descarga de guia de ayuda
	private By locationDescarga = By.cssSelector("#form\\:j_idt51");

	// LInk User
	private By locationUserButton = By
			.cssSelector("#menu\\:j_idt19 > ul > li.ui-widget.ui-menuitem.ui-corner-all.ui-menu-parent.txt_menu > a");
	private By locationMiusuario = By.id("menu:j_idt23");
	private By locationCerrar = By.id("menu:j_idt28");
	private By locationdataButton = By.id("menu:j_idt25");
	private By locationAddRfc = By.cssSelector("#form\\:j_idt44");
	// Link AddRFC
	private By locationRFCinput = By.cssSelector("#form\\:rfc");
	private By locationNominput = By.cssSelector("#form\\:nombreRazonSocial");
	private By locationAliasinput = By.cssSelector("#form\\:alias");
	private By locationCalleinput = By.cssSelector("#form\\:calle");
	private By locationExtinput = By.cssSelector("#form\\:ext");
	private By locationInteinput = By.cssSelector("#form\\:int");
	private By locationColoniainput = By.cssSelector("#form\\:colonia");
	private By locationDeleinput = By.cssSelector("#form\\:dele");
	private By locationCodigoinput = By.cssSelector("#form\\:codigo");
	private By locationEdoinput = By.cssSelector("#form\\:estado_label");
	private By locationEdo1input = By.cssSelector("#form\\:estado_panel > div > ul > li:nth-child(20)");
	private By locationLoadButton = By.id("form:j_idt67");

	private By gestionProveedores = By.linkText("Gestión de Proveedores");
	private By Finanzas = By.linkText("Finanzas");
	private By CargaArchivo = By.linkText("Carga archivo relacional");
	private By FacturasConsulta = By.linkText("Consulta de Facturas");

	/**
	 * Login en Facturacion
	 * 
	 * @param user
	 * @param ps
	 */
	public void loginFacturacion(String user, String ps) {
		try {
			Boolean bandera = false;
			Integer cont = 0;
			// u.hardWait(1);
			u.getDriver().manage().deleteAllCookies();
			String url = GlobalVariables.URL_PORTAL_APPSF;
			testCase.addTextEvidenceCurrentStep(url);
			u.get(url);
			u.getDriver().manage().window().maximize();
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("");

			// cierra popup
			u.getDriver().switchTo().activeElement().findElement(By.xpath(
					"//div[@id='form:dlgInfoTicket']//div//a[@class='ui-dialog-titlebar-icon ui-dialog-titlebar-close ui-corner-all']"))
					.click();

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
						bandera = true;
					} catch (Exception e) {
						bandera = false;
					}
				} else {
					u.getDriver().navigate().refresh();
					cont = 0;
				}
			} while (!bandera);

			testCase.addScreenShotCurrentStep(u, "Facturacion");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo iniciar sesion");
		}

	}

	/**
	 * Acceso al link de Manual de Uso del menu Guia de Usuarios click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickGuiaUs() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationGuiUserButton));
			u.highLight(locationGuiUserButton);
			testCase.addScreenShotCurrentStep(u, "Dar clic en GuiaUsuario");
			u.unhighLight(locationGuiUserButton);
			u.click(locationGuiUserButton);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(u, "Dar clic en GuiaUsuario");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en: Dar clic en GuiaUsuario");
		}
	}

	/**
	 * Acceso al link de Contactanos del Menu superior click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */
	public void clickContac() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationContactanosButton));
			u.highLight(locationContactanosButton);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Contactanos");
			u.unhighLight(locationContactanosButton);
			u.click(locationContactanosButton);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en Contactanos");
		}
	}

	/**
	 * Acceso al link de Ayuda del Menu superior click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickAyuda() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationAyudaButton));
			u.highLight(locationAyudaButton);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Ayuda");
			u.unhighLight(locationAyudaButton);
			u.click(locationAyudaButton);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep("");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en Ayuda");
		}
	}

	/**
	 * Acceso al link Edicion de la Configuracion del menu Ayuda del Menu superior
	 * click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickEdicion() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationEdicion));
			u.highLight(locationEdicion);
			u.unhighLight(locationEdicion);
			u.click(locationEdicion);
			u.hardWait(1);
			u.click(locationEdicion1);
			u.hardWait(2);
			u.click(locationEdicion2);
			u.hardWait(2);
			u.click(locationEdicion3);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Edicion de la Configuracion");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en Edicion de la Conifguracion");
		}
	}

	/**
	 * Acceso al link Facturacion del menu Ayuda del Menu superior click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickFact() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationFact));
			u.highLight(locationFact);
			u.unhighLight(locationFact);
			u.click(locationFact);
			u.hardWait(1);
			u.click(locationFact1);
			u.hardWait(2);
			u.click(locationFact2);
			u.hardWait(2);
			u.click(locationFact3);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Facturacion");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en Facturacion");
		}
	}

	/**
	 * Acceso al link Facturacion con registro del menu Ayuda del Menu superior
	 * click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickFactCR() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationFactcr));
			u.highLight(locationFactcr);
			u.unhighLight(locationFactcr);
			u.click(locationFactcr);
			u.hardWait(1);
			u.click(locationFactcr1);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Facturacion con registro");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en Facturacion con registro");
		}
	}

	/**
	 * Acceso al link Facturacion sin registro del menu Ayuda del Menu superior
	 * click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickFactSR() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationFacsr));
			u.highLight(locationFacsr);
			u.unhighLight(locationFacsr);
			u.click(locationFacsr);
			u.hardWait(1);
			u.click(locationFacsr1);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Facturacion sin registro");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en Facturacion sin registro");
		}
	}

	/**
	 * Acceso al link Facturacion XML del menu Ayuda del Menu superior click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickFactXML() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationFactxml));
			u.highLight(locationFactxml);
			u.unhighLight(locationFactxml);
			u.click(locationFactxml);
			u.hardWait(1);
			u.click(locationFactxml1);
			u.hardWait(2);
			u.click(locationFactxml2);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Facturacion XML");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en Facturacion XML");
		}
	}

	/**
	 * Acceso al link Inicio de sesion del menu Ayuda del Menu superior click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickInic() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationInicio));
			u.highLight(locationInicio);
			u.unhighLight(locationInicio);
			u.click(locationInicio);
			u.hardWait(1);
			u.click(locationInicio1);
			u.hardWait(2);
			u.click(locationInicio2);
			u.hardWait(2);
			u.click(locationInicio3);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Inicio de sesion");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en Inicio de sesion");
		}
	}

	/**
	 * Acceso al link Registro del menu Ayuda del Menu superior click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickReg() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationReg));
			u.highLight(locationReg);
			u.unhighLight(locationReg);
			u.click(locationReg);
			u.hardWait(1);
			u.click(locationReg1);
			u.hardWait(2);
			u.click(locationReg2);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Registro");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en Registro");
		}
	}

	/**
	 * Acceso al link Reimpresion del menu Ayuda del Menu superior click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickReimp() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationReim));
			u.highLight(locationReim);
			u.unhighLight(locationReim);
			u.click(locationReim);
			u.hardWait(1);
			u.click(locationReim1);
			u.hardWait(2);
			u.click(locationReim2);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Reimpresion");
			u.unhighLight(locationReim);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en Reimpresion");
		}
	}

	/**
	 * Acceso al link Descargar Archivo del menu Ayuda del Menu superior click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickDescar() {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationDescarga));
			u.highLight(locationDescarga);
			u.click(locationDescarga);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Descarga");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar clic en Descarga");
		}
	}

	/**
	 * Acceso al link Mi usuario del Menu desplegable. /
	 *********************************************************/

	public void clickUser() {
		try {

			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(locationUserButton);
			builder.moveToElement(element).build().perform();
			u.hardWait(3);
			clickOpcion("Mi Usuario", locationMiusuario);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Mi usuario");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dr click en Mi Usuario");
		}

	}

	/**
	 * Acceso al link Agregar Nuevo RFC. /
	 *********************************************************/

	public void clickaddRFC(String rfc, String nombre, String alias, String calle, String ext, String inte,
			String colonia, String dele, String codigo) {
		try {

			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(locationUserButton);
			builder.moveToElement(element).build().perform();
			u.hardWait(3);
			clickOpcion("Facturas", locationdataButton);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Agregar y Editar Datos de Facturacion");
			clickOpcion("AgregarRFC", locationAddRfc);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Agregar RFC");
			// Se llena formulario
			u.hardWait(3);
			u.sendKeys(locationRFCinput, rfc);
			u.hardWait(1);
			u.sendKeys(locationNominput, nombre);
			u.hardWait(1);
			u.sendKeys(locationAliasinput, alias);
			u.hardWait(1);
			u.sendKeys(locationCalleinput, calle);
			u.hardWait(1);
			u.sendKeys(locationExtinput, ext);
			u.hardWait(1);
			u.sendKeys(locationInteinput, inte);
			u.hardWait(1);
			u.sendKeys(locationColoniainput, colonia);
			u.hardWait(1);
			u.sendKeys(locationDeleinput, dele);
			u.hardWait(1);
			u.sendKeys(locationCodigoinput, codigo);
			u.hardWait(1);
			u.mouseOverAndClick(locationEdoinput);
			u.click(locationEdo1input);
			u.hardWait(3);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Guardar Datos de Facturacion");
			u.highLight(locationLoadButton);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Guardar Datos de Facturacion");
			u.click(locationLoadButton);
			u.hardWait(3);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Guardar Datos de Facturacion");
			u.hardWait(2);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dr click en Guardar Datos de Facturacion");
		}

	}

	/**
	 * Acceso al link Cerrar Sesion del Menu desplegable. /
	 *********************************************************/

	public void clickClose() {
		try {

			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(locationUserButton);
			builder.moveToElement(element).build().perform();
			u.hardWait(1);
			clickOpcion("Cerrar Sesion", locationCerrar);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dr click en Cerrar");
		}

	}

	/**
	 * Acceso al link Validar RFC Guardado. /
	 *********************************************************/

	public void clickvalidaRFC() {
		try {

			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(locationUserButton);
			builder.moveToElement(element).build().perform();
			u.hardWait(3);
			clickOpcion("Facturas", locationdataButton);
			testCase.addScreenShotCurrentStep(u, "Dar clic en Agregar y Editar Datos de Facturacion");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dr click en Agregar y Editar Datos de Facturacion");
		}

	}

	/******************************************************************/
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

}
