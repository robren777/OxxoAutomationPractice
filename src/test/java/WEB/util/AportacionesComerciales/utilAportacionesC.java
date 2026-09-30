package WEB.util.AportacionesComerciales;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import WEB.util.AuxMethods;
import utils.selenium.SeleniumUtil;

public class utilAportacionesC {
	private SeleniumUtil u;
	private AuxMethods aux;
	private TestCase testCase;
	private WebDriverWait wait;

	/// WEB ELEMENTS ///
	By frameAvPo = By.cssSelector("#contentFrame > iframe");

	By portalGestionProveedores = By.linkText("Gestión de Proveedores");
	By menuAportacionesC = By.cssSelector("#ddtopmenubar > ul > li > a[rel=\"ddsubmenu20040\"]");
	By opcionAportacionesC = By.id("j_idt146");

	By inputRFC = By.cssSelector("input[id=\"form:rfcSeleccionado\"]");
	By buttonBuscarRFCap = By.cssSelector("a[id=\"form:rfcs\"]");
	By buttonAceptarRFCap = By.cssSelector("a[id=\"form:seleccionar\"]");

	By labelTipoDoc = By.cssSelector("label[id=\"form:tipoDocSelec_label\"]");
	By buttonBuscarAPOR = By.cssSelector("a[id=\"form:j_idt46\"]");

	By labelPeriodo = By.cssSelector("label[id=\"form:selectPeriodo_label\"]");
	By labelTipoConvenio = By.cssSelector("label[id=\"form:j_idt32_label\"]");
	By labelRubro = By.cssSelector("label[id=\"form:j_idt38_label\"]");
	By inputFolio = By.cssSelector("input[id=\"form:paramUno\"]");

	By buttonDescargar = By.cssSelector("a[id=\"form:j_idt53\"]");
	By tableAportaciones = By.cssSelector("tbody[id=\"form:tablaArchivos_data\"] > tr");
	By tableEmpty = By.cssSelector("tbody[id=\"form:tablaArchivos_data\"] > tr.ui-datatable-empty-message");

	By cerrarSesion = By.cssSelector("a[id=\"btnLogout\"]");
	By user = By.cssSelector("input[id=\"login:userName\"]");

	/**
	 * Constructor. Recibe el SeleniumUtil y el testCase utilizado en el test.
	 * 
	 * @param SeleniumUtil
	 * @param testCase
	 * @author Esmeralda Garcia Torres
	 */
	public utilAportacionesC(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		aux = new AuxMethods(u, testCase);
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
	}

	/**
	 * Selecciona el portal de Gestion de Proveedores
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToPortalGestionProveedores() {
		aux.clickOpcion("Gestion de Proveedores", portalGestionProveedores, true);
		u.hardWait(2);
		// testCase.addScreenShotCurrentStep(u, "Portal");
	}

	/**
	 * Selecciona el menu "Aportaciones Comerciales" y el submenu "Aportaciones
	 * Comerciales"
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void goToAportacionesComerciales() {
		try {
			WebElement ele = u.getDriver().findElement(menuAportacionesC);
			Actions action = new Actions(u.getDriver());
			action.moveToElement(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al poner el mouse sobre el menu");
		}
		aux.clickOpcion("Ordenes Compra", opcionAportacionesC, true);
		u.hardWait(1);
	}

	/**
	 * Hace cambio al frame en el que estan los elementos para poder localizarlos.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void changeFrame() {
		try {
			WebElement fr = u.getDriver().findElement(frameAvPo);
			u.getDriver().switchTo().frame(fr);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al cambiar de frame");
		}
	}

	/**
	 * Hace cambio al frame padre para seleccionar opciones del menu.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void defaultFrame() {
		try {
			u.getDriver().switchTo().parentFrame();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al cambiar al frame principal");
		}
	}

	/**
	 * Busca y selecciona el RFC correspondiente.
	 * 
	 * @param rfc
	 * @author Esmeralda Garcia Torres
	 */
	public void buscarRFC(String rfc) {
		try {
			aux.enterOption("RFC", rfc, inputRFC, false);

			u.highLight(buttonBuscarRFCap);
			WebElement ele = u.getDriver().findElement(buttonBuscarRFCap);
			Actions action = new Actions(u.getDriver());
			action.click(ele).perform();
			u.unhighLight(buttonBuscarRFCap);

		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal ingresar el RFC");
		}
		try {
			u.hardWait(3);
			By rowRFC = By.cssSelector("tbody[id=\"form:tablaRfcs_data\"] > tr[data-rk=\"" + rfc + "\"]");
			aux.clickOpcion("Row RFC", rowRFC, false);
			aux.scrollToElement(buttonAceptarRFCap);
			aux.clickOpcion("AceptarRFC", buttonAceptarRFCap, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el RFC de la tabla");
		}
	}

	/**
	 * Selecciona el tipo de documento.
	 * 
	 * @param tipo
	 * @param evidence
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarTipoDoc(String tipo, Boolean evidence) {
		try {
			WebElement ele = u.getDriver().findElement(labelTipoDoc);
			Actions action = new Actions(u.getDriver());
			action.click(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el label del tipo de documento");
		}

		try {
			By opcion = By.cssSelector("li[data-label=\"" + tipo + "\"]");
			aux.clickOpcion("Tipo " + tipo, opcion, evidence);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el tipo de documento");
		}
	}

	/**
	 * Valida que se muestren las opciones en pantalla de Periodo, Tipo Convenio,
	 * Rubro y Folio.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void validarOpConvenio() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(labelPeriodo));
			wait.until(ExpectedConditions.visibilityOfElementLocated(labelTipoConvenio));
			wait.until(ExpectedConditions.visibilityOfElementLocated(labelRubro));
			wait.until(ExpectedConditions.visibilityOfElementLocated(inputFolio));
			testCase.addScreenShotCurrentStep(u, "Convenios");
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al validar las opciones en pantalla de Convenios");
		}
	}

	/**
	 * Valida que se muestre en pantalla la opci�n de Periodo.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void validarOpPeriodo() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(labelPeriodo));
			testCase.addScreenShotCurrentStep(u, "opcion periodo");
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al validar las opciones en pantalla de Factura XML");
		}
	}

	/**
	 * Realiza la busqueda del folio correspondiente.
	 * 
	 * @param folio
	 * @author Esmeralda Garcia Torres
	 */
	public void buscarFolioConvenio(String folio) {
		try {
			aux.enterOption("Folio Convenio", folio, inputFolio, false);
			aux.clickOpcion("Buscar", buttonBuscarAPOR, true);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al buscar el Folio");
		}

		try {
			u.hardWait(5);
			aux.scrollToElement(buttonDescargar);
			if (u.getDriver().findElement(tableEmpty).isDisplayed()) {
				testCase.addBoldTextEvidenceCurrentStep("No se encontraron registros");
			}
		} catch (Exception e) {
			List<WebElement> elementsTable = u.getDriver().findElements(tableAportaciones);
			if (elementsTable.size() > 1) {
				throw new AssertionError("La busqueda mostro mas de un resultado");
			}
		}
		testCase.addScreenShotCurrentStep(u, "Resultado Convenios");
	}

	/**
	 * Hace la busqueda en pantalla y toma evidencia de lo mostrado en la tabla.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void buscarApotaciones() {
		try {
			aux.clickOpcion("Buscar", buttonBuscarAPOR, true);
			u.hardWait(5);
			aux.scrollToElement(buttonDescargar);
			if (u.getDriver().findElement(tableEmpty).isDisplayed()) {
				testCase.addBoldTextEvidenceCurrentStep("No se encontraron registros");
			}
		} catch (Exception e) {

		}
		testCase.addScreenShotCurrentStep(u, "Resultado Convenios");
	}

	/**
	 * Selecciona el Periodo correspondiente.
	 * 
	 * @param periodo
	 * @param evidence
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarPeriodo(String periodo, Boolean evidence) {
		try {
			WebElement ele = u.getDriver().findElement(labelPeriodo);
			Actions action = new Actions(u.getDriver());
			action.click(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el periodo");
		}

		try {
			By opcion = By.cssSelector("li[data-label=\"" + periodo + "\"]");
			aux.clickOpcion("Tipo", opcion, evidence);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el periodo");
		}
	}

	/**
	 * Selecciona el Tipo de Convenio correspondiente.
	 * 
	 * @param convenio
	 * @param evidence
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarTipoConvenio(String convenio, Boolean evidence) {
		try {
			WebElement ele = u.getDriver().findElement(labelTipoConvenio);
			Actions action = new Actions(u.getDriver());
			action.click(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el convenio");
		}

		try {
			By opcion = By.cssSelector("li[data-label=\"" + convenio + "\"]");
			aux.clickOpcion("Tipo", opcion, evidence);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el convenio");
		}
	}

	/**
	 * Selecciona el Rubro correspondiente.
	 * 
	 * @param rubro
	 * @param evidence
	 * @author Esmeralda Garcia Torres
	 */
	public void seleccionarRubro(String rubro, Boolean evidence) {
		try {
			WebElement ele = u.getDriver().findElement(labelRubro);
			Actions action = new Actions(u.getDriver());
			action.click(ele).perform();
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el rubro");
		}

		try {
			By opcion = By.cssSelector("li[data-label=\"" + rubro + "\"]");
			aux.clickOpcion("Tipo", opcion, evidence);
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al seleccionar el rubro");
		}
	}

	/**
	 * Selecciona la opcion Descargar, en caso de no haber archivos muestra un
	 * mensaje.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void descargarArchivos() {
		Boolean band = true;
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(buttonDescargar));
		} catch (Exception e) {
			testCase.addBoldTextEvidenceCurrentStep("No hay archivos para descargar");
			testCase.addScreenShotCurrentStep(u, "No hay archivos");
			band = false;
		}
		if (band) {
			aux.clickOpcion("Descargar", buttonDescargar, true);
			testCase.addTextEvidenceCurrentStep("Obtener los archivos de la carpeta de descargas del equipo");
		}
	}

	/**
	 * Cierra sesion en el Portal de Aplicaciones.
	 * 
	 * @author Esmeralda Garcia Torres
	 */
	public void cerrarSesionPortal() {
		try {
			aux.scrollToElement(cerrarSesion);
			u.highLight(cerrarSesion);
			WebElement ele = u.getDriver().findElement(cerrarSesion);
			Actions action = new Actions(u.getDriver());
			testCase.addScreenShotCurrentStep(u, "Cerrar Sesion");
			u.unhighLight(cerrarSesion);
			action.click(ele).perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(user));
			testCase.addScreenShotCurrentStep(u, "Login");
		} catch (Exception e) {
			aux.logS("" + e);
			throw new AssertionError("Algo salio mal al Cerrar Sesion");
		}
	}

}
