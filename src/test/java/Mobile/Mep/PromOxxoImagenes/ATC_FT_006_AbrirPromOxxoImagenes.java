package Mobile.Mep.PromOxxoImagenes;

import Mobile.Mep.PromOxxoOffline.om.PostgreSQLOffline;
import Mobile.Mep.PromOxxoOffline.om.UtilOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaEvaluacionMEPOffline;
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
 * DESCRIPCIÓN:<p> Verificar que el flujo "PromOXXO de Imágenes" se abra cuando el usuario haga clic en el botón "Ir a PromOXXO"
 *<p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: <p>
 * 	- HH_TABLET_PromOxxoImagenes_Panel_emergente_abre_flujo_de_promoxxoimagenes
 *<p>
 * PREREQUISITOS: <p>
 * 1. El usuario debe estar registrado en el sistema.<p>
 * 2. Contar con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SHC.<p>
 * 3. El usuario ha iniciado sesión correctamente.<p>
 * 4. El usuario está en la pantalla "Panel de Control".<p>
 * 5. Se muestra la ventana emergente "Nuevo Periodo Promocional Disponible".<p>
 * <p>
 * INSUMOS:
 *   - Numero de empleado
 *   - crTienda
 *   - crPlaza
 *   - pin 1111
 *   - App Versión QA v1.8.2(25)
 * <p>
 * @author Mario A. De Leon Rdz.
 * @date 10/Febrero/2026
 */

public class ATC_FT_006_AbrirPromOxxoImagenes extends BaseExecution {	
	
	AppiumUtil appiumUtil = null;
	private String appPackage;
	PostgreSQLOffline sqlOffline = null;
	String usernamePromOxxoQA = null;
	String passwordPromOxxoQA = null;
	String crTienda = null;
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_005_PanelEmergenteCerrado_test(HashMap<String, String> data) throws Exception {
		appPackage = data.get("appPackage");
		String appActivity = data.get("appActivity");
		usernamePromOxxoQA = data.get("usernamePromOxxoQA");
		passwordPromOxxoQA = PasswordUtil.decryptPassword(data.get("passwordPromOxxoQA"));
		String periodId = data.get("periodId");
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
		VistaOAuth0Offline oAuthOff = new VistaOAuth0Offline(testCase, appiumUtil, this);
		PostgreSQLOffline sqlOffline = new PostgreSQLOffline(testCase, this);
		
		VistaIdentificacionTienda identifTienda = new VistaIdentificacionTienda(testCase, appiumUtil, this);
		VistaPanelDeControl vistaPanelDeControl = new VistaPanelDeControl(testCase, appiumUtil, this);
		
		//PRERREQUISITOS --------------------------------------------------------------------------------------
		sqlOffline.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-1");
		sqlOffline.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-2");
		System.out.println(periodId);
		sqlOffline.sqlInsertPOPCabeceraYExhibidorSinEv(usernamePromOxxoQA, passwordPromOxxoQA, periodId, crTienda);
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);
		identifTienda.LoginApp(crPlaza, crTienda);
		oAuthOff.loginUser(numEmpleado);
		vistaPanelDeControl.clickBtnCerrarModalPromOxxoImagenesSinEvOpc();
		//PASO 1 ---------------------------------------------------------------------------------------------
		addStep("Haga clic en el botón \"Ir a PromOXXO\"");
		vistaPanelDeControl.clickOpcionPromOxxoImagenes();
	}
	
	@AfterMethod
	public void afterMethod() {
		try {
			if (appiumUtil != null && appiumUtil.driver != null) {
				PostgreSQLOffline sqlCleanup = new PostgreSQLOffline(testCase, this);
				sqlCleanup.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-1");
				sqlCleanup.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-2");
				System.out.println("POPs eliminados correctamente");
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
        return "ATC_FT_006_AbrirPromOxxoImagenes";
    }

    @Override
    public String setTestDescription() {
        return "Verifique que el flujo \"PromOXXO de Imágenes\" se abra cuando el usuario haga clic en el botón \"Ir a PromOXXO\" en la ventana emergente \"Nuevo Periodo Promocional Disponible\"";
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
        return "1. El usuario inicia sesión con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SHC "
                + "2. El usuario está en la pantalla \"Panel de control\" "
                + "3. Se muestra la ventana emergente \"Nuevo Periodo Promocional Disponible\"";
    }

    @Override
    public void beforeTest() {
    }
}

