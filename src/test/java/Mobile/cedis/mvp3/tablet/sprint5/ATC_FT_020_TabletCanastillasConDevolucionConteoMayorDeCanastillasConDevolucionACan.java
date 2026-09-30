package Mobile.cedis.mvp3.tablet.sprint5;

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
import Mobile.cedis.om.*;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_020_TabletCanastillasConDevolucionConteoMayorDeCanastillasConDevolucionACan extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_020_TabletCanastillasConDevolucionConteoMayorDeCanastillasConDevolucionACan_test(
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
		cap.setCapability("canastilla1", data.get("canastilla1"));
		cap.setCapability("canastilla2", data.get("canastilla2"));
		cap.setCapability("canastilla3", data.get("canastilla3"));

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
		//Mobile.cedis.om.Tablet_A03_Menus_y_submenus herramienta = new Mobile.cedis.om.Tablet_A03_Menus_y_submenus(testCase, appiumUtil, this);
		Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		herramienta.logeoInicioHerramientas();

		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		menuCedis.clickBtnRecepcionPedidosCEDIS();// Selecciona la opcion de Recepción de Pedidos CCEDIS
		
		Mobile.cedis.om.A05_LoginAreaDistribucion loginRuta = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		loginRuta.LoginAreaDistribucion();//Se logea en Area de Distribución sin tomar evidencia
		
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		transferencia.BuscarTransferencia(true, data.get("numTransferencia"));//Se selecciona la primera transferencia sin tomar evidencia
		
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		DeseaComenzar.Cargando();
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();
		pantallatransferencia.Tomar3Fotos();
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));//Se captura los contenedores que se encuentran en el excel
		Thread.sleep(3000);
		pantallatransferencia.TerminarHastaResumen();
		
		Mobile.cedis.om.B03_ResumenPedidosCEDIS resumen = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		resumen.FirmaAcuerdoSinEvidencia();
		
		Mobile.cedis.om.B04_AcuerdoEntrega acuerdo = new Mobile.cedis.om.B04_AcuerdoEntrega(testCase, appiumUtil, this);
		acuerdo.FirmaAreaDistribucion("SinEvidencia", null);
		acuerdo.FirmaPersonalTienda("SinEvidencia", null);
		acuerdo.TerminarRecepcion("SinEvidencia", null);
		
		
		Mobile.cedis.om.B05_DevolucionMercancia devolucion = new Mobile.cedis.om.B05_DevolucionMercancia(testCase, appiumUtil, this);
		devolucion.SeleccionarPrimerBol("SinEvidencia", null);
	
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		
		Thread.sleep(2000);
		
		/*Paso 1: Estar en la pantalla "Devoluciones a CEDIS"
		Resultado Esperado: Se muestra la pantalla Devoluciones a CEDIS.*/
		addStep("Estar en la pantalla Devoluciones a CEDIS.\r\n"
				+ "Resultado Esperado: ?Se muestra la pantalla Devoluciones a CEDIS.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		
		/*Paso 2: Ingresa canastillas 2 canastillas a devolver (cualquier color)"
		Resultado Esperado: ?Se permite agregar canastillas a devolver*/
		addStep("Ingresa canastillas 2 canastillas a devolver (cualquier color)\r\n"
				+ "Resultado Esperado: ?Se permite agregar canastillas a devolver");
		devolucion.AumentarCanastilla(1, "Azul");
		devolucion.AumentarCanastilla(1, "Amarilla");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3: Captura la evidencia de las canastillas a devolver"
		Resultado Esperado: ?Se permite tomar captura*/
		addStep("Captura la evidencia de las canastillas a devolver\r\n"
				+ "Resultado Esperado: ?Se permite tomar captura");
		devolucion.TomarFotoDevolucion();
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 4: Ingresa 2 canastillas con devolucion con su respectiva evidencia"
		Resultado Esperado: ?Se permite agregar canastillas con devolucion*/
		Thread.sleep(3000);
		devolucion.IngresarCanastilla(data.get("canastilla1"));
		Thread.sleep(3000);
		devolucion.VariasFotoCanastillaTablet("tomaEvidencia", "Ingresa 2 canastillas con devolucion con su respectiva evidencia\r\n"
				+ "Resultado Esperado: ?Se permite agregar canastillas con devolucion" );
		Thread.sleep(3000);
		devolucion.IngresarCanastilla(data.get("canastilla2"));
		Thread.sleep(3000);
		devolucion.VariasFotoCanastillaTablet("tomaEvidencia", " " );
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 5: Intenta agregar una 3era canastilla con devolucion manualmente"
		Resultado Esperado: ?Se muestra mensaje. "Es necesario indicar la cantidad de canastillas 
		con devolucion en el color de canastillas correspondiente."*/
		Thread.sleep(3000);
		devolucion.IngresarCanastilla(data.get("canastilla3"));
		Thread.sleep(3000);
		devolucion.MensajeIndicaCanastilla("Intenta agregar una 3era canastilla con devolucion manualmente\"\r\n"
				+ "Resultado Esperado: ?Se muestra mensaje. \"Es necesario indicar la cantidad de "
				+ "canastillas con devolucion en el color de canastillas correspondiente.");
		Thread.sleep(3000);
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}


	@Override
	public String setTestFullName() {
		return "ATC_FT_020_TabletCanastillasConDevolucionConteoMayorDeCanastillasConDevolucionACan";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al intentar registrar una canastilla con devolucion cuando la cantidad de canastilla de devolucion es menor salga un mensaje de aviso";
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
		return "* Haber realizado la recepcion de una transferencia que contenga al menos bultos tipo canastilla\r\n"
				+ "* Haber ingresado al menos 2 canastillas para devolucion";
	}

	@Override
	public void beforeTest() {
	}

}