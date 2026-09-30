package WEB.cometa.om;

import org.openqa.selenium.By;

public class menuCedisElements {

	public By botonMenuCedis = By.xpath("//header//button[.//span[normalize-space()='CEDIS']] | //header//a[.//span[normalize-space()='CEDIS'] and (@aria-haspopup='menu' or @role='button')]");
	public By opcionRecepcionYDevolucion = By.xpath("//*[self::a or self::button][normalize-space(.)='Recepción y Devolución']");
	public By opcionesSubmenuCedis = By.xpath("//div[@role='menu' or contains(@class,'mat-mdc-menu-panel')]//*[self::a or self::button][@role='menuitem' or contains(@class,'mat-mdc-menu-item')] | //app-cedis-home/main//*[self::a or self::button]");
	public By opcionReportesSubmenu = By.xpath("//div[@role='menu' or contains(@class,'mat-mdc-menu-panel')]//*[self::a or self::button][.//*[normalize-space()='Reportes'] or normalize-space()='Reportes']");
	public By opcionGraficasSubmenu = By.xpath("//div[@role='menu' or contains(@class,'mat-mdc-menu-panel')]//*[self::a or self::button][.//*[normalize-space()='Graficas' or normalize-space()='Gráficas'] or normalize-space()='Graficas' or normalize-space()='Gráficas']");
	public By reportesSolapaSuperior = By.xpath("//header//nav//*[self::a or self::button or self::span][normalize-space()='Reportes']");
	public By graficasSolapaSuperior = By.xpath("//header//nav//*[self::a or self::button or self::span][normalize-space()='Graficas' or normalize-space()='Gráficas']");
	public By mensajeAccesoRestringido = By.xpath("//*[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚ', 'abcdefghijklmnopqrstuvwxyzáéíóú'), 'no tienes permiso') or contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'forbidden') or contains(normalize-space(.), '403') or contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚ', 'abcdefghijklmnopqrstuvwxyzáéíóú'), 'contacta al equipo de soporte')]");
	public By formularioLoginAccessControl = By.xpath("//form[.//input[@id='username' or @name='username']] | //input[@id='username' or @name='username']");
	public By contenedorPrincipalPagina = By.xpath("//main | //app-root | //body");
}