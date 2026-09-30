package Mobile.cedis.mvp3.handheld.sprint10;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_001_EncuestaDeSatisfaccionDespliegueDeEncuesta extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_001_EncuestaDeSatisfaccionDespliegueDeEncuesta_test(
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

		//=================================== Inicio de Caso =====================================//

		 /* 
		 * Paso 1  - Se ubica en la pantalla del listado de transferencias por recibir de Cedis, en la cual se encuentra una sola transferencia
		*/
		
		/* 
		 * Paso 2  - ?Se despliega correctamente la pantalla del listado de transferencias por recibir de Cedis, con una sola transferencia
		*/
		
		/* 
		 * Paso 3  - Se realiza el proceso de recepcion de esta transferencia
		*/
		
		/* 
		 * Paso 4  - ?Se termina el proceso de recepcion de manera satisfactoria con el mensaje "Recepcion de Pedido Exitoso"
		*/
		
		/* 
		 * Paso 5  - Se da clic en el botón de confirmacion del mensaje de finalizacion de recepcion
		*/
		
		/* 
		 * Paso 6  - ?Se despliega la pantalla de encuesta de satisfaccion, Titulo: "Encuesta de satisfaccion"
		*/

		
		addStep("Pendiente actualizar script");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: MTC-FT-001 - Encuesta de Satisfaccion - Despliegue de Encuesta
	  Desc: Validar que, la aplicación detecta correctamente la cantidad de transferencias por recibir y que la encuesta se detona al finalizar la última transferencia.
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_EncuestaDeSatisfaccionDespliegueDeEncuesta";
	}

	@Override
	public String setTestDescription() {
		return "Validar que, la aplicación detecta correctamente la cantidad de transferencias por "
				+ "recibir y que la encuesta se detona al finalizar la última transferencia.";
	}

	@Override
	public String setTestDesigner() {
		return "Vanessa Torres";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Dentro del listado de transferencias por recepcionar del Cedis, tener una sola transferencia.";
	}

	@Override
	public void beforeTest() {
	}

}