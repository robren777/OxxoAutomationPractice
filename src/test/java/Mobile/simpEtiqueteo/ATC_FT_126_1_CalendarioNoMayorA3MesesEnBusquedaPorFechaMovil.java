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

/**
 * DESCRIPCIÓN: 
 *  Se validara que en el calendario del apartado de fechas movil no nos muestre informacion más antigua a 3 meses desde el día actual
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_126_1_CalendarioNoMayorA3MesesEnBusquedaPorFechaMovil
 *
 * PRERREQUISITOS:
 *"1. Tener conectado el punto de venta
2. Tener bandera de Etiqueteo encendida
3.- Contar con dispositivo movil con la APK de Simplificación Etiqueteo en tienda de Etiqueteo instalada"
 * 
 * @author Mario Salazar
 * @date 30 Enero 2025
 */
public class ATC_FT_126_1_CalendarioNoMayorA3MesesEnBusquedaPorFechaMovil extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_126_1_CalendarioNoMayorA3MesesEnBusquedaPorFechaMovil_test(HashMap<String, String> data) throws Exception {
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
    	
    	String usuario = data.get("userLogin");
    	String password = PasswordUtil.decryptPassword(data.get("passwordLogin"));
    	SimpEtiqueteo simpEt = new SimpEtiqueteo(testCase, appiumUtil, this );
		
		//INICIO DE SCRIPT
    	//PASO 1
        /*********/
		addStep("Seleccionar app Oxxo al día");
		simpEt.iniciarApp(data.get("appPackage"), data.get("appActivity"));
    	//PASO 2
        /*********/
		addStep("Escribir usuario");
		simpEt.loginUsuario(usuario);	 
		//PASO 3
        /*********/
    	addStep("Escribir contraseña");
    	simpEt.loginContrasena(password);    	
    	//PASO 4
        /*********/
    	addStep("Selecciona la opción ingresar");
    	simpEt.loginIngresar();
    	//PASO 5
        /*********/
    	addStep("Selecciona la opción Excelencia Operativa");
    	simpEt.ExcelenciaOperativa(); 
    	//PASO 6
        /*********/
    	addStep("Selecciona la opcion de EtiqueteoPresiona botón [+] 5 veces");
    	simpEt.Etiqueteo();;
    	//PASO 7
        /*********/
    	addStep("Se presiona el boton Aceptar");
    	simpEt.aceptarColocarPapel();
    	//PASO 8
        /*********/
    	addStep("Selecciona la opcion de Fecha");
    	simpEt.clickBusquedaFecha();
    	//PASO 9
        /*********/
    	addStep("Mover el calendario a 3 meses hacia atrás");
    	simpEt.RegresarFecha3meses(3);
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_126_1_CalendarioNoMayorA3MesesEnBusquedaPorFechaMovil";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que en el calendario del apartado de fechas movil no nos muestre informacion más antigua a 3 meses desde el día actual";
	}

	@Override
	public String setTestDesigner() {
		return "Mario Salazar";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "\"1. Tener conectado el punto de venta\r\n"
				+ "2. Tener bandera de Etiqueteo encendida\r\n"
				+ "3.- Contar con dispositivo movil con la APK de Simplificación Etiqueteo en tienda de Etiqueteo instalada\"";
	}

	@Override
	public void beforeTest() {
	}
		

}
 