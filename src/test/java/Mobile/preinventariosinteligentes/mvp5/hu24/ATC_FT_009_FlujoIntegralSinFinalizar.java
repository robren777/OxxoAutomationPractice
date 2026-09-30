package Mobile.preinventariosinteligentes.mvp5.hu24;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.support.Configuraciones;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaSelectivoHu24.Escenario;
import utils.password.PasswordUtil;

/**
 * HU24 / MTC_FT_009: flujo integral sin FINALIZAR y con GUARDAR reubicado.
 */
public class ATC_FT_009_FlujoIntegralSinFinalizar
        extends BaseExecutionPreInt {

    @Override
    public String setTestDescription() {
        return "Verificar de forma integral que el flujo completo de Conteos Inteligentes "
                + "funciona correctamente con los tres cambios aplicados simultáneamente: "
                + "ausencia del botón FINALIZAR CONTEO en selectivos (CA01), ausencia del "
                + "botón FINALIZAR en el resumen (CA02) y botón GUARDAR en posición "
                + "inferior derecha (CA03).";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU24", "Reutilizado"})
    public void ATC_FT_009_FlujoIntegralSinFinalizar_test(
            HashMap<String, String> datos) throws Exception {

        addStep("Ingresar a Control > Preinventarios Inteligentes > Conteos Inteligentes.");
        conteosMvp5().iniciarDesdeLoginHastaMenuPreinventarios(
                appActivityObjetivo(), datos.get("userLogin"),
                PasswordUtil.decryptPassword(datos.get("passwordLogin")));
        testCase.addScreenShotCurrentStep(appium(), "Antes de ingresar a Conteos Inteligentes");
        conteosMvp5().entrarAConteosInteligentes();
        Assert.assertTrue(conteosMvp5().listaSemanasVisible(false),
                "No se mostro la lista de semanas de Conteos Inteligentes");

        addStep("Seleccionar la semana en curso y dar clic en CONTINUAR.");
        comunes().seleccionarSemana(false);
        Assert.assertTrue(conteosMvp5().semanaEnCursoSeleccionadaYContinuarHabilitado(false),
                "La semana en curso no quedo seleccionada o CONTINUAR no se habilito");
        testCase.addScreenShotCurrentStep(appium(), "Semana seleccionada antes de CONTINUAR");
        comunes().clickBtnContinuar(false);

        addStep("Esperar la consolidacion y validar la barra inferior del resumen.");
        esperarConsolidacionResumenRobusta();
        comunes().clickBtnAceptarConteosFaltantes(false);
        Assert.assertFalse(conteosMvp5().consolidacionEnCurso(),
                "La consolidacion continua bloqueando el resumen");
        boolean guardarDerechaInicial = conteosMvp5().guardarEstaDerechaDeCancelar();
        boolean sinFinalizarInicial = conteosMvp5().sinControlesFinalizacion();
        testCase.addTextEvidenceCurrentStep(
                "MOMENTO|GUARDAR_DERECHA|FINALIZAR|RESULTADO\n"
                        + "RESUMEN_INICIAL|" + guardarDerechaInicial + "|"
                        + (sinFinalizarInicial ? "AUSENTE" : "PRESENTE") + "|"
                        + (guardarDerechaInicial && sinFinalizarInicial
                                ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(appium(), "Resumen inicial sin FINALIZAR");
        Assert.assertTrue(guardarDerechaInicial, "GUARDAR no esta a la derecha de CANCELAR");
        Assert.assertTrue(sinFinalizarInicial, "Se encontro FINALIZAR en el resumen inicial");

        addInternalStep("Seleccionar por BD dos selectivos asignados y acotados.");
        Configuraciones configuracion = Configuraciones.cargar(datos());
        int maximoSkuPreferente = (int) configuracion.valorLong(
                "preinv.mvp5.hu24.preflight.max.skus", 15L);
        List<Escenario> escenarios = datosHu24()
                .consultarAsignadosConRangoSinFiltro(2, 1, maximoSkuPreferente);
        if (escenarios.size() < 2) {
            addInternalStep("FT_009: no existen 2 selectivos en rango 1-"
                    + maximoSkuPreferente
                    + "; aplicar fallback sin limite superior");
            escenarios = datosHu24().consultarAsignadosConRangoSinFiltro(2, 1, 0);
        }
        Assert.assertEquals(
                escenarios.size(),
                2,
                "No existen dos selectivos distintos con pendientes positivos en la distribucion vigente");
        Assert.assertNotEquals(
                escenarios.get(0).folio(),
                escenarios.get(1).folio(),
                "La consulta devolvio dos veces el mismo folio");

        for (int indice = 0; indice < escenarios.size(); indice++) {
            Escenario escenario = escenarios.get(indice);
            addStep("Completar por UI el selectivo " + (indice + 1)
                    + " de 2 y validar ausencia de FINALIZAR CONTEO.");
            CachedRowSet pendientes = datosHu24()
                    .consultarPendientesPositivos(escenario.folio());
            Assert.assertTrue(pendientes.size() > 0,
                    "El selectivo " + escenario.folio() + " no conserva pendientes");
            conteosHu24().posicionarSelectivoSinExpandir(escenario.encabezado());
            testCase.addScreenShotCurrentStep(appium(),
                    "Selectivo " + (indice + 1) + " antes de expandir");
            conteosHu24().expandirSelectivo(escenario.encabezado(), false);
            Assert.assertTrue(conteosHu24().selectivoExpandidoCoincide(
                    escenario.encabezado(), false),
                    "Se expandio un selectivo diferente al elegido por BD");
            Assert.assertTrue(conteosHu24().sinControlesFinalizacion(false),
                    "Se mostro FINALIZAR CONTEO antes de completar el selectivo");
            conteosHu24().capturarConteosPendientes(pendientes, "2", false);
            conteosHu24().desplazarAlFinalSelectivo(false);
            boolean imprimirVisible = conteosHu24().imprimirVisible(false);
            boolean sinFinalizar = conteosHu24().sinControlesFinalizacion(false);
            testCase.addTextEvidenceCurrentStep(
                    "SELECTIVO|IMPRIMIR|FINALIZAR_CONTEO|RESULTADO\n"
                            + (indice + 1) + "|" + imprimirVisible + "|"
                            + (sinFinalizar ? "AUSENTE" : "PRESENTE") + "|"
                            + (imprimirVisible && sinFinalizar ? "CORRECTO" : "INCORRECTO"));
            testCase.addScreenShotCurrentStep(appium(),
                    "Selectivo " + (indice + 1) + " completo sin FINALIZAR CONTEO");
            Assert.assertTrue(imprimirVisible, "No se alcanzo el pie IMPRIMIR del selectivo");
            Assert.assertTrue(sinFinalizar,
                    "Se mostro FINALIZAR CONTEO al completar el selectivo");
        }

        addStep("Regresar al resumen y validar la barra inferior final.");
        conteosHu24().desplazarAGuardarSelectivo(false);
        boolean guardarDerechaFinal = conteosHu24().guardarEstaDerechaDeCancelar(false);
        boolean sinFinalizarFinal = conteosHu24().sinControlesFinalizacion(false);
        testCase.addTextEvidenceCurrentStep(
                "MOMENTO|GUARDAR_DERECHA|FINALIZAR|RESULTADO\n"
                        + "RESUMEN_FINAL|" + guardarDerechaFinal + "|"
                        + (sinFinalizarFinal ? "AUSENTE" : "PRESENTE") + "|"
                        + (guardarDerechaFinal && sinFinalizarFinal
                                ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(appium(), "Resumen final antes de GUARDAR");
        Assert.assertTrue(guardarDerechaFinal, "GUARDAR no conserva su posicion inferior derecha");
        Assert.assertTrue(sinFinalizarFinal, "Se encontro FINALIZAR antes del guardado global");

        addStep("Guardar ambos selectivos y confirmar el guardado.");
        conteosInteligentes().clickBtnGuardar(false);
        testCase.addScreenShotCurrentStep(appium(), "Confirmacion global antes de GUARDAR");
        comunes().clickBtnAceptar(false);
        boolean guardadoExitoso = conteosHu24().guardadoExitosoVisible(
                Duration.ofSeconds(60), false);
        testCase.addScreenShotCurrentStep(appium(), "Conteo guardado con exito");
        Assert.assertTrue(guardadoExitoso, "No se mostro Conteo guardado con exito");
        conteosInteligentes().esperaSeOculteMensajeGuardado();

        addStep("Validar en BD ambos selectivos completos y sin duplicidad funcional.");
        int[] inconsistencias = new int[escenarios.size()];
        StringBuilder evidenciaBd = new StringBuilder(
                "IO_FOLIO|TOTAL_SKU|INCONSISTENCIAS|RESULTADO\n");
        for (int indice = 0; indice < escenarios.size(); indice++) {
            Escenario escenario = escenarios.get(indice);
            inconsistencias[indice] = datosHu24()
                    .contarInconsistenciasFinales(escenario.folio());
            evidenciaBd.append(escenario.folio()).append("|")
                    .append(escenario.total()).append("|")
                    .append(inconsistencias[indice]).append("|")
                    .append(inconsistencias[indice] == 0
                            ? "COMPLETO" : "INCONSISTENTE")
                    .append("\n");
        }
        testCase.addTextEvidenceCurrentStep(evidenciaBd.toString().trim());
        for (int indice = 0; indice < escenarios.size(); indice++) {
            Escenario escenario = escenarios.get(indice);
            Assert.assertEquals(
                    inconsistencias[indice],
                    0,
                    "El selectivo " + escenario.folio()
                            + " conserva estados incompatibles con un conteo completo");
        }
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
                    "Setup/runtime: consolidacion no visible despues del timeout; continuar flujo integral");
            return;
        }

        throw new AssertionError(
                "La consolidacion continua activa despues del timeout en FT_009");
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() throws Exception {
        asegurarEscenarioConGuardBackMvp5(
                "Setup FT_009",
                () -> datosHu24().consultarAsignadoConRangoSinFiltro(1, 0),
                "distribucion vigente con selectivo asignado");
    }

    @Override
    protected void ejecutarTeardownEspecifico() throws Exception {
        // El caso solo persiste conteos permitidos mediante la aplicacion.
    }

    @Override
    public String setPrerequisites() {
        return "1. Android 13 en HH TC52 o Tablet ET51. "
                + "2. Usuario Asesor/Líder autenticado. "
                + "3. PREINVENINT_ENABLED=TRUE. "
                + "4. Semana vigente con clasificación y asignación previas. "
                + "5. Al menos dos selectivos disponibles con SKUs sin conteo. "
                + "6. Versión de XPOS desplegada con los tres cambios de HU24 aplicados.";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_009_FlujoIntegralSinFinalizar";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
