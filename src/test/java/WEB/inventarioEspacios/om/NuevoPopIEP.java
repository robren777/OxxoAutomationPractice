package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class NuevoPopIEP {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;

    /**
    * Localizadores y metodos de la pantalla Pops > Pops > Nuevo 
    *****************************************************************************************/
	
	/*
	 * Localizadores del header
	 *****************************************************************************************/
	By xmlTituloPantalla = By.xpath("//div[@id='tituloEscritorio']/span");
	
	By xmlFlechaDer = By.xpath("//mat-tab-header/div[1]");
	By xmlFlechaIzq = By.xpath("//mat-tab-header/div[3]");
	By xmlDatosGenerales = By.xpath("//div[@role='tab'][1]");
	By xmlPlazas = By.xpath("//div[@role='tab'][2]");
	By xmlProveedores = By.xpath("//div[@role='tab'][3]");
	
	/*
	 * Localizadores 'Datos Generales'
	 *****************************************************************************************/
	By contenido = By.xpath("//mat-tab-body[1]/div");
	
	By xmlTipoPop = By.xpath("//div[@class='row form-div2 ng-star-inserted']/div[1]/div[1]");
	By xmlTipoPopLt = By.xpath("//div[@role='listbox']");
	By xmlNombrePop = By.xpath("//div[@class='row form-div2 ng-star-inserted']/div[1]/div[2]/mat-form-field//input");
	By xmlEspecificacion = By.xpath("//div[@class='row form-div2 ng-star-inserted']/div[1]/div[3]/mat-form-field//input");
	By xmlDesc = By.xpath("//div[@class='row form-div2 ng-star-inserted']/div[1]/div[4]/mat-form-field//input");
	By xmlPrecio = By.xpath("//div[@class='row form-div2 ng-star-inserted']/div[1]/div[5]/mat-form-field//input");	
	By xmlComentario = By.xpath("//div[@class='row form-div2 ng-star-inserted']/div[1]/div[6]/mat-form-field//textarea");
	
	By xmlReqPrecioSi = By.xpath("//mat-radio-button[@id='mat-radio-2']");
	By xmlReqPrecioNo = By.xpath("//mat-radio-button[@id='mat-radio-3']");
	
	By xmlContenido = By.xpath("//div[@class='row form-div2 ng-star-inserted']/div[2]/div[3]/mat-form-field//input");
	By xmlSegmento = By.xpath("//div[@class='row form-div2 ng-star-inserted']/div[2]/div[4]/mat-form-field//input");	
	By xmlKit = By.xpath("//div[@class='row form-div2 ng-star-inserted']/div[2]/div[5]/mat-form-field//input");
	By xmlTipoNegocio = By.xpath("//div[@class='row form-div2 ng-star-inserted']/div[2]/div[6]/mat-form-field//input");
	
	By xmlPerfil1 = By.xpath("");	
	By xmlPerfil2 = By.xpath("");
	
	
	By xmlGuardarBtn = By.xpath("//mat-tab-body[1]/div/button[1]");
	By xmlLimpiarBtn = By.xpath("//mat-tab-body[1]/div/button[2]");
	By xmlCancelarBtn = By.xpath("//mat-tab-body[1]/div/button[3]");
	
	
	By xmlPopUpConfGuardado = By.xpath("//h1[contains(text(),'�Desea guardar los cambios realizados?')]//ancestor::mat-dialog-container");
	By XmlPopUpConfirmarBtn = By.xpath("//mat-dialog-container//button[2]");
	By xmlPopUpCancelarBtn = By.xpath("//mat-dialog-container//button[1]");
	
	By xmlMsgPopCreado = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'se ha generado un nuevo Pop')]");
	By xmlMsgPopExistente = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'Nombre de Pop ya existe')]");
	By xmlMsgPopActualizado = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'El Pop se ha actualizado correctamente')]");
	
	
	
	
	//Variables que reciben un valor por parametro
	String StrTipoPopOp = "//div[@role='listbox']/mat-option/span[contains(text(),'%s')]";
	
	/*
	 * Localizadores 'Plazas'
	 *****************************************************************************************/
	By xmlNombrePlaza = By.xpath("//mat-form-field//input");

	//Localizadores del Grid 'Plazas filtradas'
	By xmlPlazasFiltradasLb = By.xpath("//mat-label[contains(text(),'Plazas Filtradas')]");
	By xmlPlazasFiltradas = By.xpath("//mat-tab-body[@id='mat-tab-content-0-1']/div/div[2]/div[1]");	
	By xmlSectionPF = By.xpath("//mat-tab-body[@id='mat-tab-content-0-1']/div/div[2]/div[1]//section");
	By xmlSelectAllPF = By.xpath("//mat-tab-body[@id='mat-tab-content-0-1']/div/div[2]/div[1]//section/span[1]/mat-checkbox");
	By xmlPanelOptionsPF = By.xpath("//mat-tab-body[@id='mat-tab-content-0-1']/div/div[2]/div[1]//section/span[2]/ul/div/li");

	//Localizadores del Grid 'Plazas Asociadas'
	By xmlLbPlazasAsociadas = By.xpath("//mat-label[contains(text(),'Plazas Asociadas')]");
	By xmlSectionPA = By.xpath("//mat-tab-body[@id='mat-tab-content-0-1']/div/div[2]/div[2]//section");
	By xmlPlazasAsociadas = By.xpath("//mat-tab-body[@id='mat-tab-content-0-1']/div/div[2]/div[2]");
	By xmlPanelOptionsPA = By.xpath("//mat-tab-body[@id='mat-tab-content-0-1']/div/div[2]/div[2]//section/span/div/p");

	//Variables que reciben un valor por parametro
	String StrOptionPA = "//mat-tab-body[@id='mat-tab-content-0-1']/div/div[2]/div[2]//p[contains(text(),'%s')]";
	String StrOptionPF = "//mat-tab-body[@id='mat-tab-content-0-1']/div/div[2]/div[1]//mat-checkbox[@name='check']/label/span[2][contains(text(),'%s')]";
	
	/*
	 * Localizadores 'Proveedores'
	 ******************************************************************************/
	By xmlProveedor = By.xpath("//div[@class='row form-div2 ng-star-inserted']/div[1]/div[1]");
	By xmlProveedorLt = By.xpath("//div[@role='listbox']");
	By xmlProvedorOp = By.xpath("//div[@role='listbox']/mat-option/span");
	String StrProveedorOp = "//div[@role='listbox']/mat-option/span[contains(text(),'%s')]";
	
 
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase PopsNuevo
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public NuevoPopIEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
	}
	
	/*
	 * METODOS - Header
	 *****************************************************************************************/
	
	/**
	 * Metodo para seleccionar el apartado 'Datos Generales'
	 * @author Andrea Contreras
	 */
	public void clickDatosGeneralesTab() {
		
		m.explicitWaitToBeClickable(xmlDatosGenerales, Duration.ofSeconds(10));
		u.highLight(xmlDatosGenerales);
		u.click(xmlDatosGenerales);
		testCase.addScreenShotCurrentStep(u, "clickDatosGrls");	
		u.unhighLight(xmlDatosGenerales);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
	}
	
	/**
	 * Metodo para seleccionar el apartado 'Plazas'
	 * @author Andrea Contreras
	 */
	public void clickPlazasTab() {
		
		m.explicitWaitToBeClickable(xmlPlazas, Duration.ofSeconds(10));
		u.highLight(xmlPlazas);
		u.click(xmlPlazas);;
		testCase.addScreenShotCurrentStep(u, "clickPlazas");	
		u.unhighLight(xmlPlazas);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
	}
	
	/**
	 * Metodo para seleccionar el apartado 'Proveedores'
	 * @author Andrea Contreras
	 */
	public void clickProveedoresTab() {
		
		m.explicitWaitToBeClickable(xmlProveedores, Duration.ofSeconds(10));
		m.scrollToElement(xmlProveedores);
		u.highLight(xmlProveedores);
		u.click(xmlProveedores);;
		testCase.addScreenShotCurrentStep(u, "clickProveedores");	
		u.unhighLight(xmlProveedores);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
	}
	
	/*
	 * METODOS - Apartado 'Datos Generales'
	 ******************************************************************************************/
	
	/**
	* M�todo que valida se muestre la pantalla 'Pop Nuevo'
	* @author Andrea Contreras
	* @return Regresa true si se muestran la pantalla o false si no se muestran
	*/ 
   public Boolean visualizarPantalla(){
		 
		boolean validacion = false;
		
		if(u.getText(xmlTituloPantalla).contains("Pops > Nuevo")){
			validacion = true;
		}			
			
        return validacion;
   }
	
	
	/**
	* Metodo que valida se muestre la pantalla 'Pop Nuevo', y los campos y botones de la pantalla 'Datos Generales'
	* @author Andrea Contreras
	* @return Regresa true si se muestra la pantalla y los campos o false si no se muestran
	*/ 
	public boolean validarDatosGeneralesTab() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		
		boolean validacion = false, tipoPop, nomPop, espc, desc, precio, comentario, reqPsi, reqPno, 
				cont, segmento, kit, tipoNegocio, cancelarBtn, limpiarBtn, guardarBtn, pantalla;
		 
		pantalla = visualizarPantalla();
		tipoPop = m.isEnable(xmlTipoPop);
		nomPop = m.isEnable(xmlNombrePop);
		espc = m.isEnable(xmlEspecificacion);
		desc = m.isEnable(xmlDesc);
		precio = m.isEnable(xmlPrecio);
		comentario = m.isEnable(xmlComentario);
		reqPsi = m.isEnable(xmlReqPrecioSi);
		reqPno = m.isEnable(xmlReqPrecioNo);
		cont = m.isEnable(xmlContenido);
		segmento = m.isEnable(xmlSegmento);
		kit = m.isEnable(xmlKit);
		tipoNegocio = m.isEnable(xmlTipoNegocio);
		
		cancelarBtn = m.isEnable(xmlCancelarBtn);
		limpiarBtn = m.isEnable(xmlLimpiarBtn);
		guardarBtn = m.isEnable(xmlGuardarBtn);
        
        if(tipoPop == true && nomPop == true && espc == true  && desc == true && precio == true && comentario == true 
        		&& reqPsi == true && reqPno == true && cont == true && segmento == true && kit == true 
        		&& tipoNegocio == true && cancelarBtn == true && limpiarBtn == true && guardarBtn == true 
        		&& pantalla == true) {     
       	   validacion = true; 
        }
        
        u.highLight(contenido);
        testCase.addScreenShotCurrentStep(u, "validarDatosGrls");
        u.unhighLight(contenido);
        
        return validacion;
		
	}
	
	/**
	 * Metodo para seleccionar un pop
	 * @author Andrea Contreras
	 * @param pop (String): pop a seleccionar
	 */
	public void selectTipoPop(String pop) {

		WebElement dropDownList = m.explicitWaitToBeClickable(xmlTipoPop, Duration.ofSeconds(30));
		u.hardWait(1);
		dropDownList.click();

		String xpath = String.format(StrTipoPopOp, pop);
		By elemento = By.xpath(xpath);

		u.hardWait(3);
		m.explicitWaitVisibilityOfElement(elemento, Duration.ofSeconds(20));
		
		if (m.existElement(elemento)) {
			u.click(elemento);
			// retun true;
		}
		u.hardWait(1);
		
		u.highLight(xmlTipoPop);
		testCase.addScreenShotCurrentStep(u, "selectPeriodoPop");
		u.unhighLight(xmlTipoPop);
	}
	
	/**
	 * Metodo para escribir texto en el campo 'Nombre del Pop'
	 * @author Andrea Contreras
	 * @param nombre (String): nombre del pop a escribir
	 */
	public void typeNombrePop(String nombre) {
		
		u.highLight(xmlNombrePop);
		u.sendKeys(xmlNombrePop, nombre);
		testCase.addScreenShotCurrentStep(u, "typeNomPop");	
		u.unhighLight(xmlNombrePop);
	}
	
	/**
	 * Metodo para escribir texto en el campo 'Especificacion'
	 * @author Andrea Contreras
	 * @param esp (String): especificacion del pop a escribir
	 */
	public void typeEspecificacion(String esp) {
		
		u.highLight(xmlEspecificacion);
		u.sendKeys(xmlEspecificacion, esp);
		testCase.addScreenShotCurrentStep(u, "typeEspecificacion");		
		u.unhighLight(xmlEspecificacion);
		
	}
	
	/**
	 * Metodo para escribir texto en el campo 'Descripcion'
	 * @author Andrea Contreras
	 * @param desc (String): descripcion del pop a escribir
	 */
	public void typeDescripcion(String desc) {
		
		u.highLight(xmlDesc);
		u.sendKeys(xmlDesc, desc);
		testCase.addScreenShotCurrentStep(u, "typeDesc");	
		u.unhighLight(xmlDesc);
	}
	
	/**
	 * Metodo para escribir texto en el campo 'Precio'
	 * @author Andrea Contreras
	 * @param precio (String): precio del pop a escribir
	 */
	public void typePrecio(String precio) {
		
		u.highLight(xmlPrecio);
		u.click(xmlPrecio);
		u.sendKeys(xmlPrecio, precio);
		testCase.addScreenShotCurrentStep(u, "typePrecio");	
		u.unhighLight(xmlPrecio);
	}
	
	
	/**
	 * Metodo para escribir texto en el campo 'Comentario'
	 * @author Andrea Contreras
	 * @param comentario (String): comentario del pop a escribir
	 */
	public void typeComentario(String comentario) {
		u.highLight(xmlComentario);
		u.sendKeys(xmlComentario, comentario);
		testCase.addScreenShotCurrentStep(u, "typeComentario");	
		u.unhighLight(xmlComentario);
	}
	
	
	/**
	 * Metodo para seleccionar una opcion del check 'Requiere Precio'
	 * @author Andrea Contreras
	 * @param option (String): 'Si' o 'No'
	 */
	public void checkRequierePrecio(String option) {
		
		switch(option.toUpperCase()) {
		     case "SI":
		    	 u.highLight(xmlReqPrecioSi);
		    	 u.click(xmlReqPrecioSi);
		    	 testCase.addScreenShotCurrentStep(u, "typeComentario");
		    	 u.unhighLight(xmlReqPrecioSi);
		    	 break;
		    	 
		     case "NO":
		    	 u.highLight(xmlReqPrecioNo);
		    	 u.click(xmlReqPrecioNo);
		    	 testCase.addScreenShotCurrentStep(u, "typeComentario");
		    	 u.unhighLight(xmlReqPrecioNo);
		    	 break;
		    	 
		     case "default":
		    	 testCase.addScreenShotCurrentStep(u, "typeComentario");
		    	 break;
		
		}
		
	}
	
	
	/**
	 * Metodo para escribir texto en el campo 'Contenido'
	 * @author Andrea Contreras
	 * @param contenido (String): contenido del pop a escribir
	 */
	public void typeContenido(String contenido) {
		u.highLight(xmlContenido);
		u.sendKeys(xmlContenido, contenido);
		testCase.addScreenShotCurrentStep(u, "typeContenido");	
		u.unhighLight(xmlContenido);
	}
	
	
	
	/**
	 * Metodo para escribir texto en el campo 'Segmento'
	 * @author Andrea Contreras
	 * @param segmento (String): segmento del pop a escribir
	 */
	public void typeSegmento(String segmento) {
		u.highLight(xmlSegmento);
		u.sendKeys(xmlSegmento, segmento);
		testCase.addScreenShotCurrentStep(u, "typeSegmento");	
		u.unhighLight(xmlSegmento);
	}
	
	
	/**
	 * Metodo para escribir texto en el campo 'Kit'
	 * @author Andrea Contreras
	 * @param kit (String): kit del pop a escribir
	 */
	public void typeKit(String kit) {
		u.highLight(xmlKit);
		u.sendKeys(xmlKit, kit);
		testCase.addScreenShotCurrentStep(u, "typeKit");	
		u.unhighLight(xmlKit);
	}
	
	
	/**
	 * Metodo para escribir texto en el campo 'Tipo de negocio'
	 * @author Andrea Contreras
	 * @param tipo (String): tipo de negocio del pop a escribir
	 */
	public void typeTipoNegocio(String tipo) {
		u.highLight(xmlTipoNegocio);
		u.sendKeys(xmlTipoNegocio, tipo);
		testCase.addScreenShotCurrentStep(u, "typeTipoNegocio");	
		u.unhighLight(xmlTipoNegocio);
	}
	
	
	/**
	 * Metodo dar click en el boton 'Guardar'
	 * @author Andrea Contreras
	 */
	public void clickGuardar() {
		m.scrollToElement(xmlGuardarBtn);
		u.highLight(xmlGuardarBtn);
		testCase.addScreenShotCurrentStep(u, "clickGuardar1");
		u.click(xmlGuardarBtn);	
		testCase.addScreenShotCurrentStep(u, "clickGuardar2");
		u.unhighLight(xmlGuardarBtn);
	}
	
	
	
	/**
	 * Metodo dar click en el boton 'Limpiar'
	 * @author Andrea Contreras
	 */
	public void clickLimpiar() {
		u.highLight(xmlLimpiarBtn);
		testCase.addScreenShotCurrentStep(u, "clickLimpiar1");
		u.click(xmlLimpiarBtn);	
		testCase.addScreenShotCurrentStep(u, "clickLimpiar2");
		u.unhighLight(xmlLimpiarBtn);
	}

	
	/**
	 * Metodo dar click en el boton 'Cancelar'
	 * @author Andrea Contreras
	 */
	public void clickCancelar() {
		u.highLight(xmlCancelarBtn);
		testCase.addScreenShotCurrentStep(u, "clickCancelar1");
		u.click(xmlCancelarBtn);	
		testCase.addScreenShotCurrentStep(u, "clickCancelar2");
		u.unhighLight(xmlCancelarBtn);
	}
	
	
	/** Metodo dar click en el boton 'Confirmar' de la ventana modal '�Desea guardar los cambios realizados?'
	 * @author Andrea Contreras
	 */
	public void clickBtnConfirmarPopUp() {
		u.highLight(XmlPopUpConfirmarBtn);
		testCase.addScreenShotCurrentStep(u, "clickConfirmar1");
		u.click(XmlPopUpConfirmarBtn);		
		testCase.addScreenShotCurrentStep(u, "clickConfirmar2");
		u.hardWait(2);
	}
	
	/** Metodo para validar si se muestra el mensaje de 'se ha generado un nuevo Pop'
	 * @return Regresa true si se muestra el mensaje o false si no se muestra
	 * @author Andrea Contreras
	 */
	public boolean validarMsgPopCreado() {
		u.hardWait(1);
		boolean msgExist = false;
		if (m.existElement(xmlMsgPopCreado)==true) {				
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgPlazaAsoc");
		System.out.print("Existe mensaje pop creado-"+msgExist);
		return msgExist;
	}
	
	
	/** Metodo para validar si se muestra el mensaje de 'se ha generado un nuevo Pop'
	 * @return Regresa true si se muestra el mensaje o false si no se muestra
	 * @author Andrea Contreras
	 */
	public boolean validarMsgPopYaExiste() {
		u.hardWait(1);
		boolean msgExist = false;
		if (m.existElement(xmlMsgPopExistente)==true) {				
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgPlazaAsoc");
		System.out.print("Existe mensaje ya existe-"+msgExist);
		return msgExist;
	}
	
	
	/** Metodo para validar si se muestra el mensaje de 'El Pop se ha actualizado correctamente'
	 * @return Regresa true si se muestra el mensaje o false si no se muestra
	 * @author Andrea Contreras
	 */
	public boolean validarMsgPopActualizado() {
		//u.hardWait(1);
		m.explicitWaitVisibilityOfElement(xmlMsgPopActualizado, Duration.ofSeconds(20));
		boolean msgExist = false;
		if (m.existElement(xmlMsgPopActualizado)==true) {			
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgPlazaAsoc");
		System.out.print("Existe mensaje ya existe-"+msgExist);
		return msgExist;
	}
	
	
	
	/*
	 * METODOS - Apartado 'Plazas'
	 ******************************************************************************************/
	/**
	* M�todo que valida se muestre la pantalla 'Pop Nuevo'
	* @author Andrea Contreras
	* @return Regresa true si se muestran la pantalla o false si no se muestran
	*/ 
   public Boolean validarTabActual(){
		 
		boolean validacion = false;
		
		if(u.getText(xmlTituloPantalla).contains("Pops > Nuevo")){
			validacion = true;
		}			
			
        return validacion;
   }
	
	
	/**
	* Metodo que valida se muestren los campos de la pantalla 'Plazas'
	* @author Andrea Contreras
	* @return Regresa true si se muestra la pantalla y los campos o false si no se muestran
	*/ 	
	public boolean validarPlazasTab() {
        m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		
		boolean validacion = false, nomPlaza, plazasF, plazasA, pantallaPlazas;
		 
		pantallaPlazas = true;// m.isSelected(xmlPlazas);
		
		nomPlaza = m.isEnable(xmlNombrePlaza);	
		plazasF = m.isEnable(xmlPlazasFiltradas);
		plazasA = m.isEnable(xmlPlazasAsociadas);
        
        if(nomPlaza == true && plazasF == true && plazasA == true  
        		&& pantallaPlazas == true) {     
       	   validacion = true; 
        }
        
        u.highLight(xmlNombrePlaza);
        u.highLight(xmlPlazasFiltradas);
        u.highLight(xmlPlazasAsociadas);
        
        testCase.addScreenShotCurrentStep(u, "validarCamposPlazas");  
        
        u.unhighLight(xmlNombrePlaza);
        u.unhighLight(xmlPlazasFiltradas);
        u.unhighLight(xmlPlazasAsociadas);
        
        return validacion;
	}
	
	/**
	 * Metodo para escribir texto en el campo 'Nombre de plaza'
	 * @author Andrea Contreras
	 * @param plaza (String): plaza que se quiera asociar al pop
	 */
	public void typePlaza(String plaza) {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.highLight(xmlNombrePlaza);
		u.sendKeys(xmlNombrePlaza, plaza);
		testCase.addScreenShotCurrentStep(u, "typePlaza");	
		u.unhighLight(xmlNombrePlaza);
	}
	
	
	   /**
		* M�todo para seleccionar N cantidad de plazas en la secci�n de 'Plazas filtradas' 
		* @param valores(ArrayList<String>): ArrayList de las plazas obtenidas del data provider
		* @return Regresa true si se selecciono la opci�n o false si no se selecciono
		* @author Andrea Contreras
		*/
		public boolean selectPlazaFiltradas(ArrayList<String> valores) {

			m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
			
			boolean isSelected = false;	
			int contPlazas = 0;
			u.mouseOver(xmlSectionPF);
			
			for(int i = 0; i < valores.size(); ++i){
				System.out.print("entro al ciclo");
				String locator = String.format(StrOptionPF,valores.get(i));
				By xpath = By.xpath(locator);
			    if(m.existElement(xpath)) {
			    	System.out.print("entro al IF");
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
			u.mouseOver(xmlSectionPA);
			
			for(int i = 0; i < valores.size(); ++i){
				String locator = String.format(StrOptionPA,valores.get(i));
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
	
	/*
	 * METODOS - Apartado 'Proveedores'
	 ******************************************************************************************/
	/**
	* Metodo que valida se muestren los campos de la pantalla 'Plazas'
	* @author Andrea Contreras
	* @return Regresa true si se muestra la pantalla y los campos o false si no se muestran
	*/ 	
	public boolean validarProveedoresTab() {
        m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		
		boolean validacion = false, nomProv, pantallaProv;
		 
		pantallaProv = visualizarPantalla();		
		nomProv = m.isEnable(xmlProveedor);	
        
        if(nomProv == true && pantallaProv == true) {     
       	   validacion = true; 
        }
        
        u.highLight(xmlProveedor);      
        testCase.addScreenShotCurrentStep(u, "validarCampoProveedor");         
        u.unhighLight(xmlProveedor);
        
        return validacion;
	}
	
	
	/**
	 * Metodo para escribir texto en el campo 'Nombre de proveedor'
	 * @author Andrea Contreras
	 * @param proveedor (String): proveedor que se quiera asociar al pop
	 */
	public void selectProveedor(String proveedor) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		
		WebElement dropDownList = m.explicitWaitToBeClickable(xmlProveedor, Duration.ofSeconds(30));	
		u.hardWait(1);
		dropDownList.click();
		
		
		By locator = By.xpath(String.format(StrProveedorOp, proveedor));
		//System.out.print(String.format(StrProveedorOp, proveedor));
		//u.hardWait(3);
		m.explicitWaitToBeClickable(locator, Duration.ofSeconds(30));

		if (m.existElement(locator)) {
			u.click(locator);
			// retun true;
		}
		
		u.hardWait(1);
		
		u.highLight(xmlProveedor);
		testCase.addScreenShotCurrentStep(u, "selectProveedorPop");
		u.unhighLight(xmlProveedor);

	}
	
	
	 public Boolean selectOption(String proveedor) { 
			Boolean existOption = false;
			WebElement element =  m.explicitWaitToBeClickable(xmlProveedor, Duration.ofSeconds(30));
			u.hardWait(2);
			element.click();
			List<WebElement> allOptions = u.getDriver().findElements(xmlProvedorOp);
			for(WebElement option:allOptions){
				if(option.getText().equals(proveedor)) {//busca la opcion que se pasa por parametro
				  u.hardWait(1);
				  option.click(); //da clic a esa opcion 
				  u.hardWait(1);
				  existOption = true;
				  break;
				}
			}
			
			u.highLight(xmlProveedor);
			testCase.addScreenShotCurrentStep(u, "selectOption");
			u.unhighLight(xmlProveedor);
			return existOption;
		}
	 
	

	
	
}
