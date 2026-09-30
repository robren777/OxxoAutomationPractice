package WEB.util.PortalWebRuteoDinamico;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.hotkey.Keys;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class POMCargaMasivaCatalogoRestTienda {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;

	public POMCargaMasivaCatalogoRestTienda(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}
	
	// PAGINA CARGA MASIVA CATALOGO RESTRICCIONES TIENDAS
	private By locatorCargaMasivaCatalogoRestTiendaButton = By.xpath("//a[normalize-space()='Carga Masiva CRT']");
	private By locatorTituloPagina = By.xpath("//h5[contains(text(),'Carga Masiva de Cat�logo de Restricciones de Tiend')]");
	private By locatorDescargarFormatoArchivoButton = By.xpath("//a[normalize-space()='Descargar Formato Archivo']");
	private By locatorCargarArchivoButton = By.xpath("//button[normalize-space()='Cargar Archivo']");
	private By locatorRegistrosArchivosCargadosTable = By.xpath("//table[@class='table table-bordered']");
	private By locatorRegistrosArchivosCargadosColumn = By.xpath("//table[@class='table table-bordered']//thead");
	private By locatorRegistrosArchivosCargadosBody = By.xpath("//tbody");
	private By locatorPageNavigation = By.xpath("//nav[@aria-label='Page navigation']");
	private By locatorTituloFormularioCarga = By.xpath("//h5[@id='uploadModalLabel']");
	private By locatorListaCedisDropDown = By.xpath("//select[@id='cedisSelect']");
	private By locatorSubirArchivoFileInput = By.xpath("//input[@id='fileInput']");
	private By locatorCancelarButton = By.xpath("//button[normalize-space()='Cancelar']");
	private By locatorGuardarButton = By.xpath("//button[normalize-space()='Guardar']");
	private By locatorCerrarButton = By.xpath("//button[@aria-label='Cerrar']");
	private By locatorArchivoMessage = By.xpath("//div[@class='text-danger']");
	private By locatorSelecCedisRequeridoMessage = By.xpath("//div[@class='modal-content']//div[1]//div[1]");
	private By locatorSubirArchivoRequeridoMessage = By.xpath("//div[@class='modal-body']//div[2]//div[1]");
	private By locatorVentanaModalSuccess = By.xpath("//div[@id='successModal']//div[@class='modal-body text-center']");
	private By locatorAceptarButtonModalSuccess = By.xpath("//div[@id='successModal']//button[@type='button'][normalize-space()='Aceptar']");
	private By locatorMensajeModalSuccess = By.cssSelector("div[id='successModal'] p");
	private By locatorVentanaModalFail = By.xpath("//div[@id='errorModal']//div[@class='modal-body text-center']");
	private By locatorAceptarButtonModalFail = By.xpath("//div[@id='errorModal']//button[@type='button'][normalize-space()='Aceptar']");
	private By locatorMensajeModalFail = By.cssSelector("div[id='errorModal'] p");
	private By locatorCodigoRegistroTable = By.cssSelector("tbody tr:nth-child(1) td:nth-child(1)");
	private By locatorPrimerRegistroTable = By.xpath("//tbody/tr[1]");
	private By locatorRegistrosFallidosButton = By.xpath("(//i[@title='Registros Fallidos'])[1]");
	private By locatorRegistrosExitososButton = By.xpath("(//i[@title='Registros exitosos'])[1]");
	private By locatorConsultaRegistrosButton = By.xpath("(//i[@title='Consultar Registros �xitosos'])[1]");
	private By locatorPagination = By.xpath("//ul[@class='pagination justify-content-center']");
	private By locatorSiguientePagination = By.xpath("//a[@aria-label='Next']");
	private By locatorAnteriorPagination = By.xpath("//a[@aria-label='Previous']");
	
	/**
	 * Espera a que el elemento se muestre, lo resalta, toma un screenshot, y le da click
	 * 
	 * @param name Nombre del boton, enlace u opcion
	 * @param elementBy Elemento locator a hacer clic 
	 * @param waitTime El tiempo de espera al realizar el clic
	 * @author cristhian.m.gonzalez
	 */
	public void clickOpcion(String name, By elementBy, int waitTime) throws Exception {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			testCase.addScreenShotCurrentStep(u, "Dar clic en " + name);
			u.unhighLight(elementBy);
			u.click(elementBy);
			u.hardWait(waitTime);
			testCase.addScreenShotCurrentStep("");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar clic en: " + name);
		}
	}
	
	/**
	 * Clic en Carga Masiva CRT del menu del Portal Ruteo Dinamico
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickCargaMasivaCRT() throws Exception {
		try {
			clickOpcion("Carga Masiva CRT", locatorCargaMasivaCatalogoRestTiendaButton, 3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Carga Masiva CRT");
		}
	}
	
	/**
	 * Clic en Descargar Formato Archivo para descargar el formato de archivo a cargar para las restricciones Tiendas
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickDescargarFormatoArchivo() throws Exception {
		try {
			clickOpcion("Descargar Formato Archivo", locatorDescargarFormatoArchivoButton, 10);
			u.hardWait(10);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Descargar Formato Archivo");
		}
	}
	
	/**
	 * Clic en Cargar Archivo para desplegar el formulario para cargar archivo
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickCargarArchivo() throws Exception {
		try {
			clickOpcion("Cargar Archivo", locatorCargarArchivoButton, 3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Cargar Archivo");
		}
	}
	
	/**
	 * Clic en Cancelar para quitar el formulario para cargar archivo
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickCancelar() throws Exception {
		try {
			clickOpcion("Cancelar", locatorCancelarButton, 3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Cancelar");
		}
	}
	
	/**
	 * Clic en Cerrar (icono X) para quitar el formulario para cargar archivo
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickCerrar() throws Exception {
		try {
			clickOpcion("Cancelar (icono X)", locatorCerrarButton, 3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Cerrar (icono X)");
		}
	}
	
	/**
	 * Clic en Guardar para cargar el catalogo de restriccion de tiendas
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickGuardar() throws Exception {
		try {
			clickOpcion("Guardar", locatorGuardarButton, 10);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Guardar");
		}
	}
	
	/**
	 * Clic en Icono de tachado del algun registro de la tabla para cargar pagina de registros fallidos
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickRegistrosFallidos() throws Exception {
		try {
			clickOpcion("Registros Fallidos", locatorRegistrosFallidosButton, 10);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el icono de tachado");
		}
	}
	
	/**
	 * Clic en Icono de verificado del algun registro de la tabla para cargar pagina de registros exitosos
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickRegistrosExitosos() throws Exception {
		try {
			clickOpcion("Registros Exitosos", locatorRegistrosExitososButton, 10);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el icono de verificado");
		}
	}
	
	/**
	 * Clic en Icono de lupa del algun registro de la tabla para cargar pagina de consulta de registros
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickConsultaRegistros() throws Exception {
		try {
			clickOpcion("Consulta Registros", locatorConsultaRegistrosButton, 5);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el icono de lupa");
		}
	}
	
	/**
	 * Clic en numero de la paginacion de la tabla de registros
	 * 
	 * @param numPag Numero de la paginacion de la tabla a hacer clic
	 * @author cristhian.m.gonzalez
	 */
	public void clickNumeroPaginacionTabla(int numPag) throws Exception {
		try {
			WebElement barraNumPag = u.getDriver().findElement(locatorPagination);
			List<WebElement> listaNumPag = barraNumPag.findElements(By.tagName("li"));
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			int numeroPag = 0;
			for (int i = 1; i < listaNumPag.size(); i++) {
				wait.until(ExpectedConditions.elementToBeClickable(listaNumPag.get(i)));
				numeroPag = Integer.parseInt(listaNumPag.get(i).getText());
				if(numPag == numeroPag) {
					u.hardWait(1);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", 
							listaNumPag.get(i), "color: red; border: 5px solid red;");
					u.hardWait(1);
					testCase.addScreenShotCurrentStep("");
					listaNumPag.get(i).click();
					u.hardWait(5);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", listaNumPag.get(i));
					u.hardWait(1);
					testCase.addScreenShotCurrentStep("");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el numero de pagina");
		}
	}
	
	/**
	 * Clic en boton de siguiente de la paginacion de la tabla de registros
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickSiguientePaginacion() throws Exception {
		try {
			u.hardWait(1);
			u.highLight(locatorSiguientePagination);
			u.hardWait(1);
			clickOpcion("Siguiente Paginacion", locatorSiguientePagination, 5);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton siguiente de la paginacion");
		}
	}
	
	/**
	 * Clic en boton de anterior de la paginacion de la tabla de registros
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickAnteriorPaginacion() throws Exception {
		try {
			u.hardWait(1);
			u.highLight(locatorAnteriorPagination);
			u.hardWait(1);
			clickOpcion("Anterior Paginacion", locatorAnteriorPagination, 5);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton anterior de la paginacion");
		}
	}
	
	/**
	 * Selecciona un cedis del drop down Lista de CEDIS
	 * 
	 * @param cedis Nombre del cedis a seleccionar
	 * @author cristhian.m.gonzalez
	 */
	public void seleccionarCedis(String cedis) throws Exception {
		try {
			u.hardWait(1);
			clickOpcion("Seleccionar cedis", locatorListaCedisDropDown, 2);
			u.sendKeys(locatorListaCedisDropDown, cedis);
			u.click(locatorListaCedisDropDown);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Seleccionar CEDIS");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al seleccionar cedis");
		}
	}
	
	/**
	 * Carga el archivo de catalogo de restricciones tienda en el Formulario de Carga
	 * 
	 * @param pathFile Path donde se encuentra el archivo a cargar
	 * @author cristhian.m.gonzalez
	 */
	public void seleccionarArchivo(String pathFile) throws Exception {
		try {
			u.hardWait(5);
			u.highLight(locatorSubirArchivoFileInput);
			testCase.addScreenShotCurrentStep(u, "Seleccionar Archivo");
			u.unhighLight(locatorSubirArchivoFileInput);
			u.sendKeys(locatorSubirArchivoFileInput, pathFile);
			testCase.addScreenShotCurrentStep(u, "Seleccionar Archivo");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al seleccionar archivo");
		}
	}
	
	/**
	 * Obtener dato de la tabla Registros Archivos Cargados
	 * 
	 * @param fila Numero de fila
	 * @param columna Numero de columna
	 * @return String Dato de la tabla
	 * @author cristhian.m.gonzalez
	 */
	public String obtenerDatoTabla(int fila, int columna) throws Exception {
		String dato = "";
		try {
			u.hardWait(2);
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			WebElement bodyTabla = u.getDriver().findElement(locatorRegistrosArchivosCargadosBody);
			List<WebElement> filaTabla = bodyTabla.findElements(By.tagName("tr"));
			List<WebElement> celdaTabla = filaTabla.get(fila).findElements(By.tagName("td"));
			dato = celdaTabla.get(columna).getText();
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", 
					celdaTabla.get(columna), "color: red; border: 5px solid red;");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al conseguir dato");
		}
		return dato;
	}
	
	/**
	 * Obtiene primer valor de la tabla de la columna Codigo
	 * 
	 * @return int El valor de la columna codigo del primer registro de la tabla
	 * @author cristhian.m.gonzalez
	 */
	public int obtenerCodigoPrimerRegistro() throws Exception {
		int codigo = 0;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorCodigoRegistroTable));
			u.highLight(locatorCodigoRegistroTable);
			codigo = Integer.parseInt(u.getText(locatorCodigoRegistroTable));
			u.unhighLight(locatorCodigoRegistroTable);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al obtener valor tabla");
		}
		return codigo;
	}
	
	/**
	 * Realiza tabs en la pagina n veces
	 * 
	 * @param numVeces Es la cantidad de veces que se realiza la tabulacion
	 * @author cristhian.m.gonzalez
	 */
	public void hacerTab(int numVeces) throws Exception {
		try {
			Robot robot = new Robot();
			for (int i = 0; i < numVeces; i++) {
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				u.hardWait(2);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al realizar tabulacion");
		}
	}
	
	/**
	 * Limpia el campo Subir Archivo Excel
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void limpiarCampoSubirArchivo() throws Exception {
		try {
			Robot robot = new Robot();
			u.getDriver().findElement(locatorListaCedisDropDown).sendKeys(Keys.TAB);
			u.hardWait(2);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			u.hardWait(3);
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_F4);
			robot.keyRelease(KeyEvent.VK_ALT);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al limpiar campo Subir Archivo Excel");
		}
	}
	
	/**
	 * Valida elementos principales de la pagina Carga Masiva de Catalogo de Restricciones de Tiendas
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarCargaMasivaCRT() {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorTituloPagina));
			wait.until(ExpectedConditions.elementToBeClickable(locatorDescargarFormatoArchivoButton));
			wait.until(ExpectedConditions.elementToBeClickable(locatorCargarArchivoButton));
			wait.until(ExpectedConditions.elementToBeClickable(locatorRegistrosArchivosCargadosTable));
			wait.until(ExpectedConditions.elementToBeClickable(locatorRegistrosArchivosCargadosColumn));
			wait.until(ExpectedConditions.elementToBeClickable(locatorPageNavigation));
			existencia = true;
			u.highLight(locatorTituloPagina);
			u.highLight(locatorDescargarFormatoArchivoButton);
			u.highLight(locatorCargarArchivoButton);
			u.highLight(locatorRegistrosArchivosCargadosTable);
			u.highLight(locatorRegistrosArchivosCargadosColumn);
			u.highLight(locatorPageNavigation);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorTituloPagina);
			u.unhighLight(locatorDescargarFormatoArchivoButton);
			u.unhighLight(locatorCargarArchivoButton);
			u.unhighLight(locatorRegistrosArchivosCargadosTable);
			u.unhighLight(locatorRegistrosArchivosCargadosColumn);
			u.unhighLight(locatorPageNavigation);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la pagina Carga Masiva CRT");
		}
		return existencia;
	}
	
	/**
	 * Valida elementos principales del Formulario de Carga de Archivos
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarFormularioDeCarga() {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorTituloFormularioCarga));
			wait.until(ExpectedConditions.elementToBeClickable(locatorListaCedisDropDown));
			wait.until(ExpectedConditions.elementToBeClickable(locatorSubirArchivoFileInput));
			wait.until(ExpectedConditions.elementToBeClickable(locatorCancelarButton));
			if(!u.getDriver().findElement(locatorGuardarButton).isEnabled()) {
				existencia = true;
				u.highLight(locatorTituloFormularioCarga);
				u.highLight(locatorListaCedisDropDown);
				u.highLight(locatorSubirArchivoFileInput);
				u.highLight(locatorCancelarButton);
				u.highLight(locatorGuardarButton);
				testCase.addScreenShotCurrentStep("");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos del Formulario de Carga de Archivos");
		}
		return existencia;
	}
	
	/**
	 * Valida que se haya seleccionado un cedis de la lista de cedis
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarListaCedis() {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorListaCedisDropDown));
			existencia = true;
			u.highLight(locatorListaCedisDropDown);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorListaCedisDropDown);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se selecciono el cedis");
		}
		return existencia;
	}
	
	/**
	 * Valida que se haya cargado el archivo
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarArchivoCargado() {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorSubirArchivoFileInput));
			existencia = true;
			u.highLight(locatorSubirArchivoFileInput);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorSubirArchivoFileInput);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se cargo el archivo");
		}
		return existencia;
	}
	
	/**
	 * Valida el mensaje de la ventana modal
	 * 
	 * @param mensaje Es el mensaje a validar del modal
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarMensajeModal(String mensaje) {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorVentanaModalSuccess));
			wait.until(ExpectedConditions.elementToBeClickable(locatorMensajeModalSuccess));
			wait.until(ExpectedConditions.elementToBeClickable(locatorAceptarButtonModalSuccess));
			if(mensaje.contentEquals(u.getText(locatorMensajeModalSuccess))) {
				existencia = true;
				u.highLight(locatorVentanaModalSuccess);
				u.highLight(locatorMensajeModalSuccess);
				u.highLight(locatorAceptarButtonModalSuccess);
				clickOpcion("Aceptar", locatorAceptarButtonModalSuccess, 3);
			}
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro el modal");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre el mensaje de archivo no valido
	 * 
	 * @param mensaje Es el mensaje a validar
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarMensajeArchivoNoValido(String mensaje) {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorArchivoMessage));
			if(u.getText(locatorArchivoMessage).contentEquals(mensaje)) {
				existencia = true;
				u.highLight(locatorArchivoMessage);
				testCase.addScreenShotCurrentStep("");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro el mensaje de archivo no valido");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre el mensaje de tama�o del archivo no valido
	 * 
	 * @param mensaje Es el mensaje a validar
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarMensajeTamanoArchivoNoValido(String mensaje) {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorArchivoMessage));
			if(u.getText(locatorArchivoMessage).contentEquals(mensaje)) {
				existencia = true;
				u.highLight(locatorArchivoMessage);
				testCase.addScreenShotCurrentStep("");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro el mensaje de tama�o de archivo no valido");
		}
		return existencia;
	}
	
	/**
	 * Valida el mensaje de la ventana modal cuando el nombre de archivo tiene mas de 50 caracteres
	 * 
	 * @param mensaje Es el mensaje a validar del modal
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarMensajeNombreArchivoExtenso(String mensaje) {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorVentanaModalFail));
			wait.until(ExpectedConditions.elementToBeClickable(locatorMensajeModalFail));
			wait.until(ExpectedConditions.elementToBeClickable(locatorAceptarButtonModalFail));
			if(mensaje.contentEquals(u.getText(locatorMensajeModalFail))) {
				existencia = true;
				u.highLight(locatorVentanaModalFail);
				u.highLight(locatorMensajeModalFail);
				u.highLight(locatorAceptarButtonModalFail);
				clickOpcion("Aceptar", locatorAceptarButtonModalFail, 3);
			}
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro el mensaje de nombre de archivo no valido");
		}
		return existencia;
	}
	
	/**
	 * Valida el boton se muestre desactivado
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarBotonDesactivado() {
		Boolean desactivado = false;
		try {
			u.hardWait(1);
			if(!u.getDriver().findElement(locatorGuardarButton).isEnabled()) {
				desactivado = true;
				u.highLight(locatorGuardarButton);
				testCase.addScreenShotCurrentStep("");
				u.unhighLight(locatorGuardarButton);
				u.unhighLight(locatorSelecCedisRequeridoMessage);
				u.unhighLight(locatorSubirArchivoRequeridoMessage);
			}
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se desactivo el boton");
		}
		return desactivado;
	}
	
	/**
	 * Valida que se muestren los mensajes de campos requeridos
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarCamposRequeridos() {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorSelecCedisRequeridoMessage));
			wait.until(ExpectedConditions.elementToBeClickable(locatorSubirArchivoRequeridoMessage));
			existencia = true;
			u.highLight(locatorSelecCedisRequeridoMessage);
			u.highLight(locatorSubirArchivoRequeridoMessage);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorSelecCedisRequeridoMessage);
			u.unhighLight(locatorSubirArchivoRequeridoMessage);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostraron los mensajes de campos requeridos");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre mnesaje de campo Lista de Cedis requerido
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarLlenarSoloCampoArchivo() {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorSubirArchivoRequeridoMessage));
			wait.until(ExpectedConditions.elementToBeClickable(locatorSelecCedisRequeridoMessage));
			existencia = true;
			u.highLight(locatorSelecCedisRequeridoMessage);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro mensaje de campo requerido");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre mnesaje de campo Lista de Cedis requerido
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarLlenarSoloCampoCedis() {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorSelecCedisRequeridoMessage));
			wait.until(ExpectedConditions.elementToBeClickable(locatorSubirArchivoRequeridoMessage));
			existencia = true;
			u.highLight(locatorSubirArchivoRequeridoMessage);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro mensaje de campo requerido");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre registro del archivo recien cargado
	 * 
	 * @param codigoReciente Valor del codigo del primer registro nuevo
	 * @param codigoAntiguo Valor del codigo del primer registro anterior
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarRegistroArchivoRecienCargado(int codigoReciente, int codigoAntiguo) {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorPrimerRegistroTable));
			if (codigoAntiguo < codigoReciente) {
				existencia = true;
				u.highLight(locatorPrimerRegistroTable);
				testCase.addScreenShotCurrentStep("");
				u.unhighLight(locatorPrimerRegistroTable);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro el nuevo archivo cargado");
		}
		return existencia;
	}
	
	/**
	 * Valida que el total de registro del archivo cargado sea igual al numero de errores cargados 
	 * tras seleccionar cedis incorrecto en formulario de carga
	 * 
	 * @param totalRegistros Valor de la columna Total del registro de archivo cargado recientemente
	 * @param erroresRegistros Valor de la columna Error del registro de archivo cargado recientemente
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarRegistrosConErrorCedisIncorrecto(int totalRegistros, int erroresRegistros) {
		Boolean existencia = false;
		try {
			u.hardWait(2);
			wait.until(ExpectedConditions.elementToBeClickable(locatorPrimerRegistroTable));
			if (totalRegistros == erroresRegistros) {
				existencia = true;
				u.highLight(locatorPrimerRegistroTable);
				u.unhighLight(locatorPrimerRegistroTable);
				testCase.addScreenShotCurrentStep("");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostraron los errores al cargar con cedis incorrecto");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre el cambio de listado anterior o lista principal de registros al usar paginacion
	 * 
	 * @param codigoReciente Valor del codigo del primer registro nuevo
	 * @param codigoAntiguo Valor del codigo del primer registro anterior
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarPaginacion(int codigoReciente, int codigoAntiguo) {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorPrimerRegistroTable));
			if (codigoReciente > codigoAntiguo) {
				existencia = true;
				u.highLight(locatorPrimerRegistroTable);
				testCase.addScreenShotCurrentStep("");
				u.unhighLight(locatorPrimerRegistroTable);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro el cambio de paginacion de la tabla");
		}
		return existencia;
	}
	
}
