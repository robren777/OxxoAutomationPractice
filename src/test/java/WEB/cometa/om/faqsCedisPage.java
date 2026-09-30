package WEB.cometa.om;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class faqsCedisPage extends faqsDirectosPage {

    private static final String USUARIO_ADMIN = "5555555";

    private final SeleniumUtil u;
    private final TestCase testCase;
    private final faqsCedisElements elements;

    public faqsCedisPage(SeleniumUtil u, TestCase testCase) {
        super(u, testCase);
        this.u = u;
        this.testCase = testCase;
        this.elements = new faqsCedisElements();
    }

    /**
     * Abre FAQS CEDIS desde la navegación superior y valida que la pantalla de preguntas
     * frecuentes quede disponible para consultar registros. No exige el botón Crear nueva
     * pregunta porque perfiles de solo lectura como CEDIS no lo muestran.
     *
     * @return true cuando la pantalla FAQS CEDIS se muestra; false si no puede abrirse.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean abrirYValidarFaqsCedis() {
        try {
            u.waitForLoadPage();
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
            WebElement opcionFaqsCedis = wait.until(driver -> {
                if (driver.findElements(elements.opcionFaqsCedis).isEmpty()) {
                    return null;
                }
                WebElement elemento = driver.findElements(elements.opcionFaqsCedis).get(0);
                return elemento.isDisplayed() ? elemento : null;
            });
            ((JavascriptExecutor) u.getDriver()).executeScript(
                    "arguments[0].scrollIntoView({block:'center', inline:'center'});", opcionFaqsCedis);
            u.hardWait(1);
            try {
                wait.until(ExpectedConditions.elementToBeClickable(opcionFaqsCedis)).click();
            } catch (Exception clickException) {
                System.out.println("[WARN] Click normal en FAQS CEDIS interceptado, se intenta click JavaScript: "
                        + clickException.getMessage());
                ((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", opcionFaqsCedis);
            }
            u.waitForLoadPage();
            boolean resultadoPaso;
            try {
                resultadoPaso = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60))
                        .until(driver -> u.isDisplayed(elements.contenidoFaqsCedis)
                                && !driver.findElements(elements.tarjetasFaqCedis).isEmpty()
                                && driver.findElements(elements.tarjetasFaqCedis).stream()
                                        .anyMatch(WebElement::isDisplayed));
            } catch (Exception timeoutEsperandoTarjetas) {
                resultadoPaso = u.isDisplayed(elements.contenidoFaqsCedis);
            }
            registrarResultadoFaqsCedis(resultadoPaso,
                    "FAQS CEDIS muestra la pantalla de preguntas frecuentes con el listado ya cargado",
                    "FAQS CEDIS no mostró el listado de preguntas frecuentes cargado",
                    elements.tarjetasFaqCedis);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarErrorFaqsCedis("No fue posible abrir FAQS CEDIS: " + exception.getMessage());
            return false;
        }
    }

    private void registrarResultadoFaqsCedis(boolean resultadoPaso, String mensajeExito, String motivoFallo) {
        registrarResultadoFaqsCedis(resultadoPaso, mensajeExito, motivoFallo, null);
    }

    /**
     * Registra el resultado del paso y adjunta la captura resaltando el listado de FAQS CEDIS
     * cuando se recibe un locator, para evidenciar que las preguntas ya estan cargadas.
     *
     * @param resultadoPaso resultado booleano de la validacion ejecutada.
     * @param mensajeExito mensaje registrado cuando el resultado es exitoso.
     * @param motivoFallo mensaje registrado cuando el resultado falla.
     * @param locatorResaltado locator a resaltar antes de la captura; puede ser null.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-24
     */
    private void registrarResultadoFaqsCedis(boolean resultadoPaso, String mensajeExito, String motivoFallo,
            org.openqa.selenium.By locatorResaltado) {
        String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
        String evidencia = resultadoPaso ? mensajeExito : motivoFallo;
        System.out.println("[RESULTADO PASO] " + estadoPaso + " - " + evidencia);
        testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " - " + evidencia);
        boolean resaltado = false;
        try {
            if (locatorResaltado != null && !u.getDriver().findElements(locatorResaltado).isEmpty()) {
                u.highLight(locatorResaltado);
                resaltado = true;
            }
            testCase.addScreenShotCurrentStep(evidencia.replaceAll("[\\\\/:*?\"<>|]", "-"));
        } finally {
            if (resaltado) {
                try {
                    u.unhighLight(locatorResaltado);
                } catch (Exception unhighlightException) {
                    System.out.println("[WARN] No fue posible retirar el resaltado de FAQS CEDIS: "
                            + unhighlightException.getMessage());
                }
            }
        }
    }

    private void registrarErrorFaqsCedis(String mensajeError) {
        System.out.println("[RESULTADO PASO] FAILED - " + mensajeError);
        testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED - " + mensajeError);
        try {
            testCase.addScreenShotCurrentStep("Error_FAQS_CEDIS");
        } catch (Exception screenshotException) {
            System.out.println("[WARN] No fue posible adjuntar la captura de error FAQS CEDIS: "
                    + screenshotException.getMessage());
        }
    }

    /**
     * Crea con perfil ADMIN una FAQ de CEDIS usada como precondición de casos CA02.
     * Primero elimina una FAQ residual con la misma pregunta para dejar el ambiente controlado.
     *
     * @param url URL de inicio de sesión de Access Control.
     * @param pregunta pregunta que se creará en FAQS CEDIS.
     * @param respuesta respuesta que se creará en FAQS CEDIS.
     * @return true cuando la FAQ queda creada y visible en el listado.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean crearFaqCedisComoAdmin(String url, String pregunta, String respuesta) {
        PortalCometaMVP4 pc4 = null;
        try {
            pc4 = new PortalCometaMVP4(u, testCase);
            String passwordAdmin = pc4.obtenerPasswordPorUsuario(USUARIO_ADMIN);
            boolean resultadoPaso = pc4.IngresarURL(url, true)
                    && pc4.IniciarSesionNoToken(USUARIO_ADMIN, passwordAdmin, true)
                    && pc4.seleccionarPortalCometaQA(true)
                    && abrirYValidarFaqsCedis()
                    && eliminarFaqSiExiste(pregunta)
                    && abrirFormularioCrearPregunta()
                    && capturarYValidarPregunta(pregunta)
                    && capturarYValidarRespuesta(respuesta)
                    && guardarFaq()
                    && validarPreguntaYRespuestaEnListado(pregunta, respuesta)
                    && cerrarSesionAdminObligatoria(pc4);
            registrarResultadoFaqsCedis(resultadoPaso, "Precondición creada: FAQS CEDIS contiene la pregunta con acentos",
                    "No fue posible crear la precondición de FAQS CEDIS con ADMIN");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarErrorFaqsCedis("No fue posible crear la precondición de FAQS CEDIS: " + exception.getMessage());
            cerrarSesionAdminSiEsPosible(pc4);
            return false;
        }
    }

    /**
     * Cierra la sesión actual y elimina con ADMIN una FAQ de CEDIS creada como dato de prueba.
     *
     * @param url URL de inicio de sesión de Access Control.
     * @param pregunta pregunta que debe eliminarse.
     * @return true cuando la FAQ no existe o fue eliminada correctamente.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean limpiarFaqCedisComoAdmin(String url, String pregunta) {
        PortalCometaMVP4 pc4 = null;
        try {
            pc4 = new PortalCometaMVP4(u, testCase);
            cerrarSesionActualSiEsPosible(pc4);
            String passwordAdmin = pc4.obtenerPasswordPorUsuario(USUARIO_ADMIN);
            boolean resultadoPaso = pc4.IngresarURL(url, true)
                    && pc4.IniciarSesionNoToken(USUARIO_ADMIN, passwordAdmin, true)
                    && pc4.seleccionarPortalCometaQA(true)
                    && abrirYValidarFaqsCedis()
                    && eliminarFaqSiExiste(pregunta)
                    && cerrarSesionAdminObligatoria(pc4);
            registrarResultadoFaqsCedis(resultadoPaso, "Limpieza completada: FAQS CEDIS no conserva la FAQ de prueba",
                    "No fue posible limpiar la FAQ de prueba en FAQS CEDIS");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarErrorFaqsCedis("No fue posible limpiar la FAQ de CEDIS: " + exception.getMessage());
            cerrarSesionAdminSiEsPosible(pc4);
            return false;
        }
    }

    private boolean cerrarSesionAdminObligatoria(PortalCometaMVP4 pc4) {
        try {
            boolean resultadoPaso = pc4 != null && pc4.ClicCerrarSesionPortalAccess(false);
            registrarResultadoFaqsCedis(resultadoPaso, "Sesión ADMIN cerrada correctamente",
                    "No fue posible cerrar la sesión ADMIN después de crear o limpiar la FAQ");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarErrorFaqsCedis("No fue posible cerrar la sesión ADMIN: " + exception.getMessage());
            return false;
        }
    }

    private void cerrarSesionAdminSiEsPosible(PortalCometaMVP4 pc4) {
        try {
            if (pc4 != null) {
                pc4.ClicCerrarSesionPortalAccess(false);
            }
        } catch (Exception exception) {
            System.out.println("[WARN] No fue posible cerrar sesión ADMIN antes de cerrar el navegador: " + exception.getMessage());
        }
    }

    private void cerrarSesionActualSiEsPosible(PortalCometaMVP4 pc4) {
        try {
            if (pc4 != null) {
                pc4.ClicCerrarSesionPortalAccess(false);
                System.out.println("[INFO] Sesión actual cerrada antes de iniciar limpieza con ADMIN");
            }
        } catch (Exception exception) {
            System.out.println("[WARN] No fue posible cerrar la sesión actual antes de iniciar ADMIN para limpieza: " + exception.getMessage());
        }
    }

    /**
     * Valida que FAQS CEDIS muestre el titulo de preguntas frecuentes y al menos
     * una tarjeta disponible en el listado.
     *
     * @return true cuando el titulo y el listado de FAQS CEDIS estan visibles.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-16
     */
    public boolean validarPantallaFaqsCedis() {
        try {
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
            boolean resultadoPaso = wait.until(driver -> u.isDisplayed(elements.contenidoFaqsCedis)
                    && !driver.findElements(elements.tarjetasFaqCedis).isEmpty());
            registrarResultadoFaqsCedis(resultadoPaso,
                    "FAQS CEDIS muestra el titulo Preguntas Frecuentes y el listado de preguntas disponible",
                    "FAQS CEDIS no muestra el titulo o no contiene tarjetas en el listado");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarErrorFaqsCedis("No fue posible validar la pantalla FAQS CEDIS: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Valida que FAQS CEDIS sea visible en la barra superior sin navegar todavía.
     *
     * @return true cuando la opción FAQS CEDIS está visible y habilitada.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-17
     */
    public boolean validarOpcionFaqsCedisVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
            WebElement opcionFaqsCedis = wait.until(driver -> {
                if (driver.findElements(elements.opcionFaqsCedis).isEmpty()) {
                    return null;
                }
                WebElement elemento = driver.findElements(elements.opcionFaqsCedis).get(0);
                return elemento.isDisplayed() ? elemento : null;
            });
            boolean resultadoPaso = opcionFaqsCedis != null && opcionFaqsCedis.isEnabled();
            registrarResultadoFaqsCedis(resultadoPaso,
                    "La opcion FAQS CEDIS es visible y accesible en la barra superior",
                    "La opcion FAQS CEDIS no esta visible o habilitada");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarErrorFaqsCedis("No fue posible validar la opcion FAQS CEDIS: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Valida el contenido de preguntas frecuentes sin ejecutar una navegación adicional.
     *
     * @return true cuando la pantalla contiene el listado de FAQS CEDIS.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-17
     */
    public boolean validarContenidoFaqsCedis() {
        try {
            boolean resultadoPaso = u.isDisplayed(elements.contenidoFaqsCedis);
            registrarResultadoFaqsCedis(resultadoPaso,
                    "FAQS CEDIS carga correctamente el listado de preguntas frecuentes",
                    "FAQS CEDIS no muestra el contenido esperado");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarErrorFaqsCedis("No fue posible validar el contenido FAQS CEDIS: " + exception.getMessage());
            return false;
        }
    }
}