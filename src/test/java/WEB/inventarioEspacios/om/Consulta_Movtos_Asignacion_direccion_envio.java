package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Asignacion_direccion_envio {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Asignacion_direccion_envio
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
  
    // Paso 6
    By xmlSmenuConsMov = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[3]/a/div/label");
    By xmlTotalReg = By.xpath("//*[@id=\"content-nav\"]/app-address-list/div/mat-paginator/div/div/div[2]");
    By xmlnomDir = By.xpath("//*[@id=\"mat-input-0\"]");
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroDireccion\"]/div[2]/div/div[3]/button/span[1]");
    By xmlEditar = By.xpath("//*[@id=\"content-nav\"]/app-address-list/div/div/table/tbody/tr[3]/td[10]/mat-icon");
    By xmlPestaTdsAsoc = By.xpath("//*[@id=\"mat-tab-label-13-1\"]/div");   
    
    By xmldatosGrals = By.xpath("//*[@id=\"mat-tab-label-13-0\"]/div");
    By xmlBtnGuardar = By.xpath("//*[@id=\"mat-tab-content-13-0\"]/div/button[1]/span[1]");
    By xmlConfGuardar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
      
    By xmlMenuPedAdmin = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[3]/div");
    By xmlMenuPedAdminM = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[9]/div/label");
  
/*
 * Variables que reciben un parametro
 **********************************************************************************************/
    String StrNomDirEdit = ("//table/tbody/tr/td[2]/a[contains(text(),' %s ')]//following::td[8]/mat-icon");
    String StrSelTda = ("//div[@class=\"form-group-input row lista-inventario lista-cuadro\"]/section/span[2]/ul/div/li/mat-checkbox/label/span[2][contains(text(),\'%s')]");
    
    
	/**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Asignacion_direccion_envio(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para llenar los datos de asignacion de direccion de envio y dar de alta
	 * 
	 * @author Manuel CUen
	 * @param Plaza, Direccion, Codigo Postal, Calle, Numero, Entre Calle, y Entre Calle, Nombre y Telefono del Contacto, Comentario
	 * @return ninguno
	 */
	public void clickMenuPedidoAdmin(String nomDir, String tienda, String negocio) {

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
		
		u.highLight(xmlnomDir);
		u.sendKeys(xmlnomDir, nomDir);
		
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
		u.hardWait(3);
		
		By LocNomDirEditar = By.xpath(String.format(StrNomDirEdit, nomDir));
		u.click(LocNomDirEditar);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlPestaTdsAsoc);
		u.click(xmlPestaTdsAsoc);
		
		By LocTienda = By.xpath(String.format(StrSelTda, tienda));
		u.hardWait(2);
		u.mouseOver(LocTienda);
		u.hardWait(2);
		u.click(LocTienda);
		
		u.highLight(xmldatosGrals);
		u.click(xmldatosGrals);
		u.unhighLight(xmlPestaTdsAsoc);
		u.unhighLight(xmldatosGrals);
		u.highLight(xmlBtnGuardar);
		u.click(xmlBtnGuardar);
		u.highLight(xmlConfGuardar);
		u.click(xmlConfGuardar);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickMenuPedidoAdmin");
		u.hardWait(2);

	}
	



}
