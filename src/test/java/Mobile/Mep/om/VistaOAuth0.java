package Mobile.Mep.om;

import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.Set;

import org.bouncycastle.asn1.dvcs.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class VistaOAuth0 {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
	/*
	 * Localizadores Login
	 ******************************************************************************************/
    private By campoNumeroDeEmpleado = By.xpath("//android.widget.EditText[@resource-id=\"custom-signInName\"]");
    private By pinPredeterminated = By.xpath("//android.widget.Button[@resource-id=\"btn-1\"]");
    private By btnSiguiente = By.xpath("//android.widget.Button[@resource-id=\"custom-signin\"]");
    
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase VistaOAuth0
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaOAuth0(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    
    /**
	 * Espera a que un elemento se muestre dentro de un periodo de tiempo
	 * 
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 * @date 09/10/2024
	 */
    public void waitForVisibility(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento no se volvió visible dentro del tiempo esperado: ", ex);
		}
	}
    
    /**
     * Metodo para realizar el proceso de inicio de sesión utilizando autenticación OAuth de Google dentro de la vista WebView del entorno Chrome.
     * @author Ivan Yakab
     * @param numEmpleado Valor que se ingresará en el campo "Número de empleado" para autenticarse.
     * @date 12/Septiembre/2025
     */
    public void LoginOAuthGoogleTab(String numEmpleado) throws InterruptedException
    {
    	esperarYCambiarAWebViewChrome();
		
		au.driver.findElement(campoNumeroDeEmpleado).isDisplayed();
    	au.driver.findElement(campoNumeroDeEmpleado).sendKeys(numEmpleado);

			for (int i = 0; i < 4; i++) {
				au.driver.findElement(pinPredeterminated).click();
			}
			testCase.addScreenShotCurrentStep(au, "testImage");

		waitForVisibility(btnSiguiente, 15);
		au.driver.findElement(btnSiguiente).click();
		
		au.driver.context("NATIVE_APP");
    }
    
    /**
     * Espera dinámicamente hasta que el contexto {@code WEBVIEW_chrome} esté disponible 
     * y realiza el cambio de contexto en el driver de Appium.
     * <p>
     * Este método revisa periódicamente (cada 500 ms) los contextos disponibles en la sesión
     * de Appium durante un máximo de 10 segundos. Si encuentra el contexto {@code WEBVIEW_chrome},
     * cambia automáticamente a él. Si no lo encuentra en ese tiempo, lanza una excepción.
     * </p>
     *
     * <p>Uso típico cuando se necesita interactuar con contenido WebView en aplicaciones híbridas.</p>
     *
     * @throws RuntimeException si el contexto {@code WEBVIEW_chrome} no se encuentra dentro del tiempo especificado.
     */

    public void esperarYCambiarAWebViewChrome() {
        String contextoDeseado = "WEBVIEW_org.chromium.webview_shell"; // Asegúrate de que este sea el nombre exacto
        int tiempoEsperado = 0;
        int intervalo = 500;
        int timeoutInSeconds = 15;


        while (tiempoEsperado < timeoutInSeconds * 1000) {
            Set<String> contextNames = au.driver.getContextHandles();
            
            for (String contextName : contextNames) {
                if (contextName.equalsIgnoreCase(contextoDeseado)) {
                    au.driver.context(contextName);
                    System.out.println("Contexto cambiado a: " + contextName);
                    return;
                }
            }

            try { Thread.sleep(intervalo); } catch (InterruptedException ignored) {}
            tiempoEsperado += intervalo;
        }

        throw new RuntimeException("No se encontró el contexto " + contextoDeseado + " después de " + timeoutInSeconds + " segundos.");
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
			wait.until(ExpectedConditions.elementToBeClickable(campoNumeroDeEmpleado)).click();
			au.sendKeys(campoNumeroDeEmpleado, user);

			for (int i = 0; i < 4; i++) {
				// La acción del clic se ejecuta 4 veces
				wait.until(ExpectedConditions.elementToBeClickable(pinPredeterminated)).click();
			}
			wait.until(ExpectedConditions.elementToBeClickable(btnSiguiente)).click();
		} catch (Exception e) {
			e.printStackTrace();
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
		    throw new Exception("Error al realizar login", e);
		    
		}
	}

    
}
