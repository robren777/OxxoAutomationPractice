package Mobile.POSMovil.CertificacionMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.PMCapabilities;
import Mobile.util.GlobalVariables;
import Mobile.POSMovil.om.LoginPM;
import utils.AppiumUtil;
import utils.password.PasswordUtil;


//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-009-Validar acceso usuario Spin Premia - Numero Telefono
* DESCRIPCIÓN: Dado que se quiere acceder al POS Móvil y registrarse como usuario Spin Premia, se deberá ingresar por medio de un numero de telefono
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* Telefono de usuario Spin Premia 
* 
* @autor Cesar Guel
* @date 09/09/2024
*/
//========================================================================//

public class ATC_FT_009_ValidarAccesoUsuarioSpinPremiaNumeroTelefono extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_009_ValidarAccesoUsuarioSpinPremiaNumeroTelefono_test(HashMap<String, String> data) throws Exception {
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
	addStep("Iniciar Aplicación");
  
  	AppiumUtil appiumUtil = null;
  	try {
//		appiumUtil = new AppiumUtil(url, cap);
		appiumUtil = PMCapabilities.initializeAppium("login");
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(3500); //Tiempo de Espera
  	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
			
//================================================================================================================//		
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
  
  /************************ Paso 3 - Ingresar articulo al journal ***************************/
  
  addStep("Se ingresa el articulo correspondiente al Journal");
  POSMOVIL.ingresarArticuloJournalD();
  
  /************************ Paso 4 - Totalizar articulo ***************************/
  
  addStep("Se totaliza la venta con el articulo correspondiente");
  POSMOVIL.totalizarArticulo();
  
  /************************ Paso 5 - Presionar boton Identificar cliente Spin Premia por numero de telefono ***************************/
  
  addStep("Se realiza la identificación de usuario Spin Premia");
  POSMOVIL.seleccionarBotonSpinPremiaNT();
  
  /************************ Paso 6 - Identificar cliente ingresando el numero de telefono correspondiente ***************************/
  
  addStep("Se realiza la identificación de usuario Spin Premia");
  POSMOVIL.identificacionSpinNT(data.get("telefono"));
  
  /************************ Paso 7 - Validar que la identificación correcta del cliente por numero de telefono ***************************/
  
  addStep("Se valida que al identificarse el boton se encuentre deshabilitado");
  boolean validarAccesoSpinNumeroTelefono = POSMOVIL.identificacionSpinNTSE();
  assertTrue(validarAccesoSpinNumeroTelefono,"El acceso de usuario Spin premia no fue exitoso");
  
//=======================================Cerrar Aplicaciones========================================================//
Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-009-Validar acceso usuario Spin Premia - Numero Telefono";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que se quiere acceder al POS Móvil y registrarse como usuario Spin Premia, se deberá ingresar por medio de un numero de telefono";
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
