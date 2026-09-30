package Mobile.Autoinventario;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.Autoinventario.om.PantallaDiagnostico;
import Mobile.Autoinventario.om.loginOAD;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * MTC-FT-001-Botón de código QR en Oxxo al Día con Login
 * @author Joel Rodriguez
 * Descripcion: Validar que al loguearte a Oxxo al Día e ingresar a Pantalla de Diagnóstico se muestre el botón de Generar QR para obtener la IP y Puerto de enlace del PV y que se genere el codigo QR.
 * 
 * Pre condiciones para ejecutar Script automatizado:
 * 1. Tener dispositivo HandHeld y caja de procesos.
 * 2. Tener instalada la aplicación de Oxxo al Día.
 * 3. Estar conectado al PV desde Oxxo al Día.
 * 4. Tener acceso a Oxxo al Día.
 * 5. Estar conectado a la red de Oxxo (Tienda).
 * 
 * Recomendaciones / Comentarios
 * Ejemplo:
 * 1.- En caso de querer volver a ejecutar la app una 2da vez y no se ejecute, se debe de eliminar la app de 2do plano (Borrar la ventana en 2do plano en pocas palabras, NOTA: no se desinstala la app)
 * 2.- Cerciorarse manualmente que la app funciona con la red especifica antes de ejecutar este script
 */

public class ATC_FT_001_BotonDeCodigoQrEnOxxoAlDiaConLogin extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_001_BotonDeCodigoQrEnOxxoAlDiaConLogin_test(HashMap<String, String> data) throws Exception {

		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); // Se debe de dejar esta opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto

		String url = "http://127.0.0.1:4723/";

		/************************ Paso 1 - Ingresar a la App ***************************/
		addStep("Ingresar a Oxxo al Dia");		

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicación. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	

		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));


		// Llamado al POM de Login en Oxxo al Dia
		loginOAD loginApp = new loginOAD (testCase,appiumUtil,this);
		loginApp.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));

		// Llamado al POM de Pantalla Diagnostico
		PantallaDiagnostico diagnostico = new PantallaDiagnostico (testCase, appiumUtil, this);
		diagnostico.Opciones();
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");//cerrar app
	}


	@Override
	public String setTestFullName() {
		return "MTC-FT-001-Boton de codigo QR en Oxxo al dia con login";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al loguearte a Oxxo al Día e ingresar a Pantalla de Diagnóstico se muestre el botón de Generar QR para obtener la IP y Puerto de enlace del PV y que se genere el codigo QR";
	}

	@Override
	public String setTestDesigner() {
		return "Joel Rodriguez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Tener dispositivo HandHeld y caja de procesos.\n"
				+ " 2. Tener instalada la aplicación de Oxxo al Día.\n"
				+ " 3. Estar conectado al PV desde Oxxo al Día.\n"
				+ " 4. Tener acceso a Oxxo al Día.\n"
				+ " 5. Estar conectado a la red de Oxxo (Tienda).";
	}

	@Override
	public void beforeTest() {
	}

}