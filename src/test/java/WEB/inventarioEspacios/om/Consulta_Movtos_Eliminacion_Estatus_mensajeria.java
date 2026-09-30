package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_Estatus_mensajeria {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_Espacio
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    // Localizador del Menu Admon
//    By xmlMenusAd = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[1]/div");
    By xmlMenusAd = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/label");
    By xmlMenusPedMark = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[9]/div/label");
    
    // Localizador Estatus de Mensajeria
    By xmlEstatusMensajeria = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[10]/a/div/label");
    
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
    
    // Pantalla Estatus de Mensajeria
    By xmlDescripcionEstatusMsj = By.xpath("//*[@id=\"mat-input-0\"]");
    By xmlBtnNuevo = By.xpath("//*[@id=\"formFiltroMensajeria\"]/div[2]/div/div[1]/button/span[1]");
    By xmlBtnLimpiar = By.xpath("//*[@id=\"formFiltroMensajeria\"]/div[2]/div/div[2]/button/span[1]");
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroMensajeria\"]/div[2]/div/div[3]/button/span[1]");
  //*[@id="content-nav"]/app-messenger-status/div/mat-paginator/div/div/div[2]/div
    									
    // Informacion Grid/Tabla
    By xmlLblIdEstatus = By.xpath("//*[@id=\"content-nav\"]/app-messenger-status/div/div/table/thead/tr/th[2]");
    By xmlLblDescripcion = By.xpath("//*[@id=\"content-nav\"]/app-messenger-status/div/div/table/thead/tr/th[3]");
    By xmlLblEsFin = By.xpath("//*[@id=\"content-nav\"]/app-messenger-status/div/div/table/thead/tr/th[4]");
    By xmlBtnEditar = By.xpath("//*[@id=\"content-nav\"]/app-messenger-status/div/div/table/tbody/tr[1]/td[5]/mat-icon");
    By xmlChkEliminar = By.xpath("//*[@id=\"mat-checkbox-2\"]/label/span[1]"); //cambia el atributo mat-checkbox si buscas otro que no sea el primero,como que va enumerando los registros, en este caso saque el localizador del primer registro por eso al final del mat-checkbox hay un -1
    By xmlAvancePagina = By.xpath("//*[@id=\"content-nav\"]/app-messenger-status/div/mat-paginator/div/div/div[2]/button[3]");
  //*[@id="content-nav"]/app-messenger-status/div/mat-paginator/div/div/div[2]/button[3]
    // Pantalla del boton nuevo
    By xmlDescripcionNvo = By.xpath("//*[@id=\"mat-input-1\"]");
    By xmlEsFinNvo = By.xpath("//*[@id=\"content-nav\"]/app-messenger-status-detail/div/div/div[1]/div[2]/mat-label");
    By xmlBtnCancelarNvo = By.xpath("//*[@id=\"content-nav\"]/app-messenger-status-detail/div/div/div[2]/div/div[1]/button/span[1]");
    By xmlBtnLimpiarNvo = By.xpath("//*[@id=\"content-nav\"]/app-messenger-status-detail/div/div/div[2]/div/div[2]/button/span[1]");
    By xmlBtnGuardarNvo = By.xpath("//*[@id=\"content-nav\"]/app-messenger-status-detail/div/div/div[2]/div/div[3]/button/span[1]");
    By xmlBtnGuardarEdi = By.xpath("//*[@id=\"content-nav\"]/app-messenger-status-detail/div/div/div[2]/div/div[2]/button/span[1]");
    
    // Ventana Modal del boton nuevo guardar
    By xmlBtnConfirGuardNvo = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    //Bandeja de pedidos
    By xmlMenuBandejaPed = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[3]/div/label");
    By xmlMenuBanPedMark = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div[2]/mat-list/div[1]/a/div/label");
    By xmlSmBandejaPed = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[1]/a/div/label");
    By xmlTipoPedido = By.xpath("//*[@id=\"mat-select-value-3\"]");
    By xmlPlaza = By.xpath("//*[@id=\"mat-select-value-7\"]");
    By xmlPlazaFlecha = By.xpath("//*[@id=\"mat-select-6\"]/div/div[2]");
    By xmlEstatus = By.xpath("//*[@id=\"mat-select-value-5\"]");
    By xmlLblBandejaPed = By.xpath("//*[@id=\"tituloEscritorio\"]/ol/li[2]");
    By xmlBtnBuscarBp = By.xpath("//*[@id=\"formFiltroInventario\"]/div[2]/div/div[3]/button/span[1]");
    
    //Ventana Detalle pedido y Datos de la Guia
    By xmlVentanaDetPedido = By.xpath("//*[@id=\"content-nav\"]");
    By xmlLblDetPedido = By.xpath("//*[@id=\"tituloEscritorio\"]/ol/li[2]");
    By xmlLblDatosPed = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/mat-label[1]");
    By xmlLblFolio = By.xpath("//*[@id=\"dataHead\"]/div[1]/div[1]/mat-label/b");
    By xmlLblEstatus = By.xpath("//*[@id=\"dataHead\"]/div[2]/div[1]/mat-label/b");
    By xmlSelectEstatus = By.xpath("//*[@id=\"mat-select-26\"]/div/div[2]");
    //By xmlSelectEstatus = By.xpath("//div/div[2]");
    By xmlSelectEstatusDos = By.xpath("//*[@id=\"mat-select-30\"]/div/div[2]");
    By xmlTxtRecibidoPor = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[5]/div/table/tbody/tr/td[4]/input");
    By xmlLblPeriodo = By.xpath("//*[@id=\"dataHead\"]/div[1]/div[2]/mat-label/b");
    By xmlLblPlaza= By.xpath("//*[@id=\"dataHead\"]/div[2]/div[2]/mat-label/b");
    By xmlLblTipoPed = By.xpath("//*[@id=\"dataHead\"]/div[1]/div[3]/mat-label/b");
    By xmlLblUsuario = By.xpath("//*[@id=\"dataHead\"]/div[2]/div[3]/mat-label/b");
    By xmlLblFecha = By.xpath("//*[@id=\"dataHead\"]/div[1]/div[4]/mat-label/b");
    
    By xmlLblComentario = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/mat-label[2]");
    
    // Guias
    By xmlLblGuias = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/mat-label[3]");
    By xmlLblNumGuia = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[5]/div/table/thead/tr/th[1]");
    By xmlLblProveedor = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[5]/div/table/thead/tr/th[2]");
    By xmlLblEstatusGuia = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[5]/div/table/thead/tr/th[3]");
    By xmlLblRecibido = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[5]/div/table/thead/tr/th[4]");
    By xmlLblFechaGuia = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[5]/div/table/thead/tr/th[5]");
    By xmlLblExportar = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[5]/div/table/thead/tr/th[6]");
    By xmlBtnAgregaGuia = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[6]/div[6]/div/button/span[1]");
    
    //Material Solicitado 
    By xmlLblMaterialSol = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/mat-label[4]");
    By xmlIconImagen = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[7]/div[2]/table/thead/tr/th[1]");
    By xmlLblPopId = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[7]/div[2]/table/thead/tr/th[2]");
    By xmlLblPop = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[7]/div[2]/table/thead/tr/th[3]");
    By xmlLblContenido = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[7]/div[2]/table/thead/tr/th[4]");
    By xmlLblDescrip = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[7]/div[2]/table/thead/tr/th[5]");
    By xmlLblPrecio = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[7]/div[2]/table/thead/tr/th[6]");
    By xmlLblCantidad = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[7]/div[2]/table/thead/tr/th[7]");
    By xmlLblTotal = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[7]/div[2]/table/thead/tr/th[8]");
    By xmlLblImprimPcio = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[7]/div[2]/table/thead/tr/th[9]");
    By xmlLblComentarMat = By.xpath("//*[@id=\"content-nav\"]/app-order-detail/div/div[7]/div[2]/table/thead/tr/th[10]");
    
    
    // Paso 6
 
 // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltroMensajeria\"]/div[2]/div/div[2]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    By xmlBtnBuscarPrueba = By.xpath("//*[@id=\"formFiltroMensajeria\"]/div[2]/div/div[3]/button/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion estatus de mensajeria
    String StrIdEstatus = ("//div/table/tbody/tr/td[2][contains(text(),' %s ')]/preceding-sibling::td/mat-checkbox");
    String StrEsFin = ("//mat-radio-group/mat-radio-button/label/span[2][contains(text(),'%s')]//preceding-sibling::span");
    String StrChkEditaStat = ("//table/tbody/tr/td[2][contains(text(),'%s')]//following::td[3]/mat-icon");
    String StrChkTipoPed = ("//div[@id=\"mat-select-2-panel\"]/mat-option/span[contains(text(),' %s ')]//preceding-sibling::mat-pseudo-checkbox");
    String StrChkPlaza = ("//div[@id=\"mat-select-6-panel\"]/mat-option[3]/span[contains(text(),' %s ')]//preceding-sibling::mat-pseudo-checkbox");
    String StrChkEstatus = ("//div[@id=\"mat-select-4-panel\"]/mat-option[2]/span[contains(text(),'%s')]//preceding-sibling::mat-pseudo-checkbox");
    String StrClicFolioPed = ("//table/tbody/tr/td/a[contains(text(),'%s')]");
    String StrEsFinNo = ("//table/tbody/tr[1]/td[4][contains(text(),'No')]");
    String StrEsFinSi = ("//table/tbody/tr[i]/td[4][contains(text(),'Si')]");
    //String StrTotElem = ("<div class=mat-paginator-range-label> 6 � 9 of 9 </div>");
                          
    // localizador para buscar fin si = si
    String StrBuscaFinSi = ("//table/tbody/tr/td[4]");
    // localizador Cambiar Estatus
    //String StrCambiaStats = ("//div[@id=\"mat-select-30-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String StrCambiaStats = ("//mat-option/span[contains(text(),' %s ')]");
    
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_Estatus_mensajeria(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Metodo:  Para dar clic en el menu-submenu pedido-bandeka de pedido
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickBandejaPedidos(String negocio) {
    	
    	if(negocio.toUpperCase().equals("MARCA")) {
    		System.out.println("Entro por Marca");
    		u.mouseOver(xmlMenusPedMark);
    		u.highLight(xmlMenusPedMark);
    		u.click(xmlMenusPedMark);
    		
        	u.highLight(xmlMenuBanPedMark);
        	u.click(xmlMenuBanPedMark);
        	
    	} else if(negocio.toUpperCase().equals("OXXO")) {
    		System.out.println("Entro por Marca");
    		u.mouseOver(xmlMenusAd);
    		u.highLight(xmlMenusAd);
    		u.click(xmlMenusAd);
    		
        	u.highLight(xmlMenuBandejaPed);
        	u.click(xmlMenuBandejaPed);
        	
        	u.highLight(xmlSmBandejaPed);
        	u.click(xmlSmBandejaPed);
    	}
    	
    	u.mouseOver(xmlHeader);
    	
		testCase.addScreenShotCurrentStep(u, "clickBandejaPedidos");
		u.hardWait(2);
    }
	
	/**
	 * Metodo:  Para seleccionar un Id de estatus mensajeria y dar click en el boton Eliminar 
	 * @author: Manuel CUen
	 * @param:  Id Espacio: Llenar en el data provider el Id a eliminar
	 * @return: Ninguno
	 */
		
	public void MenuAdmonElimEstatusMensajeria(String estatusId) {
		
		u.highLight(xmlMenusAd);
		u.click(xmlMenusAd);
		
		u.highLight(xmlEstatusMensajeria);
		u.click(xmlEstatusMensajeria);
		
		u.mouseOver(xmlHeader);
		
		By LocEstatusId = By.xpath(String.format(StrIdEstatus, estatusId));
		System.out.println(StrIdEstatus);
	    u.click(LocEstatusId);
	    
	    u.highLight(xmlBtonEliminar);
	    u.click(xmlBtonEliminar);
	    
	    u.highLight(xmlConfEliminar);
	    u.click(xmlConfEliminar);
	    u.hardWait(2);
	    
		testCase.addScreenShotCurrentStep(u, "MenuAdmonElimEstatusMensajeria");
		u.hardWait(2);
	}
	
	/**
	 * Metodo:  para dar clic en el submenu estatus-mensajeria
	 * @author: Manuel CUen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickEstatusMensajeria() {
    	
		u.highLight(xmlMenusAd);
		u.click(xmlMenusAd);
		
		u.highLight(xmlEstatusMensajeria);
		u.click(xmlEstatusMensajeria);
		
		u.mouseOver(xmlHeader);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		testCase.addScreenShotCurrentStep(u, "clickEstatusMensajeria");
		u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para Mostrar filtros de la pantalla estatus-mensajeria
	 * @author: Manuel CUen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickMuestraFiltros() {
    	
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlDescripcionEstatusMsj);
    	u.highLight(xmlBtnNuevo);
    	u.highLight(xmlBtnLimpiar);
    	u.highLight(xmlBtnBuscar);
    	
		testCase.addScreenShotCurrentStep(u, "clickMuestraFiltros");
		u.hardWait(2);
    	u.unhighLight(xmlDescripcionEstatusMsj);
    	u.unhighLight(xmlBtnNuevo);
    	u.unhighLight(xmlBtnLimpiar);
    	u.unhighLight(xmlBtnBuscar);
    }
    
	/**
	 * Metodo:  Para Mostrar etiquetas del grid en la pantalla estatus-mensajeria
	 * @author: Manuel CUen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickMuestraGrid() {
    	
    	u.highLight(xmlLblIdEstatus);
    	u.highLight(xmlLblDescripcion);
    	u.highLight(xmlLblEsFin);
    	u.highLight(xmlChkEliminar);
    	u.highLight(xmlBtnEditar);
    	
		testCase.addScreenShotCurrentStep(u, "clickMuestraGrid");
		u.hardWait(2);
    	u.unhighLight(xmlLblIdEstatus);
    	u.unhighLight(xmlLblDescripcion);
    	u.unhighLight(xmlLblEsFin);
    	u.unhighLight(xmlChkEliminar);
    	u.unhighLight(xmlBtnEditar);

    }
    
	/**
	 * Metodo:  Para dar clic en el boton nuevo
	 * @author: Manuel CUen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickBotonNuevo() {
    	
    	u.highLight(xmlBtnNuevo);
    	u.click(xmlBtnNuevo);
    	
		testCase.addScreenShotCurrentStep(u, "clickBotonNuevo");
		u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para Mostar/resaltar campos de la pantalla nuevo estaus-mensajeria
	 * @author: Manuel Cuen
	 * @param:  bandera, esta variable solo se utiliza para controlar el orden en los campos y botones
	 * @return: Ninguno
	 */
    public void clickMuestraPantBtnNvo(Boolean bandera) {
    	
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlDescripcionNvo);
    	u.highLight(xmlEsFinNvo);
    	u.highLight(xmlBtnCancelarNvo);
    	if (bandera == false) {
    		
    		u.highLight(xmlBtnGuardarEdi);
    		
    	}
    	
    	if (bandera == true) {
    		
        	u.highLight(xmlBtnLimpiarNvo);
        	u.highLight(xmlBtnGuardarNvo);

    	}
    	
		testCase.addScreenShotCurrentStep(u, "clickBotonNuevo");
		u.hardWait(2);
    	u.unhighLight(xmlDescripcionNvo);
    	u.unhighLight(xmlEsFinNvo);
    	u.unhighLight(xmlBtnCancelarNvo);

    	if (bandera == false) {
    		
    		u.unhighLight(xmlBtnGuardarEdi);
    		
    	}
    	
    	if (bandera == true) {
    		
        	u.unhighLight(xmlBtnLimpiarNvo);
        	u.unhighLight(xmlBtnGuardarNvo);

    	}
    }
    
	/**
	 * Metodo:  Para llenar campos en la ventana nuevo estaus-mensajeria
	 * @author: Manuel Cuen
	 * @param:  descripcion,esFin, son necesarios llenarlos en su data provider para el alta de estatus mensajeria
	 * @return: Ninguno
	 */
    public void clickCapturaNuevoEstatus(String descripcion, String esFin) {
    	
    	u.highLight(xmlDescripcionNvo);
    	u.sendKeys(xmlDescripcionNvo, descripcion);
    	
		By LocEsFin = By.xpath(String.format(StrEsFin, esFin));
		System.out.println(LocEsFin);
	    u.click(LocEsFin);
	    
		testCase.addScreenShotCurrentStep(u, "clickCapturaNuevoEstatus");
		u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para dar clic en el icono editar estaus-mensajeria por el Id Estatus
	 * @author: Manuel Cuen
	 * @param:  idEstatus, es/son necesario(s) llenarlo(s) en su data provider para el alta de estatus mensajeria
	 * @return: Ninguno
	 */
    public void clickBotonEditar(String idEstatus) {
    	
		By LocBtnEditar = By.xpath(String.format(StrChkEditaStat, idEstatus));
		u.highLight(LocBtnEditar);
	    u.click(LocBtnEditar);
	    
		testCase.addScreenShotCurrentStep(u, "clickBotonEditar");
		u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para llenar los campos de Estatus-mensajeria a modificar
	 * @author: Manuel Cuen
	 * @param:  descripModif,esFin, es/son necesario(s) llenarlo(s) en su data provider para su modificacion de datos
	 * @return: Ninguno
	 */
    public void clickModificaDatos(String descripModif, String esFin) {
    	
    	u.highLight(xmlDescripcionNvo);
    	u.getDriver().findElement(xmlDescripcionNvo).clear();
    	u.sendKeys(xmlDescripcionNvo, descripModif);
    	
		By LocEsFin = By.xpath(String.format(StrEsFin, esFin));
		System.out.println(LocEsFin);
	    u.click(LocEsFin);
	    
		testCase.addScreenShotCurrentStep(u, "clickModificaDatos");
		u.hardWait(2);
    	
    }
    
	/**
	 * Metodo:  Para dar clic en el boton Guardar
	 * @author: Manuel Cuen
	 * @param:  band, se utiliza solo para el orden y control de campos y botones, en ventana nuevo y editar
	 * @return: Ninguno
	 */
    public void clickBotonGuardar(Boolean band) {
    	
    	if (band == true) {
        	u.highLight(xmlBtnGuardarNvo);
        	u.click(xmlBtnGuardarNvo);

    	}

    	if (band == false) {
    		
        	u.highLight(xmlBtnGuardarEdi);
        	u.click(xmlBtnGuardarEdi);

    	}
    	
    	u.highLight(xmlBtnConfirGuardNvo);
    	u.click(xmlBtnConfirGuardNvo);
    	u.hardWait(2);
    	
		testCase.addScreenShotCurrentStep(u, "clickBotonGuardar");
		u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para llenar el filtro descripcion estatus-mensajeria y dar clic en el boton buscar
	 * @author: Manuel Cuen
	 * @param:  buscaNombre, es/son necesario(s) llenarlo(s) en su data provider para realizar una busqueda
	 * @return: Ninguno
	 */
    public void clickBuscaEstatus(String buscaNombre) {
    	
    	u.highLight(xmlDescripcionEstatusMsj);
    	u.sendKeys(xmlDescripcionEstatusMsj, buscaNombre);
    	
    	u.highLight(xmlBtnBuscar);
    	u.click(xmlBtnBuscar);
    	u.hardWait(2);
    	u.unhighLight(xmlDescripcionEstatusMsj);
    	u.unhighLight(xmlBtnBuscar);
    }
    
	/**
	 * Metodo:  Para seleccionar un estatus-mensajeria, por su id estatus
	 * @author: Manuel Cuen
	 * @param:  idestatus, es/son necesario(s) llenarlo(s) en su data provider para seleccionar estatus-mensajeria
	 * @return: Ninguno
	 */
    public void clickSelEstatus(String idestatus) {
    	
		By LocEstatusId = By.xpath(String.format(StrIdEstatus, idestatus));
	    u.click(LocEstatusId);
	    
		testCase.addScreenShotCurrentStep(u, "clickBtnEliminar");
		u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para Mostrar el boton eliminar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickHabilitaBtnEliminar() {
    	
    	u.highLight(xmlBtonEliminar);
    	
		testCase.addScreenShotCurrentStep(u, "clickHabilitaBtnEliminar");
		u.hardWait(2);
		u.unhighLight(xmlBtonEliminar);
    }
    
	/**
	 * Metodo:  Para dar clic en los botones eliminar y confirmar eliminar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickBtnEliminarConfirmar() {
    	
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    u.highLight(xmlBtonEliminar);
	    u.click(xmlBtonEliminar);
	    
	    u.highLight(xmlConfEliminar);
	    u.click(xmlConfEliminar);
	    u.hardWait(2);
	    
		testCase.addScreenShotCurrentStep(u, "clickBtnEliminarConfirmar");
		u.hardWait(2);
    }
    
    public void clickBuscaEsFinSi() {
    	
    	for(int i = 1; i<=3; i++) {
    		String StrEsFinSi = ("//table/tbody/tr[i]/td[4][contains(text(),'Si')]");
    		
    	}
    }
    
	
    
	/**
	 * Metodo:  Para seleccionar los filtros y buscar un pedido con estatus autorizado
	 * @author: Manuel Cuen
	 * @param:  tipoPedido,Plaza,estatus, es/son necesario(s) llenarlo(s) en su data provider para buscar pedido
	 * @return: Ninguno
	 */
    public void clickCapturaPedido(String tipoPedido,String Plaza,String estatus) {
    	
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlTipoPedido);
    	u.click(xmlTipoPedido);
    	
		By LocTipoPed = By.xpath(String.format(StrChkTipoPed, tipoPedido));
	    u.click(LocTipoPed);
	    u.mouseOverAndClick(xmlPlazaFlecha);
	    
	    u.highLight(xmlPlazaFlecha);
	    u.click(xmlPlazaFlecha);
	    
		By LocPlaza = By.xpath(String.format(StrChkPlaza, Plaza));
	    u.click(LocPlaza);
	    u.mouseOverAndClick(xmlEstatus);
	    
    	u.highLight(xmlEstatus);
    	u.click(xmlEstatus);
    	
		By LocEstatus = By.xpath(String.format(StrChkEstatus, estatus));
	    u.click(LocEstatus);
	    u.mouseOverAndClick(xmlPlazaFlecha);
	    
	    u.highLight(xmlBtnBuscarBp);
	    u.click(xmlBtnBuscarBp);
	    u.hardWait(2);
	    
		testCase.addScreenShotCurrentStep(u, "clickCapturaPedido");
		u.hardWait(2);
    	
    }
    
	/**
	 * Metodo:  Para Mostrar/resaltar elementos de la pantalla datos de pedido y guia
	 * @author: Manuel Cuen
	 * @param:  folioPedido, es/son necesario(s) llenarlo(s) en su data provider para mostrar pedido
	 * @return: Ninguno
	 */
    public void clickFolioPedido(String folioPedido) {
    	
		By LocFolioPed = By.xpath(String.format(StrClicFolioPed, folioPedido));
		System.out.println(LocFolioPed);
	    u.click(LocFolioPed);
	    
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
	    //u.highLight(xmlVentanaDetPedido);
	    u.highLight(xmlLblDetPedido);
	    u.highLight(xmlLblDatosPed);
	    u.highLight(xmlLblFolio);
	    u.highLight(xmlLblEstatus);
	    u.highLight(xmlLblPeriodo);
	    u.highLight(xmlLblPlaza);
	    u.highLight(xmlLblTipoPed);
	    u.highLight(xmlLblUsuario);
	    u.highLight(xmlLblFecha);
	    
	    u.highLight(xmlLblComentario);
	    
	    u.highLight(xmlLblGuias);
	    u.highLight(xmlLblNumGuia);
	    u.highLight(xmlLblProveedor);
	    u.highLight(xmlLblEstatusGuia);
	    u.highLight(xmlLblRecibido);
	    u.highLight(xmlLblFechaGuia);
	    u.highLight(xmlLblExportar);
	    u.highLight(xmlBtnAgregaGuia);
	    
	    u.highLight(xmlLblMaterialSol);
	    u.highLight(xmlIconImagen);
	    u.highLight(xmlLblPopId);
	    u.highLight(xmlLblPop);
	    u.highLight(xmlLblContenido);
	    u.highLight(xmlLblDescrip);
	    u.highLight(xmlLblPrecio);
	    u.highLight(xmlLblCantidad);
	    u.highLight(xmlLblTotal);
	    u.highLight(xmlLblImprimPcio);
	    u.highLight(xmlLblComentarMat);
	    
		testCase.addScreenShotCurrentStep(u, "clickFolioPedido");
		u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para validar ventana y elementos de la ventana pedido
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickValidaVentanaDetPedidoGuia() {
    	
    	 m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
    	 
    	 Boolean Validacion = false;
    	 Boolean Validacion1 = false, DetallePedido, DatosPedido, Folio, Estatus, Periodo, Plaza, TipoPedido, Usuario, Fecha;
    	 Boolean Validacion2 = false, Comentario, Guias, NumGuia, Proveedor, EstatusGuia, FechaGuia, Exportar, BtnAgregaGuia;
    	 Boolean Validacion3 = false, MaterialSol, IconImagen, PopId, Pop, Contenido, Descrip, Precio, Cantidad, Total, ImprimPcio, ComentarMat;
    			 
    	 DetallePedido = u.isDisplayed(xmlLblDetPedido);
    	 DatosPedido = u.isDisplayed(xmlLblDatosPed);
    	 Folio = u.isDisplayed(xmlLblFolio);
    	 Estatus = u.isDisplayed(xmlLblEstatus);
    	 Periodo = u.isDisplayed(xmlLblPeriodo);
    	 Plaza = u.isDisplayed(xmlLblPlaza);
    	 TipoPedido = u.isDisplayed(xmlLblTipoPed);
    	 Usuario = u.isDisplayed(xmlLblUsuario);
    	 Fecha = u.isDisplayed(xmlLblFecha);
    	 
    	 Comentario = u.isDisplayed(xmlLblComentario);
    	 Guias = u.isDisplayed(xmlLblGuias);
    	 NumGuia = u.isDisplayed(xmlLblNumGuia);
    	 Proveedor = u.isDisplayed(xmlLblProveedor);
    	 EstatusGuia = u.isDisplayed(xmlLblEstatusGuia);
    	 FechaGuia = u.isDisplayed(xmlLblFechaGuia);
    	 Exportar = u.isDisplayed(xmlLblExportar);
    	 BtnAgregaGuia = u.isDisplayed(xmlBtnAgregaGuia);
    	 
    	 MaterialSol = u.isDisplayed(xmlLblMaterialSol);
    	 IconImagen = u.isDisplayed(xmlIconImagen);
    	 PopId = u.isDisplayed(xmlLblPopId);
    	 Pop = u.isDisplayed(xmlLblPop);
    	 Contenido = u.isDisplayed(xmlLblContenido);
    	 Descrip = u.isDisplayed(xmlLblDescrip);
    	 Precio = u.isDisplayed(xmlLblPrecio);
    	 Cantidad = u.isDisplayed(xmlLblCantidad);
    	 Total = u.isDisplayed(xmlLblTotal);
    	 ImprimPcio = u.isDisplayed(xmlLblImprimPcio);
    	 ComentarMat = u.isDisplayed(xmlLblComentarMat);
    	 
    	 if (DetallePedido == true && DatosPedido == true && Folio == true && Estatus == true && Periodo == true && Plaza == true && TipoPedido == true && Usuario == true && Fecha == true) {
    		 
    		 Validacion1 = true; 
    		 
    	 }
    	 
    	 if (Comentario == true && Guias == true && NumGuia == true && Proveedor == true && EstatusGuia == true && FechaGuia == true && Exportar == true && BtnAgregaGuia == true) {
    		 
    		 Validacion2 = true; 
    		 
    	 }
    	 
    	 if (MaterialSol == true && IconImagen == true && PopId == true && Pop == true && Contenido == true && Descrip == true && Precio == true && Cantidad == true && Total == true && ImprimPcio == true && ComentarMat == true) {
    		 
    		 Validacion3 = true; 
    		 
    	 }
    	 
    	 if (Validacion1 == true && Validacion2 == true && Validacion3 == true) {
    		 
    		 Validacion = true;
    		 
    	 }
    	 
    	 if (Validacion == true) {
    		 System.out.println("Caso 10: PASADO");
    	 }
    	 
    }
	/**
	 * Metodo:  Para seleccionar estatus-esFinSi=  "Si"
	 * @author: Manuel Cuen
	 * @param:  totElem,minimoSob es/son necesario(s) llenarlo(s) en su data provider para mostrar pedido
	 * @return: Ninguno
	 */
    public void clickBuscaFinSi(int NumPagRedond, int totElem, int minimoSob) {
    	WebElement WebEle;
    	By LocFinSi = By.xpath(StrBuscaFinSi); //
    	String descripEstatus = "";
    	List<WebElement> allOptions = u.getDriver().findElements(LocFinSi);// crea una variable(allOptions) de tipo lista para guardar todos los elemetos de la columna es fin si del grid
    	int acum,diferen = 0; 
    	acum = allOptions.size();
    	System.out.println(NumPagRedond);
    	 
    	 for (int j = 0; j <= NumPagRedond; j++) {
    		 
    		 for (int i = 0; i <= allOptions.size()-1; i++) { // Recorre el grid
    			 System.out.println("Valor j: "+ j + " Valor i: "+ i);
    			 if (allOptions.get(i).getText().equals("Si")) { //Compara la primer posicion del ciclo, con la cadena "Si"
 
    				 m.highlight(allOptions.get(i));
    				 //String LocDescripEstatus = StrBuscaFinSi + "/preceding-sibling::td[1]";
    				 WebEle = allOptions.get(i).findElement(By.xpath("./preceding-sibling::td[1]")); // posicionan en la descripcion del registro que esta en "Si"
    				 descripEstatus = WebEle.getText();   // guarda en la var la descripcion
    				 m.highlight(WebEle); // resalta la pura descripcion
    				 u.hardWait(2);
    			 }
    			 
    			 if (i == allOptions.size()-1) {
    					testCase.addScreenShotCurrentStep(u, "clickBuscaFinSi");
    					u.hardWait(2);
    			 }
    			 diferen = totElem - acum;
    			 
    			 if (diferen >= minimoSob && i >= allOptions.size()-1) {
    			 //if (i >= allOptions.size()-1) {
    				 u.highLight(xmlAvancePagina);
        			 u.click(xmlAvancePagina);
        			 u.hardWait(2);
        			 u.unhighLight(xmlAvancePagina);
    				 allOptions = u.getDriver().findElements(LocFinSi);
    				 acum = acum + allOptions.size();
        		 }
    			 
    		 }
    		 
    	 } 
    	 System.out.println(descripEstatus);
    	 
    }
    
	/**
	 * Metodo:  Para dar clic en el elemento estatus
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickCambiaStatusActivo() {
    	
    	u.highLight(xmlSelectEstatus);
    	u.hardWait(2);
    	
		testCase.addScreenShotCurrentStep(u, "clickCambiaStatusActivo");
		u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para capturar tres estatus diferentes al esFinSi = "Si"
	 * @author: Manuel Cuen
	 * @param:  cambiaEstatUno,cambiaEstatDos, es/son necesario(s) llenarlo(s) en su data provider para hacer cambio de estatus
	 * 			band, esta variable para el control de los datos de estatus, saber cuando es uno, dos y tres
	 * @return: Ninguno
	 */
    public void clickCambiaEstatus(String cambiaEstatUno, String cambiaEstatDos, Boolean band) {
   	
//		u.highLight(xmlTxtRecibidoPor);
//		u.click(xmlTxtRecibidoPor);
//		u.hardWait(2);
//		u.unhighLight(xmlTxtRecibidoPor);
//    	
//    	u.highLight(xmlSelectEstatus);
//    	u.click(xmlSelectEstatus);
//    	u.hardWait(2);
//    	u.unhighLight(xmlSelectEstatus);
//    	
//		By LocCambiaEstatus = By.xpath(String.format(StrCambiaStats, cambiaEstatUno));
//	    u.click(LocCambiaEstatus);
//	    u.hardWait(2);
//		testCase.addScreenShotCurrentStep(u, "clickCambiaEstatus");
//		u.hardWait(2);
		
		u.highLight(xmlTxtRecibidoPor);
		u.click(xmlTxtRecibidoPor);
		u.hardWait(2);
		u.unhighLight(xmlTxtRecibidoPor);
		
		// Estatus Dos
    	if (band == true) {
    		u.highLight(xmlSelectEstatusDos);
    		u.click(xmlSelectEstatusDos);
    		u.hardWait(2);
    	
    		By LocCambiaEstatusDos = By.xpath(String.format(StrCambiaStats, cambiaEstatDos));
    		System.out.println(LocCambiaEstatusDos);
    		u.click(LocCambiaEstatusDos);
    		u.hardWait(2);
    		testCase.addScreenShotCurrentStep(u, "clickCambiaEstatus");
    		u.hardWait(2);
    		//u.unhighLight(xmlSelectEstatus);
    	}
    }
    
}