package Mobile.POSMovil.IntegralesMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-029-Validar logueo de usuario cajero
* DESCRIPCIÓN: Al ingresar las credenciales de usuario cajero se inicie sesion correctamente
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* CR de la plaza y Tienda
* Contraseña de cajero
* 
* @autor Cesar Guel
* @date 24/01/2025
*/
//========================================================================//

public class ATC_FT_029_ValidarLogueoDeUsuarioCajero extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_029_ValidarLogueoDeUsuarioCajero_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		//=============================================================================================//
		System.out.println("Cerrando aplicacion si está abierta");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Thread.sleep(2000);
		//=============================================================================================//
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));		
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
				
		String url = "http://127.0.0.1:4723";

/************************ Paso 1 - Ingresar a la App ***************************/
	addStep("Abrir aplicación POS Móvil al día en HandHeld.");	
  
  	AppiumUtil appiumUtil = null;
  	try {
  	appiumUtil = new AppiumUtil(url, cap);
//  		appiumUtil = PMCapabilities.initializeAppium("Login");
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
  LoginPosMovil.validarArchivoVPN();  
  
//================================================================================================================//
  /************************ Paso 2 - Validar que se muestre la pantalla de logueo ***************************/
  addStep("Se valida que se muestre la pantalla de logueo");
  boolean valpanlog = LoginPosMovil.validarPanLog();
  assertTrue(valpanlog,"No se pudo encontrar la pantalla");
  
  /************************ Paso 3 - Ingresar CR de tienda en el campo CR DE TIENDA ***************************/
  addStep("Se ingresa CR tienda");
//  LoginPosMovil.ingresarCRTiendaC(data.get("ipPv"));
  LoginPosMovil.ingresarCRTiendaC(LoginPosMovil.obtenerIPTienda(data.get("crTienda")));
  
  /************************ Paso 4 - Presionar Boton flecha o enter ***************************/
  addStep("Se presiona boton enter");
  LoginPosMovil.presBtnEnt(data.get("salInic"));

  /************************ Paso 5 - Validar se muestre pantalla para seleccionar usuario***************************/
  addStep("Se valida que se muestre el listado de usuarios");
  Thread.sleep(900);
  boolean vallisus = LoginPosMovil.validarLisUs();
  assertTrue(vallisus,"No se encontro la lista de usuarios");

  /************************ Paso 6 - Seleccionar usuario cajero ***************************/
  addStep("Se selecciona usuario cajero");
//  LoginPosMovil.selecUsuario();
  LoginPosMovil.selecUsuarioV2(data.get("crTienda"));

  /************************ Paso 7 - Ingresar contraseña de usuario en el campo contraseña ***************************/
  addStep("Se ingresa la contraseña de usuario");
//  LoginPosMovil.ingresarCtrs(PasswordUtil.decryptPassword(data.get("userPassword")));
  LoginPosMovil.ingresarCtrs(LoginPosMovil.passUser(data.get("crTienda")));

  /************************ Paso 8 - Presionar Boton flecha o enter ***************************/
  addStep("Se presiona boton enter");
  LoginPosMovil.presionarBtnEnt(data.get("salInic"));

  /************************ Paso 9 - Validar que se realice el logueo correctamente y se muestre el journal del APP POS MOVIL ***************************/
  addStep("Se valida el login correcto de usuario");
  Thread.sleep(500);
  boolean validarlogcor = LoginPosMovil.validarLogCor();
  assertTrue(validarlogcor,"No se realizo el login correcto");
  
//=======================================Cerrar Aplicaciones========================================================//
Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-029-Validar logueo de usuario cajero";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al ingresar las credenciales de usuario cajero se inicie sesion correctamente";
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
  
  @AfterTest
	public void afterTest() throws Exception {
	System.out.println("Realiza la acción del AfterTest");
	  AppiumUtil appiumUtil = null;
	  LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);	  
	  LoginPosMovil.cerrarVPN();
	  }

}
