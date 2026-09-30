package Mobile.OxxoAlDia2;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.OxxoAlDia2.om.LoginScreen;
import Mobile.OxxoAlDia2.om.PreinventariosPage;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * MTC-FT-089_Crear una nueva plantilla personalizada
 * 
 * @author Vlad
 * @date 21/September/2025
 *
 * DESCRIPTION:
 * Validate that the user can create a new custom template by entering a name, 
 * adding items to it, and successfully saving the template.
 *
 * PRECONDITIONS:
 * - Application installed
 * - Valid user logged in
 * - Access to “Preinventarios” → “Personalizados” section
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (deviceName, platformVersion, platformName, appPackage, appActivity)
 * 3. User credentials (username, PIN)
 * 4. CR Plaza and CR Tienda identifiers
 * 5. Search keyword for adding product to template (e.g., “Leche”)
 *
 * RECOMMENDATIONS / COMMENTS:
 * 1. Ensure the “Personalizados” section loads completely before adding a template.
 * 2. The test dynamically generates a unique template name using the system timestamp.
 * 3. After saving, verify that the new template appears in the template list.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android device or emulator configured and connected
 */

public class ATC_FT_089_CreateCustomTemplate extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_089_CreateCustomTemplate_test(HashMap<String, String> data) throws Exception {
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

        addStep("Go to Preinventarios and Personalizados");
        preinv.clickPreinventarios();
        assertTrue(preinv.isPreinventariosSectionVisible(), "Preinventarios section not visible");
        preinv.clickPersonalizados();
        assertTrue(preinv.isPreinventariosTitleVisible(), "Preinventarios title not visible");

        addStep("Add new template");
        preinv.clickAddTemplate();

        long millis = new Date().getTime();
        String suffix = String.valueOf(millis).substring(String.valueOf(millis).length() - 5);
        String templateName = "test" + suffix;

        preinv.enterTemplateName(templateName);
        preinv.clickSave();
        
        addStep("Search for item and add");
        preinv.searchItem(data.get("searchValue"));
        preinv.clickAddFirstItem();
        assertTrue(preinv.isItemContainerVisible(), "Item container not visible");

        addStep("Save and confirm template");
        preinv.clickSave();
        preinv.confirmSaveYes();

        assertTrue(preinv.isTemplateVisible(templateName), "Template not created");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-089_Crear una nueva plantilla personalizada";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the user can create a new custom template by entering a name, adding items and saving.";
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
        return "1. Application installed\n2. Valid user\n3. Access to Preinventarios page";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
