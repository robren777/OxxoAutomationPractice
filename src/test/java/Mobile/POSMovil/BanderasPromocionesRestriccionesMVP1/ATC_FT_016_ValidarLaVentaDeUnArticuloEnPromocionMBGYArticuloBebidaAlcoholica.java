package Mobile.POSMovil.BanderasPromocionesRestriccionesMVP1;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-016-Validar la venta de un articulo En promoción MBG y articulo bebida
*  alcoholica dentro de horario - Efectivo
* DESCRIPCIÓN: Dado que se va a realizar la venta de un articulo en promoción MBG junto con una bebida 
* alcohólica y se encuentra dentro del horario permitido, se deberá realizar la venta con método de pago EFECTIVO
* 
* PRE-REQUISITOS
* 1.- Versión mas actualizada de APK
* 2.- Tener conexión a la bd de la nube
* 3 - Contar con Impresoras Epson y Zebra
* 4 - Encender bandera - PROMOTIONS_FLG
* 5 - Cargar promoción - MBG
* 6 - Realizar la prueba dentro del horario permitido
* 
* INSUMOS:
* N/A
* 
* @autor Cesar Guel
* @date 08/04/2026
*/
//========================================================================//

public class ATC_FT_016_ValidarLaVentaDeUnArticuloEnPromocionMBGYArticuloBebidaAlcoholica extends BaseExecution {
	private String userLogin;
	private String passwordLogin;
	private String appPackage;
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_016_ValidarLaVentaDeUnArticuloEnPromocionMBGYArticuloBebidaAlcoholica_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		this.userLogin = data.get("userLogin");
	    this.passwordLogin = PasswordUtil.decryptPassword(data.get("passwordLogin"));
	    this.appPackage = data.get("appPackage");
	    
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
		
		GlobalVariables.crTiendaD = PM.getCrTienda(data.get("crTienda"));
		String crTienda = GlobalVariables.crTiendaD;
		System.out.println("CR Tienda: " + GlobalVariables.crTiendaD);
		GlobalVariables.nomTiendaD = PM.getNomTiendaD(data.get("nomTienda"));
		System.out.println("Nombre Tienda: " + GlobalVariables.nomTiendaD);
		GlobalVariables.ipPvD = PM.getIpPvD(data.get("ipPv"));
		System.out.println("IP del punto de venta: " + GlobalVariables.ipPvD);

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
  
	//  Realiza la valiación de la hora del dispositvo en caso de que se ejecute dentro del horario de venta de alcohol 
	//  actualiza la hora del dispotivo y del punto de venta
  LoginPosMovil.validarArchivoVPN();
  LoginPosMovil.existePsExe();
  System.out.println("linea de LoginPosMovil.actualizarHoraFueraHorario(data.get(\"crTienda\"));");
  LoginPosMovil.actualizarHoraDentroHorarioD();  
  System.out.println("linea de LoginPosMovil.cambiarHoraPV(data.get(\"crTienda\"), \"dentroHorario\");");
  LoginPosMovil.cambiarHoraPVD("dentroHorario");
  
//================================================================================================================//
  /************************ Paso 1 - Inicio de sesión ***************************/
  addStep("Se inicia con la validación del logeo de la tienda nivel CR plaza y CR tienda");
  LoginPosMovil.verificarLoginTiendaUserCr(crTienda,PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));
  
  /************************ Paso 2 - Se ingresa al Joural ***************************/
  addStep("Se ingresa al Journal");
  boolean ingresoJournal = POSMOVIL.ingresarJournal();
  assertTrue(ingresoJournal,"No se pudo encontrar Journal");
  
  /************************ Paso 3 - Se valida si se encuentra el apartado de servicios ***************************/
  addStep("Se busca el apartado de servicios");
  boolean artPrm = POSMOVIL.ingresarArticuloPrmMBGJournalD();
  assertTrue(artPrm,"No se pudo encontrar articulo en promocion");
  
  /************************ Paso 4 - Se valida si se encuentra el apartado de servicios ***************************/
  addStep("Se ingresa Sku de articulo bebida alcoholica en el buscador ");
  POSMOVIL.ingresarSkuArtBAJournalD();
  
  /************************ Paso 5 - Se valida si se encuentra el apartado de servicios ***************************/
  addStep("Se valida mensaje bebida alcoholica");
  boolean validarMensajeBA = POSMOVIL.busquedaConPresenciaBADE();
  assertTrue(validarMensajeBA,"No se encontro el mensaje esperado");
  
  /************************ Paso 6 - Presionar el boton calcular ***************************/
  addStep("Se presiona el boton calcular");
  POSMOVIL.seleccionarBotonCalcular();
  
  /************************ Paso 7 - Presionar el boton Total a Pagar ***************************/
  addStep("Se presiona el boton total a pagar");
  POSMOVIL.seleccionarBotonTotalPagar();
  
  /************************ Paso 8 - Seleccionar el metodo de pago en efectivo ***************************/
  addStep("Se selecciona el metodo de pago efectivo");
  POSMOVIL.seleccionarMetodoDePagoEfectivo();
  
  /************************ Paso 9 - Terminar la venta con metodo de pago efectivo ***************************/
  addStep("Se termina el pago en efectivo");
  POSMOVIL.confirmarMetodoDePagoEfectivo();
  
  /************************ Paso 10 - Validar que la venta sea exitosa ***************************/
  addStep("Se valida que la venta sea exitosa");
  boolean ventaExitosa = POSMOVIL.validarVentaExitosa();
  assertTrue(ventaExitosa,"La venta realizada no fue exitosa");
  
//=======================================Cerrar Aplicaciones========================================================//
   Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-016-Validar la venta de un articulo En promoción MBG y articulo bebida alcoholica dentro de horario - Efectivo";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que se va a realizar la venta de un articulo en promoción MBG junto con una bebida alcohólica y se encuentra "
  			+ "dentro del horario permitido, se deberá realizar la venta con método de pago EFECTIVO";
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
  			+ "3 - Contar con Impresoras Epson y Zebra"
  			+ "4 - Encender bandera - PROMOTIONS_FLG"
  			+ "5 - Cargar promoción - MBG"
  			+ "6 - Realizar la prueba dentro del horario permitido";
  }
  
  @Override
  public void beforeTest() {
  	
  }  
  
  @AfterTest
	public void afterTest() throws Exception {
//	System.out.println("Realiza la acción del AfterTest");
//	  AppiumUtil appiumUtil = null;
//	  LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);	  
//	  LoginPosMovil.cerrarVPN();
	  }	

  @AfterMethod (alwaysRun = true)
	public void afterMethod() {
		System.out.println("Entra a @AfterMethod");
		AppiumUtil appiumUtil = null;	
		
		try {
			System.out.println("Cierra la aplicación");
			Runtime.getRuntime().exec("adb shell am force-stop \"" + appPackage + "\"");			
		} catch (Exception e) {
	        e.printStackTrace();
	    } 
		
		try {
			LoginPM afterMethodLoginPosMovil = new LoginPM(testCase, appiumUtil, this);
			System.out.println("Cierra la VPN");
			afterMethodLoginPosMovil.cerrarVPN();
				
		} catch (Exception e) {
	        e.printStackTrace();
	    } 	
	}


}
