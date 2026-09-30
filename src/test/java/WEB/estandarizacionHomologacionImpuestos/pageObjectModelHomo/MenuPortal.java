package WEB.estandarizacionHomologacionImpuestos.pageObjectModelHomo;

import java.time.Duration;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MenuPortal {
	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	By textoPortalAplicaciones = By.xpath("/html/body/div/div/form/div/div/div/h1[1]");
	By portalCatalogosSAT = By.xpath("//a[contains(text(), 'SAT')]");

	/**
	 * Constructor para el menu principal del Portal de Aplicaciones OXXO
	 * 
	 * @param u
	 * @param testCase
	 * @author nellyoliviab@hexaware.com
	 */
	public MenuPortal(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Metodo para seleccionar la opcion de menu del portal
	 * 
	 * @param campoActual --------------opcion de menu a seleccionar
	 * @param captura     ------------true para obtener la captura en la evidencia
	 * 
	 * @return regresa true si se encontro el elemento
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public boolean seleccionMenu(String campoActual, boolean captura) {
		By elementoSeleccion = obtenerElemento(campoActual);
		if (elementoSeleccion != null) {
			u.scroll(350);
			try {
				u.highLight(elementoSeleccion);
				if (captura) {
					testCase.addScreenShotCurrentStep("Seleccion " + campoActual);
				}
				u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				u.unhighLight(elementoSeleccion);
				u.getDriver().findElement(elementoSeleccion).click();
				return true;

			} catch (Exception e) {
				logger.error("Error" + e);
				throw new AssertionError("Error al seleccionar el menu '" + campoActual + "'");
			}
		}
		return false;

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
		case "portalCatalogosSAT":
			elemento = portalCatalogosSAT;
			break;
		}
		return elemento;
	}

	/**
	 * Metodo para obtener el texto desplegado en la Pantalla de Inicio
	 * 
	 * @param user-- usuario de acceso
	 * @param pass-- password de acceso
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String accesoMenuPortal(String user, String pass) {
		try {
			return u.getDriver().findElement(textoPortalAplicaciones).getText();
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return "";
		}
	}
}
