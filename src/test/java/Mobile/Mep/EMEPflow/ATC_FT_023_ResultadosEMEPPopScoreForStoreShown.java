package Mobile.Mep.EMEPflow;
import static org.testng.Assert.assertTrue;
import java.util.HashMap;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.Mep.PromOxxoOffline.om.PostgreSQLOffline;
import Mobile.Mep.PromOxxoOffline.om.VistaOAuth0Offline;
import Mobile.Mep.om.EMEPflow;
import Mobile.Mep.om.VistaIdentificacionTienda;
import Mobile.util.GlobalVariables;
import modelo.BaseExecution;
import utils.AppiumUtil;
import utils.password.PasswordUtil;
import azure.annotation.AzureTestInfo;
import io.appium.java_client.remote.AutomationName;

/**
 * DESCRIPCIÓN: Check that "Calificacion POP" score for the store is shown on the "Resultado EMEP" screen
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *     - MTC-GP-023-HH_TABLET_GP_ResultadosEMEP_POPScore_for_Store_shown
 *
 * PREREQUISITOS: 1. User must be registered in the system 2. To have the Cr codes of the store: CrPlace: 10MON, CrStore: 50SHC 3. User is on the "Resultado EMEP" screen 4. Evaluation for "Cabeceras" promo space group is finished
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
public class ATC_FT_023_ResultadosEMEPPopScoreForStoreShown extends BaseExecution {

	AppiumUtil appiumUtil = null;
    private String appPackage;
    PostgreSQLOffline sqlOffline = null;
    String usernamePromOxxoQA = null;
    String passwordPromOxxoQA = null;
    String crTienda = null;
	
    @Test(dataProvider = "data-provider")
    public void ATC_GP_023_ResultadosEMEPPopScoreForStoreShown_test(HashMap<String, String> data) throws Exception {
    	
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
        emepFlow.clickOpcionResultadoEvaluacionMep();	
        /**
         * *************************************************************************************
         *  Paso 1
         * **************************************************************************************
         */
        addStep("Check that \"Calificacion POP\" score for the store is shown on the \"Resultado EMEP\" screen");
        assertTrue(emepFlow.validarCalificacionPOPCabeceras(),"");
        }

    @Override
    public void beforeTest() {
    }

    @Override
    public String setPrerequisites() {
        return "1. User must be registered in the system 2. To have the Cr codes of the store: CrPlace: 10MON, CrStore: 50SHC 3. User is on the \"Resultado EMEP\" screen 4. Evaluation for \"Cabeceras\" promo space group is finished";
    }

    @Override
    public String setTestDescription() {
        return "Check that \"Calificacion POP\" score for the store is shown on the \"Resultado EMEP\" screen";
    }

    @Override
    public String setTestDesigner() {
        return "Mariana Avitia";
    }

    @Override
    public String setTestFullName() {
        return "ATC_GP_023_ResultadosEMEPPopScoreForStoreShown";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }
}
