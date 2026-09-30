package WEB.inventarioEspacios.om;

import java.time.Duration;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class SubPlaza_IEP {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	/*
	 * Localizadores Pantalla Detalle - SubPlaza
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
          
    By xmlHeader = By.xpath("//mat-sidenav-content[@id='content-nav']/app-header");
  
    //                        PASO 6
    By xmlSubPlaza = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[11]/a/div/label");
                              
    
    //                        PASO 7
    By xmlNombreSubPlaza = By.xpath("//*[@id=\"formFiltro\"]/div[1]/div/div/mat-form-field/div");
    By xmlBtnNuevo = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div/div[1]/button");
    By xmlBtnLimpiar = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div/div[2]/button");
    By xmlBuscar = By.xpath("//*[@id=\"formFiltro\"]/div[2]/div/div[3]/button");
    
   
    //                        PASO 8
    By xmlTxtSubplaza = By.xpath("//*[@id=\"mat-input-0\"]");
    
    // Localizadores del menu administracion plaza y submenu subplazas
    By xmlMenuAdminPlaza = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[2]/div/label");
    By xmlSubplaza = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[2]/a/div/div[1]");
    						  
        //                        PASO 9
    By xmlLblIdTbl       = By.xpath("//*[@id=\"content-nav\"]/app-subplazas/div/div/div/table/thead/tr/th[2]");
    By xmlLblNombreTbl   = By.xpath("//*[@id=\"content-nav\"]/app-subplazas/div/div/div/table/thead/tr/th[3]");
    By xmlLblCvePlazaTbl = By.xpath("//*[@id=\"content-nav\"]/app-subplazas/div/div/div/table/thead/tr/th[4]");
    By xmlLblNomPlz      = By.xpath("//*[@id=\"content-nav\"]/app-subplazas/div/div/div/table/thead/tr/th[5]");
    By xmlBtnEditTbl     = By.xpath("//*[@id=\"content-nav\"]/app-subplazas/div/div/div/table/tbody/tr[1]/td[6]/mat-icon");
   
    //                        PASO 10
       // Se Aplico solo el boton Limpiar, arriba ya se saco el Xpath de el
    //                        PASO 11
    By xmlNombre = By.xpath("//*[@id=\"mat-input-1\"]");
    By xmlPlaza = By.xpath("//*[@id=\"mat-select-value-3\"]/span");
    By xmlBtnCancelar = By.xpath("//*[@id=\"content-nav\"]/app-captura-edicion-subplazas/div/div/div[2]/div/div[1]/button/span[1]");
    By xmlBtnLimpiarNvo = By.xpath("//*[@id=\"content-nav\"]/app-captura-edicion-subplazas/div/div/div[2]/div/div[2]/button/span[1]");
    By xmlBtnGuardar = By.xpath("//*[@id=\"content-nav\"]/app-captura-edicion-subplazas/div/div/div[2]/div/div[2]/button/span[1]");
    							//*[@id="content-nav"]/app-captura-edicion-subplazas/div/div/div[2]/div/div[3]/button/span[1]							
    //                        PASO 12
    //                        PASO 13
    //                        PASO 14
    By xmlBtnGuardarNvo = By.xpath("//*[@id=\"content-nav\"]/app-captura-edicion-subplazas/div/div/div[2]/div/div[3]/button/span[1]");
    /*
     * Variable que reciben un parametro
     * ***************************************************************************************/
    String SrtPlaza = ("//div[@id=\"mat-select-2-panel\"]/mat-option/span[contains(text(),' %s ')]");
    				   //div[@id="mat-select-2-panel"]/mat-option/span[contains(text(),' Monterrey - 10MON ')]
    String SrtBtnEditar = ("//table/tbody/tr/td[contains(text(),'%s')]//following::td[4]/mat-icon");
    /**
	 * Constructor
	 ******************************************************************************************/
	public SubPlaza_IEP(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Metodo:  Para dar clic en la opcion de SubPlaza
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 */
	public void clickSubPlaza() {
		
		u.highLight(xmlSubPlaza);
		u.click(xmlSubPlaza);
		testCase.addScreenShotCurrentStep(u, "clickSubPlaza");
		
		u.unhighLight(xmlSubPlaza);
	}
	
	public void clickSubplazasPlaza() {
		
		u.highLight(xmlMenuAdminPlaza);
		u.click(xmlMenuAdminPlaza);
		
		
		u.highLight(xmlSubplaza);
		u.hardWait(2);
		//u.click(xmlSubplaza);
		u.mouseOverAndClick(xmlSubplaza);
		
		u.mouseOver(xmlHeader);
		
		testCase.addScreenShotCurrentStep(u, "clickSubPlaza");
		u.hardWait(2);
		
	}

	/**
	 * Metodo:  Para Mostrar los campos de la pantalla SubPlaza
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 */
    public void clickCampos() {
	   
       me.explicitWaitToBeInvisible(Duration.ofSeconds(20));
       u.click(xmlHeader);
	   u.hardWait(2);
	   u.highLight(xmlNombreSubPlaza);
	   u.highLight(xmlBtnNuevo);
	   u.highLight(xmlBtnLimpiar);
	   u.highLight(xmlBuscar);
	   testCase.addScreenShotCurrentStep(u, "clickCampos");
	
	   u.unhighLight(xmlNombreSubPlaza);
	   u.unhighLight(xmlBtnNuevo);
	   u.unhighLight(xmlBtnLimpiar);
	   u.unhighLight(xmlBuscar);
    }

	/**
	 * Metodo:  Para llenar el nombre de una SubPlaza deseada a buscar Descripcion/Nombre
	 * @author: Manuel Cuen
	 * @param:  SubPlaza, Es necesario llenar este campo en su data provider
	 */
    public void clickIngresaFiltroBuscar(String subplaza) {
	
	   u.highLight(xmlTxtSubplaza);
	   u.sendKeys(xmlTxtSubplaza, subplaza);
	   testCase.addScreenShotCurrentStep(u, "IngresaFiltroBuscar");
	
	   u.unhighLight(xmlTxtSubplaza);
    }
    
	/**
	 * Metodo:  Para dar clic en el boton buscar una Subplaza, capturada previamente
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 */
    public void clickBtnBuscar() {
    	
    	u.highLight(xmlBuscar);
    	u.click(xmlBuscar);
    	testCase.addScreenShotCurrentStep(u, "clickBtnBuscar");
    	
    	u.unhighLight(xmlBuscar);
    	
    }
    
	/**
	 * Metodo:  Para Resaltar las Etiquetas y botones en la Tabla/Grid SubPlaza
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 */
    public void clickResPasoNueve() {
    	
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlLblIdTbl);
    	u.highLight(xmlLblNombreTbl);
    	u.highLight(xmlLblCvePlazaTbl);
    	u.highLight(xmlLblNomPlz);
    	u.highLight(xmlBtnEditTbl);
    	testCase.addScreenShotCurrentStep(u, "ResPasoNueve");
    	
    	u.unhighLight(xmlLblIdTbl);
    	u.unhighLight(xmlLblNombreTbl);
    	u.unhighLight(xmlLblCvePlazaTbl);
    	u.unhighLight(xmlLblNomPlz);
    	u.unhighLight(xmlBtnEditTbl);
    }
    
	/**
	 * Metodo:  Para dar clic en el boton Limpiar
	 * @author: Manuel Cuen
	 * @param:  Cont, Se utiliza solo para controlar la aparicion de botones en las pantallas "Nuevo" y "Editar"
	 */
    public void clickBtnLimpiar(Boolean cont) {
    	if(cont == false) {
        	u.highLight(xmlBtnLimpiar);
        	u.click(xmlBtnLimpiar);
        	testCase.addScreenShotCurrentStep(u, "clickBtnLimpiar");
        	
        	u.unhighLight(xmlBtnLimpiar);
    	}

    	if(cont == true) {
        	u.highLight(xmlBtnLimpiarNvo);
        	u.click(xmlBtnLimpiarNvo);
        	testCase.addScreenShotCurrentStep(u, "clickBtnLimpiar");
        	
        	u.unhighLight(xmlBtnLimpiarNvo);
    	}
    	
    }
    
	/**
	 * Metodo:  Para dar clic en la opcion
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 */
    public void clickLimpiaFiltros() {
    	
    	 u.highLight(xmlNombreSubPlaza);
    	 clickResPasoNueve();
    	 
    	 u.unhighLight(xmlNombreSubPlaza);
    }
    
	/**
	 * Metodo:  Para dar clic en el boton "Nuevo"
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 */
    public void clickBtnNuevo() {
    	
    	u.highLight(xmlBtnNuevo);
    	u.click(xmlBtnNuevo);
    	
    	testCase.addScreenShotCurrentStep(u, "clickBtnNuevo");
    	u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para Mostrar los campos de la pantalla "Nuevo" de la opcion SubPlaza
	 * @author: Manuel Cuen
	 * @param:  band, esta variable se utiliza solo para controlar el orden y aparicion de campos/botones en las pantallas de Editar/Nuevo
	 */
    public void clickNuevaPantalla(Boolean band) {
    	
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlNombre);
    	u.highLight(xmlPlaza);
    	u.highLight(xmlBtnCancelar);
    	u.highLight(xmlBtnLimpiarNvo);
    	if (band == false) {

    		u.highLight(xmlBtnGuardarNvo);
    	}
    	u.highLight(xmlBtnGuardar);
    	
    	testCase.addScreenShotCurrentStep(u, "clickNuevaPantalla");
    	u.hardWait(2);
    	
    	u.unhighLight(xmlNombre);
    	u.unhighLight(xmlPlaza);
    	u.unhighLight(xmlBtnCancelar);
    	u.unhighLight(xmlBtnLimpiarNvo);
    	if (band == false) {
    		u.unhighLight(xmlBtnGuardarNvo);
    	}
    	u.unhighLight(xmlBtnGuardar);
    }

	/**
	 * Metodo:  Para Llenar los campos Nombre y Plaza
	 * @author: Manuel Cuen
	 * @param:  Nombre,Plaza, es necesario que se llenen en el data provider
	 */
    public void clickLlenaCampos(String Nombre, String Plaza) {
    	
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlNombre);
    	
    	u.sendKeys(xmlNombre, Nombre);
    	u.click(xmlPlaza);
    	u.hardWait(2);
    	
    	By LocPlaza= By.xpath(String.format(SrtPlaza, Plaza));
    	u.click(LocPlaza);
    	u.hardWait(2);
    	
    	testCase.addScreenShotCurrentStep(u, "clickLlenaCampos");
    	u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para Llenar los campos Nombre y Plaza en la pantalla edicion
	 * @author: Manuel Cuen
	 * @param:  Nombredos,PlazaDos, es necesario que se llenen en el data provider
	 */
    public void clickLlenaCamposDos(String Nombredos, String PlazaDos) {
    	
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlNombre);
    	u.getDriver().findElement(xmlNombre).clear();
    	u.sendKeys(xmlNombre, Nombredos);
    	
    	u.click(xmlPlaza);
    	u.hardWait(2);
    	
    	By LocPlaza= By.xpath(String.format(SrtPlaza, PlazaDos));
    	u.getDriver().findElements(LocPlaza).clear();
    	u.click(LocPlaza);
    	u.hardWait(2);
    	
    	testCase.addScreenShotCurrentStep(u, "clickLlenaCamposDos");
    	u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para dar clic en el boton cancelar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 */
    public void clickBtnCancelar() {
    	
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlBtnCancelar);
    	u.click(xmlBtnCancelar);
    	
    	testCase.addScreenShotCurrentStep(u, "clickBtnCancelar");
    	u.hardWait(2);
    }
    
	/**
	 * Metodo:  Para dar clic en el boton guardar
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 */
    public void clickBtnGuardar(Boolean flag) {
    	
    	me.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	if (flag == false) {
    		u.highLight(xmlBtnGuardarNvo);
    		u.click(xmlBtnGuardarNvo);
    	}
    	if (flag == true) {
    		u.highLight(xmlBtnGuardar);
    		u.click(xmlBtnGuardar);
    	}
    	u.hardWait(2);
    	
    	testCase.addScreenShotCurrentStep(u, "clickBtnGuardar");
    	u.hardWait(2);

    }
    
	/**
	 * Metodo:  Para dar clic en el icono editar una SubPlaza, por numero de Id SubPlaza
	 * @author: Manuel Cuen
	 * @param:  idSubPlaza, es necesario llenar este campo, en el data provider, con el cual se editara
	 */
    public void clickBtnEditar(String idSubPlaza) {
    	
    	By LocBtnEditar= By.xpath(String.format(SrtBtnEditar, idSubPlaza));
    	u.click(LocBtnEditar);
    	
    	testCase.addScreenShotCurrentStep(u, "clickBtnGuardar");
    	u.hardWait(2);
    }
}

