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

public class VistaReporteIncidenciasPOP {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    
	/*
	 * Localizadores 
	 ******************************************************************************************/
    private final By opcionPOPDanado = AppiumBy.androidUIAutomator("new UiSelector().text(\"POP Dañado\")");
    private final By opcionReporteManual = AppiumBy.accessibilityId("Reporte Manual");
    private final By opcionZonaDeTienda = AppiumBy.androidUIAutomator("new UiSelector().text(\"Zona de Tienda\")");
    private final By opcionCabeceras = AppiumBy.accessibilityId("Cabeceras");
    private final By opcionEspacioPromocional = AppiumBy.androidUIAutomator("new UiSelector().text(\"Espacio Promocional\")");
    private final By opcionCabecera2 = AppiumBy.androidUIAutomator("new UiSelector().text(\"Cabecera 2\")");
    private final By opcionSenalizacion = AppiumBy.androidUIAutomator("new UiSelector().text(\"Señalización\")");
    private final By opcionTestPOPCabecera2 = AppiumBy.androidUIAutomator("new UiSelector().text(\"Test POP Cabecera 2\")");
    
    private final By btnContinuar = AppiumBy.accessibilityId("Continuar");
    private final By iconoSVG = AppiumBy.androidUIAutomator("new UiSelector().className(\"com.horcrux.svg.k0\").instance(1)");
    private final By opcionSeleccionaMotivo = AppiumBy.androidUIAutomator("new UiSelector().text(\"Selecciona un motivo\")");
    private final By opcionDanoFisico = AppiumBy.androidUIAutomator("new UiSelector().text(\"Daño físico\")");
    private final By btnFinalizar = AppiumBy.accessibilityId("Finalizar");
    private final By contenedorBoton = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"button-container\")");
    private final By iconButton1 = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"icon-button\").instance(0)");
    
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase VistaReporteIncidenciasPOP
	 * @author Mario A. De León. Rdz.
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 * @param baseExecution (BaseExecution): objeto de la clase
	 */
    public VistaReporteIncidenciasPOP(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    /**
     * Reporta una incidencia de un POP
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 09/Febrero/2026
     */
    public void reportarIncidenciaPOPSinEv() throws Exception {
    	try {
    		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(120));
    		
    		//Hacer clic en POP Dañado
    		wait.until(ExpectedConditions.elementToBeClickable(opcionPOPDanado)).click();
    		
    		//Hacer clic en Reporte Manual
    		wait.until(ExpectedConditions.elementToBeClickable(opcionReporteManual)).click();
    		
    		//Hacer clic en Zona de Tienda
    		wait.until(ExpectedConditions.elementToBeClickable(opcionZonaDeTienda)).click();
    		
    		//Hacer clic en Cabeceras
    		wait.until(ExpectedConditions.elementToBeClickable(opcionCabeceras)).click();
    		
    		//Hacer clic en Espacio Promocional
    		wait.until(ExpectedConditions.elementToBeClickable(opcionEspacioPromocional)).click();
    		
    		//Hacer clic en ViewGroup instance 29
    		wait.until(ExpectedConditions.elementToBeClickable(opcionCabecera2)).click();
    		
    		//Hacer clic en Señalización
    		wait.until(ExpectedConditions.elementToBeClickable(opcionSenalizacion)).click();
    		
    		//Hacer clic en ViewGroup instance 29
    		wait.until(ExpectedConditions.elementToBeClickable(opcionTestPOPCabecera2)).click();
    		
    		//Hacer clic en Continuar
    		wait.until(ExpectedConditions.elementToBeClickable(btnContinuar)).click();
    		
    		//Hacer clic en icono SVG
    		wait.until(ExpectedConditions.elementToBeClickable(iconoSVG)).click();
    		
    		//Hacer clic en Continuar
    		wait.until(ExpectedConditions.elementToBeClickable(btnContinuar)).click();
    		
    		//Hacer clic en Selecciona un motivo
    		wait.until(ExpectedConditions.elementToBeClickable(opcionSeleccionaMotivo)).click();
    		
    		//Hacer clic en Daño físico
    		wait.until(ExpectedConditions.elementToBeClickable(opcionDanoFisico)).click();
    		
    		//Hacer clic en Finalizar
    		wait.until(ExpectedConditions.elementToBeClickable(btnFinalizar)).click();
    		
    		//Hacer clic en contenedor de botón
    		wait.until(ExpectedConditions.elementToBeClickable(contenedorBoton)).click();
    		
    		//Hacer clic en icon-button primera instancia
    		wait.until(ExpectedConditions.elementToBeClickable(iconButton1)).click();
    		
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new Exception("Error al reportar incidencia de POP", e);
    	}
    }
}