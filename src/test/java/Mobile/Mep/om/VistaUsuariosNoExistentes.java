package Mobile.Mep.om;

import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.bouncycastle.asn1.dvcs.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class VistaUsuariosNoExistentes {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
	/*
	 * Localizadores Login
	 ******************************************************************************************/
	private By btnRegistrarUsuarioNuevo = By.xpath("//android.widget.TextView[@text=\"Registrar nuevo usuario\"]");
	private By btnIniciarSesion = By.xpath("//android.widget.TextView[contains(@text,'Iniciar Sesi')]");
	private By btnSiguiente = By.xpath("//android.widget.TextView[@text=\"Siguiente\"]");
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase VistaUsuariosNoExistentes
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaUsuariosNoExistentes(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
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
     * Valida que se muestre la vista "Usuarios no Existentes"
	 * @author Ivan Yakab
	 * @param NA
     * @return true En caso de que si se muestre desplegado
	 * @date 09/10/2024
	 */
    public boolean validarVistaUsuariosNoExistentes()
    {
    	waitForVisibility(btnRegistrarUsuarioNuevo, 15);
		au.driver.findElement(btnRegistrarUsuarioNuevo).isDisplayed();
		return true;
    }
    
    /**
     * Hacer click en el boton Siguiente
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void clickIniciarSesion() throws InterruptedException {
	    waitForVisibility(btnIniciarSesion, 15);
    	au.driver.findElement(btnIniciarSesion).click();
    }

    /**
     * Metodo
     * 
     * Hacer click en el boton Siguiente
     */
    public void clickBtnSiguiente() throws InterruptedException {
	    waitForVisibility(btnSiguiente, 15);
    	au.driver.findElement(btnSiguiente).click();
    }
}
