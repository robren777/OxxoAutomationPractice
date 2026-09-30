package Mobile.Mep.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class VistaPanelDeControl {
    /*
     * Variables Globales
     *  ******************************************************************************************/
    private TestCase testCase;
    AppiumUtil au = null;
    private BaseExecution baseExecution;
    
    /*
	 * Localizadores Panel de Control
	 ******************************************************************************************/
    private By opcionReporteExhNoAutorizados = By.xpath("//android.widget.TextView[@text=\"Reporte de exhibidores no autorizados\"]");
    private By opcionEvaluacionMep = By.xpath("//android.widget.TextView[@text=\"Evaluación MEP\"]");
    private By opcionGuiasDeColocacionPop = By.xpath("//android.widget.TextView[contains(@text,\"Colocaci\")]");
    private final By opcionReporteIncidenciasPOP = AppiumBy.accessibilityId("Reporte de Incidencias POP");
    private final By opcionPromOXXODeImagenes = AppiumBy.androidUIAutomator("new UiSelector().text(\"PromOXXO de Imágenes\")");
    private final By insigniaNuevoPromoxxoImagenes = AppiumBy.androidUIAutomator("new UiSelector().text(\"¡Nuevo!\")");
    private By evaluacionMepQueQuieresHacerHoyQuestion = By.xpath("(//android.view.ViewGroup[@content-desc=\"Evaluación MEP\"])[1]");
    private By guiasColocacionPopQueQuieresHacerHoyQuestion = By.xpath("//android.view.ViewGroup[@content-desc=\"Guías de colocación POP\"]");
    private By opcionCajaPromPackingList = By.xpath("//android.widget.TextView[@text=\"Caja PromOXXO - Packing List\"]");
    private By opcionResultadoEvaluacion = By.xpath("//android.widget.TextView[@text=\"Resultado Evaluación MEP\"]");
    

    private By titleOtrasFunciones = By.xpath("//android.widget.TextView[@text=\"Otras funciones\"]");
    //Localizadores modal PromOxxo Imagenes
    private By btnCerrarModalPromOxxoImagenes = AppiumBy.accessibilityId("Cerrar");
    
    /**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Panel De Control 
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaPanelDeControl(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
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
     * Metodo para hacer click en la opcion 'Reporte de Exhibidores no autorizados' dentro del apartado PromOxxo
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void clickOpcionReporteExhNoAutorizados() throws InterruptedException
    {
    	waitForVisibility(titleOtrasFunciones, 30);
    	au.driver.findElement(titleOtrasFunciones).isDisplayed();
	    au.scrollToElement(opcionReporteExhNoAutorizados);	    
    	au.driver.findElement(opcionReporteExhNoAutorizados).click();
    }
    

    /**
     * Metodo para hacer click en la opcion 'Evaluacion MEP' dentro del apartado PromOxxo
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void clickOpcionEvaluacionMep() throws InterruptedException
    {    	
	au.scrollToElement(opcionEvaluacionMep);
    	waitForVisibility(opcionEvaluacionMep, 15);
	au.scrollToElement(opcionEvaluacionMep);	    
    	au.driver.findElement(opcionEvaluacionMep).click();
    }
    
    /**
     * Metodo para hacer click en la opcion 'Guias de Colocacion POP' dentro del apartado PromOxxo
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void clickOpcionGuiasColocacionPop() throws InterruptedException
    {    	
	    au.scrollToElement(opcionGuiasDeColocacionPop);
	    au.driver.findElement(opcionGuiasDeColocacionPop).click();
    }
    
    /**
     * Metodo para validar que se muestre la vista Principal/Home de la aplicacion AlDia Promoxxo
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void validarVistaPrincipal()
    {
    	au.driver.findElement(titleOtrasFunciones).isDisplayed();
    }
    
    /**
     * Metodo para hacer validar que se encuentre disponible la opcion 'Exhibidores no autorizados y otros' dentro del apartado PromOxxo
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void validarOpcionPromOxxoReporteExhNoAutorizados()
    {
	    au.scrollToElement(opcionReporteExhNoAutorizados);
	    waitForVisibility(opcionReporteExhNoAutorizados, 15);
    	au.driver.findElement(opcionReporteExhNoAutorizados).isDisplayed();
    }
    
    /**
     * Metodo para hacer validar que se encuentre disponible la opcion 'Evaluacion MEP'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void validarOpcionPromOxxoEvaluacionMep()
    {
	    au.scrollToElement(opcionEvaluacionMep);
    	waitForVisibility(opcionEvaluacionMep, 30);
    	au.driver.findElement(opcionEvaluacionMep).isDisplayed();
    }
    
    /**
     * Metodo para hacer validar que se encuentre disponible la opcion 'Guias de Colocacion POP'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void validarOpcionPromOxxoGuiasColocacionPop()
    {
	    au.scrollToElement(opcionGuiasDeColocacionPop);
    	au.driver.findElement(opcionGuiasDeColocacionPop).isDisplayed();
    }
    

    /**
     * Metodo para hacer validar que se encuentre disponible la opcion 'Evaluacion MEP' dentro de la seccion ¿Que quieres hacer hoy?
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void validarOpcionEvaluacionMepQueQuieresHacerHoyQuestion()
    {
    	waitForVisibility(evaluacionMepQueQuieresHacerHoyQuestion, 30);
	    au.scrollToElement(evaluacionMepQueQuieresHacerHoyQuestion);
    	au.driver.findElement(evaluacionMepQueQuieresHacerHoyQuestion).isDisplayed();
    }
    
    /**
     * Metodo para hacer validar que se encuentre disponible la opcion 'Guias de Colocacion POP' dentro de la seccion ¿Que quieres hacer hoy?
	 * @author Ivan Yakab
	 * @param NA
	 * @date 09/10/2024
	 */
    public void validarOpcionGuiasColocacionPopQueQuieresHacerHoyQuestion()
    {
    	au.driver.findElement(guiasColocacionPopQueQuieresHacerHoyQuestion).isDisplayed();
    }
    
	/**
	 * Cierra el modal de promociones Oxxo Imágenes si está presente
	 * 
	 * @Autor Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 09/Febrero/2026
	 */
    public void validarBtnCerrarModalPromOxxoImagenes() throws Exception {
    	try {
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		wait.until(ExpectedConditions.visibilityOfElementLocated(btnCerrarModalPromOxxoImagenes));
 	        testCase.addScreenShotCurrentStep(au, "");
 	        testCase.addTextEvidenceCurrentStep("Se muestra  la ventana emergente \"Nuevo Periodo Promocional Disponible\", "
 	        		+ "la cual tiene: - Imagen - Título \"Nuevo Periodo Promocional Disponible\" - Texto - Botón \"Cerrar\" - "
 	        		+ "Botón \"Ir a PromOXXO\"");
    	} catch (Exception e) {
    		e.printStackTrace();
 	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
    	}
    }
	/**
	 * Valida que no se muestre el modal de promociones Oxxo Imágenes si está presente
	 * 
	 * @Autor Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 09/Febrero/2026
	 */
    public void validarQueNoSeMuestraModalPromOxxoImagenes() throws Exception {
    	try {
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(5));
    		wait.until(ExpectedConditions.invisibilityOfElementLocated(btnCerrarModalPromOxxoImagenes));
 	        testCase.addScreenShotCurrentStep(au, "");
 	        testCase.addTextEvidenceCurrentStep("NO se muestra  la ventana emergente \"Nuevo Periodo Promocional Disponible\", "
 	        		+ "la cual tiene: - Imagen - Título \"Nuevo Periodo Promocional Disponible\" - Texto - Botón \"Cerrar\" - "
 	        		+ "Botón \"Ir a PromOXXO\"");
    	} catch (Exception e) {
    		e.printStackTrace();
 	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
    	}
    }
    /***
     * Metodo para hacer click en la opcion 'Evaluacion MEP' dentro del apartado PromOxxo
	 * @Autor Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 23/Diciembre/2025
     */
    public void validarOpcionPromOxxoImagenes() throws Exception {
    	 try {
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		au.scrollToElement(opcionPromOXXODeImagenes);
    		wait.until(ExpectedConditions.visibilityOfElementLocated(opcionPromOXXODeImagenes));

 	        testCase.addScreenShotCurrentStep(au, "");
 	        testCase.addTextEvidenceCurrentStep("Se muestra la opción PromOXXO de Imagenes");
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
 	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
 	    }
    }
    /***
     * Metodo para hacer click en la opcion 'Evaluacion MEP' dentro del apartado PromOxxo
	 * @Autor Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 23/Diciembre/2025
     */
    public void validarInsigniaNuevoOpcionPromOxxoImagenes() throws Exception {
    	 try {
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		au.scrollToElement(opcionPromOXXODeImagenes);
    		wait.until(ExpectedConditions.visibilityOfElementLocated(insigniaNuevoPromoxxoImagenes));

 	        testCase.addScreenShotCurrentStep(au, "");
 	        testCase.addTextEvidenceCurrentStep("Se muestra insignia ¡Nuevo! de la opción PromOXXO de Imagenes");
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
 	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
 	    }
    }
    
    /**
     * Metodo para hacer click en la opcion 'Evaluacion MEP' dentro del apartado PromOxxo
	 * @Autor Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 23/Diciembre/2025
     */
    public void clickOpcionPromOxxoImagenes() throws Exception {
    	 try {
 	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
 	        au.scrollToElement(opcionPromOXXODeImagenes);
 	        wait.until(ExpectedConditions.elementToBeClickable(opcionPromOXXODeImagenes)).click();

 	        testCase.addScreenShotCurrentStep(au, "");
 	        testCase.addTextEvidenceCurrentStep("Se realiza clic a opción PromOXXO de Imagenes");
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
 	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
 	    }
    }
    
    /**
     * Metodo para hacer click en la opcion 'Evaluacion MEP' dentro del apartado PromOxxo
	 * @Autor Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 23/Diciembre/2025
     */
    public void clickOpcionPromOxxoImagenesSinEv() throws Exception {
    	 try {
 	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
 	        au.scrollToElement(opcionPromOXXODeImagenes);
 	        wait.until(ExpectedConditions.elementToBeClickable(opcionPromOXXODeImagenes)).click();

 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
 	    }
    }

	/**
	 * Cierra el modal de promociones Oxxo Imágenes si está presente
	 * 
	 * @Autor Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 09/Febrero/2026
	 */
    public void clickBtnCerrarModalPromOxxoImagenes() throws Exception {
    	try {
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		wait.until(ExpectedConditions.elementToBeClickable(btnCerrarModalPromOxxoImagenes)).click();
    		testCase.addScreenShotCurrentStep(au, "");
    	} catch (Exception e) {
    		e.printStackTrace();
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
    	}
    }
	/**
	 * Cierra el modal de promociones Oxxo Imágenes si está presente
	 * 
	 * @Autor Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 09/Febrero/2026
	 */
    public void clickBtnCerrarModalPromOxxoImagenesSinEv() throws Exception {
    	try {
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		wait.until(ExpectedConditions.elementToBeClickable(btnCerrarModalPromOxxoImagenes)).click();
    	} catch (Exception e) {
    		e.printStackTrace();
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
    	}
    }
    
    /**
     * Cierra el modal de promociones Oxxo Imágenes si está presente,
     * sin lanzar excepción si el elemento no existe o no es clickeable.
     *
     * @Autor Mario A. De León Rdz.
     * @return void
     * @date 09/Febrero/2026
     */
    public void clickBtnCerrarModalPromOxxoImagenesSinEvOpc() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(5)); // reducir timeout si es opcional
            wait.until(ExpectedConditions.elementToBeClickable(btnCerrarModalPromOxxoImagenes)).click();
            System.out.println("Modal encontrado, se cerró correctamente.");
        } catch (Exception e) {
            System.out.println("No se mostró el Modal de Promociones Oxxo Imágenes.");
        }
    }
    
    /**
     * Realiza clic en Reporte de Incidencias POP y selecciona el elemento ViewGroup
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 09/Febrero/2026
     */
    public void clickOpcionReporteIncidenciasPOPSinEv() throws Exception {
    	try {
    		//Hacer clic en Reporte de Incidencias POP
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		au.scrollToElement(opcionReporteIncidenciasPOP);	 
    		wait.until(ExpectedConditions.elementToBeClickable(opcionReporteIncidenciasPOP)).click();
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Error al seleccionar Reporte de Incidencias POP", e);
    	}
    }
    
    /***
     * Metodo para validar si se despliega la opcion CajaPromPackingList
	 * @Autor gtim
	 * @return void
	 * @throws Exception 
	 * @date 09/07/2026
     */
    public Boolean validarOpcionCajaPromPackingList() throws Exception {
    	 try {
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		au.scrollToElement(opcionCajaPromPackingList);
    		wait.until(ExpectedConditions.visibilityOfElementLocated(opcionCajaPromPackingList));
 	        testCase.addScreenShotCurrentStep(au, "");
 	        testCase.addTextEvidenceCurrentStep("Se muestra la opción Caja PromOXXO - Packing List");
 	        return true;
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
 	        throw new Exception("Error al visualiza la opcion Caja PromOXXO - Packing List", e);
 	    }
    }
    /**
     * Realiza clic en la opcion CajaPromPackingList
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickOpcionCajaPromPackingList() throws Exception {
    	try {
    		//Hacer clic en Reporte de Incidencias POP
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		au.scrollToElement(opcionCajaPromPackingList); 
    		wait.until(ExpectedConditions.elementToBeClickable(opcionCajaPromPackingList)).click();
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Error al intentar hacer click en la opcion Caja PromOXXO - Packing List", e);
    	}
    }
    /**
     * Realiza clic en la opcion opcionResultadoEvaluacion
     * @author gtim
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickOpcionResultadoEvaluacionMep() throws Exception {
    	try {
    		//Hacer clic en Reporte de Incidencias POP
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
    		au.scrollToElement(opcionResultadoEvaluacion); 
    		wait.until(ExpectedConditions.elementToBeClickable(opcionResultadoEvaluacion)).click();
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Error al intentar hacer click en la opcion Resultado Evaluacion MEP", e);
    	}
    }
}
