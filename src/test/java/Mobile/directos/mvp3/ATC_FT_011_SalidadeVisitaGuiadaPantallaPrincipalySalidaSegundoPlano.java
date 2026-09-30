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


public class ATC_FT_011_SalidadeVisitaGuiadaPantallaPrincipalySalidaSegundoPlano extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_011_SalidadeVisitaGuiadaPantallaPrincipalySalidaSegundoPlano_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
				
		String url = "http://127.0.0.1:4723";

		//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
		
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		  
		//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//

		
  	/************************ Paso 1 - Abrir aplicacion “Proveedores Directos” ***************************/
  	addStep("Abrir aplicacion “Proveedores Directos”");		
  
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
   * 2 - Se muestra pantalla principal de Proveedores Directos.
   */
 
  addStep("Se muestra pantalla principal de Proveedores Directos");	
  
  boolean pantallaPrincipalProveedoresDirectos = ProvDirectos.pantallaPrincipalProveedoresDirectos();
  assertTrue(pantallaPrincipalProveedoresDirectos,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta."); 
  
  
  /*
   * 3 - Seleccionar opcion “Capacitacion con Visita Guiada”.
   */
  
  addStep("Seleccionar opcion “Capacitacion con Visita Guiada”.");
  
  boolean opcionCapacitacionVisitaGuiada = ProvDirectos.opcionCapacitacionVisitaGuiada();
  assertTrue(opcionCapacitacionVisitaGuiada,"El botón no se encuentra disponible");
  
  
  /*
   * 4 - Se muestra la pantalla Capacitacion con Visita Guiada.
   */
  
  addStep("Se muestra la pantalla Capacitacion con Visita Guiada.");
  
  boolean pantallaPrincipalCapacitacionVisitaGuiada = ProvDirectos.pantallaPrincipalCapacitacionVisitaGuiada();
  assertTrue(pantallaPrincipalCapacitacionVisitaGuiada,"La pantalla principal de la sección Capacitación de Visita Guiada no se muestra de manera correcta.");

  
  /*
   * 5 - Colocar aplicación "Proveedores Directos" en segundo plano del dispositivo.
   */
  
  addStep("Colocar aplicación \"Proveedores Directos\" en segundo plano del dispositivo.");
  
  boolean botonHOME = ProvDirectos.botonHOME();
  assertTrue(botonHOME,"El botón no se encuentra disponible");
  
 
  /*
   * 6 - Se deja de mostrar pantalla de Capacitacion con Visita Guiada.
   */
  
  addStep("Se deja de mostrar pantalla de Capacitacion con Visita Guiada");
  
  boolean ausenciaPantallaCapacitacionVisitaGuiada = ProvDirectos.ausenciaPantallaCapacitacionVisitaGuiada();
  //assertTrue(ausenciaPantallaCapacitacionVisitaGuiada,"La pantalla principal de la sección Capacitación de Visita Guiada esta habilitada.");
  
  /*
   * 7 - Abrir aplicacion “Proveedores Directos”.
   */
  
  addStep("Abrir aplicacion “Proveedores Directos");  
  Thread.sleep(3000);
  appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  
  
  /*
   * 8 - Se muestra correctamente el mismo modulo de Capacitacion con Visita Guiada.
   */
  
  addStep("Se muestra correctamente el mismo modulo de Capacitacion con Visita Guiada.");
  
  boolean pantallaPrincipalCapacitacionVisitaGuiadas = ProvDirectos.pantallaPrincipalCapacitacionVisitaGuiada();
  assertTrue(pantallaPrincipalCapacitacionVisitaGuiadas,"La pantalla principal de la sección Capacitación de Visita Guiada no se muestra de manera correcta.");
  
  
  /*
   * 9 - Seleccionar botón “Back” del dispositivo.
   */
  
  addStep("Seleccionar botón “Back” del dispositivo.");
  
  boolean botonBACK = ProvDirectos.botonBACK();
  assertTrue(botonBACK,"El botón no se encuentra disponible");
  
  
  /*
   * 10 - Se muestra pantalla principal de Proveedores Directos.
   */
 
  addStep("Se muestra pantalla principal de Proveedores Directos");	
  
  boolean pantallaPrincipalProveedoresDirectoss = ProvDirectos.pantallaPrincipalProveedoresDirectos();
  assertTrue(pantallaPrincipalProveedoresDirectoss,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta."); 
  
//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
	
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
  
//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
  
  }
   
  /*
   * Nombre del caso de prueba Manual: MTC-FT-011 Salida de visita guiada pantalla principal y salida segundo plano
   * 
   * Desc: Validar que al estar posicionado en el menú “Selecciona el tipo de surtido” permita dejar la aplicación en segundo plano
   *  y al regresar a la aplicación continue en el mismo menú, estando en esta misma pantalla se valida que se pueda salir del menú
   *  a la pantalla principal presionando el botón regresar nativo de Android .
   * 
   * Prerequisitos:
	 1. Contar con la versión mas reciente del APK Proveedores directos 
	 2. Contar con un dispositivo móvil con Android 11
   * 
   * @autor Alejandro Valdez
   * @date 10/10/2024
   */

  @Override
  public String setTestFullName() {
  	return "ATC_FT_060_CasoPrueba60";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que al estar posicionado en el menú “Selecciona el tipo de surtido” permita dejar la aplicación en segundo plano "
  			+ "y al regresar a la aplicación continue en el mismo menú, estando en esta misma pantalla se valida que se pueda salir del "
  			+ "menú a la pantalla principal presionando el botón regresar nativo de Android .";
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
  			+ "2. Contar con un dispositivo móvil con Android 11";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
