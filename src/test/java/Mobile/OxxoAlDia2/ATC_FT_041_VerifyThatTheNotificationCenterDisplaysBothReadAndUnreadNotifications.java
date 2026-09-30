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
 * MTC-FT-041_Verify that the Notification Center displays both read and unread notifications
 * @author Vlad
 * @date 11/October/2025
 *
 * DESCRIPTION:
 * Validate that the Notification Center correctly displays both read and unread notifications.
 * The first notification is opened (read), and a second one is created and verified as visible (unread).
 *
 * PRECONDITIONS:
 * 1. Application installed
 * 2. Valid user credentials available
 * 3. Initial screen "Identifica tu tienda" displayed
 * 4. At least two notifications created through API request
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
 * 3. Verify that both read and unread notifications appear in the Notification Center.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android device or emulator connected
 */
public class ATC_FT_041_VerifyThatTheNotificationCenterDisplaysBothReadAndUnreadNotifications extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_041_VerifyThatTheNotificationCenterDisplaysBothReadAndUnreadNotifications_test(HashMap<String, String> data) throws Exception {

        /************************ API PRECONDITION ************************/
        String currentDate = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endsAt = ZonedDateTime.now().plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));

        String apiUrl = data.get("apiUrl");
        String basicAuth = data.get("basicAuth");
        String cookie = data.get("cookie");

        /* --- Notification 1: will be opened (read) --- */
        String notificationTitle1 = "test1 " + currentDate;
        String payload1 = String.format(
                "{\n" +
                "  \"title\": \"%s\",\n" +
                "  \"description\": \"Read notification verification (%s)\",\n" +
                "  \"priority\": \"URGENT\",\n" +
                "  \"startsAt\": \"2025-10-03T02:15:22Z\",\n" +
                "  \"endsAt\": \"%s\",\n" +
                "  \"category\": \"INCIDENT\",\n" +
                "  \"storeRefPredicate\": { \"zones\": [], \"places\": [], \"sectors\": [], \"stores\": [] },\n" +
                "  \"attachments\": [],\n" +
                "  \"requiredAction\": \"NONE\"\n" +
                "}",
                notificationTitle1, currentDate, endsAt
        );

        ApiUtil.ApiLog apiLog1 = new ApiUtil().buildApiNotificationLog(
                apiUrl,
                payload1,
                basicAuth,
                cookie
        );

        addStep("Create first notification (to be read) API REQUEST:" + apiLog1.requestText + "\r\n\r\n API RESPONSE:" + apiLog1.responseText);

        /* --- Notification 2: will remain unread --- */
        String notificationTitle2 = "test2 " + currentDate;
        String payload2 = String.format(
                "{\n" +
                "  \"title\": \"%s\",\n" +
                "  \"description\": \"Unread notification verification (%s)\",\n" +
                "  \"priority\": \"URGENT\",\n" +
                "  \"startsAt\": \"2025-10-02T02:15:22Z\",\n" +
                "  \"endsAt\": \"%s\",\n" +
                "  \"category\": \"INCIDENT\",\n" +
                "  \"storeRefPredicate\": { \"zones\": [], \"places\": [], \"sectors\": [], \"stores\": [] },\n" +
                "  \"attachments\": [],\n" +
                "  \"requiredAction\": \"NONE\"\n" +
                "}",
                notificationTitle2, currentDate, endsAt
        );

        
        ApiUtil.ApiLog apiLog2 = new ApiUtil().buildApiNotificationLog(
                apiUrl,
                payload2,
                basicAuth,
                cookie
        );

        addStep("Create second notification (to remain unread) API REQUEST:" + apiLog2.requestText + "\r\n\r\n API RESPONSE:" + apiLog2.responseText);

        Thread.sleep(5000);

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
        addStep("Validate that first notification is visible");
        assertTrue(notificationPage.isNotificationTitleVisible(notificationTitle1),
                "Notification '" + notificationTitle1 + "' not visible");
        assertTrue(notificationPage.isNotificationTitleVisible(notificationTitle2),
        "Notification '" + notificationTitle2 + "' not visible");

        addStep("Click 'Ver todos' button to open full notifications list");
        notificationPage.clickViewAllButton();

        addStep("Click on the notification to open details");
        notificationPage.clickNotificationByTitle(notificationTitle1);

        addStep("Click back to return to the main screen");
        notificationPage.clickBackButton();

        addStep("Validate that user returned to notification list");
        assertTrue(notificationPage.isUrgenteLabelNotVisible(), "Notification list not visible after returning");

        addStep("Validate that second notification (unread) is still visible");
        assertTrue(notificationPage.isNotificationTitleVisible(notificationTitle2),
                "Unread notification '" + notificationTitle2 + "' not visible in Notification Center");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-041_ Verificar que el Centro de notificaciones muestre notificaciones leídas y no leídas";
    }

    @Override
    public String setTestDescription() {
        return "Validate that both read and unread notifications appear in the Notification Center after interaction.";
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
        return "1. Application installed\n2. Valid user credentials\n3. Two notifications created via API POST before UI validation";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
