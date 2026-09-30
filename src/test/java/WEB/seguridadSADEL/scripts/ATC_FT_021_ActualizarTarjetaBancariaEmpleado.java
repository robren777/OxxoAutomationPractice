package WEB.seguridadSADEL.scripts;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import WEB.seguridadSADEL.om.EmpleadoPage;
import WEB.seguridadSADEL.om.PortalAplicacionesLoginPage;
import WEB.seguridadSADEL.om.PortalAplicacionesMainPage;
import WEB.updateNomina.om.Empleados;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.password.PasswordUtil;
import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Se valida que sea posible editar información del empleado
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -MTC_FT_021-Validar actualización de tarjeta bancaria por nueva cuenta de empleado
 *
 * PREREQUISITOS: "Acceso al portal de aplicaciones
 * Usuario con permisos para editar empleado
 * Empleado con método de pago en efectivo registrado en SADEL
 * Número de tarjeta bancaria nueva disponible para la prueba"
 *
 * INSUMOS:
 * * DEUDA TÉCNICA:
 * - En caso de reutilizar o refactorizar este script en el futuro, se deberá corregir tanto el 
 * script de prueba como los OMs involucrados para migrar las aserciones a la capa del Test.
 * * @author Mario A. De León Rdz.
 * @date 08/04/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_021_ActualizarTarjetaBancariaEmpleado extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_021_ActualizarTarjetaBancariaEmpleado_test(HashMap<String, String> data) throws Exception {
        // Variables del caso
        String url = data.get("url");
        String username = data.get("username");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String numeroTarjeta = data.get("numeroTarjeta");
        String empleadoBusquedaNSS = data.get("empleadoBusquedaNSS"); 

        // Inicialización de utilerías
        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
        PortalAplicacionesLoginPage loginPage = new PortalAplicacionesLoginPage(u, testCase);
        PortalAplicacionesMainPage mainPage = new PortalAplicacionesMainPage(u, testCase);
        Empleados empleadosUpdNom = new Empleados(u, testCase); //metodos de proyecto anterior
        EmpleadoPage empleados = new EmpleadoPage(u, testCase);

        /**
         * ******************************************************************************
         * Paso 1 - Ingresar a la URL del portal
         * ******************************************************************************
         */
        addStep("Ingresar a la URL https://oxzvmsdlqlap01.femcom.net:8443/AccessControl/pages/login_form.jsf");
        Assert.assertTrue(loginPage.abrirPagina(url), "Error: No fue posible abrir la página de Login con la URL proporcionada.");

        /**
         * ******************************************************************************
         * Paso 2 - Ingresar credenciales de acceso
         * ******************************************************************************
         */
        addStep("Ingresar usuario y contraseña");
        Assert.assertTrue(loginPage.ingresarCredenciales(username, password), "Error: Fallo al ingresar las credenciales de acceso.");

        /**
         * ******************************************************************************
         * Paso 3 - Navegar a SADEL Nómina Comisionista
         * ******************************************************************************
         */
        addStep("Seleccionar la opción de SADEL NOMINA COMISIONISTA");
        Assert.assertTrue(mainPage.seleccionarSadelNomina(), "Error: No se pudo seleccionar la opción de SADEL Nómina en el menú principal.");

        /**
         * ******************************************************************************
         * Paso 4 - Ir a sección Empleados en el menú de SADEL
         * ******************************************************************************
         */
        addStep("Ir a pantalla 'Empleados' desde el menú de SADEL");
        try {
            empleadosUpdNom.SeleccionarBotonEmpleados();
        } catch (Exception e) {
            Assert.fail("Error: Falló la navegación a la pantalla de Empleados. Detalle: " + e.getMessage());
        }

        /**
         * ******************************************************************************
         * Paso 5 - Ingresar al módulo Buscar/Editar Empleados
         * ******************************************************************************
         */
        addStep("Ingresar NSS del empleado a buscar y ejecutar búsqueda");
        Assert.assertTrue(empleados.buscarEmpleadoPorNSS(empleadoBusquedaNSS), "Error: No se logró realizar la búsqueda del empleado con el NSS proporcionado.");

        /**
         * ******************************************************************************
         * Paso 6 - Seleccionar el empleado en los resultados
         * ******************************************************************************
         */
        addStep("Seleccionar checkbox del empleado en resultados y dar click en 'Editar'");
        try {
            empleadosUpdNom.SeleccionarEmpleado();
        } catch (Exception e) {
            Assert.fail("Error: No se pudo seleccionar el empleado de la tabla de resultados. Detalle: " + e.getMessage());
        }

        /**
         * ******************************************************************************
         * Paso 7 - Navegar a pestaña Información Básica
         * ******************************************************************************
         */
        addStep("Seleccionar pestaña 'Información Básica'");
        try {
            empleadosUpdNom.SeleccionarPestanaInformacionBasica();
        } catch (Exception e) {
            Assert.fail("Error: No se pudo acceder a la pestaña de Información Básica. Detalle: " + e.getMessage());
        }

        /**
         * ******************************************************************************
         * Paso 8 - Modificar tarjeta bancaria del empleado
         * ******************************************************************************
         */
        addStep("Modificar número de tarjeta bancaria, seleccionar motivo 'Nueva Cuenta' y guardar cambios");
        Assert.assertTrue(empleados.modificarDatoDelEmpleadoTarjetaBancaria(numeroTarjeta), "Error: Hubo un fallo al intentar modificar la tarjeta bancaria y guardar los cambios.");

        /**
         * ******************************************************************************
         * Paso 9 - Validar mensaje de actualización exitosa
         * ******************************************************************************
         */
        addStep("Validar pop up con mensaje 'El empleado ha sido actualizado exitosamente'");
        Assert.assertTrue(empleados.validarMensajeActualizacionExitosa(), "Error: No se mostró la alerta de actualización exitosa o el texto fue incorrecto.");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "\"Acceso al portal de aplicaciones\r\n"
                + "Usuario con permisos para editar empleado\r\n"
                + "Empleado con método de pago en efectivo registrado en SADEL\r\n"
                + "Número de tarjeta bancaria nueva disponible para la prueba\"";
    }

    @Override
    public String setTestDescription() {
        return "Se valida que sea posible editar información del empleado";
    }

    @Override
    public String setTestDesigner() {
        return "Mario A. De León Rdz.";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_021_ActualizarTarjetaBancariaEmpleado";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}