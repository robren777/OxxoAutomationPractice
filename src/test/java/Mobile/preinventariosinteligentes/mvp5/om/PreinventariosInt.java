package Mobile.preinventariosinteligentes.mvp5.om;

import java.sql.Connection;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.appmanagement.ApplicationState;
import io.appium.java_client.android.AndroidDriver;
import modelo.TestCase;
import utils.AppiumUtil;

/**
 * Operaciones nuevas o adaptadas para MVP5. Los flujos estables permanecen
 * en los OM legacy; esta clase concentra diferencias introducidas por MVP5.
 */
public final class PreinventariosInt {
    public static final String MENSAJE_CONSOLIDANDO =
            "Se est\u00e1 realizando la consolidaci\u00f3n";
    public static final String MENSAJE_CONSOLIDANDO_ESPERADO =
            MENSAJE_CONSOLIDANDO + ".";
    public static final String MENSAJE_CONSOLIDACION_EXITOSA =
            "Consolidaci\u00f3n realizada con \u00e9xito";
    public static final String MENSAJE_ERROR_CONSOLIDACION =
            "Ocurri\u00f3 un error durante la consolidaci\u00f3n. Favor de intentar nuevamente m\u00e1s tarde.";

    private final AppiumUtil appium;
    private final TestCase testCase;
    private final String nombrePaquete;
    private final Duration tiempoEspera;
    private final AccesoApp accesoApp;
    private final ConexionBd conexionBd;

    public PreinventariosInt(
            TestCase testCase,
            AppiumUtil appium,
            String nombrePaquete,
            Duration tiempoEspera) {
        this.testCase = testCase;
        this.appium = appium;
        this.nombrePaquete = nombrePaquete;
        this.tiempoEspera = tiempoEspera;
        this.accesoApp = new AccesoApp(appium, nombrePaquete, tiempoEspera);
        this.conexionBd = new ConexionBd();
    }

    // TAG_MVP5_ISOLATED_BOOTSTRAP_V1:
    // Navegacion aislada en MVP5 para no tocar comportamiento de OMs legacy.
    public void iniciarDesdeLoginYEntrarAConteosInteligentes(
            String appActivity, String usuario, String contrasena) {
        accesoApp.iniciarDesdeLoginYEntrarAConteosInteligentes(
                appActivity, usuario, contrasena);
    }

    public void iniciarDesdeLoginHastaMenuPreinventarios(
            String appActivity, String usuario, String contrasena) {
        accesoApp.iniciarDesdeLoginHastaMenuPreinventarios(
                appActivity, usuario, contrasena);
    }

    public void entrarAConteosInteligentes() {
        accesoApp.entrarAConteosInteligentes();
    }

    /** Navega a Conteos Inteligentes y conserva evidencia al terminar. */
    public void iniciarDesdeLoginYEntrarAConteosInteligentes(
            String appActivity,
            String usuario,
            String contrasena,
            boolean screenShot) {
        iniciarDesdeLoginYEntrarAConteosInteligentes(
                appActivity, usuario, contrasena);
        tomarCaptura(screenShot);
    }

    // TAG_MVP5_ISOLATED_BOOTSTRAP_V1:
    // Entrada aislada a Conteos Propios sin depender de OMs legacy.
    public void iniciarDesdeLoginYEntrarAConteosPropios(
            String appActivity, String usuario, String contrasena) {
        accesoApp.iniciarDesdeLoginYEntrarAConteosPropios(
                appActivity, usuario, contrasena);
    }

    public void entrarAConteosPropios() {
        accesoApp.entrarAConteosPropios();
    }

    /** Espera que el proceso automatico sea observable y que libere la pantalla. */
    public void esperarConsolidacionCompleta() {
        try {
            espera().until(controlador ->
                    consolidacionEnCurso()
                            || textoVisible(MENSAJE_CONSOLIDACION_EXITOSA)
                            || textoVisible(MENSAJE_ERROR_CONSOLIDACION)
                            || detalleConteoVisible());

            if (textoVisible(MENSAJE_ERROR_CONSOLIDACION)) {
                throw new AssertionError("La consolidacion termino con error");
            }
            if (consolidacionEnCurso()) {
                espera().until(controlador -> !consolidacionEnCurso());
            }
            if (textoVisible(MENSAJE_ERROR_CONSOLIDACION)) {
                throw new AssertionError("La consolidacion termino con error");
            }
        } catch (TimeoutException excepcion) {
            throw new AssertionError(
                    "La consolidacion no termino dentro del tiempo esperado",
                    excepcion);
        }
    }

    /** Espera cualquier variante observable que inicie con el literal normativo. */
    public void esperarInicioConsolidacion() {
        try {
            espera().until(controlador ->
                    obtenerMensajeConsolidacionVisible() != null);
        } catch (TimeoutException excepcion) {
            throw new AssertionError(
                    "No aparecio el mensaje de inicio de consolidacion",
                    excepcion);
        }
    }

    /**
     * Observa el inicio de consolidacion con polling rapido sin usar un gate duro.
     * Permite validar por mensaje inicial, segundo loading o toast exitoso.
     */
    public ResultadoInicioConsolidacion observarInicioConsolidacionNoBloqueante() {
        ResultadoInicioConsolidacion resultado =
                new ResultadoInicioConsolidacion();
        Duration esperaImplicita = appium.driver.manage().timeouts()
                .getImplicitWaitTimeout();
        try {
            appium.driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            WebDriverWait esperaCorta = new WebDriverWait(
                    appium.driver,
                    Duration.ofSeconds(2));
            esperaCorta.pollingEvery(Duration.ofMillis(100));
            try {
                esperaCorta.until(controlador -> {
                    if (resultado.mensajeLoading1 == null) {
                        String mensaje = obtenerMensajeConsolidacionVisible();
                        if (mensaje != null && !mensaje.trim().isEmpty()) {
                            resultado.mensajeLoading1 = mensaje;
                            resultado.loading1Valido =
                                    MENSAJE_CONSOLIDANDO_ESPERADO.equals(mensaje)
                                            || MENSAJE_CONSOLIDANDO.equals(mensaje);
                        }
                    }
                    if (textoVisibleRapido("Cargando")) {
                        resultado.loading2Observado = true;
                    }
                    if (notificacionConsolidacionExitosaVisibleRapida()) {
                        resultado.toastObservado = true;
                    }
                    resultado.accionesManualesAusentes =
                            sinAccionesManualesConsolidacion();
                    return resultado.inicioAutomatico();
                });
            } catch (TimeoutException timeoutEsperado) {
                // Mejor esfuerzo: conservar observaciones parciales sin fallar.
            }
        } finally {
            appium.driver.manage().timeouts().implicitlyWait(esperaImplicita);
        }
        return resultado;
    }

    /** Devuelve el texto real para que el script lo compare literalmente con la matriz. */
    public String obtenerMensajeConsolidacionVisible() {
        try {
            By mensajeConsolidando = AppiumBy.androidUIAutomator(
                    "new UiSelector().className(\"android.widget.TextView\")"
                            + ".textStartsWith(\""
                            + MENSAJE_CONSOLIDANDO + "\")");
            for (WebElement elemento : appium.driver.findElements(
                    mensajeConsolidando)) {
                if (elemento.isDisplayed()) {
                    return elemento.getText();
                }
            }
            return null;
        } catch (RuntimeException excepcion) {
            return null;
        }
    }

    /**
     * Espera el Toast exacto de exito solicitado por HU14.
     *
     * Android publica los Toast en una ventana accesible transitoria. Se valida
     * su presencia porque UiAutomator2 puede reportar isDisplayed=false aunque
     * el nodo android.widget.Toast sea visible en pantalla.
     */
    public boolean esperarNotificacionConsolidacionExitosa(boolean screenShot) {
        By notificacionExitosa = By.xpath("//android.widget.Toast["
                + "@text=" + literalXpath(MENSAJE_CONSOLIDACION_EXITOSA)
                + " or @content-desc="
                + literalXpath(MENSAJE_CONSOLIDACION_EXITOSA) + "]");
        boolean visible;
        try {
            espera().until(ExpectedConditions.presenceOfElementLocated(
                    notificacionExitosa));
            visible = true;
        } catch (TimeoutException excepcion) {
            visible = false;
        }
        tomarCaptura(screenShot);
        return visible;
    }

    /** Consulta rápida del Toast de éxito sin bloquear la ejecución. */
    public boolean notificacionConsolidacionExitosaVisibleRapida() {
        By notificacionExitosa = By.xpath("//android.widget.Toast["
                + "@text=" + literalXpath(MENSAJE_CONSOLIDACION_EXITOSA)
                + " or @content-desc="
                + literalXpath(MENSAJE_CONSOLIDACION_EXITOSA) + "]");
        Duration esperaImplicita = appium.driver.manage().timeouts()
                .getImplicitWaitTimeout();
        try {
            appium.driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            return !appium.driver.findElements(notificacionExitosa).isEmpty();
        } catch (RuntimeException excepcion) {
            return false;
        } finally {
            appium.driver.manage().timeouts().implicitlyWait(esperaImplicita);
        }
    }

    /** Espera y captura el segundo loading observable de la consolidacion. */
    public boolean esperarSegundoLoadingConsolidacion(boolean screenShot) {
        Duration esperaImplicita = appium.driver.manage().timeouts()
                .getImplicitWaitTimeout();
        boolean visible;
        try {
            appium.driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            WebDriverWait esperaSegundoLoading = new WebDriverWait(
                    appium.driver, Duration.ofSeconds(8));
            esperaSegundoLoading.pollingEvery(Duration.ofMillis(100));
            esperaSegundoLoading.until(
                    controlador -> textoVisibleRapido("Cargando"));
            visible = true;
        } catch (TimeoutException excepcion) {
            visible = false;
        } finally {
            appium.driver.manage().timeouts().implicitlyWait(esperaImplicita);
        }
        tomarCaptura(screenShot && visible);
        return visible;
    }

    /** Espera la consolidacion y conserva evidencia de la pantalla liberada. */
    public void esperarConsolidacionCompleta(boolean screenShot) {
        esperarConsolidacionCompleta();
        tomarCaptura(screenShot);
    }

    public void esperarErrorConsolidacion() {
        esperarTexto(MENSAJE_ERROR_CONSOLIDACION);
    }

    public boolean consolidacionEnCurso() {
        return obtenerMensajeConsolidacionVisible() != null;
    }

    public boolean mensajeConsolidacionSinAcciones() {
        return consolidacionEnCurso()
                && sinAccionesManualesConsolidacion();
    }

    /** Evalua solo la ausencia de acciones manuales en pantalla. */
    public boolean sinAccionesManualesConsolidacion() {
        return !textoVisibleRapido("ACEPTAR")
                && !textoVisibleRapido("CANCELAR");
    }

    public boolean interaccionBloqueadaDuranteConsolidacion() {
        return mensajeConsolidacionSinAcciones();
    }

    /** Confirma seleccion visible de la semana y habilitacion de CONTINUAR. */
    public boolean semanaEnCursoSeleccionadaYContinuarHabilitado(
            boolean screenShot) {
        boolean estadoListo;
        try {
            estadoListo = espera().until(controlador -> {
                boolean semanaSeleccionada = appium.driver
                        .findElements(id("rb_wkl_item"))
                        .stream()
                        .anyMatch(elemento -> elemento.isDisplayed()
                                && elemento.isEnabled()
                                && (elemento.isSelected()
                                        || Boolean.parseBoolean(
                                                elemento.getAttribute("checked"))));
                boolean continuarHabilitado = appium.driver
                        .findElements(id("btn_wkl_continue"))
                        .stream()
                        .anyMatch(elemento -> elemento.isDisplayed()
                                && elemento.isEnabled());
                return semanaSeleccionada
                        && continuarHabilitado
                        && textoVisible("En curso");
            });
        } catch (TimeoutException excepcion) {
            estadoListo = false;
        }
        tomarCaptura(screenShot);
        return estadoListo;
    }
    /** Confirma que el menú de Conteos Propios permite crear o iniciar conteos. */
    public boolean menuConteosPropiosDisponible(boolean screenShot) {
        boolean nuevaPlantillaDisponible = appium.driver
                .findElements(id("cd_products_template"))
                .stream()
                .anyMatch(elemento -> elemento.isDisplayed()
                        && elemento.isEnabled());
        boolean iniciarConteoDisponible = appium.driver
                .findElements(id("cd_init_own_flow"))
                .stream()
                .anyMatch(elemento -> elemento.isDisplayed()
                        && elemento.isEnabled());
        boolean disponible =
                nuevaPlantillaDisponible && iniciarConteoDisponible;
        tomarCaptura(screenShot);
        return disponible;
    }

    public void seleccionarPrimeraPlantillaPropia() {
        hacerClic(By.xpath("(//android.widget.TextView[@resource-id="
                + literalXpath(nombrePaquete + ":id/tv_template_name")
                + "])[1]"));
    }

    public void abrirPrimerProductoPropio() {
        hacerClic(By.xpath("(//android.widget.TextView[@resource-id="
                + literalXpath(nombrePaquete + ":id/tv_product")
                + "])[1]"));
    }

    public boolean fisicoMuestraCompleto() {
        return textoVisibleEnProductos("Completo");
    }

    public boolean fisicoMuestraGuionesIniciales() {
        return textoVisibleEnProductos("-")
                || textoVisibleEnProductos("--");
    }

    /** Lee Teoricos, Fisicos y Completo en la tarjeta de un SKU concreto. */
    public EstadoInicialSku observarEstadoInicialSku(
            String sku,
            boolean screenShot) {
        String textoSku = "SKU: " + sku;
        String xpathTarjeta = "//*[@resource-id="
                + literalXpath(nombrePaquete + ":id/ct_product")
                + " and .//*[@resource-id="
                + literalXpath(nombrePaquete + ":id/tv_sku")
                + " and @text=" + literalXpath(textoSku) + "]]";
        By tarjeta = By.xpath(xpathTarjeta);
        if (appium.driver.findElements(tarjeta).stream()
                .noneMatch(WebElement::isDisplayed)) {
            elementoVisible(id("rv_group_products"));
            desplazarHastaSku(textoSku);
        }
        WebElement fila = elementoVisible(tarjeta);
        List<WebElement> valores = fila.findElements(
                By.className("android.widget.TextView"));
        List<WebElement> columnas = new ArrayList<>();
        int mitadFila = fila.getLocation().getX()
                + (fila.getSize().getWidth() / 2);
        for (WebElement valor : valores) {
            if (valor.isDisplayed()
                    && valor.getLocation().getX() > mitadFila) {
                columnas.add(valor);
            }
        }
        columnas.sort(Comparator.comparingInt(
                valor -> valor.getLocation().getX()));
        if (columnas.size() != 2) {
            throw new IllegalStateException(
                    "La tarjeta del SKU " + sku
                            + " no conserva las columnas Teoricos y Fisicos esperadas");
        }
        EstadoInicialSku estado = new EstadoInicialSku(
                columnas.get(0).getText().trim(),
                columnas.get(1).getText().trim(),
                valores.stream().noneMatch(elemento -> "Completo"
                        .equalsIgnoreCase(elemento.getText().trim())));
        tomarCaptura(screenShot);
        return estado;
    }

    /** La ausencia se consulta sin registrar una excepcion esperada. */
    public boolean sinControlesFinalizacion() {
        return appium.driver.findElements(id("btn_finish_preinventory")).isEmpty()
                && !textoVisible("FINALIZAR")
                && !textoVisible("FINALIZAR CONTEO");
    }

    public boolean guardarEstaDerechaDeCancelar() {
        WebElement guardar = elementoVisible(id("btn_save_preinventory"));
        WebElement cancelar = elementoVisible(id("btn_cancel_preinventory"));
        Point posicionGuardar = guardar.getLocation();
        Point posicionCancelar = cancelar.getLocation();
        return posicionGuardar.getX() > posicionCancelar.getX()
                && posicionGuardar.getY() >= posicionCancelar.getY();
    }

    /** Confirma el regreso al listado de semanas de Conteos Inteligentes. */
    public boolean listaSemanasVisible(boolean screenShot) {
        boolean visible = appium.driver.findElements(id("btn_wkl_continue"))
                .stream()
                .anyMatch(WebElement::isDisplayed)
                && appium.driver.findElements(
                        By.xpath("//android.widget.TextView[@text='En curso']"))
                        .stream()
                        .anyMatch(WebElement::isDisplayed);
        tomarCaptura(screenShot);
        return visible;
    }

    public boolean productoEditable() {
        WebElement entrada = elementoVisible(id("edt_find_sku_product"));
        return entrada.isDisplayed() && entrada.isEnabled();
    }

    /** Confirma y evidencia que el modal o vista de captura admite conteo. */
    public boolean productoEditable(boolean screenShot) {
        boolean editable = productoEditable();
        tomarCaptura(screenShot);
        return editable;
    }

    /** Confirma que el detalle esta disponible una vez liberada la consolidacion. */
    public boolean detalleConteoHabilitado(boolean screenShot) {
        boolean habilitado = detalleConteoVisible();
        tomarCaptura(screenShot);
        return habilitado;
    }

    /** Espera que el modal de captura se cierre despues de guardar el articulo. */
    public boolean modalCapturaCerrado(boolean screenShot) {
        boolean cerrado;
        try {
            cerrado = espera().until(
                    ExpectedConditions.invisibilityOfElementLocated(
                            id("edt_find_sku_product")));
        } catch (TimeoutException excepcion) {
            cerrado = false;
        }
        tomarCaptura(screenShot);
        return cerrado;
    }

    /**
     * Valida en una sola observacion que el bloque de consolidacion no exponga
     * ninguna superficie capaz de iniciar o guardar un conteo.
     */
    public ResultadoBloqueoEstructural
            observarBloqueoEstructuralConsolidacion() {
        Duration esperaImplicita = appium.driver.manage().timeouts()
                .getImplicitWaitTimeout();
        try {
            appium.driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            boolean mensajeVisible = consolidacionEnCurso()
                    || textoVisibleRapido("Cargando");
            boolean sinAceptar = !textoVisibleRapido("ACEPTAR");
            boolean sinCancelar = !textoVisibleRapido("CANCELAR");
            // Conteos Propios conserva algunos controles renderizados debajo
            // del overlay. Mientras el proceso bloqueante sea visible, esos
            // controles no representan una superficie disponible al usuario.
            boolean sinSku = mensajeVisible
                    || elementosVisibles(id("ct_product")) == 0;
            boolean sinEscaneo = mensajeVisible
                    || elementosVisibles(id("et_barcode")) == 0;
            boolean sinGuardado =
                    mensajeVisible
                    || (elementosVisibles(id("btn_save_preinventory")) == 0
                        && elementosVisibles(
                                id("btn_save_preinventory_own")) == 0);
            boolean sinExpansion = mensajeVisible
                    || elementosVisibles(By.xpath(
                            "//*[@resource-id="
                                    + literalXpath(nombrePaquete
                                            + ":id/tv_state_visibility")
                                    + " and @text='+']")) == 0;
            boolean sinModalCaptura =
                    mensajeVisible
                    || elementosVisibles(id("edt_find_sku_product")) == 0;
            return new ResultadoBloqueoEstructural(
                    mensajeVisible, sinAceptar, sinCancelar, sinSku,
                    sinEscaneo, sinGuardado, sinExpansion,
                    sinModalCaptura);
        } finally {
            appium.driver.manage().timeouts().implicitlyWait(esperaImplicita);
        }
    }

    public void validarMensajeConsolidacion() {
        asegurar(mensajeConsolidacionSinAcciones(),
                "El mensaje de consolidacion no es visible o presenta acciones inesperadas");
    }

    public void validarInteraccionBloqueada() {
        boolean mensajeBloqueante = interaccionBloqueadaDuranteConsolidacion()
                || textoVisible(MENSAJE_ERROR_CONSOLIDACION);
        asegurar(mensajeBloqueante,
                "La interaccion no quedo bloqueada por la consolidacion");
    }

    public void validarInteraccionDesbloqueada() {
        asegurar(!consolidacionEnCurso()
                        && !textoVisible(MENSAJE_ERROR_CONSOLIDACION),
                "La pantalla conserva un mensaje bloqueante despues de la consolidacion");
    }

    public void validarProductoEditable() {
        asegurar(productoEditable(),
                "El producto no quedo habilitado para edicion");
    }

    public void validarCompletoVisible() {
        asegurar(fisicoMuestraCompleto(),
                "No se mostro la etiqueta Completo");
    }

    public void validarCompletoAusente() {
        asegurar(!fisicoMuestraCompleto(),
                "La etiqueta Completo permanecio visible");
    }

    public void validarGuionesIniciales() {
        asegurar(fisicoMuestraGuionesIniciales(),
                "No se mostro el estado inicial con guiones");
    }

    public void validarAusenciaControlesFinalizacion() {
        asegurar(sinControlesFinalizacion(),
                "Se encontro un control FINALIZAR no esperado");
    }

    public void validarPosicionGuardar() {
        asegurar(guardarEstaDerechaDeCancelar(),
                "GUARDAR no esta ubicado a la derecha de CANCELAR");
    }

    public void interrumpirYReactivar(Duration tiempoSegundoPlano) {
        AndroidDriver driver = driverAndroid();
        driver.runAppInBackground(tiempoSegundoPlano);
        driver.activateApp(nombrePaquete);
    }

    /**
     * Ejerce Home como representante de las alternativas Home/Regresar/Cerrar
     * y deja la aplicacion en segundo plano para que el script reingrese desde
     * un arranque limpio.
     */
    public ResultadoInterrupcionApp interrumpirConHomeDuranteConsolidacion() {
        return interrumpirConHomeDuranteConsolidacion(false);
    }

    /**
     * Captura opcionalmente el ultimo estado visible de la consolidacion justo
     * antes de enviar la aplicacion a segundo plano con Home.
     */
    public ResultadoInterrupcionApp interrumpirConHomeDuranteConsolidacion(
            boolean screenShot) {
        boolean mensajeVisibleAntes = mensajeConsolidacionSinAcciones();
        if (mensajeVisibleAntes && screenShot) {
            testCase.addScreenShotCurrentStep(
                    appium,
                    "Consolidacion activa antes de presionar Home");
        }
        AndroidDriver driver = driverAndroid();
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
        ApplicationState estado = driver.queryAppState(nombrePaquete);
        boolean aplicacionEnSegundoPlano =
                ApplicationState.RUNNING_IN_BACKGROUND.equals(estado)
                || ApplicationState.RUNNING_IN_BACKGROUND_SUSPENDED
                        .equals(estado);
        return new ResultadoInterrupcionApp(
                mensajeVisibleAntes,
                aplicacionEnSegundoPlano,
                estado.name());
    }

    // TAG_MVP5_ISOLATED_BOOTSTRAP_V1:
    // Equivalente aislado del force-stop legacy para usos controlados en MVP5.
    public void finalizarAppConAdb() {
        accesoApp.finalizarAppConAdb();
    }

    // TAG_MVP5_DB_CONFIG_V1:
    // Conexion BD aislada en MVP5 (prioridad: -D > ENV > GlobalVariables).
    public Connection abrirConexionSatelitesMvp5() {
        return conexionBd.abrirConexionSatelitesMvp5();
    }

    public void cerrarConexionMvp5(Connection conexion) {
        conexionBd.cerrarConexionMvp5(conexion);
    }

    private int elementosVisibles(By localizador) {
        int visibles = 0;
        for (WebElement elemento : appium.driver.findElements(localizador)) {
            if (elemento.isDisplayed()) {
                visibles++;
            }
        }
        return visibles;
    }

    /** Estado inmutable de toda la superficie bloqueada por consolidacion. */
    public static final class ResultadoBloqueoEstructural {
        private final boolean mensajeVisible;
        private final boolean sinAceptar;
        private final boolean sinCancelar;
        private final boolean sinSku;
        private final boolean sinEscaneo;
        private final boolean sinGuardado;
        private final boolean sinExpansion;
        private final boolean sinModalCaptura;

        private ResultadoBloqueoEstructural(
                boolean mensajeVisible, boolean sinAceptar,
                boolean sinCancelar, boolean sinSku,
                boolean sinEscaneo, boolean sinGuardado,
                boolean sinExpansion, boolean sinModalCaptura) {
            this.mensajeVisible = mensajeVisible;
            this.sinAceptar = sinAceptar;
            this.sinCancelar = sinCancelar;
            this.sinSku = sinSku;
            this.sinEscaneo = sinEscaneo;
            this.sinGuardado = sinGuardado;
            this.sinExpansion = sinExpansion;
            this.sinModalCaptura = sinModalCaptura;
        }

        public boolean bloqueoIntegral() {
            return mensajeVisible && sinAceptar && sinCancelar
                    && sinSku && sinEscaneo && sinGuardado
                    && sinExpansion && sinModalCaptura;
        }

        public String evidencia() {
            return "PROCESO_BLOQUEANTE|ACEPTAR_AUSENTE|CANCELAR_AUSENTE"
                    + "|SKU_BLOQUEADO|ESCANEO_BLOQUEADO|GUARDADO_BLOQUEADO"
                    + "|EXPANSION_BLOQUEADA|MODAL_BLOQUEADO|RESULTADO\n"
                    + mensajeVisible + "|" + sinAceptar + "|"
                    + sinCancelar + "|" + sinSku + "|"
                    + sinEscaneo + "|" + sinGuardado + "|"
                    + sinExpansion + "|" + sinModalCaptura + "|"
                    + (bloqueoIntegral()
                            ? "BLOQUEO_INTEGRAL"
                            : "SUPERFICIE_INTERACTIVA_EXPUESTA");
        }
    }

    /** Resultado observable del arranque de consolidacion en ventana corta. */
    public static final class ResultadoInicioConsolidacion {
        private String mensajeLoading1;
        private boolean loading1Valido;
        private boolean loading2Observado;
        private boolean toastObservado;
        private boolean accionesManualesAusentes = true;

        private boolean inicioAutomatico() {
            return loading1Valido || loading2Observado || toastObservado;
        }

        public boolean inicioAutomaticoObservable() {
            return inicioAutomatico();
        }

        public String mensajeLoading1() {
            return mensajeLoading1;
        }

        public boolean loading1Valido() {
            return loading1Valido;
        }

        public boolean accionesManualesAusentes() {
            return accionesManualesAusentes;
        }

        public boolean toastObservado() {
            return toastObservado;
        }

        public String evidencia() {
            return "LOADING_1|LOADING_2|TOAST|ACEPTAR_CANCELAR|RESULTADO\n"
                    + (loading1Valido ? "SI" : "NO") + "|"
                    + (loading2Observado ? "SI" : "NO") + "|"
                    + (toastObservado ? "SI" : "NO") + "|"
                    + (accionesManualesAusentes ? "AUSENTES" : "PRESENTES") + "|"
                    + (inicioAutomatico() ? "CORRECTO" : "INCORRECTO");
        }
    }

    /** Resultado observable de la interrupcion manual representada por Home. */
    public static final class ResultadoInterrupcionApp {
        private final boolean mensajeVisibleAntes;
        private final boolean aplicacionEnSegundoPlano;
        private final String estadoAplicacion;

        private ResultadoInterrupcionApp(
                boolean mensajeVisibleAntes,
                boolean aplicacionEnSegundoPlano,
                String estadoAplicacion) {
            this.mensajeVisibleAntes = mensajeVisibleAntes;
            this.aplicacionEnSegundoPlano = aplicacionEnSegundoPlano;
            this.estadoAplicacion = estadoAplicacion;
        }

        public boolean interrupcionObservable() {
            return aplicacionEnSegundoPlano;
        }

        public String evidencia() {
            return "ALTERNATIVAS|ACCION_EJERCIDA|MENSAJE_ACTIVO_ANTES"
                    + "|ESTADO_APP|RESULTADO\n"
                    + "HOME_REGRESAR_CERRAR|HOME|"
                    + mensajeVisibleAntes + "|"
                    + estadoAplicacion + "|"
                    + (interrupcionObservable()
                            ? "INTERRUPCION_OBSERVABLE"
                            : "INTERRUPCION_NO_CONFIRMADA");
        }
    }

    private boolean textoVisibleRapido(String texto) {
        Duration esperaImplicita = appium.driver.manage().timeouts()
                .getImplicitWaitTimeout();
        try {
            appium.driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            String textoUiAutomator = texto.replace("\\", "\\\\")
                    .replace("\"", "\\\"");
            return appium.driver.findElements(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"" + textoUiAutomator + "\")"))
                    .stream()
                    .anyMatch(WebElement::isDisplayed);
        } catch (RuntimeException excepcion) {
            return false;
        } finally {
            appium.driver.manage().timeouts().implicitlyWait(esperaImplicita);
        }
    }

    public boolean textoVisible(String texto) {
        try {
            for (WebElement elemento : appium.driver.findElements(
                    By.xpath("//*[@text=" + literalXpath(texto) + "]"))) {
                if (elemento.isDisplayed()) {
                    return true;
                }
            }
            return false;
        } catch (RuntimeException excepcion) {
            // Evita getPageSource: es incompatible con AccessibilityNodeInfo en Android 16.
            return false;
        }
    }

    public void esperarTexto(String texto) {
        try {
            espera().until(controlador -> textoVisible(texto));
        } catch (TimeoutException excepcion) {
            throw new AssertionError(
                    "No aparecio el texto esperado: " + texto,
                    excepcion);
        }
    }

    private boolean detalleConteoVisible() {
        return !appium.driver.findElements(id("et_barcode")).isEmpty()
                || !appium.driver.findElements(
                        id("btn_save_preinventory")).isEmpty()
                || !appium.driver.findElements(
                        id("btn_save_preinventory_own")).isEmpty();
    }

    private boolean textoVisibleEnProductos(String texto) {
        for (WebElement producto : appium.driver.findElements(id("ct_product"))) {
            for (WebElement etiqueta : producto.findElements(
                    By.className("android.widget.TextView"))) {
                if (etiqueta.isDisplayed()
                        && texto.equalsIgnoreCase(etiqueta.getText().trim())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void desplazarHastaSku(String textoSku) {
        String comilla = String.valueOf((char) 34);
        String expresion = "new UiScrollable(new UiSelector().resourceId("
                + comilla + nombrePaquete + ":id/rv_group_products" + comilla
                + ")).setAsVerticalList().setMaxSearchSwipes(60)"
                + ".scrollIntoView(new UiSelector().resourceId("
                + comilla + nombrePaquete + ":id/tv_sku" + comilla
                + ").text(" + comilla + textoSku + comilla + "))";
        appium.driver.findElement(AppiumBy.androidUIAutomator(expresion));
    }

    private WebElement elementoVisible(By localizador) {
        return espera().until(
                ExpectedConditions.visibilityOfElementLocated(localizador));
    }

    private void hacerClic(By localizador) {
        espera().until(
                ExpectedConditions.elementToBeClickable(localizador)).click();
    }

    private By id(String recurso) {
        return By.id(nombrePaquete + ":id/" + recurso);
    }

    private WebDriverWait espera() {
        return new WebDriverWait(appium.driver, tiempoEspera);
    }

    private void tomarCaptura(boolean screenShot) {
        if (screenShot) {
            testCase.addScreenShotCurrentStep(appium, "testImage");
        }
    }

    private AndroidDriver driverAndroid() {
        if (!(appium.driver instanceof AndroidDriver)) {
            throw new IllegalStateException(
                    "La ejecucion MVP5 requiere AndroidDriver");
        }
        return (AndroidDriver) appium.driver;
    }

    private static void asegurar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    /** Valores observados en una unica tarjeta del listado inteligente. */
    public static final class EstadoInicialSku {
        private final String teorico;
        private final String fisico;
        private final boolean completoAusente;

        private EstadoInicialSku(
                String teorico,
                String fisico,
                boolean completoAusente) {
            this.teorico = teorico;
            this.fisico = fisico;
            this.completoAusente = completoAusente;
        }

        public String teorico() {
            return teorico;
        }

        public String fisico() {
            return fisico;
        }

        public boolean completoAusente() {
            return completoAusente;
        }
    }

    private static String literalXpath(String valor) {
        if (!valor.contains("'")) {
            return "'" + valor + "'";
        }
        if (!valor.contains("\"")) {
            return "\"" + valor + "\"";
        }
        String[] partes = valor.split("'", -1);
        StringBuilder resultado = new StringBuilder("concat(");
        for (int indice = 0; indice < partes.length; indice++) {
            if (indice > 0) {
                resultado.append(", \"'\", ");
            }
            resultado.append("'").append(partes[indice]).append("'");
        }
        return resultado.append(')').toString();
    }
}

