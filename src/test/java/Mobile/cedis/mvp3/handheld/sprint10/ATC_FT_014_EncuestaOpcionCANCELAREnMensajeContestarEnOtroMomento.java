package Mobile.cedis.mvp3.handheld.sprint10;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_014_EncuestaOpcionCANCELAREnMensajeContestarEnOtroMomento extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_014_EncuestaOpcionCANCELAREnMensajeContestarEnOtroMomento_test(
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
		 * Paso 1  - Posicionarse en la pantalla Encuesta de satisfacción.
		*/
		
		/* 
		 * Paso 2  - ?Se muestra correctamente la pantalla encuesta de satisfacción
		*/
		
		/* 
		 * Paso 3  - Se muestra el botón comenzar y se presiona
		*/
		
		/* 
		 * Paso 4  - ? El botón es funcional y nos arroja correctamente la pantalla de la primera sección de preguntas de encuestas de satisfacción 
		*/
		
		/* 
		 * Paso 5  - Al final de la pantalla encuesta de satisfacción en la seccion que te encuentres se visualiza el apartado contestar en otro momento.
		*/
		
		/* 
		 * Paso 6  - ? Se muestra correctamente el apartado contestar en otro momento.
		*/
		
		/* 
		 * Paso 7  - Presionar el texto contestar en otro momento
		*/
		
		/* 
		 * Paso 8  - ?Se despliega correctamente el mensaje:¿Estas seguro que no deseas continuar? Tu retroalimentación es importante. En caso de no contestarla, la encuesta se detonará en otra visita del CEDIS.", con las opciones "CONFIRMAR" y "CANCELAR".
		*/
		
		/* 
		 * Paso 9  - Presionar CANCELAR
		*/
		
		/* 
		 * Paso 10  - ?Permanece en la pantalla de encuesta de satisfacción y permite continuar contestando preguntas.
		*/

		
		addStep("Pendiente actualizar script");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: MTC-FT-014 Encuesta, opción CANCELAR en mensaje contestar en otro momento. 
	  Desc: Validar que al momento de presionar la opción contestar en otro momento se despliegue el mensaje "¿Estas seguro que no deseas continuar? Tu retroalimentación es importante. En caso de no contestarla, la encuesta se detonará en otra visita del CEDIS.", con las opciones "CONFIRMAR" y "CANCELAR".
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_014_EncuestaOpcionCANCELAREnMensajeContestarEnOtroMomento";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al momento de presionar la opción contestar en otro momento se despliegue "
				+ "el mensaje \"¿Estas seguro que no deseas continuar? Tu retroalimentación es importante. "
				+ "En caso de no contestarla, la encuesta se detonará en otra visita del CEDIS.\", "
				+ "con las opciones \"CONFIRMAR\" y \"CANCELAR\".\r\n";
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
		return "1. App Oxxo al día instalada en Handheld.\r\n"
				+ "2. Usuario y Contraseña de Aplicación Oxxo al día.\r\n"
				+ "3. Comunicación entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexión entre ambos dispositivos).\r\n"
				+ "4. P.V con acceso a la Herramienta de compra.\r\n"
				+ "5. Contar con los accesos aplicacion Oxxo al dia. Usuario: lider, Contraseña: 2\r\n"
				+ "6. Contar con dispositivo HH.\r\n"
				+ "7. Haber finalizado un flujo de recepción para que en cuanto lo finalices te arroje la encuesta.\r\n"
				+ "8.Haber cargado ASN´S a tu P.V";
	}

	@Override
	public void beforeTest() {
	}

}