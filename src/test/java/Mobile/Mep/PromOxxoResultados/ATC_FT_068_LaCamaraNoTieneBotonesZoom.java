package Mobile.Mep.PromOxxoResultados;

import Mobile.Mep.EvaluacionMep.om.VistaEvaluacionMep;
import Mobile.Mep.PromOxxoOffline.om.*;
import Mobile.Mep.om.VistaIdentificacionTienda;
import Mobile.Mep.om.VistaPanelDeControl;
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
* DESCRIPCIÓN: Comprueba que la pantalla "Cámara" no tenga posibilidad de Zoom (excepto en el grupo de espacio promocional "Banners")
*<p>
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: 
* 	- MTC-FT-068_HH_TABLET_RESULTADOS_La_cámara_no_tiene_botones_de_zoom
*<p>
* PREREQUISITOS: <p>
*	1. El usuario debe estar registrado en el sistema. 
*	2. Contar con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SYI. 
*	3. Hay un período promocional activo. 
*	4. El usuario ha iniciado sesión correctamente. 
*	5. El usuario está en la pantalla "Resultado Evaluación MEP". 
*	6. El grupo del espacio promocional (Comida Rápida) tiene folios completados/reportados/incompletos.<p>
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

public class ATC_FT_068_LaCamaraNoTieneBotonesZoom extends BaseExecution {
	
	AppiumUtil appiumUtil = null;
	private String appPackage;
	
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_068_LaCamaraNoTieneBotonesZoom_test(HashMap<String, String> data) throws Exception {
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
		sqlOffline.sqlInsertDosPOPsCabecerasSinEv(usernamePromOxxoQA, passwordPromOxxoQA, periodId, crTienda);
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
		addStep("Haga clic en cualquier grupo de espacios promocionales (excepto \"Banners\", por ejemplo \"Comida rápida\")");
		evaluacionMepOffline.clickBtnTomarFotoPrimerPop();
		utilOffline.esperarHastaQueNoSeaVisibleTexto("1x");
		utilOffline.esperarHastaQueNoSeaVisibleTexto("2x");
		utilOffline.esperarHastaQueNoSeaVisibleTexto("3x");
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
		return "ATC_FT_068_LaCamaraNoTieneBotonesZoom";
	}

	@Override
	public String setTestDescription() {
		return "Comprueba que la pantalla \"Cámara\" no tenga posibilidad de Zoom (excepto en el grupo de espacio promocional \"Banners\")";
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
		return "1. El usuario debe estar registrado en el sistema. 2. Contar con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SYI. 3. Hay un período promocional activo. 4. El usuario ha iniciado sesión correctamente. 5. El usuario está en la pantalla \"Resultado Evaluación MEP\". 6. El grupo del espacio promocional (Comida Rápida) tiene folios completados/reportados/incompletos.";
	}

	@Override
	public void beforeTest() {
	}
}

