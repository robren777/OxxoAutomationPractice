package WEB.seguridadSADEL.scripts;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import WEB.seguridadSADEL.om.EmpleadoPage;
import WEB.seguridadSADEL.om.PortalAplicacionesLoginPage;
import WEB.seguridadSADEL.om.PortalAplicacionesMainPage;
import WEB.updateNomina.om.Empleados;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Validar que sea posible editar información del empleado
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -MTC_FT_027-Validar actualización de domicilio de empleado
 *
 * PREREQUISITOS:
 * "Acceso al portal de aplicaciones
 * Usuario con permisos para editar empleados
 * Empleado registrado en SADEL con DOMICILIO capturado
 * DOMICILIO nuevo disponible para la prueba"
 *
 * INSUMOS:
 *
 * DEUDA TÉCNICA:
 * - En caso de reutilizar o refactorizar este script en el futuro, se deberá corregir tanto el 
 * script de prueba como los OMs involucrados para migrar las aserciones a la capa del Test.
 *
 * @author Mario A. De León Rdz.
 * @date 08/04/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_027_ActualizarDomicilioEmpleado extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_027_ActualizarDomicilioEmpleado_test(HashMap<String, String> data) throws Exception {
        // Variables del caso
        String url = data.get("url");
        String username = data.get("username");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String calle = data.get("calle");
        String empleadoBusquedaNSS = data.get("empleadoBusquedaNSS"); 
        
        // Inicialización de utilerías
        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
        PortalAplicacionesLoginPage loginPage = new PortalAplicacionesLoginPage(u, testCase);
        PortalAplicacionesMainPage mainPage = new PortalAplicacionesMainPage(u, testCase);
        Empleados empleadosUpdNom = new Empleados(u, testCase); //metodos de proyecto anterior
        EmpleadoPage empleados = new EmpleadoPage(u, testCase);
        
        // Pasos
        /** PASO 1---------------------------------------------------------------------------------*/
        addStep("Ingresar a la URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
        Assert.assertTrue(loginPage.abrirPagina(url), "Error: No se pudo cargar la página de inicio con la URL proporcionada.");
        
        /** PASO 2---------------------------------------------------------------------------------*/
        addStep("Ingresar usuario y contraseña rol Despacho");
        Assert.assertTrue(loginPage.ingresarCredenciales(username, password), "Error: Falló el ingreso de las credenciales de acceso.");
        
        /** PASO 3---------------------------------------------------------------------------------*/
        addStep("Seleccionar la opción de SADEL NOMINA COMISIONISTA");
        Assert.assertTrue(mainPage.seleccionarSadelNomina(), "Error: No fue posible seleccionar la opción de menú 'SADEL Nómina Comisionista'.");
        
        /** PASO 4---------------------------------------------------------------------------------*/
        addStep("Ir a pantalla 'Empleados' desde el menú de SADEL");
        try {
            empleadosUpdNom.SeleccionarBotonEmpleados();
        } catch (Exception e) {
            Assert.fail("Error: Falló la navegación a la pantalla de Empleados. Detalle: " + e.getMessage());
        }
        
        /** PASO 5---------------------------------------------------------------------------------*/
        addStep("Ingresar NSS del empleado a buscar y ejecutar búsqueda");
        Assert.assertTrue(empleados.buscarEmpleadoPorNSS(empleadoBusquedaNSS), "Error: No se logró realizar la búsqueda del empleado con el NSS proporcionado.");
        
        /** PASO 6---------------------------------------------------------------------------------*/
        addStep("Seleccionar checkbox del empleado en resultados y dar click en 'Editar'");
        try {
            empleadosUpdNom.SeleccionarEmpleado();
        } catch (Exception e) {
            Assert.fail("Error: No se pudo seleccionar el empleado de los resultados de búsqueda. Detalle: " + e.getMessage());
        }
        
        /** PASO 7---------------------------------------------------------------------------------*/
        addStep("Seleccionar pestaña 'Datos Generales'");
        try {
            empleadosUpdNom.SeleccionarPestanaDatosGenerales();
        } catch (Exception e) {
            Assert.fail("Error: No fue posible acceder a la pestaña de Datos Generales. Detalle: " + e.getMessage());
        }
        
        /** PASO 8---------------------------------------------------------------------------------*/
        addStep("Modificar información del campo 'Domicilio' y guardar cambios");
        Assert.assertTrue(empleados.modificarDatoDelEmpleadoDomicilioCalle(calle), "Error: Ocurrió un fallo al intentar modificar el campo de domicilio (calle) y guardar los cambios.");
        
        /** PASO 9---------------------------------------------------------------------------------*/
        addStep("Validar pop up con mensaje 'El empleado ha sido actualizado exitosamente'");
        Assert.assertTrue(empleados.validarMensajesActualizacionExitosaDatoGenerales(), "Error: El pop-up de confirmación para la actualización de Datos Generales no apareció o contiene un texto incorrecto.");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "\"Acceso al portal de aplicaciones\r\n"
                + "Usuario con permisos para editar empleados\r\n"
                + "Empleado registrado en SADEL con DOMICILIO capturado\r\n"
                + "DOMICILIO nuevo disponible para la prueba\"";
    }

    @Override
    public String setTestDescription() {
        return "Validar que sea posible editar información del empleado";
    }

    @Override
    public String setTestDesigner() {
        return "Mario A. De León Rdz.";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_027_ActualizarDomicilioEmpleado";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}