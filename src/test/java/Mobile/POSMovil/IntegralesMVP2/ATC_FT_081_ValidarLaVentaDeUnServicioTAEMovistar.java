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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-081-Validar la venta de un servicio TAE Movistar
* DESCRIPCIÓN: Dado que el usuario necesite realizar una recarga de tiempo aire Movistar en efectivo , se 
* deberá realizar el servicio por medio del POS Móvil.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* Telefono de cliente
* 
* @autor Cesar Guel
* @date 28/01/2025
*/
//========================================================================//

public class ATC_FT_081_ValidarLaVentaDeUnServicioTAEMovistar extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_081_ValidarLaVentaDeUnServicioTAEMovistar_test(HashMap<String, String> data) throws Exception {
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
	addStep("Abrir aplicación POS Móvil en HandHeld.");	
  
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
  
  /************************ Paso 4 - Ingresar al apartado de TAE ***************************/
  addStep("Ingresar al apartado de TAE");
  POSMOVIL.ingresarApTAE();
  
  /************************ Paso 5 - Seleccionar el tipo de compañia ***************************/
  addStep("Seleccionar el tipo de compañia");
  POSMOVIL.selecComTaeMovistar();
  
  /************************ Paso 6 - Seleccionar la cantidad de recarga ***************************/
  addStep("Seleccionar cantidad a pagar");
  POSMOVIL.selecRecTaeMovistar();
  
  /************************ Paso 7 - Validar que se agregue al journal ***************************/
  addStep("Validar se agregue al journal");
  boolean valartjou = POSMOVIL.validarArtJou();
  assertTrue(valartjou,"No se pudo agregar al journal");
  
  /************************ Paso 8 - Totalizar articulo ***************************/
  addStep("Se totaliza el articulo");
  POSMOVIL.totalizarArticulo();
  
  /************************ Paso 9 - Seleccionar metodo de pago efectivo ***************************/
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 10 - Confirmar el metodo de pago efectivo ***************************/
  addStep("Se confirma el pago");
  POSMOVIL.confirmarMetodoDePagoEfectivoC();
  
  /************************ Paso 11 - Validar que se muestre la pantalla para ingresar el numero del cliente ***************************/
  addStep("Se valida que se muestre la pantalla de ingreso de numero");
  boolean valpannumcli = POSMOVIL.validarPantIngNumCliente();
  assertTrue(valpannumcli,"No se pudo encontrar la pantalla");
  
  /************************ Paso 12 - Ingresar numero de cliente***************************/
  addStep("Se ingresa numero de telefono");
  POSMOVIL.ingresarNumCliente(data.get("numCliente"));
  
  /************************ Paso 13 - Presionar aceptar***************************/
  addStep("Se presiona aceptar");
  POSMOVIL.confirmarNumCliente();
  
  /************************ Paso 14 - Validar que la recarga sea exitosa***************************/
  addStep("Se valida recarga exitosa");
  boolean valrecexi = POSMOVIL.validarServRecExitosa();
  assertTrue(valrecexi,"La recarga no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  }

  @Override
  public String setTestFullName() {
  	return "MVP2: ATC-FT-081-Validar la venta de un servicio TAE Movistar";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que el usuario necesite realizar una recarga de tiempo aire Movistar en efectivo , se deberá realizar el servicio por medio del POS Móvil.";
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
