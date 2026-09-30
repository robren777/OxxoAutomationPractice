package Mobile.Mep.PromOxxoResultados;

import Mobile.Mep.EvaluacionMep.om.VistaEvaluacionMep;
import Mobile.Mep.PromOxxoOffline.om.*;
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
* DESCRIPCIÓN: Verifique que la pantalla de la cámara tenga botones dedicados para hacer zoom (solo para el grupo de espacio de promoción "Banners")
*<p>
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	- MTC-FT-090_HH_TABLET_EMEP_La_cámara_tiene_botones_de_zoom
*<p>
* PREREQUISITOS: <p>
* 1. El usuario debe estar registrado en el sistema.<p>
* 2. Contar con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SYI.<p>
* 3. Hay un período promocional activo (de N-1 a N+4).<p>
* 4. El usuario ha iniciado sesión correctamente.<p>
* 5. El usuario se encuentra en la pantalla "Evaluación MEP". 6. El usuario debe tener una parte de las etiquetas completadas, una parte reportada, una parte reportada con motivos "Otros"\"Sin exhibidormueble" y una parte no completada.<p>
* <p>
* INSUMOS:
*   -Numero de empleado
*   -crTienda
*   -crPlaza
*   -pin 1111
*   -APK promoxxo ALDIA 2.0 en ambiente de QA
* <p>
* @author Mario A. De Leon Rdz.
* @date 26/enero/2026
 */

public class ATC_FT_066_LaCamaraTieneBotonesDeZoom extends BaseExecution {
	
	AppiumUtil appiumUtil = null;
	private String appPackage;
	
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_090_LaCamaraTieneBotonesZoom_test(HashMap<String, String> data) throws Exception {
		appPackage = data.get("appPackage");
		String appActivity = data.get("appActivity");
		String usernamePromOxxoQA = data.get("usernamePromOxxoQA");
		String passwordPromOxxoQA = PasswordUtil.decryptPassword(data.get("passwordPromOxxoQA"));
		String periodId = data.get("periodId");
		String crTienda = data.get("crTienda");
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
		PostgreSQLOffline sqlOffline = new PostgreSQLOffline(testCase, this);
		VistaOAuth0Offline oAuthOff = new VistaOAuth0Offline(testCase, appiumUtil, this);
		VistaPanelDeControlOffline dashboardOffline = new VistaPanelDeControlOffline(testCase, appiumUtil, this);
		VistaEvaluacionMEPOffline evaluacionMepOffline = new VistaEvaluacionMEPOffline(testCase, appiumUtil, this);

		VistaIdentificacionTienda identifTienda = new VistaIdentificacionTienda(testCase, appiumUtil, this);
		VistaPanelDeControl vistaPanelDeControl = new VistaPanelDeControl(testCase, appiumUtil, this);
		VistaEvaluacionMep evaluacionMep = new VistaEvaluacionMep(testCase, appiumUtil, this);

		//PRERREQUISITOS --------------------------------------------------------------------------------------
		utilOffline.setConectividadSimpleWifi(true);
		sqlOffline.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-1");
		sqlOffline.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-2");
		sqlOffline.sqlInsertPOPBannerSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, periodId);
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);

		//1. El usuario inició sesión previamente
		identifTienda.LoginApp(crPlaza, crTienda);
		oAuthOff.loginUser(numEmpleado);
		dashboardOffline.validarVistaPrincipalSinEv();
		//3. El usuario se encuentra en la pantalla "Evaluacion MEP" offline
		vistaPanelDeControl.validarOpcionPromOxxoEvaluacionMep();
		vistaPanelDeControl.clickOpcionEvaluacionMep();
		evaluacionMep.validarVistaEvaluacionMep();
		
		//PASO 1 ---------------------------------------------------------------------------------------------
		addStep("Haga clic en \"Banners\" Grupo de espacio promocional");
		evaluacionMepOffline.clickBtnTomarFotoPrimerPop();
		utilOffline.validarVisibilidadDeTexto("1x");
		utilOffline.validarVisibilidadDeTexto("2x");
		utilOffline.validarVisibilidadDeTexto("3x");
	}
	
	@AfterMethod
	public void afterMethod() {
		try {
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
	public String setTestFullName() {
		return "ATC_FT_090_LaCamaraTieneBotonesZoom_test";
	}

	@Override
	public String setTestDescription() {
		return "Verifique que la pantalla de la cámara tenga botones dedicados para hacer zoom (solo para el grupo de espacio de promoción \"Banners\")";
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
				+ "3. Hay un período promocional activo (de N-1 a N+4)."
				+ "4. El usuario ha iniciado sesión correctamente. "
				+ "5. El usuario se encuentra en la pantalla \"Evaluación MEP\". ";
	}

	@Override
	public void beforeTest() {
	}
}

