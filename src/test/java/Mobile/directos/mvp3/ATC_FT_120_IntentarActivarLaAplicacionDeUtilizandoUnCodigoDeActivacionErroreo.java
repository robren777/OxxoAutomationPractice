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


public class ATC_FT_120_IntentarActivarLaAplicacionDeUtilizandoUnCodigoDeActivacionErroreo extends BaseExecution {
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_120_IntentarActivarLaAplicacionDeUtilizandoUnCodigoDeActivacionErroreo_test(HashMap<String, String> data) throws Exception {
	DesiredCapabilities cap = new DesiredCapabilities();
	
	cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
	cap.setCapability("deviceName", data.get("deviceName"));
	cap.setCapability("platformVersion", data.get("platformVersion"));
	cap.setCapability("platformName", data.get("platformName"));
	cap.setCapability("appPackage", data.get("appPackageP"));
	cap.setCapability("appActivity", data.get("appActivityP"));
	cap.setCapability("appium:settings[enableMultiWindows]", true);
	//cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
			
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
	assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
		+ e.getMessage());
		e.printStackTrace();
	}

	//=======================================================================================================//		
	//Llamado al OM de Proveedores Dierectos
	ProveedoresDirectos ProvDirectos = new ProveedoresDirectos(testCase, appiumUtil, this);
	//=======================================================================================================//
	/*
	 * Paso 1 - Abrir la aplicacion Proveedores directos
	 */
	addStep("Abrir la aplicación Proveedores directos");
	testCase.addScreenShotCurrentStep(appiumUtil,"testImage");
	
	/*
	 * Paso 2 - Se muestra la pantalla de Recibo Agil
	 */
	addStep("Se muestra la pantalla de Recibo Agil");
	/*
	 * Paso 3 - Se muestran los elementos princiaples de la pantalla
	 */
	addStep("Se muestran los elementos princiaples de la pantalla");
	/*
	 * Paso 4 - Se valida existencia de los siguientes elementos:
		* Logo Oxxo
		*Titulo: "Bienvenido a Recibo Ágil"
		*Texto: "Para activar la aplicación, favor de seguir los siguientes pasos:"
		*Paso 1: "Cargar Archivo de Validación"
		*Paso 2: "Captura el Código de Activación"
		*Boton: "COMENZAR" (habilitado)
	 */
	addStep("Se valida existencia de los siguientes elementos:\r\n"
			+ "* Logo Oxxo\r\n"
			+ "*Titulo: \"Bienvenido a Recibo Ágil\"\r\n"
			+ "*Texto: \"Para activar la aplicación, favor de seguir los siguientes pasos:\"\r\n"
			+ "*Paso 1: \"Cargar Archivo de Validación\"\r\n"
			+ "*Paso 2: \"Captura el Código de Activación\"\r\n"
			+ "*Boton: \"COMENZAR\" (habilitado)");
	boolean validacion = ProvDirectos.pantallaReciboAgil();
    assertTrue(validacion,"La pantalla principal Recibo Agil no se muestra de manera correcta.");
    
    /*
     * Paso 5 - Seleccionar el botón: COMENZAR
     */
    addStep("Seleccionar el botón: COMENZAR");
    validacion = ProvDirectos.seleccionarComenzarRA();
    assertTrue(validacion,"El botón no se encuentra disponible.");
    
    /*
     * Paso 6 - Se muestra la pantalla de Recibo Ágil para la carga del Archivo de Validación
     * Se muestran los elementos princiaples de la pantalla
     * Se valida existencia de los siguientes elementos:
		* Logo Oxxo
		*Titulo: "Bienvenido a Recibo Ágil"
		*Texto: "1 Cargar Archivo de Validación"
		*Boton: "Cargar archivo"
		*Boton: "CONTINUAR" (deshabilitado)
     */
    addStep("Se muestra la pantalla de Recibo Ágil para la carga del Archivo de Validación\r\n"
    		+ "\r\n"
    		+ "Logo Oxxo\r\n"
    		+ "Titulo: \"Bienvenido a Recibo Ágil\"\r\n"
    		+ "Texto: \"1 Cargar Archivo de Validación\"\r\n"
    		+ "Boton: \"Cargar archivo\"\r\n"
    		+ "Boton: \"CONTINUAR\" (deshabilitado)");
    validacion = ProvDirectos.pantallaReciboAgilCargaArchivos();
    assertTrue(validacion,"La pantalla principal para cargar Archivo de Recibo Agil no se muestra de manera correcta.");
    
    /*
     * Paso 7 - Seleccionar el boton: Cargar archivo
     */
    addStep("Seleccionar el boton: Cargar archivo");
    validacion = ProvDirectos.seleccionarCargarArchivoRA();
    assertTrue(validacion,"El botón no se encuentra disponible.");
    
    /*
     * Paso 8 - Se muestra el contenido de dispositivo para elegir el archivo de validacion a cargar.
     */
    addStep("Se muestra el contenido de dispositivo para elegir el archivo de validacion a cargar.");
    validacion = ProvDirectos.existenciaContenidoDispositivo();
    assertTrue(validacion,"La pantalla de Contenido del dispositivo no se muestra de manera correcta.");
    
    /*
     * Paso 9 - Seleccionar el archivo a cargar (valido y encriptado)
     */
    addStep("Seleccionar el archivo a cargar (valido y encriptado)");
    ProvDirectos.seleccionarArchivoValidoyEncriptado();
    
    /*
     * Paso 10 - Se valida que el archivo de validacion es cargado cargado correntamente
     	*Mensaje: "Archivo cargado correctamente"
     	*Boton: CONTINUAR (habilitado)
     */
    addStep("Se valida que el archivo de validacion es cargado cargado correntamente\r\n"
    		+ "*Mensaje: \"Archivo cargado correctamente\"\r\n"
    		+ "*Boton: CONTINUAR (habilitado)");
    validacion = ProvDirectos.mensajeArchivoCargado();
    assertTrue(validacion,"Error, No  se encontro el mensaje: Archivo cargado correctamente");
    validacion = ProvDirectos.validarContinuarHabilitado();
    assertTrue(validacion,"Error, Boton Continuar NO habilitado");
    
    /*
     * Paso 11 - Seleccionar boton: CONTINUAR
     */
    addStep("Seleccionar boton: CONTINUAR");
    ProvDirectos.tapBtnContinuarRA();
    
    /*
     * Paso 12 - Se muestra la pantalla: Captura el Código de Activación
     */
    addStep("Se muestra la pantalla: Captura el Código de Activación");
    validacion = ProvDirectos.validaExistePantallaCapturaCodigoAct();
    assertTrue(validacion,"Error, No se mostro la pantalla: Captura el Codigo de Activacion");
    
    /*
     * Paso 13 - Ingresar un codigo de activacion erroneo en el campo: Ingresa el código
     */
    addStep("Ingresar un codigo de activacion erroneo en el campo: Ingresa el codigo");
    ProvDirectos.ingresarCodigoDeActivacion_RA(data.get("codAct"));
    
    /*
     * Paso 14 - EL codigo de activacion queda capturado de manera correcta en el campo
     */
    addStep("EL codigo de activacion queda capturado de manera correcta en el campo");
    validacion= ProvDirectos.validaCodigoActivacionIngresado(data.get("codAct"));
    assertTrue(validacion,"Error, El codigo de Activacion No quedo capturado");
    
    /*
     * Paso 15 - El sistema detecta que se ingreso un codigo de activacion erroneo y muestra un mensaje de error
     */
    addStep("El sistema detecta que se ingreso un codigo de activacion erroneo y muestra un mensaje de error");
    /*
     * Paso 16 - *Se muestra el mensaje: "El código no coincide. Verifícalo e ingrésalo nuevamente."
     *Icono de error <!>
     */
    addStep("*Se muestra el mensaje: \"El código no coincide. Verifícalo e ingrésalo nuevamente.\"\r\n"
    		+ "*Icono de error <!>");
    validacion = ProvDirectos.validaMsgCodActIncorrecto_RA();
    assertTrue(validacion,"Error, No se encontro el mensaje de que el codigo de activacion Ingresado fue erroneo");
    
	
    //=======================================Cerrar Aplicaciones========================================================//
  	Thread.sleep(5000);
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
    //==================================================================================================================//
  
  }
  
  /*
   * Proveedores Directos MVP3: MTC-FT-120 Intentar activar la aplicación de utilizando un codigo de activacion erroneo
   * Desc: Validar que la aplicación regresa a la pantalla Herramientas al cancelar una devolucion donde no se agrego ningun articulo usando el boton Back y laopcion salir 
   * 
   * @autor Alex Aquino
   * @date 17/10/2024
   */
  

  @Override
  public String setTestFullName() {
  	return "ATC_FT_120_IntentarActivarLaAplicacionDeUtilizandoUnCodigoDeActivacionErroreo";
  }
  
  @Override
  public String setTestDescription() {
  	return "Validar que la aplicación regresa a la pantalla Herramientas al cancelar una devolucion donde no se agrego ningun articulo usando el boton Back y laopcion salir";
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
  	return "1. HandHeld\r\n"
  			+ "2. Usuario y Contraseña de Aplicación\r\n"
  			+ "3. Comunicación entre HH y POS";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
