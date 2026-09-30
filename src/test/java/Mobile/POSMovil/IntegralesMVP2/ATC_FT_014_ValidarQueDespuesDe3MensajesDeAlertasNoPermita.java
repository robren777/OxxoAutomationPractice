package Mobile.POSMovil.IntegralesMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-014-Validar que despues de 3 mensajes de alertas no 
* permita continur con ventas
* DESCRIPCIÓN: Se realizará ventas de articulos y se 
* muestre el mensaje de retiro parcial, continuar con ventas hasta que no se 
*  permita continuar.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra}
* 
* INSUMOS:
* SKU de articulos
* Contraseña de usuario
* 
* @autor Marcos Morales
* @date 04/07/2025
*/
//========================================================================//

public class ATC_FT_014_ValidarQueDespuesDe3MensajesDeAlertasNoPermita extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_014_ValidarQueDespuesDe3MensajesDeAlertasNoPermita_test(
			HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		//=============================================================================================//
		System.out.println("Cerrando aplicacion si está abierta");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Thread.sleep(2000);
		//=============================================================================================//

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
//		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackage"));
		cap.setCapability("appActivity", data.get("appActivity"));
		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi
											// lograr evitar la vista de ingresar la IP y puerto

		String url = "http://127.0.0.1:4723";

		/************************ Ingresar a la App ***************************/
		addStep("Abrir aplicación POSMOVIL en HandHeld.");	

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
//	  		appiumUtil = PMCapabilities.initializeAppium("Login");
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n" + e.getMessage());
			e.printStackTrace();
		}

		Thread.sleep(500);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
//=================================================================================================================//		
		/*
		 * Llamado al OM de Login de PosMovil.
		 */
		LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);
		LoginPosMovil.validarArchivoVPN();  
		
		/*
		 * Llamado al OM de PosMovil.
		 */
		PM posmovil = new PM(testCase, appiumUtil, this);

//================================================================================================================//
		
		/************************ Inicio de sesión ***************************/
//		LoginPosMovil.verificarLoginTiendaUserCr(data.get("crTienda"),PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));
		LoginPosMovil.verificarLoginTiendaUserCrV2(data.get("crTienda"),data.get("salInic"));

		/************************
		 * Paso 1 - Ingresar al Journal
		 ***************************/
		addStep("Ingresar al Journal ");
		boolean ingresarJournal = posmovil.ingresarJournal();
		assertTrue(ingresarJournal, "Journal no encontrado");


		/************************
		 * Paso 8 - Realizar ventas hasta llegar a el monto maximo configurado.
		 ***************************/
		addStep("Realizar ventas hasta llegar a el monto maximo configurado.");
//		posmovil.realizarMultiplesVentasMostrarRetiros(data.get("crTienda"),data.get("ipPv"));
		posmovil.realizarMultiplesVentasMostrarRetiros(data.get("crTienda"),LoginPosMovil.obtenerIPTienda(data.get("crTienda")));

		/************************
		 * Paso 9 - Validar que se muestre mensaje Aviso 1 de 3 "Favor de realizar el
		 * retiro"
		 ***************************/
		addStep("Validar que se muestre mensaje Aviso 1 de 3 \"Favor de realizar el retiro\"");
		String mensaje = posmovil.obtieneMensajeAvisoRetiro();
		Assert.assertEquals(mensaje, "Aviso 1 de 3");
		
		/************************
		 * Paso 10 - Presionar boton Cancelar
		 ***************************/
		addStep("Presionar boton Cancelar");
		posmovil.clickBotonCancelar();

		/************************
		 * Paso 11 - Realizar ventas para mostrar el segundo aviso
		 ***************************/
		addStep("Realizar ventas hasta llegar a el monto maximo configurado.");
//		posmovil.realizarMultiplesVentasMostrarRetiros(data.get("crTienda"),data.get("ipPv"));
		posmovil.realizarMultiplesVentasMostrarRetiros(data.get("crTienda"),LoginPosMovil.obtenerIPTienda(data.get("crTienda")));

		/************************
		 * Paso 12 - Validar que se muestre mensaje Aviso 2 de 3 "Favor de realizar el
		 * retiro"
		 ***************************/
		addStep("Validar que se muestre mensaje Aviso 2 de 3 \"Favor de realizar el retiro\"");
		mensaje = posmovil.obtieneMensajeAvisoRetiro();
		Assert.assertEquals(mensaje, "Aviso 2 de 3");
		
		/************************
		 * Paso 13 - Presionar boton Cancelar
		 ***************************/
		addStep("Presionar boton Cancelar");
		posmovil.clickBotonCancelar();

		/************************
		 * Paso 14 - Realizar ventas para mostrar el tercer aviso
		 ***************************/
		addStep("Realizar ventas hasta llegar a el monto maximo configurado.");
//		posmovil.realizarMultiplesVentasMostrarRetiros(data.get("crTienda"),data.get("ipPv"));
		posmovil.realizarMultiplesVentasMostrarRetiros(data.get("crTienda"),LoginPosMovil.obtenerIPTienda(data.get("crTienda")));

		/************************
		 * Paso 15 - Validar que se muestre mensaje Aviso 3 de 3 "Favor de realizar el
		 * retiro"
		 ***************************/
		addStep("Validar que se muestre mensaje Aviso 3 de 3 \"Favor de realizar el retiro\"");
		mensaje = posmovil.obtieneMensajeAvisoRetiro();
		Assert.assertEquals(mensaje, "Aviso 3 de 3");
				
		/************************
		 * Paso 19 - Presionar boton Aceptar
		 ***************************/
		addStep("Presionar boton Aceptar");
		posmovil.clickBotonConfirmar();

		/************************
		 * Paso 20 - Validar se muestre campo para ingresar contraseña
		 ***************************/
		addStep("Validar se muestre campo para ingresar contraseña");
		boolean edoPass = posmovil.validarCampoContrasena();
		Assert.assertTrue(edoPass, "No se encontro el campo contraseña");

		/************************
		 * Paso 21 - Ingresar contraseña
		 ***************************/
		addStep("Ingresar contraseña");
//		posmovil.ingresarContrasenaRetiro("2");
		posmovil.ingresarContrasenaRetiro(LoginPosMovil.passUser(data.get("crTienda")));

		/************************
		 * Paso 22 - Presionar boton Aceptar
		 ***************************/
		addStep("Presionar boton Aceptar");
		posmovil.clickBotonConfirmar();

		/************************
		 * Paso 23 - Validar se muestre pantalla retiro de dinero
		 ***************************/
		addStep("Validar se muestre pantalla retiro de dinero");
		boolean edoModalRetiro = posmovil.validarTituloModalRetiro();
		Assert.assertTrue(edoModalRetiro, "No se encontro el modal de Retiro");

		/************************
		 * Paso 24 - Ingresar cantidad a retirar
		 ***************************/
		addStep("Ingresar cantidad a retirar");
		posmovil.ingresarMontoDeRetiro();

		/************************
		 * Paso 25 - Presionar Aceptar
		 ***************************/
		addStep("Presionar Aceptar ");
		posmovil.clickBotonConfirmar();

		/************************
		 * Paso 26 - Validar mensanje "El monto a retirar es de'', ¿Desea continuar?"
		 ***************************/
		addStep("Validar mensanje \"El monto a retirar es de'', ¿Desea continuar?\"");
		boolean edoMensajeRetiro = posmovil.validarMensajeDeRetiro();
		Assert.assertTrue(edoMensajeRetiro, "No se encontro el Mensaje");

		/************************
		 * Paso 27 - Presionar Aceptar
		 ***************************/
		addStep("Presionar Aceptar ");
		posmovil.clickBotonConfirmar2();

		/************************
		 * Paso 28 - Validar mensaje "Se ha realizado el retiro de manera exitosa"
		 ***************************/
		addStep("Validar mensaje \"Se ha realizado el retiro de manera exitosa\"");
		boolean edoMensaje = posmovil.validarTituloModalRetiro();
		Assert.assertTrue(edoMensaje, "No se encontro el Mensaje");

		/************************
		 * Paso 29 - Validar regrese al inicio del Journal.
		 ***************************/
		addStep("Validar regrese al inicio del Journal.");
		posmovil.esperaVentanaJournalInicial();

//=======================================Cerrar Aplicaciones========================================================//
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//

	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-014-Validar que despues de 3 mensajes de alertas no permita continur con ventas.";
	}

	@Override
	public String setTestDescription() {
		return "Se realizará ventas de articulos  y se muestre el mensaje de retiro parcial, continuar con ventas hasta que no se permita continuar.";
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
				+ "3 - Contar con Impresoras Epson y Zebra";
	}

	@Override
	public void beforeTest() {

	}
	
	@AfterTest
	public void afterTest() throws Exception {
	System.out.println("Realiza la acción del AfterTest");
	  AppiumUtil appiumUtil = null;
	  LoginPM LoginPosMovil = new LoginPM(testCase, appiumUtil, this);	  
	  LoginPosMovil.cerrarVPN();
	  }

}