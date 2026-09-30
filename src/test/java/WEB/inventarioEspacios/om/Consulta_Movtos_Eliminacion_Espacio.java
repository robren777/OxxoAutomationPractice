package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_Espacio {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_espacios/equipos
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    By xmlMenuEspacio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div");
    By xmlMenuEspacioM = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[5]/div/label");
    By xmlSespacioEquipos = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[2]/a/div/label"); 
    By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
    By xmlFlecha = By.xpath("//*[@id=\"mat-expansion-panel-header-0\"]/span[2]");
    
    By xmlSistemas = By.xpath("//*[@id=\"mat-select-value-1\"]");
    By xmlNomEspacios = By.xpath("//*[@id=\"mat-input-1\"]");
    By xmlNomEquipos = By.xpath("//*[@id=\"mat-input-2\"]");
    
    By xmlSolicita = By.xpath("//*[@id=\"mat-input-0\"]");
    By xmlPlazas = By.xpath("//*[@id=\"formFiltroEquipos\"]/div[1]/div[2]/div[1]/mat-label");
    
    By xmlBtnNuevo = By.xpath("//*[@id=\"formFiltroEquipos\"]/div[2]/div/div[1]/button/span[1]");
    By xmlBtnLimpiar = By.xpath("//*[@id=\"formFiltroEquipos\"]/div[2]/div/div[2]/button/span[1]");
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroEquipos\"]/div[2]/div/div[3]/button/span[1]");
    
    By xmlLblId = By.xpath("//*[@id=\"content-nav\"]/app-spaces-list/div/div/table/thead/tr/th[2]");
    By xmlLblSist = By.xpath("//*[@id=\"content-nav\"]/app-spaces-list/div/div/table/thead/tr/th[3]");
    By xmlLblNom = By.xpath("//*[@id=\"content-nav\"]/app-spaces-list/div/div/table/thead/tr/th[4]");
    By xmlLblSoli = By.xpath("//*[@id=\"content-nav\"]/app-spaces-list/div/div/table/thead/tr/th[5]");
    By xmlLblNegoc = By.xpath("//*[@id=\"content-nav\"]/app-spaces-list/div/div/table/thead/tr/th[6]");
    By xmlLblGpoEsp = By.xpath("//*[@id=\"content-nav\"]/app-spaces-list/div/div/table/thead/tr/th[7]");
    By xmlLblSPlazas = By.xpath("//*[@id=\"content-nav\"]/app-spaces-list/div/div/table/thead/tr/th[8]");
    By xmlBtnEditar = By.xpath("//*[@id=\"content-nav\"]/app-spaces-list/div/div/table/tbody/tr[1]/td[9]/mat-icon[1]");
    By xmlBtnImagen = By.xpath("//*[@id=\"content-nav\"]/app-spaces-list/div/div/table/tbody/tr[1]/td[9]/mat-icon[2]");
    
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltroEquipos\"]/div[2]/div/div[2]/button/span[1]");                                
    By xmlConfirEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    // Pantalla Boton Nuevo
    By xmlEspacioEquipoFil = By.xpath("//*[@id=\"mat-select-value-1\"]");
    By xmlEspacioEquipoNvo = By.xpath("//*[@id=\"mat-select-value-5\"]");
    By xmlNomEspacioNvo = By.xpath("//form/div/div/div[2]/mat-form-field/div/div/div/input");
    By xmlNomSoliciNvo = By.xpath("//*[@id=\"mat-input-2\"]");
    By xmlGpoEspacio = By.xpath("//*[@id=\"mat-select-value-7\"]");
    By xmlNegocioNvo = By.xpath("//*[@id=\"formEquipos\"]/div[1]/div[1]/div[5]/mat-label");
    By xmlPlazasNvo = By.xpath("//*[@id=\"formEquipos\"]/div[1]/div[2]/div[1]/mat-label");
    By xmlBtnCancelNvo = By.xpath("//*[@id=\"formEquipos\"]/div[2]/div[1]/button/span[1]");
    By xmlBtnLimpiaNvo = By.xpath("//*[@id=\"formEquipos\"]/div[2]/div[2]/button/span[1]");
    By xmlBtnGuardaNvo = By.xpath("//*[@id=\"formEquipos\"]/div[2]/div[3]/button/span[1]");
    
    // Pantalla Editar
    By xmlBtnGuardarEdi = By.xpath("//*[@id=\"formEquipos\"]/div[2]/div[2]/button/span[1]");
    
    // Ventana Modal Eliminar
    By xmlMsjBtnElimar = By.xpath("//*[@id=\"mat-dialog-title-0\"]");
    By xmlModalBtnCancelar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/button/span[1]");
    By xmlModalBtnConfirmar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    //By xmlTpoMov = By.xpath("//div[@id=\"mat-select-0-panel\"]/mat-option/span[contains(text(),' Eliminaci�n de Espacio ')]");
    //table/tbody/tr/td[2][contains(text(), '1636')]/preceding-sibling::td/mat-checkbox  - para seleccionar precesores en una tabla
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
    String StrEspacioId = ("//table/tbody/tr/td[2][contains(text(), '%s')]/preceding-sibling::td/mat-checkbox");
   
//    String StrEspacioEquipo = ("//div[@id=\"mat-select-4-panel\"]/mat-option/span[contains(text(),'%s')]");
    String StrEspacioEquipo = ("//mat-option/span[contains(text(),'%s')]");
  //*[@id="mat-option-6"]/span
    String StrGpoEspacioNvo = ("//div[@id=\"mat-select-6-panel\"]/mat-option/span[contains(text(),'%s' )]");
    String StrPlazaNvo = ("//section/span[2]/ul/div/li/mat-checkbox/label/span[2][contains(text(),' %s ')]//preceding-sibling::span");
    String StrIconEdit = ("//table/tbody/tr/td[2][contains(text(),' %s ')]//following::td[7]/mat-icon");
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_Espacio(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para seleccionar un Id de espacio y dar click en el boton Eliminar y luego consultar el movto
	 * 
	 * @author Manuel Cuen
	 * @param Id Espacio
	 * @return ninguno
	 */
		
	public void clickMenuEspaciosAdmin(String espacioId,String sistema,String NomSpaEquipo) {
		
		clickMenuSbEquiEspa();
		clickBusquedaFiltros(sistema,NomSpaEquipo);
		// Click a la casilla del Espacio_Id
		By LocEspacioId = By.xpath(String.format(StrEspacioId, espacioId));
		System.out.println(LocEspacioId);
		u.highLight(LocEspacioId);
	    u.click(LocEspacioId);
	    
	    // Boton Eliminar
	    u.highLight(xmlBtonEliminar);
	    u.click(xmlBtonEliminar);
	    u.hardWait(2);
	    u.highLight(xmlConfirEliminar);
	    u.click(xmlConfirEliminar);
	    u.hardWait(2);
	    
	    testCase.addScreenShotCurrentStep(u, "clickMenuEspaciosAdmin"); 
	    u.hardWait(2);
		
	}
	
	/**
	 * Método Entrar al Menu Espacios Admin-Consulta Espacios/Equipos
	 * 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickMenuSbEquiEspa() {
    	
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlMenuEspacioM);
		u.click(xmlMenuEspacioM);
		u.highLight(xmlSespacioEquipos);
		u.click(xmlSespacioEquipos);
		u.mouseOver(xmlHeader);
		
		testCase.addScreenShotCurrentStep(u, "clickMenuEspaciosAdmin"); 
	    u.hardWait(2);
    }

	/**
	 * Método Resaltar Elementos de la pantalla Consulta Espacios/Equipos
	 * 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickPanEspaciosEqpos() {
    	
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlSistemas);
    	u.highLight(xmlNomEspacios);
    	u.highLight(xmlSolicita);
    	u.highLight(xmlPlazas);
    	u.highLight(xmlBtnNuevo);
    	u.highLight(xmlBtnLimpiar);
    	u.highLight(xmlBtnBuscar);
    	
		testCase.addScreenShotCurrentStep(u, "clickPanEspaciosEqpos"); 
	    u.hardWait(2);
    	u.unhighLight(xmlSistemas);
    	u.unhighLight(xmlNomEspacios);
    	u.unhighLight(xmlSolicita);
    	u.unhighLight(xmlPlazas);
    	u.unhighLight(xmlBtnNuevo);
    	u.unhighLight(xmlBtnLimpiar);
    	u.unhighLight(xmlBtnBuscar);
    }
    
	/**
	 * Método Ingresar filtros y buscar
	 * 
	 * @author: Manuel Cuen
	 * @param:  NomSpaEquipo,solicitante: son necesarios llenarlos en su data provider, para su busqueda correcta
	 * @return: Ninguno
	 */
    public void clickBusquedaFiltros(String sistema, String nomEspBuscar) {
    	
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	
    	u.highLight(xmlEspacioEquipoFil);
    	u.click(xmlEspacioEquipoFil);
    	u.hardWait(2);
    	
    	By LocEspacioEq = By.xpath(String.format(StrEspacioEquipo, sistema));
    	System.out.println(LocEspacioEq);
	    u.click(LocEspacioEq);
	    
//	    String Texto = u.getText(xmlSistemas);
//	    
//	    if (Texto == nomEspBuscar) {
//	    	
//	    }
	    
	    System.out.println(sistema);
	    
	    if (sistema.toUpperCase().equals("ESPACIOS")) {
	    	
	    	u.highLight(xmlNomEspacios);
	    	u.sendKeys(xmlNomEspacios, nomEspBuscar);
	    }

	    if (sistema.toUpperCase().equals("EQUIPOS")) {
	    	
	    	u.highLight(xmlNomEquipos);
	    	u.sendKeys(xmlNomEquipos, nomEspBuscar);
	    }

	    
//    	u.highLight(xmlSolicita);
//    	u.sendKeys(xmlSolicita, solicitante);
//    	u.hardWait(2);
    	
    	u.highLight(xmlBtnBuscar);
    	u.click(xmlBtnBuscar);
    	
		testCase.addScreenShotCurrentStep(u, "clickBusquedaFiltros"); 
	    u.hardWait(2);
	    u.unhighLight(xmlBtnBuscar);
    	
    }
    
	/**
	 * Método Resaltar Elementos del grid, en la pantalla Consulta Espacios/Equipos
	 * 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
    public void clickResultadosBusqueda() {
    	
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.scrollToElement(xmlLblId);
    	u.highLight(xmlLblId);
    	u.highLight(xmlLblSist);
    	u.highLight(xmlLblNom);
    	u.highLight(xmlLblSoli);
    	u.highLight(xmlLblNegoc);
    	u.highLight(xmlLblGpoEsp);
    	u.highLight(xmlLblSPlazas);
    	u.highLight(xmlBtnEditar);
    	u.highLight(xmlBtnImagen);
    	
		testCase.addScreenShotCurrentStep(u, "clickResultadosBusqueda"); 
	    u.hardWait(2);
    	u.unhighLight(xmlLblId);
    	u.unhighLight(xmlLblSist);
    	u.unhighLight(xmlLblNom);
    	u.unhighLight(xmlLblSoli);
    	u.unhighLight(xmlLblNegoc);
    	u.unhighLight(xmlLblGpoEsp);
    	u.unhighLight(xmlLblSPlazas);
    	u.unhighLight(xmlBtnEditar);
    	u.unhighLight(xmlBtnImagen);
    }
    
    public void clickBotonNuevo() {
    	
    	u.highLight(xmlBtnNuevo);
    	u.click(xmlBtnNuevo);
    	
		testCase.addScreenShotCurrentStep(u, "clickBotonNuevo"); 
	    u.hardWait(2);
    }
    
    public void clickSelEspacioEq(String id_EspEqu, Boolean seleccion) {
    	
    	if (seleccion == true) {
    		
        	By LocIconEspacioEq = By.xpath(String.format(StrIconEdit, id_EspEqu));
        	u.highLight(LocIconEspacioEq);
    	    u.click(LocIconEspacioEq);
    	    u.hardWait(2);
    	    
    	}

    	if (seleccion == false) {
    	
    		u.hardWait(3);
        	By LocChkEspacioEq = By.xpath(String.format(StrEspacioId, id_EspEqu));
        	System.out.println(LocChkEspacioEq);
    	    u.click(LocChkEspacioEq);
    	    u.hardWait(2);
    	    
    	}
		testCase.addScreenShotCurrentStep(u, "clickSelEspacioEq"); 
	    u.hardWait(2);
    }
    
    public void clickMuestraBtnEliminar() {
    	
    	u.highLight(xmlBtonEliminar);
    	
		testCase.addScreenShotCurrentStep(u, "clickMuestraBtnEliminar"); 
	    u.hardWait(2);
	    u.unhighLight(xmlBtonEliminar);
    }
    
    public void clickBtnEliminar() {
    	
    	u.highLight(xmlBtonEliminar);
    	u.click(xmlBtonEliminar);
    	
		testCase.addScreenShotCurrentStep(u, "clickBtnEliminar"); 
	    u.hardWait(2);
	    u.unhighLight(xmlBtonEliminar);
    }
    
    public void clickVentanaModalEliminar() {
    	
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlMsjBtnElimar);
    	u.highLight(xmlModalBtnCancelar);
    	u.highLight(xmlModalBtnConfirmar);
    	
		testCase.addScreenShotCurrentStep(u, "clickVentanaModalEliminar"); 
	    u.hardWait(2);
    	u.unhighLight(xmlMsjBtnElimar);
    	u.unhighLight(xmlModalBtnCancelar);
    	u.unhighLight(xmlModalBtnConfirmar);
    }
    
    public void clickConfirmaEliminar() {
    	
    	u.highLight(xmlModalBtnConfirmar);
    	u.click(xmlModalBtnConfirmar);
    	
		testCase.addScreenShotCurrentStep(u, "clickConfirmaEliminar"); 
	    u.hardWait(2);
    }
    
    public void clickMsjConfirmaEliminar() {
    	
		testCase.addScreenShotCurrentStep(u, "clickMsjConfirmaEliminar"); 
	    u.hardWait(2);
    }
    
    public void clickMuestraPanBtnNuevo(Boolean bandera) {
    	
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlEspacioEquipoNvo);
    	u.highLight(xmlNomEspacioNvo);
    	u.highLight(xmlNomSoliciNvo);
    	u.highLight(xmlGpoEspacio);
    	u.highLight(xmlNegocioNvo);
    	u.highLight(xmlPlazasNvo);
    	u.highLight(xmlBtnCancelNvo);
    	if (bandera == false) {
    		
    		u.highLight(xmlBtnGuardarEdi);
    		
    	}
    	if (bandera == true) {
    		
        	u.highLight(xmlBtnLimpiaNvo);
        	u.highLight(xmlBtnGuardaNvo);
        	
    	}
    	
		testCase.addScreenShotCurrentStep(u, "clickMuestraPanBtnNuevo"); 
	    u.hardWait(2);
    	u.unhighLight(xmlEspacioEquipoNvo);
    	u.unhighLight(xmlNomEspacioNvo);
    	u.unhighLight(xmlNomSoliciNvo);
    	u.unhighLight(xmlGpoEspacio);
    	u.unhighLight(xmlNegocioNvo);
    	u.unhighLight(xmlPlazasNvo);
    	u.unhighLight(xmlBtnCancelNvo);
    	if (bandera == false) {
    		
    		u.unhighLight(xmlBtnGuardarEdi);
    		
    	}

    	if (bandera == true) {
    		
        	u.unhighLight(xmlBtnLimpiaNvo);
        	u.unhighLight(xmlBtnGuardaNvo);
        	
    	}
    }
    
    public void clickCapturaEspacioEquipo(String espacioEquipo, String nomEspacioEq,String solicitante,String grupo,String plaza) {
    	
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlEspacioEquipoNvo);
    	u.click(xmlEspacioEquipoNvo);
    	u.hardWait(2);
    	
    	By LocEspacioEq = By.xpath(String.format(StrEspacioEquipo, espacioEquipo));
	    u.click(LocEspacioEq);
	    
	    u.highLight(xmlNomEspacioNvo);
	    u.sendKeys(xmlNomEspacioNvo, nomEspacioEq);
	    u.hardWait(2);
	    
    	u.highLight(xmlNomSoliciNvo);
    	u.sendKeys(xmlNomSoliciNvo, solicitante);
    	u.hardWait(2);
    	
    	u.highLight(xmlGpoEspacio);
    	u.click(xmlGpoEspacio);
    	u.hardWait(2);
    	
    	By LocGrupo = By.xpath(String.format(StrGpoEspacioNvo, grupo));
    	u.highLight(LocGrupo);
	    u.click(LocGrupo);
	    u.hardWait(2);
	    
    	By LocPlaza = By.xpath(String.format(StrPlazaNvo, plaza));
    	//u.highLight(LocPlaza);
	    u.click(LocPlaza);
	    
		testCase.addScreenShotCurrentStep(u, "clickCapturaEspacioEquipo"); 
	    u.hardWait(2);

    }
    
    public void clickBotonGuardar(Boolean band) {
    	
    	if (band == false) {
    		
    		u.unhighLight(xmlBtnGuardarEdi);
    		u.click(xmlBtnGuardarEdi);
    	}
    	
    	if (band == true) {
    		u.highLight(xmlBtnGuardaNvo);
    		u.click(xmlBtnGuardaNvo);
    		u.hardWait(2);
    	}
    	testCase.addScreenShotCurrentStep(u, "clickBotonGuardar"); 
	    u.hardWait(2);
    }
    
    public void clickMensajeGuardado() {
    	
    	testCase.addScreenShotCurrentStep(u, "clickMensajeGuardado"); 
	    u.hardWait(2);
	    
    }
    
    public void clickMuestraBtnEliminat() {
    	
    	
    }
    
    public void clickModificaDatos(String nomEspMod, String solicitanteMod,String grupo,String plaza) {
    	
	    u.highLight(xmlNomEspacioNvo);
	    u.getDriver().findElement(xmlNomEspacioNvo).clear();
	    u.sendKeys(xmlNomEspacioNvo, nomEspMod);
	    u.hardWait(2);
	    
    	u.highLight(xmlNomSoliciNvo);
    	u.getDriver().findElement(xmlNomSoliciNvo).clear();
    	u.sendKeys(xmlNomSoliciNvo, solicitanteMod);
    	u.hardWait(2);
    	
    	u.highLight(xmlGpoEspacio);
    	u.click(xmlGpoEspacio);
    	By LocGrupo = By.xpath(String.format(StrGpoEspacioNvo, grupo));
    	System.out.println(LocGrupo);
	    u.click(LocGrupo);
	    u.hardWait(2);
	    
//    	By LocPlaza = By.xpath(String.format(StrPlazaNvo, plaza));
//    	//u.highLight(LocPlaza);
//    	System.out.println(LocPlaza);
//	    u.click(LocPlaza);
	    
    	testCase.addScreenShotCurrentStep(u, "clickModificaDatos"); 
	    u.hardWait(2);
    }
}
