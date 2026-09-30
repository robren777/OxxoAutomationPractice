package Mobile.POSMovil.IntegralesMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.LoginPM;
import Mobile.POSMovil.om.PMCapabilities;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-100-Validar Que Se Muestre El Top De Productos En Fast Food Bebidas
* DESCRIPCIÓN: Validar que se muestra en el catalogo de fast food bebidas el top de productos mas vendidos.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* N/A
* 
* @autor Irving Ali
* @date 22/01/2025
*/
//========================================================================//

public class ATC_FT_100_ValidarQueSeMuestreElTopDeProductosEnFastFoodBebidas extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_100_ValidarQueSeMuestreElTopDeProductosEnFastFoodBebidas_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		//=============================================================================================//
		System.out.println("Cerrando aplicacion si está abierta");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Thread.sleep(2000);
		//=============================================================================================//
		
//		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
//		cap.setCapability("deviceName", data.get("deviceName"));
//		cap.setCapability("platformVersion", data.get("platformVersion"));
//		cap.setCapability("platformName", data.get("platformName"));
//		cap.setCapability("appPackage", data.get("appPackage"));
//		cap.setCapability("appActivity", data.get("appActivity"));
//		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
				
//		String url = "http://127.0.0.1:4723";

/************************ Paso 1 - Ingresar a la App ***************************/
	addStep("Abrir aplicación POS MOVIL en HandHeld.");	
  
  	AppiumUtil appiumUtil = null;
  	try {
  	//appiumUtil = new AppiumUtil(url, cap);
  		appiumUtil = PMCapabilities.initializeAppium("Login");
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(500);
  	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
//=================================================================================================================//		
  /*
   * Llamado al OM de Login de PosMovil.
   */
  LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);
  /*
   * Llamado al OM de PosMovil.
   */
  PM POSMOVIL = new PM(testCase, appiumUtil, this);
  
//================================================================================================================//
  /************************ Paso 2 - Inicio de sesión ***************************/
  addStep("Se inicia con la validación del logeo de la tienda nivel CR plaza y CR tienda");
  LoginPosMovil.verificarLoginTiendaUserCr(data.get("crTienda"),PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));
  
  /************************ Paso 3 - Ingresar al Journal ***************************/
  addStep("Se ingresa al Journal");
  boolean ingresarJournal = POSMOVIL.ingresarJournal();
  assertTrue(ingresarJournal,"Journal no encontrado");
  
  /************************ Paso 4 - Ingresar al Apartado FastFood ***************************/
  
 addStep("Ingresar al apartado de fast food");
 POSMOVIL.seleccionarBotonFastFood();	
 
 /************************ Paso 5 - Validar se muestre en pantalla opciones de fast food ***************************/
 
 addStep("Se muestra opciones de Fast Food");
 boolean ingresaropcionesFF = POSMOVIL.OpcionFastFood();
 assertTrue(ingresaropcionesFF,"No se muestra opciones de Fast Food");
  
  /************************ Paso 6 - Seleccionar Bebidas ***************************/
  
  addStep("Se selecciona Boton Bebidas");
  POSMOVIL.seleccionarBotonBebidas();
  
  /************************ Paso 7 - Validar se muestre en pantalla opciones de bebidas ***************************/
  
  addStep("Se muestra opciones de bebidas");
  boolean ingresaropcionesBEB = POSMOVIL.OpcionFastFoodBebidas();
  assertTrue(ingresaropcionesBEB,"No se muestra opciones de bebidas");
  
  /************************ Paso 8 - Validar que en la parte inferior se muestren los productos mas vendidos ***************************/
  
  addStep("Se muestra productos bebidas mas vendidos");
  boolean BebidaTOP = POSMOVIL.BebidasTOP();
  assertTrue(BebidaTOP,"No se muestra productos mas vendidos bebidas");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-100-Validar Que Se Muestre El Top De Productos En Fast Food Bebidas";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que se muestra en el catalogo de fast food bebidas el top de productos mas vendidos.";
  }  

  @Override
  public String setTestDesigner() {
  	return "Irving Ali";
  }  

  @Override
  public String setTestInstanceID() {
  	return null;  
  }
  
  @Override
  public String setPrerequisites() {
  	return "1-Versión mas actualizada de APK\r\n"
  			+ "2-Tener conexión a la bd de la nube\r\n"
  			+ "3-Contar con Impresoras Epson y Zebra";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
