package WorkFlows.util.Sembrado;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.selenium.SeleniumUtil;

public class pomSembradoALM {
	private SeleniumUtil u;
	private WebDriverWait wait;

	// LOGIN WEB ELEMENTS
	private By locationUserInput = By.id("login:userName");
	private By locationPassInput = By.id("login:userPass");
	private By locationLoginButton = By.id("login:loginButton");

	// HOME WEB ELEMENTS
	private By locationMenuInicio = By.id("menu_cnt");
	private By locationSembradoPlazaLink = By.linkText("Sembrado Plaza");

	// SEMBRADO PLAZA MENU WEB ELEMENTS
	private By locationConfigPlazaPlazaMenu = By.id("e0_0i");

	private By locationAsinacionPlazasLink = By.id("e0_2i");
	private By locationAPagregarButton = By.cssSelector("#TABLE_9 > tbody > tr:nth-child(6) > td > input");

	private By locationCatalogoABcorreoLink = By.id("e0_42i");
	private By locationABagregarButton = By
			.cssSelector("tr.tablaDatos:nth-child(6) > td:nth-child(1) > table:nth-child(1)"
					+ " > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(2) > input:nth-child(1)");

	private By locationCombinabilidadLink = By.id("e0_8i");
	private By locationCBAgregarButton = By.cssSelector(".boton");

	private By locationCompatibilidadLink = By.id("e0_9i");
	private By locationCPplazaSelect = By.id("idCmbPlaza");

	private By locationConjuntosLink = By.id("e0_1i");
	private By locationCJcrearButton = By.cssSelector(".boton");

	private By locationEquivalenciasLink = By.id("e0_10i");
	private By locationEQrecuperarValoresButton = By
			.cssSelector("tr.tablaDatos:nth-child(9) > td:nth-child(3) > input:nth-child(1)");

	private By locationLegosLink = By.id("e0_3i");
	private By locationTextLegos = By.cssSelector("#idCmbConjunto");

	// MESSAGE POP UP WEB ELEMENTS
	private By locationMsgPopUp = By.id("divMsjSem");
	private By locationMsgPopUpAceptar = By.cssSelector(".ui-button-text");
	private By locationMsgPopUpSi = By.cssSelector("button.ui-button:nth-child(1)");
	private By locationMsgPopUpNo = By.cssSelector("button.ui-button:nth-child(2)");
	private By locationMsgPopUpSalir = By.cssSelector("button.ui-button:nth-child(3)");

	public pomSembradoALM(SeleniumUtil u) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
	}

	/**
	 * Se loguea en la página y con las credenciales correspondientes.
	 * 
	 * @param url
	 * @param user
	 * @param password
	 * @author 73289
	 */
	public void loginSembrado(String url, String user, String password) {
		try {
			u.get(url);
			Boolean bandera = false;
			Integer cont = 0;
			do {
				cont += 1;
				if (cont < 4) {
					wait.until(ExpectedConditions.elementToBeClickable(locationLoginButton));
					u.highLight(locationUserInput);
					u.sendKeys(locationUserInput, user);
					u.highLight(locationPassInput);
					u.sendKeys(locationPassInput, password);
					u.highLight(locationLoginButton);
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

		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo iniciar sesión");
		}
	}

	/**
	 * Búsca la opción de "Sembrado Plaza" e ingresa.
	 * 
	 * @author 73289
	 */
	public void goToSembradoPlaza() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationSembradoPlazaLink));
			u.highLight(locationSembradoPlazaLink);
			u.click(locationSembradoPlazaLink);
			wait.until(ExpectedConditions.elementToBeClickable(locationConfigPlazaPlazaMenu));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar a Sembrado Plaza");
		}
	}

	/**
	 * Abre el menú "Configuración Plaza Plaza" para mostrar sus opciones.
	 * 
	 * @author 73289
	 */
	public void goToConfigPlazaPlaza() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationConfigPlazaPlazaMenu));
			u.highLight(locationConfigPlazaPlazaMenu);
			u.click(locationConfigPlazaPlazaMenu);
			wait.until(ExpectedConditions.elementToBeClickable(locationAsinacionPlazasLink));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo seleccionar el menú Configuración Plaza Plaza");
		}
	}

	/**
	 * Búsca en el menú "Configuración Plaza Plaza" la opción "Asignación de Plaza"
	 * e ingresa.
	 * 
	 * @author 73289
	 */
	public void goToAsignacionPlazas() {
		try {
			goToConfigPlazaPlaza();

			wait.until(ExpectedConditions.elementToBeClickable(locationAsinacionPlazasLink));
			u.highLight(locationAsinacionPlazasLink);
			u.click(locationAsinacionPlazasLink);

			wait.until(ExpectedConditions.elementToBeClickable(locationAPagregarButton));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar a Asignación de plazas");
		}
	}

	/**
	 * Búsca en el menú "Configuración Plaza Plaza" la opción "Catálogo AB Correo" e
	 * ingresa.
	 * 
	 * @author 73289
	 */
	public void goToCatalogoABcorreo() {
		try {
			goToConfigPlazaPlaza();

			wait.until(ExpectedConditions.elementToBeClickable(locationCatalogoABcorreoLink));
			u.highLight(locationCatalogoABcorreoLink);
			u.scroll();
			u.click(locationCatalogoABcorreoLink);

			wait.until(ExpectedConditions.elementToBeClickable(locationABagregarButton));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar a Catálogo AB Correo");
		}
	}

	/**
	 * Búsca en el menú "Configuración Plaza Plaza" la opción "Combinabilidad" e
	 * ingresa.
	 * 
	 * @author 73289
	 */
	public void goToCombinabilidad() {
		try {
			goToConfigPlazaPlaza();

			wait.until(ExpectedConditions.elementToBeClickable(locationCombinabilidadLink));
			u.highLight(locationCombinabilidadLink);
			scrollToElement(locationCombinabilidadLink);
			u.click(locationCombinabilidadLink);

			wait.until(ExpectedConditions.elementToBeClickable(locationCBAgregarButton));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar a Combinabilidad");
		}
	}

	/**
	 * Búsca en el menú "Configuración Plaza Plaza" la opción "Compatibilidad" e
	 * ingresa.
	 * 
	 * @author 73289
	 */
	public void goToCompatibilidad() {
		try {
			goToConfigPlazaPlaza();

			wait.until(ExpectedConditions.elementToBeClickable(locationCompatibilidadLink));
			u.highLight(locationCompatibilidadLink);
			scrollToElement(locationCompatibilidadLink);
			u.click(locationCompatibilidadLink);

			wait.until(ExpectedConditions.elementToBeClickable(locationCPplazaSelect));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar a Compatibilidad");
		}
	}

	/**
	 * Búsca en el menú "Configuración Plaza Plaza" la opción "Conjuntos" e ingresa.
	 * 
	 * @author 73289
	 */
	public void goToConjuntos() {
		try {
			goToConfigPlazaPlaza();

			wait.until(ExpectedConditions.elementToBeClickable(locationConjuntosLink));
			u.highLight(locationConjuntosLink);
			u.click(locationConjuntosLink);

			wait.until(ExpectedConditions.elementToBeClickable(locationCJcrearButton));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar a Conjuntos");
		}
	}

	/**
	 * Búsca en el menú "Configuración Plaza Plaza" la opción "Equivalencias" e
	 * ingresa.
	 * 
	 * @author 73289
	 */
	public void goToEquivalencias() {
		try {
			goToConfigPlazaPlaza();

			wait.until(ExpectedConditions.elementToBeClickable(locationEquivalenciasLink));
			u.highLight(locationEquivalenciasLink);
			u.click(locationEquivalenciasLink);

			wait.until(ExpectedConditions.elementToBeClickable(locationEQrecuperarValoresButton));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar a Equivalencias");
		}
	}

	/**
	 * Búsca en el menú "Configuración Plaza Plaza" la opción "Legos" e ingresa.
	 * 
	 * @author 73289
	 */
	public void goToLegos() {
		try {
			goToConfigPlazaPlaza();

			wait.until(ExpectedConditions.elementToBeClickable(locationLegosLink));
			u.highLight(locationLegosLink);
			u.click(locationLegosLink);

			loadingDialogBox();
			wait.until(ExpectedConditions.elementToBeClickable(locationTextLegos));
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se pudo ingresar a Legos");
		}
	}

	public void loadingDialogBox() {
		try {
			By loading = By.cssSelector(
					"body > div.blockUI.blockMsg.blockPage.ui-dialog.ui-widget.ui-corner-all.ui-widget-content.ui-draggable > div.ui-widget-content.ui-dialog-content");
			while (u.getDriver().findElement(loading).isDisplayed()) {
				u.hardWait(2);
				System.out.println("loading");
			}
			u.hardWait(1);
		} catch (Exception e) {

		}
	}

	/**
	 * Regresa el texto mostrado en el cuadro de diálogo.
	 * 
	 * @return String
	 * @author 73289
	 */
	public String getTextDialogBox() {
		String resultMsnString = "";
		try {
			wait.until(ExpectedConditions.elementToBeClickable(locationMsgPopUp));
			resultMsnString = u.getDriver().findElement(locationMsgPopUp).getText();
			WebElement m = u.getDriver().findElement(By.cssSelector(".ui-dialog-titlebar"));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", m);
			u.hardWait(1);
		} catch (Exception e) {
			// System.out.println(" -> " + e);
			// throw new AssertionError("Error al extraer el texto del cuadro de diálogo");
		}
		return resultMsnString;
	}

	/**
	 * Da clic en el botón correspondiente del cuadro de diálogo. Las opciones son:
	 * aceptar, si, no, salir.
	 * 
	 * @param option
	 * @author 73289
	 */
	public void selectOptionDialogBox(String option) {
		try {
			switch (option.toUpperCase()) {
			case "ACEPTAR":
				wait.until(ExpectedConditions.elementToBeClickable(locationMsgPopUpAceptar));
				u.highLight(locationMsgPopUpAceptar);
				u.click(locationMsgPopUpAceptar);
				break;
			case "SI":
				wait.until(ExpectedConditions.elementToBeClickable(locationMsgPopUpSi));
				u.highLight(locationMsgPopUpSi);
				u.click(locationMsgPopUpSi);
				u.hardWait(3);
				break;
			case "NO":
				wait.until(ExpectedConditions.elementToBeClickable(locationMsgPopUpNo));
				u.highLight(locationMsgPopUpNo);
				u.click(locationMsgPopUpNo);
				break;
			case "SALIR":
				wait.until(ExpectedConditions.elementToBeClickable(locationMsgPopUpSalir));
				u.highLight(locationMsgPopUpSalir);
				u.click(locationMsgPopUpSalir);
				break;
			default:
				throw new AssertionError("La opción: " + option.toUpperCase() + " no es valida");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			throw new AssertionError("No se encontró la opción: " + option.toUpperCase());
		}
	}
	
	public void scrollToElement(By element) {
		WebElement moveToelement = u.getDriver().findElement(element);
		Coordinates coordinate = ((Locatable) moveToelement).getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();
	}

}
