package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.ExcelUtil;
import utils.selenium.SeleniumUtil;

public class NuevaPlazaCcmIEP {
	
	SeleniumUtil u;
	TestCase testCase;
	ExcelUtil eu;
	MetodosExtra m;
	Date date;

	/*
	 * Localizadores Pantalla 'Consulta de Plazas CCM > Nuevo' 
     * Administracion > Consulta de plazas CCM > Nuevo
	 ******************************************************************************************/

	/**
	 * Localizadores - Header de la P�gina
	 ******************************************************************************************/
	By xmlTituloPantalla = By.xpath("//div[@id='tituloEscritorio']/span");

	/**
	 * Localizadores - Pantalla 'Consulta de plazas CCM > Nuevo'
	 ******************************************************************************************/

	By xmlFiltros = By.xpath("//div[@class='form-div divDetalle']/div[1]");
	By xmlBotones = By.xpath("//div[@class='form-div divDetalle']/div[2]/div");

	By xmlNombrePlazaCCM = By.xpath("//input[@id='mat-input-1']");

	// Lista desplegable 'Plaza'
	By xmlPlaza = By.xpath("//div[@class='form-div divDetalle']/div/div[2]/mat-form-field/div/div[1]");
	By xmlPlazaPanel = By.xpath("//div[@id='mat-select-4-panel']");
	By xmlPlazaOp = By.xpath("//div[@id='mat-select-4-panel']/mat-option/span");

	// Botones 'Cancelar', 'Limpiar', 'Guardar'
	By xmlCancelarBtn = By.xpath("//div[@class='form-div divDetalle']/div[2]/div/div[1]/button");
	By xmlLimpiarBtn = By.xpath("//div[@class='form-div divDetalle']/div[2]/div/div[2]/button");
	By xmlGuardarBtn = By.xpath("//div[@class='form-div divDetalle']/div[2]/div/div[3]/button");

	
	/**
	 * Localizadores - Ventana '�Desea guardar los cambios aplicados?'
	 ******************************************************************************************/
	
	By xmlPopUpGuardarCambios = By.xpath("//h5[contains(text(),'�Desea guardar los cambios aplicados?')]/ancestor::mat-dialog-container");
	By xmlPopUpCancelarBtn = By.xpath("//mat-dialog-container/app-dialog-confirm/div[2]/div/div/button");
	By xmlPopUpConfirmarBtn = By.xpath("//mat-dialog-container/app-dialog-confirm/div[2]/div/div/div/button");
	
	By xmlMsg = By.xpath("//snack-bar-container//span");
	By xmlMsgPlazaCcmCreada = By.xpath("//snack-bar-container//span[contains(text(),'La plaza ccm  se ha agregado correctamente')]");
	By xmlMsgFaltanDatos = By.xpath("//snack-bar-container//span[contains(text(),'Ingresar nombre de la plaza ccm y plaza')]");

	
	/*
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase NuevaPlazaCcmIEP
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public NuevaPlazaCcmIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.date = new Date();
		this.m = new MetodosExtra(u, testCase);
		
	}
	
	/*
	 * METODOS - Apartado 'Filtros'
	 ******************************************************************************************/
	
	//*************************************** VALIDACIONES *************************************
	
	/**
	 * Metodo que valida se muestren la pantalla 'Consulta de plazas CCM > Nuevo'
	 * @return True o false si se muestra la pantalla
	 * @author Andrea Contreras
	 */
	public Boolean visualizarPantalla() {

		boolean validacion = false;
		String pantallaActual = u.getText(xmlTituloPantalla);

		if (pantallaActual.contains("Consulta de plazas CCM > Nuevo")) {
			validacion = true;
		}

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los filtros de la pantalla 'Consulta de plazas CCM'
	 * @return True o false si se muestran los fiLtros
	 * @author Andrea Contreras
	 */
	public Boolean visualizarFiltros() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));

		boolean validacion = false, nomPlazaCCM, plaza;

		u.mouseOver(xmlFiltros);
		nomPlazaCCM = m.isEnable(xmlNombrePlazaCCM);
		plaza = m.isEnable(xmlPlaza);

		if (nomPlazaCCM == true && plaza == true) {
			validacion = true;
		}
		
		u.highLight(xmlFiltros);
		testCase.addScreenShotCurrentStep(u, "verificarFiltros");
		u.unhighLight(xmlFiltros);

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los botones de la pantalla 'Consulta de plazas CCM'
	 * @return True o false si se muestran los botones
	 * @author Andrea Contreras
	 */
	public Boolean visualizarBotones() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
		boolean validacion = false, cancelar, limpiar, guardar;

		u.mouseOver(xmlFiltros);
		cancelar = m.isEnable(xmlCancelarBtn);
		limpiar = m.isEnable(xmlLimpiarBtn);
		guardar = m.isEnable(xmlGuardarBtn);

		if (cancelar == true && limpiar == true && guardar == true ) {
			validacion = true;
		}
		
		u.highLight(xmlBotones);
		testCase.addScreenShotCurrentStep(u, "verificarBotones");
		u.unhighLight(xmlBotones);

		return validacion;
	}


	/**
	 * Metodo que valida se muestre la pantalla 'Consulta de plazas CCM > Nuevo', los filtros y los botones
	 * @return True o false si se muestra la pantalla, los filtros y los botones
	 * @author Andrea Contreras
	 */
	public Boolean visualizarCampos() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(50));

		boolean validacion = false;
		boolean pantalla = visualizarPantalla();
		boolean filtros = visualizarFiltros();
		boolean botones = visualizarBotones();

		if (pantalla == true && filtros == true && botones == true) {
			validacion = true;
		}

		return validacion;
	}
	
	//*************************************** FILTROS *************************************
	
	/**
	 * Metodo para ingresar un valor en el campo 'Nombre de plaza CCM'
	 * @param nombrePlaza (String): nombre de la plaza a ingresar
	 * @author Andrea Contreras
	 */
	public void typeNombrePlazaCCM(String nombrePlaza) {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));		
		u.highLight(xmlNombrePlazaCCM);
		u.sendKeys(xmlNombrePlazaCCM, nombrePlaza);
		testCase.addScreenShotCurrentStep(u, "typeNombrePlazaCCM");
		u.unhighLight(xmlNombrePlazaCCM);	
	}
	
	/**
	 * Metodo para seleccionar una plaza
	 * @param plaza (String): plaza a seleccionar
	 * @return True o false si se selecciono la opcion
	 * @author Andrea Contreras
	 */
	public boolean selectPlaza(String plaza) {

		boolean existOption = false;
		WebElement element = m.explicitWaitToBeClickable(xmlPlaza, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlPlazaOp);
		for (WebElement option : allOptions) {
			if (option.getText().equals(plaza)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.highLight(xmlPlaza);
		testCase.addScreenShotCurrentStep(u, "selectPlaza");
		u.unhighLight(xmlPlaza);
		return existOption;

	}
	
	
	//*************************************** BOTONES *************************************
	
    
	/**
     * Metodo para dar click en el boton 'Limpiar'
	 * @author Andrea Contreras
	 */
    public void clickLimpiarBtn(){ 
    	
    	u.scrollToElement(xmlTituloPantalla);
    	m.explicitWaitToBeClickable(xmlLimpiarBtn,Duration.ofSeconds(10));
	    u.highLight(xmlLimpiarBtn);
	    testCase.addScreenShotCurrentStep(u, "btnLimpiar");
	    u.click(xmlLimpiarBtn);	
	    m.explicitWaitToBeClickable(xmlLimpiarBtn,Duration.ofSeconds(10));
	    testCase.addScreenShotCurrentStep(u, "clickLimpiar");
	    u.unhighLight(xmlLimpiarBtn);
		
   }
    
	/**
     * Metodo para dar click en el boton 'Cancelar'
	 * @author Andrea Contreras
	 */
    public void clickCancelarBtn(){ 
    	
    	m.explicitWaitToBeClickable(xmlCancelarBtn,Duration.ofSeconds(10));
	    u.highLight(xmlCancelarBtn);
	    testCase.addScreenShotCurrentStep(u, "btnCancelar");
	    u.click(xmlCancelarBtn);	
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
	    testCase.addScreenShotCurrentStep(u, "clickCancelar");
	    u.unhighLight(xmlCancelarBtn);
		
   }
    
	/**
     * Metodo para dar click en el boton 'Guardar'
	 * @author Andrea Contreras
	 */
    public void clickGuardarBtn(){ 
    	
    	m.explicitWaitToBeClickable(xmlGuardarBtn,Duration.ofSeconds(10));
	    u.highLight(xmlGuardarBtn);
	    testCase.addScreenShotCurrentStep(u, "btnGuardar");
	    u.click(xmlGuardarBtn);	
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
	    testCase.addScreenShotCurrentStep(u, "clickGuardar");
	    u.unhighLight(xmlGuardarBtn);
		
   }
    
    
	/*
	 * METODOS - Ventana '�Desea guardar los cambios aplicados?'
	 ******************************************************************************************/

    /**
	* M�todo para validar que se muestra la ventana '�Desea guardar los cambios aplicados?'
	* @return True si se muestra la ventana o false si no se muestra
	* @author Andrea Contreras
	*/
	public boolean validarPopUpGuardarCambios(){
		
		boolean exist = m.existElement(xmlPopUpGuardarCambios);
		testCase.addScreenShotCurrentStep(u, "msgGuardarCambios");
		return exist;
		
	}
	
	/**
	* Metodo para dar click en el bot�n 'Confirmar' de la ventana '�Desea guardar los cambios realizados?'
	* @author Andrea Contreras
	*/
	public void clickBtnConfirmarPopUpGuardar(){ 
	
		m.explicitWaitToBeClickable(xmlPopUpConfirmarBtn,Duration.ofSeconds(10));
		u.highLight(xmlPopUpConfirmarBtn);
		testCase.addScreenShotCurrentStep(u, "clickConfirmar");
		u.click(xmlPopUpConfirmarBtn);		
		testCase.addScreenShotCurrentStep(u, "msgConfirmar");
	}
	
	/**
	* Metodo para validar que se muestra el mensaje 'La plaza ccm se ha agregado correctamente'
	* * @author Andrea Contreras
	*/
	public boolean validarMsgPlazaCcmCreada() {

		//m.explicitWaitVisibilityOfElement(xmlMsgPlazaCcmCreada, 20);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		boolean msgExist = false;
		if (m.existElement(xmlMsgPlazaCcmCreada)==true) {				
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgPlazaCcmCreada");
		System.out.print("\nExiste mensaje la plaza se ha agregado -"+msgExist);
		return msgExist;
	}
	
	/**
	* Metodo para validar que se muestra el mensaje con el texto indicado
	* @param
	* @return true o false si se muestra el mensaje con el texto indicado
	* @author Andrea Contreras
	*/
	public boolean validarMsgTexto(String texto) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    texto = texto.replaceAll("(\n|\r)", "");	
		boolean msgExist = false;
		
		if (m.existElement(xmlMsg)==true) {	
			String textoScreen = u.getText(xmlMsg).replaceAll("(\n|\r)", "");
			u.highLight(xmlMsg);
			if(textoScreen.equals(texto)) {
				msgExist = true;
			}
		}
		
		testCase.addScreenShotCurrentStep(u, "msg");
		System.out.print("\nExiste mensaje  -"+msgExist);
		return msgExist;
	}
	
	
	


}
