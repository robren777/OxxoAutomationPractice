package Mobile.Mep.EvaluacionMep;

import Mobile.Mep.EvaluacionMep.om.VistaEvaluacionMep;
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
* DESCRIPCIÓN:<p> Verificar que la etiqueta informada anteriormente (Informado) se evaluó correctamente
*<p>
* NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: <p>
* 	- MTC-FT-MTC-FT-088_HH_TABLET_EMEP_Evaluar_30_Códigos_QR_con_diferentes_imágenes
*<p>
* PREREQUISITOS: <p>
* 1. El usuario debe estar registrado en el sistema.<p>
* 2. Contar con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SYI.<p>
* 3. Hay un período promocional activo (de N-1 a N+4).<p>
* 4. El usuario ha iniciado sesión correctamente.<p>
* 5. El usuario se encuentra en la pantalla "Evaluación MEP".<p>
* 6. Hay al menos un grupo de espacio promocional sin completar con al menos 30 códigos QR sin completar (p. ej., Cuarto Frío).<p>
* <p>
* INSUMOS:
*   -Numero de empleado
*   -crTienda
*   -crPlaza
*   -pin 1111
*   -App Versión QA v1.8.2(25)
* <p>
* @author Mario A. De Leon Rdz.
* @date 26/enero/2026
 */

public class ATC_FT_088_Evaluar30CodigosQR extends BaseExecution {	
	
	AppiumUtil appiumUtil = null;
	private String appPackage;
	String usernamePromOxxoQA = null;
	String passwordPromOxxoQA = null;
	String crTienda = null;
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_088_Evaluar30CodigosQR_test(HashMap<String, String> data) throws Exception {
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
		UtilOffline utilOffline = new UtilOffline(testCase, appiumUtil, this);
		PostgreSQLOffline sqlOffline = new PostgreSQLOffline(testCase, this);
		VistaOAuth0Offline oAuthOff = new VistaOAuth0Offline(testCase, appiumUtil, this);
		VistaEvaluacionMEPOffline evaluacionMepOffline = new VistaEvaluacionMEPOffline(testCase, appiumUtil, this);
		
		VistaIdentificacionTienda identifTienda = new VistaIdentificacionTienda(testCase, appiumUtil, this);
		VistaPanelDeControl vistaPanelDeControl = new VistaPanelDeControl(testCase, appiumUtil, this);
		VistaEvaluacionMep evaluacionMep = new VistaEvaluacionMep(testCase, appiumUtil, this);
		//Entorno virtual para la camara del emulador
		String directorioDestinoFolder = data.get("directorioDestinoFolder");
    	String pngCodigo1 = data.get("pngCodigo1");
    	utilPhotoEmulator photoEMEP = new utilPhotoEmulator();
    	photoEMEP.CopiarMoverRenombrar(pngCodigo1, directorioDestinoFolder);
		
		//PRERREQUISITOS --------------------------------------------------------------------------------------
		utilOffline.setConectividadSimpleWifi(true);
		sqlOffline.sqlDeleteTreintaPOPsSincronoSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda);
		sqlOffline.sqlInsertTreintaPOPsCabecerasSincronoSinEv(usernamePromOxxoQA, passwordPromOxxoQA, periodId, crTienda);
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);
		
		//1. El usuario debe estar registrado en el sistema.
		identifTienda.LoginApp(crPlaza, crTienda);
		oAuthOff.loginUser(numEmpleado);
		vistaPanelDeControl.clickBtnCerrarModalPromOxxoImagenesSinEv();
		
		//4. El usuario se encuentra en la pantalla "Evaluacion MEP"
		vistaPanelDeControl.validarOpcionPromOxxoEvaluacionMep();
		vistaPanelDeControl.clickOpcionEvaluacionMep();
		evaluacionMep.validarVistaEvaluacionMep();
		
		//PASO 1 ---------------------------------------------------------------------------------------------
		addStep("Haga clic en \"Tomar Foto\" para el grupo de espacios promocionales al menos 30 códigos QR sin completar (por ejemplo, Cuarto Frio)");
		evaluacionMepOffline.clickBtnTomarFotoPrimerPop();
		//PASO 2 ---------------------------------------------------------------------------------------------
		addStep("Tome una fotografía del código QR de la etiqueta completa");
		evaluacionMepOffline.clickIconoTomarUnaFotografia();
		//PASO 3 ---------------------------------------------------------------------------------------------
		addStep("Haga clic en el botón \"->\"");
		evaluacionMepOffline.clickBtnFlechaSiguiente();
		//PASO 4 ---------------------------------------------------------------------------------------------
		addStep("Haga clic en el botón \"Continuar\"");
		evaluacionMepOffline.clickBtnContinuar();
		//PASO 5 ---------------------------------------------------------------------------------------------
		addStep("Haga clic en el botón \"Finalizar\"");
		evaluacionMepOffline.clickBtnFinalizar();
		//PASO 6 ---------------------------------------------------------------------------------------------
		addStep("Consulte la etiqueta mencionada en el BE");
		utilOffline.validarVisibilidadDeTexto("completo");
		
	}
	
	@AfterMethod
	public void afterMethod() {
		try {
			if (appiumUtil != null && appiumUtil.driver != null) {
				PostgreSQLOffline sqlCleanup = new PostgreSQLOffline(testCase, this);
				sqlCleanup.sqlDeleteTreintaPOPsSincronoSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda);
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
		return "ATC_FT_088_Evaluar30CodigosQR";
	}

	@Override
	public String setTestDescription() {
		return "Comprueba que se evalúen correctamente 30 códigos QR a partir de diferentes imágenes";
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
				+ "6. Hay al menos un grupo de espacio promocional sin completar con al menos 30 códigos QR sin completar (p. ej., Cuarto Frío).";
		}

	@Override
	public void beforeTest() {
	}
}

