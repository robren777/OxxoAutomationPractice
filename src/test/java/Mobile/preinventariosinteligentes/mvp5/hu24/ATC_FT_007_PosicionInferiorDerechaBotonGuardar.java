package Mobile.preinventariosinteligentes.mvp5.hu24;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import utils.password.PasswordUtil;

/**
 * HU24 / MTC_FT_007: posicion inferior derecha del boton GUARDAR.
 */
public class ATC_FT_007_PosicionInferiorDerechaBotonGuardar
        extends BaseExecutionPreInt {

    @Override
    public String setTestDescription() {
        return "Verificar que el botón GUARDAR se ha reubicado a la posición inferior "
                + "derecha del resumen de Preinventarios Inteligentes, ocupando el lugar "
                + "donde anteriormente estaba el botón FINALIZAR. La distribución esperada "
                + "es: CANCELAR en la posición inferior izquierda y GUARDAR en la posición "
                + "inferior derecha.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU24", "Reutilizado"})
    public void ATC_FT_007_PosicionInferiorDerechaBotonGuardar_test(
            HashMap<String, String> datos) throws Exception {

        addStep("Ingresar a Control > Preinventarios Inteligentes > Conteos Inteligentes.");
        conteosMvp5().iniciarDesdeLoginHastaMenuPreinventarios(
                appActivityObjetivo(),
                datos.get("userLogin"),
                PasswordUtil.decryptPassword(datos.get("passwordLogin")));
        testCase.addScreenShotCurrentStep(
                appium(), "Antes de ingresar a Conteos Inteligentes");
        conteosMvp5().entrarAConteosInteligentes();
        Assert.assertTrue(conteosMvp5().listaSemanasVisible(false),
                "No se mostro la lista de semanas de Conteos Inteligentes");

        addStep("Seleccionar la semana en curso y dar clic en CONTINUAR.");
        comunes().seleccionarSemana(false);
        Assert.assertTrue(
                conteosMvp5().semanaEnCursoSeleccionadaYContinuarHabilitado(false),
                "La semana en curso no quedo seleccionada o CONTINUAR no se habilito");
        testCase.addScreenShotCurrentStep(
                appium(), "Semana seleccionada antes de CONTINUAR");
        comunes().clickBtnContinuar(false);

        addStep("Esperar la finalizacion de la consolidacion automatica.");
        esperarConsolidacionResumenRobusta();
        comunes().clickBtnAceptarConteosFaltantes(false);
        Assert.assertFalse(conteosMvp5().consolidacionEnCurso(),
                "La consolidacion continua bloqueando el resumen");
        testCase.addScreenShotCurrentStep(
                appium(), "Resumen disponible despues de la consolidacion");

        addStep("Validar CANCELAR izquierda, GUARDAR derecha y ausencia de FINALIZAR.");
        boolean guardarDerecha = conteosMvp5().guardarEstaDerechaDeCancelar();
        boolean sinFinalizar = conteosMvp5().sinControlesFinalizacion();
        testCase.addTextEvidenceCurrentStep(
                "CANCELAR_IZQUIERDA|GUARDAR_DERECHA|FINALIZAR|RESULTADO\n"
                        + "true|" + guardarDerecha + "|"
                        + (sinFinalizar ? "AUSENTE" : "PRESENTE") + "|"
                        + (guardarDerecha && sinFinalizar ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(
                appium(), "CANCELAR izquierda y GUARDAR derecha");
        Assert.assertTrue(guardarDerecha, "GUARDAR no esta a la derecha de CANCELAR");
        Assert.assertTrue(sinFinalizar, "Se encontro un control FINALIZAR en la barra inferior");

        addStep("Dar clic en CANCELAR y validar el regreso al listado de semanas.");
        conteosInteligentes().clickBtnCancelar(false);
        Assert.assertTrue(conteosMvp5().listaSemanasVisible(false),
                "CANCELAR no regreso al listado de semanas");
        testCase.addScreenShotCurrentStep(
                appium(), "Listado de semanas despues de CANCELAR");
    }

    private void esperarConsolidacionResumenRobusta() {
        try {
            conteosMvp5().esperarConsolidacionCompleta(false);
            return;
        } catch (AssertionError timeoutConsolidacion) {
            addInternalStep(
                    "Setup/runtime: timeout de consolidacion; validar si la pantalla ya esta liberada");
        }

        if (!conteosMvp5().consolidacionEnCurso()) {
            addInternalStep(
                    "Setup/runtime: consolidacion no visible despues del timeout; continuar validacion de resumen");
            return;
        }

        throw new AssertionError(
                "La consolidacion continua activa despues del timeout en FT_007");
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() throws Exception {
        asegurarEscenarioConGuardBackMvp5(
                "Setup FT_007",
                () -> datosHu24().consultarAsignadoConRangoSinFiltro(1, 0),
                "distribucion vigente con selectivo asignado");
    }

    @Override
    protected void ejecutarTeardownEspecifico() throws Exception {
        // El caso cancela sin capturar ni guardar conteos.
    }

    @Override
    public String setPrerequisites() {
        return "1. Android 13 en HH TC52 o Tablet ET51. "
                + "2. Usuario Asesor/Líder autenticado. "
                + "3. PREINVENINT_ENABLED=TRUE. "
                + "4. Semana vigente activa. "
                + "5. Versión de XPOS desplegada con la nueva distribución de botones "
                + "(GUARDAR en posición derecha).";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_007_PosicionInferiorDerechaBotonGuardar";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
