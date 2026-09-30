package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Pedidos_Autorizacion_Automatica {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	
	/*
	 * Localizadores Pantalla Detalle - AdmonConsultaMovtos
	 ******************************************************************************************/
	// Localizadores de la pantalla loguin
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");
    
    By xmlMenus = By.xpath("//mat-sidenav[@id = 'sidenav']");
    
    // Localizador(es) de la pantalla Tipos de Pedido
    By xmlMenuPedido = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[3]/div/label");
    By xmlSbTiposPedido = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[4]/a/div/label");
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
    By xmlPerfilSesion = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
    By xmlCerrarSesion = By.xpath("//*[@id=\"mat-menu-panel-0\"]/div/div");

    // Localizadores del grid pedidos
    By xmlLblId = By.xpath("//*[@id=\"content-nav\"]/app-order-type/div/div/table/thead/tr/th[2]");
    By xmlLblNombre = By.xpath("//*[@id=\"content-nav\"]/app-order-type/div/div/table/thead/tr/th[4]");
    By xmlLblTipoPop = By.xpath("//*[@id=\"content-nav\"]/app-order-type/div/div/table/thead/tr/th[5]");
    By xmlLblPeriodos = By.xpath("//*[@id=\"content-nav\"]/app-order-type/div/div/table/thead/tr/th[6]");
    By xmlLblExportar = By.xpath("//*[@id=\"content-nav\"]/app-order-type/div/div/table/thead/tr/th[7]");
    By xmlLblAutorizacion = By.xpath("//*[@id=\"content-nav\"]/app-order-type/div/div/table/thead/tr/th[8]");
    By xmlLblProveedor = By.xpath("//*[@id=\"content-nav\"]/app-order-type/div/div/table/thead/tr/th[9]");
    By xmlLblActivo = By.xpath("//*[@id=\"content-nav\"]/app-order-type/div/div/table/thead/tr/th[10]");
    
    // Localizadores del Mensaje de la ventana modal
    By xmlMensajeAutorizacion = By.xpath("//*[@id=\"mat-dialog-title-0\"]");
    By xmlBtnConfirmarMsj = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    // Localizador Entrar al Menu Pedidos Plaza
    By xmlMenuPedPlaza = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[3]/div/label");
    							  //*[@id="sidenav"]/div/app-sidebar/mat-nav-list/div[3]/a[3]/div/label
    By xmlSbCrearPed = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[2]/a/div/label");
    
    
    // Localizadores pantalla Crear pedido paso 1
    By xmlBtnSiguiente = By.xpath("//*[@id=\"cdk-step-content-0-0\"]/form/div[2]/div/button/span[1]");
    							   
    // Localizadores pantalla Crear pedido paso 2
    By xmlPlaza = By.xpath("//*[@id=\"mat-select-value-1\"]/span");
    By xmlSubPlaza = By.xpath("//*[@id=\"mat-select-value-3\"]/span");
    By xmlTiendaEntrega = By.xpath("//*[@id=\"mat-select-value-13\"]");
    By xmlTiendaEspejo = By.xpath("//mat-select[@formcontrolname=\"tiendaEspejoIdCtrl\"]/div/div/span");
    By xmlDirEnvio = By.xpath("//*[@id=\"mat-select-value-5\"]/span");
    By xmlAgregaOtraDir = By.xpath("//*[@id=\"cdk-step-content-0-1\"]/form/div[1]/div/div[4]/a");
    By xmlDirNueva = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[1]/div/mat-form-field/div/div[1]/div");   
    By xmlBtnConfirmar = By.xpath("//*[@id=\"mat-dialog-1\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    By xmlBtnSiguiente2 = By.xpath("//*[@id=\"cdk-step-content-0-1\"]/form/div[2]/div/button[2]/span[1]");
    By xmlBtnSiguiente3 = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[2]/div/button[3]/span[1]");
    
    // Localizadores pantalla Crear pedido paso 3 
    By xmlPeriodo = By.xpath("//*[@id=\"mat-select-value-11\"]");
    By xmlNombrePop = By.xpath("//form[@name=\"filtroPop\"]/div/div/div/mat-form-field/div/div/div/input");
    By xmlFolio = By.xpath("//form[@name=\"filtroPop\"]/div/div/div[2]/mat-form-field/div/div/div/input");
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div/button/span[1]");
    By xmlTipoPedido = By.xpath("//form[@name=\"filtroPop\"]/div/div[2]/div/mat-form-field/div/div/div/mat-select/div/div/span");
    By xmlIdPop = By.xpath("//form[@name=\"filtroPop\"]/div/div[3]/div/mat-form-field/div/div/div/input");
    By xmlCantidad = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr[1]/td[7]/div");
    //By xmlBtnAgregarAcces = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr[2]/td[9]/button/span[1]");
    String xmlAgregarBtn = "//table/tbody/tr/td/span[contains(text(),'%s')]//ancestor::td/following-sibling::td[7]/button";
    String xmlAgregarBtn2 = "//table/tbody/tr/td/span[contains(text(),'%s')]//ancestor::td/following-sibling::td[5]/button";
    String xmlAgregarBtn3 = "//table/tbody/tr/td/span[contains(text(),'%s')]//ancestor::td/following-sibling::td[5]/button/span[contains(text(), 'Agregar')]";
    
    
    By xmlMsjCantMayor = By.xpath("//*[@id=\"mat-dialog-title-0\"]");
    By xmlBtnCancelar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/button/span[1]");
    By xmlBtnConfirmarDos = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
                                       
    By xmlEmpaque = By.xpath("//*[@id=\"mat-checkbox-1\"]/label/span[1]");
    By xmlBtnAgregarUno = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr[1]/td[6]/button/span[1]");
    By xmlBtnAgregarTres = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[2]/div/button[3]/span[1]");
    								
    By xmlBtnAgregarAmbTiendas = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr[1]/td[11]/button/span[1]");
    By xmlBtnAgregarAndatti = By.xpath("//*[@id=\"cdk-step-content-0-2\"]/form/div[1]/div/div[2]/table/tbody/tr[3]/td[9]/button/span[1]");
    By xmlFolioBuscar = By.xpath("//*[@name=\"folioPop\"] ");   
    
    // Localizadores pantalla Crear pedido paso 4
    By xmlBtnCrear = By.xpath("//*[@id=\"cdk-step-content-0-3\"]/div[3]/div/button[3]/span[1]");
    By xmlMsjCrearPed = By.xpath("//*[@id=\"mat-dialog-title-1\"]");  
    By xmlMsjCrearPed2 = By.xpath("//*[@id=\"mat-dialog-title-0\"]");  
    By xmlBtnCancelarDos = By.xpath("//*[@id=\"mat-dialog-1\"]/app-dialog-confirm/div[2]/div/div/button/span[1]");	
    By xmlBtnCancelarTres = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/button/span[1]");
    By xmlBtnConfirmarTres = By.xpath("//*[@id=\"mat-dialog-1\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]"); 
    By xmlBtnConfirmarCuatro = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]"); 
    By xmlComentario = By.xpath("//mat-sidenav-content[@id=\"content-nav\"]/app-order-create/div/mat-horizontal-stepper/div[2]/div[4]/div[2]/table/tbody/tr/td");
    By xmlComentarioP4 = By.xpath("//*[@id=\"mat-input-3\"]");
    By xmlComentUnoAper = By.xpath("//*[@id=\"mat-input-3\"]");		
    By xmlComentarioUno = By.xpath("//table/tbody/tr[1]/td/input[@name='comentariosDetail']");	
    
    /*
     * Variable que reciben un parametro
     * ***************************************************************************************/
//    String SrtNomPedido = ("//table/tbody/tr/td[4][contains(text(),'%s')]//ancestor::td//following-sibling::td[5]/mat-checkbox/label/span");
    String SrtNomPedido = ("//table/tbody/tr/td[4][contains(text(),'%s')]//preceding-sibling::td/following-sibling::td[7]/mat-checkbox");
    String SrtTipoPedido = ("//mat-radio-group/mat-radio-button/label/span[2][contains(text(),'%s')]");
    String SrtPlaza = ("//div[@id=\"mat-select-0-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String SrtSubPlaza = ("//div[@id=\"mat-select-2-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String SrtDirEnvio = ("//div[@id=\"mat-select-4-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String SrtTipoPed = ("//div[@id=\"mat-select-10-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String SrtEmpaque = ("//mat-checkbox[@id=\"mat-checkbox-1\"]/label/span[2][contains(text(),'Empaque')]//preceding-sibling::span");
    String SrtTiendEntrega = ("//div[@id=\"mat-select-12-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String SrtTiendEspejo = ("//div[@id=\"mat-select-13-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String SrtTiendEspejo2 = ("//div[@id=\"mat-select-12-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String SrtPeriodo = ("//div[@id=\"mat-select-10-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String Srtcandidad = ("//table/tbody/tr/td/span[contains(text(),'%s')]//ancestor::td/following-sibling::td[5]/div");
    String Srtcandidad2 = ("//table/tbody/tr/td/span[contains(text(),'%s')]//ancestor::td/following-sibling::td[4]/div");
    String SrtcantidadAmbTiendas = ("//table/tbody/tr/td[3]/span[contains(text(),'%s')]//ancestor::td/following-sibling::td[5]/div");
    String SrtcandidadAndatti = ("//table/tbody/tr/td/span[contains(text(),'%s')]//ancestor::td/following-sibling::td[5]/div");
    
       
    /**
	 * Constructor
	 ******************************************************************************************/
	public Pedidos_Autorizacion_Automatica(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método: Para dar click al menu Pedido-->Tipos de Pedido
	 * 
	 * @author: Manuel CUen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickMenuSbTiposPedido(){
		u.scrollTo(xmlMenuPedido);
		u.highLight(xmlMenuPedido);
		testCase.addScreenShotCurrentStep(u, "clickMenuPedido");
		u.click(xmlMenuPedido);
		
		u.highLight(xmlSbTiposPedido);
	    testCase.addScreenShotCurrentStep(u, "clickSbTiposPedido");
		u.click(xmlSbTiposPedido);
		
		u.mouseOverAndClick(xmlHeader);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
		testCase.addScreenShotCurrentStep(u, "clickMenuSbTiposPedido2");
		u.hardWait(2);
	}
	
	/**
	 * Método: Para Mostrar/Resaltar Etiquetas del Grid Tipos de Pedido
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickMuestraGrid() {
		
//	    u.highLight(xmlLblId);
//	    u.highLight(xmlLblNombre);
//	    u.highLight(xmlLblTipoPop);
//	    u.highLight(xmlLblPeriodos);
//	    u.highLight(xmlLblExportar);
	    u.highLight(xmlLblAutorizacion);
//	    u.highLight(xmlLblProveedor);
//	    u.highLight(xmlLblActivo);
	    
		testCase.addScreenShotCurrentStep(u, "clickMuestraGrid");
		u.hardWait(2);
		
//	    u.unhighLight(xmlLblId);
//	    u.unhighLight(xmlLblNombre);
//	    u.unhighLight(xmlLblTipoPop);
//	    u.unhighLight(xmlLblPeriodos);
//	    u.unhighLight(xmlLblExportar);
	    u.unhighLight(xmlLblAutorizacion);
//	    u.unhighLight(xmlLblProveedor);
//	    u.unhighLight(xmlLblActivo);
	}
	
	/**
	 * Método: Para dar clic en chkbox de Autorizacion Automatica, de acuerdo al Numero de Pedido
	 * @author: Manuel Cuen
	 * @param:  NumeroPedido: Es necesario capturar el numero de pedido que deseas autorizar, en su data provider
	 * @return: ninguno
	 */
	public Boolean clickSeleccionPedido(String nombrePedido) {
		
		Boolean checkbox = false;
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		By LocNomPedido = By.xpath(String.format(SrtNomPedido, nombrePedido));
		m.scrollToElement(LocNomPedido);
		u.highLight(LocNomPedido);
		
		testCase.addScreenShotCurrentStep(u, "clickSeleccionPedido");
		u.hardWait(2);
		
		String classAtr = u.getDriver().findElement(LocNomPedido).getAttribute("class");

		if(classAtr.contains("mat-checkbox-checked")) {
			checkbox = true;
		}else {
			u.click(LocNomPedido);
		}
		
		testCase.addScreenShotCurrentStep(u, "clickSeleccionPedido");
		u.hardWait(2);
		
		return checkbox;
	}
	
	/**
	 * Método: Para resaltar mensaje de confirmacion de Autorizacion Automatica
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickMensajeVentanaModal() {
		
		u.highLight(xmlMensajeAutorizacion);
		
		testCase.addScreenShotCurrentStep(u, "clickMensajeVentanaModal");
		u.hardWait(2);
		
		u.highLight(xmlBtnConfirmarMsj);
		u.click(xmlBtnConfirmarMsj);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickMensajeVentanaModal");
		u.hardWait(2);
	}
	
	/**
	 * Método: Para dar clic al perfil y cerrar sesion, para iniciar con usuario plaza
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickCerrarSesion() {
		
		u.highLight(xmlPerfilSesion);
		u.click(xmlPerfilSesion);
		
	    u.highLight(xmlCerrarSesion);
	    u.click(xmlCerrarSesion);
	    
//	    u.getDriver().quit();

	}
	
	public void clickCrearPedidoPasoUno(String TipoPedido, Boolean band) {
		
		u.mouseOver(xmlMenus);
		u.highLight(xmlMenuPedPlaza);
		u.click(xmlMenuPedPlaza);
		
		u.highLight(xmlSbCrearPed);
		u.click(xmlSbCrearPed);
		
		u.mouseOverAndClick(xmlHeader);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		
		By LocTipoPedido = By.xpath(String.format(SrtTipoPedido, TipoPedido));
		u.click(LocTipoPedido);
		
		u.highLight(xmlBtnSiguiente);
		if (band == true) {
			testCase.addScreenShotCurrentStep(u, "clickCrearPedido");
			u.hardWait(2);
		}
		u.click(xmlBtnSiguiente);
	}
	
	/**
	 * Método: Para Crear en un pedido
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public Boolean clickCrearPedido(String plaza, String subPlaza, String direccionEnvio, String direccionNueva, String TipoPed, String cantidad, String cantidadDos, String tiendaEntrega, String folio, Boolean bandera, String opcion) {
		
		Boolean ban = true;
		//paso 2
		u.highLight(xmlPlaza);
		u.click(xmlPlaza);
		
		By LocPlaza = By.xpath(String.format(SrtPlaza, plaza));
		//System.out.println(LocPlaza);
		u.click(LocPlaza);
		
		u.hardWait(2);
		u.highLight(xmlSubPlaza);
		u.click(xmlSubPlaza);
		
		u.hardWait(2);
		By LocSubPlaza = By.xpath(String.format(SrtSubPlaza, subPlaza));
		//System.out.println(LocSubPlaza);
		u.click(LocSubPlaza);
		if(opcion.toUpperCase().equals("FALTANTE")) {
			if (bandera == false) {
				clickTiendaEntrega(tiendaEntrega);
			}
		} else if(opcion.toUpperCase().equals("APERTURA")) {
			if (bandera == false) {
				clickTiendaEspejo(tiendaEntrega);
			}
		}
		
		u.hardWait(2);
		u.highLight(xmlDirEnvio);
		u.click(xmlDirEnvio);
		
		By LocDirEnvio = By.xpath(String.format(SrtDirEnvio, direccionEnvio));
		u.click(LocDirEnvio);
		
		
		u.hardWait(2);
		u.highLight(xmlBtnSiguiente2);
		u.click(xmlBtnSiguiente2);
		
		
		if(bandera == true){
		
			u.highLight(xmlTipoPedido);
			u.click(xmlTipoPedido);
		
			By LocTipoPed = By.xpath(String.format(SrtTipoPed, TipoPed));
			//System.out.println(LocTipoPed);
			u.click(LocTipoPed);
			u.hardWait(2);
		
			clickAgregarImagen();
		
			u.highLight(xmlBtnBuscar);
			u.click(xmlBtnBuscar);
		}
		// ESTA CONDICION ES PARA MARCAR EL CHECKBOX DEL EMPAQUE EN LOS CPS DE FALTANTE,ADICIONAL Y APERTURA		
		if (bandera == false) {
			
			try {
				clickBuscaFolio(folio);
				u.hardWait(2);
				clickSeleccionaEmpaque();
				/*if(opcion.toUpperCase().equals("ADICIONAL")) {
					clickCapturaCantidad(cantidad,folio, "2");
				}*/
			}catch(Exception e) {
				ban = false;
			}
		}
		
		// Captura Cantidad 
		m.explicitWaitToBeInvisible(Duration.ofSeconds(40));
	
			if(TipoPed.toUpperCase().equals("PEDIDOS GENERICOS") || (TipoPed.toUpperCase().equals("ACCESORIOS") )) {
				clickCapturaCantidad(cantidadDos,folio, "1");
				if (bandera == true) {
					clickAgregarImagen();
				}
				
			} else if(TipoPed.toUpperCase().equals("FALTANTE") || TipoPed.toUpperCase().equals("ADICIONAL")){
				clickCapturaCantidad(cantidad,folio, "2");
				bandera = false;
			} else if (TipoPed.toUpperCase().equals("APERTURA")) {
				clickCapturaCantidad2(folio);
				bandera = false;
			}
			
			u.hardWait(2);
			
			try {
				u.highLight(xmlMsjCantMayor);
			
				u.highLight(xmlBtnConfirmarDos);
				u.click(xmlBtnConfirmarDos);
			}catch(Exception e) {
				if (TipoPed.toUpperCase().equals("PEDIDOS GENERICOS") || TipoPed.toUpperCase().equals("ACCESORIOS")) {
					ban = false;
				}
				
			}
		
		if (ban) {
			
			if (bandera == false) {
				testCase.addScreenShotCurrentStep(u, "clickCrearPedido");
				u.hardWait(2);
			}
			
			//u.highLight(xmlBtnSiguiente3);
			u.click(xmlBtnSiguiente3);
		}
		
		return ban;
	}
	
	public void clickBtnCrearPedido(String comentarios) {
		
		u.highLight(xmlComentUnoAper);
		u.sendKeys(xmlComentUnoAper, comentarios);
		
//		u.highLight(xmlComentarioP4);
//		u.sendKeys(xmlComentarioP4, comentariosDos);
		
//		testCase.addScreenShotCurrentStep(u, "clickBtnCrearPedido");
//		u.hardWait(2);
		
		u.highLight(xmlBtnCrear);
		
		testCase.addScreenShotCurrentStep(u, "clickBtnCrearPedido");
		u.unhighLight(xmlBtnCrear);
		u.hardWait(2);
		u.click(xmlBtnCrear);
		
	}
	
	public void clickBtnCrearPedido2(String comentarios) {
		
		u.highLight(xmlComentarioUno);
		u.sendKeys(xmlComentarioUno, comentarios);
		
		u.highLight(xmlBtnCrear);
		
		testCase.addScreenShotCurrentStep(u, "clickBtnCrearPedido");
		u.unhighLight(xmlBtnCrear);
		u.hardWait(2);
		u.click(xmlBtnCrear);
		
	}
	
	public void clickVentanaModalConfirmarPedido() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.highLight(xmlMsjCrearPed);
		u.highLight(xmlBtnCancelarDos);
		u.highLight(xmlBtnConfirmarTres);
		
		testCase.addScreenShotCurrentStep(u, "clickVentanaModalConfirmarPedido");
		u.hardWait(2);
		u.unhighLight(xmlMsjCrearPed);
		u.unhighLight(xmlBtnCancelarDos);
		u.unhighLight(xmlBtnConfirmarTres);
		
		u.highLight(xmlBtnConfirmarTres);
		u.click(xmlBtnConfirmarTres);
		u.hardWait(5);
		
		testCase.addScreenShotCurrentStep(u, "clickVentanaModalConfirmarPedido");
		u.hardWait(2);
		
	}
	
	public void clickVentanaModalConfirmarPedido2() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.highLight(xmlMsjCrearPed2);
		u.highLight(xmlBtnCancelarTres);
		u.highLight(xmlBtnConfirmarCuatro);
		
		testCase.addScreenShotCurrentStep(u, "clickVentanaModalConfirmarPedido");
		u.hardWait(2);
		u.unhighLight(xmlMsjCrearPed2);
		u.unhighLight(xmlBtnCancelarTres);
		u.unhighLight(xmlBtnConfirmarCuatro);
		
		u.highLight(xmlBtnConfirmarCuatro);
		testCase.addScreenShotCurrentStep(u, "clickVentanaModalConfirmarPedido");
		u.click(xmlBtnConfirmarCuatro);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "VentanaModalConfirmarPedido");
		
	}
	
	public void clickAgregarImagen() {
		testCase.addScreenShotCurrentStep(u, "clickAgregarImagen");
		u.hardWait(2);
	}
	
	public void clickCapturaCantidad(String cantidad, String folio, String xpath) {
		
		By LocCantidad = By.xpath("");
		By AgregarBtn = By.xpath("");
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(50));
		if (xpath.equals("1")) {
			LocCantidad = By.xpath(String.format(Srtcandidad, folio));
			AgregarBtn = By.xpath(String.format(xmlAgregarBtn, folio));
		} else if (xpath.equals("2")) {
			LocCantidad = By.xpath(String.format(Srtcandidad2, folio));
			AgregarBtn = By.xpath(String.format(xmlAgregarBtn2, folio));
		}
		
		//System.out.println(LocCantidad);
		u.getDriver().findElement(LocCantidad).clear();
		u.click(LocCantidad);
		u.sendKeys(LocCantidad, cantidad);
		
		u.hardWait(2);
		u.highLight(AgregarBtn);
		u.mouseOverAndClick(AgregarBtn);
	}
	
	/**
	 * Metodo para gregar cantidad a pedidos de tipo apertura
	 * @param folio
	 * @author Maria Duenas
	 */
	public void clickCapturaCantidad2(String folio) {
		
		By AgregarBtn = By.xpath(String.format(xmlAgregarBtn3, folio));
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(50));
		
		if (m.existElement(AgregarBtn)) {
			u.highLight(AgregarBtn);
			testCase.addScreenShotCurrentStep(u, "Cantidad");
			u.unhighLight(AgregarBtn);
			u.click(AgregarBtn);
		}
		
	}

	public Boolean clickCantidadAmbientacionTiendas(String cantidad, String folio, String TipoPed) {
		Boolean ban = true;
		m.explicitWaitToBeInvisible(Duration.ofSeconds(50));
		if(TipoPed.equals("Ambientaci�n de Tiendas")) {
			By LocCantidad = By.xpath(String.format(SrtcantidadAmbTiendas, folio));
			//System.out.println(LocCantidad);
			u.getDriver().findElement(LocCantidad).clear();
			u.click(LocCantidad);
			u.sendKeys(LocCantidad, cantidad);
			
			u.hardWait(2);
			u.highLight(xmlBtnAgregarAmbTiendas);
			u.mouseOverAndClick(xmlBtnAgregarAmbTiendas);
		}
		
		if(TipoPed.equals("Andatti y Comida R�pida")) {
			By LocCantidad = By.xpath(String.format(SrtcandidadAndatti, folio));
			//System.out.println(LocCantidad);
			u.getDriver().findElement(LocCantidad).clear();
			u.click(LocCantidad);
			u.sendKeys(LocCantidad, cantidad);
			
			u.hardWait(2);
			u.highLight(xmlBtnAgregarAndatti);
			u.mouseOverAndClick(xmlBtnAgregarAndatti);
			
		}
		
		u.hardWait(2);
		try {
			u.highLight(xmlMsjCantMayor);
			
			u.highLight(xmlBtnConfirmarDos);
			
			testCase.addScreenShotCurrentStep(u, "clickCantidadAmbientacionTiendas");
			u.hardWait(2);
			u.click(xmlBtnConfirmarDos);
			u.hardWait(4);
			testCase.addScreenShotCurrentStep(u, "clickCantidadAmbientacionTiendas");
			u.hardWait(2);
		}catch(Exception e) {
			ban = false;
		}
		return ban;
	}
	
	public void clickPeriodo(String periodoPrueba) {
		
		u.highLight(xmlPeriodo);
		u.click(xmlPeriodo);
		
		By LocPeriodo = By.xpath(String.format(SrtPeriodo, periodoPrueba));
		//System.out.println(LocPeriodo);
		u.click(LocPeriodo);
		
	}
	
	public void clickTiendaEntrega(String tiendaEntrega) {
		
		u.hardWait(5);
		u.highLight(xmlTiendaEntrega);
		u.mouseOverAndClick(xmlTiendaEntrega);
		
		By LocTiendaEntrega = By.xpath(String.format(SrtTiendEntrega, tiendaEntrega));
		//System.out.println(LocTiendaEntrega);
		u.click(LocTiendaEntrega);
		
	}
	
	public void clickTiendaEspejo(String tiendaEspejo) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.click(xmlTiendaEntrega);
		
		By LocTiendaEspejo = By.xpath(String.format(SrtTiendEspejo2, tiendaEspejo));
		u.click(LocTiendaEspejo);
		
		//System.out.println(LocTiendaEntrega);
		
		
	}
	
	public void clickSeleccionaEmpaque() {
		
		u.highLight(xmlEmpaque);
		u.click(xmlEmpaque);
		
	}
	
	public void clickCapturaComentariosApertura(String comentarios) {
		
		u.highLight(xmlComentUnoAper);
		u.sendKeys(xmlComentUnoAper, comentarios);
		
		testCase.addScreenShotCurrentStep(u, "clickCapturaComentariosApertura");
		u.hardWait(2);
	}
	
	public void clickBuscaFolio(String folio) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.highLight(xmlFolioBuscar);
		u.sendKeys(xmlFolioBuscar, folio);
		
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
	}
	
	public void clickDatosReceptor(String plaza, String subPlaza, String direccionEnvio, String direccionNueva) {
		
		//paso 2
		u.highLight(xmlPlaza);
		u.click(xmlPlaza);
		
		By LocPlaza = By.xpath(String.format(SrtPlaza, plaza));
		//System.out.println(LocPlaza);
		u.click(LocPlaza);
		
		u.hardWait(2);
		u.highLight(xmlSubPlaza);
		u.click(xmlSubPlaza);
		
		u.hardWait(2);
		By LocSubPlaza = By.xpath(String.format(SrtSubPlaza, subPlaza));
		//System.out.println(LocSubPlaza);
		u.click(LocSubPlaza);
		
		u.hardWait(2);
		u.highLight(xmlDirEnvio);
		u.click(xmlDirEnvio);
		
		By LocDirEnvio = By.xpath(String.format(SrtDirEnvio, direccionEnvio));
		//System.out.println(LocDirEnvio);
		u.click(LocDirEnvio);

	}
	
	public void clickSiguienteDos() {
		
		u.highLight(xmlBtnSiguiente2);
		u.click(xmlBtnSiguiente2);
		
	}
	
	public void clickSeleccionarPops(String TipoPed) {
		
		u.highLight(xmlTipoPedido);
		u.click(xmlTipoPedido);
	
		By LocTipoPed = By.xpath(String.format(SrtTipoPed, TipoPed));
		//System.out.println(LocTipoPed);
		u.click(LocTipoPed);
		
	}
	
	public void clickBuscarPops() {
		
		u.highLight(xmlBtnBuscar);
		
		testCase.addScreenShotCurrentStep(u, "clickBuscarPops");
		u.hardWait(2);
		
		u.click(xmlBtnBuscar);
		
	}
	
	public void clickSiguienteTres() {
		
		u.hardWait(2);
		u.highLight(xmlBtnAgregarTres);
		u.click(xmlBtnAgregarTres);
		
	}

}
