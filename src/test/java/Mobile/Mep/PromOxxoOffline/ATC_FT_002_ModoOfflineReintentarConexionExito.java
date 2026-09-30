package Mobile.Mep.PromOxxoOffline;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod; // Agregado para mantener estructura
import org.testng.annotations.Test;

import Mobile.Mep.PromOxxoOffline.om.InicioAppOffline;
import Mobile.Mep.PromOxxoOffline.om.UtilOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaIdentificacionTiendaOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaOAuth0Offline;
import Mobile.Mep.PromOxxoOffline.om.VistaPanelDeControlOffline;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import Mobile.util.GlobalVariables;
import utils.AppiumUtil;

/**
 * DESCRIPCIÓN: Verificar que se muestre la pantalla "No hay conexión a internet" cuando un usuario previamente logueado sin conexión a internet ingresa a la aplicación
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-002_HH_TABLET_Modo sin conexión_Inicio_de_sesión_Comprobación_Reintento_conexión_éxito
 *
 * PREREQUISITOS:
 * - Appium Server iniciado, Dispositivo conectado y ADB configurado.
 * - El usuario inició sesión previamente con los códigos Cr de la tienda: CrPlace: 10UMI, CrStore: 50GZA 
 * - Internet deshabilitado (se realiza la inicio del script)
 * - El usuario se encuentra en la pantalla "No hay conexión a internet" (se realiza la inicio del script)
 *
 * INSUMOS:
 * - Datos de dispositivo (deviceName, platformVersion, etc.)
 * - Credenciales de tienda (crPlaza, crTienda, numEmpleado)
 *
 * @author Mario A. De León Rdz.
 * @date 18/12/2025
 */
public class ATC_FT_002_ModoOfflineReintentarConexionExito extends BaseExecution {

	AppiumUtil appiumUtil = null;
	private String appPackage;

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_002_ModoOfflineReintentarConexionExito_test(HashMap<String, String> data) throws Exception {
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
		InicioAppOffline inicioOffline = new InicioAppOffline(testCase, appiumUtil, this);
		UtilOffline utilOffline = new UtilOffline(testCase, appiumUtil, this);
		VistaIdentificacionTiendaOffline idTiendaOff = new VistaIdentificacionTiendaOffline(testCase, appiumUtil, this);
		VistaOAuth0Offline oAuthOff = new VistaOAuth0Offline(testCase, appiumUtil, this);
		VistaPanelDeControlOffline dashboardOffline = new VistaPanelDeControlOffline(testCase, appiumUtil, this);
		//PRERREQUISITOS --------------------------------------------------------------------------------------
		utilOffline.setConectividadSimple(true);
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);
		idTiendaOff.loginAppSinEv(data.get("crPlaza"), data.get("crTienda"));
		oAuthOff.loginUser(data.get("numEmpleado"));
		utilOffline.setConectividadSimple(false);
		appiumUtil.driver.terminateApp(appPackage);
		appiumUtil.driver.activateApp(appPackage);
		//PASO 1 ---------------------------------------------------------------------------------------------
		addStep("Habilitar Internet");
		assertTrue(inicioOffline.validarModoOfflineInicio(), "No se mostró correctamente el modo offline");
		utilOffline.setConectividadSimpleConEv(true);
		
		//PASO 2 ---------------------------------------------------------------------------------------------
		addStep("Haga clic en el botón 'Actualizar'");
		inicioOffline.clickActualizarOffline();
		dashboardOffline.validarVistaPrincipal();
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
		return "ATC_FT_002_ModoOfflineReintentarConexionExito_test";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que se muestre la pantalla \"No hay conexión a internet\" cuando un usuario previamente logueado sin conexión a internet ingresa a la aplicación";
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
				+ "El usuario inició sesión previamente con los códigos Cr de la tienda: CrPlace: 10UMI, CrStore: 50GZA \n"
				+ "Internet está deshabilitado \n"
				+ "El usuario se encuentra en la pantalla \"No hay conexión a internet\"";
	}
}