package WEB.util.PortalWebRuteoDinamico;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.App;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class POMPlanDeCarga {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;

	public POMPlanDeCarga(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}
	
	// PAGINA PLAN DE CARGA
	private By locatorPlanDeCargaButton = By.xpath("//a[normalize-space()='Plan de Carga']");
	private By locatorTituloPagina = By.xpath("//h5[normalize-space()='Obtenci�n Plan Carga']");
	private By locatorListaCedisTitle = By.xpath("//label[normalize-space()='Lista de CEDIS']");
	private By locatorListaCedisDropDown = By.xpath("//select[@id='cedisList']");
	private By locatorFechaInicialCalendar = By.xpath("//input[@id='fechaInicial']");
	private By locatorFechaFinalCalendar = By.xpath("//input[@id='fechaFinal']");
	private By locatorCargarDepositosButton = By.xpath("//button[normalize-space()='Cargar Dep�sitos']");
	private By locatorConsultarButton = By.xpath("//button[normalize-space()='Consultar']");
	private By locatorCancelarButton = By.xpath("//button[normalize-space()='Cancelar']");
	private By locatorListaDepositosTitle = By.xpath("//label[normalize-space()='Lista de Dep�sitos']");
	private By locatorListaDepositosDropDown = By.xpath("//select[@id='depositoList']");
	private By locatorResultadoConsultaTitle = By.xpath("//h6[normalize-space()='Resultados de la Consulta']");
	private By locatorResultadoConsultaTable = By.xpath("//table[@class='table table-bordered me-3']");
	private By locatorResultadoConsultaColumns = By.xpath("//table[@class='table table-bordered me-3']//thead//tr");
	private By locatorResultadoConsultaBody = By.xpath("//tbody");	
	private By locatorPrimerRegistroTable = By.xpath("//tbody/tr[1]");
	private By locatorDescargarPlanCargaButton = By.xpath("//button[normalize-space()='Descargar Plan Carga']");
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
			u.hardWait(1);
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
	 * Clic en Plan de Carga del menu del Portal Ruteo Dinamico
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickPlanDeCarga() throws Exception {
		try {
			clickOpcion("Plan de Carga", locatorPlanDeCargaButton, 10);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Plan de Carga");
		}
	}
	
	/**
	 * Clic en boton Cargar Depositos
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickCargarDepositos() throws Exception {
		try {
			clickOpcion("Cargar Depositos", locatorCargarDepositosButton, 7);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Cargar Depositos");
		}
	}
	
	/**
	 * Clic en boton Consultar
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickConsultar() throws Exception {
		try {
			clickOpcion("Consultar", locatorConsultarButton, 7);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Consultar");
		}
	}
	
	/**
	 * Clic en boton Cancelar
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickCancelar() throws Exception {
		try {
			clickOpcion("Cancelar", locatorCancelarButton, 2);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Cancelar");
		}
	}
	
	/**
	 * Clic en boton Descargar Plan Carga
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickDescargarPlanCarga() throws Exception {
		try {
			irFinalPagina();
			clickOpcion("Descargar Plan Carga", locatorDescargarPlanCargaButton, 30);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Descargar Plan Carga");
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
			u.hardWait(1);
			u.sendKeys(locatorListaCedisDropDown, cedis);
			u.click(locatorListaCedisTitle);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Seleccionar CEDIS");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al seleccionar cedis");
		}
	}
	
	/**
	 * Selecciona un deposito del drop down Lista de Depositos
	 * 
	 * @param cedis Nombre del deposito a seleccionar
	 * @author cristhian.m.gonzalez
	 */
	public void seleccionarDeposito(String cedis) throws Exception {
		try {
			u.hardWait(1);
			clickOpcion("Seleccionar deposito", locatorListaDepositosDropDown, 2);
			u.hardWait(1);
			u.sendKeys(locatorListaDepositosDropDown, cedis);
			u.click(locatorListaDepositosTitle);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Seleccionar Deposito");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al seleccionar deposito");
		}
	}
	
	/**
	 * Retorna la fecha actual en formato MM/dd/yyyy
	 * 
	 * @return String Valor de fecha actual
	 * @author cristhian.m.gonzalez
	 */
	public String obtenerFechaActual() throws Exception {
		String fechaActual = "";
		try {
			Date fecha = new Date();
			String formato = "MM/dd/yyyy";
			SimpleDateFormat fechaConFormato = new SimpleDateFormat(formato);
			fechaActual = fechaConFormato.format(fecha);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Fallo al obtener fecha actual");
		}
		return fechaActual;
	}
	
	/**
	 * Retorna una fecha atras de la actual
	 * 
	 * @param tiempo Es el numero o cantidad de dias a regresar a la fecha actual
	 * @return String La fecha anterior a la actual
	 * @author cristhian.m.gonzalez
	 */
	public String obtenerFechaAnterior(int dias) throws Exception {
		String fechaAnterior = "";
		try {
			if(dias > 0) {
				Calendar calendario = Calendar.getInstance();
				calendario.add(Calendar.DAY_OF_MONTH, -dias);
		        Date fecha = calendario.getTime();
		        String formato = "MM/dd/yyyy";
				SimpleDateFormat fechaConFormato = new SimpleDateFormat(formato);
				fechaAnterior = fechaConFormato.format(fecha);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Fallo al obtener fecha anterior");
		}
		return fechaAnterior;
	}
	
	/**
	 * Establece la fecha inicial
	 * 
	 * @param fecha Fecha inicial
	 * @author cristhian.m.gonzalez
	 */
	public void establecerFechaInicial(String fecha) throws Exception {
		try {
			u.hardWait(2);
			clicCalendario(locatorFechaInicialCalendar);
			u.click(locatorFechaInicialCalendar);
			u.hardWait(1);
			u.sendKeys(locatorFechaInicialCalendar, fecha);
			testCase.addScreenShotCurrentStep(u, "Establecer fecha inicial");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al establecer fecha inicial");
		}
	}
	
	/**
	 * Establece la fecha final
	 * 
	 * @param fecha Fecha final
	 * @author cristhian.m.gonzalez
	 */
	public void establecerFechaFinal(String fecha) throws Exception {
		try {
			u.hardWait(2);
			clicCalendario(locatorFechaFinalCalendar);
			u.click(locatorFechaFinalCalendar);
			u.hardWait(1);
			u.sendKeys(locatorFechaFinalCalendar, fecha);
			testCase.addScreenShotCurrentStep(u, "Establecer fecha final");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al establecer fecha final");
		}
	}
	
	/**
	 * Establece la fecha inicial y final con fecha actual
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void establecerFechas() throws Exception {
		try {
			Robot robot = new Robot();
			u.hardWait(1);
			clicCalendario(locatorFechaInicialCalendar);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
			u.hardWait(1);
			clicCalendario(locatorFechaFinalCalendar);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al establecer fechas con fecha actual");
		}
	}
	
	/**
	 * Muestra el calendario de los campos de fecha
	 * 
	 * @param elementBy Elemento locator a hacer clic 
	 * @author cristhian.m.gonzalez
	 */
	public void clicCalendario(By elementBy) throws Exception {
		try {
			Robot robot = new Robot();
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(elementBy));
			u.highLight(elementBy);
			testCase.addScreenShotCurrentStep(u, "Dar clic en icono Calendario");
			u.unhighLight(elementBy);
			u.click(elementBy);
			u.hardWait(1);
			hacerTab(1);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al hacer clic en icono Calendario");
		}
	}
	
	/**
	 * Obtener dato de la tabla de Resultados de la consulta
	 * 
	 * @param fila Numero de fila
	 * @param celda Numero de columna
	 * @return String Dato de la tabla
	 * @author cristhian.m.gonzalez
	 */
	public String obtenerDatoTabla(int fila, int columna) throws Exception {
		String dato = "";
		try {
			u.hardWait(3);
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			WebElement bodyTabla = u.getDriver().findElement(locatorResultadoConsultaBody);
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
	 * Clic en numero de la paginacion de la tabla de registros
	 * 
	 * @param numPag Numero de la paginacion de la tabla a hacer clic
	 * @author cristhian.m.gonzalez
	 */
	public void clickNumeroPaginacionTabla(int numPag) throws Exception {
		try {
			irFinalPagina();
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
					u.hardWait(3);
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
			irFinalPagina();
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
			irFinalPagina();
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
	 * Emula seleccionar todo el texto
	 *
	 * @author cristhian.m.gonzalez
	 */
	public void seleccionarTexto() throws Exception {
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (AWTException e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al seleccionar texto");
		}
	}
	
	/**
	 * Copia un valor seleccionado
	 * 
	 * @return String El valor copiado
	 * @author cristhian.m.gonzalez
	 */
	String copyPaste() throws Exception {
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		} catch (AWTException e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al copiar texto");
		}
		return App.getClipboard();
	}
	
	/**
	 * Realiza tabs en la pagina n veces
	 * 
	 * @param numVeces Numero de veces al realizar el tab
	 * @author cristhian.m.gonzalez
	 */
	public void hacerTab(int numVeces) throws Exception {
		try {
			Robot robot = new Robot();
			for (int i = 0; i < numVeces; i++) {
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				u.hardWait(1);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al hacer tab");
		}
	}
	
	/**
	 * Se dirige al final de la pagina
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void irFinalPagina() throws Exception {
		try {
			u.hardWait(3);
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
			u.hardWait(3);
			js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
			u.hardWait(3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al ir al final de la pagina");
		}
	}
	
	/**
	 * Valida elementos principales de la pagina Plan de Carga
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarPlanDeCarga(){
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorTituloPagina));
			wait.until(ExpectedConditions.elementToBeClickable(locatorListaCedisDropDown));
			wait.until(ExpectedConditions.elementToBeClickable(locatorFechaInicialCalendar));
			wait.until(ExpectedConditions.elementToBeClickable(locatorFechaFinalCalendar));
			wait.until(ExpectedConditions.elementToBeClickable(locatorCargarDepositosButton));
			wait.until(ExpectedConditions.elementToBeClickable(locatorConsultarButton));
			wait.until(ExpectedConditions.elementToBeClickable(locatorCancelarButton));
			existencia = true;
			u.highLight(locatorTituloPagina);
			u.highLight(locatorListaCedisDropDown);
			u.highLight(locatorFechaInicialCalendar);
			u.highLight(locatorFechaFinalCalendar);
			u.highLight(locatorCargarDepositosButton);
			u.highLight(locatorConsultarButton);
			u.highLight(locatorCancelarButton);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorTituloPagina);
			u.unhighLight(locatorListaCedisDropDown);
			u.unhighLight(locatorFechaInicialCalendar);
			u.unhighLight(locatorFechaFinalCalendar);
			u.unhighLight(locatorCargarDepositosButton);
			u.unhighLight(locatorConsultarButton);
			u.unhighLight(locatorCancelarButton);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la pagina Plan de Carga");
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
	 * Valida que el campo fecha incial no este vacio o tenga una fecha
	 * 
	 * @param fechaInicial Es la fecha incial a validar
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarFechaInicial(String fechaInicial) {
		Boolean existencia = false;
		String fecha = "";
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorFechaInicialCalendar));
			hacerTab(1);
			u.click(locatorFechaInicialCalendar);
			seleccionarTexto();
			fecha = copyPaste().trim();
			u.hardWait(1);
			if(fecha.contentEquals(fechaInicial)) {
				existencia = true;
				u.highLight(locatorFechaInicialCalendar);
				testCase.addScreenShotCurrentStep(u, "Establecer fecha inicial");
				u.unhighLight(locatorFechaInicialCalendar);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se pudo establecer la fecha inicial");
		}
		return existencia;
	}
	
	/**
	 * Valida que el campo fecha final no este vacio o tenga una fecha
	 * 
	 * @param fechaInicial Es la fecha final a validar
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarFechaFinal(String fechaFinal) {
		Boolean existencia = false;
		String fecha = "";
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorFechaFinalCalendar));
			hacerTab(1);
			u.click(locatorFechaFinalCalendar);
			seleccionarTexto();
			fecha = copyPaste().trim();
			u.hardWait(1);
			if(fecha.contentEquals(fechaFinal)) {
				existencia = true;
				u.highLight(locatorFechaFinalCalendar);
				testCase.addScreenShotCurrentStep(u, "Establecer fecha final");
				u.unhighLight(locatorFechaFinalCalendar);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se pudo establecer la fecha final");
		}
		return existencia;
	}
	
	/**
	 * Valida que se haya mostrado la lista de depositos
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarListaDepositos() {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorListaDepositosDropDown));
			existencia = true;
			u.highLight(locatorListaDepositosDropDown);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorListaDepositosDropDown);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se selecciono el deposito");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre mensaje de alerta
	 * 
	 * @param mensaje El mensaje a validar de la ventana de alerta
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarMensajeAlerta(String mensaje) {
		Boolean existencia = false;
		String mensajeAlerta;
		try {
			u.hardWait(5);
			mensajeAlerta = u.getDriver().switchTo().alert().getText();
			if(mensajeAlerta.contentEquals(mensaje)) {
				existencia = true;
				u.getDriver().switchTo().alert().accept();
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro mensaje de alerta");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre mensaje de seleccionar cedis
	 * 
	 * @param mensaje El mensaje a validar de la ventana de alerta
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarMensajeSeleccionarCedis(String mensaje) {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			if(validarMensajeAlerta(mensaje)) {
				wait.until(ExpectedConditions.elementToBeClickable(locatorListaCedisDropDown));
				existencia = true;
				u.highLight(locatorListaCedisDropDown);
				testCase.addScreenShotCurrentStep("");
				u.unhighLight(locatorListaCedisDropDown);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro mensaje de alerta de seleccionar cedis");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre mensaje de llenar campos fecha
	 * 
	 * @param mensaje El mensaje a validar de la ventana de alerta
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarMensajeLlenarCamposFechas(String mensaje) {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			if(validarMensajeAlerta(mensaje)) {
				wait.until(ExpectedConditions.elementToBeClickable(locatorFechaInicialCalendar));
				wait.until(ExpectedConditions.elementToBeClickable(locatorFechaFinalCalendar));
				existencia = true;
				u.highLight(locatorFechaInicialCalendar);
				u.highLight(locatorFechaFinalCalendar);
				testCase.addScreenShotCurrentStep("");
				u.unhighLight(locatorFechaInicialCalendar);
				u.unhighLight(locatorFechaFinalCalendar);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro mensaje de llenar campos fecha");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre mensaje de deposito no existente de cedis seleccionado
	 * 
	 * @param mensaje El mensaje a validar de la ventana de alerta
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarCargarDepositosSinDeposito(String mensaje) {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			if(validarMensajeAlerta(mensaje)) {
				wait.until(ExpectedConditions.elementToBeClickable(locatorListaCedisDropDown));
				wait.until(ExpectedConditions.elementToBeClickable(locatorFechaInicialCalendar));
				wait.until(ExpectedConditions.elementToBeClickable(locatorFechaFinalCalendar));
				existencia = true;
				u.highLight(locatorListaCedisDropDown);
				u.highLight(locatorFechaInicialCalendar);
				u.highLight(locatorFechaFinalCalendar);
				testCase.addScreenShotCurrentStep("");
				u.unhighLight(locatorListaCedisDropDown);
				u.unhighLight(locatorFechaInicialCalendar);
				u.unhighLight(locatorFechaFinalCalendar);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro mensaje de alerta de deposito no existente");
		}
		return existencia;
	}
	
	/**
	 * Valida elementos principales al realizar consulta
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarConsulta() {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorResultadoConsultaTitle));
			wait.until(ExpectedConditions.elementToBeClickable(locatorResultadoConsultaTable));
			wait.until(ExpectedConditions.elementToBeClickable(locatorResultadoConsultaColumns));
			wait.until(ExpectedConditions.elementToBeClickable(locatorResultadoConsultaBody));
			wait.until(ExpectedConditions.elementToBeClickable(locatorDescargarPlanCargaButton));
			existencia = true;
			u.highLight(locatorResultadoConsultaTitle);
			u.highLight(locatorResultadoConsultaTable);
			u.highLight(locatorResultadoConsultaColumns);
			u.highLight(locatorResultadoConsultaBody);
			u.highLight(locatorDescargarPlanCargaButton);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorResultadoConsultaTitle);
			u.unhighLight(locatorResultadoConsultaTable);
			u.unhighLight(locatorResultadoConsultaColumns);
			u.unhighLight(locatorResultadoConsultaBody);
			u.unhighLight(locatorDescargarPlanCargaButton);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro el resultado de la consulta");
		}
		return existencia;
	}
	
	/**
	 * Valida que se limpien los campos de la consulta
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarLimpiarConsulta() {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorListaCedisDropDown));
			wait.until(ExpectedConditions.elementToBeClickable(locatorFechaInicialCalendar));
			wait.until(ExpectedConditions.elementToBeClickable(locatorFechaFinalCalendar));
			wait.until(ExpectedConditions.elementToBeClickable(locatorCargarDepositosButton));
			wait.until(ExpectedConditions.elementToBeClickable(locatorConsultarButton));
			wait.until(ExpectedConditions.elementToBeClickable(locatorCancelarButton));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorListaDepositosDropDown));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorResultadoConsultaTitle));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorResultadoConsultaBody));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorResultadoConsultaTable));
			existencia = true;
			u.highLight(locatorListaCedisDropDown);
			u.highLight(locatorFechaInicialCalendar);
			u.highLight(locatorFechaFinalCalendar);
			u.highLight(locatorCargarDepositosButton);
			u.highLight(locatorConsultarButton);
			u.highLight(locatorCancelarButton);
			testCase.addScreenShotCurrentStep("");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se limpiaron los campos de la consulta");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre mensaje de sin registros existentes
	 * 
	 * @param mensaje El mensaje a validar de la ventana de alerta
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarMensajeSinRegistrosExistentes(String mensaje) {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			if(validarMensajeAlerta(mensaje)) {
				wait.until(ExpectedConditions.elementToBeClickable(locatorListaCedisDropDown));
				wait.until(ExpectedConditions.elementToBeClickable(locatorFechaInicialCalendar));
				wait.until(ExpectedConditions.elementToBeClickable(locatorFechaFinalCalendar));
				wait.until(ExpectedConditions.elementToBeClickable(locatorListaDepositosDropDown));
				existencia = true;
				u.highLight(locatorListaCedisDropDown);
				u.highLight(locatorFechaInicialCalendar);
				u.highLight(locatorFechaFinalCalendar);
				u.highLight(locatorListaDepositosDropDown);
				testCase.addScreenShotCurrentStep("");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro mensaje de alerta sin registros existentes");
		}
		return existencia;
	}
	
	/**
	 * Valida paginacion por numero de transferencia sea diferente primer registro
	 * 
	 * @param TsfNoPagAnt Numero de transferencia primer registro de la primera pagina
	 * @param TsfNoPagSig Numero de transferencia primer registro de la siguiente pagina
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarAvanzarPaginacion(String TsfNoPagAnt, String TsfNoPagSig) {
		Boolean existencia = false;
		try {
			u.hardWait(2);
			if(!TsfNoPagAnt.contentEquals(TsfNoPagSig)) {
				wait.until(ExpectedConditions.elementToBeClickable(locatorPrimerRegistroTable));
				existencia = true;
				u.highLight(locatorPrimerRegistroTable);
				testCase.addScreenShotCurrentStep("");
				u.unhighLight(locatorPrimerRegistroTable);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se realizo el cambio de paginacion");
		}
		return existencia;
	}
	
	/**
	 * Valida regresar a la pagina anterior y verificar por numero de transferencia
	 * 
	 * @param TsfNoPagAnt Numero de transferencia primer registro de la primera pagina
	 * @param TsfNoPagSig Numero de transferencia primer registro de la siguiente pagina
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarRegresarPaginacion(String TsfNoPagAnt, String TsfNoPagSig) {
		Boolean existencia = false;
		try {
			u.hardWait(2);
			if(TsfNoPagAnt.contentEquals(TsfNoPagSig)) {
				wait.until(ExpectedConditions.elementToBeClickable(locatorPrimerRegistroTable));
				existencia = true;
				u.highLight(locatorPrimerRegistroTable);
				testCase.addScreenShotCurrentStep("");
				u.unhighLight(locatorPrimerRegistroTable);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se realizo el cambio de paginacion");
		}
		return existencia;
	}
}
