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
 * Validar el comportamiento correcto de la seccion: "Devolucion de contenedores", subseccion: "Canastillas"
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_090_HandHeldDevolucionDeContenedores:RegistroYValidacionDeCanastillas
 *
 * PRERREQUISITOS:
 * 1. Tener devoluciones tipo normal y especial programada, que abarquen los ultimos 14 dias y fechas mas antiguas
 * 2. Tener BOL seleccionado
 *
 * @author Juan Ibarra
 * @date   08/12/2024
 */


public class ATC_FT_090_HandheldDevolucionDeContenedoresRegistroYValidacionDeCanastillas extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_090_HandheldDevolucionDeContenedoresRegistroYValidacionDeCanastillas_test(
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
		acuerdo.FirmaAreaDistribucion("SinEvidencia", null);
		acuerdo.FirmaPersonalTienda("SinEvidencia", null);
		acuerdo.TerminarRecepcion("SinEvidencia", null);
		
		
		Mobile.cedis.om.B05_DevolucionMercancia devolucion = new Mobile.cedis.om.B05_DevolucionMercancia(testCase, appiumUtil, this);
		devolucion.SeleccionarPrimerBol("SinEvidencia", null);
	
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");// Se desliza hacía abajo
		/*Paso 1 Se ubica en la pantalla: "Devoluciones a CEDIS", seccion: "Devolucion de contenedores", subseccion: "Canastillas"
		 * Resultado esperado: Se despliega correctamente la subseccion: "Canastillas", con las especificaciones de inicio siguientes:
		 * *Opciones de canastillas: Azul, Amarillo, Verde, Naranja y Otro
		 * *Todas las opciones inician en 0
		 * *Tienen los botones: "-" y "+" para ajustar hacia abajo o hacia arriba las cantidades
		 */		
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		addStep("Se ubica en la pantalla: \"Devoluciones a CEDIS\", seccion: \"Devolucion de contenedores\", subseccion: \"Canastillas\"\r\n"
				+ "\n"
				+ "Resultado esperado: Se despliega correctamente la subseccion: \"Canastillas\", con las especificaciones de inicio siguientes:\r\n"
				+ "*Opciones de canastillas: Azul, Amarillo, Verde, Naranja y Otro\r\n"
				+ "*Todas las opciones inician en 0\r\n"
				+ "*Tienen los botones: \"-\" y \"+\" para ajustar hacia abajo o hacia arriba las cantidades");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		devolucion.SeleccionarObjeto("Menos Azul");
		
		
		/*Paso 2 Se da clic en el boton: "-", de cualquier opcion de color de las canastillas
		 * Resultado esperado: Se mantiene el numero en 0, ya que el valor ingresado no puede ser negativo
		 */		
		addStep("Se da clic en el boton: \"-\", de cualquier opcion de color de las canastillas\r\n"
				+ "\n"
				+ "Resultado esperado: Se mantiene el numero en 0, ya que el valor ingresado no puede ser negativo");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		
		/*Paso 3 Se da clic en el boton: "+", de cualquier opcion de color de las canastillas
		 * Resultado esperado: Se incrementa el numero de canastillas ingresadas para el color correspondiente
		 */		
		addStep("Se da clic en el boton: \"+\", de cualquier opcion de color de las canastillas\r\n"
				+ "\n"
				+ "Resultado esperado: Se incrementa el numero de canastillas ingresadas para el color correspondiente");			
		devolucion.SeleccionarObjeto("Mas Azul");		
		devolucion.SeleccionarObjeto("Mas Azul");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");				

		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));

	}

	

	@Override
	public String setTestFullName() {
		return "ATC-FT-090-HandHeld Devolucion de contenedores: Registro y validacion de Canastillas";
	}

	@Override
	public String setTestDescription() {
		return "Validar el comportamiento correcto de la seccion: \"Devolucion de contenedores\", subseccion: \"Canastillas\"";
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
		return "1.Tener devoluciones tipo normal y especial programada, que abarquen los ultimos 14 dias y fechas mas antiguas\r\n"
				+ "2.-Tener BOL seleccionado";
	}

	@Override
	public void beforeTest() {
	}

}