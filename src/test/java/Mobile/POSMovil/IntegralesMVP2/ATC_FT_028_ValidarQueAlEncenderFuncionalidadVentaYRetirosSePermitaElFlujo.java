package Mobile.POSMovil.IntegralesMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.SQLCMDFormatter;
import Mobile.POSMovil.om.LoginPM;
import Mobile.POSMovil.om.PMCapabilities;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-028 Validar que al encender funcionalidad Venta y Retiros se permita el flujo.
* DESCRIPCIÓN: Al tener habilitado la funcionalidad de Venta y Retiros se permite el flujo Retiros.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Tener habilitado funcionalidad Venta y Retiros
* 
* INSUMOS:
* SKU de articulos
* 
* @autor Marcos Morales
* @date 04/07/2025
*/
//========================================================================//

public class ATC_FT_028_ValidarQueAlEncenderFuncionalidadVentaYRetirosSePermitaElFlujo extends BaseExecution {
	
  @Test(dataProvider = "data-provider", enabled = true)
  public void ATC_FT_028_ValidarQueAlEncenderFuncionalidadVentaYRetirosSePermitaElFlujo_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		//=============================================================================================//
		System.out.println("Cerrando aplicacion si está abierta");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Thread.sleep(2000);
		//=============================================================================================//
		
//		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
//		cap.setCapability("deviceName", data.get("deviceName"));
//		cap.setCapability("platformVersion", data.get("platformVersion"));
//		cap.setCapability("platformName", data.get("platformName"));
//		cap.setCapability("appPackage", data.get("appPackage"));
//		cap.setCapability("appActivity", data.get("appActivity"));
//		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
				
//		String url = "http://127.0.0.1:4723";

		/************************ Ingresar a la App ***************************/
		addStep("Iniciar Aplicación POS Móvil");
  
  	AppiumUtil appiumUtil = null;
  	try {
  	//appiumUtil = new AppiumUtil(url, cap);
  		appiumUtil = PMCapabilities.initializeAppium("Login");
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
  	
  	Thread.sleep(500);
  	testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
//=================================================================================================================//		
  /*
   * Llamado al OM de Login de PosMovil.
   */
  LoginPM loginPM = new LoginPM(testCase, appiumUtil, this);
  /*
   * Llamado al OM de PosMovil.
   */
  PM posmovil = new PM(testCase, appiumUtil, this);
  
//================================================================================================================//

	/************************ Deshabilita el permiso ***************************/
	posmovil.deshabilitarHabilitarPermiso(data.get("userLogin"),
			PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("crTienda"), 4, "Venta y Retiros", 9,
			"Retiro de efectivo", 1);
	
	/************************ Inicio de sesión ***************************/
	loginPM.verificarLoginTiendaUserCrV2(data.get("crTienda"),data.get("salInic"));

	addStep("Permisos Habilitados en la NUBE");
	String permisos = SQLCMDFormatter.formatResultSet(posmovil.obtienePermisosBDNube(data.get("crTienda")));
	System.out.println(permisos);
	testCase.addTextEvidenceCurrentStep(permisos);
	
	/************************
	 * Paso 1 - Ingresar al Journal
	 ***************************/
	addStep("Ingresar al Journal ");
	boolean ingresarJournal = posmovil.ingresarJournal();
	assertTrue(ingresarJournal, "Journal no encontrado");

	/************************
	 * Paso 2 - Realizar ventas hasta llegar a el monto maximo configurado.
	 ***************************/
	addStep("Realizar ventas hasta llegar a el monto maximo configurado.");
	posmovil.realizarMultiplesVentasMostrarRetiros(data.get("crTienda"),data.get("ipPv"));

	/************************
	 * Paso 3 - Validar que se muestre mensaje Aviso 1 de 3 "Favor de realizar el
	 * retiro"
	 ***************************/
	addStep("Validar que se muestre mensaje Aviso 1 de 3 \"Favor de realizar el retiro\"");
	String mensaje = posmovil.obtieneMensajeAvisoRetiro();
	Assert.assertEquals(mensaje, "Aviso 1 de 3");

	/************************
	 * Paso 4 - Presionar el boton realizar retiro
	 ***************************/
	addStep("Presionar el boton realizar retiro");
	posmovil.clickBotonConfirmar();

	/************************
	 * Paso 5 - Validar se muestre campo para ingresar contraseña
	 ***************************/
	addStep("Validar se muestre campo para ingresar contraseña");
	boolean edoPass = posmovil.validarCampoContrasena();
	Assert.assertTrue(edoPass, "No se encontro el campo contraseña");

	/************************
	 * Paso 6 - Ingresar contraseña
	 ***************************/
	addStep("Ingresar contraseña");
	loginPM.ingresarCtrs(loginPM.passUser(data.get("crTienda")));

	/************************
	 * Paso 7 - Presionar boton Aceptar
	 ***************************/
	addStep("Presionar boton Aceptar");
	posmovil.clickBotonConfirmar();
	
	/************************
	 * Paso 8 - Validar se muestre pantalla retiro de dinero
	 ***************************/
	addStep("Validar se muestre pantalla retiro de dinero");
	boolean edoModalRetiro = posmovil.validarTituloModalRetiro();
	Assert.assertTrue(edoModalRetiro, "No se encontro el modal de Retiro");

	/************************
	 * Paso 9 - Ingresar cantidad a retirar
	 ***************************/
	addStep("Ingresar cantidad a retirar");
	posmovil.ingresarMontoDeRetiro();

	/************************
	 * Paso 10 - Presionar Aceptar
	 ***************************/
	addStep("Presionar Aceptar ");
	posmovil.clickBotonConfirmar();
	
	/************************
	 * Paso 11 - Validar mensanje "El monto a retirar es de'', ¿Desea continuar?"
	 ***************************/
	addStep("Validar mensanje \"El monto a retirar es de'', ¿Desea continuar?\"");
	boolean edoMensajeRetiro = posmovil.validarMensajeDeRetiro();
	Assert.assertTrue(edoMensajeRetiro, "No se encontro el Mensaje");

	/************************
	 * Paso 12 - Presionar Aceptar
	 ***************************/
	addStep("Presionar Aceptar ");
	posmovil.clickBotonConfirmar2();
	
	/************************
	 * Paso 13 - Validar mensaje "Se ha realizado el retiro de manera exitosa"
	 ***************************/
	addStep("Validar mensaje \"Se ha realizado el retiro de manera exitosa\"");
	boolean edoMensaje = posmovil.validarTituloModalRetiro();
	Assert.assertTrue(edoMensaje, "No se encontro el Mensaje");

	/************************
	 * Paso 14 - Validar regrese al inicio del Journal.
	 ***************************/
	addStep("Validar regrese al inicio del Journal.");
	posmovil.esperaVentanaJournalInicial();
  
//=======================================Cerrar Aplicaciones========================================================//
	Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
  }

  @Override
  public String setTestFullName() {
  	return "MTC-FT-028 Validar que al encender funcionalidad Venta y Retiros se permita el flujo.";
  }
  
  @Override
  public String setTestDescription() {
  	return "Al tener habilitado la funcionalidad de Venta y Retiros se permite el flujo Retiros.";
  }  

  @Override
  public String setTestDesigner() {
  	return "Marcos Morales";
  }  

  @Override
  public String setTestInstanceID() {
  	return null;  
  }
  
  @Override
  public String setPrerequisites() {
  	return "1.- Versión mas actualizada de APK\r\n"
  			+ "2.- Tener conexión a la bd de la nube\r\n"
  			+ "3 - Contar con Impresoras Epson y Zebra\r\n"
  			+ "4.- Tener habilitado funcionalidad Venta y Retiros";
  }
  
  @Override
  public void beforeTest() {
  	
  }  

}
