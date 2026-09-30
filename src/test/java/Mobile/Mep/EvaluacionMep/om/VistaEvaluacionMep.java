package Mobile.Mep.EvaluacionMep.om;

import modelo.BaseExecution;
import modelo.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Mobile.Mep.PromOxxoOffline.om.VistaEvaluacionMEPOffline;
import io.appium.java_client.AppiumBy;
import utils.AppiumUtil;

import java.time.Duration;
import java.util.List;

/**
 * DESCRIPCIÓN: Page Object Model que representa la vista de Evaluación MEP (online).
 * Contiene los localizadores y métodos de interacción/validación para el flujo completo
 * de evaluación: vista principal, búsqueda, filtros, toma de fotografías, galería de fotos,
 * folio de producción, modales de error, resultado de tienda y estados de POPs.
 *
 * NOMBRE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 *   - Casos de prueba del módulo Evaluación MEP (online).
 *
 * PREREQUISITOS:
 *   - El usuario ha iniciado sesión correctamente.
 *   - El usuario está en la pantalla "Panel de Control".
 *   - El dispositivo tiene conexión a internet activa.
 *
 * INSUMOS:
 *   - N/A
 *
 * @author Ivan Yakab
 * @date 03/Noviembre/2025
 */
public class VistaEvaluacionMep {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    private VistaEvaluacionMEPOffline vistaEvaluacionMepOffline;
    
    
	/*
	 * Localizadores de la vista Evaluacion MEP
	 ******************************************************************************************/
    private By titleEvaluacionMep = By.xpath("//android.widget.TextView[@text=\"Evaluación MEP\"]");
    private By zonaDeTiendaText = By.xpath("//android.widget.TextView[@text=\"Zona de Tienda:\"]");
    private By btnFlechaAtras = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[1]");

    private By iconoBorrarBarraBusqueda = By.xpath("//android.view.ViewGroup[@resource-id=\"input-right-icon-button\"]");
    private By btnBusqueda = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[2]");
    private By barraBusquedaActiva = By.xpath("//android.widget.EditText");
    private By textCancelar = By.xpath("//android.widget.TextView[@text=\"Cancelar\"]");
    private By etiquetaCompleto = By.xpath("//android.widget.TextView[@text=\"Completo\"]");
    
    /*
	 * Localizadores del modal de Error 'Informacion del periodo no disponible'
	 ******************************************************************************************/
    
    private By modalErrorContainer = By.xpath("//*[@resource-id=\"modal-container\"]");
    private By modalErrorTitle = By.xpath("//android.widget.TextView[@text=\"Información del periodo no disponible\"]");
    private By modalErrorText = By.xpath("//android.widget.TextView[contains(@text,\"promocional.\")]");
    private By btnReintentar = By.xpath("//android.view.ViewGroup[@content-desc=\"Reintentar\"]");
    private By btnTextReintentar = By.xpath("//android.widget.TextView[@text=\"Reintentar\"]");
    
    private By btnCancelar = By.xpath("//android.view.ViewGroup[@content-desc=\"Cancelar\"]");
    private By btnTextCancelar = By.xpath("//android.widget.TextView[@text=\"Cancelar\"]");
    
    /*
	 * Localizadores Evaluacion MEP cuando existen POPs
	 ******************************************************************************************/
    
    private By btnTomarFoto = By.xpath("//android.view.ViewGroup[@content-desc=\"Tomar Foto(s)\"]");
    private By btnTomarFotoPrimerPop = By.xpath("(//android.view.ViewGroup[@content-desc=\"Tomar Foto(s)\"])[1]");
    private By titleEspacioPromocional = By.xpath("//android.widget.TextView[@text=\"Cabeceras\"]");
    private By textCodigosValidados = By.xpath("//android.widget.TextView[@text=\"Códigos validados:   \"]");
    private By textCodigosCompletados0De1 = By.xpath("//android.widget.TextView[@text=\"0/1\"]");
    private By textCodigosCompletados0De1PrimerPop = By.xpath("(//android.widget.TextView[@text=\"0/1\"])[1]");
    private By iconExpand1Record = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[6]");
    private By codigosValidados1de1 = By.xpath("//android.widget.TextView[@text=\"1/1\"]");
    private By codigosValidados2de2 = By.xpath("//android.widget.TextView[@text=\"2/2\"]");
    private By codigosValidados1de2 = By.xpath("//android.widget.TextView[@text=\"1/2\"]");
    private By codigosValidados2de3 = By.xpath("//android.widget.TextView[@text=\"2/3\"]");

    private By btnFiltro = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[5]");
    private By espacioCabecera = By.xpath("//android.widget.TextView[@text=\"Cabecera 1\"]");
    
    private By titleFiltroPor = By.xpath("//android.widget.TextView[@text=\"Filtro por:\"]");
    
    private By btnFiltroTodos = By.xpath("//android.view.ViewGroup[@content-desc=\"Todos\"]");
    private By textFiltroTodos = By.xpath("//android.widget.TextView[@text=\"Todos\"]");
    
    private By btnFiltroCompletado = By.xpath("//android.view.ViewGroup[@content-desc=\"Completado\"]");
    private By textFiltroCompletado = By.xpath("//android.widget.TextView[@text=\"Completado\"]");
    
    private By btnFiltroIncompletos = By.xpath("//android.view.ViewGroup[@content-desc=\"Incompletos\"]");
    private By textFiltroIncompletos = By.xpath("//android.widget.TextView[@text=\"Incompletos\"]");
  
    private By popsStatusIncompleto = By.xpath("//android.widget.TextView[@text='Tomar Foto(s)']");
    private By popsStatusCompleto = By.xpath("//android.widget.TextView[@text='Completo']");

    /*
	 * Localizadores Filtro activado - Mensaje de Error 
	 ******************************************************************************************/
    private By titleSinResultados = By.xpath("//android.widget.TextView[@text=\"Sin resultados.\"]");
    private By textMensajeError = By.xpath("//android.widget.TextView[@text=\"No hay espacios promocionales que coincidan con el filtro seleccionado.\"]");
    
    /*
	 * Localizadores Fotografia 
	 ******************************************************************************************/
    private final By btnCirculoTomarFotografia = AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(24)");

    private By titleCabeceras = By.xpath("//android.widget.TextView[@text=\"Cabeceras\"]");
    private By fotografias = By.xpath("//android.widget.ImageView");
    private By primerFotografia = By.xpath("(//android.widget.ImageView)[1]");

    private By btnFlechaSiguiente = By.xpath("(//android.view.ViewGroup)[last()]");
    private By btnAndroidCameraAlert = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]");
    private By unaFotografia = By.xpath("//android.widget.TextView[@text=\"1\"]");
    
    /*
	 * Localizadores Fotografia - Guia Modal
	 ******************************************************************************************/
    
    private By btnGuia = By.xpath("(//android.view.ViewGroup)[last() - 4]");
    private By btnGuiaGaleriaFotos = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[last() - 2]");

    private By titleGuia = By.xpath("//android.widget.TextView[@text=\"Guía para toma de fotografías\"]");
    private By pointOne = By.xpath("//android.widget.TextView[@text=\"Buena iluminación: Evita sombras y reflejos sobre el código Aruco.\"]");
    private By pointTwo = By.xpath("//android.widget.TextView[@text=\"Estabilidad: Sujeta bien el teléfono para evitar imágenes borrosas.\"]");
    private By pointThree = By.xpath("//android.widget.TextView[@text=\"Encuadre correcto: Asegúrate de que el código Aruco y la promoción sean completamente visibles.\"]");
    private By pointFour = By.xpath("//android.widget.TextView[@text=\"Sin obstrucciones: No tapes el código ni la promoción con objetos o manos.\"]");
    private By pointFive = By.xpath("//android.widget.TextView[@text=\"Revisión rápida: Antes de capturar, verifica que la imagen sea clara y legible.\"]");
    private By btnSubmit = By.xpath("//android.view.ViewGroup[@content-desc=\"Cerrar\"]");

    /*
	 * Localizadores Galeria de Fotos 
	 ******************************************************************************************/
    private By revision1Fotografia = By.xpath("//android.widget.TextView[@text=\"1 foto:\"]");
    private By revision3Fotografias = By.xpath("//android.widget.TextView[@text=\"3 fotos:\"]");
    private By btnContinuar = By.xpath("//android.view.ViewGroup[@content-desc=\"Continuar\"]");
    private By btnRetomarFotografiaUnaFoto = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[last() - 1]");	
    private By btnEliminarFotografiaUnaFoto = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[last()]");	
    private By btnTomarFotoGaleriaFotos = By.xpath("//android.view.ViewGroup[@content-desc=\"Tomar foto\"]");
    private By alertaTitle = By.xpath("//android.widget.TextView[@text=\"La foto ha sido eliminada.\"]");
    private By textDeshacer = By.xpath("//android.widget.TextView[@text=\"Deshacer\"]");
    private By btnCerrarMensajeAlerta = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[4]");
    
    /*
	 * Localizadores Folio de Produccion
	 ******************************************************************************************/

    private By titleFolioDeProduccion = By.xpath("//android.widget.TextView[contains(@text, 'Folio de Produ')]");
    private By primerfolioDeProduccionCompleto = By.xpath("(//android.widget.TextView[@text=\"Completo\"])[1]");
    private By primerfolioDeProduccionCompletoLabelFolio160 = By.xpath("//android.view.ViewGroup[contains(@content-desc,'Label name') and contains(@content-desc,'P8-TEST-1')]");
    private By allBtnTomarFoto = By.xpath("//android.widget.TextView[@text=\"Tomar Foto\"]");

    private By btnFinalizar = By.xpath("//android.view.ViewGroup[@content-desc=\"Finalizar\"]");
    private By btnTomarFotoGeneral = By.xpath("//android.widget.TextView[@text=\"Tomar Foto General\"]");
    private By btnGuardarAvance = By.xpath("//android.view.ViewGroup[@content-desc=\"Guardar avance\"]");
    private By btnBuscar = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[last() - 1]");
    private By btnBuscarActivadoText = By.xpath("//android.widget.EditText");
    private By retornarVistaAnterior = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[1]");
    private By etiquetaEnCurso = By.xpath("//android.widget.TextView[@text=\"En curso\"]");
    
    /*
	 * Localizadores Folio de Produccion - Mensaje de error No se identificaron algunos o todos los codigos
	 ******************************************************************************************/
    private final By textModalErrorFaltanCodigos = AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Faltan códigos\")");
    private By titleFolioMensajeDeErrorDosPops = By.xpath("//android.widget.TextView[@text=\"Faltan códigos por verificar en esta zona.\"]");
    private By textFolioMensajeDeErrorDosPops = By.xpath("//android.widget.TextView[@text=\"Asegúrate de tomar otra foto del material o finaliza la Zona si no tienes el POP.\"]");

    /*
	 * Localizadores Folio de Produccion - Mensaje de error cuando buscamos un Grupo y no se encuentra
	 ******************************************************************************************/
    private By ceroResults = By.xpath("//android.widget.TextView[@text=\"0 results\"]");
    private By titleMensajeErrorBusquedaInvalida = By.xpath("//android.widget.TextView[@text=\"No se encontraron coincidencias\"]");
    private By textMensajeErrorBusquedaInvalida = By.xpath("//android.widget.TextView[contains(@text,\" intente de nuevo escribiendo el nombre del Espacio Promocional.\")]");
    
    /*
	 * Localizadores Mensaje Exitoso - Identificacion de codigos fue exitosa
	 ******************************************************************************************/
    private By titleIdentificacionCodigos = By.xpath("//android.widget.TextView[@text=\"La identificación de códigos fue exitosa\"]");
    private By btnCerrar = By.xpath("//android.view.ViewGroup[@content-desc=\"Cerrar\"]");
    
    /*
	 * Localizadores Mensaje Alerta - La validacion de codigos está incompleta
	 ******************************************************************************************/
   
    private By titleValidacionCodigosIncompleta = By.xpath("//android.widget.TextView[contains(@text,\"La validaci\")]");
    private By textValidacionCodigosIncompletaAfterBtnFinalizar = By.xpath("//android.widget.TextView[contains(@text,\"Si da click en\")]");
    
    /*
	 * Localizadores Evaluacion MEP - Resultado Tienda
	 ******************************************************************************************/
    private By textResultadoTienda = By.xpath("//android.widget.TextView[@text=\"Resultado tienda:\"]");
    private By textCeroPercentage = By.xpath("//android.widget.TextView[@text=\"0%\"]");
    private By btnHelpResultadoTienda = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[6]");
    private By btnVerDetalle = By.xpath("//android.view.ViewGroup[@content-desc=\"Ver detalle\"]");
    private By codigosDetectadosCeroPercentage = By.xpath("//android.widget.TextView[@text=\"Códigos detectados 0% (0)\"]");
    private By materialesConIncidenciaCeroPercentage = By.xpath("//android.widget.TextView[@text=\"Materiales con incidencia 0% (0)\"]");
    private By sinEvaluarOneHundredPercentageOnePop = By .xpath("//android.widget.TextView[@text=\"Sin evaluar 100% (1)\"]");
    
    /*
	 * Localizadores Evaluacion MEP - Resultado Tienda - 4 Pops
	 ******************************************************************************************/
    private By codigosDetectados25Percentage = By.xpath("//android.widget.TextView[@text=\"Códigos detectados 25% (1)\"]");
    private By materialesConIncidencia25Percentage = By.xpath("//android.widget.TextView[@text=\"Materiales con incidencia 25% (1)\"]");
    private By sinEvaluar50Percentage = By .xpath("//android.widget.TextView[@text=\"Sin evaluar 50% (2)\"]");
    private By score4Records = By.xpath("//android.widget.TextView[@text=\"25%\"]");
    
    private By ultimoCodigoValidado = By.xpath("(//android.widget.TextView[@text=\"Códigos validados:   \"])[4]");
    
    /*
	 * Localizadores Barra de Busqueda - Vista Folio de Prod
	 ******************************************************************************************/
    private By btnCerrarBarraBusquedaFolioProd = By.xpath("//android.view.ViewGroup[@resource-id=\"input-right-icon-button\"]");
    private By btnCancelarBarraBusquedaFolioProd = By.xpath("//android.widget.TextView[@text=\"Cancelar\"]");

    /*
	 * Localizadores Mensaje de Error al Buscar un producto y no se encuentra
	 ******************************************************************************************/
    private By titleNoCoincidencias = By.xpath("//android.widget.TextView[@text=\"No se encontraron coincidencias\"]");
    
    /*
	 * Localizadores Folio de Produccion - Sin conexion - Mensaje de Alerta
	 ******************************************************************************************/
    private By titleErrorAlEnviarFotos = By.xpath("//android.widget.TextView[@text=\"Error al enviar las fotos.\"]");
    private By textErrorAlEnviarFotos = By.xpath("//android.widget.TextView[contains(@text,\"Verifica tu conexi\")]");
    
    /*
	 * Localizadores Evaluacion MEP - Reported As Damaged
	 ******************************************************************************************/
    private By btnContinuarEvaluacionFirst = By.xpath("(//android.widget.TextView[contains(@text,\"Continuar evaluación\")])[1]");
    private By btnContinuarEvaluacion = By.xpath("//android.widget.TextView[@text=\"Continuar evaluación\"]");
    private By statusReportadoComoFaltante = By.xpath("//android.widget.TextView[@text=\"Reportado como Faltante\"]");
    
    /*
	 * Localizadores Mensaje Alerta - ¿Quieres guardar este proceso?
	 ******************************************************************************************/
    private By titleQuieresGuardarEsteProceso = By.xpath("//android.widget.TextView[@text=\"¿Quieres guardar este proceso?\"]");
    private By textTodosLosCambiosRealizados = By.xpath("//android.widget.TextView[contains(@text,\"Todos los cambios realizados ser\")]");
    
    /*
	 * Localizadores Mensaje Alerta - Tienes un espacio promocional pendiente a finalizar, ¿deseas continuar validando estos materiales? - Vista EMEP
	 ******************************************************************************************/
    private By btnIgnorar = By.xpath("//android.view.ViewGroup[@content-desc=\"Ignorar\"]");
    private By textContinuarValidandoMateriales = By.xpath("//android.widget.TextView[contains(@text,\"Tienes un espacio promocional pendiente a finalizar, ¿deseas continuar validando estos materiales?\")]");
    private By titleContinuarValidandoMateriales = By.xpath("//android.widget.TextView[contains(@text,\"incompleta\")]");
    private By popUpProgresoGuardadoExitosamente = By.xpath("//android.widget.TextView[@text=\"Progreso guardado exitosamente.\"]");
    
	/**
	 * Constructor de la clase Vista Evaluacion MEP
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaEvaluacionMep(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    /**
   	 * Espera a que un elemento se muestre dentro de un periodo de tiempo
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
      	 * Espera a que un elemento no se muestre dentro del DOM dentro de un periodo de tiempo
      	 * @author Ivan Yakab
      	 * @param locator Identificador del elemento
      	 * @param timeout Tiempo maximo de espera
      	 * @date 11/11/2025
      	 */
          public void waitForInvisibility(By locator, int timeout) {
      		WebDriverWait wait = new WebDriverWait(au.driver, Duration.ofSeconds(timeout));
      		try {
      			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
      		} catch (Exception ex) {
      			throw new RuntimeException("El elemento si se encuentra dentro del DOM, no desapareció dentro del tiempo esperado: ", ex);
      		}
      	}
    
    /**
     * Validar que se muestre la vista de 'Evaluacion MEP'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarVistaEvaluacionMep() throws InterruptedException {
     try {
        	waitForVisibility(titleEvaluacionMep, 15);
    	    au.driver.findElement(titleEvaluacionMep).isDisplayed();

    	    waitForVisibility(zonaDeTiendaText, 30);
    	    au.driver.findElement(zonaDeTiendaText).isDisplayed();

     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarVistaEvaluacionMep", e);
     }
    }
    

    /**
     * Metodo para hacer click en el icono 'Busqueda' 'Lupa' dentro de la vista 'Evaluacion MEP'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnBuscarEvaluacionMep()
    {
     try {
        	waitForVisibility(btnBusqueda, 30);
        	au.driver.findElement(btnBusqueda).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnBuscarEvaluacionMep", e);
     }
    }
    

    /**
     * Metodo para validar que despues de hacer click en el boton 'Buscar', aparezca el boton cancelar y una barra de busqueda
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarBarraDeBusquedaActiva() throws InterruptedException {
     try {
        	waitForVisibility(barraBusquedaActiva, 30);
    	    	au.driver.findElement(barraBusquedaActiva).isDisplayed();
    	    waitForVisibility(textCancelar, 30);
    	    	au.driver.findElement(textCancelar).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarBarraDeBusquedaActiva", e);
     }
    }
    
    /**
     * Metodo para validar que aparezca un mensaje de alerta cuando buscamos un producto y no se encuentra
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarMensajeAlertaNoCoincidencias() throws InterruptedException {
     try {
        	waitForVisibility(titleNoCoincidencias, 30);
    	    	au.driver.findElement(titleNoCoincidencias).isDisplayed();
    	    	waitForVisibility(titleNoCoincidencias, 30);
    	    	au.driver.findElement(titleNoCoincidencias).isDisplayed();
    	    	waitForVisibility(titleNoCoincidencias, 30);
    	    	au.driver.findElement(titleNoCoincidencias).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarMensajeAlertaNoCoincidencias", e);
     }
    }
    
    /**
     * Metodo para validar que al hacer click en la 'X' dentro de la barra de busqueda desaparezca el texto que previamente ingresamos
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickYValidarBorradoBarraBusqueda() throws InterruptedException {
     try {
        	waitForVisibility(iconoBorrarBarraBusqueda, 30);
    	    	au.driver.findElement(iconoBorrarBarraBusqueda).isDisplayed();
    	    	au.driver.findElement(iconoBorrarBarraBusqueda).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickYValidarBorradoBarraBusqueda", e);
     }
    }
    
    /**
     * Metodo para validar que al hacer click en la palabra 'Cancelar' de lado de la barra de busqueda
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickYValidarCanceladoBarraBusqueda() throws InterruptedException {
     try {
        	waitForVisibility(textCancelar, 30);
    	    	au.driver.findElement(textCancelar).isDisplayed();
    	    	au.driver.findElement(textCancelar).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickYValidarCanceladoBarraBusqueda", e);
     }
    }
    
    
    /**
     * Metodo para buscar un valor dentro de un grupo de espacio promocional
	 * @author Ivan Yakab
	 * @param nombreGrupoEspacioPromocional - Es el grupo que buscaremos dentro de la barra de busqueda activa
	 * @date 03/11/2025
	 */
    public void buscarGrupoEspacioPromocional(String nombreGrupoEspacioPromocional) throws InterruptedException {
     try {
    	    waitForVisibility(barraBusquedaActiva, 30);	
        	au.driver.findElement(barraBusquedaActiva).isDisplayed();
    	    	au.driver.findElement(barraBusquedaActiva).sendKeys(nombreGrupoEspacioPromocional);
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en buscarGrupoEspacioPromocional", e);
     }
    }
    
    /**
     * Metodo para validar que exista un POP que coincida con la busqueda de un Grupo de Espacio Promocional
	 * @author Ivan Yakab
	 * @param NA
     * @return true or false
	 * @date 03/11/2025
	 */
    public boolean existenciaBusquedaCompleta(String valorABuscar) {
     try {

    	    List<WebElement> elementos = retornarElementosQueCoincidanConBusqueda(valorABuscar);

    	    int cantElementos = elementos.size();
    	    String cantidadStr = Integer.toString(cantElementos);

    	    if (cantElementos > 0)
    	    {
    	    	testCase.addTextEvidenceCurrentStep("Se han encontrado " + cantidadStr + " elementos con valor " + valorABuscar);
    	    	return true;
    	    }
    	    else
    	    {
    	    	testCase.addTextEvidenceCurrentStep("Se han encontrado " + cantidadStr + " elementos con valor " + valorABuscar + ". Prueba fallida, no se encontraron elementos.");
    	    	return false;
    	    }	    
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
	 }
    

    /**
     * Metodo para validar cuantos elementos coincidan cuando buscamos un grupo de espacio promocional dentro de la barra de busqueda
	 * @author Ivan Yakab
     * @param nombreGrupoEspacioPromocional Es el nombre del Grupo Espacio Promocional
	 * @date 03/11/2025
	 * @return retorna una lista de WebElement que coincidan con el parametro y selector buscado
	 */
    private List<WebElement> retornarElementosQueCoincidanConBusqueda(String nombreGrupoEspacioPromocional)
    {
    	return au.driver.findElements(By.xpath("//android.widget.TextView[@text=\"" + nombreGrupoEspacioPromocional + "\"]"));
    }
    

    /**
     * Metodo para hacer click en el icono 'Flecha' 'atras' dentro de la vista 'Evaluacion MEP'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickFlechaAtras()
    {
     try {
        	waitForVisibility(btnFlechaAtras, 30);
        	au.driver.findElement(btnFlechaAtras).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickFlechaAtras", e);
     }
    }
    
    /**
     * Metodo para validar la existencia del modal de error "Informacion del periodo no disponible"
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarExistenciaModalAlertaPeriodoNoDisponible() throws InterruptedException {
      try {
    	    	au.driver.findElement(modalErrorContainer).isDisplayed();
    	    	au.driver.findElement(btnCancelar).isDisplayed();
    	    	au.driver.findElement(btnReintentar).isDisplayed();

      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          throw new RuntimeException("Error en validarExistenciaModalAlertaPeriodoNoDisponible", e);
      }
    }
    
    /**
     * Metodo para validar que se muestre el modal de error "Informacion del periodo no disponible"
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarModalAlertaPeriodoNoDisponible() throws InterruptedException {
      try {
    	    	au.driver.findElement(modalErrorContainer).isDisplayed();
    	    	au.driver.findElement(btnCancelar).isDisplayed();
    	    	au.driver.findElement(btnReintentar).isDisplayed();

    	    	au.driver.findElement(modalErrorTitle).isDisplayed();
    	    	au.driver.findElement(modalErrorText).isDisplayed();
    	    	au.driver.findElement(btnTextCancelar).isDisplayed();
    	    	au.driver.findElement(btnTextReintentar).isDisplayed();
      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          throw new RuntimeException("Error en validarModalAlertaPeriodoNoDisponible", e);
      }
    }
    
    /**
     * Metodo para hacer click en el boton 'Reintentar' dentro del modal de error "Informacion del periodo no disponible" en la vista "Evaluacion MEP"
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnReintentar()
    {
     try {
        	waitForVisibility(btnReintentar, 30);
        	au.driver.findElement(btnReintentar).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnReintentar", e);
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'Cancelar' dentro del modal de error "Informacion del periodo no disponible" en la vista "Evaluacion MEP"
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnCancelar()
    {
     try {
        	waitForVisibility(btnCancelar, 30);
        	au.driver.findElement(btnCancelar).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnCancelar", e);
     }
    }

    

    /**
     * Metodo para validar que se muestre un espacio promocional
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarUnEspacioPromocional() throws InterruptedException {
      try {
    	    	au.driver.findElement(btnTomarFoto).isDisplayed();
    	    	au.driver.findElement(titleEspacioPromocional).isDisplayed();
    	    	au.driver.findElement(textCodigosValidados).isDisplayed();
    	    	au.driver.findElement(textCodigosCompletados0De1).isDisplayed();
    	    	au.driver.findElement(iconExpand1Record).isDisplayed();

    	    	au.driver.findElement(iconExpand1Record).click();

      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          throw new RuntimeException("Error en validarUnEspacioPromocional", e);
      }
    }
    
    /**
     * Metodo para validar que se muestre el Resultado de Tienda
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarResultadoTiendaBar() throws InterruptedException {
      try {
    	    	au.driver.findElement(textResultadoTienda).isDisplayed();
    	    	au.driver.findElement(textCeroPercentage).isDisplayed();
    	    	au.driver.findElement(btnHelpResultadoTienda).isDisplayed();
    	    	au.driver.findElement(btnVerDetalle).isDisplayed();
    	    	au.driver.findElement(codigosDetectadosCeroPercentage).isDisplayed();
    	    	au.driver.findElement(materialesConIncidenciaCeroPercentage).isDisplayed();
    	    	au.driver.findElement(sinEvaluarOneHundredPercentageOnePop).isDisplayed();
      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          throw new RuntimeException("Error en validarResultadoTiendaBar", e);
      }
    }
    

    /**
     * Metodo para hacer click en el boton 'Ver detalle' dentro de la barra fija "Resultado Tienda" en la vista "Evaluacion MEP"
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnVerDetalle()
    {
     try {
        	waitForVisibility(btnVerDetalle, 30);
        	au.driver.findElement(btnVerDetalle).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnVerDetalle", e);
     }
    }
    
    /**
     * Metodo para validar que se muestre el Resultado de Tienda basado en los 4 registros que insertamos
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarResultadoTiendaBar4Records() throws InterruptedException {
     try {
        	waitForVisibility(textResultadoTienda, 30);
        	au.driver.findElement(textResultadoTienda).isDisplayed();
        	au.driver.findElement(btnHelpResultadoTienda).isDisplayed();
        	au.driver.findElement(btnVerDetalle).isDisplayed();
        	au.driver.findElement(codigosDetectados25Percentage).isDisplayed();
        	au.driver.findElement(materialesConIncidencia25Percentage).isDisplayed();
        	au.driver.findElement(sinEvaluar50Percentage).isDisplayed();
        	au.driver.findElement(score4Records).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarResultadoTiendaBar4Records", e);
     }
    }
    
    /**
     * Metodo para hacer scroll al ultimo POP enlistado
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void scrollLastElement()
    {
     try {
        	au.scrollToElement(ultimoCodigoValidado);
        	waitForVisibility(ultimoCodigoValidado, 30);
        	au.driver.findElement(ultimoCodigoValidado).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en scrollLastElement", e);
     }
    }
    
    /**
     * Metodo para validar la vista Toma de Fotografia cuando hacemos previamente click en Tomar Foto
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarVistaTomaDeFotografia() throws InterruptedException {
      try {
        	 waitForVisibility(titleCabeceras, 30);
    	    	au.driver.findElement(titleCabeceras).isDisplayed();
      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          throw new RuntimeException("Error en validarVistaTomaDeFotografia", e);
      }
    }
    

    /**
     * Metodo para hacer click en el boton 'Guia' dentro de la funcionalidad 'Camara'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnGuiaCamaraVistaFotografia() {
     try {
        	waitForVisibility(btnGuia, 30);
        	au.driver.findElement(btnGuia).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnGuiaCamaraVistaFotografia", e);
     }
	}
    
    /**
     * Metodo para hacer click en el boton 'Guia' dentro de la funcionalidad 'Galeria de Fotos'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnGuiaCamaraVistaGaleriaDeFotos() {
     try {
        	waitForVisibility(btnGuiaGaleriaFotos, 30);
        	au.driver.findElement(btnGuiaGaleriaFotos).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnGuiaCamaraVistaGaleriaDeFotos", e);
     }
	}
    
    /**
     * Metodo para hacer click en el boton 'Guia' dentro de la funcionalidad 'Toma de fotografia'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnCerrar() {
     try {
        	waitForVisibility(btnSubmit, 30);
        	au.driver.findElement(btnSubmit).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnCerrar", e);
     }
	}
    
    /**
     * Metodo para validar que exista el boton 'Flecha' 'Siguiente' dentro de la funcionalidad Toma de Fotografia
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarExistenciaGuiaCamara()
    {
     try {
        	waitForVisibility(titleGuia, 30);
        	au.driver.findElement(titleGuia).isDisplayed();
        	au.driver.findElement(pointOne).isDisplayed();
        	au.driver.findElement(pointTwo).isDisplayed();
        	au.driver.findElement(pointThree).isDisplayed();
        	au.driver.findElement(pointFour).isDisplayed();
        	au.driver.findElement(pointFive).isDisplayed();
        	au.driver.findElement(btnSubmit).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarExistenciaGuiaCamara", e);
     }
    }

    /**
     * Metodo para validar que los botones 'Retomar Fotografia' y 'Eliminar fotografia' dentro de una foto
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarExistenciaBotonRetomarYEliminarUnaFoto()
    {
     try {
        	waitForVisibility(btnEliminarFotografiaUnaFoto, 30);
        	au.driver.findElement(btnEliminarFotografiaUnaFoto).isDisplayed();
        	au.driver.findElement(btnRetomarFotografiaUnaFoto).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarExistenciaBotonRetomarYEliminarUnaFoto", e);
     }
    }
    

    /**
     * Metodo para hacer click en el boton 'Tomar Foto' dentro de la vista 'Galeria de Fotos'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnTomarFotoGaleriaFotos()
    {
     try {
        	waitForVisibility(btnTomarFotoGaleriaFotos, 30);
        	au.driver.findElement(btnTomarFotoGaleriaFotos).isDisplayed();
        	au.driver.findElement(btnTomarFotoGaleriaFotos).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnTomarFotoGaleriaFotos", e);
     }
    }
    
    /**
     * Metodo para hacer validar que exista 1 Fotografia tomada dentro de la funcionalidad 'Toma de fotografia'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarCantidadUnaFotografiaTomada()
    {
     try {
        	waitForVisibility(unaFotografia, 30);
        	au.driver.findElement(unaFotografia).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarCantidadUnaFotografiaTomada", e);
     }
    }
    
    /**
     * Metodo para hacer validar que exista 1 Fotografia tomada dentro de la funcionalidad 'Toma de fotografia'
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarNoExistaCantidadUnaFotografiaTomada()
    {
     try {
        	boolean notExistElement = au.driver.findElements(unaFotografia).isEmpty();
        	if (notExistElement == true)
        	{
        		System.out.println("No se ha encontrado el elemento 1 fotografia tomada dentro de la vista 'Tomar Fotografia'");
        		return true;
        	}
        	else
        	{
        		testCase.addTextEvidenceCurrentStep("Si se encontró el elemento");
        		return false;
        	}    	
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
    }
    
    
    /**
     * Metodo para hacer click en el icono 'Retomar Foto' dentro de la vista 'Galeria de Fotos'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnReTomarFotoGaleriaFotos()
    {
     try {
        	au.driver.findElement(btnRetomarFotografiaUnaFoto).isDisplayed();
        	au.driver.findElement(btnRetomarFotografiaUnaFoto).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnReTomarFotoGaleriaFotos", e);
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'Eliminar Foto' dentro de la vista 'Galeria de Fotos'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnEliminarFotoGaleriaFotos()
    {
     try {
        	waitForVisibility(btnEliminarFotografiaUnaFoto, 30);
        	au.driver.findElement(btnEliminarFotografiaUnaFoto).isDisplayed();
        	au.driver.findElement(btnEliminarFotografiaUnaFoto).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnEliminarFotoGaleriaFotos", e);
     }
    }
    

    /**
     * Metodo para validar que no exista la guia 'Guia para toma de fotografias'
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarGuiaCerrada()
    {
     try {
        	boolean notExistElement = au.driver.findElements(titleGuia).isEmpty();

        	if (notExistElement == true)
        	{
        		System.out.println("No se ha encontrado el elemento Guia para toma de fotografias");
            	return true;
        	}
        	else
        	{

        		testCase.addTextEvidenceCurrentStep("Si se encontró el elemento. Prueba fallida.");
            	return false;
        	}

     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'Tomar Foto' en la vista "Evaluacion MEP"
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnTomarFotoPrimerPop()
    {
     try {
        	waitForVisibility(btnTomarFotoPrimerPop, 30);
        	au.driver.findElement(btnTomarFotoPrimerPop).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnTomarFotoPrimerPop", e);
     }
    }

    /**
     * Metodo para hacer click en el icono para tomar una fotografia en la funcionalidad 'Toma de fotografia'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickIconoTomarUnaFotografia() throws InterruptedException {
     try {
        	waitForVisibility(btnCirculoTomarFotografia, 30);
    	    	au.driver.findElement(btnCirculoTomarFotografia).isDisplayed();
    	    	au.driver.findElement(btnCirculoTomarFotografia).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickIconoTomarUnaFotografia", e);
     }
    }
    
    /**
     * Metodo para hacer click en el icono para tomar una fotografia en la funcionalidad 'Toma de fotografia'
     * @throws InterruptedException
     */
    /**
     * Metodo para hacer click en el icono para tomar una fotografia en la funcionalidad 'Toma de fotografia'
	 * @author Ivan Yakab
	 * @param CantidadDeClicks Cantidad de clicks que se le hará al icono de circulo
     * @return 
	 * @date 03/11/2025
	 */
    public boolean clickIconoTomarMasFotografias(String CantidadDeClicks) throws InterruptedException {
     try {

    	    int cantidadInt = Integer.parseInt(CantidadDeClicks);
        	int cantFotosInicial = 2;

    	    for (int i = 1; i <= cantidadInt; i++) {
    	    	au.driver.findElement(btnCirculoTomarFotografia).isDisplayed();
    	    	au.driver.findElement(btnCirculoTomarFotografia).click();
    	    	if (au.driver.findElement(By.xpath("//android.widget.TextView[@text=\"" + cantFotosInicial + "\"]")).isDisplayed())
    	    	{
    	    		System.out.println("Foto " + cantFotosInicial + " tomada.");
    	    		cantFotosInicial++;
    	    	}
    	    	else
    	    	{
    	    		return false;
    	    	}
    		}
    		return true;  	
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
    }

    
    /**
     * Metodo para validar que exista el icono para tomar una fotografia en la funcionalidad 'Toma de fotografia'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarExistenciaIconoTomarUnaFotografia() throws InterruptedException {
     try {
    	    waitForVisibility(btnCirculoTomarFotografia, 30);	
        	au.driver.findElement(btnCirculoTomarFotografia).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarExistenciaIconoTomarUnaFotografia", e);
     }
    }
    
    /**
     * Metodo para validar que existan fotografias tomadas dentro de la vista "Exhibiciones no autorizadas"
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarFotografiasTomadas()
    {
     try {
        	int cantFotos = au.driver.findElements(fotografias).size();
    		System.out.println("Se han encontrado : " + cantFotos + " fotos");
        	testCase.addTextEvidenceCurrentStep("Se han encontrado : " + cantFotos + " fotos");

     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarFotografiasTomadas", e);
     }
    }
    
    /**
     * Metodo para hacer click en el icono 'Flecha' 'Siguiente' dentro de la funcionalidad Toma de Fotografia
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnFlechaSiguiente()
    {
     try {
        	waitForVisibility(btnFlechaSiguiente, 30);
        	au.driver.findElement(btnFlechaSiguiente).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnFlechaSiguiente", e);
     }
    }
    
    /**
     * Metodo para validar que exista el boton 'Flecha' 'Siguiente' dentro de la funcionalidad Toma de Fotografia
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarExistenciaBtnFlechaSiguiente()
    {
     try {
        	waitForVisibility(btnFlechaSiguiente, 30);
        	au.driver.findElement(btnFlechaSiguiente).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarExistenciaBtnFlechaSiguiente", e);
     }
    }
    
    /**
     * Validar vista cuando hacemos click en Siguiente dentro de la funcionalidad "Toma de Fotografia" - Vista confirmacion de tienda
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarVistaConfirmacionDeUnaFotografia() throws InterruptedException {
     try {
        	waitForVisibility(revision1Fotografia, 30);
    	    	au.driver.findElement(revision1Fotografia).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarVistaConfirmacionDeUnaFotografia", e);
     }
    }
    
    /**
     * Validar vista cuando hacemos click en Siguiente dentro de la funcionalidad "Toma de Fotografia" - 3 fotografias
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarVistaConfirmacionDeTresFotografias() throws InterruptedException {
     try {
    	    waitForVisibility(revision3Fotografias, 30);	
        	au.driver.findElement(revision3Fotografias).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarVistaConfirmacionDeTresFotografias", e);
     }
    }
    
    /**
     * Metodo para validar que exista una fotografia dentro de la vista 'Galeria de fotos'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarExistenciaDeUnaFotografiaEnGaleria()
    {
     try {
        	waitForVisibility(primerFotografia, 30);
        	au.driver.findElement(primerFotografia).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarExistenciaDeUnaFotografiaEnGaleria", e);
     }
    }
    
    /**
     * Metodo para validar que existan fotografias dentro de la vista 'Galeria de fotos'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarExistenciaDeFotografiasEnGaleria()
    {
     try {
        	int cantFotografias = au.driver.findElements(fotografias).size();
        	String cantFotografiasStr = Integer.toString(cantFotografias);
        	testCase.addTextEvidenceCurrentStep("Se han encontrado: " + cantFotografiasStr + " " + "fotografias.");
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarExistenciaDeFotografiasEnGaleria", e);
     }
    }
    
    /**
     * Metodo para validar que no existan fotografias dentro de la vista 'Galeria de fotos'
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean verificarNoExistenciaDeFotografiasEnGaleria()
    {
     try {
        	boolean notExistElement = au.driver.findElements(fotografias).isEmpty();

        	if (notExistElement == true)
        	{
        		System.out.println("No se ha encontrado fotografias dentro de la vista 'Galeria de fotos'");
            	return true;
        	}
        	else
        	{
        		testCase.addTextEvidenceCurrentStep("Si se encontró el elemento");
        		return false;
        	}

     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
    }
    

    /**
     * Validar aparicion de mensaje de alerta 'La foto ha sido eliminda'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarMensajeLaFotoHaSidoEliminda() throws InterruptedException {
     try {
        	waitForVisibility(alertaTitle, 30);
    	    	au.driver.findElement(alertaTitle).isDisplayed();
    	    	au.driver.findElement(textDeshacer).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarMensajeLaFotoHaSidoEliminda", e);
     }
    }
    
    /**
     * Validar que al hacer click en el boton 'deshacer' la foto se regrese a su estado original
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnDeshacer() throws InterruptedException {
      try {
        		au.driver.findElement(textDeshacer).isDisplayed();
    	    	au.driver.findElement(textDeshacer).click();
      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          throw new RuntimeException("Error en clickBtnDeshacer", e);
      }
    }
    
    /**
     * Metodo para hacer click en el boton 'Cerrar' dentro del mensaje de alerta 'La foto ha sido eliminda'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnCerrarMensajeAlerta() throws InterruptedException {
     try {
        	waitForVisibility(btnCerrarMensajeAlerta, 30);
        		au.driver.findElement(btnCerrarMensajeAlerta).isDisplayed();
    	    	au.driver.findElement(btnCerrarMensajeAlerta).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnCerrarMensajeAlerta", e);
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'Continuar' en la vista "Confirmacion de Fotografia"
	 * @author Ivan Yakab
	 * @param CantidadDeClicks Cantidad de clicks que se le hará al icono de circulo
	 * @date 03/11/2025
	 */
    public void clickBtnContinuar()
    {
     try {
        	waitForVisibility(btnContinuar, 30);
        	au.driver.findElement(btnContinuar).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnContinuar", e);
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'While using the app' cuando usemos emulador
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void androidAlertAllowTakePictures()
    {
  try {
    		waitForVisibility(btnAndroidCameraAlert, 30);
        	au.driver.findElement(btnAndroidCameraAlert).isDisplayed();
        	au.driver.findElement(btnAndroidCameraAlert).click();
  } catch (Exception e) {
      e.printStackTrace();
      testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
      throw new RuntimeException("Error en androidAlertAllowTakePictures", e);
  }
    }
    
    /**
     * Metodo para validar que se muestre un mensaje de alerta cuando 1 POP si fue localizado pero el otro POP no fue localizado
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnCerrarMensajeCodigosPorVerificar() throws InterruptedException {
     try {
        	waitForVisibility(btnCerrarMensajeAlerta, 30);
    	    	au.driver.findElement(btnCerrarMensajeAlerta).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnCerrarMensajeCodigosPorVerificar", e);
     }
    }
    
    /**
     * Metodo para validar que no se encuentre el modal 'Faltan codigos por verificar en esta zona' dentro la vista 'Folio de produccion'
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarNoExistenciaMensajeCodigosPorVerificarFolioProd() throws InterruptedException {
      try {
        	 waitForInvisibility(btnCerrarMensajeAlerta, 30);
        	  List<WebElement> elementos = au.driver.findElements(btnCerrarMensajeAlerta);
        	  return elementos.isEmpty();
      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          return false;
      }
    }
    
    /**
     * Metodo para validar que se muestre un mensaje de alerta cuando 1 POP si fue localizado pero el otro POP no fue localizado
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarMensajeCodigosPorVerificar() throws InterruptedException {
     try {
        	waitForVisibility(titleFolioMensajeDeErrorDosPops, 30);
    	    	au.driver.findElement(titleFolioMensajeDeErrorDosPops).isDisplayed();
    	    	au.driver.findElement(textFolioMensajeDeErrorDosPops).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarMensajeCodigosPorVerificar", e);
     }
    }
    

    /**
     * Validar vista 'Folio de Produccion' cuando hacemos click en el boton "Continuar" dentro de la vista 'Confirmacion de fotografias tomadas'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarVistaFolioDeProduccion() throws InterruptedException {
     try {
        	waitForVisibility(titleFolioDeProduccion, 30);
    	    	au.driver.findElement(titleFolioDeProduccion).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarVistaFolioDeProduccion", e);
     }
    }
    
    /**
     * Validar vista 'Folio de Produccion' cuando hacemos click en el boton "Continuar" dentro de la vista 'Confirmacion de fotografias tomadas' y existen 2 o mas pops sin validar
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarVistaFolioDeProduccionConDosPopsMas() throws InterruptedException {
     try {
        	waitForVisibility(titleFolioDeProduccion, 30);
        	au.driver.findElement(titleFolioDeProduccion).isDisplayed();
    	    	au.driver.findElement(btnFinalizar).isDisplayed();
    	    	au.driver.findElement(btnTomarFotoGeneral).isDisplayed();
    	    	au.driver.findElement(btnBuscar).isDisplayed();
    	    	au.driver.findElement(btnGuardarAvance).isDisplayed();
    	    	au.driver.findElement(btnBuscar).isDisplayed();
    	    	au.driver.findElement(retornarVistaAnterior).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarVistaFolioDeProduccionConDosPopsMas", e);
     }
    }
    
	/**
	 * Validar que exista el boton 'Tomar Foto General' dentro de la vista vista 'Folio de Produccion'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
	public void validarExistenciaBtnTomarFotoGeneral() throws InterruptedException {
  try {
    		waitForVisibility(titleFolioDeProduccion, 30);
    	    	au.driver.findElement(btnTomarFotoGeneral).isDisplayed();
  } catch (Exception e) {
      e.printStackTrace();
      testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
      throw new RuntimeException("Error en validarExistenciaBtnTomarFotoGeneral", e);
  }
	}
    
    /**
     * Metodo para validar que el primer Folio de Produccion este completo
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarPrimerFolioDeProduccionCompleto()
    {
     try {
        	waitForVisibility(primerfolioDeProduccionCompleto, 30);
        	au.driver.findElement(primerfolioDeProduccionCompleto).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarPrimerFolioDeProduccionCompleto", e);
     }
    }
    
    /**
     * Metodo para validar que el folio 160 se encuentre Completado y organizado Hasta el ultimo lugar
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarPrimerFolio160CompletoLabel()
    {
     try {
        	waitForVisibility(primerfolioDeProduccionCompletoLabelFolio160, 30);
        	au.driver.findElement(primerfolioDeProduccionCompletoLabelFolio160).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarPrimerFolio160CompletoLabel", e);
     }
    }
    
    /**
     * Metodo para validar que el folio 160 se encuentre Completado y organizado Hasta el ultimo lugar
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarExistenciaBtnTomarFotoFolioProduccion()
    {
     try {
        	waitForVisibility(allBtnTomarFoto, 30);
        	au.driver.findElement(allBtnTomarFoto).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarExistenciaBtnTomarFotoFolioProduccion", e);
     }
    }
    
    /**
     * Metodo para validar que el folio 160 se encuentre Completado y organizado Hasta el ultimo lugar
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickUltimoBtnTomarFotoFolioProd()
    {
     try {
        	waitForVisibility(allBtnTomarFoto, 30);
        	au.driver.findElements(allBtnTomarFoto).get(0).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickUltimoBtnTomarFotoFolioProd", e);
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'Buscar' dentro de la vista 'Folio de produccion'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnBuscarFolioProd() throws InterruptedException {
     try {
        	waitForVisibility(btnBuscar, 30);
    	    	au.driver.findElement(btnBuscar).isDisplayed();
    	    	au.driver.findElement(btnBuscar).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnBuscarFolioProd", e);
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'Buscar' dentro de la vista 'Folio de produccion'
	 * @author Ivan Yakab
	 * @param nombreEspacioGrupo Sera el valor que busquemos dentro de la barra de busqueda
	 * @date 03/11/2025
	 */
    public void ingresarValorBtnBuscarFolioProd(String nombreEspacioGrupo) throws InterruptedException {
     try {
        	waitForVisibility(btnBuscarActivadoText, 30);
    	    	au.driver.findElement(btnBuscarActivadoText).sendKeys(nombreEspacioGrupo);
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en ingresarValorBtnBuscarFolioProd", e);
     }
    }
    
    /**
     * Metodo para validar que exista el valor buscado dentro de la vista 'Folio de produccion'
	 * @author Ivan Yakab
	 * @param nombreEspacioGrupo Sera el valor que busquemos dentro de la barra de busqueda
	 * @date 03/11/2025
	 */
    public void validarExistenciaValorBtnBuscarFolioProd(String nombreEspacioGrupo) throws InterruptedException {
     try {
        	waitForVisibility(By.xpath("(//android.widget.TextView[contains(@text,'" + nombreEspacioGrupo +"')])[1]"), 30);
    	    	au.driver.findElement(By.xpath("(//android.widget.TextView[contains(@text,'" + nombreEspacioGrupo +"')])[1]")).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarExistenciaValorBtnBuscarFolioProd", e);
     }
    }
    
    /**
     * Metodo para validar que exista el mensaje de error cuando hacemos una busqueda invalida dentro de la vista 'Folio de produccion'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarExistenciaMensajeErrorBusquedaInvalidaFolioProd() throws InterruptedException {
      try {
    	    	waitForVisibility(ceroResults, 30);
    	    	au.driver.findElement(ceroResults).isDisplayed();
    	    	au.driver.findElement(titleMensajeErrorBusquedaInvalida).isDisplayed();
    	    	au.driver.findElement(textMensajeErrorBusquedaInvalida).isDisplayed();
      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          throw new RuntimeException("Error en validarExistenciaMensajeErrorBusquedaInvalidaFolioProd", e);
      }
    }
    
    /**
     * Metodo para hacer click en el boton 'X' / 'Eliminar texto ingresado' dentro de la barra de busqueda en la vista 'Folio de produccion'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnEliminarTextoBarraBusquedaFolioProd() throws InterruptedException {
     try {
        	waitForVisibility(btnCerrarBarraBusquedaFolioProd, 30);
    	    	au.driver.findElement(btnCerrarBarraBusquedaFolioProd).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnEliminarTextoBarraBusquedaFolioProd", e);
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'Cancelar' dentro de la barra de busqueda en la vista 'Folio de produccion'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnCancelarBarraBusquedaFolioProd() throws InterruptedException {
     try {
        	waitForVisibility(btnCancelarBarraBusquedaFolioProd, 30);
    	    	au.driver.findElement(btnCancelarBarraBusquedaFolioProd).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnCancelarBarraBusquedaFolioProd", e);
     }
    }
    
    /**
     * Metodo para validar que no exista el boton 'X' / 'Eliminar texto ingresado' dentro de la barra de busqueda en la vista 'Folio de produccion'
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarNoExistenciaBtnEliminarTextoBarraBusquedaFolioProd() throws InterruptedException {
      try {
        	 waitForInvisibility(btnCerrarBarraBusquedaFolioProd, 30);
        	  List<WebElement> elementos = au.driver.findElements(btnCerrarBarraBusquedaFolioProd);
        	  return elementos.isEmpty();
      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          return false;
      }
    }
    
    /**
     * Metodo para validar que no este activa la barra de busqueda dentro la vista 'Folio de produccion'
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarNoExistenciaBarraDeBusquedaActivaFolioProd() throws InterruptedException {
      try {
        	 waitForInvisibility(barraBusquedaActiva, 30);
        	  List<WebElement> elementos = au.driver.findElements(barraBusquedaActiva);
        	  return elementos.isEmpty();
      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          return false;
      }
    }
    
    
    
    /**
     * Metodo para hacer click en el boton 'Finalizar' dentro de la vista 'Folio de produccion'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnFinalizar() throws InterruptedException {
     try {
        	waitForVisibility(btnFinalizar, 30);
    	    	au.driver.findElement(btnFinalizar).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnFinalizar", e);
     }
    }
    
    /**
     * Metodo para validar la alerta que se muestra despues de dar click en el boton 'Finalizar' dentro de la vista 'Folio de Produccion'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void verificarMensajeAlertaDespuesBtnFinalizar() throws InterruptedException {
     try {
        	waitForVisibility(btnContinuar, 30);
    	    	au.driver.findElement(btnCancelar).isDisplayed();
    	    	au.driver.findElement(btnContinuar).isDisplayed();
    	    	au.driver.findElement(titleValidacionCodigosIncompleta).isDisplayed();
    	    	au.driver.findElement(textValidacionCodigosIncompletaAfterBtnFinalizar).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en verificarMensajeAlertaDespuesBtnFinalizar", e);
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'Filtro' dentro de la vista 'Evaluacion MEP'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnFiltro() throws InterruptedException {
     try {
        	waitForVisibility(btnFiltro, 30);
    	    	au.driver.findElement(btnFiltro).isDisplayed();
    	    	au.driver.findElement(btnFiltro).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnFiltro", e);
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'Todos' dentro de la vista 'Filtrar por'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnFiltroTodos() throws InterruptedException {
     try {
        	waitForVisibility(btnFiltroTodos, 30);
    	    	au.driver.findElement(btnFiltroTodos).isDisplayed();
    	    	au.driver.findElement(btnFiltroTodos).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnFiltroTodos", e);
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'Completado' dentro de la vista 'Filtrar por'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnFiltroCompletado() throws InterruptedException {
      try {
    	    	au.driver.findElement(btnFiltroCompletado).isDisplayed();
    	    	au.driver.findElement(btnFiltroCompletado).click();
      } catch (Exception e) {
          e.printStackTrace();
          testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
          throw new RuntimeException("Error en clickBtnFiltroCompletado", e);
      }
    }
    
    /**
     * Metodo para hacer click en el boton 'Incompleto' dentro de la vista 'Filtrar por'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnFiltroIncompletos() throws InterruptedException {
     try {
        	waitForVisibility(btnFiltroIncompletos, 30);
        	au.driver.findElement(btnFiltroIncompletos).isDisplayed();
    	    au.driver.findElement(btnFiltroIncompletos).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnFiltroIncompletos", e);
     }
    }
    
    /**
     * Metodo para validar que aparezca un recuadro blanco con el texto 'Filtro por:' 'Todos' 'Completos' 'Incompletos'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarVistaFiltros()
    {
     try {
        	waitForVisibility(titleFiltroPor, 30);
        	au.driver.findElement(titleFiltroPor).isDisplayed();
        	au.driver.findElement(textFiltroTodos).isDisplayed();
        	au.driver.findElement(textFiltroCompletado).isDisplayed();
        	au.driver.findElement(textFiltroIncompletos).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarVistaFiltros", e);
     }
    }

    /**
     * Metodo para validar que aparezca un mensaje de alerta Exitosa de identificacion de codigos dentro de la vista 'Folio de Produccion'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarMensajeExitosoIdentificacionDeCodigos()
    {
     try {
        	waitForVisibility(titleIdentificacionCodigos, 30);
        	au.driver.findElement(titleIdentificacionCodigos).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarMensajeExitosoIdentificacionDeCodigos", e);
     }
    }
    
    /**
     * Metodo para validar que aparezca un mensaje de alerta de 'Sin resultados' cuando escogemos un filtro y no se encuentraron resultados
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarMensajeFiltroSinResultados()
    {
     try {
        	waitForVisibility(titleSinResultados, 30);
        	au.driver.findElement(titleSinResultados).isDisplayed();
        	au.driver.findElement(textMensajeError).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarMensajeFiltroSinResultados", e);
     }
    }
    
    /**
     * Metodo para validar que aparezca un mensaje de alerta 'Validacion de codigos incompleta' dentro de la vista 'Folio de Produccion'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarMensajeAlertaValidacionCodigosIncompleta()
    {
     try {
        	au.driver.findElement(titleValidacionCodigosIncompleta).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarMensajeAlertaValidacionCodigosIncompleta", e);
     }
    }
    
    /**
     * Metodo para validar que la cant total de Folios  este completo y se encuentre el siguiente valor 1/1
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarCantTotalDeUnPop()
    {
     try {
        	waitForVisibility(codigosValidados1de1, 30);
        	au.driver.findElement(codigosValidados1de1).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarCantTotalDeUnPop", e);
     }
    }
    
    /**
     * Metodo para validar que la cant total de Folios  este completo y se encuentre el siguiente valor 2/2
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarCantTotalDeDosPops()
    {
     try {
        	waitForVisibility(codigosValidados2de2, 30);
        	au.driver.findElement(codigosValidados2de2).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarCantTotalDeDosPops", e);
     }
    }
    
    /**
     * Metodo para validar que 1 de 2 Folios este completo y se encuentre el siguiente valor 1/2
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validar1FolioDeDosPops()
    {
     try {
        	waitForVisibility(codigosValidados1de2, 30);
        	au.driver.findElement(codigosValidados1de2).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validar1FolioDeDosPops", e);
     }
    }
    
    /**
     * Metodo para validar label de 2 de 3 Folio
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarDosFoliosDeTres()
    {
     try {
        	waitForVisibility(codigosValidados2de3, 30);
        	au.driver.findElement(codigosValidados2de3).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarDosFoliosDeTres", e);
     }
    }
    
    /**
     * Metodo para validar que este vacio el primer POP      
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarPrimerCodigoSinValidar()
    {
     try {
        	waitForVisibility(textCodigosCompletados0De1PrimerPop, 30);
        	au.driver.findElement(textCodigosCompletados0De1PrimerPop).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarPrimerCodigoSinValidar", e);
     }
    }
    
    /**
     * Metodo para contabilizar cuantos Pops se encuentran en estatus 'Incompleto' dentro de la vista 'Evaluacion MEP'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void contabilizarPopsIncompletos()
    {
     try {
    	    List<WebElement> listadoPopsIncompletos = au.driver.findElements(popsStatusIncompleto);
    	    int cantidad = listadoPopsIncompletos.size();
    	    String cantidadStr = Integer.toString(cantidad);
    	    testCase.addTextEvidenceCurrentStep("Se encontraron " + cantidadStr + " elementos con status 'Incompleto'");
    	    System.out.println("Se encontraron " + cantidad + " elementos con status 'Incompleto'");
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en contabilizarPopsIncompletos", e);
     }
    }
    
    /**
     * Metodo para contabilizar cuantos Pops tienen la etiqueta 'Tomar Foto(s)'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void contabilizarPopsConBotonTomarFoto()
    {
     try {
    	    List<WebElement> listadoPopsIncompletos = au.driver.findElements(popsStatusIncompleto);
    	    int cantidad = listadoPopsIncompletos.size();
    	    String cantidadStr = Integer.toString(cantidad);
    	    testCase.addTextEvidenceCurrentStep("Se encontraron " + cantidadStr + " elementos con status 'Incompleto'");
    	    System.out.println("Se encontraron " + cantidad + " elementos con el boton 'Tomar Foto(s)'");
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en contabilizarPopsConBotonTomarFoto", e);
     }
    }
    
    /**
     * Metodo para contabilizar cuantos Pops se encuentran en estatus 'Completo' dentro de la vista 'Evaluacion MEP'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void contabilizarPopsCompletos()
    {
     try {
    	    List<WebElement> listadoPopsCompletos = au.driver.findElements(popsStatusCompleto);
    	    int cantidad = listadoPopsCompletos.size();
    	    String cantidadStr = Integer.toString(cantidad);
    	    testCase.addTextEvidenceCurrentStep("Se encontraron " + cantidadStr + " elementos con status 'Completo'");
    	    System.out.println("Se encontraron " + cantidad + " elementos con status 'Completo'");
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en contabilizarPopsCompletos", e);
     }
    }
    
    /**
     * Metodo para hacer click el boton 'Expandir' dentro del primer POP
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBotonExpandirPrimerPop()
    {
     try {
        	waitForVisibility(iconExpand1Record, 30);
        	au.driver.findElement(iconExpand1Record).isDisplayed();
        	au.driver.findElement(iconExpand1Record).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBotonExpandirPrimerPop", e);
     }
    }
    
    /**
     * Metodo para validar que exista el GrupoPromocional 'Cabecera'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarGrupoPromocionalCabecera()
    {
     try {
        	waitForVisibility(espacioCabecera, 30);
        	au.driver.findElement(espacioCabecera).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarGrupoPromocionalCabecera", e);
     }
    }
    
    /**
     * Metodo para validar que no exista el GrupoPromocional 'Cabecera'
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarNoExistenciaGrupoPromocionalCabecera()
    {
     try {
        	boolean notExistElement = au.driver.findElements(espacioCabecera).isEmpty();

        	if (notExistElement == true)
        	{
        		System.out.println("No se ha encontrado el elemento cabecera");
        		return true;
        	}
        	else
        	{
        		testCase.addTextEvidenceCurrentStep("Si se encontró el elemento");
        		return false;

        	}

     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
    }
    
    /**
     * Metodo para validar existencia de pops con estatus 'Incompletos'
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarExistenciaDePopsIncompletos()
    {
     try {
    	    List<WebElement> listadoPopsIncompletos = au.driver.findElements(popsStatusIncompleto);
    	    int cantidad = listadoPopsIncompletos.size();

    	    if (cantidad >= 1)
    	    {
    		    String cantidadStr = Integer.toString(cantidad);
    		    testCase.addTextEvidenceCurrentStep("Existen " + cantidadStr + " elementos con status 'Incompletos'");
    		    System.out.println("Se encontraron " + cantidad + " elementos con status 'Incompletos'");
    		    return true;
    	    }
    	    else
    	    {
    		    String cantidadStr = Integer.toString(cantidad);
    		    testCase.addTextEvidenceCurrentStep("No existen elementos con status 'Incompletos'");
    		    System.out.println("Se encontro " + cantidadStr + " elementos con status 'Incompletos'");
    		    return false;
    	    }
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
    }
    
    /**
     * Metodo para validar que no existan pops con estatus 'Incompletos'
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarNoExistenciaDePopsIncompletos()
    {
     try {
    	    List<WebElement> listadoPopsIncompletos = au.driver.findElements(popsStatusIncompleto);
    	    int cantidad = listadoPopsIncompletos.size();

    	    if (cantidad == 0)
    	    {
    		    testCase.addTextEvidenceCurrentStep("No existen elementos con status 'Incompletos'");
    		    System.out.println("Se encontraron " + cantidad + " elementos con status 'Incompletos'");
    		    return true;
    	    }
    	    else
    	    {
    		    String cantidadStr = Integer.toString(cantidad);
    		    testCase.addTextEvidenceCurrentStep("Si existen" + cantidadStr + " elementos con status 'Incompletos'");
    		    System.out.println("Se encontraron " + cantidad + " elementos con status 'Incompletos'");
    		    return false;
    	    }
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
    }
    
    /**
     * Metodo para validar existencia de pops con estatus 'Completos'
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarExistenciaDePopsCompletos()
    {
     try {
    	    List<WebElement> listadoPopsCompletos = au.driver.findElements(popsStatusCompleto);
    	    int cantidad = listadoPopsCompletos.size();

    	    if (cantidad >= 1)
    	    {
    		    String cantidadStr = Integer.toString(cantidad);
    		    testCase.addTextEvidenceCurrentStep("Existen " + cantidadStr + " elementos con status 'Completos'");
    		    System.out.println("Se encontraron " + cantidad + " elementos con status 'Completos'");
    		    return true;
    	    }
    	    else
    	    {
    		    String cantidadStr = Integer.toString(cantidad);
    		    testCase.addTextEvidenceCurrentStep("No existen elementos con status 'Completos'");
    		    System.out.println("Se encontro " + cantidadStr + " elementos con status 'Completos'");
    		    return false;
    		}
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
    }
    
    /**
     * Metodo para validar que no existan pops con estauts 'Completos'
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarNoExistenciaDePopsCompletos()
    {
     try {
    	    List<WebElement> listadoPopsCompletos = au.driver.findElements(popsStatusCompleto);
    	    int cantidad = listadoPopsCompletos.size();

    	    if (cantidad == 0)
    	    {
    		    testCase.addTextEvidenceCurrentStep("No existen elementos con status 'Completos'");
    		    System.out.println("Se encontraron " + cantidad + " elementos con status 'Completos'");
    		    return true;
    	    }
    	    else
    	    {
    		    String cantidadStr = Integer.toString(cantidad);
    		    testCase.addTextEvidenceCurrentStep("Si existen " + cantidadStr + " elementos con status 'Completos'");
    		    System.out.println("Existen " + cantidad + " elementos con status 'Completos'");
    		    return false;
    	    }
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
    }
    
    
    /**
     * Validar mensaje de alerta 'Error al enviar las fotos'  'Verifica tu conexion e intentalo nuevamente' Btn Cerrar Btn Reintentar cuando no tengamos conexion 
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarMensajeAlertaErrorAlEnviarFotos() throws InterruptedException {
     try {
        	waitForVisibility(titleErrorAlEnviarFotos, 30);
        	au.driver.findElement(textErrorAlEnviarFotos).isDisplayed();
        	au.driver.findElement(titleErrorAlEnviarFotos).isDisplayed();
    	    	au.driver.findElement(btnCerrar).isDisplayed();
    	    	au.driver.findElement(btnReintentar).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarMensajeAlertaErrorAlEnviarFotos", e);
     }
    }
    
    /**
     * Click en el boton 'Cerrar' dentro del mensaje de alerta 'Error al enviar las fotos'  'Verifica tu conexion e intentalo nuevamente' Btn Cerrar Btn Reintentar cuando no tengamos conexion 
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnCerrarMensajeAlertaErrorAlEnviarFotos() throws InterruptedException {
     try {
        	waitForVisibility(btnCerrar, 30);
        	au.driver.findElement(btnCerrar).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnCerrarMensajeAlertaErrorAlEnviarFotos", e);
     }
    }
    
    /**
     * Click en el boton 'Reintentar' dentro del mensaje de alerta 'Error al enviar las fotos'  'Verifica tu conexion e intentalo nuevamente' Btn Cerrar Btn Reintentar cuando no tengamos conexion 
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnReintentarMensajeAlertaErrorAlEnviarFotos() throws InterruptedException {
     try {
        	waitForVisibility(btnReintentar, 30);
        	au.driver.findElement(btnReintentar).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnReintentarMensajeAlertaErrorAlEnviarFotos", e);
     }
    }
    
    /**
     * Click en el boton 'Continuar Evaluacion' dentro del dentro de un espacio
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnContinuarEvaluacion() throws InterruptedException {
     try {
        	waitForVisibility(btnContinuarEvaluacionFirst, 30);
        	au.driver.findElement(btnContinuarEvaluacionFirst).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnContinuarEvaluacion", e);
     }
    }
    
    /**
     * Metodo para validar que exista uno o mas status con nombre 'Reportado como Faltante' dentro de la vista Folio de Produccion
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarExistenciaStatusReportadoComoFaltante()
    {
     try {
        	waitForVisibility(statusReportadoComoFaltante, 30);
        	au.driver.findElement(statusReportadoComoFaltante).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarExistenciaStatusReportadoComoFaltante", e);
     }
    }
    
    /**
     * Validar mensaje de alerta '¿Quieres guardar este proceso?' 'Todos los cambios realizados serán guardados y podrás retomarlo más tarde para continuar con el siguiente paso.' dentro de la vista Folio de Produccion
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarMensajeQuieresGuardarEsteProceso() throws InterruptedException {
     try {
        	waitForVisibility(titleQuieresGuardarEsteProceso, 30);
        	au.driver.findElement(titleQuieresGuardarEsteProceso).isDisplayed();
        	au.driver.findElement(textTodosLosCambiosRealizados).isDisplayed();
    	    	au.driver.findElement(btnCancelar).isDisplayed();
    	    	au.driver.findElement(btnContinuar).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarMensajeQuieresGuardarEsteProceso", e);
     }
    }
    
    /**
     * Metodo para hacer click en el boton 'Guardar Avance' dentro de la vista 'Folio de Produccion'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnGuardarAvance()
    {
     try {
        	waitForVisibility(btnGuardarAvance, 30);
        	au.driver.findElement(btnGuardarAvance).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnGuardarAvance", e);
     }
    }
 
    
    /**
     * Validar mensaje de alerta 'La validación de códigos está incompleta' 'Tienes un espacio promocional pendiente a , ¿deseas continuar validando estos materiales?' dentro de la vista Evaluacion Mep
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarMensajeContinuarValidandoMateriales() throws InterruptedException {
     try {
        	waitForVisibility(textContinuarValidandoMateriales, 30);
        	au.driver.findElement(textContinuarValidandoMateriales).isDisplayed();
        	au.driver.findElement(titleContinuarValidandoMateriales).isDisplayed();
    	    	au.driver.findElement(btnIgnorar).isDisplayed();
    	    	au.driver.findElement(btnContinuar).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarMensajeContinuarValidandoMateriales", e);
     }
    }
    
    
    /**
     * Metodo para hacer click en el boton 'Ignorar' dentro del mensaje 'Continuar validando materiales'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void clickBtnIgnorar()
    {
     try {
        	waitForVisibility(btnIgnorar, 30);
        	au.driver.findElement(btnIgnorar).click();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en clickBtnIgnorar", e);
     }
    }
    
    /**
     * Validar que no exista el mensaje de alerta 'La validación de códigos está incompleta' 'Tienes un espacio promocional pendiente a finalizar, ¿deseas continuar validando estos materiales?' dentro de la vista Evaluacion Mep
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarNoExistenciaMensajeContinuarValidandoMateriales() throws InterruptedException {
     try {
        	waitForInvisibility(textContinuarValidandoMateriales, 15);
        	List<WebElement> elementos = au.driver.findElements(textContinuarValidandoMateriales);
        	return elementos.isEmpty();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
    }
    
    /**
     * Validar que no exista el pop up 'Progreso guardado exitosamente' dentro de la vista Evaluacion Mep
	 * @author Ivan Yakab
	 * @param NA
     * @return 
	 * @date 03/11/2025
	 */
    public boolean validarNoExistenciaPopUpProgresoGuardadoExitosamente() throws InterruptedException {
     try {
        	waitForInvisibility(popUpProgresoGuardadoExitosamente, 15);
        	List<WebElement> elementos = au.driver.findElements(popUpProgresoGuardadoExitosamente);
        	return elementos.isEmpty();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         return false;
     }
    }
    
    /**
     * Validar que se muestre la etiqueta de 'En Curso'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarEtiquetaEnCurso() throws InterruptedException {
     try {
        	waitForVisibility(etiquetaEnCurso, 15);
    	    au.driver.findElement(etiquetaEnCurso).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarEtiquetaEnCurso", e);
     }
    }
    
    /**
     * Validar que se muestre el boton/Etiqueta 'Continuar evaluacion'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarBtnContinuarEvaluacion() throws InterruptedException {
     try {
        	waitForVisibility(btnContinuarEvaluacion, 15);
    	    au.driver.findElement(btnContinuarEvaluacion).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarBtnContinuarEvaluacion", e);
     }
    }
    
    /**
     * Validar que se muestre la etiqueta de 'Completo'
	 * @author Ivan Yakab
	 * @param NA
	 * @date 03/11/2025
	 */
    public void validarEtiquetaCompleto() throws InterruptedException {
     try {
        	waitForVisibility(etiquetaCompleto, 15);
    	    au.driver.findElement(etiquetaCompleto).isDisplayed();
     } catch (Exception e) {
         e.printStackTrace();
         testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
         throw new RuntimeException("Error en validarEtiquetaCompleto", e);
     }
    }
    
    /**
     * Dispara la captura de fotografía y valida que el botón
     * pase a estado no clickable como efecto del click.
     *
     * Validación activa para detectar cambio transitorio de estado.
     *
     * @author Mario A. De León Rdz.
     * @throws Exception El test falla si el atributo no cambia a clickable=false.
     * @date 26/Enero/2026
     */
    public void tomarFotoYValidarBloqueo() throws Exception {
        try {
            long timeoutMs = 10000;
            long endTime = System.currentTimeMillis() + timeoutMs;
            boolean clickDisparado = false;

            while (System.currentTimeMillis() < endTime) {

                WebElement btn = au.driver.findElement(btnCirculoTomarFotografia);
                String clickable = btn.getAttribute("clickable");

                // DISPARO CONTROLADO DEL EVENTO
                if (!clickDisparado && "true".equals(clickable)) {
                    btn.click();
                    testCase.addScreenShotCurrentStep(au, "bloqueo_camara");
                    clickDisparado = true;
                    continue;
                }

                // DETECCIÓN DEL CAMBIO DE ESTADO
                if (clickDisparado && "false".equals(clickable)) {
                	System.out.println("se detecta cambio a clickable=false");
                    testCase.addTextEvidenceCurrentStep(
                        "Después de tomar la fotografía se detecta cambio a clickable=false del botón, lo que corresponde al parpadeo de la pantalla"
                    );
                    
                    return;
                }

                Thread.sleep(100);
            }

            throw new Exception(
                "Se realizó el click, pero el botón nunca cambió a clickable=false."
            );

        } catch (Exception e) {
            testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
            throw e;
        }
    }
    
    /**
	 * Valida el modal "Faltan códigos" disparando el evento en un hilo secundario.
	 * Se inicia una búsqueda activa en el hilo principal antes de ejecutar el clic
	 * para asegurar la captura del modal, debido a su naturaleza transitoria.
	 *
	 * @author Mario A. De León Rdz.
	 * @date 22/Diciembre/2025
	 */
	public void validarModalErrorFaltanCodigos() throws Exception {
	    try {
	        long endTime = System.currentTimeMillis() + 30000;
	        boolean modalDetectado = false;
	        //DISPARADOR del evento (NO lo hagas fuera del método)
	        Thread trigger = new Thread(() -> {
	            try {
	                Thread.sleep(800); 
	                vistaEvaluacionMepOffline.clickBtnContinuarSinEv();
	            } catch (Exception ignored) {}
	        });
	        trigger.start();
	        //VALIDACION CONTINUA ()
	        while (System.currentTimeMillis() < endTime) {
	            List<WebElement> elementos = au.driver.findElements(textModalErrorFaltanCodigos);

	            if (!elementos.isEmpty() && elementos.get(0).isDisplayed()) {
	                modalDetectado = true;
	                testCase.addScreenShotCurrentStep(au, "Modal Detectado");
	                testCase.addTextEvidenceCurrentStep(" - Se detecta el modal \"Faltan códigos...\"");
	                break;
	            }
	            Thread.sleep(50); // Polling rápido para capturar elementos efímeros
	        }

	        trigger.join();

	        if (!modalDetectado) {
	            testCase.addScreenShotCurrentStep(au, "Error - No se detectó modal");
	            throw new Exception("No se detectó el modal \"Faltan códigos\" tras 30s");
	        }

	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw e;
	    }
	}	
}