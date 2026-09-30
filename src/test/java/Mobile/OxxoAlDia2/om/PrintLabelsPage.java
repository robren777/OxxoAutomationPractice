package Mobile.OxxoAlDia2.om;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AppiumUtil;
import modelo.TestCase;

import java.time.Duration;

/**
 * Page Object for the "Print Labels" feature.
 * Provides methods to interact with the Print Labels page,
 * such as opening the page, searching for items, and validating results.
 * All screenshots are captured inside each method for better reporting.
 *
 * This class follows the Page Object Model standard and is used
 * in tests like MTC-FT-035 (Add items to the list for printing by typing).
 *
 * @author Vlad
 * @date 21/September/2025
 */
public class PrintLabelsPage {

    private AppiumUtil appiumUtil;
    private WebDriverWait wait;
    private TestCase testCase;

    // Locators
    private By labelsMenu = By.xpath("//android.view.ViewGroup[@content-desc=\"Print Labels\"]/android.view.ViewGroup");
    private By searchIcon = By.xpath("//android.view.ViewGroup[@resource-id=\"icon-search\"]");
    private By inputField = By.xpath("//android.widget.EditText[@resource-id=\"search-bar-input\"]");

    private By addedItem(String name) {
        return By.xpath("//android.view.ViewGroup[@content-desc=\"" + name + "\"]");
    }

    /**
     * Constructor for PrintLabelsPage.
     *
     * @param appiumUtil Appium utility instance for driver interactions
     * @param testCase   TestCase instance for attaching screenshots
     * @author Vlad
     * @date 21/September/2025
     */
    public PrintLabelsPage(AppiumUtil appiumUtil, TestCase testCase) {
        this.appiumUtil = appiumUtil;
        this.testCase = testCase;
        this.wait = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(10));
    }

    /**
     * Opens the "Print Labels" page from the application menu.
     * Waits until the element is clickable and performs the action.
     * Captures a screenshot after navigation.
     *
     * @author Vlad
     * @date 21/September/2025
     */
    public void openPrintLabels() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(labelsMenu));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Print Labels page opened");
    }

    /**
     * Clicks on the search icon to enable the search input field.
     * Waits until the icon is clickable before performing the action.
     * Captures a screenshot after clicking.
     *
     * @author Vlad
     * @date 21/September/2025
     */
    public void clickSearchIcon() {
        WebElement icon = wait.until(ExpectedConditions.elementToBeClickable(searchIcon));
        icon.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Search icon clicked");
    }

    /**
     * Types a product name into the search input field.
     * Clears any existing text before sending new input.
     * Captures a screenshot with the entered value.
     *
     * @param value The product name to type into the input field
     * @author Vlad
     * @date 21/September/2025
     */
    public void typeItem(String value) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputField));
        input.clear();
        input.sendKeys(value);
        testCase.addScreenShotCurrentStep(appiumUtil, "Typed item: " + value);
    }

    /**
     * Validates if an item with the given name is displayed
     * in the search results list on the Print Labels page.
     * Captures a screenshot with the validation result.
     *
     * @param value The expected item name
     * @return true if the item is displayed, false otherwise
     * @author Vlad
     * @date 21/September/2025
     */
    public boolean isItemDisplayed(String value) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(addedItem(value)));
        boolean displayed = el.isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Item displayed: " + value);
        return displayed;
    }
}
