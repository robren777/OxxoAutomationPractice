package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.Login;
import Mobile.directos.om.mvp3.OAD;
import Mobile.directos.om.mvp3.ProveedoresDirectos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;


public class ATC_FT_068_ValidarQueSePuedaPonerEnSegundoPlanoContinuaMostrandoLaInformacion extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_068_ValidarQueSePuedaPonerEnSegundoPlanoContinuaMostrandoLaInformacion_test(HashMap<String, String> data) throws Exception {
	  setupTestData(data);
	  DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
				
		String url = "http://127.0.0.1:4723";

		//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
		
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		  
		//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//		
  
	addStep("Abrir aplicacion “Proveedores Directos");
		  
  	AppiumUtil appiumUtil = null;
  	try {
  		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(3500);
			
  //••••••••••••••••••••••••••••••••••••Llamado al OM de Login••••••••••••••••••••••••••••••••••••//		
	
  Login LoginDirectos = new Login(testCase, appiumUtil, this);
                          
  //••••••••••••••••••••••••••••••••••••Llamado al OM de Oxxo Al Dia••••••••••••••••••••••••••••••••••••//
                          
  OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
    
  //••••••••••••••••••••••••••••••••••••Llamado al OM de Proveedores Directos••••••••••••••••••••••••••••••••••••//
    
  ProveedoresDirectos ProvDirectos = new ProveedoresDirectos(testCase, appiumUtil, this);
    
  //••••••••••••••••••••••••••••••••••••PRE - REQUISITO••••••••••••••••••••••••••••••••••••//
  
  LoginDirectos.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
  OxxoAlDia.ingresarModuloHerramientasSinEvidencia();
  OxxoAlDia.selec_VerificacionMercanciaSE();
  OxxoAlDia.selec_GenHerramientaCompraSE();
  OxxoAlDia.generacionQR_HC_Preventa_SE();

  /*
   * 1 - Abrir aplicacion “Proveedores Directos”.
   */
    
  appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
  Thread.sleep(2000);
  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  ProvDirectos.opcionComenzarProcesoRepecionMercanciaSE();
  
  /*
   * 2 - Seleccionar la opcion leer QR.
   */
  
  addStep("Seleccionar la opcion leer QR.");
  
  boolean validacion = ProvDirectos.boton_EscanearQrHC();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * 3 - Ingresar códigos QR generados o código QR unico.
   */
  
  addStep("Ingresar códigos QR generados o código QR unico.");
  
  validacion = ProvDirectos.leerQRHandHeldHC();
  assertTrue(validacion,"No se permite ingresar el código QR de manera correcta.");
  
  /*
   * 4 - Se muestra los articulos del pedido repartidor HC.
   */
  
  addStep("Se muestra los articulos del pedido repartidor HC.");
  
  validacion = ProvDirectos.validarSkusQr_HC();
  assertTrue(validacion,"No se permite ingresar el código QR de manera correcta.");
  
  /*
   * 5 - Se muestra el boton de "Detalle de venta" correctamente
   */
  
  addStep("Se muestra el boton de \"Detalle de venta\" correctamente");
  
  validacion = ProvDirectos.validarBtnDetalleVentas();
  assertTrue(validacion,"No se encuentra el botón Detalle de Ventas en pantalla.");
  
  /*
   * 6 - Presionar el boton de android multitareas para colocar la aplicación en segundo plano.
   */
  
  addStep("Presionar el boton de android multitareas para colocar la aplicación en segundo plano");
  
  validacion = ProvDirectos.botonMultitareas();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * 7 - Ingresar nuevamente a la aplicación.
   */
  
  addStep("Ingresar nuevamente a la aplicación.");
  
  appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
  Thread.sleep(2000);
  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  
  /*
   * 7 - Validar que la informacion dentro de la aplicación continue mostrandoce correctamente.
   */
  
  addStep("Validar que la informacion dentro de la aplicación continue mostrandoce correctamente.");
  
  validacion = ProvDirectos.validarSkusQr_HC();
  assertTrue(validacion,"No se permite ingresar el código QR de manera correcta.");
  validacion = ProvDirectos.validarBtnDetalleVentas();
  assertTrue(validacion,"No se encuentra el botón Detalle de Ventas en pantalla.");
  
  
//=======================================Cerrar Aplicaciones========================================================//
  ProvDirectos.eliminar_Ventas();
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
  
  }

  /*
   * Nombre del caso de prueba Manual: MTC-FT-068 Validar que se pueda poner en segundo plano continua mostrando la informacion
   * 
   * Desc: Validar que al colocar la aplicación en segundo plano la informacion de Detalle de Ventas se sigue mostrando correctamente.
   * 
   * Prerequisitos:
	 1. HandHeld
	 2. Usuario y Contraseña de Aplicación.
	 3. Comunicación entre HH y POS.
	 4. Escanear un QR pedido precargado
   * 
   * @autor Alejandro Valdez
   * @date 21/10/2024
   */
  

  @Override
  public String setTestFullName() {
  	return "ATC_FT_084_CasoPrueba84";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que al colocar la aplicación en segundo plano la informacion de Detalle de Ventas se sigue mostrando correctamente.";
  }  

  @Override
  public String setTestDesigner() {
  	return "Alejandro Valdez";
  }  

  @Override
  public String setTestInstanceID() {
  	return null;  
  }
  
  @Override
  public String setPrerequisites() {
  	return "1. HandHeld\r\n"
  			+ "2. Usuario y Contraseña de Aplicación\r\n"
  			+ "3. Comunicación entre HH y POS\r\n"
  			+ "4.Escanear un QR pedido precargado ";
  }
  
  @Override
  public void beforeTest() {
  	
  }
  
  /*
   * Metodo
   * ******************************************************************************************
   * setupTestData - metodo usado para obtener la informacion/isumos necesarios para la ejecucion del caso de prueba
   *                 de la nube de Evolucion y evitar que el programa se interrumpa por el tiempo paja/idle timeout 
   *                 del driver original.
   *                 (Fuente de Abasto/Num Proveedor, Skus, Etc.)   
   * 
   * @param data : data provider del caso automatizado(archivo.xlsx)
   * @return N/A
   */
  private void setupTestData(HashMap<String, String> data) {
      try {
          DesiredCapabilities cap = new DesiredCapabilities();
          cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
          cap.setCapability("deviceName", data.get("deviceName"));
          cap.setCapability("platformVersion", data.get("platformVersion"));
          cap.setCapability("platformName", data.get("platformName"));
          cap.setCapability("appPackage", data.get("appPackage"));
          cap.setCapability("appActivity", data.get("appActivity"));
          cap.setCapability("noReset", true);
          
          String url = "http://127.0.0.1:4723";
          
          AppiumUtil appiumUtil = new AppiumUtil(url, cap);
          OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
          OxxoAlDia.validaCrEnNubeEvolucion(data.get("IpPV"));
          OxxoAlDia.obtieneStorePlaceID();
          
          OxxoAlDia.obtenerSkusValidosxFANubeEvo(1, 1);
          
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

}
