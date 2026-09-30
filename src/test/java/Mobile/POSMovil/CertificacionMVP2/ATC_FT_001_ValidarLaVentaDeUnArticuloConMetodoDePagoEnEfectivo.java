package Mobile.POSMovil.CertificacionMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.LoginPM;
import Mobile.POSMovil.om.PMCapabilities;
import Mobile.util.GlobalVariables;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-001-Validar la venta de un articulo con metodo de pago en efectivo
* DESCRIPCIÓN: Se realizará la venta de un articulo con metodo de pago Efectivo por medio del POS Móvil.
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
* @date 08/09/2024
*/
//========================================================================//

public class ATC_FT_001_ValidarLaVentaDeUnArticuloConMetodoDePagoEnEfectivo extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_001_ValidarLaVentaDeUnArticuloConMetodoDePagoEnEfectivo_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		//=============================================================================================//
		System.out.println("Cerrando aplicacion si está abierta");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Thread.sleep(2000);
		//=============================================================================================//
		
//		cap.setcapability("appium:automationname", automationname.android_uiautomator2);
//		cap.setcapability("devicename", data.get("devicename"));
//		cap.setcapability("platformversion", data.get("platformversion"));
//		cap.setcapability("platformname", data.get("platformname"));
//		cap.setcapability("apppackage", data.get("apppackage"));
//		cap.setcapability("appactivity", data.get("appactivity"));
//		cap.setcapability("noreset", true); // se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la ip y puerto
//				
//		string url = "http://127.0.0.1:4723";
		
		GlobalVariables.crTiendaD = PM.getCrTienda(data.get("crTienda"));
		System.out.println("CR Tienda: " + GlobalVariables.crTiendaD);
		GlobalVariables.nomTiendaD = PM.getNomTiendaD(data.get("nomTienda"));
		System.out.println("Nombre Tienda: " + GlobalVariables.nomTiendaD);
		GlobalVariables.ipPvD = PM.getIpPvD(data.get("ipPv"));
		System.out.println("IP del punto de venta: " + GlobalVariables.ipPvD);
		
		/************************ Paso 1 - Ingresar a la App ***************************/
		addStep("Iniciar Aplicación POS Móvil");

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
  LoginPosMovil.verificarLoginTiendaUserCrV2D(data.get("salInic"));
  
  /************************ Paso 3 - Ingresar al Journal ***************************/
  addStep("Se ingresa al Journal");
  boolean ingresarJournal = POSMOVIL.ingresarJournal();
  assertTrue(ingresarJournal,"Journal no encontrado");
  
  /************************ Paso 4 - Ingresar Sku del articulo ***************************/
  addStep("Se ingresa el sku del articulo correspondiente al buscador");
  POSMOVIL.ingresarSkuArticuloJournalD();
  
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
  
  /************************ Paso 9 - Presionar el boton Total a Pagar ***************************/
  
  addStep("Se presiona el boton total a pagar");
  POSMOVIL.seleccionarBotonTotalPagar();
  
  /************************ Paso 10 - Seleccionar el metodo de pago en efectivo ***************************/
  
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 11 - Terminar la venta con metodo de pago efectivo ***************************/
  
  addStep("Se termina el pago en efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 12 - Validar que la venta sea exitosa ***************************/
  addStep("Se valida que la venta sea exitosa");
  boolean  ventaExitosa = POSMOVIL.validarVentaExitosa();
  assertTrue(ventaExitosa,"La venta realizada no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-001-Validar la venta de un articulo con metodo de pago en efectivo";
  }
  
  @Override
  public String setTestDescription() {
  	return "Se realizará la venta de un articulo con metodo de pago Efectivo por medio del POS Móvil.";
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
