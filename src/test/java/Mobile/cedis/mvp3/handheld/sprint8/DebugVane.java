package Mobile.cedis.mvp3.handheld.sprint8;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.cedis.om.B01_RecepcionPedidosSeleccionarTransferencia;
import Mobile.cedis.om.B02_RecepcionPedidosCEDIS;
import Mobile.cedis.om.B03_ResumenPedidosCEDIS;
import Mobile.cedis.om.B04_AcuerdoEntrega;
import Mobile.cedis.om.B05_DevolucionMercancia;
import Mobile.cedis.om.B07_ResumenDevolucion;
import Mobile.cedis.om.B08_AcuerdoDevolucion;
import Mobile.cedis.om.B09_FormatoUnicoEntrega;
import Mobile.cedis.om.B10_DeseaComenzar;
import Mobile.cedis.om.D01_Conexiones_BD;
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
//        caps.setCapability("noReset", true); // Se debe de dejar est� opcion en true para que no se borre el cache y asi lograr evitar la vista de ingresar la IP y puerto
//        caps.setCapability("appium:appPackage", data.get("appPackageP"));
//		caps.setCapability("appium:appActivity", data.get("appActivityP"));
//		caps.setCapability("ip", data.get("ip"));
		caps.setCapability("userLogin", data.get("userLogin"));
		caps.setCapability("passwordLogin", data.get("passwordLogin"));
		caps.setCapability("numTransferencia", data.get("numTransferencia"));

        String url = "http://127.0.0.1:4723/";

        //Abre la aplicación
//     Runtime.getRuntime().exec("adb shell am start " + data.get("appPackage"));

        AppiumUtil appiumUtil = null;
        try {
            appiumUtil = new AppiumUtil(url, caps);
        } catch (SessionNotCreatedException e) {
            assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n"
                    + e.getMessage());
            e.printStackTrace();
        }

        
        B01_RecepcionPedidosSeleccionarTransferencia re = new B01_RecepcionPedidosSeleccionarTransferencia(testCase, appiumUtil, this);
		B02_RecepcionPedidosCEDIS PedidoRecepcion = new B02_RecepcionPedidosCEDIS(testCase, appiumUtil, null);
		D01_Conexiones_BD conexion = new D01_Conexiones_BD(testCase, appiumUtil, null);
		B03_ResumenPedidosCEDIS resumen = new B03_ResumenPedidosCEDIS(testCase, appiumUtil, this);
		B04_AcuerdoEntrega firma = new B04_AcuerdoEntrega(testCase, appiumUtil, this);
		B05_DevolucionMercancia devo = new B05_DevolucionMercancia(testCase, appiumUtil, this);
		B07_ResumenDevolucion redevo = new B07_ResumenDevolucion(testCase, appiumUtil, this);
		B08_AcuerdoDevolucion acdevo = new B08_AcuerdoDevolucion(testCase, appiumUtil, this);
		B09_FormatoUnicoEntrega fue = new B09_FormatoUnicoEntrega(testCase, appiumUtil, this);
		B10_DeseaComenzar comenzar = new B10_DeseaComenzar(testCase, appiumUtil, null);
		
//		devo.SeleccionarBOLSinCheck("", "");
//		devo.AumentarCanastilla(1, "Azul");
//		devo.AumentarCanastilla(2, "Amarilla");
//		devo.AumentarCanastilla(3, "Verde");
//		devo.AumentarCanastilla(4, "Naranja");
//		devo.AumentarCanastilla(5, "Otro");
//		devo.AumentarContenedores(1, "Tapas");
//		devo.AumentarContenedores(1, "Envases");
////		devo.TomarFotoDevolucion();
//		devo.IngresarCanastilla("756478");
//		devo.TomarFotoCanastilla();
//		Thread.sleep(10000);
//		System.out.println("Seleccionando botón continuar...");
//		devo.SeleccionarObjeto("Continuar");
//		redevo.ValidacionEncabezado();
//		redevo.ClickFirmaAcuerdo("", "");
//		acdevo.ValidacionBotonTerminarDevolucion();
//		acdevo.subir();
//		acdevo.OpcionAreaDistribucion("", "", "Devolucion correcta");
//		acdevo.FirmaAreaDistribucion("", "");
//		acdevo.OpcionPersonalTienda("", "", "Sin comentario");
//		acdevo.FirmaPersonalTienda("", "");
//		acdevo.TerminarRecepcion("","");
//		PedidoRecepcion.DeslizarArriba();
//		PedidoRecepcion.ValidacionEncabezado();
//		PedidoRecepcion.Tomar3Fotos();
//		PedidoRecepcion.recepcionarUnaCaja();
//		PedidoRecepcion.CapturaContenedor("667209");
//		PedidoRecepcion.CapturaContenedor("986595");
//		PedidoRecepcion.SeleccionarObjeto("Terminar");
//		PedidoRecepcion.SeleccionarObjeto("Confirmar");
//		PedidoRecepcion.SeleccionarObjeto("Si");
//		PedidoRecepcion.MoverSeccion("Cajas sin etiqueta");
//		PedidoRecepcion.RegistrarSinEtiqueta();
//		acdevo.sendKeysUsingKeyEvents(appiumUtil, "B");
//		PedidoRecepcion.SeleccionarObjeto("Desea Salir");
//		acdevo.subir();
//		redevo.BuscarSeccion("Leyenda Devolución");
		redevo.ClickFirmaAcuerdo("", "");

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