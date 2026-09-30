package Mobile.preinventariosinteligentes.mvp5.om;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.android.AndroidDriver;

import Mobile.preinventariosinteligentes.mvp5.support.Configuraciones;
import Mobile.preinventariosinteligentes.mvp5.support.CicloAplicacion;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaEstadoInicialHu10;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaSkusConteosPropiosMvp5;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaSelectivoHu24;
import Mobile.preinventariosinteligentes.mvp5.support.SesionAppium;
import Mobile.preinventariosinteligentes.om.Comunes;
import Mobile.preinventariosinteligentes.om.ConteosInteligentes;
import Mobile.preinventariosinteligentes.om.ConteosPropios;
import Mobile.preinventariosinteligentes.om.Login;
import Mobile.preinventariosinteligentes.om.Menus;
import Mobile.preinventariosinteligentes.om.PlantillaTienda;
import Mobile.preinventariosinteligentes.om.RolesInteligentes;
import Mobile.preinventariosinteligentes.mvp5.support.ArranqueSesion;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * Base para casos de preinventarios en dispositivo configurable por pipeline.
 * Centraliza Appium, perfil de dispositivo y OMs comunes para evitar duplicación.
 */
public abstract class BaseExecutionPreInt extends BaseExecution {
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private HashMap<String, String> datos;
    private SesionAppium sesion;
    private AppiumUtil appium;
    private String appPackageObjetivo;
    private String appActivityObjetivo;
    private final Map<Class<?>, Object> cacheOms = new HashMap<>();
    private int contadorPasos;
    private String nombreCaso;
    private boolean pasosInternos;

    @BeforeMethod(alwaysRun = true)
    public final void setUpBase(java.lang.reflect.Method metodo, Object[] parametros) throws Exception {
        datos = extraerDataProvider(parametros);
        testCase.setProject_Name(nombreProyecto());
        nombreCaso = metodo.getName();
        contadorPasos = 0;
        pasosInternos = false;
        logConsola("INFO", "INICIO CASO");
        logConsola("INFO", "DISPOSITIVO " + etiquetaDispositivo());

        Configuraciones configuracion = Configuraciones.cargar(datos);
        appPackageObjetivo = configuracion.requeridoCapabilities(
                "android.appPackage", "appPackage");
        appActivityObjetivo = configuracion.opcionalCapabilities("android.appActivity", "appActivity");

        boolean forceStopAntesSesion = configuracion.valorBooleano("android.forceStopBeforeSession", false)
                || configuracion.valorBooleano("forceStopBeforeSession", false);

        addInternalStep(forceStopAntesSesion
                ? "Setup: cerrar app previa y abrir sesión Appium configurable"
                : "Setup: abrir sesión Appium configurable (sin cierre previo de app)"
        );
        sesion = ArranqueSesion.abrirSesion(datos);
        appium = sesion.appium();
        appPackageObjetivo = sesion.nombrePaquete();
        validarPaqueteSesion(configuracion);
        // Los OMs se crean bajo demanda para no inicializar objetos no usados por el caso.
        cacheOms.clear();

        pasosInternos = true;
        try {
            ejecutarSetupEspecifico();
        } finally {
            pasosInternos = false;
        }
    }

    @AfterMethod(alwaysRun = true)
    public final void tearDownBase(ITestResult resultado) throws Exception {
        addInternalStep("Teardown: ejecutar limpieza específica y cerrar sesión Appium");
        pasosInternos = true;
        try {
            ejecutarTeardownEspecifico();
        } finally {
            if (sesion != null) {
                sesion.close();
            } else if (appium != null && appium.driver != null) {
                appium.driver.quit();
            }
            sesion = null;
            appium = null;
            appPackageObjetivo = null;
            appActivityObjetivo = null;
            cacheOms.clear();
            datos = null;
            imprimirResultadoFinal(resultado);
            nombreCaso = null;
            pasosInternos = false;
        }
    }

    @Override
    public void addStep(String stepDescription) {
        if (pasosInternos) {
            addInternalStep(stepDescription);
            return;
        }
        super.addStep(stepDescription);
        contadorPasos++;
        logConsola("STEP", "[" + contadorPasos + "] " + stepDescription);
    }

    /** Registra trazabilidad tecnica en consola sin publicar un paso en el PDF. */
    protected final void addInternalStep(String stepDescription) {
        logConsola("INTERNAL", stepDescription);
    }

    @Override
    public void beforeTest() {
        // La inicialización dependiente de data-provider vive en @BeforeMethod.
    }

    /** Permite sobreescribir el nombre de proyecto mostrado en evidencia. */
    protected String nombreProyecto() {
        return "PreinventariosInteligentes";
    }

    /** Hook para setup del caso (por ejemplo limpieza de tablas y prerequisitos). */
    protected void ejecutarSetupEspecifico() throws Exception {
        // Implementación opcional por caso.
    }

    /** Hook para teardown del caso (por ejemplo limpieza final de datos). */
    protected void ejecutarTeardownEspecifico() throws Exception {
        // Implementación opcional por caso.
    }

    protected final HashMap<String, String> datos() {
        return datos;
    }

    protected final AppiumUtil appium() {
        return appium;
    }

    protected final String appPackageObjetivo() {
        return appPackageObjetivo;
    }

    protected final String appActivityObjetivo() {
        return appActivityObjetivo;
    }

    /**
     * Guard backend-first reutilizable para HU24: valida escenario; si no existe,
     * prepara clasificacion/distribucion por UI y vuelve a validar con polling.
     */
    protected final <T> T asegurarEscenarioConGuardBackMvp5(
            String etiquetaSetup,
            Supplier<T> consultaEscenario,
            String descripcionEscenario) throws InterruptedException {
        addInternalStep(etiquetaSetup + ": validar backend de " + descripcionEscenario);
        T escenario = consultarEscenarioSeguro(consultaEscenario);
        if (escenario != null) {
            addInternalStep(etiquetaSetup + ": backend confirma escenario vigente");
            return escenario;
        }

        addInternalStep(etiquetaSetup
                + ": escenario no disponible; ejecutar prerequisito de clasificacion/distribucion");
        prepararDistribucionInteligenteMvp5(etiquetaSetup);

        Configuraciones configuracion = Configuraciones.cargar(datos());
        long timeoutSegundos = configuracion.valorLong(
                "preinv.mvp5.hu24.preflight.timeout.seconds", 90L);
        long intervaloMs = configuracion.valorLong(
                "preinv.mvp5.hu24.preflight.poll.ms", 3000L);
        long inicio = System.currentTimeMillis();
        int intento = 0;

        while ((System.currentTimeMillis() - inicio) < (timeoutSegundos * 1000L)) {
            intento++;
            escenario = consultarEscenarioSeguro(consultaEscenario);
            if (escenario != null) {
                addInternalStep(etiquetaSetup + ": escenario disponible en intento " + intento);
                return escenario;
            }
            addInternalStep(etiquetaSetup + ": escenario aun no disponible, intento "
                    + intento + ", esperar " + intervaloMs + " ms");
            Thread.sleep(intervaloMs);
        }

        throw new IllegalStateException(
                etiquetaSetup + ": backend no confirma " + descripcionEscenario
                        + " despues de " + timeoutSegundos + " segundos");
    }

    private static <T> T consultarEscenarioSeguro(Supplier<T> consultaEscenario) {
        try {
            return consultaEscenario.get();
        } catch (IllegalStateException escenarioNoDisponible) {
            return null;
        }
    }

    private void prepararDistribucionInteligenteMvp5(String etiquetaSetup) {
        String usuario = datos().get("userLogin");
        String contrasena = PasswordUtil.decryptPassword(datos().get("passwordLogin"));
        conteosMvp5().iniciarDesdeLoginYEntrarAConteosInteligentes(
                appActivityObjetivo(),
                usuario,
                contrasena);
        comunes().seleccionarSemana(false);
        comunes().clickBtnContinuar(false);

        try {
            comunes().seleccionarClasificacion("planograma", false);
            comunes().clickBtnContinuar(false);
            comunes().clickBtnAceptar(false);
            comunes().esperaBtnCrearPlantilla();
        } catch (RuntimeException clasificacionNoVisible) {
            addInternalStep(etiquetaSetup
                    + ": clasificacion no visible; continuar polling backend");
            return;
        }

        comunes().clickBtnAceptar(false);
        plantillaTienda().agregarColaborador(false);
        plantillaTienda().seleccionarColaborador(2, false);
        plantillaTienda().seleccionarDiaTurno(false);
        plantillaTienda().clickBtnGuardarPlantilla(false);
        comunes().clickBtnAceptar(false);
        esperarLoadingSetupSinFallar(etiquetaSetup, "despues de guardar plantilla");
        rolesInteligentes().clickBtnContinuar(false);
        esperarLoadingSetupSinFallar(etiquetaSetup, "despues de continuar roles inteligentes");
        addInternalStep(etiquetaSetup
                + ": clasificacion/asignacion/distribucion preparada por UI");
    }

    private void esperarLoadingSetupSinFallar(String etiquetaSetup, String contexto) {
        try {
            comunes().esperaSeOculteLoading();
        } catch (RuntimeException loadingPersistente) {
            addInternalStep(etiquetaSetup + ": loading persistente " + contexto
                    + "; continuar con validaciones de negocio");
        }
    }

    /** Falla temprano cuando la sesion no corresponde al paquete resuelto. */
    private void validarPaqueteSesion(Configuraciones configuracion) {
        Object capability = appium.driver.getCapabilities()
                .getCapability("appium:appPackage");
        if (capability == null) {
            capability = appium.driver.getCapabilities()
                    .getCapability("appPackage");
        }
        String paqueteSesion = capability == null
                ? "AUSENTE"
                : capability.toString();
        String paqueteActivo = appium.driver instanceof AndroidDriver
                ? ((AndroidDriver) appium.driver).getCurrentPackage()
                : "NO_ANDROID";
        String origen = configuracion.origenCapabilities(
                "android.appPackage", "appPackage");
        logConsola(
                "INTERNAL",
                "PREFLIGHT_APP_PACKAGE configurado=" + appPackageObjetivo
                        + "|origen=" + origen
                        + "|sesion=" + paqueteSesion
                        + "|activo=" + paqueteActivo);

        if (!appPackageObjetivo.equals(paqueteSesion)) {
            throw new IllegalStateException(
                    "El appPackage resuelto no coincide con la sesion/paquete activo: "
                            + appPackageObjetivo + "|"
                            + paqueteSesion + "|" + paqueteActivo);
        }

        if (!appPackageObjetivo.equals(paqueteActivo)) {
            reactivarAppObjetivo();
            paqueteActivo = appium.driver instanceof AndroidDriver
                    ? ((AndroidDriver) appium.driver).getCurrentPackage()
                    : "NO_ANDROID";
            logConsola(
                    "INTERNAL",
                    "PREFLIGHT_APP_PACKAGE_POST_ACTIVATE configurado=" + appPackageObjetivo
                            + "|sesion=" + paqueteSesion
                            + "|activo=" + paqueteActivo);
        }

        if (!appPackageObjetivo.equals(paqueteActivo)) {
            throw new IllegalStateException(
                    "El appPackage resuelto no coincide con la sesion/paquete activo: "
                            + appPackageObjetivo + "|"
                            + paqueteSesion + "|" + paqueteActivo);
        }
    }

    /** Recupera foreground package tras un force-stop inicial para mantener arranque limpio. */
    private void reactivarAppObjetivo() {
        try {
            if (appium.driver instanceof AndroidDriver) {
                CicloAplicacion.iniciarLimpio(
                        (AndroidDriver) appium.driver,
                        appPackageObjetivo,
                        appActivityObjetivo);
                return;
            }
            logConsola("INTERNAL", "No se pudo reactivar app objetivo: driver no Android");
        } catch (RuntimeException exception) {
            logConsola("INTERNAL", "Reactivacion no aplicada: " + valorSeguro(exception));
        }
    }

    protected final Login login() {
        return obtenerOm(Login.class, () ->
                adaptarOmLegacyMvp5(
                        new Login(testCase, appium, this)));
    }

    protected final Menus menus() {
        return obtenerOm(Menus.class, () ->
                adaptarOmLegacyMvp5(
                        new Menus(testCase, appium, this)));
    }

    protected final Comunes comunes() {
        return obtenerOm(Comunes.class, () ->
                adaptarOmLegacyMvp5(
                        new Comunes(testCase, appium, this)));
    }

    protected final ConteosPropios conteosPropios() {
        return obtenerOm(ConteosPropios.class, () ->
                adaptarOmLegacyMvp5(
                        new ConteosPropios(testCase, appium, this)));
    }

    protected final ConteosInteligentes conteosInteligentes() {
        return obtenerOm(ConteosInteligentes.class, () ->
                adaptarOmLegacyMvp5(
                        new ConteosInteligentes(testCase, appium, this)));
    }

    protected final PlantillaTienda plantillaTienda() {
        return obtenerOm(PlantillaTienda.class, () ->
                adaptarOmLegacyMvp5(
                        new PlantillaTienda(testCase, appium, this)));
    }

    protected final RolesInteligentes rolesInteligentes() {
        return obtenerOm(RolesInteligentes.class, () ->
                adaptarOmLegacyMvp5(
                        new RolesInteligentes(testCase, appium, this)));
    }

    /** Adapta y audita una instancia legacy solo dentro de la ejecucion MVP5. */
    private <T> T adaptarOmLegacyMvp5(T omLegacy) {
        AdaptadorLocalizadoresLegacyMvp5.Resultado<T> resultado =
                AdaptadorLocalizadoresLegacyMvp5.adaptar(
                        omLegacy,
                        appPackageObjetivo);
        logConsola("INTERNAL", resultado.evidencia());
        return resultado.om();
    }

    protected final PreinventariosInt conteosMvp5() {
        Configuraciones configuracion = Configuraciones.cargar(datos);
        Duration tiempoEspera = Duration.ofSeconds(configuracion.valorLong("timeouts.explicit.seconds", 30L));
        return obtenerOm(PreinventariosInt.class,
                () -> new PreinventariosInt(
                        testCase, appium, appPackageObjetivo, tiempoEspera));
    }

    /** OM atomico de HU24 con evidencia encapsulada en cada operacion. */
    protected final ConteosInteligentesHu24 conteosHu24() {
        Configuraciones configuracion = Configuraciones.cargar(datos);
        Duration tiempoEspera = Duration.ofSeconds(
                configuracion.valorLong("timeouts.explicit.seconds", 30L));
        return obtenerOm(ConteosInteligentesHu24.class,
                () -> new ConteosInteligentesHu24(
                        testCase,
                        appium,
                        conteosInteligentes(),
                        appPackageObjetivo,
                        tiempoEspera));
    }

    /** Consultas de solo lectura para seleccionar y validar el escenario HU24. */
    protected final ConsultaSelectivoHu24 datosHu24() {
        return obtenerOm(ConsultaSelectivoHu24.class,
                () -> new ConsultaSelectivoHu24(
                        () -> conteosMvp5().abrirConexionSatelitesMvp5()));
    }

    /** Consultas de solo lectura para seleccionar y validar el escenario HU10. */
    protected final ConsultaEstadoInicialHu10 datosHu10() {
        return obtenerOm(ConsultaEstadoInicialHu10.class,
                () -> new ConsultaEstadoInicialHu10(
                        () -> conteosMvp5().abrirConexionSatelitesMvp5()));
    }

    /** Consulta read-only de SKUs para preparar plantillas de Conteos Propios. */
    protected final ConsultaSkusConteosPropiosMvp5 datosConteosPropiosMvp5() {
        return obtenerOm(ConsultaSkusConteosPropiosMvp5.class,
                () -> new ConsultaSkusConteosPropiosMvp5(
                        () -> conteosMvp5().abrirConexionSatelitesMvp5()));
    }

    /** Creador MVP5 de plantillas propias sin dependencia del OM legado mutable. */
    protected final PlantillaConteosPropiosMvp5 plantillasPropiosMvp5() {
        Configuraciones configuracion = Configuraciones.cargar(datos);
        Duration tiempoEspera = Duration.ofSeconds(
                configuracion.valorLong("timeouts.explicit.seconds", 30L));
        return obtenerOm(PlantillaConteosPropiosMvp5.class,
                () -> new PlantillaConteosPropiosMvp5(
                        appium,
                        appPackageObjetivo,
                        tiempoEspera,
                        menus(),
                        comunes(),
                        conteosPropios()));
    }

    /** Permite crear y cachear OMs nuevos sin tocar el setup base. */
    protected final <T> T obtenerOm(Class<T> tipo, Supplier<T> proveedor) {
        Object instancia = cacheOms.get(tipo);
        if (instancia == null) {
            if (appium == null) {
                throw new IllegalStateException("La sesión Appium no está inicializada. Verifique @BeforeMethod.");
            }
            instancia = proveedor.get();
            cacheOms.put(tipo, instancia);
        }
        return tipo.cast(instancia);
    }

    /** Permite escribir mensajes directos en consola de pipeline. */
    protected final void logConsola(String nivel, String mensaje) {
        String caso = nombreCaso == null ? getClass().getSimpleName() : nombreCaso;
        System.out.println("[PREINV][" + nivel + "][" + FORMATO_FECHA.format(LocalDateTime.now()) + "][" + caso + "] " + mensaje);
    }

    private void imprimirResultadoFinal(ITestResult resultado) {
        if (resultado == null) {
            logConsola("WARN", "FIN CASO - Resultado no disponible");
            return;
        }
        if (resultado.getStatus() == ITestResult.SUCCESS) {
            logConsola("OK", "FIN CASO - SUCCESS");
            return;
        }
        if (resultado.getStatus() == ITestResult.SKIP) {
            logConsola("WARN", "FIN CASO - SKIPPED: " + valorSeguro(resultado.getThrowable()));
            return;
        }
        logConsola("ERROR", "FIN CASO - FAILED: " + valorSeguro(resultado.getThrowable()));
        if (resultado.getThrowable() != null) {
            resultado.getThrowable().printStackTrace(System.out);
        }
    }

    private static String valorSeguro(Throwable error) {
        if (error == null) {
            return "sin detalle";
        }
        String detalle = error.getMessage();
        if (detalle == null || detalle.trim().isEmpty()) {
            return error.getClass().getSimpleName();
        }
        return detalle;
    }

    private String etiquetaDispositivo() {
        String udid = valorDato("android.udid", "udid", "deviceUdid");
        String tipo = valorDato("device.type", "device.tipo", "deviceType", "deviceProfile");
        String modelo = valorDato("android.deviceName", "deviceName");
        return "udid=" + valorPresentable(udid) + ", tipo=" + valorPresentable(tipo) + ", modelo=" + valorPresentable(modelo);
    }

    private String valorDato(String... claves) {
        if (datos == null) {
            return null;
        }
        for (String clave : claves) {
            String valor = System.getProperty(clave);
            if (valor != null && !valor.trim().isEmpty()) {
                return valor.trim();
            }
            valor = datos.get(clave);
            if (valor != null && !valor.trim().isEmpty()) {
                return valor.trim();
            }
        }
        return null;
    }

    private static String valorPresentable(String valor) {
        return (valor == null || valor.trim().isEmpty()) ? "n/a" : valor;
    }

    @SuppressWarnings("unchecked")
    private HashMap<String, String> extraerDataProvider(Object[] parametros) {
        if (parametros != null && parametros.length > 0 && parametros[0] instanceof HashMap<?, ?>) {
            return (HashMap<String, String>) parametros[0];
        }
        throw new IllegalStateException("No fue posible obtener datos del data-provider para el setup.");
    }

}
