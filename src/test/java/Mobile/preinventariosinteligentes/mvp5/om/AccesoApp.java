package Mobile.preinventariosinteligentes.mvp5.om;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Mobile.preinventariosinteligentes.mvp5.support.CicloAplicacion;
import io.appium.java_client.android.AndroidDriver;
import utils.AppiumUtil;

/**
 * Soporte de acceso a app y navegacion inicial para scripts MVP5.
 * Mantiene este comportamiento fuera de los OMs legacy.
 */
final class AccesoApp {
    private final AppiumUtil appium;
    private final String nombrePaquete;
    private final Duration tiempoEspera;

    AccesoApp(AppiumUtil appium, String nombrePaquete, Duration tiempoEspera) {
        this.appium = appium;
        this.nombrePaquete = nombrePaquete;
        this.tiempoEspera = tiempoEspera;
    }

    void iniciarDesdeLoginYEntrarAConteosInteligentes(String appActivity, String usuario, String contrasena) {
        iniciarDesdeLoginHastaMenuPreinventarios(appActivity, usuario, contrasena);
        entrarAConteosInteligentes();
    }

    void iniciarDesdeLoginHastaMenuPreinventarios(
            String appActivity, String usuario, String contrasena) {
        iniciarDesdeLogin(appActivity, usuario, contrasena);
    }

    void entrarAConteosInteligentes() {
        hacerClic(id("btn_register_pre_inventory"));
    }

    void iniciarDesdeLoginYEntrarAConteosPropios(String appActivity, String usuario, String contrasena) {
        iniciarDesdeLogin(appActivity, usuario, contrasena);
        hacerClic(id("btn_own_counts"));
    }

    void entrarAConteosPropios() {
        hacerClic(id("btn_own_counts"));
    }

    void finalizarAppConAdb() {
        CicloAplicacion.cerrar(driverAndroid(), nombrePaquete);
    }

    private void iniciarDesdeLogin(String appActivity, String usuario, String contrasena) {
        boolean inicioPreparado = appEnForeground(Duration.ofSeconds(3))
                && indicadorLoginVisible(Duration.ofSeconds(5));
        if (!inicioPreparado) {
            reiniciarAplicacion(appActivity);
        }

        asegurar(appEnForeground(Duration.ofSeconds(8)),
                "La app no quedo en foreground tras el inicio limpio: " + contextoActual());
        asegurar(indicadorLoginVisible(Duration.ofSeconds(12)),
                "No se detecto pantalla de login despues del inicio limpio. "
                        + contextoActual());

        asegurar(appEnForeground(Duration.ofSeconds(5)),
                "La app abandono foreground antes de capturar credenciales: " + contextoActual());

        hacerLogin(usuario, contrasena);
        navegarControlYPreinventarios();
    }

    private void reiniciarAplicacion(String appActivity) {
        CicloAplicacion.iniciarLimpio(
                driverAndroid(),
                nombrePaquete,
                appActivity);
    }

    private boolean appEnForeground(Duration timeout) {
        WebDriverWait wait = new WebDriverWait(appium.driver, timeout);
        try {
            return wait.until(driver -> {
                if (!(driver instanceof AndroidDriver)) {
                    return false;
                }
                String actual = ((AndroidDriver) driver).getCurrentPackage();
                return nombrePaquete.equals(actual);
            });
        } catch (TimeoutException ex) {
            return false;
        }
    }

    private boolean pantallaLoginVisible(Duration timeout) {
        return esVisible(id("etUserName"), timeout)
                && esVisible(id("etPassword"), timeout)
                && esVisible(id("btnLogin"), timeout);
    }

    private boolean indicadorLoginVisible(Duration timeout) {
        return esperarCualquierVisible(timeout,
                id("etUserName"),
                id("etPassword"),
                id("btnLogin"));
    }

    private void hacerLogin(String usuario, String contrasena) {
        intentarOcultarTeclado();
        WebElement campoUsuario = elementoVisible(id("etUserName"));
        campoUsuario.clear();
        campoUsuario.sendKeys(usuario);

        WebElement campoContrasena = elementoVisible(id("etPassword"));
        campoContrasena.clear();
        campoContrasena.sendKeys(contrasena);

        if (esVisible(id("ivLogo"), Duration.ofSeconds(2))) {
            appium.driver.findElement(id("ivLogo")).click();
        }
        hacerClic(id("btnLogin"));
        asegurar(esperarCualquierVisible(Duration.ofSeconds(30),
                        menuControlCard(), menuControlLabel(), menuPreInvCard(), menuPreInvLabel()),
                "Login exitoso no confirmado: no aparecio el menu principal");
    }

    private void navegarControlYPreinventarios() {
        clickPrimeroVisibleYValidar("Control", Duration.ofSeconds(30), Duration.ofSeconds(8),
                new By[] { menuControlCard(), menuControlLabel() },
                new By[] { menuPreInvCard(), menuPreInvLabel() });

        clickPrimeroVisibleYValidar("Preinventarios Inteligentes", Duration.ofSeconds(30), Duration.ofSeconds(8),
                new By[] { menuPreInvCard(), menuPreInvLabel() },
                new By[] { id("btn_register_pre_inventory"), id("btn_own_counts"), id("btn_pre_inventory_roles_assignment") });
    }

    private void clickPrimeroVisibleYValidar(String nombre,
            Duration timeoutClick,
            Duration timeoutValidacion,
            By[] candidatosClick,
            By[] candidatosValidacion) {
        RuntimeException ultimoError = null;
        for (By candidato : candidatosClick) {
            if (candidato == null) {
                continue;
            }
            try {
                WebElement elemento = esperarPrimerElementoVisible(timeoutClick, candidato);
                elemento.click();
                if (esperarCualquierVisible(timeoutValidacion, candidatosValidacion)) {
                    return;
                }
                ultimoError = new RuntimeException("Se hizo clic en " + candidato + " pero no se detecto la pantalla esperada");
            } catch (RuntimeException ex) {
                ultimoError = ex;
            }
        }
        throw new RuntimeException("No fue posible navegar con el boton " + nombre, ultimoError);
    }

    private WebElement esperarPrimerElementoVisible(Duration timeout, By... candidatos) {
        WebDriverWait wait = new WebDriverWait(appium.driver, timeout);
        return wait.until(driver -> {
            for (By locator : candidatos) {
                List<WebElement> elementos = driver.findElements(locator);
                for (WebElement elemento : elementos) {
                    if (elemento != null && elemento.isDisplayed()) {
                        return elemento;
                    }
                }
            }
            return null;
        });
    }

    private boolean esperarCualquierVisible(Duration timeout, By... locators) {
        WebDriverWait wait = new WebDriverWait(appium.driver, timeout);
        try {
            return wait.until(driver -> {
                for (By locator : locators) {
                    if (locator == null) {
                        continue;
                    }
                    for (WebElement elemento : driver.findElements(locator)) {
                        if (elemento != null && elemento.isDisplayed()) {
                            return true;
                        }
                    }
                }
                return false;
            });
        } catch (TimeoutException ex) {
            return false;
        }
    }

    private boolean esVisible(By locator, Duration timeout) {
        try {
            new WebDriverWait(appium.driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (RuntimeException ex) {
            return false;
        }
    }

    private void intentarOcultarTeclado() {
        try {
            appium.driver.hideKeyboard();
        } catch (RuntimeException ignorada) {
            // Teclado no visible: flujo esperado.
        }
    }

    private WebElement elementoVisible(By localizador) {
        return espera().until(ExpectedConditions.visibilityOfElementLocated(localizador));
    }

    private void hacerClic(By localizador) {
        espera().until(ExpectedConditions.elementToBeClickable(localizador)).click();
    }

    private By id(String recurso) {
        return By.id(nombrePaquete + ":id/" + recurso);
    }

    private WebDriverWait espera() {
        return new WebDriverWait(appium.driver, tiempoEspera);
    }

    private AndroidDriver driverAndroid() {
        if (!(appium.driver instanceof AndroidDriver)) {
            throw new IllegalStateException("La ejecucion MVP5 requiere AndroidDriver");
        }
        return (AndroidDriver) appium.driver;
    }

    private By menuControlCard() {
        return By.xpath("//android.widget.LinearLayout[@resource-id="
                + literalXpath(nombrePaquete + ":id/lyMenuItem")
                + " and .//android.widget.TextView[@resource-id="
                + literalXpath(nombrePaquete + ":id/tvMenuName")
                + " and normalize-space(@text)='Control']]//android.widget.FrameLayout[@resource-id="
                + literalXpath(nombrePaquete + ":id/cvMenu") + "]");
    }

    private By menuControlLabel() {
        return By.xpath("//android.widget.TextView[@resource-id="
                + literalXpath(nombrePaquete + ":id/tvMenuName")
                + " and normalize-space(@text)='Control']");
    }

    private By menuPreInvCard() {
        return By.xpath("//android.widget.LinearLayout[@resource-id="
                + literalXpath(nombrePaquete + ":id/lyMenuItem")
                + " and .//android.widget.TextView[@resource-id="
                + literalXpath(nombrePaquete + ":id/tvMenuName")
                + " and normalize-space(@text)='Preinventarios Inteligentes']]//android.widget.FrameLayout[@resource-id="
                + literalXpath(nombrePaquete + ":id/cvMenu") + "]");
    }

    private By menuPreInvLabel() {
        return By.xpath("//android.widget.TextView[@resource-id="
                + literalXpath(nombrePaquete + ":id/tvMenuName")
                + " and contains(normalize-space(@text),'Preinventarios Inteligentes')]");
    }

    private static void asegurar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    private static String literalXpath(String valor) {
        if (!valor.contains("'")) {
            return "'" + valor + "'";
        }
        if (!valor.contains("\"")) {
            return "\"" + valor + "\"";
        }
        String[] partes = valor.split("'", -1);
        StringBuilder resultado = new StringBuilder("concat(");
        for (int indice = 0; indice < partes.length; indice++) {
            if (indice > 0) {
                resultado.append(", \"'\", ");
            }
            resultado.append("'").append(partes[indice]).append("'");
        }
        return resultado.append(')').toString();
    }

    private String contextoActual() {
        AndroidDriver driver = driverAndroid();
        String paquete = "desconocido";
        String actividad = "desconocida";
        try {
            paquete = String.valueOf(driver.getCurrentPackage());
        } catch (RuntimeException ignorada) {
            // El valor de paquete es diagnostico opcional.
        }
        try {
            actividad = String.valueOf(driver.currentActivity());
        } catch (RuntimeException ignorada) {
            // El valor de actividad es diagnostico opcional.
        }
        return "paqueteActual=" + paquete + ", actividadActual=" + actividad;
    }
}
