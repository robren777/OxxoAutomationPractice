package WEB.AccessControl.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.selenium.SeleniumUtil;
import modelo.TestCase;

public class PortalHomePage {
	/**
	 * Se crean todos los metodos para que nos abra el navegador y se muestre la
	 * pagina de Inicio del portal, estos metodos son desarrollados por una
	 * herramienta(IA)
	 * 
	 * @author Emmanuel Gomez
	 * @return mensajes por si logra fallar el metodo
	 * @date 12/Diciembre/2025
	 */
	private final SeleniumUtil u;
	private final By paginaPrincipal = By.xpath("//h4[normalize-space()='General']");

	public PortalHomePage(SeleniumUtil u, TestCase testCase) {
		this.u = u;
	}

	private boolean elementExists(By locator, int timeoutSeconds) {
		try {
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Se crea el metodo para validar que exista la pagina una vez que se ingresa al
	 * portal
	 * 
	 * @author Emmanuel Gomez
	 * @return retorna el valor que se encuentra en la variable paginaPrincipal y le
	 *         da un tiempo
	 * @date 12/Diciembre/2025
	 */

	public boolean existePaginaPrincipal() {
		return elementExists(paginaPrincipal, 20);
	}
}