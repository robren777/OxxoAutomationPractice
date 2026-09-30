package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_Tipo_POP {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_Tienda
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    // Localizador del Menu Admin Pops
    By xmlMenusAdPops = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[4]/div/label");
    By xmlMenusAdPopsM = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[10]/div/label");
    
    // Localizador SubMenu Tipo de Pops
    By xmlSbPops = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[3]/a/div/label");
 
    // Localizador de encabezado
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
  
    // Localizador  Nombre Pops
    By xmlNomPops= By.xpath("//*[@id=\"mat-input-0\"]");
 
    // Localizador del Boton Buscar
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[4]/button/span[1]");
    
    // Paso 6
 
 // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[2]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion Tienda
    String StrChckTipoPop = ("//table/tbody/tr/td[2]/preceding-sibling::td/mat-checkbox");
                                                                               
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_Tipo_POP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para dar clic el Menu Pops Admin, SubMenu Pops, buscar un Pops por Nombre, Seleccionarla y dar clic en boton Eliminar
	 * 
	 * @author Manuel CUen
	 * @param idPops, NombrePops--->es necesario alimentarlos del data provider, para realizar prueba existosa
	 * @return ninguno
	 */

	public void clicSbMenuPops(String idTipoPops, String NombrePops, String negocio) {
		
		if(negocio.toUpperCase().equals("MARCA") ) {
			u.highLight(xmlMenusAdPopsM);
			u.click(xmlMenusAdPopsM);
		} else if(negocio.toUpperCase().equals("OXXO")){
			u.highLight(xmlMenusAdPops);
			u.click(xmlMenusAdPops);
		}
		u.highLight(xmlSbPops);
		u.click(xmlSbPops);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.mouseOver(xmlHeader);
		
		u.highLight(xmlNomPops);
		u.sendKeys(xmlNomPops, NombrePops);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
	    u.hardWait(4);
	    
	    System.out.println(StrChckTipoPop);
	    System.out.println(idTipoPops);
	    By LocTiendaId = By.xpath(String.format(StrChckTipoPop, idTipoPops));
	    u.click(LocTiendaId);
	    u.hardWait(2);
	    
	    u.highLight(xmlBtonEliminar);
	    u.click(xmlBtonEliminar);
	    
	    u.highLight(xmlConfEliminar);
	    u.click(xmlConfEliminar);
	    u.hardWait(2);
	    
	    testCase.addScreenShotCurrentStep(u, "clicSbMenuPops");
		u.hardWait(2);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlNomPops);
		u.unhighLight(xmlBtnBuscar);
		
	    
	}

}
