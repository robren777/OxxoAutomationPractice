package Mobile.preinventariosinteligentes.mvp5.hu24;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;

import javax.sql.rowset.CachedRowSet;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.support.Configuraciones;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaSelectivoHu24.Escenario;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaSelectivoHu24.PersistenciaSku;
import utils.password.PasswordUtil;

/**
 * HU24 / MTC_FT_008: funcionalidad de GUARDAR despues de su reubicacion.
 */
public class ATC_FT_008_FuncionalidadBotonGuardarReubicado
        extends BaseExecutionPreInt {

    private Escenario escenarioPreflight;

    @Override
    public String setTestDescription() {
        return "Verificar que el botón GUARDAR, reubicado a la posición inferior derecha "
                + "del resumen, ejecuta correctamente el guardado de conteos sin ningún "
                + "cambio en su comportamiento funcional. El modal de confirmación, la barra "
                + "de progreso y el mensaje de éxito deben comportarse igual que antes del "
                + "cambio de posición.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU24", "Reutilizado"})
    public void ATC_FT_008_FuncionalidadBotonGuardarReubicado_test(
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

        addInternalStep("Preflight backend resuelto para HU24-008 folio="
                + escenarioPreflight.folio()
                + " total=" + escenarioPreflight.total());

        addStep("Expandir el selectivo y capturar Fisico 2 mediante tap.");
        expandirSelectivoConRecuperacionPorColaborador();
        CachedRowSet pendientes = datosHu24()
                .consultarPendientesPositivos(escenarioPreflight.folio());
        Assert.assertTrue(pendientes.next(), "El selectivo no contiene un SKU pendiente");
        String sku = pendientes.getString("SKU");
        Assert.assertTrue(conteosHu24().fisicoPendienteVisible(sku, false),
                "El SKU elegido por BD no aparece pendiente en la UI");
        conteosHu24().abrirSkuPorTap(sku, false);
        Assert.assertEquals(conteosHu24().obtenerSkuModal(false), sku,
                "El modal abrio un SKU diferente al elegido por BD");
        testCase.addScreenShotCurrentStep(appium(), "SKU pendiente antes de capturar Fisico 2");
        conteosHu24().capturarCantidadInicial("2", false);
        boolean articuloContabilizado = conteosHu24().articuloContabilizadoVisible(false);
        testCase.addScreenShotCurrentStep(appium(), "Articulo contabilizado");
        Assert.assertTrue(articuloContabilizado, "No se mostro la confirmacion Articulo contabilizado");
        BigDecimal fisicoTarjeta = conteosHu24().obtenerFisicoTarjeta(sku, false);
        testCase.addScreenShotCurrentStep(appium(), "Tarjeta con Fisico 2 antes del guardado global");
        Assert.assertEquals(fisicoTarjeta.compareTo(new BigDecimal("2")), 0,
                "La tarjeta no muestra Fisico 2 antes del guardado global");

        addStep("Validar la posicion de GUARDAR y abrir la confirmacion global.");
        conteosHu24().desplazarAGuardarSelectivo(false);
        boolean guardarDerecha = conteosHu24().guardarEstaDerechaDeCancelar(false);
        boolean sinFinalizar = conteosHu24().sinControlesFinalizacion(false);
        testCase.addTextEvidenceCurrentStep(
                "GUARDAR_DERECHA|FINALIZAR|RESULTADO\n"
                        + guardarDerecha + "|"
                        + (sinFinalizar ? "AUSENTE" : "PRESENTE") + "|"
                        + (guardarDerecha && sinFinalizar ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(appium(), "GUARDAR reubicado antes del clic");
        Assert.assertTrue(guardarDerecha, "GUARDAR no esta ubicado a la derecha de CANCELAR");
        Assert.assertTrue(sinFinalizar, "Se encontro un control FINALIZAR junto a GUARDAR");
        conteosInteligentes().clickBtnGuardar(false);
        testCase.addScreenShotCurrentStep(appium(), "Confirmacion global antes de GUARDAR");

        addStep("Seleccionar GUARDAR en el modal y validar el mensaje de exito.");
        comunes().clickBtnAceptar(false);
        boolean guardadoExitoso = conteosHu24().guardadoExitosoVisible(
                Duration.ofSeconds(60), false);
        testCase.addScreenShotCurrentStep(appium(), "Conteo guardado con exito");
        Assert.assertTrue(guardadoExitoso, "No se mostro Conteo guardado con exito");
        conteosInteligentes().esperaSeOculteMensajeGuardado();

        addStep("Validar en BD la persistencia unica del Fisico 2.");
        PersistenciaSku persistencia = datosHu24()
                .consultarPersistenciaSku(escenarioPreflight.folio(), sku);
        boolean persistenciaCorrecta = persistencia.repeticiones() == 1
                && persistencia.fisico() != null
                && persistencia.fisico().compareTo(new BigDecimal("2")) == 0;
        testCase.addTextEvidenceCurrentStep(
                "IO_FOLIO|SKU|FISICO_ESPERADO|FISICO_PERSISTIDO|REPETICIONES|RESULTADO\n"
                        + escenarioPreflight.folio() + "|"
                        + sku + "|2|"
                        + persistencia.fisico() + "|"
                        + persistencia.repeticiones() + "|"
                        + (persistenciaCorrecta ? "PERSISTIDO" : "INCONSISTENTE"));
        Assert.assertEquals(
                persistencia.repeticiones(),
                1,
                "El guardado genero duplicidad para el folio/SKU");
        Assert.assertNotNull(
                persistencia.fisico(),
                "El fisico guardado permanece nulo en BD");
        Assert.assertEquals(
                persistencia.fisico().compareTo(new BigDecimal("2")),
                0,
                "BD no refleja el Fisico 2 capturado desde la UI");
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() throws Exception {
        addInternalStep("Setup: preflight backend-first para HU24-008");
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
                "Setup FT_008",
                () -> consultarEscenario(maximoSkuPreferente, letraReferencia),
                "escenario HU24-008 con pendientes positivos");
    }

    private Escenario consultarEscenario(int maximoSkuPreferente, String letraReferencia) {
        Escenario escenario = datosHu24().consultarAsignadoCercanoALetraSinFiltro(
                letraReferencia,
                1,
                maximoSkuPreferente);
        if (escenario != null) {
            return escenario;
        }
        return datosHu24().consultarAsignadoConRangoSinFiltro(1, maximoSkuPreferente);
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
                    "Setup/runtime: consolidacion no visible despues del timeout; continuar validacion del resumen");
            return;
        }

        throw new AssertionError(
                "La consolidacion continua activa despues del timeout en FT_008");
    }

    private void expandirSelectivoConRecuperacionPorColaborador() {
        try {
            conteosHu24().expandirSelectivo(escenarioPreflight.encabezado(), false);
            return;
        } catch (NoSuchElementException selectivoNoVisible) {
            String colaboradorAsignado = conteosHu24().obtenerColaboradorAsignado(false);
            Escenario escenarioColaborador = datosHu24().consultarAsignadoMaximo15(
                    colaboradorAsignado);
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
                + "4. Semana vigente activa con al menos un SKU sin conteo capturado. "
                + "5. Versión de XPOS desplegada con GUARDAR en posición inferior derecha.";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_008_FuncionalidadBotonGuardarReubicado";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
