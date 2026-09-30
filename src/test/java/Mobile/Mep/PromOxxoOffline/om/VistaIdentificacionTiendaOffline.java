package Mobile.Mep.PromOxxoOffline.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

/**
 * DESCRIPCIÓN: Page Object Model que representa la vista "Identifica tu tienda"
 * en modo sin conexión. Contiene los localizadores y métodos de interacción/validación
 * para el flujo de identificación de tienda (CR Plaza y CR Tienda).
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *   - Casos de prueba del módulo PromOXXO Offline relacionados con la identificación de tienda.
 *
 * PREREQUISITOS:
 *   - El dispositivo no tiene conexión a internet activa.
 *   - La aplicación está en la pantalla de identificación de tienda.
 *
 * INSUMOS:
 *   - crPlaza
 *   - crTienda
 *
 * @author Mario A. De León Rdz.
 * @date 12/Septiembre/2025
 */
public class VistaIdentificacionTiendaOffline {
    /*
     * Variables Globales
     ******************************************************************************************/
    private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;

    /*
     * Localizadores
     ******************************************************************************************/
    private By tituloIdentificaTuTienda = By.xpath("//android.widget.TextView[@text=\"Identifica tu tienda\"]");
    private By campoCrPlaza = By.xpath("//android.widget.EditText[@text=\"CR Plaza\"]");
    private By campoCrTienda = By.xpath("//android.widget.EditText[@text=\"CR Tienda\"]");
    private By btnSiguiente = By.xpath("//android.widget.TextView[@text=\"Siguiente\"]");

    /**
     * Constructor de la clase VistaIdentificacionTiendaOffline
     *
     * @author Mario A. De Leon Rdz.
     * @param au (AppiumUtil): objeto de la clase
     * @param testCase (TestCase): objeto de la clase
     * @param baseExecution (BaseExecution): objeto de la clase
     * @date 12/Septiembre/2025
     */
    public VistaIdentificacionTiendaOffline(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }

    /**
     * Metodo para ejecutar el flujo completo de login en la vista "Identificacion de Tienda"
     *
     * @author Mario A. De Leon Rdz.
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void loginApp(String crPlaza, String crTienda) throws Exception {
        validarVistaIdentificaTuTienda();
        ingresarCrPlaza(crPlaza);
        ingresarCrTienda(crTienda);
        clickBtnSiguiente();
    }

    /**
     * Metodo para ejecutar el flujo completo de login sin evidencia en la vista "Identificacion de Tienda"
     *
     * @author Mario A. De Leon Rdz.
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void loginAppSinEv(String crPlaza, String crTienda) throws Exception {
        validarVistaIdentificaTuTiendaSinEv();
        ingresarCrPlazaSinEv(crPlaza);
        ingresarCrTiendaSinEv(crTienda);
        clickBtnSiguienteSinEv();
    }

    /**
     * Metodo para validar que se muestra el titulo 'Identifica tu tienda' en la vista "Identificacion de Tienda"
     *
     * @author Mario A. De Leon Rdz.
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void validarVistaIdentificaTuTienda() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloIdentificaTuTienda));

            testCase.addScreenShotCurrentStep(au, "Validacion vista Identifica tu tienda");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'Identifica tu tienda'", e);
        }
    }
    /**
     * Metodo para validar que se muestra el titulo 'Identifica tu tienda' en la vista "Identificacion de Tienda"
     *
     * @author Mario A. De Leon Rdz.
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void validarVistaIdentificaTuTiendaSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloIdentificaTuTienda));

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al validar la vista 'Identifica tu tienda'", e);
        }
    }

    /**
     * Metodo para ingresar un valor en el campo 'CR Plaza' en la vista "Identificacion de Tienda"
     *
     * @author Mario A. De Leon Rdz.
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void ingresarCrPlaza(String crPlaza) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(campoCrPlaza)).sendKeys(crPlaza);

            testCase.addScreenShotCurrentStep(au, "Ingreso de CR Plaza: " + crPlaza);
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al ingresar el valor en el campo 'CR Plaza'", e);
        }
    }

    /**
     * Metodo para ingresar un valor en el campo 'CR Plaza' sin evidencia en la vista "Identificacion de Tienda"
     *
     * @author Mario A. De Leon Rdz.
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void ingresarCrPlazaSinEv(String crPlaza) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(campoCrPlaza)).sendKeys(crPlaza);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al ingresar el valor en el campo 'CR Plaza'", e);
        }
    }

    /**
     * Metodo para ingresar un valor en el campo 'CR Tienda' en la vista "Identificacion de Tienda"
     *
     * @author Mario A. De Leon Rdz.
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void ingresarCrTienda(String crTienda) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(campoCrTienda)).sendKeys(crTienda);

            testCase.addScreenShotCurrentStep(au, "Ingreso de CR Tienda: " + crTienda);
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al ingresar el valor en el campo 'CR Tienda'", e);
        }
    }

    /**
     * Metodo para ingresar un valor en el campo 'CR Tienda' sin evidencia en la vista "Identificacion de Tienda"
     *
     * @author Mario A. De Leon Rdz.
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void ingresarCrTiendaSinEv(String crTienda) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(campoCrTienda)).sendKeys(crTienda);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al ingresar el valor en el campo 'CR Tienda'", e);
        }
    }

    /**
     * Metodo para hacer click en el boton 'Siguiente' en la vista "Identificacion de Tienda"
     *
     * @author Mario A. De Leon Rdz.
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void clickBtnSiguiente() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnSiguiente)).click();

            testCase.addScreenShotCurrentStep(au, "Click en botón Siguiente");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón 'Siguiente'", e);
        }
    }

    /**
     * Metodo para hacer click en el boton 'Siguiente' sin evidencia en la vista "Identificacion de Tienda"
     *
     * @author Mario A. De Leon Rdz.
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void clickBtnSiguienteSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnSiguiente)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en el botón 'Siguiente'", e);
        }
    }
}