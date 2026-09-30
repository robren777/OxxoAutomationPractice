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
 * DESCRIPCIÓN: Se valida actualización de tarjeta bancaria por reposición por robo o extravio de empleado
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -MTC_FT_022-Validar actualización de tarjeta bancaria por reposición por robo o extravio de empleado
 *
 * PREREQUISITOS: "Acceso al portal de aplicaciones
 * Usuario con permisos para editar empleado
 * Empleado con método de pago en efectivo registrado en SADEL
 * Número de tarjeta bancaria nueva disponible para la prueba"
 *
 * INSUMOS:
 * @author Mario A. De León Rdz.
 * @date 08/04/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL")
public class ATC_FT_022_ActualizarTarjetaPorRoboExtravio extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_022_ActualizarTarjetaPorRoboExtravio_test(HashMap<String, String> data) throws Exception {
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
        
        // 3) Pasos
         /**
          * ******************************************************************************
          * Paso 1
          * ********************************************************************************
          */
        addStep("Ingresar a la URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
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
        addStep("Modificar número de tarjeta bancaria, seleccionar la opción \"Reposicion por robo o extravio\" y guardar cambios");
        Assert.assertTrue(empleados.modificarDatoDelEmpleadoTarjetaBancariaRoboOExtravio(numeroTarjeta), "Error: Hubo un fallo al intentar modificar la tarjeta bancaria por reposición y guardar los cambios.");

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
        return "Se valida actualización de tarjeta bancaria por reposición por robo o extravio de empleado";
    }

    @Override
    public String setTestDesigner() {
        return "Mario A. De León Rdz.";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_022_ActualizarTarjetaPorRoboExtravio";
    }

    @Override
    public String setTestInstanceID() {
        // Dejar null
        return null;
    }
}