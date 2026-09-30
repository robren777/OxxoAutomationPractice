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
 * MTC-FT-144_Ensure that the ticket contains all mandatory fields
 *
 * @author Vlad
 * @date 27/September/2025
 *
 * DESCRIPTION:
 * Validate that when a user selects a planogroup and product,
 * the ticket preview displays all mandatory fields:
 * Ubicación, Charola, Mueble, Estatus, Planogrupo, SKU, and label-card-preview.
 *
 * PRECONDITIONS:
 * - Application installed
 * - Valid user logged in
 * - Initial screen "Identifica tu tienda" available
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (deviceName, platformVersion, platformName, appPackage, appActivity)
 * 3. User credentials (username, PIN)
 * 4. CR Plaza and CR Tienda identifiers
 *
 * RECOMMENDATIONS / COMMENTS:
 * - Ensure the selected planogroup and product exist in the app.
 * - Verify that the Etiqueteo module is accessible for the user.
 *
 * DEPENDENCIES:
 * - Appium server running on http://127.0.0.1:4723
 * - Connected Android device or emulator
 */

public class ATC_FT_144_TicketContainsMandatoryFields extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_144_TicketContainsMandatoryFields_test(HashMap<String, String> data) throws Exception {
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

        addStep("Open Planogram");
        planogramPage.openPlanogram();

        addStep("Select 'Abarrotes'");
        planogramPage.selectAbarrotes();

        addStep("Select 'Gondola central'");
        planogramPage.selectGondolaCentral();

        addStep("Validate that ticket contains all mandatory fields");
        assertTrue(planogramPage.isMandatoryFieldVisible("Ubicación:"), "Ubicación field missing");
        assertTrue(planogramPage.isMandatoryFieldVisible("Charola:"), "Charola field missing");
        assertTrue(planogramPage.isMandatoryFieldVisible("Mueble:"), "Mueble field missing");
        assertTrue(planogramPage.isMandatoryFieldVisible("Estatus: Activo"), "Estatus field missing or not 'Activo'");
        assertTrue(planogramPage.isMandatoryFieldVisible("Planogrupo: Abarrotes"), "Planogrupo field missing");
        assertTrue(planogramPage.isMandatoryFieldVisible("SKU:"), "SKU field missing");
        assertTrue(planogramPage.isLabelCardPreviewVisible(), "Label card preview not visible");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-144_Ensure that the ticket contains all mandatory fields";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the ticket preview contains all mandatory fields: Ubicación, Charola, Mueble, Estatus, Planogrupo, SKU, and label card preview.";
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
