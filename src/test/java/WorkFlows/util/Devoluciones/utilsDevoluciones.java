package WorkFlows.util.Devoluciones;

import java.time.Duration;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WorkFlows.util.GlobalVariables;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class utilsDevoluciones {
	private SeleniumUtil u;
	private TestCase testCase;
	private pomDevoluciones pom;
	private WebDriverWait wait;
	/**
	 * Locators
	 */
	By app = By.xpath("(//span[text()=\"Cambiar aplicación\"])[1]");
	By appOXXO = By.xpath("//span[text()=\"Inicio Apps Web (Cosmos users)\"]");
	By inputUser = By.id("txtUserID");
	By appMenu = By.cssSelector("i[title=\"Menú Aplicación\"]");
	By buttonLogin = By.id("sub");
	By textButton = By.cssSelector("button[title=\"Muestra las aplicaciones web a las que tiene acceso\"]");
	By linkPortal = By.partialLinkText("Portal Devoluciones");
	By menuSelectedCM = By.xpath("//span[text()=\"DEVOLUCIONES MASIVAS\"]");
	By optionSelectedCR = By.xpath("//span[text()=\"CARGA REPLE\"]");
	By menuSelectedConfig = By.xpath("//span[text()=\"CONFIGURACIÓN\"]");
	By optionSelectedSD = By.xpath("//span[text()=\"APROBAR SOLICITUDES DE DEVOLUCIÓN\"]");
	By optionSelectedInbox = By.xpath("//span[text()=\"INBOX\"]");
	By optionSelectedCrear = By.xpath("//span[text()=\"CREAR DEVOLUCIÓN\"]");
	By continuarButton = By.xpath("//span[text()=\"Continuar\"]");
	By buttonAprobar = By.id("btn_aprobar");
	By buttonRechazar = By.id("btn_rechazar");
	By buttonBuscar = By.id("btn_buscar");
	By buttonSubirArchivo = By.id("btn_subirArchivo");
	By aplicarCarta = By.id("setDocumento_btn");
	By selectFile = By.id("excelFile");
	By selectFile2 = By.id("docFile");
	By ddLocator = By.id("devoluciones_cmb");
	By ddType = By.id("tipoDevolucion_cmb");
	By ddPeriodo = By.id("periodos_cmb");
	By ddMes = By.id("mesPeriodos_cmb");
	By ddMotivo = By.id("motivoDevolucion_cmb");
	By ddProcedimiento = By.id("procedimientos_cmb");
	By msgExito = By.id("msj");
	By folioCB = By.xpath("(//input[@type=\"checkbox\"])[1]");
	By tiendaCB = By.xpath("//strong[text()=\"Monterrey Dark Store\"]/preceding-sibling::input");
	By tiendaCB2 = By.id("cb_gridTiendasTree");
	By articuloCB = By.xpath("(//div[@id=\"divGridArticulosEncontrados\"]//input)[2]");
	By folioID = By.xpath("((//input[@type=\"checkbox\"])[1]/ancestor::tr)[3]/td[3]");
	By ddEstatus = By.id("estatus_cmb");
	By buscarButton = By.cssSelector("input[value=\"BUSCAR\"]");
	By tableFolios = By.id("gview_gridInboxDevoluciones");
	By tableCargaReple = By.id("gbox_gridCargaArchivos");
	By tableElement = By.xpath("//*[@id=\"2\"]/td[1]");
	By optionButton = By.xpath("(//span[@class=\"ui-button-text\"])[1]");
	By flecha1 = By.xpath("(//img[@src=\"./images/flecha_amarilla_derx1.gif\"])[1]");
	By flecha2 = By.xpath("(//img[@src=\"./images/flecha_amarilla_derx1.gif\"])[2]");
	By aplicarButton = By.id("aplicarDevolucion_btn");
	By descripcion = By.id("item_txt");
	By itemCB = By.xpath("//div[@id=\"gview_gridArticulosRelacion\"]//input");
	By generarDevolucion = By.id("generarDevolucion_btn");
	By buttonContinuar = By.xpath("//span[contains(text(),\"Continuar\")]");
	By modalProcessing = By.cssSelector("div.blockMsg");
	
	/**
	 * Constructor. Recibe el SeleniumUtil y el testCase utilizado en el test.
	 * @param u
	 * @param testCase
	 * @author Karim Duran
	 */
	public utilsDevoluciones(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.pom = new pomDevoluciones(u, testCase);
	}
	/**
	 * Ingresa a la p�gina con el usuario seleccionado
	 * @param user
	 * @author Karim Duran
	 */
	public void loginPom(String user) {
		pom.enterText("Ingresar user", user, inputUser);
		pom.clickButton("Log In", buttonLogin);
		//u.hardWait(1);
	}
	/**
	 * Ingresa a la p�gina con el usuario seleccionado cuando el usuario se encuentra dentro del DEV STUDIO
	 * @param user
	 * @author Karim Duran
	 */
	public void fullLogin(String user) {
		pom.enterText("Ingresar user", user, inputUser);
		pom.clickButton("Log In", buttonLogin);
		u.click(appMenu);
		mouseHover("Cambiar aplicación");
		u.hardWait(2);
	}
	/**
	 * Comprueba la existencia de los aplicativos disponibles para el usuario seleccionado
	 * @author Karim Duran
	 */
	public void appsWeb() {
		pom.clickButton("Mostrar Aplicaciones Web", textButton); u.hardWait(2);
		pom.viewApps("Aplicativos disponibles", linkPortal);
		u.scrollToElement(linkPortal);
		pom.viewApps("Aplicativos disponibles", linkPortal);
	}
	/**
	 * Abre el aplicativo Portal Devoluciones
	 * @author Karim Duran
	 */
	public void portalDevText() {
		u.hardWait(2);
		pom.clickButton("Portal Devoluciones", linkPortal);
	}
	/**
	 * Cambia de ventana a la pagina de Portal Devoluciones
	 * @author Karim Duran
	 */
	public void portalDev() {
		//pom.clickButton("Portal Devoluciones", linkPortal);
	    ArrayList<String> tabs2 = new ArrayList<String> (u.getDriver().getWindowHandles());
	    u.getDriver().switchTo().window(tabs2.get(1));
	    testCase.addScreenShotCurrentStep(u, "Portal Devoluciones");
	}
	/**
	 * Realiza un mouse hover sobre el menu seleccionado y da click en la opcion deseada
	 * @param casoPrueba
	 * @author Karim Duran
	 */
	public void mouseHover(String casoPrueba) {
		switch(casoPrueba) {
			case "Cargas Reple":
				pom.dropDownMouse(casoPrueba, menuSelectedCM, optionSelectedCR);
				testCase.addScreenShotCurrentStep(u, casoPrueba);
				break;
			case "Solicitudes de Devolucion":
				pom.dropDownMouse(casoPrueba, menuSelectedConfig, optionSelectedSD);
				testCase.addScreenShotCurrentStep(u, casoPrueba);
				break;
			case "Inbox":
				pom.dropDownMouse(casoPrueba, menuSelectedConfig, optionSelectedInbox);
				testCase.addScreenShotCurrentStep(u, casoPrueba);
				break;
			case "Cambiar aplicación":
				pom.dropDownMouse(casoPrueba, app, appOXXO);
				break;
			case "Crear Devolución":
				pom.dropDownMouse(casoPrueba, menuSelectedConfig, optionSelectedCrear);
				testCase.addScreenShotCurrentStep(u, casoPrueba);
				break;
		}
	}
	/**
	 * Se encarga de subir el archivo de Cargas Masivas
	 * @author Karim Duran
	 */
	public void subirArchivo() {
		pom.clickButton("Subir Archivo", buttonSubirArchivo);
		u.hardWait(5);
		pom.dropDownUtil(ddLocator, "10");
		String pathCartasReple = GlobalVariables.FILES_WF + "Files\\Devoluciones\\Cargas Reple.xlsx";
		pom.enterText("Selecciona archivo", pathCartasReple, selectFile);
		pom.clickButton("Subir Archivo", buttonSubirArchivo);
	}
	/**
	 * Se encarga de subir el archivo de Cargas Masivas
	 * @author Karim Duran
	 */
	public void crearDev(String folio) {
		waitProcessing();
		u.sendKeys(descripcion, folio); u.hardWait(1);
		waitProcessing();
		u.click(buttonBuscar); u.hardWait(1);
		waitProcessing();
		u.click(articuloCB); u.hardWait(1);
		waitProcessing();
		u.click(flecha1); u.hardWait(1);
		waitProcessing();
		testCase.addScreenShotCurrentStep(u, "Resultado");
		pom.dropDownUtil(ddType, "2"); u.hardWait(1);
		pom.dropDownUtilIndex(ddPeriodo, 1); u.hardWait(1);
		pom.dropDownUtilIndex(ddMes, 1); u.hardWait(1);
		pom.dropDownUtil(ddMotivo, "8"); u.hardWait(1);
		pom.dropDownUtil(ddProcedimiento, "2"); u.hardWait(1);
		testCase.addScreenShotCurrentStep(u, "Resultado");
		u.click(tiendaCB); u.hardWait(1);
		waitProcessing();
		u.click(flecha2); u.hardWait(1);
		waitProcessing();
		u.click(tiendaCB2); u.hardWait(1);
		waitProcessing();
		u.click(aplicarButton); u.hardWait(1);
		waitProcessing();
		final String BASE_PATH = System.getProperty("user.dir");
		String pathDevolucion = BASE_PATH + "\\Config\\Input\\Files\\Devoluciones\\Devolucion.docx";
		pom.enterText("Selecciona archivo", pathDevolucion, selectFile2);
		u.hardWait(1);
		u.click(itemCB); u.hardWait(1);
		u.click(aplicarCarta); u.hardWait(3);
		u.click(continuarButton); u.hardWait(1);
		u.click(generarDevolucion); u.hardWait(20);
		waitProcessing();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(msgExito));
		String mensaje = u.getDriver().findElement(msgExito).getText();
		try {
			mensaje.contains("Se generó con éxito");
			u.hardWait(2);
			pom.clickButton("Continuar", buttonContinuar);
			testCase.addTextEvidenceCurrentStep(mensaje);
		} catch (Exception e) {
			testCase.addScreenShotCurrentStep(u, mensaje);
			System.out.println("Selecciona otro producto o tienda");
		}
	}
	/**
	 * Comprobaci�n de que si se ingres� correctamente a la opci�n de Solicitudes de Devoluciones
	 * @author Karim Duran
	 */
	public void solicitudDev() {
		pom.viewButton("Solicitudes de Devoluciones", buttonAprobar, buttonRechazar);
	}
	/**
	 * Buscar folios por categoria (en este caso por usuario que creo la devoluci�n)
	 * @param userFF
	 * @author Karim Duran
	 */
	public void searchFolio(String text) {
		pom.dropDownUtil(ddEstatus, text);
		pom.clickButton("Buscar Folio", buscarButton);
	}
	/**
	 * Aceptar o rechazar el folio
	 * @param response
	 * @author Karim Duran
	 */
	public String folioResponse(String response) {
		pom.clickButton("Folio Deseado", folioCB);
		String folioNo = u.getDriver().findElement(folioID).getText();
		try {
		switch (response){
			case "Aprobar":
				pom.clickButton(response + " folio", buttonAprobar);
				pom.clickButton(response + " folio", optionButton);
				break;
			case "Rechazar":
				pom.clickButton(response + " folio", buttonRechazar);
				pom.clickButton(response + " folio", optionButton);
				break;
		}
		return folioNo;
		} catch (Exception e) {
			System.out.println(e);
			throw new AssertionError("No se encontraro ningun folio en estado Pendiente");
		}
	}
	/**
	 * Comprobar la existencia de la tabla de folios
	 * @author Karim Duran
	 */
	public void gridFolios(String table, String folioNo) {
		switch (table) {
		case "Folios" :
			waitProcessing();
			wait.until(ExpectedConditions.elementToBeClickable(folioCB));
			u.scrollToElement(tableFolios);
			pom.viewApps("Tabla con los folios pendientes", tableFolios);
			break;
		case "Carga Reple":
			waitProcessing();
			//wait.until(ExpectedConditions.elementToBeClickable(tableElement));
			u.scrollToElement(tableCargaReple);
			pom.viewApps("Tabla Carga Reple", tableCargaReple);
			break;
		case "Folios2" :
			try {
				waitProcessing();
				wait.until(ExpectedConditions.elementToBeClickable(folioCB));
				String elementLoc = "//td[text()=" + folioNo + "]";
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(elementLoc)));
				u.scrollToElement(tableFolios);
				u.highLight(By.xpath(elementLoc));
				pom.viewApps("Tabla con los folios pendientes", tableFolios);
				u.unhighLight(By.xpath(elementLoc));
				break;
			} catch (Exception e) {
				System.out.println(e);
				throw new AssertionError("El folio aprobado/rechazado sigue presente dentro del grid de busqueda");
			}
		}
	}
	
	public void waitProcessing() {
		try {
			u.hardWait(2);
			while (u.getDriver().findElement(modalProcessing).isDisplayed()) {
				u.hardWait(2);
				System.out.println("Procesando...");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
