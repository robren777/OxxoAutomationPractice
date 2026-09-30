package WEB.inventarioEspacios.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Modificacion_cantidad_Espacios_Tienda {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_Tienda
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    // Localizador del Menu Espacios Admin
    By xmlMenusEspaciosAd = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/label");
    By xmlMenuEspacioM = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[5]/div/label");
    
    // Localizador SubMenu Consulta de Inventarios
    By xmlSbMConsultaInv = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[1]/a/div/label");
 
    // Localizador de encabezado
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
 
    // Localizador de selector plaza
    By xmlPlaza = By.xpath("//*[@id=\"mat-select-value-3\"]/span");
    
    // Localizador  Descripcion de Tienda
    By xmlDescripTienda= By.xpath("//*[@id=\"mat-input-0\"]");
 
    // Localizador del Boton Buscar
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroInventario\"]/div[2]/div/div[4]/button/span[1]");
    
    // Localizador de la Flecha CR
    By xmlFlechaTienda = By.xpath("//*[@id=\"content-nav\"]/app-inventory-list/div/div[3]/table/tbody/tr[1]/td[2]/a/mat-icon");
    // Paso 6
    
    By xmlNomEspacio = By.xpath("//mat-tab-body/div/div/div/div/table/tbody/tr/td[contains(text(),' %s ')]/following::input");
    By xmlNombreEsp = By.xpath("//*[@id=\"mat-input-2\"]");
  //*[@id="tablaEspacios_3488368"]/thead/tr/th[3]
    
 // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion Tienda
    String StrSelecPlaza = ("//div[@id=\"mat-select-2-panel\"]/mat-option/span[contains(text(),'%s')]");
    String StrCheckTienda = ("//section/span[2]/ul/div/li[4]/mat-checkbox/label/span[2][contains(text(),'%s' )]/preceding-sibling::span");
    String StrNomEspacio = ("//mat-tab-body/div/div/div/div/table/tbody/tr/td[contains(text(),' %s ')]/following::input");
    //table/tbody/tr/td[contains(text(),' 5Ta Parrilla Koxka1 ')]/following::input
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Modificacion_cantidad_Espacios_Tienda(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para dar clic el Menu Administracion, SubMenu Tienda, buscar una Tienda por Descripcion, Seleccionarla y dar clic en boton Eliminar
	 * 
	 * @author Manuel CUen
	 * @param idTienda, DescripTienda--->es necesario alimentarlos del data provider, para realizar prueba existosa
	 * @return ninguno
	 */

	public void clicSbMenuConsultaInv(String nomPlaza, String nomTienda, String nomEspacio, String cantidad, String negocio) {
		
		if(negocio.toUpperCase().equals("MARCA")) {
			u.highLight(xmlMenuEspacioM);
			u.click(xmlMenuEspacioM);
		} else if(negocio.toUpperCase().equals("OXXO")) {
			u.highLight(xmlMenusEspaciosAd);
			u.click(xmlMenusEspaciosAd);
		}

		u.highLight(xmlSbMConsultaInv);
		u.click(xmlSbMConsultaInv);
		
		u.mouseOver(xmlHeader);
		
//		m.explicitWaitToBeInvisible(20);
//		u.highLight(xmlPlaza);
//		u.click(xmlPlaza);
//				
//	    By LocNombrePlaza = By.xpath(String.format(StrSelecPlaza, nomPlaza));	    
//	    u.click(LocNombrePlaza);
//	    u.hardWait(3);
//	    
//	    By LocNombreTienda = By.xpath(String.format(StrCheckTienda, nomTienda));
//	    System.out.println(LocNombreTienda);
//	    u.click(LocNombreTienda);
//	    u.hardWait(2);
//	    
//		m.explicitWaitToBeInvisible(20);
//		u.highLight(xmlBtnBuscar);
//		u.click(xmlBtnBuscar);
//	    u.hardWait(4);
//	    
//	    m.explicitWaitToBeInvisible(20);
//	    u.highLight(xmlFlechaTienda);
//	    u.click(xmlFlechaTienda);
//	
//	   
//	    // Cantidad con Localizador Parametrizado
//	    System.out.println(StrNomEspacio);
//	    System.out.println(nomEspacio);
//	    By LocCantidad = By.xpath(String.format(StrNomEspacio, nomEspacio));
//	    
//	    u.click(LocCantidad);
//	    u.getDriver().findElement(LocCantidad).clear();
//	    u.sendKeys(LocCantidad, cantidad);
//	    u.hardWait(2);
//        
//        u.highLight(xmlNombreEsp);
//        u.click(xmlNombreEsp);
	    
	    testCase.addScreenShotCurrentStep(u, "clicSbMenuConsultaInv");
		u.hardWait(2);
//		u.unhighLight(xmlNombreEsp);
//		u.unhighLight(xmlFlechaTienda);
//		u.unhighLight(xmlBtnBuscar);
//	    
	}

}
