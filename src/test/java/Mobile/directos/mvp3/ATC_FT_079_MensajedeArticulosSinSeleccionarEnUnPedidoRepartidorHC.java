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


public class ATC_FT_079_MensajedeArticulosSinSeleccionarEnUnPedidoRepartidorHC extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_079_MensajedeArticulosSinSeleccionarEnUnPedidoRepartidorHC_test(HashMap<String, String> data) throws Exception {
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

		//=======================================Cerrar Aplicaciones========================================================//
		  Thread.sleep(5000);
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		//==================================================================================================================//	
  
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
			
//=======================================================================================================//		
  /*
   * Llamado al OM de Login.
   */

  Login LoginDirectos = new Login(testCase, appiumUtil, this);
  
  /*
   * Llamado al OM de Oxxo Al Dia.
   */
  OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
  
  /*
   * Llamado al OM de Recibo Agil.
   */
  ProveedoresDirectos ProvDirectos = new ProveedoresDirectos(testCase, appiumUtil, this);
  
//=======================================================================================================//
  
  /*
   * -  Pre-Requisito - Generación de Código QR
   */
  
  LoginDirectos.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
  OxxoAlDia.ingresarModuloHerramientasSinEvidencia();
  OxxoAlDia.selec_VerificacionMercanciaSE();
  OxxoAlDia.selec_GenHerramientaCompraSE();
  OxxoAlDia.selec_NoGeneralSE();
  OxxoAlDia.generacionQR_HC_Repartidor_SE();
  
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
   * Paso 9 - Introducir cantidades aleatorias a los skus.
   */
  
  addStep("Introducir cantidades aleatorias a los skus.");
  validacion = ProvDirectos.incrementarCantidadesTradicionalHC();
  assertTrue(validacion,"No se pudo aumentar de manera correcta las cantidades.");
  
  /*
   * Paso 10 - Seleccionar Continuar.
   */
  
  addStep("Seleccionar Continuar.");
  
  validacion = ProvDirectos.boton_ContinuarGeneracionPedidoHCTradicional();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 11 - ? Validar se muestre pantalla de Resumen de pedido donde se visualiza: 
				Nombre de la pantalla "Resumen de artículos a descargar del camión". Los artículos capturados en la generación de pedido en forma de listado con su cantidad correspondiente (Radio Button para check al inicio de cada articulo y uno en la parte superior para poder seleccionar todos los articulos, Articulo, Descripción y Cant.)
				Campo No. De Artículos capturados 
				Campo No. De artículos entregados
				Cantidad capturada
				Cantidad entregada
				Botón Modificar pedido
				Botón Generar QR (deshabilitado)
   */
  
  addStep("? Validar se muestre pantalla de Resumen de pedido donde se visualiza: \r\n"
  		+ "Nombre de la pantalla \"Resumen de artículos a descargar del camión\". Los artículos capturados en la generación de pedido en forma de listado con su cantidad correspondiente (Radio Button para check al inicio de cada articulo y uno en la parte superior para poder seleccionar todos los articulos, Articulo, Descripción y Cant.)\r\n"
  		+ "Campo No. De Artículos capturados \r\n"
  		+ "Campo No. De artículos entregados\r\n"
  		+ "Cantidad capturada\r\n"
  		+ "Cantidad entregada\r\n"
  		+ "Botón Modificar pedido\r\n"
  		+ "Botón Generar QR (deshabilitado)");
  
  validacion = ProvDirectos.pantallaResumenArticulosADescargar();
  assertTrue(validacion,"La pantalla no se muestra de manera correcta.");
  
  /*
   * Paso 12 - Seleccionar mediante el radio button una parte de los artículos que se muestran en el listado.
   */
  
  addStep("Seleccionar mediante el radio button una parte de los artículos que se muestran en el listado.");
  
  validacion = ProvDirectos.checkFirstSku();
  assertTrue(validacion,"No se permite seleccionar el check box");
  
  /*
   * Paso 13 - Presionar el botón “Generar QR”
   */
  
  addStep("Presionar el botón “Generar QR”");
  
  validacion = ProvDirectos.selecGenerarQR();
  assertTrue(validacion,"No se permite seleccionar el botón para generar el QR.");
  
  /*
   * Paso 14 - Validar que muestre el mensaje “Artículos sin seleccionar… Faltan 2 artículos por seleccionarse del pedido original. 
   * En caso de continuar, estos artículos no serán considerados dentro del pedido. [volver][CONTINUAR]”
   */
  
  addStep("Validar que muestre el mensaje “Artículos sin seleccionar… Faltan 2 artículos por seleccionarse del pedido original."
  		+ "En caso de continuar, estos artículos no serán considerados dentro del pedido. [volver][CONTINUAR]”");
  
  validacion = ProvDirectos.msgFaltan2Articulos();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  
  /*
   * Paso 15 - Presionar volver
   */
  
  addStep("Presionar volver");
  
  validacion = ProvDirectos.opcionNo();
  assertTrue(validacion,"No se permite seleccionar el botón");
  
  /*
   * Paso 16 - Seleccionar todos los artículos (Check all).
   */
  
  addStep("Seleccionar todos los artículos (Check all).");
  
  validacion = ProvDirectos.checkAllSkus();
  assertTrue(validacion,"No se permite seleccionar el check box");
  
  /*
   * Paso 17 -  Validar que todos los artículos se visualicen seleccionados, el campo No. artículos Capturados 
   * y  Cantidad Capturada, se visualizan de acuerdo a los agregados en la generación de pedido original y 
   * los campos No. artículos Entregados y Cantidad Entregada se actualizan de acuerdo a lo seleccionado. 
   * El botón "Generar QR" se habilita
   */
  
  addStep("Validar que todos los artículos se visualicen seleccionados, el campo No. artículos Capturados y "
  		+ "Cantidad Capturada, se visualizan de acuerdo a los agregados en la generación de pedido original y "
  		+ "los campos No. artículos Entregados y Cantidad Entregada se actualizan de acuerdo a lo seleccionado. "
  		+ "El botón \"Generar QR\" se habilita");
  
  validacion = ProvDirectos.validarCantidades3CheckboxDePedidoTradicionalHC();
  assertTrue(validacion,"No se visualizan de manera correcta las cantidades.");
  
  /*
   * Paso 18 - Deseleccionar todos los artículos (Ckeck all).
   */
  
  addStep("Deseleccionar todos los artículos (Ckeck all).");
  
  validacion = ProvDirectos.checkAllSkus();
  assertTrue(validacion,"No se permite seleccionar el check box");
  
  /*
   * Paso 23 - Validar que se deseleccionen todos los artículos 
   */
  
  addStep("Validar que se deseleccionen todos los artículos ");
  
  validacion = ProvDirectos.validarSkusUnCheck();
  assertTrue(validacion,"Se encuentra seleccionado un Radio Button");
  
  /*
   * Paso 24 - Seleccionar todos los artículos (Check all).
   */
  
  addStep("Seleccionar todos los artículos (Check all).");
  
  validacion = ProvDirectos.checkAllSkus();
  assertTrue(validacion,"No se permite seleccionar el check box");
  
  /*
   * Paso 23 - Validar que se muestren todos los artículos seleccionados  
   */
  
  addStep("Validar que se muestren todos los artículos seleccionados");
  
  validacion = ProvDirectos.validarSkusCheck();
  assertTrue(validacion,"Se encuentra sin seleccionar un Radio Button");
  
  /*
   * Paso 24 - Presionar el botón “Generar QR”
   */
  
  addStep("Presionar el botón “Generar QR”");
  
  validacion = ProvDirectos.selecGenerarQR();
  assertTrue(validacion,"No se permite seleccionar el botón para generar el QR.");
  
  /*
   * Paso 25 - Validar que se genere el QR
   */
  
  addStep("Validar que se genere el QR");
  
  validacion = ProvDirectos.validarGenQR();
  assertTrue(validacion,"No se genero el Qr de manera correcta.");
   
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
  
  }
  
  /*
   * Nombre del caso de prueba Manual: MTC-FT-079 Mensaje de Artículos sin seleccionar en un pedido Repartidor HC
   * 
   * Desc: •	Validar que permita seleccionar todos los artículos de manera manual
		   •	Validar que permita seleccionar y deseleccionar con el check all
           •	Mensaje “Artículos sin seleccionar… Faltan 2 artículos por seleccionarse del pedido original. 
           		En caso de continuar, estos artículos no serán considerados dentro del pedido. [volver][CONTINUAR]”.
   * Prerequisitos:
	 1. HandHeld
	 2. Usuario y Contraseña de Aplicación.
	 3. Comunicación entre HH y POS.
	 4. Contar con un QR tipo 2 de un flujo Repartidor HC
   * 
   * @autor Alejandro Valdez
   * @date 23/10/2024
   */

  @Override
  public String setTestFullName() {
  	return "ATC_FT_090_CasoPrueba90";
  }
  
  @Override
  public String setTestDescription() {
  	return "•	Validar que permita seleccionar todos los artículos de manera manual\r\n"
  			+ "•	Validar que permita seleccionar y deseleccionar con el check all\r\n"
  			+ "•	Mensaje “Artículos sin seleccionar… Faltan 2 artículos por seleccionarse del pedido original. En caso de continuar, estos artículos no serán considerados dentro del pedido. [volver][CONTINUAR]”\r\n"
  			+ "";
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
  	return "1-Contar con el APK mas reciente de “Proveedores Directos”\r\n"
  			+ "2-Contar con un QR tipo 2 de un flujo Repartidor HC";
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
