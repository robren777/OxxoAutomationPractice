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
 * Validar que dentro del campo de texto se pueda ingresar el codigo de la canastilla correctamente
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * MTC_FT_100_HandheldCanastillasConDevolucion:EntradaManualACanastillasEnCampoDeTexto
 *
 * PRERREQUISITOS:
 * 1. Haber realizado la recepcion de una transferencia que contenga al menos bultos tipo canastilla
 * 
 *
 * @author 
 * @date 
 */


public class ATC_FT_100_HandheldCanastillasConDevolucionEntradaManualACanastillasEnCampoDeTexto extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void ATC_FT_100_HandheldCanastillasConDevolucionEntradaManualACanastillasEnCampoDeTexto_test(
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
		/*Paso 1 Estar en la pantalla "Devoluciones a CEDIS"
		 * Resultado esperado: Se muestra la pantalla Devoluciones a CEDIS
		 */
		addStep("Estar en la pantalla \"Devoluciones a CEDIS\"\r\n"
				+ "Resultado esperado: Se muestra la pantalla Devoluciones a CEDIS");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));		
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
						
		devolucion.SeleccionarPrimerBol("", "");
		devolucion.AumentarCanastilla(1, "Azul");
		/*Paso 2 Dentro de la pantalla ir a la seccion "Canastillas con devolucion"
		 * Resultado esperado: Se muestra la seccion Canastillas con devolucion
		 */	
		addStep("Dentro de la pantalla ir a la seccion \"Canastillas con devolucion\"\r\n"
				+ "Resultado esperado: Se muestra la seccion Canastillas con devolucion");
		devolucion.MoverSeccion("Continuar");
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");		
				
		/*Paso 3 Dentro de la seccion selecciona el campo de  texto "Contenedor" e ingresa el codigo de una canastilla correctamente
		 * Resultado esperado: Se realiza el registro de la canastilla 
		 */				
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		addStep("Dentro de la seccion selecciona el campo de  texto \"Contenedor\" e ingresa el codigo de una canastilla correctamente\r\n"
				+ "Resultado esperado: Se realiza el registro de la canastilla");
		devolucion.SeleccionarObjeto("Ingresar Contenedor Devolucion");
		Runtime.getRuntime().exec("adb shell input text 98745474");//Capturar texto			
		//testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		Runtime.getRuntime().exec("adb shell input keyevent 66");//realiza el enter en la handheld
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input keyevent 4");// realiza la acción de Back en la handheld
		appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
						
		// cerrar Apps
		Runtime.getRuntime().exec("adb shell am force-stop \"" + data.get("appPackage"));
	}


	@Override
	public String setTestFullName() {
		return "ATC-FT-100-Handheld Canastillas con devolucion: Entrada manual a canastillas en Campo de texto";
	}

	@Override
	public String setTestDescription() {
		return "Validar que dentro del campo de texto se pueda ingresar el codigo de la canastilla correctamente";
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
		return "1.-Haber realizado la recepcion de una transferencia que contenga al menos bultos tipo canastilla";
	}

	@Override
	public void beforeTest() {
	}

}