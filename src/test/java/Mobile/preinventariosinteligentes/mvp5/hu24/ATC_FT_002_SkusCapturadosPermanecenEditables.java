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
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaSelectivoHu24.EscenarioEdicion;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaSelectivoHu24.PersistenciaSku;
import Mobile.preinventariosinteligentes.mvp5.support.PreflightHu24Support;
import utils.password.PasswordUtil;

/**
 * HU24 / MTC_FT_002 con preflight backend-first.
 */
public class ATC_FT_002_SkusCapturadosPermanecenEditables
        extends BaseExecutionPreInt {
    private static final String CANTIDAD_ADICIONAL = "2";

    private EscenarioEdicion escenarioPreflight;

    @Override
    public String setTestDescription() {
        return "Verificar que con la eliminación del botón " + (char) 34
                + "FINALIZAR CONTEO" + (char) 34
                + ", los SKUs de un selectivo con captura previa permanecen editables. "
                + "El usuario debe poder modificar o corregir un conteo ya guardado sin "
                + "recibir bloqueo, a diferencia del comportamiento anterior donde presionar "
                + (char) 34 + "FINALIZAR CONTEO" + (char) 34
                + " bloqueaba los SKUs permanentemente.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU24", "Reutilizado"})
    public void ATC_FT_002_SkusCapturadosPermanecenEditables_test(
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

        addInternalStep("Preflight backend resuelto para HU24-002 folio="
                + escenarioPreflight.folio()
                + " sku=" + escenarioPreflight.sku());
        Assert.assertNotNull(
                escenarioPreflight.fisicoInicial(),
                "El escenario elegido no contiene un valor fisico previo");

        addStep("Expandir el selectivo que contiene el SKU previamente capturado.");
        expandirSelectivoConRecuperacionPorColaborador();
        Assert.assertTrue(
                conteosHu24().selectivoExpandidoCoincide(
                        escenarioPreflight.encabezado(), false),
                "Se expandio un selectivo diferente al elegido por preflight");
        Assert.assertTrue(
                conteosHu24().sinControlesFinalizacion(false),
                "Se mostro un control de finalizacion durante la semana activa");
        testCase.addScreenShotCurrentStep(
                appium(), "Selectivo con SKU previamente capturado");

        addStep("Abrir mediante tap el SKU con captura previa y observar V_INICIAL.");
        conteosHu24().abrirSkuPorTap(escenarioPreflight.sku(), false);
        String skuModal = conteosHu24().obtenerSkuModal(false);
        BigDecimal fisicoInicialUi = conteosHu24().obtenerFisicoActualModal(false);
        boolean capturaEditable = conteosHu24().capturaFisicaEditable(false);
        testCase.addTextEvidenceCurrentStep(
                "SKU|FISICO_BD|FISICO_UI|EDITABLE|RESULTADO\n"
                        + escenarioPreflight.sku() + "|"
                        + escenarioPreflight.fisicoInicial() + "|"
                        + fisicoInicialUi + "|" + capturaEditable + "|"
                        + (escenarioPreflight.sku().equals(skuModal)
                                && fisicoInicialUi.compareTo(escenarioPreflight.fisicoInicial()) == 0
                                && capturaEditable ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(
                appium(), "SKU con captura previa permanece editable");
        Assert.assertEquals(skuModal, escenarioPreflight.sku(), "El modal abierto corresponde a otro SKU");
        Assert.assertEquals(
                fisicoInicialUi.compareTo(escenarioPreflight.fisicoInicial()), 0,
                "El fisico precargado no coincide con el valor previo de BD");
        Assert.assertTrue(capturaEditable, "El SKU con captura previa no permite ingresar una nueva cantidad");

        addStep("Capturar 2 unidades adicionales y guardar dentro del modal.");
        BigDecimal fisicoEsperado = escenarioPreflight.fisicoInicial()
                .add(new BigDecimal(CANTIDAD_ADICIONAL));
        conteosHu24().capturarCantidadAdicional(CANTIDAD_ADICIONAL, false);
        conteosInteligentes().clickBtnGuardarCto(false);
        boolean articuloContabilizado =
                conteosHu24().articuloContabilizadoVisible(false);
        testCase.addScreenShotCurrentStep(appium(), "Articulo contabilizado");
        Assert.assertTrue(articuloContabilizado, "No se mostro la confirmacion Articulo contabilizado");
        BigDecimal fisicoTarjeta = conteosHu24()
                .obtenerFisicoTarjeta(escenarioPreflight.sku(), false);
        boolean sinFinalizar = conteosHu24().sinControlesFinalizacion(false);
        testCase.addScreenShotCurrentStep(
                appium(), "Tarjeta actualizada sin FINALIZAR CONTEO");
        Assert.assertEquals(fisicoTarjeta.compareTo(fisicoEsperado), 0,
                "La tarjeta no refleja V_INICIAL + V_CAPTURA");
        Assert.assertTrue(sinFinalizar, "La recaptura habilito un control de finalizacion");

        addStep("Guardar globalmente el conteo y confirmar la operacion.");
        conteosHu24().desplazarAGuardarSelectivo(false);
        Assert.assertTrue(conteosHu24().guardarEstaDerechaDeCancelar(false),
                "GUARDAR no esta ubicado a la derecha de CANCELAR");
        testCase.addScreenShotCurrentStep(appium(), "GUARDAR disponible antes de confirmar");
        conteosInteligentes().clickBtnGuardar(false);
        testCase.addScreenShotCurrentStep(appium(), "Confirmacion global antes de GUARDAR");
        comunes().clickBtnAceptar(false);
        boolean guardadoExitoso = conteosHu24().guardadoExitosoVisible(
                Duration.ofSeconds(60), false);
        testCase.addScreenShotCurrentStep(appium(), "Conteo guardado con exito");
        Assert.assertTrue(guardadoExitoso, "No se mostro la confirmacion de guardado exitoso");
        conteosInteligentes().esperaSeOculteMensajeGuardado();

        addStep("Validar en BD el fisico final y la ausencia de detalle duplicado.");
        PersistenciaSku persistencia = datosHu24().consultarPersistenciaSku(
                escenarioPreflight.folio(), escenarioPreflight.sku());
        boolean persistenciaCorrecta = persistencia.repeticiones() == 1
                && persistencia.fisico() != null
                && persistencia.fisico().compareTo(fisicoEsperado) == 0;
        testCase.addTextEvidenceCurrentStep(
                "IO_FOLIO|SKU|FISICO_ESPERADO|FISICO_PERSISTIDO|REPETICIONES|RESULTADO\n"
                        + escenarioPreflight.folio() + "|"
                        + escenarioPreflight.sku() + "|"
                        + fisicoEsperado + "|"
                        + persistencia.fisico() + "|"
                        + persistencia.repeticiones() + "|"
                        + (persistenciaCorrecta ? "EDITABLE_Y_UNICO" : "INCONSISTENTE"));
        Assert.assertEquals(
                persistencia.repeticiones(),
                1,
                "El folio/SKU no conserva exactamente un detalle");
        Assert.assertNotNull(
                persistencia.fisico(),
                "El fisico final no fue persistido");
        Assert.assertEquals(
                persistencia.fisico().compareTo(fisicoEsperado),
                0,
                "El fisico persistido no coincide con V_INICIAL + V_CAPTURA");
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() throws Exception {
        addInternalStep("Setup: preflight backend-first para HU24-002");
        PreflightHu24Support preflight = new PreflightHu24Support();
        Configuraciones configuracion = Configuraciones.cargar(datos());
        int maximoSkuPreferente = (int) configuracion.valorLong(
                "preinv.mvp5.hu24.preflight.max.skus", 15L);
        String letraReferencia = configuracion.opcional(
                "preinv.mvp5.hu24.preflight.letra.referencia");
        if (letraReferencia == null || letraReferencia.trim().isEmpty()) {
            letraReferencia = "A";
        }
        try {
            escenarioPreflight = preflight
                    .validarEscenarioSkuCapturado(datosHu24());
        } catch (IllegalStateException sinEscenario) {
            addInternalStep(
                    "Setup: sin SKU capturado despues de limpieza; preparar distribución y registrar una captura inicial");
            asegurarEscenarioConGuardBackMvp5(
                    "Setup FT_002",
                    () -> datosHu24().consultarAsignadoConRangoSinFiltro(1, maximoSkuPreferente),
                    "distribucion vigente con selectivo asignado");
            generarSkuCapturadoInicial(maximoSkuPreferente, letraReferencia);
            escenarioPreflight = asegurarEscenarioConGuardBackMvp5(
                    "Setup FT_002",
                    () -> preflight.validarEscenarioSkuCapturado(datosHu24()),
                    "escenario HU24-002 con SKU capturado");
        }
        addInternalStep("Setup: "
                + PreflightHu24Support.evidenciaEscenario(
                        "HU24-002",
                        escenarioPreflight.folio(),
                        0,
                        escenarioPreflight.sku()));
    }

    private void generarSkuCapturadoInicial(
            int maximoSkuPreferente,
            String letraReferencia) throws Exception {
        conteosMvp5().iniciarDesdeLoginYEntrarAConteosInteligentes(
                appActivityObjetivo(),
                datos().get("userLogin"),
                PasswordUtil.decryptPassword(datos().get("passwordLogin")));
        comunes().seleccionarSemana(false);
        comunes().clickBtnContinuar(false);
        conteosMvp5().esperarConsolidacionCompleta(false);
        comunes().clickBtnAceptarConteosFaltantes(false);

        Escenario escenario = datosHu24().consultarAsignadoCercanoALetraSinFiltro(
                letraReferencia,
                1,
                maximoSkuPreferente);
        if (escenario == null) {
            escenario = datosHu24().consultarAsignadoConRangoSinFiltro(
                    1,
                    maximoSkuPreferente);
        }
        if (escenario == null) {
            throw new IllegalStateException(
                    "No fue posible identificar un selectivo pendiente de 1 a "
                            + maximoSkuPreferente
                            + " SKU para generar captura inicial HU24-002");
        }

        CachedRowSet pendientes = datosHu24()
                .consultarPendientesPositivos(escenario.folio());
        if (!pendientes.next()) {
            throw new IllegalStateException(
                    "El selectivo elegido no tiene SKU pendientes para generar captura inicial HU24-002");
        }
        String sku = pendientes.getString("SKU");

        conteosHu24().expandirSelectivo(escenario.encabezado(), false);
        conteosHu24().abrirSkuPorTap(sku, false);
        conteosHu24().capturarCantidadInicial("1", false);
        Assert.assertTrue(
                conteosHu24().articuloContabilizadoVisible(false),
                "No se confirmo la captura inicial para habilitar HU24-002");

        conteosHu24().desplazarAGuardarSelectivo(false);
        conteosInteligentes().clickBtnGuardar(false);
        comunes().clickBtnAceptar(false);
        Assert.assertTrue(
                conteosHu24().guardadoExitosoVisible(
                        Duration.ofSeconds(60), false),
                "No se confirmo el guardado inicial para habilitar HU24-002");
        conteosInteligentes().esperaSeOculteMensajeGuardado();
    }

    private void expandirSelectivoConRecuperacionPorColaborador() {
        try {
            conteosHu24().expandirSelectivo(escenarioPreflight.encabezado(), false);
            return;
        } catch (NoSuchElementException selectivoNoVisible) {
            String colaboradorAsignado = conteosHu24()
                    .obtenerColaboradorAsignado(false);
            EscenarioEdicion escenarioColaborador = datosHu24()
                    .consultarSkuCapturado(colaboradorAsignado);
            if (escenarioColaborador == null) {
                throw selectivoNoVisible;
            }
            addInternalStep(
                    "Preflight backend ajustado por colaborador UI: "
                            + colaboradorAsignado
                            + " folio=" + escenarioColaborador.folio()
                            + " sku=" + escenarioColaborador.sku());
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
                + "5. Al menos un selectivo con uno o más SKUs con captura previa guardada "
                + "(Físicos con valores numéricos persistidos).";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_002_SkusCapturadosPermanecenEditables";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
