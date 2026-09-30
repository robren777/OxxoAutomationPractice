package Mobile.Mep.ExhibidoresNoAutorizados.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class VistaTodosLosReportes {
	
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    
	/*
	 * Localizadores 
	 ******************************************************************************************/
	private By titleTodosLosReportes = By.xpath("//android.widget.TextView[@text=\"Todos los reportes\"]");
    private By btnFlechaAtras = By.xpath("//android.view.ViewGroup[@resource-id=\"icon-button\"]");
    private By textoHistorialDeReportes = By.xpath("//android.widget.TextView[@text=\"Historial de reportes:\"]");
    
    private By mensajeNoHayMaterialesReportadosPartOne = By.xpath("//android.widget.TextView[@text=\"No hay materiales reportados\"]");
    private By mensajeNoHayMaterialesReportadosPartTwo = By.xpath("//android.widget.TextView[@text=\"Crea un reporte para verlo en esta lista\"]");
     
    
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase VistaTodosLosReportes
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaTodosLosReportes(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
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
    public void waitForVisibility(By locator, int timeout)
    {
   		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(timeout));
   		try {
   			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
   		} catch (Exception ex) {
   			throw new RuntimeException("El elemento no se volvió visible dentro del tiempo esperado: ", ex);
   		}
    }

    /**
     * Metodo para Validar vista de la opcion 'Todos los reportes'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarVistaTodosLosReportes() throws InterruptedException {
	    waitForVisibility(titleTodosLosReportes, 15);
	    au.driver.findElement(titleTodosLosReportes).isDisplayed();	    	
    }
    

    /**
     * Metodo para hacer click en el icono 'Flecha' 'atras' dentro de la vista 'Todos los reportes'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickFlechaAtras()
    {
    	waitForVisibility(btnFlechaAtras, 15);
    	au.driver.findElement(btnFlechaAtras).click();
    }
    
    /**
     * Metodo para validar que se muestre el mensaje de no hay materiales reportados
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarMensajeNoHayMaterialesReportados()
    {
    	waitForVisibility(mensajeNoHayMaterialesReportadosPartOne, 15);
    	au.driver.findElement(mensajeNoHayMaterialesReportadosPartOne).isDisplayed();
    	au.driver.findElement(mensajeNoHayMaterialesReportadosPartTwo).isDisplayed();
    }
    
    /**
     * Metodo para validar un dato en base a parametro
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarDato(String texto, String indice)
    {
    	au.driver.findElement(textoHistorialDeReportes).isDisplayed();
    	retornarElementoProveedorODescrip(texto, indice).isDisplayed();
    }
    
    /**
     * Metodo que retorna el Elemento que hace referencia al Proveedor o a la Descripcion
     * @param texto Es el texto que queramos validar
     * @param indice Es el indice que selecciona el reporte
     * @return WebElement el elemento que queremos buscar
     */
    public WebElement retornarElementoProveedorODescrip(String texto, String indice)
    {
    	return au.driver.findElement(By.xpath("(//android.widget.TextView[contains(@text,'" + texto + "')])[" + indice + "][1]"));
    }
    
}
