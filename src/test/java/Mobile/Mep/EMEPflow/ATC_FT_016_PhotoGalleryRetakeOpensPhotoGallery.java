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
 * DESCRIPCIÓN: Check that user is returned to the "Photo gallery" screen after retaking the photo
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC-GP-016-HH_TABLET_GP_PhotoGallery_Retake_opens_PhotoGallery
 *
 * PREREQUISITOS: 1. User must be registered in the system
 * 2. To have the Cr codes of the store:
 * CrPlace: 10MON, CrStore: 50SHC
 * 3. User is on the "Photo gallery" screen 
 * 
 * INSUMOS:
 * -Usuario: 1723163
 * -NIP: 1111
 * CR: 10MON50SHC
 *
 * @author Mariana Avitia Maturino
 * @date 09/07/2026
 */
@AzureTestInfo(areaPath = "placeholder", iterationPath = "placeholder", tags = "placeholder1;placeholder2", userStoryId = 1234)
public class ATC_FT_016_PhotoGalleryRetakeOpensPhotoGallery extends BaseExecution {
	
	AppiumUtil appiumUtil = null;
    private String appPackage;
    PostgreSQLOffline sqlOffline = null;
    String usernamePromOxxoQA = null;
    String passwordPromOxxoQA = null;
    String crTienda = null;
	
	@Test(dataProvider = "data-provider")
    public void ATC_FT_016_PhotoGalleryRetakeOpensPhotoGallery_test(HashMap<String, String> data) throws Exception {
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
         * *************************************************************************************
         *  Paso 1
         * **************************************************************************************
         */
        addStep("Validate that the photo gallery opens after retaking the photo");
        assertTrue(emepFlow.validarReCapturaAlbum(),"");
    }

    @Override
    public String setTestFullName() {
        return "ATC_FT_016_PhotoGalleryRetakeOpensPhotoGallery";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the photo gallery opens after retaking the photo";
    }

    @Override
    public String setTestDesigner() {
        return "Mariana Avitia";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "1. Mobile application installed\n2. Camera screen opened\n3. User has permission to take photos";
    }

    @Override
    public void beforeTest() {
        // Optional
    } 
}