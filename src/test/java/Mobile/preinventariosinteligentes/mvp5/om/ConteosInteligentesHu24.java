package Mobile.preinventariosinteligentes.mvp5.om;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.Duration;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;

import Mobile.preinventariosinteligentes.om.ConteosInteligentes;
import modelo.TestCase;
import utils.AppiumUtil;

/**
 * Acciones y consultas atomicas de Conteos Inteligentes exclusivas de HU24.
 * Los selectores provienen de dumps reales del build 218.
 */
public final class ConteosInteligentesHu24 {
    private final TestCase testCase;
    private final AppiumUtil appium;
    private final ConteosInteligentes conteosLegado;
    private final String nombrePaquete;
    private final Duration tiempoEspera;

    public ConteosInteligentesHu24(
            TestCase testCase,
            AppiumUtil appium,
            ConteosInteligentes conteosLegado,
            String nombrePaquete,
            Duration tiempoEspera) {
        this.testCase = testCase;
        this.appium = appium;
        this.conteosLegado = conteosLegado;
        this.nombrePaquete = nombrePaquete;
        this.tiempoEspera = tiempoEspera;
    }

    /** Posiciona el selectivo objetivo sin modificar su estado expandido. */
    public void posicionarSelectivoSinExpandir(String encabezado) {
        posicionarSelectivo(encabezado);
    }

    /** Deja expandido exclusivamente el selectivo indicado por su encabezado. */
    public void expandirSelectivo(String encabezado, boolean screenShot) {
        WebElement titulo = posicionarSelectivo(encabezado);
        By localizadorEstado = localizadorEstadoSelectivo(
                titulo.getText().trim());
        WebElement estado = visible(localizadorEstado);
        if ("+".equals(estado.getText().trim())) {
            titulo.click();
        }
        espera().until(controlador -> controlador.findElements(localizadorEstado)
                .stream()
                .anyMatch(elemento -> "-"
                        .equals(elemento.getText().trim())));
        tomarCaptura(screenShot);
    }

    /** Obtiene el encabezado del selectivo que se encuentra expandido. */
    public String obtenerSelectivoExpandido(boolean screenShot) {
        String xpath = "//*[@resource-id="
                + literalXpath(idRecurso("tv_group_name_preinventory"))
                + " and preceding-sibling::*[@resource-id="
                + literalXpath(idRecurso("tv_state_visibility"))
                + " and @text='-']]";
        List<WebElement> titulos = appium.driver.findElements(By.xpath(xpath));
        tomarCaptura(screenShot);
        return titulos.isEmpty() ? null : titulos.get(0).getText().trim();
    }

    /** Compara el encabezado expandido tolerando solo diferencias de acentos. */
    public boolean selectivoExpandidoCoincide(
            String encabezadoEsperado,
            boolean screenShot) {
        String encabezadoVisible = obtenerSelectivoExpandido(false);
        boolean coincide = encabezadoVisible != null
                && normalizarTexto(encabezadoVisible)
                        .equals(normalizarTexto(encabezadoEsperado));
        tomarCaptura(screenShot);
        return coincide;
    }

    /** Obtiene el colaborador asignado mostrado por la aplicacion. */
    public String obtenerColaboradorAsignado(boolean screenShot) {
        String texto = visible(By.id(idRecurso("tv_responsible")))
                .getText().trim();
        tomarCaptura(screenShot);
        return texto.replaceFirst("^Asignado a:\\s*", "").trim();
    }

    /**
     * Captura por codigo de barras todos los pendientes usando el recorrido
     * secuencial estable del OM legado y conserva una evidencia final.
     */
    public void capturarConteosPendientes(
            ResultSet pendientes,
            String cantidad,
            boolean screenShot) throws SQLException {
        conteosLegado.ingresarConteo(
                pendientes,
                cantidad,
                By.id(idRecurso("btn_count_product_save")));
        tomarCaptura(screenShot);
    }

    /** Abre mediante tap la tarjeta de un unico SKU previamente identificado. */
    public void abrirSkuPorTap(String sku, boolean screenShot) {
        By tarjeta = By.xpath(xpathTarjetaSku(sku));
        desplazarHasta(
                tarjeta,
                selectorPorTexto("tv_sku", "SKU: " + sku),
                "la tarjeta del SKU " + sku).click();
        visible(By.id(idRecurso("edt_find_sku_product")));
        tomarCaptura(screenShot);
    }

    /** Obtiene el SKU mostrado dentro del modal abierto. */
    public String obtenerSkuModal(boolean screenShot) {
        String sku = visible(By.id(idRecurso("tv_pre_sku")))
                .getText().replace("SKU:", "").trim();
        tomarCaptura(screenShot);
        return sku;
    }

    /** Obtiene el fisico acumulado previo mostrado por tvQtyCurrent. */
    public BigDecimal obtenerFisicoActualModal(boolean screenShot) {
        String texto = visible(By.id(idRecurso("tvQtyCurrent")))
                .getText().replace("+", "").trim();
        tomarCaptura(screenShot);
        return new BigDecimal(texto);
    }

    /** Consulta que el campo de nueva captura permanezca habilitado. */
    public boolean capturaFisicaEditable(boolean screenShot) {
        WebElement entrada = visible(By.id(idRecurso("edt_find_sku_product")));
        boolean editable = entrada.isDisplayed() && entrada.isEnabled();
        tomarCaptura(screenShot);
        return editable;
    }

    /** Captura una cantidad adicional y conserva evidencia dentro del OM. */
    public void capturarCantidadAdicional(String cantidad, boolean screenShot) {
        conteosLegado.capturarConteo("entero", cantidad, true, true);
        tomarCaptura(screenShot);
    }

    /** Captura el primer fisico de un SKU pendiente y guarda el modal. */
    public void capturarCantidadInicial(String cantidad, boolean screenShot) {
        conteosLegado.capturarConteo("entero", cantidad, false, true);
        tomarCaptura(screenShot);
        clickeable(By.id(idRecurso("btn_count_product_save"))).click();
    }

    /** Obtiene el fisico visible en la tarjeta del SKU indicado. */
    public BigDecimal obtenerFisicoTarjeta(String sku, boolean screenShot) {
        String xpathTarjeta = xpathTarjetaSku(sku);
        desplazarHasta(
                By.xpath(xpathTarjeta),
                selectorPorTexto("tv_sku", "SKU: " + sku),
                "la tarjeta del SKU " + sku);
        List<WebElement> valores = appium.driver.findElements(
                By.xpath(xpathTarjeta + "/android.widget.TextView"));
        if (valores.size() != 4) {
            throw new IllegalStateException(
                    "La tarjeta del SKU " + sku
                            + " no conserva los cuatro campos observados");
        }
        BigDecimal fisico = new BigDecimal(
                valores.get(2).getText().trim());
        tomarCaptura(screenShot);
        return fisico;
    }

    /** Consulta que el SKU indicado conserve guion en la columna Fisicos. */
    public boolean fisicoPendienteVisible(String sku, boolean screenShot) {
        desplazarHasta(
                By.xpath(xpathTarjetaSku(sku)),
                selectorPorTexto("tv_sku", "SKU: " + sku),
                "la tarjeta del SKU " + sku);
        By pendiente = By.xpath(xpathTarjetaSku(sku)
                + "/android.widget.TextView[3][@text='-']");
        boolean visible = appium.driver.findElements(pendiente).stream()
                .anyMatch(WebElement::isDisplayed);
        tomarCaptura(screenShot);
        return visible;
    }

    /** Consulta que exista otro SKU visible cuyo Fisico permanezca pendiente. */
    public boolean existeOtroSkuPendiente(String skuContado, boolean screenShot) {
        String xpath = "//*[@resource-id=" + literalXpath(idRecurso("ct_product"))
                + " and .//*[@resource-id=" + literalXpath(idRecurso("tv_sku"))
                + " and @text!=" + literalXpath("SKU: " + skuContado) + "]"
                + " and ./android.widget.TextView[3][@text='-']]";
        boolean existe = appium.driver.findElements(By.xpath(xpath)).stream()
                .anyMatch(WebElement::isDisplayed);
        tomarCaptura(screenShot);
        return existe;
    }

    /** Observa el mensaje transitorio posterior al guardado del articulo. */
    public boolean articuloContabilizadoVisible(boolean screenShot) {
        By mensajeToast = By.xpath("//android.widget.Toast["
                + "@text=" + literalXpath("Art\u00edculo Contabilizado")
                + " or @text=" + literalXpath("Art\u00edculo contabilizado")
                + "]");
        boolean encontrado;
        try {
            new WebDriverWait(appium.driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(mensajeToast));
            encontrado = true;
        } catch (org.openqa.selenium.TimeoutException noVisible) {
            encontrado = false;
        }
        tomarCaptura(screenShot);
        return encontrado;
    }

    /** Consulta texto e ID para detectar cualquier control de finalizacion. */
    public boolean sinControlesFinalizacion(boolean screenShot) {
        boolean ausente = appium.driver.findElements(
                By.id(idRecurso("btn_finish_preinventory"))).isEmpty()
                && appium.driver.findElements(
                        By.xpath("//*[@text='FINALIZAR' or @text='FINALIZAR CONTEO']"))
                        .isEmpty();
        tomarCaptura(screenShot);
        return ausente;
    }

    /** Comprueba la distribucion CANCELAR izquierda y GUARDAR derecha. */
    public boolean guardarEstaDerechaDeCancelar(boolean screenShot) {
        WebElement cancelar = visible(By.id(idRecurso("btn_cancel_preinventory")));
        WebElement guardar = visible(By.id(idRecurso("btn_save_preinventory")));
        Point posicionCancelar = cancelar.getLocation();
        Point posicionGuardar = guardar.getLocation();
        boolean posicionCorrecta = posicionGuardar.getX() > posicionCancelar.getX()
                && posicionGuardar.getY() >= posicionCancelar.getY();
        tomarCaptura(screenShot);
        return posicionCorrecta;
    }

    /** Posiciona el listado en el control global GUARDAR del selectivo. */
    public void desplazarAGuardarSelectivo(boolean screenShot) {
        desplazarHasta(
                By.id(idRecurso("btn_save_preinventory")),
                selectorPorRecurso("btn_save_preinventory"),
                "el control global GUARDAR del selectivo");
        tomarCaptura(screenShot);
    }

    /** Desplaza el RecyclerView hasta el pie del selectivo expandido. */
    public void desplazarAlFinalSelectivo(boolean screenShot) {
        desplazarHasta(
                By.id(idRecurso("print")),
                selectorPorRecurso("print"),
                "el pie IMPRIMIR del selectivo");
        tomarCaptura(screenShot);
    }

    /** Consulta que IMPRIMIR sea la referencia visible del final del selectivo. */
    public boolean imprimirVisible(boolean screenShot) {
        List<WebElement> controles = appium.driver.findElements(
                By.id(idRecurso("print")));
        boolean visible = !controles.isEmpty()
                && controles.get(0).isDisplayed()
                && "IMPRIMIR".equals(controles.get(0).getText().trim());
        tomarCaptura(screenShot);
        return visible;
    }

    /** Observa el mensaje de guardado mediante el metodo legacy y toma evidencia. */
    public boolean guardadoExitosoVisible(Duration timeout, boolean screenShot) {
        boolean visible = conteosLegado.retornaEstadoAutoGuardado(timeout);
        tomarCaptura(screenShot);
        return visible;
    }

    private WebElement posicionarSelectivo(String encabezado) {
        By titulos = By.id(idRecurso("tv_group_name_preinventory"));
        String esperado = normalizarTexto(encabezado);
        String nombreEsperado = nombreSelectivo(encabezado);
        int totalEsperado = totalSelectivo(encabezado);

        try {
            desplazarConUiScrollable(selectorPorTexto(
                    "tv_group_name_preinventory",
                    encabezado.toUpperCase(Locale.ROOT)));
        } catch (NoSuchElementException noExacto) {
            // Fallback: en algunos builds el sufijo de SKU cambia (SKU/SKUS).
            desplazarConUiScrollable(selectorPorTextoContiene(
                    "tv_group_name_preinventory",
                    nombreEsperado.toUpperCase(Locale.ROOT)));
        }
        for (WebElement titulo : appium.driver.findElements(titulos)) {
            if (!titulo.isDisplayed()) {
                continue;
            }
            String visible = normalizarTexto(titulo.getText());
            if (visible.equals(esperado)
                    || coincideSelectivoFlexible(
                            visible,
                            normalizarTexto(nombreEsperado),
                            totalEsperado)) {
                return titulo;
            }
        }
        throw new NoSuchElementException(
                "No se encontro el selectivo " + encabezado
                        + " despues del desplazamiento nativo");
    }

    /** Localiza el objetivo con el motor nativo del RecyclerView. */
    private WebElement desplazarHasta(
            By localizador,
            String selectorObjetivo,
            String descripcion) {
        for (WebElement elemento : appium.driver.findElements(localizador)) {
            if (elemento.isDisplayed()) {
                return elemento;
            }
        }
        desplazarConUiScrollable(selectorObjetivo);
        for (WebElement elemento : appium.driver.findElements(localizador)) {
            if (elemento.isDisplayed()) {
                return elemento;
            }
        }
        throw new NoSuchElementException("No se encontro " + descripcion
                + " despues del desplazamiento nativo");
    }

    private void desplazarConUiScrollable(String selectorObjetivo) {
        visible(By.id(idRecurso("rv_group_products")));
        String expresion = "new UiScrollable(new UiSelector().resourceId(\""
                + escaparUiAutomator(idRecurso("rv_group_products"))
                + "\")).setAsVerticalList().setMaxSearchSwipes(60)"
                + ".scrollIntoView(" + selectorObjetivo + ")";
        appium.driver.findElement(AppiumBy.androidUIAutomator(expresion));
    }

    private String selectorPorRecurso(String recurso) {
        return "new UiSelector().resourceId(\""
                + escaparUiAutomator(idRecurso(recurso)) + "\")";
    }

    private String selectorPorTexto(String recurso, String texto) {
        return selectorPorRecurso(recurso) + ".text(\""
                + escaparUiAutomator(texto) + "\")";
    }

    private String selectorPorTextoContiene(String recurso, String texto) {
        return selectorPorRecurso(recurso) + ".textContains(\""
                + escaparUiAutomator(texto) + "\")";
    }

    private static String nombreSelectivo(String encabezado) {
        int separador = encabezado.lastIndexOf(" - ");
        return separador > 0 ? encabezado.substring(0, separador).trim() : encabezado.trim();
    }

    private static int totalSelectivo(String encabezado) {
        int separador = encabezado.lastIndexOf(" - ");
        if (separador <= 0) {
            return -1;
        }
        String cola = encabezado.substring(separador + 3).trim();
        String[] partes = cola.split("\\s+");
        if (partes.length == 0) {
            return -1;
        }
        try {
            return Integer.parseInt(partes[0]);
        } catch (NumberFormatException invalido) {
            return -1;
        }
    }

    private static boolean coincideSelectivoFlexible(
            String visibleNormalizado,
            String nombreEsperadoNormalizado,
            int totalEsperado) {
        if (!visibleNormalizado.startsWith(nombreEsperadoNormalizado + " -")) {
            return false;
        }
        if (totalEsperado < 0) {
            return true;
        }
        return visibleNormalizado.matches(".*\\b" + totalEsperado + "\\b\\s*SKU(S)?\\b.*");
    }

    private static String escaparUiAutomator(String valor) {
        return valor.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    private By localizadorEstadoSelectivo(String encabezado) {
        return By.xpath("//*[@resource-id="
                + literalXpath(idRecurso("tv_group_name_preinventory"))
                + " and @text=" + literalXpath(encabezado) + "]"
                + "/preceding-sibling::*[@resource-id="
                + literalXpath(idRecurso("tv_state_visibility")) + "]");
    }

    private String xpathTarjetaSku(String sku) {
        return "//*[@resource-id=" + literalXpath(idRecurso("ct_product"))
                + " and .//*[@resource-id="
                + literalXpath(idRecurso("tv_sku"))
                + " and @text=" + literalXpath("SKU: " + sku) + "]]";
    }

    private WebElement visible(By localizador) {
        return espera().until(ExpectedConditions.visibilityOfElementLocated(localizador));
    }

    private WebElement clickeable(By localizador) {
        return espera().until(ExpectedConditions.elementToBeClickable(localizador));
    }

    private WebDriverWait espera() {
        return new WebDriverWait(appium.driver, tiempoEspera);
    }

    private String idRecurso(String recurso) {
        return nombrePaquete + ":id/" + recurso;
    }

    private void tomarCaptura(boolean screenShot) {
        if (screenShot) {
            testCase.addScreenShotCurrentStep(appium, "testImage");
        }
    }

    private static String literalXpath(String valor) {
        if (!valor.contains("'")) {
            return "'" + valor + "'";
        }
        if (!valor.contains("\"")) {
            return "\"" + valor + "\"";
        }
        throw new IllegalArgumentException("El valor no puede representarse como literal XPath");
    }

    private static String normalizarTexto(String valor) {
        return Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .trim()
                .toUpperCase(Locale.ROOT);
    }
}
