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


public class ATC_FT_080_MensajeDeArticulosSinSeleccionarAlModificarunQR extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_080_MensajeDeArticulosSinSeleccionarAlModificarunQR_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
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
   * Paso 10 - Introducir cantidades aleatorias a los skus.
   */
  
  addStep("Introducir cantidades aleatorias a los skus.");
  validacion = ProvDirectos.incrementarCantidades();
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
   * Paso 15 - Seleccionar todos los artículos (Check all).
   */
  
  addStep("Seleccionar todos los artículos (Check all).");
  
  validacion = ProvDirectos.checkAllSkus();
  assertTrue(validacion,"No se permite seleccionar el check box");
  
  /*
   * Paso 16 -  Validar que todos los artículos se visualicen seleccionados, el campo No. artículos Capturados 
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
   * Paso 17 - Presionar el botón “Generar QR”
   */
  
  addStep("Presionar el botón “Generar QR”");
  
  validacion = ProvDirectos.selecGenerarQR();
  assertTrue(validacion,"No se permite seleccionar el botón para generar el QR.");
  
  /*
   * Paso 18 - Validar que se genere el QR
   */
  
  addStep("Validar que se genere el QR");
  
  validacion = ProvDirectos.validarGenQR();
  assertTrue(validacion,"No se genero el Qr de manera correcta.");
  
  /*
   * Paso 19 - Seleccionar la opción <MODIFICAR QR>
   */
  
  addStep("Seleccionar la opción <MODIFICAR QR>");
  
  validacion = ProvDirectos.selecModificarQR();
  assertTrue(validacion,"No se permite seleccionar el botón para generar el QR.");
  
  /*
   * Paso 20 - Se muestra el mensaje “¿Estás seguro de regresar a editar el pedido?[CANCELAR][CONFIRMAR]
   * Paso 21 - Seleccionar la opción confirmar
   */
  
  addStep("Se muestra el mensaje “¿Estás seguro de regresar a editar el pedido?[CANCELAR][CONFIRMAR]"
  		+ "Seleccionar la opción confirmar");
  
  validacion = ProvDirectos.msgEditarPedido();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  
  /*
   * Paso 22 - Agregar con el botón [+] unidades a dos artículos.
   */
  
  addStep("Agregar con el botón [+] unidades a dos artículos.");
  validacion = ProvDirectos.incrementarCantidades2();
  assertTrue(validacion,"No se pudo aumentar de manera correcta las cantidades.");
  
  /*
   * Paso 22 - Seleccionar Continuar.
   */
  
  addStep("Seleccionar Continuar.");
  
  validacion = ProvDirectos.boton_ContinuarGeneracionPedido();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 23 - Muestra mensaje: ¿Está seguro que desea terminar el escaneo de productos? SI/NO
   * Paso 24 - Selecciona la opción: Si
   */
  
  addStep("Muestra mensaje: ¿Está seguro que desea terminar el escaneo de productos? SI/NO"
  		+ "Selecciona la opción: Si");
  
  validacion = ProvDirectos.msgTerminarEscaneo();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  
  /*
   * Paso 25 -  Validar que todos los artículos se visualicen seleccionados, el campo No. artículos Capturados 
   * y  Cantidad Capturada, se visualizan de acuerdo a los agregados en la generación de pedido original y 
   * los campos No. artículos Entregados y Cantidad Entregada se actualizan de acuerdo a lo seleccionado. 
   * El botón "Generar QR" se habilita
   */
  
  addStep("Validar que todos los artículos se visualicen seleccionados, el campo No. artículos Capturados y "
  		+ "Cantidad Capturada, se visualizan de acuerdo a los agregados en la generación de pedido original y "
  		+ "los campos No. artículos Entregados y Cantidad Entregada");
  
  validacion = ProvDirectos.pantallaResumenArticulosADescargar2();
  assertTrue(validacion,"No se visualizan de manera correcta las cantidades.");
  
  /*
   * Paso 26 - Presionar el botón “Generar QR”
   */
  
  addStep("Presionar el botón “Generar QR”");
  
  validacion = ProvDirectos.selecGenerarQR();
  assertTrue(validacion,"No se permite seleccionar el botón para generar el QR.");
  
  /*
   * Paso 27 - Validar que muestre el mensaje “Artículos sin seleccionar… Faltan 2 artículos por seleccionarse del pedido original. 
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
   * Nombre del caso de prueba Manual: MTC-FT-080 Mensaje de Artículos sin seleccionar al modificar un QR
   * 
   * Desc: •	Al solo faltar un artículo por seleccionar
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
  	return "ATC_FT_091_CasoPrueba91";
  }
  
  @Override
  public String setTestDescription() {
  	return "•Al solo faltar un artículo por seleccionar\r\n"
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
  	return "1-Contar con el APK más reciente de “Proveedores Directos”\r\n"
  			+ "2-Contar con una fuente de abastos valida\r\n"
  			+ "3-Contar con artículos en vigencia 1 ";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
