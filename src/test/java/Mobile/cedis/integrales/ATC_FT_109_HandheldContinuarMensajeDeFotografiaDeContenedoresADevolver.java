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
 * Validar que se muestre un mensaje de advertencia si no se tomó evidencia para canastillas con devolución
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_109_HandheldContinuar:MensajeDeFotografiaDeContenedoresADevolver
 *
 * PRERREQUISITOS:
 * 1. Haber realizado la recepcion de una transferencia que contenga al menos bultos tipo canastilla
 * 2. Haber ingresado al menos 2 canastillas para devolucion
 *
 * @author 
 * @date 
 */


public class ATC_FT_109_HandheldContinuarMensajeDeFotografiaDeContenedoresADevolver extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_109_HandheldContinuarMensajeDeFotografiaDeContenedoresADevolver_test(
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
		cap.setCapability("contenedor", data.get("contenedor"));
		cap.setCapability("canastilla1", data.get("canastilla1"));

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
		transferencia.BuscarTransferencia(true, data.get("numTransferencia"));//Se selecciona la primera transferencia sin tomar evidencia
		
		Mobile.cedis.om.B10_DeseaComenzar DeseaComenzar = new Mobile.cedis.om.B10_DeseaComenzar(testCase, appiumUtil, this);//linea agregada por Juan 
		DeseaComenzar.Cargando();//linea agregada por Juan
		DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");//linea agregada por Juan
		
		Mobile.cedis.om.B02_RecepcionPedidosCEDIS pantallatransferencia = new Mobile.cedis.om.B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		pantallatransferencia.DeslizarArriba();
		pantallatransferencia.Tomar3Fotos();
		pantallatransferencia.CapturaContenedor(data.get("contenedor"));//Se captura los contenedores que se encuentran en el excel
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
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
		
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		
		/*Paso 1: Estar en la pantalla "Devoluciones a CEDIS"
		Resultado Esperado: Se muestra la pantalla Devoluciones a CEDIS.*/
		addStep("Estar en la pantalla Devoluciones a CEDIS.\r\n"
				+ "Resultado Esperado: ?Se muestra la pantalla Devoluciones a CEDIS.");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");	
		
		//Continuar PROCESO
		devolucion.AumentarCanastilla(1, "Azul");
		devolucion.AumentarCanastilla(1, "Amarilla");
		devolucion.TomarFotoDevolucion();
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		
		/*Paso 2: Dentro de la pantalla ir a la seccion "Canastillas con devolucion"
		Resultado Esperado: Se muestra la seccion Canastillas con devolucion*/
		addStep("Dentro de la pantalla ir a la seccion Canastillas con devolución\r\n"
				+ "Resultado Esperado: ?Se muestra la seccion Canastillas con devolucion");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 3:Dentro de la seccion selecciona el campo de  texto "Ingresa código de contenedor" y dale entrada al codigo de una canastilla"
		Resultado Esperado: ?Se realiza el registro de la canastilla*/
		addStep("Dentro de la seccion selecciona el campo de  texto \"Ingresa código de contenedor\" y dale entrada al codigo de una canastilla\r\n"
				+ "Resultado Esperado: ?Se realiza el registro de la canastilla");
		devolucion.IngresarCanastilla(data.get("canastilla1"));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		/*Paso 4: Dentro de la pantalla selecciona el boton "CONTINUAR""
		Resultado Esperado: ?Se muestra mensaje: "No se cuenta con la fotografia de canastillas con devolución, favor de tomar la fotografia para continuar. ACEPTAR""*/
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		devolucion.MensajeSinEvidenciaContenedor("Dentro de la pantalla selecciona el boton \"CONTINUAR\"\r\n"
				+ "Resultado Esperado: ?Se muestra mensaje: \"No se cuenta con las fotografias de canastillas con devolución, "
				+ "favor de tomar la fotografia para continuar. ACEPTAR\"");
		
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override		
	public String setTestFullName() {
		return "Nombre caso de prueba ";
	}

	@Override
	public String setTestDescription() {
		return "Descripción del caso de prueba";
	}

	@Override
	public String setTestDesigner() {
		return "Nombre quien construye";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		return "Pre Requisitos";
	}

	@Override
	public void beforeTest() {
	}

}