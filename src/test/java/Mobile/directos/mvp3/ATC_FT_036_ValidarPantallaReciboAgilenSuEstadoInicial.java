package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.ProveedoresDirectos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;


public class ATC_FT_036_ValidarPantallaReciboAgilenSuEstadoInicial extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_036_ValidarPantallaReciboAgilenSuEstadoInicial_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
		cap.setCapability("noReset", false); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto

				
		String url = "http://127.0.0.1:4723";

		//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
		
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		  
		//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
	
	/*
	 * Paso 1 - Abrir la aplicación Proveedores directos.
	 */
	addStep("Abrir la aplicación Proveedores directos.");	  
		  
  	AppiumUtil appiumUtil = null;
  	try {
  		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	
  	Thread.sleep(3500);
  	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  	
  //••••••••••••••••••••••••••••••••••••Llamado al OM de Proveedores Directos••••••••••••••••••••••••••••••••••••//
  	
  ProveedoresDirectos ProvDirectos = new ProveedoresDirectos(testCase, appiumUtil, this);
  
  /*
   * Paso 2 - Se muestra la pantalla de Recibo Agil.
   */
  
  	addStep("Se muestra la pantalla de Recibo Agil.");
  	
    boolean validacion = ProvDirectos.existenciaReciboAgil();
    assertTrue(validacion,"La pantalla principal Recibo Agil no se muestra.");
  
  /*
   * Paso 3 - Se muestran los elementos princiaples de la pantalla.
   * Paso 4 - Se valida existencia de los siguientes elementos:
				* Logo Oxxo
				*Titulo: "Bienvenido a Recibo Ágil"
				*Texto: "Para activar la aplicación, favor de seguir los siguientes pasos:"
				*Paso 1: "Cargar Archivo de Validación"
				*Paso 2: "Captura el Código de Activación"
				*Boton: "COMENZAR" (habilitado) 
   */
    
    addStep("Se valida existencia de los siguientes elementos:\r\n"
    		+ "* Logo Oxxo\r\n"
    		+ "*Titulo: \"Bienvenido a Recibo Ágilr\n"
    		+ "*Texto: \"Para activar la aplicación, favor de seguir los siguientes pasos:r\n"
    		+ "*Paso 1: \"Cargar Archivo de Validaciónr\n"
    		+ "*Paso 2: \"Captura el Código de Activación\r\n"
    		+ "*Boton: \"COMENZAR\" (habilitado)");
    
    validacion = ProvDirectos.pantallaReciboAgil();
    assertTrue(validacion,"La pantalla principal Recibo Agil no se muestra de manera correcta.");
  
  //••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
	
    Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
    Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    
  //••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
  
  }
  
  /*
   * Nombre del caso de prueba Manual: MTC-FT-036 Validar pantalla Recibo Agil en su estado inicial
   * 
   * Desc: Validar los elementos de la pantalla Recibo Agil en su estado Inicial.
   * 
   * Prerequisitos:
	 1. Contar con la versión mas reciente del APK Proveedores directos 
	 2. Contar con un dispositivo móvil con Android 11
	 3. Tener el CR del PV dado de alta en la nube de Evolucion
	 4. Contar con accesos para consultas a BD de nube Evolucion
   * 
   * @autor Alejandro Valdez
   * @date 11/10/2024
   */

  @Override
  public String setTestFullName() {
  	return "ATC_FT_067_CasoPrueba67";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar los elementos de la pantalla Recibo Agil en su estado Inicial.";
  }  

  @Override
  public String setTestDesigner() {
  	return "Alejandro Valdez";
  }  

  @Override
  public String setTestInstanceID() {
  	return null;  
  }
  
  @Override
  public String setPrerequisites() {
  	return "1. Contar con la versión mas reciente del APK Proveedores directos \r\n"
  			+ "2. Contar con un dispositivo móvil con Android 11\r\n"
  			+ "3. Tener el CR del PV dado de alta en la nube de Evolucion\r\n"
  			+ "4. Contar con accesos para consultas a BD de nube Evolucion";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
