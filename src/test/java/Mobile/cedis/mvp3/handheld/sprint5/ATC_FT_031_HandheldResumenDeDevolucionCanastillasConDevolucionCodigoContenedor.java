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
import Mobile.cedis.om.B07_ResumenDevolucion;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

public class ATC_FT_031_HandheldResumenDeDevolucionCanastillasConDevolucionCodigoContenedor extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_031_HandheldResumenDeDevolucionCanastillasConDevolucionCodigoContenedor_Test(HashMap<String, String> data) throws Exception {

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
		System.out.println("Entrando a Devolucion CEDIS\n");
		Thread.sleep(2000);
		DeseaComenzar.Cargando();//linea agregada por Juan
		DeseaComenzar.SeleccionarObjeto("Devolucion CEDIS");//linea agregada por Juan
		
		B05_DevolucionMercancia POM_Devolucion = new B05_DevolucionMercancia(testCase, appiumUtil, this);
		System.out.println("Seleccionando BOLs\n");
		Thread.sleep(2000);
		POM_Devolucion.SeleccionarBOLSinCheck("", "");
		Thread.sleep(1000);
		POM_Devolucion.AumentarCanastilla(1, "Azul");
		POM_Devolucion.AumentarCanastilla(2, "Amarilla");
		POM_Devolucion.AumentarCanastilla(3, "Verde");
		POM_Devolucion.AumentarCanastilla(4, "Naranja");
		POM_Devolucion.AumentarCanastilla(5, "Otro");
		Thread.sleep(1000);
		System.out.println("Ingresando contenedores\n");
		POM_Devolucion.AumentarContenedores(6, "Tapas");
		POM_Devolucion.AumentarContenedores(7, "Envases");
		Thread.sleep(1000);
		System.out.println("Ingresando evidencia en Devolucion de contenedores\n");
		Thread.sleep(1000);
		POM_Devolucion.TomarFotoDevolucion();
		Thread.sleep(1000);
		POM_Devolucion.IngresarCanastilla("123456");
		POM_Devolucion.TomarFotoCanastilla();
		
		System.out.println("Dando clic en boton Continuar\n");
		Thread.sleep(5000);
		POM_Devolucion.SeleccionarObjeto("Continuar");
		Thread.sleep(2000);

		System.out.println("Confirmando de Continuar\n");
		//POM_Devolucion.SeleccionarObjeto("Si");
		B07_ResumenDevolucion B07_ResumenDevolucion = new B07_ResumenDevolucion(testCase, appiumUtil, this);

		
		// Paso #1.- Posicionarse en la pantalla "Resumen de devolucion" 
		// Resultado esperado: Se muestra pantalla "Resumen devolucion""
		System.out.println("Paso #1: Posicionarse en la pantalla \"Resumen de devolucion\"\n");
		Thread.sleep(2000);
		addStep("Posicionarse en la pantalla \"Resumen de devolucion\"",
				"Resultado esperado: Se muestra pantalla \"Resumen devolucion\"");
		Thread.sleep(2000);
		B07_ResumenDevolucion.ValidacionPantallaResumenDevolucion();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #2.- Dentro de la pantalla se encuentra la seccion: Canastillas con devolucion. 
		// Resultado esperado: Se muestra la seccion: "Canastillas con devolucion"
		System.out.println("Paso #2: Dentro de la pantalla se encuentra la seccion: Canastillas con devolucion.\n");
		Thread.sleep(2000);
		addStep("Dentro de la pantalla se encuentra la seccion: Canastillas con devolucion.",
				"Resultado esperado: Se muestra la seccion: \"Canastillas con devolucion\"");
		Thread.sleep(2000);
		B07_ResumenDevolucion.BuscarSeccion("Sección Devolución");
		B07_ResumenDevolucion.ValidacionSeccionCanastillasConDevolucion();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #3.- Dentro de la pantalla se encuentra la tabla: Codigo contenedor 
		// Resultado esperado: Se muestra la tabla codigo contenedor  en donde:
		// *Filas: muestran cada 1 de las canastillas escritas o escaneadas.
		// *Total, se contabilizan las canastillas ingresadas en la parte inferior de la tabla.
		System.out.println("Paso #3: Dentro de la pantalla se encuentra la tabla: Codigo contenedor\n");
		Thread.sleep(2000);
		addStep("Dentro de la pantalla se encuentra la tabla: Codigo contenedor",
				"Resultado esperado: \"Se muestra la tabla codigo contenedor  en donde:\r\n"
				+ " *Filas: muestran cada 1 de las canastillas escritas o escaneadas.\r\n"
				+ "*Total, se contabilizan las canastillas ingresadas en la parte inferior de la tabla.\"");
		Thread.sleep(2000);
		B07_ResumenDevolucion.ValidacionTablaCanastillasConDevolucion();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		// Cerrar Apps
		System.out.println("Terminando caso y cerrando OaD...");
		// Runtime.getRuntime().exec("adb shell am force-stop \"" +
	}

// -------------------------------------------------------------------------------------------------- //


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
		return "MTC-FT-031 Handheld Resumen de devolucion-Canastillas con devolucion(codigo contenedor)";
	}

	@Override
	public String setTestDescription() {
		return "\"Validar que en el apartado que lleva por nombre canastillas con devolucion, se haya realizado correctamente la actualizacion de los siguientes datos: \r\n"
				+ "Tabla \"\"Codigo contenedor\"\"\r\n"
				+ "\r\n"
				+ "*Filas: Mostrar cada 1 de las canastillas escritas o escaneadas.\r\n"
				+ "*Contabilizar las canastillas ingresadas en la parte inferior de la tabla.\"";
	}

	@Override
	public String setPrerequisites() {
		return "\"1. App Oxxo al dia instalada en Handheld(version mas actual).\r\n"
				+ "2. Usuario y Contraseña de Aplicacion Oxxo al dia.\r\n"
				+ "3. Comunicacion entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexion entre ambos dispositivos).\r\n"
				+ "4. P.V con acceso a la Herramienta de compra.\r\n"
				+ "5. Realizar flujo de recepcion hasta llegar a la pantalla resumen de devolucion.\r\n"
				+ "6. Haber ingresado canastillas con devolucion en la pantalla devoluciones a cedis.\"";
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
