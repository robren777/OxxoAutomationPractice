package Mobile.Mep.PromOxxoImagenes;

import Mobile.Mep.PromOxxoOffline.om.PostgreSQLOffline;
import Mobile.Mep.PromOxxoOffline.om.UtilOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaOAuth0Offline;
import Mobile.Mep.om.*;
import Mobile.util.GlobalVariables;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

import java.util.HashMap;

import static org.testng.Assert.assertTrue;

/**
* DESCRIPCIÓN:<p> Verificar que la etiqueta informada anteriormente (Informado) se evaluó correctamente
*<p>
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: <p>
* 	- MTC-FT-001_HH_TABLET_PromOxxoImagenes_Dashboard_PromOxxoImages_modulo_existe
*<p>
* PREREQUISITOS: <p>
* 1. El usuario debe estar registrado en el sistema.<p>
* 2. Contar con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SYI.<p>
* 3. El usuario ha iniciado sesión correctamente.<p>
* 4. El usuario está en la pantalla "Panel de Control".
* <p>
* INSUMOS:
*   -Numero de empleado
*   -crTienda
*   -crPlaza
*   -pin 1111
* <p>
* @author Mario A. De Leon Rdz.
* @date 26/enero/2026
 */

public class ATC_FT_001_ModuloPromOxxoDeImagenesExiste extends BaseExecution {	
	
	AppiumUtil appiumUtil = null;
	private String appPackage;
	PostgreSQLOffline sqlOffline = null;
	String usernamePromOxxoQA = null;
	String passwordPromOxxoQA = null;
	String crTienda = null;
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_001_ModuloPromOxxoDeImagenesExiste_test(HashMap<String, String> data) throws Exception {
		appPackage = data.get("appPackage");
		String appActivity = data.get("appActivity");
		usernamePromOxxoQA = data.get("usernamePromOxxoQA");
		passwordPromOxxoQA = PasswordUtil.decryptPassword(data.get("passwordPromOxxoQA"));
		crTienda = data.get("crTienda");
		String crPlaza = data.get("crPlaza");
		String numEmpleado = data.get("numEmpleado");
		//Variables y Configuración (Capabilities)----------------------------------------------------------------------

		String url = GlobalVariables.urlMep;

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		//Deshabilitar autollenado
		cap.setCapability("appium:disableAutofill", true);
		cap.setCapability("appium:autoGrantPermissions", true);
		// Deshabilitar teclado automático
		cap.setCapability("unicodeKeyboard", true);
		cap.setCapability("resetKeyboard", true);

		//Utilerias e Inicialización del Driver
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}
		// Inicialización de Page Objects (Vistas)
		UtilOffline utilOffline = new UtilOffline(testCase, appiumUtil, this);
		VistaOAuth0Offline oAuthOff = new VistaOAuth0Offline(testCase, appiumUtil, this);
		
		VistaIdentificacionTienda identifTienda = new VistaIdentificacionTienda(testCase, appiumUtil, this);
		VistaPanelDeControl vistaPanelDeControl = new VistaPanelDeControl(testCase, appiumUtil, this);
		
		//PRERREQUISITOS --------------------------------------------------------------------------------------
		utilOffline.setConectividadSimpleWifi(true);
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);
		
		//3. El usuario ha iniciado sesión correctamente.
		identifTienda.LoginApp(crPlaza, crTienda);
		oAuthOff.loginUser(numEmpleado);
		//4.El usuario está en la pantalla "Panel de Control".
		vistaPanelDeControl.clickBtnCerrarModalPromOxxoImagenesSinEvOpc();
		
		//PASO 1 ---------------------------------------------------------------------------------------------
		addStep("Desplácese hasta el módulo PromOXXO");
		vistaPanelDeControl.validarOpcionPromOxxoImagenes();
	}
	
	@AfterMethod
	public void afterMethod() {
		try {
			if (appiumUtil != null && appiumUtil.driver != null) {
				appiumUtil.driver.quit();
				System.out.println("Fin del test, se cierra app con afterMethod()");
			}
		} catch (Exception e) {
			System.err.println("Error en AfterMethod: " + e.getMessage());
			e.printStackTrace();
		}
	}	
	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ModuloPromOxxoDeImagenesExiste";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que la opción \"PromOXXO de Imágenes\" se muestre en el módulo \"PromOXXO\"";
	}

	@Override
	public String setTestDesigner() {
		return "Mario A. De León Rdz.";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. El usuario debe estar registrado en el sistema. "
				+ "2. Contar con los códigos Cr de la tienda: CrPlace: 10UMI, CrStore: 50GZA."
				+ "3. El usuario ha iniciado sesión correctamente. "
				+ "4. El usuario está en la pantalla \"Panel de Control\".";
		}

	@Override
	public void beforeTest() {
	}
}

