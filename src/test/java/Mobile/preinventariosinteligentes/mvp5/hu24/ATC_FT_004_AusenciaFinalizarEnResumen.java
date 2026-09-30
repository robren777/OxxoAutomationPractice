package Mobile.preinventariosinteligentes.mvp5.hu24;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import utils.password.PasswordUtil;

/**
 * HU24 / MTC_FT_004: ausencia de FINALIZAR en el resumen.
 */
public class ATC_FT_004_AusenciaFinalizarEnResumen
        extends BaseExecutionPreInt {

    @Override
    public String setTestDescription() {
        return "Verificar que el botón " + (char) 34 + "FINALIZAR" + (char) 34
                + " ha sido eliminado del resumen de Preinventarios Inteligentes. "
                + "La validación debe realizarse tanto con conteos incompletos como en el "
                + "escenario donde todos los conteos están capturados, ya que en el "
                + "comportamiento anterior dicha condición habilitaba el botón "
                + (char) 34 + "FINALIZAR" + (char) 34 + ".";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU24", "Reutilizado"})
    public void ATC_FT_004_AusenciaFinalizarEnResumen_test(
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

        addStep("Validar la barra inferior del resumen y la ausencia de FINALIZAR.");
        boolean sinFinalizar = conteosMvp5().sinControlesFinalizacion();
        boolean guardarDerecha = conteosMvp5().guardarEstaDerechaDeCancelar();
        testCase.addTextEvidenceCurrentStep(
                "CANCELAR_IZQUIERDA|GUARDAR_DERECHA|FINALIZAR|RESULTADO\n"
                        + "true|" + guardarDerecha + "|"
                        + (sinFinalizar ? "AUSENTE" : "PRESENTE") + "|"
                        + (guardarDerecha && sinFinalizar ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(
                appium(), "Resumen sin FINALIZAR");
        Assert.assertTrue(sinFinalizar, "Se mostro FINALIZAR en el resumen del preinventario");
        Assert.assertTrue(guardarDerecha,
                "La barra inferior no conserva CANCELAR izquierda y GUARDAR derecha");
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
                "La consolidacion continua activa despues del timeout en FT_004");
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() throws Exception {
        asegurarEscenarioConGuardBackMvp5(
                "Setup FT_004",
                () -> datosHu24().consultarAsignadoConRangoSinFiltro(1, 0),
                "distribucion vigente con selectivo asignado");
    }

    @Override
    protected void ejecutarTeardownEspecifico() throws Exception {
        // Caso de solo lectura en la UI; no captura ni persiste conteos.
    }

    @Override
    public String setPrerequisites() {
        return "1. Android 13 en HH TC52 o Tablet ET51. "
                + "2. Usuario Asesor/Líder autenticado. "
                + "3. PREINVENINT_ENABLED=TRUE. "
                + "4. Semana vigente activa con todos los selectivos y sus SKUs contados "
                + "y guardados (para cubrir el escenario que habilitaba el botón). "
                + "5. Versión de XPOS desplegada con el botón " + (char) 34
                + "FINALIZAR" + (char) 34 + " eliminado del resumen.";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_004_AusenciaFinalizarEnResumen";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
