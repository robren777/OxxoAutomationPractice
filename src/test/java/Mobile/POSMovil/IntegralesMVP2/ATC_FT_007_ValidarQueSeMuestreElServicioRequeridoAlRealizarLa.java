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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-007-Validar que se muestre el servicio requerido al realizar la busqueda por medio de Nombre
* DESCRIPCIÓN: Al ingresar al apartado de servicios y realizar la busqueda del servicio por medio de Nombre, se deberá mostrar
* el servicio correspondiente.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Solicitar al equipo de L1 que habiliten el pago con tarjea para los servicios
* 
* INSUMOS:
* Nombre del servicio
* 
* @autor Cesar Guel
* @date 22/01/2025
*/
//========================================================================//

public class ATC_FT_007_ValidarQueSeMuestreElServicioRequeridoAlRealizarLa extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_007_ValidarQueSeMuestreElServicioRequeridoAlRealizarLa_test(HashMap<String, String> data) throws Exception {
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
  
  /************************ Paso 4 - Ingresar al modulo de servicios ***************************/
  addStep("Se ingresa al modulo de servicios");
  POSMOVIL.ingresarModServ();
  
  /************************ Paso 7 - Ingresar el nombre del servicio ***************************/
  addStep("Se ingresa el nombre del servicio");
  POSMOVIL.seleccionarServicioPB(data.get("servicio")); 
  
  /************************ Paso 8 - validar que se encuentre el servicio ***************************/
  addStep("Se valida que se encontro el servicio");
  boolean validarServBus = POSMOVIL.validarServCons();
  assertTrue(validarServBus,"No se pudo encontrar el servicio buscado");
  
//=======================================Cerrar Aplicaciones========================================================//
   Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-007-Validar que se muestre el servicio requerido al realizar la busqueda por medio de Nombre";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al ingresar al apartado de servicios y realizar la busqueda del servicio por medio de Nombre, se deberá mostrar\r\n"
  			+ " el servicio correspondiente.";
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
  	return "1-Versión mas actualizada de APK\r\n"
  			+ "2-Tener conexión a la bd de la nube\r\n"
  			+ "3-Contar con Impresoras Epson y Zebra\r\n"
  			+ "4-Solicitar al equipo de L1 que habiliten el pago con tarjea para los servicios";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
