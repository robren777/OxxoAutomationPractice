package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
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


public class ATC_FT_089_SalidaDeVisitaGuiadaPrecargadoRepleSinergiaYSalidaSegundoPlano extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_089_SalidaDeVisitaGuiadaPrecargadoRepleSinergiaYSalidaSegundoPlano_test(HashMap<String, String> data) throws Exception {
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
	
	//======= Cerrar Aplicaciones =========
	Thread.sleep(5000);
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
	//======================================================================================================
	AppiumUtil appiumUtil = null;
	/*
	 * Paso 1 - Abrir aplicacion “Proveedores Directos”
	 */
	addStep("Abrir aplicacion “Proveedores Directos”");
	
	try {
		appiumUtil = new AppiumUtil(url, cap);
	} catch (SessionNotCreatedException e) {
	assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
		+ e.getMessage());
		e.printStackTrace();
	}
	appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
	testCase.addScreenShotCurrentStep(appiumUtil,"testImage");
	//=======================================================================================================//		
	//Llamado al OM de Login.
	Login LoginDirectos = new Login(testCase, appiumUtil, this);
	//Llamado al OM de Oxxo Al Dia.
	OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
	//Llamado al OM de Proveedores Dierectos
	ProveedoresDirectos ProvDirectos = new ProveedoresDirectos(testCase, appiumUtil, this);
	//=======================================================================================================//
	/*
	 * Paso 2- Se muestra pantalla principal de Proveedores Directos 
	 */
	addStep("Se muestra pantalla principal de Proveedores Directos");
	boolean validacion = ProvDirectos.pantallaPrincipalProveedoresDirectos();
	assertTrue(validacion,"Error, La aplicacion no se ejecuto(abrio) de manera correcta");
	
	/*
	 * Paso 3 - Seleccionar opcion “Capacitacion con Visita Guiada”
	 */
	addStep("Seleccionar opcion: Capacitacion con Visita Guiada");
	ProvDirectos.opcionCapacitacionVisitaGuiada();
	
	/*
	 * Paso 4 - Se muestra la pantalla Capacitacion con Visita Guiada 
	 */
	addStep("Se muestra la pantalla Capacitacion con Visita Guiada ");
	validacion = ProvDirectos.pantallaPrincipalCapacitacionVisitaGuiada();
	assertTrue(validacion,"Error, No se encontro la pantalla de Capacitacion con Visista Guiada");
	
	/*
	 * Paso 5 - Seleccionar “Pedido Precargado (Sinergia/Reple)”
	 */
	addStep("Seleccionar “Pedido Precargado (Sinergia/Reple)”");
	ProvDirectos.opcionPedidoPrecargadoSinergiaReple();
	
	/*
	 * Paso 6 - Se muestra pantalla de carga de capacitación con visita guiada: Logotipo de Oxxo, Texto: Comenzando Capacitación con visita guiada
	 */
	addStep("Se muestra pantalla de carga de capacitación con visita guiada\r\n"
			+ "Logotipo de Oxxo\r\n"
			+ "Texto: Comenzando Capacitación con visita guiada\r\n");
	ProvDirectos.validaPantallaCargaCapacitacionVisitaGuiada();
	
	/*
	 * Paso 7 - Validar que se muestre pantalla “Generación De Pedido”
	 */
	addStep("Validar que se muestre pantalla 'Generación De Pedido'");
	ProvDirectos.pantallaGeneracionDePedidoCapaciationGuiada();
	
	/*
	 * Paso 8 - se resalta opción Escanear QR de pedido o QR Hta. de compra
	 */
	addStep("se resalta opción 'Escanear QR de pedido o QR Hta. de compra'");
	testCase.addScreenShotCurrentStep(appiumUtil,"testImage");
	
	/*
	 * Paso 9 - Colocar aplicación "Proveedores Directos" en segundo plano del dispositivo
	 */
	addStep("Colocar aplicación \"Proveedores Directos\" en segundo plano del dispositivo");
	ProvDirectos.enviarAplicacionASegundoPlano();
	
	/*
	 *Paso 10 - Se deja de mostrar pantalla de Capacitacion con Visita Guiada 
	 */
	addStep("Se deja de mostrar pantalla de Capacitacion con Visita Guiada");
	validacion = ProvDirectos.validaAppSegundoPlanoCapacitacionGuiada();
	assertTrue(validacion,"Error, La aplicacion no fue enviada a segundo plano");
	
	/*
	 * Paso 11 - Abrir aplicacion “Proveedores Directos”
	 */
	addStep("Abrir aplicacion Proveedores Directos");
	appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
	appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
	testCase.addScreenShotCurrentStep(appiumUtil,"testImage");
	
	/*
	 * Paso 12 - Se muestra correctamente el mismo modulo de Capacitacion con Visita Guiada
	 */
	addStep("Se muestra correctamente el mismo modulo de Capacitacion con Visita Guiada");
	ProvDirectos.pantallaGeneracionDePedidoCapaciationGuiada();
	
	/*
	 * Paso 13 - Seleccionar botón “Salir” del mensaje anclado en la  Visita Guiada
	 */
	addStep("Seleccionar botón 'Salir' del mensaje anclado en la  Visita Guiada");
	ProvDirectos.opcionSalirDeVisitaGuiada();
	
	/*
	 * Paso 14 - Validar que se muestre mensaje "¿Estas seguro que deseas salir de la Visita Guiada? [No][Si]"
	 */
	addStep("Validar que se muestre mensaje ¿Estas seguro que deseas salir de la Visita Guiada? [No][Si]");
	validacion = ProvDirectos.mensajeSalirDeVisitaGuiada();
	assertTrue(validacion,"Error, No se encontro el mensaje: '¿Estas seguro que deseas salir de la Visita Guiada? [No][Si]'");
	
	/*
	 * Paso 15 - Seleccionar la opcion "Si"
	 */
	addStep("Seleccionar la opcion: Si");
	ProvDirectos.opcionSiGeneral();
	
	/*
	 * Paso 16 - Validar que nos posicione en la pantalla de "Bienvenida"
	 */
	addStep("Validar que nos posicione en la pantalla de 'Bienvenida'");
	validacion = ProvDirectos.pantallaPrincipalProveedoresDirectos();
	assertTrue(validacion,"Error, No se encontro la pantallade Bienvenida al salir de la visita guiada");
	
	/*
	 * Paso 17 - Seleccionar opcion “Capacitacion con Visita Guiada”
	 */
	addStep("Seleccionar opcion: Capacitacion con Visita Guiada");
	ProvDirectos.opcionCapacitacionVisitaGuiada();
	
	/*
	 * Paso 18 - Seleccionar “Pedido Precargado (Sinergia/Reple)”
	 */
	addStep("Seleccionar: Pedido Precargado (Sinergia/Reple)");
	ProvDirectos.opcionPedidoPrecargadoSinergiaReple();
	
	/*
	 * Paso 19 - Continuar con el flujo hasta llegar a la pantalla “Detalle de pedido” de la Hand Held
	 */
	addStep("Continuar con el flujo hasta llegar a la pantalla 'Detalle de pedido' de la Hand Held");
	ProvDirectos.llegarPantallaDetallePedidoVisitaGuiadaRepleSinEV();
	ProvDirectos.enviarAplicacionASegundoPlano();
	appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
	LoginDirectos.login(data.get("userLogin"),PasswordUtil.decryptPassword(data.get("passwordLogin")));
	OxxoAlDia.llegarPantallaDetallePedidoVisitaGuiadaRepleSinEV2();
	
	/*
	 * Paso 20 - Validar que se muestre el mensaje anclado "Estás realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta." [Salir]
	 */
	addStep("Validar que se muestre el mensaje anclado Estás realizando un proceso en la visita guiada de capacitación, estos datos no serán guardados o almacenados en el punto de venta, [Salir]");
	validacion = OxxoAlDia.validaMensajeProcesoVisitaGuiada();
	assertTrue(validacion,"Error no se encontro el mensaje de que se esta realizado la visita guiada");
	
	/*
	 * Paso 21 - Colocar aplicación "Oxxo al día" en segundo plano del dispositivo.
	 */
	addStep("Colocar aplicación \"Oxxo al día\" en segundo plano del dispositivo.");
	OxxoAlDia.enviarAplicacionASegundoPlano();
	
	/*
	 * Paso 22 - Se deja de mostrar pantalla de Capacitacion con Visita Guiada
	 */
	addStep("Se deja de mostrar pantalla de Capacitacion con Visita Guiada");
	validacion = OxxoAlDia.validaAppSegundoPlanoCapacitacionGuiada();
	assertTrue(validacion,"Error, la aplicacion OAD aun se encuentra abierta");
	
	/*
	 * Paso 23 - Abrir aplicacion “Oxxo al Día”
	 */
	addStep("Abrir aplicacion 'Oxxo al Día'");
	appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
	
	/*
	 * Paso 24 - Se muestra correctamente el mismo modulo de Capacitacion con Visita Guiada
	 */
	addStep("Se muestra correctamente el mismo modulo de Capacitacion con Visita Guiada");
	validacion = OxxoAlDia.validaProcesoVisitaGuiadaEnProceso();
	assertTrue(validacion,"Error, La aplicacion Ya no se encontro dentro del proceso de Visita Guiada");
	
	/*
	 * Paso 25 - Seleccionar botón “Salir” del mensaje anclado en la  Visita Guiada
	 */
	addStep("Seleccionar botón 'Salir' del mensaje anclado en la Visita Guiada");
	OxxoAlDia.opcionSalirVisitaGuiada();
	
	/*
	 * Paso 26 - Validar que se muestre mensaje "¿Estas seguro que deseas salir de la Visita Guiada? [No][Si]"
	 */
	addStep("Validar que se muestre mensaje: ¿Estas seguro que deseas salir de la Visita Guiada? [No][Si]");
	validacion = OxxoAlDia.validaMensajeSalirVisitaGuiada();
	assertTrue(validacion,"Error, No se encontro el mesaje: ¿Estas seguro que deseas salir de la Visita Guiada? [No][Si]\");");
	
	/*
	 * Paso 27 - Seleccionar la opción "Si"
	 */
	addStep("Seleccionar la opción: 'Si'");
	OxxoAlDia.opcionSiSalirMensajevisitaGuiada();
	
	/*
	 * Paso 28 - Validar que nos posicione en la pantalla de "PROVEEDORES DIRECTOS"
	 */
	addStep("Validar que nos posicione en la pantalla de 'PROVEEDORES DIRECTOS'");
	validacion = OxxoAlDia.validaSalidaProcesoCapacitacionDeVisitaGuiada();
	assertTrue(validacion,"Error, La aplicacion no termino el proceso de visita guiada al seleccionar la opcion de SALIR");
	
    //=======================================Cerrar Aplicaciones========================================================//
  	Thread.sleep(5000);
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    //==================================================================================================================//
  
  }
  
  /*
   * Proveedores Directos MVP3: MTC-FT-089 Salida de visita guiada precargado (Reple/Sinergía)y salida segundo plano
   * Desc: Validar que al encontrarnos dentro de visita guiada pedido Precargado permita dejar la aplicación en segundo plano y al regresar a la aplicación continue en el mismo menú, estando en esta misma pantalla se valida que se pueda salir del menú a la pantalla principal presionando el botón Salir 
   * 
   * @autor Alex Aquino
   * @date 11/10/2024
   */
  

  @Override
  public String setTestFullName() {
  	return "ATC_FT_089_SalidaDeVisitaGuiadaPrecargadoRepleSinergiaYSalidaSegundoPlano";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que al encontrarnos dentro de visita guiada pedido Precargado permita dejar la aplicación en segundo plano y al regresar a la aplicación continue en el mismo menú, estando en esta misma pantalla se valida que se pueda salir del menú a la pantalla principal presionando el botón Salir";
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
  	return "1. Contar con la versión mas reciente del APK Proveedores directos \r\n"
  			+ "2. Contar con un dispositivo móvil con Android 11";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
