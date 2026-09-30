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
 * MTC-FT-150_SelectCEDISAndCancel
 *
 * @author Vlad
 * @date 25/September/2025
 *
 * DESCRIPTION:
 * Validate that the user can select a CEDIS, save it,
 * then cancel or close the selection and return to the "Herramienta de Compra" screen.
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
 * 5. CEDIS Code (e.g., 65134)
 *
 * RECOMMENDATIONS / COMMENTS:
 * - Ensure that the user has permissions to access "Herramienta de Compra".
 * - Validate that at least one CEDIS code is available for selection.
 *
 * DEPENDENCIES:
 * - Appium server running on http://127.0.0.1:4723
 * - Android device or emulator connected
 */

public class ATC_FT_150_SelectCEDISAndCancel extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_150_SelectCEDISAndCancel_test(HashMap<String, String> data) throws Exception {
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

        addStep("Select CEDIS option");
        orderPage.selectCedisOption(data.get("cedisCode"));

        addStep("Click 'Guardar'");
        orderPage.clickGuardar();

        addStep("Click 'Cerrar'");
        orderPage.clickCerrar();

        addStep("Click 'Cancelar'");
        orderPage.clickCancelar();

        addStep("Validate 'Herramienta de Compra' is visible again");
        orderPage.clickCancelarSearch();
        assertTrue(orderPage.isHerramientaCompraVisible(), "'Herramienta de Compra' not visible after cancel");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-150_Change previously selected order";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the user can cancel/close after selecting a CEDIS and return to Herramienta de Compra.";
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
