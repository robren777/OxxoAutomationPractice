package Mobile.cedis.mvp3.handheld.sprint8;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_044_BOLDeDanadosPreviamenteCreadoConCajasRecepcionadasEnRecepcionEnBOLDev extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_044_BOLDeDanadosPreviamenteCreadoConCajasRecepcionadasEnRecepcionEnBOLDev_test(
			HashMap<String, String> data) throws Exception {

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
		cap.setCapability("Bol", data.get("Bol"));

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

		login.loginPV(data.get("ip"));

		// Ejecuta el logeo del lider
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		// Obtiene valor del DataProvaider
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		// Ejecuta los pasos para llegar al menu de Herramientas de CEDIS
		Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		herramienta.logeoInicioHerramientas();

		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
		
		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		Thread.sleep(3000);
		transferencia.BuscarTransferencia(true, data.get("numTransferencia"));
		
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);//linea agregada por Juan 
		DeseaComenzar.Cargando();//linea agregada por Juan
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");//linea agregada por Juan
		
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();
		pantallatransferencia.Tomar3Fotos();
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));//Se captura los contenedores que se encuentran en el excel
		Thread.sleep(3000);
		pantallatransferencia.TerminarHastaResumen();
		
		Mobile.cedis.om.B03_ResumenPedidosCEDIS resumen = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		Thread.sleep(3000);
		resumen.FirmaAcuerdoSinEvidencia();
		
		Mobile.cedis.om.B04_AcuerdoEntrega acuerdo = new Mobile.cedis.om.B04_AcuerdoEntrega(testCase, appiumUtil, this);
		Thread.sleep(3000);
		acuerdo.FirmaAreaDistribucion("SinEvidencia", null);
		acuerdo.FirmaPersonalTienda("SinEvidencia", null);
		acuerdo.TerminarRecepcion("SinEvidencia", null);
		
		
		Mobile.cedis.om.B05_DevolucionMercancia devolucion = new Mobile.cedis.om.B05_DevolucionMercancia(testCase, appiumUtil, this);
		addStep("Estar en la pantalla Devoluciones a CEDIS.\r\n"
				+ "Resultado Esperado: ?Se muestra la pantalla Devoluciones a CEDIS.");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		
		addStep("Dentro de la pantalla selecciona el botón \"Seleccionar BOL\"\r\n"
				+ "Resultado Esperado: ?Se muestra el listado de BOL's disponibles.");
		devolucion.SeleccionarObjeto("Seleccionar Bol");
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		addStep("Dentro del listado que te arroja de los BOL's selecciona el BOL que se generó con la transferencia recepcionada donde venían artículos de envase DAÑADOS.\r\n"
				+ "Resultado Esperado: ?Se visualiza y permite seleccionar BOL.");
		//Se añade la función donde se inserta el BOL especifico que conforman los artículos de envase DAÑADOS
		devolucion.NuevoSeleccionarBol(data.get("Bol"));
		Thread.sleep(3000);
		devolucion.BotonAceptarListaBOL();
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		addStep("Continua el flujo de la recepción hasta llegar a la pantalla Formato Único de Entrega (FUE)\r\n"
				+ "Resultado Esperado: ?Se muestra pantalla FUE.");
		devolucion.AumentarCanastilla(1, "Azul");
		devolucion.AumentarCanastilla(1, "Amarilla");
		devolucion.TomarFotoDevolucion();
		devolucion.NuevoIngresarCanastilla(data.get("canastilla1"));
		Thread.sleep(3000);
		devolucion.VariasFotoCanastilla("sinEvidencia", null);
		Thread.sleep(3000);
		devolucion.SeleccionarObjeto("Continuar");
		Thread.sleep(3000);
		
		Mobile.cedis.om.B07_ResumenDevolucion resumendev = new Mobile.cedis.om.B07_ResumenDevolucion(testCase, appiumUtil, null);
		resumendev.ClickFirmaAcuerdo("SinEvidencia", null);
		Thread.sleep(3000);
		
		Mobile.cedis.om.B08_AcuerdoDevolucion acuerdodev = new Mobile.cedis.om.B08_AcuerdoDevolucion(testCase, appiumUtil, null);
		acuerdodev.FirmaAreaDistribucion("SinEvidencia", null);
		acuerdodev.FirmaPersonalTienda("SinEvidencia", null);
		acuerdodev.SeleccionarObjeto("Cumplio Proceso");
		acuerdodev.TerminarRecepcion("SinEvidencia", null);
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		Mobile.cedis.om.B09_FormatoUnicoEntrega fue = new Mobile.cedis.om.B09_FormatoUnicoEntrega(testCase, appiumUtil, null);
		addStep("Dentro de la pantalla FUE se muestra el apartado de \"Movimiento Tienda\" donde señala en \"Devolución Normal\" el BOL que se seleccionó anteriormente.\r\n"
				+ "?Se muestra el BOL en Devolución normal correctamente");
		fue.validarDatoDevNormal();
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}


	@Override
	public String setTestFullName() {
		return "ATC_FT_044_BOLDeDanadosPreviamenteCreadoConCajasRecepcionadasEnRecepcionEnBOLDev";
	}

	@Override
	public String setTestDescription() {
		return "Validar que el BOL que se generó en una recepción pasada de un registro de artículos dañados al seleccionarlo "
				+ "se registre como BOL de Devolución Normal";
	}

	@Override
	public String setTestDesigner() {
		return "Jorge Alberto Lugo Martínez";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "*ASN/Pedido normal \r\n"
				+ "*BOL generado por recepción en cajas sin etiqueta/etiqueta dañada con artículos de envase dañados\r\n"
				+ "*Haber realizado flujo de recepción hasta pantalla \"Devoluciones a CEDIS\"";
	}

	@Override
	public void beforeTest() {
	}

}