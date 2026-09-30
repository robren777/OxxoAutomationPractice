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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-089-Validar Compra de Productos fast food-Alimento y articulo 
* DESCRIPCIÓN: Al realizar la compra con pago en efectivo de productos fast food-Alimento y articulo  se 
* realice correctamente 
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* SKU de articulo
* 
* @autor Cesar Guel
* @date 29/01/2025
*/
//========================================================================//

public class ATC_FT_089_ValidarCompraDeProductosFastFoodAlimentoYArticulo extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_089_ValidarCompraDeProductosFastFoodAlimentoYArticulo_test(HashMap<String, String> data) throws Exception {
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
  
  /************************ Paso 4 - Ingresar al modulo de FastFood ***************************/
  addStep("Se Ingresa al modulo de FastFood");
  POSMOVIL.ingresarModFastFood();
  
  /************************ Paso 5 - Validar que se muestre el boton FastFood Alimento ***************************/
  addStep("Se valida que se muestre el boton FastFood Alimento");
  boolean valbtnffa =  POSMOVIL.validarBotonFFAlimentos();
  assertTrue(valbtnffa,"No se encontro el boton FastFood Alimentos");
  
  /************************ Paso 6 - Presionar boton FastFood Alimento ***************************/
  addStep("Se presiona el boton FastFood Alimento");
  POSMOVIL.presionarBtnFFAlimentos();
  
  /************************ Paso 7 - Validar que se muestren los productos ***************************/
  addStep("Se valida que se muestren productos");
  boolean valArtcatccffa = POSMOVIL.validarArtCatCCFFA();
  assertTrue(valArtcatccffa,"No se pudieron encontrar los articulos");
  
  /************************ Paso 8 - Seleccionar Producto ***************************/
  addStep("Se selecciona producto");
  POSMOVIL.seleccArtFFA();
  
  /************************ Paso 9 - Validar que se agregue el producto al journal ***************************/
  addStep("Se Valida que se agrege el producto");
  boolean valartjou = POSMOVIL.validarArtJou();
  assertTrue(valartjou,"No se pudo visualizar el articulo en el journal");
  
  /************************ Paso 10 - Agregar articulo al journal ***************************/
  addStep("Se agrega articulo en el journal");
  POSMOVIL.ingresarArtJournal(data.get("ipPv"));
  
  /************************ Paso 11 - Totalizar la venta ***************************/
  addStep("Se totaliza la venta");
  POSMOVIL.totalizarArticulo();
  
  /************************ Paso 12 - Seleccionar el metodo de pago efectivo ***************************/
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 13 - Confirmar venta***************************/
  addStep("Se confirma la venta");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 14 - Validar mensaje de venta exitosa ***************************/
  addStep("Se valida mensaje de venta exitosa");
  boolean valvtaexi = POSMOVIL.validarVentaExitosa();
  assertTrue(valvtaexi,"La venta no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-089-Validar Compra de Productos fast food-Alimento y articulo ";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al realizar la compra con pago en efectivo de productos fast food-Alimento y articulo  se realice correctamente ";
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
