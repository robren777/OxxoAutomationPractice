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


public class ATC_FT_078_MensajeDeArticulosSinSeleccionarEnUnPedidoRepartidor extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_078_MensajeDeArticulosSinSeleccionarEnUnPedidoRepartidor_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
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
  ProvDirectos.opcionComenzarProcesoRepecionMercanciaSE();
  
  /*
   * Paso 2 -  Muestra pantalla Generar pedido, en su estado inicial.
   */
  
  addStep("Muestra pantalla Generar pedido, en su estado inicial");
  
  boolean validacion = ProvDirectos.pantallaPrincipalGeneraciondePedido();
  assertTrue(validacion,"La pantalla principal Generación de Pedidos no se muestra de manera correcta.");
  
  /*
   * Paso 3 - Seleccionar opción "Generar Pedido y Entrega".
   */
  
  addStep("Seleccionar opción \"Generar Pedido y Entrega\".");
  
  validacion = ProvDirectos.boton_GenPedidoEntrega();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 4 - La opción queda seleccionada.
   */
  
  addStep("La opción queda seleccionada.");
  
  validacion = ProvDirectos.validaOpcCumplioProCambFis();
  assertTrue(validacion,"La opción no se encuentra seleccionada.");
  
  /*
   * Paso 5 - Ingresar Fuente de Abasto Válida
   */
  
  ProvDirectos.obtenerSkusRelacionadosFaValida(3);
  addStep("Ingresar Fuente de Abasto Válida.");
  
  validacion = ProvDirectos.ingresar_FaValidaPV();
  assertTrue(validacion,"No se pudo ingresar la FA de manera correcta.");
  
  /*
   * Paso 6 - El campo se llena correctamente. 
   */
  
  addStep("El campo se llena correctamente.");
  
  validacion = ProvDirectos.validacionCampoLlenoFA();
  assertTrue(validacion,"La opción no se encuentra seleccionada.");
  
  /*
   * Paso 7 - Ingresar n cantidad de artículos (válidos) más al listado del pedido. 
   */
  
  addStep("Ingresar n cantidad de artículos (válidos) más al listado del pedido.");
  
  validacion = ProvDirectos.ingresar_Skus();
  assertTrue(validacion,"No se pudo ingresar los Skus de manera correcta.");
  
  /*
   * Paso 8 - Validar que los Articulos se agregan al listado del pedido.
   */
  
  addStep("Validar que los Articulos se agregan al listado del pedido.");
  validacion = ProvDirectos.ValidarSkusIngresados();
  assertTrue(validacion,"No se encontraron los botones que contienen los articulos correspondientes.");

  
  /*
   * Paso 9 - Introducir cantidades aleatorias a los skus.
   */
  
  addStep("Introducir cantidades aleatorias a los skus.");
  validacion = ProvDirectos.incrementarCantidades();
  assertTrue(validacion,"No se pudo aumentar de manera correcta las cantidades.");
  
  
  /*
   * Paso 10 - Se ingresan las cantidades aleatorias, El campo No. Artículos se llenará con los skus agregados 
   * al generar el pedido y el campo Total de Piezas deberá coincidir con la suma de las cantidades agregada de cada uno de ellos.
   */
  
  addStep("Se ingresan las cantidades aleatorias, El campo No. Artículos se llenará con los skus agregados"
  		+ "al generar el pedido y el campo Total de Piezas deberá coincidir con la suma de las cantidades agregada de cada uno de ellos.");
  
  validacion = ProvDirectos.skus_postIncremento();
  assertTrue(validacion,"No se pudo aumentar de manera correcta las cantidades.");
  
  /*
   * Paso 11 - Seleccionar Continuar.
   */
  
  addStep("Seleccionar Continuar.");
  
  validacion = ProvDirectos.boton_ContinuarGeneracionPedido();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 12 - Muestra mensaje: ¿Está seguro que desea terminar el escaneo de productos? SI/NO
   * Paso 13 - Selecciona la opción: Si
   */
  
  addStep("Muestra mensaje: ¿Está seguro que desea terminar el escaneo de productos? SI/NO"
  		+ "Selecciona la opción: Si");
  
  validacion = ProvDirectos.msgTerminarEscaneo();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  ProvDirectos.opcionSiGeneralSE();
  
  /*
   * Paso 14 - ? Validar se muestre pantalla de Resumen de pedido donde se visualiza: 
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
   * Paso 15 - Seleccionar mediante el radio button una parte de los artículos que se muestran en el listado.
   */
  
  addStep("Seleccionar mediante el radio button una parte de los artículos que se muestran en el listado.");
  
  validacion = ProvDirectos.checkFirstSku();
  assertTrue(validacion,"No se permite seleccionar el check box");
  
  /*
   * Paso 16 - Se visualiza seleccionado el radio button 
   * No. artículos Capturados y Cantidad Capturada, se visualizan de acuerdo a los agregados en Generación de Pedido 
   * y los campos No. artículos Entregados y Cantidad Entregada se actualizan de acuerdo a lo seleccionado
   */
  
  addStep("Se visualiza seleccionado el radio button "
  		+ "No. artículos Capturados y Cantidad Capturada, se visualizan de acuerdo a los agregados en Generación de Pedido "
  		+ "y los campos No. artículos Entregados y Cantidad Entregada se actualizan de acuerdo a lo seleccionado");
  
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
  
  /*
   * Paso 19 - Presionar volver
   */
  
  addStep("Presionar volver");
  
  validacion = ProvDirectos.opcionNo();
  assertTrue(validacion,"No se permite seleccionar el botón");
  
  /*
   * Paso 20 - Seleccionar todos los artículos (Check all).
   */
  
  addStep("Seleccionar todos los artículos (Check all).");
  
  validacion = ProvDirectos.checkAllSkus();
  assertTrue(validacion,"No se permite seleccionar el check box");
  
  /*
   * Paso 21 -  Validar que todos los artículos se visualicen seleccionados, el campo No. artículos Capturados 
   * y  Cantidad Capturada, se visualizan de acuerdo a los agregados en la generación de pedido original y 
   * los campos No. artículos Entregados y Cantidad Entregada se actualizan de acuerdo a lo seleccionado. 
   * El botón "Generar QR" se habilita
   */
  
  addStep("Validar que todos los artículos se visualicen seleccionados, el campo No. artículos Capturados y "
  		+ "Cantidad Capturada, se visualizan de acuerdo a los agregados en la generación de pedido original y "
  		+ "los campos No. artículos Entregados y Cantidad Entregada se actualizan de acuerdo a lo seleccionado. "
  		+ "El botón \"Generar QR\" se habilita");
  
  validacion = ProvDirectos.validarCantidades3Checkbox();
  assertTrue(validacion,"No se visualizan de manera correcta las cantidades.");
  
  /*
   * Paso 22 - Deseleccionar todos los artículos (Ckeck all).
   */
  
  addStep("Deseleccionar todos los artículos (Ckeck all).");
  
  validacion = ProvDirectos.checkAllSkus();
  assertTrue(validacion,"No se permite seleccionar el check box");
  
  /*
   * Paso 23 -  Validar se visualicen todos los artículos con radio button vacio, el campo No. artículos Capturados
   *  y  Cantidad Capturada, se visualizan de acuerdo a los agregados en el pedido original y los campos 
   *  No. artículos Entregados y Cantidad Entregada se muestran con cantidad 0. El botón "Generar QR" se deshabilita
   */
  
  addStep("Validar se visualicen todos los artículos con radio button vacio, el campo No. artículos Capturados "
  		+ "y  Cantidad Capturada, se visualizan de acuerdo a los agregados en el pedido original y los campos "
  		+ "No. artículos Entregados y Cantidad Entregada se muestran con cantidad 0. El botón \"Generar QR\" se deshabilita");
  
  validacion = ProvDirectos.pantallaResumenUnCheck();
  assertTrue(validacion,"No se visualizan de manera correcta las cantidades.");
  
  
//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
	
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
  
//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//	
  
  }
    
  /*
   * Nombre del caso de prueba Manual: MTC-FT-078 Mensaje de Artículos sin seleccionar en un pedido Repartidor
   * 
   * Desc: •Validar que permita seleccionar varios artículos de forma manual
   • Validar que permita seleccionar y deseleccionar con el check all
   • Mensaje “Artículos sin seleccionar… Faltan 2 artículos por seleccionarse del pedido original. 
   • En caso de continuar, estos artículos no serán considerados dentro del pedido. [volver][CONTINUAR]”
   • Se valida no afectación del flujo original. 
   * 
   * Prerequisitos:
	 1. HandHeld
	 2. Usuario y Contraseña de Aplicación.
	 3. Comunicación entre HH y POS.
	 4. Contar con artículos en vigencia 1
	 5. Contar con una fuente de abastos valida
   * 
   * @autor Alejandro Valdez
   * @date 22/10/2024
   */

  @Override
  public String setTestFullName() {
  	return "ATC_FT_089_CasoPrueba89";
  }
  
  @Override
  public String setTestDescription() {
  	return "•Validar que permita seleccionar varios artículos de forma manual\r\n"
  			+ "   • Validar que permita seleccionar y deseleccionar con el check all\r\n"
  			+ "   • Mensaje “Artículos sin seleccionar… Faltan 2 artículos por seleccionarse del pedido original. \r\n"
  			+ "   • En caso de continuar, estos artículos no serán considerados dentro del pedido. [volver][CONTINUAR]”\r\n"
  			+ "   • Se valida no afectación del flujo original.";
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
  	return "1-Contar con el APK más reciente de “Proveedores Directos”\r\n"
  			+ "2-Contar con una fuente de abastos valida\r\n"
  			+ "3-Contar con artículos en vigencia 1 ";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
