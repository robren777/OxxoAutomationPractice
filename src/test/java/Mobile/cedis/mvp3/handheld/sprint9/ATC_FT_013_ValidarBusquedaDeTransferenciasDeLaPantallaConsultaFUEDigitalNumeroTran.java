package Mobile.cedis.mvp3.handheld.sprint9;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_013_ValidarBusquedaDeTransferenciasDeLaPantallaConsultaFUEDigitalNumeroTran extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_013_ValidarBusquedaDeTransferenciasDeLaPantallaConsultaFUEDigitalNumeroTran_test(
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
		cap.setCapability("contenedor", data.get("contenedor"));

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

		// Ejecuta el logeo hacia el punto de venta
		Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);

		login.loginPV(data.get("ip"));

		// Ejecuta el logeo del lider
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));


		/*Paso 1.- Ingresar al menú principal de Oxxo al día
		 * Resultado esperado: Se ingresa correctamente al menú principal
		 */		
		addStep("Ingresar al menú principal de Oxxo al día\r\n"
				+ "Resultado esperado: Se ingresa correctamente al menú principal");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 2.- Seleccionar el icono de Excelencia Operativa
		 * Resultado esperado: Se despliega la pantalla de Excelencia Operativa
		 */		
		addStep("Seleccionar el icono de Excelencia Operativa\r\n"
				+ "Resultado esperado: Se despliega la pantalla de Excelencia Operativa");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3.- Seleccionar el icono de Herramienta de Compra
		 * Resultado esperado: Se despliega el submenú de la pantalla de HERRAMIENTA DE COMPRA
		 */		
		addStep("Seleccionar el icono de Herramienta de Compra\r\n"
				+ "Resultado esperado: Se despliega el submenú de la pantalla de HERRAMIENTA DE COMPRA");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 4.- Seleccionar el icono de Cometa
		 * Resultado esperado: Se despliega la pantalla de Herramientas
		 */		
		addStep("Seleccionar el icono de Cometa\r\n"
				+ "Resultado esperado: Se despliega la pantalla de Herramientas");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 5.- Seleccionar la opción que lleva por nombre "Consulta FUE Digital"
		 * Resultado esperado: Se ingresa a la pantalla "Consulta FUE Digital", y se muestra los radio botón Transferencia y Fecha.
		 */		
		addStep("Seleccionar la opción que lleva por nombre \"Consulta FUE Digital\"\r\n"
				+ "Resultado esperado: Se ingresa a la pantalla \"Consulta FUE Digital\", y se muestra los radio botón Transferencia y Fecha.");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 6.- Seleccionar el radio botón de Transferencia
		 * Resultado esperado: Se habilita el apartado para el ingreso del numero de transferencia a buscar.
		 */		
		addStep("Seleccionar el radio botón de Transferencia\r\n"
				+ "Resultado esperado: Se habilita el apartado para el ingreso del numero de transferencia a buscar.");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 7.- Ingresar un numero de transferencia que no sea valida.
		 * Resultado esperado: Se ingresa correctamente el numero de transferencia  no valida a buscar.
		 */		
		addStep("Ingresar un numero de transferencia que no sea valida.\r\n"
				+ "Resultado esperado: Se ingresa correctamente el numero de transferencia  no valida a buscar.");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 8.- Seleccionar el botón que lleva por nombre buscar 
		 * Resultado esperado: Se despliega correctamente el mensaje: No se encontró un pedido en la fecha previamente consultada ACEPTAR.
		 */		
		addStep("Seleccionar el botón que lleva por nombre buscar \r\n"
				+ "Resultado esperado: Se despliega correctamente el mensaje: No se encontró un pedido en la fecha previamente consultada ACEPTAR.");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC_FT_013_ValidarBusquedaDeTransferenciasDeLaPantallaConsultaFUEDigitalNumeroTran";
	}

	@Override
	public String setTestDescription() {
		return "Validación Búsqueda de transferencias (Por número de transferencia incorrecto) de la pantalla  llamada \"Consulta FUE Digital\"";
	}

	@Override
	public String setTestDesigner() {
		return "Juan Ibarra";
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
				+ "5. Contar con los accesos aplicación Oxxo al día. Usuario: líder, Contraseña: 2\r\n"
				+ "6. Contar con dispositivo HH.";
	}

	@Override
	public void beforeTest() {
	}

}