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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-124-Validar Venta de Andatti Gourmet Caliente
* DESCRIPCIÓN: Al realizar la compra con pago en efectivo de productos fast food-Bebidas se realice correctamente.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Solicitar al equipo de L1 que habiliten el pago con tarjea para los servicios
* 
* INSUMOS:
* Nombre/SKU de articulo FastFood Andatti Gourmet Caliente
* 
* @autor Cesar Delgado
* @date 01/08/2025
*/
//========================================================================//

public class ATC_FT_124_ValidarVentaDeAndattiGourmetCaliente extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_124_ValidarVentaDeAndattiGourmetCaliente_test(HashMap<String, String> data) throws Exception {
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
		
		String crTienda = data.get("crTienda");

	/************************ Paso 1 - Ingresar a la App ***************************/
	addStep("Iniciar Aplicación POS Móvil");
  
  	AppiumUtil appiumUtil = null;
  	try {
  	//appiumUtil = new AppiumUtil(url, cap);
  		appiumUtil = PMCapabilities.initializeAppium("Login");
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(3500); //Tiempo de Espera
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
  boolean validarJournal =  POSMOVIL.ingresarJournal();
  assertTrue(validarJournal,"No se encontro Journal");
  
  /************************ Paso 4 - Ingresar al apartado Fast Food ***************************/
  
  addStep("Ingresar al apartado de fast food");
  POSMOVIL.seleccionarBotonFastFood();
  
  /************************ Paso 5 - Validar se muestre en pantalla opciones de Fast Food***************************/
  
  addStep("Se muestra opciones de Fast Food");
  boolean ingresaropcionesFF = POSMOVIL.OpcionFastFood();
  assertTrue(ingresaropcionesFF,"No se muestra opciones de Fast Food");
  
  /************************ Paso 6 - Seleccionar Bebidas ***************************/
  
  addStep("Se selecciona Boton Bebidas");
  POSMOVIL.seleccionarBotonBebidas();
  
  /************************ Paso 7 - Validar se muestre en pantalla opciones de bebidas ***************************/
  addStep("Se muestra opciones de Bebidas");
  boolean ingresaropcionesBB = POSMOVIL.OpcionFastFoodBebidas();
  assertTrue(ingresaropcionesBB,"No se muestra opciones de Fast Food");
  
  /************************ Paso 8 - Seleccionar Capuchino ***************************/
  
  addStep("Se selecciona Boton Capuchino");
  POSMOVIL.seleccionarCapuchino();
  
  /************************ Paso 9 - Validar se muestre en pantalla opciones de bebidas ***************************/
  addStep("Se muestra opciones de Capchino");
  boolean opcionCapuchino = POSMOVIL.OpcionesDeCapuchino();
  assertTrue(opcionCapuchino,"No se muestra opciones de Fast Food");
   
  /************************ Paso 10 - Seleccionar Capuchino Andatti ***************************/
  
  addStep("Se selecciona Boton Capuchino Andatti ");
  POSMOVIL.seleccionaropcionCapuchino();
  
  /************************ Paso 11 - Seleccionar tipo vainilla ***************************/
  
  addStep("Se selecciona Boton vainilla");
  POSMOVIL.seleccionarCapuchinoVainilla();
  
  /************************ Paso 12 - Validar se muestre tamaño ***************************/
  
  addStep("Se muestran los tamaños de bebidas");
  boolean MedidaVainilla = POSMOVIL.ValidamedidaBebida();
  assertTrue(MedidaVainilla,"No se muestra tamaños de bebidas");
    
  /************************ Paso 13 - Seleccionar tamaño ***************************/
  
  addStep("Se selecciona tamaño de medida");
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
  
  /************************ Paso 17 - Presionar boton calcular ***************************/
  addStep("Se presiona el boton calcular");
  POSMOVIL.seleccionarBotonCalcular();
  
  /************************ Paso 18 - Presionar boton Total a Pagar ***************************/
  addStep("Se presiona el boton Total a Pagar");
  POSMOVIL.seleccionarBotonTotalPagar();
  
  /************************ Paso 19 - Seleccionar metodo de pago efectivo ***************************/
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 20 - Confirmar el metodo de pago efectivo ***************************/
  addStep("Se termina la venta con metodo de pago efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 21 - Validar que la venta exitosa se muestre en bd ***************************/
  addStep("Se valida que el monto de la venta se se muestre en base de datos");
  boolean validacionVentaExitosa =  POSMOVIL.validarVentaExitosa();
  assertTrue(validacionVentaExitosa,"La venta realizada no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-124-Validar Venta de Andatti Gourmet Caliente";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al realizar la compra con pago en efectivo de productos fast food-Bebidas se realice correctamente.";
  }  

  @Override
  public String setTestDesigner() {
  	return "Cesar Delgado";
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
