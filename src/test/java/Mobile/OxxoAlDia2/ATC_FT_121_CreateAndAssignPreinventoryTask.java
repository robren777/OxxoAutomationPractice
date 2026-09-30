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
 * MTC-FT-121_Create and assign a new pre-inventory task
 * 
 * @author Vlad
 * @date 27/September/2025
 *
 * DESCRIPTION:
 * Validate that the user can open the Preinventarios section, assign a new pre-inventory task,
 * and confirm that it starts successfully.
 *
 * PRECONDITIONS:
 * - Application installed
 * - Valid user logged in
 * - Access to “Preinventarios” page
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (deviceName, platformVersion, platformName, appPackage, appActivity)
 * 3. User credentials (username, PIN)
 * 4. CR Plaza and CR Tienda identifiers
 *
 * RECOMMENDATIONS / COMMENTS:
 * 1. Make sure the device is connected and Appium server is running.
 * 2. Ensure the selected supplier is available before assigning a new pre-inventory task.
 * 3. This test repeats task creation to confirm proper assignment handling.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android device or emulator connected and configured
 */

public class ATC_FT_121_CreateAndAssignPreinventoryTask extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_121_CreateAndAssignPreinventoryTask_test(HashMap<String, String> data) throws Exception {
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

        addStep("Go to Preinventarios and validate section");
        preinv.clickPreinventarios();
        assertTrue(preinv.isPreinventariosSectionVisible(), "Preinventarios section not visible");

        addStep("Open supplier list");
        preinv.openSupplierList();
        assertTrue(preinv.isSupplierListVisible(), "Supplier list not visible on second attempt");

        addStep("Select first supplier");
        preinv.selectFirstSupplier();

        addStep("Validate pre-inventory started");
        preinv.clickPreinventarios();
        assertTrue(preinv.isPreinventoryStarted(), "Pre-inventory did not start");

        /************************ REPEAT to ensure task assignment ************************/
        addStep("Open supplier list again");
        preinv.openSupplierList();
        assertTrue(preinv.isSupplierListVisible(), "Supplier list not visible on second attempt");

        addStep("Select first supplier again");
        preinv.selectFirstSupplier();
        assertTrue(preinv.isSupplierSelected(), "Supplier was not selected successfully on second attempt");

        addStep("Validate pre-inventory started again");
        preinv.clickPreinventarios();
        assertTrue(preinv.isPreinventoryStarted(), "Pre-inventory did not start on second attempt");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-121_Create and assign a new pre-inventory task";
    }

    @Override
    public String setTestDescription() {
        return "Ensure that the user can create and assign a new pre-inventory task and validate its execution.";
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
        return "1. Application installed\n2. Valid user logged in\n3. Access to Preinventarios page";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
