package Mobile.POSMovil.PromocionesMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.PMCapabilities;
import Mobile.POSMovil.om.LoginPM;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;


//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-046-Validar que al subir una promoción a la nube, esta se aplique 
* correctamente sin generar duplicaciones en el POS Móvil
* DESCRIPCIÓN: Al realizar la carga de una promoción en la nube, esta se deberá aplicar correctamente a los 
* productos sin generar duplicaciones.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Dar de alta las promociones correspóndientes a probar 
* 
* INSUMOS:
* CR de la tienda
* 
* @autor Cesar Guel
* @date 19/06/2025
*/
//========================================================================//

public class ATC_FT_046_ValidarQueAlSubirUnaPromocionALaNubeEstaSeAplique extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_046_ValidarQueAlSubirUnaPromocionALaNubeEstaSeAplique_test(HashMap<String, String> data) throws Exception {
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
//				
//		String url = "http://127.0.0.1:4723";
		
		String tienda = data.get("nomTienda");

/************************ Paso 1 - Ingresar a la App ***************************/
	addStep("Abrir aplicación POS Móvil en HandHeld.");	
  
  	AppiumUtil appiumUtil = null;
  	try {
//  		appiumUtil = new AppiumUtil(url, cap);
  		appiumUtil = PMCapabilities.initializeAppium("login");
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
  
  /************************ Paso 3 - ingresar al Journaln ***************************/
  addStep("Se ingresa al Journal");
  boolean validarJournal = POSMOVIL.ingresarJournal();
  assertTrue(validarJournal,"No se encontro Journal");
  
  /************************ Paso 4 - Ingresar Sku del articulo en promocion ***************************/
  addStep("Se ingresa el sku del articulo en promoción al Journal");
  POSMOVIL.ingresarArticuloPrmJournal(tienda);
  
  /************************ Paso 5 - Presionar boton calcular ***************************/
  addStep("Se presiona el boton calcular");
  POSMOVIL.seleccionarBotonCalcular();
  
  /************************ Paso 6 - Presionar boton Total a Pagar ***************************/
  addStep("Se presiona el boton Total a Pagar");
  POSMOVIL.seleccionarBotonTotalPagar();
  
  /************************ Paso 7 - Seleccionar metodo de pago efectivo ***************************/
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 8 - Confirmar el metodo de pago efectivo ***************************/
  addStep("Se termina la venta con metodo de pago efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 9 - Validar que la venta sea exitosa ***************************/
  addStep("Se valida que el monto de la venta se se muestre en base de datos");
  boolean validacionVentaExitosa = POSMOVIL.validarVentaExitosa();
  assertTrue(validacionVentaExitosa,"La venta realizada no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-046-Validar que al subir una promoción a la nube, esta se aplique correctamente sin generar duplicaciones en el POS Móvil";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al realizar la carga de una promoción en la nube, esta se deberá aplicar correctamente a los productos sin generar duplicaciones.";
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
  			+ "3 - Contar con Impresoras Epson y Zebra\r\n"
  			+ "4-Dar de alta las promociones correspóndientes a probar ";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
