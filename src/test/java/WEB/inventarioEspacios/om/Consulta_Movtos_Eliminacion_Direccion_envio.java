package WEB.inventarioEspacios.om;

import org.openqa.selenium.By;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_Direccion_envio {

	SeleniumUtil u;
	TestCase testCase;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_Direccion_envio
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
  
    // Paso 6
    By xmlNomDireccion = By.xpath("//*[@id=\"mat-input-0\"]");
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroDireccion\"]/div[2]/div/div[3]/button/span[1]");
    By xmlSmenuConsMov = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[3]/a/div/label");
    By xmlMenuPedAdmin = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[3]/div");
    By xmlMenuPedAdminM = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[9]/div/label");
    By xmlCasillaDir = By.xpath("//*[@id=\"mat-checkbox-1\"]");
    By xmlBtnEliminar = By.xpath("//*[@id=\"formFiltroDireccion\"]/div[2]/div/div[2]/button/span[1]");
    By xmlConfirmaElim = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button");
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion Espacio
    String StrEspacio = ("//table/tbody/tr/td[2]/a[contains(text(),' Nueva Direccion ')]/parent::td/preceding-sibling::td/mat-checkbox");
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_Direccion_envio(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;

    }
	
	/**
	 * Método para Activar el checkbox en el listado del alta de direccion de envio y eliminarlo
	 * 
	 * @author Manuel CUen
	 * @param nomDir   Es necesario Capturarlo en su Data provider
	 * @return ninguno
	 */
	public void clickMenuPedidoAdmin(String nomDir, String negocio) {

		if(negocio.toUpperCase().equals("MARCA")) {
			u.highLight(xmlMenuPedAdminM);
			u.click(xmlMenuPedAdminM);
		} else if(negocio.toUpperCase().equals("OXXO")) {
			u.highLight(xmlMenuPedAdmin);
			u.click(xmlMenuPedAdmin);
		}
		u.highLight(xmlSmenuConsMov);
		u.click(xmlSmenuConsMov);
		u.mouseOver(xmlHeader);
		
//		u.highLight(xmlNomDireccion);
//		u.sendKeys(xmlNomDireccion, nomDir);
//		
//		u.highLight(xmlBtnBuscar);
//		u.click(xmlBtnBuscar);
//		u.hardWait(3);
//		
//		By LocEspacio = By.xpath(String.format(StrEspacio, nomDir));
//		System.out.println(LocEspacio);
//	    u.click(LocEspacio);
//		
//	    u.highLight(xmlBtnEliminar);
//		u.click(xmlBtnEliminar);
//		u.highLight(xmlConfirmaElim);
//		u.click(xmlConfirmaElim);
//		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickMenuPedidoAdmin");	
		u.hardWait(2);

	}

}
