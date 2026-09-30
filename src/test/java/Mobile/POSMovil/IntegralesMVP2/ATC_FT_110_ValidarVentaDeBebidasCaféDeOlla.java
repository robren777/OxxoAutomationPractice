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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-110-Validar Venta De Bebidas Café De Olla
* DESCRIPCIÓN: Al realizar la compra con pago en efectivo de productos fast food-Bebidas se realice correctamente.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Solicitar al equipo de L1 que habiliten el pago con tarjea para los servicios
* 
* INSUMOS:
* Nombre/SKU del articulo FastFood bebidas Cafe de Olla
* 
* @autor Irving Ali
* @date 27/01/2025
*/
//========================================================================//

public class ATC_FT_110_ValidarVentaDeBebidasCaféDeOlla extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_110_ValidarVentaDeBebidasCaféDeOlla_test(HashMap<String, String> data) throws Exception {
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
  
  /************************ Paso 8 - Seleccionar Opcion Americano ***************************/
  
  addStep("Se selecciona Boton Americano");
  POSMOVIL.seleccionarAmericano();
  
  /************************ Paso 9 - Validar se muestren Opciones de Americano ***************************/

  addStep("Se muestra opciones de Americano");
  boolean opcionAME = POSMOVIL.OpcionesAmericano();
  assertTrue(opcionAME,"No se muestra opciones de Americano");
  
  /************************ Paso 10 - Seleccionar opcion de Americano ***************************/

  addStep("Se selecciona Boton Americano Andatti");
  POSMOVIL.seleccionaropcionAmericano();
  
  /************************ Paso 11 - Seleccionar tipo Cafe de Olla ***************************/

  addStep("Se selecciona Boton Cafe de olla");
  POSMOVIL.seleccionarcafeolla();
  
  /************************ Paso 12 - Validar se muestra los tamaños de bebidas ***************************/

  addStep("Se muestra los tamaños de bebidas");
  boolean MedidaMOKA = POSMOVIL.ValidamedidaBebida();
  assertTrue(MedidaMOKA,"No se muestra los tamaños de bebidas");
  
  /************************ Paso 14 - Seleccionar la medida de la bebida ***************************/

  addStep("Se selecciona Boton Media");
  POSMOVIL.seleccionarmedidabebida();
  
  /************************ Paso 15 - Agregar articulo ***************************/
  
  addStep("Se selecciona Boton + ");
  POSMOVIL.agregarART();
  
  /************************ Paso 16 - Presionar Boton Continuar ***************************/

  addStep("Se selecciona Boton Continuar ");
  POSMOVIL.BotonContinuar();
  
  /************************ Paso 17 - Validar se agregue el producto al journal ***************************/
  
  addStep("Valida se agregue articulo");
  boolean ArticuloAgregado = POSMOVIL.articuloJournal();
  assertTrue(ArticuloAgregado,"No se encuentra articulo en Journal");
  
  /************************ Paso 18 - Presionar el boton calcular ***************************/
  
  addStep("Se presiona el boton calcular");
  POSMOVIL.seleccionarBotonCalcular();
  
  /************************ Paso 19 - Presionar el boton Total a Pagar ***************************/
  
  addStep("Se presiona el boton total a pagar");
  POSMOVIL.seleccionarBotonTotalPagar();
  
  /************************ Paso 20 - Seleccionar el metodo de pago en efectivo ***************************/
  
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 21 - Terminar la venta con metodo de pago efectivo ***************************/
  
  addStep("Se termina el pago en efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 22 - Validar que la venta sea exitosa ***************************/
  
  addStep("Se valida que la venta sea exitosa");
  boolean ventaExitosa = POSMOVIL.validarVentaExitosa();
  assertTrue(ventaExitosa,"La venta realizada no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-110-Validar Venta De Bebidas Café De Olla";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al realizar la compra con pago en efectivo de productos fast food-Bebidas se realice correctamente ";
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
  	return "1.- Versión mas actualizada de APK\r\n"
  			+ "2.- Tener conexión a la bd de la nube\r\n"
  			+ "3 - Contar con Impresoras Epson y Zebra\r\n"
  			+ "4 - Solicitar al equipo de L1 que habiliten el pago con tarjea para los servicios";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
