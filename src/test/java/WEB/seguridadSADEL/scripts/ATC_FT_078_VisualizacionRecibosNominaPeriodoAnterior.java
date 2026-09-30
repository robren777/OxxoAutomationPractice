package WEB.seguridadSADEL.scripts;

import static org.testng.Assert.assertTrue;
import java.util.HashMap;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.annotations.Test;

import WEB.seguridadSADEL.om.PortalAplicacionesLoginPage;
import WEB.seguridadSADEL.om.NominaRecibos;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Consultar los recibos de nómina de un periodo anterior
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     -MTC_FT_078-Visualización de Recibos de Nomina de un periodo anterior
 *
 * PREREQUISITOS:  
 *      - Contar con nominas de Periodos Anteriores 
 * 		- Credenciales válidas para acceso al sistema
 *		- Agregar en la columna username del usuario con el que accederemos al portal
 *		- Agregar en la columna password con el que accederemos al portal 
 *      - Agregar en la columna tienda el nombre de la tienda que se usara para la busqueda
 *      - Agregar en la columna periodo la fecha que se usara para la busqueda
 *		- El Explorer debe ejecutarse en la versión IE5
 *
 * @author Carlos Martínez Ortega
 * @date 12/06/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_078_VisualizacionRecibosNominaPeriodoAnterior extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_078_VisualizacionRecibosNominaPeriodoAnterior_test(HashMap<String, String> data) throws Exception {
    	/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_SADEL;
		/**
		 * Variables Data Provider
		 ******************************************************************************************/
		String username = data.get("username");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String tienda = data.get("tienda");
		String periodo = data.get("periodo");
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
        NominaRecibos Nr = new NominaRecibos(u, testCase);
		
        /**
		 * Los Pasos de la Evidencia:
		 ******************************************************************************************/
        addStep("Ingresar URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
        assertTrue(Se.Ingresaralportal(url), "No se pudo ingresar a la URL del portal de aplicaciones");

        addStep("Ingresar usuario y contraseña");
        assertTrue(Se.login(username,password), "No se inició sesión correctamente");

        addStep("Seleccionar la opción de SADEL NOMINA COMISIONISTA");
        assertTrue(Se.validarIngresoSadelNominaComisionista(), "No se ingresó correctamente al sistema SADEL");
        
        addStep("Seleccionar la opción 'Nomina'");
        assertTrue(Nr.pantallaNomina(), "No se desplegó la pantalla de Nómina");
        
        addStep("Seleccionar la pestaña de 'Recibos'");
        assertTrue(Nr.seleccionarPestañaRecibos(), "No se pudo seleccionar la pestaña de recibos");
        
        addStep("Seleccionar la Tienda y el Periodo");
        assertTrue(Nr.seleccionarTiendaYPeriodo(tienda, periodo), "No se pudo seleccionar la tienda o el periodo");
        
        addStep("Validar se visualicen los empleado con su opcion de ver recibo");
        assertTrue(Nr.validarTablaConInformacion(), "No se visualizan los recibos de los empleados");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        // Mismo que PREREQUISITOS en documentación
        return "- Contar con nominas de Periodos Anteriores \r\n"
        + "- Credenciales válidas para acceso al sistema\r\n"
		+ "- Tener un usuario con permisos para visualizar los Reportes "
        + "- Agregar en la columna username del Data Provider un username con permisos para visualizar reportes \r\n"
		+ "- Agregar en la columna password del Data Provider el password correcto.\r\n"
		+ "- Agregar en la columna zona del Data Provider la zona que se usara para la busqueda.\r\n"
		+ "- Agregar en la columna plaza del Data Provider la plaza que se usara para la busqueda.\r\n"
		+ "- Agregar en la columna tienda del Data Provider la tienda que se usara para la busqueda.\r\n"
		+ "- Agregar en la columna periodo del Data Provider el periodo que se usara para la busqueda.\r\n"
		+ "- Agregar en la columna rutabase del Data Provider la ruta donde se encuentra la carpeta en la que se guardara el archivo"
   	 	+ "- Agregar en la columna carpetaDescargas del Data Provider la carpeta donde se guardara el archivo descargado"
        + "- Agregar en la columna Nombreesperado del Data Provider el nombre del archivo descargado a validar."
		+ "- El Explorer debe ejecutarse en la versión IE5";
    }

    @Override
    public String setTestDescription() {
        // Mismo que DESCRIPCIÓN en documentación
        return "Consultar los recibos de nómina de un periodo anterior";
    }

    @Override
    public String setTestDesigner() {
        // Mismo que @author
        return "Carlos Martínez Ortega";
    }

    @Override
    public String setTestFullName() {
        // Mismo nombre de la clase
        return "ATC_FT_078_VisualizacionRecibosNominaPeriodoAnterior";
    }

    @Override
    public String setTestInstanceID() {
        // Dejar null
        return null;
    }
}
