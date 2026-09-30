package EBS.util.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Clase con las acciones y locators de la pagina de EBS necesarios para
 * realizar el inicio de sesion
 *
 * @author JesusH@hexaware.com
 * @version 1.0
 * @since 5/23/2022
 */
public class EBSLogin {
	SeleniumUtil u;
	TestCase testCase;
	String url;
	String user;
	String password;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	// Generacion del constructor

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @param url      Sitio al que se ingresara
	 * @param user     Nombre de usuario
	 * @param password Contraseña del usuario
	 * @author JesusH@hexaware.com
	 * @since 5/23/2022
	 */
	public EBSLogin(SeleniumUtil u, TestCase testCase, String url, String user, String password) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.url = url;
		this.user = user;
		this.password = password;
	}

	// Locators de la Pantalla de inicio de sesion
	By usernameField = By.id("usernameField");
	By passwordField = By.id("passwordField");
	By logInBtnXpath = By.xpath("//*[@id='ButtonBox']//button[@class='OraButton left']");
	By logInBtnEBSColXpath = By.xpath("//*[@id=\"SubmitButton\"]");
	By slangXpath = By.xpath("//div[@id='LanguagePickerBox']//select[@id='slang']");
	By SpanishSlangXpath = By.xpath("//div[@id='LanguagePickerBox']//select[@id='slang']//option[@lang='es']");
	By SpanishSlangColombiaXpath = By.xpath("//*[@id=\"langOptionsTable\"]/tbody/tr[3]/td[2]/a/img");

	/**
	 * Metodo para navegar al sitio
	 *
	 * @param url Direccion del sitio a visitar
	 * @author JesusH@hexaware.com
	 */
	public void navigate(String url) {
		try {
			u.get(url);
			u.waitForLoadPage();
		} catch (TimeoutException timeoutException) {
			logger.error("TimeOut " + timeoutException);
		}
	}

	/**
	 * Metodo para hacer click en el boton "Login" de la pagina de EBS
	 *
	 * @author JesusH@hexaware.com
	 */
	public void clickLogIn() {
		try {
			u.highLight(logInBtnXpath);
			u.click(logInBtnXpath);
			u.waitForLoadPage();
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}

	}

	/**
	 * Metodo para configurar el lenguaje como Español Latinoamericano
	 *
	 * @author JesusH@hexaware.com
	 */
	public void setLanguage() {
		try {
			u.highLight(slangXpath);
			Select language = new Select(u.getDriver().findElement(slangXpath));
			language.selectByValue("ESA");
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}

	}

	/**
	 * @author MisraimH@hexaware.com Metodo para configurar el lenguaje como Español
	 *         Latinoamericano EBS Colombia
	 */
	public void setLanguageEBSColombia() {
		try {
			u.highLight(SpanishSlangColombiaXpath);
			u.click(SpanishSlangColombiaXpath);
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}

	}

	/**
	 * Metodo para hacer click en el boton "Login" de la pagina de EBS Colombia
	 *
	 * @author MisraimH@hexaware.com
	 */
	public void clickLogInEBSColombia() {
		try {
			u.highLight(logInBtnEBSColXpath);
			u.click(logInBtnEBSColXpath);
			u.waitForLoadPage();
			u.screenShotFull("Login de la pagina de EBS Colombia");
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}

	}

	/**
	 * Metodo para ingresar los datos de inicio de sesión
	 *
	 * @author JesusH@hexaware.com
	 */
	public void setCredentials() {
		try {
			u.highLight(usernameField);
			u.sendKeys(usernameField, user);
			u.highLight(passwordField);
			u.sendKeys(passwordField, password);
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}

	}
}
