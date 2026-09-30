package Mobile.OxxoAlDia2;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.OxxoAlDia2.om.LoginScreen;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * MTC-FT-006_El usuario puede eliminar su nombre de usuario desde la página de inicio de sesión
 * @author Vlad
 * @date 07/October/2025
 * 
 * DESCRIPTION:
 * Validate that the user can remove (clear) the username field on the login page
 * and verify that the field becomes empty.
 *
 * PRECONDITIONS:
 * - Application installed
 * - Valid login screen available
 * - Username field contains a pre-filled value
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (deviceName, platformVersion, platformName, appPackage, appActivity)
 * 3. User credentials (PIN only, username field will be cleared)
 * 4. CR Plaza and CR Tienda identifiers
 *
 * RECOMMENDATIONS / COMMENTS:
 * 1. Ensure Appium server is running and device/emulator is connected.
 * 2. Make sure the login page is loaded before performing the clear action.
 * 3. The test only verifies UI clearing behavior (no login submission).
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android device or emulator connected
 *
 */
public class ATC_FT_006_RemoveUsernameFromLoginPage extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_006_RemoveUsernameFromLoginPage_test(HashMap<String, String> data) throws Exception {
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

        /************************ LOGIN SCREEN ************************/
        testCase.setProject_Name("OxxoAlDia2");

        LoginScreen loginScreen = new LoginScreen(appiumUtil, testCase);

        addStep("Validate initial screen is visible");
        assertTrue(loginScreen.isInitialScreenDisplayed(), "Initial screen not displayed");

        addStep("Enter CR Plaza and CR Tienda");
        loginScreen.enterCRPlaza(data.get("crPlaza"));
        loginScreen.enterCRTienda(data.get("crTienda"));

        addStep("Click Siguiente");
        loginScreen.clickSiguiente();

        addStep("Click Siguiente second button");
        loginScreen.clickSiguiente();

        String webviewContext = loginScreen.waitForWebViewContext();

        addStep("Enter username into the username field: " + data.get("username"));
        loginScreen.enterUsername(webviewContext, data.get("username"));

        addStep("Validate username is entered correctly");
        assertTrue(loginScreen.isUsernameEntered(data.get("username")),
                "Entered username not visible or incorrect");

        addStep("Clear the username field");
        loginScreen.clearUsernameField();

        addStep("Validate that username field is empty after clearing");
        assertTrue(loginScreen.isUsernameFieldEmpty(), "Username field is not empty after clearing");    
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-006_Remove username from login page";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the user can clear the username field and confirm it is empty.";
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
        return "1. Application installed\n2. Access to login screen\n3. Username field contains text";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
