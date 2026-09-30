package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_Tipo_Pedido {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_Tipo_Pedido
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    // Localizador del Menu Pedidos Admin
    By xmlMenusTipoPed = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[3]/div/label");
    By xmlMenusTipoPedM = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[9]/div/label");
    
    // Localizador SubMenu Tpos de Pedido
    By xmlSbMtipoPed = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[4]/a/div/label");
 
    // Localizador de encabezado
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
  
    // Localizador  Nombre Tipo de Pedido
    By xmlNombreTipoPed= By.xpath("//*[@id=\"mat-input-0\"]");
 
    // Localizador del Boton Buscar
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[4]/button/span[1]");
    
    // Paso 6
 
 // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[2]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion Tipo de Pedido
    String StrCheckTipoPed = ("//div/table/tbody/tr/td[2]/a[contains(text(),'%s')]/ancestor::td/preceding-sibling::td/mat-checkbox");
                             //div/table/tbody/tr/td[2]/a/span[contains(text(),'3501198')]/ancestor::td/preceding-sibling::td/mat-checkbox                                                 
  //div/table/tbody/tr/td[2]/a/span[contains(text(),'3501199')]/ancestor::td/preceding-sibling::td/mat-checkbox
    
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_Tipo_Pedido(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para dar clic el Menu Pedidos Admin, SubMenu Tipos de Pedido, buscar un Tipo de pedido por Nombre, Seleccionarla y dar clic en boton Eliminar
	 * 
	 * @author Manuel CUen
	 * @param idTipoPedido, NombreTipoPed--->es necesario alimentarlos del data provider, para realizar prueba existosa
	 * @return ninguno
	 */

	public void clicSbMenuTipoPedido(String idTipoPedido, String NombreTipoPed, String negocio) {
		
		if(negocio.toUpperCase().equals("MARCA")) {
			u.highLight(xmlMenusTipoPedM);
			u.click(xmlMenusTipoPedM);
		} else if(negocio.toUpperCase().equals("OXXO")) {
			u.highLight(xmlMenusTipoPed);
			u.click(xmlMenusTipoPed);
		}
		u.highLight(xmlSbMtipoPed);
		u.click(xmlSbMtipoPed);
		
		u.mouseOver(xmlHeader);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlNombreTipoPed);
		u.sendKeys(xmlNombreTipoPed, NombreTipoPed);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
	    u.hardWait(4);
	    
	    System.out.println(StrCheckTipoPed);
	    System.out.println(idTipoPedido);
	    By LocTipoPedId = By.xpath(String.format(StrCheckTipoPed, idTipoPedido));
	    u.click(LocTipoPedId);
	    u.hardWait(2);
	    
	    u.highLight(xmlBtonEliminar);
	    u.click(xmlBtonEliminar);
	    
	    u.highLight(xmlConfEliminar);
//	    u.click(xmlConfEliminar);
	    u.hardWait(2);
	    
	    testCase.addScreenShotCurrentStep(u, "clicSbMenuTipoPedido");
		u.hardWait(2);
		
		u.highLight(xmlNombreTipoPed);
		u.unhighLight(xmlBtnBuscar);
		
	    
	}


}
