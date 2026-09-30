package WEB.cometa.om;

import org.openqa.selenium.By;

/**
 * Locators para la pantalla Cambios Fisicos Directos y su modal de vista preliminar.
 * Incluye campos de las 5 columnas nuevas de HU 308162.
 *
 * @author Michelle Ceja
 * @date 2026-08-04
 */
public class cambiosFisicosElements {

    /** Primera fila del grid de Cambios Fisicos Directos. */
    public By primeraFilaGridCF = By.xpath(
        "(//app-directos-cambios-fisicos//data-grid//tbody//tr[contains(@class,'mat-mdc-row')])[1]");

    /** Contenedor del modal que muestra la vista preliminar del comprobante de CF. */
    public By modalVistaPreliminarCF = By.xpath(
        "//app-directos-cambios-fisicos//app-ticket-vista-preliminar" +
        " | //*[starts-with(@id,'mat-mdc-dialog-')]//app-ticket-vista-preliminar" +
        " | //mat-dialog-container[.//span[normalize-space()='Descarga Comprobante en Formato:']]");

    /**
     * Campo "Observaciones Tienda" en la vista preliminar del comprobante de CF.
     * Busca por label o por texto de etiqueta.
     */
    public By campoObservacionesTiendaModal = By.xpath(
        "(//*[contains(@class,'mat-mdc-dialog') or contains(@id,'mat-mdc-dialog-')]" +
        "  //*[contains(normalize-space(),'Observaciones Tienda')" +
        "     or contains(normalize-space(),'Observaciones tienda')]" +
        "  /following-sibling::*[1]" +
        " | //*[starts-with(@id,'mat-mdc-dialog-')]" +
        "  //*[normalize-space(text())='Observaciones Tienda' or normalize-space(text())='Observaciones tienda']" +
        "  /following::*[contains(@class,'value') or self::span or self::p][1])[1]");

    /**
     * Campo "Observaciones Proveedor" en la vista preliminar del comprobante de CF.
     */
    public By campoObservacionesProveedorModal = By.xpath(
        "(//*[contains(@class,'mat-mdc-dialog') or contains(@id,'mat-mdc-dialog-')]" +
        "  //*[contains(normalize-space(),'Observaciones Proveedor')" +
        "     or contains(normalize-space(),'Observaciones proveedor')]" +
        "  /following-sibling::*[1]" +
        " | //*[starts-with(@id,'mat-mdc-dialog-')]" +
        "  //*[normalize-space(text())='Observaciones Proveedor' or normalize-space(text())='Observaciones proveedor']" +
        "  /following::*[contains(@class,'value') or self::span or self::p][1])[1]");

    /** Boton de descarga Excel individual dentro del modal de vista preliminar. */
    public By btnDescargaExcelIndividualCF = By.xpath(
        "(//*[starts-with(@id,'mat-mdc-dialog-')]//button[contains(@class,'download-excel')]" +
        " | //app-ticket-vista-preliminar//button[contains(@class,'download-excel')])[1]");

    /** Boton cerrar del modal de vista preliminar de CF. */
    public By btnCerrarModalVistaPreliminarCF = By.xpath(
        "(//*[starts-with(@id,'mat-mdc-dialog-')]//button[contains(@class,'close-icon') or contains(@class,'btn-close')]" +
        " | //app-ticket-vista-preliminar//button[contains(@class,'close')])[1]");

    /** Folio visible en el modal de vista preliminar. */
    public By folioEnModalCF = By.xpath(
        "(//*[starts-with(@id,'mat-mdc-dialog-')]" +
        "  //*[contains(normalize-space(),'Folio')]/following-sibling::*[1])[1]");

    /** Seccion de descarga dentro del modal (ancla para scroll). */
    public By seccionDescargaModalCF = By.xpath(
        "(//span[normalize-space()='Descarga Comprobante en Formato:'])[1]");

    /** Encabezado Precio del grid de Cambios Fisicos Pendientes. */
    public By encabezadoPrecioGridCambiosFisicosPendientes = By.xpath(
        "(//table//th[normalize-space()='Precio'] | //mat-header-row//mat-header-cell[normalize-space()='Precio'])[1]");

    /** Celdas de la columna Precio del grid de Cambios Fisicos Pendientes. */
    public By celdasColumnaPrecioGridCFP = By.xpath("//table//td[contains(@class,'mat-column-salePrice')]");

    /** Filas visibles del grid de Cambios Fisicos Pendientes. */
    public By filasGridCambiosFisicosPendientes = By.xpath("//table//tbody//tr[contains(@class,'mat-mdc-row')]");

    /** Input de busqueda SKU o descripcion en filtros de Cambios Fisicos Pendientes. */
    public By inputBuscarSkuDescripcionCFP = By.xpath(
        "(//app-directos-cambios-fisicos-pendientes//app-filtros-de-busqueda//input[" +
        "contains(@placeholder,'SKU') or contains(@placeholder,'sku') or " +
        "contains(@placeholder,'descripción') or contains(@placeholder,'descripcion')]" +
        " | /html/body/app-root/app-directos-cambios-fisicos-pendientes/div/div/div[1]/app-filtros-de-busqueda/div[2]/div[1]/input)[1]");

    /** Seccion de descarga de resumen dentro de filtros de Cambios Fisicos Pendientes. */
    public By seccionDescargaResumenExcelCFP = By.xpath(
        "(//app-directos-cambios-fisicos-pendientes//app-filtros-de-busqueda" +
        "//div[contains(@class,'download-button-container')]" +
        " | //app-directos-cambios-fisicos-pendientes//app-descargas-resumen-pantalla" +
        "//div[contains(@class,'download-button-container')])[1]");

    /** Boton de descarga de resumen Excel en Cambios Fisicos Pendientes. */
    public By btnDescargaResumenExcelCFP = By.xpath(
        "((//app-directos-cambios-fisicos-pendientes//app-filtros-de-busqueda" +
        "//div[contains(@class,'download-button-container')]" +
        " | //app-directos-cambios-fisicos-pendientes//app-descargas-resumen-pantalla" +
        "//div[contains(@class,'download-button-container')]" +
        " | //app-directos-cambios-fisicos-pendientes//app-descargas-de-comprobantes)" +
        "//button[contains(@class,'btn-excel') and not(@disabled)])[1]");
}
