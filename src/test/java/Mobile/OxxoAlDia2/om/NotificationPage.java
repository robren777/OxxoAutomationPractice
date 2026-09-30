package Mobile.OxxoAlDia2.om;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.AppiumUtil;
import modelo.TestCase;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

/**
 * Page Object for the Notifications section in the OxxoAlDia mobile app.
 * Provides validation methods for verifying that a notification is visible,
 * including checking the notification chip and a notification title.
 *
 * Used in tests like:
 * - MTC-FT-033_VerifyDifferentNotificationTypes
 *
 * Each method captures screenshots for reporting purposes.
 *
 * @author Vlad
 * @date 10/October/2025
 */
public class NotificationPage {

    private final AppiumUtil appiumUtil;
    private final TestCase testCase;
    private final WebDriverWait wait;

    // Locators
    private final By dateLabel = By.xpath("//android.widget.TextView[contains(@text, ', ')]");
    private final By notificationChip = By.xpath("//android.view.ViewGroup[@resource-id='notification-chip']");
    private By notificationTitle(String title) {
        return By.xpath("(//android.widget.TextView[@text='" + title + "'])[1]");
    }

    /**
     * Constructor for NotificationPage.
     *
     * @param appiumUtil Appium utility instance for driver interactions
     * @param testCase   TestCase instance for attaching screenshots
     * @author Vlad
     * @date 10/October/2025
     */
    public NotificationPage(AppiumUtil appiumUtil, TestCase testCase) {
        this.appiumUtil = appiumUtil;
        this.testCase = testCase;
        this.wait = new WebDriverWait(appiumUtil.driver, Duration.ofSeconds(30));
    }

    /**
     * Validates that both the notification chip and a specific notification title are visible.
     * 
     * This method:
     *  - Waits for the notification chip to appear
     *  - Waits for the notification with the given title (e.g., “test 2025-10-10”)
     *  - Takes screenshots for both validation steps
     *
     * @param notificationTitle Title of the notification created via API
     * @return true if both elements are visible, otherwise false
     * @author Vlad
     * @date 10/October/2025
     */
    public boolean isNotificationVisible(String notificationTitle) {
        boolean isChipVisible = false;
        boolean isNotificationVisible = false;

        try {
            isChipVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(notificationChip)).isDisplayed();
            testCase.addScreenShotCurrentStep(appiumUtil, "Notification chip visible: " + isChipVisible);

            isNotificationVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(notificationTitle(notificationTitle))).isDisplayed();
            testCase.addScreenShotCurrentStep(appiumUtil, "Notification with title '" + notificationTitle + "' visible: " + isNotificationVisible);

        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil, "Notification not found: " + e.getMessage());
            return false;
        }

        return isChipVisible && isNotificationVisible;
    }

        /**
     * Clicks the "Ver todos" (View All) button in the notifications section.
     * Captures a screenshot after the action.
     *
     * @author Vlad
     * @date 10/October/2025
     */
    public void clickViewAllButton() {
        By viewAllBtn = By.xpath("//android.widget.TextView[@text='Ver todos']");
        wait.until(ExpectedConditions.elementToBeClickable(viewAllBtn)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Ver todos' button");
    }

    /**
     * Validates that the "Actividades del día" title is displayed after clicking "Ver todos".
     *
     * @return true if title is visible, otherwise false
     * @author Vlad
     * @date 10/October/2025
     */
    public boolean isDailyActivitiesVisible() {
        By dailyActivitiesTitle = By.xpath("//android.widget.TextView[@text='Actividades del día']");
        boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(dailyActivitiesTitle)).isDisplayed();
        testCase.addScreenShotCurrentStep(appiumUtil, "'Actividades del día' title visible: " + visible);
        return visible;
    }

    /**
     * Validates that a notification with the specified title is visible on the screen.
     *
     * @param notificationTitle the title of the notification to verify
     * @return true if the notification with the given title is visible, otherwise false
     * @author Vlad
     * @date 11/October/2025
     */
    public boolean isNotificationTitleVisible(String notificationTitle) {
        try {
            By titleLocator = By.xpath("(//android.widget.TextView[@text='" + notificationTitle + "'])[1]");
            boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(titleLocator)).isDisplayed();
            testCase.addScreenShotCurrentStep(appiumUtil, "Notification with title '" + notificationTitle + "' visible: " + visible);
            return visible;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil, "Notification title '" + notificationTitle + "' not visible");
            return false;
        }
    }

    /**
     * Validates that the 'Urgente' label (priority indicator) is visible on the screen.
     *
     * @return true if the 'Urgente' label is visible, otherwise false
     * @author Vlad
     * @date 11/October/2025
     */
    public boolean isUrgenteLabelVisible() {
        try {
            By urgenteLocator = By.xpath("(//android.widget.TextView[@text='Urgente'])[1]");
            boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(urgenteLocator)).isDisplayed();
            testCase.addScreenShotCurrentStep(appiumUtil, "'Urgente' label visible: " + visible);
            return visible;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil, "'Urgente' label not visible");
            return false;
        }
    }

    /**
     * Clicks a notification by its title to open the details screen.
     *
     * @param notificationTitle the title of the notification to click
     * @author Vlad
     * @date 11/October/2025
     */
    public void clickNotificationByTitle(String notificationTitle) {
        By titleLocator = By.xpath("(//android.widget.TextView[@text='" + notificationTitle + "'])[1]");
        wait.until(ExpectedConditions.elementToBeClickable(titleLocator)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked on notification: " + notificationTitle);
    }

    /**
     * Clicks the back button to return to the main notifications list.
     *
     * @author Vlad
     * @date 11/October/2025
     */
    public void clickBackButton() {
        By backButton = By.xpath("//android.view.ViewGroup[@resource-id='icon-button']");
        wait.until(ExpectedConditions.elementToBeClickable(backButton)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked Back button (icon-button) to return to notifications list");
    }

    /**
     * Validates that the 'Urgente' label is no longer visible (after reading).
     *
     * @return true if the 'Urgente' label is NOT visible, otherwise false
     * @author Vlad
     * @date 11/October/2025
     */
    public boolean isUrgenteLabelNotVisible() {
        try {
            By urgenteLocator = By.xpath("(//android.widget.TextView[@text='Urgente'])[1]");
            boolean notVisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(urgenteLocator));
            testCase.addScreenShotCurrentStep(appiumUtil, "'Urgente' label no longer visible: " + notVisible);
            return notVisible;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil, "'Urgente' label still visible or check failed");
            return false;
        }
    }

    /**
     * Verifies that the current date displayed in the UI matches the system date (in Spanish locale).
     * Example of expected format: "Domingo, 12 oct"
     *
     * @author Vlad
     * @date 12/October/2025
     * @return true if the displayed date matches today’s day and month, otherwise false
     */
    public boolean verifyCurrentDateDisplayedCorrectly() {
        Locale locale = new Locale("es", "ES");
        LocalDate today = LocalDate.now();

        // Build expected localized values
        String expectedDayName = today.getDayOfWeek().getDisplayName(TextStyle.FULL, locale);
        String expectedMonthShort = new DateFormatSymbols(locale).getShortMonths()[today.getMonthValue() - 1];
        String expectedDay = String.valueOf(today.getDayOfMonth());

        WebElement dateElement = appiumUtil.wait.until(ExpectedConditions.visibilityOfElementLocated(dateLabel));
        String actualText = dateElement.getText().toLowerCase();

        testCase.addScreenShotCurrentStep(appiumUtil, "Displayed date text: " + actualText);

        boolean isDayCorrect = actualText.contains(expectedDay);
        boolean isMonthCorrect = actualText.contains(expectedMonthShort.substring(0, 3));
        boolean isWeekdayCorrect = actualText.contains(expectedDayName.substring(0, 3).toLowerCase());

        assertTrue(isDayCorrect, "Displayed date does not contain correct day number: expected " + expectedDay);
        assertTrue(isMonthCorrect, "Displayed month abbreviation mismatch: expected " + expectedMonthShort);
        assertTrue(isWeekdayCorrect, "Displayed weekday does not match current day: expected " + expectedDayName);

        testCase.addScreenShotCurrentStep(appiumUtil, "Date verification passed — matched: " + expectedDayName + ", " + expectedDay + " " + expectedMonthShort);
        return isDayCorrect && isMonthCorrect && isWeekdayCorrect;
    }

    /**
     * Clicks the toggle switch element responsible for filtering unread notifications.
     * <p>
     * Located by resource-id <b>toggle-track</b>.
     * Used to switch between showing all notifications and unread ones only.
     *
     * @author Vlad
     * @date 12/October/2025
     */
    public void clickToggleTrack() {
        By toggle = By.xpath("//android.view.ViewGroup[@resource-id='toggle-track']");
        wait.until(ExpectedConditions.elementToBeClickable(toggle)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked toggle-track");
    }

    /**
     * Checks that the notification with the specified title is <b>not visible</b> on the screen.
     * <p>
     * Typically used to confirm that a notification has been marked as read
     * or filtered out after toggling the unread filter.
     *
     * @param title The notification title text to verify.
     * @return true if the notification is not visible, false if it is still displayed.
     *
     * @author Vlad
     * @date 12/October/2025
     */
    public boolean isNotificationTitleNotVisible(String title) {
        try {
            appiumUtil.driver.findElement(By.xpath("(//android.widget.TextView[@text='" + title + "'])[1]"));
            return false; // Still visible
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil,
                "Notification '" + title + "' not visible (expected behavior)");
            return true;
        }
    }

    /**
     * Checks that two notification icons with the same title are visible on the screen.
     * <p>
     * This method ensures that both notifications (e.g., from different categories)
     * are displayed and their icon elements are present.
     *
     * @param title The notification title (e.g. "test1 2025-10-12")
     * @return true if both icons are visible, false otherwise
     *
     * @author Vlad
     * @date 13/October/2025
     */
    public boolean areNotificationIconsVisible(String title) {
        try {
            String firstIconXPath = "(//android.view.ViewGroup[@content-desc=\"" + title + ", Urgente, hace (1 min)\"])[1]/android.view.ViewGroup/android.widget.ImageView";
            String secondIconXPath = "(//android.view.ViewGroup[@content-desc=\"" + title + ", Urgente, hace (1 min)\"])[2]/android.view.ViewGroup/android.widget.ImageView";

            boolean firstIconVisible = appiumUtil.driver.findElement(By.xpath(firstIconXPath)).isDisplayed();
            boolean secondIconVisible = appiumUtil.driver.findElement(By.xpath(secondIconXPath)).isDisplayed();

            testCase.addScreenShotCurrentStep(appiumUtil, "Checked visibility of both notification icons for: " + title);

            return firstIconVisible && secondIconVisible;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil,
                "⚠️ One or both icons not visible for: " + title + "\n" + e.getMessage());
            return false;
        }
    }

    /**
     * Clicks the back button inside the notification modal.
     * <p>
     * Used when the user opens a notification and needs to return to the list.
     */
    public void clickNotificationBackButton() {
        By backButton = By.xpath("//android.view.ViewGroup[@resource-id='notification-back-button']");
        wait.until(ExpectedConditions.elementToBeClickable(backButton)).click();
        testCase.addScreenShotCurrentStep(appiumUtil, "Clicked notification back button");
    }

    /**
     * Validates that the notification modal displays the correct title and description.
     * <p>
     * Checks for the presence of:
     * <ul>
     *   <li>The header "Notificaciones"</li>
     *   <li>The notification title text</li>
     *   <li>The notification description matching the API payload</li>
     * </ul>
     *
     * @param title The expected notification title (e.g. "test 2025-10-14")
     * @param description The expected description text from API payload
     * @return true if all elements are visible and match, false otherwise
     *
     * @author Vlad
     * @date 14/October/2025
     */
    public boolean isNotificationModalVisible(String title, String description) {
        try {
            // Validate header "Notificaciones"
            boolean headerVisible = appiumUtil.driver
                    .findElement(By.xpath("//android.widget.TextView[@text='Notificaciones']"))
                    .isDisplayed();

            // Validate notification title
            boolean titleVisible = appiumUtil.driver
                    .findElement(By.xpath("//android.widget.TextView[@text='" + title + "']"))
                    .isDisplayed();

            // Validate notification description text matches payload
            String actualDescription = appiumUtil.driver
                    .findElement(By.xpath("//android.widget.TextView[@text='" + description + "']"))
                    .getText();

            boolean descriptionMatches = actualDescription.equals(description);

            boolean result = headerVisible && titleVisible && descriptionMatches;

            testCase.addScreenShotCurrentStep(appiumUtil,
                    "Validated modal for '" + title + "' — header, title, and description visible");

            return result;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil,
                    "Failed to validate modal for '" + title + "'. Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validates that the “Marcar Como Realizado” (Mark as done) button
     * is visible on the expanded notification modal.
     * <p>
     * This confirms that the task-type notification includes an actionable
     * section allowing the user to mark it as completed.
     *
     * @return true if the “Marcar Como Realizado” button is visible, false otherwise.
     *
     * @author Vlad
     * @date 14/October/2025
     */
    public boolean isMarkAsDoneButtonVisible() {
        try {
            By markAsDoneButton = By.xpath("//android.view.ViewGroup[@content-desc='Marcar Como Realizado']");
            boolean isVisible = appiumUtil.driver.findElement(markAsDoneButton).isDisplayed();

            testCase.addScreenShotCurrentStep(appiumUtil,
                    "Verified 'Marcar Como Realizado' button is visible in expanded modal");
            return isVisible;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil,
                    "'Marcar Como Realizado' button not visible or not found.\n" + e.getMessage());
            return false;
        }
    }

    /**
     * Clicks the “Marcar Como Realizado” (Mark as done) button.
     * <p>
     * Used to complete a task notification from its expanded modal view.
     *
     * @author Vlad
     * @date 14/October/2025
     */
    public void clickMarkAsDoneButton() {
        try {
            By markAsDoneButton = By.xpath("//android.view.ViewGroup[@content-desc='Marcar Como Realizado']");
            wait.until(ExpectedConditions.elementToBeClickable(markAsDoneButton)).click();
            testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Marcar Como Realizado' button");
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil,
                    "Failed to click 'Marcar Como Realizado' button.\n" + e.getMessage());
            throw e;
        }
    }

    /**
     * Validates that the “Completado” (Completed) status is NOT visible
     * immediately after opening the task notification.
     * <p>
     * This ensures that tasks are not auto-marked as completed upon viewing.
     *
     * @return true if the “Completado” label is NOT visible, false if it is found on screen.
     *
     * @author Vlad
     * @date 14/October/2025
     */
    public boolean isCompletedStatusNotVisible() {
        try {
            By completedLabel = By.xpath("(//android.widget.TextView[@text='Completado'])[1]");
            boolean isVisible = appiumUtil.driver.findElement(completedLabel).isDisplayed();

            testCase.addScreenShotCurrentStep(appiumUtil,
                    "Unexpectedly found 'Completado' status visible (should not appear before marking done)");
            return !isVisible;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil,
                    "'Completado' status not visible as expected (element not found)");
            return true; // Expected behavior
        }
    }

    /**
     * Validates that the “Completado” (Completed) status is visible
     * after the user marks the task as done.
     * <p>
     * This confirms that pressing the “Marcar Como Realizado” button
     * successfully updates the task state.
     *
     * @return true if the “Completado” label is visible, false otherwise.
     *
     * @author Vlad
     * @date 14/October/2025
     */
    public boolean isCompletedStatusVisible() {
        try {
            By completedLabel = By.xpath("(//android.widget.TextView[@text='Completado'])[1]");
            boolean isVisible = appiumUtil.driver.findElement(completedLabel).isDisplayed();

            testCase.addScreenShotCurrentStep(appiumUtil,
                    "Verified 'Completado' status visible after marking task as done");
            return isVisible;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil,
                    "'Completado' status not visible after marking as done.\n" + e.getMessage());
            return false;
        }
    }

    /**
     * Validates that the “Subir foto” (Upload Photo) button is visible
     * in the expanded notification view.
     *
     * @return true if the “Subir foto” button is displayed, false otherwise.
     *
     * @author Vlad
     * @date 15/October/2025
     */
    public boolean isUploadPhotoButtonVisible() {
        try {
            By uploadPhotoButton = By.xpath("//android.view.ViewGroup[@content-desc='Subir foto']");
            boolean isVisible = appiumUtil.driver.findElement(uploadPhotoButton).isDisplayed();

            testCase.addScreenShotCurrentStep(appiumUtil, "Verified 'Subir foto' button is visible in expanded modal");
            return isVisible;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil, "'Subir foto' button not found in expanded view");
            return false;
        }
    }

    /**
     * Clicks the “Subir foto” (Upload Photo) button in the expanded task view.
     * <p>
     * Used to trigger the photo upload flow or to open the camera options.
     *
     * @author Vlad
     * @date 16/October/2025
     */
    public void clickUploadPhotoButton() {
        try {
            By uploadPhotoButton = By.xpath("//android.view.ViewGroup[@content-desc='Subir foto']");
            WebElement button = appiumUtil.driver.findElement(uploadPhotoButton);

            testCase.addScreenShotCurrentStep(appiumUtil, "Clicking 'Subir foto' button");
            button.click();

        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil,
                    "Failed to click 'Subir foto' button\n" + e.getMessage());
            throw e;
        }
    }

    /**
     * Clicks the “Tomar foto” (Take Photo) button.
     *
     * @author Vlad
     * @date 16/October/2025
     */
    public void clickTakePhotoButton() {
        try {
            By takePhotoButton = By.xpath("//android.view.ViewGroup[@content-desc='Tomar foto']");
            appiumUtil.driver.findElement(takePhotoButton).click();
            testCase.addScreenShotCurrentStep(appiumUtil, "Clicked 'Tomar foto' button to open camera");
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil, "Failed to click 'Tomar foto' button\n" + e.getMessage());
            throw e;
        }
    }

    /**
     * Validates that the camera shutter button (“Shutter”) is visible.
     *
     * @return true if camera shutter is displayed, false otherwise
     *
     * @author Vlad
     * @date 16/October/2025
     */
    public boolean isCameraShutterVisible() {
        try {
            By shutterButton = By.xpath("//android.widget.ImageView[@content-desc='Shutter']");
            boolean isVisible = appiumUtil.driver.findElement(shutterButton).isDisplayed();

            testCase.addScreenShotCurrentStep(appiumUtil, "Verified camera shutter is visible");
            return isVisible;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil, "Camera shutter not found or not visible\n" + e.getMessage());
            return false;
        }
    }

    /**
     * Checks if the “Enviar” (Send) button is enabled and clickable.
     * <p>
     * Used to verify whether the button can be interacted with before or after photo upload.
     *
     * @return true if the 'Enviar' button is enabled (clickable), false if disabled.
     *
     * @author Vlad
     * @date 16/October/2025
     */
    public boolean isSendButtonEnabled() {
        try {
            By sendButton = By.xpath("//android.view.ViewGroup[@content-desc='Enviar']");
            WebElement button = appiumUtil.driver.findElement(sendButton);

            // Check both 'enabled' and 'clickable' attributes — some builds only update one
            boolean isEnabled = Boolean.parseBoolean(button.getAttribute("enabled"));
            boolean isClickable = Boolean.parseBoolean(button.getAttribute("clickable"));

            testCase.addScreenShotCurrentStep(appiumUtil,
                    String.format("'Enviar' button state — enabled: %s, clickable: %s", isEnabled, isClickable));

            return isEnabled && isClickable;
        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(appiumUtil,
                    "Failed to locate 'Enviar' button or read attributes\n" + e.getMessage());
            return false;
        }
    }


}
