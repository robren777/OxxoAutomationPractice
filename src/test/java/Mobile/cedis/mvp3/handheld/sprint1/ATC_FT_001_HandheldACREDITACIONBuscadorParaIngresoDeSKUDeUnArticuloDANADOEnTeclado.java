package Mobile.cedis.mvp3.handheld.sprint1;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
import Mobile.cedis.om.C03_Acreditacion_de_Danados;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;



public class ATC_FT_001_HandheldACREDITACIONBuscadorParaIngresoDeSKUDeUnArticuloDANADOEnTeclado extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_001_HandheldACREDITACIONBuscadorParaIngresoDeSKUDeUnArticuloDANADOEnTeclado_test(HashMap<String, String> data) throws Exception {
		
		/******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
		DesiredCapabilities caps = new DesiredCapabilities();
		
		caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		caps.setCapability("deviceName", data.get("deviceName"));
		caps.setCapability("platformVersion", data.get("platformVersion"));
		caps.setCapability("platformName", data.get("platformName"));
		caps.setCapability("appPackage", data.get("appPackage"));
		caps.setCapability("appActivity", data.get("appActivity"));
		//caps.setCapability("appium:appPackage", data.get("appPackageP"));
		//caps.setCapability("appium:appActivity", data.get("appActivityP"));
		caps.setCapability("ip", data.get("ip"));
		caps.setCapability("userLogin", data.get("userLogin"));
		caps.setCapability("passwordLogin", data.get("passwordLogin"));
		caps.setCapability("numTransferencia", data.get("numTransferencia"));
		//caps.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
		
		String url = "http://127.0.0.1:4723/";
		
		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");

		//================================ Abrir aplicativo =====================================//
		
		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, caps);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
		
		
		//Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));
		
		//---------------------------------------- INICIO ------------------------------------------------

		//En el login de punto de venta ingreso la ip y el puerto
		A01_Login_Punto_De_Venta LoginPDVOaD = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		System.out.println("Logeando Punto de Venta");
		Thread.sleep(2000);
		LoginPDVOaD.loginPV("10.75.6.171");

		//En el login de lider ingreso el usuario y contraseña
		A02_Login_Lider LoginLiderOaD = new A02_Login_Lider(testCase, appiumUtil, this);
		System.out.println("Logeando Lider");
		Thread.sleep(2000);
		LoginLiderOaD.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		//En la pantalla principal doy clic en el boton de excelencia operativa, luego en la hc y al final e cometa
		A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
		System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA");
		Thread.sleep(2000);
		MenuOaD.logeoInicioHerramientas();

		//En el menu de herramientas doy clic en la verificacion de cedis
		A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
		System.out.println("Dando clic en Verificacion de CEDIS");
		Thread.sleep(2000);
		ModulosOaD.clickBtnVerificacionMercanciaCEDIS();

		//Entro al listado de pedidos por verificar y busco la transferencia que quiero a traves del buscador para entrar en esa
		C01_Listado_de_transferencias_a_verificar ListadoVerificacion = new C01_Listado_de_transferencias_a_verificar(testCase, appiumUtil, this);
		System.out.println("Dando clic en Transferencia a Verificar");
		Thread.sleep(2000);
		ListadoVerificacion.busquedaPrimeraTransferenciaPorVerificar_Marcelo();

		//Entro a verificacion, espero la barra, doy clic en bultos, clic en el primero, tomo evidencia y verifico todos los articulos cuadrando las cantidades,
		//Lo mismo para el segundo bulto y doy clic en continuar, para pasar directo a Caducos
		C02_Verificacion_De_Pedido_Y_Permisos verificacion = new C02_Verificacion_De_Pedido_Y_Permisos(testCase, appiumUtil, this);
		System.out.println("Entrando a Verificacion");
		verificacion.esperaDinamicaLoaderDeVerificacion(90000);
		verificacion.darClickBotonBultos();
		Thread.sleep(2000);
		verificacion.hacerClickEnElPrimerBultoDelPadre();
		Thread.sleep(2000);
		verificacion.tomarFoto();
		Thread.sleep(2000);
		verificacion.verificarArticulo();
		Thread.sleep(2000);
		verificacion.darClickBotonBultos();
		Thread.sleep(2000);
		verificacion.hacerClickEnElSegundoBultoDelPadre();
		Thread.sleep(2000);
		verificacion.tomarFoto();
		Thread.sleep(2000);
		verificacion.verificarArticulo();
		Thread.sleep(2000);
		verificacion.continuarDanadosHH();
		Thread.sleep(2000);
		
//		------------------------------------- //FALTA VERIFICAR PEDIDO EL METODO ---------------------------------------------------
		
		C03_Acreditacion_de_Danados danados = new C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
		
		//=================================== Conexión BD =========================================//
		//Connection conn = DriverManager.getConnection(GlobalVariables.url,GlobalVariables.userc, GlobalVariables.password);
		//=================================== Inicio de Caso =====================================//
		/*
		 *  Paso 1 - Estar en la pantalla de "Dañados" 
			Nota: Previo hay que realizar la verificación del pedido 
		 */
		addStep("Estar en la pantalla de Dañados");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/*
		 *  Paso 2 - ?Se muestra pantalla de dañados correctamente 
		 */
		addStep("?Se muestra pantalla de dañados correctamente");
		danados.pantallaDanados();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/*
		 *  Paso 3 - Dentro de la pantalla se da clic en el boton: "Ingresar codigo SKU" 
		 */
		addStep("Dentro de la pantalla se da clic en el boton: Ingresar codigo SKU");
		/*
		 *  Paso 4 - ?Se muestra botón correctamente
		 */
		addStep("?Se muestra botón correctamente");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/*
		 *  Paso 5 - Selecciona el botón 
		 */
		addStep("Selecciona el botón ");
		danados.presionarbotonIngresarCodigoSKU();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		/*
		 *  Paso 6 - ?Aparece un campo de texto "Escribe el SKU o el nombre del artículo" 
		 */
		addStep("?Aparece un campo de texto \"Escribe el SKU o el nombre del artículo\" ");
		danados.siexisteTXT();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		Thread.sleep(5000);
		// Cerrar Apps
		Runtime.getRuntime().exec("adb shell pm clear" + data.get("appPackage"));
	}
	
		/*
		 * Cedis MVP3 Sprint 1: ATC_FT_001_HandheldACREDITACIONBuscadorParaIngresoDeSKUDeUnArticuloDANADOEnTeclado
		 * Descripción:Validar que exista un campo de texto como buscador para el ingreso de SKU por medio de teclado digital
		 *
		 * @autor Vanessa Torres
		 * @date 15/08/2024
		 */ 
		 	
		@Override
		public String setTestFullName() {
			return "ATC_FT_001_HandheldACREDITACIONBuscadorParaIngresoDeSKUDeUnArticuloDANADOEnTeclado";
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
			return "Haber recepcionado un pedido con cajas/canastillas"
					+ "Haber verificado ese mismo pedido";
		}
		
		@BeforeTest
		public void beforeTest() {
		}
		
	}