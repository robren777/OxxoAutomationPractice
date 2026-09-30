package Mobile.cedis.mvp3.handheld.sprint5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.A01_Login_Punto_De_Venta;
import Mobile.cedis.om.A02_Login_Lider;
import Mobile.cedis.om.A03_Menus_y_submenus;
import Mobile.cedis.om.A04_MenuHerramientas;
import Mobile.cedis.om.A05_LoginAreaDistribucion;
import Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia;
import Mobile.cedis.om.B05_DevolucionMercancia;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_027_HandheldContinuarBOLSinEscanearCanastillasConDevolucion extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_027_HandheldContinuarBOLSinEscanearCanastillasConDevolucion_Test(HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		caps.setCapability("deviceName", data.get("deviceName"));
		caps.setCapability("platformVersion", data.get("platformVersion"));
		caps.setCapability("platformName", data.get("platformName"));
		caps.setCapability("appPackage", data.get("appPackage"));
		caps.setCapability("appActivity", data.get("appActivity"));

		String url = "http://127.0.0.1:4723/";

		// Cerrar Apps
		Thread.sleep(1000);
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage") + "\"");

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, caps);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}

		Thread.sleep(1000);

		/***********************************************************************************************/

		// Apuntado de Punto de Venta
		A01_Login_Punto_De_Venta LoginPDVOaD = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		System.out.println("\nLogeando Punto de Venta\n");
		Thread.sleep(2000);
		LoginPDVOaD.loginPV("10.75.6.85");

		// Login usuario Lider
		A02_Login_Lider LoginLiderOaD = new A02_Login_Lider(testCase, appiumUtil, this);
		System.out.println("Logeando Lider\n");
		Thread.sleep(2000);
		LoginLiderOaD.login(data.get("userLogin"), PasswordUtil.decryptPassword(data.get("passwordLogin")));

		// Menu principal hasta Herramientas de COMETA
		A03_Menus_y_submenus MenuOaD = new A03_Menus_y_submenus(testCase, appiumUtil, this);
		System.out.println("Dando clic en Excelencia Operativa -> HC y COMETA\n");
		Thread.sleep(2000);
		MenuOaD.logeoInicioHerramientas();

		// En el menu de herramientas doy clic en la verificacion de cedis
		A04_MenuHerramientas ModulosOaD = new A04_MenuHerramientas(testCase, appiumUtil, this);
		System.out.println("Dando clic en Recepcion de Pedidos CEDIS\n");
		Thread.sleep(2000);
		ModulosOaD.clickBtnRecepcionPedidosCEDIS();

		// En el menu de herramientas doy clic en la verificacion de cedis
		A05_LoginAreaDistribucion LoginAreaDistribucion = new A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		System.out.println("Logeando Area de Distribucion CEDIS\n");
		Thread.sleep(2000);
		LoginAreaDistribucion.LoginAreaDistribucion();

		B01_RecepcionPedidosSeleccionarTransferencia POM_ListadoDeRecepcion = new B01_RecepcionPedidosSeleccionarTransferencia(
		testCase, appiumUtil, this);
		System.out.println("Entrando a la primera transferencia por Recepcionar de CEDIS\n");
		Thread.sleep(2000);
		POM_ListadoDeRecepcion.SeleccionarPrimeraTransferencia(true);
		
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);//linea agregada por Juan 
		DeseaComenzar.Cargando();//linea agregada por Juan
		DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");//linea agregada por Juan
		
		// Paso #1.- Estar en la pantalla "Devoluciones a CEDIS"
		// Resultado esperado: Se muestra la pantalla Devoluciones a CEDIS
		System.out.println("\nPaso #1: Estar en la pantalla \"Devoluciones a CEDIS\"");
		Thread.sleep(2000);
		addStep("Estar en la pantalla \"Devoluciones a CEDIS\"",
				"Resultado esperado: Se muestra la pantalla Devoluciones a CEDIS");
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		B05_DevolucionMercancia POM_Devolucion = new B05_DevolucionMercancia(testCase, appiumUtil, this);
		System.out.println("\nSeleccionando BOL");
		Thread.sleep(1000);
		POM_Devolucion.SeleccionarPrimerBol("", "");
		Thread.sleep(1000);
//		System.out.println("Ingresando canastilla en Devolucion de contenedores\n");
//		Thread.sleep(1000);
		POM_Devolucion.AumentarCanastilla(1, "Azul");
		System.out.println("\nIngresando evidencia en Devolucion de contenedores");
		Thread.sleep(1000);
		POM_Devolucion.TomarFotoDevolucion();
		
		// Paso #2.- Seleccionar un BOL y no escanear canastilla con devolucion, despues ir al boton de CONTINUAR y seleccionarlo
		// Resultado esperado: Se muestra mensaje: "Se seleccionaron devoluciones pero no se escanearon canastillas, ¿deseas continuar? No/Si"
		System.out.println("\nPaso #2: Seleccionar un BOL y no escanear canastilla con devolucion, despues ir al boton de CONTINUAR y seleccionarlo");
		Thread.sleep(2000);
		addStep("Seleccionar un BOL y no escanear canastilla con devolucion, despues ir al boton de CONTINUAR y seleccionarlo",
				"Resultado esperado: Se muestra mensaje: \"Se seleccionaron devoluciones pero no se escanearon canastillas, ¿deseas continuar? No/Si\"");
		POM_Devolucion.SeleccionarObjeto("Continuar");
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #3.- Seleccionar NO
		// Resultado esperado: El sistema automaticamente te ubica en la parte "Canastillas con devolucion"
		System.out.println("\nPaso #3: Seleccionar NO");
		Thread.sleep(2000);
		addStep("Seleccionar NO",
				"Resultado esperado: El sistema automaticamente te ubica en la parte \"Canastillas con devolucion\"");
		POM_Devolucion.SeleccionarObjeto("No");
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #4.- Nuevamente selecciona el boton CONTINUAR sin haber escaneado nada 
		// Resultado esperado: Se muestra mensaje: "Se seleccionaron devoluciones pero no se escanearon canastillas, ¿deseas continuar? No/Si""
		System.out.println("\nPaso #4: Nuevamente selecciona el boton CONTINUAR sin haber escaneado nada ");
		Thread.sleep(2000);
		addStep("Nuevamente selecciona el boton CONTINUAR sin haber escaneado nada ",
				"Resultado esperado: Se muestra mensaje: \"Se seleccionaron devoluciones pero no se escanearon canastillas, ¿deseas continuar? No/Si\"");
		POM_Devolucion.SeleccionarObjeto("Continuar");
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #5.- Seleccionar SI 
		// Resultado esperado: Se muestra pantalla "Resumen devolucion""
		System.out.println("\nPaso #5: Seleccionar SI");
		Thread.sleep(2000);
		addStep("Seleccionar SI",
				"Resultado esperado: Se muestra pantalla \"Resumen devolucion\"");
		POM_Devolucion.SeleccionarObjeto("Si");
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// Cerrar Apps
		System.out.println("Terminando caso y cerrando OaD...");
		// Runtime.getRuntime().exec("adb shell am force-stop \"" +
	}

// --------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------
	/**
	 * CEDIS MVP #3:
	 * ATC_FT_014_HandHeld_ACREDITACION_Modificar_Danados_y_Caducos_Agregar_un_articulo_danado_con_el_maximo_ingresado_en_Articulos_Caducos.
	 * Desc: 1 - Validar que no permita avanzar a la verificación si no encuentra la
	 * Fuente de abasto en XPOS. 2 - Validar que permita leer el mismo QR por
	 * segunda ocasión, ya que no se genera un a OC
	 * 
	 * @autor Jesús Marcelo Gutiérrez Bravo - QA GTIM
	 * @date 20/08/2024
	 */

//Datos para el caso de prueba
	@AfterMethod
	public void baseAfterMethod(ITestResult result) {
		try {
			// Lógica para manejar recursos y liberar al final de cada prueba.
			// Por ejemplo, cerrar conexiones a BD o archivos.
			// Asegúrate de que todo esté liberado correctamente.

			if (result.getStatus() == ITestResult.FAILURE) {
				// Lógica adicional si la prueba falló
				System.out.println("La prueba falló: " + result.getName());
			}
		} catch (Exception e) {
			// Captura cualquier excepción que pueda ocurrir en el proceso
			System.err.println("Ocurrió un error en @AfterMethod: " + e.getMessage());
		} finally {
			// Lógica para asegurarse de que los recursos se liberen correctamente
		}
	}

	@BeforeTest
	public void beforeTest() {
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-027 Handheld Continuar: BOL sin escanear canastillas con devolucion";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al darle continuar sin haber escaneado canastillas con devolucion pero si haber seleccionado un BOL el sistema arroje un mensaje";
	}

	@Override
	public String setPrerequisites() {
		return "*Haber seleccionado un BOL pero no haber escaneado una canastilla";
	}

	@Override
	public String setTestDesigner() {
		return "Jesus Marcelo Gutierrez Bravo";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
