package Mobile.OxxoAlDia2;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import modelo.BaseExecution;
import utils.AppiumUtil;

public class LoginSuccessLogoutTest extends BaseExecution {

    @Test
    public void MTC_FT_004_loginAndLogout() throws Exception {
        AppiumUtil appiumUtil = new AppiumUtil("http://127.0.0.1:4723", getCapabilities());
        appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        testCase.addScreenShotCurrentStep(appiumUtil, "Inicio - App abierta");

        addStep("Verificar pantalla 'Identifica tu tienda'");
        assertElementDisplayed(appiumUtil, By.xpath("//android.widget.TextView[@text='Identifica tu tienda']"));
        assertElementDisplayed(appiumUtil, By.xpath("//android.widget.ImageView"));

        addStep("Ingresar CR Plaza = 10TLC y CR Tienda = 50FLV");
        sendKeys(appiumUtil, AppiumBy.androidUIAutomator("new UiSelector().text(\"CR Plaza\")"), "10TLC");
        sendKeys(appiumUtil, AppiumBy.androidUIAutomator("new UiSelector().text(\"CR Tienda\")"), "50FLV");

        clickNext(appiumUtil);

        addStep("Esperar изображение на дашборде");
        By image1Locator = By.xpath("(//android.widget.ScrollView/android.view.ViewGroup/android.widget.ImageView)[1]");
        WebElement image1 = waitUntilVisible(appiumUtil, image1Locator);
        assertTrue(image1.isDisplayed(), "Image1 не отображается");

        addStep("Нажать 'Siguiente' повторно");
        clickNext(appiumUtil);

        addStep("Переключение в WEBVIEW и ввод логина");
        String webviewContext = waitForWebViewContext(appiumUtil);
        appiumUtil.driver.context(webviewContext);
        sendKeys(appiumUtil, By.id("username"), "1723163");
        appiumUtil.driver.pressKey(new KeyEvent(AndroidKey.ENTER));

        addStep("Нажать 4 раза 'btn-1'");
        WebElement btn1 = appiumUtil.driver.findElement(By.id("btn-1"));
        for (int i = 0; i < 4; i++) btn1.click();

        addStep("Нажать 'btn-next'");
        WebElement btnNext = appiumUtil.driver.findElement(By.id("btn-next"));
        btnNext.click();

        addStep("Подтвердить дашборд и разлогиниться");
        appiumUtil.driver.context("NATIVE_APP");

        assertElementDisplayed(appiumUtil, By.xpath("//android.view.ViewGroup[@resource-id='dashboardContainer']"));

        WebElement menuBurger = appiumUtil.driver.findElement(By.xpath("//android.view.ViewGroup[@resource-id='icon-button']"));
        menuBurger.click();

        WebElement logOutButton = appiumUtil.driver.findElement(By.xpath("(//android.view.ViewGroup[@resource-id='icon-button'])[2]"));
        logOutButton.click();

        WebDriverWait confirmWait = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(10));
        WebElement confirmLogout = confirmWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc='Cerrar sesión']")));
        confirmLogout.click();

        assertElementDisplayed(appiumUtil, By.xpath("//android.view.ViewGroup[@resource-id='dashboardContainer']/android.view.ViewGroup[1]"));
        assertElementDisplayed(appiumUtil, By.xpath("//android.view.ViewGroup[@content-desc='Jose Eduardo Cruz Mendez, ENCARGADO TURNO']"));

        testCase.addScreenShotCurrentStep(appiumUtil, "Logout exitoso");
    }

    private void sendKeys(AppiumUtil appiumUtil, By locator, String value) {
        WebElement el = appiumUtil.driver.findElement(locator);
        el.sendKeys(value);
    }

    private void clickNext(AppiumUtil appiumUtil) {
        WebElement btn = appiumUtil.driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='Siguiente']"));
        btn.click();
    }

    private WebElement waitUntilVisible(AppiumUtil appiumUtil, By locator) {
        WebDriverWait wait = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void assertElementDisplayed(AppiumUtil appiumUtil, By locator) {
        WebElement element = appiumUtil.driver.findElement(locator);
        assertTrue(element.isDisplayed(), "❌ Elemento no visible: " + locator.toString());
    }

    private String waitForWebViewContext(AppiumUtil appiumUtil) {
        WebDriverWait wait = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(15));
        return wait.until(driver -> {
            Set<String> contexts = ((AndroidDriver) driver).getContextHandles();
            return contexts.stream().filter(c -> c.contains("WEBVIEW")).findFirst().orElse(null);
        });
    }

    private org.openqa.selenium.remote.DesiredCapabilities getCapabilities() {
        org.openqa.selenium.remote.DesiredCapabilities cap = new org.openqa.selenium.remote.DesiredCapabilities();
        cap.setCapability("appium:automationName", "UiAutomator2");
        cap.setCapability("deviceName", "Pixel_6_API_30");
        cap.setCapability("platformVersion", "12.0");
        cap.setCapability("platformName", "Android");
        cap.setCapability("appPackage", "your.app.package");
        cap.setCapability("appActivity", "your.app.MainActivity");
        return cap;
    }

    @Override
    public String setTestFullName() {
        return "MTC_FT_004_loginAndLogout";
    }

    @Override
    public String setTestDescription() {
        return "Login, dashboard verification, and logout from mobile app";
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
        return "1. App instalada\n2. Usuario válido\n3. Acceso a WEBVIEW";
    }

    @Override
    public void beforeTest() {
        // Optional pre-test setup
    }
}

