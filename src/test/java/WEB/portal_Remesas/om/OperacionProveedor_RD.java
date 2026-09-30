package WEB.portal_Remesas.om;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.sikuli.script.FindFailed;
import org.testng.Assert;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class OperacionProveedor_RD {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	/*
	 * Localizadores Pantalla Genera Reporte por Montos
	 ******************************************************************************************/
	By usuario = By.xpath("//*[@id=\"content-nav\"]/app-header/mat-toolbar/div/div[1]/div[3]/p");
    By negocio = By.xpath("//*[@id=\"sidenav\"]/div/app-sidebar/mat-nav-list/div[1]/div/label[1]");
    By perfil = By.xpath("");
    
	/*
	 * Localizadores del modulo de Retiros de dinero - Operacion con Proveedor
	 ******************************************************************************************/
    By operacionProveedor = By.xpath("//*[@id=\"submenu2\"]/li[2]/a");
    
    By proveedorSelect = By.xpath("//div[@id='form1:proveedor']");
    By proveedorSelectOp = By.xpath("//div[@class=\"ui-selectonemenu-items-wrapper\"]/ul/li");
    
    By opciones = By.xpath("//tbody/tr/td/a");
    
    String opcionesStr = "//tbody/tr/td/a[text()='%s']";
    
    
    By uploadInput = By.name("frmCargaAccount:uploader_input");
    By loadArchivoTiendasBtn = By.xpath("//div[@id='frmCargaAccount:uploader']/div[1]/button[1]");
    By msjCarga = By.xpath("//span[@class='ui-messages-info-detail']");
    
    By plazaSelect = By.xpath("//div[@id='frmTiendasAgentes:plaza']/ul/li");
    By tiendaSelect = By.xpath("//div[@id='frmTiendasAgentes:tiendaCR']/ul/li");    
    By estatusTiendaDesplegar = By.xpath("//div[@id='frmTiendasAgentes:j_idt144']/div[3]");
    By estatusTiendaSelect = By.xpath("//div[@id='frmTiendasAgentes:j_idt144_panel']/div/ul/li");
    By generarReporteBtn = By.id("frmTiendasAgentes:generaReporte");
        
    By resultados = By.id("frmTiendasAgentes:tablaAgentesMG");
    
    
    
    By xmlProveedorRetiros = By.xpath("//*[@id=\"form1:proveedor_label\"]");//revisar donde usar este localizador
    By xmlEnlaceManual = By.xpath("//*[@id=\"form1:gridOutputPanel\"]/table/tbody/tr[1]/td[1]/a");
    By xmlNumeroTiendas = By.xpath("//*[@id=\"frmAltaManual:acordeon\"]/h3[3]/a");
    By xmlBtnExaminar = By.xpath("//*[@id=\"frmAltaManual:acordeon:uploader\"]/div[1]/span");
    By xmlArchivoCargado = By.xpath("//*[@id=\"frmAltaManual:acordeon:uploader\"]/div[2]/table/tbody/tr/td[2]");
    By xmlArchivoIncorrecto = By.xpath("//*[@id=\"frmAltaManual:acordeon:uploader\"]/div[2]/div/div/ul/li/span[1]");
  								
  
 // Variables que reciben un Parametro modulo de Retiros de dinero - Operacion con Proveedor
    String SrtOperadorProv = ("//*[@id=\"form1:proveedor_panel\"]/div/ul/li[5][contains(text(),'%s')]"); 
  
    /**
	 * Constructor
	 ******************************************************************************************/
	
    /**
	 * Constructor de la clase OperacionProveedor_RD
	 * @author Andrea Contreras
	 * @param u (SeleniumUtil): objeto de la clase 
	 * @param testCase (testCase): objeto de la clase
	 */
    public OperacionProveedor_RD(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
    }
	
	/**
	 * Metodo para dar click en la opcion 'Operaci�n con proveedor' del menu 'Retiro de Dinero'
	 * @author: Andrea Contreras
	 * @param:  null
	 * @return: null
	 */
	public void clickOperacionProveedorOp() {
		
		u.highLight(operacionProveedor);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "clickSeleccionarPortalRemesas");
		u.mouseOverAndClick(operacionProveedor);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u, "clickSeleccionarPortalRemesas");
	}
	
	/**
	 * Metodo para seleccionar proveedor
	 * @param entidad (String): proveedor a seleccionar
	 * @author Andrea Contreras
	 */
	public boolean seleccionarProveedor(String prov) {
		
		boolean existOption = false;
		me.explicitWaitToBeClickable(proveedorSelect, Duration.ofSeconds(90));
		u.click(proveedorSelect);
		
		List<WebElement> allOptions = u.getDriver().findElements(proveedorSelectOp);
		for (WebElement option : allOptions) {
			if (option.getText().equals(prov)) {
				u.hardWait(1);
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}
		testCase.addScreenShotCurrentStep(u, "seleccionaProveedor");
		
		return existOption;

	}
	
	/**
	 * Metodo para seleccionar una opcion de la pantalla 'Operaci�n con proveedor'
	 * @param opcion (String): opcion a seleccionar
	 * @author Andrea Contreras
	 */
	public boolean seleccionarOpcion(String opcion) {
		
		boolean existOption = false;
		me.explicitWaitToBeClickable(opciones, Duration.ofSeconds(90));
		
		List<WebElement> allOptions = u.getDriver().findElements(opciones);
		
		for (WebElement option : allOptions) {
			System.out.print(option.getText());
			System.out.print("   "+opcion);
			if (option.getText().equals(opcion)) {
				u.hardWait(1);
				me.highlight(option);
				testCase.addScreenShotCurrentStep(u, "clickOpcion");
				option.click();
				u.hardWait(1);
				existOption = true;
				break;
			}
		}
		testCase.addScreenShotCurrentStep(u, "opcionSeleccionada");
		
		return existOption;

	}
	
	//***********************************Carga AgentID
	
	/**
	 * Metodo para cargar archivo excel
	 * @return Retorna true si se cargo correctamente
	 * @throws FindFailed
	 * @author Andrea Contreras
	 */
	public Boolean cargarArchivo(String filePath) throws FindFailed {
		
		Boolean correct = false;
		try {
			u.highLight(uploadInput);
			testCase.addScreenShotCurrentStep(u, "cargarArchivo");
			u.sendKeys(uploadInput, filePath);
			testCase.addScreenShotCurrentStep(u, "cargarArchivo");
			u.unhighLight(uploadInput);
			correct = true;
		}catch(Exception e) {
			e.printStackTrace();
			testCase.addScreenShotCurrentStep(u, "Error");
		}
		
		
		
		return correct;
	}
	
	
    /**
     * Metodo para dar click en el boton 'Load Archivo Tiendas'
     * @author Andrea Contreras
     */
    public void clickLoadArchivoTiendasBtn() {
    	
    	u.highLight(loadArchivoTiendasBtn);
    	testCase.addScreenShotCurrentStep(u, "loadArchivoTiendas");
    	u.click(loadArchivoTiendasBtn);
    	testCase.addScreenShotCurrentStep(u, "clickloadArchivoTiendas");
    	
    }
    
    /**
     * Metodo para validar la carga correcta del archivo de tiendas 'Carga AgentID'
     * @author Andrea Contreras
     */
    public boolean validarCargaAgentIDMensaje(String mensaje) {
    	u.hardWait(2);
    	me.scrollToElement(msjCarga);
    	u.highLight(msjCarga);
    	testCase.addScreenShotCurrentStep(u, "validarMensaje");
    	
    	boolean validacion = false;
    	if(u.getText(msjCarga).equals(mensaje)) {
    		validacion = true;
    	}
    	
    	return validacion;
    	
    }
    
    //
	//***********************************Tiendas como agentes MoneyGram
    
    public void selectPlaza(String plaza){
    	
    	try {
    		
    		me.explicitWaitToBeClickable(plazaSelect, Duration.ofSeconds(90));   		
    		List<WebElement> allOptions = u.getDriver().findElements(plazaSelect);
    		
    		for (WebElement option : allOptions) {
    			if (option.getText().equals(plaza)) {
    				u.hardWait(1);
    				//me.highlight(option);
    				//testCase.addScreenShotCurrentStep(u, "clickOpcion");
    				option.click();
    				u.hardWait(1);
    				break;
    			}
    		}
		
    	}catch(Exception e) {
    		Assert.fail("No se pudo seleccionar la plaza");
		}	 	 

		testCase.addScreenShotCurrentStep(u, "plazaSeleccionada");
		
    }
	
    
    public void selectTienda(String tienda){
    	
    	try {
    		
    		me.explicitWaitToBeClickable(tiendaSelect, Duration.ofSeconds(90));   		
    		List<WebElement> allOptions = u.getDriver().findElements(tiendaSelect);
    		
    		for (WebElement option : allOptions) {
    			if (option.getText().equals(tienda)) {
    				u.hardWait(1);
    				//me.highlight(option);
    				//testCase.addScreenShotCurrentStep(u, "clickOpcion");
    				option.click();
    				u.hardWait(1);
    				break;
    			}
    		}
    		
    	}catch(Exception e) {
    		Assert.fail("No se pudo seleccionar la tienda");
		}	 	 

		testCase.addScreenShotCurrentStep(u, "tiendaSeleccionada");
		
    }
    
   public void selectEstatusTienda(String estatus){
    	
    	try {
    		
    		me.explicitWaitToBeClickable(estatusTiendaDesplegar, Duration.ofSeconds(90));
    		u.click(estatusTiendaDesplegar);
    		
    		List<WebElement> allOptions = u.getDriver().findElements(estatusTiendaSelect);
    		
    		for (WebElement option : allOptions) {
    			if (option.getText().equals(estatus)) {
    				u.hardWait(1);
    				//me.highlight(option);
    				//testCase.addScreenShotCurrentStep(u, "clickOpcion");
    				option.click();
    				u.hardWait(1);
    				break;
    			}
    		}
		
    	}catch(Exception e) {
    		Assert.fail("No se pudo seleccionar el estatus de la tienda");
		}	 	 

		testCase.addScreenShotCurrentStep(u, "estatusTiendaSeleccionada");
		
    }
   
	/**
	 * Metodo para dar click en el boton 'Generar Reporte'
	 * @author Andrea Contreras
	 */
	public void clickGenerarReporteBtn() {

		u.scrollToElement(generarReporteBtn);
	    u.highLight(generarReporteBtn);
		testCase.addScreenShotCurrentStep(u, "generaReporteBtn");
	    u.click(generarReporteBtn);
		testCase.addScreenShotCurrentStep(u, "clickGeneraReporte");

	}
	
	/**
	 * Metodo para dar click en el boton 'Generar Reporte'
	 * @author Andrea Contreras
	 */
	public void visualizarResultados() {

		u.scrollToElement(resultados);
	    u.highLight(resultados);
		testCase.addScreenShotCurrentStep(u, "resultadosConsulta");

	}

}
