package WEB.seguridadSADEL.om;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import utils.selenium.SeleniumUtil;
import modelo.TestCase;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class NominaRecibos {
    private final SeleniumUtil u;
    private final TestCase testCase;

    /**
	 * Localizadores
	 ******************************************************************************************/
    private final By botonNomina = By.xpath("//img[contains(@alt,'Nómina')]");
    private final By seleccionarpestañarecibos = By.id("tab3");
    private final By seleccionartienda = By.id("idStore");
    private final By seleccionarperiodo = By.id("idPeriodo");
    private final By botoncancelar = By.id("Image35");
    private final By guardarincidencia = By.id("Image34");
    private final By tablacargada = By.id("idTablaPrincipal");

    public NominaRecibos(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
    }
    
    /**
     * Método para validar el ingreso a la pantalla de Nómina.
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @throws Exception
     */
    public boolean pantallaNomina() {
        boolean validacion = false;
        try {
            Thread.sleep(800);
            u.click(botonNomina);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));
            testCase.addScreenShotCurrentStep(u, "Se despliega pantalla de Nomina");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar a la pantalla Nomina" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para validar el ingreso a la pestaña de Recibos.
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarPestañaRecibos() {
        boolean validacion = false;
        try {
            u.click(seleccionarpestañarecibos);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botoncancelar));
            testCase.addScreenShotCurrentStep(u, "Se despliega pestaña de Recibos");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar la pestaña Recibos " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para ingresar la Tienda y el Periodo.
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @param tienda  Nombre de la tienda a seleccionar
     * @param periodo Periodo a seleccionar
     * @throws Exception
     */
    public boolean seleccionarTiendaYPeriodo(String tienda, String periodo) {
        boolean validacion = false;
        try {
            u.waitWebElement(seleccionartienda, 25, 4);
            u.getDriver().findElement(seleccionartienda).click();
            Thread.sleep(500);
            u.getDriver().findElement(seleccionartienda).sendKeys(tienda);
            Thread.sleep(500);
            u.getDriver().findElement(seleccionartienda).sendKeys(Keys.ENTER);
            Thread.sleep(500);

            WebElement comboperiodo = u.getDriver().findElement(seleccionarperiodo);
            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript(
                "var select = arguments[0]; " +
                "var valorBuscado = arguments[1]; " +
                "var encontrado = false; " +
                "for (var i = 0; i < select.options.length; i++) { " +
                "   if (select.options[i].text.replace(/^\\s+|\\s+$/g, '') === valorBuscado.replace(/^\\s+|\\s+$/g, '')) { " +
                "       select.selectedIndex = i; " +
                "       encontrado = true; " +
                "       break; " +
                "   } " +
                "} " +
                "if (encontrado && select.fireEvent) { " +
                "   select.fireEvent('onchange'); " +
                "}", comboperiodo, periodo
            );

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tablacargada));
            testCase.addScreenShotCurrentStep(u, "La tabla de Recibos cargo correctamente");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar la tienda y el periodo " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para validar que la tabla cargó información.
     *
     * @author Carlos Martínez Ortega
     * @date 29/Mayo/2026
     * @throws Exception
     */
    public boolean validarTablaConInformacion() {
        boolean validacion = false;
        try {
            WebElement elementotablacargada = u.waitWebElement(tablacargada, 60, 5);
            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript("arguments[0].scrollIntoView(true);", elementotablacargada);
            System.out.println("Scroll nativo ejecutado correctamente.");

            List<WebElement> filasDatos = u.getDriver().findElements(
                By.xpath("//div[@id='divDataGrid']//tbody/tr")
            );

            if (filasDatos.isEmpty()) {
                throw new Exception("La tabla no apareció o muestra el mensaje 'No se encontraron resultados'");
            }

            testCase.addScreenShotCurrentStep(u, "La tabla cargó correctamente con información (" + filasDatos.size() + " registros)");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("La tabla no cargo información " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }
}
