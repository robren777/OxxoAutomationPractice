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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-052-Validar la venta de un servicio TAE-Efectivo
* DESCRIPCIÓN: Dado que el usuario necesite realizar una recarga de tiempo aire en efectivo, se deberá realizar 
* el servicio por medio del POS Móvil.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* Telefono de cliente TAE
* 
* @autor Cesar Guel
* @date 27/01/2025
*/
//========================================================================//

public class ATC_FT_052_ValidarLaVentaDeUnServicioTAEEfectivo extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_052_ValidarLaVentaDeUnServicioTAEEfectivo_test(HashMap<String, String> data) throws Exception {
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
	addStep("Abrir aplicación POSMOVIL en HandHeld.");	
  
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
  
  /************************ Paso 4 - Ingresar al apartado TAE***************************/
  addStep("Se Ingresa al apartado de TAE");
  POSMOVIL.ingresarApTAE();
  
  /************************ Paso 5 - Seleccionar tipo de servicio TAE ***************************/
  addStep("Se selecciona el tipo de servicio TAE");
  POSMOVIL.seleccionarComTAE();
  
  /************************ Paso 6 - Totalizar venta***************************/
  addStep("Se totaliza la vanta");
  POSMOVIL.totalizarArticulo();
  
  /************************ Paso 7 - Seleccionar metodo de pago efectivo ***************************/
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 8 - Confirmar Pago en efectivo ***************************/
  addStep("Se confirma el pago con efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 9 - Ingresar numero de cliente ***************************/
  addStep("Se ingresa el numero del cliente");
  POSMOVIL.ingresarNumCliente(data.get("numCliente"));
  
  /************************ Paso 10 - Confirmar numero de cliente ***************************/
  addStep("Se confirma el numero del cliente");
  POSMOVIL.confirmarNumCliente();
  
  /************************ Paso 11 - Validar que la recarga sea exitosa ***************************/
  addStep("Se valida que la recarga sea exitosa");
  boolean validarTaeExi = POSMOVIL.validarServRecExitosa();
  assertTrue(validarTaeExi,"La recarga no fue exitosa");

//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-052-Validar la venta de un servicio TAE-Efectivo";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que el usuario necesite realizar una recarga de tiempo aire en efectivo, se deberá realizar el servicio por medio del POS Móvil.";
  }  

  @Override
  public String setTestDesigner() {
  	return "Cesar Guel";
  }  

  @Override
  public String setTestInstanceID() {
  	return null;  
  }
  
  @Override
  public String setPrerequisites() {
  	return "1.- Versión mas actualizada de APK\r\n"
  			+ "2.- Tener conexión a la bd de la nube\r\n"
  			+ "3 - Contar con Impresoras Epson y Zebra";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
