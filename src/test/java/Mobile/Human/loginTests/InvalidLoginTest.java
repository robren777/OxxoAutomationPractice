package Mobile.Human.loginTests;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import utils.AppiumUtil;

/**
 * MTC-FT-002 - Validar errores si los campos CR Plaza y CR Tienda son inválidos
 * Descripción: Después de ingresar valores inválidos en los campos CR, deben mostrarse los mensajes de error correctos.
 * 
 * Requisitos previos:
 * - App instalada
 * - Pantalla inicial "Identifica tu tienda" доступна
 */

public class InvalidLoginTest extends BaseExecution {

    private static final String ERROR_TEXT_PLAZA = "CR Plaza, El nombre del lugar debe tener 5 caracteres.";
    private static final String ERROR_TEXT_TIENDA = "CR Tienda, El nombre de la tienda debe tener 5 caracteres.";

    @Test
    public void MTC_FT_002_showErrorIfCRFieldsInvalid() throws Exception {
        addStep("Inicializar Appium y abrir aplicación");
        AppiumUtil appiumUtil = new AppiumUtil("http://127.0.0.1:4723", getCapabilities());
        appiumUtil.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        testCase.addScreenShotCurrentStep(appiumUtil, "Pantalla Inicial");

        addStep("Validar pantalla 'Identifica tu tienda'");
        assertElementDisplayed(appiumUtil, By.xpath("//android.widget.TextView[@text='Identifica tu tienda']"));
        assertElementDisplayed(appiumUtil, By.xpath("//android.widget.ImageView"));

        addStep("Presionar botón 'Siguiente' без ввода данных");
        clickNextButton(appiumUtil);

        addStep("Ingresar valores inválidos: Plaza=10TL, Tienda=50FL");
        fillCrFields(appiumUtil, "10TL", "50FL");

        clickNextButton(appiumUtil);

        addStep("Validar mensajes de error для CR Plaza и CR Tienda");
        assertErrorDisplayed(appiumUtil, ERROR_TEXT_PLAZA);
        assertErrorDisplayed(appiumUtil, ERROR_TEXT_TIENDA);

        testCase.addScreenShotCurrentStep(appiumUtil, "Errores mostrados");
    }

    private void fillCrFields(AppiumUtil appiumUtil, String plaza, String tienda) {
        WebElement crPlaza = appiumUtil.driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"CR Plaza\")"));
        crPlaza.sendKeys(plaza);

        WebElement crTienda = appiumUtil.driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"CR Tienda\")"));
        crTienda.sendKeys(tienda);
    }

    private void clickNextButton(AppiumUtil appiumUtil) {
        WebElement btn = appiumUtil.driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='Siguiente']"));
        btn.click();
    }

    private void assertErrorDisplayed(AppiumUtil appiumUtil, String errorText) {
        WebElement error = appiumUtil.driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='" + errorText + "']"));
        assertTrue(error.isDisplayed(), "❌ No se mostró el error esperado: " + errorText);
    }

    private void assertElementDisplayed(AppiumUtil appiumUtil, By locator) {
        WebElement element = appiumUtil.driver.findElement(locator);
        assertTrue(element.isDisplayed(), "❌ Elemento no visible: " + locator.toString());
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
        return "MTC_FT_002_showErrorIfCRFieldsInvalid";
    }

    @Override
    public String setTestDescription() {
        return "Validar errores si los campos CR Plaza y CR Tienda son inválidos";
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
        return "1. App instalada\n2. Usuario en pantalla de 'Identifica tu tienda'";
    }

    @Override
    public void beforeTest() {
        // Опционально
    }
}
