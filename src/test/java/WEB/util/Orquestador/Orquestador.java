package WEB.util.Orquestador;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.util.AuxMethods;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Orquestador {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private AuxMethods aux;

	private By DivEnvelope = By.cssSelector("body > div.envelope");
	
	// MENU PORTAL APLICACIONES
	private By Proveedores = By.xpath("//*[text()[normalize-space() = 'Proveedores']]");
	private By GestionProveedores = By.xpath("//*[contains(text(),'Gesti�n de Proveedores')]");
	
	// MODULO GESTION DE PROVEEDORES
	private By HGestionProveedores = By.xpath("//h1[text()[normalize-space() = 'Gesti�n de Proveedores']]");
	private By AltaArticulos = By.xpath("//*[text()[normalize-space() = 'Alta Art�culos']]");
	private By Abasto = By.xpath("//*[text()[normalize-space() = 'Abasto']]");
	private By AportacionesComerciales = By.xpath("//*[text()[normalize-space() = 'Aportaciones Comerciales']]");
	private By Finanzas = By.xpath("//*[text()[normalize-space()=\"Finanzas\"]]");
	private By Administracion = By.xpath("//*[text()[normalize-space()='Administracion']]");
	private By SolicitudCambio = By.xpath("//*[text()[normalize-space()='Solicitud de Cambio']]");
	
	//APARTADO ALTA ARTICULOS
	private By AltaProductos = By.xpath("//ul[@id='ddsubmenu8903']/li/a");
	
	//APARTADO ABASTO
	private By subMenuAbasto = By.xpath("//*[@id='ddsubmenu8894']");
	private By reporteDevoluciones = By.xpath("//*[text()[normalize-space() = 'Reporte de Devoluciones']]");
	private By stockout = By.xpath("//*[text()[normalize-space() = 'Stockout y venta perdida']]");
	
	//APARTADO APORTACIONES COMERCIALES
	private By SubAportacionesComerciales = By.cssSelector("#j_idt146");

	public Orquestador(SeleniumUtil u, TestCase testCase, AuxMethods aux) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
		this.aux = aux;
	}

	boolean ProvON = false;
	
	/**
	 * Metodo para comprobar que el apartado de Proveedor esta activo
	 * 
	 */
	public boolean IsProveedoresActive() {
		
		aux.scrollToElement(Proveedores);
		u.hardWait(3);
		if("Proveedores".equals(u.getText(Proveedores))) {
			
			ProvON = true;
			u.highLight(Proveedores);
			u.hardWait(2);
			testCase.addTextEvidenceCurrentStep("Se encontro el Modulo: Proveedores "   );
			
			
			return ProvON;
		}
		else {
			
			testCase.addTextEvidenceCurrentStep("No se encontro el Modulo: Proveedores "   );

		}
		
		
		return ProvON;
	
	}
	
	
	
	/**
	 * Metodo para hacer click en 'Gestion de Proveedores'
	 * 
	 * 
	 */
	
	public void ClickGestionProveedores() {
		
		
		
		u.hardWait(3);
		u.highLight(GestionProveedores);
		u.hardWait(5);
		aux.clickOpcion("Proveedores", GestionProveedores, true);
		u.hardWait(5);
		
		
	}

	/**
	 * Comprueba que el texto del header sea igual al esperado
	 * 
	 * @return true si el texto de h1 coincide con el esperado 'Gesti�n de Proveedores'
	 */
	public boolean IsGestionProveedores() {
		
		boolean header1 = false; 
		aux.scrollToElement(GestionProveedores);
		
		u.waitForLoadPage();
		
		if("Gesti�n de Proveedores".equals(u.getText(HGestionProveedores))) {
			
			header1 = true;
			testCase.addTextEvidenceCurrentStep("Se encontro el Modulo: Gesti�n de Proveedores "   );
			aux.clickOpcion("Proveedores", HGestionProveedores, true);
			u.hardWait(2);
			
			
			return header1;
		}
		
		testCase.addTextEvidenceCurrentStep("No se encontro el Modulo: Gesti�n de Proveedores "   );
		return header1;
	}
	
	/**
	 * Metodo que hace resalta el boton de 'Alta Articulos' y hace clic en el submenu 'Alta de Productos'
	 * 
	 */
	public void clickAltaArticulos() {
		
		u.highLight(AltaArticulos);
		u.mouseOver(AltaArticulos);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Alta Articulos");
		u.hardWait(2);
		aux.clickOpcion("Alta Productos", AltaProductos, true);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Muestra de Pantalla Alta de Articulos ");
		
		
	}
	
	/**
	 * Metodo que resalta el modulo 'Abasto' del menu orquestador
	 * 
	 */
	public void clickAbasto() {
		
		u.highLight(Abasto);
		u.mouseOver(Abasto);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("Abasto");
		u.hardWait(2);
		testCase.addTextEvidenceCurrentStep("Se muestran las opciones del Submenu del modulo 'Abasto' ");
		
	}
	
	
	
	/**
	 * Metodo que hace resalta el boton de 'Aportaciones Comerciales' y hace clic en el submenu 'Alta de Productos'
	 * 
	 */
	public void clickAportacionesComerciales() {
		
		u.highLight(AportacionesComerciales);
		u.mouseOver(AportacionesComerciales);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("AportacionesComerciales");
		u.hardWait(3);
		aux.clickOpcion("SubAportacionesComerciales", SubAportacionesComerciales, true);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("Muestra de Pantalla Aportaciones Comerciales ");
		u.hardWait(5);
		u.highLight(DivEnvelope);
		testCase.addScreenShotCurrentStep(u, "FullScreen");
		
	}
	
	
	/**
	 * Metodo que resalta el modulo 'Finanzas' del menu orquestador
	 * 
	 */
	public void clickFinanzas() {
		
		u.highLight(Finanzas);
		u.mouseOver(Finanzas);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u,"Finanzas");
		u.hardWait(3);
		testCase.addTextEvidenceCurrentStep("Se muestran las opciones del Submenu del modulo 'Finanzas' ");
		
		
	}
	
	
	/**
	 * Metodo que resalta el modulo 'Administracion' del menu orquestador
	 * 
	 */
	public void clickAdministracion() {
		
		u.highLight(Administracion);
		u.mouseOver(Administracion);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u,"Administracion");
		u.hardWait(3);
		testCase.addTextEvidenceCurrentStep("Se muestran las opciones del Submenu del modulo 'Administracion' ");
		
		
	}
	
	
	
	/**
	 * Metodo que resalta el modulo 'Solicitud de Cambio' del menu orquestador
	 * 
	 */
	public void clickSolicitudCambio() {
		
		u.highLight(SolicitudCambio);
		u.mouseOver(SolicitudCambio);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep(u,"SolicitudCambio");
		u.hardWait(3);
		testCase.addTextEvidenceCurrentStep("Se muestran las opciones del Submenu del modulo 'Solicitud de Cambio' ");
		
		
	}
	
	
	
	
}
