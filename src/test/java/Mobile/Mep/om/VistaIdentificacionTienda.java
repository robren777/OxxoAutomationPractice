package Mobile.Mep.om;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.bouncycastle.asn1.dvcs.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class VistaIdentificacionTienda {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    
	/*
	 * Localizadores Login
	 ******************************************************************************************/
	private By tituloIdentificaTuTienda = By.xpath("//android.widget.TextView[@text=\"Identifica tu tienda\"]");
	private By campoCrPlaza = By.xpath("//android.widget.EditText[@text=\"CR Plaza\"]");
	private By campoCrTienda = By.xpath("//android.widget.EditText[@text=\"CR Tienda\"]");
	private By btnSiguiente = By.xpath("//android.widget.TextView[@text=\"Siguiente\"]");
	private final By btnContinuaralIniciodeSesion = AppiumBy.accessibilityId("Continuar al inicio de sesión");
	private By btnPopUp = By.xpath("//android.widget.TextView[@text=\"Continuar al inicio de sesión\"]");

	
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase VistaIdentificacionTienda
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
    public VistaIdentificacionTienda(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    /**
     * Realiza el login completo en la aplicación ingresando CR Plaza y CR Tienda
     * @param crPlaza Código de la plaza a ingresar
     * @param crTienda Código de la tienda a ingresar
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void LoginApp(String crPlaza, String crTienda) throws Exception {
        try {
            /* Paso 1 - Validar que se muestra la vista Identifica tu tienda */
            validarVistaIdentificaTuTienda();

            /* Paso 2 - Ingresar CR Plaza */
            ingresarCrPlaza(crPlaza);

            /* Paso 3 - Ingresar CR Tienda */
            ingresarCrTienda(crTienda);

            /* Paso 4 - Validar si existe el botón Siguiente o aparece pop up de IP no valida */
            if (au.driver.findElements(btnSiguiente).size() > 0) {
                clickBtnSiguiente();
            } else {
                clickContinuaralIniciodeSesion();
            }

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al realizar login en la aplicación", e);
        }
    }
    /**
	 * Hacer click en Continuar al inicio de sesión si aparece pop up de IP no encontrada
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo máximo de espera en segundos
	 * @author Mariana Avitia
	 * @return void
	 * @throws Exception
	 * @date 09/07/2026
	 */
    public void clickContinuaralIniciodeSesion() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnContinuaralIniciodeSesion)).click();

            //testCase.addScreenShotCurrentStep(au, "Click a continuar al inicio de sesión");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic a continuar al inicio de sesión", e);
        }
    }  
    /**
     * Realiza el login completo en la aplicación ingresando CR Plaza y CR Tienda
     * @param crPlaza Código de la plaza a ingresar
     * @param crTienda Código de la tienda a ingresar
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void loginApp(String crPlaza, String crTienda) throws Exception {
    	try {
    		/* Paso 1 - Validar que se muestra la vista Identifica tu tienda */
    		validarVistaIdentificaTuTienda();
    		
    		/* Paso 2 - Ingresar CR Plaza */
    		ingresarCrPlaza(crPlaza);
    		
    		/* Paso 3 - Ingresar CR Tienda */
    		ingresarCrTienda(crTienda);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		throw new Exception("Error al realizar login en la aplicación", e);
    	} 
    }
    /**
	 * Espera a que un elemento se muestre dentro de un periodo de tiempo
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo máximo de espera en segundos
	 * @author Marcos Morales
	 * @return void
	 * @throws Exception
	 * @date 09/10/2024
	 */
    public void waitForVisibility(By locator, int timeout) throws Exception {
    	try {
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(timeout));
    		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		throw new Exception("El elemento no se volvió visible dentro del tiempo esperado", e);
    	}
	}
    
    /**
     * Valida que se muestra la vista 'Identifica tu tienda'
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void validarVistaIdentificaTuTienda() throws Exception {
    	try {
    		/* Paso 1 - Esperar a que el título sea visible */
    		waitForVisibility(tituloIdentificaTuTienda, 15);
    		
    		/* Paso 2 - Validar que el elemento se muestra */
    		au.driver.findElement(tituloIdentificaTuTienda).isDisplayed();
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		throw new Exception("Error al validar la vista Identifica tu tienda", e);
    	}
    }
    
    /**
     * Ingresa un valor dentro del campo 'CR Plaza'
     * @param crPlaza Código de la plaza a ingresar
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void ingresarCrPlaza(String crPlaza) throws Exception {
    	try {
    		/* Paso 1 - Ingresar el CR Plaza en el campo correspondiente */
    		au.driver.findElement(campoCrPlaza).sendKeys(crPlaza);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		throw new Exception("Error al ingresar CR Plaza", e);
    	}
    }
    
    /**
     * Ingresa un valor dentro del campo 'CR Tienda'
     * @param crTienda Código de la tienda a ingresar
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void ingresarCrTienda(String crTienda) throws Exception {
    	try {
    		/* Paso 1 - Ingresar el CR Tienda en el campo correspondiente */
    		au.driver.findElement(campoCrTienda).sendKeys(crTienda);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		throw new Exception("Error al ingresar CR Tienda", e);
    	}
    }
    
    /**
     * Realiza clic en el botón 'Siguiente'
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void clickBtnSiguiente() throws Exception {
    	try {
    		/* Paso 1 - Hacer clic en el botón Siguiente */
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		wait.until(ExpectedConditions.elementToBeClickable(btnSiguiente)).click();
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		throw new Exception("Error al dar clic en el botón Siguiente", e);
    	}
    }
    
    /**
     * Realiza clic en el botón 'Siguiente'
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 12/Septiembre/2025
     */
    public void clickBtnPopup() throws Exception {
    	try {
    		/* Paso 1 - Hacer clic en el botón Siguiente */
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		wait.until(ExpectedConditions.elementToBeClickable(btnPopUp)).click();
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		throw new Exception("Error al dar clic en el botón PopUp", e);
    	}
    }
}