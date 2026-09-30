package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class NuevoTipoPedidoIEP {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla 'Tipos de pedido > Nuevo' 
     * Pedidos > Tipos de pedido > Nuevo
	 ******************************************************************************************/

	/**
	 * Localizadores - Header de la P�gina
	 ******************************************************************************************/
	By xmlTituloPantalla = By.xpath("//div[@id='tituloEscritorio']/span");
	By xmlLoading = By.xpath("//div[@class = 'progress-loader'][@hidden]");

	/**
	 * Localizadores - Apartado 'Filtros'
	 ******************************************************************************************/ 
	By xmlFiltros = By.xpath("//div[@class='contenedor']/div/div[1]");
	By xmlBotones = By.xpath("//div[@class='contenedor']/div/div[2]/div");
	
	
    // Localizador  'Nombre de Pedido'
    By xmlNombreTipoPed= By.name("nombreTipo");
    
    // Localizador 'Periodos Visibles'
    By xmlPeriodosVis= By.name("periodoTipo");
    
    
	// Lista desplegable 'Tipo de Pop'
	By xmlTipoPop = By.xpath("//div[@class='contenedor']/div/div[1]/div[3]/mat-form-field/div/div[1]");
	By xmlTipoPopPanel = By.xpath("//div[@role='listbox']");
	By xmlTipoPopOp = By.xpath("//div[@id='mat-select-2-panel']/mat-option/span");
    
    // Localizador 'Comentario'
    By xmlComentario= By.name("comentario");
    

 
    // Localizador del Boton Cancelar, Limpiar, Guardar
    By xmlBtnCancelar = By.xpath("//div[@class='contenedor']/div/div[2]/div/div[1]/button");
    By xmlBtnLimpiar = By.xpath("//div[@class='contenedor']/div/div[2]/div/div[2]/button");
    By xmlBtnGuardar = By.xpath("//div[@class='contenedor']/div/div[2]/div/div[3]/button");
    
	/**
	 * Localizadores - Ventana '�Desea guardar los cambios aplicados?'
	 ******************************************************************************************/
	
	By xmlPopUpGuardarCambios = By.xpath("//h5[contains(text(),'�Desea guardar los cambios aplicados?')]/ancestor::mat-dialog-container");
	By xmlPopUpCancelarBtn = By.xpath("//mat-dialog-container/app-dialog-confirm/div[2]/div/div/button");
	By xmlPopUpConfirmarBtn = By.xpath("//mat-dialog-container/app-dialog-confirm/div[2]/div/div/div/button");
	
	By xmlMsgTipoPedidoCreado = By.xpath("//snack-bar-container//span[contains(text(),'El tipo de pedido se ha agregado correctamente')]");
	
    
    
    
    /**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase NuevoTipoPedidoIEP
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
    public NuevoTipoPedidoIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
    
    /*
	 * METODOS - Apartado 'Filtros'
	 ******************************************************************************************/
	
	//*************************************** VALIDACIONES *************************************
	
	/**
	 * Metodo que valida se muestren la pantalla 'Tipos de pedido > Nuevo'
	 * @return True o false si se muestran la pantalla
	 * @author Andrea Contreras
	 */
	public Boolean visualizarPantalla() {
		boolean validacion = false;
		String pantallaActual = u.getText(xmlTituloPantalla);

		if (pantallaActual.contains("Tipos de pedido > Nuevo")) {
			validacion = true;
		}

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los filtros de la pantalla 'Tipos de pedido > Nuevo'
	 * @return True o false si se muestran los fitros
	 * @author Andrea Contreras
	 */
	public Boolean visualizarFiltros() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));

		boolean validacion = false, nombre, periodVis, tipoPop, comentario;

		u.mouseOver(xmlFiltros);
		nombre = m.isEnable(xmlNombreTipoPed);
		periodVis = m.isEnable(xmlPeriodosVis);
		tipoPop = m.isEnable(xmlTipoPop);
		comentario = m.isEnable(xmlComentario);
		
		if (nombre == true && periodVis == true && tipoPop == true && comentario == true) {
			validacion = true;
		}
		
		u.highLight(xmlFiltros);
		testCase.addScreenShotCurrentStep(u, "verificarFiltro");
		u.unhighLight(xmlFiltros);

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los botones de la pantalla 'Tipos de pedido > Nuevo'
	 * @return True o false si se muestran los botones
	 * @author Andrea Contreras
	 */
	public Boolean visualizarBotones() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
		boolean validacion = false, cancelar, limpiar, guardar;

		u.mouseOver(xmlFiltros);
		cancelar = m.existElement(xmlBtnCancelar);
		limpiar = m.isEnable(xmlBtnLimpiar);
		guardar = m.isEnable(xmlBtnGuardar);

		if (cancelar == true && limpiar == true && guardar == true ) {
			validacion = true;
		}
		
		u.highLight(xmlBotones);
		testCase.addScreenShotCurrentStep(u, "verificarBotones");
		u.unhighLight(xmlBotones);

		return validacion;
	}
	


	/**
	 * Metodo que valida se muestren los filtros y botones de la pantalla 'Tipos de pedido > Nuevo'
	 * @return True o false si se muestran los filtro y botones
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
	 * Metodo para ingresar un valor en el campo 'Nombre del Tipo de Pedido'
	 * @param nomTipoPed (String): nombre del tipo de pedido a ingresar
	 * @author Andrea Contreras
	 */
	public void typeNombre(String nomTipoPed) {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));		
		u.highLight(xmlNombreTipoPed);
		u.sendKeys(xmlNombreTipoPed, nomTipoPed);
		testCase.addScreenShotCurrentStep(u, "typeNombre");
		u.unhighLight(xmlNombreTipoPed);	
	}
	
	/**
	 * Metodo para ingresar un valor en el campo 'PeriodosVisibles'
	 * @param numPeriod (String): numero de periodos visibles a ingresar
	 * @author Andrea Contreras
	 */
	public void typePeriodosVisibles(String numPeriod) {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));		
		u.highLight(xmlPeriodosVis);
		u.clear(xmlPeriodosVis);
		u.sendKeys(xmlPeriodosVis, numPeriod);
		testCase.addScreenShotCurrentStep(u, "typePeriodosVisibles");
		u.unhighLight(xmlPeriodosVis);	
	}
	
	/**
	 * Metodo para seleccionar el tipo del POP
	 * @param tipoPop (String): plaza a seleccionar
	 * @return True o false si se selecciono la opcion
	 * @author Andrea Contreras
	 */
	public boolean selectTipoPop(String tipoPop) {

		boolean existOption = false;
		WebElement element = m.explicitWaitToBeClickable(xmlTipoPop, Duration.ofSeconds(30));
		u.hardWait(2);
		element.click();
		List<WebElement> allOptions = u.getDriver().findElements(xmlTipoPopOp);
		for (WebElement option : allOptions) {
			if (option.getText().equals(tipoPop)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}

		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.highLight(xmlTipoPop);
		testCase.addScreenShotCurrentStep(u, "selectTipoPOP");
		u.unhighLight(xmlTipoPop);
		return existOption;

	}
	
	/**
	 * Metodo para ingresar un valor en el campo 'Comentario'
	 * @param comentario (String): comentario a ingresar
	 * @author Andrea Contreras
	 */
	public void typeComentario(String comentario) {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));		
		u.highLight(xmlComentario);
		//u.clear(xmlComentario);
		u.sendKeys(xmlComentario, comentario);
		testCase.addScreenShotCurrentStep(u, "typeComentario");
		u.unhighLight(xmlComentario);	
	}

  

	
	//*************************************** BOTONES *************************************

	/**
     * Metodo para dar click en el boton 'Cancelar'
	 * @author Andrea Contreras
	 */
    public void clickCancelarBtn(){ 
    	
    	m.explicitWaitToBeClickable(xmlBtnCancelar,Duration.ofSeconds(10));
	    u.highLight(xmlBtnCancelar);
	    testCase.addScreenShotCurrentStep(u, "btnCancelar");
	    u.click(xmlBtnCancelar);	
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
	    testCase.addScreenShotCurrentStep(u, "clickCancelar");
	    u.unhighLight(xmlBtnCancelar);
		
   }
	/**
     * Metodo para dar click en el boton 'Limpiar'
	 * @author Andrea Contreras
	 */
    public void clickLimpiarBtn(){ 
    	
    	u.scrollToElement(xmlTituloPantalla);
    	m.explicitWaitToBeClickable(xmlBtnLimpiar,Duration.ofSeconds(10));
	    u.highLight(xmlBtnLimpiar);
	    testCase.addScreenShotCurrentStep(u, "btnLimpiar");
	    u.click(xmlBtnLimpiar);	
	    m.explicitWaitToBeClickable(xmlBtnLimpiar,Duration.ofSeconds(10));
	    testCase.addScreenShotCurrentStep(u, "clickLimpiar");
	    u.unhighLight(xmlBtnLimpiar);
		
   }
    
	/**
     * Metodo para dar click en el boton 'Guardar'
	 * @author Andrea Contreras
	 */
    public void clickGuardarBtn(){ 
    	
    	m.explicitWaitToBeClickable(xmlBtnGuardar,Duration.ofSeconds(10));
	    u.highLight(xmlBtnGuardar);
	    testCase.addScreenShotCurrentStep(u, "btnGuardar");
	    u.click(xmlBtnGuardar);	
	    m.explicitWaitToBeInvisible2(Duration.ofSeconds(15));
	    testCase.addScreenShotCurrentStep(u, "clickGuardar");
	    u.unhighLight(xmlBtnGuardar);
		
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
		//testCase.addScreenShotCurrentStep(u, "msgGuardarCambios");
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
	}
	
	/**
	* Metodo para validar que se muestra el mensaje 'El tipo de pedido se ha agregado correctamente'
	* * @author Andrea Contreras
	*/
	public boolean validarMsgTipoPedidoCreado() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		boolean msgExist = false;
		if (m.existElement(xmlMsgTipoPedidoCreado)==true) {		
			u.highLight(xmlMsgTipoPedidoCreado);
			msgExist = true;
			u.unhighLight(xmlMsgTipoPedidoCreado);
		}
		testCase.addScreenShotCurrentStep(u, "msgPlazaCcmCreada");
		System.out.print("Mensaje Tipo Pop se ha agregado -"+msgExist+"\n");
		return msgExist;
	}
	

    
    

    


}
