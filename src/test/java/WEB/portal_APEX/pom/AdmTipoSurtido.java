package WEB.portal_APEX.pom;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AdmTipoSurtido extends BasePage {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	private final By tableTitle = By.xpath("//*[text()='Loc Class - Tipo Surtido']");
	private final By editButton = By.xpath("//label[normalize-space()='Editar']/parent::div");
	private final By saveButton = By.xpath("//span[normalize-space()='Guardar']/parent::button");
	private final By locClassFirstRow = By.xpath("//div[@id='TIPO-SURTIDO_ig_grid_vc']//div[@class='a-GV-w-scroll']//table//tr[1]//td[3]");
	private final By cedisFirstRow = By.xpath("//div[@id='TIPO-SURTIDO_ig_grid_vc']//div[@class='a-GV-w-scroll']//table//tr[1]//td[2]");
	private final By tipoSurtidoFirstRow = By.xpath("//div[@id='TIPO-SURTIDO_ig_grid_vc']//div[@class='a-GV-w-scroll']//table//tr[1]//td[4]");
	private final By tipoSurtidoSecondRow = By.xpath("//div[@id='TIPO-SURTIDO_ig_grid_vc']//div[@class='a-GV-w-scroll']//table//tr[2]//td[4]");
	private final By tipoSurtidoThirdRow = By.xpath("//div[@id='TIPO-SURTIDO_ig_grid_vc']//div[@class='a-GV-w-scroll']//table//tr[3]//td[4]");
	private final By tipoSurtidoColumn = By.xpath("//div[@id='TIPO-SURTIDO_ig_grid_vc']//div[@class='a-GV-w-scroll']//table//td[4]");
	private final By allCedis = By.xpath("//div[@id='TIPO-SURTIDO_ig_grid_vc']//div[@class='a-GV-w-scroll']//table//tr//td[2]");
	private final By tipoSurtidoDropdown = By.xpath("//div[@class='a-GV-columnItemContainer u-vh']//select//option");
	private final By loadingSpinner = By.xpath("//span[@class='u-Processing-spinner']");
	private final By successMessage = By.xpath("//div[@id='t_Alert_Success']");
	private final By actionsButton = By.xpath("//span[normalize-space()='Acciones']/parent::button");
	private final By filterButton = By.xpath("//div[contains(@id, 'ig_toolbar_actions_button_menu')]/div[@class='a-Menu-content']//span[contains(.,'Filtro')]");
	private final By filterColumnDropdown = By.xpath("//select[contains(@id, '_ig_FD_COLUMN')]");
	private final By filterValueInput = By.xpath("//input[contains(@id, '_ig_FD_VALUE')]");
	private final By filterSaveButton = By.xpath("//button[contains(@class, 'ui-button--hot') and normalize-space()='Guardar']");
	private final By filterConfirmation = By.xpath("//div[contains(@id,'ig_report_settings')]//ul[contains(@class, 'a-IG-controls')]");
	private final By removeFilterButton = By.xpath("//div[contains(@id,'ig_report_settings')]//ul[contains(@class, 'a-IG-controls')]//span//button[@title='Remove Filtro']");
	private final By cedisColumnText = By.xpath("//div[@class='a-GV-w-scroll']//table[@role='presentation']//tbody//tr//td[2]");
	private final By locClassColumnText = By.xpath("//div[@class='a-GV-w-scroll']//table[@role='presentation']//tbody//tr//td[3]");
	private final By tipoSurtidoColumnText = By.xpath("//div[@class='a-GV-w-scroll']//table[@role='presentation']//tbody//tr//td[4]");
	
	public AdmTipoSurtido(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/**
	 * Retorna si el bot�n est� visible
	 *
	 * @author Edwin Chi
	 * @return Retorna si el bot�n est� visible
	 * @date 29/Octubre/2024
	 */
	public boolean isEditButtonDisplayed() {
		return u.isDisplayed(editButton);
	}

	/**
	 * Retorna si el bot�n est� visible
	 *
	 * @author Edwin Chi
	 * @return Retorna si el bot�n est� visible
	 * @date 29/Octubre/2024
	 */
	public boolean isSaveButtonDisplayed() {
		return u.isDisplayed(saveButton);
	}
	
	/**
	 * Click en la secci�n Adm Tipo Surtido
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void clickAdmTipoSurtido() {
		u.click(tableTitle);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}
	
	/**
	 * Retorna si el t�tutlo de la tabla est� visible
	 *
	 * @author Edwin Chi
	 * @return Retorna si el t�tutlo de la tabla est� visible
	 * @date 29/Octubre/2024
	 */
	public boolean isTableTitleDisplayed() throws Exception {
		testCase.addScreenShotCurrentStep("Screenshot");
		return u.isDisplayed(tableTitle);
	}
	
	/**
	 * Click en el bot�n Editar
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void clickEditButton() {
		u.click(editButton);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("Screenshot");
	}
	
	/**
	 * Retorna el valor del primer Loc Class en la tabla
	 *
	 * @author Edwin Chi
	 * @return Retorna el valor del primer Loc Class en la tabla
	 * @date 29/Octubre/2024
	 */
	public String getFirstLocClass() {
		return u.getText(locClassFirstRow).trim();
	}
	
	/**
	 * Retorna el valor del primer Cedis en la tabla
	 *
	 * @author Edwin Chi
	 * @return Retorna el valor del primer Cedis en la tabla
	 * @date 29/Octubre/2024
	 */
	public String getFirstCedisOperativo() {
		return u.getText(cedisFirstRow).trim();
	}
	
	/**
	 * Retorna el valor del primer Tipo Surtido en la tabla
	 *
	 * @author Edwin Chi
	 * @return Retorna el valor del primer Tipo Surtido en la tabla
	 * @date 29/Octubre/2024
	 */
	public String getFirstTipoSurtido() {
		return u.getText(tipoSurtidoFirstRow).trim();
	}

	/**
	 * Retorna el valor del segundo Tipo Surtido en la tabla
	 *
	 * @author Edwin Chi
	 * @return Retorna el valor del segundo Tipo Surtido en la tabla
	 * @date 29/Octubre/2024
	 */
	public String getSecondTipoSurtido() {
		return u.getText(tipoSurtidoSecondRow).trim();
	}

	/**
	 * Retorna el valor del tercer Tipo Surtido en la tabla
	 *
	 * @author Edwin Chi
	 * @return Retorna el valor del tercer Tipo Surtido en la tabla
	 * @date 29/Octubre/2024
	 */
	public String getThirdTipoSurtido() {
		return u.getText(tipoSurtidoThirdRow).trim();
	}	
	
	/**
	 * Editar el primer Loc Class de la tabla
	 *
	 * @author Edwin Chi
	 * @param locClass a ingresar en la tabla 
	 * @date 29/Octubre/2024
	 */
	public void editFirstLocClass(String locClass) {
		u.click(locClassFirstRow);
		u.hardWait(1);		
		pressKeys(u, locClass);
		u.hardWait(1);
		pressKeys(u, Keys.ENTER);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("Screenshot");
	}
	
	/**
	 * Editar el primer Cedis de la tabla
	 *
	 * @author Edwin Chi
	 * @param cedis a ingresar en la tabla 
	 * @date 29/Octubre/2024
	 */
	public void editFirstCedisOperativo(String cedis) {
		u.click(cedisFirstRow);
		u.hardWait(1);		
		pressKeys(u, cedis);
		u.hardWait(1);
		pressKeys(u, Keys.ENTER);
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("Screenshot");
	}
	
	/**
	 * Editar el primer Tipo Surtido de la tabla.
	 * Cambiar� a un Tipo Surtido al que se tenga actualmente.
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void editFirstTipoSurtido() {
		String initialTipoSurtido = getFirstTipoSurtido();
		u.click(tipoSurtidoFirstRow);
		u.hardWait(1);
		if(initialTipoSurtido.contains("Cajas")) {
			pressKeys(u, Keys.ARROW_DOWN);
		} else {
			pressKeys(u, Keys.ARROW_UP);
		}
		u.hardWait(1);
		pressKeys(u, Keys.ENTER);
		testCase.addScreenShotCurrentStep("Screenshot");	
	}
	
	/**
	 * Retorna el valor de todos los Cedis de la tabla en una lista objetos de tipo String
	 *
	 * @author Edwin Chi
	 * @return Retorna el valor de todos los Cedis de la tabla en una lista objetos de tipo String
	 * @date 29/Octubre/2024
	 */
	public List<String> getAllCedisText() {
		return u.getDriver()
		.findElements(allCedis)
		.stream()
		.map(ele -> ele.getText())
		.filter(ele -> ele.length() > 0)
		.collect(Collectors.toList());
	}
	
	/**
	 * Click en el primer Tipo Surtido
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void clickOnFirstTipoSurtido() {
		u.hardWait(1);
		u.click(tipoSurtidoFirstRow);
		u.hardWait(1);
	}
	
	/**
	 * Retorna el valor de todos los Tipo de Surtido de la tabla en una lista objetos de tipo String
	 *
	 * @author Edwin Chi
	 * @return Retorna el valor de todos los Tipo de Surtido de la tabla en una lista objetos de tipo String
	 * @date 29/Octubre/2024
	 */
	public List<String> getTipoSurtidoText() throws Exception {
		return u.getDriver().findElements(tipoSurtidoDropdown)
				.stream()
				.map(option -> option.getAttribute("value").trim()).collect(Collectors.toList());
	}

	/**
	 * Selecciona el Tipo Surtido de Cajas
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void selectTipoSurtidoCajas() {
		u.click(tipoSurtidoFirstRow);
		u.hardWait(1);
		u.click(tipoSurtidoFirstRow);
		pressKeys(u, Keys.HOME);
		u.hardWait(1);
		pressKeys(u, Keys.ENTER);
		testCase.addScreenShotCurrentStep("Screenshot");
	}

	/**
	 * Selecciona el Tipo Surtido de Nulo
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void selectTipoSurtidoNulo() {
		u.click(tipoSurtidoFirstRow);
		u.hardWait(1);
		u.click(tipoSurtidoFirstRow);
		pressKeys(u, Keys.HOME);
		pressKeys(u, Keys.ARROW_DOWN);
		u.hardWait(1);
		pressKeys(u, Keys.ENTER);
		testCase.addScreenShotCurrentStep("Screenshot");
	}

	/**
	 * Selecciona el Tipo Surtido de Piezas
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
    public void selectTipoSurtidoPiezas() {
		u.click(tipoSurtidoSecondRow);
		u.hardWait(1);
		u.click(tipoSurtidoSecondRow);
		pressKeys(u, Keys.HOME);
		pressKeys(u, Keys.ARROW_DOWN);
		pressKeys(u, Keys.ARROW_DOWN);
		u.hardWait(1);
		pressKeys(u, Keys.ENTER);
		testCase.addScreenShotCurrentStep("Screenshot");
    }

	/**
	 * Selecciona el Tipo Surtido de Refrigerados
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
    public void selectTipoSurtidoRefrigerados() {
		u.click(tipoSurtidoThirdRow);
		u.hardWait(1);
		u.click(tipoSurtidoThirdRow);
		pressKeys(u, Keys.HOME);
		pressKeys(u, Keys.ARROW_DOWN);
		pressKeys(u, Keys.ARROW_DOWN);
		pressKeys(u, Keys.ARROW_DOWN);
		u.hardWait(1);
		pressKeys(u, Keys.ENTER);
		testCase.addScreenShotCurrentStep("Screenshot");
    }

	/**
	 * Click en el bot�n Guardar
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void clickSaveButton(){
		u.click(saveButton);
		testCase.addScreenShotCurrentStep("Screenshot");
	}

	/**
	 * Retorna si la animaci�n de carga se ha ocultado, si no, espera a que lo haga.
	 *
	 * @author Edwin Chi
	 * @return Retorna si la animaci�n de carga se ha ocultado, si no, espera a que lo haga
	 * @date 29/Octubre/2024
	 */
	public boolean isLoadingSpinnerInvisible() throws Exception {
		u.hardWait(2);
		return esperarElementoInvisible(u, loadingSpinner);
	}

	/**
	 * Recarga la p�gina
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void reloadPage() {
		u.refresh();
	}

	/**
	 * Regresa si el mensaje de �xito es mostrado en pantalla
	 *
	 * @author Edwin Chi
	 * @return Regresa si el mensaje de �xito es mostrado en pantalla
	 * @date 29/Octubre/2024
	 */
    public boolean isSuccessMesageDisplayed() {
        boolean isSuccessMessageDisplayed = u.isDisplayed(successMessage);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		return isSuccessMessageDisplayed;
    }

	/**
	 * Retorna el valor de todos los Tipo de Surtido de la tabla en una lista objetos de tipo String
	 *
	 * @author Edwin Chi
	 * @return Retorna el valor de todos los Tipo de Surtido de la tabla en una lista objetos de tipo String
	 * @date 29/Octubre/2024
	 */
	public List<String> getAllTipoSurtidoText(){
		return u.getDriver().findElements(tipoSurtidoColumn)
			.stream()
			.map(tipoSurtido -> tipoSurtido.getText().trim()).collect(Collectors
			.toList());
	}

	/**
	 * Click en el bot�n de Actions
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void clickOnActionsButton() {
		u.click(actionsButton);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Click en el bot�n de Filtrar
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void clickOnFilterButton() {
		u.click(filterButton);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Seleccionar el tipo de columna a filtrar
	 *
	 * @author Edwin Chi
	 * @param dropDownText el nombre de la columna a filtrar ejemplo: Cedis Operativo, Tipo Surtido  
	 * @date 29/Octubre/2024
	 */
	public void selectColumn(String dropdownText) {
		Select columnSelect = new Select(u.getDriver().findElement(filterColumnDropdown));
		columnSelect.selectByVisibleText(dropdownText);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Escribe el valor a buscar en el filtro
	 *
	 * @author Edwin Chi
	 * @param value es el valor a buscar en el filtro, como un Cedis en espec�fico, la palabra Nulo etc.
	 * @date 29/Octubre/2024
	 */
	public void typeValue(String value) {
		u.sendKeys(filterValueInput, value);
		u.hardWait(2);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Click en el bot�n de Guardar en la ventana del filtro
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void clickOnSaveButtonFromFilter() throws Exception {
		u.click(filterSaveButton);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		esperarElementoInvisible(u, filterButton);
	}
	
	/**
	 * Retorna si el elemento que confirma el criterio de b�squeda es mostrado en pantalla
	 *
	 * @author Edwin Chi
	 * @return Retorna si el elemento que confirma el criterio de b�squeda es mostrado en pantalla
	 * @date 29/Octubre/2024
	 */
	public boolean isFilterConfirmationDisplayed() throws Exception{
		boolean isDisplayed = obtenerElementoVisible(u, filterConfirmation).isDisplayed();
		u.hardWait(3);
		return isDisplayed;
	}

	/**
	 * Retorna todos los CEDIS filtrados como parte del criterio de b�squeda.
	 *
	 * @author Edwin Chi
	 * @return Lista de texto con todos los CEDIS encontrados en la tabla
	 * @date 29/Octubre/2024
	 */
	public List<String> getFilteredCedisRecords() throws Exception {
		return u.listElements(cedisColumnText)
			.stream()
			.map(cedisElement -> cedisElement.getText().trim())
			.filter(cedisElement -> cedisElement.length() > 0).collect(Collectors
			.toList());
	}

	/**
	 * Click en el bot�n de cerrar para remover el filtro aplicado.
	 * �til si se desea realizar un filtro nuevo.
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void removeFilter() throws Exception {
		u.click(removeFilterButton);
		esperarElementoInvisible(u, removeFilterButton);
	}

	/**
	 * Retorna todos los Loc Class filtrados como parte del criterio de b�squeda.
	 *
	 * @author Edwin Chi
	 * @return Lista de texto con todos los Loc Class encontrados en la tabla
	 * @date 29/Octubre/2024
	 */
	public List<String> getFilteredLocClassRecords() {
		return u.listElements(locClassColumnText)
			.stream()
			.map(locClassElement -> locClassElement.getText().trim())
			.filter(locClassElement -> locClassElement.length() > 0).collect(Collectors
			.toList());
	}

	/**
	 * Retorna todos los Tipo Surtido filtrados como parte del criterio de b�squeda.
	 *
	 * @author Edwin Chi
	 * @return Lista de texto con todos los Tipo Surtido encontrados en la tabla
	 * @date 29/Octubre/2024
	 */
	public List<String> getFilteredTipoSurtidoRecords() {
		return u.listElements(tipoSurtidoColumnText)
			.stream()
			.map(tipoSurtidoElement -> tipoSurtidoElement.getText().trim())
			.filter(tipoSurtidoElement -> tipoSurtidoElement.length() > 0).collect(Collectors
			.toList());
	}
}
