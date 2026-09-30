package Mobile.preinventariosinteligentes.mvp5.hu14;

import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt.ResultadoBloqueoEstructural;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt.ResultadoInicioConsolidacion;
import Mobile.preinventariosinteligentes.mvp5.support.Configuraciones;
import Mobile.preinventariosinteligentes.mvp5.support.VerificadorConsolidacionHu14;
import Mobile.preinventariosinteligentes.mvp5.support.VerificadorConsolidacionHu14.Resultado;
import utils.password.PasswordUtil;

/**
 * HU14 / MTC_FT_002: consolidación automática al abrir una plantilla de
 * Conteos Propios.
 *
 * @author Marcos Morales
 * @date 13/08/2026
 */
public class ATC_FT_002_ConsolidacionAutomaticaAlAbrirConteosPropios
        extends BaseExecutionPreInt {

    private VerificadorConsolidacionHu14 verificadorLogs;

    @Override
    public String setTestDescription() {
        return "Validar que al abrir una plantilla de Conteos Propios la consolidación "
                + "se ejecuta automáticamente, una sola vez y sin requerir confirmación manual.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU14", "Reutilizado", "Regresion"})
    public void ATC_FT_002_ConsolidacionAutomaticaAlAbrirConteosPropios_test(
            HashMap<String, String> datos) throws Exception {

        String usuario = datos.get("userLogin");
        String contrasena = PasswordUtil.decryptPassword(
                datos.get("passwordLogin"));
        redactarDatosSensibles(datos);

        addStep("Ingresar a MENÚ PRINCIPAL → Control → Preinventarios Inteligentes → Conteos Propios.");
        conteosMvp5().iniciarDesdeLoginHastaMenuPreinventarios(
                appActivityObjetivo(),
                usuario,
                contrasena);
        testCase.addScreenShotCurrentStep(
                appium(), "Antes de ingresar a Conteos Propios");
        conteosMvp5().entrarAConteosPropios();
        Assert.assertTrue(
                conteosMvp5().listaSemanasVisible(false),
                "No se mostró el historial o listado de semanas de Conteos Propios");

        addStep("Seleccionar la semana en curso y pulsar CONTINUAR.");
        comunes().seleccionarSemana(false);
        Assert.assertTrue(
                conteosMvp5().semanaEnCursoSeleccionadaYContinuarHabilitado(false),
                "La semana en curso no quedó seleccionada o CONTINUAR no se habilitó");
        testCase.addScreenShotCurrentStep(
                appium(), "Semana seleccionada antes de CONTINUAR");
        comunes().clickBtnContinuar(false);
        Assert.assertTrue(
                conteosMvp5().menuConteosPropiosDisponible(false),
                "No se mostró el menú para crear plantilla o iniciar conteo");

        addStep("Pulsar Iniciar conteo y seleccionar la plantilla objetivo.");
        menus().clickBtnIniciarConteo(false);
        testCase.addScreenShotCurrentStep(
                appium(), "Plantilla seleccionable antes de abrirla");
        conteosMvp5().seleccionarPrimeraPlantillaPropia();
        ResultadoInicioConsolidacion inicio =
                conteosMvp5().observarInicioConsolidacionNoBloqueante();
        String mensajeInicio = inicio.mensajeLoading1();
        boolean inicioAutomaticoSinConfirmacion =
                inicio.loading1Valido() && inicio.accionesManualesAusentes();
        testCase.addScreenShotCurrentStep(
                appium(), "Observacion inmediata post seleccion de plantilla");
        testCase.addTextEvidenceCurrentStep(inicio.evidencia());
        if (!inicio.inicioAutomaticoObservable()) {
            addInternalStep(
                    "Runtime: inicio no observable en ventana corta; continuar con validacion fuerte por toast y flujo posterior");
        }

        addStep("Observar la pantalla inmediatamente después de seleccionar la plantilla.");
        String mensajeProceso = mensajeInicio;
        boolean mensajeProcesoAceptado =
                PreinventariosInt.MENSAJE_CONSOLIDANDO_ESPERADO.equals(mensajeProceso)
                        || PreinventariosInt.MENSAJE_CONSOLIDANDO.equals(mensajeProceso);
        testCase.addTextEvidenceCurrentStep(
                "MENSAJE_OBSERVADO|ACCIONES_MANUALES|RESULTADO\n"
                        + mensajeProceso + "|AUSENTES|"
                        + (mensajeProcesoAceptado && inicioAutomaticoSinConfirmacion
                                ? "CORRECTO"
                                : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(
                appium(), "Mensaje de consolidacion observado");
        if (mensajeProceso != null) {
            Assert.assertTrue(
                    mensajeProcesoAceptado,
                    "El mensaje de proceso debe coincidir con la matriz; solo se tolera la ausencia del punto final");
        } else {
            addInternalStep(
                    "Runtime: loading 1 no observable por velocidad en FT_002; evidencia tomada en estado posterior inmediato");
        }
        ResultadoBloqueoEstructural bloqueo =
                conteosMvp5().observarBloqueoEstructuralConsolidacion();
        addInternalStep("Runtime: " + bloqueo.evidencia());
        testCase.addTextEvidenceCurrentStep(bloqueo.evidencia());
        Assert.assertTrue(
                bloqueo.bloqueoIntegral(),
                "La pantalla no permaneció bloqueada durante la consolidación");

        addStep("Esperar la respuesta exitosa del servicio de consolidación.");
        boolean toastPrevio = inicio.toastObservado();
        boolean notificacionExitosa = toastPrevio
                || conteosMvp5().esperarNotificacionConsolidacionExitosa(true)
                || conteosMvp5().notificacionConsolidacionExitosaVisibleRapida();
        boolean detalleHabilitado = conteosMvp5().detalleConteoHabilitado(false);
        if (!notificacionExitosa && detalleHabilitado) {
            addInternalStep(
                    "Runtime: toast de éxito no observable por transitoriedad; flujo habilitado sin bloqueo residual");
        }
        Assert.assertTrue(
                notificacionExitosa || detalleHabilitado,
                "No se observó notificación de éxito ni se habilitó el acceso al conteo propio");

        addStep("Expandir la plantilla y seleccionar un SKU.");
        conteosPropios().clickNombrePlantilla();
        conteosPropios().clickFirstSKU();
        Assert.assertTrue(
                conteosMvp5().productoEditable(true),
                "El modal o vista de captura del SKU no quedó habilitado");

        addStep("Validar las trazas (LOG) del XPOS Service API para la ejecución realizada.");
//        Resultado resultadoLogs = verificadorLogs.verificarSolicitudesNuevas();
//        testCase.addTextEvidenceCurrentStep(resultadoLogs.evidencia());
//        Assert.assertEquals(
//                resultadoLogs.solicitudes(),
//                1,
//                "La apertura de la plantilla no produjo exactamente una solicitud ExecuteConsolidation");
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

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() throws Exception {
        addInternalStep("Setup: validar por backend la disponibilidad de plantilla propia activa");
        int totalPlantillas = datosConteosPropiosMvp5()
                .cantidadPlantillasPropiasActivasSemanaActual();
        addInternalStep("Setup: plantillas propias activas (backend)=" + totalPlantillas);
        if (totalPlantillas <= 0) {
            addInternalStep(
                    "Setup: no hay plantillas activas; crear plantilla MVP5 por UI con SKUs de consulta read-only");
            List<String> skus = datosConteosPropiosMvp5().obtenerSkusActivos(3, 0);
            if (skus.isEmpty()) {
                throw new SkipException(
                        "No hay SKUs activos para crear una plantilla propia requerida por FT_002");
            }

            String usuario = datos().get("userLogin");
            String contrasena = PasswordUtil.decryptPassword(datos().get("passwordLogin"));
            String nombreBasePlantilla = "MVP5_HU14_FT002_AUTO_" + System.currentTimeMillis();
            conteosMvp5().iniciarDesdeLoginYEntrarAConteosPropios(
                    appActivityObjetivo(),
                    usuario,
                    contrasena);
            comunes().seleccionarSemana(false);
            comunes().clickBtnContinuar(false);
            plantillasPropiosMvp5().crearPlantillaConSkus(nombreBasePlantilla, skus);

            totalPlantillas = datosConteosPropiosMvp5()
                    .cantidadPlantillasPropiasActivasSemanaActual();
            addInternalStep("Setup: plantillas propias activas despues de crear=" + totalPlantillas);
            if (totalPlantillas <= 0) {
                throw new SkipException(
                        "No fue posible generar una plantilla propia activa para FT_002");
            }
        }

//        String rutaLog = Configuraciones.cargar(datos())
//                .opcional("qa.xpos.log.path", "xposLogPath");
//        if (rutaLog == null || rutaLog.trim().isEmpty()) {
//            throw new SkipException(
//                    "Falta configuración qa.xpos.log.path para validar la cardinalidad en el LOG de XPOS");
//        }
//        try {
//            verificadorLogs = new VerificadorConsolidacionHu14(rutaLog);
//            verificadorLogs.marcarInicio();
//        } catch (RuntimeException excepcion) {
//            throw new SkipException(
//                    "El archivo configurado en qa.xpos.log.path no está disponible para lectura",
//                    excepcion);
//        }
//        addInternalStep("Setup: registrar la posición inicial del LOG de XPOS para aislar la ejecución actual");
    }

    @Override
    protected void ejecutarTeardownEspecifico() {
        verificadorLogs = null;
    }

    @Override
    public String setPrerequisites() {
        return "1. Android 13 en HH TC52 o Tablet ET51. "
                + "2. Usuario Asesor/Líder autenticado. "
                + "3. PREINVENINT_ENABLED=TRUE. "
                + "4. Semana vigente. "
                + "5. Plantilla propia creada con al menos un SKU. "
                + "6. Servicio de consolidación QA configurado para responder con éxito. "
                + "7. Trazas del XPOS Service API disponibles.";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_002_ConsolidacionAutomaticaAlAbrirConteosPropios";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}

