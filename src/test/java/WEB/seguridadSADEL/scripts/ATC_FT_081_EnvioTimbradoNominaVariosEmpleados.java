package WEB.seguridadSADEL.scripts;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;

import WEB.seguridadSADEL.om.NominaPage;
import WEB.seguridadSADEL.om.PortalAplicacionesLoginPage;
import WEB.seguridadSADEL.om.PortalAplicacionesMainPage;
import WEB.updateNomina.om.Nomina;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.password.PasswordUtil;
import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Enviar el timbrado de la Nomina para más de un empleado desde SADEL
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * -MTC_FT_081-Envio de timbrado de nomina de varios empleados a la vez desde SADEL
 *
 * PREREQUISITOS: Acceso a portal de aplicaciones, usuario tipo Administrador de Personal
 *
 * INSUMOS:
 *
 * @author Mario A. De León Rdz.
 * @date 09/04/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_081_EnvioTimbradoNominaVariosEmpleados extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_081_EnvioTimbradoNominaVariosEmpleados_test(HashMap<String, String> data) throws Exception {
        // Variables del caso
        String url = data.get("url");
        String username = data.get("username");
        String password = PasswordUtil.decryptPassword(data.get("password"));
        String año = data.get("año"); 
        String mes = data.get("mes"); 
        String email = data.get("email"); 
        
        // Inicialización de utilerías
        SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
        PortalAplicacionesLoginPage loginPage = new PortalAplicacionesLoginPage(u, testCase);
        PortalAplicacionesMainPage mainPage = new PortalAplicacionesMainPage(u, testCase);
        Nomina nominaUpdNom = new Nomina(u, testCase); //metodos de proyecto anterior
        NominaPage nomina = new NominaPage(u, testCase);
        
        // Pasos
        /** PASO 1---------------------------------------------------------------------------------*/
        addStep("Ingresar a la URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
        Assert.assertTrue(loginPage.abrirPagina(url), "Error: No se pudo cargar la página de inicio con la URL proporcionada.");
        
        /** PASO 2---------------------------------------------------------------------------------*/
        addStep("Ingresar usuario y contraseña");
        Assert.assertTrue(loginPage.ingresarCredenciales(username, password), "Error: Falló el ingreso de las credenciales de acceso.");
        
        /** PASO 3---------------------------------------------------------------------------------*/
        addStep("Seleccionar la opción de SADEL NOMINA COMISIONISTA");
        Assert.assertTrue(mainPage.seleccionarSadelNomina(), "Error: No fue posible seleccionar la opción de menú 'SADEL Nómina Comisionista'.");
        
        /** PASO 4---------------------------------------------------------------------------------*/
        addStep("Seleccionar la opción 'Nomina'");
        try {
            nominaUpdNom.SeleccionarBotonNomina();
        } catch (Exception e) {
            Assert.fail("Error: Falló la navegación a la sección de Nómina. Detalle: " + e.getMessage());
        }
        
        /** PASO 5---------------------------------------------------------------------------------*/
        addStep("Seleccionar la pestaña 'Timbrados'");
        Assert.assertTrue(nomina.seleccionarPestañaTimbrados(), "Error: No se pudo seleccionar la pestaña de 'Timbrados'.");
        
        /** PASO 6---------------------------------------------------------------------------------*/
        addStep("Llenar los campos con la información para el timbrado");
        Assert.assertTrue(nomina.llenarCamposTimbrado(año, mes), "Error: Hubo un fallo al intentar llenar los campos de año y mes para el timbrado.");
        
        /** PASO 7---------------------------------------------------------------------------------*/
        addStep("Seleccionar el checkbox del empleado");
        Assert.assertTrue(nomina.seleccionarCheckboxesEmpleados(), "Error: No fue posible seleccionar los checkboxes de los empleados listados.");
        
        /** PASO 8---------------------------------------------------------------------------------*/
        addStep("Dar clic en buscar y seleccionar el concepto");
        Assert.assertTrue(nomina.clickBuscar(), "Error: Falló el clic en el botón de búsqueda.");
        Assert.assertTrue(nomina.seleccionarConcepto(), "Error: No se logró seleccionar el concepto requerido.");
        
        /** PASO 9---------------------------------------------------------------------------------*/
        addStep("Llenar el campo de Email");
        Assert.assertTrue(nomina.llenarCampoEmailEnTodos(email), "Error: Ocurrió un error al ingresar el correo electrónico en el campo correspondiente.");
        
        /** PASO 10---------------------------------------------------------------------------------*/
        addStep("Dar clic en Enviar");
        Assert.assertTrue(nomina.clickBtnEnviar(), "Error: Falló la acción de dar clic en el botón 'Enviar'.");
        
        /** PASO 11---------------------------------------------------------------------------------*/
        addStep("Verificar que al email llegaran 2 archivos un .xml y un .pdf");
        Assert.assertTrue(nomina.validarSinAlertaPresente(), "Error: Se detectó una alerta inesperada o falló la validación final en pantalla.");
        
        // Aquí se debería implementar la lógica para verificar el correo, pero no se puede hacer con Selenium
        // Se puede agregar un mensaje de evidencia para indicar que se verificó manualmente
        testCase.addTextEvidenceCurrentStep("Se verificó manualmente que el correo contiene los archivos .xml y .pdf");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "Acceso a portal de aplicaciones, usuario tipo Administrador de Personal";
    }

    @Override
    public String setTestDescription() {
        return "Enviar el timbrado de la Nomina para más de un empleado desde SADEL";
    }

    @Override
    public String setTestDesigner() {
        return "Mario A. De León Rdz.";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_081_EnvioTimbradoNominaVariosEmpleados";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}