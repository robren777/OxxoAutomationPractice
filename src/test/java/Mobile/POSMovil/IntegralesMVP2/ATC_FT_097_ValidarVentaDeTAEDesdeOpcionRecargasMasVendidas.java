package Mobile.POSMovil.IntegralesMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-097-Validar venta de TAE desde opcion recargas mas
* DESCRIPCIÓN: Validar venta de TAE eligiendo una opcion de recargas mas vendidas.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Solicitar al equipo de L1 que habiliten el pago con tarjea para los servicios
* 
* INSUMOS:
* Telefono del cliente
* 
* @autor Marcos Morales
* @date 23/06/2025
*/
//========================================================================//

public class ATC_FT_097_ValidarVentaDeTAEDesdeOpcionRecargasMasVendidas extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_097_ValidarVentaDeTAEDesdeOpcionRecargasMasVendidas_test(HashMap<String, String> data)
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
		addStep("Ingresar a POS MOVIL");

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
		LoginPosMovil.verificarLoginTiendaUserCr(data.get("crTienda"),PasswordUtil.decryptPassword(data.get("passwordLogin")),data.get("salInic"));

		/************************
		 * Paso 2 - Ingresar al Journal
		 ***************************/
		addStep("Validar se muestra el journal");
		boolean ingresarJournal = posmovil.ingresarJournal();
		assertTrue(ingresarJournal, "Journal no encontrado");

		/************************
		 * Paso 3 - Ingresar al apartado de TAE
		 ***************************/
		addStep("Ingresar al apartado de TAE");
		posmovil.clickOpcionTAE();

		/************************
		 * Paso 4 - Validar se muestren las diferentes compañias
		 ***************************/
		addStep("Validar se muestren las diferentes compañias");
		List<String> prov = posmovil.obtieneListaProveedoresTAE();
		Assert.assertEquals(prov.get(0), "Iusacell");
		Assert.assertEquals(prov.get(1), "Movistar");
		Assert.assertEquals(prov.get(2), "Nextel");
		Assert.assertEquals(prov.get(3), "Telcel");
		Assert.assertEquals(prov.get(4), "Unefon");

		/************************
		 * Paso 5 - Validar se muestren los servicios TOP ventas en TAE
		 ***************************/
		addStep("Validar se muestren los servicios TOP ventas en TAE");
		boolean[] botones = posmovil.retornaEstadoRecargasMasVendidas();
		Assert.assertTrue(botones[0]);
		Assert.assertTrue(botones[1]);
		Assert.assertTrue(botones[2]);

		/************************
		 * Paso 6 - Presionar boton + en uno de los servicios mas vendidos
		 ***************************/
		addStep("Presionar boton + en uno de los servicios mas vendidos");
		posmovil.clickOpcionTAEMasVendido("2");

		/************************
		 * Paso 7 - Validar se agregue al journal
		 ***************************/
		addStep("Validar se agregue al journal");
		int journal = posmovil.retornaCantidadProductosJournal();
		Assert.assertNotEquals(journal, 0);

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
		posmovil.esperaSeOculteLoading();
		posmovil.confirmarPagoEfectivo();

		/************************
		 * Paso 11 - Validar que se pueda realizar la venta del servicio
		 ***************************/
		addStep("Validar que se pueda realizar la venta del servicio");
		boolean edoMensaje = posmovil.validarMensajeVentaExitosa();
		assertTrue(edoMensaje, "La venta no fue exitosa");
		
		/************************
		 * Paso 12 - Validar se muestre pantalla para ingresar numero de telefono
		 ***************************/
		addStep("Validar se muestre pantalla para ingresar numero de telefono");
		String mensaje = posmovil.obtieneTituloVentanaRecargaTAE();
		Assert.assertEquals(mensaje, "Recarga de tiempo aire");

		/************************
		 * Paso 13 - Ingresar numero de telefono
		 ***************************/
		addStep("Ingresar numero de telefono");
		posmovil.ingresarNumeroTelefono(data.get("numCliente"));

		/************************
		 * Paso 14 - Confirmar numero de telefono
		 ***************************/
		addStep("Confirmar numero de telefono");
		posmovil.confirmarNumeroTelefono(data.get("numCliente"));

		/************************
		 * Paso 15 - Presionar aceptar
		 ***************************/
		addStep("Se presiona aceptar");
		posmovil.clickBtnAceptarRecarga();

		/************************
		 * Paso 16 - Validar se realice la recarga correctamente.
		 ***************************/
		addStep("Validar se realice la recarga correctamente.");
		mensaje = posmovil.obtieneMensajeVentaTAE();
		Assert.assertEquals(mensaje, "Recarga de tiempo aire exitosa");
		posmovil.clickBotonFinalizar();

//=======================================Cerrar Aplicaciones========================================================//
Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//

	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-097-Validar venta de TAE desde opcion recargas mas vendidas";
	}

	@Override
	public String setTestDescription() {
		return "Validar venta de TAE eligiendo una opcion de recargas mas vendidas.";
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
}