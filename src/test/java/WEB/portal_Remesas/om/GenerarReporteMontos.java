package WEB.portal_Remesas.om;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;

import WEB.om.MetodosExtra;
import WEB.sarv3.om.ReportesSARv3;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class GenerarReporteMontos {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	MetodosExtra me;
	ReportesSARv3 r;
	
	/*
	 * Localizadores Pantalla Genera Reporte por Montos
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");
    By xmlOpPortalRemesas = By.xpath("//a[contains(text(),'Portal Remesas')]");
    									//*[@id="homeSubview:j_idt61"]/div/div[2]/div[1]/div[1]/div[2]/div/ul/li[18]/a
    By xmlMenuEnviosDinero = By.xpath("//*[@id=\"ddtopmenubar\"]/ul/li[2]/a");
    By xmlOpReportes = By.xpath("//*[@id=\"submenu1\"]/li/a"); 
    By xmlOpMontos = By.xpath("//*[@id=\"subsubmenu1\"]/li[1]/a");
    By xmlOpRutas = By.xpath("//*[@id=\"subsubmenu1\"]/li[2]/a");
    By xmlFechaInicial = By.xpath("//*[@id=\"form1:fechaInicio_input\"]");
    By xmlFechaFin = By.xpath("//*[@id=\"form1:fechaFin_input\"]");
    By xmlBtnGenerar = By.xpath("//*[@id=\"form1:btnReporte\"]");
    By xmlMsjSinResultados = By.xpath("//*[@id=\"form1:messages\"]/div/ul/li/span[2]");
//    By xmlBtnGenerarExcel = By.xpath("//*[@id=\"form1:btnExcel\"]");
    									//*[@id=\"form1:btnReporte\"]
    By xmlBtnGenerarExcel = By.xpath("//*[@id=\"form1:generaReporte\"]"); 
    By xmlChkEnExcel = By.xpath("//*[@id=\"form1:salidaReporte\"]/tbody/tr/td[3]/div/div[2]");
    By xmlChkEnPantalla = By.xpath("//*[@id=\"form1:salidaReporte\"]/tbody/tr/td[1]/div/div[2]");
                                    //*[@id="form1:salidaReporte"]/tbody/tr/td[1]/div/div[2]
    By xmlMsjError = By.xpath("//*[@id=\"form1:messages\"]/div/ul/li/span[2]");
    
// Reporte
    By xmlLblFecha = By.xpath("//*[@id=\"form1:tablaMontos:j_idt169\"]/span[1]");
    By xmlLblZona = By.xpath("//*[@id=\"form1:tablaMontos:j_idt171\"]/span[1]");
    By xmlLblPlaza = By.xpath("//*[@id=\"form1:tablaMontos:j_idt173\"]/span[1]");
    By xmlLblTienda = By.xpath("//*[@id=\"form1:tablaMontos:j_idt175\"]/span[1]");
    By xmlLblProveedorRep = By.xpath("//*[@id=\"form1:tablaMontos:j_idt177\"]/span[1]");
    By xmlLblMonto = By.xpath("//*[@id=\"form1:tablaMontos:j_idt179\"]/span[1]");
    By xmlTxtBuscar = By.xpath("//*[@id=\"form1:j_idt167\"]");
    By xmlTxtBuscarAnalisis = By.xpath("//*[@id=\"form1:buscar\"]");
    By xmlTxtMostrarNumPag = By.xpath("//*[@id=\"form1:mostrarResultados\"]/div[3]/span");
    By xmlTotalFilas = By.xpath("//*[@id=\"form1:mostrandoResultados\"]");
    
 // Reporte de Analisis por Dias
    By xmlOpAnalisisXDias = By.xpath("//*[@id=\"subsubmenu1\"]/li[3]/a");
    By xmlOpAnalisisXDiasRet = By.xpath("//*[@id=\"subsubmenu2\"]/li[3]/a");
    By xmlLblZonaAXD = By.xpath("//*[@id=\"form1:tablaDias:j_idt185\"]/span");
    By xmlLblPlazaAXD = By.xpath("//*[@id=\"form1:tablaDias:j_idt186\"]/span");
    By xmlLblTiendaAXD = By.xpath("//*[@id=\"form1:tablaDias:j_idt187\"]/span");
    By xmlLblProveedorRepAXD = By.xpath("//*[@id=\"form1:tablaDias:j_idt188\"]/span");
    By xmlAcumuladoTrans = By.xpath("//*[@id=\"form1:tablaDias:j_idt189\"]/span");
    By xmlDiaLunes = By.xpath("//*[@id=\"form1:tablaDias:j_idt193\"]/span");
    By xmlDiaMartes = By.xpath("//*[@id=\"form1:tablaDias:j_idt194\"]/span");
    By xmlDiaMiercoles = By.xpath("//*[@id=\"form1:tablaDias:j_idt195\"]/span");
    By xmlDiaJueves = By.xpath("//*[@id=\"form1:tablaDias:j_idt196\"]/span");
    By xmlDiaViernes = By.xpath("//*[@id=\"form1:tablaDias:j_idt197\"]/span");
    By xmlDiaSabado = By.xpath("//*[@id=\"form1:tablaDias:j_idt198\"]/span");
    By xmlDiaDomingo = By.xpath("//*[@id=\"form1:tablaDias:j_idt199\"]/span");
    By xmlSumatoria = By.xpath("//*[@id=\"form1:tablaDias:j_idt190\"]/span");
//    By xmlMostrarTipo = By.xpath("//*[@id=\"form1:tablaHoras:j_idt226\"]/span");
    By xmlMostrarTipo = By.xpath("//*[@id=\"form1:tablaDias:j_idt201\"]/span");
    By xmlMostrarmonto = By.xpath("//*[@id=\"form1:tablaDias:j_idt202\"]/span");
    By xmlChekTransa = By.xpath("//*[@id=\"form1:modo\"]/tbody/tr/td[1]/div/div[2]");
    By xmlChekMonto = By.xpath("//*[@id=\"form1:modo\"]/tbody/tr/td[3]/div/div[2]");
   
    By xmlMontoAcumulado = By.xpath("//*[@id=\"form1:modo\"]/tbody/tr/td[3]/div/div[2]");
    By xmlTransAcumuladas = By.xpath("//*[@id=\"form1:modo\"]/tbody/tr/td[1]/div/div[2]/span");
    By xmlSumaMonto = By.xpath("//*[@id=\"form1:tablaDias:j_idt191\"]/span");
    
 // Reporte de Analisis por Horas
    By xmlOpAnalisisXHoras = By.xpath("//*[@id=\"subsubmenu1\"]/li[4]/a");
    By xmlLblZonaAXH = By.xpath("//*[@id=\"form1:tablaHoras:j_idt177\"]/span");
    By xmlLblPlazaAXH = By.xpath("//*[@id=\"form1:tablaHoras:j_idt178\"]/span");
    By xmlLblTiendaAXH = By.xpath("//*[@id=\"form1:tablaHoras:j_idt179\"]/span");
    By xmlLblProveedorRepAXH = By.xpath("//*[@id=\"form1:tablaHoras:j_idt180\"]/span");
    By xmlAcumuladoTransAXH = By.xpath("//*[@id=\"form1:tablaHoras:j_idt181\"]/span");
    By xml0800 = By.xpath("//*[@id=\"form1:tablaHoras:j_idt193\"]/span");
    By xml0900 = By.xpath("//*[@id=\"form1:tablaHoras:j_idt194\"]/span");
    By xml1000 = By.xpath("//*[@id=\"form1:tablaHoras:j_idt195\"]/span");
    By xml1100 = By.xpath("//*[@id=\"form1:tablaHoras:j_idt196\"]/span");
    By xml1200 = By.xpath("//*[@id=\"form1:tablaHoras:j_idt197\"]/span");
    By xml1300 = By.xpath("//*[@id=\"form1:tablaHoras:j_idt198\"]/span");
    By xml1400 = By.xpath("//*[@id=\"form1:tablaHoras:j_idt199\"]/span");
    By xml1500 = By.xpath("//*[@id=\"form1:tablaHoras:j_idt200\"]/span");
    By xml1600 = By.xpath("//*[@id=\"form1:tablaHoras:j_idt201\"]/span");
    By xml1700 = By.xpath("//*[@id=\"form1:tablaHoras:j_idt202\"]/span");
    By xml1800 = By.xpath("//*[@id=\"form1:tablaHoras:j_idt203\"]/span");
    By xmlSumatoriaTrans = By.xpath("//*[@id=\"form1:tablaHoras:j_idt182\"]/span");
    By xmlSumatoriaMonto = By.xpath("//*[@id=\"form1:tablaHoras:j_idt183\"]/span");
    By xmlHoraDe = By.xpath("//*[@id=\"form1:tp1_input\"]");
    By xmlHoraHasta = By.xpath("//*[@id=\"form1:tp2_input\"]");
    By xmlMostrarTipoHra = By.xpath("//*[@id=\"form1:tablaHoras:j_idt226\"]/span");
    By xmlMostrarmontoHra = By.xpath("//*[@id=\"form1:tablaHoras:j_idt227\"]/span");
    
    
// Localizadores del Menu Configuracion
    By xmlMenuConfiguracion = By.xpath("//*[@id=\"menu3\"]");
    By xmlEnvioyRetiro = By.xpath("//*[@id=\"submenu3\"]/li[1]/a");
    By xmlListaProv = By.xpath("//a[contains(text(),'Lista de Proveedores')]");
    By xmlAtributosCompart = By.xpath("//*[@id=\"subsubmenu3\"]/li[4]/a");
    By xmlLblEnvioDinero = By.xpath("//ul[@id='submenu3']/li[1]/a");
    By xmlLbAtributosProv = By.xpath("//ul[@id='submenu3']/li[2]/a");
    
    
    By xmlValorAtrib = By.xpath("//*[@id=\"form1:tablaAtributos_data\"]/tr[6]/td[3]");    
    By xmlBtnModif = By.xpath("//*[@id=\"form1:j_idt147\"]");
    By xmlValorAtributo = By.xpath("//*[@id=\"form1:valorAtr\"]");
    By xmlBtnActualizar = By.xpath("//*[@id=\"form1:j_idt141\"]");
    By xmlMensajeActual = By.xpath("//*[@id=\"form1:messages\"]/div/ul/li");
    By xmlProveedor = By.xpath("//div[@id='form1:proveedor']");
    By xmlProveedorDesplegar = By.xpath("//div[@id=\"form1:proveedor\"]/div[3]");
    By xmlOpProveedor = By.xpath("//div[@class=\"ui-selectonemenu-items-wrapper\"]/ul/li");    
    By xmlMostrarProvedoor = By.xpath("//*[@id=\"form1:proveedor_panel\"]");
    By xmlListaRemesadoresAP = By.xpath("//div[@id='form1:proveedor_panel']/div/ul/li");
    By xmlOpcionesAP = By.xpath("//tbody/tr/td/a");
    By xmlBtnParametros = By.xpath("//*[@id=\"form1:gridOutputPanel\"]/table/tbody/tr[1]/td[1]/a"); 
   
    
    //By xmlFormaDePago = By.xpath("//*[@id=\"form1:tablaAtributos_data\"]/tr[4]/td[2]");
    By xmlFormaDePago = By.xpath("//*[@id=\"form1:tablaAtributos_data\"]/tr/td[2][text()='FORMA-PAGO']");
   
    By xmlDescParametro = By.id("form1:descAtributo");
    By xmlDescParametroEmpty = By.xpath("//input[@id='form1:descAtributo'][@value]");
    By xmlUnidadNegocio = By.id("form1:unidadNeg");
    By xmlUnidadNegocioEmpty = By.xpath("//input[@id='form1:unidadNeg'][@value]");
    By xmlValorParametro = By.xpath("//div[@id='form1:combo']/label");
    By xmlValorParametriOp = By.xpath("//div[@id='form1:combo_panel']/div/ul/li");
    By xmlValorParametroList = By.xpath("//div[@id='form1:combo_panel']/div/ul");
    
    By xmlParametroFDP = By.xpath("//*[@id=\"form1:combo_label\"]");
    By xmlOpcionesFDP = By.xpath("//*[@id=\"form1:combo\"]/div[3]");
    By xmlBtnEfectivo = By.xpath("//*[@id=\"form1:combo_panel\"]/div/ul/li[1]");
    By xmlBtnActualizarFDP = By.xpath("//*[@id=\"form1:j_idt147\"]");
    By xmlBtnModificar = By.xpath("//*[@id=\"form1:j_idt149\"]");
    By xmlMsj = By.xpath("//div[@id='form1:messages']/div/ul");
    By xmlMsjParte1 = By.xpath("//div[@id='form1:messages']/div/ul/li/span[1]");
    By xmlMsjParte2 = By.xpath("//div[@id='form1:messages']/div/ul/li/span[2]");
  
    
// Variables que reciben un Parametro    
    String SrtZonaOxxo = ("//div[@id=\"form1:selectedZonas\"]/ul/li[contains(text(),'%s')]");
    String SrtCRPlaza = ("//div[@id=\"form1:selectedPlazas\"]/ul/li[contains(text(),'%s')]");
    String SrtCRTienda = ("//div[@id=\"form1:selectedTiendas\"]/ul/li[contains(text(),'%s')]");
    String SrtProveedor = ("//div[@id=\"form1:selectedProveedor\"]/ul/li[contains(text(),'%s')]");
    String SrtNumRegistros = ("//div[@id=\"form1:mostrarResultados_panel\"]/div/ul/li[contains(text(),'%s')]");
    String SrtDiaSemana = ("//table[@id=\"form1:diasSeleccionados\"]/tbody/tr/td/label[contains(text(),'%s')]//parent::td/preceding-sibling::td[1]");
    String SrtHoraDeCal = ("//*[@id=\"ui-timepicker-div\"]/table/tbody/tr/td[1]/table/tbody/tr[2]/td[1]/a[contains(text(),'%s')]");
    String SrtMinutoDeCal = ("//*[@id=\"ui-timepicker-div\"]/table/tbody/tr/td[2]/table/tbody/tr[1]/td[2]/a[contains(text(),'%s')]");
    String SrtHoraHastaCal = ("//*[@id=\"ui-timepicker-div\"]/table/tbody/tr/td[1]/table/tbody/tr[3]/td[2]/a[contains(text(),'%s')]");
    String SrtMinutoHastaCal = ("//*[@id=\"ui-timepicker-div\"]/table/tbody/tr/td[2]/table/tbody/tr[1]/td[2]/a[contains(text(),'%s')]");
   				 
 
	/*
	 * Localizadores del modulo de Retiros de dinero
	 ******************************************************************************************/
    // Reportes Montos
    //String xmlMenuRetirosDinero = ("//*[@id=\"ddtopmenubar\"]/ul/li[3]/a");
    //By xmlMenuRetirosDinero = By.xpath("//*[@id=\\\"ddtopmenubar\\\"]/ul/li[3]/a");
    By xmlMenuRetirosDinero = By.xpath("//*[@id=\"ddtopmenubar\"]/ul/li[3]/a");									
    By xmlOpRetReportes = By.xpath("//*[@id=\"submenu2\"]/li[1]/a");
    By xmlOpMontosRetiros = By.xpath("//*[@id=\"subsubmenu2\"]/li[1]/a");
    By xmlOpAnalisisXHraRet = By.xpath("//*[@id=\"subsubmenu2\"]/li[4]/a");
    By xmlOpTiemposPromedios = By.xpath("//*[@id=\"subsubmenu2\"]/li[5]/a");
    
	/*
	 * Localizadores del modulo de Retiros de dinero - Operacion con Proveedor
	 ******************************************************************************************/
    By xmlOperacionProveedor = By.xpath("//*[@id=\"submenu2\"]/li[2]/a");
    By xmlProveedorRetiros = By.xpath("//*[@id=\"form1:proveedor_label\"]");//revisar donde usar este localizador
    By xmlEnlaceManual = By.xpath("//*[@id=\"form1:gridOutputPanel\"]/table/tbody/tr[1]/td[1]/a");
    By xmlNumeroTiendas = By.xpath("//*[@id=\"frmAltaManual:acordeon\"]/h3[3]/a");
    By xmlBtnExaminar = By.xpath("//*[@id=\"frmAltaManual:acordeon:uploader\"]/div[1]/span");
    By xmlArchivoCargado = By.xpath("//table[@class='ui-fileupload-files']//tr/td[2]");
    By xmlArchivoIncorrecto = By.xpath("//span[@class='ui-messages-error-summary']");
  								
  
 // Variables que reciben un Parametro modulo de Retiros de dinero - Operacion con Proveedor
    String SrtOperadorProv = ("//*[@id=\"form1:proveedor_panel\"]/div/ul/li[5][contains(text(),'%s')]"); 
  
    /**
	 * Constructor
	 ******************************************************************************************/
	public GenerarReporteMontos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
		m = new MetodosExtra(u, testCase);
    }
	
	
	
	/**
	 * Metodo: Llena filtros de las opciones de reportes 
	 * @author: Manuel CUen
	 * @param:  fechaInicio: todos estos estos parametros son necesarios llenarlos en su data-provider para realizar la prueba
	 *          fechaFin
	 *          zonaOxxo
	 *          cRPlaza
	 *          cRTienda
	 *          proveedor
	 * @return: ninguno
	 */
	public void clickCapturaFiltros(String fechaInicio, String fechaFin, String zonaOxxo,String cRPlaza, String cRTienda, String proveedor) {
		
		System.out.println(fechaInicio);
		u.highLight(xmlFechaInicial);
		u.click(xmlFechaInicial);
		u.sendKeys(xmlFechaInicial, fechaInicio);
		
		System.out.println(fechaFin);
		u.highLight(xmlFechaFin);
		u.click(xmlFechaFin);
		u.sendKeys(xmlFechaFin, fechaFin);
		u.hardWait(2);
		
		By LocZonaOxxo = By.xpath(String.format(SrtZonaOxxo, zonaOxxo));
		System.out.println(LocZonaOxxo);
		u.click(LocZonaOxxo);
		u.hardWait(2);
		
		By LocCRPlaza = By.xpath(String.format(SrtCRPlaza, cRPlaza));
		System.out.println(LocCRPlaza);
		u.click(LocCRPlaza);
		u.hardWait(2);
		
		By LocCRTienda = By.xpath(String.format(SrtCRTienda, cRTienda));
		System.out.println(LocCRTienda);
		u.click(LocCRTienda);
		u.hardWait(2);
		
		By LocProveedor = By.xpath(String.format(SrtProveedor, proveedor));
		System.out.println(LocProveedor);
		u.click(LocProveedor);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickCapturaFiltros");
		
	}

	/**
	 * Metodo:  Da Click en el Menu Portal de Remesas 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickSeleccionarPortalRemesas() {
		
		u.scrollToElement(xmlOpPortalRemesas);
		u.highLight(xmlOpPortalRemesas);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("clickSeleccionarPortalRemesas");
		u.click(xmlOpPortalRemesas);
		u.hardWait(4);
		u.getDriver().navigate().refresh();
		u.waitForLoadPage();
		u.hardWait(4);
		testCase.addScreenShotCurrentStep("clickSeleccionarPortalRemesas");
	}
	
	/**
	 * Metodo:  Da Click en el Modulo de Envios de Dinero - Reportes 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickSeleccionarEnviosDinero() {
		
		u.highLight(xmlMenuEnviosDinero);
		u.mouseOver(xmlMenuEnviosDinero);
		u.hardWait(2);
		
		u.highLight(xmlOpReportes);
		u.mouseOver(xmlOpReportes);

	}
	
	/**
	 * Metodo:  Da Click en el Modulo de 'Retiro de Dinero'
	 * @author: Andrea Contreras
	 * @param:  ninguno
	 * @return: ninguno
	 *  */
	public void clickRetiroDinero() {
		
		u.highLight(xmlMenuRetirosDinero);
		testCase.addScreenShotCurrentStep(u, "highlightRetiroDinero");	
		u.mouseOver(xmlMenuRetirosDinero);
		u.hardWait(2);
		//testCase.addScreenShotCurrentStep(u, "clickRetiroDinero");	

	}
	
	/**
	 * Metodo:  Da Click en el Modulo de Retiros de Dinero - Reportes 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 *  */
	public void clickSeleccionarRetirosDinero() {
		u.hardWait(2);
		u.highLight(xmlMenuRetirosDinero);
		
		u.mouseOver(xmlMenuRetirosDinero);
		u.hardWait(2);
		
		u.highLight(xmlOpRetReportes);
		u.mouseOver(xmlOpRetReportes);

	}
	
	/**
	 * Metodo:  Da Click en la Opcion Montos 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickOpcionMontos() {
		
		u.highLight(xmlOpMontos);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpcionMontos");
		u.mouseOverAndClick(xmlOpMontos);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpcionMontos");
		
	}
	
	/**
	 * Metodo:  Da Click en la Opcion Montos del modulo retiros de dinero
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickOpMontosRetiros() {
		
		u.highLight(xmlOpMontosRetiros);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpMontosRetiros");
		u.mouseOverAndClick(xmlOpMontosRetiros);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpMontosRetiros");
		
		
	}
	
	/**
	 * Metodo:  Da Click en la Opcion Tiempos Promedios del modulo retiros de dinero
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickOpTiemposPromedios() {
		
		u.highLight(xmlOpTiemposPromedios);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpMontosRetiros");
		u.mouseOverAndClick(xmlOpTiemposPromedios);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpMontosRetiros");
		
		
	}
	
	/**
	 * Metodo:  Da Click en la casilla Exportar a Excel y lo Descargue al Momento de dar click en Generar Reporte 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickBotonExportar() {
		
		u.highLight(xmlChkEnExcel);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickBotonExportar");
		u.click(xmlChkEnExcel);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickBotonExportar");	
		u.unhighLight(xmlChkEnExcel);
	}
	
	/**
	 * Metodo: Da Click en el Boton Generar Reporte 
	 * @author: Manuel Cuen
	 * @param:  tipoReporte: Se llena este campo en su data-provider, por lo general debe estar en "Pantalla" y cuando selecciona la opcion
	 *          Exportar a Excel, se le pone Excel
	 * @return: ninguno
	 */
	public void clickBtnGenerarReporte(String tipoReporte, Boolean band) {

	   	u.hardWait(2);
	   	u.highLight(xmlBtnGenerarExcel);
	   	u.hardWait(2);
	   	testCase.addScreenShotCurrentStep(u, "clickBtnGenerarReporte");
	  	u.mouseOverAndClick(xmlBtnGenerarExcel);

		if(band == true) {
			Alert alert = u.getDriver().switchTo().alert();
			alert.accept();
		}
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickBtnGenerarReporte");
	}
	
	/**
	 * Metodo:  Da click en el radio boton exportar en pantalla 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickBotonExportarPant() {
		
		u.highLight(xmlChkEnPantalla);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickBotonExportarPant");
		u.click(xmlChkEnPantalla);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickBotonExportarPant");
		u.unhighLight(xmlChkEnPantalla);	
		
	}

	/**
	 * Metodo: Resalta las Etiquetas del Reporte, una vez Generado 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickReporteMontos() {
		 
		u.hardWait(2);
		u.highLight(xmlLblFecha);
		u.highLight(xmlLblZona);
		u.highLight(xmlLblPlaza);
		u.highLight(xmlLblTienda);
		u.highLight(xmlLblProveedorRep);
		u.highLight(xmlLblMonto);
	
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickReporteMontos");
		
	}
	
	/**
	 * Metodo: Recorre hasta el textbox buscar en las 4 opciones de reporte, dependiendo el caso 
	 * @author: Manuel Cuen
	 * @param:  buscar.- palabra o frase a buscar dentro del reporte que se genera
	 * 			bandera.- Controla las 4 opciones para recorrer depende el caso
	 * @return: ninguno
	 */
	public void clickBusquedaDinamica(String buscar, Boolean bandera) {
		
		
		if(bandera == true) {
			u.highLight(xmlTxtBuscar);
			u.scrollToElement(xmlTxtBuscar);
			
			u.hardWait(5);
			u.click(xmlTxtBuscar);
			u.sendKeys(xmlTxtBuscar, buscar);
		} else if(bandera == false) {
			
			u.highLight(xmlTxtBuscarAnalisis);
			u.scrollToElement(xmlTxtBuscarAnalisis);
		
			u.click(xmlTxtBuscarAnalisis);
			u.sendKeys(xmlTxtBuscarAnalisis, buscar);
		}
		
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickBusquedaDinamica");
	}
	
	/**
	 * Metodo: Recorre hasta el textbox buscar 
	 * @author: Manuel Cuen
	 * @param:  bandera.- Controla las opciones para recorrer al textbox buscar
	 * @return: ninguno
	 */
	public void clickPosicionaEnBuscar(Boolean bandera) {
		
		if(bandera == true) {
			u.highLight(xmlTxtBuscar);
			u.scrollToElement(xmlTxtBuscar);
		} else if(bandera == false) {
			u.highLight(xmlTxtBuscarAnalisis);
			u.scrollToElement(xmlTxtBuscarAnalisis);
		}
	}
	
	/**
	 * Metodo: Posiciona y limpia el textbox buscar 
	 * @author: Manuel Cuen
	 * @param:  bandera.- Controla las opciones para limpiar al textbox buscar
	 * @return: ninguno
	 */
	public void clickLimpiarBuscar(Boolean bandera) {
		
		if(bandera == true) {
			u.highLight(xmlTxtBuscar);
			u.scrollToElement(xmlTxtBuscar);
			u.getDriver().findElement(xmlTxtBuscar).clear();
		} else if(bandera == false) {
			u.highLight(xmlTxtBuscarAnalisis);
			u.scrollToElement(xmlTxtBuscarAnalisis);
			u.getDriver().findElement(xmlTxtBuscarAnalisis).clear();
		}
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickLimpiarBuscar");
	}
	
	/**
	 * Metodo: capturar una imagen en General 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickAgregarImagen() {
		testCase.addScreenShotCurrentStep(u, "clickAgregarImagen");
		u.hardWait(2);
	}

	/**
	 * Metodo: Cambia el Valor Mostrado, en el select "Mostrando Resultados" 
	 * @author: Manuel Cuen
	 * @param:  numRegistros: Se captura en su data-provider el numero de registros qu desea que se muestren
	 * @return: validacion: regresa verdadero si el numero de registros mostrados en etiqueta son iguales al data provider
	 */
	public Boolean clickMostrarRegistros(String numRegistros) throws IOException {
		Boolean validacion = false;
		
		u.hardWait(3);
		u.scrollToElement(xmlTxtMostrarNumPag);
		u.highLight(xmlTxtMostrarNumPag);
		clickAgregarImagen();
		u.click(xmlTxtMostrarNumPag);
		
		By LocNumRegistros = By.xpath(String.format(SrtNumRegistros, numRegistros));
		clickAgregarImagen();
		u.click(LocNumRegistros);		
		
		  u.hardWait(5);
//		  u.scrollToElement(xmlTotalFilas);
		  u.highLight(xmlTotalFilas);
		  testCase.addScreenShotCurrentStep(u, "MuestraRegistros");
		  String filas = u.getText(xmlTotalFilas);
		  filas  =  filas.substring(15, 17);
		  filas = filas.trim();
		  int reng = Integer.parseInt(filas);
		  int numRegis = Integer.parseInt(numRegistros);
		  
		  System.out.println("Variable reng " + reng);
		  System.out.println("Variable numRegis " + numRegis);
		  
		  if(numRegis == reng) {
			  System.out.println("Validacion: Son Iguales Data-Provider y Etiqueta Muestra Resultados");
			  validacion = true;
		  }
		  u.hardWait(2);
		  u.unhighLight(xmlTotalFilas);
		  return validacion;  
		
	}
	
	/**
	 * Metodo: Resalta Mensaje de Error Enviado por sistema, solicitadas en algunos casos de prueba 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickEnviaMensajeError() {
		
		u.highLight(xmlMsjError);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickEnviaMensajeError");
		u.hardWait(2);
		u.unhighLight(xmlMsjError);
	}
	
	/**
	 * Metodo: Da Click en la Opcion Rutas 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickOpcionRutas() {
		
		u.highLight(xmlOpRutas);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickReporteRutas");
		u.mouseOverAndClick(xmlOpRutas);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickReporteRutas");
		
	}
	
	/**
	 * Metodo: Da Click en la Opcion Analisis por dias 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickOpAnalisisXDias() {
		
		u.highLight(xmlOpAnalisisXDias);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpAnalisisXDias");
		u.mouseOverAndClick(xmlOpAnalisisXDias);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpAnalisisXDias");
		
	}
	
	/**
	 * Metodtodo: Da Click en la Opcion Analisis por dias del menu retiro de dinero
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickOpAnalisisXDiasRet() {
		
		u.highLight(xmlOpAnalisisXDiasRet);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpAnalisisXDiasRet");
		u.mouseOverAndClick(xmlOpAnalisisXDiasRet);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpAnalisisXDiasRet");
		
	}
	
	
	/**
	 * Metodo: Da click en la casilla dia de la semana, que desea activar 
	 * @author: Manuel Cuen
	 * @param:  diaSemana: Es necesario llenar en el dat provider, para seleccionar un dia en especifico o todos
	 * @return: ninguno
	 */
	public void clickDiaSemana(String diaSemana) {
		
		By LocDiaSemana = By.xpath(String.format(SrtDiaSemana, diaSemana));
		System.out.println(LocDiaSemana);
		u.highLight(LocDiaSemana);
		u.click(LocDiaSemana);		
		u.hardWait(2);
		
	}
	
	/**
	 * Metodo: Resalta las etiquetas Generadas en el reporte, de la opcion Analisis por Dias 
	 * @author: Manuel Cuen
	 * @param:  mostrar: Es necesario llenar con la palabra "Monto", para resaltar etiquetas, en ciertos casos de prueba
	 * @return: ninguno
	 */
	public void clickReporteAnalisisXDia(String mostrar) {
		
//		u.highLight(xmlTxtBuscarAnalisis);
//		u.scrollToElement(xmlTxtBuscarAnalisis);
		
		u.hardWait(5);
		u.highLight(xmlLblZonaAXD);
		u.highLight(xmlLblPlazaAXD);
		u.highLight(xmlLblTiendaAXD);
		u.highLight(xmlLblProveedorRepAXD);
		u.highLight(xmlAcumuladoTrans);
		u.highLight(xmlDiaLunes);
		u.highLight(xmlDiaMartes);
		u.highLight(xmlDiaMiercoles);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickReporteAnalisisXDia");
		u.highLight(xmlDiaJueves);
		u.highLight(xmlDiaViernes);
		u.highLight(xmlDiaSabado);
		u.highLight(xmlDiaDomingo);
		u.scrollToElement(xmlDiaDomingo);
//		u.highLight(xmlSumatoria);

		if(mostrar.toUpperCase().equals("MONTO")) {
			
			u.scrollToElement(xmlSumaMonto);
			u.highLight(xmlSumaMonto);

		} else {
			u.scrollToElement(xmlSumatoria);
			u.highLight(xmlSumatoria);
		}
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickReporteAnalisisXDia");
	}
	
	/**
	 * Metodo: Resalta las etiquetas Generadas en el reporte, de la opcion Analisis por Horas 
	 * @author: Manuel Cuen
	 * @param:  mostrar: Es necesario llenar con la palabra "Monto", controlar opciones y esaltar etiquetas, en ciertos casos de prueba
	 * @return: ninguno
	 */
	public void clickReporteAnalisisXHoras(String mostrar) {
		
//		u.highLight(xmlTxtBuscarAnalisis);
//		u.scrollToElement(xmlTxtBuscarAnalisis);
		
		u.hardWait(5);
		u.highLight(xmlLblZonaAXH);
		u.highLight(xmlLblPlazaAXH);
		u.highLight(xmlLblTiendaAXH);
		u.highLight(xmlLblProveedorRepAXH);
		u.highLight(xmlAcumuladoTransAXH);
		u.highLight(xml0800);
		u.highLight(xml0900);
		u.highLight(xml1000);
		u.highLight(xml1100);
		u.highLight(xml1200);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickReporteAnalisisXHoras");
		u.highLight(xml1300);
		u.highLight(xml1400);
		u.highLight(xml1500);
		u.highLight(xml1600);
		u.highLight(xml1700);
		//u.highLight(xml1800);  //Se lo quite porque con los filtros zona oxxo, plaza y proveedor diferente de todos lo elimina del reporte
		if(mostrar.toUpperCase().equals("MONTO")) {
			u.scrollToElement(xmlSumatoriaMonto);
			u.highLight(xmlSumatoriaMonto);
		}else {
			u.scrollToElement(xmlSumatoriaTrans);
			u.highLight(xmlSumatoriaTrans);
		}

		
		testCase.addScreenShotCurrentStep(u, "clickReporteAnalisisXHoras");
		
	}
	
	/**
	 * Metodo: Dar click y Seleccionar un Proveedor deseado, Mediante un parametro 
	 * @author: Manuel Cuen
	 * @param:  proveedorDos: Es necesario llenarlo en su data-provider, para realizar prueba con un segundo proveedor
	 * @return: ninguno
	 */
	public void SeleccionarOtroProveedor(String proveedorDos) {
		
		By LocProveedorDos = By.xpath(String.format(SrtProveedor, proveedorDos));
		System.out.println(LocProveedorDos);
		u.click(LocProveedorDos);
		u.hardWait(2);
	}
	
	/**
	 * MÃ©todo: Resalta la Etiqueta de "Monto Acumulado", en algunos casos de prueba solicitados 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickMontoAcumulado() {
	
//		u.highLight(xmlTransAcumuladas);
		u.click(xmlTransAcumuladas);
		
		u.highLight(xmlMontoAcumulado);
		u.click(xmlMontoAcumulado);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickMontoAcumulado");
	}
	
	/**
	 * Metodo: Da Click en la Opcion Analisis por Horas 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickOpAnalisisXHoras() {
		
		u.highLight(xmlOpAnalisisXHoras);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpAnalisisXHoras");
		u.mouseOverAndClick(xmlOpAnalisisXHoras);
		
		Alert alert = u.getDriver().switchTo().alert();
		alert.accept();
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpAnalisisXHoras");
		
	}
	
	/**
	 * Metodotodo: Da Click en la Opcion Analisis por Horas del Modulo Retiro de Dinero 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickOpAnalisisXHraRet() {
		
		u.highLight(xmlOpAnalisisXHraRet);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpAnalisisXHraRet");
		u.mouseOverAndClick(xmlOpAnalisisXHraRet);
		
		Alert alert = u.getDriver().switchTo().alert();
		alert.accept();
		//u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpAnalisisXHoras");
		
	}
	
	/**
	 * Metodotodo: Selecciona la Hora y Minuto de Inicio, Hora y minuto final de la hora, en analisis por Horas del Submenu Retiro de Dinero 
	 * @author: Manuel Cuen
	 * @param:  horaDe:		estos 4 parametros son necesario llenar en el data provider, para su seleccion en el calendario de hora
	 * 			minutoDe:
	 * 			horaHasta:
	 * 			minutoHasta:
	 * @return: ninguno
	 */
	public void clickSeleccionHorario(String horaDe, String minutoDe, String horaHasta, String minutoHasta) {
		
		u.highLight(xmlHoraDe);
		u.click(xmlHoraDe);
		
		u.hardWait(2);
		By LocHraDeCal = By.xpath(String.format(SrtHoraDeCal, horaDe));
		System.out.println(LocHraDeCal);
		u.click(LocHraDeCal);
		
		u.hardWait(2);
		By LocMinutoDeCal = By.xpath(String.format(SrtMinutoDeCal, minutoDe));
		System.out.println(LocMinutoDeCal);
		u.click(LocMinutoDeCal);
		
		u.highLight(xmlHoraHasta);
		u.click(xmlHoraHasta);
		
		u.hardWait(2);
		By LocHraHastaCal = By.xpath(String.format(SrtHoraHastaCal, horaHasta));
		System.out.println(LocHraHastaCal);
		u.click(LocHraHastaCal);
		
		u.hardWait(2);
		By LocMinutoHastaCal = By.xpath(String.format(SrtMinutoHastaCal, minutoHasta));
		System.out.println(LocMinutoHastaCal);
		u.click(LocMinutoHastaCal);
		
		testCase.addScreenShotCurrentStep(u, "clickSeleccionHorario"); 
		
	}
	
	/**
	 * Metodo:  Dar click en el menu Configurcion-Envio y Retiro 
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMenuConfiguracion() {
		
		u.highLight(xmlMenuConfiguracion);
		testCase.addScreenShotCurrentStep("opcionConfiguracion");
		u.mouseOver(xmlMenuConfiguracion);	
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("clickConfiguracion");


	}
	

	
	
	/**
	 * Metodo: Dar click en el menu Configurcion-Envio y Retiro 
	 * @author: Manuel Cuen
	 * @param: ninguno
	 * @return: ninguno
	 */
	public void clickOpAtributoCompartido() {
		
		u.highLight(xmlAtributosCompart);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpAtributoCompartido");
		u.mouseOverAndClick(xmlAtributosCompart);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpAtributoCompartido");
		
	}
	
	/**
	 * Metodo:  Da Click en el Modulo de Configurcion-Envio y Retiro - Lista de Proveedores
	 * @author: Andrea Contreras
	 * @param:  ninguno
	 * @return: ninguno
	 *  */
	public void posicionarseOpListaProvedores() {

		u.hardWait(2);
		u.highLight(xmlListaProv);	
		testCase.addScreenShotCurrentStep(u, "clickOpAtributoCompartido");
		u.unhighLight(xmlListaProv);

	}
	
	/**
	 * Metodo:  Da Click en el Modulo de Configurcion-Envio y Retiro - Lista de Proveedores
	 * @author: Andrea Contreras
	 * @param:  ninguno
	 * @return: ninguno
	 *  */
	public void clickOpListaProvedores() {

		u.hardWait(2);
		u.highLight(xmlListaProv);	
		testCase.addScreenShotCurrentStep(u, "clickOpAtributoCompartido");
		u.mouseOverAndClick(xmlListaProv);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickOpAtributoCompartido");

	}
	
	/**
	 * Metodo:  Visualizar la lista de remesadores
	 * @author: David Valdez
	 * @param:  ninguno
	 * @return: true o false si hay remesadores en la lista
	 *  */
	public boolean visualizaRemesadores() {
				
		//u.click(xmlProveedorDesplegar);
		//u.highLight(xmlProveedorDesplegar);
		
		boolean validacion = false;
		List<WebElement> allOptions = u.getDriver().findElements(xmlListaRemesadoresAP);
		
		if(allOptions.size()>1) {
			validacion = true;
		}
		
		//u.highLight(xmlMostrarProvedoor);
		testCase.addScreenShotCurrentStep(u, "listaRemesadores");
		u.click(xmlProveedorDesplegar);
		
		return validacion;
	}
	
	
	/**
	 * Metodo:  Visualizar la lista de remesadores indicados
	 * @author: Andrea Contreras
	 * @param:  remesadores a validar
	 * @return: true o false si se encuentran los remesadores indicados
	 *  */
	public boolean visualizaRemesadores(String... remesador) {
				
		boolean validacion = false;	
		int count = 0;
		
		//System.out.print(remesador.length);
		for(int i = 0; i < remesador.length; i++) {			
			List<WebElement> allOptions = u.getDriver().findElements(xmlListaRemesadoresAP);
			for (WebElement option : allOptions) {
				if(option.getText().equals(remesador[i])) {
					//System.out.print(option.getText());				
					//me.highlight(option);					
					count++;
				}
			}			
		}
		
		if(count==remesador.length) {
			validacion = true;
		}
		
		testCase.addScreenShotCurrentStep("visualizarRemesadores2");
		u.click(xmlProveedorDesplegar);
		
		return validacion;
	}
	
	/**
	 * Metodo:  Dar click en la opcion de Configurcion-Envio y Retiro 
	 * @author: Manuel Cuen & David Valdez
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickEnvioDinero() {
		
		u.hardWait(2);
		u.highLight(xmlLblEnvioDinero);	
		testCase.addScreenShotCurrentStep(u, "envioDinero");
		u.mouseOver(xmlLblEnvioDinero); // Se modifico el click por mouseOver debido a que no funcionaba con click
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickEnvioDinero");
		
	}
	
	
	/**
	 * Metodo:  Dar click en la opcion de Configurcion-Envio y Retiro 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void clickAtributosProveedor() {
		
		u.hardWait(2);
		u.highLight(xmlLbAtributosProv);	
		testCase.addScreenShotCurrentStep("AtributosProv");
		u.click(xmlLbAtributosProv);
		u.hardWait(4);
		u.getDriver().navigate().refresh();
		u.waitForLoadPage();
		u.hardWait(4);
		testCase.addScreenShotCurrentStep("clickAtributosProv");
		u.hardWait(2);
	}
	
	/**
	 * Metodo:  Modificar los valores dentro de la opcion configuracion- 
	 * @author: Manuel Cuen
	 * @param: ninguno
	 * @return: ninguno
	 */
	public void clickModificaValores(String valorAtributo) {
		
		u.scrollToElement(xmlValorAtrib);
		u.highLight(xmlValorAtrib);
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickModificaValores");
		u.click(xmlValorAtrib);
		
		u.hardWait(2);
		u.highLight(xmlBtnModif);
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickModificaValores");
		u.click(xmlBtnModif);
		
		u.hardWait(2);
		u.highLight(xmlValorAtributo);
		u.getDriver().findElement(xmlValorAtributo).clear();
		u.sendKeys(xmlValorAtributo, valorAtributo);
		
		u.hardWait(2);
		u.highLight(xmlBtnActualizar);
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickModificaValores");
		u.click(xmlBtnActualizar);
		
		u.highLight(xmlMensajeActual);
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickModificaValores");
		
		u.scrollToElement(xmlValorAtrib);
		u.highLight(xmlValorAtrib);
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickModificaValores");
	}
	
	/**
	 * Metodo:  Valida si se Descargo Algun archivo, por medio de una ruta de descarga 
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public Boolean validarDescarga() {
		Boolean exist = false;
		
		String rutaArchivo = System.getProperty("user.home") + "\\Downloads\\reporteDias.xlsx";
		System.out.println(rutaArchivo);
		File f = new File(rutaArchivo);
		
		if (f.exists()) {
			exist = true;
		}
		
		return exist;
	}
	
	/**
	 * Metodo:  Abrir Archivo excel NOTA: mientras se utilice este metodo no se debe
	 * abrir ninguna otra ventana ya que se interpone y Sikuli no encuentra los elementos.
	 * @return correct: true si encontro el elemento o false en caso contrario
	 * @throws FindFailed
	 * @author: Manuel Cuen
	 */
	public Boolean abrirExcelEdge(String imagenArchivo, String cerrarVent) throws FindFailed{
		u.hardWait(5);
		u.waitForLoadPage();
		
		testCase.addScreenShotCurrentStep("Open");
		Boolean correct = me.clickElementSikuli("EDI", imagenArchivo); //Da click en boton Open de la ventana de descarga
//		Boolean ct = me.clickElementSikuli("EDI", cerrarVent);
		u.waitForLoadPage();

		u.hardWait(5);
		testCase.addScreenShotCurrentStep("capturaExcel");
		
		return correct;
	}
	
	/**
	 * Metodo:  Cerrar mensaje de descarga archivo excel NOTA: mientras se utilice este metodo no se debe
	 * abrir ninguna otra ventana ya que se interpone y Sikuli no encuentra los elementos.
	 * @return correct: true si encontro el elemento o false en caso contrario
	 * @throws FindFailed
	 * @author: Manuel Cuen
	 */
	public Boolean cerrarExcelEdge(String imagenArchivo, String cerrarVent) throws FindFailed{
		//reporteMontos
		u.waitForLoadPage();
		
		testCase.addScreenShotCurrentStep("Open");
		Boolean correct = me.clickElementSikuli("EDI", cerrarVent); //Da click en boton Cerrar de la ventana de descarga
		u.waitForLoadPage();
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("capturaExcel");
		return correct;
	}
	
	/**
	 * Metodo: Validar los datos de un archivo de excel
	 * @author: Manuel Cuen
	 * @param:  OpcionReporte: Se agrega en el data provider para controla la opcion ejemplo "Montos"
	 * 			filtro: Se debe capturar el numero de columna, por el cual se debe filtrar, de acuerdo al archivo de excel
	 * 			strFiltro: Se llena automaticamente con valores del dp, en cada script
	 * 			filtroProv: se debe capturar en el data provider, por el cual se debe filtrar, cuando se validen 2 columnas
	 * @return: validacion: regresa verdadero si el contador de registros es igual al numero total de registros en el archivo generado por reporte
	 */
  public Boolean validarDatos(String r, String tipoPedido, String OpcionReporte, int filtro, String strFiltro, int descarga, int filtroProv) throws IOException {
		Boolean validacion = false;
		String pathYourSystem = "";

		if(OpcionReporte.toUpperCase().equals("MONTOS")) {
			pathYourSystem = System.getProperty("user.home") + "\\Downloads\\reporteMontos.xlsx";
		} else if(OpcionReporte.toUpperCase().equals("ANALISISDIA")) {
			if(descarga == 0) {
				pathYourSystem = System.getProperty("user.home") + "\\Downloads\\reporteDias.xlsx";
				System.out.println("Entro y tomo archivo 1 ");
			} else if(descarga == 1) {
				pathYourSystem = System.getProperty("user.home") + "\\Downloads\\reporteDias (1).xlsx";
				System.out.println("Entro y tomo archivo 2 ");
			}		
			if(filtroProv == 3 && descarga == 0) {
				OpcionReporte = "MONTOS";
			}
			
			if(descarga == 1) {
				strFiltro  = "MGI";
			}
		}else if(OpcionReporte.toUpperCase().equals("ANALISISHORA")) {
			if(descarga == 0) {
				pathYourSystem = System.getProperty("user.home") + "\\Downloads\\reporteHoras.xlsx";
				System.out.println("Entro y tomo archivo 1 Analisis Por Hora");
			} else if(descarga == 1) {
				pathYourSystem = System.getProperty("user.home") + "\\Downloads\\reporteHoras (1).xlsx";
				System.out.println("Entro y tomo archivo 2 Analisis por Hora (1)");
			}
			if(filtroProv == 3 && descarga == 0) {
				OpcionReporte = "MONTOS";
			}
			
		}else if(OpcionReporte.toUpperCase().equals("RUTAS")) {
			pathYourSystem = System.getProperty("user.home") + "\\Downloads\\reporteRutas.xlsx";  // checar bien nombre de archivo, cuando se pueda generar
		}else if(OpcionReporte.toUpperCase().equals("MONTOSTODOS")) {
			pathYourSystem = System.getProperty("user.home") + "\\Downloads\\reporteMontos.xlsx";
		} else if(OpcionReporte.toUpperCase().equals("TIEMPOS")) {
			pathYourSystem = System.getProperty("user.home") + "\\Downloads\\reporteTiempoPromedio.xlsx";
			OpcionReporte = "MONTOSTODOS";
			System.out.println("Entrada 1 ");
		}
		
		File fichero = new File(pathYourSystem);
	    FileInputStream inputStream = new FileInputStream(new File(pathYourSystem));
	    Workbook workbook = new XSSFWorkbook(inputStream);
	    Sheet firstSheet = workbook.getSheetAt(0);
//	    ExcelUtil e = new ExcelUtil(pathYourSystem);
	      
		int ng = firstSheet.getLastRowNum();
		System.out.println("Valor de ng: "+ng);
	    int cont = 0;
	    int contodos = 0;
	    for (int i = 1; i <= ng; i++) {
	    	
	    	// Observaciones: agregar condicion para la opcion de rutas, pero hasta que funcione el reporte ya que marca error, se desconoce el funcionamiento del mismo
	    	
	    	if(OpcionReporte.toUpperCase().equals("ANALISISDIA") || OpcionReporte.toUpperCase().equals("ANALISISHORA")) {
	    		String str =  datoCelda(firstSheet,  i,  filtro); // parametros objeto Hoja, fila, columna(Proveedor)
	    		// se valida que la primer fila y columna cero, tengan valor
	    		System.out.println("Valor celda: "+ i + " " + str);
	    		System.out.println("Valor DP: "+ i + " " + strFiltro);
	    		if(filtro == 0 || filtro == 1 || filtro == 2) {
	    			if(str.toUpperCase().equals(strFiltro.toUpperCase())) {
	    				cont++;
	    			} else if(str.length() > 0 ) {
		    			cont++;
		    		}
	    		} 

	    		if(str.toUpperCase().equals(strFiltro) && (filtro == filtroProv)){ // esta condicion es para validar el caso 52, 53 y 70 
	    			System.out.println("Entro a contar los de Oxxo ");
	    			cont++;
	    		}
	    		
	    		if(descarga == 1 && str.length() > 0) {          // para cuando tenga que validar todos los proveedores en un segundo archivo
	    			contodos++;
	    			
	    		}
	    		
	    	} else if(OpcionReporte.toUpperCase().equals("MONTOS")) {
	    		String str =  datoCelda(firstSheet,  i,  filtroProv); // parametros objeto Hoja, fila, columna(Proveedor)
	    		String strPlaza =  datoCelda(firstSheet,  i,  filtro);
	    		System.out.println("Valor celda Excel: "+ i + " " + str);
	    		System.out.println("Valor celda DP: "+ i + " " + strPlaza);
	    		System.out.println("Valor strFiltro: "+ i + " " + strFiltro);
	    		if(str.toUpperCase().equals("WU")) {
	    			if(strPlaza.toUpperCase().equals(strFiltro.toUpperCase())) {
	    				cont++;
	    			}
	    		}
	    		
	    		if(str.toUpperCase().equals("OXXO") || str.toUpperCase().equals("MGI") || str.toUpperCase().equals("TNC")) {
	    			if(strPlaza.toUpperCase().equals(strFiltro.toUpperCase())) {
	    				cont++;
	    			}
	    		}
	    		
	    	}  else if(OpcionReporte.toUpperCase().equals("MONTOSTODOS")) {
	    		System.out.println("Entrada 2 ");
	    		
	    		String str =  datoCelda(firstSheet,  i,  filtro);
	    		System.out.println("Valor del archivo descargado: "+str);
	    		// se valida que la primer fila y columna cero, tengan valor
	    		if(str.length() > 0 ) {
	    			cont++;
	    		}
	    	} 

	    	if(cont == ng || contodos == ng) {
	    		validacion = true;
	    	}
		}
	    
	    System.out.println("Valor de Cont: "+cont);
	    System.out.println("Valor de Contodos: "+contodos);
	    return validacion;
	 
  }
  
	/**
	 * Metodo: Posicionarse en una celda y poder extraer su contenido
	 * @author: Manuel Cuen
	 * @param:  sheet: controla el numero de hojas en el libro excel
	 * 			r:		controla el numero de renglones dentro de la hoja Excel
	 * 			c:		controla el numero de columnas dentro de la hoja Excel
	 * @return: ninguno
	 */
  public String datoCelda(Sheet sheet, int r, int c) {
      DataFormatter formatter = new DataFormatter();
      
      Cell celda = sheet.getRow(r).getCell(c);
      String contenidoCelda = formatter.formatCellValue(celda);
      //System.out.println("celda: -" + contenidoCelda + "-");
      
      return contenidoCelda;
  }
  
	/**
	 * Metodo:  captura texto que se desea buscar dentro del reporte generado y compara los registros que se muestran 
	 * @param:  sheet: controla el numero de hojas en el libro excel
	 * 			r:		controla el numero de renglones dentro de la hoja Excel
	 * 			c:		controla el numero de columnas dentro de la hoja Excel
	 * @return: validacion: regresa verdadero si los registros son diferentes, cuando se muestran los datos
	 * @author: Manuel Cuen
	 */
  public Boolean MuestraRegistros(String buscar, Boolean bandera) throws IOException {
	  Boolean validacion = false;
	  
		  u.scrollToElement(xmlTotalFilas);
		  u.highLight(xmlTotalFilas);
		  testCase.addScreenShotCurrentStep(u, "MuestraRegistros");
		  String filas = u.getText(xmlTotalFilas);
		  filas  =  filas.substring(20, 23);
		  for(int i = 0; i <= filas.length(); i++) {
			  if(i + 1 <= filas.length()) {
				  String digito = filas.substring(i, i + 1);
				  if(digito.toUpperCase().equals("R")) {
					  filas  =  filas.substring(0, 2); 
				  } else {
					  filas  =  filas.substring(0, 3);
				  }
			  }
		  }
		  filas = filas.trim();
		  int reng = Integer.parseInt(filas);

			u.hardWait(5);
			clickBusquedaDinamica(buscar, bandera);

		  u.hardWait(5);
		  u.scrollToElement(xmlTotalFilas);
		  u.highLight(xmlTotalFilas);
		  testCase.addScreenShotCurrentStep(u, "MuestraRegistros");
		  String filasDos = u.getText(xmlTotalFilas);
		  filasDos  =  filasDos.substring(20, 23);
		  for(int i = 0; i <= filasDos.length(); i++) {
			  if(i + 1 <= filasDos.length()) {
				  String digito = filasDos.substring(i, i + 1);
				  if(digito.toUpperCase().equals("R")) {
					  filasDos  =  filasDos.substring(0, 2); 
				  } else {
					  filasDos  =  filasDos.substring(0, 3);
				  }
			  }
		  }
		  filasDos = filasDos.trim();
		  int NumReg = Integer.parseInt(filasDos);

//	  u.unhighLight(xmlTotalFilas);
	  if(reng != NumReg) {
		  validacion = true;
	  }
	  return validacion;
  }

	/**
	 * Metodo:  extrae el numero de registros mostrados, para compararlos con el numero de registro total, del archivo generado, con el boton "generar reporte" 
	 * @param:  tipoReporte: Se utiliza para ejecutar metodo dentro 
	 * 			bandera:		
	 * @return: validacion: regresa verdadero si los registros son diferentes, cuando se muestran los datos, significa que efectivamente cambiaron los datos como se esperaba
	 * @author: Manuel Cuen
	 */
  public Boolean MuestraRegistrosDos(String tipoReporte,Boolean bandera) throws IOException {
	  Boolean validacion = false;
	  u.unhighLight(xmlTotalFilas);
	  	  
	  u.hardWait(5);
	  u.scrollToElement(xmlTotalFilas);
	  u.highLight(xmlTotalFilas);
	  testCase.addScreenShotCurrentStep(u, "MuestraRegistros");
	  String filas = u.getText(xmlTotalFilas);
	  filas  =  filas.substring(20, 23);
	  for(int i = 0; i <= filas.length(); i++) {
		  if(i + 1 <= filas.length()) {
			  String digito = filas.substring(i, i + 1);
			  if(digito.toUpperCase().equals("R")) {
				  filas  =  filas.substring(0, 2); 
			  } else {
				  filas  =  filas.substring(0, 3);
			  }
		  }
	  }
	  filas = filas.trim();
	  int reng = Integer.parseInt(filas);
	  
	  clickBtnGenerarReporte(tipoReporte,bandera);
	  
	  u.hardWait(5);
	  u.scrollToElement(xmlTotalFilas);
	  u.highLight(xmlTotalFilas);
	  testCase.addScreenShotCurrentStep(u, "MuestraRegistros");
	  String filasDos = u.getText(xmlTotalFilas);
	  filasDos  =  filasDos.substring(20, 23);
	  for(int j = 0; j <= filasDos.length(); j++) {
		  if(j +1 <= filasDos.length()) {
			  String digito = filasDos.substring(j, j + 1);
			  if(digito.toUpperCase().equals("R")) {
				  filasDos  =  filasDos.substring(0, 2); 
			  } else {
				  filasDos  =  filasDos.substring(0, 3);
			  }
		  }	  

	  }
	  filasDos = filasDos.trim();
	  int NumReg = Integer.parseInt(filasDos);
	  
	  if(reng != NumReg) {

		  validacion = true;
	  }
	  return validacion;
  }
  
	/**
	 * Metodo:  valida si existen los elementos check del mostrar transacciones o monto acumulados y compararlos con etiquetas mostradas en reporte generado 
	 * @param:  xmlChekTransa: Se utiliza verificar si existe en modo ejecucion dentro de la interfaz 
	 * 			xmlChekMonto:  Se utiliza verificar si existe en modo ejecucion dentro de la interfaz
	 * 			mostrar: Se llena en data provider para controlar el fujo de las opciones mostradas de transacciones y montos acumulados
	 * 			opReporte: Se llena en data provider para controlar el fujo de las opciones de los reportes, ejemplo "Montos"
	 * @return: validacion: regresa verdadero si encontro los elementos dentro de la interfaz
	 * @author: Manuel Cuen
	 */
  public Boolean validaMostrarReporte(By xmlChekTransa, By xmlChekMonto, String mostrar, String opReporte) throws IOException{
	  u.hardWait(2);
	  Boolean validacion = false;
	  Boolean exist = false;
//	  u.highLight(xmlMontoAcumulado);
//	  u.click(xmlMontoAcumulado);
//	  Boolean enable = m.isEnable(xmlMontoAcumulado);
//	  if(enable) {
//		  System.out.println("El elemento esta Activado");
//	  } else {
//		  System.out.println("El elemento esta Inactivo");
//	  }
	  
	  
	if(mostrar.toUpperCase().equals("TRANSACCIONES")) {       //Para cuando el el check del elemento transacciones este activado
		  exist = m.existElement(xmlChekTransa);
		  if(exist) {
			  if(opReporte.toUpperCase().equals("ANALISISHORA")) {
				  System.out.println("Entro por Analisis hora");
				  System.out.println(u.getText(xmlMostrarTipoHra));
				  if(mostrar.toUpperCase().equals(u.getText(xmlMostrarTipoHra).toUpperCase())) {
					  validacion = true;
				  }
			  } else if(opReporte.toUpperCase().equals("ANALISISDIA")){
				  System.out.println("Entro por Analisis dia");
				  System.out.println(u.getText(xmlMostrarTipo));
				  if(mostrar.toUpperCase().equals(u.getText(xmlMostrarTipo).toUpperCase())) {
					  validacion = true;
				  }
			  }
		  }
	} else if(mostrar.toUpperCase().equals("MONTO")) {       //Para cuando el el check del elemento monto este activado
		System.out.println("Entro la opcion MONTO");
		exist = m.existElement(xmlChekMonto);
		  if(exist) {
			  if(opReporte.toUpperCase().equals("ANALISISHORA")) {
				  System.out.println("Entro por Analisis hora");
				  System.out.println(u.getText(xmlMostrarmontoHra));
				  if(mostrar.toUpperCase().equals(u.getText(xmlMostrarmontoHra).toUpperCase())) {
					  validacion = true;
				  }
			  }  else if(opReporte.toUpperCase().equals("ANALISISDIA")){
				  System.out.println("Entro por Analisis dia");
				  System.out.println(u.getText(xmlMostrarmonto));
				  if(mostrar.toUpperCase().equals(u.getText(xmlMostrarmonto).toUpperCase())) {
					  validacion = true;
				  }
			  }
		  }
	} 
	return validacion;
  }
  
	/**
	 * Metodo para seleccionar proveedor
	 * @param entidad (String): proveedor a seleccionar
	 * @author Andrea Contreras
	 */
	public boolean seleccionarProveedor(String prov) {
		
		boolean existOption = false;
		me.explicitWaitVisibilityOfElement(xmlProveedor, Duration.ofSeconds(250));
		u.click(xmlProveedor);
		
		List<WebElement> allOptions = u.getDriver().findElements(xmlOpProveedor);
		for (WebElement option : allOptions) {
			if (option.getText().equals(prov)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}
		testCase.addScreenShotCurrentStep("seleccionaProveedor");
		
		return existOption;

	}
	
	/**
	 * Metodo para seleccionar proveedor
	 * @param entidad (String): proveedor a seleccionar
	 * @author David Valdez
	 * @return boolean: false si no se encontro, true si se selecciono el proveedor
	 */
	public boolean seleccionProveedor(String prov) {
		u.waitForLoadPage();
		
		try {
			By provA = By.xpath("//*[.='"+ prov +"']");
			u.highLight(provA);
			u.click(provA);
			testCase.addScreenShotCurrentStep(u, "seleccionaProveedor");
			return true;
		}catch(Exception ex) {
			System.out.println("No se encontro proveedor");
		}
		return false;
	}
	
	/**
	 * Metodo para presionar el boton editar despues de seleccionar un proveedor
	 * @param null
	 * @author David Valdez
	 */
	public void presionaEditar() {
		By buttonEditar = By.xpath("//*[@id=\"form1:btnEditar\"]");
		
		u.hardWait(2);
		u.highLight(buttonEditar);
		testCase.addScreenShotCurrentStep(u, "BotonEditar");
		u.click(buttonEditar);
    
    }

 /**
	 * Metodo:  Da click en el Modulo Retiro Dinero Opcion Operacion con Proveedor
	 * @author: Manuel Cuen
	 * @param:  ninguno
	 * @return: ninguno
	 */
  public void clickOperacionConProveedor() {
	  
		u.hardWait(2);
		u.highLight(xmlMenuRetirosDinero);
		
		u.mouseOver(xmlMenuRetirosDinero);
		u.hardWait(2);
		
		u.highLight(xmlOperacionProveedor);
		testCase.addScreenShotCurrentStep(u, "clickOperacionConProveedor");
		u.hardWait(5);
		u.mouseOverAndClick(xmlOperacionProveedor);
		
  }
  
	/**
	 * Metodo:  Para Seleccionar el proveedor, enlace Registro Manual de Tiendas y Generar Manualmente Cierto Numero de Tiendas en la opcion Operacion con proveedor
	 * @author: Manuel Cuen
	 * @param:  proveedor: es Neceario llenarlo en su data provider, para realizar la prueba
	 * @return: ninguno
	 */
  public void clickSeleccionProveedor(String proveedor) {
	  
	  u.hardWait(2);
	  u.highLight(xmlProveedor);
	  testCase.addScreenShotCurrentStep(u, "clickSeleccionProveedor");
	  u.click(xmlProveedor);
	  
	  By LocOperadorProv = By.xpath(String.format(SrtOperadorProv, proveedor));
	  System.out.println(LocOperadorProv);
	  u.click(LocOperadorProv);
	  testCase.addScreenShotCurrentStep(u, "clickSeleccionProveedor");
	  u.hardWait(2);
		
	  u.highLight(xmlEnlaceManual);
	  testCase.addScreenShotCurrentStep(u, "clickSeleccionProveedor");
	  u.click(xmlEnlaceManual);
		
	  u.highLight(xmlNumeroTiendas);
	  testCase.addScreenShotCurrentStep(u, "clickSeleccionProveedor");
	  u.click(xmlNumeroTiendas); 
	  
	  u.waitForLoadPage();
	  u.hardWait(5);
		  
  }
  
	/**
	 * Metodo:  Para validar si se cargo correctamente el archivo en 'Operacion con proveedor - Registro Manual Tienda'
	 * @author: Andrea Contreras
	 * @param:  filename: nombre del archivo
	 * @return: true o false si se cargo correctamente el archivo
	 */
	public boolean validarCargaArchivo(String filename) {
		
		boolean validacion = false;
		
		if(me.existElement(xmlArchivoCargado)) {
			u.scrollToElement(xmlArchivoCargado);
			u.highLight(xmlArchivoCargado);
			if(u.getText(xmlArchivoCargado).equals(filename)) {
				validacion = true;
			}				
		}
		
		testCase.addScreenShotCurrentStep(u, "validacionCarga");
		return validacion;
		
	}
	
	
	  
		/**
		 * Metodo:  Para validar si se muestra un mensaje de error 'Operacion con proveedor - Registro Manual Tienda'
		 * @author: Andrea Contreras
		 * @param:  mensaje: texto del mensaje de error a validar
		 * @return: true o false si se mostro el mensaje de error
		 */
		public boolean validarMsjError(String error) {
			
			boolean validacion = false;
			
			if(me.existElement(xmlArchivoIncorrecto)) {
				u.scrollToElement(xmlArchivoIncorrecto);
				u.highLight(xmlArchivoIncorrecto);
				if(u.getText(xmlArchivoIncorrecto).equals(error)) {
					validacion = true;
				}				
			}
			
			testCase.addScreenShotCurrentStep(u, "validacionMensaje");
			return validacion;
			
		}
  
	/**
	 * Metodo:  Para dar click en el boton Examinar Operacion con proveedor
	 * @author: Manuel Cuen
	 * @param:  proveedor: es Neceario llenarlo en su data provider, para realizar la prueba
	 * @return: ninguno
	 */
	public void clickBtnExaminar() {
		u.hardWait(2);
		u.unhighLight(xmlBtnExaminar);
		testCase.addScreenShotCurrentStep(u, "clickBtnExaminar");
		u.click(xmlBtnExaminar);
		
	}
	
	/**
	 * Metodo:  Para dar click en la imagenes Descargas y CascaronAltaManual del explorador de archivos, utilizando Sikuli  
	 * @author: Manuel Cuen
	 * @param:  descargas: revisar que se tenga esta imagen en la carpeta descargas
	 * 			cascaron: revisar que se tenga esta imagen en la carpeta descargas
	 * @return: valida: regresa verdadero si encuentra el elemento carga del explorador de archivos yFalso en caso contrario
	 */
	public Boolean clickDescargas(String descargas, String cascaron) throws FindFailed{
	
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep("Click");
		Boolean correct = me.clickElementSikuli("EDI", descargas); //Da click en boton Cerrar de la ventana de descarga
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("Click");
		Boolean valida = me.clickElementSikuli("EDI", cascaron);
		u.waitForLoadPage();
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("clickDescargas");
		return correct;
	}
	
	/**
	 * Metodo:  Para dar click en la imagen open del explorador de archivos, utilizando Sikuli 
	 * @author: Manuel Cuen
	 * @param:  open: revisar que se tenga esta imagen en la carpeta descargas
	 * 			xmlArchivoCargado: elemento que busca para validar si lo encuentra o no
	 * @return: valida: regresa verdadero si encuentra el elemento carga del explorador de archivos yFalso en caso contrario
	 */
	public Boolean clickOpenCascaron(String open, By xmlArchivoCargado) throws FindFailed{
		Boolean valida = false;
		u.waitForLoadPage();
		
		testCase.addScreenShotCurrentStep("Open");
		Boolean correct = me.clickElementSikuli("EDI", open); //Da click en boton Cerrar de la ventana de descarga
		u.waitForLoadPage();
		
		u.hardWait(2);
		u.highLight(xmlArchivoCargado);
		testCase.addScreenShotCurrentStep("clickOpenCascaron");
		valida = m.existElement(xmlArchivoCargado);
		return valida;
		
	}
	
	/**
	 * Metodo:  Metodo que se�ala la opcion de Seleccione el Proveedor
	 * @author: Yair Liz�rraga
	 * @param:  ninguno
	 * @return: ninguno
	 */
	public void MouseOverProveedor() {
		
		u.highLight(xmlProveedor);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("MouseOverProveedor");
		
	}
		/**
		 * Metodo:  Metodo que da clic en la opcion seleccione proveedor para desplegar remesadores
		 * @author: Yair Liz�rraga
		 * @param:  ninguno
		 * @return: ninguno
		 */
		public void PresionarSeleccioneProveedor() {
			
			u.click(xmlProveedor);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("PresionarSeleccioneProveedor");
}
		/**
		 * Metodo:  Metodo que hace highlight en remesadores
		 * @author: Yair Liz�rraga
		 * @param:  ninguno
		 * @return: ninguno
		 */
		public void MostrarRemesadores() {
			
			u.highLight(xmlMostrarProvedoor);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("MostrarRemesadores");
}
		/**
		 * Metodo:  Metodo que da clic en el boton parametros
		 * @author: Yair Liz�rraga
		 * @param:  ninguno
		 * @return: ninguno
		 */
		public void PresionarBtnParametros() {
			
			u.highLight(xmlBtnParametros);;
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "PresionarBtnParametros");
			u.click(xmlBtnParametros);
			
			}
		
		/**
		 * Metodo para seleccionar una opcion de la pantalla 'Atributos Proveedor'
		 * @param opcion (String): opcion a seleccionar
		 * @author Andrea Contreras
		 */
		public boolean seleccionarOpcion(String opcion) {
			
			boolean existOption = false;
			me.explicitWaitToBeClickable(xmlOpcionesAP, Duration.ofSeconds(250));
			
			List<WebElement> allOptions = u.getDriver().findElements(xmlOpcionesAP);
			
			for (WebElement option : allOptions) {
				System.out.print(option.getText());
				System.out.print("   "+opcion);
				if (option.getText().equals(opcion)) {
					u.hardWait(1);
					me.highlight(option);
					testCase.addScreenShotCurrentStep("clickOpcion");
					option.click();
					u.hardWait(1);
					existOption = true;
					break;
				}
			}
			
			u.waitForLoadPage();
			u.hardWait(4);
			u.getDriver().navigate().refresh();
			u.waitForLoadPage();
			u.hardWait(6);
			testCase.addScreenShotCurrentStep("opcionSeleccionada");
			
			return existOption;

		}
		/**
		 * Metodo:  Metodo que da clic en el boton parametros
		 * @author: Yair Liz�rraga
		 * @param:  ninguno
		 * @return: ninguno
		 */
		public void PresionarFormaDePago() {
			
			u.scrollToElement(xmlFormaDePago);
			u.highLight(xmlFormaDePago);
			testCase.addScreenShotCurrentStep("PresionarFormaDePago1");				
			u.click(xmlFormaDePago);
			u.waitForLoadPage();
			u.hardWait(6);				
			u.scrollToElement(By.xpath("//h3[1]"));
			testCase.addScreenShotCurrentStep("PresionarFormaDePago2");

         }
		
		/**
		 * Metodo para validar que se muestren la informacion de la opcion 'Forma de Pago'
		 * @author: Andrea Contreras
		 * @param:  ninguno
		 * @return: true o false si se muestra la informacion
		 */
		public boolean revisarOpcionesFormaPago() {
			
			me.explicitWaitVisibilityOfElement(xmlValorParametro, Duration.ofSeconds(40));
			u.scrollToElement(By.id("form1:j_idt134"));
			
			boolean validacion = false, descVal, valorVal, unidadNegVal;
			
			descVal =  me.existElement(xmlDescParametroEmpty);
			valorVal = me.existElement(xmlValorParametro); 
			unidadNegVal = me.existElement(xmlUnidadNegocioEmpty);
			
			testCase.addScreenShotCurrentStep("revisarOpciones1");
			
			if(descVal == true && valorVal == true && unidadNegVal == true) {
				validacion = true;
				u.highLight(xmlDescParametro);		
				u.highLight(xmlValorParametro);
				u.highLight(xmlUnidadNegocio);
				testCase.addScreenShotCurrentStep("revisarOpciones2");
				u.unhighLight(xmlDescParametro);		
				u.unhighLight(xmlValorParametro);
				u.unhighLight(xmlUnidadNegocio);
			}								
			
			return validacion;	

         }
		
		/**
		 * Metodo:  Metodo que da clic en el boton Modificar
		 * @author: Yair Liz�rraga
		 * @param:  ninguno
		 * @return: ninguno
		 */
		public void PresionarBtnModificar() {
			
			u.highLight(xmlBtnModificar);;
			u.hardWait(4);
			testCase.addScreenShotCurrentStep("PresionarBtnModificar");
			u.click(xmlBtnModificar);

}
		/**
		 * Metodo:  Metodo que da clic en el boton Modificar
		 * @author: Yair Liz�rraga
		 * @param:  ninguno
		 * @return: ninguno
		 */
		public void VisualizarMediosDePago() {
			
			u.mouseOver(xmlParametroFDP);
			u.click(xmlParametroFDP);
			u.hardWait(2);
			u.highLight(xmlOpcionesFDP);
			testCase.addScreenShotCurrentStep(u, "VisualizarMediosDePago2");

}
		/**
		 * Metodo para seleccionar valor de parametro
		 * @param entidad (String): proveedor a seleccionar
		 * @author Yair Lizarraga
		 */
		public void ClicBtnEfectivo() {
			
			u.highLight(xmlBtnEfectivo);;
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "ClicBtnEfectivo");
			u.click(xmlBtnEfectivo);
			
			
}
		/**
		 * Metodo para seleccionar valor de parametro
		 * @param entidad (String): proveedor a seleccionar
		 * @author Yair Lizarraga
		 */
		public void ClicBtnActualizar() {
			
			u.highLight(xmlBtnActualizarFDP);
			testCase.addScreenShotCurrentStep("ClicBtnActualizar");
			u.click(xmlBtnActualizarFDP);
			u.waitForLoadPage();
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("ClicBtnActualizar2");
        }
		
		/**
		 * Metodo para visualizar la opcion 'Valores del Parametro'
		 * @author: Andrea Contreras
		 * @param:  ninguno
		 * @return: true o false si se visualiza la opcion
		 */
		public boolean visualizarValoresParametro() {
			
			me.explicitWaitVisibilityOfElement(xmlParametroFDP, Duration.ofSeconds(10));
			boolean validacion = me.existElement(xmlParametroFDP);
			u.highLight(xmlParametroFDP);
			testCase.addScreenShotCurrentStep("visualizarValoresParametro1");
			u.mouseOverAndClick(xmlParametroFDP);
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("visualizarValoresParametro2");
			u.unhighLight(xmlParametroFDP);
			u.hardWait(2);
			return validacion;

        }
		
		/**
		 * Metodo para deplegar la lista de opciones del campo 'Valores del Parametro'
		 * @author: Andrea Contreras
		 * @param:  ninguno
		 * @return: ninguno
		 */
		public void desplegarValoresParametro() {
			
			u.mouseOverAndClick(xmlParametroFDP);
			u.highLight(xmlValorParametroList);
			testCase.addScreenShotCurrentStep(u, "visualizarValoresParametro1");


        }
		
		
		/**
		 * Metodo para validar que se muestren las opciones indicadas
		 * @author: Andrea Contreras
		 * @param:  las opciones a validar
		 * @return: true o false si se visualiza la opcion
		 */
		public boolean revisarOpcionesValorParametro(String... valor) {
		
			boolean validacion = false;
			int count = 0;
			for(int i = 0; i < valor.length; i++) {			
				u.hardWait(1);
				List<WebElement> allOptions = u.getDriver().findElements(xmlValorParametriOp);
				for (WebElement option : allOptions) {
					u.hardWait(2);
					if(option.getText().equals(valor[i])) {
						//me.highlight(option);
						//testCase.addScreenShotCurrentStep(u, "valor"+i);						
						count++;
					}
				}			
			}
			
			if(count==valor.length) {
				validacion = true;
			}
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("visualizarValoresParametro2");
			
			return validacion;

        }
		
		
		
		/**
		 * Metodo para seleccionar una opcion en el campo 'Valores del Parametro
		 * @author: Andrea Contreras
		 * @param:  opcion a seleccionar
		 */
		public void selectValorParametro(String opcion) {
		
			u.hardWait(3);
			List<WebElement> allOptions = u.getDriver().findElements(xmlValorParametriOp);
			for (WebElement option : allOptions) {
				u.hardWait(2);
				if(option.getText().equals(opcion)) {
					//me.highlight(option);
					//testCase.addScreenShotCurrentStep(u, "seleccionarValorParametro1");
					option.click();
					break;
				}
			}
			u.hardWait(6);
			testCase.addScreenShotCurrentStep("seleccionarValorParametro2");			


        }
		
		/**
		 * Metodo para validar los dos textos del mensaje que se muestra en la pantalla
		 * @param msj1 (String): texto del mensaje1
		 * @param msj2 (String): texto del mensaje2
		 * @return true o false si coninciden los textos del mensaje
		 * @author Andrea Contreras
		 */
		public boolean validarTextoDosMensajes(String msj1,String msj2) {
			
			boolean validacion = false, v1 = false, v2 = false;
			
			if(me.existElement(xmlMsjParte1)) {
				if(u.getText(xmlMsjParte1).trim().equals(msj1.trim())) {
					v1 = true;
				}				
			}
			
            if(me.existElement(xmlMsjParte2)) {
            	if(u.getText(xmlMsjParte2).trim().equals(msj2.trim())) {
					v2 = true;
				}
			}
            
            if(v1 == true && v2 == true) {
            	validacion = true;
            }
			
			
			u.highLight(xmlMsj);
			testCase.addScreenShotCurrentStep("mensaje");
			
			return validacion;
        }
		
		
		
		
		
		
}

