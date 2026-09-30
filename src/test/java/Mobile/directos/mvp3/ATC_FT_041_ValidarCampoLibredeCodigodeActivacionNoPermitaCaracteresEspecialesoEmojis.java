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


public class ATC_FT_041_ValidarCampoLibredeCodigodeActivacionNoPermitaCaracteresEspecialesoEmojis extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_041_ValidarCampoLibredeCodigodeActivacionNoPermitaCaracteresEspecialesoEmojis_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
		cap.setCapability("appium:settings[enableMultiWindows]", true);
		cap.setCapability("noReset", false); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto

				
		String url = "http://127.0.0.1:4723";

		//=======================================Cerrar Aplicaciones========================================================//
		  Thread.sleep(5000);
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		//==================================================================================================================//
		  
	/*
	 * Paso 1 - Estar posicionado en la pantalla de Capturar el Código de Activación.
	 */
	addStep("Estar posicionado en la pantalla de Capturar el Código de Activación.");	  
		  
  	AppiumUtil appiumUtil = null;
  	try {
  		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	
  	Thread.sleep(3500);
  	
    /*
     * Llamado al OM de Proveedores Directos.
     */
  	
  	ProveedoresDirectos ProvDirectos = new ProveedoresDirectos(testCase, appiumUtil, this);
  
    // Pre - Requisito - Llegar a Pantalla de Captura de código
  	ProvDirectos.seleccionarComenzarRASE();
  	ProvDirectos.seleccionarCargarArchivoRASE();    
  	ProvDirectos.seleccionarArchivoValidoSE(); 	
  	ProvDirectos.seleccionarContinuarRASE();
  	Thread.sleep(1000);
  	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  	
  	/*
  	 * Paso 2 - Se valida existencia de los siguientes elementos:
					Logo Oxxo
					Titulo: "Bienvenido a Recibo Ágil"
					Texto: "2 Captura el Código de Activación"
					Campo: "Ingresa el Código"
					Boton: "ACTIVAR" (deshabilitado)
					Texto: "¿Necesitas ayuda?"
  	 */
  	
  	
    addStep("Se valida existencia de los siguientes elementos:\r\n"
    		+ "Logo Oxxo\r\n"
    		+ "Titulo: \"Bienvenido a Recibo Ágil\"\r\n"
    		+ "Texto: \"2 Captura el Código de Activación\"\r\n"
    		+ "Campo: \"Ingresa el Código\"\r\n"
    		+ "Boton: \"ACTIVAR\" (deshabilitado)\r\n"
    		+ "Texto: \"¿Necesitas ayuda?\"");
    
    boolean validacion = ProvDirectos.pantallaReciboAgilIngresoCodigo();
    assertTrue(validacion,"La pantalla principal para Capturar el Código de Recibo Agil no se muestra de manera correcta.");
  	
    /*
     * Paso 3 - Ingresar código con caracteres especiales
     */
    
    addStep("Ingresar código con caracteres especiales");
    
    ProvDirectos.ingresarCaracteresEspeciales();
    
    /*
     * Paso 4 - Verificar que no permite ingresar caracteres especiales
     */
    
    addStep("Verificar que no permite ingresar caracteres especiales");
    
    validacion = ProvDirectos.validacionCampoVacioCodigoActivacion();
    assertTrue(validacion,"No se muestra Vacio el campo para ingresar código.");
    
    /*
     * Paso 5 - Ingresar emojis
     */
    
    addStep("Ingresar emojis");
    
    ProvDirectos.ingresarEmojis();
    
    /*
     * Paso 6 - Verificar que no permite ingresar emojis
     */
    
    addStep("Verificar que no permite ingresar emojis");
    
    validacion = ProvDirectos.validacionCampoVacioCodigoActivacion();
    assertTrue(validacion,"No se muestra Vacio el campo para ingresar código.");
    
    /*
     * Paso 7 - Intentar colocar espacios
     */
    
    addStep("Intentar colocar espacios");
    
    ProvDirectos.ingresarEspacios();
    
    /*
     * Paso 8 - Verificar que no permita ingresar espacios
     */
    
    addStep("Verificar que no permita ingresar espacios");
    
    validacion = ProvDirectos.validacionCampoVacioCodigoActivacion();
    assertTrue(validacion,"No se muestra Vacio el campo para ingresar código.");
    
    /*
     * Paso 9 - Validar que permita ingresar números 
     */
    
    addStep("Validar que permita ingresar números ");
    
    ProvDirectos.ingresarNumeros();
    
    /*
     * Paso 10 - Verificar que permita ingresar números 
     */
    
    addStep("Verificar que permita ingresar números ");
    
    validacion = ProvDirectos.validacionCampoLlenoCodigoActivacion();
    assertTrue(validacion,"No se muestra con datos el campo para ingresar código.");
    
    
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
//==================================================================================================================//
  
  }
  
  /*
   * Nombre del caso de prueba Manual: MTC-FT-041 validar campo libre de Código de Activación no permita caracteres especiales o emojis
   * 
   * Desc: Validar que no se permita ingresar caracteres especiales (-,.”<>_etc) o emojis en el campo libre de código de activación.
   * 
   * Prerequisitos:
	 1. Contar con un archivo que este correctamente encriptado y que su contenido sea válido.
	 2. Colocar Archivo generado en la carpeta de descargas (Downloads).
	 3. Verificar que el nombre del archivo que se coloque sea "archivo_encriptado.aes".
	 4. Contar con un dispositivo móvil con Android 11
   * 
   * @autor Alejandro Valdez
   * @date 16/10/2024
   */

  @Override
  public String setTestFullName() {
  	return "ATC_FT_072_validar_campo_libre_de_Codigo_de_Activacion_no_permita_caracteres_especiales_o_emojis";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que no se permita ingresar caracteres especiales (-,.”<>_etc) o emojis en el campo libre de código de activación.";
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
  	return "Contar con archivo CSV Encriptado de manera correcta y que sea válido."
  			+ "1.Contar con la versión mas reciente del APK Proveedores directos\r\n"
  			+ "2.Tener el CR del PV dado de alta en la nube de Evolución\r\n"
  			+ "3.Haber habrierto APK Proveedores directos y haber cargado archivo csv\r\n"
  			+ "4.esta posicionado en la pantalla de codigo de activacion";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
