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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-036-Validar Compra de Productos fast food-Alimento Pago Efectivo
* DESCRIPCIÓN: Al realizar la compra con pago en efectivo de productos fast food-Alimento se realice 
* correctamente 
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
*INSUMOS:
*N/A
* 
* @autor Cesar Guel
* @date 24/01/2025
*/
//========================================================================//

public class ATC_FT_036_ValidarCompraDeProductosFastFoodAlimentoPagoEfectivo extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_036_ValidarCompraDeProductosFastFoodAlimentoPagoEfectivo_test(HashMap<String, String> data) throws Exception {
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
  
  /************************ Paso 4 - Ingresar al apartado FastFood ***************************/
  addStep("Se presiona el foton Fast Food");
  POSMOVIL.ingresarModFastFood();
  
  /************************ Paso 5 - Validar el boton FastFood Alimento ***************************/
  addStep("Se valida que se muestre el boton Alimentos");
  POSMOVIL.validarBotonFFAlimentos();
  
  /************************ Paso 6 - Presionar boton FastFood Alimento ***************************/
  addStep("Se presiona el boton Fast Food Alimentos");
  POSMOVIL.presionarBtnFFAlimentos();
  
  /************************ Paso 7 - Ingresar al catalogo de articulos FastFood Alimentos ***************************/
  addStep("Se ingresa al catalogo de articulos FastFood Alimentos");
  POSMOVIL.ingresarCatFFAli();
  
  /************************ Paso 8 - Validar exista el articulo en el catalogo de FastFood ***************************/
  addStep("Se valida que se muestren productos");
  boolean validarartffa = POSMOVIL.validarArtFFAli();
  assertTrue(validarartffa,"No se encontro el articulo FastFood Alimento");
  
  /************************ Paso 9 - Seleccionar articulo FastFood Alimento ***************************/
  addStep("Se selecciona el producto");
  POSMOVIL.seleccArtFFA();
  
  /************************ Paso 10 - Validar se muestre articulo en el journal ***************************/
  addStep("Se valida que el producto se muestre en el journal");
  boolean valArtJou = POSMOVIL.validarArtJou();
  assertTrue(valArtJou,"No se pudo encontrar el articulo en el journal");
  
  /************************ Paso 11 - Totalizar articulo ***************************/
  addStep("Se totaliza la venta");
  POSMOVIL.totalizarArticulo();
  
  /************************ Paso 12 - Seleccionar metodo de pago efectivo ***************************/
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 13 - Confirmar el metodo de pago efectivo ***************************/
  addStep("Se termina el pago con metodo de pago efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 14 - Validar que la venta sea exitosa ***************************/
  addStep("Se valida que la venta sea exitosa");
  boolean valVenExit = POSMOVIL.validarVentaExitosa();
  assertTrue(valVenExit,"La venta no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//===========================================================================================================//
  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-036-Validar Compra de Productos fast food-Alimento Pago Efectivo";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al realizar la compra con pago en efectivo de productos fast food-Alimento se realice correctamente ";
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
