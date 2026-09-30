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
* 	- MTC-FT-087_HH_TABLET_EMEP_Etiqueta_completada_estado_no_cambiado
*<p>
* PREREQUISITOS: <p>
* 1. El usuario debe estar registrado en el sistema.<p>
* 2. Contar con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SYI.<p>
* 3. Hay un período promocional activo (de N-1 a N+4).<p>
* 4. El usuario ha iniciado sesión correctamente.<p>
* 5. El usuario se encuentra en la pantalla "Evaluación MEP".<p>
* 6. Hay al menos 1 grupo de espacio promocional sin completar con códigos QR (por ejemplo, Cuarto Frio).<p> 
* 7. Se informó al menos 1 de las etiquetas para el grupo de espacio promocional.
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

public class ATC_FT_087_EtiquetaCompletadaEstadoNoCambiado extends BaseExecution {	
	
	AppiumUtil appiumUtil = null;
	private String appPackage;
	PostgreSQLOffline sqlOffline = null;
	String usernamePromOxxoQA = null;
	String passwordPromOxxoQA = null;
	String crTienda = null;
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_087_EtiquetaCompletadaEstadoNoCambiado_test(HashMap<String, String> data) throws Exception {
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
    	String pngCodigo2 = data.get("pngCodigo2");
    	utilPhotoEmulator photoEMEP = new utilPhotoEmulator();
    	photoEMEP.CopiarMoverRenombrar(pngCodigo1, directorioDestinoFolder);
		
		//PRERREQUISITOS --------------------------------------------------------------------------------------
		utilOffline.setConectividadSimpleWifi(true);
		sqlOffline.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-1");
		sqlOffline.sqlDeletePOPSinEv(usernamePromOxxoQA, passwordPromOxxoQA, crTienda, "P13-TEST-2");
		System.out.println(periodId);
		sqlOffline.sqlInsertDosPOPsCabecerasSinEv(usernamePromOxxoQA, passwordPromOxxoQA, periodId, crTienda);
		appiumUtil.driver.activateApp(appPackage+"/"+appActivity);
		
		//1. El usuario debe estar registrado en el sistema.
		identifTienda.LoginApp(crPlaza, crTienda);
		oAuthOff.loginUser(numEmpleado);
		vistaPanelDeControl.clickBtnCerrarModalPromOxxoImagenesSinEv();
		
		//4. El usuario se encuentra en la pantalla "Evaluacion MEP"
		vistaPanelDeControl.validarOpcionPromOxxoEvaluacionMep();
		vistaPanelDeControl.clickOpcionEvaluacionMep();
		evaluacionMep.validarVistaEvaluacionMep();
		
		//7. Al menos una de las etiquetas del grupo de espacios promocionales se completó mediante el progreso de guardado.
		evaluacionMepOffline.clickBtnTomarFotoPrimerPopSinEv();
		evaluacionMepOffline.clickIconoTomarUnaFotografiaSinEv();
		evaluacionMepOffline.clickBtnFlechaSiguienteSinEv();
		evaluacionMepOffline.clickBtnContinuarSinEv();
		
		evaluacionMep.clickBtnGuardarAvance();
		evaluacionMep.clickBtnContinuar();
		photoEMEP.CopiarMoverRenombrar(pngCodigo2, directorioDestinoFolder);
		//PASO 1 ---------------------------------------------------------------------------------------------
		addStep("Haz clic en \"Tomar Foto\" para el grupo de espacios promocionales con códigos QR (ej. Cuarto Frio)");
		evaluacionMepOffline.clickBtnContinuarEvaluacion();
		evaluacionMepOffline.clickTomarFotoFoliosDeProduccion();
		//PASO 2 ---------------------------------------------------------------------------------------------
		addStep("Tome una foto del código QR relacionado con otro grupo de espacio promocional (por ejemplo, Salchikoxka)");
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
		return "ATC_FT_087_EtiquetaCompletadaEstadoNoCambiado";
	}

	@Override
	public String setTestDescription() {
		return "Verificar que la etiqueta previamente evaluada (Completado) no cambie su estado";
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
				+ "6. Hay al menos un grupo de espacios promocionales sin completar con códigos QR (p. ej., Cuarto Frío). "
				+ "7. Al menos una de las etiquetas del grupo de espacios promocionales se completó mediante el progreso de guardado.";
		}

	@Override
	public void beforeTest() {
	}
}

