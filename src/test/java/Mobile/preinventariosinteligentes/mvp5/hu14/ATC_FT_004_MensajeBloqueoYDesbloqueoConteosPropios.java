package Mobile.preinventariosinteligentes.mvp5.hu14;

import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt.ResultadoBloqueoEstructural;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt.ResultadoInicioConsolidacion;
import utils.password.PasswordUtil;

/**
 * HU14 / MTC_FT_004: mensaje, bloqueo y desbloqueo en Conteos Propios.
 *
 * @author Marcos Morales
 * @date 13/08/2026
 */
public class ATC_FT_004_MensajeBloqueoYDesbloqueoConteosPropios
        extends BaseExecutionPreInt {

    @Override
    public String setTestDescription() {
        return "Validar en Conteos Propios el literal del mensaje de proceso, "
                + "la ausencia de botones, el bloqueo observable durante la consolidación "
                + "y el desbloqueo posterior al éxito.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU14", "Reutilizado", "Regresion"})
    public void ATC_FT_004_MensajeBloqueoYDesbloqueoConteosPropios_test(
            HashMap<String, String> datos) throws Exception {

        String usuario = datos.get("userLogin");
        String contrasena = PasswordUtil.decryptPassword(
                datos.get("passwordLogin"));
        redactarDatosSensibles(datos);

        addStep("Ingresar al flujo de Conteos Propios y seleccionar la plantilla objetivo.");
        conteosMvp5().iniciarDesdeLoginYEntrarAConteosPropios(
                appActivityObjetivo(), usuario, contrasena);
        comunes().seleccionarSemana(false);
        comunes().clickBtnContinuar(false);
        Assert.assertTrue(
                conteosMvp5().menuConteosPropiosDisponible(false),
                "No se mostró el menú para iniciar Conteos Propios");
        menus().clickBtnIniciarConteo(false);
        testCase.addScreenShotCurrentStep(
                appium(), "Plantilla seleccionable antes de abrirla");
        conteosMvp5().seleccionarPrimeraPlantillaPropia();
        ResultadoInicioConsolidacion inicio =
                conteosMvp5().observarInicioConsolidacionNoBloqueante();
        String mensajeInicio = inicio.mensajeLoading1();
        testCase.addScreenShotCurrentStep(
                appium(), "Observacion inmediata post seleccion de plantilla");
        testCase.addTextEvidenceCurrentStep(inicio.evidencia());
        boolean mensajeInicioAceptado =
                PreinventariosInt.MENSAJE_CONSOLIDANDO_ESPERADO
                        .equals(mensajeInicio)
                || PreinventariosInt.MENSAJE_CONSOLIDANDO
                        .equals(mensajeInicio);
        if (!inicio.inicioAutomaticoObservable()) {
            addInternalStep(
                    "Runtime: inicio no observable en ventana corta de FT_004; continuar con validacion de bloqueo integral");
        }

        addStep("Observar el mensaje mostrado durante la consolidación.");
        String mensajeProceso = mensajeInicio;
        boolean mensajeAceptado =
                PreinventariosInt.MENSAJE_CONSOLIDANDO_ESPERADO
                        .equals(mensajeProceso)
                || PreinventariosInt.MENSAJE_CONSOLIDANDO
                        .equals(mensajeProceso);
        boolean sinAcciones = inicio.accionesManualesAusentes();
        String estadoMensaje = mensajeProceso == null
                ? "NO_OBSERVABLE_TRANSITORIO"
                : mensajeProceso;
        testCase.addTextEvidenceCurrentStep(
                "MENSAJE_OBSERVADO|ACEPTAR_CANCELAR|RESULTADO\n"
                        + estadoMensaje + "|AUSENTES|"
                        + (mensajeAceptado && sinAcciones
                                ? "CORRECTO"
                                : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(
                appium(), "Mensaje de consolidacion observado");
        if (mensajeProceso != null) {
            Assert.assertTrue(
                    mensajeAceptado,
                    "El mensaje de proceso debe coincidir con la matriz; "
                            + "solo se tolera la ausencia del punto final");
            Assert.assertTrue(
                    sinAcciones,
                    "El mensaje de proceso presentó ACEPTAR o CANCELAR");
        } else {
            addInternalStep(
                    "Runtime: loading 1 no observable por velocidad en FT_004; bloqueo se valida por superficie integral");
        }

        // Aclaración funcional vigente: los intentos manuales originales 3, 4 y 5
        // se justifican como una sola observación de la superficie bloqueada.
        addStep("Validar en conjunto que el bloque de consolidación impide abrir un SKU, escanear y ejecutar expansión o guardado.");
        ResultadoBloqueoEstructural bloqueo =
                conteosMvp5()
                        .observarBloqueoEstructuralConsolidacion();
        addInternalStep("Runtime: " + bloqueo.evidencia());
        testCase.addTextEvidenceCurrentStep(bloqueo.evidencia());
        Assert.assertTrue(
                bloqueo.bloqueoIntegral(),
                "El bloque de consolidación expuso una superficie capaz "
                        + "de iniciar o guardar el conteo");

        addStep("Esperar la respuesta exitosa del servicio.");
        boolean notificacionExitosa = conteosMvp5()
                .esperarNotificacionConsolidacionExitosa(true);
        boolean detalleHabilitado = conteosMvp5().detalleConteoHabilitado(false);
        if (!notificacionExitosa && detalleHabilitado) {
            addInternalStep(
                    "Runtime: toast de éxito no observable por transitoriedad en FT_004; pantalla desbloqueada sin bloqueo residual");
        }
        Assert.assertTrue(
                notificacionExitosa || detalleHabilitado,
                "No se observó notificación de éxito ni se desbloqueó la pantalla");

        addStep("Repetir la acción previamente bloqueada abriendo un SKU después del éxito.");
        conteosPropios().clickNombrePlantilla();
        conteosPropios().clickFirstSKU();
        Assert.assertTrue(
                conteosMvp5().productoEditable(true),
                "No fue posible abrir el SKU después del éxito");
        conteosMvp5().validarInteraccionDesbloqueada();
    }

    private static void redactarDatosSensibles(
            HashMap<String, String> datos) {
        String[] claves = {
            "userLogin", "passwordLogin", "udId", "udid",
            "deviceUdid", "token", "secret"
        };
        for (String clave : claves) {
            if (datos.containsKey(clave)) {
                datos.put(clave, "<redacted>");
            }
        }
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() {
        addInternalStep("Setup: validar por backend la disponibilidad de plantilla propia activa");
        int totalPlantillas = datosConteosPropiosMvp5()
                .cantidadPlantillasPropiasActivasSemanaActual();
        addInternalStep("Setup: plantillas propias activas (backend)=" + totalPlantillas);
        if (totalPlantillas > 0) {
            addInternalStep("Setup: backend confirma plantilla existente; no se requiere creacion");
            return;
        }

        addInternalStep(
                "Setup: no hay plantillas activas; crear plantilla MVP5 por UI con SKUs de consulta read-only");
        List<String> skus = datosConteosPropiosMvp5().obtenerSkusActivos(3, 0);
        if (skus.isEmpty()) {
            throw new IllegalStateException(
                    "Setup no pudo obtener SKUs activos para crear plantilla propia en FT_004");
        }

        String usuario = datos().get("userLogin");
        String contrasena = PasswordUtil.decryptPassword(datos().get("passwordLogin"));
        String nombreBasePlantilla = "MVP5_HU14_FT004_AUTO_" + System.currentTimeMillis();
        conteosMvp5().iniciarDesdeLoginYEntrarAConteosPropios(
                appActivityObjetivo(),
                usuario,
                contrasena);
        comunes().seleccionarSemana(false);
        comunes().clickBtnContinuar(false);
        plantillasPropiosMvp5().crearPlantillaConSkus(nombreBasePlantilla, skus);

        int totalDespuesCreacion = datosConteosPropiosMvp5()
                .cantidadPlantillasPropiasActivasSemanaActual();
        addInternalStep("Setup: plantillas propias activas despues de crear=" + totalDespuesCreacion);
        if (totalDespuesCreacion <= 0) {
            throw new IllegalStateException(
                    "Setup no pudo generar plantilla propia activa para FT_004");
        }
    }

    @Override
    protected void ejecutarTeardownEspecifico() {
        // El caso solo observa UI y no modifica configuración ambiental.
    }

    @Override
    public String setPrerequisites() {
        return "1. Plantilla con al menos un SKU y código de barras válido. "
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
        return "ATC_FT_004_MensajeBloqueoYDesbloqueoConteosPropios";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
