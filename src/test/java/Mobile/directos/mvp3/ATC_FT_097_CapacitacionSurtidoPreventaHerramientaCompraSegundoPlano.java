package Mobile.directos.mvp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.directos.om.mvp3.Login;
import Mobile.directos.om.mvp3.OAD;
import Mobile.directos.om.mvp3.ProveedoresDirectos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_097_CapacitacionSurtidoPreventaHerramientaCompraSegundoPlano extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_097_CapacitacionSurtidoPreventaHerramientaCompraSegundoPlano_test(
			HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
		cap.setCapability("appium:settings[enableMultiWindows]", true);
		cap.setCapability("noReset", true); // Se debe de dejar esta opcion en true para que no se borre el cache y asi
											// lograr evitar la vista de ingresar la IP y puerto
		String url = "http://127.0.0.1:4723";

		// =======================================Cerrar Aplicaciones========================================================//
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		// =======================================Inicio Aplicaciones========================================================//
		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}
		Thread.sleep(3500);

		// =========================================Llamado al OM============================================================//
		ProveedoresDirectos ProveedoresD = new ProveedoresDirectos(testCase, appiumUtil, this);
		Login login = new Login(testCase, appiumUtil, this);
		OAD OxxoAlDia = new OAD(testCase, appiumUtil, this);
		Thread.sleep(5000);

		// =========================================Inicio Ejecucion=========================================================//

		/*
		 * 1	Abrir la aplicación “Proveedores directos”
		 * 2	Seleccionar la opción “Capacitación con Visita Guiada”
		 */	
		addStep("Abrir la aplicación [Proveedores directos]","Seleccionar la opción “Capacitación con Visita Guiada”");
		assertTrue(ProveedoresD.VGCapacitacion(), "Boton no disponible: \"Capacitacion con Visita Guiada\"");
		
		/*
		 * 3	Seleccionar la opción “Preventa”
		 */
		addStep("Seleccionar la opción “Preventa”");
		ProveedoresD.opcionPreventa();
		
		/*
		 * 4	Seleccionar la opción “Por Herramienta de Compra”
		 */
		addStep("Seleccionar la opción “Por Herramienta de Compra”");
		ProveedoresD.OpcionHerramientaCompra();
		
		/*
		 * 5	Validar que se muestre pantalla “Generación De Pedido”
		 * Validar mensaje:
		 * Estés realizando un proceso en la visita guiada... Ver mas 
		 * Botón “Salir”
		 * Nota: este mensaje se mostrará durante toda la visita guiada
		 */
		addStep("Validar que se muestre pantalla [Generación De Pedido]");
		boolean validacion = ProveedoresD.validarAvisoVisitaGuiada();
		assertTrue(validacion, "no se muestra de manera correcta el Aviso salir visita guiada");
		
		
		/*
		 * 6	Colocar aplicación "Proveedores Directos" en segundo plano del dispositivo.
		 */
		addStep("Colocar aplicación [Proveedores Directos] en segundo plano del dispositivo.");
		ProveedoresD.botonHOME();
		
		/*
		 * 7	Abrir aplicacion “Proveedores Directos”
		 */
		addStep("Abrir aplicacion “Proveedores Directos”");
		appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
		Thread.sleep(2000);//tiempo para que se se muestre correctamente la evidencia
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 * 8	Seleccionar botón [Salir] del mensaje anclado en la  Visita Guiada
		 */
		addStep("Seleccionar botón [Salir] del mensaje anclado en la  Visita Guiada");
		ProveedoresD.salirVisitaGuiada();
		
		/*
		 * 9	Seleccionar la opcion "Si"
		 */
		addStep("Seleccionar la opcion [Si]");
		ProveedoresD.opcionSi();
		
		/*
		 * Validar que nos posicione en la pantalla de "Bienvenida"
		 */
		addStep("Validar que se muestre pantalla de [Bienvenida]");
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),
				"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");
		
		/*
		 * 10	Seleccionar la opción “Capacitación con Visita Guiada”
		 */
		addStep("Seleccionar [Capacitación con Visita Guiada]");
		assertTrue(ProveedoresD.VGCapacitacion(), "Boton no disponible: Capacitacion con Visita Guiada\"");
		
		/*
		 * 11	Seleccionar la opción “Preventa”
		 */
		addStep("Seleccionar la opción [Preventa]");
		ProveedoresD.opcionPreventa();
		
		/*
		 * 12	Seleccionar la opción “Por Herramienta de Compra”
		 */
		addStep("Seleccionar la opción [Por Herramienta de Compra]");
		ProveedoresD.OpcionHerramientaCompra();
		Thread.sleep(5000);
		
		/*
		 * 13	Validar que se muestre pantalla “Generación De Pedido”
		 * Validar mensaje: 
		 * Estés realizando un proceso en la visita guiada... Ver mas
		 * Botón “Salir”
		 */
		addStep("Validar que se muestre pantalla [Generación De Pedido]");
		validacion = ProveedoresD.validarAvisoVisitaGuiada();
		assertTrue(validacion, "no se muestra de manera correcta el Aviso salir visita guiada");
		
		/*
		 * paso 14: Continuar con el flujo hasta llegar a la pantalla “Detalle de pedido” de la Hand Held
		 */
		addStep("Continuar con el flujo hasta llegar a la pantalla [Detalle de pedido] de la Hand Held");
		OxxoAlDia.opcionVerPasosGenerarQR();
		ProveedoresD.opcionSiguiente();
		ProveedoresD.VGcontinuar();
		OxxoAlDia.pegarQRp();
		ProveedoresD.VGsi();
		ProveedoresD.clicSumar();
		ProveedoresD.clicSumar();
		ProveedoresD.clicSumar();
		ProveedoresD.clicSumar();
		Thread.sleep(3000);
		ProveedoresD.clicContiuar();
		Thread.sleep(1000);
		ProveedoresD.opcionContinuarQR();
		ProveedoresD.opcionContinuarQR();
		Thread.sleep(3000);
		ProveedoresD.opcionComenzar();
		ProveedoresD.clicPantalla();
		ProveedoresD.clicContiuar();
		ProveedoresD.VGPasosLecturaQR();
		// copiar codigo QR
		OxxoAlDia.copiarQR(); 
		// cerrar app proveedores
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		// abrir app Oxxo al Dia
		appiumUtil.driver.activateApp(data.get("appPackage") + "/" + data.get("appActivity"));
		Thread.sleep(5000);
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		
		OxxoAlDia.ingresarModuloHerramientas();
		OxxoAlDia.opcionVerificacionMercancia();
		OxxoAlDia.opcionleerQR();
		
		Thread.sleep(4000);
		OxxoAlDia.pegarQR();
		OxxoAlDia.aceptar();
		Thread.sleep(4000);
		
		
		/*
		 * Validar que se muestre el mensaje anclado 
		 * Estés realizando un proceso en la visita guiada... Ver mas 
		 * Botón “Salir”
		 * 
		 */
		addStep("Validar que se muestre el mensaje anclado:"
				+ "Estés realizando un proceso en la visita guiada... Ver mas "
				+ "Botón [Salir]");
		validacion = ProveedoresD.validarAvisoVisitaGuiadaOAD();
		assertTrue(validacion, "no se muestra de manera correcta el Aviso salir visita guiada");
		
		
		/*
		 * 15	Colocar aplicación "Oxxo al día" en segundo plano del dispositivo.
		 */
		addStep("Colocar aplicación [Oxxo al día] en segundo plano del dispositivo.");
		ProveedoresD.botonHOME();
		
		/*
		 * 16	Abrir aplicacion “Oxxo al Día”
		 */
		addStep("Abrir aplicacion [Oxxo al Día]");
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		Thread.sleep(2000);//tiempo para que se se muestre correctamente la evidencia
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 * Seleccionar botón “Salir” del mensaje anclado en la  Visita Guiada
		 */
		
		addStep("Seleccionar botón [Salir] del mensaje anclado en la  Visita Guiada");
		OxxoAlDia.opcionSalirDeVisitaGuiada();
		/*
		 * Seleccionar la opción "Si"
		 */
		addStep("Seleccionar la opción [Si]");
		OxxoAlDia.opcionSiGeneral();
		
		/*
		 * Validar que nos posicione en la pantalla de "Proveedores Directos"
		 */
		
		addStep("Validar que nos posicione en la pantalla de [Proveedores Directos]");
		validacion = OxxoAlDia.pantallaProveedoresDirectos();
		assertTrue(validacion,"Error, la pantalla de proveedores directos no se mestra correctamente");

	}
	
	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-160 Salida de visita guiada en un  Flujo de capacitación Preventa por herramienta de compra y salida segundo plano
	 * Desc: Validar que al encontrarnos dentro de visita guiada pedido Preventa por herramienta permita dejar la aplicación en segundo plano y al regresar a la aplicación continue en el mismo menú, estando en esta misma pantalla se valida que se pueda salir del menú a la pantalla principal presionando el botón Salir.
	 * 
	 * @autor Miguel Angel Bautista Marroquin
	 * 
	 * @date 25/10/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_097_CapacitacionSurtidoPreventaHerramientaCompraSegundoPlano";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al encontrarnos dentro de visita guiada pedido Preventa por herramienta permita dejar la aplicación en segundo plano y al regresar a la aplicación continue en el mismo menú, estando en esta misma pantalla se valida que se pueda salir del menú a la pantalla principal presionando el botón Salir.";
	}

	@Override
	public String setTestDesigner() {
		return "Miguel Bautista";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1.	Contar con la versión mas reciente del APK Proveedores directos \r\n"
				+ "2.	Contar con un dispositivo móvil con Android 11";
	}

	@Override
	public void beforeTest() {
		
	}

}
