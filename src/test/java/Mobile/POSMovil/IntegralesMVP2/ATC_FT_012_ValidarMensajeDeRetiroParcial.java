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
* NOMBRE DE CASO DE PRUEBA: MTC-FT-012-Validar mensaje de retiro parcial. 
* DESCRIPCIÓN: Se realizara ventas, validar que se muestre mensaje de retiro parcial.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* SKU de articulos
* 
* @autor Marcos Morales
* @date 24/06/2025
*/
//========================================================================//

public class ATC_FT_012_ValidarMensajeDeRetiroParcial extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_012_ValidarMensajeDeRetiroParcial_test(HashMap<String, String> data) throws Exception {
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
		addStep("Iniciar Aplicación POS Móvil");

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
		 * Paso 2 - Ingresar el SKU del articulo en el buscador
		 ***************************/
		addStep("Ingresar el SKU del articulo en el buscador");
//		posmovil.ingresarSKUEnBuscador(data.get("sku"));
		posmovil.ingresarSKUEnBuscador(posmovil.ConsultaArticulo(LoginPosMovil.obtenerIPTienda(data.get("crTienda"))));

		/************************
		 * Paso 3 - Ingresar la cantidad de articulos a comprar
		 ***************************/
		addStep("Ingresar la cantidad de articulos a comprar");
		posmovil.clickEnterEnCampoBuscador();
		posmovil.agregarProductoAlJournal();

		/************************
		 * Paso 4 - Seleccionar Total a pagar
		 ***************************/
		addStep("Seleccionar Total a pagar");
		posmovil.clickBotonCalcular();
		posmovil.clickBotonTotalPagar();

		/************************
		 * Paso 5 - Confirmar el metodo de pago-Efectivo
		 ***************************/
		addStep("Confirmar el metodo de pago-Efectivo");
		posmovil.clickMetodoDePagoEfectivo();

		/************************
		 * Paso 6 - Finalizar la venta
		 ***************************/
		addStep("Finalizar la venta");
		posmovil.confirmarPagoEfectivo();

		/************************
		 * Paso 7 - ?Validar que se realice la venta exitosamente
		 ***************************/
		addStep("?Validar que se realice la venta exitosamente");
		posmovil.esperaVentanaTicketDigital();
		Assert.assertTrue(posmovil.validarMensajeToast("Venta completada con éxito", 30), "La venta no fué exitosa");

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
		Assert.assertEquals(mensaje, "Aviso 1 de 3", "No se mostró el mensaje de retiro");

//=======================================Cerrar Aplicaciones========================================================//
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//
	
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-012-Validar mensaje de retiro parcial.";
	}

	@Override
	public String setTestDescription() {
		return "Se realizara ventas, validar que se muestre mensaje de retiro parcial.";
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
		return "1.- Versión mas actualizada de APK\r\n" + "2.- Tener conexión a la bd de la nube\r\n"
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