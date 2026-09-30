package WEB.AccessControl;

import static org.testng.Assert.assertNotNull;

import java.util.HashMap;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import WEB.util.AccessControlPortalLogin;
import WEB.util.AuxMethods;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/* DESCRIPCION : Validar que un usuario externo pueda iniciar sesión correctamente en el portal Access Control
 * 
 * MTC-FT-02-ACCESO AL PORTAL DE ACCESS CONTROL(DOMINIO INTERNO) CON USUARIO DE OXXO
 * 
 * PREREQUISITOS:
 * 	TENER ACCESO AL INTERNET
 * 	USUARIO Y CONTRASEÑA DE LA VPN
 * 	TENER A LA MANO EL CELULAR POR EL DOBLE FACTOR
 * 	USUARIO Y CONTRASEÑA DEL PORTAL
 * 	
 * 
 * INSUMOS:
 * 	Accesos al portal
 * 
 * @Author Emmanuel Gomez
 * @Date 27/Octubre/2025
 */
public class ATC_FT_002_AccesoPortalAccessControlUsuarioInterno extends BaseExecution {

	private SeleniumUtil u;
	private AccessControlPortalLogin AC;
	private AuxMethods aux;

	/**
	 * Prueba principal - obtiene datos desde DataProvider DataProvider debe
	 * contener las claves: - URL - email - password
	 */
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_AccesoPortalAccessControlUsuarioInterno_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Evolución Tecnlógica de Portales");

		// Obtener datos desde el DataProvider
		String url = data.get("URL");
		String usuario = data.get("email");
		String password = data.get("password");

		// Validación inicial de datos
		assertNotNull(url, "El campo URL no viene en el DataProvider.");
		assertNotNull(usuario, "El campo email no viene en el DataProvider.");
		assertNotNull(password, "El campo password no viene en el DataProvider.");

		// Inicializar Selenium
		addStep("Inicializar el navegador y abrir la URL del portal");
		u = new SeleniumUtil(new ChromeTest(), true);
		aux = new AuxMethods(u, testCase);
		AC = new AccessControlPortalLogin(u, testCase);

		u.get(url);
		testCase.addTextEvidenceCurrentStep("URL utilizada: " + url);
		aux.logP("1. Portal abierto correctamente.");

		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(50));

		// 🔹 1. Clic en botón de inicio de sesión
		addStep("Hacer clic en el botón 'Login Interno'");

		testCase.addScreenShotCurrentStep(u, "Botón de inicio de sesión presionado");
		JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
		WebElement botonVerificar = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginWithAzure")));
		js.executeScript("arguments[0].scrollIntoView({behavior:'instant', block:'center'});", botonVerificar);
		js.executeScript("arguments[0].style.border='5px solid red'", botonVerificar);
		botonVerificar.click();
		testCase.addScreenShotCurrentStep(u, "Se presionó el botón para ingresar al portal");

		// 🔹 2. Ingresar credenciales
		addStep("Ingresar usuario y contraseña");
		WebElement campoUsuario = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0116")));
		campoUsuario.clear();
		campoUsuario.sendKeys(usuario);
		js.executeScript("arguments[0].scrollIntoView({behavior:'instant', block:'center'});", campoUsuario);
		js.executeScript("arguments[0].style.border='5px solid red'", campoUsuario);
		aux.logP("3. Usuario ingresado: " + usuario);
		testCase.addScreenShotCurrentStep(u, "Usuario Correcto");

		// Botón siguiente (si lo hay en el flujo de Microsoft Auth)
		try {
			WebElement botonSiguiente = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
			botonSiguiente.click();
		} catch (Exception e) {
			aux.logP("No se encontró botón 'Siguiente', se continúa directamente.");
		}

		WebElement campoPassword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("i0118")));
		campoPassword.clear();
		campoPassword.sendKeys(password);
		js.executeScript("arguments[0].scrollIntoView({behavior:'instant', block:'center'});", campoPassword);
		js.executeScript("arguments[0].style.border='5px solid red'", campoPassword);
		aux.logP("4. Contraseña ingresada.");

		testCase.addScreenShotCurrentStep(u, "Credenciales ingresadas correctamente");

		WebElement botonEntrar = wait.until(ExpectedConditions.elementToBeClickable(By.id("idSIButton9")));
		botonEntrar.click();
		aux.logP("5. Clic en el botón 'Iniciar sesión'.");

		By opcionSMS = By.cssSelector("div[data-value='OneWaySMS']");
		WebElement smsOption = wait.until(ExpectedConditions.elementToBeClickable(opcionSMS));
		u.highLight(opcionSMS);
		smsOption.click();
		testCase.addScreenShotCurrentStep(u, "Opción 'Enviar mensaje de texto' seleccionada");

		WebElement botonComprobar = wait
				.until(ExpectedConditions.elementToBeClickable(By.id("idSubmit_SAOTCC_Continue")));
		botonComprobar.click();
		;
		aux.logP("5. Clic en el botón 'Iniciar sesión'.");

		// 🔹 3. Validar acceso exitoso
		addStep("Validar acceso exitoso al portal");
		WebElement elementoHome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dropdownBasic1")));

		assertNotNull(elementoHome,
				"No se encontró el elemento del portal tras el login. Verifica credenciales o flujo.");
		testCase.addScreenShotCurrentStep(u, "Acceso exitoso al portal");
		aux.logP("6. Acceso exitoso al portal confirmado.");

		// 🔹 4. Cerrar sesión (opcional)
		addStep("Cerrar sesión del portal");
		try {
			AC.logoutAccessControl(); // Usa el método del helper
			aux.logP("7. Sesión cerrada correctamente.");
			testCase.addScreenShotCurrentStep(u, "Cerrar Sesion Exitoso");
		} catch (Exception e) {
			aux.logP("⚠️ No se pudo cerrar sesión: " + e.getMessage());
		}

		// 🔹 5. Finalizar
		addStep("Finalizar prueba y cerrar navegador");
		u.close();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. TENER ACCESO A INTERNET\n" + "2. USUARIO Y CONTRASEÑA DE OXXO\n"
				+ "3. TENER A LA MANO EL CELULAR POR EL DOBLE FACTOR\n" + "4. TENER ACTIVA LA VPN";
	}

	@Override
	public String setTestDescription() {
		return "Validar que un usuario externo pueda iniciar sesión correctamente en el portal Access Control.";
	}

	@Override
	public String setTestDesigner() {
		return "Emmanuel Gomez";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}
}
