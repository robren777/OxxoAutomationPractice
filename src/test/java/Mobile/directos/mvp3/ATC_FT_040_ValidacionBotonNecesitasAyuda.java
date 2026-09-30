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


public class ATC_FT_040_ValidacionBotonNecesitasAyuda extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_040_ValidacionBotonNecesitasAyuda_test(HashMap<String, String> data) throws Exception {
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
     * Paso 3 - Validar que se encuentre habilitado "¿Necesitas ayuda?"
     * Paso 4 - Seleccionar "¿Necesitas ayuda?"
     */
    
    addStep("Seleccionar \"¿Necesitas ayuda?\"");
    
    validacion = ProvDirectos.seleccionarNeedHelp();
    assertTrue(validacion,"El botón no se encuentra disponible.");
    
    /*
     * Paso 5 - Validar que se muestre información:
					-LOGO
					-Contacta a tu administrador de TI para que active el APP en tu dispositivo o al equipo comercial 
					para que te proporcione el código de activación.
					-Botón “ACEPTAR”
     */
    
    addStep("Validar que se muestre información:\r\n"
    		+ "-LOGO\r\n"
    		+ "-Contacta a tu administrador de TI para que active el APP en tu dispositivo o al equipo comercial \r\n"
    		+ "para que te proporcione el código de activación.\r\n"
    		+ "-Botón “ACEPTAR”");
    
    validacion = ProvDirectos.popUp_NecesitasAyuda();
    assertTrue(validacion,"El pop-Up del apartado ¿Necesitas ayuda? no se muestra de manera correcta.");
    
    
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
//==================================================================================================================//
  
  }
  
  /*
   * Nombre del caso de prueba Manual: MTC-FT-040 Validacion boton ¿necesita ayuda?
   * 
   * Desc: Validar que el boton "¿necesita ayuda?" contiene información correspondiente.
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
  	return "ATC_FT_071_CasoPrueba71";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que el boton \"¿necesita ayuda?\" contiene información correspondiente.";
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
  			+ "1. Contar con la versión mas reciente del APK Proveedores directos \r\n"
  			+ "2. Contar con un dispositivo móvil con Android 11\r\n"
  			+ "3. Tener el CR del PV dado de alta en la nube de Evolucion\r\n"
  			+ "4. Contar con accesos para consultas a BD de nube Evolucion";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
