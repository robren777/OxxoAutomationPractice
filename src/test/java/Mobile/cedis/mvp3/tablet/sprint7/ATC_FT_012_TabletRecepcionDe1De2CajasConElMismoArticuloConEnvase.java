package Mobile.cedis.mvp3.tablet.sprint7;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_012_TabletRecepcionDe1De2CajasConElMismoArticuloConEnvase extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_012_TabletRecepcionDe1De2CajasConElMismoArticuloConEnvase_test(
			HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		cap.setCapability("appium:appPackage", data.get("appPackageP"));
		cap.setCapability("appium:appActivity", data.get("appActivityP"));
		cap.setCapability("ip", data.get("ip"));
		cap.setCapability("userLogin", data.get("userLogin"));
		cap.setCapability("passwordLogin", data.get("passwordLogin"));
		cap.setCapability("numTransferencia", data.get("numTransferencia"));
		cap.setCapability("contenedor", data.get("contenedor"));

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n" + e.getMessage());
			e.printStackTrace();
		}

		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

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
		B10_DeseaComenzar.Cargando();
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
		//B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753840-7501064113024-2");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-1");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-2");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-3");
		
		B02_RecepcionPedidosCEDIS.CapturaContenedor("667209");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("667445");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("756377");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("756407");
		
		B02_RecepcionPedidosCEDIS.CapturaContenedor("986595");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("986601");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("986618");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("986625");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("756384");
		B02_RecepcionPedidosCEDIS.CapturaContenedor("756391");
		
		// Dando clic en Terminar
		System.out.println("\nDando clic en Terminar...");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Terminar");
		// Dando clic en Confirmar Recepcion
		System.out.println("\nDando clic en Confirmar Recepcion...");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Confirmar");
		// Dando clic en Si Hay Cajas Sin Etiqueta/Danada
		System.out.println("\nDando clic en Si Hay Cajas Sin Etiqueta/Danada...");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Si");
		Thread.sleep(2000);

		
		// Paso #1.- Estar en la sección de \"Cajas sin etiquetas/etiquetas dañadas\" y se muestra por registrar la caja (2) con artículos de envases que no se recepcionó
		// Resultado esperado: Se muestra la sección \"Cajas sin etiquetas/etiquetas dañadas\" con la caja que falto de recepcionar
		System.out.println("\nPaso #1: Posicionarse en la seccion: Cajas Sin Etiqueta/Dañada...");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		addStep("Estar en la sección de \"Cajas sin etiquetas/etiquetas dañadas\" y se muestra por registrar la caja (2) con artículos de envases que no se recepción",
				"Resultado esperado: Se muestra la sección \"Cajas sin etiquetas/etiquetas dañadas\" con la caja que falto de recepcionar");
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// Paso #2.- Registra la caja (2) manualmente 
		// Resultado esperado: Se registra la caja sin etiqueta/etiqueta dañada
		System.out.println("\nPaso #2: Registra la caja (2) manualmente...");
		Thread.sleep(2000);
		addStep("Registra la caja (2) manualmente",
				"Resultado esperado: Se registra la caja sin etiqueta/etiqueta dañada");
		B02_RecepcionPedidosCEDIS.RegistrarSinEtiquetaTablet();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #3.- Termina todo el proceso de recepción del pedido y llega hasta el FUE 
		// Resultado esperado: Se termina correctamente el proceso de recepción y se llegó hasta el FUE
		System.out.println("\nPaso #3: Termina todo el proceso de recepción del pedido y llega hasta el FUE...");
		Thread.sleep(2000);
		addStep("Termina todo el proceso de recepción del pedido y llega hasta el FUE",
				"Resultado esperado: Se termina correctamente el proceso de recepción y se llegó hasta el FUE");
		System.out.println("\nFinalizando proceso de Recepcion...");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Terminar Cajas Sin Etiqueta");
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Confirmar");
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Continuar Dañados");
		B02_RecepcionPedidosCEDIS.SeleccionarObjeto("Terminar Dañados");
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
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #4.- Dentro del resumen FUE se encuentra la sección "Cajas sin etiquetas/etiquetas dañadas" y viene registrado la caja (2) con artículos de envase  
		// Resultado esperado: Se muestra registrado la caja con artículos de envase en el resumen FUE
		System.out.println("\nPaso #4: Dentro del resumen FUE se encuentra la sección \"Cajas sin etiquetas/etiquetas dañadas\" y viene registrado la caja (2) con artículos de envase ...");
		Thread.sleep(2000);
		addStep("Dentro del resumen FUE se encuentra la sección \"Cajas sin etiquetas/etiquetas dañadas\" y viene registrado la caja (2) con artículos de envase",
				"Resultado esperado: ?Se muestra registrado la caja con artículos de envase en el resumen FUE");
		Mobile.cedis.om.B09_FormatoUnicoEntrega B09_FormatoUnicoEntrega = new Mobile.cedis.om.B09_FormatoUnicoEntrega(testCase,
				appiumUtil, this);
		B09_FormatoUnicoEntrega.ValidacionCajasSinEtiqueta();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #5.- Finaliza el proceso de recepción para que se muestre el mensaje del recepción de pedido exitoso  
		// Resultado esperado: Se muestra el mensaje de recepción exitosa
		System.out.println("\nPaso #5: Finaliza el proceso de recepción para que se muestre el mensaje del recepción de pedido exitoso...");
		Thread.sleep(2000);
		addStep("Finaliza el proceso de recepción para que se muestre el mensaje del recepción de pedido exitoso",
				"Resultado esperado: Se muestra el mensaje de recepción exitosa");
		B09_FormatoUnicoEntrega.FinalizarFlujo();
		System.out.println("\nFinalizando...");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(12000);
		System.out.println("\nFinalizando...");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(12000);
		System.out.println("\nFinalizando...");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Thread.sleep(11000);
		System.out.println("\nFinalizando...");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");


		// --------------------------------------------------------------------------------------------------------------------------

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	/*
	 * CEDIS MVP3:
	 * "MTC-FT-012 Tablet Recepción de 1 de 2 cajas con el mismo articulo con envase"
	 * Desc: Validar que se realice correctamente la recepción 1 de 2 cajas con el
	 * mismo articulo con envase.
	 * 
	 * @autor: Jesus Marcelo Gutierrez Bravo
	 * 
	 * @date: 27/01/2025
	 */

	@Override
	public String setTestFullName() {
		return "ATC_FT_012_TabletRecepcionDe1De2CajasConElMismoArticuloConEnvase";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se realice correctamente la recepción  1 de 2 cajas con el mismo articulo con envase";
	}

	@Override
	public String setTestDesigner() {
		return "Jesus Marcelo Gutierrez Bravo";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "\"*ASN/Pedido con artículos de envases (2 cajas de 1 mismo articulo)\r\n"
				+ "*Apk OaD con la función de envases \r\n"
				+ "*Haber recibido solamente 1 caja y dejar la otra para recibir como caja sin etiqueta\r\n"
				+ "*Realizar flujo hasta \"\"Cajas sin etiquetas/etiquetas dañadas\"\"\"";
	}

	@Override
	public void beforeTest() {
	}

}