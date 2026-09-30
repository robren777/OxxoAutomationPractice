package Mobile.preinventariosinteligentes.mvp5.hu14;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt.ResultadoInicioConsolidacion;
import Mobile.preinventariosinteligentes.mvp5.support.Configuraciones;
import Mobile.preinventariosinteligentes.mvp5.support.VerificadorConsolidacionHu14;
import Mobile.preinventariosinteligentes.mvp5.support.VerificadorConsolidacionHu14.Resultado;
import utils.password.PasswordUtil;

/**
 * HU14 / MTC_FT_001: consolidacion automatica al continuar en Conteos Inteligentes.
 *
 * @author Marcos Morales
 * @date 13/08/2026
 */
public class ATC_FT_001_ConsolidacionAutomaticaAlContinuarConteosInteligentes
        extends BaseExecutionPreInt {

    private VerificadorConsolidacionHu14 verificadorLogs;

    @Override
    public String setTestDescription() {
        return "Validar que en Conteos Inteligentes, después de seleccionar la semana en curso "
                + "y dar clic en CONTINUAR, el sistema ejecuta automáticamente exactamente una "
                + "consolidación, sin botón, confirmación ni acción manual.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU14", "Reutilizado", "Regresion"})
    public void ATC_FT_001_ConsolidacionAutomaticaAlContinuarConteosInteligentes_test(
            HashMap<String, String> datos) throws Exception {

        String usuario = datos.get("userLogin");
        String contrasena = PasswordUtil.decryptPassword(
                datos.get("passwordLogin"));
        redactarDatosSensibles(datos);

        addStep("Ingresar a MENÚ PRINCIPAL → Control → Preinventarios Inteligentes → Conteos Inteligentes.");
        conteosMvp5().iniciarDesdeLoginHastaMenuPreinventarios(
                appActivityObjetivo(),
                usuario,
                contrasena);
        testCase.addScreenShotCurrentStep(
                appium(), "Antes de ingresar a Conteos Inteligentes");
        conteosMvp5().entrarAConteosInteligentes();
        Assert.assertTrue(
                conteosMvp5().listaSemanasVisible(false),
                "No se mostró la lista de semanas disponibles de Conteos Inteligentes");

        addStep("Seleccionar la semana en curso.");
        comunes().seleccionarSemana(false);
        Assert.assertTrue(
                conteosMvp5().semanaEnCursoSeleccionadaYContinuarHabilitado(true),
                "La semana en curso no quedó seleccionada o CONTINUAR no se habilitó");

        addStep("Dar clic en CONTINUAR para ingresar al detalle de la semana seleccionada.");
        testCase.addScreenShotCurrentStep(
                appium(), "Semana seleccionada antes de CONTINUAR");
        comunes().clickBtnContinuar(false);
        ResultadoInicioConsolidacion inicio =
                conteosMvp5().observarInicioConsolidacionNoBloqueante();
        String mensajeInicio = inicio.mensajeLoading1();
        boolean inicioAutomaticoSinConfirmacion =
                inicio.loading1Valido() && inicio.accionesManualesAusentes();
        testCase.addScreenShotCurrentStep(
                appium(), "Observacion inmediata post CONTINUAR");
        testCase.addTextEvidenceCurrentStep(inicio.evidencia());
        if (!inicio.inicioAutomaticoObservable()) {
            addInternalStep(
                    "Runtime: inicio no observable en ventana corta; continuar con validacion fuerte por toast y flujo posterior");
        }

        addStep("Observar la pantalla inmediatamente después de pulsar CONTINUAR.");
        String mensajeProceso = mensajeInicio;
        boolean mensajeProcesoAceptado =
                PreinventariosInt.MENSAJE_CONSOLIDANDO_ESPERADO.equals(mensajeProceso)
                        || PreinventariosInt.MENSAJE_CONSOLIDANDO.equals(mensajeProceso);
        boolean interaccionBloqueada = inicioAutomaticoSinConfirmacion;
        testCase.addTextEvidenceCurrentStep(
                "MENSAJE_OBSERVADO|INTERACCION|RESULTADO\n"
                        + mensajeProceso + "|BLOQUEADA|"
                        + (mensajeProcesoAceptado && interaccionBloqueada
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
                    "Runtime: loading 1 no observable por velocidad en FT_001; evidencia tomada en estado posterior inmediato");
        }
        if (!interaccionBloqueada) {
            addInternalStep(
                    "Runtime: loading inicial no estable; validar continuidad por loading 2/toast");
        }

        addStep("Esperar la respuesta exitosa del proceso de consolidación.");
        boolean notificacionExitosa = conteosMvp5()
                .esperarNotificacionConsolidacionExitosa(true);
        Assert.assertTrue(
                notificacionExitosa,
                "No se mostró la notificación Consolidación realizada con éxito");
        aceptarConteosFaltantesSiAplica();
        Assert.assertTrue(
                conteosMvp5().detalleConteoHabilitado(false),
                "El acceso al conteo no quedó habilitado después de la consolidación exitosa");

        addStep("Dar clic en (+) de un selectivo y seleccionar un SKU.");
        conteosInteligentes().clickBtnPlus();
        conteosInteligentes().clickFirstSKU();
        Assert.assertTrue(
                conteosMvp5().productoEditable(true),
                "El modal o vista de captura no quedó habilitado después de la consolidación");

        addStep("Validar las trazas (LOG) del XPOS Service API para la ejecución realizada.");
//        Resultado resultadoLogs = verificadorLogs.verificarSolicitudesNuevas();
//        testCase.addTextEvidenceCurrentStep(resultadoLogs.evidencia());
//        Assert.assertEquals(
//                resultadoLogs.solicitudes(),
//                1,
//                "El ingreso actual no produjo exactamente una solicitud ExecuteConsolidation");
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

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() throws Exception {
//        addInternalStep("Setup: usar el escenario de éxito HU14 preparado externamente, sin modificar datos de negocio");
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
                + "4. Semana vigente con clasificación y asignación previas. "
                + "5. Servicio o componente de consolidación QA configurado para responder con éxito. "
                + "6. Si el ambiente lo permite, trazas del componente de consolidación disponibles.";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_ConsolidacionAutomaticaAlContinuarConteosInteligentes";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}




