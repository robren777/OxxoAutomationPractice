package Mobile.cedis.mvp3.handheld.sprint5;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Mobile.cedis.om.B02_RecepcionPedidosCEDIS;
import Mobile.cedis.om.B11_EncuestaSatisfaccion;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
import Mobile.cedis.om.C05_Resumen_de_verificacion_acreditacion;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class Debug_Marcelo extends BaseExecution {
	@Test(dataProvider = "data-provider", enabled = true)
	public void Debug_Marcelo_Test(HashMap<String, String> data) throws Exception {

		/*******************************
		 * Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script
		 ********************/
		DesiredCapabilities caps = new DesiredCapabilities();

		caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
		caps.setCapability("deviceName", data.get("deviceName"));
		caps.setCapability("platformVersion", data.get("platformVersion"));
		caps.setCapability("platformName", data.get("platformName"));
		String url = "http://127.0.0.1:4723/";


		AppiumUtil appiumUtil = null;
		try {
			appiumUtil = new AppiumUtil(url, caps);
		} catch (SessionNotCreatedException e) {
			assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
			e.printStackTrace();
		}

		Thread.sleep(1000);

		/***********************************************************************************************/	
		B11_EncuestaSatisfaccion B11_EncuestaSatisfaccion = new B11_EncuestaSatisfaccion(testCase, appiumUtil, this); 
		C02_Verificacion_De_Pedido_Y_Permisos C02_Verificacion_De_Pedido_Y_Permisos = new C02_Verificacion_De_Pedido_Y_Permisos(testCase, appiumUtil, this); 
		B02_RecepcionPedidosCEDIS B02_RecepcionPedidosCEDIS = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, this);
		C05_Resumen_de_verificacion_acreditacion resumen = new C05_Resumen_de_verificacion_acreditacion(testCase, appiumUtil, this);
		
		resumen.ValidacionMensajeModDanadosYCaducos();
		
//		Runtime.getRuntime().exec("adb shell input swipe 20 1000 20 850 100");
//		
//		C02_Verificacion_De_Pedido_Y_Permisos.verificarArticulo();
//		
//		Runtime.getRuntime().exec("adb shell input swipe 100 500 100 1450 100");
//		Thread.sleep(2000);
//		B02_RecepcionPedidosCEDIS.Tomar3Fotos();
//
//		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753840-7501064113024-1");
//		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753840-7501064113024-2");
//		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-1");
//		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-2");
//		B02_RecepcionPedidosCEDIS.CapturaContenedor("00001118000086753841-7501064191909-3");
//		
//		B02_RecepcionPedidosCEDIS.CapturaContenedor("5667209");
//		B02_RecepcionPedidosCEDIS.CapturaContenedor("667445");
//		//B02_RecepcionPedidosCEDIS.CapturaContenedor("756377");
//		//B02_RecepcionPedidosCEDIS.CapturaContenedor("756407");
//		
//		B02_RecepcionPedidosCEDIS.CapturaContenedor("7986595");
//		//B02_RecepcionPedidosCEDIS.CapturaContenedor("986601");
//		//B02_RecepcionPedidosCEDIS.CapturaContenedor("986618");
//		//B02_RecepcionPedidosCEDIS.CapturaContenedor("986625");
//		//B02_RecepcionPedidosCEDIS.CapturaContenedor("756384");
//		B02_RecepcionPedidosCEDIS.CapturaContenedor("756391");
		
				
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		
		/***********************************************************************************************/	
	}


//Datos para el caso de prueba
	@AfterMethod
	public void baseAfterMethod() {
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
		return "DESCRIPCION";
	}

	@Override
	public String setPrerequisites() {
		return "PRE-REQUISITOS";
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
