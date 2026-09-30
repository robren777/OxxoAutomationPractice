package WEB.inventarioEspacios.om;

import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.By;
import com.ibm.icu.impl.Assert;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Proveedores_Boton_Buscar_Adm_OXXO {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;
	/*
	 * Localizadores 
	 ******************************************************************************************/
	By xmlUsuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By xmlNegocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By xmlPerfil = By.xpath("");  
    
    //Menu Admon
    By xmlMenusAd = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/a[1]/div");
    
    //SubMenu Proveedores
    By xmlProveedor = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[3]/div/mat-list/div[2]/a/div/label");
 
    // encabezado
    By xmlHeader = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/mat-icon");
 
    // Filtros 
    By xmlNomProveedor = By.xpath("//*[@id=\"mat-input-0\"]");
    By xmlBtnNuevo = By.xpath("//*[@id=\"formFiltroProveedor\"]/div[2]/div/div[1]/button/span");
    By xmlBtnLimpiar = By.xpath("//*[@id=\"formFiltroProveedor\"]/div[2]/div/div[2]/button/span");
    By xmlBtnBuscar = By.xpath("//*[@id=\"formFiltroProveedor\"]/div[2]/div/div[3]/button/span");
    
    // Grid
    By xmlLblIdProv = By.xpath("//*[@id=\"content-nav\"]/app-supplier/div/div/table/thead/tr/th[2]");
    By xmlLblNombre = By.xpath("//*[@id=\"content-nav\"]/app-supplier/div/div/table/thead/tr/th[3]");
    By xmlLblEmail = By.xpath("//*[@id=\"content-nav\"]/app-supplier/div/div/table/thead/tr/th[4]");
    By xmlBtnEditar = By.xpath("//*[@id=\"content-nav\"]/app-supplier/div/div/table/tbody/tr[1]/td[5]/mat-icon");
    
    // Pie del Grid
    By xmlPiePag = By.xpath("//*[@id=\"content-nav\"]/app-supplier/div/mat-paginator/div/div");
    
    // Pantalla nuevo proveedor
    By xmlNomProvedorNvo = By.xpath("//*[@id=\"mat-input-1\"]");
    By xmlEmail = By.xpath("//*[@id=\"mat-input-2\"]");
    By xmlBtnCancelar = By.xpath("//*[@id=\"content-nav\"]/app-supplier-detail/div/div/div[2]/div/div[1]/button/span");
    By xmlBtnLimpiarNvo = By.xpath("//*[@id=\"content-nav\"]/app-supplier-detail/div/div/div[2]/div/div[2]/button/span[1]");                                       
    By xmlBtnGuardarNvo = By.xpath("//*[@id=\"content-nav\"]/app-supplier-detail/div/div/div[2]/div/div[3]/button/span[1]");
    By xmlBtnGuardar = By.xpath("//*[@id=\"content-nav\"]/app-supplier-detail/div/div/div[2]/div/div[2]/button/span[1]"); 
    By xmlCorfirGuardar = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");
    By xmlCorfirGuardarDos = By.xpath("//*[@id=\"mat-dialog-1\"]/app-dialog-confirm/div[2]/div/div/div/button/span[1]");   
    By xmlMsjConfGuardar = By.xpath("//*[@id=\"mat-dialog-title-0\"]");
    By xmlMsjConfGuardarDos = By.xpath("//*[@id=\"mat-dialog-title-1\"]");
    By xmlBtnCancelarMsjConf = By.xpath("//*[@id=\"mat-dialog-0\"]/app-dialog-confirm/div[2]/div/div/button/span[1]");
    By xmlBtnCancMsjConfDos = By.xpath("//*[@id=\"mat-dialog-1\"]/app-dialog-confirm/div[2]/div/div/button/span[1]");                                    
    /*
     * Variables que reciben un parametro
     * *******************************************************************************************/
    String StrChkProv =("//table/tbody/tr/td[2][contains(text(),' %s ')]"); //following-sibling::td[3]/mat-icon  
    String StrChkElimProv =("//table/tbody/tr/td[contains(text(),'%s')]//preceding-sibling::td/mat-checkbox");
    
 // Localizadores de Avance de Pagina para Seleccion Multiple sin parametro
    By xmlBtnNextPageDisable     = By.xpath("//div[@class=\"mat-paginator-container\"]/div[2]/button[3][@disabled=\"true\"]");
    By xmlBtnPreviousPageDisable = By.xpath("//div[@class=\"mat-paginator-container\"]/div[2]/button[2][@disabled=\"true\"]");										
    By xmlBtnNextPage = By.xpath("//mat-paginator/div/div/div[2]/button[3]/span[1]");
    							//*[@id=\"content-nav\"]/app-reglas/div/mat-paginator/div/div/div[2]/button[3]/span[1] 
    /**
	 * Constructor
	 ******************************************************************************************/
	public Proveedores_Boton_Buscar_Adm_OXXO(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Método Accesar al menu y submenu Provedores
	 * @author Manuel Cuen
	 * @param Ninguno
	 * @return ninguno
	 */
	public void clickMenuSbProveedores() {
		
		u.highLight(xmlMenusAd);
		u.click(xmlMenusAd);
		
		u.highLight(xmlProveedor);
		u.click(xmlProveedor);
		
		u.mouseOver(xmlHeader);
		u.hardWait(2);
		
		testCase.addScreenShotCurrentStep(u, "clickMenuSbProveedores");
		u.hardWait(2);
		
	}
	
	/**
	 * Método Resalta el Filtro Nombre de proveedor y botones
	 * @author Manuel Cuen
	 * @param Ninguno
	 * @return ninguno
	 */
	public void clickPantProveedor() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlNomProveedor);
		u.highLight(xmlBtnNuevo);
		u.highLight(xmlBtnLimpiar);
		u.highLight(xmlBtnBuscar);
		
		testCase.addScreenShotCurrentStep(u, "clickPantProveedor");
		u.hardWait(2);
		u.unhighLight(xmlNomProveedor);
		u.unhighLight(xmlBtnNuevo);
		u.unhighLight(xmlBtnLimpiar);
		u.unhighLight(xmlBtnBuscar);
	}
	
	/**
	 * Método Resalta etiquetas del Grid proveedores
	 * @author Manuel Cuen
	 * @param Ninguno
	 * @return ninguno
	 */
	public void clickGridProveedor() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlLblIdProv);
		u.highLight(xmlLblNombre);
		u.highLight(xmlLblEmail);
		u.highLight(xmlBtnEditar);
		
		testCase.addScreenShotCurrentStep(u, "clickGridProveedor");
		u.hardWait(2);
		
		u.unhighLight(xmlLblIdProv);
		u.unhighLight(xmlLblNombre);
		u.unhighLight(xmlLblEmail);
		u.unhighLight(xmlBtnEditar);
	}
	
	/**
	 * Método Captura Nombre de Proveedor Inexistente
	 * @author Manuel Cuen
	 * @param NomProveedor, Es necesario capturarlo en su Data Provider
	 * @return ninguno
	 */
	public void clickCapturaProv(String NomProveedor) {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlNomProveedor);
		u.sendKeys(xmlNomProveedor, NomProveedor);
		
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
		
		testCase.addScreenShotCurrentStep(u, "clickCapturaProv");
		u.hardWait(2);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.unhighLight(xmlNomProveedor);
		u.unhighLight(xmlBtnBuscar);
	}
	
	public void clickMensaje() {
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.highLight(xmlPiePag);
		
		testCase.addScreenShotCurrentStep(u, "clickMensaje");
		u.hardWait(2);
		
		u.unhighLight(xmlPiePag);
	}
	
	/**
	 * Método Captura Nombre de Proveedor Existente
	 * @author Manuel Cuen
	 * @param NomProvExi, Es necesario capturarlo en su Data Provider
	 * @return ninguno
	 */
    public void clickCapProvExis(String NomProvExi) {
		
    	m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
    	u.highLight(xmlNomProveedor);
		u.getDriver().findElement(xmlNomProveedor).clear();
		u.sendKeys(xmlNomProveedor, NomProvExi);
		
		u.highLight(xmlBtnBuscar);
		u.click(xmlBtnBuscar);
		
		testCase.addScreenShotCurrentStep(u, "clickCapProvExis");
		u.hardWait(2);
		
		m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
		u.unhighLight(xmlNomProveedor);
		u.unhighLight(xmlBtnBuscar);
	}
    
	/**
	 * Método Limpia la pantalla
	 * @author Manuel Cuen
	 * @param Ninguno
	 * @return ninguno
	 */
    public void clickBtnLimpiar() {
    	
    	u.highLight(xmlBtnLimpiar);
    	u.click(xmlBtnLimpiar);
    	u.hardWait(2);
    	
    	testCase.addScreenShotCurrentStep(u, "clickBtnLimpiar");
		u.hardWait(2);
    }
    
	/**
	 * Método Entra a la pantalla de Captura un nuevo Nombre de Proveedor 
	 * @author Manuel Cuen
	 * @param  Ninguno
	 * @return Ninguno
	 */
   public void clickBtnNuevo() {
	   
	   u.highLight(xmlBtnNuevo);
	   u.click(xmlBtnNuevo);
	   u.hardWait(2);
   	
   	   testCase.addScreenShotCurrentStep(u, "clickBtnNuevo");
	   u.hardWait(2);
   }
   
	/**
	 * Método: Muestra la pantalla con filtro y botones
	 * @author Manuel Cuen
	 * @param  Ninguno
	 * @return Ninguno
	 */
   public void clickPanNuevoProv(boolean cont) {
	   
	   m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	   u.highLight(xmlNomProvedorNvo);
	   u.highLight(xmlEmail);
	   u.highLight(xmlBtnCancelar);
	   u.highLight(xmlBtnLimpiarNvo);
	   if (cont == false) {
		   u.highLight(xmlBtnGuardar);
	   }
	   
	   if (cont == true ) {
		   u.highLight(xmlBtnGuardarNvo);  
	   }
	  
	   
	   testCase.addScreenShotCurrentStep(u, "clickPanNuevoProv");
	   u.hardWait(2);
	   
	   u.unhighLight(xmlNomProvedorNvo);
	   u.unhighLight(xmlEmail);
	   u.unhighLight(xmlBtnCancelar);
	   u.unhighLight(xmlBtnLimpiarNvo);
	   u.unhighLight(xmlBtnGuardar);
   }
 
	/**
	 * Método: Captura el nombre de nuevo proveedor
	 * @author Manuel Cuen
	 * @param  NomProveedor, necesario introducirlo en su data provider
	 * @return Ninguno
	 */
   public void clickCapturaProvNvo(String NomProveedor) {
	   
	   m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	   u.highLight(xmlNomProvedorNvo);
	   u.sendKeys(xmlNomProvedorNvo, NomProveedor);
	   u.hardWait(2);
	   
	   u.unhighLight(xmlNomProvedorNvo);
   }
   
	/**
	 * Método: Captura un Email del nuevo proveedor
	 * @author Manuel Cuen
	 * @param  email,emailCorrec,cont necesario introducirlo en su data provider, "band" es solo para saber que dato va introducir
	 * @return Ninguno
	 */
   public void clickEmail(String email, String emailCorrec, boolean band) {
	   System.out.println(band);
	   if (band == false) {
		   u.highLight(xmlEmail);
		   u.sendKeys(xmlEmail, email);
           
		   u.unhighLight(xmlEmail);
           
	   }
	   
	   if (band == true) {
		   
		   u.getDriver().findElement(xmlEmail).clear();
		   u.highLight(xmlEmail);
		   u.sendKeys(xmlEmail, emailCorrec);
           
		   u.unhighLight(xmlEmail);
	   }
   }
   
	/**
	 * Método: Para dar clic en el boton guardar
	 * @author Manuel Cuen
	 * @param  Ninguno
	 * @return Ninguno
	 */
   public void clickGuardar() {
	   
	   u.highLight(xmlBtnGuardar);
	   u.click(xmlBtnGuardar);
	   u.hardWait(2);
	   
	   testCase.addScreenShotCurrentStep(u, "clickGuardar");
	   u.hardWait(2);
	   
   }
   
   /**
	 * Método: Para guardar Mensaje de error al momento de guardar
	 * @author Manuel Cuen
	 * @param  Ninguno
	 * @return Ninguno
	 */
   public void clickMensajeError() {
	   
	   testCase.addScreenShotCurrentStep(u, "clickMensajeError");
	   u.hardWait(2); 
   }
   
   /**
	 * Método: Para Seleccionar un proveedor deseado a Editar
	 * @author Manuel Cuen
	 * @param  IdProveedor, necesario introducirlo en su data provider, en base a este Id, es el proveedor que editara
	 * @return Ninguno
	 */
   public void clickEditarProv(String IdProveedor) {
	   
	   m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	   
	   u.hardWait(3);
	   By LocEditarProv = By.xpath(String.format(StrChkProv, IdProveedor));
	   System.out.println(LocEditarProv);
	   u.click(LocEditarProv);

   }
   
   /**
	 * Método: Para dar clic en el boton confirmar del boton guardar
	 * @author Manuel Cuen
	 * @param  Ninguno
	 * @return Ninguno
	 */
   public void clickConfirGuardar() {
       
	   m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	   u.highLight(xmlCorfirGuardarDos);
	   u.click(xmlCorfirGuardarDos);
	   u.hardWait(2);
	   
	   testCase.addScreenShotCurrentStep(u, "clickConfirGuardar");
	   u.hardWait(2);
   }
   
   /**
	 * Método: Para dar clic en el boton cancelar
	 * @author Manuel Cuen
	 * @param  Ninguno
	 * @return Ninguno
	 */
   public void clickBtnCancelar() {
	   
	   u.unhighLight(xmlBtnGuardar);
	   
	   u.highLight(xmlBtnCancelarMsjConf);
	   u.click(xmlBtnCancelarMsjConf);
	   
	   testCase.addScreenShotCurrentStep(u, "clickBtnCancelar");
	   u.hardWait(2);

   }
   
   /**
	 * Método: Para Mostrar/Resaltar mensaje y botones de la pantalla de confirmar guardar
	 * @author: Manuel Cuen
	 * @param:  band, esta variable solo se utiliza para controlar el orden y elementos en la pantalla proveedores
	 * @return: Ninguno
	 */
   public void clickMsjConfirGuardar(Boolean bandera) {
	   
	   m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
	   if (bandera == false) {
		   u.highLight(xmlMsjConfGuardarDos);
		   u.highLight(xmlBtnCancMsjConfDos);
		   u.highLight(xmlCorfirGuardarDos);
		   u.hardWait(2);
		   
		   u.unhighLight(xmlMsjConfGuardarDos);
		   u.unhighLight(xmlBtnCancMsjConfDos);
		   u.unhighLight(xmlCorfirGuardarDos);

	   }
	   if (bandera == true) {
		   u.highLight(xmlMsjConfGuardar);
		   u.highLight(xmlBtnCancelarMsjConf);
		   u.highLight(xmlCorfirGuardar);
		   u.hardWait(2);
		   
		   u.unhighLight(xmlMsjConfGuardar);
		   u.unhighLight(xmlBtnCancelarMsjConf);
		   u.unhighLight(xmlCorfirGuardar);
	   }
	  
	   u.hardWait(2);
	   
	   testCase.addScreenShotCurrentStep(u, "clickMsjConfirGuardar");
	   u.hardWait(2);
	   
   }
   
   /**
	 * Método: Para guardar la imagen con el mensaje de Actualizacion de datos
	 * @author: Manuel Cuen
	 * @param:  Ninguno
	 * @return: Ninguno
	 */
   public void clickMensajeActualizado() {
	   
	   testCase.addScreenShotCurrentStep(u, "clickMsjConfirGuardar");
	   u.hardWait(2);
   }
   
   /**
    * Metodo para seleccionar mas de un Proveedor
    * @author: Manuel Cuen
    * @param: Proveedores(ArrayList<String>): Proveedor a seleccionar
    */
   public Boolean clickSeleccionMultipleProv (ArrayList <String> Proveedores) {
	   
	   m.explicitWaitToBeInvisible(Duration.ofSeconds(10));
	   Boolean isSelected = false, validacion = false;
	   int selectedProv = 0;
	   Boolean ciclo = false;
	   Boolean exist = false;
	   
	   u.highLight(xmlBtnNextPage);
	   
	   for(int i = 0; i < Proveedores.size(); i++) {
		   
		   String locProv = String.format(StrChkProv, Proveedores.get(i));
		   System.out.println(locProv);
		   By element = By.xpath(locProv);
		   System.out.println(element);
		   
		   
		   do {
			   if(m.existElement(element)==true) {
				   ciclo = false;
				   exist = true;
				   System.out.print("-se encontro en el Grid\n");
				   u.highLight(element);
				   String checkStr = locProv+"//preceding-sibling::td/mat-checkbox";
				   u.click(By.xpath(checkStr));
				   u.hardWait(2);
			   }

		   if(m.existElement(element)==false) {
			   if((m.existElement(xmlBtnNextPageDisable)==true)&&(m.existElement(xmlBtnPreviousPageDisable)==true)) {
				   ciclo = false;
				   System.out.print("-no existen el registro en el Grid\n");
				   //testCase.addTextEvidenceCurrentStep("No se encontro el tipo de pedido especificado");
				   Assert.fail("No se encontro el/los proveedor(es) especificado(s) en el Grid");

			   }else if(m.existElement(xmlBtnNextPageDisable)==false) {
				   ciclo = true;
				   u.click(xmlBtnNextPage);
				   m.explicitWaitToBeInvisible(Duration.ofSeconds(20));
				   System.out.print("-cambia a la siguiente pagina para seguir buscando\n");
			   }else {
				   ciclo = false;
				   System.out.print("-no existen el registro en el Grid\n");
				   //testCase.addTextEvidenceCurrentStep("No se encontro el tipo de pedido especificado");
				   Assert.fail("No se encontro el/los proveedor(es) especificado(s) en el Grid");
			   }
		   }
		   }while (ciclo);

		   
	   }
	   testCase.addScreenShotCurrentStep(u, "clickSeleccionMultipleProv");
	   u.hardWait(2);
	return validacion;

   }
   
   public void clickBuscaProveedor(String Nom_prov1) {
	 
	   u.highLight(xmlNomProveedor);
	   u.getDriver().findElement(xmlNomProveedor).clear();
	   u.sendKeys(xmlNomProveedor, Nom_prov1);
	   
	   u.highLight(xmlBtnBuscar);
	   u.click(xmlBtnBuscar);
	   
   }
   
   public void clickSeleccionProveedor(String IdProveedor) {
	   
	   u.hardWait(2);
	   By LocElimProv = By.xpath(String.format(StrChkElimProv, IdProveedor));
	   System.out.println(LocElimProv);
	   u.click(LocElimProv);
   }
}
