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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-033-Validar la venta de un articulo en promocion MBG y con metodo 
*  de pago en efectivo
* DESCRIPCIÓN: Se realizará la venta de un articulo en promocion MBG y con metodo de pago Efectivo por 
* medio del POS Móvil
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Dar de alta las promociones correspóndientes a probar - MBG
* 
* INSUMOS:
* SKU de articulo en promoción MBG
* 
* @autor Cesar Guel
* @date 19/06/2025
*/
//========================================================================//


public class ATC_FT_033_ValidarLaVentaDeUnArticuloEnPromocionMBG extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_033_ValidarLaVentaDeUnArticuloEnPromocionMBG_test(HashMap<String, String> data) throws Exception {
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
  /************************ Paso 2 - Iniciar Sesión ***************************/
  
  addStep("Se inicia con la validación del logeo de la tienda nivel CR plaza y CR tienda");
  LoginPosMovil.verificarLoginTiendaUserCr(data.get("crTienda"),PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));
  
  /************************ Paso 3 - Ingresar al Journal ***************************/
  
  addStep("Se ingresa al Journal");
  boolean inicioJournal = POSMOVIL.ingresarJournal();
  assertTrue(inicioJournal,"No se encontro el Journal");
  
  /************************ Paso 4 - Ingresar el Sku del articulo al buscador ***************************/
  
  addStep("Se ingresa el articulo en promoción MBG correspondiente al Journal");
  POSMOVIL.ingresarSkuArticuloPrmMBG(tienda);
  
  /************************ Paso 5 - Realizar la busqueda del articulo ***************************/
  
  addStep("Se realiza la busqueda del articulo");
  POSMOVIL.buscarArticulo();
  
  /************************ Paso 6 - Seleccionar el articulo ***************************/
  
  addStep("Se selecciona el articulo");
  POSMOVIL.seleccionarArticulo();
  
  /************************ Paso 7 - Seleccionar la cantidad del articulo ***************************/
  
  addStep("Se selecciona la cantidad articulo");
  POSMOVIL.seleccionarCantidadArticulo();
  
  /************************ Paso 8 - Presionar el boton calcular ***************************/
  
  addStep("Se presiona el boton calcular");
  POSMOVIL.seleccionarBotonCalcular();
  
  /************************ Paso 9 - Validar que se encuentre el articulo en promoción ***************************/
  
  addStep("Se valida que el articulo este en promocion");
  boolean validarArtPrm = POSMOVIL.validarArticuloPrmJournal();
  assertTrue(validarArtPrm,"No se encuentra un articulo con promocion en el Journal");
  
  /************************ Paso 10 - Presionar el boton Total a Pagar ***************************/
  
  addStep("Se presiona el boton total a pagar");
  POSMOVIL.seleccionarBotonTotalPagar();
  
  /************************ Paso 11 - Seleccionar el metodo de pago en efectivo ***************************/
  
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 12 - Terminar la venta con metodo de pago efectivo ***************************/
  
  addStep("Se termina el pago en efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 13 - Validar que la venta sea exitosa ***************************/
  
  addStep("Se valida que la venta sea exitosa");
  boolean ventaExitosa = POSMOVIL.validarVentaExitosa();
  assertTrue(ventaExitosa,"La venta realizada no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-033-Validar la venta de un articulo en promocion MBG y con metodo de pago en efectivo";
  }
  
  @Override
  public String setTestDescription() {
  	return "Se realizará la venta de un articulo en promocion MBG y con metodo de pago Efectivo por medio del POS Móvil";
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
  			+ "4-Dar de alta las promociones correspóndientes a probar - MBG";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
