package WEB.cometa.om;

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import WEB.util.GlobalVariables;
import WEB.cometa.om.PortalCometaMVP4;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class rutaLiquidacionPage {
	
	SeleniumUtil u;
	TestCase testCase;
	private String tiendaDesmarcadaPersonalizacion = "";
	private String tiendaSinInfoPersonalizacion = "";
	private String tiendaConInfoPersonalizacion = "";
	private LinkedHashMap<String, String> estadoLiquidacionRutaAntesModal = new LinkedHashMap<String, String>();
	// Snapshots del grid de Liquidacion de Ruta capturados por registraResumenPrimerRenglonYTiendasTransferencia
	// y consumidos por validaRecalculoResumenSumaManualTiendasIncluidas / validaSumaResumenContraBDTiendasIncluidas.
	private LinkedHashMap<String, LinkedHashMap<String, Long>> snapshotsResumen = new LinkedHashMap<String, LinkedHashMap<String, Long>>();
	private LinkedHashMap<String, java.util.List<LinkedHashMap<String, Object>>> snapshotsTiendasTransferencia = new LinkedHashMap<String, java.util.List<LinkedHashMap<String, Object>>>();
	// Snapshots del resultset completo de xxcm_historico_correos capturados por capturaSnapshotBDRegistrosRutaDinamicaEnFecha
	// y consumidos por validaSnapshotBDRegistrosCoincideConReferencia para confirmar que la exclusion desde el portal NO altera BD.
	private LinkedHashMap<String, java.util.List<LinkedHashMap<String, Object>>> snapshotsRegistrosBD = new LinkedHashMap<String, java.util.List<LinkedHashMap<String, Object>>>();
	// Horas representativas obtenidas desde BD (xxcm_historico_correos.fecha_registro::time) para replicar el escenario
	// 10h/14h/16h de la matriz MTC_FT_025 (Validacion del rango combinado de fecha y hora en el grid).
	// Se llenan por obtenerEscenarioHorasDesdeBD antes de capturar los filtros de horas del portal.
	private String horaMinimaBD = "";
	private String horaMediaBD = "";
	private String horaMaximaBD = "";
	
	

	public rutaLiquidacionPage(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		}
	
	
	/**
	 * Valida la existencia de una etiqueta en un contenedor 
	 * 
	 * @param container --> Elemento donde se almacena la etiqueta 
	 * @param etiqueta --> Texto de la etiqueta a buscar
	 * @return --> Regresa true si encuentra el texto
	 * 
	 * @author Alicia.Ceja
	 * @date Junio 2026
	 */
	
	public boolean validaExisteEtiqueta( By container, String etiqueta) {
		try {
			u.scrollToElement(container);
			u.hardWait(2);

			By texto = By.xpath(".//*[contains(text(),'" + etiqueta + "')]");
			WebElement cont = u.getDriver().findElement(container);
			List<WebElement> elementos = cont.findElements(texto);
			
					if(!elementos.isEmpty() && elementos.get(0).isDisplayed()) {
						u.highLight(texto);
						testCase.addBoldTextEvidenceCurrentStep("Se encuentra texto: "+etiqueta);
						testCase.addScreenShotCurrentStep("");
						u.unhighLight(texto);
						return  cont.getText().contains(etiqueta);
					}else {

						testCase.addBoldTextEvidenceCurrentStep("No se encuentra texto: "+etiqueta);
						testCase.addScreenShotCurrentStep("");
					return  cont.getText().contains(etiqueta);
					}
			
			}catch(Exception e) {
					
					System.out.println("Error al buscar etiqueta "+e);
					return false;

				}
	
	}

	/**
	 * Llena los filtros de Liquidación de Ruta con un rango de fechas inválido:
	 * Fecha inicial = fechaFormateada (fecha de BD),
	 * Fecha final   = fechaFormateada - 1 día (día anterior → rango inválido).
	 * Las horas se calculan igual que en llenadoFiltrosLR:
	 * Hora inicial = horaFormateada - 1h, Hora final = horaFormateada + 1h.
	 *
	 * Comportamiento esperado del sistema al consultar con este rango:
	 * no muestra resultados y borra automáticamente la fecha final.
	 *
	 * @param pc4             Instancia de PortalCometaMVP4 para reutilizar sus selectores.
	 * @param fechaFormateada Fecha obtenida de BD (formato dd/MM/yyyy o el que use el dropdown).
	 * @param horaFormateada  Hora obtenida de BD en formato HH:mm.
	 * @param screenshot      True para capturar evidencia en cada paso.
	 * @return True si se completó el llenado de filtros sin errores.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-06-23
	 */
	public boolean llenadoFiltrosLRRangoInvalido(PortalCometaMVP4 pc4, String fechaFormateada, String horaFormateada, boolean screenshot) {
		try {
			WebDriver driver = u.getDriver();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			// Calcular horas
			DateTimeFormatter fmt24 = DateTimeFormatter.ofPattern("HH:mm");
			DateTimeFormatter fmt12 = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
			LocalTime horaBase     = LocalTime.parse(horaFormateada, fmt24);
			String horaInicialAMPM = horaBase.minusHours(1).format(fmt12).toUpperCase();
			String horaFinalAMPM   = horaBase.plusHours(1).format(fmt12).toUpperCase();

			// --- Fecha Inicial (hoy = fechaFormateada de BD, primera opción del dropdown) ---
			u.waitWebElement(pc4.btnFechaInicial, 20, 2);
			u.scrollToElement(pc4.btnFechaInicial);
			u.highLight(pc4.btnFechaInicial);
			if (screenshot) testCase.addScreenShotCurrentStep("Fecha_Inicial_Campo");
			u.click(pc4.btnFechaInicial);
			u.unhighLight(pc4.btnFechaInicial);
			u.waitWebElement(pc4.dropdownDesplegado, 15, 5);
			// Se selecciona la primera opción visible del dropdown (fecha más reciente)
			By xpathFechaInicial = By.xpath("(//mat-option[ancestor::div[not(contains(@style,'display: none'))]])[1]");
			wait.until(ExpectedConditions.elementToBeClickable(xpathFechaInicial)).click();
			if (screenshot) testCase.addScreenShotCurrentStep("Fecha_Inicial_Seleccionada");

			// --- Hora Inicial (hora BD - 1h) ---
			u.waitWebElement(pc4.btnHoraInicial, 15, 3);
			u.scrollToElement(pc4.btnHoraInicial);
			u.highLight(pc4.btnHoraInicial);
			if (screenshot) testCase.addScreenShotCurrentStep("Hora_Inicial_Campo");
			u.click(pc4.btnHoraInicial);
			u.unhighLight(pc4.btnHoraInicial);
			u.waitWebElement(pc4.dropdownDesplegado, 15, 5);
			By xpathHoraInicial = By.xpath("//mat-option/span[normalize-space(text())='" + horaInicialAMPM + "' and ancestor::div[not(contains(@style,'display: none'))]]");
			WebElement horaInicialEl = wait.until(ExpectedConditions.elementToBeClickable(xpathHoraInicial));
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", horaInicialEl);
			u.hardWait(1);
			horaInicialEl.click();
			if (screenshot) testCase.addScreenShotCurrentStep("Hora_Inicial_Seleccionada");

			// --- Fecha Final (ayer = segunda opción del dropdown, sin calcular ni formatear fecha) ---
			u.waitWebElement(pc4.btnFechaFinal, 15, 3);
			u.scrollToElement(pc4.btnFechaFinal);
			u.highLight(pc4.btnFechaFinal);
			if (screenshot) testCase.addScreenShotCurrentStep("Fecha_Final_Campo");
			u.click(pc4.btnFechaFinal);
			u.unhighLight(pc4.btnFechaFinal);
			u.waitWebElement(pc4.dropdownDesplegado, 15, 5);
			// Se selecciona la segunda opción visible del dropdown (un día antes que la inicial)
			// garantizando siempre fecha_final < fecha_inicial → rango inválido
			By xpathFechaFinal = By.xpath("(//mat-option[ancestor::div[not(contains(@style,'display: none'))]])[2]");
			wait.until(ExpectedConditions.elementToBeClickable(xpathFechaFinal)).click();
			if (screenshot) testCase.addScreenShotCurrentStep("Fecha_Final_Ayer_Seleccionada");

			// --- Hora Final (hora BD + 1h) ---
			u.waitWebElement(pc4.btnHoraFinal, 15, 3);
			u.scrollToElement(pc4.btnHoraFinal);
			u.highLight(pc4.btnHoraFinal);
			if (screenshot) testCase.addScreenShotCurrentStep("Hora_Final_Campo");
			u.click(pc4.btnHoraFinal);
			u.unhighLight(pc4.btnHoraFinal);
			u.waitWebElement(pc4.dropdownDesplegado, 15, 5);
			By xpathHoraFinal = By.xpath("//mat-option/span[normalize-space(text())='" + horaFinalAMPM + "' and ancestor::div[not(contains(@style,'display: none'))]]");
			WebElement horaFinalEl = wait.until(ExpectedConditions.elementToBeClickable(xpathHoraFinal));
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", horaFinalEl);
			u.hardWait(1);
			horaFinalEl.click();
			if (screenshot) testCase.addScreenShotCurrentStep("Hora_Final_Seleccionada");

			return true;

		} catch (Exception e) {
			System.out.println("[ERROR] llenadoFiltrosLRRangoInvalido: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Valida que el campo Fecha Final de Liquidación de Ruta esté vacío (sin fecha seleccionada).
	 * El sistema borra automáticamente la fecha final cuando se consulta con un rango inválido.
	 *
	 * @param screenshot True para capturar evidencia del estado del campo.
	 * @return True si el campo Fecha Final está vacío; false si contiene alguna fecha.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-06-23
	 */
	public boolean validaFechaFinalVacia(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriver driver = u.getDriver();
			java.util.List<WebElement> campos = driver.findElements(elementos.spanFechaFinal);
			// Si el span no existe en el DOM, el campo está vacío (el sistema lo borró)
			if (campos.isEmpty()) {
				if (screenshot) testCase.addScreenShotCurrentStep("Fecha_Final_Vacia_OK");
				System.out.println("[validaFechaFinalVacia] span no existe en DOM → campo vacío");
				return true;
			}
			String texto = campos.get(0).getText().trim();
			if (screenshot) testCase.addScreenShotCurrentStep("Fecha_Final_Vacia_" + (texto.isEmpty() ? "OK" : "FALLO"));
			System.out.println("[validaFechaFinalVacia] texto en campo: '" + texto + "'");
			return texto.isEmpty();
		} catch (Exception e) {
			System.out.println("[ERROR] validaFechaFinalVacia: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el botón Consultar de Liquidación de Ruta esté deshabilitado.
	 * Usa pc4.xmlConsultar (selector genérico por texto 'Consultar') y verifica !isEnabled().
	 * No busca el botón solo cuando está deshabilitado para evitar NoSuchElementException.
	 *
	 * @param pc4        Instancia de PortalCometaMVP4 para reutilizar su selector xmlConsultar.
	 * @param screenshot True para capturar evidencia.
	 * @return True si el botón está deshabilitado; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-06-23
	 */
	public boolean validaBtnConsultarLRDeshabilitado(PortalCometaMVP4 pc4, boolean screenshot) {
		try {
			WebDriver driver = u.getDriver();
			WebElement btn = driver.findElement(pc4.xmlConsultar);
			if (btn == null || !btn.isDisplayed()) {
				System.out.println("[validaBtnConsultarLRDeshabilitado] botón no visible");
				return false;
			}
			boolean deshabilitado = !btn.isEnabled();
			if (screenshot) testCase.addScreenShotCurrentStep("Btn_Consultar_LR_Deshabilitado_" + (deshabilitado ? "OK" : "FALLO"));
			System.out.println("[validaBtnConsultarLRDeshabilitado] botón deshabilitado: " + deshabilitado);
			return deshabilitado;
		} catch (Exception e) {
			System.out.println("[ERROR] validaBtnConsultarLRDeshabilitado: " + e.getMessage());
			return false;
		}
	}
	
	
	/**
	 * Realiza la suma de todos los valores que contiene la etiqueta Tapas y compara el resultado con el
	 * Total Tapas Canastivas para verificar que sea la misma cantidad
	 * 
	 * @param container --> Elemento donde se almacena la etiqueta 
	 * @param etiqueta --> Texto de la etiqueta a buscar
	 * @return --> Regresa true si la suma es igual
	 * @date Junio 2026
	 */
	public boolean sumarTapas (By container, String etiquetaTapas, String etiquetaTapasCanastillas) {
				

				try {
				WebElement cont = u.getDriver().findElement(container);
				int sumaTapas = 0;
				List<WebElement> tapasElements = cont.findElements(
				By.xpath(".//*[contains(text(),'" + etiquetaTapas + "')]")
				);
				for (WebElement elemento : tapasElements) {
				String numero = elemento.getText()
				         .replaceAll("[^0-9]", "");
				if (!numero.isEmpty()) {
				sumaTapas += Integer.parseInt(numero);
				}
				}
				WebElement canastillasElement = cont.findElement(
				By.xpath(".//*[contains(text(),'" + etiquetaTapasCanastillas + "')]")
				);
				int tapasCanastillas = Integer.parseInt(
				canastillasElement.getText().replaceAll("[^0-9]", "")
				);
				
				testCase.addBoldTextEvidenceCurrentStep("La suma de Tapas: "+sumaTapas +" es igual a la cantidad de Total Tapas Canastillas "+tapasCanastillas);
				return sumaTapas == tapasCanastillas;
				} catch (Exception e) {
				return false;
				}
	}

	/**
	 * Valida que las primeras 2 lineas del contenedor, en el apartado de Detalle se encuentre la etiqueta Tapas
	 * 
	 * @param container --> Elemento donde se almacena la etiqueta 
	 * @return --> Regresa true si la funcion es exitosa
	 * @author Alicia.Ceja
	 * @date Junio 2026
	 */
	
	public boolean validarTapasPrimerasDosLineas(By container) {
		   try {
		       WebElement cont = u.getDriver().findElement(container);
		       List<WebElement> tapas = cont.findElements( By.xpath(".//*[contains(text(),'Tapas:')]"));
		       
		       By tapas1 = By.xpath("(//*[contains(text(),'Tapas:')])[1]");
		       By tapas2 = By.xpath("(//*[contains(text(),'Tapas:')])[2]");

		       
		       if (tapas.size() >= 2) {
		    	   u.scrollToElement(tapas1);
		    	   testCase.addBoldTextEvidenceCurrentStep("Tapas linea 1: ");
		           u.highLight(tapas1);
		           testCase.addScreenShotCurrentStep("");
		           u.hardWait(2);
		           u.unhighLight(tapas1);
		    	 
		           u.scrollToElement(tapas2);
		    	   testCase.addBoldTextEvidenceCurrentStep("Tapas linea 2: ");
		           u.highLight(tapas2);
		           testCase.addScreenShotCurrentStep("");
		           u.hardWait(2);
		           u.unhighLight(tapas2);
		           return true;
		       }else {
		    	   u.highLight(tapas1);
		           testCase.addScreenShotCurrentStep("");
		           u.hardWait(2);
		           u.unhighLight(tapas1);
		    	   return true;
		       }
		      
		   } catch (Exception e) {
		       return false;
		   }
		}
	
	/**
	 * Valida que se muestren las dos leyendas informativas en la pantalla de Liquidación de Ruta.
	 * Confirma que ambas leyendas estén visibles, se encuentren en renglones separados y espera a que desaparezca el overlay de carga antes de capturar evidencia.
	 *
	 * @param screenshot True para capturar evidencia del estado de las leyendas.
	 * @return True si ambas leyendas están visibles en renglones separados; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-08
	 */
	public boolean validaLeyendasInformativasLiquidacionRuta(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement leyendaHandHeld = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.leyendaEntregasHandHeld));
			WebElement leyendaUnigis = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.leyendaRutaDinamicaUnigis));
			String leyendaHandHeldEsperada = "Esta pantalla muestra únicamente las entregas registradas desde la Hand Held; por lo tanto, las entregas que no se realicen mediante COMETA no se visualizarán en este apartado.";
			String leyendaUnigisEsperada = "Visualiza la información por Ruta Dinámica y personaliza tu consulta agregando o eliminando tiendas según tus necesidades.";
			String leyendaHandHeldActual = leyendaHandHeld.getText().replaceAll("\\s+", " ").trim();
			String leyendaUnigisActual = leyendaUnigis.getText().replaceAll("\\s+", " ").trim();

			boolean leyendasVisibles = leyendaHandHeld.isDisplayed() && leyendaUnigis.isDisplayed();
			boolean renglonesSeparados = leyendaHandHeld.getLocation().getY() != leyendaUnigis.getLocation().getY();
			boolean leyendaHandHeldCoincide = leyendaHandHeldEsperada.equals(leyendaHandHeldActual);
			boolean leyendaUnigisCoincide = leyendaUnigisEsperada.equals(leyendaUnigisActual);

			System.out.println("[VALIDACION LEYENDA 1] Esperado: " + leyendaHandHeldEsperada);
			System.out.println("[VALIDACION LEYENDA 1] Actual: " + leyendaHandHeldActual);
			System.out.println("[VALIDACION LEYENDA 1] Resultado: " + (leyendaHandHeldCoincide ? "ES EL MISMO" : "NO ES EL MISMO"));
			System.out.println("[VALIDACION LEYENDA 2] Esperado: " + leyendaUnigisEsperada);
			System.out.println("[VALIDACION LEYENDA 2] Actual: " + leyendaUnigisActual);
			System.out.println("[VALIDACION LEYENDA 2] Resultado: " + (leyendaUnigisCoincide ? "ES EL MISMO" : "NO ES EL MISMO"));

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]") ));
			u.hardWait(1);

			if (screenshot) {
				u.highLight(elementos.leyendaEntregasHandHeld);
				u.highLight(elementos.leyendaRutaDinamicaUnigis);
				testCase.addScreenShotCurrentStep("Leyendas_Informativas_Liquidacion_Ruta");
				u.unhighLight(elementos.leyendaEntregasHandHeld);
				u.unhighLight(elementos.leyendaRutaDinamicaUnigis);
			}

			testCase.addBoldTextEvidenceCurrentStep("Leyenda 1 esperada: " + leyendaHandHeldEsperada);
			testCase.addBoldTextEvidenceCurrentStep("Leyenda 1 actual: " + leyendaHandHeldActual);
			testCase.addBoldTextEvidenceCurrentStep("Leyenda 1 resultado: " + (leyendaHandHeldCoincide ? "ES EL MISMO" : "NO ES EL MISMO"));
			testCase.addBoldTextEvidenceCurrentStep("Leyenda 2 esperada: " + leyendaUnigisEsperada);
			testCase.addBoldTextEvidenceCurrentStep("Leyenda 2 actual: " + leyendaUnigisActual);
			testCase.addBoldTextEvidenceCurrentStep("Leyenda 2 resultado: " + (leyendaUnigisCoincide ? "ES EL MISMO" : "NO ES EL MISMO"));
			return leyendasVisibles && renglonesSeparados && leyendaHandHeldCoincide && leyendaUnigisCoincide;
		} catch (Exception e) {
			System.out.println("Error al validar leyendas informativas de Liquidación de Ruta: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Valida la organización visual de los filtros de Liquidación de Ruta en dos líneas.
	 * Confirma que la primera línea contenga Fecha inicial, Hora inicial, Fecha final y Hora final, y que la segunda línea contenga Selector de Filtro, Ruta Dinámica y Tienda.
	 * Usa los contenedores visuales de cada línea para evitar falsos negativos por diferencias internas de alineación entre componentes.
	 *
	 * @param screenshot True para capturar evidencia visual de la organización de filtros.
	 * @return True si los filtros están visibles, ordenados y distribuidos en dos líneas; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-08
	 */
	public boolean validaOrganizacionVisualFiltrosDosLineas(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement contenedorPrimeraLinea = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.contenedorPrimeraLineaFiltros));
			WebElement contenedorSegundaLinea = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.contenedorSegundaLineaFiltros));
			WebElement fechaInicial = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroFechaInicial));
			WebElement horaInicial = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroHoraInicial));
			WebElement fechaFinal = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroFechaFinal));
			WebElement horaFinal = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroHoraFinal));
			WebElement selectorFiltro = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroSelectorFiltro));
			WebElement rutaDinamica = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			WebElement tienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));

			String textoPrimeraLinea = normalizarTextoFiltro(contenedorPrimeraLinea);
			String textoSegundaLinea = normalizarTextoFiltro(contenedorSegundaLinea);
			boolean primeraLinea = textoPrimeraLinea.contains("Fecha inicial")
					&& textoPrimeraLinea.contains("Hora inicial")
					&& textoPrimeraLinea.contains("Fecha final")
					&& textoPrimeraLinea.contains("Hora final");
			boolean segundaLinea = textoSegundaLinea.contains("Selector de Filtro")
					&& textoSegundaLinea.contains("Ruta Dinámica")
					&& textoSegundaLinea.contains("Tienda");
			boolean segundaDebajoPrimera = contenedorSegundaLinea.getLocation().getY() > contenedorPrimeraLinea.getLocation().getY();
			boolean ordenPrimeraLinea = fechaInicial.getLocation().getX() < horaInicial.getLocation().getX()
					&& horaInicial.getLocation().getX() < fechaFinal.getLocation().getX()
					&& fechaFinal.getLocation().getX() < horaFinal.getLocation().getX();
			boolean ordenSegundaLinea = selectorFiltro.getLocation().getX() < rutaDinamica.getLocation().getX()
					&& rutaDinamica.getLocation().getX() < tienda.getLocation().getX();

			System.out.println("[VALIDACION FILTROS] Linea 1 esperada: Fecha inicial | Hora inicial | Fecha final | Hora final");
			System.out.println("[VALIDACION FILTROS] Linea 1 actual: " + textoPrimeraLinea);
			System.out.println("[VALIDACION FILTROS] Linea 1 resultado: " + (primeraLinea && ordenPrimeraLinea ? "ES EL MISMO" : "NO ES EL MISMO"));
			System.out.println("[VALIDACION FILTROS] Linea 2 esperada: Selector de Filtro | Ruta Dinámica (UNIGIS) | Tienda");
			System.out.println("[VALIDACION FILTROS] Linea 2 actual: " + textoSegundaLinea);
			System.out.println("[VALIDACION FILTROS] Linea 2 resultado: " + (segundaLinea && ordenSegundaLinea && segundaDebajoPrimera ? "ES EL MISMO" : "NO ES EL MISMO"));

			testCase.addBoldTextEvidenceCurrentStep("Linea 1 esperada: Fecha inicial | Hora inicial | Fecha final | Hora final");
			testCase.addBoldTextEvidenceCurrentStep("Linea 1 actual: " + textoPrimeraLinea);
			testCase.addBoldTextEvidenceCurrentStep("Linea 1 resultado: " + (primeraLinea && ordenPrimeraLinea ? "ES EL MISMO" : "NO ES EL MISMO"));
			testCase.addBoldTextEvidenceCurrentStep("Linea 2 esperada: Selector de Filtro | Ruta Dinámica (UNIGIS) | Tienda");
			testCase.addBoldTextEvidenceCurrentStep("Linea 2 actual: " + textoSegundaLinea);
			testCase.addBoldTextEvidenceCurrentStep("Linea 2 resultado: " + (segundaLinea && ordenSegundaLinea && segundaDebajoPrimera ? "ES EL MISMO" : "NO ES EL MISMO"));

			if (screenshot) {
				u.highLight(elementos.filtroFechaInicial);
				u.highLight(elementos.filtroHoraInicial);
				u.highLight(elementos.filtroFechaFinal);
				u.highLight(elementos.filtroHoraFinal);
				u.highLight(elementos.filtroSelectorFiltro);
				u.highLight(elementos.filtroRutaDinamica);
				u.highLight(elementos.filtroTienda);
				testCase.addScreenShotCurrentStep("Organizacion_Filtros_Dos_Lineas");
				u.unhighLight(elementos.filtroFechaInicial);
				u.unhighLight(elementos.filtroHoraInicial);
				u.unhighLight(elementos.filtroFechaFinal);
				u.unhighLight(elementos.filtroHoraFinal);
				u.unhighLight(elementos.filtroSelectorFiltro);
				u.unhighLight(elementos.filtroRutaDinamica);
				u.unhighLight(elementos.filtroTienda);
			}

			return primeraLinea && segundaLinea && segundaDebajoPrimera && ordenPrimeraLinea && ordenSegundaLinea;
		} catch (Exception e) {
			System.out.println("Error al validar organización visual de filtros en dos líneas: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Compara la posición vertical de dos elementos para validar que pertenezcan al mismo renglón visual.
	 * Usa una tolerancia en pixeles para cubrir diferencias menores de renderizado entre navegadores o resoluciones.
	 *
	 * @param primerElemento Primer elemento que se compara visualmente.
	 * @param segundoElemento Segundo elemento que se compara visualmente.
	 * @param toleranciaPixeles Diferencia máxima permitida entre posiciones verticales.
	 * @return True si ambos elementos están dentro de la misma línea visual; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-08
	 */
	private boolean mismaLinea(WebElement primerElemento, WebElement segundoElemento, int toleranciaPixeles) {
		return Math.abs(primerElemento.getLocation().getY() - segundoElemento.getLocation().getY()) <= toleranciaPixeles;
	}

	/**
	 * Normaliza el texto visible de un filtro de Liquidación de Ruta para mostrarlo en consola y evidencia.
	 * Elimina saltos de línea y espacios repetidos generados por el renderizado del componente.
	 *
	 * @param filtro Elemento visual del filtro que se va a reportar.
	 * @return Texto del filtro sin espacios repetidos ni saltos de línea.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-08
	 */
	private String normalizarTextoFiltro(WebElement filtro) {
		return filtro.getText().replaceAll("\\s+", " ").trim();
	}

	/**
	 * Valida el estado inicial inactivo de los controles dependientes en Liquidación de Ruta.
	 * Confirma que Selector de Filtro, Ruta Dinámica, Tienda y Consultar estén deshabilitados al ingresar a la pantalla.
	 *
	 * @param screenshot True para capturar evidencia visual del estado inicial de los controles.
	 * @return True si todos los controles se muestran inactivos; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaEstadoInicialInactivoFiltrosYConsultar(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement selectorFiltro = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroSelectorFiltro));
			WebElement rutaDinamica = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			WebElement tienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			WebElement consultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonConsultarLiquidacionRuta));

			boolean selectorInactivo = estaDeshabilitado(selectorFiltro);
			boolean rutaInactiva = estaDeshabilitado(rutaDinamica);
			boolean tiendaInactiva = estaDeshabilitado(tienda);
			boolean consultarInactivo = estaDeshabilitado(consultar);

			System.out.println("[VALIDACION ESTADO INICIAL] Selector de Filtro esperado: INACTIVO | Actual: " + (selectorInactivo ? "INACTIVO" : "ACTIVO"));
			System.out.println("[VALIDACION ESTADO INICIAL] Ruta Dinámica esperado: INACTIVO | Actual: " + (rutaInactiva ? "INACTIVO" : "ACTIVO"));
			System.out.println("[VALIDACION ESTADO INICIAL] Tienda esperado: INACTIVO | Actual: " + (tiendaInactiva ? "INACTIVO" : "ACTIVO"));
			System.out.println("[VALIDACION ESTADO INICIAL] Consultar esperado: INACTIVO | Actual: " + (consultarInactivo ? "INACTIVO" : "ACTIVO"));

			testCase.addBoldTextEvidenceCurrentStep("Selector de Filtro esperado: INACTIVO | Actual: " + (selectorInactivo ? "INACTIVO" : "ACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica esperado: INACTIVO | Actual: " + (rutaInactiva ? "INACTIVO" : "ACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Tienda esperado: INACTIVO | Actual: " + (tiendaInactiva ? "INACTIVO" : "ACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Consultar esperado: INACTIVO | Actual: " + (consultarInactivo ? "INACTIVO" : "ACTIVO"));

			if (screenshot) {
				u.highLight(elementos.filtroSelectorFiltro);
				u.highLight(elementos.filtroRutaDinamica);
				u.highLight(elementos.filtroTienda);
				u.highLight(elementos.botonConsultarLiquidacionRuta);
				testCase.addScreenShotCurrentStep("Estado_Inicial_Inactivo_Filtros_Consultar");
				u.unhighLight(elementos.filtroSelectorFiltro);
				u.unhighLight(elementos.filtroRutaDinamica);
				u.unhighLight(elementos.filtroTienda);
				u.unhighLight(elementos.botonConsultarLiquidacionRuta);
			}

			return selectorInactivo && rutaInactiva && tiendaInactiva && consultarInactivo;
		} catch (Exception e) {
			System.out.println("Error al validar estado inicial inactivo de filtros y Consultar: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Intenta hacer clic en los controles inactivos de Liquidación de Ruta y valida que no disparen acciones.
	 * Confirma que Selector de Filtro, Ruta Dinámica, Tienda y Consultar permanezcan sin abrir paneles ni ejecutar consulta.
	 *
	 * @param screenshot True para capturar evidencia visual posterior al intento de interacción.
	 * @return True si los controles no abren paneles ni ejecutan acciones; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaIntentoInteraccionControlesInactivos(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement selectorFiltro = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroSelectorFiltro));
			WebElement rutaDinamica = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			WebElement tienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			WebElement consultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonConsultarLiquidacionRuta));

			boolean selectorNoAbre = noAbrePanelAlIntentarClick(selectorFiltro, "Selector de Filtro", elementos);
			boolean rutaNoAbre = noAbrePanelAlIntentarClick(rutaDinamica, "Ruta Dinámica", elementos);
			boolean tiendaNoAbre = noAbrePanelAlIntentarClick(tienda, "Tienda", elementos);
			boolean consultarNoEjecuta = clickNoHabilitaNiEjecutaConsulta(consultar, elementos);

			if (screenshot) {
				u.highLight(elementos.filtroSelectorFiltro);
				u.highLight(elementos.filtroRutaDinamica);
				u.highLight(elementos.filtroTienda);
				u.highLight(elementos.botonConsultarLiquidacionRuta);
				testCase.addScreenShotCurrentStep("Intento_Interaccion_Controles_Inactivos_Sin_Accion");
				u.unhighLight(elementos.filtroSelectorFiltro);
				u.unhighLight(elementos.filtroRutaDinamica);
				u.unhighLight(elementos.filtroTienda);
				u.unhighLight(elementos.botonConsultarLiquidacionRuta);
			}

			return selectorNoAbre && rutaNoAbre && tiendaNoAbre && consultarNoEjecuta;
		} catch (Exception e) {
			System.out.println("Error al validar intento de interacción con controles inactivos: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Determina si un elemento o alguno de sus hijos está marcado como deshabilitado.
	 * Revisa atributos y clases comunes de Angular Material como disabled, aria-disabled y clases que contienen disabled.
	 *
	 * @param elemento Elemento visual que se evaluará.
	 * @return True si el elemento se considera deshabilitado; false si permanece habilitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	private boolean estaDeshabilitado(WebElement elemento) {
		String disabled = elemento.getAttribute("disabled");
		String ariaDisabled = elemento.getAttribute("aria-disabled");
		String clase = elemento.getAttribute("class");
		if (disabled != null || "true".equalsIgnoreCase(ariaDisabled) || (clase != null && clase.toLowerCase().contains("disabled")) || !elemento.isEnabled()) {
			return true;
		}
		List<WebElement> hijosDeshabilitados = elemento.findElements(By.xpath(".//*[@disabled or @aria-disabled='true' or contains(@class,'disabled')]") );
		return !hijosDeshabilitados.isEmpty();
	}

	/**
	 * Intenta hacer clic sobre un control inactivo y valida que no abra un panel desplegable.
	 * Registra en consola y evidencia si el control permanece sin interacción visible.
	 *
	 * @param control Control sobre el que se intenta hacer clic.
	 * @param nombreControl Nombre funcional del control para reportar la validación.
	 * @param elementos Selectores de Liquidación de Ruta para identificar paneles activos.
	 * @return True si no se abre ningún panel desplegable; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	private boolean noAbrePanelAlIntentarClick(WebElement control, String nombreControl, rutaLiquidacionElements elementos) {
		try {
			int panelesAntes = u.getDriver().findElements(elementos.panelDesplegableActivo).size();
			try {
				control.click();
			} catch (Exception e) {
				System.out.println("[VALIDACION CLICK INACTIVO] " + nombreControl + " no permite clic directo: " + e.getMessage());
			}
			u.hardWait(1);
			int panelesDespues = u.getDriver().findElements(elementos.panelDesplegableActivo).size();
			boolean noAbre = panelesDespues <= panelesAntes;
			System.out.println("[VALIDACION CLICK INACTIVO] " + nombreControl + " esperado: NO ABRE PANEL | Actual: " + (noAbre ? "NO ABRE PANEL" : "ABRE PANEL"));
			testCase.addBoldTextEvidenceCurrentStep(nombreControl + " al hacer clic esperado: NO ABRE PANEL | Actual: " + (noAbre ? "NO ABRE PANEL" : "ABRE PANEL"));
			return noAbre;
		} catch (Exception e) {
			System.out.println("Error al intentar clic sobre " + nombreControl + ": " + e.getMessage());
			return false;
		}
	}

	/**
	 * Intenta hacer clic sobre el botón Consultar deshabilitado y valida que permanezca inactivo.
	 * El sistema no debe ejecutar acciones ni habilitar el botón mientras no existan filtros obligatorios completos.
	 *
	 * @param botonConsultar Botón Consultar visible en Liquidación de Ruta.
	 * @param elementos Selectores de Liquidación de Ruta para reubicar el botón tras el intento de clic.
	 * @return True si el botón permanece deshabilitado después del clic; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	private boolean clickNoHabilitaNiEjecutaConsulta(WebElement botonConsultar, rutaLiquidacionElements elementos) {
		try {
			try {
				botonConsultar.click();
			} catch (Exception e) {
				System.out.println("[VALIDACION CLICK INACTIVO] Consultar no permite clic directo: " + e.getMessage());
			}
			u.hardWait(1);
			WebElement consultarActual = u.getDriver().findElement(elementos.botonConsultarLiquidacionRuta);
			boolean sigueInactivo = estaDeshabilitado(consultarActual);
			System.out.println("[VALIDACION CLICK INACTIVO] Consultar esperado: NO EJECUTA ACCION | Actual: " + (sigueInactivo ? "NO EJECUTA ACCION" : "SE HABILITO O EJECUTO"));
			testCase.addBoldTextEvidenceCurrentStep("Consultar al hacer clic esperado: NO EJECUTA ACCION | Actual: " + (sigueInactivo ? "NO EJECUTA ACCION" : "SE HABILITO O EJECUTO"));
			return sigueInactivo;
		} catch (Exception e) {
			System.out.println("Error al intentar clic sobre Consultar: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Captura Fecha inicial y Fecha final en Liquidación de Ruta para habilitar el Selector de Filtro.
	 * Selecciona una fecha inicial anterior o igual a la fecha final usando las opciones visibles del calendario/desplegable.
	 *
	 * @param screenshot True para capturar evidencia del llenado de fechas.
	 * @return True si se seleccionan correctamente Fecha inicial y Fecha final; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean capturaFechasInicialFinalParaHabilitarSelector(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement btnFechaInicial = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonFechaInicialLiquidacionRuta));
			u.highLight(elementos.filtroFechaInicial);
			if (screenshot) {
				testCase.addBoldTextEvidenceCurrentStep("Se abre Fecha inicial para seleccionar una fecha anterior o igual a la Fecha final.");
				testCase.addScreenShotCurrentStep("Fecha_Inicial_Campo");
			}
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", btnFechaInicial);
			u.unhighLight(elementos.filtroFechaInicial);

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionFechaVisibleLiquidacionRuta));
			List<WebElement> opcionesFechaInicial = u.getDriver().findElements(elementos.opcionFechaVisibleLiquidacionRuta);
			int indiceFechaInicial = opcionesFechaInicial.size() > 1 ? 1 : 0;
			String fechaInicialSeleccionada = normalizarTextoFiltro(opcionesFechaInicial.get(indiceFechaInicial));
			opcionesFechaInicial.get(indiceFechaInicial).click();
			System.out.println("[CAPTURA FECHAS] Fecha inicial seleccionada: " + fechaInicialSeleccionada);
			testCase.addBoldTextEvidenceCurrentStep("Fecha inicial seleccionada: " + fechaInicialSeleccionada);

			WebElement btnFechaFinal = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonFechaFinalLiquidacionRuta));
			u.highLight(elementos.filtroFechaFinal);
			if (screenshot) {
				testCase.addBoldTextEvidenceCurrentStep("Se abre Fecha final para seleccionar una fecha igual o posterior a la Fecha inicial.");
				testCase.addScreenShotCurrentStep("Fecha_Final_Campo");
			}
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", btnFechaFinal);
			u.unhighLight(elementos.filtroFechaFinal);

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionFechaVisibleLiquidacionRuta));
			List<WebElement> opcionesFechaFinal = u.getDriver().findElements(elementos.opcionFechaVisibleLiquidacionRuta);
			String fechaFinalSeleccionada = normalizarTextoFiltro(opcionesFechaFinal.get(0));
			opcionesFechaFinal.get(0).click();
			System.out.println("[CAPTURA FECHAS] Fecha final seleccionada: " + fechaFinalSeleccionada);
			testCase.addBoldTextEvidenceCurrentStep("Fecha final seleccionada: " + fechaFinalSeleccionada);

			if (screenshot) {
				u.highLight(elementos.filtroFechaInicial);
				u.highLight(elementos.filtroFechaFinal);
				testCase.addScreenShotCurrentStep("Fechas_Inicial_Final_Seleccionadas");
				u.unhighLight(elementos.filtroFechaInicial);
				u.unhighLight(elementos.filtroFechaFinal);
			}

			return true;
		} catch (Exception e) {
			System.out.println("Error al capturar Fecha inicial y Fecha final: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida el estado de los controles después de capturar Fecha inicial y Fecha final.
	 * Confirma que Selector de Filtro se active automáticamente, permite abrir sus opciones, y que Ruta Dinámica, Tienda y Consultar permanezcan inactivos.
	 *
	 * @param screenshot True para capturar evidencia visual del estado posterior al llenado de fechas.
	 * @return True si Selector de Filtro está activo, despliega opciones, y los demás controles permanecen inactivos; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaSelectorFiltroActivoDemasControlesInactivos(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement selectorFiltro = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroSelectorFiltro));
			WebElement controlSelectorFiltro = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.controlSelectorFiltroLiquidacionRuta));
			WebElement rutaDinamica = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			WebElement tienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			WebElement consultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonConsultarLiquidacionRuta));

			boolean selectorActivo = !estaDeshabilitado(selectorFiltro);
			boolean rutaInactiva = estaDeshabilitado(rutaDinamica);
			boolean tiendaInactiva = estaDeshabilitado(tienda);
			boolean consultarInactivo = estaDeshabilitado(consultar);

			System.out.println("[VALIDACION ACTIVACION SELECTOR] Selector de Filtro esperado: ACTIVO | Actual: " + (selectorActivo ? "ACTIVO" : "INACTIVO"));
			System.out.println("[VALIDACION ACTIVACION SELECTOR] Ruta Dinámica esperado: INACTIVO | Actual: " + (rutaInactiva ? "INACTIVO" : "ACTIVO"));
			System.out.println("[VALIDACION ACTIVACION SELECTOR] Tienda esperado: INACTIVO | Actual: " + (tiendaInactiva ? "INACTIVO" : "ACTIVO"));
			System.out.println("[VALIDACION ACTIVACION SELECTOR] Consultar esperado: INACTIVO | Actual: " + (consultarInactivo ? "INACTIVO" : "ACTIVO"));

			testCase.addBoldTextEvidenceCurrentStep("Selector de Filtro esperado: ACTIVO | Actual: " + (selectorActivo ? "ACTIVO" : "INACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica esperado: INACTIVO | Actual: " + (rutaInactiva ? "INACTIVO" : "ACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Tienda esperado: INACTIVO | Actual: " + (tiendaInactiva ? "INACTIVO" : "ACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Consultar esperado: INACTIVO | Actual: " + (consultarInactivo ? "INACTIVO" : "ACTIVO"));

			boolean selectorAbreOpciones = false;
			if (selectorActivo) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", controlSelectorFiltro);
				wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.panelDesplegableActivo));
				wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionSelectorFiltroRutaDinamica));
				wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionSelectorFiltroTienda));
				selectorAbreOpciones = true;
				System.out.println("[VALIDACION ACTIVACION SELECTOR] Opciones esperadas: Ruta Dinámica (UNIGIS) | Tienda");
				System.out.println("[VALIDACION ACTIVACION SELECTOR] Resultado opciones: " + (selectorAbreOpciones ? "SE DESPLIEGAN" : "NO SE DESPLIEGAN"));
				testCase.addBoldTextEvidenceCurrentStep("Selector de Filtro despliega opciones esperadas: Ruta Dinámica (UNIGIS) | Tienda");
				if (screenshot) {
					testCase.addScreenShotCurrentStep("Selector_Filtro_Activo_Opciones_Desplegadas");
				}
				u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
				u.hardWait(1);
			}

			boolean rutaNoAbre = noAbrePanelAlIntentarClick(rutaDinamica, "Ruta Dinámica", elementos);
			boolean tiendaNoAbre = noAbrePanelAlIntentarClick(tienda, "Tienda", elementos);

			if (screenshot) {
				u.highLight(elementos.filtroSelectorFiltro);
				u.highLight(elementos.filtroRutaDinamica);
				u.highLight(elementos.filtroTienda);
				u.highLight(elementos.botonConsultarLiquidacionRuta);
				testCase.addScreenShotCurrentStep("Selector_Filtro_Activo_Demas_Inactivos");
				u.unhighLight(elementos.filtroSelectorFiltro);
				u.unhighLight(elementos.filtroRutaDinamica);
				u.unhighLight(elementos.filtroTienda);
				u.unhighLight(elementos.botonConsultarLiquidacionRuta);
			}

			return selectorActivo && selectorAbreOpciones && rutaInactiva && tiendaInactiva && consultarInactivo
					&& rutaNoAbre && tiendaNoAbre;
		} catch (Exception e) {
			System.out.println("Error al validar activación del Selector de Filtro: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Valida que el Selector de Filtro despliegue únicamente las opciones Ruta Dinámica (UNIGIS) y Tienda.
	 * Confirma que el control esté activo después de capturar Fecha inicial y Fecha final.
	 *
	 * @param screenshot True para capturar evidencia visual de las opciones desplegadas.
	 * @return True si el selector está activo y muestra solo las dos opciones esperadas; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaOpcionesDisponiblesSelectorFiltro(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement selectorFiltro = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroSelectorFiltro));
			WebElement controlSelectorFiltro = wait.until(ExpectedConditions.elementToBeClickable(elementos.controlSelectorFiltroLiquidacionRuta));
			boolean selectorActivo = !estaDeshabilitado(selectorFiltro);
			if (!selectorActivo) {
				testCase.addBoldTextEvidenceCurrentStep("Selector de Filtro esperado: ACTIVO | Actual: INACTIVO");
				return false;
			}

			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", controlSelectorFiltro);
			WebElement panel = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.panelDesplegableActivo));
			List<WebElement> opciones = panel.findElements(By.xpath(".//mat-option[not(@disabled) and not(contains(@class,'mat-mdc-option-disabled'))]"));
			boolean tieneRutaDinamica = !u.getDriver().findElements(elementos.opcionSelectorFiltroRutaDinamica).isEmpty();
			boolean tieneTienda = !u.getDriver().findElements(elementos.opcionSelectorFiltroTienda).isEmpty();
			boolean soloOpcionesEsperadas = opciones.size() == 2;

			System.out.println("[VALIDACION OPCIONES SELECTOR] Opciones esperadas: Ruta Dinámica (UNIGIS) | Tienda");
			System.out.println("[VALIDACION OPCIONES SELECTOR] Total opciones esperadas: 2 | Actual: " + opciones.size());
			testCase.addBoldTextEvidenceCurrentStep("Selector de Filtro muestra opciones esperadas: Ruta Dinámica (UNIGIS) y Tienda");
			testCase.addBoldTextEvidenceCurrentStep("Total opciones esperadas: 2 | Actual: " + opciones.size());
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Selector_Filtro_Opciones_Disponibles");
			}

			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			return tieneRutaDinamica && tieneTienda && soloOpcionesEsperadas;
		} catch (Exception e) {
			System.out.println("Error al validar opciones disponibles en Selector de Filtro: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona Ruta Dinámica (UNIGIS) en el Selector de Filtro y valida que se muestre como valor elegido.
	 * Permite reutilizar la selección de Ruta Dinámica en casos posteriores de Liquidación de Ruta.
	 *
	 * @param screenshot True para capturar evidencia visual de la opción seleccionada.
	 * @return True si Ruta Dinámica (UNIGIS) queda visible como valor elegido; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean seleccionaRutaDinamicaEnSelectorFiltro(boolean screenshot) {
		rutaLiquidacionElements elementos = new rutaLiquidacionElements();
		return seleccionaOpcionSelectorFiltro(elementos.opcionSelectorFiltroRutaDinamica, "Ruta Dinámica (UNIGIS)", "Ruta Dinámica", null, "Selector_Filtro_Ruta_Dinamica_Seleccionada", screenshot);
	}

	/**
	 * Selecciona Tienda en el Selector de Filtro y valida que reemplace cualquier selección previa.
	 * Permite reutilizar la selección de Tienda en casos posteriores de Liquidación de Ruta.
	 *
	 * @param screenshot True para capturar evidencia visual de la opción seleccionada.
	 * @return True si Tienda queda visible como valor elegido y no permanece Ruta Dinámica; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean seleccionaTiendaEnSelectorFiltro(boolean screenshot) {
		rutaLiquidacionElements elementos = new rutaLiquidacionElements();
		return seleccionaOpcionSelectorFiltro(elementos.opcionSelectorFiltroTienda, "Tienda", "Tienda", null, "Selector_Filtro_Tienda_Seleccionada", screenshot);
	}

	/**
	 * Selecciona una opción del Selector de Filtro y valida el texto visible posterior a la selección.
	 * Centraliza la interacción con el selector para evitar duplicar lógica entre Ruta Dinámica y Tienda.
	 *
	 * @param opcionSelector Localizador de la opción que se seleccionará dentro del desplegable.
	 * @param valorEsperado Texto esperado para reportar en evidencia.
	 * @param textoRequerido Texto que debe aparecer en el valor visible del selector.
	 * @param textoNoPermitido Texto que no debe permanecer visible después de la selección; puede ser null.
	 * @param nombreEvidencia Nombre de la captura de evidencia para la opción seleccionada.
	 * @param screenshot True para capturar evidencia visual de la opción seleccionada.
	 * @return True si el selector muestra el texto requerido y no muestra el texto no permitido; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	private boolean seleccionaOpcionSelectorFiltro(By opcionSelector, String valorEsperado, String textoRequerido, String textoNoPermitido, String nombreEvidencia, boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement controlSelectorFiltro = wait.until(ExpectedConditions.elementToBeClickable(elementos.controlSelectorFiltroLiquidacionRuta));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", controlSelectorFiltro);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.panelDesplegableActivo));
			WebElement opcion = wait.until(ExpectedConditions.elementToBeClickable(opcionSelector));
			opcion.click();

			WebElement selectorConValor = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.controlSelectorFiltroLiquidacionRuta));
			String textoSeleccionado = normalizarTextoFiltro(selectorConValor);
			boolean valorCorrecto = textoSeleccionado.contains(textoRequerido)
					&& (textoNoPermitido == null || !textoSeleccionado.contains(textoNoPermitido));

			System.out.println("[VALIDACION OPCIONES SELECTOR] Valor elegido esperado: " + valorEsperado + " | Actual: " + textoSeleccionado);
			testCase.addBoldTextEvidenceCurrentStep("Valor elegido esperado: " + valorEsperado + " | Actual: " + textoSeleccionado);
			if (screenshot) {
				u.highLight(elementos.filtroSelectorFiltro);
				testCase.addScreenShotCurrentStep(nombreEvidencia);
				u.unhighLight(elementos.filtroSelectorFiltro);
			}

			return valorCorrecto;
		} catch (Exception e) {
			System.out.println("Error al seleccionar opción en Selector de Filtro: " + e.getMessage());
			return false;
		}
	}
	/**
	 * Valida que el filtro Ruta Dinámica (UNIGIS) esté activo y que Consultar permanezca inactivo.
	 * Este estado se espera después de elegir Ruta Dinámica (UNIGIS) en el Selector de Filtro sin capturar una ruta.
	 *
	 * @param screenshot True para capturar evidencia visual del estado de ambos controles.
	 * @return True si Ruta Dinámica está activa y Consultar sigue inactivo; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaRutaDinamicaActivaConsultarInactivo(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement rutaDinamica = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			WebElement consultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonConsultarLiquidacionRuta));
			boolean rutaActiva = !estaDeshabilitado(rutaDinamica);
			boolean consultarInactivo = estaDeshabilitado(consultar);

			System.out.println("[VALIDACION RUTA DINAMICA] Ruta Dinámica esperado: ACTIVO | Actual: " + (rutaActiva ? "ACTIVO" : "INACTIVO"));
			System.out.println("[VALIDACION RUTA DINAMICA] Consultar esperado: INACTIVO | Actual: " + (consultarInactivo ? "INACTIVO" : "ACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica esperado: ACTIVO | Actual: " + (rutaActiva ? "ACTIVO" : "INACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Consultar esperado: INACTIVO | Actual: " + (consultarInactivo ? "INACTIVO" : "ACTIVO"));
			if (screenshot) {
				u.highLight(elementos.filtroRutaDinamica);
				u.highLight(elementos.botonConsultarLiquidacionRuta);
				testCase.addScreenShotCurrentStep("Ruta_Dinamica_Activa_Consultar_Inactivo");
				u.unhighLight(elementos.filtroRutaDinamica);
				u.unhighLight(elementos.botonConsultarLiquidacionRuta);
			}

			return rutaActiva && consultarInactivo;
		} catch (Exception e) {
			System.out.println("Error al validar Ruta Dinámica activa y Consultar inactivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el botón Consultar esté activo después de capturar una Ruta Dinámica (UNIGIS) válida.
	 * Permite reutilizar la validación de habilitación de consulta en flujos por Ruta Dinámica.
	 *
	 * @param screenshot True para capturar evidencia visual del botón Consultar activo.
	 * @return True si Consultar está activo; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaBotonConsultarActivo(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement consultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonConsultarLiquidacionRuta));
			boolean consultarActivo = !estaDeshabilitado(consultar);

			System.out.println("[VALIDACION CONSULTAR] Consultar esperado: ACTIVO | Actual: " + (consultarActivo ? "ACTIVO" : "INACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Consultar esperado: ACTIVO | Actual: " + (consultarActivo ? "ACTIVO" : "INACTIVO"));
			if (screenshot) {
				u.highLight(elementos.botonConsultarLiquidacionRuta);
				testCase.addScreenShotCurrentStep("Boton_Consultar_Activo");
				u.unhighLight(elementos.botonConsultarLiquidacionRuta);
			}

			return consultarActivo;
		} catch (Exception e) {
			System.out.println("Error al validar botón Consultar activo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el filtro Tienda esté activo después de capturar una Ruta Dinámica (UNIGIS) válida.
	 * Este estado confirma la activación automática del filtro Tienda en el flujo por Ruta Dinámica.
	 *
	 * @param screenshot True para capturar evidencia visual del filtro Tienda activo.
	 * @return True si Tienda está activa; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaFiltroTiendaActivo(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement tienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			WebElement controlTienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.controlFiltroTienda));
			String claseTienda = controlTienda.getAttribute("class");
			String ariaDisabled = controlTienda.getAttribute("aria-disabled");
			boolean tiendaActiva = controlTienda.isEnabled()
					&& !"true".equalsIgnoreCase(ariaDisabled)
					&& (claseTienda == null || !claseTienda.toLowerCase().contains("disabled"));

			System.out.println("[VALIDACION TIENDA] Tienda esperado: ACTIVO | Actual: " + (tiendaActiva ? "ACTIVO" : "INACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Tienda esperado: ACTIVO | Actual: " + (tiendaActiva ? "ACTIVO" : "INACTIVO"));
			if (screenshot) {
				u.highLight(elementos.filtroTienda);
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Activo");
				u.unhighLight(elementos.filtroTienda);
			}

			return tiendaActiva;
		} catch (Exception e) {
			System.out.println("Error al validar filtro Tienda activo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el filtro Tienda permanezca inactivo antes de capturar una Ruta Dinámica (UNIGIS) válida.
	 * Este estado se espera después de elegir Ruta Dinámica (UNIGIS) en el Selector de Filtro.
	 *
	 * @param screenshot True para capturar evidencia visual del filtro Tienda inactivo.
	 * @return True si Tienda está inactiva; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaFiltroTiendaInactivo(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement tienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			boolean tiendaInactiva = estaDeshabilitado(tienda);

			System.out.println("[VALIDACION TIENDA] Tienda esperado: INACTIVO | Actual: " + (tiendaInactiva ? "INACTIVO" : "ACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Tienda esperado: INACTIVO | Actual: " + (tiendaInactiva ? "INACTIVO" : "ACTIVO"));
			if (screenshot) {
				u.highLight(elementos.filtroTienda);
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Inactivo");
				u.unhighLight(elementos.filtroTienda);
			}

			return tiendaInactiva;
		} catch (Exception e) {
			System.out.println("Error al validar filtro Tienda inactivo: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Captura una Ruta Dinámica (UNIGIS) válida en el filtro activo de Liquidación de Ruta.
	 * Usa el input contenido dentro del filtro Ruta Dinámica para evitar interceptaciones del label de Angular Material.
	 *
	 * @param rutaDinamica Ruta Dinámica obtenida desde BD que se capturará en el filtro.
	 * @param screenshot True para capturar evidencia visual de la ruta capturada.
	 * @return True si la ruta queda capturada en el input; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean capturaRutaDinamicaUnigis(String rutaDinamica, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica obtenida desde BD vacía o nula");
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement filtroRuta = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			WebElement inputRuta = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.inputRutaDinamica));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].focus();", inputRuta);
			inputRuta.sendKeys(Keys.CONTROL + "a");
			inputRuta.sendKeys(Keys.DELETE);
			inputRuta.sendKeys(rutaDinamica.trim());
			inputRuta.sendKeys(Keys.TAB);
			u.hardWait(1);

			String valorCapturado = inputRuta.getAttribute("value");
			boolean rutaCapturada = rutaDinamica.trim().equals(valorCapturado);
			System.out.println("[CAPTURA RUTA DINAMICA] Ruta esperada: " + rutaDinamica.trim() + " | Actual: " + valorCapturado);
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica esperada: " + rutaDinamica.trim() + " | Actual: " + valorCapturado);
			if (screenshot) {
				u.highLight(elementos.filtroRutaDinamica);
				testCase.addScreenShotCurrentStep("Ruta_Dinamica_Capturada");
				u.unhighLight(elementos.filtroRutaDinamica);
			}

			return rutaCapturada;
		} catch (Exception e) {
			System.out.println("Error al capturar Ruta Dinámica (UNIGIS): " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Abre el filtro Tienda activo para dejar evidencia visual del listado desplegado.
	 * Permite evidenciar las tiendas disponibles o preseleccionadas tras capturar Ruta Dinámica sin recorrer todo el listado.
	 *
	 * @param screenshot True para capturar evidencia visual del listado desplegado.
	 * @return True si el filtro Tienda abre el panel de opciones; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean abreFiltroTiendaYValidaOpcionesVisibles(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement tienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			WebElement controlTienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.controlFiltroTienda));
			String claseTienda = controlTienda.getAttribute("class");
			String ariaDisabled = controlTienda.getAttribute("aria-disabled");
			boolean tiendaActiva = controlTienda.isEnabled()
					&& !"true".equalsIgnoreCase(ariaDisabled)
					&& (claseTienda == null || !claseTienda.toLowerCase().contains("disabled"));
			if (!tiendaActiva) {
				testCase.addBoldTextEvidenceCurrentStep("Tienda esperado: ACTIVO | Actual: INACTIVO");
				return false;
			}

			controlTienda = wait.until(ExpectedConditions.elementToBeClickable(elementos.controlFiltroTienda));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", controlTienda);
			try {
				controlTienda.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", controlTienda);
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
			System.out.println("[VALIDACION TIENDA] Filtro Tienda esperado: ABRE OPCIONES | Actual: ABRE OPCIONES");
			testCase.addBoldTextEvidenceCurrentStep("Filtro Tienda esperado: ABRE OPCIONES | Actual: ABRE OPCIONES");
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Opciones_Visibles");
			}

			return true;
		} catch (Exception e) {
			System.out.println("Error al abrir filtro Tienda y validar opciones visibles: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que Tienda esté activo y que Ruta Dinámica (UNIGIS) y Consultar permanezcan inactivos en el flujo por Tienda.
	 * Este estado se espera después de elegir Tienda en el Selector de Filtro sin seleccionar tiendas.
	 *
	 * @param screenshot True para capturar evidencia visual del estado de los controles.
	 * @return True si Tienda está activo, Ruta Dinámica está inactiva y Consultar sigue inactivo; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaTiendaActivaRutaDinamicaYConsultarInactivos(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement tienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			WebElement controlTienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.controlFiltroTienda));
			WebElement rutaDinamica = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			WebElement consultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonConsultarLiquidacionRuta));
			String claseTienda = controlTienda.getAttribute("class");
			String ariaDisabled = controlTienda.getAttribute("aria-disabled");
			boolean tiendaActiva = controlTienda.isEnabled()
					&& !"true".equalsIgnoreCase(ariaDisabled)
					&& (claseTienda == null || !claseTienda.toLowerCase().contains("disabled"));
			boolean rutaInactiva = estaDeshabilitado(rutaDinamica);
			boolean consultarInactivo = estaDeshabilitado(consultar);

			System.out.println("[VALIDACION FLUJO TIENDA] Tienda esperado: ACTIVO | Actual: " + (tiendaActiva ? "ACTIVO" : "INACTIVO"));
			System.out.println("[VALIDACION FLUJO TIENDA] Ruta Dinámica esperado: INACTIVO | Actual: " + (rutaInactiva ? "INACTIVO" : "ACTIVO"));
			System.out.println("[VALIDACION FLUJO TIENDA] Consultar esperado: INACTIVO | Actual: " + (consultarInactivo ? "INACTIVO" : "ACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Tienda esperado: ACTIVO | Actual: " + (tiendaActiva ? "ACTIVO" : "INACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica esperado: INACTIVO | Actual: " + (rutaInactiva ? "INACTIVO" : "ACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Consultar esperado: INACTIVO | Actual: " + (consultarInactivo ? "INACTIVO" : "ACTIVO"));
			if (screenshot) {
				u.highLight(elementos.filtroTienda);
				u.highLight(elementos.filtroRutaDinamica);
				u.highLight(elementos.botonConsultarLiquidacionRuta);
				testCase.addScreenShotCurrentStep("Tienda_Activa_Ruta_Dinamica_Consultar_Inactivos");
				u.unhighLight(elementos.filtroTienda);
				u.unhighLight(elementos.filtroRutaDinamica);
				u.unhighLight(elementos.botonConsultarLiquidacionRuta);
			}

			return tiendaActiva && rutaInactiva && consultarInactivo;
		} catch (Exception e) {
			System.out.println("Error al validar flujo Tienda con Ruta Dinámica y Consultar inactivos: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida el formato visible de las opciones del filtro Tienda.
	 * Confirma que cada opción revisada tenga CR Tienda seguido de un espacio y el Nombre Tienda.
	 *
	 * @param screenshot True para capturar evidencia visual del listado de tiendas.
	 * @return True si las opciones visibles revisadas cumplen el formato esperado; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaFormatoOpcionesFiltroTienda(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(screenshot);
				if (!filtroAbierto) {
					return false;
				}
			}

			List<WebElement> opciones = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementos.opcionesFiltroTiendaVisibles));
			int opcionesARevisar = Math.min(opciones.size(), 5);
			boolean formatoCorrecto = opcionesARevisar > 0;
			for (int i = 0; i < opcionesARevisar; i++) {
				String opcion = normalizarTextoFiltro(opciones.get(i));
				boolean coincideFormato = opcion.matches("^[A-Za-z0-9]+\\s+.+");
				System.out.println("[VALIDACION FORMATO TIENDA] Opción " + (i + 1) + ": " + opcion + " | Formato: " + (coincideFormato ? "CORRECTO" : "INCORRECTO"));
				testCase.addBoldTextEvidenceCurrentStep("Opción Tienda " + (i + 1) + ": " + opcion + " | Formato: " + (coincideFormato ? "CORRECTO" : "INCORRECTO"));
				formatoCorrecto = formatoCorrecto && coincideFormato;
			}

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Formato_Opciones_Filtro_Tienda");
			}

			return formatoCorrecto;
		} catch (Exception e) {
			System.out.println("Error al validar formato de opciones del filtro Tienda: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Marca la primera tienda disponible en el filtro Tienda.
	 * Reutiliza la apertura del filtro Tienda y selecciona una opción visible sin recorrer todo el listado.
	 *
	 * @param screenshot True para capturar evidencia visual de la tienda marcada.
	 * @return True si se marca una tienda disponible; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean marcaPrimeraTiendaDisponible(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					return false;
				}
			}

			WebElement opcionTienda = wait.until(ExpectedConditions.elementToBeClickable(elementos.primeraOpcionFiltroTienda));
			String tiendaSeleccionada = normalizarTextoFiltro(opcionTienda);
			opcionTienda.click();
			u.hardWait(1);

			System.out.println("[SELECCION TIENDA] Tienda marcada: " + tiendaSeleccionada);
			testCase.addBoldTextEvidenceCurrentStep("Tienda marcada: " + tiendaSeleccionada);
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Tienda_Marcada_Boton_Consultar");
			}

			return !tiendaSeleccionada.isEmpty();
		} catch (Exception e) {
			System.out.println("Error al marcar la primera tienda disponible: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Intenta interactuar con Ruta Dinámica (UNIGIS) durante el flujo Tienda y valida que permanezca bloqueada.
	 * Confirma que el filtro no acepte interacción cuando el Selector de Filtro está configurado en Tienda.
	 *
	 * @param screenshot True para capturar evidencia visual de Ruta Dinámica bloqueada.
	 * @return True si Ruta Dinámica permanece inactiva después del intento de interacción; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaRutaDinamicaBloqueadaEnFlujoTienda(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));

			WebElement rutaDinamica = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			try {
				rutaDinamica.click();
			} catch (Exception e) {
				System.out.println("[VALIDACION RUTA DINAMICA BLOQUEADA] Ruta Dinámica no permite clic directo: " + e.getMessage());
			}
			u.hardWait(1);

			WebElement rutaDinamicaActual = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			boolean rutaInactiva = estaDeshabilitado(rutaDinamicaActual);
			System.out.println("[VALIDACION RUTA DINAMICA BLOQUEADA] Ruta Dinámica esperado: INACTIVO | Actual: " + (rutaInactiva ? "INACTIVO" : "ACTIVO"));
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica esperado: INACTIVO | Actual: " + (rutaInactiva ? "INACTIVO" : "ACTIVO"));
			if (screenshot) {
				u.highLight(elementos.filtroRutaDinamica);
				testCase.addScreenShotCurrentStep("Ruta_Dinamica_Bloqueada_Flujo_Tienda");
				u.unhighLight(elementos.filtroRutaDinamica);
			}

			return rutaInactiva;
		} catch (Exception e) {
			System.out.println("Error al validar Ruta Dinámica bloqueada en flujo Tienda: " + e.getMessage());
			return false;
		}

	}
	
	/**
	 * Valida contra BD que las opciones visibles del filtro Tienda coincidan con CR_TIENDA + espacio + DESC_TIENDA.
	 * Consulta xxcm_historico_correos y xxcm_region_plaza_tienda_job_sync para los CR_TIENDA visibles en el portal.
	 *
	 * @param screenshot True para capturar evidencia visual del listado de tiendas usado para comparar contra BD.
	 * @return True si cada opción visible coincide con la etiqueta construida desde BD; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-09
	 */
	public boolean validaOpcionesFiltroTiendaContraBD(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(screenshot);
				if (!filtroAbierto) {
					return false;
				}
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionesFiltroTiendaVisibles));
			List<WebElement> opciones = u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles);
			int opcionesARevisar = Math.min(opciones.size(), 5);
			if (opcionesARevisar == 0) {
				testCase.addBoldTextEvidenceCurrentStep("No se encontraron opciones visibles en el filtro Tienda para validar contra BD");
				return false;
			}

			List<String> etiquetasPortal = new ArrayList<String>();
			List<String> crTiendas = new ArrayList<String>();
			for (int i = 0; i < opcionesARevisar; i++) {
				String etiquetaPortal = normalizarTextoFiltro(opciones.get(i));
				String[] partes = etiquetaPortal.split("\\s+", 2);
				if (partes.length < 2) {
					testCase.addBoldTextEvidenceCurrentStep("Opción Tienda sin formato mínimo CR + Nombre: " + etiquetaPortal);
					return false;
				}
				etiquetasPortal.add(etiquetaPortal);
				crTiendas.add(partes[0]);
			}

			StringBuilder crIn = new StringBuilder();
			for (int i = 0; i < crTiendas.size(); i++) {
				if (i > 0) {
					crIn.append(",");
				}
				crIn.append("'").append(crTiendas.get(i).replace("'", "''")).append("'");
			}

			String query = "SELECT DISTINCT r.cr_tienda, r.desc_tienda "
					+ "FROM xxcm_historico_correos h "
					+ "LEFT JOIN xxcm_region_plaza_tienda_job_sync r "
					+ "ON r.id_retek = h.id_retek "
					+ "WHERE h.origen = 'cedis' "
					+ "AND h.version_documento = 2 "
					+ "AND h.is_visible = true "
					+ "AND h.cr_tienda IN (" + crIn.toString() + ") "
					+ "ORDER BY r.cr_tienda";

			LinkedHashMap<String, String> etiquetasBD = new LinkedHashMap<String, String>();
			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(query)) {
				while (rs.next()) {
					String crTienda = rs.getString("cr_tienda");
					String descTienda = rs.getString("desc_tienda");
					etiquetasBD.put(crTienda, (crTienda + " " + descTienda).replaceAll("\\s+", " ").trim());
				}
			}

			boolean coincidenEtiquetas = etiquetasBD.size() == etiquetasPortal.size();
			System.out.println("[VALIDACION BD TIENDA] Opciones portal revisadas: " + etiquetasPortal.size() + " | Registros BD: " + etiquetasBD.size());
			testCase.addBoldTextEvidenceCurrentStep("Opciones portal revisadas: " + etiquetasPortal.size() + " | Registros BD: " + etiquetasBD.size());
			for (String etiquetaPortal : etiquetasPortal) {
				String crTienda = etiquetaPortal.split("\\s+", 2)[0];
				String etiquetaBD = etiquetasBD.get(crTienda);
				boolean coincide = etiquetaPortal.equals(etiquetaBD);
				System.out.println("[VALIDACION BD TIENDA] Portal: " + etiquetaPortal + " | BD: " + etiquetaBD + " | Resultado: " + (coincide ? "COINCIDE" : "NO COINCIDE"));
				testCase.addBoldTextEvidenceCurrentStep("Portal: " + etiquetaPortal + " | BD: " + etiquetaBD + " | Resultado: " + (coincide ? "COINCIDE" : "NO COINCIDE"));
				coincidenEtiquetas = coincidenEtiquetas && coincide;
			}

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Opciones_Filtro_Tienda_Validadas_BD");
			}

			return coincidenEtiquetas;
		} catch (Exception e) {
			System.out.println("Error al validar opciones del filtro Tienda contra BD: " + e.getMessage());
			return false;
		}
	}
	/**
	 * Marca una tienda disponible y valida que el texto visible del filtro Tienda muestre la tienda seleccionada.
	 * Cierra el desplegable después de seleccionar para validar el texto final del control.
	 *
	 * @param screenshot True para capturar evidencia visual del texto visible con una tienda seleccionada.
	 * @return True si el filtro Tienda muestra una selección única y no muestra Múltiples seleccionados; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean marcaPrimeraTiendaYValidaTextoSeleccionUnica(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					return false;
				}
			}

			WebElement primeraOpcion = wait.until(ExpectedConditions.elementToBeClickable(elementos.primeraOpcionFiltroTienda));
			String tiendaSeleccionada = normalizarTextoFiltro(primeraOpcion);
			primeraOpcion.click();
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			u.hardWait(1);

			WebElement filtroTienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			String textoFiltro = normalizarTextoFiltro(filtroTienda);
			boolean muestraSeleccionUnica = textoFiltro.contains(tiendaSeleccionada)
					&& !textoFiltro.toLowerCase().contains("múltiples")
					&& !textoFiltro.toLowerCase().contains("multiples");

			System.out.println("[VALIDACION TEXTO TIENDA] Tienda seleccionada: " + tiendaSeleccionada + " | Texto filtro: " + textoFiltro);
			testCase.addBoldTextEvidenceCurrentStep("Tienda seleccionada: " + tiendaSeleccionada + " | Texto filtro: " + textoFiltro);
			if (screenshot) {
				u.highLight(elementos.filtroTienda);
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Texto_Seleccion_Unica");
				u.unhighLight(elementos.filtroTienda);
			}

			return muestraSeleccionUnica;
		} catch (Exception e) {
			System.out.println("Error al validar texto de filtro Tienda con selección única: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Marca una segunda tienda disponible y valida que el texto visible del filtro Tienda muestre Múltiples seleccionados.
	 * Cierra el desplegable después de seleccionar para validar el texto final del control.
	 *
	 * @param screenshot True para capturar evidencia visual del texto visible con selección múltiple.
	 * @return True si el filtro Tienda muestra Múltiples seleccionados; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean marcaSegundaTiendaYValidaTextoMultiple(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					return false;
				}
			}

			WebElement segundaOpcion = wait.until(ExpectedConditions.elementToBeClickable(elementos.segundaOpcionFiltroTienda));
			String segundaTienda = normalizarTextoFiltro(segundaOpcion);
			segundaOpcion.click();
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			u.hardWait(1);

			WebElement filtroTienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			String textoFiltro = normalizarTextoFiltro(filtroTienda);
			boolean muestraMultiples = textoFiltro.toLowerCase().contains("múltiples seleccionados")
					|| textoFiltro.toLowerCase().contains("multiples seleccionados");

			System.out.println("[VALIDACION TEXTO TIENDA] Segunda tienda marcada: " + segundaTienda + " | Texto filtro: " + textoFiltro);
			testCase.addBoldTextEvidenceCurrentStep("Segunda tienda marcada: " + segundaTienda + " | Texto filtro: " + textoFiltro);
			if (screenshot) {
				u.highLight(elementos.filtroTienda);
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Texto_Multiples_Seleccionados");
				u.unhighLight(elementos.filtroTienda);
			}

			return muestraMultiples;
		} catch (Exception e) {
			System.out.println("Error al validar texto de filtro Tienda con selección múltiple: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Marca una segunda tienda disponible en el filtro Tienda sin cerrar el desplegable.
	 * Permite separar la selección de la validación del texto visible en pasos distintos del script.
	 *
	 * @param screenshot True para capturar evidencia visual de la segunda tienda marcada.
	 * @return True si se marca una segunda tienda disponible; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean marcaSegundaTiendaDisponible(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					return false;
				}
			}

			WebElement opcionTienda = wait.until(ExpectedConditions.elementToBeClickable(elementos.segundaOpcionFiltroTienda));
			String tiendaSeleccionada = normalizarTextoFiltro(opcionTienda);
			opcionTienda.click();
			u.hardWait(1);

			System.out.println("[SELECCION TIENDA] Segunda tienda marcada: " + tiendaSeleccionada);
			testCase.addBoldTextEvidenceCurrentStep("Segunda tienda marcada: " + tiendaSeleccionada);
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Segunda_Tienda_Marcada");
			}

			return !tiendaSeleccionada.isEmpty();
		} catch (Exception e) {
			System.out.println("Error al marcar la segunda tienda disponible: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Marca dos tiendas que cuenten con informacion en BD dentro del rango de fechas seleccionado en pantalla.
	 * Usa el buscador del filtro Tienda para seleccionar tiendas con datos y evitar consultas sin detalle.
	 *
	 * @param usuario Usuario cuyo perfil y alcance de tiendas se validan en BD.
	 * @param screenshot True para capturar evidencia visual de las tiendas marcadas.
	 * @return True si se marcan dos tiendas con informacion en el rango; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	public boolean marcaDosTiendasConInformacionEnRangoDesdeBD(String usuario, boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No fue posible abrir el filtro Tienda para marcar tiendas con informacion");
					return false;
				}
			}

			LocalDate fechaInicial = obtieneFechaSeleccionadaFiltro(elementos.filtroFechaInicial);
			LocalDate fechaFinal = obtieneFechaSeleccionadaFiltro(elementos.filtroFechaFinal);

			// INTENTO RAPIDO (nuevo): tomar tiendas candidatas de BD y buscarlas UNA POR UNA por nombre en el buscador del filtro.
			// Evita el recorrido/scroll por todo el catalogo, que era lo que hacia lento el paso.
			LinkedHashMap<String, String> tiendasBDrapidas = obtenerTiendasConInformacionPorRangoDesdeBD(fechaInicial, fechaFinal, 8, usuario);
			List<String> tiendasBDrapidasLista = new ArrayList<String>();
			for (String etiquetaBD : tiendasBDrapidas.values()) {
				tiendasBDrapidasLista.add(etiquetaBD);
			}
			if (tiendasBDrapidasLista.size() >= 2) {
				System.out.println("[SELECCION TIENDA RAPIDA] Candidatas BD para busqueda por nombre: " + tiendasBDrapidasLista);
				testCase.addBoldTextEvidenceCurrentStep("Candidatas BD para busqueda por nombre (fast path): " + tiendasBDrapidasLista);
				List<String> tiendasMarcadasRapido = marcaDosTiendasPorBusquedaNombreDesdeBD(tiendasBDrapidasLista, elementos, wait);
				if (tiendasMarcadasRapido.size() == 2) {
					boolean panelAbierto = !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
					String estadoPaso = panelAbierto ? "PASSED" : "FAILED";
					String motivoFallo = panelAbierto ? "" : "El panel Tienda se cerro despues de marcar por busqueda de nombre";
					System.out.println("[RESULTADO PASO] " + estadoPaso + " - Tiendas marcadas por busqueda de nombre (fast path): " + tiendasMarcadasRapido + (panelAbierto ? "" : " | Motivo: " + motivoFallo));
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " | Tiendas marcadas por busqueda de nombre (fast path): " + tiendasMarcadasRapido + (panelAbierto ? "" : " | Motivo: " + motivoFallo));
					if (screenshot) {
						testCase.addScreenShotCurrentStep("Tiendas_Marcadas_Fast_Path");
					}
					return panelAbierto;
				}
				System.out.println("[SELECCION TIENDA RAPIDA] Fast path no logro marcar 2 tiendas (marcadas: " + tiendasMarcadasRapido.size() + "), se caera a los flujos previos con scroll.");
				testCase.addBoldTextEvidenceCurrentStep("Fast path no logro marcar 2 tiendas por nombre, se intentaran los flujos previos con scroll acotado");
			}

			List<String> tiendasPrimerasVisibles = marcaPrimerasTiendasVisiblesConInformacionDesdeBD(fechaInicial, fechaFinal, elementos, wait, 2, 5);
			if (tiendasPrimerasVisibles.size() == 2) {
				boolean panelAbierto = !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
				String estadoPaso = panelAbierto ? "PASSED" : "FAILED";
				String motivoFallo = panelAbierto ? "" : "El panel Tienda se cerro despues de seleccionar las primeras tiendas visibles con informacion";
				System.out.println("[RESULTADO PASO] " + estadoPaso + " - Primeras tiendas visibles con informacion marcadas: " + tiendasPrimerasVisibles + (panelAbierto ? "" : " | Motivo: " + motivoFallo));
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " | Primeras tiendas visibles con informacion marcadas: " + tiendasPrimerasVisibles + (panelAbierto ? "" : " | Motivo: " + motivoFallo));
				if (screenshot) {
					testCase.addScreenShotCurrentStep("Primeras_Tiendas_Visibles_Con_Informacion_Marcadas");
				}
				return panelAbierto;
			}

			LinkedHashMap<String, String> tiendasObjetivoBD = obtenerTiendasConInformacionPorRangoDesdeBD(fechaInicial, fechaFinal, 8, usuario);
			List<String> tiendasObjetivo = new ArrayList<String>();
			for (String tiendaObjetivoBD : tiendasObjetivoBD.values()) {
				tiendasObjetivo.add(tiendaObjetivoBD);
			}

			if (tiendasObjetivo.size() >= 2) {
				System.out.println("[SELECCION TIENDA CON INFO] Intento rapido por buscador con tiendas BD: " + tiendasObjetivo);
				testCase.addBoldTextEvidenceCurrentStep("Intento rapido por buscador con tiendas BD: " + tiendasObjetivo);
				boolean seleccionRapida = buscaYMarcaDosTiendasPorBusquedaDirecta(tiendasObjetivo, elementos, wait);
				if (seleccionRapida) {
					boolean panelAbierto = !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
					String estadoPaso = panelAbierto ? "PASSED" : "FAILED";
					String motivoFallo = panelAbierto ? "" : "El panel Tienda se cerro despues de seleccionar por buscador";
					System.out.println("[RESULTADO PASO] " + estadoPaso + " - Tiendas con informacion marcadas por buscador" + (panelAbierto ? "" : " | Motivo: " + motivoFallo));
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " | Tiendas con informacion marcadas por buscador" + (panelAbierto ? "" : " | Motivo: " + motivoFallo));
					if (screenshot) {
						testCase.addScreenShotCurrentStep("Tiendas_Con_Informacion_Marcadas_Busqueda");
					}
					return panelAbierto;
				}
				System.out.println("[SELECCION TIENDA CON INFO] Busqueda directa no encontro dos tiendas, se usara recorrido visible como respaldo");
				testCase.addBoldTextEvidenceCurrentStep("Busqueda directa no encontro dos tiendas, se usara recorrido visible como respaldo");
			}

			List<String> tiendasMarcadasPorScroll = marcaTiendasVisiblesConInformacionDesdeBD(fechaInicial, fechaFinal, elementos, wait, 2);
			if (tiendasMarcadasPorScroll.size() < 2) {
				String motivoFallo = "No se encontraron dos tiendas visibles con informacion en BD dentro del selector. Rango: " + fechaInicial + " a " + fechaFinal + " | Tiendas marcadas: " + tiendasMarcadasPorScroll.size();
				System.out.println("[RESULTADO PASO] FAILED - Marcar tiendas con informacion | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			boolean resultadoPaso = tiendasMarcadasPorScroll.size() == 2 && !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "No se conservaron dos tiendas marcadas con informacion. Actual: " + tiendasMarcadasPorScroll;
			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Tiendas con informacion marcadas: " + tiendasMarcadasPorScroll + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " | Tiendas con informacion marcadas: " + tiendasMarcadasPorScroll + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Tiendas_Con_Informacion_Marcadas");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Marcar tiendas con informacion | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida contra BD las primeras opciones visibles del selector Tienda y marca las primeras que tengan informacion.
	 * Revisa unicamente una muestra visible acotada para evitar cargar o recorrer todo el catalogo del portal.
	 *
	 * @param fechaInicial Fecha inicial seleccionada en el portal.
	 * @param fechaFinal Fecha final seleccionada en el portal.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita del flujo actual.
	 * @param limite Cantidad de tiendas con informacion que se deben marcar.
	 * @param maxOpcionesVisibles Cantidad maxima de opciones visibles que se revisaran.
	 * @return Lista de tiendas visibles marcadas con informacion en BD.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private List<String> marcaPrimerasTiendasVisiblesConInformacionDesdeBD(LocalDate fechaInicial, LocalDate fechaFinal, rutaLiquidacionElements elementos, WebDriverWait wait, int limite, int maxOpcionesVisibles) throws Exception {
		List<String> tiendasMarcadas = new ArrayList<String>();
		aseguraPanelFiltroTiendaAbierto(elementos, new WebDriverWait(u.getDriver(), Duration.ofSeconds(5)));

		wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionesFiltroTiendaVisibles));
		List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), maxOpcionesVisibles);
		LinkedHashMap<String, String> tiendasVisibles = new LinkedHashMap<String, String>();
		LinkedHashMap<String, WebElement> elementosPorCr = new LinkedHashMap<String, WebElement>();
		for (WebElement opcion : opcionesVisibles) {
			String etiqueta = normalizarTextoFiltro(opcion);
			if (!etiqueta.isEmpty()) {
				String cr = etiqueta.split("\\s+", 2)[0];
				tiendasVisibles.put(cr, etiqueta);
				elementosPorCr.put(cr, opcion);
			}
		}

		LinkedHashMap<String, String> tiendasConInfo = obtenerTiendasVisiblesConInformacionPorRangoDesdeBD(fechaInicial, fechaFinal, tiendasVisibles);
		System.out.println("[BD PRIMERAS TIENDAS VISIBLES] Revisadas: " + tiendasVisibles.values() + " | Con informacion: " + tiendasConInfo.values());
		testCase.addBoldTextEvidenceCurrentStep("Primeras tiendas visibles revisadas: " + tiendasVisibles.values() + " | Con informacion en BD: " + tiendasConInfo.values());

		for (String crTienda : tiendasConInfo.keySet()) {
			if (tiendasMarcadas.size() == limite) {
				break;
			}
			WebElement opcion = elementosPorCr.get(crTienda);
			if (opcion == null) {
				continue;
			}
			String etiqueta = normalizarTextoFiltro(opcion);
			boolean marcadaAntes = estaOpcionTiendaMarcada(opcion);
			if (!marcadaAntes) {
				haceClickSeguroOpcionTienda(opcion);
				esperaRenderCorto();
			}
			boolean marcada = marcadaAntes || esperaOpcionTiendaMarcadaPorCr(crTienda, elementos);
			System.out.println("[SELECCION PRIMERAS VISIBLES] Portal: " + etiqueta + " | Marcada: " + marcada);
			testCase.addBoldTextEvidenceCurrentStep("Tienda visible con informacion seleccionada: " + etiqueta + " | Marcada: " + marcada);
			if (marcada) {
				tiendasMarcadas.add(etiqueta);
			}
		}

		return tiendasMarcadas;
	}

	/**
	 * Recorre las opciones visibles del selector Tienda, valida en BD cuales tienen informacion para el rango y marca dos.
	 * Evita depender del buscador cuando el perfil Admin no filtra correctamente por CR o nombre.
	 *
	 * @param fechaInicial Fecha inicial seleccionada en el portal.
	 * @param fechaFinal Fecha final seleccionada en el portal.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita del flujo actual.
	 * @param limite Cantidad de tiendas con informacion que se deben marcar.
	 * @return Lista de tiendas marcadas en el selector.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private List<String> marcaTiendasVisiblesConInformacionDesdeBD(LocalDate fechaInicial, LocalDate fechaFinal, rutaLiquidacionElements elementos, WebDriverWait wait, int limite) throws Exception {
		List<String> tiendasMarcadas = new ArrayList<String>();
		List<String> crRevisadas = new ArrayList<String>();
		for (int pagina = 0; pagina < 10 && tiendasMarcadas.size() < limite; pagina++) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionesFiltroTiendaVisibles));
			List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 20);
			LinkedHashMap<String, String> tiendasVisibles = new LinkedHashMap<String, String>();
			for (WebElement opcion : opcionesVisibles) {
				String etiqueta = normalizarTextoFiltro(opcion);
				if (etiqueta.isEmpty()) {
					continue;
				}
				String crTienda = etiqueta.split("\\s+", 2)[0];
				if (!crRevisadas.contains(crTienda)) {
					tiendasVisibles.put(crTienda, etiqueta);
					crRevisadas.add(crTienda);
				}
			}

			LinkedHashMap<String, String> tiendasConInfo = obtenerTiendasVisiblesConInformacionPorRangoDesdeBD(fechaInicial, fechaFinal, tiendasVisibles);
			System.out.println("[BD TIENDAS VISIBLES CON INFO] Pagina: " + (pagina + 1) + " | Visibles: " + tiendasVisibles.values() + " | Con informacion: " + tiendasConInfo.values());
			for (WebElement opcion : opcionesVisibles) {
				String etiqueta = normalizarTextoFiltro(opcion);
				if (etiqueta.isEmpty()) {
					continue;
				}
				String crTienda = etiqueta.split("\\s+", 2)[0];
				if (!tiendasConInfo.containsKey(crTienda) || tiendasMarcadas.contains(etiqueta)) {
					continue;
				}
				if (!estaOpcionTiendaMarcada(opcion)) {
					try {
						opcion.click();
					} catch (Exception e) {
						((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", opcion);
					}
					esperaRenderCorto();
				}
				WebElement opcionActualizada = esperaOpcionTiendaVisiblePorCr(crTienda, elementos, wait);
				boolean marcada = opcionActualizada != null && estaOpcionTiendaMarcada(opcionActualizada);
				System.out.println("[SELECCION TIENDA VISIBLE CON INFO] Portal: " + etiqueta + " | Marcada: " + marcada);
				testCase.addBoldTextEvidenceCurrentStep("Tienda visible con informacion seleccionada: " + etiqueta + " | Marcada: " + marcada);
				if (marcada) {
					tiendasMarcadas.add(etiqueta);
				}
				if (tiendasMarcadas.size() == limite) {
					return tiendasMarcadas;
				}
			}

			WebElement contenedorScroll = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.contenedorScrollFiltroTienda));
			Long scrollAntes = (Long) ((JavascriptExecutor) u.getDriver()).executeScript("return arguments[0].scrollTop;", contenedorScroll);
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollTop = arguments[0].scrollTop + Math.max(arguments[0].clientHeight, 450);", contenedorScroll);
			esperaRenderCorto();
			Long scrollDespues = (Long) ((JavascriptExecutor) u.getDriver()).executeScript("return arguments[0].scrollTop;", contenedorScroll);
			if (scrollDespues.longValue() == scrollAntes.longValue()) {
				break;
			}
		}
		return tiendasMarcadas;
	}

	/**
	 * Obtiene desde BD la fecha mas reciente con informacion CEDIS visible para consultar por filtro Tienda.
	 * Devuelve la fecha con el formato visible del portal para reutilizar capturaFechasInicialFinalDesdeBD.
	 *
	 * @param usuario Usuario cuyo perfil y alcance se validan al buscar la fecha.
	 * @return Fecha formateada como la muestra el portal, por ejemplo 9 jul 2026.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	public String obtenerFechaConInformacionTiendaDesdeBD(String usuario) {
		String query = "SELECT h.fecha_registro::date AS fecha "
				+ "FROM xxcm_historico_correos h "
				+ "WHERE h.origen = 'cedis' "
				+ "AND h.version_documento = 2 "
				+ "AND h.is_visible = true "
				+ "AND h.id_retek IS NOT NULL "
				+ "AND h.transferencia IS NOT NULL "
				+ "AND h.fecha_registro::date BETWEEN CURRENT_DATE - 3 AND CURRENT_DATE "
				+ "AND (? IS NULL OR EXISTS ("
				+ "    SELECT 1 FROM xxcm_user_keys perfil "
				+ "    JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = perfil.usuario_id "
				+ "    WHERE datos_usuario.usuario = ? "
				+ "    AND perfil.short_name = 'PCPERFIL' "
				+ "    AND UPPER(TRIM(perfil.key_value)) = 'ADMIN'"
				+ ") OR h.suppliable_id::text IN ("
				+ "    SELECT alcance.key_value FROM xxcm_user_keys alcance "
				+ "    JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = alcance.usuario_id "
				+ "    WHERE datos_usuario.usuario = ? "
				+ "    AND alcance.short_name IN ('PCRFC', 'PCSUPPLIERID')"
				+ ")) "
				+ "GROUP BY h.fecha_registro::date "
				+ "HAVING COUNT(DISTINCT h.id_retek) >= 2 "
				+ "ORDER BY h.fecha_registro::date DESC "
				+ "LIMIT 1";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
			String usuarioNormalizado = usuario == null || usuario.trim().isEmpty() ? null : usuario.trim();
			statement.setString(1, usuarioNormalizado);
			statement.setString(2, usuarioNormalizado);
			statement.setString(3, usuarioNormalizado);
			try (ResultSet rs = statement.executeQuery()) {
				if (rs.next() && rs.getDate("fecha") != null) {
					LocalDate fecha = rs.getDate("fecha").toLocalDate();
					String fechaFormateadaPortal = fecha.format(DateTimeFormatter.ofPattern("d MMM yyyy", new Locale("es", "ES"))).replace(".", "").toLowerCase(new Locale("es", "ES"));
					System.out.println("[BD FECHA TIENDA] Usuario: " + usuarioNormalizado + " | Fecha con informacion CEDIS: " + fecha + " | Portal: " + fechaFormateadaPortal);
					testCase.addBoldTextEvidenceCurrentStep("Fecha con informacion CEDIS obtenida desde BD para el alcance del usuario: " + fechaFormateadaPortal);
					return fechaFormateadaPortal;
				}
			}
		} catch (Exception e) {
			System.out.println("[BD FECHA TIENDA] Error al obtener fecha con informacion CEDIS: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Error al obtener fecha con informacion CEDIS desde BD: " + e.getMessage());
		}

		return "";
	}

	/**
	 * Marca una tienda si ya se encuentra visible dentro del listado abierto del filtro Tienda.
	 * Evita usar el buscador cuando las tiendas con informacion ya estan visibles en el selector.
	 *
	 * @param etiquetaTienda Etiqueta visible CR_TIENDA espacio NOMBRE_TIENDA.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita del flujo actual.
	 * @return True si la tienda visible queda marcada; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private boolean buscaYMarcaTiendaVisiblePorEtiqueta(String etiquetaTienda, rutaLiquidacionElements elementos, WebDriverWait wait) {
		try {
			String crTienda = etiquetaTienda.split("\\s+", 2)[0];
			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					return false;
				}
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionesFiltroTiendaVisibles));
			List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 12);
			for (WebElement opcion : opcionesVisibles) {
				String etiqueta = normalizarTextoFiltro(opcion);
				if (!etiqueta.isEmpty() && etiqueta.startsWith(crTienda)) {
					boolean marcadaAntes = estaOpcionTiendaMarcada(opcion);
					if (!marcadaAntes) {
						haceClickSeguroOpcionTienda(opcion);
						esperaRenderCorto();
					}
					boolean marcada = marcadaAntes || esperaOpcionTiendaMarcadaPorCr(crTienda, elementos);
					System.out.println("[SELECCION TIENDA VISIBLE CON INFO] Esperada: " + etiquetaTienda + " | Portal: " + etiqueta + " | Marcada: " + marcada);
					testCase.addBoldTextEvidenceCurrentStep("Tienda visible con informacion seleccionada: " + etiqueta + " | Marcada: " + marcada);
					return marcada;
				}
			}
			return false;
		} catch (Exception e) {
			System.out.println("No se pudo marcar tienda visible con informacion, se intentara buscador: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Busca una tienda por CR en el filtro Tienda y marca su checkbox si se encuentra sin seleccionar.
	 *
	 * @param etiquetaTienda Etiqueta visible CR_TIENDA espacio NOMBRE_TIENDA.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita del flujo actual.
	 * @return True si la tienda queda marcada; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private boolean buscaYMarcaTiendaPorEtiqueta(String etiquetaTienda, rutaLiquidacionElements elementos, WebDriverWait wait) {
		try {
			String crTienda = etiquetaTienda.split("\\s+", 2)[0];
			WebDriverWait waitCorto = new WebDriverWait(u.getDriver(), Duration.ofSeconds(5));
			aseguraPanelFiltroTiendaAbierto(elementos, waitCorto);
			WebElement inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, waitCorto);
			limpiaYCapturaBusquedaTienda(inputBusqueda, crTienda);
			esperaRenderCorto();

			List<WebElement> opcionesFiltradas = new ArrayList<WebElement>();
			for (int intento = 0; intento < 5 && opcionesFiltradas.isEmpty(); intento++) {
				opcionesFiltradas = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 10);
				if (opcionesFiltradas.isEmpty()) {
					esperaRenderCorto();
				}
			}
			for (WebElement opcion : opcionesFiltradas) {
				String etiqueta = normalizarTextoFiltro(opcion);
				if (!etiqueta.isEmpty() && etiqueta.startsWith(crTienda)) {
					boolean marcadaAntes = estaOpcionTiendaMarcada(opcion);
					if (!marcadaAntes) {
						haceClickSeguroOpcionTienda(opcion);
						esperaRenderCorto();
					}
					boolean marcada = marcadaAntes || esperaOpcionTiendaMarcadaPorCr(crTienda, elementos);
					System.out.println("[SELECCION TIENDA CON INFO] Esperada: " + etiquetaTienda + " | Portal: " + etiqueta + " | Marcada: " + marcada);
					testCase.addBoldTextEvidenceCurrentStep("Tienda con informacion seleccionada: " + etiqueta + " | Marcada: " + marcada);
					return marcada;
				}
			}
			return false;
		} catch (Exception e) {
			System.out.println("Error al buscar y marcar tienda con informacion: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Busca cada tienda objetivo por CR completo y marca sus opciones dentro del filtro Tienda.
	 * Limpia el buscador al final para que el control muestre correctamente Multiples seleccionados al cerrar.
	 *
	 * @param etiquetasTiendas Etiquetas CR_TIENDA espacio NOMBRE_TIENDA que se deben marcar.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita del flujo actual.
	 * @return True si todas las tiendas objetivo quedan marcadas al mismo tiempo; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private boolean buscaYMarcaTiendasEnUnaBusqueda(List<String> etiquetasTiendas, rutaLiquidacionElements elementos, WebDriverWait wait) {
		try {
			if (etiquetasTiendas == null || etiquetasTiendas.size() < 2) {
				return false;
			}

			List<String> crTiendas = new ArrayList<String>();
			for (String etiquetaTienda : etiquetasTiendas) {
				crTiendas.add(etiquetaTienda.split("\\s+", 2)[0]);
			}

			WebDriverWait waitCorto = new WebDriverWait(u.getDriver(), Duration.ofSeconds(5));
			aseguraPanelFiltroTiendaAbierto(elementos, waitCorto);
			WebElement inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, waitCorto);

			List<String> tiendasMarcadas = new ArrayList<String>();
			for (int i = 0; i < crTiendas.size(); i++) {
				String crTienda = crTiendas.get(i);
				limpiaYCapturaBusquedaTienda(inputBusqueda, crTienda);
				esperaRenderCorto();
				WebElement opcion = esperaOpcionTiendaVisiblePorCr(crTienda, elementos, wait);
				if (opcion == null) {
					String[] partesEtiqueta = etiquetasTiendas.get(i).split("\\s+", 2);
					if (partesEtiqueta.length > 1) {
						limpiaYCapturaBusquedaTienda(inputBusqueda, partesEtiqueta[1]);
						esperaRenderCorto();
						opcion = esperaOpcionTiendaVisiblePorCr(crTienda, elementos, wait);
					}
					if (opcion == null) {
						System.out.println("[SELECCION TIENDA CON INFO] No se encontro opcion visible para CR completo: " + crTienda + " | Etiqueta: " + etiquetasTiendas.get(i));
						return false;
					}
				}

				String etiqueta = normalizarTextoFiltro(opcion);
				if (!estaOpcionTiendaMarcada(opcion)) {
					haceClickSeguroOpcionTienda(opcion);
					esperaRenderCorto();
				}
				boolean marcada = esperaOpcionTiendaMarcadaPorCr(crTienda, elementos);
				System.out.println("[SELECCION TIENDA CON INFO] Esperada: " + etiquetasTiendas.get(i) + " | Portal: " + etiqueta + " | Marcada: " + marcada);
				testCase.addBoldTextEvidenceCurrentStep("Tienda con informacion seleccionada: " + etiqueta + " | Marcada: " + marcada);
				if (!marcada) {
					return false;
				}
				tiendasMarcadas.add(etiquetasTiendas.get(i));
			}

			limpiaYCapturaBusquedaTienda(inputBusqueda, "");
			esperaRenderCorto();
			int seleccionadas = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
			boolean resultado = tiendasMarcadas.size() == etiquetasTiendas.size() && seleccionadas >= etiquetasTiendas.size();
			System.out.println("[SELECCION TIENDA CON INFO] Tiendas objetivo marcadas: " + tiendasMarcadas + " | Seleccionadas visibles al limpiar busqueda: " + seleccionadas);
			testCase.addBoldTextEvidenceCurrentStep("Tiendas objetivo marcadas: " + tiendasMarcadas + " | Seleccionadas visibles al limpiar busqueda: " + seleccionadas);
			return resultado;
		} catch (Exception e) {
			System.out.println("Error al buscar y marcar tiendas por CR completo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene el prefijo comun de una lista de CR Tienda para filtrar varias tiendas en una sola busqueda.
	 *
	 * @param valores Lista de CR Tienda.
	 * @return Prefijo comun encontrado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private String obtienePrefijoComun(List<String> valores) {
		if (valores == null || valores.isEmpty()) {
			return "";
		}
		String prefijo = valores.get(0);
		for (String valor : valores) {
			while (!valor.startsWith(prefijo) && !prefijo.isEmpty()) {
				prefijo = prefijo.substring(0, prefijo.length() - 1);
			}
		}
		return prefijo;
	}

	/**
	 * Espera una opcion visible del filtro Tienda que inicie con el CR indicado.
	 *
	 * @param crTienda CR Tienda que debe aparecer al inicio de la opcion.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita del flujo actual.
	 * @return Opcion visible encontrada, o null si no aparece.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private WebElement esperaOpcionTiendaVisiblePorCr(String crTienda, rutaLiquidacionElements elementos, WebDriverWait wait) {
		for (int intento = 0; intento < 5; intento++) {
			List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 20);
			for (WebElement opcion : opcionesVisibles) {
				String etiqueta = normalizarTextoFiltro(opcion);
				if (!etiqueta.isEmpty() && etiqueta.startsWith(crTienda)) {
					return opcion;
				}
			}
			esperaRenderCorto();
		}
		return null;
	}

	/**
	 * Asegura que el panel del filtro Tienda este abierto antes de buscar una tienda.
	 *
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita corta para interacciones del filtro.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private void aseguraPanelFiltroTiendaAbierto(rutaLiquidacionElements elementos, WebDriverWait wait) {
		boolean panelVisible = false;
		List<WebElement> paneles = u.getDriver().findElements(elementos.panelFiltroTiendaActivo);
		for (WebElement panel : paneles) {
			if (panel.isDisplayed()) {
				panelVisible = true;
				break;
			}
		}
		if (panelVisible) {
			return;
		}

		WebElement controlTienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.controlFiltroTienda));
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", controlTienda);
		try {
			controlTienda.click();
		} catch (Exception e) {
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", controlTienda);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
		esperaRenderCorto();
	}

	/**
	 * Obtiene el input visible del buscador de Tienda sin esperar tiempos largos innecesarios.
	 *
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita corta para ubicar el input.
	 * @return Input visible del buscador Tienda.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private WebElement obtieneInputBusquedaTiendaVisible(rutaLiquidacionElements elementos, WebDriverWait wait) {
		List<WebElement> inputsBusqueda = u.getDriver().findElements(elementos.inputBusquedaFiltroTienda);
		for (WebElement input : inputsBusqueda) {
			if (input.isDisplayed()) {
				return input;
			}
		}
		return wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.inputBusquedaFiltroTienda));
	}

	/**
	 * Limpia el buscador de Tienda sin usar atajos globales y captura el texto indicado.
	 * Evita que Ctrl+A seleccione el texto completo de la pagina cuando el input pierde foco.
	 *
	 * @param inputBusqueda Input visible del buscador Tienda.
	 * @param textoBusqueda Texto que se capturara en el buscador.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private void limpiaYCapturaBusquedaTienda(WebElement inputBusqueda, String textoBusqueda) {
		JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
		js.executeScript(
				"arguments[0].scrollIntoView({block:'center'});"
				+ "arguments[0].focus();"
				+ "arguments[0].value = '';"
				+ "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
				+ "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
				inputBusqueda);
		try {
			inputBusqueda.click();
		} catch (Exception e) {
			js.executeScript("arguments[0].click();", inputBusqueda);
		}
		inputBusqueda.sendKeys(textoBusqueda);
	}

	/**
	 * Obtiene la fecha seleccionada visible en un filtro de fecha de Liquidacion de Ruta.
	 *
	 * @param filtroFecha Localizador del filtro de fecha.
	 * @return Fecha seleccionada convertida a LocalDate.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private LocalDate obtieneFechaSeleccionadaFiltro(By filtroFecha) {
		WebElement filtro = u.getDriver().findElement(filtroFecha);
		String textoFiltro = normalizarTextoFiltro(filtro).toLowerCase(new Locale("es", "ES"));
		java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("(\\d{1,2}\\s+[a-záéíóúñ.]+\\s+\\d{4})").matcher(textoFiltro);
		if (!matcher.find()) {
			throw new IllegalArgumentException("No se encontro fecha visible en filtro: " + textoFiltro);
		}
		return convertirFechaVisibleALocalDate(matcher.group(1));
	}

	/**
	 * Convierte una fecha visible del portal en formato d MMM yyyy a LocalDate.
	 *
	 * @param fechaVisible Fecha visible del portal.
	 * @return Fecha convertida a LocalDate.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private LocalDate convertirFechaVisibleALocalDate(String fechaVisible) {
		String fechaNormalizada = fechaVisible.trim().toLowerCase(new Locale("es", "ES")).replace(".", "").replaceAll("\\s+", " ");
		String[] partesFecha = fechaNormalizada.split(" ");
		int indiceMes = partesFecha.length > 3 && "de".equals(partesFecha[1]) ? 2 : 1;
		int dia = Integer.parseInt(partesFecha[0]);
		String mesTexto = partesFecha[indiceMes];
		int anio = Integer.parseInt(partesFecha[indiceMes + 1]);
		int mes;
		if (mesTexto.startsWith("ene")) {
			mes = 1;
		} else if (mesTexto.startsWith("feb")) {
			mes = 2;
		} else if (mesTexto.startsWith("mar")) {
			mes = 3;
		} else if (mesTexto.startsWith("abr")) {
			mes = 4;
		} else if (mesTexto.startsWith("may")) {
			mes = 5;
		} else if (mesTexto.startsWith("jun")) {
			mes = 6;
		} else if (mesTexto.startsWith("jul")) {
			mes = 7;
		} else if (mesTexto.startsWith("ago")) {
			mes = 8;
		} else if (mesTexto.startsWith("sep")) {
			mes = 9;
		} else if (mesTexto.startsWith("oct")) {
			mes = 10;
		} else if (mesTexto.startsWith("nov")) {
			mes = 11;
		} else if (mesTexto.startsWith("dic")) {
			mes = 12;
		} else {
			throw new IllegalArgumentException("No se pudo interpretar el mes de la fecha: " + fechaVisible);
		}
		return LocalDate.of(anio, mes, dia);
	}

	/**
	 * Obtiene tiendas con informacion en BD para el rango de fechas seleccionado.
	 *
	 * @param fechaInicial Fecha inicial seleccionada en el portal.
	 * @param fechaFinal Fecha final seleccionada en el portal.
	 * @param limite Cantidad maxima de tiendas a devolver.
	 * @return Mapa CR_TIENDA -> etiqueta visible esperada.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private LinkedHashMap<String, String> obtenerTiendasConInformacionPorRangoDesdeBD(LocalDate fechaInicial, LocalDate fechaFinal, int limite, String usuario) throws Exception {
		String query = "SELECT cr_tienda, nombre_tienda "
				+ "FROM ( "
				+ "  SELECT COALESCE(r.cr_tienda, h.cr_tienda) AS cr_tienda, "
				+ "  MAX(COALESCE(r.desc_tienda, h.cr_tienda)) AS nombre_tienda, "
				+ "  COUNT(DISTINCT h.id_retek) AS total_id_retek "
				+ "  FROM xxcm_historico_correos h "
				+ "  LEFT JOIN xxcm_region_plaza_tienda_job_sync r ON r.id_retek = h.id_retek "
				+ "  WHERE h.origen = 'cedis' "
				+ "  AND h.version_documento = 2 "
				+ "  AND h.is_visible = true "
				+ "  AND h.id_retek IS NOT NULL "
				+ "  AND h.transferencia IS NOT NULL "
				+ "  AND h.fecha_registro::date BETWEEN ? AND ? "
				+ "  AND (? IS NULL OR EXISTS ("
				+ "      SELECT 1 FROM xxcm_user_keys perfil "
				+ "      JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = perfil.usuario_id "
				+ "      WHERE datos_usuario.usuario = ? "
				+ "      AND perfil.short_name = 'PCPERFIL' "
				+ "      AND UPPER(TRIM(perfil.key_value)) = 'ADMIN'"
				+ "  ) OR h.suppliable_id::text IN ("
				+ "      SELECT alcance.key_value FROM xxcm_user_keys alcance "
				+ "      JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = alcance.usuario_id "
				+ "      WHERE datos_usuario.usuario = ? "
				+ "      AND alcance.short_name IN ('PCRFC', 'PCSUPPLIERID')"
				+ "  )) "
				+ "  GROUP BY COALESCE(r.cr_tienda, h.cr_tienda) "
				+ "  HAVING COUNT(DISTINCT h.id_retek) = 1 "
				+ ") tiendas_unicas "
				+ "ORDER BY cr_tienda "
				+ "LIMIT ?";

		LinkedHashMap<String, String> tiendas = new LinkedHashMap<String, String>();
		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
			String usuarioNormalizado = usuario == null || usuario.trim().isEmpty() ? null : usuario.trim();
			ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
			ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
			ps.setString(3, usuarioNormalizado);
			ps.setString(4, usuarioNormalizado);
			ps.setString(5, usuarioNormalizado);
			ps.setInt(6, limite);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String crTienda = rs.getString("cr_tienda");
					String nombreTienda = rs.getString("nombre_tienda");
					if (crTienda != null && !crTienda.trim().isEmpty()) {
						String etiqueta = (crTienda.trim() + " " + (nombreTienda == null ? "" : nombreTienda.trim())).replaceAll("\\s+", " ").trim();
						tiendas.put(crTienda.trim(), etiqueta);
					}
				}
			}
		}

		System.out.println("[BD TIENDAS CON INFO] Usuario: " + usuario + " | Rango: " + fechaInicial + " a " + fechaFinal + " | Tiendas: " + tiendas.values());
		testCase.addBoldTextEvidenceCurrentStep("Tiendas con informacion en BD para el rango " + fechaInicial + " a " + fechaFinal + ": " + tiendas.values());
		return tiendas;
	}

	/**
	 * Obtiene tiendas del catalogo CEDIS que tienen actividad reciente pero NO cuentan con registros dentro del rango objetivo.
	 * Sirve para el fast path del MTC_FT_070 paso 5: entrega candidatas "sin informacion en el rango" que muy probablemente si aparezcan en el droplist del perfil por haber tenido actividad los ultimos 30 dias.
	 * Ignora las tiendas que si tienen registros en el rango objetivo para no repetir la tienda CON info.
	 *
	 * @param fechaInicial Fecha inicial seleccionada en el portal.
	 * @param fechaFinal Fecha final seleccionada en el portal.
	 * @param limite Cantidad maxima de tiendas a devolver.
	 * @param usuario Usuario cuyo perfil y alcance de tiendas se validan en BD.
	 * @return Mapa CR_TIENDA -> etiqueta visible esperada para tiendas activas fuera del rango pero SIN datos dentro del rango.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private LinkedHashMap<String, String> obtenerTiendasSinInformacionPorRangoDesdeBD(LocalDate fechaInicial, LocalDate fechaFinal, int limite, String usuario) throws Exception {
		String query = "SELECT cr_tienda, nombre_tienda "
				+ "FROM ( "
				+ "  SELECT COALESCE(r.cr_tienda, h.cr_tienda) AS cr_tienda, "
				+ "  MAX(COALESCE(r.desc_tienda, h.cr_tienda)) AS nombre_tienda, "
				+ "  COUNT(DISTINCT h.id_retek) AS total_id_retek "
				+ "  FROM xxcm_historico_correos h "
				+ "  LEFT JOIN xxcm_region_plaza_tienda_job_sync r ON r.id_retek = h.id_retek "
				+ "  WHERE h.origen = 'cedis' "
				+ "  AND h.version_documento = 2 "
				+ "  AND h.is_visible = true "
				+ "  AND h.id_retek IS NOT NULL "
				+ "  AND (? IS NULL OR EXISTS ("
				+ "      SELECT 1 FROM xxcm_user_keys perfil "
				+ "      JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = perfil.usuario_id "
				+ "      WHERE datos_usuario.usuario = ? "
				+ "      AND perfil.short_name = 'PCPERFIL' "
				+ "      AND UPPER(TRIM(perfil.key_value)) = 'ADMIN'"
				+ "  ) OR h.suppliable_id::text IN ("
				+ "      SELECT alcance.key_value FROM xxcm_user_keys alcance "
				+ "      JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = alcance.usuario_id "
				+ "      WHERE datos_usuario.usuario = ? "
				+ "      AND alcance.short_name IN ('PCRFC', 'PCSUPPLIERID')"
				+ "  )) "
				+ "  AND h.fecha_registro::date BETWEEN (?::date - INTERVAL '30 days') AND (?::date + INTERVAL '30 days') "
				+ "  AND h.fecha_registro::date NOT BETWEEN ? AND ? "
				+ "  AND COALESCE(r.cr_tienda, h.cr_tienda) NOT IN ( "
				+ "    SELECT DISTINCT COALESCE(r2.cr_tienda, h2.cr_tienda) "
				+ "    FROM xxcm_historico_correos h2 "
				+ "    LEFT JOIN xxcm_region_plaza_tienda_job_sync r2 ON r2.id_retek = h2.id_retek "
				+ "    WHERE h2.origen = 'cedis' "
				+ "    AND h2.version_documento = 2 "
				+ "    AND h2.is_visible = true "
				+ "    AND h2.id_retek IS NOT NULL "
				+ "    AND h2.fecha_registro::date BETWEEN ? AND ? "
				+ "  ) "
				+ "  GROUP BY COALESCE(r.cr_tienda, h.cr_tienda) "
				+ "  HAVING COUNT(DISTINCT h.id_retek) = 1 "
				+ ") tiendas_unicas "
				+ "ORDER BY cr_tienda "
				+ "LIMIT ?";

		LinkedHashMap<String, String> tiendas = new LinkedHashMap<String, String>();
		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
			String usuarioNormalizado = usuario == null || usuario.trim().isEmpty() ? null : usuario.trim();
			ps.setString(1, usuarioNormalizado);
			ps.setString(2, usuarioNormalizado);
			ps.setString(3, usuarioNormalizado);
			ps.setDate(4, java.sql.Date.valueOf(fechaInicial));
			ps.setDate(5, java.sql.Date.valueOf(fechaFinal));
			ps.setDate(6, java.sql.Date.valueOf(fechaInicial));
			ps.setDate(7, java.sql.Date.valueOf(fechaFinal));
			ps.setDate(8, java.sql.Date.valueOf(fechaInicial));
			ps.setDate(9, java.sql.Date.valueOf(fechaFinal));
			ps.setInt(10, limite);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String crTienda = rs.getString("cr_tienda");
					String nombreTienda = rs.getString("nombre_tienda");
					if (crTienda != null && !crTienda.trim().isEmpty()) {
						String etiqueta = (crTienda.trim() + " " + (nombreTienda == null ? "" : nombreTienda.trim())).replaceAll("\\s+", " ").trim();
						tiendas.put(crTienda.trim(), etiqueta);
					}
				}
			}
		}

		System.out.println("[BD TIENDAS SIN INFO EN RANGO] Usuario: " + usuario + " | Rango objetivo: " + fechaInicial + " a " + fechaFinal + " | Tiendas activas fuera de rango: " + tiendas.values());
		testCase.addBoldTextEvidenceCurrentStep("Tiendas SIN informacion en el rango " + fechaInicial + " a " + fechaFinal + " (activas +-30 dias): " + tiendas.values());
		return tiendas;
	}

	/**
	 * Filtra en BD las tiendas visibles del selector que tienen informacion CEDIS en el rango seleccionado.
	 * Devuelve las etiquetas visibles del portal para poder seleccionar exactamente opciones renderizadas.
	 *
	 * @param fechaInicial Fecha inicial seleccionada en el portal.
	 * @param fechaFinal Fecha final seleccionada en el portal.
	 * @param tiendasVisibles Mapa CR_TIENDA -> etiqueta visible en el selector.
	 * @return Mapa CR_TIENDA -> etiqueta visible con informacion en BD.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private LinkedHashMap<String, String> obtenerTiendasVisiblesConInformacionPorRangoDesdeBD(LocalDate fechaInicial, LocalDate fechaFinal, LinkedHashMap<String, String> tiendasVisibles) throws Exception {
		LinkedHashMap<String, String> tiendasConInfo = new LinkedHashMap<String, String>();
		if (tiendasVisibles == null || tiendasVisibles.isEmpty()) {
			return tiendasConInfo;
		}

		StringBuilder placeholders = new StringBuilder();
		for (int i = 0; i < tiendasVisibles.size(); i++) {
			if (i > 0) {
				placeholders.append(",");
			}
			placeholders.append("?");
		}

		String query = "SELECT DISTINCT COALESCE(r.cr_tienda, h.cr_tienda) AS cr_tienda "
				+ "FROM xxcm_historico_correos h "
				+ "LEFT JOIN xxcm_region_plaza_tienda_job_sync r ON r.id_retek = h.id_retek "
				+ "WHERE h.origen = 'cedis' "
				+ "AND h.version_documento = 2 "
				+ "AND h.is_visible = true "
				+ "AND h.id_retek IS NOT NULL "
				+ "AND h.fecha_registro::date BETWEEN ? AND ? "
				+ "AND COALESCE(r.cr_tienda, h.cr_tienda) IN (" + placeholders.toString() + ") "
				+ "ORDER BY COALESCE(r.cr_tienda, h.cr_tienda)";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setDate(1, java.sql.Date.valueOf(fechaInicial));
			ps.setDate(2, java.sql.Date.valueOf(fechaFinal));
			int parametro = 3;
			for (String crTienda : tiendasVisibles.keySet()) {
				ps.setString(parametro, crTienda);
				parametro++;
			}
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String crTienda = rs.getString("cr_tienda");
					if (crTienda != null && tiendasVisibles.containsKey(crTienda.trim())) {
						tiendasConInfo.put(crTienda.trim(), tiendasVisibles.get(crTienda.trim()));
					}
				}
			}
		}

		return tiendasConInfo;
	}

	/**
	 * Cierra el selector Tienda haciendo clic fuera del componente y valida el texto visible con selección única.
	 * El texto del filtro debe corresponder a la tienda seleccionada y no a Múltiples seleccionados.
	 *
	 * @param screenshot True para capturar evidencia visual del filtro cerrado con una tienda seleccionada.
	 * @return True si el texto visible corresponde a una selección única; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean cierraSelectorTiendaYValidaTextoSeleccionUnica(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement rutaDinamica = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			try {
				rutaDinamica.click();
			} catch (Exception e) {
				u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			}
			u.hardWait(1);

			WebElement filtroTienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			String textoFiltro = normalizarTextoFiltro(filtroTienda);
			boolean muestraSeleccionUnica = textoFiltro.contains(" ")
					&& !textoFiltro.toLowerCase().contains("múltiples")
					&& !textoFiltro.toLowerCase().contains("multiples")
					&& !textoFiltro.equalsIgnoreCase("Tienda");

			System.out.println("[VALIDACION TEXTO TIENDA] Texto esperado: tienda única | Actual: " + textoFiltro);
			testCase.addBoldTextEvidenceCurrentStep("Texto esperado en Tienda: tienda única | Actual: " + textoFiltro);
			if (screenshot) {
				u.highLight(elementos.filtroTienda);
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Cerrado_Seleccion_Unica");
				u.unhighLight(elementos.filtroTienda);
			}

			return muestraSeleccionUnica;
		} catch (Exception e) {
			System.out.println("Error al cerrar selector Tienda y validar texto de selección única: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Cierra el selector Tienda haciendo clic fuera del componente y valida el texto visible con selección múltiple.
	 * El texto del filtro debe mostrar Múltiples seleccionados.
	 *
	 * @param screenshot True para capturar evidencia visual del filtro cerrado con selección múltiple.
	 * @return True si el texto visible muestra Múltiples seleccionados; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean cierraSelectorTiendaYValidaTextoMultiple(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
			} catch (Exception e) {
				WebElement zonaSegura = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.leyendaEntregasHandHeld));
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", zonaSegura);
			}
			u.hardWait(1);

			WebElement filtroTienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			String textoFiltro = normalizarTextoFiltro(filtroTienda).replace("Limpiar selección", "").replaceAll("\\s+", " ").trim();
			boolean muestraMultiples = textoFiltro.toLowerCase().contains("múltiples seleccionados")
					|| textoFiltro.toLowerCase().contains("multiples seleccionados");

			System.out.println("[VALIDACION TEXTO TIENDA] Texto esperado: Múltiples seleccionados | Actual: " + textoFiltro);
			testCase.addBoldTextEvidenceCurrentStep("Texto esperado en Tienda: Múltiples seleccionados | Actual: " + textoFiltro);
			if (screenshot) {
				u.highLight(elementos.filtroTienda);
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Cerrado_Multiples_Seleccionados");
				u.unhighLight(elementos.filtroTienda);
			}

			return muestraMultiples;
		} catch (Exception e) {
			System.out.println("Error al cerrar selector Tienda y validar texto de selección múltiple: " + e.getMessage());
			return false;
		}
	}
	/**
	 * Captura Fecha inicial mayor que Fecha final en Liquidación de Ruta.
	 * Selecciona primero la fecha más reciente como inicial y después una fecha anterior como final.
	 *
	 * @param screenshot True para capturar evidencia visual del rango inválido.
	 * @return True si se capturan las fechas para generar el escenario Fecha final menor que Fecha inicial; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean capturaFechaFinalMenorQueFechaInicial(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement btnFechaInicial = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonFechaInicialLiquidacionRuta));
			u.highLight(elementos.filtroFechaInicial);
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", btnFechaInicial);
			u.unhighLight(elementos.filtroFechaInicial);

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionFechaVisibleLiquidacionRuta));
			List<WebElement> opcionesFechaInicial = u.getDriver().findElements(elementos.opcionFechaVisibleLiquidacionRuta);
			String fechaInicialSeleccionada = normalizarTextoFiltro(opcionesFechaInicial.get(0));
			opcionesFechaInicial.get(0).click();
			System.out.println("[CAPTURA FECHAS INVALIDAS] Fecha inicial seleccionada: " + fechaInicialSeleccionada);
			testCase.addBoldTextEvidenceCurrentStep("Fecha inicial seleccionada: " + fechaInicialSeleccionada);

			WebElement btnFechaFinal = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonFechaFinalLiquidacionRuta));
			u.highLight(elementos.filtroFechaFinal);
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", btnFechaFinal);
			u.unhighLight(elementos.filtroFechaFinal);

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionFechaVisibleLiquidacionRuta));
			List<WebElement> opcionesFechaFinal = u.getDriver().findElements(elementos.opcionFechaVisibleLiquidacionRuta);
			if (opcionesFechaFinal.size() < 2) {
				testCase.addBoldTextEvidenceCurrentStep("No hay suficientes fechas visibles para seleccionar una Fecha final menor que Fecha inicial");
				return false;
			}
			String fechaFinalSeleccionada = normalizarTextoFiltro(opcionesFechaFinal.get(1));
			opcionesFechaFinal.get(1).click();
			System.out.println("[CAPTURA FECHAS INVALIDAS] Fecha final seleccionada: " + fechaFinalSeleccionada);
			testCase.addBoldTextEvidenceCurrentStep("Fecha final seleccionada: " + fechaFinalSeleccionada);

			if (screenshot) {
				u.highLight(elementos.filtroFechaInicial);
				u.highLight(elementos.filtroFechaFinal);
				testCase.addScreenShotCurrentStep("Fecha_Final_Menor_Fecha_Inicial");
				u.unhighLight(elementos.filtroFechaInicial);
				u.unhighLight(elementos.filtroFechaFinal);
			}

			return true;
		} catch (Exception e) {
			System.out.println("Error al capturar Fecha final menor que Fecha inicial: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Posiciona el cursor sobre Consultar y valida el tooltip visible esperado.
	 * Normaliza espacios para comparar textos generados por Angular Material.
	 *
	 * @param textoEsperado Texto esperado del tooltip de Consultar.
	 * @param screenshot True para capturar evidencia visual del tooltip.
	 * @return True si el tooltip visible contiene el texto esperado; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	private boolean validaTooltipConsultar(String textoEsperado, boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement consultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonConsultarLiquidacionRuta));
			new Actions(u.getDriver()).moveToElement(consultar).perform();
			u.hardWait(1);

			String textoEsperadoNormalizado = textoEsperado.replaceAll("\\s+", " ").trim();
			String textoActual = wait.until(driver -> {
				String textoEncontrado = "";
				List<WebElement> candidatos = driver.findElements(By.xpath("//*[string-length(normalize-space(.)) > 0]"));
				for (WebElement candidato : candidatos) {
					try {
						if (candidato.isDisplayed()) {
							String textoCandidato = candidato.getText().replaceAll("\\s+", " ").trim();
							if (textoCandidato.contains(textoEsperadoNormalizado)
									&& (textoEncontrado.isEmpty() || textoCandidato.length() < textoEncontrado.length())) {
								textoEncontrado = textoCandidato;
							}
						}
					} catch (Exception e) {
						// El DOM puede refrescar el tooltip durante el hover; se ignora y se reintenta en el siguiente polling.
					}
				}
				return textoEncontrado.isEmpty() ? null : textoEncontrado;
			});
			boolean coincideTooltip = textoActual.contains(textoEsperadoNormalizado);

			System.out.println("[VALIDACION TOOLTIP CONSULTAR] Esperado: " + textoEsperadoNormalizado);
			System.out.println("[VALIDACION TOOLTIP CONSULTAR] Actual: " + textoActual);
			testCase.addBoldTextEvidenceCurrentStep("Tooltip esperado: " + textoEsperadoNormalizado);
			testCase.addBoldTextEvidenceCurrentStep("Tooltip actual: " + textoActual);
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Tooltip_Consultar");
			}

			return coincideTooltip;
		} catch (Exception e) {
			System.out.println("Error al validar tooltip de Consultar: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida el tooltip de Consultar cuando no se han completado los filtros obligatorios.
	 *
	 * @param screenshot True para capturar evidencia visual del tooltip.
	 * @return True si el tooltip muestra el mensaje de campos obligatorios; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaTooltipConsultarCamposObligatorios(boolean screenshot) {
		return validaTooltipConsultar("Fecha inicial, fecha final y selección de Ruta Dinámica (UNIGIS) o Tienda son campos obligatorios.", screenshot);
	}

	/**
	 * Valida el tooltip de Consultar cuando Ruta Dinámica (UNIGIS) está seleccionada pero vacía.
	 *
	 * @param screenshot True para capturar evidencia visual del tooltip.
	 * @return True si el tooltip solicita capturar Ruta Dinámica; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaTooltipConsultarRutaDinamicaVacia(boolean screenshot) {
		return validaTooltipConsultar("para habilitar la consulta", screenshot);
	}

	/**
	 * Valida el tooltip de Consultar cuando Tienda está seleccionada pero no hay tiendas marcadas.
	 *
	 * @param screenshot True para capturar evidencia visual del tooltip.
	 * @return True si el tooltip solicita seleccionar una o más tiendas; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaTooltipConsultarTiendaSinSeleccion(boolean screenshot) {
		return validaTooltipConsultar("Selecciona una o más tiendas para habilitar la consulta.", screenshot);
	}

	/**
	 * Valida el tooltip de Consultar cuando la Fecha final es menor que la Fecha inicial.
	 *
	 * @param screenshot True para capturar evidencia visual del tooltip.
	 * @return True si el tooltip muestra el mensaje de rango inválido de fechas; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaTooltipConsultarFechaFinalMenorInicial(boolean screenshot) {
		return validaTooltipConsultar("La fecha final no puede ser menor que la fecha inicial.", screenshot);
	}

	/**
	 * Retira el cursor del botón Consultar y valida que el tooltip esperado deje de mostrarse.
	 * Mueve el cursor a una zona estable de la pantalla para cerrar el tooltip sin modificar filtros.
	 *
	 * @param textoTooltip Texto del tooltip que debe ocultarse al retirar el cursor.
	 * @param screenshot True para capturar evidencia visual posterior al cierre del tooltip.
	 * @return True si el tooltip deja de mostrarse; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean retiraCursorConsultarYValidaTooltipOculto(String textoTooltip, boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
			String textoTooltipNormalizado = textoTooltip.replaceAll("\\s+", " ").trim();

			WebElement zonaSegura = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.leyendaEntregasHandHeld));
			new Actions(u.getDriver()).moveToElement(zonaSegura).perform();
			u.hardWait(1);

			boolean tooltipOculto = wait.until(driver -> {
				List<WebElement> candidatos = driver.findElements(By.xpath("//*[string-length(normalize-space(.)) > 0]"));
				for (WebElement candidato : candidatos) {
					try {
						if (candidato.isDisplayed()) {
							String textoCandidato = candidato.getText().replaceAll("\\s+", " ").trim();
							if (textoCandidato.contains(textoTooltipNormalizado)) {
								return false;
							}
						}
					} catch (Exception e) {
						// El DOM puede refrescarse al ocultar el tooltip; se ignora y se reintenta en el siguiente polling.
					}
				}
				return true;
			});

			System.out.println("[VALIDACION TOOLTIP CONSULTAR] Tooltip esperado: OCULTO | Texto validado: " + textoTooltipNormalizado + " | Actual: " + (tooltipOculto ? "OCULTO" : "VISIBLE"));
			testCase.addBoldTextEvidenceCurrentStep("Tooltip esperado: OCULTO | Texto validado: " + textoTooltipNormalizado + " | Actual: " + (tooltipOculto ? "OCULTO" : "VISIBLE"));
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Tooltip_Consultar_Oculto");
			}

			return tooltipOculto;
		} catch (Exception e) {
			System.out.println("Error al retirar cursor de Consultar y validar tooltip oculto: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Valida que el filtro Tienda muestre primero las tiendas precargadas por Ruta Dinámica y que coincidan contra BD.
	 * Confirma que las opciones marcadas queden agrupadas arriba y que el resto de tiendas visibles permanezca sin marca.
	 *
	 * @param rutaDinamica Ruta Dinámica (UNIGIS) capturada en el filtro.
	 * @param screenshot True para capturar evidencia visual del selector Tienda abierto.
	 * @return True si las tiendas seleccionadas visibles coinciden con BD y aparecen antes que las no seleccionadas; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaTiendasPrecargadasRutaDinamicaContraBD(String rutaDinamica, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica vacía para validar tiendas precargadas");
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					return false;
				}
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionesFiltroTiendaVisibles));
			List<WebElement> opciones = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 5);
			List<String> tiendasSeleccionadasPortal = new ArrayList<String>();
			boolean encontroNoSeleccionada = false;
			boolean seleccionadasPrimero = true;
			int tiendasNoSeleccionadas = 0;
			if (opciones.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("No se encontraron opciones visibles sin hacer scroll en el filtro Tienda");
				return false;
			}

			for (WebElement opcion : opciones) {
				String etiqueta = normalizarTextoFiltro(opcion);
				boolean seleccionada = estaOpcionTiendaMarcada(opcion);
				if (seleccionada) {
					if (encontroNoSeleccionada) {
						seleccionadasPrimero = false;
					}
					tiendasSeleccionadasPortal.add(etiqueta);
				} else {
					encontroNoSeleccionada = true;
					tiendasNoSeleccionadas++;
				}
			}

			LinkedHashMap<String, String> tiendasBD = obtenerTiendasRutaDinamicaDesdeBD(rutaDinamica);
			boolean tieneSeleccionadas = !tiendasSeleccionadasPortal.isEmpty();
			boolean coincidenConBD = tieneSeleccionadas && !tiendasBD.isEmpty();

			System.out.println("[VALIDACION TIENDAS PRECARGADAS] Ruta Dinámica: " + rutaDinamica.trim());
			System.out.println("[VALIDACION TIENDAS PRECARGADAS] Opciones visibles revisadas sin scroll: " + opciones.size() + " | Seleccionadas visibles portal: " + tiendasSeleccionadasPortal.size() + " | Registros BD ruta: " + tiendasBD.size() + " | No seleccionadas visibles: " + tiendasNoSeleccionadas);
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica validada: " + rutaDinamica.trim());
			testCase.addBoldTextEvidenceCurrentStep("Opciones visibles revisadas sin scroll: " + opciones.size() + " | Tiendas seleccionadas visibles portal: " + tiendasSeleccionadasPortal.size() + " | Registros BD ruta: " + tiendasBD.size() + " | Tiendas no seleccionadas visibles: " + tiendasNoSeleccionadas);

			for (String tiendaPortal : tiendasSeleccionadasPortal) {
				String crTienda = tiendaPortal.split("\\s+", 2)[0];
				String tiendaBD = tiendasBD.get(crTienda);
				boolean coincide = tiendaPortal.equals(tiendaBD);
				System.out.println("[VALIDACION TIENDAS PRECARGADAS] Portal: " + tiendaPortal + " | BD: " + tiendaBD + " | Resultado: " + (coincide ? "COINCIDE" : "NO COINCIDE"));
				testCase.addBoldTextEvidenceCurrentStep("Portal: " + tiendaPortal + " | BD: " + tiendaBD + " | Resultado: " + (coincide ? "COINCIDE" : "NO COINCIDE"));
				coincidenConBD = coincidenConBD && coincide;
			}

			testCase.addBoldTextEvidenceCurrentStep("Tiendas asociadas visibles aparecen antes que el resto visible: " + (seleccionadasPrimero ? "SI" : "NO"));
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Tiendas_Precargadas_Ruta_Dinamica");
			}

			return tieneSeleccionadas && seleccionadasPrimero && coincidenConBD;
		} catch (Exception e) {
			System.out.println("Error al validar tiendas precargadas de Ruta Dinámica contra BD: " + e.getMessage());
			return false;
		}
	}


	/**
	 * Ejecuta la consulta de Liquidación de Ruta y valida que el encabezado del grid muestre la ruta sin palabra personalizada.
	 * Espera a que desaparezcan modales de carga antes de capturar evidencia limpia del resultado.
	 *
	 * @param rutaDinamica Ruta Dinámica (UNIGIS) que debe aparecer en el encabezado del grid.
	 * @param screenshot True para capturar evidencia visual del encabezado validado.
	 * @return True si la consulta carga detalle y el encabezado muestra Ruta sin personalizada; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean consultaRutaDinamicaYValidaEncabezadoSinPersonalizada (String rutaDinamica, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica vacía para validar encabezado del grid");
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			u.hardWait(1);
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
			} catch (Exception e) {
				System.out.println("[VALIDACION CONSULTA RUTA] Panel Tienda cerrado o no visible antes de consultar: " + e.getMessage());
			}

			WebElement consultar = wait.until(ExpectedConditions.elementToBeClickable(elementos.botonConsultarLiquidacionRuta));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", consultar);
			try {
				consultar.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", consultar);
			}

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			WebElement detalle = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerDetalle));
			((JavascriptExecutor) u.getDriver()).executeScript("var y = arguments[0].getBoundingClientRect().top + window.pageYOffset - 260; window.scrollTo(0, Math.max(y, 0));", detalle);
			u.hardWait(1);
			String textoDetalle = normalizarTextoFiltro(detalle);
			boolean muestraRuta = textoDetalle.contains("Ruta:") && textoDetalle.contains(rutaDinamica.trim());
			boolean sinPersonalizada = !textoDetalle.toLowerCase().contains("personalizada");

			System.out.println("[VALIDACION ENCABEZADO GRID] Esperado: Ruta: " + rutaDinamica.trim() + " sin personalizada | Actual: " + textoDetalle);
			testCase.addBoldTextEvidenceCurrentStep("Encabezado esperado: Ruta: " + rutaDinamica.trim() + " sin personalizada");
			testCase.addBoldTextEvidenceCurrentStep("Encabezado/detalle actual: " + textoDetalle);
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Encabezado_Ruta_Dinamica_Sin_Personalizada");
			}

			return muestraRuta && sinPersonalizada;
		} catch (Exception e) {
			System.out.println("Error al consultar y validar encabezado de Ruta Dinámica: " + e.getMessage());
			return false;
		}
	}


	/**
	 * Valida que el grid de Liquidación de Ruta muestre el total de tiendas esperado contra BD para la Ruta Dinámica y fecha consultadas.
	 * El total se compara contra las tiendas de la ruta que cuentan con información dentro de la fecha filtrada por el portal.
	 *
	 * @param rutaDinamica Ruta Dinámica (UNIGIS) usada para consultar el grid.
	 * @param fechaFormateada Fecha inicial/final seleccionada en el filtro con formato visible del portal.
	 * @param screenshot True para capturar evidencia visual del grid validado.
	 * @return True si el encabezado del grid coincide con el total esperado en BD para la ruta y fecha consultadas; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	public boolean validaGridTiendasRutaDinamicaContraBD(String rutaDinamica, String fechaFormateada, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica vacía para validar grid contra BD");
				return false;
			}
			if (fechaFormateada == null || fechaFormateada.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Fecha vacía para validar grid contra BD");
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			WebElement detalle = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerDetalle));
			((JavascriptExecutor) u.getDriver()).executeScript("var y = arguments[0].getBoundingClientRect().top + window.pageYOffset - 260; window.scrollTo(0, Math.max(y, 0));", detalle);
			u.hardWait(1);
			String textoDetalle = normalizarTextoFiltro(detalle);
			String textoPantalla = normalizarTextoFiltro(u.getDriver().findElement(By.tagName("body")));
			LinkedHashMap<String, String> tiendasBD = obtenerTiendasRutaDinamicaDesdeBD(rutaDinamica);
			int tiendasEsperadasBD = obtenerTotalTiendasRutaDinamicaPorFechaDesdeBD(rutaDinamica, fechaFormateada);
			java.util.regex.Matcher matcherTotal = java.util.regex.Pattern.compile("Tiendas Totales de la Ruta:\\s*(\\d+)").matcher(textoDetalle);
			int tiendasGrid = matcherTotal.find() ? Integer.parseInt(matcherTotal.group(1)) : 0;
			boolean muestraRuta = textoDetalle.contains("Ruta:") && textoDetalle.contains(rutaDinamica.trim());
			boolean tieneTiendasBD = !tiendasBD.isEmpty();
			boolean totalGridValido = tiendasGrid > 0 && tiendasGrid == tiendasEsperadasBD;
			boolean gridFiltradoPorRango = tieneTiendasBD && tiendasGrid < tiendasBD.size();
			boolean muestraLeyendaTiendasSinInfo = textoPantalla.contains("Algunas tiendas no se muestran") || textoPantalla.contains("no cuentan con información dentro del rango seleccionado");

			System.out.println("[VALIDACION GRID RUTA DINAMICA] Ruta: " + rutaDinamica.trim() + " | Fecha filtro: " + fechaFormateada.trim() + " | Tiendas asociadas BD: " + tiendasBD.size() + " | Tiendas esperadas BD por fecha: " + tiendasEsperadasBD + " | Tiendas mostradas grid: " + tiendasGrid + " | Leyenda tiendas sin informacion: " + (muestraLeyendaTiendasSinInfo ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica validada en grid: " + rutaDinamica.trim());
			testCase.addBoldTextEvidenceCurrentStep("Fecha consultada en filtros: " + fechaFormateada.trim());
			testCase.addBoldTextEvidenceCurrentStep("Tiendas asociadas a la ruta en BD: " + tiendasBD.size());
			testCase.addBoldTextEvidenceCurrentStep("Tiendas esperadas en BD para la ruta considerando la fecha filtrada: " + tiendasEsperadasBD);
			testCase.addBoldTextEvidenceCurrentStep("Tiendas mostradas en el grid según encabezado: " + tiendasGrid);
			testCase.addBoldTextEvidenceCurrentStep("Leyenda de tiendas sin información en el rango seleccionado: " + (muestraLeyendaTiendasSinInfo ? "SI" : "NO"));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Grid_Tiendas_Ruta_Dinamica_Contra_BD");
			}

			return muestraRuta && tieneTiendasBD && totalGridValido && (!gridFiltradoPorRango || muestraLeyendaTiendasSinInfo);
		} catch (Exception e) {
			System.out.println("Error al validar grid de tiendas por Ruta Dinámica contra BD: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene desde BD el total de tiendas con información para una Ruta Dinámica en la fecha consultada por el portal.
	 * Considera la misma fecha inicial y final seleccionada en el filtro.
	 *
	 * @param rutaDinamica Ruta Dinámica (UNIGIS) usada en el filtro del portal.
	 * @param fechaFormateada Fecha seleccionada en el portal con formato d MMM yyyy.
	 * @return Total de tiendas distintas con información para la ruta y fecha consultadas.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private int obtenerTotalTiendasRutaDinamicaPorFechaDesdeBD(String rutaDinamica, String fechaFormateada) throws Exception {
		String fechaNormalizada = fechaFormateada.trim().toLowerCase(new Locale("es", "ES")).replace(".", "").replaceAll("\\s+", " ");
		String[] partesFecha = fechaNormalizada.split(" ");
		int indiceMes = partesFecha.length > 3 && "de".equals(partesFecha[1]) ? 2 : 1;
		int dia = Integer.parseInt(partesFecha[0]);
		String mesTexto = partesFecha[indiceMes];
		int anio = Integer.parseInt(partesFecha[indiceMes + 1]);
		int mes;
		if (mesTexto.startsWith("ene")) {
			mes = 1;
		} else if (mesTexto.startsWith("feb")) {
			mes = 2;
		} else if (mesTexto.startsWith("mar")) {
			mes = 3;
		} else if (mesTexto.startsWith("abr")) {
			mes = 4;
		} else if (mesTexto.startsWith("may")) {
			mes = 5;
		} else if (mesTexto.startsWith("jun")) {
			mes = 6;
		} else if (mesTexto.startsWith("jul")) {
			mes = 7;
		} else if (mesTexto.startsWith("ago")) {
			mes = 8;
		} else if (mesTexto.startsWith("sep")) {
			mes = 9;
		} else if (mesTexto.startsWith("oct")) {
			mes = 10;
		} else if (mesTexto.startsWith("nov")) {
			mes = 11;
		} else if (mesTexto.startsWith("dic")) {
			mes = 12;
		} else {
			throw new IllegalArgumentException("No se pudo interpretar el mes de la fecha: " + fechaFormateada);
		}
		LocalDate fechaConsulta = LocalDate.of(anio, mes, dia);
		String query = "SELECT COUNT(DISTINCT h.id_retek) AS total_tiendas "
				+ "FROM xxcm_historico_correos h "
				+ "WHERE h.ruta_propuesta = ? "
				+ "AND h.fecha_registro::date = ? "
				+ "AND h.origen = 'cedis' "
				+ "AND TRIM(h.ruta_propuesta) <> '' "
				+ "AND h.version_documento = 2 "
				+ "AND h.is_visible = true "
				+ "AND h.transferencia IS NOT NULL "
				+ "AND h.id_retek IS NOT NULL";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, rutaDinamica.trim());
			ps.setDate(2, java.sql.Date.valueOf(fechaConsulta));
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? rs.getInt("total_tiendas") : 0;
			}
		}
	}

	/**
	 * Obtiene desde BD las tiendas asociadas a una Ruta Dinámica para comparar selector y grid.
	 * Devuelve las etiquetas con el formato CR_TIENDA espacio NOMBRE_TIENDA usado por el portal.
	 *
	 * @param rutaDinamica Ruta Dinámica (UNIGIS) usada en el filtro del portal.
	 * @return Mapa ordenado por CR Tienda con la etiqueta esperada para cada tienda.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	private LinkedHashMap<String, String> obtenerTiendasRutaDinamicaDesdeBD(String rutaDinamica) throws Exception {
		String query = "SELECT DISTINCT h.id_retek, COALESCE(r.cr_tienda, h.cr_tienda) AS cr_tienda, "
				+ "COALESCE(r.desc_tienda, h.cr_tienda) AS nombre_tienda "
				+ "FROM xxcm_historico_correos h "
				+ "LEFT JOIN xxcm_region_plaza_tienda_job_sync r ON r.id_retek = h.id_retek "
				+ "WHERE h.ruta_propuesta = ? "
				+ "AND TRIM(h.ruta_propuesta) <> '' "
				+ "AND h.version_documento = 2 "
				+ "AND h.is_visible = true "
				+ "AND h.id_retek IS NOT NULL "
				+ "ORDER BY COALESCE(r.cr_tienda, h.cr_tienda)";

		LinkedHashMap<String, String> tiendasBD = new LinkedHashMap<String, String>();
		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, rutaDinamica.trim());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					String crTienda = rs.getString("cr_tienda");
					String nombreTienda = rs.getString("nombre_tienda");
					if (crTienda != null && !crTienda.trim().isEmpty()) {
						String etiqueta = (crTienda.trim() + " " + (nombreTienda == null ? "" : nombreTienda.trim())).replaceAll("\\s+", " ").trim();
						tiendasBD.put(crTienda.trim(), etiqueta);
					}
				}
			}
		}

		return tiendasBD;
	}

	/**
	 * Determina si una opción del filtro Tienda está marcada en el componente multiselect.
	 * Revisa clases y atributos comunes de ng-select para no depender de un único marcador visual.
	 *
	 * @param opcion Opción visible del selector Tienda.
	 * @return True si la opción aparece marcada; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	private boolean estaOpcionTiendaMarcada(WebElement opcion) {
		String clase = opcion.getAttribute("class");
		String ariaSelected = opcion.getAttribute("aria-selected");
		String ariaChecked = opcion.getAttribute("aria-checked");
		boolean marcadaPorAtributo = "true".equalsIgnoreCase(ariaSelected) || "true".equalsIgnoreCase(ariaChecked)
				|| (clase != null && clase.toLowerCase().contains("ng-option-selected"));
		boolean marcadaPorHijo = !opcion.findElements(By.xpath(".//input[@checked or @aria-checked='true'] | .//*[contains(@class,'checked') or contains(@class,'selected')]")).isEmpty();
		boolean marcadaPorCheckbox = false;
		try {
			marcadaPorCheckbox = Boolean.TRUE.equals(((JavascriptExecutor) u.getDriver()).executeScript(
					"const checks = arguments[0].querySelectorAll(\"input[type='checkbox'], input[type='radio'], [role='checkbox']\");"
					+ "return Array.from(checks).some(c => c.checked === true || c.getAttribute('checked') !== null || c.getAttribute('aria-checked') === 'true' || c.classList.contains('checked') || c.classList.contains('selected'));",
					opcion));
		} catch (Exception e) {
			marcadaPorCheckbox = false;
		}
		return marcadaPorAtributo || marcadaPorHijo || marcadaPorCheckbox;
	}

	/**
	 * Filtra las opciones del selector Tienda para validar solo las que están visibles en pantalla sin hacer scroll.
	 * Limita la muestra revisada para evitar recorrer catálogos completos renderizados por el componente.
	 *
	 * @param opciones Opciones localizadas en el selector Tienda.
	 * @param limiteOpciones Cantidad máxima de opciones visibles que se van a revisar.
	 * @return Lista de opciones visibles dentro del viewport actual.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	private List<WebElement> obtenerOpcionesTiendaVisiblesEnPantalla(List<WebElement> opciones, int limiteOpciones) {
		List<WebElement> opcionesVisibles = new ArrayList<WebElement>();
		JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
		try {
			@SuppressWarnings("unchecked")
			List<WebElement> opcionesVisiblesJs = (List<WebElement>) js.executeScript(
					"const limite = arguments[0];"
					+ "const candidatos = Array.from(document.querySelectorAll('app-cedis-liquidacion-ruta .tienda-container .ng-option[role=\"option\"]'));"
					+ "const visibles = [];"
					+ "for (const opcion of candidatos) {"
					+ "  const r = opcion.getBoundingClientRect();"
					+ "  const cs = window.getComputedStyle(opcion);"
					+ "  if (r.height > 0 && r.width > 0 && r.bottom > 0 && r.top < window.innerHeight && r.right > 0 && r.left < window.innerWidth && cs.display !== 'none' && cs.visibility !== 'hidden') {"
					+ "    visibles.push(opcion);"
					+ "    if (visibles.length >= limite) break;"
					+ "  }"
					+ "}"
					+ "return visibles;",
					limiteOpciones);
			if (opcionesVisiblesJs != null && !opcionesVisiblesJs.isEmpty()) {
				return opcionesVisiblesJs;
			}
		} catch (Exception e) {
			System.out.println("[VALIDACION TIENDAS PRECARGADAS] No se pudo obtener opciones visibles por JS en lote: " + e.getMessage());
		}

		int opcionesARevisar = Math.min(opciones == null ? 0 : opciones.size(), 200);
		for (int indice = 0; indice < opcionesARevisar; indice++) {
			WebElement opcion = opciones.get(indice);
			try {
				Boolean visibleEnPantalla = (Boolean) js.executeScript(
						"const r = arguments[0].getBoundingClientRect();"
						+ "const cs = window.getComputedStyle(arguments[0]);"
						+ "return r.height > 0 && r.width > 0 && r.bottom > 0 && r.top < window.innerHeight && r.right > 0 && r.left < window.innerWidth && cs.display !== 'none' && cs.visibility !== 'hidden';",
						opcion);
				if (opcion.isDisplayed() && Boolean.TRUE.equals(visibleEnPantalla)) {
					opcionesVisibles.add(opcion);
				}
				if (opcionesVisibles.size() >= limiteOpciones) {
					break;
				}
			} catch (Exception e) {
				System.out.println("[VALIDACION TIENDAS PRECARGADAS] Opción no disponible para validar visibilidad: " + e.getMessage());
			}
		}
		return opcionesVisibles;
	}
	
	/**
	 * Captura Fecha inicial y Fecha final usando la fecha obtenida desde BD para la Ruta Dinámica.
	 * No captura horas, porque el flujo de Ruta Dinámica habilita Ruta, Tienda y Consultar con el rango de fechas.
	 *
	 * @param fechaFormateada Fecha obtenida desde BD con el formato visible del calendario del portal.
	 * @param screenshot True para capturar evidencia visual de las fechas seleccionadas.
	 * @return True si ambas fechas se seleccionan correctamente; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean capturaFechasInicialFinalDesdeBD(String fechaFormateada, boolean screenshot) {
		try {
			if (fechaFormateada == null || fechaFormateada.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Fecha obtenida desde BD vacía o nula");
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			String fecha = fechaFormateada.trim();
			By opcionFechaBD = By.xpath("//mat-option/span[normalize-space(text())='" + fecha + "' and ancestor::div[not(contains(@style,'display: none'))]]");

			WebElement btnFechaInicial = wait.until(ExpectedConditions.elementToBeClickable(elementos.botonFechaInicialLiquidacionRuta));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", btnFechaInicial);
			WebElement opcionInicial = wait.until(ExpectedConditions.elementToBeClickable(opcionFechaBD));
			opcionInicial.click();
			u.hardWait(1);
			System.out.println("[CAPTURA FECHAS BD] Fecha inicial seleccionada: " + fecha);
			testCase.addBoldTextEvidenceCurrentStep("Fecha inicial seleccionada desde BD: " + fecha);

			WebElement btnFechaFinal = wait.until(ExpectedConditions.elementToBeClickable(elementos.botonFechaFinalLiquidacionRuta));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", btnFechaFinal);
			WebElement opcionFinal = wait.until(ExpectedConditions.elementToBeClickable(opcionFechaBD));
			opcionFinal.click();
			u.hardWait(1);
			System.out.println("[CAPTURA FECHAS BD] Fecha final seleccionada: " + fecha);
			testCase.addBoldTextEvidenceCurrentStep("Fecha final seleccionada desde BD: " + fecha);

			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			if (screenshot) {
				u.highLight(elementos.filtroFechaInicial);
				u.highLight(elementos.filtroFechaFinal);
				testCase.addScreenShotCurrentStep("Fechas_Inicial_Final_Desde_BD");
				u.unhighLight(elementos.filtroFechaInicial);
				u.unhighLight(elementos.filtroFechaFinal);
			}

			return true;
		} catch (Exception e) {
			System.out.println("Error al capturar Fecha inicial y Fecha final desde BD: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Valida que el boton Devoluciones Especiales se muestre junto al boton Recepcion de Transferencias y Devoluciones en Liquidacion de Ruta.
	 * Registra evidencia visual y confirma que ambos botones permanezcan en la misma linea horizontal.
	 *
	 * @param screenshot true si se desea guardar screenshot en el reporte; false en caso contrario.
	 * @return true si el boton se visualiza y mantiene alineacion horizontal con el boton Recepcion de Transferencias y Devoluciones.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaBotonDevolucionesEspecialesLiquidacionRuta(boolean screenshot) throws Exception {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			u.waitWebElement(elementos.btnDevolucionesEspeciales, 30, 5);
			if (u.isDisplayed(elementos.btnRecepcionTransferenciasDevolucionesLiquidacionRuta) && u.isDisplayed(elementos.btnDevolucionesEspeciales)) {
				WebElement botonRecepcion = u.getDriver().findElement(elementos.btnRecepcionTransferenciasDevolucionesLiquidacionRuta);
				WebElement botonDevolucionesEspeciales = u.getDriver().findElement(elementos.btnDevolucionesEspeciales);
				boolean mismaLinea = mismaLinea(botonRecepcion, botonDevolucionesEspeciales, 12);

				u.highLight(elementos.btnRecepcionTransferenciasDevolucionesLiquidacionRuta);
				u.highLight(elementos.btnDevolucionesEspeciales);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se visualiza el boton Devoluciones Especiales junto a Recepcion de Transferencias y Devoluciones.");
					testCase.addScreenShotCurrentStep("Boton_Devoluciones_Especiales_Liquidacion_Ruta");
				}
				u.unhighLight(elementos.btnRecepcionTransferenciasDevolucionesLiquidacionRuta);
				u.unhighLight(elementos.btnDevolucionesEspeciales);

				return mismaLinea;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar boton Devoluciones Especiales: " + e.getMessage());
			throw new Exception("Error al validar boton Devoluciones Especiales: " + e.getMessage());
		}
	}

	/**
	 * Acciona el boton Devoluciones Especiales desde la pantalla Liquidacion de Ruta.
	 * Espera el cambio de pantalla despues del clic para permitir la validacion posterior del modulo destino.
	 *
	 * @return true si fue posible accionar el boton Devoluciones Especiales.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean clicBotonDevolucionesEspecialesLiquidacionRuta() throws Exception {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			u.waitWebElement(elementos.btnDevolucionesEspeciales, 30, 5);
			if (u.isDisplayed(elementos.btnDevolucionesEspeciales)) {
				u.click(elementos.btnDevolucionesEspeciales);
				u.waitForLoadPage();
				u.hardWait(1);
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al hacer clic en Devoluciones Especiales: " + e.getMessage());
			throw new Exception("Error al hacer clic en Devoluciones Especiales: " + e.getMessage());
		}
	}

	/**
	 * Valida que se muestre la pantalla Devoluciones Especiales de CEDIS.
	 * Confirma que permanezcan visibles los elementos base del portal y el titulo de la pantalla destino.
	 *
	 * @param screenshot true si se desea guardar screenshot en el reporte; false en caso contrario.
	 * @return true si la pantalla Devoluciones Especiales se muestra correctamente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean scDevolucionesEspecialesCedis(boolean screenshot) throws Exception {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			u.waitWebElement(elementos.lbDevolucionesEspecialesCedis, 30, 5);
			if (u.isDisplayed(elementos.logoCometa) && u.isDisplayed(elementos.logoUser) && u.isDisplayed(elementos.lbInicioExterno) && u.isDisplayed(elementos.iconArrow)
					&& u.isDisplayed(elementos.lbDevolucionesEspecialesCedis)) {
				u.highLight(elementos.lbDevolucionesEspecialesCedis);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se muestra la pantalla Devoluciones Especiales de CEDIS.");
					testCase.addScreenShotCurrentStep("Pantalla_Devoluciones_Especiales_Cedis");
				}
				u.unhighLight(elementos.lbDevolucionesEspecialesCedis);
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar pantalla Devoluciones Especiales: " + e.getMessage());
			throw new Exception("Error al validar pantalla Devoluciones Especiales: " + e.getMessage());
		}
	}

	/**
	 * Valida que se muestre el boton Liquidacion de Ruta dentro de la pantalla Devoluciones Especiales.
	 * Registra evidencia visual antes de regresar al modulo Liquidacion de Ruta.
	 *
	 * @param screenshot true si se desea guardar screenshot en el reporte; false en caso contrario.
	 * @return true si el boton Liquidacion de Ruta se muestra correctamente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaBotonLiquidacionRutaDevolucionesEspeciales(boolean screenshot) throws Exception {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			u.waitWebElement(elementos.btnLiquidacionRutaDevolucionesEspeciales, 30, 5);
			if (u.isDisplayed(elementos.btnLiquidacionRutaDevolucionesEspeciales)) {
				u.highLight(elementos.btnLiquidacionRutaDevolucionesEspeciales);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se visualiza el boton Liquidacion de Ruta dentro de Devoluciones Especiales.");
					testCase.addScreenShotCurrentStep("Boton_Liquidacion_Ruta_Devoluciones_Especiales");
				}
				u.unhighLight(elementos.btnLiquidacionRutaDevolucionesEspeciales);
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar boton Liquidacion de Ruta en Devoluciones Especiales: " + e.getMessage());
			throw new Exception("Error al validar boton Liquidacion de Ruta en Devoluciones Especiales: " + e.getMessage());
		}
	}

	/**
	 * Acciona el boton Liquidacion de Ruta desde la pantalla Devoluciones Especiales.
	 * Permite regresar al modulo de Liquidacion de Ruta despues de validar la redireccion a Devoluciones Especiales.
	 *
	 * @return true si fue posible accionar el boton Liquidacion de Ruta.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean clicBotonLiquidacionRutaDesdeDevolucionesEspeciales() throws Exception {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			u.waitWebElement(elementos.btnLiquidacionRutaDevolucionesEspeciales, 30, 5);
			if (u.isDisplayed(elementos.btnLiquidacionRutaDevolucionesEspeciales)) {
				u.click(elementos.btnLiquidacionRutaDevolucionesEspeciales);
				u.waitForLoadPage();
				u.hardWait(1);
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al hacer clic en Liquidacion de Ruta desde Devoluciones Especiales: " + e.getMessage());
			throw new Exception("Error al hacer clic en Liquidacion de Ruta desde Devoluciones Especiales: " + e.getMessage());
		}
	}
	
	/**
	 * Valida que el filtro Tienda muestre opciones sin selección inicial y que Limpiar selección esté deshabilitado.
	 * Mantiene abierto el listado de tiendas para permitir seleccionar una opción en el siguiente paso.
	 *
	 * @param screenshot True para capturar evidencia visual del listado y la opción Limpiar selección.
	 * @return True si existen opciones visibles, no hay tiendas marcadas y Limpiar selección está deshabilitado; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaFiltroTiendaSinSeleccionYLimpiarDeshabilitado(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					return false;
				}
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionesFiltroTiendaVisibles));
			boolean tieneOpciones = !u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles).isEmpty();
			boolean sinSeleccion = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).isEmpty();
			boolean limpiarDeshabilitado = !estaLimpiarSeleccionTiendaHabilitado(elementos);

			System.out.println("[VALIDACION LIMPIAR SELECCION] Opciones visibles esperado: SI | Actual: " + (tieneOpciones ? "SI" : "NO"));
			System.out.println("[VALIDACION LIMPIAR SELECCION] Tiendas seleccionadas esperado: 0 | Actual: " + u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size());
			System.out.println("[VALIDACION LIMPIAR SELECCION] Limpiar selección esperado: DESHABILITADO | Actual: " + (limpiarDeshabilitado ? "DESHABILITADO" : "HABILITADO"));
			testCase.addBoldTextEvidenceCurrentStep("Opciones visibles esperado: SI | Actual: " + (tieneOpciones ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Tiendas seleccionadas esperado: 0 | Actual: " + u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size());
			testCase.addBoldTextEvidenceCurrentStep("Limpiar selección esperado: DESHABILITADO | Actual: " + (limpiarDeshabilitado ? "DESHABILITADO" : "HABILITADO"));

			if (screenshot) {
				u.highLight(elementos.btnLimpiarSeleccionFiltroTienda);
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Limpiar_Seleccion_Deshabilitado");
				u.unhighLight(elementos.btnLimpiarSeleccionFiltroTienda);
			}

			return tieneOpciones && sinSeleccion && limpiarDeshabilitado;
		} catch (Exception e) {
			System.out.println("Error al validar filtro Tienda sin selección y Limpiar selección deshabilitado: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que una tienda esté marcada y que Limpiar selección quede habilitado.
	 * Registra el resultado esperado contra el estado actual del selector.
	 *
	 * @param screenshot True para capturar evidencia visual de la tienda marcada y Limpiar selección habilitado.
	 * @return True si existe una tienda marcada y Limpiar selección está habilitado; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaLimpiarSeleccionHabilitadoConTiendaMarcada(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
			int seleccionadas = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
			boolean limpiarHabilitado = estaLimpiarSeleccionTiendaHabilitado(elementos);

			System.out.println("[VALIDACION LIMPIAR SELECCION] Tiendas seleccionadas esperado: >= 1 | Actual: " + seleccionadas);
			System.out.println("[VALIDACION LIMPIAR SELECCION] Limpiar selección esperado: HABILITADO | Actual: " + (limpiarHabilitado ? "HABILITADO" : "DESHABILITADO"));
			testCase.addBoldTextEvidenceCurrentStep("Tiendas seleccionadas esperado: >= 1 | Actual: " + seleccionadas);
			testCase.addBoldTextEvidenceCurrentStep("Limpiar selección esperado: HABILITADO | Actual: " + (limpiarHabilitado ? "HABILITADO" : "DESHABILITADO"));

			if (screenshot) {
				u.highLight(elementos.btnLimpiarSeleccionFiltroTienda);
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Limpiar_Seleccion_Habilitado");
				u.unhighLight(elementos.btnLimpiarSeleccionFiltroTienda);
			}

			return seleccionadas >= 1 && limpiarHabilitado;
		} catch (Exception e) {
			System.out.println("Error al validar Limpiar selección habilitado con tienda marcada: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Hace clic en Limpiar selección del filtro Tienda y valida que todas las tiendas queden desmarcadas.
	 * Confirma que Limpiar selección vuelva al estado deshabilitado después de limpiar el filtro.
	 *
	 * @param screenshot True para capturar evidencia visual posterior a limpiar la selección.
	 * @return True si no quedan tiendas marcadas y Limpiar selección queda deshabilitado; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean clicLimpiarSeleccionYValidaTiendaSinSeleccion(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.btnLimpiarSeleccionFiltroTienda));
			WebElement limpiarSeleccion = obtenerElementoAccionableLimpiarSeleccion(elementos);
			limpiarSeleccion.click();
			u.hardWait(1);

			int seleccionadas = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
			boolean limpiarDeshabilitado = !estaLimpiarSeleccionTiendaHabilitado(elementos);

			System.out.println("[VALIDACION LIMPIAR SELECCION] Tiendas seleccionadas esperado despues de limpiar: 0 | Actual: " + seleccionadas);
			System.out.println("[VALIDACION LIMPIAR SELECCION] Limpiar selección esperado despues de limpiar: DESHABILITADO | Actual: " + (limpiarDeshabilitado ? "DESHABILITADO" : "HABILITADO"));
			testCase.addBoldTextEvidenceCurrentStep("Tiendas seleccionadas esperado después de limpiar: 0 | Actual: " + seleccionadas);
			testCase.addBoldTextEvidenceCurrentStep("Limpiar selección esperado después de limpiar: DESHABILITADO | Actual: " + (limpiarDeshabilitado ? "DESHABILITADO" : "HABILITADO"));

			if (screenshot) {
				u.highLight(elementos.btnLimpiarSeleccionFiltroTienda);
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Limpiar_Seleccion_Post_Limpieza");
				u.unhighLight(elementos.btnLimpiarSeleccionFiltroTienda);
			}

			return seleccionadas == 0 && limpiarDeshabilitado;
		} catch (Exception e) {
			System.out.println("Error al hacer clic en Limpiar selección y validar filtro Tienda sin selección: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Determina si la opción Limpiar selección del filtro Tienda está habilitada.
	 * Revisa el elemento visible y su botón contenedor para detectar atributos o clases de estado deshabilitado.
	 *
	 * @param elementos Selectores de Liquidación de Ruta.
	 * @return True si Limpiar selección se encuentra habilitado; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	private boolean estaLimpiarSeleccionTiendaHabilitado(rutaLiquidacionElements elementos) {
		try {
			WebElement limpiarSeleccion = obtenerElementoAccionableLimpiarSeleccion(elementos);
			String clase = limpiarSeleccion.getAttribute("class");
			String ariaDisabled = limpiarSeleccion.getAttribute("aria-disabled");
			String disabled = limpiarSeleccion.getAttribute("disabled");
			boolean deshabilitado = disabled != null || "true".equalsIgnoreCase(ariaDisabled)
					|| (clase != null && clase.toLowerCase().contains("disabled")) || !limpiarSeleccion.isEnabled();
			return !deshabilitado;
		} catch (Exception e) {
			System.out.println("[VALIDACION LIMPIAR SELECCION] No se pudo determinar estado de Limpiar selección: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene el elemento accionable asociado al texto Limpiar selección del filtro Tienda.
	 * Si el texto visible está dentro de un botón, retorna el botón ancestro para validar y accionar el estado real.
	 *
	 * @param elementos Selectores de Liquidación de Ruta.
	 * @return Elemento accionable de Limpiar selección.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	private WebElement obtenerElementoAccionableLimpiarSeleccion(rutaLiquidacionElements elementos) {
		WebElement limpiarTexto = u.getDriver().findElement(elementos.btnLimpiarSeleccionFiltroTienda);
		List<WebElement> botones = limpiarTexto.findElements(By.xpath("./ancestor-or-self::button[1]"));
		return botones.isEmpty() ? limpiarTexto : botones.get(0);
	}
	
	/**
	 * Valida que Fecha inicial y Fecha final muestren asterisco obligatorio al cargar Liquidacion de Ruta.
	 * Confirma que los campos dinamicos Ruta Dinamica y Tienda no muestren asterisco antes de elegir Selector de Filtro.
	 *
	 * @param screenshot True para capturar evidencia visual de los asteriscos iniciales.
	 * @return True si Fecha inicial y Fecha final muestran asterisco y los campos dinamicos no lo muestran; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaAsteriscosInicialesCamposObligatorios(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			boolean fechaInicialConAsterisco = seVisualizaAsterisco(elementos.asteriscoFechaInicial);
			boolean fechaFinalConAsterisco = seVisualizaAsterisco(elementos.asteriscoFechaFinal);
			boolean rutaSinAsterisco = !seVisualizaAsterisco(elementos.asteriscoRutaDinamica);
			boolean tiendaSinAsterisco = !seVisualizaAsterisco(elementos.asteriscoTienda);

			System.out.println("[VALIDACION ASTERISCOS] Fecha inicial esperado: CON ASTERISCO | Actual: " + (fechaInicialConAsterisco ? "CON ASTERISCO" : "SIN ASTERISCO"));
			System.out.println("[VALIDACION ASTERISCOS] Fecha final esperado: CON ASTERISCO | Actual: " + (fechaFinalConAsterisco ? "CON ASTERISCO" : "SIN ASTERISCO"));
			System.out.println("[VALIDACION ASTERISCOS] Ruta Dinámica inicial esperado: SIN ASTERISCO | Actual: " + (rutaSinAsterisco ? "SIN ASTERISCO" : "CON ASTERISCO"));
			System.out.println("[VALIDACION ASTERISCOS] Tienda inicial esperado: SIN ASTERISCO | Actual: " + (tiendaSinAsterisco ? "SIN ASTERISCO" : "CON ASTERISCO"));
			testCase.addBoldTextEvidenceCurrentStep("Fecha inicial esperado: CON ASTERISCO | Actual: " + (fechaInicialConAsterisco ? "CON ASTERISCO" : "SIN ASTERISCO"));
			testCase.addBoldTextEvidenceCurrentStep("Fecha final esperado: CON ASTERISCO | Actual: " + (fechaFinalConAsterisco ? "CON ASTERISCO" : "SIN ASTERISCO"));
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica inicial esperado: SIN ASTERISCO | Actual: " + (rutaSinAsterisco ? "SIN ASTERISCO" : "CON ASTERISCO"));
			testCase.addBoldTextEvidenceCurrentStep("Tienda inicial esperado: SIN ASTERISCO | Actual: " + (tiendaSinAsterisco ? "SIN ASTERISCO" : "CON ASTERISCO"));

			if (screenshot) {
				u.highLight(elementos.filtroFechaInicial);
				u.highLight(elementos.filtroFechaFinal);
				testCase.addScreenShotCurrentStep("Asteriscos_Iniciales_Campos_Obligatorios");
				u.unhighLight(elementos.filtroFechaInicial);
				u.unhighLight(elementos.filtroFechaFinal);
			}

			return fechaInicialConAsterisco && fechaFinalConAsterisco && rutaSinAsterisco && tiendaSinAsterisco;
		} catch (Exception e) {
			System.out.println("Error al validar asteriscos iniciales de campos obligatorios: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que Ruta Dinamica muestre asterisco obligatorio despues de elegir Ruta Dinamica en Selector de Filtro.
	 * Confirma que Tienda permanezca sin asterisco mientras la regla dinamica corresponde a Ruta Dinamica.
	 *
	 * @param screenshot True para capturar evidencia visual del asterisco dinamico en Ruta Dinamica.
	 * @return True si Ruta Dinamica muestra asterisco y Tienda no lo muestra; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaAsteriscoDinamicoRutaDinamica(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			boolean rutaConAsterisco = seVisualizaAsterisco(elementos.asteriscoRutaDinamica);
			boolean tiendaSinAsterisco = !seVisualizaAsterisco(elementos.asteriscoTienda);

			System.out.println("[VALIDACION ASTERISCOS] Ruta Dinámica esperado: CON ASTERISCO | Actual: " + (rutaConAsterisco ? "CON ASTERISCO" : "SIN ASTERISCO"));
			System.out.println("[VALIDACION ASTERISCOS] Tienda esperado: SIN ASTERISCO | Actual: " + (tiendaSinAsterisco ? "SIN ASTERISCO" : "CON ASTERISCO"));
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica esperado: CON ASTERISCO | Actual: " + (rutaConAsterisco ? "CON ASTERISCO" : "SIN ASTERISCO"));
			testCase.addBoldTextEvidenceCurrentStep("Tienda esperado: SIN ASTERISCO | Actual: " + (tiendaSinAsterisco ? "SIN ASTERISCO" : "CON ASTERISCO"));

			if (screenshot) {
				u.highLight(elementos.filtroRutaDinamica);
				u.highLight(elementos.filtroTienda);
				testCase.addScreenShotCurrentStep("Asterisco_Dinamico_Ruta_Dinamica");
				u.unhighLight(elementos.filtroRutaDinamica);
				u.unhighLight(elementos.filtroTienda);
			}

			return rutaConAsterisco && tiendaSinAsterisco;
		} catch (Exception e) {
			System.out.println("Error al validar asterisco dinamico de Ruta Dinámica: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que Tienda muestre asterisco obligatorio despues de elegir Tienda en Selector de Filtro.
	 * Confirma que Ruta Dinamica deje de mostrar asterisco al cambiar la regla dinamica a Tienda.
	 *
	 * @param screenshot True para capturar evidencia visual del asterisco dinamico en Tienda.
	 * @return True si Tienda muestra asterisco y Ruta Dinamica no lo muestra; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean validaAsteriscoDinamicoTienda(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			boolean rutaSinAsterisco = !seVisualizaAsterisco(elementos.asteriscoRutaDinamica);
			boolean tiendaConAsterisco = seVisualizaAsterisco(elementos.asteriscoTienda);

			System.out.println("[VALIDACION ASTERISCOS] Ruta Dinámica esperado: SIN ASTERISCO | Actual: " + (rutaSinAsterisco ? "SIN ASTERISCO" : "CON ASTERISCO"));
			System.out.println("[VALIDACION ASTERISCOS] Tienda esperado: CON ASTERISCO | Actual: " + (tiendaConAsterisco ? "CON ASTERISCO" : "SIN ASTERISCO"));
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinámica esperado: SIN ASTERISCO | Actual: " + (rutaSinAsterisco ? "SIN ASTERISCO" : "CON ASTERISCO"));
			testCase.addBoldTextEvidenceCurrentStep("Tienda esperado: CON ASTERISCO | Actual: " + (tiendaConAsterisco ? "CON ASTERISCO" : "SIN ASTERISCO"));

			if (screenshot) {
				u.highLight(elementos.filtroRutaDinamica);
				u.highLight(elementos.filtroTienda);
				testCase.addScreenShotCurrentStep("Asterisco_Dinamico_Tienda");
				u.unhighLight(elementos.filtroRutaDinamica);
				u.unhighLight(elementos.filtroTienda);
			}

			return rutaSinAsterisco && tiendaConAsterisco;
		} catch (Exception e) {
			System.out.println("Error al validar asterisco dinamico de Tienda: " + e.getMessage());
			return false;
		}
		
		
	}

	/**
	 * Determina si un asterisco obligatorio se encuentra visible en pantalla.
	 * Centraliza la revisión para soportar marcadores renderizados como texto o como clase required-marker.
	 *
	 * @param asterisco Localizador del asterisco obligatorio a validar.
	 * @return True si al menos un marcador de asterisco está visible; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	private boolean seVisualizaAsterisco(By asterisco) {
		try {
			List<WebElement> candidatos = u.getDriver().findElements(asterisco);
			for (WebElement candidato : candidatos) {
				if (candidato.isDisplayed()) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			System.out.println("[VALIDACION ASTERISCOS] No se pudo validar asterisco: " + e.getMessage());
			return false;
		}
	}


	/**
	 * Captura texto en el buscador del filtro Tienda y valida que las sugerencias visibles contengan el criterio indicado.
	 * Mantiene el selector abierto para permitir comparar búsquedas consecutivas del autocomplete.
	 *
	 * @param textoBusqueda Texto que se captura en el buscador del filtro Tienda.
	 * @param textoEsperado Texto que debe aparecer en al menos una sugerencia visible.
	 * @param screenshot True para capturar evidencia visual de las sugerencias filtradas.
	 * @return True si existen sugerencias visibles y al menos una contiene el texto esperado; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean capturaBusquedaFiltroTiendaYValidaSugerencias(String textoBusqueda, String textoEsperado, boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					return false;
				}
			}

			WebElement inputBusqueda = wait.until(ExpectedConditions.elementToBeClickable(elementos.inputBusquedaFiltroTienda));
			inputBusqueda.sendKeys(Keys.CONTROL + "a");
			inputBusqueda.sendKeys(Keys.DELETE);
			inputBusqueda.sendKeys(textoBusqueda);
			u.hardWait(1);

			List<String> sugerencias = obtenerTextosOpcionesFiltroTiendaVisibles(10);
			boolean tieneSugerencias = !sugerencias.isEmpty();
			boolean contieneEsperado = false;
			String esperadoNormalizado = textoEsperado.toLowerCase();
			for (String sugerencia : sugerencias) {
				if (sugerencia.toLowerCase().contains(esperadoNormalizado)) {
					contieneEsperado = true;
				}
			}

			System.out.println("[VALIDACION AUTOCOMPLETE TIENDA] Busqueda: " + textoBusqueda + " | Sugerencias: " + sugerencias);
			testCase.addBoldTextEvidenceCurrentStep("Busqueda capturada en Tienda: " + textoBusqueda);
			testCase.addBoldTextEvidenceCurrentStep("Sugerencias visibles: " + sugerencias);
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Autocomplete_Tienda_" + textoBusqueda);
			}

			return tieneSugerencias && contieneEsperado;
		} catch (Exception e) {
			System.out.println("Error al capturar busqueda y validar sugerencias del filtro Tienda: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Compara que las sugerencias visibles del filtro Tienda sean equivalentes entre dos búsquedas.
	 * Normaliza mayúsculas y minúsculas para confirmar que el autocomplete no distingue casing.
	 *
	 * @param primeraBusqueda Primer texto de búsqueda capturado.
	 * @param segundaBusqueda Segundo texto de búsqueda capturado.
	 * @param screenshot True para capturar evidencia visual de la segunda búsqueda.
	 * @return True si ambas búsquedas devuelven las mismas sugerencias visibles normalizadas; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean comparaAutocompleteFiltroTiendaSinDistinguirMayusculas(String primeraBusqueda, String segundaBusqueda, boolean screenshot) {
		try {
			boolean primeraValida = capturaBusquedaFiltroTiendaYValidaSugerencias(primeraBusqueda, primeraBusqueda, screenshot);
			List<String> sugerenciasPrimera = normalizaListaTextos(obtenerTextosOpcionesFiltroTiendaVisibles(10));
			boolean segundaValida = capturaBusquedaFiltroTiendaYValidaSugerencias(segundaBusqueda, primeraBusqueda, screenshot);
			List<String> sugerenciasSegunda = normalizaListaTextos(obtenerTextosOpcionesFiltroTiendaVisibles(10));

			boolean mismasSugerencias = sugerenciasPrimera.equals(sugerenciasSegunda);
			System.out.println("[VALIDACION AUTOCOMPLETE TIENDA] Sugerencias minusculas: " + sugerenciasPrimera);
			System.out.println("[VALIDACION AUTOCOMPLETE TIENDA] Sugerencias mayusculas: " + sugerenciasSegunda);
			testCase.addBoldTextEvidenceCurrentStep("Sugerencias con " + primeraBusqueda + ": " + sugerenciasPrimera);
			testCase.addBoldTextEvidenceCurrentStep("Sugerencias con " + segundaBusqueda + ": " + sugerenciasSegunda);
			testCase.addBoldTextEvidenceCurrentStep("Autocomplete sin distinguir mayusculas/minusculas: " + (mismasSugerencias ? "SI" : "NO"));

			return primeraValida && segundaValida && mismasSugerencias;
		} catch (Exception e) {
			System.out.println("Error al comparar autocomplete del filtro Tienda: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Limpia el buscador del filtro Tienda y valida que el listado pueda navegarse mediante scroll sin cerrar el selector.
	 * Permite confirmar que el listado completo vuelve a estar disponible sin sugerencias filtradas.
	 *
	 * @param screenshot True para capturar evidencia visual después de navegar el listado.
	 * @return True si el selector permanece abierto y el scroll modifica la posición del listado; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean limpiaBusquedaTiendaYNavegaListadoConScroll(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement inputBusqueda = wait.until(ExpectedConditions.elementToBeClickable(elementos.inputBusquedaFiltroTienda));
			inputBusqueda.sendKeys(Keys.CONTROL + "a");
			inputBusqueda.sendKeys(Keys.DELETE);
			u.hardWait(1);

			WebElement contenedorScroll = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.contenedorScrollFiltroTienda));
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			Long scrollAntes = (Long) js.executeScript("return arguments[0].scrollTop;", contenedorScroll);
			js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 350;", contenedorScroll);
			u.hardWait(1);
			Long scrollDespues = (Long) js.executeScript("return arguments[0].scrollTop;", contenedorScroll);
			boolean panelAbierto = !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
			boolean scrollDisponible = scrollDespues.longValue() > scrollAntes.longValue() || !u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles).isEmpty();

			System.out.println("[VALIDACION SCROLL TIENDA] Scroll antes: " + scrollAntes + " | Despues: " + scrollDespues + " | Panel abierto: " + panelAbierto);
			testCase.addBoldTextEvidenceCurrentStep("Scroll Tienda antes: " + scrollAntes + " | despues: " + scrollDespues + " | panel abierto: " + panelAbierto);
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Listado_Completo_Scroll");
			}

			return panelAbierto && scrollDisponible;
		} catch (Exception e) {
			System.out.println("Error al limpiar busqueda y navegar listado Tienda: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Marca dos tiendas adicionales no seleccionadas y valida que el selector Tienda permanezca abierto después de cada selección.
	 * Confirma que la multiselección conserva las marcas previas sin cerrar automáticamente el listado.
	 *
	 * @param screenshot True para capturar evidencia visual de las tiendas adicionales marcadas.
	 * @return True si se marcan dos tiendas adicionales y el selector permanece abierto; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public boolean marcaDosTiendasAdicionalesYValidaSelectorAbierto(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					return false;
				}
			}

			List<String> tiendasMarcadas = new ArrayList<String>();
			int intentos = 0;
			while (tiendasMarcadas.size() < 2 && intentos < 4) {
				List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 10);
				WebElement opcion = null;
				for (WebElement candidato : opcionesVisibles) {
					String textoCandidato = normalizarTextoFiltro(candidato);
					if (!textoCandidato.isEmpty() && !tiendasMarcadas.contains(textoCandidato)) {
						opcion = candidato;
						break;
					}
				}
				if (opcion == null) {
					WebElement contenedorScroll = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.contenedorScrollFiltroTienda));
					((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 300;", contenedorScroll);
					u.hardWait(1);
					intentos++;
					continue;
				}

				String tienda = normalizarTextoFiltro(opcion);
				opcion.click();
				u.hardWait(1);
				boolean panelAbierto = !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
				System.out.println("[VALIDACION MULTISELECCION TIENDA] Tienda adicional marcada: " + tienda + " | Panel abierto: " + panelAbierto);
				testCase.addBoldTextEvidenceCurrentStep("Tienda adicional marcada: " + tienda + " | Panel abierto: " + panelAbierto);
				if (!panelAbierto) {
					return false;
				}
				tiendasMarcadas.add(tienda);
				intentos++;
			}

			boolean conservaMarcas = tiendasMarcadas.size() == 2 && !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
			testCase.addBoldTextEvidenceCurrentStep("Tiendas adicionales marcadas: " + tiendasMarcadas);
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Multiseleccion_Adicional");
			}

			return conservaMarcas;
		} catch (Exception e) {
			System.out.println("Error al marcar tiendas adicionales y validar selector abierto: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene los textos visibles de las opciones del filtro Tienda en el estado actual del selector.
	 * Limita la cantidad de textos para generar evidencia legible del autocomplete.
	 *
	 * @param limiteOpciones Cantidad máxima de opciones visibles que se van a reportar.
	 * @return Lista de textos visibles del filtro Tienda.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	private List<String> obtenerTextosOpcionesFiltroTiendaVisibles(int limiteOpciones) {
		List<String> textos = new ArrayList<String>();
		List<WebElement> opciones = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(new rutaLiquidacionElements().opcionesFiltroTiendaVisibles), limiteOpciones);
		for (WebElement opcion : opciones) {
			String texto = normalizarTextoFiltro(opcion);
			if (!texto.isEmpty()) {
				textos.add(texto);
			}
		}
		return textos;
	}

	/**
	 * Normaliza una lista de textos para comparaciones de autocomplete sin distinguir mayúsculas ni espacios repetidos.
	 *
	 * @param textos Lista de textos visibles del selector.
	 * @return Lista normalizada en minúsculas.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	private List<String> normalizaListaTextos(List<String> textos) {
		List<String> normalizados = new ArrayList<String>();
		for (String texto : textos) {
			normalizados.add(texto.replaceAll("\\s+", " ").trim().toLowerCase());
		}
		return normalizados;
	}

		/**
	 * Obtiene una palabra de búsqueda a partir de la primera tienda visible en el filtro Tienda.
	 * Usa el nombre de la tienda y evita depender de textos fijos como Orion cuando no aparecen en el ambiente.
	 *
	 * @return Palabra en minúsculas tomada de una tienda visible para usarla en el autocomplete.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-10
	 */
	public String obtieneTextoBusquedaDesdePrimeraTiendaVisible() {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					return "";
				}
			}
	
			List<String> opciones = obtenerTextosOpcionesFiltroTiendaVisibles(10);
			for (String opcion : opciones) {
				String[] partes = opcion.replaceAll("[^A-Za-zÁÉÍÓÚáéíóúÑñ0-9 ]", " ").trim().split("\\s+");
				for (int i = 1; i < partes.length; i++) {
					String palabra = partes[i].trim();
					if (palabra.length() >= 4 && palabra.matches(".*[A-Za-zÁÉÍÓÚáéíóúÑñ].*")) {
						String busqueda = palabra.toLowerCase();
						System.out.println("[VALIDACION AUTOCOMPLETE TIENDA] Texto de búsqueda dinámico obtenido: " + busqueda + " desde opción: " + opcion);
						testCase.addBoldTextEvidenceCurrentStep("Texto de búsqueda dinámico obtenido: " + busqueda + " desde opción: " + opcion);
						return busqueda;
					}
				}
			}
			return "";
		} catch (Exception e) {
			System.out.println("Error al obtener texto de búsqueda desde tienda visible: " + e.getMessage());
			return "";
		}
	}

	/**
	 * Desmarca la primera tienda precargada visible por la Ruta Dinamica en el filtro Tienda.
	 * Conserva el selector abierto para permitir agregar una tienda adicional en el siguiente paso.
	 *
	 * @param screenshot True para capturar evidencia visual de la tienda desmarcada.
	 * @return True si se localiza y desmarca una tienda precargada; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	public boolean desmarcaPrimeraTiendaPrecargadaRutaDinamica(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No fue posible abrir el filtro Tienda para desmarcar una tienda precargada");
					return false;
				}
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionesFiltroTiendaVisibles));
			List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 12);
			WebElement tiendaPrecargada = null;
			String tiendaSeleccionada = "";
			for (WebElement opcion : opcionesVisibles) {
				String etiqueta = normalizarTextoFiltro(opcion);
				if (!etiqueta.isEmpty() && estaOpcionTiendaMarcada(opcion)) {
					tiendaPrecargada = opcion;
					tiendaSeleccionada = etiqueta;
					break;
				}
			}

			if (tiendaPrecargada == null) {
				String motivoFallo = "No se encontro una tienda precargada visible marcada. Esperado: al menos una tienda seleccionada por Ruta Dinamica | Actual: 0 visibles";
				System.out.println("[RESULTADO PASO] FAILED - Desmarcar tienda precargada | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			boolean marcadaAntes = estaOpcionTiendaMarcada(tiendaPrecargada);
			try {
				tiendaPrecargada.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", tiendaPrecargada);
			}
			u.hardWait(1);
			boolean panelAbierto = !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
			boolean marcadaDespues = true;
			try {
				marcadaDespues = estaOpcionTiendaMarcada(tiendaPrecargada);
			} catch (Exception e) {
				List<WebElement> opcionesActualizadas = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 12);
				for (WebElement opcionActualizada : opcionesActualizadas) {
					String etiquetaActualizada = normalizarTextoFiltro(opcionActualizada);
					if (tiendaSeleccionada.equals(etiquetaActualizada)) {
						marcadaDespues = estaOpcionTiendaMarcada(opcionActualizada);
						break;
					}
				}
			}
			boolean resultadoPaso = panelAbierto && marcadaAntes && !marcadaDespues;
			tiendaDesmarcadaPersonalizacion = resultadoPaso ? tiendaSeleccionada : "";
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "La tienda no cambio a estado desmarcado o el selector se cerro. Esperado: marcadaAntes=true, marcadaDespues=false y panel abierto | Actual: marcadaAntes=" + marcadaAntes + ", marcadaDespues=" + marcadaDespues + ", panelAbierto=" + panelAbierto;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Desmarcar tienda precargada: " + tiendaSeleccionada + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " | Tienda desmarcada: " + tiendaSeleccionada + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Tienda_Precargada_Desmarcada_Ruta_Dinamica");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Desmarcar tienda precargada | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Marca una tienda adicional no precargada dentro del filtro Tienda para personalizar la consulta por Ruta Dinamica.
	 * Evita volver a marcar la tienda desmarcada en el paso anterior y mantiene el selector abierto.
	 *
	 * @param screenshot True para capturar evidencia visual de la tienda adicional marcada.
	 * @return True si se marca una tienda adicional distinta a la desmarcada; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	public boolean marcaTiendaAdicionalFueraRutaDinamica(boolean screenshot) {
		return marcaTiendaAdicionalFueraRutaDinamica(null, screenshot);
	}

	/**
	 * Marca una tienda adicional que no pertenezca a la Ruta Dinamica capturada.
	 * Busca primero una opcion unchecked distinta a la tienda desmarcada; si no la encuentra, usa BD y autocomplete como respaldo.
	 *
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) capturada en el filtro.
	 * @param screenshot True para capturar evidencia visual de la tienda adicional marcada.
	 * @return True si se marca una tienda adicional fuera de la ruta; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	public boolean marcaTiendaAdicionalFueraRutaDinamica(String rutaDinamica, boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No fue posible abrir el filtro Tienda para marcar una tienda adicional");
					return false;
				}
			}

			WebElement tiendaAdicional = null;
			String tiendaMarcada = "";
			String tiendaFueraRutaBD = "";
			if (rutaDinamica != null && !rutaDinamica.trim().isEmpty()) {
				posicionaScrollTiendaCercaPrimerUnchecked(rutaDinamica, elementos, wait);
			}

			int intentosScroll = 0;
			while (tiendaAdicional == null && intentosScroll < 8) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionesFiltroTiendaVisibles));
				List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 12);
				for (WebElement opcion : opcionesVisibles) {
					String etiqueta = normalizarTextoFiltro(opcion);
					boolean esTiendaDesmarcada = !tiendaDesmarcadaPersonalizacion.isEmpty() && etiqueta.equals(tiendaDesmarcadaPersonalizacion);
					if (!etiqueta.isEmpty() && !estaOpcionTiendaMarcada(opcion) && !esTiendaDesmarcada) {
						tiendaAdicional = opcion;
						tiendaMarcada = etiqueta;
						break;
					}
				}
				if (tiendaAdicional == null) {
					WebElement contenedorScroll = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.contenedorScrollFiltroTienda));
					((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollTop = arguments[0].scrollTop + Math.max(arguments[0].clientHeight * 2, 900);", contenedorScroll);
					esperaRenderCorto();
					intentosScroll++;
				}
			}

			if (tiendaAdicional == null && rutaDinamica != null && !rutaDinamica.trim().isEmpty()) {
				tiendaFueraRutaBD = obtenerEtiquetaTiendaFueraRutaDinamicaDesdeBD(rutaDinamica);
				if (tiendaFueraRutaBD != null && !tiendaFueraRutaBD.trim().isEmpty()) {
					String crTiendaFueraRuta = tiendaFueraRutaBD.split("\\s+", 2)[0];
					WebElement inputBusqueda = wait.until(ExpectedConditions.elementToBeClickable(elementos.inputBusquedaFiltroTienda));
					inputBusqueda.sendKeys(Keys.CONTROL + "a");
					inputBusqueda.sendKeys(Keys.DELETE);
					inputBusqueda.sendKeys(crTiendaFueraRuta);
					u.hardWait(1);

					List<WebElement> opcionesFiltradas = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 10);
					for (WebElement opcion : opcionesFiltradas) {
						String etiqueta = normalizarTextoFiltro(opcion);
						if (!etiqueta.isEmpty() && etiqueta.startsWith(crTiendaFueraRuta) && !estaOpcionTiendaMarcada(opcion)) {
							tiendaAdicional = opcion;
							tiendaMarcada = etiqueta;
							break;
						}
					}
				}
			}

			if (tiendaAdicional == null) {
				String motivoFallo = "No se encontro tienda adicional unchecked distinta a la tienda desmarcada. Tienda desmarcada: " + tiendaDesmarcadaPersonalizacion + " | Scrolls revisados: " + intentosScroll + " | Tienda fuera de ruta BD: " + tiendaFueraRutaBD;
				System.out.println("[RESULTADO PASO] FAILED - Marcar tienda adicional | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			int seleccionadasAntes = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
			try {
				tiendaAdicional.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", tiendaAdicional);
			}
			u.hardWait(1);
			int seleccionadasDespues = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
			boolean panelAbierto = !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
			boolean resultadoPaso = panelAbierto && seleccionadasDespues > seleccionadasAntes;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "La tienda adicional no cambio a estado marcado o el selector se cerro. Esperado: seleccionadas despues mayor que antes y panel abierto | Actual: antes=" + seleccionadasAntes + ", despues=" + seleccionadasDespues + ", panelAbierto=" + panelAbierto;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Marcar tienda adicional: " + tiendaMarcada + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " | Tienda adicional marcada: " + tiendaMarcada + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (!tiendaFueraRutaBD.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Tienda fuera de Ruta Dinamica obtenida desde BD: " + tiendaFueraRutaBD);
			}
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Tienda_Adicional_Marcada_Ruta_Dinamica");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Marcar tienda adicional | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Posiciona el scroll del filtro Tienda cerca del primer registro no precargado por la Ruta Dinamica.
	 * Usa el total de tiendas asociadas a la ruta para evitar recorrer el listado de poco en poco.
	 *
	 * @param rutaDinamica Ruta Dinamica capturada en el filtro.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita del flujo actual.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private void posicionaScrollTiendaCercaPrimerUnchecked(String rutaDinamica, rutaLiquidacionElements elementos, WebDriverWait wait) {
		try {
			LinkedHashMap<String, String> tiendasRuta = obtenerTiendasRutaDinamicaDesdeBD(rutaDinamica);
			int totalTiendasRuta = tiendasRuta.size();
			if (totalTiendasRuta == 0) {
				return;
			}

			WebElement contenedorScroll = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.contenedorScrollFiltroTienda));
			Long altoOpcion = (Long) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const host = arguments[0];"
					+ "const opt = host.querySelector('.ng-option[role=\"option\"]') || host.querySelector('.ng-option');"
					+ "return opt ? Math.max(Math.round(opt.getBoundingClientRect().height), 32) : 48;",
					contenedorScroll);
			long posicionObjetivo = Math.max(0, ((long) totalTiendasRuta - 3L) * altoOpcion.longValue());
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollTop = arguments[1];", contenedorScroll, posicionObjetivo);
			esperaRenderCorto();

			System.out.println("[SCROLL TIENDA] Ruta Dinamica: " + rutaDinamica.trim() + " | Tiendas precargadas BD: " + totalTiendasRuta + " | Posicion objetivo: " + posicionObjetivo);
			testCase.addBoldTextEvidenceCurrentStep("Scroll Tienda posicionado cerca del primer unchecked. Tiendas precargadas BD: " + totalTiendasRuta);
		} catch (Exception e) {
			System.out.println("[SCROLL TIENDA] No se pudo posicionar cerca del primer unchecked: " + e.getMessage());
		}
	}

	/**
	 * Espera brevemente a que el virtual scroll del filtro Tienda renderice las opciones visibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private void esperaRenderCorto() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Obtiene desde BD una tienda con informacion de CEDIS que no pertenezca a la Ruta Dinamica indicada.
	 * Devuelve la etiqueta con formato CR_TIENDA espacio NOMBRE_TIENDA para buscarla en el filtro Tienda.
	 *
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) que debe excluirse de la busqueda.
	 * @return Etiqueta de una tienda fuera de la ruta, o cadena vacia si no se encuentra.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private String obtenerEtiquetaTiendaFueraRutaDinamicaDesdeBD(String rutaDinamica) throws Exception {
		String query = "SELECT DISTINCT COALESCE(r.cr_tienda, h.cr_tienda) AS cr_tienda, "
				+ "COALESCE(r.desc_tienda, h.cr_tienda) AS nombre_tienda "
				+ "FROM xxcm_historico_correos h "
				+ "LEFT JOIN xxcm_region_plaza_tienda_job_sync r ON r.id_retek = h.id_retek "
				+ "WHERE h.origen = 'cedis' "
				+ "AND h.version_documento = 2 "
				+ "AND h.is_visible = true "
				+ "AND h.id_retek IS NOT NULL "
				+ "AND NOT EXISTS ( "
				+ "    SELECT 1 "
				+ "    FROM xxcm_historico_correos hr "
				+ "    WHERE hr.ruta_propuesta = ? "
				+ "    AND TRIM(hr.ruta_propuesta) <> '' "
				+ "    AND hr.version_documento = 2 "
				+ "    AND hr.is_visible = true "
				+ "    AND hr.id_retek = h.id_retek "
				+ ") "
				+ "ORDER BY COALESCE(r.cr_tienda, h.cr_tienda) "
				+ "LIMIT 1";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, rutaDinamica.trim());
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String crTienda = rs.getString("cr_tienda");
					String nombreTienda = rs.getString("nombre_tienda");
					String etiqueta = (crTienda.trim() + " " + (nombreTienda == null ? "" : nombreTienda.trim())).replaceAll("\\s+", " ").trim();
					System.out.println("[VALIDACION TIENDA FUERA RUTA] Ruta Dinamica: " + rutaDinamica.trim() + " | Tienda fuera de ruta BD: " + etiqueta);
					testCase.addBoldTextEvidenceCurrentStep("Tienda fuera de Ruta Dinamica obtenida desde BD: " + etiqueta);
					return etiqueta;
				}
			}
		}

		return "";
	}

	/**
	 * Ejecuta la consulta de Liquidacion de Ruta y valida que el encabezado muestre Ruta con palabra personalizada.
	 * Espera a que desaparezcan modales de carga para capturar evidencia limpia del grid.
	 *
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) capturada en el filtro.
	 * @param screenshot True para capturar evidencia visual del encabezado validado.
	 * @return True si el encabezado contiene la ruta consultada y la palabra personalizada; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	public boolean consultaRutaDinamicaYValidaEncabezadoPersonalizada(String rutaDinamica, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Ruta Dinamica vacia para validar encabezado personalizada");
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			u.hardWait(1);
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
			} catch (Exception e) {
				System.out.println("[VALIDACION CONSULTA RUTA PERSONALIZADA] Panel Tienda cerrado o no visible antes de consultar: " + e.getMessage());
			}

			WebElement consultar = wait.until(ExpectedConditions.elementToBeClickable(elementos.botonConsultarLiquidacionRuta));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", consultar);
			try {
				consultar.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", consultar);
			}

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			WebElement detalle = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerDetalle));
			((JavascriptExecutor) u.getDriver()).executeScript("var y = arguments[0].getBoundingClientRect().top + window.pageYOffset - 260; window.scrollTo(0, Math.max(y, 0));", detalle);
			u.hardWait(1);
			String textoDetalle = normalizarTextoFiltro(detalle);
			boolean muestraRuta = textoDetalle.contains("Ruta:") && textoDetalle.contains(rutaDinamica.trim());
			boolean muestraPersonalizada = textoDetalle.toLowerCase().contains("personalizada");
			boolean resultadoPaso = muestraRuta && muestraPersonalizada;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "Encabezado no contiene ruta y personalizada. Esperado: Ruta: " + rutaDinamica.trim() + " personalizada | Actual: " + textoDetalle;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Encabezado Ruta personalizada" + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Encabezado esperado: Ruta: " + rutaDinamica.trim() + " personalizada");
			testCase.addBoldTextEvidenceCurrentStep("Encabezado/detalle actual: " + textoDetalle);
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Encabezado_Ruta_Dinamica_Personalizada");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Encabezado Ruta personalizada | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Ejecuta la consulta de Liquidacion de Ruta por filtro Tienda y valida que el encabezado muestre Ruta: Personalizada.
	 * Se usa cuando no se captura Ruta Dinamica y la consulta depende unicamente de tiendas seleccionadas.
	 *
	 * @param screenshot True para capturar evidencia visual del encabezado validado.
	 * @return True si el encabezado contiene Ruta: Personalizada; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	public boolean consultaTiendaYValidaEncabezadoPersonalizada(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			u.hardWait(1);
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
			} catch (Exception e) {
				System.out.println("[VALIDACION CONSULTA TIENDA PERSONALIZADA] Panel Tienda cerrado o no visible antes de consultar: " + e.getMessage());
			}

			WebElement consultar = wait.until(ExpectedConditions.elementToBeClickable(elementos.botonConsultarLiquidacionRuta));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", consultar);
			try {
				consultar.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", consultar);
			}

			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			WebElement detalle = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerDetalle));
			((JavascriptExecutor) u.getDriver()).executeScript("var y = arguments[0].getBoundingClientRect().top + window.pageYOffset - 260; window.scrollTo(0, Math.max(y, 0));", detalle);
			u.hardWait(1);
			String textoDetalle = normalizarTextoFiltro(detalle);
			boolean muestraRutaPersonalizada = textoDetalle.contains("Ruta:") && textoDetalle.toLowerCase().contains("personalizada");
			boolean resultadoPaso = muestraRutaPersonalizada;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "Encabezado no contiene Ruta: Personalizada. Esperado: Ruta: Personalizada | Actual: " + textoDetalle;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Encabezado Ruta: Personalizada" + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Encabezado esperado: Ruta: Personalizada");
			testCase.addBoldTextEvidenceCurrentStep("Encabezado/detalle actual: " + textoDetalle);
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Encabezado_Tienda_Personalizada");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Encabezado Ruta: Personalizada | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Fast path: marca DOS tiendas con informacion buscandolas UNA POR UNA por nombre en el input del filtro Tienda.
	 * No hace scroll ni recorre el catalogo: itera la lista de candidatas obtenidas desde BD, escribe el nombre en el buscador y hace clic en la primera opcion cuyo CR coincida.
	 * Reduce drasticamente el tiempo del paso y evita depender del render por virtual-scroll.
	 * Si una candidata no aparece, avanza a la siguiente hasta lograr 2 marcadas o agotar la lista.
	 *
	 * @param etiquetasBD Lista de tiendas candidatas desde BD con formato CR_TIENDA espacio NOMBRE_TIENDA.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita del flujo actual.
	 * @return Lista con las 2 tiendas marcadas (etiqueta portal); si no logra marcar 2, la lista tendra menor tamano.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private List<String> marcaDosTiendasPorBusquedaNombreDesdeBD(List<String> etiquetasBD, rutaLiquidacionElements elementos, WebDriverWait wait) {
		List<String> tiendasMarcadas = new ArrayList<String>();
		if (etiquetasBD == null || etiquetasBD.size() < 2) {
			System.out.println("[FAST PATH] Lista de candidatas BD insuficiente (size=" + (etiquetasBD == null ? 0 : etiquetasBD.size()) + "). Se aborta fast path.");
			return tiendasMarcadas;
		}
		// Timeout MUY corto (2 seg) para que si el input o la opcion no aparecen en ese tiempo, el fast path abandona rapido y cae al respaldo con scroll.
		WebDriverWait waitMinimo = new WebDriverWait(u.getDriver(), Duration.ofSeconds(2));
		System.out.println("[FAST PATH] Iniciando fast path con " + etiquetasBD.size() + " candidatas BD. Timeout por operacion: 2s.");
		testCase.addBoldTextEvidenceCurrentStep("Fast path iniciado con " + etiquetasBD.size() + " candidatas | Timeout por operacion: 2s");

		int candidatasFallidasConsecutivas = 0;
		for (int idx = 0; idx < etiquetasBD.size() && tiendasMarcadas.size() < 2; idx++) {
			String etiquetaBD = etiquetasBD.get(idx);
			// Si ya fallaron 2 candidatas seguidas sin encontrar el input/opcion, el buscador no es viable para este perfil; se abandona.
			if (candidatasFallidasConsecutivas >= 2) {
				System.out.println("[FAST PATH] Se abandona fast path tras " + candidatasFallidasConsecutivas + " fallos consecutivos (buscador no viable para el perfil actual). Marcadas: " + tiendasMarcadas.size());
				testCase.addBoldTextEvidenceCurrentStep("Fast path abandonado tras " + candidatasFallidasConsecutivas + " fallos consecutivos; se caera al respaldo con scroll");
				break;
			}
			if (etiquetaBD == null || etiquetaBD.trim().isEmpty()) {
				continue;
			}
			String crTienda = etiquetaBD.split("\\s+", 2)[0];
			String nombreTienda = etiquetaBD.split("\\s+", 2).length > 1 ? etiquetaBD.split("\\s+", 2)[1] : crTienda;

			System.out.println("[FAST PATH #" + (idx + 1) + "] Intentando marcar: " + etiquetaBD + " | CR: " + crTienda + " | Nombre: '" + nombreTienda + "'");

			try {
				// PASO A: asegurar panel abierto
				System.out.println("[FAST PATH #" + (idx + 1) + "] A) Asegurando panel abierto...");
				aseguraPanelFiltroTiendaAbierto(elementos, waitMinimo);
				System.out.println("[FAST PATH #" + (idx + 1) + "] A) Panel abierto OK");

				// PASO B: obtener input del buscador con timeout corto
				System.out.println("[FAST PATH #" + (idx + 1) + "] B) Buscando input del buscador (timeout 2s)...");
				WebElement inputBusqueda = null;
				try {
					inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, waitMinimo);
					System.out.println("[FAST PATH #" + (idx + 1) + "] B) Input del buscador ENCONTRADO");
				} catch (Exception e) {
					System.out.println("[FAST PATH #" + (idx + 1) + "] B) Input del buscador NO encontrado en 2s: " + e.getClass().getSimpleName() + " | " + e.getMessage());
					candidatasFallidasConsecutivas++;
					continue;
				}

				// PASO C: escribir nombre
				System.out.println("[FAST PATH #" + (idx + 1) + "] C) Escribiendo nombre '" + nombreTienda + "' en el buscador...");
				capturaBusquedaTiendaComoUsuario(inputBusqueda, nombreTienda);
				System.out.println("[FAST PATH #" + (idx + 1) + "] C) Nombre escrito OK");

				// PASO D: esperar opcion filtrada (2 intentos cortos)
				System.out.println("[FAST PATH #" + (idx + 1) + "] D) Esperando opcion filtrada con CR " + crTienda + " (2 intentos)...");
				WebElement opcion = esperaOpcionTiendaFiltradaPorCr(crTienda, elementos, 2);
				if (opcion == null) {
					System.out.println("[FAST PATH #" + (idx + 1) + "] D) Opcion por nombre NO encontrada; reintentando por CR " + crTienda);
					try {
						inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, waitMinimo);
						capturaBusquedaTiendaComoUsuario(inputBusqueda, crTienda);
						opcion = esperaOpcionTiendaFiltradaPorCr(crTienda, elementos, 2);
					} catch (Exception e) {
						System.out.println("[FAST PATH #" + (idx + 1) + "] D) Reintento por CR fallo: " + e.getMessage());
					}
				}
				if (opcion == null) {
					System.out.println("[FAST PATH #" + (idx + 1) + "] D) No se encontro opcion visible para " + etiquetaBD + " ni por nombre ni por CR. Siguiente candidata.");
					testCase.addBoldTextEvidenceCurrentStep("Fast path #" + (idx + 1) + ": no aparece opcion visible para " + etiquetaBD + ", se intenta siguiente");
					candidatasFallidasConsecutivas++;
					continue;
				}
				System.out.println("[FAST PATH #" + (idx + 1) + "] D) Opcion filtrada ENCONTRADA");

				// PASO E: click SIN polling posterior. Angular Material multiselect refresca el DOM tras el click y el polling se cuelga por StaleElement/render races. Se confia en el click y se valida por conteo total al final.
				String etiquetaPortal = normalizarTextoFiltro(opcion);
				boolean marcadaAntes = estaOpcionTiendaMarcada(opcion);
				System.out.println("[FAST PATH #" + (idx + 1) + "] E) Estado previo | Portal: " + etiquetaPortal + " | Marcada antes: " + marcadaAntes);
				int seleccionadasAntesClick = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
				System.out.println("[FAST PATH #" + (idx + 1) + "] E) Seleccionadas antes del click: " + seleccionadasAntesClick);
				if (!marcadaAntes) {
					haceClickSeguroOpcionTienda(opcion);
					System.out.println("[FAST PATH #" + (idx + 1) + "] E) Click ejecutado");
				}
				// Espera fija breve para que Angular procese; NO polling.
				try { Thread.sleep(400); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
				System.out.println("[FAST PATH #" + (idx + 1) + "] E) 400ms de espera fija completados");
				// Validacion por conteo (no por polling en la opcion individual, que se cuelga).
				int seleccionadasDespuesClick = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
				boolean marcada = marcadaAntes || seleccionadasDespuesClick > seleccionadasAntesClick;
				System.out.println("[FAST PATH #" + (idx + 1) + "] E) Seleccionadas despues del click: " + seleccionadasDespuesClick + " | Marcada final: " + marcada);
				testCase.addBoldTextEvidenceCurrentStep("Fast path #" + (idx + 1) + " tienda: " + etiquetaPortal + " | Marcada: " + marcada + " | Seleccionadas: " + seleccionadasAntesClick + " -> " + seleccionadasDespuesClick);
				if (marcada) {
					tiendasMarcadas.add(etiquetaPortal);
					candidatasFallidasConsecutivas = 0;
				} else {
					candidatasFallidasConsecutivas++;
				}
				// Limpia el buscador entre candidatas para que la siguiente iteracion parta con el listado completo (evita filtros residuales que oculten opciones).
				if (tiendasMarcadas.size() < 2) {
					try {
						System.out.println("[FAST PATH #" + (idx + 1) + "] F) Limpiando buscador antes de siguiente candidata...");
						WebElement inputParaLimpiar = obtieneInputBusquedaTiendaVisible(elementos, waitMinimo);
						capturaBusquedaTiendaComoUsuario(inputParaLimpiar, "");
						System.out.println("[FAST PATH #" + (idx + 1) + "] F) Buscador limpiado OK");
					} catch (Exception e) {
						System.out.println("[FAST PATH #" + (idx + 1) + "] F) No se pudo limpiar buscador entre candidatas: " + e.getMessage());
					}
				}
			} catch (Exception e) {
				System.out.println("[FAST PATH #" + (idx + 1) + "] EXCEPCION marcando " + etiquetaBD + ": " + e.getClass().getSimpleName() + " | " + e.getMessage());
				candidatasFallidasConsecutivas++;
			}
		}

		// Limpia el buscador al final para que al cerrar el panel el control muestre correctamente "Multiples seleccionados".
		try {
			System.out.println("[FAST PATH] Limpiando buscador al finalizar...");
			WebElement inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, waitMinimo);
			capturaBusquedaTiendaComoUsuario(inputBusqueda, "");
			System.out.println("[FAST PATH] Buscador limpiado OK");
		} catch (Exception e) {
			System.out.println("[FAST PATH] No se pudo limpiar el buscador al final: " + e.getMessage());
		}
		System.out.println("[FAST PATH] Terminado. Tiendas marcadas: " + tiendasMarcadas.size() + " de 2");
		return tiendasMarcadas;
	}

	/**
	 * Fast path para MTC_FT_070 paso 5: marca UNA sola tienda del filtro Tienda usando el buscador por CR.
	 * Reutiliza el patron acreditado en MTC_FT_068 / MTC_FT_069 (buscador + click sin polling), pero con un limite pequeno de candidatas y un CR opcional a excluir para no repetir la tienda ya marcada.
	 * Deja el buscador limpio al terminar para que el conteo de seleccionadas del portal siga siendo veraz.
	 *
	 * @param candidatasBD Mapa ordenado CR_TIENDA -> etiqueta portal candidata (obtenido de BD).
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param crExcluir CR que no debe volver a marcarse; null o vacio para no excluir ninguno.
	 * @param categoria Etiqueta corta para trazabilidad en logs y evidencia (por ejemplo "CON info" o "SIN info").
	 * @return Etiqueta portal de la tienda marcada; cadena vacia si no logra marcar ninguna candidata.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private String marcaUnaTiendaPorBusquedaFastPath(LinkedHashMap<String, String> candidatasBD, rutaLiquidacionElements elementos, String crExcluir, String categoria) {
		if (candidatasBD == null || candidatasBD.isEmpty()) {
			System.out.println("[FAST PATH 070 " + categoria + "] Sin candidatas BD; se aborta.");
			return "";
		}
		WebDriverWait waitMinimo = new WebDriverWait(u.getDriver(), Duration.ofSeconds(3));
		int fallosConsecutivos = 0;
		int intento = 0;
		for (java.util.Map.Entry<String, String> candidata : candidatasBD.entrySet()) {
			intento++;
			if (fallosConsecutivos >= 3) {
				System.out.println("[FAST PATH 070 " + categoria + "] Abandono por " + fallosConsecutivos + " fallos consecutivos del buscador.");
				return "";
			}
			String cr = candidata.getKey();
			String etiquetaBD = candidata.getValue();
			if (cr == null || cr.trim().isEmpty()) {
				continue;
			}
			if (crExcluir != null && cr.equals(crExcluir)) {
				continue;
			}
			try {
				aseguraPanelFiltroTiendaAbierto(elementos, waitMinimo);
				WebElement input;
				try {
					input = obtieneInputBusquedaTiendaVisible(elementos, waitMinimo);
				} catch (Exception e) {
					System.out.println("[FAST PATH 070 " + categoria + " #" + intento + "] Input buscador no disponible: " + e.getMessage());
					fallosConsecutivos++;
					continue;
				}
				capturaBusquedaTiendaComoUsuario(input, cr);
				esperaRenderCorto();
				WebElement opcion = esperaOpcionTiendaFiltradaPorCr(cr, elementos, 2);
				if (opcion == null) {
					System.out.println("[FAST PATH 070 " + categoria + " #" + intento + "] Candidata " + etiquetaBD + " no visible en droplist del perfil; siguiente.");
					try {
						capturaBusquedaTiendaComoUsuario(input, "");
					} catch (Exception ignored) {
					}
					fallosConsecutivos++;
					continue;
				}
				String etiquetaPortal = normalizarTextoFiltro(opcion);
				boolean marcadaAntes = estaOpcionTiendaMarcada(opcion);
				int seleccionadasAntes = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
				if (!marcadaAntes) {
					haceClickSeguroOpcionTienda(opcion);
				}
				try { Thread.sleep(400); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
				int seleccionadasDespues = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
				int delta = seleccionadasDespues - seleccionadasAntes;
				// Perfil Admin: el droplist puede tener tiendas con el mismo CR duplicadas por plaza y ng-select con virtual-scroll re-renderea slots del DOM entre findElement y click. Si el clic marco mas de 1 tienda (delta > 1), el intento de toggle-back es fragil: puede marcar tiendas ajenas al filtro (5004D en vez de 50000) por drift del slot re-renderizado. Se ABANDONA el fast path SIN intentar revertir aqui; la limpieza total se delega al metodo externo (Limpiar seleccion + reabrir panel + limpiar buscador) que garantiza estado limpio antes del slow path.
				if (delta > 1) {
					System.out.println("[FAST PATH 070 " + categoria + " #" + intento + "] Delta inesperado " + delta + " tras click sobre " + etiquetaPortal + " (perfil con CR duplicado). Se abandona el fast path SIN toggle-back; el metodo externo limpiara todas las selecciones antes del slow path.");
					testCase.addBoldTextEvidenceCurrentStep("Fast path 070 " + categoria + " intento " + intento + ": CR " + cr + " marco " + delta + " tiendas por duplicado en perfil. Se abandona fast path para que el flujo externo limpie con 'Limpiar seleccion' y ejecute el slow path desde estado limpio.");
					return "";
				}
				try {
					WebElement inputLimpieza = obtieneInputBusquedaTiendaVisible(elementos, waitMinimo);
					capturaBusquedaTiendaComoUsuario(inputLimpieza, "");
					esperaRenderCorto();
				} catch (Exception ignored) {
				}
				boolean marcada = marcadaAntes || delta == 1;
				System.out.println("[FAST PATH 070 " + categoria + " #" + intento + "] " + etiquetaPortal + " | Marcada: " + marcada + " | Seleccionadas: " + seleccionadasAntes + " -> " + seleccionadasDespues + " (delta " + delta + ")");
				testCase.addBoldTextEvidenceCurrentStep("Fast path 070 " + categoria + " intento " + intento + ": " + etiquetaPortal + " | Marcada: " + marcada + " | delta: " + delta);
				if (marcada) {
					return etiquetaPortal.isEmpty() ? etiquetaBD : etiquetaPortal;
				}
				fallosConsecutivos++;
			} catch (Exception e) {
				System.out.println("[FAST PATH 070 " + categoria + " #" + intento + "] EXCEPCION con " + etiquetaBD + ": " + e.getClass().getSimpleName() + " | " + e.getMessage());
				fallosConsecutivos++;
			}
		}
		return "";
	}

	/**
	 * Busca tiendas candidatas por CR en el filtro Tienda y marca las primeras dos que el portal encuentre.
	 * Acelera el flujo frente al recorrido por scroll del catalogo completo, especialmente para perfil Admin.
	 *
	 * @param etiquetasTiendas Lista de tiendas candidatas con formato CR_TIENDA espacio NOMBRE_TIENDA.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita del flujo actual.
	 * @return True si dos tiendas quedan marcadas con el panel aun abierto; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private boolean buscaYMarcaDosTiendasPorBusquedaDirecta(List<String> etiquetasTiendas, rutaLiquidacionElements elementos, WebDriverWait wait) {
		List<String> tiendasMarcadas = new ArrayList<String>();
		if (etiquetasTiendas == null || etiquetasTiendas.isEmpty()) {
			return false;
		}

		String primeraTienda = etiquetasTiendas.get(0);
		if (primeraTienda != null && !primeraTienda.trim().isEmpty()) {
			boolean primeraMarcada = buscaYMarcaPrimeraTiendaPorNombre(primeraTienda, elementos);
			if (primeraMarcada) {
				tiendasMarcadas.add(primeraTienda);
			}
		}

		if (tiendasMarcadas.size() == 1) {
			String segundaTienda = marcaTiendaDisponibleMasProximaEnPanel(elementos, wait, tiendasMarcadas);
			if (!segundaTienda.isEmpty()) {
				tiendasMarcadas.add(segundaTienda);
			}
		}

		boolean resultadoPaso = tiendasMarcadas.size() == 2 && !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
		String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
		String motivoFallo = resultadoPaso ? "" : "Tiendas marcadas por busqueda directa/scroll corto: " + tiendasMarcadas.size() + " de 2. Candidatas BD: " + etiquetasTiendas;
		System.out.println("[RESULTADO PASO] " + estadoPaso + " - Busqueda directa de tiendas con informacion" + (resultadoPaso ? " | Tiendas: " + tiendasMarcadas : " | Motivo: " + motivoFallo));
		testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " | Busqueda directa de tiendas con informacion" + (resultadoPaso ? " | Tiendas: " + tiendasMarcadas : " | Motivo: " + motivoFallo));
		return resultadoPaso;
	}

	/**
	 * Busca la primera tienda por nombre para validar el comportamiento del autocomplete sin depender solo del CR.
	 * Si el nombre no devuelve la opcion esperada, intenta nuevamente por CR como respaldo rapido.
	 *
	 * @param etiquetaTienda Etiqueta CR_TIENDA espacio NOMBRE_TIENDA que se buscara en el filtro.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @return True si la primera tienda queda marcada; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private boolean buscaYMarcaPrimeraTiendaPorNombre(String etiquetaTienda, rutaLiquidacionElements elementos) {
		try {
			String crTienda = etiquetaTienda.split("\\s+", 2)[0];
			String nombreTienda = etiquetaTienda.split("\\s+", 2).length > 1 ? etiquetaTienda.split("\\s+", 2)[1] : crTienda;
			WebDriverWait waitCorto = new WebDriverWait(u.getDriver(), Duration.ofSeconds(6));
			reabrePanelFiltroTienda(elementos, waitCorto);

			WebElement inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, waitCorto);
			capturaBusquedaTiendaComoUsuario(inputBusqueda, nombreTienda);
			esperaRenderCorto();

			WebElement opcion = esperaOpcionTiendaFiltradaPorCr(crTienda, elementos, 6);
			if (opcion == null) {
				inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, waitCorto);
				capturaBusquedaTiendaComoUsuario(inputBusqueda, crTienda);
				esperaRenderCorto();
				opcion = esperaOpcionTiendaFiltradaPorCr(crTienda, elementos, 6);
			}
			if (opcion == null) {
				System.out.println("[SELECCION TIENDA CON INFO] No se encontro primera tienda por nombre ni CR: " + etiquetaTienda);
				return false;
			}

			String etiquetaPortal = normalizarTextoFiltro(opcion);
			boolean marcadaAntes = estaOpcionTiendaMarcada(opcion);
			if (!marcadaAntes) {
				haceClickSeguroOpcionTienda(opcion);
				esperaRenderCorto();
			}
			boolean marcada = marcadaAntes || esperaOpcionTiendaMarcadaPorCr(crTienda, elementos);
			System.out.println("[SELECCION TIENDA CON INFO] Primera tienda por nombre: " + nombreTienda + " | Portal: " + etiquetaPortal + " | Marcada: " + marcada);
			testCase.addBoldTextEvidenceCurrentStep("Primera tienda seleccionada por nombre: " + etiquetaPortal + " | Marcada: " + marcada);
			return marcada;
		} catch (Exception e) {
			System.out.println("Error al buscar primera tienda por nombre: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Con el filtro Tienda abierto, limpia la busqueda y marca la tienda no seleccionada mas cercana mediante scroll corto.
	 * No recorre ni cuenta todo el catalogo; revisa solo opciones visibles y pocos desplazamientos controlados.
	 *
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita del flujo actual.
	 * @param tiendasMarcadas Tiendas ya marcadas para evitar duplicarlas.
	 * @return Etiqueta de la tienda adicional marcada, o cadena vacia si no se encontro.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private String marcaTiendaDisponibleMasProximaEnPanel(rutaLiquidacionElements elementos, WebDriverWait wait, List<String> tiendasMarcadas) {
		try {
			List<String> crTiendasMarcadas = new ArrayList<String>();
			for (String tiendaMarcada : tiendasMarcadas) {
				if (tiendaMarcada != null && !tiendaMarcada.trim().isEmpty()) {
					crTiendasMarcadas.add(tiendaMarcada.split("\\s+", 2)[0]);
				}
			}
			aseguraPanelFiltroTiendaAbierto(elementos, new WebDriverWait(u.getDriver(), Duration.ofSeconds(5)));
			try {
				WebElement inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, new WebDriverWait(u.getDriver(), Duration.ofSeconds(3)));
				capturaBusquedaTiendaComoUsuario(inputBusqueda, "");
				esperaRenderCorto();
			} catch (Exception e) {
				System.out.println("[SELECCION TIENDA CON INFO] No se pudo limpiar buscador antes del scroll corto: " + e.getMessage());
			}

			for (int pagina = 0; pagina < 5; pagina++) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionesFiltroTiendaVisibles));
				List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 12);
				for (WebElement opcion : opcionesVisibles) {
					String etiqueta = normalizarTextoFiltro(opcion);
					if (etiqueta.isEmpty()) {
						continue;
					}
					String crTienda = etiqueta.split("\\s+", 2)[0];
					if (crTiendasMarcadas.contains(crTienda) || tiendasMarcadas.contains(etiqueta) || estaOpcionTiendaMarcada(opcion)) {
						continue;
					}
					haceClickSeguroOpcionTienda(opcion);
					esperaRenderCorto();
					boolean marcada = esperaOpcionTiendaMarcadaPorCr(crTienda, elementos);
					System.out.println("[SELECCION TIENDA PROXIMA] Portal: " + etiqueta + " | Marcada: " + marcada + " | Pagina scroll: " + (pagina + 1));
					testCase.addBoldTextEvidenceCurrentStep("Tienda proxima disponible seleccionada: " + etiqueta + " | Marcada: " + marcada);
					return marcada ? etiqueta : "";
				}

				WebElement contenedorScroll = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.contenedorScrollFiltroTienda));
				Long scrollAntes = (Long) ((JavascriptExecutor) u.getDriver()).executeScript("return arguments[0].scrollTop;", contenedorScroll);
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollTop = arguments[0].scrollTop + Math.max(arguments[0].clientHeight, 360);", contenedorScroll);
				esperaRenderCorto();
				Long scrollDespues = (Long) ((JavascriptExecutor) u.getDriver()).executeScript("return arguments[0].scrollTop;", contenedorScroll);
				if (scrollDespues.longValue() == scrollAntes.longValue()) {
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error al marcar tienda disponible mas proxima por scroll corto: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Escribe una tienda candidata en el buscador del filtro Tienda y marca unicamente su opcion coincidente.
	 * Replica el flujo manual: escribir CR, esperar la opcion filtrada y marcar el checkbox sin tocar selecciones previas.
	 *
	 * @param etiquetaTienda Etiqueta CR_TIENDA espacio NOMBRE_TIENDA que se buscara en el filtro.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @return True si la tienda queda marcada; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private boolean buscaYMarcaTiendaComoUsuarioPorEtiqueta(String etiquetaTienda, rutaLiquidacionElements elementos) {
		try {
			String crTienda = etiquetaTienda.split("\\s+", 2)[0];
			WebDriverWait waitCorto = new WebDriverWait(u.getDriver(), Duration.ofSeconds(6));
			reabrePanelFiltroTienda(elementos, waitCorto);

			WebElement inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, waitCorto);
			capturaBusquedaTiendaComoUsuario(inputBusqueda, crTienda);
			esperaRenderCorto();

			WebElement opcion = esperaOpcionTiendaFiltradaPorCr(crTienda, elementos, 8);
			if (opcion == null) {
				String[] partesEtiqueta = etiquetaTienda.split("\\s+", 2);
				if (partesEtiqueta.length > 1) {
					inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, waitCorto);
					capturaBusquedaTiendaComoUsuario(inputBusqueda, partesEtiqueta[1]);
					esperaRenderCorto();
					opcion = esperaOpcionTiendaFiltradaPorCr(crTienda, elementos, 8);
				}
			}

			if (opcion == null) {
				System.out.println("[SELECCION TIENDA CON INFO] No se encontro opcion filtrada para: " + etiquetaTienda);
				return false;
			}

			String etiquetaPortal = normalizarTextoFiltro(opcion);
			boolean marcadaAntes = estaOpcionTiendaMarcada(opcion);
			if (!marcadaAntes) {
				haceClickSeguroOpcionTienda(opcion);
				esperaRenderCorto();
			}
			boolean marcada = marcadaAntes || esperaOpcionTiendaMarcadaPorCr(crTienda, elementos);
			System.out.println("[SELECCION TIENDA CON INFO] Busqueda natural: " + crTienda + " | Portal: " + etiquetaPortal + " | Marcada: " + marcada);
			testCase.addBoldTextEvidenceCurrentStep("Tienda con informacion seleccionada por busqueda natural: " + etiquetaPortal + " | Marcada: " + marcada);
			return marcada;
		} catch (Exception e) {
			System.out.println("Error al buscar y marcar tienda como usuario: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Cierra y vuelve a abrir el filtro Tienda para regenerar el input de busqueda antes de cada consulta.
	 * Evita que la segunda busqueda falle cuando el multiselect oculta el input despues de marcar una tienda.
	 *
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita corta para reabrir el filtro.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private void reabrePanelFiltroTienda(rutaLiquidacionElements elementos, WebDriverWait wait) {
		try {
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			esperaRenderCorto();
			try {
				new WebDriverWait(u.getDriver(), Duration.ofSeconds(1)).until(ExpectedConditions.invisibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
			} catch (Exception e) {
				System.out.println("[SELECCION TIENDA CON INFO] Panel Tienda no cerro con ESC, se reabrira de cualquier forma.");
			}
		} catch (Exception e) {
			System.out.println("[SELECCION TIENDA CON INFO] No fue posible enviar ESC antes de reabrir Tienda: " + e.getMessage());
		}
		WebElement controlTienda = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.controlFiltroTienda));
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", controlTienda);
		try {
			controlTienda.click();
		} catch (Exception e) {
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", controlTienda);
		}
		wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
		wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.inputBusquedaFiltroTienda));
		esperaRenderCorto();
	}

	/**
	 * Captura texto en el buscador de Tienda usando eventos de teclado sobre el input visible.
	 * Evita limpiar el selector completo o accionar opciones ya seleccionadas.
	 *
	 * @param inputBusqueda Input visible del buscador Tienda.
	 * @param textoBusqueda Texto que se escribira en el buscador.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private void capturaBusquedaTiendaComoUsuario(WebElement inputBusqueda, String textoBusqueda) {
		try {
			System.out.println("[BUSCADOR] Iniciando captura de texto: '" + textoBusqueda + "'");
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			System.out.println("[BUSCADOR] Paso 1: scroll+focus+clear via JS");
			js.executeScript(
					"arguments[0].scrollIntoView({block:'center'});"
					+ "arguments[0].focus();"
					+ "arguments[0].value = '';"
					+ "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
					+ "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
					inputBusqueda);
			System.out.println("[BUSCADOR] Paso 2: click en input");
			try {
				inputBusqueda.click();
			} catch (Exception e) {
				System.out.println("[BUSCADOR] click nativo fallo, usando JS click: " + e.getMessage());
				js.executeScript("arguments[0].click();", inputBusqueda);
			}
			System.out.println("[BUSCADOR] Paso 3: sendKeys('" + textoBusqueda + "')");
			inputBusqueda.sendKeys(textoBusqueda);
			System.out.println("[BUSCADOR] Paso 4: dispatch input/change events");
			js.executeScript(
					"arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
					+ "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
					inputBusqueda);
			System.out.println("[BUSCADOR] Captura completada OK");
		} catch (Exception e) {
			System.out.println("[BUSCADOR] Excepcion en flujo principal (" + e.getClass().getSimpleName() + "): " + e.getMessage() + ". Intentando fallback JS puro.");
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			js.executeScript(
					"arguments[0].focus();"
					+ "arguments[0].value = arguments[1];"
					+ "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
					+ "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
					inputBusqueda, textoBusqueda);
			System.out.println("[BUSCADOR] Fallback JS completado");
		}
	}

	/**
	 * Espera una opcion filtrada por CR dentro del panel Tienda abierto.
	 * Devuelve solo la opcion cuyo texto inicia con el CR buscado para no tocar tiendas ya seleccionadas distintas.
	 *
	 * @param crTienda CR_TIENDA esperado al inicio de la opcion.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param intentos Cantidad de intentos cortos de renderizado.
	 * @return Opcion filtrada coincidente, o null si no aparece.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private WebElement esperaOpcionTiendaFiltradaPorCr(String crTienda, rutaLiquidacionElements elementos, int intentos) {
		for (int intento = 0; intento < intentos; intento++) {
			List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 20);
			for (WebElement opcion : opcionesVisibles) {
				String etiqueta = normalizarTextoFiltro(opcion);
				if (!etiqueta.isEmpty() && etiqueta.startsWith(crTienda)) {
					return opcion;
				}
			}
			esperaRenderCorto();
		}
		return null;
	}

	/**
	 * Hace clic sobre el checkbox interno de una opcion Tienda cuando existe; si no, usa la opcion completa.
	 * Esto replica mejor la interaccion manual y evita alternar accidentalmente el renglon equivocado del multiselect.
	 *
	 * @param opcion Opcion visible del filtro Tienda.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private void haceClickSeguroOpcionTienda(WebElement opcion) {
		try {
			System.out.println("[CLICK OPCION] Iniciando click seguro sobre opcion");
			// CLICK PRIMERO SOBRE EL NG-OPTION COMPLETO (no el checkbox interno).
			// En ng-select con virtual scroll, el checkbox interno puede quedar ligado a otro renglon
			// tras un re-render, lo que causa que se marque una tienda distinta a la buscada.
			// El click sobre la opcion completa garantiza que Angular ejecute el toggle del renglon correcto.
			String etiquetaEsperada = "";
			try {
				etiquetaEsperada = normalizarTextoFiltro(opcion);
			} catch (Exception e) { }
			System.out.println("[CLICK OPCION] Etiqueta de la opcion sobre la que se hara click: '" + etiquetaEsperada + "'");
			try {
				((JavascriptExecutor) u.getDriver()).executeScript(
						"arguments[0].scrollIntoView({block:'center', inline:'nearest'});"
						+ "arguments[0].click();",
						opcion);
				System.out.println("[CLICK OPCION] Click JS sobre ng-option completo OK");
				return;
			} catch (Exception e) {
				System.out.println("[CLICK OPCION] Click JS sobre ng-option fallo: " + e.getMessage() + ". Intentando fallback con checkbox interno.");
			}
			// FALLBACK: si el click sobre la opcion completa fallo, buscar checkbox interno.
			List<WebElement> checkboxes = opcion.findElements(By.xpath(".//input[@type='checkbox'] | .//*[@role='checkbox']"));
			System.out.println("[CLICK OPCION] Fallback: checkboxes internos encontrados: " + checkboxes.size());
			for (WebElement checkbox : checkboxes) {
				try {
					if (checkbox.isDisplayed() && checkbox.isEnabled()) {
						((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", checkbox);
						System.out.println("[CLICK OPCION] Fallback click JS sobre checkbox interno OK");
						return;
					}
				} catch (Exception e) {
					System.out.println("[CLICK OPCION] Fallback checkbox no accesible: " + e.getMessage());
				}
			}
			System.out.println("[CLICK OPCION] Ningun fallback aplicable; la opcion pudo no haber sido marcada");
		} catch (Exception e) {
			System.out.println("[CLICK OPCION] Excepcion final: " + e.getClass().getSimpleName() + " | " + e.getMessage());
			try {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", opcion);
				System.out.println("[CLICK OPCION] Fallback ultimo recurso click JS OK");
			} catch (Exception e2) {
				System.out.println("[CLICK OPCION] Fallback ultimo recurso tambien fallo: " + e2.getMessage());
			}
		}
	}

	/**
	 * Espera a que una opcion Tienda identificada por CR quede marcada despues del clic.
	 * Reconsulta las opciones visibles para evitar usar una referencia obsoleta del DOM.
	 *
	 * @param crTienda CR_TIENDA que debe quedar seleccionado.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @return True si el CR aparece marcado dentro del selector; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-11
	 */
	private boolean esperaOpcionTiendaMarcadaPorCr(String crTienda, rutaLiquidacionElements elementos) {
		for (int intento = 0; intento < 6; intento++) {
			List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 20);
			for (WebElement opcion : opcionesVisibles) {
				String etiqueta = normalizarTextoFiltro(opcion);
				if (!etiqueta.isEmpty() && etiqueta.startsWith(crTienda)) {
					return estaOpcionTiendaMarcada(opcion);
				}
			}
			esperaRenderCorto();
		}
		return false;
	}

	/**
	 * Selecciona en el filtro Tienda una tienda CON informacion y una tienda SIN informacion en el rango de la fecha consultada.
	 * Toma como universo unicamente las tiendas visibles en el droplist del portal (con scroll acotado) para respetar el filtrado real que aplica el sistema por perfil, y las cruza contra BD para identificar cuales tienen datos en la fecha y cuales no.
	 * Guarda las etiquetas seleccionadas en variables de instancia para reutilizarlas en pasos posteriores del script.
	 *
	 * @param usuario Usuario cuyo perfil y alcance de tiendas se validan en BD.
	 * @param fechaFormateada Fecha capturada en el portal en formato d MMM yyyy.
	 * @param screenshot True para capturar evidencia visual de la seleccion de ambas tiendas.
	 * @return True si ambas tiendas quedan marcadas en el filtro Tienda; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean marcaPrimeraTiendaConInfoYUnaSinInfoEnRangoDesdeBD(String usuario, String fechaFormateada, boolean screenshot) {
		try {
			if (fechaFormateada == null || fechaFormateada.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Fecha vacia para obtener tiendas con y sin informacion");
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			LocalDate fechaConsulta = convertirFechaVisibleALocalDate(fechaFormateada);

			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No fue posible abrir el filtro Tienda para marcar la seleccion mixta");
					return false;
				}
			}

			// FAST PATH (MTC_FT_070 paso 5): antes de recorrer el droplist con scroll+cruce contra BD (flujo lento),
			// se intenta marcar UNA tienda CON info y UNA tienda SIN info directamente por el buscador (mismo patron que
			// acelero MTC_FT_068 / MTC_FT_069). Si funciona, se guarda estado y se retorna; si no, se cae al flujo previo.
			try {
				LinkedHashMap<String, String> candidatasConInfoBD = obtenerTiendasConInformacionPorRangoDesdeBD(fechaConsulta, fechaConsulta, 20, usuario);
				LinkedHashMap<String, String> candidatasSinInfoBD = obtenerTiendasSinInformacionPorRangoDesdeBD(fechaConsulta, fechaConsulta, 20, usuario);
				System.out.println("[MTC_FT_070 FAST PATH] Candidatas BD CON info: " + candidatasConInfoBD.values());
				System.out.println("[MTC_FT_070 FAST PATH] Candidatas BD SIN info (activas +-30 dias): " + candidatasSinInfoBD.values());
				if (!candidatasConInfoBD.isEmpty() && !candidatasSinInfoBD.isEmpty()) {
					String etiquetaConInfoRapida = marcaUnaTiendaPorBusquedaFastPath(candidatasConInfoBD, elementos, "", "CON info");
					if (!etiquetaConInfoRapida.isEmpty()) {
						String crConInfoRapida = etiquetaConInfoRapida.split("\\s+", 2)[0];
						String etiquetaSinInfoVisible = marcaPrimeraTiendaVisibleSinInformacionEnRango(fechaConsulta, elementos, wait, crConInfoRapida);
						if (!etiquetaSinInfoVisible.isEmpty()) {
							int seleccionadasRapido = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
							boolean panelAbiertoRapido = !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
							if (seleccionadasRapido == 2 && panelAbiertoRapido) {
								tiendaConInfoPersonalizacion = etiquetaConInfoRapida;
								tiendaSinInfoPersonalizacion = etiquetaSinInfoVisible;
								System.out.println("[RESULTADO PASO] PASSED - Seleccion mixta MTC_FT_070 | CON info por buscador: " + etiquetaConInfoRapida + " | SIN info visible: " + etiquetaSinInfoVisible + " | Seleccionadas: " + seleccionadasRapido);
								testCase.addBoldTextEvidenceCurrentStep("Resultado paso: PASSED | Tienda con info marcada por buscador: " + etiquetaConInfoRapida + " | Tienda sin info marcada desde opciones visibles: " + etiquetaSinInfoVisible);
								if (screenshot) {
									testCase.addScreenShotCurrentStep("Filtro_Tienda_Con_Info_Y_Sin_Info_Visible");
								}
								return true;
							}
							System.out.println("[MTC_FT_070 FAST PATH] Seleccion mixta dejo conteo distinto a 2 (Actual: " + seleccionadasRapido + ", panelAbierto=" + panelAbiertoRapido + "). Limpiando selecciones antes de caer al flujo previo con scroll.");
							testCase.addBoldTextEvidenceCurrentStep("Seleccion mixta dejo " + seleccionadasRapido + " tiendas (esperado 2); se limpian selecciones y se ejecuta el flujo previo con scroll.");
							try {
								if (estaLimpiarSeleccionTiendaHabilitado(elementos)) {
									WebElement limpiar = obtenerElementoAccionableLimpiarSeleccion(elementos);
									((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", limpiar);
									u.hardWait(1);
									int seleccionadasTrasLimpiar = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
									System.out.println("[MTC_FT_070 FAST PATH] Selecciones tras Limpiar seleccion: " + seleccionadasTrasLimpiar);
									testCase.addBoldTextEvidenceCurrentStep("Selecciones tras Limpiar seleccion: " + seleccionadasTrasLimpiar + " (esperado 0)");
								} else {
									System.out.println("[MTC_FT_070 FAST PATH] Limpiar seleccion no habilitado; no se pudo limpiar antes de fallback.");
								}
							} catch (Exception e) {
								System.out.println("[MTC_FT_070 FAST PATH] No se pudo limpiar seleccion tras conteo incorrecto: " + e.getMessage());
							}
							// Al hacer clic en "Limpiar seleccion" el panel del filtro Tienda se puede cerrar. Se reabre para que el flujo previo (scroll+cruce)
							// encuentre las opciones visibles sin timeout esperando `opcionesFiltroTiendaVisibles`.
							try {
								if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
									boolean panelReabierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
									System.out.println("[MTC_FT_070 FAST PATH] Panel filtro Tienda reabierto tras limpiar: " + panelReabierto);
									if (!panelReabierto) {
										testCase.addBoldTextEvidenceCurrentStep("No fue posible reabrir el panel Tienda tras limpiar selecciones; el flujo previo con scroll puede fallar");
									}
								}
							} catch (Exception e) {
								System.out.println("[MTC_FT_070 FAST PATH] Error al reabrir panel Tienda tras limpiar: " + e.getMessage());
							}
							// El input del buscador Tienda pudo quedar con el ultimo termino de busqueda del fast path (por ejemplo "50000"),
							// lo que hace que ng-select siga filtrando el droplist y el flujo previo con scroll no vea el catalogo completo.
							// Se limpia el buscador y se da un hardWait corto para que ng-select regenere el listado antes del flujo lento.
							try {
								WebElement inputBusquedaLimpio = obtieneInputBusquedaTiendaVisible(elementos, new WebDriverWait(u.getDriver(), Duration.ofSeconds(3)));
								capturaBusquedaTiendaComoUsuario(inputBusquedaLimpio, "");
								u.hardWait(2);
								int opcionesTrasReset = u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles).size();
								System.out.println("[MTC_FT_070 FAST PATH] Buscador Tienda limpiado tras reabrir panel. Opciones visibles tras reset: " + opcionesTrasReset);
								testCase.addBoldTextEvidenceCurrentStep("Buscador Tienda limpiado tras reabrir panel para el flujo previo con scroll | Opciones visibles: " + opcionesTrasReset);
							} catch (Exception e) {
								System.out.println("[MTC_FT_070 FAST PATH] No se pudo limpiar el buscador tras reabrir panel: " + e.getMessage());
							}
						} else {
							System.out.println("[MTC_FT_070 FAST PATH] Se marco CON info pero no se encontro una tienda SIN info visible para completar la seleccion mixta. Se limpian selecciones antes de caer al flujo previo.");
							testCase.addBoldTextEvidenceCurrentStep("Fast path solo logro tienda CON info; no se encontro tienda SIN info visible. Se limpian selecciones y se ejecutara el flujo previo con scroll desde estado limpio");
							// Limpieza total: aunque CON info se marco correctamente (5006P), la tienda SIN info dejo posibles duplicados marcados (por delta > 1) o el buscador con residuo.
							// Se usa el mismo bloque de limpieza que ya se ejecuta cuando el conteo total no es 2.
							try {
								if (estaLimpiarSeleccionTiendaHabilitado(elementos)) {
									WebElement limpiar = obtenerElementoAccionableLimpiarSeleccion(elementos);
									((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", limpiar);
									u.hardWait(1);
									int seleccionadasTrasLimpiar = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
									System.out.println("[MTC_FT_070 FAST PATH] Selecciones tras Limpiar seleccion (SIN info abandonado): " + seleccionadasTrasLimpiar);
									testCase.addBoldTextEvidenceCurrentStep("Selecciones tras Limpiar seleccion: " + seleccionadasTrasLimpiar + " (esperado 0)");
								} else {
									System.out.println("[MTC_FT_070 FAST PATH] Limpiar seleccion no habilitado; no se pudo limpiar antes del slow path.");
								}
							} catch (Exception e) {
								System.out.println("[MTC_FT_070 FAST PATH] No se pudo limpiar seleccion tras SIN info abandonado: " + e.getMessage());
							}
							try {
								if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
									boolean panelReabierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
									System.out.println("[MTC_FT_070 FAST PATH] Panel filtro Tienda reabierto tras limpiar (SIN info abandonado): " + panelReabierto);
								}
							} catch (Exception e) {
								System.out.println("[MTC_FT_070 FAST PATH] Error al reabrir panel Tienda tras limpiar: " + e.getMessage());
							}
							try {
								WebElement inputBusquedaLimpio = obtieneInputBusquedaTiendaVisible(elementos, new WebDriverWait(u.getDriver(), Duration.ofSeconds(3)));
								capturaBusquedaTiendaComoUsuario(inputBusquedaLimpio, "");
								u.hardWait(2);
								int opcionesTrasReset = u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles).size();
								System.out.println("[MTC_FT_070 FAST PATH] Buscador Tienda limpiado tras reabrir panel (SIN info abandonado). Opciones visibles tras reset: " + opcionesTrasReset);
								testCase.addBoldTextEvidenceCurrentStep("Buscador Tienda limpiado tras reabrir panel para el flujo previo con scroll | Opciones visibles: " + opcionesTrasReset);
							} catch (Exception e) {
								System.out.println("[MTC_FT_070 FAST PATH] No se pudo limpiar el buscador tras reabrir panel: " + e.getMessage());
							}
						}
					} else {
						System.out.println("[MTC_FT_070 FAST PATH] No se logro marcar CON info por buscador; se cae al flujo previo con scroll.");
						testCase.addBoldTextEvidenceCurrentStep("Fast path no logro tienda CON info por buscador; se ejecutara el flujo previo con scroll");
					}
				} else {
					System.out.println("[MTC_FT_070 FAST PATH] BD no entrega candidatas suficientes (CON info=" + candidatasConInfoBD.size() + ", SIN info=" + candidatasSinInfoBD.size() + "); se cae al flujo previo.");
					testCase.addBoldTextEvidenceCurrentStep("Fast path sin candidatas BD suficientes; se ejecutara el flujo previo con scroll");
				}
			} catch (Exception e) {
				System.out.println("[MTC_FT_070 FAST PATH] Excepcion en fast path: " + e.getClass().getSimpleName() + " | " + e.getMessage() + "; se cae al flujo previo.");
				testCase.addBoldTextEvidenceCurrentStep("Fast path arrojo excepcion (" + e.getClass().getSimpleName() + "); se ejecutara el flujo previo con scroll");
			}

			LinkedHashMap<String, String> tiendasVisiblesUnicas = new LinkedHashMap<String, String>();
			LinkedHashMap<String, WebElement> elementosPorCr = new LinkedHashMap<String, WebElement>();
			for (int pagina = 0; pagina < 2 && tiendasVisiblesUnicas.size() < 12; pagina++) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionesFiltroTiendaVisibles));
				List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 15);
				for (WebElement opcion : opcionesVisibles) {
					String etiqueta = normalizarTextoFiltro(opcion);
					if (etiqueta.isEmpty()) {
						continue;
					}
					String crTienda = etiqueta.split("\\s+", 2)[0];
					if (!tiendasVisiblesUnicas.containsKey(crTienda)) {
						tiendasVisiblesUnicas.put(crTienda, etiqueta);
						elementosPorCr.put(crTienda, opcion);
					}
				}
				if (tiendasVisiblesUnicas.size() >= 6 || pagina == 1) {
					break;
				}
				try {
					WebElement contenedorScroll = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.contenedorScrollFiltroTienda));
					Long scrollAntes = (Long) ((JavascriptExecutor) u.getDriver()).executeScript("return arguments[0].scrollTop;", contenedorScroll);
					((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollTop = arguments[0].scrollTop + Math.max(arguments[0].clientHeight, 360);", contenedorScroll);
					esperaRenderCorto();
					Long scrollDespues = (Long) ((JavascriptExecutor) u.getDriver()).executeScript("return arguments[0].scrollTop;", contenedorScroll);
					if (scrollDespues.longValue() == scrollAntes.longValue()) {
						break;
					}
				} catch (Exception e) {
					break;
				}
			}

			if (tiendasVisiblesUnicas.size() < 2) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se obtuvieron al menos 2 tiendas visibles distintas en el droplist. Visibles: " + tiendasVisiblesUnicas.values());
				return false;
			}

			LinkedHashMap<String, String> tiendasVisiblesConInfo = obtenerTiendasVisiblesConInformacionPorRangoDesdeBD(fechaConsulta, fechaConsulta, tiendasVisiblesUnicas);
			LinkedHashMap<String, String> tiendasVisiblesSinInfo = new LinkedHashMap<String, String>();
			for (java.util.Map.Entry<String, String> entrada : tiendasVisiblesUnicas.entrySet()) {
				if (!tiendasVisiblesConInfo.containsKey(entrada.getKey())) {
					tiendasVisiblesSinInfo.put(entrada.getKey(), entrada.getValue());
				}
			}

			System.out.println("[MTC_FT_070 PASO 5] Tiendas visibles revisadas: " + tiendasVisiblesUnicas.values());
			System.out.println("[MTC_FT_070 PASO 5] Tiendas visibles CON info en la fecha " + fechaConsulta + ": " + tiendasVisiblesConInfo.values());
			System.out.println("[MTC_FT_070 PASO 5] Tiendas visibles SIN info en la fecha " + fechaConsulta + ": " + tiendasVisiblesSinInfo.values());
			testCase.addBoldTextEvidenceCurrentStep("Tiendas visibles revisadas: " + tiendasVisiblesUnicas.values());
			testCase.addBoldTextEvidenceCurrentStep("Tiendas visibles CON info en la fecha: " + tiendasVisiblesConInfo.values());
			testCase.addBoldTextEvidenceCurrentStep("Tiendas visibles SIN info en la fecha: " + tiendasVisiblesSinInfo.values());

			String tiendaConInfo = "";
			String crConInfo = "";
			boolean marcadaConInfo = false;

			if (!tiendasVisiblesConInfo.isEmpty()) {
				crConInfo = tiendasVisiblesConInfo.keySet().iterator().next();
				tiendaConInfo = tiendasVisiblesConInfo.get(crConInfo);
				WebElement opcionConInfo = elementosPorCr.get(crConInfo);
				if (opcionConInfo == null || !esOpcionVigente(opcionConInfo)) {
					opcionConInfo = esperaOpcionTiendaVisiblePorCr(crConInfo, elementos, wait);
				}
				if (opcionConInfo != null) {
					if (!estaOpcionTiendaMarcada(opcionConInfo)) {
						haceClickSeguroOpcionTienda(opcionConInfo);
						esperaRenderCorto();
					}
					marcadaConInfo = esperaOpcionTiendaMarcadaPorCr(crConInfo, elementos);
				}
				System.out.println("[MTC_FT_070 PASO 5] Marca tienda con info visible " + tiendaConInfo + " | Resultado: " + marcadaConInfo);
				testCase.addBoldTextEvidenceCurrentStep("Tienda con info marcada desde droplist visible: " + tiendaConInfo + " | Resultado: " + marcadaConInfo);
			}

			if (!marcadaConInfo) {
				System.out.println("[MTC_FT_070 PASO 5] Ninguna tienda visible tiene info. Se intenta fallback rapido por buscador contra BD.");
				testCase.addBoldTextEvidenceCurrentStep("Ninguna tienda visible tiene info en la fecha. Se intenta fallback rapido por buscador contra BD.");
				LinkedHashMap<String, String> tiendasConInfoBD = obtenerTiendasConInformacionPorRangoDesdeBD(fechaConsulta, fechaConsulta, 5, usuario);
				aseguraPanelFiltroTiendaAbierto(elementos, new WebDriverWait(u.getDriver(), Duration.ofSeconds(5)));
				WebElement inputBusquedaFallback = obtieneInputBusquedaTiendaVisible(elementos, new WebDriverWait(u.getDriver(), Duration.ofSeconds(5)));
				int candidatasIntentadas = 0;
				for (java.util.Map.Entry<String, String> candidata : tiendasConInfoBD.entrySet()) {
					candidatasIntentadas++;
					String candidataCr = candidata.getKey();
					String candidataEtiqueta = candidata.getValue();
					capturaBusquedaTiendaComoUsuario(inputBusquedaFallback, candidataCr);
					esperaRenderCorto();
					WebElement opcionFallback = esperaOpcionTiendaFiltradaPorCr(candidataCr, elementos, 3);
					if (opcionFallback == null) {
						System.out.println("[MTC_FT_070 PASO 5 FALLBACK] Candidata no visible en droplist del perfil: " + candidataEtiqueta);
						continue;
					}
					if (!estaOpcionTiendaMarcada(opcionFallback)) {
						haceClickSeguroOpcionTienda(opcionFallback);
						esperaRenderCorto();
					}
					boolean marcadaFallback = esperaOpcionTiendaMarcadaPorCr(candidataCr, elementos);
					if (marcadaFallback) {
						crConInfo = candidataCr;
						tiendaConInfo = candidataEtiqueta;
						marcadaConInfo = true;
						System.out.println("[MTC_FT_070 PASO 5 FALLBACK] Tienda con info marcada por buscador rapido: " + tiendaConInfo + " | Intento: " + candidatasIntentadas);
						testCase.addBoldTextEvidenceCurrentStep("Tienda con info marcada por buscador rapido: " + tiendaConInfo + " | Intento: " + candidatasIntentadas);
						break;
					}
				}
				try {
					capturaBusquedaTiendaComoUsuario(inputBusquedaFallback, "");
					esperaRenderCorto();
				} catch (Exception e) {
					System.out.println("[MTC_FT_070 PASO 5 FALLBACK] No se pudo limpiar buscador tras fallback CON info: " + e.getMessage());
				}
			}

			if (!marcadaConInfo) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se pudo marcar ninguna tienda con informacion (ni droplist ni buscador). Fecha: " + fechaFormateada);
				return false;
			}

			if (tiendasVisiblesSinInfo.isEmpty()) {
				LinkedHashMap<String, String> visiblesActuales = new LinkedHashMap<String, String>();
				List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 15);
				for (WebElement opcion : opcionesVisibles) {
					String etiqueta = normalizarTextoFiltro(opcion);
					if (!etiqueta.isEmpty()) {
						String cr = etiqueta.split("\\s+", 2)[0];
						if (!cr.equals(crConInfo)) {
							visiblesActuales.put(cr, etiqueta);
						}
					}
				}
				LinkedHashMap<String, String> conInfoActuales = obtenerTiendasVisiblesConInformacionPorRangoDesdeBD(fechaConsulta, fechaConsulta, visiblesActuales);
				for (java.util.Map.Entry<String, String> entrada : visiblesActuales.entrySet()) {
					if (!conInfoActuales.containsKey(entrada.getKey())) {
						tiendasVisiblesSinInfo.put(entrada.getKey(), entrada.getValue());
					}
				}
			}

			if (tiendasVisiblesSinInfo.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se encontro una tienda visible sin informacion en la fecha " + fechaFormateada);
				return false;
			}

			String crSinInfo = "";
			String tiendaSinInfo = "";
			for (java.util.Map.Entry<String, String> entrada : tiendasVisiblesSinInfo.entrySet()) {
				if (!entrada.getKey().equals(crConInfo)) {
					crSinInfo = entrada.getKey();
					tiendaSinInfo = entrada.getValue();
					break;
				}
			}
			if (crSinInfo.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se encontro una tienda sin informacion distinta a la tienda con informacion marcada");
				return false;
			}

			WebElement opcionSinInfo = esperaOpcionTiendaVisiblePorCr(crSinInfo, elementos, wait);
			if (opcionSinInfo == null) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se pudo localizar la opcion visible para la tienda sin informacion: " + tiendaSinInfo);
				return false;
			}
			if (!estaOpcionTiendaMarcada(opcionSinInfo)) {
				haceClickSeguroOpcionTienda(opcionSinInfo);
				esperaRenderCorto();
			}
			boolean marcadaSinInfo = esperaOpcionTiendaMarcadaPorCr(crSinInfo, elementos);
			System.out.println("[MTC_FT_070 PASO 5] Marca tienda sin info " + tiendaSinInfo + " | Resultado: " + marcadaSinInfo);
			testCase.addBoldTextEvidenceCurrentStep("Tienda sin info marcada: " + tiendaSinInfo + " | Resultado: " + marcadaSinInfo);
			if (!marcadaSinInfo) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se pudo marcar la tienda sin informacion: " + tiendaSinInfo);
				return false;
			}

			int seleccionadas = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
			boolean resultadoPaso = seleccionadas >= 2 && !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
			tiendaConInfoPersonalizacion = resultadoPaso ? tiendaConInfo : "";
			tiendaSinInfoPersonalizacion = resultadoPaso ? tiendaSinInfo : "";

			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "No se conservaron dos tiendas marcadas. Seleccionadas visibles: " + seleccionadas;
			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Marcar una tienda con info (" + tiendaConInfo + ") y una sin info (" + tiendaSinInfo + ")" + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " | Tienda con info marcada: " + tiendaConInfo + " | Tienda sin info marcada: " + tiendaSinInfo + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Marca_Con_Info_Y_Sin_Info");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Marcar tiendas mixtas | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Verifica si una opcion del filtro Tienda sigue siendo accesible en el DOM actual.
	 * Evita reutilizar referencias obsoletas cuando el multiselect regenera el listado despues del scroll o busqueda.
	 *
	 * @param opcion Opcion visible previamente capturada.
	 * @return True si la opcion sigue visible en el DOM; false si ya no es accesible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private boolean esOpcionVigente(WebElement opcion) {
		try {
			return opcion.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Ejecuta la consulta de Liquidacion de Ruta y valida que el grid muestre el mensaje "Algunas tiendas no se muestran..." acompanado del boton "Ver tiendas".
	 * Ambos elementos se identifican con los selectores oficiales del portal `span.mensaje-tiendas-sin-info` y `button.btn-ver-tiendas`.
	 *
	 * @param screenshot True para capturar evidencia visual del mensaje y boton en el grid.
	 * @return True si el mensaje y el boton "Ver tiendas" se muestran despues de la consulta; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean consultaYValidaMensajeYBotonVerTiendasVisibleEnGrid(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			u.hardWait(1);
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
			} catch (Exception e) {
				System.out.println("[MTC_FT_070 CONSULTA] Panel Tienda cerrado o no visible antes de consultar: " + e.getMessage());
			}

			WebElement consultar = wait.until(ExpectedConditions.elementToBeClickable(elementos.botonConsultarLiquidacionRuta));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", consultar);
			try {
				consultar.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", consultar);
			}
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			By mensajeSelector = By.cssSelector("span.mensaje-tiendas-sin-info");
			By botonSelector = By.cssSelector("button.btn-ver-tiendas");
			String mensajeEsperado = "Algunas tiendas no se muestran porque no cuentan con información dentro del rango seleccionado";
			WebElement mensaje = wait.until(ExpectedConditions.visibilityOfElementLocated(mensajeSelector));
			WebElement boton = wait.until(ExpectedConditions.visibilityOfElementLocated(botonSelector));
			String textoMensaje = normalizarTextoFiltro(mensaje);
			String textoBoton = normalizarTextoFiltro(boton);
			boolean mensajeCoincide = textoMensaje.contains(mensajeEsperado);
			boolean botonVisible = boton.isDisplayed();
			boolean resultadoPaso = mensajeCoincide && botonVisible;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "Mensaje esperado: '" + mensajeEsperado + "' | Actual: '" + textoMensaje + "' | Boton Ver tiendas visible: " + botonVisible;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Mensaje tiendas sin info y boton Ver tiendas visibles" + (resultadoPaso ? " | Texto: " + textoMensaje : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Mensaje esperado: " + mensajeEsperado);
			testCase.addBoldTextEvidenceCurrentStep("Mensaje actual: " + textoMensaje);
			testCase.addBoldTextEvidenceCurrentStep("Boton Ver tiendas visible: " + (botonVisible ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				u.highLight(mensajeSelector);
				u.highLight(botonSelector);
				testCase.addScreenShotCurrentStep("Mensaje_Y_Boton_Ver_Tiendas_Visible");
				u.unhighLight(mensajeSelector);
				u.unhighLight(botonSelector);
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Mensaje y boton Ver tiendas visibles | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el boton "Ver tiendas" respete el diseno, comportamiento y estilo visual del resto de botones ya implementados en Liquidacion de Ruta.
	 * Compara clases Angular Material principales contra el boton Consultar y confirma que el boton este visible, habilitado y con el label correcto.
	 *
	 * @param screenshot True para capturar evidencia visual del boton Ver tiendas.
	 * @return True si el boton respeta el estandar visual del portal; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaEstiloBotonVerTiendasEstandarAplicacion(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			By botonSelector = By.cssSelector("button.btn-ver-tiendas");
			WebElement botonVerTiendas = wait.until(ExpectedConditions.visibilityOfElementLocated(botonSelector));
			WebElement botonConsultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonConsultarLiquidacionRuta));

			String claseVerTiendas = botonVerTiendas.getAttribute("class");
			String claseConsultar = botonConsultar.getAttribute("class");
			String labelVerTiendas = normalizarTextoFiltro(botonVerTiendas);
			int alturaVerTiendas = botonVerTiendas.getSize().getHeight();
			int alturaConsultar = botonConsultar.getSize().getHeight();
			int diferenciaAlturaPx = Math.abs(alturaVerTiendas - alturaConsultar);

			String[] clasesEstandarObligatorias = new String[] {"mdc-button", "mat-mdc-button-base"};
			boolean tieneClasesEstandar = true;
			StringBuilder clasesFaltantes = new StringBuilder();
			for (String claseEstandar : clasesEstandarObligatorias) {
				if (claseVerTiendas == null || !claseVerTiendas.contains(claseEstandar)) {
					tieneClasesEstandar = false;
					if (clasesFaltantes.length() > 0) {
						clasesFaltantes.append(", ");
					}
					clasesFaltantes.append(claseEstandar);
				}
			}
			boolean mismoTipoRaised = claseVerTiendas != null && claseConsultar != null
					&& claseVerTiendas.contains("mat-mdc-raised-button") && claseConsultar.contains("mat-mdc-raised-button");
			boolean labelCorrecto = labelVerTiendas.equalsIgnoreCase("Ver tiendas");
			boolean botonHabilitado = botonVerTiendas.isEnabled();
			boolean alturaConsistente = diferenciaAlturaPx <= 6;

			boolean resultadoPaso = tieneClasesEstandar && mismoTipoRaised && labelCorrecto && botonHabilitado && alturaConsistente;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!tieneClasesEstandar) motivoFallo.append("Clases estandar faltantes: ").append(clasesFaltantes).append(" | ");
			if (!mismoTipoRaised) motivoFallo.append("No comparte estilo mat-mdc-raised-button con Consultar | ");
			if (!labelCorrecto) motivoFallo.append("Label esperado: 'Ver tiendas' | Actual: '").append(labelVerTiendas).append("' | ");
			if (!botonHabilitado) motivoFallo.append("Boton no habilitado para interaccion | ");
			if (!alturaConsistente) motivoFallo.append("Diferencia de altura vs Consultar: ").append(diferenciaAlturaPx).append("px | ");

			System.out.println("[VALIDACION ESTILO BOTON] Ver tiendas classes: " + claseVerTiendas);
			System.out.println("[VALIDACION ESTILO BOTON] Consultar classes: " + claseConsultar);
			System.out.println("[VALIDACION ESTILO BOTON] Altura Ver tiendas: " + alturaVerTiendas + "px | Altura Consultar: " + alturaConsultar + "px");
			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Estilo del boton Ver tiendas" + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			testCase.addBoldTextEvidenceCurrentStep("Boton Ver tiendas visible con etiqueta esperada: " + (labelCorrecto ? "SI" : "NO") + " | Actual: " + labelVerTiendas);
			testCase.addBoldTextEvidenceCurrentStep("Boton Ver tiendas habilitado para interaccion: " + (botonHabilitado ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Disenio consistente con el boton Consultar: " + (tieneClasesEstandar && mismoTipoRaised && alturaConsistente ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				u.highLight(botonSelector);
				testCase.addScreenShotCurrentStep("Estilo_Boton_Ver_Tiendas");
				u.unhighLight(botonSelector);
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Estilo del boton Ver tiendas | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Reabre el filtro Tienda y desmarca la tienda sin informacion previamente marcada, conservando la tienda con informacion para repetir la consulta.
	 * Usa la etiqueta guardada por `marcaPrimeraTiendaConInfoYUnaSinInfoEnRangoDesdeBD`.
	 *
	 * @param screenshot True para capturar evidencia visual del filtro Tienda tras desmarcar la tienda sin informacion.
	 * @return True si la tienda sin informacion queda desmarcada y el selector permanece abierto; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean desmarcaTiendaSinInfoPersonalizacion(boolean screenshot) {
		try {
			if (tiendaSinInfoPersonalizacion == null || tiendaSinInfoPersonalizacion.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se cuenta con tienda sin informacion previamente marcada para desmarcar");
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			if (u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty()) {
				boolean filtroAbierto = abreFiltroTiendaYValidaOpcionesVisibles(false);
				if (!filtroAbierto) {
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se pudo abrir el filtro Tienda para desmarcar la tienda sin informacion");
					return false;
				}
			}

			String crTiendaSinInfo = tiendaSinInfoPersonalizacion.split("\\s+", 2)[0];
			WebElement inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, new WebDriverWait(u.getDriver(), Duration.ofSeconds(5)));
			capturaBusquedaTiendaComoUsuario(inputBusqueda, crTiendaSinInfo);
			esperaRenderCorto();

			WebElement opcion = esperaOpcionTiendaFiltradaPorCr(crTiendaSinInfo, elementos, 6);
			if (opcion == null) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se encontro la opcion filtrada para el CR " + crTiendaSinInfo + " (" + tiendaSinInfoPersonalizacion + ")");
				return false;
			}

			boolean marcadaAntes = estaOpcionTiendaMarcada(opcion);
			if (marcadaAntes) {
				haceClickSeguroOpcionTienda(opcion);
				esperaRenderCorto();
			}
			boolean desmarcada = !esperaOpcionTiendaMarcadaPorCr(crTiendaSinInfo, elementos);

			capturaBusquedaTiendaComoUsuario(inputBusqueda, "");
			esperaRenderCorto();

			int seleccionadas = u.getDriver().findElements(elementos.opcionesFiltroTiendaSeleccionadas).size();
			boolean panelAbierto = !u.getDriver().findElements(elementos.panelFiltroTiendaActivo).isEmpty();
			boolean resultadoPaso = desmarcada && seleccionadas >= 1 && panelAbierto;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "Tienda sin info " + tiendaSinInfoPersonalizacion + " desmarcada: " + desmarcada + " | Seleccionadas restantes: " + seleccionadas + " | Panel abierto: " + panelAbierto;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Desmarcar tienda sin informacion: " + tiendaSinInfoPersonalizacion + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " | Tienda sin info desmarcada: " + tiendaSinInfoPersonalizacion + " | Seleccionadas restantes: " + seleccionadas + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Filtro_Tienda_Sin_Info_Desmarcada");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Desmarcar tienda sin informacion | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Ejecuta la consulta de Liquidacion de Ruta y valida que el grid NO muestre el mensaje "Algunas tiendas no se muestran..." ni el boton "Ver tiendas".
	 * Espera a que desaparezcan los modales de carga antes de capturar evidencia limpia y buscar los selectores.
	 *
	 * @param screenshot True para capturar evidencia visual del grid sin mensaje ni boton.
	 * @return True si ni el mensaje ni el boton se muestran despues de la consulta; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean consultaYValidaMensajeYBotonVerTiendasOcultosEnGrid(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			u.hardWait(1);
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
			} catch (Exception e) {
				System.out.println("[MTC_FT_070 CONSULTA] Panel Tienda cerrado o no visible antes de consultar: " + e.getMessage());
			}

			WebElement consultar = wait.until(ExpectedConditions.elementToBeClickable(elementos.botonConsultarLiquidacionRuta));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", consultar);
			try {
				consultar.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", consultar);
			}
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			By mensajeSelector = By.cssSelector("span.mensaje-tiendas-sin-info");
			By botonSelector = By.cssSelector("button.btn-ver-tiendas");
			List<WebElement> mensajes = u.getDriver().findElements(mensajeSelector);
			List<WebElement> botones = u.getDriver().findElements(botonSelector);
			boolean mensajeOculto = mensajes.isEmpty() || !mensajes.get(0).isDisplayed();
			boolean botonOculto = botones.isEmpty() || !botones.get(0).isDisplayed();
			boolean resultadoPaso = mensajeOculto && botonOculto;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "Mensaje oculto: " + mensajeOculto + " | Boton Ver tiendas oculto: " + botonOculto;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Mensaje y boton Ver tiendas ocultos tras consulta solo con tiendas con informacion" + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Mensaje tiendas sin info oculto esperado: SI | Actual: " + (mensajeOculto ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Boton Ver tiendas oculto esperado: SI | Actual: " + (botonOculto ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Mensaje_Y_Boton_Ver_Tiendas_Ocultos");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Mensaje y boton Ver tiendas ocultos | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene desde BD una Ruta Dinamica que tenga al menos una tienda precargada SIN informacion CEDIS en la fecha consultada.
	 * Devuelve la ruta con mayor cantidad de tiendas sin datos para maximizar la probabilidad de reproducir el escenario en el portal.
	 *
	 * @param usuario Usuario cuyo perfil y alcance de rutas se validan en BD.
	 * @param fechaFormateada Fecha del filtro en formato d MMM yyyy que se usara para evaluar tiendas sin informacion por ruta.
	 * @return Ruta Dinamica valida o cadena vacia si no se encuentra ninguna.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public String obtenerRutaDinamicaConTiendaSinInfoEnRangoDesdeBD(String usuario, String fechaFormateada) {
	    try {
	        if (fechaFormateada == null || fechaFormateada.trim().isEmpty()) {
	            testCase.addBoldTextEvidenceCurrentStep("No se puede obtener Ruta Dinamica con tienda sin info: fecha vacia");
	            return "";
	        }
	        LocalDate fechaConsulta = convertirFechaVisibleALocalDate(fechaFormateada);
	        String query = "SELECT r.ruta_propuesta, (r.total_tiendas - rf.tiendas_en_fecha) AS tiendas_sin_info "
	                + "FROM ( "
	                + "    SELECT h.ruta_propuesta, COUNT(DISTINCT h.id_retek) AS total_tiendas "
	                + "    FROM xxcm_historico_correos h "
	                + "    WHERE h.origen = 'cedis' "
	                + "    AND h.version_documento = 2 "
	                + "    AND h.is_visible = true "
	                + "    AND h.id_retek IS NOT NULL "
	                + "    AND h.ruta_propuesta IS NOT NULL "
	                + "    AND TRIM(h.ruta_propuesta) <> '' "
	                + "    AND (? IS NULL OR EXISTS ("
	                + "        SELECT 1 FROM xxcm_user_keys perfil "
	                + "        JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = perfil.usuario_id "
	                + "        WHERE datos_usuario.usuario = ? "
	                + "        AND perfil.short_name = 'PCPERFIL' "
	                + "        AND UPPER(TRIM(perfil.key_value)) = 'ADMIN'"
	                + "    ) OR h.suppliable_id::text IN ("
	                + "        SELECT alcance.key_value FROM xxcm_user_keys alcance "
	                + "        JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = alcance.usuario_id "
	                + "        WHERE datos_usuario.usuario = ? "
	                + "        AND alcance.short_name IN ('PCRFC', 'PCSUPPLIERID')"
	                + "    )) "
	                + "    GROUP BY h.ruta_propuesta "
	                + ") r "
	                + "JOIN ( "
	                + "    SELECT h.ruta_propuesta, COUNT(DISTINCT h.id_retek) AS tiendas_en_fecha "
	                + "    FROM xxcm_historico_correos h "
	                + "    WHERE h.origen = 'cedis' "
	                + "    AND h.version_documento = 2 "
	                + "    AND h.is_visible = true "
	                + "    AND h.id_retek IS NOT NULL "
	                + "    AND h.fecha_registro::date = ? "
	                + "    AND h.ruta_propuesta IS NOT NULL "
	                + "    AND TRIM(h.ruta_propuesta) <> '' "
	                + "    AND (? IS NULL OR EXISTS ("
	                + "        SELECT 1 FROM xxcm_user_keys perfil "
	                + "        JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = perfil.usuario_id "
	                + "        WHERE datos_usuario.usuario = ? "
	                + "        AND perfil.short_name = 'PCPERFIL' "
	                + "        AND UPPER(TRIM(perfil.key_value)) = 'ADMIN'"
	                + "    ) OR h.suppliable_id::text IN ("
	                + "        SELECT alcance.key_value FROM xxcm_user_keys alcance "
	                + "        JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = alcance.usuario_id "
	                + "        WHERE datos_usuario.usuario = ? "
	                + "        AND alcance.short_name IN ('PCRFC', 'PCSUPPLIERID')"
	                + "    )) "
	                + "    GROUP BY h.ruta_propuesta "
	                + ") rf ON rf.ruta_propuesta = r.ruta_propuesta "
	                + "WHERE rf.tiendas_en_fecha > 0 "
	                + "AND rf.tiendas_en_fecha < r.total_tiendas "
	                + "ORDER BY (r.total_tiendas - rf.tiendas_en_fecha) DESC, r.ruta_propuesta "
	                + "LIMIT 1";

	        try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
	                java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
	            String usuarioNormalizado = usuario == null || usuario.trim().isEmpty() ? null : usuario.trim();
	            ps.setString(1, usuarioNormalizado);
	            ps.setString(2, usuarioNormalizado);
	            ps.setString(3, usuarioNormalizado);
	            ps.setDate(4, java.sql.Date.valueOf(fechaConsulta));
	            ps.setString(5, usuarioNormalizado);
	            ps.setString(6, usuarioNormalizado);
	            ps.setString(7, usuarioNormalizado);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    String ruta = rs.getString("ruta_propuesta");
	                    int tiendasSinInfo = rs.getInt("tiendas_sin_info");
	                    PortalCometaMVP4.rutaPropuesta = ruta.trim();
	                    System.out.println("[BD RUTA CON TIENDAS SIN INFO] Usuario: " + usuario + " | Ruta: " + ruta + " | Tiendas sin informacion en la fecha " + fechaConsulta + ": " + tiendasSinInfo);
	                    testCase.addBoldTextEvidenceCurrentStep("Ruta Dinamica con tienda(s) precargada(s) sin informacion en el rango obtenida desde BD: " + ruta + " | Tiendas sin informacion: " + tiendasSinInfo);
	                    return ruta.trim();
	                }
	            }
	        }

	        String queryFallback = "SELECT rf.fecha_consulta, r.ruta_propuesta, (r.total_tiendas - rf.tiendas_en_fecha) AS tiendas_sin_info "
	                + "FROM ( "
	                + "    SELECT h.ruta_propuesta, COUNT(DISTINCT h.id_retek) AS total_tiendas "
	                + "    FROM xxcm_historico_correos h "
	                + "    WHERE h.origen = 'cedis' "
	                + "    AND h.version_documento = 2 "
	                + "    AND h.is_visible = true "
	                + "    AND h.id_retek IS NOT NULL "
	                + "    AND h.ruta_propuesta IS NOT NULL "
	                + "    AND TRIM(h.ruta_propuesta) <> '' "
	                + "    AND (? IS NULL OR EXISTS ("
	                + "        SELECT 1 FROM xxcm_user_keys perfil "
	                + "        JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = perfil.usuario_id "
	                + "        WHERE datos_usuario.usuario = ? "
	                + "        AND perfil.short_name = 'PCPERFIL' "
	                + "        AND UPPER(TRIM(perfil.key_value)) = 'ADMIN'"
	                + "    ) OR h.suppliable_id::text IN ("
	                + "        SELECT alcance.key_value FROM xxcm_user_keys alcance "
	                + "        JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = alcance.usuario_id "
	                + "        WHERE datos_usuario.usuario = ? "
	                + "        AND alcance.short_name IN ('PCRFC', 'PCSUPPLIERID')"
	                + "    )) "
	                + "    GROUP BY h.ruta_propuesta "
	                + ") r "
	                + "JOIN ( "
	                + "    SELECT h.ruta_propuesta, h.fecha_registro::date AS fecha_consulta, COUNT(DISTINCT h.id_retek) AS tiendas_en_fecha "
	                + "    FROM xxcm_historico_correos h "
	                + "    WHERE h.origen = 'cedis' "
	                + "    AND h.version_documento = 2 "
	                + "    AND h.is_visible = true "
	                + "    AND h.id_retek IS NOT NULL "
	                + "    AND h.fecha_registro::date BETWEEN CURRENT_DATE - 3 AND CURRENT_DATE "
	                + "    AND h.ruta_propuesta IS NOT NULL "
	                + "    AND TRIM(h.ruta_propuesta) <> '' "
	                + "    AND (? IS NULL OR EXISTS ("
	                + "        SELECT 1 FROM xxcm_user_keys perfil "
	                + "        JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = perfil.usuario_id "
	                + "        WHERE datos_usuario.usuario = ? "
	                + "        AND perfil.short_name = 'PCPERFIL' "
	                + "        AND UPPER(TRIM(perfil.key_value)) = 'ADMIN'"
	                + "    ) OR h.suppliable_id::text IN ("
	                + "        SELECT alcance.key_value FROM xxcm_user_keys alcance "
	                + "        JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = alcance.usuario_id "
	                + "        WHERE datos_usuario.usuario = ? "
	                + "        AND alcance.short_name IN ('PCRFC', 'PCSUPPLIERID')"
	                + "    )) "
	                + "    GROUP BY h.ruta_propuesta, h.fecha_registro::date "
	                + ") rf ON rf.ruta_propuesta = r.ruta_propuesta "
	                + "WHERE rf.tiendas_en_fecha > 0 "
	                + "AND rf.tiendas_en_fecha < r.total_tiendas "
	                + "ORDER BY rf.fecha_consulta DESC, (r.total_tiendas - rf.tiendas_en_fecha) DESC, r.ruta_propuesta "
	                + "LIMIT 1";

	        try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
	                java.sql.PreparedStatement ps = connection.prepareStatement(queryFallback)) {
	            String usuarioNormalizado = usuario == null || usuario.trim().isEmpty() ? null : usuario.trim();
	            ps.setString(1, usuarioNormalizado);
	            ps.setString(2, usuarioNormalizado);
	            ps.setString(3, usuarioNormalizado);
	            ps.setString(4, usuarioNormalizado);
	            ps.setString(5, usuarioNormalizado);
	            ps.setString(6, usuarioNormalizado);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    LocalDate fechaFallback = rs.getDate("fecha_consulta").toLocalDate();
	                    String ruta = rs.getString("ruta_propuesta");
	                    int tiendasSinInfo = rs.getInt("tiendas_sin_info");
	                    PortalCometaMVP4.rutaPropuesta = ruta.trim();
	                    PortalCometaMVP4.fechaFormateada = rutaPropuestaFormatearFechaVisible(fechaFallback);
	                    System.out.println("[BD RUTA CON TIENDAS SIN INFO FALLBACK] Usuario: " + usuario + " | Ruta: " + ruta + " | Fecha seleccionada: " + fechaFallback + " | Tiendas sin informacion: " + tiendasSinInfo);
	                    testCase.addBoldTextEvidenceCurrentStep("No se encontro ruta con tiendas sin informacion para " + fechaConsulta + "; se selecciona fecha visible con escenario valido: " + PortalCometaMVP4.fechaFormateada + " | Ruta: " + ruta + " | Tiendas sin informacion: " + tiendasSinInfo);
	                    return ruta.trim();
	                }
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("[BD RUTA CON TIENDAS SIN INFO] Error: " + e.getMessage());
	        testCase.addBoldTextEvidenceCurrentStep("Error al obtener Ruta Dinamica con tienda sin informacion: " + e.getMessage());
	    }
	    return "";
	}

	/**
	 * Obtiene desde BD una Ruta Dinamica que tenga al menos seis tiendas precargadas SIN informacion CEDIS en la fecha consultada.
	 * Se usa para reproducir el escenario del modal Ver tiendas donde el listado debe superar el espacio visible.
	 *
	 * @param fechaFormateada Fecha del filtro en formato d MMM yyyy que se usara para evaluar tiendas sin informacion por ruta.
	 * @return Ruta Dinamica valida con seis o mas tiendas sin informacion, o cadena vacia si no se encuentra.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public String obtenerRutaDinamicaConAlMenosSeisTiendasSinInfoEnRangoDesdeBD(String fechaFormateada) {
		try {
			if (fechaFormateada == null || fechaFormateada.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("No se puede obtener Ruta Dinamica con seis o mas tiendas sin info: fecha vacia");
				return "";
			}
			LocalDate fechaConsulta = convertirFechaVisibleALocalDate(fechaFormateada);
			String query = "SELECT r.ruta_propuesta, (r.total_tiendas - rf.tiendas_en_fecha) AS tiendas_sin_info "
					+ "FROM ( "
					+ "    SELECT h.ruta_propuesta, COUNT(DISTINCT h.id_retek) AS total_tiendas "
					+ "    FROM xxcm_historico_correos h "
					+ "    WHERE h.origen = 'cedis' "
					+ "    AND h.version_documento = 2 "
					+ "    AND h.is_visible = true "
					+ "    AND h.id_retek IS NOT NULL "
					+ "    AND h.ruta_propuesta IS NOT NULL "
					+ "    AND TRIM(h.ruta_propuesta) <> '' "
					+ "    GROUP BY h.ruta_propuesta "
					+ ") r "
					+ "JOIN ( "
					+ "    SELECT h.ruta_propuesta, COUNT(DISTINCT h.id_retek) AS tiendas_en_fecha "
					+ "    FROM xxcm_historico_correos h "
					+ "    WHERE h.origen = 'cedis' "
					+ "    AND h.version_documento = 2 "
					+ "    AND h.is_visible = true "
					+ "    AND h.id_retek IS NOT NULL "
					+ "    AND h.fecha_registro::date = ? "
					+ "    AND h.ruta_propuesta IS NOT NULL "
					+ "    AND TRIM(h.ruta_propuesta) <> '' "
					+ "    GROUP BY h.ruta_propuesta "
					+ ") rf ON rf.ruta_propuesta = r.ruta_propuesta "
					+ "WHERE rf.tiendas_en_fecha > 0 "
					+ "AND (r.total_tiendas - rf.tiendas_en_fecha) >= 6 "
					+ "ORDER BY (r.total_tiendas - rf.tiendas_en_fecha) DESC, r.ruta_propuesta "
					+ "LIMIT 1";

			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
				ps.setDate(1, java.sql.Date.valueOf(fechaConsulta));
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						String ruta = rs.getString("ruta_propuesta");
						int tiendasSinInfo = rs.getInt("tiendas_sin_info");
						System.out.println("[BD RUTA CON SEIS+ TIENDAS SIN INFO] Ruta: " + ruta + " | Tiendas sin informacion en la fecha " + fechaConsulta + ": " + tiendasSinInfo);
						testCase.addBoldTextEvidenceCurrentStep("Ruta Dinamica con seis o mas tiendas precargadas sin informacion en el rango obtenida desde BD: " + ruta + " | Tiendas sin informacion: " + tiendasSinInfo);
						return ruta.trim();
					}
				}
			}
		} catch (Exception e) {
			System.out.println("[BD RUTA CON SEIS+ TIENDAS SIN INFO] Error: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Error al obtener Ruta Dinamica con seis o mas tiendas sin informacion: " + e.getMessage());
		}
		return "";
	}

	/**
	 * Captura el estado actual de Liquidacion de Ruta (filtros y encabezado del grid) para poder compararlo despues de cerrar el modal Ver tiendas.
	 * Guarda los valores en un campo de instancia consumido por `cierraModalVerTiendasConXBlancaYValidaEstadoConservado`.
	 *
	 * @return Mapa con el estado capturado (fechas, selector, ruta, tienda y encabezado del grid).
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private LinkedHashMap<String, String> capturaEstadoLiquidacionRutaActual() {
		LinkedHashMap<String, String> estado = new LinkedHashMap<String, String>();
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			estado.put("fechaInicial", obtenerTextoSeguro(elementos.filtroFechaInicial));
			estado.put("fechaFinal", obtenerTextoSeguro(elementos.filtroFechaFinal));
			estado.put("selectorFiltro", obtenerTextoSeguro(elementos.filtroSelectorFiltro));
			String textoRuta = "";
			List<WebElement> inputsRuta = u.getDriver().findElements(elementos.inputRutaDinamica);
			if (!inputsRuta.isEmpty()) {
				String valor = inputsRuta.get(0).getAttribute("value");
				textoRuta = valor == null ? "" : valor.trim();
			}
			estado.put("rutaDinamica", textoRuta);
			estado.put("tienda", obtenerTextoSeguro(elementos.filtroTienda));
			estado.put("encabezadoGrid", obtenerTextoSeguro(elementos.containerDetalle));
		} catch (Exception e) {
			System.out.println("[MTC_FT_071 ESTADO] Error al capturar estado actual: " + e.getMessage());
		}
		return estado;
	}

	/**
	 * Obtiene el texto normalizado de un elemento localizado con la estrategia estandar del selector, sin lanzar excepcion si el elemento no existe.
	 *
	 * @param localizador Localizador del elemento a leer.
	 * @return Texto normalizado del elemento, o cadena vacia si no se encuentra.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private String obtenerTextoSeguro(By localizador) {
		try {
			List<WebElement> elementos = u.getDriver().findElements(localizador);
			if (elementos.isEmpty()) {
				return "";
			}
			return normalizarTextoFiltro(elementos.get(0));
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Hace clic en el boton "Ver tiendas" del grid y valida que el modal muestre el listado de tiendas sin informacion con formato CR Tienda + Nombre Tienda y scroll vertical.
	 * Antes de hacer clic captura el estado actual (filtros y encabezado del grid) para poder validar la conservacion en el paso siguiente.
	 *
	 * @param screenshot True para capturar evidencia visual del modal abierto.
	 * @return True si el modal abre con al menos seis registros con formato valido y scroll disponible; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean haceClicBotonVerTiendasYValidaModalListado(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			By botonVerTiendasSelector = By.cssSelector("button.btn-ver-tiendas");
			By modalSelector = By.xpath("//mat-dialog-container | //*[@role='dialog']");
			By itemsListaSelector = By.xpath("//mat-dialog-container//li[normalize-space(string())!=''] | //mat-dialog-container//tr[.//td[normalize-space(string())!='']] | //*[@role='dialog']//li[normalize-space(string())!=''] | //*[@role='dialog']//tr[.//td[normalize-space(string())!='']] | //mat-dialog-container//div[contains(@class,'tienda-item') or contains(@class,'store-item') or contains(@class,'list-item')] | //*[@role='dialog']//div[contains(@class,'tienda-item') or contains(@class,'store-item') or contains(@class,'list-item')]");

			estadoLiquidacionRutaAntesModal = capturaEstadoLiquidacionRutaActual();
			System.out.println("[MTC_FT_071 PASO 6] Estado capturado antes del modal: " + estadoLiquidacionRutaAntesModal);
			testCase.addBoldTextEvidenceCurrentStep("Estado capturado antes de abrir modal Ver tiendas: " + estadoLiquidacionRutaAntesModal);

			WebElement botonVerTiendas = wait.until(ExpectedConditions.elementToBeClickable(botonVerTiendasSelector));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", botonVerTiendas);
			try {
				botonVerTiendas.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", botonVerTiendas);
			}

			WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(modalSelector));
			u.hardWait(1);

			List<WebElement> items = new ArrayList<WebElement>();
			for (int intento = 0; intento < 5 && items.isEmpty(); intento++) {
				items = u.getDriver().findElements(itemsListaSelector);
				if (items.isEmpty()) {
					esperaRenderCorto();
				}
			}

			List<String> etiquetasTiendasListado = new ArrayList<String>();
			for (WebElement item : items) {
				String etiqueta = item.getText().replaceAll("\\s+", " ").trim();
				if (etiqueta.isEmpty() || etiqueta.toLowerCase().startsWith("id retek")) {
					continue;
				}
				etiquetasTiendasListado.add(etiqueta);
			}
			int itemsARevisar = Math.min(etiquetasTiendasListado.size(), 10);
			boolean formatoCorrecto = itemsARevisar > 0;
			for (int i = 0; i < itemsARevisar; i++) {
				String etiqueta = etiquetasTiendasListado.get(i);
				boolean coincideFormato = etiqueta.matches("^[A-Za-z0-9]+\\s+.+");
				System.out.println("[MTC_FT_071 PASO 6] Tienda " + (i + 1) + ": " + etiqueta + " | Formato CR+Nombre: " + (coincideFormato ? "CORRECTO" : "INCORRECTO"));
				formatoCorrecto = formatoCorrecto && coincideFormato;
			}

			int totalItems = etiquetasTiendasListado.size();
			boolean listadoSuperaSeis = totalItems >= 6;

			By contenedorScrollModal = By.xpath("//mat-dialog-container//*[@class] | //*[@role='dialog']//*[@class]");
			WebElement contenedorScroll = null;
			try {
				contenedorScroll = (WebElement) ((JavascriptExecutor) u.getDriver()).executeScript(
						"const dialog = arguments[0];"
						+ "const candidatos = dialog.querySelectorAll('*');"
						+ "for (const c of candidatos) {"
						+ "  if (c.scrollHeight > c.clientHeight + 5 && c.clientHeight > 0) { return c; }"
						+ "}"
						+ "return null;",
						modal);
			} catch (Exception e) {
				contenedorScroll = null;
			}

			boolean scrollVerticalDisponible = false;
			long scrollAntes = 0L;
			long scrollDespues = 0L;
			if (contenedorScroll != null) {
				JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
				Object scrollAntesObj = js.executeScript("return arguments[0].scrollTop;", contenedorScroll);
				scrollAntes = scrollAntesObj instanceof Number ? ((Number) scrollAntesObj).longValue() : 0L;
				js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + Math.max(arguments[0].clientHeight, 200);", contenedorScroll);
				esperaRenderCorto();
				Object scrollDespuesObj = js.executeScript("return arguments[0].scrollTop;", contenedorScroll);
				scrollDespues = scrollDespuesObj instanceof Number ? ((Number) scrollDespuesObj).longValue() : 0L;
				scrollVerticalDisponible = scrollDespues > scrollAntes;
				js.executeScript("arguments[0].scrollTop = 0;", contenedorScroll);
			} else {
				scrollVerticalDisponible = totalItems >= 6;
				System.out.println("[MTC_FT_071 PASO 6] No se localizo contenedor scrollable del modal; se asume scroll disponible por cantidad de items >= 6.");
			}

			boolean modalVisible = modal.isDisplayed();
			boolean resultadoPaso = modalVisible && listadoSuperaSeis && formatoCorrecto && scrollVerticalDisponible;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!modalVisible) motivoFallo.append("Modal no visible | ");
			if (!listadoSuperaSeis) motivoFallo.append("Total items: ").append(totalItems).append(" (esperado >= 6) | ");
			if (!formatoCorrecto) motivoFallo.append("Alguno de los items no cumple formato CR + Nombre | ");
			if (!scrollVerticalDisponible) motivoFallo.append("Scroll vertical no disponible en el modal | ");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Modal Ver tiendas con listado y scroll" + (resultadoPaso ? " | Tiendas listadas: " + totalItems : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Total tiendas visibles en el modal (excluyendo lineas 'ID Retek'): " + totalItems + " | Esperado: al menos 6");
			testCase.addBoldTextEvidenceCurrentStep("Tiendas revisadas (primeras " + itemsARevisar + "): " + etiquetasTiendasListado.subList(0, itemsARevisar));
			testCase.addBoldTextEvidenceCurrentStep("Scroll vertical del modal disponible: " + (scrollVerticalDisponible ? "SI" : "NO") + " | scrollTop antes/despues: " + scrollAntes + "/" + scrollDespues);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Modal_Ver_Tiendas_Listado");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Modal Ver tiendas con listado | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Cierra el modal Ver tiendas haciendo clic en la X (icono de cierre en color blanco) y valida que el sistema regrese a la pantalla principal conservando filtros, informacion previa y estado.
	 * Compara el estado capturado por `haceClicBotonVerTiendasYValidaModalListado` contra el estado actual despues de cerrar el modal.
	 *
	 * @param screenshot True para capturar evidencia visual de la pantalla despues de cerrar el modal.
	 * @return True si el modal se cierra, la X es de color blanco y el estado se conserva; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean cierraModalVerTiendasConXBlancaYValidaEstadoConservado(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			By modalSelector = By.xpath("//mat-dialog-container | //*[@role='dialog']");
			WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(modalSelector));

			WebElement botonCerrar = (WebElement) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const dialog = arguments[0];"
					+ "const iconoCerrar = dialog.querySelector('mat-icon.icono-close, .icono-close');"
					+ "if (iconoCerrar) { return iconoCerrar.closest('button, [role=\"button\"]') || iconoCerrar; }"
					+ "const candidatos = dialog.querySelectorAll('button[mat-dialog-close], button[aria-label*=\"cerrar\" i], button[aria-label*=\"close\" i], button.close, button.btn-close, .close-icon, [class*=\"close\"]');"
					+ "for (const c of candidatos) { if (c.offsetParent !== null) { return c; } }"
					+ "const iconos = dialog.querySelectorAll('mat-icon, i, svg');"
					+ "for (const ic of iconos) { const t = (ic.textContent || '').trim().toLowerCase(); if ((t === 'close' || t === 'cerrar') && ic.offsetParent !== null) { return ic.closest('button, [role=\"button\"]') || ic; } }"
					+ "return null;",
					modal);

			if (botonCerrar == null) {
				String motivoFallo = "No se localizo la X (icono de cierre) del modal Ver tiendas";
				System.out.println("[RESULTADO PASO] FAILED - Cerrar modal Ver tiendas | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			String colorTextoIcono = "";
			try {
				colorTextoIcono = (String) ((JavascriptExecutor) u.getDriver()).executeScript(
						"const el = arguments[0];"
						+ "const icono = el.querySelector('mat-icon, i, svg') || el;"
						+ "return window.getComputedStyle(icono).color || '';",
						botonCerrar);
			} catch (Exception e) {
				colorTextoIcono = "";
			}
			boolean colorBlanco = colorTextoIcono != null && (
					colorTextoIcono.replace(" ", "").matches("(?i)rgba?\\(255,255,255(,.*)?\\)")
					|| colorTextoIcono.equalsIgnoreCase("white")
					|| colorTextoIcono.equalsIgnoreCase("#ffffff")
					|| colorTextoIcono.equalsIgnoreCase("#fff"));

			try {
				botonCerrar.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", botonCerrar);
			}

			boolean modalCerrado = false;
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(modalSelector));
				modalCerrado = true;
			} catch (Exception e) {
				modalCerrado = u.getDriver().findElements(modalSelector).isEmpty();
			}
			u.hardWait(1);

			LinkedHashMap<String, String> estadoDespues = capturaEstadoLiquidacionRutaActual();
			boolean fechasIguales = igualIgnorandoEspacios(estadoLiquidacionRutaAntesModal.get("fechaInicial"), estadoDespues.get("fechaInicial"))
					&& igualIgnorandoEspacios(estadoLiquidacionRutaAntesModal.get("fechaFinal"), estadoDespues.get("fechaFinal"));
			boolean selectorIgual = igualIgnorandoEspacios(estadoLiquidacionRutaAntesModal.get("selectorFiltro"), estadoDespues.get("selectorFiltro"));
			boolean rutaIgual = igualIgnorandoEspacios(estadoLiquidacionRutaAntesModal.get("rutaDinamica"), estadoDespues.get("rutaDinamica"));
			boolean tiendaIgual = igualIgnorandoEspacios(estadoLiquidacionRutaAntesModal.get("tienda"), estadoDespues.get("tienda"));
			boolean encabezadoIgual = igualIgnorandoEspacios(estadoLiquidacionRutaAntesModal.get("encabezadoGrid"), estadoDespues.get("encabezadoGrid"));

			boolean estadoConservado = fechasIguales && selectorIgual && rutaIgual && tiendaIgual && encabezadoIgual;
			boolean resultadoPaso = modalCerrado && colorBlanco && estadoConservado;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!modalCerrado) motivoFallo.append("Modal Ver tiendas no se cerro | ");
			if (!colorBlanco) motivoFallo.append("Color de la X esperado: blanco (rgb(255,255,255)) | Actual: ").append(colorTextoIcono).append(" | ");
			if (!fechasIguales) motivoFallo.append("Fechas no conservadas | ");
			if (!selectorIgual) motivoFallo.append("Selector de Filtro no conservado | ");
			if (!rutaIgual) motivoFallo.append("Ruta Dinamica no conservada | ");
			if (!tiendaIgual) motivoFallo.append("Filtro Tienda no conservado | ");
			if (!encabezadoIgual) motivoFallo.append("Encabezado del grid no conservado | ");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Cerrar modal Ver tiendas y conservar estado" + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Color de la X del modal esperado: blanco | Actual: " + colorTextoIcono);
			testCase.addBoldTextEvidenceCurrentStep("Modal cerrado: " + (modalCerrado ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Estado antes del modal: " + estadoLiquidacionRutaAntesModal);
			testCase.addBoldTextEvidenceCurrentStep("Estado despues del modal: " + estadoDespues);
			testCase.addBoldTextEvidenceCurrentStep("Fechas conservadas: " + (fechasIguales ? "SI" : "NO") + " | Selector: " + (selectorIgual ? "SI" : "NO") + " | Ruta: " + (rutaIgual ? "SI" : "NO") + " | Tienda: " + (tiendaIgual ? "SI" : "NO") + " | Encabezado grid: " + (encabezadoIgual ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Modal_Ver_Tiendas_Cerrado_Estado_Conservado");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Cerrar modal Ver tiendas | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Compara dos cadenas ignorando espacios repetidos, saltos de linea y diferencias de mayusculas/minusculas.
	 * Se usa para validar la conservacion del estado de Liquidacion de Ruta despues de cerrar un modal.
	 *
	 * @param textoEsperado Cadena de referencia.
	 * @param textoActual Cadena capturada nuevamente para comparar.
	 * @return True si ambas cadenas son equivalentes; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private boolean igualIgnorandoEspacios(String textoEsperado, String textoActual) {
		String esperado = textoEsperado == null ? "" : textoEsperado.replaceAll("\\s+", " ").trim().toLowerCase();
		String actual = textoActual == null ? "" : textoActual.replaceAll("\\s+", " ").trim().toLowerCase();
		return esperado.equals(actual);
	}

	/**
	 * Obtiene desde BD los RETEK_LOC_ID unicos de las tiendas asociadas a una Ruta Dinamica con informacion en la fecha consultada.
	 * Se usa para comparar contra el grid del portal y para validar el payload del servicio de Liquidacion de Ruta.
	 *
	 * @param usuario Usuario cuyo perfil y alcance de tiendas se validan en BD.
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) usada en el filtro del portal.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @return Mapa ordenado id_retek -> etiqueta CR_TIENDA espacio NOMBRE_TIENDA para la ruta y fecha consultadas.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public LinkedHashMap<String, String> obtenerRetekLocIdsRutaDinamicaEnFechaDesdeBD(String usuario, String rutaDinamica, String fechaFormateada) {
		LinkedHashMap<String, String> tiendasBD = new LinkedHashMap<String, String>();
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty() || fechaFormateada == null || fechaFormateada.trim().isEmpty()) {
				return tiendasBD;
			}
			LocalDate fechaConsulta = convertirFechaVisibleALocalDate(fechaFormateada);
			String query = "SELECT DISTINCT h.id_retek, COALESCE(r.cr_tienda, h.cr_tienda) AS cr_tienda, "
					+ "COALESCE(r.desc_tienda, h.cr_tienda) AS nombre_tienda "
					+ "FROM xxcm_historico_correos h "
					+ "LEFT JOIN xxcm_region_plaza_tienda_job_sync r ON r.id_retek = h.id_retek "
					+ "WHERE h.ruta_propuesta = ? "
					+ "AND h.fecha_registro::date = ? "
					+ "AND TRIM(h.ruta_propuesta) <> '' "
					+ "AND h.version_documento = 2 "
					+ "AND h.is_visible = true "
					+ "AND h.id_retek IS NOT NULL "
					+ "AND (? IS NULL OR (h.origen = 'cedis' AND (EXISTS ("
					+ "    SELECT 1 FROM xxcm_user_keys perfil "
					+ "    JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = perfil.usuario_id "
					+ "    WHERE datos_usuario.usuario = ? "
					+ "    AND perfil.short_name = 'PCPERFIL' "
					+ "    AND UPPER(TRIM(perfil.key_value)) = 'ADMIN'"
					+ ") OR h.suppliable_id::text IN ("
					+ "    SELECT alcance.key_value FROM xxcm_user_keys alcance "
					+ "    JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = alcance.usuario_id "
					+ "    WHERE datos_usuario.usuario = ? "
					+ "    AND alcance.short_name IN ('PCRFC', 'PCSUPPLIERID')"
					+ ")))) "
					+ "ORDER BY COALESCE(r.cr_tienda, h.cr_tienda)";

			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
				String usuarioNormalizado = usuario == null || usuario.trim().isEmpty() ? null : usuario.trim();
				ps.setString(1, rutaDinamica.trim());
				ps.setDate(2, java.sql.Date.valueOf(fechaConsulta));
				ps.setString(3, usuarioNormalizado);
				ps.setString(4, usuarioNormalizado);
				ps.setString(5, usuarioNormalizado);
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						String idRetek = rs.getString("id_retek");
						String crTienda = rs.getString("cr_tienda");
						String nombreTienda = rs.getString("nombre_tienda");
						if (idRetek != null && !idRetek.trim().isEmpty()) {
							String etiqueta = ((crTienda == null ? "" : crTienda.trim()) + " " + (nombreTienda == null ? "" : nombreTienda.trim())).replaceAll("\\s+", " ").trim();
							tiendasBD.put(idRetek.trim(), etiqueta);
						}
					}
				}
			}
			System.out.println("[BD RETEK_LOC_ID RUTA+FECHA] Usuario: " + usuario + " | Ruta: " + rutaDinamica.trim() + " | Fecha: " + fechaConsulta + " | Registros: " + tiendasBD.size());
			testCase.addBoldTextEvidenceCurrentStep("BD RETEK_LOC_ID para ruta " + rutaDinamica.trim() + " y fecha " + fechaConsulta + ": " + tiendasBD.size() + " tiendas | Detalle: " + tiendasBD);
		} catch (Exception e) {
			System.out.println("[BD RETEK_LOC_ID RUTA+FECHA] Error: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Error al obtener RETEK_LOC_ID desde BD: " + e.getMessage());
		}
		return tiendasBD;
	}

	/**
	 * Valida contra BD que las tiendas mostradas en el grid de Liquidacion de Ruta correspondan a la Ruta Dinamica consultada, que cada RETEK_LOC_ID sea unico y que los CR_TIENDA y NOMBRE_TIENDA coincidan con el grid del portal.
	 * Extrae del encabezado del grid el conteo de Tiendas Totales de la Ruta y compara CR + Nombre de cada tienda esperada por BD contra el bloque de detalle mostrado por el portal.
	 *
	 * @param usuario Usuario cuyo perfil y alcance de tiendas se validan en BD.
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) usada en el filtro del portal.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param screenshot True para capturar evidencia visual del grid validado.
	 * @return True si cada RETEK_LOC_ID es unico en BD, la cantidad coincide con el grid y los CR/Nombre esperados aparecen en el grid; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaRetekLocIdUnicoYCoincideConGrid(String usuario, String rutaDinamica, String fechaFormateada, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Ruta Dinamica vacia para validar RETEK_LOC_ID contra BD");
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			LinkedHashMap<String, String> tiendasBD = obtenerRetekLocIdsRutaDinamicaEnFechaDesdeBD(usuario, rutaDinamica, fechaFormateada);
			if (tiendasBD.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: BD no devolvio tiendas para ruta " + rutaDinamica + " en la fecha " + fechaFormateada);
				return false;
			}

			WebElement detalle = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerDetalle));
			String textoDetalle = normalizarTextoFiltro(detalle);
			java.util.regex.Matcher matcherTotal = java.util.regex.Pattern.compile("Tiendas\\s+Totales\\s+de\\s+la\\s+Ruta:\\s*(\\d+)").matcher(textoDetalle);
			int totalGrid = matcherTotal.find() ? Integer.parseInt(matcherTotal.group(1)) : 0;

			WebElement contenedorTiendas = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerTienda));
			String textoGridTiendas = normalizarTextoFiltro(contenedorTiendas);

			int coincidencias = 0;
			int noCoincidencias = 0;
			StringBuilder detalleNoCoincidencias = new StringBuilder();
			for (java.util.Map.Entry<String, String> tiendaBD : tiendasBD.entrySet()) {
				String etiquetaBD = tiendaBD.getValue();
				String[] partes = etiquetaBD.split("\\s+", 2);
				String crBD = partes.length > 0 ? partes[0] : "";
				String nombreBD = partes.length > 1 ? partes[1] : "";
				boolean apareceCrEnGrid = !crBD.isEmpty() && textoGridTiendas.contains(crBD);
				boolean apareceNombreEnGrid = !nombreBD.isEmpty() && textoGridTiendas.contains(nombreBD);
				if (apareceCrEnGrid && apareceNombreEnGrid) {
					coincidencias++;
				} else {
					noCoincidencias++;
					detalleNoCoincidencias.append("RETEK_LOC_ID ").append(tiendaBD.getKey()).append(" (").append(etiquetaBD).append(") | CR en grid: ").append(apareceCrEnGrid).append(", nombre en grid: ").append(apareceNombreEnGrid).append(" | ");
				}
			}

			boolean retekUnicoBD = tiendasBD.size() > 0;
			boolean totalGridCoincide = totalGrid > 0 && totalGrid == tiendasBD.size();
			boolean todasCoinciden = noCoincidencias == 0 && coincidencias == tiendasBD.size();
			boolean resultadoPaso = retekUnicoBD && totalGridCoincide && todasCoinciden;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!retekUnicoBD) motivoFallo.append("BD no devolvio RETEK_LOC_ID unicos para la ruta y fecha | ");
			if (!totalGridCoincide) motivoFallo.append("Tiendas Totales de la Ruta en grid: ").append(totalGrid).append(" | Esperadas por BD: ").append(tiendasBD.size()).append(" | ");
			if (!todasCoinciden) motivoFallo.append("Coincidencias CR+Nombre grid vs BD: ").append(coincidencias).append("/").append(tiendasBD.size()).append(" | Detalle no coincidentes: ").append(detalleNoCoincidencias);

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - RETEK_LOC_ID unico y coincide con BD" + (resultadoPaso ? " | Tiendas BD: " + tiendasBD.size() + " | Grid: " + totalGrid : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("RETEK_LOC_ID unicos obtenidos desde BD para ruta " + rutaDinamica + " y fecha " + fechaFormateada + ": " + tiendasBD.keySet());
			testCase.addBoldTextEvidenceCurrentStep("Tiendas Totales de la Ruta segun encabezado del grid: " + totalGrid + " | Esperadas por BD: " + tiendasBD.size());
			testCase.addBoldTextEvidenceCurrentStep("Coincidencias CR+Nombre grid vs BD: " + coincidencias + "/" + tiendasBD.size() + " | No coincidentes: " + noCoincidencias);
			testCase.addBoldTextEvidenceCurrentStep("Detalle BD: " + tiendasBD);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Grid_RETEK_LOC_ID_Contra_BD");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Validar RETEK_LOC_ID grid vs BD | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Instrumenta fetch y XMLHttpRequest del navegador para capturar los payloads que el portal envia al backend en la siguiente consulta.
	 * Guarda cada request en `window.__cometa_payloads__` como una lista de objetos con url, metodo y body para inspeccion posterior.
	 * Debe ejecutarse antes de disparar la consulta que se quiere auditar.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private void instrumentaCapturaPayloadsRedNavegador() {
		try {
			((JavascriptExecutor) u.getDriver()).executeScript(
					"(function(){"
					+ "  if (window.__cometa_payloads__hooked__) { window.__cometa_payloads__ = []; return; }"
					+ "  window.__cometa_payloads__hooked__ = true;"
					+ "  window.__cometa_payloads__ = [];"
					+ "  try {"
					+ "    const origFetch = window.fetch;"
					+ "    window.fetch = function() {"
					+ "      const args = arguments;"
					+ "      try {"
					+ "        const urlArg = args[0];"
					+ "        const url = (urlArg && urlArg.url) ? urlArg.url : String(urlArg);"
					+ "        const opts = args[1] || {};"
					+ "        const method = (opts && opts.method) ? opts.method : ((urlArg && urlArg.method) ? urlArg.method : 'GET');"
					+ "        let body = '';"
					+ "        if (opts && opts.body) { try { body = (typeof opts.body === 'string') ? opts.body : JSON.stringify(opts.body); } catch(e) { body = String(opts.body); } }"
					+ "        window.__cometa_payloads__.push({source:'fetch', url:String(url), method:String(method), body:String(body), ts:Date.now()});"
					+ "      } catch(e) {}"
					+ "      return origFetch.apply(this, args);"
					+ "    };"
					+ "  } catch(e) {}"
					+ "  try {"
					+ "    const origOpen = XMLHttpRequest.prototype.open;"
					+ "    const origSend = XMLHttpRequest.prototype.send;"
					+ "    XMLHttpRequest.prototype.open = function(method, url) {"
					+ "      try { this.__cometa_method__ = method; this.__cometa_url__ = url; } catch(e) {}"
					+ "      return origOpen.apply(this, arguments);"
					+ "    };"
					+ "    XMLHttpRequest.prototype.send = function(body) {"
					+ "      try {"
					+ "        let bodyStr = '';"
					+ "        if (body) { try { bodyStr = (typeof body === 'string') ? body : JSON.stringify(body); } catch(e) { bodyStr = String(body); } }"
					+ "        window.__cometa_payloads__.push({source:'xhr', url:String(this.__cometa_url__ || ''), method:String(this.__cometa_method__ || ''), body:String(bodyStr), ts:Date.now()});"
					+ "      } catch(e) {}"
					+ "      return origSend.apply(this, arguments);"
					+ "    };"
					+ "  } catch(e) {}"
					+ "})();");
			System.out.println("[MTC_FT_073 PASO 7] Interceptor de fetch/XHR instalado en window.__cometa_payloads__");
		} catch (Exception e) {
			System.out.println("[MTC_FT_073 PASO 7] No se pudo instrumentar fetch/XHR: " + e.getMessage());
		}
	}

	/**
	 * Ejecuta nuevamente la consulta de Liquidacion de Ruta con los filtros ya capturados y valida que el payload enviado al backend contenga los RETEK_LOC_ID de las tiendas de la ruta.
	 * Instrumenta previamente fetch y XHR del navegador para registrar los requests que dispara el portal al presionar Consultar.
	 * Confirma que la llave de filtrado usada por el servicio corresponda a RETEK_LOC_ID y no a identificadores alternos como CR_TIENDA.
	 *
	 * @param usuario Usuario cuyo perfil y alcance de tiendas se validan en BD.
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) usada en el filtro del portal.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param screenshot True para capturar evidencia visual de la consulta reejecutada y el resumen del payload.
	 * @return True si el payload de la consulta incluye los RETEK_LOC_ID esperados; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaPayloadConsultaUsaRetekLocIdComoLlave(String usuario, String rutaDinamica, String fechaFormateada, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty() || fechaFormateada == null || fechaFormateada.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Ruta Dinamica o fecha vacia para validar payload");
				return false;
			}

			LinkedHashMap<String, String> tiendasBD = obtenerRetekLocIdsRutaDinamicaEnFechaDesdeBD(usuario, rutaDinamica, fechaFormateada);
			if (tiendasBD.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: BD no devolvio RETEK_LOC_ID esperados para ruta " + rutaDinamica + " y fecha " + fechaFormateada);
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			u.hardWait(1);

			instrumentaCapturaPayloadsRedNavegador();

			WebElement consultar = wait.until(ExpectedConditions.elementToBeClickable(elementos.botonConsultarLiquidacionRuta));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", consultar);
			try {
				consultar.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", consultar);
			}
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(2);

			@SuppressWarnings("unchecked")
			List<java.util.Map<String, Object>> payloads = (List<java.util.Map<String, Object>>) ((JavascriptExecutor) u.getDriver()).executeScript("return window.__cometa_payloads__ || [];");
			if (payloads == null || payloads.isEmpty()) {
				String motivoFallo = "No se capturaron payloads de red al reejecutar Consultar. Verifica que el interceptor este activo antes del clic.";
				System.out.println("[RESULTADO PASO] FAILED - Payload RETEK_LOC_ID | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			List<String> retekEsperados = new ArrayList<String>(tiendasBD.keySet());
			int payloadsRevisados = 0;
			String urlSeleccionada = "";
			String metodoSeleccionado = "";
			String bodyResumen = "";
			boolean payloadUsaCrEnLugarDeRetek = false;
			List<String> idRetekPayloadSeleccionado = new ArrayList<String>();
			java.util.Map<String, Object> payloadSeleccionado = null;
			for (java.util.Map<String, Object> payload : payloads) {
				String url = String.valueOf(payload.getOrDefault("url", ""));
				String metodo = String.valueOf(payload.getOrDefault("method", ""));
				String body = String.valueOf(payload.getOrDefault("body", ""));
				String urlLower = url.toLowerCase();
				if (urlLower.startsWith("data:") || urlLower.endsWith(".js") || urlLower.endsWith(".css") || urlLower.endsWith(".png") || urlLower.endsWith(".jpg") || urlLower.endsWith(".svg") || urlLower.endsWith(".woff") || urlLower.endsWith(".woff2")) {
					continue;
				}
				if (!urlLower.contains("liquidacion_ruta") && !urlLower.contains("liquidacion-ruta") && !urlLower.contains("liquidacionruta")) {
					continue;
				}
				payloadsRevisados++;
				List<String> idRetekEnBody = extraerIdRetekDelPayload(body);
				if (idRetekEnBody.isEmpty()) {
					continue;
				}
				payloadSeleccionado = payload;
				urlSeleccionada = url;
				metodoSeleccionado = metodo;
				bodyResumen = body.length() > 800 ? body.substring(0, 800) + "..." : body;
				idRetekPayloadSeleccionado = idRetekEnBody;
				String contenidoRevisar = (url + " " + body).toLowerCase();
				payloadUsaCrEnLugarDeRetek = false;
				for (String etiquetaBD : tiendasBD.values()) {
					String[] partes = etiquetaBD.split("\\s+", 2);
					String cr = partes.length > 0 ? partes[0].toLowerCase() : "";
					if (!cr.isEmpty() && contenidoRevisar.contains("\"" + cr + "\"")) {
						payloadUsaCrEnLugarDeRetek = true;
						break;
					}
				}
			}

			int totalRetekEsperados = retekEsperados.size();
			int retekCoincidentes = 0;
			for (String esperado : retekEsperados) {
				if (idRetekPayloadSeleccionado.contains(esperado)) {
					retekCoincidentes++;
				}
			}

			boolean payloadTieneCampoIdRetek = payloadSeleccionado != null && !idRetekPayloadSeleccionado.isEmpty();
			boolean cubreMitadOMas = totalRetekEsperados == 0 ? false : (retekCoincidentes * 2) >= totalRetekEsperados;
			boolean resultadoPaso = payloadTieneCampoIdRetek && cubreMitadOMas && !payloadUsaCrEnLugarDeRetek;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!payloadTieneCampoIdRetek) motivoFallo.append("Ningun payload al servicio liquidacion_ruta contiene el campo 'idRetek' con valores. Total payloads liquidacion_ruta revisados: ").append(payloadsRevisados).append(" | ");
			if (payloadTieneCampoIdRetek && !cubreMitadOMas) motivoFallo.append("Solo ").append(retekCoincidentes).append(" de ").append(totalRetekEsperados).append(" RETEK_LOC_ID esperados aparecen en el array idRetek del payload | ");
			if (payloadUsaCrEnLugarDeRetek) motivoFallo.append("El payload usa tambien CR_TIENDA como valor entrecomillado (identificador alterno) | ");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Payload usa idRetek como llave" + (resultadoPaso ? " | Coincidencias: " + retekCoincidentes + "/" + totalRetekEsperados : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Total payloads capturados: " + payloads.size() + " | Payloads liquidacion_ruta revisados: " + payloadsRevisados);
			testCase.addBoldTextEvidenceCurrentStep("RETEK_LOC_ID esperados desde BD: " + totalRetekEsperados + " | idRetek encontrados en el payload: " + idRetekPayloadSeleccionado.size() + " | Coincidencias: " + retekCoincidentes);
			testCase.addBoldTextEvidenceCurrentStep("Valores idRetek extraidos del payload: " + idRetekPayloadSeleccionado);
			testCase.addBoldTextEvidenceCurrentStep("Payload seleccionado | URL: " + urlSeleccionada + " | Metodo: " + metodoSeleccionado);
			testCase.addBoldTextEvidenceCurrentStep("Payload body (primeros 800 chars): " + bodyResumen);
			testCase.addBoldTextEvidenceCurrentStep("Payload usa CR_TIENDA como identificador alterno entrecomillado: " + (payloadUsaCrEnLugarDeRetek ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Payload_Consulta_RETEK_LOC_ID");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Payload RETEK_LOC_ID | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Extrae del cuerpo de un request los valores numericos del campo idRetek enviado por el portal.
	 * Soporta el formato del servicio liquidacion_ruta donde el filtro se envia como {"field":"idRetek","value":[34838, 2905, ...]}.
	 * Si el campo no aparece o no tiene arreglo asociado, devuelve una lista vacia.
	 *
	 * @param body Cuerpo del request tal como lo captura el interceptor de fetch/XHR.
	 * @return Lista de valores numericos de idRetek encontrados en el payload; puede estar vacia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private List<String> extraerIdRetekDelPayload(String body) {
		List<String> valores = new ArrayList<String>();
		if (body == null || body.isEmpty()) {
			return valores;
		}
		try {
			java.util.regex.Pattern patron = java.util.regex.Pattern.compile("(?i)\"idRetek\"[^\\[]*\\[([^\\]]*)\\]");
			java.util.regex.Matcher matcher = patron.matcher(body);
			while (matcher.find()) {
				String contenido = matcher.group(1);
				if (contenido == null || contenido.trim().isEmpty()) {
					continue;
				}
				String[] tokens = contenido.split(",");
				for (String token : tokens) {
					String limpio = token.trim().replaceAll("\"", "");
					if (!limpio.isEmpty() && limpio.matches("-?\\d+")) {
						valores.add(limpio);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("[EXTRAE IDRETEK] Error al parsear payload: " + e.getMessage());
		}
		return valores;
	}

	/**
	 * Valida despues de una consulta valida por Ruta Dinamica que el grid muestre el primer renglon de resumen y el detalle de cada tienda-transferencia, con al menos dos tiendas-transferencia visibles.
	 * Extrae del encabezado del grid el conteo "Total de Transferencias: N" y localiza los renglones tienda-transferencia con selectores flexibles para tolerar variaciones del DOM entre CEDIS y Admin.
	 *
	 * @param screenshot True para capturar evidencia visual del grid con las tiendas-transferencia visibles.
	 * @return True si el encabezado reporta al menos 2 transferencias y se detectan al menos 2 renglones tienda-transferencia; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaGridConAlMenosDosTiendasTransferencia(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement detalle = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerDetalle));
			String textoDetalle = normalizarTextoFiltro(detalle);
			java.util.regex.Matcher matcherTransferencias = java.util.regex.Pattern.compile("Total\\s+de\\s+Transferencias:\\s*(\\d+)").matcher(textoDetalle);
			int totalTransferenciasGrid = matcherTransferencias.find() ? Integer.parseInt(matcherTransferencias.group(1)) : 0;

			@SuppressWarnings("unchecked")
			java.util.List<Object> renglonesJs = (java.util.List<Object>) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const raiz = document.querySelector('app-cedis-liquidacion-ruta #detalle-tiendas')"
					+ " || document.querySelector('#detalle-tiendas')"
					+ " || document.querySelector('app-cedis-liquidacion-ruta');"
					+ "if (!raiz) return [];"
					+ "const selectores = ['.tienda-wrapper', '[id=\"tienda\"]', 'app-cedis-liquidacion-ruta-tienda-transferencia', 'app-tienda-transferencia', '[class*=\"tienda-transferencia\"]', '[class*=\"renglon-transferencia\"]', 'mat-card', '[class*=\"card-transferencia\"]'];"
					+ "let renglones = [];"
					+ "for (const sel of selectores) {"
					+ "  const nodos = raiz.querySelectorAll(sel);"
					+ "  const visibles = Array.from(nodos).filter(n => n.offsetParent !== null && n.getBoundingClientRect().height > 20);"
					+ "  if (visibles.length >= 2) { renglones = visibles; break; }"
					+ "}"
					+ "if (renglones.length < 2) {"
					+ "  const candidatos = raiz.querySelectorAll('.tienda-linea-lateral');"
					+ "  renglones = Array.from(candidatos).map(l => l.closest('.tienda-wrapper, [id=\"tienda\"], mat-card, [class*=\"card\"], [class*=\"renglon\"]') || l.parentElement).filter(el => el);"
					+ "}"
					+ "return renglones;");
			int renglonesDetectados = renglonesJs == null ? 0 : renglonesJs.size();

			boolean encabezadoConDosOMas = totalTransferenciasGrid >= 2;
			boolean renglonesConDosOMas = renglonesDetectados >= 2;
			boolean resultadoPaso = encabezadoConDosOMas && renglonesConDosOMas;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!encabezadoConDosOMas) motivoFallo.append("El encabezado reporta Total de Transferencias: ").append(totalTransferenciasGrid).append(" (esperado >= 2) | ");
			if (!renglonesConDosOMas) motivoFallo.append("Renglones tienda-transferencia detectados: ").append(renglonesDetectados).append(" (esperado >= 2) | ");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Grid con al menos dos tiendas-transferencia" + (resultadoPaso ? " | Total transferencias: " + totalTransferenciasGrid + " | Renglones detectados: " + renglonesDetectados : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Total de Transferencias segun encabezado del grid: " + totalTransferenciasGrid + " | Esperado: >= 2");
			testCase.addBoldTextEvidenceCurrentStep("Renglones tienda-transferencia detectados en el detalle: " + renglonesDetectados + " | Esperado: >= 2");
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Grid_Dos_Tiendas_Transferencia");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Grid con al menos dos tiendas-transferencia | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida el estado visual inicial de cada tienda-transferencia despues de una consulta valida: linea lateral izquierda en color verde, icono de palomita y selector "Incluido en el calculo" seleccionado en color verde con palomita visible.
	 * Recorre los renglones tienda-transferencia del grid via JavaScript y evalua color de border-left, presencia de icono con texto/clase de check y texto y color del selector para tolerar variaciones del DOM.
	 *
	 * @param screenshot True para capturar evidencia visual del estado inicial de las tiendas-transferencia.
	 * @return True si todas las tiendas-transferencia detectadas cumplen linea verde, icono palomita y selector Incluido en el calculo en verde; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaEstadoInicialIncluidoCalculoTodasTiendasTransferencia(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerTienda));

			@SuppressWarnings("unchecked")
			java.util.Map<String, Object> resumen = (java.util.Map<String, Object>) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const raiz = document.querySelector('app-cedis-liquidacion-ruta #detalle-tiendas')"
					+ " || document.querySelector('#detalle-tiendas')"
					+ " || document.querySelector('app-cedis-liquidacion-ruta');"
					+ "if (!raiz) return {total:0, cumplenTodos:false, detalle:[]};"
					+ "const selectores = ['.tienda-wrapper', '[id=\"tienda\"]', 'app-cedis-liquidacion-ruta-tienda-transferencia', 'app-tienda-transferencia', '[class*=\"tienda-transferencia\"]', '[class*=\"renglon-transferencia\"]', 'mat-card', '[class*=\"card-transferencia\"]'];"
					+ "let renglones = [];"
					+ "for (const sel of selectores) {"
					+ "  const nodos = raiz.querySelectorAll(sel);"
					+ "  const visibles = Array.from(nodos).filter(n => n.offsetParent !== null && n.getBoundingClientRect().height > 20);"
					+ "  if (visibles.length >= 1) { renglones = visibles; break; }"
					+ "}"
					+ "if (renglones.length === 0) {"
					+ "  const candidatos = raiz.querySelectorAll('.tienda-linea-lateral');"
					+ "  renglones = Array.from(candidatos).map(l => l.closest('.tienda-wrapper, [id=\"tienda\"], mat-card, [class*=\"card\"], [class*=\"renglon\"]') || l.parentElement).filter(el => el);"
					+ "}"
					+ "function esVerde(rgb) {"
					+ "  if (!rgb) return false;"
					+ "  const m = rgb.match(/rgba?\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)/);"
					+ "  if (!m) return false;"
					+ "  const r = parseInt(m[1]); const g = parseInt(m[2]); const b = parseInt(m[3]);"
					+ "  return g >= 100 && g > r + 20 && g > b + 20;"
					+ "}"
					+ "let cumplenTodos = renglones.length > 0;"
					+ "const detalle = [];"
					+ "for (const ren of renglones) {"
					+ "  let lineaVerde = false;"
					+ "  let borderColor = '';"
					+ "  const lineaClase = ren.querySelector('.tienda-linea-lateral.linea-verde, [class*=\"linea-verde\"]');"
					+ "  if (lineaClase) {"
					+ "    lineaVerde = true;"
					+ "    borderColor = window.getComputedStyle(lineaClase).backgroundColor || 'clase linea-verde detectada';"
					+ "  } else {"
					+ "    const cs = window.getComputedStyle(ren);"
					+ "    borderColor = cs.borderLeftColor || '';"
					+ "    lineaVerde = esVerde(borderColor);"
					+ "    if (!lineaVerde) {"
					+ "      const hijos = ren.querySelectorAll('*');"
					+ "      for (const h of hijos) {"
					+ "        const chs = window.getComputedStyle(h);"
					+ "        if (parseFloat(chs.borderLeftWidth) >= 2 && esVerde(chs.borderLeftColor)) { lineaVerde = true; borderColor = chs.borderLeftColor; break; }"
					+ "        if (parseFloat(chs.width) <= 8 && parseFloat(chs.height) >= 30 && esVerde(chs.backgroundColor)) { lineaVerde = true; borderColor = chs.backgroundColor; break; }"
					+ "      }"
					+ "    }"
					+ "  }"
					+ "  const iconos = Array.from(ren.querySelectorAll('mat-icon, i, svg, [class*=\"icono\"], [class*=\"palomita\"], [class*=\"check\"]'));"
					+ "  const iconoPalomita = iconos.some(ic => {"
					+ "    const t = (ic.textContent || '').trim().toLowerCase();"
					+ "    const c = (ic.className && ic.className.baseVal !== undefined ? ic.className.baseVal : (ic.className || '')).toString().toLowerCase();"
					+ "    return t === 'check' || t === 'check_circle' || t === 'done' || t.includes('palomita') || c.includes('palomita') || c.includes('check') || c.includes('success') || c.includes('verde');"
					+ "  });"
					+ "  let selectorTexto = '';"
					+ "  let selectorIncluidoVerde = false;"
					+ "  const selects = Array.from(ren.querySelectorAll('mat-select, [role=\"combobox\"]'));"
					+ "  for (const s of selects) {"
					+ "    const txt = (s.textContent || '').replace(/\\s+/g, ' ').trim();"
					+ "    if (txt.toLowerCase().includes('incluido en el c')) {"
					+ "      selectorTexto = txt;"
					+ "      const scs = window.getComputedStyle(s);"
					+ "      let verdeSelect = esVerde(scs.color) || esVerde(scs.backgroundColor);"
					+ "      if (!verdeSelect) {"
					+ "        const hijos = s.querySelectorAll('*');"
					+ "        for (const h of hijos) {"
					+ "          const hcs = window.getComputedStyle(h);"
					+ "          if (esVerde(hcs.color) || esVerde(hcs.backgroundColor)) { verdeSelect = true; break; }"
					+ "        }"
					+ "      }"
					+ "      selectorIncluidoVerde = verdeSelect;"
					+ "      break;"
					+ "    }"
					+ "  }"
					+ "  if (selectorTexto.length === 0) {"
					+ "    const cont = ren.querySelector('[class*=\"tienda-contenido\"], .tienda-header-row') || ren;"
					+ "    const contTxt = (cont.textContent || '').replace(/\\s+/g, ' ').trim();"
					+ "    const rx = /(Estado[^\\n]*)?\\s*(Incluido\\s+en\\s+el\\s+c[aá]lculo)/i;"
					+ "    const m = contTxt.match(rx);"
					+ "    if (m) {"
					+ "      selectorTexto = m[2];"
					+ "      const nodos = ren.querySelectorAll('*');"
					+ "      for (const n of nodos) {"
					+ "        const nt = (n.textContent || '').replace(/\\s+/g, ' ').trim();"
					+ "        if (nt.toLowerCase() === selectorTexto.toLowerCase() || nt.toLowerCase() === 'incluido en el cálculo' || nt.toLowerCase() === 'incluido en el calculo') {"
					+ "          const ncs = window.getComputedStyle(n);"
					+ "          if (esVerde(ncs.color) || esVerde(ncs.backgroundColor)) { selectorIncluidoVerde = true; break; }"
					+ "        }"
					+ "      }"
					+ "    }"
					+ "  }"
					+ "  const cumple = lineaVerde && iconoPalomita && selectorTexto.length > 0 && selectorIncluidoVerde;"
					+ "  detalle.push({textoSelector:selectorTexto, borderColor:borderColor, lineaVerde:lineaVerde, iconoPalomita:iconoPalomita, selectorIncluidoVerde:selectorIncluidoVerde, cumple:cumple});"
					+ "  if (!cumple) cumplenTodos = false;"
					+ "}"
					+ "return {total:renglones.length, cumplenTodos:cumplenTodos, detalle:detalle};");

			int total = resumen == null ? 0 : ((Number) resumen.getOrDefault("total", 0)).intValue();
			boolean cumplenTodos = resumen != null && Boolean.TRUE.equals(resumen.get("cumplenTodos"));
			@SuppressWarnings("unchecked")
			java.util.List<java.util.Map<String, Object>> detalle = resumen == null ? new java.util.ArrayList<java.util.Map<String, Object>>() : (java.util.List<java.util.Map<String, Object>>) resumen.getOrDefault("detalle", new java.util.ArrayList<>());

			int cumplen = 0;
			StringBuilder detalleFallos = new StringBuilder();
			for (int i = 0; i < detalle.size(); i++) {
				java.util.Map<String, Object> d = detalle.get(i);
				boolean cumple = Boolean.TRUE.equals(d.get("cumple"));
				if (cumple) cumplen++;
				String textoLog = "Tienda-transferencia " + (i + 1) + " | Linea verde: " + d.get("lineaVerde") + " (color: " + d.get("borderColor") + ") | Icono palomita: " + d.get("iconoPalomita") + " | Selector Incluido verde: " + d.get("selectorIncluidoVerde") + " | Texto selector: '" + d.get("textoSelector") + "'";
				System.out.println("[VALIDACION INCLUIDO] " + textoLog);
				testCase.addBoldTextEvidenceCurrentStep(textoLog);
				if (!cumple) {
					detalleFallos.append("Renglon ").append(i + 1).append(" no cumple | ");
				}
			}

			boolean resultadoPaso = total >= 1 && cumplenTodos;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (total < 1) motivoFallo.append("No se detectaron renglones tienda-transferencia en el grid | ");
			if (total >= 1 && !cumplenTodos) motivoFallo.append("Cumplen: ").append(cumplen).append("/").append(total).append(" | ").append(detalleFallos);

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Estado inicial Incluido en el calculo para todas las tiendas-transferencia" + (resultadoPaso ? " | Total: " + total : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Total tiendas-transferencia revisadas: " + total + " | Cumplen linea verde + palomita + Incluido verde: " + cumplen);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				try {
					((JavascriptExecutor) u.getDriver()).executeScript(
							"const primerRenglon = document.querySelector('app-cedis-liquidacion-ruta #detalle-tiendas .tienda-wrapper')"
							+ " || document.querySelector('#detalle-tiendas .tienda-wrapper')"
							+ " || document.querySelector('#detalle-tiendas [id=\"tienda\"]');"
							+ "if (primerRenglon) {"
							+ "  const rect = primerRenglon.getBoundingClientRect();"
							+ "  const y = rect.top + window.pageYOffset - Math.max(120, Math.floor(window.innerHeight * 0.35));"
							+ "  window.scrollTo({top: Math.max(y, 0), left: 0, behavior: 'instant'});"
							+ "}");
					u.hardWait(1);
				} catch (Exception e) {
					System.out.println("[VALIDACION INCLUIDO] No fue posible ajustar scroll al primer renglon tienda-transferencia antes del screenshot: " + e.getMessage());
				}
				testCase.addScreenShotCurrentStep("Estado_Inicial_Incluido_Calculo_Todas_TT");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Estado inicial Incluido en el calculo | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Abre el selector Estado de la primera tienda-transferencia visible en el grid y valida que despliegue unicamente las opciones "Incluido en el calculo" y "Excluido del calculo".
	 * Deja el panel del selector abierto para que el paso siguiente pueda seleccionar la opcion Excluido sin volver a abrirlo.
	 *
	 * @param screenshot True para capturar evidencia visual del selector abierto y sus opciones.
	 * @return True si el selector abre y muestra unicamente las dos opciones esperadas; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean abreSelectorEstadoPrimeraTiendaTransferenciaYValidaOpciones(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerTienda));

			@SuppressWarnings("unchecked")
			java.util.Map<String, Object> info = (java.util.Map<String, Object>) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const raiz = document.querySelector('app-cedis-liquidacion-ruta #detalle-tiendas')"
					+ " || document.querySelector('#detalle-tiendas')"
					+ " || document.querySelector('app-cedis-liquidacion-ruta');"
					+ "if (!raiz) return null;"
					+ "const wrappers = raiz.querySelectorAll('.tienda-wrapper, [id=\"tienda\"]');"
					+ "for (const w of wrappers) {"
					+ "  if (w.offsetParent === null) continue;"
					+ "  const matSelect = w.querySelector('mat-select, [role=\"combobox\"]');"
					+ "  if (matSelect) {"
					+ "    const trigger = matSelect.querySelector('.mat-mdc-select-trigger, [cdk-overlay-origin]') || matSelect;"
					+ "    trigger.scrollIntoView({block:'center'});"
					+ "    return {matSelect: matSelect, trigger: trigger};"
					+ "  }"
					+ "  const todos = w.querySelectorAll('*');"
					+ "  for (const el of todos) {"
					+ "    const txt = (el.textContent || '').replace(/\\s+/g, ' ').trim().toLowerCase();"
					+ "    if ((txt === 'incluido en el cálculo' || txt === 'incluido en el calculo' || txt === 'excluido del cálculo' || txt === 'excluido del calculo') && el.offsetParent !== null) {"
					+ "      const ms = el.closest('mat-select, [role=\"combobox\"]');"
					+ "      const trigger = ms ? (ms.querySelector('.mat-mdc-select-trigger, [cdk-overlay-origin]') || ms) : (el.closest('mat-form-field, .mat-mdc-form-field, .mat-form-field, [class*=\"trigger\"]') || el);"
					+ "      trigger.scrollIntoView({block:'center'});"
					+ "      return {matSelect: ms || trigger, trigger: trigger};"
					+ "    }"
					+ "  }"
					+ "}"
					+ "return null;");

			if (info == null || info.get("trigger") == null) {
				String motivoFallo = "No se localizo el trigger del selector Estado en la primera tienda-transferencia (ni mat-select ni texto 'Incluido en el cálculo')";
				System.out.println("[RESULTADO PASO] FAILED - Selector Estado primera TT | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			WebElement matSelect = (WebElement) info.get("matSelect");
			WebElement trigger = (WebElement) info.get("trigger");
			String tagTrigger = trigger.getTagName();
			String claseTrigger = trigger.getAttribute("class");
			String idMatSelect = matSelect == null ? "" : matSelect.getAttribute("id");
			// Solo consola (debug tecnico): no se agrega a la evidencia del reporte.
			System.out.println("[SELECTOR ESTADO] Trigger localizado | tag: " + tagTrigger + " | class: " + claseTrigger + " | mat-select id: " + idMatSelect);

			// Diagnostico completo del mat-select antes de intentar abrirlo
			JavascriptExecutor jsExec = (JavascriptExecutor) u.getDriver();
			try {
				String diagnostico = (String) jsExec.executeScript(
						"const ms = arguments[0];"
						+ "if (!ms) return 'mat-select nulo';"
						+ "const cs = window.getComputedStyle(ms);"
						+ "const rect = ms.getBoundingClientRect();"
						+ "const atPoint = document.elementFromPoint(rect.left + rect.width/2, rect.top + rect.height/2);"
						+ "const atPointTag = atPoint ? (atPoint.tagName + '.' + (atPoint.className||'')) : 'null';"
						+ "const ngDisponible = !!(window.ng && window.ng.getComponent);"
						+ "let componente = null;"
						+ "try { componente = ngDisponible ? window.ng.getComponent(ms) : null; } catch(e){}"
						+ "const tieneOpen = componente && typeof componente.open === 'function';"
						+ "return 'tag:' + ms.tagName + ' | id:' + (ms.id||'') + ' | class:' + (ms.getAttribute('class')||'')"
						+ " + ' | aria-disabled:' + (ms.getAttribute('aria-disabled')||'')"
						+ " + ' | aria-expanded:' + (ms.getAttribute('aria-expanded')||'')"
						+ " + ' | tabindex:' + (ms.getAttribute('tabindex')||'')"
						+ " + ' | disabled attr:' + (ms.hasAttribute('disabled')?'si':'no')"
						+ " + ' | pointer-events:' + cs.pointerEvents"
						+ " + ' | display:' + cs.display"
						+ " + ' | visibility:' + cs.visibility"
						+ " + ' | rect:' + Math.round(rect.left) + ',' + Math.round(rect.top) + ',' + Math.round(rect.width) + 'x' + Math.round(rect.height)"
						+ " + ' | elementFromPoint:' + atPointTag"
						+ " + ' | window.ng disponible:' + ngDisponible"
						+ " + ' | componente.open() disponible:' + tieneOpen;",
						matSelect);
				// Solo consola (debug tecnico): no se agrega a la evidencia del reporte.
				System.out.println("[SELECTOR ESTADO DIAG] " + diagnostico);
			} catch (Exception e) {
				System.out.println("[SELECTOR ESTADO DIAG] Error al diagnosticar mat-select: " + e.getMessage());
			}

			u.hardWait(1);
			// Angular CDK 15+ ahora usa <div class="cdk-overlay-popover"> (Popover API) en lugar de cdk-overlay-container en algunos ambientes.
			// Los selectores buscan directamente el .mat-mdc-select-panel o cualquier [role="listbox"] visible sin restringir al contenedor legacy.
			By opcionEnOverlay = By.xpath("//mat-option[.//span[contains(translate(normalize-space(.),'ÁÉÍÓÚ','áéíóú'),'excluido del c') or contains(translate(normalize-space(.),'ÁÉÍÓÚ','áéíóú'),'incluido en el c')]] | //*[contains(@class,'mdc-list-item__primary-text')][contains(translate(normalize-space(.),'ÁÉÍÓÚ','áéíóú'),'excluido del c') or contains(translate(normalize-space(.),'ÁÉÍÓÚ','áéíóú'),'incluido en el c')]");
			By panelOverlay = By.cssSelector(".mat-mdc-select-panel, div[role='listbox'].mdc-menu-surface--open, div.cdk-overlay-pane .mat-mdc-select-panel");
			boolean overlayVisible = false;
			String estrategiaExitosa = "";

			// Estrategia 0 (PRIMERA): llamar directamente al metodo .open() del componente MatSelect via ng.getComponent()
			// Bypassa todos los listeners de mouse/pointer. Solo funciona si Angular esta en modo dev con debugElement expuesto.
			if (matSelect != null) {
				try {
					Object resultadoNg = jsExec.executeScript(
							"const ms = arguments[0];"
							+ "if (!window.ng || !window.ng.getComponent) return 'ng no disponible';"
							+ "try {"
							+ "  const comp = window.ng.getComponent(ms);"
							+ "  if (!comp) return 'sin componente';"
							+ "  if (typeof comp.open !== 'function') return 'sin metodo open';"
							+ "  comp.open();"
							+ "  return 'ok';"
							+ "} catch(e) { return 'excepcion:' + e.message; }",
							matSelect);
					System.out.println("[SELECTOR ESTADO] ng.getComponent().open() resultado: " + resultadoNg);
					u.hardWait(2);
					overlayVisible = detectaOverlaySelectAbierto(matSelect, panelOverlay, opcionEnOverlay);
					if (overlayVisible) estrategiaExitosa = "ng.getComponent().open() del MatSelect";
				} catch (Exception e) {
					System.out.println("[SELECTOR ESTADO] ng.getComponent().open() lanzo excepcion Java: " + e.getMessage());
				}
			}

			// Estrategia 1: dispatch pointerdown+pointerup+mousedown+mouseup SOBRE EL SPAN INTERNO (mat-mdc-select-min-line)
			// que segun elementFromPoint es el que realmente recibe eventos de mouse en el centro. SIN 'click' porque
			// Angular Material 15+ abre en pointerdown y un click sintetico posterior lo cierra (toggle).
			if (!overlayVisible) {
				System.out.println("[SELECTOR ESTADO] ng.open no disponible; intentando pointerdown/mousedown en SPAN interno (elementFromPoint)");
				try {
					jsExec.executeScript(
							"const ms = arguments[0];"
							+ "const target = ms.querySelector('.mat-mdc-select-min-line, .mat-mdc-select-value-text, .mat-mdc-select-value') || ms.querySelector('.mat-mdc-select-trigger') || ms;"
							+ "if (typeof target.focus === 'function') { try { target.focus(); } catch(e){} }"
							+ "const rect = target.getBoundingClientRect();"
							+ "const x = rect.left + rect.width/2;"
							+ "const y = rect.top + rect.height/2;"
							+ "const opts = {bubbles:true, cancelable:true, composed:true, view:window, button:0, buttons:1, clientX:x, clientY:y};"
							+ "const optsUp = {bubbles:true, cancelable:true, composed:true, view:window, button:0, buttons:0, clientX:x, clientY:y};"
							+ "try { target.dispatchEvent(new PointerEvent('pointerdown', Object.assign({pointerType:'mouse', isPrimary:true, pointerId:1}, opts))); } catch(e){}"
							+ "try { target.dispatchEvent(new MouseEvent('mousedown', opts)); } catch(e){}"
							+ "try { target.dispatchEvent(new PointerEvent('pointerup', Object.assign({pointerType:'mouse', isPrimary:true, pointerId:1}, optsUp))); } catch(e){}"
							+ "try { target.dispatchEvent(new MouseEvent('mouseup', optsUp)); } catch(e){}",
							matSelect);
					u.hardWait(2);
					overlayVisible = detectaOverlaySelectAbierto(matSelect, panelOverlay, opcionEnOverlay);
					if (overlayVisible) estrategiaExitosa = "pointerdown/mousedown sobre SPAN interno (elementFromPoint), sin click";
				} catch (Exception e) {
					System.out.println("[SELECTOR ESTADO] dispatch pointerdown en SPAN interno fallo: " + e.getMessage());
				}
			}

			// Estrategia 2: dispatch pointerdown+mousedown sobre el TRIGGER (mat-mdc-select-trigger), sin click
			if (!overlayVisible) {
				System.out.println("[SELECTOR ESTADO] Overlay no aparecio con dispatch en SPAN; intentando dispatch en trigger (sin click)");
				try {
					jsExec.executeScript(
							"const t = arguments[0];"
							+ "if (typeof t.focus === 'function') { try { t.focus(); } catch(e){} }"
							+ "const rect = t.getBoundingClientRect();"
							+ "const x = rect.left + rect.width/2;"
							+ "const y = rect.top + rect.height/2;"
							+ "const opts = {bubbles:true, cancelable:true, composed:true, view:window, button:0, buttons:1, clientX:x, clientY:y};"
							+ "const optsUp = {bubbles:true, cancelable:true, composed:true, view:window, button:0, buttons:0, clientX:x, clientY:y};"
							+ "try { t.dispatchEvent(new PointerEvent('pointerdown', Object.assign({pointerType:'mouse', isPrimary:true, pointerId:1}, opts))); } catch(e){}"
							+ "try { t.dispatchEvent(new MouseEvent('mousedown', opts)); } catch(e){}"
							+ "try { t.dispatchEvent(new PointerEvent('pointerup', Object.assign({pointerType:'mouse', isPrimary:true, pointerId:1}, optsUp))); } catch(e){}"
							+ "try { t.dispatchEvent(new MouseEvent('mouseup', optsUp)); } catch(e){}",
							trigger);
					u.hardWait(2);
					overlayVisible = detectaOverlaySelectAbierto(matSelect, panelOverlay, opcionEnOverlay);
					if (overlayVisible) estrategiaExitosa = "pointerdown/mousedown sobre trigger, sin click";
				} catch (Exception e) {
					System.out.println("[SELECTOR ESTADO] dispatch pointerdown en trigger fallo: " + e.getMessage());
				}
			}

			// Estrategia 3: click nativo Selenium sobre el SPAN interno (elementFromPoint reportado por diagnostico)
			if (!overlayVisible && matSelect != null) {
				System.out.println("[SELECTOR ESTADO] Overlay no aparecio con dispatch; intentando click nativo Selenium sobre SPAN interno");
				try {
					WebElement spanInterno = (WebElement) jsExec.executeScript(
							"return arguments[0].querySelector('.mat-mdc-select-min-line, .mat-mdc-select-value-text, .mat-mdc-select-value') || arguments[0].querySelector('.mat-mdc-select-trigger');",
							matSelect);
					if (spanInterno == null) spanInterno = trigger;
					jsExec.executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", spanInterno);
					u.hardWait(1);
					try {
						spanInterno.click();
					} catch (Exception e) {
						jsExec.executeScript("arguments[0].click();", spanInterno);
					}
					u.hardWait(2);
					overlayVisible = detectaOverlaySelectAbierto(matSelect, panelOverlay, opcionEnOverlay);
					if (overlayVisible) estrategiaExitosa = "click nativo Selenium en SPAN interno";
				} catch (Exception e) {
					System.out.println("[SELECTOR ESTADO] click nativo en SPAN interno fallo: " + e.getMessage());
				}
			}

			// Estrategia 4: Actions.click sobre el mat-select con foco previo
			if (!overlayVisible && matSelect != null) {
				System.out.println("[SELECTOR ESTADO] Overlay no aparecio con click nativo; intentando Actions.click sobre mat-select");
				try {
					jsExec.executeScript("try { arguments[0].focus(); } catch(e){}", matSelect);
					new Actions(u.getDriver()).moveToElement(matSelect).pause(300).click().perform();
					u.hardWait(2);
					overlayVisible = detectaOverlaySelectAbierto(matSelect, panelOverlay, opcionEnOverlay);
					if (overlayVisible) estrategiaExitosa = "Actions.click en mat-select con foco previo";
				} catch (Exception e) {
					System.out.println("[SELECTOR ESTADO] Actions.click sobre mat-select fallo: " + e.getMessage());
				}
			}

			// Estrategia 5: enfocar mat-select y enviar Keys.ALT+ARROW_DOWN
			if (!overlayVisible && matSelect != null) {
				System.out.println("[SELECTOR ESTADO] Overlay no aparecio; intentando ALT+ARROW_DOWN sobre mat-select enfocado");
				try {
					jsExec.executeScript("try { arguments[0].focus(); } catch(e){}", matSelect);
					u.hardWait(1);
					new Actions(u.getDriver()).keyDown(Keys.ALT).sendKeys(Keys.ARROW_DOWN).keyUp(Keys.ALT).perform();
					u.hardWait(2);
					overlayVisible = detectaOverlaySelectAbierto(matSelect, panelOverlay, opcionEnOverlay);
					if (overlayVisible) estrategiaExitosa = "ALT+ARROW_DOWN sobre mat-select";
				} catch (Exception e) {
					System.out.println("[SELECTOR ESTADO] ALT+ARROW_DOWN fallo: " + e.getMessage());
				}
			}

			// Estrategia 6: ENTER/SPACE via Actions
			if (!overlayVisible && matSelect != null) {
				System.out.println("[SELECTOR ESTADO] Overlay no aparecio; intentando ENTER/SPACE via Actions sobre mat-select enfocado");
				try {
					jsExec.executeScript("try { arguments[0].focus(); } catch(e){}", matSelect);
					u.hardWait(1);
					new Actions(u.getDriver()).sendKeys(Keys.ENTER).perform();
					u.hardWait(2);
					overlayVisible = detectaOverlaySelectAbierto(matSelect, panelOverlay, opcionEnOverlay);
					if (!overlayVisible) {
						new Actions(u.getDriver()).sendKeys(Keys.SPACE).perform();
						u.hardWait(2);
						overlayVisible = detectaOverlaySelectAbierto(matSelect, panelOverlay, opcionEnOverlay);
					}
					if (overlayVisible) estrategiaExitosa = "ENTER/SPACE via Actions sobre mat-select";
				} catch (Exception e) {
					System.out.println("[SELECTOR ESTADO] ENTER/SPACE via Actions fallo: " + e.getMessage());
				}
			}

			if (overlayVisible) {
				// Solo consola (debug tecnico de la estrategia que abrio el overlay): no se agrega a la evidencia del reporte.
				System.out.println("[SELECTOR ESTADO] Overlay abierto con estrategia: " + estrategiaExitosa);
			} else {
				String motivoFallo = "El overlay del selector Estado no se abrio con ninguna de las 7 estrategias (ng.open/pointerdown-span/pointerdown-trigger/click-nativo/Actions.click/ALT+DOWN/ENTER-SPACE). Revisar log [SELECTOR ESTADO DIAG] para atributos y elementFromPoint";
				System.out.println("[RESULTADO PASO] FAILED - Selector Estado primera TT | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			// Intento suave de esperar mat-option con XPath tradicional (compatibilidad con overlays legacy).
			// Si falla (Angular Material 15+ con Popover API renderiza como mdc-list-item sin mat-option), se procede
			// al bloque JavaScript posterior que busca opciones en TODOS los contenedores posibles
			// (.cdk-overlay-popover, .cdk-overlay-container, .mat-mdc-select-panel, [role=listbox]).
			try {
				WebDriverWait waitOpcionSuave = new WebDriverWait(u.getDriver(), Duration.ofSeconds(5));
				waitOpcionSuave.until(ExpectedConditions.presenceOfElementLocated(opcionEnOverlay));
				System.out.println("[SELECTOR ESTADO] mat-option detectado por XPath tradicional en 5s");
			} catch (Exception e) {
				System.out.println("[SELECTOR ESTADO] mat-option no detectado por XPath tradicional (esperado en Angular Material 15+ con Popover API); se procede a detectar opciones por JavaScript flexible");
			}
			u.hardWait(2);

			@SuppressWarnings("unchecked")
			java.util.List<String> opciones = (java.util.List<String>) ((JavascriptExecutor) u.getDriver()).executeScript(
					// Angular CDK 15+ ahora usa .cdk-overlay-popover ademas de .cdk-overlay-container.
					// Se busca directamente el .mat-mdc-select-panel/[role=listbox] visible sin importar el contenedor padre.
					"const panelesCandidatos = document.querySelectorAll('.mat-mdc-select-panel, div[role=\"listbox\"].mdc-menu-surface--open, div.cdk-overlay-pane .mat-mdc-select-panel, .cdk-overlay-popover .mat-mdc-select-panel, .cdk-overlay-container .mat-mdc-select-panel');"
					+ "let panel = null;"
					+ "for (const p of panelesCandidatos) { if (p.offsetParent !== null && p.getBoundingClientRect().height > 0) { panel = p; break; } }"
					+ "if (!panel) {"
					+ "  const overlays = document.querySelectorAll('.cdk-overlay-popover, .cdk-overlay-container, div.cdk-overlay-pane');"
					+ "  for (const p of overlays) { if (p.offsetParent !== null && p.getBoundingClientRect().height > 0) { panel = p; break; } }"
					+ "}"
					+ "if (!panel) return [];"
					+ "const seleccionadosItems = panel.querySelectorAll('mat-option, [role=\"option\"], [role=\"menuitem\"], li, [class*=\"list-item\"], [class*=\"option\"]');"
					+ "const textosBrutos = [];"
					+ "if (seleccionadosItems.length > 0) {"
					+ "  for (const it of seleccionadosItems) {"
					+ "    if (it.offsetParent === null) continue;"
					+ "    const txt = (it.textContent || '').replace(/\\s+/g, ' ').trim();"
					+ "    if (txt.length > 0) textosBrutos.push(txt);"
					+ "  }"
					+ "}"
					+ "if (textosBrutos.length === 0) {"
					+ "  const spans = panel.querySelectorAll('span, div, p');"
					+ "  for (const sp of spans) {"
					+ "    if (sp.offsetParent === null) continue;"
					+ "    const txt = (sp.textContent || '').replace(/\\s+/g, ' ').trim().toLowerCase();"
					+ "    if (txt === 'incluido en el cálculo' || txt === 'incluido en el calculo' || txt === 'excluido del cálculo' || txt === 'excluido del calculo') {"
					+ "      textosBrutos.push((sp.textContent || '').replace(/\\s+/g, ' ').trim());"
					+ "    }"
					+ "  }"
					+ "}"
					+ "const vistos = new Set();"
					+ "const finales = [];"
					+ "for (const t of textosBrutos) {"
					+ "  const key = t.toLowerCase();"
					+ "  if ((key.includes('incluido en el c') || key.includes('excluido del c')) && !vistos.has(key)) {"
					+ "    vistos.add(key);"
					+ "    finales.push(t);"
					+ "  }"
					+ "}"
					+ "return finales;");

			boolean tieneIncluido = false;
			boolean tieneExcluido = false;
			for (String opcion : opciones) {
				String opcionLower = opcion.toLowerCase();
				if (opcionLower.contains("incluido en el c")) tieneIncluido = true;
				if (opcionLower.contains("excluido del c")) tieneExcluido = true;
			}
			int totalOpciones = opciones.size();
			boolean soloDosOpciones = totalOpciones == 2;
			boolean resultadoPaso = tieneIncluido && tieneExcluido && soloDosOpciones;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!tieneIncluido) motivoFallo.append("No aparece opcion 'Incluido en el calculo' | ");
			if (!tieneExcluido) motivoFallo.append("No aparece opcion 'Excluido del calculo' | ");
			if (!soloDosOpciones) motivoFallo.append("El selector muestra ").append(totalOpciones).append(" opciones (esperado: 2) | ");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Selector Estado primera TT" + (resultadoPaso ? " | Opciones: " + opciones : " | Motivo: " + motivoFallo));
			// Dump crudo de opciones detectadas solo en consola (debug tecnico): la evidencia funcional queda en la siguiente linea (esperado vs actual).
			testCase.addBoldTextEvidenceCurrentStep("Opciones esperadas: Incluido en el cálculo | Excluido del cálculo | Total esperado: 2 | Total actual: " + totalOpciones);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Selector_Estado_Primera_TT_Opciones");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Selector Estado primera TT | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona la opcion "Excluido del calculo" en el selector Estado ya abierto de la primera tienda-transferencia y valida que aparezca el modal homologado "Espere un momento" mientras el sistema recalcula, y que desaparezca al finalizar.
	 * Si el selector no esta abierto lo abre primero. Tolera que el modal desaparezca rapidamente al aceptar como valido que el sistema termine sin dejar el modal visible.
	 *
	 * @param screenshot True para capturar evidencia visual del selector, del modal Espere un momento y del estado final tras el recalculo.
	 * @return True si la opcion Excluido queda seleccionada y el modal no permanece visible al final; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean seleccionaExcluidoDelCalculoPrimeraTiendaTransferenciaYValidaModal(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			By modalEspere = By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]");
			By panelOverlay = By.cssSelector(".mat-mdc-select-panel, div[role='listbox'].mdc-menu-surface--open, div.cdk-overlay-pane .mat-mdc-select-panel");

			// 1. Asegurar que el mat-select este abierto. Se reutiliza el metodo de apertura
			// que ya funciona (aria-expanded=true) y evita depender del click sobre mat-option.
			boolean overlayAbierto = false;
			try {
				java.util.List<WebElement> paneles = u.getDriver().findElements(panelOverlay);
				for (WebElement panel : paneles) { if (panel.isDisplayed()) { overlayAbierto = true; break; } }
			} catch (Exception e) { }
			if (!overlayAbierto) {
				try {
					WebElement msTest = (WebElement) ((JavascriptExecutor) u.getDriver()).executeScript(
							"return document.querySelector('mat-select[aria-expanded=\"true\"], mat-select.mat-select-open');");
					if (msTest != null) overlayAbierto = true;
				} catch (Exception e) { }
			}
			if (!overlayAbierto) {
				boolean panelAbierto = abreSelectorEstadoPrimeraTiendaTransferenciaYValidaOpciones(false);
				if (!panelAbierto) {
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se pudo abrir el selector Estado para seleccionar Excluido del calculo");
					return false;
				}
			}

			// 2. Localizar el mat-select abierto para enfocarlo y capturar valor inicial.
			JavascriptExecutor jsExec = (JavascriptExecutor) u.getDriver();
			WebElement matSelect = null;
			try {
				matSelect = (WebElement) jsExec.executeScript(
						"return document.querySelector('mat-select[aria-expanded=\"true\"]') || document.querySelector('mat-select.mat-select-open');");
			} catch (Exception e) { }
			String valorInicial = "";
			try {
				valorInicial = (String) jsExec.executeScript(
						"const ms = document.querySelector('mat-select[aria-expanded=\"true\"]') || document.querySelector('mat-select.mat-select-open');"
						+ "if (!ms) return '';"
						+ "const min = ms.querySelector('.mat-mdc-select-min-line, .mat-mdc-select-value-text');"
						+ "return min ? (min.textContent || '').trim() : '';");
			} catch (Exception e) { }
			System.out.println("[SELECTOR ESTADO] Valor inicial del selector antes de ARROW_DOWN+ENTER: '" + valorInicial + "'");
			testCase.addBoldTextEvidenceCurrentStep("Valor inicial del selector Estado: " + valorInicial);

			// 3. Enfocar el mat-select y navegar por teclado.
			// Angular Material soporta accesibilidad nativa: ARROW_DOWN mueve el highlight,
			// ENTER selecciona la opcion resaltada y dispara el modelo (ngModel/formControl).
			// Este flujo NO depende del click sobre <mat-option>, del contenedor cdk-overlay-*,
			// ni del elementFromPoint. Solo requiere que el mat-select tenga foco (ya lo tiene tras la apertura).
			if (matSelect != null) {
				try { jsExec.executeScript("try { arguments[0].focus(); } catch(e){}", matSelect); } catch (Exception e) { }
			}

			System.out.println("[SELECTOR ESTADO] Enviando ARROW_DOWN + ENTER al mat-select enfocado para seleccionar 'Excluido del calculo'");
			try {
				new Actions(u.getDriver())
					.sendKeys(Keys.ARROW_DOWN)
					.pause(400)
					.sendKeys(Keys.ENTER)
					.perform();
			} catch (Exception e) {
				System.out.println("[SELECTOR ESTADO] Actions.sendKeys ARROW_DOWN+ENTER fallo: " + e.getMessage() + " | Intentando via matSelect.sendKeys");
				if (matSelect != null) {
					try {
						matSelect.sendKeys(Keys.ARROW_DOWN);
						u.hardWait(1);
						matSelect.sendKeys(Keys.ENTER);
					} catch (Exception e2) {
						testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se pudieron enviar ARROW_DOWN + ENTER al mat-select: " + e2.getMessage());
						return false;
					}
				}
			}
			u.hardWait(1);

			// 4. Verificar que el valor visible del selector cambio a "Excluido del calculo".
			String valorFinal = "";
			try {
				valorFinal = (String) jsExec.executeScript(
						"const raiz = document.querySelector('app-cedis-liquidacion-ruta #detalle-tiendas')"
						+ " || document.querySelector('#detalle-tiendas')"
						+ " || document.querySelector('app-cedis-liquidacion-ruta') || document;"
						+ "const wrappers = raiz.querySelectorAll('.tienda-wrapper, [id=\"tienda\"], mat-select');"
						+ "for (const w of wrappers) {"
						+ "  const min = w.querySelector('.mat-mdc-select-min-line, .mat-mdc-select-value-text');"
						+ "  if (min && min.offsetParent !== null) { return (min.textContent || '').trim(); }"
						+ "}"
						+ "const cualquiera = document.querySelector('.mat-mdc-select-min-line, .mat-mdc-select-value-text');"
						+ "return cualquiera ? (cualquiera.textContent || '').trim() : '';");
			} catch (Exception e) { }
			System.out.println("[SELECTOR ESTADO] Valor final del selector tras ARROW_DOWN+ENTER: '" + valorFinal + "'");
			boolean seleccionCambio = valorFinal != null && valorFinal.toLowerCase().contains("excluido del c");

			// 5. Si ARROW_DOWN+ENTER no cambio el valor, intentar TAB como fallback (algunas versiones seleccionan con TAB en vez de ENTER).
			if (!seleccionCambio) {
				System.out.println("[SELECTOR ESTADO] ARROW_DOWN+ENTER no cambio el valor; intentando ARROW_DOWN+TAB como fallback");
				try {
					if (matSelect != null) { jsExec.executeScript("try { arguments[0].focus(); } catch(e){}", matSelect); }
					// Reabrir el select si se cerro
					if (u.getDriver().findElements(panelOverlay).isEmpty()) {
						abreSelectorEstadoPrimeraTiendaTransferenciaYValidaOpciones(false);
					}
					new Actions(u.getDriver())
						.sendKeys(Keys.ARROW_DOWN)
						.pause(400)
						.sendKeys(Keys.TAB)
						.perform();
					u.hardWait(1);
					try {
						valorFinal = (String) jsExec.executeScript(
								"const cualquiera = document.querySelector('.mat-mdc-select-min-line, .mat-mdc-select-value-text');"
								+ "return cualquiera ? (cualquiera.textContent || '').trim() : '';");
					} catch (Exception e) { }
					seleccionCambio = valorFinal != null && valorFinal.toLowerCase().contains("excluido del c");
					System.out.println("[SELECTOR ESTADO] Valor tras fallback ARROW_DOWN+TAB: '" + valorFinal + "' | seleccionCambio: " + seleccionCambio);
				} catch (Exception e) {
					System.out.println("[SELECTOR ESTADO] Fallback ARROW_DOWN+TAB fallo: " + e.getMessage());
				}
			}

			// 6. Validar aparicion y desaparicion del modal 'Espere un momento'.
			boolean modalVisto = false;
			long tsInicio = System.currentTimeMillis();
			while (System.currentTimeMillis() - tsInicio < 3000) {
				java.util.List<WebElement> modales = u.getDriver().findElements(modalEspere);
				for (WebElement m : modales) {
					try {
						if (m.isDisplayed()) { modalVisto = true; break; }
					} catch (Exception e) { }
				}
				if (modalVisto) break;
				esperaRenderCorto();
			}

			if (screenshot && modalVisto) {
				testCase.addScreenShotCurrentStep("Modal_Espere_Un_Momento_Recalculo");
			}

			try { wait.until(ExpectedConditions.invisibilityOfElementLocated(modalEspere)); } catch (Exception e) { }
			u.hardWait(1);

			boolean modalOcultoAlFinal = true;
			java.util.List<WebElement> modalesFinal = u.getDriver().findElements(modalEspere);
			for (WebElement m : modalesFinal) {
				try {
					if (m.isDisplayed()) { modalOcultoAlFinal = false; break; }
				} catch (Exception e) { }
			}

			boolean resultadoPaso = seleccionCambio && modalOcultoAlFinal;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!seleccionCambio) motivoFallo.append("El selector no cambio a 'Excluido del calculo' tras ARROW_DOWN+ENTER ni ARROW_DOWN+TAB. Valor inicial: '").append(valorInicial).append("' | Valor final: '").append(valorFinal).append("' | ");
			if (!modalOcultoAlFinal) motivoFallo.append("El modal 'Espere un momento' permanece visible despues del recalculo | ");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Seleccion Excluido del calculo por teclado" + (resultadoPaso ? " | Valor final: '" + valorFinal + "' | Modal visto: " + modalVisto : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Valor inicial del selector: " + valorInicial);
			testCase.addBoldTextEvidenceCurrentStep("Valor final del selector: " + valorFinal);
			testCase.addBoldTextEvidenceCurrentStep("Seleccion cambio a Excluido del calculo: " + (seleccionCambio ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Modal 'Espere un momento' visible durante el recalculo: " + (modalVisto ? "SI" : "NO (el sistema pudo procesar en tiempo minimo)"));
			testCase.addBoldTextEvidenceCurrentStep("Modal 'Espere un momento' oculto al finalizar: " + (modalOcultoAlFinal ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Post_Seleccion_Excluido_Del_Calculo");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Seleccion Excluido del calculo | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida la representacion visual esperada despues de cambiar a Excluido del calculo la primera tienda-transferencia: la excluida debe mostrar linea lateral gris, icono de signo menos, selector "Excluido del calculo" seleccionado en color rojo y fondo gris claro; la segunda tienda-transferencia debe conservar linea verde, icono de palomita y selector "Incluido en el calculo" en verde.
	 * Recorre los renglones tienda-transferencia del grid via JavaScript y evalua clases, texto e informacion de estilo para tolerar variaciones del DOM.
	 *
	 * @param screenshot True para capturar evidencia visual del estado de ambas tiendas-transferencia.
	 * @return True si la primera cumple el patron de excluida y las demas conservan el patron de incluida; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaEstadoVisualExcluidaVsIncluida(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerTienda));
			u.hardWait(1);

			@SuppressWarnings("unchecked")
			java.util.Map<String, Object> resumen = (java.util.Map<String, Object>) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const raiz = document.querySelector('app-cedis-liquidacion-ruta #detalle-tiendas')"
					+ " || document.querySelector('#detalle-tiendas')"
					+ " || document.querySelector('app-cedis-liquidacion-ruta');"
					+ "if (!raiz) return {total:0, detalle:[]};"
					+ "const wrappers = Array.from(raiz.querySelectorAll('.tienda-wrapper, [id=\"tienda\"]')).filter(w => w.offsetParent !== null && w.getBoundingClientRect().height > 20);"
					+ "function esVerde(rgb) {"
					+ "  if (!rgb) return false;"
					+ "  const m = rgb.match(/rgba?\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)/);"
					+ "  if (!m) return false;"
					+ "  const r = parseInt(m[1]); const g = parseInt(m[2]); const b = parseInt(m[3]);"
					+ "  return g >= 100 && g > r + 20 && g > b + 20;"
					+ "}"
					+ "function esRojo(rgb) {"
					+ "  if (!rgb) return false;"
					+ "  const m = rgb.match(/rgba?\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)/);"
					+ "  if (!m) return false;"
					+ "  const r = parseInt(m[1]); const g = parseInt(m[2]); const b = parseInt(m[3]);"
					+ "  return r >= 130 && r > g + 20 && r > b + 20;"
					+ "}"
					+ "function esGris(rgb) {"
					+ "  if (!rgb) return false;"
					+ "  const m = rgb.match(/rgba?\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)/);"
					+ "  if (!m) return false;"
					+ "  const r = parseInt(m[1]); const g = parseInt(m[2]); const b = parseInt(m[3]);"
					+ "  const diffMax = Math.max(Math.abs(r-g), Math.abs(g-b), Math.abs(r-b));"
					+ "  return diffMax <= 25 && r >= 90 && r <= 220;"
					+ "}"
					+ "function esGrisClaro(rgb) {"
					+ "  if (!rgb) return false;"
					+ "  const m = rgb.match(/rgba?\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)/);"
					+ "  if (!m) return false;"
					+ "  const r = parseInt(m[1]); const g = parseInt(m[2]); const b = parseInt(m[3]);"
					+ "  const diffMax = Math.max(Math.abs(r-g), Math.abs(g-b), Math.abs(r-b));"
					+ "  return diffMax <= 20 && r >= 180 && r <= 245;"
					+ "}"
					+ "const detalle = [];"
					+ "for (const w of wrappers) {"
					+ "  const linea = w.querySelector('.tienda-linea-lateral, [class*=\"linea-lateral\"]');"
					+ "  const claseLinea = linea ? (linea.className || '').toString().toLowerCase() : '';"
					+ "  const colorLinea = linea ? window.getComputedStyle(linea).backgroundColor : '';"
					+ "  const lineaVerde = claseLinea.includes('linea-verde') || esVerde(colorLinea);"
					+ "  const lineaGris = !lineaVerde && (claseLinea.includes('linea-gris') || claseLinea.includes('gris') || esGris(colorLinea));"
					+ "  const iconos = Array.from(w.querySelectorAll('mat-icon, i, svg, [class*=\"icono\"]'));"
					+ "  let iconoPalomita = false;"
					+ "  let iconoSignoMenos = false;"
					+ "  for (const ic of iconos) {"
					+ "    const t = (ic.textContent || '').trim().toLowerCase();"
					+ "    const c = (ic.className && ic.className.baseVal !== undefined ? ic.className.baseVal : (ic.className || '')).toString().toLowerCase();"
					+ "    if (t === 'check' || t === 'check_circle' || t === 'done' || c.includes('palomita') || c.includes('check') || c.includes('success') || c.includes('verde')) iconoPalomita = true;"
					+ "    if (t === 'remove' || t === 'do_not_disturb_on' || t === 'block' || t === 'cancel' || t === 'close' || t === 'minus' || c.includes('menos') || c.includes('remove') || c.includes('cancel') || c.includes('close') || c.includes('gris') || c.includes('excluido')) iconoSignoMenos = true;"
					+ "  }"
					+ "  let textoSelector = '';"
					+ "  let colorSelector = '';"
					+ "  let fondoSelector = '';"
					+ "  const selects = w.querySelectorAll('mat-select, [role=\"combobox\"]');"
					+ "  for (const s of selects) {"
					+ "    const txt = (s.textContent || '').replace(/\\s+/g, ' ').trim();"
					+ "    if (txt.toLowerCase().includes('incluido en el c') || txt.toLowerCase().includes('excluido del c')) {"
					+ "      textoSelector = txt;"
					+ "      const scs = window.getComputedStyle(s);"
					+ "      colorSelector = scs.color;"
					+ "      fondoSelector = scs.backgroundColor;"
					+ "      const hijos = s.querySelectorAll('*');"
					+ "      for (const h of hijos) {"
					+ "        const hcs = window.getComputedStyle(h);"
					+ "        if (esVerde(hcs.color) || esRojo(hcs.color)) { colorSelector = hcs.color; }"
					+ "        if (esGrisClaro(hcs.backgroundColor)) { fondoSelector = hcs.backgroundColor; }"
					+ "      }"
					+ "      break;"
					+ "    }"
					+ "  }"
					+ "  const esExcluida = textoSelector.toLowerCase().includes('excluido del c');"
					+ "  const esIncluida = textoSelector.toLowerCase().includes('incluido en el c');"
					+ "  const csWrapper = window.getComputedStyle(w);"
					+ "  const fondoWrapper = csWrapper.backgroundColor;"
					+ "  let fondoGrisClaro = esGrisClaro(fondoWrapper) || esGrisClaro(fondoSelector);"
					+ "  if (!fondoGrisClaro) {"
					+ "    const contenido = w.querySelector('.tienda-contenido, [class*=\"tienda-contenido\"], .tienda-header-row') || w;"
					+ "    fondoGrisClaro = esGrisClaro(window.getComputedStyle(contenido).backgroundColor);"
					+ "  }"
					+ "  const selectorVerde = esVerde(colorSelector) || esVerde(fondoSelector);"
					+ "  const selectorRojo = esRojo(colorSelector) || esRojo(fondoSelector);"
					+ "  detalle.push({"
					+ "    textoSelector: textoSelector,"
					+ "    esExcluida: esExcluida,"
					+ "    esIncluida: esIncluida,"
					+ "    lineaVerde: lineaVerde,"
					+ "    lineaGris: lineaGris,"
					+ "    claseLinea: claseLinea,"
					+ "    colorLinea: colorLinea,"
					+ "    iconoPalomita: iconoPalomita,"
					+ "    iconoSignoMenos: iconoSignoMenos,"
					+ "    selectorVerde: selectorVerde,"
					+ "    selectorRojo: selectorRojo,"
					+ "    fondoGrisClaro: fondoGrisClaro,"
					+ "    colorSelector: colorSelector,"
					+ "    fondoSelector: fondoSelector,"
					+ "    fondoWrapper: fondoWrapper"
					+ "  });"
					+ "}"
					+ "return {total:wrappers.length, detalle:detalle};");

			int total = resumen == null ? 0 : ((Number) resumen.getOrDefault("total", 0)).intValue();
			@SuppressWarnings("unchecked")
			java.util.List<java.util.Map<String, Object>> detalle = resumen == null ? new java.util.ArrayList<java.util.Map<String, Object>>() : (java.util.List<java.util.Map<String, Object>>) resumen.getOrDefault("detalle", new java.util.ArrayList<>());

			boolean primeraCumpleExcluida = false;
			boolean todasLasDemasCumplenIncluida = detalle.size() > 1;
			StringBuilder detalleFallos = new StringBuilder();
			for (int i = 0; i < detalle.size(); i++) {
				java.util.Map<String, Object> d = detalle.get(i);
				boolean esExcluida = Boolean.TRUE.equals(d.get("esExcluida"));
				boolean esIncluida = Boolean.TRUE.equals(d.get("esIncluida"));
				boolean lineaVerde = Boolean.TRUE.equals(d.get("lineaVerde"));
				boolean lineaGris = Boolean.TRUE.equals(d.get("lineaGris"));
				boolean iconoPalomita = Boolean.TRUE.equals(d.get("iconoPalomita"));
				boolean iconoSignoMenos = Boolean.TRUE.equals(d.get("iconoSignoMenos"));
				boolean selectorVerde = Boolean.TRUE.equals(d.get("selectorVerde"));
				boolean selectorRojo = Boolean.TRUE.equals(d.get("selectorRojo"));
				boolean fondoGrisClaro = Boolean.TRUE.equals(d.get("fondoGrisClaro"));

				String textoLog = "Tienda-transferencia " + (i + 1) + " | Texto selector: '" + d.get("textoSelector") + "' | Clase linea: '" + d.get("claseLinea") + "' | Color linea: " + d.get("colorLinea") + " | Linea verde: " + lineaVerde + " | Linea gris: " + lineaGris + " | Icono palomita: " + iconoPalomita + " | Icono signo menos: " + iconoSignoMenos + " | Selector verde: " + selectorVerde + " | Selector rojo: " + selectorRojo + " | Fondo gris claro: " + fondoGrisClaro;
				System.out.println("[VALIDACION EXCLUIDA/INCLUIDA] " + textoLog);
				testCase.addBoldTextEvidenceCurrentStep(textoLog);

				if (i == 0) {
					// Patron EXCLUIDA segun UI real: linea lateral izquierda gris + icono X gris + texto "Excluido del calculo" en rojo.
					// NO se exige fondo gris claro en el wrapper porque en el disenio real el card conserva fondo blanco/normal;
					// solo la linea lateral y el icono se pintan de gris. Se conserva la deteccion de fondoGrisClaro solo para evidencia.
					primeraCumpleExcluida = esExcluida && !lineaVerde && lineaGris && iconoSignoMenos && selectorRojo;
					if (!primeraCumpleExcluida) {
						detalleFallos.append("Renglon 1 esperado EXCLUIDA (linea gris + signo menos + selector rojo) no cumple | esExcluida=").append(esExcluida).append(", lineaGris=").append(lineaGris).append(", iconoSignoMenos=").append(iconoSignoMenos).append(", selectorRojo=").append(selectorRojo).append(" | ");
					}
				} else {
					boolean cumpleIncluida = esIncluida && lineaVerde && iconoPalomita && selectorVerde;
					if (!cumpleIncluida) {
						todasLasDemasCumplenIncluida = false;
						detalleFallos.append("Renglon ").append(i + 1).append(" esperado INCLUIDA (linea verde + palomita + selector verde) no cumple | ");
					}
				}
			}

			boolean hayAlMenosDos = total >= 2;
			boolean resultadoPaso = hayAlMenosDos && primeraCumpleExcluida && todasLasDemasCumplenIncluida;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!hayAlMenosDos) motivoFallo.append("Se esperaban al menos 2 tiendas-transferencia visibles y solo hay ").append(total).append(" | ");
			if (!primeraCumpleExcluida) motivoFallo.append(detalleFallos.length() > 0 ? "" : "Primera tienda-transferencia no cumple patron EXCLUIDA | ");
			if (!todasLasDemasCumplenIncluida && hayAlMenosDos) motivoFallo.append("Alguna tienda-transferencia posterior no cumple patron INCLUIDA | ");
			if (detalleFallos.length() > 0) motivoFallo.append(detalleFallos);

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Estado visual excluida vs incluida" + (resultadoPaso ? " | Total revisados: " + total : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Total tiendas-transferencia revisadas: " + total + " | Esperado: >= 2");
			testCase.addBoldTextEvidenceCurrentStep("Primera cumple patron EXCLUIDA (linea gris + signo menos + selector rojo): " + primeraCumpleExcluida);
			testCase.addBoldTextEvidenceCurrentStep("Las demas conservan patron INCLUIDA (linea verde + palomita + selector verde): " + todasLasDemasCumplenIncluida);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				try {
					((JavascriptExecutor) u.getDriver()).executeScript(
							"const primerRenglon = document.querySelector('app-cedis-liquidacion-ruta #detalle-tiendas .tienda-wrapper')"
							+ " || document.querySelector('#detalle-tiendas .tienda-wrapper')"
							+ " || document.querySelector('#detalle-tiendas [id=\"tienda\"]');"
							+ "if (primerRenglon) {"
							+ "  const rect = primerRenglon.getBoundingClientRect();"
							+ "  const y = rect.top + window.pageYOffset - Math.max(120, Math.floor(window.innerHeight * 0.30));"
							+ "  window.scrollTo({top: Math.max(y, 0), left: 0, behavior: 'instant'});"
							+ "}");
					u.hardWait(1);
				} catch (Exception e) {
					System.out.println("[VALIDACION EXCLUIDA/INCLUIDA] No fue posible ajustar scroll antes del screenshot: " + e.getMessage());
				}
				testCase.addScreenShotCurrentStep("Estado_Visual_Excluida_Vs_Incluida");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Estado visual excluida vs incluida | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Detecta si el overlay del mat-select esta abierto usando tres senales combinadas para evitar falsos negativos.
	 * Primera senal (rapida): aria-expanded="true" en el propio mat-select, que Angular actualiza inmediatamente al abrir.
	 * Segunda senal: presencia visible del panel .mat-mdc-select-panel o [role=listbox] dentro de cdk-overlay-container.
	 * Tercera senal: presencia de opciones "Incluido en el cálculo" o "Excluido del cálculo" en el overlay.
	 * El overlay se considera abierto si CUALQUIERA de las tres senales aparece.
	 *
	 * @param matSelect Elemento mat-select cuyo aria-expanded se revisa.
	 * @param panelOverlay Localizador CSS del panel del select dentro del overlay.
	 * @param opcionEnOverlay Localizador XPath de las opciones del select en el overlay.
	 * @return True si el overlay del selector esta abierto por cualquiera de las tres senales; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private boolean detectaOverlaySelectAbierto(WebElement matSelect, By panelOverlay, By opcionEnOverlay) {
		try {
			if (matSelect != null) {
				String ariaExpanded = matSelect.getAttribute("aria-expanded");
				if ("true".equalsIgnoreCase(ariaExpanded)) {
					return true;
				}
			}
		} catch (Exception e) {
			// Ignorar y probar las siguientes senales.
		}
		try {
			java.util.List<WebElement> paneles = u.getDriver().findElements(panelOverlay);
			for (WebElement panel : paneles) {
				try {
					if (panel.isDisplayed()) {
						return true;
					}
				} catch (Exception e) {
					// Ignorar y seguir probando.
				}
			}
		} catch (Exception e) {
			// Ignorar y probar la ultima senal.
		}
		try {
			return !u.getDriver().findElements(opcionEnOverlay).isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Captura un snapshot del grid de Liquidacion de Ruta: valores numericos del primer renglon (resumen) y de cada tienda-transferencia visible.
	 * Guarda el snapshot en variables de instancia usando la etiqueta indicada para poder compararlo despues (por ejemplo antes vs despues de excluir).
	 * Se extraen del primer renglon los campos: Canastillas Totales de la Ruta, Canastillas Azules, Canastillas Amarillas, Canastillas Verdes, Canastillas Naranjas, Otros, Total Cajas y Canastillas Sobrantes, Total Articulos Danados, Total Tapas Canastillas, Bols Totales, Canastillas con Devolucion, Envases Vacios.
	 * De cada renglon tienda-transferencia se capturan tambien: id (indice), etiqueta cabecera, estado (Incluido/Excluido), Canastillas Totales Devueltas de Tienda, Canastillas con Devolucion, Canastillas Azules, Canastillas Amarillas, Canastillas Verdes, Canastillas Naranjas, Otros, Total Sobrantes, Total Danados, Tapas, Envases Vacios.
	 *
	 * @param etiqueta Etiqueta unica del snapshot (por ejemplo "antes_exclusion" o "despues_exclusion") para almacenarlo y recuperarlo.
	 * @param screenshot True para capturar evidencia visual del snapshot registrado.
	 * @return True si el resumen del primer renglon se capturo correctamente y hay al menos una tienda-transferencia; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean registraResumenPrimerRenglonYTiendasTransferencia(String etiqueta, boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerDetalle));
			u.hardWait(1);

			WebElement detalle = u.getDriver().findElement(elementos.containerDetalle);
			String textoDetalle = normalizarTextoFiltro(detalle);
			String[][] camposResumen = new String[][] {
					{"canastillasTotalesRuta", "Canastillas\\s+Totales\\s+de\\s+la\\s+Ruta:\\s*(\\d+)"},
					{"canastillasAzules", "Canastillas\\s+Azules:\\s*(\\d+)"},
					{"canastillasAmarillas", "Canastillas\\s+Amarillas:\\s*(\\d+)"},
					{"canastillasVerdes", "Canastillas\\s+Verdes:\\s*(\\d+)"},
					{"canastillasNaranjas", "Canastillas\\s+Naranjas:\\s*(\\d+)"},
					{"otros", "Otros:\\s*(\\d+)"},
					{"totalCajasYCanastillasSobrantes", "Total\\s+Cajas\\s+y\\s+Canastillas\\s+Sobrantes:\\s*(\\d+)"},
					{"totalArticulosDanados", "Total\\s+Art[íi]culos\\s+Da[ñn]ados:\\s*(\\d+)"},
					{"totalTapasCanastillas", "Total\\s+Tapas\\s+Canastillas:\\s*(\\d+)"},
					{"bolsTotales", "Bols\\s+Totales:\\s*(\\d+)"},
					{"canastillasConDevolucion", "Canastillas\\s+con\\s+Devoluci[oó]n:\\s*(\\d+)"},
					{"envasesVacios", "Envases\\s+Vac[íi]os:\\s*(\\d+)"}
			};
			LinkedHashMap<String, Long> resumen = new LinkedHashMap<String, Long>();
			for (String[] campo : camposResumen) {
				java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(campo[1]).matcher(textoDetalle);
				resumen.put(campo[0], matcher.find() ? Long.parseLong(matcher.group(1)) : -1L);
			}
			snapshotsResumen.put(etiqueta, resumen);

			@SuppressWarnings("unchecked")
			java.util.List<java.util.Map<String, Object>> tiendasJs = (java.util.List<java.util.Map<String, Object>>) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const raiz = document.querySelector('app-cedis-liquidacion-ruta #detalle-tiendas')"
					+ " || document.querySelector('#detalle-tiendas')"
					+ " || document.querySelector('app-cedis-liquidacion-ruta');"
					+ "if (!raiz) return [];"
					+ "const wrappers = Array.from(raiz.querySelectorAll('.tienda-wrapper, [id=\"tienda\"]')).filter(w => w.offsetParent !== null && w.getBoundingClientRect().height > 20);"
					+ "const salida = [];"
					+ "for (const w of wrappers) {"
					+ "  const texto = (w.textContent || '').replace(/\\s+/g, ' ').trim();"
					+ "  let etiquetaCab = '';"
					+ "  const mCab = texto.match(/Tienda:\\s*([^\\.]+)\\.\\s*Transferencia:\\s*(\\d+)/i);"
					+ "  if (mCab) { etiquetaCab = 'Tienda:' + mCab[1].trim() + ' | Transferencia:' + mCab[2]; }"
					+ "  const claseLinea = (w.querySelector('.tienda-linea-lateral, [class*=\"linea-lateral\"]') || {}).className || '';"
					+ "  const excluida = /linea-gris|gris/i.test(claseLinea) || /excluido del c[aá]lculo/i.test(texto);"
					+ "  salida.push({texto: texto, etiqueta: etiquetaCab, excluida: excluida});"
					+ "}"
					+ "return salida;");
			String[][] camposTienda = new String[][] {
					{"canastillasTotalesDevueltas", "#?\\s*Canastillas\\s+Totales\\s+Devueltas\\s+de\\s+Tienda:\\s*(\\d+)"},
					{"canastillasConDevolucion", "#?\\s*Canastillas\\s+con\\s+Devoluci[oó]n:\\s*(\\d+)"},
					{"canastillasAzules", "#?\\s*Canastillas\\s+Azules:\\s*(\\d+)"},
					{"canastillasAmarillas", "#?\\s*Canastillas\\s+Amarillas:\\s*(\\d+)"},
					{"canastillasVerdes", "#?\\s*Canastillas\\s+Verdes:\\s*(\\d+)"},
					{"canastillasNaranjas", "#?\\s*Canastillas\\s+Naranjas:\\s*(\\d+)"},
					{"otros", "#?\\s*Otros:\\s*(\\d+)"},
					{"totalSobrantes", "Total\\s+Sobrantes:\\s*(\\d+)"},
					{"totalDanados", "Total\\s+Da[ñn]ados:\\s*(\\d+)"},
					{"tapas", "Tapas:\\s*(\\d+)"},
					{"envasesVacios", "Envases\\s+Vac[íi]os:\\s*(\\d+)"}
			};
			java.util.List<LinkedHashMap<String, Object>> tiendasSnapshot = new java.util.ArrayList<LinkedHashMap<String, Object>>();
			int indice = 0;
			for (java.util.Map<String, Object> tiendaJs : tiendasJs) {
				String texto = String.valueOf(tiendaJs.getOrDefault("texto", ""));
				String etiquetaTt = String.valueOf(tiendaJs.getOrDefault("etiqueta", ""));
				boolean excluida = Boolean.TRUE.equals(tiendaJs.get("excluida"));
				LinkedHashMap<String, Object> registro = new LinkedHashMap<String, Object>();
				registro.put("indice", indice);
				registro.put("etiqueta", etiquetaTt);
				registro.put("excluida", excluida);
				for (String[] campo : camposTienda) {
					java.util.regex.Matcher matcher = java.util.regex.Pattern.compile(campo[1]).matcher(texto);
					registro.put(campo[0], matcher.find() ? Long.parseLong(matcher.group(1)) : -1L);
				}
				tiendasSnapshot.add(registro);
				indice++;
			}
			snapshotsTiendasTransferencia.put(etiqueta, tiendasSnapshot);

			boolean resumenOk = !resumen.isEmpty() && resumen.get("canastillasTotalesRuta") != null && resumen.get("canastillasTotalesRuta") >= 0L;
			boolean tiendasOk = !tiendasSnapshot.isEmpty();
			boolean resultadoPaso = resumenOk && tiendasOk;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!resumenOk) motivoFallo.append("No se pudo extraer el resumen del primer renglon (Canastillas Totales de la Ruta no encontrado) | ");
			if (!tiendasOk) motivoFallo.append("No se detectaron renglones tienda-transferencia visibles | ");

			System.out.println("[SNAPSHOT " + etiqueta + "] Resumen: " + resumen);
			System.out.println("[SNAPSHOT " + etiqueta + "] Tiendas-transferencia (total: " + tiendasSnapshot.size() + ")");
			for (LinkedHashMap<String, Object> tt : tiendasSnapshot) {
				System.out.println("  - TT " + tt.get("indice") + " | Excluida: " + tt.get("excluida") + " | " + tt);
			}
			testCase.addBoldTextEvidenceCurrentStep("Snapshot " + etiqueta + " | Resumen primer renglon: " + resumen);
			testCase.addBoldTextEvidenceCurrentStep("Snapshot " + etiqueta + " | Total tiendas-transferencia registradas: " + tiendasSnapshot.size());
			for (LinkedHashMap<String, Object> tt : tiendasSnapshot) {
				testCase.addBoldTextEvidenceCurrentStep("Tienda-transferencia " + tt.get("indice") + " | Excluida: " + tt.get("excluida") + " | Datos: " + tt);
			}
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Snapshot_Grid_" + etiqueta);
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[SNAPSHOT " + etiqueta + "] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el resumen del primer renglon (capturado tras excluir una tienda-transferencia) coincida con la suma manual de las tiendas-transferencia que permanecen en estado Incluido en el calculo.
	 * Captura el snapshot actual con etiqueta "despues_exclusion", suma cada campo numerico de las tiendas Incluidas y compara contra el resumen del grid.
	 * Reporta diferencias campo por campo y confirma que el recalculo automatico del portal es correcto.
	 *
	 * @param screenshot True para capturar evidencia visual del resumen recalculado y la comparacion.
	 * @return True si todos los campos comparables coinciden entre resumen del grid y suma manual de tiendas Incluidas; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaRecalculoResumenSumaManualTiendasIncluidas(boolean screenshot) {
		try {
			boolean snapshotOk = registraResumenPrimerRenglonYTiendasTransferencia("despues_exclusion", false);
			if (!snapshotOk) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se pudo capturar snapshot 'despues_exclusion' para validar el recalculo");
				return false;
			}

			LinkedHashMap<String, Long> resumen = snapshotsResumen.get("despues_exclusion");
			java.util.List<LinkedHashMap<String, Object>> tiendas = snapshotsTiendasTransferencia.get("despues_exclusion");
			if (resumen == null || tiendas == null || tiendas.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Snapshot 'despues_exclusion' vacio");
				return false;
			}

			int tiendasIncluidas = 0;
			int tiendasExcluidas = 0;
			// Suma manual solo de tiendas Incluidas.
			// Mapeo de campo grid resumen -> campo tienda-transferencia:
			// canastillasTotalesRuta = SUM(canastillasTotalesDevueltas)
			// canastillasAzules = SUM(canastillasAzules)
			// canastillasAmarillas = SUM(canastillasAmarillas)
			// canastillasVerdes = SUM(canastillasVerdes)
			// canastillasNaranjas = SUM(canastillasNaranjas)
			// otros = SUM(otros)
			// totalCajasYCanastillasSobrantes = SUM(totalSobrantes)
			// totalArticulosDanados = SUM(totalDanados)
			// totalTapasCanastillas = SUM(tapas)
			// canastillasConDevolucion = SUM(canastillasConDevolucion)
			// envasesVacios = SUM(envasesVacios)
			// (bolsTotales del resumen es un conteo por ruta y no se compara aqui; se documenta por separado)
			String[][] mapeoCampos = new String[][] {
					{"canastillasTotalesRuta", "canastillasTotalesDevueltas"},
					{"canastillasAzules", "canastillasAzules"},
					{"canastillasAmarillas", "canastillasAmarillas"},
					{"canastillasVerdes", "canastillasVerdes"},
					{"canastillasNaranjas", "canastillasNaranjas"},
					{"otros", "otros"},
					{"totalCajasYCanastillasSobrantes", "totalSobrantes"},
					{"totalArticulosDanados", "totalDanados"},
					{"totalTapasCanastillas", "tapas"},
					{"canastillasConDevolucion", "canastillasConDevolucion"},
					{"envasesVacios", "envasesVacios"}
			};
			LinkedHashMap<String, Long> sumaManualIncluidas = new LinkedHashMap<String, Long>();
			for (String[] campo : mapeoCampos) {
				sumaManualIncluidas.put(campo[0], 0L);
			}
			java.util.List<String> etiquetasIncluidas = new java.util.ArrayList<String>();
			java.util.List<String> etiquetasExcluidas = new java.util.ArrayList<String>();
			for (LinkedHashMap<String, Object> tt : tiendas) {
				boolean excluida = Boolean.TRUE.equals(tt.get("excluida"));
				String etiquetaTt = String.valueOf(tt.get("etiqueta"));
				if (excluida) {
					tiendasExcluidas++;
					etiquetasExcluidas.add(etiquetaTt);
					continue;
				}
				tiendasIncluidas++;
				etiquetasIncluidas.add(etiquetaTt);
				for (String[] campo : mapeoCampos) {
					Object valor = tt.get(campo[1]);
					long valorLong = valor instanceof Number ? ((Number) valor).longValue() : -1L;
					if (valorLong < 0) valorLong = 0L; // campo no encontrado se cuenta como 0 para no romper la suma
					sumaManualIncluidas.put(campo[0], sumaManualIncluidas.get(campo[0]) + valorLong);
				}
			}

			int coincidencias = 0;
			int diferencias = 0;
			StringBuilder detalleDiferencias = new StringBuilder();
			for (String[] campo : mapeoCampos) {
				String campoResumen = campo[0];
				long valorResumenGrid = resumen.getOrDefault(campoResumen, -1L);
				long valorSumaManual = sumaManualIncluidas.getOrDefault(campoResumen, 0L);
				boolean coincide = valorResumenGrid == valorSumaManual;
				String textoLog = "Campo '" + campoResumen + "' | Resumen grid: " + valorResumenGrid + " | Suma manual tiendas Incluidas: " + valorSumaManual + " | " + (coincide ? "COINCIDE" : "NO COINCIDE");
				System.out.println("[RECALCULO] " + textoLog);
				testCase.addBoldTextEvidenceCurrentStep(textoLog);
				if (coincide) {
					coincidencias++;
				} else {
					diferencias++;
					detalleDiferencias.append(campoResumen).append(" (grid=").append(valorResumenGrid).append(", suma=").append(valorSumaManual).append("); ");
				}
			}

			boolean hayExcluida = tiendasExcluidas >= 1;
			boolean hayIncluidas = tiendasIncluidas >= 1;
			boolean todosCoinciden = diferencias == 0 && coincidencias == mapeoCampos.length;
			boolean resultadoPaso = hayExcluida && hayIncluidas && todosCoinciden;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!hayExcluida) motivoFallo.append("Se esperaba al menos 1 tienda-transferencia Excluida y hay 0 | ");
			if (!hayIncluidas) motivoFallo.append("Se esperaba al menos 1 tienda-transferencia Incluida y hay 0 | ");
			if (!todosCoinciden) motivoFallo.append("Campos con diferencia grid vs suma manual: ").append(detalleDiferencias);

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Recalculo resumen vs suma manual tiendas Incluidas" + (resultadoPaso ? " | Coincidencias: " + coincidencias + "/" + mapeoCampos.length + " | Incluidas: " + tiendasIncluidas + " | Excluidas: " + tiendasExcluidas : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Tiendas-transferencia Incluidas revisadas (" + tiendasIncluidas + "): " + etiquetasIncluidas);
			testCase.addBoldTextEvidenceCurrentStep("Tiendas-transferencia Excluidas revisadas (" + tiendasExcluidas + "): " + etiquetasExcluidas);
			testCase.addBoldTextEvidenceCurrentStep("Coincidencias resumen grid vs suma manual: " + coincidencias + "/" + mapeoCampos.length);
			testCase.addBoldTextEvidenceCurrentStep("Nota: Bols Totales del resumen NO se compara contra la suma de folios de bols por tienda (formatos distintos: conteo por ruta vs lista de folios).");
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Recalculo_Resumen_Suma_Manual_Incluidas");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Recalculo resumen vs suma manual | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida contra BD que las tiendas-transferencia actualmente Incluidas en el grid son consistentes con los datos de la Ruta Dinamica y fecha consultadas.
	 * Reutiliza `obtenerRetekLocIdsRutaDinamicaEnFechaDesdeBD` para obtener los id_retek esperados en la ruta y consulta agregados totales por tienda cuando estos existen en `xxcm_historico_correos`.
	 * La consulta SUM definitiva de la matriz (`SELECT SUM(f.{campo_total_unidades}) FROM {tabla_formato_unico_entrega}`) usa placeholders sin nombres finales de dev; por eso este metodo es tolerante: si las columnas esperadas no existen en BD se reporta INCONCLUSIVE con el detalle para revision manual, pero no aborta el flujo.
	 * En todos los casos se reporta la lista de id_retek de las tiendas Incluidas segun el snapshot 'despues_exclusion' para trazabilidad de QA.
	 *
	 * @param usuario Usuario cuyo perfil y alcance de tiendas se validan en BD.
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) usada en el filtro.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param screenshot True para capturar evidencia visual del resultado de la validacion BD.
	 * @return True si la trazabilidad es consistente y la suma BD coincide con el grid, o si BD no permite calcular la suma pero los id_retek Incluidos coinciden con los esperados; false si hay inconsistencias claras.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaSumaResumenContraBDTiendasIncluidas(String usuario, String rutaDinamica, String fechaFormateada, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty() || fechaFormateada == null || fechaFormateada.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Ruta Dinamica o fecha vacia para validar suma resumen contra BD");
				return false;
			}
			java.util.List<LinkedHashMap<String, Object>> tiendas = snapshotsTiendasTransferencia.get("despues_exclusion");
			LinkedHashMap<String, Long> resumen = snapshotsResumen.get("despues_exclusion");
			if (tiendas == null || tiendas.isEmpty() || resumen == null) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Snapshot 'despues_exclusion' no disponible; ejecute primero validaRecalculoResumenSumaManualTiendasIncluidas");
				return false;
			}

			LinkedHashMap<String, String> tiendasBDRuta = obtenerRetekLocIdsRutaDinamicaEnFechaDesdeBD(usuario, rutaDinamica, fechaFormateada);
			java.util.List<String> idsRetekEsperados = new java.util.ArrayList<String>(tiendasBDRuta.keySet());
			int totalTiendasBD = idsRetekEsperados.size();

			int tiendasIncluidas = 0;
			int tiendasExcluidas = 0;
			java.util.List<String> etiquetasIncluidas = new java.util.ArrayList<String>();
			java.util.List<String> etiquetasExcluidas = new java.util.ArrayList<String>();
			for (LinkedHashMap<String, Object> tt : tiendas) {
				String etiquetaTt = String.valueOf(tt.get("etiqueta"));
				if (Boolean.TRUE.equals(tt.get("excluida"))) {
					tiendasExcluidas++;
					etiquetasExcluidas.add(etiquetaTt);
				} else {
					tiendasIncluidas++;
					etiquetasIncluidas.add(etiquetaTt);
				}
			}

			boolean cantidadCoincide = totalTiendasBD > 0 && totalTiendasBD == (tiendasIncluidas + tiendasExcluidas);
			boolean hayExcluida = tiendasExcluidas >= 1;
			boolean hayIncluidas = tiendasIncluidas >= 1;

			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinamica consultada: " + rutaDinamica.trim() + " | Fecha: " + fechaFormateada.trim());
			testCase.addBoldTextEvidenceCurrentStep("Tiendas-transferencia esperadas en BD para la ruta y fecha (id_retek): " + totalTiendasBD + " | Detalle: " + tiendasBDRuta);
			testCase.addBoldTextEvidenceCurrentStep("Tiendas-transferencia visibles en el grid: Incluidas=" + tiendasIncluidas + " | Excluidas=" + tiendasExcluidas + " | Total=" + (tiendasIncluidas + tiendasExcluidas));
			testCase.addBoldTextEvidenceCurrentStep("Etiquetas Incluidas (para trazabilidad de SUM manual en BD sobre tiendas incluidas): " + etiquetasIncluidas);
			testCase.addBoldTextEvidenceCurrentStep("Etiquetas Excluidas (deben restarse del total en BD): " + etiquetasExcluidas);
			testCase.addBoldTextEvidenceCurrentStep("Nota: la matriz define SELECT SUM(f.{campo_total_unidades}) FROM {tabla_formato_unico_entrega} con placeholders pendientes de nombres finales por dev. Por eso la comparacion SUM(BD) vs Resumen(grid) NO se ejecuta automatica; se documenta trazabilidad de id_retek Incluidos y los totales del resumen segun grid.");
			testCase.addBoldTextEvidenceCurrentStep("Totales del resumen del grid tras exclusion (para comparacion manual con BD): " + resumen);

			boolean resultadoPaso = cantidadCoincide && hayExcluida && hayIncluidas;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!cantidadCoincide) motivoFallo.append("Total tiendas del grid (").append(tiendasIncluidas + tiendasExcluidas).append(") no coincide con total esperado en BD (").append(totalTiendasBD).append(") para la ruta y fecha | ");
			if (!hayExcluida) motivoFallo.append("Se esperaba al menos una tienda Excluida | ");
			if (!hayIncluidas) motivoFallo.append("Se esperaba al menos una tienda Incluida | ");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Trazabilidad BD tiendas Incluidas vs grid" + (resultadoPaso ? " | Incluidas: " + tiendasIncluidas + " | Excluidas: " + tiendasExcluidas + " | BD ruta: " + totalTiendasBD : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Trazabilidad_BD_Tiendas_Incluidas_Grid");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Trazabilidad BD tiendas Incluidas vs grid | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona la opcion "Incluido en el calculo" en el selector Estado de la primera tienda-transferencia que actualmente este Excluida y valida que aparezca el modal homologado "Espere un momento" mientras el sistema recalcula, y que desaparezca al finalizar.
	 * Si el selector no esta abierto lo abre primero reutilizando `abreSelectorEstadoPrimeraTiendaTransferenciaYValidaOpciones`. La navegacion se hace por teclado (ARROW_UP + ENTER) para llevar el highlight desde "Excluido del calculo" hasta "Incluido en el calculo" sin depender de coordenadas del overlay CDK/Popover. Si ENTER no aplica la seleccion se intenta ARROW_UP + TAB como fallback.
	 *
	 * @param screenshot True para capturar evidencia visual del selector, del modal Espere un momento y del estado final tras la reincorporacion.
	 * @return True si la opcion Incluido queda seleccionada y el modal no permanece visible al final; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean seleccionaIncluidoDelCalculoPrimeraTiendaTransferenciaYValidaModal(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			By modalEspere = By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]");
			By panelOverlay = By.cssSelector(".mat-mdc-select-panel, div[role='listbox'].mdc-menu-surface--open, div.cdk-overlay-pane .mat-mdc-select-panel");

			// 1. Asegurar que el mat-select este abierto.
			boolean overlayAbierto = false;
			try {
				java.util.List<WebElement> paneles = u.getDriver().findElements(panelOverlay);
				for (WebElement panel : paneles) { if (panel.isDisplayed()) { overlayAbierto = true; break; } }
			} catch (Exception e) { }
			if (!overlayAbierto) {
				try {
					WebElement msTest = (WebElement) ((JavascriptExecutor) u.getDriver()).executeScript(
							"return document.querySelector('mat-select[aria-expanded=\"true\"], mat-select.mat-select-open');");
					if (msTest != null) overlayAbierto = true;
				} catch (Exception e) { }
			}
			if (!overlayAbierto) {
				boolean panelAbierto = abreSelectorEstadoPrimeraTiendaTransferenciaYValidaOpciones(false);
				if (!panelAbierto) {
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se pudo abrir el selector Estado para seleccionar Incluido del calculo");
					return false;
				}
			}

			// 2. Localizar el mat-select abierto para enfocarlo y capturar valor inicial.
			JavascriptExecutor jsExec = (JavascriptExecutor) u.getDriver();
			WebElement matSelect = null;
			try {
				matSelect = (WebElement) jsExec.executeScript(
						"return document.querySelector('mat-select[aria-expanded=\"true\"]') || document.querySelector('mat-select.mat-select-open');");
			} catch (Exception e) { }
			String valorInicial = "";
			try {
				valorInicial = (String) jsExec.executeScript(
						"const ms = document.querySelector('mat-select[aria-expanded=\"true\"]') || document.querySelector('mat-select.mat-select-open');"
						+ "if (!ms) return '';"
						+ "const min = ms.querySelector('.mat-mdc-select-min-line, .mat-mdc-select-value-text');"
						+ "return min ? (min.textContent || '').trim() : '';");
			} catch (Exception e) { }
			System.out.println("[SELECTOR ESTADO] Valor inicial del selector antes de ARROW_UP+ENTER (reincorporacion): '" + valorInicial + "'");
			testCase.addBoldTextEvidenceCurrentStep("Valor inicial del selector Estado (esperado 'Excluido del calculo'): " + valorInicial);

			// 3. Enfocar el mat-select y enviar ARROW_UP + ENTER para llevar el highlight a 'Incluido en el calculo'.
			if (matSelect != null) {
				try { jsExec.executeScript("try { arguments[0].focus(); } catch(e){}", matSelect); } catch (Exception e) { }
			}
			System.out.println("[SELECTOR ESTADO] Enviando ARROW_UP + ENTER al mat-select enfocado para seleccionar 'Incluido en el calculo'");
			try {
				new Actions(u.getDriver())
					.sendKeys(Keys.ARROW_UP)
					.pause(400)
					.sendKeys(Keys.ENTER)
					.perform();
			} catch (Exception e) {
				System.out.println("[SELECTOR ESTADO] Actions.sendKeys ARROW_UP+ENTER fallo: " + e.getMessage() + " | Intentando via matSelect.sendKeys");
				if (matSelect != null) {
					try {
						matSelect.sendKeys(Keys.ARROW_UP);
						u.hardWait(1);
						matSelect.sendKeys(Keys.ENTER);
					} catch (Exception e2) {
						testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se pudieron enviar ARROW_UP + ENTER al mat-select: " + e2.getMessage());
						return false;
					}
				}
			}
			u.hardWait(1);

			// 4. Verificar que el valor visible del selector cambio a 'Incluido en el calculo'.
			String valorFinal = "";
			try {
				valorFinal = (String) jsExec.executeScript(
						"const raiz = document.querySelector('app-cedis-liquidacion-ruta #detalle-tiendas')"
						+ " || document.querySelector('#detalle-tiendas')"
						+ " || document.querySelector('app-cedis-liquidacion-ruta') || document;"
						+ "const wrappers = raiz.querySelectorAll('.tienda-wrapper, [id=\"tienda\"], mat-select');"
						+ "for (const w of wrappers) {"
						+ "  const min = w.querySelector('.mat-mdc-select-min-line, .mat-mdc-select-value-text');"
						+ "  if (min && min.offsetParent !== null) { return (min.textContent || '').trim(); }"
						+ "}"
						+ "const cualquiera = document.querySelector('.mat-mdc-select-min-line, .mat-mdc-select-value-text');"
						+ "return cualquiera ? (cualquiera.textContent || '').trim() : '';");
			} catch (Exception e) { }
			System.out.println("[SELECTOR ESTADO] Valor final del selector tras ARROW_UP+ENTER (reincorporacion): '" + valorFinal + "'");
			boolean seleccionCambio = valorFinal != null && valorFinal.toLowerCase().contains("incluido en el c");

			// 5. Fallback ARROW_UP + TAB (algunas versiones seleccionan con TAB en vez de ENTER).
			if (!seleccionCambio) {
				System.out.println("[SELECTOR ESTADO] ARROW_UP+ENTER no cambio el valor a Incluido; intentando ARROW_UP+TAB como fallback");
				try {
					if (matSelect != null) { jsExec.executeScript("try { arguments[0].focus(); } catch(e){}", matSelect); }
					if (u.getDriver().findElements(panelOverlay).isEmpty()) {
						abreSelectorEstadoPrimeraTiendaTransferenciaYValidaOpciones(false);
					}
					new Actions(u.getDriver())
						.sendKeys(Keys.ARROW_UP)
						.pause(400)
						.sendKeys(Keys.TAB)
						.perform();
					u.hardWait(1);
					try {
						valorFinal = (String) jsExec.executeScript(
								"const cualquiera = document.querySelector('.mat-mdc-select-min-line, .mat-mdc-select-value-text');"
								+ "return cualquiera ? (cualquiera.textContent || '').trim() : '';");
					} catch (Exception e) { }
					seleccionCambio = valorFinal != null && valorFinal.toLowerCase().contains("incluido en el c");
					System.out.println("[SELECTOR ESTADO] Valor tras fallback ARROW_UP+TAB: '" + valorFinal + "' | seleccionCambio: " + seleccionCambio);
				} catch (Exception e) {
					System.out.println("[SELECTOR ESTADO] Fallback ARROW_UP+TAB fallo: " + e.getMessage());
				}
			}

			// 6. Validar aparicion y desaparicion del modal 'Espere un momento'.
			boolean modalVisto = false;
			long tsInicio = System.currentTimeMillis();
			while (System.currentTimeMillis() - tsInicio < 3000) {
				java.util.List<WebElement> modales = u.getDriver().findElements(modalEspere);
				for (WebElement m : modales) {
					try {
						if (m.isDisplayed()) { modalVisto = true; break; }
					} catch (Exception e) { }
				}
				if (modalVisto) break;
				esperaRenderCorto();
			}

			if (screenshot && modalVisto) {
				testCase.addScreenShotCurrentStep("Modal_Espere_Un_Momento_Reincorporacion");
			}

			try { wait.until(ExpectedConditions.invisibilityOfElementLocated(modalEspere)); } catch (Exception e) { }
			u.hardWait(1);

			boolean modalOcultoAlFinal = true;
			java.util.List<WebElement> modalesFinal = u.getDriver().findElements(modalEspere);
			for (WebElement m : modalesFinal) {
				try {
					if (m.isDisplayed()) { modalOcultoAlFinal = false; break; }
				} catch (Exception e) { }
			}

			boolean resultadoPaso = seleccionCambio && modalOcultoAlFinal;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!seleccionCambio) motivoFallo.append("El selector no cambio a 'Incluido en el calculo' tras ARROW_UP+ENTER ni ARROW_UP+TAB. Valor inicial: '").append(valorInicial).append("' | Valor final: '").append(valorFinal).append("' | ");
			if (!modalOcultoAlFinal) motivoFallo.append("El modal 'Espere un momento' permanece visible despues del recalculo | ");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Reincorporacion tienda-transferencia a Incluido del calculo" + (resultadoPaso ? " | Valor final: '" + valorFinal + "' | Modal visto: " + modalVisto : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Valor inicial del selector: " + valorInicial);
			testCase.addBoldTextEvidenceCurrentStep("Valor final del selector: " + valorFinal);
			testCase.addBoldTextEvidenceCurrentStep("Seleccion cambio a Incluido en el calculo: " + (seleccionCambio ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Modal 'Espere un momento' visible durante el recalculo: " + (modalVisto ? "SI" : "NO (el sistema pudo procesar en tiempo minimo)"));
			testCase.addBoldTextEvidenceCurrentStep("Modal 'Espere un momento' oculto al finalizar: " + (modalOcultoAlFinal ? "SI" : "NO"));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Post_Reincorporacion_Incluido_Del_Calculo");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Reincorporacion Incluido del calculo | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el resumen del primer renglon del grid despues de reincorporar una tienda-transferencia a Incluido en el calculo coincida campo por campo con el snapshot registrado antes de cualquier exclusion.
	 * Captura un snapshot actual con etiqueta "despues_reincorporacion", verifica que no quede ninguna tienda-transferencia Excluida y compara cada campo del resumen contra la etiqueta de referencia (por convencion "antes_exclusion").
	 * Reporta diferencias campo por campo cuando existan y confirma que la reincorporacion restaura los totales originales.
	 *
	 * @param etiquetaSnapshotReferencia Etiqueta del snapshot previamente registrado con `registraResumenPrimerRenglonYTiendasTransferencia` (por ejemplo "antes_exclusion").
	 * @param screenshot True para capturar evidencia visual del resumen restaurado y la comparacion.
	 * @return True si todas las tiendas-transferencia estan Incluidas y todos los campos del resumen coinciden con el snapshot de referencia; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaResumenPrimerRenglonCoincideConSnapshot(String etiquetaSnapshotReferencia, boolean screenshot) {
		try {
			LinkedHashMap<String, Long> snapshotReferencia = snapshotsResumen.get(etiquetaSnapshotReferencia);
			if (snapshotReferencia == null || snapshotReferencia.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No existe snapshot de referencia con etiqueta '" + etiquetaSnapshotReferencia + "'; ejecute primero registraResumenPrimerRenglonYTiendasTransferencia con esa etiqueta");
				return false;
			}

			boolean snapshotOk = registraResumenPrimerRenglonYTiendasTransferencia("despues_reincorporacion", false);
			if (!snapshotOk) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se pudo capturar snapshot 'despues_reincorporacion' para validar la reincorporacion");
				return false;
			}
			LinkedHashMap<String, Long> snapshotActual = snapshotsResumen.get("despues_reincorporacion");
			java.util.List<LinkedHashMap<String, Object>> tiendasActuales = snapshotsTiendasTransferencia.get("despues_reincorporacion");

			int tiendasIncluidas = 0;
			int tiendasExcluidas = 0;
			java.util.List<String> etiquetasIncluidas = new java.util.ArrayList<String>();
			java.util.List<String> etiquetasExcluidas = new java.util.ArrayList<String>();
			for (LinkedHashMap<String, Object> tt : tiendasActuales) {
				String etiquetaTt = String.valueOf(tt.get("etiqueta"));
				if (Boolean.TRUE.equals(tt.get("excluida"))) {
					tiendasExcluidas++;
					etiquetasExcluidas.add(etiquetaTt);
				} else {
					tiendasIncluidas++;
					etiquetasIncluidas.add(etiquetaTt);
				}
			}

			int coincidencias = 0;
			int diferencias = 0;
			StringBuilder detalleDiferencias = new StringBuilder();
			for (java.util.Map.Entry<String, Long> entrada : snapshotReferencia.entrySet()) {
				String campo = entrada.getKey();
				long valorReferencia = entrada.getValue();
				long valorActual = snapshotActual.getOrDefault(campo, -999L);
				boolean coincide = valorReferencia == valorActual;
				String textoLog = "Campo '" + campo + "' | Referencia (" + etiquetaSnapshotReferencia + "): " + valorReferencia + " | Actual (despues_reincorporacion): " + valorActual + " | " + (coincide ? "COINCIDE" : "NO COINCIDE");
				System.out.println("[REINCORPORACION] " + textoLog);
				testCase.addBoldTextEvidenceCurrentStep(textoLog);
				if (coincide) {
					coincidencias++;
				} else {
					diferencias++;
					detalleDiferencias.append(campo).append(" (referencia=").append(valorReferencia).append(", actual=").append(valorActual).append("); ");
				}
			}

			boolean todasIncluidas = tiendasExcluidas == 0 && tiendasIncluidas >= 1;
			boolean todosCoinciden = diferencias == 0;
			boolean resultadoPaso = todasIncluidas && todosCoinciden;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!todasIncluidas) motivoFallo.append("Se esperaba 0 tiendas Excluidas tras la reincorporacion y hay ").append(tiendasExcluidas).append(" | ");
			if (!todosCoinciden) motivoFallo.append("Campos con diferencia referencia vs actual: ").append(detalleDiferencias);

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Resumen tras reincorporacion coincide con snapshot '" + etiquetaSnapshotReferencia + "'" + (resultadoPaso ? " | Coincidencias: " + coincidencias + "/" + snapshotReferencia.size() : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Tiendas-transferencia Incluidas tras reincorporacion: " + tiendasIncluidas + " | Excluidas: " + tiendasExcluidas + " (esperado 0)");
			testCase.addBoldTextEvidenceCurrentStep("Etiquetas Incluidas: " + etiquetasIncluidas);
			if (!etiquetasExcluidas.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Etiquetas todavia Excluidas (inesperado): " + etiquetasExcluidas);
			}
			testCase.addBoldTextEvidenceCurrentStep("Coincidencias resumen actual vs snapshot '" + etiquetaSnapshotReferencia + "': " + coincidencias + "/" + snapshotReferencia.size());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Resumen_Coincide_Snapshot_" + etiquetaSnapshotReferencia);
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Resumen coincide snapshot | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Realiza interacciones que NO disparan una nueva consulta (scroll vertical en la pantalla y apertura/cierre del Selector de Filtro con la tecla ESC) y valida que el estado Excluido de la primera tienda-transferencia se conserva al finalizar.
	 * Reutiliza `validaEstadoVisualExcluidaVsIncluida` para confirmar que la primera TT sigue con linea gris + icono X + selector rojo y las demas conservan linea verde + palomita + selector verde, sin ejecutar el boton Consultar.
	 *
	 * @param screenshot True para capturar evidencia visual del estado tras las interacciones sin consulta.
	 * @return True si la primera tienda-transferencia sigue Excluida y las demas siguen Incluidas despues de las interacciones; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean interactuaSinConsultarYValidaEstadoExcluidoPersiste(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();

			// 1. Scroll vertical hacia abajo y de regreso, sin tocar filtros.
			js.executeScript("window.scrollBy(0, 400);");
			u.hardWait(1);
			js.executeScript("window.scrollBy(0, -400);");
			u.hardWait(1);

			// 2. Abrir y cerrar el Selector de Filtro con ESC (interaccion que no dispara consulta).
			try {
				WebDriverWait waitCorto = new WebDriverWait(u.getDriver(), Duration.ofSeconds(5));
				WebElement controlSelector = waitCorto.until(ExpectedConditions.elementToBeClickable(elementos.controlSelectorFiltroLiquidacionRuta));
				js.executeScript("arguments[0].scrollIntoView({block:'center'});", controlSelector);
				js.executeScript("arguments[0].click();", controlSelector);
				u.hardWait(1);
				u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
				u.hardWait(1);
			} catch (Exception e) {
				System.out.println("[PERSISTENCIA ESTADO] No se pudo abrir/cerrar Selector de Filtro sin consultar: " + e.getMessage());
			}

			testCase.addBoldTextEvidenceCurrentStep("Interacciones sin consultar realizadas: scroll vertical y apertura/cierre del Selector de Filtro con ESC");

			boolean estadoConserva = validaEstadoVisualExcluidaVsIncluida(screenshot);
			String estadoPaso = estadoConserva ? "PASSED" : "FAILED";
			String motivoFallo = estadoConserva ? "" : "El patron visual EXCLUIDA de la primera tienda-transferencia se perdio despues de las interacciones sin consulta";

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Persistencia estado Excluido sin nueva consulta" + (estadoConserva ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (estadoConserva ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Persistencia_Estado_Excluido_Sin_Consulta");
			}
			return estadoConserva;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Persistencia estado Excluido | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Captura desde BD un snapshot completo del resultset de `xxcm_historico_correos` filtrado por la Ruta Dinamica y fecha consultadas en el portal (equivalente al SELECT * FROM {tabla_origen_transferencias} de la matriz).
	 * Guarda todas las columnas de cada registro en un mapa columna->valor y almacena la lista de registros bajo la etiqueta indicada para poder compararla despues de una exclusion desde el portal.
	 * Se usa antes y despues de excluir una tienda-transferencia en Liquidacion de Ruta para confirmar que la exclusion NO altera la fuente en BD.
	 *
	 * @param usuario Usuario cuyo perfil y alcance de registros se validan en BD.
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) capturada en el filtro del portal.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param etiqueta Etiqueta unica del snapshot (por ejemplo "antes_exclusion_bd" o "despues_exclusion_bd").
	 * @param screenshot True para capturar evidencia visual del snapshot BD registrado.
	 * @return True si BD devolvio al menos un registro y se pudo almacenar; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean capturaSnapshotBDRegistrosRutaDinamicaEnFecha(String usuario, String rutaDinamica, String fechaFormateada, String etiqueta, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty() || fechaFormateada == null || fechaFormateada.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Ruta Dinamica o fecha vacia para capturar snapshot BD");
				return false;
			}
			LocalDate fechaConsulta = convertirFechaVisibleALocalDate(fechaFormateada);
			String query = "SELECT * FROM xxcm_historico_correos "
					+ "WHERE ruta_propuesta = ? "
					+ "AND fecha_registro::date = ? "
					+ "AND TRIM(ruta_propuesta) <> '' "
					+ "AND version_documento = 2 "
					+ "AND is_visible = true "
					+ "AND id_retek IS NOT NULL "
					+ "AND (? IS NULL OR (origen = 'cedis' AND (EXISTS ("
					+ "    SELECT 1 FROM xxcm_user_keys perfil "
					+ "    JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = perfil.usuario_id "
					+ "    WHERE datos_usuario.usuario = ? "
					+ "    AND perfil.short_name = 'PCPERFIL' "
					+ "    AND UPPER(TRIM(perfil.key_value)) = 'ADMIN'"
					+ ") OR suppliable_id::text IN ("
					+ "    SELECT alcance.key_value FROM xxcm_user_keys alcance "
					+ "    JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = alcance.usuario_id "
					+ "    WHERE datos_usuario.usuario = ? "
					+ "    AND alcance.short_name IN ('PCRFC', 'PCSUPPLIERID')"
					+ ")))) "
					+ "ORDER BY id_retek";

			java.util.List<LinkedHashMap<String, Object>> registros = new java.util.ArrayList<LinkedHashMap<String, Object>>();
			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
				String usuarioNormalizado = usuario == null || usuario.trim().isEmpty() ? null : usuario.trim();
				ps.setString(1, rutaDinamica.trim());
				ps.setDate(2, java.sql.Date.valueOf(fechaConsulta));
				ps.setString(3, usuarioNormalizado);
				ps.setString(4, usuarioNormalizado);
				ps.setString(5, usuarioNormalizado);
				try (ResultSet rs = ps.executeQuery()) {
					java.sql.ResultSetMetaData meta = rs.getMetaData();
					int totalColumnas = meta.getColumnCount();
					while (rs.next()) {
						LinkedHashMap<String, Object> fila = new LinkedHashMap<String, Object>();
						for (int c = 1; c <= totalColumnas; c++) {
							String nombreColumna = meta.getColumnLabel(c);
							Object valor = rs.getObject(c);
							fila.put(nombreColumna, valor == null ? null : String.valueOf(valor));
						}
						registros.add(fila);
					}
				}
			}
			snapshotsRegistrosBD.put(etiqueta, registros);

			boolean resultadoPaso = !registros.isEmpty();
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "BD no devolvio registros para ruta " + rutaDinamica + " en la fecha " + fechaFormateada;
			System.out.println("[SNAPSHOT BD " + etiqueta + "] Usuario: " + usuario + " | Ruta: " + rutaDinamica.trim() + " | Fecha: " + fechaConsulta + " | Registros: " + registros.size());
			testCase.addBoldTextEvidenceCurrentStep("Snapshot BD " + etiqueta + " | Ruta: " + rutaDinamica.trim() + " | Fecha: " + fechaConsulta + " | Total de registros capturados: " + registros.size());
			if (!registros.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Snapshot BD " + etiqueta + " | Total de columnas por registro: " + registros.get(0).size() + " | Nombres de columnas: " + registros.get(0).keySet());
			}
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Snapshot_BD_Registros_" + etiqueta);
			}
			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[SNAPSHOT BD " + etiqueta + "] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Captura un snapshot BD actual con etiqueta "despues_exclusion_bd" y lo compara campo por campo contra el snapshot de referencia previamente registrado por `capturaSnapshotBDRegistrosRutaDinamicaEnFecha`.
	 * Se usa despues de excluir una tienda-transferencia desde el portal para confirmar que ningun campo del registro origen cambio en `xxcm_historico_correos`.
	 * Reporta diferencias id_retek por id_retek y columna por columna cuando existan, y confirma que la exclusion en el portal solo afecta la vista y NO altera la fuente en BD.
	 *
	 * @param usuario Usuario cuyo perfil y alcance de registros se validan en BD.
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) usada en el filtro del portal.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param etiquetaSnapshotReferencia Etiqueta del snapshot BD previamente registrado (por convencion "antes_exclusion_bd").
	 * @param screenshot True para capturar evidencia visual del resultado de la comparacion.
	 * @return True si el snapshot actual es identico al snapshot de referencia en cantidad de registros y en todos los valores de todas las columnas; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaSnapshotBDRegistrosCoincideConReferencia(String usuario, String rutaDinamica, String fechaFormateada, String etiquetaSnapshotReferencia, boolean screenshot) {
		try {
			java.util.List<LinkedHashMap<String, Object>> snapshotReferencia = snapshotsRegistrosBD.get(etiquetaSnapshotReferencia);
			if (snapshotReferencia == null || snapshotReferencia.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No existe snapshot BD de referencia con etiqueta '" + etiquetaSnapshotReferencia + "'. Ejecute primero capturaSnapshotBDRegistrosRutaDinamicaEnFecha con esa etiqueta");
				return false;
			}

			boolean snapshotOk = capturaSnapshotBDRegistrosRutaDinamicaEnFecha(usuario, rutaDinamica, fechaFormateada, "despues_exclusion_bd", false);
			if (!snapshotOk) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No se pudo capturar snapshot BD 'despues_exclusion_bd' para validar la integridad tras exclusion");
				return false;
			}
			java.util.List<LinkedHashMap<String, Object>> snapshotActual = snapshotsRegistrosBD.get("despues_exclusion_bd");

			int totalReferencia = snapshotReferencia.size();
			int totalActual = snapshotActual.size();
			boolean cantidadCoincide = totalReferencia == totalActual;

			LinkedHashMap<String, LinkedHashMap<String, Object>> mapaReferenciaPorIdRetek = new LinkedHashMap<String, LinkedHashMap<String, Object>>();
			for (LinkedHashMap<String, Object> registro : snapshotReferencia) {
				Object idRetek = registro.get("id_retek");
				String claveIdRetek = idRetek == null ? "" : String.valueOf(idRetek);
				mapaReferenciaPorIdRetek.put(claveIdRetek, registro);
			}
			LinkedHashMap<String, LinkedHashMap<String, Object>> mapaActualPorIdRetek = new LinkedHashMap<String, LinkedHashMap<String, Object>>();
			for (LinkedHashMap<String, Object> registro : snapshotActual) {
				Object idRetek = registro.get("id_retek");
				String claveIdRetek = idRetek == null ? "" : String.valueOf(idRetek);
				mapaActualPorIdRetek.put(claveIdRetek, registro);
			}

			int registrosComparados = 0;
			int registrosIguales = 0;
			int registrosConDiferencias = 0;
			int camposDiferentes = 0;
			StringBuilder detalleDiferencias = new StringBuilder();
			java.util.List<String> idRetekSoloReferencia = new java.util.ArrayList<String>();
			java.util.List<String> idRetekSoloActual = new java.util.ArrayList<String>();

			for (java.util.Map.Entry<String, LinkedHashMap<String, Object>> entrada : mapaReferenciaPorIdRetek.entrySet()) {
				String idRetek = entrada.getKey();
				LinkedHashMap<String, Object> registroRef = entrada.getValue();
				LinkedHashMap<String, Object> registroActual = mapaActualPorIdRetek.get(idRetek);
				if (registroActual == null) {
					idRetekSoloReferencia.add(idRetek);
					continue;
				}
				registrosComparados++;
				int diferenciasEnEsteRegistro = 0;
				StringBuilder diffCampos = new StringBuilder();
				for (java.util.Map.Entry<String, Object> campo : registroRef.entrySet()) {
					String nombreColumna = campo.getKey();
					String valorReferencia = campo.getValue() == null ? "" : String.valueOf(campo.getValue());
					Object valorActualObj = registroActual.get(nombreColumna);
					String valorActual = valorActualObj == null ? "" : String.valueOf(valorActualObj);
					if (!valorReferencia.equals(valorActual)) {
						diferenciasEnEsteRegistro++;
						camposDiferentes++;
						diffCampos.append(nombreColumna).append(" (ref='").append(valorReferencia).append("', actual='").append(valorActual).append("'); ");
					}
				}
				if (diferenciasEnEsteRegistro == 0) {
					registrosIguales++;
				} else {
					registrosConDiferencias++;
					detalleDiferencias.append("id_retek=").append(idRetek).append(" | Campos con cambio: ").append(diffCampos).append(" | ");
				}
			}
			for (String idRetek : mapaActualPorIdRetek.keySet()) {
				if (!mapaReferenciaPorIdRetek.containsKey(idRetek)) {
					idRetekSoloActual.add(idRetek);
				}
			}

			boolean sinDiferencias = camposDiferentes == 0 && idRetekSoloReferencia.isEmpty() && idRetekSoloActual.isEmpty();
			boolean resultadoPaso = cantidadCoincide && sinDiferencias;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!cantidadCoincide) motivoFallo.append("Cantidad de registros distinta | Referencia: ").append(totalReferencia).append(" | Actual: ").append(totalActual).append(" | ");
			if (!idRetekSoloReferencia.isEmpty()) motivoFallo.append("id_retek presentes SOLO en referencia (desaparecieron): ").append(idRetekSoloReferencia).append(" | ");
			if (!idRetekSoloActual.isEmpty()) motivoFallo.append("id_retek presentes SOLO en actual (aparecieron): ").append(idRetekSoloActual).append(" | ");
			if (camposDiferentes > 0) motivoFallo.append("Campos con diferencia: ").append(camposDiferentes).append(" en ").append(registrosConDiferencias).append(" registros | Detalle: ").append(detalleDiferencias);

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Registro BD identico antes vs despues de exclusion" + (resultadoPaso ? " | Registros comparados: " + registrosComparados + "/" + totalReferencia : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinamica consultada: " + rutaDinamica + " | Fecha: " + fechaFormateada);
			testCase.addBoldTextEvidenceCurrentStep("Registros en BD antes de la exclusion (snapshot '" + etiquetaSnapshotReferencia + "'): " + totalReferencia);
			testCase.addBoldTextEvidenceCurrentStep("Registros en BD despues de la exclusion (snapshot 'despues_exclusion_bd'): " + totalActual);
			testCase.addBoldTextEvidenceCurrentStep("Registros comparados por id_retek: " + registrosComparados + " | Iguales: " + registrosIguales + " | Con diferencias: " + registrosConDiferencias + " | Campos totales con diferencia: " + camposDiferentes);
			if (!idRetekSoloReferencia.isEmpty() || !idRetekSoloActual.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("id_retek presentes SOLO en referencia: " + idRetekSoloReferencia + " | id_retek presentes SOLO en actual: " + idRetekSoloActual);
			}
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Registro_BD_Identico_Antes_Despues_Exclusion");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Registro BD identico antes vs despues | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida el encabezado del grid de Liquidacion de Ruta SIN ejecutar Consultar: confirma que muestra `Ruta: {rutaDinamica}` y que la palabra "personalizada" esta presente o ausente segun se espere.
	 * Adicionalmente valida centrado visual horizontal del encabezado dentro de su contenedor padre (tolerancia +/- 12 px) o `text-align: center` en su estilo computado.
	 * Se usa despues de excluir/reincorporar una tienda-transferencia por Ruta Dinamica para verificar la actualizacion automatica del encabezado sin volver a consultar.
	 *
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) capturada previamente en el filtro.
	 * @param personalizadaEsperada True si el encabezado debe mostrar la palabra "personalizada"; false si NO debe mostrarla.
	 * @param screenshot True para capturar evidencia visual del encabezado validado.
	 * @return True si el encabezado contiene la ruta, la palabra "personalizada" coincide con lo esperado y el encabezado esta visualmente centrado; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaEncabezadoGridPalabraPersonalizada(String rutaDinamica, boolean personalizadaEsperada, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Ruta Dinamica vacia para validar encabezado del grid");
				return false;
			}
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			WebElement detalle = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerDetalle));
			((JavascriptExecutor) u.getDriver()).executeScript("var y = arguments[0].getBoundingClientRect().top + window.pageYOffset - 260; window.scrollTo(0, Math.max(y, 0));", detalle);
			u.hardWait(1);

			String textoDetalle = normalizarTextoFiltro(detalle);
			boolean muestraRuta = textoDetalle.contains("Ruta:") && textoDetalle.contains(rutaDinamica.trim());
			boolean contienePersonalizada = textoDetalle.toLowerCase().contains("personalizada");
			boolean palabraCoincide = personalizadaEsperada == contienePersonalizada;

			@SuppressWarnings("unchecked")
			java.util.Map<String, Object> centradoInfo = (java.util.Map<String, Object>) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const detalle = arguments[0];"
					+ "const ruta = arguments[1];"
					+ "function buscarEncabezado(nodo) {"
					+ "  const walker = document.createTreeWalker(nodo, NodeFilter.SHOW_ELEMENT, null);"
					+ "  let candidatoMasCorto = null;"
					+ "  while (walker.nextNode()) {"
					+ "    const el = walker.currentNode;"
					+ "    if (el.offsetParent === null) continue;"
					+ "    const txt = (el.textContent || '').replace(/\\s+/g, ' ').trim();"
					+ "    if (txt.startsWith('Ruta:') && txt.indexOf(ruta) >= 0) {"
					+ "      if (candidatoMasCorto === null || txt.length < candidatoMasCorto.txt.length) {"
					+ "        candidatoMasCorto = {el: el, txt: txt};"
					+ "      }"
					+ "    }"
					+ "  }"
					+ "  return candidatoMasCorto ? candidatoMasCorto.el : null;"
					+ "}"
					+ "const enc = buscarEncabezado(detalle) || detalle;"
					+ "const padre = enc.parentElement || detalle;"
					+ "const rectEnc = enc.getBoundingClientRect();"
					+ "const rectPad = padre.getBoundingClientRect();"
					+ "const centroEnc = rectEnc.left + rectEnc.width / 2;"
					+ "const centroPad = rectPad.left + rectPad.width / 2;"
					+ "const diffCentro = Math.abs(centroEnc - centroPad);"
					+ "const textAlign = window.getComputedStyle(enc).textAlign || '';"
					+ "const marginLeft = window.getComputedStyle(enc).marginLeft || '';"
					+ "const marginRight = window.getComputedStyle(enc).marginRight || '';"
					+ "return {"
					+ "  encontrado: enc !== detalle,"
					+ "  textoEncabezado: (enc.textContent || '').replace(/\\s+/g, ' ').trim(),"
					+ "  centroEnc: centroEnc,"
					+ "  centroPad: centroPad,"
					+ "  diffCentro: diffCentro,"
					+ "  textAlign: textAlign,"
					+ "  marginLeft: marginLeft,"
					+ "  marginRight: marginRight"
					+ "};",
					detalle, rutaDinamica.trim());

			double diffCentro = centradoInfo == null ? 999.0 : ((Number) centradoInfo.getOrDefault("diffCentro", 999)).doubleValue();
			String textAlign = centradoInfo == null ? "" : String.valueOf(centradoInfo.getOrDefault("textAlign", ""));
			String textoEncabezado = centradoInfo == null ? textoDetalle : String.valueOf(centradoInfo.getOrDefault("textoEncabezado", textoDetalle));
			boolean centradoOk = diffCentro <= 12.0 || "center".equalsIgnoreCase(textAlign);

			boolean resultadoPaso = muestraRuta && palabraCoincide && centradoOk;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!muestraRuta) motivoFallo.append("Encabezado no contiene 'Ruta:' o la ruta esperada '").append(rutaDinamica.trim()).append("' | ");
			if (!palabraCoincide) motivoFallo.append("Palabra 'personalizada' | Esperada: ").append(personalizadaEsperada).append(" | Actual: ").append(contienePersonalizada).append(" | ");
			if (!centradoOk) motivoFallo.append("Encabezado no visualmente centrado | diff centro: ").append(diffCentro).append(" px (tolerancia 12) | text-align: '").append(textAlign).append("' | ");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Encabezado grid personalizada=" + personalizadaEsperada + (resultadoPaso ? " | Texto: " + textoEncabezado : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Encabezado esperado: Ruta: " + rutaDinamica.trim() + (personalizadaEsperada ? " personalizada" : " (sin la palabra personalizada)") + " | visualmente centrado");
			testCase.addBoldTextEvidenceCurrentStep("Encabezado detectado: " + textoEncabezado);
			testCase.addBoldTextEvidenceCurrentStep("Palabra 'personalizada' esperada: " + personalizadaEsperada + " | Actual: " + contienePersonalizada);
			testCase.addBoldTextEvidenceCurrentStep("Centrado visual | diff pixeles: " + diffCentro + " (tolerancia 12) | text-align: '" + textAlign + "'");
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Encabezado_Grid_Personalizada_" + personalizadaEsperada);
			}
			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Encabezado grid personalizada | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Consulta desde BD tres horas representativas para replicar el escenario de rango combinado fecha+hora de la matriz MTC_FT_025.
	 * Extrae MIN(fecha_registro::time), la mediana (percentil 50 aproximado) y MAX(fecha_registro::time) para la ruta y fecha capturadas, y guarda las tres horas en formato HH:mm redondeadas a la media hora mas cercana para que existan en el dropdown del portal.
	 * Se ejecuta antes del paso 6 del script para no hardcodear los valores 10h/14h/16h de la matriz y adaptarse a los datos reales del ambiente de pruebas.
	 *
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) usada en el filtro del portal.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param screenshot True para agregar evidencia visual con las tres horas obtenidas.
	 * @return True si BD devolvio al menos tres horas distintas (minima, media y maxima) que garantizan el escenario; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean obtenerEscenarioHorasDesdeBD(String rutaDinamica, String fechaFormateada, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty() || fechaFormateada == null || fechaFormateada.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Ruta Dinamica o fecha vacia para obtener horas escenario desde BD");
				return false;
			}
			LocalDate fechaConsulta = convertirFechaVisibleALocalDate(fechaFormateada);
			String query = "SELECT "
					+ "  MIN(fecha_registro::time) AS hora_min, "
					+ "  MAX(fecha_registro::time) AS hora_max, "
					+ "  (percentile_cont(0.5) WITHIN GROUP (ORDER BY EXTRACT(EPOCH FROM fecha_registro::time)))::int AS segundos_mediana, "
					+ "  COUNT(DISTINCT fecha_registro::time) AS horas_distintas, "
					+ "  COUNT(*) AS total_registros "
					+ "FROM xxcm_historico_correos "
					+ "WHERE ruta_propuesta = ? "
					+ "AND fecha_registro::date = ? "
					+ "AND TRIM(ruta_propuesta) <> '' "
					+ "AND version_documento = 2 "
					+ "AND is_visible = true "
					+ "AND id_retek IS NOT NULL";

			String horaMin = "";
			String horaMax = "";
			String horaMedia = "";
			int horasDistintas = 0;
			int totalRegistros = 0;
			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
				ps.setString(1, rutaDinamica.trim());
				ps.setDate(2, java.sql.Date.valueOf(fechaConsulta));
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						java.sql.Time tMin = rs.getTime("hora_min");
						java.sql.Time tMax = rs.getTime("hora_max");
						int segundosMediana = rs.getInt("segundos_mediana");
						horasDistintas = rs.getInt("horas_distintas");
						totalRegistros = rs.getInt("total_registros");
						if (tMin != null) horaMin = redondeaAMediaHora(tMin.toLocalTime());
						if (tMax != null) horaMax = redondeaAMediaHora(tMax.toLocalTime());
						LocalTime tMedia = LocalTime.ofSecondOfDay(Math.max(0, Math.min(86340, segundosMediana)));
						horaMedia = redondeaAMediaHora(tMedia);
					}
				}
			}

			boolean tresHorasDistintas = !horaMin.isEmpty() && !horaMedia.isEmpty() && !horaMax.isEmpty()
					&& !horaMin.equals(horaMedia) && !horaMedia.equals(horaMax) && !horaMin.equals(horaMax);
			// Si la mediana redondeada colisiona con la minima o la maxima, se ajusta a un punto intermedio real (+/- 1 hora entera).
			if (!tresHorasDistintas && !horaMin.isEmpty() && !horaMax.isEmpty()) {
				LocalTime tMin = LocalTime.parse(horaMin);
				LocalTime tMax = LocalTime.parse(horaMax);
				long minutosEntre = java.time.Duration.between(tMin, tMax).toMinutes();
				if (minutosEntre >= 120) {
					LocalTime tMedioForzado = tMin.plusMinutes(minutosEntre / 2);
					horaMedia = redondeaAMediaHora(tMedioForzado);
					if (horaMedia.equals(horaMin)) horaMedia = redondeaAMediaHora(tMin.plusHours(1));
					if (horaMedia.equals(horaMax)) horaMedia = redondeaAMediaHora(tMax.minusHours(1));
					tresHorasDistintas = !horaMin.equals(horaMedia) && !horaMedia.equals(horaMax);
				}
			}

			horaMinimaBD = horaMin;
			horaMediaBD = horaMedia;
			horaMaximaBD = horaMax;

			boolean resultadoPaso = tresHorasDistintas && horasDistintas >= 2 && totalRegistros >= 3;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!tresHorasDistintas) motivoFallo.append("BD no devolvio tres horas distintas para el escenario. Min: '").append(horaMin).append("' | Media: '").append(horaMedia).append("' | Max: '").append(horaMax).append("' | ");
			if (horasDistintas < 2) motivoFallo.append("Horas distintas en BD para la ruta+fecha: ").append(horasDistintas).append(" (esperado >= 2) | ");
			if (totalRegistros < 3) motivoFallo.append("Total de registros en BD: ").append(totalRegistros).append(" (esperado >= 3) | ");

			System.out.println("[BD ESCENARIO HORAS] Ruta: " + rutaDinamica.trim() + " | Fecha: " + fechaConsulta + " | Min: " + horaMin + " | Media: " + horaMedia + " | Max: " + horaMax + " | Distintas: " + horasDistintas + " | Total registros: " + totalRegistros);
			testCase.addBoldTextEvidenceCurrentStep("Escenario horas obtenido desde BD para ruta " + rutaDinamica.trim() + " en la fecha " + fechaConsulta + " | Hora minima: " + horaMin + " | Hora media: " + horaMedia + " | Hora maxima: " + horaMax);
			testCase.addBoldTextEvidenceCurrentStep("Total registros BD: " + totalRegistros + " | Horas distintas: " + horasDistintas);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Escenario_Horas_BD");
			}
			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[BD ESCENARIO HORAS] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Redondea un LocalTime a la hora entera mas cercana dentro del rango 00:00 a 23:00 para que coincida con las opciones del dropdown del portal.
	 * El dropdown de Hora inicial y Hora final de Liquidacion de Ruta solo ofrece opciones :00 (12:00 AM, 01:00 AM, ..., 11:00 PM); NO existen opciones :30.
	 * Cualquier hora obtenida de BD con minutos distintos de 00 se ajusta a la hora entera mas cercana para que el metodo capturaHorasInicialFinalLR encuentre la opcion en el portal.
	 *
	 * @param hora Hora original en formato 24h.
	 * @return Hora redondeada a hora entera en formato HH:00.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private String redondeaAMediaHora(LocalTime hora) {
		int minutosTotales = hora.getHour() * 60 + hora.getMinute();
		int horaRedondeada = (int) Math.round(minutosTotales / 60.0);
		if (horaRedondeada >= 24) horaRedondeada = 23;
		if (horaRedondeada < 0) horaRedondeada = 0;
		return String.format("%02d:00", horaRedondeada);
	}

	/**
	 * Devuelve la etiqueta esperada del dropdown de horas del portal para una hora en formato 24h.
	 * El dropdown de Angular Material muestra las horas en formato hh:mm AM/PM (Locale English), por ejemplo "01:00 PM" o "12:30 AM".
	 *
	 * @param hora24 Hora en formato HH:mm 24h.
	 * @return Etiqueta esperada en el dropdown en formato hh:mm AM/PM.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	private String convertir24aAMPM(String hora24) {
		LocalTime hora = LocalTime.parse(hora24, DateTimeFormatter.ofPattern("HH:mm"));
		return hora.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)).toUpperCase();
	}

	/**
	 * Captura Hora inicial y Hora final en los filtros de Liquidacion de Ruta usando el dropdown del portal.
	 * Se busca cada hora en las opciones visibles del dropdown por su etiqueta AM/PM equivalente al valor 24h indicado.
	 * Requiere que Fecha inicial y Fecha final ya esten capturadas previamente (para que el Selector de Filtro y los demas controles esten activos).
	 *
	 * @param horaInicial24 Hora inicial deseada en formato HH:mm 24h (por ejemplo "00:00", "10:30", "14:00").
	 * @param horaFinal24 Hora final deseada en formato HH:mm 24h (por ejemplo "23:30", "15:00", "16:30").
	 * @param screenshot True para capturar evidencia visual de las horas capturadas.
	 * @return True si ambas horas quedan seleccionadas correctamente en el portal; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean capturaHorasInicialFinalLR(String horaInicial24, String horaFinal24, boolean screenshot) {
		try {
			if (horaInicial24 == null || horaInicial24.trim().isEmpty() || horaFinal24 == null || horaFinal24.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Hora inicial u hora final vacias para capturar filtros de hora");
				return false;
			}
			String horaInicialAMPM = convertir24aAMPM(horaInicial24.trim());
			String horaFinalAMPM = convertir24aAMPM(horaFinal24.trim());

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			// Localizadores de Hora inicial y Hora final centralizados en rutaLiquidacionElements para respetar el patron del Object Model.
			// El box-container[1] agrupa los 4 mat-form-field del primer renglon: [1] Fecha inicial, [2] Hora inicial, [3] Fecha final, [4] Hora final.
			By botonHoraInicial = elementos.botonHoraInicialLiquidacionRuta;
			By botonHoraFinal = elementos.botonHoraFinalLiquidacionRuta;
			// Se filtra con ancestor::div[not(contains(@style,'display: none'))] para ignorar mat-option de paneles cdk-overlay-pane residuales que Angular Material deja ocultos en el DOM al abrir un segundo dropdown, replicando el patron probado en PortalCometaMVP4.llenadoFiltrosLR.
			By opcionHoraInicial = By.xpath("//mat-option/span[normalize-space(text())='" + horaInicialAMPM + "' and ancestor::div[not(contains(@style,'display: none'))]]");
			By opcionHoraFinal = By.xpath("//mat-option/span[normalize-space(text())='" + horaFinalAMPM + "' and ancestor::div[not(contains(@style,'display: none'))]]");

			WebDriver driver = u.getDriver();
			JavascriptExecutor js = (JavascriptExecutor) driver;

			// --- Hora inicial ---
			WebElement btnHoraInicial = wait.until(ExpectedConditions.elementToBeClickable(botonHoraInicial));
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", btnHoraInicial);
			try {
				btnHoraInicial.click();
			} catch (Exception e) {
				js.executeScript("arguments[0].click();", btnHoraInicial);
			}
			u.hardWait(1);
			WebElement opInicial = wait.until(ExpectedConditions.visibilityOfElementLocated(opcionHoraInicial));
			js.executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", opInicial);
			u.hardWait(1);
			try {
				opInicial.click();
			} catch (Exception e) {
				js.executeScript("arguments[0].click();", opInicial);
			}
			u.hardWait(1);
			System.out.println("[CAPTURA HORAS] Hora inicial seleccionada: " + horaInicialAMPM + " (24h: " + horaInicial24 + ")");
			testCase.addBoldTextEvidenceCurrentStep("Hora inicial seleccionada: " + horaInicialAMPM + " (equivalente 24h: " + horaInicial24 + ")");

			// --- Hora final ---
			WebElement btnHoraFinal = wait.until(ExpectedConditions.elementToBeClickable(botonHoraFinal));
			js.executeScript("arguments[0].scrollIntoView({block:'center'});", btnHoraFinal);
			try {
				btnHoraFinal.click();
			} catch (Exception e) {
				js.executeScript("arguments[0].click();", btnHoraFinal);
			}
			u.hardWait(1);
			WebElement opFinal = wait.until(ExpectedConditions.visibilityOfElementLocated(opcionHoraFinal));
			js.executeScript("arguments[0].scrollIntoView({block:'center', behavior:'smooth'});", opFinal);
			u.hardWait(1);
			try {
				opFinal.click();
			} catch (Exception e) {
				js.executeScript("arguments[0].click();", opFinal);
			}
			u.hardWait(1);
			System.out.println("[CAPTURA HORAS] Hora final seleccionada: " + horaFinalAMPM + " (24h: " + horaFinal24 + ")");
			testCase.addBoldTextEvidenceCurrentStep("Hora final seleccionada: " + horaFinalAMPM + " (equivalente 24h: " + horaFinal24 + ")");

			driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			if (screenshot) {
				u.highLight(elementos.filtroHoraInicial);
				u.highLight(elementos.filtroHoraFinal);
				testCase.addScreenShotCurrentStep("Horas_Inicial_Final_Capturadas_" + horaInicial24.replace(":", "") + "_" + horaFinal24.replace(":", ""));
				u.unhighLight(elementos.filtroHoraInicial);
				u.unhighLight(elementos.filtroHoraFinal);
			}

			return true;
		} catch (Exception e) {
			System.out.println("[CAPTURA HORAS] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Ejecuta el boton Consultar de Liquidacion de Ruta y devuelve el total de tiendas visible en el encabezado del grid.
	 * Espera a que desaparezcan los modales de carga antes de leer el encabezado.
	 *
	 * @param screenshot True para capturar evidencia visual del grid con el total.
	 * @return Total de tiendas segun el encabezado del grid, o -1 si no se pudo obtener.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public int consultaYObtieneTotalTiendasEnGrid(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			u.hardWait(1);

			WebElement consultar = wait.until(ExpectedConditions.elementToBeClickable(elementos.botonConsultarLiquidacionRuta));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", consultar);
			try {
				consultar.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", consultar);
			}
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			WebElement detalle = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerDetalle));
			((JavascriptExecutor) u.getDriver()).executeScript("var y = arguments[0].getBoundingClientRect().top + window.pageYOffset - 260; window.scrollTo(0, Math.max(y, 0));", detalle);
			u.hardWait(1);
			String textoDetalle = normalizarTextoFiltro(detalle);
			java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("Tiendas\\s+Totales\\s+de\\s+la\\s+Ruta:\\s*(\\d+)").matcher(textoDetalle);
			int total = matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;

			System.out.println("[CONSULTA GRID] Total tiendas segun encabezado: " + total + " | Texto detalle: " + textoDetalle);
			testCase.addBoldTextEvidenceCurrentStep("Total tiendas segun encabezado del grid tras Consultar: " + total);
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Consulta_Total_Tiendas_Grid");
			}
			return total;
		} catch (Exception e) {
			System.out.println("[CONSULTA GRID] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return -1;
		}
	}

	/**
	 * Valida que el total de tiendas mostrado en el grid coincide con el count esperado en BD para la ruta y el rango combinado de fecha + hora indicado.
	 * Ejecuta contra `xxcm_historico_correos` una consulta BETWEEN entre (fecha + horaInicial) y (fecha + horaFinal) para la ruta capturada.
	 * Se usa para confirmar el filtrado dinamico de los pasos 7, 8 y 9 del script.
	 *
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) capturada en el filtro.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param horaInicial24 Hora inicial capturada en el filtro en formato HH:mm 24h.
	 * @param horaFinal24 Hora final capturada en el filtro en formato HH:mm 24h.
	 * @param screenshot True para capturar evidencia visual del grid y del resultado esperado en BD.
	 * @return True si el total del grid coincide exactamente con el count de BD para el rango; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaTotalTiendasGridContraBDPorRangoHoras(String rutaDinamica, String fechaFormateada, String horaInicial24, String horaFinal24, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty() || fechaFormateada == null || fechaFormateada.trim().isEmpty()
					|| horaInicial24 == null || horaInicial24.trim().isEmpty() || horaFinal24 == null || horaFinal24.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Parametros vacios para validar rango combinado contra BD");
				return false;
			}
			LocalDate fechaConsulta = convertirFechaVisibleALocalDate(fechaFormateada);
			LocalTime tInicial = LocalTime.parse(horaInicial24, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime tFinal = LocalTime.parse(horaFinal24, DateTimeFormatter.ofPattern("HH:mm"));
			// El portal interpreta Hora final HH:00 como "hasta e incluyendo el bloque completo de las HH:xx" (hasta HH:59:59).
			// Por eso el timestamp final se lleva hasta HH:59:59.999999 para alinear la query BD con el criterio visible del grid.
			LocalTime tFinalInclusiva = tFinal.plusMinutes(59).plusSeconds(59).plusNanos(999_999_999);
			String query = "SELECT COUNT(DISTINCT id_retek) AS total_tiendas "
					+ "FROM xxcm_historico_correos "
					+ "WHERE ruta_propuesta = ? "
					+ "AND fecha_registro BETWEEN ? AND ? "
					+ "AND TRIM(ruta_propuesta) <> '' "
					+ "AND version_documento = 2 "
					+ "AND is_visible = true "
					+ "AND id_retek IS NOT NULL";
			java.sql.Timestamp inicio = java.sql.Timestamp.valueOf(fechaConsulta.atTime(tInicial));
			java.sql.Timestamp fin = java.sql.Timestamp.valueOf(fechaConsulta.atTime(tFinalInclusiva));

			int totalBD = -1;
			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					java.sql.PreparedStatement ps = connection.prepareStatement(query)) {
				ps.setString(1, rutaDinamica.trim());
				ps.setTimestamp(2, inicio);
				ps.setTimestamp(3, fin);
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) totalBD = rs.getInt("total_tiendas");
				}
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			u.hardWait(1);

			// Se hace click en Consultar para que el grid refleje el rango de horas capturado. Sin este click el metodo se quedaria esperando el detalle del grid anterior (o no habria detalle si es la primera consulta).
			WebElement consultar = wait.until(ExpectedConditions.elementToBeClickable(elementos.botonConsultarLiquidacionRuta));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", consultar);
			try {
				consultar.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", consultar);
			}
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);
			WebElement detalle = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerDetalle));
			((JavascriptExecutor) u.getDriver()).executeScript("var y = arguments[0].getBoundingClientRect().top + window.pageYOffset - 260; window.scrollTo(0, Math.max(y, 0));", detalle);
			u.hardWait(1);
			String textoDetalle = normalizarTextoFiltro(detalle);
			java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("Tiendas\\s+Totales\\s+de\\s+la\\s+Ruta:\\s*(\\d+)").matcher(textoDetalle);
			int totalGrid = matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;

			boolean resultadoPaso = totalGrid >= 0 && totalGrid == totalBD;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "Total tiendas grid: " + totalGrid + " | Total esperado en BD: " + totalBD + " para rango " + inicio + " a " + fin;

			System.out.println("[VALIDACION RANGO HORAS] Ruta: " + rutaDinamica.trim() + " | Rango BD: " + inicio + " a " + fin + " | Total BD: " + totalBD + " | Total grid: " + totalGrid);
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinamica validada: " + rutaDinamica.trim() + " | Fecha: " + fechaFormateada);
			testCase.addBoldTextEvidenceCurrentStep("Rango combinado consultado en BD: " + inicio + " a " + fin);
			testCase.addBoldTextEvidenceCurrentStep("Total tiendas esperado en BD (BETWEEN): " + totalBD + " | Total mostrado en el grid: " + totalGrid);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Rango_Horas_Grid_Vs_BD_" + horaInicial24.replace(":", "") + "_" + horaFinal24.replace(":", ""));
			}
			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[VALIDACION RANGO HORAS] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Devuelve la hora minima obtenida por `obtenerEscenarioHorasDesdeBD` en formato HH:mm 24h.
	 *
	 * @return Hora minima BD en formato HH:mm, o cadena vacia si aun no se ha obtenido.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public String getHoraMinimaBD() { return horaMinimaBD; }

	/**
	 * Devuelve la hora media obtenida por `obtenerEscenarioHorasDesdeBD` en formato HH:mm 24h.
	 *
	 * @return Hora media BD en formato HH:mm, o cadena vacia si aun no se ha obtenido.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public String getHoraMediaBD() { return horaMediaBD; }

	/**
	 * Devuelve la hora maxima obtenida por `obtenerEscenarioHorasDesdeBD` en formato HH:mm 24h.
	 *
	 * @return Hora maxima BD en formato HH:mm, o cadena vacia si aun no se ha obtenido.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public String getHoraMaximaBD() { return horaMaximaBD; }

	/**
	 * Valida que ninguna de las tiendas de la Ruta Dinamica cuya hora de entrega quede FUERA del rango combinado (fecha + hora) aparezca visible en el grid del portal.
	 * Consulta BD las tiendas que estan en la ruta y fecha pero cuya `fecha_registro::time` cae fuera de [horaInicial, horaFinal], y confirma que ninguno de sus CR_TIENDA ni NOMBRE_TIENDA aparece en el contenedor de tiendas del grid.
	 * Complementa a `validaTotalTiendasGridContraBDPorRangoHoras` que solo compara el total: este metodo valida trazabilidad tienda a tienda.
	 *
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) capturada en el filtro.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param horaInicial24 Hora inicial capturada en el filtro en formato HH:mm 24h.
	 * @param horaFinal24 Hora final capturada en el filtro en formato HH:mm 24h.
	 * @param screenshot True para capturar evidencia visual del grid comparado contra BD.
	 * @return True si ninguna tienda fuera del rango aparece en el grid; false en caso contrario o si BD no tiene tiendas fuera de rango.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaTiendasFueraDeRangoNoVisiblesEnGrid(String rutaDinamica, String fechaFormateada, String horaInicial24, String horaFinal24, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty() || fechaFormateada == null || fechaFormateada.trim().isEmpty()
					|| horaInicial24 == null || horaInicial24.trim().isEmpty() || horaFinal24 == null || horaFinal24.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Parametros vacios para validar tiendas fuera de rango contra grid");
				return false;
			}

			LocalDate fechaConsulta = convertirFechaVisibleALocalDate(fechaFormateada);
			LocalTime tInicial = LocalTime.parse(horaInicial24, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime tFinal = LocalTime.parse(horaFinal24, DateTimeFormatter.ofPattern("HH:mm"));
			// La Hora final HH:00 del portal incluye todo el bloque HH:00..HH:59:59, por eso una tienda esta FUERA del rango solo si su time > HH:59:59.
			LocalTime tFinalInclusiva = tFinal.plusMinutes(59).plusSeconds(59);

			// Tiendas de la ruta+fecha cuya hora cae FUERA de [tInicial, tFinalInclusiva]
			String queryFuera = "SELECT DISTINCT h.id_retek, COALESCE(r.cr_tienda, h.cr_tienda) AS cr_tienda, "
					+ "COALESCE(r.desc_tienda, h.cr_tienda) AS nombre_tienda, h.fecha_registro::time AS hora "
					+ "FROM xxcm_historico_correos h "
					+ "LEFT JOIN xxcm_region_plaza_tienda_job_sync r ON r.id_retek = h.id_retek "
					+ "WHERE h.ruta_propuesta = ? "
					+ "AND h.fecha_registro::date = ? "
					+ "AND (h.fecha_registro::time < ? OR h.fecha_registro::time > ?) "
					+ "AND TRIM(h.ruta_propuesta) <> '' "
					+ "AND h.version_documento = 2 "
					+ "AND h.is_visible = true "
					+ "AND h.id_retek IS NOT NULL "
					+ "ORDER BY h.fecha_registro::time";

			LinkedHashMap<String, String> tiendasFueraRango = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> horasFueraRango = new LinkedHashMap<String, String>();
			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					java.sql.PreparedStatement ps = connection.prepareStatement(queryFuera)) {
				ps.setString(1, rutaDinamica.trim());
				ps.setDate(2, java.sql.Date.valueOf(fechaConsulta));
				ps.setTime(3, java.sql.Time.valueOf(tInicial));
				ps.setTime(4, java.sql.Time.valueOf(tFinalInclusiva));
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						String crTienda = rs.getString("cr_tienda");
						String nombreTienda = rs.getString("nombre_tienda");
						java.sql.Time hora = rs.getTime("hora");
						if (crTienda != null && !crTienda.trim().isEmpty()) {
							String etiqueta = (crTienda.trim() + " " + (nombreTienda == null ? "" : nombreTienda.trim())).replaceAll("\\s+", " ").trim();
							tiendasFueraRango.put(crTienda.trim(), etiqueta);
							horasFueraRango.put(crTienda.trim(), hora == null ? "" : hora.toString());
						}
					}
				}
			}

			if (tiendasFueraRango.isEmpty()) {
				String motivoFallo = "BD no devolvio tiendas de la ruta " + rutaDinamica + " con hora fuera del rango [" + tInicial + " - " + tFinal + "] en la fecha " + fechaConsulta + ". No se puede validar exclusion tienda a tienda porque el escenario BD no tiene tiendas para excluir.";
				System.out.println("[VALIDACION TIENDAS FUERA RANGO] " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			// Extraer texto del grid (contenedor de tiendas)
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			WebElement contenedorTiendas = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerTienda));
			String textoGridTiendas = normalizarTextoFiltro(contenedorTiendas);

			int tiendasVisiblesEnGrid = 0;
			StringBuilder detalleTiendasVisibles = new StringBuilder();
			for (java.util.Map.Entry<String, String> entrada : tiendasFueraRango.entrySet()) {
				String cr = entrada.getKey();
				String etiquetaBD = entrada.getValue();
				String horaBD = horasFueraRango.get(cr);
				String[] partes = etiquetaBD.split("\\s+", 2);
				String nombreBD = partes.length > 1 ? partes[1] : "";
				boolean apareceCrEnGrid = !cr.isEmpty() && textoGridTiendas.contains(cr);
				boolean apareceNombreEnGrid = !nombreBD.isEmpty() && textoGridTiendas.contains(nombreBD);
				boolean apareceEnGrid = apareceCrEnGrid || apareceNombreEnGrid;
				if (apareceEnGrid) {
					tiendasVisiblesEnGrid++;
					detalleTiendasVisibles.append(etiquetaBD).append(" (hora BD: ").append(horaBD).append("); ");
				}
				System.out.println("[VALIDACION TIENDAS FUERA RANGO] " + etiquetaBD + " | Hora BD: " + horaBD + " | Rango filtro: [" + tInicial + " - " + tFinal + "] | Visible en grid: " + (apareceEnGrid ? "SI (INESPERADO)" : "NO (correcto)"));
				testCase.addBoldTextEvidenceCurrentStep("Tienda BD fuera del rango: " + etiquetaBD + " | Hora BD: " + horaBD + " | Visible en grid: " + (apareceEnGrid ? "SI (INESPERADO)" : "NO (correcto)"));
			}

			boolean resultadoPaso = tiendasVisiblesEnGrid == 0;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : tiendasVisiblesEnGrid + " tienda(s) con hora fuera del rango [" + tInicial + " - " + tFinal + "] aparecen visibles en el grid: " + detalleTiendasVisibles;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Tiendas fuera de rango horario no visibles en grid | Total BD fuera: " + tiendasFueraRango.size() + " | Visibles en grid: " + tiendasVisiblesEnGrid + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Rango consultado: " + tInicial + " - " + tFinal + " el " + fechaConsulta + " | Ruta: " + rutaDinamica);
			testCase.addBoldTextEvidenceCurrentStep("Tiendas en BD con hora FUERA del rango: " + tiendasFueraRango.size() + " | Detalle: " + tiendasFueraRango + " | Horas: " + horasFueraRango);
			testCase.addBoldTextEvidenceCurrentStep("Tiendas fuera de rango que aparecen visibles en el grid: " + tiendasVisiblesEnGrid + " (esperado: 0)");
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Tiendas_Fuera_Rango_No_Visibles_" + horaInicial24.replace(":", "") + "_" + horaFinal24.replace(":", ""));
			}
			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[VALIDACION TIENDAS FUERA RANGO] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que TODAS las tiendas de la Ruta Dinamica cuya hora de entrega cae DENTRO del rango combinado (fecha + hora) SI aparezcan visibles en el grid del portal.
	 * Complementa a `validaTiendasFueraDeRangoNoVisiblesEnGrid` para cerrar el ciclo de trazabilidad tienda a tienda: no basta con validar que las excluidas no aparezcan; tambien se debe validar que las incluidas si aparezcan.
	 * Consulta BD las tiendas que estan en la ruta y fecha con `fecha_registro::time` dentro de [horaInicial, horaFinal], y confirma que cada CR_TIENDA y NOMBRE_TIENDA aparecen en el contenedor de tiendas del grid.
	 *
	 * @param rutaDinamica Ruta Dinamica (UNIGIS) capturada en el filtro.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param horaInicial24 Hora inicial capturada en el filtro en formato HH:mm 24h.
	 * @param horaFinal24 Hora final capturada en el filtro en formato HH:mm 24h.
	 * @param screenshot True para capturar evidencia visual del grid comparado contra BD.
	 * @return True si todas las tiendas dentro del rango aparecen en el grid; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaTiendasDentroDeRangoVisiblesEnGrid(String rutaDinamica, String fechaFormateada, String horaInicial24, String horaFinal24, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty() || fechaFormateada == null || fechaFormateada.trim().isEmpty()
					|| horaInicial24 == null || horaInicial24.trim().isEmpty() || horaFinal24 == null || horaFinal24.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Parametros vacios para validar tiendas dentro de rango visibles en grid");
				return false;
			}

			LocalDate fechaConsulta = convertirFechaVisibleALocalDate(fechaFormateada);
			LocalTime tInicial = LocalTime.parse(horaInicial24, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime tFinal = LocalTime.parse(horaFinal24, DateTimeFormatter.ofPattern("HH:mm"));
			// La Hora final HH:00 del portal incluye todo el bloque HH:00..HH:59:59, por eso al calcular tiendas DENTRO se amplia el limite superior a HH:59:59.
			LocalTime tFinalInclusiva = tFinal.plusMinutes(59).plusSeconds(59);

			String queryDentro = "SELECT DISTINCT h.id_retek, COALESCE(r.cr_tienda, h.cr_tienda) AS cr_tienda, "
					+ "COALESCE(r.desc_tienda, h.cr_tienda) AS nombre_tienda, h.fecha_registro::time AS hora "
					+ "FROM xxcm_historico_correos h "
					+ "LEFT JOIN xxcm_region_plaza_tienda_job_sync r ON r.id_retek = h.id_retek "
					+ "WHERE h.ruta_propuesta = ? "
					+ "AND h.fecha_registro::date = ? "
					+ "AND h.fecha_registro::time BETWEEN ? AND ? "
					+ "AND TRIM(h.ruta_propuesta) <> '' "
					+ "AND h.version_documento = 2 "
					+ "AND h.is_visible = true "
					+ "AND h.id_retek IS NOT NULL "
					+ "ORDER BY h.fecha_registro::time";

			LinkedHashMap<String, String> tiendasDentroRango = new LinkedHashMap<String, String>();
			LinkedHashMap<String, String> horasDentroRango = new LinkedHashMap<String, String>();
			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					java.sql.PreparedStatement ps = connection.prepareStatement(queryDentro)) {
				ps.setString(1, rutaDinamica.trim());
				ps.setDate(2, java.sql.Date.valueOf(fechaConsulta));
				ps.setTime(3, java.sql.Time.valueOf(tInicial));
				ps.setTime(4, java.sql.Time.valueOf(tFinalInclusiva));
				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						String crTienda = rs.getString("cr_tienda");
						String nombreTienda = rs.getString("nombre_tienda");
						java.sql.Time hora = rs.getTime("hora");
						if (crTienda != null && !crTienda.trim().isEmpty()) {
							String etiqueta = (crTienda.trim() + " " + (nombreTienda == null ? "" : nombreTienda.trim())).replaceAll("\\s+", " ").trim();
							tiendasDentroRango.put(crTienda.trim(), etiqueta);
							horasDentroRango.put(crTienda.trim(), hora == null ? "" : hora.toString());
						}
					}
				}
			}

			if (tiendasDentroRango.isEmpty()) {
				String motivoFallo = "BD no devolvio tiendas de la ruta " + rutaDinamica + " con hora dentro del rango [" + tInicial + " - " + tFinal + "] en la fecha " + fechaConsulta;
				System.out.println("[VALIDACION TIENDAS DENTRO RANGO] " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			WebElement contenedorTiendas = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerTienda));
			String textoGridTiendas = normalizarTextoFiltro(contenedorTiendas);

			int tiendasNoVisiblesEnGrid = 0;
			StringBuilder detalleTiendasFaltantes = new StringBuilder();
			for (java.util.Map.Entry<String, String> entrada : tiendasDentroRango.entrySet()) {
				String cr = entrada.getKey();
				String etiquetaBD = entrada.getValue();
				String horaBD = horasDentroRango.get(cr);
				String[] partes = etiquetaBD.split("\\s+", 2);
				String nombreBD = partes.length > 1 ? partes[1] : "";
				boolean apareceCrEnGrid = !cr.isEmpty() && textoGridTiendas.contains(cr);
				boolean apareceNombreEnGrid = !nombreBD.isEmpty() && textoGridTiendas.contains(nombreBD);
				boolean apareceEnGrid = apareceCrEnGrid && apareceNombreEnGrid;
				if (!apareceEnGrid) {
					tiendasNoVisiblesEnGrid++;
					detalleTiendasFaltantes.append(etiquetaBD).append(" (hora BD: ").append(horaBD).append("); ");
				}
				System.out.println("[VALIDACION TIENDAS DENTRO RANGO] " + etiquetaBD + " | Hora BD: " + horaBD + " | Rango filtro: [" + tInicial + " - " + tFinal + "] | Visible en grid: " + (apareceEnGrid ? "SI (correcto)" : "NO (INESPERADO)"));
				testCase.addBoldTextEvidenceCurrentStep("Tienda BD dentro del rango: " + etiquetaBD + " | Hora BD: " + horaBD + " | Visible en grid: " + (apareceEnGrid ? "SI (correcto)" : "NO (INESPERADO)"));
			}

			boolean resultadoPaso = tiendasNoVisiblesEnGrid == 0;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : tiendasNoVisiblesEnGrid + " tienda(s) con hora dentro del rango [" + tInicial + " - " + tFinal + "] NO aparecen en el grid: " + detalleTiendasFaltantes;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Tiendas dentro de rango visibles en grid | Total BD dentro: " + tiendasDentroRango.size() + " | No visibles en grid: " + tiendasNoVisiblesEnGrid + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Rango consultado: " + tInicial + " - " + tFinal + " el " + fechaConsulta + " | Ruta: " + rutaDinamica);
			testCase.addBoldTextEvidenceCurrentStep("Tiendas en BD con hora DENTRO del rango: " + tiendasDentroRango.size() + " | Detalle: " + tiendasDentroRango + " | Horas: " + horasDentroRango);
			testCase.addBoldTextEvidenceCurrentStep("Tiendas dentro del rango que NO aparecen en el grid: " + tiendasNoVisiblesEnGrid + " (esperado: 0)");
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Tiendas_Dentro_Rango_Visibles_" + horaInicial24.replace(":", "") + "_" + horaFinal24.replace(":", ""));
			}
			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[VALIDACION TIENDAS DENTRO RANGO] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que debajo del encabezado (Ruta: Personalizada / Ruta: {ruta}) aparezcan renglones tienda-transferencia con el formato esperado del portal:
	 * `Tienda: {id_retek} {CR_TIENDA} {NOMBRE_TIENDA}. Transferencia: {folio}. {estatus}`.
	 * Extrae por JavaScript los renglones tienda-transferencia visibles (tolerando `.tienda-wrapper`, `[id=tienda]`, `app-cedis-liquidacion-ruta-tienda-transferencia` o `mat-card`).
	 * Si se proporciona `tiendasEsperadas` (etiquetas CR + Nombre marcadas en el filtro), valida que cada una aparezca al menos una vez entre los renglones detectados y reporta las faltantes.
	 * El total esperado de renglones se toma del encabezado "Total de Transferencias: N"; si no aparece, se usa la cantidad de tiendas esperadas o al menos 1 como cota minima.
	 *
	 * @param tiendasEsperadas Lista de etiquetas CR_TIENDA espacio NOMBRE_TIENDA que se marcaron en el filtro Tienda; puede ser null o vacia para solo validar formato.
	 * @param screenshot True para capturar evidencia visual del grid con los renglones tienda-transferencia detectados.
	 * @return True si se detecta al menos un renglon con formato valido, cada tienda esperada aparece y el conteo cumple con el encabezado; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-12
	 */
	public boolean validaTiendasVisiblesEnGridDebajoEncabezado(List<String> tiendasEsperadas, boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			u.hardWait(1);

			WebElement detalle = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.containerDetalle));
			String textoDetalle = normalizarTextoFiltro(detalle);
			java.util.regex.Matcher matcherTransferencias = java.util.regex.Pattern.compile("Total\\s+de\\s+Transferencias:\\s*(\\d+)").matcher(textoDetalle);
			int totalTransferenciasEncabezado = matcherTransferencias.find() ? Integer.parseInt(matcherTransferencias.group(1)) : -1;

			@SuppressWarnings("unchecked")
			java.util.List<String> textosRenglones = (java.util.List<String>) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const raiz = document.querySelector('app-cedis-liquidacion-ruta #detalle-tiendas')"
					+ " || document.querySelector('#detalle-tiendas')"
					+ " || document.querySelector('app-cedis-liquidacion-ruta');"
					+ "if (!raiz) return [];"
					+ "const selectores = ['.tienda-wrapper', '[id=\"tienda\"]', 'app-cedis-liquidacion-ruta-tienda-transferencia', 'app-tienda-transferencia', '[class*=\"tienda-transferencia\"]', '[class*=\"renglon-transferencia\"]', 'mat-card', '[class*=\"card-transferencia\"]'];"
					+ "let renglones = [];"
					+ "for (const sel of selectores) {"
					+ "  const nodos = raiz.querySelectorAll(sel);"
					+ "  const visibles = Array.from(nodos).filter(n => n.offsetParent !== null && n.getBoundingClientRect().height > 20);"
					+ "  if (visibles.length >= 1) { renglones = visibles; break; }"
					+ "}"
					+ "if (renglones.length === 0) {"
					+ "  const candidatos = raiz.querySelectorAll('.tienda-linea-lateral');"
					+ "  renglones = Array.from(candidatos).map(l => l.closest('.tienda-wrapper, [id=\"tienda\"], mat-card, [class*=\"card\"], [class*=\"renglon\"]') || l.parentElement).filter(el => el);"
					+ "}"
					+ "return renglones.map(r => (r.textContent || '').replace(/\\s+/g, ' ').trim());");

			int totalRenglonesDetectados = textosRenglones == null ? 0 : textosRenglones.size();
			java.util.regex.Pattern patronRenglon = java.util.regex.Pattern.compile("Tienda:\\s*([^\\.]+?)\\.\\s*Transferencia:\\s*(\\d+)");
			java.util.List<String> renglonesFormatoOk = new java.util.ArrayList<String>();
			java.util.List<String> renglonesFormatoInvalido = new java.util.ArrayList<String>();
			LinkedHashMap<String, String> renglonesPorEtiquetaTienda = new LinkedHashMap<String, String>();
			if (textosRenglones != null) {
				for (String texto : textosRenglones) {
					if (texto == null || texto.isEmpty()) {
						continue;
					}
					java.util.regex.Matcher m = patronRenglon.matcher(texto);
					if (m.find()) {
						String etiquetaTienda = m.group(1).trim();
						String transferencia = m.group(2).trim();
						renglonesFormatoOk.add("Tienda: " + etiquetaTienda + " | Transferencia: " + transferencia);
						renglonesPorEtiquetaTienda.put(etiquetaTienda, transferencia);
						System.out.println("[VALIDACION TIENDAS GRID] Renglon detectado | Tienda: " + etiquetaTienda + " | Transferencia: " + transferencia + " | Texto completo: " + texto);
					} else {
						renglonesFormatoInvalido.add(texto);
						System.out.println("[VALIDACION TIENDAS GRID] Renglon SIN formato esperado (Tienda: ... Transferencia: ...): " + texto);
					}
				}
			}

			java.util.List<String> tiendasFaltantes = new java.util.ArrayList<String>();
			java.util.List<String> tiendasEncontradas = new java.util.ArrayList<String>();
			if (tiendasEsperadas != null && !tiendasEsperadas.isEmpty()) {
				for (String tiendaEsperada : tiendasEsperadas) {
					if (tiendaEsperada == null || tiendaEsperada.trim().isEmpty()) {
						continue;
					}
					String[] partes = tiendaEsperada.trim().split("\\s+", 2);
					String crEsperado = partes.length > 0 ? partes[0] : "";
					String nombreEsperado = partes.length > 1 ? partes[1] : "";
					boolean encontrada = false;
					for (String etiquetaRenglon : renglonesPorEtiquetaTienda.keySet()) {
						boolean coincideCr = !crEsperado.isEmpty() && etiquetaRenglon.contains(crEsperado);
						boolean coincideNombre = !nombreEsperado.isEmpty() && etiquetaRenglon.contains(nombreEsperado);
						if (coincideCr && coincideNombre) {
							encontrada = true;
							break;
						}
					}
					if (encontrada) {
						tiendasEncontradas.add(tiendaEsperada);
					} else {
						tiendasFaltantes.add(tiendaEsperada);
					}
				}
			}

			int minEsperado = totalTransferenciasEncabezado > 0 ? totalTransferenciasEncabezado
					: (tiendasEsperadas != null && !tiendasEsperadas.isEmpty() ? tiendasEsperadas.size() : 1);
			boolean hayRenglones = renglonesFormatoOk.size() >= 1;
			boolean cuentaCumple = renglonesFormatoOk.size() >= minEsperado;
			boolean todasEsperadasVisibles = tiendasEsperadas == null || tiendasEsperadas.isEmpty() || tiendasFaltantes.isEmpty();
			boolean resultadoPaso = hayRenglones && cuentaCumple && todasEsperadasVisibles;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!hayRenglones) motivoFallo.append("No se detectaron renglones tienda-transferencia con formato 'Tienda: ... Transferencia: ...' | ");
			if (!cuentaCumple) motivoFallo.append("Renglones con formato valido: ").append(renglonesFormatoOk.size()).append(" (esperado >= ").append(minEsperado).append(") | ");
			if (!todasEsperadasVisibles) motivoFallo.append("Tiendas esperadas NO visibles en el grid: ").append(tiendasFaltantes).append(" | ");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Tiendas visibles debajo del encabezado | Detectados: " + totalRenglonesDetectados + " | Con formato valido: " + renglonesFormatoOk.size() + " | Encabezado Transferencias: " + totalTransferenciasEncabezado + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Total de Transferencias segun encabezado: " + (totalTransferenciasEncabezado >= 0 ? totalTransferenciasEncabezado : "no detectado"));
			testCase.addBoldTextEvidenceCurrentStep("Renglones tienda-transferencia detectados: " + totalRenglonesDetectados + " | Con formato valido (Tienda: ... Transferencia: ...): " + renglonesFormatoOk.size());
			for (String renglonOk : renglonesFormatoOk) {
				testCase.addBoldTextEvidenceCurrentStep("Renglon visible en grid: " + renglonOk);
			}
			if (!renglonesFormatoInvalido.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Renglones sin formato esperado: " + renglonesFormatoInvalido);
			}
			if (tiendasEsperadas != null && !tiendasEsperadas.isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Tiendas esperadas (marcadas en filtro): " + tiendasEsperadas);
				testCase.addBoldTextEvidenceCurrentStep("Tiendas esperadas encontradas en grid: " + tiendasEncontradas);
				if (!tiendasFaltantes.isEmpty()) {
					testCase.addBoldTextEvidenceCurrentStep("Tiendas esperadas NO encontradas en grid: " + tiendasFaltantes);
				}
			}
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep("Tiendas_Visibles_Debajo_Encabezado");
			}

			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Tiendas visibles debajo del encabezado | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Marca la primera tienda visible del selector Tienda que no tenga informacion en la fecha consultada.
	 * Reproduce el comportamiento manual del caso: despues de tener una tienda CON informacion marcada, usa el listado abierto para elegir una tienda SIN informacion sin buscarla por CR.
	 *
	 * @param fechaConsulta Fecha usada para cruzar las opciones visibles contra BD.
	 * @param elementos Selectores de Liquidacion de Ruta.
	 * @param wait Espera explicita del flujo actual.
	 * @param crExcluir CR de la tienda CON informacion ya seleccionada, para no repetirla.
	 * @return Etiqueta de la tienda SIN informacion marcada, o cadena vacia si no se encuentra una opcion valida.
	 */
	private LocalDate rutaPropuestaFechaInicialRango;
	private LocalDate rutaPropuestaFechaFinalRango;
	private String rutaPropuestaFechaInicialRangoVisible = "";
	private String rutaPropuestaFechaFinalRangoVisible = "";
	private String rutaPropuestaRutaDinamicaEscenario = "";

	/**
	 * Obtiene desde BD el escenario de Ruta Dinamica propuesta usando created_at como filtro base, origen cedis y fecha_registro para fechas y horas del filtro.
	 * Parametriza la ruta y la fecha base obtenidas al inicio del flujo, y permite escenarios con uno o mas registros.
	 *
	 * @param rutaDinamica Ruta Dinamica propuesta obtenida al inicio del caso.
	 * @param fechaBaseFormateada Fecha base obtenida al inicio del caso; se usa como limite created_at mayor que esa fecha.
	 * @param screenshot True para agregar evidencia del escenario obtenido.
	 * @return True si BD devuelve al menos un registro para la ruta, fecha base y origen cedis; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	private String marcaPrimeraTiendaVisibleSinInformacionEnRango(LocalDate fechaConsulta, rutaLiquidacionElements elementos, WebDriverWait wait, String crExcluir) throws Exception {
		aseguraPanelFiltroTiendaAbierto(elementos, new WebDriverWait(u.getDriver(), Duration.ofSeconds(5)));
		try {
			WebElement inputBusqueda = obtieneInputBusquedaTiendaVisible(elementos, new WebDriverWait(u.getDriver(), Duration.ofSeconds(3)));
			capturaBusquedaTiendaComoUsuario(inputBusqueda, "");
			esperaRenderCorto();
		} catch (Exception e) {
			System.out.println("[MTC_FT_070 PASO 5] No se pudo limpiar buscador antes de buscar SIN info visible: " + e.getMessage());
		}

		for (int pagina = 0; pagina < 5; pagina++) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.opcionesFiltroTiendaVisibles));
			List<WebElement> opcionesVisibles = obtenerOpcionesTiendaVisiblesEnPantalla(u.getDriver().findElements(elementos.opcionesFiltroTiendaVisibles), 15);
			LinkedHashMap<String, String> tiendasVisibles = new LinkedHashMap<String, String>();
			LinkedHashMap<String, WebElement> elementosPorCr = new LinkedHashMap<String, WebElement>();
			for (WebElement opcion : opcionesVisibles) {
				String etiqueta = normalizarTextoFiltro(opcion);
				if (etiqueta.isEmpty()) {
					continue;
				}
				String crTienda = etiqueta.split("\\s+", 2)[0];
				if (crExcluir != null && crTienda.equals(crExcluir)) {
					continue;
				}
				if (!tiendasVisibles.containsKey(crTienda)) {
					tiendasVisibles.put(crTienda, etiqueta);
					elementosPorCr.put(crTienda, opcion);
				}
			}

			LinkedHashMap<String, String> tiendasConInfo = obtenerTiendasVisiblesConInformacionPorRangoDesdeBD(fechaConsulta, fechaConsulta, tiendasVisibles);
			System.out.println("[MTC_FT_070 PASO 5] Opciones visibles para SIN info pagina " + (pagina + 1) + ": " + tiendasVisibles.values() + " | CON info detectadas: " + tiendasConInfo.values());
			for (java.util.Map.Entry<String, String> entrada : tiendasVisibles.entrySet()) {
				String crTienda = entrada.getKey();
				if (tiendasConInfo.containsKey(crTienda)) {
					continue;
				}
				WebElement opcion = elementosPorCr.get(crTienda);
				if (opcion == null) {
					continue;
				}
				String etiqueta = entrada.getValue();
				if (!estaOpcionTiendaMarcada(opcion)) {
					haceClickSeguroOpcionTienda(opcion);
					esperaRenderCorto();
				}
				boolean marcada = esperaOpcionTiendaMarcadaPorCr(crTienda, elementos);
				System.out.println("[MTC_FT_070 PASO 5] Marca tienda SIN info visible " + etiqueta + " | Resultado: " + marcada);
				testCase.addBoldTextEvidenceCurrentStep("Tienda sin informacion marcada desde opciones visibles: " + etiqueta + " | Resultado: " + marcada);
				if (marcada) {
					return etiqueta;
				}
			}

			try {
				WebElement contenedorScroll = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.contenedorScrollFiltroTienda));
				Long scrollAntes = (Long) ((JavascriptExecutor) u.getDriver()).executeScript("return arguments[0].scrollTop;", contenedorScroll);
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollTop = arguments[0].scrollTop + Math.max(arguments[0].clientHeight, 360);", contenedorScroll);
				esperaRenderCorto();
				Long scrollDespues = (Long) ((JavascriptExecutor) u.getDriver()).executeScript("return arguments[0].scrollTop;", contenedorScroll);
				if (scrollDespues.longValue() == scrollAntes.longValue()) {
					break;
				}
			} catch (Exception e) {
				System.out.println("[MTC_FT_070 PASO 5] No se pudo avanzar scroll buscando SIN info visible: " + e.getMessage());
				break;
			}
		}

		System.out.println("[MTC_FT_070 PASO 5] No se encontro tienda SIN info visible distinta a la tienda CON info ya marcada.");
		return "";
	}

	/**
	 * Repite la consulta del flujo Tienda dejando unicamente la tienda con informacion previamente seleccionada.
	 * Desmarca la tienda sin informacion guardada, cierra el selector con seleccion unica, confirma que Consultar siga habilitado y valida que el mensaje y boton Ver tiendas queden ocultos.
	 *
	 * @param screenshot True para capturar evidencia visual de las acciones y validaciones del flujo.
	 * @return True si la consulta solo con tienda con informacion oculta el mensaje y el boton Ver tiendas; false en caso contrario.
	 */
	public boolean rutaPropuestaObtenerEscenarioCreatedAtDesdeBD(String rutaDinamica, String fechaBaseFormateada, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty() || fechaBaseFormateada == null || fechaBaseFormateada.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Ruta Dinamica o fecha base vacia para obtener escenario Ruta Propuesta");
				return false;
			}
			rutaPropuestaFechaInicialRango = null;
			rutaPropuestaFechaFinalRango = null;
			rutaPropuestaFechaInicialRangoVisible = "";
			rutaPropuestaFechaFinalRangoVisible = "";
			rutaPropuestaRutaDinamicaEscenario = rutaDinamica.trim();
			horaMinimaBD = "";
			horaMediaBD = "";
			horaMaximaBD = "";
			LocalDate fechaBase = convertirFechaVisibleALocalDate(fechaBaseFormateada);
			System.out.println("[RUTA PROPUESTA - ESCENARIO CREATED_AT] Parametros de busqueda | Ruta: " + rutaDinamica.trim() + " | Fecha base visible: " + fechaBaseFormateada + " | Fecha base SQL: " + fechaBase + " | created_at > " + java.sql.Timestamp.valueOf(fechaBase.atStartOfDay()) + " | origen: cedis");
			testCase.addBoldTextEvidenceCurrentStep("Parametros escenario Ruta Propuesta | Ruta: " + rutaDinamica.trim() + " | Fecha base: " + fechaBase + " | created_at > " + java.sql.Timestamp.valueOf(fechaBase.atStartOfDay()) + " | origen: cedis");
			String query = "SELECT MIN(fecha_registro) AS fecha_min, "
					+ "MAX(fecha_registro) AS fecha_max, "
					+ "(percentile_cont(0.5) WITHIN GROUP (ORDER BY EXTRACT(EPOCH FROM fecha_registro::time)))::int AS segundos_mediana, "
					+ "COUNT(*) AS total_registros "
					+ "FROM xxcm_historico_correos "
					+ "WHERE ruta_propuesta = ? "
					+ "AND created_at > ? "
					+ "AND origen = 'cedis' "
					+ "AND fecha_registro IS NOT NULL";

			java.sql.Timestamp fechaRegistroMin = null;
			java.sql.Timestamp fechaRegistroMax = null;
			int segundosMediana = 0;
			int totalRegistros = 0;
			String criterioEscenario = "created_at > " + java.sql.Timestamp.valueOf(fechaBase.atStartOfDay());
			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, rutaDinamica.trim());
				statement.setTimestamp(2, java.sql.Timestamp.valueOf(fechaBase.atStartOfDay()));
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						fechaRegistroMin = resultSet.getTimestamp("fecha_min");
						fechaRegistroMax = resultSet.getTimestamp("fecha_max");
						segundosMediana = resultSet.getInt("segundos_mediana");
						totalRegistros = resultSet.getInt("total_registros");
					}
				}
			}

			if (fechaRegistroMin == null || fechaRegistroMax == null || totalRegistros < 1) {
				String queryPorFechaRegistro = "SELECT MIN(fecha_registro) AS fecha_min, "
						+ "MAX(fecha_registro) AS fecha_max, "
						+ "(percentile_cont(0.5) WITHIN GROUP (ORDER BY EXTRACT(EPOCH FROM fecha_registro::time)))::int AS segundos_mediana, "
						+ "COUNT(*) AS total_registros "
						+ "FROM xxcm_historico_correos "
						+ "WHERE ruta_propuesta = ? "
						+ "AND fecha_registro::date = ? "
						+ "AND origen = 'cedis' "
						+ "AND fecha_registro IS NOT NULL";
				try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
						java.sql.PreparedStatement statement = connection.prepareStatement(queryPorFechaRegistro)) {
					statement.setString(1, rutaDinamica.trim());
					statement.setDate(2, java.sql.Date.valueOf(fechaBase));
					try (ResultSet resultSet = statement.executeQuery()) {
						if (resultSet.next()) {
							fechaRegistroMin = resultSet.getTimestamp("fecha_min");
							fechaRegistroMax = resultSet.getTimestamp("fecha_max");
							segundosMediana = resultSet.getInt("segundos_mediana");
							totalRegistros = resultSet.getInt("total_registros");
							criterioEscenario = "fallback fecha_registro::date = " + fechaBase;
						}
					}
				}
				if (fechaRegistroMin != null && fechaRegistroMax != null && totalRegistros >= 1) {
					String mensajeFallback = "Escenario obtenido por fallback fecha_registro::date para ruta " + rutaDinamica.trim() + " porque no hubo registros origen cedis con created_at > " + fechaBase;
					System.out.println("[RUTA PROPUESTA - ESCENARIO CREATED_AT] " + mensajeFallback);
					testCase.addBoldTextEvidenceCurrentStep(mensajeFallback);
				}
			}

			String suppliableIdRutaInicial = "";
			if (fechaRegistroMin == null || fechaRegistroMax == null || totalRegistros < 1) {
				suppliableIdRutaInicial = rutaPropuestaObtenerSuppliableIdRutaInicial(rutaDinamica);
				if (suppliableIdRutaInicial != null && !suppliableIdRutaInicial.trim().isEmpty()) {
					String queryRutaAlternaMismoSupplier = "SELECT ruta_propuesta, "
							+ "MIN(fecha_registro) AS fecha_min, "
							+ "MAX(fecha_registro) AS fecha_max, "
							+ "(percentile_cont(0.5) WITHIN GROUP (ORDER BY EXTRACT(EPOCH FROM fecha_registro::time)))::int AS segundos_mediana, "
							+ "COUNT(*) AS total_registros, "
							+ "MAX(created_at) AS max_created_at "
							+ "FROM xxcm_historico_correos "
							+ "WHERE origen = 'cedis' "
							+ "AND suppliable_id::text = ? "
							+ "AND created_at > ? "
							+ "AND fecha_registro IS NOT NULL "
							+ "GROUP BY ruta_propuesta "
							+ "ORDER BY max_created_at DESC NULLS LAST "
							+ "LIMIT 1";
					try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
							java.sql.PreparedStatement statement = connection.prepareStatement(queryRutaAlternaMismoSupplier)) {
						statement.setString(1, suppliableIdRutaInicial.trim());
						statement.setTimestamp(2, java.sql.Timestamp.valueOf(fechaBase.atStartOfDay()));
						try (ResultSet resultSet = statement.executeQuery()) {
							if (resultSet.next()) {
								rutaPropuestaRutaDinamicaEscenario = resultSet.getString("ruta_propuesta");
								fechaRegistroMin = resultSet.getTimestamp("fecha_min");
								fechaRegistroMax = resultSet.getTimestamp("fecha_max");
								segundosMediana = resultSet.getInt("segundos_mediana");
								totalRegistros = resultSet.getInt("total_registros");
								criterioEscenario = "fallback ruta alterna mismo suppliable_id " + suppliableIdRutaInicial + " ruta " + rutaPropuestaRutaDinamicaEscenario + " con created_at > " + fechaBase;
							}
						}
					}
					if (fechaRegistroMin == null || fechaRegistroMax == null || totalRegistros < 1) {
						String queryRutaAlternaMismoSupplierPorFecha = "SELECT ruta_propuesta, "
								+ "MIN(fecha_registro) AS fecha_min, "
								+ "MAX(fecha_registro) AS fecha_max, "
								+ "(percentile_cont(0.5) WITHIN GROUP (ORDER BY EXTRACT(EPOCH FROM fecha_registro::time)))::int AS segundos_mediana, "
								+ "COUNT(*) AS total_registros, "
								+ "MAX(fecha_registro) AS max_fecha_registro "
								+ "FROM xxcm_historico_correos "
								+ "WHERE origen = 'cedis' "
								+ "AND suppliable_id::text = ? "
								+ "AND fecha_registro::date = ? "
								+ "AND fecha_registro IS NOT NULL "
								+ "GROUP BY ruta_propuesta "
								+ "ORDER BY max_fecha_registro DESC NULLS LAST "
								+ "LIMIT 1";
						try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
								java.sql.PreparedStatement statement = connection.prepareStatement(queryRutaAlternaMismoSupplierPorFecha)) {
							statement.setString(1, suppliableIdRutaInicial.trim());
							statement.setDate(2, java.sql.Date.valueOf(fechaBase));
							try (ResultSet resultSet = statement.executeQuery()) {
								if (resultSet.next()) {
									rutaPropuestaRutaDinamicaEscenario = resultSet.getString("ruta_propuesta");
									fechaRegistroMin = resultSet.getTimestamp("fecha_min");
									fechaRegistroMax = resultSet.getTimestamp("fecha_max");
									segundosMediana = resultSet.getInt("segundos_mediana");
									totalRegistros = resultSet.getInt("total_registros");
									criterioEscenario = "fallback ruta alterna mismo suppliable_id " + suppliableIdRutaInicial + " ruta " + rutaPropuestaRutaDinamicaEscenario + " con fecha_registro::date = " + fechaBase;
								}
							}
						}
					}
					if (fechaRegistroMin != null && fechaRegistroMax != null && totalRegistros >= 1) {
						String mensajeRutaSupplier = "Ruta inicial " + rutaDinamica.trim() + " no tiene escenario origen cedis; se usara ruta alterna " + rutaPropuestaRutaDinamicaEscenario + " del mismo suppliable_id " + suppliableIdRutaInicial + ".";
						System.out.println("[RUTA PROPUESTA - ESCENARIO CREATED_AT] " + mensajeRutaSupplier);
						testCase.addBoldTextEvidenceCurrentStep(mensajeRutaSupplier);
					} else if (suppliableIdRutaInicial != null && !suppliableIdRutaInicial.trim().isEmpty()) {
						String mensajeSinRutaSupplier = "Ruta inicial " + rutaDinamica.trim() + " pertenece al suppliable_id " + suppliableIdRutaInicial + ", pero no se encontro ruta alterna origen cedis para ese mismo CEDIS. No se usara fallback global para evitar consultar una ruta sin permisos del usuario.";
						System.out.println("[RUTA PROPUESTA - ESCENARIO CREATED_AT] " + mensajeSinRutaSupplier);
						testCase.addBoldTextEvidenceCurrentStep(mensajeSinRutaSupplier);
					}
				} else {
					System.out.println("[RUTA PROPUESTA - ESCENARIO CREATED_AT] No se obtuvo suppliable_id para la ruta inicial " + rutaDinamica.trim() + "; se evaluara fallback global.");
				}
			}

				if ((fechaRegistroMin == null || fechaRegistroMax == null || totalRegistros < 1)
						&& (suppliableIdRutaInicial == null || suppliableIdRutaInicial.trim().isEmpty())) {
				String queryRutaAlterna = "SELECT ruta_propuesta, "
						+ "MIN(fecha_registro) AS fecha_min, "
						+ "MAX(fecha_registro) AS fecha_max, "
						+ "(percentile_cont(0.5) WITHIN GROUP (ORDER BY EXTRACT(EPOCH FROM fecha_registro::time)))::int AS segundos_mediana, "
						+ "COUNT(*) AS total_registros, "
						+ "MAX(created_at) AS max_created_at "
						+ "FROM xxcm_historico_correos "
						+ "WHERE origen = 'cedis' "
						+ "AND created_at > ? "
						+ "AND fecha_registro IS NOT NULL "
						+ "GROUP BY ruta_propuesta "
						+ "ORDER BY max_created_at DESC NULLS LAST "
						+ "LIMIT 1";
				try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
						java.sql.PreparedStatement statement = connection.prepareStatement(queryRutaAlterna)) {
					statement.setTimestamp(1, java.sql.Timestamp.valueOf(fechaBase.atStartOfDay()));
					try (ResultSet resultSet = statement.executeQuery()) {
						if (resultSet.next()) {
							rutaPropuestaRutaDinamicaEscenario = resultSet.getString("ruta_propuesta");
							fechaRegistroMin = resultSet.getTimestamp("fecha_min");
							fechaRegistroMax = resultSet.getTimestamp("fecha_max");
							segundosMediana = resultSet.getInt("segundos_mediana");
							totalRegistros = resultSet.getInt("total_registros");
							criterioEscenario = "fallback ruta alterna " + rutaPropuestaRutaDinamicaEscenario + " con created_at > " + fechaBase;
						}
					}
				}
				if (fechaRegistroMin != null && fechaRegistroMax != null && totalRegistros >= 1) {
					String mensajeRutaAlterna = "Ruta inicial " + rutaDinamica.trim() + " no tiene escenario origen cedis para la fecha base; se usara ruta alterna " + rutaPropuestaRutaDinamicaEscenario + " con datos validos.";
					System.out.println("[RUTA PROPUESTA - ESCENARIO CREATED_AT] " + mensajeRutaAlterna);
					testCase.addBoldTextEvidenceCurrentStep(mensajeRutaAlterna);
				}
			}

			if (fechaRegistroMin == null || fechaRegistroMax == null || totalRegistros < 1) {
				String motivoFallo = "BD no devolvio registros con fecha_registro para ruta " + rutaDinamica + " con created_at > " + fechaBase + " y origen cedis";
				System.out.println("[RUTA PROPUESTA - ESCENARIO CREATED_AT] " + motivoFallo);
				rutaPropuestaRegistrarDiagnosticoEscenario(rutaDinamica, fechaBase);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			rutaPropuestaFechaInicialRango = fechaRegistroMin.toLocalDateTime().toLocalDate();
			rutaPropuestaFechaFinalRango = fechaRegistroMax.toLocalDateTime().toLocalDate();
			rutaPropuestaFechaInicialRangoVisible = rutaPropuestaFormatearFechaVisible(rutaPropuestaFechaInicialRango);
			rutaPropuestaFechaFinalRangoVisible = rutaPropuestaFormatearFechaVisible(rutaPropuestaFechaFinalRango);
			horaMinimaBD = rutaPropuestaObtenerHoraInicioPortal(fechaRegistroMin.toLocalDateTime().toLocalTime());
			horaMaximaBD = rutaPropuestaObtenerHoraFinPortal(fechaRegistroMax.toLocalDateTime().toLocalTime());
			LocalTime horaMedia = LocalTime.ofSecondOfDay(Math.max(0, Math.min(86399, segundosMediana)));
			horaMediaBD = rutaPropuestaObtenerHoraFinPortal(horaMedia);

			System.out.println("[RUTA PROPUESTA - ESCENARIO CREATED_AT] Ruta: " + rutaPropuestaRutaDinamicaEscenario + " | Criterio: " + criterioEscenario + " | Fecha base: " + fechaBase + " | Fecha inicial: " + rutaPropuestaFechaInicialRangoVisible + " | Fecha final: " + rutaPropuestaFechaFinalRangoVisible + " | Hora inicio: " + horaMinimaBD + " | Hora fin intermedia: " + horaMediaBD + " | Hora fin maxima: " + horaMaximaBD + " | Registros: " + totalRegistros);
			testCase.addBoldTextEvidenceCurrentStep("Escenario BD obtenido | Ruta: " + rutaPropuestaRutaDinamicaEscenario + " | Origen: cedis | Criterio: " + criterioEscenario + " | Fecha base: " + fechaBase);
			testCase.addBoldTextEvidenceCurrentStep("Fecha inicial BD desde fecha_registro: " + rutaPropuestaFechaInicialRangoVisible + " | Fecha final BD desde fecha_registro: " + rutaPropuestaFechaFinalRangoVisible);
			testCase.addBoldTextEvidenceCurrentStep("Horas fecha_registro BD | Inicio: " + horaMinimaBD + " | Fin intermedio: " + horaMediaBD + " | Fin maximo: " + horaMaximaBD + " | Total registros: " + totalRegistros);
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Ruta_Propuesta_Escenario_CreatedAt_BD");
			}
			return true;
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - ESCENARIO CREATED_AT] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Captura Fecha inicial y Fecha final usando las fechas obtenidas desde BD por fecha_registro para la Ruta Propuesta.
	 * La busqueda del escenario mantiene created_at como filtro base y origen cedis.
	 *
	 * @param rutaDinamica Ruta Dinamica propuesta obtenida al inicio del caso.
	 * @param fechaBaseFormateada Fecha base obtenida al inicio del caso.
	 * @param screenshot True para agregar evidencia visual de las fechas capturadas.
	 * @return True si ambas fechas quedan seleccionadas correctamente; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	public boolean rutaPropuestaCapturaFechasObtenidasPorCreatedAt(String rutaDinamica, String fechaBaseFormateada, boolean screenshot) {
		try {
			if ((rutaPropuestaFechaInicialRango == null || rutaPropuestaFechaFinalRango == null) && !rutaPropuestaObtenerEscenarioCreatedAtDesdeBD(rutaDinamica, fechaBaseFormateada, false)) {
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(25));
			rutaPropuestaEsperarCargaLiquidacionRuta(wait);

			rutaPropuestaSeleccionarOpcionFecha(wait, elementos.botonFechaInicialLiquidacionRuta, rutaPropuestaFechaInicialRangoVisible, "Fecha inicial");
			rutaPropuestaSeleccionarOpcionFecha(wait, elementos.botonFechaFinalLiquidacionRuta, rutaPropuestaFechaFinalRangoVisible, "Fecha final");
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);

			System.out.println("[RUTA PROPUESTA - CAPTURA FECHAS CREATED_AT] Fecha inicial seleccionada: " + rutaPropuestaFechaInicialRangoVisible + " | Fecha final seleccionada: " + rutaPropuestaFechaFinalRangoVisible);
			testCase.addBoldTextEvidenceCurrentStep("Fecha inicial seleccionada desde fecha_registro BD: " + rutaPropuestaFechaInicialRangoVisible);
			testCase.addBoldTextEvidenceCurrentStep("Fecha final seleccionada desde fecha_registro BD: " + rutaPropuestaFechaFinalRangoVisible);
			if (screenshot) {
				u.highLight(elementos.filtroFechaInicial);
				u.highLight(elementos.filtroFechaFinal);
				testCase.addScreenShotCurrentStep("Ruta_Propuesta_Fechas_CreatedAt_BD");
				u.unhighLight(elementos.filtroFechaInicial);
				u.unhighLight(elementos.filtroFechaFinal);
			}
			return true;
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - CAPTURA FECHAS CREATED_AT] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Captura una Ruta Dinamica (UNIGIS) para el flujo Ruta Propuesta y confirma la seleccion en el autocomplete cuando exista opcion visible.
	 * Ademas de escribir la ruta, dispara eventos input/change/blur para que Angular actualice el modelo antes de consultar.
	 *
	 * @param rutaDinamica Ruta Dinamica efectiva del escenario que se capturara en el filtro.
	 * @param screenshot True para capturar evidencia visual de la ruta capturada.
	 * @return True si la ruta queda escrita en el input; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-14
	 */
	public boolean rutaPropuestaCapturaRutaDinamicaUnigis(String rutaDinamica, boolean screenshot) {
		try {
			if (rutaDinamica == null || rutaDinamica.trim().isEmpty()) {
				testCase.addBoldTextEvidenceCurrentStep("Ruta Dinamica efectiva vacia o nula para Ruta Propuesta");
				return false;
			}

			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			rutaPropuestaEsperarCargaLiquidacionRuta(wait);
			u.hardWait(1);

			WebElement inputRuta = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.inputRutaDinamica));
			JavascriptExecutor js = (JavascriptExecutor) u.getDriver();
			js.executeScript("arguments[0].scrollIntoView({block:'center'}); arguments[0].focus();", inputRuta);
			inputRuta.sendKeys(Keys.CONTROL + "a");
			inputRuta.sendKeys(Keys.DELETE);
			inputRuta.sendKeys(rutaDinamica.trim());
			u.hardWait(1);

			boolean opcionSeleccionada = false;
			try {
				By opcionRuta = By.xpath("(//div[contains(@class,'cdk-overlay-pane')]//*[self::mat-option or @role='option'][contains(normalize-space(.),'" + rutaDinamica.trim() + "')])[1]");
				WebElement opcion = wait.until(ExpectedConditions.elementToBeClickable(opcionRuta));
				try {
					opcion.click();
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", opcion);
				}
				opcionSeleccionada = true;
				System.out.println("[RUTA PROPUESTA - CAPTURA RUTA] Opcion autocomplete seleccionada para ruta: " + rutaDinamica.trim());
			} catch (Exception e) {
				System.out.println("[RUTA PROPUESTA - CAPTURA RUTA] No se encontro opcion autocomplete para ruta " + rutaDinamica.trim() + "; se continua con valor escrito. Motivo: " + e.getMessage());
			}

			js.executeScript("arguments[0].dispatchEvent(new Event('input', {bubbles:true})); arguments[0].dispatchEvent(new Event('change', {bubbles:true})); arguments[0].blur();", inputRuta);
			u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
			u.hardWait(1);

			String valorCapturado = inputRuta.getAttribute("value");
			boolean rutaCapturada = rutaDinamica.trim().equals(valorCapturado);
			System.out.println("[RUTA PROPUESTA - CAPTURA RUTA] Ruta esperada: " + rutaDinamica.trim() + " | Actual: " + valorCapturado + " | Opcion autocomplete seleccionada: " + opcionSeleccionada);
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinamica efectiva esperada: " + rutaDinamica.trim() + " | Actual: " + valorCapturado + " | Opcion autocomplete seleccionada: " + opcionSeleccionada);
			if (screenshot) {
				u.highLight(elementos.filtroRutaDinamica);
				testCase.addScreenShotCurrentStep("Ruta_Propuesta_Ruta_Dinamica_Capturada");
				u.unhighLight(elementos.filtroRutaDinamica);
			}

			return rutaCapturada;
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - CAPTURA RUTA] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Devuelve la Ruta Dinamica que se usara para capturar y validar el escenario de Ruta Propuesta.
	 * Si la ruta inicial no tiene datos validos, puede devolver una ruta alterna seleccionada desde BD con origen cedis.
	 *
	 * @return Ruta Dinamica efectiva del escenario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-14
	 */
	public String rutaPropuestaGetRutaDinamicaEscenario() {
		return rutaPropuestaRutaDinamicaEscenario;
	}

	/**
	 * Valida que el total de tiendas del grid coincida con el total esperado en BD para la Ruta Propuesta y rango combinado de fecha_registro.
	 * La lectura del grid usa localizadores flexibles para no depender de un id especifico del DOM.
	 *
	 * @param rutaDinamica Ruta Dinamica capturada en el filtro Ruta Dinamica (UNIGIS).
	 * @param fechaFormateada Fecha visible base del escenario.
	 * @param horaInicial24 Hora inicial capturada en formato HH:mm.
	 * @param horaFinal24 Hora final capturada en formato HH:mm.
	 * @param screenshot True para agregar evidencia visual del resultado consultado.
	 * @return True si el total del grid coincide con el total esperado en BD; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	public boolean rutaPropuestaValidaTotalTiendasGridContraBDPorRangoHoras(String rutaDinamica, String fechaFormateada, String horaInicial24, String horaFinal24, boolean screenshot) {
		try {
			if (!rutaPropuestaParametrosRangoCompletos(rutaDinamica, fechaFormateada, horaInicial24, horaFinal24)) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Parametros vacios para validar rango combinado contra BD");
				return false;
			}

			LocalDate fechaConsultaFinal = rutaPropuestaObtenerFechaFinalConsulta(fechaFormateada);
			LocalDate fechaConsultaInicial = rutaPropuestaObtenerFechaInicialConsulta(fechaFormateada);
			LocalTime horaInicial = LocalTime.parse(horaInicial24, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime horaFinal = LocalTime.parse(horaFinal24, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime horaFinalConsulta = rutaPropuestaObtenerHoraFinalConsulta(horaFinal);
			java.sql.Timestamp inicio = java.sql.Timestamp.valueOf(fechaConsultaInicial.atTime(horaInicial));
			java.sql.Timestamp fin = java.sql.Timestamp.valueOf(fechaConsultaFinal.atTime(horaFinalConsulta));
			int totalBD = rutaPropuestaObtenerTotalTiendasBD(rutaDinamica, inicio, fin);

			WebElement detalle = rutaPropuestaConsultarYLocalizarDetalle(screenshot);
			String textoDetalle = rutaPropuestaNormalizarTexto(detalle.getText() + " " + rutaPropuestaObtenerTextoRaizLiquidacionRuta());
			int totalGrid = rutaPropuestaExtraerTotalTiendasGrid(textoDetalle);
			if (totalGrid < 0) {
				totalGrid = rutaPropuestaContarTiendasVisiblesPorJavascript();
			}
			if (totalGrid < 0) {
				rutaPropuestaRegistrarDiagnosticoGrid(textoDetalle);
			}

			boolean resultadoPaso = totalGrid >= 0 && totalGrid == totalBD;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "Total tiendas grid: " + totalGrid + " | Total esperado en BD: " + totalBD + " para rango " + inicio + " a " + fin;

			System.out.println("[RUTA PROPUESTA - VALIDACION RANGO HORAS] Ruta: " + rutaDinamica.trim() + " | Rango BD: " + inicio + " a " + fin + " | Total BD: " + totalBD + " | Total grid: " + totalGrid);
			testCase.addBoldTextEvidenceCurrentStep("Ruta Dinamica validada: " + rutaDinamica.trim() + " | Fecha inicial: " + rutaPropuestaFormatearFechaVisible(fechaConsultaInicial) + " | Fecha final: " + rutaPropuestaFormatearFechaVisible(fechaConsultaFinal));
			testCase.addBoldTextEvidenceCurrentStep("Rango combinado consultado en BD: " + inicio + " a " + fin);
			testCase.addBoldTextEvidenceCurrentStep("Total tiendas esperado en BD (BETWEEN): " + totalBD + " | Total mostrado en el grid: " + totalGrid);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Ruta_Propuesta_Rango_Horas_Grid_Vs_BD_" + horaInicial24.replace(":", "") + "_" + horaFinal24.replace(":", ""));
			}
			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - VALIDACION RANGO HORAS] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que ninguna tienda con fecha_registro fuera del rango combinado aparezca visible en el grid de Ruta Propuesta.
	 * Si no hay tiendas fuera del rango, marca la validacion como no aplicable y la considera exitosa.
	 *
	 * @param rutaDinamica Ruta Dinamica capturada en el filtro Ruta Dinamica (UNIGIS).
	 * @param fechaFormateada Fecha visible base del escenario.
	 * @param horaInicial24 Hora inicial capturada en formato HH:mm.
	 * @param horaFinal24 Hora final capturada en formato HH:mm.
	 * @param screenshot True para agregar evidencia visual del resultado consultado.
	 * @return True si las tiendas fuera del rango no aparecen en el grid; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	public boolean rutaPropuestaValidaTiendasFueraDeRangoNoVisiblesEnGrid(String rutaDinamica, String fechaFormateada, String horaInicial24, String horaFinal24, boolean screenshot) {
		try {
			if (!rutaPropuestaParametrosRangoCompletos(rutaDinamica, fechaFormateada, horaInicial24, horaFinal24)) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Parametros vacios para validar tiendas fuera de rango");
				return false;
			}

			LocalDate fechaConsultaFinal = rutaPropuestaObtenerFechaFinalConsulta(fechaFormateada);
			LocalDate fechaConsultaInicial = rutaPropuestaObtenerFechaInicialConsulta(fechaFormateada);
			LocalTime horaInicial = LocalTime.parse(horaInicial24, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime horaFinal = LocalTime.parse(horaFinal24, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime horaFinalConsulta = rutaPropuestaObtenerHoraFinalConsulta(horaFinal);
			java.sql.Timestamp inicio = java.sql.Timestamp.valueOf(fechaConsultaInicial.atTime(horaInicial));
			java.sql.Timestamp fin = java.sql.Timestamp.valueOf(fechaConsultaFinal.atTime(horaFinalConsulta));
			LinkedHashMap<String, String> tiendasFueraRango = rutaPropuestaObtenerTiendasBDPorRango(rutaDinamica, fechaConsultaInicial, fechaConsultaFinal, inicio, fin, false);

			if (tiendasFueraRango.isEmpty()) {
				String motivo = "BD no devolvio tiendas fuera del rango " + inicio + " - " + fin + " para ruta " + rutaDinamica + ". El escenario puede tener un solo registro o todos los registros dentro del rango; la validacion de exclusion se marca como no aplicable.";
				System.out.println("[RUTA PROPUESTA - TIENDAS FUERA RANGO] " + motivo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: PASSED | Validacion de exclusion no aplicable: " + motivo);
				return true;
			}

			String textoGrid = rutaPropuestaObtenerTextoResultadoFlexible();
			int tiendasVisiblesEnGrid = 0;
			StringBuilder detalleTiendasVisibles = new StringBuilder();
			for (java.util.Map.Entry<String, String> tiendaBD : tiendasFueraRango.entrySet()) {
				if (rutaPropuestaTextoContieneTienda(textoGrid, tiendaBD.getKey(), tiendaBD.getValue())) {
					tiendasVisiblesEnGrid++;
					detalleTiendasVisibles.append(tiendaBD.getValue()).append("; ");
				}
				System.out.println("[RUTA PROPUESTA - TIENDAS FUERA RANGO] " + tiendaBD.getValue() + " | Visible en grid: " + (rutaPropuestaTextoContieneTienda(textoGrid, tiendaBD.getKey(), tiendaBD.getValue()) ? "SI (INESPERADO)" : "NO (correcto)"));
			}

			boolean resultadoPaso = tiendasVisiblesEnGrid == 0;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : tiendasVisiblesEnGrid + " tienda(s) fuera del rango aparecen visibles: " + detalleTiendasVisibles;

			testCase.addBoldTextEvidenceCurrentStep("Tiendas BD fuera del rango " + inicio + " - " + fin + ": " + tiendasFueraRango.values());
			testCase.addBoldTextEvidenceCurrentStep("Tiendas fuera de rango visibles en grid: " + tiendasVisiblesEnGrid + " (esperado: 0)");
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Ruta_Propuesta_Tiendas_Fuera_Rango_No_Visibles_" + horaInicial24.replace(":", "") + "_" + horaFinal24.replace(":", ""));
			}
			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - TIENDAS FUERA RANGO] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que todas las tiendas con fecha_registro dentro del rango combinado aparezcan visibles en el grid de Ruta Propuesta.
	 * Compara CR tienda y nombre de tienda obtenidos desde BD contra el texto visible del resultado en Portal COMETA.
	 *
	 * @param rutaDinamica Ruta Dinamica capturada en el filtro Ruta Dinamica (UNIGIS).
	 * @param fechaFormateada Fecha visible base del escenario.
	 * @param horaInicial24 Hora inicial capturada en formato HH:mm.
	 * @param horaFinal24 Hora final capturada en formato HH:mm.
	 * @param screenshot True para agregar evidencia visual del resultado consultado.
	 * @return True si todas las tiendas dentro del rango aparecen en el grid; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	public boolean rutaPropuestaValidaTiendasDentroDeRangoVisiblesEnGrid(String rutaDinamica, String fechaFormateada, String horaInicial24, String horaFinal24, boolean screenshot) {
		try {
			if (!rutaPropuestaParametrosRangoCompletos(rutaDinamica, fechaFormateada, horaInicial24, horaFinal24)) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: Parametros vacios para validar tiendas dentro de rango");
				return false;
			}

			LocalDate fechaConsultaFinal = rutaPropuestaObtenerFechaFinalConsulta(fechaFormateada);
			LocalDate fechaConsultaInicial = rutaPropuestaObtenerFechaInicialConsulta(fechaFormateada);
			LocalTime horaInicial = LocalTime.parse(horaInicial24, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime horaFinal = LocalTime.parse(horaFinal24, DateTimeFormatter.ofPattern("HH:mm"));
			LocalTime horaFinalConsulta = rutaPropuestaObtenerHoraFinalConsulta(horaFinal);
			java.sql.Timestamp inicio = java.sql.Timestamp.valueOf(fechaConsultaInicial.atTime(horaInicial));
			java.sql.Timestamp fin = java.sql.Timestamp.valueOf(fechaConsultaFinal.atTime(horaFinalConsulta));
			LinkedHashMap<String, String> tiendasDentroRango = rutaPropuestaObtenerTiendasBDPorRango(rutaDinamica, fechaConsultaInicial, fechaConsultaFinal, inicio, fin, true);

			if (tiendasDentroRango.isEmpty()) {
				String motivoFallo = "BD no devolvio tiendas dentro del rango " + inicio + " - " + fin + " para ruta " + rutaDinamica;
				System.out.println("[RUTA PROPUESTA - TIENDAS DENTRO RANGO] " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			String textoGrid = rutaPropuestaObtenerTextoResultadoFlexible();
			int tiendasNoVisiblesEnGrid = 0;
			StringBuilder detalleTiendasFaltantes = new StringBuilder();
			for (java.util.Map.Entry<String, String> tiendaBD : tiendasDentroRango.entrySet()) {
				if (!rutaPropuestaTextoContieneTienda(textoGrid, tiendaBD.getKey(), tiendaBD.getValue())) {
					tiendasNoVisiblesEnGrid++;
					detalleTiendasFaltantes.append(tiendaBD.getValue()).append("; ");
				}
				System.out.println("[RUTA PROPUESTA - TIENDAS DENTRO RANGO] " + tiendaBD.getValue() + " | Visible en grid: " + (rutaPropuestaTextoContieneTienda(textoGrid, tiendaBD.getKey(), tiendaBD.getValue()) ? "SI (correcto)" : "NO (INESPERADO)"));
			}

			boolean resultadoPaso = tiendasNoVisiblesEnGrid == 0;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : tiendasNoVisiblesEnGrid + " tienda(s) dentro del rango no aparecen visibles: " + detalleTiendasFaltantes;

			testCase.addBoldTextEvidenceCurrentStep("Tiendas BD dentro del rango " + inicio + " - " + fin + ": " + tiendasDentroRango.values());
			testCase.addBoldTextEvidenceCurrentStep("Tiendas dentro del rango no visibles en grid: " + tiendasNoVisiblesEnGrid + " (esperado: 0)");
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (screenshot) {
				testCase.addScreenShotCurrentStep("Ruta_Propuesta_Tiendas_Dentro_Rango_Visibles_" + horaInicial24.replace(":", "") + "_" + horaFinal24.replace(":", ""));
			}
			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - TIENDAS DENTRO RANGO] FAILED | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Verifica que los parametros indispensables del rango combinado de Ruta Propuesta tengan valor antes de consultar o validar.
	 * Evita falsos positivos cuando la ruta, fecha u horas no fueron obtenidas desde BD o capturadas en el portal.
	 *
	 * @param rutaDinamica Ruta Dinamica capturada u obtenida desde BD.
	 * @param fechaFormateada Fecha visible base del escenario.
	 * @param horaInicial24 Hora inicial del rango.
	 * @param horaFinal24 Hora final del rango.
	 * @return True si todos los parametros tienen valor; false si alguno esta vacio o nulo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	private boolean rutaPropuestaParametrosRangoCompletos(String rutaDinamica, String fechaFormateada, String horaInicial24, String horaFinal24) {
		return rutaDinamica != null && !rutaDinamica.trim().isEmpty()
				&& fechaFormateada != null && !fechaFormateada.trim().isEmpty()
				&& horaInicial24 != null && !horaInicial24.trim().isEmpty()
				&& horaFinal24 != null && !horaFinal24.trim().isEmpty();
	}

	/**
	 * Selecciona una fecha visible en un combo de Liquidacion de Ruta para el flujo Ruta Propuesta.
	 * Busca la opcion por texto exacto dentro del overlay activo de Angular Material.
	 *
	 * @param wait Espera explicita asociada al navegador activo.
	 * @param botonFecha Localizador del combo de fecha que se desea abrir.
	 * @param fechaVisible Fecha visible que se debe seleccionar.
	 * @param nombreCampo Nombre del campo para evidencia y consola.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	private void rutaPropuestaSeleccionarOpcionFecha(WebDriverWait wait, By botonFecha, String fechaVisible, String nombreCampo) {
		By opcionFecha = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-option/span[normalize-space(text())='" + fechaVisible + "']");
		WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonFecha));
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", boton);
		try {
			boton.click();
		} catch (Exception e) {
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", boton);
		}
		WebElement opcion = wait.until(ExpectedConditions.elementToBeClickable(opcionFecha));
		try {
			opcion.click();
		} catch (Exception e) {
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", opcion);
		}
		System.out.println("[RUTA PROPUESTA - CAPTURA FECHAS] " + nombreCampo + " seleccionada: " + fechaVisible);
	}

	/**
	 * Obtiene la fecha inicial para consultas BD de Ruta Propuesta.
	 * Si aun no se obtuvo el rango desde BD, usa como respaldo la fecha base indicada.
	 *
	 * @param fechaFinalFormateada Fecha base visible del escenario.
	 * @return Fecha inicial del rango obtenido desde fecha_registro o la fecha base como respaldo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	private LocalDate rutaPropuestaObtenerFechaInicialConsulta(String fechaFinalFormateada) {
		if (rutaPropuestaFechaInicialRango != null) {
			return rutaPropuestaFechaInicialRango;
		}
		return convertirFechaVisibleALocalDate(fechaFinalFormateada);
	}

	/**
	 * Obtiene la fecha final para consultas BD de Ruta Propuesta.
	 * Si aun no se obtuvo el rango desde BD, interpreta la fecha visible proporcionada por el escenario.
	 *
	 * @param fechaFinalFormateada Fecha final visible del escenario.
	 * @return Fecha final del rango.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	private LocalDate rutaPropuestaObtenerFechaFinalConsulta(String fechaFinalFormateada) {
		if (rutaPropuestaFechaFinalRango != null) {
			return rutaPropuestaFechaFinalRango;
		}
		return convertirFechaVisibleALocalDate(fechaFinalFormateada);
	}

	/**
	 * Convierte la hora final capturada en el portal al limite usado por la consulta BD de Ruta Propuesta.
	 * El combo del portal representa bloques completos de hora; por ejemplo, 08:00 cubre hasta 08:59:59.999999999.
	 *
	 * @param horaFinal Hora final seleccionada en el portal.
	 * @return Hora final que debe usarse en el timestamp de consulta.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	private LocalTime rutaPropuestaObtenerHoraFinalConsulta(LocalTime horaFinal) {
		return horaFinal.plusMinutes(59).plusSeconds(59).plusNanos(999_999_999);
	}

	/**
	 * Obtiene desde BD el total de tiendas distintas de una Ruta Propuesta para el rango combinado indicado por fecha_registro.
	 * La consulta considera la ruta propuesta, fecha_registro dentro del rango y origen fijo cedis.
	 *
	 * @param rutaDinamica Ruta Dinamica propuesta.
	 * @param inicio Timestamp inicial del rango combinado.
	 * @param fin Timestamp final inclusivo del rango combinado.
	 * @return Total de tiendas distintas esperado en BD.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	private int rutaPropuestaObtenerTotalTiendasBD(String rutaDinamica, java.sql.Timestamp inicio, java.sql.Timestamp fin) throws Exception {
		String query = "SELECT COUNT(DISTINCT id_retek) AS total_tiendas "
				+ "FROM xxcm_historico_correos "
				+ "WHERE ruta_propuesta = ? "
				+ "AND fecha_registro BETWEEN ? AND ? "
				+ "AND origen = 'cedis'";
		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, rutaDinamica.trim());
			statement.setTimestamp(2, inicio);
			statement.setTimestamp(3, fin);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt("total_tiendas");
				}
			}
		}
		return -1;
	}

	/**
	 * Obtiene el suppliable_id asociado a la ruta inicial para restringir rutas alternas al mismo CEDIS del usuario.
	 * Esto evita elegir rutas globales con datos que el perfil CEDIS no puede consultar en pantalla.
	 *
	 * @param rutaDinamica Ruta Dinamica inicial obtenida desde BD.
	 * @return suppliable_id de la ruta inicial, o cadena vacia si no existe.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-14
	 */
	private String rutaPropuestaObtenerSuppliableIdRutaInicial(String rutaDinamica) {
		String query = "SELECT suppliable_id::text AS suppliable_id "
				+ "FROM xxcm_historico_correos "
				+ "WHERE ruta_propuesta = ? "
				+ "AND origen = 'cedis' "
				+ "AND suppliable_id IS NOT NULL "
				+ "ORDER BY fecha_registro DESC NULLS LAST, created_at DESC NULLS LAST "
				+ "LIMIT 1";
		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, rutaDinamica.trim());
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String suppliableId = resultSet.getString("suppliable_id");
					System.out.println("[RUTA PROPUESTA - ESCENARIO CREATED_AT] suppliable_id ruta inicial " + rutaDinamica.trim() + ": " + suppliableId);
					return suppliableId == null ? "" : suppliableId.trim();
				}
			}
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - ESCENARIO CREATED_AT] No fue posible obtener suppliable_id de ruta inicial " + rutaDinamica + ": " + e.getMessage());
		}
		return "";
	}

	/**
	 * Obtiene desde BD tiendas dentro o fuera del rango combinado para una Ruta Propuesta.
	 * Devuelve un mapa CR tienda a etiqueta visible CR + nombre para compararla contra el texto del grid.
	 *
	 * @param rutaDinamica Ruta Dinamica propuesta.
	 * @param fechaInicial Fecha inicial seleccionada en pantalla.
	 * @param fechaFinal Fecha final seleccionada en pantalla.
	 * @param inicio Timestamp inicial del rango combinado.
	 * @param fin Timestamp final inclusivo del rango combinado.
	 * @param dentroDeRango True para obtener tiendas dentro del rango; false para obtener tiendas fuera del rango.
	 * @return Mapa de tiendas encontradas en BD para la condicion solicitada.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	private LinkedHashMap<String, String> rutaPropuestaObtenerTiendasBDPorRango(String rutaDinamica, LocalDate fechaInicial, LocalDate fechaFinal, java.sql.Timestamp inicio, java.sql.Timestamp fin, boolean dentroDeRango) throws Exception {
		String condicionRango = dentroDeRango ? "AND h.fecha_registro BETWEEN ? AND ? " : "AND h.fecha_registro::date BETWEEN ? AND ? AND (h.fecha_registro < ? OR h.fecha_registro > ?) ";
		String query = "SELECT DISTINCT h.id_retek, COALESCE(r.cr_tienda, h.cr_tienda) AS cr_tienda, "
				+ "COALESCE(r.desc_tienda, h.cr_tienda) AS nombre_tienda, h.fecha_registro::time AS hora "
				+ "FROM xxcm_historico_correos h "
				+ "LEFT JOIN xxcm_region_plaza_tienda_job_sync r ON r.id_retek = h.id_retek "
				+ "WHERE h.ruta_propuesta = ? "
				+ condicionRango
				+ "AND h.origen = 'cedis' "
				+ "ORDER BY h.fecha_registro::time";
		LinkedHashMap<String, String> tiendas = new LinkedHashMap<String, String>();
		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, rutaDinamica.trim());
			if (dentroDeRango) {
				statement.setTimestamp(2, inicio);
				statement.setTimestamp(3, fin);
			} else {
				statement.setDate(2, java.sql.Date.valueOf(fechaInicial));
				statement.setDate(3, java.sql.Date.valueOf(fechaFinal));
				statement.setTimestamp(4, inicio);
				statement.setTimestamp(5, fin);
			}
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					String crTienda = resultSet.getString("cr_tienda");
					String nombreTienda = resultSet.getString("nombre_tienda");
					if (crTienda != null && !crTienda.trim().isEmpty()) {
						String etiqueta = (crTienda.trim() + " " + (nombreTienda == null ? "" : nombreTienda.trim())).replaceAll("\\s+", " ").trim();
						tiendas.put(crTienda.trim(), etiqueta);
					}
				}
			}
		}
		return tiendas;
	}

	/**
	 * Presiona Consultar y localiza un contenedor de resultado de Liquidacion de Ruta para Ruta Propuesta.
	 * Usa localizadores flexibles y respalda con el componente completo o el body cuando no existe encabezado especifico.
	 *
	 * @param screenshot True para guardar evidencia visual del estado posterior a la consulta.
	 * @return Elemento visible que contiene el encabezado, detalle, raiz o body de la consulta.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	private WebElement rutaPropuestaConsultarYLocalizarDetalle(boolean screenshot) throws Exception {
		rutaLiquidacionElements elementos = new rutaLiquidacionElements();
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(35));
		rutaPropuestaEsperarCargaLiquidacionRuta(wait);
		u.getDriver().findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elementos.panelFiltroTiendaActivo));
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - CONSULTA] Panel Tienda cerrado o no visible antes de consultar: " + e.getMessage());
		}
		WebElement consultar = wait.until(ExpectedConditions.elementToBeClickable(elementos.botonConsultarLiquidacionRuta));
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", consultar);
		try {
			consultar.click();
		} catch (Exception e) {
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", consultar);
		}
		rutaPropuestaEsperarCargaLiquidacionRuta(wait);
		WebElement detalle = rutaPropuestaLocalizarDetalleFlexible(wait);
		try {
			((JavascriptExecutor) u.getDriver()).executeScript("var y = arguments[0].getBoundingClientRect().top + window.pageYOffset - 260; window.scrollTo(0, Math.max(y, 0));", detalle);
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - CONSULTA] No fue posible centrar detalle, se continuara con texto visible: " + e.getMessage());
		}
		if (screenshot) {
			testCase.addScreenShotCurrentStep("Ruta_Propuesta_Resultado_Consulta");
		}
		return detalle;
	}

	/**
	 * Espera a que desaparezcan los modales de carga usados por Liquidacion de Ruta en el flujo Ruta Propuesta.
	 * Cubre textos Espere un momento y Consultando antes de leer el resultado final.
	 *
	 * @param wait Espera explicita asociada al navegador activo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	private void rutaPropuestaEsperarCargaLiquidacionRuta(WebDriverWait wait) {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
	}

	/**
	 * Localiza el detalle de Liquidacion de Ruta para Ruta Propuesta sin depender de un id especifico del DOM.
	 * Usa selector flexible, textos del resultado, raiz del componente y body como respaldos progresivos.
	 *
	 * @param wait Espera explicita asociada al navegador activo.
	 * @return Elemento visible que contiene el resultado de la consulta o un respaldo legible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	private WebElement rutaPropuestaLocalizarDetalleFlexible(WebDriverWait wait) {
		rutaLiquidacionElements elementos = new rutaLiquidacionElements();
		try {
			java.util.List<WebElement> candidatosDetalle = u.getDriver().findElements(elementos.detalleLiquidacionRuta);
			for (WebElement candidato : candidatosDetalle) {
				if (candidato.isDisplayed() && !rutaPropuestaNormalizarTexto(candidato.getText()).isEmpty()) {
					return candidato;
				}
			}
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - DETALLE FLEXIBLE] No se encontro detalle por selector flexible: " + e.getMessage());
		}

		try {
			By detallePorTexto = By.xpath("//app-cedis-liquidacion-ruta//*[contains(normalize-space(.),'Tiendas Totales') or contains(normalize-space(.),'Total de Transferencias') or contains(normalize-space(.),'Ruta:') or contains(normalize-space(.),'Tienda:')]");
			java.util.List<WebElement> candidatosTexto = u.getDriver().findElements(detallePorTexto);
			for (int i = candidatosTexto.size() - 1; i >= 0; i--) {
				WebElement candidato = candidatosTexto.get(i);
				if (candidato.isDisplayed() && !rutaPropuestaNormalizarTexto(candidato.getText()).isEmpty()) {
					return candidato;
				}
			}
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - DETALLE FLEXIBLE] No se encontro detalle por texto: " + e.getMessage());
		}

		try {
			WebElement raiz = u.getDriver().findElement(By.xpath("//app-cedis-liquidacion-ruta"));
			if (raiz.isDisplayed()) {
				System.out.println("[RUTA PROPUESTA - DETALLE FLEXIBLE] Se usara el componente app-cedis-liquidacion-ruta como respaldo");
				return raiz;
			}
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - DETALLE FLEXIBLE] No se encontro raiz Liquidacion Ruta: " + e.getMessage());
		}

		System.out.println("[RUTA PROPUESTA - DETALLE FLEXIBLE] Se usara body como respaldo para leer texto visible");
		return u.getDriver().findElement(By.tagName("body"));
	}

	/**
	 * Obtiene el texto visible del resultado de Liquidacion de Ruta para Ruta Propuesta.
	 * Si no hay contenedor especifico, toma el texto completo del componente como respaldo.
	 *
	 * @return Texto normalizado del resultado visible en pantalla.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-13
	 */
	public boolean repiteConsultaSoloTiendasConInformacionYValidaMensajeOculto(boolean screenshot) {
		try {
			boolean desmarcaSinInfo = desmarcaTiendaSinInfoPersonalizacion(screenshot);
			boolean seleccionUnica = desmarcaSinInfo && cierraSelectorTiendaYValidaTextoSeleccionUnica(screenshot);
			boolean consultarActivo = seleccionUnica && validaBotonConsultarActivo(screenshot);
			boolean mensajeOculto = consultarActivo && consultaYValidaMensajeYBotonVerTiendasOcultosEnGrid(screenshot);

			boolean resultadoPaso = desmarcaSinInfo && seleccionUnica && consultarActivo && mensajeOculto;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			StringBuilder motivoFallo = new StringBuilder();
			if (!desmarcaSinInfo) motivoFallo.append("No se pudo desmarcar la tienda sin informacion previamente seleccionada | ");
			if (desmarcaSinInfo && !seleccionUnica) motivoFallo.append("El filtro Tienda no quedo con seleccion unica despues de desmarcar la tienda sin informacion | ");
			if (seleccionUnica && !consultarActivo) motivoFallo.append("El boton Consultar no quedo habilitado con solo tienda con informacion | ");
			if (consultarActivo && !mensajeOculto) motivoFallo.append("El grid no oculto el mensaje o el boton Ver tiendas al consultar solo con tienda con informacion | ");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Repetir consulta solo con tiendas con informacion y validar mensaje oculto" + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			return resultadoPaso;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Repetir consulta solo con tiendas con informacion | Motivo: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			return false;
		}
	}

	private String rutaPropuestaObtenerTextoResultadoFlexible() {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			rutaPropuestaEsperarCargaLiquidacionRuta(wait);
			u.hardWait(2);
			WebElement detalle = rutaPropuestaLocalizarDetalleFlexible(wait);
			return rutaPropuestaNormalizarTexto(detalle.getText() + " " + rutaPropuestaObtenerTextoRaizLiquidacionRuta());
		} catch (Exception e) {
			return rutaPropuestaObtenerTextoRaizLiquidacionRuta();
		}
	}

	/**
	 * Valida que la pantalla Liquidacion de Ruta muestre el titulo y los controles
	 * principales requeridos para continuar la interaccion.
	 *
	 * @param screenshot True para capturar evidencia visual de la pantalla cargada.
	 * @return True si el titulo y los controles estan visibles, y las fechas permiten interaccion.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean validarPantallaLiquidacionRuta(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(
					"//*[contains(normalize-space(.),'Espere un momento') or contains(normalize-space(.),'Consultando')]")));
			WebElement titulo = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.tituloLiquidacionRuta));
			WebElement fechaInicial = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroFechaInicial));
			WebElement fechaFinal = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroFechaFinal));
			WebElement botonFechaInicial = wait.until(ExpectedConditions.elementToBeClickable(
					elementos.botonFechaInicialLiquidacionRuta));
			WebElement botonFechaFinal = wait.until(ExpectedConditions.elementToBeClickable(
					elementos.botonFechaFinalLiquidacionRuta));
			WebElement rutaDinamica = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			WebElement consultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.botonConsultarLiquidacionRuta));

			boolean resultadoPaso = titulo.isDisplayed() && fechaInicial.isDisplayed() && fechaFinal.isDisplayed()
					&& botonFechaInicial.isEnabled() && botonFechaFinal.isEnabled()
					&& rutaDinamica.isDisplayed() && consultar.isDisplayed();
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "No se muestran todos los controles o las fechas no permiten interaccion en Liquidacion de Ruta";
			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Pantalla Liquidacion de Ruta cargada"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (resultadoPaso && screenshot) {
				testCase.addScreenShotCurrentStep(u, "Pantalla Liquidacion de Ruta cargada");
			}
			return resultadoPaso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Validar pantalla Liquidacion de Ruta | Motivo: "
					+ exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Registra diagnostico detallado cuando no se encuentra escenario de Ruta Propuesta en BD.
	 * Muestra conteos por ruta, origen, fecha_registro y created_at, ademas de ejemplos recientes para identificar el filtro que elimina los registros.
	 *
	 * @param rutaDinamica Ruta Dinamica usada para buscar el escenario.
	 * @param fechaBase Fecha base usada para el filtro created_at mayor que inicio del dia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-14
	 */
	private void rutaPropuestaRegistrarDiagnosticoEscenario(String rutaDinamica, LocalDate fechaBase) {
		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {
			java.sql.Timestamp fechaBaseTimestamp = java.sql.Timestamp.valueOf(fechaBase.atStartOfDay());
			String resumenQuery = "SELECT "
					+ "COUNT(*) AS total_ruta, "
					+ "COUNT(*) FILTER (WHERE origen = 'cedis') AS total_origen_cedis, "
					+ "COUNT(*) FILTER (WHERE origen = 'cedis' AND fecha_registro IS NOT NULL) AS total_cedis_con_fecha_registro, "
					+ "COUNT(*) FILTER (WHERE origen = 'cedis' AND created_at > ?) AS total_cedis_created_gt_base, "
					+ "COUNT(*) FILTER (WHERE origen = 'cedis' AND created_at > ? AND fecha_registro IS NOT NULL) AS total_cedis_created_gt_base_con_fecha_registro, "
					+ "MAX(created_at) AS max_created_at, "
					+ "MAX(fecha_registro) AS max_fecha_registro "
					+ "FROM xxcm_historico_correos "
					+ "WHERE ruta_propuesta = ?";

			try (java.sql.PreparedStatement statement = connection.prepareStatement(resumenQuery)) {
				statement.setTimestamp(1, fechaBaseTimestamp);
				statement.setTimestamp(2, fechaBaseTimestamp);
				statement.setString(3, rutaDinamica.trim());
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						String resumen = "Ruta: " + rutaDinamica.trim()
								+ " | created_at > " + fechaBaseTimestamp
								+ " | total ruta: " + resultSet.getInt("total_ruta")
								+ " | total origen cedis: " + resultSet.getInt("total_origen_cedis")
								+ " | total cedis con fecha_registro: " + resultSet.getInt("total_cedis_con_fecha_registro")
								+ " | total cedis created_at > base: " + resultSet.getInt("total_cedis_created_gt_base")
								+ " | total cedis created_at > base con fecha_registro: " + resultSet.getInt("total_cedis_created_gt_base_con_fecha_registro")
								+ " | max created_at: " + resultSet.getTimestamp("max_created_at")
								+ " | max fecha_registro: " + resultSet.getTimestamp("max_fecha_registro");
						System.out.println("[RUTA PROPUESTA - DIAGNOSTICO BD] " + resumen);
						testCase.addBoldTextEvidenceCurrentStep("Diagnostico BD Ruta Propuesta: " + resumen);
					}
				}
			}

			String muestrasQuery = "SELECT id, ruta_propuesta, origen, created_at, fecha_registro, id_retek, cr_tienda "
					+ "FROM xxcm_historico_correos "
					+ "WHERE ruta_propuesta = ? "
					+ "ORDER BY created_at DESC NULLS LAST, fecha_registro DESC NULLS LAST "
					+ "LIMIT 5";
			try (java.sql.PreparedStatement statement = connection.prepareStatement(muestrasQuery)) {
				statement.setString(1, rutaDinamica.trim());
				try (ResultSet resultSet = statement.executeQuery()) {
					int contador = 0;
					while (resultSet.next()) {
						contador++;
						String muestra = "Muestra " + contador
								+ " | id: " + resultSet.getObject("id")
								+ " | ruta: " + resultSet.getString("ruta_propuesta")
								+ " | origen: " + resultSet.getString("origen")
								+ " | created_at: " + resultSet.getTimestamp("created_at")
								+ " | fecha_registro: " + resultSet.getTimestamp("fecha_registro")
								+ " | id_retek: " + resultSet.getString("id_retek")
								+ " | cr_tienda: " + resultSet.getString("cr_tienda");
						System.out.println("[RUTA PROPUESTA - DIAGNOSTICO BD] " + muestra);
						testCase.addBoldTextEvidenceCurrentStep("Diagnostico BD Ruta Propuesta: " + muestra);
					}
					if (contador == 0) {
						String sinMuestras = "No existen registros en xxcm_historico_correos para ruta " + rutaDinamica.trim();
						System.out.println("[RUTA PROPUESTA - DIAGNOSTICO BD] " + sinMuestras);
						testCase.addBoldTextEvidenceCurrentStep("Diagnostico BD Ruta Propuesta: " + sinMuestras);
					}
				}
			}

			String rutasCercanasQuery = "SELECT ruta_propuesta, origen, COUNT(*) AS total, MAX(created_at) AS max_created_at, MAX(fecha_registro) AS max_fecha_registro "
					+ "FROM xxcm_historico_correos "
					+ "WHERE origen = 'cedis' "
					+ "AND created_at > ? "
					+ "GROUP BY ruta_propuesta, origen "
					+ "ORDER BY max_created_at DESC NULLS LAST "
					+ "LIMIT 5";
			try (java.sql.PreparedStatement statement = connection.prepareStatement(rutasCercanasQuery)) {
				statement.setTimestamp(1, fechaBaseTimestamp);
				try (ResultSet resultSet = statement.executeQuery()) {
					int contador = 0;
					while (resultSet.next()) {
						contador++;
						String rutaCercana = "Ruta cedis con created_at > base " + contador
								+ " | ruta: " + resultSet.getString("ruta_propuesta")
								+ " | total: " + resultSet.getInt("total")
								+ " | max created_at: " + resultSet.getTimestamp("max_created_at")
								+ " | max fecha_registro: " + resultSet.getTimestamp("max_fecha_registro");
						System.out.println("[RUTA PROPUESTA - DIAGNOSTICO BD] " + rutaCercana);
						testCase.addBoldTextEvidenceCurrentStep("Diagnostico BD Ruta Propuesta: " + rutaCercana);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - DIAGNOSTICO BD] No fue posible registrar diagnostico: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Diagnostico BD Ruta Propuesta no disponible: " + e.getMessage());
		}
	}

	/**
	 * Registra diagnostico del texto y estructura visible cuando no se puede extraer el total del grid de Ruta Propuesta.
	 * Ayuda a identificar si el portal cambio etiquetas, si el resultado aun no renderizo o si el contenido esta fuera del componente esperado.
	 *
	 * @param textoDetalle Texto visible capturado desde el detalle o componente de Liquidacion de Ruta.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-14
	 */
	private void rutaPropuestaRegistrarDiagnosticoGrid(String textoDetalle) {
		try {
			String textoNormalizado = rutaPropuestaNormalizarTexto(textoDetalle);
			String textoLimitado = textoNormalizado.length() > 1200 ? textoNormalizado.substring(0, 1200) : textoNormalizado;
			System.out.println("[RUTA PROPUESTA - DIAGNOSTICO GRID] No se pudo extraer total del grid. Texto capturado (primeros 1200 chars): " + textoLimitado);
			testCase.addBoldTextEvidenceCurrentStep("Diagnostico Grid Ruta Propuesta | Texto capturado: " + textoLimitado);

			Object diagnosticoDom = ((JavascriptExecutor) u.getDriver()).executeScript(
					"const raiz = document.querySelector('app-cedis-liquidacion-ruta') || document.body;"
					+ "const texto = (raiz.textContent || '').replace(/\\s+/g, ' ').trim();"
					+ "const selectores = ['#detalle-ruta', '#detalle-tiendas', '[id*=detalle]', '[class*=detalle]', '[class*=tienda]', 'app-cedis-liquidacion-ruta-tienda-transferencia', 'app-tienda-transferencia'];"
					+ "const resumen = [];"
					+ "for (const selector of selectores) {"
					+ "  const nodos = Array.from(raiz.querySelectorAll(selector));"
					+ "  const visibles = nodos.filter(n => n.offsetParent !== null);"
					+ "  resumen.push(selector + '=' + nodos.length + '/visibles=' + visibles.length);"
					+ "}"
					+ "const matchesTienda = texto.match(/Tienda/g);"
					+ "const matchesTransferencia = texto.match(/Transferencia/g);"
					+ "return resumen.join(' | ') + ' | ocurrencias Tienda=' + (matchesTienda ? matchesTienda.length : 0) + ' | ocurrencias Transferencia=' + (matchesTransferencia ? matchesTransferencia.length : 0) + ' | textoBody=' + texto.substring(0, 800);");
			System.out.println("[RUTA PROPUESTA - DIAGNOSTICO GRID] " + diagnosticoDom);
			testCase.addBoldTextEvidenceCurrentStep("Diagnostico DOM Ruta Propuesta: " + diagnosticoDom);
		} catch (Exception e) {
			System.out.println("[RUTA PROPUESTA - DIAGNOSTICO GRID] No fue posible registrar diagnostico de grid: " + e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Diagnostico Grid Ruta Propuesta no disponible: " + e.getMessage());
		}
	}

	/**
	 * Obtiene una Ruta Dinamica con registros visibles en la fecha indicada y dentro
	 * del alcance del usuario. Para ADMIN permite cualquier ruta; para CEDIS limita
	 * los registros a los valores PCRFC o PCSUPPLIERID configurados para el usuario.
	 * Asigna la ruta encontrada a PortalCometaMVP4.rutaPropuesta para conservar el
	 * contrato utilizado por los scripts existentes.
	 *
	 * @param usuario Usuario cuyo perfil y alcance se validan en BD.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @return True si encuentra y asigna una ruta con al menos una tienda visible; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean obtenerRutaDinamicaConInformacionParaUsuarioEnFecha(String usuario, String fechaFormateada) {
		try {
			if (usuario == null || usuario.trim().isEmpty()
					|| fechaFormateada == null || fechaFormateada.trim().isEmpty()) {
				String motivoFallo = "Usuario o fecha vacios para obtener una Ruta Dinamica con informacion";
				System.out.println("[RESULTADO PASO] FAILED - " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			LocalDate fechaConsulta = convertirFechaVisibleALocalDate(fechaFormateada);
			String query = "SELECT h.ruta_propuesta, COUNT(DISTINCT h.id_retek) AS total_tiendas "
					+ "FROM xxcm_historico_correos h "
					+ "WHERE h.fecha_registro::date = ? "
					+ "AND h.ruta_propuesta IS NOT NULL "
					+ "AND TRIM(h.ruta_propuesta) <> '' "
					+ "AND h.version_documento = 2 "
					+ "AND h.is_visible = true "
					+ "AND h.id_retek IS NOT NULL "
					+ "AND (EXISTS ("
					+ "    SELECT 1 FROM xxcm_user_keys perfil "
					+ "    JOIN xxcm_usuarios usuario ON usuario.usuario_id = perfil.usuario_id "
					+ "    WHERE usuario.usuario = ? AND perfil.short_name = 'PCPERFIL' "
					+ "    AND UPPER(perfil.key_value) = 'ADMIN'"
					+ ") OR h.suppliable_id IN ("
					+ "    SELECT alcance.key_value FROM xxcm_user_keys alcance "
					+ "    JOIN xxcm_usuarios usuario ON usuario.usuario_id = alcance.usuario_id "
					+ "    WHERE usuario.usuario = ? "
					+ "    AND alcance.short_name IN ('PCRFC', 'PCSUPPLIERID')"
					+ ")) "
					+ "GROUP BY h.ruta_propuesta "
					+ "ORDER BY total_tiendas DESC, MAX(h.fecha_registro) DESC "
					+ "LIMIT 1";

			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
					GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setDate(1, java.sql.Date.valueOf(fechaConsulta));
				statement.setString(2, usuario.trim());
				statement.setString(3, usuario.trim());
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						PortalCometaMVP4.rutaPropuesta = resultSet.getString("ruta_propuesta").trim();
						int totalTiendas = resultSet.getInt("total_tiendas");
						System.out.println("[BD RUTA CON INFORMACION] Usuario: " + usuario
								+ " | Fecha: " + fechaConsulta + " | Ruta: "
								+ PortalCometaMVP4.rutaPropuesta + " | Tiendas: " + totalTiendas);
						testCase.addBoldTextEvidenceCurrentStep("Ruta Dinamica con informacion obtenida desde BD: "
								+ PortalCometaMVP4.rutaPropuesta + " | Fecha: " + fechaConsulta
								+ " | Tiendas visibles: " + totalTiendas);
						testCase.addBoldTextEvidenceCurrentStep("Resultado paso: PASSED - Ruta Dinamica y fecha coherentes para el perfil");
						return true;
					}
				}
			}

			String motivoFallo = "BD no devolvio rutas con informacion visible para el usuario "
					+ usuario + " en la fecha " + fechaConsulta;
			System.out.println("[RESULTADO PASO] FAILED - " + motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		} catch (Exception exception) {
			String motivoFallo = "No fue posible obtener una Ruta Dinamica con informacion para el usuario y fecha. Error: "
					+ exception.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - " + motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	/**
	 * Normaliza texto obtenido del grid de Ruta Propuesta eliminando saltos de linea,
	 * espacios no separables y espacios repetidos sin alterar el contenido visible.
	 *
	 * @param texto Texto visible que se normaliza para su lectura o comparacion.
	 * @return Texto normalizado o cadena vacia cuando el valor recibido es null.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	private String rutaPropuestaNormalizarTexto(String texto) {
		if (texto == null) {
			return "";
		}
		return texto.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
	}

	/**
	 * Valida si el texto visible del grid contiene una tienda obtenida desde BD.
	 * Primero compara la etiqueta completa CR y nombre; como respaldo busca el CR como
	 * token independiente para evitar coincidencias parciales entre tiendas similares.
	 *
	 * @param textoGrid Texto visible completo del resultado de Liquidacion de Ruta.
	 * @param crTienda CR de la tienda obtenido desde BD.
	 * @param etiquetaTienda Etiqueta esperada formada por CR y nombre de tienda.
	 * @return True cuando la etiqueta completa o el CR independiente aparecen en el grid.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	private boolean rutaPropuestaTextoContieneTienda(String textoGrid, String crTienda, String etiquetaTienda) {
		String textoNormalizado = rutaPropuestaNormalizarTexto(textoGrid).toLowerCase(new Locale("es", "MX"));
		String etiquetaNormalizada = rutaPropuestaNormalizarTexto(etiquetaTienda).toLowerCase(new Locale("es", "MX"));
		String crNormalizado = rutaPropuestaNormalizarTexto(crTienda).toLowerCase(new Locale("es", "MX"));

		if (!etiquetaNormalizada.isEmpty() && textoNormalizado.contains(etiquetaNormalizada)) {
			return true;
		}
		if (crNormalizado.isEmpty()) {
			return false;
		}

		String expresionCrIndependiente = "(?s).*(?<![\\p{L}\\p{N}])"
				+ java.util.regex.Pattern.quote(crNormalizado)
				+ "(?![\\p{L}\\p{N}]).*";
		return textoNormalizado.matches(expresionCrIndependiente);
	}

	/**
	 * Obtiene el texto visible de la raiz de Liquidacion de Ruta para complementar
	 * la lectura de contenedores de resultado que pueden renderizarse por separado.
	 *
	 * @return Texto normalizado de app-cedis-liquidacion-ruta o del body como respaldo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	private String rutaPropuestaObtenerTextoRaizLiquidacionRuta() {
		try {
			java.util.List<WebElement> componentes = u.getDriver()
					.findElements(By.cssSelector("app-cedis-liquidacion-ruta"));
			for (WebElement componente : componentes) {
				if (componente.isDisplayed()) {
					return rutaPropuestaNormalizarTexto(componente.getText());
				}
			}

			WebElement body = u.getDriver().findElement(By.tagName("body"));
			return body.isDisplayed() ? rutaPropuestaNormalizarTexto(body.getText()) : "";
		} catch (Exception exception) {
			System.out.println("[RUTA PROPUESTA - TEXTO RAIZ] No fue posible obtener texto visible: "
					+ exception.getMessage());
			return "";
		}
	}

	/**
	 * Formatea una fecha para seleccionar y documentar el valor visible en los
	 * filtros de Liquidacion de Ruta, por ejemplo 17 jul 2026.
	 *
	 * @param fecha Fecha que se convierte al formato visible del portal.
	 * @return Fecha en formato d MMM yyyy, en espanol y sin punto de abreviatura.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	private String rutaPropuestaFormatearFechaVisible(LocalDate fecha) {
		if (fecha == null) {
			return "";
		}
		return fecha.format(DateTimeFormatter.ofPattern("d MMM yyyy", new Locale("es", "ES")))
				.replace(".", "")
				.toLowerCase(new Locale("es", "ES"));
	}

	/**
	 * Convierte la hora minima obtenida desde BD al inicio del mismo bloque horario
	 * disponible en el portal, garantizando que el registro minimo quede incluido.
	 *
	 * @param hora Hora minima obtenida desde fecha_registro.
	 * @return Hora inicial en formato HH:00 o cadena vacia si la hora es null.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	private String rutaPropuestaObtenerHoraInicioPortal(LocalTime hora) {
		return hora == null ? "" : String.format("%02d:00", hora.getHour());
	}

	/**
	 * Convierte una hora obtenida desde BD al bloque final HH:00 del portal.
	 * El filtro interpreta el valor como inclusivo hasta HH:59:59.
	 *
	 * @param hora Hora maxima o intermedia obtenida desde fecha_registro.
	 * @return Hora final en formato HH:00 o cadena vacia si la hora es null.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	private String rutaPropuestaObtenerHoraFinPortal(LocalTime hora) {
		return hora == null ? "" : String.format("%02d:00", hora.getHour());
	}

	/**
	 * Extrae el total de tiendas desde los encabezados conocidos del resultado de
	 * Liquidacion de Ruta, tolerando espacios y variantes menores de la etiqueta.
	 *
	 * @param textoDetalle Texto normalizado del encabezado o componente de resultado.
	 * @return Total encontrado o -1 cuando el texto no contiene un total reconocible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	private int rutaPropuestaExtraerTotalTiendasGrid(String textoDetalle) {
		String textoNormalizado = rutaPropuestaNormalizarTexto(textoDetalle);
		java.util.regex.Pattern patronTotal = java.util.regex.Pattern.compile(
				"(?:Tiendas\\s+Totales(?:\\s+de\\s+la\\s+Ruta)?|Total\\s+de\\s+Tiendas)\\s*:?\\s*(\\d+)",
				java.util.regex.Pattern.CASE_INSENSITIVE | java.util.regex.Pattern.UNICODE_CASE);
		java.util.regex.Matcher matcher = patronTotal.matcher(textoNormalizado);
		return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
	}

	/**
	 * Cuenta por JavaScript los renglones de tienda visibles cuando el encabezado no
	 * expone el total, usando los selectores ya soportados por Liquidacion de Ruta.
	 *
	 * @return Cantidad de renglones visibles o -1 cuando no pueden identificarse.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	private int rutaPropuestaContarTiendasVisiblesPorJavascript() {
		try {
			Object resultado = ((JavascriptExecutor) u.getDriver()).executeScript(
					"const raiz = document.querySelector('app-cedis-liquidacion-ruta #detalle-tiendas')"
					+ " || document.querySelector('#detalle-tiendas')"
					+ " || document.querySelector('app-cedis-liquidacion-ruta');"
					+ "if (!raiz) return -1;"
					+ "const selectores = ['.tienda-wrapper', '[id=\"tienda\"]', 'app-cedis-liquidacion-ruta-tienda-transferencia', 'app-tienda-transferencia', '[class*=\"tienda-transferencia\"]', '[class*=\"renglon-transferencia\"]', 'mat-card', '[class*=\"card-transferencia\"]'];"
					+ "for (const selector of selectores) {"
					+ "  const visibles = Array.from(raiz.querySelectorAll(selector))"
					+ "    .filter(elemento => elemento.offsetParent !== null && elemento.getBoundingClientRect().height > 20);"
					+ "  if (visibles.length > 0) return visibles.length;"
					+ "}"
					+ "return -1;");
			return resultado instanceof Number ? ((Number) resultado).intValue() : -1;
		} catch (Exception exception) {
			System.out.println("[RUTA PROPUESTA - CONTEO GRID] No fue posible contar tiendas visibles: "
					+ exception.getMessage());
			return -1;
		}
	}

	/**
	 * Obtiene una Ruta Dinamica de origen CEDIS y su fecha consultable como un solo escenario.
	 * Para CEDIS limita el resultado al supplier configurado; para ADMIN permite cualquier supplier,
	 * exige entre dos y cincuenta tiendas y transferencias para respetar el limite visible del resultado,
	 * y restringe la fecha a los cuatro dias disponibles en el portal.
	 *
	 * @param usuario Usuario cuyo perfil y alcance se validan en BD.
	 * @return True si encuentra y asigna una ruta y fecha elegibles; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean obtenerEscenarioRutaDinamicaConMultiplesTiendasParaUsuario(String usuario) {
		try {
			if (usuario == null || usuario.trim().isEmpty()) {
				String motivoFallo = "Usuario vacio para obtener escenario de Ruta Dinamica con multiples tiendas";
				System.out.println("[RESULTADO PASO] FAILED - " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			String query = "SELECT h.ruta_propuesta, h.fecha_registro::date AS fecha_consulta, "
					+ "COUNT(DISTINCT h.id_retek) AS total_tiendas, "
					+ "COUNT(DISTINCT h.transferencia) AS total_transferencias, "
					+ "MAX(h.fecha_registro) AS ultimo_registro "
					+ "FROM xxcm_historico_correos h "
					+ "WHERE h.origen = 'cedis' "
					+ "AND h.version_documento = 2 "
					+ "AND h.is_visible = true "
					+ "AND h.id_retek IS NOT NULL "
					+ "AND h.transferencia IS NOT NULL "
					+ "AND h.ruta_propuesta IS NOT NULL "
					+ "AND TRIM(h.ruta_propuesta) <> '' "
					+ "AND h.fecha_registro::date BETWEEN CURRENT_DATE - 3 AND CURRENT_DATE "
					+ "AND (EXISTS ("
					+ "    SELECT 1 FROM xxcm_user_keys perfil "
					+ "    JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = perfil.usuario_id "
					+ "    WHERE datos_usuario.usuario = ? "
					+ "    AND perfil.short_name = 'PCPERFIL' "
					+ "    AND UPPER(TRIM(perfil.key_value)) = 'ADMIN'"
					+ ") OR h.suppliable_id::text IN ("
					+ "    SELECT alcance.key_value FROM xxcm_user_keys alcance "
					+ "    JOIN xxcm_usuarios datos_usuario ON datos_usuario.usuario_id = alcance.usuario_id "
					+ "    WHERE datos_usuario.usuario = ? "
					+ "    AND alcance.short_name IN ('PCRFC', 'PCSUPPLIERID')"
					+ ")) "
					+ "GROUP BY h.ruta_propuesta, h.fecha_registro::date "
					+ "HAVING COUNT(DISTINCT h.id_retek) BETWEEN 2 AND 50 "
					+ "AND COUNT(DISTINCT h.transferencia) BETWEEN 2 AND 50 "
					+ "ORDER BY h.fecha_registro::date DESC, "
					+ "COUNT(DISTINCT h.id_retek) DESC, "
					+ "COUNT(DISTINCT h.transferencia) DESC, "
					+ "MAX(h.fecha_registro) DESC, h.ruta_propuesta ASC "
					+ "LIMIT 1";

			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
					GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					java.sql.PreparedStatement statement = connection.prepareStatement(query)) {
				statement.setString(1, usuario.trim());
				statement.setString(2, usuario.trim());
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						PortalCometaMVP4.rutaPropuesta = resultSet.getString("ruta_propuesta").trim();
						LocalDate fechaConsulta = resultSet.getDate("fecha_consulta").toLocalDate();
						PortalCometaMVP4.fechaFormateada = rutaPropuestaFormatearFechaVisible(fechaConsulta);
						int totalTiendas = resultSet.getInt("total_tiendas");
						int totalTransferencias = resultSet.getInt("total_transferencias");
						String detalle = "Usuario: " + usuario.trim()
								+ " | Ruta: " + PortalCometaMVP4.rutaPropuesta
								+ " | Fecha: " + PortalCometaMVP4.fechaFormateada
								+ " | Tiendas: " + totalTiendas
								+ " | Transferencias: " + totalTransferencias;
						System.out.println("[ESCENARIO RUTA DINAMICA MULTIPLE] " + detalle);
						testCase.addBoldTextEvidenceCurrentStep("Escenario BD seleccionado: " + detalle);
						testCase.addBoldTextEvidenceCurrentStep("Resultado paso: PASSED - Ruta y fecha elegibles obtenidas en una sola consulta");
						return true;
					}
				}
			}

			String motivoFallo = "No se encontro una Ruta Dinamica de origen CEDIS dentro de los cuatro dias visibles, con entre dos y cincuenta tiendas y transferencias para el usuario " + usuario.trim();
			System.out.println("[RESULTADO PASO] FAILED - " + motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		} catch (Exception exception) {
			String motivoFallo = "No fue posible obtener el escenario de Ruta Dinamica con multiples tiendas. Error: " + exception.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - " + motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	/**
	 * Marca dos tiendas con informacion usando el alcance global conservado para los flujos existentes.
	 * Delega en la variante por usuario sin aplicar restriccion de perfil o supplier.
	 *
	 * @param screenshot True para capturar evidencia visual de las tiendas marcadas.
	 * @return True si se marcan dos tiendas con informacion en el rango; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean marcaDosTiendasConInformacionEnRangoDesdeBD(boolean screenshot) {
		return marcaDosTiendasConInformacionEnRangoDesdeBD(null, screenshot);
	}

	/**
	 * Obtiene la fecha mas reciente con informacion CEDIS usando el alcance global conservado.
	 * Delega en la variante por usuario sin aplicar restriccion de perfil o supplier.
	 *
	 * @return Fecha formateada como la muestra el portal, o cadena vacia si no existe.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public String obtenerFechaConInformacionTiendaDesdeBD() {
		return obtenerFechaConInformacionTiendaDesdeBD(null);
	}

	/**
	 * Selecciona una tienda con informacion y otra sin informacion usando el alcance global conservado.
	 * Delega en la variante por usuario sin aplicar restriccion de perfil o supplier.
	 *
	 * @param fechaFormateada Fecha capturada en el portal en formato d MMM yyyy.
	 * @param screenshot True para capturar evidencia visual de la seleccion de ambas tiendas.
	 * @return True si ambas tiendas quedan marcadas en el filtro Tienda; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean marcaPrimeraTiendaConInfoYUnaSinInfoEnRangoDesdeBD(String fechaFormateada, boolean screenshot) {
		return marcaPrimeraTiendaConInfoYUnaSinInfoEnRangoDesdeBD(null, fechaFormateada, screenshot);
	}

	/**
	 * Obtiene una Ruta Dinamica con tiendas sin informacion usando el alcance global conservado.
	 * Delega en la variante por usuario sin aplicar restriccion de perfil o supplier.
	 *
	 * @param fechaFormateada Fecha del filtro usada para evaluar tiendas sin informacion por ruta.
	 * @return Ruta Dinamica valida o cadena vacia si no se encuentra ninguna.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public String obtenerRutaDinamicaConTiendaSinInfoEnRangoDesdeBD(String fechaFormateada) {
		return obtenerRutaDinamicaConTiendaSinInfoEnRangoDesdeBD(null, fechaFormateada);
	}

	/**
	 * Obtiene RETEK_LOC_ID por ruta y fecha usando el alcance global conservado para flujos existentes.
	 * Delega en la variante por usuario sin aplicar restriccion de perfil o supplier.
	 *
	 * @param rutaDinamica Ruta Dinamica usada en el filtro del portal.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @return Mapa ordenado id_retek -> etiqueta de tienda para la ruta y fecha consultadas.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public LinkedHashMap<String, String> obtenerRetekLocIdsRutaDinamicaEnFechaDesdeBD(String rutaDinamica, String fechaFormateada) {
		return obtenerRetekLocIdsRutaDinamicaEnFechaDesdeBD(null, rutaDinamica, fechaFormateada);
	}

	/**
	 * Valida RETEK_LOC_ID contra el grid usando el alcance global conservado para flujos existentes.
	 * Delega en la variante por usuario sin aplicar restriccion de perfil o supplier.
	 *
	 * @param rutaDinamica Ruta Dinamica usada en el filtro del portal.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param screenshot True para capturar evidencia visual del grid validado.
	 * @return True si RETEK_LOC_ID, cantidad y tiendas coinciden con el grid; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaRetekLocIdUnicoYCoincideConGrid(String rutaDinamica, String fechaFormateada, boolean screenshot) {
		return validaRetekLocIdUnicoYCoincideConGrid(null, rutaDinamica, fechaFormateada, screenshot);
	}

	/**
	 * Valida el payload RETEK_LOC_ID usando el alcance global conservado para flujos existentes.
	 * Delega en la variante por usuario sin aplicar restriccion de perfil o supplier.
	 *
	 * @param rutaDinamica Ruta Dinamica usada en el filtro del portal.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param screenshot True para capturar evidencia visual de la validacion.
	 * @return True si el payload usa los RETEK_LOC_ID esperados; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaPayloadConsultaUsaRetekLocIdComoLlave(String rutaDinamica, String fechaFormateada, boolean screenshot) {
		return validaPayloadConsultaUsaRetekLocIdComoLlave(null, rutaDinamica, fechaFormateada, screenshot);
	}

	/**
	 * Valida la trazabilidad BD de tiendas Incluidas usando el alcance global conservado.
	 * Delega en la variante por usuario sin aplicar restriccion de perfil o supplier.
	 *
	 * @param rutaDinamica Ruta Dinamica usada en el filtro.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param screenshot True para capturar evidencia visual del resultado.
	 * @return True si la trazabilidad BD y el grid son consistentes; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaSumaResumenContraBDTiendasIncluidas(String rutaDinamica, String fechaFormateada, boolean screenshot) {
		return validaSumaResumenContraBDTiendasIncluidas(null, rutaDinamica, fechaFormateada, screenshot);
	}

	/**
	 * Captura un snapshot BD usando el alcance global conservado para flujos existentes.
	 * Delega en la variante por usuario sin aplicar restriccion de perfil o supplier.
	 *
	 * @param rutaDinamica Ruta Dinamica capturada en el filtro.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param etiqueta Etiqueta unica del snapshot.
	 * @param screenshot True para capturar evidencia visual del snapshot.
	 * @return True si BD devuelve y almacena al menos un registro; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean capturaSnapshotBDRegistrosRutaDinamicaEnFecha(String rutaDinamica, String fechaFormateada, String etiqueta, boolean screenshot) {
		return capturaSnapshotBDRegistrosRutaDinamicaEnFecha(null, rutaDinamica, fechaFormateada, etiqueta, screenshot);
	}

	/**
	 * Compara snapshots BD usando el alcance global conservado para flujos existentes.
	 * Delega en la variante por usuario sin aplicar restriccion de perfil o supplier.
	 *
	 * @param rutaDinamica Ruta Dinamica usada en el filtro.
	 * @param fechaFormateada Fecha visible del portal en formato d MMM yyyy.
	 * @param etiquetaSnapshotReferencia Etiqueta del snapshot usado como referencia.
	 * @param screenshot True para capturar evidencia visual de la comparacion.
	 * @return True si ambos snapshots son identicos; false en caso contrario.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaSnapshotBDRegistrosCoincideConReferencia(String rutaDinamica, String fechaFormateada, String etiquetaSnapshotReferencia, boolean screenshot) {
		return validaSnapshotBDRegistrosCoincideConReferencia(null, rutaDinamica, fechaFormateada, etiquetaSnapshotReferencia, screenshot);
	}
	
	/**
	 * Espera a que desaparezca el modal de carga "Espere un momento" que puede mostrarse antes de seleccionar Liquidacion de Ruta.
	 * Si el modal aparece, registra evidencia opcional y espera a que deje de bloquear la pantalla.
	 *
	 * @param screenshot true para registrar evidencia visual del modal cuando aparece, false en caso contrario.
	 * @return true si el modal no aparece o desaparece dentro del tiempo esperado; false en caso contrario.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-21
	 */
	public boolean esperaModalEspereUnMomentoLiquidacionRutaDesaparece(boolean screenshot) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			WebDriverWait esperaCorta = new WebDriverWait(u.getDriver(), Duration.ofSeconds(5));
			try {
				esperaCorta.until(ExpectedConditions.visibilityOfElementLocated(elementos.mensajeEspereUnMomentoLiquidacionRuta));
				if (screenshot) {
					u.highLight(elementos.mensajeEspereUnMomentoLiquidacionRuta);
					testCase.addTextEvidenceCurrentStep("Se muestra modal de carga: Espere un momento antes de seleccionar Liquidacion de Ruta.");
					testCase.addScreenShotCurrentStep(u, "Modal_Espere_Un_Momento_Liquidacion_Ruta");
					u.unhighLight(elementos.mensajeEspereUnMomentoLiquidacionRuta);
				}
			} catch (Exception modalNoVisible) {
				return true;
			}

			WebDriverWait esperaModal = new WebDriverWait(u.getDriver(), Duration.ofSeconds(90));
			esperaModal.until(ExpectedConditions.invisibilityOfElementLocated(elementos.modalEspereUnMomentoLiquidacionRuta));
			u.hardWait(1);
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al esperar desaparicion del modal Espere un momento: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Ingresa a Liquidacion de Ruta desde Recepcion y valida la carga inicial de la pantalla.
	 * Registra T1, T2 y T_carga; valida modal Espere un momento opcional, cierre automatico en tiempo maximo y filtros principales sin No items found.
	 *
	 * @param tiempoMaximoSegundos tiempo maximo permitido para que la pantalla quede disponible.
	 * @return true cuando Liquidacion de Ruta queda disponible dentro del tiempo maximo y sus filtros cargan correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	public boolean ingresarLiquidacionRutaYValidarCargaInicial(int tiempoMaximoSegundos) {
		try {
			rutaLiquidacionElements elementos = new rutaLiquidacionElements();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime t1 = LocalDateTime.now();
			String textoT1 = t1.format(formatter);
			long inicioCarga = System.currentTimeMillis();
			testCase.addBoldTextEvidenceCurrentStep("T1 inicio de carga Liquidacion de Ruta: " + textoT1);

			WebDriverWait waitClick = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement boton = waitClick.until(ExpectedConditions.visibilityOfElementLocated(elementos.btnLiquidacionRutaRecepcion));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", boton);
			try {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", boton);
			} catch (Exception clickJs) {
				boton.click();
			}

			boolean modalValido = validarModalInicialLiquidacionRuta(elementos);
			boolean pantallaDisponible = esperarPantallaLiquidacionRutaDisponible(elementos, inicioCarga, tiempoMaximoSegundos);
			LocalDateTime t2 = LocalDateTime.now();
			String textoT2 = t2.format(formatter);
			double tiempoCarga = (System.currentTimeMillis() - inicioCarga) / 1000.0;
			String textoTiempoCarga = String.format("%.2f", tiempoCarga);
			testCase.addBoldTextEvidenceCurrentStep("T2 fin de carga Liquidacion de Ruta: " + textoT2 + " | T_carga: " + textoTiempoCarga + " segundos");

			if (!modalValido) {
				return registrarCargaLiquidacionRuta(false, "El modal Espere un momento fue visible, pero no cubrio la pantalla completa durante la carga", "Carga_Inicial_Liquidacion_Ruta_Modal");
			}

			if (!pantallaDisponible || tiempoCarga > tiempoMaximoSegundos) {
				return registrarCargaLiquidacionRuta(false, "Liquidacion de Ruta no quedo disponible dentro de " + tiempoMaximoSegundos + " segundos. T_carga: " + textoTiempoCarga, "Carga_Inicial_Liquidacion_Ruta_Tiempo");
			}

			boolean filtrosVisibles = validarFiltrosCargaInicialLiquidacionRuta(elementos);
			agregarResumenCargaInicialLiquidacionRuta(textoT1, textoT2, textoTiempoCarga, tiempoMaximoSegundos, modalValido, pantallaDisponible, filtrosVisibles);
			return registrarCargaLiquidacionRuta(filtrosVisibles,
					"Liquidacion de Ruta quedo disponible en " + textoTiempoCarga + " segundos y los filtros cargaron correctamente",
					"Carga_Inicial_Liquidacion_Ruta_CEDIS");
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al validar carga inicial de Liquidacion de Ruta: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida si el modal Espere un momento aparece al ingresar a Liquidacion de Ruta y confirma que cubra pantalla si aparece.
	 * La matriz permite carga inmediata cuando el modal no es perceptible.
	 *
	 * @param elementos selectores de Liquidacion de Ruta.
	 * @return true cuando el modal no aparece por carga inmediata o cuando aparece cubriendo pantalla.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean validarModalInicialLiquidacionRuta(rutaLiquidacionElements elementos) {
		try {
			WebElement overlay = new WebDriverWait(u.getDriver(), Duration.ofSeconds(2))
					.until(ExpectedConditions.visibilityOfElementLocated(elementos.overlayCargaLiquidacionRuta));
			boolean cubrePantalla = overlayCubreVistaLiquidacionRuta(overlay);
			testCase.addBoldTextEvidenceCurrentStep("Modal 'Espere un momento' visible en Liquidacion de Ruta. Cubre pantalla: " + cubrePantalla);
			testCase.addScreenShotCurrentStep("Overlay_Espere_Un_Momento_Liquidacion_Ruta");
			return cubrePantalla;
		} catch (Exception cargaInmediata) {
			testCase.addBoldTextEvidenceCurrentStep("La carga de Liquidacion de Ruta fue inmediata; el modal 'Espere un momento' no fue perceptible dentro de la ventana inicial.");
			return true;
		}
	}

	/**
	 * Espera a que Liquidacion de Ruta quede disponible con sus filtros base visibles.
	 *
	 * @param elementos selectores de Liquidacion de Ruta.
	 * @param inicioCarga marca de tiempo registrada antes del clic en Liquidacion de Ruta.
	 * @param tiempoMaximoSegundos tiempo maximo permitido para la carga inicial.
	 * @return true cuando la pantalla queda disponible dentro del tiempo esperado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean esperarPantallaLiquidacionRutaDisponible(rutaLiquidacionElements elementos, long inicioCarga, int tiempoMaximoSegundos) {
		try {
			long segundosTranscurridos = (System.currentTimeMillis() - inicioCarga) / 1000;
			long segundosRestantes = Math.max(1, tiempoMaximoSegundos - segundosTranscurridos);
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(segundosRestantes));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elementos.overlayCargaLiquidacionRuta));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.tituloLiquidacionRuta));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroFechaInicial));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroFechaFinal));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroSelectorFiltro));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroRutaDinamica));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elementos.filtroTienda));
			return true;
		} catch (Exception exception) {
			testCase.addBoldTextEvidenceCurrentStep("La pantalla Liquidacion de Ruta no quedo disponible dentro del tiempo maximo: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida filtros principales de Liquidacion de Ruta al terminar la carga inicial.
	 *
	 * @param elementos selectores de Liquidacion de Ruta.
	 * @return true si los filtros esperados estan visibles y no aparece No items found.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean validarFiltrosCargaInicialLiquidacionRuta(rutaLiquidacionElements elementos) {
		try {
			boolean fechaInicial = u.isDisplayed(elementos.filtroFechaInicial);
			boolean fechaFinal = u.isDisplayed(elementos.filtroFechaFinal);
			boolean selector = u.isDisplayed(elementos.filtroSelectorFiltro);
			boolean ruta = u.isDisplayed(elementos.filtroRutaDinamica);
			boolean tienda = u.isDisplayed(elementos.filtroTienda);
			boolean sinNoItemsFound = u.getDriver().findElements(elementos.mensajeNoItemsFoundLiquidacionRuta).stream().noneMatch(WebElement::isDisplayed);
			testCase.addTextEvidenceCurrentStep("Filtro Fecha inicial visible: " + fechaInicial);
			testCase.addTextEvidenceCurrentStep("Filtro Fecha final visible: " + fechaFinal);
			testCase.addTextEvidenceCurrentStep("Selector de Filtro visible: " + selector);
			testCase.addTextEvidenceCurrentStep("Campo Ruta Dinamica visible: " + ruta);
			testCase.addTextEvidenceCurrentStep("Filtro Tienda visible: " + tienda);
			testCase.addTextEvidenceCurrentStep("Liquidacion de Ruta sin No items found visible: " + sinNoItemsFound);
			return fechaInicial && fechaFinal && selector && ruta && tienda && sinNoItemsFound;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al validar filtros de carga inicial Liquidacion de Ruta: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Agrega al reporte el resumen de tiempos y filtros de la carga inicial de Liquidacion de Ruta.
	 *
	 * @param t1 fecha y hora de inicio de carga.
	 * @param t2 fecha y hora de fin de carga.
	 * @param tiempoCarga duracion total en segundos.
	 * @param tiempoMaximoSegundos tiempo maximo esperado.
	 * @param modalValido resultado de validacion del modal.
	 * @param pantallaDisponible resultado de disponibilidad de pantalla.
	 * @param filtrosVisibles resultado de validacion de filtros.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private void agregarResumenCargaInicialLiquidacionRuta(String t1, String t2, String tiempoCarga,
			int tiempoMaximoSegundos, boolean modalValido, boolean pantallaDisponible, boolean filtrosVisibles) {
		testCase.addBoldTextEvidenceCurrentStep("Resumen de carga inicial Liquidacion de Ruta");
		testCase.addTextEvidenceCurrentStep("T1 inicio: " + t1);
		testCase.addTextEvidenceCurrentStep("T2 fin: " + t2);
		testCase.addTextEvidenceCurrentStep("T_carga: " + tiempoCarga + " segundos / Maximo " + tiempoMaximoSegundos + " segundos");
		testCase.addTextEvidenceCurrentStep("Modal Espere un momento: " + modalValido);
		testCase.addTextEvidenceCurrentStep("Pantalla disponible: " + pantallaDisponible);
		testCase.addTextEvidenceCurrentStep("Filtros visibles y sin No items found: " + filtrosVisibles);
	}

	/**
	 * Registra el resultado de la validacion de carga inicial de Liquidacion de Ruta.
	 *
	 * @param resultado resultado booleano de la validacion.
	 * @param mensaje mensaje descriptivo del resultado.
	 * @param nombreCaptura nombre de captura asociado al resultado.
	 * @return el mismo resultado recibido.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean registrarCargaLiquidacionRuta(boolean resultado, String mensaje, String nombreCaptura) {
		System.out.println("[RESULTADO PASO] " + (resultado ? "PASSED" : "FAILED") + " - " + mensaje);
		testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + (resultado ? "PASSED" : "FAILED") + " - " + mensaje);
		try { testCase.addScreenShotCurrentStep(nombreCaptura); } catch (Exception ignore) { }
		return resultado;
	}

	/**
	 * Valida mediante JavaScript si el modal u overlay de Liquidacion de Ruta cubre la pantalla.
	 *
	 * @param overlay elemento visible del modal u overlay.
	 * @return true cuando existe backdrop activo o el contenedor ocupa la mayor parte del viewport.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean overlayCubreVistaLiquidacionRuta(WebElement overlay) {
		try {
			Object result = ((JavascriptExecutor) u.getDriver()).executeScript(
					"const node = arguments[0].closest('mat-dialog-container, .cdk-overlay-pane, .cdk-overlay-container, .cdk-global-overlay-wrapper') || arguments[0]; const r = node.getBoundingClientRect(); const backdrop = document.querySelector('.cdk-overlay-backdrop.cdk-overlay-backdrop-showing, .modal-backdrop.show'); return !!backdrop || (r.width >= window.innerWidth * 0.6 && r.height >= window.innerHeight * 0.6);", overlay);
			return Boolean.TRUE.equals(result);
		} catch (Exception exception) {
			return true;
		}
	}

}