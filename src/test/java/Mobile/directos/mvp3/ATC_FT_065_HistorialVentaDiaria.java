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


public class ATC_FT_065_HistorialVentaDiaria extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_065_HistorialVentaDiaria_test(HashMap<String, String> data) throws Exception {
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
  OxxoAlDia.generacionQR_HC_Repartidor_SEVenta();
  
  appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
  Thread.sleep(2000);
  ProvDirectos.opcionComenzarProcesoRepecionMercanciaSE();
  ProvDirectos.boton_EscanearQrHCSE();
  ProvDirectos.boton_ContinuarGeneracionPedidoSE();
  ProvDirectos.leerQRHandHeldHCTradicionalSE();

  /*
   * 
   *  ✔ La fecha colocada debe de ser la correspondiente a 3 semanas completas antes del dia en que se este ejecutando el script.
   * 
   */
  
  ProvDirectos.obtener_Sku();
  ProvDirectos.ventas_Articulos(2024,10,21);
  
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
  
  /*
   * Generar Qr a utilizar en Caso de prueba
   */
  
  appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
  Thread.sleep(2000);
  LoginDirectos.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
  OxxoAlDia.ingresarModuloHerramientasSinEvidencia();
  OxxoAlDia.selec_VerificacionMercanciaSE();
  OxxoAlDia.selec_GenHerramientaCompraSE();
  OxxoAlDia.generacionQR_HC_Repartidor_SEVenta();
  
  /*
   * 
   *  ✔ Inicio de Caso de Prueba
   * 
   */
  
  /*
   * Paso 1 - Abrir aplicacion “Proveedores Directos”.
   */
    
  appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
  Thread.sleep(2000);
  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  
  /*
   * Paso 2 - Se muestra pantalla principal de Proveedores Directos.
   */
  Thread.sleep(5000);
  addStep("Se muestra pantalla principal de Proveedores Directos");	
  
  boolean validacion = ProvDirectos.pantallaPrincipalProveedoresDirectos();
  assertTrue(validacion,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta."); 
  
  /*
   * Paso 3 - Seleccionar Botón "Comenzar proceso de Recepción de mercancía".
   */
  
  addStep("Seleccionar Botón \"Comenzar proceso de Recepción de mercancía\"");
  
  validacion = ProvDirectos.opcionComenzarProcesoRepecionMercancia();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 4 -  Se muestra pantalla principal de Generación de Pedido
   */
  
  addStep("Se muestra pantalla principal de Generación de Pedido");
  
  validacion = ProvDirectos.pantallaPrincipalGeneraciondePedido();
  assertTrue(validacion,"La pantalla principal Generación de Pedidos no se muestra de manera correcta.");
  
  /*
   * Paso 5 - Seleccionar Opción "Escanear QR de pedido.
   */
  
  addStep("Seleccionar Opción \"Escanear QR de pedido.");
  
  validacion = ProvDirectos.boton_EscanearQrHC();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 6 - La opción queda seleccionada.
   */
  
  addStep("La opción queda seleccionada");
  
  validacion = ProvDirectos.validaOpcEscanearQRHC();
  assertTrue(validacion,"La opción no se encuentra seleccionada.");
  
  /*
   * Paso 7 - Seleccionar Continuar.
   */
  
  addStep("Seleccionar Continuar.");
  
  validacion = ProvDirectos.boton_ContinuarGeneracionPedido();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 8 - Escanear el código QR tipo 2 de un pedido repartidor HC.
   */
  
  addStep("Escanear el código QR tipo 2 de un pedido repartidor HC.");
  
  validacion = ProvDirectos.leerQRHandHeldHCTradicional();
  assertTrue(validacion,"No se permite ingresar el código QR de manera correcta.");
  
  /*
   * Paso 9 - Seleccionar el botón Detalle de Ventas de algún Sku.
   */
  
  addStep("Seleccionar el botón Detalle de Ventas de algún Sku.");
  
  validacion = ProvDirectos.boton_DetalleVentas1Sku();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 10 - Validar que se muestra pantalla Detalle de Ventas
   * Validar que se muestre de manera correcta la estructura de 4 semanas de ventas de el Sku seleccionado.
   */

  addStep("Validar que se muestra pantalla Detalle de Ventas"
  		+ "Validar que se muestre de manera correcta la estructura de 4 semanas de ventas de el Sku seleccionado.");
  
  validacion = ProvDirectos.pantallaDetalleDeVentasEstructura();
  assertTrue(validacion,"Los elementos de la Pantalla Detalle de Ventas no se muestran correctamente.");
  
  /*
   * Paso 12 - Verificar que si en una semana no hay ventas se debe mostrar toda la semana con *** por día.
   */
  
  addStep("Verificar que si en una semana no hay ventas se debe mostrar toda la semana con *** por día.");
  
  validacion = ProvDirectos.asteriscos_DetalleVentas();
  assertTrue(validacion,"No se muestra el simbolo en los dias sin ventas");
  
  
//=======================================Cerrar Aplicaciones========================================================//
  ProvDirectos.eliminar_Ventas();
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
  
  }
  
  /*
   * Nombre del caso de prueba Manual: MTC-FT-065 Historial Venta Diaria
   * 
   * Desc: Validar que muestre la venta diaria de las últimas 4 semanas por articulo dentro de opción "Detalle de Ventas".
   * 
   * Prerequisitos:
	 1. Contar con la versión más reciente del APK Proveedores directos
	 2. Contar con un dispositivo móvil con Android 11.
	 3. Contar con un Código QR de pedido de HC.
	 4. Contar con un dispositivo móvil con Android 11
   * 
   * @autor Alejandro Valdez
   * @date 21/10/2024
   */

  @Override
  public String setTestFullName() {
  	return "ATC_FT_082_CasoPrueba82";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que muestre la venta diaria de las últimas 4 semanas por articulo dentro de opción \"Detalle de Ventas\"";
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
  	return "1. Contar con la versión más reciente del APK Proveedores directos \r\n"
  			+ "2. Contar con un dispositivo móvil con Android 11\r\n"
  			+ "3.  Contar con un Código QR de pedido de HC.\r\n"
  			+ "4. Contar con Ventas en las últimos hasta la semana -3\r\n"
  			+ "5. La semana -4 no debe contar con ventas en cada uno de los días de la semana";
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
