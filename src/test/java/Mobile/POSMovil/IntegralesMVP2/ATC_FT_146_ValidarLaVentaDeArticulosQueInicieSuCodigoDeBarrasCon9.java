package Mobile.POSMovil.IntegralesMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Test;

import Mobile.POSMovil.om.PM;
import Mobile.POSMovil.om.LoginPM;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

//========================================================================//
/**
* PROYECTO: POS Móvil MVP2
* 
* NOMBRE DE CASO DE PRUEBA: ATC-FT-146-Validar el ingreso de articulos que inicie su código de barras con 
* 9 en el Journal
* DESCRIPCIÓN: Al escanear el codigo de barras de los articulos iniciados con el numero 9 se deberá tomar
*  como articulo y no como tarjeta Spin Premia
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Solicitar al equipo de L1 que habiliten el pago con tarjea para los servicios
* 
* INSUMOS:
* Nombre/SKU del articulo FastFood Frutas y Verduras 
* 
* @autor Marcos Morales
* @date 04/07/2025
*/
//========================================================================//

public class ATC_FT_146_ValidarLaVentaDeArticulosQueInicieSuCodigoDeBarrasCon9 extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_146_ValidarLaVentaDeArticulosQueInicieSuCodigoDeBarrasCon9_test(HashMap<String, String> data)
			throws Exception {
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

		String url = "http://127.0.0.1:4723";
		
		/************************ Ingresar a la App ***************************/
		addStep("Iniciar Aplicación POS Móvil");

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
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
		LoginPM loginPM = new LoginPM(testCase, appiumUtil, this);
		/*
		 * Llamado al OM de PosMovil.
		 */
		PM posmovil = new PM(testCase, appiumUtil, this);

//================================================================================================================//
		/************************ Inicio de sesión ***************************/
		loginPM.validaSeRequiereLogin(data.get("crTienda"), data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")));

		/************************
		 * Paso 1 - Ingresar al Journal
		 ***************************/

		addStep("Ingresar al Journal ");
		Thread.sleep(500);
		boolean ingresarJournal = posmovil.ingresarJournal();
		assertTrue(ingresarJournal, "Journal no encontrado");

		/************************
		 * Paso 2 - Escanear el articulo 9002490100490
		 ***************************/

		addStep("Escanear el articulo " + data.get("SKU1"));
		posmovil.ingresarSKUEnBuscador(data.get("SKU1"));

		/************************
		 * Paso 3 - ?Validar que al escanear la tarjeta se muestre el articulo en la pantalla del journal
		 ***************************/

		addStep("?Validar que al escanear la tarjeta se muestre el articulo en la pantalla del journal");
		posmovil.clickEnterEnCampoBuscador();
		Assert.assertEquals(posmovil.obtieneDescripcionDeArticulo(), data.get("Descripcion1"));
		
		/************************
		 * Paso 4 - Escanear el articulo 9002490211486
		 ***************************/

		addStep("Escanear el articulo " + data.get("SKU2"));
		posmovil.ingresarSKUEnBuscador(data.get("SKU2"));

		/************************
		 * Paso 5 - ?Validar que al escanear la tarjeta se muestre el articulo en la pantalla del journal
		 ***************************/

		addStep("?Validar que al escanear la tarjeta se muestre el articulo en la pantalla del journal");
		posmovil.clickEnterEnCampoBuscador();
		Assert.assertEquals(posmovil.obtieneDescripcionDeArticulo(), data.get("Descripcion2"));
		
		/************************
		 * Paso 6 - Escanear el articulo 90424441
		 ***************************/

		addStep("Escanear el articulo " + data.get("SKU3"));
		posmovil.ingresarSKUEnBuscador(data.get("SKU3"));

		/************************
		 * Paso 7 - ?Validar que al escanear la tarjeta se muestre el articulo en la pantalla del journal
		 ***************************/

		addStep("?Validar que al escanear la tarjeta se muestre el articulo en la pantalla del journal");
		posmovil.clickEnterEnCampoBuscador();
		Assert.assertEquals(posmovil.obtieneDescripcionDeArticulo(), data.get("Descripcion3"));
		
		/************************
		 * Paso 8 - Escanear el articulo 9002490200220
		 ***************************/

		addStep("Escanear el articulo " + data.get("SKU4"));
		posmovil.ingresarSKUEnBuscador(data.get("SKU4"));

		/************************
		 * Paso 9 - ?Validar que al escanear la tarjeta se muestre el articulo en la pantalla del journal
		 ***************************/

		addStep("?Validar que al escanear la tarjeta se muestre el articulo en la pantalla del journal");
		posmovil.clickEnterEnCampoBuscador();
		Assert.assertEquals(posmovil.obtieneDescripcionDeArticulo(), data.get("Descripcion4"));
		
		/************************
		 * Paso 10 - ?Validar que se pueda continuar con el flujo de venta si que se acceda al escanear los articulos correspondientes
		 ***************************/

		addStep("?Validar que se pueda continuar con el flujo de venta si que se acceda al escanear los articulos correspondientes");
		posmovil.ingresarSKUEnBuscador("7501057002717");
		posmovil.clickEnterEnCampoBuscador();
		posmovil.agregarProductoAlJournal();
		posmovil.clickBotonCalcular();
		posmovil.clickBotonTotalPagar();
		posmovil.clickMetodoDePagoEfectivo();
		posmovil.confirmarPagoEfectivo();
		boolean edoMensaje = posmovil.validarMensajeVentaExitosa();
		assertTrue(edoMensaje, "La venta no fue exitosa");
		posmovil.esperaSeOculteMensajeToast("Venta completada con éxito");
		posmovil.esperaVentanaJournalInicial();

//=======================================Cerrar Aplicaciones========================================================//
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//

	}
	
	@Override
	public String setTestFullName() {
		return "MTC-FT-146-Validar el ingreso de articulos que inicie su código de barras con 9 en el Journal";
	}

	@Override
	public String setTestDescription() {
		return "Al escanear el codigo de barras de los articulos iniciados con el numero 9 se deberá tomar como articulo y no como tarjeta Spin Premia";
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