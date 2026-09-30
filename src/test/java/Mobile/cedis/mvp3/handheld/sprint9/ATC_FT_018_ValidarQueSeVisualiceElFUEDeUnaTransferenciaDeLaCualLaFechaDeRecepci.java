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

public class ATC_FT_018_ValidarQueSeVisualiceElFUEDeUnaTransferenciaDeLaCualLaFechaDeRecepci extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_018_ValidarQueSeVisualiceElFUEDeUnaTransferenciaDeLaCualLaFechaDeRecepci_test(
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

		/*Paso 1.- Ingresar al menú principal de Oxxo al día.
		 * Resultado esperado: Se ingresa correctamente al menú principal
		 */		
		addStep("Ingresar al menú principal de Oxxo al día.\r\n"
				+ "Resultado esperado: Se ingresa correctamente al menú principal");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 2.- Seleccionar el icono de Excelencia Operativa.
		 * Resultado esperado: Se despliega la pantalla de Excelencia Operativa
		 */		
		addStep("Seleccionar el icono de Excelencia Operativa.\r\n"
				+ "Resultado esperado: Se despliega la pantalla de Excelencia Operativa");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3.- Seleccionar el icono de Herramienta de Compra.
		 * Resultado esperado: Se despliega el submenú de la pantalla de HERRAMIENTA DE COMPRA
		 */		
		addStep("Seleccionar el icono de Herramienta de Compra.\r\n"
				+ "Resultado esperado: Se despliega el submenú de la pantalla de HERRAMIENTA DE COMPRA");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso4.- Seleccionar el icono de Cometa.
		 * Resultado esperado: Se despliega la pantalla de Herramientas
		 */		
		addStep("Seleccionar el icono de Cometa.\r\n"
				+ "Resultado esperado: Se despliega la pantalla de Herramientas");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
		
		
		/*Paso 5.- Seleccionar la opción que lleva por nombre "Consulta FUE Digital".
		 * Resultado esperado: Se ingresa a la pantalla "Consulta FUE Digital".
		 */		
		addStep("Seleccionar la opción que lleva por nombre \"Consulta FUE Digital\".\r\n"
				+ "Resultado esperado: Se ingresa a la pantalla \"Consulta FUE Digital\".");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 6.- Seleccionar una transferencia que se muestran en el listado de paginación de la cual se encuentra con una fecha anterior a 2 mes atrás.
		 * Resultado esperado: Se muestra el FUE de la transferencia seleccionada la cual es menor a 2 mes de antigüedad para poder visualizar su información.
		 */		
		addStep("Seleccionar una transferencia que se muestran en el listado de paginación de la cual se encuentra con una fecha anterior a 2 mes atrás.\r\n"
				+ "Resultado esperado: Se muestra el FUE de la transferencia seleccionada la cual es menor a 2 mes de antigüedad para poder visualizar su información.");
		Thread.sleep(1000);		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC_FT_018_ValidarQueSeVisualiceElFUEDeUnaTransferenciaDeLaCualLaFechaDeRecepci";
	}

	@Override
	public String setTestDescription() {
		return "Al seleccionar la transferencia se mostrara el FUE donde la fecha de recepción sea menor a 2 mes de antigüedad para poder visualizar su información.";
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
				+ "5. Contar con los accesos aplicacion Oxxo al dia. Usuario: lider, Contraseña: 2\r\n"
				+ "6. Contar con dispositivo HH.\r\n"
				+ "7. Tomar en cuenta la fecha de 2 meses anteriores para que sean visibles las transferencias, *tomar en fecha a partir del dia en que se encuentra realizando el CP.";
	}

	@Override
	public void beforeTest() {
	}

}