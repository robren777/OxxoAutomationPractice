package Mobile.cedis.mvp3.handheld.sprint1;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.C03_Acreditacion_de_Danados;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;


public class DebugVane extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void DebugVane_test(HashMap<String, String> data) throws Exception {

        /******************************* Inicio - Configuracion Pre eliminar de Dispositivo a Ejecutar Script  ********************/
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
        caps.setCapability("deviceName", data.get("deviceName"));
        caps.setCapability("platformVersion", data.get("platformVersion"));
        caps.setCapability("platformName", data.get("platformName"));
        caps.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
        caps.setCapability("appium:appPackage", data.get("appPackageP"));
		caps.setCapability("appium:appActivity", data.get("appActivityP"));
		caps.setCapability("ip", data.get("ip"));
		caps.setCapability("userLogin", data.get("userLogin"));
		caps.setCapability("passwordLogin", data.get("passwordLogin"));
		caps.setCapability("numTransferencia", data.get("numTransferencia"));

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


        C03_Acreditacion_de_Danados danados = new C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
//        C04_Acreditacion_de_Caducos caducos = new C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
        //System.out.println("Entrando a Verificacion y aceptando permisos");
        //Thread.sleep(2000);
        //Verificacion.necesitaPermisos();
//        System.out.println("Validando pantalla de dañados");
//        Thread.sleep(2000);
//		danados.pantallaDanados();
//		System.out.println("Presionado botón de INGRESAR SKU");
//        Thread.sleep(2000);
//		danados.presionarbotonIngresarCodigoSkuV();
//		System.out.println("Validando si existe la caja de texto");
//        Thread.sleep(2000);
//		danados.siexisteTXT();
//		System.out.println("Ingresando nombre artículo");
//        Thread.sleep(2000);
//		danados.ingresarnombrearticuloV();
//		System.out.println("Validando información");
//		Thread.sleep(2000);
//		danados.validardescripcionarticuloV();
		System.out.println("haciendo clic al articulo");
		
        Thread.sleep(2000);
        danados.presionarbotonNoDescripcionArticulo();
		System.out.println("Se hizo clic en NO");
		Thread.sleep(5000);
		danados.validaringresarnuevamenteskuV();
		System.out.println("validando que se pueda ingresar nuevamente sku");

//		Thread.sleep(2000);
//		danados.presionarbotonIngresarCodigoSKU();
//        System.out.print("Se puede ingresar otro skU");
		
		
//		

        // Cerrar Apps
        Thread.sleep(1000);
        System.out.println("Terminando caso...");
        Thread.sleep(1000);
        System.out.println("Cerrando OaD...");
    }

    /**
     * @autor Va
     * @date 
     */

    @Override
    public String setTestFullName() {
        return "";
    }

    @Override
    public String setTestDescription() {
        return "";
    }

    @Override
    public String setTestDesigner() {
        return "";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "";
    }

    @Override
    public void beforeTest() {
    }
}
