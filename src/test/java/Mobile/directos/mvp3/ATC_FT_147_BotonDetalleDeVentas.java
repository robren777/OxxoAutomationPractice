package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.Login;
import Mobile.directos.om.mvp3.OAD;
import Mobile.directos.om.mvp3.ProveedoresDirectos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;


public class ATC_FT_147_BotonDetalleDeVentas
extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_147_BotonDetalleDeVentas_test(HashMap<String, String> data) throws Exception {
	setupTestData(data);
	DesiredCapabilities cap = new DesiredCapabilities();
	
	cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
	cap.setCapability("deviceName", data.get("deviceName"));
	cap.setCapability("platformVersion", data.get("platformVersion"));
	cap.setCapability("platformName", data.get("platformName"));
	cap.setCapability("appPackage", data.get("appPackage"));
	cap.setCapability("appActivity", data.get("appActivity"));
	cap.setCapability("appium:settings[enableMultiWindows]", true);
	cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
			
	String url = "http://127.0.0.1:4723";
	
	//======= Cerrar Aplicaciones =========
	Thread.sleep(5000);
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	//======================================================================================================
	AppiumUtil appiumUtil = null;
	try {
		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}			
	 //=======================================================================================================//		
	//Llamado al OM de Login.
	Login LoginDirectos = new Login(testCase, appiumUtil, this);
	//Llamado al OM de Oxxo Al Dia.
	OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
	//Llamado al OM de Proveedores Dierectos
	ProveedoresDirectos ProvDirectos = new ProveedoresDirectos(testCase, appiumUtil, this);
	
	 //=======================================================================================================//
	// ------- Previo -------
	addStep(" ");
    LoginDirectos.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
    OxxoAlDia.genearQrPedidoPreventaHtaCompra(50,1);
    appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
    //-----------------------
	/*
	 * Paso 1 - Abrir aplicación "Proveedores Directos"
	 */
	addStep("Abrir aplicación \"Proveedores Directos\"");
	Thread.sleep(2000);
	testCase.addScreenShotCurrentStep(appiumUtil,"testImage");
	/*
	 * Paso 2 - Se muestra pantalla principal de Proveedores Directos
	 */
	addStep(" Se muestra pantalla principal de Proveedores Directos");
	Thread.sleep(2000);
	testCase.addScreenShotCurrentStep(appiumUtil,"testImage");
	
	/*
	 * Paso 3 - Seleccionar Botón "Comenzar proceso de Recepción de mercancía"
	 */
	addStep("Seleccionar Boton \"Comenzar proceso de Recepción de mercancia\"");
	ProvDirectos.opcionComenzarProcesoRepecionMercancia();
	
	/*
	 * Paso 4 - Se muestra pantalla principal de Generación de Pedido
	 */
	addStep("Se muestra pantalla principal de Generación de Pedido");
	ProvDirectos.pantallaGeneracionDePedidoCapaciationGuiada();
	
	/*
	 * Paso 5 - Seleccionar Opción "Escanear QR de pedido 
	 */
	addStep("Seleccionar Opcion 'Escanear QR de pedido'");
	ProvDirectos.tapRdBtnEscanearQrPedOHtaCompra();
	
	/*
	 * Paso 6 - Seleccionar botón "CONTINUAR"
	 */
	addStep("Seleccionar botón \"CONTINUAR\"");
	ProvDirectos.tapBtnContinuarGP();
	
	/*
	 * Paso 7 - Escanear codigo QR  con mas de 50 articulos
	 */
	addStep("Escanear codigo QR  con mas de 50 articulos");
	ProvDirectos.capturarQrGenHtaCompra(OAD.codigosQR);
	
	/*
	 * Paso 8 - Se muestra la pantalla Generación de Pedido con los datos correspondientes del QR Escaneado.
	 */
	addStep("Se muestra la pantalla Generacion de Pedido con los datos correspondientes del QR Escaneado.");
	boolean validacion = ProvDirectos.validaExistePantallaGenPedidoHtaCompra();
	assertTrue(validacion,"Error, No se encontro la pantalla de Generacion de Pedido");
	
	/*
	 * Paso 9 - Navegar por las paginas de Sku´s.
	 */
	addStep("Navegar por las paginas de Skus.");
	/*
	 * Paso 10 - Se muestran los Sku´s 
	 */
	addStep("Se muestran los Skus");
	ProvDirectos.mostrarDistintasPagDeQrs();
	
	/*
	 * Paso 11 - Validar que se muestre correctamente el botón "Detalle de Venta" en los Skus.
	 */
	addStep("Validar que se muestre correctamente el botón \"Detalle de Venta\" en los Skus.");
	validacion = ProvDirectos.validarBtnDetalleDeVentasGenPed();
	assertTrue(validacion,"Error, Los botones de 'Detalle de Venta' no se muestran correctamente");
	ProvDirectos.swipeCoord(200, 300, 200, 950, 1000);
	validacion = ProvDirectos.validarBtnDetalleDeVentasGenPed();
	assertTrue(validacion,"Error, Los botones de 'Detalle de Venta' no se muestran correctamente");

  
	//=======================================Cerrar Aplicaciones========================================================//
	Thread.sleep(5000);
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	//==================================================================================================================//
	  
  }
  
  /*
   * Proveedores Directos MVP3: MTC-FT-147 Botón "Detalle de Ventas"
   * Desc: Validar que se pueda seguir visualizando el botón "Detalle de Ventas" independientemente de la cantidad de Sku´s y las paginas. 
   * 
   * @autor Alex Aquino
   * @date 22/10/24
   */
  

  @Override
  public String setTestFullName() {
  	return "ATC_FT_147_BotonDetalleDeVentas";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que se pueda seguir visualizando el boton \"Detalle de Ventas\" independientemente de la cantidad de Skus y las paginas.";
  }  

  @Override
  public String setTestDesigner() {
  	return "Alex Aquino";
  }  

  @Override
  public String setTestInstanceID() {
  	return null;  
  }
  
  @Override
  public String setPrerequisites() {
  	return "1.Contar con la versión mas reciente del APK Proveedores directos \r\n"
  			+ "2.Contar con un dispositivo móvil con Android 11\r\n"
  			+ "3.Contar con un Código QR de pedido preventa de HC con mas de 50 Sku´s."
  			+ "4.Contar con la aplicacion PROVEEDORES DIRECTOS ya Activada";
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
          OxxoAlDia.ObtenerFAValidaNubeEvo(50, 1);
      } catch (Exception e) {
          Assert.fail("Error de conexion a la BD");
      }
  }

}
