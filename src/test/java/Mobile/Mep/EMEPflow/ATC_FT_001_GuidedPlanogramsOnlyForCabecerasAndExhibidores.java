package Mobile.Mep.EMEPflow;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.remote.AutomationName;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import Mobile.Mep.PromOxxoOffline.om.PostgreSQLOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaOAuth0Offline;
import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.testng.annotations.Test;
import Mobile.Mep.om.EMEPflow;
import Mobile.Mep.om.VistaIdentificacionTienda;
import Mobile.util.GlobalVariables;
import modelo.BaseExecution;
import azure.annotation.AzureTestInfo;

/**
 * DESCRIPCIÓN: Check that only Cabeceras and Exhibidores have Guided Planograms flow
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC-GP-001-HH_TABLET_GP_EMEP_Exists_only_for_Cabeceras_and_Exhibidores
 *
 * PREREQUISITOS: 1. User must be registered in the system 2. To have the Cr codes of the store: CrPlace: 10MON, CrStore: 50SHC 3. User is on the "Evaluation MEP" screen
 *
 * INSUMOS:
 * -Usuario: 1723163
 * -NIP: 1111
 * CR: 10MON50SHC
 *
 * @author Mariana Avitia
 * @date 09/07/2026
 */
@AzureTestInfo(areaPath = "placeholder", iterationPath = "placeholder", tags = "placeholder1;placeholder2", userStoryId = 1234)
public class ATC_FT_001_GuidedPlanogramsOnlyForCabecerasAndExhibidores extends BaseExecution {
	
	AppiumUtil appiumUtil = null;
    private String appPackage;
    PostgreSQLOffline sqlOffline = null;
    String usernamePromOxxoQA = null;
    String passwordPromOxxoQA = null;
    String crTienda = null;
    

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_GP_001_GuidedPlanogramsOnlyForCabecerasAndExhibidores_test(HashMap<String, String> data) throws Exception {
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
        VistaIdentificacionTienda identifTienda = new VistaIdentificacionTienda(testCase, appiumUtil, this);
        EMEPflow emepFlow = new EMEPflow(testCase, appiumUtil, this);    
        //PRERREQUISITOS --------------------------------------------------------------------------------------
        System.out.println(periodId);
        appiumUtil.driver.activateApp(appPackage + "/" + appActivity);
        identifTienda.LoginApp(crPlaza, crTienda);
        oAuthOff.loginUser(numEmpleado);       
		emepFlow.clickOpcionEvaluacionMep();	
		/**
         * ******************************************************************************
         *  Paso 1
         * ********************************************************************************
         */
        addStep("Click on the \"Cabeceras\" promo space group");
		emepFlow.validarCapturaFotosCabeceras();
		/**
         * ******************************************************************************
         *  Paso 2
         * ********************************************************************************
         */
        addStep("Click on the \"Exhibidores\" promo space group");
        emepFlow.validarCapturaFotosExhibidores();
		/**
         * ******************************************************************************
         *  Paso 3
         * ********************************************************************************
         */
        addStep("Click on the \"Banners\" promo space group");
        assertTrue(emepFlow.validarCapturaFotosBanners(),  "");     
		}

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        // Mismo que PREREQUISITOS en documentación
        return "1. User must be registered in the system 2. To have the Cr codes of the store: CrPlace: 10MON, CrStore: 50SHC 3. User is on the \"Evaluation MEP\" screen";
    }

    @Override
    public String setTestDescription() {
        // Mismo que DESCRIPCIÓN en documentación
        return "Check that only Cabeceras and Exhibidores have Guided Planograms flow";
    }

    @Override
    public String setTestDesigner() {
        // Mismo que @author
        return "";
    }

    @Override
    public String setTestFullName() {
        // Mismo nombre de la clase
        return "ATC_GP_001_GuidedPlanogramsOnlyForCabecerasAndExhibidores";
    }

    @Override
    public String setTestInstanceID() {
        // Dejar null
        return null;
    }
}
