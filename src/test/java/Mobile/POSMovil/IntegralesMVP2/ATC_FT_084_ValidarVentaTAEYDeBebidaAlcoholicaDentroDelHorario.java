package Mobile.POSMovil.IntegralesMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-084-Validar venta TAE y de bebida alcohólica - dentro 
* del horario permitido - Efectivo
* DESCRIPCIÓN: Dado que se va a realizar la venta de una bebida alcohólica y se encuentra dentro del horario 
* permitido, se deberá realizar la venta con método de pago en efectivo.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Solicitar al equipo de L1 que habiliten el pago con tarjeta para los servicios
* 
* INSUMOS:
* SKU de articulo bebida alcoholica
* Telefono del cliente
* 
* @autor Marcos Morales
* @date 25/06/2025
*/
//========================================================================//

public class ATC_FT_084_ValidarVentaTAEYDeBebidaAlcoholicaDentroDelHorario extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_084_ValidarVentaTAEYDeBebidaAlcoholicaDentroDelHorario_test(
			HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();
		
		//=============================================================================================//
		System.out.println("Cerrando aplicacion si está abierta");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		Thread.sleep(3000);
		//=============================================================================================//

//		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
//		cap.setCapability("deviceName", data.get("deviceName"));
//		cap.setCapability("platformVersion", data.get("platformVersion"));
//		cap.setCapability("platformName", data.get("platformName"));
//		cap.setCapability("appPackage", data.get("appPackage"));
//		cap.setCapability("appActivity", data.get("appActivity"));
//		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi
											// lograr evitar la vista de ingresar la IP y puerto
//		cap.setCapability("appium:settings[enableMultiWindows]", true);

//		String url = "http://127.0.0.1:4723";

		/************************ Ingresar a la App ***************************/
		addStep("Abrir aplicación POSMOVIL en HandHeld.");	

		AppiumUtil appiumUtil = null;
		try {
			//appiumUtil = new AppiumUtil(url, cap);
	  		appiumUtil = PMCapabilities.initializeAppium("Login");
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
		/*
		 * Llamado al OM de PosMovil.
		 */
		PM posmovil = new PM(testCase, appiumUtil, this);

//================================================================================================================//
		/************************ Inicio de sesión ***************************/
		LoginPosMovil.verificarLoginTiendaUserCr(data.get("crTienda"),PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));

		/************************
		 * Paso 1 - Ingresar al Journal
		 ***************************/
		addStep("Ingresar al Journal");
		boolean ingresarJournal = posmovil.ingresarJournal();
		assertTrue(ingresarJournal, "Journal no encontrado");

		/************************
		 * Paso 2 - Ingresar al apartado de TAE
		 ***************************/
		addStep("Ingresar al apartado de TAE");
		posmovil.clickOpcionTAE();

		/************************
		 * Paso 3 - Seleccionar el tipo de compañía Telcel
		 ***************************/
		addStep("Seleccionar el tipo de compañía Telcel");
		posmovil.seleccionarProveedorTAE("Telcel");

		/************************
		 * Paso 4 - Seleccionar cantidad a pagar
		 ***************************/
		addStep("Seleccionar cantidad a pagar");
		posmovil.seleccionarServicioPorScroll("TAE TELCEL $20");

		/************************
		 * Paso 5 - Validar se agregue al journal
		 ***************************/
		addStep("Validar se agregue al journal");
		int journal = posmovil.retornaCantidadProductosJournal();
		Assert.assertNotEquals(journal, 0);

		/************************
		 * Paso 6 - Agregar producto bebida alcoholica
		 ***************************/
		addStep("Agregar producto bebida alcoholica");
		posmovil.ingresarSKUEnBuscador(data.get("sku"));
		posmovil.clickEnterEnCampoBuscador();
		posmovil.agregarProductoAlJournal();

		/************************
		 * Paso 7 - Validar mensaje prohibida la venta de alcohol a menores
		 ***************************/
		addStep("Validar mensaje prohibida la venta de alcohol a menores");
		boolean edoMensaje = posmovil.validarMensajeToast("Solicita identificación oficial.", 30);
		assertTrue(edoMensaje, "No se pudo encontrar el mensaje");
		posmovil.esperaSeOculteMensajeToast("No vendas alcohol a menores de edad.");

		/************************
		 * Paso 8 - Totalizar en el journal
		 ***************************/
		addStep("Totalizar en el journal");
		posmovil.clickBotonCalcular();
		posmovil.clickBotonTotalPagar();

		/************************
		 * Paso 9 - Seleccionar el metodo de pago - Efectivo
		 ***************************/
		addStep("Seleccionar el metodo de pago - Efectivo");
		posmovil.clickMetodoDePagoEfectivo();

		/************************
		 * Paso 10 - Confirmar Cantidad+Enter
		 ***************************/
		addStep("Confirmar Cantidad+Enter");
		posmovil.confirmarPagoEfectivo();

		/************************
		 * Paso 11 - Validar se muestre pantalla para ingresar numero de telefono
		 ***************************/
		addStep("Validar se muestre pantalla para ingresar numero de telefono");
		String mensaje = posmovil.obtieneTituloVentanaRecargaTAE();
		Assert.assertEquals(mensaje, "Recarga de tiempo aire");

		/************************
		 * Paso 12 - Ingresar numero de telefono
		 ***************************/
		addStep("Ingresar numero de telefono");
		posmovil.ingresarNumeroTelefono(data.get("numCliente"));

		/************************
		 * Paso 13 - Confirmar numero de telefono
		 ***************************/
		addStep("Confirmar numero de telefono");
		posmovil.confirmarNumeroTelefono(data.get("numCliente"));

		/************************
		 * Paso 14 - Presionar Aceptar
		 ***************************/
		addStep("Presionar Aceptar");
		posmovil.clickBtnAceptarRecarga();

		/************************
		 * Paso 15 - Validar se realice la recarga correctamente.
		 ***************************/
		addStep("Validar se realice la recarga correctamente.");
		Assert.assertEquals(posmovil.obtieneMensajeVentaTAE(), "Recarga de tiempo aire exitosa");
		posmovil.clickBotonFinalizar();

//=======================================Cerrar Aplicaciones========================================================//
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//

	}

	@Override
	public String setTestFullName() {
		return "ATC-FT-084-Validar venta TAE y de bebida alcohólica - dentro del horario permitido - Efectivo";
	}

	@Override
	public String setTestDescription() {
		return "Dado que se va a realizar la venta de una bebida alcohólica y se encuentra dentro del horario permitido, "
				+ "se deberá realizar la venta con método de pago en efectivo.";
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
				+ "3 - Contar con Impresoras Epson y Zebra\r\n"
				+ "4 - Solicitar al equipo de L1 que habiliten el pago con tarjeta para los servicios";
	}

	@Override
	public void beforeTest() {

	}

}