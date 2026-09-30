package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Modificacion_campo_Tienda {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores Pantalla Detalle - Consulta_Movtos_Eliminacion_Tienda
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    // Localizador del Menu Admon
    By xmlMenusAd = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[1]/div");
    
    // Localizador SubMenu Tienda
    By xmlSbTienda = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[6]/a/div/label");
    By xmlSbTdasMarca = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[1]/a/div/label");
 
    // Localizador de encabezado
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
  
    // Localizador  Descripcion de Tienda
    By xmlDescripTienda= By.xpath("//*[@id=\"mat-input-0\"]");
 
    // Localizador del Boton Buscar
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[4]/button/span[1]");
    By xmlBtnBuscarMarca = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button/span[1]");
    
    // Localizador del Boton modificar
    By xmlBtnModificar = By.xpath("//*[@id=\"content-nav\"]/app-tiendas/div/div/div/table/tbody/tr[1]/td[10]/mat-icon");
    								 
    
    By xmlNombreTienda = By.xpath("//*[@id=\"mat-input-14\"]");
    								 //*[@id="mat-input-44"]
    
    By xmlBtnGuardar = By.xpath("//*[@id=\"content-nav\"]/app-tienda-captura-edicion/div/div/div[2]/div/div[2]/button/span[1]");
    
    By xmlBtnConf = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    // Paso 6
 
 // Localizadores botones Eliminar y confirmar eliminacion
//    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[3]/button/span[1]");
//    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion Tienda
//    String StrCheckTienda = ("//div/table/tbody/tr/td[2]/a/span[contains(text(),'%s')]/ancestor::td/preceding-sibling::td/mat-checkbox");
    
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Modificacion_campo_Tienda(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método para dar clic el Menu Administracion, SubMenu Tienda, buscar una Tienda por Descripcion, Seleccionarla y dar clic en boton Eliminar
	 * 
	 * @author Manuel CUen
	 * @param idTienda, DescripTienda--->es necesario alimentarlos del data provider, para realizar prueba existosa
	 * @return ninguno
	 */

	public void clicModificacionTienda(String DescripTda, String NomTdaModif, String negocio) {
		
		u.highLight(xmlMenusAd);
		u.click(xmlMenusAd);

		if(negocio.toUpperCase().equals("MARCA")) {
			u.highLight(xmlSbTdasMarca);
			u.click(xmlSbTdasMarca);
		} else  {
			u.highLight(xmlSbTienda);
			u.click(xmlSbTienda);
		}
		
		u.mouseOver(xmlHeader);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlDescripTienda);
		u.sendKeys(xmlDescripTienda, DescripTda);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		if(negocio.toUpperCase().equals("MARCA")) {
			u.highLight(xmlBtnBuscarMarca);
			u.click(xmlBtnBuscarMarca);
		} else  {
			u.highLight(xmlBtnBuscar);
			u.click(xmlBtnBuscar);
			u.hardWait(4);
		}
	    u.hardWait(4);
	    
	    m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	    u.highLight(xmlBtnModificar);
        u.click(xmlBtnModificar);

        System.out.print(NomTdaModif);
        m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
        u.getDriver().findElement(xmlNombreTienda).clear();
        u.highLight(xmlNombreTienda);
	    u.sendKeys(xmlNombreTienda, NomTdaModif);
	    
	    u.highLight(xmlBtnGuardar);
	    u.click(xmlBtnGuardar);
	    
	    u.highLight(xmlBtnConf);
	    u.click(xmlBtnConf);
	    u.hardWait(2);
	    
	    testCase.addScreenShotCurrentStep(u, "clicModificacionTienda");
		u.hardWait(2);
		
	    
	}

}
