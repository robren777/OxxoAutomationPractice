package Mobile.OxxoAlDia2;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.OxxoAlDia2.om.LoginScreen;
import Mobile.OxxoAlDia2.om.PreinventariosPage;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * MTC-FT-110_Complete supplier pre-inventor
 * 
 * @author Vlad
 * @date 27/September/2025
 *
 * DESCRIPTION:
 * Validate that the user can open a pending supplier pre-inventory task,
 * execute it completely, mark it as “Completo”, and finalize it successfully.
 *
 * PRECONDITIONS:
 * - Application installed
 * - Valid user logged in
 * - At least one pending supplier pre-inventory task available
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (deviceName, platformVersion, platformName, appPackage, appActivity)
 * 3. User credentials (username, PIN)
 * 4. CR Plaza and CR Tienda identifiers
 * 5. Supplier option (e.g., “Supplier 01”)
 * 6. Input value for pre-inventory completion (e.g., “10”)
 *
 * RECOMMENDATIONS / COMMENTS:
 * 1. Ensure the device is connected and the Appium server is running before execution.
 * 2. The supplier task with a blue clock icon indicates a pending pre-inventory.
 * 3. Wait for confirmation of successful completion before clicking “Imprimir”.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android emulator or physical device properly configured
 */

public class ATC_FT_110_CompleteSupplierPreinventory extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_110_CompleteSupplierPreinventory_test(HashMap<String, String> data) throws Exception {
        /************************ Device initial configuration ************************/
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
        cap.setCapability("deviceName", data.get("deviceName"));
        cap.setCapability("platformVersion", data.get("platformVersion"));
        cap.setCapability("platformName", data.get("platformName"));
        cap.setCapability("appPackage", data.get("appPackage"));
        cap.setCapability("appActivity", data.get("appActivity"));
        cap.setCapability("app", System.getProperty("user.dir") + "/" + data.get("app"));        
        cap.setCapability("chromedriverExecutable", System.getProperty("user.dir") + "/" + data.get("chromedriverExecutable"));
        cap.setCapability("chromedriver_autodownload", Boolean.parseBoolean(data.get("chromedriver_autodownload")));

        String url = "http://127.0.0.1:4723";
        AppiumUtil appiumUtil = null;

        try {
            appiumUtil = new AppiumUtil(url, cap);
        } catch (SessionNotCreatedException e) {
            assertTrue(false, "There was a problem accessing the application.\n" + e.getMessage());
        }
        appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        /************************ LOGIN FLOW ************************/
        String crPlaza = data.get("crPlaza");
        String crTienda = data.get("crTienda");

        testCase.setProject_Name("OxxoAlDia2");

        LoginScreen loginScreen = new LoginScreen(appiumUtil, testCase);

        addStep("Validate initial screen");
        assertTrue(loginScreen.isInitialScreenDisplayed(), "Initial screen not displayed");

        addStep("Enter invalid CR Plaza and CR Tienda");
        loginScreen.enterCRPlaza(crPlaza);
        loginScreen.enterCRTienda(crTienda);

        addStep("Click Siguiente");
        loginScreen.clickSiguiente();

        addStep("Click Siguiente second button");
        loginScreen.clickSiguiente();

        String webviewContext = loginScreen.waitForWebViewContext();

        addStep("Switch to WEBVIEW and login with user");
        loginScreen.loginInWebView(webviewContext, data.get("username"), data.get("pin"));

        addStep("Enter PIN and login");
        loginScreen.switchToNativeApp();

        /************************ TEST FLOW ************************/
        PreinventariosPage preinv = new PreinventariosPage(appiumUtil, testCase);

        addStep("Go to Preinventarios section");
        preinv.clickPreinventarios();

        addStep("Validate at least one pending supplier task is visible with clock icon");
        assertTrue(preinv.isPendingTaskWithBlueClockVisible(data.get("userOption")),
        "No pending supplier pre-inventory task with blue clock icon visible");

        addStep("Click first pending supplier pre-inventory task");
        preinv.clickPendingPreinventoryTask(data.get("userOption"));

        addStep("Click 'Ejecutar Preinventario'");
        preinv.clickEjecutarPreinventario();

        addStep("Click 'Completo' status");
        preinv.clickCompletoStatus();

        addStep("Enter value in input field");
        preinv.enterValueInInputField(data.get("inputValue"));

        addStep("Click 'Continuar'");
        preinv.clickContinuar();

        addStep("Click 'Finalizar'");
        preinv.clickFinalizar();

        addStep("Validate success message is displayed");
        assertTrue(preinv.isPreinventoryFinalizedSuccessfully(), 
            "Success message not visible after finalizing supplier pre-inventory");

        addStep("Click 'Imprimir'");
        preinv.clickImprimir();
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-110_Complete supplier pre-inventory";
    }

    @Override
    public String setTestDescription() {
        return "Verify that the user can complete the supplier pre-inventory and finalize it successfully.";
    }

    @Override
    public String setTestDesigner() {
        return "Vlad";
    }

    @Override
    public String setTestInstanceID() {
        return null;
    }

    @Override
    public String setPrerequisites() {
        return "1. Application installed\n2. Valid user logged in\n3. At least one supplier pre-inventory task available";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
