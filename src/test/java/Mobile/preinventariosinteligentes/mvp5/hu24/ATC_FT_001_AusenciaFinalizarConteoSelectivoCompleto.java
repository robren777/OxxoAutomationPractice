package Mobile.preinventariosinteligentes.mvp5.hu24;

import java.time.Duration;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaSelectivoHu24.Escenario;
import Mobile.preinventariosinteligentes.mvp5.support.Configuraciones;
import Mobile.preinventariosinteligentes.mvp5.support.PreflightHu24Support;
import utils.password.PasswordUtil;

/**
 * HU24 / MTC_FT_001 con preflight backend-first.
 */
public class ATC_FT_001_AusenciaFinalizarConteoSelectivoCompleto
        extends BaseExecutionPreInt {

    private Escenario escenarioPreflight;

    @Override
    public String setTestDescription() {
        return "Verificar que al completar el conteo de todos los SKUs de un selectivo "
                + "en Conteos Inteligentes, el sistema NO muestra el botón "
                + (char) 34 + "FINALIZAR CONTEO" + (char) 34
                + " que existía en versiones anteriores.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU24", "Reutilizado"})
    public void ATC_FT_001_AusenciaFinalizarConteoSelectivoCompleto_test(
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

        addStep("Seleccionar la semana en curso.");
        comunes().seleccionarSemana(false);
        Assert.assertTrue(
                conteosMvp5().semanaEnCursoSeleccionadaYContinuarHabilitado(false),
                "La semana en curso no quedo seleccionada o CONTINUAR no se habilito");
        testCase.addScreenShotCurrentStep(
                appium(), "Semana en curso seleccionada");

        addStep("Dar clic en CONTINUAR.");
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

        addInternalStep("Preflight backend resuelto para HU24-001 folio="
                + escenarioPreflight.folio()
                + " total=" + escenarioPreflight.total());

        addStep("Expandir el selectivo objetivo y validar el estado inicial.");
        expandirSelectivoConRecuperacionPorColaborador();
        CachedRowSet pendientes = datosHu24()
                .consultarPendientesPositivos(escenarioPreflight.folio());
        Assert.assertTrue(
                pendientes.size() > 0,
                "El selectivo elegido por preflight no contiene SKU positivos pendientes");
        Assert.assertTrue(
                conteosHu24().selectivoExpandidoCoincide(
                        escenarioPreflight.encabezado(), false),
                "Se expandio un selectivo diferente al elegido por preflight");
        boolean sinFinalizarInicial =
                conteosHu24().sinControlesFinalizacion(false);
        testCase.addTextEvidenceCurrentStep(
                "ESTADO_SELECTIVO|PENDIENTES|FINALIZAR_CONTEO|RESULTADO\n"
                        + "INICIAL|" + pendientes.size() + "|"
                        + (sinFinalizarInicial ? "AUSENTE" : "PRESENTE") + "|"
                        + (sinFinalizarInicial ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(
                appium(), "Selectivo inicial sin FINALIZAR CONTEO");
        Assert.assertTrue(
                sinFinalizarInicial,
                "Se mostro FINALIZAR CONTEO antes de completar el selectivo");

        addStep("Contabilizar mediante tap todos los SKU positivos pendientes.");
        conteosHu24().capturarConteosPendientes(pendientes, "2", false);
        testCase.addScreenShotCurrentStep(
                appium(), "SKUs positivos contabilizados");

        addStep("Observar el estado final y llegar al pie del selectivo.");
        conteosHu24().desplazarAlFinalSelectivo(false);
        boolean imprimirVisible = conteosHu24().imprimirVisible(false);
        boolean sinFinalizarCompleto =
                conteosHu24().sinControlesFinalizacion(false);
        testCase.addTextEvidenceCurrentStep(
                "ESTADO_SELECTIVO|IMPRIMIR|FINALIZAR_CONTEO|RESULTADO\n"
                        + "COMPLETO|" + imprimirVisible + "|"
                        + (sinFinalizarCompleto ? "AUSENTE" : "PRESENTE") + "|"
                        + (imprimirVisible && sinFinalizarCompleto
                                ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(
                appium(), "Selectivo completo sin FINALIZAR CONTEO");
        Assert.assertTrue(imprimirVisible, "No se alcanzo el pie IMPRIMIR del selectivo");
        Assert.assertTrue(
                sinFinalizarCompleto,
                "Se mostro FINALIZAR CONTEO al completar el selectivo");

        addStep("Guardar los cambios y confirmar el guardado.");
        Assert.assertTrue(
                conteosHu24().guardarEstaDerechaDeCancelar(false),
                "GUARDAR no esta ubicado a la derecha de CANCELAR");
        testCase.addScreenShotCurrentStep(
                appium(), "GUARDAR disponible antes de abrir la confirmacion");
        conteosInteligentes().clickBtnGuardar(false);
        testCase.addScreenShotCurrentStep(
                appium(), "Confirmacion global antes de GUARDAR");
        comunes().clickBtnAceptar(false);
        boolean guardadoExitoso = conteosHu24().guardadoExitosoVisible(
                Duration.ofSeconds(60), false);
        testCase.addScreenShotCurrentStep(
                appium(), "Conteo guardado con exito");
        Assert.assertTrue(guardadoExitoso, "No se mostro la confirmacion de guardado exitoso");
        conteosInteligentes().esperaSeOculteMensajeGuardado();

        addStep("Validar en BD la persistencia del selectivo completo.");
        int inconsistencias = datosHu24()
                .contarInconsistenciasFinales(escenarioPreflight.folio());
        testCase.addTextEvidenceCurrentStep(
                "IO_FOLIO|TOTAL_SKU|INCONSISTENCIAS|RESULTADO\n"
                        + escenarioPreflight.folio() + "|"
                        + escenarioPreflight.total() + "|"
                        + inconsistencias + "|"
                        + (inconsistencias == 0 ? "COMPLETO" : "INCONSISTENTE"));
        Assert.assertEquals(
                inconsistencias,
                0,
                "El selectivo conserva SKU incompatibles con el estado completo");
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() throws Exception {
        addInternalStep("Setup: preflight backend-first para HU24-001");
        PreflightHu24Support preflight = new PreflightHu24Support();
        Configuraciones configuracion = Configuraciones.cargar(datos());
        int maximoSkuPreferente = (int) configuracion.valorLong(
                "preinv.mvp5.hu24.preflight.max.skus", 15L);
        boolean permitirFallbackSinLimite = configuracion.valorBooleano(
                "preinv.mvp5.hu24.preflight.permitir.folio.mayor", false);
        String letraReferenciaConfigurada = configuracion.opcional(
                "preinv.mvp5.hu24.preflight.letra.referencia");
        final String letraReferencia = (letraReferenciaConfigurada == null
                || letraReferenciaConfigurada.trim().isEmpty())
                        ? "A"
                        : letraReferenciaConfigurada;
        escenarioPreflight = asegurarEscenarioConGuardBackMvp5(
                "Setup FT_001",
                () -> preflight.validarEscenarioSelectivoCompleto(
                        datosHu24(),
                        maximoSkuPreferente,
                        letraReferencia,
                        permitirFallbackSinLimite),
                "escenario HU24-001 con pendientes positivos");
        addInternalStep("Setup: "
                + PreflightHu24Support.evidenciaEscenario(
                        "HU24-001",
                        escenarioPreflight.folio(),
                        escenarioPreflight.total(),
                        null));
    }

    private void expandirSelectivoConRecuperacionPorColaborador() {
        try {
            conteosHu24().expandirSelectivo(escenarioPreflight.encabezado(), false);
            return;
        } catch (NoSuchElementException selectivoNoVisible) {
            String colaboradorAsignado = conteosHu24()
                    .obtenerColaboradorAsignado(false);
            Escenario escenarioColaborador = datosHu24()
                    .consultarAsignadoMaximo15(colaboradorAsignado);
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
                + "4. Semana vigente con clasificación y asignación de colaboradores previas. "
                + "5. Al menos un selectivo con mínimo dos SKUs sin conteo capturado. "
                + "6. Versión de APK sin el botón " + (char) 34
                + "FINALIZAR CONTEO" + (char) 34 + ".";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_001_AusenciaFinalizarConteoSelectivoCompleto";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
