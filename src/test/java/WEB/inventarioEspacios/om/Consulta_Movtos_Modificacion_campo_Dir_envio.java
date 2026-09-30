package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Modificacion_campo_Dir_envio {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Modificacion_campo_Dir_envio
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    // Localizador del Menu Pedidos Admin
    By xmlMenuPedAd = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[3]/div/label");
    By xmlMenuPedAdminM = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[9]/div/label");
    
    // Localizador SubMenu Consulta de Direccion de Envio
    By xmlSbConsDirEnv = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[3]/a/div/label");
 
    // Localizador de encabezado
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
  
    // Localizador  Nombre Direccion
    By xmlNombreDir= By.xpath("//*[@id=\"mat-input-0\"]");
 
    // Localizador del Boton Buscar
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroDireccion\"]/div[2]/div/div[3]/button/span[1]");
                                
    // Localizador del Boton Modificar
    By xmlBtnModificar = By.xpath("//*[@id=\"content-nav\"]/app-address-list/div/div/table/tbody/tr[1]/td[10]/mat-icon");
    
    // Localizadores a modificar
    By xmlNomDirModif = By.xpath("//mat-sidenav-content[@id=\"content-nav\"]/app-address-detail/div/div/mat-tab-group/div/mat-tab-body/div/div/div/div[2]/mat-form-field/div/div/div/input");
    By xmlCodigoP = By.xpath("//mat-sidenav-content[@id=\"content-nav\"]/app-address-detail/div/div/mat-tab-group/div/mat-tab-body/div/div/div/div[3]/mat-form-field/div/div/div/input");
    By xmlCalle = By.xpath("//mat-sidenav-content[@id=\"content-nav\"]/app-address-detail/div/div/mat-tab-group/div/mat-tab-body/div/div/div[2]/div/mat-form-field/div/div/div/input");
    By xmlNumero = By.xpath("//mat-sidenav-content[@id=\"content-nav\"]/app-address-detail/div/div/mat-tab-group/div/mat-tab-body/div/div/div[2]/div[2]/mat-form-field/div/div/div/input");
    By xmlEntreCalle = By.xpath("//mat-sidenav-content[@id=\"content-nav\"]/app-address-detail/div/div/mat-tab-group/div/mat-tab-body/div/div/div[2]/div[3]/mat-form-field/div/div/div/input");
    By xmlyEntreCalle = By.xpath("//mat-sidenav-content[@id=\"content-nav\"]/app-address-detail/div/div/mat-tab-group/div/mat-tab-body/div/div/div[2]/div[4]/mat-form-field/div/div/div/input");
    By xmlNomContacto = By.xpath("//mat-sidenav-content[@id=\"content-nav\"]/app-address-detail/div/div/mat-tab-group/div/mat-tab-body/div/div/div[2]/div[5]/mat-form-field/div/div/div/input");
    By xmlColonia = By.xpath("//*[@id=\"mat-input-70\"]");
    By xmlTelContacto = By.xpath("//mat-sidenav-content[@id=\"content-nav\"]/app-address-detail/div/div/mat-tab-group/div/mat-tab-body/div/div/div[2]/div[6]/mat-form-field/div/div/div/input");
    By xmlComentario = By.xpath("//*[@id=\"mat-input-77\"]");
 
    // Localizador Boton Guardar
    By xmlBtnGuardar = By.xpath("//*[@id=\"mat-tab-content-13-0\"]/div/button[1]/span[1]");
 
    // Localizador Boton Confirmar Guardar
    By xmlBtnConfirGuardar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    // Paso 6
 
 // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion Tienda
    String StrCheckTienda = ("//div/table/tbody/tr/td[2]/a/span[contains(text(),'%s')]/ancestor::td/preceding-sibling::td/mat-checkbox");
                             //div/table/tbody/tr/td[2]/a/span[contains(text(),'3501198')]/ancestor::td/preceding-sibling::td/mat-checkbox                                                 
  //div/table/tbody/tr/td[2]/a/span[contains(text(),'3501199')]/ancestor::td/preceding-sibling::td/mat-checkbox
    
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Modificacion_campo_Dir_envio(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para dar clic el Menu Pedidos Admin, SubMenu Consulta de Direccion de Envio, buscar un Nombre de Direccion, Seleccionarla y dar clic en boton Guardar
	 * 
	 * @author Manuel CUen
	 * @param nombreDir, nombreAmodif--->es necesario alimentarlos del data provider, para realizar prueba de modificacion existosa
	 * @return ninguno
	 */

	public void clicModificacionDirEnv(String nombreDir, String nombreAmodif, String cp, String calle, String numero, String entreCalle, String yEntreCalle, String nomContacto, String colonia, String telContacto, String comentario, String negocio) {
		
		if(negocio.toUpperCase().equals("MARCA")) {
			u.highLight(xmlMenuPedAdminM);
			u.click(xmlMenuPedAdminM);
		} else if(negocio.toUpperCase().equals("OXXO")) {
			u.highLight(xmlMenuPedAd);
			u.click(xmlMenuPedAd);
		}

		u.highLight(xmlSbConsDirEnv);
		u.click(xmlSbConsDirEnv);
		
		u.mouseOver(xmlHeader);
		
		System.out.print(nombreDir);
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlNombreDir);
		u.sendKeys(xmlNombreDir, nombreDir);
		
		
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
	    u.hardWait(4);
	    
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    u.highLight(xmlBtnModificar);
	    u.click(xmlBtnModificar);
	    
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    u.getDriver().findElement(xmlNomDirModif).clear();
	    u.highLight(xmlNomDirModif);
	    u.sendKeys(xmlNomDirModif, nombreAmodif);
	    
	    u.getDriver().findElement(xmlCodigoP).clear();
		u.highLight(xmlCodigoP);
		u.sendKeys(xmlCodigoP, cp);
		
		u.getDriver().findElement(xmlCalle).clear();
		u.highLight(xmlCalle);
		u.sendKeys(xmlCalle, calle);
		
		u.getDriver().findElement(xmlNumero).clear();
		u.highLight(xmlNumero);
		u.sendKeys(xmlNumero, numero);
		
		u.getDriver().findElement(xmlEntreCalle).clear();
		u.highLight(xmlEntreCalle);
		u.sendKeys(xmlEntreCalle, entreCalle);
		
		u.getDriver().findElement(xmlyEntreCalle).clear();
		u.highLight(xmlyEntreCalle);
		u.sendKeys(xmlyEntreCalle, yEntreCalle);
		
		u.getDriver().findElement(xmlNomContacto).clear();
		u.highLight(xmlNomContacto);
		u.sendKeys(xmlNomContacto, nomContacto);
		
		u.getDriver().findElement(xmlColonia).clear();
		u.highLight(xmlColonia);
		u.sendKeys(xmlColonia, colonia);
		
		
		u.getDriver().findElement(xmlTelContacto).clear();
		u.highLight(xmlTelContacto);
		u.sendKeys(xmlTelContacto, telContacto);
		
		u.getDriver().findElement(xmlComentario).clear();
		u.highLight(xmlComentario);
		u.sendKeys(xmlComentario, comentario);
	    
	    u.highLight(xmlBtnGuardar);
	    u.click(xmlBtnGuardar);
	    
	    u.highLight(xmlBtnConfirGuardar);
	    u.click(xmlBtnConfirGuardar);
	    u.hardWait(2);
	    

	    
	    testCase.addScreenShotCurrentStep(u, "clicModificacionDirEnv");
		u.hardWait(2);
		
		u.unhighLight(xmlNomDirModif);
		u.unhighLight(xmlBtnGuardar);
		
	    
	}

}
