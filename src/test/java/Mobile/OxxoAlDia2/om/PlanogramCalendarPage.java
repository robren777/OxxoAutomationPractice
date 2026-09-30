package Mobile.OxxoAlDia2.om;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.AppiumUtil;
import modelo.TestCase;

import java.time.Duration;

/**
 * Page Object for the Calendar / Date selection feature in Preinventarios.
 * Provides methods to select a period and validate product groups are displayed.
 * All steps capture screenshots for reporting.
 *
 * This class follows the Page Object Model standard and is used
 * in test MTC-FT-064 (The user can select a period in the calendar to display
 * the list of product groups on the Date page).
 *
 * @author Vlad
 * @version 1.0
 * @since 22/September/2025
 * @see OxxoAlDia2.ATC_FT_064_SelectPeriodInCalendar
 */
public class PlanogramCalendarPage {

    private AppiumUtil appiumUtil;
    private WebDriverWait wait;
    private TestCase testCase;

    // Locators
    private By preinventariosBtn = By.xpath("//android.widget.TextView[@text=\"Preinventario\"]");
    private By calendarIcon = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[5]");
    private By templateSelector = By.xpath("//android.widget.EditText[@text=\"Selecciona la plantilla\"]");
    private By aceptarBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Aceptar\"]");
    private By productGroupsList = By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[1]/android.view.ViewGroup[2]");

    /**
     * Constructor for PlanogramCalendarPage.
     *
     * @param appiumUtil Appium utility instance for driver interactions
     * @param testCase   TestCase instance for attaching screenshots
     */
    public PlanogramCalendarPage(AppiumUtil appiumUtil, TestCase testCase) {
        this.appiumUtil = appiumUtil;
        this.testCase = testCase;
        this.wait = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(30));
    }

    /**
     * Clicks on "Preinventarios" button to open the section.
     * Scrolls to the element if needed and captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickPreinventarios() {
        // Скроллим до элемента по тексту "Preinventarios"
        wait.until(ExpectedConditions.elementToBeClickable(preinventariosBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Preinventario'");
    }

    /**
     * Clicks the calendar icon (5th icon-button).
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickCalendarIcon() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(calendarIcon));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Calendar icon clicked");
    }

    /**
     * Opens template selector input field.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void openTemplateSelector() {
        // Открываем селектор
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(templateSelector));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Template selector opened");

        // Ждём и кликаем по первому значению "0000000"
        By firstOption = By.xpath("//android.view.ViewGroup[@content-desc=\"009\"]");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(firstOption));
        option.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked first template option: 0000000");
    }

    /**
     * Selects the first available value from the list.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickUserOption(String userDesc) {
        By option = By.xpath("//android.view.ViewGroup[@content-desc=\"" + userDesc + "\"]");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked user option: " + userDesc);
    }   
    
     /**
     * Selects the first available value from the list.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickSupplierOption(String userDesc) {
        By option = By.xpath("//android.widget.TextView[@text='" + userDesc + "']");
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(option));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked supplier option: " + userDesc);
    }    

    /**
     * Confirms selection by clicking "Aceptar".
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickAceptar() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(aceptarBtn));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Aceptar'");
    }

    /**
     * Validates that the product groups list is displayed after selecting period.
     *
     * @return true if product groups list is visible
     * @author Vlad
     * @date 22/September/2025
     */
    public boolean isProductGroupsListDisplayed() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(productGroupsList));
        boolean displayed = el.isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Product groups list displayed: " + displayed);
        return displayed;
    }

    /**
     * Presses the system back button using driver.navigate().
     *
     * @author Vlad
     * @date 01/October/2025
     */
    public void pressSystemBack() {
        appiumUtil.driver.navigate().back();
        testCase.addScreenShotCurrentStep(appiumUtil, "Pressed system BACK (navigate.back)");
    }

}
