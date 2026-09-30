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
 * DESCRIPCIÓN:<p> Verifique que el mensaje de error \"No se encontraron resultados\" se muestre cuando el usuario escribe un valor que no coincide con ninguno de los procesos de promoción
 *<p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: <p>
 * \t- HH_TABLET_PromOxxoImagenes_Materiales_Promocionales_búsqueda_error_mostrado
 *<p>
 * PREREQUISITOS: <p>
 * 1. El usuario ha iniciado sesión con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SHC.<p>
 * 2. Hay un período de promoción activo.<p>
 * 3. El usuario se encuentra en la pantalla \"Materiales promocionales\" del espacio promocional seleccionado (por ejemplo, \"MiniBanner Cerveza Hnk\").<p>
 * <p>
 * INSUMOS:
 *   - Numero de empleado
 *   - crTienda
 *   - crPlaza
 *   - pin 1111
 *   - App Versión QA v1.8.2(25)
 * <p>
 * @author Mario A. De Leon Rdz.
 * @date 26/Febrero/2026
 */

public class ATC_FT_030_MaterialesPromocionalesBusquedaErrorMostrado extends BaseExecution {

    AppiumUtil appiumUtil = null;
    private String appPackage;
    PostgreSQLOffline sqlOffline = null;
    String usernamePromOxxoQA = null;
    String passwordPromOxxoQA = null;
    String crTienda = null;

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_030_MaterialesPromocionalesBusquedaErrorMostrado_test(HashMap<String, String> data) throws Exception {
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
        vistaPromOxxoImagenes.clickEspacioPromocionalSinEv();
        List<String> espaciosPromocionales = evaluacionMepOffline.guardarGruposPromocionales();
        utilOffline.clickTextoSinEv(espaciosPromocionales.get(3));
        //PASO 1 ---------------------------------------------------------------------------------------------
        addStep("Haga clic en el icono \"Buscar\"");
        vistaPromOxxoImagenes.clickIconoBuscarMateriales();
        vistaPromOxxoImagenes.validarCampoBuscarMateriales();

        //PASO 2 ---------------------------------------------------------------------------------------------
        addStep("Comience a escribir los caracteres que no estén relacionados con ninguno de los materiales promocionales (por ejemplo, \"dfgdfg\")");
        vistaPromOxxoImagenes.ingresarTextoBusquedaMateriales("dfgdfg");
        vistaPromOxxoImagenes.validarMensajeNoResultadosMateriales();
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
        return "ATC_FT_030_MaterialesPromocionalesBusquedaErrorMostrado";
    }

    @Override
    public String setTestDescription() {
        return "Verifique que el mensaje de error \"No se encontraron resultados\" se muestre cuando el usuario escribe un valor que no coincide con ninguno de los procesos de promoción";
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
        return "1. El usuario ha iniciado sesión con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SHC. 2. Hay un período de promoción activo. 3. El usuario se encuentra en la pantalla \"Materiales promocionales\" del espacio promocional seleccionado (por ejemplo, \"MiniBanner Cerveza Hnk\").";
    }

    @Override
    public void beforeTest() {
    }
}
