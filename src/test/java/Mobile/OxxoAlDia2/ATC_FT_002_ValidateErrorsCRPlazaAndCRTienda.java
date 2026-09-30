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
 * MTC-FT-002_Validar errores si los campos CR Plaza y CR Tienda son inválidos
 * @author Vlad
 * @date 17/September/2025
 * 
 * DESCRIPTION:
 * After entering invalid values in CR fields,
 * the correct error messages must be displayed.
 *
 * PRECONDITIONS:
 * 1. Application installed on device or emulator.
 * 2. The user is on the "Identifica tu tienda" screen.
 * 3. The device or emulator is connected to Appium server (localhost:4723).
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (name, platform version, platform name, app package, app activity)
 * 3. CR Plaza (invalid value to test validation)
 * 4. CR Tienda (invalid value to test validation)
 *
 * RECOMMENDATIONS / COMMENTS:
 * 1. Use invalid values shorter than 5 characters to trigger validation messages.
 * 2. Make sure the app is freshly opened before running the test.
 * 3. Chromedriver is not required for this flow since no WebView is used.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android device or emulator connected
 *
 */
public class ATC_FT_002_ValidateErrorsCRPlazaAndCRTienda extends BaseExecution {

    private static final String ERROR_TEXT_PLAZA = "CR Plaza, El nombre del lugar debe tener 5 caracteres.";
    private static final String ERROR_TEXT_TIENDA = "CR Tienda, El nombre de la tienda debe tener 5 caracteres.";


    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_002_ValidateErrorsCRPlazaAndCRTienda_test(HashMap<String, String> data) throws Exception {
        /************************ Device initial configuration ************************/
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("appium:automationName", AutomationName.ANDROID_UIAUTOMATOR2);
        cap.setCapability("deviceName", data.get("deviceName"));
        cap.setCapability("platformVersion", data.get("platformVersion"));
        cap.setCapability("platformName", data.get("platformName"));
        cap.setCapability("appPackage", data.get("appPackage"));
        cap.setCapability("appActivity", data.get("appActivity"));
        cap.setCapability("app", System.getProperty("user.dir") + "/" + data.get("app"));        

        String url = "http://127.0.0.1:4723";

        addStep("Access the application");
        AppiumUtil appiumUtil = null;
        try {
            appiumUtil = new AppiumUtil(url, cap);
        } catch (SessionNotCreatedException e) {
            assertTrue(false, "There was a problem accessing the application.\n" + e.getMessage());
            e.printStackTrace();
        }
        testCase.addScreenShotCurrentStep(appiumUtil, "Start - App opened");

        appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        /************************ Use Page Object ************************/
        String crPlaza = data.get("crPlaza");
        String crTienda = data.get("crTienda");

        testCase.setProject_Name("OxxoAlDia2");

        LoginScreen loginScreen = new LoginScreen(appiumUtil, testCase, ERROR_TEXT_PLAZA, ERROR_TEXT_TIENDA);

        addStep("Validate initial screen");
        assertTrue(loginScreen.isInitialScreenDisplayed(), "Initial screen not displayed");

        addStep("Enter invalid CR Plaza and CR Tienda");
        loginScreen.enterCRPlaza(crPlaza);
        loginScreen.enterCRTienda(crTienda);

        addStep("Click Siguiente");
        loginScreen.clickSiguiente();

        addStep("Validate error messages");
        assertTrue(loginScreen.areErrorMessagesDisplayed(), "Error messages not displayed");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-002_Validar errores si los campos CR Plaza y CR Tienda son inválidos";
    }

    @Override
    public String setTestDescription() {
        return "Validate error messages when CR Plaza and CR Tienda fields are invalid";
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
        return "1. Application installed\n2. User on 'Identifica tu tienda' screen";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
