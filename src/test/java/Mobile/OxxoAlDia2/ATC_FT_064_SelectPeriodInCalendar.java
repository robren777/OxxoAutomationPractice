package Mobile.OxxoAlDia2;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.OxxoAlDia2.om.LoginScreen;
import Mobile.OxxoAlDia2.om.PlanogramCalendarPage;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * MTC-FT-064_Select period in calendar to display product groups on Date page
 * 
 * @author Vlad
 * @date 22/September/2025
 *
 * DESCRIPTION:
 * Validate that the user can select a period in the calendar, confirm with “Aceptar”,
 * and see the corresponding list of product groups displayed on the Date page.
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
 * 5. User option to select from calendar (e.g., “JOSUE REYES FLORES”)
 *
 * RECOMMENDATIONS / COMMENTS:
 * 1. Ensure that the “Preinventarios” module is fully loaded before interacting with the calendar.
 * 2. Wait for UI stability before selecting calendar options.
 * 3. Use stable network connectivity to avoid sync or delay issues.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android emulator or physical device connected
 */
public class ATC_FT_064_SelectPeriodInCalendar extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_064_SelectPeriodInCalendar_test(HashMap<String, String> data) throws Exception {
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
        PlanogramCalendarPage calendarPage = new PlanogramCalendarPage(appiumUtil, testCase);

        addStep("Open Preinventarios section");
        calendarPage.clickPreinventarios();

        addStep("Click calendar icon");
        calendarPage.clickCalendarIcon();

        addStep("Select first value");
        calendarPage.clickUserOption(data.get("userOption"));

        addStep("Open template selector");
        calendarPage.openTemplateSelector();

        addStep("Click Aceptar");
        calendarPage.clickAceptar();

        addStep("Validate product groups list is displayed");
        assertTrue(calendarPage.isProductGroupsListDisplayed(), "Product groups list not visible after selecting period");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-064_Select period in calendar to display product groups on Date page";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the user can select a period in the calendar, confirm with 'Aceptar', and see product groups list.";
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
