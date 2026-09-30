package WEB.I123057_PagoQRspin_MVP3.om;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.Select;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import WEB.util.pageObjectModel.EBSLogin;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

/**
 * DESCRIPCION: Clase de utileria para manejo de EBS.
 * 
 * @author Roberto Renteria
 * @date 24/MARZO/2025
 */
public class PagoQRspin_EBS {
	
	EBSLogin inicio;
	String url;
	String userName;
	String password;
	SeleniumUtil u;
	TestCase testCase;
	SikuliUtil sk;
	String pathImg;
	File FolderPath;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
	
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
	
	//Objects
	By usernameField = By.id("usernameField");
	By passwordField = By.id("passwordField");
	By logInBtnXpath = By.xpath("//*[@id='ButtonBox']//button[@class='OraButton left']");
	By logInBtnEBSColXpath = By.xpath("//*[@id=\"SubmitButton\"]");
	By slangXpath = By.xpath("//div[@id='LanguagePickerBox']//select[@id='slang']");
	By SpanishSlangXpath = By.xpath("//div[@id='LanguagePickerBox']//select[@id='slang']//option[@lang='es']");
	By SpanishSlangColombiaXpath = By.xpath("//*[@id=\"langOptionsTable\"]/tbody/tr[3]/td[2]/a/img");
	By MXOXXOGL17ADMSERVICIOS = By.xpath("//div[@class='textdivresp' and text()='MXOXXO-GL17ADM-SERVICIOS']");
	By MenuTableIzq = By.id("menuTable");
	By carpetaInformes17ADMSERVICIOS = By.xpath("//div[@class='textdivresp' and text()='Informes']");
	By carpetaSolicitar17ADMSERVICIOS = By.xpath("//div[@class='textdivresp' and text()='Solicitar']");
	By btnStandar17ADMSERVICIOS = By.xpath("//div[@class='textdiv' and text()='Standard']");
	By MXOXXOGL10TIJConsultas = By.xpath("//div[@class='textdivresp' and text()='MXOXXO-GL10TIJ-Consultas']");
	By btnRequest= By.xpath("//div[@class='textdiv' and text()='Request']");
	By MXOXXOGL17ADMConsultas = By.xpath("//div[@class='textdivresp' and text()='MXOXXO-GL17ADM-Consultas']");

	public PagoQRspin_EBS(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
		this.sk = new SikuliUtil();
		this.pathImg = sk.PatternPath("WEB\\EBS");
		this.FolderPath = new File(pathImg);
	}
	
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
		case "MXOXXO-GL10TIJ-Consultas":
			return MXOXXOGL10TIJConsultas;
		case "MXOXXO-GL17ADM-Consultas":
			return MXOXXOGL17ADMConsultas;
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
	 * Ingresar a URL de EBS
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 08/09/2025
	 */
	
	public void ingresarEBS(String url) throws Exception {
		try {
			u.get(url);
			u.waitForLoadPage();
			testCase.addScreenShotCurrentStep(u, "Acceso a la URL");
		} catch (TimeoutException timeoutException) {
			logger.error("TimeOut " + timeoutException);
		}
	}
	
	/**
	 * Click en boton log in de EBS
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void clickLogIn() {
		try {
			u.highLight(logInBtnXpath);
			u.click(logInBtnXpath);
			u.waitForLoadPage();
			testCase.addScreenShotCurrentStep(u, "Se da clic en Conectar");
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}
	}
	
	/**
	 * Cambiar idioma de EBS a Español
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void setLanguage() {
		try {
			u.highLight(slangXpath);
			Select language = new Select(u.getDriver().findElement(slangXpath));
			language.selectByValue("ESA");
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}

	}
	
	/**
	 * Ingresar credenciales al LogIn
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void setCredentials(String user, String password) {
		String pssDecrypted = PasswordUtil.decryptPassword(password);
		try {
			u.highLight(usernameField);
			u.sendKeys(usernameField, user);
			u.highLight(passwordField);
			u.sendKeys(passwordField, pssDecrypted);
			testCase.addScreenShotCurrentStep(u, "Se agregan credenciales");
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
		}

	}
	
	/**
	 * Realizar scroll a la pantalla hsta encontrar el elemento 17ADM
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void scrollToElementIfPresent17ADM() throws Exception {
	    try {
	    	u.waitWebElement(MenuTableIzq, 5, 0);
	    	if(u.isDisplayed(MenuTableIzq)) {
	    	u.highLight(MenuTableIzq);
	    	u.hardWait(2);
	    	u.scrollToElement(MXOXXOGL17ADMSERVICIOS);
	    	u.highLight(MXOXXOGL17ADMSERVICIOS);
	    	}
	    	else{
	    		throw new Exception("No se logro realizar el scroll al elemento");
	    	}
	    	
	    } catch (Exception e) {
	    	throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Abrir pestañas Solicitas, Informes y Standar
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void clickOnInformesSolicitudStandard(By carpeta1, By carpeta2, By carpeta3) throws Exception {
		try {
		u.hardWait(5);
		u.highLight(carpeta1);
		u.click(carpeta1);
		u.hardWait(6);
		u.highLight(carpeta2);
		u.click(carpeta2);
		u.hardWait(6);
		u.highLight(carpeta3);
		u.click(carpeta3);
		u.hardWait(10);
		}catch(Exception e) {
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	
	
	/**
	 * Método para abrir un aplicativo de oracle EBS Java usando Chrome
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void abrirAppletChrome() throws Exception {
		u.hardWait(5);
		try {
		Pattern btnconservar = new Pattern(FolderPath +"\\btnConservarChromee.png").similar((float).80);
		Pattern contDesc = new Pattern(FolderPath +"\\continuarDescargaChrome.png").similar((float).75);
		Pattern descSinVer = new Pattern(FolderPath +"\\descargarArchivosinVerificar.png").similar((float).75);
		Pattern aceptoLosRiesgosCheckbox = new Pattern(FolderPath +"\\boxAceptoRiesgos.png").similar((float).75);
		Pattern ejecutar = new Pattern(FolderPath +"\\btnEjecutar.png").similar((float).75);	
		Pattern btnChDescarga = new Pattern(FolderPath +"\\btnChromeDescargas.png").similar((float).70);
		
		if (sk.screen.exists(btnChDescarga,20)!=null) {
			sk.screen.click(btnChDescarga);	
		}
		
		if (sk.screen.exists(btnconservar,20)!=null) {
			sk.screen.click(btnconservar);	
		}
		
		if (sk.screen.exists(contDesc,20)!=null) {
			sk.screen.click(contDesc);	
		}
		
		if (sk.screen.exists(descSinVer,20)!=null) {
			sk.screen.click(descSinVer);	
		}
		
		String home = System.getProperty("user.home");
		File directoryDownloads = new File(home+"/Downloads");
		
		File latestFile = this.getLastModifiedFile(directoryDownloads);
		
		u.hardWait(5);
		Desktop.getDesktop().open(latestFile);
		u.hardWait(5);
					
		
		if (sk.screen.exists(aceptoLosRiesgosCheckbox,180)!=null) {
			sk.screen.click(aceptoLosRiesgosCheckbox);	
		}
		else {
			System.out.println("No se pudo dar clic en el checkbox de aceptar riesgo de aplicacion");
			throw new NoSuchElementException("Fallo en click a checkbox riesgos");
		}
		if (sk.screen.exists(ejecutar,20)!=null) {
			sk.screen.click(ejecutar);	
		}
		else {
			System.out.println("No se pudo dar clic en el boton Ejecutar de mensaje de riesgo");
			throw new NoSuchElementException("Fallo en clic a boton Ejecutar de mensaje de riesgo");
		}
		}catch(Exception e) {
			u.quit();
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	
	/**
	 * Validacion de generacion de proceso de dispersion en BD AVEBQA_OSI
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 05/09/2025
	 */
	
	public void validProcesoDispersionQRSPIN(String username, String formapago) throws Exception {
		String query = "SELECT\r\n"
				+ "   distinct user_concurrent_program_name,\r\n"
				+ "    user_name,\r\n"
				+ "    responsibility_name,\r\n"
				+ "    request_date,\r\n"
				+ "    argument_text,\r\n"
				+ "    request_id,\r\n"
				+ "    phase_code,\r\n"
				+ "    status_code,\r\n"
				+ "    logfile_name,\r\n"
				+ "    outfile_name,\r\n"
				+ "    output_file_type,\r\n"
				+ "    hold_flag,\r\n"
				+ "    fcr.*\r\n"
				+ "FROM\r\n"
				+ "    apps.fnd_concurrent_requests fcr,\r\n"
				+ "    apps.fnd_concurrent_programs_tl fcp,\r\n"
				+ "    apps.fnd_responsibility_tl fr,\r\n"
				+ "    apps.fnd_user fu\r\n"
				+ "WHERE\r\n"
				+ "    fcr.CONCURRENT_PROGRAM_ID = fcp.concurrent_program_id\r\n"
				+ "    and fcr.responsibility_id = fr.responsibility_id\r\n"
				+ "    and fcr.requested_by = fu.user_id\r\n"
				+ "    and user_concurrent_program_name = ('XXFC-Proceso de Dispersion Pago QR General')\r\n"
				+ "ORDER BY fcr.REQUEST_DATE DESC";
		
        SQLUtil dbAVEBQA_OSI = new SQLUtil(PagoQRspin_MVP3.DB_HOST_AVEBQA_OSI, PagoQRspin_MVP3.DB_USER_AVEBQA_OSI, PagoQRspin_MVP3.DB_PASSWORD_AVEBQA_OSI);
        SQLResult result = dbAVEBQA_OSI.executeQuery(query);
        System.out.println(query);
		
        if (result != null && result.getRowCount() > 0) {
            // tomar el primer registro (índice 0)
            String requestDate = result.getData(0, "REQUEST_DATE");
            String userName = result.getData(0, "USER_NAME");
            String argumentText = result.getData(0, "ARGUMENT_TEXT");
	        LocalDate hoy = LocalDate.now();
	        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String today = hoy.format(formato);
            //String today = new SimpleDateFormat("dd/MM/yy").format(new Date());

            testCase.addTextEvidenceCurrentStep("REQUEST_DATE obtenido: " + requestDate);
            testCase.addTextEvidenceCurrentStep("Fecha esperada: " + today);
            testCase.addTextEvidenceCurrentStep("USER_NAME obtenido: " + userName);
            testCase.addTextEvidenceCurrentStep("ARGUMENT_TEXT obtenido: " + argumentText);

            if (!requestDate.contains(today)) {
                throw new Exception("El REQUEST_DATE (" + requestDate + ") no corresponde al día actual (" + today + ")");
            }

            if (!username.equals(userName)) {
                throw new Exception("El USER_NAME esperado es '"+ username +"' pero se obtuvo: " + userName);
            }

            if (!argumentText.contains(formapago)) {
                throw new Exception("El ARGUMENT_TEXT no contiene '"+ formapago +"'. Valor obtenido: " + argumentText);
            }

            testCase.addTextEvidenceCurrentStep("Todas las validaciones pasaron correctamente.");
        } else {
            throw new Exception("No se encontró ningún registro en la consulta.");
        }
    }
	
	
	
	/**
	 * Método para obtener el nombre del ultimo archivo de determinado directorio
	 * @author Roberto Rentería
	 * @param directory - file con la ruta del folder a buscar
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public File getLastModifiedFile(File directory) throws Exception {
	try {
	    File[] files = directory.listFiles();
	   if (files.length == 0) return null;
	    Arrays.sort(files, new Comparator<File>() {
	        @SuppressWarnings("deprecation")
			public int compare(File o1, File o2) {
	            return new Long(o2.lastModified()).compareTo(o1.lastModified()); 
	        }});
	    return files[0];
	}catch(Exception e) {
		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
		throw new Exception("Error: " + e.getMessage());
	}
	}
	
	/**
	 * Método para dar clic en el menu Ver
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void verMenuSol() throws Exception {
		try {
		Pattern btnVer = new Pattern(FolderPath +"\\ver.png").similar((float).75);
		Pattern solicitudes = new Pattern(FolderPath +"\\solicitudes.png").similar((float).75);
		
		if (sk.screen.exists(btnVer,20)!=null) {
			sk.screen.click(btnVer);	
		}
		
		if (sk.screen.exists(solicitudes,20)!=null) {
			testCase.addScreenShotCurrentStep("");
			sk.screen.click(solicitudes);	
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para dar clic en Aceptar del panel de nueva solicitud
	 * @author Juan Gerardo Lopez
	 * @throws Exception 
	 * @date 11/Septiembre/2025
	 */	
	public void aceptarNewSolicitud() throws Exception {
		u.hardWait(8);
		try {
		Pattern btnAceptar = new Pattern(FolderPath +"\\btnAceptarNewSolicitud.png").similar((float).75);
		
		if (sk.screen.exists(btnAceptar,20)!=null) {
			sk.screen.click(btnAceptar);	
			testCase.addScreenShotCurrentStep("Se ingresa a modulo de nueva solicitud");
		}
		else {
			System.out.println("No se pudo dar clic en el boton Aceptar de nueva solicitud");
			throw new NoSuchElementException("Fallo en clic a boton Aceptar");
		}

		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para escribir el nombre del concurrente a buscar
	 * @author Roberto Rentería
	 * @date 24/Marzo/2025
	 * @param concurrente - texto con el nombre del concurrente a buscar
	 * @throws Exception 
	 */	
	public void typeConcurrente(String concurrente) throws Exception {
		try {
		
		Pattern txtbox_Nombre = new Pattern(FolderPath +"\\nombreclick.png").similar((float).75);
			
		if (sk.screen.exists(txtbox_Nombre,20)!=null) {
			sk.screen.click(txtbox_Nombre);
			u.hardWait(5);
			//sk.screen.type(concurrente);
			sk.screen.paste(concurrente);
			sk.screen.type(Key.TAB);
			testCase.addScreenShotCurrentStep("Se coloca en la solicitud");
			}
		else {
			System.out.println("No se pudo escribir el concurrente");
			throw new NoSuchElementException("Fallo en escribir el concurrente");
			}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para escribir el nombre de la forma de pago en modulo de parametros
	 * @author Roberto Rentería
	 * @date 24/Marzo/2025
	 * @param fp - texto con el nombre de la forma de pago a buscar
	 * @throws Exception 
	 */	
	public void typeFormaPago(String fp) throws Exception {
		try {
		Pattern formaPago = new Pattern(FolderPath +"\\formapagodispersion.png").similar((float).75);
		
		if (sk.screen.exists(formaPago,20)!=null) {
			sk.screen.type(fp);
			testCase.addScreenShotCurrentStep("Se agrega forma de pago");
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para escribir la fecha inicio en parametros
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void typeFechaIni(String date1) throws Exception {
		try {
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		sk.screen.type(date1);
		u.hardWait(4);
		testCase.addScreenShotCurrentStep("Se agrega fecha inicio");
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	
	/**
	 * Método para escribir la fecha final en parametros
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void typeFechaFinal(String date1) throws Exception {
		try {
		u.hardWait(2);
		sk.screen.type(Key.TAB);
		sk.screen.type(date1);
		testCase.addScreenShotCurrentStep("se agrega fecha fin");
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	
	/**
	 * Método para dar clic en el boton Aceptar en parametros
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void aceptarParametros() throws Exception {
		u.hardWait(5);
	try {
		Pattern btnAceptar = new Pattern(FolderPath +"\\btnAceptarParametros.png").similar((float).75);
		
		if (sk.screen.exists(btnAceptar,20)!=null) {
			sk.screen.click(btnAceptar);
			testCase.addScreenShotCurrentStep("Se da clic en boton aceptar del modulo parametros");
		}
		else {
			System.out.println("No se pudo dar clic en boton Aceptar en el modulo de parametros");
			throw new NoSuchElementException("Fallo en clic a boton Aceptar de modulo parametros");
		}
	}catch(Exception e) {
		testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
		throw new Exception("Error: " + e.getMessage());
	}
	}
	
	/**
	 * Método para dar clic en el botón Ejecutar
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void clickEjecutar() throws Exception {
		try {
		Pattern btnEjecutarEBS = new Pattern(FolderPath +"\\ejecutar.png").similar((float).75);
		
		if (sk.screen.exists(btnEjecutarEBS,20)!=null) {
			sk.screen.click(btnEjecutarEBS);
			testCase.addScreenShotCurrentStep("Se da clic en boton Ejecutar");
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para dar clic en el botón No
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void clicNo() throws Exception {
		try {
		Pattern no = new Pattern(FolderPath +"\\no.png").similar((float).75);
		
		if (sk.screen.exists(no,20)!=null) {
			testCase.addScreenShotCurrentStep("Click btn No");
			sk.screen.click(no);
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para dar clic en el botón Encontrar
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void clicEncontrar() throws Exception {
		try {
		Pattern encontrar = new Pattern(FolderPath +"\\encontrar.png").similar((float).75);
		
		if (sk.screen.exists(encontrar,20)!=null) {
			sk.screen.click(encontrar);	
			testCase.addScreenShotCurrentStep("Click botón Encontrar");
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para cerrar aplicativo EBS
	 * @author Juan Gerardo Lopez
	 * @date 12/Septiembre/2025
	 * @throws Exception 
	 */	
	public void closeAppEBS() throws Exception {
		try {
		//Robot robot = new Robot();
		
		Pattern AceptarCierre = new Pattern(FolderPath +"\\btnAceptarCierre.png").similar((float).75);
		Pattern botonX = new Pattern(FolderPath +"\\btnX.png").similar((float).75);
		
		//sk.screen.type(Key.ALT, "F4");
		//u.hardWait(2);
		//sk.screen.type(Key.ALT);
		//robot.keyPress(KeyEvent.VK_ALT);
		//robot.keyRelease(KeyEvent.VK_ALT); 
		
		if (sk.screen.exists(botonX,20)!=null) {
			sk.screen.click(botonX);
			}
			
		if (sk.screen.exists(AceptarCierre,20)!=null) {
			testCase.addScreenShotCurrentStep("Se cierra aplicacion EBS");
			sk.screen.click(AceptarCierre);
			}
		else {
			System.out.println("No se cerrar aplicacion EBS");
			throw new NoSuchElementException("Fallo en cierre de APP EBS");
			}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}

	/**
	 * Método para agregar parametros de Generacion de Carta Plaza
	 * @author Juan Gerardo Lopez
	 * @date 15/Septiembre/2025
	 * @throws Exception 
	 */	
	public void paramCartaPlaza(String compañia, String cuentaservicio, String tiporeporte, String fechainicio, String fechafinn) throws Exception {
		try {
			Pattern txtCompania = new Pattern(FolderPath +"\\btnCompania.png").similar((float).75);
			Pattern txtCuentaServ = new Pattern(FolderPath +"\\txtCuentaServ.png").similar((float).75);
			Pattern txtTipoReport = new Pattern(FolderPath +"\\txtTipoReport.png").similar((float).75);
			Pattern fechaini = new Pattern(FolderPath +"\\txtfechaini.png").similar((float).75);
			Pattern fechafin = new Pattern(FolderPath +"\\txtfechafin.png").similar((float).75);
			Pattern btnAceptar = new Pattern(FolderPath +"\\btnAceptarParametros.png").similar((float).75);
			
			if(sk.screen.exists(txtCompania, 15)!=null) {
				sk.screen.type(compañia);
				u.hardWait(2);
				sk.screen.type(Key.TAB);
				//sk.screen.type(Key.TAB);
				testCase.addScreenShotCurrentStep("Se escribe el parametro Compañia");
			}else {
				System.out.println("No se logro escribir la compañia en el textbox");
				throw new NoSuchElementException("Fallo la escritura en el campo compañia");
				}
			if(sk.screen.exists(txtCuentaServ, 5)!=null) {
				sk.screen.type(cuentaservicio);
				sk.screen.type(Key.TAB);
				u.hardWait(3);
				testCase.addScreenShotCurrentStep("Se escribe el parametro Cuenta Servicio");
			}else {
				System.out.println("No se logro escribir el campo Cuenta de servicio");
				throw new NoSuchElementException("Fallo la escritura en el campo Cuenta de servicio");
				}
			if(sk.screen.exists(txtTipoReport, 5)!=null) {
				sk.screen.type(tiporeporte);
				sk.screen.type(Key.TAB);
				u.hardWait(3);
				testCase.addScreenShotCurrentStep("Se escribe el parametro Tipo de Reporte");
			}else {
				System.out.println("No se logro escribir el campo Tipo de reporte");
				throw new NoSuchElementException("Fallo la escritura en el campo Tipo de reporte");
				}
			if(sk.screen.exists(fechaini, 5)!=null) {
				sk.screen.type(fechainicio);
				sk.screen.type(Key.TAB);
				u.hardWait(2);
				testCase.addScreenShotCurrentStep("Se escribe el parametro fecha inicio");
			}else {
				System.out.println("No se logro escribir la fecha de inicio");
				throw new NoSuchElementException("Fallo la escritura en el campo Fecha inicio");
				}
			if(sk.screen.exists(fechafin, 5)!=null) {
				sk.screen.type(fechafinn);
				sk.screen.type(Key.TAB);
				u.hardWait(2);
				testCase.addScreenShotCurrentStep("Se escribe el parametro fecha fin");
			}else {
				System.out.println("No se logro escribir la fecha fin");
				throw new NoSuchElementException("Fallo la escritura en el campo Fecha final");
				}
			if(sk.screen.exists(btnAceptar,10)!=null) {
				sk.screen.click(btnAceptar);
				u.hardWait(2);
				testCase.addScreenShotCurrentStep("Se da clic en boton aceptar del modulo parametros");
			}else {
				System.out.println("No se logro dar clic en el boton Aceptar");
				throw new NoSuchElementException("Fallo al dar clic en el boton Aceptar parametros");
				}
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Realizar scroll a la pantalla hsta encontrar el elemento 10TIJ-CONSULTA
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void scrollToElementIfPresent10TIJCONSULTA() throws Exception {
	    try {
	    	u.waitWebElement(MenuTableIzq, 5, 0);
	    	if(u.isDisplayed(MenuTableIzq)) {
	    	u.highLight(MenuTableIzq);
	    	u.hardWait(2);
	    	u.scrollToElement(MXOXXOGL10TIJConsultas);
	    	u.highLight(MXOXXOGL10TIJConsultas);
	    	}
	    	else{
	    		throw new Exception("No se logro realizar el scroll al elemento");
	    	}
	    	
	    } catch (Exception e) {
	    	throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/*
	 * metodo utilizado para ver el resultado del request
	 * @param 
	 * @author Mariana Avitia
	 * @throws FindFailed, AWTException
	 * @date 27/Marzo/2025
	 */
	public boolean viewOutput() throws FindFailed, AWTException {
       try {
        Pattern pattern = new Pattern(FolderPath +"\\visualizaroutput.png").similar((float).75);        
        
        // Verificar si el patrón se e
        if (sk.screen.exists(pattern,30)!=null) {
                sk.screen.wait(pattern).highlight(2, "red");
                sk.screen.click(pattern);
                testCase.addScreenShotCurrentStep("Click botón view Output");
                u.hardWait(2);
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
        	testCase.addCodeEvidenceCurrentStep("fallo al encontrar el elemento");
        	return false;
        	}
       }catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new AWTException("Error: " + e.getMessage());
       }
	}
	
	/**
	 * Método para abrir reporte excel descragado
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void abrirReprteExcel() throws Exception {
		u.hardWait(3);
		try {
		Pattern btnconservar = new Pattern(FolderPath +"\\btnConservarChrome.png").similar((float).75);
		Pattern btnChDescarga = new Pattern(FolderPath +"\\btnChromeDescargas.png").similar((float).80);
		Pattern btnSiAbrirCartaOS = new Pattern(FolderPath +"\\btnSiDescargaCartaOS.png").similar((float).80);

		
		if (sk.screen.exists(btnChDescarga,20)!=null) {
			sk.screen.click(btnChDescarga);	
		}else {
			System.out.println("No dar clic en el boton de descargas en navegador");
			throw new Exception("Fallo al dar clic en boton de descargas del navegador");
			}
		
		if (sk.screen.exists(btnconservar,20)!=null) {
			sk.screen.click(btnconservar);	
		}else {
			System.out.println("No se logro dar clic en el boton conservar");
			throw new Exception("Fallo al dar clic en el boton conservar");
			}
		
		u.hardWait(3);
		
		String home = System.getProperty("user.home");
		File directoryDownloads = new File(home+"/Downloads");
		
		File latestFile = this.getLastModifiedFile(directoryDownloads);
		
		u.hardWait(5);
		Desktop.getDesktop().open(latestFile);
		u.hardWait(5);
		
		if (sk.screen.exists(btnSiAbrirCartaOS,20)!=null) {
			sk.screen.click(btnSiAbrirCartaOS);	
		}
		
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
			}
	}
	
	/**
	 * Método para validar formato de reporte consolidado de cartas plaza
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public boolean validarReportePlaza() throws Exception {
		try {
		Pattern infoCartaPlaza = new Pattern(FolderPath +"\\infCartaPlaza.png").similar((float).80);
		Pattern camposCartaPlaza = new Pattern(FolderPath +"\\camposCartaPlaza.png").similar((float).80);
		
		if (sk.screen.exists(infoCartaPlaza,30)!=null) {
			sk.screen.wait(infoCartaPlaza).highlight(5, "blue");
			testCase.addScreenShotCurrentStep("Nombre de archivo encontrado");
			return true;
		}
		
		if (sk.screen.exists(camposCartaPlaza,30)!=null) {
			sk.screen.wait(camposCartaPlaza).highlight(5, "green");
			testCase.addScreenShotCurrentStep("Campos encontrados");
			return true;
		}
		
		
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("El reporte no tiene el formato esperado" );
			throw new Exception("Error: " + e.getMessage());
			
			}
		return false;
		
	}
	
	/**
	 * Método para validar que la peticion se haya ejecutado por completo y finalizado
	 * @author Juan Gerardo Lopez
	 * @throws Exception 
	 * @date 12/Septiembre/2025
	 */	
	
	public boolean validRequestComplet() throws FindFailed, AWTException {
	    try {
	    	Pattern refreshAut = new Pattern(FolderPath +"\\boxRefreshAut.png").similar(0.75f);
	        Pattern dataRefresh = new Pattern(FolderPath +"\\btnRefrescarDatos.png").similar(0.75f);
	        Pattern notEjecution = new Pattern(FolderPath +"\\lblNotEjecution.png").similar(0.75f);
	        Pattern ejecutionComplet = new Pattern(FolderPath +"\\lblEjecutionComplet.png").similar(0.75f);
	
	        long startTime = System.currentTimeMillis();
	        long timeout = 5 * 60 * 1000;
	        
	        if (sk.screen.exists(refreshAut, 10) != null) {
	            sk.screen.click(refreshAut);
	            testCase.addScreenShotCurrentStep("Click en botón Refrescar Datos automáticamente");
	        } else {
	            return false;
	        }
	
	        while (true) {
	            // Validar si ya está completa
	            if (sk.screen.exists(ejecutionComplet, 5) != null) {
	                System.out.println("Ejecución completada correctamente");
	                testCase.addScreenShotCurrentStep("Ejecución completada");
	                return true;
	            }
	
	            // Si aún aparece "Not Execution"
	            if (sk.screen.exists(notEjecution, 5) != null) {
	                System.out.println("Solicitud aún en proceso, refrescando datos...");
	                if (sk.screen.exists(dataRefresh, 10) != null) {
	                    sk.screen.click(dataRefresh);
	                    testCase.addScreenShotCurrentStep("Click en botón Refrescar Datos");
	                }
	            }
	
	            u.hardWait(5);
	
	            if ((System.currentTimeMillis() - startTime) > timeout) {
	                System.out.println("La solicitud no se completó en el tiempo esperado");
	                return false;
	            }
	        }
	
	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new AWTException("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Método para presionar tecla ENTER
	 * @author Juan Gerardo Lopez
	 * @throws Exception 
	 * @date 15/Septiembre/2025
	 */	
	public void keyEnter() throws Exception {
		u.hardWait(5);
		sk.type(Key.ENTER);
	}
	
	/**
	 * Método para cerrar un proceso
	 * @author Juan Gerardo Lopez
	 * @throws Exception 
	 * @date 17/Septiembre/2025
	 */	
	
	public void killProcess(String[] processName) {
		try {
			for (String process : processName ) {
			Process p;
			try {
				p = Runtime.getRuntime().exec("taskkill /F /IM " + process);
				p.waitFor();
			} catch (Exception e) {
				System.out.println(e.toString());
			}	
		}
	}catch(Exception e) {
		System.out.println(e.toString());
	}
}
	
	/**
	 * Realizar scroll a la pantalla hsta encontrar el elemento 10TIJ-CONSULTA
	 * @author Gerardo Lopez
	 * @throws Exception 
	 * @date 09/09/2025
	 */
	
	public void scrollToElementIfPresentGL17ADMConsultas() throws Exception {
	    try {
	    	u.waitWebElement(MenuTableIzq, 5, 0);
	    	if(u.isDisplayed(MenuTableIzq)) {
	    	u.highLight(MenuTableIzq);
	    	u.hardWait(2);
	    	u.scrollToElement(MXOXXOGL17ADMConsultas);
	    	u.highLight(MXOXXOGL17ADMConsultas);
	    	}
	    	else{
	    		throw new Exception("No se logro realizar el scroll al elemento");
	    	}
	    	
	    } catch (Exception e) {
	    	throw new Exception("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Método para validar formato de reporte consolidado de cartas os
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public boolean validarReporteOS() throws Exception {
		try {
		Pattern infoCartaPlaza = new Pattern(FolderPath +"\\lblReporteCartaOS.png").similar((float).80);
		Pattern camposCartaPlaza = new Pattern(FolderPath +"\\camposCartaOS.png").similar((float).75);
		
		if (sk.screen.exists(infoCartaPlaza,30)!=null) {
			sk.screen.wait(infoCartaPlaza).highlight(5, "blue");
			testCase.addScreenShotCurrentStep("Nombre de archivo encontrado");
			return true;
		}else {
			System.out.println("No se logro validar reporte");
			}
		
		if (sk.screen.exists(camposCartaPlaza,30)!=null) {
			sk.screen.wait(camposCartaPlaza).highlight(5, "green");
			testCase.addScreenShotCurrentStep("Campos encontrados");
			return true;
		}
		
		
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("El reporte no tiene el formato esperado" );
			throw new Exception("Error: " + e.getMessage());
			
			}
		return false;
		
	}
	
	/**
	 * Método para agregar parametros de Generacion de Carta OS
	 * @author Juan Gerardo Lopez
	 * @date 15/Septiembre/2025
	 * @throws Exception 
	 */	
	public void paramCartaOS(String compañia, String cuentaservicio, String fechainicio, String fechafinn) throws Exception {
		try {
			Pattern txtCompania = new Pattern(FolderPath +"\\btnCompania.png").similar((float).75);
			Pattern txtCuentaServ = new Pattern(FolderPath +"\\txtCuentaServ.png").similar((float).75);
			Pattern fechaini = new Pattern(FolderPath +"\\txtfechaini.png").similar((float).75);
			Pattern fechafin = new Pattern(FolderPath +"\\txtfechafin.png").similar((float).75);
			Pattern btnAceptar = new Pattern(FolderPath +"\\btnAceptarParametros.png").similar((float).75);
			
			if(sk.screen.exists(txtCompania, 15)!=null) {
				sk.screen.type(compañia);
				u.hardWait(2);
				sk.screen.type(Key.TAB);
				//sk.screen.type(Key.TAB);
				testCase.addScreenShotCurrentStep("Se escribe el parametro Compañia");
			}else {
				System.out.println("No se logro escribir la compañia en el textbox");
				throw new Exception("Fallo la escritura en el campo compañia");
				}
			if(sk.screen.exists(txtCuentaServ, 5)!=null) {
				sk.screen.type(cuentaservicio);
				sk.screen.type(Key.TAB);
				u.hardWait(3);
				testCase.addScreenShotCurrentStep("Se escribe el parametro Cuenta Servicio");
			}else {
				System.out.println("No se logro escribir el campo Cuenta de servicio");
				throw new Exception("Fallo la escritura en el campo Cuenta de servicio");
				}
			if(sk.screen.exists(fechaini, 5)!=null) {
				sk.screen.type(fechainicio);
				sk.screen.type(Key.TAB);
				u.hardWait(2);
				testCase.addScreenShotCurrentStep("Se escribe el parametro fecha inicio");
			}else {
				System.out.println("No se logro escribir la fecha de inicio");
				throw new Exception("Fallo la escritura en el campo Fecha inicio");
				}
			if(sk.screen.exists(fechafin, 5)!=null) {
				sk.screen.type(fechafinn);
				sk.screen.type(Key.TAB);
				u.hardWait(2);
				testCase.addScreenShotCurrentStep("Se escribe el parametro fecha fin");
			}else {
				System.out.println("No se logro escribir la fecha fin");
				throw new Exception("Fallo la escritura en el campo Fecha final");
				}
			if(sk.screen.exists(btnAceptar,10)!=null) {
				sk.screen.click(btnAceptar);
				u.hardWait(2);
				testCase.addScreenShotCurrentStep("Se da clic en boton aceptar del modulo parametros");
			}else {
				System.out.println("No se logro dar clic en el boton Aceptar");
				throw new Exception("Fallo al dar clic en el boton Aceptar parametros");
				}
			
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para validar que la peticion se haya ejecutado por completo y finalizado de carta OS
	 * @author Juan Gerardo Lopez
	 * @throws Exception 
	 * @date 17/Septiembre/2025
	 */	
	
	public boolean validRequestCompletCartaOS() throws FindFailed, AWTException {
	    try {
	        Pattern notEjecution = new Pattern(FolderPath +"\\EjecucionProceso.png").similar(0.95f);
	        Pattern ejecutionComplet = new Pattern(FolderPath +"\\EjecucionFinalizada.png").similar(0.95f);
	        Pattern refreshAut = new Pattern(FolderPath +"\\boxRefreshAut.png").similar(0.75f);
	        
	        long startTime = System.currentTimeMillis();
	        long timeout = 8 * 60 * 1000;

	        if (sk.screen.exists(refreshAut, 10) != null) {
	            sk.screen.click(refreshAut);
	            testCase.addScreenShotCurrentStep("Click en botón Refrescar Datos automáticamente");
	        } else {
	            return false;
	        }

	        while (true) {
	            if (sk.screen.exists(ejecutionComplet, 5) != null) {
	            	sk.screen.wait(ejecutionComplet).highlight(3, "red");
	                System.out.println("Ejecución completada correctamente");
	                testCase.addScreenShotCurrentStep("Ejecución completada");
	                return true;
	            }

	            if (sk.screen.exists(notEjecution, 5) != null) {
	            	sk.screen.wait(notEjecution).highlight(3, "red");
	                System.out.println("Solicitud aún en proceso... esperando 10 segundos");
	            } else {
	                System.out.println("No se detecta ni 'Not Execution' ni 'Ejecution Complet', seguimos esperando...");
	            }

	            u.hardWait(10);

	            if ((System.currentTimeMillis() - startTime) > timeout) {
	                System.out.println("La solicitud no se completó en el tiempo esperado");
	                return false;
	            }
	        }

	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new AWTException("Error: " + e.getMessage());
	    }
	}
	
	/**
	 * Método para dar clic en el botón Visualizar Registro
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void clicVRegistro() throws Exception {
		try {
		u.hardWait(4);
		Pattern vreg = new Pattern(FolderPath +"\\visualizarregistro.png").similar((float).75);
		if (sk.screen.exists(vreg,20)!=null) {
			sk.screen.click(vreg);	
			testCase.addScreenShotCurrentStep("Clic en visualizar registro");
		}
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Método para obtener el log al visualizar el registro
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void validLogContainMsjError() throws Exception {
	    try {
	        u.hardWait(7);
	        sk.screen.type(Key.ESC);

	        sk.screen.type("a", Key.CTRL);
	        u.hardWait(5);

	        sk.screen.type("c", Key.CTRL);
	        u.hardWait(3);

	        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
	        String logContent = c.getData(DataFlavor.stringFlavor).toString();

	        if (logContent.contains("No se encontró información con los parámetros ingresados")) {
	            testCase.addCodeEvidenceCurrentStep("Se encontró el mensaje de: No se encontró información con los parámetros ingresados");
	        } else if (logContent.contains("ORA-0000: normal, successful completion")) {
	            testCase.addCodeEvidenceCurrentStep("Se encontró el mensaje de ejecución exitosa ORA-0000");
	        } else {
	            testCase.addCodeEvidenceCurrentStep("No se encontró ninguno de los mensajes esperados");
	        }

	    } catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
	        throw new Exception("Error: " + e.getMessage());
	    }
	}
	/**
	 * Método para obtener el log al visualizar el registro
	 * @author Roberto Rentería
	 * @throws Exception 
	 * @date 24/Marzo/2025
	 */	
	public void getLog() throws Exception {
		
		try {
		u.hardWait(7);
	
		sk.screen.type(Key.ESC);
		
		sk.screen.type("a",Key.CTRL);
		
		u.hardWait(5);
		
		sk.screen.type("c",Key.CTRL);
		
		u.hardWait(3);
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		testCase.addCodeEvidenceCurrentStep(c.getData(DataFlavor.stringFlavor).toString());
		}catch(Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			throw new Exception("Error: " + e.getMessage());
		}
		
	}
	
	
	
}