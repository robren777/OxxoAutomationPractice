package WEB.inventarioEspacios.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_SubPlaza {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_SubPlaza
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    // Localizador del Menu Admon
    By xmlMenusAd = By.xpath("//div[@class= \"texto-menu\"]/a[2]/div");
    By xmlMenusAdM = By.xpath("//div[@class= \"texto-menu\"]/a[4]/div");
    
    // Localizador SubMenu SubPlaza
    By xmlSubPlaza = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[11]/a/div/label");
 
    // Localizador de encabezado
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
  
    // Localizador del Nombre de SubPlaza
    By xmlNombreSbPlaza= By.xpath("//*[@id=\"mat-input-0\"]");
 
    // Localizador del Boton Buscar
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div/div[3]/button/span[1]");
    
 // Localizadores botones Eliminar y confirmar eliminacion
    By xmlMsjConfirElim = By.xpath("//*[@id=\"mat-dialog-title-0\"]");
    By xmlMsjBtnCancelar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/button/span[1]");
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div/div[2]/button/span[1]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion SubPlaza
    String StrIdSubPlaza = ("//table/tbody/tr/td[2][contains(text(),'%s')]/preceding-sibling::td/mat-checkbox");
                               
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_SubPlaza(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método: Para dar clic el Menu Administracion, SubMenu SubPlaza, buscar una subplaza por Id, seleccionarla y dar clic en boton eliminar
	 * @author: Manuel CUen
	 * @param:  idRegla, NomRegla--->es necesario alimentarlos del data provider, para realizar prueba existosa
	 * @return: Ninguno
	 */

	public void clicSbMenuSbPlaza(String idSubPlaza, String idNombreSbPlaza) {
		
		u.highLight(xmlMenusAd);
		u.click(xmlMenusAd);

		u.highLight(xmlSubPlaza);
		u.click(xmlSubPlaza);
		
		u.mouseOver(xmlHeader);
		
		u.highLight(xmlNombreSbPlaza);
		u.sendKeys(xmlNombreSbPlaza, idNombreSbPlaza);
		
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
	    u.hardWait(2);
	    
	    clickSelSbPlaza(idSubPlaza,idNombreSbPlaza);

	    clickConfirmarEliminar();
	    
	    testCase.addScreenShotCurrentStep(u, "clicSbMenuSbPlaza");
		u.hardWait(2);
		
		u.unhighLight(xmlNombreSbPlaza);
		u.unhighLight(xmlBtnBuscar);
		
	    
	}
	
	/**
	 * Método: Para seleccionar una subplaza y dar click en el boton eliminar
	 * @author: Manuel Cuen
	 * @param:  idSubPlaza, idNombreSbPlaza--->es necesario alimentarlos del data provider, para realizar prueba existosa
	 * @return: Ninguno
	 */
	public void clickSelSbPlaza(String idSubPlaza, String idNombreSbPlaza) {
		
	    By LocSbPlazaId = By.xpath(String.format(StrIdSubPlaza, idSubPlaza));
	    u.click(LocSbPlazaId);
	    u.hardWait(2);
	    
	    u.highLight(xmlBtonEliminar);
	    u.click(xmlBtonEliminar);


	    testCase.addScreenShotCurrentStep(u, "clicSbMenuSbPlaza");
		u.hardWait(2);
		u.unhighLight(xmlBtonEliminar);
	}
	
	/**
	 * Método: Para Mostrar mensaje y botones en ventana modal
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickVentanaModal() {
		
		u.highLight(xmlMsjConfirElim);
		u.highLight(xmlMsjBtnCancelar);
		u.highLight(xmlConfEliminar);
		u.hardWait(2);
		
	    testCase.addScreenShotCurrentStep(u, "clickVentanaModal");
		u.hardWait(2);
		u.unhighLight(xmlMsjConfirElim);
		u.unhighLight(xmlMsjBtnCancelar);
		u.unhighLight(xmlConfEliminar);

		
	}
	
	/**
	 * Método: Para dar clic en el boton confirmar eliminar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickConfirmarEliminar() {
		
	    u.highLight(xmlConfEliminar);
	    u.click(xmlConfEliminar);
	    u.hardWait(2);
	    
	    testCase.addScreenShotCurrentStep(u, "clickConfirmarEliminar");
		u.hardWait(2);

	}
	
	/**
	 * Método: Para guardar mensaje de eliminado
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickMsjEliminado() {
		
	    testCase.addScreenShotCurrentStep(u, "clickMsjEliminado");
		u.hardWait(2);
	}
	
	/**
	 * Método: Para dar clic en el boton cancelar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
	public void clickBtnCancelar() {
		
		u.highLight(xmlMsjBtnCancelar);
		u.click(xmlMsjBtnCancelar);
		
	    testCase.addScreenShotCurrentStep(u, "clickBtnCancelar");
		u.hardWait(2);
	}
}
