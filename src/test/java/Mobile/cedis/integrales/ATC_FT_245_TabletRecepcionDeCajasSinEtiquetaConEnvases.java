package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

/**
import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
*/
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.A05_LoginAreaDistribucion;
import Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia;
import Mobile.cedis.om.B02_RecepcionPedidosCEDIS;
import Mobile.cedis.om.B03_ResumenPedidosCEDIS;
import Mobile.cedis.om.B04_AcuerdoEntrega;
import Mobile.cedis.om.B05_DevolucionMercancia;
import Mobile.cedis.om.B07_ResumenDevolucion;
import Mobile.cedis.om.B08_AcuerdoDevolucion;
import Mobile.cedis.om.B09_FormatoUnicoEntrega;
import Mobile.cedis.om.B10_DeseaComenzar;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_245_TabletRecepcionDeCajasSinEtiquetaConEnvases extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_245_TabletRecepcionDeCajasSinEtiquetaConEnvases_test(
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
		cap.setCapability("articulo", data.get("articulo"));

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell pm clear " + data.get("appPackage"));

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n"
					+ e.getMessage());
			e.printStackTrace();
		}

		// Abre la aplicación
				Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
				  // Ejecuta el logeo hacia el punto de venta
						Mobile.cedis.om.A01_Login_Punto_De_Venta login = new Mobile.cedis.om.A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
						System.out.println("Se ingresan puerto y ip para realizar conexion al punto de venta\n");
						login.loginPV(data.get("ip"));

		// Ejecuta el logeo del lider
						Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
						// Obtiene valor del DataProvaider
						System.out.println("Se ingresa credenciales oxxo al dia\n");
						loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
				
		// Menu principal hasta Herramientas de COMETA
						A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
						System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA\n");
						Thread.sleep(2000);
						MenuOaD.logeoInicioHerramientas();

		// En el menu de herramientas doy clic en la recepcion de cedis
						A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
						System.out.println("Dando clic en Recepcion de Pedidos CEDIS\n");
						Thread.sleep(2000);
						ModulosOaD.clickBtnRecepcionPedidosCEDIS();

		// En el menu de herramientas doy clic en la recepcion de cedis
						A05_LoginAreaDistribucion LoginAreaDistribucion = new A05_LoginAreaDistribucion(testCase, appiumUtil, this);
						System.out.println("Logeando Area de Distribucion CEDIS\n");
						LoginAreaDistribucion.LoginAreaDistribucion();
						Thread.sleep(2000);

		
		//OM A utilizar
		B01_RecepcionPedidosSeleccionarTransferencia POM_ListadoDeRecepcion = new B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		B02_RecepcionPedidosCEDIS PedidoRecepcion = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		B03_ResumenPedidosCEDIS ResumenCedis = new B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		B04_AcuerdoEntrega Acuerdo = new B04_AcuerdoEntrega(testCase, appiumUtil, this);		
		B05_DevolucionMercancia POM_Devolucion = new B05_DevolucionMercancia(testCase, appiumUtil, this);
		B07_ResumenDevolucion Resumen = new B07_ResumenDevolucion(testCase, appiumUtil, this);
		B08_AcuerdoDevolucion AcuerdoDev = new B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		B09_FormatoUnicoEntrega FueRec = new B09_FormatoUnicoEntrega(testCase, appiumUtil, this);
		B10_DeseaComenzar POM_DeseaComenzar = new B10_DeseaComenzar(testCase, appiumUtil, this);
				
		POM_ListadoDeRecepcion.ValidacionEncabezado();
		Thread.sleep(5000);
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");//
		Thread.sleep(3000);
		System.out.println("Se muestra la pantalla Recepción de Pedidos CEDIS con el listado de Transferencias asignadas a la tienda.");
		Thread.sleep(2000);
		POM_ListadoDeRecepcion.BuscarTransferencia(true,data.get("numTransferencia"));
		POM_DeseaComenzar.Cargando();
		POM_DeseaComenzar.ValidacionEncabezado();
		Thread.sleep(6000);
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(3000);
		System.out.println("Se muestra correctamente la pantalla de Recepción de Pedidos CEDIS  ¿Como deseas comenzar?");
		POM_DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		Thread.sleep(3000);
		System.out.println("Se inicia el flujo de la transferencia mostrando la pantalla de recepcion de pedido cedis");
		PedidoRecepcion.DeslizarArriba();
		Thread.sleep(2000);
		System.out.println("Se muestra la pantalla de recepcion pedido cedis");
		PedidoRecepcion.Tomar3Fotos();
		System.out.println("Ingresando contenedores\n");
		Thread.sleep(2000);
		PedidoRecepcion.CapturaContenedor(data.get("contenedor"));
		System.out.println("Terminando Recepcion\n");
		Thread.sleep(2000);
		PedidoRecepcion.MoverSeccion("Terminar");
		Thread.sleep(2000);
		PedidoRecepcion.SeleccionarObjeto("Terminar");
		PedidoRecepcion.SeleccionarObjeto("Confirmar");
		PedidoRecepcion.SeleccionarObjeto("Si");
		Thread.sleep(2000);
		
		/*Paso 1 Estar en la seccion "Cajas sin etiquetas/etiquetas danadas" y se muestran por registrar las cajas con articulos de envases que no se recepcionaron 	
		 * Resultado esperado: Se muestra la seccion "Cajas sin etiquetas/etiquetas danadas" con las cajas que no se recepcionaron con articulos de envases.
		 */		
		addStep(" Estar en la seccion \"Cajas sin etiquetas/etiquetas danadas\" y se muestran por registrar las cajas con articulos de envases que no se recepcionaron\r\n"
				+ "Resultado esperado: Se muestra la seccion \"Cajas sin etiquetas/etiquetas danadas\" con las cajas que no se recepcionaron con articulos de envases.");		
		PedidoRecepcion.MoverSeccion("Cajas sin etiqueta");
		System.out.println("Se muestra el proceso de cajas sin etiqueta, la cual incluye envases");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 2 Registra una caja con articulos envases manualmente	
		 * Resultado esperado: Se registra correctamente la caja sin etiqueta/etiqueta danada.
		 */		
		addStep("Registra una caja con articulos envases manualmente \r\n"
				+ "Resultado esperado: Se registra correctamente la caja sin etiqueta/etiqueta danada.");		
		PedidoRecepcion.RegistrarSinEtiquetaTablet();
		System.out.println("Registrando datos de caja sin etiqueta\n");
		Runtime.getRuntime().exec("adb shell input swipe 21 1160 23 900 40");// Se desliza hacía abajo
		Thread.sleep(5000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		PedidoRecepcion.SeleccionarObjeto("Continuar a Faltantes");
		PedidoRecepcion.SeleccionarObjeto("Confirmar");
		
		/*Paso 3 Termina todo el proceso de recepcion del pedido y llega hasta el FUE
		 * Resultado esperado: Se termina correctamente el proceso de recepcion y se llego hasta el FUE.
		 */		
		addStep("Termina todo el proceso de recepcion del pedido y llega hasta el FUE \r\n"
				+ "Resultado esperado: Se termina correctamente el proceso de recepcion y se llego hasta el FUE.");		
		PedidoRecepcion.SeleccionarObjeto("Continuar Dañados");		
		PedidoRecepcion.SeleccionarObjeto("Terminar Dañados");
		PedidoRecepcion.SeleccionarObjeto("Confirmar Dañados");
		ResumenCedis.SeleccionarObjeto("Firma Acuerdo");
		Acuerdo.FirmaAreaDistribucion("", "");
		Acuerdo.FirmaPersonalTienda("", "");
		System.out.println("Se ingresan firmas\n");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Acuerdo.SeleccionarObjeto("Terminar Recepcion");
		System.out.println("Se finaliza el proceso de firmas de acuerdo de entrega, se selecciona boton terminar recepcion");
		Acuerdo.SeleccionarObjeto("Confirmar");
		POM_Devolucion.MoverSeccion("Continuar");
		POM_Devolucion.SeleccionarObjeto("Continuar");
		POM_Devolucion.SeleccionarObjeto("Confirmar");
		Resumen.ClickFirmaAcuerdo("SinEvidencia", null);
		AcuerdoDev.FirmaAreaDistribucion("", "");
		Thread.sleep(2000);
		System.out.println("Firma Personal de Tienda\n");
		Thread.sleep(1000);
		AcuerdoDev.FirmaPersonalTienda("", "");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 20 1003 20 900 90");//
		Thread.sleep(2000);
		System.out.println("Terminando Firmas de Acuerdo de Entrega\n");
		Thread.sleep(1000);
		AcuerdoDev.TerminarRecepcion("", "CEDIS_ConfirmarSeguroTerminarAcuerdo");
		FueRec.ValidacionEncabezado();
		Thread.sleep(6000);
		System.out.println("Se ingresa correctamente a la pantalla formato unico de entrega\n");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 4 Dentro del resumen FUE se encuentra la seccion "Cajas sin etiquetas/etiquetas danadas" y viene registrado la caja con articulos de envase 	
		 * Resultado esperado: Se muestra registrado la caja con articulos de envase en el resumen FUE.
		 */		
		addStep("Dentro del resumen FUE se encuentra la seccion \"Cajas sin etiquetas/etiquetas danadas\" y viene registrado la caja con articulos de envase  \r\n"
				+ "Resultado esperado: Se muestra registrado la caja con articulos de envase en el resumen FUE.");		
		FueRec.ValidacionCajasSinEtiqueta();
		Thread.sleep(3000);
		System.out.println("Se muestra correctamente la seccion cajas sin etiqueta, etiqueta dañada con el articulo envase registrado\n");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 5 Finaliza el proceso de recepcion para que se muestre el mensaje del recepcion de pedido exitoso	
		 * Resultado esperado: Se muestra el mensaje de recepcion exitosa.
		 */		
		addStep("Finaliza el proceso de recepcion para que se muestre el mensaje del recepcion de pedido exitoso\r\n"
				+ "Resultado esperado: Se muestra el mensaje de recepcion exitosa.");		
		FueRec.FinalizarFlujoFUE();
		Thread.sleep(1000);
		FueRec.esperaDinamicaLoaderFinalizarRecepcion();
		Thread.sleep(500);
		FueRec.esperaDinamicaValidarDespliegueMensajeRecepcionExitosa();
		Thread.sleep(5000);
		System.out.println("Se muestra correctamente el mensaje de recepcion de pedido exitosa\n");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
									
										
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC_FT_245_TabletRecepcionDeCajasSinEtiquetaConEnvases";
	}

	@Override
	public String setTestDescription() {
		return "Validar que la recepcion de Envase y SKU del liquidoSe realice al registrar la caja como \"Cajas sin etiquetas\" (sku liquido y envase) con dispositivo Tablet";
	}

	@Override
	public String setTestDesigner() {
		return "Ricardo Hdz Rios";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "*Dispositivo Tablet previamente ya configurado para su uso\r\n"
				+ "*ASN/Pedido con articulos de envases\r\n"
				+ "*Apk OaD con la funcion de envases\r\n"
				+ "*No haber recibido caja en recepcion para poder recibirla como caja sin etiqueta\"";
	}

	@Override
	public void beforeTest() {
	}

}