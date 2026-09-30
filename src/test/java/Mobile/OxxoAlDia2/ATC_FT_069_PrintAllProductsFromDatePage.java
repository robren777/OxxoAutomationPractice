package Mobile.OxxoAlDia2;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.OxxoAlDia2.om.LoginScreen;
import Mobile.OxxoAlDia2.om.PlanogramPage;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * MTC-FT-069_The user can print all products from all categories on the Date page
 * 
 * @author Vlad
 * @date 27/September/2025
 *
 * DESCRIPTION:
 * Validate that the user can open the “Fecha” section, click “Imprimir todos”
 * without selecting any individual products, and see the print dialog (“Encontrar la impresora”) appear.
 *
 * PRECONDITIONS:
 * - Application installed
 * - Valid user logged in
 * - Access to “Planogram” page
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (deviceName, platformVersion, platformName, appPackage, appActivity)
 * 3. User credentials (username, PIN)
 * 4. CR Plaza and CR Tienda identifiers
 *
 * RECOMMENDATIONS / COMMENTS:
 * 1. Ensure “Fecha” section fully loads before clicking “Imprimir todos”.
 * 2. In emulator mode, the physical printer search window may not open — validation checks the UI element visibility only.
 * 3. Wait 5–10 seconds for the print dialog to appear due to device performance variations.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android emulator or device connected and configured
 */

public class ATC_FT_069_PrintAllProductsFromDatePage extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_069_PrintAllProductsFromDatePage_test(HashMap<String, String> data) throws Exception {
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
        PlanogramPage planogramPage = new PlanogramPage(appiumUtil, testCase);
        addStep("Open Etiqueteo section");
        planogramPage.openEtiqueteo();

        addStep("Click Aceptar");
        planogramPage.clickAceptar();
        
        addStep("Open Fecha section");
        planogramPage.openFechaSection();

        addStep("Click 'Imprimir todos'");
        planogramPage.clickImprimirTodos();

        addStep("Validate that print window is visible");
        assertTrue(planogramPage.isPrintWindowVisible(), "'Encontrar la impresora' not visible after printing");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-069_The user can print all products from all categories on the Date page";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the user can print all products from Fecha section and the print dialog appears.";
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
        return "1. Application installed\n2. Valid user logged in\n3. Access to Planogram page";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
