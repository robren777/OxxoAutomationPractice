package EBS.updateNomina.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PolizasSOL {
    //Elementos
    By aPestanaReportes = By.xpath("//*[@id=\"e0_0o\"]");
    By aSubmenuOracleGL = By.xpath("//*[@id=\"e0_1o\"]");



    private final SeleniumUtil u;
    private final WebDriverWait wait;
    private final TestCase testCase;


    /**
     * Constructor.
     *
     * @param u
     * @param testCase
     * @author Mario A. De Leon Rdz.
     */
    public PolizasSOL(SeleniumUtil u, TestCase testCase) {
        super(); // Llama al constructor de la clase padre (en este caso, Object)
        this.u = u; // Asigna el objeto SeleniumUtil pasado como par�metro a la variable de instancia
        this.wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60)); // Inicializa WebDriverWait con el driver de SeleniumUtil y un timeout de 60 segundos
        this.testCase = testCase; // Asigna el objeto TestCase pasado como par�metro a la variable de instancia
    }

    //Metodos para ingresar al portal de aplicaciones
    public void IngresarPolizasEnviadasAOracleGL() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(aPestanaReportes));
            u.click(aPestanaReportes);
            testCase.addScreenShotCurrentStep(u, "Submenu Polizas Enviadas a Oracle GL");
            u.click(aSubmenuOracleGL);
        } catch (Exception e) {
            System.out.println(" -> " + e);
            this.u.close();
            throw new AssertionError("Error en Polizas SOL: no fue posible hacer click en submen� Polizas Enviadas a Oracle GL");
        }
    }

    //Metodos para casos de prueba de aplicativo SADEL
    public void SeleccionarBusquedaPolizasEnviadasAOracleGL() {
        try {

        } catch (Exception e) {
            System.out.println(" -> " + e);
            this.u.close();
            throw new AssertionError("No se pudo dar click en enlace para ingresar a SADEL NOMINA COMISIONISTA");
        }
    }
    
    
    
    


} 