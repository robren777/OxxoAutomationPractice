package Mobile.preinventariosinteligentes.mvp5.support;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

/** Centraliza el inicio limpio y el cierre seguro de la app MVP5. */
public final class CicloAplicacion {

    private CicloAplicacion() {
    }

    /** Termina cualquier instancia previa y abre la actividad configurada. */
    public static void iniciarLimpio(
            AndroidDriver driver,
            String appPackage,
            String appActivity) {
        cerrar(driver, appPackage);

        String actividad = normalizarActividad(appActivity);
        if (actividad == null) {
            throw new IllegalStateException(
                    "Se requiere android.appActivity para iniciar la app desde Login");
        }

        Map<String, Object> argumentos = new HashMap<>();
        argumentos.put("intent", appPackage + "/" + actividad);
        argumentos.put("wait", true);
        driver.executeScript("mobile: startActivity", argumentos);

        try {
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                    controlador -> appPackage.equals(driver.getCurrentPackage()));
        } catch (TimeoutException excepcion) {
            throw new IllegalStateException(
                    "La app no quedo en foreground despues del inicio limpio: "
                            + appPackage + "/" + actividad,
                    excepcion);
        }
    }

    /** Finaliza la app si estaba abierta; es seguro invocarlo repetidamente. */
    public static void cerrar(AndroidDriver driver, String appPackage) {
        if (driver == null || appPackage == null || appPackage.trim().isEmpty()) {
            return;
        }
        try {
            driver.terminateApp(appPackage);
        } catch (RuntimeException ignorada) {
            // La app ya cerrada satisface la postcondicion requerida.
        }
    }

    private static String normalizarActividad(String appActivity) {
        if (appActivity == null) {
            return null;
        }
        String actividad = appActivity.trim();
        int separador = actividad.indexOf('/');
        if (separador >= 0 && separador < actividad.length() - 1) {
            actividad = actividad.substring(separador + 1).trim();
        }
        return actividad.isEmpty() ? null : actividad;
    }
}
