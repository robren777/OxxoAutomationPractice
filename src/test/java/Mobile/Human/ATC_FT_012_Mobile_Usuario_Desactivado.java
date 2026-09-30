package Mobile.Human;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.Human.om.LoginUserAndPasswordOxxoTesting;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * MTC-FT-012-Mobile-Usuario-Desactivado
 * 
 * Estos casos aplican tambien a:
 * 
 * MTC-FT-015-Mobile-Validar-mensaje-de-error-al-escribir-una-usuario-incorrecto
 * 
 * @author Ivan Yakab
 * Descripcion: Validar que al ingresar una contrase�a incorrecta, se despliegue un mensaje de error 
 * 
 * Pre condiciones para ejecutar Script automatizado:
 * 1.- Dentro del DataProvider se debe tener el usuario con el que se desea Loguear.
 * 
 * Recomendaciones / Comentarios
 * 1.- Puedes utilizar cualquier rol de Usuario (OS,PLAZA,OPERATIVO) para esta prueba.
 * 2.- En caso de que no te permita loguearte ya que el Login tiene OTP (Via SMS), solicitar a Humand o PO del Proyecto que deshabiliten el OTP en la comunidad OXXO TESTING
 * 3.- Cerciorarse manualmente (Entrar al sitio web o App) antes de ejecutar este script que el usuario que ingresaste dentro del DataProvider se pueda loguear en comunidad Oxxo Testing
 * 
 * Dependencias:
 * 1.- DataLake no tiene ambiente de QA
 * 2.- Solicitar a Humand (Fabricante de la APP y Sitio web) o PO del proyecto usuarios para ejecutar pruebas en caso de que no se tengan. A su vez preguntar cuales son los usuarios dados de baja o que pronto se dar�n de baja.
 */

public class ATC_FT_012_Mobile_Usuario_Desactivado  extends BaseExecution {	
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_012_Mobile_Infonavit_Os_test(HashMap<String, String> data) throws Exception {
		
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		
		String url = "http://127.0.0.1:4723";
		
    	/************************ Paso 1 - Ingresar a la App ***************************/
    	addStep("Acceder a la Aplicacion");		
    	
    	AppiumUtil appiumUtil = null;
    	try {
    		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Esperar 10 Segundos por cada elemento que falte por aparecer
			
		LoginUserAndPasswordOxxoTesting login = new LoginUserAndPasswordOxxoTesting(testCase, appiumUtil, this);

		login.validateMessageErrorInputUser(data.get("user"));
	}
		
	@Override
	public String setTestFullName() {
		return "ATC_FT_012_Mobile_Infonavit_Os_test";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el usuario desactivado no pueda loguearse en la app de Humand por ser un usuario dado de baja.";
	}

	@Override
	public String setTestDesigner() {
		return "Ivan Yakab";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Obtener el archivo excel con usuarios para validar el caso de prueba\r\n"
				+ "2 Tener un usuario creado previamente\r\n"
				+ "3 No tener una sesi�n activa dentro de la aplicaci�n\r\n"
				+ "4 Acceder a la primera vista de Inicio de sesi�n\r\n"
				+ "5 Tener instalada la aplicaci�n de Human en nuestro dispositivo m�vil";
	}

	@Override
	public void beforeTest() {
	}
}
