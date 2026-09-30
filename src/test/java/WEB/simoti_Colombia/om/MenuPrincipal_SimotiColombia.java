package WEB.simoti_Colombia.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MenuPrincipal_SimotiColombia {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	
	/*
	 * Localizadores Menu
	 ********************************************************************************************/
	
	By xmlLoadingMsj2= By.id("j_idt12_modal");
	By xmlLoadingMsj= By.xpath("//div[@id='j_idt12'][@aria-hidden='false']");
	/*
	 * Localizadores Menu Integridad de datos
	 ********************************************************************************************/	
	By xmlIntegridadDatos = By.xpath("//a[contains(text(), 'Integridad de Datos')]");
	
	//Opciones menu
	
	By xmlIndicadoresTotales = By.xpath("//a[contains(text(), 'Indicadores por Totales')]");
	
	/*
	 * Localizadores Menu Transmision de datos
	 ********************************************************************************************/
	
	By xmlTransmisionDatos = By.xpath("//li[4]/a[contains(text(), 'Transmisi�n de Datos')]");
	By xmlTransmisionDatosPlaza = By.xpath("//li/a[contains(text(), 'Transmisi�n de Datos')]");
	
	//Opciones menu
	
	By xmlReportePromociones = By.xpath("//a[contains(text(), 'Reporte Promociones')]");
	
	/*
	 * Localizadores Menu Transacciones Electronicas
	 ******************************************************************************************/
	
	By xmlTransaccionesElectronicas = By.xpath("//a[contains(text(), 'Transacciones Electr�nicas')]");
	By xmlTransaccionesElectronicasTDC = By.xpath("(//a[contains(text(),'Transacciones Electr�nicas')])[3]");
	
	//Opciones menu 
	
	By xmlTransacciones = By.xpath("//a[contains(text(), '- Transacciones')]");
	By xmlTransaccionesTDC = By.xpath("(//a[contains(text(), '- Transacciones TDC')])[2]");
	By xmlConsultarEditarProveedorEDI = By.xpath("//a[contains(text(),'Consultar y Editar Proveedores EDI')]");
	
	
	/*
	 * Localizadores Menu Ordenes De Compra
	 ********************************************************************************************/	
	By xmlOrdenesCompra = By.xpath("//a[contains(text(), '�rdenes de Compra')]"); //By xmlOrdenesCompra = By.xpath("//a[contains(text(), 'rdenes de Compra')]");
	
	/*
	 * Localizadores Menu Folio De Pago
	 ********************************************************************************************/	
	By xmlFolioPago = By.xpath("//a[contains(text(), 'Folio de Pago')]");
	
	/*
	 * Localizadores Menu Caja Inteligente
	 ********************************************************************************************/	
	By xmlCajaInteligente = By.xpath("//div[@id='menu_cnt']//li/a[contains(text(), 'Caja Inteligente')]");
	
	
	
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public MenuPrincipal_SimotiColombia(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Menu Integridad de datos
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Integridad de Datos
	 * @author Maria Due�as
	 */
	public void clickIntegridadDatos() {
		u.highLight(xmlIntegridadDatos);
		testCase.addScreenShotCurrentStep(u, "IntegridadDatos");
		u.unhighLight(xmlIntegridadDatos);
		u.mouseOver(xmlIntegridadDatos);
		testCase.addScreenShotCurrentStep(u, "clickIntegridadDatos");
	}
	
	/**
	 * Metodo para dar click en Indicadoires Totales
	 * @author Maria Due�as
	 */
	public void clickIndicadoresTotales() {
		u.highLight(xmlIndicadoresTotales);
		testCase.addScreenShotCurrentStep(u, "IndicadoresTotales");
		u.unhighLight(xmlIndicadoresTotales);
		u.click(xmlIndicadoresTotales);
		testCase.addScreenShotCurrentStep(u, "clickIndicadoresTotales");
	}
	
	/*
	 * Metodos Menu Transmision de datos
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Transmision de Datos usuario Administrador
	 * @author Maria Due�as
	 */
	public void clickTransmisionDatos() {
		u.highLight(xmlTransmisionDatos);
		testCase.addScreenShotCurrentStep(u, "TransmisionDatos");
		u.unhighLight(xmlTransmisionDatos);
		u.mouseOver(xmlTransmisionDatos);
		testCase.addScreenShotCurrentStep(u, "clickTransmisionDatos");
	}
	
	
	/**
	 * Metodo para dar click en Transmision de Datos usuario Plaza/Cedis/Central
	 * @author Maria Due�as
	 */
	public void clickTransmisionDatosPlazaCedisCentral() {
		u.highLight(xmlTransmisionDatosPlaza);
		testCase.addScreenShotCurrentStep(u, "TransmisionDatos");
		u.unhighLight(xmlTransmisionDatosPlaza);
		u.mouseOver(xmlTransmisionDatosPlaza);
		testCase.addScreenShotCurrentStep(u, "clickTransmisionDatos");
	}
	
	/**
	 * Metodo para dar click en Reporte promociones
	 * @author Maria Due�as
	 */
	public void clickReportePromociones() {
		u.highLight(xmlReportePromociones);
		testCase.addScreenShotCurrentStep(u, "ReportePromociones");
		u.unhighLight(xmlReportePromociones);
		u.mouseOverAndClick(xmlReportePromociones);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "clickReportePromociones");
	}
	
	
	
	/*
	 * Metodos Menu Transacciones electronicas
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Transacciones Electronicas
	 * @author Kevin Valadez
	 */
	public void clickTransaccionesElectronicas() {
		u.highLight(xmlTransaccionesElectronicas);
		testCase.addScreenShotCurrentStep(u, "TransaccionesElectronicas");
		u.unhighLight(xmlTransaccionesElectronicas);
		u.mouseOver(xmlTransaccionesElectronicas);
		testCase.addScreenShotCurrentStep(u, "clickTransaccionesElectronicas");
	}
	
	/**
	 * Metodo para dar click en Transacciones ElectronicasTDC
	 * @author Kevin Valadez
	 */
	public void clickTransaccionesElectronicasTDC() {
		u.highLight(xmlTransaccionesElectronicasTDC);
		testCase.addScreenShotCurrentStep(u, "TransaccionesElectronicasTDC");
		u.unhighLight(xmlTransaccionesElectronicasTDC);
		u.mouseOver(xmlTransaccionesElectronicasTDC);
		testCase.addScreenShotCurrentStep(u, "clickTransaccionesElectronicasTDC");
	}
	/**
	 * Metodo para dar click en Transacciones
	 * @author Kevin Valadez
	 */
	public void clickTransacciones() {
		u.highLight(xmlTransacciones);
		testCase.addScreenShotCurrentStep(u, "Transacciones");
		u.unhighLight(xmlTransacciones);
		u.mouseOverAndClick(xmlTransacciones);
		u.waitForLoadPage();
		u.getDriver().navigate().refresh();
		testCase.addScreenShotCurrentStep(u, "clickTransacciones");
	}
	
	/**
	 * Metodo para dar click en Transacciones
	 * @author Kevin Valadez
	 */
	public void clickTransaccionesTDC() {
		u.highLight(xmlTransaccionesTDC);
		testCase.addScreenShotCurrentStep(u, "TransaccionesTDC");
		u.unhighLight(xmlTransaccionesTDC);
		u.mouseOverAndClick(xmlTransaccionesTDC);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "clickTransaccionesTDC");
	}	
	
	/**
	 * Metodo para dar click en la opcion Consultar y Editar Proveedores EDI
	 * @author Andrea Contreras
	 */
	public void clickConsultarEditarProveedoresEDI() {
		u.highLight(xmlConsultarEditarProveedorEDI);
		testCase.addScreenShotCurrentStep(u, "selectConsultarEditarProveedorEDI");
		u.unhighLight(xmlConsultarEditarProveedorEDI);
		u.mouseOverAndClick(xmlConsultarEditarProveedorEDI);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep(u, "clickConsultarEditarProveedorEDI");
	}
	
	/*
	 * Otras validacines
	 ********************************************************************************************/
	
	/**
	 * Metodo para esperar a que ya no se muestre el componente de carga
	 * @author Andrea Contreras
	 * @param N/A
	 * @return N/A
	 */
	public void waitLoading() {
		boolean existe = false;
		
		do {
			existe = me.existElement(xmlLoadingMsj);	
		}while(existe == true);
		
		do {
			existe = me.existElement(xmlLoadingMsj2);	
		}while(existe == true);
				
	}
	
	
	/**
	 * Metodo para esperar a que ya no se muestre el componente de carga
	 * @author Andrea Contreras
	 * @param N/A
	 * @return N/A
	 */
	public void waitLoading2() {
		boolean existe = false;
		
		do {
			existe = me.existElement(xmlLoadingMsj2);	
		}while(existe == true);
				
	}
	
	/**
	 * Metodo para validar que exista la opcion de Reporte promociones
	 * @author Maria Due�as
	 */
	public Boolean validarReportePromociones() {
		
		Boolean exist = false;
		
		if (me.existElement(xmlReportePromociones)) {
			exist = true;
			u.highLight(xmlReportePromociones);
			testCase.addScreenShotCurrentStep(u, "ReportePromociones");
			u.unhighLight(xmlReportePromociones);
		}
		
	    return exist;
	}
	
	/**
	 * Metodo para validar que exista el menu Transacciones Electr�nicas para los usuarios:
	 *  Operador, Consulta, Administrador, Seguridad y Operacion, 
	 * @author Andrea Contreras
	 */
	public Boolean validarMenuUsuariosTE() {
		
		Boolean exist = false;
		
		if (me.existElement(xmlTransaccionesElectronicas)) {
			exist = true;
			u.highLight(xmlTransaccionesElectronicas);
			testCase.addScreenShotCurrentStep(u, "validarTransaccionesElectronicas");
			u.unhighLight(xmlTransaccionesElectronicas);
		}
		
	    return exist;
	}
	
	/**
	 * Metodo para validar que se muestren los menus para el usuario CEDIS
	 * @author Andrea Contreras
	 */
	public Boolean validarMenuUsuarioCajaInteligente() {
		
		Boolean exist = false;
		
		if (me.existElement(xmlCajaInteligente)) {
			exist = true;
			u.highLight(xmlCajaInteligente);
			testCase.addScreenShotCurrentStep(u, "validarMenusCajaInteligente");
			u.unhighLight(xmlCajaInteligente);
		}
		
	    return exist;
	}
	
	/**
	 * Metodo para validar que se muestren los menus para el usuario CEDIS y el usuario PLAZA. Ambos tienen los mismos menus
	 * @author Andrea Contreras
	 */
	public Boolean validarMenuUsuarioCedis() {
		
		Boolean validacion = false;
		
		if (me.existElement(xmlIntegridadDatos) && me.existElement(xmlTransmisionDatosPlaza) &&
				me.existElement(xmlTransaccionesElectronicas) && me.existElement(xmlOrdenesCompra) 
				&& me.existElement(xmlFolioPago)) {
			validacion = true;
			u.highLight(xmlIntegridadDatos);
			u.highLight(xmlTransmisionDatosPlaza);
			u.highLight(xmlTransaccionesElectronicas);
			u.highLight(xmlOrdenesCompra);
			u.highLight(xmlFolioPago);
			testCase.addScreenShotCurrentStep(u, "validarMenusCedis");
			u.unhighLight(xmlIntegridadDatos);
			u.unhighLight(xmlTransmisionDatosPlaza);
			u.unhighLight(xmlTransaccionesElectronicas);
			u.unhighLight(xmlOrdenesCompra);
			u.unhighLight(xmlFolioPago);
		}
		
	    return validacion;	    
	}
	
	
	

	
}
