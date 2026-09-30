package Mobile.cedis.integrales;

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

public class ATC_FT_118_HandheldResumenDeDevolucionBolSDeDevolucionBolSADevolver extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_118_HandheldResumenDeDevolucionBolSDeDevolucionBolSADevolver_test(
			HashMap<String, String> data) throws Exception {

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
		LoginPDVOaD.loginPV(data.get("ip"));

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
		
		System.out.println("Dando clic en boton Continuar\n");
		Thread.sleep(5000);
		POM_Devolucion.SeleccionarObjeto("Continuar");
		Thread.sleep(2000);

		System.out.println("Confirmando de Continuar\n");
		POM_Devolucion.SeleccionarObjeto("Si");
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
		
		// Paso #2.- Dentro de la pantalla se encuentra la seccion: Bol´s de devolucion.. 
		// Resultado esperado: Se muestra la seccion Bol´s de devolucion
		System.out.println("Paso #2: Dentro de la pantalla se encuentra la seccion: Bol´s de devolucion.\n");
		Thread.sleep(2000);
		addStep("Dentro de la pantalla se encuentra la seccion: Bol´s de devolucion.",
				"Resultado esperado: Se muestra la seccion Bol´s de devolucion");
		Thread.sleep(2000);
		B07_ResumenDevolucion.BuscarSeccion("Sección Bols");
		Thread.sleep(2000);
		B07_ResumenDevolucion.ValidacionSeccionBOLsDevolucion();
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		// Paso #3.- Dentro de la pantalla se encuentra la tabla: Bol´s a devolver 
		// Resultado esperado: "?Se muestra la tabla bol´s a devolver en donde:
		// *Columnas: contienen el Tipo de devolucion: Especial y Normal.
		// *Filas: Mostrar cada 1 de las devoluciones seleccionadas, con su tipo de devolucion.
		// *Contabilizar las devoluciones seleccionadas en la parte inferior de la tabla."
		System.out.println("Paso #3: Dentro de la pantalla se encuentra la tabla: Bol´s a devolver\n");
		Thread.sleep(2000);
		addStep("Dentro de la pantalla se encuentra la tabla: Bol´s a devolver",
				"Resultado esperado: \"Se muestra la tabla bol´s a devolver en donde:\r\n"
				+ " *Columnas: contienen el Tipo de devolucion: Especial y Normal.\r\n"
				+ "*Filas: Mostrar cada 1 de las devoluciones seleccionadas, con su tipo de devolucion.\r\n"
				+ "*Contabilizar las devoluciones seleccionadas en la parte inferior de la tabla.\"");
		Thread.sleep(2000);
		B07_ResumenDevolucion.ValidacionTablaBOLsADevolver();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		// Cerrar Apps
		System.out.println("Terminando caso y cerrando OaD...");
	}


	@Override		
	public String setTestFullName() {
		return " MTC-FT-118 Handheld Resumen de Devolucion-Bol´s de devolucion(Bol´s a devolver)";
	}

	@Override
	public String setTestDescription() {
		return " Validar que en el apartado que lleva por nombre Bol´s de devolucion, se haya realizado correctamente la actualizacion de los siguientes datos:\n"
				+ "Tabla: Bol´s a devolver\n"
				+ "Columnas: Tipo de devolucion: Especial y Normal.\n"
				+ "Filas: Mostrar cada 1 de las devoluciones seleccionadas, con su tipo de devolucion.\n"
				+ "Contabilizar las devoluciones seleccionadas en la parte inferior de la tabla.";
	}

	@Override
	public String setTestDesigner() {
		return " Jesús Marcelo Gutiérrez Bravo - GTIM QA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "*App Oxxo al dia instalada en Handheld(version mas actual).\n"
				+ "*Usuario y Contraseña de Aplicacion Oxxo al dia.\n"
				+ "*Comunicacion entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexion entre ambos dispositivos).\n"
				+ "*P.V con acceso a la Herramienta de compra.\n"
				+ "*Realizar flujo de recepcion hasta llegar a la pantalla resumen de devolucion.\n"
				+ "*Haber seleccionado bol´s especial y normal en la pantalla devoluciones a cedis.";
	}

	@Override
	public void beforeTest() {
	}

}