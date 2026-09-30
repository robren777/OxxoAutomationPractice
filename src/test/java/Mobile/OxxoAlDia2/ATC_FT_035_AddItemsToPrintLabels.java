package Mobile.OxxoAlDia2;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.OxxoAlDia2.om.LoginScreen;
import Mobile.OxxoAlDia2.om.PlanogramPage;
import Mobile.OxxoAlDia2.om.PrintLabelsPage;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * MTC-FT-035_Agregue elementos a la lista de elementos para imprimir etiquetas escribiendo
 * @author Vlad
 * @date 25/September/2025
 * 
 * DESCRIPTION:
 * Validate that the user can add items to the label printing list by typing.
 *
 * PRECONDITIONS:
 * - Application installed
 * - Valid user
 * - Initial screen "Identifica tu tienda" available
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (deviceName, platformVersion, platformName, appPackage, appActivity)
 * 3. User credentials (username, PIN)
 * 4. CR Plaza and CR Tienda identifiers
 * 5. Search keyword for product (e.g., “Leche”)
 *
 * RECOMMENDATIONS / COMMENTS:
 * 1. Ensure the “Etiqueteo” module is accessible for the logged-in user.
 * 2. Verify the app can switch from WebView to Native context before continuing.
 * 3. This test assumes that typing a valid product name returns at least one result.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android device or emulator connected
 *
 */
public class ATC_FT_035_AddItemsToPrintLabels extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_035_AddItemsToPrintLabels_test(HashMap<String, String> data) throws Exception {
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
        PrintLabelsPage printLabelsPage = new PrintLabelsPage(appiumUtil, testCase);
        PlanogramPage planogramPage = new PlanogramPage(appiumUtil, testCase);

        addStep("Open Etiqueteo section");
        planogramPage.openEtiqueteo();

        addStep("Click Aceptar");
        planogramPage.clickAceptar();

        addStep("Open Planogram");
        planogramPage.openPlanogram();

        addStep("Search and add item");
        printLabelsPage.clickSearchIcon();
        printLabelsPage.typeItem("Leche");
        assertTrue(printLabelsPage.isItemDisplayed("Leche"), "'Leche' not found in the list");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-035_Agregue elementos a la lista de elementos para imprimir etiquetas escribiendo";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the user can add items to the print labels list by typing them into the input field.";
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
        return "1. Application installed\n2. Valid user\n3. Access to 'Print Labels' page";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
