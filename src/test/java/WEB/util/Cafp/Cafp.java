package WEB.util.Cafp;

import static org.testng.Assert.assertEquals;

import java.io.File;
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

public class Cafp {
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
	private By CargaArchivo = By.linkText("Carga archivo relacional");
	private By FacturasConsulta = By.linkText("Consulta de Facturas");

	public Cafp(SeleniumUtil u, TestCase testCase) {
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
			throw new AssertionError("Fallo al dr click en Gestion Proveedores");
		}
	}

	/**
	 * Acceso al link de Carga de Archivo del menu Finanzas sobre menu desplegable
	 * click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void clickMenuFinanzas() {
		try {

			Actions builder = new Actions(this.u.getDriver());
			WebElement element = this.u.getDriver().findElement(By.linkText("Finanzas"));
			builder.moveToElement(element).build().perform();
			clickOpcion("CargaArchivo", CargaArchivo);

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("Fallo al dar click en Finanzas");
		}

	}

	/**
	 * Acceso al link de Subir Archivo Relacional Exitosamente click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void subirArchivo() {

		u.waitWebElement(By.cssSelector("#contentFrame>iframe"), 20, 2);
		WebElement iframe = this.u.getDriver().findElement(By.cssSelector("#contentFrame>iframe"));

		// Switch to the frame para subir archivo
		u.getDriver().switchTo().frame(iframe);
		File file = new File(
				"C:\\ProyectoOxxo\\ModernizacionPortales\\FrameworkWeb\\Files\\Cafp\\CFDFP_10TMP_64162.csv");
		String path = file.getAbsolutePath();
		u.getDriver().findElement(By.xpath("//input[@type='file']")).sendKeys(path);
		clickOpcion("Carga de Archivo exitosamente", By.id("form:chargeBtn"));
		String text = u.getDriver().findElement(By.id("javax_faces_developmentstage_messages")).getText();
		assertEquals("El archivo se subio correctamente CFDFP_10TMP_0000064162.csv", text);

	}

	/**
	 * Acceso al link de Subir Archivo Relacional con Plaza Exitosamente click.
	 * 
	 * @param name      ->
	 * @param elementBy ->
	 * @author David Perez Couto
	 */

	public void subirArchivoRP() {

		u.waitWebElement(By.cssSelector("#contentFrame>iframe"), 20, 2);
		WebElement iframe = this.u.getDriver().findElement(By.cssSelector("#contentFrame>iframe"));

		// Switch to the frame
		u.getDriver().switchTo().frame(iframe);
		File file = new File(
				"C:\\ProyectoOxxo\\ModernizacionPortales\\FrameworkWeb\\Files\\Cafp\\CFDFP_10TMP_74545.csv");
		String path = file.getAbsolutePath();
		u.getDriver().findElement(By.xpath("//input[@type='file']")).sendKeys(path);
		clickOpcion("Carga de Archivo exitosamente", By.id("form:chargeBtn"));
		String text = u.getDriver().findElement(By.id("javax_faces_developmentstage_messages")).getText();
		assertEquals("El archivo se subio correctamente CFDFP_10TMP_0000074545.csv", text);

	}
}
