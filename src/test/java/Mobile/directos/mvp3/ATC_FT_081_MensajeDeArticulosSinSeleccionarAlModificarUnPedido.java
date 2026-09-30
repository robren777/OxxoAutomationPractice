package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.ProveedoresDirectos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;


public class ATC_FT_081_MensajeDeArticulosSinSeleccionarAlModificarUnPedido extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_081_MensajeDeArticulosSinSeleccionarAlModificarUnPedido_test(HashMap<String, String> data) throws Exception {
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
     * Llamado al OM de Recibo Agil.
     */
    ProveedoresDirectos ProvDirectos = new ProveedoresDirectos(testCase, appiumUtil, this);
    
  //=======================================================================================================//
  
  
  /*
   * Paso 1 - Abrir aplicacion “Proveedores Directos”.
   */
    
  appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
  Thread.sleep(2000);
  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  
  /*
   * Paso 2 - Muestra Pantalla de Bienvenida.
   */
  Thread.sleep(5000);
  addStep("Muestra Pantalla de Bienvenida");	
  
  boolean validacion = ProvDirectos.pantallaPrincipalProveedoresDirectos();
  assertTrue(validacion,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta."); 
  
  /*
   * Paso 3 - Seleccionar Botón "Comenzar proceso de Recepción de mercancía".
   */
  
  addStep("Seleccionar Botón \"Comenzar proceso de Recepción de mercancía\"");
  
  validacion = ProvDirectos.opcionComenzarProcesoRepecionMercancia();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 4 -  Muestra pantalla Generar pedido, en su estado inicial.
   */
  
  addStep("Se muestra pantalla principal de Generación de Pedido");
  
  validacion = ProvDirectos.pantallaPrincipalGeneraciondePedido();
  assertTrue(validacion,"La pantalla principal Generación de Pedidos no se muestra de manera correcta.");
  
  /*
   * Paso 5 - Seleccionar opción "Generar Pedido y Entrega".
   */
  
  addStep("Seleccionar opción \"Generar Pedido y Entrega\".");
  
  validacion = ProvDirectos.boton_GenPedidoEntrega();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 6 - La opción queda seleccionada.
   */
  
  addStep("La opción queda seleccionada.");
  
  validacion = ProvDirectos.validaOpcCumplioProCambFis();
  assertTrue(validacion,"La opción no se encuentra seleccionada.");
  
  /*
   * Paso 7 - Ingresar Fuente de Abasto Válida
   */
  
  ProvDirectos.obtenerSkusRelacionadosFaValida(3);
  addStep("Ingresar Fuente de Abasto Válida.");
  
  validacion = ProvDirectos.ingresar_FaValidaPV();
  assertTrue(validacion,"No se pudo ingresar la FA de manera correcta.");
  
  /*
   * Paso 8 - El campo se llena correctamente. 
   */
  
  addStep("El campo se llena correctamente.");
  
  validacion = ProvDirectos.validacionCampoLlenoFA();
  assertTrue(validacion,"La opción no se encuentra seleccionada.");
  
  /*
   * Paso 9 - Ingresar n cantidad de artículos (válidos) más al listado del pedido. 
   */
  
  addStep("Ingresar n cantidad de artículos (válidos) más al listado del pedido.");
  
  validacion = ProvDirectos.ingresar_Skus();
  assertTrue(validacion,"No se pudo ingresar los Skus de manera correcta.");
  
  /*
   * Paso 10 - Validar que los Articulos se agregan al listado del pedido.
   */
  
  addStep("Validar que los Articulos se agregan al listado del pedido.");
  validacion = ProvDirectos.ValidarSkusIngresados();
  assertTrue(validacion,"No se encontraron los botones que contienen los articulos correspondientes.");
  
  
  /*
   * Paso 11 - Introducir cantidades aleatorias a los skus.
   */
  
  addStep("Introducir cantidades aleatorias a los skus.");
  validacion = ProvDirectos.incrementarCantidades();
  assertTrue(validacion,"No se pudo aumentar de manera correcta las cantidades.");
  
  /*
   * Paso 12 - Se ingresan las cantidades aleatorias, El campo No. Artículos se llenará con los skus agregados 
   * al generar el pedido y el campo Total de Piezas deberá coincidir con la suma de las cantidades agregada de cada uno de ellos.
   */
  
  addStep("Se ingresan las cantidades aleatorias, El campo No. Artículos se llenará con los skus agregados"
  		+ "al generar el pedido y el campo Total de Piezas deberá coincidir con la suma de las cantidades agregada de cada uno de ellos.");
  
  validacion = ProvDirectos.skus_postIncremento();
  assertTrue(validacion,"No se pudo aumentar de manera correcta las cantidades.");
  
  /*
   * Paso 13 - Seleccionar Continuar.
   */
  
  addStep("Seleccionar Continuar.");
  
  validacion = ProvDirectos.boton_ContinuarGeneracionPedido();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 14 - Muestra mensaje: ¿Está seguro que desea terminar el escaneo de productos? SI/NO
   */
  
  addStep("Muestra mensaje: ¿Está seguro que desea terminar el escaneo de productos? SI/NO");
  
  validacion = ProvDirectos.msgTerminarEscaneo();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  ProvDirectos.opcionSiGeneralSE();
  
  /*
   * Paso 15 - Seleccionar algunos los artículos.
   */
  
  addStep("Seleccionar algunos los artículos.");
  
  validacion = ProvDirectos.checkFirstSku();
  assertTrue(validacion,"No se permite seleccionar el check box");
  
  /*
   * Paso 16 - ? Validar que los artículos seleccionados se visualicen seleccionados, el campo No. artículos Capturados 
   * y  Cantidad Capturada, se visualizan de acuerdo a los agregados en la generación de pedido original y los campos 
   * No. artículos Entregados y Cantidad Entregada se actualizan de acuerdo a lo seleccionado. El botón "Generar QR" se habilita
   */
  
  addStep("? Validar que los artículos seleccionados se visualicen seleccionados, el campo No. artículos Capturados "
  		+ "y  Cantidad Capturada, se visualizan de acuerdo a los agregados en la generación de pedido original y los campos "
  		+ "No. artículos Entregados y Cantidad Entregada se actualizan de acuerdo a lo seleccionado.");
  
  validacion = ProvDirectos.validarCantidades1Checkbox();
  assertTrue(validacion,"No se visualizan de manera correcta las cantidades.");
  
  /*
   * Paso 17 - Presionar el botón “Generar QR”
   */
  
  addStep("Presionar el botón “Generar QR”");
  
  validacion = ProvDirectos.selecGenerarQR();
  assertTrue(validacion,"No se permite seleccionar el botón para generar el QR.");
  
  /*
   * Paso 18 - Validar que muestre el mensaje “Artículos sin seleccionar… Faltan 2 artículos por seleccionarse del pedido original. 
   * En caso de continuar, estos artículos no serán considerados dentro del pedido. [volver][CONTINUAR]”
   */
  
  addStep("Validar que muestre el mensaje “Artículos sin seleccionar… Faltan 2 artículos por seleccionarse del pedido original."
  		+ "En caso de continuar, estos artículos no serán considerados dentro del pedido. [volver][CONTINUAR]”");
  
  validacion = ProvDirectos.msgFaltan2Articulos();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  
  
//=======================================Cerrar Aplicaciones========================================================//
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
  
  }
 
  /*
   * Nombre del caso de prueba Manual: MTC-FT-081 Mensaje de Artículos sin seleccionar al modificar un pedido 
   * 
   * Desc: •	Al no seleccionar ningún articulo
		•	Al solo faltar un artículo por seleccionar
		•	Mensaje “Artículos sin seleccionar… Faltan 2 artículos por seleccionarse del pedido original. 
		En caso de continuar, estos artículos no serán considerados dentro del pedido. [volver][CONTINUAR]”
   * Prerequisitos:
	 1. HandHeld
	 2. Usuario y Contraseña de Aplicación.
	 3. Comunicación entre HH y POS.
	 4. Contar con una fuente de abastos valida
	 5. Contar con artículos en vigencia 1
   * 
   * @autor Alejandro Valdez
   * @date 23/10/2024
   */
  

  @Override
  public String setTestFullName() {
  	return "ATC_FT_092_CasoPrueba92";
  }
  
  @Override
  public String setTestDescription() {
  	return "•Al no seleccionar ningún articulo\r\n"
  			+ "•Al solo faltar un artículo por seleccionar\r\n"
  			+ "•Mensaje “Artículos sin seleccionar… Faltan 2 artículos por seleccionarse del pedido original. "
  			+ "En caso de continuar, estos artículos no serán considerados dentro del pedido. [volver][CONTINUAR]”";
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
  	return "1-	Contar con el APK más reciente de “Proveedores Directos”\r\n"
  			+ "2-	Contar con una fuente de abastos valida\r\n"
  			+ "3-	Contar con artículos en vigencia 1 ";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
