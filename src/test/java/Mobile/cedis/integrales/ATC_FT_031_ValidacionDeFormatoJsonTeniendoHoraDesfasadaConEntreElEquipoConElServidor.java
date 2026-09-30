package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * DESCRIPCIÓN:
 * Verificar que al concluir el proceso al tener la hora desfazada entre la handheld y el servidor el archivo Json mantenga la estructura correcta.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_031_ValidaciónDeFormatoJsonTeniendoHoraDesfasadaConEntreElEquipoConElServidor
 *
 * PRERREQUISITOS:
 * 1. Realizar el proceso completo de Recepción de Transferencia de una Tienda teniendo diferencia entre la hora del servidor y el dispositivo 
 * 
 *
 * @author 
 * @date 
 */



public class ATC_FT_031_ValidacionDeFormatoJsonTeniendoHoraDesfasadaConEntreElEquipoConElServidor extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_031_ValidacionDeFormatoJsonTeniendoHoraDesfasadaConEntreElEquipoConElServidor_test(
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
		
				
		/*Paso 1 Se ubica en la pantalla: "Recepción de Pedido Cedis" en el listado de Transferencias
		 * Resultado esperado: Se muestra correctamente la pantalla donde se visualiza el listado de transferencias disponibles para recepcionar
		 */		
		addStep("Acción realizada \r\n"
				+ "Resultado esperado: Se muestra ");		
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo		
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		//Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
									
										
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return " MTC-FT-031 Validación de formato Json teniendo hora desfasada con entre el equipo con el servidor";
	}

	@Override
	public String setTestDescription() {
		return " Verificar que al concluir el proceso al tener la hora desfazada entre la handheld y el servidor el archivo Json mantenga la estructura correcta.";
	}

	@Override
	public String setTestDesigner() {
		return " Vanessa Torres";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return " * Realizar el proceso completo de Recepción de Transferencia de una Tienda teniendo diferencia entre la hora del servidor y el dispositivo";
	}

	@Override
	public void beforeTest() {
	}

}