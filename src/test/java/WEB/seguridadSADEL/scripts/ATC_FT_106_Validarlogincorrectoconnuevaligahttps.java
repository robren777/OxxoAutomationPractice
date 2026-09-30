/**
 * 
 */
package WEB.seguridadSADEL.scripts;


import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.annotations.Test;

import WEB.seguridadSADEL.om.PortalAplicacionesLoginPage;
import WEB.util.GlobalVariables;
import azure.annotation.AzureTestInfo;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;

/**
* DESCRIPCIÓN: Validar que el login se realice correctamente
*
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: MTC-FT-106-
*
* PREREQUISITOS:
*   - En el DataProvider ingresar en la columna Usuario el usuario con el que se ingresará al sistema
*   - En el DataProvider ingresar en la columna Contraseña la contraseña correspondiente
*   - Acceso al aplicativo Seguridad SADEL
*   - Tener configurado el Edge para que se utilice en su versión IE5
*
* INSUMOS:
*   - Credenciales válidas para acceso al sistema
*
* @author Carlos Martínez Ortega
* @date 25/05/2026
*/
@AzureTestInfo(areaPath = "PTI251110-Seguridad SADEL", iterationPath = "PTI251110-Seguridad SADEL", tags = "Regresion;Automation;PTI251110-Seguridad SADEL", userStoryId = 1684)
public class ATC_FT_106_Validarlogincorrectoconnuevaligahttps extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_106_Validarlogincorrectoconnuevaligahttps_test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Seguridad SADEL -- Nomina Comisionista");

		/**
		 * Variables
		 ******************************************************************************************/
		String url = GlobalVariables.URL_PORTAL_APPS_SADEL;
		/**
		 * Variables Data Provider
		 ******************************************************************************************/
		String username = data.get("username");
		String password = PasswordUtil.decryptPassword(data.get("password"));
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

		/**
		 * Los Pasos de la Evidencia:
		 ******************************************************************************************/
		addStep("Ingresar URL https://fcportalqa.femcom.net:8443/AuthFemcoProx/login");
        assertTrue(Se.Ingresaralportal(url), "No se pudo ingresar a la URL del portal de aplicaciones");

        addStep("Ingresar usuario y contraseña");
        assertTrue(Se.login(username,password), "No se inició sesión correctamente");

        addStep("Seleccionar la opción de SADEL NOMINA COMISIONISTA");
        assertTrue(Se.validarAparezcaOpcionSadelNominaComisionista(), "No se ingresó correctamente al sistema SADEL");

        addStep("Validar ingreso correcto");
        assertTrue(Se.validarIngresoSadelNominaComisionista(), "No se ingreso correctamente al sistema Nomina Comisionista");
        
	}


	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "- En el DataProvider ingresar en la columna Usuario el usuario con el que se ingresará al sistema\r\n"
			 + "- En el DataProvider ingresar en la columna Contraseña la contraseña correspondiente\r\n"
		   	 + "- Acceso al aplicativo Seguridad SADEL (VPN, Puertos)\r\n"
			 + "- El Explorer debe ejecutarse en la versión IE5";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que el login se realize de manera correcta";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Carlos Martínez Ortega";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_106_Validarlogincorrectoconnuevaligahttps";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}