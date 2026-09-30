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

public class ATC_FT_141_PromedioDeVentaDiaria
extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_141_PromedioDeVentaDiaria_test(HashMap<String, String> data) throws Exception {
	setupTestData(data);
	DesiredCapabilities cap = new DesiredCapabilities();
	
	cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
	cap.setCapability("deviceName", data.get("deviceName"));
	cap.setCapability("platformVersion", data.get("platformVersion"));
	cap.setCapability("platformName", data.get("platformName"));
	cap.setCapability("appPackage", data.get("appPackage"));
	cap.setCapability("appActivity", data.get("appActivity"));
	cap.setCapability("appium:settings[enableMultiWindows]", false);
	cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
			
	String url = "http://127.0.0.1:4723";
	
	//======= Cerrar Aplicaciones =========
	  //Thread.sleep(5000);
	  //Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
	  //Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	  
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
    OxxoAlDia.genearQrPedidoPreventaHtaCompra(3,3);
    appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));

    /*
	 * Paso 1 - Abrir aplicacion "Proveedores Directos"
	 */
	addStep("Abrir aplicacion \"Proveedores Directos\"");
	Thread.sleep(2000);
	testCase.addScreenShotCurrentStep(appiumUtil,"testImage");
	
	/*
	 * Paso 2 - Se muestra pantalla principal de Proveedores Directos
	 */
	addStep("Se muestra pantalla principal de Proveedores Directos");
	Thread.sleep(4000);
	testCase.addScreenShotCurrentStep(appiumUtil,"testImage");
	
	/*
	 * Paso 3 - Seleccionar Boton "Comenzar proceso de Recepcion de mercancia"
	 */
	addStep("Seleccionar Boton \"Comenzar proceso de Recepcion de mercancia\"");
	ProvDirectos.opcionComenzarProcesoRepecionMercancia();
	
	/*
	 * Paso 4 - Se muestra pantalla principal de Generacion de Pedido
	 */
	addStep("Se muestra pantalla principal de Generacion de Pedido");
	boolean validacion = ProvDirectos.validaExistePantallaGenPedidoHtaCompra();
	assertTrue(validacion,"Error, No se encontro la pantalla principal de Generacion de Pedido");
	
	/*
	 * Paso 5 - Seleccionar Opcion "Escanear QR de pedido o QR de Hta. de compra"
	 */
	addStep("Seleccionar Opcion \"Escanear QR de pedido o QR de Hta. de compra\"");
	ProvDirectos.tapRdBtnEscanearQrPedOHtaCompra();
	
	/*
	 * Paso 6 - Seleccionar boton "CONTINUAR"
	 */
	addStep("Seleccionar boton \"CONTINUAR\"");
	ProvDirectos.tapBtnContinuarGP();
	
	/*
	 * Paso 7 - Escanear codigo QR
	 */
	addStep("Escanear codigo QR");
	ProvDirectos.capturarQrGenHtaCompra(OAD.codigosQR);
	
	/*
	 * Paso 8 - Se muestra la pantalla Generación de Pedido con los datos correspondientes del QR Escaneado.
	 */
	addStep("Se muestra la pantalla Generacion de Pedido con los datos correspondientes del QR Escaneado.");
	validacion = ProvDirectos.validaExistePantallaGenPedidoHtaCompra();
	assertTrue(validacion,"Error, No se encontro la pantalla de Generacion de Pedido");
	
	/*
	 * Paso 9 - Identificar el articulo 1 elegido al cual se le realizaron ventas Aleatorias en solo una semana y seleccionar el botón “Detalle de Venta”
	 */
	addStep("Identificar el articulo 1 elegido al cual se le realizaron ventas Aleatorias en solo una semana y seleccionar el botón “Detalle de Venta”");
	ProvDirectos.buscarSkuConVentasYAbrirDetalleDeVentas(OAD.SkusEvo.get(0));
	
	/*
	 * Paso 10 - Se muestra pantalla Detalle de Ventas.
	 */
	addStep("Se muestra pantalla Detalle de Ventas.");
	validacion = ProvDirectos.validaVentanaDetalleDeVentas();
	assertTrue(validacion,"Error, No se desplego la ventana de Detalle de Ventas");
	
	/*
	 * Paso 11 - Validar que el promedio que se muestran debajo de cada columna de dias sea el correcto
	 */
	addStep("Validar que el promedio que se muestran debajo de cada columna de dias sea el correcto");
	/*
	 * Paso 12 - Verificar el promedio con la formula 
		Promedio de venta = total de ventas /numero de semanas consideradas
		
		nota las semanas que no se consideran son
		-las semanas marcadas con *** no son consideras debido que no se realizaron ventas en ningun dia de la semana marcada 
		-el dia presente debido que aun no concluye la fecha administrativa
		-la semana mostrada en blanco sin registros debido que aun no corre.
	 */
	addStep("Verificar el promedio con la formula \r\n"
			+ "Promedio de venta = total de ventas /numero de semanas consideradas\r\n"
			+ "\r\n"
			+ "nota las semanas que no se consideran son\r\n"
			+ "-las semanas marcadas con *** no son consideras debido que no se realizaron ventas en ningun dia de la semana marcada \r\n"
			+ "-el dia presente debido que aun no concluye la fecha administrativa\r\n"
			+ "-la semana mostrada en blanco sin registros debido que aun no corre.\r\n");
	validacion = ProvDirectos.validaPromediosDetalleDeVenta();
	assertTrue(validacion,"Error, Las cantidades promedio no corresponden a la formula aplicada");
	
	/*
	 * Paso 13 - Verificar que solo este tomando en cuenta una semana en la formula debido que solo se registraron ventas en una semana de las 4
	 */
	addStep("Verificar que solo este tomando en cuenta una semana en la formula debido que solo se registraron ventas en una semana de las 4");
	validacion = ProvDirectos.validaSemanasConVentas(1);
	assertTrue(validacion,"Error, El articulo tuvo ventas en mas de 1 semana");
	
	/*
	 * Paso 14 - Identificar el articulo 2 elegido al cual se le realizaron ventas a 3 de las -4 semanas que evalua y seleccionar el boton “Detalle de Venta”
	 */
	addStep("Identificar el articulo 2 elegido al cual se le realizaron ventas a 3 de las -4 semanas que evalua y seleccionar el boton “Detalle de Venta”");
	ProvDirectos.cerrarDetalleDeVentasGP_SE();
	ProvDirectos.buscarSkuConVentasYAbrirDetalleDeVentas(OAD.SkusEvo.get(1));
	
	/*
	 * Paso 15 - Se muestra pantalla Detalle de Ventas.
	 */
	addStep("Se muestra pantalla Detalle de Ventas.");
	validacion = ProvDirectos.validaVentanaDetalleDeVentas();
	assertTrue(validacion,"Error, No se desplego la ventana de Detalle de Ventas");
	
	/*
	 * Paso 16 - Validar que el promedio que se muestran debajo de cada columna de dias sea el correcto
	 */
	addStep("Validar que el promedio que se muestran debajo de cada columna de dias sea el correcto");
	/*
	 * Paso 17 - Verificar el promedio con la formula 
		Promedio de venta = total de ventas /numero de semanas consideradas
		
		nota las semanas que no se consideran son
		-las semanas marcadas con *** no son consideras debido que no se realizaron ventas en ningun dia de la semana marcada 
		-el dia presente debido que aun no concluye la fecha administrativa
		-la semana mostrada en blanco sin registros debido que aun no corre.
	 */
	addStep("Verificar el promedio con la formula \r\n"
			+ "Promedio de venta = total de ventas /numero de semanas consideradas\r\n"
			+ "\r\n"
			+ "nota las semanas que no se consideran son\r\n"
			+ "-las semanas marcadas con *** no son consideras debido que no se realizaron ventas en ningun dia de la semana marcada \r\n"
			+ "-el dia presente debido que aun no concluye la fecha administrativa\r\n"
			+ "-la semana mostrada en blanco sin registros debido que aun no corre.\r\n");
	validacion = ProvDirectos.validaPromediosDetalleDeVenta();
	assertTrue(validacion,"Error, Las cantidades promedio no corresponden a la formula aplicada");
	
	/*
	 * Paso 18 - Verificar que solo este tomando en cuenta tres semanas en la formula debido que solo se registraron ventas en 3 de las 4 semanas 
	 */
	addStep("Verificar que solo este tomando en cuenta una semana en la formula debido que solo se registraron ventas en una semana de las 4");
	validacion = ProvDirectos.validaSemanasConVentas(3);
	assertTrue(validacion,"Error, El articulo tuvo ventas en mas de 3 semanas");
	
	/*
	 * Paso 19 - Identificar el articulo 2 elegido al cual se le realizaron ventas a 3 de las -4 semanas que evalua y seleccionar el boton “Detalle de Venta”
	 */
	addStep("Identificar el articulo 2 elegido al cual se le realizaron ventas a 3 de las -4 semanas que evalua y seleccionar el boton “Detalle de Venta”");
	ProvDirectos.cerrarDetalleDeVentasGP_SE();
	ProvDirectos.buscarSkuConVentasYAbrirDetalleDeVentas(OAD.SkusEvo.get(2));
	
	/*
	 * Paso 20 - Se muestra pantalla Detalle de Ventas.
	 */
	addStep("Se muestra pantalla Detalle de Ventas.");
	validacion = ProvDirectos.validaVentanaDetalleDeVentas();
	assertTrue(validacion,"Error, No se desplego la ventana de Detalle de Ventas");
	
	/*
	 * Paso 21 - Validar que el promedio que se muestran debajo de cada columna de dias sea el correcto
	 */
	addStep("Validar que el promedio que se muestran debajo de cada columna de dias sea el correcto");
	/*
	 * Paso 22 - Verificar el promedio con la formula 
		Promedio de venta = total de ventas /numero de semanas consideradas
		
		nota las semanas que no se consideran son
		-las semanas marcadas con *** no son consideras debido que no se realizaron ventas en ningun dia de la semana marcada 
		-el dia presente debido que aun no concluye la fecha administrativa
		-la semana mostrada en blanco sin registros debido que aun no corre.
	 */
	addStep("Verificar el promedio con la formula \r\n"
			+ "Promedio de venta = total de ventas /numero de semanas consideradas\r\n"
			+ "\r\n"
			+ "nota las semanas que no se consideran son\r\n"
			+ "-las semanas marcadas con *** no son consideras debido que no se realizaron ventas en ningun dia de la semana marcada \r\n"
			+ "-el dia presente debido que aun no concluye la fecha administrativa\r\n"
			+ "-la semana mostrada en blanco sin registros debido que aun no corre.\r\n");
	validacion = ProvDirectos.validaPromediosDetalleDeVenta();
	assertTrue(validacion,"Error, Las cantidades promedio no corresponden a la formula aplicada");
	
	/*
	 * Paso 23 - Verificar que solo este tomando en cuenta tres semanas en la formula debido que solo se registraron ventas en 3 de las 4 semanas 
	 */
	addStep("Verificar que solo este tomando en cuenta una semana en la formula debido que solo se registraron ventas en una semana de las 4");
	validacion = ProvDirectos.validaSemanasConVentas(3);
	assertTrue(validacion,"Error, El articulo tuvo ventas en mas de 3 semanas");
	
	/*
	 * Eliminar ventas realizadas en el caso
	 */
	ProvDirectos.eliminarVentasSimuladas(OAD.SkusEvo);
	
	//=======================================Cerrar Aplicaciones========================================================//
	Thread.sleep(5000);
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	//==================================================================================================================//
    
  }
  
  /*
   * Proveedores Directos MVP3: MTC-FT-141 Promedio de  Venta Diaria
   * Desc: Validar que se muestren el promedio correspondientes en la ventana Detalle de Ventas. 
   * 
   * @autor Alex Aquino
   * @date 21/10/2024
   */
  

  @Override
  public String setTestFullName() {
  	return "ATC_FT_141_PromedioDeVentaDiaria";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que se muestren el promedio correspondientes en la ventana Detalle de Ventas.\r\n"
  			+ "";
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
  	return "\"1.	Contar con la versión más reciente del APK Proveedores directos \r\n"
  			+ "2. Contar con un dispositivo móvil con Android 11\r\n"
  			+ "3.  Contar con un Código QR de pedido de HC.\r\n"
  			+ "4. Elegir 3 artículos vigentes de una fuente de abastos\r\n"
  			+ "5. Con el script “agregar_ventas” proporcionado por el equipo DEV generar ventas aleatorias al artículo 1 a solo una semana de las -4 \r\n"
  			+ "6.- Generar ventas aleatorias al articulo 2 a solo 3 semanas de las -4\r\n"
  			+ "7.-Generar ventas aleatorias al articulo 3 en las 4 semanas \"\r\n";
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
          OxxoAlDia.ObtenerFAValidaNubeEvo(10, 3);
          OxxoAlDia.obtenerSkusValidosxFANubeEvo(3, 3);
          OxxoAlDia.simularVentas(1, OAD.SkusEvo.get(0), 2, data.get("IpPV"));
          OxxoAlDia.simularVentas(3, OAD.SkusEvo.get(1), 4, data.get("IpPV"));
          OxxoAlDia.simularVentas(4, OAD.SkusEvo.get(2), 6, data.get("IpPV"));
          System.out.println("Simulacion de ventas realizadas......");
      } catch (Exception e) {
          Assert.fail("Error de conexion a la BD");
      }
  }

}
