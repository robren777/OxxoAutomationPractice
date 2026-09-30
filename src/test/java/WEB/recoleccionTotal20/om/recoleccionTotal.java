package WEB.recoleccionTotal20.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import exceptions.FrameworkException;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Clase para manejo de portal de recoleccion en tiendas.
 * 
 * @author Roberto Renteria
 * @date 02/Agosto/2024
 */
public class recoleccionTotal {

	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	private WebDriver driver;
	
	//Objects
	By botonAvanzado = By.xpath("//*[@id=\"details-button\"]");
	By linkContinuar = By.xpath("//*[@id=\"proceed-link\"]");
	By inputNombreDeUsuario = By.xpath("/html/body/div/div[1]/div/form/div[1]/input");
	By inputContrasena = By.xpath("/html/body/div/div[1]/div/form/div[2]/input");
	By botonInciarSesion = By.xpath("/html/body/div/div[1]/div/form/button[1]");
	
	By portalEfectivo = By.linkText("PORTAL DE EFECTIVO");
	
	By cmbTurno = By.xpath("//*[@id='mat-select-value-1']");
	By cmbDiurno = By.xpath("//span[contains(text(),'Diurno')]");
	By cmbHoraInicio = By.xpath("//*[@id='mat-select-2']");
	By cmbHoraInicioValue = By.xpath("//span[contains(text(),'06:00')]");
	By cmbHoraFin = By.xpath("//*[@id='mat-select-4']");
	By cmbHoraFinValue = By.xpath("//span[contains(text(),'07:00')]");
	By cmbRegion = By.xpath("//*[@id='mat-select-6']");
	By cmbRegionValue = By.xpath("//span[contains(text(),'10VCZ')]");
	By cmbPlaza = By.xpath("//*[@id='mat-select-8']");
	By cmbPlazaValue = By.xpath("//span[contains(text(),'10VCZ Cordoba')]");
	By cmbTienda = By.xpath("//*[@id='mat-input-0']");
	By cmbTiendaValue = By.xpath("//span[contains(text(),'50DN8-21 de Mayo VER')]");
	By btnGuardar = By.id("mostrar");
	By btnCancelar = By.id("mostrarCancelar");
	By btnActualizar = By.id("mostrarUpdate");
	By cmbPages = By.id("mat-select-10");
	By cmbPagesValue = By.xpath("//span[contains(text(),'100')]");
	By gridTiendaField = By.xpath("//td[contains(text(),'50DN8') and @class='mat-mdc-cell mdc-data-table__cell cdk-cell cdk-column-tienda mat-column-tienda ng-star-inserted']");
	By btnEliminar = By.id("btnEliminar");
	By lblBusqueda = By.xpath("//mat-label[contains(text(),'Búsqueda:')]");
	By txtBusqueda = By.id("mat-input-1");
	By cmbHoraInicioDisabled = By.xpath("//div[@class='mat-mdc-text-field-wrapper mdc-text-field ng-tns-c1205077789-2 mdc-text-field--filled mdc-text-field--disabled']");
	By cmbHoraFinDisabled = By.xpath("//div[@class='mat-mdc-text-field-wrapper mdc-text-field ng-tns-c1205077789-4 mdc-text-field--filled mdc-text-field--disabled']");
	
public recoleccionTotal(SeleniumUtil u, TestCase testCase) {
	this.u = u;
	this.driver = u.getDriver();
	this.wait = new WebDriverWait(driver, Duration.ofSeconds(40));
	this.testCase = testCase;
}

/**
 * Realiza el login al portal AuthFemco
 * @param user - usuario de login
 * @param ps - contraseña del usuario de login
 *@Author Roberto Renteria
 */
public void ingresarAuthFemco(String user, String ps) {
	String url = "https://fcportalqa.femcom.net:8443/AuthFemcoProx/login\"";
    try {
        u.getDriver().manage().deleteAllCookies();
        testCase.addTextEvidenceCurrentStep(url);
        u.get(url);
        u.getDriver().manage().window().maximize();
        u.click(botonAvanzado);
        u.click(linkContinuar);
        u.getDriver().navigate().refresh();
        wait.until(ExpectedConditions.elementToBeClickable(inputNombreDeUsuario));
        testCase.addScreenShotCurrentStep("");
        u.sendKeys(inputNombreDeUsuario, user);
        u.sendKeys(inputContrasena, ps);
        u.click(botonInciarSesion);
    } catch (Exception e) {
        System.out.println(" -> " + e);
        this.u.close();
        throw new AssertionError("No se pudo ingresar a: " + url);
    }

}

/**
 * Realiza click en Portal Efectivo para entrar al módulo
 *@Author Roberto Renteria
 */
public void clickPortalEfectivo() {
	 wait.until(ExpectedConditions.elementToBeClickable(portalEfectivo));
     testCase.addScreenShotCurrentStep("");
     u.click(portalEfectivo);
}

/**
 * Selecciona turno Diurno
 *@Author Roberto Renteria
 */
public void selectTurno() {
	wait.until(ExpectedConditions.elementToBeClickable(cmbTurno));
	u.click(cmbTurno);
	u.click(cmbDiurno);
}

/**
 * Selecciona horario de inicio
 *@Author Roberto Renteria
 */
public void selectHorarioInicio() {
	wait.until(ExpectedConditions.elementToBeClickable(cmbHoraInicio));
	u.click(cmbHoraInicio);
	u.click(cmbHoraInicioValue);
}

/**
 * Selecciona horario fin
 *@Author Roberto Renteria
 */
public void selectHorarioFin() {
	wait.until(ExpectedConditions.elementToBeClickable(cmbHoraFin));
	u.click(cmbHoraFin);
	u.hardWait(3);
	u.click(cmbHoraFinValue);
}

/**
 * Selecciona la region
 * @param region - region a seleccionar en la lista
 *@Author Roberto Renteria
 */
public void selectRegion(String region) {
	try {
	cmbRegionValue = By.xpath("//span[contains(text(),'"+region+"')]");
	wait.until(ExpectedConditions.elementToBeClickable(cmbRegion));
	u.click(cmbRegion);
	u.hardWait(3);
	u.click(cmbRegionValue);
	}catch(Exception e) {
		throw new AssertionError("No se encontró la region: " + region + ", favor de ingresar en el data provider uno relacionado al usuario" ,e);
	}
}

/**
 * Selecciona la plaza
 * @param plaza - plaza a seleccionar en la lista
 *@Author Roberto Renteria
 */
public void selectPlaza(String plaza) {
	try {
	cmbPlazaValue = By.xpath("//span[contains(text(),'"+plaza+"')]");
	wait.until(ExpectedConditions.elementToBeClickable(cmbPlaza));
	u.click(cmbPlaza);
	u.hardWait(3);
	u.click(cmbPlazaValue);
	}catch(Exception e) {
		throw new AssertionError("No se encontró la plaza: " + plaza + ", favor de ingresar en el data provider uno relacionado al usuario" ,e);
	}
}

/**
 * Selecciona la plaza
 * @param tienda - tienda a seleccionar en la lista
 *@Author Roberto Renteria
 */
public void selectTienda(String tienda) {
	try {
	cmbTiendaValue = By.xpath("//span[contains(text(),'"+tienda+"')]");
	wait.until(ExpectedConditions.elementToBeClickable(cmbTienda));
	u.click(cmbTienda);
	u.hardWait(3);
	u.click(cmbTiendaValue);
	}catch(Exception e) {
		throw new AssertionError("No se encontró la tienda: " + tienda + ", favor de ingresar en el data provider uno relacionado al usuario" ,e);
	}
}

/**
 * Realiza click en el botón guardar
 *@Author Roberto Renteria
 */
public void clickBtnGuardar() {
	wait.until(ExpectedConditions.elementToBeClickable(btnGuardar));
	u.click(btnGuardar);
	u.hardWait(5);
}

/**
 * Muestra las 100 páginas de la tabla
 *@Author Roberto Renteria
 */
public void showAllPages() {
	wait.until(ExpectedConditions.elementToBeClickable(cmbPages));
	u.click(cmbPages);
	u.hardWait(2);
	u.click(cmbPagesValue);
}

/**
 * Revisa si una tienda ya existe en la tabla para borrarla y poder registrarla nuevamente
 * @param tiendaDel - tienda a buscar a 5 digitos ejemplo: 50UAA
 *@Author Roberto Renteria
 */
public void validateTiendaToDelete(String tiendaDel) {
	try {
		gridTiendaField = By.xpath("//td[contains(text(),'"+tiendaDel+"') and @class='mat-mdc-cell mdc-data-table__cell cdk-cell cdk-column-tienda mat-column-tienda ng-star-inserted']");
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(gridTiendaField));
		u.click(gridTiendaField);
		u.hardWait(4);
		u.click(btnEliminar);
		u.hardWait(5);
	} catch (Exception e) {
		System.out.println("No existe turno previamente creado,: " + e);
	}
}

/**
 * Revisa si una tienda existe en el grid y la selecciona
 * @param tienda - tienda a seleccionar a 5 digitos ejemplo: 50UAA
 *@Author Roberto Renteria
 */
public void selectRegistroGrid(String tienda) {
	try {
		gridTiendaField = By.xpath("//td[contains(text(),'"+tienda+"') and @class='mat-mdc-cell mdc-data-table__cell cdk-cell cdk-column-tienda mat-column-tienda ng-star-inserted']");
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(gridTiendaField));
		u.click(gridTiendaField);
		testCase.addScreenShotCurrentStep("");
		u.hardWait(4);
	} catch (Exception e) {
		throw new AssertionError("No se encontró en el grid la tienda: " + tienda,e);
	}
}

/**
 * Valida si se encuentran activos los campos Turno, Hora Ini, Hora Fin y los botones Actualizar y Cancelar
 *@Author Roberto Renteria
 */
public void validateActiveField() {
	try {
	u.hardWait(6);
	driver.findElement(cmbTurno).isEnabled();
	driver.findElement(cmbHoraInicio).isEnabled();
	driver.findElement(cmbHoraFin).isEnabled();
	driver.findElement(btnActualizar).isEnabled();
	driver.findElement(btnCancelar).isEnabled();
	testCase.addScreenShotCurrentStep("");
	}catch(Exception e) {
		throw new AssertionError("No se encontró habilitado algun campo: Turno, Hora Inicio, Hora Fin, boton Actualizar o boton Cancelar",e);
	}
}

/**
 * Valida que no se encuentran activos los campos Hora Ini, Hora Fin y los botones Actualizar y Cancelar
 *@Author Roberto Renteria
 */
public void validateInactiveField() {
	
	u.hardWait(5);
	
	try {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.presenceOfElementLocated(cmbHoraInicioDisabled));
		System.out.println("correcto, no está habilitado el campo hora inicio");
		
		}catch(Exception e) {
			throw new AssertionError("Error, se encontró habilitado el campo hora inicio");
		}
	
	try {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.presenceOfElementLocated(cmbHoraFinDisabled));
		System.out.println("correcto, no está habilitado el campo hora fin");
		
		}catch(Exception e) {
			throw new AssertionError("Error, se encontró habilitado el campo hora fin");
		}
	
	
	try {
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
	wait.until(ExpectedConditions.presenceOfElementLocated(btnActualizar));
	throw new AssertionError("Error, se encontró el botón Actualizar después de presionar Cancelar");
	
	}catch(Exception e) {
		System.out.println("correcto, no existe el botón Actualizar");
		testCase.addScreenShotCurrentStep("");
	}
	
}

/**
 * Da clic en botón Cancelar
 *@Author Roberto Renteria
 */
public void clickBtnCancelar() {
	wait.until(ExpectedConditions.elementToBeClickable(btnCancelar));
	u.click(btnCancelar);
	testCase.addScreenShotCurrentStep("");
	u.hardWait(5);
}

/**
 * Selecciona todos los campos del formulario
 * @param region - region a seleccionar
 * @param plaza - plaza a seleccionar
 * @param tienda - tienda a seleccionar
 *@Author Roberto Renteria
 */
public void fillAllFields(String region, String plaza, String tienda) {
	this.selectTurno();
	this.selectHorarioInicio();
	this.selectHorarioFin();
	this.selectRegion(region);
	this.selectPlaza(plaza);
	this.selectTienda(tienda);
	testCase.addScreenShotCurrentStep("");
}

/**
 * Regresa los primero 5 dígitos del nombre de la tienda mostrado en el portal
 * @param store - nombre de tienda largo para regresar los primeros 5 dígitos
 *@Author Roberto Renteria
 */
public String getCR(String store) {
	return store.substring(0,5);
}

/**
 * Busca una tienda en el campo búsqueda y valida que exista
 * @param toSearch - tienda a buscar a 5 digitos ejemplo: 50UAA
 *@Author Roberto Renteria
 */
public void busqueda(String toSearch) throws FrameworkException {
	wait.until(ExpectedConditions.elementToBeClickable(lblBusqueda));
	u.click(lblBusqueda);
	u.hardWait(2);
	u.clear(txtBusqueda);
	u.sendKeys(txtBusqueda,toSearch);
	u.hardWait(2);
	try {
		gridTiendaField = By.xpath("//td[contains(text(),'"+toSearch+"') and @class='mat-mdc-cell mdc-data-table__cell cdk-cell cdk-column-tienda mat-column-tienda ng-star-inserted']");
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(gridTiendaField));
		u.highLight(gridTiendaField);
		testCase.addScreenShotCurrentStep("");
	} catch (Exception e) {
		throw new FrameworkException("No se encontró en el grid la tienda: " + toSearch, e);
	}
	
}
}
