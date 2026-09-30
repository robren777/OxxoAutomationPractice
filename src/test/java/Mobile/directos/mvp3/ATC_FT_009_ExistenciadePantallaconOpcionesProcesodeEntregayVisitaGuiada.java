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


public class ATC_FT_009_ExistenciadePantallaconOpcionesProcesodeEntregayVisitaGuiada extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_009_ExistenciadePantallaconOpcionesProcesodeEntregayVisitaGuiada_test(HashMap<String, String> data) throws Exception {
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

		
  	/************************ Paso 1 - Abrir aplicacion “Proveedores Directos” ***************************/
  	addStep("Abrir aplicacion “Proveedores Directos”");		
  
  	AppiumUtil appiumUtil = null;
  	try {
  		appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(3500);
  	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
			
  
  //••••••••••••••••••••••••••••••••••••Llamado al OM de Proveedores Directos••••••••••••••••••••••••••••••••••••//
  	
  ProveedoresDirectos ProvDirectos = new ProveedoresDirectos(testCase, appiumUtil, this);
  
  /*
   * 2 - Se muestra pantalla principal de Proveedores Directos con los siguientes componentes:
	  		Logotipo de Oxxo
			Texto: ¡Bienvenido!
			Opcion: Comenzar proceso de Recepcion de mercancia
			Opcion: Capacitacion con Visita Guiada
   */
 
  addStep("Se muestra pantalla principal de Proveedores Directos con los siguientes componentes:"
  		+ "Logotipo de Oxxo"
  		+ "Texto: ¡Bienvenido!"
  		+ "Opcion: Comenzar proceso de Recepcion de mercancia"
  		+ "Opcion: Capacitacion con Visita Guiada");	
  
  boolean validacion = ProvDirectos.pantallaPrincipalProveedoresDirectos();
  assertTrue(validacion,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");
  
  /*
   * 3 - Seleccionar opcion “Comenzar proceso de Recepcion de mercancia”.
   */
  
  addStep("Seleccionar opcion “Comenzar proceso de Recepcion de mercancia”");	
  
  validacion = ProvDirectos.opcionComenzarProcesoRepecionMercancia();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  
  /*
   * 4 - Se muestra pantalla principal Generación de pedido, en su estado inicial con las siguientes opciones:
			Opcion: "Actualizar catalogo" (habilitado)
			Opcion: "Asistí a tienda sin entrega" (habilitado)
			Radio button: "Generar Pedido/Preventa"
			Radio button: "Generar Pedido y Entrega(Tradicional)"
			Radio button: "Escanear QR de pedido o QR de Hta. de compra”
			Cuadro de texto "Fuentes de abasto"
			Cuadro de texto "Código de barras"
			Botón de <Escanear Código de barras> (habilitado)
   */
 
  addStep("Se muestra pantalla principal Generación de pedido, en su estado inicial con las siguientes opciones:\r\n"
  		+ "Opcion: \"Actualizar catalogo\" (habilitado)\r\n"
  		+ "Opcion: \"Asistí a tienda sin entrega\" (habilitado)\r\n"
  		+ "Radio button: \"Generar Pedido/Preventa\"\r\n"
  		+ "Radio button: \"Generar Pedido y Entrega(Tradicional)\"\r\n"
  		+ "Radio button: \"Escanear QR de pedido o QR de Hta. de compra”\r\n"
  		+ "Cuadro de texto \"Fuentes de abasto\"\r\n"
  		+ "Cuadro de texto \"Código de barras\"\r\n"
  		+ "Botón de <Escanear Código de barras> (habilitado)");
  
  validacion = ProvDirectos.pantallaPrincipalGeneraciondePedido();
  assertTrue(validacion,"La pantalla principal de Generación de Pedido no se muestra de manera correcta.");
  
  /*
   * Paso 5 - Seleccionar botón flechita atrás, posicionado en la parte superior izquierda de la pantalla.
   */
  
  addStep("Seleccionar botón flechita atrás, posicionado en la parte superior izquierda de la pantalla");
  
  validacion = ProvDirectos.botonBACKGeneracionPedido();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 6 - Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.
   */
  
  addStep("Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.");
  
  validacion = ProvDirectos.mensajeRegresarMenuVisitaGuiada();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  
  /*
   * Paso 7 - Presionar “No”.
   */
  
  addStep("Presionar “No”");
  
  validacion = ProvDirectos.opcionNo();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 8 - Validar que continue en la pantalla “Generar Pedido”
   */
  
  addStep("Validar que continue en la pantalla “Generar Pedido”");
  
  validacion = ProvDirectos.pantallaPrincipalGeneraciondePedido();
  assertTrue(validacion,"La pantalla principal de Generación de Pedido no se muestra de manera correcta.");
  
  /*
   * Paso 9 - Seleccionar el botón “SALIR”
   */
  
  addStep("Seleccionar el botón “SALIR”");
  
  validacion = ProvDirectos.opcionSalirGeneracionPedido();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 10 - Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.
   */
  
  addStep("Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.");
  
  validacion = ProvDirectos.mensajeRegresarMenuVisitaGuiada();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  
  /*
   * Paso 11 - Presionar “No”.
   */
  
  addStep("Presionar “No”");
  
  validacion = ProvDirectos.opcionNo();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 12 - Validar que continue en la pantalla “Generar Pedido”	
   */
  
  addStep("Validar que continue en la pantalla “Generar Pedido”");
  
  validacion = ProvDirectos.pantallaPrincipalGeneraciondePedido();
  assertTrue(validacion,"La pantalla principal de Generación de Pedido no se muestra de manera correcta.");
  
  /*
   * Paso 13 - Presionar el botón “BACK” nativo de Android posicionado en la esquina inferior izquierda.
   */
  
  addStep("Presionar el botón “BACK” nativo de Android posicionado en la esquina inferior izquierda.");
  
  validacion = ProvDirectos.botonBACK();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 14 - Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.
   */
  
  addStep("Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.");
  
  validacion = ProvDirectos.mensajeRegresarMenuVisitaGuiada();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  
  /*
   * Paso 15 - Presionar “No”.
   */
  
  addStep("Presionar “No”");
  
  validacion = ProvDirectos.opcionNo();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 16 - Validar que continue en la pantalla “Generar Pedido”	
   */
  
  addStep("Validar que continue en la pantalla “Generar Pedido”");
  
  validacion = ProvDirectos.pantallaPrincipalGeneraciondePedido();
  assertTrue(validacion,"La pantalla principal de Generación de Pedido no se muestra de manera correcta.");
  
  /*
   * Paso 17 - Seleccionar botón flechita atrás, posicionado en la parte superior izquierda de la pantalla.
   */
  
  addStep("Seleccionar botón flechita atrás, posicionado en la parte superior izquierda de la pantalla");
  
  validacion = ProvDirectos.botonBACKGeneracionPedido();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 18 - Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.
   */
  
  addStep("Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.");
  
  validacion = ProvDirectos.mensajeRegresarMenuVisitaGuiada();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  
  /*
   * Paso 19 - Presionar la opcion “SI”.
   */
  
  addStep("Presionar la opcion “SI”");
  
  validacion = ProvDirectos.opcionSiGeneral();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 20 - Verificar que nos redireccione al menú principal menú de “¡Bienvenido!” correctamente
   */
 
  addStep("Verificar que nos redireccione al menú principal menú de “¡Bienvenido!” correctamente");	
  
  validacion = ProvDirectos.pantallaPrincipalProveedoresDirectos();
  assertTrue(validacion,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");
  
  /*
   * Paso 21 - Seleccionar opcion “Comenzar proceso de Recepcion de mercancia”.
   */
  
  addStep("Seleccionar opcion “Comenzar proceso de Recepcion de mercancia”");	
  
  validacion = ProvDirectos.opcionComenzarProcesoRepecionMercancia();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 22 - Se muestra pantalla “Generar Pedido”
   */
  
  addStep("Se muestra pantalla “Generar Pedido”");
  
  validacion = ProvDirectos.pantallaPrincipalGeneraciondePedido();
  assertTrue(validacion,"La pantalla principal de Generación de Pedido no se muestra de manera correcta.");
  
  /*
   * Paso 23 - Seleccionar el botón “SALIR”
   */
  
  addStep("Seleccionar el botón “SALIR”");
  
  validacion = ProvDirectos.opcionSalirGeneracionPedido();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 24 - Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.
   */
  
  addStep("Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.");
  
  validacion = ProvDirectos.mensajeRegresarMenuVisitaGuiada();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  
  /*
   * Paso 25 - Presionar la opcion “SI”.
   */
  
  addStep("Presionar la opcion “SI”");
  
  validacion = ProvDirectos.opcionSiGeneral();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 26 - Verificar que nos redireccione al menú principal menú de “¡Bienvenido!” correctamente
   */
 
  addStep("Verificar que nos redireccione al menú principal menú de “¡Bienvenido!” correctamente");	
  
  validacion = ProvDirectos.pantallaPrincipalProveedoresDirectos();
  assertTrue(validacion,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");
  
  /*
   * Paso 27 - Seleccionar opcion “Comenzar proceso de Recepcion de mercancia”.
   */
  
  addStep("Seleccionar opcion “Comenzar proceso de Recepcion de mercancia”");	
  
  validacion = ProvDirectos.opcionComenzarProcesoRepecionMercancia();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 28 - Se muestra pantalla “Generar Pedido”
   */
  
  addStep("Se muestra pantalla “Generar Pedido”");
  
  validacion = ProvDirectos.pantallaPrincipalGeneraciondePedido();
  assertTrue(validacion,"La pantalla principal de Generación de Pedido no se muestra de manera correcta.");
  
  /*
   * Paso 29 - Seleccionar botón flechita atrás, posicionado en la parte superior izquierda de la pantalla.
   */
  
  addStep("Seleccionar botón flechita atrás, posicionado en la parte superior izquierda de la pantalla");
  
  validacion = ProvDirectos.botonBACKGeneracionPedido();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 30 - Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.
   */
  
  addStep("Validar que muestre el mensaje “Al regresar al menú inicial se perderán los datos ya capturados.");
  
  validacion = ProvDirectos.mensajeRegresarMenuVisitaGuiada();
  assertTrue(validacion,"El mensaje no se muestra de manera correcta.");
  
  /*
   * Paso 31 - Presionar la opcion “SI”.
   */
  
  addStep("Presionar la opcion “SI”");
  
  validacion = ProvDirectos.opcionSiGeneral();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  /*
   * Paso 32 - Verificar que nos redireccione al menú principal menú de “¡Bienvenido!” correctamente
   */
 
  addStep("Verificar que nos redireccione al menú principal menú de “¡Bienvenido!” correctamente");	
  
  validacion = ProvDirectos.pantallaPrincipalProveedoresDirectos();
  assertTrue(validacion,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");
  
  /*
   * Paso 33 - Seleccionar opcion “Capacitacion con Visita Guiada”.
   */
  
  addStep("Seleccionar opcion “Capacitacion con Visita Guiada”.");
  
  validacion = ProvDirectos.opcionCapacitacionVisitaGuiada();
  assertTrue(validacion,"El botón no se encuentra disponible");
  
  
  /*
   * Paso 34 - Se muestra la pantalla Capacitacion con Visita Guiada con los siguientes componentes:
			Logotipo de Oxxo
			Texto: Selecciona el tipo de surtido
			Opcion: Preventa
			Opcion: Surtido Tradicional (Venta a bordo)
			Opcion: Pedido Precargado (Sinergia/Reple)
   */
  
  addStep("Se muestra la pantalla Capacitacion con Visita Guiada con los siguientes componentes:\r\n"
  		+ "Logotipo de Oxxo\r\n"
  		+ "Texto: Selecciona el tipo de surtido\r\n"
  		+ "Opcion: Preventa\r\n"
  		+ "Opcion: Surtido Tradicional (Venta a bordo)\r\n"
  		+ "Opcion: Pedido Precargado (Sinergia/Reple)");
  
  validacion = ProvDirectos.pantallaPrincipalCapacitacionVisitaGuiada();
  assertTrue(validacion,"La pantalla principal de la sección Capacitación de Visita Guiada no se muestra de manera correcta.");

  
//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
	
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
  
//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
  
  }
  
  /*
   * Nombre del caso de prueba Manual: MTC-FT-009 Existencia de pantalla con opciones proceso de  entrega y visita guiada
   * 
   * Desc: Validar la pantalla de bienvenida y sus menús.
   * 
   * Prerequisitos:
	 1. Contar con la versión mas reciente del APK Proveedores directos 
	 2. Contar con un dispositivo móvil con Android 11
   * 
   * @autor Alejandro Valdez
   * @date 10/10/2024
   */

  @Override
  public String setTestFullName() {
  	return "ATC_FT_058_CasoPrueba58";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar la pantalla de bienvenida y sus menús .";
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
  	return "1. Contar con la versión mas reciente del APK Proveedores directos \r\n"
  			+ "2. Contar con un dispositivo móvil con Android 11";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
