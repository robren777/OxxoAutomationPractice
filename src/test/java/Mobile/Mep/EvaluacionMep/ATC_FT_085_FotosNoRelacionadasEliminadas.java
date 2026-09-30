package Mobile.Mep.EvaluacionMep;

import Mobile.Mep.EvaluacionMep.om.VistaEvaluacionMep;
import Mobile.Mep.PromOxxoOffline.om.PostgreSQLOffline;
import Mobile.Mep.PromOxxoOffline.om.UtilOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaEvaluacionMEPOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaOAuth0Offline;
import Mobile.Mep.PromOxxoOffline.om.VistaPanelDeControlOffline;
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
* DESCRIPCIÓN:<p> Comprueba que la foto se elimina de la Galería de fotos cuando el usuario toma una foto del código Aruco que está relacionado con otro grupo de espacio promocional
*<p>
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: <p>
* 	- MTC-FT-085_HH_TABLET_EMEP_Galería de fotos_Fotos no relacionadas_eliminadas
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

public class ATC_FT_085_FotosNoRelacionadasEliminadas extends BaseExecution {	
	
	AppiumUtil appiumUtil = null;
	private String appPackage;
	PostgreSQLOffline sqlOffline = null;
	String usernamePromOxxoQA = null;
	String passwordPromOxxoQA = null;
	String crTienda = null;
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_085_FotosNoRelacionadasEliminadas_test(HashMap<String, String> data) throws Exception {
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
		PostgreSQLOffline sqlOffline = new PostgreSQLOffline(testCase, this);
		VistaOAuth0Offline oAuthOff = new VistaOAuth0Offline(testCase, appiumUtil, this);
		VistaPanelDeControlOffline dashboardOffline = new VistaPanelDeControlOffline(testCase, appiumUtil, this);
		VistaEvaluacionMEPOffline evaluacionMepOffline = new VistaEvaluacionMEPOffline(testCase, appiumUtil, this);
		
		VistaIdentificacionTienda identifTienda = new VistaIdentificacionTienda(testCase, appiumUtil, this);
		VistaPanelDeControl vistaPanelDeControl = new VistaPanelDeControl(testCase, appiumUtil, this);
		VistaEvaluacionMep evaluacionMep = new VistaEvaluacionMep(testCase, appiumUtil, this);
		
		//Entorno virtual para la camara del emulador
		String directorioDestinoFolder = data.get("directorioDestinoFolder");
    	String nameOriginalFilePng = data.get("nameOriginalFilePng");
        	
    	utilPhotoEmulator photoEMEP = new utilPhotoEmulator();
    	photoEMEP.CopiarMoverRenombrar(nameOriginalFilePng, directorioDestinoFolder);
		
		//PRERREQUISITOS --------------------------------------------------------------------------------------
		utilOffline.setConectividadSimpleWifi(true);
		sqlOffline.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-1");
		sqlOffline.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-2");
		sqlOffline.sqlInsertPOPCabeceraYExhibidorSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda);
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);
		
		//1. El usuario debe estar registrado en el sistema.
		identifTienda.LoginApp(crPlaza, crTienda);
		oAuthOff.loginUser(numEmpleado);
		dashboardOffline.validarVistaPrincipalSinEv();
		//4. El usuario se encuentra en la pantalla "Evaluacion MEP"
		vistaPanelDeControl.validarOpcionPromOxxoEvaluacionMep();
		vistaPanelDeControl.clickOpcionEvaluacionMep();
		evaluacionMep.validarVistaEvaluacionMep();
		
		//PASO 1 ---------------------------------------------------------------------------------------------
		addStep("Haz clic en \"Tomar Foto\" para el grupo de espacios promocionales con códigos QR (ej. Cuarto Frio)");
		evaluacionMepOffline.clickBtnTomarFotoPrimerPop();
		//PASO 2 ---------------------------------------------------------------------------------------------
		addStep("Tome una foto del código QR relacionado con otro grupo de espacio promocional (por ejemplo, Salchikoxka)");
		evaluacionMepOffline.clickIconoTomarUnaFotografia();
		//PASO 3 ---------------------------------------------------------------------------------------------
		addStep("Haga clic en el botón \"->\"");
		evaluacionMepOffline.clickBtnFlechaSiguiente();
		//PASO 4 ---------------------------------------------------------------------------------------------
		addStep("Haga clic en el botón \"Continuar\"");
		evaluacionMepOffline.clickBtnContinuar();
		evaluacionMep.validarModalErrorFaltanCodigos();
		
	}
	
	@AfterMethod
	public void afterMethod() {
		try {
			if (appiumUtil != null && appiumUtil.driver != null) {
				PostgreSQLOffline sqlCleanup = new PostgreSQLOffline(testCase, this);
				sqlCleanup.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-1");
				sqlCleanup.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-2");
				System.out.println("POPs eliminados correctamente");
				System.out.println(crTienda);
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
		return "ATC_FT_085_FotosNoRelacionadasEliminadas";
	}

	@Override
	public String setTestDescription() {
		return "Comprueba que la foto se elimina de la Galería de fotos cuando el usuario toma una foto del código Aruco que "
				+ "está relacionado con otro grupo de espacio promocional";
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
				+ "5. El usuario se encuentra en la pantalla \"Evaluación MEP\". "
				+ "6. Hay al menos dos grupos de espacios promocionales sin completar con códigos QR (por ejemplo, Cuarto Frio y Salchikoxka).";
		}

	@Override
	public void beforeTest() {
	}
}

