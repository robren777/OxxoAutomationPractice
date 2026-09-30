package Mobile.Autoinventario;
 
import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.Autoinventario.om.archivos;
//import Autoinventario.mobile.om.prueba;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
//import utils.password.PasswordUtil;
 
/**
 * ATC_FT_007_InstalarAPKdelAuditorEnHHdelAuditor
 * @author Rebeca Martinez
 * Descripcion: Realizar la instalación del APK del Auditor en la HH y validar que se muestre el icono.
 * 
 * Pre condiciones para ejecutar Script automatizado:
 *  1. Se debe completar la primer interfaz de la app donde se ingresa la ip y el puerto hasta llegar al Login
 *  2. No se debe eliminar el cache ni los datos de la app
 *  3. No se debe eliminar el capability noReset ya que este capability no elimina el cache de la app
 *  4. Se debe tener dentro del data provider los usuarios y password a utilizar
 *  5. Tener conectado una HandHeld
 *  6. Tener la aplicacion instalada dentro de la HandHeld
 *  7. Estar conectado a una red especifica
 * 
 * Recomendaciones / Comentarios
 * Ejemplo:
 * 1.- Utilizar el usuario con el Rol del Nombre del Caso de Prueba
 * 2.- En caso de querer volver a ejecutar la app una 2da vez y no se ejecute, se debe de eliminar la app de 2do plano (Borrar la ventana en 2do plano en pocas palabras, NOTA: no se desinstala la app)
 * 3.- Cerciorarse manualmente que la app funciona con la red especifica antes de ejecutar este script
 */
public class ATC_FT_007_InstalarAPKdelAuditorEnHHdelAuditor extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_007_InstalarAPKdelAuditorEnHHdelAuditor_test(HashMap<String, String> data) throws Exception {
		
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
		
		String url = "http://127.0.0.1:4723/";
 
		
	   	/************************ Paso 1 - Ingresar a la App ***************************/
		addStep("Se accede correctamente a la Aplicacion (Oxxo Auditor)");		
    	
    	AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicación. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
    	
    	appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	   	/************************ Paso 2 - Ingresar a menu inicio, validar Modulos ***************************/
		// Llamado al POM prueba
        /************************  Paso 1 - Instala App Oxxo al Dia  ***************************/
    	archivos instala = new archivos(testCase,appiumUtil,this);
    	instala.tapArchivos();
    	appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
    	//Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    	}
	
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_007_InstalarAPKdelAuditorEnHHdelAuditor";
	}
 
	@Override
	public String setTestDescription() {
		return "Realizar la instalación del APK del Auditor en la HH y validar que se muestre el icono.";
	}
 
	@Override
	public String setTestDesigner() {
		return "Rebeca Martinez";
	}
 
	@Override
	public String setTestInstanceID() {
		return null;
	}
 
	@Override
	public String setPrerequisites() {
		return "1. Contar con APK del Auditor.\n"
				+ "2. Contar con HH.\\n"
				+ "3. Estar conectado a la red de Oxxo (Tienda)."
				;
				
	}
 
	@Override
	public void beforeTest() {
	}
}
