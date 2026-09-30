package WEB.updateNomina.om;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;

public class Login{
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private MetodosExtra metodosExtra;
	private SikuliUtil sk;
	
	//Elementos la conexión no es privada
	By botonAvanzado = By.xpath("/html/body/div/div[2]/button[3]");
	By linkContinuar = By.xpath("/html/body/div/div[3]/p[2]/a");
	
	//Elementos AuthFemco
	By inputNombreDeUsuario = By.xpath("/html/body/div/div[1]/div/form/div[1]/input");
	By inputContrasenia = By.xpath("/html/body/div/div[1]/div/form/div[2]/input");
	By botonInciarSesion = By.xpath("/html/body/div/div[1]/div/form/button[1]");
	By botonMostrarMasFyA = By.xpath("/html/body/app-root/app-access-control/div/div/div/div[2]/div[1]/div/div[2]/button");
	By sadelAuthFemco= By.xpath("/html/body/app-root/app-access-control/div/div/div/div[2]/div[1]/div/div[2]/div[2]/p/a");
	
	//Elementos Internet Explorer Fcptlqa
	By inputCuenta = By.xpath("/html/body/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table/tbody/tr[1]/td[2]/input");
	By inputCuenta2 = By.name("pu");
	By inputContrasena = By.xpath("/html/body/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table/tbody/tr[2]/td[2]/input");
	By inputBotonEntrar = By.xpath("/html/body/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table/tbody/tr[3]/td/input[2]");
	
	//Portal Fcptlqa
	By aSadelNominaComisionista = By.xpath("/html/body/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[2]/td/form/table/tbody/tr/td[2]/a");

	//Acces Control fcowlq02.femcom.net:7032/AccessControl/pages/login_form.jsf
	// Your connection isn't private//
	By btnAdvance = By.id("details-button");
	 By linkContinue = By.id("proceed-link");

	// LOGIN
	 By locationUserInput = By.id("login:userName");
	 By locationPassInput = By.id("login:userPass");
	 By locationLoginButton = By.id("login:loginButton");

	// INICIO PORTAL APLICACIONES
	 By locationMenuInicio = By.id("menu_cnt");

	/**
	 * Constructor.
	 * 
	 * @param u
	 * @param testCase
	 * @author Mario A. De Leon Rdz.
	 */
	public Login(SeleniumUtil u, TestCase testCase) {
	    super(); // Llama al constructor de la clase padre (en este caso, Object)
	    this.u = u; // Asigna el objeto SeleniumUtil pasado como parámetro a la variable de instancia
	    this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60)); // Inicializa WebDriverWait con el driver de SeleniumUtil y un timeout de 60 segundos
	    this.testCase = testCase; // Asigna el objeto TestCase pasado como parámetro a la variable de instancia

	}
	
	/**
	 * Método para ingrear al portal de aplicaciones (Hay que tener la segunda autenticación desactivada para su funcionamiento correcto)
	 * @author Mario A. De Leon Rdz.
	 */
	
	public void IngresarAAuthFemco(String user, String ps) {
		try {
			u.getDriver().manage().deleteAllCookies();
			String url = "https://fcportalqa.femcom.net:8443/AuthFemcoProx/login\"";
			testCase.addTextEvidenceCurrentStep(url);
			
			u.get(url);
			u.getDriver().manage().window().maximize();
			u.click(botonAvanzado);
			u.click(linkContinuar);
			
			u.getDriver().navigate().refresh();
			wait.until(ExpectedConditions.elementToBeClickable(inputNombreDeUsuario));
			u.sendKeys(inputNombreDeUsuario, user);
			u.sendKeys(inputContrasenia, ps);
			u.click(botonInciarSesion);
			} catch (Exception e) {
				System.out.println(" -> " + e);
				this.u.close();
				throw new AssertionError("No se pudo ingresar a https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
			} 
		
	};
	
	public void IngresarAFcptlqa(String user, String ps) {
		try {
			u.getDriver().manage().deleteAllCookies();
			String url = "http://fcptlqa.femcom.net:7777/AccessControl/login_form.jsp";
			testCase.addTextEvidenceCurrentStep(url);
		
			u.get(url);
			wait.until(ExpectedConditions.elementToBeClickable(inputCuenta));
			u.highLight(inputCuenta);
			u.sendKeys(inputCuenta, user);
			u.highLight(inputContrasena);
			u.sendKeys(inputContrasena, ps);
			u.click(inputBotonEntrar);
			} catch (Exception e) {
				System.out.println(" -> " + e);
				this.u.close();
				throw new AssertionError("No se pudo ingresar a http://fcptlqa.femcom.net:7777/AccessControl/login_form.jsp");
			} 
	};
	
	/**
	 * Método para ingresar al aplicativo SADEL despues de ingresar al portal de aplicaciones
	 * @author Mario Alberto De Leon Rdz.
	 */
	//Metodos para casos de prueba de aplicativo SADEL
	public void SeleccionarSADEL() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(botonMostrarMasFyA));
			testCase.addScreenShotCurrentStep(u, "linkSADEL");
			u.click(botonMostrarMasFyA);
			u.click(sadelAuthFemco);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en enlace para ingresar a SADEL NOMINA COMISIONISTA");
		}
	}
	
	public void SeleccionarSADELFcptlqa() {
		try {
			testCase.addScreenShotCurrentStep(u, "aSadelNominaComisionista");
			wait.until(ExpectedConditions.elementToBeClickable(aSadelNominaComisionista)).click();
			//u.getDriver().navigate().to("http://fcowlq06.femcom.net:8000/sadelnom/employeePendingImss.do");
			System.out.println("refresh");
			u.getDriver().navigate().refresh();
			testCase.addScreenShotCurrentStep(u, "se ingres� a SADEL");

		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo dar click en enlace para ingresar a SADEL NOMINA COMISIONISTA");
		}
	}

	public void IngresarCntMex(String user, String ps) {
		try {
			u.getDriver().manage().deleteAllCookies();
			String url = "";
			testCase.addTextEvidenceCurrentStep(url);

			u.get(url);
			u.getDriver().manage().window().maximize();
			u.click(botonAvanzado);
			u.click(linkContinuar);

			u.getDriver().navigate().refresh();
			wait.until(ExpectedConditions.elementToBeClickable(inputNombreDeUsuario));
			u.sendKeys(inputNombreDeUsuario, user);
			u.sendKeys(inputContrasena, ps);
			u.click(botonInciarSesion);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			this.u.close();
			throw new AssertionError("No se pudo ingresar a https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
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
	 * Login en Access Control con la url
	 * "https://fcportalqa.femcom.net:8443/AccessControl/pages/login_form.jsf";
	 *
	 * @param user
	 * @param ps
	 */
	public void loginAccessControlMOF(String user, String ps) {
		try {
			Boolean bandera = false;
			Integer cont = 0;
			u.hardWait(1);
			u.getDriver().manage().deleteAllCookies();
			String url = "http://fcowlq02.femcom.net:7032/AccessControl/pages/login_form.jsf";
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
	
	
} 