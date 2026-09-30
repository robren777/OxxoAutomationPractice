package WEB.cometa.om;

import org.openqa.selenium.By;

public class devolucionesElements {

	public By calendarBy = By.id("mat-datepicker-0");
	public By dropRegionDev = By.xpath("//ng-select[.//div[contains(@class,'ng-placeholder') and normalize-space()='Región']]");
	public By dropPlazanDev = By.xpath("//ng-select[.//div[contains(@class,'ng-placeholder') and normalize-space()='Plaza']]");
	public By dropProveedorDev = By.xpath("//ng-select[.//div[contains(@class,'ng-placeholder') and normalize-space()='Proveedor']]");
	public By dropZonanDev = By.xpath("//ng-select[.//div[contains(@class,'ng-placeholder') and normalize-space()='Zona']]");
	public By dropTiendaDev = By.xpath("//ng-select[.//div[contains(@class,'ng-placeholder') and normalize-space()='Tienda']]");
	public By etiquetaFuenteAbasto = By.xpath("//app-filtros-de-busqueda//*[self::mat-label or contains(@class,'ng-placeholder')][normalize-space()='Fuente de Abasto' or normalize-space()='FA (Fuente de Abasto)']");
	public By botonConsultarDevoluciones = By.xpath("//app-filtros-de-busqueda//button[contains(@class,'btn-consultar')]");
	public By filasGridDevoluciones = By.xpath("//app-directos-devoluciones//directos-devoluciones-grid//table/tbody/tr");

    public final By paginatorPrimero = By.cssSelector("button.mat-mdc-paginator-navigation-first");
    public final By paginatorSiguiente = By.cssSelector("button.mat-mdc-paginator-navigation-next");
    public final By paginatorRango = By.cssSelector("div.mat-mdc-paginator-range-label");


}
