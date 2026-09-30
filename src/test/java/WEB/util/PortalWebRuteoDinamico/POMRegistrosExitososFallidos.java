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

public class POMRegistrosExitososFallidos {
	private SeleniumUtil u;
	private WebDriverWait wait;
	private TestCase testCase;
	
	public POMRegistrosExitososFallidos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.wait = new WebDriverWait(this.u.getDriver(), Duration.ofSeconds(15));
		this.testCase = testCase;
	}
	
	// PAGINA REGISTROS FALLIDOS
	private By locatorTituloPaginaRegFallidos = By.xpath("//h5[normalize-space()='Registros Fallidos']");
	private By locatorInfoRegistroFallidoTable = By.xpath("//table[@class='table table-bordered']");
	private By locatorInfoRegistrosArchivoFallidosLabel = By.xpath("//body/app-root[1]/app-registros-fallidos[1]/div[1]/div[1]/div[1]/div[1]");
	private By locatorInfoRegistrosCargadosFallidosLabel = By.xpath("//body/app-root[1]/app-registros-fallidos[1]/div[1]/div[1]/div[1]/div[2]");
	private By locatorInfoRegistrosConErrorLabel = By.xpath("//body/app-root[1]/app-registros-fallidos[1]/div[1]/div[1]/div[1]/div[3]");
	private By locatorTituloRegistrosFallidosTable = By.xpath("//h4[normalize-space()='Lista Registros Fallidos']");
	private By locatorDescargarRegistrosFallidosButton = By.xpath("//button[normalize-space()='Descargar Registros Fallidos']");
	private By locatorRegistrosFallidosTable = By.xpath("//table[@class='table table-bordered mt-3']");
	private By locatorRegistrosFallidosBody = By.xpath("(//tbody)[2]");
	
	//PAGINA REGISTROS EXISTOSOS
	private By locatorTituloPaginaRegExitosos = By.xpath("//h5[normalize-space()='Registros Exitosos']");
	private By locatorInfoRegistroExitosoTable = By.xpath("//table[@class='table table-bordered']");
	private By locatorInfoRegistrosArchivoExitososLabel = By.xpath("//body/app-root[1]/app-registros-exitosos[1]/div[1]/div[1]/div[1]/div[1]");
	private By locatorInfoRegistrosCargadosExitososLabel = By.xpath("//body/app-root[1]/app-registros-exitosos[1]/div[1]/div[1]/div[1]/div[2]");
	private By locatorAvisoExitososMessage = By.xpath("//div[@class='success-message']");
	private By locatorTituloExitososMessage = By.xpath("//div[@class='message']");
	private By locatorDescripcionMessage = By.xpath("//div[@class='description']");
	private By locatorDescargarArchivoButton = By.xpath("//button[normalize-space()='Descargar archivo']");
	
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
	 * Clic en boton Descargar archivo
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickDescargarArchivo() throws Exception {
		try {
			clickOpcion("Descargar archivo", locatorDescargarArchivoButton, 15);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Descargar archivo");
		}
	}
	
	/**
	 * Clic en boton Descargar Registros Fallidos
	 * 
	 * @author cristhian.m.gonzalez
	 */
	public void clickDescargarRegistrosFallidos() throws Exception {
		try {
			clickOpcion("Descargar Registros Fallidos", locatorDescargarRegistrosFallidosButton, 15);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en Descargar Registros Fallidos");
		}
	}
	
	/**
	 * Obtiene primer valor de la tabla de la columna ID Carga
	 * 
	 * @return int El valor de la columna id carga del primer registro de la tabla
	 * @author cristhian.m.gonzalez
	 */
	public int obtenerIdCargaPrimerRegistro() throws Exception {
		String primerReg = "";
		String idCargaCadena = "";
		String[] primeraColumna;
		int idCarga = 0;
		try {
			u.hardWait(3);
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			WebElement bodyTabla = u.getDriver().findElement(locatorRegistrosFallidosBody);
			List<WebElement> filaTabla = bodyTabla.findElements(By.tagName("tr"));
			List<WebElement> celdaTabla = filaTabla.get(0).findElements(By.tagName("td"));
			primerReg = celdaTabla.get(0).getText();
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", 
					celdaTabla.get(0), "color: red; border: 5px solid red;");
			primeraColumna = primerReg.split(" ");
			idCargaCadena = primeraColumna[0].substring(1);
			idCarga = Integer.parseInt(idCargaCadena);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al obtener valor tabla");
		}
		return idCarga;
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
			irFinalPagina();
			clickOpcion("Siguiente Paginacion", locatorSiguientePagination, 3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton sigueinte de la paginacion");
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
			clickOpcion("Anterior Paginacion", locatorAnteriorPagination, 3);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("Fallo al dar click en el boton anterior de la paginacion");
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
	 * Valida elementos principales de la pagina Registros Fallidos
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarRegistrosFallidos() {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorTituloPaginaRegFallidos));
			wait.until(ExpectedConditions.elementToBeClickable(locatorInfoRegistroFallidoTable));
			wait.until(ExpectedConditions.elementToBeClickable(locatorInfoRegistrosArchivoFallidosLabel));
			wait.until(ExpectedConditions.elementToBeClickable(locatorInfoRegistrosCargadosFallidosLabel));
			wait.until(ExpectedConditions.elementToBeClickable(locatorInfoRegistrosConErrorLabel));
			wait.until(ExpectedConditions.elementToBeClickable(locatorTituloRegistrosFallidosTable));
			wait.until(ExpectedConditions.elementToBeClickable(locatorDescargarRegistrosFallidosButton));
			wait.until(ExpectedConditions.elementToBeClickable(locatorRegistrosFallidosTable));
			existencia = true;
			u.highLight(locatorTituloPaginaRegFallidos);
			u.highLight(locatorInfoRegistroFallidoTable);
			u.highLight(locatorInfoRegistrosArchivoFallidosLabel);
			u.highLight(locatorInfoRegistrosCargadosFallidosLabel);
			u.highLight(locatorInfoRegistrosConErrorLabel);
			u.highLight(locatorTituloRegistrosFallidosTable);
			u.highLight(locatorDescargarRegistrosFallidosButton);
			u.highLight(locatorRegistrosFallidosTable);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorTituloPaginaRegFallidos);
			u.unhighLight(locatorInfoRegistroFallidoTable);
			u.unhighLight(locatorInfoRegistrosArchivoFallidosLabel);
			u.unhighLight(locatorInfoRegistrosCargadosFallidosLabel);
			u.unhighLight(locatorInfoRegistrosConErrorLabel);
			u.unhighLight(locatorTituloRegistrosFallidosTable);
			u.unhighLight(locatorDescargarRegistrosFallidosButton);
			u.unhighLight(locatorRegistrosFallidosTable);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la pagina Registros Fallidos");
		}
		return existencia;
	}
	
	/**
	 * Valida elementos principales de la pagina Registros Existosos
	 * 
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarRegistrosExitosos() {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorTituloPaginaRegExitosos));
			wait.until(ExpectedConditions.elementToBeClickable(locatorInfoRegistroExitosoTable));
			wait.until(ExpectedConditions.elementToBeClickable(locatorInfoRegistrosArchivoExitososLabel));
			wait.until(ExpectedConditions.elementToBeClickable(locatorInfoRegistrosCargadosExitososLabel));
			wait.until(ExpectedConditions.elementToBeClickable(locatorAvisoExitososMessage));
			wait.until(ExpectedConditions.elementToBeClickable(locatorTituloExitososMessage));
			wait.until(ExpectedConditions.elementToBeClickable(locatorDescripcionMessage));
			wait.until(ExpectedConditions.elementToBeClickable(locatorDescargarArchivoButton));
			existencia = true;
			u.highLight(locatorTituloPaginaRegExitosos);
			u.highLight(locatorInfoRegistroExitosoTable);
			u.highLight(locatorInfoRegistrosArchivoExitososLabel);
			u.highLight(locatorInfoRegistrosCargadosExitososLabel);
			u.highLight(locatorAvisoExitososMessage);
			u.highLight(locatorTituloExitososMessage);
			u.highLight(locatorDescripcionMessage);
			u.highLight(locatorDescargarArchivoButton);
			testCase.addScreenShotCurrentStep("");
			u.unhighLight(locatorTituloPaginaRegExitosos);
			u.unhighLight(locatorInfoRegistroExitosoTable);
			u.unhighLight(locatorInfoRegistrosArchivoExitososLabel);
			u.unhighLight(locatorInfoRegistrosCargadosExitososLabel);
			u.unhighLight(locatorAvisoExitososMessage);
			u.unhighLight(locatorTituloExitososMessage);
			u.unhighLight(locatorDescripcionMessage);
			u.unhighLight(locatorDescargarArchivoButton);
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se ven los elementos de la pagina Registros Exitosos");
		}
		return existencia;
	}
	
	/**
	 * Valida el id carga de la tabla de errores para verificar el cambio de pagina
	 * 
	 * @param idCargaPrimerPag Id de Carga del primer registro de la primer pagina
	 * @param idCargaSigPag Id de Carga del primer registro de la siguiente pagina
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarIdCargaSiguientePag(int idCargaPrimerPag, int idCargaSigPag) {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorRegistrosFallidosBody));
			if(idCargaPrimerPag < idCargaSigPag) {
				existencia = true;
				JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
				WebElement bodyTabla = u.getDriver().findElement(locatorRegistrosFallidosBody);
				List<WebElement> filaTabla = bodyTabla.findElements(By.tagName("tr"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
						filaTabla.get(0), "color: red; border: 5px solid red;");
				testCase.addScreenShotCurrentStep("");
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", filaTabla.get(0));
			}
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se realizo el cambio de pagina");
		}
		return existencia;
	}
	
	/**
	 * Valida el id carga de la tabla de errores para verificar el regreso de pagina
	 * 
	 * @param idCargaPrimerPag Id de Carga del primer registro de la primer pagina
	 * @param idCargaSigPag Id de Carga del primer registro de la siguiente pagina
	 * @return Boolean True si existen los elementos, false en caso contrario
	 * @author cristhian.m.gonzalez
	 */
	public Boolean validarIdCargaAnteriorPag(int idCargaPrimerPag, int idCargaSigPag) {
		Boolean existencia = false;
		try {
			u.hardWait(5);
			wait.until(ExpectedConditions.elementToBeClickable(locatorRegistrosFallidosBody));
			if(idCargaPrimerPag == idCargaSigPag) {
				existencia = true;
				JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
				WebElement bodyTabla = u.getDriver().findElement(locatorRegistrosFallidosBody);
				List<WebElement> filaTabla = bodyTabla.findElements(By.tagName("tr"));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
						filaTabla.get(0), "color: red; border: 5px solid red;");
				testCase.addScreenShotCurrentStep("");
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", filaTabla.get(0));
			}
			
		} catch (Exception e) {
			System.out.println(" -> " + e);
			System.out.println("No se realizo el regreso de pagina");
		}
		return existencia;
	}
}
