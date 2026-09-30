package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.ibm.icu.impl.Assert;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Reglas_IEP {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Tiendas
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");
    By xmlMenus = By.xpath("//mat-sidenav[@id = 'sidenav']");
    By xmlMenuAdm = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/label");    							
    By xmlReglas = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[12]/a/div/label");
  
    By xmlHeader       = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
    By xmlNomRegla     = By.xpath("//*[@id=\"mat-input-0\"]");
 
    By xmlNomReglaNvo  = By.xpath("//*[@name=\"nombreRegla\"]");
    
    By xmlNomReglaEdi  = By.xpath("//*[@name=\"nombreRegla\"]");

    By xmlDescripNvo = By.xpath("//*[@id=\"mat-input-5\"]");
    By xmlDescripRegla = By.xpath("//*[@id=\"mat-input-1\"]");
    By xmlEstatus      = By.xpath("//*[@id=\"mat-select-value-1\"]");
    By xmlFechaIni     = By.xpath("//*[@id=\"mat-input-2\"]");
    By xmlFechaFinFil  = By.xpath("//*[@id=\"mat-input-3\"]");
    By xmlBtnNuevo     = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[1]/button");
    By xmlBtnExportar  = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[2]/button");
    By xmlBtnLimpiar   = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button");
    By xmlBtnBuscar    = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[4]/button");
    By xmlBtnBuscarSel    = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[5]/button/span[1]");
  
    By xmlIdRegla = By.xpath("//*[@id=\"content-nav\"]/app-reglas/div/div/div/table/thead/tr/th[2]");
    By xmlNombre = By.xpath("//*[@id=\"content-nav\"]/app-reglas/div/div/div/table/thead/tr/th[3]");
    By xmlDescrip = By.xpath("//*[@id=\"content-nav\"]/app-reglas/div/div/div/table/thead/tr/th[4]");
    By xmlObligatoria = By.xpath("//*[@id=\"content-nav\"]/app-reglas/div/div/div/table/thead/tr/th[5]");
    By xmlEstatusRes = By.xpath("//*[@id=\"content-nav\"]/app-reglas/div/div/div/table/thead/tr/th[6]");
    By xmlFechaCrea = By.xpath("//*[@id=\"content-nav\"]/app-reglas/div/div/div/table/thead/tr/th[7]");
    
    By xmlActivo = By.xpath("//*[@id=\"content-nav\"]/app-reglas/div/div/div/table/thead/tr/th[6]");
    By xmlfechaCreacion = By.xpath("//*[@id=\"content-nav\"]/app-reglas/div/div/div/table/thead/tr/th[7]");
    
 // Localizadores pesta�as
    //General
    By xmlpGeneral = By.xpath("//*[@id=\"mat-tab-label-0-0\"]");
    
    //Condicion
    By xmlpCondicion = By.xpath("//*[@id=\"mat-tab-label-0-1\"]");
    By xmlpBorraCondicion = By.xpath("//*[@id=\"mat-tab-content-0-1\"]/div/div/div/div/div[1]/div/app-calculator-reglas/div/div/div/div/button[15]");
    By xmlValidar = By.xpath("//*[@id=\"mat-tab-content-0-1\"]/div/div/div/div/div[1]/div/app-calculator-reglas/div/div/div/button");
 
    // PosCondicion
    By xmlpPosCondicion = By.xpath("//*[@id=\"mat-tab-label-0-2\"]");
    By xmlselectEspacios = By.xpath("//select[@id=\"espacios\"]");
    By xmlCantidPosCon = By.xpath("//*[@id=\"mat-tab-content-0-2\"]/div/div/div/div/div/app-postcondicion-reglas/div/div[2]/div/div/div/div/div[2]/input");
  
    // Aplicar A
    By xmlpAplicarA = By.xpath("//*[@id=\"mat-tab-label-0-3\"]");
    
    // Mensajes
    By xmlpMensajes = By.xpath("//*[@id=\"mat-tab-label-0-4\"]/div");
    
   
    By xmlFlechaPlza = By.xpath("//*[@id=\"mat-tab-content-0-3\"]/div/div/div/div/div/app-aplicar-areglas/div/div[1]/div[1]/app-select-wish-list/div/div[2]/i[1]");
    By xmlFlechaAtrasPlza = By.xpath("//*[@id=\"mat-tab-content-0-3\"]/div/div/div/div/div/app-aplicar-areglas/div/div[1]/div[1]/app-select-wish-list/div/div[2]/i[2]");
    By xmlFlechaSbPlaza = By.xpath("//*[@id=\"mat-tab-content-0-3\"]/div/div/div/div/div/app-aplicar-areglas/div/div[1]/div[2]/app-select-wish-list/div/div[2]/i[1]");
  
    // Localizadores ingresar filtros PASO 8
    
    By xmlBotonBuscar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[4]/button/span[1]");
    By xmlBtnEliminar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[2]/button/span[1]");
    
                                   
    //By xmlBtnBuscarSel = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[5]/button/span[1]");
    								
    // Localizadores ingresar filtros PASO 9
    
    By xmlIdReglaTxt = By.xpath("//*[@id=\"mat-input-0\"]");                            
    By xmlDescriptxt= By.xpath("//*[@id=\"mat-input-1\"]");
    By xmlStatus = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div[1]/div/mat-form-field/div");
    By xmlStatusFle = By.xpath("//*[@id=\"mat-select-0\"]/div/div[2]");
    By xmlStatusSel = By.xpath("//div[@id=\"mat-select-0-panel\"]/mat-option/span[contains(text(),' %s ')]");
    By xmlFecha = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div[2]");
    By xmlFechaFle = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div[2]/div/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button");
    By xmlFechaPeriodo = By.xpath("//*[@id=\"mat-datepicker-0\"]/mat-calendar-header/div/div/button[1]/span[1]");
    By xmlAEspecifico = By.xpath("//*[@id=\"mat-datepicker-0\"]/div/mat-multi-year-view/table/tbody/tr[5]/td[1]/div[1]");
    By xmlFechaMes = By.xpath("//*[@id=\"mat-datepicker-0\"]/div/mat-year-view/table/tbody/tr[4]/td[4]/div[1]");
    By xmlFechaDia = By.xpath("//*[@id=\"mat-datepicker-0\"]/div/mat-month-view/table/tbody/tr[2]/td[2]/div[1]");
    By xmlFechaFinal = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div[3]/div/mat-form-field/div/div[1]");
    By xmlFechaCalen = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div[3]/div/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button");
    By xmlFechaFinPerio = By.xpath("//*[@id=\"mat-datepicker-1\"]/mat-calendar-header/div/div/button[1]");
    By xmlFechaFinAva = By.xpath("//*[@id=\"mat-datepicker-1\"]/mat-calendar-header/div/div/button[3]");
    By xmlAEspecFin = By.xpath("//*[@id=\"mat-datepicker-1\"]/div/mat-multi-year-view/table/tbody/tr[5]/td[1]/div[1]");
    By xmlFechaMesFin = By.xpath("//*[@id=\"mat-datepicker-1\"]/div/mat-year-view/table/tbody/tr[4]/td[4]/div[1]");
    By xmlFechaDiaFin = By.xpath("//*[@id=\"mat-datepicker-1\"]/div/mat-month-view/table/tbody/tr[6]/td[5]/div[1]");
  
    By xmlBotonCancelar = By.xpath("//*[@id=\"content-nav\"]/app-captura-edicion-reglas/div/div[2]/div/div[1]/button/span[1]");
    By xmlBotonLimpiar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button");
    By xmlBtnLimpiarGral = By.xpath("//*[@id=\"content-nav\"]/app-captura-edicion-reglas/div/div[2]/div/div[2]/button/span[1]");
    By xmlBtnGuardarGral = By.xpath("//*[@id=\"content-nav\"]/app-captura-edicion-reglas/div/div[2]/div/div[3]/button/span[1]");								
    By xmlBotonGuardar = By.xpath("//*[@id=\"content-nav\"]/app-captura-edicion-reglas/div/div[2]/div/div[3]/button/span[1]");
    By xmlBtnGuardarAplicar = By.xpath("//*[@id=\"content-nav\"]/app-captura-edicion-reglas/div/div[2]/div/div[2]/button/span[1]");
    									
    // mensaje condicion
    By xmlTablero = By.xpath("//*[@id=\"mat-tab-content-0-1\"]/div/div/div/div/div[1]/div");
    By xmlMsjCondicion = By.xpath("//*[@id=\"mat-tab-content-0-1\"]/div/div/div/h6");
    By xmlLblAtriTienda = By.xpath("//*[@id=\"mat-tab-content-0-1\"]/div/div/div/div/div[2]/div[1]/strong");
    By xmlAtriEspacio = By.xpath("//*[@id=\"mat-tab-content-0-1\"]/div/div/div/div/div[2]/div[2]/strong");
    By xmlATriCantIdEspacio = By.xpath("//*[@id=\"mat-tab-content-0-1\"]/div/div/div/div/div[2]/div[3]/strong");

    //mensaje Aplicar A
    By xmlMsjAplicarA = By.xpath("//*[@id=\"mat-tab-content-0-3\"]/div/div/div/h6");
    By xmlLblPlazaApliA = By.xpath("//*[@id=\"mat-tab-content-0-3\"]/div/div/div/div/div/app-aplicar-areglas/div/div[1]/div[1]/app-select-wish-list/div/div[1]/label");
    By xmlLblSbPlazaApliA = By.xpath("//*[@id=\"mat-tab-content-0-3\"]/div/div/div/div/div/app-aplicar-areglas/div/div[1]/div[2]/app-select-wish-list/div/div[1]/label");
    By xmlLblIendaApliA  = By.xpath("//*[@id=\"mat-tab-content-0-3\"]/div/div/div/div/div/app-aplicar-areglas/div/div[2]/div[1]/app-select-wish-list/div/div[1]/label");
    By xmlLblSPlzaCcmApliA = By.xpath("//*[@id=\"mat-tab-content-0-3\"]/div/div/div/div/div/app-aplicar-areglas/div/div[2]/div[2]/app-select-wish-list/div/div[1]/label");
    By xmlLblEspacioApliA = By.xpath("//*[@id=\"mat-tab-content-0-3\"]/div/div/div/div/div/app-aplicar-areglas/div/div[3]/div/app-select-wish-list/div/div[1]/label");
    
    // Mensaje Mensaje
    By xmlMensajeTxt = By.xpath("//*[@id=\"mat-tab-content-0-4\"]/div/div/div/div/div[1]/textarea");
    By xmlMsjMsj = By.xpath("//*[@id=\"mat-tab-content-0-4\"]/div/div/div/h6");
    
    
    //Botones de la pesta�a poscondicion
    By xmlMsgPoscond = By.xpath("//*[@id=\"mat-tab-content-0-2\"]/div/div/div/h6");
    By xmlBtnAgregar = By.xpath("//*[@id=\"mat-tab-content-0-2\"]/div/div/div/div/div/app-postcondicion-reglas/div/div[1]/button");
    By xmlBtnQuitar = By.xpath("//*[@id=\"mat-tab-content-0-2\"]/div/div/div/div/div/app-postcondicion-reglas/div/div[2]/div/div/div/div/div[1]/button");
    By xmlBtEliminarAgre = By.xpath("//*[@id=\"mat-tab-content-0-2\"]/div/div/div/div/div/app-postcondicion-reglas/div/div[2]/div/div/div/div/div[1]/button");
    By xmlBtSeleccAgre = By.xpath("//*[@id=\"espacios\"]");
    By xmlBtCantidAgre = By.xpath("//*[@id=\"mat-tab-content-0-2\"]/div/div/div/div/div/app-postcondicion-reglas/div/div[2]/div/div/div/div/div[2]/input");
    
    By xmlFechaA = By.xpath("//*[@id=\"mat-datepicker-0\"]/mat-calendar-header/div/div/button[3]");
                        
    By xmlFechaFin = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div[3]");
    By xmlFechaFinFle = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div[3]/div/mat-form-field/div/div[1]/div[2]/mat-datepicker-toggle/button/span[1]/svg");
     
    // Ventana Modal de Confirmacion Eliminar
    By xmlMsjVentanModal = By.xpath("//*[@id=\"mat-dialog-title-0\"]");
    By xmlMsjBtnCancelar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/button/span[1]");
    By xmlMsjbtnConfir = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    // Localizadores de la pantalla consulta de inventario 
    By xmlMenuEspacio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[5]/div");
    By xmlSbConsultaInvent = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[1]/a/div/label");
    By xmlTpoConsulta = By.xpath("//*[@id=\"mat-select-value-5\"]");
  //*[@id="mat-select-value-5"]/span/span
  //*[@id=\"mat-select-value-5\"]
  //*[@id=\"mat-select-4\"]/div/div[2]/div
    
    By xmlPlaza = By.xpath("//*[@id=\"mat-select-value-7\"]/span");
    By xmlBtnBuscarConsInv = By.xpath("//*[@id=\"formFiltroInventario\"]/div[2]/div/div[4]/button/span[1]");
    By xmlNomEspInv = By.xpath("//input[@name=\"espaciosEquipos\"]");
    String xmlNombreEspacios = "//table/tbody/tr/td[contains(text(), '%s')]";    
    
    By xmlLblCr = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/thead/tr/th[3]");
    By xmlLblTienda = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/thead/tr/th[4]");
    By xmlLblPlaza = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/thead/tr/th[5]");
    By xmlLblSbPlaza = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/thead/tr/th[6]");
    By xmlLblAsesor = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/thead/tr/th[7]");
    By xmlLblSegmento = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/thead/tr/th[8]");
//    By xmlLblSegMaestro = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/thead/tr/th[8]");
    By xmlLblRetek = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/thead/tr/th[9]");
    By xmlLblM2 = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/thead/tr/th[10]");
    By xmlLblTiendaNicho = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/thead/tr/th[11]");
  
    
    By xmlCr = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/tbody/tr[1]/td[3]/a/span");
    					//*[@id="content-nav"]/app-inventory-list/div/div[3]/table/tbody/tr[1]/td[2]/a/mat-icon
    
    By xmlMsjReglaObliga = By.xpath("//*[@id=\"mat-dialog-title-0\"]");
    By xmlBtnCerrarMsjRegObl = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/button/span[1]");
    By xmlAvancePag = By.xpath("//*[@id=\"content-nav\"]/app-reglas/div/mat-paginator/div/div/div[2]/button[3]/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
    // seleccion de regla por Id
    String SrtIdRegla = ("//table/tbody/tr/td[2][contains(text(),'%s')]//following-sibling::td[6]/mat-icon");
    // Estatus
    String SrtStatusSel = ("//div[@id=\"mat-select-0-panel\"]/mat-option/span[contains(text(),' %s ')]");
    //  Fechas
    String SrtSelecionA = ("//mat-multi-year-view/table/tbody/tr/td/div[contains(text(),'%s')]");
    String StrSeleccioMes = ("//div/mat-year-view/table/tbody/tr/td/div[contains(text(),' %s ')]");
    String StrSeleccioDia = ("//div/mat-month-view/table/tbody/tr/td/div[contains(text(),'%s')]");                              
    String StrSeleccionDiaFin = ("//div/mat-month-view/table/tbody/tr/td/div[contains(text(),' %s ')]");	
    String SrtRadioObliga = ("//mat-radio-button/label/span[2][contains(text(),'%s')]");
    String SrtRadioEstatus = ("//mat-tab-body/div/div/div/div[3]/div[2]/div/mat-radio-group/mat-radio-button/label/span[2][contains(text(),'%s')]");
    // pesta�a condicion
    String SrtAtributoTda = ("//*[@id=\"tiendasAttributes\"]/option[contains(text(),'%s')]");
    String SrtAtribEspacio = ("//*[@id=\"espaciosAttributes\"]/option[contains(text(),'%s')]");
    String SrtCantIdEspacio = ("//*[@id=\"espacios\"]/option[contains(text(),' %s ')]");
    // pesta�a Aplicar
    String SrtAplicarPlaza = ("//select[@id=\"plazas-no\"]/option[contains(text(),' %s ')]");
    String SrtAplicarSbPlaza = ("//select[@id=\"subplazas-no\"]/option[contains(text(),'%s')]");
    String SrtAplicarTienda = ("//select[@id=\"verificar/tiendas-no\"]/option[contains(text(),' %s ')]");
    String SrtAplicarPlazaCcm = ("//select[@id=\"plazasccm-no\"]/option[contains(text(),' %s ')]");
    String SrtAplicarEspacios = ("//select[@id=\"verificar/espacios-no\"]/option[contains(text(),' %s')]");
    String SrtDeseleccionaPlaza = ("//app-select-wish-list/div/div[3]/select/option[contains(text(),' %s ')]");
    
    // Seleccion Multiple
    String SrtChkEliminar = ("//table/tbody/tr/td[2][contains(text(),'%s')]");  //preceding-sibling::td/mat-checkbox");
    
    // Seleccion Multiple sin parametro
   // By xmlBtnFirstPage           = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[1]");
    By xmlBtnFirstPage           = By.xpath("//div[@class=\"mat-paginator-container\"]/div[2]/button[1]");
    
  //By xmlBtnFirstPageDisable    = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[1][@disabled='true']");
    By xmlBtnFirstPageDisable    = By.xpath("//div[@class=\\\"mat-paginator-container\\\"]/div[2]/button[1][@disabled=\"true\"]");
    
  //By xmlBtnNextPage            = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[3]");
  //By xmlBtnNextPage            = By.xpath("//div[@class=\\\"mat-paginator-container\\\"]/div[2]/button[3]/span[1]");
    By xmlBtnNextPage = By.xpath("//*[@id=\"content-nav\"]/app-reglas/div/mat-paginator/div/div/div[2]/button[3]/span[1]");
    
    //By xmlBtnNextPageDisable     = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[3][@disabled='true']");
    By xmlBtnNextPageDisable     = By.xpath("//div[@class=\"mat-paginator-container\"]/div[2]/button[3][@disabled=\"true\"]");
    
  //By xmlBtnPreviousPageDisable = By.xpath("//div[@class='mat-paginator-container']/div[2]/button[2][@disabled='true']");
    By xmlBtnPreviousPageDisable = By.xpath("//div[@class=\"mat-paginator-container\"]/div[2]/button[2][@disabled=\"true\"]");
    
    
    
    /*
     * Variables que reciben un parametro pantalla consulta de inventarios
     * *******************************************************************************************/
    String SrtTipoConsulta = ("//div[@id=\"mat-select-4-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String SrtPlaza = ("//div[@id=\"mat-select-6-panel\"]/mat-option/span[contains(text(),' %s ')]");
    String SrtTienda = ("//section/span[2]/ul/div/li/mat-checkbox/label/span[2][contains(text(),' %s ')]//preceding-sibling::span");
    String SrtDescripEspacio = ("//tbody/tr/td[2][contains(text(),' %s ')]");
    String SrtCantidadEspacio = ("//tbody/tr/td[2][contains(text(),' %s ')]//following-sibling::td/input");
    
	/**
	 * Constructor
	 ******************************************************************************************/
	public Reglas_IEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }

	
	public void clickMenuAdmon() {
	
		u.highLight(xmlMenuAdm);
		u.click(xmlMenuAdm);
	
		u.unhighLight(xmlMenuAdm);
	}
	
	//**   Da Clic al SubMenu Reglas
	//********************************************************	
	public void clickAdmorReglas() {
	
		u.highLight(xmlReglas);
		u.click(xmlReglas);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		testCase.addScreenShotCurrentStep(u, "admorReglas");
    
		u.unhighLight(xmlReglas);
		u.mouseOver(xmlHeader);
    
	}

	//Metodo: Da click sobre el boton Buscar
	public void clickBotonBuscar() {
	
		u.highLight(xmlBotonBuscar);
		u.click(xmlBotonBuscar);
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "BotonBuscar");
	
		u.unhighLight(xmlBotonBuscar);
	
	}

	/**
	 * Método: Captura  Nombre de la Regla 
	 * @author: Manuel Cuen
	 * @param:  NombreRegla, dato necesario para la Prueba, alimentado de su Data Provider
	 * @return: Ninguno
	 */
	public void clickIdReglatxt(String NomReglaBuscar, Boolean band) {
	
		if(band == true) {
			u.highLight(xmlNomReglaNvo);
			u.getDriver().findElement(xmlNomReglaNvo).clear();
			u.sendKeys(xmlNomReglaNvo, NomReglaBuscar);
			u.hardWait(2);
		}else  if(band == false) {
			u.highLight(xmlNomRegla);
			u.getDriver().findElement(xmlNomRegla).clear();
			u.sendKeys(xmlNomRegla, NomReglaBuscar);
			u.hardWait(2);	
		}
		
	
		testCase.addScreenShotCurrentStep(u, "clickIdReglatxt");
		u.hardWait(2);
		
		if(band == true) {
			u.unhighLight(xmlNomReglaNvo);
		}else  if(band == false) {
			u.unhighLight(xmlNomRegla);
		}
	}

	/**
	 * Metodo:  Para Capturar Nombre de la Regla 
	 * @author: Manuel Cuen
	 * @param:  NombreRegla, dato necesario para la Prueba, alimentar de su Data Provider
	 * @return: Ninguno
	 */
	public void clickNomReglaEditar(String NombreRegla) {
	
		u.highLight(xmlNomReglaEdi);
		u.getDriver().findElement(xmlNomReglaEdi).clear();
		u.sendKeys(xmlNomReglaEdi, NombreRegla);
		u.hardWait(2);
	
		u.unhighLight(xmlNomReglaEdi);
	}

	/**
	 * Método: Para Capturar  descripcion de la Regla 
	 * @author: Manuel Cuen
	 * @param: descripcionTxt, dato necesario para la Prueba, alimentado de su Data Provider
	 * @return: Ninguno
	 */
	public void clickDescripTxt(String descripcionTxt) {
	
		u.highLight(xmlDescriptxt);
		u.getDriver().findElement(xmlDescriptxt).clear();
		u.sendKeys(xmlDescriptxt,descripcionTxt);
		System.out.println(xmlDescriptxt);
		
		testCase.addScreenShotCurrentStep(u, "clickDescripTxt");
		u.hardWait(2);
	
		u.unhighLight(xmlDescriptxt);
	}

	/**
	 * Metodo:  Para Capturar y dar click en los datos de la pantala nueva regla 
	 * @author: Manuel Cuen
	 * @param:  descripcionTxt,Obligatoria,EstatusTxt,nomTienda,plaza,sbPlaza datos necesario para dar alta regla, alimentar de su Data Provider
	 * @return: Ninguno
	 */
	public void clickDesripTxt(String descripcionTxt, String Obligatoria, String EstatusTxt, String nomTienda, String plaza, String sbPlaza) {
	
		u.highLight(xmlDescripNvo);
		u.sendKeys(xmlDescripNvo, descripcionTxt);
		u.hardWait(2);
	
		By LocRadioObliga= By.xpath(String.format(SrtRadioObliga, Obligatoria));
		u.click(LocRadioObliga);
		u.hardWait(2);
	
		By LocRadioEstatus= By.xpath(String.format(SrtRadioEstatus, EstatusTxt));
		u.click(LocRadioEstatus);
		u.hardWait(2);
	
		testCase.addScreenShotCurrentStep(u, "clickDescripTxt");
		u.hardWait(2);
	
	}

	/**
	 * Metodo:  Para Capturar y dar click en los datos de la pantala nueva regla, pesta�a datos grales 
	 * @author: Manuel Cuen
	 * @param:  descripcionTxt,Obligatoria,EstatusTxt,nomTienda,plaza,sbPlaza datos necesario para dar alta regla, alimentar de su Data Provider
	 * @return: Ninguno
	 */
	public void clickpDatosGrales(String descripcionTxt, String Obligatoria, String EstatusTxt, String nomTienda, String plaza, String sbPlaza) {
	
		u.highLight(xmlDescripNvo);
		u.getDriver().findElement(xmlDescripNvo).clear();
		u.sendKeys(xmlDescripNvo, descripcionTxt);
		u.hardWait(2);
	
		By LocRadioObliga= By.xpath(String.format(SrtRadioObliga, Obligatoria));
		u.click(LocRadioObliga);
		u.hardWait(2);
	
		By LocRadioEstatus= By.xpath(String.format(SrtRadioEstatus, EstatusTxt));
		u.click(LocRadioEstatus);
		u.hardWait(2);
	
		testCase.addScreenShotCurrentStep(u, "clickpDatosGrales");
		u.hardWait(2);
	
		u.unhighLight(xmlDescripNvo);
	}
	/**
	 * Metodo:  Seleccioana el Estatus y da clic sobre el
	 * @author: Manuel Cuen
	 * @param:  EstatusTxt, dato necesario para la Prueba, alimentado de su Data Provider
	 * @return: Ninguno
	 */
	public void clickStatus(String EstatusTxt) {
		
		u.highLight(xmlStatus);
		u.highLight(xmlStatusFle);
		u.click(xmlStatusFle);
		
		System.out.println(xmlStatusSel);
		System.out.println(EstatusTxt);
		
		By LocActivo = By.xpath(String.format(SrtStatusSel, EstatusTxt));
	    u.click(LocActivo);
		testCase.addScreenShotCurrentStep(u, "clickStatus");
		
		u.unhighLight(xmlStatus);
		u.unhighLight(xmlStatusFle);
		
	}

	/**
	 * Método: Capturar la Fecha inicial en A�o, Mes y Dia
	 * @author: Manuel Cuen
	 * @param:  year, mes, diaInicial, diaFinal, datos necesarios para la Prueba, alimentados de su Data Provider
	 * @return ninguno
	 */
	public void clickFechaIni(String Year,String Mes,String Dia) {
		
		u.highLight(xmlFecha);
		u.highLight(xmlFechaFle);
		u.click(xmlFechaFle);
		
		u.highLight(xmlFechaPeriodo);
		u.click(xmlFechaPeriodo);
		
		u.highLight(xmlFechaA);
		u.click(xmlFechaA);
	
		// Selecciona A�o Parametrizado
		By LocYear= By.xpath(String.format(SrtSelecionA, Year));
		u.click(LocYear);
		
		// Selecciona Mes Parametrizado
		By LocMes= By.xpath(String.format(StrSeleccioMes, Mes));
		u.click(LocMes);
		
		// Selecciona Dia Inicial Parametrizado
		By LocDiaInicial= By.xpath(String.format(StrSeleccioDia, Dia));
		u.click(LocDiaInicial);
	
		testCase.addScreenShotCurrentStep(u, "clickFechaIni");
		
		u.unhighLight(xmlFecha);
		u.unhighLight(xmlFechaFle);		
	}

	/**
	 * Método: Capturar la Fecha final en A�o, Mes y Dia
	 * @author Manuel Cuen
	 * @param user,year, mes, diaInicial, diaFinal, datos necesarios para la Prueba, alimentados de su Data Provider
	 * @return ninguno
	 */
	public void clickFechaFin(String Year,String Mes,String DiaFinal) {
		
		u.highLight(xmlFechaFinal);
		u.highLight(xmlFechaCalen);
		u.click(xmlFechaCalen);
		u.highLight(xmlFechaFinPerio);
		u.click(xmlFechaFinPerio);
		u.highLight(xmlFechaFinAva);
		u.click(xmlFechaFinAva);
		
		// Selecciona A�o Parametrizado
		By LocYear= By.xpath(String.format(SrtSelecionA, Year));
		u.click(LocYear);
			
		// Selecciona Mes Parametrizado
		By LocMes= By.xpath(String.format(StrSeleccioMes, Mes));
		u.click(LocMes);
			
		// Selecciona Dia Final Parametrizado
		By LocDiaFinal= By.xpath(String.format(StrSeleccionDiaFin, DiaFinal));
		u.click(LocDiaFinal);
		
		testCase.addScreenShotCurrentStep(u, "clickFechaFin");
		
		u.unhighLight(xmlFechaFinal);
		u.unhighLight(xmlFechaCalen);
			
	}

	/**
	 * Metodo:  Para Mostrar los campos/Etiquetas de la pantalla Regla
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickIdConsultaReglas() {
		
		u.highLight(xmlNomRegla);
		u.highLight(xmlDescripRegla);
		u.highLight(xmlEstatus);
		u.highLight(xmlFechaIni);
		u.highLight(xmlFechaFinFil);
		u.highLight(xmlBtnNuevo);
		u.highLight(xmlBtnExportar);
		u.highLight(xmlBtnLimpiar);
		u.highLight(xmlBtnBuscar);
		u.highLight(xmlIdRegla);
		u.highLight(xmlNombre);
		u.highLight(xmlDescrip);
		u.highLight(xmlObligatoria);
		u.highLight(xmlEstatusRes);
		u.highLight(xmlFechaCrea);
	
		
		testCase.addScreenShotCurrentStep(u, "IdConsultaReglas");
		
		u.unhighLight(xmlNomRegla);
		u.unhighLight(xmlDescripRegla);
		u.unhighLight(xmlEstatus);
		u.unhighLight(xmlFechaIni);
		u.unhighLight(xmlFechaFinFil);
		u.unhighLight(xmlBtnNuevo);
		u.unhighLight(xmlBtnExportar);
		u.unhighLight(xmlBtnLimpiar);
		u.unhighLight(xmlBtnBuscar);
		u.unhighLight(xmlIdRegla);
		u.unhighLight(xmlNombre);
	    u.unhighLight(xmlDescrip);
	    u.unhighLight(xmlObligatoria);
	    u.unhighLight(xmlEstatusRes);
	    u.unhighLight(xmlFechaCrea);
		testCase.addScreenShotCurrentStep(u, "IdConsultaReglas");
	}

	/**
	 * Metodo:  Para dar clic en el boton Limpiar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickBotonLimpiar() {
		
		u.highLight(xmlBotonLimpiar);
		u.click(xmlBotonLimpiar);
		testCase.addScreenShotCurrentStep(u, "clickBotonLimpiar");
		
		u.unhighLight(xmlBotonLimpiar);
	}

	/**
	 * Metodo:  Para Resaltar campos
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickSeLimpian() {
		
		u.highLight(xmlIdReglaTxt);
		u.highLight(xmlDescriptxt);
		u.highLight(xmlStatus);
		u.highLight(xmlFecha);
		u.highLight(xmlFechaFinal);
		testCase.addScreenShotCurrentStep(u, "clickSeLimpian");
		
		u.unhighLight(xmlIdReglaTxt);
		u.unhighLight(xmlDescriptxt);
		u.unhighLight(xmlStatus);
		u.unhighLight(xmlFecha);
		u.unhighLight(xmlFechaFinal);
	}

	/**
	 * Metodo:  Para dar clic en el boton nuevo
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickBtnNuevo() {
		
		u.highLight(xmlBtnNuevo);
		testCase.addScreenShotCurrentStep(u, "clickBtnNuevo1");
		u.click(xmlBtnNuevo);
		u.hardWait(4);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		testCase.addScreenShotCurrentStep(u, "clickBtnNuevo1");

		
	}

	/**
	 * Metodo:  Para dar clic en el boton Guardar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */ 
	public void clickBtnGuardarAplicar() {
		
		u.highLight(xmlBtnGuardarAplicar);
		testCase.addScreenShotCurrentStep(u, "clickBtnGuardarAplicar1");
		u.click(xmlBtnGuardarAplicar);
		u.hardWait(4);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		testCase.addScreenShotCurrentStep(u, "clickBtnGuardarAplicar1");
		
		
	}

	/**
	 * Metodo:  Para Mostrar/Resaltar los campos y botones de la Pantalla Regla
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickPanCapturaRegla() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlpGeneral);
		u.highLight(xmlpCondicion);
		u.highLight(xmlpPosCondicion);
		u.highLight(xmlpAplicarA);
		u.highLight(xmlpMensajes);
		u.highLight(xmlBotonCancelar);
		u.highLight(xmlBtnLimpiarGral);
//		u.highLight(xmlBtnGuardarGral);
	
		testCase.addScreenShotCurrentStep(u, "clickPanCapturaRegla");
		u.hardWait(2);
		
		u.unhighLight(xmlpGeneral);
		u.unhighLight(xmlpCondicion);
		u.unhighLight(xmlpPosCondicion);
		u.unhighLight(xmlpAplicarA);
		u.unhighLight(xmlpMensajes);
		u.unhighLight(xmlBotonCancelar);
		u.unhighLight(xmlBtnLimpiarGral);
		//u.unhighLight(xmlBtnGuardarGral);
		
	}
	
	/**
	 * Metodo:  Para dar clic a la pesta�a condicion
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickPesCondicion() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlpCondicion);
		u.click(xmlpCondicion);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickPesCondicion");
		u.hardWait(2);
	}
	
	public void clickIngresarEspaciosEquipos(String TipoConsulta,String plaza,String nomTienda) {
		
		/*m.explicitWaitToBeInvisible(20);
		u.mouseOverAndClick(xmlMenus);
		
		u.click(xmlMenuAdm);
		
		u.highLight(xmlMenuEspacio);
		u.click(xmlMenuEspacio);
		u.highLight(xmlSbConsultaInvent);
		u.click(xmlSbConsultaInvent);
		u.mouseOverAndClick(xmlHeader);*/
		
		u.highLight(xmlTpoConsulta);
		u.click(xmlTpoConsulta);
//		u.mouseOverAndClick(xmlTpoConsulta);
		
		By LocTipoCons= By.xpath(String.format(SrtTipoConsulta, TipoConsulta));
		System.out.println(SrtTipoConsulta);
		u.click(LocTipoCons);
		
		u.highLight(xmlPlaza);
		u.click(xmlPlaza);
		
		By Locplaza= By.xpath(String.format(SrtPlaza, plaza));
		u.click(Locplaza);
		u.hardWait(5);
		
		By LocTienda= By.xpath(String.format(SrtTienda, nomTienda));
		u.click(LocTienda);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickIngresarEspaciosEquipos");
		u.hardWait(2);
		
		u.highLight(xmlBtnBuscarConsInv);
		u.click(xmlBtnBuscarConsInv);
		
	}

	/**
	 * Metodo:  Para llenar campos de la pesta�a condicion
	 * @author: Manuel Cuen
	 * @param:  nomTienda, Es necesario llenar este dat en su dato provider para el alta de Regla
	 * @return: Ninguno
	 */
	public void clickPesCondicion(String nomTienda) {
		
		//u.click(xmlpCondicion);
		
		u.highLight(xmlpCondicion);
		u.click(xmlpCondicion);
		u.hardWait(2);
		
		//u.highLight(xmlpTextCondicion);
		//u.getDriver().findElement(xmlpTextCondicion).clear();
		u.highLight(xmlpBorraCondicion);
		u.click(xmlpBorraCondicion);
		
		System.out.println(SrtAtributoTda);
		
		By LocAtributoTda = By.xpath(String.format(SrtAtributoTda, nomTienda));
		u.click(LocAtributoTda);
		u.hardWait(2);
		
		u.highLight(xmlValidar);
		u.click(xmlValidar);
		u.hardWait(2);
		
		//SrtAtribEspacio, SrtCantIdEspacio
		
		testCase.addScreenShotCurrentStep(u, "clickPesCondicion");
		u.hardWait(2);
	
	}

	/**
	 * Metodo:  Para Mostrar campos de la pesta�a condicion
	 * @author: Manuel Cuen
	 * @param:  Ninguna
	 * @return: Ninguno
	 */
	public void clickMuestraEtiqCondicion() {
		
		u.highLight(xmlTablero);
		u.highLight(xmlMsjCondicion);
		u.highLight(xmlLblAtriTienda);
		u.highLight(xmlAtriEspacio);
		u.highLight(xmlATriCantIdEspacio);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickMuestraEtiqCondicion");
		u.hardWait(2);
		u.unhighLight(xmlTablero);
		u.unhighLight(xmlMsjCondicion);
		u.unhighLight(xmlLblAtriTienda);
		u.unhighLight(xmlAtriEspacio);
		u.unhighLight(xmlATriCantIdEspacio);
		u.unhighLight(xmlValidar);
	
	}

	/**
	 * Metodo:  Para dar clic en la pesta�a postcondicion
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickPesPosCondicion() {
		
		u.unhighLight(xmlpCondicion);
		u.highLight(xmlpPosCondicion);
		u.click(xmlpPosCondicion);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "clickPesPosCondicion");
		u.hardWait(2);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
	
	}

	/**
	 * Metodo:  Para llenar campos de la pesta�a postcondicion
	 * @author: Manuel Cuen
	 * @param:  cantidPosCond,espacio, Es necesario llenar estos campos en su dato provider para el alta de Regla
	 * @return: Ninguno
	 */
	public void clickMuestraPesPoscondicion(String cantidPosCond,String espacio, String op) {
		System.out.println(xmlselectEspacios);
		System.out.println(espacio);
		
		if(op.toUpperCase().equals("E")) {
			u.highLight(xmlBtnQuitar);
			u.click(xmlBtnQuitar);
		} 
		u.highLight(xmlMsgPoscond);
		u.highLight(xmlBtnAgregar);
		u.click(xmlBtnAgregar);
		u.highLight(xmlBtEliminarAgre);
		u.highLight(xmlBtSeleccAgre);
		u.click(xmlBtSeleccAgre);
		
		u.highLight(xmlselectEspacios);
		Select spa = new Select(u.getDriver().findElement(xmlselectEspacios));
		
		spa.selectByVisibleText(espacio);
		//spa.selectByValue("189");
	    //u.highLight(xmlselectEspacios);
	    //u.sendKeys(xmlselectEspacios, espacio);
		
	
	    u.highLight(xmlCantidPosCon);
		u.sendKeys(xmlCantidPosCon, cantidPosCond);
		
		u.highLight(xmlBtCantidAgre);
	
		testCase.addScreenShotCurrentStep(u, "clickMuestraPesPoscondicion");
		u.hardWait(2);
		
		u.unhighLight(xmlMsgPoscond);
		u.unhighLight(xmlBtnAgregar);
		u.unhighLight(xmlBtEliminarAgre);
		u.unhighLight(xmlBtSeleccAgre);
		u.unhighLight(xmlBtCantidAgre);
	
	}

	/**
	 * Metodo:  Para llenar campos de la pesta�a AplicarA
	 * @author: Manuel Cuen
	 * @param:  plaza,sbPlaza, Es necesario llenar estos campos en su dato provider para el alta de Regla
	 * @return: Ninguno
	 */
	public void clickPesAplicarA(String plaza, String sbPlaza) {
		
		u.unhighLight(xmlpPosCondicion);
		u.highLight(xmlpAplicarA);
		u.click(xmlpAplicarA);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "aplicarA");
		
		By LocAplicarPlaza = By.xpath(String.format(SrtAplicarPlaza, plaza));
		u.click(LocAplicarPlaza);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep(u, "aplicarPlaza");
		
		u.highLight(xmlFlechaPlza);
		testCase.addScreenShotCurrentStep(u, "aplicarFlechaPlaza");
		u.click(xmlFlechaPlza);
		u.hardWait(5);
		
		By LocAplicarSbPlaza = By.xpath(String.format(SrtAplicarSbPlaza, sbPlaza));
		u.click(LocAplicarSbPlaza);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep(u, "aplicarSbPlaza");
		
		u.highLight(xmlFlechaSbPlaza);
		testCase.addScreenShotCurrentStep(u, "aplicarFlechaSbPlaza");
		u.click(xmlFlechaSbPlaza);
		
		u.hardWait(2);
	    testCase.addScreenShotCurrentStep(u, "clickDescripTxt");
		u.hardWait(2);
		//SrtAplicarTienda,SrtAplicarPlazaCcm,SrtAplicarEspacios
	}

	/**
	 * Metodo:  Para limpiar campos de la pesta�a AplicarA
	 * @author: Manuel Cuen
	 * @param:  borraPlaza, Es necesario llenar este campo en su dato provider para modificar su plaza
	 * @return: Ninguno
	 */
	public void clickBorraPlazaSubPlaza(String borraPlaza) {
		
		u.unhighLight(xmlpPosCondicion);
		u.highLight(xmlpAplicarA);
		u.click(xmlpAplicarA);
		u.hardWait(3);
		
	//	u.hardWait(3);
		By LocBorraPlaza = By.xpath(String.format(SrtDeseleccionaPlaza, borraPlaza));
		u.click(LocBorraPlaza);
		
		u.highLight(xmlFlechaAtrasPlza);
		u.click(xmlFlechaAtrasPlza);
		
	    testCase.addScreenShotCurrentStep(u, "clickBorraPlazaSubPlaza");
		u.hardWait(2);
	}

	/**
	 * Metodo:  Para Mostrar/Resaltar campos/Etiquetas de la pesta�a AplicarA
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMuestraPesAplicarA() {
		
		u.highLight(xmlMsjAplicarA);
		u.highLight(xmlLblPlazaApliA);
		u.unhighLight(xmlFlechaPlza);
		u.highLight(xmlLblSbPlazaApliA);
		u.highLight(xmlLblIendaApliA);
		u.highLight(xmlLblSPlzaCcmApliA);
		u.highLight(xmlLblEspacioApliA);
		
	    testCase.addScreenShotCurrentStep(u, "clickMuestraPesAplicarA");
		u.hardWait(2);
		
		u.unhighLight(xmlMsjAplicarA);
		u.unhighLight(xmlLblPlazaApliA);
		u.unhighLight(xmlLblSbPlazaApliA);
		u.unhighLight(xmlFlechaSbPlaza);
		u.unhighLight(xmlLblIendaApliA);
		u.unhighLight(xmlLblSPlzaCcmApliA);
		u.unhighLight(xmlLblEspacioApliA);
	
	}

	/**
	 * Metodo:  Para Capturar campo mensaje de la pesta�a Mensaje
	 * @author: Manuel Cuen
	 * @param:  Mensaje, Es necesario llenar este campo en su dato provider para el alta de regla
	 * @return: Ninguno
	 */
	public void clickPesMensaje(String Mensaje) {
		
		u.unhighLight(xmlpMensajes);
		testCase.addScreenShotCurrentStep(u, "mensaje");
		u.click(xmlpMensajes);
		
		u.highLight(xmlMensajeTxt);
		u.getDriver().findElement(xmlMensajeTxt).clear();
		u.sendKeys(xmlMensajeTxt, Mensaje);
		
	    testCase.addScreenShotCurrentStep(u, "clickPesMensaje");
		u.hardWait(2);
	
		
	}

	/**
	 * Metodo:  Para Mostrar la pesta�a mensaje
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMuestraPesMensaje() {
		
		u.highLight(xmlMsjMsj);
		
	    testCase.addScreenShotCurrentStep(u, "clickMuestraPesMensaje");
		u.hardWait(2);
	
		u.unhighLight(xmlMsjMsj);
		u.unhighLight(xmlMensajeTxt);
	}

	/**
	 * Metodo:  Para Seleccionar y modificar datos de una regla por su Id regla
	 * @author: Manuel Cuen
	 * @param:  idRegla, Es necesario llenar este campo en su dato provider para Modificar datos de regla
	 * @return: Ninguno
	 */
	public void clickSelReglaEditar(String idRegla) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		
		u.hardWait(3);
		By LocIconEditar = By.xpath(String.format(SrtIdRegla, idRegla));
		u.highLight(LocIconEditar);
		testCase.addScreenShotCurrentStep(u, "iconEditar");
		u.click(LocIconEditar);
		u.hardWait(4);
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		testCase.addScreenShotCurrentStep(u, "clickSelReglaEditar");
		
	}
	
	/**
	 * Metodo:  Para Seleccionar Id regla y eliminarlo
	 * @author: Manuel Cuen
	 * @param:  idRegla, Es necesario llenar este campo en su dato provider para eliminar regla
	 * @return: Ninguno
	 */
	public void clickSelReglaEliminar(String idRegla) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		
		//u.hardWait(2);
		By LocIChkEliminar = By.xpath(String.format(SrtChkEliminar, idRegla));
		u.click(LocIChkEliminar);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickSelReglaEliminar");
		u.hardWait(2);
	}

	/**
	 * Metodo:  Para dar clic en el boton Eliminar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickBotonEliminar() {
		
		u.hardWait(2);
		u.highLight(xmlBtnEliminar);
		u.click(xmlBtnEliminar);
		
		testCase.addScreenShotCurrentStep(u, "clickBotonEliminar");
		u.hardWait(2);
	}
	
	/**
	 * Metodo:  Para Mostrar mensaje y campos de la ventana modal Eliminar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMsjVentanaModal() {
		
		u.highLight(xmlMsjVentanModal);
		u.highLight(xmlMsjBtnCancelar);
		u.highLight(xmlMsjbtnConfir);
		
		testCase.addScreenShotCurrentStep(u, "clickBotonEliminar");
		u.hardWait(2);
		
		u.unhighLight(xmlMsjVentanModal);
		u.unhighLight(xmlMsjBtnCancelar);
		u.unhighLight(xmlMsjbtnConfir);

	}
	
	/**
	 * Metodo:  Para dar clic en el boton confirmar eliminar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickConfirEliminar() {
		
		u.highLight(xmlMsjbtnConfir);
		testCase.addScreenShotCurrentStep(u, "confirEliminar");
		u.click(xmlMsjbtnConfir);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickConfirEliminar");
		u.hardWait(2);
	}
	
	public void clickResultBusquedaPlazaTda() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		u.scrollToElement(xmlLblCr);
		u.highLight(xmlLblCr);
		u.highLight(xmlLblTienda);
		u.highLight(xmlLblPlaza);
		u.highLight(xmlLblSbPlaza);
		
		u.highLight(xmlLblAsesor);
		u.highLight(xmlLblSegmento);
//		u.highLight(xmlLblSegMaestro);
		
		u.highLight(xmlLblRetek);
		u.highLight(xmlLblM2);
		u.highLight(xmlLblTiendaNicho);
//		u.click(xmlLblTiendaNicho);
		
		testCase.addScreenShotCurrentStep(u, "clickResultBusquedaPlazaTda");
		u.hardWait(2);
		
		u.unhighLight(xmlLblCr);
		u.unhighLight(xmlLblTienda);
		u.unhighLight(xmlLblPlaza);
		u.unhighLight(xmlLblSbPlaza);
		
		u.unhighLight(xmlLblAsesor);
		u.unhighLight(xmlLblSegmento);
//		u.unhighLight(xmlLblSegMaestro);
		
		u.unhighLight(xmlLblRetek);
		u.unhighLight(xmlLblM2);
		u.unhighLight(xmlLblTiendaNicho);
	}
	
	public void clickCambiaValoresEspacios(String nomEspacioEquipo, String cantEspacios) {
		
		u.highLight(xmlCr);
		u.click(xmlCr);
		
		u.highLight(xmlNomEspInv);
		u.sendKeys(xmlNomEspInv, nomEspacioEquipo);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickCambiaValoresEspacios");
		
		By LocDescripEspac = By.xpath(String.format(SrtDescripEspacio, nomEspacioEquipo));
		
		By LocCantEspacio = By.xpath(String.format(SrtCantidadEspacio, nomEspacioEquipo));
		u.getDriver().findElement(LocCantEspacio).clear();
		System.out.println(LocCantEspacio);
		u.click(LocCantEspacio);
		u.sendKeys(LocCantEspacio, cantEspacios);
		u.click(LocDescripEspac);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickCambiaValoresEspacios");
		u.hardWait(2);
		u.unhighLight(xmlCr);
		u.unhighLight(xmlNomEspInv);
	}
	
	public void clickMensajeReglaObligatoria() {
		
		
		u.hardWait(2);
		u.highLight(xmlMsjReglaObliga);
		testCase.addScreenShotCurrentStep(u, "clickMensajeReglaObligatoria1");
		u.highLight(xmlBtnCerrarMsjRegObl);
		u.hardWait(2);
		u.click(xmlBtnCerrarMsjRegObl);
		
		testCase.addScreenShotCurrentStep(u, "clickMensajeReglaObligatoria2");
		u.hardWait(2);
	}
	
	public boolean existeMensajeReglaObligatoria() {
		
		return m.existElement(xmlMsjReglaObliga);
	}
	
	
	
	public Boolean clickSeleccionMultipleReglas (ArrayList <String> Reglas) {
		   
		   m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
		   Boolean isSelected = false, validacion = false;
		   int selectedRegla = 0;
		   int paginasAvanzar = 0;
		   Boolean ciclo = false;
		   Boolean exist = false;

			   for(int i = 0; i < Reglas.size(); i++) {
			   
				   String locRegla = String.format(SrtChkEliminar, Reglas.get(i));
				   System.out.println(locRegla);
				   By element = By.xpath(locRegla);
				   System.out.println(element);
				  
//				   u.click(element); 
			   
				   do {
					   if(m.existElement(element)==true) {
						   ciclo = false;
						   exist = true;
						   System.out.print("-se encontro en el Grid\n");
						   u.highLight(element);
						   String checkStr = locRegla+"//preceding-sibling::td/mat-checkbox";
						   u.click(By.xpath(checkStr));
						   u.hardWait(2);
					   }

				   if(m.existElement(element)==false) {
					   if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
						   ciclo = false;
						   System.out.print("-no existen el registro en el Grid\n");
						   //testCase.addTextEvidenceCurrentStep("No se encontro el tipo de pedido especificado");
						   Assert.fail("No se encontro el tipo de pedido especificado en el Grid");

					   }else if(m.existElement(xmlBtnNextPageDisable)==false) {
						   ciclo = true;
						   u.click(xmlBtnNextPage);
						   m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
						   System.out.print("-cambia a la siguiente pagina para seguir buscando\n");
					   }else {
						   ciclo = false;
						   System.out.print("-no existen el registro en el Grid\n");
						   //testCase.addTextEvidenceCurrentStep("No se encontro el tipo de pedido especificado");
						   Assert.fail("No se encontro el tipo de pedido especificado en el Grid");
					   }
				   }
				   }while (ciclo);
			   }
//			   u.highLight(xmlAvancePag);
//			   u.click(xmlAvancePag);
			   
//	}
			   return validacion;	   
}

	public void clickBtnExportar() {
		
		u.highLight(xmlBtnExportar);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickBtnExportar");
		u.hardWait(2);
		u.click(xmlBtnExportar);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickBtnExportar");
		
	}
	
	public void clickMensajeReporteGenerado() {
		
		testCase.addScreenShotCurrentStep(u, "clickMensajeReporteGenerado");
		u.hardWait(2);
		u.unhighLight(xmlBtnExportar);
	}
}
