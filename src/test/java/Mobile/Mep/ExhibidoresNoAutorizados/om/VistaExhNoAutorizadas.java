package Mobile.Mep.ExhibidoresNoAutorizados.om;

import static org.testng.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class VistaExhNoAutorizadas {
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
    
    
	/*
	 * Localizadores 
	 ******************************************************************************************/
    private By btnFlechaAtras = By.xpath("//android.view.ViewGroup[@resource-id=\"icon-button\"]");
    private By btnFotografia = By.xpath("//android.widget.TextView[@text=\"Tomar foto\"]");
    private By subtitle = By.xpath("//android.widget.TextView[@text=\"Reporta exhibidores no autorizados:\"]");
    private By fotografias = By.xpath("//android.widget.ImageView");
    private By btnBorrarPrimerFoto = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[3]");
    private By btnRetomarFotografia = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[2]");

    private By campoTipoExhibicion = By.xpath("//android.widget.TextView[@text=\"Tipo de exhibición\"]");
    private By campoEspecificaProveedor = By.xpath("//android.widget.EditText[@text=\"Especifica proveedor\"]");
    private By campoEspecificaCantidad = By.xpath("//android.widget.EditText[@text=\"Especifica cantidad\"]");
    private By errorMessageEspecificaProveedor = By.xpath("//android.widget.TextView[@text=\"El proveedor debe ser texto, sin caracteres especiales\"]");
    private By errorMessageEspecificaProveedorMenorA5 = By.xpath("//android.widget.TextView[@text=\"El proveedor debe tener entre 5 y 50 caracteres\"]");

    private By errorMessageEspecificaCantidad = By.xpath("//android.widget.TextView[@text=\"Ingresa una cantidad de exhibiciones valida\"]");
    private By btnFinalizar = By.xpath("//android.view.ViewGroup[@content-desc=\"Finalizar\"]");
    

    /*
	 * Localizadores - Mensaje de alerta despues de finalizar - Reporte enviado exitosamente
	 ******************************************************************************************/
    
    private By titulo = By.xpath("//android.widget.TextView[@text=\"Reporte enviado exitosamente\"]");
    private By texto = By.xpath("//android.widget.TextView[contains(@text, 'El reporte de faltante se ha realizado')]");
    private By btnCerrar = By.xpath("//android.view.ViewGroup[@content-desc=\"Cerrar\"]");
    
    /*
	 * Localizadores - Mensaje de error - Hubo un error al procesar el reporte
	 ******************************************************************************************/
    private By tituloError = By.xpath("//android.widget.TextView[@text=\"Hubo un error al procesar el reporte.\"]");
    private By textoError = By.xpath("//android.widget.TextView[@text=\"Intenta nuevamente.\"]");
    private By btnCancelar = By.xpath("//android.view.ViewGroup[@content-desc=\"Cancelar\"]");
    private By btnReintentar = By.xpath("//android.view.ViewGroup[@content-desc=\"Reintentar\"]");
    
    
    /*
	 * Localizadores - Mensaje de alerta despues de eliminar una foto
	 ******************************************************************************************/
    private By textoMensajeAlerta = By.xpath("//android.widget.TextView[@text=\"La foto ha sido eliminada.\"]");
    private By iconoCerrarMensajeAlerta = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[2]");
    private By opcionDeshacer = By.xpath("//android.widget.TextView[@text=\"Deshacer\"]");
    /*
     * Localizadores Toma Fotografia 
     */
    private By btnAndroidCameraAlert = By.xpath("//android.widget.Button[@resource-id=\"com.android.permissioncontroller:id/permission_allow_foreground_only_button\"]");
    private By btnIconoAyuda = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[2]");
    private By btnFlechaAtrasFotografia = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[1]");
    private By btnCirculoTomarFotografia = By.xpath("(//android.view.ViewGroup[2]/android.view.ViewGroup)[1]");
    private By btnCirculoTomarDosOMasFotografias = By.xpath("//android.view.ViewGroup[2]/android.view.ViewGroup[2]");
    private By contabilizadorFotos = By.xpath("//android.widget.TextView[@text and not(contains(@text, \"Exhibiciones no autorizadas\"))]");
    private By btnFlechaSiguiente = By.xpath("(//android.view.ViewGroup[@resource-id=\"icon-button\"])[4]");
    /*
     * Localizadores Modal - Toma Fotografia 
     */
    
    private By btnCerrarModal = By.xpath("//android.widget.TextView[@text=\"Cerrar\"]");
    private By modalTitle = By.xpath("//android.widget.TextView[contains(@text, \"para toma de fotogra\")]");
    private By modalTextPointOne = By.xpath("//android.widget.TextView[contains(@text, \"Evita sombras y reflejos\")]");
    private By modalTextPointTwo = By.xpath("//android.widget.TextView[contains(@text, \"Sujeta bien el tel\")]");
    private By modalTextPointThree = By.xpath("//android.widget.TextView[contains(@text, \"Encuadre correcto\")]");
    private By modalTextPointFour = By.xpath("//android.widget.TextView[contains(@text, \"Sin obstrucciones\")]");
    private By modalTextPointFive = By.xpath("//android.widget.TextView[contains(@text, \"Antes de capturar\")]");
    
    
	/**
	 * Constructor de la clase Vista Exhibiciones no autorizadas
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public VistaExhNoAutorizadas(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
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
     * Ingresar un valor dentro del campo 'Especifica Cantidad'
     * @author Ivan Yakab
     * @param text Es el valor que ingresaremos dentro del campo 'Especifica cantidad'
     * @date 12/Septiembre/2025
     */
    public void ingresarValorCampoEspecificaCantidad(String text)
    {
    	waitForVisibility(campoEspecificaCantidad, 15);
    	au.driver.findElement(campoEspecificaCantidad).sendKeys(text);
    }
    
    /**
     * Valida que no exista el mensaje de error dentro del campo 'Especifica cantidad'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public boolean validarNoExistenciaMensajeErrorCampoEspecificaCantidad()
    {
    	return au.driver.findElements(errorMessageEspecificaCantidad).isEmpty();

    }
    
    /**
     * Valida que exista el mensaje de error dentro del campo 'Especifica cantidad'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public boolean validarExistenciaMensajeErrorCampoEspecificaCantidad()
    {	
    	waitForVisibility(errorMessageEspecificaCantidad, 15);
    	au.scrollToElement(errorMessageEspecificaCantidad);
    	return au.driver.findElement(errorMessageEspecificaCantidad).isDisplayed();

    }
    
    /**
     * Valida que no exista el mensaje de error dentro del campo 'Especifica Proveedor'
     * @author Ivan Yakab
     * @return boolean Retorna true si esta vacio o false si no esta Vacio
     * @date 12/Septiembre/2025
     */
    public boolean validarNoExistenciaMensajeErrorCampoEspecificaProveedor()
    {
    	return au.driver.findElements(errorMessageEspecificaProveedor).isEmpty();

    }
    
    /**
     * Valida que exista el mensaje de error dentro del campo 'Especifica Proveedor'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarExistenciaMensajeErrorCampoEspecificaProveedor()
    {	
    	waitForVisibility(errorMessageEspecificaProveedor, 15);
    	au.scrollToElement(errorMessageEspecificaProveedor);
    	au.driver.findElement(errorMessageEspecificaProveedor).isDisplayed();
    }
    
    /**
     * Valida que exista el mensaje de error dentro del campo 'Proveedor' cuando ingresamos menos caracteres o sobre pasamos los limites de longitud de caracteres
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarExistenciaMensajeErrorCampoEspecificaProveedorMenorA5OMayorA50()
    {	
    	waitForVisibility(errorMessageEspecificaProveedorMenorA5, 15);
    	au.scrollToElement(errorMessageEspecificaProveedorMenorA5);
    	au.driver.findElement(errorMessageEspecificaProveedorMenorA5).isDisplayed();
    }
    
    

    /**
     * Validar vista de la opcion 'Exhibiciones no autorizadas'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarVistaExhNoAutorizadas() throws InterruptedException {
    	waitForVisibility(subtitle, 15);
	    	au.driver.findElement(subtitle).isDisplayed();
	    	testCase.addScreenShotCurrentStep(au, "testImage");	
    }
    
    /**
     * Metodo para hacer click en el icono 'Flecha' 'atras' dentro de la vista 'Exhibiciones no autorizadas'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickFlechaAtras()
    {
    	waitForVisibility(btnFlechaAtras, 15);
    	au.driver.findElement(btnFlechaAtras).click();
    }
    
    /**
     * Metodo para hacer click en el campo 'Tipo de Exhibicion' dentro de la vista 'Exhibiciones no autorizadas'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickCampoSelectTipoExhibicion()
    {
    	waitForVisibility(campoTipoExhibicion, 15);
    	au.driver.findElement(campoTipoExhibicion).click();
    }

    /**
     * Metodo para hacer click en una opcion del campo "Tipo Exhibicion" dentro de la vista 'Exhibiciones no autorizadas'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void seleccionarOpcionTipoExhibicion(String opcion)
    {
    	opcionASeleccionar(opcion).click();
    	testCase.addScreenShotCurrentStep(au, "testImage");
    }
    
    /**
     * Metodo que retorna un webElement el cual utilizaremos en el metodo 'seleccionarOpcionTipoExhibicion'
     * @author Ivan Yakab
     * @param opcion Es la opcion a la que queremos acceder
     * @date 12/Septiembre/2025
     */
    private WebElement opcionASeleccionar(String opcion)
    {
    	return au.driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"" + opcion + "\"]"));
    }
    
    
    

    /**
     * Metodo para ingresar un texto dentro del campo 'Selecciona proveedor' dentro de la vista 'Exhibiciones no autorizadas'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void ingresaValorCampoProveedor(String nombreProveedor)
    {
    	waitForVisibility(campoEspecificaProveedor, 15);
    	au.driver.findElement(campoEspecificaProveedor).sendKeys(nombreProveedor);
    }
    

    

    /**
     * Metodo para hacer click en las letras 'Tomar foto' dentro de la vista 'Exhibiciones no autorizadas'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickCampoTomarFoto()
    {
    	waitForVisibility(btnFotografia, 15);
    	au.driver.findElement(btnFotografia).click();
    }
    
    /**
     * Metodo para validar que el btn finalizar se encuentre habilitado
     * @author Ivan Yakab
     * @return 
     * @date 12/Septiembre/2025
     */
    public String validarBtnFinalizarHabilitado()
    {
    	waitForVisibility(btnFinalizar, 15);
    	au.driver.findElement(btnFinalizar).isDisplayed();
    	return au.driver.findElement(btnFinalizar).getAttribute("enabled");
    }
    
    /**
     * Metodo para validar que el btn finalizar se encuentre inhabilitado
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public String validarBtnFinalizarInhabilitado()
    {
    	waitForVisibility(btnFinalizar, 15);
    	au.driver.findElement(btnFinalizar).isDisplayed();
    	return au.driver.findElement(btnFinalizar).getAttribute("enabled");
    }
    
    /**
     * Metodo para hacer click en el boton Finalizar
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickBtnFinalizar()
    {
    	waitForVisibility(btnFinalizar, 15);
    	au.driver.findElement(btnFinalizar).isDisplayed();
    	au.driver.findElement(btnFinalizar).click();
    }
    
    /**
     * Metodo para validar que se muestre la Alerta cuando el reporte se envia exitosamente
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarAlertaReporteEnviadoExitosamente()
    {
    	waitForVisibility(titulo, 15);
    	au.driver.findElement(titulo).isDisplayed();
    	au.driver.findElement(texto).isDisplayed();
    	au.driver.findElement(btnCerrar).isDisplayed();

    }
    
    /**
     * Metodo para validar que se muestre la Alerta cuando el reporte no se proceso correctamente
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarAlertaErrorAlProcesarReporte()
    {
    	au.driver.findElement(tituloError).isDisplayed();
    	au.driver.findElement(textoError).isDisplayed();
    	au.driver.findElement(btnCancelar).isDisplayed();
    	au.driver.findElement(btnReintentar).isDisplayed();
    }
    
    /**
     * Metodo para hacer click en el boton Cerrar
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickBtnCerrar()
    {
    	waitForVisibility(btnCerrar, 15);
    	au.driver.findElement(btnCerrar).isDisplayed();
    	au.driver.findElement(btnCerrar).click();
    }
    
    /**
     * Metodo para hacer click en el boton Cancelar
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickBtnCancelar()
    {
    	au.driver.findElement(btnCancelar).isDisplayed();
    	au.driver.findElement(btnCancelar).click();
    	testCase.addScreenShotCurrentStep(au, "testImage");
    }
    
    /**
     * Metodo para hacer click en el boton Reintentar
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickBtnReintentar()
    {
    	au.wait(8);
    	au.driver.findElement(btnReintentar).isDisplayed();
    	au.driver.findElement(btnReintentar).click();
    }
    

    /**
     * Metodo para hacer validar que exista el icono 'Flecha' 'Siguiente' dentro de la funcionalidad Toma de Fotografia
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarExistenciaBtnFlechaSiguiente()
    {
    	waitForVisibility(btnFlechaSiguiente, 15);
    	au.driver.findElement(btnFlechaSiguiente).isDisplayed();
    }
    
    /**
     * Metodo para hacer click en el boton 'Android Alert Allow Take Pictures'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void androidAlertAllowTakePictures()
    {
    	waitForVisibility(btnAndroidCameraAlert, 15);
	   	au.driver.findElement(btnAndroidCameraAlert).isDisplayed();
	   	au.driver.findElement(btnAndroidCameraAlert).click();
    }
    
    /**
     * Metodo para validar que se muestra el boton de Ayuda
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarVistaTomaDeFotografia() throws InterruptedException {
    	waitForVisibility(btnIconoAyuda, 15);
    	au.driver.findElement(btnIconoAyuda).isDisplayed();
    }
    
    /**
     * Metodo para hacer click en el boton de Ayuda
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickIconoAyuda() throws InterruptedException {
    	waitForVisibility(btnIconoAyuda, 15);
	    au.driver.findElement(btnIconoAyuda).click();
    }
    
    /**
     * Metodo para hacer click en el boton Tomar una fotografia
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickIconoTomarUnaFotografia() throws InterruptedException {
    	waitForVisibility(btnCirculoTomarFotografia, 15);
	    	au.driver.findElement(btnCirculoTomarFotografia).isDisplayed();
	    	au.driver.findElement(btnCirculoTomarFotografia).click();
    }
    
    /**
     * Metodo para hacer click en el boton icono de Tomar fotografia cuando queremos tomar mas de 2 fotografias
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickIconoTomarDosOMasFotografias(int cantidadDeClicks) throws InterruptedException {
	    	
	    	for (int i = 0; i < cantidadDeClicks; i++) {
	    		au.driver.findElement(btnCirculoTomarDosOMasFotografias).click();
	    		testCase.addTextEvidenceCurrentStep("Click Adicional num: " + i+1);
			}

    }
    
    /**
     * Metodo para hacer click en el icono 'Flecha' 'Siguiente' dentro de la funcionalidad Toma de Fotografia
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickBtnFlechaSiguiente()
    {
    	waitForVisibility(btnFlechaSiguiente, 15);
    	au.driver.findElement(btnFlechaSiguiente).click();
    }
    
    /**
     * Metodo que retorna la cantidad de numero de fotos tomadas
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     * @return numFotos cantidad de fotos tomadas
     */
    public String validarContabilizacionFotografias()
    {
    	waitForVisibility(contabilizadorFotos, 15);
    	au.driver.findElement(contabilizadorFotos).isDisplayed();
    	String numFotos = au.driver.findElement(contabilizadorFotos).getText();
    	return numFotos;
    }


    /**
     * Metodo para validar el texto dentro del Modal 'Guia para toma de fotografias' dentro de la funcionalidad 'Tomar fotografia'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarTextoModalGuiaTomaFotografias()
    {
    	waitForVisibility(btnCerrarModal, 15);
    	au.driver.findElement(btnCerrarModal).isDisplayed();
    	au.driver.findElement(modalTitle).isDisplayed();
    	au.driver.findElement(modalTextPointOne).isDisplayed();
    	au.driver.findElement(modalTextPointTwo).isDisplayed();
    	au.driver.findElement(modalTextPointThree).isDisplayed();
    	au.driver.findElement(modalTextPointFour).isDisplayed();
    	au.driver.findElement(modalTextPointFive).isDisplayed();
    }
    
    /**
     * Metodo para hacer click en el boton 'Cerrar' dentro del modal 'Guia para toma de fotografias'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickBtnCerrarModal()
    {
    	au.driver.findElement(btnCerrarModal).isDisplayed();
    	au.driver.findElement(btnCerrarModal).click();
    }
    
    /**
     * Metodo para hacer click en el icono 'Flecha' 'atras' dentro de la funcionalidad Toma de Fotografia
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickFlechaAtrasFotografia()
    {
    	waitForVisibility(btnFlechaAtrasFotografia, 15);
    	au.driver.findElement(btnFlechaAtrasFotografia).click();
    }

    /**
     * Metodo para validar que existan fotografias tomadas dentro de la vista "Exhibiciones no autorizadas"
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     * @return cantFotos cantidad de fotos tomadas
     */
    public int validarFotografiasTomadas()
    {
    	int cantFotos = au.driver.findElements(fotografias).size();
		System.out.println("Se han encontrado : " + cantFotos + " fotos");
		return cantFotos;
    }
    
    

    /**
     * Metodo para hacer click en el icono 'Basurero' dentro de la Exhibiciones no autorizadas
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickBtnBasurero()
    {
    	waitForVisibility(btnBorrarPrimerFoto, 15);
    	au.driver.findElement(btnBorrarPrimerFoto).isDisplayed();
    	au.driver.findElement(btnBorrarPrimerFoto).click();
    }
    

    /**
     * Metodo para hacer click en la opcion 'Deshacer' dentro del mensaje de alerta dentro de la vista Exhibiciones no autorizadas
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickOpcionDeshacer()
    {
    	au.driver.findElement(opcionDeshacer).isDisplayed();
    	au.driver.findElement(opcionDeshacer).click();
    }
    

    /**
     * Metodo para hacer click en el icono 'Re Tomar fotografia' dentro de la funcionalidad Toma de Exhibiciones no autorizadas
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickBtnRetomarFotografia()
    {
    	waitForVisibility(btnRetomarFotografia, 15);
    	au.driver.findElement(btnRetomarFotografia).isDisplayed();
    	au.driver.findElement(btnRetomarFotografia).click();
    }
    
    /**
     * Metodo para validar que NO  existan fotografias tomadas dentro de la vista "Exhibiciones no autorizadas"
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public boolean validarNoExistenciaDeFotografias()
    {
    	waitForVisibility(textoMensajeAlerta, 15);
    	au.driver.findElement(textoMensajeAlerta).isDisplayed();
    	int cantFotos = au.driver.findElements(fotografias).size();
    	if (cantFotos == 0)
    	{
    		System.out.println("Se han encontrado : " + cantFotos + " fotos");
        	testCase.addTextEvidenceCurrentStep("Se han encontrado : " + cantFotos + " fotos");
    		return true;
    	} else {
    		System.out.println("Se han encontrado : " + cantFotos + " fotos");
        	testCase.addTextEvidenceCurrentStep("Se han encontrado : " + cantFotos + " fotos");
    		return false;
    	}

		
    }


    /**
     * Metodo para validar que SI  existan fotografias tomadas dentro de la vista "Exhibiciones no autorizadas"
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarExistenciaDeFotografias()
    {
    	au.driver.findElement(textoMensajeAlerta).isDisplayed();
    	int cantFotos = au.driver.findElements(fotografias).size();
    	if (cantFotos > 0)
    	{
    		System.out.println("Se han encontrado : " + cantFotos + " fotos");
        	testCase.addTextEvidenceCurrentStep("Se han encontrado : " + cantFotos + " fotos");
    	} else {
    		System.out.println("Se han encontrado : " + cantFotos + " fotos");
        	testCase.addTextEvidenceCurrentStep("Se han encontrado : " + cantFotos + " fotos");
    		assertTrue(false);
    	}

		
    }
    
    /**
     * Metodo para validar que exista la Alerta 'La foto ha sido eliminada'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void validarExistenciaMensajeAlertaLaFotoHaSidoEliminada()
    {
    	au.driver.findElement(textoMensajeAlerta).isDisplayed();
    }
    
    /**
     * Metodo para hacer click en el boton 'Cerrar' dentro del mensaje de alerta 'La foto ha sido eliminada'
     * @author Ivan Yakab
     * @date 12/Septiembre/2025
     */
    public void clickCerrarMensajeAlertaLaFotoHaSidoEliminada()
    {
    	waitForVisibility(iconoCerrarMensajeAlerta, 15);
    	au.driver.findElement(iconoCerrarMensajeAlerta).isDisplayed();
    	au.driver.findElement(iconoCerrarMensajeAlerta).click();
    }
    
}

