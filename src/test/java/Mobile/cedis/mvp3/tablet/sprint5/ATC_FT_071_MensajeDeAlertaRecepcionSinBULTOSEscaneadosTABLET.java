package Mobile.cedis.mvp3.tablet.sprint5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_071_MensajeDeAlertaRecepcionSinBULTOSEscaneadosTABLET extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_071_MensajeDeAlertaRecepcionSinBULTOSEscaneadosTABLET_test(
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
		 * Paso 1  - Estar en la pantalla de Recepcion de Pedidos CEDIS con un dispositivo TABLET
		*/ 

		 /*
		 * Paso 2  - ?Se muestra la pantalla de Recepcion
		*/ 

		 /* 
		 * Paso 3  - Pulsar el icono de la camara y tomar las 3 fotografias que se pide en la aplicacion.
		*/ 

		 /* 
		 * Paso 4  - ?Se toman las 3 evidencias y se muestran de forma enlistada. 
		*/ 

		 /* 
		 * Paso 5  - Dar clic en el boton de TERMINAR sin haber escaneado algun bulto de Cajas, canastillas Secos y Canastillas Refrigerados
		*/ 

		 /* 
		 * Paso 6  - ?Se muestra un mensaje de alerta con el mensaje: "No se puede marcar todo el pedido como faltante, favor de corroborar los bultos del pedido."  
					 Este mensaje mostrara el boton de ACEPTAR
		*/ 

		 /* 
		 * Paso 7  - Dar clic en el boton de ACEPTAR.
		*/ 

		 /* 
		 * Paso 8  - ?Se regresa a la pantalla de Recepcion de Pedidos CEDIS cerrando el mensaje.
		*/ 
		
		addStep("Pendiente actualizar script");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: MTC-FT-071 Mensaje de Alerta - Recepcion sin BULTOS escaneados (TABLET)
	  Desc:Validar que se muestre el mensaje correspondiente en la pantalla de Recepcion de Mercancia al continuar sin haber escaneado algun bulto utilizando un dispositivo TABLET.
	  @autor: Vanessa Torres
	  @date: 04/02/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_071_MensajeDeAlertaRecepcionSinBULTOSEscaneadosTABLET";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se muestre el mensaje correspondiente en la pantalla de Recepcion de Mercancia al continuar sin haber"
				+ " escaneado algun bulto utilizando un dispositivo TABLET";
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
		return "1. Contar con dispositivo TABLET\r\n"
				+ "2. Version APK actual\r\n"
				+ "3. Punto de Venta \r\n"
				+ "4. Ingresar a la pantalla de Recepcion por Ruta\r\n"
				+ "5. Seleccionar la opcion de Recepcion sin escaner\r\n"
				+ "6. Seleccionar una transferencia a recepcionar que cuente con Cajas, canastillas Secos y Canastillas Refrigerados";
	}

	@Override
	public void beforeTest() {
	}

}