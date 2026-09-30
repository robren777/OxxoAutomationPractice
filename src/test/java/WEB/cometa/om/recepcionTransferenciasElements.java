package WEB.cometa.om;

import org.openqa.selenium.By;

public class recepcionTransferenciasElements {

	public By tituloPantalla = By.xpath("//h3[normalize-space()='Recepción de Transferencias y Devoluciones' or normalize-space()='Recepcion de Transferencias y Devoluciones']");
	public By botonLiquidacionRuta = By.xpath("//button[.//*[contains(normalize-space(),'Liquidación de Ruta') or contains(normalize-space(),'Liquidacion de Ruta')]]");
	public By botonDevolucionesEspeciales = By.xpath("//button[.//*[contains(normalize-space(),'Devoluciones Especiales')]]");
	public By flechaRegresarHeaderCometa = By.xpath("(//app-root/app-header//nav//app-button-back | //app-header//nav//app-button-back | //app-button-back)[1]");
	public By flechaRegresarHeaderCometaBoton = By.xpath("(//app-root/app-header//nav//app-button-back//button | //app-header//nav//app-button-back//button | //app-button-back//button)[1]");
	public By flechaRegresarLiquidacionRuta = By.xpath("(//app-cedis-liquidacion-ruta//button[.//mat-icon[normalize-space()='arrow_back']] | //app-cedis-liquidacion-ruta//mat-icon[normalize-space()='arrow_back']/ancestor::button[1] | //button[.//mat-icon[normalize-space()='arrow_back'] and not(ancestor::div[contains(@class,'cdk-overlay-pane')])])[1]");
	public By tituloFiltrosHistoricos = By.xpath("//h4[normalize-space()='Filtros de Búsqueda Histórica' or normalize-space()='Filtros de Busqueda Historica']");
	public By seccionFiltrosHistoricos = By.xpath("//app-filtros-historicos | //*[self::form or self::div][.//h4[normalize-space()='Filtros de Búsqueda Histórica' or normalize-space()='Filtros de Busqueda Historica']]");
	public By filtroPlaza = By.xpath("//app-filtros-historicos//ng-select[contains(translate(@bindlabel,'PLAZA','plaza'),'plaza')]");
	public By primeraOpcionPlaza = By.xpath("(//div[contains(@class,'ng-option') and @role='option'])[1]");
	public By filtroTienda = By.xpath("//app-filtros-historicos//ng-select[contains(translate(@bindlabel,'TIENDA','tienda'),'tienda')]");
	public By selectorRuta = By.xpath("(//app-filtros-historicos//mat-select[.//span[contains(normalize-space(),'Selector de Ruta')]] | //app-filtros-historicos//mat-form-field[.//mat-label[contains(normalize-space(),'Selector de Ruta')]]//mat-select)[1]");
	public By filtroRuta = By.xpath("//app-filtros-historicos//mat-label[contains(normalize-space(),'Ruta') and not(contains(normalize-space(),'Selector'))]");
	public By campoRuta = By.xpath("(//app-filtros-historicos//mat-form-field[.//mat-label[contains(normalize-space(),'Ruta') and not(contains(normalize-space(),'Selector'))] or .//input[contains(@placeholder,'Ruta')]])[1]");
	public By fechaInicio = By.xpath("//app-filtros-historicos//mat-date-range-input//input[@matstartdate or contains(@class,'mat-start-date')]");
	public By fechaFin = By.xpath("//app-filtros-historicos//mat-date-range-input//input[@matenddate or contains(@class,'mat-end-date')]");
	public By campoRangoFechas = By.xpath("(//app-filtros-historicos//mat-form-field[.//mat-date-range-input or .//mat-label[contains(normalize-space(),'Rango de Fechas')]])[1]");
	public By botonCalendarioHistorico = By.xpath("(//app-filtros-historicos//mat-datepicker-toggle//button)[1]");
	public By calendarioHistorico = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-datepicker-content");
	public By botonConsultarHistorico = By.xpath("//app-filtros-historicos//button[.//span[normalize-space()='Consultar'] and not(@disabled)]");
	public By botonConsultarHistoricoDeshabilitado = By.xpath("(//app-filtros-historicos//button[(@disabled or @aria-disabled='true') and .//span[contains(@class,'mdc-button__label') and normalize-space()='Consultar']])[1]");
	public By botonLimpiarHistorico = By.xpath("//app-filtros-historicos//button[.//span[contains(normalize-space(),'Limpiar')]]");
	public By tituloFiltrosRapidos = By.xpath("//h4[normalize-space()='Filtros de Búsqueda Rápida' or normalize-space()='Filtros de Busqueda Rapida']");
	public By seccionFiltrosRapidos = By.xpath("//app-filtros-rapidos | //*[self::form or self::div][.//h4[normalize-space()='Filtros de Búsqueda Rápida' or normalize-space()='Filtros de Busqueda Rapida']]");
	public By campoCanastilla = By.xpath("(//app-filtros-rapidos//mat-form-field[.//mat-label[normalize-space()='Escanea o teclea la canastilla'] or .//input[@placeholder='Escanea o teclea la canastilla']] | //app-filtros-rapidos//mat-form-field[.//mat-label[normalize-space()='Escanea o teclea una canastilla']])[1]");
	public By inputCanastilla = By.xpath("(//app-filtros-rapidos//input[@placeholder='Escanea o teclea la canastilla' or ancestor::mat-form-field[.//mat-label[normalize-space()='Escanea o teclea la canastilla']] or ancestor::mat-form-field[.//mat-label[normalize-space()='Escanea o teclea una canastilla']]])[1]");
	public By etiquetaCanastilla = By.xpath("(//app-filtros-rapidos//mat-label[normalize-space()='Escanea o teclea la canastilla'] | //app-filtros-rapidos//input[@placeholder='Escanea o teclea la canastilla'] | //app-filtros-rapidos//mat-label[normalize-space()='Escanea o teclea una canastilla'])[1]");
	public By campoTransferencia = By.xpath("(//app-filtros-rapidos//mat-form-field[.//mat-label[normalize-space()='Transferencia']] | //mat-form-field[.//mat-label[normalize-space()='Transferencia'] and ancestor::*[self::form or self::div][.//h4[contains(normalize-space(),'Filtros de Busqueda Rapida') or contains(normalize-space(),'Filtros de Búsqueda Rápida')]]])[1]");
	public By inputTransferencia = By.xpath("(//app-filtros-rapidos//input[ancestor::mat-form-field[.//mat-label[normalize-space()='Transferencia']] or @formcontrolname='transferencia' or @placeholder='Transferencia'] | //input[ancestor::*[self::form or self::div][.//h4[contains(normalize-space(),'Filtros de Busqueda Rapida') or contains(normalize-space(),'Filtros de Búsqueda Rápida')]] and (ancestor::mat-form-field[.//mat-label[normalize-space()='Transferencia']] or @formcontrolname='transferencia' or @placeholder='Transferencia')])[1]");
	public By etiquetaTransferencia = By.xpath("(//app-filtros-rapidos//mat-label[normalize-space()='Transferencia'] | //mat-label[normalize-space()='Transferencia' and ancestor::*[self::form or self::div][.//h4[contains(normalize-space(),'Filtros de Busqueda Rapida') or contains(normalize-space(),'Filtros de Búsqueda Rápida')]]])[1]");
	public By botonConsultarRapido = By.xpath("(//app-filtros-rapidos//div[contains(concat(' ', normalize-space(@class), ' '), ' cedis-filters-form-02 ')]//div[contains(concat(' ', normalize-space(@class), ' '), ' action-buttons ')]//button[.//span[contains(@class,'mdc-button__label') and normalize-space()='Consultar'] and not(@disabled)])[1]");
	public By botonConsultarRapidoDeshabilitado = By.xpath("(//app-filtros-rapidos//div[contains(concat(' ', normalize-space(@class), ' '), ' cedis-filters-form-02 ')]//div[contains(concat(' ', normalize-space(@class), ' '), ' action-buttons ')]//button[(@disabled or @aria-disabled='true' or contains(@class,'mat-mdc-button-disabled')) and .//span[contains(@class,'mdc-button__label') and normalize-space()='Consultar']])[1]");
	public By botonConsultarRapidoCualquierEstado = By.xpath("(//app-filtros-rapidos//div[contains(concat(' ', normalize-space(@class), ' '), ' cedis-filters-form-02 ')]//div[contains(concat(' ', normalize-space(@class), ' '), ' action-buttons ')]//button[.//span[contains(@class,'mdc-button__label') and normalize-space()='Consultar']])[1]");
	public By botonLimpiarRapido = By.xpath("(//app-filtros-rapidos//div[contains(concat(' ', normalize-space(@class), ' '), ' cedis-filters-form-02 ')]//div[contains(concat(' ', normalize-space(@class), ' '), ' action-buttons ')]//button[.//span[contains(@class,'mdc-button__label') and normalize-space()='Limpiar Filtros']] | //div[contains(concat(' ', normalize-space(@class), ' '), ' cedis-filters-form-02 ')]//div[contains(concat(' ', normalize-space(@class), ' '), ' action-buttons ')]//button[.//span[contains(@class,'mdc-button__label') and normalize-space()='Limpiar Filtros']])[1]");
	public By botonConsultarHistoricoCualquierEstado = By.xpath("//app-filtros-historicos//button[.//span[normalize-space()='Consultar']]");
	public By mensajeSinResultados = By.xpath("//*[normalize-space()='No se encontró información con el filtro seleccionado.' or normalize-space()='No se encontro informacion con el filtro seleccionado.']");
	public By tooltipBloqueo = By.xpath("//*[contains(@class,'tooltip') or @role='tooltip' or self::mat-tooltip-component]//*[contains(normalize-space(),'Para usar los filtros')] | //*[@role='tooltip' and contains(normalize-space(),'Para usar los filtros')]");
	public By erroresTransferencia = By.xpath("//mat-form-field[.//mat-label[normalize-space()='Transferencia']]//mat-error | //app-filtros-rapidos//*[contains(@class,'error') and contains(normalize-space(),'Transferencia')]");
	public By indicadorCarga = By.xpath("//*[contains(normalize-space(),'Consultando') or contains(normalize-space(),'Espere un momento')][not(self::script)] | //mat-spinner | //*[contains(@class,'spinner') or contains(@class,'loading-overlay')]");
	public By celdasTransferenciaGrid = By.xpath("//table//tbody/tr/td[count(//table//th[normalize-space()='Transferencia']/preceding-sibling::th)+1]");
	public By celdasFechaGrid = By.xpath("//table//tbody/tr/td[count(//table//th[normalize-space()='Fecha']/preceding-sibling::th)+1]");
	public By primeraFilaGrid = By.xpath("(//table//tbody/tr)[1]");
	public By primerBotonDetalleGrid = By.xpath("(//table//tbody/tr[1]//button[not(@disabled)] | //table//tbody/tr[1]//a)[1]");
	public By areaGrid = By.xpath("//table | //*[contains(@class,'grid') and (.//table or .//tbody)]");
	public By contadorGrid = By.xpath("//mat-paginator//*[contains(@class,'range-label')] | //*[contains(@class,'paginator') and contains(normalize-space(), 'de')]");
	public By paginadorGrid = By.xpath("(//table/following::mat-paginator[1] | //mat-paginator[preceding::table])[1]");
	public By botonPrimeraPaginaGrid = By.xpath("(//mat-paginator//button[contains(@class,'mat-mdc-paginator-navigation-first') or @aria-label='Primera página' or @aria-label='First page'])[1]");
	public By botonAnteriorPaginaGrid = By.xpath("(//mat-paginator//button[contains(@class,'mat-mdc-paginator-navigation-previous') or @aria-label='Página anterior' or @aria-label='Previous page'])[1]");
	public By botonSiguientePaginaGrid = By.xpath("(//mat-paginator//button[contains(@class,'mat-mdc-paginator-navigation-next') or @aria-label='Página siguiente' or @aria-label='Next page'])[1]");
	public By seccionDescargaMasiva = By.xpath("//app-descargas-masivas");
	public By textoDescargaMasiva = By.xpath("//app-descargas-masivas//*[contains(normalize-space(),'Descarga MASIVA de comprobantes filtrados') or contains(normalize-space(),'Descargar de manera masiva los comprobantes filtrados') or contains(normalize-space(),'Descargar de manera masiva los comprobantes filtra')]");
	public By textoDescargaMasivaFlexible = By.xpath("//*[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚ', 'abcdefghijklmnopqrstuvwxyzaeiou'), 'descarga masiva') and (contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚ', 'abcdefghijklmnopqrstuvwxyzaeiou'), 'comprobantes') or contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚ', 'abcdefghijklmnopqrstuvwxyzaeiou'), 'descargar de manera masiva'))]");
	public By iconosPdfDescargaMasiva = By.xpath("//app-descargas-masivas//*[not(ancestor::*[@role='tooltip' or contains(@class,'tooltip')]) and (self::button or self::a or @role='button' or contains(@class,'btn-pdf') or contains(@class,'icon-pdf') or contains(@class,'pdf'))]");
	public By descargaMasivaFue = By.xpath("(//app-descargas-masivas//*[not(ancestor::*[@role='tooltip' or contains(@class,'tooltip')]) and (self::button or self::a or @role='button' or contains(@class,'btn-pdf') or contains(@class,'icon-pdf') or contains(@class,'pdf'))])[1]");
	public By descargaMasivaVerificacion = By.xpath("(//app-descargas-masivas//*[not(ancestor::*[@role='tooltip' or contains(@class,'tooltip')]) and (self::button or self::a or @role='button' or contains(@class,'btn-pdf') or contains(@class,'icon-pdf') or contains(@class,'pdf'))])[2]");
	public By descargaMasivaTira = By.xpath("(//app-descargas-masivas//*[not(ancestor::*[@role='tooltip' or contains(@class,'tooltip')]) and (self::button or self::a or @role='button' or contains(@class,'btn-pdf') or contains(@class,'icon-pdf') or contains(@class,'pdf'))])[3]");
	public By primerFue = By.xpath("(//table//tbody/tr[1]//td[contains(@class,'FUE') or contains(@class,'fue')]//button | //table//tbody/tr[1]//button[contains(@class,'fue') or contains(@aria-label,'FUE')])[1]");
	public By modalFue = By.xpath("//mat-dialog-container//*[self::app-modal-fue or @id='fue' or @id='formato-de-devolucion']");
	public By contenidoCanastillaFue = By.xpath("//*[@id='formato-de-devolucion'] | //mat-dialog-container//*[contains(normalize-space(),'Canastilla')]");
	public By cerrarModalFue = By.xpath("//mat-dialog-container//button[contains(@class,'close') or @aria-label='Cerrar' or .//mat-icon[normalize-space()='close']]");
	public By gridFilas = By.xpath("//table//tbody/tr");
	public By tituloDevolucionesEspeciales = By.xpath("//h3[contains(normalize-space(),'Devoluciones Especiales')]");
	public By filtroCedisDevolucionesEspeciales = By.xpath("(//app-cedis-devoluciones-especiales//app-filtros-busqueda//ng-select[.//*[normalize-space()='CEDIS']] | (//app-cedis-devoluciones-especiales//app-filtros-busqueda//ng-select)[1])");
	public By columnaFecha = columna("Fecha");
	public By columnaCrTienda = columna("CR Tienda");
	public By columnaNombreTienda = columna("Nombre de Tienda");
	public By columnaTransferencia = columna("Transferencia");
	public By columnaCanastillas = columna("# Canastillas Devueltas");
	public By columnaBols = By.xpath("//th[normalize-space()='Bols en Entrega' or normalize-space()='Bols en entrega']");
	public By columnaFue = columna("FUE");
	public By columnaVerificacion = By.xpath("//th[normalize-space()='Formato de Verificación' or normalize-space()='Formato de Verificacion']");
	public By columnaEvidencias = By.xpath("//th[normalize-space()='Evidencias Fotográficas' or normalize-space()='Evidencias Fotograficas']");
	public By columnaTira = By.xpath("//th[normalize-space()='Tira de Transferencia' or normalize-space()='Tira de transferencia']");

	private By columna(String titulo) {
		return By.xpath("//th[normalize-space()='" + titulo + "']");
	}

	public By tarjetaRecepcionYDevolucion = By.xpath("//a[@href='/PortalCometa/cedis/recepcion-de-transferencias-y-devoluciones'] | //a[.//span[normalize-space()='Recepcion y Devolucion' or normalize-space()='Recepción y Devolución'] or normalize-space()='Recepcion y Devolucion' or normalize-space()='Recepción y Devolución'] | //button[.//span[normalize-space()='Recepcion y Devolucion' or normalize-space()='Recepción y Devolución'] or normalize-space()='Recepcion y Devolucion' or normalize-space()='Recepción y Devolución']");
	public By overlayEspereUnMomento = By.xpath("//*[contains(normalize-space(.),'Espere un momento')]");
	public By mensajeNoItemsFound = By.xpath("//*[contains(normalize-space(.),'No items found')]");
	public By selectorRutaControl = By.xpath("//app-filtros-historicos//mat-select[ancestor::mat-form-field[.//mat-label[contains(normalize-space(),'Selector de Ruta')]]]");
	public By opcionSelectorRutaPMO = By.xpath("//mat-option//span[normalize-space()='PMO'] | //span[contains(@class,'mdc-list-item__primary-text') and normalize-space()='PMO']");
	public By opcionSelectorRutaDinamica = By.xpath("//mat-option//span[normalize-space()='Dinamica' or normalize-space()='Dinámica'] | //span[contains(@class,'mdc-list-item__primary-text') and (normalize-space()='Dinamica' or normalize-space()='Dinámica')]");
	public By inputRuta = By.xpath("//app-filtros-historicos//input[ancestor::mat-form-field[.//mat-label[contains(normalize-space(),'Ruta') and not(contains(normalize-space(),'Selector'))]]]");
	public By opcionNgSelectVisible = By.xpath("//div[contains(@class,'ng-dropdown-panel')]//div[contains(@class,'ng-option') and @role='option' and not(contains(@class,'disabled'))]");
	public By modalEspereUnMomentoRecepcion = By.xpath("//mat-dialog-container[.//app-dialog-message and .//p[contains(normalize-space(),'Espere un momento')]] | //app-dialog-message[.//p[contains(normalize-space(),'Espere un momento')]]");
	public By filasGridEntregaRecepcion = By.xpath("//table//tbody/tr[not(contains(@class,'mat-mdc-no-data-row')) and not(contains(normalize-space(.),'No hay'))]");
}