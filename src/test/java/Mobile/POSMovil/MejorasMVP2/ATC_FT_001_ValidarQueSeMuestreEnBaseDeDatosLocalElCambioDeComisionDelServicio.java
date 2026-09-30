package Mobile.POSMovil.MejorasMVP2;

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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-001-Validar que se muestre en base de datos
 local el cambio de comisión del servicio
* DESCRIPCIÓN: Al realizar la actualización de la comisión del servicio, se debera
 de reflejar el resultado en la base de datos local
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Contar con los folios de los servicios no financieros
* Contar con .TRA de comisión por consulta / por servicios
* 
* INSUMOS:
* Folio de servicio
* 
* @autor Cesar Guel
* @date 12/12/2025
*/
//========================================================================//

public class ATC_FT_001_ValidarQueSeMuestreEnBaseDeDatosLocalElCambioDeComisionDelServicio extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_001_ValidarQueSeMuestreEnBaseDeDatosLocalElCambioDeComisionDelServicio_test(HashMap<String, String> data) throws Exception {
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
		
		/************************  Ingresar a la App ***************************/
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
  /************************ Paso 1 - Inicio de sesión ***************************/
  addStep("Se inicia con la validación del logeo de la tienda nivel CR plaza y CR tienda");
  LoginPosMovil.verificarLoginTiendaUserCr(data.get("crTienda"),PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));
  
  /************************ Paso 2 - Validación de monto de comisión de servicio ***************************/
  addStep("Se valida que la consulta sea exitosa");
  boolean validarMontoComServ = POSMOVIL.validaMontoComisionServicio(data.get("ipPv"),data.get("idServCode"),data.get("montoEsperado"));
  assertTrue(validarMontoComServ,"Los montos no coinciden");
  
//=======================================Cerrar Aplicaciones========================================================//
Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-001-Validar que se muestre en base de datos\r\n" + //
				" local el cambio de comisión del servicio";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al realizar la actualización de la comisión del servicio, se debera\r\n" + //
				" de reflejar el resultado en la base de datos local";
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
  			+ "4-Contar con los folios de los servicios no financieros\r\n"
  			+ "5- Contar con .TRA de comisión por consulta / por servicios";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
