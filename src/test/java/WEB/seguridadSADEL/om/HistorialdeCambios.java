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

public class HistorialdeCambios {
    private final SeleniumUtil u;
    private final TestCase testCase;

    /**
	 * Localizadores
	 ******************************************************************************************/
    private final By botonHistorialdeMovimientos = By.name("Image45");
    private final By seleccionazona = By.id("zoneId");
    private final By seleccionarplaza = By.id("idNewPlaza");
    private final By seleccionartienda = By.id("idNewStore");
    private final By ingresarnss = By.id("nss");
    private final By ingresarcurp = By.id("curp");
    private final By ingresarfechainicio = By.id("startDate");
    private final By ingresarfechafin = By.id("endDate");
    private final By seleccionarestatusempleado = By.id("idStatus");
    private final By seleccionarmostrar = By.id("cantidadDeRegistros");
    private final By seleccionartipomovimiento = By.id("idMovementType");
    private final By seleccionarsubtipomovimiento = By.id("idMovementSubtype");
    private final By botonbuscar = By.name("Image20");
    private final By tablacargada = By.id("divDataGrid");

    public HistorialdeCambios(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
    }
    /**
     * Método para ingresar a la pantalla de Historial de Movimientos.
     *
     * @author Carlos Martínez Ortega
     * @date 29/Mayo/2026
     * @throws Exception
     */
    public boolean pantallaHistorialDeMovimientos() {
        boolean validacion = false;
        try {
            u.waitWebElement(botonHistorialdeMovimientos, 25, 4);
            u.click(botonHistorialdeMovimientos);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botonbuscar));
            testCase.addScreenShotCurrentStep(u, "Se despliega pantalla de Historial de Movimientos");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar a la pantalla historial de movimientos " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para ingresar información en los filtros zona, plaza y tienda.
     *
     * @author Carlos Martínez Ortega
     * @date 29/Mayo/2026
     * @param zona   Zona a seleccionar
     * @param plaza  Plaza a seleccionar
     * @param tienda Tienda a seleccionar
     * @throws Exception
     */
    public boolean ingresarInformacionEnFiltros(String zona, String plaza, String tienda) {
        boolean validacion = false;
        try {
            // Seleccionar Zona
            u.waitWebElement(seleccionazona, 30, 5);
            WebElement zonaElem = u.getDriver().findElement(seleccionazona);
            zonaElem.click();
            Thread.sleep(800);
            zonaElem.sendKeys(zona);
            Thread.sleep(800);
            zonaElem.sendKeys(Keys.ENTER);
            Thread.sleep(1500);

            // Seleccionar Plaza
            u.waitWebElement(seleccionarplaza, 30, 5);
            WebElement plazaElem = u.getDriver().findElement(seleccionarplaza);
            plazaElem.click();
            Thread.sleep(800);
            plazaElem.sendKeys(plaza);
            Thread.sleep(800);
            plazaElem.sendKeys(Keys.ENTER);
            Thread.sleep(1500);

            // Seleccionar Tienda
            u.waitWebElement(seleccionartienda, 30, 5);
            WebElement tiendaElem = u.getDriver().findElement(seleccionartienda);
            tiendaElem.click();
            Thread.sleep(800);
            tiendaElem.sendKeys(tienda);
            Thread.sleep(800);
            tiendaElem.sendKeys(Keys.ENTER);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botonbuscar));
            testCase.addScreenShotCurrentStep(u, "Los filtros se seleccionaron de manera correcta");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar información en los filtros " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar información en los campos estatus y mostrar.
     *
     * @author Carlos Martínez Ortega
     * @date 29/Mayo/2026
     * @param estatus Estatus a seleccionar
     * @param mostrar Cantidad a mostrar
     * @throws Exception
     */
    public boolean seleccionarEstatusYMostrar(String estatus, String mostrar) {
        boolean validacion = false;
        try {
            u.waitWebElement(seleccionarestatusempleado, 25, 4);
            WebElement estatusElem = u.getDriver().findElement(seleccionarestatusempleado);
            estatusElem.click();
            Thread.sleep(800);
            estatusElem.sendKeys(estatus);
            Thread.sleep(800);
            estatusElem.sendKeys(Keys.ENTER);

            WebElement mostrarElem = u.getDriver().findElement(seleccionarmostrar);
            mostrarElem.click();
            Thread.sleep(800);
            mostrarElem.sendKeys(mostrar);
            Thread.sleep(800);
            mostrarElem.sendKeys(Keys.ENTER);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botonbuscar));
            testCase.addScreenShotCurrentStep(u, "Se seleccionaron los filtros de manera correcta");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logro seleccionar el estatus y motrar" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para ingresar información en el campo NSS.
     *
     * @author Carlos Martínez Ortega
     * @date 29/Mayo/2026
     * @param nss Número de Seguro Social a ingresar
     * @throws Exception
     */
    public boolean seleccionarNss(String nss) {
        boolean validacion = false;
        try {
            u.waitWebElement(ingresarnss, 25, 4);
            u.clear(ingresarnss);
            u.sendKeys(ingresarnss, nss);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botonbuscar));
            testCase.addScreenShotCurrentStep(u, "Se ingresó el Nss de manera correcta");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar información en el campo NSS" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para ingresar información en el campo CURP.
     *
     * @author Carlos Martínez Ortega
     * @date 29/Mayo/2026
     * @param curp CURP a ingresar
     * @throws Exception
     */
    public boolean seleccionarCurp(String curp) {
        boolean validacion = false;
        try {
            u.waitWebElement(ingresarcurp, 25, 4);
            u.clear(ingresarcurp);
            u.sendKeys(ingresarcurp, curp);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botonbuscar));
            testCase.addScreenShotCurrentStep(u, "Se ingreso el Curp de manera correcta");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar información en el campo Curp" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar información en los campos Tipo de Movimiento y Sub Tipo de Movimiento.
     *
     * @author Carlos Martínez Ortega
     * @date 29/Mayo/2026
     * @param tipomovimiento    Tipo de movimiento a seleccionar
     * @param subtipomovimiento Sub tipo de movimiento a seleccionar
     * @throws Exception
     */
    public boolean seleccionarTipoYSubtipoMovimiento(String tipomovimiento, String subtipomovimiento) {
        boolean validacion = false;
        try {
            u.waitWebElement(seleccionartipomovimiento, 25, 4);
            u.getDriver().findElement(seleccionartipomovimiento).click();
            Thread.sleep(800);
            u.getDriver().findElement(seleccionartipomovimiento).sendKeys(tipomovimiento);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionartipomovimiento).sendKeys(Keys.ENTER);

            u.waitWebElement(seleccionarsubtipomovimiento, 25, 4);
            u.getDriver().findElement(seleccionarsubtipomovimiento).click();
            Thread.sleep(800);
            u.getDriver().findElement(seleccionarsubtipomovimiento).sendKeys(subtipomovimiento);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionarsubtipomovimiento).sendKeys(Keys.ENTER);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botonbuscar));
            testCase.addScreenShotCurrentStep(u, "Se seleccionaron los filtros de manera correcta");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar información en tipo y subtipo de incidencia" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar información en los campos fecha inicio y fecha fin.
     *
     * @author Carlos Martínez Ortega
     * @date 29/Mayo/2026
     * @param fechainicio Fecha de inicio a ingresar
     * @param fechafin    Fecha de fin a ingresar
     * @throws Exception
     */
    public boolean ingresarFechaInicioYFin(String fechainicio, String fechafin) {
        boolean validacion = false;
        try {
            u.waitWebElement(ingresarfechainicio, 25, 4);
            u.clear(ingresarfechainicio);
            u.sendKeys(ingresarfechainicio, fechainicio);

            u.waitWebElement(ingresarfechafin, 25, 4);
            u.clear(ingresarfechafin);
            u.sendKeys(ingresarfechafin, fechafin);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(botonbuscar));
            testCase.addScreenShotCurrentStep(u, "Se ingresaron las fechas de manera correcta");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar información en los campos Fecha inicio y Fecha fin" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para validar que se cargue la tabla después de dar clic en buscar.
     *
     * @author Carlos Martínez Ortega
     * @date 29/Mayo/2026
     * @throws Exception
     */
    public boolean seleccionarBuscar() {
        boolean validacion = false;
        try {
            u.waitWebElement(botonbuscar, 25, 4);
            u.click(botonbuscar);
            System.out.println("Clic en Buscar realizado. Esperando que cargue la tabla...");
            Thread.sleep(2000);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tablacargada));
            Thread.sleep(1000);
            testCase.addScreenShotCurrentStep(u, "Se ejecutó la búsqueda de manera correcta y se muestran los resultados");
            System.out.println("¡Tabla de resultados detectada con éxito!");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró dar clic en el boton buscar" + e.getMessage());
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
            testCase.addCodeEvidenceCurrentStep("La tabla no contiene información" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }
}
