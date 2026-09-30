package Mobile.cedis.mvp3.handheld.sprint9;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_010_ValidarBusquedaDeTransferenciasDeLaPantallaConsultaFUEDigital extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_010_ValidarBusquedaDeTransferenciasDeLaPantallaConsultaFUEDigital_test(
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
		 * Paso 1  - Ingresar al menú principal de Oxxo al dia
		*/
		
		/* 
		 * Paso 2  - ? Se ingresa correctamente al menu principal
		*/
		
		/* 
		 * Paso 3  - Seleccionar el icono de Excelencia Operativa
		*/
		
		/* 
		 * Paso 4  - ? Se despliega la pantalla de Excelencia Operativa
		*/
		
		/* 
		 * Paso 5  - Seleccionar el icono de Herramienta de Compra
		*/
		
		/* 
		 * Paso 6  - ? Se despliega el submenu de la pantalla de HERRAMIENTA DE COMPRA
		*/
		
		/* 
		 * Paso 7  - Seleccionar el icono de Cometa
		*/
		
		/* 
		 * Paso 8  - ? Se despliega la pantalla de Herramientas
		*/
		
		/* 
		 * Paso 9  - Seleccionar la opción que lleva por nombre "Consulta FUE Digital"
		*/
		
		/* 
		 * Paso 10  - ? Se ingresa a la pantalla "Consulta FUE Digital", y se muestra los radio button Transferencia y Fecha.
		*/
		
		/* 
		 * Paso 11  - Seleccionar el radio button de Tranferencia
		*/
		
		/* 
		 * Paso 12 - ? Se habilita el apartado para el ingreso del numero de transferencia a buscar.
		*/
		
		/* 
		 * Paso 13 - Ingresar un numero de transferencia que sea valida.
		*/
		
		/* 
		 * Paso 14 - ? Se ingresa correctamente el numero de transferencia a buscar.
		*/
		
		/* 
		 * Paso 15 - Seleccionar el boton que lleva por nombre buscar
		*/
		
		/* 
		 * Paso 16 - ? Se despliega correctamente la transferencia la cual fue realizada su busqueda al haber ingresado el  numero de la misma.
		*/
		
		
		addStep("Pendiente actualizar script");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*CEDIS MVP3: ATC-FT-010-Validar Búsqueda de transferencias de la pantalla "Consulta FUE Digital" (Numero de Transferencia)
	  Desc: Validación Búsqueda de transferencias (Por número de transferencia) de la pantalla  llamada "Consulta FUE Digital"
	  @autor: Vanessa Torres
	  @date: 04/02/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_010_ValidarBusquedaDeTransferenciasDeLaPantallaConsultaFUEDigital";
	}

	@Override
	public String setTestDescription() {
		return "Validación Búsqueda de transferencias (Por número de transferencia) de la pantalla  llamada \"Consulta FUE Digital\"";
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
				+ "6. Contar con dispositivo HH.";
	}

	@Override
	public void beforeTest() {
	}

}