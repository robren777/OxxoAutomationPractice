package Mobile.Autoinventario;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.Autoinventario.om.Inicio;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class ATC_FT_008_ModuloAutoinventarioInteligenteHabilitadoEnApkDelAuditorEnHhDelAuditor extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_008_ModuloAutoinventarioInteligenteHabilitadoEnApkDelAuditorEnHhDelAuditor_test(HashMap<String, String> data) throws Exception {

		/******************************* Inicio - Configuracion Preliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackage"));
		cap.setCapability("appium:appActivity", data.get("appActivity"));

		String url = "http://127.0.0.1:4723/";

		/************************ Paso 1 - Ingresar a la App ***************************/
		addStep("Ingresar a Oxxo Auditor");		

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicación. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	

		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
	
	// Llamado al POM de Inicio de Oxxo Auditor
	Inicio opcInicio = new Inicio (testCase,appiumUtil,this);
	opcInicio.validateInicio();
	
	// Llamado al POM de Validar Contenido
	opcInicio.validateContenido();
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");//cerrar app
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-008- Módulo Autoinventario Inteligente habilitado en APK del Auditor en HH del Auditor.";
	}

	@Override
	public String setTestDescription() {
		return "En la HH del Auditor al ingresar en la APK del Auditor> Menú de Inicio se debe encontrar habilitado el módulo Autoinventario Inteligente";
	}

	@Override
	public String setTestDesigner() {
		return "Joel Rodriguez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. Tener instalado el APK del Auditor.\r\n"
				+ "2. Contar con HH y PV.\r\n"
				+ "3. Estar conectado a la red de Oxxo (Tienda).";
	}

	@Override
	public void beforeTest() {
	}

}