package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_Campo_Tda {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_Campo_Tda
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    By xmlMenusAd = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div");
    By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
    By xmlSmenuCamposTda = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[8]/a/div/label");
    // Paso 6
    By xmlLblVerDato = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/tbody/tr[3]/td[4]/mat-label");
    By xmlValor = By.xpath("//*[@id=\"mat-checkbox-8\"]");
    By xmlBtnEliminar = By.xpath("//*[@id=\"content-nav\"]/app-ver-datos-campo-tienda/div/div/div[4]/div/div[1]/button/span[1]");
    By xmlConfirBtnEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
      
    By xmlMenuPedAdmin = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[3]/div");
  
    // Paso 7
    By xmlLblNombre = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/thead/tr/th[1]");
    By xmlLblDescripcion = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/thead/tr/th[2]");
    By xmlLblVisible = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/thead/tr/th[3]");
    By xmlLblCampo1 = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/tbody/tr[1]/td[1]");
    By xmlLblCampo2 = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/tbody/tr[2]/td[1]");
    By xmlLblCampo3 = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/tbody/tr[3]/td[1]");
    By xmlLblCampo4 = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/tbody/tr[4]/td[1]");
    By xmlLblCampo5 = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/tbody/tr[5]/td[1]");
    By xmlLblCampo6 = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/tbody/tr[6]/td[1]");
    By xmlLblCampo7 = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/tbody/tr[7]/td[1]");
      
    //  Paso 8
    By xmlTxtDescripcion = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/tbody/tr[6]/td[2]/input");
    //By xmlChkVisible6 = By.xpath("//*[@id=\"mat-checkbox-23\"]/label/span[1]");
    By xmlChkVisible6 = By.xpath("//*[@id=\"mat-checkbox-6\"]");
    
    //   Paso 9
    By xmlBtnGuardar = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[3]/div/button/span[1]");
  
    // Paso 10
    By xmlChkEliminaValor = By.xpath("//*[@id=\"mat-checkbox-8\"]/label/span[1]");
    
    // Paso 11
    By xmlBtnEliminarValor = By.xpath("//*[@id=\"content-nav\"]/app-ver-datos-campo-tienda/div/div/div[4]/div/div[1]/button/span[1]");
    
    //   Paso 12
    By xmlVerDatos = By.xpath("//*[@id=\"content-nav\"]/app-campos-tienda/div/div[2]/table/tbody/tr[6]/td[4]/mat-label");
    By xmlMensajeElimar = By.xpath("//*[@id=\"mat-dialog-title-0\"]");
    
    //   Paso 13
    By xmlDescripValorNvo = By.xpath("//mat-form-field/div/div/div/input");
    By xmlBtnAgregar = By.xpath("//*[@id=\"content-nav\"]/app-ver-datos-campo-tienda/div/div/div[1]/div[2]/div/button/span[1]");
    By xmlLblValor = By.xpath("//*[@id=\"content-nav\"]/app-ver-datos-campo-tienda/div/div/div[3]/table/thead/tr/th[2]");
    By xmlBtnCancelar = By.xpath("//*[@id=\"content-nav\"]/app-ver-datos-campo-tienda/div/div/div[4]/div/div[2]/button/span[1]");
    By xmlBtnGuardarValorNvo = By.xpath("//*[@id=\"content-nav\"]/app-ver-datos-campo-tienda/div/div/div[4]/div/div[3]/button/span[1]");
    By xmlConfirmaEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    By xmlSbTiendas = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[6]/a/div/label");
    By xmlMenuEspacio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/label");                             
    By xmlSbInventario = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div[2]/mat-list/div[1]/a/div/label");   							  
    By xmlCampoTipoTda = By.xpath("//*[@id=\"mat-select-value-25\"]/span");
    By xmlTpoConsulta = By.xpath("//form[@name=\"filtroInventario\"]/div/div/div/mat-form-field/div/div/div/mat-select/div/div");
    By xmlPlaza = By.xpath("//form[@name=\"filtroInventario\"]/div/div/div[2]/mat-form-field/div/div/div/mat-select/div/div"); 						
    By xmlBtnBuscarConsInv = By.xpath("//*[@id=\"formFiltroInventario\"]/div[2]/div/div[4]/button/span[1]");
    By xmlCr = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/tbody/tr[1]/td[3]/a/span");
    By xmlDatosGrales = By.xpath("//*[@id=\"mat-tab-label-10-1\"]/div");
    By xmlCampoTipo = By.xpath("//mat-tab-body[2]/div/div/div[8]");
    							//*[@id="mat-select-value-73"]/span
    By xmlTipo6 = By.xpath("//*[@id=\"mat-select-value-25\"]/span");
    By xmlDescripTienda = By.xpath("//*[@id=\"mat-input-1\"]");
  
    By xmlSeleccionarSeg = By.xpath("//*[@id=\"mat-option-135\"]/span");
//    By xmlTipo5 = By.xpath("//*[@id=\"mat-select-68\"]/div/div[2]");
    										
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
     String StrNomTienda = ("//table/tbody/tr/td/a/span[contains(text(),'%s')]//ancestor::td/following-sibling::td");
     String SrtTipoConsulta = ("//div/mat-option/span[contains(text(),' %s ')]");
     String SrtPlaza = ("//div/mat-option/span[contains(text(),' %s ')]");
     String SrtTienda = ("//section/span[2]/ul/div/li/mat-checkbox/label/span[2][contains(text(),' %s ')]//preceding-sibling::span");
     String SrtTipo6 = ("//div[@id=\"mat-select-28-panel\"]/mat-option/span[contains(text(),' %s ')]");
     String SrtCampo = ("//table/tbody/tr/td[contains(text(),'%s')]/following-sibling::td[3]/mat-label");
     String SrtCheck = ("//table/tbody/tr/td[contains(text(),'%s')]/following-sibling::td[2]/mat-checkbox");
     String Srtdescrip = ("//table/tbody/tr/td[contains(text(),'%s')]/following-sibling::td/input");
     String SrtValor = ("//table/tbody/tr/td[2]/input[@placeholder='%s']/parent::td/preceding-sibling::td/mat-checkbox");
     							 
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_Campo_Tda(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);

    }
	
	/**
	 * Método para activar el checkbox y eliminar el campo tienda
	 * 
	 * @author Manuel CUen
	 * @param Ninguno
	 * @return ninguno
	 */
	public void clickMenuPedidoAdmin(String campo, String valor) {

		u.hardWait(2);
		u.mouseOver(xmlMenusAd);
		u.highLight(xmlMenusAd);
		u.click(xmlMenusAd);
		u.highLight(xmlSmenuCamposTda);
		u.click(xmlSmenuCamposTda);
		u.mouseOver(xmlHeader);
		u.hardWait(3);
		
//		By LocCampo = By.xpath(String.format(SrtCampo, campo));
//		u.highLight(LocCampo);
//		System.out.println();
//	    u.click(LocCampo);
//		
//		By LocValor = By.xpath(String.format(SrtValor, valor));
//		u.highLight(LocValor);
//		u.click(LocValor);
//		
//		u.highLight(xmlBtnEliminar);
//		u.click(xmlBtnEliminar);
//		u.highLight(xmlConfirBtnEliminar);
////		u.click(xmlConfirBtnEliminar);
//        u.hardWait(2);
//		testCase.addScreenShotCurrentStep(u, "clickMenuPedidoAdmin");
//		u.hardWait(2);

	}
	
	/**
	 * Método: Para dar click en el menu Administradir-Tienda
	 * @author: Manuel CUen
	 * @param:  Ninguno
	 * @return: ninguno
	 */
	public void clickSbMenuCamposTienda() {
		
		u.mouseOverAndClick(xmlMenusAd);
//		u.highLight(xmlMenusAd);
//		u.click(xmlMenusAd);
		u.highLight(xmlSmenuCamposTda);
		u.click(xmlSmenuCamposTda);
		u.mouseOver(xmlHeader);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickSbMenuCamposTienda");
		u.hardWait(2);
		
	}
	
	/**
	 * Método: Para Resaltar los campos de la pantalla campos tienda
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: ninguno
	 */
	public void clickMuestraListadoCampos() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlLblNombre);
		u.highLight(xmlLblDescripcion);
		u.highLight(xmlLblVisible);
//		u.highLight(xmlLblCampo1);
//		u.highLight(xmlLblCampo2);
//		u.highLight(xmlLblCampo3);
//		u.highLight(xmlLblCampo4);
//		u.highLight(xmlLblCampo5);
//		u.highLight(xmlLblCampo6);
//		u.highLight(xmlLblCampo7);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickMuestraListadoCampos");
		u.hardWait(2);
		
		u.unhighLight(xmlLblNombre);
		u.unhighLight(xmlLblDescripcion);
		u.unhighLight(xmlLblVisible);
//		u.unhighLight(xmlLblCampo1);
//		u.unhighLight(xmlLblCampo2);
//		u.unhighLight(xmlLblCampo3);
//		u.unhighLight(xmlLblCampo4);
//		u.unhighLight(xmlLblCampo5);
//		u.unhighLight(xmlLblCampo6);
//		u.unhighLight(xmlLblCampo7);
	}
	
	/**
	 * Método: Para capturar la descripcion del campo y hacer click en el chkbox visible
	 * @author: Manuel Cuen
	 * @param:  descrip: es/son necesario(s), llenar en su data provider
	 * @return: ninguno
	 */
	public void clickCambiaDescripcionCampo(String descrip, String campo) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
//		u.highLight(xmlTxtDescripcion);
		By LocDescrip = By.xpath(String.format(Srtdescrip, campo));
//		System.out.println(Srtdescrip);
		u.highLight(LocDescrip);
		u.hardWait(5);
		u.getDriver().findElement(LocDescrip).clear();
		u.hardWait(5);
		u.sendKeys(LocDescrip, descrip);
		
		By LocCheck = By.xpath(String.format(SrtCheck, campo));
		u.highLight(LocCheck);
		u.hardWait(5);
		u.click(LocCheck);
//		clickVerDatos(campo);
		
//		u.getDriver().findElement(xmlTxtDescripcion).clear();
//		u.sendKeys(xmlTxtDescripcion, descrip);
		
//		u.highLight(xmlChkVisible6);
//		u.click(xmlChkVisible6);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickCambiaDescripcionCampo");
		u.hardWait(2);
		
	}
	
	/**
	 * Método: Para Resaltar el boton guardar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: ninguno
	 */
	public void clickHabilitaBtnGuardar() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.highLight(xmlBtnGuardar);
		testCase.addScreenShotCurrentStep(u, "clickHabilitaBtnGuardar");
		u.hardWait(2);		
		
		u.unhighLight(xmlBtnGuardar);
	}
	
	/**
	 * Método: Para dar click al boton Guardar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: ninguno
	 */
	public void clickBtnGuardar() {
		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
		u.highLight(xmlBtnGuardar);
		testCase.addScreenShotCurrentStep(u, "clickHabilitaBtnGuardar");
		u.hardWait(2);
		u.click(xmlBtnGuardar);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickHabilitaBtnGuardar");
		u.hardWait(2);
	}
	
	/**
	 * Método: Solo para capturar imagen 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: ninguno
	 */
	public void clickMuestraMensaje() {
		
		testCase.addScreenShotCurrentStep(u, "clickMuestraMensaje");
		u.hardWait(2);
		
	}
	
	/**
	 * Método: Para dar click al Enlace "Ver datos"
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: ninguno
	 */
	public void clickVerDatos(String campo) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
//		u.highLight(xmlVerDatos);
//		u.click(xmlVerDatos);
		
		By LocCampo = By.xpath(String.format(SrtCampo, campo));
		u.highLight(LocCampo);
		testCase.addScreenShotCurrentStep(u, "clickVerDatos");
		u.hardWait(2);
	    u.click(LocCampo);
		
		testCase.addScreenShotCurrentStep(u, "clickVerDatos");
		u.hardWait(2);
		
	}
	
	/**
	 * Método: Para Resaltar campos de la pantalla nuevo campo
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: ninguno
	 */
	public void clickPantallaValorNuevo() {
		
		u.hardWait(5);
		u.highLight(xmlDescripValorNvo);
		u.highLight(xmlBtnAgregar);
		u.highLight(xmlLblValor);
		u.highLight(xmlBtnCancelar);
		u.highLight(xmlBtnGuardarValorNvo);
		
		testCase.addScreenShotCurrentStep(u, "clickPantallaValorNuevo");
		u.hardWait(2);
		
		u.unhighLight(xmlDescripValorNvo);
		u.unhighLight(xmlBtnAgregar);
		u.unhighLight(xmlLblValor);
		u.unhighLight(xmlBtnCancelar);
		u.unhighLight(xmlBtnGuardarValorNvo);
	}
	
	/**
	 * Método: Para capturar valor de la descripcion y dar click al boton agregar
	 * @author: Manuel Cuen
	 * @param:  descripValNvo: es/son necesario(s), llenar en su data provider
	 * @return: ninguno
	 */
	public void clickIngresaDatosValorNuevo(String descripValNvo) {
		
		u.unhighLight(xmlDescripValorNvo);
		u.sendKeys(xmlDescripValorNvo, descripValNvo);
		
		u.unhighLight(xmlBtnAgregar);
		u.click(xmlBtnAgregar);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickIngresaDatosValorNuevo");
		u.hardWait(2);
		
	}
	
	/**
	 * Método: Para dar click al boton Guardar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: ninguno
	 */
	public void clickMuestraMensajeValorNvo() {
		
		testCase.addScreenShotCurrentStep(u, "clickMuestraMensajeValorNvo");
		u.hardWait(2);
	}
	
	
	/**
	 * Método: Dar click al menu subtiendas y capturar tipo Consultas, plaza y Tienda
	 * @author: Manuel Cuen
	 * @param_1	idCampo: es/son necesario(s), llenar en su data provider
	 * @param_1: TipoConsulta es/son necesario(s), llenar en su data provider
	 * @param_2: plaza es/son necesario(s), llenar en su data provider
	 * @param_3: nomTienda es/son necesario(s), llenar en su data provider
	 * @return: ninguno
	 */
	public void clickCambiosReflejados(String idCampo,String TipoConsulta,String plaza, String nomTienda, String descripValNvo, Boolean bandera) {
		
		//	Lineas para opcion Tiendas
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.mouseOver(xmlMenusAd);
		
		u.highLight(xmlSbTiendas);
		u.click(xmlSbTiendas);
		
		u.mouseOver(xmlHeader);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
//		u.highLight(xmlDescripTienda);
//		u.sendKeys(xmlDescripTienda, nomTienda);
		
		By LocIdTienda = By.xpath(String.format(StrNomTienda, idCampo));
	    u.click(LocIdTienda);
	    
	    u.scrollToElement(xmlTipo6);
	    u.highLight(xmlTipo6);

	    u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickCambiosReflejados");
		
		// Lineas para opcion Consulta de Inventarios
		if (bandera == true) {
			
			u.mouseOverAndClick(xmlMenusAd);
		
			u.highLight(xmlMenuEspacio);
			u.click(xmlMenuEspacio);
			u.highLight(xmlSbInventario);
			u.click(xmlSbInventario);
			
			u.mouseOverAndClick(xmlHeader);
			
			m.explicitWaitToBeInvisible(Duration.ofSeconds(15));
			u.highLight(xmlTpoConsulta);
			u.click(xmlTpoConsulta);
			u.hardWait(2);
		
			By LocTipoCons= By.xpath(String.format(SrtTipoConsulta, TipoConsulta));
			System.out.println(LocTipoCons);
			u.click(LocTipoCons);
			u.hardWait(2);
			
			u.highLight(xmlPlaza);
			u.click(xmlPlaza);
		
			By Locplaza= By.xpath(String.format(SrtPlaza, plaza));
			System.out.println(Locplaza);
			u.click(Locplaza);
			u.hardWait(2);
		
			By LocTienda= By.xpath(String.format(SrtTienda, nomTienda));
			System.out.println(LocTienda);
			u.click(LocTienda);
			u.hardWait(2);
		
			u.highLight(xmlBtnBuscarConsInv);
			u.click(xmlBtnBuscarConsInv);
		
		
			u.highLight(xmlCr);
			u.click(xmlCr);
		
			u.highLight(xmlDatosGrales);
			u.click(xmlDatosGrales);
		
		
			u.highLight(xmlCampoTipo);
			u.click(xmlCampoTipo);
		
			testCase.addScreenShotCurrentStep(u, "clickCambiosReflejados");
			u.hardWait(2);
		}
	}
	
	public void clickSeleccionaValorEliminar(String valor) {
		
		u.hardWait(2);
//		u.highLight(xmlChkEliminaValor);
//		u.click(xmlChkEliminaValor);
		
		By LocValor = By.xpath(String.format(SrtValor, valor));
		u.highLight(LocValor);
		u.click(LocValor);
		
		testCase.addScreenShotCurrentStep(u, "clickSeleccionaValorEliminar");
		u.hardWait(2);
	}
	
	public void clickHabilitaEliminar() {
		
		u.hardWait(2);
		u.highLight(xmlBtnEliminarValor);
		
		testCase.addScreenShotCurrentStep(u, "clickHabilitaEliminar");
		u.hardWait(2);
	}
	
	public void clickBtnEliminar() {
		
		u.highLight(xmlBtnEliminarValor);
		u.click(xmlBtnEliminarValor);
		
		u.highLight(xmlMensajeElimar);
		
		testCase.addScreenShotCurrentStep(u, "clickBtnEliminar");
		u.hardWait(2);
	}
	
	public void clickConfirmarEliminar() {
		
		u.highLight(xmlConfirmaEliminar);
		u.click(xmlConfirmaEliminar);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickConfirmarEliminar");
		u.hardWait(2);
	}
}
