package Mobile.cedis.mvp3.handheld.sprint8;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_020_ValidacionDelFlujoAlSeleccionarMODIFICARDEVOLUCIONESDesdeLaPantallaFUE extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_020_ValidacionDelFlujoAlSeleccionarMODIFICARDEVOLUCIONESDesdeLaPantallaFUE_test(
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
			assertTrue(false, "Hubo un problema al acceder a la aplicación. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

		//=================================== Inicio de Caso =====================================//

		 /* 
		 * Paso 1  - Se inicia el caso por parte del usuario en la pantalla de FUE de una transferencia iniciada el flujo por la opción de Devoluciones a CEDIS 
		*/ 

		 /* 
		 * Paso 2  - ?Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes.
						Se muestran los botones de 
						-"MODIFICAR RECEPCIÓN"
						-"MODIFICAR DEVOLUCIONES" 
						-"TERMINAR"
		*/ 

		 /* 
		 * Paso 3  - Usuario selecciona el botón de "MODIFICAR DEVOLUCIONES" 
		*/ 

		 /* 
		 * Paso 4  - ?Se envía el caso a la pantalla de Pantalla Devoluciones CEDIS en la cual se muestra la pantalla CON la información capturada previamente. Permitiendo al usuario cargar nuevamente la información
		*/ 

		 /* 
		 * Paso 5  - Se selecciona la opción de Continuar en la pantalla de Devoluciones CEDIS una vez llenada la información correspondiente en la pantalla.
		*/ 

		 /* 
		 * Paso 6  - ?Se muestra la pantalla de Resumen Devolución con los campos correspondientes
		*/ 

		 /* 
		 * Paso 7  - Se selecciona la opción de Firma de Acuerdo en la pantalla de Resumen Devolución 
		*/ 

		 /* 
		 * Paso 8  - ?Se muestra la pantalla de Acuerdo de Devolución con los campos correspondientes donde el botón para finalizar el registro de las devoluciones ahora se renombrará a "TERMINAR DEVOLUCIÓN".
		*/ 

		 /* 
		 * Paso 9  - Se realiza la firma de Acuerdo de Devolución y selecciona el botón de TERMINAR DEVOLUCIÓN
		*/
		
		 /* 
		 * Paso 10  - ?Se muestra la pantalla de Formato Único de Entrega con sus campos correspondientes, actualizando la información correspondiente a la sección de Devoluciones a CEDIS.
		*/ 
		addStep("Pendiente actualizar script");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: MTC-FT-020 Validación del flujo al seleccionar MODIFICAR DEVOLUCIONES desde la pantalla FUE
	  Desc: Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de MODIFICAR DEVOLUCIONES en la pantalla de FUE
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_020_ValidacionDelFlujoAlSeleccionarMODIFICARDEVOLUCIONESDesdeLaPantallaFUE";
	}

	@Override
	public String setTestDescription() {
		return "Caso de prueba en el que se valida el flujo a tomar cuando se seleccione la opción de "
				+ "MODIFICAR DEVOLUCIONES en la pantalla de FUE";
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
		return "1. Contar con Transferencias por atender en la handheld\r\n"
				+ "2. Haber iniciado el flujo de la transferencia con la opción de Devoluciones a CEDIS \r\n"
				+ "3. Contar con la transferencia en la pantalla de FUE";
	}

	@Override
	public void beforeTest() {
	}

}