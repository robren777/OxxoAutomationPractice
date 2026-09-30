package WEB.seguridadSADEL.om;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import utils.selenium.SeleniumUtil;
import modelo.TestCase;

import java.io.File;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.testng.Assert;

public class Reportes {
    private final SeleniumUtil u;
    private final TestCase testCase;
    
    /**
	 * Localizadores
	 ******************************************************************************************/
    private final By reportesMenu = By.id("e0_0i");
    private final By reporteinformacionpersonal = By.id("e0_8o");
    private final By reportelideryempleadosactivos = By.id("e0_1o");
    private final By reportedatosdemograficos = By.id("e0_5o");
    private final By reportedomiciliodeempleados = By.id("e0_10o");
    private final By reporteescolaridaddeempleados = By.id("e0_11o");
    private final By reportemovimientosempleados = By.id("e0_9o");
    private final By reporteincidenciasempleados = By.id("e0_7o");
    private final By reporteplanhorario = By.id("e0_2o");
    private final By reportevalidacionincidencia = By.id("e0_6o");
    private final By reportevalidacionjornadas = By.id("e0_3o");
    private final By seleccionazona = By.id("zoneId");
    private final By seleccionazone = By.id("zone");
    private final By seleccionarlazona = By.id("idZone");
    private final By seleccionarplaza = By.id("plazaId");
    private final By seleccionarlaplaza = By.id("idPlaza");
    private final By seleccionartienda = By.id("storeId");
    private final By seleccionarlatienda = By.id("idStore");
    private final By seleccionarperiodo = By.id("idFecha");
    private final By tablacargada = By.id("divDataGrid");
    private final By botongenerar = By.name("Image12");
    private final By botonexportarexcel = By.name("Image13");
    

    public Reportes(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
    }

    /**
     * Método para desplegar las opciones de "Reportes" desde el menú principal.
     *
     * @author Carlos Martínez Ortega
     * @date 01/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarReportes() {
        boolean validacion = false;
        try {
            u.waitWebElement(reportesMenu, 25, 4);
            u.click(reportesMenu);
            testCase.addScreenShotCurrentStep(u, "Se seleccionó la opción Reportes del menú");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logro ingresar a la pantalla reportes: " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic a la opción del menú "Reporte Datos Demográficos".
     *
     * @author Carlos Martínez Ortega
     * @date 01/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarDatosDemograficos() {
        boolean validacion = false;
        try {
            u.waitWebElement(reportedatosdemograficos, 25, 4);
            u.click(reportedatosdemograficos);
            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Reporte Datos Demográficos");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logro ingresar a la pantalla Datos Demograficos " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic a la opción del menú "Reporte Información Personal".
     *
     * @author Carlos Martínez Ortega
     * @date 01/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarReporteInformacionPersonal() {
        boolean validacion = false;
        try {
            u.waitWebElement(reporteinformacionpersonal, 25, 4);
            u.click(reporteinformacionpersonal);
            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Reporte Información Personal");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logro ingresar a la pantalla Reporte Información Personal " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic a la opción del menú "Reporte Líder y Empleados Activos".
     *
     * @author Carlos Martínez Ortega
     * @date 02/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarLiderYEmpleadosActivos() {
        boolean validacion = false;
        try {
            u.waitWebElement(reportelideryempleadosactivos, 25, 4);
            u.click(reportelideryempleadosactivos);
            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Reporte Líder y Empleados Activos");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logro ingresar a la pantalla Lider y Empleados Activos " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic a la opción del menú "Reporte Domicilio de Empleados".
     *
     * @author Carlos Martínez Ortega
     * @date 02/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarDomicilioDeEmpleados() {
        boolean validacion = false;
        try {
            u.waitWebElement(reportedomiciliodeempleados, 25, 4);
            u.click(reportedomiciliodeempleados);
            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Reporte Domicilio de Empleados");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logro ingresar a la pantalla Domicilio de Empleados " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic a la opción del menú "Reporte Escolaridad Empleados".
     *
     * @author Carlos Martínez Ortega
     * @date 02/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarEscolaridadDeEmpleados() {
        boolean validacion = false;
        try {
            u.waitWebElement(reporteescolaridaddeempleados, 25, 4);
            u.click(reporteescolaridaddeempleados);
            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Reporte Escolaridad de Empleados");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logro ingresar a la pantalla Escolaridad de Empleados " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para dar clic a la opción del menú "Reporte Movimientos Empleados".
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarMovimientosEmpleados() {
        boolean validacion = false;
        try {
            u.waitWebElement(reportemovimientosempleados, 25, 4);
            u.click(reportemovimientosempleados);
            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Reporte Movimientos Empleados");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar a la pantalla Movimientos Empleados " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic a la opción del menú "Reporte Incidencias Empleados".
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarIncidenciasEmpleados() {
        boolean validacion = false;
        try {
            u.waitWebElement(reporteincidenciasempleados, 25, 4);
            u.click(reporteincidenciasempleados);
            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Reporte Incidencias Empleados");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar a la pantalla Incidencias Empleados" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic a la opción del menú "Reporte Plan Horario".
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarPlanHorario() {
        boolean validacion = false;
        try {
            u.waitWebElement(reporteplanhorario, 25, 4);
            u.click(reporteplanhorario);
            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Reporte Plan Horario");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar a la pantalla Plan Horario" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic a la opción del menú "Reporte de Validación de Incidencias".
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarValidacionIncidencias() {
        boolean validacion = false;
        try {
            u.waitWebElement(reportevalidacionincidencia, 25, 4);
            u.click(reportevalidacionincidencia);
            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Reporte de Validación de Incidencias");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar a la pantalla Validación Incidencias" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic a la opción del menú "Reporte de Validación de Jornadas".
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @throws Exception
     */
    public boolean seleccionarValidacionJornadas() {
        boolean validacion = false;
        try {
            u.waitWebElement(reportevalidacionjornadas, 25, 4);
            u.click(reportevalidacionjornadas);
            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Reporte de Validación de Jornadas");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar a la pantalla Validación Jornadas" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    
    /**
     * Método para ingresar información en los filtros zona, plaza y tienda.
     *
     * @author Carlos Martínez Ortega
     * @date 01/Junio/2026
     * @param zona   Zona a seleccionar
     * @param plaza  Plaza a seleccionar
     * @param tienda Tienda a seleccionar
     * @throws Exception
     */
    public boolean ingresarinformacionenFiltros(String zona, String plaza) {
        boolean validacion = false;
        try {
            // Seleccionar Zona
            u.waitWebElement(seleccionarlazona, 30, 5);
            u.getDriver().findElement(seleccionarlazona).click();
            Thread.sleep(500);
            u.getDriver().findElement(seleccionarlazona).sendKeys(zona);
            Thread.sleep(500);
            u.getDriver().findElement(seleccionarlazona).sendKeys(Keys.ENTER);
            Thread.sleep(500);

            // Seleccionar Plaza
            u.waitWebElement(seleccionarlaplaza, 30, 5);
            u.getDriver().findElement(seleccionarlaplaza).click();
            Thread.sleep(500);
            u.getDriver().findElement(seleccionarlaplaza).sendKeys(plaza);
            Thread.sleep(500);
            u.getDriver().findElement(seleccionarlaplaza).sendKeys(Keys.ENTER);
            Thread.sleep(500);

            testCase.addScreenShotCurrentStep(u, "Se ingreso información en los filtros zona y plaza de manera correcta");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logro ingresar información en los filtros" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar información en los filtros zona, plaza y tienda.
     *
     * @author Carlos Martínez Ortega
     * @date 01/Junio/2026
     * @param zona   Zona a seleccionar
     * @param plaza  Plaza a seleccionar
     * @param tienda Tienda a seleccionar
     * @throws Exception
     */
    public boolean ingresarenloscamposinformaion(String zona, String plaza, String tienda) {
        boolean validacion = false;
        try {
            // Seleccionar Zona
            u.waitWebElement(seleccionazona, 30, 5);
            u.getDriver().findElement(seleccionazona).click();
            Thread.sleep(2000);
            u.getDriver().findElement(seleccionazona).sendKeys(zona);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionazona).sendKeys(Keys.ENTER);
            Thread.sleep(2500);

            // Seleccionar Plaza
            u.waitWebElement(seleccionarplaza, 30, 5);
            u.getDriver().findElement(seleccionarplaza).click();
            Thread.sleep(2000);
            u.getDriver().findElement(seleccionarplaza).sendKeys(plaza);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionarplaza).sendKeys(Keys.ENTER);
            Thread.sleep(2500);

            // Seleccionar Tienda
            u.waitWebElement(seleccionartienda, 30, 5);
            u.getDriver().findElement(seleccionartienda).click();
            Thread.sleep(2000);
            u.getDriver().findElement(seleccionartienda).sendKeys(tienda);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionartienda).sendKeys(Keys.ENTER);

            testCase.addScreenShotCurrentStep(u, "Se ingreso información en los filtros zona, plaza y tienda de manera correcta");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No agrego la información en los filtros" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para ingresar información en los filtros zona, plaza y tienda (locators alternativos).
     *
     * @author Carlos Martínez Ortega
     * @date 02/Junio/2026
     * @param zona   Zona a seleccionar
     * @param plaza  Plaza a seleccionar
     * @param tienda Tienda a seleccionar
     * @throws Exception
     */
    public boolean ingresarInformacionFiltros(String zona, String plaza, String tienda) {
        boolean validacion = false;
        try {
            // Seleccionar Zona
            u.waitWebElement(seleccionazone, 30, 5);
            u.getDriver().findElement(seleccionazone).click();
            Thread.sleep(500);
            u.getDriver().findElement(seleccionazone).sendKeys(zona);
            Thread.sleep(500);
            u.getDriver().findElement(seleccionazone).sendKeys(Keys.ENTER);
            Thread.sleep(500);

            // Seleccionar Plaza
            u.waitWebElement(seleccionarplaza, 30, 5);
            u.getDriver().findElement(seleccionarplaza).click();
            Thread.sleep(500);
            u.getDriver().findElement(seleccionarplaza).sendKeys(plaza);
            Thread.sleep(500);
            u.getDriver().findElement(seleccionarplaza).sendKeys(Keys.ENTER);
            Thread.sleep(500);

            // Seleccionar Tienda
            u.waitWebElement(seleccionartienda, 30, 5);
            u.getDriver().findElement(seleccionartienda).click();
            Thread.sleep(500);
            u.getDriver().findElement(seleccionartienda).sendKeys(tienda);
            Thread.sleep(500);
            u.getDriver().findElement(seleccionartienda).sendKeys(Keys.ENTER);

            testCase.addScreenShotCurrentStep(u, "Los campos zona, plaza y tienda se llenaron de manera correcta");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error al intentar agregar información a los filtros " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para ingresar información en los filtros zona y plaza.
     *
     * @author Carlos Martínez Ortega
     * @date 03/Junio/2026
     * @param zona  Zona a seleccionar
     * @param plaza Plaza a seleccionar
     * @throws Exception
     */
    public boolean llenarloscamposfiltros(String zona, String plaza, String tienda) {
        boolean validacion = false;
        try {
            // Seleccionar Zona
            u.waitWebElement(seleccionazone, 30, 5);
            u.getDriver().findElement(seleccionazone).click();
            Thread.sleep(2000);
            u.getDriver().findElement(seleccionazone).sendKeys(zona);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionazone).sendKeys(Keys.ENTER);
            Thread.sleep(2500);

            // Seleccionar Plaza
            u.waitWebElement(seleccionarlaplaza, 30, 5);
            u.getDriver().findElement(seleccionarlaplaza).click();
            Thread.sleep(2000);
            u.getDriver().findElement(seleccionarlaplaza).sendKeys(plaza);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionarlaplaza).sendKeys(Keys.ENTER);
            
            // Seleccionar Tienda
            u.waitWebElement(seleccionarlatienda, 30, 5);
            u.getDriver().findElement(seleccionarlatienda).click();
            Thread.sleep(500);
            u.getDriver().findElement(seleccionarlatienda).sendKeys(tienda);
            Thread.sleep(500);
            u.getDriver().findElement(seleccionarlatienda).sendKeys(Keys.ENTER);

            testCase.addScreenShotCurrentStep(u, "Se llenaron de manera correcta los campos zona, plaza y tienda");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró realizar el llenado de los campos" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para ingresar información en los filtros zona, plaza, tienda y periodo.
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @param zona    Zona a seleccionar
     * @param plaza   Plaza a seleccionar
     * @param tienda  Tienda a seleccionar
     * @param periodo Periodo a seleccionar
     * @throws Exception
     */
    public boolean llenadocamposfiltrosconperiodo(String zona, String plaza, String tienda, String periodo) {
        boolean validacion = false;
        try {
            // Seleccionar Zona
            u.waitWebElement(seleccionazone, 30, 5);
            u.getDriver().findElement(seleccionazone).click();
            Thread.sleep(2000);
            u.getDriver().findElement(seleccionazone).sendKeys(zona);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionazone).sendKeys(Keys.ENTER);
            Thread.sleep(2500);

            // Seleccionar Plaza
            u.waitWebElement(seleccionarlaplaza, 30, 5);
            u.getDriver().findElement(seleccionarlaplaza).click();
            Thread.sleep(2000);
            u.getDriver().findElement(seleccionarlaplaza).sendKeys(plaza);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionarlaplaza).sendKeys(Keys.ENTER);
            Thread.sleep(2500);

            // Seleccionar Tienda
            u.waitWebElement(seleccionarlatienda, 30, 5);
            u.getDriver().findElement(seleccionarlatienda).click();
            Thread.sleep(2000);
            u.getDriver().findElement(seleccionarlatienda).sendKeys(tienda);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionarlatienda).sendKeys(Keys.ENTER);

            // Seleccionar Periodo
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

            testCase.addScreenShotCurrentStep(u, "Los campos zona, plaza, tienda y periodo se llenaron correctamente");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Los campos no se llenaron de manera correcta" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar información en los filtros zona, plaza y periodo.
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @param zona    Zona a seleccionar
     * @param plaza   Plaza a seleccionar
     * @param periodo Periodo a seleccionar
     * @throws Exception
     */
    public boolean ingresarInformacionFiltrosConPeriodo(String zona, String plaza) {
        boolean validacion = false;
        try {
            // Seleccionar Zona
            u.waitWebElement(seleccionazone, 30, 5);
            u.getDriver().findElement(seleccionazone).click();
            Thread.sleep(500);
            u.getDriver().findElement(seleccionazone).sendKeys(zona);
            Thread.sleep(500);
            u.getDriver().findElement(seleccionazone).sendKeys(Keys.ENTER);
            Thread.sleep(500);

            // Seleccionar Plaza
            u.waitWebElement(seleccionarlaplaza, 30, 5);
            u.getDriver().findElement(seleccionarlaplaza).click();
            Thread.sleep(500);
            u.getDriver().findElement(seleccionarlaplaza).sendKeys(plaza);
            Thread.sleep(500);
            u.getDriver().findElement(seleccionarlaplaza).sendKeys(Keys.ENTER);
            Thread.sleep(500);

            testCase.addScreenShotCurrentStep(u, "Se ingreso de manera correcta la información de los campso zona y plaza");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Los filtros no fueron seleccionados correctamente " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic en el botón Generar (locator: botongenerar de clase).
     *
     * @author Carlos Martínez Ortega
     * @date 01/Junio/2026
     * @throws Exception
     */
    public boolean clicBotonGenerar() {
        boolean validacion = false;
        try {
            u.waitWebElement(botongenerar, 25, 4);
            u.click(botongenerar);
            testCase.addScreenShotCurrentStep(u, "Se dio clic en el botón Generar");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró dar clic en el botón generar " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic en el botón Generar (locator: Image20).
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @throws Exception
     */
    public boolean generarelreporte() {
        boolean validacion = false;
        By botongenerar = By.id("Image20");
        try {
            u.waitWebElement(botongenerar, 25, 4);
            u.click(botongenerar);
            testCase.addScreenShotCurrentStep(u, "Se logró dar clic en el botón generar");
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró generar el reporte " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para validar que la tabla cargó información correctamente.
     *
     * @author Carlos Martínez Ortega
     * @date 01/Junio/2026
     * @throws Exception
     */
    public boolean validarTablaCargada() {
        boolean validacion = false;
        try {
            WebElement elementotablacargada = u.waitWebElement(tablacargada, 60, 5);
            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript("arguments[0].scrollIntoView(true);", elementotablacargada);
            System.out.println("Scroll nativo ejecutado correctamente.");
            testCase.addScreenShotCurrentStep(u, "Se valida que la tabla cargó información correctamente");
            u.unhighLight(tablacargada);
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("La tabla no se cargo de manera correcta" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic en el botón Exportar Excel (locator: botonexportarexcel de clase).
     *
     * @author Carlos Martínez Ortega
     * @date 08/Junio/2026
     * @throws Exception
     */
    public boolean clicBotonExportarExcel() {
        boolean validacion = false;
        try {
            u.waitWebElement(botonexportarexcel, 30, 5);
            WebElement botonExportar = u.getDriver().findElement(botonexportarexcel);
            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript("arguments[0].click();", botonExportar);
            Thread.sleep(3000);

            java.awt.Robot robot = new java.awt.Robot();
            // Enviamos ALT + G (atajo nativo en Windows para presionar el botón "Guardar")
            robot.keyPress(java.awt.event.KeyEvent.VK_ALT);
            robot.keyPress(java.awt.event.KeyEvent.VK_G);
            robot.keyRelease(java.awt.event.KeyEvent.VK_G);
            robot.keyRelease(java.awt.event.KeyEvent.VK_ALT);
            
            Thread.sleep(500);
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró dar clic en el botón exportar excel" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic en el botón Exportar Excel (locator: Image21).
     *
     * @author Carlos Martínez Ortega
     * @date 12/Junio/2026
     * @throws Exception
     */
    public boolean exportarelreporteexcel() {
        boolean validacion = false;
        By botonexportarexcel = By.id("Image21");
        try {
            u.waitWebElement(botonexportarexcel, 30, 5);
            WebElement botonExportar = u.getDriver().findElement(botonexportarexcel);
            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript("arguments[0].click();", botonExportar);
            Thread.sleep(3000);

            java.awt.Robot robot = new java.awt.Robot();
            // Enviamos ALT + G (atajo nativo en Windows para presionar el botón "Guardar")
            robot.keyPress(java.awt.event.KeyEvent.VK_ALT);
            robot.keyPress(java.awt.event.KeyEvent.VK_G);
            robot.keyRelease(java.awt.event.KeyEvent.VK_G);
            robot.keyRelease(java.awt.event.KeyEvent.VK_ALT);
            
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se encontro el botón exportar excel" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para validar que el archivo se descargó correctamente.
     *
     * @author Carlos Martínez Ortega
     * @date 08/Junio/2026
     * @param rutaBase         Propiedad del sistema que contiene la ruta base
     * @param carpetaDescargas Nombre de la carpeta de descargas
     * @param nombreEsperado   Nombre exacto del archivo esperado (con extensión .xls)
     * @throws Exception
     */
    public boolean validarDescargaExcel(String rutaBase, String carpetaDescargas, String nombreEsperado) {
        boolean validacion = false;
        try {
            Thread.sleep(1500);
            String rutaDescargas = System.getProperty(rutaBase) + "\\" + carpetaDescargas;
            File carpeta = new File(rutaDescargas);
            File[] archivos = carpeta.listFiles();

            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.getName().equals(nombreEsperado) && archivo.getName().endsWith(".xls")) {
                        System.out.println("Archivo encontrado: " + archivo.getAbsolutePath());
                        testCase.addScreenShotCurrentStep(u, "Se guardó de manera correcta el excel: " + nombreEsperado);
                        validacion = true;
                        break;
                    }
                }
            }

            if (!validacion) {
                Assert.fail("No se encontró el archivo esperado: " + nombreEsperado +
                            " (formato .xls) en la carpeta: " + rutaDescargas);
            }
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("El excel no se encontro por lo que no se descargo" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }
    
}
