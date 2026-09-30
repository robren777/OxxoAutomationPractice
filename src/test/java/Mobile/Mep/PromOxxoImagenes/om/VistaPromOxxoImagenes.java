package Mobile.Mep.PromOxxoImagenes.om;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

/**
 * DESCRIPCIÓN: Page Object Model que representa las vistas del módulo PromOXXO de Imágenes.
 * Contiene los localizadores y métodos de interacción/validación para las vistas:
 * PromOXXO de Imágenes, Seleccionar Período, Zona de Tienda, Espacio Promocional,
 * Materiales Promocionales, Vista Guía, Modal Información, Visor de Imágenes y Planogramas.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *   - FT-001 - PromOXXO de Imágenes módulo existe
 *   - FT-005 a FT-043 - Casos de prueba del módulo PromOXXO de Imágenes
 *
 * PREREQUISITOS:
 *   - El usuario debe estar registrado en el sistema.
 *   - Contar con los códigos Cr de la tienda (CrPlace y CrStore).
 *   - El usuario ha iniciado sesión correctamente.
 *   - El usuario está en la pantalla "Panel de Control".
 *
 * INSUMOS:
 *   - Número de empleado
 *   - crTienda
 *   - crPlaza
 *   - PIN
 *
 * @author Mario A. De León Rdz.
 * @date 26/Febrero/2026
 */
public class VistaPromOxxoImagenes {
    /*
     * Variables Globales
     ******************************************************************************************/
    private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;

    /*
     * Localizadores - Vista PromOXXO de Imágenes
     ******************************************************************************************/
    private By tituloPromOxxoImagenes = AppiumBy.androidUIAutomator("new UiSelector().text(\"PromOXXO de Imágenes\")");
    private By btnAtras = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"icon-button\")");
    private By subtituloProcesoPromocional = AppiumBy.androidUIAutomator("new UiSelector().text(\"Proceso Promocional:\")");
    private By procesoPromocionalActivo = AppiumBy.androidUIAutomator("new UiSelector().text(\"Cerveza\")"); 
    private By procesoInactivoCortasVigencias = AppiumBy.accessibilityId("Cortas Vigencias");
    private By procesoPromocionalNacional = AppiumBy.androidUIAutomator("new UiSelector().text(\"PromOXXO Nacional\")"); 

    /*
     * Localizadores - Vista Seleccionar Período
     ******************************************************************************************/
    private By tituloSeleccionarPeriodo = AppiumBy.androidUIAutomator("new UiSelector().text(\"Selecciona el periodo:\")");
    private By btnPeriodoActual = AppiumBy.androidUIAutomator("new UiSelector().text(\"Periodo actual\")");
    private By btnPeriodoFuturo = AppiumBy.androidUIAutomator("new UiSelector().text(\"Próximo periodo\")");
    private By btnAceptar = AppiumBy.accessibilityId("Aceptar");
    private By tituloPopupLasPromocionesComienzan = AppiumBy.androidUIAutomator("new UiSelector().text(\"Las promociones comienzan\")");

    /*
     * Localizadores - Vista Zona de Tienda
     ******************************************************************************************/
    private By tituloZonaDeTienda = AppiumBy.androidUIAutomator("new UiSelector().text(\"Zona de Tienda:\")");
    private By iconoBuscarZonaDeTienda = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"icon-button\").instance(1)");
    private By campoBuscarZonaDeTienda = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");
    private By primerGrupoEspaciosPromocionales = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").instance(1)"); 
    private By mensajeNoResultadosZonaDeTienda = AppiumBy.androidUIAutomator("new UiSelector().text(\"No se encontraron resultados\")");
    private By opcionCabeceras = AppiumBy.androidUIAutomator("new UiSelector().text(\"Cabeceras\")");
    private By opcionCabeceras1 = AppiumBy.androidUIAutomator("new UiSelector().text(\"Cabecera 1\")");
    private By opcionBanners = AppiumBy.androidUIAutomator("new UiSelector().text(\"Banners\")");
    private By opcionBannerExterior = AppiumBy.androidUIAutomator("new UiSelector().text(\"Banner Exterior\")");
    private By iconoAyudaBannerExterior = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"icon-button\").instance(2)");
    /*
     * Localizadores - Vista Espacio Promocional
     ******************************************************************************************/
    private By tituloEspacioPromocional = AppiumBy.androidUIAutomator("new UiSelector().text(\"Espacio Promocional:\")");
    private By iconoBuscarEspacioPromocional = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"icon-button\").instance(1)");
    private By campoBuscarEspacioPromocional = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");
    private By primerEspacioPromocional = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.TextView\").instance(1)"); 
    private By opcionVerTodos = AppiumBy.androidUIAutomator("new UiSelector().text(\"Ver todos\")");
    private By mensajeNoResultadosEspacioPromocional = AppiumBy.androidUIAutomator("new UiSelector().text(\"No se encontraron resultados\")");

    /*
     * Localizadores - Vista Materiales Promocionales
     ******************************************************************************************/
    private By tituloMaterialesPromocionales = AppiumBy.androidUIAutomator("new UiSelector().text(\"Materiales promocionales\")"); 
    private By iconoBuscarMateriales = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"icon-button\").instance(1)");
    private By campoBuscarMateriales = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");
    private By textoNResultados = AppiumBy.androidUIAutomator("new UiSelector().textContains(\" resultados\")");
    private By mensajeNoResultadosMateriales = AppiumBy.androidUIAutomator("new UiSelector().text(\"No se encontraron resultados\")");
    private By iconoInfo = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"icon-button\").instance(3)"); 
    private By modalInfo = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"modal-container\")"); 
    private By btnCerrarModalInfo = AppiumBy.accessibilityId("Cerrar");
    private By imagenMaterialPromocional = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(0)"); 
    private By pestanaMateriales = AppiumBy.androidUIAutomator("new UiSelector().text(\"Materiales\")");
    private By pestanaPlanogramas = AppiumBy.androidUIAutomator("new UiSelector().text(\"Planogramas\")");
    private By imagenEstantes = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(0)"); 
    private By btnCerrarVisorImagenes = AppiumBy.androidUIAutomator("new UiSelector().description(\"X\")"); 
    private By tituloVisorImagenes = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"image-viewer-title\")"); 
    private By namePdfManualesPromoxxo = AppiumBy.androidUIAutomator("new UiSelector().text(\"Manuales PROMOXXO.pdf\")");
    private By isPdfVisibleCorrectly = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.RelativeLayout\")");
    private By nameBannerExteriorJpg = AppiumBy.androidUIAutomator("new UiSelector().text(\"Banner Exterior.jpg\")");
    private By isBannerExteriorJpgVisibleCorrectly = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\")");
    private By nameManualesPromoxxoVideo = AppiumBy.androidUIAutomator("new UiSelector().text(\"Manuales PROMOXXO VIDEO.mp4\")");
    private By isManualesPromoxxoVideoVisibleCorrectly = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\")");

    
    /*
     * Localizadores - Vista Guía
     ******************************************************************************************/
    private By iconoGuia = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"icon-button\").instance(2)"); 
    private By tituloGuia = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"guide-title\")"); 

    /**
     * Constructor de la clase VistaPromOxxoImagenes
     *
     * @author Mario A. De León Rdz.
     * @param testCase (TestCase): objeto de la clase
     * @param au (AppiumUtil): objeto de la clase
     * @param baseExecution (BaseExecution): objeto de la clase
     * @date 26/Febrero/2026
     */
    public VistaPromOxxoImagenes(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }

    // ========================================================================================
    // MÉTODOS - Vista PromOXXO de Imágenes
    // ========================================================================================

    /**
     * Metodo para validar que se muestra la vista "PromOXXO de Imágenes" en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaPromOxxoImagenes() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPromOxxoImagenes));

            testCase.addScreenShotCurrentStep(au, "Validacion vista PromOXXO de Imágenes");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'PromOXXO de Imágenes'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "PromOXXO de Imágenes" sin evidencia en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaPromOxxoImagenesSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPromOxxoImagenes));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al validar la vista 'PromOXXO de Imágenes'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "PromOXXO de Imágenes" del periodo actual en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaPeriodoActual() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPromOxxoImagenes));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subtituloProcesoPromocional));

            testCase.addScreenShotCurrentStep(au, "Validacion vista PromOXXO de Imágenes - Periodo actual");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'PromOXXO de Imágenes' del periodo actual", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "PromOXXO de Imágenes" del periodo futuro en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaPeriodoFuturo() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPromOxxoImagenes));
            wait.until(ExpectedConditions.visibilityOfElementLocated(subtituloProcesoPromocional));

            testCase.addScreenShotCurrentStep(au, "Validacion vista PromOXXO de Imágenes - Periodo futuro");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'PromOXXO de Imágenes' del periodo futuro", e);
        }
    }

    /**
     * Metodo para hacer click en el botón de flecha "Atrás" en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickBtnAtras() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnAtras)).click();

            testCase.addScreenShotCurrentStep(au, "Click en botón Atrás - PromOXXO de Imágenes");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón 'Atrás' en la vista 'PromOXXO de Imágenes'", e);
        }
    }

    /**
     * Metodo para hacer click en un proceso promocional activo en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickProcesoPromocionalActivo() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(procesoPromocionalActivo)).click();

            testCase.addScreenShotCurrentStep(au, "Click en proceso promocional activo");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el proceso promocional activo", e);
        }
    }

    /**
     * Metodo para hacer click en un proceso promocional activo sin evidencia en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickProcesoPromocionalActivoSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(procesoPromocionalActivo)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en el proceso promocional activo", e);
        }
    }
    
    /**
     * Metodo para hacer click en el proceso 'PromOXXO Nacional' promocional activo en la vista "PromOXXO de Imágenes"
     *
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/Abril/2026
     */
    public void clickProcesoPromocionalPromOXXONacional() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(50));
            wait.until(ExpectedConditions.elementToBeClickable(procesoPromocionalNacional)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en el proceso 'PromOXXO Nacional' promocional activo", e);
        }
    }

    /**
     * Metodo para hacer click en un proceso promocional inactivo en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickProcesoInactivo() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(procesoInactivoCortasVigencias)).click();

            testCase.addScreenShotCurrentStep(au, "Click en proceso promocional inactivo");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el proceso promocional inactivo", e);
        }
    }

    /**
     * Metodo para validar que los procesos promocionales inactivos se muestran en gris en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarProcesosInactivos() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(procesoInactivoCortasVigencias)).getAttribute(null);

            testCase.addScreenShotCurrentStep(au, "Validacion procesos promocionales inactivos en gris");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar los procesos promocionales inactivos", e);
        }
    }

    /**
     * Metodo para validar que el proceso promocional inactivo no es clickable en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarProcesoInactivoNoClickable() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));

            WebElement elemento = wait.until(
                ExpectedConditions.presenceOfElementLocated(procesoInactivoCortasVigencias)
            );

            boolean esClickable = elemento.isEnabled() && elemento.isDisplayed();
            System.out.println(">>> esClickable: " + esClickable);

            if (esClickable) {
                testCase.addScreenShotCurrentStep(au, "FALLO: El proceso inactivo ES clickable cuando no debería serlo");
                throw new AssertionError("El proceso inactivo NO debería ser clickable, pero lo es.");
            }

            System.out.println(">>> Llegó al screenshot exitoso");
            testCase.addScreenShotCurrentStep(au, "");

        } catch (AssertionError ae) {
            testCase.addCodeEvidenceCurrentStep("Fallo de validación: " + ae.getMessage());
            throw new Exception("Validación fallida: el proceso inactivo es clickable cuando no debería serlo", ae);

        } catch (Exception e) {
            testCase.addScreenShotCurrentStep(au, "ERROR inesperado al validar el proceso inactivo");
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            e.printStackTrace();
            throw new Exception("Error al validar que el proceso promocional inactivo no es clickable", e);
        }
    }

    // ========================================================================================
    // MÉTODOS - Vista Seleccionar Período
    // ========================================================================================

    /**
     * Metodo para validar que se muestra la vista "Seleccionar período de promoción" en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaSeleccionarPeriodo() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloSeleccionarPeriodo));

            testCase.addScreenShotCurrentStep(au, "Validacion vista Seleccionar período de promoción");
            testCase.addTextEvidenceCurrentStep("Se muestra la pantalla: \"Seleccionar período de promoción\", que tiene: "
                    + "- Botón de flecha \"Atrás\" "
                    + "- Título \"Seleccionar período de promoción\" "
                    + "- Subtítulo \"Selecciona el período:\" "
                    + "- Opciones \"Período actual\" "
                    + "y \"Período futuro\"");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'Seleccionar período de promoción'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "Seleccionar período de promoción" sin evidencia en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaSeleccionarPeriodoSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloSeleccionarPeriodo));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al validar la vista 'Seleccionar período de promoción'", e);
        }
    }

    /**
     * Metodo para hacer click en el botón "Período actual" en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickPeriodoActual() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnPeriodoActual)).click();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón 'Período actual'", e);
        }
    }

    /**
     * Metodo para hacer click en el botón "Período actual" sin evidencia en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickPeriodoActualSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnPeriodoActual)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en el botón 'Período actual'", e);
        }
    }

    /**
     * Metodo para hacer click en el botón "Período futuro" en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickPeriodoFuturo() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnPeriodoFuturo)).click();

            testCase.addScreenShotCurrentStep(au, "Click en Período futuro");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón 'Período futuro'", e);
        }
    }

    /**
     * Metodo para hacer click en el botón "Aceptar" en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickBtnAceptar() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnAceptar)).click();

            testCase.addScreenShotCurrentStep(au, "Click en botón Aceptar");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón 'Aceptar'", e);
        }
    }

    /**
     * Metodo para hacer click en el botón "Aceptar" sin evidencia en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickBtnAceptarSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnAceptar)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en el botón 'Aceptar'", e);
        }
    }

    /**
     * Metodo para validar que se muestra el popup "Las promociones comienzan" en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarPopupLasPromocionesComienzan() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloPopupLasPromocionesComienzan));

            testCase.addScreenShotCurrentStep(au, "Validacion popup Las promociones comienzan");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el popup 'Las promociones comienzan'", e);
        }
    }

    // ========================================================================================
    // MÉTODOS - Vista Zona de Tienda
    // ========================================================================================

    /**
     * Metodo para validar que se muestra la vista "Zona de Tienda" en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaZonaDeTienda() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloZonaDeTienda));

            testCase.addScreenShotCurrentStep(au, "Validacion vista Zona de Tienda");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'Zona de Tienda'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "Zona de Tienda" sin evidencia en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaZonaDeTiendaSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloZonaDeTienda));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al validar la vista 'Zona de Tienda'", e);
        }
    }

    /**
     * Metodo para hacer click en el botón de flecha "Atrás" en la vista "Zona de Tienda"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickBtnAtrasZonaDeTienda() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnAtras)).click();

            testCase.addScreenShotCurrentStep(au, "Click en botón Atrás - Zona de Tienda");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón 'Atrás' en la vista 'Zona de Tienda'", e);
        }
    }

    /**
     * Metodo para hacer click en el icono "Buscar" en la vista "Zona de Tienda"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickIconoBuscarZonaDeTienda() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(iconoBuscarZonaDeTienda)).click();

            testCase.addScreenShotCurrentStep(au, "Click en icono Buscar - Zona de Tienda");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el icono 'Buscar' en la vista 'Zona de Tienda'", e);
        }
    }

    /**
     * Metodo para validar que se muestra el campo de búsqueda en la vista "Zona de Tienda"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarCampoBuscarZonaDeTienda() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(campoBuscarZonaDeTienda));

            testCase.addScreenShotCurrentStep(au, "Validacion campo Buscar - Zona de Tienda");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el campo 'Buscar' en la vista 'Zona de Tienda'", e);
        }
    }

    /**
     * Metodo para ingresar texto en el campo de búsqueda en la vista "Zona de Tienda"
     *
     * @author Mario A. De León Rdz.
     * @param texto Texto a ingresar en el campo de búsqueda
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void ingresarTextoBusquedaZonaDeTienda(String texto) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(campoBuscarZonaDeTienda)).sendKeys(texto);

            testCase.addScreenShotCurrentStep(au, "Ingreso de texto en búsqueda Zona de Tienda: " + texto);
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al ingresar texto en el campo 'Buscar' en la vista 'Zona de Tienda'", e);
        }
    }

    /**
     * Metodo para validar que se muestran resultados de búsqueda en la vista "Zona de Tienda"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarResultadosBusquedaZonaDeTienda() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(primerGrupoEspaciosPromocionales));

            testCase.addScreenShotCurrentStep(au, "Validacion resultados de búsqueda - Zona de Tienda");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar los resultados de búsqueda en la vista 'Zona de Tienda'", e);
        }
    }

    /**
     * Metodo para validar el mensaje "No se encontraron resultados" en la vista "Zona de Tienda"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarMensajeNoResultadosZonaDeTienda() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(mensajeNoResultadosZonaDeTienda));

            testCase.addScreenShotCurrentStep(au, "Validacion mensaje No se encontraron resultados - Zona de Tienda");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el mensaje 'No se encontraron resultados' en la vista 'Zona de Tienda'", e);
        }
    }

    /**
     * Metodo para hacer click en un grupo de espacios promocionales en la vista "Zona de Tienda"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickGrupoEspaciosPromocionales() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(primerGrupoEspaciosPromocionales)).click();

            testCase.addScreenShotCurrentStep(au, "Click en grupo de espacios promocionales");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el grupo de espacios promocionales", e);
        }
    }

    /**
     * Metodo para hacer click en un grupo de espacios promocionales sin evidencia en la vista "Zona de Tienda"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickGrupoEspaciosPromocionalesSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(primerGrupoEspaciosPromocionales)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en el grupo de espacios promocionales", e);
        }
    }

    /**
     * Metodo para validar la lista de grupos de espacios promocionales contra la base de datos en la vista "Zona de Tienda"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarListaGruposEspaciosPromocionalesConBD() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(primerGrupoEspaciosPromocionales));

            testCase.addScreenShotCurrentStep(au, "Validacion lista grupos espacios promocionales vs BD");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la lista de grupos de espacios promocionales contra la base de datos", e);
        }
    }

    // ========================================================================================
    // MÉTODOS - Vista Espacio Promocional
    // ========================================================================================

    /**
     * Metodo para validar que se muestra la vista "Espacio Promocional" en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaEspacioPromocional() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloEspacioPromocional));

            testCase.addScreenShotCurrentStep(au, "Validacion vista Espacio Promocional");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'Espacio Promocional'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "Espacio Promocional" sin evidencia en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaEspacioPromocionalSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloEspacioPromocional));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al validar la vista 'Espacio Promocional'", e);
        }
    }

    /**
     * Metodo para hacer click en el botón de flecha "Atrás" en la vista "Espacio Promocional"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickBtnAtrasEspacioPromocional() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnAtras)).click();

            testCase.addScreenShotCurrentStep(au, "Click en botón Atrás - Espacio Promocional");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón 'Atrás' en la vista 'Espacio Promocional'", e);
        }
    }

    /**
     * Metodo para hacer click en el icono "Buscar" en la vista "Espacio Promocional"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickIconoBuscarEspacioPromocional() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(iconoBuscarEspacioPromocional)).click();

            testCase.addScreenShotCurrentStep(au, "Click en icono Buscar - Espacio Promocional");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el icono 'Buscar' en la vista 'Espacio Promocional'", e);
        }
    }

    /**
     * Metodo para validar que se muestra el campo de búsqueda en la vista "Espacio Promocional"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarCampoBuscarEspacioPromocional() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(campoBuscarEspacioPromocional));

            testCase.addScreenShotCurrentStep(au, "Validacion campo Buscar - Espacio Promocional");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el campo 'Buscar' en la vista 'Espacio Promocional'", e);
        }
    }

    /**
     * Metodo para ingresar texto en el campo de búsqueda en la vista "Espacio Promocional"
     *
     * @author Mario A. De León Rdz.
     * @param texto Texto a ingresar en el campo de búsqueda
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void ingresarTextoBusquedaEspacioPromocional(String texto) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(campoBuscarEspacioPromocional)).sendKeys(texto);

            testCase.addScreenShotCurrentStep(au, "Ingreso de texto en búsqueda Espacio Promocional: " + texto);
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al ingresar texto en el campo 'Buscar' en la vista 'Espacio Promocional'", e);
        }
    }

    /**
     * Metodo para validar que se muestran resultados de búsqueda en la vista "Espacio Promocional"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarResultadosBusquedaEspacioPromocional() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(primerEspacioPromocional));

            testCase.addScreenShotCurrentStep(au, "Validacion resultados de búsqueda - Espacio Promocional");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar los resultados de búsqueda en la vista 'Espacio Promocional'", e);
        }
    }

    /**
     * Metodo para validar el mensaje "No se encontraron resultados" en la vista "Espacio Promocional"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarMensajeNoResultadosEspacioPromocional() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(mensajeNoResultadosEspacioPromocional));

            testCase.addScreenShotCurrentStep(au, "Validacion mensaje No se encontraron resultados - Espacio Promocional");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el mensaje 'No se encontraron resultados' en la vista 'Espacio Promocional'", e);
        }
    }

    /**
     * Metodo para validar el orden de los espacios promocionales en la vista "Espacio Promocional"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarOrdenEspaciosPromocionales() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(opcionVerTodos));

            testCase.addScreenShotCurrentStep(au, "Validacion orden espacios promocionales");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el orden de los espacios promocionales", e);
        }
    }

    /**
     * Metodo para hacer click en un espacio promocional en la vista "Espacio Promocional"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickEspacioPromocional() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(primerEspacioPromocional)).click();

            testCase.addScreenShotCurrentStep(au, "Click en espacio promocional");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el espacio promocional", e);
        }
    }

    /**
     * Metodo para hacer click en un espacio promocional sin evidencia en la vista "Espacio Promocional"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickEspacioPromocionalSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(primerEspacioPromocional)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en el espacio promocional", e);
        }
    }

    /**
     * Metodo para hacer click en la opción "Ver todos" en la vista "Espacio Promocional"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickVerTodos() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(opcionVerTodos)).click();

            testCase.addScreenShotCurrentStep(au, "Click en Ver todos");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en la opción 'Ver todos'", e);
        }
    }

    // ========================================================================================
    // MÉTODOS - Vista Materiales Promocionales
    // ========================================================================================

    /**
     * Metodo para validar que se muestra la vista "Materiales promocionales" en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaMaterialesPromocionales() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloMaterialesPromocionales));

            testCase.addScreenShotCurrentStep(au, "Validacion vista Materiales promocionales");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'Materiales promocionales'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "Materiales promocionales" sin evidencia en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaMaterialesPromocionalesSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloMaterialesPromocionales));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al validar la vista 'Materiales promocionales'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "Materiales promocionales" con opción "Ver todos" en la vista "PromOXXO de Imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaMaterialesPromocionalesVerTodos() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloMaterialesPromocionales));

            testCase.addScreenShotCurrentStep(au, "Validacion vista Materiales promocionales - Ver todos");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'Materiales promocionales' con Ver todos", e);
        }
    }

    /**
     * Metodo para hacer click en el botón de flecha "Atrás" en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickBtnAtrasMaterialesPromocionales() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnAtras)).click();

            testCase.addScreenShotCurrentStep(au, "Click en botón Atrás - Materiales promocionales");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón 'Atrás' en la vista 'Materiales promocionales'", e);
        }
    }

    /**
     * Metodo para hacer click en el icono "Buscar" en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickIconoBuscarMateriales() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(iconoBuscarMateriales)).click();

            testCase.addScreenShotCurrentStep(au, "Click en icono Buscar - Materiales promocionales");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el icono 'Buscar' en la vista 'Materiales promocionales'", e);
        }
    }

    /**
     * Metodo para validar que se muestra el campo de búsqueda en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarCampoBuscarMateriales() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(campoBuscarMateriales));

            testCase.addScreenShotCurrentStep(au, "Validacion campo Buscar - Materiales promocionales");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el campo 'Buscar' en la vista 'Materiales promocionales'", e);
        }
    }

    /**
     * Metodo para ingresar texto en el campo de búsqueda en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @param texto Texto a ingresar en el campo de búsqueda
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void ingresarTextoBusquedaMateriales(String texto) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(campoBuscarMateriales)).sendKeys(texto);

            testCase.addScreenShotCurrentStep(au, "Ingreso de texto en búsqueda Materiales promocionales: " + texto);
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al ingresar texto en el campo 'Buscar' en la vista 'Materiales promocionales'", e);
        }
    }

    /**
     * Metodo para validar que se muestran resultados de búsqueda en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarResultadosBusquedaMateriales() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(textoNResultados));

            testCase.addScreenShotCurrentStep(au, "Validacion resultados de búsqueda - Materiales promocionales");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar los resultados de búsqueda en la vista 'Materiales promocionales'", e);
        }
    }

    /**
     * Metodo para validar que se muestra el texto "N resultados" en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarTextoNResultados() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(textoNResultados));

            testCase.addScreenShotCurrentStep(au, "Validacion texto N resultados - Materiales promocionales");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el texto 'N resultados' en la vista 'Materiales promocionales'", e);
        }
    }

    /**
     * Metodo para validar el mensaje "No se encontraron resultados" en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarMensajeNoResultadosMateriales() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(mensajeNoResultadosMateriales));

            testCase.addScreenShotCurrentStep(au, "Validacion mensaje No se encontraron resultados - Materiales promocionales");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el mensaje 'No se encontraron resultados' en la vista 'Materiales promocionales'", e);
        }
    }
    
    // ========================================================================================
    // MÉTODOS - Vista Guía
    // ========================================================================================

    /**
     * Metodo para hacer click en el icono "?" en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickIconoGuia() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(iconoGuia)).click();

            testCase.addScreenShotCurrentStep(au, "Click en icono Guía");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el icono '?' de la Guía", e);
        }
    }

    /**
     * Metodo para hacer click en el icono "?" sin evidencia en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickIconoGuiaSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(iconoGuia)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en el icono '?' de la Guía", e);
        }
    }

    /**
     * Metodo para hacer click en el botón "Atrás" en la vista "Guía"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickBtnAtrasGuia() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnAtras)).click();

            testCase.addScreenShotCurrentStep(au, "Click en botón Atrás - Guía");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón 'Atrás' en la vista 'Guía'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "Guía" en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaGuia() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloGuia));

            testCase.addScreenShotCurrentStep(au, "Validacion vista Guía");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'Guía'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "Guía" sin evidencia en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaGuiaSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloGuia));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al validar la vista 'Guía'", e);
        }
    }

    // ========================================================================================
    // MÉTODOS - Modal Información del Material Promocional
    // ========================================================================================

    /**
     * Metodo para hacer click en el icono "i" en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickIconoInfo() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(iconoInfo)).click();

            testCase.addScreenShotCurrentStep(au, "Click en icono Info");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el icono 'i' de información", e);
        }
    }

    /**
     * Metodo para validar que se muestra el modal "Información del material promocional"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarModalInformacionMaterialPromocional() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalInfo));

            testCase.addScreenShotCurrentStep(au, "Validacion modal Información del material promocional");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el modal 'Información del material promocional'", e);
        }
    }

    /**
     * Metodo para hacer click en el botón "Cerrar" del modal de información
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickBtnCerrarModalInfo() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnCerrarModalInfo)).click();

            testCase.addScreenShotCurrentStep(au, "Click en botón Cerrar - Modal Info");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón 'Cerrar' del modal de información", e);
        }
    }

    /**
     * Metodo para validar que el modal "Información del material promocional" está cerrado
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarModalInformacionCerrado() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalInfo));

            testCase.addScreenShotCurrentStep(au, "Validacion modal Información cerrado");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar que el modal 'Información del material promocional' está cerrado", e);
        }
    }

    // ========================================================================================
    // MÉTODOS - Visor de Imágenes
    // ========================================================================================

    /**
     * Metodo para validar que se muestre la imagen del material promocional en la seccion 'Cabecera 1'
     *
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void isImagenMaterialPromocionalDisplayed() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(imagenMaterialPromocional));
            ele.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al verificar que se muestre la imagen", e);
        }
    }
    
    
    /**
     * Metodo para hacer click en la imagen del material promocional en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickImagenMaterialPromocional() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(imagenMaterialPromocional)).click();

            testCase.addScreenShotCurrentStep(au, "Click en imagen material promocional");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en la imagen del material promocional", e);
        }
    }

    /**
     * Metodo para hacer click en la imagen del material promocional sin evidencia en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickImagenMaterialPromocionalSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(imagenMaterialPromocional)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en la imagen del material promocional", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "Visor de imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaVisorDeImagenes() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloVisorImagenes));

            testCase.addScreenShotCurrentStep(au, "Validacion vista Visor de imágenes");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'Visor de imágenes'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "Visor de imágenes" sin evidencia
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaVisorDeImagenesSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(tituloVisorImagenes));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al validar la vista 'Visor de imágenes'", e);
        }
    }

    /**
     * Metodo para hacer click en el botón "X" en la vista "Visor de imágenes"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickBtnCerrarVisorImagenes() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnCerrarVisorImagenes)).click();

            testCase.addScreenShotCurrentStep(au, "Click en botón X - Visor de imágenes");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón 'X' en la vista 'Visor de imágenes'", e);
        }
    }

    // ========================================================================================
    // MÉTODOS - Vista Planogramas
    // ========================================================================================

    /**
     * Metodo para hacer click en la pestaña "Planogramas" en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickPestanaPlanogramas() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(pestanaPlanogramas)).click();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en la pestaña 'Planogramas'", e);
        }
    }

    /**
     * Metodo para hacer click en la pestaña "Planogramas" sin evidencia en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickPestanaPlanogramasSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(pestanaPlanogramas)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en la pestaña 'Planogramas'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "Planogramas" en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaPlanogramas() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(pestanaPlanogramas));
            wait.until(ExpectedConditions.visibilityOfElementLocated(imagenEstantes));

            testCase.addScreenShotCurrentStep(au, "Validacion vista Planogramas");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la vista 'Planogramas'", e);
        }
    }

    /**
     * Metodo para validar que se muestra la vista "Planogramas" sin evidencia en la vista "Materiales promocionales"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarVistaPlanogramasSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(pestanaPlanogramas));
            wait.until(ExpectedConditions.visibilityOfElementLocated(imagenEstantes));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al validar la vista 'Planogramas'", e);
        }
    }

    /**
     * Metodo para hacer click en la imagen de estantes en la vista "Planogramas"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickImagenEstantes() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(imagenEstantes)).click();

            testCase.addScreenShotCurrentStep(au, "Click en imagen de estantes - Planogramas");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en la imagen de estantes en 'Planogramas'", e);
        }
    }

    /**
     * Metodo para hacer click en la imagen de estantes sin evidencia en la vista "Planogramas"
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void clickImagenEstantesSinEv() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(imagenEstantes)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en la imagen de estantes en 'Planogramas'", e);
        }
    }

    /**
     * Metodo para validar el orden correcto de los materiales promocionales en la vista "Materiales promocionales - Ver todos"
     * Recibe la lista de grupos en orden esperado, captura todos los textos visibles con scroll completo,
     * extrae los grupos encontrados y verifica que aparezcan en el mismo orden alfabético.
     *
     * @author Mario A. De León Rdz.
     * @param gruposEsperados Lista de grupos obtenida antes de entrar a Ver todos (índices 0,1,2 se ignoran)
     * @return void
     * @throws Exception
     * @date 09/Marzo/2026
     */
    public void validarOrdenMaterialesPromocionales(List<String> gruposEsperados) throws Exception {
        try {
            // ── 1. Capturar todos los textos con scroll completo ──────────────────
            List<String> todosLosTextos = new ArrayList<>();
            String ultimaPaginaTexto = "";

            while (true) {
                List<WebElement> elementos = au.driver.findElements(
                    By.xpath("//android.widget.TextView[@text and string-length(@text) > 0]")
                );

                StringBuilder paginaActual = new StringBuilder();
                for (WebElement el : elementos) {
                    String texto = el.getText().trim();
                    if (!texto.isEmpty()) {
                        paginaActual.append(texto).append("|");
                        if (!todosLosTextos.contains(texto)) {
                            todosLosTextos.add(texto);
                        }
                    }
                }

                if (paginaActual.toString().equals(ultimaPaginaTexto)) break;
                ultimaPaginaTexto = paginaActual.toString();

                try {
                    au.driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"
                    ));
                } catch (Exception ignored) {
                    break;
                }
            }

            System.out.println("Textos capturados en pantalla: " + todosLosTextos);

            // ── 2. Filtrar solo los grupos reales (ignorar índices 0, 1 y 2) ─────
            List<String> gruposReales = gruposEsperados.subList(3, gruposEsperados.size());
            System.out.println("Grupos esperados: " + gruposReales);

            // ── 3. Extraer grupos encontrados en pantalla en el orden que aparecen
            List<String> gruposEncontrados = new ArrayList<>();
            for (String texto : todosLosTextos) {
                if (gruposReales.contains(texto)) {
                    gruposEncontrados.add(texto);
                }
            }

            System.out.println("Grupos encontrados: " + gruposEncontrados);

            // ── 4. Validar que todos los grupos esperados estén presentes ─────────
            if (!gruposEncontrados.containsAll(gruposReales)) {
                List<String> faltantes = new ArrayList<>(gruposReales);
                faltantes.removeAll(gruposEncontrados);
                String msg = "FALLO: Grupos no encontrados en pantalla: " + faltantes;
                testCase.addCodeEvidenceCurrentStep(msg);
                throw new Exception(msg);
            }

            // ── 5. Validar orden alfabético de los grupos encontrados ─────────────
            List<String> gruposOrdenados = new ArrayList<>(gruposEncontrados);
            java.util.Collections.sort(gruposOrdenados, String.CASE_INSENSITIVE_ORDER);

            if (!gruposEncontrados.equals(gruposOrdenados)) {
                String msg = "FALLO orden de grupos.\nEsperado:  " + gruposOrdenados
                           + "\nObtenido:  " + gruposEncontrados;
                testCase.addCodeEvidenceCurrentStep(msg);
                throw new Exception(msg);
            }

            // ── 6. Evidencia de éxito ─────────────────────────────────────────────
            testCase.addTextEvidenceCurrentStep("Grupos en orden correcto: " + gruposEncontrados);
            testCase.addScreenShotCurrentStep(au, "Validacion orden materiales promocionales - CORRECTO");

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el orden de los materiales promocionales", e);
        }
    }

    /**
     * Metodo para validar que las pestañas "Materiales" y "Planogramas" no se muestran cuando no hay planograma
     *
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 26/Febrero/2026
     */
    public void validarQueNoseMuestranPestanasMaterialesPlanogramas() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(pestanaMateriales));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(pestanaPlanogramas));

            testCase.addScreenShotCurrentStep(au, "Validacion que no se muestran pestañas Materiales y Planogramas");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar que las pestañas 'Materiales' y 'Planogramas' no se muestran", e);
        }
    }

    /**
     * Obtiene y almacena en memoria los materiales promocionales visibles en la evaluación
     * Incluye scroll automático para capturar elementos fuera del viewport
     *
     * @author Mario A. De León Rdz.
     * @return List<String> Lista de materiales promocionales encontrados
     * @throws Exception
     * @date 22/Diciembre/2025
     */
    public List<String> guardarMaterialesPromocionales() throws Exception {
        List<String> materiales = new ArrayList<>();

        try {
            // Scroll de un solo paso para revelar el primer material
            au.driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"
            ));

            List<WebElement> elementos = au.driver.findElements(
                By.xpath("//android.widget.TextView[@text and string-length(@text) > 0]")
            );

            System.out.println("Materiales promocionales encontrados:");
            for (WebElement elemento : elementos) {
                String nombreMaterial = elemento.getText().trim();
                if (!nombreMaterial.isEmpty() && !materiales.contains(nombreMaterial)) {
                    materiales.add(nombreMaterial);
                    System.out.println(" - " + nombreMaterial);
                }
            }

            System.out.println("Total materiales promocionales encontrados: " + materiales.size());

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al obtener los materiales promocionales", e);
        }

        return materiales;
    }
    
    /**
     * Metodo para hacer click en la Zona de Tienda 'Cabeceras' en la vista "PromOXXO Nacional"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void clickZonaDeTiendaCabeceras() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(opcionCabeceras)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en la opcion de 'Cabeceras' en la zona de tienda", e);
        }
    }
    
    /**
     * Metodo para hacer click en el 'Espacio Promocional | Cabecera 1' en la vista "Cabeceras"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void clickEspacioPromocionalCabecera1() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(opcionCabeceras1)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en la opcion de 'Cabecera 1' en la vista 'Cabeceras'", e);
        }
    }
    
    /**
     * Metodo para hacer click en la Zona de Tienda 'Banners' en la vista "PromOXXO Nacional"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void clickZonaDeTiendaBanners() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(opcionBanners)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en la opcion de 'Banners' en la zona de tienda", e);
        }
    }
    
    /**
     * Metodo para hacer click en el 'Espacio Promocional | Banner Exterior' en la vista "Cabeceras"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void clickEspacioPromocionalBannerExterior() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(opcionBannerExterior)).click();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al dar clic en la opcion de 'Banner Exterior' en la vista 'Cabeceras'", e);
        }
    }
    
    /**
     * Metodo para hacer click en el boton de icono de 'Ayuda' | 'Duda' Dentro de Banner Exterior en la vista "Banner Exterior"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void clickBtnIconoAyudaBannerExterior() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(iconoAyudaBannerExterior)).click();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en la pestaña 'Ayuda' | 'Duda' Dentro de Banner Exterior", e);
        }
    }
    
    
    /**
     * Metodo para validar que se muestre el pdf Manuales PROMOXXO.pdf al dar click en el icono de ayuda dentro de Banner Exterior en la vista "Banner Exterior"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void isManualesPromoxxoPdfDisplayed() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(namePdfManualesPromoxxo));
            ele.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al verificar que se muestre el PDF Manuales PROMOXXO.pdf", e);
        }
    }
    
    /**
     * Metodo para hacer click en el pdf Manuales PROMOXXO.pdf dentro de la vista "Banner Exterior"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void clickManualesPromoxxoPdf() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(namePdfManualesPromoxxo)).click();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al hacer click en PDF Manuales PROMOXXO.pdf", e);
        }
    }
    
    /**
     * Metodo para verificar que se muestre correctamente el PDF Manuales PROMOXXO.pdf al hacer click en el icono de ayuda dentro de Banner Exterior en la vista "Banner Exterior"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void verificarPdfMostradoCorrectamente() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(isPdfVisibleCorrectly));
            ele.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al verificar que se muestre correctamente el PDF Manuales PROMOXXO.pdf", e);
        }
    }
    
    /**
     * Metodo para validar que se muestre el archivo Banner Exterior.jpg al dar click en el icono de ayuda dentro de Banner Exterior en la vista "Banner Exterior"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void isBannerExteriorJpgDisplayed() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(nameBannerExteriorJpg));
            ele.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al verificar que se muestre el archivo Banner Exterior.jpg", e);
        }
    }
    
    /**
     * Metodo para hacer click en el archivo Banner Exterior.jpg al dar click en el icono de ayuda dentro de Banner Exterior en la vista "Banner Exterior"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void clickFileBannerExteriorJpg() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(nameBannerExteriorJpg)).click();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al hacer click en el archivo Banner Exterior.jpg", e);
        }
    }
    
    /**
     * Metodo para verificar que se muestre correctamente el archivo Banner Exterior.jpg al hacer click en el icono de ayuda dentro de Banner Exterior en la vista "Banner Exterior"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void verificarImagenJpgMostradaCorrectamente() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(isBannerExteriorJpgVisibleCorrectly));
            ele.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al verificar que se muestre correctamente el archivo Banner Exterior.jpg", e);
        }
    }
    
    /**
     * Metodo para validar que se muestre el archivo 'Manuales PROMOXXO Video.mp4' al dar click en el icono de ayuda dentro de Banner Exterior en la vista "Banner Exterior"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 05/05/2026
     */
    public void isManualesPromoxxoVideoDisplayed() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(nameManualesPromoxxoVideo));
            ele.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al verificar que se muestre el archivo 'Manuales PROMOXXO VIDEO.mp4'", e);
        }
    }
    
    /**
     * Metodo para hacer click en el archivo 'Manuales PROMOXXO VIDEO.mp4' dentro de la vista "Banner Exterior"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 05/05/2026
     */
    public void clickManualesPromoxxoVideo() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(nameManualesPromoxxoVideo)).click();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al hacer click en el archivo 'Manuales PROMOXXO VIDEO.mp4'", e);
        }
    }
    
    /**
     * Metodo para verificar que se muestre correctamente el archivo Banner Exterior.jpg al hacer click en el icono de ayuda dentro de Banner Exterior en la vista "Banner Exterior"
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 04/05/2026
     */
    public void verificarManualesPromoxxoVideoSeMuestraCorrectamente() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            WebElement ele = wait.until(ExpectedConditions.visibilityOfElementLocated(isManualesPromoxxoVideoVisibleCorrectly));
            ele.isDisplayed();
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al verificar que se muestre correctamente el archivo 'Manuales PROMOXXO VIDEO.mp4'", e);
        }
    }
}