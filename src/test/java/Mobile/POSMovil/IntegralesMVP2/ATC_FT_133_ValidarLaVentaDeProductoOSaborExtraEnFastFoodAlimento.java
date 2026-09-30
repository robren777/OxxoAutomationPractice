package Mobile.POSMovil.IntegralesMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.LoginPM;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;


//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-133-Validar La Venta De Producto OSabor Extra En Fast Food Alimento
* DESCRIPCIÓN: Validar que se realice la venta correctamente de un producto en fast food Alimento.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Solicitar al equipo de L1 que habiliten el pago con tarjea para los servicios
* 
* INSUMOS:
* Nombre/SKU de articulo FastFood O Sabor Extra
* 
* @autor Irving Ali Orozco
* @date 12/06/2025
*/
//========================================================================//

public class ATC_FT_133_ValidarLaVentaDeProductoOSaborExtraEnFastFoodAlimento extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_133_ValidarLaVentaDeProductoOSaborExtraEnFastFoodAlimento_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		//=============================================================================================//
		System.out.println("Cerrando aplicacion si está abierta");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Thread.sleep(2000);
		//=============================================================================================//
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
//		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
				
		String url = "http://127.0.0.1:4723";

/************************ Paso 1 - Ingresar a la App ***************************/
	addStep("Abrir aplicación POS MOVIL en HandHeld.");	
  
  	AppiumUtil appiumUtil = null;
  	try {
  		appiumUtil = new AppiumUtil(url, cap);
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

  /************************ Paso 6 - Seleccionar Alimentos ***************************/
  
  addStep("Se selecciona Boton Alimentos");
  POSMOVIL.seleccionarBotonAlimentos();
  
  /************************ Paso 7 - Seleccionar Comida Caliente ***************************/
  
  addStep("Se selecciona Boton Comida Caliente");
  POSMOVIL.seleccionarComidaCaliente();
  
  /************************ Paso 8 - Validar se muestren las opciones de comida caliente ***************************/
  
  addStep("Se muestra opciones de Comida Caliente");
  boolean ComidaCaliente = POSMOVIL.OpcionesComidaCaliente();
  assertTrue(ComidaCaliente,"No se muestra opciones de Comida Caliente");
  
 /************************ Paso 9 - Seleccionar Osabor ***************************/
  
  addStep("Se selecciona Boton Osabor");
  POSMOVIL.seleccionarOsabor();
  
  /************************ Paso 10 - Validar se muestren las opciones de Osabor ***************************/
  
  addStep("Se muestra opciones de Comida Osabor");
  boolean ComidaOSABOR = POSMOVIL.OpcionesComidaOsabor();
  assertTrue(ComidaOSABOR,"No se muestra opciones de Comida Osabor");
  
/************************ Paso 11 - Seleccionar Otros ***************************/
  
  addStep("Se selecciona Boton Otros");
  POSMOVIL.seleccionarOOtros();
  
  /************************ Paso 12 - Seleccionar Extras ***************************/
  
  addStep("Se selecciona Extras");
  POSMOVIL.seleccionarExtras();
  
  /************************ Paso 13 - Agregar articulo ***************************/
  
  addStep("Se selecciona Boton + ");
  POSMOVIL.agregarART();
  
  /************************ Paso 14 - Presionar Boton Continuar ***************************/

  addStep("Se selecciona Boton Continuar ");
  POSMOVIL.BotonContinuar();
  
  /************************ Paso 15 - Validar se agregue el producto al journal ***************************/
  
  addStep("Valida se agregue articulo");
  boolean ArticuloAgregado = POSMOVIL.articuloJournal();
  assertTrue(ArticuloAgregado,"No se encuentra articulo en Journal");
  
  /************************ Paso 16 - Presionar el boton calcular ***************************/
  
  addStep("Se presiona el boton calcular");
  POSMOVIL.seleccionarBotonCalcular();
  
  /************************ Paso 17 - Presionar el boton Total a Pagar ***************************/
  
  addStep("Se presiona el boton total a pagar");
  POSMOVIL.seleccionarBotonTotalPagar();
  
  /************************ Paso 18 - Seleccionar el metodo de pago en efectivo ***************************/
  
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 19 - Terminar la venta con metodo de pago efectivo ***************************/
  
  addStep("Se termina el pago en efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 20 - Validar que la venta sea exitosa ***************************/
  
  addStep("Se valida que la venta sea exitosa");
  boolean ventaExitosa = POSMOVIL.validarVentaExitosa();
  assertTrue(ventaExitosa,"La venta realizada no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-133-Validar La Venta De Producto OSabor Extra En Fast Food Alimento";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que se realice la venta correctamente de un producto en fast food Alimento.";
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
