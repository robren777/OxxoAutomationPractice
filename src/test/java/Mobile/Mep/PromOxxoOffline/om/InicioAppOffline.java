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
 * DESCRIPCIÓN: Page Object Model que representa la pantalla inicial de la aplicación
 * en modo sin conexión. Contiene los localizadores y métodos de interacción/validación
 * para la vista "No hay conexión a internet" y el banner de modo sin conexión.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *   - Casos de prueba del módulo PromOXXO Offline relacionados con la pantalla inicial.
 *
 * PREREQUISITOS:
 *   - El dispositivo no tiene conexión a internet activa.
 *   - La aplicación está instalada en el dispositivo.
 *
 * INSUMOS:
 *   - N/A
 *
 * @author Mario A. De León Rdz.
 * @date 15/Diciembre/2025
 */
public class InicioAppOffline {
	/**
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
	private AppiumUtil au;
	private BaseExecution baseExecution;

	/**
	 * Localizadores Login
	 ******************************************************************************************/

	private final By noInternetMsg = AppiumBy.androidUIAutomator("new UiSelector().text(\"No hay conexión a internet\")");
	private final By btnActualizarSinConexion = AppiumBy.accessibilityId("Actualizar");
	private final By btnAbrirModoSinConexión = AppiumBy.androidUIAutomator("new UiSelector().description(\"Abrir modo sin conexión\")");
	
	private final By textEstasTrabajandoSinConexion = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"collapsible-banner-title\")");
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase PromOxxoOffline
	 * 
	 * @param au            (AppiumUtil): objeto de la clase
	 * @param testCase      (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 * @author Alejandro Montalvo
	 * @return PromOxxoOffline Instancia de la clase.
	 * @date 15/Diciembre/2025
	 */
	public InicioAppOffline(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
		this.testCase = testCase;
		this.au = au;
		this.baseExecution = baseExecution;
	}

	/**
	 * Valida el mensaje de Modo Offline en pantalla inicial.
	 * 
	 * @author Mario A. De León Rdz.
	 * @return boolean Retorna {@code true} si el mensaje es visible.
	 * @date 15/Diciembre/2025
	 */
	public boolean validarModoOfflineInicio() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(noInternetMsg));
			testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
			testCase.addTextEvidenceCurrentStep("Se muestra  la pantalla \"No hay conexión a internet\", que tiene: - Logotipo de \"OXXO\""
					+ " - Imagen de \"Problema de wifi\" - Título \"No hay conexión a internet\" - Texto de descripción - Botón \"Actualizar\" con"
					+ " texto blanco y fondo azul - Botón \"Abrir modo sin conexión\" con texto azul y fondo celeste");
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Valida el mensaje de Modo Offline en pantalla inicial sin mandar evidencia al reporte.
	 * 
	 * @author Mario A. De León Rdz.
	 * @return boolean Retorna {@code true} si el mensaje es visible.
	 * @date 15/Diciembre/2025
	 */
	public boolean validarModoOfflineInicioSinEv() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(noInternetMsg));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Valida que se muestre el botón Abrir Modo Sin Conexión ya que la existencia de este botón indica si ya se encontraba un usuario registrado o no.
	 * En caso de que no se haya iniciado sesión previamente no se muestra el botón.
	 * 
	 * @author Mario A. De León Rdz.
	 * @return boolean Retorna {@code true} si el mensaje es visible.
	 * @date 15/Diciembre/2025
	 */
	public boolean validarBotonAbrirSinConexión() {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(btnAbrirModoSinConexión));
			testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
			testCase.addTextEvidenceCurrentStep("Se muestra la pantalla \"No hay conexión a internet\", que tiene: "
					+ "- Logotipo de \"OXXO\" - Imagen de \"Problema de wifi\" - Título \"No hay conexión a internet\" - Texto de descripción - Botón \"Actualizar\" con texto blanco y fondo azul");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Realiza clic al botón actualizar de la pantalla No hay conexión a internet
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 18/Diciembre/2025
	 */
	public void clickActualizarOffline() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.elementToBeClickable(btnActualizarSinConexion));
	        au.click(btnActualizarSinConexion);

	        testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
	        testCase.addTextEvidenceCurrentStep("Se realiza clic al botón Actualizar");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
	    }
	}
	
	/**
	 * Realiza clic al botón actualizar de la pantalla No hay conexión a internet
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 18/Diciembre/2025
	 */
	public void clickAbrirModoSinConexion() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.elementToBeClickable(btnAbrirModoSinConexión));
	        au.click(btnAbrirModoSinConexión);

	        testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
	        testCase.addTextEvidenceCurrentStep("Se realiza clic al botón Abrir Modo Sin Conexión");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
	    }
	}
	
	/**
	 * Realiza clic al botón actualizar de la pantalla No hay conexión a internet
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickEstasTrabajandoSinConexion() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(textEstasTrabajandoSinConexion));
	        au.click(btnAbrirModoSinConexión);

	        testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
	        testCase.addTextEvidenceCurrentStep("Se realiza clic al banenr Estas Trabajando Sin Conexión");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
	    }
	}
    

   
}