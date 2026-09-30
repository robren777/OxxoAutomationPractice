package EBS.util.pageObjectModel;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import EBS.util.EBSDeskApp.pomSimetrik;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Clase con los locators y acciones de la pagina de "EBS inicio"
 *
 * @author JesusH@hexaware.com
 * @version 1.0
 * @since 5/23/2022
 */
public class EBSPaginaInicial extends pomSimetrik {

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
		super(u, testCase);
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	public EBSPaginaInicial(SeleniumUtil u, TestCase testCase, String PatternsImg) {
		super(u, testCase, PatternsImg);
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
	// By MXOXXOCMXXXXXSuperUsuario =
	// By.xpath("//div[normalize-space()='MXOXXO-CMXXXXX-Super Usuario']");
	By MXOXXOCMXXXXXCedisCluster = By.xpath("//div[normalize-space()='MXOXXO-GLXXXXX-Administrador CEDIS Clúster']");
	By valoresCRCEDISCLUSTERS = By.xpath("//*[contains(text(),'Valores de CR CEDIS CLUSTERS')]");

	By MXOXXOGL10PCKCONSULTA = By.xpath("//div[normalize-space()='MXOXXO-GL10PCK-CONSULTA']");
	By MXOXXOGL10PCKUSUARIO = By.xpath("//div[normalize-space()='MXOXXO-GL10PCK-Usuario']");
	By MXOXXOGL10CANCONSULTA = By.xpath("//div[normalize-space()='MXOXXO-GL10CAN-CONSULTA']");
	By MXOXXOGL10CANUSUARIO = By.xpath("//div[normalize-space()='MXOXXO-GL10CAN-Usuario']");

	By MXOXXOGL10MONConsulta = By.xpath("//div[normalize-space()='MXOXXO-GL10MON-CONSULTA']");

	By MXOXXOGLBackOfficeConsiliaciones = By
			.xpath("//*[contains(text(),'MXOXXO-GL-BackOffice Conciliaciones SF y SNF')]");
	By Facturas = By.xpath("//*[contains(text(),'Facturas')]");
	By OrdenesDecompra = By.xpath("(//*[contains(text(),'de Compra')])[2]");
	By purchaseOrder = By.xpath("//div[normalize-space()='Purchase Orders']");
	By INV_Transacciones = By.xpath("(//div[normalize-space()='Transacciones'])[3]");
	By INV_Receiving = By.xpath("(//div[text()='Receiving'])[1]");
	By INV_Receipts = By.xpath("(//div[normalize-space()='Receipts'])[1]");
	By MXOXXOINVXXXXXSuperUsuario = By.xpath("//div[normalize-space()='MXOXXO-INXXXXX-Super Usuario']");
	By MXOXXO_APXXXXX_Pagos = By.xpath("//div[normalize-space()='Pagos']");
	By MXOXXO_APXXXXX_PagosEntrar = By.xpath("//div[normalize-space()='Pagos']");
	By MXOXXOCMXXXXXSuperUsuario = By.xpath("//div[@class='textdivresp' and text()='MXOXXO-APXXXXX-Super Usuario']");
	By MXOXXOCMXXXXXSuperUsuarioInvoices = By.xpath("//div[@class='textdivresp' and text()='Invoices']");
	By MXOXXOCMXXXXXSuperUsuarioInvoicesEntry = By.xpath("//div[@class='textdivresp' and text()='Entry']");
	By MXOXXOCMXXXXXSuperUsuarioInvoicesEntryInvoices = By.xpath("//div[@class='textdiv' and text()='Invoices']");
	By MXOXXOCC17FAAUsuarioActFijoOS = By.xpath("//*[contains(text(),'MXOXXO-CC17FAA-Usuario_ACT_FIJO_OS')]");
	By MXOXXOCC17FAAUsuarioActFijoOSReportes = By.xpath("//*[contains(text(),'Reportes')]");
	By MXOXXOCC17FAAUsuarioActFijoOSRun = By.xpath("//*[contains(text(),'Run')]");
	By MXOXXOCC17FAAUsuarioActFijoOSCargosCentrales = By
			.xpath("//*[@id=\"53448:50120:92291:0:mainMenuREST5wjpIn-0\"]/a/div[2]");
	By MXOXXOCC17FAAUsuarioActFijoOSCargosCentralesCapturaCargosCentrales = By
			.xpath("//*[@id=\"LICargos_Centrales\"]/a/div[2]");
	By MXOXXOAP17ADMUsuarioOSADTIVO_OS = By.xpath("//*[contains(text(),'MXOXXO-AP17ADM-USUARIO_OS_ADTIVO_OS')]");
	By MXOXXOAP17ADMUsuarioOSADTIVO_OS_Prorrateo_Central = By
			.xpath("//div[@class='textdivresp' and text()='Prorrateo Central']");
	By MXOXXOAP17ADMUsuarioOSADTIVO_OS_Prorrateo_Central_Prorrateo_Central = By
			.xpath("//*[@id=\"LIProrrateo_Central\"]/a/div[2]");
	By MXOXXO_GLXXXXX_SuperUsuario = By.xpath("//div[@class='textdivresp' and text()='MXOXXO-GLXXXXX-Super Usuario']");
	By MXOXXO_GLXXXXX_SuperUsuarioJournals = By.xpath("//div[@class='textdivresp' and text()='Journals']");
	By MXOXXO_GLXXXXX_SuperUsuarioJournalsEnter = By.xpath("//div[@class='textdiv' and text()='Enter']");
	By MXOXXXO_APXXXXX_CONSULTAS_FISCAL_OS = By
			.xpath("//div[@class='textdivresp' and text()='MXOXXO-APXXXXX-CONSULTAS_FISCAL_OS']");
	By MXOXXXO_APXXXXX_CONSULTAS_FISCAL_OS_Otros = By.xpath("//div[@class='textdivresp' and text()='Otro']");
	By MXOXXXO_APXXXXX_CONSULTAS_FISCAL_OS_Otros_EjecutarSolicitud = By
			.xpath("//div[@class='textdiv' and text()='Ejecutar Solicitud']");
	By MXOXXOAP17ADMUsuarioOSADTIVO_OS_AP_Usuario = By.xpath("//div[@class='textdivresp' and text()='AP Usuario']");
	By MXOXXOAP17ADMUsuarioOSADTIVO_OS_AP_Usuario_Otro = By.xpath("//div[@class='textdivresp' and text()='Otro']");
	By MXOXXOAP17ADMUsuarioOSADTIVO_OS_AP_Usuario_Otro_EjecutarSolicitud = By
			.xpath("//div[@class='textdiv' and text()='Ejecutar Solicitud']");
	By MXOXXO_GLXXXXX_SuperUsuarioFacturas = By.xpath("//div[@class='textdivresp' and text()='Facturas']");
	By MXOXXO_GLXXXXX_SuperUsuarioFacturasEntrar = By.xpath("//div[@class='textdivresp' and text()='Entrar']");
	By MXOXXO_GLXXXXX_SuperUsuarioFacturasEntrarFacturas = By.xpath("//div[@class='textdiv' and text()='Facturas']");
	By MXOXXOOM17FAAMESACONTROLFRM = By.xpath("//div[normalize-space()='MXOXXO-OM17FAA-MESA CONTROL FRM']");
	By standard17admServicios = By.xpath("//div[text()='Standard']");// ----
	// Super Usuario PO
	By MXOXXO_POXXXXX_Super_Usuario = By.xpath("//div[@class='textdivresp' and text()='MXOXXO-POXXXXX-Super Usuario']");
	By MXOXXO_POXXXXX_Super_Usuario_OrdenesDeCompra = By
			.xpath("//div[@class='textdivresp' and text()='Órdenes de Compra']");
	By MXOXXO_POXXXXX_Super_Usuario_PurchaseOrderSummary = By
			.xpath("//div[@class='textdiv' and text()='Purchase Order Summary']");
	// Usuario Recepcion OC
	By MXOXXO_IN17FAA_Admon_ACT_FIJO_OS = By
			.xpath("//div[@class='textdivresp' and text()='MXOXXO-IN17FAA-Admon_ACT_FIJO_OS']");
	By MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_Transacciones = By
			.xpath("//div[@class='textdivresp' and text()='Transacciones']");
	By MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_Recepcion = By.xpath("//div[@class='textdivresp' and text()='Recepción']");
	By MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_Receipts = By.xpath("//div[@class='textdiv' and text()='Receipts']");
	By MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_ViewReceivingTransactions = By.xpath("//div[@class='textdiv' and text()='View Receiving Transactions']");
	By logoutEBS = By.xpath(
			"//div[img[contains(@src,'logout_greyscale.gif') and @title='Desconectarse' and @alt='Desconectarse']]");

	//casos EBS PAGO QR BAZ
			By MXOXXOGLXXXXXSUPERUSUARIO = By.xpath("//div[normalize-space()='MXOXXO-GLXXXXX-Super Usuario']");	
			//By MXOXXOGLXXXXXSUPERUSUARIO = By.xpath("//a[normalize-space()='MXOXXO-GLXXXXX-Super Usuario']");
			By INFORMES = By.xpath("//div[normalize-space()='Informes']");
			By SOLICITAR = By.xpath("//div[normalize-space()='Solicitar']");
			By STANDARD = By.xpath("//div[normalize-space()='Standard']");
		


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

	By carpetaOtrosMX = By
			.xpath("//a[normalize-space()='MXOXXO-APXXXXX-Super Usuario']/following-sibling::ul/li[15]/a");
	@FindBy(xpath = "//a[normalize-space()='MXOXXO-APXXXXX-Super Usuario']/following-sibling::ul/li[15]/a")
	public WebElement carpetaOtrosMXElement;

	By carpetaCargosCentrales = By.xpath(
			"//a[normalize-space()='MXOXXO-CC17FAA-Usuario_ACT_FIJO_OS']/following-sibling::ul//div[normalize-space()='Cargos Centrales']");
	@FindBy(xpath = "//a[normalize-space()='MXOXXO-CC17FAA-Usuario_ACT_FIJO_OS']/following-sibling::ul//div[normalize-space()='Cargos Centrales']")
	public WebElement carpetaCargosCentralesElement;

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

	By NuevoSistema = By.xpath("//div[normalize-space()='Nuevo sistema ICM']");//// div[normalize-space()='Nuevo sistema
																				//// ICM']
	@FindBy(xpath = "//div[normalize-space()='Nuevo sistema ICM']") //// *[contains(text(),'Nuevo sistema ICM')]
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

	By carpetaPagosCO10CHIUsuario = By.xpath("//div[normalize-space()='Pagos']");//// li[13]/ul/li[4]/a/div[2][normalize-space()='Pagos']
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

	By carpetaRequest10PCK = By.xpath("//div[contains(text(),'Request')]");
	@FindBy(xpath = "//div[contains(text(),'Request')]")
	public WebElement carpetacarpetaRequest10PCKElement;

	By carpetaReportesContable = By.xpath("//div[contains(text(),'Genera Reportes para consulta Contable')]");
	@FindBy(xpath = "//div[contains(text(),'Genera Reportes para consulta Contable')]")
	public WebElement carpetaReportesContableElement;

	By carpetaPedidosDeVenta = By
			.xpath("//a[@href='#dummyAnchor']/child::div[2] [normalize-space()='Pedidos de Venta']");
	@FindBy(xpath = "//a[@href='#dummyAnchor']/child::div[2] [normalize-space()='Pedidos de Venta']")
	public WebElement carpetaPedidosDeVentaElement;

	By carpetaWorkOrders = By.xpath(
			"//div[normalize-space()='MXOXXO-EAMXXXXX-Super Usuario']/parent::a/following-sibling::ul//div[contains(text(),'Work Orders')]");
	@FindBy(xpath = "//div[normalize-space()='MXOXXO-EAMXXXXX-Super Usuario']/parent::a/following-sibling::ul//div[contains(text(),'Work Orders')]")
	public WebElement carpetaWorkOrdersElement;

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

	By runTransaccionesDeEnvio = By.xpath("//div[normalize-space()='Transacciones de Envío']");
	@FindBy(xpath = "//div[normalize-space()='Transacciones de Envío']")
	public WebElement runTransaccionesDeEnvioElement;

	By runOrganizadorDePedido = By.xpath("//div[normalize-space()='Organizador de Pedido']");
	@FindBy(xpath = "//div[normalize-space()='Organizador de Pedido']")
	public WebElement runOrganizadorDePedidoElement;

	By runDespacharPedidosDeVentas = By.xpath("//a[normalize-space()='Despachar Pedidos de Ventas']");
	@FindBy(xpath = "//div[normalize-space()='Despachar Pedidos de Ventas']")
	public WebElement runDespacharPedidosDeVentasElement;

	By workOrdersSub = By.xpath("//*[@id=\"LIWork_Orders\"]/a/div[2]");
	@FindBy(xpath = "//*[@id=\"LIWork_Orders\"]/a/div[2]")
	public WebElement workOrdersSubElement;

	By runConcurrentMXSuperUsuario = By.xpath("//div[normalize-space()='Concurrent']");
	@FindBy(xpath = "//div[normalize-space()='Concurrent']")
	public WebElement runConcurrentMXSuperUsuarioElement;

	By runCapturaDeClavesArt = By.xpath("//div[normalize-space()='Captura de Claves de Activo']");
	@FindBy(xpath = "//div[normalize-space()='Captura de Claves de Activo']")
	public WebElement runrunCapturaDeClavesArtElement;

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

	By ejecutarConcurrentes = By.xpath("//div[contains(text(), 'Ejecutar concurrentes')]");
	By capturaGruposConsultas = By.xpath("//div[contains(text(), 'Captura de grupos para consultas')]");

	By viewItems;
	String service;
	String newService;

	By txtBoxGrupo = By.xpath("//input[@id = 'lovSearchGrupo']");
	By btnGo = By.xpath("//button[@title = 'Go']");
	By btnCrearItem = By.xpath("//button[@id = 'btnCreateItem']");
	By txtBoxServicio = By.xpath("//input[@id = 'lovServicio']");
	By txtBoxDescripcion = By.xpath("//input[@id = 'txtDescripcionItem']");
	By txtBoxDescripcionUpdate = By.xpath("//input[@id = 'txtDescripcion']");
	By btnAplicar = By.xpath("//button[@id = 'btnApplyItem']");
	By btnAplicarUpdate = By.xpath("//button[@id = 'btnApply']");
	By txtItemCreado = By.xpath("//div[text() =  'Item creado satisfactoriamente']");
	By txtItemActualizado = By.xpath("//*[@id=\"OADialogPopupRN\"]/table/tbody/tr/td/span");
	By btnGrupos = By.xpath("//button[@id = 'btnGruposItem']");
	By btnOkActualizar = By.xpath("//button[@title = 'Ok' and text() = 'Ok']");

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

	By textoPaginaInicial = By.xpath("//h1[@type='OraHeader']");
	@FindBy(xpath = "//h1[@type='OraHeader']")
	public WebElement textoPaginaInicialElement;

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

	By carpetaConsultaNacionales = By.xpath("//li//div[text()='Consulta']");
	By carpetaSolicitudesNacionales = By.xpath("//li//div[text()='Solicitudes']");
	By cuentaNacionales = By.xpath("//li//div[text()='Cuenta']");

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
		case "MXOXXO-GLXXXXX-Administrador CEDIS Clúster":
			return MXOXXOCMXXXXXCedisCluster;
		case "Valores de CR CEDIS CLUSTERS":
			return valoresCRCEDISCLUSTERS;
		case "MXOXXO-GL10PCK-CONSULTA":
			return MXOXXOGL10PCKCONSULTA;
		case "MXOXXO-GL10PCK-Usuario":
			return MXOXXOGL10PCKUSUARIO;
		case "MXOXXO-GL10CAN-CONSULTA":
			return MXOXXOGL10CANCONSULTA;
		case "MXOXXO-GL10CAN-Usuario":
			return MXOXXOGL10CANUSUARIO;
		case "MXOXXO-GL10MON-Consulta":
			return MXOXXOGL10MONConsulta;
		case "MXOXXO-GL-BackOffice Conciliaciones SF y SNF":
			return MXOXXOGLBackOfficeConsiliaciones;
		case "MXOXXO-APXXXXX-Super_Usuario":
			return MXOXXOCMXXXXXSuperUsuario;
		case "MXOXXOCMXXXXXSuperUsuarioInvoices":
			return MXOXXOCMXXXXXSuperUsuarioInvoices;
		case "MXOXXOCMXXXXXSuperUsuarioInvoicesEntry":
			return MXOXXOCMXXXXXSuperUsuarioInvoicesEntry;
		case "MXOXXOCMXXXXXSuperUsuarioInvoicesEntryInvoices":
			return MXOXXOCMXXXXXSuperUsuarioInvoicesEntryInvoices;
		case "MXOXXOCC17FAAUsuarioActFijoOSCargosCentrales":
			return MXOXXOCC17FAAUsuarioActFijoOSCargosCentrales;
		case "MXOXXOCC17FAAUsuarioActFijoOSCargosCentralesCapturaCargosCentrales":
			return MXOXXOCC17FAAUsuarioActFijoOSCargosCentralesCapturaCargosCentrales;
		case "MXOXXOAP17ADMUsuarioOSADTIVO_OS":
			return MXOXXOAP17ADMUsuarioOSADTIVO_OS;
		case "MXOXXOAP17ADMUsuarioOSADTIVO_OS_ProrrateoCentral":
			return MXOXXOAP17ADMUsuarioOSADTIVO_OS_Prorrateo_Central;
		case "MXOXXOAP17ADMUsuarioOSADTIVO_OS_ProrrateoCentral_Prorrateo_Central":
			return MXOXXOAP17ADMUsuarioOSADTIVO_OS_Prorrateo_Central_Prorrateo_Central;
		case "MXOXXO-GLXXXXX-SuperUsuario":
			return MXOXXO_GLXXXXX_SuperUsuario;
		case "MXOXXO-GLXXXXX-SuperUsuarioJournals":
			return MXOXXO_GLXXXXX_SuperUsuarioJournals;
		case "MXOXXO-GLXXXXX-SuperUsuarioJournalsEnter":
			return MXOXXO_GLXXXXX_SuperUsuarioJournalsEnter;
		case "MXOXXXO_APXXXXX_CONSULTAS_FISCAL_OS":
			return MXOXXXO_APXXXXX_CONSULTAS_FISCAL_OS;
		case "MXOXXXO_APXXXXX_CONSULTAS_FISCAL_OS_Otros":
			return MXOXXXO_APXXXXX_CONSULTAS_FISCAL_OS_Otros;
		case "MXOXXXO_APXXXXX_CONSULTAS_FISCAL_OS_Otros_EjecutarSolicitud":
			return MXOXXXO_APXXXXX_CONSULTAS_FISCAL_OS_Otros_EjecutarSolicitud;
		case "MXOXXOAP17ADMUsuarioOSADTIVO_OS_AP_Usuario":
			return MXOXXOAP17ADMUsuarioOSADTIVO_OS_AP_Usuario;
		case "MXOXXOAP17ADMUsuarioOSADTIVO_OS_AP_Usuario_Otro":
			return MXOXXOAP17ADMUsuarioOSADTIVO_OS_AP_Usuario_Otro;
		case "MXOXXOAP17ADMUsuarioOSADTIVO_OS_AP_Usuario_Otro_EjecutarSolicitud":
			return MXOXXOAP17ADMUsuarioOSADTIVO_OS_AP_Usuario_Otro_EjecutarSolicitud;
		case "MXOXXO-GLXXXXX-SuperUsuarioFacturas":
			return MXOXXO_GLXXXXX_SuperUsuarioFacturas;
		case "MXOXXO-GLXXXXX-SuperUsuarioFacturasEntrar":
			return MXOXXO_GLXXXXX_SuperUsuarioFacturasEntrar;
		case "MXOXXO-GLXXXXX-SuperUsuarioFacturasEntrarFacturas":
			return MXOXXO_GLXXXXX_SuperUsuarioFacturasEntrarFacturas;
		case "MXOXXO-OM17FAA-MESA CONTROL FRM":
			return MXOXXOOM17FAAMESACONTROLFRM;
		case "MXOXXO-POXXXXX-Super_Usuario":
			return MXOXXO_POXXXXX_Super_Usuario;
		case "MXOXXO-POXXXXX-Super_Usuario-OrdenesDeCompra":
			return MXOXXO_POXXXXX_Super_Usuario_OrdenesDeCompra;
		case "MXOXXO-POXXXXX-Super_Usuario-OrdenesDeCompra-PurchaseOrderSummary":
			return MXOXXO_POXXXXX_Super_Usuario_PurchaseOrderSummary;
		case "MXOXXO_IN17FAA_Admon_ACT_FIJO_OS":
			return MXOXXO_IN17FAA_Admon_ACT_FIJO_OS;
		case "MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_Transacciones":
			return MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_Transacciones;
		case "MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_Recepcion":
			return MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_Recepcion;
		case "MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_Receipts":
			return MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_Receipts;
		case "MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_ViewReceivingTransactions":
			return MXOXXO_IN17FAA_Admon_ACT_FIJO_OS_ViewReceivingTransactions;
		case "logoutEBS":
			return logoutEBS;
		}
		return null;
	}

	/**
	 * Realiza un clic en la responsabilidad especificada
	 *
	 * @param responsabilidad Nombre de la responsabilidad a probar
	 * @author JesusH@hexaware.com
	 */
	public Boolean clickOnResponsabilidad(String responsabilidad) {
		// u.waitForLoadPage();

		try {

			By obj = responsabilidad(responsabilidad);
			u.waitWebElement(obj, 50, 1);
			waitElementToBeClickable(obj, 50);
			u.highLight(obj);
			u.click(obj);
			u.hardWait(2);
			return true;

		} catch (Exception e) {
			logger.error("Error Responsabilidad no encontrada " + e);
			System.out.println("Error Responsabilidad no encontrada " + e);
			return false;
		}

	}

	public void waitElementToBeClickable(By elemento, int seconds) {

		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.elementToBeClickable(elemento));
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
	 * Realiza un clic en la responsabilidad especificada
	 *
	 * @param responsabilidad Nombre de la responsabilidad a probar
	 * @author JesusH@hexaware.com
	 */
	public void clickOnResponsabilidad2(String responsabilidad) {
		// u.waitForLoadPage();
		try {
			By obj = responsabilidad(responsabilidad);
			u.highLight(obj);
			waitElementToBeClickable(obj, 50);
			u.click(obj);
			u.hardWait(2);
		} catch (Exception e) {
			logger.error("Error Responsabilidad no encontrada " + e);
			System.out.println("Error Responsabilidad no encontrada " + e);
		}

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
	 * Hace un remarcado y clic en la opcion Ejecutar Concurrentes
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @date 26/Noviembre/2024
	 */
	public void clickEjecutarConcurrentes() {
		u.waitWebElement(ejecutarConcurrentes, 20, 1);
		u.highLight(ejecutarConcurrentes);
		screenshot();
		u.click(ejecutarConcurrentes);
		u.waitForLoadPage();
	}

	/**
	 * Hace un remarcado y clic en la opcion Captura de grupos para consultas
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @return regresa un true o false en funcion de muestre el grupo buscado
	 * @date 26/Noviembre/2024
	 */
	public boolean clickCapturaGruposConsultas() {
		try {
			u.waitWebElement(capturaGruposConsultas, 20, 1);
			u.highLight(capturaGruposConsultas);
			screenshot();
			u.click(capturaGruposConsultas);
			u.waitForLoadPage();
			u.waitWebElement(txtBoxGrupo, 20, 1);

			return true;
		} catch (Exception e) {
			logger.error("No se ha encontrado la opcion buscada " + e);
			System.out.println("No se ha encontrado la opcion buscada" + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado e ingresa el nombre del grupo en el campo group
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @param group toma el valor del grupo a buscar
	 * @return Regresa un true o un False en funcion de si encuentra el grupo
	 *         buscado
	 * @date 26/Noviembre/2024
	 */
	public boolean searchGroup(String group) {
		try {
			u.waitWebElement(txtBoxGrupo, 20, 1);
			u.highLight(txtBoxGrupo);
			u.click(txtBoxGrupo);
			u.sendKeys(txtBoxGrupo, group);

			u.sendKeys(txtBoxGrupo, Key.TAB);
			// clicCloseWindow();

			closeWindowsExtra();

			u.waitWebElement(btnGo, 20, 1);
			u.highLight(btnGo);
			screenshot();
			try {
				u.click(btnGo);
			} catch (Exception ex) {
				sk.screen.type(Key.TAB + Key.TAB);
				u.hardWait(1);
				u.click(btnGo);
			}

			return true;
		} catch (Exception e) {
			logger.error("Fallo al ingresar el nombre del grupo" + e);
			System.out.println("Fallo al ingresar el nombre del grupo" + e);
			return false;
		}
	}

	/**
	 * Cierra la pantalla extra que se abre cuando se escribe en el campo Grupo
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @return Regresa un true o un False en funcion de que cierre la pantalla
	 *         emergente
	 * @date 26/Noviembre/2024
	 */
	public boolean closeWindowsExtra() {
		try {
			// Esperar hasta que aparezca una nueva ventana
			Duration duration = Duration.ofSeconds(60);

			WebDriverWait wait = new WebDriverWait(u.getDriver(), duration);
			wait.until(ExpectedConditions.numberOfWindowsToBe(2));

			String mainWindowHandle = u.getDriver().getWindowHandle();

			// Obtener todos los handles de las ventanas abiertas
			Set<String> allWindowHandles = u.getDriver().getWindowHandles();

			// Cambiar el foco a la nueva ventana
			for (String windowHandle : allWindowHandles) {
				if (!windowHandle.equals(mainWindowHandle)) {
					u.getDriver().switchTo().window(windowHandle);
					break;
				}
			}

			// Cerrar la nueva ventana y volver a la ventana principal
			u.getDriver().close();
			u.getDriver().switchTo().window(mainWindowHandle);

			return true;
		} catch (Exception e) {
			System.out.println("Fallo al cerrar la ventana emergente del campo nombre del grupo: " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el icono de lupa para ver el listado de items
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @param group toma el nombre del grupo del cual se veran los items
	 * @return Regresa un true o un False en funcion de que se muestre la lista de
	 *         items del grupo indicado
	 * @date 26/Noviembre/2024
	 */
	public boolean clickViewItems(String group) {
		viewItems = By.xpath("(//a[contains(@onclick, '" + group + "')])[2]");
		try {
			u.waitWebElement(viewItems, 15, 1);
			u.highLight(viewItems);
			screenshot();
			u.click(viewItems);

			By labelGroup = By.xpath("(//span[contains(text(), '" + group + "')])");
			u.waitWebElement(labelGroup, 20, 1);

			return true;
		} catch (Exception e) {
			viewItems = By.xpath("//a[contains(@onclick, '" + group + "')]");
			try {
				u.waitWebElement(viewItems, 40, 1);
				u.highLight(viewItems);
				screenshot();
				u.click(viewItems);

				By labelGroup = By.xpath("(//span[contains(text(), '" + group + "')])[2]");
				u.waitWebElement(labelGroup, 20, 1);

				return true;
			} catch (Exception i) {

				logger.error("No se muestra la lista de items del grupo " + e);
				System.out.println("No se muestra la lista de items del grupo " + e);
				return false;
			}
		}
	}

	/**
	 * Hace un remarcado y da clic en el icono de lupa para ver el listado de items
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @return Regresa un true o un False en funcion de que se abra la pantalla para
	 *         crear un servicio
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnCrearItem() {
		try {
			u.waitWebElement(btnCrearItem, 20, 1);
			u.highLight(btnCrearItem);
			screenshot();
			u.click(btnCrearItem);

			u.waitWebElement(txtBoxServicio, 20, 1);

			return true;
		} catch (Exception e) {
			logger.error("No se muestra la pantalla para creacion de items " + e);
			System.out.println("No se muestra la pantalla para creacion de items " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y llena los campos de Servicio y Descripcion para crear
	 * items
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @param servicio toma el numero de servicio con el que se creara el item.
	 *                 descripcion toma el valor de la descripcion que tendra dicho
	 *                 item creado
	 * @return Regresa un true o un False en funcion que se llene correctamente los
	 *         campos para crear un nuevo item
	 * @date 26/Noviembre/2024
	 */
	public boolean fillCreateItem(String servicio, String descripcion) {
		service = servicio;
		try {
			u.waitWebElement(txtBoxServicio, 20, 1);
			u.highLight(txtBoxServicio);
			u.sendKeys(txtBoxServicio, service);

			u.waitWebElement(txtBoxDescripcion, 20, 1);
			u.highLight(txtBoxDescripcion);
			u.sendKeys(txtBoxDescripcion, descripcion);
			screenshot();

			return true;
		} catch (Exception e) {
			logger.error("No se ingresan los datos exitosamente" + e);
			System.out.println("No se ingresan los datos exitosamente " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y edita los campos de Servicio y Descripcion para
	 * actualizar items
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @param servicio toma el numero de servicio con el que se actualizara el item.
	 *                 descripcion toma el valor de la descripcion que tendra dicho
	 *                 item actualizado
	 * @return Regresa un true o un False en funcion de que se llenen correctamente
	 *         los campos para actualizar un item
	 * @date 26/Noviembre/2024
	 */
	public boolean fillUpdateItem(String servicio, String descripcion) {
		newService = servicio;
		try {
			u.waitWebElement(txtBoxServicio, 20, 1);
			u.highLight(txtBoxServicio);

			u.waitWebElement(txtBoxDescripcionUpdate, 20, 1);
			u.highLight(txtBoxDescripcionUpdate);

			screenshot();

			u.clear(txtBoxServicio);
			u.clear(txtBoxDescripcionUpdate);
			u.hardWait(1);

			u.sendKeys(txtBoxServicio, newService);
			u.sendKeys(txtBoxDescripcionUpdate, descripcion);
			screenshot();

			return true;
		} catch (Exception e) {
			logger.error("No se actualizaron los datos exitosamente" + e);
			System.out.println("No se actualizaron los datos exitosamente " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el boton Aplicar para completar el registro
	 * del item
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @return Regresa un true o un False en funcion de que se muestre el mensaje
	 *         confirmando la creacion del item
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnAplicar() {
		try {
			u.waitWebElement(btnAplicar, 20, 1);
			u.highLight(btnAplicar);
			screenshot();
			u.click(btnAplicar);

			u.waitWebElement(txtItemCreado, 20, 1);
			u.highLight(txtItemCreado);
			screenshot();

			return true;
		} catch (Exception e) {
			logger.error("No se muestra el mensaje  de confirmacion " + e);
			System.out.println("No se muestra el mensaje  de confirmacion " + e);
			return false;
		}
	}

	/*
	 * Obtiene la fecha actual en un formato ddmmyyyy en caso de que la variable sea
	 * null
	 * 
	 * @author 1000068915 - Jorge Hernande
	 * 
	 * @param service toma el valor del servicio que sera evaluado para saber si es
	 * null
	 * 
	 * @return Regresa la fecha actual en un formato ddMMyyyy
	 * 
	 * @date 26/Noviembre/2024
	 */
	public String getService(String service) {

		if (service.equals("")) {
			LocalDate currentDate = LocalDate.now();

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

			String date = currentDate.format(formatter);
			System.out.println(date);

			String newService = date;

			return newService;
		}

		return service;
	}

	/*
	 * Obtiene la fecha actual mas 1 dia en un formato ddmmyyyy en caso de que la
	 * variable sea null
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * 
	 * @param service toma el valor del servicio que sera evaluado para saber si es
	 * null
	 * 
	 * @return Regresa la fecha actual en un formato ddMMyyyy
	 * 
	 * @date 26/Noviembre/2024
	 */
	public String getService2(String service) {

		if (service.equals("")) {
			LocalDate currentDate = LocalDate.now();

			currentDate = currentDate.plusDays(1);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

			String date = currentDate.format(formatter);
			System.out.println(date);

			String newService = date;

			return newService;
		}

		return service;
	}

	/**
	 * Hace un remarcado y da clic en el boton Aplicar para completar el proceso de
	 * actualizacion de un Item
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @return Regresa un true o un False en funcion de que se muestre el mensaje
	 *         confirmando la actualizacion del item
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnAplicarUpdate() {
		try {
			u.waitWebElement(btnAplicarUpdate, 20, 1);
			u.highLight(btnAplicarUpdate);
			screenshot();
			u.click(btnAplicarUpdate);

			By frame = By.xpath("//iframe[@id = 'iframedefaultDialogPopup']");
			u.waitWebElement(frame, 15, 2);
			WebElement iframe = u.getDriver().findElement(frame);
			u.getDriver().switchTo().frame(iframe.getAttribute("id"));

			u.waitWebElement(txtItemActualizado, 10, 1);
			u.highLight(txtItemActualizado);
			screenshot();

			return true;
		} catch (Exception e) {
			logger.error("No se muestra el mensaje  de confirmacion " + e);
			System.out.println("No se muestra el mensaje  de confirmacion " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el boton OK de la pantalla de confirmacion de
	 * Item Actualizados
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @return Regresa un true o un False en funcion de que se deje de mostrar el
	 *         mensaje confirmando la actualizacion del itemdel item
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnOk() {
		try {
			u.waitWebElement(btnOkActualizar, 20, 1);
			u.highLight(btnOkActualizar);
			screenshot();
			u.click(btnOkActualizar);

			u.waitWebElement(viewItems, 20, 1);

			return true;
		} catch (Exception e) {
			logger.error("No se muestra la lista de items del grupo " + e);
			System.out.println("No se muestra la lista de items del grupo " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el icono de basura para eliminar items En la
	 * pantalla de items de grupos
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @param service toma el valor del servicio que se buscara para eliminarlo
	 * @return Regresa un true o un False en funcion de que se muestre el mensaje de
	 *         eliminacion del item
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnDelete(String service) {
		By btnDeleteItem = By.xpath("//span[@title = 'Servicio' and text() = '" + service
				+ "']/ancestor::tr[1]//a[contains(@id, 'DeleteItem')]");

		try {
			u.waitWebElement(btnDeleteItem, 10, 1);
			u.highLight(btnDeleteItem);
			screenshot();
			u.click(btnDeleteItem);
			u.waitWebElement(txtBoxServicio, 20, 1);

			return true;
		} catch (Exception e) {
			logger.error("No se muestra el mensaje preguntando si queremos eliminar el item " + e);
			System.out.println("No se muestra el mensaje preguntando si queremos eliminar el item " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el icono de Lapiz para actualizar items En la
	 * pantalla de items de grupos
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @param service toma el valor del servicio que se buscara para actualizarlo
	 * @return Regresa un true o un False en funcion de que se muestre la pantalla
	 *         de actualizaciond e items
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnUpdate(String service) {
		By btnUpdateItem = By.xpath("//span[@title = 'Servicio' and text() = '" + service
				+ "']/ancestor::tr[1]//a[contains(@id, 'UpdateItem')]");

		try {
			u.waitWebElement(btnUpdateItem, 10, 1);
			u.highLight(btnUpdateItem);
			screenshot();
			u.click(btnUpdateItem);
			u.waitWebElement(txtBoxServicio, 20, 1);

			return true;
		} catch (Exception e) {
			logger.error("No se muestra la pantalla para actualizar item  " + e);
			System.out.println("No se muestra la pantalla para actualizar item  " + e);
			return false;
		}
	}

	/**
	 * Hace un remarcado y da clic en el boton grupos para volver a la pantalla de
	 * Grupos
	 *
	 * @author 1000068915 - Jorge Hernandez
	 * @return Regresa un true o un False en funcion de que se muestre la pantalla
	 *         de grupos
	 * @date 26/Noviembre/2024
	 */
	public boolean clicBtnGrupos() {
		try {
			u.waitWebElement(btnGrupos, 20, 1);
			u.highLight(btnGrupos);
			screenshot();
			u.click(btnGrupos);

			return true;
		} catch (Exception e) {
			logger.error("No se muestra la lista de items del grupo " + e);
			System.out.println("No se muestra la lista de items del grupo " + e);
			return false;
		}
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
		case "Valores de CR CEDIS CLUSTERS":
			return valoresCRCEDISCLUSTERS;
		case "carpeta request 10pck-consulta":
			return carpetaRequest10PCK;
		case "genera reportes para consulta contable":
			return carpetaReportesContable;
		case "Ordenes de Compra":
			return OrdenesDecompra;
		case "Pagos":
			return MXOXXO_APXXXXX_Pagos;
		case "pedidos de venta":
			return carpetaPedidosDeVenta;
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
		// u.highLight(loc);
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
		testCase.addScreenShotCurrentStep(u, "clickRunReportesCO10CHIUsuario");
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

	/**
	 * Método para Ejecutar Otros
	 *
	 * @author yairl@hexaware.com
	 */
	public void EjecutarOtros() {
		u.waitForLoadPage();
		try {
			u.highLight(carpetaOtrosMX);
			u.click(carpetaOtrosMX);
			u.hardWait(10);
			System.out.println("Ingresar a la siguiente ruta: Ver solicitudes Ejecutar Solicitud");
		} catch (Exception e) {
			logger.error("Error ruta no encontrada " + e);
			System.out.println("Error ruta no encontrada " + e);
		}
	}

	/**
	 * Hace un remarcado y click en MXOXXO-MXXXXXX-Super Usuario / Other /
	 * Concurrent
	 * 
	 * @author yairl@hexaware.com
	 */
	public void clickRunTransaccionesMxSuperUsuario() {
		u.highLight(runConcurrentMXSuperUsuario);
		testCase.addScreenShotCurrentStep(u, "transacciones");
		u.hardWait(15);
		u.click(runConcurrentMXSuperUsuario);
		u.unhighLight(runConcurrentMXSuperUsuario);
	}

	/**
	 * Ingresa a la ruta de cargos centrales
	 *
	 * @param image
	 * @param number
	 * @return
	 * @author Yair Lizarraga
	 */
	public void EjecutarCargosCentrales() {
		u.waitForLoadPage();
		try {
			u.highLight(carpetaCargosCentrales);
			u.click(carpetaCargosCentrales);
			u.hardWait(10);
			System.out.println("Ingresar a la siguiente ruta: Ver solicitudes Ejecutar Solicitud");
		} catch (Exception e) {
			logger.error("Error ruta no encontrada " + e);
			System.out.println("Error ruta no encontrada " + e);
		}

		/**
		 * Da clic en la opcion Captura clave de articulo
		 *
		 * @author Yair Lizarraga
		 */
	}

	public void clickRunCapturaClaveArticulo() {
		u.highLight(runCapturaDeClavesArt);
		testCase.addScreenShotCurrentStep(u, "transacciones");
		u.hardWait(15);
		u.click(runCapturaDeClavesArt);
		u.unhighLight(runCapturaDeClavesArt);
	}

	/**
	 * Realiza un clic en la responsabilidad especificada
	 *
	 * @param responsabilidad Nombre de la responsabilidad a probar
	 * @author JesusH@hexaware.com
	 */
	public String clickOnResponsabilidad3(String responsabilidad) {
		u.waitForLoadPage();
		String seleccion = null;
		try {
			By obj = responsabilidad(responsabilidad);
			u.highLight(obj);
			seleccion = obj.toString();
			u.click(obj);
		} catch (Exception e) {
			logger.error("Error Responsabilidad no encontrada " + e);
			System.out.println("Error Responsabilidad no encontrada " + e);
		}
		return seleccion;

	}

	/**
	 * Hace un remarcado y clic en la carpeta indicada
	 *
	 * @param carpeta Carpeta a la que se desea ingresar dentro de la
	 *                responsabilidad seleccionada
	 * @return result variable para validar con assertTrue que el elemento se
	 *         encontró
	 * @author JesusH@hexaware.com
	 */
	public String clickOnCarpeta2(String carpeta) {
		String result = null;
		try {
			By obj = carperta(carpeta);
			u.scroll();
			Actions action = new Actions(u.getDriver());
			action.moveToElement(u.getDriver().findElement(obj));
			action.perform();
			u.hardWait(1);
			result = obj.toString();
			u.highLight(obj);
			u.click(obj);
		} catch (Exception e) {
			logger.error("Error Carpeta no encontrada " + e);
			System.out.println("Error Carpeta no encontrada " + e);
		}
		return result;
	}

	/**
	 * Hace un remarcado y click en MXOXXO-OM17FAA-MESA CONTROL FRM / Pedidos de
	 * Venta/ Transacciones de Envío
	 * 
	 * @author NellyOliviaB@hexaware.com
	 */
	public String clickRunTransaccionesDeEnvio() {
		String result = "";
		// u.click(runTransaccionesDeEnvio);
		result = runTransaccionesDeEnvio.toString();
		return result;
	}

	/**
	 * Hace un remarcado y click en MXOXXO-OM17FAA-MESA CONTROL FRM / Pedidos de
	 * Venta/ Despachar Pedidos de Ventas
	 * 
	 * @author NellyOliviaB@hexaware.com
	 */
	public String clickRunDespacharPedidosDeVentas() {
		String result = null;
		result = runDespacharPedidosDeVentas.toString();
		u.click(runDespacharPedidosDeVentas);
		return result;
	}

	/**
	 * Hace un remarcado y click en MXOXXO-OM17FAA-MESA CONTROL FRM / Pedidos de
	 * Venta/ Organizador de pedido
	 * 
	 * @author NellyOliviaB@hexaware.com
	 */
	public String clickRunOrganizadorDePedido() {
		String result = null;
		result = runOrganizadorDePedido.toString();
		// u.highLight(runOrganizadorDePedido);
		u.click(runOrganizadorDePedido);
		return result;
	}

	/**
	 * Encuentra el mensaje de: Página de inicio
	 *
	 * @author NellyOliviaB@hexaware.com
	 */
	public String encontrarTextoPaginaInicio() {
		u.highLight(textoPaginaInicial);
		String texto = textoPaginaInicialElement.getText();
		return texto;
	}

	int numeroEvidencia = 0;

	/*
	 * Realiza una captura de pantalla del paso actual
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 * 
	 * @date 26/Noviembre/2024
	 * 
	 */
	public void screenshot() {
		u.hardWait(1);
		String filename = "evidence" + numeroEvidencia;
		testCase.addScreenShotCurrentStep(filename);
		numeroEvidencia++;
	}

	/**
	 * Valida en el archivo Relacional que este contenga la validacion comercial
	 * exitosa
	 *
	 * 
	 * @author ricardor4@hexaware.com
	 * @return
	 */
	public boolean ValidacionComercialFolio(String Folio, String palabravalidacion) throws IOException {

		String validacion = "";
		// Ruta del archivo

		// Obtiene la ruta del directorio de descargas predeterminado del usuario
		String rutaDescargas = System.getProperty("user.home") + "/Downloads/";

		// Obtiene la lista de archivos en la carpeta de descargas
		File carpetaDescargas = new File(rutaDescargas);
		File[] archivos = carpetaDescargas.listFiles();

		// Ordena los archivos por fecha de modificaciÃ³n en orden descendente
		Arrays.sort(archivos, Comparator.comparingLong(File::lastModified).reversed());

		// Obtiene el primer archivo de la lista ordenada (el mÃ¡s reciente)
		File file = archivos[0];
		// File file = new File(System.getProperty("user.home") +
		// "/Downloads/XXFC_Informe_de_Requerimientos_130325.xls");

		// Contar las lÃ­neas del archivo para inicializar el arreglo
		int totalLineas = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while (br.readLine() != null) {
				totalLineas++;
			}
		} catch (IOException e) {
			e.printStackTrace();

		}

		// Inicializar el arreglo con el nÃºmero de lÃ­neas
		String[] renglones = new String[totalLineas];

		// Leer el archivo y llenar el arreglo
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			int contador = 0;
			while ((line = br.readLine()) != null) {
				renglones[contador] = line;
				contador++;
			}

			// Buscar la lÃ­nea que contiene "FP_10MON_65555" y mostrar la lÃ­nea anterior
			for (int i = 0; i < renglones.length; i++) {
				if (renglones[i].contains(Folio)) {
					if (i >= 2) { // Verifica que exista una lÃ­nea anterior
						System.out.println("COLUMNA DE VALIDACION---- : " + renglones[i - 4]);
						validacion = renglones[i - 4];

					} else {
						System.out.println("No hay suficientes lÃ­neas anteriores.");
					}

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (validacion.toLowerCase().contains(palabravalidacion.toLowerCase())) {

			return true;
		}

		else {
			return false;

		}
	}

	/**
	 * MÃ©todo para leer el archivo CSV mÃ¡s reciente descargado desde la carpeta de
	 * descargas, que contiene los errores con respecto a las validaciones web
	 * 
	 * @return errores -> Retorna la lista de errores obtenida en el archivo
	 * @author Daniel Martinez Garcia
	 */
	public void AbrirUltimoArchivoDescargadoExcel() {

		// Obtiene la ruta del directorio de descargas predeterminado del usuario
		String rutaDescargas = System.getProperty("user.home") + "/Downloads/";

		// Obtiene la lista de archivos en la carpeta de descargas
		File carpetaDescargas = new File(rutaDescargas);
		File[] archivos = carpetaDescargas.listFiles();

		// Ordena los archivos por fecha de modificaciÃ³n en orden descendente
		Arrays.sort(archivos, Comparator.comparingLong(File::lastModified).reversed());

		// Verifica si hay archivos en la carpeta de descargas
		if (archivos.length > 0) {
			// Obtiene el primer archivo de la lista ordenada (el mÃ¡s reciente)
			File archivo = archivos[0];

			try {
				Desktop.getDesktop().open(archivo);
				System.out.println("Archivo Excel ejecutado correctamente.");
				u.hardWait(10);
				System.out.println(archivo.getName());

			} catch (IOException e) {
				System.out.println("El archivo no existe en la ruta especificada.");
			}
		}

	}

	/**
	 * Hace un remarcado y clic en la carpeta Standard de la carpeta
	 * 17ADM-SSERVICIOS_ADTIVO_OS
	 *
	 * @author Mario.garza@grupotimexico.com
	 */
	public void clickStandard17admServicios() {
		u.highLight(standard17admServicios);
		u.click(standard17admServicios);
	}

	/**
	 * este metodo te dirige a la pantalla de configuracion de remesas
	 *
	 * @author Ricardor4@hexaware.com
	 */
	public boolean configuracionRemesas() {
		u.highLight(carpetaConsultaNacionales);
		u.click(carpetaConsultaNacionales);
		u.hardWait(3);
		u.highLight(cuentaNacionales);
		u.click(cuentaNacionales);
		return true;
	}
	

	/**
	 * Se hace el flujo para ingresar a la carpeta Consulta de MXOXXO-GL17ADM-CARTAS-FEMSA
	 *
	 * @param 
	 * @author Mónica Soto
	 */
	public void carpetaMXOXXOGLXXXXXSUPERUSUARIO () {
		u.waitForLoadPage();
		u.highLight(MXOXXOGLXXXXXSUPERUSUARIO);
		u.click(MXOXXOGLXXXXXSUPERUSUARIO);
		
		u.highLight(INFORMES);
		u.click(INFORMES);
		
		u.highLight(SOLICITAR);
		u.click(SOLICITAR);
		
		u.highLight(STANDARD);
		u.click(STANDARD);
		testCase.addScreenShotCurrentStep(u, "MXOXXO-GLXXXXX-Super Usuario");
	}
	

}
