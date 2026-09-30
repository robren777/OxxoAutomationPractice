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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-001-Validar que al activar las promociones correspondientes a probar 
* se realice la carga a la nube y se permita hacer la venta de un articulo en promoción 
* DESCRIPCIÓN: Dado que se activen las promociones correspondientes a las pruebas el componente 
* SaleInjection debera de realizar la carga hacía la nube para posteriormente realizar la prueba desde el POS Movil.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Dar de alta las promociones correspóndientes a probar - Simples
* 
* INSUMOS:
* SKU de articulo en promoción 
* 
* @autor Cesar Guel
* @date 28/10/2024
*/
//========================================================================//

public class ATC_FT_001_ValidarQueAlActivarLasPromocionesCorrespondientes extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_001_ValidarQueAlActivarLasPromocionesCorrespondientes_test(HashMap<String, String> data) throws Exception {
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
				
//	String url = "http://127.0.0.1:4723";
		
		String tienda = data.get("nomTienda");

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
  addStep("Se valida que el monto de la venta se muestre en base de datos");
  boolean validacionVentaExitosa = POSMOVIL.validarVentaExitosaArtPrmNube(data.get("crTienda"),data.get("ipPv"));
  assertTrue(validacionVentaExitosa,"La venta realizada no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
   Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-001-Validar que al activar las promociones correspondientes a probar se realice la carga a la nube y se permita hacer la venta de un articulo en promoción";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que se activen las promociones correspondientes a las pruebas el componente SaleInjection debera de realizar la carga hacía la nube para posteriormente realizar la prueba desde el POS Movil.";
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
  			+ "4-Dar de alta las promociones correspóndientes a probar - Simples";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
