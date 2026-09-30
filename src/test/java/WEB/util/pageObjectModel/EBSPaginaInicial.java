package WEB.util.pageObjectModel;

import modelo.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.selenium.SeleniumUtil;
import java.util.Locale;

/**
 * Clase con los locators y acciones de la pagina de "EBS inicio"
 *
 * @author JesusH@hexaware.com
 * @version 1.0
 * @since 5/23/2022
 */
public class EBSPaginaInicial {

	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	// Generacion del constructor

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author JesusH@hexaware.com
	 */
	public EBSPaginaInicial(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	/*
	 * Inicio Responsabilidades
	 */
	By MXOXXOCO10VCZUSUARIO32SGR = By.xpath("//div[normalize-space()='MXOXXO-CO10VCZ-USUARIO-32SGR']");
	By MXOXXOPOXXXXXSolicitudesdeInversion = By
			.xpath("//div[normalize-space()='MXOXXO-POXXXXX-Solicitudes de Inversion']");
	By MXOXXOAPXXXXXSuperUsuario = By.xpath("//a[normalize-space()='MXOXXO-APXXXXX-Super Usuario']");
	By MXOXXOAPXXXXXADMONPROVEEDORES = By.xpath("//a[normalize-space()='MXOXXO-APXXXXX-ADMON-PROVEEDORES']");
	By MXOXXOGLXXXXXSuperUsuario = By.xpath("//a[normalize-space()='MXOXXO-GLXXXXX-Super Usuario']");
	By MXOXXOFNDXXXXXUsuarioCargaMasiva = By.xpath("//a[normalize-space()='MXOXXO-FNDXXXXX-Usuario carga masiva']");
	By MXOXXOAPXXXXXCONSULTAS = By.xpath("//div[normalize-space()='MXOXXO-APXXXXX-CONSULTAS']");
	By MXOXXOFNDXXXXXsuperusuarioconfigurar = By
			.xpath("//div[normalize-space()='MXOXXO-POXXXXX-Super Usuario Configurar']");
	By MXOXXOFSXXXXXUsuario_FISCAL_OS = By.xpath("//ul[@id='treemenu1']//li[8]/a");
	By MXXXXXGLXXXXXConsulta = By.xpath("//div[normalize-space()='MXXXXX-GLXXXXX-Consulta']");
	By MXOXXOCOXXXXXADMINISTRADOR = By.xpath("//div[normalize-space()='MXOXXO-COXXXXX-ADMINISTRADOR']");
	By PEOXXOGLXXXXXSUPERUSUARIO = By.xpath("//a[normalize-space()='PEOXXO-GLXXXXX-SUPER USUARIO']");
	By MXOXXOWM17FAACOORDINADOREQPFRM = By.xpath("//div[normalize-space()='MXOXXO-WM17FAA-COORDINADOR EQP FRM']");
	By MXOXXOAR10MONAJUSTETRANSACCIONES = By.xpath("//a[normalize-space()='MXOXXO-AR10MON-AJUSTE-TRANSACCIONES']");
	By MXOXXOIN17FAAAdmonACTFIJOOS = By.xpath("//div[normalize-space()='MXOXXO-IN17FAA-Admon_ACT_FIJO_OS']");
	By MXOXXOPNXXXXSuperUsuario = By.xpath("//a[normalize-space()='MXOXXO-PNXXXXX-Super Usuario']");
	By MXOXXOEAM10VCZEQUIPOMANTO = By.xpath("//div[normalize-space()='MXOXXO-EAM10VCZ-EQUIPO-MANTO']");
	By MXOXXOGL17ADMSERVICIOS = By.xpath("//div[normalize-space()='MXOXXO-GL17ADM-SERVICIOS']");
	By MXOXXOOM17FAACONSULTAFRM = By.xpath("//a[normalize-space()='MXOXXO-OM17FAA-CONSULTA FRM']");
	By MXOXXOPOXXXXXSuperUsuarioConfigurar = By
			.xpath("//a[normalize-space()='MXOXXO-POXXXXX-Super Usuario Configurar']");
	By MXOXXOPOXXXXXSuperUsuario = By.xpath("//a[normalize-space()='MXOXXO-POXXXXX-Super Usuario']");
	By MXOXXOAPXXXXXADMONPROVEEDORESOPECATOS = By
			.xpath("//div[normalize-space()='MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS']");
	By MXOXXOPO10HERCompras = By.xpath("//a[normalize-space()='MXOXXO-PO10HER-Compras']");

	By MXOXXOPO10QRICompras = By.xpath("//a[normalize-space()='MXOXXO-PO10QRI-Compras']");	

	By MXOXXOPOXXXXXPLANTILLADEREQUISICIONES = By
			.xpath("//div[normalize-space()='MXOXXO-POXXXXX-PLANTILLA DE REQUISICIONES']");
	By MXOXXOAP10MONUSUARIOFACTURA = By.xpath("//div[normalize-space()='MXOXXO-AP10MON-USUARIO-FACTURA']");
	By MXOXXOPNXXXXXAdmonOSACTFIJOS = By.xpath("//a[normalize-space()='MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS']");
	By MXOXXOPNXXXXXAdmonOS_ACT_FIJO_OS = By.xpath("//div[normalize-space()='MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS']");

	By MXOXXOICXXXXXCENTRAL = By.xpath("//a[normalize-space()='MXOXXO-ICXXXXX-CENTRAL']");

	By MXOXXOGL10POZUSUARIOCTLTIENDAS = By.xpath("//div[normalize-space()='MXOXXO-GL10POZ-USUARIO CTL TIENDAS']");
	By MXFINAGLXXXXXSuperUsuarioINFFINOS = By
			.xpath("//div[normalize-space()='MXFINA-GLXXXXX-Super Usuario_INF_FIN_OS']");
	By MXOXXOFSXXXXXUsuario = By.xpath("//div[normalize-space()='MXOXXO-FSXXXXX-Usuario']");
	By MXOXXOHELPDESK = By.xpath("//div[normalize-space()='MXOXXO-HELP-DESK']");
	By MXOXXOPOXXXXXConsulta = By.xpath("//div[normalize-space()='MXOXXO-POXXXXX-Consulta']");
	By MXOXXOEAMXXXXXSuperUsuario = By.xpath("//div[normalize-space()='MXOXXO-EAMXXXXX-Super Usuario']");
	By MXOXXOHRXXXXXConfigurador = By.xpath("//div[normalize-space()='MXOXXO-HRXXXXX-Configurador']");
	By MXOXXOPOXXXXXCOMPRAS = By.xpath("//div[normalize-space()='MXOXXO-POXXXXX-COMPRAS']");
	By MXOXXOAP15IXMAdmonProveedores = By.xpath("//div[normalize-space()='MXOXXO-AP15IXM-Admon Proveedores']");
	By MXOXXOAP10TIJUSUARIO = By.xpath("//div[normalize-space()='MXOXXO-AP10TIJ-USUARIO']");
	By MXOXXOAPXXXXXUSUARIOFACTURA = By.xpath("//div[normalize-space()='MXOXXO-APXXXXX-USUARIO-FACTURA']");
	By MXOXXOARXXXXXSuperUsuario = By.xpath("//div[normalize-space()='MXOXXO-ARXXXXX-Super Usuario']");
	By MXOXXOAR10GTORECIBOS = By.xpath("//div[normalize-space()='MXOXXO-AR10GTO-RECIBOS']");
	By MXOXXOCO10TMPUSUARIO = By.xpath("//div[normalize-space()='MXOXXO-CO10TMP-USUARIO']");
	By MXOXXOCO10CHIUSUARIO = By.xpath("//div[normalize-space()='MXOXXO-CO10CHI-USUARIO']");
	By MXOXXOCO10QRIUSUARIO = By.xpath("//div[normalize-space()='MXOXXO-CO10QRI-USUARIO']");
	By MXOXXOCMXXXXXSuperUsuario = By.xpath("//div[normalize-space()='MXOXXO-CMXXXXX-Super Usuario']");

	/*
	 * Fin Responsabilidades
	 */

	/*
	 * Inicio Carpetas
	 */

	By carpetaTienda = By.xpath("//div[normalize-space()='Tienda']");
	@FindBy(xpath = "//div[normalize-space()='Consultas']")
	public WebElement carpetaTiendaElement;

	By carpetaGlConsulta = By.xpath("//div[normalize-space()='Consultas']");
	@FindBy(xpath = "//div[normalize-space()='Consultas']")
	public WebElement carpetaGlConsultaElement;

	By carpetaArticulos = By.xpath("//li//div[@class='textdivresp'][normalize-space()='Artículos']");
	@FindBy(xpath = "//li//div[@class='textdivresp'][normalize-space()='Artículos']")
	public WebElement carpetaArticulosElement;

	By carpetaFacturas = By.xpath("//div[@class='textdivresp'][normalize-space()='Facturas']/parent::a");
	@FindBy(xpath = "//div[@class='textdivresp'][normalize-space()='Facturas']/parent::a")
	public WebElement carpetaFacturasElement;

	By carpetaFacturasEntrar = By.xpath(
			"//div[@class='textdivresp'][normalize-space()='Facturas']/parent::a/following-sibling::ul//div[text()='Entrar']/parent::a");
	@FindBy(xpath = "//div[@class='textdivresp'][normalize-space()='Facturas']/parent::a/following-sibling::ul//div[text()='Entrar']/parent::a")
	public WebElement carpetaFacturasEntrarElement;

	By carpetaEmpleados = By.xpath("//div[normalize-space()='Empleados']");
	@FindBy(xpath = "//div[normalize-space()='Empleados']")
	public WebElement carpetaEmpleadosElement;

	By carpetaPlantillaDeProveedores = By.xpath("//li/ul/li[20]/a/div[2][contains(text(),'Plantilla de Proveedores')]");
	@FindBy(xpath = "//li/ul/li[20]/a/div[2][contains(text(),'Plantilla de Proveedores')]")
	public WebElement carpetaPlantillaDeProveedoresElement;

	By carpetaProveedores = By.xpath("//div[normalize-space()='Proveedores']");
	@FindBy(xpath = "//div[normalize-space()='Proveedores']")
	public WebElement carpetaProveedoresElement;

	By carpetaProveedoresAP = By.xpath("//tbody/tr/td/ul/li[1]/ul/li[3]/a/div[2][contains(text(),'Proveedores')]");
	@FindBy(xpath = "//tbody/tr/td/ul/li[1]/ul/li[3]/a/div[2][contains(text(),'Proveedores')]")
	public WebElement carpetaProveedoresAPElement;

	By carpetaOtros = By.xpath("//a[normalize-space()='MXOXXO-GLXXXXX-Super Usuario']/following-sibling::ul/li[8]/a");
	@FindBy(xpath = "//a[normalize-space()='MXOXXO-GLXXXXX-Super Usuario']/following-sibling::ul/li[8]/a")
	public WebElement carpetaOtrosElement;

	By carpetaReportesCoAdmin = By.xpath("//ul/li[6]/a/div[2][contains(text(),'Reportes')]");
	@FindBy(xpath = "//ul/li[6]/a/div[2][contains(text(),'Reportes')]")
	public WebElement carpetaReportesCoAdminElement;

	By carpetaLadAddOnPeruPeGlSU = By.xpath("//ul/li[2]/ul/li[2]/a/div[2][contains(text(),'LAD ADD-On Peru')]");
	@FindBy(xpath = "//ul/li[2]/ul/li[2]/a/div[2][contains(text(),'LAD ADD-On Peru')])")
	public WebElement carpetaLadAddOnPeruPeGlSUElement;

	By carpetaOtrosAPSU = By.xpath(
			"//div[normalize-space()='MXOXXO-APXXXXX-Super Usuario']/parent::a/following-sibling::ul//div[normalize-space()='Otros']");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-APXXXXX-Super Usuario']/parent::a/following-sibling::ul//div[normalize-space()='Otros']")
	public WebElement carpetaOtrosAPSUElement;

	By carpetaRequestAPSU = By.xpath(
			"//div[normalize-space()='MXOXXO-APXXXXX-Super Usuario']/parent::a/following-sibling::ul//div[normalize-space()='Otros']/parent::a/following-sibling::ul//div[normalize-space()='Requests']");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-APXXXXX-Super Usuario']/parent::a/following-sibling::ul//div[normalize-space()='Otros']/parent::a/following-sibling::ul//div[normalize-space()='Requests']")
	public WebElement carpetaRequestAPSUElement;

	By carpetaCRs = By.xpath("//div[normalize-space()='CRs']");
	@FindBy(xpath = "//div[normalize-space()='CRs']")
	public WebElement carpetaCRsElement;

	By carpetaAdministracionAlmacen = By.xpath("//li//div[text()='Administracion de Almacén']");
	@FindBy(xpath = "//li//div[text()='Administracion de Almacén']")
	public WebElement carpetaAdministracionDeAlmacen;

	By carpetaOtrosWmCoEqpFrm = By.xpath("//ul/li[4]/ul/li[8]/a/div[2][contains(text(),'Otros')]");
	@FindBy(xpath = "//ul/li[4]/ul/li[8]/a/div[2][contains(text(),'Otros')]")
	public WebElement carpetaDeOtrosWmCoEqpFrm;

	By carpetaSolicitudesWmCoEqpFrm = By.xpath("//ul/li[4]/ul/li[8]/ul/li[6]/a/div[2][contains(text(),'Solicitudes')]");
	@FindBy(xpath = "//ul/li[4]/ul/li[8]/ul/li[6]/a/div[2][contains(text(),'Solicitudes')]")
	public WebElement carpetaDeSolicitudesWmCoEqpFrm;

	By carpetaRequestsWmCoEqpFrm = By
			.xpath("//ul/li[4]/ul/li[8]/ul/li[6]/ul/li[4]/a/div[2][contains(text(),'Requests')]");
	@FindBy(xpath = "//ul/li[4]/ul/li[8]/ul/li[6]/ul/li[4]/a/div[2][contains(text(),'Requests')]")
	public WebElement carpetaDeRequestsWmCoEqpFrm;

	By carpetaTransaccionesSolicitudes = By.xpath(
			"//div[normalize-space()='MXOXXO-AR10MON-AJUSTE-TRANSACCIONES']/parent::a/following-sibling::ul//a[normalize-space()='Solicitudes']");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-AR10MON-AJUSTE-TRANSACCIONES']/parent::a/following-sibling::ul//a[normalize-space()='Solicitudes']")
	public WebElement carpetaTransaccionesSolicitudesElement;

	By carpetaPedidosTrabajo = By.xpath("//div[@class='textdivresp'][normalize-space()='Pedidos de Trabajo']");
	@FindBy(xpath = "//div[@class='textdivresp'][normalize-space()='Pedidos de Trabajo']")
	public WebElement carpetaPedidosTrabajoElement;

	By carpetaInformes = By.xpath(
			"//div[normalize-space()='MXOXXO-GL17ADM-SERVICIOS']/parent::a/following-sibling::ul//div[contains(text(),'Informes')]");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-GL17ADM-SERVICIOS']/parent::a/following-sibling::ul//div[contains(text(),'Informes')]")
	public WebElement carpetaInformesElement;

	By carpetaSolicitar = By.xpath(
			"//div[normalize-space()='MXOXXO-GL17ADM-SERVICIOS']/parent::a/following-sibling::ul//div[contains(text(),'Solicitar')]");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-GL17ADM-SERVICIOS']/parent::a/following-sibling::ul//div[contains(text(),'Solicitar')]")
	public WebElement carpetaSolicitarElement;

	By carpetaConfiguracion = By.xpath("//div[normalize-space()='Configuración']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Configuración']/parent::a")
	public WebElement carpetaConfiguracionElement;

	By carpetaFinanciera = By.xpath("//div[normalize-space()='Financiera']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Financiera']/parent::a")
	public WebElement carpetaFinancieraElement;

	By carpetaFlexFields = By.xpath("//div[normalize-space()='Flexfields']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Flexfields']/parent::a")
	public WebElement carpetaFlexFieldsElement;

	By carpetaClave = By.xpath("//div[normalize-space()='Clave']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Clave']/parent::a")
	public WebElement carpetaClaveElement;

	By carpetaArrendamientosDocumentos = By.xpath(
			"//div[normalize-space()='MXOXXO-PNXXXXX-Super Usuario']/parent::a/following-sibling::ul//a[normalize-space()='Arrendamientos y Documentos']");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-PNXXXXX-Super Usuario']/parent::a/following-sibling::ul//a[normalize-space()='Arrendamientos y Documentos']")
	public WebElement carpetaArrendamientosDocumentosElement;

	By carpetaArrendamientoPrincipal = By.xpath(
			"//div[normalize-space()='MXOXXO-PNXXXXX-Super Usuario']/parent::a/following-sibling::ul//a[normalize-space()='Arrendamientos y Documentos']/following-sibling::ul//a[normalize-space()='Arrendamiento Principal']");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-PNXXXXX-Super Usuario']/parent::a/following-sibling::ul//a[normalize-space()='Arrendamientos y Documentos']/following-sibling::ul//a[normalize-space()='Arrendamiento Principal']")
	public WebElement carpetaArrendamientoElement;

	By carpetaOtrosFRM = By.xpath("//ul[@id='treemenu1']/li[18]/ul/li[5]/a");
	@FindBy(xpath = "//ul[@id='treemenu1']/li[17]/ul/li[5]/a")
	public WebElement carpetaOtrosFRMElement;

	By solicitudesOtrosFRM = By.xpath("//ul[@id='treemenu1']/li[18]/ul/li[5]/ul/li[6]/a");
	@FindBy(xpath = "//ul[@id='treemenu1']/li[17]/ul/li[5]/ul/li[6]/a")
	public WebElement solicitudesOtrosFRMlement;

	By requestOtrosFRM = By.xpath("//ul[@id='treemenu1']/li[18]/ul/li[5]/ul/li[6]/ul/li[4]/a");
	@FindBy(xpath = "//ul[@id='treemenu1']/li[17]/ul/li[5]/ul/li[6]/ul/li[4]/a")
	public WebElement requestOtrosFRMlement;

	By carpetaSistema = By.xpath("//div[normalize-space()='Sistema']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Sistema']/parent::a")
	public WebElement carpetaSistemaElement;

	By carpetaSolicitudesInternasPoSu = By.xpath("//ul/li[9]/a/div[2][contains(text(),'Solicitudes Internas')]");
	@FindBy(xpath = "//ul/li[9]/a/div[2][contains(text(),'Solicitudes Internas')]")
	public WebElement carpetaDeSolicitudesInternasPoSu;

	By carpetaSetup = By.xpath("//div[normalize-space()='Setup']");
	@FindBy(xpath = "//div[normalize-space()='Setup']")
	public WebElement carpetaSetupElement;

	By carpetaReportesAdmonProOpCaOs = By
			.xpath("//tbody/tr/td/ul/li[3]/ul/li[4]/a/div[2][contains(text(),'Reportes')]");
	@FindBy(xpath = "//tbody/tr/td/ul/li[3]/ul/li[4]/a/div[2][contains(text(),'Reportes')]")
	public WebElement carpetaReportesAdmonProOpCaOsElement;

	By carpetaProveedoresAdmonProOpCaOs = By.xpath("//li//li//a/div[text() = 'Proveedores']");
	@FindBy(xpath = "//li//li//a/div[text() = 'Proveedores']")
	public WebElement carpetaProveedoresAdmonProOpCaOsElement;

	By carpetaSuperUsuarioConfiguracion = By.xpath(
			"//div[normalize-space()='MXOXXO-PNXXXXX-Super Usuario']/parent::a/following-sibling::ul//a[normalize-space()='Configuración']");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-PNXXXXX-Super Usuario']/parent::a/following-sibling::ul//a[normalize-space()='Configuración']")
	public WebElement carpetaSuperUsuarioConfiguracionElement;

	By administracionalmacen = By.xpath("//li//div[text()='Administracion de Almacén']");
	@FindBy(xpath = "//li//div[text()='Administracion de Almacén']")
	public WebElement administracionalmacenElement;

	By otros = By.xpath("//li//div[text()='Otros']");
	@FindBy(xpath = "//li//div[text()='Otros']")
	public WebElement otrosElement;

	By solicitudesotros = By.xpath("//li//div[text()='Solicitudes']");
	@FindBy(xpath = "//li//div[text()='Solicitudes']")
	public WebElement solicitudesotrosElement;

	By requests = By.xpath("//li//div[text()='Requests']");
	@FindBy(xpath = "//li//div[text()='Requests']")
	public WebElement requestsElement;

	By carpetaTabuladores = By.xpath("//div[normalize-space()='Tabuladores']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Tabuladores']/parent::a")
	public WebElement carpetaTabuladoresElement;

	By carpetaAprobarExportarPagos = By.xpath("//div[normalize-space()='Aprobar y Exportar Pagos']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Aprobar y Exportar Pagos']/parent::a")
	public WebElement carpetaAprobarExportarPagosElement;

	By carpetaProcesoDeReservas = By.xpath("//div[normalize-space()='Reserva de Rentas']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Reserva de Rentas']/parent::a")
	public WebElement carpetaProcesoDeReservasElement;

	By carpetaRenegociacionCancelacionContratos = By
			.xpath("//div[normalize-space()='Renegociacion/Cancelación de Contratos']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Renegociacion/Cancelación de Contratos']/parent::a")
	public WebElement carpetaRenegociacionCancelacionContratosElement;

	By carpetaInformesGLXXXXXCONSULTAS = By.xpath("//div[normalize-space()='Informes']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Informes']/parent::a")
	public WebElement carpetaInformesGLXXXXXCONSULTASElement;

	By carpetaInformesSolicitarGLXXXXXCONSULTAS = By.xpath("//div[normalize-space()='Solicitar']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Solicitar']/parent::a")
	public WebElement carpetaInformesSolicitarGLXXXXXCONSULTASElement;

	By carpetaArrendamientosDocumentosArrendamientoPrincipal = By
			.xpath("//div[normalize-space()='Arrendamientos y Documentos : Arrendamiento Principal']");
	@FindBy(xpath = "//div[normalize-space()='Arrendamientos y Documentos : Arrendamiento Principal']")
	public WebElement carpetaArrendamientosDocumentosArrendamientoPrincipalElement;

	By carpetaReportes = By.xpath("//div[normalize-space()='Reportes']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Reportes']/parent::a")
	public WebElement carpetaReportesElement;

	By NuevoSistema = By.xpath("//div[normalize-space()='Nuevo sistema ICM']");////div[normalize-space()='Nuevo sistema ICM']
	@FindBy(xpath = "//div[normalize-space()='Nuevo sistema ICM']")////*[contains(text(),'Nuevo sistema ICM')]
	public WebElement NuevoSistemaICM;

	By carpetaIncrementoRentas = By.xpath("//div[normalize-space()='Incrementos de Rentas']");
	@FindBy(xpath = "//div[normalize-space()='Incrementos de Rentas']")
	public WebElement carpetaIncrementoRentasElement;

	By ByRecepcionClonacionCancelacion = By.xpath("//div[text()='Recepción, Clonación, Cancelación']");
	@FindBy(xpath = "//div[text()='Recepción, Clonación, Cancelación']")
	public WebElement ByRecepcionClonacionCancelacionElement;

	By ReporteDeConvenios = By.xpath("//div[text()='Reporte de Convenios']");
	@FindBy(xpath = "//div[text()='Reporte de Convenios']")
	public WebElement ReporteDeConveniosElement;

	By ReporteDeConveniosIC = By.xpath("//div[text() = 'Reporte IC']");
	@FindBy(xpath = "//div[text() = 'Reporte IC']")
	public WebElement ReporteDeConveniosICElement;

	By AdmonOS_ACT_FIJO_OSinterno = By.xpath(
			"//a[@href='#dummyAnchor']//div[@class='textdivresp'][normalize-space()='MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS']");
	@FindBy(xpath = "//a[@href='#dummyAnchor']//div[@class='textdivresp'][normalize-space()='MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS']")
	public WebElement AdmonOS_ACT_FIJO_OSinternoElement;

	By carpetaConfiguracionGlSuperUsuario = By.xpath("//div[normalize-space()='Configuración']");
	@FindBy(xpath = "//div[normalize-space()='Configuración']")
	public WebElement carpetaConfiguracionGlSuperUsuarioElement;

	By carpetaFinancieraGLSuperUsuario = By.xpath("//div[normalize-space()='Financiera']");
	@FindBy(xpath = "//div[normalize-space()='Financiera']")
	public WebElement carpetaFinancieraGLSuperUsuarioElement;

	By carpetaFlexfieldsGLSuperUsuario = By.xpath("//div[normalize-space()='Flexfields']");
	@FindBy(xpath = "//div[normalize-space()='Flexfields']")
	public WebElement carpetaFlexfieldsGLSuperUsuarioElement;

	By carpetaClaveGLSuperUsuario = By.xpath("//div[normalize-space()='Clave']");
	@FindBy(xpath = "//div[normalize-space()='Clave']")
	public WebElement carpetaClaveGLSuperUsuarioElement;

	By carpetaSistemaGLSuperUsuario = By.xpath("//div[normalize-space()='Sistema']");
	@FindBy(xpath = "//div[normalize-space()='Sistema']")
	public WebElement carpetaSistemaGLSuperUsuarioElement;

	By carpetaSolicitudes = By.xpath("//div[normalize-space()='Solicitudes']");
	@FindBy(xpath = "//div[normalize-space()='Solicitudes']")
	public WebElement carpetaSolicitudesElement;

	By carpetaRequest = By.xpath("//div[normalize-space()='Request']");
	@FindBy(xpath = "//div[normalize-space()='Request']")
	public WebElement carpetaRequestElement;

	By carpetaSolicitarGL17Servicios = By.xpath("//div[contains(text(),'Solicitar')]");
	@FindBy(xpath = "//div[contains(text(),'Solicitar')]")
	public WebElement carpetaSolicitarGL17ServiciosElement;

	By carpetaAsientosGLSuperUsuario = By.xpath("//div[contains(text(),'Asientos')]");
	@FindBy(xpath = "//div[contains(text(),'Asientos')]")
	public WebElement carpetaAsientosGLSuperUsuarioElement;

	@FindBy(xpath = "//div[contains(text(),'Ingreso')]")
	public WebElement carpetaIngreso;

	By carpetaPersonasHRConfigurador = By.xpath("//ul/li[22]/ul/li[2]/a/div[2][contains(text(),'Personas')]");
	@FindBy(xpath = "//ul/li[22]/ul/li[2]/a/div[2][contains(text(),'Personas')]")
	public WebElement carpetaPersonasHRConfiguradorElement;

	By carpetaProveedoresAP15AdmonProveedores = By
			.xpath("//tbody/tr/td/ul/li[3]/ul/li[3]/a/div[2][contains(text(),'Proveedores')]");
	@FindBy(xpath = "//tbody/tr/td/ul/li[3]/ul/li[3]/a/div[2][contains(text(),'Proveedores')]")
	public WebElement carpetaProveedoresAP15AdmonProveedoresElement;

	By carpetaFacturasAP10TijUsuario = By
			.xpath("//tbody/tr/td/ul/li[2]/ul/li[16]/a/div[2][contains(text(),'Facturas')]");
	@FindBy(xpath = "//tbody/tr/td/ul/li[2]/ul/li[16]/a/div[2][contains(text(),'Facturas')]")
	public WebElement carpetaFacturasAP10TijUsuarioElement;

	By carpetaIngresoFactAP10TijUsuario = By.xpath("//div[contains(text(),'Ingreso')]");
	@FindBy(xpath = "//div[contains(text(),'Ingreso')]")
	public WebElement carpetaIngresoFactAP10TijUsuarioElement;

	@FindBy(xpath = "//div[normalize-space()='Recepción']")
	public WebElement carpetaRecepcion;

	By carpetaTransaccionesARSuperUsuario = By.xpath("//ul/li[8]/a/div[2][contains(text(),'Transacciones')]");
	@FindBy(xpath = "//ul/li[8]/a/div[2][contains(text(),'Transacciones')]")
	public WebElement carpetaTransaccionesARSuperUsuarioElement;

	By carpetaTransaccionAR10MonAjusTran = By.xpath("//li[10]/ul/li[3]/a/div[2][normalize-space()='Transacciones']");
	@FindBy(xpath = "//li[10]/ul/li[3]/a/div[2][normalize-space()='Transacciones']")
	public WebElement carpetaTransaccionAR10MonAjusTranElement;

	By carpetaReportesCO10MPUsuario = By.xpath("//li[3]/a/div[2][normalize-space()='Reportes']");
	@FindBy(xpath = "//li[3]/a/div[2][normalize-space()='Reportes']")
	public WebElement carpetaReportesCO10MPUsuarioElement;

	By carpetaReportesCO10CHIUsuario = By.xpath("//li[3]/a/div[2][normalize-space()='Reportes']");
	@FindBy(xpath = "//li[3]/a/div[2][normalize-space()='Reportes']")
	public WebElement carpetaReportesCO10CHIUsuarioElement;

	By carpetaPagosCO10CHIUsuario = By.xpath("//div[normalize-space()='Pagos']");////li[13]/ul/li[4]/a/div[2][normalize-space()='Pagos']
	@FindBy(xpath = "//div[normalize-space()='Pagos']")
	public WebElement carpetaPagosCO10CHIUsuariElement;

	By carpetaPrestamoC010CHIUsuario = By.xpath("//li[13]/ul/li[5]/a/div[2][normalize-space()='Prestamo']");
	@FindBy(xpath = "//li[13]/ul/li[5]/a/div[2][normalize-space()='Prestamo']")
	public WebElement carpetaPrestamoC010CHIUsuarioElement;

	By carpetaNuevoSistemaICMCO10CHIUsuario = By.xpath("//li[7]/a/div[2][normalize-space()='Nuevo sistema ICM']");
	@FindBy(xpath = "//li[7]/a/div[2][normalize-space()='Nuevo sistema ICM']")
	public WebElement carpetaNuevoSistemaICMCO10CHIUsuarioElement;

	By carpetaConfiguracionAPSuperUsuario = By.xpath("//li[13]/a/div[2][normalize-space()='Configuración']");
	@FindBy(xpath = "//li[13]/a/div[2][normalize-space()='Configuración']")
	public WebElement carpetaConfiguracionAPSuperUsuarioElement;

	By carpetaConsultasAPSuperUsuario = By.xpath("//li[13]/ul/li[10]/a/div[2][normalize-space()='Consultas']");
	@FindBy(xpath = "//li[13]/ul/li[10]/a/div[2][normalize-space()='Consultas']")
	public WebElement carpetaConsultasAPSuperUsuarioElement;

	By carpetaNuevoSistemaICMCO10QRIUsuario = By
			.xpath("//li[14]/ul/li[7]/a/div[2][normalize-space()='Nuevo sistema ICM']");
	@FindBy(xpath = "//li[14]/ul/li[7]/a/div[2][normalize-space()='Nuevo sistema ICM']")
	public WebElement carpetaNuevoSistemaICMCO10QRIUsuarioElement;

	By carpetaReportesCO10QRIUsuario = By.xpath("//li[3]/a/div[2][normalize-space()='Reportes']");
	@FindBy(xpath = "//li[3]/a/div[2][normalize-space()='Reportes']")
	public WebElement carpetaReportesCO10QRIUsuarioElement;

	By carpetaBankStatementsCMSuperUsuario = By
			.xpath("//div[@class = 'textdivresp'][normalize-space()='Bank Statements']");
	@FindBy(xpath = "//div[@class = 'textdivresp'][normalize-space()='Bank Statements']")
	public WebElement carpetaBankStatementsCMSuperUsuarioElement;

	/*
	 * Fin Carpetas
	 */

	/*
	 * Inicio Formularios
	 */
	By formFacturas = By.xpath("//ul/li[1]/ul/li[2]/a/div[2][normalize-space()='Facturas']");
	By plantillaDeProveedoresForm = By
			.xpath("//tbody/tr/td/ul/li/ul/li[20]/ul//div[contains(text(),'Plantilla de Proveedores')]");

	By entryForm = By.xpath("//div[2][normalize-space()='Entry']");

	By cargaArchivosAdmonProOpCaOs = By
			.xpath("//tr/td/ul/li[3]/ul/li[2]/a/div[2][contains(text(),'Carga de Archivos')]");

	By entryAdmonProOpCaOs = By.xpath("//li//li//a/div[text() = 'Entry']");

	By cargaDeArchivo = By.xpath("//div[contains(text(),'Carga de Archivo')]/parent::a");
	@FindBy(xpath = "//div[contains(text(),'Carga de Archivo')]/parent::a")
	public WebElement cargaDeArchivoElement;

	By creacionConvenios = By.xpath("//div[contains(text(),'Creacion Convenios')]/parent::a");
	@FindBy(xpath = "//div[contains(text(),'Creacion Convenios')]/parent::a")
	public WebElement creacionConveniosElement;

	By generacionDocumentosReversa = By.xpath("//*[contains(text(),'Generacion de Documentos y Reversa')]");
	@FindBy(xpath = "//*[contains(text(),'Generacion de Documentos y Reversa')]")
	public WebElement GeneracioDeDocumentosYReversa;

	By plantillaDeRequisiciones = By.xpath(
			"//ul[@id='MXOXXO-POXXXXX-PLANTILLA_DE_REQUISICIONES']//a//div[text()='Plantilla de Requisiciones']");
	@FindBy(xpath = "//ul[@id='MXOXXO-POXXXXX-PLANTILLA_DE_REQUISICIONES']//a//div[text()='Plantilla de Requisiciones']")
	public WebElement plantillaDeRequisicionesElement;

	By plantillasCartaPorte = By.xpath("//ul/li[29]/ul/li[2]/a/div[2][text()='Plantillas de Carta Porte']");
	@FindBy(xpath = "//ul/li[29]/ul/li[2]/a/div[2][text()='Plantillas de Carta Porte']")
	public WebElement plantillasCartaPorteElement;

	By entryAP15AdmonProveedores = By
			.xpath("//tbody/tr/td/ul/li[3]/ul/li[3]/ul/li[1]/a/div[2][contains(text(),'Entry')]");
	@FindBy(xpath = "//tbody/tr/td/ul/li[3]/ul/li[3]/ul/li[1]/a/div[2][contains(text(),'Entry')]")
	public WebElement entryAP15AdmonProveedoresElement;

	By generacionDocumentosReversasCO10CHIUsuario = By
			.xpath("//li[7]/ul/li[2]/a/div[2][normalize-space()='Generacion de Documentos y Reversa']");
	@FindBy(xpath = "//li[7]/ul/li[2]/a/div[2][normalize-space()='Generacion de Documentos y Reversa']")
	public WebElement generacionDocumentosReversasCO10CHIUsuarioElement;

	By pagoAdelantadoSemanalDelLider = By
			.xpath("//li[7]/ul/li[1]/a/div[2][normalize-space()='Pago Adelantado Semanal del Lider']");
	@FindBy(xpath = "//li[7]/ul/li[1]/a/div[2][normalize-space()='Pago Adelantado Semanal del Lider']")
	public WebElement pagoAdelantadoSemanalDelLiderElement;

	/*
	 * Fin Formularios
	 */

	/*
	 * Inicio Applets
	 */

	By stockLocators = By.xpath("//div[normalize-space()='Stock Locators']");
	@FindBy(xpath = "//div[normalize-space()='Stock Locators']")
	public WebElement stockLocatorsElement;

	By despachosContables = By.xpath("//div[normalize-space()='Despachos Contables']");
	@FindBy(xpath = "//div[normalize-space()='Despachos Contables']")
	public WebElement despachosContablesElement;

	By runAccount = By.xpath("//div[normalize-space()='Account']");
	@FindBy(xpath = "//div[normalize-space()='Account']")
	public WebElement runAccountElement;

	By runConsultaSolicitud = By.xpath("//div[normalize-space()='Consulta de Solicitudes de Inversión']");
	@FindBy(xpath = "//div[normalize-space()='Consulta de Solicitudes de Inversión']")
	public WebElement runConsultaSolicitudElement;

	By runCoAdmin = By.xpath("//li[@id='LIReportes']//div[@class='textdiv'][normalize-space()='Run']");
	@FindBy(xpath = "//li[@id='LIReportes']//div[@class='textdiv'][normalize-space()='Run']")
	public WebElement runCoAdminElement;

	By informesPeGlSU = By.xpath("//tbody/tr/td/ul/li[2]/ul/li[2]/ul/li/a/div[2][normalize-space()='Informes']");
	@FindBy(xpath = "//li[@id='LAD_ADD-On_Peru'][normalize-space()='Informes']")
	public WebElement informesPeGlSUElement;

	By runTransaccion = By.xpath(
			"//div[normalize-space()='MXOXXO-AR10MON-AJUSTE-TRANSACCIONES']/parent::a/following-sibling::ul//a[normalize-space()='Solicitudes']/following-sibling::ul//a[normalize-space()='Run']");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-AR10MON-AJUSTE-TRANSACCIONES']/parent::a/following-sibling::ul//a[normalize-space()='Solicitudes']/following-sibling::ul//a[normalize-space()='Run']")
	public WebElement runTransaccionElement;

	By solicitudesActFijos = By.xpath("//ul[@id='treemenu1']/li[10]/ul/li[1]/a");
	@FindBy(xpath = "//ul[@id='treemenu1']/li[10]/ul/li[1]/a")
	public WebElement solicitudesActFijosElement;

	By visualizarArrendamientosDoc = By.xpath("//div[normalize-space()='MXOXXO-PNXXXXX-Super Usuario']/"
			+ "parent::a/following-sibling::ul//a[normalize-space()='Arrendamientos y Documentos']"
			+ "/following-sibling::ul//a[normalize-space()='Arrendamiento Principal']"
			+ "/following-sibling::ul//a[normalize-space()='Visualizar Arrendamientos y Documentos']");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-PNXXXXX-Super Usuario']/"
			+ "parent::a/following-sibling::ul//a[normalize-space()='Arrendamientos y Documentos']"
			+ "/following-sibling::ul//a[normalize-space()='Arrendamiento Principal']"
			+ "/following-sibling::ul//a[normalize-space()='Visualizar Arrendamientos y Documentos']")
	public WebElement visualizarArrendamientosDocElement;

	By pedidosTrabajo = By.xpath("//div[@class='textdiv'][normalize-space()='Pedidos de Trabajo']");
	@FindBy(xpath = "//div[@class='textdiv'][normalize-space()='Pedidos de Trabajo']")
	public WebElement pedidosTrabajoElement;

	By runStandard = By.xpath("//div[@class='textdiv'][normalize-space()='Standard']/parent::a");
	@FindBy(xpath = "//div[@class='textdiv'][normalize-space()='Standard']/parent::a")
	public WebElement runStandardElement;

	By runValores = By
			.xpath("//li[@id='LIClaveSegmentos']//div[@class='textdiv'][normalize-space()='Valores']/parent::a");
	@FindBy(xpath = "//li[@id='LIClaveSegmentos']//div[@class='textdiv'][normalize-space()='Valores']/parent::a")
	public WebElement runValoresElement;

	By runRequestFRM = By.xpath("//ul[@id='treemenu1']/li[18]/ul/li[5]/ul/li[6]/ul/li[4]/ul/li/a");
	@FindBy(xpath = "//ul[@id='treemenu1']/li[18]/ul/li[5]/ul/li[6]/ul/li[4]/ul/li/a")
	public WebElement runRequestFRMElement;

	By ordenesDeCompra = By.xpath(
			"//ul[@id='MXOXXO-PO10HER-ComprasÓrdenes_de_CompraEstatus_de_Aprobaci�n_Ordenes_de_CompraConsulta_Ordenes_de_CompraProrrateo_de_�rdenes_de_Compra']/li/a");
	@FindBy(xpath = "//ul[@id='MXOXXO-PO10HER-ComprasÓrdenes_de_CompraEstatus_de_Aprobación_Ordenes_de_CompraConsulta_Ordenes_de_CompraProrrateo_de_Órdenes_de_Compra']/li/a")
	public WebElement ordenesDeCompraElement;
		
	String ordenesDeCompraRes = "//ul[@id='%s�rdenes_de_CompraEstatus_de_Aprobaci�n_Ordenes_de_CompraConsulta_Ordenes_de_CompraProrrateo_de_�rdenes_de_Compra']/li/a";

	By reclasifContratosyDirNEXXO = By.xpath(
			"//div[normalize-space()='MXOXXO-PNXXXXX-Super Usuario']/parent::a/following-sibling::ul//a[normalize-space()='Configuraci�n']/following-sibling::ul//a[normalize-space()='Reclasificacion de Contratos y Direcciones NEXXO']");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-PNXXXXX-Super Usuario']/parent::a/following-sibling::ul//a[normalize-space()='Configuración']/following-sibling::ul//a[normalize-space()='Reclasificacion de Contratos y Direcciones NEXXO']")
	public WebElement reclasifContratosyDirNEXXOElement;

	By runConsultasGeneralLedger = By.xpath("//div[normalize-space()='Consultas de General Ledger']");
	@FindBy(xpath = "//div[normalize-space()='Consultas de General Ledger']")
	public WebElement runConsultasGeneralLedgerElement;

	By verSolicitudesAdmonProOpCaOs = By.xpath("//li//li//a/div[text() = 'Ver Solicitudes']");
	@FindBy(xpath = "//li//li//a/div[text() = 'Ver Solicitudes']")
	public WebElement verSolicitudesAdmonProOpCaOsElement;

	By runWmCoEqpFrm = By.xpath("//ul/li[4]/ul/li[8]/ul/li[6]/ul/li[4]/ul/li[1]/a/div[2][contains(text(),'Run')]");
	@FindBy(xpath = "//li//div[text()='Run']")
	public WebElement runWmCoEqpFrmElement;

	By runServiciosProdFPago = By.xpath("//div[normalize-space()='Servicios, Prod. y Formas Pago']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Servicios, Prod. y Formas Pago']/parent::a")
	public WebElement runServiciosProdFPagoElement;

	By runSolicitudes = By.xpath("//div[normalize-space()='Solicitudes']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Solicitudes']/parent::a")
	public WebElement runSolicitudesElement;

	By runAprobacionPagos = By.xpath(
			"//div[normalize-space()='Aprobar y Exportar Pagos']/parent::a/following-sibling::ul//a[normalize-space()='Aprobación de Pagos']");
	@FindBy(xpath = "//div[normalize-space()='Aprobar y Exportar Pagos']/parent::a/following-sibling::ul//a[normalize-space()='Aprobación de Pagos']")
	public WebElement runAprobacionPagosElement;

	By runExportarPagosCuentasPorCobrar = By.xpath(
			"//div[normalize-space()='Aprobar y Exportar Pagos']/parent::a/following-sibling::ul//a[normalize-space()='Exportar Pagos a Cuentas por Pagar']");
	@FindBy(xpath = "//div[normalize-space()='Aprobar y Exportar Pagos']/parent::a/following-sibling::ul//a[normalize-space()='Exportar Pagos a Cuentas por Pagar']")
	public WebElement runExportarPagosCuentasPorCobrarElement;

	By runInformePagosAprobados = By.xpath(
			"//div[normalize-space()='Aprobar y Exportar Pagos']/parent::a/following-sibling::ul//a[normalize-space()='Informe de pagos Aprobados']");
	@FindBy(xpath = "//div[normalize-space()='Aprobar y Exportar Pagos']/parent::a/following-sibling::ul//a[normalize-space()='Informe de pagos Aprobados']")
	public WebElement runInformePagosAprobadosElement;

	By runProcesoDeReserva = By.xpath(
			"//div[normalize-space()='Reserva de Rentas']/parent::a/following-sibling::ul//a[normalize-space()='Proceso de Reserva']");
	@FindBy(xpath = "//div[normalize-space()='Reserva de Rentas']/parent::a/following-sibling::ul//a[normalize-space()='Proceso de Reserva']")
	public WebElement runProcesoDeReservaElement;

	By runReportesDeReserva = By.xpath(
			"//div[normalize-space()='Reserva de Rentas']/parent::a/following-sibling::ul//a[normalize-space()='Reportes de Reserva']");
	@FindBy(xpath = "//div[normalize-space()='Reserva de Rentas']/parent::a/following-sibling::ul//a[normalize-space()='Reportes de Reserva']")
	public WebElement runReportesDeReservaElement;

	By runCapturaRenegociacionCancelacionContratos = By
			.xpath("//div[normalize-space()='Captura de Renegociacion/Cancelación de Contratos']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Captura de Renegociacion/Cancelación de Contratos']/parent::a")
	public WebElement runCapturaRenegociacionCancelacionContratosElement;

	By runInformesSolicitarStandard = By.xpath("//div[normalize-space()='Standard']/parent::a");
	@FindBy(xpath = "//div[normalize-space()='Standard']/parent::a")
	public WebElement runInformesSolicitarStandardElement;

	By runVisualizarArrendamientosDocumentos = By
			.xpath("//div[normalize-space()='Visualizar Arrendamientos y Documentos']");
	@FindBy(xpath = "//div[normalize-space()='Visualizar Arrendamientos y Documentos']")
	public WebElement runVisualizarArrendamientosDocumentosElement;

	By run = By.xpath("//div[normalize-space()='Run']");
	@FindBy(xpath = "//div[normalize-space()='Run']")
	public WebElement runElement;

	By calculoDeIncrementosRentas = By.xpath("//div[normalize-space()='Incrementos de Rentas']");
	@FindBy(xpath = "//div[normalize-space()='Incrementos de Rentas']")
	public WebElement calculoDeIncrementosRentasElement;

	By runCalculoIncrementoRentas = By.xpath("//div[normalize-space()='Calculo de Incrementos de Rentas']");
	@FindBy(xpath = "//div[normalize-space()='Calculo de Incrementos de Rentas']")
	public WebElement runCalculoIncrementoRentasElement;

	By runValoresGLSuperUsuario = By
			.xpath("//ul/li[7]/ul/li[2]/ul/li[3]/ul/li[1]/ul/li[2]/a/div[2][normalize-space()='Valores']");
	@FindBy(xpath = "//ul/li[7]/ul/li[2]/ul/li[3]/ul/li[1]/ul/li[2]/a/div[2][normalize-space()='Valores']")
	public WebElement runValoresGLSuperUsuarioElement;

	By runArticulosOrganizacion = By.xpath("//div[normalize-space()='Artículos de Organización']");
	@FindBy(xpath = "//div[normalize-space()='Artículos de Organización']")
	public WebElement runArticulosOrganizacionElement;

	By runConsultasLedgerGLSuperUsuario = By.xpath("//div[normalize-space()='Consultas de General Ledger']");
	@FindBy(xpath = "//div[normalize-space()='Consultas de General Ledger']")
	public WebElement runConsultasLedgerGLSuperUsuarioElement;

	By analisisDeSaldos = By
			.xpath("//*[@id='LIMXOXXO-GL10POZ-USUARIO_CTL_TIENDAS']/a[normalize-space()='Analisis de Saldos']");
	@FindBy(xpath = "//*[@id='LIMXOXXO-GL10POZ-USUARIO_CTL_TIENDAS']/a[normalize-space()='Analisis de Saldos']")
	public WebElement analisisDeSaldosElement;

	By runReportes = By.xpath("//div[normalize-space()='Reportes']");
	@FindBy(xpath = "//div[normalize-space()='Reportes']")
	public WebElement runReportesElement;

	By runEnterGLSuperUsuario = By.xpath("//div[contains(text(),'Enter')]");
	@FindBy(xpath = "//div[contains(text(),'Enter')]")
	public WebElement runEnterGLSuperUsuarioElement;

	By runStockLocatorsCoFrm = By.xpath("//div[normalize-space()='Stock Locators']");
	@FindBy(xpath = "//div[normalize-space()='Stock Locators']")
	public WebElement runStockLocatorsCoFrmElement;

	By runUsuariosHD = By.xpath("//div[normalize-space()='Usuarios']");
	@FindBy(xpath = "//div[normalize-space()='Usuarios']")
	public WebElement runUsuarioHDElement;

	By runConsultaOrdenesCompraPOConsulta = By.xpath("//div[normalize-space()='Consulta de Ordenes de compra']");
	@FindBy(xpath = "//div[normalize-space()='Consulta de Ordenes de compra']")
	public WebElement runConsultaOrdenesCompraPOConsultaElement;

	By runInformesEAMSuperUsuario = By.xpath("//div[normalize-space()='Informes']");
	@FindBy(xpath = "//div[normalize-space()='Informes']")
	public WebElement runInformesEAMSuperUsuarioElement;

	@FindBy(xpath = "//li[@id='LIIngreso']//div[normalize-space()='Facturas']")
	public WebElement appletFacturas;

	By runOrdenesCompraPO = By.xpath("//div[normalize-space()='Órdenes de Compra']");
	@FindBy(xpath = "//div[normalize-space()='Órdenes de Compra']")
	public WebElement runOrdenesCompraPOElement;

	By runIngresarMantenerHRConfigurador = By.xpath("//div[normalize-space()='Ingresar y Mantener']");
	@FindBy(xpath = "//div[normalize-space()='Ingresar y Mantener']")
	public WebElement runIngresarMantenerHRConfiguradorElement;

	@FindBy(xpath = "//div[normalize-space()='Consulta Ordenes de Compra']")
	public WebElement consultaOrdenesDeCompra;

	By runFacturasAP10TijUsuario = By.xpath("//li[3]/ul/li[1]/a/div[2][normalize-space()='Facturas']");
	@FindBy(xpath = "//li[3]/ul/li[1]/a/div[2][normalize-space()='Facturas']")
	public WebElement runFacturasAP10TijUsuarioElement;

	By runTransaccionesARSuperUsuario = By.xpath("//li[3]/a/div[2][normalize-space()='Transacciones']");
	@FindBy(xpath = "//li[3]/a/div[2][normalize-space()='Transacciones']")
	public WebElement runTransaccionesARSuperUsuarioElement;

	By runTrasaccionesAR10MonAjusteTran = By.xpath("//li[3]/ul/li/a/div[2][normalize-space()='Transacciones']");
	@FindBy(xpath = "//li[3]/ul/li/a/div[2][normalize-space()='Transacciones']")
	public WebElement runTrasaccionesAR10MonAjusteTranElement;

	By runRecibosAR10GTORecibos = By.xpath("//div[normalize-space()='Recibos']");
	@FindBy(xpath = "//div[normalize-space()='Recibos']")
	public WebElement runRecibosAR10GTORecibosElement;

	By runReportesCO10MPUsuario = By.xpath("//div[normalize-space()='Run']");
	@FindBy(xpath = "//div[normalize-space()='Run']")
	public WebElement runReportesCO10MPUsuarioElement;

	By runReportesCO10CHIUsuario = By.xpath("//div[normalize-space()='Run']");
	@FindBy(xpath = "//div[normalize-space()='Run']")
	public WebElement runReportesCO10CHIUsuarioElement;

	By runGeneracionPagosCuentaComisionistas = By
			.xpath("//div[normalize-space()='Generaci�n de Pagos a Cuenta de Comisionista']");
	@FindBy(xpath = "//div[normalize-space()='Generaci�n de Pagos a Cuenta de Comisionista']")
	public WebElement runGeneracionPagosCuentaComisionistasElement;
	
	By runPrestamoCO10CHIUsuario = By.xpath("//div[@class='textdivresp'][contains(text(),'Prestamo')]");
	@FindBy(xpath = "//div[@class='textdivresp'][contains(text(),'Prestamo')]")
	public WebElement runPrestamoCO10CHIUsuarioElement;

	By runPrestamosCO10CHIUsuario = By.xpath("//div[normalize-space()='Prestamos']");
	@FindBy(xpath = "//div[normalize-space()='Prestamos']")
	public WebElement runPrestamosCO10CHIUsuarioElement;	

	By runCuentasAPagarAPSuperUsuario = By.xpath("//div[normalize-space()='Cuentas a Pagar']");
	@FindBy(xpath = "//div[normalize-space()='Cuentas a Pagar']")
	public WebElement runCuentasAPagarAPSuperUsuarioElement;

	By runReportesCO10QRIUsuario = By.xpath("//div[normalize-space()='Run']");
	@FindBy(xpath = "//div[normalize-space()='Run']")
	public WebElement runReportesCO10QRIUsuarioElement;

	By runBankStatementInterfaceCMSuperUsuario = By.xpath("//div[normalize-space()='Bank Statement Interface']");
	@FindBy(xpath = "//div[normalize-space()='Bank Statement Interface']")
	public WebElement runBankStatementInterfaceCMSuperUsuarioElement;

	@FindBy(xpath = "//div[normalize-space()='Recepciones']")
	public WebElement appletRecepciones;

	@FindBy(xpath = "//div[normalize-space()='Consulta Ordenes de Compra']")
	public WebElement appletConsultaOrdenesDeCompra;
	/*
	 * Fin Applets
	 */

	/*
	 * Inicio Elementos generales (botones, cajas de texto, etc)
	 */
	By altaCentrosResponsabilidad = By.xpath("//div[normalize-space()='Alta de Centros de Responsabilidad']");
	By calculoIncrementosdeRentas = By.xpath("//div[normalize-space()='Calculo de Incrementos de Rentas']");
	By navegador = By.xpath("//*[@id='header']/tbody/tr[2]/td/table/tbody/tr/td/h2");
	By visualizarEmpleados = By.xpath("//div[normalize-space()='Visualizar Empleados']");
	By entryProveedoresAP = By.xpath(
			"//ul[@id='MXOXXO-APXXXXX-ADMON-PROVEEDORESCatalogo_Regimen_Resico']//div[normalize-space()='Entry']/parent::a");
	By facturasEntrar = By.xpath(
			"//div[@class='textdivresp'][normalize-space()='Facturas']/parent::a/following-sibling::ul//div[text()='Entrar']/parent::a");
	By facturasEntrarFacturas = By.xpath("//li[@id='LIEntrarLotes_de_Facturas']//div[text()='Facturas']/parent::a");
	By consultaProveedores = By.xpath("//div[normalize-space()='Consulta Proveedores']");
	By cargaarchivoscon = By.xpath("//div[normalize-space()='Carga de Archivos']");
	By consultaSolicitudes = By.xpath("//ul/li[16]/ul/li[2]/a");
	By requestStandard = By.xpath("//ul[@id='treemenu1']/li[16]/ul/li[2]/ul/li/ul/li[@id='LIRequestFinancial']/a");
	By contabilidadElectronica = By.xpath(
			"//ul[@id='treemenu1']//li[8]/ul[@id='MXOXXO-FSXXXXX-Usuario_FISCAL_OSReportesListados_SAT']/li[6]/a");
	By solicitudesContabilidadElectronica = By.xpath(
			"//ul[@id='treemenu1']//li[8]/ul[@id='MXOXXO-FSXXXXX-Usuario_FISCAL_OSReportesListados_SAT']/li[6]/ul/li[1]/a");
	By mantCuentasConatble = By.xpath("//div[normalize-space()='Mantenimiento a cuentas contables y Comisiones']");
	By solicitudes = By.xpath("//ul[@id='treemenu1']//div[text()='Solicitudes']/parent :: a");
	By request1 = By.xpath("//ul[@id='treemenu1']//a//div[text()='Request']/parent :: a");
	By standard = By.xpath("//li[@id='LIRequestFinancial']//div[text()='Standard']/parent :: a");
	By lupabusqueda = By.xpath("//table[@id='CargaArchivosRN']//input[@id='lovProceso']/following-sibling::a");
	By cajadebusqueda = By.xpath("//input[@id='lovProceso']");
	By tabla = By.xpath("//span[@id='N1:msgConcurrente:0']");
	By newFrame = By.xpath("//frame[@title='Content']");
	By request = By.xpath("//ul[@id='treemenu1']//a//div[text()='Request']/parent :: a");
	By runrequests = By.xpath("//li//ul[@id = 'RequestsRun']//li[@id='LIRequests']");
	By otrosWmCoEqpFrm = By.xpath("//ul/li[4]/ul/li[8]/a/div[2][contains(text(),'Otros')]");
	By solicitudesWmCoEqpFrm = By.xpath("//ul/li[4]/ul/li[8]/ul/li[6]/a/div[2][contains(text(),'Solicitudes')]");
	By requestsWmCoEqpFrm = By.xpath("//ul/li[4]/ul/li[8]/ul/li[6]/ul/li[4]/a/div[2][contains(text(),'Requests')]");
	By sotro = By.xpath("//li//li//div[text()='Otro']");
	By ejecutarsolicitud = By.xpath("//li//li//li[@id='LIOtro']");
	By versolicitudmetodo = By.xpath("//li//li//li[@id='LIOtroEjecutar_Solicitud']");
	By versolicitudes = By.xpath("//div[normalize-space()='Ver Solicitudes']");
	By pedidostrabajo = By.xpath("//div//li//div[text()='Pedidos de Trabajo']");
	By cargaaddenda = By.xpath(
			"//div//li//ul//li[@id= 'LIPedidos_de_TrabajoPedidos_de_TrabajoCerrar_Pedidos_de_TrabajoMateriales_y_Mano_de_Obra_-_Fase_2']");
	By seleccionaraddenda = By.xpath("//input[@id='MessageFileUpload_oafileUpload']");
	By aplicaraddenda = By.xpath("//button[@id='Submit']");
	By home = By.xpath("//img[@title='Página Inicial']");
	By regresarpedidos = By.xpath("//div//li//ul//li[@id='LIPedidos_de_Trabajo']");
	By seleccionRapida = By.xpath("//table/tbody/tr[2]/td[2]/a/img");
	By solicitudesInternasPoSu = By.xpath("//ul/li[9]/a/div[2][contains(text(),'Solicitudes Internas')]");
	By resumenSolicitudInternaPoSu = By
			.xpath("//ul/li[9]/ul/li[1]/a/div[2][contains(text(),'Resumen de Solicitud Interna')]");
	By entry = By.xpath("//div[normalize-space()='Entry']");
	By carpetaProvedoresCat = By.xpath("//li//div[text()='Proveedores']");
	By mensajeEmergente = By.xpath("/html/body/div[2]/div/div[5]");

	By transCRs = By.xpath("//div[normalize-space()='Transferencia de CRs']");
	@FindBy(xpath = "//div[normalize-space()='Transferencia de CRs']")
	public WebElement transCRsElement;

	By provFormaPago = By.xpath("//div[normalize-space()='Prov - Forma Pago']");
	@FindBy(xpath = "//div[normalize-space()='Prov - Forma Pago']")
	public WebElement provFormaPagoElement;

	By btnCrearConvenio = By.xpath("//*[@id='btnCrear']");
	@FindBy(xpath = "//*[@id='btnCrear']")
	public WebElement btnCrearConvenioElement;

	By inputPlaza = By.xpath("//*[@id='CRPlaza']");
	@FindBy(xpath = "//*[@id='CRPlaza']")
	public WebElement Plaza;

	By inputDistrito = By.xpath("//*[@id='CRDistrito']");
	@FindBy(xpath = "//*[@id='CRDistrito']")
	public WebElement Distrito;

	By inputTienda = By.xpath("//*[@id='CRTienda']");
	@FindBy(xpath = "//*[@id='CRTienda']")
	public WebElement Tienda;

	By inputRFC = By.xpath("//*[@id='RFC']");
	@FindBy(xpath = "//*[@id='RFC']")
	public WebElement RFC;

	By inputComisionistaNombre = By.xpath("//*[@id='ComisionistaNombre']");
	@FindBy(xpath = "//*[@id='ComisionistaNombre']")
	public WebElement ComisionistaNombre;

	By inputFechaInicialo = By.xpath("//*[@id='FechaInicial']");
	@FindBy(xpath = "//*[@id='FechaInicial']")
	public WebElement FechaInicial;

	By ChkSelDocsGen = By.xpath("//*[@id='ChkSelDocsGen']");
	@FindBy(xpath = "//*[@id='ChkSelDocsGen']")
	public WebElement ChkTipoDocumentoSelDocsGen;

	By btnIr = By.xpath("//*[@id='btnGo']");
	@FindBy(xpath = "//*[@id='btnGo']")
	public WebElement btnIrGeneracionDocumentosReversa;

	By cargaMasivaRenegociaciones = By.xpath("//div[normalize-space()='Carga Masiva de Renegociaciones']");
	@FindBy(xpath = "//div[normalize-space()='Carga Masiva de Renegociaciones']")
	public WebElement cargaMasivaRenegociacionesElement;

	/*
	 * Fin Elementos generales (botones, cajas de texto, etc)
	 */

	/*
	 * Inicio EBS Internacional Header
	 */

	By configuracion = By.xpath("//table[@id='globalHeaderID']//a[@title='Definiciones']");
	@FindBy(xpath = "//table[@id='globalHeaderID']//a[@title='Definiciones']")
	public WebElement configuracionElement;

	By preferencias = By.xpath("//span[@class='OraMenuItemText'][normalize-space()='Preferencias']");
	@FindBy(xpath = "//span[@class='OraMenuItemText'][normalize-space()='Preferencias']")
	public WebElement preferenciasElement;

	/*
	 * Fin EBS Internacional Header
	 */

	/**
	 * Metodo utilizado para checar cual es la responsabilidad que se va a probar
	 *
	 * @param Responsabilidad Texto que nos indica que responsabilidad es a la que
	 *                        se desea acceder para realizar las pruebas
	 * @return Un WebElement dependiendo del texto que recibe
	 * @author JesusH@hexaware.com
	 */
	public By responsabilidad(String Responsabilidad) {
		switch (Responsabilidad) {
		case "MXOXXO-APXXXXX-Super Usuario":
			return MXOXXOAPXXXXXSuperUsuario;
		case "MXOXXO-APXXXXX-ADMON-PROVEEDORES":
			return MXOXXOAPXXXXXADMONPROVEEDORES;
		case "MXOXXO-APXXXXX-CONSULTAS":
			return MXOXXOAPXXXXXCONSULTAS;
		case "MXOXXO-FNDXXXXX-Usuario carga masiva":
			return MXOXXOFNDXXXXXUsuarioCargaMasiva;
		case "MXOXXO-POXXXXX-Super Usuario Configurar":
			return MXOXXOFNDXXXXXsuperusuarioconfigurar;
		case "MXOXXO-FSXXXXX-Usuario_FISCAL_OS":
			return MXOXXOFSXXXXXUsuario_FISCAL_OS;
		case "MXOXXO-GLXXXXX-Super Usuario":
			return MXOXXOGLXXXXXSuperUsuario;
		case "MXXXXX-GLXXXXX-Consulta":
			return MXXXXXGLXXXXXConsulta;
		case "MXOXXO-COXXXXX-ADMINISTRADOR":
			return MXOXXOCOXXXXXADMINISTRADOR;
		case "MXOXXO-WM17FAA-COORDINADOR EQP FRM":
			return MXOXXOWM17FAACOORDINADOREQPFRM;
		case "PEOXXO-GLXXXXX-SUPER USUARIO":
			return PEOXXOGLXXXXXSUPERUSUARIO;
		case "MXOXXO-AR10MON-AJUSTE-TRANSACCIONES":
			return MXOXXOAR10MONAJUSTETRANSACCIONES;
		case "MXOXXO-IN17FAA-Admon_ACT_FIJO_OS":
			return MXOXXOIN17FAAAdmonACTFIJOOS;
		case "MXOXXO-PNXXXXX-Super Usuario":
			return MXOXXOPNXXXXSuperUsuario;
		case "MXOXXO-EAM10VCZ-EQUIPO-MANTO":
			return MXOXXOEAM10VCZEQUIPOMANTO;
		case "MXOXXO-GL17ADM-SERVICIOS":
			return MXOXXOGL17ADMSERVICIOS;
		case "MXOXXO-OM17FAA-CONSULTA FRM":
			return MXOXXOOM17FAACONSULTAFRM;
		case "MXOXXO-PO10HER-Compras":
			return MXOXXOPO10HERCompras;
		case "MXOXXO-PO10QRI-Compras":
			return MXOXXOPO10QRICompras;
		case "MXOXXO-POXXXXX-Super Usuario":
			return MXOXXOPOXXXXXSuperUsuario;
		case "MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS":
			return MXOXXOAPXXXXXADMONPROVEEDORESOPECATOS;
		case "MXOXXO-POXXXXX-PLANTILLA DE REQUISICIONES":
			return MXOXXOPOXXXXXPLANTILLADEREQUISICIONES;
		case "MXOXXO-AP10MON-USUARIO-FACTURA":
			return MXOXXOAP10MONUSUARIOFACTURA;
		case "MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS":
			return MXOXXOPNXXXXXAdmonOS_ACT_FIJO_OS;
		case "MXOXXO-ICXXXXX-CENTRAL":
			return MXOXXOICXXXXXCENTRAL;
		case "MXOXXO-GL10POZ-USUARIO CTL TIENDAS":
			return MXOXXOGL10POZUSUARIOCTLTIENDAS;
		case "MXFINA-GLXXXXX-Super Usuario_INF_FIN_OS":
			return MXFINAGLXXXXXSuperUsuarioINFFINOS;
		case "MXOXXO-POXXXXX-Solicitudes de Inversion":
			return MXOXXOPOXXXXXSolicitudesdeInversion;
		case "MXOXXO-FSXXXXX-Usuario":
			return MXOXXOFSXXXXXUsuario;
		case "MXOXXO-CO10VCZ-USUARIO-32SGR":
			return MXOXXOCO10VCZUSUARIO32SGR;
		case "MXOXXO-HELP-DESK":
			return MXOXXOHELPDESK;
		case "MXOXXO-POXXXXX-Consulta":
			return MXOXXOPOXXXXXConsulta;
		case "MXOXXO-EAMXXXXX-Super Usuario":
			return MXOXXOEAMXXXXXSuperUsuario;
		case "MXOXXO-APXXXXX-USUARIO-FACTURA":
			return MXOXXOAPXXXXXUSUARIOFACTURA;
		case "MXOXXO-HRXXXXX-Configurador":
			return MXOXXOHRXXXXXConfigurador;
		case "MXOXXO-POXXXXX-COMPRAS":
			return MXOXXOPOXXXXXCOMPRAS;
		case "MXOXXO-AP15IXM-Admon Proveedores":
			return MXOXXOAP15IXMAdmonProveedores;
		case "MXOXXO-AP10TIJ-USUARIO":
			return MXOXXOAP10TIJUSUARIO;
		case "MXOXXO-ARXXXXX-Super Usuario":
			return MXOXXOARXXXXXSuperUsuario;
		case "MXOXXO-AR10GTO-RECIBOS":
			return MXOXXOAR10GTORECIBOS;
		case "MXOXXO-CO10TMP-USUARIO":
			return MXOXXOCO10TMPUSUARIO;
		case "MXOXXO-CO10CHI-USUARIO":
			return MXOXXOCO10CHIUSUARIO;
		case "MXOXXO-CO10QRI-USUARIO":
			return MXOXXOCO10QRIUSUARIO;
		case "MXOXXO-CMXXXXX-Super Usuario":
			return MXOXXOCMXXXXXSuperUsuario;
		}
		return null;
	}

	/**
	 * Realiza un clic en la responsabilidad especificada
	 *
	 * @param responsabilidad Nombre de la responsabilidad a probar
	 * @author JesusH@hexaware.com
	 */
	public void clickOnResponsabilidad(String responsabilidad) {
		u.waitForLoadPage();
		try {
			By obj = responsabilidad(responsabilidad);
			u.highLight(obj);
			testCase.addScreenShotCurrentStep(u, "Responsabilidad");
			u.unhighLight(obj);
			u.click(obj);
			u.hardWait(2);
		} catch (Exception e) {
			logger.error("Error Responsabilidad no encontrada " + e);
			System.out.println("Error Responsabilidad no encontrada " + e);
		}

	}

	/**
	 * envio parametro de caja de busqueda
	 *
	 * @return By
	 * @author ricardo.palacios@neoris.com
	 */
	public By cajadebusqueda() {
		return cajadebusqueda;
	}

	/**
	 * envio parametro de frame
	 *
	 * @return By
	 * @author ricardo.palacios@neoris.com
	 */
	public By newframe() {
		return newFrame;
	}

	/**
	 * envio parametro de tabla
	 *
	 * @return By
	 * @author ricardo.palacios@neoris.com
	 */
	public By tabla() {
		return tabla;
	}

	/**
	 * Hace un remarcado y clic en la lupa de busqueda
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clicklupabusqueda() {
		u.highLight(lupabusqueda);
		u.click(lupabusqueda);
	}

	/**
	 * Hace un remarcado y clic en carga masiva de renegociaciones
	 *
	 * @author ricardo.palacios@neoris.com
	 */

	public void clickCargaMasivaRenegociaciones() {
		u.highLight(cargaMasivaRenegociaciones);
		u.click(cargaMasivaRenegociaciones);
	}

	/**
	 * Hace un remarcado y clic en MXOXXOPNXXXXXAdmonOS_ACT_FIJO_OSinterno
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickAdmonOSACTFIJOOSinterno() {
		u.highLight(AdmonOS_ACT_FIJO_OSinterno);
		u.click(AdmonOS_ACT_FIJO_OSinterno);
	}

	/**
	 * Hace un remarcado y clic en Reportes de Incrementos de Renta
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickCalculoIncrementosRenta() {
		u.highLight(calculoIncrementosdeRentas);
		u.click(calculoIncrementosdeRentas);
	}

	/**
	 * Hace un remarcado y clic en entry
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickEntry() {
		u.highLight(entry);
		u.click(entry);
	}

	/**
	 * Hace un remarcado y clic en XXFC - Carga de Gastos Addenda
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void cargaaddenda() {
		u.highLight(cargaaddenda);
		u.click(cargaaddenda);
		u.waitForLoadPage();
	}

	/**
	 * Hace un remarcado y clic en consulta de solicitudes
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void ClickrunConsultaSolicitud() {
		u.highLight(runConsultaSolicitud);
		u.click(runConsultaSolicitud);
	}

	/**
	 * Hace un remarcado y clic en captura de renegociacion de contratos
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickCapturaRenegociacionContratos() {
		u.highLight(runCapturaRenegociacionCancelacionContratos);
		u.click(runCapturaRenegociacionCancelacionContratos);
		u.waitForLoadPage();
	}

	/**
	 * Hace un remarcado y clic en XXFC - Carga de Gastos Addenda
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void aplicaraddenda() {
		u.highLight(aplicaraddenda);
		u.click(aplicaraddenda);
		u.waitForLoadPage();
	}

	/**
	 * Hace un remarcado y clic para seleccionar un archivo desde Carga de Gastos
	 * Addenda
	 *
	 * @param ruta
	 * @author ricardo.palacios@neoris.com
	 */
	public void seleccionaraddenda(String ruta) {
		WebElement uploadElement = u.getDriver().findElement(seleccionaraddenda);
		// enter the file path onto the file-selection input field
		uploadElement.sendKeys(ruta);
	}

	/**
	 * Hace un remarcado y clic en la carpeta carga de archivos
	 *
	 * @author JesusH@hexaware.com
	 */
	public void clickcargaarchivosr() {
		u.highLight(cargaarchivoscon);
		u.click(cargaarchivoscon);
		u.waitForLoadPage();
	}

	/**
	 * Hace un remarcado y clic en applet alta de centros responsabilidad
	 *
	 * @author ricardo.palacios
	 */
	public void clickAltaCentrosResponsabilidad() {
		u.highLight(altaCentrosResponsabilidad);
		u.click(altaCentrosResponsabilidad);
		u.hardWait(2);
	}

	/**
	 * Hace un remarcado y clic en la carpeta de Empleados
	 *
	 * @author JesusH@hexaware.com
	 */
	public void clickEmpleados() {
		u.highLight(carpetaEmpleados);
		u.click(carpetaEmpleados);
	}

	/**
	 * ejecuta applet stock locator
	 *
	 * @author ricardo.palacios
	 */
	public void clickStockLocators() {
		u.highLight(stockLocators);
		u.click(stockLocators);
	}

	/**
	 * Hace un remarcado y clic en la carpeta Standard
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickStandard() {
		u.highLight(standard);
		u.click(standard);
	}

	/**
	 * Hace un remarcado y clic en applet pedidios
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void regresarpedidos() {
		u.highLight(regresarpedidos);
		u.click(regresarpedidos);
	}

	/**
	 * Hace un remarcado y clic en ventana home en la paguina ebs
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void home() {
		u.highLight(home);
		u.click(home);
	}

	/**
	 * Hace un remarcado y clic en carpeta consultas de MXXXXX-GLXXXXX-Consultas
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void clickConsultas() {
		u.highLight(carpetaGlConsulta);
		u.click(carpetaGlConsulta);
	}

	/**
	 * Hace un remarcado y clic en el run de runAccount
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void runAccount() {
		u.highLight(runAccount);
		u.click(runAccount);
	}

	/**
	 * Hace un remarcado y clic en el
	 *
	 * @author ricardo.palacios@neoris.com
	 */
	public void runrequests() {
		u.highLight(runrequests);
		u.click(runrequests);
	}

	/**
	 * Metodo utilizado para checar cual es la carpeta que se va a probar
	 *
	 * @param Carperta Texto que nos indica que carpeta es a la que se desea acceder
	 *                 para realizar las pruebas
	 * @return Un WebElement dependiendo del texto que recibe
	 * @author JesusH@hexaware.com
	 */
	public By carperta(String Carperta) {
        System.out.print(Carperta.toLowerCase(Locale.ROOT));
		switch (Carperta.toLowerCase(Locale.ROOT)) {
		case "empleados":
			return carpetaEmpleados;
		case "facturas":
			return carpetaFacturas;
		case "plantilla de proveedores":
			return carpetaPlantillaDeProveedores;
		case "proveedoresadmonprove":
			return carpetaProveedoresAP;
		case "consulta proveedores":
			return consultaProveedores;
		case "proveedores":
			return carpetaProveedores;
		case "entrar":
			return carpetaFacturasEntrar;
		case "otros":
			return carpetaOtros;
		case "solicitudes":
			return solicitudes;
		case "admin":
			return administracionalmacen;
		case "o":
			return otros;
		case "so":
			return solicitudesotros;
		case "Requests":
			return request;
		case "reportes":
			return carpetaReportesCoAdmin;
		case "request1":
			return request1;
		case "r":
			return requests;
		case "lad add-on peru":
			return carpetaLadAddOnPeruPeGlSU;
		case "otro":
			return sotro;
		case "pedidos de trabajo":
			return pedidostrabajo;
		case "crs":
			return carpetaCRs;
		case "administracion de almacen":
			return administracionalmacen;
		case "otros administracion de almacen":
			return otrosWmCoEqpFrm;
		case "solicitudes administracion de almacen":
			return solicitudesWmCoEqpFrm;
		case "requests administracion de almacen":
			return requestsWmCoEqpFrm;
		case "informes":
			return carpetaInformes;
		case "Arrendamientos y Documentos":
			return carpetaArrendamientosDocumentos;
		case "Arrendamiento Principal":
			return carpetaArrendamientoPrincipal;
		case "otros consulta frm":
			return carpetaOtrosFRM;
		case "otros solicitudes frm":
			return solicitudesOtrosFRM;
		case "otros request frm":
			return requestOtrosFRM;
		case "solicitudes internas":
			return solicitudesInternasPoSu;
		case "reportes admon pro op ca os":
			return carpetaReportesAdmonProOpCaOs;
		case "proveedores admon pro op ca os":
			return carpetaProveedoresAdmonProOpCaOs;
		case "proveedorescat":
			return carpetaProvedoresCat;
		case "arrendamientos y documentos: arrendamiento principal":
			return carpetaArrendamientosDocumentosArrendamientoPrincipal;
		case "incremento de rentas":
			return carpetaIncrementoRentas;
		case "renegociacion cancelacion de contratos":
			return carpetaRenegociacionCancelacionContratos;
		case "nuevo sistema icm":
			return NuevoSistema;
		case "calculo de incrementos de rentas":
			return calculoDeIncrementosRentas;
		case "admonosctfijos":
			return AdmonOS_ACT_FIJO_OSinterno;
		case "configuracion gl super usuario":
			return carpetaConfiguracionGlSuperUsuario;
		case "financiera gl super usuario":
			return carpetaFinancieraGLSuperUsuario;
		case "flexfields gl super usuario":
			return carpetaFlexfieldsGLSuperUsuario;
		case "clave gl super usuario":
			return carpetaClaveGLSuperUsuario;
		case "sistema gl super usuario":
			return carpetaSistemaGLSuperUsuario;
		case "setup cm super usuario":
			return carpetaSetup;
		case "tabuladores co administrador":
			return carpetaTabuladores;
		case "solicitar gl 17 servicios":
			return carpetaSolicitarGL17Servicios;
		case "asientos gl super usuario":
			return carpetaAsientosGLSuperUsuario;
		case "carpeta articulosOs":
			return carpetaArticulos;
		case "carpetatienda":
			return carpetaTienda;
		case "personas hr configurador":
			return carpetaPersonasHRConfigurador;
		case "proveedores ap15 admon proveedores":
			return carpetaProveedoresAP15AdmonProveedores;
		case "facturas ap10 tij usuario":
			return carpetaFacturasAP10TijUsuario;
		case "ingreso factura ap10 tij usuario":
			return carpetaIngresoFactAP10TijUsuario;
		case "transacciones ar super usuario":
			return carpetaTransaccionesARSuperUsuario;
		case "transacciones ar10 mon ajuste transacciones":
			return carpetaTransaccionAR10MonAjusTran;
		case "reportes co10 mp usuario":
			return carpetaReportesCO10MPUsuario;
		case "reportes cp10 chi usuario":
			return carpetaReportesCO10CHIUsuario;
		case "pagos co10 chi usuario":
			return carpetaPagosCO10CHIUsuario;
		case "prestamo co10 chi usuario":
			return carpetaPrestamoC010CHIUsuario;
		case "nuevo sistema icm co10 chi usuario":
			return carpetaNuevoSistemaICMCO10CHIUsuario;
		case "configuracion ap super usuario":
			return carpetaConfiguracionAPSuperUsuario;
		case "consultas ap super usuario":
			return carpetaConsultasAPSuperUsuario;
		case "nuevo sistema icm co10 qri usuario":
			return carpetaNuevoSistemaICMCO10QRIUsuario;
		case "reportes cp10 qri usuario":
			return carpetaReportesCO10QRIUsuario;
		case "bank statements cm super usuario":
			return carpetaBankStatementsCMSuperUsuario;
		}

		return null;
	}

	/**
	 * Hace un remarcado y clic en la carpeta indicada
	 *
	 * @param carpeta Carpeta a la que se desea ingresar dentro de la
	 *                responsabilidad seleccionada
	 * @author JesusH@hexaware.com
	 */
	public void clickOnCarpeta(String carpeta) {
		try {
			By obj = carperta(carpeta);
			System.out.print(obj.toString());
			u.scroll();
			Actions action = new Actions(u.getDriver());
			action.moveToElement(u.getDriver().findElement(obj));
			action.perform();
			u.hardWait(1);
			u.highLight(obj);
			testCase.addScreenShotCurrentStep(u, "carpeta");
			u.unhighLight(obj);
			u.click(obj);
		} catch (Exception e) {
			logger.error("Error Carpeta no encontrada " + e);
			System.out.println("Error Carpeta no encontrada " + e);
		}
	}

	/**
	 * Realiza un remarcado y clic en la forma de Visualizar Empleados
	 *
	 * @author JesusH@hexaware.com
	 */
	public void clickVisualizarEmpleados() {
		u.highLight(visualizarEmpleados);
		u.click(visualizarEmpleados);

	}

	/**
	 * Realiza un remarcado y clic en la forma de Ejecutar Solicitudes
	 *
	 * @author ricardo.palacios
	 */
	public void clickEjecutarSolicitudes() {
		u.highLight(ejecutarsolicitud);
		u.click(ejecutarsolicitud);

	}

	/**
	 * Realiza un remarcado y clic en Articulos
	 *
	 * @author ricardo.palacios
	 */
	public void clickArticulos() {
		u.highLight(carpetaArticulos);
		u.click(carpetaArticulos);

	}

	/**
	 * Ejecuta un proceso para ver solicitudes en los applets
	 *
	 * @author ricardo.palacios
	 */
	public void verSolicitudApplets() {

		clickOnResponsabilidad("MXOXXO-APXXXXX-CONSULTAS");
		clickOnCarpeta("Otro");
		verSolicitudes();
	}

	/**
	 * Realiza un remarcado y clic en ver solicitudes
	 *
	 * @author ricardo.palacios
	 */
	public void verSolicitudes() {

		u.hardWait(1);
		u.highLight(versolicitudmetodo);
		u.click(versolicitudmetodo);

	}

	/**
	 * Realiza un remarcado y clic en la forma de ver Solicitudes
	 *
	 * @author ricardo.palacios
	 */
	public void clickVerSolicitudes() {
		u.highLight(versolicitudes);
		u.click(versolicitudes);

	}

	/**
	 * Realiza un remarcado y clic en articulos organizacion
	 *
	 * @author ricardo.palacios
	 */
	public void clickArticulosOrganizacion() {
		u.highLight(runArticulosOrganizacion);
		u.click(runArticulosOrganizacion);

	}

	/**
	 * Realiza navega al final de la pagina, realiza un remarcado y clic en la
	 * "Plantilla de Proveedores"
	 *
	 * @author JesusH@hexaware.com
	 */
	public void abrePlantillaDeProveedores() {
		try {
			u.highLight(plantillaDeProveedoresForm);
			testCase.addScreenShotCurrentStep(u, "abrePlantillaDeProveedores");
			u.click(plantillaDeProveedoresForm);
		} catch (TimeoutException e) {
			logger.error("Error " + e);
			System.out.println("Error " + e);
		}

	}

	/**
	 * Realiza un clic en hipervinculo de "Entry" localizado dentro de
	 * MXOXXOAPXXXXXAdmonProveedores \ Proveedores
	 *
	 * @author edwin.ramirez@neoris.com
	 */
	public void clickOnEntryAP() {
		u.click(entryProveedoresAP);
		u.waitForLoadPage();
	}

	/**
	 * Realiza clic en la carpeta MXOXXOAPXXXXXSuperUsuario \ Facturas \ Entrar
	 *
	 * @author edwin.ramirez@neoris.com
	 */
	public void clickFacturasEntrar() {
		u.highLight(facturasEntrar);
		u.click(facturasEntrar);
	}

	/**
	 * Realiza clic en la carpeta MXOXXOAPXXXXXSuperUsuario \ Facturas \ Entrar \
	 * Facturas
	 *
	 * @author edwin.ramirez@neoris.com
	 */
	public void clickEntrarFacturas() {
		u.highLight(facturasEntrarFacturas);
		u.click(facturasEntrarFacturas);
		u.waitForLoadPage();
	}

	/**
	 * Realiza clic en la carpeta MXOXXO-FSXXXXX-Usuario_FISCAL_OS \ XXFC - SAT
	 * Contabilidad Electronica \
	 *
	 * @author Jonofre@Hexaware.com
	 */
	public void clickContabilidadElectronica() {

		u.highLight(contabilidadElectronica);
		u.click(contabilidadElectronica);
	}

	/**
	 * Realiza clic en la carpeta MXOXXO-FSXXXXX-Usuario_FISCAL_OS \ XXFC - SAT
	 * Contabilidad Electronica \ Solicitudes de Contabilidad Electronica
	 *
	 * @author Jonofre@Hexaware.com
	 */
	public void clickSolicitudesContabilidadElectronica() {

		u.highLight(solicitudesContabilidadElectronica);
		u.click(solicitudesContabilidadElectronica);
		u.waitForLoadPage();
	}

	/**
	 * Realiza clic en la carpeta MXXXXX-GLXXXXX-Consulta \ Solicitudes
	 *
	 * @author Jonofre@Hexaware.com
	 */
	public void clickConsultaSolicitudes() {

		u.highLight(consultaSolicitudes);
		u.click(consultaSolicitudes);
		u.waitForLoadPage();
	}

	/**
	 * Realiza clic en la carpeta MXXXXX-GLXXXXX-Consulta \ Solicitudes \ Request \
	 * Standard
	 *
	 * @author Jonofre@Hexaware.com
	 */
	public void clickRequestStandard() {

		u.highLight(request1);
		u.click(request1);
		u.setWaitTimeSeconds(2000);
		u.highLight(requestStandard);
		u.click(requestStandard);
	}

	public void clickMantCuentasConatble() {
		u.highLight(mantCuentasConatble);
		u.click(mantCuentasConatble);

	}

	/**
	 * Realiza clic en la carpeta MXOXXO-WM17FAA-COORDINADOR EQP FRM Requests / Run
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunWmCoEqpFrm() {
		u.highLight(runWmCoEqpFrm);
		u.click(runWmCoEqpFrm);
	}

	/**
	 * Hace un remarcado y clic en la carpeta de Solicitudes de la Responsabilidad
	 * 'MXOXXO-AR10MON-AJUSTE-TRANSACCIONES'
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickTransaccionesSolicitudes() {
		u.highLight(carpetaTransaccionesSolicitudes);
		u.click(carpetaTransaccionesSolicitudes);
	}

	/**
	 * Hace un remarcado y clic en la carpeta de Solicitudes \ RUN de la
	 * Responsabilidad 'MXOXXO-AR10MON-AJUSTE-TRANSACCIONES'
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickAppTransacciones() {
		u.highLight(runTransaccion);
		u.click(runTransaccion);
	}

	/**
	 * Hace un remarcado y clic en Solicitudes de la responsabilidad
	 * 'MXOXXO-IN17FAA-Admon_ACT_FIJO_OS'
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickSolicitudesAdmonACTFIJO() {
		u.highLight(solicitudesActFijos);
		u.click(solicitudesActFijos);
	}

	/**
	 * Realiza clic en la carpeta MXOXXO-PNXXXXX-Super Usuario \ Arrendamientos y
	 * Documentos \ Arrendamiento Principal \ Visualizar Arrendamientos y Documentos
	 *
	 * @author Jonofre@Hexaware.com
	 */
	public void clickVisualizarArrendamientosDocs() {

		u.highLight(carpetaArrendamientosDocumentos);
		u.click(carpetaArrendamientosDocumentos);
		u.hardWait(2);
		u.highLight(carpetaArrendamientoPrincipal);
		u.click(carpetaArrendamientoPrincipal);
		u.hardWait(2);
		u.highLight(visualizarArrendamientosDoc);
		u.click(visualizarArrendamientosDoc);
		u.waitForLoadPage();
	}

	/**
	 * Realiza clic en la carpeta MXOXXO-PNXXXXX-Super Usuario \ Configuracion y
	 * ejecutar el applet Reclasificacion de Contratos y Direcciones NEXXO
	 *
	 * @author Jonofre@Hexaware.com
	 */
	public void clickReclasificacionContratosDireccionesNexxo() {

		u.highLight(carpetaSuperUsuarioConfiguracion);
		u.click(carpetaSuperUsuarioConfiguracion);
		u.hardWait(2);
		u.highLight(reclasifContratosyDirNEXXO);
		u.click(reclasifContratosyDirNEXXO);
		u.hardWait(2);
		u.waitForLoadPage();
	}

	/**
	 * Hace un remarcado y clic en Run de Otros,Solicitudes, Requests de la
	 * responsabilidad 'MXOXXO-OM17FAA-CONSULTA FRM'
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickRunRequestConsultaFRM() {

		u.highLight(solicitudesOtrosFRM);
		u.click(solicitudesOtrosFRM);
		u.hardWait(2);
		u.highLight(requestOtrosFRM);
		u.click(requestOtrosFRM);
		u.hardWait(2);
		u.highLight(runRequestFRM);
		u.click(runRequestFRM);
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-POXXXXX-Super Usuario Solicitudes Internas
	 * / Resumen de Solicitud Interna
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickResumenSolicitud() {
		u.highLight(resumenSolicitudInternaPoSu);
		u.click(resumenSolicitudInternaPoSu);
	}

	/**
	 * Hace un remarcado y clic en el formulario carga de archivos
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickCargaArchivosAdmonProOpCaOs() {
		u.highLight(cargaArchivosAdmonProOpCaOs);
		u.click(cargaArchivosAdmonProOpCaOs);
		u.waitForLoadPage();
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS
	 * Reportes / Ver Solicitudes
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickVerSolicitudesAdmonProOpCaOs() {
		u.highLight(verSolicitudesAdmonProOpCaOs);
		u.click(verSolicitudesAdmonProOpCaOs);
	}

	/**
	 * Hace un remarcado y clic en Órdenes de Compra de la responsabilidad
	 * 'MXOXXO-PO10HER-Compras'
	 *
	 * @author JoseO@hexaware.com
	 */
	public void clickOrdenesDeCompra() {

		u.highLight(ordenesDeCompra);
		u.click(ordenesDeCompra);
		u.hardWait(2);

	}
	
	/**
	 * Hace un remarcado y clic en Órdenes de Compra de la responsabilidad indicada
	 *
	 * @author andreaalejandrac@hexaware.com
	 */
	public void clickOrdenesDeCompra(String responsabilidad) {

		By loc = By.xpath(String.format(ordenesDeCompraRes, responsabilidad));
		//u.highLight(loc);
		testCase.addScreenShotCurrentStep(u, "ordenesDeCompra");
		u.click(loc);
		u.hardWait(2);

	}

	/**
	 * Realiza un clic en hipervinculo de "Entry" localizado dentro de
	 * MXOXXO-APXXXXX-ADMON-PROVEEDORES_OPE_CAT_OS \ Proveedores \ Entry
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickOnEntryAmonProOpCaOs() {
		u.click(entryAdmonProOpCaOs);
		u.waitForLoadPage();
	}

	/**
	 * Realiza clic en la carpeta MXOXXO-PNXXXXX-Super Usuario \ Aprobar y Exportar
	 * Pagos \ Aprobacion de Pagos
	 *
	 * @author JoseO@Hexaware.com
	 */
	public void clickOnAprobacionPagos() {

		u.highLight(carpetaAprobarExportarPagos);
		u.click(carpetaAprobarExportarPagos);
		u.hardWait(5);
		u.highLight(runAprobacionPagos);
		u.click(runAprobacionPagos);
		u.hardWait(5);

	}

	/**
	 * Realiza click en la carpeta MXOXXO-PNXXXXX-Super Usuario \ Aprobar y Exportar
	 * Pagos \ Exportar Pagos a Cuentas por Cobrar
	 *
	 * @author JoseO@Hexaware.com
	 */
	public void clickOnExportarPagosCuentasPorCobrar() {

		u.highLight(carpetaAprobarExportarPagos);
		u.click(carpetaAprobarExportarPagos);
		u.hardWait(5);
		u.highLight(runExportarPagosCuentasPorCobrar);
		u.click(runExportarPagosCuentasPorCobrar);
		u.hardWait(5);

	}

	/**
	 * Realiza click en la carpeta MXOXXO-PNXXXXX-Super Usuario \ Aprobar y Exportar
	 * Pagos \ Informe de pagos Aprobados
	 *
	 * @author JoseO@Hexaware.com
	 */
	public void clickOnInformePagosAprobados() {

		u.highLight(carpetaAprobarExportarPagos);
		u.click(carpetaAprobarExportarPagos);
		u.hardWait(5);
		u.highLight(runInformePagosAprobados);
		u.click(runInformePagosAprobados);
		u.hardWait(5);

	}

	/**
	 * Realiza click en la carpeta MXOXXO-PNXXXXX-Super Usuario \ Reserva de Rentas
	 * \ Proceso de Reservas
	 *
	 * @author JoseO@Hexaware.com
	 */
	public void clickOnProcesoReserva() {

		u.highLight(carpetaProcesoDeReservas);
		u.click(carpetaProcesoDeReservas);
		u.hardWait(5);
		u.highLight(runProcesoDeReserva);
		u.click(runProcesoDeReserva);
		u.hardWait(5);

	}

	/**
	 * Realiza click en la carpeta MXOXXO-PNXXXXX-Super Usuario \ Reserva de Rentas
	 * \ Reportes de Reservas
	 *
	 * @author JoseO@Hexaware.com
	 */

	public void clickOnReportesReserva() {

		u.highLight(carpetaProcesoDeReservas);
		u.click(carpetaProcesoDeReservas);
		u.hardWait(5);
		u.highLight(runReportesDeReserva);
		u.click(runReportesDeReserva);
		u.hardWait(5);

	}

	/**
	 * Hace click en la Renegociacion Y Cancelacion de contratos
	 *
	 * @author JoseO@Hexaware.com
	 */
	public void clickOnCapturaRenegociacionContratos() {

		u.highLight(carpetaRenegociacionCancelacionContratos);
		u.click(carpetaRenegociacionCancelacionContratos);
		u.hardWait(5);
		u.highLight(runCapturaRenegociacionCancelacionContratos);
		u.click(runCapturaRenegociacionCancelacionContratos);
		u.hardWait(5);

	}

	/**
	 * Ingresa al campo de Solicitud Standard
	 *
	 * @author JoseO@Hexaware.com
	 */
	public void clickOnInformesSolicitudStandard() {

		u.highLight(carpetaInformesGLXXXXXCONSULTAS);
		u.click(carpetaInformesGLXXXXXCONSULTAS);
		u.hardWait(5);
		u.highLight(carpetaInformesSolicitarGLXXXXXCONSULTAS);
		u.click(carpetaInformesSolicitarGLXXXXXCONSULTAS);
		u.hardWait(5);
		u.highLight(runInformesSolicitarStandard);
		u.click(runInformesSolicitarStandard);
		u.hardWait(10);
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS
	 * Arrendamientos y Documentos: Arrendamiento Principal / Visualizar
	 * Arrendamientos y Documentos
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickVisualizarArrendamientosDocumentos() {
		u.highLight(runVisualizarArrendamientosDocumentos);
		u.click(runVisualizarArrendamientosDocumentos);

	}

	/**
	 * Hace un remarcado y clic en despachos contables
	 *
	 * @author ricardo.palacios
	 */
	public void ClickDespachosContables() {
		u.highLight(despachosContables);
		u.click(despachosContables);

	}

	/**
	 * Hace un remarcado y click en MXOXXO-ICXXXXX-CENTRAL / Creacion convenios
	 *
	 * @author JoseO@Hexaware.com
	 */
	public void clickCreacionConvenios() {

		u.highLight(creacionConvenios);
		u.click(creacionConvenios);
		u.waitForLoadPage();
		u.click(btnCrearConvenio);

	}

	/**
	 * Hace un remarcado y clic en MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS Incremento de
	 * Rentas / Calculo de Incremento de Rentas
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickCalculoIncrementoRentas() {
		u.highLight(runCalculoIncrementoRentas);
		u.click(runCalculoIncrementoRentas);

	}

	/**
	 * Hace un remarcado y clic en MXOXXO-PNXXXXX-Admon OS_ACT_FIJO_OS Incremento de
	 * Rentas / Calculo de Incremento de Rentas / Captura de
	 * Renegociacio/Cancelacion de Contratos
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickCapturaRenegociacionCancelacionContratos() {
		u.highLight(runCapturaRenegociacionCancelacionContratos);
		u.click(runCapturaRenegociacionCancelacionContratos);

	}

	/**
	 * Hace un remarcado en la opcion de reporte Recepcion, Clonacion, Cancelacion
	 * de la Responsabiliadad MXOXXO-ICXXXXX-CENTRAL
	 *
	 * @author JoseO@Hexaware.com
	 */
	public void clickOnRecepcionClonacionCancelacion() {

		u.highLight(ByRecepcionClonacionCancelacion);
		testCase.addScreenShotCurrentStep(u, "RecepcionClonacionCancelacion");
		u.click(ByRecepcionClonacionCancelacion);
		testCase.addScreenShotCurrentStep(u, "clickRecepcionClonacionCancelacion");
		u.hardWait(5);

	}

	/**
	 * Hace un remarcado en la opcion de Reporte de Convenio de la Responsabilidad
	 * MXOXXO-ICXXXXX-CENTRAL
	 *
	 * @author JoseO@Hexaware.com
	 */
	public void clickOnReporteDeConvenio() {

		u.highLight(ReporteDeConvenios);
		testCase.addScreenShotCurrentStep(u, "ReporteDeConvenio");
		u.click(ReporteDeConvenios);
		testCase.addScreenShotCurrentStep(u, "clickOnReporteDeConvenio");
		u.hardWait(5);

	}

	/**
	 * Hace un remarcado en la opcion de Reportes IC de la Responsabilidad
	 * MXOXXO-ICXXXXX-CENTRAL
	 */
	public void clickOnReporteDeConvenioIC() {

		u.highLight(ReporteDeConveniosIC);
		testCase.addScreenShotCurrentStep(u, "ReporteIC");
		u.click(ReporteDeConveniosIC);
		testCase.addScreenShotCurrentStep(u, "clickReporteIC");
		u.hardWait(5);

	}

	/**
	 * Método para ingresar a la siguiente ruta: Nuevo sistema ICM/Generacion de
	 * Documentos y Reversa
	 *
	 * @author misraimH@hexaware.com
	 */
	public void IngresarMenuGeneracionDocumentosReversa() {
		u.waitForLoadPage();
		try {
			u.highLight(NuevoSistema);
			u.click(NuevoSistema);
			u.highLight(generacionDocumentosReversa);
			u.click(generacionDocumentosReversa);
			System.out.println("Ingresar a la siguiente ruta: Nuevo sistema ICM/Generacion de Documentos y Reversa");
		} catch (Exception e) {
			logger.error("Error ruta no encontrada " + e);
			System.out.println("Error ruta no encontrada " + e);
		}
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-GLXXXXX-SUPER USUARIO Configuración /
	 * Financiera / Flexfields / Clave / Valores
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickValoresGLSuperUsuario() {
		u.highLight(runValoresGLSuperUsuario);
		u.click(runValoresGLSuperUsuario);

	}

	/**
	 * Método para Capturar parámetros de busqueda:Plaza, Distrito, Tienda, RFC,
	 * FechaInicial
	 *
	 * @param Plaza        ejemplo '10MEX'
	 * @param Distrito     ejemplo '31HSW'
	 * @param Tienda       ejemplo '5008N'
	 * @param RFC          ejemplo 'CARR710203FY4'
	 * @param FechaInicial ejemplo '01-Ene-2010'
	 * @author misraimH@hexaware.com
	 */
	public void capturarParametrosGeneracionDocumentosReversa(String Plaza, String Distrito, String Tienda, String RFC,
			String FechaInicial) {
		u.waitForLoadPage();
		try {
			u.highLight(inputPlaza);
			u.sendKeys(inputPlaza, Plaza);
			u.highLight(inputDistrito);
			u.sendKeys(inputDistrito, Distrito);
			u.hardWait(2);
			u.highLight(inputTienda);
			u.sendKeys(inputTienda, Tienda);
			u.hardWait(2);
			u.highLight(inputRFC);
			u.sendKeys(inputRFC, RFC);
			u.highLight(inputComisionistaNombre);
			u.highLight(inputFechaInicialo);
			u.sendKeys(inputFechaInicialo, FechaInicial);
			u.highLight(ChkSelDocsGen);
			u.click(ChkSelDocsGen);

			u.highLight(btnIr);
			u.click(btnIr);

			System.out.println("Capturar parámetros de busqueda:Plaza, Distrito, Tienda, RFC, FechaInicial");
		} catch (Exception e) {
			logger.error("Error  " + e);
			System.out.println("Error  " + e);
		}
	}

	/**
	 * Método para ingresar a la siguiente ruta:Solicitudes/Ejecutar una Nueva
	 * Solicitud/Solicitud Unica
	 *
	 * @author misraimH@hexaware.com
	 */
	public void IngresarSolicitudUnica() {
		u.waitForLoadPage();
		try {
			u.highLight(carpetaReportesCoAdmin);
			u.click(carpetaReportesCoAdmin);
			u.highLight(runCoAdmin);
			u.click(runCoAdmin);
			u.hardWait(15);
			System.out
					.println("Ingresar a la siguiente ruta: Solicitudes/Ejecutar una Nueva Solicitud/Solicitud Unica");
		} catch (Exception e) {
			logger.error("Error ruta no encontrada " + e);
			System.out.println("Error ruta no encontrada " + e);
		}
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-GLXXXXX-SUPER USUARIO Configuración /
	 * Sistema / Consultas de General Ledger
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickConsultasLedgerGLSuperUsuario() {
		u.highLight(runConsultasLedgerGLSuperUsuario);
		u.click(runConsultasLedgerGLSuperUsuario);

	}

	/**
	 * Hace un remarcado y clic en MXOXXO-CMXXXXX-Super Usuario Setup / Prov - Forma
	 * Pago
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickProvFormaPago() {
		u.highLight(provFormaPago);
		u.click(provFormaPago);

	}

	/**
	 * Método para Ejecutar una Solicit
	 *
	 * @author misraimH@hexaware.com
	 */
	public void EjecutarSolicitud() {
		u.waitForLoadPage();
		try {
			u.highLight(carpetaOtros);
			u.click(carpetaOtros);
			u.highLight(solicitudesotros);
			u.click(solicitudesotros);
			u.hardWait(20);
			System.out.println("Ingresar a la siguiente ruta: Ver solicitudes Ejecutar Solicitud");
		} catch (Exception e) {
			logger.error("Error ruta no encontrada " + e);
			System.out.println("Error ruta no encontrada " + e);
		}
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-COXXXXX-ADMINISTRADOR Tabuladores /
	 * Servicios, Prod. y Formas Pago
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunServiciosProdFormasPago() {
		u.highLight(runServiciosProdFPago);
		u.click(runServiciosProdFPago);

	}

	/**
	 * Hace un remarcado y clic en MXOXXO-GL17ADM-SERVICIOS Informes / Solicitar y
	 * Standard
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunStandardGLServicios() {
		u.highLight(runStandard);
		u.click(runStandard);
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-GLXXXXX-SUPER USUARIO Asientos / Enter
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunEnterGLSuperUsuario() {
		u.highLight(runEnterGLSuperUsuario);
		u.click(runEnterGLSuperUsuario);
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-WM17FAA-COORDINADOR EQP FRM / Plantillas
	 * de Carta Porte
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickplantillasCartaPorte() {
		u.highLight(plantillasCartaPorte);
		u.click(plantillasCartaPorte);
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-WM17FAA-COORDINADOR EQP FRM / Stock
	 * Locators
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickrunStockLocatorsCoFrm() {
		u.highLight(runStockLocatorsCoFrm);
		u.click(runStockLocatorsCoFrm);
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-HELP-DESK / Usuarios
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunUsuariosHD() {
		u.highLight(runUsuariosHD);
		u.click(runUsuariosHD);
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-POXXXXX-Consulta / Consulta de Ordenes de
	 * compra
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunConsultaOrdenesCompraPOConsulta() {
		u.highLight(runConsultaOrdenesCompraPOConsulta);
		u.click(runConsultaOrdenesCompraPOConsulta);
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-EAMXXXXX-Super Usuario / Informes
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunInformesEAMSuperUsuario() {
		u.highLight(runInformesEAMSuperUsuario);
		u.click(runInformesEAMSuperUsuario);
	}

	/**
	 * Se envía el parámetro con el mensaje de error
	 *
	 * @return
	 * @author JessicaR@hexaware.com
	 */
	public String getTextMensajeError() {

		u.highLight(mensajeEmergente);
		return u.getText(mensajeEmergente);
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-POXXXXX-COMPRAS / Órdenes de Compra
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunOrdenesCompraPO() {
		u.highLight(runOrdenesCompraPO);
		u.click(runOrdenesCompraPO);
	}

	/**
	 * Hace un remarcado y clic en MXOXXO-HRXXXXX-Configurador / Personas / Ingresar
	 * y mantener
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunIngresarMantenerHRConfigurador() {
		u.highLight(runIngresarMantenerHRConfigurador);
		u.click(runIngresarMantenerHRConfigurador);
	}

	/**
	 * Hace un remrcado y clic en MXOXXO-AP15IXM-Admon Proveedores / Proveedores /
	 * Entry
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickformularioEntryAP15AdmonProveedores() {
		u.highLight(entryAP15AdmonProveedores);
		u.click(entryAP15AdmonProveedores);

	}

	/**
	 * Hace un remarcado y click en MXOXXO-AP10TIJ-USUARIO / Facturas / Ingreso /
	 * Facturas
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunFacturasAP10TijUsuario() {
		u.highLight(runFacturasAP10TijUsuario);
		u.click(runFacturasAP10TijUsuario);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-ARXXXXX-Super Usuario / Transacciones /
	 * Transacciones
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunTransaccionesARSuperUsuario() {
		u.highLight(runTransaccionesARSuperUsuario);
		testCase.addScreenShotCurrentStep(u, "transacciones");
		u.click(runTransaccionesARSuperUsuario);
		u.unhighLight(runTransaccionesARSuperUsuario);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-AR10MON-AJUSTE-TRANSACCIONES /
	 * Transacciones / Transacciones
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunTrasaccionesAR10MonAjusteTran() {
		u.highLight(runTrasaccionesAR10MonAjusteTran);
		u.click(runTrasaccionesAR10MonAjusteTran);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-AR10GTO-RECIBOS / Recibos
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickrunRecibosAR10GTORecibos() {
		u.highLight(runRecibosAR10GTORecibos);
		u.click(runRecibosAR10GTORecibos);
	}

	/**
	 * Hace un remarcado y click en carpeta Solicitar
	 * 
	 * @author MariaD1@hexaware.com
	 */
	public void clickcarpetaSolicitar() {
		u.highLight(carpetaSolicitar);
		testCase.addScreenShotCurrentStep(u, "carpetaSolicitar");
		u.unhighLight(carpetaSolicitar);
		u.click(carpetaSolicitar);
	}
	
	/**
	 * Hace un remarcado y click en runStandard
	 * 
	 * @author MariaD1@hexaware.com
	 */
	public void clickrunStandard() {
		u.highLight(runStandard);
		testCase.addScreenShotCurrentStep(u, "runStandard");
		u.unhighLight(runStandard);
		u.click(runStandard);
	}
	
	/**
	 * Hace un remarcado y click en MXOXXO-CO10TMP-USUARIO / Reportes / Run
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunReportesCO10MPUsuario() {
		u.highLight(runReportesCO10MPUsuario);
		u.click(runReportesCO10MPUsuario);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-CO10CHI-USUARIO / Reportes / Run
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunReportesCO10CHIUsuario() {
		u.highLight(runReportesCO10CHIUsuario);
		testCase.addScreenShotCurrentStep(u,"clickRunReportesCO10CHIUsuario");
		u.click(runReportesCO10CHIUsuario);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-CO10CHI-USUARIO / Pagos / Generación de
	 * Pagos a Cuenta de Comisionista
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunGeneracionPagosCuentaComisionistas() {
		u.highLight(runGeneracionPagosCuentaComisionistas);
		u.click(runGeneracionPagosCuentaComisionistas);
	}
	
	/**
	 * Hace un remarcado y click en MXOXXO-CO10CHI-USUARIO / Prestamo / Prestamos
	 * 
	 * @author AndreaalejandraC@hexaware.com
	 */
	public void clickRunPrestamoCO10CHIUsuario() {
		u.highLight(runPrestamoCO10CHIUsuario);
		u.click(runPrestamoCO10CHIUsuario);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-CO10CHI-USUARIO / Prestamo / Prestamos
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunPrestamosCO10CHIUsuario() {
		u.highLight(runPrestamosCO10CHIUsuario);
		u.click(runPrestamosCO10CHIUsuario);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-CO10CHI-USUARIO / Nuevo sistema ICM /
	 * Generacion de Documentos y Reversa
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickGeneracionDocumentosReversasCO10CHIUsuario() {
		u.highLight(generacionDocumentosReversasCO10CHIUsuario);
		u.click(generacionDocumentosReversasCO10CHIUsuario);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-APXXXXX-Super Usuario / Configuración /
	 * Consultas / Cuentas a Pagar
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunCuentasAPagarAPSuperUsuario() {
		u.highLight(runCuentasAPagarAPSuperUsuario);
		u.click(runCuentasAPagarAPSuperUsuario);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-CO10QRI-USUARIO / Nuevo sistema ICM /
	 * Pago Adelantado Semanal del Líder
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickPagoAdelantadoSemanalDelLider() {
		u.highLight(pagoAdelantadoSemanalDelLider);
		u.click(pagoAdelantadoSemanalDelLider);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-CO10QRI-USUARIO / Reportes / Run
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunReportesCO10QRIUsuario() {
		u.highLight(runReportesCO10QRIUsuario);
		u.click(runReportesCO10QRIUsuario);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-CMXXXXX-Super Usuario / Bank Statements /
	 * Bank Statement Interface
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunBankStatementInterfaceCMSuperUsuario() {
		u.highLight(runBankStatementInterfaceCMSuperUsuario);
		u.click(runBankStatementInterfaceCMSuperUsuario);
	}

	/**
	 * Hace un remarcado y click en MXOXXO-GLXXXXX-Super Usuario / Otros /
	 * Solicitudes
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunSolicitudesGLSuperUsuario() {
		u.highLight(runSolicitudes);
		u.click(runSolicitudes);
	}
}
