package Mobile.Mep.PromOxxoImagenes;

import Mobile.Mep.PromOxxoImagenes.om.VistaPromOxxoImagenes;
import Mobile.Mep.PromOxxoOffline.om.PostgreSQLOffline;
import Mobile.Mep.PromOxxoOffline.om.UtilOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaEvaluacionMEPOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaOAuth0Offline;
import Mobile.Mep.om.*;
import Mobile.util.GlobalVariables;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utils.AppiumUtil;
import utils.password.PasswordUtil;

import java.util.HashMap;
import java.util.List;

import static org.testng.Assert.assertTrue;

/**
 * DESCRIPCIÓN:<p> Verifique que la pantalla \"Zona de Tienda\" contenga el listado de grupos de espacios Promocionales disponibles para el Proceso Promocional seleccionado
 *<p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: <p>
 * \t- HH_TABLET_PromOxxoImagenes_ZonaDeTienda_correcto_promoEspacioGrupos_mostrado
 *<p>
 * PREREQUISITOS: <p>
 * 1. El usuario ha iniciado sesión con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SHC.<p>
 * 2. Hay un período de promoción activo.<p>
 * 3. El usuario se encuentra en la pantalla \"Zona de Tienda\" para el proceso de promoción seleccionado (por ejemplo, \"Cerveza\").<p>
 * <p>
 * INSUMOS:
 *   - Numero de empleado
 *   - crTienda
 *   - crPlaza
 *   - pin 1111
 * <p>
 * @author Mario A. De Leon Rdz.
 * @date 26/Febrero/2026
 */

public class ATC_FT_020_ZonaDeTiendaCorrectoPromoEspacioGruposMostrado extends BaseExecution {

    AppiumUtil appiumUtil = null;
    private String appPackage;
    String usernamePromOxxoQA = null;
    String passwordPromOxxoQA = null;
    String crTienda = null;

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_020_ZonaDeTiendaCorrectoPromoEspacioGruposMostrado_test(HashMap<String, String> data) throws Exception {
        appPackage = data.get("appPackage");
        String appActivity = data.get("appActivity");
        usernamePromOxxoQA = data.get("usernamePromOxxoQA");
        passwordPromOxxoQA = PasswordUtil.decryptPassword(data.get("passwordPromOxxoQA"));
        String periodId = data.get("periodId");
        crTienda = data.get("crTienda");
        String crPlaza = data.get("crPlaza");
        String numEmpleado = data.get("numEmpleado");

        //Variables y Configuración (Capabilities)----------------------------------------------------------------------
        String url = GlobalVariables.urlMep;

        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
        cap.setCapability("deviceName", data.get("deviceName"));
        cap.setCapability("platformVersion", data.get("platformVersion"));
        cap.setCapability("platformName", data.get("platformName"));
        cap.setCapability("appPackage", data.get("appPackage"));
        cap.setCapability("appActivity", data.get("appActivity"));
        cap.setCapability("appium:disableAutofill", true);
        cap.setCapability("appium:autoGrantPermissions", true);
        cap.setCapability("unicodeKeyboard", true);
        cap.setCapability("resetKeyboard", true);

        //Utilerias e Inicialización del Driver
        try {
            appiumUtil = new AppiumUtil(url, cap);
        } catch (SessionNotCreatedException e) {
            assertTrue(false, "Hubo un problema al acceder a la aplicacion. \n" + e.getMessage());
            e.printStackTrace();
        }

        // Inicialización de Page Objects (Vistas)
        VistaOAuth0Offline oAuthOff = new VistaOAuth0Offline(testCase, appiumUtil, this);
        PostgreSQLOffline sqlOffline = new PostgreSQLOffline(testCase, this);
        UtilOffline utilOffline = new UtilOffline(testCase, appiumUtil, this);
        VistaIdentificacionTienda identifTienda = new VistaIdentificacionTienda(testCase, appiumUtil, this);
        VistaPanelDeControl vistaPanelDeControl = new VistaPanelDeControl(testCase, appiumUtil, this);
        VistaEvaluacionMEPOffline evaluacionMepOffline = new VistaEvaluacionMEPOffline(testCase, appiumUtil, this);
        VistaPromOxxoImagenes vistaPromOxxoImagenes = new VistaPromOxxoImagenes(testCase, appiumUtil, this);
        //PRERREQUISITOS --------------------------------------------------------------------------------------
        appiumUtil.driver.activateApp(appPackage + "/" + appActivity);
        identifTienda.LoginApp(crPlaza, crTienda);
        oAuthOff.loginUser(numEmpleado);
        vistaPanelDeControl.clickBtnCerrarModalPromOxxoImagenesSinEvOpc();
        vistaPanelDeControl.clickOpcionPromOxxoImagenesSinEv();
        vistaPromOxxoImagenes.clickBtnAceptarSinEv();
        vistaPromOxxoImagenes.clickPeriodoActualSinEv();
        vistaPromOxxoImagenes.validarVistaPromOxxoImagenesSinEv();
        vistaPromOxxoImagenes.clickProcesoPromocionalActivoSinEv();
        vistaPromOxxoImagenes.clickGrupoEspaciosPromocionalesSinEv();
        List<String> grupos = evaluacionMepOffline.guardarGruposPromocionales();
        utilOffline.clickTextoSinEv(grupos.get(2));

        //PASO 1 ---------------------------------------------------------------------------------------
        addStep("Compare la lista de grupos de espacios promocionales disponibles con los datos de la base de datos");
        utilOffline.clickTexto(grupos.get(2));
        List<String> espaciosPromocionales = evaluacionMepOffline.guardarGruposPromocionales();
        sqlOffline.validarListaGruposEspaciosPromocionalesConBD(usernamePromOxxoQA, passwordPromOxxoQA, espaciosPromocionales, crTienda, crPlaza);
        

    }

    @AfterMethod
    public void afterMethod() {
        try {
            if (appiumUtil != null && appiumUtil.driver != null) {
                appiumUtil.driver.quit();
                System.out.println("Fin del test, se cierra app con afterMethod()");
            }
        } catch (Exception e) {
            System.err.println("Error en AfterMethod: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_020_ZonaDeTiendaCorrectoPromoEspacioGruposMostrado";
    }

    @Override
    public String setTestDescription() {
        return "Verifique que la pantalla \"Zona de Tienda\" contenga el listado de grupos de espacios Promocionales disponibles para el Proceso Promocional seleccionado";
    }

    @Override
    public String setTestDesigner() {
        return "Mario A. De León Rdz.";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "1. El usuario ha iniciado sesión con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SHC. 2. Hay un período de promoción activo. 3. El usuario se encuentra en la pantalla \"Zona de Tienda\" para el proceso de promoción seleccionado (por ejemplo, \"Cerveza\").";
    }

    @Override
    public void beforeTest() {
    }
}
