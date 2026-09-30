package Mobile.Mep.PromOxxoOffline.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

/**
 * DESCRIPCIÓN: Page Object Model que representa la vista de autenticación OAuth0
 * en modo sin conexión. Contiene los localizadores y métodos de interacción para
 * el flujo de inicio de sesión con número de empleado y PIN.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *   - Casos de prueba del módulo PromOXXO Offline relacionados con el inicio de sesión.
 *
 * PREREQUISITOS:
 *   - La aplicación está en la pantalla de inicio de sesión.
 *
 * INSUMOS:
 *   - Número de empleado
 *   - PIN (4 dígitos)
 *
 * @author Mario A. De Leon Rdz.
 * @date 14/Diciembre/2025
 */
public class VistaOAuth0Offline {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
	/*
	 * Localizadores Login
	 ******************************************************************************************/
	private final By btnSiguienteNuevoUsuario = AppiumBy.accessibilityId("Siguiente");
	private final By btnNuevoUsuario = AppiumBy.accessibilityId("Iniciar Sesión");
	private final By inputUsuario = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"custom-signInName\")");
	private final By btnNIP1 = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"btn-1\")");
	private final By siguienteBtn = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"custom-signin\")");

	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase VistaOAuth0
	 * @author Mario A. De Leon Rdz.
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaOAuth0Offline(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
	/**
	 * Realiza el proceso de inicio de sesión de un usuario en la aplicación móvil.
	 * Este método simula la entrada de un número de empleado y la subsecuente
	 * entrada de un PIN, haciendo clic repetidamente en un botón específico
	 * (simulando la entrada de los 4 dígitos del PIN con el mismo botón, o la
	 * acción de 'Enter').
	 *
	 * @param user     Nombre o número de identificación del empleado a ingresar.
	 * @param password PIN o contraseña (actualmente simulado por 4 clics).
	 * @author Mario A. De León Rdz.
	 * @throws Exception Si ocurre un error al interactuar con los elementos del
	 *                   driver.
	 * @return boolean Retorna {@code true} si la secuencia de entrada de datos y
	 *         clics se ejecuta sin excepciones,
	 * @date 14/Diciembre/2025
	 */
	public void loginUser(String user) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(btnNuevoUsuario)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(inputUsuario)).click();
			au.sendKeys(inputUsuario, user);

			for (int i = 0; i < 4; i++) {
				// La acción del clic se ejecuta 4 veces
				wait.until(ExpectedConditions.elementToBeClickable(btnNIP1)).click();
			}
			wait.until(ExpectedConditions.elementToBeClickable(siguienteBtn)).click();
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
		    throw new Exception("Error al realizar login", e);
		    
		}
	}

    
}