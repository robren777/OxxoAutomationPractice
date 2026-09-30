package WEB.portal_APEX.pom;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class DescargaPronosticoCEDIS extends BasePage {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	// Localizadores de ventana de "Descarga Pronostico CEDIS"
	By descarga_pronostico_cedis = By.xpath("//span[contains(text(), 'Descarga Pron�stico CEDIS')]");

	// Localizadores de ventana opciones de portal APEX
	By tabla_buscar = By.xpath("//*[@id=\"R146698562803152327_heading\"]");

	// Localizadores de tabla BuscarHistorial
	By validar_tabla = By.xpath("//*[@id=\"R146698747273152329_ig_grid_vc_cur\"]");
	By buscar_historial = By.xpath("//span[contains(text(), 'Buscar Historial')]");
	By descargar = By.xpath("(//span[contains(text(), 'Descarga')])[2]");
	By download = By.xpath("//button[contains(text(), 'Descargar')]");
	By resultado_busqueda_historial = By.xpath("//div[@aria-label='Reporte Historial Excel']//div[@class='a-GV-bdy']//table//tbody//td[1]");

	// Localizadores filtrado por CEDIS Historial
	By select_cedis_historial = By.xpath("//*[@id='P46_CEDIS_HIST']");

	// Localizador Loader Historial
	By loaderLocator = By.xpath("//span[@class='u-Processing-spinner']");
	By mensajeDescargaArchivo = By.xpath("(//h2[normalize-space()='Archivo preparado. Empezando descarga'])[1]");

	// Localizadores fechas Historial
	By fecha_inicio_historial = By.xpath("//*[@id=\"P46_FECHA_INICIO_HIST|input\"]");
	By fecha_fin_historial = By.xpath("//*[@id=\"P46_FECHA_FIN_HIST|input\"]");

	// Localizadores fechas Pronostico
	By fecha_inicio_pronostico = By.xpath("(//input[@id='P46_FECHA_INICIO_PRO|input'])[1]");
	By fecha_fin_pronostico = By.xpath("(//input[@id='P46_FECHA_FIN_PRO|input'])[1]");

	// Localizadores de tabla BuscarPronostico
	By buscar_pronostico = By.xpath("//span[contains(text(), 'Buscar Pron�stico')]");
	By select_cedis_pronostico = By.xpath("//*[@id=\"P46_CEDIS_PRO\"]");
	By select_cedis_pronostico_Tipo_Surtido = By.xpath("//*[@id=\"P46_TIPO_PRO\"]");
	By descargar_pronostico = By.xpath("(//span[@class='a-Button-label'][normalize-space()='Descarga'])[2]");
	By download_pronostico = By.xpath("(//button[normalize-space()='Descargar'])[1]");
	By resultado_busqueda = By.xpath("//div[@aria-label='Pronostico 5 Meses adelante']//div[@class='a-GV-bdy']//table//tbody//td[1]");

	public DescargaPronosticoCEDIS(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}

	/**
	 * Click en el boton descargar pronostico CEDIS seccion Pronostico CEDIS
	 *
	 * @author Enrique Castilleros
	 * @throws Exception
	 * @date 30/Octubre/2024
	 */
	public boolean clickDescargaPronosticoCEDIS() throws Exception {
		u.click(descarga_pronostico_cedis);
		u.hardWait(1);
		u.refresh();
		obtenerElementoVisible(u, tabla_buscar);
		testCase.addBoldTextEvidenceCurrentStep(
				"Se visualizan correctamente 2 apartados:\r\n" + "� Historial\r\n" + "� Pronostico\r\n" + "");
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		return obtenerElementoVisible(u, tabla_buscar).isDisplayed();
	}

	/**
	 * Valida de que se visualice la tabla de Tabla Pronostico CEDIS de la seccion
	 * Historial
	 *
	 * @author Enrique Castilleros
	 * @date 30/Octubre/2024
	 */
	public void validaHistorialTablaPronosticoCEDIS() throws Exception {
		obtenerElementoVisible(u, tabla_buscar);
		testCase.addBoldTextEvidenceCurrentStep(
				"Se mostrara todos los cedis de esten en el rango seleccionado de \"Fecha Inicio Historial\" y \"Fecha Fin Historial\", donde se mostrara la informacion en una tabla, el cual contrendra los encabezados:\r\n"
						+ "� Linea Surtido\r\n" + "� Demanda\r\n" + "� CEDIS\r\n" + "� Fecha\r\n" + "");
		testCase.addScreenShotCurrentStep("nombre de la imagen");

	}

	/**
	 * Envia el texto para llenar el campo historial fecha en la seccion Historial
	 *
	 * @author Enrique Castilleros
	 * @throws Exception
	 * @date 30/Octubre/2024
	 */
	public boolean textoHistorialFechas() throws Exception {
		String fechaFormateadaActual = obtenerFechaFormateada(LocalDate.now());
		String fechaFormateadaDosMesesAtras = obtenerFechaFormateada(LocalDate.now().minusMonths(2));
		
		obtenerElementoVisibleConIntentos(u, fecha_inicio_historial);
		
		testCase.addTextEvidenceCurrentStep("Fecha inicio: " + fechaFormateadaDosMesesAtras);
		testCase.addTextEvidenceCurrentStep("Fecha fin: " + fechaFormateadaActual);
		me.explicitWaitVisibilityOfElement(fecha_inicio_historial, Duration.ofSeconds(60));
		me.explicitWaitVisibilityOfElement(fecha_fin_historial, Duration.ofSeconds(60));
		
		u.sendKeys(fecha_inicio_historial, fechaFormateadaDosMesesAtras);
		u.sendKeys(fecha_fin_historial, fechaFormateadaActual);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		return obtenerElementoVisibleConIntentos(u, fecha_inicio_pronostico).isDisplayed();

	}

	/**
	 * Click en el boton Buscar en la seccion Historial
	 *
	 * @author Enrique Castilleros
	 * @throws Exception
	 * @date 30/Octubre/2024
	 */
	public boolean clickHistorialBuscarHistorial() throws Exception {
		u.click(buscar_historial);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		esperarElementoInvisible(u, loaderLocator);
		obtenerElementoVisible(u, resultado_busqueda_historial);
		testCase.addBoldTextEvidenceCurrentStep("Se mostrara todos los cedis filtrados");
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		return obtenerElementoVisible(u, resultado_busqueda_historial).isDisplayed();

	}

	/**
	 * Click en la lista desplegable CEDIS en la seccion Historial
	 *
	 * @author Enrique Castilleros
	 * @date 30/Octubre/2024
	 */
	public void clickHistorialCEDIS(String cedis) {
		WebElement selectElement = u.getDriver().findElement(select_cedis_historial);
		Select select = new Select(selectElement);
		select.selectByValue(cedis);
		testCase.addScreenShotCurrentStep("nombre de la imagen");

	}

	/**
	 * Click en boton Descargar Archivo en la seccion de Historial
	 *
	 * @author Enrique Castilleros
	 * @throws Exception
	 * @date 30/Octubre/2024
	 */
	public boolean clickHistorialDescargarArchivo() throws Exception {
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		u.click(descargar);
		u.click(download);
		obtenerElementoVisible(u, mensajeDescargaArchivo);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		testCase.addBoldTextEvidenceCurrentStep("Se descarga el archivo correctamente");
		return obtenerElementoVisible(u, mensajeDescargaArchivo).isDisplayed();
	}

	/**
	 * Envia texto de fechas en la seccion de Pronostico
	 *
	 * @author Enrique Castilleros
	 * @throws Exception
	 * @date 30/Octubre/2024
	 */

	public boolean textoPronosticoFechas() throws Exception {
		int mesesAdelante = 3; // Cambiar este n�mero para calcular otros meses hacia adelante
		Actions action;
		String fechaFormateadaActual = obtenerFechaFormateada(LocalDate.now());
		String fechaFormateadaMesesAdelante = obtenerFechaFormateada(LocalDate.now().plusMonths(mesesAdelante));
		
		obtenerElementoVisibleConIntentos(u, fecha_inicio_pronostico);

		testCase.addTextEvidenceCurrentStep("Fecha inicio: " + fechaFormateadaActual);
		testCase.addTextEvidenceCurrentStep("Fecha fin: " + fechaFormateadaMesesAdelante);
		me.explicitWaitVisibilityOfElement(fecha_inicio_pronostico, Duration.ofSeconds(60));
		me.explicitWaitVisibilityOfElement(fecha_fin_pronostico, Duration.ofSeconds(60));

		u.click(fecha_inicio_pronostico);
		u.clear(fecha_inicio_pronostico);
		u.sendKeys(fecha_inicio_pronostico, fechaFormateadaActual);
		action = new Actions(u.getDriver()); 
		action.sendKeys(Keys.TAB)
			.sendKeys(Keys.ESCAPE)
			.perform();
		
		u.click(fecha_fin_pronostico);
		u.clear(fecha_fin_pronostico);
		u.sendKeys(fecha_fin_pronostico, fechaFormateadaMesesAdelante);
		action = new Actions(u.getDriver()); 
		action.sendKeys(Keys.TAB)
			.sendKeys(Keys.ESCAPE)
			.perform();		
		
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		return obtenerElementoVisibleConIntentos(u, fecha_inicio_pronostico).isDisplayed();

	}

	/**
	 * Click en el boton buscar de la seccion pronosticos
	 *
	 * @author Enrique Castilleros
	 * @throws Exception
	 * @date 30/Octubre/2024
	 */

	public void clickPronosticoBuscar() throws Exception {
		u.click(buscar_pronostico);
		u.hardWait(2);
		esperarElementoInvisible(u, loaderLocator);
	}
	
	
	/**
	 * Valida que el objeto sea visible
	 *
	 * @author Enrique Castilleros
	 * @date 28/Noviembre/2024
	 */
	public boolean sonMostradosLosRegistros() throws Exception {
		return u.isDisplayed(resultado_busqueda);
	}

	/**
	 * Click en la lista desplegable en CEDIS de la seccion pronostico
	 *
	 * @author Enrique Castilleros
	 * @date 30/Octubre/2024
	 */

	public void selectPronosticoCEDISpronostico(String cedis) {
		WebElement selectElement = u.getDriver().findElement(select_cedis_pronostico);
		Select select = new Select(selectElement);
		select.selectByValue(cedis);
		testCase.addScreenShotCurrentStep("nombre de la imagen");

	}

	/**
	 * Click en la lista desplegable en tipo surtido de la seccion pronostico
	 *
	 * @author Enrique Castilleros
	 * @date 30/Octubre/2024
	 */

	public void selectPronosticoCEDISTipoSurtido(String tipoSurtido) {
		WebElement selectElement = u.getDriver().findElement(select_cedis_pronostico_Tipo_Surtido);
		Select select = new Select(selectElement);
		select.selectByValue(tipoSurtido);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Click en el boton de descarga de la seccion pronostico
	 *
	 * @author Enrique Castilleros
	 * @throws Exception
	 * @date 30/Octubre/2024
	 */
	public boolean clickPronosticoDescargarArchivo() throws Exception {
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		u.click(descargar_pronostico);
		u.click(download_pronostico);
		obtenerElementoVisible(u, mensajeDescargaArchivo);
		u.hardWait(5);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
		testCase.addBoldTextEvidenceCurrentStep("Se descarga el archivo correctamente");
		return obtenerElementoVisible(u, mensajeDescargaArchivo).isDisplayed();
	}

	/**
	 * Abrir archivo utilizando el programa por defecto que se tenga configurado en
	 * el sistema operativo.
	 *
	 * @author Edwin Chi
	 * @param filePath es la ruta del archivo a abrir
	 * @date 03/Enero/2025
	 * @throws IOException cuando el archivo de la ruta propocionada no se encuentre
	 *                     o no se pueda abrir
	 */
	public void openFile(Path filePath) throws IOException {
		if (filePath != null) {
			File file = filePath.toFile();
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.OPEN)) {
				desktop.open(file);
				u.hardWait(5);
			} else {
				throw new IOException("No se pudo abrir el archivo: " + filePath);
			}
		} else {
			throw new IOException("No se encontraron archivos en la ruta: " + filePath);
		}
	}
}
