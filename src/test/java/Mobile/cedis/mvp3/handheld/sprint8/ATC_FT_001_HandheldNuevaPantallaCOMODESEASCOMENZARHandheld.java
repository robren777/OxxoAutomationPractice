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

public class ATC_FT_001_HandheldNuevaPantallaCOMODESEASCOMENZARHandheld extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_001_HandheldNuevaPantallaCOMODESEASCOMENZARHandheld_test(
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
		
		/*Paso 1.-Se ubica en la pantalla: "Recepción de pedido Cedis"
		 * Resultado esperado: Se despliega correctamente la pantalla: "Recepción de pedido Cedis", donde se visualiza el listado de transferencias.
		 */		
		addStep("Se ubica en la pantalla: \"Recepción de pedido Cedis\"\r\n"
				+ "Resultado esperado: Se despliega correctamente la pantalla: \"Recepción de pedido Cedis\", donde se visualiza el listado de transferencias.");
		Thread.sleep(1000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia transferencia = new Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);				
		transferencia.BuscarTransferencia(true,data.get("numTransferencia"));				
		
		/*Paso 2 -Se da clic en una de estas transferencias
		 * Resultado esperado:  Se despliega correctamente la nueva pantalla de recepción con el titulo que lleva por nombre ¿CÓMO DESEAS COMENZAR?.
		 */		
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);
		DeseaComenzar.Cargando();	
		addStep("Se da clic en una de estas transferencias\r\n"
				+ "Resultado esperado:Se despliega correctamente la nueva pantalla de recepción con el titulo que lleva por nombre ¿CÓMO DESEAS COMENZAR?");		
		DeseaComenzar.SeleccionarObjeto("Titulo Pantalla");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "ATC_FT_001_HandheldNuevaPantallaCOMODESEASCOMENZARHandheld";
	}

	@Override
	public String setTestDescription() {
		return "Validar que se haya agregado una nueva pantalla en el apartado recepción de pedido cedis la cual funciona para decidir por cual flujo iniciar la recepción de la transferencia seleccionada.";
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
		return "1.-*Contar con la versión de la apk más actual\r\n"
				+ "2.-Haber cargado transferencias en el P.V\r\n"
				+ "3.-Contar con dispositivo HandHeld\r\n"
				+ "4.-Tener asignado un P.V para tener conexión entre dispositivo HandHeld y P.V\r\n"
				+ "5.-Tener activados las credenciales del login de ruta\r\n"
				+ "-Usuario 123\r\n"
				+ "-Contraseña 2\r\n"
				+ "6.-Ingresar a la aplicación OXXO al día hasta la parte donde se muestra el listado de las transferencias.";
	}

	@Override
	public void beforeTest() {
	}

}