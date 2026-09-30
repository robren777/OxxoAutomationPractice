package WEB.inventarioEspacios.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_Regla {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_Regla
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    // Localizador del Menu Admon
    By xmlMenusAd = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/label");
    						    
    
    // Localizador SubMenu Regla
    By xmlSmenuRegla = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[12]/a/div/label");
 
    // Localizador de encabezado
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
  
    // Localizador del Nombre de Regla
    By xmlNombreRegla = By.xpath("//*[@id=\"mat-input-0\"]");
    By xmlEstatus = By.xpath("//*[@id=\"mat-select-value-1\"]");
 
    // Localizador del Boton Buscar
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[4]/button/span[1]");
    
    // Paso 6
 
 // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[2]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion estatus de mensajeria
    String StrIdRegla = ("//table/tbody/tr/td[2][contains(text(),'%s')]/preceding-sibling::td/mat-checkbox");
    String StrEstatus = ("//div[@id=\"mat-select-0-panel\"]/mat-option/span[contains(text(),'%s')]");    
  
                               
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_Regla(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para dar clic el Menu Administracion, SubMenu Reglas, buscar una regla por Id, seleccionarla y dar clic en botn eliminar
	 * 
	 * @author Manuel CUen
	 * @param idRegla, NomRegla, estatus--->es necesario alimentarlos del data provider, para realizar prueba existosa
	 * @return ninguno
	 */
		

	public void clickSubMenuRegla(String idRegla, String NomRegla, String estatus) {
		
		u.highLight(xmlMenusAd);
		u.click(xmlMenusAd);
		
		u.highLight(xmlSmenuRegla);
		u.click(xmlSmenuRegla);
		
		u.mouseOver(xmlHeader);
		
//		u.highLight(xmlNombreRegla);
//		u.sendKeys(xmlNombreRegla, NomRegla);
//		
//		u.highLight(xmlEstatus);
//		u.click(xmlEstatus);
//		
//		By LocEstatus = By.xpath(String.format(StrEstatus, estatus));
//		System.out.println(StrIdRegla);
//		u.highLight(LocEstatus);
//	    u.click(LocEstatus);
//	    u.hardWait(2);
//		
//		u.highLight(xmlBtnBuscar);
//		u.click(xmlBtnBuscar);
//	    u.hardWait(2);
//		
//		By LocReglaId = By.xpath(String.format(StrIdRegla, idRegla));
//	    u.click(LocReglaId);
//	    u.hardWait(2);
//	    
//	    u.highLight(xmlBtonEliminar);
//	    u.click(xmlBtonEliminar);
//	    
//	    u.highLight(xmlConfEliminar);
//	    //u.click(xmlConfEliminar);
//	    
//	    testCase.addScreenShotCurrentStep(u, "clickSubMenuRegla");
//		u.hardWait(2);
//		u.unhighLight(xmlNombreRegla);
//		u.unhighLight(LocEstatus);
//		u.unhighLight(xmlBtnBuscar);
	}
}
