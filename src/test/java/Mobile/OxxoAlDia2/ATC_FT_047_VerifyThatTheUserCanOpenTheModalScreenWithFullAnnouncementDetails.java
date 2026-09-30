package Mobile.OxxoAlDia2;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import Mobile.OxxoAlDia2.om.LoginScreen;
import Mobile.OxxoAlDia2.om.NotificationPage;
import Mobile.util.ApiUtil;
import io.appium.java_client.remote.AutomationName;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * MTC-FT-047_Verify that the user can open the modal screen with full announcement details
 * @author Vlad
 * @date 14/October/2025
 *
 * DESCRIPTION:
 * Validate that the user can open the modal window displaying the full content of
 * a notification (announcement), including title and description.
 *
 * PRECONDITIONS:
 * 1. Application installed
 * 2. Valid user credentials available
 * 3. Initial screen "Identifica tu tienda" displayed
 * 4. Notification created through API request
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (deviceName, platformVersion, platformName, appPackage, appActivity)
 * 3. User credentials (username, PIN)
 * 4. CR Plaza and CR Tienda identifiers
 *
 * RECOMMENDATIONS / COMMENTS:
 * 1. Ensure Appium server is running and device/emulator is connected.
 * 2. Ensure backend notification API responds with 201 before executing the UI test.
 * 3. Verify that notification modal shows both title and full description.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android device or emulator connected
 */
public class ATC_FT_047_VerifyThatTheUserCanOpenTheModalScreenWithFullAnnouncementDetails extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_047_VerifyThatTheUserCanOpenTheModalScreenWithFullAnnouncementDetails_test(HashMap<String, String> data) throws Exception {

        /************************ API PRECONDITION ************************/
        String currentDate = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endsAt = ZonedDateTime.now().plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));

        String notificationTitle = "test " + currentDate;
        String description = "Return some product with next expirations dates: 12 Nov 2025";
        String apiUrl = data.get("apiUrl");
        String basicAuth = data.get("basicAuth");
        String cookie = data.get("cookie");

        String payload = String.format(
                "{\n" +
                "  \"title\": \"%s\",\n" +
                "  \"description\": \"%s\",\n" +
                "  \"priority\": \"URGENT\",\n" +
                "  \"startsAt\": \"2025-10-01T02:15:22Z\",\n" +
                "  \"endsAt\": \"%s\",\n" +
                "  \"category\": \"INCIDENT\",\n" +
                "  \"storeRefPredicate\": { \"zones\": [], \"places\": [], \"sectors\": [], \"stores\": [] },\n" +
                "  \"attachments\": [],\n" +
                "  \"requiredAction\": \"NONE\"\n" +
                "}",
                notificationTitle, description, endsAt
        );

        ApiUtil.ApiLog apiLog = new ApiUtil().buildApiNotificationLog(
                apiUrl,
                payload,
                basicAuth,
                cookie
        );

        addStep("Create notification event via API before running UI validation. API REQUEST:" + apiLog.requestText + "\r\n\r\n API RESPONSE:" + apiLog.responseText);

        /************************ DEVICE INITIALIZATION ************************/
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
        NotificationPage notificationPage = new NotificationPage(appiumUtil, testCase);

        addStep("Validate initial screen");
        assertTrue(loginScreen.isInitialScreenDisplayed(), "Initial screen not displayed");

        addStep("Enter CR Plaza and CR Tienda");
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
        addStep("Validate that notification with today's title is visible");
        assertTrue(notificationPage.isNotificationTitleVisible(notificationTitle),
                "Notification with title '" + notificationTitle + "' not visible on screen");

        addStep("Click 'Ver todos' button to open full notifications list");
        notificationPage.clickViewAllButton();

        addStep("Click on the notification to open modal with details");
        notificationPage.clickNotificationByTitle(notificationTitle);

        addStep("Validate that modal screen shows full announcement details");
        assertTrue(notificationPage.isNotificationModalVisible(notificationTitle, description),
                "Notification modal content mismatch or not visible");

    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-047_Verificar que el usuario pueda abrir la pantalla modal con todos los detalles del anuncio";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the user can open a notification modal and see the full title and description from backend data.";
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
        return "1. Application installed\n2. Valid user credentials\n3. Notification created via API POST before UI validation";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
