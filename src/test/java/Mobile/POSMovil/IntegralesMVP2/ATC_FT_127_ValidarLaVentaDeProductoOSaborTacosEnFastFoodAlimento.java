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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-127-Validar la venta de producto O Sabor Tacos en fastfood Alimento.
* DESCRIPCIÓN: Validar que se realice la venta correctamente de un producto en fastfood Alimento.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* 
* INSUMOS:
* Nombre/SKU de articulo FastFood O Sabor Tacos
* 
* @autor Marcos Morales
* @date 03/07/2025
*/
//========================================================================//

public class ATC_FT_127_ValidarLaVentaDeProductoOSaborTacosEnFastFoodAlimento extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_127_ValidarLaVentaDeProductoOSaborTacosEnFastFoodAlimento_test(HashMap<String, String> data)
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
		addStep("Ingresar al Journal ");
		boolean ingresarJournal = posmovil.ingresarJournal();
		assertTrue(ingresarJournal, "Journal no encontrado");

		/************************
		 * Paso 2 - Ingresar al apartado de fast food
		 ***************************/
		addStep("Ingresar al apartado de fast food");
		posmovil.clickOpcionFastFood();

		/************************
		 * Paso 3 - Validarse muestre en pantalla opciones de fast food
		 ***************************/
		addStep("Validarse muestre en pantalla opciones de fast food");
		List<String> opFastFood = posmovil.obtieneListaOpcionesFastFood();
		Assert.assertEquals(opFastFood.get(0), "Fast Food Alimentos");
		Assert.assertEquals(opFastFood.get(1), "Fast Food Bebidas");
		posmovil.seleccionarFastFood("Fast Food Alimentos");
		posmovil.seleccionarFastFood("Comida caliente");

		/************************
		 * Paso 4 - Seleccionar O Sabor
		 ***************************/
		addStep("Seleccionar O Sabor");
		posmovil.seleccionarFastFood("OSABOR");

		/************************
		 * Paso 5 - Validar se muestre en pantalla opciones de alimentos
		 ***************************/
		addStep("Validar se muestre en pantalla opciones de alimentos");
		opFastFood = posmovil.obtieneListaOpcionesFastFood();
		Assert.assertEquals(opFastFood.get(0), "BURRITOS");
		Assert.assertEquals(opFastFood.get(1), "OTROS");
		Assert.assertEquals(opFastFood.get(2), "QUESADILLAS");
		Assert.assertEquals(opFastFood.get(3), "TACOS");

		/************************
		 * Paso 6 - Seleccionar Burritos
		 ***************************/
		addStep("Seleccionar Tacos ");
		posmovil.seleccionarFastFood("TACOS");

		/************************
		 * Paso 7 - Validar se muestren las opciones
		 ***************************/
		addStep("Validar se muestren las opciones");
		opFastFood = posmovil.obtieneListaOpcionesFastFood();
		Assert.assertEquals(opFastFood.get(0), "TACOS CLASICOS");
		Assert.assertEquals(opFastFood.get(1), "TACOS ESPECIALES");

		/************************
		 * Paso 8 - Seleccionar
		 ***************************/
		addStep("Seleccionar");
		posmovil.seleccionarFastFood("TACOS CLASICOS");

		/************************
		 * Paso 9 - Validar se muestren los productos relacionados
		 ***************************/
		addStep("Validar se muestren los productos relacionados");
		Assert.assertNotEquals(posmovil.listadoDeProductosFastFood(), 0);

		/************************
		 * Paso 10 - Agregar producto
		 ***************************/
		addStep("Agregar producto");
		posmovil.agregarProductoAlJournal(1);

		/************************
		 * Paso 11 - Validar se muestre boton continuar
		 ***************************/
		addStep("Validar se muestre boton continuar");
		Assert.assertTrue(posmovil.retornaEstadoBotonContinuar());

		/************************
		 * Paso 12 - Seleccionar boton continuar
		 ***************************/
		addStep("Seleccionar boton continuar");
		posmovil.clickBotonContinuar();

		/************************
		 * Paso 13 - Validar se agregue al journal
		 ***************************/
		addStep("Validar se agregue al journal");
		int journal = posmovil.retornaCantidadProductosJournal();
		Assert.assertEquals(journal, 1);

		/************************
		 * Paso 14 - Totalizar en el journal
		 ***************************/
		addStep("Totalizar en el journal");
		posmovil.clickBotonCalcular();
		posmovil.clickBotonTotalPagar();

		/************************
		 * Paso 15 - Seleccionar el metodo de pago - Efectivo
		 ***************************/
		addStep("Seleccionar el metodo de pago - Efectivo");
		posmovil.esperaSeOculteLoading();
		posmovil.clickMetodoDePagoEfectivo();

		/************************
		 * Paso 16 - Confirmar Cantidad+Enter
		 ***************************/
		addStep("Confirmar Cantidad+Enter");
		posmovil.esperaSeOculteLoading();
		posmovil.confirmarPagoEfectivo();

		/************************
		 * Paso 17 - Validar que se pueda realizar la venta del producto
		 ***************************/
		addStep("Validar que se pueda realizar la venta del producto");
		boolean edoMensaje = posmovil.validarVentaExitosa();
		assertTrue(edoMensaje, "La venta no fue exitosa");

//=======================================Cerrar Aplicaciones========================================================//
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//

	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-127-Validar la venta de producto O Sabor Tacos en fast food Alimento.";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realice la venta correctamente de un producto en fast food Alimento.";
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
