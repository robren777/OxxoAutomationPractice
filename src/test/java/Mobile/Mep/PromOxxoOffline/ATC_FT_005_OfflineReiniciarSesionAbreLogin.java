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
import Mobile.Mep.PromOxxoOffline.om.VistaIdentificacionTiendaOffline;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import Mobile.util.GlobalVariables;
import utils.AppiumUtil;

/**
 * DESCRIPCIÓN: Verificar que el login online se abra cuando el usuario haga clic en el botón "Actualizar"
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-005_HH_TABLET_Modo sin conexión_Iniciar_reinicio_de_sesión_Se_abre_la_página_de_inicio_de_sesión
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
public class ATC_FT_005_OfflineReiniciarSesionAbreLogin extends BaseExecution {

	AppiumUtil appiumUtil = null;
	private String appPackage;

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_005_OfflineReiniciarSesionAbreLogin_test(HashMap<String, String> data) throws Exception {

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
		VistaIdentificacionTiendaOffline idTiendaOff = new VistaIdentificacionTiendaOffline(testCase, appiumUtil, this);
		
		//PRERREQUISITOS --------------------------------------------------------------------------------------
		utilOffline.setConectividadSimple(false);
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);

		//PASO 1 ---------------------------------------------------------------------------------------------
		addStep("Iniciar la aplicación");
		assertTrue(inicioOffline.validarModoOfflineInicio(), "No se mostró correctamente el modo offline");
		//PASO 2 ---------------------------------------------------------------------------------------------
		addStep("Habilitar internet");
		utilOffline.setConectividadSimpleConEv(true);
		Thread.sleep(3000);
		//PASO 3 ---------------------------------------------------------------------------------------------
		addStep("Haga clic en el botón \"Actualizar\"");
		inicioOffline.clickActualizarOffline();
		idTiendaOff.validarVistaIdentificaTuTienda();
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
		return "ATC_FT_005_OfflineReiniciarSesionAbreLogin_test";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que el login online se abra cuando el usuario haga clic en el botón \"Actualizar\"";
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