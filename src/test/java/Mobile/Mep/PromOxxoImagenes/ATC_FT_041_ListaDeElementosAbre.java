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
 * DESCRIPCIÓN:<p> Compruebe que la imagen con la lista de elementos se abra en la pantalla "Visor de imágenes" cuando el usuario haga clic en ellos
 *<p>
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN: <p>
 * HH_TABLET_PromOxxoImagenes_CabecerasExhibidores_ListaDeElementos_imagen_abre
 *<p>
 * PREREQUISITOS: <p>
 * 1. El usuario ha iniciado sesión con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SHC.
 * 2. Hay un período de promoción activo.
 * 3. Hay un planograma para el espacio promocional.
 * 4. El usuario se encuentra en la pantalla "Materiales promocionales" de uno de los espacios promocionales "Cabeceras" o "Exhibidores" (por ejemplo, "Cabecera Fría Cerveza").
 * <p>
 * INSUMOS:
 *   - Numero de empleado
 *   - crTienda
 *   - crPlaza
 *   - pin 1111
 *   - App Versión QA v1.9.1(28)
 * <p>
 * @author Ivan Yakab
 * @date 30/Abril/2026
 */

public class ATC_FT_041_ListaDeElementosAbre extends BaseExecution {

    AppiumUtil appiumUtil = null;
    private String appPackage;
    PostgreSQLOffline sqlOffline = null;
    String usernamePromOxxoQA = null;
    String passwordPromOxxoQA = null;
    String crTienda = null;

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_041_ListaDeElementosAbre_test(HashMap<String, String> data) throws Exception {
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
		VistaOAuth0 auth0 = new VistaOAuth0(testCase, appiumUtil, this);
		VistaChromeAppCache vistaChromeAppCache = new VistaChromeAppCache(testCase, appiumUtil, this);
		VistaUsuariosNoExistentes usuariosNoExistentes = new VistaUsuariosNoExistentes(testCase, appiumUtil, this);
        VistaPanelDeControl vistaPanelDeControl = new VistaPanelDeControl(testCase, appiumUtil, this);
        VistaEvaluacionMEPOffline evaluacionMepOffline = new VistaEvaluacionMEPOffline(testCase, appiumUtil, this);
        VistaPromOxxoImagenes vistaPromOxxoImagenes = new VistaPromOxxoImagenes(testCase, appiumUtil, this);

        appiumUtil.driver.activateApp(appPackage + "/" + appActivity);
                
        addStep("Realizamos el inicio de sesión con un usuario que tenga los códigos Cr de la tienda: CrPlace:" + crPlaza + ", CrStore: " + crTienda);
		identifTienda.LoginApp(data.get("crPlaza"),data.get("crTienda"));
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

    	addStep("Se abre la vista 'No existen usuarios aun'");
		usuariosNoExistentes.validarVistaUsuariosNoExistentes();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

	    addStep("Hacer click en el boton Iniciar Sesion");
		usuariosNoExistentes.clickIniciarSesion();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		addStep("Se abre la vista de autenticación OAuth de Google dentro de la App de Chrome y iniciamos sesion con el numero de empleado: " + numEmpleado);
        oAuthOff.loginUser(numEmpleado);
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

		
		addStep("Validar que se muestre la opcion Evaluacion MEP");
		vistaPanelDeControl.clickOpcionPromOxxoImagenes();
		testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
		
		addStep("Validar que se muestre el popup de promociones comienzan...");
		vistaPromOxxoImagenes.validarPopupLasPromocionesComienzan();
        testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
        
        addStep("Hacer click en el boton Aceptar del popup");
        vistaPromOxxoImagenes.clickBtnAceptar();
        testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

        addStep("Click en 'Periodo actual'");
        vistaPromOxxoImagenes.clickPeriodoActual();
        testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
        
        addStep("Click opcion 'PromOXXO Nacional'");
        vistaPromOxxoImagenes.clickProcesoPromocionalPromOXXONacional();
        testCase.addScreenShotCurrentStep(appiumUtil, "testImage");

        addStep("Click en la opcion 'Cabeceras' dentro de la Zona de Tienda");
        vistaPromOxxoImagenes.clickZonaDeTiendaCabeceras();
        testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
        
        addStep("Click en la cabecera 'Cabecera 1'");
        vistaPromOxxoImagenes.clickEspacioPromocionalCabecera1();
        testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
        
        addStep("Click en la pestana 'Planogramas'");
        vistaPromOxxoImagenes.clickPestanaPlanogramas();
        testCase.addScreenShotCurrentStep(appiumUtil, "testImage");
        
        addStep("Verificar que se muestre la imagen de la Cabecera 1 en la pestana 'Planogramas'");
        vistaPromOxxoImagenes.isImagenMaterialPromocionalDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "testImage");        
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
        return "ATC_FT_041_ListaDeElementosAbre";
    }

    @Override
    public String setTestDescription() {
        return "Compruebe que la imagen con la lista de elementos se abra en la pantalla \"Visor de imágenes\" cuando el usuario haga clic en ellos";
    }

    @Override
    public String setTestDesigner() {
        return "Ivan Yakab";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "1. El usuario ha iniciado sesión con los códigos Cr de la tienda: CrPlace: 10MON, CrStore: 50SHC.\n" +
				"2. Hay un período de promoción activo.\n" +
				"3. Hay un planograma para el espacio promocional.\n" +
				"4. El usuario se encuentra en la pantalla \"Materiales promocionales\" de uno de los espacios promocionales \"Cabeceras\" o \"Exhibidores\" (por ejemplo, \"Cabecera Fría Cerveza\").";
    }

    @Override
    public void beforeTest() {
    }
}
