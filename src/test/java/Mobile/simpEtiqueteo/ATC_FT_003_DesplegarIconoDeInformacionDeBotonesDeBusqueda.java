package Mobile.simpEtiqueteo;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.simpEtiqueteo.om.SimpEtiqueteo;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
//import simpEtiqueteo.om.BDSimpEtiqueteo;

/**
 * DESCRIPCIÓN: 
 * Validar que se pueda seleccionar el ícono de información (i) de las opciones ""Por fecha"" y ""Planograma"" para visualizar una breve explicación de cada botón 
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_003_DesplegarIconoDeInformacionDeBotonesDeBusqueda
 *
 * PRERREQUISITOS:
 * 1. Handheld con mínimo Android 11
 * 2. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.
 * 
 * @author Amauri Rodriguez
 * @date 30 Enero 2025
 */

public class ATC_FT_003_DesplegarIconoDeInformacionDeBotonesDeBusqueda extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_003_DesplegarIconoDeInformacionDeBotonesDeBusqueda_test (HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Simplificacion de Etiqueteo en Tienda");
		
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); //Evita borrado de cache
		
		
		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		
    	AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
    	
    	Thread.sleep(2000);
    	appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
		
		SimpEtiqueteo simpEt = new SimpEtiqueteo(testCase, appiumUtil, this );
		//INICIO DE SCRIPT
        /***************************************************/
    	
    	//PASO 1
    	addStep("Seleccionar app Oxxo al d�a");
    	simpEt.iniciarApp(data.get("appPackage"), data.get("appActivity"));

    	//PASO 2
    	addStep("Escribir usuario");
    	simpEt.loginUsuario(data.get("userLogin"));
    	
    	//PASO 3
    	addStep("Escribir contrase�a");
    	simpEt.loginContrasena(PasswordUtil.decryptPassword(data.get("passwordLogin")));
    	
    	//PASO 4
    	addStep("Selecciona la opci�n ingresar");
    	simpEt.loginIngresar();
    	
    	//PASO 5
    	addStep("Selecciona la opci�n Excelencia Operativa");
    	simpEt.ExcelenciaOperativa();
    	
    	//PASO 6
    	addStep("Selecciona la opcion de Etiqueteo");
    	simpEt.Etiqueteo();
    	
    	//PASO 7
    	addStep("Selecciona el Bot�n Aceptar");
    	simpEt.aceptarColocarPapel();

    	//PASO 8
    	addStep("Seleccionar el icono de informaci�n (i) Por Fecha");
    	simpEt.mantenerPresionadoFechas();
    	
    	//PASO 9
    	addStep("Seleccionar el icono de informaci�n (i) de Planograma");
    	simpEt.mantenerPresionadoPlanograma();
    	
	}
	
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_003_DesplegarIconoDeInformacionDeBotonesDeBusqueda";
	}

	@Override
	public String setTestDescription() {
		return "\"Validar que se pueda seleccionar el ícono de información (i) de las opciones \"\"Por fecha\"\" y \"\"Planograma\"\" para visualizar una breve explicación de cada botón\r\n"
				+ "\"";
	}

	@Override
	public String setTestDesigner() {
		return "Amauri Rodriguez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Handheld con mínimo Android 11\r\n"
				+ "2. Tener conectado el dispositivo móvil a una red donde mantenga comunicación con el punto de venta.";
	}

	@Override
	public void beforeTest() {
	}

}
