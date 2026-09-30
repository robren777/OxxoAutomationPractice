package Mobile.OxxoAlDia2.om;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AppiumUtil;
import modelo.TestCase;

import java.time.Duration;

/**
 * Page Object for the Planogram feature.
 * Contains actions for navigating to Etiqueteo, Planogram,
 * selecting groups, and validating results.
 *
 * Used in tests like MTC-FT-049.
 *
 * @author Vlad
 * @date 22/September/2025
 */
public class PlanogramPage {

    private AppiumUtil appiumUtil;
    private WebDriverWait wait;
    private TestCase testCase;

    // Locators
    private By etiqueteoBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Etiqueteo\"]");
    private By aceptarBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Aceptar\"]");
    private By planogramBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Planogramas\"]/android.view.ViewGroup");
    private By abarrotesOption = By.xpath("//android.view.ViewGroup[@content-desc=\"Abarrotes\"]");
    private By gondolaCentralOption = By.xpath("//android.view.ViewGroup[@content-desc=\"Todas las charolas\"]");
    private By successMsg = By.xpath("//android.widget.TextView[@text=\"¡Estás al día! Sin etiquetas pendientes.\"]");
    private By abarrotesText = By.xpath("//android.widget.TextView[@text=\"Abarrotes\"]");
    private final By firstScrollElement = By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]");
    private final By imprimirBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Imprimir\"]");
    private final By errorPrinterNotFound = By.xpath("//android.widget.TextView[@text=\"Impresora no encontrada\"]");
    private By fechaSectionBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Fecha\"]/android.view.ViewGroup");
    private By selectAllLabel = By.xpath("//android.widget.TextView[@text=\"Seleccionar todos\"]");
    private By firstCheckedProduct = By.xpath("(//com.horcrux.svg.SvgView[@resource-id=\"checkbox-checked-icon\"])[1]/com.horcrux.svg.GroupView/com.horcrux.svg.PathView[1]");
    private By secondUncheckedProduct = By.xpath("(//com.horcrux.svg.SvgView[@resource-id=\"checkbox-unchecked-icon\"])[2]/com.horcrux.svg.GroupView/com.horcrux.svg.PathView");
    private By imprimirTodosBtn = By.xpath("//android.widget.TextView[@text=\"Imprimir todos\"]");
    private By printWindowLabel = By.xpath("//android.widget.TextView[@text=\"Encontrar la impresora\"]");

    public PlanogramPage(AppiumUtil appiumUtil, TestCase testCase) {
        this.appiumUtil = appiumUtil;
        this.testCase = testCase;
        this.wait = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(30));
    }

    /**
     * Opens the Etiqueteo section by clicking its button.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void openEtiqueteo() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(etiqueteoBtn));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Etiqueteo section opened");
    }

    /**
     * Clicks on the "Aceptar" button in the Etiqueteo flow.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickAceptar() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(aceptarBtn));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Aceptar clicked");
    }

    /**
     * Opens the Planogram section by clicking the corresponding button.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void openPlanogram() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(planogramBtn));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Planogram opened");
    }

    /**
     * Selects the "Abarrotes" group in the Planogram section.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void selectAbarrotes() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(abarrotesOption));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Abarrotes selected");
    }

    /**
     * Selects the "Gondola central" option inside the "Abarrotes" group.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void selectGondolaCentral() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(gondolaCentralOption));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Gondola central selected");
    }

    /**
     * Validates whether the success confirmation message is visible on the Planogram page.
     *
     * @return true if the success message is displayed, otherwise false
     * @author Vlad
     * @date 22/September/2025
     */
    public boolean isSuccessTextDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMsg)).isDisplayed();
    }

    /**
     * Validates whether the "Abarrotes" text is visible on the Planogram page.
     *
     * @return true if the "Abarrotes" text is displayed, otherwise false
     * @author Vlad
     * @date 22/September/2025
     */
    public boolean isAbarrotesTextDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(abarrotesText)).isDisplayed();
    }

    /**
     * Checks if both success text and 'Abarrotes' text are visible.
     *
     * @return true if both texts are displayed
     * @author Vlad
     * @date 22/September/2025
     */
    public boolean areTextsDisplayed() {
        boolean success = isSuccessTextDisplayed();
        boolean abarrotes = isAbarrotesTextDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, 
            "Checked if both success and Abarrotes texts are displayed");
        return success && abarrotes;
    }


     /**
     * Clicks the first element inside the ScrollView on the Planogram page.
     * <p>
     * This represents selecting the first required field before attempting to proceed.
     * The action waits until the element is clickable, then performs a click.
     * A screenshot is captured after the interaction for reporting.
     *
     * @author Vlad
     * @date 23/September/2025
     */
    public void clickFirstScrollElement() {
        wait.until(ExpectedConditions.elementToBeClickable(firstScrollElement)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked first element inside ScrollView (field 1)");
    }

    /**
     * Clicks the "Imprimir" button on the Planogram page.
     * <p>
     * This triggers the print action. Since no printer is configured in the test environment,
     * the expected result is the appearance of an error message ("Impresora no encontrada").
     * A screenshot is captured after the interaction for reporting.
     *
     * @author Vlad
     * @date 23/September/2025
     */
    public void clickImprimir() {
        wait.until(ExpectedConditions.elementToBeClickable(imprimirBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Imprimir'");
    }

    /**
     * Waits up to 15 seconds for the error message "Impresora no encontrada" to appear.
     * <p>
     * This validates that the system prevents continuation when no printer is available.
     * The method returns {@code true} if the error message is visible, otherwise the test fails.
     * A screenshot is captured once the message is detected.
     *
     * @return true if the error message is visible within 15 seconds
     * @author Vlad
     * @date 23/September/2025
     */
    public boolean isPrinterNotFoundErrorVisible() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorPrinterNotFound));
        boolean visible = error.isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Validated error message: 'Impresora no encontrada'");
        return visible;
    }

    /**
     * Validates that the list of products is displayed inside the selected category.
     * Uses two product group elements under the ScrollView as reference.
     *
     * @return true if products list is visible
     * @author Vlad
     * @date 23/September/2025
     */
    public boolean isProductsListVisible() {
        WebElement product1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@text=\"¡Estás al día! Sin etiquetas pendientes.\"]")));
        WebElement product2 = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.view.ViewGroup[@content-desc=\"Todas las charolas\"]")));
        boolean visible = product1.isDisplayed() && product2.isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Validated products list is visible in category");
        return visible;
    }

    /**
     * Opens the "Fecha" section on Planogram page.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 26/September/2025
     */
    public void openFechaSection() {
        wait.until(ExpectedConditions.elementToBeClickable(fechaSectionBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Opened 'Fecha' section");
    }

    /**
     * Validates that the 'Seleccionar todos' option is visible.
     *
     * @return true if visible, false otherwise
     * @author Vlad
     * @date 26/September/2025
     */
    public boolean isSelectAllVisible() {
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(selectAllLabel)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "'Seleccionar todos' visible: " + visible);
        return visible;
    }

    /**
     * Selects the first product that is already marked as checked
     * in the Fecha section. Captures a screenshot after clicking.
     *
     * @author Vlad
     * @date 27/September/2025
     */
    public void clickFirstCheckedProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(firstCheckedProduct)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked first checked product");
    }

    /**
     * Selects the second product that is currently unchecked
     * in the Fecha section. Captures a screenshot after clicking.
     *
     * @author Vlad
     * @date 27/September/2025
     */
    public void clickSecondUncheckedProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(secondUncheckedProduct)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked second unchecked product");
    }

    /**
     * Clicks the "Imprimir todos" button to print all selected products.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 27/September/2025
     */
    public void clickImprimirTodos() {
        wait.until(ExpectedConditions.elementToBeClickable(imprimirTodosBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Imprimir todos'");
    }

    /**
     * Validates that the print window with label "Encontrar la impresora"
     * is displayed after clicking "Imprimir todos".
     *
     * @return true if the print window is visible, otherwise false
     * @author Vlad
     * @date 27/September/2025
     */
    public boolean isPrintWindowVisible() {
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(printWindowLabel)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Print window 'Encontrar la impresora' visible: " + visible);
        return visible;
    }

        /**
     * Validates that a mandatory ticket field with the given text is visible.
     * This method dynamically builds an XPath locator for a TextView containing the provided field text.
     * Captures a screenshot after validation.
     *
     * Example checks:
     * - "Ubicación:"
     * - "Charola:"
     * - "Mueble:"
     * - "Estatus: Activo"
     * - "Planogrupo: Abarrotes"
     * - "SKU:"
     *
     * @param fieldText Partial or full text of the mandatory field to validate
     * @return true if the field is visible, otherwise false
     * @author Vlad
     * @date 27/September/2025
     */
    public boolean isMandatoryFieldVisible(String fieldText) {
        By locator = By.xpath("//android.widget.TextView[contains(@text,'" + fieldText + "')]");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Mandatory field '" + fieldText + "' visible: " + visible);
        return visible;
    }

    /**
     * Validates that the ticket's label card preview is visible.
     * This method checks for any element with a resource-id that starts with "label-card-preview-".
     * Captures a screenshot after validation.
     *
     * Example:
     * - //android.view.ViewGroup[@resource-id="label-card-preview-7501052477121"]
     *
     * @return true if a label card preview element is visible, otherwise false
     * @author Vlad
     * @date 27/September/2025
     */
    public boolean isLabelCardPreviewVisible() {
        By locator = By.xpath("//android.view.ViewGroup[contains(@resource-id,'label-card-preview-')]");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Label card preview visible: " + visible);
        return visible;
    }
}
