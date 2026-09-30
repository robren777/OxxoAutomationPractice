package WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MenuPortalSAT {
	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	By menuCatalogoSAT = By.xpath("//*[@id=\"ddtopmenubar\"]/ul/li[3]/a");

	By opcionCatalogosProductos = By.xpath(
			"//a[@href='/portalFiscalizacion-web/views/catalogosSat/serviciosSat.do?page=%2FportalFiscalizacion-web%2Fviews%2FcatalogosSat%2FserviciosSat.do']");

	By menuCatalogoGeneralDeImpuestos = By.xpath("//a[text()='Catalogo general de impuestos']");
	By opcionInformacionDeImpuestos = By.xpath("//a[contains(text(), 'Estructura de Impuestos')]");

	By menuConsultarImpuestos = By.xpath("//a[text()='Consultar impuestos']");
	By opcionImpuestosCompraVenta = By.xpath("//*[@id=\"ddsubmenu441\"]/li[1]/a");

	By opcionElementosObligatorios = By.xpath("//*[@id=\"ddsubmenu20139\"]//li[2]/a");
	By opcionConsultaDeImpuestosAsignados = By.xpath("//a[contains(text(), 'Asignados')]");
	// ul[@id='ddsubmenu20138']/li[2]/a

	/**
	 * Constructor para el menu principal del Portal SAT
	 * 
	 * @param u
	 * @param testCase
	 * @author nellyoliviab@hexaware.com
	 */
	public MenuPortalSAT(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Metodo para mover el cursor a la opcion de menu en portal SAT
	 * 
	 * @param campoActual --------------opcion de menu a seleccionar
	 * @param captura     ------------true para obtener la captura en la evidencia
	 * 
	 * @return regresa true si se encontro el elemento
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	
	public boolean moverCursorSobreOpcionMenu(String campoActual, boolean captura) {
		By elementoSeleccion = obtenerElemento(campoActual);
		if (elementoSeleccion != null) {
			try {
				u.highLight(elementoSeleccion);
				if (captura) {
					testCase.addScreenShotCurrentStep("Seleccion" + campoActual);
				}
				u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				u.unhighLight(elementoSeleccion);
				WebElement elementoWeb = u.getDriver().findElement(elementoSeleccion);
				new Actions(u.getDriver()).moveToElement(elementoWeb).perform();
				return true;
			} catch (Exception e) {
				logger.error("Error" + e);
				throw new AssertionError("Error al seleccionar el menu " + campoActual);
			}
		}
		return false;
	}

	/**
	 * Metodo para seleccionar la opcion del menu donde se encuentra el cursor
	 * 
	 * @param campoActual --------------opcion de menu a seleccionar
	 * @param captura     ------------true para obtener la captura en la evidencia
	 * 
	 * @return regresa true si se encontro el elemento
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionOpcionDelMenuDesplegado(String campoActual, boolean captura) {
		By elementoSeleccion = obtenerElemento(campoActual);
		try {
			u.highLight(elementoSeleccion);
			if (captura) {
				testCase.addScreenShotCurrentStep("Seleccion" + campoActual);
			}
			u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			u.unhighLight(elementoSeleccion);
			u.getDriver().findElement(elementoSeleccion).click();
			return true;
		} catch (Exception e) {
			logger.error("Error" + e);
			throw new AssertionError("Error al seleccionar el menú '" + campoActual + "'");
		}
	}

	/**
	 * Metodo para asignar el elemento By con el que se realizara interaccion en el
	 * paso actual
	 * 
	 * @param nombreDelCampo --------------es el campo de la pagina actual que se
	 *                       asignara al By
	 * 
	 * @return regresa el elemento By con el que se realizaran las futuras
	 *         interacciones (captura, seleccion, obtener texto, etc)
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public By obtenerElemento(String nombreDelCampo) {
		By elemento = null;
		switch (nombreDelCampo) {
		case "catalogoSAT":
			elemento = menuCatalogoSAT;
			break;
		case "consultarImpuestos":
			elemento = menuConsultarImpuestos;
			break;
		case "ImpuestosCompraVenta":
			elemento = opcionImpuestosCompraVenta;
			break;
		case "catalogoGeneralDeImpuestos":
			elemento = menuCatalogoGeneralDeImpuestos;
			break;
		case "informacionDeImpuestos":
			elemento = opcionInformacionDeImpuestos;
			break;
		case "ConsultaDeImpuestosAsignados":
			elemento = opcionConsultaDeImpuestosAsignados;
			break;
		case "elementosObligatorios":
			elemento = opcionElementosObligatorios;
			break;
		default:
			throw new AssertionError("No se encontró la opción '" + nombreDelCampo + "'");
		}
		return elemento;
	}
}