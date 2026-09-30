package Mobile.cedis.gv;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class Debug_Marcelo extends BaseExecution {
	@SuppressWarnings("null")
	@Test(dataProvider = "data-provider", enabled = true)
	public void Debug_Marcelo_Test(HashMap<String, String> data) throws InterruptedException, IOException  {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		caps.setCapability("deviceName", data.get("deviceName"));
		caps.setCapability("platformVersion", data.get("platformVersion"));
		caps.setCapability("platformName", data.get("platformName"));
		String url = "http://127.0.0.1:4723/";

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, caps);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}

		// Ejecuta el logeo hacia el punto de venta
		System.out.println("\nLogeandose en el Punto de Venta Oxxo al dia...");
		Thread.sleep(3000);
		Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		login.loginPV(data.get("ip"));
		Thread.sleep(3000);

		// Ingresar al menu principal de Oxxo al dia
		System.out.println("\nIngresando al menu principal de Oxxo al dia...");
		Thread.sleep(3000);
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		Thread.sleep(3000);

		// Seleccionar el icono de Excelencia Operativa
		Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		System.out.println("\nDando clic en Excelencia Operativa...");
		Thread.sleep(2000);
		herramienta.SeleccionarObjeto("Excelencia");
		Thread.sleep(3000);

		// Seleccionar el icono de Herramienta de Compra
		System.out.println("\nDando clic en Herramienta de Compra...");
		Thread.sleep(2000);
		herramienta.SeleccionarObjeto("Herramienta");
		Thread.sleep(3000);

		// Seleccionar el icono de Cometa.
		System.out.println("\nDando clic en Cometa...");
		Thread.sleep(3000);
		herramienta.SeleccionarObjeto("Cometa");
		Thread.sleep(3000);

		// Clic en Recepcion de Pedidos CEDIS
		System.out.println("\nDando clic en Recepcion de Pedidos CEDIS...");
		Thread.sleep(3000);
		Mobile.cedis.om.A04_MenuHerramientas C04MenuHerramienta = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil,
				this);
		C04MenuHerramienta.clickBtnRecepcionPedidosCEDIS();
		Thread.sleep(3000);

		// Login de Area de Distribucion
		System.out.println("\nLogeando Area de Distribucion...");
		Thread.sleep(3000);
		Mobile.cedis.om.A05_LoginAreaDistribucion C05LoginAreaDistribucion = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase,
				appiumUtil, this);
		C05LoginAreaDistribucion.LoginAreaDistribucion();
		Thread.sleep(3000);

		// Dando clic en Recepcion Sin Escaner
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia B01_RecepcionPedidosSeleccionarTransferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(
				testCase, appiumUtil, this);
		System.out.println("\nDando clic en Switch Recepcion Sin Escaner y Entrando en la Primer Transferencia...");
		Thread.sleep(3000);
		B01_RecepcionPedidosSeleccionarTransferencia.SeleccionarPrimeraTransferencia(true);
		Thread.sleep(3000);

		// Dando clic en Recepcion de Pedido
		Mobile.cedis.om.B10_DeseaComenzar B10_DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		System.out.println("\nEsperando carga de las Rutas y dando clic en Recepcion de Pedido...");
		Thread.sleep(3000);
		B10_DeseaComenzar.Cargando();
		Thread.sleep(3000);
		System.out.println("\nDando click en Recepcion de Pedido.");
		Thread.sleep(3000);
		B10_DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		Thread.sleep(3000);

		
		// Ingresando las Imagenes de evidencia de Recepcion
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS B02_RecepcionPedidosCEDIS = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		System.out.println("\nRealizando scroll hasta el tope superior de la pantalla, para la posterior toma de evidencias...");
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
		Thread.sleep(3000);
		System.out.println("\nValidando pantalla e información general...");
		Thread.sleep(3000);
		B02_RecepcionPedidosCEDIS.ValidacionEncabezado();
		Thread.sleep(3000);
		System.out.println("\nIngresando las 3 imagenes de evidencia obligatorias...");
		Thread.sleep(3000);
		B02_RecepcionPedidosCEDIS.Tomar3Fotos();
		Thread.sleep(3000);
		System.out.println("\nValidando las 3 imagenes de evidencia tomadas...");
		Thread.sleep(3000);
		B02_RecepcionPedidosCEDIS.ValidarGuardadoDeLasTresFotografias();
		Thread.sleep(3000);
		

		// Paso 1: Se ubica en la pantalla: "Recepción de pedido CEDIS", en la subsección: "Contenedor"
		// Resultado esperado: Se despliega correctamente el campo de texto: "Ingresa código de contenedor". En la pantalla: "Recepción 		                                                                		   de pedido CEDIS", en la subsección: "Contenedor""	
		System.out.println("\nPaso #1 Se ubica en la pantalla: Recepcion de pedido CEDIS, en la subseccion: Contenedor");
		addStep("Se ubica en la pantalla: Recepcion de pedido CEDIS, en la subseccion: Contenedor", "Resultado esperado: Se despliega correctamente el campo de texto: Ingresa código de contenedor.");
		Thread.sleep(3000);
		System.out.println("\nMoviendose a la seccion: Contenedor");
		Thread.sleep(3000);
		B02_RecepcionPedidosCEDIS.MoverSeccion("Campo Ingresar Codigo Contenedor");
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 75");
		Thread.sleep(3000);
		System.out.println("\nValidando la seccion: Contenedor");
		Thread.sleep(3000);
		B02_RecepcionPedidosCEDIS.ValidarSeccionContenedor();
		Thread.sleep(3000);
		System.out.println("\nTomando evidencia del paso");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
		Thread.sleep(3000);
		
		
		// Paso 2: Se da clic en el campo de texto: "Ingresa código de contenedor".	
		// Resultado esperado: Se despliega correctamente el teclado.	
		System.out.println("\nPaso #2 Se da clic en el campo de texto: Ingresa codigo de contenedor");
		addStep("Se da clic en el campo de texto: Ingresa codigo de contenedor", "Resultado esperado: Se despliega correctamente el teclado.");		
		Thread.sleep(3000);
		System.out.println("\nDando clic en el campo: Ingresa codigo contenedor");
		Thread.sleep(3000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Ingresar Contenedor");
		Thread.sleep(3000);
		System.out.println("\nValidando despliegue de campo y teclado");
		Thread.sleep(3000);
		B02_RecepcionPedidosCEDIS.validarTecladoDesplegado();
		Thread.sleep(3000);
		System.out.println("\nTomando evidencia del paso");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		Thread.sleep(3000);

		
		// Paso 3 Se escribe el codigo de una canastilla. Que no este dentro del pedido para agregarla en la recepcion.	
		// Resultado esperado: Se ingresa el codigo de una canastilla. Que no este dentro del pedido para agregarla en la recepcion.
		System.out.println("\nPaso #3 Se escribe el codigo de una canastilla. Que no este dentro del pedido para agregarla en la recepcion.");
		addStep("Se escribe el codigo de una canastilla. Que no este dentro del pedido para agregarla en la recepcion.", "Resultado esperado: Se ingresa el codigo de una canastilla. Que no este dentro del pedido para agregarla en la recepcion.");
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell input keyevent KEYCODE_7");
		Thread.sleep(1500);
		Runtime.getRuntime().exec("adb shell input keyevent KEYCODE_8");
		Thread.sleep(1500);
		Runtime.getRuntime().exec("adb shell input keyevent KEYCODE_9");
		Thread.sleep(1500);
		Runtime.getRuntime().exec("adb shell input keyevent KEYCODE_7");
		Thread.sleep(1500);
		Runtime.getRuntime().exec("adb shell input keyevent KEYCODE_8");
		Thread.sleep(1500);
		Runtime.getRuntime().exec("adb shell input keyevent KEYCODE_9");
		Thread.sleep(3000);
		System.out.println("\nTomando evidencia del paso");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		Thread.sleep(3000);

		
		// Paso 4 Se da clic en el botón de: "Enter".
		// Resultado esperado: Se despliega correctamente el mensaje: "El código que ha ingresado no se encuentra en el pedido" [Confirmar]
		System.out.println("\nPaso #4 Se da clic en el boton de: Enter.");
		addStep("Se da clic en el boton de: Enter", "Resultado esperado: Se despliega correctamente el mensaje: El codigo que ha ingresado no se encuentra en el pedido");	
		Runtime.getRuntime().exec("adb shell input keyevent KEYCODE_ENTER");
		Thread.sleep(3000);
		System.out.println("\nValidando despliegue del mensaje: El codigo que ha ingresado no se encuentra en el pedido");
		Thread.sleep(3000);
		B02_RecepcionPedidosCEDIS.ValidacionMsjCanastillaNoPertenecePedido();
		Thread.sleep(3000);
		System.out.println("\nTomando evidencia del paso");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		Thread.sleep(3000);

		// Cerrar Apps
		System.out.println("Terminando caso y cerrando OaD...");
	}

//Datos para el caso de prueba
	@AfterMethod
	public void baseAfterMethod(ITestResult result) {
		
	}

	@BeforeTest
	public void beforeTest() {
	}

	@Override
	public String setTestFullName() {
		return "DEBUG";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al \"aceptar\" el mensaje fotografias de canastillas con devolucion la pantalla haga un autoscroll hasta la parte donde hace falta la evidencia";
	}

	@Override
	public String setPrerequisites() {
		return "*Haber realizado la recepción de una transferencia que contenga al menos bultos tipo canastilla\r\n"
				+ "*Indicar cantidad de canastillas con devolución (color correspondiente)\r\n"
				+ "*Haber seleccionado un BOL al menos\r\n"
				+ "*Haber colocado la evidencia de devolución de canastillas";
	}

	@Override
	public String setTestDesigner() {
		return "Marcelo Gutiérrez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
