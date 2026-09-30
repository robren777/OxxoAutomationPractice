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
import Mobile.util.GlobalVariables;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-009-Validar que al seleccionar conusltar se muestre la información corr
* espondiente del reporte 
* DESCRIPCIÓN: Dado que seleccionamos el reporte HEF POS Móvil y presionamos el boton Consultar, se deberá 
* de mostrar una pantalla con la información correspondiente a los movimientos realizados en el día.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Solicitar al equipo de L1 que habiliten el pago con tarjeta para los servicios
* 
* INSUMOS:
* N/A
* 
* @autor Cesar Guel
* @date 23/01/2025
*/
//========================================================================//

public class ATC_FT_009_ValidarQueAlSeleccionarConusltarSeMuestreLaInformacion extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_009_ValidarQueAlSeleccionarConusltarSeMuestreLaInformacion_test(HashMap<String, String> data) throws Exception {
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
		
		GlobalVariables.crTiendaD = PM.getCrTienda(data.get("crTienda"));
		System.out.println("CR Tienda: " + GlobalVariables.crTiendaD);
		GlobalVariables.ipPvD = PM.getIpPvD(data.get("ipPv"));
		System.out.println("IP del punto de venta: " + GlobalVariables.ipPvD);

/************************ Paso 1 - Ingresar a la App ***************************/
	addStep("Abrir aplicación POS Móvil al día en HandHeld.");	
  
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
  LoginPosMovil.verificarLoginTiendaUserCrV2D(data.get("salInic"));
  
  /************************ Paso 3 - Ingresar al Journal ***************************/
  addStep("Se ingresa al Journal");
  boolean ingresarJournal = POSMOVIL.ingresarJournal();
  assertTrue(ingresarJournal,"Journal no encontrado");
  TimeUnit.SECONDS.sleep(5);
  
  /************************ Paso 4 - Ingresar al apartado Opciones ***************************/
  addStep("Se Ingresa al apartado de Opciones");
  POSMOVIL.ingresarApOpciones();
  
  /************************ Paso 5 - Ingresar al apartado Cortes Administrativos ***************************/
  addStep("Se ingresa al apartado de Cortes Administrativos");
  POSMOVIL.ingresarApCortesAdmin();
  
  /************************ Paso 6 - Ingresar al apartado tipo de corte ***************************/
  addStep("Se ingresa al apartado de tipo de cortes");
  POSMOVIL.ingresarApTipoCort();
  
  /************************ Paso 7 - Seleccionar el tipo de corte HEF ***************************/
  addStep("Se selecciona el tipo de corte HEF");
  POSMOVIL.seleccionarTipCortHef();
  
  /************************ Paso 8 - Presionar el boton consultar ***************************/
  addStep("Se presiona el boton Consultar");
  POSMOVIL.consultarCortHEF();
  
  /************************ Paso 9 - Validar Elementos Corte HEF ***************************/
  addStep("Se valida que se encuentren los elementos del reporte");
  boolean validarEleCorHef = POSMOVIL.validarElemCorteHEF();
  assertTrue(validarEleCorHef, "No se encontraron los elementos");
 
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//===========================================================================================================//
  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-009-Validar que al seleccionar conusltar se muestre la información correspondiente del reporte";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que seleccionamos el reporte HEF POS Móvil y presionamos el boton "
  			+ "Consultar, se deberá de mostrar una pantalla con la información "
  			+ "correspondiente a los movimientos realizados en el día.";
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
  	return "1. HandHeld\n"
  			+ "2. Usuario y Contraseña de Aplicación\n"
  			+ "3. Comunicación entre Handheld y POS";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
