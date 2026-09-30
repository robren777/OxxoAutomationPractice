package WEB.cometa.om;

import org.openqa.selenium.By;

public class devolucionesEspecialesElements {

	public By btnDevolucionesEspeciales = By.xpath("(//app-cedis-recepcion-transferencias-y-devoluciones//button[.//*[normalize-space()='Devoluciones Especiales']] | //app-cedis-liquidacion-ruta//button[.//*[normalize-space()='Devoluciones Especiales']] | //button[.//*[normalize-space()='Devoluciones Especiales']] | //span[normalize-space()='Devoluciones Especiales']/ancestor::button[1])[1]");
	public By overlayCargaDevEspeciales = By.xpath("//*[contains(normalize-space(),'Consultando') or contains(normalize-space(),'Espere un momento') or contains(@class,'loading-border')]");
	public By tituloDevolucionesEspeciales = By.xpath("//app-cedis-devoluciones-especiales//*[self::h1 or self::h2 or self::h3 or self::h4 or self::span][normalize-space()='Devoluciones Especiales']");
	public By dropCedisDevEspeciales = By.xpath("(//app-cedis-devoluciones-especiales//app-filtros-busqueda//ng-select[.//*[normalize-space()='CEDIS']] | (//app-cedis-devoluciones-especiales//app-filtros-busqueda//ng-select)[1])");
	public By dropPlazaDevEspeciales = By.xpath("(//app-cedis-devoluciones-especiales//app-filtros-busqueda//ng-select[.//*[normalize-space()='Plaza']] | (//app-cedis-devoluciones-especiales//app-filtros-busqueda//ng-select)[2])");
	public By dropTiendaDevEspeciales = By.xpath("(//app-cedis-devoluciones-especiales//app-filtros-busqueda//ng-select[.//*[normalize-space()='Tienda']] | (//app-cedis-devoluciones-especiales//app-filtros-busqueda//ng-select)[3])");
	public By dropBolDevEspeciales = By.xpath("(//app-cedis-devoluciones-especiales//app-filtros-busqueda//ng-select[.//*[normalize-space()='BOL']] | (//app-cedis-devoluciones-especiales//app-filtros-busqueda//ng-select)[4])");
	public By rangoFechaDevEspeciales = By.xpath("(//app-cedis-devoluciones-especiales//app-filtros-busqueda//mat-form-field[.//mat-date-range-input or .//mat-datepicker-toggle or .//input] | (//app-cedis-devoluciones-especiales//app-filtros-busqueda//mat-form-field)[1])");
	public By btnCalendarioDevEspeciales = By.xpath("//app-cedis-devoluciones-especiales//app-filtros-busqueda//mat-form-field[.//mat-date-range-input or .//mat-datepicker-toggle or .//input]//mat-datepicker-toggle//button | //app-cedis-devoluciones-especiales//app-filtros-busqueda//mat-form-field[.//mat-date-range-input or .//mat-datepicker-toggle or .//input]//button[contains(@aria-label,'calendar') or contains(@aria-label,'calendario')]");
	public By calendarioOverlayDevEspeciales = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-datepicker-content | //div[contains(@class,'cdk-overlay-pane')]//mat-calendar");
	public By encabezadoCalendarioDevEspeciales = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-period-button')]");
	public By btnMesAnteriorCalendarioDevEspeciales = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-previous-button')]");
	public By btnMesSiguienteCalendarioDevEspeciales = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-next-button')]");
	public By encabezadosDiasSemanaCalendarioDevEspeciales = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//thead//th[contains(@class,'mat-calendar-table-header') or contains(@class,'mat-calendar-table-header-divider')]/span[not(contains(@class,'cdk-visually-hidden'))] | //div[contains(@class,'cdk-overlay-pane')]//thead//th/span[contains(@class,'mat-calendar-table-header')]");
	public By diaActualCalendarioDevEspeciales = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//*[contains(@class,'mat-calendar-body-today')] | //div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-body-cell')][.//*[contains(@class,'mat-calendar-body-today')]]");
	public By diasDisponiblesCalendarioDevEspeciales = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-body-cell') and not(contains(@class,'mat-calendar-body-disabled'))]");
	public By btnRecepcionTransferenciasDevolucionesEspeciales = By.xpath("(//app-cedis-devoluciones-especiales//button[.//span[normalize-space()='Recepci\u00f3n de Transferencias y Devoluciones']] | //app-cedis-devoluciones-especiales//span[normalize-space()='Recepci\u00f3n de Transferencias y Devoluciones'])[1]");
	public By btnLimpiarFiltrosDevEspeciales = By.xpath("/html/body/app-root/app-cedis-devoluciones-especiales/div/app-filtros-busqueda/form/div[3]/div[2]/button[1]");
	public By btnConsultarDevEspeciales = By.xpath("//app-cedis-devoluciones-especiales//button[.//span[normalize-space()='Consultar'] or normalize-space()='Consultar']");
	public By areaGridDevEspeciales = By.xpath("(//app-cedis-devoluciones-especiales//table)[1]");
	public By filasGridDevEspeciales = By.xpath("//app-cedis-devoluciones-especiales//table//tbody/tr[not(contains(@class,'mat-mdc-no-data-row')) and not(contains(normalize-space(.),'No hay'))]");
	public By botonVistaPreliminarPrimerRegistroDevEspeciales = By.xpath("(//app-cedis-devoluciones-especiales//table//tbody/tr[not(contains(@class,'mat-mdc-no-data-row'))]//td[contains(@class,'mat-column-devolucionEspecial') or contains(@class,'cdk-column-devolucionEspecial') or contains(@class,'mat-column-devolucion') or contains(@class,'cdk-column-devolucion')]//button)[1]");
	public By contenedorVistaPreliminarTicketDevEspeciales = By.xpath("//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container or contains(@class,'cdk-dialog-container')][.//button[contains(@class,'btn-close') or contains(@class,'close-icon')] or .//*[contains(normalize-space(.),'Descarga Comprobante en Formato')]]");
	public By leyendaDescargaComprobanteTicketDevEspeciales = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container or contains(@class,'cdk-dialog-container')]//span[contains(normalize-space(.),'Descarga Comprobante en Formato')])[1]");
	public By botonPdfTicketDevEspeciales = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container or contains(@class,'cdk-dialog-container')]//button[contains(@class,'download-pdf') or contains(@aria-label,'PDF') or contains(@title,'PDF')])[1]");
	public By botonCerrarVistaPreliminarTicketDevEspeciales = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container or contains(@class,'cdk-dialog-container')]//button[contains(@class,'btn-close') or contains(@class,'close-icon') or contains(@aria-label,'Cerrar')])[1]");
	public By rangoActualTicketDevEspeciales = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container or contains(@class,'cdk-dialog-container')]//*[contains(@class,'mat-mdc-paginator-range-label') or contains(@class,'mat-paginator-range-label')])[1]");
	public By botonPrimeraPaginaTicketDevEspeciales = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container or contains(@class,'cdk-dialog-container')]//button[@aria-label='Primera página' or @aria-label='Primera pagina'])[1]");
	public By botonPaginaAnteriorTicketDevEspeciales = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container or contains(@class,'cdk-dialog-container')]//button[@aria-label='Página anterior' or @aria-label='Pagina anterior'])[1]");
	public By botonPaginaSiguienteTicketDevEspeciales = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container or contains(@class,'cdk-dialog-container')]//button[@aria-label='Página siguiente' or @aria-label='Pagina siguiente'])[1]");
	public By botonUltimaPaginaTicketDevEspeciales = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container or contains(@class,'cdk-dialog-container')]//button[@aria-label='Última página' or @aria-label='Ultima pagina'])[1]");
	public By tooltipNavegacionTicketDevEspeciales = By.xpath("//*[contains(@class,'mdc-tooltip__surface') and string-length(normalize-space(.)) > 0]");
	public By celdasFechaGridDevEspeciales = By.xpath("//app-cedis-devoluciones-especiales//table//tbody/tr/td[contains(@class,'mat-column-fecha') or contains(@class,'cdk-column-fecha')] | //app-cedis-devoluciones-especiales//table//tbody/tr/td[count(//app-cedis-devoluciones-especiales//table//th[contains(normalize-space(),'Fecha')]/preceding-sibling::th)+1]");
	public By seccionDescargaMasivaDevEspeciales = By.xpath("//app-cedis-devoluciones-especiales//app-descargas-masivas");
	public By leyendaDescargaMasivaDevEspeciales = By.xpath("//app-cedis-devoluciones-especiales//app-descargas-masivas//*[contains(normalize-space(),'Descarga MASIVA de comprobantes filtrados') or contains(normalize-space(),'Descargar de manera masiva los comprobantes filtrados') or contains(normalize-space(),'Descargar de manera masiva los comprobantes filtra')]");
	public By botonPdfMasivoDescargasDevEspeciales = By.cssSelector("app-cedis-devoluciones-especiales app-descargas-masivas button[data-testid='btn-pdf-masivo-descargas']");
	public By modalEspereUnMomentoDevEspeciales = By.xpath("//*[contains(@class,'cdk-overlay-pane') or contains(@class,'cdk-overlay-container') or self::mat-dialog-container or self::app-dialog-message][contains(normalize-space(.),'Espere un momento')]");
	public By popupCentroDescargaDevEspeciales = By.xpath("//app-info-download-dialog[contains(normalize-space(),'centro de descarga') or contains(normalize-space(),'centro de Descarga') or contains(normalize-space(),'visualizar')]");
	public By btnCerrarPopupCentroDescargaDevEspeciales = By.xpath("//app-info-download-dialog//button[@aria-label='Cerrar' or @aria-label='Close' or contains(@class,'close')]");
	public By labelPaginadorDevEspeciales = By.xpath("//app-cedis-devoluciones-especiales//mat-paginator//*[contains(@class,'mat-mdc-paginator-range-label') or contains(@class,'mat-paginator-range-label')]");
	public By panelOpcionesDevEspeciales = By.xpath("//ng-dropdown-panel[contains(@class,'ng-dropdown-panel')]");
	public By opcionesDevEspeciales = By.xpath("//ng-dropdown-panel[contains(@class,'ng-dropdown-panel')]//div[contains(@class,'ng-option') and not(contains(@class,'ng-option-disabled'))]");
	public By primeraOpcionDevEspeciales = By.xpath("(//ng-dropdown-panel[contains(@class,'ng-dropdown-panel')]//div[contains(@class,'ng-option') and not(contains(@class,'ng-option-disabled'))])[1]");
	public By opcionesCedisDevEspeciales = By.xpath("//ng-dropdown-panel[contains(@class,'ng-dropdown-panel')]//div[contains(@class,'ng-option') and not(contains(@class,'ng-option-disabled'))]");
	public By primeraOpcionCedisDevEspeciales = By.xpath("(//ng-dropdown-panel[contains(@class,'ng-dropdown-panel')]//div[contains(@class,'ng-option') and not(contains(@class,'ng-option-disabled'))])[1]");
	public By selectorRegistrosPorPaginaDevEspeciales = By.xpath("//app-cedis-devoluciones-especiales//mat-paginator//mat-select");
	public By opcionRegistrosPorPaginaDevEspeciales = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option[not(contains(@class,'mat-option-disabled'))]");
	public By botonPrimeraPaginaGridDevEspeciales = By.xpath("(//app-grid-devoluciones-especiales//mat-paginator//button)[1]");
	public By botonPaginaAnteriorGridDevEspeciales = By.xpath("(//app-grid-devoluciones-especiales//mat-paginator//button)[2]");
	public By botonPaginaSiguienteGridDevEspeciales = By.xpath("(//app-grid-devoluciones-especiales//mat-paginator//button)[3]");
	public By botonUltimaPaginaGridDevEspeciales = By.xpath("(//app-grid-devoluciones-especiales//mat-paginator//button)[4]");
	public By encabezadosGridDevEspeciales = By.xpath("//app-cedis-devoluciones-especiales//table//th[contains(@class,'mat-header-cell') or contains(@class,'mat-mdc-header-cell')]");

}
