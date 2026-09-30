package Mobile.cedis.mvp3.handheld.sprint1;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.C01_Listado_de_transferencias_a_verificar;
import Mobile.cedis.om.C02_Verificacion_De_Pedido_Y_Permisos;
import Mobile.cedis.om.C04_Acreditacion_de_Caducos;
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

        C04_Acreditacion_de_Caducos caducos = new C04_Acreditacion_de_Caducos(testCase, appiumUtil, this);
		
		
    	//=================================== Inicio de Caso =====================================//
    	
      //Entro al listado de pedidos por verificar y busco la transferencia que quiero a traves del buscador para entrar en esa
		C01_Listado_de_transferencias_a_verificar ListadoVerificacion = new C01_Listado_de_transferencias_a_verificar(testCase, appiumUtil, this);
		System.out.println("Dando clic en Transferencia a Verificar");
		Thread.sleep(2000);
		ListadoVerificacion.busquedaPrimeraTransferenciaPorVerificar_Marcelo();

		//Entro a verificacion, espero la barra, doy clic en bultos, clic en el primero, tomo evidencia y verifico todos los articulos cuadrando las cantidades,
		//Lo mismo para el segundo bulto y doy clic en continuar, para pasar directo a Caducos
		C02_Verificacion_De_Pedido_Y_Permisos verificacion = new C02_Verificacion_De_Pedido_Y_Permisos(testCase, appiumUtil, this);
		System.out.println("Entrando a Verificacion");
		verificacion.esperaDinamicaLoaderDeVerificacion(90000);
		verificacion.darClickBotonBultos();
		Thread.sleep(2000);
		verificacion.hacerClickEnElPrimerBultoDelPadre();

        
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


