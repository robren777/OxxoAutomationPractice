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
 * MTC-FT-159_CreatePurchaseOrder
 *
 * @author Vlad
 * @date 25/September/2025
 *
 * DESCRIPTION:
 * Validate that the user can access "Herramienta de Compra", open the CEDIS filter,
 * search by CEDIS name, select a CEDIS, save, and verify that the orders list is displayed in a modal container.
 *
 * PRECONDITIONS:
 * - Application installed
 * - Valid user logged in
 * - Access to Herramienta de Compra module
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (deviceName, platformVersion, platformName, appPackage, appActivity)
 * 3. User credentials (username, PIN)
 * 4. CR Plaza and CR Tienda identifiers
 * 5. CEDIS Name and Code (e.g., "CEDIS Monterrey", 65134)
 *
 * RECOMMENDATIONS / COMMENTS:
 * - Ensure that the user has access to CEDIS filtering functionality.
 * - Verify that the modal container with order list appears after saving.
 *
 * DEPENDENCIES:
 * - Appium server running on http://127.0.0.1:4723
 * - Android device or emulator connected
 */

public class ATC_FT_159_CreatePurchaseOrder extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_159_CreatePurchaseOrder_test(HashMap<String, String> data) throws Exception {
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

        addStep("Enter search value (CEDIS name): " + data.get("cedisName"));
        orderPage.enterSearchValue(data.get("cedisName"));

        addStep("Select first CEDIS option");
        orderPage.selectCedisOption(data.get("cedisCode"));

        addStep("Click 'Guardar'");
        orderPage.clickGuardar();

        addStep("Validate modal container (orders list) is displayed");
        assertTrue(orderPage.isModalContainerVisible(), "Modal container not visible after saving CEDIS");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-159_Create purchase order";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the user can create a purchase order by selecting a CEDIS by name.";
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
