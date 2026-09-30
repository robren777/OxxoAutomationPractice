package Mobile.cedis.mvp3.handheld.sprint5;

import static org.testng.Assert.assertTrue;

//import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.B05_DevolucionMercancia;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class Debug_Ricardo extends BaseExecution{
	private AppiumUtil appiumUtil;
	@Test(dataProvider = "data-provider", enabled = true)
	
	public void Debug_Ricardo_test(HashMap<String, String> data) throws Exception   {
	

	    /******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
	    DesiredCapabilities caps = new DesiredCapabilities();

	    caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
	    caps.setCapability("deviceName", data.get("deviceName"));
	    caps.setCapability("platformVersion", data.get("platformVersion"));
	    caps.setCapability("platformName", data.get("platformName"));
	    caps.setCapability("noReset", true);


	    String url = "http://127.0.0.1:4723/";

	    Thread.sleep(1000);
	    //Abre la aplicación
	    Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

	    AppiumUtil appiumUtil = null;
	    try {
	        appiumUtil = new AppiumUtil(url, caps);
	    } catch (SessionNotCreatedException e) {
	        assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
	                + e.getMessage());
	        e.printStackTrace();
	    }
		B05_DevolucionMercancia POM_Devolucion = new B05_DevolucionMercancia(testCase, appiumUtil, this);
		POM_Devolucion.SeleccionarObjeto("Continuar");
		POM_Devolucion.SeleccionarObjeto("Confirmar");
}
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1. App Oxxo al día instalada en Handheld.\r\n"
				+ "2. Usuario y Contraseña de Aplicación Oxxo al día.\r\n"
				+ "3. Comunicación entre HH y POS (red wifi (Handheld) y cableada (POS), que permitan conexión entre ambos dispositivos).\r\n"
				+ "4. P.V con acceso a la Herramienta de compra.\r\n"
				+ "5. Haber recepcionado un pedido con cajas/canastillas"
				+ "6. Que el SKU que se meterá como caduco no sea faltante \r\n"
				+ "7. Haber verificado ese mismo pedido\r\n";
	}
	@Override
	public String setTestDescription() {
		return "Validar que al seleccionar un SKU del listado se muestre un mensaje de confirmación ";
	}

	@Override
	public String setTestDesigner() {
		return "Ricardo Hdz";
	}

	@Override
	public String setTestFullName() {
		return "MTC-FT-008/ATC_FT_008_HandHeld_Acreditacion_Mensaje_al_seleccionar_un_SKU_de_un_caduco_del_listado_filtrado";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}	
}


