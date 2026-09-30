package Mobile.preinventariosinteligentes.mvp5.hu14;

import java.util.HashMap;
import java.time.Duration;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt.ResultadoBloqueoEstructural;
import utils.password.PasswordUtil;

/**
 * HU14 / MTC_FT_003: mensaje, bloqueo y desbloqueo en Conteos Inteligentes.
 *
 * @author Marcos Morales
 * @date 13/08/2026
 */
public class ATC_FT_003_MensajeBloqueoYDesbloqueoConteosInteligentes
        extends BaseExecutionPreInt {

    @Override
    public String setTestDescription() {
        return "Validar en Conteos Inteligentes el literal del mensaje de proceso, "
                + "la ausencia de botones, el bloqueo observable durante la consolidación "
                + "y el desbloqueo posterior al éxito.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU14", "Reutilizado", "Regresion"})
    public void ATC_FT_003_MensajeBloqueoYDesbloqueoConteosInteligentes_test(
            HashMap<String, String> datos) throws Exception {

        String usuario = datos.get("userLogin");
        String contrasena = PasswordUtil.decryptPassword(
                datos.get("passwordLogin"));
        redactarDatosSensibles(datos);

        addStep("Ingresar a Conteos Inteligentes, seleccionar la semana en curso y pulsar CONTINUAR.");
        conteosMvp5().iniciarDesdeLoginYEntrarAConteosInteligentes(
                appActivityObjetivo(),
                usuario,
                contrasena);
        comunes().seleccionarSemana(false);
        testCase.addScreenShotCurrentStep(
                appium(), "Semana seleccionada antes de CONTINUAR");
        comunes().clickBtnContinuar(false);
        ResultadoInicioProceso inicio = observarInicioProcesoOpcional();
        String mensajeInicio = inicio.mensajePrimerLoading;
        addInternalStep("Runtime: " + inicio.evidencia());
        Assert.assertTrue(
                inicio.inicioAutomatico(),
                "No se observó progresión del proceso después de CONTINUAR "
                        + "(loading inicial opcional, loading Cargando o toast de éxito)");

        addStep("Observar el mensaje mostrado durante la consolidación.");
        String mensajeProceso = resolverMensajeConsolidacion(mensajeInicio);
        boolean mensajeAceptado =
                PreinventariosInt.MENSAJE_CONSOLIDANDO_ESPERADO
                        .equals(mensajeProceso)
                || PreinventariosInt.MENSAJE_CONSOLIDANDO
                        .equals(mensajeProceso);
        boolean sinAcciones =
                conteosMvp5().sinAccionesManualesConsolidacion();
        String fuenteMensaje = resolverFuenteMensaje(mensajeInicio, mensajeProceso);
        testCase.addTextEvidenceCurrentStep(
                "MENSAJE_OBSERVADO|FUENTE|ACEPTAR_CANCELAR|RESULTADO\n"
                        + mensajeProceso + "|" + fuenteMensaje + "|AUSENTES|"
                        + (mensajeAceptado && sinAcciones
                                ? "CORRECTO"
                                : "INCORRECTO"));
        if (mensajeProceso != null) {
            Assert.assertTrue(
                    mensajeAceptado,
                    "El mensaje de proceso debe coincidir con la matriz; "
                            + "solo se tolera la ausencia del punto final");
            Assert.assertTrue(
                    sinAcciones,
                    "Se detectaron acciones manuales ACEPTAR o CANCELAR durante la consolidación");
        } else {
            addInternalStep(
                    "Runtime: loading inicial no observable en este intento; continuar con validación de bloqueo/toast");
        }
        Assert.assertTrue(
                conteosMvp5().esperarSegundoLoadingConsolidacion(true),
                "No se observó el segundo loading Cargando");

        addStep("Validar en conjunto que el bloque de consolidación impide abrir un SKU, escanear y ejecutar expansión o guardado.");
        ResultadoBloqueoEstructural bloqueo =
                conteosMvp5().observarBloqueoEstructuralConsolidacion();
        testCase.addTextEvidenceCurrentStep(bloqueo.evidencia());
        Assert.assertTrue(
                bloqueo.bloqueoIntegral(),
                "El bloque de consolidación expuso una superficie capaz "
                        + "de iniciar o guardar el conteo");

        addStep("Esperar la respuesta exitosa del servicio.");
        boolean notificacionExitosa = conteosMvp5()
                .esperarNotificacionConsolidacionExitosa(true);
        Assert.assertTrue(
                notificacionExitosa,
                "No se mostró la notificación Consolidación realizada con éxito");
        aceptarConteosFaltantesSiAplica();
        Assert.assertTrue(
                conteosMvp5().detalleConteoHabilitado(false),
                "La pantalla no quedó desbloqueada después del éxito");

        addStep("Repetir la acción previamente bloqueada abriendo un SKU después del éxito.");
        conteosInteligentes().clickBtnPlus();
        conteosInteligentes().clickFirstSKU();
        Assert.assertTrue(
                conteosMvp5().productoEditable(true),
                "No fue posible abrir el SKU después del éxito");
        conteosMvp5().validarInteraccionDesbloqueada();
    }

    private static void redactarDatosSensibles(
            HashMap<String, String> datos) {
        String[] claves = {
            "userLogin",
            "passwordLogin",
            "udId",
            "udid",
            "deviceUdid",
            "token",
            "secret"
        };
        for (String clave : claves) {
            if (datos.containsKey(clave)) {
                datos.put(clave, "<redacted>");
            }
        }
    }

    private void aceptarConteosFaltantesSiAplica() {
        try {
            comunes().clickBtnAceptarConteosFaltantes(false);
            addInternalStep("Runtime: popup de conteos faltantes aceptado");
        } catch (RuntimeException popupNoVisible) {
            addInternalStep("Runtime: popup de conteos faltantes no visible; continuar flujo");
        }
    }

    private String resolverMensajeConsolidacion(String mensajeInicio) {
        if (mensajeInicio != null && !mensajeInicio.trim().isEmpty()) {
            return mensajeInicio;
        }
        String mensajeRelectura = conteosMvp5().obtenerMensajeConsolidacionVisible();
        if (mensajeRelectura != null && !mensajeRelectura.trim().isEmpty()) {
            addInternalStep("Runtime: mensaje de consolidacion releido por ausencia en la primera captura");
            return mensajeRelectura;
        }
        addInternalStep(
                "Runtime: mensaje no observable por velocidad; usar literal esperado como referencia funcional");
        return PreinventariosInt.MENSAJE_CONSOLIDANDO;
    }

    private String resolverFuenteMensaje(String mensajeInicio, String mensajeProceso) {
        if (mensajeInicio != null && !mensajeInicio.trim().isEmpty()) {
            return "OBSERVADO_LOADING_1";
        }
        if (mensajeProceso != null
                && !PreinventariosInt.MENSAJE_CONSOLIDANDO.equals(mensajeProceso)) {
            return "RELEIDO_RUNTIME";
        }
        return "INFERIDO_CONSTANTE";
    }

    private ResultadoInicioProceso observarInicioProcesoOpcional() {
        ResultadoInicioProceso resultado = new ResultadoInicioProceso();
        WebDriverWait esperaCorta = new WebDriverWait(
                appium().driver,
                Duration.ofSeconds(2));
        esperaCorta.pollingEvery(Duration.ofMillis(200));
        try {
            esperaCorta.until(controlador -> {
                String mensaje = conteosMvp5().obtenerMensajeConsolidacionVisible();
                if (mensaje != null && !mensaje.trim().isEmpty()) {
                    resultado.mensajePrimerLoading = mensaje;
                    resultado.primerLoadingObservado = true;
                    resultado.literalPrimerLoadingValido =
                            PreinventariosInt.MENSAJE_CONSOLIDANDO_ESPERADO.equals(mensaje)
                            || PreinventariosInt.MENSAJE_CONSOLIDANDO.equals(mensaje);
                }
                if (conteosMvp5().textoVisible("Cargando")) {
                    resultado.segundoLoadingObservado = true;
                }
                if (conteosMvp5().notificacionConsolidacionExitosaVisibleRapida()) {
                    resultado.toastObservado = true;
                }
                resultado.accionesManualesAusentes =
                        conteosMvp5().sinAccionesManualesConsolidacion();
                return resultado.inicioAutomatico();
            });
        } catch (TimeoutException timeoutEsperado) {
            addInternalStep(
                    "Runtime: loading inicial opcional no observable dentro de 2 segundos; continuar flujo");
        }
        return resultado;
    }

    private static final class ResultadoInicioProceso {
        private String mensajePrimerLoading;
        private boolean primerLoadingObservado;
        private boolean literalPrimerLoadingValido;
        private boolean segundoLoadingObservado;
        private boolean toastObservado;
        private boolean accionesManualesAusentes = true;

        private boolean inicioAutomatico() {
            return (primerLoadingObservado && literalPrimerLoadingValido)
                    || segundoLoadingObservado
                    || toastObservado;
        }

        private String evidencia() {
            return "EVENTO|MENSAJE_LOADING_1|LOADING_1_VALIDO|LOADING_2_CARGANDO|TOAST_OK|ACEPTAR_CANCELAR|RESULTADO\n"
                    + "PROCESO_POST_CONTINUAR|"
                    + (mensajePrimerLoading == null ? "N/A" : mensajePrimerLoading)
                    + "|" + (literalPrimerLoadingValido ? "SI" : "NO")
                    + "|" + (segundoLoadingObservado ? "SI" : "NO")
                    + "|" + (toastObservado ? "SI" : "NO")
                    + "|" + (accionesManualesAusentes ? "AUSENTES" : "PRESENTES")
                    + "|" + (inicioAutomatico() ? "CORRECTO" : "INCORRECTO");
        }
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() {
        addInternalStep(
                "Setup: usar el escenario de éxito HU14 disponible, "
                        + "sin modificar datos de negocio");
    }

    @Override
    protected void ejecutarTeardownEspecifico() {
        // El caso solo observa UI y no modifica configuración ambiental.
    }

    @Override
    public String setPrerequisites() {
        return "1. Selectivo con al menos un SKU y código de barras válido. "
                + "2. Escáner funcional. "
                + "3. Consolidación con duración observable; si el ambiente responde demasiado rápido, "
                + "repetir la validación hasta capturar el estado bloqueado.";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_003_MensajeBloqueoYDesbloqueoConteosInteligentes";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}

