package Mobile.Human.om;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;

import modelo.BaseExecution;
import modelo.TestCase;
import utils.AppiumUtil;

public class FormulariosTramites {
	
	/*
	 * Variables Globales
	 ******************************************************************************************/
	private TestCase testCase;
    private AppiumUtil au;
    private BaseExecution baseExecution;
	/*
	 * Localizadores Home Seccion Apps
	 ******************************************************************************************/
	private By opcionApps = By.xpath("//android.widget.TextView[@text=\"Apps\"]");
	private By appFormYTramites = By.xpath("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[1]/android.view.ViewGroup[1]");
	private By appsTitle = By.xpath("//android.widget.TextView[@text=\"Formularios y Tr�mites\"]");
	/*
	 * Localizadores Formularios y Tramites
	 ******************************************************************************************/
	private By buscadorTramite = By.className("android.widget.EditText"); // Buscar el tramite
	private By titleFormulario = By.xpath("//android.view.View[@text=\"Formularios y Tr�mites\"]");
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase Formularios_Tramites
	 * @author Ivan Yakab
	 * @param au (AppiumUtil): objeto de la clase 
	 * @param testCase (TestCase): objeto de la clase
	 */
    public FormulariosTramites(TestCase testCase, AppiumUtil au, BaseExecution baseExecution) {
        this.testCase = testCase;
        this.au = au;
        this.baseExecution = baseExecution;
    }
    
    /**
     * Metodo
	 ******************************************************************************************/
    /**
     * Busca dentro del apartado Formulario y Tramites, dentro de la barra de busqueda el Formulario y seleccionalo
	 *
     * @param tramiteABuscar El tramite que buscaremos en la barra de busqueda para que aparezca en el listado de tramites
     * @param tramiteASeleccionar El tramite que seleccionaremos, Nota: Es importante escribir el mismo nombre del tramite como aparece en el listado de tramites
     */
    public void opcionTramites(String tramiteABuscar, String tramiteASeleccionar) throws InterruptedException {
    	
		// ESPERA DE 15 SEGUNDOS - MAQUINA VIRTUAL 
		Thread.sleep(15000);
    	
		/************************ Paso 7 - Tap en el boton inferior "Apps" ***************************/
    	baseExecution.addStep("Tap en el boton inferior 'Apps'");
    		String assertTitleMenuApp = au.driver.findElement(opcionApps).getText();
    		assertEquals(assertTitleMenuApp,"Apps");
	    	au.click(opcionApps);
			testCase.addScreenShotCurrentStep(au, "testImage");
		
		/************************ Paso 8 - Tap en "Formularios y Tramites" ***************************/
	    baseExecution.addStep("Tap en Bot�n 'Formularios y Tramites");
			String assertTitleApps = au.driver.findElement(appsTitle).getText();
			assertEquals(assertTitleApps,"Formularios y Tr�mites");
	    	au.click(appFormYTramites);
			testCase.addScreenShotCurrentStep(au, "testImage");
		
		buscarEnBarraBusqueda(tramiteABuscar);
		seleccionarTramite(tramiteASeleccionar);
    }
    
    /**
     * Metodo
	 ******************************************************************************************/
    /**
     * Busca dentro del apartado Formulario y Tramites, dentro de la barra de busqueda el Formulario
	 *
     * @param nombreTramiteBuscado El tramite que buscaremos en la barra de busqueda para que aparezca en el listado de tramites
     */
    private void buscarEnBarraBusqueda(String nombreTramiteBuscado) throws InterruptedException {
		/************************ Paso 9 - Ingresar en la barra de Busqueda de formularios la palabra "infonavit" ***************************/
    	baseExecution.addStep("Tap en el Buscador del formulario y escribir tramite a buscar");
			String assertTitleFormulario = au.driver.findElement(titleFormulario).getText();
			assertEquals(assertTitleFormulario,"Formularios y Tr�mites");
	    	au.sendKeys(buscadorTramite, nombreTramiteBuscado);
			testCase.addScreenShotCurrentStep(au, "testImage");
			Thread.sleep(5000);
    }
    
    /**
     * Metodo
	 ******************************************************************************************/
    /**
     * Selecciona el tramite buscado dentro del listado
	 *
     * @param nombreTramiteSeleccionado El tramite que seleccionaremos dentro del listado de tramites
     */
    private void seleccionarTramite(String nombreTramiteSeleccionado) throws InterruptedException {
    	/************************ Paso 10 - Escoger tr�mite ***************************/
    	baseExecution.addStep("Tap en tramite buscado");
	    	By tramite = By.xpath("(//android.widget.TextView[@text=\"" + nombreTramiteSeleccionado + "\"])");
	    	String assertTramite = au.driver.findElement(tramite).getText();
	    	assertEquals(nombreTramiteSeleccionado,assertTramite);
	    	
			testCase.addScreenShotCurrentStep(au, "testImage");
	    	au.click(tramite);
	    	Thread.sleep(5000);
    }
    
    /**
     * Metodo
	 ******************************************************************************************/
    /**
     * Busca el elemento de cada campo, haz un scroll hacia ese elemento, obten su valor de ese campo y realiza un assert para validar el valor actual del campo vs el DataProvider	 *
     * @param nombreCampo El campo que buscaremos y realizaremos un assert para validar su valor actual vs el esperado (DataProvider)
     * @param nombreCampoEvidencia Sirve para dar referencia dentro de la evidencia (Carpeta: OUTPUT) al campo el cual realizaremos el assert
     */
 	public void validacionCampo(String nombreCampo, String nombreCampoEvidencia) {
 		By elemento = By.xpath("//android.widget.TextView[@text='" + nombreCampo  + "']");
 		au.scrollToElement(elemento);
 		String elementoGetText = au.driver.findElement(elemento).getText();
 		assertEquals(elementoGetText,nombreCampo);
		testCase.addScreenShotCurrentStep(au, "testImage");
 		testCase.addCodeEvidenceCurrentStep("Validacion campo " + "'" + nombreCampoEvidencia + "'\n"
 											+ " Valor Actual: " + elementoGetText + "\n"
 											+ " Valor Esperado: " + nombreCampo);
 	}
}




