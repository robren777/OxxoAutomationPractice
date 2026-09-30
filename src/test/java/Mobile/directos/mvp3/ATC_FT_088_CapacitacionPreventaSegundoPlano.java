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
//caso 13 spint 7
public class ATC_FT_088_CapacitacionPreventaSegundoPlano extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_088_CapacitacionPreventaSegundoPlano_test(HashMap<String, String> data) throws Exception {
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
		 * 2	Validar que se muestre pantalla de “Bienvenida”
		 * Se muestra la pantalla Capacitación con Visita Guiada con los siguientes componentes:
		 * Logotipo de Oxxo
		 * Texto: Selecciona el tipo de surtido
		 * Opción: Preventa
		 * Opción: Surtido Tradicional (Venta a bordo)
		 * Opción: Pedido Precargado (Sinergia/Reple)
		 */
	
		addStep("Abrir la aplicación [Proveedores directos]","Validar que se muestre pantalla de [Bienvenida]");
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),
				"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");
		
		/*
		 * 3	Seleccionar “Capacitación con Visita Guiada”
		 * Se muestra la pantalla Capacitación con Visita Guiada con los siguientes componentes:
		 * Logotipo de Oxxo
		 * Texto: Hay dos formas de realizar el pedido, selecciona alguna para comenzar la capacitación
		 * Opción: Por Captura Manual Escaneando artículo por artículo
		 * Opción: Por Herramienta de Compra
		 */
		
		addStep("Seleccionar “Capacitación con Visita Guiada”");
		assertTrue(ProveedoresD.VGCapacitacion(), "Boton no disponible: \"Capacitacion con Visita Guiada\"");
		
		/*
		 * 4	Seleccionar “Pereventa”
		 */
		addStep("Seleccionar [Preventa]");
		ProveedoresD.opcionPreventa();
		
		/*
		 * 5	Seleccionar “Por Captura Manual Escaneando artículo por artículo”
		 */
		addStep("Seleccionar “Por Captura Manual Escaneando artículo por articulo”");
		assertTrue(ProveedoresD.VGCapturaManual(), "Boton no disponible: Por Captura Manual");
		
		/*
		 * 7	Validar mensaje:
		 * Estés realizando un proceso en la visita guiada... Ver mas 
		 * Botón “Salir”
		 * Nota: este mensaje se mostrará durante toda la visita guiada
		 */
		addStep("Validar mensaje: Estés realizando un proceso en la visita guiada... Ver mas \n"
				+ "Botón “Salir” \n"
				+ "Nota: este mensaje se mostrará durante toda la visita guiada");
		boolean validacion = ProveedoresD.validarAvisoVisitaGuiada();
		assertTrue(validacion, "no se muestra de manera correcta el Aviso salir visita guiada");
		
		/*
		 * 8 Validar mensaje: 
		 * Selecciona la opción “Generar Pedido/ Preventa"" paa iniciar el proceso de recepción de mercancia."
		 */
		addStep("Validar mensaje:  Selecciona la opción “Generar Pedido/ Preventa\"\" paa iniciar el proceso de recepción de mercancia.");
		validacion = ProveedoresD.ValidarMensajePedidoEntrega();
		assertTrue(validacion,"Error el mensaje no se muestra de manera correcta");
		
		/*
		 * 9	Seleccionar “Generar Pedido/Preventa
		 */
		addStep("Seleccionar [Generar Pedido/Preventa]");
		ProveedoresD.opcionPedidoEntrega();
		
		/*
		 * 10	"Validar mensaje: 
		 * Captura tu Fuente de abasto, mejor conocido como número de proveedor."
		 */
		addStep("Validar mensaje: Captura tu Fuente de abasto, mejor conocido como número de proveedor.");
		assertTrue(ProveedoresD.VGFuenteAbastoTB(),"Error el mensaje no se muestra de manera correcta");
		
		/*
		 * 11	Ingresar fuente de abasto manualmente
		 */
		addStep("Ingresar fuente de abasto manualmente");
		ProveedoresD.ingresarFuenteAbasto("12321");
		Thread.sleep(2000); 
		
		/*
		 * 12	Colocar aplicación "Proveedores Directos" en segundo plano del dispositivo.
		 */
		addStep("Colocar aplicación \"Proveedores Directos\" en segundo plano del dispositivo.");
		ProveedoresD.botonHOME();
		
		/*
		 * 13	Abrir aplicacion “Proveedores Directos”
		 */
		addStep("Abrir aplicacion [Proveedores Directos]");
		appiumUtil.driver.activateApp(data.get("appPackageP")+"/"+data.get("appActivityP"));
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		
		/*
		 * 14	Seleccionar botón “Salir” del mensaje anclado en la  Visita Guiada
		 */
		addStep("Seleccionar botón [Salir] del mensaje anclado en la  Visita Guiada");
		ProveedoresD.salirVisitaGuiada();
		/*
		 * 15	Seleccionar la opcion "Si"
		 */
		addStep("Seleccionar la opcion [Si]");
		ProveedoresD.opcionSi();
		
		/*
		 * 16	Validar que se muestre pantalla de “Bienvenida”
		 * ?Se muestra pantalla principal de Proveedores Directos con los siguientes componentes:
		 * Logotipo de Oxxo
		 * Texto: ¡Bienvenido!
		 * Opción: Comenzar proceso de Recepción de mercancía
		 * Opción: Capacitación con Visita Guiada
		 */
		addStep("Se muestra pantalla principal de Proveedores Directos con los siguientes componentes: \r\n"
				+ "Logotipo de Oxxo \r\n"
				+ "Texto: ¡Bienvenido! \r\n"
				+ "Opción: Comenzar proceso de Recepción de mercancía \r\n"
				+ "Opción: Capacitación con Visita Guiada");
		
		assertTrue(ProveedoresD.pantallaPrincipalProveedoresDirectos(),
				"La pantalla principal de la App Proveedores Directos no se muestra de manera correcta.");
		
		/*
		 * 17	Seleccionar “Capacitación con Visita Guiada”
		 */
		addStep("Seleccionar [Capacitación con Visita Guiada]");
		assertTrue(ProveedoresD.VGCapacitacion(), "Boton no disponible: Capacitacion con Visita Guiada\"");
		/*
		 * 18	Seleccionar “Preventa”
		 */
		addStep("Seleccionar [Preventa]");
		ProveedoresD.opcionPreventa();
		
		
		/*
		 * 19	Continuar con el flujo hasta llegar a la pantalla “Detalle de pedido” de la Hand Held
		 */
		addStep("Continuar con el flujo hasta llegar a la pantalla “Detalle de pedido” de la Hand Held");
		
		ProveedoresD.VGCapturaManual();
		Thread.sleep(7000);
		ProveedoresD.opcionPedidoEntrega();
		ProveedoresD.ingresarFuenteAbasto("12321");
		Thread.sleep(2000); 
		ProveedoresD.VGCodigoDeBarras();
		ProveedoresD.VGpaloma();
		ProveedoresD.VGsumar();
		ProveedoresD.VGClicPantalla();
		ProveedoresD.VGcontinuar();
		ProveedoresD.VGsi();
		ProveedoresD.opcionContinuarQR();
		ProveedoresD.opcionContinuarQR();
		Thread.sleep(2000);
		ProveedoresD.opcionComenzar();
		ProveedoresD.clicPantalla();
		ProveedoresD.clicContiuar();
		ProveedoresD.VGPasosLecturaQR();
		OxxoAlDia.copiarQR();
		
		// abrir app Oxxo al Dia
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		Thread.sleep(5000);
		
		login.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		
		OxxoAlDia.ingresarModuloHerramientas();
		OxxoAlDia.opcionVerificacionMercancia();
		
		OxxoAlDia.opcionleerQR();
		
		addStep("Escanear QR");
		Thread.sleep(4000);
		OxxoAlDia.pegarQR();
		
		Thread.sleep(3000);
		OxxoAlDia.aceptar();
		
		
		/*
		 * 20	Colocar aplicación "Oxxo al día" en segundo plano del dispositivo.
		 */
		addStep("Colocar aplicación \"Oxxo al día\" en segundo plano del dispositivo.");
		ProveedoresD.botonHOME();
		
		
		/*
		 * 21	Abrir aplicacion “Oxxo al Día”
		 */
		addStep("Abrir aplicacion “Oxxo al Día”");
		appiumUtil.driver.activateApp(data.get("appPackage")+"/"+data.get("appActivity"));
		
		
		/*
		 * 22	Seleccionar botón “Salir” del mensaje anclado en la  Visita Guiada
		 */
		addStep("Seleccionar botón [Salir] del mensaje anclado en la  Visita Guiada");
		OxxoAlDia.opcionSalirDeVisitaGuiada();
		
		/*
		 * 23	Seleccionar la opción "Si"
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
	 * MTC-FT-150 Salida de visita guiada en un  Flujo Preventa y salida segundo plano
	 * Desc: Validar que al encontrarnos dentro de visita guiada pedido Preventa permita dejar la aplicación en segundo plano y al regresar a la aplicación continue en el mismo menú, estando en esta misma pantalla se valida que se pueda salir del menú a la pantalla principal presionando el botón Salir.
	 * 
	 * @autor Miguel Bautista
	 * 
	 * @date 28/10/2024
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_088_CapacitacionPreventaSegundoPlano";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al encontrarnos dentro de visita guiada pedido Preventa permita dejar la aplicación en segundo plano y al regresar a la aplicación continue en el mismo menú, estando en esta misma pantalla se valida que se pueda salir del menú a la pantalla principal presionando el botón Salir.";
	}

	@Override
	public String setTestDesigner() {
		return "Miguel Bautista Marroquin";
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
