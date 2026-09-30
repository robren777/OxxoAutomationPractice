package WEB.seguridadSADEL.scripts;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.util.HashMap;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.annotations.Test;

import WEB.seguridadSADEL.om.PortalAplicacionesLoginPage;
import WEB.seguridadSADEL.om.Reportes;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;
import utils.password.PasswordUtil;
import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Generar reporte el Reporte Validación de Jornadas de manera correcta desde SADEL
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     -MTC _FT_099-Validar ejecución de reporte Validación de Jornadas
 *
 * PREREQUISITOS:   
 * 		- Credenciales válidas para acceso al sistema
 *		- Tener un usuario con permisos para visualizar los Reportes
 *		- Agregar en la columna username del usuario con el que accederemos al portal
 *		- Agregar en la columna password con el que accederemos al portal 
 *		- Agregar en la columna zona el nombre de la zona que se usara para la busqueda
 *   	- Agregar en la columna plaza el nombre de la plaza que se usara para la busqueda
 *		- El Explorer debe ejecutarse en la versión IE5
 *
 * @author Carlos Martínez Ortega
 * @date 12/06/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_099_ValidacionReporteJornadas extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_099_ValidacionReporteJornadas_test(HashMap<String, String> data) throws Exception {
    	/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_SADEL;
		/**
		 * Variables Data Provider
		 ******************************************************************************************/
		String username = data.get("username");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String zona = data.get("zona");
		String plaza = data.get("plaza");
		/**
		 * Utilerias
		 ******************************************************************************************/
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.attachToEdgeChrome();
		options.withEdgeExecutablePath("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");
		options.setCapability("ignoreProtectedModeSettings", true);
        options.setCapability("ignoreZoomSetting",true);
        options.withInitialBrowserUrl("https://www.google.com");
      
        InternetExplorerDriver driver = new InternetExplorerDriver(options);
        
        SeleniumUtil u = new SeleniumUtil(driver);
        
		PortalAplicacionesLoginPage Se = new PortalAplicacionesLoginPage(u, testCase);
        Reportes Re = new Reportes(u, testCase);
		
        /**
		 * Los Pasos de la Evidencia:
		 ******************************************************************************************/
        addStep("Ingresar URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
        assertTrue(Se.Ingresaralportal(url), "No se pudo ingresar a la URL del portal de aplicaciones");

        addStep("Ingresar usuario y contraseña");
        assertTrue(Se.login(username,password), "No se inició sesión correctamente");

        addStep("Seleccionar la opción de SADEL NOMINA COMISIONISTA");
        assertTrue(Se.validarIngresoSadelNominaComisionista(), "No se ingresó correctamente al sistema SADEL");
        
        addStep("Seleccionar del menú de opciones la pestaña 'Reportes'");
        assertTrue(Re.seleccionarReportes(), "No se logró seleccionar la opción de Reportes del menú");
        
        addStep("Seleccionar opción 'Validación de Jornadas'");
        assertTrue(Re.seleccionarValidacionJornadas(), "No se logró seleccionar la opción Validación de Jornadas");
        
        addStep("Ingresar información en filtros de búsqueda");
        assertTrue(Re.ingresarInformacionFiltrosConPeriodo(zona, plaza), "No se logro ingresar información en los filtros");
        
        addStep("Clic en botón 'Generar'");
        assertTrue(Re.clicBotonGenerar(), "No se logró dar clic en el botón generar");
        
        addStep("Validar información en la tabla generada");
        assertTrue(Re.validarTablaCargada(), "No se logró validar la información de la tabla generada");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        // Mismo que PREREQUISITOS en documentación
        return "- Credenciales válidas para acceso al sistema\r\n"
		+ "- Tener un usuario con permisos para visualizar los Reportes "
        + "- Agregar en la columna username del Data Provider un username con permisos para visualizar reportes \r\n"
		+ "- Agregar en la columna password del Data Provider el password correcto.\r\n"
		+ "- Agregar en la columna zona del Data Provider la zona que se usara para la busqueda.\r\n"
		+ "- Agregar en la columna plaza del Data Provider la plaza que se usara para la busqueda.\r\n"
		+ "- El Explorer debe ejecutarse en la versión IE5";
    }

    @Override
    public String setTestDescription() {
        // Mismo que DESCRIPCIÓN en documentación
        return "Generar reporte el Reporte Validación de Jornadas de manera correcta desde SADEL";
    }

    @Override
    public String setTestDesigner() {
        // Mismo que @author
        return "Carlos Martínez Ortega";
    }

    @Override
    public String setTestFullName() {
        // Mismo nombre de la clase
        return "ATC_FT_099_ValidacionReporteJornadas";
    }

    @Override
    public String setTestInstanceID() {
        // Dejar null
        return null;
    }
}
