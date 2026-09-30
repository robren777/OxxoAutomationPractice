package Mobile.cedis.gv;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
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
import Mobile.cedis.om.B02_RecepcionPedidosCEDIS;
import Mobile.cedis.om.B03_ResumenPedidosCEDIS;
import Mobile.cedis.om.B05_DevolucionMercancia;
import Mobile.cedis.om.B07_ResumenDevolucion;
import Mobile.cedis.om.B08_AcuerdoDevolucion;
import Mobile.cedis.om.B10_DeseaComenzar;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class Debug_MarceloTABLET extends BaseExecution {
	@SuppressWarnings("null")
	@Test(dataProvider = "data-provider", enabled = true)
	public void Debug_MarceloTABLET_Test(HashMap<String, String> data) throws InterruptedException, IOException  {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		cap.setCapability("deviceName", data.get("deviceName"));
		cap.setCapability("platformVersion", data.get("platformVersion"));
		cap.setCapability("platformName", data.get("platformName"));
		// Tener en cuenta que para los Scripts de Tablet hay que poner el udid sino no funciona, se puso el udid de la tablet prestada (No es la de la galleta)
		cap.setCapability("udid", data.get("udId"));

		String url = "http://127.0.0.1:4723/";

		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, cap);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
					+ e.getMessage());
			e.printStackTrace();
		}
		
		//Inicializacion de POMs
		B01_RecepcionPedidosSeleccionarTransferencia B01_RecepcionPedidosSeleccionarTransferencia = new B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		B10_DeseaComenzar B10_DeseaComenzar = new B10_DeseaComenzar(testCase, appiumUtil, this);
		B05_DevolucionMercancia B05_DevolucionMercancia = new B05_DevolucionMercancia(testCase, appiumUtil, this);
		B08_AcuerdoDevolucion B08_AcuerdoDevolucion = new B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		B07_ResumenDevolucion B07_ResumenDevolucion = new B07_ResumenDevolucion(testCase, appiumUtil, this);
		B02_RecepcionPedidosCEDIS B02_RecepcionPedidosCEDIS = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		B03_ResumenPedidosCEDIS B03_ResumenPedidosCEDIS = new B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		A05_LoginAreaDistribucion A05_LoginAreaDistribucion = new A05_LoginAreaDistribucion(testCase, appiumUtil, this);
		A03_Menus_y_submenus A03_Menus_y_submenus = new A03_Menus_y_submenus(testCase, appiumUtil, this);
		A04_MenuHerramientas A04_MenuHerramientas = new A04_MenuHerramientas(testCase, appiumUtil, this);
		A01_Login_Punto_De_Venta A01_Login_Punto_De_Venta = new A01_Login_Punto_De_Venta(testCase, appiumUtil, this);
		A02_Login_Lider A02_Login_Lider = new A02_Login_Lider(testCase, appiumUtil, this);
		Mobile.cedis.om.A05_LoginAreaDistribucion C05LoginAreaDistribucion = new Mobile.cedis.om.A05_LoginAreaDistribucion(testCase,
				appiumUtil, this);
		

				
/* 
 * Paso 22 - Daremos clic en la primer transferencia que avanzamos e ingresamos datos
 * Resultado esperado: Se despliega correctamente esta transferencia con los datos que ingresamos correctamente (cajas, canastillas, cajas sin etiqueta, sobrantes y danados)
*/
		addStep("Daremos clic en la primer transferencia que avanzamos e ingresamos datos", "Resultado esperado: Se despliega correctamente esta transferencia con los datos que ingresamos correctamente (cajas, canastillas, cajas sin etiqueta, sobrantes y danados)");
		B01_RecepcionPedidosSeleccionarTransferencia.SeleccionarPrimeraTransferencia(true);
		Thread.sleep(2000);
		B10_DeseaComenzar.Cargando();
		Thread.sleep(2000);
		B10_DeseaComenzar.SeleccionarObjeto("Recepcion Pedido");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidacionEncabezado();
		B02_RecepcionPedidosCEDIS.ValidarGuardadoDeLasTresFotografias();
		B02_RecepcionPedidosCEDIS.ValidarAumentoDeBultosEnTotalEntregado();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarDespliegueSeccionSobrantes();
		B02_RecepcionPedidosCEDIS.ValidarIngresoSobrantes(5);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		Runtime.getRuntime().exec("adb shell input swipe 21 1003 23 900 40");
		Thread.sleep(2000);
		B02_RecepcionPedidosCEDIS.ValidarDespliegueSeccionDanados();
		B02_RecepcionPedidosCEDIS.ValidacionIngresoDanados(30);
		Thread.sleep(2000);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
	}

//Datos para el caso de prueba
	@AfterMethod
	public void baseAfterMethod(ITestResult result) {
		
	}

	@BeforeTest
	public void beforeTest() {
	}

	@Override
	public String setTestFullName() {
		return "DEBUG";
	}

	@Override
	public String setTestDescription() {
		return "Validar que al \"aceptar\" el mensaje fotografias de canastillas con devolucion la pantalla haga un autoscroll hasta la parte donde hace falta la evidencia";
	}

	@Override
	public String setPrerequisites() {
		return "*Haber realizado la recepción de una transferencia que contenga al menos bultos tipo canastilla\r\n"
				+ "*Indicar cantidad de canastillas con devolución (color correspondiente)\r\n"
				+ "*Haber seleccionado un BOL al menos\r\n"
				+ "*Haber colocado la evidencia de devolución de canastillas";
	}

	@Override
	public String setTestDesigner() {
		return "Marcelo Gutiérrez";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
