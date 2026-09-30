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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-098-Validar La Venta De Un Servicio Telcel Con Top Bebidas
* DESCRIPCIÓN: Dado que el usuario necesite realizar una recarga de tiempo aire, se deberá realizar el servicio
*  por medio del POS Móvil.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* Telefono del cliente
* 
* @autor Irving Ali
* @date 12/06/2025
*/
//========================================================================//

public class ATC_FT_098_ValidarLaVentaDeUnServicioTelcelConTopBebidas extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_098_ValidarLaVentaDeUnServicioTelcelConTopBebidas_test(HashMap<String, String> data) throws Exception {
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
  
 /************************ Paso 4 -  Ingresar al apartado de TAE ***************************/
  
  addStep("Se selecciona Boton TAE");
  POSMOVIL.seleccionarBotonTAE();
  
  /************************ Paso 5 -  Validar se muestren las diferentes compañias ***************************/
  addStep("Se valida diferentes compañias TAE");
  boolean ingresarTAE = POSMOVIL.ingresarTAE();
  assertTrue(ingresarTAE,"No se muestra las diferentes compañias");
  
 /************************ Paso 4 -  Ingresar a Telcel ***************************/
  
  addStep("Se selecciona Boton Telcel");
  POSMOVIL.seleccionarBotonTelcel();
   
 /************************ Paso 6 -  Ingresar a Telcel TAE 30 ***************************/
  
  addStep("Se selecciona Boton TAE TELCEL 30");
  POSMOVIL.seleccionarBotonTelcelTAE30();
  
 /************************ Paso 7 - Validar se agregue el producto al journal ***************************/
  
  addStep("Valida se agregue articulo");
  boolean ArticuloAgregado = POSMOVIL.articuloJournal();
  assertTrue(ArticuloAgregado,"No se encuentra articulo en Journal");
  
  /************************ Paso 8 - Ingresar al Apartado FastFood ***************************/
  
 addStep("Ingresar al apartado de fast food");
 POSMOVIL.seleccionarBotonFastFood();
 
 /************************ Paso 9 - Validar se muestre en pantalla opciones de fast food ***************************/
 
 addStep("Se muestra opciones de Fast Food");
 boolean ingresaropcionesFF = POSMOVIL.OpcionFastFood();
 assertTrue(ingresaropcionesFF,"No se muestra opciones de Fast Food");
  
  /************************ Paso 10 - Seleccionar Bebidas ***************************/
  
  addStep("Se selecciona Boton Bebidas");
  POSMOVIL.seleccionarBotonBebidas();
  
  /************************ Paso 11 - Validar se muestre en pantalla opciones de bebidas ***************************/
  
  addStep("Se muestra opciones de bebidas");
  boolean ingresaropcionesBEB = POSMOVIL.OpcionFastFoodBebidas();
  assertTrue(ingresaropcionesBEB,"No se muestra opciones de bebidas");
  
  /************************ Paso 12 - Validar que en la parte inferior se muestren los productos mas vendidos ***************************/
  
  addStep("Se muestra productos bebidas mas vendidos");
  boolean BebidaTOP = POSMOVIL.BebidasTOP();
  assertTrue(BebidaTOP,"No se muestra productos mas vendidos bebidas");
  
  /************************ Paso 13 - Seleccionar Boton + Bebida TOP ***************************/
  
  addStep("Se selecciona Boton + Bebida TOP");
  POSMOVIL.agregartopbebida();
  
  /************************ Paso 14 - Validar se agregue el producto al journal ***************************/
  
  addStep("Valida se agregue articulo");
  boolean articulotopBebida = POSMOVIL.articuloJournal();
  assertTrue(articulotopBebida,"No se encuentra articulo en Journal");
  
/************************ Paso 15 - Presionar el boton calcular ***************************/
  
  addStep("Se presiona el boton calcular");
  POSMOVIL.seleccionarBotonCalcular();
  
  /************************ Paso 16 - Presionar el boton Total a Pagar ***************************/
  
  addStep("Se presiona el boton total a pagar");
  POSMOVIL.seleccionarBotonTotalPagar();
  
 /************************ Paso 17 - Seleccionar el metodo de pago en efectivo ***************************/
  
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 18 - Terminar la venta con metodo de pago efectivo ***************************/
  
  addStep("Se termina el pago en efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
 /************************ Paso 19 - Validar se muestre pantalla de ingreso de numero de cliente ***************************/
  
  addStep("Valida se muestre pantalla de ingreso de numero de cliente");
  boolean PantallaTAE = POSMOVIL.validarPantIngNumCliente();
  assertTrue(PantallaTAE,"NO se muestra pantalla de ingreso de numero de cliente");
  
 /************************ Paso 20 - Ingresar numero de cliente ***************************/
  
  addStep("Ingresar numero de cliente");
  POSMOVIL.ingresarNumCliente(data.get("numCliente"));
  
 /************************ Paso 21 - Confirmar numero de cliente ***************************/
  
  addStep("Confirmar numero de cliente");
  POSMOVIL.confirmarNumCliente();
  
 /************************ Paso 22 - Validar Recarga exitosa ***************************/
  
  addStep("Validar Recarga exitosa");
  boolean RecargaTAE = POSMOVIL.validarServRecExitosa();
  assertTrue(RecargaTAE,"No se realiza recarga exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-098-Validar La Venta De Un Servicio Telcel Con Top Bebidas";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que el usuario necesite realizar una recarga de tiempo aire, se deberá realizar el servicio por medio del POS Móvil.";
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
  	return "1-Versión mas actualizada de APK\r\n"
  			+ "2-Tener conexión a la bd de la nube\r\n"
  			+ "3-Contar con Impresoras Epson y Zebra";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
