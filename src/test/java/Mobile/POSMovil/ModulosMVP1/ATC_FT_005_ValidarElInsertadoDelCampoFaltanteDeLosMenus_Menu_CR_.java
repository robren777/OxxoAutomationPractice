package Mobile.POSMovil.ModulosMVP1;

import static org.testng.Assert.assertTrue;
import org.testng.Assert;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.LoginPM;
import Mobile.POSMovil.om.PMCapabilities;
import java.time.Duration;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import Mobile.POSMovil.om.SQLCMDFormatter;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP1
* 
* NOMBRE DE CASO DE PRUEBA: ATC_FT_005_ValidarElInsertadoDelCampoFaltanteDeLosMenus_Menu_CR_
* DESCRIPCIÓN: Dado que al eliminar un campo y quede como faltante en el registro de la caja al reiniciar el aplicativo se tendrá que agregar en el menú correspondientemente.
* 
* PRE-REQUISITOS
* 1.Versión más actualizada de APK
* 2.Tener conexión a la bd de la nube
* 3.Contar con los permisos de borrado para la bd de POS Móvil
* 
* INSUMOS:
* SKU de articulos
* 
* @autor Juan Ibarra 
* @date 21/04/2026
*
* Adecuaión comentarios de equipo BAU
* 1.- En el Paso 1 y 2 no traen evidencias
* 2.- Validacion de PSEXE y VPN deben realizarse en un before Method y en un AfterMethod Cerrar la Conexión de la VPN y quitar el archivo PSEXE
*
* @date 12/06/2026
* @autor Juan Ibarra
*
*/
//========================================================================//

public class ATC_FT_005_ValidarElInsertadoDelCampoFaltanteDeLosMenus_Menu_CR_ extends BaseExecution {
	private String userLogin;
	private String passwordLogin;
	private String appPackage;


@Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_005_ValidarElInsertadoDelCampoFaltanteDeLosMenus_Menu_CR__test(HashMap<String, String> data) throws Exception {
	this.userLogin = data.get("userLogin");
    this.passwordLogin = PasswordUtil.decryptPassword(data.get("passwordLogin"));
    this.appPackage = data.get("appPackage");
    
	DesiredCapabilities cap = new DesiredCapabilities();	

	cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
	cap.setCapability("deviceName", data.get("deviceName"));
	cap.setCapability("platformName", data.get("platformName"));
	cap.setCapability("appPackage", data.get("appPackage"));
	cap.setCapability("appActivity", data.get("appActivity"));
	cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
			
	String url = "http://127.0.0.1:4723";

  	AppiumUtil appiumUtil = null;

	//=============================================================================================//
  	System.out.println("Cerrando aplicacion si está abierta");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	Thread.sleep(2000);
	//=============================================================================================//


  	try {  		
  		appiumUtil = new AppiumUtil(url, cap);
//  		appiumUtil = PMCapabilities.initializeAppium("login");
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(500); 
	
  	//	Llamado a los OM's necesarios para la prueba.
  	LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);
  	LoginPosMovil.validarArchivoVPN();
  	LoginPosMovil.existePsExe();
//  	
  	// Llamado al OM de PosMovil.
  	PM POSMOVIL = new PM(testCase, appiumUtil, this);

//================================================================================================================//
    /************************ Paso 1.	Ingresar a SQL Server ***************************/	  
	  addStep("Ingresar a SQL Server");
	  testCase.addCodeEvidenceCurrentStep("Se ingresa a SQL Server" );

	  /************************ Paso 2.	Ingresar las credenciales de conexión ***************************/
	  addStep("Ingresar las credenciales de conexión");
	  testCase.addCodeEvidenceCurrentStep("Se ingresan Ingresar las credenciales " );
	  
	  /************************ Paso 3.	Realizar consulta de los menús POS Móvil ***************************/	  
	  addStep("Realizar consulta de los menús POS Móvil");
	  String permisos = SQLCMDFormatter.formatResultSet(POSMOVIL.obtienePermisosBDNube(data.get("crTienda")));
	  testCase.addTextEvidenceCurrentStep("Consulta general de la tabla:\r\n"+permisos);
	  String fastfood = SQLCMDFormatter.formatResultSet(POSMOVIL.obtienePermisosBDNubeEspecifico(data.get("crTienda"), 10));
	  testCase.addTextEvidenceCurrentStep("Valores de fastfood:\r\n" + fastfood);
	  
	  /************************ Paso 4.	 Eliminar el ultimo campo del registro del menú de la caja correspondiente***************************/	  
	  addStep("Eliminar el ultimo campo del registro del menú de la caja correspondiente");	  
	  POSMOVIL.eliminarPermisosBDNubeEspecifico(data.get("crTienda"), 10);
	  String permisoseliminar = SQLCMDFormatter.formatResultSet(POSMOVIL.obtienePermisosBDNube(data.get("crTienda")));
	  testCase.addTextEvidenceCurrentStep("Consulta general de la tabla después de eliminar el registro :\r\n"+permisoseliminar);
	  String fastfoodeliminar = SQLCMDFormatter.formatResultSet(POSMOVIL.obtienePermisosBDNubeEspecifico(data.get("crTienda"), 10));
	  testCase.addTextEvidenceCurrentStep("Valores de fastfood después de eliminar el registro:\r\n" + fastfoodeliminar);  
	   
	  /************************ Paso 5.	Reiniciar aplicativo POS Móvil***************************/	  
	  addStep("Reiniciar aplicativo POS Móvil");	  
	  LoginPosMovil.verificarLoginTiendaUserCrV2(data.get("crTienda"),data.get("salInic"));	  
	  boolean ingresoJournal = POSMOVIL.ingresarJournalV2();
	  assertTrue(ingresoJournal,"No se pudo encontrar Journal");  
	  
	  /************************ Paso 6.	?Validar que al consultar los menús se inserte el campo faltante***************************/
	  addStep("Validar que al consultar los menús se inserte el campo faltante");
	  String permisosdespues = SQLCMDFormatter.formatResultSet(POSMOVIL.obtienePermisosBDNube(data.get("crTienda")));
	  testCase.addTextEvidenceCurrentStep("Consulta general de la tabla de registros después de entrar a POS Movil:\r\n"+permisosdespues);
	  String fastfooddespues = SQLCMDFormatter.formatResultSet(POSMOVIL.obtienePermisosBDNubeEspecifico(data.get("crTienda"), 10));
	  testCase.addTextEvidenceCurrentStep("Valores de fastfood después de entrar a POS Movil:\r\n" + fastfooddespues);

			
  //=======================================Cerrar Aplicaciones========================================================//
     Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
  //==================================================================================================================//

  
  }

  @Override
  public String setTestFullName() {
  	return "ATC-FT-005-Validar el insertado del campo faltante de los menús(Menu_CR)";
  }
  
  @Override
  public String setTestDescription() {
  	return "Dado que al eliminar un campo y quede como faltante en el registro de la caja al reiniciar el aplicativo se tendrá que agregar en el menú correspondientemente.";
  }  

  @Override
  public String setTestDesigner() {
  	return "Juan Ibarra";
  }  

  @Override
  public String setTestInstanceID() {
  	return null;  
  }
  
  @Override
  public String setPrerequisites() {
  	return "1.Versión más actualizada de APK\r\n"
  			+ "2.Tener conexión a la bd de la nube\r\n"
  			+ "3.Contar con los permisos de borrado para la bd de POS Móvil";
  }
  
  @Override
  public void beforeTest() {
//	  System.out.println("Realiza la acción del BeforeTest");
//	  AppiumUtil appiumUtil = null;
//	  LoginPM beforePosMovil = new LoginPM(testCase, appiumUtil, this);
//	  beforePosMovil.validarArchivoVPN();
//	  try {
//		beforePosMovil.existePsExe();
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}		  
	}
	
  
//   @Before 
//	public void BeforeMethod() throws Exception {
//	   System.out.println("Realiza la acción del BeforeMethod");
//		  AppiumUtil appiumUtil = null;
//		  LoginPM beforePosMovil = new LoginPM(testCase, appiumUtil, this);
//		  beforePosMovil.validarArchivoVPN();
//		  beforePosMovil.existePsExe();
////		  try {
////			beforePosMovil.existePsExe();
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}	
//	  }	
//	
//	@AfterMethod (alwaysRun = true)
//	public void afterMethod() {
//		System.out.println("Entra a @AfterMethod");
//		AppiumUtil appiumUtil = null;	
//		
//		try {
//			System.out.println("Cierra la aplicación");
//			Runtime.getRuntime().exec("adb shell am force-stop \"" + appPackage + "\"");			
//		} catch (Exception e) {
//	        e.printStackTrace();
//	    } 
//		
//		try {
//			LoginPM afterMethodLoginPosMovil = new LoginPM(testCase, appiumUtil, this);
//			System.out.println("Cierra la VPN");
//			afterMethodLoginPosMovil.cerrarVPN();
//				
//		} catch (Exception e) {
//	        e.printStackTrace();
//	    } 	
//	}

}