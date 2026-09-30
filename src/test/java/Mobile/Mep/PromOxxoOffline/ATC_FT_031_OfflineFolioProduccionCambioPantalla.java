package Mobile.Mep.PromOxxoOffline;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import Mobile.Mep.PromOxxoOffline.om.PostgreSQLOffline;
import Mobile.Mep.PromOxxoOffline.om.UtilOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaEvaluacionMEPOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaIdentificacionTiendaOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaOAuth0Offline;
import Mobile.Mep.PromOxxoOffline.om.VistaPanelDeControlOffline;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import Mobile.util.GlobalVariables;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * DESCRIPCIÓN:Verifique que la pantalla "Folio de producción" cambie a la versión sin conexión cuando Internet se desactive después de que el usuario ingrese a esta pantalla
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-031_HH_TABLET_Modo sin conexión_FolioDeProduccion_cambios_de_pantalla
 *
 * PREREQUISITOS:
 * - Appium Server iniciado, Dispositivo conectado y ADB configurado.
 * - El usuario inició sesión previamente con los códigos Cr de la tienda: CrPlace: 10UMI, CrStore: 50GZA 
 * - Internet habilitado (se realiza la inicio del script)
 * - El usuario se encuentra en la pantalla "Galería de fotos" en línea.
 *
 * INSUMOS:
 * - Datos de dispositivo (deviceName, platformVersion, etc.)
 * - Credenciales de tienda (crPlaza, crTienda, numEmpleado)
 *
 * @author Mario A. De León Rdz.
 * @date 19/12/2025
 */
public class ATC_FT_031_OfflineFolioProduccionCambioPantalla extends BaseExecution {

	AppiumUtil appiumUtil = null;
	private String appPackage;

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_031_OfflineFolioProduccionCambioPantalla_test(HashMap<String, String> data) throws Exception {

		testCase.setProject_Name("Mobile Mep --> PromOxxo Offline");

		appPackage = data.get("appPackage");
		String appActivity = data.get("appActivity");
		String usernamePromOxxoQA = data.get("usernamePromOxxoQA");
		String passwordPromOxxoQA = PasswordUtil.decryptPassword(data.get("passwordPromOxxoQA"));
		String tienda = data.get("crTienda");
		
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
		VistaIdentificacionTiendaOffline idTiendaOff = new VistaIdentificacionTiendaOffline(testCase, appiumUtil, this);
		VistaOAuth0Offline oAuthOff = new VistaOAuth0Offline(testCase, appiumUtil, this);
		VistaPanelDeControlOffline dashboardOffline = new VistaPanelDeControlOffline(testCase, appiumUtil, this);
		VistaEvaluacionMEPOffline evaluacionMepOffline = new VistaEvaluacionMEPOffline(testCase, appiumUtil, this);
		PostgreSQLOffline sqlOffline = new PostgreSQLOffline(testCase, this);
		
		//PRERREQUISITOS --------------------------------------------------------------------------------------
		//Hacer insert de un pop en public.promo_labels y en public.promo_label_statuses pero borrar antes el insert en caso de que ya existiera antes.
		sqlOffline.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, tienda);
		sqlOffline.sqlInsertPOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, tienda);
		//2. Internet está habilitado.
		utilOffline.setConectividadSimpleWifi(true);
		utilOffline.setConectividadSimpleDatos(false);
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);

		//1. El usuario inició sesión previamente
		idTiendaOff.loginAppSinEv(data.get("crPlaza"), tienda);
		oAuthOff.loginUser(data.get("numEmpleado"));
		dashboardOffline.validarVistaPrincipalSinEv();

		//3. El usuario se encuentra en la pantalla "Galería de fotos" en línea.
		dashboardOffline.clickOpcionEvaluacionMepSinEv();
		evaluacionMepOffline.clickBtnTomarFotoPrimerPopSinEv();
		evaluacionMepOffline.clickIconoTomarUnaFotografiaSinEv();
		evaluacionMepOffline.clickBtnFlechaSiguienteSinEv();
		//PASO 1 ---------------------------------------------------------------------------------------------
		addStep("Haga clic en el botón \"Continuar\"");
		evaluacionMepOffline.clickBtnContinuar();
		//PASO 2 ---------------------------------------------------------------------------------------------
		addStep("Desactivar Internet");
		utilOffline.setConectividadSimpleWifiConEv(false);
		dashboardOffline.validarDashboardOfflineSinEv();
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
		return "ATC_FT_031_OfflineFolioProduccionCambioPantalla_test";
	}

	@Override
	public String setTestDescription() {
		return "Verifique que la pantalla \"Folio de producción\" cambie a la versión sin conexión cuando Internet se desactive después de que el usuario ingrese a esta pantalla";
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
		return "- Appium Server iniciado, Dispositivo conectado y ADB configurado.\r\n"
				+ " - El usuario inició sesión previamente con los códigos Cr de la tienda: CrPlace: 10UMI, CrStore: 50GZA \r\n"
				+ " - Internet habilitado (se realiza la inicio del script)\r\n"
				+ " - El usuario está en la pantalla \"Galería de fotos\" en línea";
	}
}