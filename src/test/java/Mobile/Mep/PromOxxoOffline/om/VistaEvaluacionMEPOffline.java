package Mobile.Mep.PromOxxoOffline.om;

import modelo.BaseExecution;
import modelo.TestCase;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.appium.java_client.AppiumBy;
import utils.AppiumUtil;

/**
 * DESCRIPCIÓN: Page Object Model que representa la vista de Evaluación MEP
 * en modo sin conexión. Contiene los localizadores y métodos de interacción/validación
 * para el flujo completo de evaluación: toma de fotografías, navegación entre secciones,
 * manejo de modales de error, galería de fotos y finalización de evaluación.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *   - Casos de prueba del módulo PromOXXO Offline relacionados con Evaluación MEP.
 *
 * PREREQUISITOS:
 *   - El usuario ha iniciado sesión correctamente en modo sin conexión.
 *   - El usuario está en la pantalla "Panel de Control" en modo sin conexión.
 *
 * INSUMOS:
 *   - N/A
 *
 * @author Mario A. De León Rdz.
 * @date 15/Diciembre/2025
 */
public class VistaEvaluacionMEPOffline {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    @SuppressWarnings("unused")
	private BaseExecution baseExecution;
	private UtilOffline utilOffline;

    
	/*
	 * Localizadores de la vista Evaluacion MEP
	 ******************************************************************************************/
    private final By titleEvaluacionMep = By.xpath("//android.widget.TextView[@text=\"Evaluación MEP\"]");
    private final By btnFlechaAtras = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[1]");

    
    /*
	 * Localizadores del modal de Error 'Informacion del periodo no disponible'
	 ******************************************************************************************/
    
    private By modalErrorContainer = By.xpath("//*[@resource-id=\"modal-container\"]");
    private By btnReintentar = By.xpath("//android.view.ViewGroup[@content-desc=\"Reintentar\"]");
    
    private By btnCancelar = By.xpath("//android.view.ViewGroup[@content-desc=\"Cancelar\"]");
    
    /*
	 * Localizadores Evaluacion MEP cuando existen POPs
	 ******************************************************************************************/
    
    private By btnTomarFotoPrimerPop = By.xpath("(//android.view.ViewGroup[@content-desc=\"Tomar Foto(s)\"])[1]");

    private final By btnContinuarEvaluacion = AppiumBy.accessibilityId("Continuar evaluación");
    private final By btnGuardarYContinuar = AppiumBy.accessibilityId("Guardar y Continuar");
    private final By tagGuardadoEnModoSinConexion = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(32)");
    private final By textModalEstaSeguroQueDeseaCancelar = AppiumBy.androidUIAutomator("new UiSelector().text(\"¿Está seguro que desea cancelar?\")");
    private final By btnSiContinuar = AppiumBy.accessibilityId("Sí, continuar");
    private final By btnSiCancelarModalDeseaCancelar = AppiumBy.accessibilityId("Cancelar");
    private final By textModalActualizando = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Actualizando\")");
    private final By textModalFalloLaSincronizacion = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Falló la sincronización\")");
    private final By btnReintentarModalFalloLaSincronizacion = AppiumBy.accessibilityId("Reintentar");
    private final By btnCerrarModalFalloLaSincronizacion = AppiumBy.accessibilityId("Cerrar");
    private final By textModalReintentandoLaSincronizacion = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Reintentando sincronización\")");
    private final By textModalConexionAInternetRestablecido = AppiumBy.androidUIAutomator("new UiSelector().text(\"La conexión a internet se ha restablecido\")");
    private final By btnContinuarEnModoSinConexion = AppiumBy.accessibilityId("Continuar en Modo sin conexión");
    private final By textModalLimiteMaximoFotografias = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Límite máximo de fotografías\")");
    private final By textMsgConexionRestablecida = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Conexión restablecida\")");
    private final By textMsgProgresoGuardadoExitosamente = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Progreso guardado\")");
    private final By btnTomarFotoFoliosDeProduccion = AppiumBy.accessibilityId("Tomar Foto");
    /*
	 * Localizadores Fotografia 
	 ******************************************************************************************/
    private final By btnCirculoTomarFotografia = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(23)");
    private final By btnCirculoTomarFotografias = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(26)");
    
    private By fotografias = By.xpath("//android.widget.ImageView");
    private By primerFotografia = By.xpath("(//android.widget.ImageView)[1]");

    private By btnFlechaSiguiente = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[3]");
    
    /*
	 * Localizadores Galeria de Fotos 
	 ******************************************************************************************/
    private final By btnContinuar = By.xpath("//android.view.ViewGroup[@content-desc=\"Continuar\"]");
    private final By btnTomarFotoGaleriaFotos = By.xpath("//android.view.ViewGroup[@content-desc=\"Tomar foto\"]");
    
    /*
	 * Localizadores Folio de Produccion
	 ******************************************************************************************/
    private final By btnFinalizar = By.xpath("//android.view.ViewGroup[@content-desc=\"Finalizar\"]");

    
    /**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Vista Evaluacion MEP
	 * @author Mario A. De León Rdz.
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaEvaluacionMEPOffline(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
        this.utilOffline = new UtilOffline(testCase, au, baseExecution);
    }
    
	/**
	 * Valida que se muestre el titulo de evaluación MEP para validar si se encuentra en el apartado.
	 * @return boolean
	 * @author Mario A. De León Rdz.
	 * @throws Exception 
	 * @date 18/Diciembre/2025
	 */
    public boolean validarVistaEvaluacionMep() throws Exception {
	    	try {
		        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
		        wait.until(ExpectedConditions.elementToBeClickable(titleEvaluacionMep)).click();
	    		testCase.addScreenShotCurrentStep(au, "testImage");
	    		return true;
	    	} catch (Exception e) {
	    		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	    		e.printStackTrace();
	    		return false;
	    	}
    };
    
    /**
     * Metodo para hacer click en el icono 'Flecha' 'atras' dentro de la vista 'Evaluacion MEP'
     * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
     */
    public void clickFlechaAtras() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnFlechaAtras)).click();
	    	testCase.addScreenShotCurrentStep(au, "testImage");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        throw new Exception("Error al dar clic en boton Flecha Atras", e);
	    }	
    };
    
    /**
     * Metodo para hacer click en el icono 'Flecha' 'atras' dentro de la vista 'Evaluacion MEP'
     * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
     */
    public void clickFlechaAtrasSinEv() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnFlechaAtras)).click();;
	    } catch (Exception e) {
	    	e.printStackTrace();
	        throw new Exception("Error al dar clic en boton Flecha Atras", e);
	    }	
    }
    
    /**
     * Metodo para validar la existencia del modal de error "Informacion del periodo no disponible"
     * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
     */
    public boolean validarExistenciaModalAlertaPeriodoNoDisponible() throws Exception {
			try {
		    	au.driver.findElement(modalErrorContainer).isDisplayed();
		    	au.driver.findElement(btnCancelar).isDisplayed();
		    	au.driver.findElement(btnReintentar).isDisplayed();
		    	testCase.addScreenShotCurrentStep(au, "testImage");
				return true;
			} catch (Exception e) {
				return false;
			}
    }
    
    
    
    /**
     * Metodo para hacer click en el boton 'Tomar Foto' en la vista "Evaluacion MEP"
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
     */
    public void clickBtnTomarFotoPrimerPop() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnTomarFotoPrimerPop)).click();
 	        testCase.addScreenShotCurrentStep(au, "");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
	    }
    }

    /**
     * Metodo para hacer click en el boton 'Tomar Foto' en la vista "Evaluacion MEP"
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
     */
    public void clickBtnTomarFotoPrimerPopSinEv() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnTomarFotoPrimerPop)).click();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
	    }
    }

    /**
     * Metodo para hacer click en el icono para tomar una fotografia en la funcionalidad 'Toma de fotografia'
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
     */
    public void clickIconoTomarUnaFotografia() throws Exception {
		    try {
		        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
		        wait.until(ExpectedConditions.elementToBeClickable(btnCirculoTomarFotografia)).click();
	 	        testCase.addScreenShotCurrentStep(au, "");
		    } catch (Exception e) {
		    	e.printStackTrace();
		        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
		    }
    }
    
    /**
     * Metodo para hacer click en el icono para tomar una fotografia en la funcionalidad 'Toma de fotografia'
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
     */
    public void clickIconoTomarUnaFotografiaSinEv() throws Exception {
		    try {
		        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
		        wait.until(ExpectedConditions.elementToBeClickable(btnCirculoTomarFotografia)).click();
		    } catch (Exception e) {
		    	e.printStackTrace();
		        throw new Exception("Error al dar clic en Actualizar (Offline)", e);
		    }
    }
    
    /**
     * Método para hacer click en el ícono para tomar múltiples fotografías
     * en la funcionalidad "Toma de fotografía".
     * 
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception El método falla el test si ocurre un error durante la toma de fotografías.
     * @date 22/Diciembre/2025
     */
    public void clickIconoTomarMasFotografias(String CantidadDeClicks) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            int cantidadInt = Integer.parseInt(CantidadDeClicks);

            // CLICK 1 (instance 23)
            WebElement primerBoton = wait.until(
                ExpectedConditions.elementToBeClickable(btnCirculoTomarFotografia)
            );
            primerBoton.click();

            // Espera a que aparezca el botón post-captura
            wait.until(ExpectedConditions.presenceOfElementLocated(btnCirculoTomarFotografias));

            // CLICKS RESTANTES (instance 25)
            for (int i = 2; i <= cantidadInt; i++) {
                WebElement botonFoto = wait.until(
                    ExpectedConditions.elementToBeClickable(btnCirculoTomarFotografias)
                );

                botonFoto.click();

                // Micro-pausa para que la cámara complete el ciclo
                Thread.sleep(200);
            }
            Thread.sleep(1000);
            testCase.addScreenShotCurrentStep(au, "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(
                "Error al tomar múltiples fotografías en la funcionalidad 'Toma de fotografía'", e
            );
        }
    }
    
    /**
     * Método para hacer click en el ícono para tomar múltiples fotografías
     * en la funcionalidad "Toma de fotografía".
     * 
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception El método falla el test si ocurre un error durante la toma de fotografías.
     * @date 22/Diciembre/2025
     */
    public void clickIconoTomarMasFotografiasSinEv(String CantidadDeClicks) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            int cantidadInt = Integer.parseInt(CantidadDeClicks);

            // CLICK 1 (instance 23)
            WebElement primerBoton = wait.until(
                ExpectedConditions.elementToBeClickable(btnCirculoTomarFotografia)
            );
            primerBoton.click();

            // Espera a que aparezca el botón post-captura
            wait.until(ExpectedConditions.presenceOfElementLocated(btnCirculoTomarFotografias));

            // CLICKS RESTANTES (instance 25)
            for (int i = 2; i <= cantidadInt; i++) {
                WebElement botonFoto = wait.until(
                    ExpectedConditions.elementToBeClickable(btnCirculoTomarFotografias)
                );

                botonFoto.click();

                // Micro-pausa para que la cámara complete el ciclo
                Thread.sleep(200);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(
                "Error al tomar múltiples fotografías en la funcionalidad 'Toma de fotografía'", e
            );
        }
    }

    /**
     * Metodo para validar que exista el icono para tomar una fotografia en la funcionalidad 'Toma de fotografia'
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception 
     * @date 22/Diciembre/2025
     */
    public void validarExistenciaIconoTomarUnaFotografia() throws Exception {
    	try {
    		au.driver.findElement(btnCirculoTomarFotografia).isDisplayed();
	    	testCase.addScreenShotCurrentStep(au, "testImage");
	    	testCase.addTextEvidenceCurrentStep("Se abre la pantalla \"Cámara\", que tiene: - Botón de flecha \"Atrás\" - Nombre del grupo de espacio Promo elegido como título - Icono \"?\" - Botón circular \"Tomar foto\"");
    	} catch (Exception e) {
            e.printStackTrace();
            throw new Exception(
                "Error al tomar múltiples fotografías en la funcionalidad 'Toma de fotografía'", e
            );
        }
    };
	    	
    /**
     * Metodo para hacer click en el icono 'Flecha' 'Siguiente' dentro de la funcionalidad Toma de Fotografia
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
     */
    public void clickBtnFlechaSiguiente() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnFlechaSiguiente)).click();
 	        testCase.addScreenShotCurrentStep(au, "");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en btnContinuarEvaluacion", e);
	    }
    }
    /**
     * Metodo para hacer click en el icono 'Flecha' 'Siguiente' dentro de la funcionalidad Toma de Fotografia
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
     */
    public void clickBtnFlechaSiguienteSinEv() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnFlechaSiguiente)).click();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en btnContinuarEvaluacion", e);
	    }
    }
    /**
     * Valida la existencia de al menos una fotografía en la galería
     * 
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 22/Diciembre/2025
     */
    public void validarExistenciaDeUnaFotografiaEnGaleria() throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.visibilityOfElementLocated(primerFotografia));
            testCase.addTextEvidenceCurrentStep("Se muestra la \"Galería de fotos\" sin conexión, que tiene: "
            		+ "- Botón de flecha \"Atrás\" - Nombre del grupo del espacio promocional como título - \"?\" Ícono "
            		+ "- Mensaje \"Estás trabajando en modo sin conexión\" - Lista de fotos tomadas - Botón \"Guardar y Continuar\" "
            		+ "- \"Podrás finalizar el espacio promocional una vez que el dispositivo vuelva a tener conexión\". texto");
            testCase.addScreenShotCurrentStep(au, "Fotografia encontrada en galería");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw new Exception("Error al validar la existencia de una fotografía en la galería", e);
        }
    }
    
    /**
     * Metodo para validar que no existan fotografias dentro de la vista 'Galeria de fotos'
     * 
     * @author Mario A. De León Rdz.
     * @return void
     * @throws Exception
     * @date 22/Diciembre/2025
     */
    public void validarNoExistenciaDeFotografiasEnGaleria() throws Exception {
        try {
            boolean notExistElement = au.driver.findElements(fotografias).isEmpty();

            if (notExistElement) {
                testCase.addScreenShotCurrentStep(au, "Galería sin fotografías");
            } else {
                throw new Exception("Se encontraron fotografías dentro de la vista 'Galeria de Fotos'");
            }
        } catch (Exception e) {
            e.printStackTrace();
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            testCase.addScreenShotCurrentStep(au, "Error validando galería de fotos");
            throw e;
        }
    }
    
    /**
    * Metodo para hacer click en el boton 'Continuar' en la vista "Confirmacion de Fotografia"]
    * 
    * @author Mario A. De León Rdz.
    * @return void
    * @throws Exception
    * @date 22/Diciembre/2025
    */
   public void clickBtnContinuar() throws Exception {
       try {
           WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(120));
           wait.until(ExpectedConditions.elementToBeClickable(btnContinuar)).click();

           testCase.addScreenShotCurrentStep(au, "Click en botón Continuar");
       } catch (Exception e) {
           e.printStackTrace();
           testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
           throw new Exception("Error al dar clic en el botón Continuar", e);
       }
   }
   
   /**
   * Metodo para hacer click en el boton 'Continuar' en la vista "Confirmacion de Fotografia"]
   * 
   * @author Mario A. De León Rdz.
   * @return void
   * @throws Exception
   * @date 22/Diciembre/2025
   */
  public void clickBtnContinuarSinEv() throws Exception {
      try {
          WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
          wait.until(ExpectedConditions.elementToBeClickable(btnContinuar)).click();
      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          throw new Exception("Error al dar clic en el botón Continuar", e);
      }
  }
    
    /**
     * Metodo para hacer click en el boton 'Finalizar' dentro de la vista 'Folio de produccion'
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
     */
    public void clickBtnFinalizar() throws Exception {
		    try {
		        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(120));
		        wait.until(ExpectedConditions.elementToBeClickable(btnFinalizar)).click();
		    	testCase.addScreenShotCurrentStep(au, "testImage");
		    } catch (Exception e) {
		    	e.printStackTrace();
		        throw new Exception("Error al dar clic en boton Finalizar)", e);
		    }	        
    }
    
    /**
     * Metodo para hacer click en el boton 'Finalizar' dentro de la vista 'Folio de produccion'
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
     */
    public void clickBtnFinalizarSinEv() throws Exception {
		    try {
		        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
		        wait.until(ExpectedConditions.elementToBeClickable(btnFinalizar)).click();;
		    } catch (Exception e) {
		    	e.printStackTrace();
		        throw new Exception("Error al dar clic en boton Finalizar)", e);
		    }	        
    }
    
	/**
	 * Realiza clic al botón continuar evaluacion
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickBtnContinuarEvaluacion() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnContinuarEvaluacion)).click();

	        testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en btnContinuarEvaluacion", e);
	    }
	}
	
	/**
	 * Realiza clic al botón continuar evaluacion
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickBtnGuardarYContinuar() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnGuardarYContinuar)).click();
	        testCase.addScreenShotCurrentStep(au, "Pantalla Inicial");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en btnContinuarEvaluacion", e);
	    }
	}
	
	/**
	 * Realiza clic al botón continuar evaluacion
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickBtnGuardarYContinuarSinEv() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnGuardarYContinuar)).click();
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en btnContinuarEvaluacion", e);
	    }
	}
	
	/**
	 * Valida que se muestre la etiqueta verde "Guardado en modo sin conexión" para el grupo de espacios Promo
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarEtiquetaGuardadoEnModoSinConexion() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(tagGuardadoEnModoSinConexion));
	        testCase.addScreenShotCurrentStep(au, "");
	        testCase.addTextEvidenceCurrentStep(" Se abre la pantalla \"Evaluación MEP\" y se muestra la etiqueta verde \"Guardado en modo sin conexión\" para el grupo de espacios Promo");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("No se mostró la notificacion 'Tu conexión ha sido restablecida'", e);
		}
	};
	
	/**
	 * Valida que se muestre el modal con el titulo "¿Está seguro que desea cancelar?" 
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarEstaSeguroQueDeseaCancelar() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(textModalEstaSeguroQueDeseaCancelar));
	        testCase.addScreenShotCurrentStep(au, "");
	        testCase.addTextEvidenceCurrentStep("Se abre el modal, el cual tiene: - \"¿Está seguro que desea cancelar?\" título - \"Se perderá todo el progreso de los artículos agregados a la lista.\" texto - Botón \"Cancelar\" - Botón \"Sí, continuar\"");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("No se mostró la notificacion 'Tu conexión ha sido restablecida'", e);
		}
	};
	
	/**
	 * Realiza clic al botón 'Si, continuar' del modal con el titulo "¿Está seguro que desea cancelar?"
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickBtnSiContinuar() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnSiContinuar)).click();

	        testCase.addScreenShotCurrentStep(au, "");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en btnContinuarEvaluacion", e);
	    }
	}
	
	/**
	 * Realiza clic al botón 'Cancelar' del modal con el titulo "¿Está seguro que desea cancelar?"
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickBtnCancelarDeseaCancelar() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnSiCancelarModalDeseaCancelar)).click();

	        testCase.addScreenShotCurrentStep(au, "");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en btnContinuarEvaluacion", e);
	    }
	}
	
	/**
	 * Valida que se muestre el modal de actualización al restablecer la conectividad.
	 * Se activa la internet y posteriormente se abre una ventana activa de validación
	 * para capturar el modal, el cual es transitorio.
	 *
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test si el modal no es detectado.
	 * @date 22/Diciembre/2025
	 */
	public void validarModalActualizando() throws Exception {
	    try {
	        long startTime = System.currentTimeMillis();
	        long endTime = startTime + 30000; // 30 segundos activos
	        boolean modalDetectado = false;

	        // Thread que DISPARA el evento (NO lo hagas fuera del método)
	        Thread triggerInternet = new Thread(() -> {
	            try {
	                Thread.sleep(1000); // asegura que el loop ya esté activo
	                utilOffline.setConectividadSimpleWifi(true);
	            } catch (Exception ignored) {}
	        });
	        triggerInternet.start();

	        // VALIDACIÓN ACTIVA DEL MODAL
	        while (System.currentTimeMillis() < endTime) {

	            testCase.addScreenShotCurrentStep(au, "");

	            boolean modalVisible = au.driver
	                    .findElements(textModalActualizando)
	                    .stream()
	                    .anyMatch(WebElement::isDisplayed);

	            if (modalVisible) {
	                modalDetectado = true;
	                testCase.addTextEvidenceCurrentStep(
	                        " - Se detecta el modal \"Actualizando… por favor, espera un momento.\""
	                );
	                break;
	            }

	            Thread.sleep(150);
	        }

	        triggerInternet.join();

	        if (!modalDetectado) {
	            throw new Exception(
	                    "No se detectó el modal \"Actualizando…\" después de 30 segundos"
	            );
	        }

	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw e;
	    }
	}




	/**
	 * Valida que se muestre el modal con el titulo "¿Está seguro que desea cancelar?" 
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarModalFalloSincronizacion() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.visibilityOfElementLocated(textModalFalloLaSincronizacion));
	        testCase.addScreenShotCurrentStep(au, "");
	        testCase.addTextEvidenceCurrentStep("Se muestra el modal de error \"Falló la sincronización\", el cual tiene: \n"
	        		+ "- Naranja \"!\" icono - \"Falló la sincronización\" título - \"No se puede sincronizar la información. Por favor, intente finalizar de nuevo desde los espacios promocionales.\" texto - Botón \"Cerrar\" - Botón \"Reintentar\"");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("No se mostró la notificacion 'Falló la sincronización'", e);
		}
	};
	
	/**
	 * Realiza clic al botón 'Reintentar' del modal con el titulo "Falló la sincronización"
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickBtnReintentarModalFalloSincronizacion() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnReintentarModalFalloLaSincronizacion)).click();

	        testCase.addScreenShotCurrentStep(au, "");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en btnContinuarEvaluacion", e);
	    }
	}
	
	/**
	 * Realiza clic al botón 'Reintentar' del modal con el titulo "Falló la sincronización"
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickBtnCerrarModalFalloSincronizacion() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnCerrarModalFalloLaSincronizacion)).click();

	        testCase.addScreenShotCurrentStep(au, "");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en btnContinuarEvaluacion", e);
	    }
	}
	
	/**
	 * Valida que se muestre el modal con el titulo "¿Está seguro que desea cancelar?" 
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarModalReintentandoSincronizacion() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(textModalReintentandoLaSincronizacion));
	        testCase.addScreenShotCurrentStep(au, "");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("No se mostró la notificacion 'Falló la sincronización'", e);
		}
	};
	/**
	 * Valida que se muestre el mensaje  "La conexión a internet se ha restablecido"
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarConexionSeHaRestablecido() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(textModalConexionAInternetRestablecido));
			testCase.addScreenShotCurrentStep(au, "");
			testCase.addTextEvidenceCurrentStep("Se muestra el modal \"La conexión a internet se ha restablecido\", el cual tiene:\n - Internet con ícono de marca de verificación verde - Título \"La conexión a internet se ha restablecido\" - \"Puedes seguir trabajando en modo sin conexión o ir a “Iniciar sesión” para continuar en línea y sincronizar tu progreso\". texto - Botón \"Continuar en Modo sin conexión\" - Botón \"Ir a Iniciar Sesión\"");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("No se mostró la notificacion 'Tu conexión ha sido restablecida'", e);
		}
	};
	/**
	 * Realiza clic al botón 'Reintentar' del modal con el titulo "Falló la sincronización"
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickBtnContinuarEnModoSinConexion() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnContinuarEnModoSinConexion)).click();
	        Thread.sleep(1000);
	        testCase.addScreenShotCurrentStep(au, "");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en btnContinuarEvaluacion", e);
	    }
	}
	/**
	 * Valida que se muestre el mensaje el modal "Limite máximo de fotografías alcanzadas (50). Favor de conectarse a la red antes de continuar."
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarMensajeLimiteMaximoFotografias() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(textModalLimiteMaximoFotografias));
			testCase.addScreenShotCurrentStep(au, "");
			testCase.addTextEvidenceCurrentStep("Se muestra el mensaje \"Limite máximo de fotografías alcanzadas (50). Favor de conectarse a la red antes de continuar.\" Se debe mostrar un mensaje de brindis rojo: el botón \"Tomar foto\" debe estar atenuado.");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("No se mostró la notificacion 'Tu conexión ha sido restablecida'", e);
		}
	};
	
	/**
	 * Valida que se muestre el mensaje  "La conexión a internet se ha restablecido"
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarMensajeConexionRestablecida() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(textMsgConexionRestablecida));
			testCase.addTextEvidenceCurrentStep("Se muestra el mensaje de brindis verde que indica que Internet ha vuelto. - La pantalla \"Galería de fotos\" sin conexión no debe cambiarse a en línea.");
			testCase.addScreenShotCurrentStep(au, "");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("No se mostró la notificacion 'Tu conexión ha sido restablecida'", e);
		}
	};
	
	/**
	 * Valida que se muestre el mensaje de brindis verde que indica "Progreso guardado exitosamente"
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El metodo falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarMensajeProgresoGuardadoExitosamente() throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(textMsgProgresoGuardadoExitosamente));
			testCase.addTextEvidenceCurrentStep("Se muestra el mensaje de brindis verde que indica \"Progreso guardado exitosamente\"");
			testCase.addScreenShotCurrentStep(au, "");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
    		e.printStackTrace();
	        throw new Exception("No se mostró la notificacion 'Tu conexión ha sido restablecida'", e);
		}
	};
	
	/**
	 * Valida que se muestre el icono "Tomar Foto" para confirmar que el usuario
	 * se encuentra en la vista "Galería de Fotos"
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception El método falla el test en caso de que no se encuentre el elemento esperado.
	 * @date 22/Diciembre/2025
	 */
	public void validarVistaGaleria() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(btnTomarFotoGaleriaFotos));

	        testCase.addTextEvidenceCurrentStep("Se muestra el icono 'Tomar Foto', el usuario se encuentra en la vista 'Galería de Fotos'");
	        testCase.addScreenShotCurrentStep(au, "Vista Galería de Fotos");
	    } catch (Exception e) {
	        e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("No se mostró el icono 'Tomar Foto' en la vista 'Galería de Fotos'", e);
	    }
	}
	
	/**
	 * Realiza clic al botón 'Reintentar' del modal con el titulo "Falló la sincronización"
	 * 
	 * @author Mario A. De León Rdz.
	 * @return void
	 * @throws Exception 
	 * @date 22/Diciembre/2025
	 */
	public void clickTomarFotoFoliosDeProduccion() throws Exception {
	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.elementToBeClickable(btnTomarFotoFoliosDeProduccion)).click();
	        Thread.sleep(500);
	        testCase.addScreenShotCurrentStep(au, "");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al dar clic en btnContinuarEvaluacion", e);
	    }
	}

	/**
	 * Obtiene y almacena en memoria los grupos promocionales visibles en la evaluación
	 * 
	 * @author Mario A. De León Rdz.
	 * @return List<String> Lista de grupos promocionales encontrados
	 * @throws Exception
	 * @date 22/Diciembre/2025
	 */
	public List<String> guardarGruposPromocionales() throws Exception {
	    List<String> grupos = new ArrayList<>();

	    try {
	        WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
	                By.xpath("//android.widget.TextView[@text and string-length(@text) > 0]")
	        ));

	        List<WebElement> elementos = au.driver.findElements(
	                By.xpath("//android.widget.TextView[@text and string-length(@text) > 0]")
	        );

	        System.out.println("Grupos promocionales encontrados:");

	        for (WebElement elemento : elementos) {
	            String nombreGrupo = elemento.getText().trim();

	            if (!nombreGrupo.isEmpty() && !grupos.contains(nombreGrupo)) {
	                grupos.add(nombreGrupo);
	                System.out.println(" - " + nombreGrupo);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error al obtener los grupos promocionales", e);
	    }

	    return grupos;
	}

	
	/**
	 * Valida que los grupos promocionales de una sesión previa sigan presentes
	 * después de cerrar y abrir la aplicación
	 * 
	 * @author Mario A. De León Rdz.
	 * @param gruposEsperados Lista de grupos promocionales guardados previamente
	 * @return void
	 * @throws Exception
	 * @date 22/Diciembre/2025
	 */
	public void validarGruposPromocionalesDeSesionPrevia(List<String> gruposEsperados) throws Exception {

	    try {
	        List<String> gruposActuales = guardarGruposPromocionales();

	        System.out.println("Validando grupos promocionales de sesión previa:");
	        System.out.println("Grupos actuales: " + gruposActuales);

	        testCase.addTextEvidenceCurrentStep("La lista de grupos del Espacio Promocional es la misma que la del último ingreso en línea a la pantalla 'Evaluación MEP'");
	        testCase.addScreenShotCurrentStep(au, "Grupos Promocionales - Nueva Sesión");

	        for (String grupoEsperado : gruposEsperados) {
	            Assert.assertTrue(
	                    gruposActuales.contains(grupoEsperado),
	                    "No se encontró el grupo promocional: " + grupoEsperado
	            );
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error al validar los grupos promocionales de la sesión previa", e);
	    }
	}



}