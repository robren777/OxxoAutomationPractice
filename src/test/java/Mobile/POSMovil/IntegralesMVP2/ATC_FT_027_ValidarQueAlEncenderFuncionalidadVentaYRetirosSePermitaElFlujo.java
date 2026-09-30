package Mobile.POSMovil.IntegralesMVP2;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

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
* NOMBRE DE CASO DE PRUEBA: ATC-FT-027 Validar que al encender funcionalidad Venta y Retiros se permita 
* el flujo
* DESCRIPCIÓN: Al tener habilitado la funcionalidad de Venta y Retiros se permite el flujo Venta de Tiempo aire.
* 
* PRE-REQUISITOS
* Versión mas actualizada de APK
* Tener conexión a la bd de la nube
* Contar con Impresoras Epson y Zebra
* Tener habilitado funcionalidad Venta y Retiros
* 
* INSUMOS:
* Telefono de usuario 
* 
* @autor Marcos Morales
* @date 30/06/2025
*/
//========================================================================//

public class ATC_FT_027_ValidarQueAlEncenderFuncionalidadVentaYRetirosSePermitaElFlujo extends BaseExecution {

	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_027_ValidarQueAlEncenderFuncionalidadVentaYRetirosSePermitaElFlujo_test(
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
//		cap.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi
											// lograr evitar la vista de ingresar la IP y puerto

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
			Assert.assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n" + e.getMessage());
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
		Assert.assertTrue(ingresarJournal, "Journal no encontrado");

		/************************
		 * Paso 2 - Presionar boton Tiempo Aire
		 ***************************/
		addStep("Presionar boton Tiempo Aire");
		posmovil.clickOpcionTAE();

		/************************
		 * Paso 3 - Validar se muestren compañias de tiempo aire
		 ***************************/
		addStep("Validar se muestren compañias de tiempo aire");
		List<String> prov = posmovil.obtieneListaProveedoresTAE();
		Assert.assertEquals(prov.get(0), "Iusacell");
		Assert.assertEquals(prov.get(1), "Movistar");
		Assert.assertEquals(prov.get(2), "Nextel");
		Assert.assertEquals(prov.get(3), "Telcel");
		Assert.assertEquals(prov.get(4), "Unefon");

		/************************
		 * Paso 4 - Seleccionar compañia de tiempo aire
		 ***************************/
		addStep("Seleccionar compañia de tiempo aire");
		posmovil.seleccionarProveedorTAE("Telcel");

		/************************
		 * Paso 5 - Validar se muestran las cantidades de recarga
		 ***************************/
		addStep("Validar se muestran las cantidades de recarga");
		int cantRecargas = posmovil.muestraListadoDeRecargas();
		Assert.assertNotEquals(cantRecargas, 0);

		/************************
		 * Paso 6 - Seleccionar cantidad a recargar
		 ***************************/
		addStep("Seleccionar cantidad a recargar");
		posmovil.seleccionarServicioPorScroll("TAE TELCEL $20");

		/************************
		 * Paso 7 - Validar se agregue al journal
		 ***************************/
		addStep("Validar se agregue al journal");
		int journal = posmovil.retornaCantidadProductosJournal();
		Assert.assertNotEquals(journal, 0);

		/************************
		 * Paso 8 - Presionar boton total
		 ***************************/
		addStep("Presionar boton total");
		posmovil.clickBotonCalcular();
		posmovil.clickBotonTotalPagar();

		/************************
		 * Paso 9 - Seleccionar metodo de pago
		 ***************************/
		addStep("Seleccionar metodo de pago");
		posmovil.clickMetodoDePagoEfectivo();
		posmovil.confirmarPagoEfectivo();

		/************************
		 * Paso 10 - Finalizar la venta
		 ***************************/
		addStep("Finalizar la venta");		
		//boolean edoMensaje = posmovil.validarMensajeVentaExitosa();
		//Assert.assertTrue(edoMensaje, "La venta no fue exitosa");

		posmovil.ingresarNumeroTelefono(data.get("numCliente"));
		posmovil.confirmarNumeroTelefono(data.get("numCliente"));
		posmovil.clickBotonAceptarRecarga();

		/************************
		 * Paso 11 - Validar que la recarga sea exitosa
		 ***************************/
		 addStep("Se valida que la recarga sea exitosa");
		  boolean validarTaeExi = posmovil.validarServRecExitosa();
		  assertTrue(validarTaeExi,"La recarga no fue exitosa");
		posmovil.clickBotonFinalizar();

//=======================================Cerrar Aplicaciones========================================================//
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
//==================================================================================================================//

	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-027 Validar que al encender funcionalidad Venta y Retiros se permita el flujo.";
	}

	@Override
	public String setTestDescription() {
		return "Al tener habilitado la funcionalidad de Venta y Retiros se permite el flujo Venta de Tiempo aire.";
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
				+ "3 - Contar con Impresoras Epson y Zebra\r\n" + "4.- Tener habilitado funcionalidad Venta y Retiros";
	}

	@Override
	public void beforeTest() {

	}

}
