package WEB.seguridadSADEL.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.selenium.SeleniumUtil;
import modelo.TestCase;

public class PortalAplicacionesMainPage {
    private final SeleniumUtil u;
    private final TestCase testCase;

    private final By sadelNominaOption = By.linkText("SADEL NOMINA COMISIONISTA");

    /**
     * Constructor de la clase PortalAplicacionesMainPage.
     * Inicializa las utilidades de Selenium y el caso de prueba asociado.
     * @author Mario A. De León Rdz.
     * @param u Instancia de SeleniumUtil para la interacción con el navegador.
     * @param testCase Instancia de TestCase para el registro de pasos y screenshots.
     * @date 10/Junio/2026
     */
    public PortalAplicacionesMainPage(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
    }

    /**
     * Selecciona la opción de SADEL Nómina en el portal de aplicaciones.
     * Realiza clic en la opción correspondiente y captura screenshot del paso.
     * @author Mario A. De León Rdz.
     * @return 
     * @date 10/Junio/2026
     */
    public boolean seleccionarSadelNomina() throws Exception {
    	boolean validacion = false;
        try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(sadelNominaOption)).click();
			u.refresh();
            testCase.addScreenShotCurrentStep(u, "pantalla_sadel");
            validacion = true;
        } catch (Exception e) {
            throw new Exception("Error al seleccionar SADEL Nómina en línea " 
                    + e.getStackTrace()[0].getLineNumber() 
                    + " -> " + e.getClass().getSimpleName() 
                    + ": " + e.getMessage());
        }
        return validacion;
    }

}
