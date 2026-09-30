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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-088-Validar venta de TAE y bebida alcohólica - fuera de horario permitido
* DESCRIPCIÓN: Dado que un usuario desea realizar la compra de una bebida alcohólica, pero se encuentra fuera 
* del horario permitido, deberá mostrarse un mensaje donde indique que no se puede realizar la venta del articulo 
* correspondiente.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* Telefono del cliente
* SKU de articulo bebida alcoholica
* 
* @autor Marcos Morales
* @date 26/06/2025
*/
//========================================================================//

public class ATC_FT_088_ValidarVentaDeTAEYBebidaAlcoholicaFueraDeHorarioPermitido extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_088_ValidarVentaDeTAEYBebidaAlcoholicaFueraDeHorarioPermitido_test(HashMap<String, String> data)
			throws Exception {
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
//		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi
											// lograr evitar la vista de ingresar la IP y puerto

//		String url = "http://127.0.0.1:4723";

		/************************
		 * Paso 1 - Ingresar a la App
		 ***************************/
		addStep("Ingresar al Journal");

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
		 * Paso 2 - Validar se muestre el journal
		 ***************************/
		addStep("Validar se muestre el journal");
		boolean ingresarJournal = posmovil.ingresarJournal();
		assertTrue(ingresarJournal, "Journal no encontrado");

		/************************
		 * Paso 3 - Ingresar al apartado de TAE
		 ***************************/
		addStep("Ingresar al apartado de TAE");
		posmovil.clickOpcionTAE();

		/************************
		 * Paso 4 - Seleccionar el tipo de compañía Telcel
		 ***************************/
		addStep("Seleccionar el tipo de compañía Telcel");
		posmovil.seleccionarProveedorTAE("Telcel");

		/************************
		 * Paso 5 - Seleccionar cantidad a pagar
		 ***************************/
		addStep("Seleccionar cantidad a pagar");
		posmovil.seleccionarServicioPorScroll("TAE TELCEL $20");

		/************************
		 * Paso 6 - Validar que se agregue al journal
		 ***************************/
		addStep("Validar se agregue al journal");
		int journal = posmovil.retornaCantidadProductosJournal();
		Assert.assertNotEquals(journal, 0);

		/************************
		 * Paso 7 - Agregar producto bebida alcoholica
		 ***************************/
		addStep("Agregar producto bebida alcoholica");
		posmovil.ingresarSKUEnBuscador(data.get("sku"));
		posmovil.clickEnterEnCampoBuscador();
		
		/************************
		 * Paso 8 - Validar mensaje fuera de horario permitido
		 ***************************/
		addStep("Validar mensaje fuera de horario permitido");
		boolean mensaje = posmovil.validarMensajeToast("Venta fuera de horario.", 30);
		assertTrue(mensaje, "No se pudo encontrar el mensaje");

		/************************
		 * Paso 9 - Validar no se agregue el producto al journal
		 ***************************/
		addStep("Validar no se agregue el producto al journal");
		posmovil.clickBotonLimpiarBusqueda();
		journal = posmovil.retornaCantidadProductosJournal();
		Assert.assertNotEquals(journal, 0);

		/************************
		 * Paso 10 - Totalizar en el journal
		 ***************************/
		addStep("Totalizar en el journal");
		posmovil.clickBotonCalcular();
		posmovil.clickBotonTotalPagar();

		/************************
		 * Paso 11 - Seleccionar metodo de pago efectivo
		 ***************************/
		addStep("Se selecciona el metodo de pago efectivo");
		posmovil.clickMetodoDePagoEfectivo();

		/************************
		 * Paso 12 - Confirmar Cantidad +Aceptar
		 ***************************/
		addStep("Confirmar Cantidad +Aceptar");
		posmovil.confirmarPagoEfectivo();

		/************************
		 * Paso 13 - ?Validar que se pueda realizar la venta del servicio
		 ***************************/
		addStep("?Validar que se pueda realizar la venta del servicio");
		boolean edoMensaje = posmovil.validarMensajeVentaExitosa();
		assertTrue(edoMensaje, "La venta no fue exitosa");

		/************************
		 * Paso 14 - Validar se muestre pantalla para ingresar numero de telefono
		 ***************************/
		addStep("Validar se muestre pantalla para ingresar numero de telefono");
		String msj = posmovil.obtieneTituloVentanaRecargaTAE();
		Assert.assertEquals(msj, "Recarga de tiempo aire");

		/************************
		 * Paso 15 - Ingresar numero de telefono
		 ***************************/
		addStep("Ingresar numero de telefono");
		posmovil.ingresarNumeroTelefono(data.get("numCliente"));

		/************************
		 * Paso 16 - Confirmar numero de telefono
		 ***************************/
		addStep("Confirmar numero de telefono");
		posmovil.confirmarNumeroTelefono(data.get("numCliente"));

		/************************
		 * Paso 17 - Presionar Aceptar
		 ***************************/
		addStep("Presionar Aceptar");
		posmovil.clickBtnAceptarRecarga();

		/************************
		 * Paso 18 - Validar se realice la recarga correctamente.
		 ***************************/
		addStep("Validar se realice la recarga correctamente.");
		msj = posmovil.obtieneMensajeVentaTAE();
		Assert.assertEquals(msj, "Recarga de tiempo aire exitosa");
		posmovil.clickBotonFinalizar();

//=======================================Cerrar Aplicaciones========================================================//
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//

	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-088-Validar venta de TAE y bebida alcohólica - fuera de horario permitido";
	}

	@Override
	public String setTestDescription() {
		return "Dado que un usuario desea realizar la compra de una bebida alcohólica, pero se encuentra fuera del horario permitido, deberá mostrarse un mensaje donde indique que no se puede realizar la venta del articulo correspondiente.";
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

}