package WEB.seguridadSADEL.om;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import utils.selenium.SeleniumUtil;
import modelo.TestCase;
import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EnvioTimbrado {
    private final SeleniumUtil u;
    private final TestCase testCase;

    /**
	 * Localizadores
	 ******************************************************************************************/
    private final By botonNomina = By.xpath("//img[contains(@alt,'Nómina')]");
    private final By pestañatimbrado = By.xpath("//li[@id='tab4']");
    private final By seleccionarano = By.xpath("//select[@id='idAnio']");
    private final By seleccionarmes = By.xpath("//select[@id='idMes']");
    private final By seleccionarconcepto = By.xpath("//select[@id='concepto']");
    private final By seleccionarempleado = By.xpath("//input[@id='idTimbrado_0']");
    private final By botonbuscar = By.xpath("//img[@name='Image11']");
    private final By botonenviar = By.xpath("//img[@id='Image34']");
    private final By guardarincidencia = By.id("Image34");

    public EnvioTimbrado(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
    }
    
    
    /**
     * Método para desplegar la pantalla de Nómina.
     *
     * @author Carlos Martínez Ortega
     * @date 22/junio/2026
     * @throws Exception
     */
    public void pantallaNomina() throws Exception {
        try {
            Thread.sleep(800);
            u.click(botonNomina);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));
            testCase.addScreenShotCurrentStep(u, "Se despliega pantalla de Nomina");
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("La pantalla nomina no se desplego: " + e.getMessage());
        }
    }

    /**
     * Método para seleccionar la pestaña de Timbrado.
     *
     * @author Carlos Martínez Ortega
     * @date 22/junio/2026
     * @throws Exception
     */
    public void seleccionarPestañaTimbrado() throws Exception {
        try {
            u.click(pestañatimbrado);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botonenviar));
            testCase.addScreenShotCurrentStep(u, "Se despliega pantalla de Incidencias");
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("No se desplego la pantalla Incidencias: " + e.getMessage());
        }
    }

    /**
     * Método para llenar información de año y mes, y seleccionar empleado.
     *
     * @author Carlos Martínez Ortega
     * @date 22/junio/2026
     * @param ano Año a seleccionar
     * @param mes Mes a seleccionar
     * @throws Exception
     */
    public void llenarInformacionYSeleccionarEmpleado(String ano, String mes) throws Exception {
        try {
            u.waitWebElement(seleccionarano, 25, 4);
            u.getDriver().findElement(seleccionarano).click();
            Thread.sleep(1000);
            u.getDriver().findElement(seleccionarano).sendKeys(ano);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionarano).sendKeys(Keys.ENTER);

            u.waitWebElement(seleccionarmes, 25, 4);
            u.getDriver().findElement(seleccionarmes).click();
            Thread.sleep(1000);
            u.getDriver().findElement(seleccionarmes).sendKeys(mes);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionarmes).sendKeys(Keys.ENTER);

            u.click(seleccionarempleado);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botonenviar));
            testCase.addScreenShotCurrentStep(u, "Se lleno de manera correcta el campo cantidad de horas");
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("No se pudo ingresar la cantidad de horas y minutos: " + e.getMessage());
        }
    }

    /**
     * Método para seleccionar concepto y buscar.
     *
     * @author Carlos Martínez Ortega
     * @date 22/junio/2026
     * @param concepto Concepto a seleccionar en el dropdown
     * @throws Exception
     */
    public void seleccionarConceptoYBuscar(String concepto) throws Exception {
        try {
            u.click(botonbuscar);

            u.waitWebElement(seleccionarconcepto, 25, 4);
            u.getDriver().findElement(seleccionarconcepto).click();
            Thread.sleep(1000);
            u.getDriver().findElement(seleccionarconcepto).sendKeys(concepto);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionarconcepto).sendKeys(Keys.ENTER);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));
            testCase.addScreenShotCurrentStep(u, "Se lleno de manera correcta el campo cantidad de horas");
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("No se pudo ingresar la cantidad de horas y minutos: " + e.getMessage());
        }
    }

    /**
     * Método para hacer click en el botón Enviar.
     *
     * @author Carlos Martínez Ortega
     * @date 22/junio/2026
     * @throws Exception
     */
    public void clicEnEnviar() throws Exception {
        try {
            u.click(pestañatimbrado);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botonenviar));
            testCase.addScreenShotCurrentStep(u, "Se despliega pantalla de Incidencias");
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("No se desplego la pantalla Incidencias: " + e.getMessage());
        }
    }
}
