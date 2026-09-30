package WEB.cometa.om;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import WEB.util.GlobalVariables;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;



public class PortalCometa {
	
	SeleniumUtil u;
	TestCase testCase;
	
	private SikuliUtil sikuli = new SikuliUtil();
	private Screen screen = new Screen();
	private Pattern p1;
	private String s1;
	private String folder = "Comprobantes de compra";

	/**
	 * Localizador de simbolo que aparece cuando esta cargando la pantalla
	 ******************************************************************************************/

	/**
	 * Sitio Seguro
	 ******************************************************************************************/
	By xmlPrivatingMessage = By.xpath("//*[@id=\"main-message\"]/h1");
	By xmlPrivatingAdvanced = By.xpath("//*[@id=\"details-button\"]");
    By xmlPrivatingProceed = By.xpath("//*[@id=\"proceed-link\"]");
    
    /**
	 * Inicio Sesion
	 ******************************************************************************************/
    By xmlUserName = By.xpath("//*[@id=\"email\"]");    
    By xmlPassword = By.xpath("//*[@id=\"password\"]");
    By xmlLoginButton = By.xpath("//*[@id=\"login\"]");
    By xmlCambiarContrasena = By.xpath("/html/body/div/div[1]/div/form/a");
	By xmlMensageErrorLogin = By.xpath("//div[@id='error_user' and not(starts-with(@class, 'd-none'))]");
	By xmlMensageErrorLoginIntentos = By.xpath("//div[@id='too_many_attempts' and not(starts-with(@class, 'd-none'))]");

    //Cambiar contasena 
    By xmlUserCc = By.xpath("//input[@formcontrolname='username']");
    By xmlPasswordCc = By.xpath("//input[@formcontrolname='currentPassword']");
    By xmlNewPasswordCc = By.xpath("//input[@formcontrolname='newPassword']");
    By xmlPasswordConfirmation = By.xpath("//input[@formcontrolname='passwordConfirmation']");
    By xmlAcceptButton = By.xpath("//button[contains(text(),'Aceptar')]");
    
    By xmlSignInCorreo = By.xpath("//*[@id=\"i0116\"]");
    By xmlSignInPassword = By.xpath("//*[@id=\"i0118\"]");
    By xmlSesionIniciada = By.xpath("//*[@id=\"idSIButton9\"]");
    By xmlSignInButton = By.xpath("//*[@id=\"lightbox\"]/div[3]/div/div[2]/div/div[5]/div/div/div/div");
    
    
  //Grid Portal Recepcion de transferencias y devoluciones

  	By xmlThFecha = By.xpath("(//th[normalize-space()='Fecha'])[1]");
  	By xmlThCRTienda = By.xpath("(//th[normalize-space()='CR Tienda'])[1]");
  	By xmlThNombreTienda = By.xpath("(//th[normalize-space()='Nombre de Tienda'])[1]");
  	By xmlThTransferencia = By.xpath("(//th[normalize-space()='Transferencia'])[1]");
  	By xmlThCanastillas = By.xpath("(//th[normalize-space()='# Canastillas Devueltas'])[1]");
  	By xmlThBolsEntrega = By.xpath("(//th[normalize-space()='Bols en Entrega'])[1]");
  	By xmlThFUE = By.xpath("(//th[normalize-space()='FUE'])[1]");
  	By xmlThTiraTransferencia = By.xpath("(//th[normalize-space()='Tira de Transferencia'])[1]");
  	By xmlThEvidenciaFoto = By.xpath("(//th[normalize-space()='Evidencias Fotográficas'])[1]");
    /**
	 * Portal Web
	 ******************************************************************************************/
    By xmlCerroSesion = By.xpath("/html/body/app-root/app-access-control/div/div/app-header/nav/div/div[2]/div/ul/li[1]/a");
    By xmlPortalCometa = By.xpath("/html/body/app-root/app-access-control/div/div/div/div[2]/div/div/div[2]/div/p/a");
    
    /**
	 * Portal Cometa
	 ******************************************************************************************/
    By xmlaDirectos = By.xpath("//a[span[text()='Directos']]");
    By xmlNavDirectos = By.xpath("(//button[span[@class='mdc-button__label' and contains(text(),'Directos')]])[1]");
	By xmlNavDirectosReportes = By.xpath("//button[span[@class='mat-mdc-menu-item-text' and contains(text(),'Reportes')]]");
    By xmlCedisOption = By.xpath("//a[span[text()='CEDIS']]");
    By xmlCedisCEDIS = By.xpath("/html/body/app-root/app-home/main/a[1]/span");
    By xmlInicioCEDIS = By.xpath("/html/body/app-root/app-header/header/nav/app-navigation-menu/nav/div[1]]");
    By xmlFAQsButtonCEDIS = By.xpath("//button[.//span[normalize-space()='FAQS CEDIS']]");
    By xmlEditarFAQ = By.xpath("//button[@mat-button]//span[normalize-space()='Editar']");
    By xmlGuardarEdicion = By.xpath("//button[@mat-button and .//span[normalize-space()='Guardar']]");
    By xmlEliminarFAQ = By.xpath("//button[contains(@class,'mat-mdc-button')]//span[contains(@class,'mdc-button__label') and normalize-space()='Eliminar']");
    By xmlConfimarEliminar = By.xpath("//mat-dialog-container//app-delete-faq-dialog-form//button[@mat-button and .//span[normalize-space()='Eliminar']]");
    By xmlaPortalCometaQA = By.xpath("/html/body/app-root/app-access-control/div/div/div/div[2]/div/div/div[2]/div/p");
    By xmlCedis = By.xpath("/html/body/app-root/app-header/header/nav/app-navigation-menu/nav/div[2]/button/span[2]");
    By xmlInicio = By.xpath("//button[span[starts-with(text(),' Inicio')]]");
    By xmlFAQsButton = By.xpath("/html/body/app-root/app-header/header/nav/app-navigation-menu/nav/div[3]/button");
    By xmlUserButton = By.xpath("/html/body/app-root/app-header/header/div[1]/div");
    By xmlCerrarSesion = By.xpath("//button[@mat-menu-item]//span[normalize-space()='Cerrar Sesión']");

    By tbPlaza         		= By.xpath("//ng-select[@bindlabel= 'plaza']");
    By lstFir 				= By.xpath("(//input[@id='item'])[1]");
    
    By xlmQuitarOvERLAY = By.xpath("/html/body/div/div[2]");
    
    //Portal Directos
    By xmlDirectos = By.xpath("//a[contains(@class,'card')]//span[normalize-space()='Directos']");
    // By btnDirectos = By.xpath("/html/body/app-root/app-home/main/a[2]");
    By btnDirectos = By.xpath("/html/body/app-root/app-home/main/a[1]");
    By btnDirectos_ = By.xpath("/html/body/app-root/app-home/main/a[1]");
    By btnReportes = By.xpath("/html/body/app-root/app-directos-home/main/div/a[2]");
    By btnEntregasCometa = By.xpath("/html/body/app-root/app-reportes-directos-indice/main/a[1]");

    By xmlComprobantesDeCompra = By.xpath("//span[normalize-space()='Comprobantes de Compra']");
    By btnComprobantesDeCompra = By.xpath("/html/body/app-root/app-directos-home/main/div/a[1]");
    By xmlReportesDirectos = By.xpath("//*[@id=\"mat-menu-panel-1\"]/div/button[2]");
    By xmlGraficasDirectos = By.xpath("/html/body/app-root/app-directos-home/main/div[2]/a[2]");
    By xmlReporteDirectosMsg = By.xpath("//p[text()='Coming Soon!']");
    
    By xmlOpcionReportes = By.xpath("//*[@href]/span[text() = 'Reportes']");
    By xmlOpcionInicioExterno = By.xpath("//button[span[contains(text(), 'Inicio Externo')]]");

    //Portal CEDIS
    By xmlRecepciondTransferyDev = By.xpath("//a[contains(@class,'card')]//span[normalize-space()='Recepción']");
    By xmlReportes = By.xpath("//*[@id=\"mat-menu-panel-1\"]/div/button[2]/span[contains(text(), 'Reportes')] ");
    By xmlGraficas = By.xpath("//*[@id=\"mat-menu-panel-1\"]/div/button[3]/span[contains(text(), 'Graficas')] ");
    
    
    //Portal Recepcion de transferencias y devoluciones
	By xmlPrimerFueGrid = By.xpath("(//tr[@role='row' and @mat-row])[1]");
    By xmlRegistrosPorPagina = By.xpath("//*[@id=\"mat-select-value-1\"]");
    By xmlCincoRegistros = By.xpath("//*[@id=\"mat-option-0\"]");
    By xmlDiezRegistros = By.xpath("//*[@id=\"mat-option-1\"]");
    By xmlQuinceRegistros = By.xpath("//*[@id=\"mat-option-2\"]");
    By xmlCRPlaza = By.xpath("//ng-select[contains (@bindlabel, 'plaza')]");
	By xmlCRPlazaOp1 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[1]");
	By xmlCRPlazaOp2 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[2]");
    By xmlConsultarButton = By.xpath("/html/body/app-root/app-cedis-recepcion-de-transferencias-y-devoluciones/main/div[1]/app-filters-form/div[1]/form/div[2]/button[2]/span[2]");

    By xmlLimpiaFiltros = By.xpath("//span[normalize-space()='Limpiar Filtros']");
    By xmlVerFue = By.xpath("(//*[name()='path'])[15]");
    By xmlVerTransferencia = By.xpath("//*[@id=\"mat-tab-content-0-0\"]/div/div/app-tabla-entrega/div/table/tbody/tr[1]/td[8]/button/span[2]");
    By xmlVerFotograficas = By.xpath("(//span[@class='mat-mdc-button-touch-target'])[17]");
	By xmlDescargaFUE = By.xpath("(//button[span[@class='mdc-button__label' and contains(text(), 'Descargar')]])[1]");
	By xmlDescargaTiraTransf = By.xpath("(//button[span[@class='mdc-button__label' and contains(text(), 'Descargar')]])[2]");
	By xmlRangoFechasCedisRecep = By.xpath("//mat-datepicker-toggle/button/span[2]");
	By xmlConsultarBusqHistorica = By.xpath("(//button[span[@class='mdc-button__label'and text()='Consultar'] and not(@disabled)])[1]"); //se agrega el no(@disabled) para validar en casos de prueba cuando esta habilitado (ya se selecciono un filtro para buscar)
	By xmlConsultarBusqHistorica1 = By.xpath("/html/body/app-root/app-recepcion-de-transferencias/main/div[1]/app-filtros-historicos/form/div/div[4]/button[2]/span[2]");
	By xmlCedisCRPlaza = By.xpath("//app-filtros-historicos//ng-select//div/div/div[2]");
	By xmlCedisCRPlazaOptions = By.xpath("//div[@id='mat-autocomplete-0']");
	By xmlCedisCRPlazaOption = By.xpath("//mat-option[@id='mat-option-108']");
	By xmlCedisCRTienda = By.xpath("/html/body/app-root/app-recepcion-de-transferencias/main/div[1]/app-filtros-historicos/form/div/div[2]/ng-select/div/div");
	By xmlCedisCRTiendaOptions = By.xpath("//div[@id='mat-autocomplete-1']");
	By xmlConsultarHistoricaDesactivado = By.xpath("//button[@disabled='true' and span[text()='Consultar']]");
	By xmlCRPlazaOptionMTYNorte = By.xpath("//div[contains(@class, 'ng-option') and contains(@class, 'ng-star-inserted') and contains(@class, 'ng-option-marked')]");
	By xmlCRPlazaOption2 = By.xpath("//div[contains(@class, 'ng-option') and contains(@class, 'ng-star-inserted') and contains(@class, 'ng-option-marked')]/following-sibling::div[1]");
	By xmlXpathTabla = By.xpath("//*[@id=\"mat-tab-content-0-0\"]/div/div/app-data-table/div/table/tbody");
	By xmlXpathFilas = By.xpath("//tbody/tr"); // XPath relativo para las filas dentro de la tabla
	By xmlCerrarFUE = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-modal-fue/main/div[1]/div[2]/div/button[3]");
	By xmlCerrarFUEv1 = By.xpath("//*[@id=\"Vector 75_2\"]");
	By xmlPaginadoNextCEDIS = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-modal-fue/main/div[3]/div/mat-paginator/div/div/div/button[3]/span[3]");
	By xmlPaginadoLastCEDIS = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-modal-fue/main/div[3]/div/mat-paginator/div/div/div/button[4]/span[3]");
	By xmlDescargarComprobanteTira = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-modal-tira-transferencia/main/div[1]/div[2]/div/button[1]");
	By xmlRangoAnos = By.xpath("//*[@id=\"mat-datepicker-0\"]/mat-calendar-header/div/div/button[1]");
	By xmlRango3Anos = By.xpath("//*[@id=\"mat-datepicker-0\"]/div/mat-multi-year-view/table/tbody/tr[6]");
	By xmlTooltipFecha = By.xpath("//*[@id=\"cdk-overlay-0\"]/mat-tooltip-component/div/div");
	By xmlVerEvidencia = By.xpath("//app-modal-fue//main//button[contains(text(), 'Ver evidencia')]");
	By xmlCanastilla = By.xpath("//*[@id=\"formato-de-devolucion\"]/tbody/tr[1]/th[1]");
	By xmlTablaCanastilla = By.xpath("//*[@id=\"emailContainer\"]/table");
	By xmlDescargaMasivaFue = By.xpath("/html/body/app-root/app-recepcion-de-transferencias/main/div[2]/app-descargas-masivas/div/div/div[1]/button");
	By xmlDescargaIndividualFue = By.xpath("//button[contains(@class, 'download-pdf')]");
	By xmlRutaPropuesta = By.xpath("//*[@id=\"headContent\"]/p[1]");
	By xmlPlazaPlaceHolder = By.xpath("/html/body/app-root/app-recepcion-de-transferencias/main/div[1]/app-filtros-historicos/form/div/div[1]/ng-select/div/div/div[1]");
	By xmlTiendaPlaceHolder = By.xpath("/html/body/app-root/app-recepcion-de-transferencias/main/div[1]/app-filtros-historicos/form/div/div[2]/ng-select/div/div/div[1]");
	By xmlSelectorRutaPlaceHolder = By.xpath("//*[@id=\"mat-mdc-form-field-label-0\"]/mat-label");
	By xmlRutaPlaceHolder = By.xpath("//*[@id=\"mat-mdc-form-field-label-2\"]/mat-label");
	By xmlBusquedaRapida = By.xpath("//*[@id=\"mat-mdc-form-field-label-6\"]/mat-label");
	By xmlPlazaTXT = By.xpath("//*[@id=\"tira-de-transferencia\"]/div[1]/table/tr[1]/td[2]");
	By xmlTiendaTXT = By.xpath("//*[@id=\"tira-de-transferencia\"]/div[1]/table/tr[2]/td[2]");
	By xmlFechaTXT = By.xpath("//*[@id=\"tira-de-transferencia\"]/div[1]/table/tr[3]/td[2]");
	By xmlFechaADMVTXT = By.xpath("//*[@id=\"tira-de-transferencia\"]/div[1]/table/tr[4]/td[2]");
	
	By xmlRutaPropuestaFUE = By.xpath("//p[contains(text(), 'Ruta Propuesta:')]");
	By xmlRutaOriginalFUE = By.xpath("//p[contains(text(), 'Ruta:')]");
	By xmlNombreTienda = By.xpath("//th[contains(text(), 'Nombre de Tienda')]");
	By xmlDevolucionMercancia = By.xpath("(//div[@id=\"fue\"] //table)[6] //tr[td[text()='Total']]/td[2]");
	By xmlDevolucionCanastilla = By.xpath("(//div[@id=\"fue\"] //table)[7] //tr[td[text()='Total']]/td[2]");
	


	By xmlTituloTira = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-modal-tira-transferencia/main/div[1]/div[1]");
	By xmlNextPageTira = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-modal-tira-transferencia/main/div[3]/div/mat-paginator/div/div/div/button[3]/span[1]");
	By xmlLastPageTira = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-modal-tira-transferencia/main/div[3]/div/mat-paginator/div/div/div/button[4]");
	By xmlCerrarTira = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-modal-tira-transferencia/main/div[1]/div[2]/div/button[2]");

	By xmlTituloFoto = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-modal-evidencias-fotograficas/main/div[1]/div[1]");
	By xmlNextPageFoto = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-modal-evidencias-fotograficas/main/div[3]/div/mat-paginator/div/div/div/button[3]");
	By xmlLastPageFoto = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-modal-evidencias-fotograficas/main/div[3]/div/mat-paginator/div/div/div/button[4]");
	By xmlCerrarFotografica = By.xpath("//button[contains(@class, 'close-icon')]");

	By xmlTituloFiltrosBusquedaComprobantes = By.xpath("/html/body/app-root/app-comprobantes-de-compra/div/h4");

	By xmlSelectPlazaRecepcion = By.xpath("//ng-select[contains (@bindlabel, 'plaza')]");
	By xmlRecepcionPlazaOption1 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[1]");
	By xmlRecepcionPlazaOption2 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[2]");
	By xmlRecepcionPlazaOption3 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[3]");
	By xmlRecepcionPlazaOption4 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[4]");
	By xmlLimpiarSeleccionPlaza = By.xpath("//ng-select[contains (@bindlabel, 'plaza')]/following-sibling::button[@class = 'clear-filter-button']");
	By xmlSelectTiendaRecepcion = By.xpath("//ng-select[contains (@bindlabel, 'tienda')]");
	By xmlTituloFiltroBusquedaRapida = By.xpath("/html/body/app-root/app-recepcion-de-transferencias/main/div[1]/app-filtros-historicos/h4");
	By xmlRecepcionTiendaOption1 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[1]");
	By xmlRecepcionTiendaOption2 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[2]");
	By xmlRecepcionTiendaOption3 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[3]");
	By xmlRecepcionTiendaOption4 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[4]");
	By xmlLimpiarSeleccionTienda = By.xpath("//ng-select[contains (@bindlabel, 'tienda')]/following-sibling::button[@class = 'clear-field-button']");
	By xmlSelectorRuta = By.xpath("//mat-select[@role= 'combobox' and @id= 'mat-select-0']");
	By xmlSelectorRutaOpciones = By.xpath("//div[@role= 'listbox']");
	By xmlOpcionDinamica = By.xpath("//mat-option[span[starts-with(text(), 'Din')]]");
	By xmlOpcionPMO = By.xpath("//mat-option[span[contains(text(), 'PMO')]]");
	By xmlMessageNotResultRecepcion = By.xpath("//div[contains(text(), 'No se encontr')]");
	By xmlResultadoBusquedasCedis = By.xpath("//div[@aria-live = 'polite' and @class = 'mat-mdc-paginator-range-label']");
	String notaFiltroRutaRecepcion = "";
	By xmlNotaFiltroRutaRecepcion = By.xpath("//mat-label[contains(text(), 'Ruta (Ejemplo: OBR284)')]");
	By xmlInputRutaCedis = By.xpath("//input[@id = 'mat-input-0']");
	By xmlFiltroPlazaRecepcionDeshabilitado = By.xpath("//ng-select[@bindlabel = 'plaza' and contains(@class, 'ng-select-disabled')]");
	By xmlFiltroTiendaRecepcionDeshabilitado = By.xpath("//ng-select[@bindlabel = 'tienda' and contains(@class, 'ng-select-disabled')]");
	By xmlSelectorRutaDeshabilitado = By.xpath("(//mat-form-field[contains(@class, 'mat-form-field-disabled')])[1]");
	By xmlInputRutaDeshabilitado = By.xpath("(//mat-form-field[contains(@class, 'mat-form-field-disabled')])[2]");
	By xmlMensajeFiltroBusquedaRapida = By.xpath("//mat-label[contains(text(), 'Escanea o teclea la canastilla')]");
	By xmlBotonLliquidacionRuta = By.xpath("//button[span[contains(text(), 'Liquidaci')]]");
	By xmlmenuSuperior = By.xpath("//div[@aria-haspopup = 'menu']");

	By xmlRegionConsulta = By.xpath("(//tbody/tr/td[3])[1]");
	By xmlPlazaConsulta = By.xpath("(//tbody/tr/td[4])[1]");
	By xmlPlazaDetalle = By.xpath("//app-detalle //p[contains(text(), 'Plaza')]");

	//Liquidacion de Ruta
	By xmlTituloPagLiquidacionRuta = By.xpath("//h3[contains(text(), 'Liquidaci')]");
	By xmlBotonRecepcionLiquidacionRuta = By.xpath("//button[span[contains(text(), 'Transferencias y Devoluciones')]]");
	By xmlInputRutaLiquidacion = By.xpath("//div[label[mat-label[contains(text(), '(UNIGIS)')]]]");
	By xmlInputRutaLiquidacion2 = By.xpath("//div[label[mat-label[contains(text(), '(UNIGIS)')]]]/input");
	By xmlFechaInicialLiquidacion = By.xpath("//*[@id=\"mat-select-value-5\"]/span");
	By xmlFechaInicialLiquidacionOp4 = By.xpath("(//mat-option[@role = 'option' and contains(@id,'mat-option-')])[4]");
	By xmlHoraInicialLiquidacion = By.xpath("//*[@id=\"mat-select-value-7\"]/span");
	By xmlHoraInicialLiquidacionOp1 = By.xpath("(//mat-option[@role = 'option' and contains(@id,'mat-option-')])[1]");
	By xmlFechaFinLiquidacion = By.xpath("//*[@id=\"mat-select-8\"]/div");
	By xmlFechaFinLiquidacionOp1 = By.xpath("(//mat-option[@role = 'option' and contains(@id,'mat-option-')])[1]");
	By xmlHoraFinLiquidacion = By.xpath("//*[@id=\"mat-select-value-11\"]/span");
	By xmlHoraFinLiquidacionOplast = By.xpath("(//mat-option[@role = 'option' and contains(@id,'mat-option-')])[last()]");
	By xmlBotonConsultarLiquidacion = By.xpath("//button[span[contains(text(), 'Consultar')]]");
	By xmlLabelClasificacionColor = By.xpath("//div[contains(text(), 'Clasificaci')]");
	By xmlLabelDetalleLiquidacion = By.xpath("//div[contains(text(), 'Detalle')]");
	By xmlBtnConsultarTooltip = By.xpath("//*[starts-with(@class, 'mdc-tooltip') and contains(text(), 'El Rango de fechas y Ruta son campos obligatorios')]");
	By xmlConsultarAlLadoLimpiarFiltrosLiquidacion = By.xpath("//button[span[contains(text(), ' Limpiar Filtros ')]]/following-sibling::button[span[contains(text(), 'Consultar')]]");
	By xmlBtnFueCeroCanastillas = By.xpath("(//td[text()='0'])[1]/following-sibling::td[2]");
	By xmlBtnVerEvidenciaFueDisabled = By.xpath("//button[text() = ' Ver evidencia ' and @disabled]");


	By xmlXpathLiquidacionRutaButton = By.xpath("//button[span[contains(text(), 'Liquidaci')]]");

    //Portal Comprobantes de compra
	By xmlTituloComprobantes = By.xpath("//h3[contains(text(), 'compra')]");
	By xmlProveedorOp1 = By.xpath("//div[contains(@class, 'ng-option') and contains(@class, 'ng-star-inserted') and contains(@class, 'ng-option-marked')]");
	By xmlProveedorOp2 = By.xpath("//div[contains(@class, 'ng-option') and contains(@class, 'ng-star-inserted') and contains(@class, 'ng-option-marked')]/following-sibling::div[1]");
    By xmlProveedor = By.xpath("//ng-select[contains (@bindlabel, 'supplierName')]");
    By xmlProveedorInput = By.xpath("(//ng-select[contains (@bindlabel, 'supplierName')]//input)[1]");
	By xmlProveedorOption1 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[1]");
	By xmlProveedorOption2 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[2]");
    By xmlRangoFechas = By.xpath("//mat-datepicker-toggle[@data-mat-calendar='mat-datepicker-0']");
    By xmlBotonFlechaIzquierda = By.xpath("//*[@id=\"mat-datepicker-0\"]/mat-calendar-header/div/div/button[2]");
    By xmlBotonFlechaDerecha = By.xpath("//*[@id=\"mat-datepicker-0\"]/mat-calendar-header/div/div/button[3]");
    By xmlSeleccionarFecha = By.xpath("//*[@id=\"mat-datepicker-0\"]/div/mat-month-view/table/tbody/tr[2]/td[4]/button/span[1]");
    By xmlSeleccionarFecha2 = By.xpath("//*[@id=\"mat-datepicker-0\"]/div/mat-month-view/table/tbody/tr[5]/td[4]/button/span[1]");
    By xmlConsultar = By.xpath("//button[span[text()=' Consultar ']]");
    By xmlMensajeFecha = By.xpath("/html/body/app-root/app-directos-comprobantes-de-compra/app-tabla-resultados/mat-card/mat-card-content/div[1]/table/tbody/tr/td");
    By xmlBotonDescargar = By.xpath("//span[@class='mdc-button__label' and contains(text(), 'Descargar')]");
	By xmlSeleccionarRegistroComprobantes = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[@mat-row][1]");
    By xmlDescargarPDF = By.xpath("//button[contains(@class,'download-pdf')]");
    By xmlDescargarExcel = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-detalle/div/mat-card[9]/mat-card-content/button[2]");
    By xmlSeleccionarFA = By.xpath("/html/body/app-root/app-directos-comprobantes-de-compra/app-tabla-resultados/div/mat-card/mat-card-content/form/div[1]/mat-form-field[2]/div[1]/div[2]/div");
    By xmlClicFA = By.xpath("//*[@id=\"mat-option-14\"]");
    By xmlCRTiendainput = By.xpath("//div[div[text()='Tienda']]//input");
    By xmlFuenteDeAbasto = By.xpath("//mat-label[contains(text(), 'FA (Fuente de Abasto)')]");
    By xmlZona = By.xpath("//ng-select[contains (@bindlabel, 'zona')]");
	By xmlZonaInput = By.xpath("//ng-select[@bindvalue='zona']/div/div/div/input[@type='text']");
	By xmlZonaOp1 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[1]");
	By xmlZonaOp2 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[2]");
	By xmlZonaOp3 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[3]");
	By xmlZonaOp4 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[4]");
	By xmlOpcionesZona = By.xpath("//div[@role=\"listbox\"]");
    By xmlRegion = By.xpath("//ng-select[contains (@bindlabel, 'region')]");
    
	By xmlInputRegion = By.xpath("//ng-select[contains (@bindlabel, 'region')]/div/div/div/input[(@type = 'text')]");
	By xmlOpcionesFuenteRegion = By.xpath("//div[@role=\"listbox\"]");
	By xmlregionOp1 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[1]");
	By xmlregionOp2 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[2]");
	By xmlregionOp3 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[3]");
	By xmlregionOp4 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[4]");
	By xmlOpcionesPlaza = By.xpath("//div[@role=\"listbox\"]");
	By xmlPlazaOp1 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[1]");
	By xmlPlazaOp2 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[2]");
	By xmlPlazaOp3 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[3]");
	By xmlCRPlazainput = By.xpath("//input[@placeholder='Plaza']");
    By xmlCRPLazaDirectos = By.xpath("//ng-select[contains (@bindlabel, 'plaza')]");
	By xmlCRPlazaDirectosInput = By.xpath("//ng-select[@bindvalue='plaza']/div/div/div/input[@type='text']");
    By xmlCRTienda = By.xpath("//ng-select[contains (@bindlabel, 'tienda')]");
	By xmlOpcionesTienda = By.xpath("//div[@role=\"listbox\"]");
	By xmlTiendaOp1 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[1]");
	By xmlTiendaOp2 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[2]");
	By xmlTiendaOpLast = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[last()]");
    By xmlCRTiendaOp1 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[1]");
	By xmlCRTiendaOp2 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[2]");
	By xmlCRTiendaOp3 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[3]");
	By xmlCRTiendaOp4 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[4]");
	By xmlRadioButtonExcelMasivo = By.xpath("//*[@id=\"mat-radio-3\"]/div/div");
    By xmlRadioButtonPDFMasivo = By.xpath("//*[@id=\"mat-radio-2\"]/div/div");
    By xmlDescargarMasivo = By.xpath("(//span[contains(text(), 'Descargar')])[2]");
    By xmlLimpiarFiltros = By.xpath("//button[span[text()=' Limpiar Filtros ']]");
	By xmlProveedorOptions = By.xpath("//*[@id='mat-autocomplete-0']");
	By xmlFuenteAbasto = By.xpath("//ng-select[contains (@bindlabel, 'fuenteDeAbasto')]");
	By xmlFuenteAbastoInput = By.xpath("//ng-select[contains (@bindlabel, 'fuenteDeAbasto')]//input");
	By xmlOpcionesFuenteAbasto = By.xpath("//div[@role=\"listbox\"]");
	By xmlFuenteAbastoOp1 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[1]");
	By xmlFuenteAbastoOp2 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[2]");
	By xmlFuenteAbastoOp3 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[3]");
	By xmlFuenteAbastoOp4 = By.xpath("(//div[starts-with(@class, 'ng-option') and contains(@role, 'option')])[4]");
	By xmlFuenteAbastoOption = By.xpath("(//mat-option[starts-with(@id,'mat-option')])[1]");
	By xmlFuenteAbastoValidacion = By.xpath("(//td[text()=2086])[1]");
	By xmlFechaActual = By.xpath("//td[@role='gridcell' and button[@class='mat-calendar-body-cell mat-calendar-body-active']]");
	By xmlLeyendaComprobante = By.xpath("//p[@Class='overlay-text top']");
	By xmlSelloComprobante = By.xpath("(//mat-card-content[@Class='mat-mdc-card-content']/img)[1]");
	By fa = By.xpath("/html/body/app-root/app-comprobantes-de-compra/app-tabla-de-informacion/mat-card/mat-card-content/div/table/tbody/tr[1]/td[1]");
	By fecha = By.xpath("//*[@id=\"mat-tab-content-0-0\"]/div/div/app-data-table/div/table/thead/tr/th[1]");
	By registro = By.xpath("/html/body/app-root/app-directos-comprobantes-de-compra/app-tabla-resultados/mat-card/mat-card-content/div[1]/table/tbody/tr[1]/td[2]");
	By btnPDF = By.xpath("//*[@id=\"mat-mdc-dialog-0\"]/div/div/app-detalle/div/mat-card[10]/mat-card-content/button[1]/span[4]");
	By fechaInicial = By.xpath("//*[@id=\"mat-datepicker-0\"]/div/mat-month-view/table/tbody/tr[2]/td[2]/button/span[1]");
	By fechaFinal = By.xpath("//*[@id=\"mat-datepicker-0\"]/div/mat-month-view/table/tbody/tr[5]/td[3]/button/span[1]");
	By inputZona = By.xpath("//*[@id=\"mat-input-2\"]");
	By checkNorte = By.xpath("//*[@id=\"mat-mdc-checkbox-87\"]/div/label");
	By checkPacifico = By.cssSelector("#mat-mdc-checkbox-82 > div > label");
	By btnLimpiarFiltros = By.cssSelector("body > app-root > app-directos-comprobantes-de-compra > app-tabla-resultados > div > mat-card > mat-card-actions > button.margin-r.mdc-button.mdc-button--outlined.mat-mdc-outlined-button.mat-unthemed.mat-mdc-button-base > span.mdc-button__label");
	By btnConsultar = By.xpath("/html/body/app-root/app-directos-comprobantes-de-compra/app-tabla-resultados/div/mat-card/mat-card-actions/button[2]/span[4]");
	By btnDescargar = By.xpath("/html/body/app-root/app-directos-comprobantes-de-compra/app-tabla-resultados/mat-card/mat-card-content/app-download-filtered-summary-button/div/button/span[4]");
	By inputFA = By.xpath("//*[@id=\"mat-input-1\"]");
	By check2521 = By.cssSelector("#mat-mdc-checkbox-1 > div > label");
	By check10228 = By.cssSelector("#mat-mdc-checkbox-2 > div > label");
	By btnDescargaMasiva = By.xpath("/html/body/app-root/app-directos-comprobantes-de-compra/app-tabla-resultados/mat-card/mat-card-content/app-download-filtered-summary-button/div/button/span[4]");
	By btnOct2024 = By.xpath("//*[@id=\"mat-datepicker-0\"]/mat-calendar-header/div/div/button[1]/span[2]");
	By btn2021 = By.xpath("//*[@id=\"mat-datepicker-0\"]/div/mat-multi-year-view/table/tbody/tr[6]/td[1]/button/span[1]");
	By btnAtras = By.xpath("//*[@id=\"mat-datepicker-0\"]/mat-calendar-header/div/div/button[2]/span[2]");
	By inputPlaza_ = By.xpath("//*[@id=\"mat-input-4\"]");
	By btnCedis = By.xpath("/html/body/app-root/app-home/main/a[1]");
	By btnRecepcion = By.xpath("/html/body/app-root/app-cedis-home/main/a[1]");
	By xmlLimpiarSeleccionProveedor = By.xpath("//ng-select[contains(@bindlabel, 'supplierName')]/following-sibling::button[contains(@class, 'clear-field-button')]");
	By xmlLimpiarSeleccionFA = By.xpath("//ng-select[contains(@bindlabel, 'fuenteDeAbasto')]/following-sibling::button[contains(@class, 'clear-field-button')]");
	By xmlLimpiarSeleccionZona = By.xpath("//ng-select[contains(@bindlabel, 'zona')]/following-sibling::button[contains(@class, 'clear-field-button')]");
	By xmlLimpiarSeleccionRegion = By.xpath("//ng-select[contains(@bindlabel, 'region')]/following-sibling::button[contains(@class, 'clear-field-button')]");
	By xmlLimpiarSeleccionPlazaDirectos = By.xpath("//ng-select[contains (@bindlabel, 'plaza')]/following-sibling::button[@class = 'clear-field-button']");
	By xmlLimpiarSeleccionTiendasDirectos = By.xpath("//ng-select[contains(@bindlabel, 'tienda')]/following-sibling::button[contains(@class, 'clear-field-button')]");

	By btnResumen = By.xpath("/html/body/app-root/app-directos-comprobantes-de-compra/app-tabla-resultados/mat-card/mat-card-content/app-download-filtered-summary-button/div/button");
	By inputRegion = By.xpath("//*[@id=\"mat-input-3\"]");

	By gridNombreTienda1 = By.xpath("(//td[starts-with(@class, 'mat-mdc-cell') and contains(text(), 'Cerveceria Modelo de Mexico')])[1]/following-sibling::td[contains(@class, 'mat-column-tienda')]");
	By gridObtenerCRTienda1 = By.xpath("(//td[starts-with(@class, 'mat-mdc-cell') and contains(text(), 'Cerveceria Modelo de Mexico')])[1]/following-sibling::td[contains(@class, 'mat-column-crTienda')]");
	By preliminarNombreTienda = By.xpath("//p[@class = 'cr-tienda cr-tienda-font-size']");
	By preliminarObtenerCRTienda = By.xpath("//p[@class = 'cr-tienda right-padding cr-tienda-font-size']");
	By textoAcuerdoEntrega = By.xpath("(//div[@class=\"tablas-header\"])[1]/p[1]");
	By tablaArticulosEntregados = By.xpath("(//div[@id = 'tablas'])[1]");
	By formatoMonedaTablaPrelimnar = By.xpath("//td[starts-with(text(), '$')]");
	By textoTotalGeneralVenta = By.xpath("//div[@class= 'totales-box']/div/p[2]");

	//LiquidaciÃ³n de Ruta
	//By xml = By.xpath("");
	By xmlRecepcionButton = 	By.xpath("//button[span[contains(text(), 'Transferencias y Devoluciones')]]");
	By xmlInputRuta = 			By.xpath("//div[label[mat-label[contains(text(), '(UNIGIS)')]]]/input");
	By xmlSelectFechainicio = 	By.xpath("//div[div[div[label[mat-label[contains(text(), 'Fecha inicial')]]]]]");
	By xmlSelectFechaFin = 		By.xpath("//div[div[div[label[mat-label[contains(text(), 'Fecha final')]]]]]");
	By xmlSelectHoraInicio = 	By.xpath("//div[div[div[label[mat-label[contains(text(), 'Hora inicial')]]]]]");
	By xmlSelectHoraFin = 		By.xpath("//div[div[div[label[mat-label[contains(text(), 'Hora final')]]]]]");
	By xmlLimpiarFiltroButton = By.xpath("//button[span[contains(text(), 'Limpiar Filtros')]]");
	By xmlConsultarRutaButton = By.xpath("//button[span[contains(text(), 'Consultar')]]");

	By xmlDetalleRuta = By.xpath("//div[@id='detalle-ruta']");
	By xmlDetalleTiendas = By.xpath("//div[@id='detalle-tiendas']");
	By xmlMenuSuperiorUsuario = By.xpath("//div[@class='top-bar']/div");
	By xmlMenuSuperior = By.xpath("//nav[div][@class='nav-bar']");
	By xmlClasificacionColor = By.xpath("//div[ div[contains(text(), 'por Color')] and div/div/p[contains(text(), 'Canastillas Totales')]]");
	By xmlDetalleGeneral = By.xpath("//div[ div[contains(text(), 'Detalle')] and div/div/p[contains(text(), 'Total Cajas y Canastillas Sobrantes')]]");
	By xmlClasificacionColorInfo = By.xpath("//div[p[contains(text(), 'Canastillas Totales de la Ruta')] and p[contains(text(), 'Canastillas Azules')] and p[contains(text(), 'Canastillas Amarillas')] and p[contains(text(), 'Canastillas Verdes')] and p[contains(text(), 'Canastillas Naranjas')] and p[contains(text(), 'Otros')]]");

	By xmlPantallaLiquidacion = By.xpath("//h3[contains(text(), 'de Ruta')]");
	
	By xmlLabelConsultaSinDatos = By.xpath("//div[@id='detalle-ruta']/div/p[contains(text(), 'con los filtros proporcionados')]");

	By xmlTiendas = By.xpath("//div[@id='tienda']");
	By xmlTiendaNombre = By.xpath("//div[@id='tienda']/div/p[contains(text(), 'Tienda')]");

	By xmlbolsLabel = By.xpath(".//p[contains(text(), 'Bols')]");
	By xmlTablaTiendasLiquidacionRuta = By.xpath("//div[@id ='detalle-tiendas']");

	//By xml = By.xpath("");
	//nav/div/button/span[contains(text(), 'Inicio Externo')]
	//nav/div/button/span[contains(text(), 'CEDIS')]
	//nav/div/button/span[contains(text(), 'Inicio Externo')]
	//nav/div/button/span[contains(text(), 'Inicio Externo')]



    private String name;
    private List<Object[]> rows;
	By xmlRegionMexico = By.xpath("//td[contains(text(),'Mexico')]");
	By xmlRegionMexcicoOp1 = By.xpath("(//td[contains(text(),'Mexico')])[1]");

	String hoy = String.valueOf(LocalDate.now().getDayOfMonth()); //Obtener dia actual de el mes
	String manana = String.valueOf(LocalDate.now().plusDays(1).getDayOfMonth()); // Obtener el dia siguiente
	By xmlFechaInicialRangoFecha = By.xpath("//div[@class='mat-date-range-input-wrapper']");
	By xmlFechaFinalRegistrada = By.xpath("//*[@id=\"mat-date-range-input-0\"]/div/div[1]/input");
	By xmlPrimerDiaHabilidato = By.xpath("(//button[starts-with(@class, 'mat-calendar-body-cell') and not(@aria-disabled)])[1]");
	By xmlUltimoDiaHabilitado = By.xpath("(//button[starts-with(@class, 'mat-calendar-body-cell') and not(@aria-disabled)])[last()]");
	By xmlFechaInicioMes = By.xpath("//span[starts-with(@class, 'mat-calendar-body-cell-content mat-focus-indicator') and text()=1]");
	By xmlFechaFinalMes30 = By.xpath("//span[starts-with(@class, 'mat-calendar-body-cell-content mat-focus-indicator') and text()=30]");
	By xmlFechaFinalMes31 = By.xpath("//span[starts-with(@class, 'mat-calendar-body-cell-content mat-focus-indicator') and text()=31]");
	By xmlFechaFinalMes28 = By.xpath("//span[starts-with(@class, 'mat-calendar-body-cell-content mat-focus-indicator') and text()=28]");
	By xmlFechaDiaActual = By.xpath("//span[starts-with(@class, 'mat-calendar-body-cell-content mat-focus-indicator') and text()=" +hoy+ "]");
	By xmlFechaDiaSiguiente = By.xpath("//span[starts-with(@class, 'mat-calendar-body-cell-content mat-focus-indicator') and text()=" +manana+ "]");
	By xmlValidacionFiltradoFechaSiguente = By.xpath("(//td[contains(@class,'cdk-column-fecha mat-column-fecha') and contains(text(),"+manana+")])[1]");
	By xmlValidacionFiltradoFechaActual = By.xpath("(//td[contains(@class,'cdk-column-fecha mat-column-fecha') and contains(text()," +hoy+")])[1]");
	By xmlGridOption1Fecha = By.xpath("//td[contains(@class,'cdk-column-fecha mat-column-fecha')]");
	By xmlPaginadoNext = By.xpath("(//button[starts-with(@class, 'mat-mdc-tooltip-trigger mat-mdc-paginator-navigation-next') and not(@disabled)])[1]");
	By xmlPaginadoFinal = By.xpath("(//button[starts-with(@class, 'mat-mdc-tooltip-trigger mat-mdc-paginator-navigation-last') and not(@disabled)])[1]");
	By xmlSegundaFechaFiltro = By.xpath("(//div[span[@class='mat-date-range-input-mirror']])[last()]");
	By xmlFechaUltimopRegistro = By.xpath("(//td[contains(@class, ' mat-column-fecha ')])[last()]");

	By xmlCometaDirectosCPNoInfo = By.xpath("//td[@class='mat-cell no-data']"); //mensaje que se muestra cuando no se encuentra informacion en el filtrado en directos comprobantes compra
	By xmlPrimerComprobanteFiltro = By.xpath("(//tr[@role='row' and @mat-row])[1]");
	By xmlDescargaPdfDetallesComprobante = By.xpath("//button[span[@class='mdc-button__label' and text()=' PDF ']]");
	By xmlDescargarExcelDetalleComprobante = By.xpath("//button[span[@class='mdc-button__label' and text()=' EXCEL ']]");
	By xmlDescargaMasivaExcel = By.xpath("//div[input[@id='mat-radio-3-input']]");
	By xmlDescargaMasivaPdf = By.cssSelector("button.btn-pdf");
	By xmlBotonDescargaMasiva = By.xpath("//button[span[@class='mdc-button__label' and text()=' Descargar ']]");
	/**
	 * Constructor
	 ******************************************************************************************/
	
	public PortalCometa(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	
		
		
	}

/**
 * Destaca el elemento indicado y toma una captura de pantalla
 * @param elemento	->	Elemento a destacar
 * @param imageName	->	Nombre con el que se guarda la imagen
 * @author Yair LizÃƒÂ¡rraga
 * @date Agosto 2024
 */
private void tomarEvidencia(By elemento, String imageName) {
	u.highLight(elemento);
	testCase.addScreenShotCurrentStep(u, imageName);
	u.unhighLight(elemento);
}

/**
 * Si el elemento esta desplegado, toma evidencia
 * @param elemento	->	Elemento a buscar
 * @author Yair LizÃƒÂ¡rraga
 * @date Agosto 2024
 */
private Boolean encontrarElemento(By elemento) {
    boolean isValid = true; // Inicializamos la bandera en true

    try {
        if (u.getDriver().findElement(elemento).isDisplayed()) {
            tomarEvidencia(elemento, "Elemento");
        } else {
            isValid = false; // Si el elemento no se encuentra, la bandera se pone en false
        }
    } catch (Exception e) {
        isValid = false; // Si ocurre una excepcin, la bandera se pone en false
    }

    return isValid; // Devolvemos la bandera
}

	////////////////////////METODOS LOGIN
		/**
		 * Metodo para dar iniciar sesion en el portal cometa
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public void IniciarSesion(String userName,String password) {
	 		u.sendKeys(xmlUserName, userName);
	 		u.sendKeys(xmlPassword, password);
	 		u.click(xmlLoginButton);
	 		testCase.addScreenShotCurrentStep(u, "clickContainer");
	 	}

		/**
		 * Metodo para dar iniciar sesion en el portal cometa
		 * @author Jorge Hernandez
		 * @date Septiembre 2024
		 */
	 	 public boolean IniciarSesionNoToken(String userName,String password) {
			 boolean result;
			 if(u.isDisplayed(xmlUserName) && u.isDisplayed(xmlPassword)){
				 u.sendKeys(xmlUserName, userName);
				 u.sendKeys(xmlPassword, password);
				 u.click(xmlLoginButton);
				 testCase.addScreenShotCurrentStep(u, "clickContainer");

				 result = true;
			 } else {
				 result = false;
			 }


			 return result;
		 }

		/**
		 * Metodo para saltar ventana de "Conexion no privada"
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public void PrivatePortal() {
			
			Boolean bandera = false;
			try {
				u.getDriver().findElement(xmlPrivatingMessage);
				bandera = true;
			}catch (Exception e) {
				bandera = false;
			}
			
			if (bandera) {
				u.click(xmlPrivatingAdvanced);
		    	u.click(xmlPrivatingProceed);
			}
	
}
		/**
		 * Metodo para dar clic en la app de portal cometa
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public void ClicPortalCometa() {
	 	u.hardWait(20);
	 	u.highLight(xmlPortalCometa);
	 	testCase.addScreenShotCurrentStep(u, "Portal Cometa");
	 	u.click(xmlPortalCometa);
	 	testCase.addScreenShotCurrentStep(u, "Portal Cometa Pantalla");
	 	
	 	}
	 	

 	/**
 	 * Valida que aparezca la opcion del portal Cometa
 	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
 	 */
 	public void validarOpcionCometa() {
 		try {
 			u.waitWebElement(xmlPortalCometa, 60, 2);
 			testCase.addScreenShotCurrentStep("Muestra solo el menu cometa");
 		}catch(Exception ex) {
 			System.out.println("Error --> "+ ex.getMessage());
 		}
 	}

	/**
	 * Metodo para dar clic en el boton inicio
	 * @author Yair Lizarraga
	 * @date Agosto 2024
	 */
 	public Boolean ClicBotonInicio() {
 	    boolean isValid = true; // Inicializamos la bandera en true

	 	    try {
	 		 	u.highLight(xmlInicio);
	 		 	testCase.addScreenShotCurrentStep(u, "Inicio");
	 		 	u.click(xmlInicio);
	 		 	testCase.addScreenShotCurrentStep(u, "Portal Cometa Pantalla");
	 	    } catch (Exception e) {
	 	        isValid = false; // Si ocurre una excepci n, la bandera se pone en false
	 	    }

	 	    return isValid; // Devolvemos la bandera
	 	}


 	/**
	 * Metodo para dar clic en el boton Cedis
	 * @author Yair Lizarraga
	 * @date Agosto 2024
	 */
	public void ClicBotonCedis() {
	u.highLight(xmlCedisOption);
	testCase.addScreenShotCurrentStep(u, "Cedis");
	u.click(xmlCedisOption);
	testCase.addScreenShotCurrentStep(u, "Portal Cometa Pantalla");
	 }


	/**
	 * Click en el boton de Directos
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void clickDirectos() {
		try {
			u.waitWebElement(xmlDirectos, 60, 2);
			u.highLight(xmlDirectos);
			testCase.addScreenShotCurrentStep("Click Directos");
			u.click(xmlDirectos);
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Click en el boton de Directos
	 * @author Jose Zarate - 1000072516
	 * @date Noviembre 2024
	 */
	public void clickDirectos_plaza() {
		try {
			u.waitWebElement(btnDirectos_, 60, 2);
			u.highLight(btnDirectos_);
			testCase.addScreenShotCurrentStep("Click Directos");
			u.click(btnDirectos_);
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}
	
	/**
	 * Click en el boton de Cedis
	 * @author Jose Zarate - 1000072516
	 * @date Noviembre 2024
	 */
	public void clickCedis() {
		try {
			u.waitWebElement(btnCedis, 60, 2);
			u.highLight(btnCedis);
			testCase.addScreenShotCurrentStep("Click Cedis");
			u.click(btnCedis);
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}
	
	/**
	 * Click en el boton de Cedis
	 * @author Jose Zarate - 1000072516
	 * @date Noviembre 2024
	 */
	public void clickRecepcion() {
		try {
			u.waitWebElement(btnRecepcion, 60, 2);
			u.highLight(btnRecepcion);
			testCase.addScreenShotCurrentStep("Click Recepcion");
			u.click(btnRecepcion);
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}
	
	/**
	 * Click en el boton de Reportes
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void clickReportes() {
		try {
			u.waitWebElement(btnReportes, 60, 2);
			u.highLight(btnReportes);
			testCase.addScreenShotCurrentStep("Click Reportes");
			u.click(btnReportes);
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Selecciona una opcion del menu de graficas
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void selectOpcion() {
		try {
			u.waitWebElement(btnEntregasCometa, 60, 2);
			u.highLight(btnEntregasCometa);
			testCase.addScreenShotCurrentStep("Click Entregas por medio de COMETA");
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Realiza un hover a la opcion Entregas por medio de COMETA del menu de graficas
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void selectEntregasCometa() {
		try {
			Actions actions = new Actions(u.getDriver());
			WebElement element = u.getDriver().findElement(btnEntregasCometa);
			actions.moveToElement(element).perform();
			testCase.addScreenShotCurrentStep("Hover en Entregas por medio de COMETA");
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Click en el boton de Comprobantes de compra
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void clickComprobantesDeCompra() {
		try {
			u.waitWebElement(xmlComprobantesDeCompra, 60, 2);
			u.highLight(xmlComprobantesDeCompra);
			testCase.addScreenShotCurrentStep("Click Comprobantes de compra");
			u.click(xmlComprobantesDeCompra);
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Click en el primer registro del grid
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void clickRegistro() {
		try {
			u.waitWebElement(registro, 60, 2);
			u.highLight(registro);
			testCase.addScreenShotCurrentStep("Click en el registro");
			u.click(registro);
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Click en el boton de descarga PDF
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void clickDescargaPDF() {
		try {
			u.waitWebElement(btnPDF, 60, 2);
			u.scrollToElement(btnPDF);
			u.highLight(btnPDF);
			testCase.addScreenShotCurrentStep("Click en descarga PDF");


			WebElement element = u.getDriver().findElement(btnPDF);
			
			// Forza el click en el boton Consultar
			JavascriptExecutor jsExecutor = (JavascriptExecutor) u.getDriver();
			jsExecutor.executeScript("arguments[0].click();", element);
			
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Realiza el llenado de filtros para levar a cabo una busqueda
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void llenaFiltros() {
		try {
			capturaProveedor();
			clickRangoDeFechas();
			clickFechaInicial();
			clickFechaFinal();
			
			testCase.addScreenShotCurrentStep("Llenado de filtros");

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Realiza el llenado de filtros para levar a cabo una busqueda
	 * @author Jose Zarate - 1000072516
	 * @date Noviembre 2024
	 */
	public void llenaFiltros_plaza() {
		try {
			capturaPlaza();
			clickRangoDeFechas();
			clickFechaInicial();
			clickFechaFinal();
						
			testCase.addScreenShotCurrentStep("Llenado de filtros");
			
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}
	
	/**
	 * Captura el filtro de plaza
	 * @author Jose Zarate - 1000072516
	 * @date Noviembre 2024
	 */
	public void capturaPlaza() {
		try {
			u.waitWebElement(inputPlaza_, 60, 2);
			u.highLight(inputPlaza_);
			u.sendKeys(inputPlaza_, "10MON 10MON APODACA" + Key.TAB);
			
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}
	
	/**
	 * Captura el filtro del proveedor
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void capturaProveedor() {
		try {
			u.waitWebElement(xmlProveedor, 60, 2);
			u.highLight(xmlProveedor);
			u.sendKeys(xmlProveedor, "EMBOTELLADORAS BEPENSA SA DE CV" + Key.TAB);

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Captura el filtro del proveedor
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void clickProveedor() {
		try {
			u.waitWebElement(xmlProveedor, 60, 2);
			u.highLight(xmlProveedor);
			u.click(xmlProveedor);
			testCase.addScreenShotCurrentStep("Click en el filtro Proveedor");

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}


	/**
	 * Hace click en el icono del calendario
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public boolean clickRangoDeFechas() {
		boolean result;

		if(u.isDisplayed(xmlRangoFechas)){
			u.highLight(xmlRangoFechas);
			u.click(xmlRangoFechas);

			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Selecciona el aÃ±o 2024 en el calendario
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public boolean select2024() {
		boolean result;
		u.waitWebElement(btnOct2024, 60, 2);
		if(u.isDisplayed(btnOct2024)){
			u.highLight(btnOct2024);
			testCase.addScreenShotCurrentStep("Selcciona 2024");
			u.click(btnOct2024);
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Selecciona el aÃ±o 2021 en el calendario
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public boolean select2021() {
		boolean result;
		u.waitWebElement(btn2021, 60, 2);
		if(u.isDisplayed(btn2021)){
			u.highLight(btn2021);
			testCase.addScreenShotCurrentStep("Selcciona 2021");
			u.click(btn2021);

			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Seleciona la fecha inicial del rango de fechas
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void clickFechaInicial() {
		try {
			u.highLight(btnAtras);
			
			WebElement element = u.getDriver().findElement(btnAtras);
			
			// Forza el click en el boton Consultar
			JavascriptExecutor jsExecutor = (JavascriptExecutor) u.getDriver();
			jsExecutor.executeScript("arguments[0].click();", element);
			
			u.hardWait(3);
			u.highLight(fechaInicial);
			u.click(fechaInicial);

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Selecciona la fecha final del rango de fechas
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void clickFechaFinal() {
		try {
			u.highLight(fechaFinal);
			u.click(fechaFinal);

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Da click en el boton Consultar
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void clickConsultar() {
		try {
			u.hardWait(3);
			u.highLight(btnConsultar);
			testCase.addScreenShotCurrentStep("Click en Consultar");

			WebElement element = u.getDriver().findElement(btnConsultar);

			// Forza el click en el boton Consultar
			JavascriptExecutor jsExecutor = (JavascriptExecutor) u.getDriver();
			jsExecutor.executeScript("arguments[0].click();", element);

			//u.click(btnConsultar);

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Click en el boton Descargar
	 * @author Jose Zarate -  1000072516
	 * @date Octubre 2024
	 */
	public void clickDescargar() {
		try {
			u.hardWait(5);
			u.scrollToElement(btnDescargar);
			u.scroll(300);
			u.highLight(btnDescargar);
			testCase.addScreenShotCurrentStep("Click en boton Descargar");

			WebElement element = u.getDriver().findElement(btnDescargar);
			
			// Forza el click en el boton Consultar
			JavascriptExecutor jsExecutor = (JavascriptExecutor) u.getDriver();
			jsExecutor.executeScript("arguments[0].click();", element);
			
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Selecciona la opcion de Zona norte en el filtro de Zonas
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void agregarZonaNorte() {
		try {
			u.waitWebElement(inputZona, 20, 2);
			u.highLight(inputZona);
			u.sendKeys(inputZona, "NOR");
			testCase.addScreenShotCurrentStep("Iniciales de Zona");

			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "Norte.png");
			screen.wait(p1, 30.0);
			screen.click();
			u.unhighLight(inputZona);
			testCase.addScreenShotCurrentStep("Agrega Zona");

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Selecciona la opcion de Pacifico en el filtro de Zona
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void agregarZonaPacifico() {
		try {
			u.waitWebElement(inputZona, 20, 2);
			u.highLight(inputZona);
			u.sendKeys(inputZona, "PACIF");
			testCase.addScreenShotCurrentStep("Iniciales de Zona");

			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "Pacifico.png");
			screen.wait(p1, 30.0);
			screen.click();
			u.unhighLight(inputZona);
			testCase.addScreenShotCurrentStep("Agrega Zona");

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}


	/**
	 * Elimina la zona Norte agregada en el fltro Zonas
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void eliminarZonaNorte() {
		try {

			u.click(inputZona);
			testCase.addScreenShotCurrentStep("Click en zona");


			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "Norte-select.png");
			screen.wait(p1, 30.0);
			screen.click();
			u.unhighLight(inputZona);
			testCase.addScreenShotCurrentStep("Elimina Zona");

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}


	/**
	 * Elimina la zona Pacifico del filtro Zonas
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void eliminarZonaPacifico() {
		try {

			u.hardWait(3);
			u.click(inputZona);
			testCase.addScreenShotCurrentStep("Click en zona");


			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "Pacifico-select.png");
			screen.wait(p1, 30.0);
			screen.click();
			u.unhighLight(inputZona);
			testCase.addScreenShotCurrentStep("Elimina Zona");


		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}


	/**
	 * Limpia todos los valores de los filtro
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void clickLimpiarFiltros() {
		try {
			u.hardWait(5);
			u.highLight(btnLimpiarFiltros);
			testCase.addScreenShotCurrentStep("Click limpiar filtros");
			u.click(btnLimpiarFiltros);

			u.hardWait(2);
			testCase.addScreenShotCurrentStep("Se limpian filtros");
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Agrega un valor 2521 en el filtro FA
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void agregarFA2521() {
		try {
			u.waitWebElement(inputFA, 60, 2);
			u.highLight(inputFA);
			u.sendKeys(inputFA, "2521");
			testCase.addScreenShotCurrentStep("Agrega FA");

			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "2521.png");
			screen.wait(p1, 30.0);
			screen.click();
			u.unhighLight(inputFA);
			testCase.addScreenShotCurrentStep("Agrega FA");

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}


	/**
	 * Agrega el valor 10220 al filtro FA
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void agregarFA10228() {
		try {
			u.waitWebElement(inputFA, 60, 2);
			u.highLight(inputFA);
			u.sendKeys(inputFA, "10228");
			testCase.addScreenShotCurrentStep("Agrega FA");


			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "10228.png");
			screen.wait(p1, 30.0);
			screen.click();
			u.unhighLight(inputFA);
			testCase.addScreenShotCurrentStep("Agrega FA");

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Elimina el valor 2521 del filtro FA
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void eliminar2521() {
		try {

			u.hardWait(3);
			u.click(inputFA);
			testCase.addScreenShotCurrentStep("Click en FA");

			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "2521-select.png");
			screen.wait(p1, 30.0);
			screen.click();
			u.unhighLight(inputFA);
			testCase.addScreenShotCurrentStep("Elimina FA");


		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Elimina el valor 10228 del filtro FA
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public void eliminar10228() {
		try {

			u.hardWait(3);
			u.click(inputFA);
			testCase.addScreenShotCurrentStep("Click en FA");

			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "10228-select.png");
			screen.wait(p1, 30.0);
			screen.click();
			u.unhighLight(inputFA);
			testCase.addScreenShotCurrentStep("Elimina FA");


		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Selecciona la opcion de CDMX en el filtro de Region
	 * @author Jose Zarate - 1000072516
	 * @date Diciembre 2024
	 */
	public void agregarRegionCDMX() {
		try {
			u.waitWebElement(inputRegion, 20, 2);
			u.highLight(inputRegion);
			u.sendKeys(inputRegion, "CD");
			testCase.addScreenShotCurrentStep("Iniciales de CDMX");

			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "cdmx.png");
			screen.wait(p1, 30.0);
			screen.click();
			u.unhighLight(inputRegion);
			testCase.addScreenShotCurrentStep("Agrega Zona");

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Selecciona la opcion de Noreste Oriente en el filtro de Region
	 * @author Jose Zarate - 1000072516
	 * @date Diciembre 2024
	 */
	public void agregarRegionNoresteOriente() {
		try {
			u.waitWebElement(inputRegion, 20, 2);
			u.highLight(inputRegion);
			u.sendKeys(inputRegion, "No");
			testCase.addScreenShotCurrentStep("Iniciales de Noreste Oriente");

			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "Noreste Oriente.png");
			screen.wait(p1, 30.0);
			screen.click();
			u.unhighLight(inputRegion);
			testCase.addScreenShotCurrentStep("Agrega Zona");

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}

	/**
	 * Click en el boton de descarga masiva
	 * @author Jose Zarate - 1000072516
	 * @date Octubre 2024
	 */
	public boolean clickDescargaMasiva() {
		boolean result;
		u.waitWebElement(btnDescargaMasiva, 30, 2);
		if(u.isDisplayed(btnDescargaMasiva)){
			u.scrollToElement(btnDescargaMasiva);
			u.highLight(btnDescargaMasiva);
			testCase.addScreenShotCurrentStep("Click en Descargar");

			WebElement element = u.getDriver().findElement(btnDescargaMasiva);
			// Forza el click en el boton de Descarga
			JavascriptExecutor jsExecutor = (JavascriptExecutor) u.getDriver();
			jsExecutor.executeScript("arguments[0].click();", element);

			result = true;
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Valida la region del comprobante de compra
	 * @return --> True en caso de encontrarlo, False en caso contrario
	 * @author 1000072516 - Jose Zarate
	 * @date Diciembre 2024
	 */
	public boolean validarRegionComprobante() {
		boolean result=false;
		String plazaReal, plazaObtenida;
		try {
			//Tomar valor de region
			plazaReal="Plaza: "+u.getText(xmlPlazaConsulta);
			System.out.println("####"+plazaReal);
			u.highLight(xmlPlazaConsulta);
			testCase.addScreenShotCurrentStep("Valor de plaza");
			u.unhighLight(xmlPlazaConsulta);
			u.click(xmlPlazaConsulta);

			u.hardWait(5);

			if(u.isDisplayed(xmlPlazaDetalle)) {
				plazaObtenida=u.getText(xmlPlazaDetalle);
				System.out.println("####"+plazaObtenida);
				if(plazaReal.contains(plazaObtenida))
					result=true;
			}
			else {
				System.out.println("No se encontrÃ³ el detalde de la Plaza");
				return false;
			}


		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
			result=false;
		}

		return result;
	}

	/**
	 * Obtener la Refgion
	 * @author 1000072516 - Jose Zarate
	 * @date Diciembre 2024
	 */
	public void obtenerRegion() {
		try {
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "fecha.png");
			screen.wait(p1, 30.0);

			Match match = screen.find(p1);

			if(match != null) {
				int x = match.getX();
				int y = match.getY();
				int width = match.getW();
				int height = match.getH();

				System.out.println("Coordenadas encontradas");
				System.out.println("x: "+ x);
				System.out.println("y: "+ y);
				System.out.println("Ancho: "+ width);
				System.out.println("Alto: "+ height);

				match.highlight(5);

			}else {
				System.out.println("Imagen no encontrada en la pantalla");
			}

		}catch(Exception ex) {

		}
	}

	/**
	 * Obtener Texto
	 * @author 1000072516 - Jose Zarate
	 * @date Diciembre 2024
	 */
	public String obtenerTextoOrden() {
		try {
			Region region = new Region(225,437,61,25);
			region.highlight();

			String orden = region.text();
			System.out.println("Ordem encontrada: "+ orden);
			return orden;

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
			return ex.getMessage();
		}
	}

	/**
	 * Obtener Texto Fecha
	 * @author 1000072516 - Jose Zarate
	 * @date Diciembre 2024
	 */
	public String obtenerTextoFecha() {
		try {
			Region region = new Region(197,376,82,23);
			region.highlight();

			String fecha = region.text();
			System.out.println("Fecha encontrada: "+ fecha);
			return fecha;

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
			return ex.getMessage();
		}

	}

	/**
	 * Obtener Texto
	 * @author 1000072516 - Jose Zarate
	 * @param orden -> Para validar el nombre del nombre del documento
	 * @param fecha -> Para validar la fecha del nombre del documento
	 * @date Diciembre 2024
	 */
	public boolean comparaNombre(String orden, String fecha) {
		try {
			u.hardWait(10);

			String downloadPath = System.getProperty("user.home") + "/Downloads";
			File file = new File(downloadPath+"/OC_"+orden+"_"+fecha+".pdf");

			if(file.exists()) {
				System.out.println("El archivo existe");
				return true;
			}else {
				System.out.println("El archivo no existe");
				return false;
			}

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
			return false;
		}
	}

	/**
	 * Metodo para comparar el nombre de el excel
	 * @author 1000072516 - Jose Zarate
	 * @param nombre -> Para validar el nombre del nombre del documento
	 * @date Diciembre 2024
	 */
	public boolean comparaNombreExcel(String nombre) {
		try {
			u.hardWait(10);

			String downloadPath = System.getProperty("user.home") + "/Downloads";
			File file = new File(downloadPath+"/"+nombre+".xlsx");

			if(file.exists()) {
				System.out.println("El archivo existe");
				return true;
			}else {
				System.out.println("El archivo no existe");
				return false;
			}

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
			return false;
		}
	}

	/**
	 * Metodo para validar el encabezado de el documento
	 * @author 1000072516 - Jose Zarate
	 * @date Diciembre 2024
	 */
	public boolean abrirYCompararEncabezados() {
		boolean result = false;
		try {
			String downloadFilepath = System.getProperty("user.name") + "/Downloads";
			ChromeOptions options = new ChromeOptions();
			options.addArguments("download.default_directory=" + downloadFilepath);

			File folder = new File(downloadFilepath);
			File[] files = folder.listFiles((dir, name) -> name.endsWith(".xlsx") || name.endsWith(".xls"));

			if(files != null && files.length > 0) {
				File mostRecentFile = files[0];
				for(File file : files) {
					if(file.lastModified() > mostRecentFile.lastModified()) {
						mostRecentFile = file;
					}
				}
				System.out.println("Archivo mas reciente: "+ mostRecentFile.getName());
				List<String> encabezadosEsperados = Arrays.asList("FA", "Nombre del Proveedor", "RegiÃ³n", "Plaza", "CR Tienda", "Nombre de Tienda","Fecha","Orden de Compra","RemisiÃ³n","Folio GOMA","Fecha ADMVA","Total General a la Venta","Cifra del proveedor","Movs. Valorizados", "Total Costo");

				CompararEncabezados(mostRecentFile, encabezadosEsperados);
				result = true;
			}
		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
			result = false;
		}

		return result;
	}

	/**
	 * Metodo para comparar los encabezados de el documento
	 * @author 1000072516 - Jose Zarate
	 * @param file -> Documento
	 * @param encabezadosEsperados -> Lista de encabezados esperados
	 * @date Octubre 2024
	 */
	public void CompararEncabezados(File file, List<String> encabezadosEsperados) {
		try(FileInputStream fis = new FileInputStream(file)) {
			Workbook workbook = file.getName().endsWith(".xlsx") ? new XSSFWorkbook(fis) : new XSSFWorkbook(fis);
			Sheet sheet = workbook.getSheetAt(0);

			Row headerRow = sheet.getRow(0);
			List<String> encabezadosArchivo = new ArrayList<>();
			for(Cell cell : headerRow) {
				encabezadosArchivo.add(cell.getStringCellValue().trim());
			}

			if(encabezadosArchivo.equals(encabezadosEsperados)) {
				System.out.println("Los encabezados coinciden con los esperados");
				testCase.addBoldTextEvidenceCurrentStep("Los encabezados coinciden");
			}else {
				workbook.close();
				System.out.println("Los encabezados no coinciden");
			}

			workbook.close();

		}catch(Exception ex) {
			System.out.println("Error --> "+ ex.getMessage());
		}
	}


		/**
		 * Metodo para dar clic en el boton Recepcion de transferencias en el menu cedis
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public Boolean ClicBotonRecepcionDeTransferencias() {
		    boolean isValid = true; // Inicializamos la bandera en true

		    try {
		        u.highLight(xmlRecepciondTransferyDev);
		        testCase.addScreenShotCurrentStep(u, "Recepcion de Transferencias");
		        u.click(xmlRecepciondTransferyDev);
		        testCase.addScreenShotCurrentStep(u, "Portal Cometa Pantalla");
		    } catch (Exception e) {
		        isValid = false; // Si ocurre una excepci  n, la bandera se pone en false
		    }

		    return isValid; // Devolvemos la bandera
		}
		
		/**
		 * Metodo para dar clic en el boton reportes en el menu cedis
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public void ClicBotonReportes() {
		u.highLight(xmlReportes);
		testCase.addScreenShotCurrentStep(u, "Reportes Cedis");
		u.click(xmlReportes);
		testCase.addScreenShotCurrentStep(u, "Portal Cometa Pantalla");
		}
		
		/**
		 * Metodo para dar clic en el boton graficas en el menu cedis
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public void ClicBotonGraficas() {
		u.highLight(xmlGraficas);
		testCase.addScreenShotCurrentStep(u, "Graficas Cedis");
		u.click(xmlGraficas);
		testCase.addScreenShotCurrentStep(u, "Portal Cometa Pantalla");
		}
		
		/**
		 * Metodo para validar que existan todos los botones en el menu cedis
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public Boolean ValidarExistenciaBotonesCedis() {
		    boolean isValid = true; // Inicializamos la bandera en true

		    try {
		        u.highLight(xmlUserButton);
		        u.mouseOver(xmlUserButton);
		        u.click(xmlUserButton);
		        testCase. addScreenShotCurrentStep(u, "Boton Inicio");
		        u.highLight(xmlCerrarSesion);
		        u.unhighLight(xmlCerrarSesion);
		        testCase. addScreenShotCurrentStep(u, "Cerrar Sesion");
		        u.mouseOverAndClick(xmlUserButton);
		        //u.click(xlmQuitarOvERLAY);
		        u.highLight(xmlCedis);
		        u.click(xmlCedis);
		        testCase. addScreenShotCurrentStep(u, "Boton Inicio");
		        u.unhighLight(xmlCedis);
		        u.highLight( xmlRecepciondTransferyDev);
		        u.highLight(xmlReportes);
		        u.highLight(xmlGraficas);
		        testCase. addScreenShotCurrentStep(u, "Boton Inicio");
		    } catch (Exception e) {
		        isValid = false; // Si ocurre una excepci n, la bandera se pone en false
		    }

		    return isValid; // Devolvemos la bandera
		}
		
		/**
		 * Metodo para validar que existan todos los botones en el menu directos
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public Boolean ValidarExistenciaBotonesDirectos() {
		    boolean isValid = true; // Inicializamos la bandera en true

		    try {
		        u.highLight(xmlUserButton);
		        u.mouseOverAndClick(xmlUserButton);
		        u.unhighLight(xmlUserButton);
		        testCase. addScreenShotCurrentStep(u, "Boton Inicio");
		        u.mouseOverAndClick(xmlUserButton);
		        u.mouseOverAndClick(xmlDirectos);
		        u.click(xmlDirectos);
		        u.highLight( xmlComprobantesDeCompra);
		        u.highLight(xmlReportesDirectos);
		        //u.highLight(xmlGraficasDirectos);//se quito boton
		    } catch (Exception e) {
		        isValid = false; // Si ocurre una excepci  n, la bandera se pone en false
		    }

		    return isValid; // Devolvemos la bandera
		}
		
		
		/**
		 * Metodo para validar que funcione el registros por pagina y mostrar 5 registros
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public void ValidarRegistrosPorPagina5() {
		u.scrollToElement(xmlRegistrosPorPagina);
		u.highLight(xmlRegistrosPorPagina);
		u.click(xmlRegistrosPorPagina);
		testCase.addScreenShotCurrentStep(u, "Registros");
		u.highLight(xmlCincoRegistros);
		u.click(xmlCincoRegistros);
		u.hardWait(5);
		u.scrollToElement(xmlRegistrosPorPagina);
		testCase.addScreenShotCurrentStep(u, "5 Registros");
		}
		
		
		/**
		 * Metodo para validar que funcione el registros por pagina y mostrar 10 registros
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public void ValidarRegistrosPorPagina10() {
		u.click(xmlRegistrosPorPagina);
		u.highLight(xmlDiezRegistros);
		u.click(xmlDiezRegistros);
		u.hardWait(5);
		u.scrollToElement(xmlRegistrosPorPagina);
		testCase.addScreenShotCurrentStep(u, "10 Registros");
		u.click(xmlRegistrosPorPagina);
		u.highLight(xmlQuinceRegistros);
		u.click(xmlQuinceRegistros);
		u.hardWait(5);
		u.scrollToElement(xmlRegistrosPorPagina);
		testCase.addScreenShotCurrentStep(u, "15 Registros");
		}
		
		
		/**
		 * Metodo para validar que funcione el registros por pagina y mostrar 15 registros
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public Boolean ValidarRegistrosPorPagina15() {
		    boolean isValid = true; // Inicializamos la bandera en true

		    try {
		        u.click(xmlRegistrosPorPagina);
		        u.highLight(xmlQuinceRegistros);
		        u.click(xmlQuinceRegistros);
		        u.hardWait(5);
		        u.scrollToElement (xmlRegistrosPorPagina);
		        testCase. addScreenShotCurrentStep(u, "15 Registros");
		    } catch (Exception e) {
		        isValid = false; // Si ocurre una excepcin, la bandera se pone en false
		    }

		    return isValid; // Devolvemos la bandera
		}
		/**
		 * Metodo para validar que se cierre sesin por inactividad despues de 15 minutos
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 * @throws InterruptedException
		 */
		public Boolean ValidarCierreDeSesionInactividad() throws InterruptedException {
		    boolean isValid = true; // Inicializamos la bandera en true

		    try {
		        Thread.sleep(900000);
		        u.waitWebElement(xmlCerroSesion, 910, 10);
		        u.highLight(xmlCerroSesion);
		        testCase. addScreenShotCurrentStep(u, "Cierre Sesion");
		    } catch (Exception e) {
		        isValid = false; // Si ocurre una excepcin, la bandera se pone en false
		    }

		    return isValid; // Devolvemos la bandera
		}
	 	
		/**
		 * Metodo para dar clic en el boton cerrar sesion
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public Boolean ClicCerrarSesion() {
		    boolean isValid = true; // Inicializamos la bandera en true

		    try {
		        u.highLight(xmlUserButton);
		        testCase. addScreenShotCurrentStep(u, "User");
		        u.click(xmlUserButton);
		        testCase. addScreenShotCurrentStep(u, "Portal Cometa Pantalla");
		        u.highLight(xmlCerrarSesion);
		        u.click(xmlCerrarSesion);
		        u.waitWebElement(xmlUserName, 10, 5);
		        testCase. addScreenShotCurrentStep(u, "Sesi n cerrada");
		    } catch (Exception e) {
		        isValid = false; // Si ocurre una excepci n, la bandera se pone en false
		    }

		    return isValid; // Devolvemos la bandera
		}
		/**
		 * Metodo para validar los botones de menu admin directos
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public Boolean ValidarMenuAdminDirectos() {
		    boolean isValid = true; // Inicializamos la bandera en true

		    try {
		        u.highLight(xmlUserButton);
		        u.click(xmlUserButton);
		        testCase.addScreenShotCurrentStep(u, "Boton Inicio");
		        u.unhighLight(xmlUserButton);
		        u.highLight(xmlCerrarSesion);
		        testCase.addScreenShotCurrentStep(u, "Cerrar Sesion");
		        u.mouseOverAndClick(xmlUserButton);
		        u.mouseOverAndClick(xmlDirectos);
		        u.highLight(xmlFAQsButton);
		        u.highLight(xmlComprobantesDeCompra);
		    } catch (Exception e) {
		        isValid = false; // Si ocurre una excepci  n, la bandera se pone en false
		    }

		    return isValid; // Devolvemos la bandera
		}
		/**
		 * Metodo para dar clic en el boton cerrar sesion
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
		public Boolean ValidarMenuOxxoOsDirectos() {
		    boolean isValid = true; // Inicializamos la bandera en true

		    try {
		    	u.highLight(xmlUserButton);
				u.click(xmlUserButton);
				testCase.addScreenShotCurrentStep(u, "Boton Inicio");
				u.unhighLight(xmlUserButton);
				u.highLight(xmlCerrarSesion);
				testCase.addScreenShotCurrentStep(u, "Cerrar Sesion");
				u.mouseOverAndClick(xmlUserButton);
				u.mouseOverAndClick(xmlDirectos);
				u.highLight(xmlFAQsButton);
				u.highLight(xmlComprobantesDeCompra);				
				testCase.addScreenShotCurrentStep(u, "Botones");
		    } catch (Exception e) {
		        isValid = false; // Si ocurre una excepci n, la bandera se pone en false
		    }

		    return isValid; // Devolvemos la bandera
		}
		
	 	/////metodos nuevos
	 	/**
		 * Metodo para dar clic en el boton directos e ir a su respectiva pantalla
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public void ClicBotonDirectos() {
	 	u.highLight(xmlDirectos);
	 	testCase.addScreenShotCurrentStep(u, "Pantall Directos");
	 	u.click(xmlDirectos);
	 	testCase.addScreenShotCurrentStep(u, "Portal Cometa Directos Pantalla");
	 	
	 	}
	 	
		/**
		 * Metodo para dar clic en el boton comprobantes de compra
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public boolean ClicBotonComprobantesDeCompra() {
			 if(u.isDisplayed(xmlComprobantesDeCompra)){
				 u.highLight(xmlComprobantesDeCompra);
				 testCase.addScreenShotCurrentStep(u, "Pantalla Comprobantes de compra");
				 u.click(xmlComprobantesDeCompra);
				 testCase.addScreenShotCurrentStep(u, "Portal Cometa Directos cdc Pantalla");
				 u.waitForLoadPage();
				 boolean ok;
				 try {
				     ok = MensajeEspereDesaparece(false);
				 } catch (Exception e) {
				     throw new RuntimeException("Fallo MensajeEspereDesaparece: " + e.getMessage(), e);
				 }			
				 
				 return true;
			 } else {
				 return false;
			 }
	 	}
	 	/**
		 * Valida que el mensaje "Espere un momento" ya haya desaparecido en tickets de Devoluciones
		 *
		 * @param screenshot true o false para guardar evidencia
		 * @return true si el mensaje ya no se visualiza
		 * @throws Exception cuando ocurre un error en la validacion
		 * @author Jose Pecina
		 * @date Septiembre 2025
		 */
		public boolean MensajeEspereDesaparece(boolean screenshot) throws Exception{
			try {
			boolean result;  
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(360));
			 wait.until(driver -> {
			     try {
			         JavascriptExecutor js = (JavascriptExecutor) driver;
			         String script =
			             "const overlays = document.querySelectorAll('.cdk-global-overlay-wrapper');" +
			             "for (const overlay of overlays) {" +
			             "  const text = overlay.innerText || '';" +
			             "  if (text.includes('Espere un momento')) return false;" +
			             "}" +
			             "return true;";  // Si no encontró ninguno con ese texto, entonces ya desapareció
			         return Boolean.TRUE.equals(js.executeScript(script));
			     } catch (Exception e) {
			         System.out.println("Error al ejecutar JS para validar overlay: " + e.getMessage());
			         return false;
			     }
			 });
			 if (screenshot) {
		            testCase.addTextEvidenceCurrentStep("Se visualiza la ventana emergente cargando");
		            testCase.addScreenShotCurrentStep("Descarga");
		        }
			result = true;
			
			
			return result;
		}catch (Exception e) {			
				testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
				throw new Exception ("Error " + e.getMessage());			
		}
		}
	 	/**
		 * Metodo para dar clic en el boton Reportes de la pantalla Directos
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public void ClicBotonReportesDirecto() {
	 	u.highLight(xmlReportesDirectos);
	 	testCase.addScreenShotCurrentStep(u, "Pantall Reportes Directo");
	 	u.click(xmlReportesDirectos);
	 	testCase.addScreenShotCurrentStep(u, "Reportes Directo");
	 	
	 	}
	 	
	 	/**
		 * Metodo para dar clic en el boton Reportes de la pantalla Directos
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public void ClicBotonGraficasDirecto() {
	 	u.highLight(xmlGraficasDirectos);
	 	testCase.addScreenShotCurrentStep(u, "Pantall Graficas Directo");
	 	u.click(xmlGraficasDirectos);
	 	testCase.addScreenShotCurrentStep(u, "Graficas Directo");
	 	
	 	}
	 	
	 	/**
		 * Metodo para dar validar que existe boton FAQs
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public void ValidarBotonFAQsCEDIS() {
	 	u.highLight(xmlFAQsButtonCEDIS);
	 	testCase.addScreenShotCurrentStep(u, "FAQs");
	 	u.click(xmlFAQsButtonCEDIS);
	 	testCase.addScreenShotCurrentStep(u, "Portal Cometa Directos Pantalla");
	 	
	 	}
	 	/**
	 	 * Metodo para validar la existencia del boton faqs
	 	 * @author Yair LizÃƒÂ¡rraga
	 	 */
	 	public Boolean ValidarBotonFAQs() {
	 	    boolean isValid = true; // Inicializamos la bandera en true

	 	    try {
			 	u.highLight(xmlFAQsButton);
			 	testCase.addScreenShotCurrentStep(u, "FAQs");
			 	u.click(xmlFAQsButton);
			 	testCase.addScreenShotCurrentStep(u, "Portal Cometa Directos Pantalla");

	 	    } catch (Exception e) {
	 	        isValid = false; // Si ocurre una excepci  n, la bandera se pone en false
	 	    }

	 	    return isValid; // Devolvemos la bandera
	 	}
	 	/**
		 * Metodo para dar validar que existe boton Reportar de Incidencias NOTA: EL BOTÃƒâ€œN AUN NO EXISTE
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public void ValidarBotonReporteDeIncidencia() {
	 	u.highLight(xmlFAQsButton);
	 	testCase.addScreenShotCurrentStep(u, "Reporte de Incidencia");
	 	u.click(xmlFAQsButton);
	 	testCase.addScreenShotCurrentStep(u, "Reporte de Incidencias");
	 	}
	 	
	 	/*
		 * Metodo para dar seleccionar A UNA FA
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public void SeleccionarFA() {
	 	u.click(xmlSeleccionarFA);
	 	testCase.addScreenShotCurrentStep(u, "clickContainer");
	 	u.click(xmlClicFA);
	 	testCase.addScreenShotCurrentStep(u, "Fechas");
	 	}
	 	
	 	
		/**
		 * Metodo para dar seleccionar una fecha
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public void SeleccionarFecha() {
	 	u.click(xmlRangoFechas);
	 	testCase.addScreenShotCurrentStep(u, "clickContainer");
	 	u.click(xmlBotonFlechaIzquierda);
	 	u.click(xmlBotonFlechaIzquierda);
	 	u.click(xmlSeleccionarFecha);
	 	u.click(xmlBotonFlechaDerecha);
	 	u.click(xmlBotonFlechaDerecha);
	 	u.click(xmlSeleccionarFecha2);
	 	testCase.addScreenShotCurrentStep(u, "Fechas");
	 	}
	 	
	 	/**
		 * Metodo para dar seleccionar una fecha
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public void SeleccionarFechaErronea() {
	 	u.click(xmlRangoFechas);
	 	testCase.addScreenShotCurrentStep(u, "clickContainer");
	 	u.click(xmlSeleccionarFecha2);
	 	u.click(xmlSeleccionarFecha2);
	 	testCase.addScreenShotCurrentStep(u, "Fechas");
	 	}
	 	
	 	/**
		 * Metodo para dar dar clic en el boton consultar de la pantalla de comprobantes
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public Boolean ClicBotonConsultar() {
	 	    boolean isValid = true; // Inicializamos la bandera en true

	 	    try {
	 	        u.click(xmlConsultar);
	 	        testCase. addScreenShotCurrentStep(u, "clickContainer");
	 	    } catch (Exception e) {
	 	        isValid = false; // Si ocurre una excepci n, la bandera se pone en false
	 	    }

	 	    return isValid; // Devolvemos la bandera
	 	}
	 	/**
		 * Metodo validar texto de fecha erronea
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public void ValidarFechaErronea(String mensajeEsperado) {
	 	String actualText = u.getText(xmlMensajeFecha);
	 	if("mensajeEsperado" == "actualText"){
		 	testCase.addScreenShotCurrentStep(u, "clickContainer");
		}
	 	}
	 	
	 	
		/**
		 * Metodo para dar dar clic en el boton consultar de la pantalla de comprobantes
		 * @author Yair Lizarraga
		 * @date Agosto 2024
		 */
	 	public Boolean ClicSeleccionarRegsitro() {
	 	    boolean isValid = true; // Inicializamos la bandera en true

	 	    try {
	 		 	u.highLight(xmlSeleccionarRegistroComprobantes);
	 		 	u.click(xmlSeleccionarRegistroComprobantes);
	 		 	testCase.addScreenShotCurrentStep(u, "clickContainer");
	 	    } catch (Exception e) {
	 	        isValid = false; // Si ocurre una excepci n, la bandera se pone en false
	 	    }
	 	   try {
			     MensajeEspereDesaparece(false);
			 } catch (Exception e) {
			     throw new RuntimeException("Fallo MensajeEspereDesaparece: " + e.getMessage(), e);
			 }	
	 	    return isValid; // Devolvemos la bandera
	 	}
	 	/**
		 * Metodo dar clic en el botn descargar
		 * @author Yair Lizarraga
		 * @date Agosto 2024
	 	 * @throws InterruptedException
		 */
	 	public boolean ClicBotonDescargar() throws InterruptedException {
			 u.scrollToElement(xmlBotonDescargar);
			 if(u.isDisplayed(xmlBotonDescargar)){
				 u.highLight(xmlBotonDescargar);
				 testCase.addScreenShotCurrentStep(u, "clickContainer");
				 u.click(xmlBotonDescargar);
				 testCase.addScreenShotCurrentStep(u, "clickContainer");
				 u.hardWait(5);
				 return true;
			 } else {
				 return false;
			 }

	 	}
	 	/**
	 	 * Metodo para dar clic en el boton descargar PDF de un reporte
	 	 * @date Agosto 2024
	 	 * @author Yair LizÃƒÂ¡rraga
	 	 */
	 	public void ClicBotonDescargarPDF() throws InterruptedException {
		 u.scrollToElement(xmlDescargarPDF);
		 u.highLight(xmlDescargarPDF);
		 testCase.addScreenShotCurrentStep(u, "clickContainer");
		 u.click(xmlDescargarPDF);
			u.hardWait(5);
		 testCase.addScreenShotCurrentStep(u, "clickContainer");
		 	}
		/**
	 	 * Metodo para dar clic en el boton descargar EXCEL de un reporte
	 	 * @date Agosto 2024
	 	 * @author Yair LizÃƒÂ¡rraga
	 	 */
		public void ClicBotonDescargarExcel() throws InterruptedException {
		u.scrollToElement(xmlDescargarExcel);
		u.highLight(xmlDescargarExcel);
		testCase.addScreenShotCurrentStep(u, "clickContainer");
		u.click(xmlDescargarExcel);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep(u, "clickContainer");
			 	}
	 	
		/**
	 	 * Metodo para validar la descargar PDF de un reporte
	 	 * @date Septiembre 2024
	 	 * @author Yair LizÃƒÂ¡rraga
	 	 */
	 	public Boolean validarDescargaResumen() {
			boolean exist = false;
			String home = System.getProperty("user.home");
			home = home + "\\Downloads";
			String expectedFileNamePart = "OC_";//Nombre como referencia
			String pathYourSystem = getLastModified(home).getAbsolutePath();
			File f = new File(pathYourSystem);
			if(pathYourSystem.contains(expectedFileNamePart) && pathYourSystem.endsWith(".xlsx") && f.exists()){
				exist = true;
			}
            return exist;
	 	}
	 	
		/**
	 	 * Metodo para validar la descargar PDF de un reporte
	 	 * @date Septiembre 2024
	 	 * @author Yair LizÃƒÂ¡rraga
	 	 */
		public Boolean validarDescargaComprobantePDF() {
			boolean exist = false;
			String home = System.getProperty("user.home");
			home = home + "\\Downloads";
			String expectedFileNamePart = "OC";//Nombre como referencia
			String pathYourSystem = getLastModified(home).getAbsolutePath();
			File f = new File(pathYourSystem);
			if(pathYourSystem.contains(expectedFileNamePart) && pathYourSystem.endsWith(".pdf") && f.exists()){
				exist = true;
			}
	    	return exist;

		}
	/**
	 * Metodo para validar la descargar masiva de reportes Pdf
	 * @date Septiembre 2024
	 * @author Jorge Hernandez
	 */
	public Boolean validarDescargaComprobanteMasivaPDF() {
		boolean exist = false;
		String home = System.getProperty("user.home");
		home = home + "\\Downloads";
		String expectedFileNamePart = "Concentrado_OC";//Nombre como referencia
		String pathYourSystem = getLastModified(home).getAbsolutePath();
		File f = new File(pathYourSystem);
		if(pathYourSystem.contains(expectedFileNamePart) && pathYourSystem.endsWith(".zip") && f.exists()){
			exist = true;
		}
		return exist;

	}
			/**
			* Metodo para dar busca por cr
			* @date Agosto 2024
			* @author Yair Lizarraga
			*/
			public void BuscarCRPlaza(String crplaza) {
			u.hardWait(3);
			u.highLight(xmlCRPlaza);
			WebDriver driver = u.getDriver();
            Actions actions = new Actions(driver);
            u.hardWait(2);
            u.click(xmlCRPlaza);
            actions.sendKeys(crplaza).perform();
            u.unhighLight(xmlCRPlaza);
			}
			
			/**
			* Metodo para dar clic en el botn limpia filtros
			* @date Agosto 2024
			* @author Yair Lizarraga
			*/
			public Boolean ClicLimpiaFiltro() {
			    boolean isValid = true; // Inicializamos la bandera en true

			    try {
					u.highLight(xmlLimpiaFiltros);
					u.click(xmlLimpiaFiltros);
					testCase.addScreenShotCurrentStep(u, "LimpiaFiltros");
			    } catch (Exception e) {
			        isValid = false; // Si ocurre una excepcin, la bandera se pone en false
			    }

			    return isValid; // Devolvemos la bandera
			}
		 	
			/**
			* Metodo para dar clic en el botn Ver fue
			* @date Agosto 2024
			* @author Yair Lizarraga
			*/
			public Boolean ClicVerFue() {
			    boolean isValid = true; // Inicializamos la bandera en true
			    try {
			    	u.scrollToElement(xmlVerFue);
					u.highLight(xmlVerFue);
					testCase. addScreenShotCurrentStep(u, "Cerrar Tira existe");
					u.click(xmlVerFue);
					u.hardWait(5);
					testCase.addScreenShotCurrentStep(u, "Verfue");
			    } catch (Exception e) {
			        isValid = false; // Si ocurre una excepci n, la bandera se pone en false
			    }

			    return isValid; // Devolvemos la bandera
			}
			/**
			* Metodo para dar clic en el boton Ver tira de transferencia
			 * @date Agosto 2024
			* @author Yair Lizarraga
			*/
			public Boolean ClicVerTransferencia() {
			    boolean isValid = true; // Inicializamos la bandera en true

			    try {
					u.highLight(xmlVerTransferencia);
					u.click(xmlVerTransferencia);
					u.hardWait(5);
					testCase.addScreenShotCurrentStep(u, "VerTransferencia");
			    } catch (Exception e) {
			        isValid = false; // Si ocurre una excepcion, la bandera se pone en false
			    }

			    return isValid; // Devolvemos la bandera
			}
			/**
			* Metodo para dar clic en el botn Ver tira de transferencia
			* @date Agosto 2024
			* @author Yair Lizarraga
			*/
			public Boolean ClicVerEvidenciaFotografica() {
			    boolean isValid = true; // Inicializamos la bandera en true

			    try {
					u.highLight(xmlVerFotograficas);
					u.click(xmlVerFotograficas);
					u.hardWait(5);
					testCase.addScreenShotCurrentStep(u, "xmlVerFotograficas");
			    } catch (Exception e) {
			        isValid = false; // Si ocurre una excepci n, la bandera se pone en false
			    }

			    return isValid; // Devolvemos la bandera
			}

			/**
		 	 * Metodo para validar la existencia de los filtros
		 	 * @date Agosto 2024
		 	 * @author Yair LizÃƒÂ¡rraga
		 	 */
			public Boolean validarFiltrosDirectos() { 
			    boolean isValid = true; // Inicializamos la bandera en true 

			    try {
					encontrarElemento(xmlProveedor);
			        encontrarElemento(xmlFuenteDeAbasto);
			        encontrarElemento(xmlZona); 
			        encontrarElemento(xmlRegion); 
			        encontrarElemento(xmlCRPLazaDirectos); 
			        encontrarElemento(xmlCRTienda); 
			        encontrarElemento(xmlRangoFechas); 
			    } catch (Exception e) {
			        isValid = false; // Si ocurre una excepci n, la bandera se pone en false
			    }

			    return isValid; // Devolvemos la bandera 
			}
			/**
			* Metodo para dar clic en el botn de radio button excel
			* @date Agosto 2024
			* @author Yair Lizarraga
			*/
			public boolean ClicExcelMasivo() {
				boolean result;
				if(u.isDisplayed(xmlRadioButtonExcelMasivo)){
					u.highLight(xmlRadioButtonExcelMasivo);
					u.click(xmlRadioButtonExcelMasivo);
					u.hardWait(5);
					testCase.addScreenShotCurrentStep(u, "ExcelMasivo");

					result = true;
				} else {
					result = false;
				}
				return result;
			}
			
			/**
			* Metodo para dar clic en el botn de radio button PDF
			 * @date Agosto 2024
			* @author Yair Lizarraga
			*/
			public void ClicPDFMasivo() {
			u.highLight(xmlRadioButtonPDFMasivo);
			u.click(xmlRadioButtonPDFMasivo);
			u.hardWait(5);
			testCase.addScreenShotCurrentStep(u, "PDFMasivo");
			}

			/**
			* Metodo para dar clic en el boton de radio button PDF
			 * @date Agosto 2024
			* @author Yair Lizarraga
			*/
			public boolean clicDescargaMasiva() {
				boolean result;
				if(u.isDisplayed(xmlDescargarMasivo)){
					u.highLight(xmlDescargarMasivo);
					u.click(xmlDescargarMasivo);
					u.hardWait(5);
					testCase.addScreenShotCurrentStep(u, "Descarga Masiva");
					result = true;
				} else {
					result = false;
				}

				return result;
			}

			/**
		 	 * Metodo para dar clic en el boton de limpiar filtros
		 	 * @date Agosto 2024
		 	 * @author Yair LizÃƒÂ¡rraga
		 	 */
			public Boolean clicLimpiarFiltros() {
			    boolean isValid = true; // Inicializamos la bandera en true

			    try {
					u.highLight(xmlLimpiarFiltros);
					u.click(xmlLimpiarFiltros);
					u.hardWait(5);
					testCase.addScreenShotCurrentStep(u, "Limpiar Filtros");
			    } catch (Exception e) {
			        isValid = false; // Si ocurre una excepcin, la bandera se pone en false
			    }

			    return isValid; // Devolvemos la bandera
			}

			/**
		 	 * Metodo para dar clic en el boton editar FAQ
		 	 * @date Agosto 2024
		 	 * @author Yair LizÃƒÂ¡rraga
		 	 */
			public void clicEditarFAQ() {
				u.highLight(xmlEditarFAQ);
				u.click(xmlEditarFAQ);
				u.hardWait(5);
				testCase.addScreenShotCurrentStep(u, "EditarFAQ");
				}
			/**
		 	 * Metodo para dar clic en el boton guardar FAQ
		 	 * @date Agosto 2024
		 	 * @author Yair LizÃƒÂ¡rraga
		 	 */
			public void clicGuardarFAQ() {
				u.highLight(xmlGuardarEdicion);
				u.click(xmlGuardarEdicion);
				u.hardWait(5);
				testCase.addScreenShotCurrentStep(u, "xmlGuardarEdicion");
				}
			/**
		 	 * Metodo para dar clic en el boton eliminar FAQ
		 	 * @date Agosto 2024
		 	 * @author Yair LizÃƒÂ¡rraga
		 	 */
			public Boolean clicEliminarFAQ() {
			    boolean isValid = true; // Inicializamos la bandera en true

			    try {
					u.highLight(xmlEliminarFAQ);
					u.click(xmlEliminarFAQ);
					u.hardWait(5);
					testCase.addScreenShotCurrentStep(u, "xmlGuardarEdicion");
					u.highLight(xmlConfimarEliminar);
					u.click(xmlConfimarEliminar);
			    } catch (Exception e) {
			        isValid = false; // Si ocurre una excepci n, la bandera se pone en false
			    }

			    return isValid; // Devolvemos la bandera
			}

			  	/** Metodo para validar region en grid
				 * @date Agosto 2024
			     * @author Jorge Hernandez
			     */
			    public boolean validarRegion(){
					boolean result = false;
			        u.hardWait(6);
			        int nRegion = u.listElements(xmlRegionMexico).size();
			        if(nRegion > 0){
			            u.highLight(xmlRegionMexcicoOp1);
						if(u.getText(xmlRegionMexcicoOp1).equals("Mexico")){
							testCase.addScreenShotCurrentStep("Validacion de comprobantes nivel nacional");
							result =  true;
						}
			        } else {
						result =  false;
					}
					return result;
			    }

			    /**
			     * Metodo para validar lista de proveedores en Directos
				 * @date Agosto 2024
			     * @author Jorge Hernandez
			     */
			    public boolean validarProveedoresDirectos(){
					boolean result;

			        u.hardWait(4);
					if(u.isDisplayed(xmlProveedor)){
						u.highLight(xmlProveedor);
						u.click(xmlProveedor);
						u.unhighLight(xmlProveedor);
						result = true;
					} else {
						result = false;
					}

					u.hardWait(4);

			        u.waitWebElement(xmlProveedorOptions, 20, 2);
					if(u.isDisplayed(xmlProveedorOptions)){
						u.highLight(xmlProveedorOptions);
						testCase.addScreenShotCurrentStep("Validacion listado proveedores directos");
						u.unhighLight(xmlProveedorOptions);
						result = true;
					} else {
						result = false;
					}

					return result;

			    }

			    /**
			     * Metodo para Seleccionar el portal Cometa en Access Control
				 * @date Agosto 2024
			     * @author Jorge Hernandez
			     */
			    public boolean seleccionarPortalCometaQA(){
			    	u.waitForLoadPage();
			    	u.hardWait(5);
					u.waitWebElement(xmlPortalCometa, 120, 3); //De esta manera se puede modificar el tiempo de espera despues de el login por temas verificacion
					u.highLight(xmlPortalCometa);
					testCase.addScreenShotCurrentStep(u, "Portal Cometa Pantalla");
					u.click(xmlPortalCometa);
					u.waitForLoadPage();
					u.hardWait(10);
					testCase.addScreenShotCurrentStep("Portal Cometa");
					return true;


			    }

			    /**
			     * Metodo para seleccionar el apartado de Directos en Cometa
				 * @date Agosto 2024
			     * @author Jorge Hernandez
			     */
			    public boolean clicBotonCometaDirectos() {
			    	u.hardWait(5);
					if(u.isDisplayed(xmlaDirectos)){
						u.highLight(xmlaDirectos);
						testCase.addScreenShotCurrentStep(u, "Pantalla Directos");
						u.click(xmlaDirectos);
						testCase.addScreenShotCurrentStep(u, "Portal Cometa Directos Pantalla");

						return true;
					} else {
						return false;
					}
			    }

			    /**
			     * Metodo para validar los filtros de la pagina de comprobantes
				 * @date Agosto 2024
			     * @author Jorge Hernandez
			     */
			    public boolean validarFiltrosExistentesComprobantesCompra(){
					boolean result;
					if(u.isDisplayed(xmlProveedor)){
						u.highLight(xmlProveedor);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlFuenteAbasto)){
						u.highLight(xmlFuenteAbasto);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlZona)){
						u.highLight(xmlZona);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlRegion)){
						u.highLight(xmlRegion);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlCRPlaza)){
						u.highLight(xmlCRPlaza);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlCRTienda)){
						u.highLight(xmlCRTienda);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlRangoFechas)){
						u.highLight(xmlRangoFechas);
						result = true;
					} else {
						result = false;
					}

					if(result){
						testCase.addScreenShotCurrentStep(u, "Validacion de Filtros en la pantalla");
					}

			        u.unhighLight(xmlProveedor);
			        u.unhighLight(xmlFuenteAbasto);
			        u.unhighLight(xmlZona);
			        u.unhighLight(xmlRegion);
			        u.unhighLight(xmlCRPlaza);
			        u.unhighLight(xmlCRTienda);
			        u.unhighLight(xmlRangoFechas);

					return result;
			    }

			    /**
			     * Metodo para seleccionar y validar la consulta mediante la fecha
				 * @date Noviembre 2024
			     * @author Jorge Hernandez
			     */
			    public boolean seleccionarCaracteristicaFiltradoValidacionFecha(int meses){
					boolean result;
			        u.mouseOver(xmlFuenteAbasto);
					if(u.isDisplayed(xmlFuenteAbasto)){
						u.highLight(xmlFuenteAbasto);
						u.mouseOver(xmlFuenteAbasto);
						u.click(xmlFuenteAbasto);
						u.unhighLight(xmlFuenteAbasto);
						u.scrollToElement(xmlFuenteAbastoOption); //Se debe seleccionar algo mas para buscar por fechas, en este caso elegi FA
						u.click(xmlFuenteAbastoOption);
						result = true;
					} else {
						result = false;
					}

					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)){
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor de i para cuantos meses atras seleccionar fecha
							u.click(xmlBotonFlechaIzquierda);
						}

						if(u.isDisplayed(xmlFechaDiaActual)){
							u.highLight(xmlFechaDiaActual);
							u.mouseOver(xmlFechaDiaActual);
							u.click(xmlFechaDiaActual);
							u.unhighLight(xmlFechaDiaActual);
						}

						if(u.isDisplayed(xmlFechaDiaSiguiente)){
							u.highLight(xmlFechaDiaSiguiente);
							u.mouseOver(xmlFechaDiaSiguiente);
							u.click(xmlFechaDiaSiguiente);
						}

						consultarComprobantes();

						boolean date1, date2;
						try{
							date1 = u.isDisplayed(xmlValidacionFiltradoFechaActual);
						} catch (Exception e) {
							date1 = false;
						}

						try{
							date2 = u.isDisplayed(xmlValidacionFiltradoFechaSiguente);
						} catch (Exception e) {
							date2 = false;
						}
						try {

							if(u.isDisplayed(xmlGridOption1Fecha)){
								if(date1){
									u.highLight(xmlValidacionFiltradoFechaActual);
									testCase.addScreenShotCurrentStep(u, "Validacion Filtrado");
									u.unhighLight(xmlValidacionFiltradoFechaActual);
								}
								if(date2){
									u.highLight(xmlValidacionFiltradoFechaSiguente);
									testCase.addScreenShotCurrentStep(u, "Validacion Filtrado");
									u.unhighLight(xmlValidacionFiltradoFechaSiguente);
								}
							}

						} catch (Exception e) {
							throw new RuntimeException(e);
						}

						result = true;
					} else {
						result = false;
					}

					return result;
			    }

			    /**
			     * Metodo para consultar con fuente de abastos y una fecha y mostrar los resultados en pantalla Directos
				 * @param meses toma el valor de la cantidad de meses a retroceder para obtener resultados en la consulta
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean seleccionarCaracteristicaFiltrado(int meses){
					boolean result = false;
					u.hardWait(1);
					if(u.isDisplayed(xmlFuenteAbasto)){
						u.click(tbPlaza);
			             u.isDisplayed(lstFir);
			             u.click(lstFir);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlRangoFechas)){
						u.highLight(xmlRangoFechas);
						u.mouseOverAndClick(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlBotonFlechaIzquierda)){
						u.highLight(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor de i para cuantos meses atras seleccionar fecha
							u.mouseOverAndClick(xmlBotonFlechaIzquierda);
						}

						u.highLight(xmlPrimerDiaHabilidato);
						u.mouseOver(xmlPrimerDiaHabilidato);
						u.click(xmlPrimerDiaHabilidato);
						u.unhighLight(xmlPrimerDiaHabilidato);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}else if(u.isDisplayed(xmlFechaFinalMes30)){
							u.highLight(xmlFechaFinalMes30);
							u.mouseOver(xmlFechaFinalMes30);
							u.click(xmlFechaFinalMes30);
						} else if (u.isDisplayed(xmlFechaFinalMes28)) {
							u.highLight(xmlFechaFinalMes28);
							u.mouseOver(xmlFechaFinalMes28);
							u.click(xmlFechaFinalMes28);
						}

					}

					if(result){
						consultarComprobantes();
						testCase.addScreenShotCurrentStep(u, "Consulta con filtros");
					}

					return result;
			    }

			    /**
			     * Metodo para selecionar la fehca de el dia de manana junto con un FA directos
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean seleccionarfechaManana(){
					boolean result;

			        u.mouseOver(xmlFuenteAbasto);
					if(u.isDisplayed(xmlFuenteAbasto)){
						u.mouseOverAndClick(xmlFuenteAbasto);
						u.scrollToElement(xmlFuenteAbastoOption);
						u.click(xmlFuenteAbastoOption);

						result = true;
					} else {
						result = false;
					}

			        u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)){
						u.highLight(xmlRangoFechas);
						u.mouseOverAndClick(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlBotonFlechaDerecha)){
						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOverAndClick(xmlBotonFlechaDerecha);
						result = true;
					} else {
						result = false;
					}


			        u.mouseOver(xmlFechaDiaActual);
					if(u.isDisplayed(xmlFechaDiaActual)){
						u.highLight(xmlFechaDiaActual);
						u.mouseOverAndClick(xmlFechaActual);
						testCase.addScreenShotCurrentStep("Seleccion fecha dia actual");
						u.unhighLight(xmlFechaDiaActual);

						result = true;
					} else {
						result = false;
					}

			        u.mouseOver(xmlFechaDiaSiguiente);
					if(u.isDisplayed(xmlFechaDiaSiguiente)){
						u.highLight(xmlFechaDiaSiguiente);
						testCase.addScreenShotCurrentStep("Seleccion fecha dia siguiente");
						u.mouseOverAndClick(xmlFechaDiaSiguiente);

						result = true;
					} else {
						result = false;
					}

					return result;
			    }

			    /**
			     * Metodo para validar visibilidad de boton consultar en directos y realizar la consulta
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean consultarComprobantes(){
						u.mouseOver(xmlConsultar);
					if(u.isDisplayed(xmlConsultar)){
						u.highLight(xmlConsultar);
						u.click(xmlConsultar);
						u.unhighLight(xmlConsultar);
			        	u.hardWait(20); //TIempo de espera para reflejar la busqueda en el grid

						testCase.addScreenShotCurrentStep(u, "Busqueda completada");
						return true;
					} else {
						return false;
					}
			    }

			    /**
			     * Metodo para realizar una consulta en Busqueda historica de Cedis
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean consultarCedisBusquedaHistorica() {
			        boolean result = false;
			        if (u.isDisplayed(xmlConsultarBusqHistorica1)) {
			            u.highLight(xmlConsultarBusqHistorica1);
			            u.click(xmlConsultarBusqHistorica1);
			            u.unhighLight(xmlConsultarBusqHistorica1);
			            u.hardWait(5);
			            testCase.addScreenShotCurrentStep(u, "Consulta realizada");
			            result = true;
			        }

			        return result;
			    }

			    /**
			     * Metodo para realizar la limpieza de los filtros
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean limpiarFiltros(){
					boolean result = false;
					u.mouseOver(xmlLimpiarFiltros);
					if(u.isDisplayed(xmlLimpiarFiltros)){
						u.highLight(xmlLimpiarFiltros);
						u.mouseOver(xmlLimpiarFiltros);
						testCase.addScreenShotCurrentStep(u, "Limpiado De Filtro(s)");
						u.click(xmlLimpiarFiltros);
						u.hardWait(4);
						u.unhighLight(xmlLimpiarFiltros);
						testCase.addScreenShotCurrentStep(u, "Filtros limpios");
						result = true;
					}
					return result;
			    }

			    /**
			     * Metodo para validar la limpieza de un campo, en este caso el de Fuente Abasto
			     * @author Jorge Hernandez
				 * @date Septiembre 2024
			     */
			    public boolean validacionLimpiezaCampoFA(){
					boolean result = false;
					if(u.isDisplayed(xmlFuenteAbasto)){
						result = true;
					} else {
						result = false;
					}

					if(u.getText(xmlFuenteAbasto).isEmpty()){
						result = true;
					} else {
						result = false;
					}

					return result;
			    }

			    /**
			     * Metodo para validar la Fuente de abastos en gird dado un FA en filtros
				 * @param meses toma el valor de la cantidad de meses a retroceder para obtener resultados en la consulta
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean validacionFAYZona(int meses){
			        u.hardWait(2);
					boolean result;

					if(u.isDisplayed(xmlFuenteAbasto)){
						u.mouseOver(xmlFuenteAbasto);
						u.mouseOverAndClick(xmlFuenteAbasto);
						u.scrollToElement(xmlFuenteAbastoOption); //Se debe seleccionar algo mas para buscar por fechas, en este caso elegi FA
						u.click(xmlFuenteAbastoOption);
						result = true;
					}

					if(u.isDisplayed(xmlRangoFechas)){
						u.highLight(xmlRangoFechas);
						u.mouseOverAndClick(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						if(u.isDisplayed(xmlBotonFlechaIzquierda)){
							u.highLight(xmlBotonFlechaIzquierda);
							for (int i = 0; i < meses; i++) { // valor de i para cuantos meses atras seleccionar fecha
								u.mouseOverAndClick(xmlBotonFlechaIzquierda);
							}
						}

						if(u.isDisplayed(xmlFechaDiaActual)){
							u.highLight(xmlFechaDiaActual);
							u.mouseOver(xmlFechaDiaActual);
							u.click(xmlFechaDiaActual);
							u.unhighLight(xmlFechaDiaActual);
						}

						if(u.isDisplayed(xmlFechaDiaSiguiente)){
							u.highLight(xmlFechaDiaSiguiente);
							u.mouseOver(xmlFechaDiaSiguiente);
							u.click(xmlFechaDiaSiguiente);
						}

						result = true;
					} else {
						result = false;
					}

			        consultarComprobantes();

			        u.hardWait(4);
			        u.waitWebElement(xmlFuenteAbastoValidacion, 20, 2);
					if(u.isDisplayed(xmlFuenteAbastoValidacion)){
						u.highLight(xmlFuenteAbastoValidacion);
						testCase.addScreenShotCurrentStep(u, "Validacion Fuente Abastos");
						u.unhighLight(xmlFuenteAbastoValidacion);

						result = true;
					} else {
						result = false;
					}

					return  result;

			    }


				/**
	 			* Metodo para seleccionar rango de fechas para busqueda
	 			* @param meses toma el valor de la cantidad de meses a retroceder para obtener resultados en la consulta
	 			* @author Jorge Hernandez
				* @date Octubre 2024
	 			*/
				public boolean seleccionRangoFechas(int meses){
					boolean result = false;

					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlPrimerDiaHabilidato)){
							u.highLight(xmlPrimerDiaHabilidato);
							u.mouseOver(xmlPrimerDiaHabilidato);
							u.click(xmlPrimerDiaHabilidato);
							u.unhighLight(xmlPrimerDiaHabilidato);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}
						u.unhighLight(xmlBotonFlechaDerecha);
						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}
						testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

						result = true;
					}

					return result;
				}

			    /**
			     * Metodo para validar mensaje en directos al no encontrar registros
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean validarNoinfoCometaBusqueda(){
					boolean result;

					if(u.isDisplayed(xmlCometaDirectosCPNoInfo)){
						u.highLight(xmlCometaDirectosCPNoInfo);
						testCase.addScreenShotCurrentStep(u, "Validacion Mensaje No Informacion");

						result = true;
					} else {
						result = false;
					}

					return result;
			    }

			    /**
			     * Metodo para validar que sea visible el boton de descarga en Directos, comprobante de compras y realizar la acion de click
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean validarBotonDescargaComprobantesCompra(){
					boolean result;
			        u.scrollToElement(xmlBotonDescargar);
					if(u.isDisplayed(xmlBotonDescargar)){
						u.highLight(xmlBotonDescargar);
						testCase.addScreenShotCurrentStep(u, "Validacion Boton descarga Comprobantes");
						u.click(xmlBotonDescargar);
						u.hardWait(5);

						result = true;
					} else {
						result = false;
					}
					return result;
			    }

			    /**
			     * Metodo para seleccionar el primer comprobante de compra en el grid de Comprobantes de compras en Directos
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean seleccionarPrimerComprobanteCompra(){
					boolean result;
					u.waitWebElement(xmlPrimerComprobanteFiltro, 30, 2);
					if(u.isDisplayed(xmlPrimerComprobanteFiltro)){
						u.highLight(xmlPrimerComprobanteFiltro);
						u.click(xmlPrimerComprobanteFiltro);
						u.hardWait(10); //se necesita para que se cargue la pantalla de detalles del comprobante de compra
						testCase.addScreenShotCurrentStep(u, "Detalles Comprobante Compra");
						result = true;
					} else {
						result = false;
					}

					return result;
			    }

			    /**
			     * Metodo para realizar la descarga de los detalles de comprobantes de compras en Directos-Comprobantes de compra como PDF
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean descargarDetallesComprobantePdf(){
					boolean result;
			        u.scrollToElement(xmlDescargaPdfDetallesComprobante);
					if(u.isDisplayed(xmlDescargaPdfDetallesComprobante)){
						u.highLight(xmlDescargaPdfDetallesComprobante);
						testCase.addScreenShotCurrentStep(u, "Boton descarga Pdf Comprobante");
						u.click(xmlDescargaPdfDetallesComprobante);
						u.unhighLight(xmlDescargaPdfDetallesComprobante);
						u.hardWait(11);//cambiar el wait dependiendo de lo que tarde en realizar la descarga

						result = true;
					} else {
						result = false;
					}
					return result;
			    }

			    /**
			     * Metodo para realizar la descarga de los detalles de comprobantes de compras en Directos-Comprobantes de compra como Excel
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean descargarDetallesComprobanteExcel(){
					boolean result = false;
			        u.hardWait(3);
			        if(u.isDisplayed(xmlPrimerComprobanteFiltro)){
						u.highLight(xmlDescargarExcelDetalleComprobante);
						testCase.addScreenShotCurrentStep(u, "Boton descarga Excel Comprobante");
						u.click(xmlDescargarExcelDetalleComprobante);
						u.unhighLight(xmlDescargarExcelDetalleComprobante);
						u.hardWait(15); //cambiar el wait dependiendo de lo que tarde en realizar la descarga
						result = true;
					} else {
						result = false;
					}
					return result;
			    }

			    /**
			     * Metodo para realizar el filtrado de Comprobantes en Directos usando FA
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public void filtradoComprobantesFA(){
			        u.highLight(xmlFuenteAbasto);
			        u.click(xmlFuenteAbasto);

			        u.click(xmlFuenteAbastoOption);

			        consultarComprobantes();

			        testCase.addScreenShotCurrentStep(u, "Filtrado Comprobantes por FA");
			    }

			    /**
			     * Metodo para realizar la descarga massiva de los comporbantes de directos como Excel
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean descargaComprobantesEntregaMasivaExcel(){
					boolean result = false;
			        u.scroll(15);
//						u.scrollToElement(xmlDescargaMasivaExcel);
					if(u.isDisplayed(xmlDescargaMasivaExcel)){
						u.mouseOver(xmlDescargaMasivaExcel);
						u.highLight(xmlDescargaMasivaExcel);
						u.click(xmlDescargaMasivaExcel);
						u.unhighLight(xmlDescargaMasivaExcel);

						result = true;
					} else {
						result = false;
					}

					u.mouseOver(xmlBotonDescargaMasiva);
					if(u.isDisplayed(xmlBotonDescargaMasiva)){
						u.highLight(xmlBotonDescargaMasiva);
						testCase.addScreenShotCurrentStep(u, "Boton Descarga Masiva Excel");
						u.click(xmlBotonDescargaMasiva);
						u.hardWait(10); //se necesita para el tiempo de la descarga

						result = true;
					} else {
						result = false;
					}

					return result;
			    }

			    /**
			     * Metodo para realizar la descarga massiva de los comporbantes de directos como PDF
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean descargaComprobantesEntregaMasivaPdf(){
					boolean result = false;
			        u.scroll(20);
//						u.scrollToElement(xmlDescargaMasivaPdf);
					if(u.isDisplayed(xmlDescargaMasivaPdf)){
						u.highLight(xmlDescargaMasivaPdf);
						u.click(xmlDescargaMasivaPdf);
						u.unhighLight(xmlDescargaMasivaPdf);

						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlBotonDescargaMasiva)){
						u.highLight(xmlBotonDescargaMasiva);
						testCase.addScreenShotCurrentStep(u, "Boton Descarga Masiva Pdf");
						u.click(xmlBotonDescargaMasiva);

						result = true;
					} else {
						result = false;
					}

			        u.hardWait(8); //para validar que se logre descargar el archivo

					return result;
			    }

			    /**
			     * Metodo para validar el boton de inicio en Comprobantes de compras Directos
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean validacionBotonInicioCP(){
					boolean result = false;
			        u.hardWait(2);
					if(u.isDisplayed(xmlInicio)){
						u.highLight(xmlInicio);
						testCase.addScreenShotCurrentStep(u, "Validacion Boton Inicio Comprobante Compra");
						u.click(xmlInicio);
						u.unhighLight(xmlInicio);
						testCase.addScreenShotCurrentStep(u, "Validacion redireccion Inicio Cometa");

						result = true;
					} else {
						result = false;
					}
					return  result;
			    }

			    /**
			     * Metodo para realizar la accion de click en el boton de navegacion de Reportes-Directos
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean ClickBotonNavDirectosReportes(){
					boolean result;
			        u.hardWait(2);
			        if(u.isDisplayed(xmlNavDirectos)){
						u.highLight(xmlNavDirectos);
						u.click(xmlNavDirectos);
						u.unhighLight(xmlNavDirectos);

						result = true;
					} else {
						result = false;
					}

			        if(u.isDisplayed(xmlNavDirectosReportes)){
						u.highLight(xmlNavDirectosReportes);
						testCase.addScreenShotCurrentStep(u, "Boton Reportes Home");
						u.click(xmlNavDirectosReportes);

						result = true;
					} else {
						result = false;
					}

					return result;
			    }

			    /**
			     * Metodo para validar el mensaje de la pantalla de Reportes Directos
			     * @author Jorge Hernandez
				 * @date Septiembre 2024
			     */
			    public boolean validacionMensajeReporteDirectos(){
					boolean result = false;
			        u.hardWait(2);
			        if(u.isDisplayed(xmlReporteDirectosMsg)){
						testCase.addScreenShotCurrentStep(u, "Validacion Mensaje Reportes Directos");
						result = true;
					}

					return result;
			    }

			    /**
			     * Metodo para navegar a la pagina de Cedis
			     * @author Jorge Hernandez
				 * @date Septiembre 2024
			     */
			    public boolean navegarCometaCedis(){
					boolean result;
			        if(u.waitWebElement(xmlCedisOption, 30, 2).isDisplayed()){
						u.highLight(xmlCedisOption);
						testCase.addScreenShotCurrentStep("Seleccion opcion Cedis");
						u.click(xmlCedisOption);
						u.waitForLoadPage();

						result = true;
					} else {
						result = false;
					}
					return result;
			    }

			    /**
			     * Metodo para ingresar a la pantalla de Transferencia en la pantalla de Cedis
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean cedisRecepciondTransferyDev(){
					boolean result;
		        	if(u.isDisplayed(xmlRecepciondTransferyDev)){
						u.highLight(xmlRecepciondTransferyDev);
						testCase.addScreenShotCurrentStep(u, "Pantalla Ceids");
						u.click(xmlRecepciondTransferyDev);
						u.waitForLoadPage();
						testCase.addScreenShotCurrentStep(u, "Ingresar Cedis Recepcion");

						result = true;
					} else {
						result = false;
					}
			        return result;
			    }

			    /**
			     * Metodo para realizar el filtrado en cedis usando unas opciones predeterminadas para mostrar informacion
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean FiltradoCedisPlaza(){ //Metodo para realizar Busqueda rapida con fechas para mostrar botones de descarga en CEDIS
					boolean result;
					u.hardWait(5);
			        if(u.isDisplayed(xmlCedisCRPlaza)){
						u.highLight(xmlCedisCRPlaza);
						u.click(xmlCedisCRPlaza);
						u.unhighLight(xmlCedisCRPlaza);
						u.click(xmlCRPlazaOptionMTYNorte);
						u.highLight(xmlCRPlazaOptionMTYNorte);
						u.click(xmlCRPlazaOption2);
						u.highLight(xmlCRPlazaOption2);
						result = true;
					} else {
						result = false;
					}
					return result;
			    }
			    /**
			     * Metodo para realizar el filtrado en cedis usando unas opciones predeterminadas para mostrar informacion
				 * @param meses toma el valor de la cantidad de meses a retroceder para obtener resultados en la consulta
			     * @author Yair Lizarraga
				 * @date Agosto 2024
			     */
			    public boolean FiltradoCedisMeses(int meses){ //Metodo para realizar Busqueda rapida con fechas para mostrar botones de descarga en CEDIS
					boolean result;
					u.hardWait(5);
			        if(u.isDisplayed(xmlRangoFechasCedisRecep)){
						u.highLight(xmlRangoFechasCedisRecep);
						u.mouseOverAndClick(xmlRangoFechasCedisRecep);
						u.unhighLight(xmlRangoFechasCedisRecep);

						u.highLight(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) {
							u.mouseOverAndClick(xmlBotonFlechaIzquierda);
						}

						u.highLight(xmlFechaInicioMes);
						u.mouseOverAndClick(xmlFechaInicioMes);
						u.unhighLight(xmlFechaInicioMes);


						u.mouseOverAndClick(xmlBotonFlechaDerecha);
						u.mouseOverAndClick(xmlBotonFlechaDerecha);
						u.mouseOverAndClick(xmlBotonFlechaDerecha);
						u.mouseOverAndClick(xmlBotonFlechaDerecha);

						u.highLight(xmlFechaInicioMes);
						u.mouseOverAndClick(xmlFechaInicioMes);
						u.unhighLight(xmlFechaInicioMes);
						testCase. addScreenShotCurrentStep(u, "Cerrar Tira existe");
						result = true;
					} else {
						result = false;
					}

					if(result){
						consultarCedisBusquedaHistorica();
						testCase. addScreenShotCurrentStep(u, "Cerrar Tira existe");
					}
					return result;
			    }

			    /**
			     * Metodo para realizar la accion de descargar con la opcion de Fue en Cedis
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean cedisDescargaMasivaFUE(){
					boolean result;
					if(u.isDisplayed(xmlDescargaFUE)){
						u.scrollToElement(xmlDescargaFUE);
						u.highLight(xmlDescargaFUE);
						testCase.addScreenShotCurrentStep(u, "Boton Descarga Masiva FUE");
						u.click(xmlDescargaFUE);
						u.hardWait(15);

						result = true;
					} else {
						result = false;
					}
			        return result;
			    }

			    /**
			     * Metodo para realizar la accion de descargar con la opcion de Tira de transferencia en Cedis
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean cedisDescargaMasivaTiraTransf(){
					boolean result;
			        u.scrollToElement(xmlDescargaTiraTransf);
					if(u.isDisplayed(xmlDescargaTiraTransf)){
						u.highLight(xmlDescargaTiraTransf);
						testCase.addScreenShotCurrentStep(u, "Boton Descarga Masiva Tira Transferencia");
						u.click(xmlDescargaTiraTransf);
						u.hardWait(15);

						result = true;
					} else {
						result = false;
					}
					return result;
			    }

			    /**
			     * Metodo para validar el menu de CRPlaza en Cedis y ver el listado de opciones que brinda
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean validarMenuCRPlaza(){
					boolean result;
			        if(u.isDisplayed(xmlCedisCRPlaza)){
						u.highLight(xmlCedisCRPlaza);
						u.click(xmlCedisCRPlaza);
						u.unhighLight(xmlCedisCRPlaza);

						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlCedisCRPlazaOptions)){
						u.highLight(xmlCedisCRPlazaOptions);
						result = true;
					} else {
						result = false;
					}

					if(result){
						testCase.addScreenShotCurrentStep(u, "Validacion de filtro de CR Plaza");
					}

					return result;
			    }

			    /**
			     * Metodo para validar menu de CR Tienda en Cedis y ver el listado de opciones que brinda
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean validarMenuCRTienda(){
			        boolean result;
					if(u.isDisplayed(xmlCedisCRPlaza)){
						u.highLight(xmlCedisCRPlaza);
						u.click(xmlCedisCRPlaza);
						u.unhighLight(xmlCedisCRPlaza);
						u.scrollToElement(xmlCedisCRPlazaOption);
						u.highLight(xmlCedisCRPlazaOption);
						u.click(xmlCedisCRPlazaOption);

						result = true;
					} else {
						result = false;
					}

			        if(u.isDisplayed(xmlCedisCRTienda)){
						u.highLight(xmlCedisCRTienda);
						u.click(xmlCedisCRTienda);
						u.unhighLight(xmlCedisCRTienda);
						u.hardWait(2);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlCedisCRTiendaOptions)){
						u.highLight(xmlCedisCRTiendaOptions);
					}

					if(result){
						testCase.addScreenShotCurrentStep(u, "Validacion de filtro de CR Tienda");
					}
					return result;
			    }

			    /**
			     * Metodo para validar solapa desahabilitada
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public void validarSolapaDeshabilitada(){ //Cedis-Reporte Boton consultar en Busqueda Historica
			        u.isDisplayed(xmlConsultarHistoricaDesactivado); //Verificar que el boton esta deshabilitado/desactivado por medio de un xpath validando el atrinuto disabled
			        u.highLight(xmlConsultarHistoricaDesactivado);
			        testCase.addScreenShotCurrentStep(u, "Validacion solapa deshabilitada");
			        u.unhighLight(xmlConsultarHistoricaDesactivado);
			    }

			    /**
			     * Metodo para validar solapa activada
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public void validarSolapaActivada(){
			        u.isDisplayed(xmlConsultarBusqHistorica); //Valildacion de que el boton ya esta habilitado/activado con xpath sin atributo disabled
			        u.highLight(xmlConsultarBusqHistorica);
			        testCase.addScreenShotCurrentStep(u, "Validacion solapa habilitada");
			        u.unhighLight(xmlConsultarBusqHistorica);
			    }

			    /**
			     * Metodo para validar que existen las columnas de la tabla de Cedis
			     * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean validarColumnasGridCedisRecepcion(){ //Verificar que los nombres de los th del gris sean los correctos
			        boolean result;
			        
			        u.scrollToElement(By.xpath("(//span[@class='mdc-tab__text-label'])[1]"));
			        
					if(u.isDisplayed(xmlThFecha)){
						u.highLight(xmlThFecha);
						result = true;
					} else {
						result = false;
					}

			        if(u.isDisplayed(xmlThCRTienda)){
						u.highLight(xmlThCRTienda);
						result = true;
					} else {
						result = false;
					}

			        if(u.isDisplayed(xmlThNombreTienda)){
						u.highLight(xmlThNombreTienda);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlThTransferencia)){
						u.highLight(xmlThTransferencia);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlThCanastillas)){
						u.highLight(xmlThCanastillas);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlThBolsEntrega)){
						u.highLight(xmlThBolsEntrega);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlThFUE)){
						u.highLight(xmlThFUE);
						result = true;
					} else {
						result = false;
					}

					if(u.isDisplayed(xmlThTiraTransferencia)){
						u.highLight(xmlThTiraTransferencia);
						result = true;
					} else {
						result = false;
					}


					if(u.isDisplayed(xmlThEvidenciaFoto)){
						u.highLight(xmlThEvidenciaFoto);
						result = true;
					} else {
						result = false;
					}

					if(result){
						testCase.addScreenShotCurrentStep(u, "Validacion de columnas Cedis busqueda historico");

						u.unhighLight(xmlThFecha);
						u.unhighLight(xmlThCRTienda);
						u.unhighLight(xmlThNombreTienda);
						u.unhighLight(xmlThTransferencia);
						u.unhighLight(xmlThCanastillas);
						u.unhighLight(xmlThBolsEntrega);
						u.unhighLight(xmlThFUE);
						u.unhighLight(xmlThTiraTransferencia);
						u.unhighLight(xmlThEvidenciaFoto);
					}
					return result;
			    }

			    /**
			     * Metodo para obtener el ultimo archivo descargado
				 * @param directoryFilePath -> direccion de el documento
			     * @author Yair Lizarraga
				 * @date Agosto 2024
			     */
			    public static File getLastModified(String directoryFilePath) {
			        File directory = new File(directoryFilePath);
			        File[] files = directory.listFiles(File::isFile);
			        long lastModifiedTime = Long.MIN_VALUE;
			        File chosenFile = null;

			        if (files != null)
			        {
			            for (File file : files)
			            {
			                if (file.lastModified() > lastModifiedTime)
			                {
			                    chosenFile = file;
			                    lastModifiedTime = file.lastModified();
			                }
			            }
			        }

			        return chosenFile;
			    }

			    /**
			     * Metodo para abrir el ultimo archivo descargado (si se realiza la descarga de un PDF) abrirlo en navegador para obtener evidencia de descargado
			     * @param tipoArchivo -> tipo de archivo que se va a abrir
				 * @param u -> selenium
				 * @author Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public boolean openLastFilePDFDownloadFolder(String tipoArchivo, SeleniumUtil u) {
			        String home = System.getProperty("user.home");
			        home = home + "\\Downloads";
			        System.out.println(home);
			        System.out.println(getLastModified(home));

			        File file = new File(getLastModified(home).getAbsolutePath());

			        if(file.exists()) {
			            try {
			                u.getDriver().switchTo().newWindow(WindowType.WINDOW);
			                ArrayList<String> tabs = new ArrayList<String> (u.getDriver().getWindowHandles());
			                u.getDriver().switchTo().window(tabs.get(1)); //cambiar al tab del pdf
			                u.get(file.getAbsolutePath());
			                u.hardWait(4);
			                testCase.addScreenShotCurrentStep(tipoArchivo);
			                u.getDriver().close();
							// findElement(By.cssSelector("body")).sendKeys(Keys.ALT + "f4");
							// Cerrar tab de el pdf
			                u.getDriver().switchTo().window(tabs.get(0)); //regresar al tab de la pagina

			            } catch (Exception e) {
			                e.printStackTrace();
			                return false;
			            }
			        } else {
			            return false;
			        }
			        return true;
			    }

			    /**
			     * Metodo para abrir el ultimo archivo descargado
				 * @Param -> Tipo de archivo que se abrira
			     * @author Yair Lizarraga
				 * @date Agosto 2024
			     */
			    
			    public boolean openLastFileDownloadFolder(String tipoArchivo) {
			        String home = System.getProperty("user.home");
			        home = home + "\\Downloads";
			        System.out.println(home);
			        System.out.println(getLastModified(home));

			        File file = new File(getLastModified(home).getAbsolutePath());

			        //first check if Desktop is supported by Platform or not
			        if(!Desktop.isDesktopSupported()){
			            System.out.println("Desktop is not supported");
			            return false;
			        }

			        Desktop desktop = Desktop.getDesktop();
			        if(file.exists()) {
			            try {
			                desktop.open(file);
			                u.hardWait(10);
			                testCase.addScreenShotCurrentStep(tipoArchivo);
							Runtime.getRuntime().exec("cmd /c taskkill /f /im excel.exe");
			            } catch (IOException e) {
			                e.printStackTrace();
			                return false;
			            }
			        } else {
			            return false;
			        }
			        return true;
			    }
			    

			    /**
			     * Metodo para validar los headers de un excel validando las columnas por estado directos
			     * @author Yair Lizarraga/Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public Boolean ExcelHeaderValidatorPorEstado(){
			        String home = System.getProperty("user.home");
			        home = home + "\\Downloads";
			        File file = new File(getLastModified(home).getAbsolutePath());
			        String[] expectedHeaders = {"Estado", "LÃƒÂ­mite Remesa", "Usuario Registro", "Fecha Registro", "Usuario Actualizacin", "Fecha Actualizacin"};
			        try {
			            FileInputStream fis = new FileInputStream(file);
			            Workbook workbook = WorkbookFactory.create(fis);
			            Sheet sheet = workbook.getSheetAt(0);
			            Row headerRow = sheet.getRow(0);

			            boolean headersValid = true;
			            for(int i= 0; i <expectedHeaders.length; i++) {
			                String actualHeader = headerRow.getCell(i).getStringCellValue();
			                testCase.addTextEvidenceCurrentStep("Los encabezados son correctos se esperaba en el excel: " + expectedHeaders[i]+" y se encontro: " +actualHeader+ "");
			                if(!expectedHeaders[i].equals(actualHeader)) {
			                    headersValid = false;
			                    System.out.println("Encabezado incorrecto en la columna" + (i + 1) + "Se esperaba:" + expectedHeaders[i]+ "Pero se encontro" +actualHeader+ "");
			                }
			            }
			            if(headersValid) {
			                System.out.println("Los encabezados son correctos");
			                return true;

			            }else {
			                System.out.println("Se encontraron errores en los encabezados");
			            }

			            workbook.close();
			            fis.close();

			        }catch(IOException e) {
			            e.printStackTrace();
			        }
			        return false;
			    }

			    /**
			     * Metodo para validar los headers de un excel validando las columnas Comprobante de Entrega
			     * @author Yair Lizarraga/Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public Boolean ExcelHeaderValidatorComprobanteEntrega(){
			        String home = System.getProperty("user.home");
			        home = home + "\\Downloads";
			        File file = new File(getLastModified(home).getAbsolutePath());
			        String[] expectedHeaders = {"FA", "Nombre del Proveedor", "Plaza", "Regin", "CR Tienda", "Nombre de la Tienda", "Fecha", "Orden de compra", "Remisin", "Folio goma", "Fecha ADMVA", "Total General a la Venta", "Cifra del proveedor", "Movts. Valorizados (nÃƒÂºmero de artÃƒÂ­culos recibidos)", "Total costo"};
			        try {
			            FileInputStream fis = new FileInputStream(file);
			            Workbook workbook = WorkbookFactory.create(fis);
			            Sheet sheet = workbook.getSheetAt(0);
			            Row headerRow = sheet.getRow(0);

			            boolean headersValid = true;
			            for(int i= 0; i <expectedHeaders.length; i++) {
			                String actualHeader = headerRow.getCell(i).getStringCellValue();
			                testCase.addTextEvidenceCurrentStep("Los encabezados son correctos se esperaba en el excel: " + expectedHeaders[i]+" y se encontro: " +actualHeader+ "");
			                if(!expectedHeaders[i].equals(actualHeader)) {
			                    headersValid = false;
			                    System.out.println("Encabezado incorrecto en la columna" + (i + 1) + "Se esperaba:" + expectedHeaders[i]+ "Pero se encontro" +actualHeader+ "");
			                }
			            }
			            if(headersValid) {
			                System.out.println("Los encabezados son correctos");
			                return true;

			            }else {
			                System.out.println("Se encontraron errores en los encabezados");
			            }

			            workbook.close();
			            fis.close();

			        }catch(IOException e) {
			            e.printStackTrace();
			        }
			        return false;
			    }

			    /**
			     * Metodo para validar los headers de un excel validando las columnas de Comprobantes de Entrega Detalles
			     * @author Yair Lizarraga/Jorge Hernandez
				 * @date Agosto 2024
			     */
			    public Boolean ExcelHeaderValidatorComprobanteEntregaDetalles(){
			    	 String home = System.getProperty("user.home");
			    	    home = home + "/Downloads";
			    	    File file = new File(getLastModified(home).getAbsolutePath());
			    	    String[] expectedHeaders = {
			    	        "FA", "Proveedor", "Region", "Plaza", "CR Tienda", "Nombre de la Tienda", 
			    	        "Fecha", "Hora", "Fecha ADMVA", "Compras directas ! FOL- GOMA", "Orden de Compra", 
			    	        "Pedido Adicional", "Remision", "Tiempo de estadia", "CVE Articulo", "Descripcion", 
			    	        "Costo", "Precio", "UDS (unidades pedidas)", "U.COM (unidades compradas)", "VALTOT", 
			    	        "Total Danados", "Total Caducos", "Total Faltantes"
			    	    };

			    	    try {
			    	        FileInputStream fis = new FileInputStream(file);
			    	        Workbook workbook = WorkbookFactory.create(fis);
			    	        Sheet sheet = workbook.getSheetAt(0);
			    	        Row headerRow = sheet.getRow(0);

			    	        boolean headersValid = true;

			    	        for (int i = 0; i < expectedHeaders.length; i++) {
			    	            // Aplica removerTildes a los valores esperados y reales
			    	            String actualHeader = removerTildes(headerRow.getCell(i).getStringCellValue().trim());
			    	            String expectedHeader = removerTildes(expectedHeaders[i]);

			    	            testCase.addTextEvidenceCurrentStep(
			    	                "Los encabezados son correctos. Se esperaba en el excel: " + expectedHeader +
			    	                " y se encontrÃ³: " + actualHeader + ""
			    	            );

			    	            if (!expectedHeader.equals(actualHeader)) {
			    	                headersValid = false;
			    	                System.out.println("Encabezado incorrecto en la columna " + (i + 1) +
			    	                    ". Se esperaba: " + expectedHeader + " pero se encontrÃ³: " + actualHeader + "");
			    	            }
			    	        }

			    	        if (headersValid) {
			    	            System.out.println("Los encabezados son correctos");
			    	            return true;
			    	        } else {
			    	            System.out.println("Se encontraron errores en los encabezados");
			    	        }

			    	        workbook.close();
			    	        fis.close();

			    	    } catch (IOException e) {
			    	        e.printStackTrace();
			    	    }

			    	    return false;
			    	}
			    
			    /**
			 	 * Metodo para validar que se descargo el comprobante de CEDISFUE
			 	 * @author Yair LizÃƒÂ¡rraga
				 * @date Septiembre 2024
			 	 */
				public Boolean validarDescargaComprobanteCedisFUE() {
					Boolean exist = false;
					String home = System.getProperty("user.home");
					home = home + "\\Downloads";
					String expectedFileNamePart = "CEDIS - FUE - Comprobantes filtrados";
					String pathYourSystem = getLastModified(home).getAbsolutePath();
					File f = new File(pathYourSystem);
					if(pathYourSystem.contains(expectedFileNamePart) && pathYourSystem.endsWith(".zip") && f.exists()){
						exist = true;
					}
					return exist;

				}
			    
			    /**
			 	 * Metodo para validar que se descargo el comprobante de CEDIS TIra de transferencia
			 	 * @author Yair LizÃƒÂ¡rraga
				 * @date Septiembre 2024
			 	 */
				public Boolean validarDescargaComprobanteCedisTiraTransferencia() {
					Boolean exist = false;
					String home = System.getProperty("user.home");
					home = home + "\\Downloads";
					String expectedFileNamePart = "CEDIS - Tira de Transferencia - Comprobantes filtrados";
					String pathYourSystem = getLastModified(home).getAbsolutePath();
					File f = new File(pathYourSystem);
					if(pathYourSystem.contains(expectedFileNamePart) && pathYourSystem.endsWith(".zip") && f.exists()){
						exist = true;
					}
					return exist;

					}

				/**
				 * Metodo para dar clic en la app de portal cometa
				 * @author Yair Lizarraga
				 * @date Septiembre 2024
				 */
			 	public void clicCambiarContrasena() {
			 	u.highLight(xmlCambiarContrasena);
			 	testCase.addScreenShotCurrentStep(u, "xmlAcceptButton");
			 	u.click(xmlCambiarContrasena);
			 	testCase.addScreenShotCurrentStep(u, "xmlAcceptButton2");
			 	
			 	}

				/**
				 * Metodo para cambiar el password
				 * @param newpassword -> nuevo password
				 * @param password -> password actual
				 * @param user -> usuario
				 * @param passwordConfirmation -> confirmacion de nuevo password
				 * @author Yair Lizarraga
				 * @date Septiembre 2024
				 */
				public Boolean cambiarContrasena(String user, String password, String newpassword, String passwordConfirmation) {
				    boolean isSuccess = true; // Inicializamos la bandera en true 

				    try { 
				    	u.sendKeys(xmlUserCc, user);
				 		u.sendKeys(xmlPasswordCc, password);
				 		u.sendKeys(xmlNewPasswordCc, newpassword);
				 		u.sendKeys(xmlPasswordConfirmation, passwordConfirmation);
				 		testCase.addScreenShotCurrentStep(u, "Passwordchange");
				 		u.click(xmlAcceptButton);
				 		u.hardWait(5);
				 		testCase.addScreenShotCurrentStep(u, "Passwordchange");
				    } catch (Exception e) { 
				        isSuccess = false; // Si ocurre una excepci n, la bandera se pone en false
				    } 

				    return isSuccess; // Devolvemos la bandera 
				}

				/**
				 * Metodo para validar el numero de filas en la tabla
				 * @author Yair Lizarraga
				 * @date Septiembre 2024
				 */
				public Boolean validarNumeroDeFilasEnTabla() {
				    boolean isValid = false; // Inicializamos la bandera en false
				    u.hardWait(10);//para que cargue la tabla
				    try {
				        // Obtenemos la tabla que coincide con el XPath
				        WebElement tabla = u.getDriver().findElement(xmlXpathTabla);

				        // Obtenemos la lista de elementos internos (filas) dentro de la tabla
				        List<WebElement> elementos = tabla.findElements(xmlXpathFilas);

				        // Contamos los elementos
				        int count = elementos.size();
				        System.out.print(elementos.size());
				        // Validamos que el nÃƒÂºmero de elementos sea 20
				        if (count == 20) {
				        	u.scrollToElement(xmlXpathTabla);
				        	u.highLight(xmlXpathTabla);
						 	testCase.addScreenShotCurrentStep(u, "elementos");
				            isValid = true; // Si hay 20 elementos, la validacin es exitosa
				        }
				    } catch (Exception e) {
				        // Manejo de excepciones si ocurre algÃƒÂºn error durante la ejecucin
				        e.printStackTrace();
				    }

				    return isValid; // Devolvemos el resultado de la validacin
				}

				/**
	 			* Metodo para validar el rango de meses validos para consultar en Directos
				* @param meses -> cantidad de meses que se van a retroceder
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean validacionRangoMeses(int meses){
					 boolean result = false;
					 u.mouseOver(xmlRangoFechas);
					 if(u.isDisplayed(xmlRangoFechas)) {
						 u.highLight(xmlRangoFechas);
						 u.mouseOver(xmlRangoFechas);
						 u.click(xmlRangoFechas);
						 u.unhighLight(xmlRangoFechas);

						 u.highLight(xmlBotonFlechaIzquierda);
						 u.mouseOver(xmlBotonFlechaIzquierda);
						 for (int i = 0; i < meses; i++) { // valor para contemplar 3 meses atras en rango de fechas
							 u.click(xmlBotonFlechaIzquierda);
						 }

						 if(u.isDisplayed(xmlPrimerDiaHabilidato)){
							 u.highLight(xmlPrimerDiaHabilidato);
							 u.mouseOver(xmlPrimerDiaHabilidato);
							 u.click(xmlPrimerDiaHabilidato);
							 u.unhighLight(xmlPrimerDiaHabilidato);
					 	}

						 u.highLight(xmlBotonFlechaDerecha);
						 u.mouseOver(xmlBotonFlechaDerecha);
						 for (int i = 0; i < 3; i++) { // valor para contemplar 4 meses atras en rango de fechas
							 u.click(xmlBotonFlechaDerecha);
						 }

						 if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							 u.highLight(xmlUltimoDiaHabilitado);
							  testCase.addScreenShotCurrentStep(u, "Se valida el rango de fechas");
							 result = true;
						 } else {
							 result = false; //indicando que el rango de meses no es el indicado
						 }
					 }
					return result;
				}

				/**
	 			* Metodo para validar antiguedad de resultados de comprobantes en grid de Directos
				* @param meses -> cantidad de meses que se van a retroceder
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public Boolean validacionAntiguedad(int meses){
					 boolean result = false;
					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar 3 meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}

						if(u.isDisplayed(xmlPrimerDiaHabilidato)) {
							u.highLight(xmlPrimerDiaHabilidato);
							u.mouseOver(xmlPrimerDiaHabilidato);
							u.click(xmlPrimerDiaHabilidato);
							u.unhighLight(xmlPrimerDiaHabilidato);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar 3 meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}

						if(u.isDisplayed(xmlUltimoDiaHabilitado)) {
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}

						consultarComprobantes();

						if(u.isDisplayed(xmlPaginadoFinal)){
							u.highLight(xmlPaginadoFinal);
							u.mouseOverAndClick(xmlPaginadoFinal);
							u.unhighLight(xmlPaginadoFinal);
						} else if (u.isDisplayed(xmlPaginadoNext)) {
							u.highLight(xmlPaginadoNext);
							u.mouseOverAndClick(xmlPaginadoNext);
							u.unhighLight(xmlPaginadoNext);
						}

						LocalDate date = LocalDate.parse(u.getText(xmlSegundaFechaFiltro));
						LocalDate date2 = LocalDate.parse(u.getText(xmlFechaUltimopRegistro));

						int fechaRegistro = date2.getYear();
						int fechaBusqueda = date.getYear();
						int yearInvalido = LocalDate.now().getYear() - 4; //tratando de una busqeuda con Y actual
						if(fechaRegistro > (fechaBusqueda-4)){ //tratando de una busqueda con Y anterior al actual
							result = true;
						} else {
							result = false;
						}

					}
					 return result;
				}

				/**
	 			* Metodo para validar el nombre de formato pdf en Directos
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public Boolean validarformatoNombrePdf(){
					 boolean result = false;
					 String home = System.getProperty("user.home");
					 home = home + "\\Downloads";
					 String expectedFileNamePart = "OC_";
					 String pathYourSystem = getLastModified(home).getAbsolutePath();
					 File f = new File(pathYourSystem);
					 if(pathYourSystem.contains(expectedFileNamePart) && pathYourSystem.endsWith(".pdf") && f.exists()){
					 	 result = true;
					 }
					 return result;
				}


				/**
	 			* Metodo para validar el nombre de formato resumen en Directos
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean validacionFormatoNombreResumen(){
					boolean result = false;
					String home = System.getProperty("user.home");
					home = home + "\\Downloads";
					String expectedFileNamePart = "Resumen";
					String pathYourSystem = getLastModified(home).getAbsolutePath();
					File f = new File(pathYourSystem);
					if(pathYourSystem.contains(expectedFileNamePart) && pathYourSystem.endsWith(".xlsx") && f.exists()){
						result = true;
					}
					return result;
				}

				/**
	 			* Metodo para validar el nombre de formato masivo en Directos
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean validacionFormatoNombreMasivo(){
					boolean result = false;
					String home = System.getProperty("user.home");
					home = home + "\\Downloads";
					String expectedFileNamePart = "Concentrado_OC_"; //formato de nombre actual Concentrado_OC_2024-05-01_2024-05-31.xlsx
					String pathYourSystem = getLastModified(home).getAbsolutePath();
					File f = new File(pathYourSystem);
					if(pathYourSystem.contains(expectedFileNamePart) && pathYourSystem.endsWith(".xlsx") && f.exists()){
						result = true;
					}
					return result;
				}

				/**
	 			* Metodo para validar la visibilidad de leyenda en comprobante
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean validarLeyendaComprobante(String leyenda){
					 boolean result = false;
					 if(u.isDisplayed(xmlLeyendaComprobante)){
						 if(u.getText(xmlLeyendaComprobante).contains(leyenda)){
							 result = true;
						 }
					 }
					 return result;
				}

				/**
	 			* Metodo para validar la visibiliad de el sello en comprobante
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean validaconSelloComprobantes(){
					 boolean result = false;
					 if(u.isDisplayed(xmlSelloComprobante)){
						 result = true;
					 }
					 return result;
				}

				/**
	 			* Metodo para validar la busqueda con filtro Plaza en Directos
				* @param meses -> cantidad de meses que se van a retroceder
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean busquedaFiltroPlaza(int meses){
					boolean result;
					if(u.isDisplayed(xmlCRPLazaDirectos)){
						u.highLight(xmlCRPLazaDirectos);
						u.mouseOverAndClick(xmlCRPLazaDirectos);
						u.unhighLight(xmlCRPLazaDirectos);
						u.highLight(xmlCRPlazaOp1);
						testCase.addScreenShotCurrentStep(u, "Seleccion de plaza");
						u.click(xmlCRPlazaOp1);

						result = true;
					} else {
						result = false;
					}

					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlFechaInicioMes)){
							u.highLight(xmlFechaInicioMes);
							u.mouseOver(xmlFechaInicioMes);
							u.click(xmlFechaInicioMes);
							u.unhighLight(xmlFechaInicioMes);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar 4 meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}
						u.unhighLight(xmlBotonFlechaDerecha);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}
						testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

						consultarComprobantes();
					}

					return result;
				}

				/**
	 			* Metodo para validar la busqueda con filtro Region en Directos
				* @param meses -> cantidad de meses que se van a retroceder
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean busquedaFiltroRegion(int meses){
					 boolean result;
					 if(u.isDisplayed(xmlRegion)){
						 u.highLight(xmlRegion);
						 u.mouseOverAndClick(xmlRegion);
						 u.unhighLight(xmlRegion);
						 u.highLight(xmlregionOp1);
						 testCase.addScreenShotCurrentStep(u, "Seleccion de region");
						 u.click(xmlregionOp1);

						 result = true;
					 } else {
						 result = false;
					 }

					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlFechaInicioMes)){
							u.highLight(xmlFechaInicioMes);
							u.mouseOver(xmlFechaInicioMes);
							u.click(xmlFechaInicioMes);
							u.unhighLight(xmlFechaInicioMes);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}
						u.unhighLight(xmlBotonFlechaDerecha);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}
						testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

						consultarComprobantes();
					}
					 return result;
				}

				/**
	 			* Metodo para validar la busqueda multiple de Tienda en Directos
				* @param meses -> cantidad de meses que se van a retroceder
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean busquedaMultiTienda(int meses){
					boolean result;
					if(u.isDisplayed(xmlCRTienda)) {
						u.highLight(xmlCRTienda);
						u.click(xmlCRTienda);
						u.unhighLight(xmlCRTienda);
						u.highLight(xmlCRTiendaOp1);
						u.click(xmlCRTiendaOp1);

						try {
							if(u.isDisplayed(xmlCRTiendaOp2)){
								u.highLight(xmlCRTiendaOp2);
								u.click(xmlCRTiendaOp2);
							}
							result = true;
						} catch (NoSuchElementException e){
							result = false;
							testCase.addTextEvidenceCurrentStep("No se pudo seleccionar una segunda Tienda");
						}
						testCase.addScreenShotCurrentStep(u, "Seleccion de tiendas");
					} else {
						result = false;
					}
					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlFechaInicioMes)){
							u.highLight(xmlFechaInicioMes);
							u.mouseOver(xmlFechaInicioMes);
							u.click(xmlFechaInicioMes);
							u.unhighLight(xmlFechaInicioMes);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}
						u.unhighLight(xmlBotonFlechaDerecha);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}
						testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

						consultarComprobantes();
					}
					return result;
				}

				/**
	 			* Metodo para validar la busqueda multiple de Plaza en Directos
				* @param meses -> cantidad de meses que se van a retroceder
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean seleccionMultiFA(int meses){
					boolean result;
					if(u.isDisplayed(xmlFuenteAbasto)) {
						u.highLight(xmlFuenteAbasto);
						u.mouseOverAndClick(xmlFuenteAbasto);
						u.unhighLight(xmlFuenteAbasto);
						u.highLight(xmlFuenteAbastoOp1);
						u.click(xmlFuenteAbastoOp1);

						try {
							u.highLight(xmlFuenteAbasto);
							u.mouseOverAndClick(xmlFuenteAbasto);
							u.unhighLight(xmlFuenteAbasto);
							if(u.isDisplayed(xmlFuenteAbastoOp2)){
								u.highLight(xmlFuenteAbastoOp2);
							}
							u.click(xmlFuenteAbastoOp2);
							result = true;
						} catch (NoSuchElementException e){
							result = false;
							testCase.addTextEvidenceCurrentStep("No se pudo seleccionar un segundo FA");
						}
						testCase.addScreenShotCurrentStep(u, "Seleccion de FA");
						result = true;
					} else {
						result = false;
					}
					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlFechaInicioMes)){
							u.highLight(xmlFechaInicioMes);
							u.mouseOver(xmlFechaInicioMes);
							u.click(xmlFechaInicioMes);
							u.unhighLight(xmlFechaInicioMes);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}
						u.unhighLight(xmlBotonFlechaDerecha);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}
						testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

						consultarComprobantes();
					}
					return result;
				}


				/**
	 			* Metodo para validar la busqueda multiple de Plaza en Directos
				* @param meses -> cantidad de meses que se van a retroceder
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean busquedaMultiPlaza(int meses){
					boolean result;
					if(u.isDisplayed(xmlCRPLazaDirectos)) {
						u.highLight(xmlCRPLazaDirectos);
						u.mouseOverAndClick(xmlCRPLazaDirectos);
						u.unhighLight(xmlCRPLazaDirectos);
						u.highLight(xmlCRPlazaOp1);
						u.click(xmlCRPlazaOp1);

						try {
							u.highLight(xmlCRPLazaDirectos);
							u.mouseOverAndClick(xmlCRPLazaDirectos);
							u.unhighLight(xmlCRPLazaDirectos);
							if(u.isDisplayed(xmlCRPlazaOp2)){
								u.highLight(xmlCRPlazaOp2);
							}
							u.click(xmlCRPlazaOp2);
							result = true;
						} catch (NoSuchElementException e){
							result = false;
							testCase.addTextEvidenceCurrentStep("No se pudo seleccionar una segunda Plaza");
						}
						testCase.addScreenShotCurrentStep(u, "Seleccion de plazas");
						result = true;
					} else {
						result = false;
					}
					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlFechaInicioMes)){
							u.highLight(xmlFechaInicioMes);
							u.mouseOver(xmlFechaInicioMes);
							u.click(xmlFechaInicioMes);
							u.unhighLight(xmlFechaInicioMes);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}
						u.unhighLight(xmlBotonFlechaDerecha);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}
						testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

						consultarComprobantes();
					}
					return result;
				}


				/**
	 			* Metodo para validar el funcionamiento del autocomplete en Filtro FA Directos
				* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean validarAutocompleteFA(){
					boolean result;
					if(u.isDisplayed(xmlFuenteAbasto)){
						u.highLight(xmlFuenteAbasto);
						u.mouseOverAndClick(xmlFuenteAbasto);
						u.unhighLight(xmlFuenteAbasto);
						u.sendKeys(xmlFuenteAbastoInput,"669");
						testCase.addScreenShotCurrentStep(u, "Se valida el autocomplete");

						result = true;
					} else {
						result = false;
					}
					 return result;
				}

				/**
	 			* Metodo para validar el funcionamiento del autocomplete en Filtro Plaza Directos
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean validarAutocompletePlaza(){
					boolean result;
					if(u.isDisplayed(xmlCRPLazaDirectos)){
						u.highLight(xmlCRPLazaDirectos);
						u.mouseOverAndClick(xmlCRPLazaDirectos);
						u.unhighLight(xmlCRPLazaDirectos);
						u.sendKeys(xmlCRPlazaDirectosInput,"10M");
						testCase.addScreenShotCurrentStep(u, "Se valida el autocomplete");

						result = true;
					} else {
						result = false;
					}
					 return result;
				}

				/**
	 			* Metodo para validar la busqueda con filtro Tienda en Directos
	 			* @param  meses ->	meses atrÃ¡s a la que se harÃ¡ la consulta
	 			* @return true si la selecciÃ³n se pudo elegir
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean busquedaFiltroTienda(int meses){
					 boolean result;
					if(u.isDisplayed(xmlCRTienda)){
						u.highLight(xmlCRTienda);
						u.mouseOverAndClick(xmlCRTienda);
						u.unhighLight(xmlCRTienda);
						u.highLight(xmlCRTiendaOp1);
						testCase.addScreenShotCurrentStep(u, "Seleccion de Tienda");
						u.click(xmlCRTiendaOp1);

						result = true;
					} else {
						result = false;
					}

					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlFechaInicioMes)){
							u.highLight(xmlFechaInicioMes);
							u.mouseOver(xmlFechaInicioMes);
							u.click(xmlFechaInicioMes);
							u.unhighLight(xmlFechaInicioMes);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar 3 meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}
						u.unhighLight(xmlBotonFlechaDerecha);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}
						testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

						consultarComprobantes();
					}
					 return result;
				}

				/**
	 			* Metodo para validar la busqueda con filtro Proveedor en Directos
	 			* @author Jorge Hernandez
	 			* @param  meses ->	meses atrÃ¡s a la que se harÃ¡ la consulta
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean busquedaFiltroProveedor(int meses){
					boolean result;
					if(u.isDisplayed(xmlProveedor)) {
						u.highLight(xmlProveedor);
						u.mouseOverAndClick(xmlProveedor);
						u.unhighLight(xmlProveedor);
						u.highLight(xmlProveedorOp1);
						testCase.addScreenShotCurrentStep(u, "Seleccion de proveedor");
						u.click(xmlProveedorOp1);
						result = true;
					} else {
						result = false;
					}

					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlFechaInicioMes)){
							u.highLight(xmlFechaInicioMes);
							u.mouseOver(xmlFechaInicioMes);
							u.click(xmlFechaInicioMes);
							u.unhighLight(xmlFechaInicioMes);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar 3 meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}
						u.unhighLight(xmlBotonFlechaDerecha);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}
						testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

						consultarComprobantes();
					}
					return result;
				}

				/**
	 			* Metodo para validar la busqueda multiple de Proveedor Directos
	 			* @author Jorge Hernandez
	 			* @param  meses ->	meses atrÃ¡s a la que se harÃ¡ la consulta
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean busquedaFiltroMultiProveedor(int meses){
					boolean result;
					if(u.isDisplayed(xmlProveedor)) {
						u.highLight(xmlProveedor);
						u.mouseOverAndClick(xmlProveedor);
						u.unhighLight(xmlProveedor);
						u.highLight(xmlProveedorOp1);
						u.click(xmlProveedorOp1);

						try {
							u.highLight(xmlProveedor);
							u.mouseOverAndClick(xmlProveedor);
							u.unhighLight(xmlProveedor);
							if(u.isDisplayed(xmlProveedorOp2)){
								u.highLight(xmlProveedorOp2);
							}
							u.click(xmlProveedorOp2);
							result = true;
						} catch (NoSuchElementException e){
							result = false;
							testCase.addTextEvidenceCurrentStep("No se pudo seleccionar un segundo proveedor");
						}
						testCase.addScreenShotCurrentStep(u, "Seleccion de proveedores");
						result = true;
					} else {
						result = false;
					}
					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlFechaInicioMes)){
							u.highLight(xmlFechaInicioMes);
							u.mouseOver(xmlFechaInicioMes);
							u.click(xmlFechaInicioMes);
							u.unhighLight(xmlFechaInicioMes);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}
						u.unhighLight(xmlBotonFlechaDerecha);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)) {
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
							testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");
						}

						consultarComprobantes();
					}
					return result;
				}

				/**
	 			* Metodo para validar el funcionamiento del autocomplete en Filtro Proveedor Directos
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean validacionAutocompleteProveedor(){
					boolean result;
					if(u.isDisplayed(xmlProveedor)){
						u.highLight(xmlProveedor);
						u.mouseOverAndClick(xmlProveedor);
						u.unhighLight(xmlProveedor);
						u.sendKeys(xmlProveedorInput,"Bim");
						testCase.addScreenShotCurrentStep(u, "Se valida el autocomplete");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion de Proveedor despues de seleccionar uno por autocomplete en Directos
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean seleccionAutocompleteProveedor(){
					boolean result;
					if(u.isDisplayed(xmlProveedor)){
						u.highLight(xmlProveedor);
						u.mouseOverAndClick(xmlProveedor);
						u.unhighLight(xmlProveedor);
						u.sendKeys(xmlProveedorInput,"Bo");
						u.highLight(xmlProveedorOp1);
						u.click(xmlProveedorOp1);
						u.mouseOverAndClick(xmlProveedor);
						testCase.addScreenShotCurrentStep(u, "Se valida seleccion de proveedor");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion de Plaza en filtro en Directos
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean seleccionPlaza(){
					boolean result;
					if(u.isDisplayed(xmlCRPlazaOp1)){
						u.highLight(xmlCRPlazaOp1);
						u.click(xmlCRPlazaOp1);
						testCase.addScreenShotCurrentStep("Seleccion de plaza");
						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion de opcion 2 de  Plaza en filtro en Directos
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean seleccionPlazaOp2(){
					boolean result;
					if(u.isDisplayed(xmlCRPLazaDirectos)){
						u.highLight(xmlCRPLazaDirectos);
						u.mouseOverAndClick(xmlCRPLazaDirectos);
						u.unhighLight(xmlCRPLazaDirectos);
						u.highLight(xmlCRPlazaOp2);
						u.click(xmlCRPlazaOp2);
						testCase.addScreenShotCurrentStep(u, "Seleccion de plaza");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion de Plaza despues de seleccionar una por autocomplete en Directos
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean seleccionAutocompletePlaza(){
					boolean result;
					if(u.isDisplayed(xmlCRPLazaDirectos)){
						u.highLight(xmlCRPLazaDirectos);
						u.mouseOverAndClick(xmlCRPLazaDirectos);
						u.unhighLight(xmlCRPLazaDirectos);
						u.sendKeys(xmlCRPlazaDirectosInput,"10M");
						u.highLight(xmlCRPlazaOp1);
						u.click(xmlCRPlazaOp1);
						testCase.addScreenShotCurrentStep(u, "Se valida seleccion de plaza");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion de Region en filtro en Directos
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean seleccionRegionop2(){
					boolean result;
					if(u.isDisplayed(xmlregionOp2)){
						u.highLight(xmlregionOp2);
						u.click(xmlregionOp2);
						testCase.addScreenShotCurrentStep(u, "Seleccion de region");
						u.unhighLight(xmlregionOp2);

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion de Region despues de seleccionar una por autocomplete en Directos
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean seleccionAutocompleteRegion(){
					boolean result;
					if(u.isDisplayed(xmlRegion)){
						u.highLight(xmlRegion);
						u.mouseOverAndClick(xmlRegion);
						u.unhighLight(xmlRegion);
						u.mouseOverAndClick(xmlRegion);
						u.sendKeys(xmlInputRegion,"P");
						u.highLight(xmlregionOp1);
						u.click(xmlregionOp1);
						testCase.addScreenShotCurrentStep(u, "Se valida seleccion de region");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion de Tienda en filtro en Directos
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean seleccionTienda(){
					boolean result;
					if(u.isDisplayed(xmlCRTienda)){
						u.highLight(xmlCRTienda);
						u.mouseOverAndClick(xmlCRTienda);
						u.unhighLight(xmlCRTienda);
						u.highLight(xmlCRTiendaOp1);
						u.click(xmlCRTiendaOp1);
						u.mouseOverAndClick(xmlCRTienda);
						testCase.addScreenShotCurrentStep(u, "Seleccion de tienda");
						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion de Tienda en filtro en Directos
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean seleccionTiendaOp2(){
					boolean result;
					if(u.isDisplayed(xmlCRTiendainput)){
						u.highLight(xmlCRTiendainput);
						u.mouseOverAndClick(xmlCRTiendainput);
						u.unhighLight(xmlCRTiendainput);
						u.highLight(xmlCRTiendaOp2);
						u.click(xmlCRTiendaOp2);
						u.mouseOverAndClick(xmlCRTiendainput);
						testCase.addScreenShotCurrentStep(u, "Seleccion de tienda");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion de Tienda despues de seleccionar una por autocomplete en Directos
	 			* @author Jorge Hernandez
	 			*  @return true si la selecciÃ³n se pudo elegir
				*  @date Octubre 2024
				*/
				public boolean seleccionAutocompleteTienda(){
					boolean result;
					if(u.isDisplayed(xmlCRTienda)){
						u.highLight(xmlCRTienda);
						u.mouseOverAndClick(xmlCRTienda);
						u.unhighLight(xmlCRTienda);
						u.sendKeys(xmlCRTiendainput,"MTY");
						u.highLight(xmlCRTiendaOp1);
						u.click(xmlCRTiendaOp1);
						u.mouseOverAndClick(xmlCRTiendainput);
						testCase.addScreenShotCurrentStep(u, "Se valida seleccion de tienda");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion de Proveedor en filtro en Directos cierra el filtro al seleccionar opcion
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean seleccionProveedor(){
					boolean result;
					if(u.isDisplayed(xmlProveedor)){
						u.highLight(xmlProveedor);
						u.mouseOverAndClick(xmlProveedor);
						u.unhighLight(xmlProveedor);
						u.highLight(xmlProveedorOp1);
						u.click(xmlProveedorOp1);
						if(u.isDisplayed(xmlTituloFiltrosBusquedaComprobantes)){ //Condicional para cerrar el la lista de opciones de el filtro (dar click fuera de la lista)
							u.click(xmlTituloFiltrosBusquedaComprobantes);
						}
						testCase.addScreenShotCurrentStep(u, "Seleccion de proveedor");
						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion de Proveedor en filtro en Directos
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n se pudo elegir
				* @date Octubre 2024
				*/
				public boolean seleccionProveedorOp2(){
					boolean result;
					if(u.isDisplayed(xmlProveedor)){
						u.highLight(xmlProveedor);
						u.mouseOverAndClick(xmlProveedor);
						u.unhighLight(xmlProveedor);
						u.highLight(xmlProveedorOp2);
						testCase.addScreenShotCurrentStep(u, "Seleccion de proveedor");
						u.click(xmlProveedorOp2);
						u.mouseOverAndClick(xmlProveedor);

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la seleccion multiple en filtro Proveedor en Directos
	 			* @author Jorge Hernandez
	 			* @return true si la selecciÃ³n mÃºltiple se pudo elegir
				* @date Octubre 2024
				*/
				public boolean seleccionMultiProveedor(){
					boolean result;
					u.hardWait(55);
					if(u.isDisplayed(xmlProveedor)){
						u.highLight(xmlProveedor);
						u.mouseOverAndClick(xmlProveedor);
						u.unhighLight(xmlProveedor);
						u.highLight(xmlProveedorOp1);
						u.click(xmlProveedorOp1);

						if(u.isDisplayed(xmlProveedorOp2)){
							u.highLight(xmlProveedorOp2);
							u.click(xmlProveedorOp2);
						}
						testCase.addScreenShotCurrentStep(u, "Seleccion de Proveedores");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
				 * Metodo para validar la seleccion multiple en filtro FA en Directos
				 * @author Jorge Hernandez
				 * @return true si la selecciÃ³n mÃºltiple se pudo elegir
				 * @date Abril 2025
				 */
				public boolean seleccionMultiFiltroFA(){
					boolean result = false;
					if(u.isDisplayed(xmlFuenteAbasto)){
						u.highLight(xmlFuenteAbasto);
						u.mouseOverAndClick(xmlFuenteAbasto);
						u.unhighLight(xmlFuenteAbasto);
						u.highLight(xmlFuenteAbastoOp1);
						u.click(xmlFuenteAbastoOp1);

						if(u.isDisplayed(xmlFuenteAbastoOp2)){
							u.highLight(xmlFuenteAbastoOp2);
							u.click(xmlFuenteAbastoOp2);
						}
						testCase.addScreenShotCurrentStep(u, "Seleccion de Proveedores");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
				 * Metodo para validar la seleccion multiple en filtro Plaza en Directos
				 * @author Jorge Hernandez
				 * @return true si la selecciÃ³n mÃºltiple se pudo elegir
				 * @date Abril 2025
				 **/
				public boolean seleccionMultiPlaza(){
					boolean result = false;
						if(u.isDisplayed(xmlOpcionesPlaza)){
							u.click(xmlPlazaOp2);

							if(u.isDisplayed(xmlPlazaOp3)){
								u.click(xmlPlazaOp3);
							}
							testCase.addScreenShotCurrentStep(u, "Seleccion de Plazas");

							result = true;
						} else {
							result = false;
					}
					return result;
				}

				/**
				 * Metodo para validar la seleccion multiple en filtro Tienda en Directos
				 * @author Jorge Hernandez
				 * @return true si la selecciÃ³n multiple se pudo elegir
				 * @date Abril 2025
				 **/
				public boolean seleccionMultiTienda(){
					boolean result;
					if(u.isDisplayed(xmlOpcionesTienda)){
						u.click(xmlTiendaOp1);

						if(u.isDisplayed(xmlTiendaOp2)){
							u.click(xmlTiendaOp2);
						}
						testCase.addScreenShotCurrentStep(u, "Seleccion de Tiendas");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
				 * Metodo para validar la seleccion multiple en filtro FA en Directos
				 * @author Jorge Hernandez
				 * @return true si la selecciÃ³n mÃºltiple se pudo elegir
				 * @date Abril 2025
				 **/
				public boolean seleccionMultiFuenteAbastos(){
					boolean result;
					if(u.isDisplayed(xmlFuenteAbasto)){
						u.highLight(xmlFuenteAbasto);
						u.mouseOverAndClick(xmlFuenteAbasto);
						u.unhighLight(xmlFuenteAbasto);
						u.highLight(xmlFuenteAbastoOp1);
						u.click(xmlFuenteAbastoOp1);

						if(u.isDisplayed(xmlFuenteAbastoOp2)){
							u.highLight(xmlFuenteAbastoOp2);
							u.click(xmlFuenteAbastoOp2);
						}
						testCase.addScreenShotCurrentStep(u, "Seleccion de FA");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
				 * Metodo para validar la seleccion multiple en filtro Zona en Directos
				 * @author Jorge Hernandez
				 * @return true si la selecciÃ³n mÃºltiple se pudo elegir
				 * @date Abril 2025
				 **/
				public boolean seleccionMultiZona(){
					boolean result;
					if(u.isDisplayed(xmlZona)){
						u.highLight(xmlZona);
						u.mouseOverAndClick(xmlZona);
						u.unhighLight(xmlZona);
						u.highLight(xmlZonaOp1);
						u.click(xmlZonaOp1);

						if(u.isDisplayed(xmlZonaOp2)){
							u.highLight(xmlZonaOp2);
							u.click(xmlZonaOp2);
						}
						testCase.addScreenShotCurrentStep(u, "Seleccion de Zonas");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
				 * Metodo para validar la seleccion multiple en filtro Region en Directos
				 * @author Jorge Hernandez
				 * @return true si la selecciÃ³n mÃºltiple se pudo elegir
				 * @date Abril 2025
				 **/
				public boolean seleccionMultiRegion(){
					boolean result;
					if(u.isDisplayed(xmlRegion)){
						u.highLight(xmlRegion);
						u.mouseOverAndClick(xmlRegion);
						u.unhighLight(xmlRegion);
						u.highLight(xmlregionOp1);
						u.click(xmlregionOp1);

						if(u.isDisplayed(xmlregionOp2)){
							u.highLight(xmlregionOp2);
							u.click(xmlregionOp2);
						}
						u.waitForLoadPage();
						testCase.addScreenShotCurrentStep(u, "Seleccion de Regiones");

						result = true;
					} else {
						result = false;
					}
					return result;
				}


				/**
	 			* Metodo para validar el funcionamiento de deseleccionar proveedor de filtro en Directos
	 			* @author Jorge Hernandez
	 			* @return true si se pudo deseleccionar provedor previamente seleccionado
				* @date Octubre 2024
				*/
				public boolean deseleccionarProveedorSeleccionado(){
					boolean result;
					if(u.isDisplayed(xmlProveedor)){
						u.highLight(xmlProveedor);
						u.mouseOverAndClick(xmlProveedor);
						u.unhighLight(xmlProveedor);
						u.highLight(xmlProveedorOp1);
						u.click(xmlProveedorOp1);
						u.mouseOverAndClick(xmlProveedor);
						testCase.addScreenShotCurrentStep(u, "Deseleccion de Proveedor");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la busqueda de comprobantes con filtro FA (Fuente Abasto) en Directos
	 			* @author Jorge Hernandez
	 			* @param meses	-> Cantidad de meses hacia atrÃ¡s que se harÃ¡ la consulta
	 			* @return 	true si se pudo realizar la selecciÃ³n de fechas
	 			* 			false si la selecciÃ³n tuvo alguna complicaciÃ³n
				* @date Octubre 2024
				*/
				public boolean seleccionarFA(int meses){
					boolean result;
					if(u.isDisplayed(xmlFuenteAbasto)){
						u.highLight(xmlFuenteAbasto);
						u.mouseOverAndClick(xmlFuenteAbasto);
						u.unhighLight(xmlFuenteAbasto);
						u.highLight(xmlFuenteAbastoOp1);
						testCase.addScreenShotCurrentStep(u, "Seleccion de FA");
						u.click(xmlFuenteAbastoOp1);

						result = true;
					} else {
						result = false;
					}

					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlFechaInicioMes)){
							u.highLight(xmlFechaInicioMes);
							u.mouseOver(xmlFechaInicioMes);
							u.click(xmlFechaInicioMes);
							u.unhighLight(xmlFechaInicioMes);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar 4 meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}
						u.unhighLight(xmlBotonFlechaDerecha);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}
						testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

						consultarComprobantes();

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar la busqueda de comprobantes con filtro Region en Directos
	 			* @author Jorge Hernandez
	 			* @param meses	-> Cantidad de meses hacia atrÃ¡s que se harÃ¡ la consulta
	 			* @return 	true si se pudo realizar la selecciÃ³n de fechas
	 			* 			false si la selecciÃ³n tuvo alguna complicaciÃ³n
				* @date Octubre 2024
				*/
				public boolean busquedaMultiRegion(int meses){
					boolean result;
					if(u.isDisplayed(xmlRegion)){
						u.highLight(xmlRegion);
						u.mouseOverAndClick(xmlRegion);
						u.unhighLight(xmlRegion);
						u.highLight(xmlregionOp1);
						u.click(xmlregionOp1);

						try {
							u.highLight(xmlRegion);
							u.mouseOverAndClick(xmlRegion);
							u.unhighLight(xmlRegion);
							if(u.isDisplayed(xmlregionOp2)){
								u.highLight(xmlregionOp2);
							}
							u.click(xmlregionOp2);
							result = true;
						} catch (NoSuchElementException e){
							result = false;
							testCase.addTextEvidenceCurrentStep("No se pudo seleccionar una segunda Region");
						}
						testCase.addScreenShotCurrentStep(u, "Seleccion de regiones");
					} else {
						result = false;
					}

					u.mouseOver(xmlRangoFechas);
					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlFechaInicioMes)){
							u.highLight(xmlFechaInicioMes);
							u.mouseOver(xmlFechaInicioMes);
							u.click(xmlFechaInicioMes);
							u.unhighLight(xmlFechaInicioMes);
						}

						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						for (int i = 0; i < 3; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaDerecha);
						}
						u.unhighLight(xmlBotonFlechaDerecha);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}
						testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

						consultarComprobantes();
						
						result = true;
					} else {
						result = false;
					}
					 return result;
				}

				/**
				 * Valida el acceso a la pagina de login de Access Control
				 * @author Jose Zarate - 1000072516
				 * @date Octubre 2024
				 */
				public void validarAccesoLogin() {
					try {
						u.waitWebElement(xmlUserName, 30, 2);
						testCase.addScreenShotCurrentStep("Acceso a la pagina de login");
					}catch(Exception ex) {
						System.out.println(" -> " + ex);
					}
				}

				/**
				 * Valida que aparezca el grid en la pagina de Comprobantes de Compra
				 * @author Jose Zarate - 1000072516
				 * @return 	true si se pudo validar el grid
				 * @date Octubre 2024
				 */
				public boolean validarGrid() {
					boolean result;
					u.waitWebElement(fa, 60, 2);
					if(u.isDisplayed(fa)){
						u.hardWait(10);
						testCase.addScreenShotCurrentStep("Se muestran encabezados del grid");
						result = true;
					} else {
						result = false;
					}
					return result;
				}
				

				/**
				 * Realiza una consulta para que el grid se poble de informaciÃ³n
				 * @author Isaac E. GarcÃ­a
				 * @param meses	-> Cantidad de meses hacia atrÃ¡s que se harÃ¡ la consulta
				 * @return 	true si se pudo realizar la selecciÃ³n de fechas
				 *  			false si la selecciÃ³n tuvo alguna complicaciÃ³n
				 * @date Abril 2025
				 */
				public boolean ConsultarGrid(int meses ){
					boolean result=false;

					try {
						clickRegistro();
						seleccionarFechas(meses);
						result = true;
						testCase.addScreenShotCurrentStep("Se muestran encabezados del grid");
					} catch (Exception e) {
						e.printStackTrace();
					}

					return result;
				}


				/**
	 			* Metodo para validar la busqueda de comprobantes con filtro de fechas en Directos
	 			* @author Isaac E. GarcÃ­a
	 			* @param meses	-> Cantidad de meses hacia atrÃ¡s que se harÃ¡ la consulta
	 			* @return 	true si se pudo realizar la selecciÃ³n de fechas
	 			* 			false si la selecciÃ³n tuvo alguna complicaciÃ³n
				* @date Abril 2025
				*/
				public boolean seleccionarFechas(int meses){
					boolean result;
					u.mouseOver(xmlRangoFechas);

					if(u.isDisplayed(xmlRangoFechas)) {
						u.highLight(xmlRangoFechas);
						u.mouseOver(xmlRangoFechas);
						u.click(xmlRangoFechas);
						u.unhighLight(xmlRangoFechas);

						u.highLight(xmlBotonFlechaIzquierda);
						u.mouseOver(xmlBotonFlechaIzquierda);
						for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
							u.click(xmlBotonFlechaIzquierda);
						}
						u.unhighLight(xmlBotonFlechaIzquierda);

						if(u.isDisplayed(xmlFechaInicioMes)) {
							u.highLight(xmlFechaInicioMes);
							u.mouseOver(xmlFechaInicioMes);
							u.click(xmlFechaInicioMes);
							u.unhighLight(xmlFechaInicioMes);
						}


						u.highLight(xmlBotonFlechaDerecha);
						u.mouseOver(xmlBotonFlechaDerecha);
						// valor para contemplar 4 meses atras en rango de fechas
						for (int i = 0; i < 4; i++) {
						    WebElement botonDerecho = u.getDriver().findElement(xmlBotonFlechaDerecha);

						    if (botonDerecho.isEnabled()) {
						    	u.click(xmlBotonFlechaDerecha);
						    } else {
						        System.out.println("BotÃ³n deshabilitado, se detiene el avance.");
						        break;
						    }
						}

						u.unhighLight(xmlBotonFlechaDerecha);

						if(u.isDisplayed(xmlUltimoDiaHabilitado)){
							u.highLight(xmlUltimoDiaHabilitado);
							u.mouseOver(xmlUltimoDiaHabilitado);
							u.click(xmlUltimoDiaHabilitado);
						}
						testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

						consultarComprobantes();

						result = true;
					} else {
						result = false;
					}
					return result;
				}


				/**
				 * Scroll al componente del encabezado FA
				 * @author Jose Zarate - 1000072516
				 * @date Noviembre 2024
				 */
				public void scrollToFa() {
					try {
						u.waitWebElement(fa, 60, 2);
						u.hardWait(5);
						u.scrollToElement(fa);
						testCase.addScreenShotCurrentStep("Visualiza registros");
					}catch(Exception ex) {
						System.out.println(" -> " + ex);
					}
				}
				
				/**
				 * Scroll al componente del encabezado Fecha
				 * @author Jose Zarate - 1000072516
				 * @date Noviembre 2024
				 */
				public void scrollToFecha() {
					try {
						u.waitWebElement(fecha, 60, 2);
						u.hardWait(5);
						u.scrollToElement(fecha);
						testCase.addScreenShotCurrentStep("Visualiza registros");
					}catch(Exception ex) {
						System.out.println(" -> " + ex);
					}
				}

				/**
	 			* Metodo para validar el funcionamiento del autocomplete en Filtro Region Directos
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				**/
				public boolean validarAutocompleteRegion(){
					boolean result;
					if(u.isDisplayed(xmlRegion)){
						u.highLight(xmlRegion);
						u.mouseOverAndClick(xmlRegion);
						u.unhighLight(xmlRegion);
						u.sendKeys(xmlRegion,"J");
						testCase.addScreenShotCurrentStep(u, "Se valida el autocomplete");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				/**
				 *	Metodo para validar el funcionamiento del autocomplete en Filtro Region y seleccionar multiples opciones Directos
				 * @author Jorge Hernandez
				 * @date Abril 2025
				 */
				public boolean autocompleteMultiSeleccionRegion(){
					boolean result = false;
					if(u.isDisplayed(xmlRegion)){
						u.highLight(xmlRegion);
						u.click(xmlRegion);
						u.unhighLight(xmlRegion);
						u.sendKeys(xmlInputRegion,"J");

						if(u.isDisplayed(xmlregionOp1)){
							u.highLight(xmlregionOp1);
							u.click(xmlregionOp1);
							u.highLight(xmlregionOp2);
							u.click(xmlregionOp2);
							u.unhighLight(xmlregionOp1);
							u.unhighLight(xmlregionOp2);

							testCase.addScreenShotCurrentStep(u, "Se valida el autocomplete y multi seleccion");
							result = true;
						}
					} else {
						result = false;
					}
					return result;
				}

				/**
	 			* Metodo para validar el funcionamiento del autocomplete en Filtro Tienda Directos
	 			* @author Jorge Hernandez
				* @date Octubre 2024
				*/
				public boolean validarAutocompleteTienda(){
					boolean result;
					u.hardWait(5);

					if(u.isDisplayed(xmlCRTienda)){
						u.highLight(xmlCRTienda);
						u.mouseOverAndClick(xmlCRTienda);
						u.unhighLight(xmlCRTienda);
						u.sendKeys(xmlCRTiendainput,"MTY");
						testCase.addScreenShotCurrentStep(u, "Se valida el autocomplete");

						result = true;
					} else {
						result = false;
					}
					return result;
				}

				  public void SQLResult() {
				        this.rows = new ArrayList<>();
				    }

				    public void addRow(Object[] row) {
				        this.rows.add(row);
				    }

				    public List<Object[]> getRows() {
				        return this.rows;
				    }

					public Boolean validarJerFiltros() {
					    boolean isValid = true; // Inicializamos la bandera en true

					    try {
					    	encontrarElemento(xmlProveedor);
					        encontrarElemento(xmlFuenteDeAbasto);
					        encontrarElemento(xmlZona);
					        encontrarElemento(xmlRegion);
					        encontrarElemento(xmlCRPLazaDirectos);
					        encontrarElemento(xmlCRTienda);
					        encontrarElemento(xmlRangoFechas);
					    } catch (Exception e) {

					        isValid = false; // Si ocurre una excepcion, la bandera se pone en false
					    }

					    return isValid; // Devolvemos la bandera
					}

					/**
					  * Metodo para  eliminar Encabezado del excel individual
					  * @author Yair Lizarraga
					 * @date Octubre 2024
				   **/
				    public Boolean ExcelHeaderValidatorEncabezado(){
				    	String home = System.getProperty("user.home");
				        home = home + "/Downloads";
				        File file = new File(getLastModified(home).getAbsolutePath());
				        String[] expectedHeaders = {
				            "FA", "Nombre del Proveedor", "Region", "Plaza", "CR Tienda", "Nombre de Tienda",
				            "Fecha", "Orden de Compra", "Remision", "Folio GOMA", "Fecha ADMVA",
				            "Total General a la Venta", "Cifra del proveedor", "Movs. Valorizados", "Total Costo"
				        };

				        try {
				            FileInputStream fis = new FileInputStream(file);
				            Workbook workbook = WorkbookFactory.create(fis);
				            Sheet sheet = workbook.getSheetAt(0);
				            Row headerRow = sheet.getRow(0);

				            boolean headersValid = true;

				            for (int i = 0; i < expectedHeaders.length; i++) {
				                // Aplica removerTildes a los valores esperados y reales
				                String actualHeader = removerTildes(headerRow.getCell(i).getStringCellValue());
				                String expectedHeader = removerTildes(expectedHeaders[i]);

				                testCase.addTextEvidenceCurrentStep(
				                    "Los encabezados son correctos se esperaba en el excel: " + expectedHeader +
				                    " y se encontrÃ³: " + actualHeader + ""
				                );

				                if (!expectedHeader.equals(actualHeader)) {
				                    headersValid = false;
				                    System.out.println("Encabezado incorrecto en la columna " + (i + 1) +
				                        ". Se esperaba: " + expectedHeader + " pero se encontrÃ³: " + actualHeader + "");
				                }
				            }

				            if (headersValid) {
				                System.out.println("Los encabezados son correctos");
				                return true;
				            } else {
				                System.out.println("Se encontraron errores en los encabezados");
				            }

				            workbook.close();
				            fis.close();

				        } catch (IOException e) {
				            e.printStackTrace();
				        }

				        return false;
				    }
					/**
		 			* Metodo para validar la busqueda con filtro zona en Directos
		 			* @param meses  -> meses a retroceder
		 			* @author Yair Lizarraga
					* @date Abril 2025
					*/
					public boolean busquedaFiltroZona(int meses){
						 boolean result;
						 if(u.isDisplayed(xmlZona)){
							 u.highLight(xmlZona);
							 u.click(xmlZona);
							 u.unhighLight(xmlZona);
							 u.highLight(xmlZonaOp1);
							 testCase.addScreenShotCurrentStep(u, "Seleccion de zona");
							 u.click(xmlTituloComprobantes);

							 result = true;
						 } else {
							 result = false;
						 }

                        u.click(xmlTituloFiltrosBusquedaComprobantes);
						if(u.isDisplayed(xmlRangoFechas)) {
							u.highLight(xmlRangoFechas);
							u.mouseOver(xmlRangoFechas);
							u.click(xmlRangoFechas);
							u.unhighLight(xmlRangoFechas);

							u.highLight(xmlBotonFlechaIzquierda);
							u.mouseOver(xmlBotonFlechaIzquierda);
							for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
								u.click(xmlBotonFlechaIzquierda);
							}
							u.unhighLight(xmlBotonFlechaIzquierda);

							if(u.isDisplayed(xmlFechaInicioMes)){
								u.highLight(xmlFechaInicioMes);
								u.mouseOver(xmlFechaInicioMes);
								u.click(xmlFechaInicioMes);
								u.unhighLight(xmlFechaInicioMes);
							}

							u.highLight(xmlBotonFlechaDerecha);
							u.mouseOver(xmlBotonFlechaDerecha);
							for (int i = 0; i < 3; i++) { // valor para contemplar x meses atras en rango de fechas
								u.click(xmlBotonFlechaDerecha);
							}
							u.unhighLight(xmlBotonFlechaDerecha);

							if(u.isDisplayed(xmlUltimoDiaHabilitado)){
								u.highLight(xmlUltimoDiaHabilitado);
								u.mouseOver(xmlUltimoDiaHabilitado);
								u.click(xmlUltimoDiaHabilitado);
							}
							testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

							consultarComprobantes();
						}

						 return result;
					}

					/**
		 			* Metodo para validar la busqueda de comprobantes con filtro Region en Directos
					* @param meses -> meses a retroceder
		 			* @author Jorge Hernandez
					* @date Octubre 2024
					*/
					public boolean busquedaMultiZonas(int meses){
						boolean result;
						if(u.isDisplayed(xmlZona)){
							u.highLight(xmlZona);
							u.click(xmlZona);
							u.unhighLight(xmlZona);
							u.highLight(xmlZonaOp1);
							u.click(xmlZonaOp1);

							if(u.isDisplayed(xmlZonaOp2)){
								u.highLight(xmlZonaOp2);
								u.click(xmlZonaOp2);
							}
							testCase.addScreenShotCurrentStep(u, "Seleccion de Zonas");

							result = true;
						} else {
							result = false;
						}

						u.click(xmlTituloFiltrosBusquedaComprobantes);
						if(u.isDisplayed(xmlRangoFechas)) {
							u.highLight(xmlRangoFechas);
							u.mouseOver(xmlRangoFechas);
							u.click(xmlRangoFechas);
							u.unhighLight(xmlRangoFechas);

							u.highLight(xmlBotonFlechaIzquierda);
							u.mouseOver(xmlBotonFlechaIzquierda);
							for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
								u.click(xmlBotonFlechaIzquierda);
							}
							u.unhighLight(xmlBotonFlechaIzquierda);

							if(u.isDisplayed(xmlFechaInicioMes)){
								u.highLight(xmlFechaInicioMes);
								u.mouseOver(xmlFechaInicioMes);
								u.click(xmlFechaInicioMes);
								u.unhighLight(xmlFechaInicioMes);
							}

							u.highLight(xmlBotonFlechaDerecha);
							u.mouseOver(xmlBotonFlechaDerecha);
							for (int i = 0; i < 3; i++) { // valor para contemplar x meses atras en rango de fechas
								u.click(xmlBotonFlechaDerecha);
							}
							u.unhighLight(xmlBotonFlechaDerecha);

							if(u.isDisplayed(xmlUltimoDiaHabilitado)){
								u.highLight(xmlUltimoDiaHabilitado);
								u.mouseOver(xmlUltimoDiaHabilitado);
								u.click(xmlUltimoDiaHabilitado);
							}
							testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");

							consultarComprobantes();

							result = true;
						} else {
							result = false;
						}
						 return result;
					}
					/**
					 * Metodo para  eliminar Region individual
					 * @author Yair Lizarraga
					 * @date Octubre 2024
				   **/
					public boolean eliminarRegionIndv(){
						 boolean result;
						 if(u.isDisplayed(xmlRegion)){
							 u.highLight(xmlRegion);
							 u.mouseOverAndClick(xmlRegion);
							 u.unhighLight(xmlRegion);
							 u.unhighLight(xmlregionOp1);
							 u.click(xmlregionOp1);
							 testCase.addScreenShotCurrentStep(u, "Seleccion de region");

							 result = true;
						 }else {result = false;
					}
						return result;

}
					/**
					  * Metodo para  eliminar Plaza individual
					  * @author Yair Lizarraga
					 * @date Octubre 2024
				   **/
					public boolean eliminarPlazaIndv(){
						 boolean result;
						 if(u.isDisplayed(xmlCRPlaza)){
							 u.highLight(xmlCRPlaza);
							 u.mouseOverAndClick(xmlCRPlaza);
							 u.unhighLight(xmlCRPlaza);
							 u.unhighLight(xmlCRPlazaOp1);
							 u.click(xmlCRPlazaOp1);
							 testCase.addScreenShotCurrentStep(u, "Seleccion de Plaza");

							 result = true;
						 }else {result = false;
					}
						return result;

}
					/**
					 * Metodo para eliminar Tienda individual
					 * @author Yair Lizarraga
					 * @date Octubre 2024
				   **/
					public boolean eliminarTiendaIndv(){
						 boolean result;
						 if(u.isDisplayed(xmlCRTiendainput)){
							 u.highLight(xmlCRTiendainput);
							 u.mouseOverAndClick(xmlCRTiendainput);
							 u.unhighLight(xmlCRTiendainput);
							 u.highLight(xmlCRTiendaOp1);
							 u.click(xmlCRTiendaOp1);
							 u.mouseOverAndClick(xmlCRTiendainput);
							 u.unhighLight(xmlCRTiendaOp1);
							 testCase.addScreenShotCurrentStep(u, "Eliminar tienda");

							 result = true;
						 }else {result = false;
					}
						return result;

}
					/**
					 * Metodo para  eliminar Proveedor individual
					 * @author Yair Lizarraga
					 * @date Octubre 2024
				   **/
					public boolean eliminarProveedorIndv(){
						 boolean result;
						 if(u.isDisplayed(xmlProveedor)){
							 u.highLight(xmlProveedor);
							 u.mouseOverAndClick(xmlProveedor);
							 u.unhighLight(xmlProveedor);
							 u.highLight(xmlProveedorOp1);
							 u.click(xmlProveedorOp1);
							 u.unhighLight(xmlProveedorOp1);
							 u.click(xmlProveedor);
							 u.hardWait(2);
							 testCase.addScreenShotCurrentStep(u, "Seleccion de Proveedor");

							 result = true;
						 }else {result = false;
					}
						return result;

}
					/**
					  * Metodo para  eliminar FA individual
					  * @author Yair Lizarraga
					 * @date Octubre 2024
				   **/
					public boolean eliminarFAIndv(){
						boolean result;
						if(u.isDisplayed(xmlFuenteAbasto)){
							u.mouseOverAndClick(xmlFuenteAbasto);
							u.unhighLight(xmlFuenteAbasto);
							u.highLight(xmlFuenteAbastoOp1);
							u.click(xmlFuenteAbastoOp1);
							testCase.addScreenShotCurrentStep(u, "Seleccion de regiones");

							result = true;
						} else {
							result = false;
						}
						return result;

}
					/**
		 			* Metodo para validar el funcionamiento del autocomplete en Filtro Zona Directos
		 			* @author Yair Lizarraga
					 * @date Octubre 2024
					 **/
					public boolean validarAutocompleteZona(){
						boolean result;
						if(u.isDisplayed(xmlZona)){
							u.highLight(xmlZona);
							u.mouseOverAndClick(xmlZona);
							u.unhighLight(xmlZona);
							u.click(xmlZona);

							u.sendKeys(xmlZonaInput,"PAC");
							testCase.addScreenShotCurrentStep(u, "Se valida el autocomplete");

							result = true;
						} else {
							result = false;
						}
						return result;
					}

					/**
		 			* Metodo para validar la seleccion de Zona despues de seleccionar una por autocomplete en Directos
		 			* @author Jorge Hernandez
					 * @date Octubre 2024
					 **/
					public boolean seleccionAutocompleteZona(){
						boolean result;
						if(u.isDisplayed(xmlZona)){
							u.highLight(xmlZona);
							u.mouseOverAndClick(xmlZona);
							u.unhighLight(xmlZona);
							u.sendKeys(xmlZonaInput,"PAC");
							u.highLight(xmlPlazaOp1);
							u.click(xmlPlazaOp1);
							u.mouseOverAndClick(xmlZona);
							testCase.addScreenShotCurrentStep(u, "Se valida seleccion de tienda");
							u.click(xmlTituloFiltrosBusquedaComprobantes);
							result = true;
						} else {
							result = false;
						}
						return result;
					}

					    /**
						 * Metodo para validar encabezados de archivos masivos
						 * @author Yair Lizarraga
						 * @date Diciembre 2024
						 **/
				       public Boolean abrirYCompararEncabezadosMasivo(){
					        String home = System.getProperty("user.home");
					        home = home + "\\Downloads";
					        File file = new File(getLastModified(home).getAbsolutePath());
					        String[] expectedHeaders = {"FA", "Proveedor", "RegiÃ³n", "Plaza", "CR Tienda", "Nombre de la Tienda",
									"Fecha", "Hora", "Fecha ADMVA", "Compras directas ! FOL- GOMA", "Orden de Compra", "Pedido Adicional", "RemisiÃ³n",
									"Tiempo de estadÃ­a", "CVE Articulo", "DescripciÃ³n","Costo","Precio","UDS (unidades pedidas)","U.COM (unidades compradas)","VALTOT",
									"Total DaÃ±ados","Total Caducos","Total Faltantes"};
					        try {
					            FileInputStream fis = new FileInputStream(file);
					            Workbook workbook = WorkbookFactory.create(fis);
					            Sheet sheet = workbook.getSheetAt(0);
					            Row headerRow = sheet.getRow(0);

					            boolean headersValid = true;
					            for(int i= 0; i <expectedHeaders.length; i++) {
					                String actualHeader = headerRow.getCell(i).getStringCellValue().trim();
					                testCase.addTextEvidenceCurrentStep("Los encabezados son correctos se esperaba en el excel: " + expectedHeaders[i]+" y se encontro: " +actualHeader+ "");
					                if(!expectedHeaders[i].equals(actualHeader)) {
					                    headersValid = false;
					                    System.out.println("Encabezado incorrecto en la columna " + ( i + 1) + " Se esperaba: " + expectedHeaders[i]+ " Pero se encontro " +actualHeader+ "");
					                }
					            }
					            if(headersValid) {
					                System.out.println("Los encabezados son correctos");
					                return true;

					            }else {
					                System.out.println("Se encontraron errores en los encabezados");
					            }

					            workbook.close();
					            fis.close();

					        }catch(IOException e) {
					            e.printStackTrace();
					        }
					        return false;
					    }

						/**
					 	 * Metodo para dar clic en el boton descargar EXCEL resumen
					 	 * @author Yair LizÃƒÂ¡rraga
					 	 * @date Diciembre 2024
					 	 */
						public boolean ClicBotonDescargarResumen(){
							 boolean result;
							 if(u.isDisplayed(btnResumen)){
									u.scrollToElement(btnResumen);
									u.highLight(btnResumen);
									testCase.addScreenShotCurrentStep(u, "clickContainer");
									u.unhighLight(btnResumen);
									u.click(btnResumen);
									u.hardWait(7);
									testCase.addScreenShotCurrentStep(u, "clickContainer");

								 result = true;
							 }else {result = false;
						}
							return result;
						}

						/**
					     * Metodo para ingresar a la pantalla de LiquidaciÃ³n de Ruta de la pantalla
					     * Transferencia en la pantalla de Cedis.
					     *
					     * @author 	Isaac E. GarcÃ­a
						 * @return 	true si se pudo ingresar a la pantalla de LiquidaciÃ³n de Ruta
					     * 			false si hubo algÃºn incombeniente y no se pudo ingresar a LiquidaciÃ³n de Ruta
						 * @date Abril 2025
					     */
					    public boolean CedisRecepcionLiquidacionRuta(){
							boolean result;
				        	if(u.isDisplayed(xmlXpathLiquidacionRutaButton)){
								u.highLight(xmlXpathLiquidacionRutaButton);
								testCase.addScreenShotCurrentStep(u, "Pantalla Recepcion de Transferencias y Devoluciones");
								u.click(xmlXpathLiquidacionRutaButton);
								u.hardWait(5);
								testCase.addScreenShotCurrentStep(u, "Ingresar LiquidaciÃ³n de Ruta");
								//u.unhighLight(xmlXpathLiquidacionRutaButton);

								result = true;
							} else {
								result = false;
							}
					        return result;
					    }

					    /**
					    * MÃ©todo para verificar si una cadena contiene espacios o caracteres especiales.
					    * @author 	Isaac E. GarcÃ­a
					    * @param 	input -> Cadena a evaluar.
					    * @return 	true si la cadena tiene espacios o caracteres especiales
					    * 			false si solo contiene letras y/o nÃºmeros.
						* @date Abril 2025
					    */
					    public boolean contieneEspaciosOCaracteresEspeciales(String input) {
					        if (input == null || input.isEmpty()) {
					            System.out.println("Cadena vacÃ­a o nula.");
					            return true;
					        }

					        // ExpresiÃ³n regular que verifica espacios o caracteres especiales
					        String regex = "[^a-zA-Z0-9]";

					        if (input.matches(".*" + regex + ".*")) {
					            System.out.println("La cadena contiene espacios o caracteres especiales.");
					            return true;
					        } else {
					            System.out.println("La cadena solo contiene letras y/o nÃºmeros.");
					            return false;
					        }
					    }

					    /**
					     * Metodo obtener el valor de un elemento que no es alcanzable con u.getText()
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @param	locator -> es el selector de tipo By que se buscarÃ¡ sacar su .value
					     * @return	Retorna el valor .value de la etiqueta HTML del selector dado.
						 * @date Abril 2025
					     */
					    public String obtenerValue(By locator) {
					        try {
					        	WebElement element = u.getDriver().findElement(locator);
					        	System.out.println("Valor del elemento: "+locator+" -----> ~" + element.getAttribute("value")+"~ #####");
					            return element.getAttribute("value");
					        } catch (Exception e) {
					            System.out.println("Error al obtener el texto del input: " + e.getMessage());
					            return null;
					        }
					    }

					    /**
					     * Metodo para verificar que el input de Ruta en la pantalla LiquidaciÃ³n de Ruta
					     * cumpla con no tomar caracteres especiales o espacios
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @param	input -> Es la cadena que se va a ingresar a evaluar
					     * @return	true si los datos de entrada vienen con caracteres no permitidos y
					     * 				no se refleja en el campo
					     * 			false si los datos de entrada no son vÃ³lidos o si la cadena de texto
					     * 				se refleja con caracteres especiales/espacios
						 * @date Abril 2025
					     */
					    public boolean validarInputRuta(String input, boolean cadenaEspecial){
							boolean result;
							String output;

							if(!contieneEspaciosOCaracteresEspeciales(input) && cadenaEspecial == true)
								return false;

				        	if(u.isDisplayed(xmlInputRuta)){
								u.highLight(xmlInputRuta);
								testCase.addScreenShotCurrentStep(u, "Pantalla Recepcion de LiquidaciÃ³n de Ruta");
								u.sendKeys(xmlInputRuta, input);
								output=obtenerValue(xmlInputRuta);
								testCase.addScreenShotCurrentStep(u, "Se ingresa texto en el campo");
								u.unhighLight(xmlInputRuta);

								if(contieneEspaciosOCaracteresEspeciales(output))
									return false;
								else
									if(output==input)
										return false;

								testCase.addScreenShotCurrentStep(u, "Se intentÃ³ ingresar la cadena de texto: "+input+" y se registrÃ³ "+output+"");
								result = true;
							} else {
								result = false;
							}
					        return result;
					    }

					    /**
					     * Metodo para verificar que un campo sea visible
					     * @author	Isaac E. GarcÃ­a
					     * @param 	name -> 	Nombre del elemento a buscar visibilidad
					     * @param 	click -> 	Marca si hay que hacer clic al elemento o no
					     * @return	true si el elemento es visible
					     * 			false si el elemento a buscar no estÃ¡ dentro de los parÃ¡metros
					     * 				o no es visible el elemento
					     * @date Abril 2025
					     */
					    public boolean VisibilidadElementoLiquidacionRuta(String name, boolean click){
							boolean result, isSelect=false, isMultiple=false;
							By element = null, option=null;
							int options= 0;
							try {
								switch(name) {
								case "LiquidacionLabel":
									element=xmlPantallaLiquidacion;
								break;
								case "BotonLiquidacionRuta":
									element = xmlXpathLiquidacionRutaButton;
								break;
								case "MenuSuperiorUsuario":
									element = xmlMenuSuperiorUsuario;
								break;
								case "MenuSuperior":
									isMultiple = true;
									element = xmlMenuSuperior;
									options=5;
								break;
								case "BotonRecepcion":
									element = xmlRecepcionButton;
								break;
								case "Ruta":
									element = xmlInputRuta;
									break;
								case "FechaInicio":
									isSelect = true;
									options=4;
									element = xmlSelectFechainicio;
									break;
								case "FechaFin":
									isSelect = true;
									options=4;
									element = xmlSelectFechaFin;
									break;
								case "HoraInicial":
									isSelect = true;
									options=24;
									element = xmlSelectHoraInicio;
									break;
								case "HoraFinal":
									isSelect = true;
									options=24;
									element = xmlSelectHoraFin;
									break;
								case "LimpiarFiltroButton":
									element = xmlLimpiarFiltroButton ;
								break;
								case "ConsultaButton":
									element = xmlConsultarRutaButton ;
								break;
								case "PantallaDetalle":
									element = xmlDetalleRuta;
								break;
								case "PantallaDetalleTiendas":
									element = xmlDetalleTiendas;
								break;
								case "ClasificacionColor":
									element = xmlClasificacionColor;
								break;
								case "ClasificacionColorInfo":
									element = xmlClasificacionColorInfo;
								break;
								case "DetalleGeneral":
									element = xmlDetalleGeneral;
								break;
								case "ConsultaSinDatos":
									element = xmlLabelConsultaSinDatos;
								break;
								default:
									System.out.println("Elemento no definido para validar visualizaciÃ³n");
									return false;
							}
				        	if(u.isDisplayed(element)){
								u.highLight(element);
								testCase.addScreenShotCurrentStep(u, "Elemento encontrado");
								u.unhighLight(element);
								if(click)
									u.scrollToElement(element);
									u.click(element);
								u.waitForLoadPage();
								u.hardWait(3);
								if(isSelect || isMultiple){
									for(int i =0; i<options; i++) {

										if(isSelect)
											option = By.xpath("//mat-option["+(i+1)+"]");
										if(isMultiple)
											option = By.xpath("//nav[div][@class='nav-bar']/div["+(i+1)+"]");

										if(u.isDisplayed(element))
											u.highLight(option);
										else
											result=false;
									} //End for

								if(isSelect) {
									option = By.xpath("//mat-option["+(options)+"]");
									u.click(option);
								}

								}

								if(name.equals("ConsultaButton"))
									//Validacion de resultados en pantalla
									if(u.isDisplayed(xmlTablaTiendasLiquidacionRuta)){
										u.scrollToElement(xmlTablaTiendasLiquidacionRuta);
										u.highLight(xmlTablaTiendasLiquidacionRuta);
										testCase.addScreenShotCurrentStep(u, "Busqueda exitosa");
										u.unhighLight(xmlTablaTiendasLiquidacionRuta);
										result = true;
									} else {
										result = false;
									}
									result = true;
								}
				        		else {
									result = false;
								}

							}
							catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								result = false;
							}

					        return result;
					    }

					    /**
					     * Metodo para ingresar datos al apartado de filtro en Liquidacion de Ruta
					     * @author	Isaac E. GarcÃ­a
					     * @param 	name 	-> 	Nombre del elemento a dar input
					     * @param 	option 	-> 	PosiciÃ³n del elemento que se elige
					     * @return	true si el elemento es visible y se pudo realizar la selecciÃ³n del elemento
					     * 			false si el elemento a buscar no estÃ¡ dentro de los parÃ¡metros
					     * 				o no es visible el elemento
					     * @date Abril 2025
					     */
					    public boolean inputFiltroLiquidacionRuta(String name, int option){
							boolean result, isSelect;
							By element = null, optionSelected=null;
							int options=0;

							switch(name) {
								case "FechaInicio":
									isSelect = true;
									options=4;
									element = xmlSelectFechainicio;
									break;
								case "FechaFin":
									isSelect = true;
									options=4;
									element = xmlSelectFechaFin;
									break;
								case "HoraInicial":
									isSelect = true;
									options=24;
									element = xmlSelectHoraInicio;
									break;
								case "HoraFinal":
									isSelect = true;
									options=24;
									element = xmlSelectHoraFin;
									break;
								default:
									System.out.println("Elemento no definido para validar visualizaciÃ³n");
									return false;
							}
							if(option>options) {
								System.out.println("La opcion elegida estÃ¡ fuera del rango de las opciones permitidas.");
								return false;
							}

				        	if(u.isDisplayed(element)){
								u.highLight(element);
								testCase.addScreenShotCurrentStep(u, "Elemento visible");
								u.click(element);
								u.hardWait(1);

								optionSelected = By.xpath("//mat-option["+(option)+"]");
								u.click(optionSelected);
								testCase.addScreenShotCurrentStep(u, "El elemento es visible");
								u.unhighLight(element);

								result = true;
							} else {
								result = false;
							}
					        return result;
					    }

					    /**
					     * Metodo para realizar una consulta aleatoria en Liquidacion de Ruta
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @param	rutaDinamica -> Es la cadena que se va a ingresar a evaluar
					     * @return	true si se pudo realizar la consulta y devuelve informaciÃ³n
					     * 				o muestra la leyenda de no hay informaciÃ³n
					     * 			false si no se puede realizar la consulta
						 * @date Abril 2025
					     */
					    public boolean buscarLiquidacionRuta(String rutaDinamica){
							boolean result;
							validarInputRuta(rutaDinamica, false);

							//Ingresar fecha inicio y fecha final
							inputFiltroLiquidacionRuta("FechaInicio",4);
							inputFiltroLiquidacionRuta("FechaFin",1);

							if(u.isDisplayed(xmlConsultarRutaButton)) {
								u.highLight(xmlConsultarRutaButton);
								u.click(xmlConsultarRutaButton);
								u.hardWait(5);

								u.highLight(xmlDetalleRuta);
								testCase.addScreenShotCurrentStep(u, "Se pudo realizar la consulta satisfactoriamente");
								u.unhighLight(xmlDetalleRuta);
							}
							else {
								System.out.println("No se encuentra visible el botÃ³n de consultar");
								return false;
							}

							result=true;

							return result;
					    }

					    /**
					     * Metodo para obtener la cantidad total de una clasificacion segun lo devuelto por la pagina
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @param	textoBuscado	-> Es el texto antes de los : que se busca
					     * @return	Devuelve el valor en forma de cadena de lo que se muestra despues del : del elemento a buscar
						 * @date Abril 2025
					     */
					    public String obtenerDatoDelTotal(String textoBuscado){
					    	System.out.println("##### Obteniendo el valor de: "+textoBuscado);
					    	if(obtenerCantidadTiendas()==0) {
					    		testCase.addScreenShotCurrentStep(u, "No existe apartado de totales; la consulta no devuelve tiendas");
					    		return null;
					    	}

					    	try {
					    		System.out.println("//div[@id='detalle-ruta'] //p[contains(text(), '" + textoBuscado + "')]");
					    		//u.hardWait(20);
					    		List<WebElement> elementos = u.getDriver().findElements(By.xpath("//div[@id='detalle-ruta'] //p[contains(text(), '" + textoBuscado + "')]"));

					            if(textoBuscado=="Total Art" || textoBuscado=="Canastillas con" ) {
					            	u.highLight(By.xpath("//div[@id='detalle-ruta'] //p[contains(text(), '" + textoBuscado+"')]"));
					            	testCase.addScreenShotCurrentStep(u, "El elemento "+textoBuscado+" es visible");
					            	u.unhighLight(By.xpath("//div[@id='detalle-ruta'] //p[contains(text(), '" + textoBuscado + "')]"));
					            }

					            else {
					            	u.highLight(By.xpath("//div[@id='detalle-ruta'] //p[contains(text(), '" + textoBuscado + "')]"));
					            	testCase.addScreenShotCurrentStep(u, "El elemento "+textoBuscado+" es visible");
					            	u.unhighLight(By.xpath("//div[@id='detalle-ruta'] //p[contains(text(), '" + textoBuscado + "')]"));
					            }

					            for (WebElement elemento : elementos) {
					                String textoCompleto = elemento.getText().trim();

					                // Caso 1: Si el elemento tiene id "headerRuta", obtener el siguiente <p>
					                if (elemento.getAttribute("id") != null && elemento.getAttribute("id").equals("headerRuta")) {
					                    WebElement siguienteElemento = elemento.findElement(By.xpath("following-sibling::p[1]"));

					                    return siguienteElemento.getText().trim();
					                }

					                // Caso 2: Si el texto tiene ":", verificar dÃ³nde estÃ¡ el valor
					                if (textoCompleto.contains(":")) {
					                	String[] partes = textoCompleto.split(":");
					                	if (partes.length > 1) {
					                		return partes[1].trim();
					                	}
					                }
					            }

					            System.out.println("No se encontrÃ³ el dato para: " + textoBuscado);
					            return null;
					        } catch (Exception e) {
					            System.out.println("Error al obtener el dato: "+textoBuscado + e.getMessage());
					            return null;
					        }
					    }

					    /**
					     * Metodo para obtener la cantidad de tiendas de una consulta en LiquidaciÃ³n de Ruta
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @return	Cantidad en entero de las tiendas existentes al hacer una consulta
						 * @date Abril 2025
					     */
					    public int obtenerCantidadTiendas(){
							int cantidad=0;
							try {
								if(VisibilidadElementoLiquidacionRuta("PantallaDetalle", false)) {
									List<WebElement> element = u.getDriver().findElements(xmlTiendas);
									cantidad = element.size();
									System.out.println("#########"
													  +"Se encontraron "+cantidad+" tienda(s)"
													  + "#########");
								}
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println("#########"
												 + "Error al realizar la consulta con los filtros"
												 + "#########");
							}
							return cantidad;
					    }

					    /**
					     * Metodo para contabilizar que lo mostrado en detalle (total) sea igual al sumarizado
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @param	textoBuscado -> Elemento que se buscarÃ¡ contabilizar su total
					     * @return	true si la contabilizaciÃ³n de lo mostrado en las tiendas coincide con el total mostrado
					     * 			false si la contabilizaciÃ³n no coincide con el total mostrado en la pagina
						 * @date Abril 2025
					     */

					    public boolean contarTiendasConTexto(String textoBuscado) {
					        boolean result;
					        int contador = 0, index, total = 1;

					        try {
					        	List<WebElement>  tiendas = u.getDriver().findElements(By.id("tienda"));
					            int totalTiendas = obtenerCantidadTiendas();

					            if (tiendas.size() != totalTiendas) {
					                System.out.println("Advertencia: NÃºmero de tiendas encontradas no coincide con el esperado.");
					            }

					            index = 0;
					            for (WebElement tienda : tiendas) {
					                List<WebElement> parrafos = tienda.findElements(By.tagName("p"));
					                index++;

					                By tiendaLocator = By.xpath("//div[@id='tienda'][" + index + "]");
					                u.scrollToElement(tiendaLocator);
					                u.hardWait(1);
					                u.highLight(tiendaLocator);
					                testCase.addScreenShotCurrentStep(u, "Valor de " + textoBuscado + " en la tienda #" + index);
					                u.unhighLight(tiendaLocator);

					                for (WebElement parrafo : parrafos) {
					                    String textoParrafo = parrafo.getText();


					                    // Normalizar ambos textos
					                    String normalizadoParrafo = removerTildes(textoParrafo.toLowerCase());
					                    String normalizadoBuscado = removerTildes(textoBuscado.toLowerCase());
					                    System.out.println("Texto buscar: " + normalizadoBuscado + " Parrafo: " + normalizadoParrafo);

					                    if (normalizadoParrafo.contains(normalizadoBuscado)) {
					                        //Obtener el valor que se muestra e irlo sumarizando al contador
						                	String[] partes = parrafo.getText().split(":");
						                	if (partes.length > 1) {
						                		contador+= Integer.valueOf(partes[1].trim());
						                	}
						                	System.out.println("##### Parrafo seleecionado: " + normalizadoParrafo);
				    	                    break;
					                    }
					                }
					            }

					        } catch (Exception e) {
					            System.out.println("Error al contar tiendas con el texto: " + e.getMessage());
					        }


				    	    //Casos especiales
				    	    switch(textoBuscado) {
				    	    	case "Canastillas Totales Devueltas de Tienda":
				    	    		textoBuscado="Canastillas Totales de la Ruta";
				    	    	break;
				    	    	case "Total Sobrantes":
				    	    		textoBuscado="Total Cajas y Canastillas Sobrantes";
				    	    	break;
				    	    	case "Total Danados":
				    	    		textoBuscado="Total Art";
				    	    	break;
				    	    	case "Canastillas con Devolucion":
				    	    		textoBuscado="Canastillas con";
				    	    	break;
				    	    }

					        total = Integer.valueOf(obtenerDatoDelTotal(textoBuscado));

					        if (contador == total) {
					            result = true;
					        } else {
					            System.out.println("El total de " + textoBuscado + " no coincide. \nSe contabilizaron: " + contador + " Y en total son " + total + ".");
					            result = false;
					        }

					        return result;
					    }

					    /** FunciÃ³n para remover tildes
						 * @author 	Isaac E. GarcÃ­a
						 * @date Abril 2025
						 */
					    public String removerTildes(String texto) {
					        return Normalizer.normalize(texto, Normalizer.Form.NFD)
					                         .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
					    }

				    	 /**
					     * Metodo para contabilizar de tiendas sea igual al mostrado y muestra tienda por tienda
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @return	true si las tiendas contabilizadas son igual al total mostrado
					     * 			false si las tiendas contabilizadas no coincide con el total mostrado
						 * @date Abril 2025
					     */
				    	public boolean contarTiendas() {
				    		boolean result;
				    		int contador = 0, index, totalTiendas=0;

				    	    try {
				    	    	totalTiendas = obtenerCantidadTiendas();
				    	    	List<WebElement> tiendas = u.getDriver().findElements(By.id("tienda"));

				    	        if (tiendas.size() != totalTiendas) {
				    	            System.out.println("Advertencia: NÃºmero de tiendas encontradas no coincide con el esperado.");
				    	        }

				    	        index=0;
				    	        for (WebElement tienda : tiendas) {
				    	            List<WebElement> parrafos = tienda.findElements(By.tagName("p"));

				    	            index++;
				    	            By tiendaLocator = By.xpath("//div[@id='tienda']["+index+"]");
				    	            u.scrollToElement(tiendaLocator);
				    	            u.hardWait(1);// PequeÃ±a pausa para asegurar la visibilidad
				    	            u.highLight(tiendaLocator);
				    	            testCase.addScreenShotCurrentStep(u, "Tienda :"+index);
				    	            u.unhighLight(tiendaLocator);
				    	            contador++;
				    	        }
				    	    } catch (Exception e) {
				    	        System.out.println("Error al contar tiendas con el texto: " + e.getMessage());
				    	    }



				    	    if(contador==totalTiendas) {
				    	    	if(contador==0 && totalTiendas==0)
				    	    		result=false;
				    	    	else
				    	    		result=true;
				    	    }
				    	    else {
				    	    	System.out.println("El total de tiendas no coincide. \nSe contabilizaron: "+contador+" Y en total son "+totalTiendas+".");
				    	    	result=false;
				    	    }

				    	    return result;
				    	}

				    	/**
					     * Metodo para verificar que el formato mostrado en cada una de las tiendas atiende
					     * al formato que debe de ser.
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @return	true si el formato de todas las tiendas atiende al formato
					     * 			false si al menos una de las tiendas no atiende con el formato
						 * @date Abril 2025
					     */
				    	public boolean validarFormatoNombreTienda() {
				    		boolean result=false;
				    		// ExpresiÃ³n regular para validar el formato
				    		String regex = "^Tienda: \\d+ [A-Z0-9]+ .+\\. Transferencia: \\d+\\. Pedido entregado con COMETA$";

				    		int index, totalTiendas=0;
				    		String nombreTienda=null;


				    	    try {
				    	    	totalTiendas = obtenerCantidadTiendas();
				    	    	List<WebElement> tiendas = u.getDriver().findElements((xmlTiendaNombre));

				    	        if (tiendas.size() != totalTiendas) {
				    	            System.out.println("Advertencia: NÃºmero de tiendas encontradas no coincide con el esperado.");
				    	        }

				    	        index=0;
				    	        for (WebElement tienda : tiendas) {
				    	            index++;
				    	            By tiendaLocator = By.xpath("(//div[@id='tienda']/div/p[contains(text(), 'Tienda')])["+index+"]");
				    	            u.scrollToElement(tiendaLocator);
				    	            u.hardWait(1);// PequeÃ±a pausa para asegurar la visibilidad
				    	            u.highLight(tiendaLocator);
				    	            testCase.addScreenShotCurrentStep(u, "Tienda :"+index);

				    	            nombreTienda=u.getText(tiendaLocator);


				    	            if (nombreTienda.matches(regex)) {
							            System.out.println("El formato de nombre de la tienda "+index+" sÃ­ coincide.");
							            result= true;
							        } else {
							            System.out.println("El formato de nombre de la tienda "+index+" no coincide.");
							            result = false;
							        }

				    	            u.unhighLight(tiendaLocator);
				    	        }
				    	    } catch (Exception e) {
				    	        System.out.println("Error al contar tiendas con el texto: "+nombreTienda+"\n"+ e.getMessage());
				    	    }

				    	    return result;
				    	}

				    	/**
					     * Metodo  validar el orden en el que se muestra la informaciÃ³n sea correcta; arriba los
					     * tÃ­tulos y abajo su detalle (en casos que asÃ­ lo aplique)
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @return	true si el orden de la infromaciÃ³n en las tiendas sea correcto
					     * 			false si el orden de la informaciÃ³n no es correcto
						 * @date Abril 2025
					     */
				    	public boolean validarOrdenCanastillas() {
				    	    boolean ordenCorrecto = true;

				    	    List<WebElement> tiendas = u.getDriver().findElements(By.id("tienda"));

				    	    for (int i = 0; i < tiendas.size(); i++) {
				    	        WebElement tienda = tiendas.get(i);

				    	        // Ubicar elementos dentro de la tienda actual
				    	        WebElement canastillasTotales = tienda.findElement(By.xpath(".//p[contains(text(), '#Canastillas Totales Devueltas de Tienda')]"));
				    	        WebElement canastillasAzules = tienda.findElement(By.xpath(".//p[contains(text(), '#Canastillas Azules')]"));
				    	        WebElement canastillasDevolucion = tienda.findElement(By.xpath(".//p[contains(text(), '#Canastillas con Devoluci')]"));
				    	        WebElement bols = tienda.findElement(By.xpath(".//p[contains(text(), 'Bols')]"));

				    	        // Obtener posiciones Y de los elementos
				    	        int yTotales = canastillasTotales.getLocation().getY();
				    	        int yAzules = canastillasAzules.getLocation().getY();
				    	        int yDevolucion = canastillasDevolucion.getLocation().getY();
				    	        int yBols = bols.getLocation().getY();

				    	        // Validar el orden esperado
				    	        if (yTotales >= yAzules) {
				    	            System.out.println("Error en tienda " + (i + 1) + ": Canastillas Totales Devueltas no estÃ¡ arriba de Canastillas Azules.");
				    	            ordenCorrecto = false;
				    	        }

				    	        if (yDevolucion >= yBols) {
				    	            System.out.println("Error en tienda " + (i + 1) + ": Canastillas con DevoluciÃ³n no estÃ¡ arriba de Bols.");
				    	            ordenCorrecto = false;
				    	        }
				    	    }

				    	    return ordenCorrecto;
				    	}

				    	/**
					     * Metodo para contabilizar el total de bols que existen en la consulta
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @return	true si concide el total mostrado con lo contabilizado
					     * 			false si no coincide el total mostrado con lo contabilizado
						 * @date Abril 2025
					     */
				    	public boolean contarTotalBols() {
				    	    boolean result;
				    	    int totalBols = 0, bolsHeader = 0;
				    	 
				    	    List<WebElement> tiendas = u.getDriver().findElements(By.id("tienda"));
				    	 
				    	    for (int i = 0; i < tiendas.size(); i++) {
				    	        WebElement tienda = tiendas.get(i);
				    	 
				    	        try {
				    	            WebElement bolsLabel = tienda.findElement(By.xpath(".//p[contains(text(), 'Bols')]"));
				    	            WebElement bolsValueElement = bolsLabel.findElement(By.xpath("./following-sibling::p"));
				    	            String bolsTexto = bolsValueElement.getText().replaceAll("[^0-9,]", "").trim();
				    	 
				    	            if (!bolsTexto.isEmpty()) {
				    	                String[] valores = bolsTexto.split(",");
				    	                totalBols += valores.length;
				    	                System.out.println("Tienda " + (i + 1) + ": " + bolsTexto + " Bols encontrados: " + valores.length);
				    	            } else {
				    	                System.out.println("Tienda " + (i + 1) + ": Sin valores de Bols.");
				    	            }
				    	 
				    	        } catch (Exception e) {
				    	            System.out.println("Tienda " + (i + 1) + ": No se encontrÃ³ el campo 'Bols'.");
				    	        }
				    	    }
				    	 
				    	    System.out.println("Total de Bols en todas las tiendas: " + totalBols);
				    	    bolsHeader = Integer.parseInt(obtenerDatoDelTotal("Bols Totales"));
				    	    System.out.println("Total de Bols header " + bolsHeader);
				    	 
				    	    result = (totalBols == bolsHeader);
				    	    return result;
				    	}

				    	/**
					     * Metodo para contabilizar de tiendas sea igual al mostrado
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @param	Elemento	->	Nombre del elemento para asociarlo al xPath (si es que existe)
					     * @return	true si el elemento en la tienda es visible
					     * 			false si no es visible o si el Elemento no es una opciÃ³n vÃ¡lida
						 * @date Abril 2025
					     */
				    	public boolean validarElementoEnTiendas(String Elemento) {
						    By elemento =null;
						    boolean result=false;
					    		switch(Elemento) {
					    	    	case "Bols":
					    	    		elemento=xmlbolsLabel;
					    	    	break;
					    	    	default:
					    	    		System.out.println("El elemento dado para validarElementoEnTiendas no existe dentro de las opciones.");
					    	    		return false;
					    	    }


			    			List<WebElement> tiendas = u.getDriver().findElements(By.id("tienda"));

			    		    for (int i = 0; i < tiendas.size(); i++) {
			    		        try {

			    		            if (u.isDisplayed(elemento)) {
			    		                System.out.println("El elemento en la Tienda " + (i + 1) + " es visible.");
			    		                result=true;
			    		            } else {
			    		                System.out.println("El elemento en la Tienda " + (i + 1) + " NO es visible.");
			    		                result=false;
			    		            }

			    		        } catch (NoSuchElementException e) {
			    		            System.out.println("El elemento no se encontrÃ³ en la Tienda " + (i + 1) + ".");
			    		            result=false;
			    		        }
			    		    }

			    		    return result;
				    	}


				    	 /**
					     * MÃ©todo para validar una consultar si la fecha fin aparece sin datos
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @return	true si la fecha final no tiene datos
					     * 			false si existe algun error
						 * @date Abril 2025
					     */
					    public boolean validarConsultaFecha() {
					    	boolean result=false;
					        try {
						    	VisibilidadElementoLiquidacionRuta("ConsultaButton", true);
						    	if(obtenerValue(xmlSelectFechaFin)==null)
						    		result=true;
					        } catch (Exception e) {
					            System.out.println("Error al obtener el texto del input: " + e.getMessage());
					            return false;
					        }
					        return result;
					    }

					    /**
					     * MÃ©todo para poder verificar que al utilizar el botÃ³n de Limpiar Filtro en LiquidaciÃ³n
					     * 	de Ruta elimina todo y se deja de ver, en caso de que lo hubiera, informaciÃ³n desplegada
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @param	//locator -> es el selector de tipo By que se buscarÃ¡ sacar su .value
					     * @return	true si no se muestra informaciÃ³n al dar clic al botÃ³n y que haya podido ser alcanzado
					     * 			false si se muestra informaciÃ³n despugs de dar clic al botÃ³n o el botÃ³n no es alcanzable
						 * @date Abril 2025
					     */
					    public boolean validarLimpiarFiltro() {
					    	boolean result=false;
					        try {
					        	result = VisibilidadElementoLiquidacionRuta("LimpiarFiltroButton", true);
					        	result &= !VisibilidadElementoLiquidacionRuta("PantallaDetalle", false);
					        	
					        	//Esperar a que se limpie el filtro y tomar evidencia
					        	
					        	u.hardWait(2);
					        	testCase.addScreenShotCurrentStep(u, "Filtros limpiados");
					        	
					        } catch (Exception e) {
					            System.out.println("Error al validar la limpieza del filtro " + e.getMessage());
					            return false;
					        }
					        return result;
					    }

					    /**
					     * Metodo para verificar cuantas transferencias unicas existen de cada
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @return	El nÃºmero de transferencias unicas
						 * @date Abril 2025
					     */
					    public int contarTransferenciasUnicas() {
					    		List<WebElement> tiendas = u.getDriver().findElements(By.id("tienda"));
					    	    Set<String> transferenciasUnicas = new HashSet<>();

					    	    for (WebElement tienda : tiendas) {
					    	        try {
					    	            WebElement textoTienda = tienda.findElement(By.xpath(".//p[contains(text(), 'Transferencia:')]"));
					    	            String texto = textoTienda.getText();

					    	            String[] partes = texto.split("Transferencia: ");
					    	            if (partes.length > 1) {
					    	                String transferencia = partes[1].split("\\.")[0].trim(); 					    	                transferenciasUnicas.add(transferencia);
					    	            }
					    	        } catch (NoSuchElementException e) {
					    	            System.out.println("No se encontrÃ³ informaciÃ³n de transferencia en una tienda.");
					    	        }
					    	    }

					    	    System.out.println("Total de transferencias Ãºnicas: " + transferenciasUnicas.size());
					    	    return transferenciasUnicas.size();
					    }


					    /**
					     * Metodo para saber si existe al menos una transferencia repetida en una de las tiendas
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @return	true si esxiste al menos una transferencia repetida
					     * 			false si todas son unicas.
						 * @date Abril 2025
					     */
					    public boolean hayTransferenciasRepetidas() {
					    		imprimirEvidenciaTiendas();
					    		List <WebElement> tiendas = u.getDriver().findElements(By.id("tienda"));
					    	    Set<String> transferencias = new HashSet<>();
					    	    boolean hayRepetidas = false;

					    	    for (WebElement tienda : tiendas) {
					    	        String textoTienda = tienda.getText();
					    	        String transferencia = obtenerTransferencia(textoTienda);

					    	        if (transferencias.contains(transferencia)) {
					    	            hayRepetidas = true;
					    	            break;
					    	        }
					    	        transferencias.add(transferencia);
					    	    }
					    	    return hayRepetidas;
					    	}


					    /**
					     * MÃ©todo para extraer solo el nÃºmero de la transferenciatextos
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @param	textoTienda	->	Extrae la transferencia del texto
					     * @return	El nÃºmero de transferencia
						 * @date Abril 2025
					     */
				    	private String obtenerTransferencia(String textoTienda) {
				    	    String[] partes = textoTienda.split("Transferencia: ");
				    	    if (partes.length > 1) {
				    	        return partes[1].split("\\.")[0];
				    	    }
				    	    return "";
				    	}

				    	/**
					     * MÃ©todo para verificar que el orden de las tiendas sea ordenado por
					     * 	tienda y por fecha de la mÃ¡s reciente a la menos.
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @return	true si el orden cumple las expectativas
					     * 			false si el orden no cumple las expectativas o la informaciÃ³n no es suficiente
						 * @date Abril 2025
					     */
				    	public boolean verificarOrdenTiendas() {
				    	    String dbUrl = GlobalVariables.DB_URL_Cometa;
				    	    String dbUser = GlobalVariables.DB_USER_Cometa;
				    	    String dbPassword = GlobalVariables.DB_PASSWORD_Cometa;

				    	    String query = "SELECT id, cr_plaza, cr_tienda, ruta_propuesta, fecha_registro " +
				    	                   "FROM public.xxcm_historico_correos " +
				    	                   "WHERE fecha_registro >= CURRENT_DATE - INTERVAL '3 days' " +
				    	                   "AND fecha_registro <= CURRENT_DATE " +
				    	                   "AND ruta_propuesta = 'NCD002' " +
				    	                   "AND version_documento = 2 " +
				    	                   "AND id_retek IS NOT NULL " +
				    	                   "AND is_visible = true " +
				    	                   "ORDER BY cr_tienda, fecha_registro DESC";

				    	    imprimirEvidenciaTiendas();
				    	    imprimirQuery(query);

				    	    try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				    	         Statement stmt = connection.createStatement();
				    	         ResultSet rs = stmt.executeQuery(query)) {

				    	        Map<String, List<Timestamp>> tiendasFechas = new HashMap<>();

				    	        while (rs.next()) {
				    	        	System.out.println("WHILE");

				    	            String tienda = rs.getString("cr_tienda");
				    	            Timestamp fecha = rs.getTimestamp("fecha_registro");

				    	            tiendasFechas.putIfAbsent(tienda, new ArrayList<>());
				    	            tiendasFechas.get(tienda).add(fecha);
				    	        }

				    	        int index = 0;
				    	        for (Map.Entry<String, List<Timestamp>> entry : tiendasFechas.entrySet()) {
				    	            List<Timestamp> fechas = entry.getValue();
				    	            index++;

				    	            System.out.println("FOR");
				    	            // Agregar evidencia visual
				    	            By tiendaLocator = By.xpath("//div[@id='tienda'][" + index + "]");
				    	            u.scrollToElement(tiendaLocator);
				    	            u.hardWait(1);
				    	            u.highLight(tiendaLocator);
				    	            testCase.addScreenShotCurrentStep(u, "Tienda: " + entry.getKey());
				    	            u.unhighLight(tiendaLocator);

				    	            // Verificar el orden de las fechas
				    	            for (int i = 0; i < fechas.size() - 1; i++) {
				    	                if (fechas.get(i).before(fechas.get(i + 1))) {
				    	                    System.out.println("Error: La tienda " + entry.getKey() + " no estÃ¡ en orden descendente.");
				    	                    return false;
				    	                }
				    	            }
				    	        }
				    	        return true;

				    	    } catch (Exception e) {
				    	    	System.out.println("catch");
				    	        e.printStackTrace();
				    	        return false;
				    	    }
				    	}

				    	/**
					     * MÃ©todo imprimir en evidencia un query
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @param	query	->	Query a imprimir
						 * @date Abril 2025
					     */
				    	public void imprimirQuery(String query) {
				    		String dbUrl = GlobalVariables.DB_URL_Cometa;
				    	    String dbUser = GlobalVariables.DB_USER_Cometa;
				    	    String dbPassword = GlobalVariables.DB_PASSWORD_Cometa;
				    		 try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
				    	                Statement stmt = connection.createStatement();
				    	                ResultSet rs = stmt.executeQuery(query)) {

				    	            // Obtener metadatos de la consulta
				    	            ResultSetMetaData metaData = rs.getMetaData();
				    	            int columnCount = metaData.getColumnCount();

				    	            // Crear un StringBuilder para almacenar los resultados
				    	            StringBuilder resultBuilder = new StringBuilder();

				    	            // Crear la fila de encabezado con formato justificado
				    	            for (int i = 1; i <= columnCount; i++) {
				    	                resultBuilder.append(String.format("%-20s", metaData.getColumnName(i)));
				    	            }
				    	            resultBuilder.append("\n");

				    	            // Rellenar las filas con los datos con formato justificado
				    	            while (rs.next()) {
				    	                for (int i = 1; i <= columnCount; i++) {
				    	                    resultBuilder.append(String.format("%-20s", rs.getString(i)));
				    	                }
				    	                resultBuilder.append("\n");
				    	            }

				    	               // Imprimir los resultados en la evidencia
				    	               testCase.addTextEvidenceCurrentStep(resultBuilder.toString());

				    	           } catch (Exception e) {
				    	               e.printStackTrace();
				    	           }
				    	}


				    	/**
					     * MÃ©todo para obtener el id de la tienda
					     *
					     * @author 	Isaac E. GarcÃ­a
					     * @return	Devuelve el Id de la tienda
						 * @date Abril 2025
					     */
				    	private String obtenerIdTienda(String textoTienda) {
				    	    String[] partes = textoTienda.split("Tienda: ");
				    	    if (partes.length > 1) {
				    	        return partes[1].split(" ")[0];
				    	    }
				    	    return "";
				    	}


				    	/**
				    	* MÃ©todo para recorrer todas las tiendas visibles en la pÃ¡gina y agregarlas a la evidencia.
				    	* Captura una captura de pantalla de cada tienda y la adjunta a la evidencia del test.
				    	* @author Isaac E. GarcÃ­a
						* @date Abril 2025
				    	*/
				    	public void imprimirEvidenciaTiendas() {
				    		contarTiendas();
				    	    List<WebElement> tiendas =  u.getDriver().findElements(By.xpath("//div[@id='tienda']"));
				    	    int index = 0;

				    	    for (WebElement tienda : tiendas) {
				    	        index++;
				    	        By tiendaLocator = By.xpath("//div[@id='tienda'][" + index + "]");

				    	        u.scrollToElement(tiendaLocator);
				    	        u.hardWait(1); // PequeÃ±a pausa para asegurar visibilidad
				    	        u.highLight(tiendaLocator);
				    	        testCase.addScreenShotCurrentStep(u, "Tienda: " + index);
				    	        u.unhighLight(tiendaLocator);
				    	    }
				    	    testCase.addTextEvidenceCurrentStep("Total de tiendas registradas en la evidencia: " + index);

				    	}


				    	/**
				   	  * Metodo para comprar el encabezado de los resumenes
				   	  * @author Yair Lizarraga
					  * @date Diciembre 2024
				      **/
				       public Boolean abrirYCompararEncabezadosResumen(){
					        String home = System.getProperty("user.home");
					        home = home + "\\Downloads";
					        File file = new File(getLastModified(home).getAbsolutePath());
					        String[] expectedHeaders = {"FA", "Nombre del Proveedor", "RegiÃ³n", "Plaza", "CR Tienda", "Nombre de Tienda",
									"Fecha", "Orden de Compra", "RemisiÃ³n", "Folio GOMA", "Fecha ADMVA", "Total General a la Venta", "Cifra del proveedor",
									"Movs. Valorizados", "Total Costo"};
					        try {
					            FileInputStream fis = new FileInputStream(file);
					            Workbook workbook = WorkbookFactory.create(fis);
					            Sheet sheet = workbook.getSheetAt(0);
					            Row headerRow = sheet.getRow(0);

					            boolean headersValid = true;
					            for(int i= 0; i <expectedHeaders.length; i++) {
					                String actualHeader = headerRow.getCell(i).getStringCellValue().trim();
					                testCase.addTextEvidenceCurrentStep("Los encabezados son correctos se esperaba en el excel: " + expectedHeaders[i]+" y se encontro: " +actualHeader+ "");
					                if(!expectedHeaders[i].equals(actualHeader)) {
					                    headersValid = false;
					                    System.out.println("Encabezado incorrecto en la columna " + ( i + 1) + " Se esperaba: " + expectedHeaders[i]+ " Pero se encontro " +actualHeader+ "");
					                }
					            }
					            if(headersValid) {
					                System.out.println("Los encabezados son correctos");
					                return true;

					            }else {
					                System.out.println("Se encontraron errores en los encabezados");
					            }

					            workbook.close();
					            fis.close();

					        }catch(IOException e) {
					            e.printStackTrace();
					        }
					        return false;
					    }

				   	/**
				  	  * Metodo para validar que los filtros de zona no se duplican
				  	  * @author Yair Lizarraga
					 * @date Diciembre 2024
				     **/
					public boolean validacionNoDuplicadosFiltroZona(){
						boolean result = false;
						List<WebElement> zonaElements = u.listElements(inputZona);
						Set<String> options = new HashSet<>();
						for (WebElement zonaOption : zonaElements){
							if(options.contains(zonaOption.getText())){
								result = false;
								return result;
							} else {
								options.add(zonaOption.getText());
								result = true;
							}
						}
						return result;
					}

					/**
					  * Metodo para validar PDF
					  * @author Yair Lizarraga
					 * @date Diciembre 2024
				   **/
					public boolean validacionListadoPdf() throws Exception{
						boolean result = false;
						String url = u.getDriver().getCurrentUrl();
						String get_Source = u.getDriver().getPageSource();
						System.out.println(get_Source.contains("Plaza"));
						return result;
					}

					/**
					  * Metodo para mostrar las opciones que tiene el filtro Plaza en la pantalla de Recepcion
	 				  * @author Jorge Hernandez
					 * @date Abril 2025
	                **/
					public boolean selectPlazaRecepcion() throws Exception{
						boolean result = false;
						if(u.isDisplayed(xmlSelectPlazaRecepcion)){
							u.highLight(xmlSelectPlazaRecepcion);
							u.click(xmlSelectPlazaRecepcion);
							testCase.addScreenShotCurrentStep("Select Plaza seleccionado");
							u.unhighLight(xmlSelectPlazaRecepcion);
							result = true;
						} else {
							result = false;
						}
						return result;
					}

					/**
					 * Metodo para seleccionar la opcion de el select Plaza en la pantalla de Recepcion al seleccionar opcion cierra el filtro
					 * @author Jorge Hernandez
					 * @param option -> se usa para seleccionar una opcion en especifico de la lista, inica en 1 hasta 4 cantidad de opciones que tenga el filtro
					 * @date Abril 2025
					 * */
					public boolean selectOpcionPlazaRecepcion(String option) throws Exception{
						boolean result = false;
						if(option.equals("1")){
							if(u.isDisplayed(xmlRecepcionPlazaOption1)){
								u.highLight(xmlRecepcionPlazaOption1);
								u.click(xmlRecepcionPlazaOption1);
								u.unhighLight(xmlRecepcionPlazaOption1);
								result = true;
								if(u.isDisplayed(xmlTituloFiltroBusquedaRapida)){ //Condicional para cerrar el la lista de opciones de el filtro (dar click fuera de la lista)
									u.click(xmlTituloFiltroBusquedaRapida);
								}
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
							}
						}
						if(option.equals("2")){
							if(u.isDisplayed(xmlRecepcionPlazaOption2)){
								u.highLight(xmlRecepcionPlazaOption2);
								u.click(xmlRecepcionPlazaOption2);
								u.unhighLight(xmlRecepcionPlazaOption2);
								result = true;
								if(u.isDisplayed(xmlTituloFiltroBusquedaRapida)){ //Condicional para cerrar el la lista de opciones de el filtro (dar click fuera de la lista)
									u.click(xmlTituloFiltroBusquedaRapida);
								}
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
							}
						}
						if(option.equals("3")){
							if(u.isDisplayed(xmlRecepcionPlazaOption3)){
								u.highLight(xmlRecepcionPlazaOption3);
								u.click(xmlRecepcionPlazaOption3);
								u.unhighLight(xmlRecepcionPlazaOption3);
								result = true;
								if(u.isDisplayed(xmlTituloFiltroBusquedaRapida)){ //Condicional para cerrar el la lista de opciones de el filtro (dar click fuera de la lista)
									u.click(xmlTituloFiltroBusquedaRapida);
								}
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
							}
						}
						if(option.equals("4")){
							if(u.isDisplayed(xmlRecepcionPlazaOption4)){
								u.highLight(xmlRecepcionPlazaOption4);
								u.click(xmlRecepcionPlazaOption4);
								u.unhighLight(xmlRecepcionPlazaOption4);
								result = true;
								if(u.isDisplayed(xmlTituloFiltroBusquedaRapida)){ //Condicional para cerrar el la lista de opciones de el filtro (dar click fuera de la lista)
									u.click(xmlTituloFiltroBusquedaRapida);
								}
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
							}
						}
						return result;
					}

					/**
					 * Metodo para validar visibilidad o usar la funcion Limpiar Seleccion de el filtro Plaza en la pantalla de Recepcion/Comprobantes Compra
					 * @author Jorge Hernandez
					 * @param option -> se usa para seleccionar si se quiere validar la visibilidad o la funcionalidad de el metodo
					 * @date Abril 2025
					 * */
					public boolean limpiarSeleccionPlaza(String option){
						boolean result = false;
						if(u.isDisplayed(xmlLimpiarSeleccionPlaza)){
							if(option.equals("visibilidad")){
								u.highLight(xmlLimpiarSeleccionPlaza);
								testCase.addScreenShotCurrentStep("Limpiar Seleccion es visible en filtro Plaza");
								result = true;
							}
							if(option.equals("funcionalidad")){
								u.highLight(xmlLimpiarSeleccionPlaza);
								u.click(xmlLimpiarSeleccionPlaza);
									testCase.addScreenShotCurrentStep("Se limpio la seleccion de el filtro");
								if(u.getText(xmlSelectPlazaRecepcion).toString().trim().equals("Plaza")){
									result = true;
								}
							}
						}
						return result;
					}

					/**
					 * Metodo para validar visibilidad o usar la funcion Limpiar Seleccion de el filtro Plaza en la pantalla de Comprobantes Compra
					 * @author Jorge Hernandez
					 * @param option -> se usa para seleccionar si se quiere validar la visibilidad o la funcionalidad de el metodo
					 * @date Abril 2025
					 * */
					public boolean limpiarSeleccionPlazaDirectos(String option){
						boolean result = false;
						if(u.isDisplayed(xmlLimpiarSeleccionPlazaDirectos)){
							if(option.equals("visibilidad")){
								u.highLight(xmlLimpiarSeleccionPlazaDirectos);
								testCase.addScreenShotCurrentStep("Limpiar Seleccion es visible en filtro Plaza");
								result = true;
							}
							if(option.equals("funcionalidad")){
								u.highLight(xmlLimpiarSeleccionPlazaDirectos);
								u.click(xmlLimpiarSeleccionPlazaDirectos);
								testCase.addScreenShotCurrentStep("Se limpio la seleccion de el filtro");
								if(u.getText(xmlSelectPlazaRecepcion).toString().trim().equals("Plaza")){
									result = true;
								}
							}
						}
						return result;
					}

					/**
					 * Metodo para seleccionar el filtro Tienda y mostrar las opciones en la pantalla de Recepcion
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 **/
					public boolean selectTiendaRecepcion(){
						boolean result = false;
						if(u.isDisplayed(xmlSelectTiendaRecepcion)){
							u.highLight(xmlSelectTiendaRecepcion);
							u.click(xmlSelectTiendaRecepcion);
							testCase.addScreenShotCurrentStep("Select Tienda seleccionado");
							u.unhighLight(xmlSelectTiendaRecepcion);
							result = true;
						} else {
							result = false;
						}
						return result;
					}

						/** Metodo para dar clic en el texto Busqueda Historica en la pantalla de Recepcion, se usa como metodo para cerrar los filtros
						 * @author Jorge Hernandez
						 * @date Abril 2025
						 */
						public boolean tituloBusquedaHistorica(){
						boolean result = false;
						if(u.isDisplayed(xmlTituloFiltroBusquedaRapida)){ //Condicional para cerrar el la lista de opciones de el filtro (dar click fuera de la lista)
							u.click(xmlTituloFiltroBusquedaRapida);
							result = true;
						}
						return result;
					}
						
						public boolean tituloBusquedaComprobante(){
							boolean result = false;
							if(u.isDisplayed(xmlTituloFiltrosBusquedaComprobantes)){ //Condicional para cerrar el la lista de opciones de el filtro (dar click fuera de la lista)
								u.click(xmlTituloFiltrosBusquedaComprobantes);
								result = true;
							}
							return result;
						}

					/**
					 * Metodo para seleccionar la opcion de el select Tienda en la pantalla de Recepcion al seleccionar opcion cierra el filtro
					 * @author Jorge Hernandez
					 * @Param option -> se usa para seleccionar una opcion en especifico de la lista, inica en 1 hasta 4 cantidad de opciones que tenga el filtro
					 * @date Abril 2025
					 * */
					public boolean selectOpcionTiendaRecepcion(String option) throws Exception{
						boolean result = false;
						if(option.equals("1")){
							if(u.isDisplayed(xmlRecepcionTiendaOption1)){
								u.highLight(xmlRecepcionTiendaOption1);
								u.click(xmlRecepcionTiendaOption1);
								u.unhighLight(xmlRecepcionTiendaOption1);
								result = true;
								if(u.isDisplayed(xmlTituloFiltroBusquedaRapida)){ //Condicional para cerrar el la lista de opciones de el filtro (dar click fuera de la lista)
									u.click(xmlTituloFiltroBusquedaRapida);
								}
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
							}
						}
						if(option.equals("2")){
							if(u.isDisplayed(xmlRecepcionTiendaOption2)){
								u.highLight(xmlRecepcionTiendaOption2);
								u.click(xmlRecepcionTiendaOption2);
								u.unhighLight(xmlRecepcionTiendaOption2);
								result = true;
								if(u.isDisplayed(xmlTituloFiltroBusquedaRapida)){ //Condicional para cerrar el la lista de opciones de el filtro (dar click fuera de la lista)
									u.click(xmlTituloFiltroBusquedaRapida);
								}
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
							}
						}
						if(option.equals("3")){
							if(u.isDisplayed(xmlRecepcionTiendaOption3)){
								u.highLight(xmlRecepcionTiendaOption3);
								u.click(xmlRecepcionTiendaOption3);
								u.unhighLight(xmlRecepcionTiendaOption3);
								result = true;
								if(u.isDisplayed(xmlTituloFiltroBusquedaRapida)){ //Condicional para cerrar el la lista de opciones de el filtro (dar click fuera de la lista)
									u.click(xmlTituloFiltroBusquedaRapida);
								}
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
							}
						}
						if(option.equals("4")){
							if(u.isDisplayed(xmlRecepcionTiendaOption4)){
								u.highLight(xmlRecepcionTiendaOption4);
								u.click(xmlRecepcionTiendaOption4);
								u.unhighLight(xmlRecepcionTiendaOption4);
								result = true;
								if(u.isDisplayed(xmlTituloFiltroBusquedaRapida)){ //Condicional para cerrar el la lista de opciones de el filtro (dar click fuera de la lista)
									u.click(xmlTituloFiltroBusquedaRapida);
								}
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
							}
						}
						return result;
					}

					/** Metodo para seleccionar opcion de el filtro tienda
					 * @author Jorge Hernandez
					 * @param option -> selecciona una opcion de el filtro entre 1 y 4
					 * @date Abril 2025
					 * */
					public boolean seleccionOpcionFiltroTienda(String option){
						boolean result = false;
						if(option.equals("1")){
							if(u.isDisplayed(xmlCRTienda)){
								u.highLight(xmlCRTienda);
								u.mouseOverAndClick(xmlCRTienda);
								u.unhighLight(xmlCRTienda);
								u.highLight(xmlCRTiendaOp1);
								u.click(xmlCRTiendaOp1);
								u.mouseOverAndClick(xmlCRTienda);
								testCase.addScreenShotCurrentStep(u, "Seleccion de tienda");
								result = true;
							}
						}
						if(option.equals("2")){
							if(u.isDisplayed(xmlCRTienda)){
								u.highLight(xmlCRTienda);
								u.mouseOverAndClick(xmlCRTienda);
								u.unhighLight(xmlCRTienda);
								u.highLight(xmlCRTiendaOp2);
								u.click(xmlCRTiendaOp1);
								u.mouseOverAndClick(xmlCRTienda);
								testCase.addScreenShotCurrentStep(u, "Seleccion de tienda");
								result = true;
							}
						}
						if(option.equals("3")){
							if(u.isDisplayed(xmlCRTienda)){
								u.highLight(xmlCRTienda);
								u.mouseOverAndClick(xmlCRTienda);
								u.unhighLight(xmlCRTienda);
								u.highLight(xmlCRTiendaOp3);
								u.click(xmlCRTiendaOp1);
								u.mouseOverAndClick(xmlCRTienda);
								testCase.addScreenShotCurrentStep(u, "Seleccion de tienda");
								result = true;
							}
						}
						if(option.equals("4")){
							if(u.isDisplayed(xmlCRTienda)){
								u.highLight(xmlCRTienda);
								u.mouseOverAndClick(xmlCRTienda);
								u.unhighLight(xmlCRTienda);
								u.highLight(xmlCRTiendaOp4);
								u.click(xmlCRTiendaOp1);
								u.mouseOverAndClick(xmlCRTienda);
								testCase.addScreenShotCurrentStep(u, "Seleccion de tienda");
								result = true;
							}
						}
						return result;
					}

					/**
					 * Metodo para seleccionar la opcion de el select Zona en la pantalla de Recepcion no cierra el filtro
					 * @author Jorge Hernandez
					 * @param option -> se usa para seleccionar una opcion en especifico de la lista, inica en 1 hasta 4 cantidad de opciones que tenga el filtro
					 * @date Abril 2025
					 */
					public boolean seleccionOpcionZona(String option) throws Exception{
						boolean result = false;
						if(option.equals("1")){
							if(u.isDisplayed(xmlZonaOp1)){
								u.highLight(xmlZonaOp1);
								u.click(xmlZonaOp1);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.unhighLight(xmlZonaOp1);
								result = true;
							}
						}
						if(option.equals("2")){
							if(u.isDisplayed(xmlZonaOp2)){
								u.highLight(xmlZonaOp2);
								u.click(xmlZonaOp2);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.unhighLight(xmlZonaOp2);
								result = true;
							}
						}
						if(option.equals("3")){
							if(u.isDisplayed(xmlZonaOp3)){
								u.highLight(xmlZonaOp3);
								u.click(xmlZonaOp3);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.unhighLight(xmlZonaOp3);
								result = true;
							}
						}
						if(option.equals("4")){
							if(u.isDisplayed(xmlZonaOp4)){
								u.highLight(xmlZonaOp4);
								u.click(xmlZonaOp4);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.unhighLight(xmlZonaOp4);
								result = true;
							}
						}
						return result;
					}

					/**
					 * Metodo para validar visibilidad o usar la funcion Limpiar Seleccion del filtro Tienda en la pantalla de Comprobantes de Compra
					 * @author Jorge Hernandez
					 * @Param option -> se usa para seleccionar si se quiere validar la visibilidad o la funcionalidad de el metodo
					 * @date Abril 2025
					 */
					public boolean limpiarSeleccionTienda(String option){
						boolean result = false;
						if(u.isDisplayed(xmlLimpiarSeleccionTienda)){
							if(option.equals("visibilidad")){
								u.highLight(xmlLimpiarSeleccionTienda);
								testCase.addScreenShotCurrentStep("Limpiar Seleccion es visible en filtro Tienda");
								result = true;
							}
							if(option.equals("funcionalidad")){
								u.highLight(xmlLimpiarSeleccionTienda);
								u.click(xmlLimpiarSeleccionTienda);
								u.unhighLight(xmlLimpiarSeleccionTienda);
								if(u.getText(xmlSelectTiendaRecepcion).equals("Tienda")){
									testCase.addScreenShotCurrentStep("Se limpio la seleccion de el filtro");
									result = true;
								}
							}
						}
						return result;
					}


					/**
					 * Metodo para validar visibilidad o usar la funcion Limpiar Seleccion del filtro Zona en la pantalla de Comprobantes de Compra
					 * @author Jorge Hernandez
					 * @Param option -> se usa para seleccionar si se quiere validar la visibilidad o la funcionalidad de el metodo
					 * @date Abril 2025
					 */
					public boolean limpiarSeleccionZona(String option){
						boolean result = false;
						if(u.isDisplayed(xmlLimpiarSeleccionZona)){
							if(option.equals("visibilidad")){
								u.highLight(xmlLimpiarSeleccionZona);
								testCase.addTextEvidenceCurrentStep("Limpiar Seleccion es visible en filtro Zona");
								result = true;
							}
							if(option.equals("funcionalidad")){
								u.highLight(xmlLimpiarSeleccionZona);
								u.click(xmlLimpiarSeleccionZona);
								testCase.addScreenShotCurrentStep("Se limpio la seleccion de el filtro");
								result = true;
							}
						}
						return result;
					}

					/**
					 * Metodo para validar visibilidad o usar la funcion Limpiar Seleccion del filtro Zona en la pantalla de Comprobantes de Compra
					 * @author Jorge Hernandez
					 * @Param option -> se usa para seleccionar si se quiere validar la visibilidad o la funcionalidad de el metodo
					 * @date Abril 2025
					 */
					public boolean limpiarSeleccionZonaOp(String option){
						boolean result = false;
						if(u.isDisplayed(xmlZona)){
							u.click(xmlZona);
							if(u.isDisplayed(xmlOpcionesZona)){
								if(option.equals("1")){
									u.highLight(xmlZonaOp1);
									u.click(xmlZonaOp1);
									testCase.addScreenShotCurrentStep("Opcion 1 deseleccionada");
									u.unhighLight(xmlZonaOp1);
									result = true;
								}
								if(option.equals("2")){
									u.highLight(xmlZonaOp2);
									u.click(xmlZonaOp2);
									testCase.addScreenShotCurrentStep("Opcion 2 deseleccionada");
									u.unhighLight(xmlZonaOp2);
									result = true;
								}
								if(option.equals("3")){
									u.highLight(xmlZonaOp3);
									u.click(xmlZonaOp3);
									testCase.addScreenShotCurrentStep("Opcion 3 deseleccionada");
									u.unhighLight(xmlZonaOp3);
									result = true;
								}
								if(option.equals("4")){
									u.highLight(xmlZonaOp4);
									u.click(xmlZonaOp4);
									testCase.addScreenShotCurrentStep("Opcion 4 deseleccionada");
									u.unhighLight(xmlZonaOp4);
									result = true;
								}
							}
						}
						return result;
					}

					/**
					 * Metodo para validar visibilidad o usar la funcion Limpiar Seleccion del filtro Proveedor en la pantalla de Comprobantes de Compra
					 * @author Jorge Hernandez
					 * @Param option -> se usa para seleccionar si se quiere validar la visibilidad o la funcionalidad de el metodo
					 * @date Abril 2025
					 */
					public boolean limpiarSeleccionProveedor(String option){
						boolean result = false;
						if(u.isDisplayed(xmlLimpiarSeleccionProveedor)){
							if(option.equals("visibilidad")){
								u.highLight(xmlLimpiarSeleccionProveedor);
								testCase.addTextEvidenceCurrentStep("Limpiar Seleccion es visible en filtro Proveedor");
								result = true;
							}
							if(option.equals("funcionalidad")){
								u.highLight(xmlLimpiarSeleccionProveedor);
								u.click(xmlLimpiarSeleccionProveedor);
								testCase.addScreenShotCurrentStep("Se limpio la seleccion de el filtro");
								result = true;
							}
						}
						return result;
					}

					/**
					 * Metodo para validar visibilidad o usar la funcion Limpiar Seleccion del filtro FA en la pantalla de Comprobantes de Compra
					 * @author Jorge Hernandez
					 * @Param option -> se usa para seleccionar si se quiere validar la visibilidad o la funcionalidad de el metodo
					 * @date Abril 2025
					 */
					public boolean limpiarSeleccionFA(String option){
						boolean result = false;
						if(u.isDisplayed(xmlLimpiarSeleccionFA)){
							if(option.equals("visibilidad")){
								u.highLight(xmlLimpiarSeleccionFA);
								testCase.addTextEvidenceCurrentStep("Limpiar Seleccion es visible en filtro FA");
								result = true;
							}
							if(option.equals("funcionalidad")){
								u.highLight(xmlLimpiarSeleccionFA);
								u.click(xmlLimpiarSeleccionFA);
								testCase.addScreenShotCurrentStep("Se limpio la seleccion de el filtro");
								result = true;
							}
						}
						return result;
					}

					/**
					 * Metodo para validar visibilidad o usar la funcion Limpiar Seleccion del filtro Region en la pantalla de Comprobantes de Compra
					 * @author Jorge Hernandez
					 * @Param option -> se usa para seleccionar si se quiere validar la visibilidad o la funcionalidad de el metodo
					 * @date Abril 2025
					 */
						public boolean limpiarSeleccionRegion(String option){
						boolean result = false;
						if(u.isDisplayed(xmlLimpiarSeleccionRegion)){
							if(option.equals("visibilidad")){
								u.highLight(xmlLimpiarSeleccionRegion);
								testCase.addScreenShotCurrentStep("Limpiar Seleccion es visible en filtro Region");
								u.unhighLight(xmlLimpiarSeleccionRegion);
								result = true;
							}
							if(option.equals("funcionalidad")){
								u.highLight(xmlLimpiarSeleccionRegion);
								u.click(xmlLimpiarSeleccionRegion);
								u.unhighLight(xmlLimpiarSeleccionRegion);
								if(u.getText(xmlRegion).startsWith("Regi")){
									testCase.addScreenShotCurrentStep("Se limpio la seleccion de el filtro");
									result = true;
								}
							}
						}
						return result;
					}

					/**
					 * Metodo para validar seleccion y mostrado de opciones de filtro FA en la pantalla de Comprobantes de Compra
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 **/
					public boolean seleccionFiltroFA(){
						boolean result = false;
						if(u.isDisplayed(xmlFuenteAbasto)){
							u.highLight(xmlFuenteAbasto);
							u.click(xmlFuenteAbasto);
							testCase.addScreenShotCurrentStep("Se muestran las opciones de el filtro");
							u.unhighLight(xmlFuenteAbasto);
							result = true;
						}
						return result;
					}

					/**
					 * Metodo para validar seleccion y mostrado de opciones de filtro Zona en la pantalla de Comprobantes de Compra
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 **/
					public boolean seleccionFiltroZona(){
						boolean result = false;
						if(u.isDisplayed(xmlZona)){
							u.highLight(xmlZona);
							u.click(xmlZona);
							testCase.addScreenShotCurrentStep("Se muestran las opciones de el filtro");
							u.unhighLight(xmlZona);
							result = true;
						}
						return result;
					}

					/**
					 * Metodo para validar seleccion y mostrado de opciones de filtro Region en la pantalla de Comprobantes de Compra
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 **/
					public boolean seleccionFiltroRegion(){
						boolean result = false;
						if(u.isDisplayed(xmlRegion)){
							u.highLight(xmlRegion);
							u.click(xmlRegion);
							testCase.addScreenShotCurrentStep("Se muestran las opciones de el filtro");
							u.unhighLight(xmlRegion);
							result = true;
						}
						return result;
					}

					/**
					 * Metodo para validar seleccion y mostrado de opciones de filtro Plaza en la pantalla de Comprobantes de Compra
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 **/
					public boolean seleccionFiltroPlaza(){
						boolean result = false;
						if(u.isDisplayed(xmlCRPLazaDirectos)){
							u.highLight(xmlCRPLazaDirectos);
							u.click(xmlCRPLazaDirectos);
							testCase.addScreenShotCurrentStep("Se muestran las opciones de el filtro");
							u.unhighLight(xmlCRPLazaDirectos);
							result = true;
						}
						return result;
					}

					/**
					 * Metodo para validar seleccion y mostrado de opciones de filtro Plaza en la pantalla de Comprobantes de Compra
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 **/
					public boolean seleccionFiltroTienda(){
						boolean result = false;
						if(u.isDisplayed(xmlCRTienda)){
							u.highLight(xmlCRTienda);
							u.click(xmlCRTienda);
							testCase.addScreenShotCurrentStep("Se muestran las opciones de el filtro");
							u.unhighLight(xmlCRTienda);
							result = true;
						}
						return result;
					}

					/**
					 * Metodo para seleccionar la opcion de el select FA en la pantalla de Recepcion
					 * @author Jorge Hernandez
					 * @param option -> se usa para seleccionar una opcion en especifico de la lista, inica en 1 hasta x cantidad de opciones que tenga el filtro
					 * @date Abril 2025
					 */
					public boolean seleccionOpcionFA(String option) throws Exception{
						boolean result = false;
						if(option.equals("1")){
							if(u.isDisplayed(xmlFuenteAbastoOp1)){
								u.highLight(xmlFuenteAbastoOp1);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.click(xmlFuenteAbastoOp1);
								u.unhighLight(xmlFuenteAbastoOp1);
								result = true;
							}
						}
						if(option.equals("2")){
							if(u.isDisplayed(xmlFuenteAbastoOp2)){
								u.highLight(xmlFuenteAbastoOp2);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.click(xmlFuenteAbastoOp2);
								u.unhighLight(xmlFuenteAbastoOp2);
								result = true;
							}
						}
						if(option.equals("3")){
							if(u.isDisplayed(xmlFuenteAbastoOp3)){
								u.highLight(xmlFuenteAbastoOp3);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.click(xmlFuenteAbastoOp3);
								u.unhighLight(xmlFuenteAbastoOp3);
								result = true;
							}
						}
						if(option.equals("4")){
							if(u.isDisplayed(xmlFuenteAbastoOp4)){
								u.highLight(xmlFuenteAbastoOp4);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.click(xmlFuenteAbastoOp4);
								u.unhighLight(xmlFuenteAbastoOp4);
								result = true;
							}
						}
						return result;
					}

					/**
					 * Metodo para seleccionar la opcion de el select Zona en la pantalla de Recepcion no cierra el filtro
					 * @author Jorge Hernandez
					 * @param option -> se usa para seleccionar una opcion en especifico de la lista, inica en 1 hasta x cantidad de opciones que tenga el filtro
					 * @date Abril 2025
					 */
					public boolean seleccionOpcionRegion(String option) throws Exception{
						boolean result = false;
						if(option.equals("1")){
							if(u.isDisplayed(xmlregionOp1)){
								u.highLight(xmlregionOp1);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.click(xmlregionOp1);
								u.unhighLight(xmlregionOp1);
								result = true;
							}
						}
						if(option.equals("2")){
							if(u.isDisplayed(xmlregionOp2)){
								u.highLight(xmlregionOp2);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.click(xmlregionOp2);
								u.unhighLight(xmlregionOp2);
								result = true;
							}
						}
						if(option.equals("3")){
							if(u.isDisplayed(xmlregionOp3)){
								u.highLight(xmlregionOp3);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.click(xmlregionOp3);
								u.unhighLight(xmlregionOp3);
								result = true;
							}
						}
						if(option.equals("4")){
							if(u.isDisplayed(xmlregionOp4)){
								u.highLight(xmlregionOp4);
								testCase.addScreenShotCurrentStep("Opcion seleccionada");
								u.click(xmlregionOp4);
								u.unhighLight(xmlregionOp4);
								result = true;
							}
						}
						return result;
					}

					/** Metodo para seleccionar el filtro Rango de fechas
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 **/
					public boolean seleccionFiltroRangoFechas(){
						boolean result = false;
						if(u.isDisplayed(xmlRangoFechas)) {
							u.highLight(xmlRangoFechas);
							u.mouseOverAndClick(xmlRangoFechas);
							testCase.addScreenShotCurrentStep("Rango fechas seleccionado");
							u.unhighLight(xmlRangoFechas);
							result = true;
						}
							return result;
					}

					/** Metodo para seleccionar la fecha nical de el filtro rango de fechas
					 * @param meses -> Meses a retroceder
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 **/
					public boolean seleccionFechaInicialRangoFechas(int meses){
						boolean result = false;
						if(u.isDisplayed(xmlBotonFlechaIzquierda)){
							u.highLight(xmlBotonFlechaIzquierda);
							for (int i = 0; i < meses; i++) { // valor de i para cuantos meses atras seleccionar fecha
								u.mouseOverAndClick(xmlBotonFlechaIzquierda);
							}
							u.unhighLight(xmlBotonFlechaIzquierda);
							u.highLight(xmlPrimerDiaHabilidato);
							u.mouseOver(xmlPrimerDiaHabilidato);
							u.click(xmlPrimerDiaHabilidato);
							testCase.addScreenShotCurrentStep("Primera fecha seleccionada");
							u.unhighLight(xmlPrimerDiaHabilidato);
							result = true;
						}
						return result;
					}

					/** Metodo para seleccionar fecha final de el filtro rango de fechas
					 * @param meses -> meses a avanzar
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 **/
					public boolean seleccionFechaFinalRangoFechas(int meses){
						boolean result = false;
						if(u.isDisplayed(xmlBotonFlechaDerecha)){
							u.highLight(xmlBotonFlechaDerecha);
							for (int i = 0; i < meses; i++) { // valor de i para cuantos meses atras seleccionar fecha
								u.mouseOverAndClick(xmlBotonFlechaDerecha);
							}
							u.unhighLight(xmlBotonFlechaDerecha);
							if(u.isDisplayed(xmlUltimoDiaHabilitado)){
								u.highLight(xmlUltimoDiaHabilitado);
								u.mouseOver(xmlUltimoDiaHabilitado);
								u.unhighLight(xmlUltimoDiaHabilitado);
								u.click(xmlUltimoDiaHabilitado);
								testCase.addScreenShotCurrentStep("Segunda fecha seleccionada");
								result = true;
							}
						}

						return result;
					}

					/** Metodo para validar el formato de fechas
					 * @param formato -> formato de fecha a comparar
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 **/
					public boolean validacionFormatoFechas(String formato){

						boolean result = false;
						try {
					        // Paso 1: Crear el formateador de fecha
					        System.out.println("Creando el formateador de fecha con el formato: " + formato);
					        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);

					        // Paso 2: Obtener el texto del campo
					        System.out.println("Obteiendo el texto del campo...");
					        WebElement input = u.getDriver().findElement(By.xpath("//div[@class='mat-date-range-input-wrapper']//input"));
					        String valorInput = input.getAttribute("value");
					        System.out.println("Valor del input:" + valorInput);
					        System.out.println("Texto obtenido:" + valorInput);

					        // Paso 3: Intentar parsear la fecha
					        System.out.println("Parseando la fecha...");
					        LocalDate date;
					        try {
					            // Intentar con el formato inicial proporcionado
					            date = LocalDate.parse(valorInput, formatter);
					            testCase.addScreenShotCurrentStep("Fecha parseada correctamente");
					        } catch (Exception e) {
					            System.out.println("Error con el formato inicial, intentando con otro formato...");
					            // Intentar con un formato alternativo
					            date = LocalDate.parse(valorInput, DateTimeFormatter.ofPattern("d/M/yyyy"));
					            testCase.addScreenShotCurrentStep("Fecha parseada correctamente");
					        }
					        System.out.println("Fecha parseada correctamente: " + date);

					        // Si todo sale bien
					        result = true;
					    } catch (Exception e) {
					        // Capturar el error y mostrarlo
					        System.out.println("OcurriÃ³ un error: " + e.getMessage());
					        e.printStackTrace();
					        result = false;
					    }
//						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
//						u.hardWait(5);
//						try{
//							String fecha1 = u.getText(xmlFechaInicialRangoFecha);
//                            if(!u.getText(xmlFechaInicialRangoFecha).equals("Fecha Inicio")){
//                                System.out.println(fecha1);
//                                LocalDate date = LocalDate.parse(fecha1, formatter);
//                                System.out.println(date);
//                                result = true;
//                            }
//
//						} catch (DateTimeParseException e) {
//							 result = false;
//						}

					    return result;
					}

					/** Metodo para seleccionar el filtro Ruta de Busqueda historica Recepcion
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean seleccionRutaBusquedaHistorica(){
						boolean result = false;
						if(u.isDisplayed(xmlSelectorRuta)){
							u.highLight(xmlSelectorRuta);
							u.click(xmlSelectorRuta);
							testCase.addScreenShotCurrentStep("Se muestran las opciones de el selector");
							u.unhighLight(xmlSelectorRuta);
							result = true;
						}
						return result;
					}

					/** Metodo para realizar una consulta con ruta dinamica
					 *@author Jorge Hernandez
					 * @param ruta -> variable con el valor de ruta con la cual se realiza la consulta
					 * @date Abril 2025
					 */
					public boolean filtradoRutaDinamica(String ruta){
						boolean result = false;
						if(u.isDisplayed(xmlSelectorRuta)){
							u.highLight(xmlSelectorRuta);
							u.click(xmlSelectorRuta);
							u.unhighLight(xmlSelectorRuta);

							if (u.isDisplayed(xmlOpcionDinamica)){
								u.highLight(xmlOpcionDinamica);
								u.click(xmlOpcionDinamica);
								u.unhighLight(xmlOpcionDinamica);

								testCase.addScreenShotCurrentStep("Opcion Dinamica seleccionada");
							}
						}

						if(u.isDisplayed(xmlInputRutaCedis)){
							u.highLight(xmlInputRutaCedis);
							u.click(xmlInputRutaCedis);
							u.sendKeys(xmlInputRutaCedis, ruta);
							u.unhighLight(xmlInputRutaCedis);
						}

						seleccionRangoFechas(3);

						consultarCedisBusquedaHistorica();

						if(u.isDisplayed(xmlMessageNotResultRecepcion)){
							u.highLight(xmlMessageNotResultRecepcion);
							testCase.addScreenShotCurrentStep("La busqueda muestra que no despliega FUE v1");
							result = true;
						}
						return result;
					}

					/** Metodo para realizar una consulta con ruta dinamica
					 *@author Jorge Hernandez
					 * @param ruta -> variable con el valor de ruta con la cual se realiza la consulta
					 * @date Abril 2025
					 */
					public boolean filtradoRutaPMOV2(String ruta){
						boolean result = false;
						if(u.isDisplayed(xmlSelectorRuta)){
							u.highLight(xmlSelectorRuta);
							u.click(xmlSelectorRuta);
							u.unhighLight(xmlSelectorRuta);

							if (u.isDisplayed(xmlOpcionPMO)){
								u.highLight(xmlOpcionPMO);
								u.click(xmlOpcionPMO);
								u.unhighLight(xmlOpcionPMO);

								testCase.addScreenShotCurrentStep("Opcion Dinamica seleccionada");
							}
						}

						if(u.isDisplayed(xmlInputRutaCedis)){
							u.highLight(xmlInputRutaCedis);
							u.click(xmlInputRutaCedis);
							u.sendKeys(xmlInputRutaCedis, ruta);
							u.unhighLight(xmlInputRutaCedis);
						}

						seleccionRangoFechas(8);

						consultarCedisBusquedaHistorica();

						if(u.isDisplayed(xmlResultadoBusquedasCedis)){
							u.scrollToElement(xmlResultadoBusquedasCedis);
							if(!u.getText(xmlResultadoBusquedasCedis).equals(" 0 of 0 ")){
								testCase.addScreenShotCurrentStep("La busqueda muestra FUE v2");
								result = true;
							}
						}

						return result;
					}

					/** Metodo para validar que sea visible la nota de filtro historico
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarNotaFiltroHistorico(){
						boolean result = false;
						if(u.isDisplayed(xmlNotaFiltroRutaRecepcion)){
							u.highLight(xmlNotaFiltroRutaRecepcion);
							testCase.addScreenShotCurrentStep("Se muestra la nota de el filtro Ruta");
							result = true;
						}
						return result;
					}

					/** Metodo par validar que sea visible la opcion Dinamica en el Selector Ruta
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarOpcionDinamica(){
						boolean result = false;
						if(u.isDisplayed(xmlSelectorRuta)){
							u.highLight(xmlSelectorRuta);
							u.click(xmlSelectorRuta);
							u.unhighLight(xmlSelectorRuta);

							if(u.isDisplayed(xmlOpcionDinamica)){
								u.highLight(xmlOpcionDinamica);
								testCase.addScreenShotCurrentStep("Se encontro la opcion Dinamica en el filtro");
								u.unhighLight(xmlOpcionDinamica);
								result = true;
							}
						}
						return result;
					}

					/** Metodo para seleccionar la opcion Dinamica en el filtro Ruta
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean seleccionOpcionDinamica(){
						boolean result = false;
						if(u.isDisplayed(xmlOpcionDinamica)){
							u.highLight(xmlOpcionDinamica);
							u.click(xmlOpcionDinamica);
							testCase.addScreenShotCurrentStep("Se muestra la opcion seleccionada en el selector");
							result = true;
						}
						return result;
					}

					/**Metodo para seleccionar la opcion PMO en el filtro Ruta
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarOpcionPMO(){
						boolean result = false;
						if(u.isDisplayed(xmlSelectorRuta)){
							u.highLight(xmlSelectorRuta);
							u.click(xmlSelectorRuta);
							u.unhighLight(xmlSelectorRuta);

							if(u.isDisplayed(xmlOpcionPMO)){
								u.highLight(xmlOpcionPMO);
								testCase.addScreenShotCurrentStep("Se encontro la opcion PMO en el filtro");
								u.unhighLight(xmlOpcionPMO);
								result = true;
							}
						}
						return result;
					}

					/** Metodo para validar que filtro plaza y tienda esten desactivado
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarPlazaTiendaDesactivados(){
						boolean result = false;
						if(u.isDisplayed(xmlFiltroPlazaRecepcionDeshabilitado) && u.isDisplayed(xmlFiltroTiendaRecepcionDeshabilitado)){
							u.highLight(xmlFiltroPlazaRecepcionDeshabilitado);
							u.highLight(xmlFiltroTiendaRecepcionDeshabilitado);
							testCase.addScreenShotCurrentStep("Los filtros Plaza y Tienda estan deshabilitados");
							u.unhighLight(xmlFiltroPlazaRecepcionDeshabilitado);
							u.unhighLight(xmlFiltroTiendaRecepcionDeshabilitado);
							result = true;
						}
						return result;
					}

					/** Metodo para seleccionar el filtro plaza de busqueda historica
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean seleccionarPlazaBusquedaHistorica(){
						boolean result = false;
						if(u.isDisplayed(xmlSelectPlazaRecepcion)){
							u.highLight(xmlSelectPlazaRecepcion);
							u.click(xmlSelectPlazaRecepcion);
							testCase.addScreenShotCurrentStep("Se muestran las opciones de el selector");
							u.unhighLight(xmlSelectPlazaRecepcion);
							result = true;
						}
						return result;
					}

					/** Metodo para validar que el selector Ruta este desactivado
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarRutaDesactivado(){
						boolean result = false;
						if(u.isDisplayed(xmlSelectorRutaDeshabilitado)){
							u.highLight(xmlSelectorRutaDeshabilitado);
							testCase.addScreenShotCurrentStep("Se muestra el selector Ruta deshabilitado");
							u.unhighLight(xmlSelectorRutaDeshabilitado);
							result = true;
						}
						return result;
					}

					/** Metodo para validar el nuevo mensaje en el filtro busqueda rapida
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarCambioMensajeFiltroBusquedaRapida(){
						boolean result = false;
						if(u.isDisplayed(xmlMensajeFiltroBusquedaRapida)){
							u.highLight(xmlMensajeFiltroBusquedaRapida);
							testCase.addScreenShotCurrentStep("Se muestra el mensaje en el Filtro Busqueda Rapida");
							u.unhighLight(xmlMensajeFiltroBusquedaRapida);
							result = true;
						}
						return result;
					}

					/** Metodo para dar clic en el boton Liquidacion de Ruta
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					 public boolean clickBotonLiquidacionRuta(){
						boolean result = false;
						if(u.isDisplayed(xmlBotonLliquidacionRuta)){
							u.highLight(xmlBotonLliquidacionRuta);
							u.click(xmlBotonLliquidacionRuta);
							u.waitForLoadPage();
							testCase.addScreenShotCurrentStep("Se redirecciono a la pagina Liquidacion de Ruta");
							result = true;
						}
						return result;
					}

					/** Metodo para validar el menu superior en pantalla Liquidacion de Ruta
					 * @author Jorge Hernandez
					 * @date Abril 2025
					*/
					public boolean validarMenuSuperiorLiquidacionRuta(){
						boolean result = false;
						if(u.isDisplayed(xmlmenuSuperior)){
							u.highLight(xmlmenuSuperior);
							u.click(xmlmenuSuperior);
							testCase.addScreenShotCurrentStep("Se muesra el menu superior en la pantalla");
							u.unhighLight(xmlmenuSuperior);
							result = true;
						}
						return result;
					}

					/** Metodo para valdiar el titulo de la pantalla de Liquidacion de Ruta
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarTituloPantallaLiquidacionRuta(){
						boolean result = false;
						if(u.isDisplayed(xmlTituloPagLiquidacionRuta)){
							u.highLight(xmlTituloPagLiquidacionRuta);
							testCase.addScreenShotCurrentStep("Se visualiza el Titulo de la pantalla");
							u.unhighLight(xmlTituloPagLiquidacionRuta);
							result = true;
						}
						return result;
					}

					/** Metodo para validar la visibilidad del Boton Recepcion en pantalla Liquidacion de Ruta
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarVisibilidadBotonRecepcion(){
						boolean result = true;
						if(u.isDisplayed(xmlBotonRecepcionLiquidacionRuta)){
							u.highLight(xmlBotonRecepcionLiquidacionRuta);
							testCase.addScreenShotCurrentStep("Se muestra el boton de Recepcion en pantalla Liquidacion de Ruta");
						}
						return result;
					}

					/** Metodo para realizar una consulta con una Ruta en Liquidacion de Ruta
					 * @author Jorge Hernandez
					 * @param ruta -> valor tomado desde el DataProvider
					 * @date Abril 2025
					 */
					public boolean consultaLiquidacionRuta(String ruta){
						boolean result = false;
						//Filtro Ruta
						if(u.isDisplayed(xmlInputRutaLiquidacion)){
							u.highLight(xmlInputRutaLiquidacion);
							u.unhighLight(xmlInputRutaLiquidacion);
							u.click(xmlInputRutaLiquidacion);
							u.sendKeys(xmlInputRutaLiquidacion2, ruta);
							result = true;
						}

						//Filtro Fecha inicial
						if(u.isDisplayed(xmlFechaInicialLiquidacion)){
							u.highLight(xmlFechaInicialLiquidacion);
							u.click(xmlFechaInicialLiquidacion);
							u.unhighLight(xmlFechaInicialLiquidacion);
							u.highLight(xmlFechaInicialLiquidacionOp4);
							u.click(xmlFechaInicialLiquidacionOp4);
							result = true;
						}

						//Filtro Hora inicial
						if(u.isDisplayed(xmlHoraInicialLiquidacion)){
							u.highLight(xmlHoraInicialLiquidacion);
							u.click(xmlHoraInicialLiquidacion);
							u.unhighLight(xmlHoraInicialLiquidacion);
							u.highLight(xmlHoraInicialLiquidacionOp1);
							u.click(xmlHoraInicialLiquidacionOp1);
							result = true;
						}

						//Filtro Fecha fin
						if(u.isDisplayed(xmlFechaFinLiquidacion)){
							u.highLight(xmlFechaFinLiquidacion);
							u.click(xmlFechaFinLiquidacion);
							u.unhighLight(xmlFechaFinLiquidacion);
							u.highLight(xmlFechaFinLiquidacionOp1);
							u.click(xmlFechaFinLiquidacionOp1);
							result = true;
						}

						//Filtro Hora fin
						if(u.isDisplayed(xmlHoraFinLiquidacion)){
							u.highLight(xmlHoraFinLiquidacion);
							u.click(xmlHoraFinLiquidacion);
							u.unhighLight(xmlHoraFinLiquidacion);
							u.highLight(xmlHoraFinLiquidacionOplast);
							u.click(xmlHoraFinLiquidacionOplast);
							result = true;
						}

						if(u.isDisplayed(xmlBotonConsultarLiquidacion)){
							u.highLight(xmlBotonConsultarLiquidacion);
							u.click(xmlBotonConsultarLiquidacion);
							u.unhighLight(xmlBotonConsultarLiquidacion);
							testCase.addScreenShotCurrentStep("Se muestra el resultado de la consulta");
							result = true;
						}

						return result;
					}

					/** Metodo para validar los subtitulos de la pantalla Liquidacion de Ruta
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarSubTitulosLiquidacionRuta(){
						boolean result = false;
						if(u.isDisplayed(xmlLabelClasificacionColor) && u.isDisplayed(xmlLabelDetalleLiquidacion)){
							u.highLight(xmlLabelClasificacionColor);
							u.highLight(xmlLabelDetalleLiquidacion);
							testCase.addScreenShotCurrentStep("Se muestran los subtitulos de la pantalla");
							u.unhighLight(xmlLabelClasificacionColor);
							u.unhighLight(xmlLabelDetalleLiquidacion);
							result = true;
						}
						return result;
					}

					/** Metodo para validar los filtros de Liquidacion de Ruta
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarFiltrosLliquidacionRuta(){
						boolean result = false;
						if(u.isDisplayed(xmlInputRutaLiquidacion) && u.isDisplayed(xmlFechaInicialLiquidacion) && u.isDisplayed(xmlHoraInicialLiquidacion) &&
								u.isDisplayed(xmlFechaFinLiquidacion) && u.isDisplayed(xmlHoraFinLiquidacion) && u.isDisplayed(xmlLimpiarFiltros) && u.isDisplayed(xmlBotonConsultarLiquidacion)){
							u.highLight(xmlInputRutaLiquidacion);
							u.highLight(xmlFechaInicialLiquidacion);
							u.highLight(xmlHoraInicialLiquidacion);
							u.highLight(xmlFechaFinLiquidacion);
							u.highLight(xmlHoraFinLiquidacion);
							u.highLight(xmlLimpiarFiltros);
							u.highLight(xmlBotonConsultarLiquidacion);
							testCase.addScreenShotCurrentStep("Se visualizan correctamente los filtros");
							u.unhighLight(xmlInputRutaLiquidacion);
							u.unhighLight(xmlFechaInicialLiquidacion);
							u.unhighLight(xmlHoraInicialLiquidacion);
							u.unhighLight(xmlFechaFinLiquidacion);
							u.unhighLight(xmlHoraFinLiquidacion);
							u.unhighLight(xmlLimpiarFiltros);
							u.unhighLight(xmlBotonConsultarLiquidacion);
							result = true;
						}
						return result;
					}

					/** Metodo para validar el tooltip con fechas en boton consultar
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean consultarLiquidacionFechaTooltipRuta(){
						boolean result = false;
						if(u.isDisplayed(xmlBotonConsultarLiquidacion)){
							u.mouseOver(xmlBotonConsultarLiquidacion);
							if(u.isDisplayed(xmlBtnConsultarTooltip)){
								u.highLight(xmlBtnConsultarTooltip);
								testCase.addScreenShotCurrentStep("El tooltip muestra el mensaje");
								result = true;
							}
						}
						return result;
					}

					/** Metodo para validar mensaje del tooltip con Ruta
					 * @author Jorge Hernandez
					 * @param ruta -> dato que se obtiene desde el DataProvider
					 * @date Abril 2025
					 */
					public boolean consultarLiquidacionRutaTooltipFecha(String ruta){
						boolean result = false;
						if(u.isDisplayed(xmlInputRutaLiquidacion)){
							u.highLight(xmlInputRutaLiquidacion);
							u.click(xmlInputRutaLiquidacion);
							u.unhighLight(xmlInputRutaLiquidacion);
							u.sendKeys(xmlInputRutaLiquidacion2, ruta);
							if(u.isDisplayed(xmlBotonConsultarLiquidacion)){
								u.mouseOver(xmlBotonConsultarLiquidacion);
								if(u.isDisplayed(xmlBtnConsultarTooltip)){
									u.highLight(xmlBtnConsultarTooltip);
									testCase.addScreenShotCurrentStep("El tooltip muestra el mensaje");
									result = true;
								}
							}
						}

						return result;
					}

					/** Metodo para validar el orden de los botones de Liquidacionde Ruta
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarOrdenBotonesLiquidacionRuta(){
						boolean result = false;
						if(u.isDisplayed(xmlLimpiarFiltros)){
							u.highLight(xmlLimpiarFiltros);
							if(u.isDisplayed(xmlConsultarAlLadoLimpiarFiltrosLiquidacion)){
								u.highLight(xmlConsultarAlLadoLimpiarFiltrosLiquidacion);
								testCase.addScreenShotCurrentStep("El orden de los botones es el correcto");
								u.unhighLight(xmlLimpiarFiltros);
								u.unhighLight(xmlConsultarAlLadoLimpiarFiltrosLiquidacion);
								result = true;
							}
						}
						return result;
					}

					/** Metodo para realizar consulta con la ultima tienda de la lista
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean consultaRecepcionTiendaFechas(){
						boolean result = false;
						if(u.isDisplayed(xmlSelectTiendaRecepcion)){
							u.highLight(xmlSelectTiendaRecepcion);
							u.click(xmlSelectTiendaRecepcion);
							u.unhighLight(xmlSelectTiendaRecepcion);
							u.click(xmlTiendaOpLast);
							seleccionRangoFechas(4);
							consultarCedisBusquedaHistorica();
							result = true;
						}
						return result;
					}

					/** Metodo para seleccionar un FUE en Recepcion que no tenga Canastillas Devueltas
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean seleccionarFueSinCanastillasDevueltas(){
						boolean result = false;
						if(u.isDisplayed(xmlBtnFueCeroCanastillas)){
							u.scrollToElement(xmlBtnFueCeroCanastillas);
							u.highLight(xmlBtnFueCeroCanastillas);
							testCase.addScreenShotCurrentStep("Seleccion de FUE sin Canastillas devueltas");
							u.click(xmlBtnFueCeroCanastillas);
							u.unhighLight(xmlBtnFueCeroCanastillas);
						}
						return result;
					}

					/** Metodo para validar que el boton de Ver Evidencia de un FUE V2 este desactivado
					 * @author Jorge Hernandez
					 * @date Abril 2025
					 */
					public boolean validarBotonVerEvidenciaInhabilitado(){
						boolean result = false;
						if(u.isDisplayed(xmlBtnVerEvidenciaFueDisabled)){
							u.highLight(xmlBtnVerEvidenciaFueDisabled);
							testCase.addScreenShotCurrentStep("Se valida que el boton esta deshabilitado");
							result = true;
						}
						return result;
					}

					/**
					 * Metodo para consultar con proveedor y rango de fechas para mostrar los resultados en pantalla Directos
					 * @param meses toma el valor de la cantidad de meses a retroceder para obtener resultados en la consulta
					 * @author Jorge Hernandez
					 * @date Agosto 2024
					 */
					public boolean consultaProveedorComprobantesCompra(String proveedor, int meses){
						boolean result = false;
						u.waitForLoadPage();
						u.mouseOver(xmlProveedor);
						if(u.isDisplayed(xmlProveedor)){
							u.highLight(xmlProveedor);
							u.click(xmlProveedor);
							u.sendKeys(xmlProveedorInput, proveedor);
							u.waitWebElement(xmlProveedorOp1, 130, 2);
							u.unhighLight(xmlProveedor);
							u.click(xmlProveedorOp1);

							result = true;
						} else {
							result = false;
						}

						if(u.isDisplayed(xmlRangoFechas)){
							u.highLight(xmlRangoFechas);
							u.mouseOverAndClick(xmlRangoFechas);
							u.unhighLight(xmlRangoFechas);

							result = true;
						} else {
							result = false;
						}

						if(u.isDisplayed(xmlBotonFlechaIzquierda)){
							u.highLight(xmlBotonFlechaIzquierda);
							for (int i = 0; i < meses; i++) { // valor de i para cuantos meses atras seleccionar fecha
								u.mouseOverAndClick(xmlBotonFlechaIzquierda);
							}
							u.unhighLight(xmlBotonFlechaIzquierda);

							u.highLight(xmlPrimerDiaHabilidato);
							u.mouseOver(xmlPrimerDiaHabilidato);
							u.click(xmlPrimerDiaHabilidato);
							u.unhighLight(xmlPrimerDiaHabilidato);

							if(u.isDisplayed(xmlUltimoDiaHabilitado)){
								u.highLight(xmlUltimoDiaHabilitado);
								u.mouseOver(xmlUltimoDiaHabilitado);
								u.click(xmlUltimoDiaHabilitado);
							}else if(u.isDisplayed(xmlFechaFinalMes30)){
								u.highLight(xmlFechaFinalMes30);
								u.mouseOver(xmlFechaFinalMes30);
								u.click(xmlFechaFinalMes30);
							} else if (u.isDisplayed(xmlFechaFinalMes28)) {
								u.highLight(xmlFechaFinalMes28);
								u.mouseOver(xmlFechaFinalMes28);
								u.click(xmlFechaFinalMes28);
							}

						}
						testCase.addScreenShotCurrentStep(u, "Filtros listos para consulta");

						return result;
					}

					/**
					 * Metodo para validar en Grid de Comprobantes de Compra el formato de el nombre de la tienda sin el CRTienda
					 * @author Jorge Hernandez
					 * @date 23 Mayo 2025
					 */
					public Boolean validarNombreTiendaNoCRGrid(){
						boolean result = false;
						if(u.isDisplayed(gridObtenerCRTienda1)){
							u.highLight(gridNombreTienda1);
							String nombreTienda = u.getText(gridNombreTienda1);
							u.unhighLight(gridNombreTienda1);

							u.highLight(gridObtenerCRTienda1);
							String crTienda1 = u.getText(gridObtenerCRTienda1);
							u.unhighLight(gridObtenerCRTienda1);

                            result = !nombreTienda.contains(crTienda1);
							testCase.addScreenShotCurrentStep("Validacion Nombre de Tienda en grid");
						}
						return result;
					}

					/**
					 * Metodo para validar el formato de el nombre de tienda en la vista preliminar de el Comprobante de Compra Directos
					 * @author Jorge Hernandez
					 * @date 23 Mayo 2025
					 */
					public Boolean validarNombreTiendaNoCRVistaPreliminar(){
						boolean result = false;
						u.hardWait(10); //tiempo para que cargue la vista preliminar de el Comprobante de Compra
						if(u.isDisplayed(preliminarObtenerCRTienda) && u.isDisplayed(preliminarNombreTienda)){
							u.highLight(preliminarObtenerCRTienda);
							String preliminarCRTienda = u.getText(preliminarObtenerCRTienda);
							u.unhighLight(preliminarObtenerCRTienda);

							u.highLight(preliminarNombreTienda);
							String preliminarNombreT = u.getText(preliminarNombreTienda).trim();
							u.unhighLight(preliminarNombreTienda);

							String crTienda = preliminarCRTienda.split(": ")[1].trim();

							System.out.println(crTienda);
							result = !preliminarNombreT.contains(crTienda);
							testCase.addScreenShotCurrentStep("Validacion Nombre de Tienda en vista preliminar");

						}
						return result;
					}

					/**
					 * Metodo para validar que el texto ACUERDO DE ENTREGA de la vista preliminar de el Comprobante de Compra esa el correcto
					 * @author Jorge Hernandez
					 * @date 23 Mayo 2025
					 */
					public boolean validarEscrituraAcuerdoEntrega(String txtSeccion){
						boolean result = false;
						if(u.isDisplayed(textoAcuerdoEntrega)){
							u.highLight(textoAcuerdoEntrega);
							testCase.addScreenShotCurrentStep("Texto validado");
							String nombreSeccion = u.getText(textoAcuerdoEntrega);
							u.unhighLight(textoAcuerdoEntrega);

							result = txtSeccion.equals(nombreSeccion);
						}
						return result;
					}

					/**
					 * Metodo para validar que en la tabla Articulos Entregados los campos de Costo, Precio y Valtot tengan el formato de moneda $
					 * @author Jorge Hernandez
					 * @date 23 Mayo 2025
					 */
					public Boolean validarFormatoMonedaTablaPreliminar(){
						boolean result = false;
						u.hardWait(10);
						if (u.isDisplayed(tablaArticulosEntregados)){
							u.scrollToElement(tablaArticulosEntregados);
							if (u.isDisplayed(formatoMonedaTablaPrelimnar)){
								List<WebElement> elements = u.listElements(formatoMonedaTablaPrelimnar);
								for (int i = 1; i < elements.size()+1; i++) {
									By elementI = By.xpath("(//td[starts-with(text(), '$')])" + "[" + i + "]");
									u.highLight(elementI);
								}
                                testCase.addScreenShotCurrentStep("Validacion de formato moneda");
								result = true;
							} else {
								result = false;
							}

						}
						return result;
					}

					/**
					 * Metodo para validar el formato correcto de el campo Total General a la Venta
					 * @author Jorge Hernandez
					 * @date 26 Mayo 2025
					 */
					public Boolean validarformatoTotalGeneralVenta(String txtSeccion){
						boolean result = false;
						u.hardWait(10);
						if(u.isDisplayed(textoTotalGeneralVenta)){
							u.scrollToElement(textoTotalGeneralVenta);
							u.highLight(textoTotalGeneralVenta);
							String nombreSeccion = u.getText(textoTotalGeneralVenta);
							testCase.addScreenShotCurrentStep("Validacion formato campo Total General a la Venta");
							u.unhighLight(textoTotalGeneralVenta);
							String texto = nombreSeccion.split(": ")[0];

							result = txtSeccion.equals(texto);
						}
						return result;
					}

					/**
					 * Metodo para dar validar la existencia del boton cerrar en la vista de FUE
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean ClicVerCerrarFUE() {
						boolean isValid = true; // Inicializamos la bandera en true

						try {
							u.highLight(xmlCerrarFUE);
							u.mouseOver(xmlCerrarFUE);
							testCase. addScreenShotCurrentStep(u, "Cerrar FUE existe");
							u.click(xmlCerrarFUE);
							testCase. addScreenShotCurrentStep(u, "Cerrar FUE existe");
						} catch (Exception e) {
							isValid = false; // Si ocurre una excepci , la bandera se pone en false
						}

						return isValid; // Devolvemos la bandera
					}

					/**
					 * Metodo para validar que se cierra el FUE V1
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean ClicVerCerrarFUEv1() {
						boolean isValid = true; // Inicializamos la bandera en true

						try {
							u.highLight(xmlCerrarFUEv1);
							u.mouseOver(xmlCerrarFUEv1);
							testCase. addScreenShotCurrentStep(u, "Cerrar FUE existe");
							u.click(xmlCerrarFUEv1);
						} catch (Exception e) {
							isValid = false; // Si ocurre una excepci , la bandera se pone en false
						}

						return isValid; // Devolvemos la bandera
					}


					/**
					 * Metodo para dar validar la existencia del boton cerrar en la vista de FUE
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
						public Boolean ClicPaginadoFUE() {
							boolean result;

							if(u.isDisplayed(xmlPaginadoNextCEDIS)) {
								u.highLight(xmlPaginadoNextCEDIS);
								u.mouseOver(xmlPaginadoNextCEDIS);
								u.click(xmlPaginadoNextCEDIS);
								testCase. addScreenShotCurrentStep(u, "Next");
								u.highLight(xmlPaginadoLastCEDIS);
								u.mouseOver(xmlPaginadoLastCEDIS);
								u.click(xmlPaginadoLastCEDIS);
								testCase. addScreenShotCurrentStep(u, "Next");
								 result = true;
							 }else {result = false;
						}
							return result;
						}

					/**
					 * Metodo para validar la existencia de los elemntos correspondientes
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarTiraRecepcion() {
						boolean result;

						if(u.isDisplayed(xmlDescargarComprobanteTira)){
							 u.highLight(xmlDescargarComprobanteTira);
							 u.mouseOver(xmlDescargarComprobanteTira);
							 testCase. addScreenShotCurrentStep(u, "Descarga");
							 u.unhighLight(xmlDescargarComprobanteTira);
							 u.highLight(xmlNextPageTira);
							 u.mouseOver(xmlNextPageTira);
							 testCase. addScreenShotCurrentStep(u, "Next");
							 u.unhighLight(xmlNextPageTira);
							 u.highLight(xmlLastPageTira);
							 u.mouseOver(xmlLastPageTira);
							 testCase. addScreenShotCurrentStep(u, "Last");
							 u.unhighLight(xmlLastPageTira);
							 u.highLight(xmlTituloTira);
							 if(u.getText(xmlTituloTira).equals("Tira de Transferencia")){
								 result = true;
							 	testCase.addScreenShotCurrentStep("Validacion de comprobantes nivel nacional");
							 }
							result = true;
						 }else {result = false;
					}
						return result;
					}

					/**
					 * Metodo para validar la existencia de los elementos de evidencias fotograficas
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarFotoRecepcion() {
						boolean result;

						if(u.isDisplayed(xmlNextPageFoto)){
							 u.highLight(xmlNextPageFoto);
							 u.mouseOver(xmlNextPageFoto);
							 testCase.addScreenShotCurrentStep(u, "Next");
							 u.unhighLight(xmlNextPageFoto);
							 u.highLight(xmlLastPageFoto);
							 u.mouseOver(xmlLastPageFoto);
							 testCase.addScreenShotCurrentStep(u, "Last");
							 u.unhighLight(xmlLastPageFoto);
							 u.highLight(xmlLastPageFoto);
							 if(u.getText(xmlTituloFoto).contains("Evidencias")){
							 	testCase.addScreenShotCurrentStep("Titulo foto");
								result = true;
							 }
							result = true;
						 }else {result = false;
					}
						return result;
					}


					/**
					* Metodo para dar clic en el boton Ver evidencia fotografica
					* @author Yair Lizarraga
					 * @date Abril 2025
					*/
					public Boolean ClicVerFotografica() {
						boolean isValid = true; // Inicializamos la bandera en true

						try {
							u.scrollToElement(By.xpath("(//span[@class='mdc-tab__content'])[1]"));
							u.highLight(xmlVerFotograficas);
							u.mouseOverAndClick(xmlVerFotograficas);
							u.unhighLight(xmlVerFotograficas);
							u.hardWait(5);
							testCase.addScreenShotCurrentStep(u, "xmlVerFotograficas");
						} catch (Exception e) {
							isValid = false; // Si ocurre una excepcion, la bandera se pone en false
						}

						return isValid; // Devolvemos la bandera
					}

					/**
					 * Metodo para validar que funciona e boton cerrar Tira
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean ClicVerCerrarTira() {
						boolean isValid = true; // Inicializamos la bandera en true

						try {
							u.highLight(xmlCerrarTira);
							u.mouseOver(xmlCerrarTira);
							testCase. addScreenShotCurrentStep(u, "Cerrar Tira existe");
							u.click(xmlCerrarTira);
							testCase. addScreenShotCurrentStep(u, "Cerrar Tira existe");
						} catch (Exception e) {
							isValid = false; // Si ocurre una excepci , la bandera se pone en false
						}

						return isValid; // Devolvemos la bandera
					}

					/**
					 * Metodo para validar que funciona el boton cerrar evidencia fotografica
					 * @author Yair Lizarraga
			 		 * @date Abril 2025
					 */
					public Boolean ClicVerCerrarFotografica() {
						boolean isValid = true; // Inicializamos la bandera en true

						try {
							u.highLight(xmlCerrarFotografica);
							u.mouseOver(xmlCerrarFotografica);
							u.click(xmlCerrarFotografica);
							testCase. addScreenShotCurrentStep(u, "Cerrar Tira existe");
						} catch (Exception e) {
							isValid = false; // Si ocurre una excepci , la bandera se pone en false
						}

						return isValid; // Devolvemos la bandera
					}

					/**
					 * Metodo para validar el filtrado a 3 anos
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public boolean Filtrado3anos(){ //Metodo para realizar Busqueda rapida con fechas para mostrar botones de descarga en CEDIS
						boolean result;
						u.hardWait(5);
						if(u.isDisplayed(xmlRangoFechasCedisRecep)){
							u.highLight(xmlRangoFechasCedisRecep);
							u.mouseOverAndClick(xmlRangoFechasCedisRecep);
							testCase. addScreenShotCurrentStep(u, "Cerrar Tira existe");
							u.unhighLight(xmlRangoFechasCedisRecep);
							u.click(xmlRangoAnos);
							u.highLight(xmlRango3Anos);
							testCase. addScreenShotCurrentStep(u, "Cerrar Tira existe");

							result = true;
						} else {
							result = false;
						}
						return result;
					}

					/**
					 * Metodo para validar que se muestra el tooltip
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarTooltip() {
						boolean isValid = true; // Inicializamos la bandera en true
						u.waitWebElement(xmlPrimerFueGrid, 70, 2);
						seleccionarPlazaBusquedaHistorica();
						seleccionPlazaOp2();
						try {
							u.highLight(xmlConsultarBusqHistorica1);
							u.mouseOver(xmlConsultarBusqHistorica1);
							u.highLight(xmlTooltipFecha);
							testCase.addScreenShotCurrentStep(u, "Tooltip");
						} catch (Exception e) {
							isValid = false; // Si ocurre una excepcion, la bandera se pone en false
						}

						return isValid; // Devolvemos la bandera
					}

					/**
					 * Metodo para validar que se muestra el tooltip
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public boolean validarTooltipFechaDIRECTOS(){
						boolean result;
						u.waitWebElement(xmlPrimerComprobanteFiltro, 120, 2);
						if(u.isDisplayed(xmlProveedor)) {
							u.highLight(xmlProveedor);
							u.mouseOverAndClick(xmlProveedor);
							u.unhighLight(xmlProveedor);
							u.highLight(xmlProveedorOp1);
							testCase.addScreenShotCurrentStep(u, "Seleccion de proveedor");
							u.click(xmlProveedorOp1);
							 u.highLight(xmlConsultar);
								u.mouseOver(xmlConsultar);
								u.highLight(xmlTooltipFecha);
								testCase. addScreenShotCurrentStep(u, "Tooltip");
							result = true;
						} else {
							result = false;
						}
						return result;
					}

					/**
					 * Metodo para validar que existe el user
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarUser() {
						boolean isValid = true; // Inicializamos la bandera en true

						try {
							u.highLight(xmlUserButton);
							testCase. addScreenShotCurrentStep(u, "User");
							u.click(xmlUserButton);
						} catch (Exception e) {
							isValid = false; // Si ocurre una excepci n, la bandera se pone en false
						}

						return isValid; // Devolvemos la bandera
					}

					 /**
					 * Metodo para validar que existe el boton ver evidencia
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarVerEvidencia() {
						boolean isValid = true; // Inicializamos la bandera en true

						try {
							 u.highLight(xmlVerEvidencia);
								u.mouseOver(xmlVerEvidencia);
								testCase. addScreenShotCurrentStep(u, "Ver Evidencia");
						} catch (Exception e) {
							isValid = false; // Si ocurre una excepcion, la bandera se pone en false
						}

						return isValid; // Devolvemos la bandera
					}

					/**
					 * Metodo para validar que se abre la evidencia
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean clicVerEvidencia() {
						boolean result;

						if(u.isDisplayed(xmlCanastilla)) {
							u.scrollToElement(xmlCanastilla);
							u.highLight(xmlVerEvidencia);
							u.click(xmlVerEvidencia);
							u.highLight(xmlTablaCanastilla);
							testCase. addScreenShotCurrentStep(u, "Ver Evidencia");
							result = true;
					} else {
						result = false;
					}
						return result;
					}

					/**
					 * Metodo para validar que se cierra evidencia
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean clicVerEvidenciaCerrar() {
						boolean result;

						if(u.isDisplayed(xmlCanastilla)) {
							u.scrollToElement(xmlCanastilla);
							u.highLight(xmlVerEvidencia);
							u.click(xmlVerEvidencia);
							u.highLight(xmlTablaCanastilla);
							testCase. addScreenShotCurrentStep(u, "Ver Evidencia");
							u.click(xmlVerEvidencia);
							testCase. addScreenShotCurrentStep(u, "Ver Evidencia cerrado");
							result = true;
					} else {
						result = false;
					}
						return result;
					}

					/**
					 * Metodo para validar que se visualiza la canastilla
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean clicVerCanastilla() {
						boolean result;

						if(u.isDisplayed(xmlCanastilla)) {
							u.scrollToElement(xmlCanastilla);
							u.highLight(xmlCanastilla);
							testCase. addScreenShotCurrentStep(u, "Ver Canastilla");
							result = true;
					} else {
						result = false;
					}
						return result;
					}

					 /**
					 * Metodo para validar que se descargo el FUE Masivo
					 * @author Yair Lizarraga
					   * @date Abril 2025
					 */
					public Boolean clicDescargaMasivaFue() {
						boolean result;

						if(u.isDisplayed(xmlDescargaMasivaFue)) {
							u.highLight(xmlDescargaMasivaFue);
							u.click(xmlDescargaMasivaFue);
							testCase. addScreenShotCurrentStep(u, "Descarga Masiva FUE");
							u.hardWait(90);
							result = true;
					} else {
						result = false;
					}
						return result;
					}

					/**
					 * Metodo para validar que se descargo el FUE Masivo
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarCarpetaDescargaMasivaFue() {
						Boolean exist = false;
						String home = System.getProperty("user.home");
						home = home + "\\Downloads";
						String expectedFileNamePart = "CEDIS - FUE - Comprobantes filtrados";
						String pathYourSystem = getLastModified(home).getAbsolutePath();
						File f = new File(pathYourSystem);
						if(pathYourSystem.contains(expectedFileNamePart) && pathYourSystem.endsWith(".zip") && f.exists()){
							openLastFileDownloadFolder(".zip");
							exist = true;
						}
						return exist;

					}

					 /**
					 * Metodo para validar que se descargo el FUE Individual
					 * @author Yair Lizarraga
					  * @date Abril 2025
					 */
					
					public Boolean validarNoExistRuta() {
						boolean result;

						if(u.isDisplayed(xmlDescargaIndividualFue)) {
							result = false;
					} else {
						u.highLight(xmlDescargaIndividualFue);
						u.click(xmlDescargaIndividualFue);
						testCase. addScreenShotCurrentStep(u, "Descarga Individual FUE");
						u.hardWait(10);
						result = true;
					}
						return result;
						
					}
					public Boolean clicDescargaIndividualFue() {
						boolean result;

						if(u.isDisplayed(xmlDescargaIndividualFue)) {
							u.highLight(xmlDescargaIndividualFue);
							testCase. addScreenShotCurrentStep(u, "Cerrar Tira existe");
							u.click(xmlDescargaIndividualFue);
							testCase. addScreenShotCurrentStep(u, "Descarga Individual FUE");
							u.hardWait(10);
							result = true;
					} else {
						result = false;
					}
						return result;
					}

					/**
					 * Metodo para validar que se descargo el FUE Individual
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarCarpetaDescargaIndividualFue() {
						Boolean exist = false;
						String home = System.getProperty("user.home");
						home = home + "\\Downloads";
						String expectedFileNamePart = "CEDIS - FUE";
						String pathYourSystem = getLastModified(home).getAbsolutePath();
						File f = new File(pathYourSystem);
						if(pathYourSystem.contains(expectedFileNamePart) && pathYourSystem.endsWith(".pdf") && f.exists()){
							openLastFilePDFDownloadFolder(".pdf", u);
							exist = true;
						}
						return exist;

					}

					 /**
					 * Metodo para validar que el fue es V2
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarFUEV2() {
						boolean result;
						if(u.isDisplayed(xmlRutaPropuesta)){
							 u.highLight(xmlRutaPropuesta);
							 u.mouseOver(xmlRutaPropuesta);
							 testCase. addScreenShotCurrentStep(u, "Descarga");
							 String textoElemento = u.getText(xmlRutaPropuesta);
							 System.out.println(textoElemento);
							 if (textoElemento.contains("Ruta Propuesta:")) {
									// Registro de caso de prueba exitoso
									testCase.addScreenShotCurrentStep("Ruta propuesta validada correctamente");
									result = true;
								} else {
									// Registro de caso de prueba fallido
									result = false;
								}
							} else {
								result = false;
							}

							return result;
					}

					 /**
					 * Metodo para validar que el fue es V1
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarFUEV1() {
						boolean result;

						// Verificar si el elemento NO estÃ¡ visible
						if (u.isDisplayed(xmlRutaPropuesta)) {
							// Registro de caso de prueba exitoso porque el elemento no existe
							testCase.addScreenShotCurrentStep("ValidaciÃ³n correcta: El elemento 'Ruta Propuesta' no existe.");
							result = false;
						} else {
							// Si el elemento existe, realizar acciones adicionales y validar su contenido
								result = true;
						}

						return result;
					}

					 /**
					 * Metodo para validar que cierra el fue V1
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarCerrarFUEV1() {
						boolean result;  // Inicializamos la bandera en true

						if(u.isDisplayed(xmlCerrarFUEv1)){
							 u.highLight(xmlCerrarFUEv1);
							 u.mouseOver(xmlCerrarFUEv1);
							 testCase. addScreenShotCurrentStep(u, "Ver Evidencia");
								result = true;
					} else {
						result = false;
					}
						return result;
					}

					 /**
					 * Metodo para validar que existen los filtros CEDIS
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public boolean filtradoCedisPlaza(){ //Metodo para realizar Busqueda rapida con fechas para mostrar botones de descarga en CEDIS
						boolean result;
						u.hardWait(16);
						if(u.isDisplayed(xmlCedisCRPlaza)){
							u.highLight(xmlCedisCRPlaza);
							u.click(xmlCedisCRPlaza);
							u.unhighLight(xmlCedisCRPlaza);
							u.click(xmlCRPlazaOptionMTYNorte);
							u.highLight(xmlCRPlazaOptionMTYNorte);
							u.click(xmlCRPlazaOption2);
							u.highLight(xmlCRPlazaOption2);
							testCase. addScreenShotCurrentStep(u, "Cerrar Tira existe");
							u.click(xmlTituloFiltroBusquedaRapida);
							result = true;
						} else {
							result = false;
						}
						return result;
					}

					/**
					 * Metodo para validar que existen los filtros CEDIS
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public boolean filtradoCedisTienda(){ //Metodo para realizar Busqueda rapida con fechas para mostrar botones de descarga en CEDIS
						boolean result;
						u.hardWait(5);
						if(u.isDisplayed(xmlCedisCRTienda)){
							u.highLight(xmlCedisCRTienda);
							u.click(xmlCedisCRTienda);
							u.unhighLight(xmlCedisCRTienda);
							u.click(xmlCRPlazaOptionMTYNorte);
							u.highLight(xmlCRPlazaOptionMTYNorte);
							u.click(xmlCRPlazaOption2);
							u.highLight(xmlCRPlazaOption2);
							testCase. addScreenShotCurrentStep(u, "Cerrar Tira existe");
							result = true;
						} else {
							result = false;
						}
						return result;
					}

					/**
					 * Metodo para validar que el filtro plaza se llama Plaza
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarFiltroPlaza() {
						boolean result;
						if(u.isDisplayed(xmlPlazaPlaceHolder)){
							 u.highLight(xmlPlazaPlaceHolder);
							 u.mouseOver(xmlPlazaPlaceHolder);
							 testCase. addScreenShotCurrentStep(u, "Plaza");
							 String textoElemento = u.getText(xmlPlazaPlaceHolder);
							 System.out.println(textoElemento);
							 if (textoElemento.contains("Plaza")) {
									// Registro de caso de prueba exitoso
									testCase.addScreenShotCurrentStep("Ruta propuesta validada correctamente");
									result = true;
								} else {
									// Registro de caso de prueba fallido
									result = false;
								}
							} else {
								result = false;
							}

							return result;
					}

					/**
					 * Metodo para validar que el filtro tienda se llama Tienda
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarFiltroTienda() {
						boolean result;
						if(u.isDisplayed(xmlTiendaPlaceHolder)){
							 u.highLight(xmlTiendaPlaceHolder);
							 u.mouseOver(xmlTiendaPlaceHolder);
							 testCase. addScreenShotCurrentStep(u, "Tienda");
							 String textoElemento = u.getText(xmlTiendaPlaceHolder);
							 System.out.println(textoElemento);
							 if (textoElemento.contains("Tienda")) {
									// Registro de caso de prueba exitoso
									testCase.addScreenShotCurrentStep("Ruta propuesta validada correctamente");
									result = true;
								} else {
									// Registro de caso de prueba fallido
									result = false;
								}
							} else {
								result = false;
							}

							return result;
					}

					/**
					 * Metodo para validar los filtros de la pagina de comprobantes
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public boolean validarFiltrosExistentesRecepcion(){
						boolean result;
						if(u.isDisplayed(xmlPlazaPlaceHolder)){
							u.highLight(xmlPlazaPlaceHolder);
							result = true;
						} else {
							result = false;
						}

						if(u.isDisplayed(xmlTiendaPlaceHolder)){
							u.highLight(xmlTiendaPlaceHolder);
							result = true;
						} else {
							result = false;
						}

						if(u.isDisplayed(xmlSelectorRutaPlaceHolder)){
							u.highLight(xmlSelectorRutaPlaceHolder);
							result = true;
						} else {
							result = false;
						}

						if(u.isDisplayed(xmlRutaPlaceHolder)){
							u.highLight(xmlRutaPlaceHolder);
							result = true;
						} else {
							result = false;
						}

						if(u.isDisplayed(xmlRangoFechasCedisRecep)){
							u.highLight(xmlRangoFechasCedisRecep);
							result = true;
						} else {
							result = false;
						}

						if(u.isDisplayed(xmlBusquedaRapida)){
							u.highLight(xmlBusquedaRapida);
							result = true;
						} else {
							result = false;
						}

						if(result){
							testCase.addScreenShotCurrentStep(u, "Validacion de Filtros en la pantalla");
						}

						u.unhighLight(xmlPlazaPlaceHolder);
						u.unhighLight(xmlTiendaPlaceHolder);
						u.unhighLight(xmlSelectorRutaPlaceHolder);
						u.unhighLight(xmlRutaPlaceHolder);
						u.unhighLight(xmlRangoFechasCedisRecep);
						u.unhighLight(xmlBusquedaRapida);

						return result;
					}
					
					public Boolean validarCamposTiraNoVacios() {
					    boolean result = true;

					    // Validar el campo PLAZA
					    String textoPlaza = u.getText(xmlPlazaTXT); // xmlElementoPlaza es el identificador del elemento que contiene el valor de PLAZA
					    u.highLight(xmlPlazaTXT);
					    if (textoPlaza.trim().isEmpty()) { // Validar que el texto no estÃ© vacÃ­o
					        testCase.addScreenShotCurrentStep("El campo PLAZA estÃ¡ vacÃ­o.");
					        result = false;
					    }

					    // Validar el campo TIENDA
					    String textoTienda = u.getText(xmlTiendaTXT); // xmlElementoTienda es el identificador del elemento que contiene el valor de TIENDA
					    u.highLight(xmlTiendaTXT);
					    if (textoTienda.trim().isEmpty()) { // Validar que el texto no estÃ© vacÃ­o
					        testCase.addScreenShotCurrentStep("El campo TIENDA estÃ¡ vacÃ­o.");
					        result = false;
					    }

					    // Validar el campo FECHA
					    String textoFecha = u.getText(xmlFechaTXT); // xmlElementoFecha es el identificador del elemento que contiene el valor de FECHA
					    u.highLight(xmlFechaTXT);
					    if (textoFecha.trim().isEmpty()) { // Validar que el texto no estÃ© vacÃ­o
					        testCase.addScreenShotCurrentStep("El campo FECHA estÃ¡ vacÃ­o.");
					        result = false;
					    }

					    // Validar el campo FECHA ADMVA
					    String textoFechaAdmva = u.getText(xmlFechaADMVTXT); // xmlElementoFechaAdmva es el identificador del elemento que contiene el valor de FECHA ADMVA
					    u.highLight(xmlFechaADMVTXT);
					    if (textoFechaAdmva.trim().isEmpty()) { // Validar que el texto no estÃ© vacÃ­o
					        testCase.addScreenShotCurrentStep("El campo FECHA ADMVA estÃ¡ vacÃ­o.");
					        result = false;
					    }

					    return result;
					}
					
					/**
					 * Metodo para validar que no se muestra el tooltip
					 * @author Yair Lizarraga
					 * @date Abril 2025
					 */
					public Boolean validarNoTooltip() {
						  boolean isValid = true; // Inicializamos la bandera en true
						    try {
						        // Intentamos interactuar con el elemento del tooltip
						        // Verificamos si el tooltip existe
						        if (u.isDisplayed(xmlTooltipFecha)) { // MÃ©todo que verifica si el elemento estÃ¡ presente
						            testCase.addScreenShotCurrentStep(u, "El tooltip estÃ¡ presente cuando no deberÃ­a.");
						            isValid = false; // Si el tooltip existe, cambiamos la bandera a false
						        }
						    } catch (Exception e) {
						        // Si ocurre una excepciÃ³n, asumimos que el tooltip no existe
						        testCase.addScreenShotCurrentStep(u, "ExcepciÃ³n capturada, el tooltip no existe.");
						    }

						    return isValid; // Devolvemos la bandera
						}
					
					/**
		 			* Metodo para validar la busqueda con filtro Proveedor en Directos
		 			* @author Isaac E. Garcia
		 			* @param  meses 			->	meses atrÃ¡s a la que se harÃ¡ la consulta
		 			* @return true si la selecciÃ³n se pudo elegir
					* @date Mayo 2025
					*/
					public boolean busquedaBusquedaHistorica(int meses){
						boolean result=false;

						u.mouseOver(xmlRangoFechas);
						if(u.isDisplayed(xmlRangoFechas)) {
							u.highLight(xmlRangoFechas);
							u.mouseOver(xmlRangoFechas);
							u.click(xmlRangoFechas);
							u.unhighLight(xmlRangoFechas);

							u.highLight(xmlBotonFlechaIzquierda);
							u.mouseOver(xmlBotonFlechaIzquierda);
							for (int i = 0; i < meses; i++) { // valor para contemplar x meses atras en rango de fechas
								u.click(xmlBotonFlechaIzquierda);
							}
							u.unhighLight(xmlBotonFlechaIzquierda);

							if(u.isDisplayed(xmlFechaInicioMes)){
								u.highLight(xmlFechaInicioMes);
								u.mouseOver(xmlFechaInicioMes);
								u.click(xmlFechaInicioMes);
								u.unhighLight(xmlFechaInicioMes);
							}

							u.highLight(xmlBotonFlechaDerecha);
							u.mouseOver(xmlBotonFlechaDerecha);
							// valor para contemplar 4 meses atras en rango de fechas
							for (int i = 0; i < 3; i++) {
							    WebElement botonDerecho = u.getDriver().findElement(xmlBotonFlechaDerecha);

							    if (botonDerecho.isEnabled()) {
							    	u.click(xmlBotonFlechaDerecha);
							    } else {
							        System.out.println("BotÃ³n deshabilitado, se detiene el avance.");
							        break;
							    }
							}
							u.unhighLight(xmlBotonFlechaDerecha);

							if(u.isDisplayed(xmlUltimoDiaHabilitado)){
								u.highLight(xmlUltimoDiaHabilitado);
								u.mouseOver(xmlUltimoDiaHabilitado);
								u.click(xmlUltimoDiaHabilitado);
							}
							testCase.addScreenShotCurrentStep(u, "Rango de fechas seleccionado");
							
							u.highLight(By.xpath("(//button//*[contains(text(), 'Consultar')])[1]"));
							u.click(By.xpath("(//button//*[contains(text(), 'Consultar')])[1]"));
							u.unhighLight(By.xpath("(//button//*[contains(text(), 'Consultar')])[1]"));
							
							if(u.waitWebElement(By.xpath("//tbody//tr[1]"), 30, 2).isDisplayed())
								result=true;

							u.hardWait(10);
							//consultarComprobantes();
						}
						return result;
					}
					
				
					
					/**
		 			* Metodo para seleccionar un registro de FUE V1
		 			* @author Isaac E. Garcia
		 			* @return true si la selecciÃ³n se pudo elegir
					* @date Mayo 2025
					*/
					public boolean seleccionarFUE_V1() {
						boolean result=false;
						String transferencia="";
						String dbUrl = GlobalVariables.DB_URL_Cometa;
			    	    String dbUser = GlobalVariables.DB_USER_Cometa;
			    	    String dbPassword = GlobalVariables.DB_PASSWORD_Cometa;

			    	    String query = 	"SELECT 		transferencia " +
			    	    				"FROM 			public.xxcm_historico_correos "+
			    	    				"WHERE 			version_documento IS NULL "
			    	    				+ "				AND transferencia LIKE('40%')"
			    	    				+ "				AND is_visible = true AND estatus = true"
			    	    				+ "				AND origen = 'cedis'"
			    	    				+ "				AND id_retek IN ('16266')"+
			    	    				"ORDER BY 		created_at DESC "+ 
			    	    				"LIMIT 1;";

			    	    
			    	    try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
			    	            Statement stmt = connection.createStatement();
			    	            ResultSet rs = stmt.executeQuery(query)) {
			    	    
			    	           if (rs.next()) {
			    	               transferencia = rs.getString("transferencia"); // Guarda el valor
			    	               testCase.addTextEvidenceCurrentStep("Transferencia encontrada: " + transferencia);
			    	               
			    	               // Buscar la fila que contenga la transferencia especÃ­fica y hacer clic en el botÃ³n "Ver" de la columna FUE
			    	               String xpathBotonVer = "//tr[td[contains(text(), '" + transferencia + "')]]/td[contains(@class, 'mat-column-FUE')]//button[.//span[text()=' Ver ']]";
			    	               
			    	               WebElement botonVer = u.getDriver().findElement(By.xpath(xpathBotonVer));
			    	               u.highLight(By.xpath(xpathBotonVer));
			    	               u.scrollToElement(By.xpath(xpathBotonVer));
			    	               testCase.addScreenShotCurrentStep(u, "Se da clic en boton Ver FUE");
			    	               u.unhighLight(By.xpath(xpathBotonVer));
			    	               
			    	               botonVer.click();
			    	               result=true;
			    	               esperarCarga(3);
			    	               testCase.addScreenShotCurrentStep(u, "Se muestra la vista preliminar");
			    	               //validarFormatoFUE_V1();
			    	               
			    	               
			    	           } else {
			    	               testCase.addTextEvidenceCurrentStep("No se encontrÃ³ ninguna transferencia.");
			    	           }
			    	    
			    	       } catch (Exception e) {
			    	    	   System.out.println("Error --> "+ e.getMessage());
			    	           e.printStackTrace();
			    	       }

			    	   return result;
					}
					
					
					/**
		 			* Metodo para validar el mensaje de ComingSoon en Directos
		 			* @author Isaac E. Garcia
		 			* @return true si la selecciÃ³n se pudo elegir
					* @date Mayo 2025
					*/
					public boolean validarComingSoonDirectos() {
						boolean result=false;
						
						try {
							//Ingresar a la pantalla de repotes en Directos
							if(u.isDisplayed(xmlOpcionReportes)){
								u.highLight(xmlOpcionReportes);
								testCase.addScreenShotCurrentStep(u, "Se da clic en la opcion de reportes");
								u.unhighLight(xmlOpcionReportes);
								u.click(xmlOpcionReportes);
								
								//Validar el mensaje de Coming Soon!
								if(u.isDisplayed(xmlReporteDirectosMsg)) {
									u.highLight(xmlReporteDirectosMsg);
									testCase.addScreenShotCurrentStep(u, "Se muestra el mensaje de Coming Soon!");
									u.unhighLight(xmlReporteDirectosMsg);
									
									result = true;
								}
							} 

						} catch (Exception e) {
							
							e.printStackTrace();
							return false;
						}

						
						return result;
					}
					
					/**
		 			* Metodo para validar el mensaje de ComingSoon en Directos
		 			* @author Isaac E. Garcia
		 			* @return true si la selecciÃ³n se pudo elegir
					* @date Mayo 2025
					*/
					public boolean validarComingSoonCEDIS() {
						boolean result=false;
						
						try {
							//Ingresar a la pantalla de repotes en Directos
							if(u.isDisplayed(xmlOpcionReportes)){
								u.highLight(xmlOpcionReportes);
								testCase.addScreenShotCurrentStep(u, "Se da clic en la opcion de reportes");
								u.unhighLight(xmlOpcionReportes);
								u.click(xmlOpcionReportes);
								
								//Validar el mensaje de Coming Soon!
								if(u.isDisplayed(xmlReporteDirectosMsg)) {
									u.highLight(xmlReporteDirectosMsg);
									testCase.addScreenShotCurrentStep(u, "Se muestra el mensaje de Coming Soon!");
									u.unhighLight(xmlReporteDirectosMsg);
									
									result = true;
								}
							} 

						} catch (Exception e) {
							
							e.printStackTrace();
							return false;
						}

						esperarCarga(3);
						return result;
					}
					/**
		 			* Metodo para regresar a la pantalla de inicio externo
		 			* @author Isaac E. Garcia
		 			* @return true si la selecciÃ³n se pudo elegir
					* @date Mayo 2025
					*/
					public boolean regresarMenuInicial() {
						boolean result=false;
						
						try {
							
							if(u.isDisplayed(xmlOpcionInicioExterno)){
								u.highLight(xmlOpcionInicioExterno);
								testCase.addScreenShotCurrentStep(u, "Se da clic en la opcion de Inicio Externo");
								u.unhighLight(xmlOpcionInicioExterno);
								u.click(xmlOpcionInicioExterno);
								
								if(u.waitWebElement(xmlCedisOption, 30, 2).isDisplayed()) {
									testCase.addScreenShotCurrentStep(u, "Se muestra la pantalla inicial de COMETA");									
									result = true;
								}
							} 

						} catch (Exception e) {
							
							e.printStackTrace();
							return false;
						}

						
						return result;
					}
					
					

					/**
		 			* Metodo para contar Canastillas Devueltas
		 			* @author Isaac E. Garcia
		 			* @return true si la selecciÃ³n se pudo elegir
					* @date Mayo 2025
					*/
					public int contarCanastillasDevueltas() {
						int result = 0;

						try {
							// Verificar si el elemento estÃ¡ visible
							for(int i=1; i<20; i++) {
								 String xpathCanastillaDevuelta = " (//tr["+i+"]//td)[5]";
								 int CanastillaDevuelta = Integer.parseInt(u.getText(By.xpath(xpathCanastillaDevuelta)));
								 System.out.println("Valor de Canastilla Devuelta --> "+CanastillaDevuelta);
								 
								 if(CanastillaDevuelta>0) {
									 u.scrollToElement(By.xpath(xpathCanastillaDevuelta));
									 u.highLight(By.xpath(xpathCanastillaDevuelta));
									 testCase.addScreenShotCurrentStep(u, "Se muestra un registro con varias canastillas devueltas");	
									 u.unhighLight(By.xpath(xpathCanastillaDevuelta));
									 System.out.println("Valor de Canastilla Devuelta FINAL--> "+CanastillaDevuelta);
									 result=CanastillaDevuelta;
									 return result;
								 }
							}
						} catch (Exception e) {
							result=0;
							e.printStackTrace();
						}
						
						
						
						return result;
					}
					
					/**
		 			* Metodo para seleccionar el registro con canastillas devueltas
		 			* @author Isaac E. Garcia
		 			* @return true si la selecciÃ³n se pudo elegir
					* @date Mayo 2025
					*/
					public boolean seleccionarRegistro(int canastillasDevueltas) {
						boolean result=false;
						
						try {
							for(int i=1; i<20; i++) {
								 String xpathCanastillaDevuelta = " (//tr["+i+"]//td)[5]";
								 int CanastillaDevuelta = Integer.parseInt(u.getText(By.xpath(xpathCanastillaDevuelta)));
								 
								 if(CanastillaDevuelta>0) {
									 	String xpathBotonVer = "(//tr/td[contains(@class, 'mat-column-FUE')]//button[.//span[text()=' Ver ']])["+i+"]";
				    	               
				    	               WebElement botonVer = u.getDriver().findElement(By.xpath(xpathBotonVer));
				    	               u.highLight(By.xpath(xpathBotonVer));
				    	               u.scrollToElement(By.xpath(xpathBotonVer));
				    	               testCase.addScreenShotCurrentStep(u, "Se da clic en boton Ver FUE");
				    	               u.unhighLight(By.xpath(xpathBotonVer));
				    	               
				    	               botonVer.click();
				    	               result=true;
				    	               esperarCarga(3);
				    	               break;
								 }
							}
						} catch (Exception e) {
							result=false;
							e.printStackTrace();
						}
						
						
						return result;
					}
					
					
					
					
					

					/**
		 			* Metodo para verificar si el formato de FUE V1 es correcto
		 			* @author Isaac E. Garcia
		 			* @return true si la selecciÃ³n se pudo elegir
					* @date Mayo 2025
					*/
					public boolean validarFormatoFUE_V1() {
						boolean result=false;

						try {
							// Verificar si el elemento estÃ¡ visible
							if(u.isDisplayed(xmlRutaOriginalFUE)) {
								u.highLight(xmlRutaOriginalFUE);
								testCase.addScreenShotCurrentStep("ValidaciÃ³n correcta: el formato FUE V1 es correcto");
								u.unhighLight(xmlRutaOriginalFUE);
								result = true;									
							}
							 else {
								result = false;
							}
						} catch (Exception e) {
							result=false;
							e.printStackTrace();
						}

						return result;
					}

					/**
		 			* Metodo para verificar el nombre de columna Nombre Tienda en el Grid
		 			* @author Isaac E. Garcia
		 			* @return true si el nombre es correcto
					* @date Mayo 2025
					*/
					public boolean validarColumnaNombreTienda() {
						boolean result=false;

						try {
							// Verificar si el elemento estÃ¡ visible
							if(u.isDisplayed(xmlNombreTienda)) {
								u.scrollToElement(xmlNombreTienda);
								u.highLight(xmlNombreTienda);
								testCase.addScreenShotCurrentStep("ValidaciÃ³n correcta: El elemento 'Nombre de Tienda' es correcto.");
								u.unhighLight(xmlNombreTienda);
								result = true;									
							}
							 else {
								result = false;
							}
						} catch (Exception e) {
							result=false;
							e.printStackTrace();
						} 

						return result;
					}

					/**
		 			* Metodo para calcular el numero de canastillas devueltas
		 			* @author Isaac E. Garcia
		 			* @return true si el nombre es correcto
					* @date Mayo 2025
					*/
					public boolean calcularCanastillasDevueltas(int canastillaDevuelta) {
						boolean result = false;
						int devolucionMercancia=0, devolucionCanastilla=0;
						
						try {
							// Verificar si el elemento estÃ¡ visible
							if(u.isDisplayed(xmlDevolucionMercancia)) {
								u.scrollToElement(xmlDevolucionMercancia);
								u.highLight(xmlDevolucionMercancia);
								devolucionMercancia= Integer.parseInt(u.getText(xmlDevolucionMercancia));
								testCase.addScreenShotCurrentStep("Validacion correcta: El elemento 'Devolucion de mercancia' es correcto y tiene valor: "+devolucionMercancia);
								u.unhighLight(xmlDevolucionMercancia);
								result = true;									
							}
							// Verificar si el elemento estÃ¡ visible
							if(u.isDisplayed(xmlDevolucionCanastilla)) {
								u.scrollToElement(xmlDevolucionCanastilla);
								u.highLight(xmlDevolucionCanastilla);
								devolucionCanastilla= Integer.parseInt(u.getText(xmlDevolucionCanastilla));
								testCase.addScreenShotCurrentStep("Validacion correcta: El elemento 'Devolucion de canastilla' es correcto y tiene valor: "+devolucionCanastilla);
								u.unhighLight(xmlDevolucionCanastilla);
								result = true;									
							}
							
							if(result && (devolucionCanastilla+devolucionMercancia)==canastillaDevuelta)
								testCase.addScreenShotCurrentStep("El valor de canastillas devueltas es de "+canastillaDevuelta+" y es igual a sumar Devolucion de canastillas("+devolucionCanastilla+") + Devolucion de mercancia("+devolucionMercancia+")");
							else {								
								System.out.println("El valor de canastillas devueltas es de "+canastillaDevuelta+" y es igual a sumar Devolucion de canastillas("+devolucionCanastilla+") + Devolucion de mercancia("+devolucionMercancia+")");
								result = false;
							}
							
						} catch (Exception e) {
							e.printStackTrace();
							return false;
						}
						
						return result;
					}
					

					/**
		 			* Metodo para esperar un determinado numero de segundos a que cargue un elemento
		 			* @author Isaac E. Garcia
		 			* @param seg	->	Segundos que espera
		 			* @return true si el nombre es correcto
					* @date Mayo 2025
					*/
					public void esperarCarga(int seg) {
						u.hardWait(seg);
					}

					
					
				
}