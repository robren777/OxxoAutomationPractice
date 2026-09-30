package Mobile.preinventariosinteligentes.mvp5.hu10;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt.EstadoInicialSku;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaEstadoInicialHu10.Escenario;
import Mobile.preinventariosinteligentes.mvp5.support.ConsultaEstadoInicialHu10.Persistencia;
import utils.password.PasswordUtil;

/**
 * HU10 / MTC_FT_002: estado inicial de un SKU nunca contado.
 *
 * @author Marcos Morales
 * @date 12/08/2026
 */
public class ATC_FT_002_EstadoInicialConGuionesSinCompleto
        extends BaseExecutionPreInt {

    @Override
    public String setTestDescription() {
        return "Verificar que un SKU incluido desde la creación del selectivo, "
                + "con existencia teórica mayor a cero y sin primer conteo, "
                + "muestra guiones en Teóricos y Físicos sin la etiqueta Completo.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU10", "Reutilizado", "Regresion"})
    public void ATC_FT_002_EstadoInicialConGuionesSinCompleto_test(
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
        testCase.addScreenShotCurrentStep(
                appium(), "Semana en curso seleccionada");
        Assert.assertTrue(
                conteosMvp5()
                        .semanaEnCursoSeleccionadaYContinuarHabilitado(false),
                "La semana En curso no quedó seleccionada o CONTINUAR no se habilitó");

        addStep("Dar clic en el botón CONTINUAR.");
        testCase.addScreenShotCurrentStep(
                appium(), "Semana seleccionada antes de CONTINUAR");
        comunes().clickBtnContinuar(false);

        addStep("Esperar la finalización de la consolidación automática.");
        conteosMvp5().esperarInicioConsolidacion();
        String mensajeConsolidacion =
                conteosMvp5().obtenerMensajeConsolidacionVisible();
        boolean mensajeCorrecto =
                PreinventariosInt.MENSAJE_CONSOLIDANDO_ESPERADO
                        .equals(mensajeConsolidacion)
                || PreinventariosInt.MENSAJE_CONSOLIDANDO
                        .equals(mensajeConsolidacion);
        boolean sinAcciones =
                conteosMvp5().sinAccionesManualesConsolidacion();
        testCase.addTextEvidenceCurrentStep(
                "MENSAJE_OBSERVADO|ACCIONES_MANUALES|RESULTADO\n"
                        + mensajeConsolidacion + "|"
                        + (sinAcciones ? "AUSENTES" : "PRESENTES") + "|"
                        + (mensajeCorrecto && sinAcciones
                                ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(
                appium(), "Mensaje de consolidacion observado");
        Assert.assertTrue(
                mensajeCorrecto && sinAcciones,
                "El loading inicial no coincide o expone acciones manuales");
        boolean notificacionExitosa = conteosMvp5()
                .esperarNotificacionConsolidacionExitosa(true);
        Assert.assertTrue(
                notificacionExitosa,
                "No se mostro el toast de consolidacion exitosa");
        aceptarConteosFaltantesSiAplica();
        Assert.assertTrue(
                conteosMvp5().detalleConteoHabilitado(false),
                "No se mostro el detalle del preinventario consolidado");

        addInternalStep(
                "Identificar por BD un SKU asignado con teórico positivo "
                        + "y sin captura física.");
        Escenario escenario = datosHu10().consultarSkuNuncaContado();
        Assert.assertNotNull(
                escenario,
                "No existe un SKU asignado nunca contado con teórico positivo");
        Assert.assertNotNull(
                escenario.teorico(),
                "El escenario elegido no contiene existencia teórica");
        Assert.assertTrue(
                escenario.teorico().signum() > 0,
                "El escenario elegido no conserva existencia teórica mayor a cero");
        Assert.assertNull(
                escenario.fisico(),
                "El SKU elegido ya contiene una captura física");

        addStep("Dar clic en el botón \"+\" del selectivo que contiene el SKU nunca contado.");
        conteosHu24().posicionarSelectivoSinExpandir(escenario.encabezado());
        testCase.addScreenShotCurrentStep(
                appium(), "Selectivo objetivo antes de expandir");
        conteosHu24().expandirSelectivo(escenario.encabezado(), false);
        Assert.assertTrue(
                conteosHu24().selectivoExpandidoCoincide(
                        escenario.encabezado(), false),
                "Se expandió un selectivo diferente al elegido por BD");

        addStep("Localizar el SKU nunca contado y observar las columnas Teóricos y Físicos.");
        EstadoInicialSku estadoUi = conteosMvp5()
                .observarEstadoInicialSku(escenario.sku(), false);
        boolean estadoInicialCorrecto = "-".equals(estadoUi.teorico())
                && "-".equals(estadoUi.fisico())
                && estadoUi.completoAusente();
        testCase.addTextEvidenceCurrentStep(
                "SKU|TEORICOS|FISICOS|COMPLETO|RESULTADO\n"
                        + escenario.sku() + "|"
                        + estadoUi.teorico() + "|"
                        + estadoUi.fisico() + "|"
                        + (estadoUi.completoAusente() ? "AUSENTE" : "PRESENTE") + "|"
                        + (estadoInicialCorrecto ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(
                appium(), "SKU nunca contado con estado inicial");
        Assert.assertEquals(
                estadoUi.teorico(),
                "-",
                "La columna Teóricos del SKU nunca contado no muestra guion");
        Assert.assertEquals(
                estadoUi.fisico(),
                "-",
                "La columna Físicos del SKU nunca contado no muestra guion");
        Assert.assertTrue(
                estadoUi.completoAusente(),
                "La etiqueta Completo aparece en la fila del SKU nunca contado");

        addStep("[BD] Validar en modo solo lectura el detalle del folio y el SKU.");
        Persistencia persistencia = datosHu10().consultarPersistencia(
                escenario.folio(), escenario.sku());
        boolean estadoCorrecto = persistencia.repeticiones() == 1
                && persistencia.teorico() != null
                && persistencia.teorico().signum() > 0
                && persistencia.fisico() == null;
        testCase.addTextEvidenceCurrentStep(
                "IO_FOLIO|SKU|IO_ONHAND|IO_ONHANDREAL|REPETICIONES|RESULTADO\n"
                        + escenario.folio() + "|"
                        + escenario.sku() + "|"
                        + persistencia.teorico() + "|"
                        + persistencia.fisico() + "|"
                        + persistencia.repeticiones() + "|"
                        + (estadoCorrecto
                                ? "NUNCA_CONTADO_SIN_MUTACION"
                                : "ESTADO_INCONSISTENTE"));
        Assert.assertEquals(
                persistencia.repeticiones(),
                1,
                "El folio/SKU no conserva exactamente un detalle");
        Assert.assertNotNull(
                persistencia.teorico(),
                "El teórico desapareció del detalle persistido");
        Assert.assertTrue(
                persistencia.teorico().signum() > 0,
                "El teórico persistido no es mayor a cero");
        Assert.assertNull(
                persistencia.fisico(),
                "Abrir el listado creó indebidamente una captura física");
    }

    private void aceptarConteosFaltantesSiAplica() {
        try {
            comunes().clickBtnAceptarConteosFaltantes(false);
            addInternalStep("Runtime: popup de conteos faltantes aceptado");
        } catch (RuntimeException popupNoVisible) {
            addInternalStep("Runtime: popup de conteos faltantes no visible; continuar flujo");
        }
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

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() {
        addInternalStep(
                "Setup: usar la distribución inteligente vigente sin modificar datos de negocio");
    }

    @Override
    protected void ejecutarTeardownEspecifico() {
        // El caso es de solo lectura y no requiere restaurar datos.
    }

    @Override
    public String setPrerequisites() {
        return "1. Android 13 en HH TC52 o Tablet ET51. "
                + "2. Usuario Asesor/Líder autenticado. "
                + "3. PREINVENINT_ENABLED=TRUE. "
                + "4. Semana vigente con clasificación y asignación previas. "
                + "5. Al menos un SKU de la distribución vigente con IO_OnHand > 0 "
                + "e IO_OnHandReal NULL, incluido desde la creación del selectivo.";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_002_EstadoInicialConGuionesSinCompleto";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
