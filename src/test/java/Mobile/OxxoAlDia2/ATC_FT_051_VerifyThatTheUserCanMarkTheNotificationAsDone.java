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
 * MTC-FT-051_Verify that the user can mark the notification as done
 * @author Vlad
 * @date 14/October/2025
 *
 * DESCRIPTION:
 * Validate that the user can open a notification with the “Mark as done” required action,
 * press the “Marcar Como Realizado” button, and see that the task status updates to “Completado”.
 *
 * PRECONDITIONS:
 * 1. Application installed
 * 2. Valid user credentials available
 * 3. Initial screen "Identifica tu tienda" displayed
 * 4. Notification created through API request with "requiredAction": "MARK_AS_DONE"
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
 * 3. Verify that clicking the “Marcar Como Realizado” button changes the notification status to “Completado”.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android device or emulator connected
 */
public class ATC_FT_051_VerifyThatTheUserCanMarkTheNotificationAsDone extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_051_VerifyThatTheUserCanMarkTheNotificationAsDone_test(HashMap<String, String> data) throws Exception {

        /************************ API PRECONDITION ************************/
        String currentDate = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endsAt = ZonedDateTime.now().plusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));

        String notificationTitle = "test " + currentDate;
        String description = "Verify mark as done functionality for task notifications.";

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
                "  \"category\": \"TASK\",\n" +
                "  \"storeRefPredicate\": { \"zones\": [], \"places\": [], \"sectors\": [], \"stores\": [] },\n" +
                "  \"attachments\": [],\n" +
                "  \"requiredAction\": \"MARK_AS_DONE\"\n" +
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

        addStep("Click on the notification to open expanded task view");
        notificationPage.clickNotificationByTitle(notificationTitle);

        addStep("Validate that 'Marcar Como Realizado' button is visible");
        assertTrue(notificationPage.isMarkAsDoneButtonVisible(),
                "'Marcar Como Realizado' button not visible in expanded modal");

        addStep("Click 'Marcar Como Realizado' button");
        notificationPage.clickMarkAsDoneButton();

        addStep("Click back to return to the notification list again");
        notificationPage.clickBackButton();

        addStep("Validate that 'Completado' status is visible after marking as done");
        assertTrue(notificationPage.isCompletedStatusVisible(),
                "'Completado' status not visible after marking task as done");
    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-051_ Verificar que el usuario pueda marcar como realizada la notificación";
    }

    @Override
    public String setTestDescription() {
        return "Validate that after clicking 'Marcar Como Realizado', the notification status updates to 'Completado'.";
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
        return "1. Application installed\n2. Valid user credentials\n3. Notification with 'MARK_AS_DONE' action created via API before UI validation";
    }

    @Override
    public void beforeTest() {
        // Optional
    }
}
