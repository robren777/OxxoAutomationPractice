package WEB.util.PortalWebRuteoDinamico;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class POMConsultaCatalogoRestTienda {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;

	public POMConsultaCatalogoRestTienda(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}
	
	// PAGINA CONSULTA CATALOGO RESTRICCIONES TIENDAS
	private By locatorConsultaCatalogoRestTiendaButton = By.xpath("//a[normalize-space()='Consultar Catalogo Restricci�n Tiendas']");
	private By locatorTituloPagina = By.xpath("//h5[normalize-space()='Formulario de Consulta']");
	private By locatorListaCedisTitle = By.xpath("//label[normalize-space()='Lista de CEDIS']");
	private By locatorListaCedisDropDown = By.xpath("//select[@id='cedisList']");
	private By locatorConsultarButton = By.xpath("//button[normalize-space()='Consultar']");
	private By locatorCancelarButton = By.xpath("//button[normalize-space()='Cancelar']");
	private By locatorRegistrosConsultaTable = By.xpath("//table[@class='table table-bordered']");
	private By locatorRegistrosConsultaColumn = By.xpath("//table[@class='table table-bordered']//thead");
	private By locatorRegistrosConsultaBody = By.xpath("//tbody");
	private By locatorPrimerRegistroTable = By.xpath("//tbody/tr[1]");
	private By locatorPreconfirmacionButton = By.xpath("//span[normalize-space()='Preconfirmaci�n']");
	private By locatorPreconfirmacionTitle = By.xpath("//h3[normalize-space()='Preconfirmaci�n']");
	private By locatorPreconfirmacionTable = By.cssSelector("body > app-root:nth-child(1) > app-consulta-catalogo-rt:nth-child(3)"
			+ " > div:nth-child(1) > div:nth-child(6) > table:nth-child(2)");
	private By locatorPreconfirmacionBody = By.cssSelector("body > app-root:nth-child(1) > app-consulta-catalogo-rt:nth-child(3) > "
												+ "div:nth-child(1) > div:nth-child(6) > table:nth-child(2) > tbody:nth-child(2)");
	private By locatorVerDetalleRegNuevosButton = By.xpath("//tbody/tr/td[4]/button[1]");
	private By locatorVerDetalleRegActualizadosButton = By.xpath("//tbody/tr/td[5]/button[1]");
	private By locatorDetallesRDMTitle = By.xpath("//h1[@id='ModalLabel']");
	private By locatorDetallesRDMTable = By.xpath("//div[@class='container mt-4']//table[@class='table table-bordered']");
	private By locatorDetallesRDMCloseButton = By.xpath("//button[normalize-space()='Close']");
	private By locatorConfirmarButton = By.xpath("//button[@class='btn btn-success']");
	private By locatorConfirmarDesactivadoButton = By.xpath("//button[@class='btn btn-success disabled']");
	private By locatorDescargarCatRestTiendasButton = By.xpath("//button[normalize-space()='Descargar Cat�logo de Restricciones de Tiendas']");
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
	 * Clic en Consultar Catalogo Restriccion Tiendas del menu del Portal Ruteo Dinamico
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickConsultarCatalogoRestriccionTiendas() throws Exception {
		try {
			clickOpcion("Consultar Catalogo Restriccion Tiendas", locatorConsultaCatalogoRestTiendaButton, 5);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Consultar Catalogo Restriccion Tiendas");
		}
	}
	
	/**
	 * Clic en el boton Consultar
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickConsultar() throws Exception {
		try {
			clickOpcion("Consulta Registros", locatorConsultarButton, 7);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton Consultar");
		}
	}
	
	/**
	 * Clic en el boton Cancelar
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickCancelar() throws Exception {
		try {
			clickOpcion("Cancelar", locatorCancelarButton, 3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton Cancelar");
		}
	}
	
	/**
	 * Clic en el boton Preconfirmacion
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickPreconfirmacion() throws Exception {
		try {
			u.hardWait(1);
			clickOpcion("Preconfirmacion", locatorPreconfirmacionButton, 15);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton Preconfirmacion");
		}
	}
	
	/**
	 * Clic en el boton Ver Detalle de la tabla de preconfirmacion del los registros nuevos RDM
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickVerDetalleNuevos() throws Exception {
		try {
			clickOpcion("Ver Detalle", locatorVerDetalleRegNuevosButton, 3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton Ver Detalle registros nuevos RDM");
		}
	}
	
	/**
	 * Clic en el boton Ver Detalle de la tabla de preconfirmacion del los registros actualizados RDM
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickVerDetalleActualizados() throws Exception {
		try {
			clickOpcion("Ver Detalle", locatorVerDetalleRegActualizadosButton, 3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton Ver Detalle registros actualizados RDM");
		}
	}
	
	/**
	 * Clic en el boton Confirmar de la tabla de preconfirmacion
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickConfirmar() throws Exception {
		try {
			clickOpcion("Confirmar", locatorConfirmarButton, 15);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton Confirmar");
		}
	}
	
	/**
	 * Clic en el boton Descargar Catqlogo de Restricciones de Tiendas
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickDescargarCatRestTiendas() throws Exception {
		try {
			irFinalPagina();
			u.hardWait(1);
			clickOpcion("Descargar Catalogo de Restricciones de Tiendas", locatorDescargarCatRestTiendasButton, 20);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton Descargar Catalogo de Restricciones de Tiendas");
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
	 * Obtener dato de la tabla Consulta Registros
	 * 
	 * @param fila Numero de fila
	 * @param columna Numero de columna
	 * @return String Dato de la tabla
	 * @author cristhian.m.gonzalez
	 */
	public String obtenerDatoTabla(int fila, int columna) throws Exception {
		String dato = "";
		try {
			u.hardWait(5);
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			WebElement bodyTabla = u.getDriver().findElement(locatorRegistrosConsultaBody);
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
	 * Obtener dato de la tabla Preconfirmacion
	 * 
	 * @param celda Numero de celda
	 * @return String Dato de la tabla
	 * @author cristhian.m.gonzalez
	 */
	public String obtenerDatoTablaPreconfirmacion(int celda) throws Exception {
		String dato = "";
		try {
			u.hardWait(2);
			WebElement bodyTabla = u.getDriver().findElement(locatorPreconfirmacionBody);
			List<WebElement> filaTabla = bodyTabla.findElements(By.tagName("tr"));
			List<WebElement> celdaTabla = filaTabla.get(0).findElements(By.tagName("td"));
			dato = celdaTabla.get(celda).getText();
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
					u.hardWait(1);
					js.executeScript("arguments[0].setAttribute('style', arguments[1]);", listaNumPag.get(i));
					irPrincipioPagina();
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
			irFinalPagina();
			u.hardWait(1);
			u.highLight(locatorSiguientePagination);
			u.hardWait(1);
			clickOpcion("Siguiente Paginacion", locatorSiguientePagination, 3);
			u.hardWait(1);
			u.unhighLight(locatorSiguientePagination);
			irPrincipioPagina();
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
			clickOpcion("Anterior Paginacion", locatorAnteriorPagination, 3);
			u.hardWait(1);
			u.unhighLight(locatorAnteriorPagination);
			irPrincipioPagina();
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton anterior de la paginacion");
		}
	}
	
	/**
	 * Se dirige al principio de la pagina
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void irPrincipioPagina() throws Exception {
		try {
			u.hardWait(1);
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			js.executeScript("window.scrollTo(0, 0)");
			u.hardWait(1);
			js.executeScript("window.scrollTo(0, 0)");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al ir al principio de la pagina");
		}
	}
	
	/**
	 * Se dirige al final de la pagina
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void irFinalPagina() throws Exception {
		try {
			u.hardWait(1);
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
			u.hardWait(1);
			js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al ir al final de la pagina");
		}
	}
	
	/**
	 * Valida elementos principales de la pagina Consultar Catalogo Restriccion Tiendas
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarConsultarCatalogoRestriccionTiendas() {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorTituloPagina));
			wait.until(ExpectedConditions.elementToBeClickable(locatorListaCedisDropDown));
			wait.until(ExpectedConditions.elementToBeClickable(locatorConsultarButton));
			wait.until(ExpectedConditions.elementToBeClickable(locatorCancelarButton));
			wait.until(ExpectedConditions.elementToBeClickable(locatorRegistrosConsultaTable));
			wait.until(ExpectedConditions.elementToBeClickable(locatorRegistrosConsultaColumn));
			existencia = true;
			u.highLight(locatorTituloPagina);
			u.highLight(locatorListaCedisDropDown);
			u.highLight(locatorConsultarButton);
			u.highLight(locatorCancelarButton);
			u.highLight(locatorRegistrosConsultaTable);
			u.highLight(locatorRegistrosConsultaColumn);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorTituloPagina);
			u.unhighLight(locatorListaCedisDropDown);
			u.unhighLight(locatorConsultarButton);
			u.unhighLight(locatorCancelarButton);
			u.unhighLight(locatorRegistrosConsultaTable);
			u.unhighLight(locatorRegistrosConsultaColumn);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la pagina Consultar Catalogo Restriccion Tiendas");
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
	 * Valida que se muestren registros en la tabla al dar boton Consultar con cedis seleccionado
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarConsultar() {
		Boolean existencia = false;
		String claveCedisPrimerFila, claveCedisSegundaFila;
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorRegistrosConsultaBody));
			claveCedisPrimerFila = obtenerDatoTabla(0, 0);
			claveCedisSegundaFila = obtenerDatoTabla(1, 0);
			if(claveCedisPrimerFila.contentEquals(claveCedisSegundaFila)) {
				existencia = true;
				u.highLight(locatorRegistrosConsultaBody);
				testCase.addScreenShotCurrentStep("");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostraron registros de la consulta");
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
			u.hardWait(1);
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
	 * Valida que se muestre mensaje de alerta de seleccionar cedis
	 * 
	 * @param mensaje El mensaje a validar de la ventana de alerta
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarConsultarSinCedis(String mensaje) {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			if(validarMensajeAlerta(mensaje)) {
				existencia = true;
				u.highLight(locatorListaCedisDropDown);
				testCase.addScreenShotCurrentStep("");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro mensaje de alerta de seleccionar cedis");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre mensaje de alerta al seleccionar cedis sin registros asociados
	 * 
	 * @param mensaje El mensaje a validar de la ventana de alerta
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarConsultarCedisSinRegistrosAsociados(String mensaje) {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			if(validarMensajeAlerta(mensaje)) {
				existencia = true;
				u.highLight(locatorListaCedisDropDown);
				testCase.addScreenShotCurrentStep("");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro mensaje de alerta de seleccionar cedis con registros asociados");
		}
		return existencia;
	}
	
	/**
	 * Valida elementos principales de la tabla Preconfirmacion
	 * 
	 * @param cedis El cedis seleccionado de la consulta
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarTablaPreconfirmacion(String cedis) {
		Boolean existencia = false;
		String cedisClave, nombreCedis;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorPreconfirmacionTitle));
			wait.until(ExpectedConditions.elementToBeClickable(locatorPreconfirmacionTable));
			wait.until(ExpectedConditions.elementToBeClickable(locatorPreconfirmacionBody));
			cedisClave = obtenerDatoTablaPreconfirmacion(0);
			nombreCedis = obtenerDatoTablaPreconfirmacion(1);
			if(cedis.contains(cedisClave) && cedis.contains(nombreCedis)) {
				existencia = true;
				u.highLight(locatorPreconfirmacionTitle);
				u.highLight(locatorPreconfirmacionTable);
				u.highLight(locatorPreconfirmacionBody);
				testCase.addScreenShotCurrentStep("");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro la tabla de Preconfirmacion");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre mensaje de alerta al confirmar
	 * 
	 * @param mensaje Es el mensaje a validar
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarConfirmacion(String mensaje) {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			if(validarMensajeAlerta(mensaje)) {
				u.hardWait(1);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorPreconfirmacionTitle));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorPreconfirmacionBody));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorPreconfirmacionTable));
				wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorConfirmarButton));
				existencia = true;
				u.highLight(locatorListaCedisDropDown);
				u.highLight(locatorConsultarButton);
				u.highLight(locatorCancelarButton);
				u.highLight(locatorPreconfirmacionButton);
				u.highLight(locatorRegistrosConsultaBody);
				u.highLight(locatorRegistrosConsultaTable);
				testCase.addScreenShotCurrentStep("");
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro mensaje de alerta de confirmacion");
		}
		return existencia;
	}
	
	/**
	 * Valida que se limpie la consulta y la preconfirmacion
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarLimpiarConsulta() {
		Boolean existencia = false;
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorListaCedisDropDown));
			wait.until(ExpectedConditions.elementToBeClickable(locatorConsultarButton));
			wait.until(ExpectedConditions.elementToBeClickable(locatorCancelarButton));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorPreconfirmacionButton));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorPreconfirmacionTitle));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorPreconfirmacionBody));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorPreconfirmacionTable));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locatorConfirmarButton));
			existencia = true;
			u.highLight(locatorListaCedisDropDown);
			u.highLight(locatorConsultarButton);
			u.highLight(locatorCancelarButton);
			u.highLight(locatorRegistrosConsultaBody);
			u.highLight(locatorRegistrosConsultaTable);
			testCase.addScreenShotCurrentStep("");
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se limpio la consulta");
		}
		return existencia;
	}
	
	/**
	 * Valida que se muestre deshabilitado el boton Confirmar
	 * 
	 * @param cedis El cedis seleccionado de la consulta
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarBotonConfirmacionDeshabilitado(String cedis) {
		Boolean desactivado = false;
		String estilo = "";
		int regNuevosRDM, regActualizarRDM;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorPreconfirmacionTitle));
			wait.until(ExpectedConditions.elementToBeClickable(locatorPreconfirmacionTable));
			wait.until(ExpectedConditions.elementToBeClickable(locatorPreconfirmacionBody));
			regNuevosRDM = Integer.parseInt(obtenerDatoTablaPreconfirmacion(3));
			regActualizarRDM = Integer.parseInt(obtenerDatoTablaPreconfirmacion(4));
			estilo = u.getDriver().findElement(locatorConfirmarDesactivadoButton).getAttribute("class");
			desactivado = estilo.contains("disable");
			if(regNuevosRDM == 0 && regActualizarRDM == 0 && desactivado) {
				u.highLight(locatorConfirmarDesactivadoButton);
				testCase.addScreenShotCurrentStep("");
				u.unhighLight(locatorConfirmarDesactivadoButton);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se muestra desactivado el boton Confirmar");
		}
		return desactivado;
	}
	
	/**
	 * Valida elementos principales de la tabla de Detalles(Nuevos-Actualizados) RDM
	 * 
	 * @param tituloTablaDetalles Es el titulo a validar de la tabla de Detalles(Nuevos-Actualizados) RDM
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarVentanaDetallesRDM(String tituloTablaDetalles) {
		Boolean existencia = false;
		String titulo = "";
		try {
			u.hardWait(1);
			wait.until(ExpectedConditions.elementToBeClickable(locatorDetallesRDMTitle));
			wait.until(ExpectedConditions.elementToBeClickable(locatorDetallesRDMTable));
			wait.until(ExpectedConditions.elementToBeClickable(locatorDetallesRDMCloseButton));
			titulo = u.getDriver().findElement(locatorDetallesRDMTitle).getText();
			if(titulo.contentEquals(tituloTablaDetalles)) {
				existencia = true;
				u.highLight(locatorDetallesRDMTitle);
				u.highLight(locatorDetallesRDMTable);
				u.highLight(locatorDetallesRDMCloseButton);
				clickOpcion("Close", locatorDetallesRDMCloseButton, 3);
			}
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se mostro la tabla de Detalles(Nuevos-Actualizados) RDM");
		}
		return existencia;
	}
	
	/**
	 * Valida paginacion por codigo de cliente sea diferente primer registro
	 * 
	 * @param codClientePagAnt Codigo de Cliente primer registro de la primera pagina
	 * @param codClientePagSig Codigo de Cliente primer registro de la siguiente pagina
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarAvanzarPaginacion(String codClientePagAnt, String codClientePagSig) {
		Boolean existencia = false;
		try {
			u.hardWait(3);
			if(!codClientePagAnt.contentEquals(codClientePagSig)) {
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
	 * Valida regresar a la pagina anterior y verificar por codigo de cliente
	 * 
	 * @param codClientePagAnt Codigo de Cliente primer registro de la primera pagina
	 * @param codClientePagSig Codigo de Cliente primer registro de la siguiente pagina
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarRegresarPaginacion(String codClientePagAnt, String codClientePagSig) {
		Boolean existencia = false;
		try {
			u.hardWait(3);
			if(codClientePagAnt.contentEquals(codClientePagSig)) {
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
