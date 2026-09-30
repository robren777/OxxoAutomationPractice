package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.ArrayList;
//import org.apache.bcel.generic.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class DetallePops {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;

	/*
	 * Localizadores Pantalla Detalle - POPs
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
	By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
	By xmlPerfil = By.xpath("");

	By xmlTituloPantalla = By.xpath("//div[@id='tituloEscritorio']/span");
	
	By xmlFiltros = By.xpath("//div[@role='region']//form/div[1]");

	By XmlNombrePop = By.xpath("//*[@id=\"mat-input-0\"]");
	By XmlFiltNomPop = By.xpath("//*[@id=\"formFiltroPop\"]/div[1]/div[1]/div[1]");
	                            //*[@id="formFiltroPop"]/div[1]/div[1]/div[1]

	By XmlperiodoPop = By.xpath("//*[@id=\"mat-select-value-5\"]/span");
	By XmlperiodoPopF = By.xpath("//*[@id=\"mat-select-4\"]/div/div[2]");
	By XmlFiltPerioPop = By.xpath("//*[@id=\"formFiltroPop\"]/div[1]/div[2]/div[1]");
	By XmlPeriodoPopSPerio = By.xpath("//*[@id=\"mat-option-4\"]/span");

	By XmlProveedorPop = By.xpath("//*[@id=\"formFiltroPop\"]/div[1]/div[1]/div[2]/mat-form-field/div");

	By XmlProveedorPopSelec = By.xpath("//div[@id=\"mat-select-0-panel\"]/mat-option/span[contains(text(),\"ONTIME\")]");

	By XmlFiltProvPop = By.xpath("//*[@id=\"formFiltroPop\"]/div[1]/div[1]/div[2]");

	By xmlTipoPopSpan = By.xpath("//*[@id='formFiltroPop']/div[1]/div[2]/div[2]/mat-form-field//mat-select/div/div/span");
	By XmlTipoPop = By.xpath("//*[@id=\"formFiltroPop\"]/div[1]/div[2]/div[2]/mat-form-field/div");
	By XmlTipoPopF = By.xpath("//*[@id=\"mat-select-6\"]/div/div[2]");
	By XmlTipoPopSelec = By.xpath("//div[@id=\"mat-select-6-panel\"]/mat-option/span[contains(text(),\"Pop PromociÃ³n\")]");

	By XmlFiltTipoPop = By.xpath("//*[@id=\"formFiltroPop\"]/div[1]/div[2]/div[2]");
	By XmlLbtTotReg = By.xpath("//*[@id=\"content-nav\"]/app-pops-list/div/mat-paginator/div/div/div[2]/div");

	By XmlEstatusF = By.xpath("//form[@id='formFiltroPop']/div[1]/div[1]/div[3]//mat-select/div/div[2]");
	By XmlEstatus = By.xpath("//form[@id='formFiltroPop']/div[1]/div[1]/div[3]");
	
	By XmlBtnBuscar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[4]/button");
	By xmlBtnLimpiar = By.xpath("//form[@id='formFiltroPop']/div[2]//div[3]/button");
	By xmlBtnEliminar = By.xpath("//form[@id='formFiltroPop']/div[2]//div[2]/button");
	By xmlBtnNuevo = By.xpath("//form[@id='formFiltroPop']/div[2]//div[1]/button");
	
	
	// paso 5
	By XmlImagen = By.xpath("//*[@id=\"content-nav\"]/app-pops-list/div/div/table/tbody/tr[1]/td[13]/mat-icon[2]");
	
	// paso 6
	By XmlVentanaImagen = By.xpath("//*[@id=\"mat-dialog-0\"]");////h5[contains(text(),'Imagen del POP')]/ancestor::mat-dialog-container	
	By XmlLblSel_Archivo = By.xpath("//*[@id=\"mat-dialog-0\"]/app-carga-imagen/form/div[1]/div/div[2]/input");
    By XmlImg = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen//img");
	
	By XmlLblNombreImagen = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[1]/div/div[3]/mat-label[1]");
	By XmlLblFechaImagen = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[1]/div/div[3]/mat-label[2]");
	By XmlLblTamanoImagen = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[1]/div/div[3]/mat-label[3]");
	By XmlBtnCancelar = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[2]/div/div[2]/button");
	By XmlBtnAceptar = By.xpath("//*[@id='mat-dialog-0']/app-carga-imagen/form/div[2]/div/div[3]/button");

	
	By xmlMsgCargaImg = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'Imagen cargada exitosamente')]");
	// Paso 10
	// Nota:
	
	/* Ventana '�Esta seguro que desea eliminar los Pops seleccionados?'
	 ******************************************************************************************/
	By xmlPopUpEliminar = By.xpath("//mat-dialog-container/app-dialog-confirm//h5[contains(text(),'�Est� seguro que desea eliminar los Pops seleccionados?')]");
	By xmlCancelarElim = By.xpath("//mat-dialog-container/app-dialog-confirm/div[2]/div/div/button");
	By xmlConfirmarElim = By.xpath("//mat-dialog-container/app-dialog-confirm/div[2]/div/div/div/button");

	
	By xmlMsgConfElim = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'Se han eliminado los registros exitosamente')]");
	
	/* Ventana '�Esta seguro que desea activar el registro?'
	 ******************************************************************************************/
	By xmlPopUpActivar = By.xpath("//div[@class='cdk-overlay-pane']//h5[contains(text(),'�Esta seguro que desea activar el registro?')]");
	By xmlCancelarAct = By.xpath("//mat-dialog-container/app-dialog-confirm/div[2]/div/div/button");
	By xmlConfirmarAct = By.xpath("//mat-dialog-container/app-dialog-confirm/div[2]/div/div/div/button");

	
	By xmlMsgActivacion = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'Se han eliminado los registros exitosamente')]");
	By xmlMsgInactivacion = By.xpath("//div[@class='cdk-overlay-pane']//descendant::span[contains(text(),'Se han eliminado los registros exitosamente')]");

	
	/* Grid - Resultados de busuqeda
	 ******************************************************************************************/

	By xmlGridTable = By.xpath("//app-pops-list/div/div/table");
	
	By xmlGridHdrId = By.xpath("//th[@role='columnheader'][2]");		
	By xmlGridHdrPop = By.xpath("//th[@role='columnheader'][4]");		
	By xmlGridHdrTipo = By.xpath("//th[@role='columnheader'][5]");	
	By xmlGridHdrPeriodo = By.xpath("//th[@role='columnheader'][6]");		
	By xmlGridHdrPrecio = By.xpath("//th[@role='columnheader'][7]");		
	By xmlGridHdrEspec	= By.xpath("//th[@role='columnheader'][8]");	
	By xmlGridHdrDesc = By.xpath("//th[@role='columnheader'][9]");	
	By xmlGridHdrProv = By.xpath("//th[@role='columnheader'][10]");	
	By xmlGridHdrNumPlaza	= By.xpath("//th[@role='columnheader'][11]");	
	By xmlGridHdrActivo = By.xpath("//th[@role='columnheader'][12]");		
	By xmlGridHdrAccion = By.xpath("//th[@role='columnheader'][13]");	
	
	By xmlGridAllResult = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr");
	By xmlGridEditIcon = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[13]/mat-icon[contains(text(),'edit')]");
	By xmlGridImgIcon = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[13]/mat-icon[contains(text(),'image')]");
	
	By xmlGridId = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[2]");		
	By xmlGridPop = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[4]");		
	By xmlGridTipo = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[5]");	
	By xmlGridPeriodo = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[6]");		
	By xmlGridPrecio = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[7]");		
	By xmlGridEspec	= By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[8]");	
	By xmlGridDesc = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[9]");	
	By xmlGridProv = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[10]");	
	By xmlGridNumPlaza	= By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[11]");	
	By xmlGridActivo = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[12]");		
	By xmlGridAccion = By.xpath("//td[@role='gridcell'][1]/mat-checkbox//ancestor::tr/td[13]");	
	
	//Opciones para cambiar de pagina en el Grid - Resultados de búsuqeda
	By xmlBtnFirstPage = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[1]");
	By xmlBtnFirstPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[1][@disabled='true']");
	//By xmlBtnPreviousPage = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[2]");
	By xmlBtnPreviousPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[2][@disabled='true']");
	By xmlBtnNextPage = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[3]");	
	By xmlBtnNextPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[3][@disabled='true']");
	//By xmlBtnLastPage = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[4]");	
	By xmlBtnLastPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[4][@disabled='true']");
	

	/*
	 * Variables que reciben un valor por parametro
	 ******************************************************************************************/
	String StrPeriodoPopOp = "//div[@class='cdk-overlay-pane']//div[@role='listbox']/mat-option/span[contains(text(),'%s')]";
	String StrProveedorOp = "//div[@class='cdk-overlay-pane']//div[@role='listbox']/mat-option/span[contains(text(),'%s')]";
	String StrTipoPopOp = "//div[@class='cdk-overlay-pane']//div[@role='listbox']/mat-option/span[contains(text(),'%s')]";
	
	String StrGridPop = "//app-pops-list/div/div/table/tbody/tr/td[4][contains(text(),'%s')]";
	String StrGridCheckPop = "//app-pops-list//table//td[4][contains(text(),'%s')]/preceding-sibling::td[3]/mat-checkbox";
	
	String StrGridIsActive = "//app-pops-list//table//td[4][contains(text(),'%s')]/following-sibling::td[8]/mat-checkbox//input[@aria-checked='true']";	
	String StrGridIsInactive = "//app-pops-list//table//td[4][contains(text(),'%s')]/following-sibling::td[8]/mat-checkbox//input[@aria-checked='false']";
	String StrGridCheckActivo = "//app-pops-list//table//td[4][contains(text(),'%s')]/following-sibling::td[8]/mat-checkbox";
	String StrImagenPop = "//app-pops-list//td[4][contains(text(),'%s')]/following-sibling::td[9]/mat-icon[2]";
	String StrEditPop = "//app-pops-list//td[4][contains(text(),'%s')]/following-sibling::td[9]/mat-icon[1]";
	
	String StrIdPop = "//table/tbody/tr/td[2]/a[contains(text(),'%s ')]//following::td[11]/mat-icon[2]";
	/**
	 * Constructor
	 ******************************************************************************************/
	/**
	 * Constructor de la clase DetallePops
	 * @author Manuel Cuen
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
	public DetallePops(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
	}

	public void DetallePantallaPOPs() {

		u.highLight(XmlNombrePop);
		u.click(XmlNombrePop);

	}

	/**
	 * Metodo que valida se muestren la pantalla 'Asociar POP a Plazas'
	 * @return Regresa true si se muestran la pantalla o false si no se muestran
	 * @author Andrea Contreras
	 */
	public Boolean visualizarPantalla() {

		boolean validacion = u.isDisplayed(xmlTituloPantalla);
		return validacion;
	}

	/**
	 * Metodo que valida se muestren los filtros de Relacionar un POP con sus
	 * respectivas plazas
	 * @author Andrea Contreras
	 * @return Regresa true si se muestran los fitros o false si no se muestran
	 */
	public Boolean visualizarFiltros() {

		boolean validacion = false, nomPop, periodo, proveedor, tipoPop;

		u.mouseOver(xmlTituloPantalla);
		nomPop = m.isEnable(XmlNombrePop);
		periodo = m.isEnable(XmlperiodoPop);
		proveedor = m.isEnable(XmlProveedorPop);
		tipoPop = m.isEnable(xmlTipoPopSpan);

		if (nomPop == true && periodo == true && proveedor == true && tipoPop == true) {
			validacion = true;
		}

		return validacion;
	}

	/**
	 * Metodo que valida la pantalla y los filtros
	 * @author Andrea Contreras
	 * @return Regresa true si se muestran la pantalla y filtros o false si no se
	 *         muestran
	 */
	public boolean validarCampos() {
        m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
        
		boolean validacion = false;
		boolean pantalla = visualizarPantalla();
		boolean filtros = visualizarFiltros();

		//u.highLight(XmlFiltNomPop);
		//u.highLight(XmlFiltPerioPop);
		//u.highLight(XmlFiltProvPop);
		//u.highLight(XmlFiltTipoPop);
		u.highLight(xmlFiltros);
		
		testCase.addScreenShotCurrentStep(u, "filtroNombrePop");
		
		u.unhighLight(xmlFiltros);
		//u.unhighLight(XmlFiltNomPop);
		//u.unhighLight(XmlFiltPerioPop);
		//u.unhighLight(XmlFiltProvPop);
		//u.unhighLight(XmlFiltTipoPop);

		if (pantalla == true && filtros == true) {
			validacion = true;
		}

		return validacion;
	}

	public void clickFiltroNombrePops() {

		u.highLight(XmlFiltNomPop);
		u.highLight(XmlFiltPerioPop);
		u.highLight(XmlFiltProvPop);
		u.highLight(XmlFiltTipoPop);
		u.highLight(XmlLbtTotReg);
		testCase.addScreenShotCurrentStep(u, "filtroNombrePop");

		u.unhighLight(XmlFiltNomPop);
		u.unhighLight(XmlFiltPerioPop);
		u.unhighLight(XmlFiltProvPop);
		u.unhighLight(XmlFiltTipoPop);
		u.unhighLight(XmlLbtTotReg);
	}

	public void clickNombrePop(String nombrePop) {

		u.highLight(XmlFiltNomPop);
 		u.sendKeys(XmlNombrePop, nombrePop);
 		u.hardWait(1);
		//testCase.addScreenShotCurrentStep(u, "clickNombrePop");
		u.unhighLight(XmlFiltNomPop);
	}

	public void clickperiodoPop() {

		u.highLight(XmlperiodoPop);
		u.click(XmlNombrePop);
		u.hardWait(1);

	}

	public void clickPeriodoPopF() {
		u.highLight(XmlperiodoPopF);
		u.click(XmlperiodoPopF);
		u.hardWait(1);
		//testCase.addScreenShotCurrentStep(u, "clickPeriodoPopF");

	}

	public void clickPeriodoPopSinPerio() {

		u.highLight(XmlPeriodoPopSPerio);
		u.click(XmlPeriodoPopSPerio);
		u.hardWait(1);
		//testCase.addScreenShotCurrentStep(u, "clickPeriodoPopSinPerio");

		u.unhighLight(XmlperiodoPop);
		u.unhighLight(XmlperiodoPopF);
	}

	/**
	 * Metodo para seleccionar un periodo
	 * @author Andrea Contreras
	 * @param periodo (String): periodo a ingresar como filtro
	 */
	public void selectPeriodoPop(String periodo) {

		u.highLight(XmlFiltPerioPop);

		WebElement dropDownList = m.explicitWaitToBeClickable(XmlperiodoPopF, Duration.ofSeconds(30));
		dropDownList.click();

		String xpath = String.format(StrPeriodoPopOp, periodo);
		By elemento = By.xpath(xpath);

		if (m.existElement(elemento)) {
			u.click(elemento);
			// retun true;
		}
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "selectPeriodoPop");
		u.unhighLight(XmlFiltPerioPop);
	}

	public void clickProveedorPop() {
		u.highLight(XmlProveedorPop);
		u.click(XmlProveedorPop);
		u.hardWait(1);
		//testCase.addScreenShotCurrentStep(u, "clickProveedorPop");
	}

	public void clickProveedorPopSelec() {
		u.hardWait(3);
		u.highLight(XmlProveedorPopSelec);
		u.click(XmlProveedorPopSelec);
		u.hardWait(1);
		//testCase.addScreenShotCurrentStep(u, "clickProveedorPopSelec");

		u.unhighLight(XmlProveedorPop);
	}

	/**
	 * Metodo para seleccionar al proveedor
	 * @author Andrea Contreras
	 * @param proveedor (String): proveedor a ingresar como filtro
	 */
	public void selectProveedorPop(String proveedor) {
		u.highLight(XmlFiltProvPop);

		WebElement dropDownList = m.explicitWaitToBeClickable(XmlProveedorPop, Duration.ofSeconds(30));
		dropDownList.click();

		String xpath = String.format(StrProveedorOp, proveedor);
		By elemento = By.xpath(xpath);

		if (m.existElement(elemento)) {
			u.click(elemento);
			// retun true;
		}
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "selectProveedor");
		u.unhighLight(XmlFiltProvPop);
	}

	public void clickTipoPop() {

		// u.highLight(XmlTipoPop);
		u.highLight(XmlTipoPopF);
		u.click(XmlTipoPopF);
		u.hardWait(1);
		
	}

	public void clickTipoPopSelec() {
		u.hardWait(10);
		// u.click(XmlTipoPopF);
		u.click(XmlTipoPopSelec);
		u.hardWait(1);
		//testCase.addScreenShotCurrentStep(u, "clickTipoPopSelec");

		u.unhighLight(XmlTipoPop);
		u.unhighLight(XmlTipoPopF);
	}

	/**
	 * Metodo para seleccionar el tipo de pop
     * @author Andrea Contreras
	 * @param tipoPop (String): tipo de pop a ingresar como filtro
	 */
	public void selectTipoPop(String tipoPop) {
		
		u.highLight(XmlFiltTipoPop);
		WebElement dropDownList = m.explicitWaitToBeClickable(XmlTipoPopF, Duration.ofSeconds(30));
		dropDownList.click();

		String xpath = String.format(StrTipoPopOp, tipoPop);
		By elemento = By.xpath(xpath);

		if (m.existElement(elemento)) {
			u.click(elemento);
			// retun true;
		}
        u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "selectTipoPop");
		u.unhighLight(XmlFiltTipoPop);
	}
	
	
	/**
	 * Metodo para seleccionar el estatus del pop
     * @author Andrea Contreras
	 * @param estatus (String): estatus a ingresar como filtro
	 */
	public void selectEstatusPop(String estatus) {
		u.highLight(XmlLbtTotReg);
		WebElement dropDownList = m.explicitWaitToBeClickable(XmlEstatusF, Duration.ofSeconds(30));
		dropDownList.click();

		String xpath = String.format(StrTipoPopOp, estatus);
		By elemento = By.xpath(xpath);

		if (m.existElement(elemento)) {
			u.click(elemento);
			// retun true;
		}

		testCase.addScreenShotCurrentStep(u, "selectTipoPop");

	}

	public void clickBtnBuscarPop() {
		u.hardWait(3);
		u.highLight(XmlBtnBuscar);
		testCase.addScreenShotCurrentStep(u, "clickBuscarPop");
		u.click(XmlBtnBuscar);		
		u.unhighLight(XmlBtnBuscar);
	}
	

	public void clickImagenPop() {

		u.highLight(XmlImagen);
		u.click(XmlImagen);
		testCase.addScreenShotCurrentStep(u, "clickImagenPop");

		u.unhighLight(XmlImagen);
	}
	
	/**
	 * Metodo para dar click en el icono 'Imagen' del pop indicado
	 * @param pop (String): nombre del pop
	 * @author Andrea Contreras
	 */
	public void clickImagenIconPop(String pop) {

		boolean exist = false;
		String locator = String.format(StrImagenPop, pop);
		By element = By.xpath(locator);							
		
		do {
			System.out.print("entro al ciclo do while");
			if(m.existElement(element)==true) {
			
			u.highLight(element);	
			testCase.addScreenShotCurrentStep(u, "clickImg");
			u.click(element);
			testCase.addScreenShotCurrentStep(u, "clickImg");
			u.unhighLight(element);
			exist = true;	
			
			}
			
			if(m.existElement(element)==false) {
				
			if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {		
				break;				
				
			}else if(m.existElement(xmlBtnFirstPageDisable)==true && m.existElement(xmlBtnNextPageDisable)==false) {
				
    			  u.click(xmlBtnNextPage);
    			  m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    			
    			}else if (m.existElement(xmlBtnNextPageDisable)==true && m.existElement(xmlBtnFirstPageDisable)==false) {
   				
    				u.click(xmlBtnFirstPage);
    				m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    				
    	        }
    		}		
			
		}while(!exist);
	}

	public void clickVentanaImagen() {

		u.highLight(XmlLblSel_Archivo);
		u.highLight(XmlBtnCancelar);
		u.highLight(XmlBtnAceptar);
		testCase.addScreenShotCurrentStep(u, "clickVentanaImagen");

		u.unhighLight(XmlLblSel_Archivo);
		u.unhighLight(XmlBtnCancelar);
		u.unhighLight(XmlBtnAceptar);

	}
	
	/**
	 * Metodo para validar la informaci�n de la ventana modal al subir una imagen
	 * @return Regresa true si se muestra la ventana y la informaci�n o false si no se muestra
	 * @author Andrea Contreras
	 */
	public boolean validarVentanaSubirImagen() {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		boolean validacion = false, ventana, imagen, btnCancelar, btnAceptar;

		ventana = m.existElement(XmlVentanaImagen);
		imagen = m.existElement(XmlImg);
		
		btnCancelar = m.existElement(XmlBtnCancelar);
		btnAceptar = m.existElement(XmlBtnAceptar);

		if (ventana == true && imagen == true && btnAceptar == true 
				&& btnCancelar == true) {
			validacion = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "validarVentanaSubirImagen");

		return validacion;
	}
	
	/**
	 * Metodo para validar la informaci�n de la ventana modal al cambiar una imagen
	 * @return Regresa true si se muestra la ventana y la informaci�n o false si no se muestra
	 * @author Andrea Contreras
	 */
	public boolean validarVentanaCambiarImagen() {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		boolean ventana, imagen, nom, fecha, tam, btnCancelar, btnAceptar,
			 validacion = false;
		
		ventana = m.existElement(XmlVentanaImagen);
		imagen = m.existElement(XmlImg);
		nom = m.existElement(XmlLblNombreImagen);
		fecha = m.existElement(XmlLblFechaImagen);
		tam = m.existElement(XmlLblTamanoImagen);
		
		btnCancelar = m.existElement(XmlBtnCancelar);
		btnAceptar = m.existElement(XmlBtnAceptar);		
		
		if (ventana == true && imagen == true && nom == true && fecha == true 
				&& tam == true && btnAceptar == true && btnCancelar == true) {
			validacion = true;

		}
		
		testCase.addScreenShotCurrentStep(u, "validarVentanaSubirImagen");

		return validacion;
	}
	
	/**
	 * Metodo para validar la informaci�n de la ventana modal despues de subir/cambiar la imagen
	 * @return Regresa true si se muestra la ventana y la informaci�n o false si no se muestra
	 * @author Andrea Contreras
	 */
	public boolean validarImagenCargada(String imgNueva) {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		boolean valInfo = false, ventana, imagen, nom, fecha, tam, btnCancelar, btnAceptar,
		valImg = false, validacion = false;
		String imgActual = "";
		String [] array = null;
		
		ventana = u.isDisplayed(XmlVentanaImagen);
		imagen = u.isDisplayed(XmlImg);
		nom = u.isDisplayed(XmlLblNombreImagen);
		fecha = u.isDisplayed(XmlLblFechaImagen);
		tam = u.isDisplayed(XmlLblTamanoImagen);
		
		btnCancelar = u.isDisplayed(XmlBtnCancelar);
		btnAceptar = u.isDisplayed(XmlBtnAceptar);		
		
		if (ventana == true && imagen == true && nom == true && fecha == true 
				&& tam == true && btnAceptar == true && btnCancelar == true) {
			valInfo = true;
			array = u.getText(XmlLblNombreImagen).split(": ");
			imgActual = array[array.length-1];
			u.highLight(XmlImg);
		}
		
		if(imgNueva.equals(imgActual)) {
			valImg = true;
		}
		
		if(valImg == true && valInfo == true) {
			validacion = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "validarVentanaImagen");

		return validacion;
	}

	public void clickSelArchivo() {
		u.hardWait(2);
		u.sendKeys(XmlLblSel_Archivo,
				"c:\\Users\\manuel.cuen\\OneDrive - NEORIS\\Pictures\\C9F281219B0042368B58F148AE58D794.png");
		testCase.addScreenShotCurrentStep(u, "clickSelArchivo");

		u.unhighLight(XmlLblSel_Archivo);

	}
	
	/**
	 * Metodo para dar click en el boton 'Aceptar' de la ventana 'Imagen del POP'
	 * @author Andrea Contreras
	 */
	public void clickBtnAceptarImagen() {
		
		u.highLight(XmlBtnAceptar);
		testCase.addScreenShotCurrentStep(u, "clickAceptarImg");
		u.click(XmlBtnAceptar);		
		u.unhighLight(XmlBtnAceptar);

	}
	
	/**
	* Metodo para validar mensaje 'Imagen cargada exitosamente'
	* @return true si se muestra la imagen o false si no se muestra
	* @author Andrea Contreras
	*/
	public boolean validarMsgImgCargada() {
		
		m.explicitWaitVisibilityOfElement(xmlMsgCargaImg, Duration.ofSeconds(10));
		
		boolean msgExist = false;
		if (m.existElement(xmlMsgCargaImg)==true) {				
			msgExist = true;
		}
		
		testCase.addScreenShotCurrentStep(u, "msgImagenCargada");
		return msgExist;
	}
	
	/**
	* Metodo para cargar una imagen
	* @param path (String): Ruta donde se encuentra la imagen a cargar
	* @author Andrea Contreras
	*/
	public void uploadImg(String path) {
		
		testCase.addScreenShotCurrentStep(u, "clickSelArchivo");
		u.sendKeys(XmlLblSel_Archivo, path);
		testCase.addScreenShotCurrentStep(u, "clickSelArchivo");

	}

	public void clickSeVisualiza() {

		u.highLight(XmlLblNombreImagen);
		u.highLight(XmlLblFechaImagen);
		u.highLight(XmlLblTamanoImagen);
		u.highLight(XmlBtnCancelar);
		u.highLight(XmlBtnAceptar);
		testCase.addScreenShotCurrentStep(u, "clickSeVisualiza");

		u.unhighLight(XmlLblNombreImagen);
		u.unhighLight(XmlLblFechaImagen);
		u.unhighLight(XmlLblTamanoImagen);
		u.unhighLight(XmlBtnCancelar);
		u.unhighLight(XmlBtnAceptar);
		u.hardWait(3);
		u.highLight(XmlBtnCancelar);
		u.click(XmlBtnCancelar);
		testCase.addScreenShotCurrentStep(u, "clickSeVisualiza");
		u.hardWait(2);
	}

	public void clickBtnAceptar() {

		u.highLight(XmlBtnAceptar);
		u.click(XmlBtnAceptar);
		testCase.addScreenShotCurrentStep(u, "clickBtnAceptar");

		u.unhighLight(XmlBtnAceptar);
	}

	/**
	 * Metodo para validar que se muestren el grid con la siguiente informacion: Id,
	 * POP, Tipo, Periodo, Requiere precio, Especificacion, Descripcion, Proveedor,
	 * No. Plaza, Activo, Accion
	 * @author Andrea Contreras
	 * @return true si se muestra la informaciÃ³n o false si no se muestra
	 */
	public boolean validarGridInfo() {

		boolean validacion = false;
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));

	    m.scrollToElement(xmlGridTable); 
	    u.mouseOver(xmlGridTable);
	    
		boolean existResults = m.existMultipleElements(xmlGridAllResult);
		boolean columnsGrid = validarGridColumns();
		
		if(columnsGrid && existResults) {
			validacion = true;
		}
		testCase.addScreenShotCurrentStep(u, "validarGrid");

		return validacion;

	}
	
	public boolean validarGridColumns() {

		boolean validacion = false;
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));

		Boolean hdrId = m.existElement(xmlGridHdrId);
		Boolean hdrPop = m.existElement(xmlGridHdrPop);	
		Boolean hdrTipo = m.existElement(xmlGridHdrTipo);	
		Boolean hdrPeriodo = m.existElement(xmlGridHdrPeriodo);	
		Boolean hdrPrecio = m.existElement(xmlGridHdrPrecio);	
		Boolean hdrEspec = m.existElement(xmlGridHdrEspec);	
		Boolean hdrDesc = m.existElement(xmlGridHdrDesc);	
		Boolean hdrProv = m.existElement(xmlGridHdrProv);	
		Boolean hdrNumPlaza	= m.existElement(xmlGridHdrNumPlaza);	
		Boolean hdrActivo = m.existElement(xmlGridHdrActivo);	
		Boolean hdrAccion = m.existElement(xmlGridHdrAccion);
		
		if(hdrId && hdrPop && hdrTipo && hdrPeriodo && hdrPrecio && hdrEspec && hdrDesc && hdrProv 
				&& hdrNumPlaza && hdrActivo && hdrAccion) {
			validacion = true;
		}

		return validacion;

	}
	
	/**
	 * Metodo seleccionar una opcion del Grid
	 * @author Andrea Contreras
	 * @param pop(String): pop a seleccionar
	 */
	public void selectPop(String pop) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
	    u.mouseOver(xmlGridTable);
	    
		String locator = String.format(StrGridCheckPop, pop);
		By element = By.xpath(locator);
		u.click(element);

		testCase.addScreenShotCurrentStep(u, "validarGrid");

	}
	
	
	/**
	 * Metodo para seleccionar varias opciones del Grid
	 * @author Andrea Contreras
	 * @param pops(ArrayList <String>): pops a seleccionar
	 */
	public boolean selectPopMultiple(ArrayList <String> pops) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		boolean isSelected = false, validacion = false;	
		int selectedPops = 0;
	    	
        for(int i = 0; i < pops.size(); ++i){
        	String locator = String.format(StrGridPop, pops.get(i));
    		By element = By.xpath(locator);							
			
			do {
				System.out.print("entro al ciclo do while");
				if(m.existElement(element)==true) {
				
				u.highLight(element);
				String checkStr = locator+"/preceding-sibling::td[3]/mat-checkbox";			
				u.click(By.xpath(checkStr));
				u.hardWait(2);
				testCase.addScreenShotCurrentStep(u, "checkPop");
				//u.unhighLight(element);
				isSelected = true;
				selectedPops+=1;
				System.out.print("-encontro el pop-");			
				
				}
				
				if(m.existElement(element)==false) {
					
				if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
					System.out.print("-no existen el pop");			
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
	
	/**
	 * Metodo para validar que el boton 'Eliminar' este habilitado
	 * @author Andrea Contreras
	 * @return Regresa true si el boton esta habilitado o false si no lo esta.
	 */
	public boolean isEliminarBtnEnable() {
		
		boolean isEnable = m.isEnable(xmlBtnEliminar);	

		if(isEnable) {
			u.highLight(xmlBtnEliminar);
		}
		
		testCase.addScreenShotCurrentStep(u, "isDeleteBtnActive");

		return isEnable;	
		
	}
	
	/**
	* Metodo para dar click en el boton 'Eliminar'
	* @author Andrea Contreras
	*/
	public void clickEliminarPop(){ 
		
		m.explicitWaitToBeClickable(xmlBtnEliminar,Duration.ofSeconds(10));
		m.scrollToElement(xmlBtnEliminar);
		u.highLight(xmlBtnEliminar);
		testCase.addScreenShotCurrentStep(u, "btnEliminar");
		u.click(xmlBtnEliminar);		
		testCase.addScreenShotCurrentStep(u, "clickEliminar");
		u.unhighLight(xmlBtnEliminar);
		
	}
	
	/**
	* Metodo para validar que se muestra la ventana '�Esta seguro que desea eliminar los Pops seleccionados?
    * Tambien se eliminare lo siguiente: - Asociaciones a plazas - Asociaciones a proveedores - Asociaciones a pedidos'
	* @return Regresa true si se muestra la ventana o false si no se muestra
	* @author Andrea Contreras
	*/
	public boolean validarPopUpConfirmarEliminacion(){
		
		boolean exist = m.existElement(xmlPopUpEliminar);
		testCase.addScreenShotCurrentStep(u, "validarPopUpElim");
		return exist;
		
	}
	
	
	/**
	* Metodo para dar click en el boton 'Confirmar' de la ventana '�Esta seguro que desea eliminar los Pops seleccionados?
    * Tambien se eliminara lo siguiente: - Asociaciones a plazas - Asociaciones a proveedores - Asociaciones a pedidos'
	* @author Andrea Contreras
	*/
	public void clickBtnConfirmarPopUpEliminar(){ 
		
		m.explicitWaitToBeClickable(xmlCancelarElim,Duration.ofSeconds(10));
		u.highLight(xmlConfirmarElim);
		testCase.addScreenShotCurrentStep(u, "clickConfirmarElim");
		u.click(xmlConfirmarElim);	
		
	}
	
	
	/**
	* Metodo para dar click en el boton 'Confirmar' de la ventana '�Esta seguro que desea eliminar los Pops seleccionados?
    * Tambien se eliminara lo siguiente: - Asociaciones a plazas - Asociaciones a proveedores - Asociaciones a pedidos'
	* @author Andrea Contreras
	*/
	public boolean validarMsgEliminarPlazaPop() {
		
		m.explicitWaitVisibilityOfElement(xmlMsgConfElim, Duration.ofSeconds(360));
		
		boolean msgExist = false;
		if (m.existElement(xmlMsgConfElim)==true) {				
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgPopEliminar");
		System.out.print("Existe mensaje-"+msgExist);
		return msgExist;
	}
	
	
	/**
	* Metodo para Activar o desactivar pop
	* @author Andrea Contreras
	*/
	public boolean activarDesactivarPop() {
		
		m.explicitWaitVisibilityOfElement(xmlMsgConfElim, Duration.ofSeconds(360));
		
		boolean msgExist = false;
		if (m.existElement(xmlMsgConfElim)==true) {				
			msgExist = true;
		}
		testCase.addScreenShotCurrentStep(u, "msgPopEliminar");
		System.out.print("Existe mensaje-"+msgExist);
		return msgExist;
	}
	
	/**
	* Obtener estatus del pop: activo o inactivo
	* @params pop(String): nombre del pop a activar/desactivar
	* @return 
	* @author Andrea Contreras
	*/
	public String obtenerEstatusActualPop(String pop) {
		
		String estatus = "";
		boolean findPop = false;
		By element = By.xpath(String.format(StrGridPop, pop));							
		
		do {
			System.out.print("entro al ciclo do while");
			if(m.existElement(element)==true) {
				
				//u.highLight(element);
				By isActive = By.xpath(String.format(StrGridIsActive, pop));
				By isInactive = By.xpath(String.format(StrGridIsInactive, pop));
				
				if(m.existElement(isActive)) 
					estatus = "1";
				if(m.existElement(isInactive)) 
					estatus = "0";

			    //testCase.addScreenShotCurrentStep(u, "checkPop");
			    //u.unhighLight(element);
				findPop = true;
			    System.out.print("-encontro el pop-");						
			}
			
			if(m.existElement(element)==false) {
				
			if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
				System.out.print("-no existen el pop");		
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
			
		}while(!findPop);
		
		return estatus;
	}
	
	/**
	* Metodo para Activar o desactivar pop
	* @params pop(String): nombre del pop a activar/desactivar
	* @author Andrea Contreras
	*/
	public void activarDesactivarPop(String pop) {
		
		String locator = String.format(StrGridCheckActivo, pop);
		By element = By.xpath(locator);
		u.highLight(xmlGridPop);
		testCase.addScreenShotCurrentStep(u, "selectedPop");
		u.click(element);
		testCase.addScreenShotCurrentStep(u, "checkStatus");

	}
	
	
	/**
	* Metodo para dar click en el boton 'Confirmar' de la ventana '�Esta seguro que desea activar el registro?' y '�Esta seguro que desea desactivar el registro'
	* @author Andrea Contreras
	*/
	public void clickBtnConfirmarPopUpActivarDesactivar(){ 
		
		m.explicitWaitToBeClickable(xmlConfirmarAct,Duration.ofSeconds(10));
		u.highLight(xmlConfirmarAct);
		testCase.addScreenShotCurrentStep(u, "xmlConfirmarActivarDesactivar");
		u.click(xmlConfirmarAct);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "estatusFinal");
	}
	
	/*
	 * Nuevo Pop
	 ******************************************************************************************/
	
	/**
	* Metodo para dar click en el boton 'Nuevo'
	* @author Andrea Contreras
	*/
	public void clickBtnNuevoPop(){ 
		
		u.highLight(xmlBtnNuevo);
		testCase.addScreenShotCurrentStep(u, "clickBotonNuevo");
		u.click(xmlBtnNuevo);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		testCase.addScreenShotCurrentStep(u, "cambioPantallaNuevo");
	}
	
	public void clickBuscaIdPops(String IdPops) {
		u.hardWait(3);
		By LocIdPop = By.xpath(String.format(StrIdPop, IdPops));
		System.out.println();
		u.click(LocIdPop);
		u.hardWait(3);
		
		testCase.addScreenShotCurrentStep(u, "clickBuscaIdPops");
		u.hardWait(2);
	}

}
