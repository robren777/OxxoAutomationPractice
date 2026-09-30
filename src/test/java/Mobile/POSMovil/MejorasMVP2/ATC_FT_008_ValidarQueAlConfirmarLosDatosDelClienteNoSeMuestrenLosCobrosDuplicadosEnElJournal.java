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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-008-Validar que al confirmar los datos del cliente no se muestren los cobros duplicados en el Journal.
* DESCRIPCIÓN: Al realizar la venta de un servicio no financiero, no se deberá mostrar la comisión duplicada en el journal
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Contar con los folios de los servicios no financieros
* 
* INSUMOS:
* Folio de servicio
* 
* @autor Cesar Guel
* @date 17/12/2025
*/
//========================================================================//

public class ATC_FT_008_ValidarQueAlConfirmarLosDatosDelClienteNoSeMuestrenLosCobrosDuplicadosEnElJournal extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_008_ValidarQueAlConfirmarLosDatosDelClienteNoSeMuestrenLosCobrosDuplicadosEnElJournal_test(HashMap<String, String> data) throws Exception {
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
  LoginPosMovil.verificarLoginTiendaUserCr(data.get("crTienda"),PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));
  
  /************************ Paso 3 - Ingresar al Journal ***************************/
  addStep("Se ingresa al Journal");
  boolean ingresarJournal = POSMOVIL.ingresarJournal();
  assertTrue(ingresarJournal,"Journal no encontrado");
  
  /************************ Paso 4 - Ingresar al apartado de servicios ***************************/
  addStep("Se ingresa al apartado de servicios");
  POSMOVIL.ingresarModServ();
  
  /************************ Paso 5 - Seleccionar servicio Sky en línea ***************************/
  addStep("Se selecciona el tipo de servicio Sky en línea");
  POSMOVIL.seleccionarServicioSky(data.get("servicio"));
  
  /************************ Paso 6 - Ingresar la información del cliente ***************************/
  addStep("Se ingresa la información del cliente");
  POSMOVIL.ingresarInfCliente(PasswordUtil.decryptPassword(data.get("infCliente")));
  
  /************************ Paso 7 - Confirmar información ***************************/
  addStep("Se confirma la información");
  POSMOVIL.confirmarInfCliente();
  POSMOVIL.siContinuarServ();
  
  /************************ Paso 8 - Validar que no se muestre el cobro de la comisión duplicado***************************/
  addStep("Se valida que no se muestre el cobro de la comisión del servicio duplicado");
  boolean valCobroComisionND = POSMOVIL.validarCobroComServD();
  assertTrue(valCobroComisionND,"Los elementos estan duplicados");
  
//=======================================Cerrar Aplicaciones========================================================//
Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-008-Validar que al confirmar los datos del cliente no se muestren los cobros duplicados en el Journal.";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al realizar la venta de un servicio no financiero, no se deberá mostrar la comisión duplicada en el journal";
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
  	return "11.- Versión mas actualizada de APK\r\n"
  			+ "2.- Tener conexión a la bd de la nube\r\n"
  			+ "3 - Contar con Impresoras Epson y Zebra\r\n"
  			+ "4-Contar con los folios de los servicios no financieros";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
