package EBS.cfeEnLinea.om;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Match;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;

import modelo.TestCase;
import utils.SikuliUtil;
import utils.selenium.SeleniumUtil;




public class BaseCfeEnLinea{

	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private WebDriver driver;
	
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
	//objects
	
	
	By footerboxEbs = By.id("FooterBox");
	
	By TXTUser = By.id("usernameField");
	By TXTPassword = By.id("passwordField");

	/*
	 * Inicio Responsabilidades
	 */

	By DesarrolladordeAplicaciones = By.xpath("//div[normalize-space()='Desarrollador de Aplicaciones']");
	By MXOXXOcartasFemsa = By.xpath("//div[normalize-space()='MXOXXO-GLXXXXX-CARTAS-FEMSA']");
	By MXOXXOGL17ADMSERVICIOSADTIVOOS = By.xpath("//div[normalize-space()='MXOXXO-GL17ADM-SERVICIOS_ADTIVO_OS']");
	By MXOXXOGL17ADMCARTASFEMSA = By.xpath("//div[normalize-space()='MXOXXO-GL17ADM-CARTAS-FEMSA']");
	By MXOXXOAP17ADMUSUARIOOSADTIVOOS = By.xpath("//div[normalize-space()='MXOXXO-AP17ADM-USUARIO_OS_ADTIVO_OS']");

	By MXOXXOCO10VCZUSUARIO32SGR = By.xpath("//div[normalize-space()='MXOXXO-CO10VCZ-USUARIO-32SGR']");
	By MXOXXOPOXXXXXSolicitudesdeInversion = By.xpath("//div[normalize-space()='MXOXXO-POXXXXX-Solicitudes de Inversion']");
	By MXOXXOAPXXXXXSuperUsuario = By.xpath("//a[normalize-space()='MXOXXO-APXXXXX-Super Usuario']");
	By MXOXXOAPXXXXXADMONPROVEEDORES = By.xpath("//a[normalize-space()='MXOXXO-APXXXXX-ADMON-PROVEEDORES']");
	By MXOXXOGLXXXXXSuperUsuario = By.xpath("//a[normalize-space()='MXOXXO-GLXXXXX-Super Usuario']");	
	By MXOXXOFNDXXXXXUsuarioCargaMasiva = By.xpath("//a[normalize-space()='MXOXXO-FNDXXXXX-Usuario carga masiva']");
	By MXOXXOAPXXXXXCONSULTAS = By.xpath("//div[normalize-space()='MXOXXO-APXXXXX-CONSULTAS']");
	By MXOXXOFNDXXXXXsuperusuarioconfigurar = By.xpath("//div[normalize-space()='MXOXXO-POXXXXX-Super Usuario Configurar']");
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

	//carpetas
		By Request = By.xpath("//a[normalize-space()='Request']");
	By Financial = By.xpath("//a[ normalize-space()='Financial']");
	By Informes = By.xpath("//a[ normalize-space()='Informes']");
	By Solicitar = By.xpath("//a[normalize-space()='Solicitar']");
	By Standard = By.xpath("//a[normalize-space()='Standard']");
	By Pagos = By.xpath("//a[normalize-space()='Pagos']");
	By Consulta = By.xpath("//a[normalize-space()='Consulta']");
	By Cuenta = By.xpath("//*[@id=\"LIConsulta\"]/a/div[2]");

	//Fin objects
	
	public BaseCfeEnLinea(SeleniumUtil u, TestCase testCase, SikuliUtil sk,HashMap<String, Pattern> ImgPattern) {
		
		this.u = u;
		this.sk = sk;
		this.driver = u.getDriver();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		this.testCase = testCase;
		this.ImgPattern = ImgPattern;
	}
	
	/**
     * Metodo utilizado para ingresar al portal EBS
     * @author Alan Perez
     * @param Responsabilidad string para hacer match con el listado de responsabilidades
     * @date 11/Noviembre/2025 
     */
	public void GoToEBSPage() {
	String url = "http://fcrebsqa.femcom.net:8011/OA_HTML/AppsLocalLogin.jsp?requestUrl=http%3A%2F%2Ffcrebsqa.femcom.net%3A8011%2FOA_HTML%2FOA.jsp%3Fpage%3D%2Foracle%2Fapps%2Ffnd%2Fframework%2Fnavigate%2Fwebui%2FNewHomePG%26homePage%3DY%26OAPB%3DFWK_HOMEPAGE_BRAND%26transactionid%3D570987066%26oapc%3D2%26oas%3D4ppKB3RdwGUZ9ZtkXsf3GA..&cancelUrl=http%3A%2F%2Ffcrebsqa.femcom.net%3A8011%2FOA_HTML%2FAppsLogin&langCode=US#dummyAnchor";
		u.get(url);
		u.getDriver().getTitle();
		u.getDriver().manage().window().maximize();
		u.getText(footerboxEbs);
		
		
	}

	/**
	 * Metodo utilizado para checar cual es la responsabilidad que se va a probar
	 * @param Responsabilidad Texto que nos indica que responsabilidad es a la que se desea acceder para realizar las pruebas
	 * @return Un WebElement dependiendo la responsabilidad que le enviamos como parametro en caso contrario de no tener la responsabilidad regresa null
	 * @date 11/Noviembre/2025
	 * @author Alan Perez
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
     * Metodo utilizado para hacer clic en la responsabilidad del portal EBS
     * @author Alan Perez
     * @param Responsabilidad string para hacer match con el listado de responsabilidades
     * @date 11/Noviembre/2025
     * @return true si realiza clic en el objeto responsabilidad
     * @return false si no encuentra la responsabilidad en la lista 
     */
	public boolean clickOnResponsabilidad(String responsabilidad) {
		u.waitForLoadPage();
		try {
			By obj = responsabilidad(responsabilidad);
			driver.manage().window().maximize();
			u.highLight(obj);
			testCase.addScreenShotCurrentStep("Clic en responsabilidad");
			u.click(obj);
			return true;
		} catch (Exception e) {
			logger.error("Error Responsabilidad no encontrada " + e);
			System.out.println("Error Responsabilidad no encontrada " + e);
			testCase.addScreenShotCurrentStep("No hizo clic en responsabilidad");
			return false;
		}

	}
	
	
		/**
		     * Metodo utilizado para ingresar las credenciales en la platafoma EBS
		     * @author Alan Perez
		     * @param User usuario de la cuenta
		     * @param password contraseña de la cuenta
		     * @date 11/Noviembre/2025
		     */
	public void IngresoCredenciales(String User, String Password) {
		u.highLight(TXTUser);
		u.getDriver().findElement(TXTUser).click();
		driver.findElement(TXTUser).sendKeys(User);
		driver.findElement(TXTPassword).click();
		driver.findElement(TXTPassword).sendKeys(Password);
	}

	

	
	
	
	/**
     * Metodo utilizado para validar las carpetas en el aplicativo EBS WEB
     * @author Alan Perez
     * @param carpeta a la cual se buscara en el listado
     * @date 11/Noviembre/2025
     */
	public By carpeta_c(String Carpeta) {
		switch (Carpeta)
		{		
		case "request":
			return Request ;
					
		case "Financial":
			return Financial;
			
		case "Informes":
			return Informes;
			
			case "Solicitar":
				return Solicitar;
				
			case "Standard":
				return Standard;
			case "Pagos":
				return Pagos;
				
			case "Consulta":
				return Consulta;
				
			case "Cuenta":
				return Cuenta;
		}
		return null;
	
		
		}
	
	
	/**
     * Metodo para esperar que un elemento sea clickeable
     * @author Alan Perez
     * @param elemento es el objeto a buscar
     * @param int segundos a esperar
     * @date 11/Noviembre/2025
     */
	public void waitElementToBeClickable(By elemento, int seconds) {

		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.elementToBeClickable(elemento)); 
		}
	
	
	
	/**
     * Metodo utilizado para hacer clic en la carpeta correspondiente del portal EBS
     * @author Alan Perez
     * @param Carpeta string para hacer match con el listado de carpetas
     * @date 11/Noviembre/2025
     * @return true si realiza clic en la carpeta del portal EBS
     * @return false si no encuentra la carpeta en la lista
     */
	public boolean ClickOnCarpeta(String carpeta) {
		// TODO Auto-generated method stub
		try {
			By obj = carpeta_c(carpeta);
			waitElementToBeClickable(obj,50);
			u.hardWait(5);
			u.highLight(obj);
			testCase.addScreenShotCurrentStep("Clic en la carpeta:"+" "+ carpeta);
			u.click(obj);
			u.hardWait(2);
			u.hardWait(3);		return true;
		} catch (Exception e) {
		
			System.out.println("Error Responsabilidad no encontrada " + e);
		     testCase.addScreenShotCurrentStep("No realizo el clic en la carpeta"+ " " +carpeta);
		return false;
		}

	}

	/**
     * Metodo utilizado para abrir EBS y validar la ruta de descargas
     * @author Alan Perez
     * @date 11/Noviembre/2025
     */
	public void AbrirEBS() {
		// TODO Auto-generated method stub
	//Se ingresa a la carpeta descargas 
		// Ruta de la carpeta de descargas
		// Obtener la ruta de la carpeta de descargas del usuario
        String userHome = System.getProperty("user.home");  // Ruta base del usuario (en Windows, sería C:/Users/[usuario])
        String downloadDirectory = userHome + "/Downloads"; // Ruta completa hacia la carpeta Downloads

        // Palabra clave que está en el nombre del archivo
        String keyword = "frmservlet";  // Ajusta esta palabra a la que siempre tendrá el archivo

        // Obtener la lista de archivos en la carpeta de descargas
        File folder = new File(downloadDirectory);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().contains(keyword)) {
                    System.out.println("Archivo encontrado: " + file.getName());

                    // Intentar abrir el archivo con el programa predeterminado del sistema
                    try {
                        if (System.getProperty("os.name").toLowerCase().contains("win")) {
                            // En Windows
                            Runtime.getRuntime().exec("cmd /c start " + file.getAbsolutePath());
                        } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                            // En MacOS
                            Runtime.getRuntime().exec("open " + file.getAbsolutePath());
                        } else {
                            // En Linux
                            Runtime.getRuntime().exec("xdg-open " + file.getAbsolutePath());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;  // Detener el ciclo una vez que encontramos el archivo
                }
            }
        } else {
            System.out.println("No se pudo acceder a la carpeta de descargas.");
           
        }
	}


	/**
     * Metodo utilizado para conservar los archivos de descarga 
     * @author Alan Perez
     * @date 11/Noviembre/2025
     */
	public void ConservarArchivos() throws FindFailed, InterruptedException {
		// TODO Auto-generated method stub
		// Crear un objeto Actions para simular la tecla Tab
        Actions actions = new Actions(driver);

        // El texto que estás buscando en el elemento
        String textoEsperado = "Conservar";

        // Número máximo de tabulaciones para evitar un bucle infinito
        int maxTabulaciones = 50;

        // Repetir el proceso de presionar Tab hasta encontrar el texto
        for (int i = 0; i < maxTabulaciones; i++) {
        	// Hacer tab
      actions.sendKeys(Keys.TAB);
            
         
                u.hardWait(1); // Espera de 1 segundo

            // Obtener el elemento actual
            WebElement elementoActual = driver.switchTo().activeElement();

            // Verificar si el texto esperado está en el elemento
            if (elementoActual.getText().contains(textoEsperado)) {
                System.out.println("¡Elemento encontrado! Haciendo clic...");
                // Hacer clic en el elemento
                actions.sendKeys(Keys.TAB);
                elementoActual.click();
                break; // Termina el bucle
            }
            else {
            	System.out.println("Elemento no encontrado");
            }
        }
         
	
	
	}
	SikuliUtil sk;
	ArrayList<Pattern> patterns;
	File Folder;
	HashMap<String, Pattern> ImgPattern;
	ArrayList<String> ListImage;
	Pattern pathImg;
	
	
	/**
     * Metodo utilizado para utilizar las imagenes cargadas en el framework
     * @author Alan Perez
     * @param File folder donde se encuentran las imagenes
     * @date 11/Noviembre/2025
     * @return imgPattern ruta de la imagen a buscar
     */
	HashMap<String, Pattern> getPatternsImg(File Folder) {
		sk.screen = new Screen();
		if (Folder.exists() && Folder.isDirectory()) {
			File[] files = Folder.listFiles();
			for (File s : files) {
				patterns.add(new Pattern(s.getAbsolutePath()).similar((float) .95));
			}
			for (Pattern p : patterns) {
				String fname = p.getFilename().toString();
				fname = fname.replace("\\", "-");
				fname = fname.replace(".png", "");
				fname = fname.replace(".PNG", "");
				String[] afname = fname.split("-");
				ImgPattern.put(afname[afname.length - 1], p);
			}
		}
			return ImgPattern;
		}
	
	

	/**
	 * Cierra la ventana activa dentro del Applet enviando la combinacion de teclas 'Ctrl' + 'F4'
	 *@return true en caso de cerrar la ventana false si no 
	 * @author Alan Perez
	 * @date 24/Noviembre/2025
	 */
	public boolean closeActiveWindow() {
	    try {
	        sk.screen.keyDown(Key.CTRL);
	        sk.screen.keyDown(Key.F4);
	        sk.screen.keyUp(Key.F4);
	        sk.screen.keyUp(Key.CTRL);

	        testCase.addScreenShotCurrentStep("Cierra la ventana activa");
	        return true;
	    } 
	    catch (Exception e) {
	        System.out.println("Error al cerrar la ventana activa: " + e.getMessage());
	        testCase.addScreenShotCurrentStep("No cierra la ventana actual");
	        return false;
	    }
	}




	/**
     * Metodo para ejecutar la ventana de JAVA 
     * @author Alan Perez
     * @param File folder para buscar la imagen correspondiente
     * @date 11/Noviembre/2025

     */
		public void runApplet(File Folder) throws FindFailed {
			
			System.out.println("Run Applet");
			System.out.println("El folder es: " + Folder);
			
			ImgPattern = getPatternsImg(Folder);
			System.out.println("Folder es:"+ Folder);
			 Screen screen = new Screen();
			 String fol = Folder.toString();
			 
			 
			 System.out.println("El path es el siguiente: ");
		 // Ruta de la imagen que se buscará
	            Pattern pattern = new Pattern(fol);  // Crear el patrón con la imagen
		
	            System.out.println("Esperando a que el patrón aparezca...");
	            Match match = screen.wait(pattern, 10); // Esperar hasta 10 segundos

	            // Paso 4: Comprobar si el patrón fue encontrado
	            if (match != null) {
	            	screen.click(match);
	            }
	            else {
	            	System.out.println("El patrón no se encontró dentro del tiempo de espera.");
	            }
   } 
		
		
		
		
		
		
		/**
	     * Metodo utilizado para hacer clic en cambiar responsabilidad de EBS
	     * @author Alan Perez
	     * @param Folder carpeta donde se encuentra la imagen
	     * @param PngName imagen a buscar para hacer clic 
	     * @date 11/Noviembre/2025
	     * @return true si termina el flujo en caso de false es dado que no encontro la imagen del flujo
	     */
		public boolean CambioResponsabilidad(File folder, String PngName) throws FindFailed {
			
			try {
			HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
		        System.out.println("Patrones cargados del folder: " + folder);

		        // Crear una instancia de Screen de Sikuli
		        Screen screen = new Screen();
		        
		        
		        // Switch responsabilidad btn
		        String switchR = "switch_responsability.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
		        Pattern pattern = ImgPattern.get(switchR);  // Obtén el patrón del HashMap usando el nombre de la imagen
		        Match match = screen.wait(pattern, 10);
		        
		        System.out.println("Patrón encontrado, haciendo clic...");
                screen.click(match);
		        
                
                
                // Seleccion de APX_SuperUsuario
		        String APX = "APX_SuperUsuario.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
		        Pattern pattern2 = ImgPattern.get(PngName);  // Obtén el patrón del HashMap usando el nombre de la imagen
		        Match match2 = screen.wait(pattern2, 15);
		        
		        System.out.println("Patrón encontrado, haciendo clic...");
                screen.click(match2);
                
                
                String Acept = "AceptarEBS.png";
                Pattern pattern5 = ImgPattern.get(Acept);
                Match match5 = screen.wait(pattern5,15);
                System.out.println("Patrón encontrado, haciendo clic...");
                screen.click(match5);
                
                
                //Seleccionar ver request
                String verRequest = "Ver_request.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
		        Pattern pattern3 = ImgPattern.get(verRequest);  // Obtén el patrón del HashMap usando el nombre de la imagen
		        Match match3 = screen.wait(pattern3, 10);
		        
		        System.out.println("Patrón encontrado, haciendo clic...");
                screen.click(match3);
                
                
                //Seleccionar Solicitudes
                String Solicitud = "SolicitudesEBS.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
		        Pattern pattern4 = ImgPattern.get(Solicitud);  // Obtén el patrón del HashMap usando el nombre de la imagen
		        Match match4 = screen.wait(pattern4, 20);
		        
		        System.out.println("Patrón encontrado, haciendo clic...");
                screen.click(match4);
				testCase.addScreenShotCurrentStep("Realiza el cambio de responsabilidad");
                return true;
			}
			catch(Exception e) {
				testCase.addScreenShotCurrentStep("No realiza el cambio de responsabilidad");
			return false;
			}
	
		}
		
		
		
		
		
		
		
		
		/**
	     * Metodo utilizado ejecutar el archivo JAVA 
	     * @author Alan Perez
	     * @param Folder carpeta de archivos para buscar la imagen correspondiente
	     * @date 11/Noviembre/2025
	     * @return true si realiza clic en el objeto para ejecutar el archivo JAVA false si no encuentra el objeto para ejecutar el archivo JAVA
	     */
	public boolean RunApplet2(File folder) throws FindFailed {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        // Crear una instancia de Screen de Sikuli
        Screen screen = new Screen();
        
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "EBS_AceptoRiesgos.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen

        try {
    if (pattern == null) {
        System.out.println("El patrón '" + imageName + "' no existe en el HashMap.");
        return false;
    }

    System.out.println("Esperando a que el patrón aparezca...");

    Match match = screen.wait(pattern, 10);

    if (match != null) {
        System.out.println("Patrón encontrado, haciendo clic...");
        screen.click(match);
		testCase.addScreenShotCurrentStep("Se ejecta el applet");
        u.hardWait(5);
        return true;
    } else {
        System.out.println("El patrón no se encontró dentro del tiempo de espera.");
		testCase.addScreenShotCurrentStep("No se ejecuto el applet");
        return false;
    }

} catch (Exception e) {
    System.out.println("Error al buscar o hacer clic en el patrón: " + e.getMessage());
			testCase.addScreenShotCurrentStep("No se ejecuto el applet");
    return false;
}

        
    }
	
	
	/**
     * Metodo utilizado para hacer clic en el boton de ejecutar de la ventana JAVA
     * @author Alan Perez
     * @param Folder carpeta donde se encuentra la imagen a buscar 
     * @date 11/Noviembre/2025
     * @return true si realiza clic en el boton de ejecutar return false si no encuentra la responsabilidad en la lista 
     */
public boolean EjecutarApplet(File folder) throws FindFailed {

    // Llamar al método que obtiene los patrones de las imágenes dentro del folder
    HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
    System.out.println("Patrones cargados del folder: " + folder);

    Screen screen2 = new Screen();

    String imageName = "Ejecutar_EBS_Comision.png";
    Pattern pattern = ImgPattern.get(imageName);

    try {

        // Validación inicial
        if (pattern == null) {
            System.out.println("El patrón no existe en el HashMap: " + imageName);
            return false;
        }

        System.out.println("Esperando a que el patrón aparezca..." + imageName);

        // Esperar a que aparezca el patrón
        Match match = screen2.wait(pattern, 15);

        if (match != null) {
            System.out.println("Patrón encontrado, haciendo clic..." + imageName);
            screen2.click(match);
			testCase.addScreenShotCurrentStep("Se encontro correctamente el applet");
            return true;
        } else {
            System.out.println("El patrón no se encontró dentro del tiempo de espera.");
			testCase.addScreenShotCurrentStep("No encontro el elemento para ejecutar el applet");
            return false;
        }

    } catch (Exception e) {
        System.out.println("No se encontró el elemento: " + e);
		testCase.addScreenShotCurrentStep("No encontro el elemento para ejecutar el applet");
        return false;
    }
}


	
	/**
     * Metodo utilizado para obtener las imagenes de una carpeta
     * @author Alan Perez
     * @param Folder carpeta donde se encuentran las imagenes del proyecto
     * @date 11/Noviembre/2025
     * @return ImgPattern regresa si la carpeta se encuntra dentro del proyecto y tiene archivos tipo PNG en caso de que no exista se regresa un null 
     */
    public HashMap<String, Pattern> getPatternsImg2(File folder) {
        HashMap<String, Pattern> ImgPattern = new HashMap<>();
        
        // Asegúrate de que la carpeta exista y contenga archivos
        if (folder.exists() && folder.isDirectory()) {
            // Obtener todos los archivos dentro de la carpeta
            File[] files = folder.listFiles();
            
            if (files != null) {
                // Recorrer todos los archivos
                for (File file : files) {
                    // Solo procesar archivos PNG
                    if (file.isFile() && file.getName().endsWith(".png")) {
                        // Crear un patrón para cada archivo de imagen y agregarlo al HashMap
                        Pattern pattern = new Pattern(file.getAbsolutePath()).similar(0.95f);
                        ImgPattern.put(file.getName(), pattern);
                    }
                }
            }
        } else {
            System.out.println("La carpeta no existe o no es un directorio válido.");
            return null; 
        }
        
        return ImgPattern;
    }


    
    /**
     * Metodo utilizado para hacer clic en Ver menu 
     * @author Alan Perez
     * @param Folder carpeta donde se encuentra la imagen para realizar clic
     * @date 11/Noviembre/2025
     * @return true si realiza clic en los objetos correspondientes del flujo EBS ver>Solicitudes return false si no encuentra los objetos ver>solicitudes
     */
    public boolean VerMenu_Solicitudes(File folder) {

    HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
    System.out.println("Patrones cargados del folder: " + folder);

    App oracleApp = App.focus("Oracle Applications - AVEBQA");
    driver.manage().window().maximize();      
    Screen screen = new Screen();

    String imageName1 = "Ver_EBS.png";
    Pattern pattern1 = ImgPattern.get(imageName1);

    // Validación inicial
    if (pattern1 == null) {
        System.out.println("Patrón no encontrado en el HashMap: " + imageName1);
        return false;
    }

    try {
        pattern1 = pattern1.similar(0.5f);

        System.out.println("Esperando a que el patrón aparezca: " + imageName1);
        Match match1 = screen.wait(pattern1, 30);

        if (match1 == null) {
            System.out.println("No se encontró el patrón en pantalla: " + imageName1);
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName1);
            return false;
        }

        // Si aparece, clic
        System.out.println("Patrón encontrado, haciendo clic: " + imageName1);
        screen.click(match1);

        // ========= SEGUNDA IMAGEN =========
        String imageName2 = "SolicitudesEBS.png";
        Pattern pattern2 = ImgPattern.get(imageName2);

        if (pattern2 == null) {
            System.out.println("Patrón no encontrado en el HashMap: " + imageName2);
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName2);
            return false;
        }

        pattern2 = pattern2.similar(0.5f);

        System.out.println("Esperando a que el patrón aparezca: " + imageName2);
        Match match2 = screen.wait(pattern2, 30);

        if (match2 == null) {
            System.out.println("No se encontró el patrón en pantalla: " + imageName2);
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName1);
            return false;
        }

        System.out.println("Patrón encontrado, haciendo clic: " + imageName2);
        screen.click(match2);

        testCase.addScreenShotCurrentStep("Se despliega el menú Ver");
        u.hardWait(5);
        return true;

    } catch (Exception e) {
        System.out.println("Excepción al buscar patrones: " + e.getMessage());
		testCase.addScreenShotCurrentStep("No se encontro ningun patron en pantalla");
        return false;
    }
}


        

    /**
     * Metodo utilizado para ejecutar una nueva solicitud en EBS
     * @author Alan Perez
     * @param folder carpeta donde se encuentra la imagen del boton ejecutar nueva solicitud
     * @date 11/Noviembre/2025
     * @return true si realiza clic en el boton ejecutar nueva solicitud  false si no encuentra el boton 
     */
    public boolean EjecutarNuevaSolicitud(File folder) {
        // Llamar al método que obtiene los patrones de las imágenes dentro del folder
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        // Crear una instancia de Screen de Sikuli
        Screen screen = new Screen();

        // Seleccionar una imagen para buscar en la pantalla
        String imageName = "nuevasolicitud.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        Pattern pattern = ImgPattern.get(imageName);  // Obtener el patrón del HashMap

        try {
            if (pattern != null) {
                // Aplicar configuración de similitud
                pattern = pattern.similar(0.30f);
				u.hardWait(3);
                System.out.println("Esperando a que el patrón aparezca... " + imageName);
                Match match = screen.wait(pattern, 30);  // Esperar hasta 30 segundos

                if (match != null) {
                    System.out.println("Patrón encontrado, haciendo clic... " + imageName);
                    screen.click(match);
                    testCase.addScreenShotCurrentStep("Se da clic en solicitudes");
                    u.hardWait(3);
                    return true;
                } else {
                    System.out.println("No se encontró el patrón: " + imageName);
					testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
                    return false;
                }
            } else {
                System.out.println("El patrón no existe en el HashMap: " + imageName);
				testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Excepción al buscar el patrón: " + e.getMessage());
			testCase.addScreenShotCurrentStep("Excepcion al buscar el patron");
            return false;
        }
    }
	  
    
    /**
     * Metodo utilizado para hacer clic en busqueda de concurrente
     * @author Alan Perez
     * @param Folder carpeta donde se encuentra la imagen de la busqueda concurrente
     * @date 11/Noviembre/2025
     * @return true si realiza clic en el objeto busqueda concurrente false si no encuentra el objeto busqueda concurrente 
     */
	  
    public boolean BusquedaConcurrente(File folder) {
        // Obtener patrones de imagen
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        Screen screen = new Screen();
        String imageName = "puntosbusqueda.png";
        Pattern pattern = ImgPattern.get(imageName);

        try {
            if (pattern != null) {
                // Establecer similitud del 30%
                pattern = pattern.similar(0.50f);
                System.out.println("Esperando a que el patrón aparezca con 30% de similitud... " + imageName);

                Match match = screen.wait(pattern, 30); // espera hasta 30 segundos

                if (match != null) {
                    System.out.println("Patrón encontrado, haciendo clic... " + imageName);
                    screen.doubleClick(match);
					testCase.addScreenShotCurrentStep("Se encontro el patron" + imageName);
                    return true;
                } else {
                    System.out.println("No se encontró el patrón: " + imageName);
					testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
                    return false;
                }
            } else {
                System.out.println("El patrón es nulo: " + imageName);
				testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error en BusquedaConcurrente: " + e.getMessage());
			testCase.addScreenShotCurrentStep("Excepcion");
            return false;
        }
    }
    
    
    
    /**
     * Metodo utilizado para hacer clic en el campo Nombre en EBS
     * @author Alan Perez
     * @param Folder carpeta para obtener las imagenes de la carpeta 
     * @date 11/Noviembre/2025
     * @return true si realiza clic en la imagen campo nombre EBS  false si no encuentra la imagen para realizar clic 
     */
    public boolean CampoNombreEBS(File folder) {
        // Obtener patrones de imagen
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        Screen screen = new Screen();
        String imageName = "Nombre_EBS.png";
        Pattern pattern = ImgPattern.get(imageName);

        try {
            if (pattern != null) {
                // Establecer similitud del 30%
                pattern = pattern.similar(0.50f);
                System.out.println("Esperando a que el patrón aparezca con 30% de similitud... " + imageName);

                Match match = screen.wait(pattern, 30); // espera hasta 30 segundos

                if (match != null) {
                    System.out.println("Patrón encontrado, haciendo clic... " + imageName);
                    screen.click(match);
                    testCase.addScreenShotCurrentStep("clic en campo nombre EBS");
                    return true;
                } else {
                    System.out.println("No se encontró el patrón: " + imageName);
					testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
                    return false;
                }
            } else {
                System.out.println("El patrón es nulo: " + imageName);
				testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error en BusquedaConcurrente: " + e.getMessage());
			testCase.addScreenShotCurrentStep("Excepcion");
            return false;
        }
    }
    
    
    String InicioConcu ="XXFC";  
    
    /**
     * Metodo utilizado para teclear el string XXFC que corresponde al inicio de todos los concurrentes
     * @author Alan Perez
     * @date 11/Noviembre/2025
     * @return true si realiza el tecleo de XXFC en caso false regresa un mensaje de error 
     */
    
    public boolean TypeXXFC() {

    try {
        Robot robot = new Robot();

        // Escribir el texto carácter por carácter
        for (char c : InicioConcu.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                System.out.println("Carácter no válido para tecla: " + c);
                continue;
            }

            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
        }

        // Presionar Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
testCase.addScreenShotCurrentStep("Se escribio de manera correcta XXFC para buscar concurrente");
        u.hardWait(2);

        return true;

    } catch (Exception e) {
        System.out.println("Error en TypeXXFC: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Excepcion en TypeXXFC");
        e.printStackTrace();
        return false;
    }
}

 
    
    /**
     * Metodo utilizado para hacer clic textbox buscar y enviar el nombre del concurrente
     * @author Alan Perez
     * @param folder carpeta para buscar la imagen y poder realizar clic en el objeto 
     * @param concurrente nombre del concurrente a escribir y buscar
     * @date 11/Noviembre/2025
     * @return true si realiza clic en el objeto para escribir el concurrente correspondiente en EBS false si no encuentra el objeto
     */
    public boolean typeInEBS(File folder, String concurrente) throws AWTException {
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        Screen screen = new Screen();
        String imageName = "TextBox_Buscar_EBS.png";
        Pattern pattern = ImgPattern.get(imageName);
        Robot robot = new Robot();

        try {
            if (pattern != null) {
                // Establecer similitud del 30%
                pattern = pattern.similar(0.30f);
                System.out.println("Esperando a que el patrón aparezca con 30% de similitud... " + imageName);

                Match match = screen.wait(pattern, 30);

                if (match != null) {
                    System.out.println("Patrón encontrado, haciendo clic... " + imageName);
                    screen.click(match);

                    // Escribir el texto carácter por carácter
                    for (char c : concurrente.toCharArray()) {
                        int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                        if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                            System.out.println("Carácter no válido para tecla: " + c);
                            continue;
                        }
                        robot.keyPress(keyCode);
                        robot.keyRelease(keyCode);
                    }
                   u.hardWait(2);
                   testCase.addScreenShotCurrentStep("Se escribe en el cambo el nombre del concurrente");
                    return true;
                } else {
                    System.out.println("No se encontró el patrón: " + imageName);
					testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
                    return false;
                }
            } else {
                System.out.println("El patrón es nulo: " + imageName);
				testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error en typeInEBS: " + e.getMessage());
			testCase.addScreenShotCurrentStep("Excepcion en escribir XXFC ");
            return false;
        }
    }

    /**
     * Metodo utilizado buscar el concurrente 
     * @author Alan Perez
     * @param folder carpeta para buscar la imagen y poder realizar clic en el objeto 
     * @param concurrente nombre del concurrente a escribir y buscar
     * @date 11/Noviembre/2025
     * @return true si realiza clic en el objeto para escribir el concurrente correspondiente en EBS false si no encuentra el objeto 
     */
	public boolean BuscarConcurrente(int tabCount) throws AWTException {
		
	        try {
	            Robot robot = new Robot();
	            
	            // Opcional: pequeña pausa antes de empezar
	            u.hardWait(1);

	            // Enviar tabulaciones
	            for (int i = 0; i < tabCount; i++) {
	                robot.keyPress(KeyEvent.VK_TAB);
	                robot.keyRelease(KeyEvent.VK_TAB);
	                u.hardWait(1); // pequeña pausa entre tabs
	            }

	            // Enviar Enter
	            robot.keyPress(KeyEvent.VK_ENTER);
	            robot.keyRelease(KeyEvent.VK_ENTER);
	            testCase.addScreenShotCurrentStep("Se da clic en boton buscar");
	            return true;

	        } catch (AWTException e) {
	            e.printStackTrace();  
				testCase.addScreenShotCurrentStep("Se tuvo una excepcion al realizar clic");
	        }  
		testCase.addScreenShotCurrentStep("No realiza clic en el boton buscar");
			return false;
	    }
	

	/**
     * Metodo utilizado para ejecutar la solicitud 
     * @author Alan Perez
     * @param folder carpeta para buscar la imagen y poder realizar clic en el objeto 
     * @date 11/Noviembre/2025
     * @return true si realiza clic en el objeto para ejecutar la solicitud false si no encuentra el objeto
     */
	public boolean EjecutarSolicitud(File folder) {

    // Cargar patrones
    HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
    System.out.println("Patrones cargados del folder: " + folder);

    Screen screen = new Screen();
    Robot robot;

    try {
        robot = new Robot();
    } catch (AWTException e) {
        System.out.println("Error al crear Robot: " + e.getMessage());
        return false;
    }

    String imageName = "EjecutarSolicitud_EBS.png";
    Pattern pattern = ImgPattern.get(imageName);

    // Validación inicial
    if (pattern == null) {
        System.out.println("Patrón no encontrado en el HashMap: " + imageName);
		testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
        return false;
    }

    try {

        System.out.println("Esperando a que el patrón aparezca: " + imageName);
        Match match = screen.wait(pattern, 30);
        if (match == null) {
            System.out.println("No se encontró el patrón en pantalla: " + imageName);
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
            return false;
        }

        // Click en el patrón
        System.out.println("Patrón encontrado, haciendo clic: " + imageName);
        screen.click(match);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        u.hardWait(1);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        u.hardWait(3);
		testCase.addScreenShotCurrentStep("Se encuentra el patron en pantalla"+ imageName);
        return true;

    } catch (Exception e) {
        System.out.println("Error en EjecutarSolicitud(): " + e.getMessage());
        return false;
    }
}


	/**
     * Metodo utilizado para encontrar una solicitud en EBS 
     * @author Alan Perez
     * @param folder carpeta para buscar en pantalla la imagen del boton  
     * @date 11/Noviembre/2025
     * @return true si realiza clic en el boton encontrar solicitud en EBS false si no encuentra el objeto
     */
	public boolean EncontrarSolicitud(File folder) {

    // Cargar patrones
    HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
    System.out.println("Patrones cargados del folder: " + folder);

    Screen screen = new Screen();
    String imageName = "EncontrarSolicitud_EBS.png";
    Pattern pattern = ImgPattern.get(imageName);

    // Validación inicial
    if (pattern == null) {
        System.out.println("Patrón no encontrado en el HashMap: " + imageName);
        return false;
    }

    // Ajustar similitud
    pattern = pattern.similar(0.30f);

    try {
        System.out.println("Esperando a que el patrón aparezca: " + imageName);
        Match match = screen.wait(pattern, 30);

        if (match == null) {
            System.out.println("No se encontró el patrón en pantalla: " + imageName);
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
            return false;
        }

        // Acción cuando se encuentra
        System.out.println("Patrón encontrado, haciendo clic: " + imageName);
        screen.click(match);
        u.hardWait(7);
        testCase.addScreenShotCurrentStep("Se encuentra la solicitud correspondiente");
        return true;

    } catch (Exception e) {
        System.out.println("Error en EncontrarSolicitud(): " + e.getMessage());
		testCase.addScreenShotCurrentStep("Se obtuvo una excepcional realizar clic en encontrar solicitud");
        return false;
    }
}


	/**
     * Metodo utilizado hacer cierta cantidad de tabs y escribir 
     * @author Alan Perez
     * @param numberOfTabs numero de tabs a ejecutar 
     * @param concurrente nombre del concurrente a escribir y buscar
     * @date 11/Noviembre/2025
     * @return true si realiza clic en el objeto para escribir el concurrente correspondiente en EBS false si no encuentra el objeto 
     */
	public void TabAndType( int numberOfTabs, String text) throws AWTException, InterruptedException {
		
		Robot robot = new Robot();
        
        // Enviar las tabulaciones (TAB)
        for (int i = 0; i < numberOfTabs; i++) {
            robot.keyPress(KeyEvent.VK_TAB);  // Simula la tecla TAB
            robot.keyRelease(KeyEvent.VK_TAB); // Libera la tecla TAB
            u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido    
        }
        
     // Escribe el texto
        for (char c : text.toCharArray()) {
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));  // Simula la tecla de cada carácter
            robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c)); // Libera la tecla
            u.hardWait(1); // Pausa opcional entre caracteres
        }
      u.hardWait(5);
	}
	
	
	
	/**
     * Metodo utilizado para realizar cierta cantidad de enter
     * @author Alan Perez
     * @param numberofenter numero de enter 
     * @param concurrente nombre del concurrente a escribir y buscar
     * @date 11/Noviembre/2025
     */
	public void Enter1(int numberOfenter) throws AWTException, InterruptedException{
		Robot robot = new Robot();
		 for (int i = 0; i < numberOfenter; i++) {
             robot.keyPress(KeyEvent.VK_ENTER);  // Simula la tecla TAB
             robot.keyRelease(KeyEvent.VK_ENTER); // Libera la tecla TAB
             u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
         }
	}

	

	/**
     * Metodo utilizado para realizar tab 
     * @author Alan Perez
     * @param numberOfTab numero de tabs a realizar 
     * @date 11/Noviembre/2025
     */
	public void tab2(int numberOfTab) throws AWTException, InterruptedException{
		Robot robot = new Robot();
		 for (int i = 0; i < numberOfTab; i++) {
             robot.keyPress(KeyEvent.VK_TAB);  // Simula la tecla TAB
             robot.keyRelease(KeyEvent.VK_TAB); // Libera la tecla TAB
             u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
         }
	}
	
	

	/**
     * Metodo utilizado para realizar tecla hacia abajo 
     * @author Alan Perez
     * @param  numberOfdown cantidad de veces que se presionara la tecla 
     * @date 11/Noviembre/2025
     */
	public void KeyDown(int numberOfdown) throws AWTException, InterruptedException{
		Robot robot = new Robot();
		 for (int i = 0; i < numberOfdown; i++) {
             robot.keyPress(KeyEvent.VK_DOWN);  // Simula la tecla TAB
             robot.keyRelease(KeyEvent.VK_DOWN); // Libera la tecla TAB
             u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
         }
	}
	
	
	

	/**
     * Metodo utilizado para dar enter en los parametros de los concurrentes EBS 
     * @author Alan Perez
     * @param NumberOfEnter cantidad de enters a realizar 
     * @date 11/Noviembre/2025
     * @return true si realiza el enter correspondiente false si no realiza el enter
     */
	public boolean EnterParam(int NumberOfEnter) {
    try {
        Robot robot = new Robot();

        if (robot != null) {
            for (int i = 0; i < NumberOfEnter; i++) {
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                u.hardWait(1);
            }
			testCase.addScreenShotCurrentStep("Se da enter al parametro");
            return true;
        }

    } catch (AWTException e) {
        e.printStackTrace();
		testCase.addScreenShotCurrentStep("No se da enter al parametro");
        return false;
    }
	return false;
}

	
	

	/**
     * Metodo utilizado para realizar un tab y despues cierta cantidad de enters
     * @author Alan Perez
     * @param numberOfEnter cantidad de enters a dar  
     * @date 11/Noviembre/2025
     */
	public void Enter(int numberOfEnter) throws AWTException, InterruptedException {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		 robot.keyPress(KeyEvent.VK_TAB);  // Simula la tecla TAB
         robot.keyRelease(KeyEvent.VK_TAB); // Libera la tecla TAB	
      // Enviar las tabulaciones (TAB)
         for (int i = 0; i < numberOfEnter; i++) {
             robot.keyPress(KeyEvent.VK_ENTER);  // Simula la tecla TAB
             robot.keyRelease(KeyEvent.VK_ENTER); // Libera la tecla TAB
            u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
         }
	}


	/**
     * Metodo utilizado para hacer clic en el buscador de concurrentes 
     * @author Alan Perez
     * @param folder carpeta para buscar la imagen y poder realizar clic en el buscador de concurrentes
     * @date 11/Noviembre/2025
     * @return true si realiza clic en el objeto para escribir el concurrente correspondiente en EBS false si no encuentra el objeto
     */
	public boolean ConcurrenteName(File folder) {

    // Cargar patrones del folder
    HashMap<String, Pattern> imgPattern = getPatternsImg2(folder);
    System.out.println("Patrones cargados del folder: " + folder);

    Screen screen = new Screen();
    String imageName = "nombreclick.png";
    Pattern pattern = imgPattern.get(imageName);

    try {
        if (pattern == null) {
            System.out.println("No existe el patrón en el HashMap: " + imageName);
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
            return false;
        }

        // Ajustar similitud opcional
        pattern = pattern.similar(0.35f);
        System.out.println("Buscando patrón en pantalla: " + imageName);

        // Esperar hasta 30 segundos
        Match match = screen.wait(pattern, 30);

        if (match != null) {
            System.out.println("Patrón encontrado, haciendo clic: " + imageName);
            screen.click(match);
			testCase.addScreenShotCurrentStep("Se encontro el patron en pantalla"+ imageName);
            u.hardWait(5);
            return true;
        }

        System.out.println("El patrón no apareció en pantalla: " + imageName);
		testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
        return false;

    } catch (Exception e) {
        System.out.println("Error en ConcurrenteName: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Se obtuvo una excepcion en el nombre del concurrente");
        return false;
    }
}




	/**
     * Metodo utilizado para escribir el parametro servicio en EBS 
     * @author Alan Perez
     * @param folder carpeta para buscar la imagen y poder realizar clic en el objeto 
     * @date 11/Noviembre/2025
     * @return true si realiza clic en el objeto para escribir el concurrente correspondiente en EBS false si no encuentra el objeto
     */
public boolean ParametroServicio(File folder) {

    HashMap<String, Pattern> imgPattern = getPatternsImg2(folder);
    System.out.println("Patrones cargados del folder: " + folder);

    Screen screen = new Screen();
    String imageName = "Servicio_EBS.png";
    Pattern pattern = imgPattern.get(imageName);

    try {

        // Validar que el patrón exista
        if (pattern == null) {
            System.out.println("El patrón no existe en el HashMap: " + imageName);
			testCase.addScreenShotCurrentStep("No se encontro el patron en hashMarp"+ imageName);
            return false;
        }

        System.out.println("Buscando patrón en pantalla: " + imageName);

        // Espera hasta 30 segundos
        Match match = screen.wait(pattern.similar(0.35f), 30);

        if (match == null) {
            System.out.println("No se encontró el patrón en pantalla: " + imageName);
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
            return false;
        }

        // Click en la imagen
        System.out.println("Patrón encontrado, haciendo clic: " + imageName);
        screen.click(match);
		testCase.addScreenShotCurrentStep("Se encuentra el patron correctamente"+ imageName);
        u.hardWait(5);

        // Enviar TAB 2 veces
        Robot robot = new Robot();
        for (int i = 0; i < 2; i++) {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            u.hardWait(1);
        }
        testCase.addScreenShotCurrentStep("Se realizaron 2 tab");
        return true;

    } catch (Exception e) {
        System.out.println("Error en ParametroServicio: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Se obtuvo un error en el parametro servicio");
        return false;
    }
}

	
	

	/**
     * Metodo utilizado para escribir algun texto 
     * @author Alan Perez
     * @param texto string a escribir 
     * @date 11/Noviembre/2025
     */
	public void Escribir(String texto) throws InterruptedException, AWTException {
		// Escribe el texto
		Robot robot = new Robot();
        for (char c : texto.toCharArray()) {
            robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(c));  // Simula la tecla de cada carácter
            robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(c)); // Libera la tecla
            u.hardWait(1); // Pausa opcional entre caracteres
        }
        
      u.hardWait(5);
	}
	
	

	/**
     * Metodo utilizado para enviar el parametro servicio 
     * @author Alan Perez
     * @param String servicio servicio a escribir en EBS
     * @date 11/Noviembre/2025
     * @return true si realiza el envio del servicio false si no envia el servicio
     */
public boolean EnvioParametro(String servicio) {
    try {

        if (servicio == null) {
            System.out.println("El parámetro 'servicio' es nulo");
			testCase.addScreenShotCurrentStep("El parametro servicio es nulo");
            return false;
        }

        // Escribir el texto
        Escribir(servicio);

        // Simular Enter
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
      testCase.addScreenShotCurrentStep("Se ingresa el servicio como parametro y se da un enter"+" "+servicio);
        return true;

    } catch (Exception e) {
        System.out.println("Error en EnvioParametro: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Se tuvo un error al enviar el parametro");
        return false;
    }
}

	  
	
	
	
	/**
     * Metodo utilizado para realizar un enter 
     * @author Alan Perez
     * @param number cantidad de enters a dar 
     * @date 11/Noviembre/2025
     */
	public void Enter3(int number) throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);  
        robot.keyRelease(KeyEvent.VK_ENTER);
	}
	  
	
	/**
     * Metodo utilizado para escribir el parametro informe 
     * @author Alan Perez
     * @param informe string a escribir en el parametro
     * @date 11/Noviembre/2025
     */
	  public void parametrosInforme(String Informe) throws AWTException, InterruptedException {
		 Escribir(Informe);
	  }

	  
		/**
	     * Metodo utilizado para escribir el parametro fecha pago desde
	     * @author Alan Perez
	     * @param fecha string a escribir en el parametro
	     * @date 11/Noviembre/2025
	     */
	  public void FechaPagoDesde(String fecha)throws AWTException, InterruptedException{
		  Escribir(fecha);
	  }
	  

	  /**
	     * Metodo utilizado para escribir el parametro fecha pago hasta
	     * @author Alan Perez
	     * @param fecha string a escribir en el parametro
	     * @date 11/Noviembre/2025
	     */
	  public void FechaPagoHasta(String fecha)throws AWTException, InterruptedException{
		  Escribir(fecha);
	  }
	  
	  
	  /**
	     * Metodo utilizado para escribir el parametro fecha pago 
	     * @author Alan Perez
	     * @param fecha string a escribir en el parametro
	     * @date 11/Noviembre/2025
	     */
	  public void FechaPago(String fecha)throws AWTException, InterruptedException {
		  Escribir(fecha);
	  }
	  
	  /**
	     * Metodo utilizado para escribir el parametro incluir fechas
	     * @author Alan Perez
	     * @param YesNo escribir una de las 2 opciones
	     * @date 11/Noviembre/2025
	     */
	  public void IncluirFacturas(String YesNo)throws AWTException, InterruptedException{
		  Escribir(YesNo);
	  }
	  /**
	     * Metodo utilizado para escribir yes/no en el parametro opcion resumen 
	     * @author Alan Perez
	     * @param string yes/no opcion para habilitar la opcion
	     * @date 11/Noviembre/2025
	     */
	  public void OpcionResumen(String YesNo)throws AWTException, InterruptedException{
		  Escribir(YesNo);
	  }
	  
	  
	  /**
	     * Metodo utilizado para escribir yes/no en el parametro total proveedor
	     * @author Alan Perez
	     * @param string yes/no opcion para habilitar la opcion
	     * @date 11/Noviembre/2025
	     */
	  public void TotalProveedor(String YesNo)throws AWTException, InterruptedException{
		  Escribir(YesNo);
	  }
	  
	  /**
	     * Metodo utilizado para escribir la plaza a buscar en EBS
	     * @author Alan Perez
	     * @param string plaza se escribe la plaza correspondiente
	     * @date 11/Noviembre/2025
	     */
	  public void Plaza(String plaza) throws InterruptedException, AWTException {
		  Escribir(plaza);
	  }
	  
	  
	  
	  /**
	     * Metodo utilizado para escribir la compania 
	     * @author Alan Perez
	     * @param string compania se escribe la compania correspondiente
	     * @date 11/Noviembre/2025
	     */
	  public void Compania(String com) throws InterruptedException, AWTException {
		  Escribir(com);
	  }
	  
	  
	  /**
	     * Metodo utilizado para hacer clic en compania 99 
	     * @author Alan Perez
	     * @param folder buscar una imagen de un objeto 
	     * @date 11/Noviembre/2025
	     */
	  //parametros
	  public void Compania99Clic(File folder)  throws InterruptedException, AWTException, FindFailed{
		// TODO Auto-generated method stub
			// Llamar al método que obtiene los patrones de las imágenes dentro del folder
	        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
	        System.out.println("Patrones cargados del folder: " + folder);

	        // Crear una instancia de Screen de Sikuli
	        Screen screen = new Screen();
	       
	        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
	        String imageName = "Compañia99_EBS.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
	        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
	        
	        
	        // Verificar si el patrón se encuentra
	        if (pattern != null) {
	            System.out.println("Esperando a que el patrón aparezca..."+imageName);
	            
	            // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
	            Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
	         
	                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
	                screen.doubleClick(match);
				testCase.addScreenShotCurrentStep("Se encuentra correctamente el patron"+ imageName);
                 u.hardWait(5);
             
	        }
	        else {
	        	 System.out.println("Patrón no encontrado");
				testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
	        }
	  }
	  
	  
	  
	  
	  /**
	     * Metodo utilizado para realizar tab 
	     * @author Alan Perez
	     * @param numberOfT numero de tabs a realizar
	     * @date 11/Noviembre/2025
	     */
	public void Enter2(int numberOfT) throws AWTException, InterruptedException {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		 for (int i = 0; i < numberOfT; i++) {
            robot.keyPress(KeyEvent.VK_TAB);  // Simula la tecla TAB
            robot.keyRelease(KeyEvent.VK_TAB); // Libera la tecla TAB
            u.hardWait(1);// Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
        }
	}



	 /**
     * Metodo utilizado para dar clic en si una vez se despliegan las opciones
     * @author Alan Perez
     * @param folder se encuentra la imagen a hacer clic correspondiente
     * @date 11/Noviembre/2025
     */
	public void ParamSi(File folder) throws FindFailed {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        // Crear una instancia de Screen de Sikuli
        Screen screen = new Screen();
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "YesParam_EBS.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        
        System.out.println(pattern);
        // Verificar si el patrón se encuentra
        if (pattern != null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);
            
            // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
            Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
          
            System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
			testCase.addScreenShotCurrentStep("Se encontro el patron en pantalla"+ imageName);
            screen.click(match);
			
        }
        else {
        	System.out.println("No se encontro la imagen");
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
        	
        	}

	}

	
	
	 /**
     * Metodo utilizado para realizar clic en fieldvencimiento
     * @author Alan Perez
     * @param folder se encuentra la imagen a hacer clic correspondiente
     * @date 11/Noviembre/2025
     */
	public void FieldVencimiento(File folder) throws FindFailed {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        // Crear una instancia de Screen de Sikuli
        Screen screen = new Screen();
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "PagarVencimiento_EBS.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        
        
        // Verificar si el patrón se encuentra
        if (pattern != null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);
            // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
            Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
			testCase.addScreenShotCurrentStep("Se encontro el patron en pantalla"+ imageName);
                screen.click(match);
        }
        else {
        	System.out.println("No se encontro la imagen");
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
        	}

	}
	
	
	
	
	 /**
     * Metodo utilizado para realizar clic en boton aceptar
     * @author Alan Perez
     * @param folder se encuentra la imagen a hacer clic correspondiente
     * @date 11/Noviembre/2025
     */
	public void btnAceptar(File folder) throws FindFailed {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        // Crear una instancia de Screen de Sikuli
        Screen screen = new Screen();
       
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "AceptarEBS.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        
        
        // Verificar si el patrón se encuentra
        if (pattern != null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);
            // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
            Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
            System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
			testCase.addScreenShotCurrentStep("Se encontro el patron en pantalla"+ imageName);
            screen.click(match);
        }
        else {
        	System.out.println("No se encontro la imagen");
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
        	}
        
	}
	
	
	
	 /**
     * Metodo utilizado para hacer uso de la tecla keydown
     * @author Alan Perez
     * @date 11/Noviembre/2025
     */
	public void keydown() throws AWTException, InterruptedException {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		u.hardWait(5);
		// Simular la tecla de flecha hacia abajo (KeyEvent.VK_DOWN)
        robot.keyPress(KeyEvent.VK_DOWN);  // Simula la presionar la tecla de flecha hacia abajo
        robot.keyRelease(KeyEvent.VK_DOWN); 
           u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
       
	}
	

	
	 /**
     * Metodo utilizado para realizar enter
     * @author Alan Perez
     * @date 11/Noviembre/2025
     */
	public void keyEnterCD() throws AWTException, InterruptedException {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		u.hardWait(5);
		// Simular la tecla de flecha hacia abajo (KeyEvent.VK_DOWN)
        robot.keyPress(KeyEvent.VK_ENTER);  
        robot.keyRelease(KeyEvent.VK_ENTER); 
          u.hardWait(1); // Opcional: pequeña pausa para evitar que el Robot sea demasiado rápido
	}



	 /**
     * Metodo utilizado para escribir la cuenta corriente
     * @author Alan Perez
     * @param string cuenta a escribir 
     * @date 11/Noviembre/2025
     */
	public void CuentaCorriente(String cuenta) throws InterruptedException, AWTException {
		// TODO Auto-generated method stub
		Escribir(cuenta);
	}


	 /**
     * Metodo utilizado para realizar clic en tipo reporte
     * @author Alan Perez
     * @param folder donde se encuentra la imagen a hacer clic correspondiente
     * @date 11/Noviembre/2025
     */
	public void ClicTipoReporte(File folder) throws FindFailed, InterruptedException {
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        // Crear una instancia de Screen de Sikuli
        Screen screen = new Screen();
       u.hardWait(7);
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "ReporteA_EBS.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        
        
        // Verificar si el patrón se encuentra
        if (pattern != null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);
            
            // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
            Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
          
            System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
			testCase.addScreenShotCurrentStep("Se encontro el patron en pantalla"+ imageName);
            screen.doubleClick(match);
        }
        else {
        	System.out.println("No se encontro la imagen");
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
        
        	}
		
	}

	 /**
     * Metodo utilizado para escribir en el campo tipo salida
     * @author Alan Perez
     * @param String tipo salida a escribir
     * @date 11/Noviembre/2025
     */
	public void TipoDeSalida(String string) throws InterruptedException, AWTException {
		// TODO Auto-generated method stub
		u.hardWait(7);
		Escribir(string);
	}


	 /**
     * Metodo utilizado para validar los output de los concurrentes
     * @author Alan Perez
     * @param folder se encuentra la imagen a hacer clic correspondiente
     * @date 11/Noviembre/2025
     * @return true si realiza el clic correspondiente para validar el output false si no realiza el clic correspondiente 
     */
	public boolean ValidarOutput(File folder) {

    try {
        // Cargar patrones
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        Screen screen = new Screen();
        u.hardWait(5);

        // Nombre del patrón a buscar
        String imageName = "VisualizarOutput_EBS.png";
        Pattern pattern = ImgPattern.get(imageName);

        if (pattern == null) {
            System.out.println("Patrón no encontrado en el HashMap: " + imageName);
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
            return false;
        }

        // Esperar coincidencia
        System.out.println("Esperando a que el patrón aparezca..." + imageName);
        Match match = screen.wait(pattern, 30);

        if (match != null) {
            System.out.println("Patrón encontrado, haciendo clic..." + imageName);
			testCase.addScreenShotCurrentStep("Se encontro el patron en pantalla"+ imageName);
            screen.click(match);
            return true;
        } else {
            System.out.println("No se encontró el patrón en pantalla: " + imageName);
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
            return false;
        }

    } catch (Exception e) {
        System.out.println("Error en ValidarOutput: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Se obtiene una excepcion al dar clic en la imagen");
        return false;
    }
}



	 /**
     * Metodo utilizado para refrescar la data de los concurrentes ejecutados
     * @author Alan Perez
     * @param folder se encuentra la imagen a hacer clic correspondiente
     * @date 11/Noviembre/2025
     * @return true si realiza el clic correspondiente para refrescar la data false si no realiza el clic correspondiente
     */
	public boolean RefreshData(File folder) throws InterruptedException, FindFailed {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        // Crear una instancia de Screen de Sikuli
        Screen screen = new Screen();
  		u.hardWait(8);
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "RefrescarDatos_EBS.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        
        
        // Verificar si el patrón se encuentra
        if (pattern != null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);
            
            // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
            Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
       		u.hardWait(7);
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                screen.click(match);
			testCase.addScreenShotCurrentStep("Se encontro el patron en pantalla"+ imageName);
                return true;
        }
        else {
        	System.out.println("No se encontro la imagen");
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
        	return false;
        	}
	}


	 /**
     * Metodo utilizado para abrir el archivo excel 
     * @author Alan Perez
     * @param folder se encuentra la imagen a hacer clic correspondiente
     * @date 11/Noviembre/2025
     * @return true si realiza el clic correspondiente para abrir el excel en EBS false si no realiza el clic correspondiente
     */
	public boolean AbrirXLS(File folder) throws InterruptedException, FindFailed {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        // Crear una instancia de Screen de Sikuli
        Screen screen = new Screen();
       u.hardWait(5);
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "AbrirReporteFEMCO_CARTAS.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        
        
        // Verificar si el patrón se encuentra
        if (pattern != null) {
            System.out.println("Esperando a que el patrón aparezca..."+imageName);
            
            // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
            Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
                screen.click(match);
			testCase.addScreenShotCurrentStep("Se encontro el patron en pantalla"+ imageName);
                return true;
        }
        else {
        System.out.println("No se encontro la imagen");
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
        return false;	
        }
	}

	 /**
     * Metodo utilizado para hacer clic en el parametro distribucion bus 
     * @author Alan Perez
     * @param folder se encuentra la imagen a hacer clic correspondiente
     * @date 11/Noviembre/2025
     * @return true si realiza el clic correspondiente para ingresar el parametro false si no realiza el clic correspondiente 
     */
	public void DistribucionBus(File folder) throws InterruptedException, FindFailed {
		// TODO Auto-generated method stub
		// Llamar al método que obtiene los patrones de las imágenes dentro del folder
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        // Crear una instancia de Screen de Sikuli
        Screen screen = new Screen();
      	u.hardWait(5);
        // Seleccionar una imagen para buscar en la pantalla (esto depende de tus patrones)
        String imageName = "Buscar_EBSDistribucion.png";  // Asegúrate de que este nombre esté disponible en ImgPattern
        Pattern pattern = ImgPattern.get(imageName);  // Obtén el patrón del HashMap usando el nombre de la imagen
        
        
        // Verificar si el patrón se encuentra
        if (pattern != null) {
            System.out.println("Esperando a que el patrón aparezca..."+ imageName);
            
            // Esperar hasta que el patrón aparezca en la pantalla durante 10 segundos
            Match match = screen.wait(pattern, 30);  // Esperar hasta 10 segundos
          
                System.out.println("Patrón encontrado, haciendo clic..."+ imageName);
			testCase.addScreenShotCurrentStep("Se encontro el patron en pantalla"+ imageName);
                screen.click(match);
        }
        else {
        	System.out.println("No se encontro la imagen");
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
        	}
	}

	
	
	
	 /**
     * Metodo utilizado descargar el archivo de chrome
     * @author Alan Perez
     * @param folder se encuentra la imagen a hacer clic correspondiente
     * @date 11/Noviembre/2025
     * @return true si realiza el clic correspondiente para descargar el archivo chrome false si no realiza el clic correspondiente 
     */
public boolean BtnDescarga(File folder) {

    try {
        // Cargar patrones
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        Screen screen = new Screen();
        u.hardWait(5);

        // Nombre del patrón a buscar
        String imageName = "DescargaArchivoChrome.png";
        Pattern pattern = ImgPattern.get(imageName);

        // Validar si existe el patrón
        if (pattern == null) {
            System.out.println("Patrón no encontrado en el HashMap: " + imageName);
			testCase.addScreenShotCurrentStep("No se encontro el patron en HashMap"+ imageName);
            return false;
        }

        // Esperar coincidencia
        System.out.println("Esperando a que el patrón aparezca..." + imageName);
        Match match = screen.wait(pattern, 30);

        if (match != null) {
            System.out.println("Patrón encontrado, haciendo clic..." + imageName);
            screen.click(match);
			testCase.addScreenShotCurrentStep("Se encontro el patron en pantalla"+ imageName);
            return true;
        } else {
            System.out.println("No se encontró el patrón en pantalla: " + imageName);
			testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
            return false;
        }

    } catch (Exception e) {
        System.out.println("Error en BtnDescarga: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Se obtiene excepcion al dar clic en BtnDescarga");
        return false;
    }
}


	
	 /**
     * Metodo utilizado para abrir un archivo 
     * @author Alan Perez
     * @date 11/Noviembre/2025
     * @return true si realiza el clic correspondiente para abrir el archivo false si no realiza el clic correspondiente 
     */
public boolean AbrirArchivo() {
    try {
        Robot robot = new Robot();

        // Espera inicial
        u.hardWait(5);

        // Clic izquierdo
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

        // CTRL + J
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_J);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        u.hardWait(5);

        // Presionar TAB 4 veces
        for (int i = 0; i < 4; i++) {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            u.hardWait(1);
        }

        u.hardWait(1);

        // ENTER
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        u.hardWait(1);

        // Flecha abajo
        robot.keyPress(KeyEvent.VK_DOWN);
        u.hardWait(1);

        // ENTER
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        u.hardWait(1);

        // ENTER nuevamente
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        u.hardWait(1);

        // Flecha abajo
        robot.keyPress(KeyEvent.VK_DOWN);
        u.hardWait(1);

        // ENTER final
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        u.hardWait(5);
       testCase.addScreenShotCurrentStep("Se abre el archivo correctamente");
        return true;

    } catch (Exception e) {
        System.out.println("Error en AbrirArchivo: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Error al abrir el archivo");
        return false;
    }
}

	
	
	
	
	 /**
     * Metodo utilizado para ejecutar el archivo JAVA
     * @author Alan Perez
     * @date 11/Noviembre/2025
     * @return true si termina el flujo correspondiente
     */
public boolean EjecutarArchivoJava() {

    try {
        Robot robot = new Robot();
        // Espera inicial
        u.hardWait(5);
        // Presionar TAB 11 veces
        for (int i = 0; i < 11; i++) {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            u.hardWait(1);
        }

        // Presionar ENTER
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        u.hardWait(3);
		testCase.addScreenShotCurrentStep("Se ejecuta correctamente el archivo java");
        return true;

    } catch (Exception e) {
        System.out.println("Error en EjecutarArchivoJava: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Error por la excepcion de ejecutar el archivo java");
        return false;
    }
}

	

	/**
		 * Metodo para manejar la ventana de advertencia que aparece al abrir un applet
		 * @author Alan Perez 
		 * @return true da clic en aceptar riesgos y ejecutar el app java false en caso que no realice el clic 
	  	 * @date 20/11/2025
		 */
		public boolean runApplet3(File folder) throws Exception{
			System.out.println("Run Applet");
			Pattern aceptoRiesgosYDeseoEjecutar = new Pattern(folder +"\\aceptoRiesgosYDeseoEjecutar.png").similar((float).70);
			Pattern ejecutarApplet = new Pattern(folder +"\\ejecutarApplet.png").similar((float).70);
	 
			try {
				//se da click primero a la advertencia de aceptar el riesgo
				if (sk.screen.exists(aceptoRiesgosYDeseoEjecutar,20)!=null) {
					sk.screen.click(aceptoRiesgosYDeseoEjecutar);
				}
				//se da click a Ejecutar
				if (sk.screen.exists(ejecutarApplet,20)!=null) {
					sk.screen.click(ejecutarApplet);
				}
			} catch (FindFailed e) {
				testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
				throw new Exception("Error: " + e.getMessage());
			}
			u.hardWait(10);
			testCase.addScreenShotCurrentStep("Se ejecuta correctamente el applet");
			return true;
			
		}
	
	
	
	
	
	
	
	
	
	
	
		 /**
	     * Metodo utilizado para manejar la advertencia de java 
	     * @author Alan Perez
	     * @param folder se encuentra la imagen a hacer clic correspondiente
	     * @date 11/Noviembre/2025
	     * @return true si realiza el clic correspondiente para continuar con el proceso de abrir la app java false si no realiza el clic correspondiente
	     */
public boolean AdvertenciaJava(File folder) {

    try {
        // Cargar patrones
        HashMap<String, Pattern> ImgPattern = getPatternsImg2(folder);
        System.out.println("Patrones cargados del folder: " + folder);

        Screen screen = new Screen();
        String imageName = "AceptoComision.png";
        Pattern pattern = ImgPattern.get(imageName);

        u.hardWait(6);

        // Validar que exista el patrón
        if (pattern == null) {
            System.out.println("Patrón no encontrado en HashMap: " + imageName);
			testCase.addScreenShotCurrentStep("No se encontro el patron en hashMap"+ imageName);
            return false;
        }

        System.out.println("Esperando a que el patrón aparezca... " + imageName);

        // Esperar la imagen
        Match match = screen.wait(pattern, 30);

        if (match != null) {
            System.out.println("Patrón encontrado, haciendo clic...");
            screen.click(match);
            u.hardWait(5);
			testCase.addScreenShotCurrentStep("Se da clic en el boton acepto riesgos en java");
            return true;
        }

        System.out.println("No se encontró el patrón en pantalla: " + imageName);
		testCase.addScreenShotCurrentStep("No se encontro el patron en pantalla"+ imageName);
        return false;

    } catch (Exception e) {
        System.out.println("Error en AdvertenciaJava: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Marca una excepcion al querer realizar un clic en el boton acepto riesgos");
        return false;
    }
}

	
	
	/**
	 * Metodo para dar enter en el boton aceptar
	 * @author Alan Perez
	 * @throws AWTException, InterruptedException
  	 * @date 03/10/2025
  	 * @return true si finaliza el flujo correspondiente al dar clic en aceptar false en caso de no hacer clic 
	 */
public boolean BtnAceptarConcu() throws AWTException, InterruptedException {
    
    try {
        Robot robot = new Robot();

        // Pequeña pausa antes de presionar Enter
        u.hardWait(2);

        // Enviar Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        testCase.addScreenShotCurrentStep("Se da clic en botón aceptar");

        return true;
        
    } catch (AWTException e) {
        System.out.println("Error al crear instancia de Robot: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Error en generar la instancia robot");
        return false;

    } catch (Exception e) {
        System.out.println("Error inesperado al intentar presionar ENTER: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Error inesperado al intentar presionar ENTER");
        return false;
    }
}

	
	/**
	 * Metodo para dar enter en el boton aceptar y ejecutar ventana de EBS
	 * @author Alan Perez
  	 * @date 03/10/2025
  	 * @return true si da clic en aceptar y ejecutar false en caso de hacer clic 
	 */
	
	public boolean btnAceptarYEjecutar() throws AWTException, InterruptedException {
    
    try {
        Robot robot = new Robot();

        // Pausa opcional
        u.hardWait(2);

        // Enviar Enter (warning)
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // Enviar Enter (ejecutar)
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        testCase.addScreenShotCurrentStep("Se presiona ENTER en Aceptar y Ejecutar");
        return true;

    } catch (AWTException e) {
        System.out.println("Error al crear instancia de Robot: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Error al generar la instancia robot");
        return false;

    } catch (Exception e) {
        System.out.println("Error inesperado al presionar ENTER: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Error inesperado al presionar enter");
        return false;
    }
}

	
	
	/**
	 * Metodo para dar enter en el boton ejecutar
	 *
	 * @author Alan Perez
	 * @return true en caso de realizar clic en ejecutar EBS false en caso de no realizar el clic 
  	 * @date 03/10/2025
	 */
	
public boolean btnEjecutar() throws AWTException, InterruptedException {

    try {
        Robot robot = new Robot();

        // Pausa opcional
        u.hardWait(1);

        // Enviar ENTER para Ejecutar
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        u.hardWait(5);

        // TAB
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

     
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
		testCase.addScreenShotCurrentStep("Se da enter en el boton ejecutar");
        return true;

    } catch (AWTException e) {
        System.out.println("Error con Robot (AWTException): " + e.getMessage());
		testCase.addScreenShotCurrentStep("No se genero la instancia de robot");
        return false;

    } catch (Exception e) {
        System.out.println("Error inesperado en btnEjecutar(): " + e.getMessage());
		testCase.addScreenShotCurrentStep("Ocurrio una excepcion");
        return false;
    }
}

	
	
	/**
	 * Metodo para dar clic en el boton cancelar
	 *
	 * @author Alan Perez
  	 * @date 03/10/2025
  	 * @return true si realiza correctamente clic en el boton para cancelar los parametros por default false en caso contrario
	 */
public boolean BtnCancelarParamDefault() throws AWTException, InterruptedException {
    try {

        Robot robot = new Robot();

        // Pausa opcional
        u.hardWait(1);

        // TAB
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);

        // ENTER para cancelar warning
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // TAB para abrir parámetros
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
testCase.addScreenShotCurrentStep("Se da enter en boton de cancelar para cancelar los parametros por default");
        return true;

    } catch (AWTException e) {
        System.out.println("Error con Robot (AWTException): " + e.getMessage());
		testCase.addScreenShotCurrentStep("Error al generar la instancia Robot");
        return false;

    } catch (Exception e) {
        System.out.println("Error inesperado en BtnCancelarParamDefault(): " + e.getMessage());
		testCase.addScreenShotCurrentStep("Error inesperado al realizar el enter en el boton");
        return false;
    }
}

	
	
	/**
	 * @author Alan Perez
	 * @description Verifica si se ha recibido un correo del remitente esperado en los últimos 5 minutos.
	 * @param host Servidor IMAP (ej. imap.gmail.com)
	 * @param port Puerto IMAP (ej. 993)
	 * @param username Correo del usuario
	 * @param password Contraseña del usuario (o contraseña de aplicación)
	 * @param remitenteEsperado Dirección de correo del remitente esperado
	 * @return true si se encontró un correo válido, false en caso contrario
	 */
public boolean validarCorreoPorRemitente(String host, String port, String username, String password, String remitenteEsperado) throws Exception {
    Properties properties = new Properties();
    properties.put("mail.store.protocol", "imaps");
    properties.put("mail.imaps.host", host);
    properties.put("mail.imaps.port", port);
    properties.put("mail.imaps.ssl.enable", "true");

    Session session = Session.getInstance(properties);
    Store store = null;
    Folder inbox = null;

    try {

        store = session.getStore("imaps");
        store.connect(username, password);

        inbox = store.getFolder("INBOX");
        inbox.open(javax.mail.Folder.READ_ONLY);

        Message[] messages = inbox.getMessages();
        Date ahora = new Date();
        long cincoMinutos = 5 * 60 * 1000;

        // Buscar desde el más reciente
        for (int i = messages.length - 1; i >= 0; i--) {

            Message message = messages[i];
            Address[] froms = message.getFrom();

            String remitente = (froms != null && froms.length > 0)
                    ? ((InternetAddress) froms[0]).getAddress()
                    : null;

            Date fechaCorreo = message.getSentDate();

            // Validación
            if (remitente != null && remitente.equalsIgnoreCase(remitenteEsperado)) {
                long diferencia = Math.abs(ahora.getTime() - fechaCorreo.getTime());

                if (diferencia <= cincoMinutos) {
                    System.out.println("Correo encontrado:");
                    System.out.println("Remitente: " + remitente);
                    System.out.println("Asunto: " + message.getSubject());
                    System.out.println("Fecha: " + fechaCorreo);
                    testCase.addScreenShotCurrentStep("Correo");
                    return true;
                }
            }
        }

    } catch (Exception e) {
        System.err.println("Error al validar correo: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Error al validar correo");
        throw new Exception("Error al validar correo: " + e.getMessage());
    } finally {
        try {
            if (inbox != null && inbox.isOpen()) inbox.close(false);
        } catch (Exception ignored) {}

        try {
            if (store != null) store.close();
        } catch (Exception ignored) {}
    }

    // No se encontró correo válido
	testCase.addScreenShotCurrentStep("No se encontro correo valido");
    return false;
	
}


	
	/**
	 * @Description obtener Contenido del correo en texto plano
	 * @author Alan Perez
	 * @param p - Variable que almacena el correo electrónico completo para despues dividirlo por partes y regresar el contenido buscado del correo
	 * @return - Regresa el contenido del correo electronico en caso de false regresa la excepcion 
	 */
	private String obtenerContenidoMensaje(Part p) throws Exception {
		try {
	    if (p.isMimeType("text/plain")) {
	        return (String) p.getContent();
	    } else if (p.isMimeType("text/html")) {
	        return (String) p.getContent();
	    } else if (p.isMimeType("multipart/*")) {
	        Multipart mp = (Multipart) p.getContent();
	        for (int i = 0; i < mp.getCount(); i++) {
	            String resultado = obtenerContenidoMensaje(mp.getBodyPart(i));
	            if (resultado != null) return resultado;
	        }
	    }
		} catch (Exception e) {
	        testCase.addCodeEvidenceCurrentStep("Error: " + e.getMessage());
			
	        throw new Exception("Error: " + e.getMessage());
	    }
	    return null;
	}
	
	
	
	/**
	 * Metodo para enviar el servicio como tipo string y escribirlo
	 *@param String Servicio para agregar como parametro en EBS 
	 * @author Alan Perez
  	 * @date 03/10/2025
	 */
	
	public boolean TypeParamsService(String Servicio) throws AWTException, InterruptedException {

    try {
        Robot robot = new Robot();

        // Presionar TAB 3 veces
        for (int i = 0; i < 3; i++) {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            u.hardWait(2);
        }

        // Escribir el texto carácter por carácter
        for (char c : Servicio.toCharArray()) {
            boolean upperCase = Character.isUpperCase(c);
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                System.out.println("Carácter no válido para tecla: " + c);
                continue;
            }

            if (upperCase) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }

            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);

            if (upperCase) {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }

            u.hardWait(1); // pequeña pausa entre teclas
        }

        // Último TAB después de escribir
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
testCase.addScreenShotCurrentStep("Se escribio correctamente el parametro servicio");
        return true;
    }

    catch (Exception e) {
        System.err.println("Error en TypeParamsService: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Error en escribir el servicio");
        return false;
    }
}

	
	/**
	 * Metodo para ingresar fecha en EBS en los parametros de concurrente
	 *@param Fecha ini fecha inicial de parametro
	 *@param Fecha Fin fecha fin de parametro
	 * @author Alan Perez
  	 * @date 03/10/2025
  	 * @return true si finaliza el proceso de ingresar fecha iniciar y fecha fin en los parametros en caso contrario regresa false
	 */
	
	public boolean TypeFecha(String FechaIni, String FechaFin) throws AWTException, InterruptedException {

    try {
        Robot robot = new Robot();

        // Escribir Fecha Inicio
        for (char c : FechaIni.toCharArray()) {
            boolean upperCase = Character.isUpperCase(c);
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                System.out.println("Carácter no válido para tecla: " + c);
                continue;
            }

            if (upperCase) robot.keyPress(KeyEvent.VK_SHIFT);

            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);

            if (upperCase) robot.keyRelease(KeyEvent.VK_SHIFT);

            u.hardWait(1);
        }

        // TAB después de FechaIni
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);

        // Escribir Fecha Fin
        for (char c : FechaFin.toCharArray()) {
            boolean upperCase = Character.isUpperCase(c);
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);

            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                System.out.println("Carácter no válido para tecla: " + c);
                continue;
            }

            if (upperCase) robot.keyPress(KeyEvent.VK_SHIFT);

            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);

            if (upperCase) robot.keyRelease(KeyEvent.VK_SHIFT);

            u.hardWait(1);
        }

        // Presionar TAB 3 veces
        for (int i = 0; i < 3; i++) {
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            u.hardWait(1);
        }

        u.hardWait(5);

        // Enter final
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
testCase.addScreenShotCurrentStep("Se ingresa las fechas correctamente");
        return true;
    }

    catch (Exception e) {
        System.err.println("Error en TypeFecha: " + e.getMessage());
		testCase.addScreenShotCurrentStep("Ocurrio alguna excepcion");
		
        return false;
    }
	}
}







	        
	  
	  
	  
	  
	  
	  
	  
	   
	
		
		
		
		
	


	
	

	
	
	
	

