package Mobile.OxxoAlDia2;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.OxxoAlDia2.om.LoginScreen;
import Mobile.OxxoAlDia2.om.OrderPage;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * MTC-FT-149 – Validate CEDIS selection and order list retrieval
 * @author Vlad
 * @date 24/September/2025
 *
 * Description:
 * Validate that the user can access the "Herramienta de Compra" section,
 * open the CEDIS filter, search by code, select a CEDIS, save it,
 * and verify that the orders list is displayed in a modal container.
 *
 * Preconditions:
 * 1. The mobile application must be installed on the device or emulator.
 * 2. A valid test user must be available (username and PIN).
 * 3. Access to the “Herramienta de Compra” feature is required.
 * 4. Device or emulator must have a stable connection to the QA environment.
 *
 * Inputs:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Login credentials (username, PIN)
 * 3. CR Plaza and CR Tienda values for login
 * 4. CEDIS Code (used for search and selection)
 *
 * Recommendations / Comments:
 * 1. Verify manually that the user can log in before executing the script.
 * 2. Ensure chromedriver compatibility if the test switches to WebView.
 * 3. Recommended Android version: 12.0 or higher.
 *
 * Dependencies:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Connected Android device or emulator
 * 3. Access to QA environment backend APIs
 */

public class ATC_FT_149_SelectCEDISAndRetrieveOrders extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_149_SelectCEDISAndRetrieveOrders_test(HashMap<String, String> data) throws Exception {
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
        OrderPage orderPage = new OrderPage(appiumUtil, testCase);

        addStep("Open 'Herramienta de Compra'");
        orderPage.openHerramientaCompra();

        addStep("Validate user is on 'Todos' screen");
        assertTrue(orderPage.isTodosScreenVisible(), "'Todos' screen not visible");

        addStep("Click filter icon");
        orderPage.clickFilterIcon();

        addStep("Enter search value: " + data.get("cedisCode"));
        orderPage.enterSearchValue(data.get("cedisCode"));

        addStep("Select first CEDIS option");
        orderPage.selectCedisOption(data.get("cedisCode"));

        addStep("Click 'Guardar'");
        orderPage.clickGuardar();

        addStep("Validate modal container (orders list) is displayed");
        assertTrue(orderPage.isModalContainerVisible(), "Modal container not visible after saving CEDIS");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-149_Select one of the CEDIS and retrieve order list";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the user can select a CEDIS, save it, and retrieve the orders list in modal container.";
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
        return "1. Application installed\n2. Valid user logged in\n3. Access to Herramienta de Compra";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
