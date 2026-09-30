package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_Proveedor {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_Espacio
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    // Localizador del Menu Admon
    By xmlMenusAd = By.xpath("//div[@class= \"texto-menu\"]/a[1]/div");
    By xmlMenusAdM = By.xpath("//div[@class= \"texto-menu\"]/a[4]/div");
    
    // Localizador SubMenu Proveedores
    By xmlProveedor = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[2]/a/div/label");
    By xmlProveedorM = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[1]/a/div/label");
 
    // Localizador del encabezado
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
 
    // Localizador del nombre de proveedor
    By xmlNomProveedor = By.xpath("//*[@id=\"mat-input-0\"]");
 
    // Localizador del boton buscar
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroProveedor\"]/div[2]/div/div[3]/button/span[1]");
    
    // Localizador(es) de Mensaje y botones de confirmar Eliminar
    By xmlMsjConfirmar = By.xpath("//*[@id=\"mat-dialog-title-0\"]");
    By xmlBtnCancelar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/button/span");
    By xmlBtnConfirmar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span");
    // Paso 6
 
 // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltroProveedor\"]/div[2]/div/div[2]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
    // Localizador de Proveedor
    String StrChBoxProveedor = ("//table/tbody/tr/td[2][contains(text(),'%s')]/preceding-sibling::td/mat-checkbox");
    String StrChBoxProveedor2 = ("//table/tbody/tr/td[2][contains(text(),'%s')]/preceding-sibling::td/mat-checkbox");
    String StrChBoxProveedor3 = ("//table/tbody/tr/td[2][contains(text(),'%s')]/preceding-sibling::td/mat-checkbox");
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_Proveedor(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método Busca un proveedor con su filtro por nombre, lo selecciona por su Id y lo Elimina
	 * @author Manuel Cuen
	 * @param provedorId,NomProveedor, se deben capturar en el data provider, para poder buscar y eliminar el proveedor deseado
	 * @return ninguno
	 */
	public void clickSubMenuProveedor(String IdProveedor, String NomProveedor, String negocio) {
		
		if(negocio.toUpperCase().equals("OXXO")) {
			u.highLight(xmlMenusAd);
			u.click(xmlMenusAd);
			
			u.highLight(xmlProveedor);
			u.click(xmlProveedor);
		}
		
		if(negocio.toUpperCase().equals("MARCA")) {
//			u.highLight(xmlMenusAdM);
			u.click(xmlMenusAdM);
			
//			u.highLight(xmlProveedorM);
			u.click(xmlProveedorM);	
		}
		
		u.mouseOver(xmlHeader);
		u.hardWait(2);
		
		u.highLight(xmlNomProveedor);
		System.out.println(NomProveedor);
		u.sendKeys(xmlNomProveedor, NomProveedor);
		
		
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
		u.hardWait(2);

		clickSelProveedor(IdProveedor);
	    
	    clickBtnEliminar();
	    clickBtnConfirmarElim();
	    u.hardWait(2);
		
	    testCase.addScreenShotCurrentStep(u, "clickSubMenuProveedor");
		u.hardWait(2);
		
		u.unhighLight(xmlNomProveedor);
		u.unhighLight(xmlBtnBuscar);

	}

	/**
	 * Método selecciona un proveedor por su Id
	 * @author Manuel Cuen
	 * @param IdProveedor, se debe capturar en el data providery para poder seleccionarlo 
	 * @return ninguno
	 */
	public void clickSelProveedor(String IdProveedor) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		By LocProveedorId = By.xpath(String.format(StrChBoxProveedor, IdProveedor));
	    u.click(LocProveedorId);
	    
	    testCase.addScreenShotCurrentStep(u, "clickSelProveedor");
		u.hardWait(2);
	}
	
	/**
	 * Método selecciona 3 proveedores por su Id
	 * @author Manuel Cuen
	 * @param IdProveedor,IdProveedor2,IdProveedor3: Se deben capturar en el data provider y poder seleccionarlos 
	 * @return ninguno
	 */
	public void clickSelMultProv(String IdProveedor, String IdProveedor2, String IdProveedor3) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		By LocProveedorId = By.xpath(String.format(StrChBoxProveedor, IdProveedor));
	    u.click(LocProveedorId);
	    u.hardWait(2);
	    
		By LocProveedorId2 = By.xpath(String.format(StrChBoxProveedor2, IdProveedor2));
	    u.click(LocProveedorId2);
	    u.hardWait(2);

	    By LocProveedorId3 = By.xpath(String.format(StrChBoxProveedor3, IdProveedor3));
	    u.click(LocProveedorId3);
	    u.hardWait(2);
	    
	    testCase.addScreenShotCurrentStep(u, "clickSelMultProv");
		u.hardWait(2);
	}
	
	/**
	 * Método: Dar click al boton Eliminar
	 * @author: Manuel Cuen
	 * @param:  Ninguno 
	 * @return: ninguno
	 */
	public void clickBtnEliminar() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    u.highLight(xmlBtonEliminar);
	    u.click(xmlBtonEliminar);
	    u.hardWait(2);
		
	    testCase.addScreenShotCurrentStep(u, "clickBtnEliminar");
		u.hardWait(2);

	}
	
	/**
	 * Método: Dar click al boton Confirmar, para Eliminar Proveedor(es)
	 * @author: Manuel Cuen
	 * @param:  Ninguno 
	 * @return: ninguno
	 */
	public void clickBtnConfirmarElim() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlConfEliminar);
	    u.click(xmlConfEliminar);
	    u.hardWait(2);
	    
	    testCase.addScreenShotCurrentStep(u, "clickBtnConfirmarElim");
		u.hardWait(2);

	}
	
	/**
	 * Método: Guardar mensaje de Eliminacion con exito
	 * @author: Manuel Cuen
	 * @param:  Ninguno 
	 * @return: ninguno
	 */
	public void clickMsjConfElim() {
		
		 testCase.addScreenShotCurrentStep(u, "clickMsjConfElim");
		 u.hardWait(2);
	}
	
	/**
	 * Método: Resaltar el mensaje de "Esta seguro que desea elimiar" y sus botones
	 * @author: Manuel Cuen
	 * @param:  Ninguno 
	 * @return: ninguno
	 */
	public void clickMensajeConfirmar() {
		
		u.highLight(xmlMsjConfirmar);
		u.highLight(xmlBtnCancelar);
		u.highLight(xmlBtnConfirmar);
        u.hardWait(2);
	    
	    testCase.addScreenShotCurrentStep(u, "clickMensajeConfirmar");
		u.hardWait(2);
		u.unhighLight(xmlMsjConfirmar);
		u.unhighLight(xmlBtnCancelar);
		u.unhighLight(xmlBtnConfirmar);
	}
	
}
