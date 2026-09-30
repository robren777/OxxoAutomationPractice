package WEB.sarv3.om;

import org.openqa.selenium.By;
import org.sikuli.script.FindFailed;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MenuPrincipalSAR {
	
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	/*
	 * Localizadores Menu Registrar Poliza
	 ********************************************************************************************/
	
	By xmlRegistrarPoliza = By.xpath("//div[contains(text(), 'Registrar P�liza')]");
	
	//Opciones menu Registrar poliza
	
	By xmlRegPolizaSeguro = By.xpath("//div[contains(text(),'Registro P�liza de Seguro')]");
	
	/*
	 * Localizadores Menu Reportes
	 ********************************************************************************************/
	
	By xmlReportes = By.xpath("//div[contains(text(), 'Reportes')]");
	
	//Opciones menu reportes
	
	By xmlConsultaPasswords = By.xpath("//div[contains(text(),'Consulta de Passwords para Ajustes en POS')]");
	By xmlSemaforizacion = By.xpath("//div[contains(text(),'Reporte Semaforizaci�n de Siniestros')]");
	By xmlNivelServicio = By.xpath("//div[contains(text(),'Nivel de Servicio')]");
	By xmlIndicadoresGestion = By.xpath("//div[contains(text(), 'Indicadores de Gestion')]");
	By xmlSaldoSiniestros = By.xpath("//div[contains(text(), 'Saldo de Siniestros / Estadistica de Siniestrabilidad')]");
	By xmlCartaReclamacion = By.xpath("//div[contains(text(), 'Carta de Reclamaci�n de Mercanc�a/Dinero y Valores')]");
	By xmlSegDenuncia = By.xpath("//div[contains(text(), 'Seguimiento de la Denuncia')]");
	By xmlDespachoMasivos = By.xpath("//div[contains(text(), 'Despacho Masivos')]");
	
	/*
	 * Localizadores Menu Catalogo de Delincuentes
	 ********************************************************************************************/
	
	By xmlCatDelincuentes = By.xpath("//div[contains(text(), 'Cat�logo de Delincuentes')]");
	
	//Opciones menu catalogo delincuenctes
	
	By xmlBuscarDelincuente = By.xpath("//div[contains(text(), 'Buscar Delincuente')]");
	By xmlCrearDelincuente = By.xpath("//div[contains(text(), 'Crear Delincuente Externo')]");
	
	/*
	 * Localizadores Menu Adm�n Catalogos
	 ********************************************************************************************/
	
	By xmlAdmonCatalogos = By.xpath("//div[contains(text(), 'Adm�n de Cat�logos')]");
	
	//Opciones menu catalogo delincuenctes
	
	By xmlTSSiniestro = By.xpath("//div[contains(text(), 'Tipos y Subtipos de Siniestro')]");
	By xmlCatOtrosNegocios = By.xpath("//div[contains(text(), 'Cat�logo Otros Negocios')]");
	
	/*
	 * Localizadores Menu Centro de Control
	 ********************************************************************************************/
	
	By xmlCentroControl = By.xpath("//div[contains(text(), 'Centro de Control')]");
	
	//Opciones menu centro de control
	
	By xmlCatalogoAutoridades = By.xpath("//div[contains(text(), 'Cat�logo de Autoridades')]");
	By xmlHorariosCapturar = By.xpath("//div[contains(text(), 'Horarios por Capturar')]");
	By xmlConsultaAsistencias = By.xpath("//div[contains(text(), 'Consulta de Asistencias')]");
	
	/*
	 * Localizadores Menu Cat�logos
	 ********************************************************************************************/
	
	//By xmlCatalogos = By.xpath("//div[contains(text(), 'Cat�logos')]");
	By xmlCatalogos = By.xpath("//*[@id=\"menuDinamico\"]/div[2]/div[11]");
	
	//Opciones menu catalogos
	
	By xmlConfiguracion = By.xpath("//div[contains(text(), 'Configuraci�n de Siniestros y Responsables de PP')]");
	By xmlCatDelitos = By.xpath("//div[contains(text(), 'Cat�logo de Delitos')]");
	By xmlCatEmpleados = By.xpath("//div[contains(text(), 'Cat�logo de Empleados')]");
	
	/*
	 * Localizadores Alta de Siniestros
	 ********************************************************************************************/
	
	By xmlAltaSiniestro = By.xpath("//div[contains(text(), 'Alta de Siniestros')]");
	
	/*
	 * Localizadores B�squeda de Siniestros
	 ********************************************************************************************/
	
	//By xmlBusquedaSiniestro = By.xpath("//*[@id=\"menuDinamico\"]/div[2]/div[2]");
	By xmlBusSiniestro = By.xpath("//div[contains(text(), 'B�squeda de Siniestros')]");
	
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public MenuPrincipalSAR(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Menu Registrar Poliza
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Registrar Poliza
	 * @author Maria Due�as
	 */
	public void clickRegistrarPoliza() {
		u.highLight(xmlRegistrarPoliza);
		testCase.addScreenShotCurrentStep(u, "clickRegistrarPoliza");
		u.click(xmlRegistrarPoliza);
		u.highLight(xmlRegistrarPoliza);
	}
	
	/**
	 * Metodo para dar click en Registrar Poliza de Seguro
	 * @author Maria Due�as
	 */
	public void clickRegPolizaSeguro() {
		u.highLight(xmlRegPolizaSeguro);
		testCase.addScreenShotCurrentStep(u, "clickRegPolizaSeguro");
		u.click(xmlRegPolizaSeguro);
		u.highLight(xmlRegPolizaSeguro);
	}
	
	
	/*
	 * Metodos Menu Reportes
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Reportes
	 * @author Maria Due�as
	 */
	public void clickReportes() {
		u.highLight(xmlReportes);
		testCase.addScreenShotCurrentStep(u, "clickReportes");
		u.mouseOverAndClick(xmlReportes);
		u.unhighLight(xmlReportes);
		
	}
	
	/**
	 * Metodo para dar click en Consulta Passwords
	 * @author Maria Due�as
	 */
	public void clickConsultaPasswords() {
		u.highLight(xmlConsultaPasswords);
		testCase.addScreenShotCurrentStep(u, "clickConsultaPasswords");
		u.mouseOverAndClick(xmlConsultaPasswords);
		u.unhighLight(xmlConsultaPasswords);
		
	}
	
	/**
	 * Metodo para dar click en Semaforizacion
	 * @author Maria Due�as
	 */
	public void clickSemaforizacion() {
		u.highLight(xmlSemaforizacion);
		testCase.addScreenShotCurrentStep(u, "clickSemaforizacion");
		u.mouseOverAndClick(xmlSemaforizacion);
		u.unhighLight(xmlSemaforizacion);
		
	}
	
	/**
	 * Metodo para dar click en Nivel de Servicio
	 * @author Maria Due�as
	 */
	public void clickNivelServicio() {
		u.highLight(xmlNivelServicio);
		testCase.addScreenShotCurrentStep(u, "clickNivelServicio");
		u.click(xmlNivelServicio);
		u.unhighLight(xmlNivelServicio);
	}
	
	/**
	 * Metodo para dar click en Indicadores de Gestion
	 * @author Maria Due�as
	 */
	public void clickIndicadoresGestion() {
		u.highLight(xmlIndicadoresGestion);
		testCase.addScreenShotCurrentStep(u, "clickIndicadoresGestion");
		u.click(xmlIndicadoresGestion);
		u.unhighLight(xmlIndicadoresGestion);
	}
	
	/**
	 * Metodo para dar click en Saldo de siniestros
	 * @author Maria Due�as
	 */
	public void clickSaldoSiniestros() {
		u.highLight(xmlSaldoSiniestros);
		testCase.addScreenShotCurrentStep(u, "clickSaldoSiniestros");
		u.click(xmlSaldoSiniestros);
		u.unhighLight(xmlSaldoSiniestros);
	}
	
	/**
	 * Metodo para dar click en Saldo de siniestros
	 * @author Maria Due�as
	 */
	public void clickCartaReclamacion() {
		u.highLight(xmlCartaReclamacion);
		testCase.addScreenShotCurrentStep(u, "clickCartaReclamacion");
		u.click(xmlCartaReclamacion);
		u.unhighLight(xmlCartaReclamacion);
	}
	
	/**
	 * Metodo para dar click en Seguimiento de la denuncia
	 * @author Maria Due�as
	 */
	public void clickSeguimientoDenuncia() {
		u.highLight(xmlSegDenuncia);
		testCase.addScreenShotCurrentStep(u, "clickSeguimientoDenuncia");
		u.click(xmlSegDenuncia);
		u.unhighLight(xmlSegDenuncia);
	}
	
	/**
	 * Metodo para dar click en Despacho masivos
	 * @author Maria Due�as
	 */
	public void clickDespachoMasivos() {
		u.highLight(xmlDespachoMasivos);
		testCase.addScreenShotCurrentStep(u, "clickDespachoMasivos");
		u.click(xmlDespachoMasivos);
		u.unhighLight(xmlDespachoMasivos);
	}
	
	/*
	 * Metodos Menu Catalogo delincuentes
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Cat�logo de Delincuentes
	 * @author Maria Due�as
	 */
	public void clickCatDelincuentes() {
		u.highLight(xmlCatDelincuentes);
		testCase.addScreenShotCurrentStep(u, "clickCatDelincuentes");
		u.mouseOverAndClick(xmlCatDelincuentes);
		u.unhighLight(xmlCatDelincuentes);
		
	}
	
	/**
	 * Metodo para dar click en Buscar Delincuente
	 * @author Maria Due�as
	 */
	public void clickBuscarDelincuente() {
		u.highLight(xmlBuscarDelincuente);
		testCase.addScreenShotCurrentStep(u, "clickBuscarDelincuente");
		u.mouseOverAndClick(xmlBuscarDelincuente);
		u.unhighLight(xmlBuscarDelincuente);
		
	}
	
	/**
	 * Metodo para dar click en Crear Delincuente Externo
	 * @author Maria Due�as
	 */
	public void clickCrearDelincuente() {
		u.highLight(xmlCrearDelincuente);
		testCase.addScreenShotCurrentStep(u, "clickCrearDelincuente");
		u.mouseOverAndClick(xmlCrearDelincuente);
		u.unhighLight(xmlCrearDelincuente);
		
	}
	
	/*
	 * Metodos Menu Adm�n de Cat�logos
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Adm�n de Cat�logos
	 * @author Maria Due�as
	 */
	public void clickAdmonCatalogos() {
		u.highLight(xmlAdmonCatalogos);
		testCase.addScreenShotCurrentStep(u, "clickAdmonCatalogos");
		u.mouseOverAndClick(xmlAdmonCatalogos);
		u.unhighLight(xmlAdmonCatalogos);
		
	}
	/**
	 * Metodo para dar click en Tipos y Subtipos de Siniestros
	 * @author Maria Due�as
	 */
	public void clickTSSiniestro() {
		u.highLight(xmlTSSiniestro);
		testCase.addScreenShotCurrentStep(u, "clickTSSiniestro");
		u.mouseOverAndClick(xmlTSSiniestro);
		u.unhighLight(xmlTSSiniestro);
		
	}
	
	/**
	 * Metodo para dar click en Catalogos otros negocios
	 * @author Maria Due�as
	 */
	public void clickCatOtrosNegocios() {
		u.highLight(xmlCatOtrosNegocios);
		testCase.addScreenShotCurrentStep(u, "clickCatOtrosNegocios");
		u.mouseOverAndClick(xmlCatOtrosNegocios);
		u.unhighLight(xmlCatOtrosNegocios);
		
	}
	
	/*
	 * Metodos Menu Centro de Control
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Centro de control
	 * @author Maria Due�as
	 */
	public void clickCentroControl() {
		u.highLight(xmlCentroControl);
		testCase.addScreenShotCurrentStep(u, "clickCentroControl");
		u.mouseOverAndClick(xmlCentroControl);
		u.unhighLight(xmlCentroControl);
		
	}
	
	/**
	 * Metodo para dar click en Catalogo de Autoridades 
	 * @author Maria Due�as
	 */
	public void clickCatalogoAutoridades() {
		u.highLight(xmlCatalogoAutoridades);
		testCase.addScreenShotCurrentStep(u, "clickCatalogoAutoridades");
		u.mouseOverAndClick(xmlCatalogoAutoridades);
		u.unhighLight(xmlCatalogoAutoridades);
		
	}
	
	/**
	 * Metodo para dar click en Horarios por capturar 
	 * @author Maria Due�as
	 */
	public void clickHorariosCapturar() {
		u.highLight(xmlHorariosCapturar);
		testCase.addScreenShotCurrentStep(u, "clickHorariosCapturar");
		u.mouseOverAndClick(xmlHorariosCapturar);
		u.unhighLight(xmlHorariosCapturar);
		
	}
	/**
	 * Metodo para dar click en Consulta de asistencias
	 * @author Maria Due�as
	 */
	public void clickConsultaAsistencias() {
		u.highLight(xmlConsultaAsistencias);
		testCase.addScreenShotCurrentStep(u, "clickConsultaAsistencias");
		u.mouseOverAndClick(xmlConsultaAsistencias);
		u.unhighLight(xmlConsultaAsistencias);
		
	}
	
	/*
	 * Metodos Menu Cat�logos
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Catalogos
	 * @author Maria Due�as
	 */
	public void clickCatalogos() {
		u.highLight(xmlCatalogos);
		testCase.addScreenShotCurrentStep(u, "clickCatalogos");
		u.mouseOverAndClick(xmlCatalogos);
		u.unhighLight(xmlCatalogos);
		
	}
	
	/**
	 * Metodo para dar click en Configuracion
	 * @author Maria Due�as
	 */
	public void clickConfiguracion() {
		u.highLight(xmlConfiguracion);
		testCase.addScreenShotCurrentStep(u, "clickConfiguracion");
		u.mouseOverAndClick(xmlConfiguracion);
		u.unhighLight(xmlConfiguracion);
		
	}
	
	/**
	 * Metodo para dar click en Catalogo de delitos
	 * @author Maria Due�as
	 */
	public void clickCatDelitos() {
		u.highLight(xmlCatDelitos);
		testCase.addScreenShotCurrentStep(u, "clickCatDelitos");
		u.mouseOverAndClick(xmlCatDelitos);
		u.unhighLight(xmlCatDelitos);
		
	}
	
	/**
	 * Metodo para dar click en Catalogo de empleados
	 * @author Maria Due�as
	 */
	public void clickCatEmpleados() {
		u.highLight(xmlCatEmpleados);
		testCase.addScreenShotCurrentStep(u, "clickCatEmpleados");
		u.mouseOverAndClick(xmlCatEmpleados);
		u.unhighLight(xmlCatEmpleados);
		
	}
	
	/*
	 * Metodos Alta de Siniestros
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Alta de Siniestros
	 * @author Maria Due�as*/
	 
	public void clickAltaSiniestros() {
		u.highLight(xmlAltaSiniestro);
		testCase.addScreenShotCurrentStep(u, "clickAltaSiniestros");
		u.mouseOverAndClick(xmlAltaSiniestro);
		u.unhighLight(xmlAltaSiniestro);
		
	}
	
	/*
	 * Metodos B�squeda de Siniestros
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Busqueda de Siniestros
	 * @author Maria Due�as
	 * @throws FindFailed */
	 
	public void clickBusquedaSiniestros() throws FindFailed {
		
		me.clickElementSikuli("SARv3", "BusquedaSiniestros");
		
	}
	
	/**
	 * Metodo para dar click en 
	 * @author Maria Due�as
	 
	public void click() {
		u.highLight(xml);
		testCase.addScreenShotCurrentStep(u, "");
		u.mouseOverAndClick();
		u.unhighLight();
		
	}*/
}
