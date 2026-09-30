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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-075-Validar que se muestre el top de productos en fast food Alimento.
* DESCRIPCIÓN: Validar que se muestra en el catalogo de fast food el top de productos mas vendidos.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* Folio de referencia de servicio
* 
* @autor Cesar Guel
* @date 28/01/2025
*/
//========================================================================//

public class ATC_FT_075_ValidarQueSeMuestreElTopDeProductosEnFastFoodAlimento extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_075_ValidarQueSeMuestreElTopDeProductosEnFastFoodAlimento_test(HashMap<String, String> data) throws Exception {
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
  
  /************************ Paso 4 - Ingresar al apartado de FastFoodl ***************************/
  addStep("Se ingresa al apartado de Fast Food");
  POSMOVIL.ingresarModFastFood();
  
  /************************ Paso 5 - Validar que se muestren las opciones de FastFood***************************/
  addStep("Se valida que se muestren las opciones de FastFood");
  boolean valcatsff = POSMOVIL.validarCatsFFAYB();
  assertTrue(valcatsff,"No se pudieron encontrar");
  
  /************************ Paso 6 - Ingresar al modulo de FastFood Alimento ***************************/
  addStep("Se selecciona FastFood Alimento");
  POSMOVIL.presionarBtnFFAlimentos();
  
  /************************ Paso 7 - Validar que se muestren las opciones de FastFood Alimentos ***************************/
  addStep("Se valida que se muestren las opciones de alimentos");
  boolean valOpcFFA = POSMOVIL.validarCatsFFA();
  assertTrue(valOpcFFA,"No se pudieron encontrar las opciones");
  
  /************************ Paso 8 - Ingresar a la categoria Comida Caliente ***************************/
  addStep("Se selecciona la categoria Comida Caliente");
  POSMOVIL.ingresarCatComCalFFAlimento();
  
  /************************ Paso 9 - Validar que se muestren las opciones de Comida Caliente ***************************/
  addStep("Se valida que se muestren las opciones de Comida Caliente");
  boolean valopccc = POSMOVIL.validarCatComCalFFA();
  assertTrue(valopccc,"No se encontraron los elementos");
  
  /************************ Paso 10 - Validar que se muestre el top de alimentos FastFood Comida Caliente ***************************/
  addStep("Se valida que se muestre el top de alimentos");
  boolean valtopccffa = POSMOVIL.validarTopCCFFA();
  assertTrue(valtopccffa,"No se encontro el top de alimentos calientes");
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-075-Validar que se muestre el top de productos en fast food Alimento.";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que se muestra en el catalogo de fast food el top de productos mas vendidos.";
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
