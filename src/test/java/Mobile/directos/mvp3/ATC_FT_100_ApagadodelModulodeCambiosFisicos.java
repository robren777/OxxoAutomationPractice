package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.Login;
import Mobile.directos.om.mvp3.OAD;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;


public class ATC_FT_100_ApagadodelModulodeCambiosFisicos extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_100_ApagadodelModulodeCambiosFisicos_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
				
		String url = "http://127.0.0.1:4723";

		//=======================================Cerrar Aplicaciones========================================================//
		  Thread.sleep(5000);
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		//==================================================================================================================//
	
		  addStep("Abrir aplicación Mi OXXO al día en HandHeld, seguir el flujo /Excelencia Operativa/Herramienta"
			  		+ "de compra/Cometa/Cambios físicos de productos (Proveedores Directos)");
		  
  	AppiumUtil appiumUtil = null;
  	try {
  		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(3500);
			
//=======================================================================================================//		
  /*
   * Llamado al OM de Login.
   */
		
  Login LoginDirectos = new Login(testCase, appiumUtil, this);
  
  /*
   * Llamado al OM de Oxxo Al Dia.
   */
  OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
  
//=======================================================================================================//
  
  /*
   * Paso 1 -  Abrir aplicación Mi OXXO al día en HandHeld, seguir el flujo /Excelencia Operativa/Herramienta 
   * de compra/Cometa/Cambios físicos de productos (Proveedores Directos)
   */
  
  LoginDirectos.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
  
  OxxoAlDia.ingresarModuloHerramientasOff();
  
  /*
   * Paso 2 - Verificar que se muestren las opciones:

				Recepción de Pedidos CEDIS
				Verificación de Mercancía Pedido CEDIS
				Consultar Tiras de Entrega
				Verificación de Mercancía Pedidos Proveedor Directo
	
	Validar que no se muestren la opción “Cambios Físicos de Producto (Proveedores Directos)”
   */
  
  
  addStep("Verificar que se muestren las opciones:\r\n"
  		+ "\r\n"
  		+ "Recepción de Pedidos CEDIS\r\n"
  		+ "Verificación de Mercancía Pedido CEDIS\r\n"
  		+ "Consultar Tiras de Entrega\r\n"
  		+ "Verificación de Mercancía Pedidos Proveedor Directo"
  		+ "Validar que no se muestren la opción “Cambios Físicos de Producto (Proveedores Directos)”");
  
  boolean validacion = OxxoAlDia.validarPantallaHerramientasCFyDevOff();
  assertTrue(validacion,"La opción de Cambios Fisicos o Devoluciones de Producto se encuentra visible.");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
  
  }
  
  /*
   * Nombre del caso de prueba Manual: MTC-FT-100-Apagado del módulo de “Cambios Físicos” 
   * 
   * Desc: Validar que no se visualice en la aplicación de Oxxo Al Dia el módulo de cambios físicos al ser apagado en central.
   * 
   * Prerequisitos:
	 1. HandHeld
	 2. Usuario y Contraseña de Aplicación.
	 3. Comunicación entre HH y POS.
	 4. Validar con el equipo de Desarrollo que la opción esta deshabilitada en las BD de Movilidad
   * 
   * @autor Alejandro Valdez
   * @date 24/10/2024
   */
  
  @Override
  public String setTestFullName() {
  	return "ATC_FT_098_CasoPrueba90";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que no se visualice en la aplicación de Oxxo Al Dia el módulo de cambios físicos al ser apagado en central.";
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
  	return "1.-Contar con la aplicación más reciente de Oxxo al día \r\n"
  			+ "2.- Validar con el equipo de Desarrollo que la opción esta deshabilitada en las BD de Movilidad";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
