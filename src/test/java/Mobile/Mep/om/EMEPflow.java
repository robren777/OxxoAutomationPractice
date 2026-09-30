package Mobile.Mep.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.connection.ConnectionState;
import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class EMEPflow {

    /*
     * Variables Globales
     ******************************************************************************************/
    private TestCase testCase;
    private AppiumUtil au;

    /*
     * Localizadores
     ******************************************************************************************/
    private final By opcionEvaluacionMep =By.xpath("//android.widget.TextView[@text=\"Evaluación MEP\"]");
    private final By tomarFotoCabecera = By.xpath("(//android.widget.TextView[@text=\"Tomar Foto(s)\"])[2]");
    private final By btnPermitirSoloMientrasSeUsaLaApp = By.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button");
    private final By txtInstruccionCaptura = By.xpath("//android.widget.TextView[@text='Inicia en la charola más alta y captura hasta la última de la foto anterior.']");
    private final By txtInstruccionCaptura2 = By.xpath("//android.widget.TextView[@text=\"Inicia en la charola más baja y captura la sección indicada en la imagen.\"]"); 
    private final By btnCancelar = AppiumBy.accessibilityId("Cancelar");
    private final By opcionExhibidores = By.xpath("//android.widget.TextView[@text='Exhibidores']");
    private final By btnTomarFotosExhibidores =By.xpath("//android.widget.TextView[@text='Exhibidores']/ancestor::android.view.ViewGroup[1]//*[@text='Tomar Foto(s)']");
    private final By opcionBanners = By.xpath("//android.widget.TextView[@text='Banners']");
    private final By btnTomarFotosBanners = By.xpath("(//android.widget.TextView[@text='Tomar Foto(s)'])[1]");
    private final By txtEvaluacionMep = By.xpath("//android.widget.TextView[@text='Evaluación MEP']");
    private final By btnContinuar = By.xpath("//android.widget.TextView[@text='Continuar']");
    private final By txtCabecera1Camara = By.xpath("//android.widget.TextView[@text='CABECERA 1']");
    private final By btnInformacion = AppiumBy.androidUIAutomator("new UiSelector().className(\"com.horcrux.svg.k0\").instance(1)");
    private final By btnCapturarFoto = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(28)");
    private final By btnCapturarFotoGondola = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(25)");
    private final By btnRegresar = By.xpath("(//android.view.ViewGroup[@resource-id='icon-button'])[1]");
    private final By txtCabecera1 = By.xpath("//android.widget.TextView[@text='CABECERA 1']");
    private final By imgFotoPreview = By.xpath("//android.widget.ImageView");
    private final By btnRetomar = By.xpath("(//android.view.ViewGroup[@resource-id='icon-button'])[2]");
    private final By btnGuardarAvance = By.xpath("//android.view.ViewGroup[@content-desc='Guardar Avance']");
    private final By btnGuardarYContinuar = By.xpath("//android.view.ViewGroup[@content-desc='Guardar y continuar']");
    private final By txtGuardarProceso = By.xpath("//android.widget.TextView[@text='¿Quieres guardar este proceso?']");
    private final By btnContinuarProceso = By.xpath("//android.view.ViewGroup[@content-desc='Continuar']");
    private final By txtMensajeGuardarProceso = By.xpath("//android.widget.TextView[@text='Todos los cambios realizados serán guardados y podrás retomarlo más tarde para continuar con el siguiente paso.']");    
    private final By btnCancelarProceso = By.xpath("//android.view.ViewGroup[@content-desc=\"Cancelar\"]");
    private final By txtProgresoGuardadoExitosamente = By.xpath("//android.widget.TextView[@text='Progreso guardado exitosamente']");
    private final By txtSeccionSuperior = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Sección superior\")");
    private final By txtSeccionInferior = By.xpath( "//android.widget.TextView[contains(@text,'Sección inferior')]");
    private final By btnFinalizar = AppiumBy.accessibilityId("Finalizar");
    private final By btnCerrar = By.xpath("//android.widget.TextView[@text='Cerrar']");
    private final By btnAlbum = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\")");
    private final By btnRetomarAlbum = AppiumBy.androidUIAutomator("new UiSelector().className(\"com.horcrux.svg.SvgView\").instance(3)");
    private final By imgFotoCapturada = By.xpath("//android.widget.TextView[@text='1 foto:']/following::android.widget.ImageView[1]");
    private final By txtGuiaTomaFotografias = AppiumBy.androidUIAutomator("new UiSelector().text(\"Guía para toma de fotografías\")");
    private final By btnActualizar = By.xpath("//android.widget.TextView[@text=\"Actualizar\"]");
    private final By btnTomarFotoGondolas = By.xpath("//android.widget.TextView[@text='Góndolas']/ancestor::android.view.ViewGroup[1]//*[@content-desc='Tomar Foto(s)']");
    private final By previewGondolas = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(23)");
    private final By txtValidacionCodigosIncompleta = AppiumBy.androidUIAutomator("new UiSelector().text(\"La validación de códigos está incompleta\")");
    private final By btnRegresarEV = AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"icon-button\").instance(0)");
    private final By cabecerasFinalizado = By.xpath("//android.widget.TextView[@text='12 fotos:']/following::android.widget.ImageView[1]");
    private final By txtFolioProduccion = AppiumBy.androidUIAutomator("new UiSelector().text(\"Folio de Producción:\")");
    private final By opcionResultadoEvaluacionMEP = AppiumBy.androidUIAutomator("new UiSelector().text(\"Resultado Evaluación MEP\")");
    private final By txtCalificacionExhibicionCabeceras =By.xpath("//android.widget.TextView[@text='Cabeceras']/ancestor::android.view.ViewGroup[3]//android.widget.TextView[@text='Calificación Exhibición:']");
    private final By txtCalificacionPOP = By.xpath( "//android.view.ViewGroup[.//android.widget.TextView[@text='Cabeceras']]//android.widget.TextView[@text='Calificación POP:']");
    private final By contenedorCalificacionPOP = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(54)");
    private final By txtResultadoTienda = AppiumBy.androidUIAutomator("new UiSelector().text(\"Resultado tienda:\")");
    /**
     * Constructor
     */
    public EMEPflow(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
    }  
    /**
     * Valida si un elemento se encuentra visible
     * @param locator Identificador del elemento
     * @param timeout Tiempo máximo de espera en segundos
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 10/07/2026
     */
    public boolean isElementVisible(By locator, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;

        } catch (Exception e) {
            return false;
        }
    }    
    /**
	 * Espera a que un elemento se muestre dentro de un periodo de tiempo
	 * 
	 * @author Marcos Morales
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo maximo de espera
	 * @date 09/10/2024
	 */
    public void waitForVisibility(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(timeout));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception ex) {
			throw new RuntimeException("El elemento no se volvió visible dentro del tiempo esperado: ", ex);
		}
	}
    /**
     * Valida si boton Actualizar se encuentra visible y da clic
     * @param locator Identificador del elemento
     * @param timeout Tiempo máximo de espera en segundos
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 10/07/2026
     */
    public void clickBotonActualizarSiExiste() {
        try {
            if (isElementVisible(btnActualizar, 5)) {
                au.driver.findElement(btnActualizar).click();
            }
        } catch (Exception e) {
            // No hace nada, el botón es opcional.
        }
    }
    /**
     * Realiza clic en la opción 'Evaluación MEP' dentro del apartado PromOxxo
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 09/10/2024
     */
    public void clickOpcionEvaluacionMep() throws Exception {
        try {
            /* Paso 1 - Desplazarse hasta la opción 'Evaluación MEP' */
            au.scrollToElement(opcionEvaluacionMep);

            /* Paso 2 - Esperar a que la opción sea seleccionable y hacer clic */
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.elementToBeClickable(opcionEvaluacionMep)).click();
            
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en la opción Evaluación MEP", e);
        }
    }
    /**
     * Realiza clic en la opción 'Evaluación MEP' dentro del apartado PromOxxo
     * @author Ivan Yakab
     * @return void
     * @throws Exception
     * @date 09/10/2024
     */
    public void clickOpcionResultadoEvaluacionMep() throws Exception {
        try {
            /* Paso 1 - Desplazarse hasta la opción 'Evaluación MEP' */
            au.scrollToElement(opcionResultadoEvaluacionMEP);

            /* Paso 2 - Esperar a que la opción sea seleccionable y hacer clic */
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.elementToBeClickable(opcionResultadoEvaluacionMEP)).click();
            
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en la opción Evaluación MEP", e);
        }
    }
    /**
	 * Hacer click en botón tomar foto de la sección cabecera
	 * @param locator Identificador del elemento
	 * @param timeout Tiempo máximo de espera en segundos
	 * @author Mariana Avitia
	 * @return void
	 * @throws Exception
	 * @date 09/07/2026
	 */
    public void clickTomarFotoCabecera() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(tomarFotoCabecera)).click();

            //testCase.addScreenShotCurrentStep(au, "Click a boton "Tomar foto(s)" en sección cabecera");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic a en Tomar Foto", e);
        }
    }
    /**
     * Hacer click en botón permitir ubicación solo mientras se usa la app
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickPermitirSoloMientrasSeUsaLaApp() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnPermitirSoloMientrasSeUsaLaApp)).click();

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón Permitir solo mientras se usa la app", e);
        }
    }
    /**
     * Hacer click en botón cancelar
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickCancelar() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnCancelar)).click();

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón Cancelar", e);
        }
    }
    /**
     * Hacer click en botón tomar fotos de la sección banners
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickTomarFotosBanners() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnTomarFotosBanners)).click();

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón Tomar Foto(s) de la sección Banners", e);
        }
      }
    /**
     * Hacer click en botón tomar fotos de la sección banners
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 09/07/2026
     */
    public void clickTomarFotosExhibidores() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnTomarFotosExhibidores)).click();

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón Tomar Foto(s) de la sección Banners", e);
        }
      }
    /**
     * Hacer click en botón About
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 10/07/2026
     */
    public void clickInformacion() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnInformacion)).click();

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón About", e);
        }
    }
    /**
     * Hacer click en botón Capturar Foto
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 10/07/2026
     */
    public void clickCapturarFoto() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnCapturarFoto)).click();

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón Capturar Foto", e);
        }
    }
    /**
     * Hacer click en botón Guardar Avance
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 16/07/2026
     */
    public void clickGuardarAvance() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.elementToBeClickable(btnGuardarAvance)).click();

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al dar clic en el botón Capturar Foto", e);
        }
    }
    /**
     * Validar captura de fotos en grupo promocional Cabeceras
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 10/07/2026
     */
    public boolean validarCapturaFotosCabeceras() throws Exception {
        try {

            clickTomarFotoCabecera();

            if (isElementVisible(btnPermitirSoloMientrasSeUsaLaApp, 5)) {
                clickPermitirSoloMientrasSeUsaLaApp();
            }

            boolean visible = isElementVisible(txtInstruccionCaptura, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addTextEvidenceCurrentStep("Se abre cámara y se muestra pop up");
            }

            clickCancelar();

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar captura de fotos en Cabeceras", e);
        }
    }
    /**
     * Validar captura de fotos en grupo promocional Exhibidores
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 10/07/2026
     */
    public boolean validarCapturaFotosExhibidores() throws Exception {
        try {

            au.scrollToElement(opcionExhibidores);

            clickTomarFotosExhibidores();

            if (isElementVisible(btnPermitirSoloMientrasSeUsaLaApp, 5)) {
                clickPermitirSoloMientrasSeUsaLaApp();
            }

            boolean visible = isElementVisible(txtInstruccionCaptura, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addTextEvidenceCurrentStep("Se abre cámara y se muestra pop up");
            }

            clickCancelar();

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar captura de fotos en Exhibidores", e);
        }
    }
    /**
     * Validar captura de fotos en grupo promocional Banners
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 10/07/2026
     */
    public boolean validarCapturaFotosBanners() throws Exception {
        try {

            au.scrollToElement(opcionBanners);

            clickTomarFotosBanners();

            testCase.addScreenShotCurrentStep(au, "");
            testCase.addTextEvidenceCurrentStep("Se abre cámara y no se muestra pop up");

            if (isElementVisible(btnPermitirSoloMientrasSeUsaLaApp, 5)) {

                clickPermitirSoloMientrasSeUsaLaApp();

                if (isElementVisible(txtInstruccionCaptura, 5)) {
                    return false;
                }

                clickCancelar();
            }

            testCase.addCodeEvidenceCurrentStep("Caso exitoso: No apareció mensaje de permiso en Banners");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar captura de fotos en Banners", e);
        }
    }
    /**
     * Validar que se muestre mensaje de instrucción de captura en Cabeceras
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 10/07/2026
     */
    public boolean validarMensajeInstruccionCapturaCabeceras() throws Exception {
        try {

            clickTomarFotoCabecera();

            if (isElementVisible(btnPermitirSoloMientrasSeUsaLaApp, 5)) {
                clickPermitirSoloMientrasSeUsaLaApp();
            }

            boolean visible = isElementVisible(txtInstruccionCaptura, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep("Caso exitoso: Se mostró la instrucción de captura en Cabeceras");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar mensaje de instrucción de captura en Cabeceras", e);
        }
    }
    /**
     * Validar botón Cancelar en captura de Cabeceras y regreso a pantalla Evaluación MEP
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 10/07/2026
     */
    public boolean validarBotonCancelarCapturaCabeceras() throws Exception {
        try {

            clickTomarFotoCabecera();

            if (isElementVisible(btnPermitirSoloMientrasSeUsaLaApp, 5)) {
                clickPermitirSoloMientrasSeUsaLaApp();
            }

            waitForVisibility(btnCancelar, 30);

            clickCancelar();

            boolean visible = isElementVisible(txtEvaluacionMep, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep("Caso exitoso: Se mostró botón Cancelar y regresó a pantalla Evaluación MEP");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar botón Cancelar y regreso a Evaluación MEP", e);
        }
    }
    /**
     * Validar botón Continuar y apertura de cámara en captura de Cabeceras
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 10/07/2026
     */
    public boolean validarBotonContinuarCapturaCabeceras() throws Exception {
        try {

            clickTomarFotoCabecera();

            if (isElementVisible(btnPermitirSoloMientrasSeUsaLaApp, 5)) {
                clickPermitirSoloMientrasSeUsaLaApp();
            }

            waitForVisibility(btnContinuar, 30);

            au.driver.findElement(btnContinuar).click();

            boolean visible = isElementVisible(txtCabecera1Camara, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep("Caso exitoso: Se presionó Continuar y se mostró la sección de cámara CABECERA 1");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar botón Continuar y apertura de cámara", e);
        }
    }
    /**
     * Validar acceso a la pantalla '?'
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 10/07/2026
     */
    public boolean validarMasInformacion() throws Exception {
        try {

            clickInformacion();

            boolean visible = isElementVisible(txtInstruccionCaptura, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep("Caso exitoso: Se mostró la pantalla de captura al dar clic en '?'");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar acceso a la pantalla '?'", e);
        }
    }
    /**
     * Validar que al capturar una foto se muestre la pantalla Preview de Foto
     * @author Mariana Avitia
     * @return void
     * @throws Exception
     * @date 10/07/2026
     */
    public boolean validarPreviewFoto() throws Exception {
        try {

            clickCapturarFoto();

            waitForVisibility(btnRegresar, 30);
            waitForVisibility(txtCabecera1, 30);
            waitForVisibility(imgFotoPreview, 30);
            waitForVisibility(btnRetomar, 30);
            waitForVisibility(btnGuardarAvance, 30);

            boolean visible = isElementVisible(btnGuardarYContinuar, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep("Caso exitoso: Se mostró la pantalla Preview de Foto");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la pantalla Preview de Foto", e);
        }
    }
    /**
     * Da clic en el botón Retomar y valida que se muestre la pantalla de captura
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 14/07/2026
     */
    public boolean validarBotonRetomar() throws Exception {
        try {
        	
        	 clickCapturarFoto();

            // Validar que el botón Retomar esté visible
            waitForVisibility(btnRetomar, 30);

            // Dar clic en el botón Retomar
            au.driver.findElement(btnRetomar).click();

            // Validar que se muestre la instrucción de captura
            boolean visible = isElementVisible(txtInstruccionCaptura, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep("Caso exitoso: Se presionó el botón Retomar y se mostró la pantalla de captura.");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el botón Retomar.", e);
        }
    }
    /**
     * Da clic en el botón Guardar y continuar y valida que se muestre la siguiente pantalla
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 14/07/2026
     */
    public boolean validarBotonGuardarYContinuar() throws Exception {
        try {

        	clickCapturarFoto();
        	
            waitForVisibility(btnGuardarYContinuar, 30);

            au.driver.findElement(btnGuardarYContinuar).click();

            boolean visible = isElementVisible(txtInstruccionCaptura2, 40);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep("Caso exitoso: Se presionó el botón Guardar y continuar y se mostró la siguiente pantalla.");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el botón Guardar y continuar.", e);
        }
    }
    /**
     * Da clic en el botón Guardar Avance y valida que se muestre la pantalla esperada
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 14/07/2026
     */
    public boolean validarBotonGuardarAvance() throws Exception {
        try {
        	
        	clickCapturarFoto();
        	waitForVisibility(btnGuardarAvance, 30);
        	au.driver.findElement(btnGuardarAvance).click();
        	 
        	boolean visible = isElementVisible(txtGuardarProceso, 30)&&
        	isElementVisible(txtGuardarProceso, 30) &&
        	isElementVisible(btnContinuarProceso, 30) &&
        	isElementVisible(txtMensajeGuardarProceso, 30);

            

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep("Caso exitoso: Se presionó el botón Guardar Avance y se mostró la pantalla esperada.");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el botón Guardar Avance.", e);
        }
    }

    /**
     * Da clic en el botón Guardar Cancelar Guardar Avance
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 16/07/2026
     */
    public boolean validarCancelarAvance() throws Exception {
        try {
        	
        	clickCapturarFoto();

        	waitForVisibility(btnGuardarAvance, 30);
        	au.driver.findElement(btnGuardarAvance).click();

        	waitForVisibility(btnCancelarProceso, 30);
        	au.driver.findElement(btnCancelarProceso).click();
        	 
        	boolean visible =
        	        isElementVisible(btnRegresar, 30) &&
        	        isElementVisible(txtCabecera1, 30) &&
        	        isElementVisible(imgFotoPreview, 30) &&
        	        isElementVisible(btnRetomar, 30) &&
        	        isElementVisible(btnGuardarAvance, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep("Caso exitoso: Se presionó el botón Guardar Avance y se mostró la pantalla esperada.");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el botón Guardar Avance.", e);
        }
    }
    /**
     * Presiona el botón Continuar del popup Guardar Avance y valida
     * que se muestre el mensaje de éxito.
     *
     * @author Mariana Avitia
     * @return true si el mensaje se muestra correctamente
     * @throws Exception
     * @date 16/07/2026
     */
    public boolean validarProgresoGuardado() throws Exception {
        try {

            waitForVisibility(btnContinuarProceso, 30);
            au.driver.findElement(btnContinuarProceso).click();

            waitForVisibility(txtProgresoGuardadoExitosamente, 30);

            testCase.addScreenShotCurrentStep(au, "");
            testCase.addCodeEvidenceCurrentStep(
                    "Caso exitoso: Se presionó el botón Continuar y se mostró el mensaje 'Progreso guardado exitosamente'.");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el mensaje de Progreso guardado exitosamente.", e);
        }
    }
    /**
     * Valida que se muestre el texto "Sección inferior 2/12" en la pantalla.
     *
     * @author Mariana Avitia
     * @return true si el texto se muestra correctamente
     * @throws Exception
     * @date 17/07/2026
     */
    public boolean validarSeccionInferiorDos() throws Exception {
        try {
        	clickTomarFotoCabecera();

            if (isElementVisible(btnPermitirSoloMientrasSeUsaLaApp, 5)) {
                clickPermitirSoloMientrasSeUsaLaApp();
            }

            waitForVisibility(txtSeccionSuperior, 30);

            testCase.addScreenShotCurrentStep(au, "");
            testCase.addCodeEvidenceCurrentStep(
                    "Caso exitoso: Se mostró correctamente el texto 'Sección inferior 2/12'.");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar que se muestre la sección inferior 2/12.", e);
        }
    }
    /**
     * Valida que se muestre el botón Finalizar en la pantalla.
     *
     * @author Mariana Avitia
     * @return true si el botón se muestra correctamente
     * @throws Exception
     * @date 17/07/2026
     */
    public boolean validarBotonFinalizar() throws Exception {
        try {
            
            clickCapturarFoto();

            waitForVisibility(btnFinalizar, 30);

            testCase.addScreenShotCurrentStep(au, "");
            testCase.addCodeEvidenceCurrentStep(
                    "Caso exitoso: Se mostró correctamente el botón Finalizar.");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar que se muestre el botón Finalizar.", e);
        }
    }
    /**
     * Cierra la cámara, selecciona el álbum, retoma la captura y valida
     * que se muestre la instrucción de captura.
     *
     * @author Mariana Avitia
     * @return true si la instrucción se muestra correctamente
     * @throws Exception
     * @date 17/07/2026
     */
    public boolean validarRetomarAlbum() throws Exception {
        try {

        	
    		validarBotonContinuarCapturaCabeceras();
    		
    		validarBotonGuardarYContinuar();
        	
            waitForVisibility(btnCerrar, 30);
            au.driver.findElement(btnCerrar).click();

            waitForVisibility(btnAlbum, 30);
            au.driver.findElement(btnAlbum).click();

            waitForVisibility(btnRetomarAlbum, 30);
            au.driver.findElement(btnRetomarAlbum).click();

            waitForVisibility(txtInstruccionCaptura, 30);

            testCase.addScreenShotCurrentStep(au, "");
            testCase.addCodeEvidenceCurrentStep(
                    "Caso exitoso: Se cerró la cámara, se seleccionó el álbum, se retomó la captura y se mostró correctamente la instrucción de captura.");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la instrucción de captura al retomar desde el álbum.", e);
        }
    }
    
    /**
     * Cierra la cámara, selecciona el álbum, retoma la captura y valida
     * que se muestre la fotografía capturada.
     *
     * @author Mariana Avitia
     * @return true si la fotografía se muestra correctamente
     * @throws Exception
     * @date 17/07/2026
     */
    public boolean validarReCapturaAlbum() throws Exception {
        try {

            validarBotonContinuarCapturaCabeceras();

            validarBotonGuardarYContinuar();

            waitForVisibility(btnCerrar, 30);
            au.driver.findElement(btnCerrar).click();

            waitForVisibility(btnAlbum, 30);
            au.driver.findElement(btnAlbum).click();

            waitForVisibility(btnRetomarAlbum, 30);
            au.driver.findElement(btnRetomarAlbum).click();

            waitForVisibility(txtInstruccionCaptura, 30);

            waitForVisibility(btnContinuarProceso, 30);
            au.driver.findElement(btnContinuarProceso).click();

            waitForVisibility(btnCapturarFoto, 30);
            au.driver.findElement(btnCapturarFoto).click();
            
            waitForVisibility(btnGuardarYContinuar, 30);
            au.driver.findElement(btnGuardarYContinuar).click();

            // Valida que se muestre la fotografía capturada
            waitForVisibility(imgFotoCapturada, 30);

            testCase.addScreenShotCurrentStep(au, "");
            testCase.addCodeEvidenceCurrentStep(
                    "Caso exitoso: Se retomó la captura y se mostró correctamente la fotografía capturada.");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la fotografía capturada al retomar desde el álbum.", e);
        }
    }
    /**
     * Validar acceso a la pantalla '?' desde album de fotos
     *
     * @author Mariana Avitia
     * @return true si la instrucción se muestra correctamente
     * @throws Exception
     * @date 17/07/2026
     */
    public boolean validarMasInformacionAlbum() throws Exception {
    	try {

            validarBotonContinuarCapturaCabeceras();

            validarBotonGuardarYContinuar();

            waitForVisibility(btnCerrar, 30);
            au.driver.findElement(btnCerrar).click();

            waitForVisibility(btnAlbum, 30);
            au.driver.findElement(btnAlbum).click();

            waitForVisibility(btnInformacion, 30);
            au.driver.findElement(btnInformacion).click();

            // Valida que se muestre la guía
            waitForVisibility(txtGuiaTomaFotografias, 30);

            testCase.addScreenShotCurrentStep(au, "");
            testCase.addCodeEvidenceCurrentStep(
                    "Caso exitoso: Se abrió la guía para toma de fotografías correctamente.");

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la guía para toma de fotografías.", e);
        }
    }
    /**
     * Cambiar el estado de conexión del dispositivo móvil
     *
     * @author Mariana Avitia
     * @param estadoConexion valor que define el estado de conexión:
     *                       0 - Sin conexión (WiFi y datos apagados)
     *                       1 - Modo avión
     *                       2 - Solo WiFi
     *                       4 - Solo datos móviles
     *                       6 - WiFi y datos activos
     * @throws Exception
     * @date 20/07/2026
     */
    public void cambiarEstadoConexion(int estadoConexion) throws Exception {
        try {

            au.driver.setConnection(new ConnectionState(estadoConexion));

        } catch (Exception e) {
            throw new Exception("Error al cambiar estado de conexión: " + e.getMessage());
        }
    }
    /**
     * Valida que el botón Guardar Avance no se muestre en pantalla
     *
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 20/07/2026
     */
    public boolean validarBotonGuardarAvanceNoVisible() throws Exception {
        try {
        	
        	validarBotonContinuarCapturaCabeceras();
        	
        	clickCapturarFoto();

            boolean noVisible = !isElementVisible(btnGuardarAvance, 5);

            if (noVisible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep(
                        "Caso exitoso: El botón Guardar Avance no se muestra en pantalla.");
            } else {
                testCase.addCodeEvidenceCurrentStep(
                        "Error: El botón Guardar Avance se encuentra visible en pantalla.");
            }

            return noVisible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar que el botón Guardar Avance no se muestre.", e);
        }
    }
    /**
     * Da clic en el botón Tomar Foto(s) de Góndolas y valida que se muestre la pantalla de captura
     *
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 21/07/2026
     */
    public boolean validarBotonTomarFotoGondolas() throws Exception {
        try {

            waitForVisibility(btnTomarFotoGondolas, 30);
            au.driver.findElement(btnTomarFotoGondolas).click();
            
            waitForVisibility(btnCapturarFotoGondola, 30);
            au.driver.findElement(btnCapturarFotoGondola).click();

            boolean visible = isElementVisible(previewGondolas, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep(
                        "Caso exitoso: Se presionó el botón Tomar Foto(s) de Góndolas y se mostró la pantalla de captura correctamente.");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar el botón Tomar Foto(s) de Góndolas.", e);
        }
    }  
    /**
     * Regresa a la pantalla de Evaluación MEP, continúa el proceso y valida
     * que se muestre la Sección inferior.
     *
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 21/07/2026
     */
    public boolean validarContinuarProcesoEvaluacionMep() throws Exception {
        try {
        	
        	validarBotonContinuarCapturaCabeceras();
        	validarBotonGuardarAvance();
        	validarProgresoGuardado();
        	
        	Thread.sleep(5000);
        	//waitForVisibility(btnRegresarEV, 30);
            au.driver.findElement(btnRegresarEV).click();

            clickOpcionEvaluacionMep();

            waitForVisibility(txtValidacionCodigosIncompleta, 30);

            boolean visible = isElementVisible(txtValidacionCodigosIncompleta, 30);

            if (!visible) {
                testCase.addCodeEvidenceCurrentStep(
                        "Error: No se mostró el mensaje 'La validación de códigos está incompleta'.");
                return false;
            }

            au.driver.findElement(btnContinuarProceso).click();

            Thread.sleep(3000);

            boolean resultado = isElementVisible(txtSeccionInferior, 30);

            if (resultado) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep(
                        "Caso exitoso: Se continuó el proceso y se mostró correctamente la Sección inferior.");
            }

            return resultado;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la continuación del proceso en Evaluación MEP.", e);
        }
    }
    
    /**
     * Realiza la captura de fotografías hasta que se muestre el botón Finalizar.
     *
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 21/07/2026
     */
    public boolean validarCapturaFotosHastaFinalizar() throws Exception {
        try {

            int intentos = 0;
            int maxIntentos = 20;
            boolean visibleFinalizar = false;

            do {

                // Validar si ya se encuentra en la pantalla final
                visibleFinalizar = isElementVisible(btnFinalizar, 5);

                if (!visibleFinalizar) {

                    // Capturar fotografía
                    waitForVisibility(btnCapturarFoto, 30);
                    au.driver.findElement(btnCapturarFoto).click();

                    // Validar si aparece Guardar y continuar
                    if (isElementVisible(btnGuardarYContinuar, 10)) {

                        au.driver.findElement(btnGuardarYContinuar).click();

                        // Cerrar modal después de guardar
                        waitForVisibility(btnCerrar, 30);
                        au.driver.findElement(btnCerrar).click();

                    }

                    intentos++;

                }

            } while (!visibleFinalizar && intentos < maxIntentos);


            if (visibleFinalizar) {

                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep(
                        "Caso exitoso: Se realizaron las capturas necesarias y se mostró el botón Finalizar correctamente.");

            } else {

                testCase.addCodeEvidenceCurrentStep(
                        "Error: No se mostró el botón Finalizar después de realizar las capturas.");

            }

            return visibleFinalizar;

        } catch (Exception e) {

            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al realizar captura de fotografías hasta visualizar Finalizar.", e);

        }
    }  
    /**
     * Da clic en el botón Finalizar y valida que se muestre la pantalla de Cabeceras finalizado.
     *
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 21/07/2026
     */
    public boolean validarFinalizarEvaluacion() throws Exception {
        try {

            waitForVisibility(btnFinalizar, 30);
            au.driver.findElement(btnFinalizar).click();

            boolean visible = isElementVisible(cabecerasFinalizado, 30);

            if (visible) {

                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep(
                        "Caso exitoso: Se presionó el botón Finalizar y se mostró correctamente la pantalla de Cabeceras finalizado.");

            } else {

                testCase.addCodeEvidenceCurrentStep(
                        "Error: No se mostró la pantalla de Cabeceras finalizado después de presionar Finalizar.");

            }

            return visible;

        } catch (Exception e) {

            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al finalizar la evaluación y validar pantalla de Cabeceras finalizado.", e);

        }
    }
    /**
     * Da clic en el botón Continuar y valida que se muestre el Folio de Producción.
     *
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 21/07/2026
     */
    public boolean validarFolioProduccion() throws Exception {
        try {
        	
        	validarCapturaFotosHastaFinalizar();
        	validarFinalizarEvaluacion() ;

            // Continuar con el proceso
            waitForVisibility(btnContinuarProceso, 30);
            au.driver.findElement(btnContinuarProceso).click();

            // Validar que se muestre el Folio de Producción
            boolean visible = isElementVisible(txtFolioProduccion, 30);

            if (visible) {

                testCase.addScreenShotCurrentStep(au, "");
                testCase.addCodeEvidenceCurrentStep(
                        "Caso exitoso: Se presionó el botón Continuar y se mostró correctamente la pantalla Folio de Producción.");

            } else {

                testCase.addCodeEvidenceCurrentStep(
                        "Error: No se mostró el Folio de Producción después de presionar Continuar.");

            }

            return visible;

        } catch (Exception e) {

            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al continuar el proceso y validar el Folio de Producción.", e);

        }
    }
    /**
     * Validar que se muestre Resultado Evaluación MEP
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 21/07/2026
     */
    public boolean validarResultadoTiendaEvaluacionMEP() throws Exception {
        try {

            boolean visible = isElementVisible(txtResultadoTienda, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addTextEvidenceCurrentStep("Se muestra correctamente Resultado Tienda dentro de Evaluación MEP");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar Resultado Tienda detro de Evaluación MEP", e);
        }
    }
    /**
     * Validar que se muestre Calificacion POP dentro de Cabeceras
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 21/07/2026
     */
    public boolean validarCalificacionPOPCabeceras() throws Exception {
        try {

            boolean visible = isElementVisible(txtCalificacionPOP, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addTextEvidenceCurrentStep("Se muestra correctamente Calificacion POP dentro de Cabeceras");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar  Calificacion POP dentro de Cabeceras", e);
        }
    }
    /**
     * Validar que se muestre Calificacion POP dentro de Cabeceras
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 21/07/2026
     */
    public boolean validarCalificacionPOPResultadosMEP() throws Exception {
        try {

            boolean visible = isElementVisible(contenedorCalificacionPOP, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addTextEvidenceCurrentStep("Se muestra correctamente Calificacion POP dentro de Resultados Evaluacion MEP");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar  Calificacion POP dentro de Resultados Evaluacion MEP", e);
        }
    }
    /**
     * Validar que se muestre Calificacion Exhibicion dentro de Cabeceras
     * @author Mariana Avitia
     * @return boolean
     * @throws Exception
     * @date 21/07/2026
     */
    public boolean validarCalificacionExhibicionCabeceras() throws Exception {
        try {

            boolean visible = isElementVisible(txtCalificacionExhibicionCabeceras, 30);

            if (visible) {
                testCase.addScreenShotCurrentStep(au, "");
                testCase.addTextEvidenceCurrentStep("Se muestra correctamente Calificacion Exhibicion dentro de Cabeceras");
            }

            return visible;

        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar Calificacion Exhibicion dentro de Cabeceras", e);
        }
    }
}