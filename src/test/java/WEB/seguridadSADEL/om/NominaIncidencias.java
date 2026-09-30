package WEB.seguridadSADEL.om;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import utils.selenium.SeleniumUtil;
import modelo.TestCase;
import java.time.Duration;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class NominaIncidencias {
    private final SeleniumUtil u;
    private final TestCase testCase;

    /**
	 * Localizadores
	 ******************************************************************************************/
    private final By botonNomina = By.xpath("//img[contains(@alt,'Nómina')]");
    private final By seleccionarpestañaincidencia = By.xpath("//li[@class='TabbedPanelsTab TabbedPanelsTabSelected']");
    private final By seleccionarfecha = By.id("idFecha");
    private final By seleccionarplaza = By.id("idPlaza");
    private final By seleccionartienda = By.id("idStore");
    private final By nuevaincidencia = By.xpath("//img[contains(@src, 'btn_nuevo')]");
    private final By editarincidencia = By.xpath("//img[contains(@src, 'btn_editar')]");
    private final By eliminarincidencia = By.xpath("//img[contains(@src, 'btn_eliminar')]");
    private final By verdetalleincidencia = By.xpath("//img[contains(@src, 'btn_verDetalle')]");
    private final By tipodeincidencia = By.id("idTipoIncidencia");
    private final By subtipodeincidencia = By.id("idSubtipoIncidencia");
    private final By justificaciondelacarga = By.id("justificacionCarga");
    private final By guardarincidencia = By.id("Image34");
    private final By cantidadhoras = By.id("cantHora");
    private final By cantidadminutos = By.id("cantMin");
    private final By ingresarimporte = By.id("importe");
    private final By importecuota = By.id("importeCuota");
    private final By totaladeudado = By.id("importeTotalDesc");
    private final By mensajeRetornoDetalle = By.id("mensajeRetornoDetalle");
    
    /**
     * Variables
     */
    public final String INCIDENCIA_PRESENCIAS = "Presencias";
    public final String COLUMNA_INCIDENCIA = ", Columna";
    public final String ARGUMENTOS_CLICK = "arguments[0].click();";
    public final String ARGUMENTOS_SCROLL = "arguments[0].scrollIntoView(true);";
    public final String MENSAJE_ESPERADO = "La petición esta en lista de espera";
    public final String ARGUMENTOS_RETORNO = "return arguments[0].value;";

    public NominaIncidencias(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
    }
    
    /**
     * Método para validar el ingreso a la pantalla de Nómina.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @throws Exception
     */
    public boolean pantallaNomina() {
        boolean validacion = false;
        try {
            Thread.sleep(800);  // Si es posible, reemplaza esto por un waitWebElement más adelante
            u.click(botonNomina);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            testCase.addScreenShotCurrentStep(u, "Se despliega pantalla de Nomina");

            if (u.isDisplayed(guardarincidencia)) {
                validacion = true;
            }
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar a la pantalla Nomina " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para validar el ingreso a la pestaña de Incidencias.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @throws Exception
     */
    public boolean seleccionarPestañaIncidencias() {
        boolean validacion = false;
        try {
            u.click(seleccionarpestañaincidencia);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            testCase.addScreenShotCurrentStep(u, "Se despliega pantalla de Incidencias");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar la pestaña inciedncias" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar la Plaza.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @param nombrePlaza Plaza a seleccionar
     * @throws Exception
     */
    public boolean seleccionarPlaza(String nombrePlaza) {
        boolean validacion = false;
        try {
            u.waitWebElement(seleccionarplaza, 25, 4);
            u.getDriver().findElement(seleccionarplaza).click();
            Thread.sleep(1000);
            u.getDriver().findElement(seleccionarplaza).sendKeys(nombrePlaza);
            Thread.sleep(1500);
            u.getDriver().findElement(seleccionarplaza).sendKeys(Keys.ENTER);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            testCase.addScreenShotCurrentStep(u, "Se seleccionó correctamente la tienda");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar la plaza" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar la Tienda.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @param nombreTienda Tienda a seleccionar
     * @throws Exception
     */
    public boolean seleccionarTienda(String nombreTienda) {
        boolean validacion = false;
        try {
            Thread.sleep(1500);
            u.waitWebElement(seleccionartienda, 35, 6);
            u.getDriver().findElement(seleccionartienda).click();
            Thread.sleep(1500);
            u.getDriver().findElement(seleccionartienda).sendKeys(nombreTienda);
            Thread.sleep(800);
            u.getDriver().findElement(seleccionartienda).sendKeys(Keys.ENTER);
            Thread.sleep(1000);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            testCase.addScreenShotCurrentStep(u, "Se seleccionó correctamente la tienda");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar la tienda" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar el Periodo de Fechas.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @throws Exception
     */
    public boolean seleccionarPeriodo() {
        boolean validacion = false;
        String rangoFechasDinamico = "";
        try {
            LocalDate hoy = LocalDate.now();
            LocalDate lunes = hoy.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate domingo = hoy.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            rangoFechasDinamico = lunes.format(formato) + " - " + domingo.format(formato);

            u.waitWebElement(seleccionarfecha, 30, 4);
            WebElement comboFecha = u.getDriver().findElement(seleccionarfecha);

            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript(
                "var select = arguments[0]; " +
                "var valorBuscado = arguments[1]; " +
                "var encontrado = false; " +
                "for (var i = 0; i < select.options.length; i++) { " +
                " if (select.options[i].text.replace(/^\\s+|\\s+$/g, '') === valorBuscado.replace(/^\\s+|\\s+$/g, '')) { " +
                " select.selectedIndex = i; " +
                " encontrado = true; " +
                " break; " +
                " } " +
                "} " +
                "if (encontrado && select.fireEvent) { " +
                " select.fireEvent('onchange'); " +
                "}", comboFecha, rangoFechasDinamico
            );

            System.out.println("Opción del combo modificada de manera atómica con JavaScript.");
            Thread.sleep(2000);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            testCase.addScreenShotCurrentStep(u, "Se selecciono el rango de fechas");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar el período" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para dar clic en un día y cerrar la alerta para cuando en ese día descansa el colaborador.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @param numeroFila      Fila de la celda en la tabla
     * @param numeroColumna   Columna de la celda en la tabla
     * @param tipoIncidencia  Tipo de incidencia a registrar
     * @throws Exception
     */
    public boolean seleccionarDiaACargar(String numeroFila, String numeroColumna, String tipoIncidencia) {
        boolean validacion = false;
        try {
        	By celda = getCeldaTabla(numeroFila, numeroColumna);
            WebElement elementoCelda = u.waitWebElement(celda, 30, 5);

            if (elementoCelda == null) {
                throw new Exception("No se encontró la celda en el HTML.");
            }

            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript(ARGUMENTOS_CLICK, elementoCelda);

            if (tipoIncidencia != null && tipoIncidencia.trim().equalsIgnoreCase(INCIDENCIA_PRESENCIAS)) {
                try {
                    Thread.sleep(1800);
                    Alert alert = u.getDriver().switchTo().alert();
                    String mensajeAlert = alert.getText().trim();
                    alert.accept();
                    System.out.println(mensajeAlert.contains("Día de descanso del empleado")
                        ? "Alerta de Día de descanso aceptada"
                        : "Otra alerta fue aceptada");
                } catch (Exception e) {
                    System.out.println("No se detectó alerta aunque era Presencias");
                }
            } else {
                System.out.println("Tipo de incidencia: " + tipoIncidencia + " No se espera alerta");
            }

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Día a cargar de manera correcta");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar el día" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para dar clic en la opción nueva Incidencia y validar que se muestren los campos correctos
     * según la incidencia a dar de alta.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @param tipoIncidencia Tipo de incidencia a registrar
     * @throws Exception
     */
    public boolean seleccionarNuevo(String tipoIncidencia) {
        boolean validacion = false;
        try {
            u.click(nuevaincidencia);
            u.waitWebElement(guardarincidencia, 30, 4);
            WebElement elementotipodeincidencia = u.waitWebElement(tipodeincidencia, 30, 5);

            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript(ARGUMENTOS_SCROLL, elementotipodeincidencia);

            boolean tipoHabilitado = u.getDriver().findElement(tipodeincidencia).isEnabled();
            boolean subtipoHabilitado = u.getDriver().findElement(subtipodeincidencia).isEnabled();
            boolean justificacionHabilitada = u.getDriver().findElement(justificaciondelacarga).isEnabled();

            boolean cantidadHorasHabilitada = false;
            boolean cantidadMinutosHabilitada = false;
            boolean importeHabilitado = false;
            boolean importeCuotaHabilitado = false;
            boolean totalAdeudadoHabilitado = false;

            boolean camposCorrectos = false;

            if (tipoIncidencia.equalsIgnoreCase(INCIDENCIA_PRESENCIAS) ||
                tipoIncidencia.equalsIgnoreCase("Horas Extras")) {
                cantidadHorasHabilitada = u.getDriver().findElement(cantidadhoras).isEnabled();
                cantidadMinutosHabilitada = u.getDriver().findElement(cantidadminutos).isEnabled();
                camposCorrectos = tipoHabilitado && subtipoHabilitado && justificacionHabilitada &&
                                  cantidadHorasHabilitada && cantidadMinutosHabilitada;

            } else if (tipoIncidencia.equalsIgnoreCase("Absentismos") ||
                       tipoIncidencia.equalsIgnoreCase("Vacaciones")) {
                camposCorrectos = tipoHabilitado && subtipoHabilitado && justificacionHabilitada;

            } else if (tipoIncidencia.equalsIgnoreCase("Pagos Complementarios")) {
                importeHabilitado = u.getDriver().findElement(ingresarimporte).isEnabled();
                camposCorrectos = tipoHabilitado && subtipoHabilitado && justificacionHabilitada && importeHabilitado;

            } else if (tipoIncidencia.equalsIgnoreCase("Descuentos")) {
                importeCuotaHabilitado = u.getDriver().findElement(importecuota).isEnabled();
                totalAdeudadoHabilitado = u.getDriver().findElement(totaladeudado).isEnabled();
                camposCorrectos = tipoHabilitado && subtipoHabilitado && justificacionHabilitada &&
                                  importeCuotaHabilitado && totalAdeudadoHabilitado;
            } else {
                camposCorrectos = tipoHabilitado && subtipoHabilitado && justificacionHabilitada;
            }

            if (!camposCorrectos) {
                throw new Exception("Los campos de nueva incidencia no se habilitaron correctamente.");
            }

            testCase.addScreenShotCurrentStep(u, "Se dio clic en 'Nuevo'");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró dar clic en nuevo" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para seleccionar el Tipo de Incidencia.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @param tipoIncidencia Tipo de incidencia a seleccionar
     * @throws Exception
     */
    public boolean seleccionarTipoIncidencia(String tipoIncidencia) {
        boolean validacion = false;
        try {
            u.waitWebElement(tipodeincidencia, 25, 4);
            u.getDriver().findElement(tipodeincidencia).click();
            Thread.sleep(1000);
            u.getDriver().findElement(tipodeincidencia).sendKeys(tipoIncidencia);
            Thread.sleep(800);
            u.getDriver().findElement(tipodeincidencia).sendKeys(Keys.ENTER);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            testCase.addScreenShotCurrentStep(u, "Se seleccionó correctamente el Tipo de Incidencia");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar Tipo Incidencia" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para seleccionar el Sub Tipo de Incidencia.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @param subtipoIncidencia Subtipo de incidencia a seleccionar
     * @throws Exception
     */
    public boolean seleccionarSubtipoIncidencia(String subtipoIncidencia) {
        boolean validacion = false;
        try {
            u.waitWebElement(subtipodeincidencia, 25, 4);
            u.getDriver().findElement(subtipodeincidencia).click();
            Thread.sleep(1000);
            u.getDriver().findElement(subtipodeincidencia).sendKeys(subtipoIncidencia);
            Thread.sleep(800);
            u.getDriver().findElement(subtipodeincidencia).sendKeys(Keys.ENTER);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            testCase.addScreenShotCurrentStep(u, "Se seleccionó correctamente el Subtipo de Incidencia");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar el subtipo incidencia" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar la Justificación de la Carga.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @param justificaciondelaCarga Texto de justificación a ingresar
     * @throws Exception
     */
    public boolean ingresarJustificacionCarga(String justificaciondelaCarga) {
        boolean validacion = false;
        try {
            u.clear(justificaciondelacarga);
            u.sendKeys(justificaciondelacarga, justificaciondelaCarga);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            testCase.addScreenShotCurrentStep(u, "Se ingresó la justificación de manera correcta");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar la justificación de carga" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar la cantidad de horas y minutos.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @param cantidadHoras   Número de horas a ingresar
     * @param cantidadMinutos Número de minutos a ingresar
     * @throws Exception
     */
    public boolean ingresarCantidadHoras(String cantidadHoras, String cantidadMinutos) {
        boolean validacion = false;
        try {
            u.waitWebElement(cantidadhoras, 25, 4);
            u.getDriver().findElement(cantidadhoras).click();
            Thread.sleep(1000);
            u.getDriver().findElement(cantidadhoras).sendKeys(cantidadHoras);
            Thread.sleep(800);
            u.getDriver().findElement(cantidadhoras).sendKeys(Keys.ENTER);

            u.waitWebElement(cantidadminutos, 25, 4);
            u.getDriver().findElement(cantidadminutos).click();
            Thread.sleep(1000);
            u.getDriver().findElement(cantidadminutos).sendKeys(cantidadMinutos);
            Thread.sleep(800);
            u.getDriver().findElement(cantidadminutos).sendKeys(Keys.ENTER);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            testCase.addScreenShotCurrentStep(u, "Se lleno de manera correcta el campo cantidad de horas");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar la cantidad horas" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar el importe cuota.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @param importeCuota Valor del importe cuota a ingresar
     * @throws Exception
     */
    public boolean ingresarImporteCuota(String importeCuota) {
        boolean validacion = false;
        try {
            u.clear(importecuota);
            u.sendKeys(importecuota, importeCuota);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));
            testCase.addScreenShotCurrentStep(u, "Se ingresó el importe de cuota de manera correcta");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingreesar el importe cuota" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para ingresar el total adeudado.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @param totalAdeudado Valor del total adeudado a ingresar
     * @throws Exception
     */
    public boolean ingresarTotalAdeudado(String totalAdeudado) {
        boolean validacion = false;
        try {
            u.clear(totaladeudado);
            u.sendKeys(totaladeudado, totalAdeudado);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));
            testCase.addScreenShotCurrentStep(u, "Se ingresó el total adeudado de manera correcta");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar el total adeudado" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para ingresar el importe.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @param importe Valor del importe a ingresar
     * @throws Exception
     */
    public boolean ingresarImporte(String importe) {
        boolean validacion = false;
        try {
            u.clear(ingresarimporte);
            u.sendKeys(ingresarimporte, importe);
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));
            testCase.addScreenShotCurrentStep(u, "Se ingresó el importe de manera correcta");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró ingresar el importe" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para guardar la incidencia.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @throws Exception
     */
    public boolean guardarIncidencia() {
        boolean validacion = false;
        try {
            u.click(guardarincidencia);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            testCase.addScreenShotCurrentStep(u, "Se guardó correctamente la incidencia");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró guardar la incidencia" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para seleccionar el día donde se agregó la incidencia y validar la alerta de descanso del empleado.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @param numeroFila     Fila de la celda en la tabla
     * @param numeroColumna  Columna de la celda en la tabla
     * @param tipoIncidencia Tipo de incidencia registrada
     * @throws Exception
     */
    public boolean validarVisualizacionDeIncidencia(String numeroFila, String numeroColumna, String tipoIncidencia) {
        boolean validacion = false;
        try {
        	By celda = getCeldaTabla(numeroFila, numeroColumna);
            Thread.sleep(800);
            WebElement elementoCelda = u.waitWebElement(celda, 30, 5);

            if (elementoCelda == null) {
                throw new Exception("No se encontró la celda en el HTML.");
            }

            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript(ARGUMENTOS_CLICK, elementoCelda);

            if (tipoIncidencia != null && tipoIncidencia.trim().equalsIgnoreCase(INCIDENCIA_PRESENCIAS)) {
                try {
                    Thread.sleep(1800);
                    Alert alert = u.getDriver().switchTo().alert();
                    String mensajeAlert = alert.getText().trim();
                    alert.accept();
                    System.out.println(mensajeAlert.contains("Alerta de Día de descanso del empleado")
                        ? "La Alerta de Día de descanso fue aceptada"
                        : "Una alerta distinta fue aceptada");
                } catch (Exception e) {
                    System.out.println("No se detectó alerta aunque era Presencias");
                }
            } else {
                System.out.println("El Tipo de incidencia: " + tipoIncidencia + " No aparece alerta");
            }

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));
            testCase.addScreenShotCurrentStep(u, "Se seleccionó el Día a cargar de manera correcta");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se genero la evidencia creada" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para dar clic en detalle de incidencia, validar que se activen los campos correctos
     * según el tipo de incidencia y que los campos tengan información.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @param tipoIncidencia Tipo de incidencia registrada
     * @throws Exception
     */
    public boolean verDetalleIncidencia(String tipoIncidencia) {
        boolean validacion = false;
        JavascriptExecutor jsExecutor = (JavascriptExecutor) u.getDriver();
        try {
            u.waitWebElement(verdetalleincidencia, 20, 4);
            u.click(verdetalleincidencia);

            WebElement elementotipodeincidencia = u.waitWebElement(tipodeincidencia, 30, 5);
            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript(ARGUMENTOS_SCROLL, elementotipodeincidencia);

            if (elementotipodeincidencia == null) {
                throw new Exception("El formulario de detalle de incidencia no apareció.");
            }

            Thread.sleep(1000);

            String tipo = u.getDriver().findElement(tipodeincidencia).getText().trim();
            String subtipo = u.getDriver().findElement(subtipodeincidencia).getText().trim();
            String cantidadHoras = u.getDriver().findElement(cantidadhoras).getText().trim();
            String cantidadMinutos = u.getDriver().findElement(cantidadminutos).getText().trim();

            // Extracción limpia usando método auxiliar
            String justificacion = obtenerTextoJs(justificaciondelacarga, jsExecutor);
            String importeValor = obtenerTextoJs(ingresarimporte, jsExecutor);
            String importeCuota = obtenerTextoJs(importecuota, jsExecutor);
            String totalAdeudado = obtenerTextoJs(totaladeudado, jsExecutor);

            // Agrupamos en un arreglo para reducir el número de parámetros
            String[] valoresDetalle = { cantidadHoras, cantidadMinutos, importeValor, importeCuota, totalAdeudado };

            boolean camposCorrectos = validarCamposPorTipo(tipoIncidencia, tipo, subtipo, justificacion, valoresDetalle);

            if (!camposCorrectos) {
                throw new Exception("Los campos de detalle no cargaron información correctamente para el tipo: " + tipoIncidencia);
            }

            testCase.addScreenShotCurrentStep(u, "Se visualizó correctamente el detalle de la incidencia");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró visualizar el detalle de la incidencia " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para validar que el mensaje de la incidencia agregada es el correcto.
     *
     * @author Carlos Martínez Ortega
     * @date 26/Mayo/2026
     * @throws Exception
     */
    public boolean validarMensajeListaDeEspera() {
        boolean validacion = false;
        try {
            String mensajeReal = u.getText(mensajeRetornoDetalle).trim();
            String mensajeEsperado = MENSAJE_ESPERADO;

            if (!mensajeReal.contains(mensajeEsperado) && !mensajeReal.equals(mensajeEsperado)) {
                throw new Exception("El mensaje esperado no apareció. Se esperaba: '" + mensajeEsperado +
                                    "' pero se obtuvo: '" + mensajeReal + "'");
            }

            testCase.addScreenShotCurrentStep(u, "Se valida la carga de incidencia correcta");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("El mensaje no es el valido " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para seleccionar el botón editar y validar que la incidencia habilite los campos correctos.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @param tipoIncidencia Tipo de incidencia a editar
     * @throws Exception
     */
    public boolean seleccionarEditar(String tipoIncidencia) {
        boolean validacion = false;
        try {
            u.waitWebElement(editarincidencia, 25, 4);
            u.click(editarincidencia);

            WebElement elementotipodeincidencia = u.waitWebElement(tipodeincidencia, 30, 5);
            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript(ARGUMENTOS_SCROLL, elementotipodeincidencia);
            System.out.println("Scroll nativo ejecutado correctamente.");

            boolean mensajeDetectado = false;
            try {
                u.waitWebElement(mensajeRetornoDetalle, 20, 4);
                String mensajeActual = u.getText(mensajeRetornoDetalle).trim();
                if (mensajeActual.contains(MENSAJE_ESPERADO)) {
                    mensajeDetectado = true;
                } else {
                    throw new Exception("La Petición ya fue Actualizada. No se puede editar.");
                }
            } catch (Exception ignored) {
                // No apareció el mensaje
            }

            if (!mensajeDetectado) {
                throw new Exception("No se desplegó la información del campo a editar (mensaje de estado no encontrado).");
            }

            boolean tipoHabilitado = u.getDriver().findElement(tipodeincidencia).isEnabled();
            boolean subtipoHabilitado = u.getDriver().findElement(subtipodeincidencia).isEnabled();
            boolean justificacionHabilitada = u.getDriver().findElement(justificaciondelacarga).isEnabled();

            if (!tipoHabilitado || !subtipoHabilitado || !justificacionHabilitada) {
                throw new Exception("Los campos de edición no se habilitaron correctamente para " + tipoIncidencia);
            }

            testCase.addScreenShotCurrentStep(u, "Se abrió correctamente la edición para " + tipoIncidencia);
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logro dar clic en editar " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para editar los campos subtipo de incidencia y justificación.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @param nuevoSubtipoIncidencia Nuevo subtipo de incidencia a seleccionar
     * @param nuevaJustificacion     Nueva justificación a ingresar
     * @throws Exception
     */
    public boolean editarInformacion(String nuevoSubtipoIncidencia, String nuevaJustificacion) {
        boolean validacion = false;
        try {
            u.waitWebElement(subtipodeincidencia, 25, 4);
            u.getDriver().findElement(subtipodeincidencia).click();
            Thread.sleep(1000);
            u.getDriver().findElement(subtipodeincidencia).sendKeys(nuevoSubtipoIncidencia);
            Thread.sleep(800);
            u.getDriver().findElement(subtipodeincidencia).sendKeys(Keys.ENTER);

            u.waitWebElement(justificaciondelacarga, 25, 4);
            u.getDriver().findElement(justificaciondelacarga).click();
            Thread.sleep(500);
            u.getDriver().findElement(justificaciondelacarga).sendKeys(Keys.chord(Keys.CONTROL, "a"));
            u.getDriver().findElement(justificaciondelacarga).sendKeys(Keys.DELETE);
            Thread.sleep(500);
            u.getDriver().findElement(justificaciondelacarga).sendKeys(nuevaJustificacion);

            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));
            testCase.addScreenShotCurrentStep(u, "Se editó correctamente el Subtipo y Justificación");

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró editar la información del registro " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para validar que los campos se editaron correctamente y aparezca el mensaje correcto.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @param nuevoSubtipoIncidencia Subtipo de incidencia esperado tras la edición
     * @param nuevaJustificacion     Justificación esperada tras la edición
     * @throws Exception
     */
    public boolean validarCamposEditados(String nuevoSubtipoIncidencia, String nuevaJustificacion) {
        boolean validacion = false;
        JavascriptExecutor jsExecutor = (JavascriptExecutor) u.getDriver();
        try {
            WebElement elementotipodeincidencia = u.waitWebElement(tipodeincidencia, 30, 5);
            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript(ARGUMENTOS_SCROLL, elementotipodeincidencia);
            System.out.println("Scroll nativo ejecutado correctamente.");

            String subtipoActual = u.getDriver().findElement(subtipodeincidencia).getText().trim();
            String justificacionActual = "";
            if (u.waitWebElement(justificaciondelacarga, 5, 1) != null) {
                WebElement laJustificacion = u.getDriver().findElement(justificaciondelacarga);
                justificacionActual = (String) jsExecutor.executeScript(ARGUMENTOS_RETORNO, laJustificacion);
                justificacionActual = (justificacionActual != null) ? justificacionActual.trim() : "";
            }

            boolean subtipoCorrecto = subtipoActual.contains(nuevoSubtipoIncidencia) ||
                                      subtipoActual.equalsIgnoreCase(nuevoSubtipoIncidencia);
            boolean justificacionCorrecta = justificacionActual.contains(nuevaJustificacion);

            u.waitWebElement(mensajeRetornoDetalle, 15, 4);
            String mensajeActual = u.getText(mensajeRetornoDetalle).trim();
            boolean mensajeCorrecto = mensajeActual.contains(MENSAJE_ESPERADO);

            if (!subtipoCorrecto || !justificacionCorrecta || !mensajeCorrecto) {
                throw new Exception("La validación de campos editados falló. " +
                                    "Subtipo: '" + subtipoActual + "' | " +
                                    "Justificación: '" + justificacionActual + "' | Mensaje: '" + mensajeActual + "'");
            }

            testCase.addScreenShotCurrentStep(u, "Se validó correctamente la edición de la incidencia y el mensaje de lista de espera");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró validar la edición de los registros" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para seleccionar el día en el que se tiene una incidencia registrada para eliminar.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @param numeroFila    Fila de la celda en la tabla
     * @param numeroColumna Columna de la celda en la tabla
     * @throws Exception
     */
    public boolean seleccionarDiaAEliminar(String numeroFila, String numeroColumna) {
        boolean validacion = false;
        try {
        	By celda = getCeldaTabla(numeroFila, numeroColumna);
            WebElement elementoCelda = u.waitWebElement(celda, 25, 4);

            if (elementoCelda == null) {
                throw new Exception("No se encontró la celda en el HTML.");
            }

            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript(ARGUMENTOS_CLICK, elementoCelda);
            
            String textoCelda = elementoCelda.getText().trim();

            if (textoCelda.isEmpty()) {
                throw new Exception("No se tiene incidencia registrada en la celda seleccionada (fila " + numeroFila +
                		COLUMNA_INCIDENCIA + numeroColumna + ").");
            }

            if (textoCelda.contains(INCIDENCIA_PRESENCIAS)) {
                try {
                    Thread.sleep(1500);
                    Alert alert = u.getDriver().switchTo().alert();
                    String mensajeAlert = alert.getText().trim();
                    alert.accept();
                    System.out.println(mensajeAlert.contains("El Día de descanso del empleado")
                        ? "Alerta por Día de descanso aceptada"
                        : "Una alerta diferente fue aceptada");
                } catch (Exception e) {
                    System.out.println("No se detectó alerta aunque la celda es Presencias");
                }
            } else {
                System.out.println("El Tipo de incidencia es: " + textoCelda + " No se espera que aparezca alguna alerta");
            }

            testCase.addScreenShotCurrentStep(u, "Se seleccionó correctamente el día a eliminar");
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró seleccionar el día a eliminar " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para dar clic al botón de eliminar y validar el mensaje de confirmación.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @throws Exception
     */
    public boolean seleccionarEliminar() {
        boolean validacion = false;
        try {
            u.waitWebElement(eliminarincidencia, 25, 4);
            u.click(eliminarincidencia);
            Thread.sleep(2000);

            Alert alert = u.getDriver().switchTo().alert();
            String mensajeAlert = alert.getText().trim();

            String textoEsperado = "¿Desea eliminar la incidencia seleccionada?";

            if (!mensajeAlert.contains(textoEsperado)) {
                throw new Exception("Alerta diferente a la esperada. Se obtuvo: '" + mensajeAlert + "'");
            }

            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró dar clic en eliminar" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    /**
     * Método para aceptar la confirmación del eliminar.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @throws Exception
     */
    public boolean seleccionarAceptar() {
        boolean validacion = false;
        try {
            Thread.sleep(1500);
            Alert alert = u.getDriver().switchTo().alert();
            String mensajeAlert = alert.getText().trim();
            System.out.println("Aceptando alerta: " + mensajeAlert);
            alert.accept();
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(guardarincidencia));
            testCase.addScreenShotCurrentStep(u, "Se aceptó la ventana de confirmación de eliminación");
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logro dar clic en el botón Aceptar" + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }


    /**
     * Método para validar que la incidencia del día seleccionado se eliminó correctamente.
     *
     * @author Carlos Martínez Ortega
     * @date 27/Mayo/2026
     * @param numeroFila    Fila de la celda en la tabla
     * @param numeroColumna Columna de la celda en la tabla
     * @throws Exception
     */
    public boolean validarIncidenciaEliminada(String numeroFila, String numeroColumna) {
        boolean validacion = false;
        try {
        	By celda = getCeldaTabla(numeroFila, numeroColumna);
            u.waitWebElement(celda, 25, 4);
            String textoCelda = u.getDriver().findElement(celda).getText().trim();

            if (!textoCelda.isEmpty()) {
                throw new Exception("La incidencia NO fue eliminada. Aún aparece texto en la celda: '" + textoCelda + "'");
            }

            testCase.addScreenShotCurrentStep(u, "Se validó correctamente la eliminación de la incidencia en fila " + numeroFila);
            validacion = true;
        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("No se logró eliminar la incidencia " + e.getMessage());
            e.printStackTrace();
        }
        return validacion;
    }

    // =========================================================================
    // Métodos Auxiliares
    // =========================================================================

    private String obtenerTextoJs(By locator, JavascriptExecutor jsExecutor) {
        if (u.waitWebElement(locator, 5, 1) != null) {
            WebElement element = u.getDriver().findElement(locator);
            String valor = (String) jsExecutor.executeScript(ARGUMENTOS_RETORNO, element);
            return (valor != null) ? valor.trim() : "";
        }
        return "";
    }

    private boolean validarCamposPorTipo(String tipoIncidencia, String t, String s, String j, String[] datos) {
        if (t.isEmpty() || s.isEmpty() || j.isEmpty()) {
            return false;
        }
        
        // datos[0] = cantidadHoras, datos[1] = cantidadMinutos
        // datos[2] = importeValor, datos[3] = importeCuota, datos[4] = totalAdeudado
        switch (tipoIncidencia.toUpperCase()) {
            case "PRESENCIAS":
            case "HORAS EXTRAS":
                return !datos[0].isEmpty() && !datos[1].isEmpty();
            case "PAGOS COMPLEMENTARIOS":
                return !datos[2].isEmpty();
            case "DESCUENTOS":
                return !datos[3].isEmpty() && !datos[4].isEmpty();
            case "ABSENTISMOS":
            case "VACACIONES":
            default:
                return true;
        }
    }
    
    public By getCeldaTabla(String fila, String columna) {
        return By.xpath("//tbody/tr[" + fila + "]/td[" + columna + "]");
    }


    
    
}
