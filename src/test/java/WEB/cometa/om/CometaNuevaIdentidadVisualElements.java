package WEB.cometa.om;

import org.openqa.selenium.By;

public class CometaNuevaIdentidadVisualElements {

	public By solapaSuperiorPortalCometa = By.cssSelector("header .top-bar, app-header header, header, nav, mat-toolbar");
	public By logoNuevaIdentidadVisual = By.cssSelector("header .top-bar img.logo[src*='portal-cometa-logo'], header .top-bar img.logo, img.logo[src*='portal-cometa-logo']");
	public By btnCalendarioRecepcionCedis = By.xpath("(//app-recepcion-de-transferencias//mat-datepicker-toggle//button | //app-filtros-historicos//mat-datepicker-toggle//button | //mat-datepicker-toggle//button)[1]");
	public By contenedorDatepicker = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-datepicker-content");
	public By btnConsultarRecepcionCedis = By.xpath("(/html/body/app-root/app-recepcion-de-transferencias/main/div[1]/app-filtros-historicos/form/div/div[4]/button[2]/span[2] | //app-filtros-historicos//button[.//span[normalize-space()='Consultar'] and not(@disabled)]//span[normalize-space()='Consultar'] | //app-recepcion-de-transferencias//button[.//span[normalize-space()='Consultar'] and not(@disabled)]//span[normalize-space()='Consultar'])[1]");
	public By gridRecepcionCedis = By.xpath("(//app-tabla-entrega//table//tbody//tr[1] | //app-tabla-entrega//table//tr[@role='row' and not(contains(@class,'header'))][1] | //app-tabla-entrega//table//tbody)[1]");
	public By btnFuePrimerRegistroCedis = By.xpath("(//app-tabla-entrega//table/tbody/tr[1]//td[contains(@class,'mat-column-FUE')]//button | //app-tabla-entrega//table/tbody/tr[1]/td[7]//button)[1]");
	public By btnCerrarModalFue = By.xpath("//*[@id='mat-mdc-dialog-0']/div/div/app-modal-fue/main/div[1]/div[2]/div/button[3]");
	public By btnCerrarModalGrid = By.xpath("(//*[starts-with(@id,'mat-mdc-dialog-')]/div/div/app-detalle/div[1]/div[2]/div/button[3] | //*[starts-with(@id,'mat-mdc-dialog-')]/div/div/app-ticket-vista-preliminar/modal-paginated/main/div[1]/div[1]/button | //app-detalle//button[contains(@class,'close-icon') or contains(@class,'close')] | //app-ticket-vista-preliminar//modal-paginated//main/div[1]/div[1]/button)[last()]");
	public By tituloDevolucionesDirectos = By.xpath("(//app-directos-devoluciones//*[contains(@class,'custom-header-title') and normalize-space()='Devoluciones'] | //span[contains(@class,'custom-header-title') and normalize-space()='Devoluciones'])[1]");
	public By filtrosDevolucionesDirectos = By.xpath("(//app-directos-devoluciones//app-filtros-de-busqueda | //app-directos-devoluciones//form | //app-directos-devoluciones//*[contains(normalize-space(),'Proveedor') and contains(normalize-space(),'Rango')])[1]");
	public By btnCalendarioDevolucionesDirectos = By.xpath("(//app-directos-devoluciones//mat-datepicker-toggle//button | //app-directos-devoluciones//button[contains(@aria-label,'calendar') or contains(@aria-label,'calendario')])[1]");
	public By btnConsultarDevolucionesDirectos = By.xpath("(/html/body/app-root/app-directos-devoluciones/div/app-filtros-de-busqueda/div/div[2]/button[2] | //app-directos-devoluciones//button[.//span[normalize-space()='Consultar'] and not(@disabled)])[1]");
	public By primeraFilaGridDevoluciones = By.xpath("(/html/body/app-root/app-directos-devoluciones/div/directos-devoluciones-grid/div/table/tbody/tr[1] | //app-directos-devoluciones//directos-devoluciones-grid//table//tbody//tr[1] | //directos-devoluciones-grid//table//tbody//tr[1])[1]");
	public By tituloCambiosFisicosDirectos = By.xpath("(//app-directos-cambios-fisicos//*[contains(@class,'custom-header-title') and (normalize-space()='Cambios Fisicos' or normalize-space()='Cambios Físicos')] | //span[contains(@class,'custom-header-title') and (normalize-space()='Cambios Fisicos' or normalize-space()='Cambios Físicos')])[1]");
	public By filtrosCambiosFisicosDirectos = By.xpath("(//app-directos-cambios-fisicos//app-filtros-de-busqueda | //app-directos-cambios-fisicos//form | //app-directos-cambios-fisicos//*[contains(normalize-space(),'Proveedor') and contains(normalize-space(),'Rango')])[1]");
	public By btnCalendarioCambiosFisicosDirectos = By.xpath("(//app-directos-cambios-fisicos//mat-datepicker-toggle//button | //app-directos-cambios-fisicos//button[contains(@aria-label,'calendar') or contains(@aria-label,'calendario')])[1]");
	public By btnConsultarCambiosFisicosDirectos = By.xpath("(//app-directos-cambios-fisicos//button[.//span[normalize-space()='Consultar'] and not(@disabled)] | //app-directos-cambios-fisicos//button[contains(@class,'btn-consultar') and not(@disabled)])[1]");
	public By primeraFilaGridCambiosFisicos = By.xpath("(/html/body/app-root/app-directos-cambios-fisicos/data-grid/div/table/tbody/tr[1] | //app-directos-cambios-fisicos//data-grid//table//tbody//tr[1] | //app-directos-cambios-fisicos//data-grid//table//tr[@role='row'][1])[1]");
	public By btnFaqsDirectos = By.xpath("(//a[@href='/PortalCometa/faqs/directos/home'] | //button[.//span[normalize-space()='FAQS Directos']] | //span[normalize-space()='FAQS Directos'])[1]");
	public By btnFaqsCedis = By.xpath("(//a[@href='/PortalCometa/faqs/cedis/home'] | //button[.//span[normalize-space()='FAQS CEDIS']] | //span[normalize-space()='FAQS CEDIS'])[1]");
	public By overlaysCarga = By.xpath("//*[contains(normalize-space(), 'Consultando') or contains(normalize-space(), 'Espere un momento')]");
	public By encabezadosGridCFPendientes = By.xpath("//data-grid//tr[contains(@class,'row-header')]//th[contains(@class,'cell-title')]");
	public By encabezadoPrecioGridCFPendientes = By.xpath("//data-grid//th[contains(@class,'mat-column-salePrice')]");
	public By encabezadoCostoGridCFPendientes = By.xpath("//data-grid//th[contains(@class,'mat-column-costo')]");
	public By celdasPrecioGridCFPendientes = By.xpath("//data-grid//td[contains(@class,'mat-column-salePrice')]");
}