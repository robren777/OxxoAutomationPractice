package Mobile.Mep.EvaluacionMep.om;

import modelo.BaseExecution;
import modelo.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AppiumUtil;

import java.time.Duration;

/**
 * DESCRIPCIÓN: Page Object Model que representa la vista "Resultado Evaluación MEP".
 * Contiene los localizadores y métodos de validación para la pantalla de resultados
 * de la evaluación MEP.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *   - Casos de prueba del módulo Evaluación MEP relacionados con la vista de resultados.
 *
 * PREREQUISITOS:
 *   - El usuario ha completado una evaluación MEP.
 *   - El usuario está en la pantalla "Panel de Control".
 *
 * INSUMOS:
 *   - N/A
 *
 * @author Ivan Yakab
 * @date 03/Noviembre/2025
 */
public class VistaResultadoEvaluacionMep {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    
    
	/*
	 * Localizadores
	 ******************************************************************************************/
    private By titleOneResultadoEvaluacionMep = By.xpath("//android.widget.TextView[@text=\"Resultados\"]");
    
    private By titleTwoResultadoEvaluacionMep = By.xpath("//android.widget.TextView[@text=\"Evaluación MEP\"]");
    

    
    /**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Vista Resultado Evaluacion MEP
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaResultadoEvaluacionMep(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
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
     * Metodo para validar que se muestre la vista "Resultado Evaluación MEP".
     *
     * @author Mario A. De Leon Rdz.
     * @return void
     * @throws Exception
     * @date 03/Noviembre/2025
     */
    public void validarVistaResultadoEvaluacionMep() throws Exception {
        try {
            waitForVisibility(titleOneResultadoEvaluacionMep, 30);
            au.driver.findElement(titleOneResultadoEvaluacionMep).isDisplayed();
            waitForVisibility(titleTwoResultadoEvaluacionMep, 30);
            au.driver.findElement(titleTwoResultadoEvaluacionMep).isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'Resultado Evaluación MEP'", e);
        }
    }
}