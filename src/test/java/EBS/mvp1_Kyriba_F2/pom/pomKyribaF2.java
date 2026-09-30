package EBS.mvp1_Kyriba_F2.pom;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

import EBS.tools.LoginTool;
import EBS.util.GlobalVariables;
import modelo.TestCase;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;

public class pomKyribaF2 {
//	private IMAUtils ima;
	private final SeleniumUtil u;
	private final TestCase testCase;
	private final HashMap<String, String> data;
	private final PasswordUtil passwordUtil = new PasswordUtil();

	private SikuliUtil sikuli = new SikuliUtil();
	private Screen screen = new Screen();
	private Pattern p1;
	private String s1;
	private String folder = "EBS";
	private Select list;
	private String mainTab;
	private Set<String> handles;

	// Identificadores
	private final By inputUsuario = By.id("Uid");
	private final By inputPass = By.id("Pwd");
	private final By btnLogin = By.id("3054");

	private final By finanzasQA = By
			.xpath("//*[@id=\"projects_ProjectsStyle\"]/table/tbody/tr[3]/td[1]/div/table/tbody/tr/td[2]/a");
	private final By btnContinuar = By.id("3054");
	private final By infoFinanciera = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[2]/td[2]/table/tbody/tr/td[2]/span/a");
	private final By infoDeCedis = By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/div/table/tbody/tr/td[2]/a");
	private final By cluster = By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[1]/td[1]/div/table/tbody/tr/td[2]/a");
	private final By analisisEstructuraContableCluster = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[1]/td[1]/div/table/tbody/tr/td[2]/a");
	private final By analisisEstructuraContable = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[1]/td[2]/div/table/tbody/tr/td[2]/a");
	private final By balanceFiltradoPorCedisCluster = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[1]/td[2]/div/table/tbody/tr/td[2]/a");
	private final By balanceFiltradoPorCedis = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[2]/td[2]/div/table/tbody/tr/td[2]/a");
	private final By edoDeResultadosPorCedisCluster = By
			.xpath("//*[@id='FolderIcons']/tbody/tr[2]/td[1]/div/table/tbody/tr/td[2]/a");
	private final By edoDeResultadosPorCedis = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[3]/td[1]/div/table/tbody/tr/td[2]/a");
	private final By edoDeResultadosPorCedisEstacionalCluster = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[2]/td[2]/div/table/tbody/tr/td[2]/a");
	private final By edoDeResultadosPorCedisEstacional = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[3]/td[2]/div/table/tbody/tr/td[2]/a");
	private final By uafirCedisCluster = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[3]/td[1]/div/table/tbody/tr/td[2]/a");
	private final By uafirCedis = By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[1]/div/table/tbody/tr/td[2]/a");
	private final By uafirCedisEstacionalCluster = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[3]/td[2]/div/table/tbody/tr/td[2]/a");
	private final By uafirCedisEstacional = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[5]/td[2]/div/table/tbody/tr/td[2]/a");
	private final By balanceDeComprobacion = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[4]/td[1]/div/table/tbody/tr/td[2]/a");
	private final By reporteDeVariacionesDeGastoPorCuentaCluster = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[4]/td[2]/div/table/tbody/tr/td[2]/a");
	private final By reporteDeVariacionesDeGastoPorCuenta = By
			.xpath("//*[@id=\"FolderIcons\"]/tbody/tr[6]/td/div/table/tbody/tr/td[2]/a");

	private final By btnAgregarJerarquia = By.xpath("//*[@id=\"id_mstr81\"]/img");
	private final By dropYear = By.xpath("//*[@id=\"id_mstr109\"]/tbody/tr/td[2]");

	private final By btnEliminarYear = By.xpath("//*[@id=\"id_mstr118\"]/img");
	private final By btnAgregarYear = By.xpath("//*[@id=\"id_mstr125\"]/img");
	private final By btnAgregarYear_ = By.xpath("//*[@id=\"id_mstr75\"]/img");
	private final By btnAgregarYearBalance = By.xpath("//*[@id=\"id_mstr109\"]/img");

	private final By btnAgregarEmpresa = By.xpath("//*[@id=\"id_mstr158\"]/img");
	private final By btnAgregarEmpresa_ = By.xpath("//*[@id=\"id_mstr108\"]/img");
	private final By btnAgregarEmpresaBalance = By.xpath("//*[@id=\"id_mstr63\"]/img");

	private final By btnEjecutarInforme = By.xpath("//*[@id=\"id_mstr167\"]");
	private final By btnEjecutarInforme_ = By.xpath("//*[@id=\"id_mstr117\"]");
	private final By btnEjecutarInformeBalance = By.xpath("//*[@id=\"id_mstr117\"]");

	private final By selectCluster = By.xpath("(//*[@id='elemKey'])[2]");
	private final By selectCluster_ = By.xpath("(//*[@id='elemKey'])[1]");
	private final By selectPlaza = By.xpath("(//*[@id='elemKey'])[3]");
	private final By selectPlaza_ = By.xpath("(//*[@id='elemKey'])[2]");

	private final By filasDeDatos = By.xpath("//*[@id=\"report_toolbar\"]/table/tbody/tr/td[3]/span[1]");
	private final By filasDeDatos_ = By.xpath("//*[@id=\"report_toolbar\"]/table/tbody/tr/td[2]/span[1]");
	private final By informacionDeCedis = By
			.xpath("//*[@id=\"pathBean_ReportPathStyle\"]/div[2]/div[1]/div[2]/span[7]/a");
	private final By filasDatos = By.xpath("//span[contains(text(),'Filas de datos:')]");
	private final By filasDatos1 = By.xpath("//*[@id=\"table_UniqueReportID\"]/tbody/tr[5]/td[3]");

	private final By btnMenu = By.xpath("//*[@id=\"ribbonToolbarTabsListContainer\"]/div[1]/table/tbody/tr/td[3]");
	private final By btnExportar = By.xpath("//*[@id=\"RptHomeMenu_\"]/tbody/tr/td/div/div[18]");
	private final By btnExcelConFormato = By.xpath(
			"//*[@id=\"RptHomeExportMenu_WEB-INFxmllayoutsblocksHomeExportMenuLayoutxml\"]/tbody/tr/td/div/div[8]/a/div[2]/span[1]");
	private final By selectDelimitador = By.xpath("//*[@id=\"exportPlaintextDelimiter\"]");
	private final By btnExportarReporte = By.xpath("//*[@id=\"3131\"]");
	private final By cerrarVentana = By.xpath("//*[@id=\"mstrWeb_content\"]/div/a");

	By btnAtras;
	By FoldersList = By.xpath("//div[@class='mstrLargeIconViewItemName']");
	By C_FinanzasQA = By.xpath("(//a[@class='mstrLargeIconViewItemLink'])[5]");
	By dropDownCluster = By.xpath("//span[contains(text(),'Cluster:')]/select");
	By dropDownplaza = By.xpath("//span[contains(text(),'Plaza:')]/select");
	By dropDownCR = By.xpath("//span[contains(text(),'Tipo de CR:')]/select");
	By Total = By.xpath("//*[@fg='topLeftMv']");
	By Total_ = By.xpath("//*[@id='table_UniqueReportID']/tbody/tr[5]/td[3]");
	By Total1 = By.xpath("//*[@id='table_UniqueReportID']/tbody/tr[5]/td[3]");
	By dropDownUO = By.xpath("//span[contains(text(),'UO:')]/select");
	By dropDownIndicadores = By.xpath("//span[contains(text(),'Indicadores:')]/select");
	By dropDownTipoCluster = By.xpath("//span[contains(text(),'Tipo de CR:')]/select");
	By C_InformacionDeCedis = By.xpath("(//a[@class='mstrLargeIconViewItemLink'])[9]");
	By C_InformacionDeCedis2 = By.xpath("(//a[@class='mstrLargeIconViewItemLink'])[9]/parent::td");
	By C_Cluster = By.xpath("//a[text()='Cluster']");
	By tipoGrupoSeleccionado = By.xpath("//div[@class='mstrExprEditorConditionContainer']");
	By MesSeleccionado = By.xpath("(//div[@class='mstrExprEditorConditionContainer'])[2]");
	By tipoGrupoRemover = By.xpath("(//*[@class='mstrBGIcon_tbRemove'])[1]");
	By tipoMesRemover = By.xpath("(//*[@class='mstrBGIcon_tbRemove'])[2]");
	By tipoGrupoAgregar = By.xpath("(//*[@class=\"mstrBGIcon_tbAdd\"])[1]");
	By tipoMesAgregar = By.xpath("(//*[@class=\"mstrBGIcon_tbAdd\"])[2]");
	By RubroGrupoSeleccionar = By.xpath("(//*[@class='mstrBGIcon_treeNodeClosedOrphan    mstrTreeViewNodeConnector'])");
	By RubrotipoGrupoSeleccionar = By.xpath("(//*[@class='mstrBGIcon_ae mstrTreeViewNodeShortDisplay'])");
	By MthtYear = By.xpath("(//*[@class='mstrBGIcon_ae mstrListBlockItemName'])");
	By btn_EjecutarReport = By.xpath("//input[@class='mstrButton' and @value='Ejecutar Informe']");
	By MXOXXO_APXXXXX_Pagos = By.xpath("(//div[normalize-space()='Pagos'])[1]");
	By MXOXXO_APXXXXX_PagosEntrar = By.xpath("(//div[normalize-space()='Entrar'])[2]");
	By MXOXXO_APXXXXX_PagosEntrarGerentePagos = By.xpath("//div[normalize-space()='Gerente de Pagos']");
	By MXOXXO_APXXXXX_EjecutarProcesoPagounica = By
			.xpath("//a[normalize-space()='Ejecutar Solicitud de Proceso de Pago �nica']");
	By MXOXXO_APXXXXX_Otros = By.xpath("(//div[normalize-space()='Otros'])[1]");
	By MXOXXO_APXXXXX_OtrosRequests = By.xpath("(//div[normalize-space()='Requests'])");
	By MXOXXO_APXXXXX_OtrosRequestsRun = By.xpath("(//div[normalize-space()='Run'])");
	By MXOXXOCMXXXXXUsuarioBankStatements = By.xpath("(//div[normalize-space()='Bank Statements'])[1]");
	By MXOXXOCMXXXXXUsuarioBankStatementsInterfas = By
			.xpath("(//div[normalize-space()='Bank Statement Interface'])[1]");
	By MXOXXOCMXXXXXUsuarioSetUp = By.xpath("(//div[normalize-space()='Setup'])[1]");
	By MXOXXOCMXXXXXUsuarioSetUpBanks = By.xpath("(//div[normalize-space()='Banks'])[1]");
	By MXOXXOCMXXXXXUsuarioSetUpBanksBanks = By.xpath("(//div[normalize-space()='Banks'])[2]");
	By i_MXOXXOCMXXXXXBanksName = By.xpath("(//input[@id='BankName'])");
	By L_MXOXXOCMXXXXXBanksActualizar = By.xpath("//*[@id='BankResultTable:FndUpdateImage:0']/img");
	By L_MXOXXOCMXXXXXBanksSucursal = By.xpath("//*[text()='BANCO DE OCCIDENTE S.A.']/following::img[2]");
	By LoteDePaga = By.xpath("(//*[contains(text(),'Nombre Lote Pago')])/following::input[1]");
	By plantilla = By.xpath("(//*[contains(text(),'Usar Plantilla')])/following::input[1]");
	By plantillaselected = By.xpath("//*[contains(text(),'OS_MONTERREYCNC_CCO_UO')]");
	By fechaLimite = By.xpath("//*[contains(text(),'Fecha L�mite de Pago')]/following::input[1]");
	By ReceptorPAGO = By.xpath("//*[contains(text(),'Receptor Pago')]/following::input[1]");
	By btnEjecutar = By.xpath("(//*[@type='submit'])[2]");
	By ConfirmacionDeEjecucion = By.xpath("//*[contains(text(),'Confirmaci')]/following::div[2]");
	By iptLoteName = By.xpath("//input[@id='SearchCheckrunName']");
	By btnIr = By.xpath("//button[@title='Ir']");
	By btnHome = By.xpath("//img[@title='Home']");
	By tbLote = By.xpath("//a[@id='ResultsTable:LINK:0']");
	By tbFAC_Selected = By.xpath("//*[@id='SelectedPsTable:TradingPartner:0']/following::td[2]");
	By EjecutarPAgo = By.xpath("//*[@id='ResubmitPsr']");
	By RefreshState = By.xpath("//button[@id='ResultsTable:Refresh:0']");
	By EstatusPago = By.xpath("//*[@id='ResultsTable:StatusCodeDsp:0']");
	By IniciarAccion = By.xpath("//*[@title='Efectuar Acci�n']");
	By Detalles = By.xpath("//img[@src='/OA_HTML/cabo/images/alta/disclosecollapsed_ena.png']");
	By btn_Continuar = By.xpath("//*[@id='Continue']");
	By btn_apply = By.xpath("//*[@id='Apply']");
	By lnk_VisualizarSucursal = By.xpath("//*[@id='BankResultTable:FndView:0']/img");
	By pSucursales = By.xpath("//*[contains(text(),'Simple de Sucursal')]");
	
	/**
	 * Constructor de la clase
	 * 
	 * @param u        ---------> Objeto para el uso de los metodos del framework
	 * @param testCase --> Objeto para la generacion de evidencias
	 * @param data     ------> Objeto para la obtencion de datos del dataProvider
	 * @author 1000072516 - Antono Zarate
	 */

	String ebsUrl = GlobalVariables.getEbsMexico;
	String userName = GlobalVariables.getGAGUAYOHexEbsUsr;
	String password = GlobalVariables.getGAGUAYOHexEbsPwd;

	public pomKyribaF2(SeleniumUtil u, TestCase testCase, HashMap<String, String> data) {
		LoginTool inicio = new LoginTool(u, testCase, ebsUrl, userName, password);
		this.u = u;
		this.testCase = testCase;
		this.data = data;
	}


	/**
	 * Metodo para hacer barrido de las carpetas y seleccionar por nombre visible.
	 * Cedis Cluster
	 * 
	 * @param Nombre de Carpeta
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */

	public void seleccionarCarpeta(String carpeta) {
		u.hardWait(5);
		List<WebElement> elementos = u.getDriver().findElements(FoldersList);

		// Itera sobre los elementos
		for (WebElement elemento : elementos) {
			String texto = elemento.getText();

			if (texto.contains(carpeta)) {
				System.out.println("Texto encontrado: " + texto);
				WebElement precedingSiblingLink = elemento.findElement(By.xpath("preceding-sibling::a"));
				highlightElement(u, precedingSiblingLink);
				testCase.addScreenShotCurrentStep("C_Cluster");
				precedingSiblingLink.click();

				break;
			}
		}
	}

	/**
	 * Metodo para remarcar en rojo un elemento determinado
	 * 
	 * @param element
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */

	public void highlightElement(SeleniumUtil u, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
		// Guarda el estilo original del elemento
		String originalStyle = element.getAttribute("style");
		// Modifica el estilo del elemento para añadir un borde rojo
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
				"border: 2px solid red; border-style: dashed;");

		
		

		// Restaura el estilo original del elemento
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, originalStyle);
	}

	/**
	 * Metodo para Navegar hacia la pantalla Finanzas Cedis Cluster
	 * 
	 * @param Folder
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */
	public void navigateTo_FinanzasQA_InformacinFinanciera_InformacionCEDIS_Cluster2(String Folder) {
		
			waitUntilvisibilityOfElementLocated(C_FinanzasQA);
			testCase.addScreenShotCurrentStep("C_Cluster");
			seleccionarCarpeta("Finanzas QA");
			u.hardWait(5);
			// u.click(C_FinanzasQA);
		

		u.hardWait(3);

		
			// ima.SelectOptionByXpath("//*[@type='submit']");
		
		
			By informesCompartidos = By.xpath("//*[@title='Informes compartidos']");

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));

			wait.until(ExpectedConditions.elementToBeClickable(informesCompartidos));
			u.highLight(informesCompartidos);
			testCase.addScreenShotCurrentStep("InfoFin");
			u.click(informesCompartidos);
			u.hardWait(5);
		

		
			By C_InformacionFinanciera = By.xpath("(//a[@class='mstrLargeIconViewItemLink'])[4]");
			By hilihi = By.xpath("(//a[@class='mstrLargeIconViewItemLink'])[4]/parent::td");
			waitUntilElementToBeClickable(C_InformacionFinanciera);
			u.highLight(C_InformacionFinanciera);
			testCase.addScreenShotCurrentStep("InfoFin");
			u.click(C_InformacionFinanciera);
			u.hardWait(8);
		

		
			waitUntilvisibilityOfElementLocated(C_InformacionDeCedis);
			u.hardWait(5);
			u.highLight(C_InformacionDeCedis2);
			testCase.addScreenShotCurrentStep("InfoFincedis");
			u.hardWait(5);
			seleccionarCarpeta(Folder);
		

		
			waitUntilElementToBeClickable(C_Cluster);
			u.highLight(C_Cluster);
			u.hardWait(2);
			seleccionarCarpeta("Cluster");
		
	}

	/**
	 * Metodo para selccionar y hacer click en Finanzas QA
	 * 
	 * @param
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */
	public void selectFinanzas() {
		
			u.waitForLoadPage();
			u.waitWebElement(finanzasQA, 120, 3);
			u.highLight(finanzasQA);
			testCase.addScreenShotCurrentStep("Click en Finanzas QA");
			u.click(finanzasQA);

	
	}

	/**
	 * Click en el boton Continuar
	 * 
	 * @author 1000072516 - Antonio Zarate
	 */
	public void selectContinuar() {
		
			u.waitForLoadPage();
			u.waitWebElement(btnContinuar, 120, 3);
			u.highLight(btnContinuar);
			testCase.addScreenShotCurrentStep("Click en Continuar");
			u.click(btnContinuar);

		
	}

	/**
	 * Click en la opcion Informacion Financiera
	 * 
	 * @author 1000072516 - Antonio Zarate
	 */
	public void selectInfoFinanciera() {
		
			u.waitForLoadPage();
			u.waitWebElement(infoFinanciera, 120, 3);
			u.highLight(infoFinanciera);
			testCase.addScreenShotCurrentStep("Click en Informacion Financiera");
			u.click(infoFinanciera);

		
	}

	/**
	 * Buscar File Explorer
	 * 
	 * @author Gilberto Aguayo
	 * @return
	 */
	public boolean typeFileExplorer() {
		try {
			u.hardWait(3);
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "typeTosearch.png");
			screen.wait(p1, 30.0);
			screen.click();
			screen.type("File");
			System.out.println("Selecciona el Year 2023");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			return false;
		}
		return true;
	}

	/**
	 * Buscar File Explorer
	 * 
	 * @author Gilberto Aguayo
	 * @return
	 */
	public boolean cerrarExcelreport() {
		try {
			u.hardWait(3);
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "closeExcel.png");
			screen.wait(p1, 30.0);
			screen.click();
			screen.highlight();
			System.out.println("Selecciona el Year 2023");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			return false;
		}
		return true;
	}

	/**
	 * Buscar File Explorer
	 * 
	 * @author Gilberto Aguayo
	 * @return
	 */
	public boolean clickFileExplorer() {
		try {
			u.hardWait(3);
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + "FileExplorer.png");
			screen.wait(p1, 30.0);
			screen.click();
			System.out.println("Selecciona el Year 2023");
		} catch (Exception ex) {
			System.out.println(" -> " + ex);
			return false;
		}
		return true;
	}

	
	

	/**
	 * Metodo para para esperar que un elemento sea Visible.
	 * 
	 * @param Element
	 * @throws
	 * @author GilbertoAguayo
	 */

	public void waitUntilvisibilityOfElementLocated(By Element) {

		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));

		wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
	}

	/**
	 * Metodo para para esperar que un elemento sea clickable.
	 * 
	 * @param Elemento
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */

	public void waitUntilElementToBeClickable(By Element) {

		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));

		wait.until(ExpectedConditions.elementToBeClickable(Element));
	}

	/**
	 * Metodo para esperar que un elemento Con determinado texto sea Visible.
	 * 
	 * @param texto
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */
	public boolean esperarElementoConTexto(String texto, int timeoutInSeconds) {
		try {

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(timeoutInSeconds));

			boolean textoPresente = wait.until(ExpectedConditions.textToBePresentInElementLocated(
					By.xpath("//div[@class='mstrPathTextCurrent']//span[@class='mstrPathLast']"), texto));

			if (textoPresente) {
				System.out.println("El elemento está disponible y contiene el texto: " + texto);
				return true;
			} else {
				System.out.println("El elemento no contiene el texto esperado.");
				return false;
			}
		} catch (NoSuchElementException e) {

			System.out.println("El elemento no está disponible en la página.");
			return false;
		} catch (Exception e) {
			// Capturar cualquier otra excepción durante la espera
			System.out.println("Ocurrió un error durante la espera del elemento: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Metodo para validar Report Generado en Cedis Cluster
	 * 
	 * @param title
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */
	public boolean validarReportGenerado(String title) {

		By titleReport = By.xpath("//span[@class='mstrPanelTitle' and text()='" + title + "']");

		try {
			// JavascriptExecutor js = (JavascriptExecutor) u.getDriver();

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(600));
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(titleReport));
			//// div[@class='mstrBGIcon_ae mstrListBlockItemName' and contains(text(),
			//// 'Marzo:2023')]
			System.out.println(element.getText());
			u.highLight(titleReport);
			testCase.addScreenShotCurrentStep("ejecutar");

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	

	/**
	 * Metodo para hacer click en el filtro cluster
	 * 
	 * @param
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */
	public void filtro_Cluster() {
		// TODO Auto-generated method stub
		waitUntilElementToBeClickable(dropDownCluster);
		u.click(dropDownCluster);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("dw");

	}

	/**
	 * Metodo para remover el tipo grupo Seleccionado
	 * 
	 * @param Nombre de Tipo Grupo
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */

	public void remover_mesDefault() {

	
			waitUntilElementToBeClickable(tipoGrupoSeleccionado);
			u.hardWait(2);
			u.click(tipoGrupoSeleccionado);
			u.hardWait(1);
			u.click(tipoGrupoRemover);

		
	}

	/**
	 * Método para obtener el listado de los textos visibles en el dropdown cluster
	 * 
	 * @param SelectCluster
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */
	public void getDropdownOptionsTextCluster(WebDriver driver, String SelectCluster) {
		// Localizar el dropdown
		WebElement dropdownElement = driver.findElement(dropDownCluster);

		// Inicializar un objeto Select
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByVisibleText(SelectCluster);
		u.hardWait(10);
		u.waitForLoadPage();
		u.highLight(dropDownCluster);
		testCase.addScreenShotCurrentStep("dwselect");
	}

	/**
	 * Método para obtener el listado de los textos visibles en el dropdown textos
	 * visibles en el dropdown plaza
	 * 
	 * @param SelectCluster
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */
	public void getDropdownOptionsTextPLAZA(WebDriver driver, String selecPlaza) {
		// Localizar el dropdown
		WebElement dropdownElement = driver.findElement(dropDownplaza);

		// Inicializar un objeto Select
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByVisibleText(selecPlaza);
		u.hardWait(10);
		u.waitForLoadPage();
		u.highLight(dropDownplaza);
		testCase.addScreenShotCurrentStep("dwselect");
	}

	/**
	 * Método para obtener el listado de los textos visibles en el dropdown textos
	 * visibles en el dropdown TextUO
	 * 
	 * @param SelectCluster
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */
	public void getDropdownOptionsTextUO(WebDriver driver, String selecUO) {
		// Localizar el dropdown
		WebElement dropdownElement = driver.findElement(dropDownUO);

		// Inicializar un objeto Select
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByVisibleText(selecUO);
		u.hardWait(10);
		u.waitForLoadPage();
		u.highLight(dropDownUO);
		testCase.addScreenShotCurrentStep("dwselect");
	}

	/**
	 * Método para obtener el listado de los textos visibles en el dropdown textos
	 * visibles en el dropdown TipoCR
	 * 
	 * @param SelectCluster
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */
	public void getDropdownOptionsTipoCR(WebDriver driver, String selecCR) {
		// Localizar el dropdown
		WebElement dropdownElement = driver.findElement(dropDownCR);

		// Inicializar un objeto Select
		Select dropdown = new Select(dropdownElement);
		dropdown.selectByVisibleText(selecCR);
		u.hardWait(10);
		u.waitForLoadPage();
		u.highLight(dropDownCR);
		testCase.addScreenShotCurrentStep("dwselect");
	}

	/**
	 * Método para remarcar en rojo un elemento determinado
	 * 
	 * @param cluster
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */
	public void higlightCluster(String cluster) {
		
			// JavascriptExecutor js = (JavascriptExecutor) u.getDriver();

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(50));
			WebElement element = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[text()='" + cluster + "']")));
			//// div[@class='mstrBGIcon_ae mstrListBlockItemName' and contains(text(),
			//// 'Marzo:2023')]

			// Scroll into view if necessary
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);

			// Click the element using JavaScript Executor
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", element);

			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();

			js.executeScript("window.scrollBy(1000, -300);");

			u.highLight(By.xpath("//td[text()='" + cluster + "']"));
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("clusterselect");

		
	}

	/**
	 * Método para remarcar en rojo la celda de una tabla
	 * 
	 * @param cluster
	 * @throws AssertionError
	 * @author GilbertoAguayo
	 */
	public void higlightTabla() {

		
			// JavascriptExecutor js = (JavascriptExecutor) u.getDriver();

			// Encuentra todas las celdas de la fila 4
			List<WebElement> cells = u.getDriver()
					.findElements(By.xpath("//*[@id='table_UniqueReportID']/tbody/tr[4]/td"));
			StringBuilder rowText = new StringBuilder();
			// Itera sobre cada celda, resáltala y obtén su texto
			for (WebElement cell : cells) {
				// Resalta la celda usando JavaScript
				JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
				js.executeScript("arguments[0].style.border='3px solid red'", cell);

				// Pausa para que puedas ver el highlight
				
				rowText.append(cell.getText()).append(" | "); // Usa un separador, por ejemplo " | "
				// Obtén el texto de la celda (opcional)
				String cellText = cell.getText();
				System.out.println(cellText);

				// Opcionalmente, puedes quitar el borde después de un tiempo
				// js.executeScript("arguments[0].style.border=''", cell);
			}

			testCase.addScreenShotCurrentStep("clusterselect");
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			js.executeScript("window.scrollBy(1000, -300);");
			testCase.addScreenShotCurrentStep("clusterselect");
			testCase.addTextEvidenceCurrentStep(rowText.toString());
			System.out.println(rowText.toString());

		

	}

	/**
	 * Buscar File Explorer
	 * 
	 * @author Gilberto Aguayo
	 * @throws FindFailed 
	 */
	public void clickimagenName(String imageName) throws FindFailed {
	
			u.hardWait(1);
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + imageName + ".png");
			screen.wait(p1, 30.0);
			screen.click();

			System.out.println("yesButton");
		
	}

	/**
	 * Buscar File Explorer
	 * 
	 * @author Gilberto Aguayo
	 * @throws FindFailed 
	 */
	public void highlightImage(String imageName) throws FindFailed {
		
			u.hardWait(1);
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + imageName + ".png");
			screen.find(p1).highlight(1, "red");
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("save");

			screen.find(p1).highlightOff();
			System.out.println("yesButton");
		
	}

	/**
	 * Buscar File Explorer
	 * 
	 * @author Gilberto Aguayo
	 * @throws FindFailed 
	 */
	public void highlightImagePermanente(String imageName) throws FindFailed {
		
			u.hardWait(1);
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + imageName + ".png");
			screen.find(p1).highlight("red");
			u.hardWait(2);
			testCase.addScreenShotCurrentStep("save");

			screen.find(p1).highlightOff();
			System.out.println("yesButton");
		
	}

	/**
	 * Buscar File Explorer
	 * 
	 * @author Gilberto Aguayo
	 * @throws FindFailed 
	 */
	public void waitimagenName(String imageName) throws FindFailed {
	
			u.hardWait(3);
			s1 = sikuli.PatternPath(folder);
			p1 = sikuli.PatternImage(s1 + imageName + ".png");
			screen.wait(p1, 30);
			u.hardWait(1);
			System.out.println("yesButton");
		
	}

	/**
	 * Metodo para buscar una factura
	 * 
	 * @param String factura
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 * @return 
	 */
	public boolean BusquedaFactura(String factura) throws FindFailed {
		u.hardWait(5);
		int tiempoMaximoEspera = 30;
		// Intervalo entre intentos (en segundos)

		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < tiempoMaximoEspera * 1000) {
			
				clickimagenName("lamparabusqueda");

				System.out.println("Se hizo clic en el botón.");

				waitimagenName("NoProvedorEncontrarFacturaWindow");

				break;

			
		}
		u.hardWait(1);
		for (int i = 0; i < 5; i++) {
			screen.type(Key.TAB);
			u.hardWait(1);
			System.out.println("Key TAB 5 times ");
		}
		u.hardWait(3);
		screen.type(factura);
		System.out.println("type factura");

		clickimagenName("btnEncontrarFactura");
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("clusterselect");
		try {
			waitimagenName("btnAcciones");
		}catch(Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para hacer clic en el boton Acciones en la pantalla Facturas
	 * 
	 * @param
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 * @throws FindFailed 
	 */
	public void clickAccionesbtn() throws FindFailed {
	
			clickimagenName("btnAcciones");
			waitimagenName("windowAcciones");
			testCase.addScreenShotCurrentStep("clusterselect");
		
	}

	/**
	 * Metodo para Visualizar Registro
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 * @throws FindFailed 
	 */
	public void clickVisualizarRegistro() throws FindFailed {
	
			waitimagenName("VisualizarRegistroXXFC");
			testCase.addScreenShotCurrentStep("clusterselect");
			clickimagenName("VisualizarRegistroXXFC");

		
	}

	/**
	 * Metodo para hacer clic en el boton Acciones en la pantalla Facturas
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean VisualizarRegistroFinalizadoNormal() {
		try {
			waitimagenName("XXFC_AP_KYRIBA_OUT");
			testCase.addScreenShotCurrentStep("XXFC_AP_KYRIBA_OUT");

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para hacer clic en el boton Acciones en la pantalla Facturas
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public void crearOrdenDeCompra(String U_operativa, String proveedor, String sucursal) {
		
			u.hardWait(2);
			screen.type(U_operativa);
			typeNTab(2);
			screen.type(proveedor);
			typeNTab(1);
			screen.type(sucursal);
			typeNTab(1);
			testCase.addScreenShotCurrentStep("clusterselect");
		
	}

	/**
	 * Metodo para hacer clic en el boton Acciones en la pantalla Facturas
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 * @throws FindFailed 
	 */
	public void clickEnvios() throws FindFailed {
		

			waitimagenName("Envios");
			clickimagenName("btnAcciones");
			testCase.addScreenShotCurrentStep("clusterselect");
		
	}

	/**
	 * Metodo para Validar una factura en la pantalla Acciones
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 * @throws FindFailed 
	 */
	public void validarFactura() throws FindFailed {
		
			clickimagenName("checkBoxValidar");
			waitimagenName("checkBoxValidarselect");
			highlightImage("checkBoxValidarselect");
			clickimagenName("aceptarValidarFacturabtn");

		
	}

	/**
	 * Metodo para verificar que la factura este validada en la pantalla muestre el
	 * estatus validada
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean ValidarEstadoFactura(String state) {
		try {

			waitimagenName(state);
			u.hardWait(1);
			// highlightImage(state);

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para verificar que la factura este validada en la pantalla muestre el
	 * estatus validada
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean PantallaSolicitudes() {
		try {

			waitimagenName("PantallaSolicitudes");
			u.hardWait(1);
			// highlightImagePermanente("PantallaSolicitudes");

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para verificar que la factura este validada en la pantalla muestre el
	 * estatus validada
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean CrearPagosFinalizadoNormal() {
		try {

			waitimagenName("CrearPagosFinalizadoSinError");
			u.hardWait(1);
			highlightImagePermanente("CrearPagosFinalizadoSinError");
			u.hardWait(1);

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para verificar que la factura este validada en la pantalla muestre el
	 * estatus validada
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean FormatearInstruccionesDepagoFinaLNormal() {
		try {

			waitimagenName("FormatearInstruccionesDepagoFinaLNormal");
			u.hardWait(1);
			highlightImagePermanente("FormatearInstruccionesDepagoFinaLNormal");
			u.hardWait(1);

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para verificar que la factura este validada en la pantalla muestre el
	 * estatus validada
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean RegistrarEstadoFinalizadoNormal() {
		try {

			waitimagenName("RegistrarEstadoFinalizadoNormal");
			u.hardWait(1);
			highlightImagePermanente("RegistrarEstadoFinalizadoNormal");
			u.hardWait(1);

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para contabilizar factura en la pantalla Acciones
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 * @throws FindFailed 
	 */
	public void ContabilizarFactura() throws FindFailed {
	
			clickimagenName("checkBoxContavilizarFactura");
			waitimagenName("checkBoxContavilizarFacturaSelected");
			// highlightImage("checkBoxContavilizarFacturaSelected");
			clickimagenName("radioFinalContabilizarFactura");
			waitimagenName("radioFinalContabilizarFacturaSelected");
			// highlightImage("radioFinalContabilizarFacturaSelected");
			u.hardWait(1);
			clickimagenName("aceptarValidarFacturabtn");

		
	}

	/**
	 * Metodo para llenar crear Orden de compra
	 *
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 * @throws FindFailed 
	 */
	public void llenarOrdenDeCompra() throws FindFailed {
		
			waitimagenName("OrdenesDeCompra");
			testCase.addScreenShotCurrentStep("OrdenesDeCompra");
			clickimagenName("proveedorOrdendePago");
			u.hardWait(1);
			screen.type("RADIOMOVIL DIPSA S.A DE C.V." + Key.TAB);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("llenadoOrdenesDeCompra");

		
	}

	/**
	 * Metodo para capturar el Nombre de lote de pago
	 *
	 * @param loteName
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public void capturarNombreDeLoteDePago(String loteName) {
		
			waitElementToBeClickable(LoteDePaga, 50);
			u.sendKeys(LoteDePaga, loteName);
			u.highLight(LoteDePaga);
			testCase.addScreenShotCurrentStep("LoteDePaga");
			u.unhighLight(LoteDePaga);

		
	}

	/**
	 * Metodo para seleccionar Plantilla para lote de pago
	 *
	 * @param nombrePlantilla
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public void seleccionarPlantilla(String nombrePlantilla) {
		
			waitElementToBeClickable(plantilla, 50);
			u.sendKeys(plantilla, nombrePlantilla);
			u.hardWait(2);
			typeKEY("TAB");
			u.hardWait(3);
			u.highLight(plantilla);
			testCase.addScreenShotCurrentStep("LoteDePaga");
			/*
			 * waitUntilvisibilityOfElementLocated(plantillaselected);
			 * u.highLight(plantilla); u.highLight(plantillaselected);
			 * testCase.addScreenShotCurrentStep("LoteDePaga");
			 */
			u.unhighLight(plantilla);
			u.unhighLight(plantillaselected);

		
	}

	/**
	 * Metodo para seleccionar Plantilla para lote de pago
	 *
	 * @param nombrePlantilla
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public void loteDePago_SeleccionPagoProgramado(String fecha, String receptor) {
		
			waitElementToBeClickable(fechaLimite, 50);
			u.clear(fechaLimite);
			u.sendKeys(fechaLimite, fecha);
			u.hardWait(2);
			typeKEY("TAB");
			u.highLight(fechaLimite);
			waitUntilElementToBeClickable(ReceptorPAGO);
			u.highLight(ReceptorPAGO);
			u.sendKeys(ReceptorPAGO, receptor);
			u.hardWait(2);
			typeKEY("TAB");
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("LoteDePaga");
			u.unhighLight(fechaLimite);
			u.unhighLight(ReceptorPAGO);

		
	}

	/**
	 * Metodo para hacer click en el boton Ejecutar
	 *
	 * @param nombrePlantilla
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public void loteDePago_click_Ejecutar() {
		
			waitElementToBeClickable(btnEjecutar, 50);
			u.hardWait(2);
			u.highLight(btnEjecutar);
			testCase.addScreenShotCurrentStep("btnEjecutar");
			u.click(btnEjecutar);
			u.unhighLight(btnEjecutar);

	
	}

	/**
	 * Metodo para buscar lote de pago por nombre
	 *
	 * @param nombreLote
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public void buscarSolicitudDeLoteDePago(String nombreLote) {
		
			waitElementToBeClickable(iptLoteName, 50);
			u.highLight(iptLoteName);
			u.sendKeys(iptLoteName, nombreLote);
			waitElementToBeClickable(btnIr, 50);
			u.highLight(btnIr);
			testCase.addScreenShotCurrentStep("btnEjecutar");
			u.click(btnIr);

		
	}

	/**
	 * Metodo para buscar lote de pago por nombre
	 *
	 * @param nombreLote
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public void click_BTN_IR() {
		
			waitElementToBeClickable(btnIr, 50);
			u.highLight(btnIr);
			testCase.addScreenShotCurrentStep("btnEjecutar");
			u.click(btnIr);

	}
	/**
	 * Metodo para hacer click en el boton Home
	 *
	 * @param nombreLote
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public void click_BTN_Home() {
	
			waitElementToBeClickable(btnHome, 50);
			u.highLight(btnHome);
			testCase.addScreenShotCurrentStep("btnHome");
			u.click(btnHome);
	}
	

	/**
	 * Metodo para validar la ejecucion del lote de pago
	 *
	 * @param name
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean validar_ejecucion(String name) {

		waitUntilvisibilityOfElementLocated(ConfirmacionDeEjecucion);
		WebElement elemento = u.getDriver().findElement(ConfirmacionDeEjecucion);

		// Obt�n el texto del elemento
		String textoCompleto = elemento.getText();

		// Verifica que el texto contenga el nombre
		boolean contieneNombre = textoCompleto.contains(name);

		// Si ambos est�n presentes, la validaci�n es exitosa
		if (contieneNombre == true) {
			System.out.println(textoCompleto);
		} else {
			System.out.println("No se ejecuto correctamente");
		}
		testCase.addScreenShotCurrentStep("btnEjecutar");
		return contieneNombre;
	}

	/**
	 * Metodo para validar se encontro el lote de pago ejecutado
	 *
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean loteDepagoEncontrado(String lote) {

		waitUntilvisibilityOfElementLocated(tbLote);
		WebElement elemento = u.getDriver().findElement(tbLote);

		// Obt�n el texto del elemento
		String textoCompleto = elemento.getText();

		// Verifica que el texto contenga el nombre
		boolean contieneNombre = textoCompleto.contains(lote);

		// Si ambos est�n presentes, la validaci�n es exitosa
		if (contieneNombre == true) {
			System.out.println(textoCompleto);
		} else {
			System.out.println("No se ejecuto correctamente");
		}
		testCase.addScreenShotCurrentStep("btnEjecutar");
		return contieneNombre;
	}

	/**
	 * Metodo para ver las Facturas para pago de lote
	 *
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public void loteDepagoFacturasSeleccionadas() {
		waitUntilvisibilityOfElementLocated(tbLote);
		u.highLight(tbLote);
		testCase.addScreenShotCurrentStep("btnEjecutar");
		u.click(tbLote);
		waitUntilvisibilityOfElementLocated(tbFAC_Selected);
		u.highLight(tbFAC_Selected);
		testCase.addScreenShotCurrentStep("btnEjecutar");

	}

	/**
	 * Metodo para Ejecutar el Pago
	 *
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public void EjecutarPago() {

		waitUntilElementToBeClickable(EjecutarPAgo);
		u.highLight(EjecutarPAgo);
		testCase.addScreenShotCurrentStep("btnEjecutar");
		u.click(EjecutarPAgo);

	}

	/**
	 * Metodo para Ejecutar el Pago
	 *
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public void clickContinuar() {

		waitUntilElementToBeClickable(btn_Continuar);
		u.highLight(btn_Continuar);
		testCase.addScreenShotCurrentStep("btn_Continuar");
		u.click(btn_Continuar);
		waitUntilElementToBeClickable(btn_apply);
		u.highLight(btn_apply);
		testCase.addScreenShotCurrentStep("btn_Continuar");
		u.click(btn_apply);

	}

	/**
	 * Metodo para Ejecutar el Pago
	 *
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean RefrescarEstado(String Estatus) {

		waitUntilElementToBeClickable(RefreshState);

		int attempts = 0;

		while (attempts < 80) {
			try {
				u.hardWait(1);
				WebElement element = u.getDriver().findElement(EstatusPago);
				if (element.getText().contains(Estatus)) {
					System.out.println("Texto encontrado!");
					u.highLight(EstatusPago);
					testCase.addScreenShotCurrentStep("EstatusPago");
					return true;
				}
			} catch (NoSuchElementException e) {
				// Elemento no existe, continuar
			}

			System.out.println("Texto no encontrado - Refrescando...");
			try {
				u.hardWait(1);
				WebElement refreshBtn = u.getDriver().findElement(RefreshState);
				refreshBtn.click();
				attempts++;
				Thread.sleep(2000); // Espera para recarga
			} catch (NoSuchElementException e) {
				System.out.println("Bot�n de refresh no encontrado");
				break;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
		System.out.println("M�ximo de intentos alcanzado (" + 50 + ")");
		return false;

	}

	/**
	 * Metodo para hacer click en Iniciar Accion
	 *
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean IniciarAccion() {
		try {
			waitUntilElementToBeClickable(IniciarAccion);
			u.highLight(IniciarAccion);
			testCase.addScreenShotCurrentStep("EstatusPago");
			u.click(IniciarAccion);
		} catch (Exception e) {
			return false;
		}

		return true;

	}

	/**
	 * Metodo para Ejecutar el Pago
	 *
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean EjecutarProcesoDePago() {
		try {
			waitUntilElementToBeClickable(btnIr);
			u.highLight(btnIr);
			testCase.addScreenShotCurrentStep("EstatusPago");
			u.hardWait(1);
			u.click(btnIr);
		} catch (Exception e) {
			return false;
		}

		return true;

	}

	/**
	 * Metodo para Ejecutar el Pago
	 *
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean Clickdetalles() {
		try {
			waitUntilElementToBeClickable(Detalles);
			u.highLight(Detalles);
			testCase.addScreenShotCurrentStep("Detalles");
			u.hardWait(1);
			u.click(Detalles);
		} catch (Exception e) {
			return false;
		}

		return true;

	}

	/**
	 * Metodo para Validar el Pago
	 *
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean ValidarPago() {
		try {
			//Espera encontrar el Importe == 0 si encuentra distinto de cero regresa false
			// se valida con assertFalse para que al ser distinto de cero quiere decir que esta pagado
			waitimagenName("ImportePagado");

			testCase.addScreenShotCurrentStep("Detalles");
			u.hardWait(1);
		} catch (Exception e) {
			return false;
		}

		return true;

	}

	/**
	 * Metodo para Ejecutar el Pago
	 *
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean VisualizarPagos() {
		try {
			waitimagenName("VisualizarPagos");
			clickimagenName("VisualizarPagos");
			waitimagenName("pagoVisualizado");
			testCase.addScreenShotCurrentStep("Detalles");
			u.hardWait(1);
		} catch (Exception e) {
			return false;
		}

		return true;

	}

	/**
	 * Methodo para presionar una tecla indicada
	 * 
	 * @param key
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 **/
	public void typeKEY(String key) {

		switch (key) {
		case "TAB":

			screen.type(Key.TAB);
			System.out.println("type TAB");
			break;
		case "ENTER":

			screen.type(Key.ENTER);
			System.out.println("Type ENTER");
			break;
		case "BACKSPACE":
			screen.type(Key.BACKSPACE);
			System.out.println("Type ENTER");
			break;
		case "multiplicar":
			System.out.println("Vamos a multiplicar 3 * 3: " + (3 * 3));
			break;
		case "imprimir":
			System.out.println("Imprimiendo un mensaje de ejemplo.");
			break;
		default:
			System.out.println("Acción no reconocida.");
			break;
		}

	}

	/**
	 * Methodo para entrar a Gerente de Pagos en la responsabilidad
	 * MXOXXOAPXXXXXSuperUsuario
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 **/
	public void MXOXXOCMXXXXX_Usuario_Bank_Statements_BankStatementInterface() {
		ScrollToElement(MXOXXOCMXXXXXUsuarioBankStatements);
		u.click(MXOXXOCMXXXXXUsuarioBankStatements);
		ScrollToElement(MXOXXOCMXXXXXUsuarioBankStatementsInterfas);
		u.highLight(MXOXXOCMXXXXXUsuarioBankStatementsInterfas);
		testCase.addScreenShotCurrentStep("click");
		u.click(MXOXXOCMXXXXXUsuarioBankStatementsInterfas);
	}

	/**
	 * Methodo para entrar a Gerente de Pagos en la responsabilidad
	 * MXOXXOAPXXXXXSuperUsuario
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 **/
	public void MXOXXOAPXXXXXSuperUsuarioPagosEntrarGerenteDePagos() {
		ScrollToElement(MXOXXO_APXXXXX_Pagos);
		u.click(MXOXXO_APXXXXX_Pagos);
		ScrollToElement(MXOXXO_APXXXXX_PagosEntrar);
		u.click(MXOXXO_APXXXXX_PagosEntrar);
		ScrollToElement(MXOXXO_APXXXXX_PagosEntrarGerentePagos);
		u.highLight(MXOXXO_APXXXXX_PagosEntrarGerentePagos);
		testCase.addScreenShotCurrentStep("click");
		u.click(MXOXXO_APXXXXX_PagosEntrarGerentePagos);
	}

	/**
	 * Methodo para entrar a Gerente de Pagos en la responsabilidad
	 * MXOXXOAPXXXXXSuperUsuario
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 **/
	public void MXOXXOAPXXXXXSuperUsuarioOtrosRequestsRun() {
		ScrollToElement(MXOXXO_APXXXXX_Otros);
		u.click(MXOXXO_APXXXXX_Otros);
		ScrollToElement(MXOXXO_APXXXXX_OtrosRequests);
		u.click(MXOXXO_APXXXXX_OtrosRequests);
		ScrollToElement(MXOXXO_APXXXXX_OtrosRequestsRun);
		u.highLight(MXOXXO_APXXXXX_OtrosRequestsRun);
		testCase.addScreenShotCurrentStep("click");
		u.click(MXOXXO_APXXXXX_OtrosRequestsRun);
	}

	/**
	 * Methodo para entrar a Gerente de Pagos en la responsabilidad
	 * MXOXXOAPXXXXXSuperUsuario
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 **/
	public void MXOXXO_CMXXXXX_SuperUsuarioSetUpBanks() {
		ScrollToElement(MXOXXOCMXXXXXUsuarioSetUp);
		u.click(MXOXXOCMXXXXXUsuarioSetUp);
		ScrollToElement(MXOXXOCMXXXXXUsuarioSetUpBanks);
		u.click(MXOXXOCMXXXXXUsuarioSetUpBanks);
		ScrollToElement(MXOXXOCMXXXXXUsuarioSetUpBanksBanks);
		u.highLight(MXOXXOCMXXXXXUsuarioSetUpBanksBanks);
		testCase.addScreenShotCurrentStep("click");
		u.click(MXOXXOCMXXXXXUsuarioSetUpBanksBanks);
	}

	/**
	 * Methodo para entrar a Gerente de Pagos en la responsabilidad
	 * MXOXXOAPXXXXXSuperUsuario
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 **/
	public void MXOXXO_CMXXXXX_BuscarNombreBank(String Bank) {
		waitElementToBeClickable(i_MXOXXOCMXXXXXBanksName, 50);
		u.sendKeys(i_MXOXXOCMXXXXXBanksName, Bank);
		typeKEY("TAB");
		u.hardWait(1);
		waitElementToBeClickable(btnIr, 20);
		u.highLight(btnIr);
		testCase.addScreenShotCurrentStep("click");
		u.click(btnIr);
		waitUntilElementToBeClickable(L_MXOXXOCMXXXXXBanksActualizar);
		u.click(L_MXOXXOCMXXXXXBanksActualizar);
	}

	/**
	 * Methodo para entrar a Gerente de Pagos en la responsabilidad
	 * MXOXXOAPXXXXXSuperUsuario
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 **/
	public void MXOXXO_CMXXXXX_BuscarNombreBankSucursal(String Bank) {
		waitElementToBeClickable(i_MXOXXOCMXXXXXBanksName, 50);
		u.sendKeys(i_MXOXXOCMXXXXXBanksName, Bank);
		typeKEY("TAB");
		u.hardWait(1);
		waitElementToBeClickable(btnIr, 20);
		u.highLight(btnIr);
		testCase.addScreenShotCurrentStep("click");
		u.click(btnIr);
		waitUntilElementToBeClickable(By.xpath("//*[text()='" + Bank + "']/following::img[2]"));
		u.click(By.xpath("//*[text()='" + Bank + "']/following::img[2]"));
	}

	/**
	 * Methodo para entrar a Gerente de Pagos en la responsabilidad
	 * MXOXXOAPXXXXXSuperUsuario
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 **/
	public void MXOXXOAPXXXXXEjecutarSolicitudProcesodePago() {
		ScrollToElement(MXOXXO_APXXXXX_EjecutarProcesoPagounica);
		u.highLight(MXOXXO_APXXXXX_EjecutarProcesoPagounica);
		testCase.addScreenShotCurrentStep("click");
		u.click(MXOXXO_APXXXXX_EjecutarProcesoPagounica);

	}

	/**
	 * Esperar Pantalla y Genera Factura
	 * 
	 * @param U_Operativa, proveedor, FactNumber,Importe, Terminos
	 * @throws FindFailed
	 * @author Gilberto Aguayo
	 */
	public void GenerarFactura(String U_Operativa, String proveedor, String Sucursal, String fecha, String FactNumber,
			String Importe, String Terminos) throws FindFailed {
		testCase.addScreenShotCurrentStep("factura");
		u.hardWait(3);
		clickimagenName("newLine");
		u.hardWait(3);
		screen.type(U_Operativa);
		u.hardWait(1);
		typeNTab(5);
		screen.type(proveedor);
		typeNTab(1);
		screen.type(Sucursal);
		typeNTab(1);
		screen.type(fecha);
		typeNTab(1);
		screen.type(FactNumber);
		typeNTab(1);
		u.hardWait(1);
		screen.type("USD");
		typeNTab(1);
		screen.type(Importe);
		testCase.addScreenShotCurrentStep("factura");
		typeNTab(15);
		screen.type(Terminos);
		typeNTab(1);
	}

	/**
	 * Metodo para hacer TAB N veces
	 * 
	 * @param num
	 * @throws
	 * @author Gilberto Aguayo
	 */
	public void typeNTab(int num) {

		for (int i = 0; i < num; i++) {
			
				screen.type(Key.TAB);
				u.hardWait(2);

			
			System.out.println("Buscando campo formato de fecha.");
		}
	}
	/**
	 * M�todo para dar click al boton Save
	 * 
	 * @param iteracion
	 * @author GilbertoA@hexaware.com
	 */
	public boolean btnSaveClick() throws FindFailed {
		try {
			waitimagenName("SaveClick");
			highlightImage("SaveClick");

			clickimagenName("SaveClick");
			u.hardWait(1);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	
	
	/**
	 * Metodo para llenar campos factura 
	 * 
	 * @param numero Fecha
	 * @author GilbertoA@hexaware.com
	 * @throws FindFailed
	 */
	public void clickNewbtnFactura() throws FindFailed {
		// ImgPatter = getPatternsImg(Fol);
		// Intentar hacer clic en el botón hasta que responda
		int tiempoMaximoEspera = 30;
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < tiempoMaximoEspera * 1000) {
			System.out.println("Esperando a que el boton este habilitado...");
			waitimagenName("FacturaADuplicar");
			u.hardWait(3);
			clickimagenName("addNew");
			u.hardWait(3);
			// waitimagenName("FacturaDuplicada");
			break; // Salir del bucle si el clic fue exitoso

		}
		u.hardWait(1);

		// screen.type(Key.ALT+"E"); screen.type("D"+"R"); u.hardWait(3);

	}

	public void TabClickerConFecha(String Fac, String fecha) throws FindFailed {
		// String formatoFecha = "\\d{2}-[A-Z]{3}-\\d{4}";

		for (int i = 0; i < 7; i++) {
			try {
				typeNTab(1);
				u.hardWait(1);

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Buscando campo formato de fecha.");
		}

		String textoEncontrado = screen.text();

		// Si se encuentra el formato de fecha, salir del bucl
		System.out.println("Fecha encontrada: " + textoEncontrado);

		screen.type(fecha);
		typeNTab(1);
		u.hardWait(2);
		typeNTab(1);
		u.hardWait(1);
		screen.type(Key.ENTER);
		u.hardWait(3);
		screen.type(Fac);

		typeNTab(7);
		System.out.println("Buscando campo formato de fecha.");

		screen.type(fecha);
	}

	/**
	 * Metodo para verificar que la factura este validada en la pantalla muestre el
	 * estatus validada
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean ValidarCampo(String text) {
		try {

			waitUntilvisibilityOfElementLocated(By.xpath("//*[text()='" + text + "']"));
			u.hardWait(1);
			u.highLight(By.xpath("//*[text()='" + text + "']"));
			u.hardWait(1);

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para verificar valores en Campos en los Bancos y Sucursales
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean ValidarHigienico(String text) {

		try {

			waitUntilvisibilityOfElementLocated(By.xpath("//input[@value='" + text + "']"));
			u.hardWait(1);
			u.highLight(By.xpath("//input[@value='" + text + "']"));
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("save");

			u.unhighLight(By.xpath("//input[@value='" + text + "']"));

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para verificar valores en Campos en los Bancos y Sucursales
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean ValidarHigienicoDistro(String text) {

		try {

			waitUntilvisibilityOfElementLocated(By.xpath("(//input[@value='" + text + "'])[2]"));
			u.hardWait(1);
			u.highLight(By.xpath("(//input[@value='" + text + "'])[2]"));
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("save");

			u.unhighLight(By.xpath("(//input[@value='" + text + "'])[2]"));

		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Metodo para verificar valores en Campos en los Bancos y Sucursales
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public String ValidarHigiEstado() {

		String Campo = null;
		try {

			waitUntilvisibilityOfElementLocated(By.xpath("//*[@id='HzAddressStyleFlex7']"));
			u.hardWait(1);
			WebElement dropdown = u.getDriver().findElement(By.xpath("//*[@id='HzAddressStyleFlex7']"));
			Select select = new Select(dropdown);
			Campo = select.getFirstSelectedOption().getText();
			// Campo =
			// u.getDriver().findElement(By.xpath("//*[@id='HzAddressStyleFlex7']")).getAttribute("value");

		} catch (Exception ex) {
			waitUntilvisibilityOfElementLocated(By.xpath("//*[@id='HzAddressStyleFlex7']"));
			u.hardWait(1);

			Campo = u.getDriver().findElement(By.xpath("//*[@id='HzAddressStyleFlex7']")).getAttribute("value");
		}
		if (!Campo.isBlank()) {

			System.out.println(Campo);
		} else {

			Campo = "Null";
			System.out.println(Campo);
		}
		u.highLight(By.xpath("//*[@id='HzAddressStyleFlex7']"));
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("save");
		u.unhighLight(By.xpath("//*[@id='HzAddressStyleFlex7']"));
		return Campo;
	}

	/**
	 * Metodo para verificar valores en Campos en los Bancos y Sucursales
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public String ValidarHigCodigoPostal() {
		boolean Value;
		String Campo = null;

		try {

			u.hardWait(1);
			Campo = u.getDriver().findElement(By.xpath("//*[@id='HzAddressStyleFlex9']")).getAttribute("value");
			u.highLight(By.xpath("//*[@id='HzAddressStyleFlex9']"));
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("save");

			u.unhighLight(By.xpath("//*[@id='HzAddressStyleFlex9']"));

		} catch (Exception ex) {

			u.hardWait(1);
			Campo = u.getDriver().findElement(By.xpath("//*[@id='HzAddressStyleFlex8']")).getAttribute("value");
			u.highLight(By.xpath("//*[@id='HzAddressStyleFlex8']"));
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("save");

			u.unhighLight(By.xpath("//*[@id='HzAddressStyleFlex8']"));
		}

		if (!Campo.isBlank()) {

			System.out.println(Campo);
		} else {

			Campo = "Null";
			System.out.println(Campo);
		}

		return Campo;
	}

	/**
	 * Metodo para verificar valores en Campos en los Bancos y Sucursales
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public String ValidarHigIDContibuyente() {
		boolean Value;
		String Campo = null;
		try {

			waitUntilvisibilityOfElementLocated(By.xpath("//*[@id='TaxpayerId']"));
			u.hardWait(1);
			Campo = u.getDriver().findElement(By.xpath("//*[@id='TaxpayerId']")).getAttribute("value");
			System.out.println(Campo);
			if (!Campo.isBlank()) {
				System.out.println(Campo);
			} else {

				Campo = "Null";
				System.out.println(Campo);
			}
			u.highLight(By.xpath("//*[@id='TaxpayerId']"));
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("save");

			u.unhighLight(By.xpath("//*[@id='TaxpayerId']"));
		} catch (Exception ex) {

		}
		return Campo;
	}

	/**
	 * Metodo para verificar valores en Campos en los Bancos y Sucursales
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public String ValidarHigiEstatus() {

		String Campo = null;
		try {

			waitUntilvisibilityOfElementLocated(By.xpath("//*[@id='hzPartySiteStatus']"));
			u.hardWait(1);

			WebElement dropdown = u.getDriver().findElement(By.xpath("//*[@id='hzPartySiteStatus']"));
			Select select = new Select(dropdown);
			Campo = select.getFirstSelectedOption().getText();
			// Value =
			// u.getText(By.xpath("//*[text()='Estado']/following::Select[1]")).isEmpty();
			if (!Campo.isBlank()) {

				// Campo = u.getText(By.xpath("//*[@id='hzPartySiteStatus']"));
				System.out.println(Campo);
			} else {

				Campo = "Null";
				System.out.println(Campo);
			}
			u.highLight(By.xpath("//*[@id='hzPartySiteStatus']"));
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("save");

			u.unhighLight(By.xpath("//*[@id='hzPartySiteStatus']"));
		} catch (Exception ex) {

		}
		return Campo;
	}

	/**
	 * Metodo para verificar valores en Campos en los Bancos y Sucursales
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public String ValidarHigiTipoSucursal() {

		String Campo = null;
		try {

			waitUntilvisibilityOfElementLocated(By.xpath("//*[@id='BranchType']"));
			u.hardWait(1);

			WebElement dropdown = u.getDriver().findElement(By.xpath("//*[@id='BranchType']"));
			Select select = new Select(dropdown);
			Campo = select.getFirstSelectedOption().getText();
			// Value =
			// u.getText(By.xpath("//*[text()='Estado']/following::Select[1]")).isEmpty();
			if (!Campo.isBlank()) {

				// Campo = u.getText(By.xpath("//*[@id='hzPartySiteStatus']"));
				System.out.println(Campo);
			} else {

				Campo = "Null";
				System.out.println(Campo);
			}
			u.highLight(By.xpath("//*[@id='BranchType']"));
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("save");

			u.unhighLight(By.xpath("//*[@id='BranchType']"));
		} catch (Exception ex) {

		}
		return Campo;
	}

	/**
	 * Metodo para verificar que la factura este validada en la pantalla muestre el
	 * estatus validada
	 * 
	 * @param
	 * @throws AssertinError
	 * @author Gilberto Aguayo
	 */
	public boolean clickSucursalIcon() {
		try {

			waitUntilElementToBeClickable(lnk_VisualizarSucursal);
			u.hardWait(1);
			u.highLight(lnk_VisualizarSucursal);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("lnk_VisualizarSucursal");
			u.click(lnk_VisualizarSucursal);
			waitUntilvisibilityOfElementLocated(pSucursales);
			testCase.addScreenShotCurrentStep("lnk_VisualizarSucursal");
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public void CerrarPopupEdge() throws AWTException {
		u.hardWait(3);

		// Usa Robot para enviar TAB y ENTER (simula navegaci�n del popup)
		Robot robot = new Robot();

		// Navega entre cuentas (puedes repetir TAB m�s veces si es necesario)
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);

		// Confirma
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	/**
	 * Metodo para Cargar n�meros desde el archivo al inicio
	 * 
	 * @param
	 * @author GilbertoA@hexaware.com
	 */

	/**
	 * Metodo para hacer Scroll hacia un elemento determinado
	 * 
	 * @param By locator
	 * 
	 * @author GilbertoA@hexaware.com
	 */
	public void ScrollToElement(By locator) {
		
			waitElementToBeClickable(locator, 50);
			WebElement elemento = u.getDriver().findElement(locator);

			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			js.executeScript("arguments[0].scrollIntoView(true);", elemento);

	}
	/**
	 * Metodo para hacer explicit wait para que el elemento sea clickeable
	 * 
	 * @param By locator
	 * 
	 * @author GilbertoA@hexaware.com
	 */
	public void waitElementToBeClickable(By elemento, int seconds) {

		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(seconds));
		wait.until(ExpectedConditions.elementToBeClickable(elemento));
	}
}
