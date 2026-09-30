package WEB.seguridadSADEL.scripts;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.annotations.Test;

import WEB.seguridadSADEL.om.PortalAplicacionesLoginPage;
import WEB.seguridadSADEL.om.PendientesanteIMSS;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
// import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.password.PasswordUtil;

//import util.PortalEquipamiento.PomPortalEquipamiento;

import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCIÓN: Validar la correcta captura de presencias de un empleado en el modulo de incidencias del sistema SADEL
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     -MTC_FT_111-Validar exportación de excel de Pendientes ante el IMSS
 *
 * PREREQUISITOS: 
 * 	- En el DataProvider ingresar en la columna Usuario el usuario con el que se ingresará al sistema
*   - En el DataProvider ingresar en la columna Contraseña la contraseña correspondiente
*   - En el DataProvider ingresar en la columna rutabase la ruta donde se encuentra la carpeta en la que se guardara el archivo
*   - En el DataProvider ingresar en la columna carpetaDescargas la carpeta donde se guardara el archivo descargado
*   - En el DataProvider ingresar en la columna Nombreesperado el nombre del archivo descargado a validar.
*   - Acceso al aplicativo Seguridad SADEL
*   - Tener configurado el Edge para que se utilice en su versión IE5
 *
 * INSUMOS:
 *		- Credenciales válidas para acceso al sistema 
 *
 * @author Carlos Martínez Ortega
 * @date 08/06/2026
 */
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_111_ValidarexportaciondeexceldePendientesanteelIMSS extends BaseExecution {
	SeleniumUtil u;
	
    @Test(dataProvider = "data-provider")
    public void ATC_FT_111_ValidarexportaciondeexceldePendientesanteelIMSS_test(HashMap<String, String> data) throws Exception {
    	/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_SADEL;
		/**
		 * Variables Data Provider
		 ******************************************************************************************/
		String username = data.get("username");
		String password = PasswordUtil.decryptPassword(data.get("password"));
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
        PendientesanteIMSS Pe = new PendientesanteIMSS(u, testCase);
		
        /**
		 * Los Pasos de la Evidencia:
		 ******************************************************************************************/
        addStep("Ingresar URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
        assertTrue(Se.Ingresaralportal(url), "No se pudo ingresar a la URL del portal de aplicaciones");

        addStep("Ingresar usuario y contraseña");
        assertTrue(Se.login(username,password), "No se inició sesión correctamente");

        addStep("Seleccionar la opción de SADEL NOMINA COMISIONISTA");
        assertTrue(Se.validarIngresoSadelNominaComisionista(), "No se ingresó correctamente al sistema SADEL");
        
        addStep("Seleccionar del menú de opciones el botón de Pendientes ante el IMSS");
        assertTrue(Pe.seleccionarPendientesAnteIMSS(), "No se logro seleccionar la opción Pendientes ante el IMSS");
        
        addStep("Click en botón 'Exportar a Excel'");
        assertTrue(Pe.seleccionarExportarExcel(), "No se logro dar clic en el botón exportar a excel");
        
        addStep("Validar excel generado correctamente");
        assertTrue(Pe.validarDescargaExcel(rutaBase, carpetaDescargas, Nombreesperado), "No se encontro el excel descargado");
    }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        // Mismo que PREREQUISITOS en documentación
        return "Tener información en la tabla Pendiente ante IMSS";
    }

    @Override
    public String setTestDescription() {
        // Mismo que DESCRIPCIÓN en documentación
        return "- En el DataProvider ingresar en la columna Usuario el usuario con el que se ingresará al sistema\r\n"
        	 + "- En el DataProvider ingresar en la columna Contraseña la contraseña correspondiente\r\n"
        	 + "- En el DataProvider ingresar en la columna rutabase la ruta donde se encuentra la carpeta en la que se guardara el archivo"
        	 + "- En el DataProvider ingresar en la columna carpetaDescargas la carpeta donde se guardara el archivo descargado"
             + "- En el DataProvider ingresar en la columna Nombreesperado el nombre del archivo descargado a validar."
        	 + "- Acceso al aplicativo Seguridad SADEL"
             + "- Tener configurado el Edge para que se utilice en su versión IE5";
    }

    @Override
    public String setTestDesigner() {
        // Mismo que @author
        return "Carlos Martínez Ortega";
    }

    @Override
    public String setTestFullName() {
        // Mismo nombre de la clase
        return "ATC_FT_111_ValidarexportacióndeexceldePendientesanteelIMSS";
    }

    @Override
    public String setTestInstanceID() {
        // Dejar null
        return null;
    }
}
