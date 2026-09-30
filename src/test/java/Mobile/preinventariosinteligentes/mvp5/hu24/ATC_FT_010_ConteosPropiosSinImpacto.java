package Mobile.preinventariosinteligentes.mvp5.hu24;

import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.support.Configuraciones;
import utils.password.PasswordUtil;

/**
 * HU24 / MTC_FT_010: Conteos Propios sin impacto por HU24.
 */
public class ATC_FT_010_ConteosPropiosSinImpacto
        extends BaseExecutionPreInt {

    @Override
    public String setTestDescription() {
        return "Verificar que el alcance de HU24 es exclusivo del módulo Conteos "
                + "Inteligentes (INTELIGENT_COUNT) y que el módulo Conteos Propios "
                + "(OWN_COUNT) conserva su comportamiento previo sin cambios no deseados "
                + "en su interfaz o flujo.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU24", "Reutilizado"})
    public void ATC_FT_010_ConteosPropiosSinImpacto_test(
            HashMap<String, String> datos) throws Exception {

        addStep("Ingresar a Control > Preinventarios Inteligentes > Conteos Propios.");
        conteosMvp5().iniciarDesdeLoginHastaMenuPreinventarios(
                appActivityObjetivo(), datos.get("userLogin"),
                PasswordUtil.decryptPassword(datos.get("passwordLogin")));
        testCase.addScreenShotCurrentStep(appium(), "Antes de ingresar a Conteos Propios");
        conteosMvp5().entrarAConteosPropios();
        Assert.assertTrue(conteosMvp5().listaSemanasVisible(false),
                "No se mostro la lista de semanas de Conteos Propios");

        addStep("Seleccionar la semana en curso y dar clic en CONTINUAR.");
        comunes().seleccionarSemana(false);
        Assert.assertTrue(conteosMvp5().semanaEnCursoSeleccionadaYContinuarHabilitado(false),
                "La semana en curso no quedo seleccionada o CONTINUAR no se habilito");
        testCase.addScreenShotCurrentStep(appium(), "Semana seleccionada antes de CONTINUAR");
        comunes().clickBtnContinuar(false);
        boolean[] opciones = conteosPropios().retornaEstadoBotonesNuevaPlantillaEIniciarConteo();
        testCase.addTextEvidenceCurrentStep(
                "CREAR_NUEVA_PLANTILLA|INICIAR_CONTEO|RESULTADO\n"
                        + opciones[0] + "|" + opciones[1] + "|"
                        + (opciones[0] && opciones[1] ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(appium(), "Menu de Conteos Propios disponible");
        Assert.assertTrue(opciones[0] && opciones[1],
                "No se mostraron Crear nueva plantilla e Iniciar conteo");

        addStep("Dar clic en Iniciar Conteo y seleccionar la plantilla objetivo.");
        menus().clickBtnIniciarConteo(false);
        Assert.assertFalse(conteosPropios().listaPlantillas().isEmpty(),
                "No existe una plantilla propia disponible para iniciar el conteo");
        testCase.addScreenShotCurrentStep(appium(), "Plantilla disponible antes de abrirla");
        conteosMvp5().seleccionarPrimeraPlantillaPropia();
        esperarConsolidacionPropiosRobusta();
        boolean[] detalle = conteosPropios().retornaEstadoElementosDetallePlantilla();
        testCase.addTextEvidenceCurrentStep(
                "CODIGO_BARRAS|TITULO_SEMANA|RESULTADO\n"
                        + detalle[0] + "|" + detalle[1] + "|"
                        + (detalle[0] && detalle[1] ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(appium(), "Detalle de plantilla propia disponible");
        Assert.assertTrue(detalle[0] && detalle[1],
                "El detalle propio no conserva codigo de barras y titulo de semana");

        addStep("Expandir la plantilla, capturar Fisico 2 y guardar dentro del modal.");
        conteosPropios().clickNombrePlantilla();
        Assert.assertFalse(conteosPropios().listaProductos().isEmpty(),
                "La plantilla propia no contiene productos visibles");
        testCase.addScreenShotCurrentStep(appium(), "Plantilla expandida antes de abrir un SKU");
        conteosPropios().clickFirstSKU();
        boolean[] modal = conteosPropios().retornaEstadoElementosCapturaConteo();
        boolean modalCompleto = true;
        for (boolean elementoVisible : modal) {
            modalCompleto = modalCompleto && elementoVisible;
        }
        testCase.addTextEvidenceCurrentStep(
                "MODAL_COMPLETO|RESULTADO\n" + modalCompleto + "|"
                        + (modalCompleto ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(appium(), "Modal de captura propio disponible");
        Assert.assertTrue(modalCompleto, "El modal de Conteos Propios perdio elementos funcionales");
        conteosPropios().capturarConteo("2");
        conteosPropios().clickBtnGuardarCto(false);
        boolean modalCerrado = conteosMvp5().modalCapturaCerrado(false);
        boolean productosDisponibles = !conteosPropios().listaProductos().isEmpty();
        testCase.addScreenShotCurrentStep(appium(), "Producto disponible despues de guardar Fisico 2");
        Assert.assertTrue(modalCerrado, "El modal propio permanecio abierto despues de guardar el articulo");
        Assert.assertTrue(productosDisponibles, "El producto dejo de estar disponible despues de la captura");

        addStep("Validar que el resumen propio conserva IMPRIMIR y GUARDAR.");
        boolean[] controles = conteosPropios().retornaEstadoBtnsImprimirGuardar();
        testCase.addTextEvidenceCurrentStep(
                "IMPRIMIR|GUARDAR|RESULTADO\n"
                        + controles[0] + "|" + controles[1] + "|"
                        + (controles[0] && controles[1] ? "CORRECTO" : "INCORRECTO"));
        testCase.addScreenShotCurrentStep(appium(), "Resumen propio con IMPRIMIR y GUARDAR");
        Assert.assertTrue(controles[0] && controles[1],
                "Conteos Propios no conserva sus controles IMPRIMIR y GUARDAR");
    }

    private void esperarConsolidacionPropiosRobusta() {
        try {
            conteosMvp5().esperarConsolidacionCompleta(false);
            return;
        } catch (AssertionError timeoutConsolidacion) {
            addInternalStep(
                    "Setup/runtime: timeout de consolidacion en Conteos Propios; validar si la pantalla ya esta liberada");
        }

        if (!conteosMvp5().consolidacionEnCurso()) {
            addInternalStep(
                    "Setup/runtime: consolidacion no visible despues del timeout; continuar flujo de Conteos Propios");
            return;
        }

        throw new AssertionError(
                "La consolidacion continua activa despues del timeout en FT_010");
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() throws Exception {
        addInternalStep("Setup: validar por backend si existe plantilla propia activa para la semana vigente");

        int totalPlantillas = datosConteosPropiosMvp5()
                .cantidadPlantillasPropiasActivasSemanaActual();
        addInternalStep("Setup: plantillas propias activas (backend)=" + totalPlantillas);
        if (totalPlantillas > 0) {
            addInternalStep("Setup: backend confirma plantillas existentes; no se requiere creacion");
            return;
        }

        Configuraciones configuracion = Configuraciones.cargar(datos());
        int cantidadSkus = leerEnteroPositivo(
                configuracion.opcional(
                        "preinv.mvp5.own.template.sku.count",
                        "ownTemplateSkuCount"),
                3,
                "preinv.mvp5.own.template.sku.count");
        int offsetSkus = leerEnteroNoNegativo(
                configuracion.opcional(
                        "preinv.mvp5.own.template.sku.offset",
                        "ownTemplateSkuOffset"),
                0,
                "preinv.mvp5.own.template.sku.offset");

        addInternalStep(
                "Setup: no hay plantillas propias; se crea una plantilla MVP5 por UI con SKUs obtenidos por consulta read-only");
        List<String> skus = datosConteosPropiosMvp5()
                .obtenerSkusActivos(cantidadSkus, offsetSkus);
        addInternalStep("Setup: SKUs read-only seleccionados=" + skus.size());
        String nombreBasePlantilla = "MVP5_ATC_FT_010_AUTO_" + System.currentTimeMillis();

        String usuario = datos().get("userLogin");
        String contrasena = PasswordUtil.decryptPassword(datos().get("passwordLogin"));
        conteosMvp5().iniciarDesdeLoginYEntrarAConteosPropios(
                appActivityObjetivo(),
                usuario,
                contrasena);
        comunes().seleccionarSemana(false);
        comunes().clickBtnContinuar(false);
        plantillasPropiosMvp5().crearPlantillaConSkus(nombreBasePlantilla, skus);

        int totalDespuesCreacion = datosConteosPropiosMvp5()
                .cantidadPlantillasPropiasActivasSemanaActual();
        addInternalStep("Setup: plantillas propias activas despues de crear=" + totalDespuesCreacion);
        if (totalDespuesCreacion <= 0) {
            throw new IllegalStateException(
                    "Setup no pudo generar una plantilla propia activa para ATC_FT_010");
        }
        addInternalStep("Setup: plantilla propia creada por UI y validada por backend");
    }

    private static int leerEnteroPositivo(
            String valor,
            int predeterminado,
            String clave) {
        if (valor == null || valor.trim().isEmpty()) {
            return predeterminado;
        }
        try {
            int numero = Integer.parseInt(valor.trim());
            if (numero <= 0) {
                throw new IllegalArgumentException(
                        "La configuracion " + clave + " debe ser mayor a cero");
            }
            return numero;
        } catch (NumberFormatException excepcion) {
            throw new IllegalArgumentException(
                    "La configuracion " + clave + " no es un entero valido",
                    excepcion);
        }
    }

    private static int leerEnteroNoNegativo(
            String valor,
            int predeterminado,
            String clave) {
        if (valor == null || valor.trim().isEmpty()) {
            return predeterminado;
        }
        try {
            int numero = Integer.parseInt(valor.trim());
            if (numero < 0) {
                throw new IllegalArgumentException(
                        "La configuracion " + clave + " no puede ser negativa");
            }
            return numero;
        } catch (NumberFormatException excepcion) {
            throw new IllegalArgumentException(
                    "La configuracion " + clave + " no es un entero valido",
                    excepcion);
        }
    }

    @Override
    protected void ejecutarTeardownEspecifico() throws Exception {
        // El caso no realiza guardado global del preinventario propio.
    }

    @Override
    public String setPrerequisites() {
        return "1. Android 13 en HH TC52 o Tablet ET51. "
                + "2. Usuario Asesor/Líder autenticado. "
                + "3. PREINVENINT_ENABLED=TRUE. "
                + "4. Semana vigente activa con plantilla de Conteos Propios disponible "
                + "con al menos un SKU. "
                + "5. Versión de XPOS desplegada con los cambios de HU24.";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_010_ConteosPropiosSinImpacto";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
