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
 * DESCRIPCIÓN: Se valida que sea posible editar información del empleado
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -MTC_FT_024-Validar actualización de RFC de empleado
 *
 * PREREQUISITOS: "Acceso al portal de aplicaciones
 * Usuario con Rol Despacho
 * Empleado registrado en SADEL con RFC capturado
 * RFC nuevo disponible para la prueba"
 *
 * INSUMOS:
 * * DEUDA TÉCNICA:
 * - En caso de reutilizar o refactorizar este script en el futuro, se deberá corregir tanto el 
 * script de prueba como los OMs involucrados para migrar las aserciones a la capa del Test.
 * *
 * @author Mario A. De León Rdz.
 * @date 08/04/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_024_ValidarActualizacionRFCEmpleado extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_024_ValidarActualizacionRFCEmpleado_test(HashMap<String, String> data) throws Exception {
        // Variables del caso
        String url = data.get("url");
        String username = data.get("username");
        String password = PasswordUtil.decryptPassword(data.get("password"));
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
        Assert.assertTrue(loginPage.abrirPagina(url), "Error: No fue posible abrir la página de Login utilizando la URL proporcionada.");
        
        /** PASO 2---------------------------------------------------------------------------------*/
        addStep("Ingresar usuario y contraseña rol Despacho");
        Assert.assertTrue(loginPage.ingresarCredenciales(username, password), "Error: Falló el ingreso de las credenciales de acceso con rol Despacho.");
        
        /** PASO 3---------------------------------------------------------------------------------*/
        addStep("Seleccionar la opción de SADEL NOMINA COMISIONISTA");
        Assert.assertTrue(mainPage.seleccionarSadelNomina(), "Error: No se pudo seleccionar la opción de menú 'SADEL Nómina Comisionista'.");
        
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
        addStep("Seleccionar pestaña 'Información Básica'");
        try {
            empleadosUpdNom.SeleccionarPestanaInformacionBasica();
        } catch (Exception e) {
            Assert.fail("Error: Falló el acceso a la pestaña de Información Básica. Detalle: " + e.getMessage());
        }
        
        /** PASO 8---------------------------------------------------------------------------------*/
        addStep("Modificar información del campo 'RFC' y guardar cambios");
        Assert.assertTrue(empleados.modificarDatoDelEmpleadoRFCConUsuarioDespacho(), "Error: Hubo un fallo al intentar modificar el campo RFC con el usuario Despacho y guardar los cambios.");
        
        /** PASO 9---------------------------------------------------------------------------------*/
        addStep("Validar pop up con mensaje 'El empleado ha sido actualizado exitosamente'");
        Assert.assertTrue(empleados.validarMensajeActualizacionExitosa(), "Error: El pop-up de confirmación de actualización exitosa no apareció o el mensaje es incorrecto.");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "\"Acceso al portal de aplicaciones\r\n"
                + "Usuario con Rol Despacho\r\n"
                + "Empleado registrado en SADEL con RFC capturado\r\n"
                + "RFC nuevo disponible para la prueba\"";
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
        return "ATC_FT_024_ValidarActualizacionRFCEmpleado";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}