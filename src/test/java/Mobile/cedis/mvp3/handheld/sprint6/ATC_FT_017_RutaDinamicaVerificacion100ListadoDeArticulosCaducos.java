package Mobile.cedis.mvp3.handheld.sprint6;

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
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
import Mobile.cedis.om.C04_Acreditacion_de_Caducos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_017_RutaDinamicaVerificacion100ListadoDeArticulosCaducos extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_017_RutaDinamicaVerificacion100ListadoDeArticulosCaducos_test(
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
		System.out.println("Logueando al Punto de Venta");
		login.loginPV(data.get("ip"));
	
		// Ejecuta el logeo del lider
		Mobile.cedis.om.A02_Login_Lider loginLider = new Mobile.cedis.om.A02_Login_Lider(testCase, appiumUtil, this);
		// Obtiene valor del DataProvaider
		System.out.println("Loguendo la cuenta de Lider");
		loginLider.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));
	
		// Ejecuta los pasos para llegar al menu de Herramientas de CEDIS
		Thread.sleep(3000);
		Mobile.cedis.om.A03_Menus_y_submenus herramienta = new Mobile.cedis.om.A03_Menus_y_submenus(testCase, appiumUtil, this);
		System.out.println("Entrando al módulo de Herramientas");
		herramienta.logeoInicioHerramientas();
	
		Mobile.cedis.om.A04_MenuHerramientas menuCedis = new Mobile.cedis.om.A04_MenuHerramientas(testCase, appiumUtil, this);
		System.out.println("Entrando al módulo de Verificación de Mercancía CEDIS");
		menuCedis.clickBtnVerificacionMercanciaCEDIS();// Selecciona la opcion de Recepción de Pedidos CEDIS
		
		Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar listadoVerif = new Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar(testCase, appiumUtil, this);
		System.out.println("Entrando a una transferencia a Verificar");
		Thread.sleep(2000);
		listadoVerif.busquedaPrimeraTransferenciaPorVerificar_Marcelo();
		
		C02_Verificacion_De_Pedido_Y_Permisos procesoVerif = new C02_Verificacion_De_Pedido_Y_Permisos(testCase, appiumUtil, this);
		System.out.println("Entrando a Verificacion");
		procesoVerif.esperaDinamicaLoaderDeVerificacion(90000);
		procesoVerif.darClickBotonBultos();
		Thread.sleep(2000);
		procesoVerif.hacerClickEnElPrimerBultoDelPadre();
		Thread.sleep(2000);
		procesoVerif.tomarFoto();
		Thread.sleep(2000);
		procesoVerif.verificarArticulo();
		Thread.sleep(2000);
		procesoVerif.darClickBotonBultos();
		Thread.sleep(2000);
		procesoVerif.hacerClickEnElSegundoBultoDelPadre();
		Thread.sleep(2000);
		procesoVerif.tomarFoto();
		Thread.sleep(2000);
		procesoVerif.verificarArticulo();
		Thread.sleep(2000);
		procesoVerif.continuarCaducosDirecto();
		Thread.sleep(2000);
		
		C04_Acreditacion_de_Caducos caducosVerif = new C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
		System.out.println("Entrando a la pantalla de Artículos Caducos.");
		Thread.sleep(3000);
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
		addStep("Ubicarse en la pantalla: \"Listado de Articulos Caducos\""
				+ "Resultado Esperado: ?Se despliega la informacion general de tienda:\r\n"
				+ "*Logo\r\n"
				+ "*Fecha DD-(Nombre del mes)-AAAA HH:MM\r\n"
				+ "*Ruta\r\n"
				+ "*Ruta Dinamica\r\n"
				+ "*CR Tienda\r\n"
				+ "*Nombre de la tienda\r\n"
				+ "*Transferencia");
		Thread.sleep(2000);
		caducosVerif.validarEncabezadoRutasCaducos();
		Thread.sleep(3000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}



	@Override
	public String setTestFullName() {
		return "ATC_FT_017_RutaDinamicaVerificacion100ListadoDeArticulosCaducos";
	}

	@Override
	public String setTestDescription() {
		return "Validar que en la pantalla de Listado de Articulos Caducos ya se visualice el valor de la ruta dinamica.";
	}

	@Override
	public String setTestDesigner() {
		return "Jorge Alberto Lugo Martinez";
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
				+ "5. Contar con transferencias en el P.V\r\n"
				+ "6. Haber realizado el flujo completo de Recepcion para un pedido de forma exitosa\r\n"
				+ "7. Haber realizado la verificacion de mercancia, ingresando articulos para poder marcarlos como dañados/caducos.";
	}

	@Override
	public void beforeTest() {
	}

}