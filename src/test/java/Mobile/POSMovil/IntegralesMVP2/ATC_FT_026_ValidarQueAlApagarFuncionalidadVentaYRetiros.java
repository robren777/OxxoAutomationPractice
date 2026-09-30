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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-026 Validar que al apagar funcionalidad Venta y Retiros no se permita
*  el flujo.
* DESCRIPCIÓN: Al tener deshabilitado la funcionalidad de Venta y Retiros no se permite el flujo Retiros.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Tener deshabilitado funcionalidad Venta y Retiros
* 
* INSUMOS:
* SKU de articulos
* 
* @autor Marcos Morales
* @date 02/07/2025
*/
//========================================================================//

public class ATC_FT_026_ValidarQueAlApagarFuncionalidadVentaYRetiros extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_026_ValidarQueAlApagarFuncionalidadVentaYRetiros_test(
			HashMap<String, String> data) throws Exception {
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
//		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true
		// para que no se borre el cache y asi lograr evitar la vista de ingresar la IP
		// y puerto

//		String url = "http://127.0.0.1:4723";
		/************************ Ingresar a la App ***************************/
		addStep("Iniciar Aplicación POS Móvil");

		/************************
		 * Paso 1 - Ingresar a la App
		 ***************************/

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
		LoginPM loginPM = new LoginPM(testCase, appiumUtil, this);
		/*
		 * Llamado al OM de PosMovil.
		 */
		PM posmovil = new PM(testCase, appiumUtil, this);

//================================================================================================================//

		/************************ Deshabilita el permiso ***************************/
		posmovil.deshabilitarHabilitarPermiso(data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("crTienda"), 4, "Venta y Retiros", 9,
				"Retiro de efectivo", 0);

		/************************
		 * Documenta los servicios para el CR utilizado
		 ***************************/
		addStep("Permisos Habilitados en la NUBE");
		String permisos = SQLCMDFormatter.formatResultSet(posmovil.obtienePermisosBDNube(data.get("crTienda")));
		System.out.println("Permisos en la NUBE\n" + permisos);
		testCase.addTextEvidenceCurrentStep(permisos);

		/************************ Inicio de sesión ***************************/
		  loginPM.verificarLoginTiendaUserCrV2(data.get("crTienda"),data.get("salInic"));	  


		/************************
		 * Paso 1 - Ingresar al Journal
		 ***************************/
		addStep("Ingresar al Journal");
		boolean ingresarJournal = posmovil.ingresarJournal();
		assertTrue(ingresarJournal, "Journal no encontrado");

		/************************
		 * Paso 2 - Ingresar el SKU del articulo en el buscador
		 ***************************/
		addStep("Ingresar el SKU del articulo en el buscador");
		posmovil.ingresarSKUEnBuscador(data.get("sku"));

		/************************
		 * Paso 3 - Ingresar la cantidad de articulos a comprar
		 ***************************/
		addStep("Ingresar la cantidad de articulos a comprar");
		posmovil.clickEnterEnCampoBuscador();
		posmovil.agregarProductoAlJournal();

		/************************
		 * Paso 4 - Seleccionar Calcular
		 ***************************/
		addStep("Seleccionar Calcular");
		posmovil.clickBotonCalcular();

		/************************
		 * Paso 5 - Presionar boton Pagar
		 ***************************/
		addStep("Presionar boton Pagar");
		posmovil.clickBotonTotalPagar();

		/************************
		 * Paso 6 - Validar opciones de pago
		 ***************************/
		addStep("Validar opciones de pago");
		posmovil.esperaSeOculteLoading();
		boolean metodosPago[] = posmovil.retornaEstadoFormasDePago();
		Assert.assertTrue(metodosPago[0]);
		Assert.assertTrue(metodosPago[1]);

		/************************
		 * Paso 7 - Presionar pago en efectivo
		 ***************************/
		addStep("Presionar pago en efectivo");
		posmovil.esperaSeOculteLoading();
		posmovil.clickMetodoDePagoEfectivo();

		/************************
		 * Paso 8 - Terminar venta con pago en efectivo
		 ***************************/
		addStep("Terminar venta con pago en efectivo");
		posmovil.esperaSeOculteLoading();
		posmovil.confirmarPagoEfectivo();
		
		/************************
		 * Paso 9 - ?Validar que se realice la venta exitosamente
		 ***************************/
		addStep("?Validar que se realice la venta exitosamente");
		//posmovil.esperaVentanaTicketDigital();
		Assert.assertTrue(posmovil.validarMensajeToast("Venta completada con éxito", 30), "La venta no fué exitosa");

		/************************
		 * Paso 10 - Realizar ventas hasta llegar a el monto maximo configurado.
		 ***************************/
		addStep("Realizar ventas hasta llegar a el monto maximo configurado.");
		posmovil.realizarMultiplesVentasMostrarRetiros(data.get("crTienda"),data.get("ipPv"));

		/************************
		 * Paso 11 - ?Validar que no se muestre mensaje de retiro.
		 ***************************/
		addStep("?Validar que no se muestre mensaje de retiro.");
		Assert.assertFalse(posmovil.obtieneEstadoMensajeAvisoRetiro());
		
		/************************
		 * Paso 12 - Se habilita nuevamente el Permiso
		 ***************************/
		posmovil.deshabilitarHabilitarPermiso(data.get("userLogin"),
				PasswordUtil.decryptPassword(data.get("passwordLogin")), data.get("crTienda"), 4, "Venta y Retiros", 9,
				"Retiro de efectivo", 1);

//=======================================Cerrar Aplicaciones========================================================//
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//

	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-026 Validar que al apagar funcionalidad Venta y Retiros no se permita el flujo.";
	}

	@Override
	public String setTestDescription() {
		return "Al tener deshabilitado la funcionalidad de Venta y Retiros no se permite el flujo Retiros.";
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
		return "1.- Versión mas actualizada de APK" + "2.- Tener conexión a la bd de la nube"
				+ "3 - Contar con Impresoras Epson y Zebra"
				+ "4.- Tener deshabilitado funcionalidad Venta y Retiros";
	}

	@Override
	public void beforeTest() {

	}

}