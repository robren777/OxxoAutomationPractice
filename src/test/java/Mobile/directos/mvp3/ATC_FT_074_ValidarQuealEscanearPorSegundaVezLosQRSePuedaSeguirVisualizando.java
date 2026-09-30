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


public class ATC_FT_074_ValidarQuealEscanearPorSegundaVezLosQRSePuedaSeguirVisualizando extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_074_ValidarQuealEscanearPorSegundaVezLosQRSePuedaSeguirVisualizando_test(HashMap<String, String> data) throws Exception {
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
  
  /*
   * 2 - Se muestra pantalla principal de Proveedores Directos.
   */
  Thread.sleep(5000);
  addStep("Se muestra pantalla principal de Proveedores Directos");	
  
  boolean validacion = ProvDirectos.pantallaPrincipalProveedoresDirectos();
  assertTrue(validacion,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta."); 
  
  /*
   * 3 - Seleccionar Botón "Comenzar proceso de Recepción de mercancía".
   */
  
  addStep("Seleccionar Botón \"Comenzar proceso de Recepción de mercancía\"");
  
  validacion = ProvDirectos.opcionComenzarProcesoRepecionMercancia();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * 4 -  Se muestra pantalla principal de Generación de Pedido
   */
  
  addStep("Se muestra pantalla principal de Generación de Pedido");
  
  validacion = ProvDirectos.pantallaPrincipalGeneraciondePedido();
  assertTrue(validacion,"La pantalla principal Generación de Pedidos no se muestra de manera correcta.");
  
  
  /*
   * 5 - Seleccionar Opción "Escanear QR de pedido.
   */
  
  addStep("Seleccionar Opción \"Escanear QR de pedido.");
  
  validacion = ProvDirectos.boton_EscanearQrHC();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  
  /*
   * 6 - Seleccionar botón "CONTINUAR".
   * 7 - Escanear código QR.
   */
  
  addStep("Ingresar códigos QR generados o código QR unico.");
  
  validacion = ProvDirectos.leerQRHandHeldHC();
  assertTrue(validacion,"No se permite ingresar el código QR de manera correcta.");
   
  
  /*
   * 8 - Se muestra la pantalla Generación de Pedido verificar los articulo muestren “Detalle de Pedido”.
   */
  
  addStep("Se muestra la pantalla Generación de Pedido verificar los articulo muestren “Detalle de Pedido”.");
  
  validacion = ProvDirectos.validarSkusQr_HC();
  assertTrue(validacion,"No se permite ingresar el código QR de manera correcta.");
    
  validacion = ProvDirectos.validarBtnDetalleVentas();
  assertTrue(validacion,"No se encuentra el botón Detalle de Ventas en pantalla.");
  
  /*
   * 9 - Presionar el botón Back nativo de Android 
   */
  
  addStep("Presionar el botón Back nativo de Android ");
  
  validacion = ProvDirectos.botonBACK();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * 10 - Validar que salga del detalle de pedido.
   */
  
  addStep("Validar que salga del detalle de pedido.");
  
  validacion = ProvDirectos.pantallaPrincipalGeneraciondePedidoT();
  assertTrue(validacion,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta."); 
  
  /*
   * 11 - Escanear nuevamente el mismo QR.
   */
  
  addStep("Escanear nuevamente el mismo QR.");
  
  validacion = ProvDirectos.leerQRHandHeldHCT();
  assertTrue(validacion,"No se permite ingresar el código QR de manera correcta.");
  
  /*
   * 12 - Verificar que se muestre la información del QR y que en cada uno de los artículos muestre información en “Detalle de Venta”.
   */
  
  addStep("Verificar que se muestre la información del QR y que en cada uno de los artículos muestre información en “Detalle de Venta”");
  
  ProvDirectos.opcionNoSE();
  validacion = ProvDirectos.validarSkusQr_HC();
  assertTrue(validacion,"No se pueden validar los skus");
    
  validacion = ProvDirectos.validarBtnDetalleVentas();
  assertTrue(validacion,"No se encuentra el botón Detalle de Ventas en pantalla.");
  
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
  
  }
  
  /*
   * Nombre del caso de prueba Manual: MTC-FT-074 Validar que al escanear por segunda vez los QR se pueda seguir visualizando la opción "Detalle de Ventas"
   * 
   * Desc: Validar que se pueda seguir visualizando el botón "Detalle de Ventas". 
   * 
   * Prerequisitos:
	 1. HandHeld
	 2. Usuario y Contraseña de Aplicación.
	 3. Comunicación entre HH y POS.
	 4. Contar con un Código QR Tipo 2
   * 
   * @autor Alejandro Valdez
   * @date 22/10/2024
   */
  

  @Override
  public String setTestFullName() {
  	return "ATC_FT_086_CasoPrueba86";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que se pueda seguir visualizando el botón \"Detalle de Ventas\".";
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
  	return "1.Contar con la versión más reciente del APK Proveedores directos \r\n"
  			+ "2.Contar con un dispositivo móvil con Android 11\r\n"
  			+ "3.Contar con un Código QR Tipo 2\r\n";
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
