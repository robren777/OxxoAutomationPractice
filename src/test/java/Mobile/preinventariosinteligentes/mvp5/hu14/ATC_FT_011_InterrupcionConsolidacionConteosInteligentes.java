package Mobile.preinventariosinteligentes.mvp5.hu14;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt.ResultadoInicioConsolidacion;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt.ResultadoInterrupcionApp;
import utils.password.PasswordUtil;

/**
 * HU14 / MTC_FT_011: interrupción manual durante la consolidación en
 * Conteos Inteligentes.
 *
 * @author Marcos Morales
 * @date 13/08/2026
 */
public class ATC_FT_011_InterrupcionConsolidacionConteosInteligentes
        extends BaseExecutionPreInt {

    @Override
    public String setTestDescription() {
        return "Validar que durante la interrupción manual del proceso de consolidación, "
                + "la aplicación mantiene la integridad de los datos/flujo en el módulo "
                + "de Conteos Inteligentes.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU14", "Reutilizado", "Regresion"})
    public void ATC_FT_011_InterrupcionConsolidacionConteosInteligentes_test(
            HashMap<String, String> datos) throws Exception {

        String usuario = datos.get("userLogin");
        String contrasena = PasswordUtil.decryptPassword(
                datos.get("passwordLogin"));
        redactarDatosSensibles(datos);

        addStep("Ingresar a MENÚ PRINCIPAL → Control → Preinventarios Inteligentes → Conteos Inteligentes.");
        conteosMvp5().iniciarDesdeLoginHastaMenuPreinventarios(
                appActivityObjetivo(), usuario, contrasena);
        testCase.addScreenShotCurrentStep(
                appium(), "Antes de ingresar a Conteos Inteligentes");
        conteosMvp5().entrarAConteosInteligentes();
        Assert.assertTrue(
                conteosMvp5().listaSemanasVisible(false),
                "No se mostró la lista de semanas de Conteos Inteligentes");

        addStep("Seleccionar la semana en curso.");
        comunes().seleccionarSemana(false);
        Assert.assertTrue(
                conteosMvp5()
                        .semanaEnCursoSeleccionadaYContinuarHabilitado(false),
                "La semana En curso no quedó seleccionada o CONTINUAR no se habilitó");

        testCase.addScreenShotCurrentStep(
                appium(), "Semana en curso seleccionada");

        addStep("Dar clic en CONTINUAR para ingresar al detalle de la semana seleccionada.");
        testCase.addScreenShotCurrentStep(
                appium(), "Semana seleccionada antes de CONTINUAR");
        comunes().clickBtnContinuar(false);
        ResultadoInicioConsolidacion inicio =
                conteosMvp5().observarInicioConsolidacionNoBloqueante();
        boolean inicioAutomatico = inicio.inicioAutomaticoObservable();
        testCase.addTextEvidenceCurrentStep(
                inicio.evidencia());
        testCase.addScreenShotCurrentStep(
                appium(), "Observacion inmediata post CONTINUAR");
        if (!inicioAutomatico) {
            addInternalStep(
                    "Runtime: inicio no observable en ventana corta de FT_011; intentar interrupcion solo si hay ventana activa");
        }

        boolean ventanaInterrupcionActiva = inicioAutomatico
                || conteosMvp5().consolidacionEnCurso()
                || conteosMvp5().textoVisible("Cargando");
        if (!ventanaInterrupcionActiva) {
            addInternalStep(
                    "Runtime: ventana de interrupcion no activa; ejecutar reintento tecnico previo a Home");
            reingresarAConteosInteligentes(
                    appActivityObjetivo(),
                    usuario,
                    contrasena);
            comunes().clickBtnContinuar(false);
            ResultadoInicioConsolidacion inicioReintento =
                    conteosMvp5().observarInicioConsolidacionNoBloqueante();
            testCase.addTextEvidenceCurrentStep(inicioReintento.evidencia());
            ventanaInterrupcionActiva = inicioReintento.inicioAutomaticoObservable()
                    || conteosMvp5().consolidacionEnCurso()
                    || conteosMvp5().textoVisible("Cargando");
            addInternalStep(
                    "Runtime: ventana activa tras reintento tecnico="
                            + ventanaInterrupcionActiva);
        }
        Assert.assertTrue(
                ventanaInterrupcionActiva,
                "No se observó una ventana activa para ejecutar la interrupción durante consolidación");

        // Home, Regresar y Cerrar son alternativas equivalentes. Se ejerce
        // Home como representante, igual que el criterio de los casos 3 y 4.
        addStep("Presionar el boton Home/Regresar/Cerrar la aplicación mientras se muestra el mensaje \"Se está realizando la consolidación.\"");
        ResultadoInterrupcionApp interrupcion = conteosMvp5()
                .interrumpirConHomeDuranteConsolidacion(true);
        testCase.addTextEvidenceCurrentStep(interrupcion.evidencia());
        Assert.assertTrue(
                interrupcion.interrupcionObservable(),
                "No se confirmó la interrupción con Home durante la consolidación");

        addStep("Reingresar al mismo flujo Conteos Inteligentes, Semana En Curso + CONTINUAR.");
        conteosMvp5().iniciarDesdeLoginYEntrarAConteosInteligentes(
                appActivityObjetivo(), usuario, contrasena);
        comunes().seleccionarSemana(false);
        comunes().clickBtnContinuar(false);
        esperarConsolidacionRobusta();
        boolean recuperacionCompleta =
                conteosMvp5().detalleConteoHabilitado(false);
        testCase.addTextEvidenceCurrentStep(
                "EVENTO|ACCION_MANUAL|RESULTADO\n"
                        + "RECUPERACION_POST_INTERRUPCION|ESTADO_FINAL_O_DETALLE|"
                        + (recuperacionCompleta ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(
                appium(), "Flujo recuperado despues de la interrupcion");
        Assert.assertTrue(
                recuperacionCompleta,
                "El reingreso no alcanzó un estado final utilizable");

        addStep("Esperar consolidación exitosa y abrir un SKU.");
        boolean detalleDisponible =
                conteosMvp5().detalleConteoHabilitado(false);
        Assert.assertTrue(
                detalleDisponible,
                "El detalle no quedó disponible después de la interrupción");
        aceptarConteosFaltantesSiAplica();
        conteosInteligentes().clickBtnPlus();
        conteosInteligentes().clickFirstSKU();
        Assert.assertTrue(
                conteosMvp5().productoEditable(false),
                "La captura conservó un bloqueo residual después del reingreso");
        testCase.addScreenShotCurrentStep(
                appium(), "SKU editable despues de la recuperacion");
        conteosMvp5().validarInteraccionDesbloqueada();
    }

    private static void redactarDatosSensibles(HashMap<String, String> datos) {
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

    private void esperarConsolidacionRobusta() {
        try {
            conteosMvp5().esperarConsolidacionCompleta(false);
            return;
        } catch (AssertionError timeoutConsolidacion) {
            addInternalStep(
                    "Runtime: timeout de consolidacion en reingreso; validar si la pantalla ya esta liberada");
        }

        if (!conteosMvp5().consolidacionEnCurso()) {
            addInternalStep(
                    "Runtime: consolidacion no visible despues del timeout; continuar reingreso");
            return;
        }

        throw new AssertionError(
                "La consolidacion continua activa despues del timeout en FT_011");
    }

    private void aceptarConteosFaltantesSiAplica() {
        try {
            comunes().clickBtnAceptarConteosFaltantes(false);
            addInternalStep("Runtime: popup de conteos faltantes aceptado");
        } catch (RuntimeException popupNoVisible) {
            addInternalStep("Runtime: popup de conteos faltantes no visible; continuar flujo");
        }
    }

    private void reingresarAConteosInteligentes(
            String appActivity,
            String usuario,
            String contrasena) {
        conteosMvp5().iniciarDesdeLoginYEntrarAConteosInteligentes(
                appActivity,
                usuario,
                contrasena);
        comunes().seleccionarSemana(false);
        Assert.assertTrue(
                conteosMvp5().semanaEnCursoSeleccionadaYContinuarHabilitado(false),
                "No se dejó lista la semana en curso para reintento de FT_011");
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() {
        addInternalStep(
                "Setup: usar el escenario nominal HU14 disponible, "
                        + "sin modificar datos de negocio");
    }

    @Override
    protected void ejecutarTeardownEspecifico() {
        // El caso interrumpe la app, pero no modifica configuración ambiental.
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
        return "ATC_FT_011_InterrupcionConsolidacionConteosInteligentes";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
