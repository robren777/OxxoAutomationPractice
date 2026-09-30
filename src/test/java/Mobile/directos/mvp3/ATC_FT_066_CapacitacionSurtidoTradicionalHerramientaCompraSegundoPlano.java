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

public class ATC_FT_066_CapacitacionSurtidoTradicionalHerramientaCompraSegundoPlano extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_066_CapacitacionSurtidoTradicionalHerramientaCompraSegundoPlano_test(HashMap<String, String> data) throws Exception {
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appPackage", data.get("appPackageP"));
		cap.setCapability("appActivity", data.get("appActivityP"));
		// cap.setCapability("appium:settings[allowInvisibleElements]", true);
		cap.setCapability("appium:settings[enableMultiWindows]", true);
		cap.setCapability("noReset", true); // Se debe de dejar esta opcion en true para que no se borre el cache y asi
											// lograr evitar la vista de ingresar la IP y puerto

		String url = "http://127.0.0.1:4723";

		// =======================================Cerrar
		// Aplicaciones========================================================//
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackageP") + "\"");
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");
		// =======================================Inicio
		// Aplicaciones========================================================//
		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}
		Thread.sleep(3500);

		// =========================================Llamado al OM============================================================//
		Login login = new Login(testCase, appiumUtil, this);
		OAD OxxoAlDia = new OAD(testCase, appiumUtil, null);
		ProveedoresDirectos ProveedoresD = new ProveedoresDirectos(testCase, appiumUtil, this);
		Thread.sleep(5000);
		
		
		/*
		 * 1	Abrir la aplicación “Proveedores directos”
		 */
		addStep("Abrir la aplicación “Proveedores directos”");
		/*
		 * 2	Validar que se muestre pantalla de “Bienvenida”
		 */
		
		/*
		 * 3	Seleccionar “Capacitación con Visita Guiada”
		 */
		addStep("Seleccionar “Capacitación con Visita Guiada”");
		assertTrue(ProveedoresD.VGCapacitacion(), "Boton no disponible: \"Capacitacion con Visita Guiada\"");
		/*
		 * 4	Seleccionar “Surtido Tradicional (Venta a bordo)”
		 */
		addStep("Seleccionar “Surtido Tradicional (Venta a bordo)”");
		ProveedoresD.OpcionSurtidoTradicional();
		
		/*
		 * 5	Seleccionar “Por Herramienta de compra”
		 */
		addStep("Seleccionar “Por Herramienta de compra”");
		ProveedoresD.OpcionHerramientaCompra();
		
		
		/*
		 * 6	Validar que se muestre pantalla “Generación De Pedido”
		 * 7	"Validar mensaje: 
		 * Estés realizando un proceso en la visita guiada... Ver mas
		 * Botón “Salir”
		 * 
		 * Nota: este mensaje se mostrará durante toda la visita guiada"
		 */
		addStep("Validar que se muestre pantalla “Generación De Pedido”");
		boolean validacion = ProveedoresD.validarAvisoVisitaGuiada();
		assertTrue(validacion, "no se muestra de manera correcta el Aviso salir visita guiada");
		
		
		/*
		 * 8	"Validar mensaje: 
		 * Selecciona la opción “Escanear QR de pedido o QR de Hta. de compra” para iniciar el proceso de recepción de mercancía.
		 * Solicita al equipo de tienda que te proporcione el código QR de la herramienta de compra correspondiente a tu fuente de abasto.
		 * Botón: Ver pasos para generar QR en Hand Held"
		 */
		
		addStep("Validar mensaje: Selecciona la opción “Escanear QR de pedido o QR de Hta. de compra” para iniciar el proceso de recepción de mercancía. "
				+ "Solicita al equipo de tienda que te proporcione el código QR de la herramienta de compra correspondiente a tu fuente de abasto."
				+ "Botón: Ver pasos para generar QR en Hand Held\"");
		
		assertTrue(OxxoAlDia.ValidarMensajeEscanearQRpedidoT(), "El mensaje no se muestra de la forma correcta");
		
		
		/*
		 * 9	Colocar aplicación "Proveedores Directos" en segundo plano del dispositivo.
		 */
		addStep("Colocar aplicación [Proveedores Directos] en segundo plano del dispositivo.");
		ProveedoresD.botonHOME();
		
		/*
		 * 10	Abrir aplicacion “Proveedores Directos”
		 */
		addStep("Abrir aplicacion “Proveedores Directos”");
		appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
		Thread.sleep(2000);//tiempo para que se se muestre correctamente la evidencia
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		/*
		 * 11	Seleccionar botón “Salir” del mensaje anclado en la Visita Guiada
		 */
		addStep("Seleccionar botón [Salir] del mensaje anclado en la  Visita Guiada");
		ProveedoresD.salirVisitaGuiada();
		
		/*
		 * 12	Seleccionar la opcion "Si"
		 */
		addStep("Seleccionar la opcion [Si]");
		ProveedoresD.opcionSi();
		
		/*
		 * 14	Validar que se muestre pantalla de “Bienvenida”
		 */
		addStep("Validar que se muestre pantalla de [Bienvenida]");
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),
				"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");
		
		/*
		 * 15	Seleccionar “Capacitación con Visita Guiada”
		 */
		addStep("Seleccionar [Capacitación con Visita Guiada]");
		assertTrue(ProveedoresD.VGCapacitacion(), "Boton no disponible: Capacitacion con Visita Guiada\"");
		
		/*
		 * 16	Seleccionar “Surtido Tradicional (Venta a bordo)”
		 */
		addStep("Seleccionar [Surtido Tradicional (Venta a bordo)]");
		ProveedoresD.OpcionSurtidoTradicional();
		ProveedoresD.OpcionHerramientaCompra();
		/*
		 * 17	Continuar con el flujo hasta llegar a la pantalla “Detalle de pedido” de la Hand Held
		 */
		addStep("Continuar con el flujo hasta llegar a la pantalla “Detalle de pedido” de la Hand Held");
		Thread.sleep(5000);
		ProveedoresD.opcionPedidoQR();
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
		OxxoAlDia.copiarQR();
		
		// abrir app Oxxo al Dia
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		Thread.sleep(5000);
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
				
		OxxoAlDia.ingresarModuloHerramientas();
		OxxoAlDia.opcionVerificacionMercancia();
		OxxoAlDia.opcionleerQR();
		Thread.sleep(4000);
		OxxoAlDia.pegarQR();
		Thread.sleep(3000);
		OxxoAlDia.aceptar();
		Thread.sleep(3000);
		
		
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
		 * 18	Colocar aplicación "Oxxo al día" en segundo plano del dispositivo.
		 */
		addStep("Colocar aplicación [Oxxo al día] en segundo plano del dispositivo.");
		ProveedoresD.botonHOME();
		
		/*
		 * 19	Abrir aplicacion “Oxxo al Día”
		 */
		addStep("Abrir aplicacion [Oxxo al Día]");
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		Thread.sleep(2000);//tiempo para que se se muestre correctamente la evidencia
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*
		 * 20	Seleccionar botón “Salir” del mensaje anclado en la  Visita Guiada
		 */
		addStep("Seleccionar botón [Salir] del mensaje anclado en la  Visita Guiada");
		OxxoAlDia.opcionSalirDeVisitaGuiada();
		
		/*
		 * 21	Seleccionar la opción "Si"
		 */
		addStep("Seleccionar la opción [Si]");
		OxxoAlDia.opcionSiGeneral();
		
		/*
		 * Validar que nos posicione en la pantalla de "PROVEEDORES DIRECTOS"
		 */
		addStep("Validar que nos posicione en la pantalla de [Proveedores Directos]");
		validacion = OxxoAlDia.pantallaProveedoresDirectos();
		assertTrue(validacion,"Error, la pantalla de proveedores directos no se mestra correctamente");

	}

	/*
	 * Proveedores Directos MVP3:
	 * MTC-FT-108 Salida de visita guiada en un  Flujo de capacitación Surtido Tradicional por herramienta de compra y salida segundo plano
	 * Desc: Validar que al encontrarnos dentro de visita guiada pedidoTradicional por herramienta permita dejar la aplicación en segundo plano y al regresar a la aplicación continue en el mismo menú, estando en esta misma pantalla se valida que se pueda salir del menú a la pantalla principal presionando el botón Salir.
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 28/10/2024
	 * 
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_066_CapacitacionSurtidoTradicionalHerramientaCompraSegundoPlano";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al encontrarnos dentro de visita guiada pedidoTradicional por herramienta permita dejar la aplicación en segundo plano y al regresar a la aplicación continue en el mismo menú, estando en esta misma pantalla se valida que se pueda salir del menú a la pantalla principal presionando el botón Salir.";
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
