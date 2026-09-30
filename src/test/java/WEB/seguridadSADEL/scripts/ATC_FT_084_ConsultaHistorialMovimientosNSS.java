package WEB.seguridadSADEL.scripts;

import static org.testng.Assert.assertTrue;
import java.util.HashMap;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.annotations.Test;

import WEB.seguridadSADEL.om.HistorialdeCambios;
import WEB.seguridadSADEL.om.PortalAplicacionesLoginPage;
import WEB.util.GlobalVariables;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Realizar una consulta en la pantalla de Consulta de Historial de Movimientos con el campo NSS
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     -MTC _FT_084-Consulta de Historial de Movimientos con el campo NSS
 *
 * PREREQUISITOS: 
 *		- Credenciales válidas para acceso al sistema
 *		- Un usuario con permisos para generar reportes
 *		- Agregar en la columna username del usuario con el que accederemos al portal
 *		- Agregar en la columna password con el que accederemos al portal 
 *		- Agregar en la columna zona el nombre de la zona que se usara para la busqueda
 *   	- Agregar en la columna plaza el nombre de la plaza que se usara para la busqueda
 *		- Agregar en la columna tienda el nombre de la tieda que se usara para la busqueda
 *		- Agregar en la columna nss el número nss del usuario que se usara para la busqueda
 *		- El Explorer debe ejecutarse en la versión IE5
 *
 *@author Carlos Martínez Ortega
 * @date 29/05/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_084_ConsultaHistorialMovimientosNSS extends BaseExecution {
	SeleniumUtil u;
    @Test(dataProvider = "data-provider")
    public void ATC_FT_084_ConsultaHistorialMovimientosNSS_test(HashMap<String, String> data) throws Exception {
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
		String tienda = data.get("tienda");
		String nss = data.get("nss");
		
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
        HistorialdeCambios Hc = new HistorialdeCambios(u, testCase);
		
        /**
		 * Los Pasos de la Evidencia:
		 ******************************************************************************************/
        addStep("Ingresar URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
        assertTrue(Se.Ingresaralportal(url), "No se pudo ingresar a la URL del portal de aplicaciones");

        addStep("Ingresar usuario y contraseña");
        assertTrue(Se.login(username,password), "No se inició sesión correctamente");

        addStep("Seleccionar la opción de SADEL NOMINA COMISIONISTA");
        assertTrue(Se.validarIngresoSadelNominaComisionista(), "No se ingresó correctamente al sistema SADEL");
        
        addStep("Ir a pantalla 'Historial de Movimientos'");
        assertTrue(Hc.pantallaHistorialDeMovimientos(),"No se ingresó correctamente a la pantalla Hisotrial de Movimientos");
        
        addStep("Seleccionar información en los campos Zona, Plaza y Tienda");
        assertTrue(Hc.ingresarInformacionEnFiltros(zona, plaza, tienda), "No se logro seleccionar información en los campos");
        
        addStep("Llenar el campo NSS con información correcta");
        assertTrue(Hc.seleccionarNss(nss), "No se pudo ingresar la información en el campo NSS");
        
        addStep("Dar click en el Botón 'Buscar'");
        assertTrue(Hc.seleccionarBuscar(), "No se logro dar clic en el botón buscar");
        
        addStep("En pantalla se deben mostrar los registros seleccionados a mostrar y que cumplen con el valor seleccionado de estatus de empleado");
        assertTrue(Hc.validarTablaConInformacion(), "No se motraron los registros en pantalla");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        // Mismo que PREREQUISITOS en documentación
        return "- Contar con acceso a la VPN\r\n"
		+ "- Configuración de registro de incidencias abierto "
        + "- Agregar en la columna username del Data Provider un username que exista \r\n"
		+ "- Agregar en la columna password del Data Provider el password correcto.\r\n"
		+ "- Agregar en la columna zona del Data Provider la zona que se usara para la busqueda.\r\n"
		+ "- Agregar en la columna plaza del Data Provider la pplaza que se usara para la busqueda.\r\n"
		+ "- Agregar en la columna tienda del Data Provider la tienda que se usara para la busqueda.\r\n"
		+ "- Agregar en la columna nss del Data Provider el nss que se usara para la busqueda.\r\n"
		+ "- El Explorer debe ejecutarse en la versión IE5";
    }

    @Override
    public String setTestDescription() {
        // Mismo que DESCRIPCIÓN en documentación
        return "Realizar una consulta en la pantalla de Consulta de Historial de Movimientos con el campo NSS";
    }

    @Override
    public String setTestDesigner() {
        // Mismo que @author
        return "";
    }

    @Override
    public String setTestFullName() {
        // Mismo nombre de la clase
        return "ATC_FT_084_ConsultaHistorialMovimientosNSS";
    }

    @Override
    public String setTestInstanceID() {
        // Dejar null
        return null;
    }
}
