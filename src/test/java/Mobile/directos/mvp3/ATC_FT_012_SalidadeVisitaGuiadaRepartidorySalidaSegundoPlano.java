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


public class ATC_FT_012_SalidadeVisitaGuiadaRepartidorySalidaSegundoPlano extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_012_SalidadeVisitaGuiadaRepartidorySalidaSegundoPlano_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
		cap.setCapability("appium:settings[enableMultiWindows]", true);
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
			
  
  //••••••••••••••••••••••••••••••••••••Llamado al OM de Login••••••••••••••••••••••••••••••••••••//		
	
  Login LoginDirectos = new Login(testCase, appiumUtil, this);
        	          
  //••••••••••••••••••••••••••••••••••••Llamado al OM de Oxxo Al Dia••••••••••••••••••••••••••••••••••••//
        	          
  OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
  
  //••••••••••••••••••••••••••••••••••••Llamado al OM de Proveedores Directos••••••••••••••••••••••••••••••••••••//
  
  ProveedoresDirectos ProvDirectos = new ProveedoresDirectos(testCase, appiumUtil, this);
  
  /*
   * 2 - Se muestra pantalla principal de Proveedores Directos.
   */
 
  addStep("Se muestra pantalla principal de Proveedores Directos");	
  
  boolean pantallaPrincipalProveedoresDirectos = ProvDirectos.pantallaPrincipalProveedoresDirectos();
  assertTrue(pantallaPrincipalProveedoresDirectos,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta."); 
  
  
  /*
   * 3 - Seleccionar opcion “Capacitacion con Visita Guiada”.
   */
  
  addStep("Seleccionar opcion “Capacitacion con Visita Guiada”.");
  
  boolean opcionCapacitacionVisitaGuiada = ProvDirectos.opcionCapacitacionVisitaGuiada();
  assertTrue(opcionCapacitacionVisitaGuiada,"El botón no se encuentra disponible");
  
  
  /*
   * 4 - Se muestra la pantalla Capacitacion con Visita Guiada.
   */
  
  addStep("Se muestra la pantalla Capacitacion con Visita Guiada.");
  
  boolean pantallaPrincipalCapacitacionVisitaGuiada = ProvDirectos.pantallaPrincipalCapacitacionVisitaGuiada();
  assertTrue(pantallaPrincipalCapacitacionVisitaGuiada,"La pantalla principal de la sección Capacitación de Visita Guiada no se muestra de manera correcta.");

  
  /*
   * 5 - Seleccionar la opcion "Surtido Tradicional (Venta a bordo)"”.
   */
  
  addStep("Seleccionar la opcion \"Surtido Tradicional (Venta a bordo)\".");
  
  boolean opcionSurtidoTradicional = ProvDirectos.opcionSurtidoTradicional();
  assertTrue(opcionSurtidoTradicional,"El botón no se encuentra disponible");
  
  
  /*
   * 6 - Validar que muestre pantalla donde indique las dos formas de realizar el pedido:
   * 		"Por Captura Manual Escaneando artículo por artículo"
			"Por Herramienta de Compra"
   */
  
  addStep("Validar que muestre pantalla donde indique las dos formas de realizar el pedido:");
  
  boolean pantallaSurtidoTradicional = ProvDirectos.pantallaSurtidoTradicional();
  assertTrue(pantallaSurtidoTradicional,"La pantalla principal de Surtido Tradicional no se muestra de manera correcta.");
  
  
  /*
   * 7 - Seleccionar la opcion "Por Captura Manual Escaneando artículo por artículo".
   */
  
  addStep("Seleccionar la opcion \"Por Captura Manual Escaneando artículo por artículo\"");
  
  boolean opcionCapturaManualSurtidoTradicional = ProvDirectos.opcionCapturaManualSurtidoTradicional();
  assertTrue(opcionCapturaManualSurtidoTradicional,"El botón no se encuentra disponible");
  
  
  /*
   * 8 - Validar que muestre la pantalla Generacion de Pedido con el mensaje anclado
   *  "Estás realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados 
   *  o almacenados en el punto de venta." [Salir]
   */
  
  addStep("Validar que muestre la pantalla Generacion de Pedido  con el mensaje anclado \"Estás realizando "
  		+ "un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados "
  		+ "en el punto de venta.\" [Salir]");
  
  //Tiempo de espera que realiza para iniciar el proceso de Visita Guiada.
  Thread.sleep(5000);
  
  boolean mensajeProcesoDeVisitaGuiada = ProvDirectos.mensajeProcesoDeVisitaGuiada();
  assertTrue(mensajeProcesoDeVisitaGuiada,"El mensaje que se encuentra en el flujo de Visita Guiada no se muestra de manera correcta.");
  
  
  /*
   * 9 - Colocar aplicación "Proveedores Directos" en segundo plano del dispositivo.
   */
  
  addStep("Colocar aplicación \"Proveedores Directos\" en segundo plano del dispositivo.");
  
  boolean botonHOME = ProvDirectos.botonHOME();
  assertTrue(botonHOME,"El botón no se encuentra disponible");
  
  
  /*
   * 10 - Se deja de mostrar pantalla de Capacitacion con Visita Guiada.
   */
  
  addStep("Se deja de mostrar pantalla de Capacitacion con Visita Guiada");
  
  boolean ausenciaPantallaVisitaGuiadaSurtidoTradicionalPorCapturaManual = ProvDirectos.ausenciaPantallaVisitaGuiadaSurtidoTradicionalPorCapturaManual();
  assertTrue(ausenciaPantallaVisitaGuiadaSurtidoTradicionalPorCapturaManual,"La Pantalla Visita Guiada Surtido Tradicional Por Captura Manual de la sección Capacitación de Visita Guiada esta habilitada.");  
  
  /*
   * 11 - Abrir aplicacion “Proveedores Directos”.
   */
  
  addStep("Abrir aplicacion “Proveedores Directos");  
  Thread.sleep(3000);
  appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
  testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
  
  
  /*
   * 12 - Se muestra correctamente el mismo modulo de Capacitacion con Visita Guiada.
   */
  
  addStep("Se muestra correctamente el mismo modulo de Capacitacion con Visita Guiada.");
  
  boolean existenciaPantallaVisitaGuiadaSurtidoTradicionalPorCapturaManual = ProvDirectos.existenciaPantallaVisitaGuiadaSurtidoTradicionalPorCapturaManual();
  assertTrue(existenciaPantallaVisitaGuiadaSurtidoTradicionalPorCapturaManual,"La Pantalla Visita Guiada Surtido Tradicional Por Captura Manual no se muestra de manera correcta.");
  
  
  /*
   * 13 - Seleccionar botón “Salir” del mensaje anclado en la  Visita Guiada
   */
  
  addStep("Seleccionar botón “Salir” del mensaje anclado en la  Visita Guiada");
  
  boolean opcionSalirDeVisitaGuiada = ProvDirectos.opcionSalirDeVisitaGuiada();
  assertTrue(opcionSalirDeVisitaGuiada,"El botón no se encuentra disponible");
  
  
  /*
   * 14 - Validar que se muestre mensaje "¿Estas seguro que deseas salir de la Visita Guiada? [No][Si]".
   */
  
  addStep("Validar que se muestre mensaje \"¿Estas seguro que deseas salir de la Visita Guiada? [No][Si]\".");
  
  boolean mensajeSalirDeVisitaGuiada = ProvDirectos.mensajeSalirDeVisitaGuiada();
  assertTrue(mensajeSalirDeVisitaGuiada,"El mensaje de Warning no se muestra de manera correcta.");
  
  
  /*
   * 15 - Seleccionar la opcion "Si" 
   */
  
  addStep("Seleccionar la opcion \"Si\"");
  
  boolean opcionSiGeneral = ProvDirectos.opcionSiGeneral();
  assertTrue(opcionSiGeneral,"El botón no se encuentra disponible");
  
  
  /*
   * 16 - Validar que nos posicione en la pantalla de "Bienvenida"
   */
 
  addStep("Validar que nos posicione en la pantalla de \"Bienvenida\"");	
  
  boolean pantallaPrincipalProveedoresDirectoss = ProvDirectos.pantallaPrincipalProveedoresDirectos();
  assertTrue(pantallaPrincipalProveedoresDirectoss,"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta."); 
  
  
  /*
   * 17 - Seleccionar opcion “Capacitacion con Visita Guiada”.
   */
  
  addStep("Seleccionar opcion “Capacitacion con Visita Guiada”.");
  
  boolean opcionCapacitacionVisitaGuiadaa = ProvDirectos.opcionCapacitacionVisitaGuiada();
  assertTrue(opcionCapacitacionVisitaGuiadaa,"El botón no se encuentra disponible");
  
  
  /*
   * 18 - Seleccionar la opcion "Surtido Tradicional (Venta a bordo)"”.
   */
  
  addStep("Seleccionar la opcion \"Surtido Tradicional (Venta a bordo)\".");
  
  boolean opcionSurtidoTradicionall = ProvDirectos.opcionSurtidoTradicional();
  assertTrue(opcionSurtidoTradicionall,"El botón no se encuentra disponible");
  
  
  /*
   * 19 - Continuar con el flujo hasta llegar a la pantalla “Detalle de pedido” de la Hand Held.
   */
  
  addStep("Continuar con el flujo hasta llegar a la pantalla “Detalle de pedido” de la Hand Held.");
  boolean flujoSurtidoTradicionalVisitaGuiadaSinEvidencia = ProvDirectos.flujoSurtidoTradicionalVisitaGuiadaSinEvidencia();
  assertTrue(flujoSurtidoTradicionalVisitaGuiadaSinEvidencia,"El botón no se encuentra disponible");
    
  Thread.sleep(3000);
  appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
  
  
  LoginDirectos.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
  
  OxxoAlDia.ingresarDetallePedidoVisitaGuiadaSinEvidencia();
  
  
  /*
   * 20 - Validar que muestre la pantalla Generacion de Pedido con el mensaje anclado
   *  "Estás realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados 
   *  o almacenados en el punto de venta." [Salir]
   */
  
  addStep("Validar que muestre la pantalla Generacion de Pedido  con el mensaje anclado \"Estás realizando "
  		+ "un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados "
  		+ "en el punto de venta.\" [Salir]");
  
  //Tiempo de espera que realiza para iniciar el proceso de Visita Guiada.
  Thread.sleep(5000);
  
  boolean mensajeProcesoDeVisitaGuiadas = OxxoAlDia.mensajeProcesoDeVisitaGuiada();
  assertTrue(mensajeProcesoDeVisitaGuiadas,"El mensaje que se encuentra en el flujo de Visita Guiada no se muestra de manera correcta.");
  
  
  /*
   * 21 - Colocar aplicación "Oxxo al día" en segundo plano del dispositivo.
   */
  
  addStep("Colocar aplicación \"Oxxo al día\" en segundo plano del dispositivo.");
  
  boolean botonHOMEE = OxxoAlDia.botonHOME();
  assertTrue(botonHOMEE,"El botón no se encuentra disponible");
  
  
  /*
   * 22 - Se deja de mostrar pantalla de Capacitacion con Visita Guiada.
   */
  
  addStep("Se deja de mostrar pantalla de Capacitacion con Visita Guiada");
  
  boolean ausenciaPantallaVisitaGuiada = OxxoAlDia.ausenciaPantallaVisitaGuiada();
  assertTrue(ausenciaPantallaVisitaGuiada,"La Pantalla Visita Guiada esta habilitada."); 
  
  
  /*
   * 23 - Abrir aplicacion “Oxxo al Día”.
   */
  
  addStep("Abrir aplicacion “Oxxo al Día”");  
  
  appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
  Thread.sleep(3000);
  
  /*
   * 24 - Se muestra correctamente el mismo modulo de Capacitacion con Visita Guiada.
   */
  
  addStep("Se muestra correctamente el mismo modulo de Capacitacion con Visita Guiada.");
  
  boolean existenciaPantallaVisita = OxxoAlDia.existenciaPantallaVisita();
  assertTrue(existenciaPantallaVisita,"La Pantalla Visita Guiada Surtido Tradicional Por Captura Manual no se muestra de manera correcta.");
  
  
  /*
   * 25 - Seleccionar botón “Salir” del mensaje anclado en la  Visita Guiada
   */
  
  addStep("Seleccionar botón “Salir” del mensaje anclado en la  Visita Guiada");
  
  boolean opcionSalirDeVisitaGuiadas = OxxoAlDia.opcionSalirDeVisitaGuiada();
  assertTrue(opcionSalirDeVisitaGuiadas,"El botón no se encuentra disponible");
  
  
  /*
   * 26 - Validar que se muestre mensaje "¿Estas seguro que deseas salir de la Visita Guiada? [No][Si]".
   */
  
  addStep("Validar que se muestre mensaje \"¿Estas seguro que deseas salir de la Visita Guiada? [No][Si]\".");
  
  Thread.sleep(3000);
  boolean mensajeSalirDeVisitaGuiadas = OxxoAlDia.mensajeSalirDeVisitaGuiada();
  assertTrue(mensajeSalirDeVisitaGuiadas,"El mensaje de Warning no se muestra de manera correcta.");
  
  
  /*
   * 27 - Seleccionar la opcion "Si" 
   */
  
  addStep("Seleccionar la opcion \"Si\"");
  
  boolean opcionSiGenerall = OxxoAlDia.opcionSiGeneral();
  assertTrue(opcionSiGenerall,"El botón no se encuentra disponible");
  
  
  /*
   * 28 - Validar que nos posicione en la pantalla de "PROVEEDORES DIRECTOS" 
   */
  
  addStep("Validar que nos posicione en la pantalla de \"PROVEEDORES DIRECTOS\".");
  
  boolean existenciaPantallaVerificacióndeMercanciaPedidos = OxxoAlDia.existenciaPantallaVerificacióndeMercanciaPedidos();
  assertTrue(existenciaPantallaVerificacióndeMercanciaPedidos,"La pantalla Principal de Verificación de Mercancia Pedidos Proveedor Directo no se muestra de manera correcta.");
  
//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
	
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
  Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
  
//••••••••••••••••••••••••••••••••••••Cerrar Aplicaciones••••••••••••••••••••••••••••••••••••//
  
  }
  
  /*
   * Nombre del caso de prueba Manual: MTC-FT-012 Salida de visita guiada repartidor y salida segundo plano
   * 
   * Desc: Validar que al encontrarnos dentro de visita guiada pedido Repartidor tradicional permita dejar 
   * la aplicación en segundo plano y al regresar a la aplicación continue en el mismo menú, estando en esta misma 
   * pantalla se valida que se pueda salir del menú a la pantalla principal presionando el botón Salir.
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
  	return "ATC_FT_061_CasoPrueba61";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que al encontrarnos dentro de visita guiada pedido Repartidor tradicional permita dejar la aplicación en "
  			+ "segundo plano y al regresar a la aplicación continue en el mismo menú, estando en esta misma pantalla se valida"
  			+ " que se pueda salir del menú a la pantalla principal presionando el botón Salir.";
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
