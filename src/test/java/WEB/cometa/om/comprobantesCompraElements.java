package WEB.cometa.om;

import org.openqa.selenium.By;

public class comprobantesCompraElements {

	//public By tituloComprobantesCompras = By.xpath("//*[contains(translate(normalize-space(.),"
	//		+ "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'comprobantes de compra')]");
	public By filtroProveedor = By.xpath("//ng-select[contains(@bindlabel,'supplierName')]");
	public By filtroFuenteAbasto = By.xpath("//ng-select[contains(@bindlabel,'fuenteDeAbasto')]");
	public By filtroZona = By.xpath("//ng-select[contains(@bindlabel,'zona')]");
	public By filtroRegion = By.xpath("//ng-select[contains(@bindlabel,'region')]");
	public By filtroPlaza = By.xpath("//ng-select[contains(@bindlabel,'plaza')]");
	public By filtroTienda = By.xpath("//ng-select[contains(@bindlabel,'tienda')]");
	public By filtroRangoFechas = By.xpath("//mat-datepicker-toggle[@data-mat-calendar='mat-datepicker-0']");
	public By inputFechaInicio = By.xpath("//mat-date-range-input//input[@matstartdate or contains(@class,'mat-start-date')]");
	public By inputFechaFin = By.xpath("//mat-date-range-input//input[@matenddate or contains(@class,'mat-end-date')]");
	public By botonConsultar = By.xpath("//button[.//span[normalize-space()='Consultar']]");
	public By botonConsultarHabilitado = By.xpath("//button[.//span[normalize-space()='Consultar'] and not(@disabled) and not(contains(@class,'mat-mdc-button-disabled'))]");
	public By botonCalendario = By.xpath("//mat-datepicker-toggle/button");
	public By calendario = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-datepicker-content");
	public By filasGrid = By.xpath("//data-grid//tbody/tr | //table[contains(@class,'mat-mdc-table')]//tbody/tr");
	public By botonPeriodo = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-period-button')]");
	public By botonAnterior = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-previous-button')]");
	public By botonSiguiente = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-next-button')]");
	public By diasHabilitados = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-month-view"
			+ "//button[not(contains(@class,'mat-calendar-body-disabled'))]");
	private static final String COMPONENTE_COMPROBANTES = "(//app-comprobantes-de-compra | //app-directos-comprobantes-de-compra)";

	public By tituloComprobantesCompra = By.xpath(COMPONENTE_COMPROBANTES + "//*[contains(@class,'custom-header-title') and translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='comprobantes de compra']");
	public By modalComprobanteCompra = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container][.//*[contains(normalize-space(.),'Comprobante de compra') or contains(normalize-space(.),'Comprobante de Compra')]])[1]");
	public By tituloModalComprobanteCompra = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//*[contains(normalize-space(.),'Comprobante de compra') or contains(normalize-space(.),'Comprobante de Compra')])[1]");
	public By contenidoModalComprobanteCompra = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//*[contains(@class,'modal-body') or contains(@class,'content') or contains(@class,'pdf') or self::iframe or self::embed or self::object])[1]");
	public By footerModalComprobanteCompra = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//*[contains(@class,'modal-footer') or contains(@class,'footer')])[last()]");
	public By botonDescargaPdfModal = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//button[contains(@class,'download-pdf') or .//*[contains(normalize-space(.),'PDF')]])[1]");
	public By botonDescargaExcelModal = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//button[contains(@class,'download-excel') or .//*[contains(normalize-space(.),'Excel')]])[1]");
	public By botonCerrarModal = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//button[contains(@class,'close') or @aria-label='Cerrar' or .//*[normalize-space()='close']])[1]");
	public By paginadorModalComprobanteCompra = By.xpath("(//*[starts-with(@id,'mat-mdc-dialog-')]/div/div/app-detalle/div[4]/div/mat-paginator | //*[starts-with(@id,'mat-mdc-dialog-')][.//app-detalle]//app-detalle//mat-paginator | //*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container][.//app-detalle]//app-detalle//mat-paginator | //app-detalle//mat-paginator)[last()]");
	public By botonPrimeroModal = By.xpath("(//*[starts-with(@id,'mat-mdc-dialog-')]/div/div/app-detalle/div[4]/div/mat-paginator/div/div/div/button[1] | //*[starts-with(@id,'mat-mdc-dialog-')][.//app-detalle]//app-detalle//mat-paginator//button[1] | //*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container][.//app-detalle]//app-detalle//mat-paginator//button[1] | //app-detalle//mat-paginator//button[contains(@class,'mat-mdc-paginator-navigation-first')])[last()]");
	public By botonAnteriorModal = By.xpath("(//*[starts-with(@id,'mat-mdc-dialog-')]/div/div/app-detalle/div[4]/div/mat-paginator/div/div/div/button[2] | //*[starts-with(@id,'mat-mdc-dialog-')][.//app-detalle]//app-detalle//mat-paginator//button[2] | //*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container][.//app-detalle]//app-detalle//mat-paginator//button[2] | //app-detalle//mat-paginator//button[contains(@class,'mat-mdc-paginator-navigation-previous')])[last()]");
	public By botonSiguienteModal = By.xpath("(//*[starts-with(@id,'mat-mdc-dialog-')]/div/div/app-detalle/div[4]/div/mat-paginator/div/div/div/button[3] | //*[starts-with(@id,'mat-mdc-dialog-')][.//app-detalle]//app-detalle//mat-paginator//button[3] | //*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container][.//app-detalle]//app-detalle//mat-paginator//button[3] | //app-detalle//mat-paginator//button[contains(@class,'mat-mdc-paginator-navigation-next')])[last()]");
	public By botonUltimoModal = By.xpath("(//*[starts-with(@id,'mat-mdc-dialog-')]/div/div/app-detalle/div[4]/div/mat-paginator/div/div/div/button[4] | //*[starts-with(@id,'mat-mdc-dialog-')][.//app-detalle]//app-detalle//mat-paginator//button[4] | //*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container][.//app-detalle]//app-detalle//mat-paginator//button[4] | //app-detalle//mat-paginator//button[contains(@class,'mat-mdc-paginator-navigation-last')])[last()]");
	public By contadorPaginacionModal = By.xpath("(//*[starts-with(@id,'mat-mdc-dialog-')]/div/div/app-detalle/div[4]/div/mat-paginator//div[contains(@class,'mat-mdc-paginator-range-label')] | //*[starts-with(@id,'mat-mdc-dialog-')][.//app-detalle]//app-detalle//mat-paginator//div[contains(@class,'mat-mdc-paginator-range-label')] | //*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container][.//app-detalle]//app-detalle//mat-paginator//div[contains(@class,'mat-mdc-paginator-range-label')] | //app-detalle//mat-paginator//div[contains(@class,'mat-mdc-paginator-range-label')])[last()]");
	public By overlaysCarga = By.xpath("//*[contains(normalize-space(.),'Consultando') or contains(normalize-space(.),'Espere un momento')]");
}