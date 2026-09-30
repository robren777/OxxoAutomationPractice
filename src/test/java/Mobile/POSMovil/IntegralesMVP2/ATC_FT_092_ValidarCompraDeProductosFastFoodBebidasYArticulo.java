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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-092-Validar Compra De Productos Fast Food Bebidas Y Articulo
* DESCRIPCIÓN: Al realizar la compra con pago en efectivo de productos fast food-bebidas y articulo  se realice
*  correctamente .
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* SKU de articulo
* 
* @autor Irving Ali Orozco
* @date 22/01/2025
*/
//========================================================================//

public class ATC_FT_092_ValidarCompraDeProductosFastFoodBebidasYArticulo extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_092_ValidarCompraDeProductosFastFoodBebidasYArticulo_test(HashMap<String, String> data) throws Exception {
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
		
		String nFF = data.get("nFF");

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
  POSMOVIL.ingresarModFastFood();
  
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
  
  /************************ Paso 8 - Seleccionar Opcion Capuchino ***************************/
  
  addStep("Se selecciona Boton Capchino");
  POSMOVIL.seleccionarCapuchino();
  
  /************************ Paso 9 - Validar se muestren Opciones de Capuchino ***************************/

  addStep("Se muestra opciones de Capuchino");
  boolean opcionCapuchin = POSMOVIL.OpcionCapuchino();
  assertTrue(opcionCapuchin,"No se muestra opciones de Capuchino");
  
  /************************ Paso 10 - Seleccionar opcion de Capuchino ***************************/

  addStep("Se selecciona Boton Capuchino");
  POSMOVIL.seleccionaropcionCapuchino();
  
  
  /************************ Paso 11 - Seleccionar tipo moka ***************************/

  addStep("Se selecciona Boton Moka");
  POSMOVIL.seleccionarCapuchinoMOKA();
  
  /************************ Paso 12 - Validar se muestra los tamaños de bebidas ***************************/

  addStep("Se muestra los tamaños de bebidas");
  boolean MedidaMOKA = POSMOVIL.ValidamedidaBebida();
  assertTrue(MedidaMOKA,"No se muestra los tamaños de bebidas");
  
  /************************ Paso 13 - Seleccionar la medida de la bebida ***************************/

  addStep("Se selecciona Boton 12oz");
  POSMOVIL.seleccionarmedidabebida();
  
  /************************ Paso 14 - Agregar articulo ***************************/
  
  addStep("Se selecciona Boton + ");
  POSMOVIL.agregarART();
  
  /************************ Paso 15 - Presionar Boton Continuar ***************************/

  addStep("Se selecciona Boton Continuar ");
  POSMOVIL.BotonContinuar();
  
  /************************ Paso 16 - Validar se agregue el producto al journal ***************************/
  
  addStep("Valida se agregue articulo");
  boolean ArticuloAgregado = POSMOVIL.articuloJournal();
  assertTrue(ArticuloAgregado,"No se encuentra articulo en Journal");
  
  /************************ Paso 17 - Ingresar el articulo Fast Food  ***************************/
  addStep("Se ingresa el articulo Fast Food");
  POSMOVIL.ingresarSkuArticuloJournal(data.get("ipPv"));
  POSMOVIL.buscarArticulo();
  
 /************************ Paso 18- Seleccionar el articulo ***************************/
  
  addStep("Se selecciona el articulo");
  POSMOVIL.seleccionarArticulo();
  
  /************************ Paso 19 - Seleccionar la cantidad del articulo ***************************/
  
  addStep("Se selecciona la cantidad articulo");
  POSMOVIL.seleccionarCantidadArticulo();
  
  /************************ Paso 20 - Presionar el boton calcular ***************************/
  
  addStep("Se presiona el boton calcular");
  POSMOVIL.seleccionarBotonCalcular();
  
  /************************ Paso 21 - Presionar el boton Total a Pagar ***************************/
  
  addStep("Se presiona el boton total a pagar");
  POSMOVIL.seleccionarBotonTotalPagar();
  
  /************************ Paso 22 - Seleccionar el metodo de pago en efectivo ***************************/
  
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 23 - Terminar la venta con metodo de pago efectivo ***************************/
  
  addStep("Se termina el pago en efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 24 - Validar que la venta sea exitosa ***************************/
  
  addStep("Se valida que la venta sea exitosa");
  boolean ventaExitosa = POSMOVIL.validarVentaExitosa();
  assertTrue(ventaExitosa,"La venta realizada no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-092-Validar Compra De Productos Fast Food Bebidas Y Articulo";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al realizar la compra con pago en efectivo de productos fast food-bebidas y articulo  se realice correctamente .";
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
  	return "1. HandHeld\n"
  			+ "2. Usuario y Contraseña de Aplicación\n"
  			+ "3. Comunicación entre Handheld y POS";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
