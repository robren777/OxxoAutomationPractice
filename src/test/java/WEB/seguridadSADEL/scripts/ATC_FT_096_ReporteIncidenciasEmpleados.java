package WEB.seguridadSADEL.scripts;

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
 * DESCRIPCIÓN: Generar el Reporte Incidencias de Empleados de manera correcta desde SADEL
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     -MTC_FT_096-Validar ejecución de reporte Incidencias de Empleados
 *
 * PREREQUISITOS:   
 * 		- Credenciales válidas para acceso al sistema
 *		- Tener un usuario con permisos para visualizar los Reportes
 *		- Agregar en la columna username del usuario con el que accederemos al portal
 *		- Agregar en la columna password con el que accederemos al portal 
 *		- Agregar en la columna zona el nombre de la zona que se usara para la busqueda
 *   	- Agregar en la columna plaza el nombre de la plaza que se usara para la busqueda
 *      - Agregar en la columna tienda el nombre de la tienda que se usara para la busqueda
 *      - Agregar en la columna periodo la fecha que se usara para la busqueda
 *		- Agregar en la columna rutabase la ruta donde se encuentra la carpeta en la que se guardara el archivo
 *  	- Agregar en la columna carpetaDescargas la carpeta donde se guardara el archivo descargado
 *      - Agregar en la columna Nombreesperado el nombre del archivo descargado a validar.
 *		- El Explorer debe ejecutarse en la versión IE5
 *
 * @author Carlos Martínez Ortega
 * @date 12/06/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_096_ReporteIncidenciasEmpleados extends BaseExecution {

    @Test(dataProvider = "data-provider")
    public void ATC_FT_096_ReporteIncidenciasEmpleados_test(HashMap<String, String> data) throws Exception {
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
		String periodo = data.get("periodo");
		String rutaBase = data.get("rutaBase");
		String carpetaDescargas = data.get("carpetaDescargas");
		String Nombreesperado = data.get("Nombreesperado");
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
        
        addStep("Seleccionar opción 'Incidencias Empleados'");
        assertTrue(Re.seleccionarIncidenciasEmpleados(), "No se logro dar clic en la opción Incidencias Empleados");
        
        addStep("Ingresar información en filtros de búsqueda");
        assertTrue(Re.llenadocamposfiltrosconperiodo(zona, plaza, tienda, periodo), "No se logró ingresar información en los campos");
        
        addStep("Clic en botón 'Generar'");
        assertTrue(Re.clicBotonGenerar(), "No se logró dar clic en el botón generar");
        
        addStep("Validar información en la tabla generada");
        assertTrue(Re.validarTablaCargada(), "No se logró validar la información de la tabla");
        
        addStep("Click en botón 'Exportar a Excel'");
        assertTrue(Re.clicBotonExportarExcel(), "No se logro dar clic en el botón Exportar Excel");
        
        addStep("Validar la generación del documento de excel con el reporte");
        assertTrue(Re.validarDescargaExcel(rutaBase, carpetaDescargas, Nombreesperado), "No se logró validar que el documento de excel se generara");
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
        return "Generar el Reporte Incidencias de Empleados de manera correcta desde SADEL";
    }

    @Override
    public String setTestDesigner() {
        // Mismo que @author
        return "";
    }

    @Override
    public String setTestFullName() {
        // Mismo nombre de la clase
        return "ATC_FT_096_ReporteIncidenciasEmpleados";
    }

    @Override
    public String setTestInstanceID() {
        // Dejar null
        return null;
    }
}
