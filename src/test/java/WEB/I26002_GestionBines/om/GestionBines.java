package WEB.I26002_GestionBines.om;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import WM.util.GlobalVariables;
import modelo.TestCase;
import utils.password.PasswordUtil;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;
import java.util.Random;

/**
 *om GestionBines.
 *
 *@author	Oliver Martinez
 *@date 	23/06/2026
 */
public class GestionBines {
	
	SeleniumUtil u;
	TestCase testCase;
	WebDriverWait wait;
	
	//Login
	By userNameBox = By.id("P101_USERNAME");
	By passwordBox = By.id("P101_PASSWORD");
	By btnLogin = By.xpath("//a[text() = 'Login']");
	
	//Session Message
	By sessionErrorIcon = By.className("a-Error-icon");
	By lblHere = By.linkText("here");
	
	//Bines menu page OPtions
	By optionInicio = By.className("t1ParentTabCenterOn");
	By optionBines = By.linkText("BINes");
	By optionBancos = By.linkText("Bancos");
	By optionMontosMax = By.partialLinkText("Montos");
	By optionBanderas = By.linkText("Banderas");
	By optionTiposTarjetas = By.partialLinkText("Tipos");
	
	//Inicio page
	By headerCargaIncompleta = By.xpath("//td[@class = 't1RegionHeader'  and text() = 'Carga Incompleta']");
	By headerArchivosCargados = By.xpath("//td[@class = 't1RegionHeader'  and text() = 'Archivos cargados']");
	
	//BINes page
	By binesTable = By.className("a-IRR-tableContainer");
	By searchField = By.id("R2256417609093598_search_field");
	By searchButton = By.id("R2256417609093598_search_button");
	By btnCrearNuevo = By.linkText("Crear Nuevo");
	//BINes search result page
	By editBinIcon = By.xpath("//a[contains(@href, 'P110_BIN')]");
	By searchPageText = By.xpath("//span[contains(text(), 'Row text contains')]");
	//BINes form page
	By btnAplicarCambios = By.linkText("Aplicar Cambios");
	By btnAceptar = By.linkText("Aceptar");
	By versionHeader = By.xpath("//td[@class = 't1RegionHeader' and text() = 'Historico de Versiones']");
	By binField = By.id("P110_BIN");
	By dropListStatus = By.id("P110_STATUS");
	By dropListBandera = By.id("P110_BANDERA_ID");
	By dropListBank = By.id("P110_BANK_ID");
	By dropListType = By.id("P110_TYPE_ID");
	By fieldDescription = By.id("P110_BANK_DESC");
	By fieldProduct = By.id("P110_PRODUCT");
	By dropListBankCode = By.id("P110_BANK_CODE");
	By dropListPtsBancomer = By.id("P110_PTS");
	By dropListDev = By.id("P110_DEV");
	By dropListApplyDep = By.id("P110_APPLY_DEP");
	By dropListIntegrator = By.id("P110_INTEGRATOR");
	By successMessage = By.xpath("//div[@id=\"MESSAGE\" and text() = 'Action Processed.']");
	By btnCrearNuevoBanco = By.linkText("Crear Nuevo");
	By btnAcceptBankName = By.xpath("//a[normalize-space()='Aceptar']");
	By newBankNameBox = By.xpath("//input[@id='P210_BANK']");
	By searchBarBanks = By.xpath("//input[@id='P200_SEARCH']");
	By btnGoSearchBar = By.xpath("//a[normalize-space()='Go']");
	By btnEditBank = By.xpath("//img[@alt='Edit']");
	By btnAceptarCambio = By.xpath("//a[normalize-space()='Aplicar Cambios']");
	By lblEstado = By.xpath("//span[normalize-space()='Estado']");
	By dropListStatusBank = By.xpath("//select[@id='P210_STATUS']");
	By btnLogout = By.xpath("//a[normalize-space()='Logout']");

	String activeBank = "377";
	
	/**
	 *Crea e inicializa om GestionBines.
	 *
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public GestionBines(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
	}
	
	/**
	 *Realiza login en el aplicativo <b>Apex BINes</b>.
	 *
	 *@return	isSessionExpired
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public void loginApex(String url, String user, String password) {
	
		PasswordUtil passUtil = new PasswordUtil();
		String pass = passUtil.decryptPass(password);
		u.getDriver().manage().deleteAllCookies();
		u.get(url); 
		try {
			u.getDriver().findElement(userNameBox).sendKeys(user);
			u.getDriver().findElement(passwordBox).sendKeys(pass);
			testCase.addScreenShotCurrentStep(u, "login");
			u.click(btnLogin);
			System.out.println("Se realiza login exitoso");
			
		} catch (Exception e) {
			System.out.println("No se encontro pantalla Login. \r\n" + e );
		}
	}
	
	/**
	 *Valida si se muestra mensaje de sesion expirada.
	 *
	 *@return	isSessionExpired
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public boolean validateSessionMessage() {
		boolean isSessionExpired = false;
		try {
			if (u.getDriver().findElements(sessionErrorIcon).size()>1) {
				isSessionExpired = u.getDriver().findElements(sessionErrorIcon).size() > 0;
				testCase.addScreenShotCurrentStep(u, "expiredSession");
				u.click(lblHere);
			}else
				testCase.addScreenShotCurrentStep(u, "expiredSession");
		} catch (Exception e) {
			System.out.println("No se encontro mensaje de sesion expirada. \r\n" + e);
		}
		return isSessionExpired;
	}
	
	/**
	 *Valida si de muestra la pantalla Inicio.
	 *
	 *@return	isInicioPageVisible
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public boolean validateInicioPage() {
		boolean isInicioPageVisible = false;
		try {
			if (u.getDriver().findElements(headerArchivosCargados).size() > 0) {
				isInicioPageVisible = u.getDriver().findElements(headerArchivosCargados).size() > 0;
				testCase.addScreenShotCurrentStep(u, "Inicio");
				System.out.println("Se valida que se inicio sesion correctamente");
			}else {
				testCase.addScreenShotCurrentStep(u, "Inicio");
				System.out.println("No se muestra el inicio de sesion correcto");
			}
		} catch (Exception e) {
			System.out.println("Pagina de inicio no es visible");
		}
		return isInicioPageVisible;
	}
	
	/**
	 *Selecciona la opcion <b>BINes</b> en el menu.
	 *
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public void selectBinesOption() {
		try {
			testCase.addScreenShotCurrentStep(u, "Bines");
			u.click(optionBines);
		} catch (Exception e) {
			System.out.println("Opcion BINes no se encuentra en pantalla. \r\n" + e);
		}
	}
	
	/**
	 *Valida si se muestra la pantalla de BINes.
	 *
	 *@return	isBinesPageVisible
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public boolean validateBinesPage() {
		boolean isBinesPageVisible = false;
		try {
			if (u.getDriver().findElements(binesTable).size() > 0) {
				isBinesPageVisible = u.getDriver().findElements(binesTable).size() > 0;
				testCase.addScreenShotCurrentStep(u, "Bines Page");
			}else
				testCase.addScreenShotCurrentStep(u, "Bines Page");
		} catch (Exception e) {
			System.out.println("Pagina BINes no se encuentra en pantalla. \r\n" + e);
		}
		return isBinesPageVisible;
	}
	
	/**
	 *Ingresa BIN a editar en el campo buscar para modificarlo.
	 *
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public void searchBinAndEdit(String bin) {
		try {
			u.sendKeys(searchField, bin);
			u.click(searchButton);
			u.waitWebElement(searchPageText, 5, 1);
			testCase.addScreenShotCurrentStep(u, "click edit");
			WebElement element = u.getDriver().findElement(searchPageText);
			if (element.isEnabled()) {
				u.getDriver().findElement(editBinIcon).click();
			}
		} catch (Exception e) {
			System.out.println("No se logro ingresar a editar BIN. \r\n" + e);
		}
	}
	
	/**
	 *Valida si se muestra en pantalla el formulario de BINes.
	 *
	 *@return	isEditBinPageVisible
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public boolean validateBinPageForm() {
		boolean isEditBinPageVisible = false;
		try {
			if (u.getDriver().findElements(versionHeader).size() > 0) {
				isEditBinPageVisible = u.getDriver().findElements(versionHeader).size() > 0;
				testCase.addScreenShotCurrentStep(u, "edit Bines Page");
			}else
				testCase.addScreenShotCurrentStep(u, "edit Bines Page");
		} catch (Exception e) {
			System.out.println("Pagina para editar BIN no se encuentra en pantalla. \r\n" + e);
		}
		return isEditBinPageVisible;
	}
	
	/**
	 *Selecciona opciones de los campos del formulario en el flujo modificar BIN.
	 *
	 *@param	drop	Campo tipo <b>Select</b> que se desea seleccionar.
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public void selectOptionEditBin(String drop) {
		WebElement dropDownList = null;
		Select dropListB;
		try {
		switch (drop) {
		case "status":
			WebElement dropDownListStatus = u.getDriver().findElement(dropListStatus);
			dropListB = new Select(dropDownListStatus);
			dropListB.selectByContainsVisibleText("Inactivo");
			break;
		case "bank":
			WebElement dropDownListBank = u.getDriver().findElement(dropListBank);
			dropListB = new Select(dropDownListBank);
			dropListB.selectByContainsVisibleText("PRUEBA");
			break;
		case "bankcode":
			dropDownList = u.getDriver().findElement(dropListBankCode);
			break;
		case "pts bancomer":
			dropDownList = u.getDriver().findElement(dropListPtsBancomer);
			break;
		case "devolucion":
			dropDownList = u.getDriver().findElement(dropListDev);
			break;
		case "deposito":
			dropDownList = u.getDriver().findElement(dropListApplyDep);
			break;
		case "integrador":
			dropDownList = u.getDriver().findElement(dropListIntegrator);
		default:
			throw new IllegalArgumentException("No se encuentra el elemento: " + drop);
		}
		Select dropList = new Select(dropDownList);
		List<WebElement> options = dropList.getOptions();
		for (WebElement option : options) {
			if (!option.isSelected()) {
				dropList.selectByVisibleText(option.getText());
				break;
			}
		}	
		} catch (Exception e) {
			System.out.println("No fue posible editar los campos tipo lista. \r\n" + e);
		}
	}
	
	/**
	 *Selecciona opciones de los campos del formulario en el flujo crear BIN.
	 *
	 *@param	drop	Campo tipo <b>Select</b> que se desea seleccionar.
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public void selectOptionCreateBin(String drop) {
		WebElement dropDownList = null;
		Select dropListB;
		try {
		switch (drop) {
		case "status":
			u.waitWebElement(dropListStatus, 3, 1);
			WebElement dropDownListStatus = u.getDriver().findElement(dropListStatus);
			dropListB = new Select(dropDownListStatus);
			dropListB.selectByContainsVisibleText("Inactivo");
			break;
		case "bandera":
			dropDownList = u.getDriver().findElement(dropListBandera);
			break;
		case "bank":
			u.waitWebElement(dropListBank, 3, 1);
			WebElement dropDownListBank = u.getDriver().findElement(dropListBank);
			dropListB = new Select(dropDownListBank);
			dropListB.selectByContainsVisibleText("PRUEBA");
			break;
		case "type":
			dropDownList = u.getDriver().findElement(dropListType);
			break;
		case "bankcode":
			dropDownList = u.getDriver().findElement(dropListBankCode);
			break;
		case "pts bancomer":
			dropDownList = u.getDriver().findElement(dropListPtsBancomer);
			break;
		case "devolucion":
			dropDownList = u.getDriver().findElement(dropListDev);
			break;
		case "deposito":
			dropDownList = u.getDriver().findElement(dropListApplyDep);
			break;
		case "integrador":
			dropDownList = u.getDriver().findElement(dropListIntegrator);
			break;
		default:
			throw new IllegalArgumentException("No se encuentra el elemento: " + drop);
		}
		Select dropList = new Select(dropDownList);
		List<WebElement> options = dropList.getOptions();
		for (WebElement option : options) {
			if (!option.isSelected()) {
				dropList.selectByVisibleText(option.getText());
				break;
			}
		}	
		} catch (Exception e) {
			System.out.println("No fue posible seleccionar opcion en los campos tipo lista. \r\n" + drop + " " + e);
		}
	}
	
	/**
	 *Hace click en el boton <b>Aplicar Cambios</b> en el flujo modificar BIN.
	 *
	 *@author	Oliver Martinez
	 *@throws Exception Si no se encuentra el elemento.
	 *@date 	23/06/2026
	 */
	public void selectAplicarCambios() throws Exception {
		try {
			testCase.addScreenShotCurrentStep(u, "Edited BIN");
			u.click(btnAplicarCambios);
		} catch (Exception e) {
			throw new Exception("No se encontro boton Aplicar cambios. " + e);
		}
	}
	
	/**
	 *Hace click en el boton <b>Crear Nuevo</b> de la pantalla <b>BINes</b>
	 *
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public void selectCrearNuevo() {
		try {
			testCase.addScreenShotCurrentStep(u, "Crear nuevo");
			u.click(btnCrearNuevo);
		} catch (Exception e) {
			System.out.println("No se encontro boton Crear nuevo. \r\n" + e);
		}
	}
	
	/**
	 *Ingresa informacion en los campos del flujo cracion de BIN.
	 *
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public void typeInCreateBinFields(String bin, String bankDesc, String product) {
		try {
			u.sendKeys(binField, bin);
			u.sendKeys(fieldDescription, bankDesc);
			u.sendKeys(fieldProduct, product);
			testCase.addScreenShotCurrentStep(u, "Campos completos");
		} catch (Exception e) {
			System.out.println("No se encontro campo para ingresar informacion. \r\n" + e);
		}
	}
	
	/**
	 *Hace click en el boton <b>Aceptar</b>al hacer flujo de creacion de BIN.
	 *
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public void aceptarCreateBin() {
		try {
			testCase.addScreenShotCurrentStep(u, "Aceptar");
			u.click(btnAceptar);
		} catch ( Exception e) {
			System.out.println("No se encontro boton Aceptar. \r\n" + e);
		}
	}
	
	/**
	 *Obtiene conexion a base de datos utilizada en OM de gestion de bines de Web Methods. 
	 *
	 *@return	dasDBConnection
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public SQLUtil getDataBaseConnection() {
		SQLUtil dasDBConnection = null;
		try {
			WM.om.GestionBines wmGestionBines = new WM.om.GestionBines(testCase);
			dasDBConnection = wmGestionBines.getConnection();
			return dasDBConnection;
		} catch (Exception e) {
			System.out.println("No se pudo realizar conexion. \r\n" + e);
		}
		return dasDBConnection;
	}
	
	/**
	 *Valida si el BIN modificado en Apex se guardo correctamente en base de datos. 
	 *
	 *@return	isBinCreated
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public boolean getBinChanges(String bin) {
		boolean isBinCreated = false;
		String queryGetBinChange = "SELECT BANDERA_ID, BANK_CODE, BANK_DESC, BANK_ID, BIN, CREATION_DATE, LAST_UPDATE_DATE, STATUS\r\n"
				+ "FROM EO_RMS.TDC_BIN_CHANGES\r\n"
				+ "WHERE BIN = " + bin;
		try {
			SQLUtil connection = getDataBaseConnection();
			SQLResult res = connection.executeQuery(queryGetBinChange);
			if (!res.isEmpty()) {
				isBinCreated = !res.isEmpty();
				testCase.addQueryEvidenceCurrentStep(res);
			}else {
				isBinCreated = res.isEmpty();
				testCase.addQueryEvidenceCurrentStep(res);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("No se pudo realizar consulta. \r\n" + e);
		}
		return isBinCreated;
	}
	
	/**
	 *Valida si el BIN creado en Apex se creo correctamente en base de datos. 
	 *
	 *@return	isBinSaved
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public boolean getBinCreated(String bin) {
		boolean isBinSaved = false;
		String queryGetBinChange = "SELECT BIN, STATUS, BANDERA_ID, BANK_ID, TYPE_ID, BIN_CODE, BANK_DESC, BANK_CODE\r\n"
				+ "FROM EO_RMS.TDC_BIN\r\n"
				+ "WHERE BIN = " + bin;
		try {
			SQLUtil connection = getDataBaseConnection();
			SQLResult res = connection.executeQuery(queryGetBinChange);
			if (!res.isEmpty()) {
				isBinSaved = !res.isEmpty();
				testCase.addQueryEvidenceCurrentStep(res);
			}else {
				isBinSaved = res.isEmpty();
				testCase.addQueryEvidenceCurrentStep(res);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("No se pudo realizar consulta. \r\n" + e);
		}
		return isBinSaved;
	}
	
	/**
	 *Obtiene BIN maximo del banco de pruebas para bines de <b>padre de 6 digitos, 
	 *bines hijo de 8 digitos y bines padre de 8 digitos</b>. 
	 *
	 *@return	maxBin
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public int getMaxBinPerBank(String binType) throws Exception {
		String activeBank = getActiveBank();
		int maxBin = 0;
		int maxBinToSix = 0;
		String queryMaxBin = "SELECT MAX(BIN) AS MAX_BIN\r\n"
				+ "FROM EO_RMS.TDC_BIN\r\n"
				+ "WHERE BANK_ID = '" + activeBank +"'\r\n"
				+ "AND REGEXP_LIKE(BIN, '^[0-9]{%s}$')";
		String queryMaxBinFormatted = "";
		try {
			SQLUtil connection = getDataBaseConnection();
			SQLResult res;
			switch (binType) {
			case "6Parent":
				queryMaxBinFormatted = String.format(queryMaxBin, 6);
				System.out.println("Max Bin 6Parent: " + queryMaxBinFormatted);
				res = connection.executeQuery(queryMaxBinFormatted);
				if (!res.isEmpty()) {
					maxBin = Integer.parseInt(res.getData(0, "MAX_BIN")) + 1;
					do {
						maxBin++;
						System.out.println("Max BIN: " + maxBin);
					} while (binHasChild(maxBin) == false);
				}
				break;
			case "8Child":
				queryMaxBinFormatted = String.format(queryMaxBin, 6);
				System.out.println("Max Bin 8Child: " + queryMaxBinFormatted);
				res = connection.executeQuery(queryMaxBinFormatted);
				if (!res.isEmpty()) {
					maxBin = Integer.parseInt(res.getData(0, "MAX_BIN"));
					do {
						maxBin = maxBin * 100;
						maxBinToSix = maxBin / 100;
						System.out.println("Max BIN: " + maxBin);
					} while (binHasChild(maxBinToSix) == false);
				}
				break;
			case "8Parent":
				queryMaxBinFormatted = String.format(queryMaxBin, 8);
				System.out.println("Max Bin 8Parent: " + queryMaxBinFormatted);
				res = connection.executeQuery(queryMaxBinFormatted);
				if (!res.isEmpty()) {
					maxBin = Integer.parseInt(res.getData(0, "MAX_BIN"));
					do {
						maxBin += 100;
						maxBinToSix = maxBin / 100;
						System.out.println("Max BIN: " + maxBin);
					} while (binHasChild(maxBinToSix) == false);
				}
				break;

			default:
				System.out.println("Solo se reciben como parametros 6 o 8.");
				break;
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("No se pudo realizar consulta bin max. \r\n" + e);
		}
		return maxBin;
	}
	
	/**
	 *Obtiene banco de pruebas con estatus activo.
	 *
	 *@return	activeBank
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	private String getActiveBank() throws Exception {
		activeBank = "";
		String queryActiveBank = "SELECT *\r\n"
				+ "FROM (\r\n"
				+ "    SELECT ID, BANK, STATUS\r\n"
				+ "    FROM EO_RMS.TDC_BANK \r\n"
				+ "    WHERE BANK LIKE '%PRUEBA%'\r\n"
				+ "    AND STATUS = 'A'\r\n"
				+ "    ORDER BY DBMS_RANDOM.value\r\n"
				+ "    )\r\n"
				+ "WHERE ROWNUM = 1";
		System.out.println("Query: " + queryActiveBank);
		try {
			SQLUtil connection = getDataBaseConnection();
			SQLResult res = connection.executeQuery(queryActiveBank);
			if (!res.isEmpty()) {
				activeBank = res.getData(0, "ID");
				System.out.println("Active bank: " + activeBank);
			}else {
				throw new Exception("No se encontraron registros de banco de pruebas activo.");
			}
			connection.close();
		} catch (Exception e) {
			throw new Exception("No se pudo consultar banco de pruebas." + e);
		}
		return activeBank;
	}
	
	/**
	 *Valida si el BIN tiene bines hijo.
	 *
	 *@param	maxBin Bin a validar.
	 *@return	hasChild
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	private boolean binHasChild(int maxBin) throws Exception {
		boolean hasChild = false;
		String querygetChildBin= "SELECT BIN, STATUS\r\n"
				+ "FROM EO_RMS.TDC_BIN\r\n"
				+ "WHERE BIN LIKE '" + maxBin + "%'";
		System.out.println("BIN has Child: " + querygetChildBin);
		try {
			SQLUtil connection = getDataBaseConnection();
			SQLResult res = connection.executeQuery(querygetChildBin);
			if (!res.isEmpty()) {
				hasChild = !res.isEmpty();
			}else {
				hasChild = res.isEmpty();
			}
			connection.close();
		} catch (Exception e) {
			throw new Exception("No se pudo consultar BIN de pruebas." + e);
		}
		return hasChild;
	}
	
	/**
	 *Obtiene BIN al azar de banco con status activo para poderlo editar.
	 *
	 *@return	binToEdit
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public String getBin() throws Exception {
		String binToEdit = "";
		String queryActiveBank = "SELECT *\r\n"
				+ "FROM (\r\n"
				+ "    SELECT BIN, STATUS, PRODUCT\r\n"
				+ "    FROM EO_RMS.TDC_BIN\r\n"
				+ "    WHERE BANK_ID = '" + activeBank + "'\r\n"
				+ "    ORDER BY DBMS_RANDOM.VALUE\r\n"
				+ "    )\r\n"
				+ "WHERE ROWNUM = 1";
		System.out.println("Query: " + queryActiveBank);
		try {
			SQLUtil connection = getDataBaseConnection();
			SQLResult res = connection.executeQuery(queryActiveBank);
			if (!res.isEmpty()) {
				binToEdit = res.getData(0, "BIN");
				System.out.println("BIN: " + binToEdit);
			}else {
				throw new Exception("No se encontraron registros de banco de pruebas activo.");
			}
			connection.close();
		} catch (Exception e) {
			throw new Exception("No se pudo consultar banco de pruebas.");
		}
		return binToEdit;
	}
	
	/**
	 *Valida si el mensaje de guardado exitoso se muestra en pantalla.
	 *
	 *@return	True o False
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public boolean validateSuccesMessage() throws Exception {
		boolean isMessageDisplayed = false;
		try {
			u.waitWebElement(successMessage, 5, 1);
			WebElement element = u.getDriver().findElement(successMessage);
			if (element.isDisplayed()) {
				testCase.addScreenShotCurrentStep(u, "Guardado exitoso");
				isMessageDisplayed = element.isDisplayed();
			}else {
				testCase.addScreenShotCurrentStep(u, "Guardado");
				isMessageDisplayed = element.isDisplayed();
			}
		} catch (Exception e) {
			throw new Exception("No se encontro mensaje de guardado exitoso.");
		}
		return isMessageDisplayed;
	}
	
	/**
	 *Valida si el banco tiene BINes disponibles para crear.
	 *
	 *@return	True o False
	 *@author	Oliver Martinez
	 *@date 	23/06/2026
	 */
	public boolean hasAvailableBin() throws Exception {
		boolean isBinAvailable = false;
		String activeBank = getActiveBank();
		String queryAvailableBin = "SELECT \r\n"
				+ "    COUNT(BIN) AS BINES_UTILIZADOS,\r\n"
				+ "    (9999 - COUNT(BIN)) AS BINES_DISPONIBLES\r\n"
				+ "FROM EO_RMS.TDC_BIN \r\n"
				+ "WHERE BANK_ID = '" + activeBank + "'";
		System.out.println("QueryAvailableBin: " + queryAvailableBin);
		try {
			SQLUtil connection = getDataBaseConnection();
			SQLResult res = connection.executeQuery(queryAvailableBin);
			testCase.addQueryEvidenceCurrentStep(res);
			int availableBines = Integer.parseInt(res.getData(0, "BINES_DISPONIBLES"));
			isBinAvailable = availableBines > 0;
			testCase.addQueryEvidenceCurrentStep(res);
			connection.close();
		} catch (Exception e) {
			throw new Exception("No se pudo realizar la consulta.");
		}
		return isBinAvailable;
	}
	
	/**
	 *Selccionar opciones del banco
	 *
	 *@author	Gerardo Lopez
	 *@date 	01/07/2026
	 */
	public void selectBankOption() {
		try {
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Bancos");
			u.click(optionBancos);
			System.out.println("Se da clic en modulo bancos");
		} catch (Exception e) {
			System.out.println("No se logro dar clic en Opcion Bancos. \r\n" + e);
		}
	}
	
	/**
	 *Selccionar opciones del banco
	 *
	 *@author	Gerardo Lopez
	 *@date 	01/07/2026
	 */
	public void clickBtnCreateBank() {
		try {
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Boton Crear Nuevo Banco");
			u.click(btnCrearNuevoBanco);
			System.out.println("Se da clic en boton Crear nuevo banco");
		} catch (Exception e) {
			System.out.println("No se logro dar clic en el boton de Crear Nuevo Banco. \r\n" + e);
		}
	}
	
	/**
	 *Ingresar informacion de nuevo banco
	 *
	 *@author	Gerardo Lopez
	 *@date 	01/07/2026
	 */
	public void typeInfoNewBank(String nameBanco) {
	    try {
	        int numeroRandom = new Random().nextInt(9) + 1;
	        nombreBancoGenerado = nameBanco + numeroRandom;
	        
	        WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(newBankNameBox));
	        
	        u.getDriver().findElement(newBankNameBox).sendKeys(nombreBancoGenerado);
	        testCase.addScreenShotCurrentStep(u, "Nombre del nuevo banco: " + nombreBancoGenerado);
	        System.out.println("Se ingresa el nombre del nuevo banco: " + nombreBancoGenerado);

	    } catch (Exception e) {
	        System.out.println("No se encontro recuadro para ingresar nombre del nuevo banco. \r\n" + e);
	    }
	}
	
	/**
	 *Se da clic en boton de crear nuevo banco
	 *
	 *@author	Gerardo Lopez
	 *@date 	01/07/2026
	 */
	public void clickBtnAcceptNewBank() {
		try {
			u.hardWait(2);
			testCase.addScreenShotCurrentStep(u, "Boton Aceptar Crear Nuevo Banco");
			u.click(btnAcceptBankName);
			System.out.println("Se da clic en boton ACEPTAR para crear el nuevo banco");
		} catch (Exception e) {
			System.out.println("No se logro dar clic en el boton de Aceptar Crear Nuevo Banco. \r\n" + e);
		}
	}
	
	/**
	 * @Description Realiza multiples intentos de conexion a BD FCDASPFR 
	 * @Autor Gerardo Lopez
	 * @throws Exception
	 * @date 26/Mayo/2026
	 */
    private SQLUtil conectarConReintentos() throws Exception {
        int maxIntentos = 5;
        for (int intento = 1; intento <= maxIntentos; intento++) {
            try {
                SQLUtil db = new SQLUtil(
                    GlobalVariables.DB_HOST_FCDASPFR_QA,
                    GlobalVariables.DB_USER_FCDASPFR_QA,
                    GlobalVariables.DB_PASSWORD_FCDASPFR_QA
                );
                System.out.println("BD Conexión exitosa en el intento " + intento);
                return db;
            } catch (Exception eConn) {
                System.err.println("BD Intento " + intento + " fallido: " + eConn.getMessage());
                if (intento == maxIntentos) {
                    throw new Exception("No se pudo conectar a la BD tras " + maxIntentos + " intentos: " + eConn.getMessage());
                }
                try {
                    Thread.sleep(2000L * intento);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new Exception("Hilo interrumpido durante reintento de conexión a BD.");
                }
            }
        }
        throw new Exception("No se pudo establecer conexión a la BD.");
    }
    
    /**
     * Obtener conexion realizada en el metodo conectarConReintentos()
     *
     *@author	Oliver Martinez
     *@throws 	Exception Error de conexion a la base de datos.
     *@Date		06/25/2026
     */
    public SQLUtil getConnection() throws Exception {
    	SQLUtil connection = null;
    	try {
    		connection =  conectarConReintentos();
		} catch (InterruptedException e) {
			throw new Exception("No se pudo establecer conexión a la BD.");
		}
		return connection;
    }
    
	/**
	 *Se ingresa nombre de banco en la barra buscadora de Bancos
	 *
	 *@author	Gerardo Lopez
	 *@date 	01/07/2026
	 */
	public void typeBankSearchBar(String nameBanco) {
		try {
			u.getDriver().findElement(searchBarBanks).sendKeys(nameBanco);
			testCase.addScreenShotCurrentStep(u, "Busqueda de nombre del nuevo banco");
		} catch (Exception e) {
			System.out.println("No se encontro recuadro para ingresar nombre del nuevo banco. \r\n" + e );
		}
	}
	
	/**
	 *Click en boton de GO de barra buscadora de banco
	 *
	 *@author	Gerardo Lopez
	 *@date 	01/07/2026
	 */
	public void clickBtnGoSearchBar() {
		try {
			u.hardWait(2);
			u.click(btnGoSearchBar);
			testCase.addScreenShotCurrentStep(u, "Busqueda de nombre del nuevo banco");
		} catch (Exception e) {
			System.out.println("No se encontro pudo dar click en boton de buscar banco. \r\n" + e );
		}
	}
	
	/**
	 *Click en boton de Editar nuevo banco
	 *
	 *@author	Gerardo Lopez
	 *@date 	01/07/2026
	 */
	public void clickBtnEditBank() {
		try {
			u.hardWait(2);
			u.click(btnEditBank);
			testCase.addScreenShotCurrentStep(u, "Ingresar a edit de banco");
		} catch (Exception e) {
			System.out.println("No se logro dar clic en el boton de editar banco. \r\n" + e );
		}
	}
	
	/**
	 *Busqueda en BD del nuevo banco creado
	 *
	 *@return	dataBank
	 *@author	Gerardo Lopez
	 *@date 	01/07/2026
	 */
	public boolean searchNewDataBank() throws Exception {
	    boolean dataBank = false;
	    try {
	        String querySearchBank = "SELECT *\r\n"
	                + "FROM (\r\n"
	                + "        SELECT *\r\n"
	                + "        FROM EO_RMS.TDC_BANK\r\n"
	                + "        WHERE TRUNC(CREATION_DATE) = TRUNC(SYSDATE)\r\n"
	                + "        AND BANK = '" + nombreBancoGenerado + "')\r\n"
	                + "WHERE ROWNUM = 1";
	        SQLUtil dbDASRMS = getConnection();
	        SQLResult resBank = dbDASRMS.executeQuery(querySearchBank);

	        if (resBank.getRowCount() > 0) {
	            testCase.addQueryEvidenceCurrentStep(resBank);
	            dataBank = true;
	            System.out.println("Se encontro el nombre del nuevo banco agregado en BD");
	        } else {
	            testCase.addQueryEvidenceCurrentStep(resBank);
	            dataBank = false;
	            System.out.println("No se encontro el nombre del nuevo banco agregado en BD");
	        }
	        dbDASRMS.close();
	    } catch (Exception e) {
	        throw new Exception("Error en SELECT: " + e.getMessage());
	    }
	    return dataBank;
	}
	
	/**
	 *Metodo privado para guardar String del nombre del banco generado
	 *
	 *@author	Gerardo Lopez
	 *@date 	01/07/2026
	 */
	private String nombreBancoGenerado;
	
	/**
	 *Obtiene el nombre del ultimo banco creado en BD, basado en la fecha de creacion.
	 *Util cuando se necesita editar el banco creado en un test previo, sin depender
	 *de variables en memoria entre ejecuciones distintas.
	 *
	 *@return	ultimoBancoCreado
	 *@author	Gerardo Lopez
	 *@date 	13/07/2026
	 */
	public String getLastCreatedBank() throws Exception {
	    String ultimoBancoCreado = "";
	    String queryLastBank = "SELECT *\r\n"
	            + "FROM (\r\n"
	            + "        SELECT BANK, CREATION_DATE\r\n"
	            + "        FROM EO_RMS.TDC_BANK\r\n"
	            + "        ORDER BY ID DESC\r\n"
	            + "        )\r\n"
	            + "WHERE ROWNUM = 1";
	    System.out.println("Query ultimo banco creado: " + queryLastBank);
	    try {
	        SQLUtil connection = getConnection();
	        SQLResult res = connection.executeQuery(queryLastBank);
	        if (res.getRowCount() > 0) {
	            ultimoBancoCreado = res.getData(0, "BANK");
	            System.out.println("Ultimo banco creado: " + ultimoBancoCreado);
	        } else {
	            throw new Exception("No se encontro ningun banco creado el dia de hoy.");
	        }
	        connection.close();
	    } catch (Exception e) {
	        throw new Exception("No se pudo consultar el ultimo banco creado. " + e.getMessage());
	    }
	    return ultimoBancoCreado;
	}

	/**
	 *Obtiene el status actualmente seleccionado en el dropdown de edicion de banco.
	 *
	 *@return	statusActual	"A" o "I"
	 *@author	Gerardo Lopez
	 *@date 	13/07/2026
	 */
	public String getCurrentBankStatus() {
	    String statusActual = "";
	    try {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(dropListStatusBank));
	        WebElement statusDropdown = u.getDriver().findElement(dropListStatusBank);
	        Select dropList = new Select(statusDropdown);
	        statusActual = dropList.getFirstSelectedOption().getAttribute("value");
	        System.out.println("Status actual del banco: " + statusActual);
	    } catch (Exception e) {
	        System.out.println("No se logro obtener el status actual del banco. \r\n" + e);
	    }
	    return statusActual;
	}

	/**
	 *Cambia el status del banco en edicion entre Activo (A) e Inactivo (I).
	 *
	 *@param	status	Valor a seleccionar: "A" para Activo, "I" para Inactivo.
	 *@author	Gerardo Lopez
	 *@date 	13/07/2026
	 */
	public void selectBankStatus(String status) {
	    try {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(dropListStatusBank));
	        WebElement statusDropdown = u.getDriver().findElement(dropListStatusBank);
	        Select dropList = new Select(statusDropdown);
	        dropList.selectByValue(status);
	        testCase.addScreenShotCurrentStep(u, "Status del banco cambiado a: " + status);
	        System.out.println("Se selecciona status del banco: " + status);
	    } catch (Exception e) {
	        System.out.println("No se logro cambiar el status del banco. \r\n" + e);
	    }
	}

	/**
	 *Invierte el status actual del banco (A -> I, o I -> A).
	 *
	 *@return	nuevoStatus	El status al que se cambio.
	 *@author	Gerardo Lopez
	 *@date 	13/07/2026
	 */
	public String toggleBankStatus() {
	    String nuevoStatus = "";
	    try {
	        String statusActual = getCurrentBankStatus();
	        nuevoStatus = statusActual.equals("A") ? "I" : "A";
	        selectBankStatus(nuevoStatus);
	    } catch (Exception e) {
	        System.out.println("No se logro invertir el status del banco. \r\n" + e);
	    }
	    return nuevoStatus;
	}

	/**
	 *Hace clic en el boton para guardar los cambios del banco editado.
	 *
	 *@author	Gerardo Lopez
	 *@date 	13/07/2026
	 */
	public void saveBankChanges() {
	    try {
	        u.hardWait(2);
	        testCase.addScreenShotCurrentStep(u, "Guardar cambios banco");
	        u.click(btnAceptarCambio);
	        System.out.println("Se guardan los cambios del banco editado");
	        u.hardWait(5);
	    } catch (Exception e) {
	        System.out.println("No se logro dar clic en el boton de guardar cambios. \r\n" + e);
	    }
	}

	/**
	 *Valida en base de datos que el status del banco editado se haya actualizado correctamente.
	 *
	 *@param	bankName		Nombre del banco a validar.
	 *@param	statusEsperado	Status esperado tras la edicion: "A" o "I".
	 *@return	statusUpdated
	 *@author	Gerardo Lopez
	 *@date 	13/07/2026
	 */
	public boolean validateBankStatus(String bankName, String statusEsperado) throws Exception {
	    boolean statusUpdated = false;
	    try {
	        String queryValidateStatus = "SELECT *\r\n"
	                + "FROM (\r\n"
	                + "        SELECT BANK, STATUS\r\n"
	                + "        FROM EO_RMS.TDC_BANK\r\n"
	                + "        WHERE BANK = '" + bankName + "'\r\n"
	                + "        AND STATUS = '" + statusEsperado + "')\r\n"
	                + "WHERE ROWNUM = 1";
	        SQLUtil dbDASRMS = getConnection();
	        SQLResult resBank = dbDASRMS.executeQuery(queryValidateStatus);

	        if (resBank.getRowCount() > 0) {
	            testCase.addQueryEvidenceCurrentStep(resBank);
	            statusUpdated = true;
	            System.out.println("El status del banco se actualizo correctamente a: " + statusEsperado);
	        } else {
	            testCase.addQueryEvidenceCurrentStep(resBank);
	            statusUpdated = false;
	            System.out.println("El status del banco no coincide con el esperado: " + statusEsperado);
	        }
	        dbDASRMS.close();
	    } catch (Exception e) {
	        throw new Exception("Error en SELECT: " + e.getMessage());
	    }
	    return statusUpdated;
	}
	
	/**
	 *Cierra la sesion del usuario en el aplicativo Apex antes de finalizar el script.
	 *
	 *@author	Gerardo Lopez
	 *@date 	13/07/2026
	 */
	public void logout() {
	    try {
	        wait.until(ExpectedConditions.elementToBeClickable(btnLogout));
	        u.click(btnLogout);
	        testCase.addScreenShotCurrentStep(u, "Logout");
	        System.out.println("Se cierra sesion correctamente");
	    } catch (Exception e) {
	        System.out.println("No se logro cerrar sesion. \r\n" + e);
	    }
	}
	
}//Fin de OM