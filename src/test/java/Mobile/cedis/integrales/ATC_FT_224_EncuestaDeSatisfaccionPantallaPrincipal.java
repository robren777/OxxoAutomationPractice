package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_224_EncuestaDeSatisfaccionPantallaPrincipal extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_224_EncuestaDeSatisfaccionPantallaPrincipal_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		caps.setCapability("deviceName", data.get("deviceName"));
		caps.setCapability("platformVersion", data.get("platformVersion"));
		caps.setCapability("platformName", data.get("platformName"));
		caps.setCapability("appPackage", data.get("appPackage"));
		caps.setCapability("appActivity", data.get("appActivity"));

		String url = "http://127.0.0.1:4723/";

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, caps);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		// Ejecuta el logeo hacia el punto de venta
		System.out.println("\nLogeandose en el Punto de Venta Oxxo al dia...");
		Thread.sleep(2000);
		Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		login.loginPV(data.get("ip"));

		// Ingresar al menu principal de Oxxo al dia
		System.out.println("\nIngresando al menu principal de Oxxo al dia...");
		Thread.sleep(2000);
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
		Thread.sleep(5000);

		// Seleccionar el icono de Excelencia Operativa
		Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		System.out.println("\nDando clic en Excelencia Operativa...");
		Thread.sleep(2000);
		herramienta.SeleccionarObjeto("Excelencia");

		// Seleccionar el icono de Herramienta de Compra
		System.out.println("\nDando clic en Herramienta de Compra...");
		Thread.sleep(2000);
		herramienta.SeleccionarObjeto("Herramienta");

		// Seleccionar el icono de Cometa.
		System.out.println("\nDando clic en Cometa...");
		Thread.sleep(2000);
		herramienta.SeleccionarObjeto("Cometa");

		// Clic en Recepcion de Pedidos CEDIS
		System.out.println("\nDando clic en Recepcion de Pedidos CEDIS...");
		Thread.sleep(2000);
		Mobile.cedis.om.A04_MenuHerramientas C04MenuHerramienta = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil,
				this);
		C04MenuHerramienta.clickBtnRecepcionPedidosCEDIS();

		// Login de Area de Distribucion
		System.out.println("\nLogeando Area de Distribucion...");
		Thread.sleep(2000);
		Mobile.cedis.om.A05_LoginAreaDistribucion C05LoginAreaDistribucion = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase,
				appiumUtil, this);
		C05LoginAreaDistribucion.LoginAreaDistribucion();

		// Dando clic en Recepcion Sin Escaner
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia B01_RecepcionPedidosSeleccionarTransferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(
				testCase, appiumUtil, this);
		System.out.println("\nDando clic en Switch Recepcion Sin Escaner y Entrando en la Primer Transferencia...");
		Thread.sleep(2000);
		B01_RecepcionPedidosSeleccionarTransferencia.SeleccionarPrimeraTransferencia(true);

		// Dando clic en Recepcion de Pedido
		Mobile.cedis.om.B10_DeseaComenzar B10_DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		System.out.println("\nDando clic en Recepcion de Pedido...");
		Thread.sleep(2000);
		B10_DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		
		// Ingresando las Imagenes de evidencia de Recepcion
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS B02_RecepcionPedidosCEDIS = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase,
				appiumUtil, this);
		System.out.println("\nIngresando las 3 Imagenes de evidencia de Recepcion...");
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.Tomar3Fotos();

		// Ingresando bultos
		System.out.println("\nIngresando bultos completos del pedido...");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753840-7501064113024-1");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753840-7501064113024-2");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-1");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-2");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-3");
		
		B02_RecepcionPedidosCEDIS.CapturaContenedor("5667209");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("667445");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("756377");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("756407");
		
		B02_RecepcionPedidosCEDIS.CapturaContenedor("7986595");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("986601");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("986618");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("986625");
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("756384");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("756391");
		
		// Dando clic en Terminar
		System.out.println("\nFinalizando flujo completo de Recepcion (Recepcion y Devolucion)...");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Terminar");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Confirmar");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Continuar Sobrantes");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Terminar Dañados");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Confirmar");
		Thread.sleep(2000);
		Mobile.cedis.om.B03_ResumenPedidosCEDIS B03_ResumenPedidosCEDIS = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase,appiumUtil, this);
		B03_ResumenPedidosCEDIS.SeleccionarObjeto("Firma Acuerdo");
		Thread.sleep(2000);
		Mobile.cedis.om.B04_AcuerdoEntrega B04_AcuerdoEntrega = new Mobile.cedis.om.B04_AcuerdoEntrega(testCase,
				appiumUtil, this);
		B04_AcuerdoEntrega.FirmaAreaDistribucion("", "");
		B04_AcuerdoEntrega.FirmaPersonalTienda("", "");
		B04_AcuerdoEntrega.TerminarRecepcion("", "");
		Thread.sleep(2000);
		Mobile.cedis.om.B05_DevolucionMercancia B05_DevolucionMercancia = new Mobile.cedis.om.B05_DevolucionMercancia(testCase,
				appiumUtil, this);
		B05_DevolucionMercancia.SeleccionarObjeto("Continuar");
		B05_DevolucionMercancia.SeleccionarObjeto("Confirmar Seleccionar Bol");
		Thread.sleep(2000);
		Mobile.cedis.om.B07_ResumenDevolucion B07_ResumenDevolucion = new Mobile.cedis.om.B07_ResumenDevolucion(testCase,
				appiumUtil, this);
		B07_ResumenDevolucion.ClickFirmaAcuerdo("", "");
		Thread.sleep(2000);
		Mobile.cedis.om.B08_AcuerdoDevolucion B08_AcuerdoDevolucion = new Mobile.cedis.om.B08_AcuerdoDevolucion(testCase,
				appiumUtil, this);
		B08_AcuerdoDevolucion.FirmaAreaDistribucion("", "");
		B08_AcuerdoDevolucion.FirmaPersonalTienda("", "");
		B08_AcuerdoDevolucion.TerminarRecepcion("", "");
		Thread.sleep(2000);
		Mobile.cedis.om.B09_FormatoUnicoEntrega B09_FormatoUnicoEntrega = new Mobile.cedis.om.B09_FormatoUnicoEntrega(testCase,
				appiumUtil, this);
				
		B09_FormatoUnicoEntrega.FinalizarFlujoFUE();
		Thread.sleep(1000);
		B09_FormatoUnicoEntrega.esperaDinamicaLoaderFinalizarRecepcion();
		Thread.sleep(500);
		B09_FormatoUnicoEntrega.esperaDinamicaValidarDespliegueMensajeRecepcionExitosa();
		Thread.sleep(500);
		
		B09_FormatoUnicoEntrega.SeleccionarObjeto("Confirmar Pedido");
		Thread.sleep(5000);
		
		// Paso #1.- Se despliega correctamente la pantalla: "Encuesta de satisfaccion"
		// Resultado esperado: Se desplego correctamente la pantalla: "Encuesta de satisfaccion"
		Mobile.cedis.om.B11_EncuestaSatisfaccion B11_EncuestaSatisfaccion = new Mobile.cedis.om.B11_EncuestaSatisfaccion(testCase,
				appiumUtil, this);
		System.out.println("\nPaso #1: Se despliega correctamente la pantalla: \"Encuesta de satisfaccion\"...");
		addStep("Se despliega correctamente la pantalla: \"Encuesta de satisfaccion\"",
				"Resultado esperado: Se desplego correctamente la pantalla: \"Encuesta de satisfaccion\"");
		B11_EncuestaSatisfaccion.ValidacionPantallaEncuestaDeSatisfaccion();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #2.- Se despliegan correctamente los datos de: Fecha, Hora, Ruta, CR Tienda y Transferencia
		// Resultado esperado: Se despliegaron correctamente los datos de: Fecha, Hora, Ruta, CR Tienda y Transferencia
		System.out.println("\nPaso #2: Se despliegan correctamente los datos de: Fecha, Hora, Ruta, CR Tienda y Transferencia...");
		addStep("Se despliegan correctamente los datos de: Fecha, Hora, Ruta, CR Tienda y Transferencia",
				"Resultado esperado: Se despliegaron correctamente los datos de: Fecha, Hora, Ruta, CR Tienda y Transferencia");
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #3.- Se despliega correctamente el boton: "Comenzar
		// Resultado esperado: Se desplego correctamente el boton: "Comenzar
		System.out.println("\nPaso #3: Se despliega correctamente el boton: Comenzar...");
		addStep("Se despliega correctamente el boton: Comenzar",
				"Resultado esperado: Se desplego correctamente el boton: Comenzar");
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	}


	@Override		
	public String setTestFullName() {
		return " MTC-FT-224 - Encuesta de Satisfaccion - Pantalla principal";
	}

	@Override
	public String setTestDescription() {
		return " Validar que, al ser direccionado a la pantalla de encuesta de satisfaccion, se despliegue correctamente la pantalla inicial de encuesta de satisfaccion.";
	}

	@Override
	public String setTestDesigner() {
		return " Jesús Marcelo Gutiérrez Bravo - GTIM QA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "* Haber finalizado la recepcion, de la ultima transferencia listadas";
	}

	@Override
	public void beforeTest() {
	}

}