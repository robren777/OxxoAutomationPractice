package Mobile.OxxoAlDia2.om;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AppiumUtil;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

import java.time.Duration;
import java.util.Set;

import modelo.TestCase; 

/**
 * Page Object for the Login screen "Identifica tu tienda".
 * Contains only actions and return values (no asserts).
 * Implements explicit waits and uses testCase screenshots for better stability.
 *
 * Used both for:
 * - Valid login flow (other tests, e.g. 035)
 * - Invalid login validation (test 002 with error messages)
 */
public class LoginScreen {

    private AppiumUtil appiumUtil;
    private WebDriverWait wait;
    private TestCase testCase;

    // Locators
    private By titleIdentificaTuTienda = By.xpath("//android.widget.TextView[@text='Identifica tu tienda']");
    private By inputCRPlaza = AppiumBy.androidUIAutomator("new UiSelector().text(\"CR Plaza\")");
    private By inputCRTienda = AppiumBy.androidUIAutomator("new UiSelector().text(\"CR Tienda\")");
    private By btnSiguiente = By.xpath("//android.view.ViewGroup[@content-desc='Siguiente']");
    private By inputUsername = By.xpath("//android.widget.EditText[@resource-id='custom-signInName']");
    private By btnDigitTemplate = By.xpath("//android.widget.Button[@resource-id='btn-%s']");
    private By btnSignIn = By.xpath("//android.widget.Button[@resource-id='custom-signin']");

    // Error texts 
    private String errorTextPlaza;
    private String errorTextTienda;

    public LoginScreen(AppiumUtil appiumUtil, TestCase testCase) {
        this.appiumUtil = appiumUtil;
        this.testCase = testCase;
        this.wait = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(30));
    }

    public LoginScreen(AppiumUtil appiumUtil, TestCase testCase, String errorTextPlaza, String errorTextTienda) {
        this(appiumUtil, testCase);
        this.errorTextPlaza = errorTextPlaza;
        this.errorTextTienda = errorTextTienda;
    }

    /**
     * Verify that the initial screen "Identifica tu tienda" is displayed.
     *
     * @author Vlad
     * @return true if the initial screen is visible
     * @date 21/September/2025
     */
    public boolean isInitialScreenDisplayed() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(titleIdentificaTuTienda));
        boolean displayed = el.isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Initial screen displayed: " + displayed);
        return displayed;
    }

    /**
     * Enter CR Plaza value into the corresponding field.
     *
     * @author Vlad
     * @param value the text to be entered in the CR Plaza input
     * @date 21/September/2025
     */
    public void enterCRPlaza(String value) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(inputCRPlaza));
        el.clear();
        el.sendKeys(value);
        testCase.addScreenShotCurrentStep(appiumUtil, "CR Plaza value entered: " + value);
    }

     /**
     * Switches to WEBVIEW context and performs login with username.
     *
     * @param webviewContext context name for WEBVIEW
     * @param username       user login to input
     * @author Vlad
     * @date 24/September/2025
     */
    public void loginInWebView(String webviewContext, String username, String pin) {
        appiumUtil.driver.context("NATIVE_APP");
        WebDriverWait wait = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(20));

        WebElement userField = wait.until(ExpectedConditions.elementToBeClickable(inputUsername));
        userField.sendKeys(username);
        testCase.addScreenShotCurrentStep(appiumUtil, "Entered username: " + username);

        for (char digit : pin.toCharArray()) {
            String xpath = String.format(btnDigitTemplate.toString().replace("By.xpath: ", ""), digit);
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            btn.click();
        }

        WebElement signInBtn = wait.until(ExpectedConditions.elementToBeClickable(btnSignIn));
        signInBtn.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked on Sign In button");
    }

    /**
     * Switches driver context back to NATIVE_APP.
     * Used after completing WEBVIEW login.
     *
     * @author Vlad
     * @date 24/September/2025
     */
    public void switchToNativeApp() {
        appiumUtil.driver.context("NATIVE_APP");
        testCase.addScreenShotCurrentStep(appiumUtil, "Switched back to NATIVE_APP context");
    }

    /**
     * Enter CR Tienda value into the corresponding field.
     *
     * @author Vlad
     * @param value the text to be entered in the CR Tienda input
     * @date 21/September/2025
     */
    public void enterCRTienda(String value) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(inputCRTienda));
        el.clear();
        el.sendKeys(value);
        testCase.addScreenShotCurrentStep(appiumUtil, "CR Tienda value entered: " + value);
    }

    /**
     * Click on the "Siguiente" button to proceed with the login flow.
     *
     * @author Vlad
     * @date 21/September/2025
     */
    public void clickSiguiente() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(btnSiguiente));
        btn.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Siguiente' button");
    }

    /**
     * Verify that both error messages for CR Plaza and CR Tienda are displayed.
     * Used in negative validation scenarios such as test 002.
     *
     * @author Vlad
     * @return true if both error messages are visible
     * @date 21/September/2025
     */
    public boolean areErrorMessagesDisplayed() {
        WebElement errorPlaza = wait.until(ExpectedConditions.visibilityOfElementLocated(
            AppiumBy.accessibilityId("CR Plaza, El nombre del lugar debe tener 5 caracteres.")));
    
        WebElement errorTienda = wait.until(ExpectedConditions.visibilityOfElementLocated(
            AppiumBy.accessibilityId("CR Tienda, El nombre de la tienda debe tener 5 caracteres.")));
    
        boolean displayed = errorPlaza.isDisplayed() && errorTienda.isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Error messages displayed: " + displayed);
        return displayed;
    }    

    /**
     * Wait for WEBVIEW context and return its name.
     * Used in login flows where webview interaction is required (e.g. test 035).
     *
     * @author Vlad
     * @return the WEBVIEW context name when available
     * @date 21/September/2025
     */
    public String waitForWebViewContext() {
        WebDriverWait waitWebview = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(15));
        return waitWebview.until(driver -> {
            Set<String> contexts = ((AndroidDriver) driver).getContextHandles();
            return contexts.stream().filter(c -> c.contains("WEBVIEW")).findFirst().orElse(null);
        });
    }

    /**
     * Verifies that the home screen (dashboard) is visible after a successful login.
     *
     * @return true if the home screen is visible, otherwise false
     * @author Vlad
     * @date 06/October/2025
     */
    public boolean isHomeScreenDisplayed() {
        By homeScreenLocator = By.xpath("//android.view.ViewGroup[@content-desc=\"Etiqueteo\"]");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(homeScreenLocator)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Home screen visible after login: " + visible);
        return visible;
    }

    /**
     * Validates that the user remains on the login screen after entering an invalid username.
     * The check is based on the visibility of the username input field.
     *
     * @return true if the username input field is visible (login failed), otherwise false
     * @author Vlad
     * @date 06/October/2025
     */
    public boolean isInvalidLoginMessageVisible() {
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(inputUsername)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Invalid login attempt detected — username field still visible: " + visible);
        return visible;
    }

    /**
     * Enters username into the username field.
     *
     * @param username The username to enter
     * @author Vlad
     * @date 07/October/2025
     */
    public void enterUsername(String webviewContext, String username) {
        appiumUtil.driver.context("NATIVE_APP");
        WebDriverWait wait = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(20));

        WebElement userField = wait.until(ExpectedConditions.elementToBeClickable(inputUsername));
        userField.sendKeys(username);
        testCase.addScreenShotCurrentStep(appiumUtil, "Entered username: " + username);
    }

    /**
     * Validates that the username was entered correctly.
     *
     * @param expectedUsername The expected username value
     * @return true if the field contains the expected username, otherwise false
     * @author Vlad
     * @date 07/October/2025
     */
    public boolean isUsernameEntered(String expectedUsername) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputUsername));
        String currentValue = input.getText();
        boolean matches = expectedUsername.equals(currentValue);
        testCase.addScreenShotCurrentStep(appiumUtil, "Username entered matches expected: " + matches);
        return matches;
    }

    /**
     * Clears the username field on the login screen.
     *
     * @author Vlad
     * @date 07/October/2025
     */
    public void clearUsernameField() {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputUsername));
        input.clear();
        testCase.addScreenShotCurrentStep(appiumUtil, "Cleared username field");
    }

    /**
     * Validates that the username field is empty.
     *
     * @return true if field is empty, otherwise false
     * @author Vlad
     * @date 07/October/2025
     */
    public boolean isUsernameFieldEmpty() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(inputUsername));
        String value = input.getText();
        boolean empty = value == null || value.isEmpty();
        testCase.addScreenShotCurrentStep(appiumUtil, "Username field empty: " + empty);
        return empty;
    }

    /**
     * Switches driver context to WEBVIEW for login operations.
     *
     * @param webviewContext The name of the WebView context to switch to
     * @author Vlad
     * @date 07/October/2025
     */
    public void switchToWebView(String webviewContext) {
        appiumUtil.driver.context(webviewContext);
        testCase.addScreenShotCurrentStep(appiumUtil, "Switched to WEBVIEW context: " + webviewContext);
    }

        /**
     * Sends a backend API request to create a notification.
     * Logs both the request and response as screenshot steps for reporting.
     *
     * Used as a precondition before validating notification visibility.
     *
     * @param url     API endpoint for notification creation
     * @param payload JSON body sent in the request
     * @param auth    Authorization header value
     * @param cookie  Cookie header value
     * @throws Exception if the API request fails
     * @author Vlad
     * @date 29/November/2025
     */
        public void apiCreateNotificationStep(
            String url,
            String payload,
            String auth,
            String cookie
    ) throws Exception {

        // Build request log
        StringBuilder reqLog = new StringBuilder();
        reqLog.append("POST ").append(url).append("\n\n");
        reqLog.append("Headers:\n");
        reqLog.append("Content-Type: application/json\n");
        if (auth != null) reqLog.append("Authorization: ").append(auth).append("\n");
        if (cookie != null) reqLog.append("Cookie: ").append(cookie).append("\n");
        reqLog.append("\nPayload:\n").append(payload).append("\n");

        testCase.addScreenShotCurrentStep(appiumUtil, "API REQUEST:\n" + reqLog);

        // Execute API call
        int status = Mobile.util.ApiUtil.postJson(url, payload, auth, cookie);

        // Build response log
        String respLog = "Status: " + status;

        testCase.addScreenShotCurrentStep(appiumUtil, "API RESPONSE:\n" + respLog);
    }
}