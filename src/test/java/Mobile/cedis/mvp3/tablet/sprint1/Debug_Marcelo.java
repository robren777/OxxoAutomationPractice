package Mobile.cedis.mvp3.tablet.sprint1;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.C03_Acreditacion_de_Danados;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

// MTC-FT-014/HandHeld
// ACREDITACIÓN
// "Modificar Dañados y Caducos" - Agregar un artículo dañado con el máximo ingresado en "Artículos Caducos"
public class Debug_Marcelo extends BaseExecution {
    @Test(dataProvider = "data-provider", enabled = true)
    public void Debug_Marcelo_test(HashMap<String, String> data) throws Exception {

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

        //Entro a Caducos para ingresar el burrito que tiene de cantidad máxima 8 por eso el for
        C03_Acreditacion_de_Danados danados = new C03_Acreditacion_de_Danados(testCase, appiumUtil, this);
        Thread.sleep(1000);
        danados.presionarbotonIngresarCodigoSKU();
        Thread.sleep(300);
        danados.ingresar3primerasletrasV();
        Thread.sleep(300);
        danados.clicPrimerArticuloListado();
        Thread.sleep(300);
        danados.presionarbotonSiDescripcionArticulo();
        Thread.sleep(300);
        for (int i = 1; i < 8; i++) {
            danados.aumentarcantidadskuDanados();
            Thread.sleep(500);
        }
        danados.tomarfotografiaDanados();
        Thread.sleep(1000);
        danados.finalizarDanados();
        danados.presionarbotonSiDescripcionArticulo();
        danados.presionarbotonNoDescripcionArticulo();
    }

    /**
     * Proveedores Directos MVP2: MTC-FT-001 HandHeld-Escanear QR de pedido Repartidor, con fuente de abasto inválida por segunda ocación.
     * Desc:
     * 1 - Validar que no permita avanzar a la verificación si no encuentra la Fuente de abasto en XPOS.
     2 - Validar que permita leer el mismo QR por segunda ocasión, ya que no se genera un a OC

     * @autor Ma
     * @date 31/05/2024
     */

    @Override
    public String setTestFullName() {
        return "MTC-FT-014/HandHeld ACREDITACIÓN \"Modificar Dañados y Caducos\" - Agregar un artículo dañado con el máximo ingresado en \"Artículos Caducos\"";
    }

    @Override
    public String setTestDescription() {
        return "Validar que al dar clic en: \"Modificar Dañados y Caducos\", no nos permita agregar como dañado un artículo al cual previamente se ingreso su cantidad máxima verificada como caducos";
    }

    @Override
    public String setTestDesigner() {
        return "Marcelo Gutiérrez";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "*Haber recepcionado un pedido con cajas/canastillas\n" +
                "*Haber verificado ese mismo pedido\n" +
                "*Haber ingresado el máximo verificado de un artículo como caduco/s para que no nos dejé ingresarlo como artículo dañado";
    }

    @Override
    public void beforeTest() {
    }
}


