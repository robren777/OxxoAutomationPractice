package WEB.cometa.om;

import org.openqa.selenium.By;

public class rutaLiquidacionElements {

	public By tituloLiquidacionRuta = By.xpath("//app-cedis-liquidacion-ruta//*[self::h1 or self::h2 or self::h3 or self::span][normalize-space()='Liquidación de Ruta']");
	public By modalEspereUnMomentoLiquidacionRuta = By.xpath("/html/body/div[2]/div/div[2]/mat-dialog-container/div/div/app-dialog-message/main/div[2]/div[2]");
	public By mensajeEspereUnMomentoLiquidacionRuta = By.xpath("//*[@id='mat-mdc-dialog-0']/div/div/app-dialog-message/main/div[2]/div[2]/p");
	public By containerDetalle = By.id("detalle-ruta");//Primera linea de detalle
	public By containerTienda = By.id("detalle-tiendas");
	public By leyendaEntregasHandHeld = By.xpath("//app-cedis-liquidacion-ruta//div[contains(@class,'header-legends')]/p[1][contains(normalize-space(.),'Esta pantalla muestra únicamente las entregas registradas desde la Hand Held')]");
	public By leyendaRutaDinamicaUnigis = By.xpath("//app-cedis-liquidacion-ruta//div[contains(@class,'header-legends')]/p[2][contains(normalize-space(.),'Visualiza la información por Ruta Dinámica')]");
	public By contenedorPrimeraLineaFiltros = By.xpath("(//app-cedis-liquidacion-ruta//div[contains(@class,'filtros-grid')]//div[contains(@class,'box-container')])[1]");
	public By contenedorSegundaLineaFiltros = By.xpath("(//app-cedis-liquidacion-ruta//div[contains(@class,'filtros-grid')]//div[contains(@class,'box-container')])[2]");
	public By filtroFechaInicial = By.xpath("//app-cedis-liquidacion-ruta//mat-form-field[.//*[contains(normalize-space(.),'Fecha inicial')]]");
	public By filtroHoraInicial = By.xpath("//app-cedis-liquidacion-ruta//mat-form-field[.//*[contains(normalize-space(.),'Hora inicial')]]");
	public By filtroFechaFinal = By.xpath("//app-cedis-liquidacion-ruta//mat-form-field[.//*[contains(normalize-space(.),'Fecha final')]]");
	public By filtroHoraFinal = By.xpath("//app-cedis-liquidacion-ruta//mat-form-field[.//*[contains(normalize-space(.),'Hora final')]]");
	public By asteriscoFechaInicial = By.xpath("//app-cedis-liquidacion-ruta//mat-form-field[.//*[contains(normalize-space(.),'Fecha inicial')]]//*[contains(@class,'required-marker') or normalize-space()='*']");
	public By asteriscoFechaFinal = By.xpath("//app-cedis-liquidacion-ruta//mat-form-field[.//*[contains(normalize-space(.),'Fecha final')]]//*[contains(@class,'required-marker') or normalize-space()='*']");
	public By filtroSelectorFiltro = By.xpath("//app-cedis-liquidacion-ruta//mat-form-field[.//*[contains(normalize-space(.),'Selector de Filtro')]]");
	public By controlSelectorFiltroLiquidacionRuta = By.xpath("((//app-cedis-liquidacion-ruta//div[contains(@class,'filtros-grid')]//div[contains(@class,'box-container')])[2]//mat-form-field)[1]//mat-select");
	public By filtroRutaDinamica = By.xpath("//app-cedis-liquidacion-ruta//mat-form-field[.//mat-label[contains(normalize-space(.),'Ruta Dinámica')]]");
	public By asteriscoRutaDinamica = By.xpath("//app-cedis-liquidacion-ruta//mat-form-field[.//mat-label[contains(normalize-space(.),'Ruta Dinámica')]]//*[contains(@class,'required-marker') or normalize-space()='*']");
	public By inputRutaDinamica = By.xpath("//app-cedis-liquidacion-ruta//mat-form-field[.//mat-label[contains(normalize-space(.),'Ruta Dinámica')]]//input");
	public By filtroTienda = By.xpath("//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')][.//*[contains(normalize-space(.),'Tienda')]]");
	public By asteriscoTienda = By.xpath("//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')][.//*[contains(normalize-space(.),'Tienda')]]//*[contains(@class,'required-marker') or normalize-space()='*' or contains(normalize-space(.),'Tienda *')]");
	public By controlFiltroTienda = By.xpath("//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')][.//*[contains(normalize-space(.),'Tienda')]]//*[self::mat-select or self::ng-select or @role='combobox' or contains(@class,'mat-mdc-select') or contains(@class,'ng-select-container')]");
	public By panelFiltroTiendaActivo = By.xpath("//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//ng-dropdown-panel | //app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//*[contains(@class,'ng-dropdown-panel')]");
	public By inputBusquedaFiltroTienda = By.xpath("//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//ng-dropdown-panel//input | //app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//*[contains(@class,'ng-input')]//input");
	public By contenedorScrollFiltroTienda = By.xpath("//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//ng-dropdown-panel//div[contains(@class,'scroll-host') or contains(@class,'ng-dropdown-panel-items')] | //app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//*[contains(@class,'ng-dropdown-panel-items')]");
	public By opcionesFiltroTiendaVisibles = By.xpath("//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//div[contains(@class,'ng-option') and @role='option']");
	public By opcionesFiltroTiendaSeleccionadas = By.xpath("//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//div[contains(@class,'ng-option') and @role='option' and (contains(@class,'ng-option-selected') or @aria-selected='true' or .//input[@checked or @aria-checked='true'])]");
	public By opcionesFiltroTiendaNoSeleccionadas = By.xpath("//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//div[contains(@class,'ng-option') and @role='option' and not(contains(@class,'ng-option-selected')) and not(@aria-selected='true') and not(.//input[@checked or @aria-checked='true'])]");
	public By primeraOpcionFiltroTienda = By.xpath("(//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//div[contains(@class,'ng-option') and @role='option'])[1]");
	public By segundaOpcionFiltroTienda = By.xpath("(//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//div[contains(@class,'ng-option') and @role='option'])[2]");
	public By btnLimpiarSeleccionFiltroTienda = By.xpath("(//app-cedis-liquidacion-ruta//div[contains(@class,'tienda-container')]//*[self::button or self::span or self::a][normalize-space()='Limpiar selección' or normalize-space()='Limpiar Selección'])[1]");
	public By botonConsultarLiquidacionRuta = By.xpath("//app-cedis-liquidacion-ruta//button[.//span[normalize-space()='Consultar'] or normalize-space()='Consultar']");
	public By detalleLiquidacionRuta = By.xpath("//app-cedis-liquidacion-ruta-detalle | //app-cedis-liquidacion-ruta//*[contains(normalize-space(.),'Ruta:') and (contains(normalize-space(.),'Total de Transferencias') or contains(normalize-space(.),'Tiendas Totales de la Ruta'))] | //app-cedis-liquidacion-ruta//*[contains(@id,'detalle') or contains(@class,'detalle')]");
	public By panelDesplegableActivo = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//*[contains(@class,'mat-mdc-select-panel') or contains(@class,'ng-dropdown-panel') or @role='listbox']");
	public By botonFechaInicialLiquidacionRuta = By.xpath("((//app-cedis-liquidacion-ruta//div[contains(@class,'filtros-grid')]//div[contains(@class,'box-container')])[1]//mat-form-field)[1]//mat-select");
	public By botonFechaFinalLiquidacionRuta = By.xpath("((//app-cedis-liquidacion-ruta//div[contains(@class,'filtros-grid')]//div[contains(@class,'box-container')])[1]//mat-form-field)[3]//mat-select");
	public By botonHoraInicialLiquidacionRuta = By.xpath("((//app-cedis-liquidacion-ruta//div[contains(@class,'filtros-grid')]//div[contains(@class,'box-container')])[1]//mat-form-field)[2]//mat-select");
	public By botonHoraFinalLiquidacionRuta = By.xpath("((//app-cedis-liquidacion-ruta//div[contains(@class,'filtros-grid')]//div[contains(@class,'box-container')])[1]//mat-form-field)[4]//mat-select");
	public By opcionFechaVisibleLiquidacionRuta = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option[not(@disabled) and not(contains(@class,'mat-mdc-option-disabled'))]");
	public By opcionSelectorFiltroRutaDinamica = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option[contains(normalize-space(.),'Ruta Dinámica')]");
	public By opcionSelectorFiltroTienda = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option[contains(normalize-space(.),'Tienda')]");
	public By tooltipLiquidacionRuta = By.xpath("//*[@role='tooltip' and string-length(normalize-space(.)) > 0] | //*[contains(@class,'tooltip') and string-length(normalize-space(.)) > 0]");
	public By btnRecepcionTransferenciasDevolucionesLiquidacionRuta = By.xpath("(//app-cedis-liquidacion-ruta//button[.//span[normalize-space()='Recepción de Transferencias y Devoluciones']] | //app-cedis-liquidacion-ruta//span[normalize-space()='Recepción de Transferencias y Devoluciones'])[1]");
	public By btnDevolucionesEspeciales = By.xpath("(//app-cedis-liquidacion-ruta//button[.//span[normalize-space()='Devoluciones Especiales']] | //app-cedis-liquidacion-ruta//span[normalize-space()='Devoluciones Especiales'])[1]");
	public By lbDevolucionesEspecialesCedis = By.xpath("(//app-cedis-devoluciones-especiales//span[contains(@class,'custom-header-title') and normalize-space()='Devoluciones Especiales'] | //app-cedis-devoluciones-especiales//*[self::h1 or self::h2 or self::h3][normalize-space()='Devoluciones Especiales'])[1]");
	public By btnLiquidacionRutaDevolucionesEspeciales = By.xpath("(//app-cedis-devoluciones-especiales//button[.//span[normalize-space()='Liquidación de Ruta']] | //app-cedis-devoluciones-especiales//span[normalize-space()='Liquidación de Ruta'])[1]");
	public By logoCometa = By.xpath("//img[@alt='Portal Cometa Logo']");
	public By logoUser = By.xpath("//mat-icon[normalize-space()='account_circle']");
	public By lbInicioExterno = By.xpath("//div[1]//button[1]//span[4]");
	public By iconArrow = By.xpath("//img[@alt='Down Arrow']");

	/** Span que muestra el texto de la fecha seleccionada en el campo Fecha Final (mat-form-field[4]) */
	public By spanFechaFinal = By.xpath(
		"/html/body/app-root/app-cedis-liquidacion-ruta/main/div/mat-form-field[4]//span[contains(@class,'mat-mdc-select-min-line')]");

	public By btnLiquidacionRutaRecepcion = By.xpath("//button[.//span[normalize-space()='Liquidación de Ruta' or normalize-space()='Liquidacion de Ruta'] or normalize-space()='Liquidación de Ruta' or normalize-space()='Liquidacion de Ruta']");
	public By overlayCargaLiquidacionRuta = By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando') or contains(@class,'loading-border')]");
	public By mensajeNoItemsFoundLiquidacionRuta = By.xpath("//*[contains(normalize-space(.),'No items found')]");
	public By opcionesSelectorFiltroLiquidacionRuta = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option[not(@disabled) and not(contains(@class,'mat-mdc-option-disabled'))]");
	public By opcionesFechaLiquidacionRuta = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option[not(@disabled) and not(contains(@class,'mat-mdc-option-disabled'))]");
	public By calendarioOverlayLiquidacionRuta = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-datepicker-content | //div[contains(@class,'cdk-overlay-pane')]//mat-calendar | //div[contains(@class,'cdk-overlay-pane')]//*[contains(@class,'mat-mdc-select-panel')]");

}
