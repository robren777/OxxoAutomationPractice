package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AsociarPopPlazasIEP {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	
	/*Localizadores Pantalla Asociar POP a Plazas
	 * Administraci�n > Asociar POP a Plazas
	 ******************************************************************************************/
	
	/**
	 * Localizadores - Header de la P�gina
	 ******************************************************************************************/
	By xmlTituloPantalla = By.xpath("//div[@id='tituloEscritorio']/span");
	
	/**
	 * Localizadores - Apartado 'Relacionar un POP con sus respectivas plazas'
	 ******************************************************************************************/
	
	By xmlInputs = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[1]/div");
	
	//Localizadores de lista desplegable Tipo de Pop
	By xmlLbTipoPop = By.xpath("//mat-select[@name='tipoPop']");
	By xmlTipoPopList = By.xpath("//form[@id='formFiltroPlazasPop']/div[1]/div/div[1]/mat-form-field/div/div/div/mat-select/div/div[2]");	
	By xmlTipoPopPanel = By.xpath("//form[@id='formFiltroPlazasPop']/div[1]//div[@class='form-group-input row'][1]//child::mat-select[@aria-disabled='false']");
	//By xmlTipoPopOp = By.xpath("//div[@id='mat-select-0-panel']/mat-option/span");
	
	//Localizadores de lista desplegable Pop
	By xmlLbPop = By.xpath("//mat-select[@name='pop']");
	By xmlPopList = By.xpath("//form[@id='formFiltroPlazasPop']/div[1]/div/div[2]/mat-form-field/div/div/div/mat-select/div/div[2]");
	By xmlPopPanel = By.xpath("//form[@id='formFiltroPlazasPop']/div[1]//div[@class='form-group-input row'][2]//child::mat-select[@aria-disabled='false']");
	//By xmlPopOp = By.xpath("//div[@id='mat-select-2-panel']/mat-option/span");
	By xmlPopActual = By.xpath("//mat-select[@name='pop']/div/div[1]/span/span");
	
	//Localizadores de Input Nombre de la Plaza
	By xmlLbNombrePlaza = By.xpath("//input[@name='nombrePlaza']");
	By xmlNombrePlaza = By.xpath("//form[@id='formFiltroPlazasPop']/div[1]//div[@class='form-group-input row'][3]//child::input");
	By xmlNombrePlazaPanel = By.xpath("//form[@id='formFiltroPlazasPop']/div[1]//div[@class='form-group-input row'][3]");

	//Localizadores del Grid 'Plazas filtradas'
	By xmlLbPlazasFiltradas = By.xpath("//mat-label[contains(text(),'Plazas filtradas')]");
	By xmlSectionPF = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[1]");
	By xmlPlazasFiltradas = By.xpath("//*[@id='formFiltroPlazasPop']/div[2]/div[1]/div[2]/section");
	By xmlPanelPFSelectAll = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[1]/div[2]/section/span[1]/mat-checkbox/label/span[1]/input");
	By xmlPanelPFOptions = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[1]/div[2]/section/span[2]/ul/div/li");

	//Localizadores del Grid 'Plazas Asociadas'
	By xmlLbPlazasAsociadas = By.xpath("//mat-label[contains(text(),'Plazas Asociadas')]");
	By xmlSectionPA = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[2]");
	By xmlPlazasAsociadas = By.xpath("//*[@id='formFiltroPlazasPop']/div[2]/div[2]/div[2]/section");
	By xmlPanelPAOptions = By.xpath("//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[2]/div[2]/section/span[2]/ul/div/li");

	
	//Localizadores de los Botones 'Guardar' y 'Cancelar'
	By xmlBtnGuardar = By.xpath("//form[@id='formFiltroPlazasPop']//span[contains(text(),'Guardar')]//parent::button");
	By xmlBtnCancelar = By.xpath("//form[@id='formFiltroPlazasPop']/div[3]/div/div[2]/button");
	By xmlBtnEliminar = By.xpath("//form[@id='formFiltroPlazasPop']/div[3]/div/div[1]/button");
	
	
	
	/**
	 * Localizadores - Apartado 'Consulta de POPs asociados'
	 ******************************************************************************************/
	By xmlLbConsultaPopAsociado = By.xpath("//mat-label[contains(text(),'Consulta de POPs asociados')]");
	
	//Localizadores del header de la tabla
    By xmlPopsAsColumns = By.xpath("//div[@class='contenedor']/div[2]/table/thead/tr/th");
    By xmlPopsAsRows = By.xpath("//div[@class='contenedor']/div[2]/table/tbody/tr");
	By xmlPopsAsChecks = By.xpath("//div[@class='contenedor']/div[2]/table/tbody/tr/td[1]/mat-checkbox/label/span[1]/input");
    By xmlPopsAsNumPlazas = By.xpath("//div[@class='contenedor']/div[2]/table/tbody/tr/td[4]");
	
	//Opciones con check seleccionado
	By xmlPopsAsIsCheckOptions = By.xpath("//div[@class='contenedor']/div[2]/table/tbody/tr/td[1]/mat-checkbox/label/span[1]/input[@aria-checked='true']");
	
	//Opciones con check no seleccionado
	By xmlPopsAsIsNotCheckOptions = By.xpath("//div[@class='contenedor']/div[2]/table/tbody/tr/td[1]/mat-checkbox/label/span[1]/input[@aria-checked='false']");
	
	//Opciones para cambiar de pagina en el Grid 'Consulta de POPs asociados'
	By xmlBtnFirstPage = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[1]");
	By xmlBtnFirstPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[1][@disabled='true']");
	//By xmlBtnPreviousPage = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[2]");
	By xmlBtnPreviousPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[2][@disabled='true']");
	By xmlBtnNextPage = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[3]");	
	By xmlBtnNextPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[3][@disabled='true']");
	//By xmlBtnLastPage = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[4]");	
	By xmlBtnLastPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[4][@disabled='true']");
	
	/**
	 * Localizadores - Ventana '�Desea guardar los cambios realizados?'
	 ******************************************************************************************/
	
	By xmlPopUpGuardar = By.xpath("//mat-dialog-container//child::h1[contains(text(),'�Desea guardar los cambios realizados?')]");
	By xmlPopUpGuardarText = By.xpath("//mat-dialog-container/app-global-confirm-dialog/h1[contains(text(),'�Desea guardar los cambios realizados?')]");
	By xmlCancelarCambios = By.xpath("//mat-dialog-container/app-global-confirm-dialog/div[2]/button[1]");
	By xmlGuardarCambios = By.xpath("//mat-dialog-container/app-global-confirm-dialog/div[2]/button[2]");
	
	By xmlMsgPPA = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'Se ha asociado plazas-pop exitosamente')]");
	By xmlMsgFPP = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'seleccione pop/plazas a asociar!')]");
	
	/**
	 * Localizadores - Ventana '�Esta seguro que desea eliminar las asociaci�nes del POP?'
	 ******************************************************************************************/
	
	By xmlPopUpEliminar = By.xpath("//mat-dialog-container/app-dialog-confirm//h5[contains(text(),' �Esta seguro que desea eliminar las asociaci�nes del POP?')]");
	By xmlCancelarElim = By.xpath("//mat-dialog-container/app-dialog-confirm/div[2]/div/div/button");
	By xmlConfirmarElim = By.xpath("//mat-dialog-container/app-dialog-confirm/div[2]/div/div/div/button");
	
	By xmlMsgCE = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'La asociaci�n pop/plazas se ha eliminado correctamente')]");
	
	
	/*
	 * Variables que recibiran un valor por parametro
	 ******************************************************************************************/
	String StrTipoPopOp2 = "//div[@id='mat-select-0-panel']/mat-option/span[contains(text(),'%s')]";
	String StrPopOp = "//div[@id='mat-select-2-panel']/mat-option/span[contains(text(),'%s')]";
	
	String PanelPFOp = "//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[1]/div[2]/section/span[2]/ul/div/li/mat-checkbox/label/span[2][contains(text(),'%s')]";	
	String PanelPAOp = "//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[2]/div[2]/section/span[2]/ul/div/li[contains(text(),'%s')]";
	String PanelPAOpDel = "//div[@id='cdk-accordion-child-0']/div/form/div[2]/div[2]/div[2]/section/span[2]/ul/div/li[contains(text(),'%s')]/mat-icon";
	
	String PopsOpcion = "//div[@class='contenedor']/div[2]/table/tbody/tr/td[contains(text(),'%s')]";
	String PopsSelectedCheck = "//div[@class='contenedor']/div[2]/table/tbody/tr/td[1]/mat-checkbox/label/span[1]/input[@aria-checked='true']/ancestor::td/following-sibling::td[1][contains(text(),'%s')]";
	String PopsNumPlazas = "//div[@class='contenedor']/div[2]/table/tbody/tr/td[2][contains(text(),'%s')]/following-sibling::td[2]";
	String PopsIconEdit = "//div[@class='contenedor']/div[2]/table/tbody/tr/td[2][contains(text(),'%s')]/following-sibling::td[3]/mat-icon";
	/*
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase AsociarPopPlazasIEP
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public AsociarPopPlazasIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u,testCase);
	}
	
	
	/*
	 * METODOS
	 ******************************************************************************************/
	 
	 /**
	 * M�todo que valida se muestren los filtros de Relacionar un POP con sus respectivas plazas
	 * @author Andrea Contreras
	 * @return Regresa true si se muestran los fitros o false si no se muestran
	 */ 
	public Boolean visualizarFiltros(){
		 
		boolean validacion = false, tipoP, pop, nomPop;
		 
		u.mouseOver(xmlPopPanel);
		tipoP = m.isEnable(xmlPopPanel);
        pop = m.isEnable(xmlTipoPopPanel);
        nomPop = m.isEnable(xmlNombrePlazaPanel);
        
        if(tipoP == true && pop == true && nomPop == true) {     
       	 validacion = true; 
        }
        
        return validacion;
   }
	 /**
	 * M�todo que valida se muestren las tablas de Relacionar un POP con sus respectivas plazas 'Plazas Filtradas' y 'Plazas Asociadas'
	 * @author Andrea Contreras
	 * @return Regresa true si se muestran las tablas o false si no se muestran
	 */ 
	public Boolean visualizarTablas(){
		 
		boolean validacion = false, areaPF, areaPA;
        
        u.mouseOver(xmlPlazasFiltradas);
        areaPF = m.isEnable(xmlPlazasFiltradas);
        areaPA = m.isEnable(xmlPlazasAsociadas);     
        
        if(areaPF == true && areaPA == true) {
       	 validacion = true; 
        }
        
        return validacion;
   }
	/**
	* M�todo que valida se muestren la pantalla 'Asociar POP a Plazas'
	* @author Andrea Contreras
	* @return Regresa true si se muestran la pantalla o false si no se muestran
	*/ 
   public Boolean visualizarPantalla(){
		 
		boolean validacion = u.isDisplayed(xmlTituloPantalla);        
        return validacion;
   }
	 
	
	/**
	* M�todo que valida se muestren los campos de Relacionar un POP con sus respectivas plazas y Relacionar un POP con sus respectivas plazas Consulta de POPs asociados con Selenium
	* @author Andrea Contreras
	* @param option (String): opci�n de pop a seleccionar
	* @return Regresa true si se muestran los campos o false si no se muestran
	*/
	public Boolean visualizarCampos(){
		 
		 m.explicitWaitToBeInvisible(Duration.ofSeconds(50));
		 
		 boolean validacion = false;
		 boolean pantalla = visualizarPantalla();
		 boolean filtros = visualizarFiltros();
         u.highLight(xmlTipoPopPanel);
         u.highLight(xmlPopPanel);       
         u.highLight(xmlNombrePlazaPanel);
         boolean tablas = visualizarTablas();
         u.highLight(xmlSectionPF);  
         u.highLight(xmlSectionPA); 
         
         testCase.addScreenShotCurrentStep(u, "verificarCampos");  
         
         u.unhighLight(xmlTipoPopPanel);
         u.unhighLight(xmlPopPanel);       
         u.unhighLight(xmlNombrePlazaPanel);   
         u.unhighLight(xmlSectionPF);  
         u.unhighLight(xmlSectionPA); 
         
         if(pantalla == true && filtros == true && tablas == true) {
        	 validacion = true; 
         }
                
         return validacion;
    }
	
    /**
	* Metodo para seleccionar el tipo de pop que su usara como filtro
	* @author Andrea Contreras
	* @param tipoPop (String): opci�n de pop a seleccionar
	* @return Regresa true si se selecciono la opci�n o false si no se selecciono
	*/
	public boolean selectTipoPop(String tipoPop) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		boolean existOption = false; 
		WebElement dropDownList =  m.explicitWaitToBeClickable(xmlTipoPopList, Duration.ofSeconds(30));
		u.hardWait(1);
		dropDownList.click();		
		By xpath = By.xpath(String.format(StrTipoPopOp2, tipoPop));
		
        if(m.existElement(xpath)) {
        	existOption = true; 
        	u.click(xpath);
        }
        
        m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
        testCase.addScreenShotCurrentStep(u, "selectTipoPop");	
		return existOption;
		
	}
	
    /**
	* Metodo para seleccionar el pop que su usara como filtro
	* @author Andrea Contreras
	* @param pop (String): pop a seleccionar
	* @return Regresa true si se selecciono la opci�n o false si no se selecciono
	*/
	public boolean selectPop(String pop) {	
		
		boolean existOption = false; 
		m.explicitWaitToBeInvisible(Duration.ofSeconds(30));
		WebElement element =  m.explicitWaitToBeClickable(xmlPopList, Duration.ofSeconds(10));
		element.click();
		String locator = String.format(StrPopOp, pop);
        if(m.existElement(By.xpath(locator))) {
        	existOption = true; 
        	u.click(By.xpath(locator));
        }
        m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		testCase.addScreenShotCurrentStep(u, "selectPop");	
		return existOption;
		
	}
	
    /**
	* Metodo para ingresar el nombre de la plaza a usar como filtro
	* @author Andrea Contreras
	* @param plaza (String): el nombre de la plaza a ingresar
	*/
	public void inputNombrePlaza(String plaza) {
		u.highLight(xmlNombrePlaza);
		u.sendKeys(xmlNombrePlaza, plaza);
		testCase.addScreenShotCurrentStep(u, "nombrePlaza");
		u.unhighLight(xmlNombrePlaza);
	}
	
    /**
	* Metodo para validar si se muestran resultados en el Grid despu�s de realizar una b�squeda 
	* @author Andrea Contreras
	* @return Regresa true si existen resultados en el Grid o false si no existen
	*/
	public boolean validarGrid() {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
	    m.scrollToElement(xmlLbConsultaPopAsociado); 
	    u.click(xmlLbConsultaPopAsociado);
		boolean exist = m.existMultipleElements(xmlPopsAsRows);
		//System.out.print("Validar grid --- "+exist+"---");
		testCase.addScreenShotCurrentStep(u, "validarGrid");
        return exist;
	}
	
    /**
	* Metodo para validar si se muestran resultados en los paneles despu�s de realizar una b�squeda
	* @return Regresa true si se muestran resultados o false si no se muestran
	* @author Andrea Contreras
	*/
	public boolean validarPaneles() {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		m.scrollToElement(xmlLbPlazasFiltradas); 
		u.click(xmlLbPlazasFiltradas);
		boolean exists =  false;
		if(m.existMultipleElements(xmlPanelPFOptions) == true && m.existElement(xmlPanelPFSelectAll) == true) {
			exists = true;
		}
		//System.out.print("Validar paneles --- "+exists+"---");
		testCase.addScreenShotCurrentStep(u, "validarPaneles");
		return exists;
	}
	
   /**
	* M�todo que valida si el Grid 'Consulta de POPs asociados' y el panel 'Plazas Filtradas' muestran resultados	 
	* @return Regresa true si se muestran resultados o false si no se muestran
	* @author Andrea Contreras
	*/
	public boolean validarGridPaneles(){
		boolean validacion =  false;
		if(validarGrid() == true && validarPaneles() == true) {
			validacion = true;
		}
		return validacion;
	}
	
   /**
	* M�todo para seleccionar N cantidad de plazas en la secci�n de 'Plazas filtradas' 
	* @param valores(ArrayList<String>): ArrayList de las plazas obtenidas del data provider
	* @return Regresa true si se selecciono la opci�n o false si no se selecciono
	* @author Andrea Contreras
	*/
	public boolean selectPlazaFiltradas(ArrayList<String> valores) {

		boolean isSelected = false;	
		int contPlazas = 0;
		
		m.scrollToElement(xmlLbPlazasFiltradas);
		u.mouseOver(xmlPlazasFiltradas);
		
		for(int i = 0; i < valores.size(); ++i){
			String locator = String.format(PanelPFOp,valores.get(i));
			By xpath = By.xpath(locator);
		    if(m.existElement(xpath)) {
		    	m.scrollToElement(xpath);
		    	u.highLight(xpath);
				testCase.addScreenShotCurrentStep(u, "selectPF1_"+i);				
	        	u.click(xpath);
	        	testCase.addScreenShotCurrentStep(u, "selectPF2_"+i);
	        	contPlazas++;
		      }
		}		
		
		if(contPlazas==valores.size()) {
			isSelected = true; 
		}
		return isSelected;	
		
	}
	
	
   /**
	* M�todo para validar que las plazas se muestren asociadas en la secci�n 'Plazas Asociadas'
	* @param valores(ArrayList<String>): ArrayList de las plazas obtenidas del data provider
	* @return Regresa true si se muestran asociadas o false si no se muestran asociadas
	* @author Andrea Contreras
	*/
	public boolean validarPlazasSeanAsociadas(ArrayList<String> valores) {
		
		boolean exist = false;
		int contPlazas = 0;
		u.mouseOver(xmlPlazasAsociadas);
		
		for(int i = 0; i < valores.size(); ++i){
			String locator = String.format(PanelPAOp,valores.get(i));
			By xpath = By.xpath(locator);
		    if(m.existElement(xpath)) {		    	
		    	 m.scrollToElement(xpath);
			     u.highLight(xpath);
			      
			     testCase.addScreenShotCurrentStep(u, "validarPA");
			     contPlazas++;
		      }
		}
		
		if(contPlazas==valores.size()) {
			exist = true; 
		}
		
		return exist;	
		
	}
	
   /**
	* M�todo para dar click en el bot�n 'Guardar'
	* @author Andrea Contreras
	*/
	public void clickGuardar(){ 
			
		List<WebElement> allOptions = u.getDriver().findElements(xmlBtnGuardar);
		
		for(WebElement option:allOptions){
			if(option.isDisplayed()) {					
				option.click(); 	
				testCase.addScreenShotCurrentStep(u, "clickGuardar");
							
			}
		}
	
	}
	
	/**
	* M�todo para validar que se muestra la ventana '�Esta seguro que desea eliminar las asociaci�nes del POP?'
	* @return Regresa true si se muestra la ventana o false si no se muestra
	* @author Andrea Contreras
	*/
	public boolean validarPopUpConfirmarCambios(){
		
		boolean exist = m.existElement(xmlPopUpGuardar);
		testCase.addScreenShotCurrentStep(u,"popUpConfirmarCambios");
		return exist;
		
	}

	/**
	* M�todo para dar click en el bot�n 'Confirmar' de la ventana '�Desea guardar los cambios realizados?'
	* @author Andrea Contreras
	*/
	public void clickBtnConfirmarPopUpGuardar(){ 
	
		m.explicitWaitToBeClickable(xmlGuardarCambios,Duration.ofSeconds(10));
		u.highLight(xmlGuardarCambios);
		testCase.addScreenShotCurrentStep(u, "clickConfirmar");
		u.click(xmlGuardarCambios);		
		testCase.addScreenShotCurrentStep(u, "msgConfirmar");
	}
	
	/**
	* M�todo para dar click en el bot�n 'Cancelar' de la ventana '�Desea guardar los cambios realizados?'
	* @author Andrea Contreras
	*/
	public void clickBtnCancelarPopUpGuardar(){ 
		
		m.explicitWaitToBeClickable(xmlCancelarCambios,Duration.ofSeconds(10));
		u.highLight(xmlCancelarCambios);
		testCase.addScreenShotCurrentStep(u, "clickCancelar");
		u.click(xmlCancelarCambios);		
		testCase.addScreenShotCurrentStep(u, "msgCancelar");
		
	}
	
	/**
	* M�todo para dar click en el bot�n 'Eliminar'
	* @author Andrea Contreras
	*/
	public void clickEliminar(){ 
		
		m.explicitWaitToBeClickable(xmlBtnEliminar,Duration.ofSeconds(10));
		u.highLight(xmlBtnEliminar);
		u.click(xmlBtnEliminar);		
		testCase.addScreenShotCurrentStep(u, "clickEliminar");
		u.unhighLight(xmlBtnEliminar);
		
	}
	
	/**
	* M�todo para validar que se muestra la ventana '�Esta seguro que desea eliminar las asociaci�nes del POP?'
	* @return Regresa true si se muestra la ventana o false si no se muestra
	* @author Andrea Contreras
	*/
	public boolean validarPopUpConfirmarEliminacion(){
		
		boolean exist = m.existElement(xmlPopUpEliminar);
		testCase.addScreenShotCurrentStep(u, "validarPopUpElim");
		return exist;
		
	}
	
	/**
	* M�todo para dar click en el bot�n 'Confirmar' de la ventana '�Esta seguro que desea eliminar las asociaci�nes del POP?'
	* @author Andrea Contreras
	*/
	public void clickBtnConfirmarPopUpEliminar(){ 
		
		m.explicitWaitToBeClickable(xmlCancelarElim,Duration.ofSeconds(10));
		u.highLight(xmlConfirmarElim);
		testCase.addScreenShotCurrentStep(u, "clickConfirmarElim");
		u.click(xmlConfirmarElim);		
	}
	
	/**
	* M�todo para dar click en el bot�n 'Cancelar' de la ventana '�Esta seguro que desea eliminar las asociaci�nes del POP?'
	* @author Andrea Contreras
	*/
	public void clickBtnCancelarPopUpEliminar(){ 
		
		m.explicitWaitToBeClickable(xmlCancelarElim,Duration.ofSeconds(10));
		u.highLight(xmlCancelarElim);
		u.click(xmlCancelarElim);		
		testCase.addScreenShotCurrentStep(u, "clickCancelarElim");
	}
	
	
	
	public boolean validarMsgFaltaPopPlaza() {
		m.explicitWaitVisibilityOfElement(xmlMsgFPP, Duration.ofSeconds(30));
		boolean msgExist = false;
		if (m.existElement(xmlMsgFPP)==true) {	
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgFaltanDatos");
		System.out.print("Existe mensaje-"+msgExist);
		return msgExist;
	}
	
	//Se valida si se muestra el mensaje 'Se ha asociado plazas-pop exitosamente'
	public boolean validarMsgPlazaPopAsoc() {
		//m.explicitWaitToBeInvisible(50);
		m.explicitWaitVisibilityOfElement(xmlMsgPPA, Duration.ofSeconds(70));
		boolean msgExist = false;
		if (m.existElement(xmlMsgPPA)==true) {				
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgPlazaAsoc");
		System.out.print("Existe mensaje-"+msgExist);
		return msgExist;
	}
	
	
	//Se valida si se muestra el mensaje 'Se ha asociado plazas-pop exitosamente'
	public boolean validarMsgEliminarPlazaPop() {
		m.explicitWaitVisibilityOfElement(xmlMsgCE, Duration.ofSeconds(30));
		boolean msgExist = false;
		if (m.existElement(xmlMsgCE)==true) {				
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgPopEliminar");
		System.out.print("Existe mensaje-"+msgExist);
		return msgExist;
	}
	
	//Se obtiene el numero de plazas que se encuentra en el grid 'Consulta de POPs asociados' del POP seleccionado 
	//en la busqueda de datos
	public int obtenerNumeroPlazasActual(String pop) {	
				
		m.explicitWaitInvisibilityOfElement(xmlMsgPPA, Duration.ofSeconds(30));			
		int numPlaza = 0;
		boolean click = false;
		By xpath = By.xpath(String.format(PopsNumPlazas,pop));		
		
		do {
			
			m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
			
			if(m.existElement(xpath)==true) {	    
				m.scrollToElement(xpath); 
	            u.highLight(xpath);
			    u.click(xpath);
			    numPlaza = Integer.valueOf(u.getText(xpath));
			    testCase.addScreenShotCurrentStep(u, "numPlazas");
			    u.unhighLight(xpath);
			    click = true;
			    System.out.print("-encontro el pop-");						
			}
			
			if(m.existElement(xpath)==false) {
				
			if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
				numPlaza = 0;
				click = true;
				System.out.print("-no existen plazas en el pop, se realiza break del while-");			
				break;				
				
			}else if(m.existElement(xmlBtnFirstPageDisable)==true && m.existElement(xmlBtnNextPageDisable)==false) {
				
    			  u.click(xmlBtnNextPage);
    			  m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    			  click = false;
    			  System.out.print("-cambia a la siguiente pagina para seguir buscando-");
    			
    			}else if (m.existElement(xmlBtnNextPageDisable)==true && m.existElement(xmlBtnFirstPageDisable)==false) {
   				
    				u.click(xmlBtnFirstPage);
    				m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    				click = false;
    				System.out.print("-cambia a la primera pagina para seguir buscando-");
    				
    	        }
    		}		
			
		}while(!click);
		
		System.out.print("NUM PLA-"+numPlaza);
		return numPlaza;	
		
	}
	
	public boolean validarAumNumPlazas(int numAntes, int numDespues) {
		
		boolean validarAumContP = false;
	    //System.out.print(Integer.valueOf(numAntes));
	    //System.out.print(Integer.valueOf(numDespues));
		if(Integer.valueOf(numAntes)==Integer.valueOf(numDespues)) {
			validarAumContP = true;
		}
		
		return validarAumContP;	
		
	}
	

	
	public boolean selectPopsAsociados(ArrayList<String> pops) {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	
		boolean isSelected = false, validacion = false;	
		int selectedPops = 0;
		
		for(int i = 0; i < pops.size(); ++i){
			
			String locator = String.format(PopsOpcion, pops.get(i));
			By element = By.xpath(locator);								
			
			do {
				
				if(m.existElement(element)==true) {
				
				u.highLight(element);
				String checkStr = locator+"/preceding-sibling::td/mat-checkbox";			
				u.click(By.xpath(checkStr));
				testCase.addScreenShotCurrentStep(u, "checkPopAsociado");
				//u.unhighLight(element);
				isSelected = true;
				selectedPops+=1;
				System.out.print("-encontro el pop-");			
				
				}
				
				if(m.existElement(element)==false) {
					
				if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
					System.out.print("-no existen plazas en el pop, se realiza break del while-");			
					break;				
					
				}else if(m.existElement(xmlBtnFirstPageDisable)==true && m.existElement(xmlBtnNextPageDisable)==false) {
					
	    			  u.click(xmlBtnNextPage);
	    			  m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    			  System.out.print("-cambia a la siguiente pagina para seguir buscando-");
	    			
	    			}else if (m.existElement(xmlBtnNextPageDisable)==true && m.existElement(xmlBtnFirstPageDisable)==false) {
	   				
	    				u.click(xmlBtnFirstPage);
	    				m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    				System.out.print("-cambia a la primera pagina para seguir buscando-");
	    				
	    	        }
	    		}		
				
			}while(!isSelected);
		
		}		
		
		if(selectedPops==pops.size()) {
			validacion = true;
		}
		
		return validacion;	
		
	}
	
	
	
	public boolean isEliminarBtnActive() {
		
		boolean isActive = false;	

		if(m.existElement(xmlBtnEliminar)) {
			u.highLight(xmlBtnEliminar);
			isActive = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "isDeleteBtnActive");

		return isActive;	
		
	}
	
	
	public boolean isSelectPopsAsoc(ArrayList<String> pops) {
		
		boolean isSelect = false, allSelect = false;	
		
		for(int i = 0; i < pops.size(); ++i){
			
			String locator = String.format(PopsSelectedCheck,pops.get(i));	
			String locatorStr = String.format(PopsOpcion,pops.get(i));
			
		    if(m.existElement(By.xpath(locator))) {
		    	u.highLight(By.xpath(locatorStr));
				isSelect = true; 
				
		      }
		    
		   // if(isSelect = true);
		    
		}	
		
		testCase.addScreenShotCurrentStep(u, "isSelectPA");
		
		return isSelect;	
		
	}
	
	public void clickEditarPop(String pop) {
		
		By element = By.xpath(String.format(PopsIconEdit, pop));
		boolean isSelected = false;
		
		do {
			
			if(m.existElement(element)==true) {
			
			u.highLight(element);		
			u.click(element);
			testCase.addScreenShotCurrentStep(u, "clickEditPopAsoc");
			isSelected = true;
			m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
			System.out.print("-encontro el pop-");			
			
			}
			
			if(m.existElement(element)==false) {
				
			if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
				isSelected = false;
				System.out.print("-no existen plazas en el pop, se realiza break del while-");			
				break;				
				
			}else if(m.existElement(xmlBtnFirstPageDisable)==true && m.existElement(xmlBtnNextPageDisable)==false) {
				
    			  u.click(xmlBtnNextPage);
    			  m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    			  isSelected = false;
    			  System.out.print("-cambia a la siguiente pagina para seguir buscando-");
    			
    			}else if (m.existElement(xmlBtnNextPageDisable)==true && m.existElement(xmlBtnFirstPageDisable)==false) {
   				
    				u.click(xmlBtnFirstPage);
    				m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    				isSelected = false;
    				System.out.print("-cambia a la primera pagina para seguir buscando-");
    				
    	        }
    		}		
			
		}while(!isSelected);	
		
	}
	
	public boolean validarPopActualSeleccionado(String pop) {
		
		u.mouseOver(xmlLbPop);
		testCase.addScreenShotCurrentStep(u, "popActual");
		boolean validacion = u.getText(xmlPopActual).equals(pop);
		
		return validacion;
	}
	
	public boolean validarActualizacionDatos(String popActuaizado) {
		
		boolean validacion = false;
		
		if(validarPopActualSeleccionado(popActuaizado)==true&&validarPaneles()==true) {
			validacion = true;
		}
		
		return validacion;
		
	}
	

	public void editarPopsPlazas(ArrayList<String> valores, String accion) {
		
		m.scrollToElement(xmlLbPlazasFiltradas);
		//int contPlazas = 0;
		String locator = null;		
		u.mouseOver(xmlPlazasAsociadas);
       
		for(int i = 0; i < valores.size(); ++i){
			
			if(accion.toUpperCase().equals("AGREGAR")) {
				locator = String.format(PanelPFOp,valores.get(i));			
				u.mouseOver(xmlPlazasFiltradas);
			}
			
	        if(accion.toUpperCase().equals("ELIMINAR")) {
	        	locator = String.format(PanelPAOpDel,valores.get(i));
	        	u.mouseOver(xmlPlazasAsociadas);
			}
	        
	        if(accion.equals(null)) {
	        	break;
			}
			       
            By xpath = By.xpath(locator);
			
		    if(m.existElement(xpath)) {
		    	m.scrollToElement(xpath);
		    	u.highLight(xpath);
				testCase.addScreenShotCurrentStep(u, "selectPF1_"+i);				
	        	u.click(xpath);
	        	testCase.addScreenShotCurrentStep(u, "selectPF2_"+i);
	        	//contPlazas++;
		      }
		}		
		
	}

	

	

}
