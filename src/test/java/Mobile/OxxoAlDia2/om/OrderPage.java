package Mobile.OxxoAlDia2.om;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AppiumUtil;
import modelo.TestCase;

import java.time.Duration;

/**
 * Page Object for Order (Herramienta de Compra - CEDIS selection and orders list).
 * Encapsulates all actions and validations for selecting a CEDIS and retrieving orders.
 *
 * @author Vlad
 * @date 24/September/2025
 */
public class OrderPage {

    private AppiumUtil appiumUtil;
    private TestCase testCase;
    private WebDriverWait wait;

    // Locators
    private By herramientaCompraBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Herramienta de Compra\"]");
    private By todosScreen = By.xpath("//android.view.ViewGroup[@content-desc=\"Todos\"]");
    private By todosLabel = By.xpath("(//android.widget.TextView[@text=\"Todos\"])[1]");
    private By filterIcon = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[2]");
    private By searchInput = By.xpath("//android.widget.EditText[@resource-id=\"search-bar-input\"]");
    private By cedisOption = By.xpath("//android.widget.TextView[@text=\"Fuente de abasto: 65134\"]");
    private By guardarBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Guardar\"]");
    private By modalContainer = By.xpath("//android.view.ViewGroup[@resource-id=\"modal-container\"]");

    /**
     * Constructor for OrderPage.
     *
     * @param appiumUtil Appium utility instance for driver interactions
     * @param testCase   TestCase instance for attaching screenshots
     */
    public OrderPage(AppiumUtil appiumUtil, TestCase testCase) {
        this.appiumUtil = appiumUtil;
        this.testCase = testCase;
        this.wait = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(30));
    }

    /**
     * Opens the "Herramienta de Compra" section.
     * Waits until the element is clickable, clicks it,
     * and captures a screenshot for reporting.
     * @author Vlad
     * @date 24/September/2025
     */
    public void openHerramientaCompra() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(herramientaCompraBtn));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Opened 'Herramienta de Compra'");
    }

    /**
     * Validates that the user is on the "Todos" screen.
     * Checks visibility of both the screen container and the label.
     * @return true if both elements are visible
     * @author Vlad
     * @date 24/September/2025
     */
    public boolean isTodosScreenVisible() {
        boolean screenVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(todosScreen)).isDisplayed();
        boolean labelVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(todosLabel)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "'Todos' screen visible: " + screenVisible + ", label visible: " + labelVisible);
        return screenVisible && labelVisible;
    }

    /**
     * Clicks on the filter icon (second icon-button).
     * Used to open the CEDIS filter modal.
     * @author Vlad
     * @date 24/September/2025
     */
    public void clickFilterIcon() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(filterIcon));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked filter icon (2nd icon-button)");
    }

    /**
     * Enters a search value (CEDIS code) into the input field.
     * Clears the field before typing and captures screenshot.
     * @param value The CEDIS code to type
     * @author Vlad
     * @date 24/September/2025
     */
    public void enterSearchValue(String value) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        input.clear();
        input.sendKeys(value);
        testCase.addScreenShotCurrentStep(appiumUtil, "Entered value in search: " + value);
    }

    /**
     * Selects the CEDIS option from the results list using the given code.
     *
     * @param cedisCode the CEDIS code (e.g., "65134")
     * @author Vlad
     * @date 24/September/2025
     */
    public void selectCedisOption(String cedisCode) {
        By option = By.xpath("//android.widget.TextView[@text=\"Fuente de abasto: " + cedisCode + "\"]");
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(option));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Selected CEDIS option: " + cedisCode);
    }

    /**
     * Clicks the "Guardar" button in the CEDIS selection modal.
     * Confirms the chosen CEDIS for order retrieval.
     * @author Vlad
     * @date 24/September/2025
     */
    public void clickGuardar() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(guardarBtn));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Guardar'");
    }

    /**
     * Validates that the modal container (orders list) is visible.
     * Waits for the modal and confirms its display.
     * @return true if modal container is visible
     * @author Vlad
     * @date 24/September/2025
     */
    public boolean isModalContainerVisible() {
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(modalContainer)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "Modal container visible: " + visible);
        return visible;
    }

        /**
     * Clicks on a product by its content-desc (e.g., "4").
     *
     * @param productDesc content-desc value of the product
     * @author Vlad
     * @date 24/September/2025
     */
    public void clickProductByDesc(String productDesc) {
        By product = By.xpath("//android.view.ViewGroup[@content-desc=\"" + productDesc + "\"]");
        wait.until(ExpectedConditions.elementToBeClickable(product)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked product with content-desc: " + productDesc);
    }

    /**
     * Clicks on a quantity input field by its text value (e.g., "4").
     *
     * @param value text value of the input field to click
     * @author Vlad
     * @date 24/September/2025
     */
    public void clickQuantityField(String value) {
        By inputField = By.xpath("//android.widget.EditText[@text=\"" + value + "\"]");
        wait.until(ExpectedConditions.elementToBeClickable(inputField)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked quantity input field with value: " + value);
    }

    /**
     * Enters a numeric quantity into the currently active input field.
     *
     * @param quantity value to input (e.g., "5")
     * @author Vlad
     * @date 24/September/2025
     */
    public void enterQuantity(String quantity) {
        By activeInput = By.className("android.widget.EditText");
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(activeInput));
        input.clear();
        input.sendKeys(quantity);
        testCase.addScreenShotCurrentStep(appiumUtil, "Entered quantity: " + quantity);
    }

    /**
     * Clicks the "Continuar" button to proceed with the order flow.
     *
     * @author Vlad
     * @date 24/September/2025
     */
    public void clickContinuar() {
        By continuarBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Continuar\"]");
        wait.until(ExpectedConditions.elementToBeClickable(continuarBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Continuar'");
    }

    /**
     * Confirms the save action by clicking "Sí" in the confirmation dialog.
     *
     * @author Vlad
     * @date 24/September/2025
     */
    public void confirmSaveYes() {
        By yesBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Sí\"]");
        wait.until(ExpectedConditions.elementToBeClickable(yesBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Confirmed save with 'Sí'");
    }

    /**
     * Clicks the "Cerrar" button to close the modal.
     *
     * @author Vlad
     * @date 25/September/2025
     */
    public void clickCerrar() {
        By cerrarBtn = By.xpath("//android.widget.TextView[@text=\"Cerrar\"]");
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(cerrarBtn));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Cerrar'");
    }

    /**
     * Clicks the "Cancelar" button to cancel the selected order.
     *
     * @author Vlad
     * @date 25/September/2025
     */
    public void clickCancelar() {
        By cancelarBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Cancelar\"]");
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(cancelarBtn));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Cancelar'");
    }

    /**
     * Validates that the 'Herramienta de Compra' label is visible again.
     *
     * @return true if visible
     * @author Vlad
     * @date 25/September/2025
     */
    public boolean isHerramientaCompraVisible() {
        By herramientaCompraLabel = By.xpath("//android.widget.TextView[@text=\"Herramienta de Compra\"]");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(herramientaCompraLabel)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "'Herramienta de Compra' visible again: " + visible);
        return visible;
    }

     /**
     * Clicks the "Cancelar" button (by text).
     *
     * @author Vlad
     * @date 25/September/2025
     */
    public void clickCancelarSearch() {
        By cancelarBtn = By.xpath("//android.widget.TextView[@text=\"Cancelar\"]");
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(cancelarBtn));
        el.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Cancelar'");
        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

       /**
     * Validates that the "Orden de Compra" title is visible on the screen.
     * <p>
     * This method waits until the "Orden de Compra" title element is visible,
     * then confirms its presence. It also captures a screenshot of the current step
     * for reporting purposes.
     * </p>
     *
     * @return true if the "Orden de Compra" title is displayed, false otherwise
     * @author Vlad
     * @date 25/September/2025
     */
    public boolean isOrdenDeCompraTitleVisible() {
        By title = By.xpath("(//android.widget.TextView[@text=\"Orden de Compra\"])[1]");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(title)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "'Orden de Compra' title visible: " + visible);
        return visible;
    }

    /**
     * Validates that the selected CEDIS (distribution center) is displayed by its code.
     * <p>
     * This method dynamically constructs the locator based on the provided CEDIS code.
     * It waits for the element with text "Fuente de abasto: {cedisCode}" to be visible.
     * The method also logs the result and captures a screenshot of the current step.
     * </p>
     *
     * @param cedisCode the CEDIS code to verify (e.g., "65134").
     *                  This value should be retrieved from the test data provider or Excel file.
     * @return true if the CEDIS with the given code is visible, false otherwise
     * @author Vlad
     * @date 25/September/2025
     */
    public boolean isCedisDisplayed(String cedisCode) {
        By cedis = By.xpath("//android.widget.TextView[@text=\"Fuente de abasto: " + cedisCode + "\"]");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(cedis)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "CEDIS " + cedisCode + " visible: " + visible);
        return visible;
    }

    /**
     * Validates that 'Ingresa el número de remisión' field is visible on the order screen.
     *
     * @return true if the field is displayed, false otherwise
     * @author Vlad
     * @date 25/September/2025
     */
    public boolean isRemisionFieldVisible() {
        By remisionField = By.xpath("//android.widget.TextView[@text=\"Ingresa el número de remisión\"]");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(remisionField)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "'Ingresa el número de remisión' field visible: " + visible);
        return visible;
    }

    /**
     * Enters a remision number into the 'Número de remisión' input field.
     *
     * @param remisionNumber the remision number to be entered
     * @author Vlad
     * @date 25/September/2025
     */
    public void enterRemisionNumber(String remisionNumber) {
        By remisionInput = By.xpath("//android.widget.EditText[@text=\"Número de remisión\"]");
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(remisionInput));
        input.clear();
        input.sendKeys(remisionNumber);
        testCase.addScreenShotCurrentStep(appiumUtil, "Entered remision number: " + remisionNumber);
    }

    /**
     * Validates that the 'Recibir' button is visible.
     *
     * @return true if the button is visible, false otherwise
     * @author Vlad
     * @date 25/September/2025
     */
    public boolean isRecibirButtonVisible() {
        By recibirBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Recibir\"]");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(recibirBtn)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "'Recibir' button visible: " + visible);
        return visible;
    }

    /**
     * Clicks the 'Recibir' button to confirm reception of the order.
     * @author Vlad
     * @date 25/September/2025
     */
    public void clickRecibir() {
        By recibirBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Recibir\"]");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(recibirBtn));
        btn.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Recibir' button");
    }

    /**
     * Validates that the 'Cargando...' message is visible.
     *
     * @return true if the message is visible, false otherwise
     * @author Vlad
     * @date 25/September/2025
     */
    public boolean isCargandoVisible() {
        By cargando = By.xpath("//android.widget.TextView[@text=\"Cargando...\"]");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(cargando)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "'Cargando...' message visible: " + visible);
        return visible;
    }

    /**
     * Clicks the 'Aceptar' button in the purchase order flow.
     * @author Vlad
     * @date 25/September/2025
     */
    public void clickAceptar() {
        By aceptarBtn = By.xpath("//android.view.ViewGroup[@content-desc=\"Aceptar\"]");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(aceptarBtn));
        btn.click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Aceptar' button");
    }
}
