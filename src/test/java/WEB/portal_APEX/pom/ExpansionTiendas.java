package WEB.portal_APEX.pom;


import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class ExpansionTiendas extends BasePage {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	private final Duration TIMEOUT = Duration.ofSeconds(60);

	private final By tabCargarArchivo = By.xpath("//span[normalize-space()='Paso 1 .- Cargar Archivo']/parent::button");
	private final By tabRefresh = By.xpath("//span[normalize-space()='Paso 2.- Refresh']/parent::button");
	private final By tabProcesaArchivo = By.xpath("//span[normalize-space()='Paso 2.- Procesa Archivo']/parent::button");
	private final By tabDescargaLog = By.xpath("//span[normalize-space()='Paso 3.- Descarga Log']/parent::button");
	private final By accionesButton = By.xpath("//button[.='Acciones']");
	private final By statusLineaLogColumn = By.xpath("//table[contains(@aria-label,'Region = Reporte Log')]//tbody//tr//td[4]");
	private final By descargaLocExitButton = By.xpath("//span[@class='ui-button-icon ui-icon ui-icon-closethick']");
	private final By botonCargarArchivo = By.xpath("//span[normalize-space()='Cargar Archivo']/parent::button");
	private final By inputArchivo = By.xpath("//input[@id='P44_BLOB_CONTENT']");
	private final By tablaRefreshUsuario = By.xpath("//table[@id='report_table_412']//tbody//tr[1]//td[1]");
	private final By tablaRefreshNombreArchivo = By.xpath("//table[@id='report_table_412']//tbody//tr[1]//td[2]");
	private final By tablaRefreshTipo = By.xpath("//table[@id='report_table_412']//tbody//tr[1]//td[3]");
	private final By tablaRefreshStatus = By.xpath("//table[@id='report_table_412']//tbody//tr[1]//td[4]");
	private final By tablaTotalError = By.xpath("//td[@headers='TOTAL_ERROR']");
	private final By tablaRefreshCargadoStatus = By.xpath("//table[@id='report_table_412']//tbody//tr[1]//td[4 and normalize-space()='CARGADO']");
	private final By tablaRefreshProcesadoStatus = By.xpath("//table[@id='report_table_412']//tbody//tr[1]//td[4 and normalize-space()='PROCESADO']");
	private final By okButtonFromModal = By.xpath("//button[normalize-space()='OK']");
	private final By buscarButton = By.xpath("//span[normalize-space()='Buscar']/parent::button");
	private final By fechaRecordsFromExpansionDeTiendaCalculo = By.xpath("//div[@class='a-GV-w-scroll']//table[@role='presentation']//tbody//tr//td[2]");
	private final By cedisRecordsFromExpansionDeTiendaCalculo = By.xpath("//div[@class='a-GV-w-scroll']//table[@role='presentation']//tbody//tr//td[3]");
	private final By totalTiendasRecordsFromExpansionDeTiendaCalculo = By.xpath("//div[@class='a-GV-w-scroll']//table[@role='presentation']//tbody//tr//td[4]");
	private final By cajasRecordsFromExpansionDeTiendaCalculo = By.xpath("//div[@class='a-GV-w-scroll']//table[@role='presentation']//tbody//tr//td[5]");
	private final By piezasRecordsFromExpansionDeTiendaCalculo = By.xpath("//div[@class='a-GV-w-scroll']//table[@role='presentation']//tbody//tr//td[6]");
	private final By refrigeradosRecordsFromExpansionDeTiendaCalculo = By.xpath("//div[@class='a-GV-w-scroll']//table[@role='presentation']//tbody//tr//td[7]");
	private final By loadingSpinner = By.xpath("//span[@class='u-Processing-spinner']");
	private final By descargarButton = By.xpath("//span[normalize-space()='Descarga']/parent::button");
	private final By csvOption = By.xpath("//span[@class='a-IG-dialog-iconList-label']/parent::span");
	private final By downloadButton = By.xpath("//button[normalize-space()='Descargar']");
	private final By successDownloadMessage = By.xpath("//div[@id='t_Alert_Success']");
	private final By editButton = By.xpath("//label[normalize-space()='Editar']");
	private final By saveButton = By.xpath("//span[normalize-space()='Guardar']");
	private final By totalTiendasInput = By.xpath("//div[@aria-label='Expansion de Tiendas Calculos IG']//table//tbody//tr[1]//td[4]");
	private final By actionsButton = By.xpath("//span[normalize-space()='Actions']/parent::button");
	private final By filterButton = By.xpath("//div[contains(@id, 'ig_toolbar_actions_button_menu')]/div[@class='a-Menu-content']//span[contains(.,'Filter')]");
	private final By filterColumnDropdown = By.xpath("//select[contains(@id, '_ig_FD_COLUMN')]");
	private final By filterValueInput = By.xpath("//input[contains(@id, '_ig_FD_VALUE')]");
	private final By filterSaveButton = By.xpath("//button[contains(@class, 'ui-button--hot') and normalize-space()='Save']");
	private final By filterConfirmation = By.xpath("//div[contains(@id,'ig_report_settings')]//ul[contains(@class, 'a-IG-controls')]");
	private final By fechaInicioInput = By.xpath("//input[@id='P41_FECHA_INICIO|input']");
	private final By fechaFinInput = By.xpath("//input[@id='P41_FECHA_FIN|input']");
	private final By cedisDropdown = By.xpath("//select[@id='P41_CEDIS']");
	private final By cargaArchivoExpansionDeTiendasLog = By.xpath("//h2[contains(.,'Carga Archivo Expansi�n de Tiendas LOG')]");
	private final By expansionDeTiendasCalculo = By.xpath("//h2[contains(.,'Expansi�n de Tienda C�lculo')]");
	private final By processMessage = By.xpath("//div[@role='alertdialog']//div[@class='a-AlertMessage-details']");

	public ExpansionTiendas(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/**
	 * Retorna si la tabla Carga Archivo Expansi�n de Tiendas LOG es mostrada en pantalla
	 *
	 * @author Edwin Chi
	 * @return Verdadero o Falso dependiendo si el elemento es mostrado en pantalla
	 * @date 29/Octubre/2024
	 */
	public boolean isCargaArchivoExpansionDeTiendasLogDisplayed() {
		boolean isTableDisplayed = u.isDisplayed(cargaArchivoExpansionDeTiendasLog);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		return isTableDisplayed;
	}
	
	/**
	 * Retorna si la tabla Expansi�n de Tienda C�lculo es mostrada en pantalla
	 *
	 * @author Edwin Chi
	 * @return Verdadero o Falso dependiendo si el elemento es mostrado en pantalla
	 * @date 29/Octubre/2024
	 */
	public boolean isExpansionDeTiendasCalculoDisplayed() {
		boolean isTableDisplayed = u.isDisplayed(expansionDeTiendasCalculo);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		return isTableDisplayed;
	}

	/**
	 * Click en la tab Cargar Archivo
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
 	 */
	public void clickTabCargarArchivo() throws Exception {
		u.hardWait(1);
		u.click(tabCargarArchivo);
		esperarElementoInvisible(u, loadingSpinner);
	}

	/**
	 * Click en la tab Refresh
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
 	 */
	public void clickTabRefresh() throws Exception {
		u.click(tabRefresh);
		u.hardWait(2);
		esperarElementoInvisible(u, loadingSpinner);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Retorna si el estado CARGADO es mostrado en pantalla
	 *
	 * @author Edwin Chi
	 * @return Verdadero o Falso dependiendo si el elemento es mostrado en pantalla
	 * @date 29/Octubre/2024
 	 */
	public boolean isStatusCargadoDisplayed() throws Exception {
		esperarElementoInvisible(u, loadingSpinner);
		return obtenerElementoVisible(u, tablaRefreshCargadoStatus).isDisplayed();
	}

	/**
	 * Retorna si el estado PROCESADO es mostrado en pantalla
	 *
	 * @author Edwin Chi
	 * @return Verdadero o Falso dependiendo si el elemento es mostrado en pantalla
	 * @date 29/Octubre/2024
 	 */
	public boolean isStatusProcesadoDisplayed() throws Exception {
		esperarElementoInvisible(u, loadingSpinner);
		return obtenerElementoVisible(u, tablaRefreshProcesadoStatus).isDisplayed();
	}

	/**
	 * Click en la tab Procesar Archivo.
	 * Tambi�n espera a que la animaci�n de cargar se oculte.
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
 	 */
	public void clickTabProcesaArchivo() throws Exception {
		u.click(tabProcesaArchivo);
		u.hardWait(2);
		esperarElementoInvisible(u, loadingSpinner);
	}

	/**
	 * Click en la tab Descarga Log
	 * Tambi�n espera a que la animaci�n de cargar se oculte.
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
 	 */
	public void clickTabDescargaLog() throws Exception {
		u.click(tabDescargaLog);
		esperarElementoInvisible(u, loadingSpinner);
		u.getDriver().switchTo().frame(0);
		me.explicitWaitVisibilityOfElement(accionesButton, TIMEOUT);
	}

	/**
	 * Carga el archivo csv en la pantalla.
	 *
	 * @author Edwin Chi
	 * @param rutaDelArchivo es la ruta del archivo csv junto con el nombre del mismo
	 * @date 29/Octubre/2024
 	 */
	public void cargarArchivo(String rutaDelArchivo) throws Exception {
		u.getDriver().switchTo().frame(0);
		u.isDisplayed(botonCargarArchivo);
		u.hardWait(2);
		u.sendKeys(inputArchivo, rutaDelArchivo);
		u.hardWait(2);
	}

	/**
	 * Click en el bot�n Cargar Archivo
	 * Tambi�n espera a que la animaci�n de cargar se oculte.
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
 	 */
	public void clickBotonCargarArchivo() throws Exception {
		u.click(botonCargarArchivo);
		u.hardWait(2);
		esperarElementoInvisible(u, loadingSpinner);
	}

	/**
	 * Regresa si la ventana para cargar el archivo se ocult� con �xito.
	 * Tambi�n recarga la p�gina.
	 *
	 * @author Edwin Chi
	 * @return Verdadero o Falso dependiendo si el elemento es mostrado en pantalla
	 * @date 29/Octubre/2024
 	 */
	public boolean desaparecioVentanaCargarArchivo() throws Exception {
		boolean desaparecioVentana = esperarElementoInvisible(u, botonCargarArchivo);
		u.getDriver().switchTo().defaultContent();
		u.refresh();
		u.hardWait(2);
		return desaparecioVentana;
	}

	/**
	 * Regresa el usuario que carg� el archivo csv
	 *
	 * @author Edwin Chi
	 * @return el nombre del usuario que carg� el archivo csv, ya sea el CEDIS o el OS
	 * @date 29/Octubre/2024
 	 */
	public String getUsuarioFromTable() {
		return u.getText(tablaRefreshUsuario);
	}

	/**
	 * Regresa el nombre del archivo csv que fue cargado
	 *
	 * @author Edwin Chi
	 * @return nombre del archivo csv
	 * @date 29/Octubre/2024
 	 */
	public String getNombreArchivoFromTable() {
		return u.getText(tablaRefreshNombreArchivo);
	}

	/**
	 * Regresa el tipo del archivo csv que fue cargado
	 *
	 * @author Edwin Chi
	 * @return tipo del archivo csv
	 * @date 29/Octubre/2024
 	 */
	public String getTipoFromTable() {
		return u.getText(tablaRefreshTipo);
	}

	/**
	 * Regresa el estado del archivo csv que fue cargado
	 *
	 * @author Edwin Chi
	 * @return estado del archivo csv, puede ser CARGADO o PROCESADO
	 * @date 29/Octubre/2024
 	 */
	public String getStatusFromTable() {
		return u.getText(tablaRefreshStatus);
	}

	/**
	 * Click en el bot�n Ok en el modal cuando se le da click en Procesar Archivo.
	 * Tambi�n espera a que el modal desaparezca.
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
 	 */
	public void clickOkButtonFromModal() throws Exception {
		u.click(okButtonFromModal);
		u.hardWait(1);
		esperarElementoInvisible(u, okButtonFromModal);
	}

	/**
	 * Click en el bot�n Buscar.
	 * Tambi�n espera a la animaci�n de carga desaparezca.
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
 	 */
	public void clickBuscarButton() throws Exception {
		u.click(buscarButton);
		esperarElementoInvisible(u, loadingSpinner);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Obtener toda la informaci�n de la columna Fecha de la tabla Expansi�n de Tienda C�lculo.
	 *
	 * @author Edwin Chi
	 * @return lista de WebElement con los resultados encontrados
	 * @date 29/Octubre/2024
 	 */
	public List<WebElement> getFechaRecordsFromExpansionDeTiendaCalculo() throws Exception {
		return getAllVisibleElements(u, fechaRecordsFromExpansionDeTiendaCalculo);
	}
	
	/**
	 * Obtener toda la informaci�n de la columna CEDIS de la tabla Expansi�n de Tienda C�lculo.
	 *
	 * @author Edwin Chi
	 * @return lista de WebElement con los resultados encontrados
	 * @date 29/Octubre/2024
 	 */
	public List<WebElement> getCedisRecordsFromExpansionDeTiendaCalculo() throws Exception {
		return getAllVisibleElements(u, cedisRecordsFromExpansionDeTiendaCalculo);
	}

	/**
	 * Obtener toda la informaci�n de la columna Total de la tabla Expansi�n de Tienda C�lculo.
	 *
	 * @author Edwin Chi
	 * @return lista de WebElement con los resultados encontrados
	 * @date 29/Octubre/2024
 	 */
	public List<WebElement> getTotalTiendasRecordsFromExpansionDeTiendaCalculo() throws Exception {
		return getAllVisibleElements(u, totalTiendasRecordsFromExpansionDeTiendaCalculo);
	}

	/**
	 * Obtener toda la informaci�n de la columna Cajas de la tabla Expansi�n de Tienda C�lculo.
	 *
	 * @author Edwin Chi
	 * @return lista de WebElement con los resultados encontrados
	 * @date 29/Octubre/2024
 	 */
	public List<WebElement> getCajasRecordsFromExpansionDeTiendaCalculo() throws Exception {
		return getAllVisibleElements(u, cajasRecordsFromExpansionDeTiendaCalculo);
	}

	/**
	 * Obtener toda la informaci�n de la columna Piezas de la tabla Expansi�n de Tienda C�lculo.
	 *
	 * @author Edwin Chi
	 * @return lista de WebElement con los resultados encontrados
	 * @date 29/Octubre/2024
 	 */
	public List<WebElement> getPiezasRecordsFromExpansionDeTiendaCalculo() throws Exception {
		return getAllVisibleElements(u, piezasRecordsFromExpansionDeTiendaCalculo);
	}

	/**
	 * Obtener toda la informaci�n de la columna Refrigerados de la tabla Expansi�n de Tienda C�lculo.
	 *
	 * @author Edwin Chi
	 * @return lista de WebElement con los resultados encontrados
	 * @date 29/Octubre/2024
 	 */
	public List<WebElement> getRefrigeradosRecordsFromExpansionDeTiendaCalculo() throws Exception {
		return getAllVisibleElements(u, refrigeradosRecordsFromExpansionDeTiendaCalculo);
	}
	
	/**
	 * Obtener toda la informaci�n filtrada de la columna Fecha de la tabla Expansi�n de Tienda C�lculo.
	 *
	 * @author Edwin Chi
	 * @return lista de WebElement con los resultados encontrados bajo los criterios de b�squeda establecidos
	 * @date 29/Octubre/2024
 	 */
	public List<WebElement> getFilteredFechaRecordsFromExpansionDeTiendaCalculo() throws Exception {
		return u.listElements(fechaRecordsFromExpansionDeTiendaCalculo);
	}
	
	/**
	 * Obtener toda la informaci�n filtrada de la columna Cedis de la tabla Expansi�n de Tienda C�lculo.
	 *
	 * @author Edwin Chi
	 * @return lista de WebElement con los resultados encontrados bajo los criterios de b�squeda establecidos
	 * @date 29/Octubre/2024
 	 */
	public List<WebElement> getFilteredCedisRecordsFromExpansionDeTiendaCalculo() throws Exception {
		return u.listElements(cedisRecordsFromExpansionDeTiendaCalculo);
	}

	/**
	 * Click en el bot�n Descargar.
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
 	 */
	public void clickDescargarButton() {
		u.click(descargarButton);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Click en el bot�n CSV dentro del modal.
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
 	 */
	public void clickCsvOption() {
		u.click(csvOption);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Click en el bot�n Descargar dentro del modal.
	 * Tambi�n espera a que la animaci�n de carga desaparezca.
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
 	 */
	public void clickDownloadButton() throws Exception {
		u.click(downloadButton);
		esperarElementoInvisible(u, loadingSpinner);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}
	
	/**
	 * Validar que el mensaje de �xito es mostrado en pantalla
	 *
	 * @author Edwin Chi
	 * @return Verdadero o Falso dependiendo si el elemento es mostrado en pantalla
	 * @date 29/Octubre/2024
 	 */
	public boolean isSuccessMessageDisplayed() {
		boolean isSuccessMessageDisplayed = u.isDisplayed(successDownloadMessage);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		u.hardWait(5);
		return isSuccessMessageDisplayed;
	}

	/**
	 * Click en el bot�n Editar 
	 * Tambi�n valida que la animaci�n de carga desaparezca.
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void clickOnEditButton() {
		u.click(editButton);
		u.hardWait(3);
		testCase.addScreenShotCurrentStep("nombre de la imagen");			
	}

	/**
	 * Escribe un nuevo valor en el campo Total Tiendas
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void typeNewTotalTiendas(String totalTiendas) {
		u.click(totalTiendasInput);
		u.hardWait(1);
		Actions action = new Actions(u.getDriver()); 
		action.keyDown(Keys.CONTROL)
			.sendKeys("a")
			.keyUp(Keys.CONTROL)
			.sendKeys(Keys.DELETE)
			.sendKeys(totalTiendas)
			.perform();
		u.hardWait(1);
		testCase.addScreenShotCurrentStep("nombre de la imagen");			
	}

	/**
	 * Click en el bot�n Guardar
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void clickOnSaveButton() {
		u.click(saveButton);
		testCase.addScreenShotCurrentStep("nombre de la imagen");	
	}

	/**
	 * Recargar la p�gina
	 *
	 * @author Edwin Chi
	 * @date 29/Octubre/2024
	 */
	public void reloadPage() {
		u.refresh();
	}

	/**
	 * Regresa el Total de Tiendas del primer registro de la tabla
	 *
	 * @author Edwin Chi
	 * @return el texto encontrado en Total Tiendas
	 * @date 29/Octubre/2024
	 */
	public String getTotalTiendasFromFirstRow() {
		u.scrollToElement(totalTiendasInput);
		return u.getText(totalTiendasInput);
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
	public void typeValue(String expectedFecha) {
		u.sendKeys(filterValueInput, expectedFecha);
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
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		return isDisplayed;
	}

	/**
	 * Retorna si la animaci�n de carga se ha ocultado, si no, espera a que lo haga.
	 *
	 * @author Edwin Chi
	 * @return Retorna si la animaci�n de carga se ha ocultado, si no, espera a que lo haga
	 * @date 29/Octubre/2024
	 */
	public boolean isLoadingSpinnerInvisible() throws Exception {
		u.hardWait(3);
		return esperarElementoInvisible(u, loadingSpinner);
	}

	/**
	 * Escribe un valor en el campo Fecha Inicio
	 *
	 * @author Edwin Chi
	 * @param fechaInicio es la fecha a ingresar para definir el alcance de b�squeda
	 * @date 29/Octubre/2024
	 */
	public void typeFechaInicio(String fechaInicio) {
		u.click(fechaInicioInput);
		u.clear(fechaInicioInput);
		u.sendKeys(fechaInicioInput, fechaInicio);
		Actions action = new Actions(u.getDriver()); 
		action.sendKeys(Keys.TAB)
			.perform();
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Escribe un valor en el campo Fecha Fin
	 *
	 * @author Edwin Chi
	 * @param fechaFin es la fecha a ingresar para definir el alcance de b�squeda
	 * @date 29/Octubre/2024
	 */
	public void typeFechaFin(String fechaFin) {
		u.click(fechaFinInput);
		u.clear(fechaFinInput);
		u.sendKeys(fechaFinInput, fechaFin);
		Actions action = new Actions(u.getDriver()); 
		action.sendKeys(Keys.TAB)
			.sendKeys(Keys.TAB)
			.perform();
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Selecciona un valor en el campo Cedis
	 *
	 * @author Edwin Chi
	 * @param cedis es el valor a escoger de entre todos los CEDIS disponibles en pantalla
	 * @date 29/Octubre/2024
	 */
	public void selectCedis(String cedis) {
		Select columnSelect = new Select(u.getDriver().findElement(cedisDropdown));
		columnSelect.selectByVisibleText(cedis);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}
	
	/**
	 * Retorna el total de errores recibidos al procesar el archivo
	 *
	 * @author Edwin Chi
	 * @return cantidad de errores en formato de texto mostrados en pantalla
	 * @date 29/Octubre/2024
	 */
	public String getTotalErrors() {
		return u.getText(tablaTotalError);
	}

	/**
	 * Regresa el texto de las filas dentro de la tabla Expansi�n de Tiendas C�lculo
	 *
	 * @author Edwin Chi
	 * @param cedis es el n�mero de CEDIS a encontrar dentro de la tabla
	 * @return el texto de todas las filas donde se encuentre un CEDIS
	 * @date 08/Noviembre/2024
	 */
	public String getRowsFromCedis(String cedis) {
		return u.getText(By.xpath("//div[@class='a-GV-w-scroll']//table[@role='presentation']//tbody//tr//td[3 and contains(., '" + cedis+ "')]/parent::tr"));
	}

	/**
	 * Validar que la tabla donde se carga el archivo no est� visible
	 *
	 * @author Edwin Chi
	 * @return true si la tabla est� oculta
	 * @date 08/Noviembre/2024
	 */
	public boolean isTableCargaArchivoExpansionDeTiendasLogHidden() throws Exception {
		return esperarElementoInvisible(u, cargaArchivoExpansionDeTiendasLog);
	}

	/**
	 * Retorna el valor de todos los Errores de la tabla en una lista objetos de tipo String
	 *
	 * @author Edwin Chi
	 * @return Retorna el valor de todos los Errores de la tabla en una lista objetos de tipo String
	 * @date 21/Noviembre/2024
	 */
	public List<String> getTextFromColumnStatusLineaLog(){
		return u.listElements(statusLineaLogColumn)
			.stream()
			.map(statusLineaLog -> statusLineaLog.getText().trim())
			.filter(statusLineaLog -> statusLineaLog.length() > 0)
			.collect(Collectors.toList());
	}

	/**
	 * Cierra el popup de Descarga Log
	 *
	 * @author Edwin Chi
	 * @date 21/Noviembre/2024
	 */
	public void closeDescargaLogPopup(){
		u.click(descargaLocExitButton);
		me.explicitWaitVisibilityOfElement(descargaLocExitButton, TIMEOUT);
		u.getDriver().switchTo().defaultContent();
	}

	/**
	 * Retorna el mensaje al momento de procesar un archivo
	 *
	 * @author Edwin Chi
	 * @return el mensaje al momento de procesar un archivo
	 * @date 21/Noviembre/2024
	 */
    public String getProcessFileMessage() {
		return u.getText(processMessage);
    }
    
    /**
	 * Ir al final de la p�gina
	 *
	 * @author Edwin Chi
	 * @date 28/Noviembre/2024
	 */
    public void goToEndOfPage() {
    	pressKeys(u, Keys.END);
    	u.hardWait(1);
    }
}
