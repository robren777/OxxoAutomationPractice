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
 * MTC-FT-042_Ensure that the user can sort unread notifications
 * @author Vlad
 * @date 12/October/2025
 *
 * DESCRIPTION:
 * Validate that the user can use the "toggle" filter to sort unread notifications,
 * open one, return to the list, and verify that the notification disappears after marking as read.
 *
 * PRECONDITIONS:
 * 1. Application installed
 * 2. Valid user credentials available
 * 3. Initial screen "Identifica tu tienda" displayed
 * 4. Notification created through API request
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android device or emulator connected
 */
public class ATC_FT_042_EnsureThatTheUserCanSortUnreadNotifications extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_042_EnsureThatTheUserCanSortUnreadNotifications_test(HashMap<String, String> data) throws Exception {

        /************************ API PRECONDITION ************************/
        String currentDate = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endsAt = ZonedDateTime.now().plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));

        String notificationTitle = "test1 " + currentDate;
        String apiUrl = data.get("apiUrl");
        String basicAuth = data.get("basicAuth");
        String cookie = data.get("cookie");

        String payload = String.format(
                "{\n" +
                "  \"title\": \"%s\",\n" +
                "  \"description\": \"Ensure that unread notifications can be filtered, opened, and marked as read (%s)\",\n" +
                "  \"priority\": \"URGENT\",\n" +
                "  \"startsAt\": \"2025-10-01T02:15:22Z\",\n" +
                "  \"endsAt\": \"%s\",\n" +
                "  \"category\": \"INCIDENT\",\n" +
                "  \"storeRefPredicate\": { \"zones\": [], \"places\": [], \"sectors\": [], \"stores\": [] },\n" +
                "  \"attachments\": [],\n" +
                "  \"requiredAction\": \"NONE\"\n" +
                "}",
                notificationTitle, currentDate, endsAt
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

        addStep("Validate notification with today's title is visible");
        assertTrue(notificationPage.isNotificationTitleVisible(notificationTitle),
                "Notification '" + notificationTitle + "' not visible");

        addStep("Click 'Ver todos' button to open full notifications list");
        notificationPage.clickViewAllButton();
        
        addStep("Click toggle filter to show unread notifications only");
        notificationPage.clickToggleTrack();

        addStep("Validate notification still visible after applying unread filter");
        assertTrue(notificationPage.isNotificationTitleVisible(notificationTitle),
                "Notification '" + notificationTitle + "' not visible after toggle filter");

        addStep("Click on the notification to open details");
        notificationPage.clickNotificationByTitle(notificationTitle);

        addStep("Click back to return to the notification list");
        notificationPage.clickBackButton();

        addStep("Validate that the notification is no longer visible (marked as read)");
        assertTrue(notificationPage.isNotificationTitleNotVisible(notificationTitle),
                "Notification '" + notificationTitle + "' still visible after marking as read");

        addStep("Click toggle filter again to hide read notifications");
        notificationPage.clickToggleTrack();

        addStep("Validate notification still visible after returning");
        assertTrue(notificationPage.isNotificationTitleVisible(notificationTitle),
                "Notification not visible after returning from detail view");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-042_ Asegúrese de que el usuario pueda ordenar las notificaciones no leídas";
    }

    @Override
    public String setTestDescription() {
        return "Validate that the user can toggle unread notifications, open and mark them as read.";
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
