package Mobile.preinventariosinteligentes.om;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class Login {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/*
	 * Localizadores de inicio de sesion
	 * */
	private By user = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/etUserName");
	private By password = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/etPassword");
	private By btnLogin = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/btnLogin");
	private By imgLogo = By.id("com.femco.oxxo.operaciones.mitienda.debug:id/ivLogo");

	private By btnControl = By.xpath("(//android.widget.ImageView[@resource-id=\"com.femco.oxxo.operaciones.mitienda.debug:id/ibImage\"])[2]");


	/**
	 * Constructor de la clase Login
	 * @author Marcos Morales
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
	public Login(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}


	/**
	 * Espera a que un elemento este visible en pantalla dentro de un periodo de tiempo
	 * @author Marcos Morales
	 * @param e Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 */
	public void waitForVisibility(WebElement e, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOf(e));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento no se volvió visible dentro del tiempo esperado: ", ex);
		}
	}

	/**
	 * Espera a que un elemento este visible en pantalla dentro de un periodo de tiempo
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 */
	public void waitForVisibility(By locator, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento no se volvió visible dentro del tiempo esperado: ", ex);
		}
	}

	/**
	 * Espera a que un elemento se oculte dentro de un periodo de tiempo
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 */
	public void waitForHidden(By locator, Duration timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, timeout);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento todavía es visible después del tiempo de espera: " + locator, ex);
		}
	}


	/**
	 * Método para inicio de sesion en aplicación Oxxo al Día
	 * @author Marcos Morales
	 * @param nombreDeUsuario Usuario
	 * @param contraDeUsuario Contraseña
	 */
	public void login(String nombreDeUsuario, String contraDeUsuario){
		au.driver.hideKeyboard();
		waitForVisibility(user, Duration.ofSeconds(30));
		au.sendKeys(user, nombreDeUsuario);
		waitForVisibility(password, Duration.ofSeconds(30));
		au.sendKeys(password, contraDeUsuario);
		au.click(imgLogo);
		au.click(btnLogin);
		waitForVisibility(btnControl, Duration.ofSeconds(30));
	}
}