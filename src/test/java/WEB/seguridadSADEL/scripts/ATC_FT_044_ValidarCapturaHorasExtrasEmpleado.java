package WEB.seguridadSADEL.scripts;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.seguridadSADEL.om.PortalAplicacionesLoginPage;
import WEB.seguridadSADEL.om.NominaIncidencias;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
// import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.password.PasswordUtil;

//import util.PortalEquipamiento.PomPortalEquipamiento;

import utils.selenium.MEdgeTest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCIÓN: Validar la correcta captura de horas extras de un empleado en el modulo de incidencias del sistema SADEL
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     -MTC_FT_044-Validar en Incidencias la captura de horas extras de empleado
 *
 * PREREQUISITOS: 
 * 		- Credenciales válidas para acceso al sistema
 *		- Configuración de registro de incidencias abierto
 *		- Agregar en la columna username del usuario con el que accederemos al portal
 *		- Agregar en la columna password con el que accederemos al portal 
 *		- Agregar en la columna nombreTienda el nombre de la tienda con el que trabajaremos
 *   	- Agregar en la columna numeroFila el número de fila del calendario
 *		- Agregar en la columna numeroColumna el número de columna del calendario
 *		- Agregar en la columna tipoIncidencia el tipo de incidencia que agregaremos
 *		- Agregar en la columna justificaciondelaCarga la justificación de la incidencia
 *		- Agregar en la columna cantidadHoras la cantidad de horas de la incidencia
 *		- Agregar en la columna cantidadMinutos la cantidad de minutos de la incidencia
 *		- El Explorer debe ejecutarse en la versión IE5
 *
 * @author Carlos Martínez Ortega
 * @date 26/05/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_044_ValidarCapturaHorasExtrasEmpleado extends BaseExecution {
	SeleniumUtil u;
	
    @Test(dataProvider = "data-provider")
    public void ATC_FT_044_ValidarCapturaHorasExtrasEmpleado_test(HashMap<String, String> data) throws Exception {
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
		String tipoIncidencia = data.get("tipoIncidencia");
		String justificaciondelaCarga = data.get("justificaciondelaCarga");
		String cantidadHoras = data.get("cantidadHoras");
		String cantidadMinutos = data.get("cantidadMinutos");
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
        
        addStep("Selecciónar día a cargar");
        assertTrue(Ni.seleccionarDiaACargar(numeroFila, numeroColumna, tipoIncidencia), "No se pudo seleccionar el día a cargar");
        
        addStep("Click en botón 'Nuevo'");
        assertTrue(Ni.seleccionarNuevo(tipoIncidencia), "No se pudo dar clic en el botón Nuevo");
        
        addStep("Seleccionar en Tipo de Incidencia opción 'Horas Extras'");
        assertTrue(Ni.seleccionarTipoIncidencia(tipoIncidencia), "No se pudo seleccionar el Tipo de Incidencia");
        
        addStep("Ingresar Justificación de la carga");
        assertTrue(Ni.ingresarJustificacionCarga(justificaciondelaCarga), "No se pudo ingresar la justificación");
        
        addStep("Llenar el campo Cantidad de Horas");
        assertTrue(Ni.ingresarCantidadHoras(cantidadHoras, cantidadMinutos), "No se logro llenar el campo Cantidad de Horas");
        
        addStep("Click en botón 'Guardar'");
        assertTrue(Ni.guardarIncidencia(), "No se pudo guardar la incidencia");
        
        addStep("Validar visualización de incidencia");
        assertTrue(Ni.validarVisualizacionDeIncidencia(numeroFila, numeroColumna, tipoIncidencia), "No se pudo validar la visualización de la incidencia");
        
        addStep("Click en botón 'Detalle'");
        assertTrue(Ni.verDetalleIncidencia(tipoIncidencia), "No se pudo abrir/ver el detalle de la incidencia");
        
        addStep("Validar la carga de incidencia correcta");
        assertTrue(Ni.validarMensajeListaDeEspera(), "No se validó el mensaje de lista de espera");
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
		+ "- Agregar en la columna nombreTienda del Data Provider la tienda que se usara.\r\n"
		+ "- Agregar en la columna numeroFila del Data Provider un número de fila.\r\n"
		+ "- Agregar en la columna numeroColumna del Data Provider un número de columna.\r\n"
		+ "- Agregar en la columna tipoIncidencia del Data Provider el tipo de incidencia que agregaremos.\r\n"
		+ "- Agregar en la columna justificaciondelaCarga del Data Provider una justificación para la incidencia que agregaremos.\r\n"
		+ "- Agregar en la columna cantidadHoras del Data Provider la cantidad de horas que agregaremos.\r\n"
		+ "- Agregar en la columna cantidadMinutos del Data Provider la cantidad de minutos que agregaremos."
		+ "- El Explorer debe ejecutarse en la versión IE5";
    }

    @Override
    public String setTestDescription() {
        // Mismo que DESCRIPCIÓN en documentación
        return "Validar la correcta captura de horas extras de un empleado en el modulo de incidencias del sistema SADEL";
    }

    @Override
    public String setTestDesigner() {
        // Mismo que @author
        return "";
    }

    @Override
    public String setTestFullName() {
        // Mismo nombre de la clase
        return "ATC_FT_044_ValidarCapturaHorasExtrasEmpleado";
    }

    @Override
    public String setTestInstanceID() {
        // Dejar null
        return null;
    }
}
