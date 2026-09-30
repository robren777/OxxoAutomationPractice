package WEB.inventarioEspacios.om;

import java.time.Duration;
import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Consulta_Movtos_Eliminacion_Tienda {

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
    By xmlMenusAd = By.xpath("//div[@class= \"texto-menu\"]/a[2]/div");
    By xmlMenusAdM = By.xpath("//div[@class= \"texto-menu\"]/a[4]/div");
    
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
    
    // Paso 6
 
 // Localizadores botones Eliminar y confirmar eliminacion
    By xmlBtnCancelar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/button");
    By xmlBtonEliminar = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[2]/button/span[1]");
    								 //*[@id="formFiltro"]/div[3]/div/div[3]/button/span[1]
    By xmlBtEliminarMarca = By.xpath("//*[@id=\"formFiltro\"]/div[3]/div/div[2]/button/span[1]"); 
    By xmlMensaje = By.xpath("//*[@id=\"mat-dialog-title-0\"]");
    By xmlConfEliminar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    By xmlLblTienda = By.xpath("//*[@id=\"titulo_gral\"]");
    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
 // Localizador checkbox Eliminacion Tienda
    String StrCheckTienda = ("//div/table/tbody/tr/td[2]/a/span[contains(text(),'%s')]/ancestor::td/preceding-sibling::td/mat-checkbox/label/span");
                             //div/table/tbody/tr/td[2]/a/span[contains(text(),'3501198')]/ancestor::td/preceding-sibling::td/mat-checkbox                                                 
  //div/table/tbody/tr/td[2]/a/span[contains(text(),'3501199')]/ancestor::td/preceding-sibling::td/mat-checkbox
    
    /**
	 * Constructor
	 ******************************************************************************************/
	public Consulta_Movtos_Eliminacion_Tienda(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Metodo para dar clic el Menu Administracion, SubMenu Tienda, buscar una Tienda por Descripcion, Seleccionarla y dar clic en boton Eliminar
	 * @author Manuel Cuen
	 * @param idTienda, DescripTienda--->es necesario alimentarlos del data provider, para realizar prueba existosa
	 * @return ninguno
	 */

	public void clicSbMenuTienda(String idTienda, String DescripTienda, String negocio) {
		
		if(negocio.toUpperCase().equals("BARA")) {
			
			u.highLight(xmlMenusAdM);
			u.click(xmlMenusAdM);
			
			u.highLight(xmlSbTdasMarca);
			u.click(xmlSbTdasMarca);
		} 
		
		if(negocio.toUpperCase().equals("OXXO") || negocio.toUpperCase().equals("MARCA")) {
			
			m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
			u.highLight(xmlMenusAd);
			u.click(xmlMenusAd);
			
			u.highLight(xmlSbTienda);
			u.click(xmlSbTienda);
		}
		
		u.mouseOver(xmlHeader);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlDescripTienda);
		u.sendKeys(xmlDescripTienda, DescripTienda);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		if(negocio.toUpperCase().equals("MARCA")) {
			u.highLight(xmlBtnBuscarMarca);
			u.click(xmlBtnBuscarMarca);
		} else  {
			u.highLight(xmlBtnBuscar);
			u.click(xmlBtnBuscar);
			u.hardWait(4);
		}
		
	    clickSeleccionTdaEliminar(idTienda);

	    if(negocio.toUpperCase().equals("MARCA")) {
	    	u.highLight(xmlBtEliminarMarca);
	    	u.click(xmlBtEliminarMarca);
	    } else  {	
	    	clicBtnEliminar();
	    }

	    clickBtnConfirmarEliminar();
	    
	    testCase.addScreenShotCurrentStep(u, "clicSbMenuSbPlaza");
		u.hardWait(2);
		
		u.highLight(xmlDescripTienda);
		if(negocio.toUpperCase().equals("MARCA")) {
			u.unhighLight(xmlBtnBuscarMarca);
		} else {
			u.unhighLight(xmlBtnBuscar);
		}
	    
	}
	
	// Da clic en el checkbox de la tienda a eliminar
    public void clickSeleccionTdaEliminar(String idTienda) {
    	
    	System.out.println(StrCheckTienda);
	    System.out.println(idTienda);
	    By LocTiendaId = By.xpath(String.format(StrCheckTienda, idTienda));
	    u.hardWait(5);
	    u.click(LocTiendaId);
	    u.hardWait(2);
	    
	    testCase.addScreenShotCurrentStep(u, "clickSeleccionTdaEliminar");
		u.hardWait(2);
    }
    
    // Resalta el boton de "Eliminar", que aparece despues de dar click en el checkbox de la tienda
    public void muestraBotonEliminar() {
    	
    	 u.highLight(xmlBtonEliminar);
    	 
    	 testCase.addScreenShotCurrentStep(u, "muestraBotonEliminar");
 		 u.hardWait(2);
    	
 		 u.unhighLight(xmlBtonEliminar);
    }
    
    // Da click en el boton eliminar
    public void clicBtnEliminar() {
    	
	    u.highLight(xmlBtonEliminar);
	    u.click(xmlBtonEliminar);
	    
   	 	testCase.addScreenShotCurrentStep(u, "clicBtnEliminar");
		 u.hardWait(2);
		 
		 u.unhighLight(xmlBtonEliminar);
    }
    
    // Resalta el mensaje y los botones cancelar y confirmar, cuando se da el click en el boton eliminar
   public void clickMuestraMsj() {
	   
	   u.highLight(xmlMensaje);
	   u.highLight(xmlBtnCancelar);
	   u.highLight(xmlConfEliminar);
	   u.hardWait(2);
  	 	
	   testCase.addScreenShotCurrentStep(u, "clickMuestraMsj");
	   u.hardWait(2);

		 u.unhighLight(xmlMensaje);
		 u.unhighLight(xmlBtnCancelar);
		 u.unhighLight(xmlConfEliminar);
		
   }
   
   // Da click en el boton de confirmacion al dar click en el boton eliminar
   public void clickBtnConfirmarEliminar() {
	   
	    u.highLight(xmlConfEliminar);
	    u.click(xmlConfEliminar);
	    u.hardWait(2);

		testCase.addScreenShotCurrentStep(u, "clickBtnConfirmarEliminar");
		u.hardWait(2);

   }
   
   // Da click en el boton cancelar, que aparece junto con el boton confirmar, al hacer click en el boton eliminar
   public void clickBtnCancelar() {
	   
	   u.highLight(xmlBtnCancelar);
	   testCase.addScreenShotCurrentStep(u, "clickBtnCancelar");
	   u.hardWait(2);
	   u.click(xmlBtnCancelar);
	   u.hardWait(2);
	   
	   testCase.addScreenShotCurrentStep(u, "clickBtnCancelar");
	   u.hardWait(2);
	   
   }
   
   // Da click en la etiqueta de Tienda de la pantalla de seleccion de tiendas, para evidenciar esa pantalla activa
   public void clickPantallaTienda() {
	   m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	   
	   u.highLight(xmlLblTienda);
	   u.hardWait(2);
	   
	   testCase.addScreenShotCurrentStep(u, "clickPantallaTienda");
	   u.hardWait(2);
	   
	   u.unhighLight(xmlLblTienda);
   }
}
