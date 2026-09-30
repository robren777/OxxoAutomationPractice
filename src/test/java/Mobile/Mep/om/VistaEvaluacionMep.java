
package Mobile.Mep.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class VistaEvaluacionMep {
    /*
     * Variables Globales
     *  ******************************************************************************************/
    private TestCase testCase;
    AppiumUtil au = null;
    private BaseExecution baseExecution;
    
    /*
	 * Localizadores Panel de Control
	 ******************************************************************************************/
    
    /***Gondolas***/
    private By btnTomarFotoGondolas = By.xpath("//android.widget.TextView[@text='Góndolas']/preceding-sibling::android.view.ViewGroup[.//android.widget.TextView[contains(@text,'Tomar Foto')]]");
    private By btnCamaraAntesDeUnaFoto = By.xpath("(//android.view.ViewGroup)[last()]");
    private By btnCamara = By.xpath("(//android.view.ViewGroup)[last()-1]");
    private By btnGaleria = By.xpath("//android.widget.ImageView");
    private By btnFlechaDerecha = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[3]");
    
 
    /**Cabecera**/
    private By btnFinalizar = By.xpath("//android.widget.TextView[@text=\"Finalizar\"]");
    private By btnCerrar = By.xpath("//android.widget.TextView[@text=\"Cerrar\"]");
    private By btnGuardarYContinuar = By.xpath("//android.widget.TextView[@text=\"Guardar y continuar\"]");
    private By btnContinuar = By.xpath("//android.widget.TextView[@text=\"Continuar\"]");
    
    /**cabecera galeria**/
    private By titCabeceraGaleria = By.xpath("//android.widget.TextView[@text=\"12 fotos:\"]");
    private By btnFirstRetekPhoto = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[3]");
    
    /**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Panel De Control 
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaEvaluacionMep(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    /**
     * Realiza clic en el boton Tomar Fotos de la seccion Gondolas
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnTomarFotoGondolas() throws Exception {
    	try {
    		//Hacer clic en Reporte de Incidencias POP
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		au.scrollToElement(btnTomarFotoGondolas); 
    		wait.until(ExpectedConditions.elementToBeClickable(btnTomarFotoGondolas)).click();
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Error al intentar hacer click en el boton Tomar Fotos de la seccion Gondolas", e);
    	}
    }
    
    public Boolean validateCamaraBeforeIsDisplayed() {
	try {
	    return (au.driver.findElement(btnCamaraAntesDeUnaFoto).isDisplayed());
	} catch (Exception ex) {
	    throw new RuntimeException("El elemento btnCamaraAntesDeUnaFoto no se volvió visible: ", ex);
	}
    }
    /**
     * Realiza clic en el boton CamaraBefore
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnCamaraBefore() throws Exception {
    	try {
    		//Hacer clic en Reporte de Incidencias POP
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		wait.until(ExpectedConditions.elementToBeClickable(btnCamaraAntesDeUnaFoto)).click();
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Error al intentar hacer click en el boton obturador de Fotos de la seccion Gondolas", e);
    	}
    }
    public Boolean validateCamaraAfterIsDisplayed() {
	try {
	    return (au.driver.findElement(btnCamara).isDisplayed());
	} catch (Exception ex) {
	    throw new RuntimeException("El elemento btnCamaraAntesDeUnaFoto no se volvió visible: ", ex);
	}
    }
    public Boolean validateGalleryIsDisplayed() {
	try {
	    return (au.driver.findElement(btnGaleria).isDisplayed());
	} catch (Exception ex) {
	    throw new RuntimeException("El elemento btnCamaraAntesDeUnaFoto no se volvió visible: ", ex);
	}
    }
    public Boolean validateRigthArrowIsDisplayed() {
	try {
	    return (au.driver.findElement(btnFlechaDerecha).isDisplayed());
	} catch (Exception ex) {
	    throw new RuntimeException("El elemento btnCamaraAntesDeUnaFoto no se volvió visible: ", ex);
	}
    }
    public Boolean validateBtnContinuarIsDisplayed() {
	try {
	    return (au.driver.findElements(btnContinuar).size()>0);
	} catch (Exception ex) {
	    throw new RuntimeException("El elemento btnContinuar no se volvió visible: ", ex);
	}
    }
    /**
     * Realiza clic en el boton Continuar
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnContinuar() throws Exception {
    	try {
    		//Hacer clic en Reporte de Incidencias POP
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		wait.until(ExpectedConditions.elementToBeClickable(btnContinuar)).click();
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Error al intentar hacer click en continuar", e);
    	}
    }
    /**
     * Realiza clic en el boton Camara
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnCamara() throws Exception {
    	try {
    		//Hacer clic en Reporte de Incidencias POP
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		wait.until(ExpectedConditions.elementToBeClickable(btnCamara)).click();
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Error al intentar hacer click en el boton obturador de Fotos", e);
    	}
    }
    /**
     * Realiza clic en el boton btnGuardarYContinuar
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnGuardarYContinuar() throws Exception {
    	try {
    		//Hacer clic en Reporte de Incidencias POP
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		wait.until(ExpectedConditions.elementToBeClickable(btnGuardarYContinuar)).click();
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Error al intentar hacer click en el boton Guardar Y Continuar", e);
    	}
    }
    /**
     * Realiza clic en el boton btnCerrar
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnCerrar() throws Exception {
    	try {
    		//Hacer clic en Reporte de Incidencias POP
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		wait.until(ExpectedConditions.elementToBeClickable(btnCerrar)).click();
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Error al intentar hacer click en el btnCerrar", e);
    	}
    }
    /**
     * Realiza clic en el boton btnFinalizar
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickBtnFinalizar() throws Exception {
    	try {
    		//Hacer clic en Reporte de Incidencias POP
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		wait.until(ExpectedConditions.elementToBeClickable(btnFinalizar)).click();
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Error al intentar hacer click en el btnFinalizar", e);
    	}
    }
    
}
