package Mobile.Mep.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class VistaChromeAppCache {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
	/*
	 * Localizadores 
	 ******************************************************************************************/
	private By btnContinuarSinCuenta = By.xpath("//android.widget.Button[@resource-id=\"com.android.chrome:id/signin_fre_dismiss_button\"]");
    private By btnContinuarHH = By.xpath("//android.widget.Button[@resource-id=\"com.android.chrome:id/signin_fre_continue_button\"]");
	private By btnGotItPrivacy = By.id("com.android.chrome:id/ack_button");
    
    
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase VistaChromeAppCache
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaChromeAppCache(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
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
     * Hacer click en el boton Continuar sin cuenta en Dispositivo Emulado
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void clickBtnContinuarSinCuenta() throws InterruptedException {
	    waitForVisibility(btnContinuarHH, 15);
    	au.driver.findElement(btnContinuarSinCuenta).click();
    }
    
    /**
     * Hacer click en el boton Continuar en dispositivo HH Fisica
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void clickBtnContinuar() throws InterruptedException {
		au.driver.findElement(btnContinuarHH).isDisplayed();
    	au.driver.findElement(btnContinuarHH).click();
    }
    
    /**
     * Hacer click en el boton Got It para aceptar Politicas Privacidad Google
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void clickBtnGotItPrivacy() throws InterruptedException {
	    waitForVisibility(btnGotItPrivacy, 15);
    	au.driver.findElement(btnGotItPrivacy).click();
    }
}
