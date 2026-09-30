package Mobile.Mep.ExhibidoresNoAutorizados.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class VistaReporteExhNoAutorizados {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    
    
	/*
	 * Localizadores 
	 ******************************************************************************************/
    
    private By btnFlechaAtras = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[1]");
    private By opcionExhibidoresNoAutorizados = By.xpath("//android.widget.TextView[@text=\"Exhibidores no autorizados\"]");
    private By btnModalReporteExhNoAutorizados = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[2]"); 
    
    private By btnVerTodosLosReportes = By.xpath("//android.widget.TextView[@text=\"Ver todos los reportes\"]");
    
    
    private By modalTitle = By.xpath("//android.widget.TextView[@text=\"Guía para reportar exhibidores no autorizados\"]");
    private By modalText = By.xpath("//android.widget.TextView[@text=\"Usa esta opción para reportar de forma inmediata cualquier exhibidor que no esté autorizado en tu tienda.\"]");
    private By modalHowToReport = By.xpath("//android.widget.TextView[@text=\"¿Cómo reportar?\"]");

    private By modalTextPointOne = By.xpath("//android.widget.TextView[@text=\"1. Detecta el exhibidor no autorizado.\"]");
    private By modalTextPointTwo = By.xpath("//android.widget.TextView[@text=\"2. Toma una foto clara del exhibidor.\"]");
    private By modalTextPointThree = By.xpath("//android.widget.TextView[@text=\"3. Completa los siguientes campos:\"]");
    
    private By modalA = By.xpath("//android.widget.TextView[@text=\"a. Tipo de exhibición\"]");
    private By modalB = By.xpath("//android.widget.TextView[@text=\"b. Proveedor\"]");
    private By modalC = By.xpath("//android.widget.TextView[@text=\"c. Cantidad de exhibiciones\"]");

    
    private By modalTextPointFour = By.xpath("//android.widget.TextView[@text=\"4. Pulsa Finalizar para enviar el reporte al equipo responsable.\"]");
    

    private By btnCerrarModal = By.xpath("//android.view.ViewGroup[@content-desc=\"Cerrar\"]");
    
    
    /**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Vista Reporte de Exhibidores no autorizados y otros
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaReporteExhNoAutorizados(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
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
    * Metodo para Validar vista de la opcion Reporte de Exhibidores no autorizados
    * @author Ivan Yakab
    * @date 12/Septiembre/2025
    */
    public void validarVistaReporteExhNoAutorizados() throws InterruptedException {
    	waitForVisibility(opcionExhibidoresNoAutorizados, 15);
	    au.driver.findElement(opcionExhibidoresNoAutorizados).isDisplayed();
    }
    
    /**
     * Metodo para hacer click en el icono 'Flecha' 'atras' dentro de la vista 'Reporte de Exhibidores no autorizados'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickFlechaAtras()
    {
    	waitForVisibility(btnFlechaAtras, 15);
    	au.driver.findElement(btnFlechaAtras).click();
    }
    
    /**
     * Metodo para hacer click en la opcion 'Exhibidores no autorizados' dentro de la vista 'Exhibidores no autorizados y otros'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickOpcionExhibidoresNoAutorizados()
    {
    	waitForVisibility(opcionExhibidoresNoAutorizados, 15);
    	au.driver.findElement(opcionExhibidoresNoAutorizados).click();
    }

    /**
     * Metodo para hacer click en el modal dentro de la vista 'Reporte de Exhibidores no autorizados'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickModalReporteExhNoAutorizados()
    {
    	waitForVisibility(btnModalReporteExhNoAutorizados, 15);
    	au.driver.findElement(btnModalReporteExhNoAutorizados).isDisplayed();
    	au.driver.findElement(btnModalReporteExhNoAutorizados).click();
    }

    /**
     * Metodo para validar el texto dentro del Modal 'Guia para reportar Exhibidores no autorizados' dentro de la vista 'Reporte de exhibidores no autorizados'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarTextoModalReporteExhNoAutorizados()
    {
    	waitForVisibility(modalTitle, 15);
    	au.driver.findElement(modalTitle).isDisplayed();
    	
    	au.driver.findElement(modalText).isDisplayed();
    	au.driver.findElement(modalHowToReport).isDisplayed();
    	au.driver.findElement(modalTextPointOne).isDisplayed();
    	au.driver.findElement(modalTextPointTwo).isDisplayed();
    	au.driver.findElement(modalTextPointThree).isDisplayed();
    	
    	au.driver.findElement(modalA).isDisplayed();
    	au.driver.findElement(modalB).isDisplayed();
    	au.driver.findElement(modalC).isDisplayed();

    	au.driver.findElement(modalTextPointFour).isDisplayed();
    	
    	testCase.addScreenShotCurrentStep(au, "testImage");
    }

    /**
     * Metodo para hacer click en el boton 'Cerrar' 
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickBtnCerrarModal()
    {
    	waitForVisibility(btnCerrarModal, 15);
    	au.driver.findElement(btnCerrarModal).isDisplayed();
    	au.driver.findElement(btnCerrarModal).click();
    }
    
    /**
     * Metodo para hacer click en el boton 'Ver todos los reportes'  dentro de la vista "Exhibidores no autorizados y otros"
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickBtnVerTodosLosReportes()
    {
    	waitForVisibility(btnVerTodosLosReportes, 15);
    	au.driver.findElement(btnVerTodosLosReportes).isDisplayed();
    	au.driver.findElement(btnVerTodosLosReportes).click();
    }
}

