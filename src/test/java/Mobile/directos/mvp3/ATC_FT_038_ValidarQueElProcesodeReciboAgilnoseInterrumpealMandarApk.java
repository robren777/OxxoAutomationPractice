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


public class ATC_FT_038_ValidarQueElProcesodeReciboAgilnoseInterrumpealMandarApk extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_038_ValidarQueElProcesodeReciboAgilnoseInterrumpealMandarApk_test(HashMap<String, String> data) throws Exception {
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
  	
    /*
     * Llamado al OM de Proveedores Directos.
     */
  	
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
    
    /*
     * Paso 5 - Seleccionar el botón: COMENZAR
     */
    
    addStep("Seleccionar el botón: COMENZAR");
    
    validacion = ProvDirectos.seleccionarComenzarRA();
    assertTrue(validacion,"El botón no se encuentra disponible.");
    
    /*
     * Paso 6 - Se muestra la pantalla de Recibo Ágil para la carga del Archivo de Validación
     * Se muestran los elementos princiaples de la pantalla
     * Se valida existencia de los siguientes elementos:
		* Logo Oxxo
		*Titulo: "Bienvenido a Recibo Ágil"
		*Texto: "1 Cargar Archivo de Validación"
		*Boton: "Cargar archivo"
		*Boton: "CONTINUAR" (deshabilitado)
     */
    
    addStep("Se muestra la pantalla de Recibo Ágil para la carga del Archivo de Validación"
    		+ "Se muestran los elementos princiaples de la pantalla"
    		+ "Se valida existencia de los siguientes elementos:\r\n"
    		+ "* Logo Oxxo\r\n"
    		+ "*Titulo: \"Bienvenido a Recibo Ágil\"\r\n"
    		+ "*Texto: \"1 Cargar Archivo de Validación\"\r\n"
    		+ "*Boton: \"Cargar archivo\"\r\n"
    		+ "*Boton: \"CONTINUAR\" (deshabilitado)");
    
    validacion = ProvDirectos.pantallaReciboAgilCargaArchivos();
    assertTrue(validacion,"La pantalla principal para cargar Archivo de Recibo Agil no se muestra de manera correcta.");
    
    /*
     * Paso 7 - Seleccionar el boton: Cargar archivo
     */
    
    addStep("Seleccionar el boton: Cargar archivo");
    
    validacion = ProvDirectos.seleccionarCargarArchivoRA();
    assertTrue(validacion,"El botón no se encuentra disponible.");
    
    /*
     * Paso 8 - Se muestra el contenido de dispositivo para elegir el archivo de validacion a cargar.
     */
    
    addStep("Se muestra el contenido de dispositivo para elegir el archivo de validacion a cargar.");
    
    validacion = ProvDirectos.existenciaContenidoDispositivo();
    assertTrue(validacion,"La pantalla de Contenido del dispositivo no se muestra de manera correcta.");
    
    /*
     * Paso 9 - Presionar el boton HOME para enviar la aplicación a Segundo Plano.
     */
    
    addStep("Presionar el boton HOME para enviar la aplicación a Segundo Plano");
    
    validacion = ProvDirectos.botonHOME();
    assertTrue(validacion,"El botón no se encuentra disponible");
    
    /*
     * Paso 10 - Se muestra la pantalla de HOME  en el dispositivo
     */
    
    addStep("Se muestra la pantalla de HOME  en el dispositivo");
    testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
    
    /*
     * Paso 11 - Regresar a la aplicación Proveedores Directos.
     */
    
    addStep("Regresar a la aplicación Proveedores Directos");  
    Thread.sleep(1000);
    appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
    testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
    
    /*
     * Paso 12 - Se valida se muestra nuevamente el contenido del dispositivo para elegir el archivo de validacion a cargar
     */
    
    addStep("Se valida se muestra nuevamente el contenido del dispositivo para elegir el archivo de validacion a cargar");
    
    validacion = ProvDirectos.existenciaContenidoDispositivo();
    assertTrue(validacion,"La pantalla de Contenido del dispositivo no se muestra de manera correcta.");
    
  
  //••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
    ProvDirectos.botonHOMESE();
    Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
    Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    
  //••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
  
  }
    
  /*
   * Nombre del caso de prueba Manual: MTC-FT-038 Validar que el proceso de recibo agil no se interrumpe al mandar la aplicación a segundo plano y regresar
   * 
   * Desc: Validar que el proceso de recibo agil no se interrumpe al mandar la aplicación a segundo plano y regresar.
   * 
   * Prerequisitos:
	 1. Contar con la versión mas reciente del APK Proveedores directos 
	 2. Contar con un dispositivo móvil con Android 11
	 3. Tener el CR del PV dado de alta en la nube de Evolucion
	 4. Contar con accesos para consultas a BD de nube Evolucion
   * 
   * @autor Alejandro Valdez
   * @date 14/10/2024
   */

  @Override
  public String setTestFullName() {
  	return "ATC_FT_069_CasoPrueba69";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que el proceso de recibo agil no se interrumpe al mandar la aplicación a segundo plano y regresar.";
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
