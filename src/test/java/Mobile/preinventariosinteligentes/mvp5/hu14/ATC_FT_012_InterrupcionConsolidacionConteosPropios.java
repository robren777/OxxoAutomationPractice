package Mobile.preinventariosinteligentes.mvp5.hu14;

import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.preinventariosinteligentes.mvp5.om.BaseExecutionPreInt;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt.ResultadoInicioConsolidacion;
import Mobile.preinventariosinteligentes.mvp5.om.PreinventariosInt.ResultadoInterrupcionApp;
import utils.password.PasswordUtil;

/**
 * HU14 / MTC_FT_012: interrupción manual durante la consolidación en
 * Conteos Propios.
 *
 * @author Marcos Morales
 * @date 13/08/2026
 */
public class ATC_FT_012_InterrupcionConsolidacionConteosPropios
        extends BaseExecutionPreInt {

    @Override
    public String setTestDescription() {
        return "Validar que durante la interrupción manual del proceso de consolidación, "
                + "la aplicación mantiene la integridad de los datos/flujo en el módulo "
                + "de Conteos Propios.";
    }

    @Test(dataProvider = "data-provider", enabled = true,
            groups = {"MVP5", "HU14", "Reutilizado", "Regresion"})
    public void ATC_FT_012_InterrupcionConsolidacionConteosPropios_test(
            HashMap<String, String> datos) throws Exception {

        String usuario = datos.get("userLogin");
        String contrasena = PasswordUtil.decryptPassword(
                datos.get("passwordLogin"));
        redactarDatosSensibles(datos);

        addStep("Ingresar a MENÚ PRINCIPAL → Control → Preinventarios Inteligentes → Conteos Propios.");
        conteosMvp5().iniciarDesdeLoginHastaMenuPreinventarios(
                appActivityObjetivo(), usuario, contrasena);
        testCase.addScreenShotCurrentStep(
                appium(), "Antes de ingresar a Conteos Propios");
        conteosMvp5().entrarAConteosPropios();
        Assert.assertTrue(
                conteosMvp5().listaSemanasVisible(false),
                "No se mostró el listado de semanas de Conteos Propios");

        addStep("Seleccionar la semana en curso y pulsar CONTINUAR.");
        comunes().seleccionarSemana(false);
        testCase.addScreenShotCurrentStep(
                appium(), "Semana seleccionada antes de CONTINUAR");
        comunes().clickBtnContinuar(false);
        Assert.assertTrue(
                conteosMvp5().menuConteosPropiosDisponible(false),
                "No se mostró el menú para crear o iniciar Conteos Propios");

        addStep("Pulsar Iniciar conteo y seleccionar la plantilla objetivo.");
        menus().clickBtnIniciarConteo(false);
        testCase.addScreenShotCurrentStep(
                appium(), "Plantilla seleccionable antes de abrirla");
        conteosMvp5().seleccionarPrimeraPlantillaPropia();
        ResultadoInicioConsolidacion inicio =
                conteosMvp5().observarInicioConsolidacionNoBloqueante();
        boolean inicioAutomatico = inicio.inicioAutomaticoObservable();
        testCase.addTextEvidenceCurrentStep(
                inicio.evidencia());
        testCase.addScreenShotCurrentStep(
                appium(), "Observacion inmediata post seleccion de plantilla");
        Assert.assertTrue(
                inicioAutomatico,
                "No se observó inicio automático de consolidación por loading 1, loading 2 ni toast");

        // Home, Regresar y Cerrar son alternativas equivalentes. Se ejerce
        // Home como representante, igual que el criterio de los casos 3 y 4.
        addStep("Presionar el boton Home/Regresar/Cerrar la aplicación mientras se muestra el mensaje \"Se está realizando la consolidación.\"");
        ResultadoInterrupcionApp interrupcion = conteosMvp5()
                .interrumpirConHomeDuranteConsolidacion(true);
        testCase.addTextEvidenceCurrentStep(interrupcion.evidencia());
        Assert.assertTrue(
                interrupcion.interrupcionObservable(),
                "No se confirmó la interrupción con Home durante la consolidación");

        addStep("Reingresar al mismo flujo de conteos Propios, Seleccionar Semana En Curso + CONTINUAR, Iniciar conteo, Seleccionar plantilla");
        conteosMvp5().iniciarDesdeLoginYEntrarAConteosPropios(
                appActivityObjetivo(), usuario, contrasena);
        comunes().seleccionarSemana(false);
        comunes().clickBtnContinuar(false);
        menus().clickBtnIniciarConteo(false);
        conteosMvp5().seleccionarPrimeraPlantillaPropia();
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
                "El detalle propio no quedó disponible después de la interrupción");
        conteosPropios().clickNombrePlantilla();
        conteosPropios().clickFirstSKU();
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
                    "Runtime: timeout de consolidacion en Conteos Propios; validar si la pantalla ya esta liberada");
        }

        if (!conteosMvp5().consolidacionEnCurso()) {
            addInternalStep(
                    "Runtime: consolidacion no visible despues del timeout; continuar reingreso");
            return;
        }

        throw new AssertionError(
                "La consolidacion continua activa despues del timeout en FT_012");
    }

    @Override
    protected String nombreProyecto() {
        return "PreinventariosInteligentes MVP5";
    }

    @Override
    protected void ejecutarSetupEspecifico() {
        addInternalStep("Setup: validar por backend la disponibilidad de plantilla propia activa");
        int totalPlantillas = datosConteosPropiosMvp5()
                .cantidadPlantillasPropiasActivasSemanaActual();
        addInternalStep("Setup: plantillas propias activas (backend)=" + totalPlantillas);
        if (totalPlantillas > 0) {
            addInternalStep("Setup: backend confirma plantilla existente; no se requiere creacion");
            return;
        }

        addInternalStep(
                "Setup: no hay plantillas activas; crear plantilla MVP5 por UI con SKUs de consulta read-only");
        List<String> skus = datosConteosPropiosMvp5().obtenerSkusActivos(3, 0);
        if (skus.isEmpty()) {
            throw new IllegalStateException(
                    "Setup no pudo obtener SKUs activos para crear plantilla propia en FT_012");
        }

        String usuario = datos().get("userLogin");
        String contrasena = PasswordUtil.decryptPassword(datos().get("passwordLogin"));
        String nombreBasePlantilla = "MVP5_HU14_FT012_AUTO_" + System.currentTimeMillis();
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
                    "Setup no pudo generar plantilla propia activa para FT_012");
        }
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
                + "4. Semana vigente. "
                + "5. Plantilla propia creada con al menos un SKU. "
                + "6. Servicio o componente de consolidación QA configurado para responder con éxito. "
                + "7. Trazas del XPOS Service API disponibles.";
    }

    @Override
    public String setTestDesigner() {
        return "Marcos Morales";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_012_InterrupcionConsolidacionConteosPropios";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
