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


public class ATC_FT_043_IntentarCargarunArchivodeValidacionCorrectoyEncriptadoPeroVacio extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_043_IntentarCargarunArchivodeValidacionCorrectoyEncriptadoPeroVacio_test(HashMap<String, String> data) throws Exception {
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
     * Paso 4 - Se muestra la pantalla de Recibo Ágil para la carga del Archivo de Validación
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
     * Paso 7 - Se muestra el contenido de dispositivo para elegir el archivo de validacion a cargar.
     */
    
    addStep("Se muestra el contenido de dispositivo para elegir el archivo de validacion a cargar.");
    
    validacion = ProvDirectos.existenciaContenidoDispositivo();
    assertTrue(validacion,"La pantalla de Contenido del dispositivo no se muestra de manera correcta.");
    
    /*
     * Paso 8 - Seleccionar el archivo a cargar (Valido y encriptado pero VACIO).
     */
    
    addStep("Seleccionar el archivo a cargar (Valido y encriptado pero VACIO).");
    
    validacion = ProvDirectos.seleccionarArchivoVacioyEncriptado();
    assertTrue(validacion,"El archivo no se encuentra disponible.");
    
    /*
     * Paso 9 - El archivo no es cargado y se muestra el siguiente mensaje: "Error al leer el archivo, ingresa un archivo válido."
     */
    
    addStep("El archivo no es cargado y se muestra el siguiente mensaje: \"Error al leer el archivo, ingresa un archivo válido.\"");
    
    validacion = ProvDirectos.mensajeErrorAlLeerElArchivo();
    assertTrue(validacion,"El mensaje de Warning no se muestra de manera correcta.");
    

    /*
     * Paso 10 - El boton de CONTINUAR permanece Inactivo
     */
    
    addStep("El boton de CONTINUAR permanece Inactivo");
    
    validacion = ProvDirectos.validarContinuarDeshabilitado();
    assertTrue(validacion,"El botón no se encuentra disponible.");
    
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
//==================================================================================================================//
  
  }
  
  /*
   * Nombre del caso de prueba Manual: MTC-FT-043 Intentar cargar un archivo de validacion correcto y encriptado pero vacio
   * 
   * Desc: Validar que la aplicacion NO permite la carga de un achivo correcto y encriptado  pero vacio mostrando 
   * el mensaje: "Error al leer el archivo, ingresa un archivo válido."
   * 
   * Prerequisitos:
	 1. Contar con un archivo que este correctamente encriptado pero que su contendio este vacio, unicamente cuenta con los
	    encabezados de las columnas.
	 2. Colocar Archivo generado en la carpeta de descargas.
	 3. Verificar que el nombre del archivo que se coloque sea "archivo_encriptado.aes".
	 4. Contar con un dispositivo móvil con Android 11
   * 
   * @autor Alejandro Valdez
   * @date 16/10/2024
   */

  @Override
  public String setTestFullName() {
  	return "ATC_FT_074_CasoPrueba74";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que la aplicacion NO permite la carga de un achivo correcto y encriptado  pero vacio mostrando el mensaje: "
  			+ "\"Error al leer el archivo, ingresa un archivo válido.\"";
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
