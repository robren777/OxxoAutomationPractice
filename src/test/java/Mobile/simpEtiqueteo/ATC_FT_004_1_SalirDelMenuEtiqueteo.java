package Mobile.simpEtiqueteo;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.simpEtiqueteo.om.SQLSimpEtiqueteo;
import Mobile.simpEtiqueteo.om.SimpEtiqueteo;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * DESCRIPCIÓN: 
 *  "Validar que al  Salir de la opción de Etiqueteo (tanto de botón app, como botón nativo de android) se muestra el mensaje:
Recuerda
Regresar al papel de ticket en la impresora
Botón Aceptar
"
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_004_1_SalirDelMenuEtiqueteo
 *
 * PRERREQUISITOS:
 *"1. Tablet con mínimo Android 11
2. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta."
 * 
 * @author Mario A. De Leon Rdz.
 * @date 30 Enero 2025
 */
public class ATC_FT_004_1_SalirDelMenuEtiqueteo extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_1_SalirDelMenuEtiqueteo_test(HashMap<String, String> data) throws Exception {
		//PREREQUISITOS
		String serverXposStore = data.get("serverXposStore");
		String serverSatelites = data.get("serverSatelites");

		//Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackageP"));
		cap.setCapability("appium:appActivity", data.get("appActivityP"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
		
		String url = "http://127.0.0.1:4723/";
		
		// Cerrar Apps
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");

    	AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
    	SimpEtiqueteo simp = new SimpEtiqueteo(testCase, appiumUtil, this);
    	SQLSimpEtiqueteo sqlSimp = new SQLSimpEtiqueteo(testCase, this);
    	sqlSimp.encenderImpresoraVirtual(serverXposStore);
    	sqlSimp.sqlActualizaFechaPlanogramaMovil(serverSatelites);
    	
    	Thread.sleep(2000);
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		
		//PASO 1 --------------------------------------------------------------------------------------
		addStep("Seleccionar app Oxxo al día");
		Thread.sleep(5000);
		simp.iniciarApp(data.get("appPackage"), data.get("appActivity"));
		//PASO 2--------------------------------------------------------------------------------------
		addStep("Escribir usuario");
		simp.loginUsuario(data.get("userLogin"));
		//PASO 3--------------------------------------------------------------------------------------
		addStep("Escribir contraseña");
		simp.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
		//PASO 4--------------------------------------------------------------------------------------
		addStep("Selecciona la opción ingresar");
		simp.loginIngresar();
		//PASO 5--------------------------------------------------------------------------------------
		addStep("Selecciona la opción Excelencia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 6--------------------------------------------------------------------------------------
		addStep("Selecciona la opcion de Etiqueteo");
		simp.Etiqueteo();
		//PASO 7--------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 8--------------------------------------------------------------------------------------
		addStep("Selecciona la opción de \"Regresar (<--)\" de la app");
		simp.regresarApp();
		//PASO 9--------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 10--------------------------------------------------------------------------------------
		addStep("Seleccionar Excelecia Operativa");
		simp.ExcelenciaOperativa();
		//PASO 11--------------------------------------------------------------------------------------
		addStep("Selecciona la opción de Etiqueteo");
		simp.Etiqueteo();
		//PASO 12--------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();
		//PASO 13--------------------------------------------------------------------------------------
		addStep("Selecciona la opción de \"Regresar (<--)\"  nativa del dispositivo móvil");
		simp.regresarAndroid();
		//PASO 14--------------------------------------------------------------------------------------
		addStep("Selecciona el Botón Aceptar");
		simp.aceptarColocarPapel();

		
		// Cerrar Apps
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_004_1_SalirDelMenuEtiqueteo";
	}

	@Override
	public String setTestDescription() {
		return "\"Validar que al  Salir de la opción de Etiqueteo (tanto de botón app, como botón nativo de android) se muestra el mensaje:\r\n"
				+ "Recuerda\r\n"
				+ "Regresar al papel de ticket en la impresora\r\n"
				+ "Botón Aceptar\r\n"
				+ "\"";
	}

	@Override
	public String setTestDesigner() {
		return "Mario A. De Leon Rdz.";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "\"1. Tablet con mínimo Android 11\r\n"
				+ "2. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.\"";
	}

	@Override
	public void beforeTest() {
	}
}

