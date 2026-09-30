package Mobile.cedis.mvp3.handheld.sprint10;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_003_EncuestaDeSatisfaccionDespliegueDeEncuestaCuandoSeRealizaUnaRecepcionD extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_003_EncuestaDeSatisfaccionDespliegueDeEncuestaCuandoSeRealizaUnaRecepcionD_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackageP"));
		cap.setCapability("appium:appActivity", data.get("appActivityP"));
		cap.setCapability("ip", data.get("ip"));
		cap.setCapability("userLogin", data.get("userLogin"));
		cap.setCapability("passwordLogin", data.get("passwordLogin"));
		cap.setCapability("numTransferencia", data.get("numTransferencia"));

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

		
		/*
		 * Paso 1 .
		 */
		
		addStep("Pendiente actualizar script");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: MTC-FT-030/TABLET ACREDITACIÓN "Modificar Dañados y Caducos" - Agregar y quitar artículos dañados y caducos
	  Desc:Validar que las instrucciones que se muestran en la pantalla de dañados en verificación estén actualizadas de acuerdo al cambio de redacción.
	  @autor: Juan Ibarra
	  @date: 08/12/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_EncuestaDeSatisfaccionDespliegueDeEncuestaCuandoSeRealizaUnaRecepcionD";
	}

	@Override
	public String setTestDescription() {
		return "Validar que ";
	}

	@Override
	public String setTestDesigner() {
		return "Nombre Recurso";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. \r\n"
				+ "2. \r\n"
				+ "3. \r\n"
				+ "4. \r\n"
				+ "5. ";
	}

	@Override
	public void beforeTest() {
	}

}