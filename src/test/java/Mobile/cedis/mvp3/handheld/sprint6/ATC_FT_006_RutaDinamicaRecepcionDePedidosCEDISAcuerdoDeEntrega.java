package Mobile.cedis.mvp3.handheld.sprint6;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_006_RutaDinamicaRecepcionDePedidosCEDISAcuerdoDeEntrega extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_006_RutaDinamicaRecepcionDePedidosCEDISAcuerdoDeEntrega_test(
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
		//transferencia.BuscarPrineraTransferencia(true, data.get("ip"));//Se selecciona la primera transferencia sin tomar evidencia
		transferencia.BuscarTransferencia(true,data.get("numTransferencia"));
				
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		DeseaComenzar.Cargando();
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();
		pantallatransferencia.Tomar3Fotos();
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));//Se captura los contenedores que se encuentran en el excel
		pantallatransferencia.TerminarHastaResumen();
		
		Mobile.cedis.om.B03_ResumenPedidosCEDIS resumen = new Mobile.cedis.om.B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		resumen.FirmaAcuerdoSinEvidencia();
		
		Mobile.cedis.om.B04_AcuerdoEntrega acuerdo = new Mobile.cedis.om.B04_AcuerdoEntrega(testCase, appiumUtil, this);		
		/*Paso 1 Ubicarse en la pantalla: Acuerdo de entrega.
		 * Resultado esperado: Se despliega la informacion general de tienda:
		 * *Logo
		 * *Fecha DD-(Nombre del mes)-AAAA HH:MM
		 * *Ruta
		 * *Ruta Dinamica
		 * *CR Tienda
		 * *Nombre de la tienda
		 * *Lider Tienda
		 * *Transferencia
		 */		
		addStep("Ubicarse en la pantalla: Acuerdo de entrega.\r\n"
				+ "Resultado esperado: Se despliega la informacion general de tienda:\r\n"
				+ "*Logo\r\n"
				+ "*Fecha DD-(Nombre del mes)-AAAA HH:MM\r\n"
				+ "*Ruta\r\n"
				+ "*Ruta Dinamica\r\n"
				+ "*CR Tienda\r\n"
				+ "*Nombre de la tienda\r\n"
				+ "*Lider Tienda\r\n"
				+ "*Transferencia");
		Thread.sleep(1000);	
		acuerdo.ValidacionEncabezado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");					
								
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override
	public String setTestFullName() {
		return "ATC_FT_006_RutaDinamicaRecepcionDePedidosCEDISAcuerdoDeEntrega";
	}

	@Override
	public String setTestDescription() {
		return "Validar que en la pantalla de acuerdo de entrega se visualice el valor de la ruta dinamica.";
	}

	@Override
	public String setTestDesigner() {
		return "Juan Ibarra";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "1. App Oxxo al dia instalada en Handheld.\r\n"
				+ "2. Usuario y Contraseña de Aplicacion Oxxo al dia.\r\n"
				+ "3. Comunicacion entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexion entre ambos dispositivos).\r\n"
				+ "4. P.V con acceso a la Herramienta de compra.\r\n"
				+ "5. Contar con transferencias en el P.V.";
	}

	@Override
	public void beforeTest() {
	}

}