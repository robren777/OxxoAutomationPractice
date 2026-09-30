package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EditarTipoPedidoIEP {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla 'Tipos de pedido > Editar' 
     * Pedidos > Tipos de pedido > Editar
	 ******************************************************************************************/

	/**
	 * Localizadores - Header de la P�gina
	 ******************************************************************************************/
	By xmlTituloPantalla = By.xpath("//div[@id='tituloEscritorio']/span");

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
 
    
    // Localizador del Boton 'Cancelar' y 'Guardar'
    By xmlBtnCancelar = By.xpath("//div[@class='contenedor']/div/div[2]/div/div[1]/button");
    By xmlBtnGuardar = By.xpath("//div[@class='contenedor']/div/div[2]/div/div[3]/button");
    
  
    
	/**
	 * Localizadores - Ventana '�Desea guardar los cambios aplicados?'
	 ******************************************************************************************/

 
    // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtnEliminar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[2]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    

    // Localizador checkbox Eliminacion Tipo de Pedido
    String StrCheckTipoPed = ("//div/table/tbody/tr/td[2]/a[contains(text(),'%s')]/ancestor::td/preceding-sibling::td/mat-checkbox");
 

	/**
	 * Localizadores - Ventana 'Imagen tipo de pedido'
	 ******************************************************************************************/

	
	By XmlVentanaImagen = By.xpath("//mat-dialog-container[@id='mat-dialog-0']");////h5[contains(text(),'Imagen del POP')]/ancestor::mat-dialog-container	
	By XmlLblSel_Archivo = By.xpath("//mat-dialog-container[@id='mat-dialog-0']/app-carga-imagen/form/div[1]/div/div[2]/input");
    By XmlImg = By.xpath("//mat-dialog-container[@id='mat-dialog-0']/app-carga-imagen//img");
	
	By XmlLblNombreImagen = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[1]/div/div[3]/mat-label[1]");
	By XmlLblFechaImagen = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[1]/div/div[3]/mat-label[2]");
	By XmlLblTamanoImagen = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[1]/div/div[3]/mat-label[3]");
	By XmlBtnCancelar = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[2]/div/div[2]/button");
	By XmlBtnAceptar = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[2]/div/div[3]/button");

	
	By xmlMsgCargaImg = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'Imagen cargada exitosamente')]");
	
	
    /**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase TipoPedidoIEP
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
    public EditarTipoPedidoIEP(SeleniumUtil u, TestCase testCase) {
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
	 * Metodo que valida se muestren la pantalla 'Tipos de pedido > Editar'
	 * @return True o false si se muestran la pantalla
	 * @author Andrea Contreras
	 */
	public Boolean visualizarPantalla() {

		boolean validacion = false;
		String pantallaActual = u.getText(xmlTituloPantalla);

		if (pantallaActual.contains("Tipos de pedido > Editar")) {
			validacion = true;
		}

		return validacion;
	}
	
	/**
	 * Metodo que valida se muestren los filtros de la pantalla 'Tipos de pedido > Editar'
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
	 * Metodo que valida se muestren los botones de la pantalla 'Tipos de pedido > Editar'
	 * @return True o false si se muestran los botones
	 * @author Andrea Contreras
	 */
	public Boolean visualizarBotones() {

		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
		boolean validacion = false, cancelar, guardar;

		u.mouseOver(xmlFiltros);
		cancelar = m.isEnable(xmlBtnCancelar);
		guardar = m.isEnable(xmlBtnGuardar);

		if (cancelar == true && guardar == true ) {
			validacion = true;
		}
		
		u.highLight(xmlBotones);
		testCase.addScreenShotCurrentStep(u, "verificarBotones");
		u.unhighLight(xmlBotones);

		return validacion;
	}
		

	/**
	 * Metodo que valida se muestren los filtros y botones de la pantalla 'Tipos de pedido > Editar'
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
     * Metodo para dar click en el boton 'Guardar'
	 * @author Andrea Contreras
	 */
    public void clickGuardarBtn(){ 
    	
    	m.explicitWaitToBeClickable(xmlBtnGuardar,Duration.ofSeconds(10));
	    u.highLight(xmlBtnGuardar);
	    testCase.addScreenShotCurrentStep(u, "btnGuardar");
	    u.click(xmlBtnGuardar);	
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
	    testCase.addScreenShotCurrentStep(u, "clickGuardar");
	    u.unhighLight(xmlBtnGuardar);
		
   }
    

	
	/*
	 * METODOS - Ventana 'Imagen tipo de pedido' 
	 ******************************************************************************************/
   
	
	
	/**
	* Metodo para validar mensaje 'Imagen cargada exitosamente'
	* @return true si se muestra la imagen o false si no se muestra
	* @author Andrea Contreras
	*/
	public boolean validarMsgPedidoEditado() {
		
		m.explicitWaitVisibilityOfElement(xmlMsgCargaImg, Duration.ofSeconds(10));
		
		boolean msgExist = false;
		if (m.existElement(xmlMsgCargaImg)==true) {				
			msgExist = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "msgImagenCargada");
		return msgExist;
	}
	

    


}
