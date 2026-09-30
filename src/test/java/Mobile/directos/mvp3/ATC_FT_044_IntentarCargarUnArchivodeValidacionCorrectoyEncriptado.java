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


public class ATC_FT_044_IntentarCargarUnArchivodeValidacionCorrectoyEncriptado extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_044_IntentarCargarUnArchivodeValidacionCorrectoyEncriptado_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
		cap.setCapability("noReset", false); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto

				
		String url = "http://127.0.0.1:4723";

		//=======================================Cerrar Aplicaciones========================================================//
		  Thread.sleep(5000);
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		//==================================================================================================================//
	  
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
   * Paso 2 - Se muestra la pantalla de Recibo Ágil
				Logo Oxxo
				Titulo: "Bienvenido a Recibo Ágil"Texto: "Para activar la aplicación, favor de seguir los siguientes pasos:"
				texto: 1 "Cargar Archivo de Validación"
				texto 2 "Captura el Código de Activación"
				Boton: "COMENZAR" (habilitado)
   */
  
  	addStep("Se muestra la pantalla de Recibo Ágil\r\n"
  			+ "\r\n"
  			+ "Logo Oxxo\r\n"
  			+ "Titulo: \"Bienvenido a Recibo Ágil\"Texto: \"Para activar la aplicación, favor de seguir los siguientes pasos:\"\r\n"
  			+ "texto: 1 \"Cargar Archivo de Validación\"\r\n"
  			+ "texto 2 \"Captura el Código de Activación\"\r\n"
  			+ "Boton: \"COMENZAR\" (habilitado)");
    
    boolean validacion = ProvDirectos.pantallaReciboAgil();
    assertTrue(validacion,"La pantalla principal Recibo Agil no se muestra de manera correcta.");
    
    /*
     * Paso 3 - Seleccionar el botón: COMENZAR
     */
    
    addStep("Seleccionar el botón: COMENZAR");
    
    validacion = ProvDirectos.seleccionarComenzarRA();
    assertTrue(validacion,"El botón no se encuentra disponible.");
    
    /*
     * Paso 4 - Validar que se muestre deshabilitado el botón “CONTINUAR”
     * Paso 5 - Se muestra la pantalla de Recibo Ágil para la carga del Archivo de Validación
     * Se muestran los elementos princiaples de la pantalla
     * Se valida existencia de los siguientes elementos:
		* Logo Oxxo
		*Titulo: "Bienvenido a Recibo Ágil"
		*Texto: "1 Cargar Archivo de Validación"
		*Boton: "Cargar archivo"
		*Boton: "CONTINUAR" (deshabilitado)
     */
    
    addStep("Se muestra la pantalla de Recibo Ágil para la carga del Archivo de Validación\r\n"
    		+ "\r\n"
    		+ "Logo Oxxo\r\n"
    		+ "Titulo: \"Bienvenido a Recibo Ágil\"\r\n"
    		+ "Texto: \"1 Cargar Archivo de Validación\"\r\n"
    		+ "Boton: \"Cargar archivo\"\r\n"
    		+ "Boton: \"CONTINUAR\" (deshabilitado)");
    validacion = ProvDirectos.pantallaReciboAgilCargaArchivos();
    assertTrue(validacion,"La pantalla principal para cargar Archivo de Recibo Agil no se muestra de manera correcta.");
    
    /*
     * Paso 6 - Seleccionar el boton: Cargar archivo
     */
    
    addStep("Seleccionar el boton: Cargar archivo");
    
    validacion = ProvDirectos.seleccionarCargarArchivoRA();
    assertTrue(validacion,"El botón no se encuentra disponible.");
    
    /*
     * Paso 7 - Seleccionar el archivo a cargar (valido y encriptado).
     */
    
    addStep("Seleccionar el archivo a cargar (valido y encriptado).");
    
    validacion = ProvDirectos.seleccionarArchivoValidoyEncriptado();
    assertTrue(validacion,"El archivo no se encuentra disponible.");

    /*
     * Paso 8 - Se valida que el archivo de validacion es cargado cargado correntamente
				*Texto: "Archivo cargado correctamente"
				*Boton: CONTINUAR (habilitado)
     */
    
    addStep("Validar botón continuar Habilitado"
    		+ "Texto: \\\"Archivo cargado correctamente");
    validacion = ProvDirectos.validarContinuarHabilitado();
    assertTrue(validacion,"El botón no se encuentra disponible.");
    
    validacion = ProvDirectos.mensajeArchivoCargado();
    assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
    
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
//==================================================================================================================//
  
  }
    
  /*
   * Nombre del caso de prueba Manual: MTC-FT-044 Intentar cargar un archivo de validacion correcto y encriptado
   * 
   * Desc: Validar que la aplicacion permite la carga de un achivo cuando esta correcto y encriptado mostrando el mensaje: "Archivo cargado correctamente".
   * 
   * Prerequisitos:
	 1. Contar con un archivo que este correctamente encriptado y que su contenido sea válido.
	 2. Colocar Archivo generado en la carpeta de descargas.
	 3. Verificar que el nombre del archivo que se coloque sea "archivo_encriptado.aes".
	 4. Contar con un dispositivo móvil con Android 11
   * 
   * @autor Alejandro Valdez
   * @date 16/10/2024
   */

  @Override
  public String setTestFullName() {
  	return "ATC_FT_075_CasoPrueba75";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que la aplicacion permite la carga de un achivo cuando esta correcto y encriptado mostrando el mensaje: \"Archivo cargado correctamente\"";
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
