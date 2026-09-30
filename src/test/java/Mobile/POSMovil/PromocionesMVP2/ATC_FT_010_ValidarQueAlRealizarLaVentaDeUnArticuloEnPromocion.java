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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-010-Validar que al realizar la venta de un articulo en promoción se mu
* estre la información correspondiente a la venta del ticket en la base de datos de la nube
* DESCRIPCIÓN: Dado que se va a realizar la venta de un articulo en promocion en el POS Movil, se debera de 
* reflejar la información correspondiente al ticket de la venta en la base de datos de la nube.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Dar de alta las promociones correspóndientes a probar - Simple
* 
* INSUMOS:
* SKU de articulo en promoción 
* 
* @autor Cesar Guel
* @date 02/11/2024
*/
//========================================================================//

public class ATC_FT_010_ValidarQueAlRealizarLaVentaDeUnArticuloEnPromocion extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_010_ValidarQueAlRealizarLaVentaDeUnArticuloEnPromocion_test(HashMap<String, String> data) throws Exception {
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

	addStep("Iniciar Aplicación");
  
  	AppiumUtil appiumUtil = null;
  	try {
//  		appiumUtil = new AppiumUtil(url, cap);
  		appiumUtil = PMCapabilities.initializeAppium("login");
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(3500);
  	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
//=================================================================================================================//		
  /*
   * Llamado al OM de Login PosMovil.
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
  POSMOVIL.ingresarJournal();
  
  /************************ Paso 4 - Ingresar Sku del articulo en promocion ***************************/
  addStep("Se ingresa el sku del articulo en promoción al Journal");
  POSMOVIL.ingresarSkuArtPrmJournal(tienda);
  
  /************************ Paso 5 - Realizar la busqueda del articulo ***************************/
  
  addStep("Se realiza la busqueda del articulo");
  POSMOVIL.buscarArticulo();
  
  /************************ Paso 6 - Seleccionar articulo ***************************/
  addStep("Se selecciona el articulo correspondiente");
  POSMOVIL.seleccionarArticulo();
  
  /************************ Paso 7 - Seleccionar cantidad de articulos***************************/
  addStep("Se ingresa la cantidad de articulos");
  POSMOVIL.seleccionarCantidadArticulo();
  
  /************************ Paso 8 - Presionar boton calcular ***************************/
  addStep("Se presiona el boton calcular");
  POSMOVIL.seleccionarBotonCalcular();
  
  /************************ Paso 9 - Presionar boton Total a Pagar ***************************/
  addStep("Se presiona el boton Total a Pagar");
  POSMOVIL.seleccionarBotonTotalPagar();
  
  /************************ Paso 10 - Seleccionar metodo de pago efectivo ***************************/
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 11 - Confirmar el metodo de pago efectivo ***************************/
  addStep("Se termina la venta con metodo de pago efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 12 - Validar que la venta exitosa se muestre en bd ***************************/
  addStep("Se valida que la venta sea exitosa");
  boolean ventaExitosa = POSMOVIL.validarVentaExitosa();
  assertTrue(ventaExitosa,"La venta realizada no fue exitosa");
  
  /************************ Paso 13 - Validar que el monto del ticket de la venta es correcto ***************************/
  addStep("Se valida que el monto de la venta se muestre en base de datos");
  boolean validarTicketNube = POSMOVIL.validarArtVendidoNube(data.get("crTienda"));
  assertTrue(validarTicketNube,"El monto de la venta no es correcto");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//===========================================================================================================//
  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-010-Validar que al realizar la venta de un articulo en promoción se muestre la información correspondiente a la venta del ticket en la base de datos de la nube";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que se va a realizar la venta de un articulo en promocion en el POS Movil, se debera de reflejar la información correspondiente al ticket de la venta en la base de datos de la nube.";
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
  	return "1. HandHeld\n"
  			+ "2. Usuario y Contraseña de Aplicación\n"
  			+ "3. Comunicación entre Handheld y POS";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
