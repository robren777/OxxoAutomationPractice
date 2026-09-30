package Mobile.cedis.integrales;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
import Mobile.cedis.om.C04_Acreditacion_de_Caducos;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

/**
 * DESCRIPCIÓN:
 * Validar que exista un campo de texto como buscador para el ingreso de SKU por medio de teclado digital
 * 
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_006_HandheldACREDITACIONBuscadorParaIngresoDeSKUDeUnArticuloCADUCOEnTecladoDigital
 * 
 * PRERREQUISITOS:
 *1.Haber recepcionado un pedido con cajas/canastillas
 *2.Haber verificado ese mismo pedido
 *
 *@author Vanessa Torres
 *@date   05/08/2024
 */


public class ATC_FT_006_HandheldACREDITACIONBuscadorParaIngresoDeSKUDeUnArticuloCADUCOEnTecladoDigital
		extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_006_HandheldACREDITACIONBuscadorParaIngresoDeSKUDeUnArticuloCADUCOEnTecladoDigital_test(
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
			assertTrue(false, "Hubo un problema al acceder a la aplicaci�n. \n" + e.getMessage());
			e.printStackTrace();
		}

		// Abre la aplicación
		Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

		// ---------------------------------------- INICIO ------------------------------------------------ //

		// En el login de punto de venta ingreso la ip y el puerto
		A01_Login_Punto_De_Venta LoginPDVOaD = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		System.out.println("Logeando Punto de Venta");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		LoginPDVOaD.loginPV(data.get("ip"));

		// En el login de lider ingreso el usuario y contraseña
		A02_Login_Lider LoginLiderOaD = new A02_Login_Lider(testCase, appiumUtil, this);
		System.out.println("Logeando Lider");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		LoginLiderOaD.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		// En la pantalla principal doy clic en el boton de excelencia operativa, luego
		// en la hc y al final e cometa
		A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
		System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		MenuOaD.logeoInicioHerramientas();

		// En el menu de herramientas doy clic en la verificacion de cedis
		A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
		System.out.println("Dando clic en Verificacion de CEDIS");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		ModulosOaD.clickBtnVerificacionMercanciaCEDIS();

		// Entro al listado de pedidos por verificar y busco la transferencia que quiero
		// a traves del buscador para entrar en esa
		C01_Listado_de_transferencias_a_verificar ListadoVerificacion = new C01_Listado_de_transferencias_a_verificar(
				testCase, appiumUtil, this);
		System.out.println("Dando clic en Transferencia a Verificar");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		ListadoVerificacion.busquedaPrimeraTransferenciaPorVerificar_Marcelo();

		// Entro a verificacion, espero la barra, doy clic en bultos, clic en el
		// primero, tomo evidencia y verifico todos los articulos cuadrando las
		// cantidades,
		// Lo mismo para el segundo bulto y doy clic en continuar, para pasar directo a
		// Caducos
		C02_Verificacion_De_Pedido_Y_Permisos verificacion = new C02_Verificacion_De_Pedido_Y_Permisos(testCase,
				appiumUtil, this);
		System.out.println("Entrando a Verificacion");
		verificacion.esperaDinamicaLoaderDeVerificacion(90000);
		verificacion.darClickBotonBultos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.hacerClickEnElPrimerBultoDelPadre();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.tomarFoto();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.verificarArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.darClickBotonBultos();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.hacerClickEnElSegundoBultoDelPadre();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.tomarFoto();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.verificarArticulo();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		verificacion.continuarCaducosDirecto();
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

//				------------------------------------- //FALTA VERIFICAR PEDIDO EL METODO ---------------------------------------------------

		C04_Acreditacion_de_Caducos caducos = new C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);

		// =================================== Inicio de Caso =====================================//

		/*
		 * Paso 1 - Estar en la pantalla de "Caducos" Nota: Previo hay que realizar la
		 * verificación del pedido
		 */
		addStep("Estar en la pantalla de Caducos");

		/*
		 * Paso 2 - ?Se muestra pantalla de caducos correctamente
		 */
		addStep("?Se muestra pantalla de caducos correctamente");
		caducos.pantallaCaducos();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 3 - Dentro de la pantalla se muestra un botón amarillo
		 * "INGRESAR CÓDIGO SKU"
		 */
		addStep("Dentro de la pantalla se muestra un botón amarillo INGRESAR CÓDIGO SKU");
		caducos.siexisteBoton();
		/*
		 * Paso 4 - ?Se muestra botón correctamente
		 */
		addStep("?Se muestra botón correctamente");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		/*
		 * Paso 5 - Selecciona el botón
		 */
		addStep("Selecciona el botón");
		caducos.presionarbotonIngresarCodigoSKU();

		/*
		 * Paso 6 - ?Aparece un campo de texto "Escribe el SKU o el nombre del artículo"
		 */
		addStep("?Aparece un campo de texto Escribe el SKU o el nombre del artículo");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	}

	

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_HandheldACREDITACIONBuscadorParaIngresoDeSKUDeUnArticuloCADUCOEnTeclado";
	}

	@Override
	public String setTestDescription() {
		return "Validar que exista un campo de texto como buscador para el ingreso de SKU por medio de teclado digital.";
	}

	@Override
	public String setTestDesigner() {
		return "Vanessa Torres";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Haber recepcionado un pedido con cajas/canastillas" + "Haber verificado ese mismo pedido";
	}

	@Override
	public void beforeTest() {
	}

}