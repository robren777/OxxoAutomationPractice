package Mobile.preinventariosinteligentes.mvp5.hu24;

import java.math.BigDecimal;
import java.util.HashMap;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.support.Configuraciones;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaSelectivoHu24.EscenarioParcial;
import utils.password.PasswordUtil;

/**
 * HU24 / MTC_FT_003 con preflight backend-first.
 */
public class ATC_FT_003_AusenciaFinalizarConteoSelectivoParcial
        extends BaseExecutionPreInt {

    private EscenarioParcial escenarioPreflight;

    @Override
    public String setTestDescription() {
        return "Verificar que el botón " + (char) 34 + "FINALIZAR CONTEO"
                + (char) 34 + " tampoco aparece cuando el selectivo tiene solo una parte "
                + "de sus SKUs contados, confirmando que ha sido eliminado completamente "
                + "de la interfaz y no se muestra en ningún estado del selectivo "
                + "(completo ni parcial).";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU24", "Reutilizado"})
    public void ATC_FT_003_AusenciaFinalizarConteoSelectivoParcial_test(
            HashMap<String, String> datos) throws Exception {

        addStep("Ingresar a Control > Preinventarios Inteligentes > Conteos Inteligentes.");
        conteosMvp5().iniciarDesdeLoginHastaMenuPreinventarios(
                appActivityObjetivo(),
                datos.get("userLogin"),
                PasswordUtil.decryptPassword(datos.get("passwordLogin")));
        testCase.addScreenShotCurrentStep(
                appium(), "Antes de ingresar a Conteos Inteligentes");
        conteosMvp5().entrarAConteosInteligentes();
        Assert.assertTrue(
                conteosMvp5().listaSemanasVisible(false),
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
        conteosMvp5().esperarConsolidacionCompleta(false);
        comunes().clickBtnAceptarConteosFaltantes(false);
        Assert.assertFalse(
                conteosMvp5().consolidacionEnCurso(),
                "La consolidacion continua bloqueando la pantalla");
        testCase.addScreenShotCurrentStep(
                appium(), "Resumen disponible despues de la consolidacion");

        addInternalStep("Preflight backend resuelto para HU24-003 folio="
                + escenarioPreflight.folio()
                + " total=" + escenarioPreflight.total()
                + " sku=" + escenarioPreflight.primerSku());

        addStep("Expandir el selectivo objetivo y validar su estado inicial.");
        expandirSelectivoConRecuperacionPorColaborador();
        Assert.assertTrue(
                conteosHu24().selectivoExpandidoCoincide(
                        escenarioPreflight.encabezado(), false),
                "Se expandio un selectivo diferente al elegido por BD");
        Assert.assertTrue(
                conteosHu24().fisicoPendienteVisible(
                        escenarioPreflight.primerSku(), false),
                "El primer SKU no conserva el fisico pendiente inicial");
        Assert.assertTrue(
                conteosHu24().sinControlesFinalizacion(false),
                "Se mostro FINALIZAR CONTEO en el estado inicial");
        testCase.addTextEvidenceCurrentStep(
                "ESTADO_SELECTIVO|SKU_OBJETIVO|FINALIZAR_CONTEO|RESULTADO\n"
                        + "INICIAL|" + escenarioPreflight.primerSku()
                        + "|AUSENTE|CORRECTO");
        testCase.addScreenShotCurrentStep(
                appium(), "Selectivo pendiente sin FINALIZAR CONTEO");

        addStep("Contabilizar unicamente el primer SKU con Fisico 1.");
        conteosHu24().abrirSkuPorTap(escenarioPreflight.primerSku(), false);
        Assert.assertEquals(conteosHu24().obtenerSkuModal(false),
                escenarioPreflight.primerSku(), "El modal abierto corresponde a otro SKU");
        Assert.assertTrue(conteosHu24().capturaFisicaEditable(false),
                "El primer SKU no permite capturar el fisico");
        conteosInteligentes().capturarConteo("1");
        conteosInteligentes().clickBtnGuardarCto(false);
        boolean articuloContabilizado = conteosHu24().articuloContabilizadoVisible(false);
        testCase.addScreenShotCurrentStep(appium(), "Articulo contabilizado");
        Assert.assertTrue(articuloContabilizado, "No se mostro la confirmacion Articulo contabilizado");
        BigDecimal fisicoCapturado = conteosHu24().obtenerFisicoTarjeta(
                escenarioPreflight.primerSku(), false);
        boolean otroPendiente = conteosHu24().existeOtroSkuPendiente(
                escenarioPreflight.primerSku(), false);
        boolean sinFinalizarParcial = conteosHu24().sinControlesFinalizacion(false);
        testCase.addTextEvidenceCurrentStep(
                "FISICO_CAPTURADO|OTRO_SKU_PENDIENTE|FINALIZAR_CONTEO|RESULTADO\n"
                        + fisicoCapturado + "|" + otroPendiente + "|"
                        + (sinFinalizarParcial ? "AUSENTE" : "PRESENTE") + "|"
                        + (fisicoCapturado.intValue() == 1 && otroPendiente && sinFinalizarParcial
                                ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(
                appium(), "Selectivo parcial sin FINALIZAR CONTEO");
        Assert.assertEquals(fisicoCapturado.intValueExact(), 1, "El primer SKU no muestra Fisico 1");
        Assert.assertTrue(otroPendiente, "No permanece visible otro SKU con fisico pendiente");
        Assert.assertTrue(sinFinalizarParcial,
                "Se mostro FINALIZAR CONTEO despues de contar un solo SKU");

        addStep("Desplazarse al pie del selectivo parcialmente contado.");
        conteosHu24().desplazarAlFinalSelectivo(false);
        boolean imprimirVisible = conteosHu24().imprimirVisible(false);
        boolean sinFinalizarPie = conteosHu24().sinControlesFinalizacion(false);
        boolean posicionGuardar = conteosHu24().guardarEstaDerechaDeCancelar(false);
        testCase.addTextEvidenceCurrentStep(
                "IMPRIMIR|FINALIZAR_CONTEO|GUARDAR_DERECHA|RESULTADO\n"
                        + imprimirVisible + "|"
                        + (sinFinalizarPie ? "AUSENTE" : "PRESENTE") + "|"
                        + posicionGuardar + "|"
                        + (imprimirVisible && sinFinalizarPie && posicionGuardar
                                ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(appium(), "Pie del selectivo parcial");
        Assert.assertTrue(imprimirVisible, "No se alcanzo el pie IMPRIMIR del selectivo parcial");
        Assert.assertTrue(sinFinalizarPie, "Existe un control de finalizacion al pie del selectivo parcial");
        Assert.assertTrue(posicionGuardar,
                "Los controles estandar CANCELAR y GUARDAR no conservan su distribucion");
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() throws Exception {
        addInternalStep("Setup: preflight backend-first para HU24-003");
        Configuraciones configuracion = Configuraciones.cargar(datos());
        int maximoSkuPreferente = (int) configuracion.valorLong(
                "preinv.mvp5.hu24.preflight.max.skus", 15L);
        String letraReferenciaConfigurada = configuracion.opcional(
                "preinv.mvp5.hu24.preflight.letra.referencia");
        final String letraReferencia = (letraReferenciaConfigurada == null
                || letraReferenciaConfigurada.trim().isEmpty())
                        ? "A"
                        : letraReferenciaConfigurada;

        escenarioPreflight = asegurarEscenarioConGuardBackMvp5(
                "Setup FT_003",
                () -> consultarEscenarioParcial(letraReferencia, maximoSkuPreferente),
                "escenario HU24-003 totalmente pendiente");
    }

    private EscenarioParcial consultarEscenarioParcial(
            String letraReferencia,
            int maximoSkuPreferente) {
        EscenarioParcial escenario = datosHu24()
                .consultarTotalmentePendienteCercanoALetraSinFiltro(
                        letraReferencia,
                        2,
                        maximoSkuPreferente);
        if (escenario != null) {
            return escenario;
        }
        return datosHu24().consultarTotalmentePendienteSinFiltro();
    }

    private void expandirSelectivoConRecuperacionPorColaborador() {
        try {
            conteosHu24().expandirSelectivo(escenarioPreflight.encabezado(), false);
            return;
        } catch (NoSuchElementException selectivoNoVisible) {
            String colaboradorAsignado = conteosHu24().obtenerColaboradorAsignado(false);
            EscenarioParcial escenarioColaborador = datosHu24()
                    .consultarTotalmentePendiente(colaboradorAsignado);
            if (escenarioColaborador == null) {
                throw selectivoNoVisible;
            }
            addInternalStep(
                    "Preflight backend ajustado por colaborador UI: "
                            + colaboradorAsignado
                            + " folio=" + escenarioColaborador.folio()
                            + " total=" + escenarioColaborador.total());
            escenarioPreflight = escenarioColaborador;
            conteosHu24().expandirSelectivo(escenarioPreflight.encabezado(), false);
        }
    }

    @Override
    protected void ejecutarTeardownEspecifico() throws Exception {
        escenarioPreflight = null;
    }

    @Override
    public String setPrerequisites() {
        return "1. Android 13 en HH TC52 o Tablet ET51. "
                + "2. Usuario Asesor/Líder autenticado. "
                + "3. PREINVENINT_ENABLED=TRUE. "
                + "4. Semana vigente activa. "
                + "5. Al menos un selectivo con más de un SKU sin ningún conteo capturado "
                + "(todos los Físicos en guión o NULL).";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_003_AusenciaFinalizarConteoSelectivoParcial";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
