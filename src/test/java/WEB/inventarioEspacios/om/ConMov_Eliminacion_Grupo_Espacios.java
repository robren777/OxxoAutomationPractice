package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;


public class ConMov_Eliminacion_Grupo_Espacios {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - ConMov_Eliminacion_Grupo_Espacios
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    By xmlMenuEspacio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div");
    By xmlMenuEspacioMar = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[5]/div/label");
    By xmlSmenuGpoEspacio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[6]/a/div/label");
    By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
    
    // Pantalla consulta de grupo de espacios
    By xmlNombreGpo = By.xpath("//*[@id=\"mat-input-0\"]");
    By xmlLblIdGpo = By.xpath("//*[@id=\"content-nav\"]/app-spaces-group-list/div/div/table/thead/tr/th[2]");
    By xmlLblNombreGpo = By.xpath("//*[@id=\"content-nav\"]/app-spaces-group-list/div/div/table/thead/tr/th[3]");
    By xmlLblMensajeError = By.xpath("//*[@id=\"content-nav\"]/app-spaces-group-list/div/div/table/thead/tr/th[4]");
    By xmlLblAplicarVal = By.xpath("//*[@id=\"content-nav\"]/app-spaces-group-list/div/div/table/thead/tr/th[5]");
    By xmlBtnEditar = By.xpath("//*[@id=\"content-nav\"]/app-spaces-group-list/div/div/table/tbody/tr[1]/td[6]/mat-icon");
    By xmlBtnNuevo = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[1]/button/span[1]");
    By xmlBtnLimpiar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[3]/button/span[1]");
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[4]/button/span[1]");
    
    // Pantalla Nuevo Grupo de espacios
    By xmlNomGpoNuevo = By.xpath("//*[@id=\"mat-input-1\"]");
    By xmlComentario = By.xpath("//*[@id=\"mat-input-2\"]");
    By xmlAplicar = By.xpath("//*[@id=\"content-nav\"]/app-spaces-group-detail/div/div/div[1]/div[3]/mat-label");
    By xmlAplicarSiNo = By.xpath("//*[@id=\"content-nav\"]/app-spaces-group-detail/div/div/div[1]/div[4]/mat-radio-group");
    By xmlBtnCancelarNvo = By.xpath("//*[@id=\"content-nav\"]/app-spaces-group-detail/div/div/div[2]/div/div[1]/button/span[1]");
    By xmlBtnLimpiarNvo = By.xpath("//*[@id=\"content-nav\"]/app-spaces-group-detail/div/div/div[2]/div/div[2]/button/span[1]");
    By xmlBtnGuardarNvo = By.xpath("//*[@id=\"content-nav\"]/app-spaces-group-detail/div/div/div[2]/div/div[3]/button/span[1]");
    By xmlBtnGuardarEdi = By.xpath("//*[@id=\"content-nav\"]/app-spaces-group-detail/div/div/div[2]/div/div[2]/button/span[1]");
    By xmlBtnConfGuardar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    // Ventana modal de confirmacion de eliminacion
    By xmlMsjConfEliminar = By.xpath("//*[@id=\"mat-dialog-title-0\"]");
    By xmlBtnCancelConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/button/span[1]");
    By xmlBtnConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    // Paso 6
    By xmlBtnEliminar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[2]/button/span[1]");
    By xmlBtnConfirEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador Aplicar Si No
    String SrtAplicarSiNo = ("//mat-radio-group/mat-radio-button/label/span[2][contains(text(),'%s')]");
    String SrtEditar = ("//table/tbody/tr/td[contains(text(),' %s ')]//following::td[4]/mat-icon");
    
    // Localizador checkbox Eliminacion Grupo Espacio
    String StrCheckGpoEspacio = ("//tbody/tr/td[contains(text(),' %s ')]/preceding-sibling::td/mat-checkbox");
	
	/**
	 * Constructor
	 ******************************************************************************************/
	public ConMov_Eliminacion_Grupo_Espacios(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
	}

	
	
	/**
	 * Metodo:  Para activar el checkbox y eliminar un grupo de espacio 
	 * @author: Manuel CUen
	 * @param:  grupoId-se necesita indicar en su dp, el IdGrupo que desee eliminar
	 *          negocio: Ocupa llenar este campo en data-provider para que tome correcto su localizador de acuerdo al negocio
	 * @return: Ninguno
	 */
	
	public void clickMenuGpoEspacio(String grupoId, String negocio) {
		
		u.hardWait(2);
		if(negocio.toUpperCase().equals("MARCA")) {
			u.mouseOver(xmlMenuEspacioMar);
			u.highLight(xmlMenuEspacioMar);
	        u.click(xmlMenuEspacioMar);
		} else {
			u.mouseOver(xmlMenuEspacio);
			u.highLight(xmlMenuEspacio);
	        u.click(xmlMenuEspacio);			
		}
        
        u.highLight(xmlSmenuGpoEspacio);
        u.click(xmlSmenuGpoEspacio);
        u.mouseOver(xmlHeader);
        
        By LocGrupoEstatusId = By.xpath(String.format(StrCheckGpoEspacio, grupoId));
	    u.click(LocGrupoEstatusId);
	    
	    u.highLight(xmlBtnEliminar);
	    u.click(xmlBtnEliminar);
	    
	    u.highLight(xmlBtnConfirEliminar);
	    u.click(xmlBtnConfirEliminar);
	    
	    testCase.addScreenShotCurrentStep(u, "clickMenuPedidoAdmin");
	    u.hardWait(2);
	}
	
	/**
	 * Metodo:  Para dar clic en el submenu grupo de espacios 
	 * @author: Manuel CUen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickGpoEspaciosEquipos() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.mouseOver(xmlMenuEspacioMar);
		u.highLight(xmlMenuEspacioMar);
        u.click(xmlMenuEspacioMar);
        
        u.highLight(xmlSmenuGpoEspacio);
        u.click(xmlSmenuGpoEspacio);
        u.mouseOver(xmlHeader);
        
	    testCase.addScreenShotCurrentStep(u, "clickMenuPedidoAdmin");
	    u.hardWait(2);
        
	}
	
	/**
	 * Metodo:  Para mostrar/resaltar elementos de la pantalla grupo de espacios 
	 * @author: Manuel CUen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMuestraFiltros() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlNombreGpo);
		u.highLight(xmlBtnNuevo);
		u.highLight(xmlBtnLimpiar);
		u.highLight(xmlBtnBuscar);
		
	    testCase.addScreenShotCurrentStep(u, "clickMuestraFiltros");
	    u.hardWait(2);
	    u.unhighLight(xmlNombreGpo);
		u.unhighLight(xmlBtnNuevo);
		u.unhighLight(xmlBtnLimpiar);
		u.unhighLight(xmlBtnBuscar);
	}

	/**
	 * Metodo:  Para mostrar/resaltar elementos de la tabla/grid grupo de espacios 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMuestraGrid() {
		
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    u.highLight(xmlLblIdGpo);
	    u.highLight(xmlLblNombreGpo);
	    u.highLight(xmlLblMensajeError);
	    u.highLight(xmlLblAplicarVal);
	    u.highLight(xmlBtnEditar);
		
	    testCase.addScreenShotCurrentStep(u, "clickMuestraGrid");
	    u.hardWait(2);
	    u.unhighLight(xmlLblIdGpo);
	    u.unhighLight(xmlLblNombreGpo);
	    u.unhighLight(xmlLblMensajeError);
	    u.unhighLight(xmlLblAplicarVal);
	    u.unhighLight(xmlBtnEditar);
	}
	
	/**
	 * Metodo:  Para dar clic en el boton nuevo
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickBtnNuevo() {
		
		u.highLight(xmlBtnNuevo);
		u.click(xmlBtnNuevo);
		
	    testCase.addScreenShotCurrentStep(u, "clickBtnNuevo");
	    u.hardWait(2);
	}
	
	/**
	 * Metodo:  Para mostrar elementos de la pantalla boton nuevo 
	 * @author: Manuel Cuen
	 * @param:  bandera, Se utiliza solo para controlar pantalla nuevo y editar
	 * @return: Ninguno
	 */
	public void clickMuestraPantNuevo(Boolean bandera) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlNomGpoNuevo);
		u.highLight(xmlComentario);
		u.highLight(xmlAplicar);
		u.highLight(xmlBtnCancelarNvo);
		if (bandera == true) {
			u.highLight(xmlBtnLimpiarNvo);
			u.highLight(xmlBtnGuardarNvo);
		}
		if (bandera == false) {
			//u.highLight(xmlBtnLimpiarNvo);
			u.highLight(xmlBtnGuardarEdi);
		}
		
	    testCase.addScreenShotCurrentStep(u, "clickMuestraPantNuevo");
	    u.hardWait(2);
		u.unhighLight(xmlNomGpoNuevo);
		u.unhighLight(xmlComentario);
		u.unhighLight(xmlAplicar);
		u.unhighLight(xmlBtnCancelarNvo);
		if (bandera == true) {
			u.unhighLight(xmlBtnLimpiarNvo);
			u.unhighLight(xmlBtnGuardarNvo);
		}
		if (bandera == false) {
			//u.unhighLight(xmlBtnLimpiarNvo);
			u.unhighLight(xmlBtnGuardarEdi);
		}
		
	}
	
	/**
	 * Metodo:  Para capturar los datos de la pantalla nuevo 
	 * @author: Manuel Cuen
	 * @param:  nombreGrupo,comentario,Aplicar es/son necesario(s) llenarlo(s) en su data provider, para dar alta
	 * @return: Ninguno
	 */
	public void clickCapturaDatos(String nombreGrupo, String comentario, String Aplicar) {
		
		u.highLight(xmlNomGpoNuevo);
		u.getDriver().findElement(xmlNomGpoNuevo).clear();
		u.sendKeys(xmlNomGpoNuevo, nombreGrupo);
		
		u.highLight(xmlComentario);
		u.getDriver().findElement(xmlComentario).clear();
		u.sendKeys(xmlComentario, comentario);
		
		By LocAplicar= By.xpath(String.format(SrtAplicarSiNo, Aplicar));
		System.out.println(LocAplicar);
		u.click(LocAplicar);
		
	    testCase.addScreenShotCurrentStep(u, "clickCapturaDatos");
	    u.hardWait(2);

	}
	
	/**
	 * Metodo:  Para dar clic en el boton guardar 
	 * @author: Manuel Cuen
	 * @param:  bandera, Se utiliza solo para controlar pantalla nuevo y editar
	 * @return: Ninguno
	 */
	
	public void clikBtnGuardar(Boolean bandera) {
		
		if (bandera == false) {
			
			u.highLight(xmlBtnGuardarEdi);
		    u.click(xmlBtnGuardarEdi);
		    
		    u.highLight(xmlBtnConfGuardar);
		    u.click(xmlBtnConfGuardar);
			
		    testCase.addScreenShotCurrentStep(u, "clikBtnGuardar");
		    u.hardWait(2);

		}
		
		if (bandera == true) {
		
			u.highLight(xmlBtnGuardarNvo);
		    u.click(xmlBtnGuardarNvo);
		    
		    u.highLight(xmlBtnConfGuardar);
		    u.click(xmlBtnConfGuardar);
			
		    testCase.addScreenShotCurrentStep(u, "clikBtnGuardar");
		    u.hardWait(2);

		}

		

	    
   
	}
	
	/**
	 * Metodo:  Para dar guardar evidencia de mensaje guardado 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMensajeCrea() {
		
	    testCase.addScreenShotCurrentStep(u, "clickMensajeCrea");
	    u.hardWait(2);
	}
	
	/**
	 * Metodo:  Para llenar filtro nombre de grupo estatus y dar clic en el boton buscar 
	 * @author: Manuel Cuen
	 * @param:  BuscarGrupo, es/son necesario(s) llenarlo(s) en su data provider, para buscar grupo y eliminar o editar
	 * @return: Ninguno
	 */
	public void clickBusquedaGpo(String BuscarGrupo) {
		
		u.highLight(xmlNombreGpo);
		u.sendKeys(xmlNombreGpo,BuscarGrupo);
		
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
		
	    testCase.addScreenShotCurrentStep(u, "clickBusquedaGpo");
	    u.hardWait(2);
	    u.unhighLight(xmlNombreGpo);
	    u.unhighLight(xmlBtnBuscar);
	}
	
	/**
	 * Metodo:  Para seleccionar un grupo de espacio y editar 
	 * @author: Manuel Cuen
	 * @param:  IdGrupo, es/son necesario(s) llenarlo(s) en su data provider, para modificar grupo deseado
	 * @return: Ninguno
	 */
	public void clickEditarGpo(String IdGrupo) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlBtnEditar);
		
		
		By LocGrupoEspacio= By.xpath(String.format(SrtEditar, IdGrupo));
		u.click(LocGrupoEspacio);
		
	    testCase.addScreenShotCurrentStep(u, "clickEditarGpo");
	    u.hardWait(2);
		
	}
	
	/**
	 * Metodo:  Para seleccionar un grupo de espacio a eliminar 
	 * @author: Manuel Cuen
	 * @param:  grupoId, es/son necesario(s) llenarlo(s) en su data provider, para eliminar grupo deseado
	 * @return: Ninguno
	 */
	public void clickSelGrupoEliminar(String grupoId) {
		
        By LocGrupoEstatusId = By.xpath(String.format(StrCheckGpoEspacio, grupoId));
	    u.click(LocGrupoEstatusId);
	    
	    testCase.addScreenShotCurrentStep(u, "clickSelGrupoEliminar");
	    u.hardWait(2);
	}
	
	/**
	 * Metodo:  Para mostrar/resaltar boton eliminar 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMuestraBtnEliminar() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlBtnEliminar);
		
	    testCase.addScreenShotCurrentStep(u, "clickMuestraBtnEliminar");
	    u.hardWait(2);
	    u.unhighLight(xmlBtnEliminar);
	}
	
	/**
	 * Metodo:  Para dar clic en boton eliminar 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickBtnEliminar() {
		
	    u.highLight(xmlBtnEliminar);
	    u.click(xmlBtnEliminar);

	    testCase.addScreenShotCurrentStep(u, "clickBtnEliminar");
	    u.hardWait(2);
	}
	
	/**
	 * Metodo:  Para Mostrar/resaltar mensaje y elementos de ventana modal eliminar 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickVentanaModalEliminar() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlMsjConfEliminar);
		u.highLight(xmlBtnCancelConfEliminar);
		u.highLight(xmlBtnConfEliminar);
		
	    testCase.addScreenShotCurrentStep(u, "clickVentanaModalEliminar");
	    u.hardWait(2);
		u.unhighLight(xmlMsjConfEliminar);
		u.unhighLight(xmlBtnCancelConfEliminar);
		u.unhighLight(xmlBtnConfEliminar);
		
	    testCase.addScreenShotCurrentStep(u, "clickVentanaModalEliminar");
	    u.hardWait(2);
	}
	
	/**
	 * Metodo:  Para dar clic en boton confirmar eliminar 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickBtnConfirmacionElim() {
		
	    u.highLight(xmlBtnConfirEliminar);
	    u.click(xmlBtnConfirEliminar);
	    
	    testCase.addScreenShotCurrentStep(u, "clickBtnConfirmacionElim");
	    u.hardWait(2);
	}
	
	/**
	 * Metodo:  Para guardar/evidenciar mensaje de eliminado 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMensajeEliminado() {
		
	    testCase.addScreenShotCurrentStep(u, "clickMensajeEliminado");
	    u.hardWait(2);
	}
}
