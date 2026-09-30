package WEB.cometa.om;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class accessControlPage {

    private final SeleniumUtil u;
    private final TestCase testCase;
    private final PortalCometaMVP4 portalCometa;

    public accessControlPage(SeleniumUtil u, TestCase testCase, PortalCometaMVP4 portalCometa) {
        this.u = u;
        this.testCase = testCase;
        this.portalCometa = portalCometa;
    }

    /**
     * Captura el usuario en Access Control y comprueba que el campo conserve el valor completo.
     *
     * @param user usuario que debe mostrarse en el campo de inicio de sesion.
     * @return true cuando el campo Usuario contiene exactamente el valor recibido.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-17
     */
    public boolean capturarYValidarUsuario(String user) {
        try {
            WebElement campoUsuario = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30))
                    .until(ExpectedConditions.elementToBeClickable(portalCometa.xmlUserName));
            campoUsuario.clear();
            campoUsuario.sendKeys(user);
            String valorActual = campoUsuario.getAttribute("value");
            boolean resultadoPaso = user != null && user.equals(valorActual);
            registrarResultado(resultadoPaso,
                    "El campo Usuario muestra el valor '" + user + "'",
                    "El campo Usuario no conserva el valor esperado. Esperado: " + user + " | Actual: " + valorActual,
                    "Usuario_capturado", portalCometa.xmlUserName);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible capturar el usuario en Access Control: " + exception.getMessage(),
                    "Error_captura_usuario");
            return false;
        }
    }

    /**
     * Captura la contrasena en Access Control y valida que permanezca enmascarada.
     * La contrasena no se imprime ni se agrega a la evidencia.
     *
     * @param password contrasena configurada para el usuario en prueba.
     * @return true cuando el campo conserva el valor y utiliza el tipo password.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-17
     */
    public boolean capturarYValidarPassword(String password) {
        try {
            WebElement campoPassword = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30))
                    .until(ExpectedConditions.elementToBeClickable(portalCometa.xmlPassword));
            campoPassword.clear();
            campoPassword.sendKeys(password);
            String valorActual = campoPassword.getAttribute("value");
            String tipoCampo = campoPassword.getAttribute("type");
            boolean resultadoPaso = password != null && password.equals(valorActual)
                    && "password".equalsIgnoreCase(tipoCampo);
            registrarResultado(resultadoPaso,
                    "El campo Contrasena muestra los caracteres enmascarados",
                    "El campo Contrasena no conserva un valor enmascarado. Tipo actual: " + tipoCampo,
                    "Password_enmascarado", portalCometa.xmlPassword);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible capturar la contrasena en Access Control: " + exception.getMessage(),
                    "Error_captura_password");
            return false;
        }
    }

    /**
     * Da clic en Iniciar sesion y espera que Portal COMETA quede disponible en Access Control.
     *
     * @return true cuando las credenciales son aceptadas y aparece la opcion Portal COMETA.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-17
     */
    public boolean iniciarSesionYValidarAcceso() {
        try {
            new WebDriverWait(u.getDriver(), Duration.ofSeconds(30))
                    .until(ExpectedConditions.elementToBeClickable(portalCometa.xmlLoginButton))
                    .click();
            boolean resultadoPaso = new WebDriverWait(u.getDriver(), Duration.ofSeconds(70))
                    .until(ExpectedConditions.visibilityOfElementLocated(portalCometa.xmlPortalCometa))
                    .isDisplayed();
            registrarResultado(resultadoPaso,
                    "El sistema valida las credenciales e inicia sesion correctamente",
                    "La opcion Portal COMETA no se mostro despues de iniciar sesion",
                    "Sesion_iniciada", portalCometa.xmlPortalCometa);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible iniciar sesion en Access Control: " + exception.getMessage(),
                    "Error_inicio_sesion");
            return false;
        }
    }

            /**
             * Selecciona Portal COMETA y valida que el menu principal muestre las opciones del perfil.
             * Para ADMIN tambien comprueba CEDIS y FAQS CEDIS antes de generar la captura final.
             *
             * @param perfilAdmin true cuando se deben validar las opciones adicionales del perfil ADMIN.
             * @return true cuando el portal carga todas las opciones esperadas para el perfil.
             *
             * @author Mauricio Gurrola
             * @date 2026-07-17
             */
            public boolean seleccionarPortalCometaYValidarMenu(boolean perfilAdmin) {
            try {
                boolean portalSeleccionado = portalCometa.seleccionarPortalCometaQA(false);
                WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(70));
                boolean opcionesDirectos = portalSeleccionado
                    && wait.until(ExpectedConditions.visibilityOfElementLocated(portalCometa.btnDirectos)).isDisplayed()
                    && wait.until(ExpectedConditions.visibilityOfElementLocated(portalCometa.btnFaqsDirectos)).isDisplayed()
                    && wait.until(ExpectedConditions.visibilityOfElementLocated(portalCometa.btnCentroDeDescargas)).isDisplayed();
                boolean opcionesAdmin = !perfilAdmin
                    || (wait.until(ExpectedConditions.visibilityOfElementLocated(portalCometa.btnCedis)).isDisplayed()
                    && wait.until(ExpectedConditions.visibilityOfElementLocated(portalCometa.btnFaqsCedis)).isDisplayed());
                boolean resultadoPaso = opcionesDirectos && opcionesAdmin;
                String opcionesEsperadas = perfilAdmin
                    ? "CEDIS, Directos, FAQS Directos, FAQS CEDIS y Centro de descarga"
                    : "Directos, FAQS Directos y Centro de descarga";
                registrarResultado(resultadoPaso,
                    "Se visualiza la pantalla principal del portal Cometa con las opciones: " + opcionesEsperadas,
                    "El menu principal no muestra todas las opciones esperadas: " + opcionesEsperadas,
                    "Menu_principal_Portal_COMETA", portalCometa.btnDirectos, portalCometa.btnFaqsDirectos,
                    portalCometa.btnCentroDeDescargas);
                return resultadoPaso;
            } catch (Exception exception) {
                registrarError("No fue posible seleccionar Portal COMETA y validar su menu: " + exception.getMessage(),
                    "Error_menu_Portal_COMETA");
                return false;
            }
            }

                /**
                 * Valida la configuracion del perfil antes de iniciar la navegacion.
                 * La evidencia identifica perfil y usuario sin exponer la contrasena.
                 *
                 * @param url URL de inicio de sesion de Access Control.
                 * @param user usuario recibido desde el data provider.
                 * @param userEsperado usuario oficial del perfil segun la matriz.
                 * @param perfil nombre del perfil que se ejecuta.
                 * @param password contrasena recuperada desde la configuracion existente.
                 * @return true cuando la URL, el usuario y la contrasena requeridos estan configurados.
                 *
                 * @author Mauricio Gurrola
                 * @date 2026-07-17
                 */
                public boolean validarPrecondicionPerfil(String url, String user, String userEsperado, String perfil,
                    String password) {
                try {
                    boolean urlConfigurada = url != null && !url.trim().isEmpty();
                    boolean resultadoPaso = urlConfigurada && userEsperado.equals(user)
                        && password != null && !password.trim().isEmpty();
                    System.out.println("[PERFIL EN PRUEBA] Perfil: " + perfil + " | Usuario: " + user);
                    testCase.addBoldTextEvidenceCurrentStep("Perfil en prueba: " + perfil + " | Usuario: " + user);
                    registrarResultado(resultadoPaso,
                        "El perfil " + perfil + " esta configurado para el usuario " + user,
                        "La configuracion no corresponde al perfil " + perfil + ". URL configurada: "
                            + urlConfigurada + " | Usuario esperado: " + userEsperado + " | Usuario actual: " + user,
                        "Precondicion_perfil_" + perfil);
                    return resultadoPaso;
                } catch (Exception exception) {
                    registrarError("No fue posible validar la precondicion del perfil " + perfil + ": "
                        + exception.getMessage(), "Error_precondicion_perfil_" + perfil);
                    return false;
                }
                }

            /**
             * Ingresa a Access Control y valida que la pantalla de inicio de sesion quede disponible.
             *
             * @param url URL de inicio de sesion recibida desde el data provider.
             * @return true cuando los campos de autenticacion se muestran correctamente.
             *
             * @author Mauricio Gurrola
             * @date 2026-07-17
             */
            public boolean ingresarUrlYValidarLogin(String url) {
                try {
                    boolean resultadoPaso = portalCometa.IngresarURL(url, false);
                    registrarResultado(resultadoPaso,
                            "El sistema muestra la pantalla de inicio de sesion del portal Access Control",
                            "La pantalla de inicio de sesion de Access Control no esta disponible",
                            "Pantalla_inicio_sesion_Access_Control", portalCometa.xmlUserName,
                            portalCometa.xmlPassword);
                    return resultadoPaso;
                } catch (Exception exception) {
                    registrarError("No fue posible ingresar a la URL de Access Control: " + exception.getMessage(),
                            "Error_ingreso_Access_Control");
                    return false;
                }
            }

    private void registrarResultado(boolean resultadoPaso, String mensajeExito, String motivoFallo,
            String nombreCaptura, By... locators) {
        String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
        String evidencia = resultadoPaso ? mensajeExito : "Motivo: " + motivoFallo;
        System.out.println("[RESULTADO PASO] [" + estadoPaso + "] - " + evidencia);
        testCase.addBoldTextEvidenceCurrentStep(
            "Resultado paso: [" + estadoPaso + "] - " + evidencia);
        List<By> locatorsResaltados = new ArrayList<By>();
        try {
            for (By locator : locators) {
                if (u.getDriver().findElements(locator).stream().anyMatch(WebElement::isDisplayed)) {
                    u.highLight(locator);
                    locatorsResaltados.add(locator);
                }
            }
            testCase.addScreenShotCurrentStep(nombreCaptura);
        } finally {
            for (By locator : locatorsResaltados) {
                try {
                    u.unhighLight(locator);
                } catch (Exception exception) {
                    System.out.println("[WARN] No fue posible retirar el resaltado: " + exception.getMessage());
                }
            }
        }
    }

    private void registrarError(String mensajeError, String nombreCaptura) {
        System.out.println("[RESULTADO PASO] [FAILED] - Motivo: " + mensajeError);
        testCase.addBoldTextEvidenceCurrentStep(
            "Resultado paso: [FAILED] - Motivo: " + mensajeError);
        try {
            testCase.addScreenShotCurrentStep(nombreCaptura);
        } catch (Exception screenshotException) {
            System.out.println("[WARN] No fue posible adjuntar la captura de error: "
                    + screenshotException.getMessage());
        }
    }

    /**
     * Espera que Access Control muestre nuevamente el login despues del logout de Portal COMETA.
     * Si la recarga tarda mas de lo esperado, ejecuta un reintento controlado de URL y valida de nuevo.
     *
     * @param url URL de login de Access Control.
     * @param timeoutSegundos tiempo maximo de espera inicial para detectar el login.
     * @return true cuando se detecta el login por campo email o por URL de Login.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-27
     */
    public boolean esperarPantallaLoginAccessControlTrasLogout(String url, int timeoutSegundos) {
        try {
            new WebDriverWait(u.getDriver(), Duration.ofSeconds(timeoutSegundos)).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.id("email")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='email']"))));
            registrarResultado(true,
                    "Access Control listo tras logout (email o URL de login visible)",
                    "No aplica",
                    "AccessControl_Login_Tras_Logout", portalCometa.xmlUserName);
            return true;
        } catch (TimeoutException timeout) {
            try {
                u.getDriver().navigate().to(url);
                try {
                    portalCometa.PrivatePortal();
                } catch (Exception ignored) {
                    // Si no aparece el aviso de sitio privado, se continua con la espera normal de login.
                }
                new WebDriverWait(u.getDriver(), Duration.ofSeconds(20)).until(ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(By.id("email")),
                        ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='email']"))));
                registrarResultado(true,
                        "Access Control listo tras reintento controlado de URL",
                        "No aplica",
                        "AccessControl_Login_Tras_Logout_Reintento", portalCometa.xmlUserName);
                return true;
            } catch (Exception reintento) {
                registrarError("Reintento de URL no estabilizo Login de Access Control. Motivo: "
                        + reintento.getMessage(), "AccessControl_Login_Tras_Logout_Error");
                return false;
            }
        } catch (Exception exception) {
            registrarError("No fue posible esperar la pantalla de login de Access Control tras logout. Motivo: "
                    + exception.getMessage(), "AccessControl_Login_Tras_Logout_Error");
            return false;
        }
    }

    /**
     * Ejecuta un cierre de sesion robusto desde Portal COMETA y deja lista la pantalla de login de Access Control.
     * Si el flujo primario de logout falla por dropdown/menu, intenta variantes compatibles y luego valida login.
     *
     * @param url URL de login de Access Control.
     * @param timeoutSegundos tiempo maximo para esperar login despues del logout.
     * @return true cuando se logra cerrar sesion y se detecta el login listo para reingresar credenciales.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-27
     */
    public boolean cerrarSesionPortalYPrepararReLogin(String url, int timeoutSegundos) {
        boolean logoutEjecutado = false;
        try {
            logoutEjecutado = Boolean.TRUE.equals(portalCometa.ClicCerrarSesionPortalAccess(true));
        } catch (Exception excepcionLogoutPrincipal) {
                System.out.println("[RESULTADO PASO] [FAILED] - Motivo: Logout principal con ClicCerrarSesionPortalAccess fallo: "
                    + excepcionLogoutPrincipal.getMessage());
        }

        if (!esLoginAccessControlVisible(5)) {
            try {
                boolean logoutAlterno = Boolean.TRUE.equals(portalCometa.ClicCerrarSesionPortal(true));
                logoutEjecutado = logoutEjecutado || logoutAlterno;
                if (logoutAlterno) {
                    System.out.println("[RESULTADO PASO] [PASSED] - Logout alterno ejecutado con ClicCerrarSesionPortal");
                }
            } catch (Exception excepcionLogoutAlterno) {
                System.out.println("[RESULTADO PASO] [FAILED] - Motivo: Logout alterno con ClicCerrarSesionPortal fallo: "
                        + excepcionLogoutAlterno.getMessage());
            }
        }

        if (!esLoginAccessControlVisible(5)) {
            try {
                boolean logoutAlternoFinal = portalCometa.logoutPC(false);
                logoutEjecutado = logoutEjecutado || logoutAlternoFinal;
                if (logoutAlternoFinal) {
                    System.out.println("[RESULTADO PASO] [PASSED] - Logout alterno final ejecutado con logoutPC");
                }
            } catch (Exception excepcionLogoutFinal) {
                System.out.println("[RESULTADO PASO] [FAILED] - Motivo: Logout alterno final con logoutPC fallo: "
                        + excepcionLogoutFinal.getMessage());
            }
        }

        boolean loginDisponible = esperarPantallaLoginAccessControlTrasLogout(url, timeoutSegundos);
        boolean resultadoPaso = logoutEjecutado && loginDisponible;
        registrarResultado(resultadoPaso,
                "Logout completado y login de Access Control listo para reingreso",
                "No se completo el logout o no se detecto login de Access Control. Logout ejecutado: "
                        + logoutEjecutado + " | Login visible: " + loginDisponible,
                "AccessControl_Logout_y_ReLogin", portalCometa.xmlUserName);
        return resultadoPaso;
    }

    /**
     * Verifica si la pantalla de Access Control esta visible por campo email o URL de login.
     *
     * @param timeoutSegundos tiempo maximo de espera para detectar login.
     * @return true cuando el login de Access Control esta visible en pantalla.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-27
     */
    private boolean esLoginAccessControlVisible(int timeoutSegundos) {
        try {
            new WebDriverWait(u.getDriver(), Duration.ofSeconds(timeoutSegundos)).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(By.id("email")),
                    ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='email']"))));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }
}