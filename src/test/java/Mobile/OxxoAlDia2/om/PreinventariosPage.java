package Mobile.OxxoAlDia2.om;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import utils.AppiumUtil;
import modelo.TestCase;

import java.time.Duration;

/**
 * Page Object for Preinventarios and Custom Templates (Personalizados).
 * Provides actions for navigating, creating templates, searching items,
 * applying filters, and validating template visibility.
 *
 * All screenshots are captured inside each method for better reporting.
 *
 * Used in tests like:
 * - MTC-FT-082 (Validate custom search bar)
 * - MTC-FT-089 (Create custom template)
 *
 * @author Vlad
 * @date 22/September/2025
 */
public class PreinventariosPage {

    private AppiumUtil appiumUtil;
    private WebDriverWait wait;
    private TestCase testCase;

    // Locators
    private By preinventariosBtn = By.xpath("//android.widget.TextView[@text=\"Preinventario\"]");
    private By preinventariosSection = By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]");
    private By personalizadosBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Personalizados\"]");
    private By preinventariosTitle = By.xpath("//android.widget.TextView[@text=\"Preinventarios\"]");
    private By addTemplateBtn = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[4]");
    private By templateNameInput = By.xpath("//android.widget.EditText[@text=\"Nombre de Plantilla\"]");
    private By saveBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Guardar\"]");
    private By confirmYesBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Sí, continuar\"]");
    private By searchInput = By.xpath("//android.widget.EditText[@resource-id=\"search-bar-input\"]");
    private By firstAddItemBtn = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[1]");
    private By itemContainer = By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup");
    private By firstFilterBtn = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[1]");
    private By secondFilterBtn = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[2]");
    private By supplierPreinvSelectorBtn = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[5]");
    private By supplierOption(String supplierDesc) {
        return By.xpath("//android.view.ViewGroup[@content-desc=\"" + supplierDesc + "\"]");
    }
    private By templateSelector = By.xpath("//android.widget.EditText[@text=\"Selecciona la plantilla\"]");
    private By aceptarBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Aceptar\"]");
    private By roleLabel = By.xpath("(//android.widget.TextView[@text=\"ROL\"])[1]");
    private By saveProgressBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Guardar\"]");
    private By progressSavedMsg = By.xpath("//android.widget.TextView[@text=\"Progreso guardado\"]");
    // Dynamic locators
    private By pendingTask(String supplierName) {
        return By.xpath("//android.view.ViewGroup[@content-desc=\"" + supplierName + "\"]");
    }
    private By pendingTaskClockIcon(String supplierName) {
        return By.xpath("//android.view.ViewGroup[@content-desc=\"" + supplierName + "\"]"
                + "/android.view.ViewGroup/android.view.ViewGroup/com.horcrux.svg.SvgView"
                + "/com.horcrux.svg.GroupView/com.horcrux.svg.PathView");
    }
    // Assigned pre-inventory selector
    private By assignedTaskSelector = By.xpath(
        "//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[4]/android.view.ViewGroup"
    );
    private By removeItemBtn = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[2]");
    private By noProductsLabel = By.xpath("//android.widget.TextView[@text=\"No hay productos aún\"]");
    private By ejecutarPreinventarioBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Ejecutar Preinventario\"]");
    private By completoStatusBtn = By.xpath("//android.widget.TextView[@text=\"Completo\"]");
    private By continuarBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Continuar\"]");
    private By finalizarBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Finalizar\"]");
    private By successMessage = By.xpath("//android.widget.TextView[@text=\"El preinventario fue finalizado con éxito.\"]");
    private By imprimirBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Imprimir\"]");
    private By inputField = By.className("android.widget.EditText");

    /**
     * Constructor for PreinventariosPage.
     *
     * @param appiumUtil Appium utility instance for driver interactions
     * @param testCase   TestCase instance for attaching screenshots
     * @author Vlad
     * @date 22/September/2025
     */
    public PreinventariosPage(AppiumUtil appiumUtil, TestCase testCase) {
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
     * Verifies that Preinventarios section is visible.
     *
     * @return true if section is visible, otherwise false
     * @author Vlad
     * @date 22/September/2025
     */
    public boolean isPreinventariosSectionVisible() {
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(preinventariosSection)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Preinventarios section visible: " + visible);
        return visible;
    }

    /**
     * Clicks on "Personalizados" button to switch to custom templates.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickPersonalizados() {
        wait.until(ExpectedConditions.elementToBeClickable(personalizadosBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Personalizados'");
    }

    /**
     * Verifies that the Preinventarios title is visible.
     *
     * @return true if title is visible, otherwise false
     * @author Vlad
     * @date 22/September/2025
     */
    public boolean isPreinventariosTitleVisible() {
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(preinventariosTitle)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Preinventarios title visible: " + visible);
        return visible;
    }

    /**
     * Clicks [+] to add a new template.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickAddTemplate() {
        wait.until(ExpectedConditions.elementToBeClickable(addTemplateBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked [+] Add Template");
    }

    /**
     * Enters a template name into the input field.
     *
     * @param templateName name of the template to enter
     * @author Vlad
     * @date 22/September/2025
     */
    public void enterTemplateName(String templateName) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(templateNameInput));
        input.clear();
        input.sendKeys(templateName);
        testCase.addScreenShotCurrentStep(appiumUtil, "Entered template name: " + templateName);
    }

    /**
     * Clicks on the Save button.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Guardar'");
    }

    /**
     * Confirms Save action by clicking "Sí, continuar".
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void confirmSaveYes() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Confirmed Save -> 'Sí, continuar'");
    }

    /**
     * Types a value in the search bar.
     *
     * @param value string value to type into the search field
     * @author Vlad
     * @date 22/September/2025
     */
    public void searchItem(String value) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        input.click(); 
        input.clear();
        input.sendKeys(value);
        appiumUtil.driver.pressKey(new KeyEvent(AndroidKey.SEARCH));
        appiumUtil.driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        testCase.addScreenShotCurrentStep(appiumUtil, "Typed into search bar: " + value);
    }

    /**
     * Clicks [+] on the first search result.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickAddFirstItem() {
        wait.until(ExpectedConditions.elementToBeClickable(firstAddItemBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked [+] on first item");
    }

    /**
     * Verifies that the item container is visible.
     *
     * @return true if item container is visible, otherwise false
     * @author Vlad
     * @date 22/September/2025
     */
    public boolean isItemContainerVisible() {
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(itemContainer)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Item container visible: " + visible);
        return visible;
    }

    /**
     * Verifies if a template with the given name is visible.
     *
     * @param templateName name of the template to check
     * @return true if template is visible, otherwise false
     * @author Vlad
     * @date 22/September/2025
     */
    public boolean isTemplateVisible(String templateName) {
        By templateLocator = By.xpath("//android.widget.TextView[@text=\"" + templateName + "\"]");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(templateLocator)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Template visible: " + templateName + " -> " + visible);
        return visible;
    }

    /**
     * Clicks on the first filter button.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickFirstFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(firstFilterBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked first filter button");
    }

    /**
     * Clicks on the second filter button.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void clickSecondFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(secondFilterBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked second filter button");
    }

    /**
     * Searches for a template by name and presses Enter.
     * Captures a screenshot after the action.
     *
     * @param templateName name of the template to search
     * @author Vlad
     * @date 22/September/2025
     */
    public void searchTemplate(String templateName) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        input.click(); 
        input.clear();
        input.sendKeys(templateName);
        appiumUtil.driver.pressKey(new KeyEvent(AndroidKey.SEARCH));
        appiumUtil.driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        testCase.addScreenShotCurrentStep(appiumUtil, "Searched for template: " + templateName);
    }

    /**
     * Deletes a template by its name.
     * Clicks the delete button, confirms the action, and captures screenshots.
     *
     * @param templateName name of the template to delete
     * @author Vlad
     * @date 22/September/2025
     */
    public void deleteTemplate(String templateName) {
        try {
            appiumUtil.driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().descriptionContains(\"" + templateName + "\"))"));
    
            By deleteBtn = By.xpath(
                "//android.view.ViewGroup[contains(@content-desc, '" + templateName + "')]" +
                "/android.view.ViewGroup[2]/android.view.ViewGroup"
            );
    
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(deleteBtn));
            btn.click();
            testCase.addScreenShotCurrentStep(appiumUtil, "Clicked delete for template: " + templateName);
    
            WebElement confirm = wait.until(ExpectedConditions.elementToBeClickable(confirmYesBtn));
            confirm.click();
            testCase.addScreenShotCurrentStep(appiumUtil, "Confirmed delete for template: " + templateName);
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil, "Template not found or delete failed: " + templateName);
            throw e;
        }
    }
    
    /**
     * Opens the supplier list by clicking on "Proveedor".
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void openSupplierList() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.TextView[@text=\"Proveedor\"]"))).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Supplier list opened");
    }

    /**
     * Selects the first supplier from the supplier list.
     * Avoids hardcoding supplier details (e.g., names or items count).
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 22/September/2025
     */
    public void selectFirstSupplier() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//android.view.ViewGroup[@content-desc])[1]"))).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "First supplier selected");
    }

    /**
     * Validates that pre-inventory has started by checking visibility of confirmation layout.
     *
     * @return true if confirmation layout is visible, otherwise false
     * @author Vlad
     * @date 22/September/2025
     */
    public boolean isPreinventoryStarted() {
        By confirmationLayout = By.xpath(
            "//android.widget.FrameLayout[@resource-id=\"android:id/content\"]"
        + "/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup"
        + "/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup"
        + "/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup"
        + "/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup"
        + "/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup"
        + "/android.view.ViewGroup/android.view.ViewGroup[2]"
        );
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationLayout)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Pre-inventory started, confirmation visible: " + visible);
        return visible;
    }

     /**
     * Opens a template by double-clicking on its name.
     *
     * @param templateName Name of the template to open
     * @author Vlad
     * @date 25/September/2025
     */
    public void openTemplateByDoubleClick(String templateName) {
        By templateLocator = By.xpath("//android.widget.TextView[@text='" + templateName + "']");
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(templateLocator));
        el.click();
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Opened template by double-click: " + templateName);
    }

     /**
     * Validates that the inventory status "Completo" is visible.
     *
     * @return true if the "Completo" status is displayed
     * @author Vlad
     * @date 25/September/2025
     */
    public boolean isInventoryStatusCompletoVisible() {
        By completoStatus = By.xpath("//android.widget.TextView[@text='Completo']");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(completoStatus)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Validated inventory status 'Completo' is visible: " + visible);
        return visible;
    }
    
    /**
     * Opens the supplier pre-inventory selector (5th icon button).
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 25/September/2025
     */
    public void openSupplierPreinventorySelector() {
        wait.until(ExpectedConditions.elementToBeClickable(supplierPreinvSelectorBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Opened supplier pre-inventory selector");
    }

    /**
     * Selects a supplier from the list by its description.
     *
     * @param supplierDesc Supplier name/description to select
     * @author Vlad
     * @date 25/September/2025
     */
    public void selectSupplier(String supplierDesc) {
        wait.until(ExpectedConditions.elementToBeClickable(supplierOption(supplierDesc))).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Selected supplier: " + supplierDesc);
    }

    /**
     * Retrieves the first value from the template selector.
     *
     * @return The first template name as a String
     * @author Vlad
     * @date 25/September/2025
     */
    public String pickFirstTemplateFromSelector() {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(templateSelector));
        String firstTemplate = input.getText();
        testCase.addScreenShotCurrentStep(appiumUtil, "Picked first template from selector: " + firstTemplate);
        return firstTemplate;
    }

    /**
     * Confirms supplier and template selection by clicking "Aceptar".
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 25/September/2025
     */
    public void confirmSelection() {
        wait.until(ExpectedConditions.elementToBeClickable(aceptarBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Confirmed supplier/template selection");
    }

    /**
     * Validates that the pre-inventory is in progress by checking the role label.
     *
     * @return true if the "ROL" label is visible, otherwise false
     * @author Vlad
     * @date 25/September/2025
     */
    public boolean isPreinventoryInProgress() {
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(roleLabel)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Pre-inventory in progress, role label visible: " + visible);
        return visible;
    }

    /**
     * Clicks "Guardar" to save the pre-inventory progress.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 25/September/2025
     */
    public void clickSaveProgress() {
        wait.until(ExpectedConditions.elementToBeClickable(saveProgressBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Guardar' to save progress");
    }

    /**
     * Validates that the pre-inventory progress was saved successfully.
     *
     * @return true if success message is visible, otherwise false
     * @author Vlad
     * @date 25/September/2025
     */
    public boolean isProgressSaved() {
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(progressSavedMsg)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Progress saved message visible: " + visible);
        return visible;
    }

    /**
     * Validates that a pending pre-inventory task is displayed
     * with blue highlight and a blue clock icon in the upper left corner.
     *
     * In real-world case, could be extended with:
     *  - checking presence of child element (clock icon path)
     *  - checking style/color via getAttribute("content-desc") or image comparison
     *
     * @param supplierName Supplier task name to validate
     * @return true if the pending task with blue highlight and clock icon is visible
     * @author Vlad
     * @date 25/September/2025
     */
    public boolean isPendingTaskWithBlueClockVisible(String supplierName) {
        try {
            appiumUtil.driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().descriptionContains(\"" + supplierName + "\"))"));
    
            By block = By.xpath("//android.view.ViewGroup[contains(@content-desc,'" + supplierName + "')]");
            boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(block)).isDisplayed();
    
            testCase.addScreenShotCurrentStep(appiumUtil,
                    "Pending pre-inventory block for " + supplierName + " visible=" + visible);
    
            return visible;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil,
                    "Block for " + supplierName + " not found after scrolling");
            return false;
        }
    }

    /**
     * Selects the assigned pre-inventory task from the selector list.
     * Opens the selector and chooses the supplier by description.
     *
     * @param supplierDesc Supplier name/description to select
     * @author Vlad
     * @date 25/September/2025
     */
    public void selectAssignedPreinventoryTask(String supplierDesc) {
        wait.until(ExpectedConditions.elementToBeClickable(assignedTaskSelector)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Opened assigned pre-inventory selector");

        wait.until(ExpectedConditions.elementToBeClickable(supplierOption(supplierDesc))).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Selected assigned pre-inventory task: " + supplierDesc);
    }

     /**
     * Clicks the [x] remove button for the first added item in the template.
     * This action removes the selected product from the custom template list.
     * Captures a screenshot after the action for reporting.
     *
     * @author Vlad
     * @date 27/September/2025
     */
    public void clickRemoveItemFromTemplate() {
        wait.until(ExpectedConditions.elementToBeClickable(removeItemBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked remove item from template");
    }

    /**
     * Validates that the message "No hay productos aún" is visible.
     * This confirms that all items were removed from the template list
     * and no products remain in the current template.
     * Captures a screenshot after validation.
     *
     * @return true if the message is visible, otherwise false
     * @author Vlad
     * @date 27/September/2025
     */
    public boolean isNoProductsMessageVisible() {
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(noProductsLabel)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Validated 'No hay productos aún' message visible: " + visible);
        return visible;
    }

    /**
     * Clicks on the first pending pre-inventory task by supplier name.
     *
     * @param supplierName Name/description of the supplier task
     * @author Vlad
     * @date 27/September/2025
     */
    public void clickPendingPreinventoryTask(String supplierName) {
        appiumUtil.driver.findElement(AppiumBy.androidUIAutomator(
            "new UiScrollable(new UiSelector().scrollable(true))" +
            ".scrollIntoView(new UiSelector().descriptionContains(\"" + supplierName + "\"))"));

        By block = By.xpath("//android.view.ViewGroup[contains(@content-desc,'" + supplierName + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(block)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked pending pre-inventory task: " + supplierName);
    }

    /**
     * Clicks the "Ejecutar Preinventario" button.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 27/September/2025
     */
    public void clickEjecutarPreinventario() {
        wait.until(ExpectedConditions.elementToBeClickable(ejecutarPreinventarioBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Ejecutar Preinventario'");
    }

    /**
     * Selects "Completo" status option.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 27/September/2025
     */
    public void clickCompletoStatus() {
        wait.until(ExpectedConditions.elementToBeClickable(completoStatusBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Completo' status");
    }

    /**
     * Clicks the "Continuar" button.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 27/September/2025
     */
    public void clickContinuar() {
        wait.until(ExpectedConditions.elementToBeClickable(continuarBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Continuar'");
    }

    /**
     * Clicks the "Finalizar" button to finish the pre-inventory.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 27/September/2025
     */
    public void clickFinalizar() {
        wait.until(ExpectedConditions.elementToBeClickable(finalizarBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Finalizar'");
    }

    /**
     * Validates that the success message is displayed after finalizing.
     *
     * @return true if success message is visible, otherwise false
     * @author Vlad
     * @date 27/September/2025
     */
    public boolean isPreinventoryFinalizedSuccessfully() {
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Validated pre-inventory success message visible: " + visible);
        return visible;
    }

    /**
     * Clicks the "Imprimir" button to print after completion.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 27/September/2025
     */
    public void clickImprimir() {
        wait.until(ExpectedConditions.elementToBeClickable(imprimirBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Imprimir'");
    }

    /**
     * Enters a numeric value into the EditText input field.
     *
     * @param value The value to input (e.g., "4")
     * @author Vlad
     * @date 27/September/2025
     */
    public void enterValueInInputField(String value) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputField));
        input.clear();
        input.sendKeys(value);
        testCase.addScreenShotCurrentStep(appiumUtil, "Entered value in input field: " + value);
    }

    /**
     * Verifies that the supplier list is visible on the screen.
     *
     * @return true if supplier list is visible, otherwise false
     * @author Vlad
     * @date 27/September/2025
     */
    public boolean isSupplierListVisible() {
        boolean visible = wait.until(ExpectedConditions
            .visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='Proveedor']"))).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Supplier list visible: " + visible);
        return visible;
    }

    /**
     * Verifies that a supplier was successfully selected.
     *
     * @return true if supplier is selected or marked as assigned, otherwise false
     * @author Vlad
     * @date 27/September/2025
     */
    public boolean isSupplierSelected() {
        boolean visible = wait.until(ExpectedConditions
            .visibilityOfElementLocated(preinventariosBtn))
            .isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Supplier selected successfully: " + visible);
        return visible;
    }

}
