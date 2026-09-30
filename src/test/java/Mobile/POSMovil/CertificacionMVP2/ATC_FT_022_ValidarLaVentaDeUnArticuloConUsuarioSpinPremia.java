package Mobile.POSMovil.CertificacionMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.PMCapabilities;
import Mobile.util.GlobalVariables;
import Mobile.POSMovil.om.LoginPM;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-022-Validar la venta de un artículo con usuario Spin Premia
* DESCRIPCIÓN: Dado que se va a realizar la venta de un artículo y el usuario accede como usuario Spin Premia
* , se pueda realizar la venta correctamente
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* SKU de articulo
* Telefono de usuario Spin Premia
* 
* @autor Cesar Guel
* @date 31/01/2025
*/
//========================================================================//

public class ATC_FT_022_ValidarLaVentaDeUnArticuloConUsuarioSpinPremia extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_022_ValidarLaVentaDeUnArticuloConUsuarioSpinPremia_test(HashMap<String, String> data) throws Exception {
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
		
		GlobalVariables.crTiendaD = PM.getCrTienda(data.get("crTienda"));
		System.out.println("CR Tienda: " + GlobalVariables.crTiendaD);
		GlobalVariables.nomTiendaD = PM.getNomTiendaD(data.get("nomTienda"));
		System.out.println("Nombre Tienda: " + GlobalVariables.nomTiendaD);
		GlobalVariables.ipPvD = PM.getIpPvD(data.get("ipPv"));
		System.out.println("IP del punto de venta: " + GlobalVariables.ipPvD);

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
  
//Cambiar la URL de coalición si es que no tiene la del simulador
  POSMOVIL.cambioUrlCoal(data.get("UrlCoal"));
  
//================================================================================================================//
  /************************ Paso 2 - Inicio de sesión ***************************/
  addStep("Se inicia con la validación del logeo de la tienda nivel CR plaza y CR tienda");
  LoginPosMovil.verificarLoginTiendaUserCrV2D(data.get("salInic"));
  
  /************************ Paso 3 - Ingresar al Journal ***************************/
  addStep("Se ingresa al Journal");
  boolean ingresarJournal = POSMOVIL.ingresarJournal();
  assertTrue(ingresarJournal,"Journal no encontrado");
  
  /************************ Paso 4 - Ingresar sku de articulo al Journal ***************************/
  
  addStep("Se ingresa el Sku del articulo en el buscador");
  POSMOVIL.ingresarSkuArticuloJournalD();
  
  /************************ Paso 5 - Buscar articulo ***************************/
  
  addStep("Se realiza la busqueda del articulo");
  POSMOVIL.buscarArticulo();
  
  /************************ Paso 6 - Seleccionar articulo ***************************/
  
  addStep("Se selecciona el articulo");
  POSMOVIL.seleccionarArticulo();
  
  /************************ Paso 7 - Seleccionar cantidad de articulos ***************************/
  
  addStep("Se selecciona la cantidad de articulos");
  POSMOVIL.seleccionarCantidadArticulo();
  
  /************************ Paso 8 - Presionar el boton calcular ***************************/
  
  addStep("Se presiona el boton calcular");
  POSMOVIL.seleccionarBotonCalcular();
  
  /************************ Paso 9 - Presionar el boton Total a Pagar ***************************/
  
  addStep("Se presiona el boton total a pagar");
  POSMOVIL.seleccionarBotonTotalPagar();
  
  /************************ Paso 10 - Presionar boton Identificar cliente Spin Premia ***************************/
  
  addStep("Se presiona el boton identificar cliente");
  POSMOVIL.seleccionarBotonSpinPremiaNT();
  
  /************************ Paso 11 - Ingresar numero de telefono ***************************/
  
  addStep("Se identifica el usuario Spin Premia por numero de telefono");
  POSMOVIL.identificacionSpinNT(data.get("telefono"));
  
  /************************ Paso 12 - Seleccionar metodo de pago efectivo ***************************/
   
  addStep("Se termina la venta del articulo con usuario Spin Premia por numero de telefono");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 13 - Terminar la venta con metodo de pago efectivo ***************************/
  
  addStep("Se termina el pago en efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 14 - Validar que la venta sea exitosa ***************************/
  
  addStep("Se valida que la venta sea exitosa");
  boolean ventaExitosa = POSMOVIL.validarVentaExitosa();
  assertTrue(ventaExitosa,"La venta realizada no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//============================================================================================================//
  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-022-Validar la venta de un artículo con usuario Spin Premia";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que se va a realizar la venta de un artículo y el usuario accede como usuario Spin Premia, se pueda realizar la venta correctamente";
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
  	return "1.- Versión mas actualizada de APK"
  			+ "2.- Tener conexión a la bd de la nube"
  			+ "3 - Contar con Impresoras Epson y Zebra";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
