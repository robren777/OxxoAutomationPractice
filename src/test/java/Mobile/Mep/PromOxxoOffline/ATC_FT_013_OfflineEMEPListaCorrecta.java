package Mobile.Mep.PromOxxoOffline;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

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
 * DESCRIPCIÓN: Verifique que la lista de grupos de espacios promocionales sea la misma que la del inicio de sesión anterior del usuario
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC-FT-013_HH_TABLET_OfflineMode_EMEP_lista_correcta_mostrada
 *
 * PREREQUISITOS:
 * - Appium Server iniciado, Dispositivo conectado y ADB configurado.
 * - El usuario inició sesión previamente con los códigos Cr de la tienda: CrPlace: 10UMI, CrStore: 50GZA 
 * - Internet deshabilitado (se realiza la inicio del script)
 * - El usuario recuerda qué grupos de espacios promocionales y su progreso era antes de desconectarse
 * - El usuario se encuentra en la pantalla "Evaluación MEP"
 *
 * INSUMOS:
 * - Datos de dispositivo (deviceName, platformVersion, etc.)
 * - Credenciales de tienda (crPlaza, crTienda, numEmpleado)
 *
 * @author Mario A. De León Rdz.
 * @date 19/12/2025
 */
public class ATC_FT_013_OfflineEMEPListaCorrecta extends BaseExecution {

	AppiumUtil appiumUtil = null;
	private String appPackage;

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_013_OfflineEMEPListaCorrecta_test(HashMap<String, String> data) throws Exception {

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
		utilOffline.setConectividadSimple(true);
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);

		//Para llegar a Evaluacion MEP:
		idTiendaOff.loginAppSinEv(data.get("crPlaza"), tienda);
		oAuthOff.loginUser(data.get("numEmpleado"));
		dashboardOffline.validarVistaPrincipalSinEv();
		dashboardOffline.clickOpcionEvaluacionMepSinEv();
		utilOffline.validarVisibilidadDeTextoSinEv("Evaluación MEP");
		//Para validar:
		utilOffline.setConectividadSimple(false);
		utilOffline.validarVisibilidadDeTextoSinEv("modo sin conexión");
		List<String> gruposSesionPrevia = evaluacionMepOffline.guardarGruposPromocionales();
		utilOffline.setConectividadSimple(true);
//		CERRAR APP 
		appiumUtil.driver.quit();
//		INGRESAR A LA APP DE NUEVO Y EN EL DASHBOARD DESCONECTAR INTERNET
		//Iniciar driver nuevamente
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}
		// RECREAR utilidades y vistas
		utilOffline = new UtilOffline(testCase, appiumUtil, this);
		idTiendaOff = new VistaIdentificacionTiendaOffline(testCase, appiumUtil, this);
		oAuthOff = new VistaOAuth0Offline(testCase, appiumUtil, this);
		dashboardOffline = new VistaPanelDeControlOffline(testCase, appiumUtil, this);
		evaluacionMepOffline = new VistaEvaluacionMEPOffline(testCase, appiumUtil, this);
		// INICIAR APP
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);
		utilOffline.validarVisibilidadDeTextoSinEv("Identifica tu tienda");
		idTiendaOff.loginAppSinEv(data.get("crPlaza"), tienda);
		oAuthOff.loginUser(data.get("numEmpleado"));
		dashboardOffline.validarVistaPrincipalSinEv();
		dashboardOffline.clickOpcionEvaluacionMepSinEv();
		utilOffline.validarVisibilidadDeTextoSinEv("Evaluación MEP");
		//Para validar:
		utilOffline.setConectividadSimple(false);
		utilOffline.validarVisibilidadDeTextoSinEv("modo sin conexión");
		//PASO 1 ---------------------------------------------------------------------------------------------
		addStep("Preste atención a los Grupos de Espacio Promocional");
		evaluacionMepOffline.validarGruposPromocionalesDeSesionPrevia(gruposSesionPrevia);
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
		return "ATC_FT_013_OfflineEMEPListaCorrecta_test";
	}

	@Override
	public String setTestDescription() {
		return "Verifique que la pantalla \"Cámara\" se abra cuando el usuario haga clic en el botón \"Tomar Foto\" para "
				+ "cualquiera de los grupos de espacios promocionales";
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
				+ " - Internet deshabilitado (se realiza la inicio del script)\r\n"
				+ " - El usuario recuerda qué grupos de espacios promocionales y su progreso era antes de desconectarse\r\n"
				+ " - El usuario se encuentra en la pantalla \"Evaluación MEP\"";
	}
}