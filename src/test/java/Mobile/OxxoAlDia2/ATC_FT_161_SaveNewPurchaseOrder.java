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
 * "MTC-FT-161_Save new purchase order
 *
 * @author Vlad
 * @date 24/September/2025
 *
 * DESCRIPTION:
 * Validate that the user can select a CEDIS, input product quantity,
 * and successfully save a new purchase order.
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
 * 5. CEDIS name and code (e.g., “CEDIS Monterrey”, 65134)
 *
 * DEPENDENCIES:
 * - Appium server running on http://127.0.0.1:4723
 * - Android device or emulator connected
 */

public class ATC_FT_161_SaveNewPurchaseOrder extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_161_SaveNewPurchaseOrder_test(HashMap<String, String> data) throws Exception {
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

        addStep("Select CEDIS option by code: " + data.get("cedisCode"));
        orderPage.selectCedisOption(data.get("cedisCode"));

        addStep("Click 'Guardar'");
        orderPage.clickGuardar();

        addStep("Validate modal container (orders list) is displayed");
        assertTrue(orderPage.isModalContainerVisible(), "Modal container not visible after saving CEDIS");

        addStep("Confirm 'Sí' on save dialog");
        orderPage.confirmSaveYes();
        assertTrue(orderPage.isOrdenDeCompraTitleVisible(), "Orden De Compra not visible");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-161_Save new purchase order";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the user can save a new purchase order by selecting a product, entering quantity, and confirming save.";
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
