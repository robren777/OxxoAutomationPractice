package WEB.seguridadSADEL.scripts;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.annotations.Test;

import WEB.seguridadSADEL.om.NominaIncidencias;
import WEB.seguridadSADEL.om.PortalAplicacionesLoginPage;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;
import utils.password.PasswordUtil;
import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Validar la eliminación de una incidencia correctamente
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     -MTC_FT_055-Validar eliminación de incidencias
 *
 * PREREQUISITOS: 
 * 		- Credenciales válidas para acceso al sistema
 *		- Configuración de registro de incidencias abierto
 *		- Agregar en la columna username del usuario con el que accederemos al portal
 *		- Agregar en la columna password con el que accederemos al portal 
 *		- Agregar en la columna nombreTienda el nombre de la tienda con el que trabajaremos
 *   	- Agregar en la columna numeroFila el número de fila del calendario
 *		- Agregar en la columna numeroColumna el número de columna del calendario
 *		- El Explorer debe ejecutarse en la versión IE5
 *
 * @author Carlos Martínez Ortega 
 * @date 27/05/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_055_ValidarEliminacionIncidencias extends BaseExecution {
	SeleniumUtil u;
    @Test(dataProvider = "data-provider")
    public void ATC_FT_055_ValidarEliminacionIncidencias_test(HashMap<String, String> data) throws Exception {
    	/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_SADEL;
		/**
		 * Variables Data Provider
		 ******************************************************************************************/
		String username = data.get("username");
		String password = PasswordUtil.decryptPassword(data.get("password"));
		String nombreTienda = data.get("tienda");
		String numeroFila = data.get("numeroFila");
		String numeroColumna = data.get("numeroColumna");	
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
        
        // SikuliSADEL sikuliSADEL = new SikuliSADEL(u, testCase, "WEB\\SeguridadSADEL");
		PortalAplicacionesLoginPage Se = new PortalAplicacionesLoginPage(u, testCase);
        NominaIncidencias Ni = new NominaIncidencias(u, testCase);
        
        /**
		 * Los Pasos de la Evidencia:
		 ******************************************************************************************/
        addStep("Ingresar URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
        assertTrue(Se.Ingresaralportal(url), "No se pudo ingresar a la URL del portal de aplicaciones");

        addStep("Ingresar usuario y contraseña");
        assertTrue(Se.login(username,password), "No se inició sesión correctamente");

        addStep("Seleccionar la opción de SADEL NOMINA COMISIONISTA");
        assertTrue(Se.validarIngresoSadelNominaComisionista(), "No se ingresó correctamente al sistema SADEL");
        
        addStep("Seleccionar del menú de opciones el botón de Nomina");
        assertTrue(Ni.pantallaNomina(), "No se desplegó la pantalla de Nómina");
        
        addStep("Seleccionar pestaña de 'Incidencias'");
        assertTrue(Ni.seleccionarPestañaIncidencias(), "No se pudo seleccionar la pestaña de Incidencias");
        
        addStep("Seleccionar Tienda");
        assertTrue(Ni.seleccionarTienda(nombreTienda), "No se pudo seleccionar la tienda");
        
        addStep("Capturar periodo de fechas");
        assertTrue(Ni.seleccionarPeriodo(), "No se pudo seleccionar el rango de fechas");
        
        addStep("Selecciónar donde haya un registro de Absentismos");
        assertTrue(Ni.seleccionarDiaAEliminar(numeroFila, numeroColumna), "No se pudo seleccionar un registro");
        
        addStep("Click en botón 'Eliminar'");
        assertTrue(Ni.seleccionarEliminar(), "No se pudo dar clic en el botón eliminar");
        
        addStep("Click en el botón 'Aceptar'");
        assertTrue(Ni.seleccionarAceptar(), "No se pudo dar clic en el botón aceptar");
        
        addStep("Validar la eliminación correcta de la incidencia");
        assertTrue(Ni.validarIncidenciaEliminada(numeroFila, numeroColumna), "Se valida que aun existe la incidencia");
        
        }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "- Contar con acceso a la VPN\r\n"
        		+ "- Configuración de registro de incidencias abierto "
                + "- Agregar en la columna username del Data Provider un username que exista \r\n"
        		+ "- Agregar en la columna password del Data Provider el password correcto.\r\n"
        		+ "- Agregar en la columna nombreTienda del Data Provider la tienda que se usara.\r\n"
        		+ "- Agregar en la columna numeroFila del Data Provider un número de fila.\r\n"
        		+ "- Agregar en la columna numeroColumna del Data Provider un número de columna.\r\n"
        		+ "- El Explorer debe ejecutarse en la versión IE5";
    }

    @Override
    public String setTestDescription() {
        return "Validar la eliminación de una incidencia correctamente";
    }

    @Override
    public String setTestDesigner() {
        return "";
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_055_ValidarEliminacionIncidencias";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
