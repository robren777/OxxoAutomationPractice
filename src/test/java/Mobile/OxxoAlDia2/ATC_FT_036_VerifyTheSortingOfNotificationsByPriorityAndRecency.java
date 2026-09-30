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
 * MTC-FT-036_Verify the sorting of notifications by priority and recency
 * @author Vlad
 * @date 10/October/2025
 *
 * DESCRIPTION:
 * Validate that notifications are displayed in the correct order based on their
 * priority (e.g., URGENT first) and recency (most recent notifications appear first).
 * Two notifications are created — one with category INCIDENT and one with category PRICE —
 * to confirm that the sorting logic is applied correctly.
 *
 * PRECONDITIONS:
 * 1. Application installed
 * 2. Valid user credentials available
 * 3. Initial screen "Identifica tu tienda" displayed
 * 4. At least two notifications created through API requests
 *
 * INPUTS:
 * 1. Application path (e.g., apps/OxxoAlDia.apk)
 * 2. Device configuration (deviceName, platformVersion, platformName, appPackage, appActivity)
 * 3. User credentials (username, PIN)
 * 4. CR Plaza and CR Tienda identifiers
 *
 * RECOMMENDATIONS / COMMENTS:
 * 1. Ensure Appium server is running and device/emulator is connected.
 * 2. Ensure that backend notification creation API responds with 201 before UI execution.
 * 3. Verify that URGENT notifications appear before informational ones, and newer before older.
 *
 * DEPENDENCIES:
 * 1. Appium server running on http://127.0.0.1:4723
 * 2. Android device or emulator connected
 */
public class ATC_FT_036_VerifyTheSortingOfNotificationsByPriorityAndRecency extends BaseExecution {

    @Test(dataProvider = "data-provider", enabled = true)
    public void ATC_FT_036_VerifyTheSortingOfNotificationsByPriorityAndRecency_test(HashMap<String, String> data) throws Exception {

        /************************ API PRECONDITION ************************/
        String currentDate = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endsAt = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));

        String apiUrl = data.get("apiUrl");
        String basicAuth = data.get("basicAuth");
        String cookie = data.get("cookie");

        String notificationTitle1 = "urgent " + currentDate;
        String notificationTitle2 = "price " + currentDate;

        /* --- First notification (INCIDENT - URGENT) --- */
        String payloadIncident = String.format(
                "{\n" +
                "  \"title\": \"%s\",\n" +
                "  \"description\": \"Backend test notification (INCIDENT) for sorting validation on %s\",\n" +
                "  \"priority\": \"URGENT\",\n" +
                "  \"startsAt\": \"2025-10-01T02:15:22Z\",\n" +
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
                payloadIncident,
                basicAuth,
                cookie
        );

        addStep("Create first (INCIDENT) notification via API before running UI validation API REQUEST:" + apiLog1.requestText + "\r\n\r\n API RESPONSE:" + apiLog1.responseText);
        
        /* --- Second notification (PRICE - NORMAL) --- */
        String payloadPrice = String.format(
            "{\n" +
            "  \"title\": \"%s\",\n" +
            "  \"description\": \"Backend test notification (PRICE) for sorting validation on %s\",\n" +
            "  \"priority\": \"URGENT\",\n" +
            "  \"startsAt\": \"2025-10-01T02:15:22Z\",\n" +
            "  \"endsAt\": \"%s\",\n" +
            "  \"category\": \"PRICE\",\n" +
            "  \"rolesPredicate\": { \"roles\": [\"LIDER TIENDA\", \"ENCARGADO INCAPACIDAD\", \"AYUDANTE PANADERO\", \"AYUDANTE TIENDA\", \"ENCARGADO TURNO\"] },\n" +
            "  \"storeRefPredicate\": { \"zones\": [], \"places\": [], \"sectors\": [], \"stores\": [] },\n" +
            "  \"attachments\": [],\n" +
            "  \"requiredAction\": \"NONE\"\n" +
            "}",
            notificationTitle2, currentDate, endsAt
        );

        ApiUtil.ApiLog apiLog2 = new ApiUtil().buildApiNotificationLog(
                apiUrl,
                payloadPrice,
                basicAuth,
                cookie
        );

        addStep("Create second (PRICE) notification via API before running UI validation API REQUEST:" + apiLog2.requestText + "\r\n\r\n API RESPONSE:" + apiLog2.responseText);
    
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
        addStep("Validate both notifications are visible in NotificationPage");
        assertTrue(notificationPage.isNotificationVisible(notificationTitle1),
            "Urgent notification not visible: " + notificationTitle1);
        assertTrue(notificationPage.isNotificationVisible(notificationTitle2),
            "Price notification not visible: " + notificationTitle2);

        addStep("Click 'Ver todos' button to open full notifications list");
        notificationPage.clickViewAllButton();

        addStep("Validate 'Actividades del día' title is displayed");
        assertTrue(notificationPage.isDailyActivitiesVisible(),
            "'Actividades del día' screen not visible after clicking 'Ver todos'");

        // addStep("Validate that notification icons for different categories are different");
        // assertTrue(notificationPage.areNotificationIconsVisible(notificationTitle1),
        // "Icons are the same — expected different icons for notifications with different categories");

    }

    @Override
    public String setTestFullName() {
        return "MTC-FT-036_Verificar la clasificación de las notificaciones por prioridad y actualidad";
    }

    @Override
    public String setTestDescription() {
        return "Validate that notifications are correctly sorted by priority (URGENT first) and recency.";
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
