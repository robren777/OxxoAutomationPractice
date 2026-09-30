package EBS.util.pageObjectModel;

import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Clase con los locators y acciones de la pagina de "EBS Plantilla de
 * proveedores"
 *
 * @author JesusH@hexaware.com
 * @version 1.0
 * @since 5/23/2022
 */
public class EBSPlantillaDeProveedores {
	SeleniumUtil u;
	TestCase testCase;

	// Generacion del constructor

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author JesusH@hexaware.com
	 */
	public EBSPlantillaDeProveedores(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By tipo = By.xpath("//select[@id='choiceTipo']");
	By nivel = By.xpath("//select[@id='choiceNivel']");
	By tipoDeTasa = By.id("choiceTipoTasa");
	By tipoDePersona = By.id("choiceTipoPersona");
	By btnLimpiar = By.xpath("//button[@id='btnLimpiar']");
	By btnIr = By.xpath("//button[@id='btnIr']");
	By btnExportar = By
			.xpath("//div[@id='oafusercontent']//button[@id='btnCrear']/preceding-sibling::button[text()='Exportar']");
	By btnCrear = By.xpath("//div[@id='oafusercontent']//button[@id='btnCrear']");
	By separarTabla = By.xpath("//div[@id='oafusercontent']//a[@title='Separar Tabla: ']");
	By recargar = By.xpath("//div[@id='oafusercontent']//a[@id='tblProveedorRN:refreshIcon']");
	By defTabla = By.xpath("//div[@id='oafusercontent']//a[@id='settings:tblProveedorRN']");
	By labelTipoDeTasa = By.xpath("//span[@class='OraPromptText OraPromptSpaceBig'][normalize-space()='Tipo de Tasa']");
	By labelTipoDePersona = By
			.xpath("//span[@class='OraPromptText OraPromptSpaceBig'][normalize-space()='Tipo de Persona']");
	By labelTipo = By.xpath("//span[@class='OraPromptText OraPromptSpaceBig'][normalize-space()='Tipo']");
	By labelNivel = By.xpath("//span[@class='OraPromptText OraPromptSpaceBig'][normalize-space()='Nivel']");
	By columnTipoDePersona = By.xpath("//*[@id='sortableHeader7'][contains(text(),'Tipo Persona')]");
	By columnTipoDeTasa = By.xpath("//*[@id='sortableHeader6'][contains(text(),'Tipo Tasa')]");
	By valueTipoDePersona = By.xpath("//table[@id='tblProveedorRN:Content']/tbody/tr/td[4]/span");
	By valueTipoDeTasa = By.xpath("//table[@id='tblProveedorRN:Content']/tbody/tr/td[5]/span");
	By actualizar = By.xpath("//a[@id='tblProveedorRN:imgActualizar:0']");
	By columnNivel = By.xpath("//*[@id='sortableHeader2'][contains(text(),'Nivel')]");
	By columnTipoProveedor = By.xpath("//*[@id='sortableHeader1'][contains(text(),'Tipo Proveedor')]");
	By columnPlaza = By.xpath("//*[@id='sortableHeader3'][contains(text(),'Plaza')]");
	By columnActualizar = By.xpath("//*[@id='sortableHeader4'][contains(text(),'Actualizar')]");
	By columnEliminar = By.xpath("//*[@id='sortableHeader5'][contains(text(),'Eliminar')]");

	/**
	 * Busca y hace un remarcado de un dropdown
	 *
	 * @param dropDown Nombre (label) del dropdown a verificar
	 * @return True or False si el elemento es mostrado en la pagina
	 * @author JesusH@hexaware.com
	 */
	public Boolean dropdownIsDisplayed(String dropDown) {
		switch (dropDown) {
		case "tipo de persona":
			u.highLight(tipoDePersona);
			testCase.addScreenShotCurrentStep(u, "tipoDePersona");
			return u.highLight(tipoDePersona).isDisplayed();
		case "tipo de tasa":
			u.highLight(tipoDeTasa);
			testCase.addScreenShotCurrentStep(u, "tipoDeTasa");
			return u.highLight(tipoDeTasa).isDisplayed();
		case "nivel":
			u.highLight(nivel);
			testCase.addScreenShotCurrentStep(u, "nivel");
			return u.highLight(nivel).isDisplayed();
		case "tipo":
			u.highLight(tipo);
			testCase.addScreenShotCurrentStep(u, "tipo");
			return u.highLight(tipo).isDisplayed();
		}
		return null;

	}

	/**
	 * Busca y hace un remarcado de la etiqueda de un filtro
	 *
	 * @param filtro Etiqueta del filtro que se desea obtener su texto
	 * @return El texto extraido del elemento label, dicho texto se convierte a
	 *         minusculas
	 * @author JesusH@hexaware.com
	 */
	public String getFilterLabelText(String filtro) {
		switch (filtro) {
		case "tipo de persona":
			u.highLight(labelTipoDePersona);
			testCase.addScreenShotCurrentStep(u, "labelTipoDePersona");
			return u.getText(labelTipoDePersona).toLowerCase(Locale.ROOT);
		case "tipo de tasa":
			u.highLight(labelTipoDeTasa);
			testCase.addScreenShotCurrentStep(u, "labelTipoDeTasa");
			return u.getText(labelTipoDeTasa).toLowerCase(Locale.ROOT);
		case "nivel":
			u.highLight(labelNivel);
			testCase.addScreenShotCurrentStep(u, "labelNivel");
			return u.getText(labelNivel).toLowerCase(Locale.ROOT);
		case "tipo":
			u.highLight(labelTipo);
			testCase.addScreenShotCurrentStep(u, "labelTipo");
			return u.getText(labelTipo).toLowerCase(Locale.ROOT);
		}
		return null;

	}

	/**
	 * Busca y hace un remarcado de una columna ingresada
	 *
	 * @param column Nombre de la columna (necesita estan en minusculas)
	 * @return El texto extraido de la columna, dicho texto se convierte a
	 *         minusculas
	 * @author JesusH@hexaware.com
	 */
	public String getColumnTitle(String column) {
		switch (column) {
		case "tipo persona":
			u.highLight(columnTipoDePersona);
			testCase.addScreenShotCurrentStep(u, "columnTipoDePersona");
			return u.getText(columnTipoDePersona).toLowerCase(Locale.ROOT);
		case "tipo tasa":
			u.highLight(columnTipoDeTasa);
			testCase.addScreenShotCurrentStep(u, "columnTipoDeTasa");
			return u.getText(columnTipoDeTasa).toLowerCase(Locale.ROOT);
		case "nivel":
			u.highLight(columnNivel);
			testCase.addScreenShotCurrentStep(u, "columnNivel");
			return u.getText(columnNivel).toLowerCase(Locale.ROOT);
		case "plaza":
			u.highLight(columnPlaza);
			testCase.addScreenShotCurrentStep(u, "columnPlaza");
			return u.getText(columnPlaza).toLowerCase(Locale.ROOT);
		case "tipo proveedor":
			u.highLight(columnTipoProveedor);
			testCase.addScreenShotCurrentStep(u, "columnTipoProveedor");
			return u.getText(columnTipoProveedor).toLowerCase(Locale.ROOT);
		case "actualizar":
			u.highLight(columnActualizar);
			testCase.addScreenShotCurrentStep(u, "columnActualizar");
			return u.getText(columnActualizar).toLowerCase(Locale.ROOT);
		case "eliminar":
			u.highLight(columnEliminar);
			testCase.addScreenShotCurrentStep(u, "columnEliminar");
			return u.getText(columnEliminar).toLowerCase(Locale.ROOT);
		}
		return null;
	}

	/**
	 * Busca y hace un remarcado de la columna
	 *
	 * @param column Nombre de la columna (necesita estan en minusculas)
	 * @return True or False si la columna es mostrada
	 * @author JesusH@hexaware.com
	 */
	public Boolean columnIsDisplayed(String column) {
		switch (column) {
		case "tipo persona":
			u.highLight(columnTipoDePersona);
			testCase.addScreenShotCurrentStep(u, "columnTipoDePersona");
			return u.highLight(columnTipoDePersona).isDisplayed();
		case "tipo tasa":
			u.highLight(columnTipoDeTasa);
			testCase.addScreenShotCurrentStep(u, "columnTipoDeTasa");
			return u.highLight(columnTipoDeTasa).isDisplayed();
		case "nivel":
			u.highLight(columnNivel);
			testCase.addScreenShotCurrentStep(u, "columnNivel");
			return u.highLight(columnNivel).isDisplayed();
		case "plaza":
			u.highLight(columnPlaza);
			testCase.addScreenShotCurrentStep(u, "columnPlaza");
			return u.highLight(columnPlaza).isDisplayed();
		case "tipo proveedor":
			u.highLight(columnTipoProveedor);
			testCase.addScreenShotCurrentStep(u, "columnTipoProveedor");
			return u.highLight(columnTipoProveedor).isDisplayed();
		case "actualizar":
			u.highLight(columnActualizar);
			testCase.addScreenShotCurrentStep(u, "columnActualizar");
			return u.highLight(columnActualizar).isDisplayed();
		case "eliminar":
			u.highLight(columnEliminar);
			testCase.addScreenShotCurrentStep(u, "columnEliminar");
			return u.highLight(columnEliminar).isDisplayed();
		}
		return null;

	}

	/**
	 * Selecciona el tipo en el dropdown "Tipo" del formulario "Plantillas"
	 *
	 * @param tipoS el nombre (visibleText) del tipo a elegir.
	 * @author edwin.ramirez@neoris.com
	 */
	public void selectTipo(String tipoS) {
		u.waitForLoadPage();
		u.highLight(tipo);
		u.click(tipo);
		Select optT = new Select(u.getDriver().findElement(tipo));
		optT.selectByVisibleText(tipoS);

	}

	/**
	 * Selecciona el nivel en el dropdown "Nivel" del formulario "Plantillas"
	 *
	 * @param nivelOpt el nombre (visibleText) del nivel a elegir.
	 * @author edwin.ramirez@neoris.com
	 */
	public void selectNivel(String nivelOpt) {
		u.highLight(nivel);
		Select opt = new Select(u.getDriver().findElement(nivel));
		switch (nivelOpt) {
		case "Proveedor":
			opt.selectByIndex(1);
			break;
		case "Sucursal":
			opt.selectByIndex(2);
			break;
		}
	}

	/**
	 * Da click al boton de "Ir" y realiza la consulta
	 *
	 * @author edwin.ramirez@neoris.com
	 */
	public void clickIr() {
		u.highLight(btnIr);
		u.click(btnIr);
	}

	public void clickEditar() {
		u.highLight(actualizar);
		u.click(actualizar);
	}

	public String getTipoPersona() {
		String textTipoPersona = u.getText(valueTipoDePersona);
		return textTipoPersona;
	}

	public String getTipoTasa() {
		String textTipoTasa = u.getText(valueTipoDeTasa);
		return textTipoTasa;
	}

	/**
	 * Seleccionar el tipo de tasa
	 *
	 * @param tasaOpt el nombre (visibleText) del "Tipo de Tasa" a elegir.
	 * @author JesusH@hexaware.com
	 */
	public void selectTipoTasa(String tasaOpt) {
		u.highLight(tipoDeTasa);
		Select opt = new Select(u.getDriver().findElement(tipoDeTasa));
		switch (tasaOpt.toLowerCase(Locale.ROOT)) {
		case "f":
			opt.selectByIndex(1);
			break;
		case "i":
			opt.selectByIndex(2);
			break;
		case "":
			opt.selectByIndex(0);
			break;
		}
	}

	/**
	 * Seleccionar el tipo de persona
	 *
	 * @param personaOpt el nombre (visibleText) del "Tipo de Persona" a elegir.
	 * @author JesusH@hexaware.com
	 */
	public void selectTipoPersona(String personaOpt) {
		u.highLight(tipoDePersona);
		Select opt = new Select(u.getDriver().findElement(tipoDePersona));
		switch (personaOpt.toLowerCase(Locale.ROOT)) {
		case "f":
			opt.selectByIndex(1);
			break;
		case "m":
			opt.selectByIndex(2);
			break;
		case "r":
			opt.selectByIndex(3);
			break;
		case "":
			opt.selectByIndex(0);
			break;
		}
	}

}
