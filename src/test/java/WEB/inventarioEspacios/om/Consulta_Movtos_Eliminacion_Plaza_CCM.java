package WEB.inventarioEspacios.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_Plaza_CCM {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_Plaza_CCM
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    // Localizador del Menu Admon
    By xmlMenusAd = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/label");
    
    // Localizador Consulta Plaza CCM 
    By xmlSmenuPlazaCCM = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div[1]/mat-list/div[4]/a/div/label");
    
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
    
    // Localizador Nombre de la Plaza CCM
    By xmlNombrePlaza = By.xpath("//*[@id=\"mat-input-0\"]");
    
 // Localizador del boton Buscar
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroPlaza\"]/div[2]/div/div[4]/button/span[1]");
    
    // Paso 6
 
 // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltroPlaza\"]/div[2]/div/div[2]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion estatus de mensajeria
    String StrCheckPlazaCCM = ("//table/tbody/tr/td[2][contains(text(),' %s ')]/preceding-sibling::td/mat-checkbox");
    
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_Plaza_CCM(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para seleccionar menu admin-consulta plazas ccm, activar checkbox Id Plaza y dar clic al boton Elininar
	 * 
	 * @author Manuel Cuen
	 * @param Id Plaza, nombre plaza ccm - es necesario capturar el Id y nombre plaza, en el data-provider que desea Eliminar
	 * @return ninguno
	 */
		
	public void clickSmenuPlazaCCM(String PlazaCCMId, String nomPlazaCcm) {
		
		u.highLight(xmlMenusAd);
		u.click(xmlMenusAd);
		
		u.highLight(xmlSmenuPlazaCCM);
		u.click(xmlSmenuPlazaCCM);
		
		u.mouseOver(xmlHeader);
		u.hardWait(2);
		
		u.highLight(xmlNombrePlaza);
		u.sendKeys(xmlNombrePlaza, nomPlazaCcm);
		
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
			
		By LocPlazaCCMId = By.xpath(String.format(StrCheckPlazaCCM, PlazaCCMId));
	    u.click(LocPlazaCCMId);
	    
		u.highLight(xmlBtonEliminar);
		u.click(xmlBtonEliminar);
		
		u.highLight(xmlConfEliminar);
		u.click(xmlConfEliminar);
		
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickSmenuPlazaCCM");
		u.hardWait(2);
		
		u.unhighLight(xmlNombrePlaza);
		u.unhighLight(xmlBtnBuscar);
		
	}

}
