package Mobile.OxxoAlDia2;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.OxxoAlDia2.om.LoginScreen;
import Mobile.OxxoAlDia2.om.PlanogramCalendarPage;
import Mobile.OxxoAlDia2.om.PreinventariosPage;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * MTC-FT-122_Pending pre-inventory tasks visual verification
 * 
 * @author Vlad
 * @date 25/September/2025
 *
 * DESCRIPTION:
 * Validate that pending supplier pre-inventory tasks assigned to the user
 * are displayed with the expected blue background and blue clock icon in the upper-left corner.
 *
 * PRECONDITIONS:
 * - Application installed
 * - Valid user logged in
 * - At least one pending pre-inventory task assigned
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (deviceName, platformVersion, platformName, appPackage, appActivity)
 * 3. User credentials (username, PIN)
 * 4. CR Plaza and CR Tienda identifiers
 * 5. Supplier option for validation (e.g., “Supplier 1”)
 *
 * RECOMMENDATIONS / COMMENTS:
 * 1. Make sure the supplier task is already assigned before test execution.
 * 2. Ensure Appium server is running and device is connected.
 * 3. This test verifies only visual indicators, not business logic.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android device or emulator configured
 */

public class ATC_FT_122_PendingPreinventoryTasksVisual extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_122_PendingPreinventoryTasksVisual_test(HashMap<String, String> data) throws Exception {
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
        PlanogramCalendarPage calendarPage = new PlanogramCalendarPage(appiumUtil, testCase);

        addStep("Go to Preinventarios section");
        preinv.clickPreinventarios();

        addStep("Click calendar icon");
        calendarPage.clickCalendarIcon();

        addStep("Select first value");
        calendarPage.clickSupplierOption(data.get("userOption"));

        addStep("Open template selector");
        calendarPage.openTemplateSelector();

        addStep("Click Aceptar");
        calendarPage.clickAceptar();

        addStep("Validate that pending pre-inventory task is visible with blue background and clock icon");
        assertTrue(preinv.isPendingTaskWithBlueClockVisible(data.get("userOption")),
            "Pending task with blue clock icon not visible for supplier: " + data.get("userOption"));
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-122_Pending pre-inventory tasks visual verification";
    }

    @Override
    public String setTestDescription() {
        return "Ensure that assigned pending pre-inventory tasks are displayed with blue background and blue clock icon.";
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
        return "1. Application installed\n2. Valid user logged in\n3. At least one pending pre-inventory task assigned";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
