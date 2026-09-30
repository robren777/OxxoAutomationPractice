package Mobile.Mep.PromOxxoOffline;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod; // Agregado para mantener estructura
import org.testng.annotations.Test;

import Mobile.Mep.PromOxxoOffline.om.InicioAppOffline;
import Mobile.Mep.PromOxxoOffline.om.UtilOffline;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import Mobile.util.GlobalVariables;
import utils.AppiumUtil;

/**
 * DESCRIPCIÓN: Comprueba que el botón "Abrir modo sin conexión" no se muestra cuando un usuario que no ha iniciado sesión previamente abre la aplicación
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-004_HH_TABLET_Modo sin conexión_Inicio de sesión_Usuario no conectado_Verificar_pantalla inicial_con_1_botón
 *
 * PREREQUISITOS:
 * - Appium Server iniciado, Dispositivo conectado y ADB configurado.
 * - El usuario NO había iniciado sesión previamente 
 * - Internet está deshabilitado
 *
 * INSUMOS:
 * - Datos de dispositivo (deviceName, platformVersion, etc.)
 * - Credenciales de tienda (crPlaza, crTienda, numEmpleado)
 *
 * @author Mario A. De León Rdz.
 * @date 19/12/2025
 */
public class ATC_FT_004_OfflineUsuarioNoConectado1Boton extends BaseExecution {

	AppiumUtil appiumUtil = null;
	private String appPackage;

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_004_OfflineUsuarioNoConectado1Boton_test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Mobile Mep --> PromOxxo Offline");

		appPackage = data.get("appPackage");
		String appActivity = data.get("appActivity");
		
		//Variables y Configuración (Capabilities)----------------------------------------------------------------------

		String url = GlobalVariables.urlMep;

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
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
		InicioAppOffline inicioOffline = new InicioAppOffline(testCase, appiumUtil, this);
		UtilOffline utilOffline = new UtilOffline(testCase, appiumUtil, this);
		//PRERREQUISITOS --------------------------------------------------------------------------------------
		utilOffline.setConectividadSimple(false);
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);

		//PASO 1 ---------------------------------------------------------------------------------------------
		addStep("Iniciar la aplicación");
		assertTrue(inicioOffline.validarModoOfflineInicio(), "No se mostró correctamente el modo offline");
		assertFalse(inicioOffline.validarBotonAbrirSinConexión(), "Se muestra botón Abrir modo sin conexión");
	}

	@AfterMethod
	public void afterMethod() {
		UtilOffline utilOffline = new UtilOffline(testCase, appiumUtil, this);
		try {
			utilOffline.setConectividadSimple(true);
			if (appiumUtil != null && appiumUtil.driver != null && appPackage != null) {
				appiumUtil.driver.quit();
				System.out.println("Fin del test, se cierra app con afterMethod()");
			}
		} catch (Exception e) {
			System.err.println("Error en AfterMethod: " + e.getMessage());
			e.printStackTrace();
		}
	}
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_OfflineUsuarioNoConectado1Boton_test";
	}

	@Override
	public String setTestDescription() {
		return "Comprueba que el botón \"Abrir modo sin conexión\" no se muestra cuando un usuario que no ha iniciado sesión previamente abre la aplicación";
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
		return "Appium Server iniciado, Dispositivo conectado y ADB configurado.\n"
				+ "El usuario NO había iniciado sesión previamente \r\n"
				+ "Internet está deshabilitado";
	}
}