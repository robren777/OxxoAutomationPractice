package WEB.cometa.om;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class faqsDirectosPage {

    private static final String USUARIO_ADMIN = "5555555";

    private final SeleniumUtil u;
    private final TestCase testCase;
    private final faqsDirectosElements elements;

    public faqsDirectosPage(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
        this.elements = new faqsDirectosElements();
    }

    /**
     * Abre FAQS Directos y confirma que la pantalla de preguntas frecuentes quedó disponible.
     *
     * @return true cuando la pantalla FAQS Directos se muestra; false si no puede abrirse.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-14
     */
    public boolean abrirYValidarFaqsDirectos() {
        try {
            u.click(elements.opcionFaqsDirectos);
            u.waitForLoadPage();
            boolean resultadoPaso = u.isDisplayed(elements.contenidoFaqsDirectos);
            registrarResultado(resultadoPaso, "FAQS Directos muestra la pantalla de preguntas frecuentes",
                    "FAQS Directos no mostró la pantalla de preguntas frecuentes");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible abrir FAQS Directos: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Abre el formulario de creación de una pregunta frecuente.
     *
     * @return true cuando el campo Pregunta queda visible; false si no se abre el formulario.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-14
     */
    public boolean abrirFormularioCrearPregunta() {
        try {
            esperarElementoClickeable(elements.botonCrearNuevaPregunta).click();
            boolean resultadoPaso = esperarElementoVisible(elements.campoPregunta).isDisplayed();
            registrarResultado(resultadoPaso, "El formulario para crear una FAQ quedó abierto",
                    "No se mostró el formulario para crear una FAQ");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible abrir el formulario de creación: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Confirma que el campo Pregunta puede recibir texto.
     *
     * @return true cuando el campo Pregunta está visible y habilitado; false en otro caso.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-14
     */
    public boolean enfocarCampoPregunta() {
        try {
            WebElement campo = esperarElementoClickeable(elements.campoPregunta);
            campo.click();
            boolean resultadoPaso = campo.isDisplayed() && campo.isEnabled();
            registrarResultado(resultadoPaso, "El campo Pregunta recibe el foco y está listo para texto",
                    "El campo Pregunta no recibió el foco o no está habilitado");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible enfocar el campo Pregunta: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Captura y valida el texto completo de una pregunta en el formulario.
     *
     * @param pregunta texto que debe capturarse en el campo Pregunta.
     * @return true cuando el valor del campo coincide exactamente con el texto recibido.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-14
     */
    public boolean capturarYValidarPregunta(String pregunta) {
        try {
            WebElement campo = esperarElementoClickeable(elements.campoPregunta);
            String valorActual = escribirYObtenerValor(campo, pregunta);
            boolean resultadoPaso = pregunta.equals(valorActual);
            registrarResultado(resultadoPaso, "El campo Pregunta conserva todos los caracteres acentuados capturados",
                    "Pregunta distinta. Esperado: " + pregunta + " | Actual: " + valorActual);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible capturar la Pregunta: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Captura y valida el texto completo de una respuesta en el formulario.
     *
     * @param respuesta texto que debe capturarse en el campo Respuesta.
     * @return true cuando el valor del campo coincide exactamente con el texto recibido.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-14
     */
    public boolean capturarYValidarRespuesta(String respuesta) {
        try {
            WebElement campo = esperarElementoClickeable(elements.campoRespuesta);
            String valorActual = escribirYObtenerValor(campo, respuesta);
            boolean resultadoPaso = respuesta.equals(valorActual);
            registrarResultado(resultadoPaso, "El campo Respuesta conserva todos los caracteres acentuados capturados",
                    "Respuesta distinta. Esperado: " + respuesta + " | Actual: " + valorActual);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible capturar la Respuesta: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Guarda la FAQ creada y espera el regreso al listado de preguntas frecuentes.
     *
     * @return true cuando el formulario se cierra y se muestra el listado; false si no se guarda.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-14
     */
    public boolean guardarFaq() {
        try {
            esperarElementoClickeable(elements.botonGuardar).click();
            new WebDriverWait(u.getDriver(), Duration.ofSeconds(15))
                    .until(ExpectedConditions.invisibilityOfElementLocated(elements.campoPregunta));
            boolean resultadoPaso = u.isDisplayed(elements.contenidoFaqsDirectos);
            registrarResultado(resultadoPaso, "La FAQ se guardó y el formulario se cerró",
                    "La FAQ no se guardó o el formulario continuó abierto");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible guardar la FAQ: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Verifica que una FAQ guardada se visualice en el listado con su texto exacto.
     *
     * @param pregunta texto completo que debe aparecer en el listado.
     * @return true cuando el título de la FAQ está visible y coincide con el texto esperado.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-14
     */
    public boolean validarPreguntaEnListado(String pregunta) {
        try {
            By preguntaEsperada = By.xpath("//*[normalize-space()=" + literalXpath(pregunta) + "]");
            WebElement elemento = esperarElementoVisible(preguntaEsperada);
            u.scrollToElement(preguntaEsperada);
            boolean resultadoPaso = pregunta.equals(elemento.getText().trim());
            registrarResultado(resultadoPaso, "La FAQ se visualiza en el listado con caracteres acentuados correctos",
                    "El listado no muestra la pregunta esperada. Esperado: " + pregunta + " | Actual: " + elemento.getText());
            return resultadoPaso;
        } catch (Exception exception) {
            String contenidoTarjeta = obtenerContenidoTarjetaPorPrefijo(pregunta);
            String detalleTarjeta = contenidoTarjeta != null
                    ? " | Contenido real guardado en la tarjeta encontrada: " + contenidoTarjeta
                    : " | No se localizó ninguna tarjeta relacionada con el prefijo de la pregunta";
            registrarError("No se localizó la FAQ creada en el listado: " + exception.getMessage() + detalleTarjeta);
            return false;
        }
    }

    /**
     * Busca una tarjeta de FAQ cuyo texto comience con un prefijo de la pregunta esperada e
     * imprime su contenido completo como evidencia de diagnóstico. Permite distinguir si un
     * fallo de coincidencia se debe a truncamiento por límite de longitud en backend/base de
     * datos u otra alteración distinta a la pérdida de caracteres acentuados.
     *
     * @param pregunta texto completo esperado; se toma un prefijo para localizar la tarjeta.
     * @return texto completo de la tarjeta encontrada, o null si no se localiza ninguna coincidencia.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    private String obtenerContenidoTarjetaPorPrefijo(String pregunta) {
        try {
            String prefijo = pregunta.length() > 15 ? pregunta.substring(0, 15) : pregunta;
            for (WebElement tarjeta : u.getDriver().findElements(elements.tarjetasFaq)) {
                String textoTarjeta = tarjeta.getText().trim();
                if (textoTarjeta.startsWith(prefijo)) {
                    System.out.println("[DIAGNOSTICO] Contenido real guardado en la tarjeta: " + textoTarjeta);
                    return textoTarjeta;
                }
            }
        } catch (Exception exception) {
            System.out.println("[WARN] No fue posible inspeccionar las tarjetas de FAQ: " + exception.getMessage());
        }
        return null;
    }

    /**
     * Elimina la FAQ identificada por su pregunta, resaltando en rojo la tarjeta localizada y el
     * botón de basurero antes de dar clic, valida que el diálogo de confirmación muestre la
     * pregunta correcta antes de confirmar, espera el overlay "Espere un momento" y confirma que
     * la FAQ ya no se visualice en el listado.
     *
     * @param pregunta texto de la FAQ que debe eliminarse.
     * @return true cuando la FAQ deja de estar presente en el listado.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean eliminarFaqPorPregunta(String pregunta) {
        try {
            String tarjetaXpath = "//*[contains(concat(' ', normalize-space(@class), ' '), ' faq-card ')][.//*[normalize-space()="
                    + literalXpath(pregunta) + "]]";
            By tarjetaFaq = By.xpath(tarjetaXpath);
            By botonEliminarTarjeta = By.xpath("(" + tarjetaXpath + "//button)[last()]");

            if (!buscarFaqEnListado(tarjetaFaq)) {
                registrarError("No se localizó la FAQ para eliminar después de recorrer el listado: " + pregunta);
                return false;
            }
            esperarElementoVisible(tarjetaFaq);
            u.scrollToElement(tarjetaFaq);
            u.highLight(tarjetaFaq);
            u.hardWait(1);
            testCase.addTextEvidenceCurrentStep("FAQ localizada para eliminar: " + pregunta);
            testCase.addScreenShotCurrentStep("FAQ_localizada_para_eliminar");
            u.unhighLight(tarjetaFaq);

            WebElement botonEliminar = esperarElementoClickeable(botonEliminarTarjeta);
            u.highLight(botonEliminarTarjeta);
            u.hardWait(1);
            testCase.addScreenShotCurrentStep("Boton_eliminar_resaltado");
            botonEliminar.click();

            By preguntaEnDialogo = By.xpath("//mat-dialog-container//*[normalize-space()=" + literalXpath(pregunta) + "]");
            WebElement previewDialogo = esperarElementoVisible(preguntaEnDialogo);
            boolean previewCoincide = pregunta.equals(previewDialogo.getText().trim());
            if (!previewCoincide) {
                registrarError("El dialogo de confirmacion muestra una pregunta distinta a la esperada. Esperado: "
                        + pregunta + " | Actual: " + previewDialogo.getText());
                return false;
            }
            testCase.addTextEvidenceCurrentStep("El dialogo de confirmacion muestra la pregunta correcta: " + pregunta);

            u.highLight(elements.botonConfirmarEliminar);
            u.hardWait(1);
            testCase.addScreenShotCurrentStep("Confirmar_eliminacion_resaltado");
            esperarElementoClickeable(elements.botonConfirmarEliminar).click();

                new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(elements.botonConfirmarEliminar));
            new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                            "//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
                u.waitForLoadPage();

                boolean resultadoPaso = u.getDriver().findElements(tarjetaFaq).isEmpty()
                    || new WebDriverWait(u.getDriver(), Duration.ofSeconds(15))
                        .until(ExpectedConditions.invisibilityOfElementLocated(tarjetaFaq));
                registrarResultado(resultadoPaso, "La FAQ de prueba fue eliminada del listado de FAQS",
                    "La FAQ de prueba continúa visible después de confirmar la eliminación");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible eliminar la FAQ de prueba: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Elimina una FAQ del listado únicamente si existe, permitiendo limpiar datos residuales de
     * ejecuciones previas sin fallar cuando la tarjeta no está presente.
     *
     * @param pregunta texto exacto de la FAQ que se desea limpiar.
     * @return true cuando la FAQ no existe o fue eliminada correctamente.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean eliminarFaqSiExiste(String pregunta) {
        try {
            By tarjetaFaq = By.xpath("//*[contains(concat(' ', normalize-space(@class), ' '), ' faq-card ')][.//*[normalize-space()="
                    + literalXpath(pregunta) + "]]");
                if (!buscarFaqEnListado(tarjetaFaq)) {
                registrarResultado(true, "No existe FAQ residual para limpiar: " + pregunta,
                        "No aplica limpieza de FAQ residual");
                return true;
            }
            for (int intento = 1; intento <= 3; intento++) {
                System.out.println("[LIMPIEZA FAQ] Intento " + intento + " para eliminar: " + pregunta);
                boolean eliminado = eliminarFaqPorPregunta(pregunta);
                u.waitForLoadPage();
                if (eliminado && !buscarFaqEnListado(tarjetaFaq)) {
                    registrarResultado(true, "La FAQ ya no está visible después de la limpieza: " + pregunta,
                            "La FAQ permanece visible después de limpieza");
                    return true;
                }
                u.hardWait(2);
            }
            boolean resultadoPaso = !buscarFaqEnListado(tarjetaFaq);
            registrarResultado(resultadoPaso, "La FAQ ya no está visible después de reintentos de limpieza: " + pregunta,
                    "La FAQ permanece visible después de 3 intentos de limpieza: " + pregunta);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible validar o limpiar la FAQ residual: " + exception.getMessage());
            return false;
        }
    }

    private WebElement esperarElementoVisible(By localizador) {
        return new WebDriverWait(u.getDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(localizador));
    }

    private boolean buscarFaqEnListado(By tarjetaFaq) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
            js.executeScript("window.scrollTo(0, 0);");
            u.hardWait(1);
            for (int intento = 0; intento < 15; intento++) {
                java.util.List<WebElement> tarjetas = u.getDriver().findElements(tarjetaFaq);
                if (!tarjetas.isEmpty()) {
                    u.scrollToElement(tarjetaFaq);
                    return true;
                }
                js.executeScript("window.scrollBy(0, Math.max(500, window.innerHeight * 0.75));");
                u.hardWait(1);
            }
            return false;
        } catch (Exception exception) {
            System.out.println("[WARN] No fue posible recorrer el listado para buscar FAQ: " + exception.getMessage());
            return !u.getDriver().findElements(tarjetaFaq).isEmpty();
        }
    }

    private WebElement esperarElementoClickeable(By localizador) {
        return new WebDriverWait(u.getDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.elementToBeClickable(localizador));
    }

    private String literalXpath(String texto) {
        if (!texto.contains("'")) {
            return "'" + texto + "'";
        }
        return "concat('" + texto.replace("'", "',\"'\",'") + "')";
    }

    /**
     * Captura un texto en el campo pegándolo desde el portapapeles (Ctrl+V) y, si no queda
     * disponible el portapapeles, tipiándolo carácter por carácter con reintentos. El pegado
     * evita que la traducción de teclas por distribución de teclado descarte caracteres durante
     * el tipiado rápido, garantizando una captura confiable de vocales acentuadas.
     *
     * @param campo elemento de entrada donde se captura el texto.
     * @param texto texto completo que debe quedar reflejado en el campo.
     * @return valor final leído del campo tras el último intento de captura.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    private String escribirYObtenerValor(WebElement campo, String texto) {
        String valorActual = "";
        try {
            StringSelection seleccion = new StringSelection(texto);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(seleccion, null);
            campo.click();
            campo.clear();
            new Actions(u.getDriver())
                    .keyDown(Keys.CONTROL)
                    .sendKeys("v")
                    .keyUp(Keys.CONTROL)
                    .perform();
            valorActual = campo.getAttribute("value");
            if (texto.equals(valorActual)) {
                return valorActual;
            }
        } catch (Exception exception) {
            System.out.println("[WARN] No fue posible pegar el texto desde el portapapeles: " + exception.getMessage());
        }

        for (int intento = 1; intento <= 3; intento++) {
            campo.clear();
            for (int indice = 0; indice < texto.length(); indice++) {
                campo.sendKeys(String.valueOf(texto.charAt(indice)));
            }
            valorActual = campo.getAttribute("value");
            if (texto.equals(valorActual)) {
                return valorActual;
            }
        }
        return valorActual;
    }

    /**
     * Sustituye los caracteres no válidos para nombres de archivo en Windows (\\ / : * ? " < > |)
     * y acota la longitud, de modo que la evidencia de un paso fallido con texto acentuado o con
     * signos de interrogación no rompa la generación de la captura de pantalla.
     *
     * @param texto texto original de la evidencia.
     * @return texto sanitizado seguro para usarse como nombre de archivo.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    private String sanitizarNombreArchivo(String texto) {
        if (texto == null) {
            return "";
        }
        String sanitizado = texto.replaceAll("[\\\\/:*?\"<>|]", "-");
        if (sanitizado.length() > 150) {
            sanitizado = sanitizado.substring(0, 150);
        }
        return sanitizado;
    }

    private void registrarResultado(boolean resultadoPaso, String mensajeExito, String motivoFallo) {
        String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
        String evidencia = resultadoPaso ? mensajeExito : motivoFallo;
        System.out.println("[RESULTADO PASO] " + estadoPaso + " - " + evidencia);
        testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " - " + evidencia);
        testCase.addScreenShotCurrentStep(sanitizarNombreArchivo(evidencia));
    }

    private void registrarError(String mensajeError) {
        System.out.println("[RESULTADO PASO] FAILED - " + mensajeError);
        testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED - " + mensajeError);
        try {
            testCase.addScreenShotCurrentStep("Error_FAQS_Directos");
        } catch (Exception screenshotException) {
            System.out.println("[WARN] No fue posible adjuntar la captura de error: "
                    + screenshotException.getMessage());
        }
    }

    /**
     * Abre el formulario de edición de la primera FAQ visible en el listado de preguntas.
     * Se utiliza para validar edición de un registro existente y restaurarlo al final de la prueba.
     *
     * @return true cuando el formulario de edición queda visible con el campo Pregunta disponible.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean abrirFormularioEditarPrimeraFaq() {
        try {
            By primeraTarjeta = By.xpath("(//*[contains(concat(' ', normalize-space(@class), ' '), ' faq-card ')])[1]");
            By botonEditar = By.xpath("((//*[contains(concat(' ', normalize-space(@class), ' '), ' faq-card ')])[1]//button)[1]");
            esperarElementoVisible(primeraTarjeta);
            u.scrollToElement(primeraTarjeta);
            u.highLight(primeraTarjeta);
            u.hardWait(1);
            testCase.addScreenShotCurrentStep("FAQ_existente_seleccionada_para_editar");
            u.unhighLight(primeraTarjeta);
            esperarElementoClickeable(botonEditar).click();
            boolean resultadoPaso = esperarElementoVisible(elements.campoPregunta).isDisplayed();
            registrarResultado(resultadoPaso, "El formulario de edición se abre mostrando el contenido actual de la FAQ",
                    "No se abrió el formulario de edición de la primera FAQ");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible abrir el formulario de edición de la primera FAQ: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Lee el contenido actual del campo Pregunta en el formulario de edición.
     *
     * @return texto actual del campo Pregunta; cadena vacía si no fue posible leerlo.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public String obtenerPreguntaActualFormulario() {
        try {
            String valorActual = esperarElementoVisible(elements.campoPregunta).getAttribute("value");
            boolean resultadoPaso = valorActual != null && !valorActual.trim().isEmpty();
            registrarResultado(resultadoPaso, "Pregunta original documentada: " + valorActual,
                    "El campo Pregunta original está vacío o no pudo leerse");
            return valorActual == null ? "" : valorActual;
        } catch (Exception exception) {
            registrarError("No fue posible leer la Pregunta original: " + exception.getMessage());
            return "";
        }
    }

    /**
     * Lee el contenido actual del campo Respuesta en el formulario de edición.
     *
     * @return texto actual del campo Respuesta; cadena vacía si no fue posible leerlo.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public String obtenerRespuestaActualFormulario() {
        try {
            String valorActual = esperarElementoVisible(elements.campoRespuesta).getAttribute("value");
            boolean resultadoPaso = valorActual != null && !valorActual.trim().isEmpty();
            registrarResultado(resultadoPaso, "Respuesta original documentada: " + valorActual,
                    "El campo Respuesta original está vacío o no pudo leerse");
            return valorActual == null ? "" : valorActual;
        } catch (Exception exception) {
            registrarError("No fue posible leer la Respuesta original: " + exception.getMessage());
            return "";
        }
    }

    /**
     * Agrega caracteres al final del campo Pregunta y valida que el texto final conserve los acentos.
     *
     * @param textoAgregado caracteres que se agregan al final de la Pregunta.
     * @return true cuando el campo Pregunta muestra el texto original más los caracteres agregados.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean agregarTextoAlFinalPregunta(String textoAgregado) {
        try {
            WebElement campo = esperarElementoClickeable(elements.campoPregunta);
            String original = campo.getAttribute("value");
            String esperado = (original == null ? "" : original) + textoAgregado;
            String valorActual = escribirYObtenerValor(campo, esperado);
            boolean resultadoPaso = esperado.equals(valorActual);
            registrarResultado(resultadoPaso, "El campo Pregunta muestra los caracteres acentuados agregados al final",
                    "Pregunta editada distinta. Esperado: " + esperado + " | Actual: " + valorActual);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible agregar texto al final de Pregunta: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Agrega texto al inicio del campo Respuesta y valida que el texto final conserve los acentos.
     *
     * @param textoAgregado texto que se agrega al inicio de la Respuesta.
     * @return true cuando el campo Respuesta muestra el texto agregado seguido del contenido previo.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean agregarTextoAlInicioRespuesta(String textoAgregado) {
        try {
            WebElement campo = esperarElementoClickeable(elements.campoRespuesta);
            String original = campo.getAttribute("value");
            String esperado = textoAgregado + (original == null ? "" : original);
            String valorActual = escribirYObtenerValor(campo, esperado);
            boolean resultadoPaso = esperado.equals(valorActual);
            registrarResultado(resultadoPaso, "El campo Respuesta muestra el texto acentuado agregado al inicio",
                    "Respuesta editada distinta. Esperado: " + esperado + " | Actual: " + valorActual);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible agregar texto al inicio de Respuesta: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Abre el formulario de edición de la FAQ localizada por su pregunta exacta en el listado.
     *
     * @param pregunta texto exacto de la FAQ que debe editarse.
     * @return true cuando el formulario de edición queda visible para la FAQ indicada.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean abrirFormularioEditarFaqPorPregunta(String pregunta) {
        try {
            String tarjetaXpath = "//*[contains(concat(' ', normalize-space(@class), ' '), ' faq-card ')][.//*[normalize-space()="
                    + literalXpath(pregunta) + "]]";
            By tarjetaFaq = By.xpath(tarjetaXpath);
            By botonEditar = By.xpath("(" + tarjetaXpath + "//button)[1]");
            esperarElementoVisible(tarjetaFaq);
            u.scrollToElement(tarjetaFaq);
            u.highLight(tarjetaFaq);
            u.hardWait(1);
            testCase.addScreenShotCurrentStep("FAQ_editada_localizada_para_restaurar");
            u.unhighLight(tarjetaFaq);
            esperarElementoClickeable(botonEditar).click();
            boolean resultadoPaso = esperarElementoVisible(elements.campoPregunta).isDisplayed();
            registrarResultado(resultadoPaso, "El formulario de edición se abre para restaurar la FAQ indicada",
                    "No se abrió el formulario de edición para restaurar la FAQ indicada");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible abrir la FAQ editada para restaurarla: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Restaura los campos Pregunta y Respuesta con sus valores originales y guarda la edición.
     *
     * @param preguntaOriginal texto original de la Pregunta.
     * @param respuestaOriginal texto original de la Respuesta.
     * @return true cuando ambos campos quedan restaurados y el formulario se guarda correctamente.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean restaurarPreguntaYRespuestaOriginal(String preguntaOriginal, String respuestaOriginal) {
        try {
            boolean preguntaRestaurada = capturarYValidarPregunta(preguntaOriginal);
            boolean respuestaRestaurada = capturarYValidarRespuesta(respuestaOriginal);
            boolean guardado = guardarFaq();
            boolean resultadoPaso = preguntaRestaurada && respuestaRestaurada && guardado;
            registrarResultado(resultadoPaso, "La FAQ fue restaurada con su contenido original",
                    "No fue posible restaurar la FAQ con su contenido original");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible restaurar la FAQ original: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Valida que una FAQ del listado muestre exactamente la pregunta y respuesta esperadas.
     * También desplaza la pantalla hasta la tarjeta encontrada para dejar evidencia visual clara.
     *
     * @param pregunta texto exacto de la pregunta esperada.
     * @param respuesta texto exacto de la respuesta esperada.
     * @return true cuando la tarjeta contiene ambos textos sin alteraciones.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean validarPreguntaYRespuestaEnListado(String pregunta, String respuesta) {
        try {
            String tarjetaXpath = "//*[contains(concat(' ', normalize-space(@class), ' '), ' faq-card ')][.//*[normalize-space()="
                    + literalXpath(pregunta) + "] and .//*[normalize-space()=" + literalXpath(respuesta) + "]]";
            By tarjetaFaq = By.xpath(tarjetaXpath);
            esperarElementoVisible(tarjetaFaq);
            u.scrollToElement(tarjetaFaq);
            u.highLight(tarjetaFaq);
            u.hardWait(1);
            WebElement tarjeta = u.getDriver().findElement(tarjetaFaq);
            String textoTarjeta = tarjeta.getText().trim();
            boolean resultadoPaso = textoTarjeta.contains(pregunta) && textoTarjeta.contains(respuesta);
            registrarResultado(resultadoPaso, "La FAQ muestra pregunta y respuesta con caracteres acentuados correctos",
                    "La FAQ no muestra los textos esperados. Esperado pregunta: " + pregunta + " | Esperado respuesta: "
                            + respuesta + " | Actual: " + textoTarjeta);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible validar pregunta y respuesta en el listado: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Recorre las tarjetas visibles del listado de FAQS y valida que no existan caracteres
     * corruptos o sustituciones comunes al renderizar acentos.
     *
     * @return true cuando todas las tarjetas visibles están libres de textos corruptos.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean validarListadoFaqsSinCaracteresCorruptos() {
        try {
            java.util.List<WebElement> tarjetas = u.getDriver().findElements(elements.tarjetasFaq);
            if (tarjetas.isEmpty()) {
                registrarError("No se encontraron tarjetas de FAQ para validar caracteres corruptos");
                return false;
            }
            String textoCorrupto = "";
            for (WebElement tarjeta : tarjetas) {
                String textoTarjeta = tarjeta.getText();
                if (textoTarjeta != null && (textoTarjeta.contains("???") || textoTarjeta.contains("�")
                        || textoTarjeta.contains("□") || textoTarjeta.contains("Ã"))) {
                    textoCorrupto = textoTarjeta;
                    break;
                }
            }
            boolean resultadoPaso = textoCorrupto.trim().isEmpty();
            registrarResultado(resultadoPaso, "El listado visible de FAQS no muestra caracteres corruptos ni sustituciones",
                    "Se detectaron caracteres corruptos en el listado de FAQS. Actual: " + textoCorrupto);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible validar caracteres corruptos en el listado de FAQS: " + exception.getMessage());
            return false;
        }
    }

    /**
     * Crea con perfil ADMIN una FAQ de Directos usada como precondición de casos CA02.
     * Primero elimina una FAQ residual con la misma pregunta para dejar el ambiente controlado.
     *
     * @param url URL de inicio de sesión de Access Control.
     * @param pregunta pregunta que se creará en FAQS Directos.
     * @param respuesta respuesta que se creará en FAQS Directos.
     * @return true cuando la FAQ queda creada y visible en el listado.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean crearFaqDirectosComoAdmin(String url, String pregunta, String respuesta) {
        PortalCometaMVP4 pc4 = null;
        try {
            pc4 = new PortalCometaMVP4(u, testCase);
            String passwordAdmin = pc4.obtenerPasswordPorUsuario(USUARIO_ADMIN);
            boolean resultadoPaso = pc4.IngresarURL(url, true)
                    && pc4.IniciarSesionNoToken(USUARIO_ADMIN, passwordAdmin, true)
                    && pc4.seleccionarPortalCometaQA(true)
                    && abrirYValidarFaqsDirectos()
                    && eliminarFaqSiExiste(pregunta)
                    && abrirFormularioCrearPregunta()
                    && capturarYValidarPregunta(pregunta)
                    && capturarYValidarRespuesta(respuesta)
                    && guardarFaq()
                    && validarPreguntaYRespuestaEnListado(pregunta, respuesta)
                    && cerrarSesionAdminObligatoria(pc4);
            registrarResultado(resultadoPaso, "Precondición creada: FAQS Directos contiene la pregunta con acentos",
                    "No fue posible crear la precondición de FAQS Directos con ADMIN");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible crear la precondición de FAQS Directos: " + exception.getMessage());
            cerrarSesionAdminSiEsPosible(pc4);
            return false;
        }
    }

    /**
     * Cierra la sesión actual y elimina con ADMIN una FAQ de Directos creada como dato de prueba.
     *
     * @param url URL de inicio de sesión de Access Control.
     * @param pregunta pregunta que debe eliminarse.
     * @return true cuando la FAQ no existe o fue eliminada correctamente.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-15
     */
    public boolean limpiarFaqDirectosComoAdmin(String url, String pregunta) {
        PortalCometaMVP4 pc4 = null;
        try {
            pc4 = new PortalCometaMVP4(u, testCase);
            cerrarSesionActualSiEsPosible(pc4);
            String passwordAdmin = pc4.obtenerPasswordPorUsuario(USUARIO_ADMIN);
            boolean resultadoPaso = pc4.IngresarURL(url, true)
                    && pc4.IniciarSesionNoToken(USUARIO_ADMIN, passwordAdmin, true)
                    && pc4.seleccionarPortalCometaQA(true)
                    && abrirYValidarFaqsDirectos()
                    && eliminarFaqSiExiste(pregunta)
                    && cerrarSesionAdminObligatoria(pc4);
            registrarResultado(resultadoPaso, "Limpieza completada: FAQS Directos no conserva la FAQ de prueba",
                    "No fue posible limpiar la FAQ de prueba en FAQS Directos");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible limpiar la FAQ de Directos: " + exception.getMessage());
            cerrarSesionAdminSiEsPosible(pc4);
            return false;
        }
    }

    private boolean cerrarSesionAdminObligatoria(PortalCometaMVP4 pc4) {
        try {
            desplazarInicioPagina();
            boolean resultadoPaso = pc4 != null && pc4.ClicCerrarSesionPortalAccess(false);
            registrarResultado(resultadoPaso, "Sesión ADMIN cerrada correctamente",
                    "No fue posible cerrar la sesión ADMIN después de crear o limpiar la FAQ");
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible cerrar la sesión ADMIN: " + exception.getMessage());
            return false;
        }
    }

    private void cerrarSesionAdminSiEsPosible(PortalCometaMVP4 pc4) {
        try {
            if (pc4 != null) {
                desplazarInicioPagina();
                pc4.ClicCerrarSesionPortalAccess(false);
            }
        } catch (Exception exception) {
            System.out.println("[WARN] No fue posible cerrar sesión ADMIN antes de cerrar el navegador: " + exception.getMessage());
        }
    }

    private void cerrarSesionActualSiEsPosible(PortalCometaMVP4 pc4) {
        try {
            if (pc4 != null) {
                desplazarInicioPagina();
                pc4.ClicCerrarSesionPortalAccess(false);
                System.out.println("[INFO] Sesión actual cerrada antes de iniciar limpieza con ADMIN");
            }
        } catch (Exception exception) {
            System.out.println("[WARN] No fue posible cerrar la sesión actual antes de iniciar ADMIN para limpieza: " + exception.getMessage());
        }
    }

    private void desplazarInicioPagina() {
        try {
            ((JavascriptExecutor) u.getDriver()).executeScript("window.scrollTo(0, 0);");
            u.hardWait(1);
        } catch (Exception exception) {
            System.out.println("[WARN] No fue posible desplazar al inicio antes de cerrar sesión: " + exception.getMessage());
        }
    }

    /**
     * Valida que la URL actual corresponda a la pantalla FAQS Directos.
     *
     * @return true cuando la ruta actual contiene /PortalCometa/faqs/directos/home.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-17
     */
    public boolean validarRutaFaqsDirectos() {
        try {
            String urlActual = u.getDriver().getCurrentUrl();
            boolean resultadoPaso = urlActual != null
                    && urlActual.toLowerCase().contains("/portalcometa/faqs/directos/home");
            registrarResultado(resultadoPaso,
                    "La URL refleja la ruta de la pantalla FAQS Directos sin errores de redireccion",
                    "La URL no corresponde a FAQS Directos. Actual: " + urlActual);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible validar la URL de FAQS Directos: " + exception.getMessage());
            intentarCapturaError("Error_validacion_ruta_FAQS_Directos");
            return false;
        }
    }

    /**
     * Comprueba que FAQS Directos termine de cargar sin errores HTTP visibles ni pantalla vacia.
     *
     * @return true cuando el cuerpo contiene informacion y no muestra errores 403, 404 o 500.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-17
     */
    public boolean validarCargaFaqsDirectosSinErrores() {
        try {
            new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
                            "//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
            WebElement cuerpo = esperarElementoVisible(By.tagName("body"));
            String textoPantalla = cuerpo.getText() == null ? "" : cuerpo.getText().trim();
            String textoMinusculas = textoPantalla.toLowerCase();
            boolean errorTecnico = textoMinusculas.contains("404")
                    || textoMinusculas.contains("500")
                    || textoMinusculas.contains("403")
                    || textoMinusculas.contains("not found")
                    || textoMinusculas.contains("internal server error")
                    || textoMinusculas.contains("forbidden");
            boolean resultadoPaso = cuerpo.isDisplayed() && !textoPantalla.isEmpty() && !errorTecnico;
            registrarResultado(resultadoPaso,
                    "La pantalla FAQS carga correctamente sin errores tecnicos ni areas completamente en blanco",
                    "FAQS Directos esta vacia o muestra un error HTTP. Texto visible: " + textoPantalla);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible validar la carga limpia de FAQS Directos: " + exception.getMessage());
            intentarCapturaError("Error_carga_FAQS_Directos");
            return false;
        }
    }

    /**
     * Valida que el encabezado de FAQS Directos muestre el titulo Preguntas frecuentes.
     *
     * @return true cuando el titulo esperado esta visible y es legible.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-17
     */
    public boolean validarTituloPreguntasFrecuentes() {
        try {
            By tituloPreguntasFrecuentes = By.xpath(
                    "//*[self::h1 or self::h2 or self::h3 or contains(@class,'title') or contains(@class,'titulo')]"
                    + "[translate(normalize-space(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='preguntas frecuentes']");
            WebElement titulo = esperarElementoVisible(tituloPreguntasFrecuentes);
            u.scrollToElement(tituloPreguntasFrecuentes);
            boolean resultadoPaso = "Preguntas frecuentes".equalsIgnoreCase(titulo.getText().trim());
            registrarResultado(resultadoPaso,
                    "El encabezado Preguntas frecuentes es visible y legible en la parte superior del contenido",
                    "No se encontro el titulo Preguntas frecuentes. Actual: " + titulo.getText());
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible validar el titulo Preguntas frecuentes: " + exception.getMessage());
            intentarCapturaError("Error_titulo_Preguntas_frecuentes");
            return false;
        }
    }

    /**
     * Valida que el listado de FAQS Directos contenga al menos una tarjeta con pregunta y respuesta.
     *
     * @return true cuando una tarjeta visible contiene al menos dos lineas de texto legible.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-17
     */
    public boolean validarListadoConPreguntaYRespuesta() {
        try {
            WebElement primeraTarjeta = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30))
                    .until(ExpectedConditions.visibilityOfElementLocated(elements.tarjetasFaq));
            u.scrollToElement(elements.tarjetasFaq);
            String textoTarjeta = primeraTarjeta.getText() == null ? "" : primeraTarjeta.getText().trim();
            int lineasConTexto = 0;
            for (String linea : textoTarjeta.split("\\R")) {
                if (!linea.trim().isEmpty()) {
                    lineasConTexto++;
                }
            }
            boolean resultadoPaso = primeraTarjeta.isDisplayed() && lineasConTexto >= 2;
            registrarResultado(resultadoPaso,
                    "El listado muestra al menos una FAQ con texto de pregunta y respuesta, sin error de autorizacion",
                    "La primera tarjeta no contiene pregunta y respuesta legibles. Contenido actual: " + textoTarjeta);
            return resultadoPaso;
        } catch (Exception exception) {
            registrarError("No fue posible validar una pregunta y respuesta en FAQS Directos: " + exception.getMessage());
            intentarCapturaError("Error_listado_FAQS_Directos");
            return false;
        }
    }

    /**
     * Intenta adjuntar una captura del estado actual cuando una validacion termina por excepcion.
     *
     * @param nombreCaptura nombre seguro para la evidencia de error.
     *
     * @author Mauricio Gurrola
     * @date 2026-07-17
     */
    private void intentarCapturaError(String nombreCaptura) {
        try {
            testCase.addScreenShotCurrentStep(nombreCaptura);
        } catch (Exception screenshotException) {
            System.out.println("[WARN] No fue posible adjuntar la captura de error: "
                    + screenshotException.getMessage());
        }
    }
}