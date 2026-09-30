package EBS.util.pageObjectModel.ebsInternational;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Clase con los locators y acciones de la pagina de "EBS inicio"
 *
 * @author JesusH@hexaware.com
 * @version 1.8
 * @since 5/23/2022
 */
public class EBSPaginaInicialInternacional {

	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	// Generacion del constructor

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author JesusH@hexaware.com
	 */
	public EBSPaginaInicialInternacional(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);

	}

	/*
	 * Responsabilidades
	 */
	By PEOXXOGLXXXXXCONSULTA = By.xpath("//a[normalize-space()='PEOXXO-GLXXXXX-CONSULTA']");
	By PEOXXOGLXXXXXSUPERUSUARIO = By.xpath("//a[normalize-space()='PEOXXO-GLXXXXX-SUPER USUARIO']");
	By COOXXOGLXXXXXSuperUsuario = By.xpath("//*[@id='AppsNavLink']");
	By PEOXXOAR10LIMTRANSACCIONES = By.xpath("//a[normalize-space()='PEOXXO-AR10LIM-TRANSACCIONES']");

	By PEOXXOCMXXXXXSUPERUSUARIO = By.xpath("//a[normalize-space()='PEOXXO-CMXXXXX-SUPER USUARIO']");
	@FindBy(xpath = "//a[normalize-space()='PEOXXO-CMXXXXX-SUPER USUARIO']")
	public WebElement peOxxoCmXXXXXSuperUsuario;
	

	By CLOKMCCMXXXXXSUPERUSUARIO = By.xpath("//a[normalize-space()='CLOKMC-CMXXXXX-SUPER USUARIO']");
	@FindBy(xpath = "//a[normalize-space()='CLOKMC-CMXXXXX-SUPER USUARIO']")
	public WebElement clOkmcCmXXXXXSuperUsuario;

	By CLOKMCCMXXXXXSUPERUSUARIO2 = By.xpath("//a[normalize-space()='CLOKMC-CMXXXXX-SUPER USUARIO']");
	@FindBy(xpath = "//a[normalize-space()='CLOKMC-CMXXXXX-SUPER USUARIO']")
	public WebElement ClokmcCmXXXXXSuperUsuario;


	/*
	 * Carpetas
	 */
	By carpetaLadAddOnPeruPeGlSU = By.xpath("//ul[@rel='open']//div[contains(text(),'LAD ADD-On Peru')]");
	@FindBy(xpath = "//ul[@rel='open']//div[contains(text(),'LAD ADD-On Peru')]")
	public WebElement carpetaLadAddOnPeruPeGlSUElement;

	By carpetaContabilidadGeneralPeGlSU = By
			.xpath("//tr/td/ul/li[2]/ul/li[1]/a/div[2][normalize-space()='Contabilidad General']");
	@FindBy(xpath = "//tr/td/ul/li[2]/ul/li[1]/a/div[2][normalize-space()='Contabilidad General']")
	public WebElement carpetaContabilidadGeneralPeGlSUElement;

	By carpetaCRsPeGlSU = By.xpath("//tbody/tr/td/ul/li[2]/ul/li[3]/a/div[2][normalize-space()='CRs']");
	@FindBy(xpath = "//tbody/tr/td/ul/li[2]/ul/li[3]/a/div[2][normalize-space()='CRs']")
	public WebElement carpetaCRsPeGlSUElement;

	By carpetaLadAddOnPeruPeGlCo = By.xpath("//ul[@rel='open']//div[contains(text(),'LAD ADD-On Peru')]");
	@FindBy(xpath = "//ul[@rel='open']//div[contains(text(),'LAD ADD-On Peru')]")
	public WebElement carpetaLadAddOnPeruPeGlCoElement;

	By carpetaReportesPeGlCo = By.xpath("//tr/td/ul/li[1]/ul/li[4]/a/div[2][contains(text(),'Reportes')]");
	@FindBy(xpath = "//tr/td/ul/li[1]/ul/li[4]/a/div[2][contains(text(),'Reportes')]")
	public WebElement carpetaReportesPeGlCoElement;

	By carpetaConsultaPeGlCo = By.xpath("//td/ul/li[1]/ul/li[3]/a/div[2][contains(text(),'Consulta')]");
	@FindBy(xpath = "//td/ul/li[1]/ul/li[3]/a/div[2][contains(text(),'Consulta')]")
	public WebElement carpetaConsultaPeGlCoElement;

	By carpetaImportarPeGlCo = By.xpath("//ul/li[1]/ul/li[2]/a/div[2][contains(text(),'Importar')]");
	@FindBy(xpath = "//ul/li[1]/ul/li[2]/a/div[2][contains(text(),'Importar')]")
	public WebElement carpetaImportarPeGlCoElement;

	By carpetaInformesPeGlCo = By.xpath("//tr/td/ul/li[1]/ul/li[1]/a/div[2][contains(text(),'Informes')]");
	@FindBy(xpath = "//tr/td/ul/li[1]/ul/li[1]/a/div[2][contains(text(),'Informes')]")
	public WebElement carpetaInformesPeGlCoElement;

	By PeOxxoCMxxxxxSuperUsuarioConfiguracion = By
			.xpath("//li[@class='submenu']/a/div[2][normalize-space()='Configuración']");
	@FindBy(xpath = "//li[@class='submenu']/a/div[2][normalize-space()='Configuración']")
	public WebElement peOxxoCMxxxxxSuperUsuarioConfiguracionElement;

	By carpetaPeCmSuUsConfigBancos = By.xpath("//li[@class='submenu']/a/div[2][normalize-space()='Bancos']");
	@FindBy(xpath = "//li[@class='submenu']/a/div[2][normalize-space()='Bancos']")
	public WebElement peCmSuUsConfigBancosElement;

	@FindBy(xpath = "//li[@id='BancosBancos']/a/div[2][normalize-space()='Cuentas Bancarias']")
	public WebElement peCmSuUsConfigCuentasBancariasElement;

	@FindBy(xpath = "//*[@class='textdivresp'][contains(text(),'Otro')]")
	public WebElement peCmSuUsOtro;

	@FindBy(xpath = "//*[@class='textdivresp'][contains(text(),'Programas')]")
	public WebElement peCmSuUsProgramas;

	public By ejecutar = By.xpath("//*[@class='textdiv'][contains(text(),'Ejecutar')]");
	@FindBy(xpath = "//*[@class='textdiv'][contains(text(),'Ejecutar')]")
	public WebElement peCmSuUsOtroProgramasEjecutar;

	@FindBy(xpath = "//*[@class='textdivresp'][contains(text(),'Otro')]")
	public WebElement peCmSuperUsuarioOtro;

	@FindBy(xpath = "//*[@class='textdiv'][contains(text(),'Concurrente')]")
	public WebElement peCmSuperUsuarioConcurrente;

	By carpetaTransaccionesAR10LIM = By.xpath("//li[@class='submenu']/a/div[2][normalize-space()='Transacciones']");
	@FindBy(xpath = "//li[@class='submenu']/a/div[2][normalize-space()='Transacciones']")
	public WebElement carpetaTransaccionesAR10LIMElement;

	/*
	 * Formularios
	 */

	By formFacturas = By.xpath("//ul/li[1]/ul/li[2]/a/div[2][normalize-space()='Facturas']");
	By plantillaDeProveedoresForm = By
			.xpath("//tbody/tr/td/ul/li/ul/li[20]/ul//div[contains(text(),'Plantilla de Proveedores')]");

	By entryForm = By.xpath("//div[2][normalize-space()='Entry']");

	/*
	 * Applets
	 */
	By informesPeGlSU = By.xpath("//tbody/tr/td/ul/li[2]/ul/li[2]/ul/li/a/div[2][normalize-space()='Informes']");
	@FindBy(xpath = "//li[@id='LAD_ADD-On_Peru'][normalize-space()='Informes']")
	public WebElement informesPeGlSUElement;

	By informesPeGlCo = By.xpath("//tr/td/ul/li[1]/ul/li[5]/ul/li/a/div[2][normalize-space()='Informes']");
	@FindBy(xpath = "//tr/td/ul/li[1]/ul/li[5]/ul/li/a/div[2][normalize-space()='Informes']")
	public WebElement informesPeGlCoElement;

	By consultasPeCMSU = By
			.xpath("//tr/td/table/tbody/tr/td/ul/li[2]/ul/li[10]/a/div[2][normalize-space()='Consultas']");
	@FindBy(xpath = "//tr/td/table/tbody/tr/td/ul/li[2]/ul/li[10]/a/div[2][normalize-space()='Consultas']")
	public WebElement consultasPeCMSUElement;

	@FindBy(xpath = "//div[@class='textdiv'][contains(text(),'Consultas')]")
	public WebElement consultasPeCmSuperUsuario;

	By transaccionesAR10LIM = By
			.xpath("//tbody/tr/td/ul/li[1]/ul/li[1]/ul/li/a/div[2][normalize-space()='Transacciones']");
	@FindBy(xpath = "//tbody/tr/td/ul/li[1]/ul/li[1]/ul/li/a/div[2][normalize-space()='Transacciones']")
	public WebElement transaccionesAR10LIMElement;

	/*
	 * Elementos generales (botones, cajas de texto, etc)
	 */
	By navegador = By.xpath("//*[@id='header']/tbody/tr[2]/td/table/tbody/tr/td/h2");
	By apply = By.id("Apply");
	By paginaInicial = By.xpath("//img[@title='PĂĄgina Inicial']");
	By vistaXML = By.xpath("/html/body/div[3]/div[1]/ul/li/a");

	/*
	 * EBS Internacional Header
	 */
	By configuracion = By.xpath("//a[@title='Settings']");
	@FindBy(xpath = "//table[@id='globalHeaderID']//a[@title='Definiciones']")
	public WebElement configuracionElement;

	By preferencias = By.xpath("//span[@class='OraMenuItemText'][normalize-space()='Preferences']");
	@FindBy(xpath = "//span[@class='OraMenuItemText'][normalize-space()='Preferencias']")
	public WebElement preferenciasElement;

	By currentLanguage = By.id("CurrentLanguage");
	@FindBy(id = "CurrentLanguage")
	public WebElement currentLanguageElement;

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
		case "PEOXXO-GLXXXXX-CONSULTA":
			return PEOXXOGLXXXXXCONSULTA;
		case "PEOXXO-GLXXXXX-SUPER USUARIO":
			return PEOXXOGLXXXXXSUPERUSUARIO;
		case "COOXXO-GLXXXXX-Super Usuario":
			return COOXXOGLXXXXXSuperUsuario;
		case "PEOXXO-CMXXXXX-SUPER USUARIO":
			return PEOXXOCMXXXXXSUPERUSUARIO;
		case "CLOKMC-CMXXXXX-SUPER USUARIO":
			return CLOKMCCMXXXXXSUPERUSUARIO;
		case "PEOXXO-AR10LIM-TRANSACCIONES":
			return PEOXXOAR10LIMTRANSACCIONES;
		}
		return null;
	}

	/**
	 * Realiza un click en la responsabilidad especificada
	 *
	 * @param responsabilidad Nombre de la responsabilidad a probar
	 * @author JesusH@hexaware.com
	 */
	public void clicEnResponsabilidad(String responsabilidad) {
		u.waitForLoadPage();
		try {
			By obj = responsabilidad(responsabilidad);
			u.highLight(obj);
			testCase.addScreenShotCurrentStep(u, "clicEnResponsabilidad");
			u.click(obj);
		} catch (Exception e) {
			System.err.println("Error Responsabilidad no encontrada " + e);
			logger.error("Error Responsabilidad no encontrada " + e);
			testCase.addScreenShotCurrentStep(u, "Error Responsabilidad no encontrada");
			testCase.addBoldTextEvidenceCurrentStep("Error Responsabilidad no encontrada");
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
		switch (Carperta.toLowerCase(Locale.ROOT)) {
		case "lad add-on peru peglsu":
			return carpetaLadAddOnPeruPeGlSU;
		case "contabilidad general peglsu":
			return carpetaContabilidadGeneralPeGlSU;
		case "crs peglsu":
			return carpetaCRsPeGlSU;
		case "lad add-on peru peglco":
			return carpetaLadAddOnPeruPeGlCo;
		case "reportes peglco":
			return carpetaReportesPeGlCo;
		case "consultas peglco":
			return carpetaConsultaPeGlCo;
		case "importar peglco":
			return carpetaImportarPeGlCo;
		case "informes peglco":
			return carpetaInformesPeGlCo;
		case "transacciones ar10lim":
			return carpetaTransaccionesAR10LIM;

		}
		return null;
	}

	/**
	 * Hace un remarcado y click en la carpeta indicada
	 *
	 * @param carpeta Carpeta a la que se desea ingresar dentro de la
	 *                responsabilidad seleccionada
	 * @author JesusH@hexaware.com
	 */
	public void clicEnCarpeta(String carpeta) {
		try {
			By obj = carperta(carpeta);
			u.scroll();
			Actions action = new Actions(u.getDriver());
			action.moveToElement(u.getDriver().findElement(obj));
			action.perform();
			u.hardWait(1);
			u.highLight(obj);
			u.click(obj);
		} catch (Exception e) {
			logger.error("Error Carpeta no encontrada " + e);
			System.out.println("Error Carpeta no encontrada " + e);
		}
	}

	public void setLanguage(String language) {
		u.click(configuracion);
		u.click(preferencias);
		currentLanguage(language);
		u.click(apply);
	}

	public void currentLanguage(String language) {
		Select cLanguage = new Select(u.getDriver().findElement(currentLanguage));
		cLanguage.selectByVisibleText(language);
	}

	public void home() {
		u.click(paginaInicial);
	}

	/**
	 * Hace un remarcado y clic en PEOXXO-GLXXXXX-SUPER USUARIO LAD ADD-On Peru /
	 * Informes
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunInformesPeGlSU() {
		u.highLight(informesPeGlSU);
		u.click(informesPeGlSU);
	}

	/**
	 * Hace un remarcado y clic en PEOXXO-GLXXXXX-CONSULTA LAD ADD-On Peru /
	 * Informes
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunInformesPeGlCo() {
		u.highLight(informesPeGlCo);
		u.click(informesPeGlCo);
	}

	public void doubleClick(WebElement element) {
		Actions actions = new Actions(u.getDriver());
		actions.doubleClick(element).perform();
	}

	/**
	 * Hace un remarcado y clic en PEOXXO-CMXXXXX-SUPER USUARIO / Consultas
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunconsultasPeCMSU() {
		u.highLight(consultasPeCMSU);
		u.click(consultasPeCMSU);
	}

	/**
	 * Hace un remarcado y clic en PEOXXO-AR10LIM-TRANSACCIONES / Transacciones /
	 * Transacciones
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickRunTransaccionesAR10LIM() {
		u.highLight(transaccionesAR10LIM);
		u.click(transaccionesAR10LIM);
	}

	/**
	 * Hace un remarcado y clic en la Vista XML de la pĂĄgina en donde se muestra la
	 * factura del caso de prueba
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickVistaXML() {
		u.highLight(vistaXML);
		u.click(vistaXML);
	}

}