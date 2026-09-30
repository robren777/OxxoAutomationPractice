package WEB.inventarioEspacios.om;

import java.time.Duration;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_POP {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_Espacio
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    // Localizador del Menu Pops Admin
    By xmlPopAdmin = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[4]/div/label");
    
    // Localizador SubMenu Pops
    By xmlSmenuPop = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[1]/a/div/label");
    
    // Localizador Encabezado
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
   
    // Paso 6
 
    // Localizadores botones Eliminar y confirmar eliminacion
    By xmlnombrePops = By.xpath("//*[@id=\"mat-input-0\"]");
    By xmlbtnBuscar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[4]/button");
    By xmlBtnEliminar = By.xpath("//*[@id=\"formFiltroPop\"]/div[2]/div/div[2]/button/span[1]");
    By xmlBtnConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion estatus de mensajeria
    //String StrIdPops = ("//table/tbody/tr/td/a[contains(text(),'%s ')]/parent::td/preceding-sibling::td/mat-checkbox");
    String StrIdPops = ("//table/tbody/tr/td/a[contains(text(),'%s')]/parent::td/preceding-sibling::td/mat-checkbox");
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_POP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para seleccionar un Id de espacio y dar click en el boton Eliminar y luego consultar el movto
	 * 
	 * @author Manuel CUen
	 * @param Id Espacio
	 * @return ninguno
	 */
	
	public void MenuPopsPops(String codigoPops,String nomPops) {
		
		System.out.println(StrIdPops);
		System.out.println(codigoPops);
		
		u.highLight(xmlPopAdmin);
		u.click(xmlPopAdmin);
		
		u.highLight(xmlSmenuPop);
		u.click(xmlSmenuPop);
		
		u.mouseOver(xmlHeader);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlnombrePops);
		u.sendKeys(xmlnombrePops, nomPops);
		
		u.highLight(xmlbtnBuscar);
		u.click(xmlbtnBuscar);
		u.hardWait(5);
		
		By LocPopsId = By.xpath(String.format(StrIdPops, codigoPops));
		System.out.println(LocPopsId);
	    u.click(LocPopsId);
	    
	    u.highLight(xmlBtnEliminar);
	    u.click(xmlBtnEliminar);
	    
	    u.highLight(xmlBtnConfEliminar);
	    u.click(xmlBtnConfEliminar);
	    
		testCase.addScreenShotCurrentStep(u, "clickMenuPopsPops");
		u.hardWait(2);
	}

}
