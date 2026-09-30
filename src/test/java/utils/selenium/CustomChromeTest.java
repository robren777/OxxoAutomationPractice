package utils.selenium;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class CustomChromeTest {

    /**
     * Inicializa ChromeDriver con perfil temporal o real, CDP fix y opciones
     * recomendadas.
     * @author Emmanuel Gomez
     * @param maximized   True para abrir maximizado
     * @param profilePath Ruta del perfil de Chrome. Puede ser temporal o real.
     * @return WebDriver inicializado
     * @date 02/Diciembre/2025
     */
    public WebDriver initDriverWithProfile(boolean maximized, String profilePath) {
        ChromeOptions options = new ChromeOptions();

        // Evitar detección automatizada
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Si se proporciona ruta de perfil, usarla
        if (profilePath != null && !profilePath.isEmpty()) {
            options.addArguments("user-data-dir=" + profilePath);
            // Si quieres un subperfil específico:
            // options.addArguments("profile-directory=Default");
        }

        if (maximized) {
            options.addArguments("start-maximized");
        }

        // Crear y devolver el driver
        return new ChromeDriver(options);
    }
}