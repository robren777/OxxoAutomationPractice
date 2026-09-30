package WEB.cometa.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.util.GlobalVariables;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class recepcionTransferenciasPage {

	private final SeleniumUtil u;
	private final TestCase testCase;
	private final recepcionTransferenciasElements elements;
	private String ultimaCanastilla;
	private String transferenciaA;
	private String transferenciaB;
	private String fechaInicioHistoricaBD;
	private String fechaFinHistoricaBD;
	private int conteoTransferenciaA;
	private int contadorPasoFallback;
	private List<String> ultimoSnapshotGrid = new ArrayList<String>();
	private String usuarioContextoActual;

	public recepcionTransferenciasPage(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
		this.elements = new recepcionTransferenciasElements();
	}

	/**
	 * Valida la URL de Recepcion sin errores de redireccion.
	 *
	 * @return true si la ruta es correcta.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaRutaRecepcion() {
		try {
			String url = u.getDriver().getCurrentUrl().toLowerCase();
			String source = u.getDriver().getPageSource().toLowerCase();
			boolean result = url.contains("/cedis/recepcion-de-transferencias-y-devoluciones")
					&& !source.contains("404") && !source.contains("not found");
			return registrar(result, "La URL corresponde a Recepcion de Transferencias y Devoluciones",
					"URL inesperada o error 404. URL actual: " + url, "URL_Recepcion_CEDIS");
		} catch (Exception exception) { return error("No fue posible validar la URL: " + exception.getMessage()); }
	}

	/**
	 * Valida el titulo principal de la pantalla.
	 *
	 * @return true si el titulo esperado esta visible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaTituloPantalla() {
		try { return registrarConResaltado(u.isDisplayed(elements.tituloPantalla),
				"La pantalla muestra el titulo Recepcion de Transferencias y Devoluciones",
				"No se encontro el titulo Recepcion de Transferencias y Devoluciones", "Titulo_Recepcion_CEDIS",
				elements.tituloPantalla);
		} catch (Exception exception) { return error("No fue posible validar el titulo: " + exception.getMessage()); }
	}

	/**
	 * Valida los botones superiores Liquidacion de Ruta y Devoluciones Especiales.
	 *
	 * @return true si ambos estan visibles y habilitados.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaBotonesSuperiores() {
		try {
			WebElement liquidacion = u.getDriver().findElement(elements.botonLiquidacionRuta);
			WebElement devoluciones = u.getDriver().findElement(elements.botonDevolucionesEspeciales);
			return registrarConResaltado(liquidacion.isDisplayed() && liquidacion.isEnabled() && devoluciones.isDisplayed() && devoluciones.isEnabled(),
					"Liquidacion de ruta y Devoluciones Especiales son visibles y funcionales",
					"Uno de los botones superiores no esta visible o habilitado", "Botones_Superiores_Recepcion",
					elements.botonLiquidacionRuta, elements.botonDevolucionesEspeciales);
		} catch (Exception exception) { return error("No fue posible validar los botones superiores: " + exception.getMessage()); }
	}

	/**
	 * Valida y resalta individualmente los controles de busqueda historica.
	 * La evidencia identifica Plaza, Tienda, Selector de Ruta, Ruta y Rango de Fechas sin resaltar el contenedor completo.
	 *
	 * @return true si el titulo y todos los campos historicos requeridos estan visibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 * Valida la seccion y campos de busqueda historica.
	 *
	 * @return true si todos los campos requeridos estan visibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaFiltrosHistoricos() {
		try {
			boolean result = u.isDisplayed(elements.tituloFiltrosHistoricos) && u.isDisplayed(elements.filtroPlaza)
					&& u.isDisplayed(elements.filtroTienda) && u.isDisplayed(elements.selectorRuta)
					&& u.isDisplayed(elements.filtroRuta) && u.isDisplayed(elements.fechaInicio) && u.isDisplayed(elements.fechaFin);
			return registrarConResaltado(result, "Filtros de Busqueda Historica muestra Plaza, Tienda, Selector de Ruta, Ruta y Rango de fechas",
					"Falta uno o mas campos en Filtros de Busqueda Historica", "Filtros_Historicos_Recepcion",
					elements.tituloFiltrosHistoricos, elements.filtroPlaza, elements.filtroTienda,
					elements.selectorRuta, elements.campoRuta, elements.campoRangoFechas);
		} catch (Exception exception) { return error("No fue posible validar filtros historicos: " + exception.getMessage()); }
	}

	/**
	 * Valida la seccion y campo de busqueda rapida.
	 *
	 * @return true si el campo de canastilla esta visible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaFiltrosRapidos() {
		try { return registrarConResaltado(u.isDisplayed(elements.tituloFiltrosRapidos) && u.isDisplayed(elements.etiquetaCanastilla),
				"Filtros de Busqueda Rapida muestra el campo Escanea o teclea la canastilla",
				"No se encontro la seccion o el campo de Busqueda Rapida", "Filtros_Rapidos_Recepcion",
				elements.seccionFiltrosRapidos);
		} catch (Exception exception) { return error("No fue posible validar filtros rapidos: " + exception.getMessage()); }
	}

	/**
	 * Valida la seccion de descarga masiva y sus tres controles.
	 *
	 * @return true si FUE, Verificacion y Tira estan visibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaDescargasMasivas() {
		try {
			esperarFinCarga();
			WebElement seccionDescarga = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.seccionDescargaMasiva));
			((JavascriptExecutor) u.getDriver()).executeScript(
					"arguments[0].scrollIntoView({block:'center', inline:'center'});", seccionDescarga);
			u.hardWait(1);
			boolean textoVisible = u.getDriver().findElements(elements.textoDescargaMasiva).stream()
					.anyMatch(WebElement::isDisplayed)
					|| u.getDriver().findElements(elements.textoDescargaMasivaFlexible).stream()
							.anyMatch(WebElement::isDisplayed);
			List<WebElement> controlesVisibles = u.getDriver().findElements(elements.iconosPdfDescargaMasiva).stream()
					.filter(WebElement::isDisplayed).collect(java.util.stream.Collectors.toList());
			int iconosPdfVisibles = controlesVisibles.size();
			boolean fueVisible = u.getDriver().findElements(elements.descargaMasivaFue).stream()
					.anyMatch(WebElement::isDisplayed);
			boolean verificacionVisible = u.getDriver().findElements(elements.descargaMasivaVerificacion).stream()
					.anyMatch(WebElement::isDisplayed);
			boolean tiraVisible = u.getDriver().findElements(elements.descargaMasivaTira).stream()
					.anyMatch(WebElement::isDisplayed);
			String textoSeccionNormalizado = normalizar(seccionDescarga.getText());
			boolean etiquetasEsperadas = textoSeccionNormalizado.contains("fue")
					&& textoSeccionNormalizado.contains("verificacion")
					&& textoSeccionNormalizado.contains("tira");
			boolean result = textoVisible && iconosPdfVisibles >= 3
					&& (etiquetasEsperadas || (fueVisible && verificacionVisible && tiraVisible));
			if (!result) {
				testCase.addBoldTextEvidenceCurrentStep("Detalle validacion Descarga MASIVA -> textoVisible="
						+ textoVisible + " | iconosPdfVisibles=" + iconosPdfVisibles
						+ " | FUE=" + fueVisible + " | Verificacion=" + verificacionVisible
						+ " | Tira=" + tiraVisible + " | etiquetasEsperadas=" + etiquetasEsperadas
						+ " | textoSeccion='" + normalizarTextoDiagnostico(seccionDescarga.getText()) + "'");
			}
			return registrarConResaltado(result, "Descarga MASIVA muestra FUE, Verificacion y Tira",
					"Falta el texto o uno de los tres controles de descarga masiva", "Descargas_Masivas_Recepcion",
					elements.seccionDescargaMasiva, elements.iconosPdfDescargaMasiva);
		} catch (Exception exception) { return error("No fue posible validar descargas masivas: " + exception.getMessage()); }
	}

	/**
	 * Valida todas las columnas requeridas del grid ENTREGA.
	 *
	 * @return true si todas estan visibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaColumnasGridEntrega() {
		try {
			boolean result = u.isDisplayed(elements.columnaFecha) && u.isDisplayed(elements.columnaCrTienda)
					&& u.isDisplayed(elements.columnaNombreTienda) && u.isDisplayed(elements.columnaTransferencia)
					&& u.isDisplayed(elements.columnaCanastillas) && u.isDisplayed(elements.columnaBols)
					&& u.isDisplayed(elements.columnaFue) && u.isDisplayed(elements.columnaVerificacion)
					&& u.isDisplayed(elements.columnaEvidencias) && u.isDisplayed(elements.columnaTira);
			return registrarConResaltado(result, "El grid ENTREGA muestra todas las columnas esperadas",
					"El grid ENTREGA no muestra todas las columnas requeridas", "Columnas_Grid_ENTREGA",
					elements.areaGrid);
		} catch (Exception exception) { return error("No fue posible validar columnas del grid: " + exception.getMessage()); }
	}

	/**
	 * Selecciona la primera Plaza disponible.
	 *
	 * @return true cuando el filtro conserva una seleccion.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean seleccionaPrimeraPlaza() {
		try {
			esperarFinCarga();
			By backdropActivo = By.cssSelector(".cdk-overlay-backdrop.cdk-overlay-backdrop-showing");
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(25));
			Exception ultimoError = null;
			boolean plazaSeleccionada = false;

			for (int intento = 1; intento <= 3 && !plazaSeleccionada; intento++) {
				try {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(backdropActivo));
					WebElement plaza = wait.until(ExpectedConditions.elementToBeClickable(elements.filtroPlaza));
					((JavascriptExecutor) u.getDriver()).executeScript(
							"arguments[0].scrollIntoView({block:'center', inline:'center'});", plaza);
					u.hardWait(1);
					plaza.click();

					WebElement primeraOpcion = wait.until(ExpectedConditions.elementToBeClickable(elements.primeraOpcionPlaza));
					((JavascriptExecutor) u.getDriver()).executeScript(
							"arguments[0].scrollIntoView({block:'nearest', inline:'nearest'});", primeraOpcion);
					primeraOpcion.click();

					wait.until(ExpectedConditions.invisibilityOfElementLocated(backdropActivo));
					plazaSeleccionada = true;
				} catch (Exception exception) {
					ultimoError = exception;
					new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
					u.hardWait(1);
				}
			}

			if (!plazaSeleccionada && ultimoError != null) {
				return error("No fue posible seleccionar Plaza tras esperar el overlay y reintentar: "
						+ ultimoError.getMessage());
			}

			return registrar(plazaSeleccionada, "El filtro Plaza acepta la primera opcion disponible",
					"No fue posible seleccionar una Plaza", "Plaza_Seleccionada_Recepcion");
		} catch (Exception exception) { return error("No fue posible seleccionar Plaza: " + exception.getMessage()); }
	}

	/**
	 * Captura el rango de fechas indicado por la matriz.
	 *
	 * @param inicio fecha inicial dd/MM/yyyy.
	 * @param fin fecha final dd/MM/yyyy.
	 * @return true si ambos valores quedan capturados.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean capturaRangoFechas(String inicio, String fin) {
		try {
			boolean inicioCapturado = capturaFecha(elements.fechaInicio, inicio, "Inicio");
			boolean finCapturado = inicioCapturado && capturaFecha(elements.fechaFin, fin, "Fin");
			boolean result = inicioCapturado && finCapturado;
			return registrarConResaltado(result, "El rango de fechas queda configurado del " + inicio + " al " + fin,
					"El rango de fechas no conserva los valores esperados o no habilita Consultar",
					"Rango_Fechas_Recepcion",
					elements.fechaInicio, elements.fechaFin);
		} catch (Exception exception) { return error("No fue posible capturar el rango de fechas: " + exception.getMessage()); }
	}

	/**
	 * Ejecuta la consulta historica y valida registros en el grid.
	 *
	 * @return true cuando aparece al menos una fila.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean consultaHistoricaConResultados() {
		try {
			esperarFinCarga();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonConsultar = wait.until(ExpectedConditions.elementToBeClickable(
					elements.botonConsultarHistoricoCualquierEstado));
			botonConsultar.click();
			u.hardWait(1);
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(10)).until(driver -> {
				boolean hayRegistros = !driver.findElements(elements.filasGridEntregaRecepcion).isEmpty();
				boolean sinResultados = driver.findElements(elements.mensajeSinResultados).stream()
						.anyMatch(WebElement::isDisplayed);
				return hayRegistros || sinResultados;
			});
			boolean result = !u.getDriver().findElements(elements.filasGridEntregaRecepcion).isEmpty();
			boolean sinResultados = u.getDriver().findElements(elements.mensajeSinResultados).stream()
					.anyMatch(WebElement::isDisplayed);
			String motivo = sinResultados
					? "La consulta historica no mostro registros para los filtros seleccionados"
					: "La consulta historica no mostro registros en el grid";
			ultimoSnapshotGrid = snapshotGrid();
			return registrarConResaltado(result, "La consulta historica muestra registros en el grid ENTREGA",
					motivo, "Consulta_Historica_Recepcion",
					elements.areaGrid);
		} catch (Exception exception) { return error("No fue posible consultar filtros historicos: " + exception.getMessage()); }
	}

	/**
	 * Abre el primer FUE, intenta conservar una canastilla desde su contenido y cierra el modal.
	 * Preserva el contrato historico: el resultado depende de que el FUE sea visible.
	 *
	 * @return true cuando el FUE se muestra correctamente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 * Abre el primer FUE, conserva una canastilla visible para busqueda rapida y cierra el modal.
	 *
	 * @return true si el FUE se muestra correctamente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean abrePrimerFueYConservaCanastilla() {
		return abrePrimerFueYProcesaCanastilla(false);
	}

	/**
	 * Abre el primer FUE y exige obtener una canastilla escaneable antes de cerrar el modal.
	 * Esta variante se usa cuando el siguiente flujo depende funcionalmente de esa canastilla.
	 *
	 * @return true cuando el FUE es visible y contiene una canastilla valida.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	public boolean abrePrimerFueYObtieneCanastilla() {
		return abrePrimerFueYProcesaCanastilla(true);
	}

	/**
	 * Ejecuta la apertura del FUE y controla si la canastilla es obligatoria para el resultado.
	 *
	 * @param exigirCanastilla true cuando el paso debe fallar si no se obtiene una canastilla.
	 * @return true cuando el FUE cumple el contrato solicitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private boolean abrePrimerFueYProcesaCanastilla(boolean exigirCanastilla) {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(30))
					.until(ExpectedConditions.elementToBeClickable(elements.primerFue)).click();
			WebElement modal = new WebDriverWait(u.getDriver(), Duration.ofSeconds(40))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.modalFue));
			ultimaCanastilla = extraerCanastillaFue(modal);
			boolean result = modal.isDisplayed() && (!exigirCanastilla || ultimaCanastilla != null);
			registrarConResaltado(result,
					ultimaCanastilla == null
							? "El primer documento FUE se visualiza correctamente"
							: "El primer documento FUE se visualiza y contiene la canastilla " + ultimaCanastilla,
					"El FUE se abrio, pero no contiene una canastilla escaneable. Texto visible: "
							+ normalizarTextoDiagnostico(modal.getText()),
					"FUE_Individual_Recepcion", elements.modalFue);
			if (!u.getDriver().findElements(elements.cerrarModalFue).isEmpty()) {
				u.getDriver().findElement(elements.cerrarModalFue).click();
			}
			return result;
		} catch (Exception exception) { return error("No fue posible abrir el primer FUE: " + exception.getMessage()); }
	}

	/**
	 * Busca un identificador de canastilla dentro de controles, celdas y texto del FUE.
	 * Descarta etiquetas, totales y valores exclusivamente descriptivos.
	 *
	 * @param modal contenedor visible del FUE.
	 * @return canastilla encontrada o null cuando el FUE no expone un identificador valido.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private String extraerCanastillaFue(WebElement modal) {
		Pattern etiquetaCanastilla = Pattern.compile(
				"(?i)(?:numero|no\\.?|id|codigo)?\\s*(?:de\\s+)?canastilla\\s*[:#-]?\\s*([A-Z0-9-]{4,})");
		WebElement contenedorFue = modal.findElements(By.xpath("ancestor-or-self::mat-dialog-container"))
				.stream().findFirst().orElse(modal);
		List<WebElement> valoresAdyacentes = contenedorFue.findElements(By.xpath(
				".//*[self::th or self::td or self::label or self::span][contains(translate(normalize-space(.),'CANASTILLA','canastilla'),'canastilla')]"
				+ "/following-sibling::*[self::td or self::th or self::span or self::input][1]"));
		for (WebElement candidato : valoresAdyacentes) {
			String token = tokenCanastillaSinEtiqueta(candidato);
			if (token != null) {
				return token;
			}
		}
		for (WebElement tabla : contenedorFue.findElements(By.xpath(".//table"))) {
			List<WebElement> filas = tabla.findElements(By.xpath(".//tr"));
			if (filas.isEmpty()) {
				continue;
			}
			List<WebElement> encabezados = filas.get(0).findElements(By.xpath("./th | ./td"));
			int indiceCanastilla = -1;
			for (int indice = 0; indice < encabezados.size(); indice++) {
				if (normalizar(encabezados.get(indice).getText()).contains("canastilla")) {
					indiceCanastilla = indice;
					break;
				}
			}
			if (indiceCanastilla < 0) {
				continue;
			}
			for (int indiceFila = 1; indiceFila < filas.size(); indiceFila++) {
				List<WebElement> celdas = filas.get(indiceFila).findElements(By.xpath("./th | ./td"));
				if (celdas.size() > indiceCanastilla) {
					String token = tokenCanastillaSinEtiqueta(celdas.get(indiceCanastilla));
					if (token != null) {
						return token;
					}
				}
			}
		}
		List<WebElement> candidatos = contenedorFue.findElements(By.xpath(
				".//input | .//td | .//th | .//span | .//p | .//div[contains(@class,'canastilla')]"));
		for (WebElement candidato : candidatos) {
			String valor = candidato.getAttribute("value");
			String texto = valor == null || valor.trim().isEmpty() ? candidato.getText() : valor;
			String canastilla = extraerTokenCanastilla(texto, etiquetaCanastilla);
			if (canastilla != null) {
				return canastilla;
			}
		}
		return extraerTokenCanastilla(contenedorFue.getText(), etiquetaCanastilla);
	}

	/**
	 * Valida un valor adyacente a una etiqueta o encabezado Canastilla.
	 *
	 * @param candidato celda, control o texto asociado a la columna Canastilla.
	 * @return identificador escaneable o null cuando el valor es descriptivo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private String tokenCanastillaSinEtiqueta(WebElement candidato) {
		String valor = candidato.getAttribute("value");
		String texto = valor == null || valor.trim().isEmpty() ? candidato.getText() : valor;
		if (texto == null) {
			return null;
		}
		Matcher matcher = Pattern.compile("(?i)([A-Z0-9-]{4,})").matcher(texto.trim());
		while (matcher.find()) {
			String token = matcher.group(1);
			if (!token.equalsIgnoreCase("CANASTILLA") && !token.equalsIgnoreCase("DEVUELTAS")
					&& !token.equalsIgnoreCase("DEVOLUCION") && !token.equalsIgnoreCase("TOTAL")) {
				return token;
			}
		}
		return null;
	}

	/**
	 * Extrae y valida un token asociado a la etiqueta Canastilla.
	 *
	 * @param texto contenido candidato del FUE.
	 * @param patron patron que relaciona la etiqueta con su identificador.
	 * @return token normalizado o null cuando no es un identificador escaneable.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private String extraerTokenCanastilla(String texto, Pattern patron) {
		if (texto == null || texto.trim().isEmpty()) {
			return null;
		}
		Matcher matcher = patron.matcher(texto.replaceAll("\\s+", " ").trim());
		while (matcher.find()) {
			String token = matcher.group(1).trim();
			if (!token.equalsIgnoreCase("DEVUELTAS") && !token.equalsIgnoreCase("DEVOLUCION")
					&& !token.equalsIgnoreCase("TOTAL") && token.matches("[A-Za-z0-9-]{4,}")) {
				return token;
			}
		}
		return null;
	}

	/**
	 * Reduce el texto del FUE para registrar un diagnostico legible sin saturar la evidencia.
	 *
	 * @param texto contenido completo visible del FUE.
	 * @return texto normalizado con longitud maxima de 300 caracteres.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private String normalizarTextoDiagnostico(String texto) {
		String normalizado = texto == null ? "" : texto.replaceAll("\\s+", " ").trim();
		return normalizado.length() <= 300 ? normalizado : normalizado.substring(0, 300) + "...";
	}

	/**
	 * Inicia la descarga masiva FUE.
	 *
	 * @return true cuando el control puede accionarse.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean descargaMasivaFue() {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(30))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.seccionDescargaMasiva));
			List<WebElement> controlesVisibles = u.getDriver().findElements(elements.iconosPdfDescargaMasiva).stream()
					.filter(WebElement::isDisplayed).collect(java.util.stream.Collectors.toList());
			if (controlesVisibles.isEmpty()) {
				return registrar(false, "", "No se encontraron controles visibles para descarga masiva FUE",
						"Descarga_Masiva_FUE_No_Visible");
			}
			Exception ultimoErrorClick = null;
			for (WebElement control : controlesVisibles) {
				try {
					((JavascriptExecutor) u.getDriver()).executeScript(
							"arguments[0].scrollIntoView({block:'center', inline:'center'});", control);
					u.hardWait(1);
					new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
							.until(ExpectedConditions.elementToBeClickable(control));
					control.click();
					return registrar(true, "El sistema inicia la descarga masiva de FUEs sin errores",
							"No fue posible iniciar la descarga masiva de FUEs", "Descarga_Masiva_FUE");
				} catch (Exception clickException) {
					ultimoErrorClick = clickException;
				}
			}
			String detalleError = ultimoErrorClick == null ? "sin detalle"
					: normalizarTextoDiagnostico(ultimoErrorClick.getMessage());
			testCase.addBoldTextEvidenceCurrentStep(
					"No fue posible accionar ningun control visible de descarga masiva. Detalle: " + detalleError);
			return registrar(false, "", "No fue posible iniciar la descarga masiva de FUEs",
					"Descarga_Masiva_FUE_Fallida");
		} catch (Exception exception) { return error("No fue posible iniciar la descarga masiva FUE: " + exception.getMessage()); }
	}

	/**
	 * Abre Liquidacion de Ruta desde Recepcion y valida que la pantalla destino quede visible.
	 *
	 * @return true cuando la navegación termina en Liquidacion de Ruta sin error de acceso.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean abreLiquidacionRuta() {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.botonLiquidacionRuta)).click();
			boolean result = new WebDriverWait(u.getDriver(), Duration.ofSeconds(40))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.botonDevolucionesEspeciales)).isDisplayed();
			return registrarConResaltado(result,
					"El sistema navega a Liquidacion de Ruta sin requerir accesos adicionales",
					"Liquidacion de Ruta no cargó correctamente", "Liquidacion_Ruta_Accesible",
					elements.botonDevolucionesEspeciales);
		} catch (Exception exception) {
			return error("No fue posible abrir Liquidacion de Ruta: " + exception.getMessage());
		}
	}

	/**
	 * Limpia filtros historicos y captura la canastilla obtenida del FUE en busqueda rapida.
	 *
	 * @return true si el campo acepta el valor.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean limpiaFiltrosYCapturaCanastilla() {
		try {
			if (usuarioContextoActual != null && !usuarioContextoActual.trim().isEmpty()) {
				String canastillaBd = new CometaBdValidationHelper(testCase)
						.obtenerCanastillaValidaSegunPerfil(usuarioContextoActual);
				if (canastillaBd != null && !canastillaBd.trim().isEmpty()) {
					ultimaCanastilla = canastillaBd.trim();
					testCase.addBoldTextEvidenceCurrentStep(
							"Canastilla cargada desde BD para el usuario " + usuarioContextoActual + ": "
									+ ultimaCanastilla);
				}
			}
			if (ultimaCanastilla == null || ultimaCanastilla.trim().isEmpty()) {
				return registrar(false, "",
						"No se obtuvo una canastilla valida desde BD ni desde el FUE abierto en el paso anterior",
						"Canastilla_No_Disponible");
			}
			esperarFinCarga();
			By backdropActivo = By.cssSelector(".cdk-overlay-backdrop.cdk-overlay-backdrop-showing");
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(25));
			Exception ultimoErrorClick = null;
			boolean limpioHistorico = false;

			for (int intento = 1; intento <= 3 && !limpioHistorico; intento++) {
				try {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(backdropActivo));
					WebElement botonLimpiar = wait.until(ExpectedConditions.elementToBeClickable(elements.botonLimpiarHistorico));
					((JavascriptExecutor) u.getDriver()).executeScript(
							"arguments[0].scrollIntoView({block:'center', inline:'center'});", botonLimpiar);
					u.hardWait(1);
					botonLimpiar.click();
					esperarFinCarga();
					wait.until(driver -> valorCampo(elements.fechaInicio).isEmpty() && valorCampo(elements.fechaFin).isEmpty());
					limpioHistorico = true;
				} catch (Exception clickException) {
					ultimoErrorClick = clickException;
					new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
					u.hardWait(1);
				}
			}

			if (!limpioHistorico) {
				String detalle = ultimoErrorClick == null ? "sin detalle" : normalizarTextoDiagnostico(ultimoErrorClick.getMessage());
				return error("No fue posible limpiar filtros historicos para habilitar Busqueda Rapida: " + detalle);
			}

			WebElement input = wait.until(ExpectedConditions.elementToBeClickable(elements.inputCanastilla));
			input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			input.sendKeys(Keys.DELETE);
			input.sendKeys(ultimaCanastilla);
			WebElement botonConsultarRapido = wait.until(ExpectedConditions.visibilityOfElementLocated(
					elements.botonConsultarRapidoCualquierEstado));
			boolean capturaCorrecta = ultimaCanastilla.equals(input.getAttribute("value"));
			boolean rapidaHabilitada = !elementoDeshabilitado(botonConsultarRapido);
			boolean resultadoPaso = capturaCorrecta && rapidaHabilitada;
			return registrarConResaltado(resultadoPaso,
					"Los filtros historicos se limpian y Busqueda Rapida acepta la canastilla " + ultimaCanastilla,
					"Busqueda Rapida no quedo habilitada o no conserva la canastilla. Captura=" + capturaCorrecta
							+ " | Consultar habilitado=" + rapidaHabilitada,
					"Canastilla_Busqueda_Rapida",
					elements.inputCanastilla);
		} catch (Exception exception) { return error("No fue posible preparar la busqueda rapida: " + exception.getMessage()); }
	}

	/**
	 * Ejecuta busqueda rapida y valida resultados.
	 *
	 * @return true cuando el grid muestra registros.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean consultaRapidaConResultados() {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.botonConsultarRapido)).click();
			boolean result = new WebDriverWait(u.getDriver(), Duration.ofSeconds(70))
					.until(ExpectedConditions.numberOfElementsToBeMoreThan(elements.gridFilas, 0)).size() > 0;
			return registrarConResaltado(result, "La busqueda rapida muestra resultados coherentes en el grid",
					"La busqueda rapida no mostro resultados", "Consulta_Rapida_Recepcion", elements.areaGrid);
		} catch (Exception exception) { return error("No fue posible ejecutar la busqueda rapida: " + exception.getMessage()); }
	}

	/**
	 * Regresa a Recepcion y abre Devoluciones Especiales.
	 *
	 * @return true si la pantalla destino carga sin error de acceso.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean regresaYAbreDevolucionesEspeciales() {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(40));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonDevolucionesEspeciales));
			WebElement flechaRegresar = null;
			if (u.getDriver().findElements(elements.flechaRegresarHeaderCometaBoton).stream().anyMatch(WebElement::isDisplayed)) {
				flechaRegresar = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.flechaRegresarHeaderCometaBoton));
			} else if (u.getDriver().findElements(elements.flechaRegresarHeaderCometa).stream().anyMatch(WebElement::isDisplayed)) {
				flechaRegresar = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.flechaRegresarHeaderCometa));
			} else if (u.getDriver().findElements(elements.flechaRegresarLiquidacionRuta).stream().anyMatch(WebElement::isDisplayed)) {
				flechaRegresar = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.flechaRegresarLiquidacionRuta));
			}
			if (flechaRegresar == null) {
				throw new RuntimeException("No se encontro la flecha de regreso en Header ni en la pantalla de Liquidacion de Ruta.");
			}
			((JavascriptExecutor) u.getDriver()).executeScript(
					"arguments[0].scrollIntoView({block:'center', inline:'center'});", flechaRegresar);
			u.hardWait(1);
			try {
				wait.until(ExpectedConditions.elementToBeClickable(flechaRegresar)).click();
			} catch (Exception clickFlechaFallido) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", flechaRegresar);
			}
			esperarFinCarga();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.tituloPantalla));
			try {
				new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
						.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayEspereUnMomento));
			} catch (Exception overlayPersistente) {
				testCase.addBoldTextEvidenceCurrentStep(
						"Overlay de carga aun visible antes de clicar Devoluciones Especiales; se intenta click resiliente.");
			}
			WebElement botonDevoluciones = new WebDriverWait(u.getDriver(), Duration.ofSeconds(25))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.botonDevolucionesEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript(
					"arguments[0].scrollIntoView({block:'center', inline:'center'});", botonDevoluciones);
			u.hardWait(1);
			Exception ultimoErrorClick = null;
			for (int intento = 1; intento <= 3; intento++) {
				try {
					new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
							.until(ExpectedConditions.elementToBeClickable(botonDevoluciones));
					botonDevoluciones.click();
					ultimoErrorClick = null;
					break;
				} catch (Exception clickInterceptado) {
					ultimoErrorClick = clickInterceptado;
					if (intento == 1) {
						try {
							((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", botonDevoluciones);
							ultimoErrorClick = null;
							break;
						} catch (Exception clickJsFallido) {
							ultimoErrorClick = clickJsFallido;
						}
					}
					esperarFinCarga();
					botonDevoluciones = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15))
							.until(ExpectedConditions.visibilityOfElementLocated(elements.botonDevolucionesEspeciales));
					((JavascriptExecutor) u.getDriver()).executeScript(
							"arguments[0].scrollIntoView({block:'center', inline:'center'});", botonDevoluciones);
					u.hardWait(1);
				}
			}
			if (ultimoErrorClick != null) {
				throw ultimoErrorClick;
			}
			esperarFinCarga();
			boolean result = new WebDriverWait(u.getDriver(), Duration.ofSeconds(40)).until(driver -> {
				boolean tituloVisible = driver.findElements(elements.tituloDevolucionesEspeciales).stream()
						.anyMatch(WebElement::isDisplayed);
				boolean filtroCedisVisible = driver.findElements(elements.filtroCedisDevolucionesEspeciales).stream()
						.anyMatch(WebElement::isDisplayed);
				String urlActual = driver.getCurrentUrl().toLowerCase();
				boolean urlCorrecta = urlActual.contains("devoluciones-especiales");
				return tituloVisible || filtroCedisVisible || urlCorrecta;
			});
			return registrarConResaltado(result, "Devoluciones Especiales sigue accesible sin permisos adicionales",
					"No fue posible abrir Devoluciones Especiales", "Devoluciones_Especiales_Accesible",
					elements.tituloDevolucionesEspeciales, elements.filtroCedisDevolucionesEspeciales,
					elements.botonDevolucionesEspeciales);
		} catch (Exception exception) { return error("No fue posible navegar a Devoluciones Especiales: " + exception.getMessage()); }
	}

	/**
	 * Registra el resultado de una validacion funcional en consola y evidencia del paso actual.
	 * Agrega una captura con el nombre indicado para respaldar el resultado observado.
	 *
	 * @param result resultado booleano de la validacion ejecutada.
	 * @param success mensaje que se registra cuando la validacion es exitosa.
	 * @param failure mensaje que se registra cuando la validacion falla.
	 * @param screenshot nombre de la captura asociada a la evidencia del paso.
	 * @return el resultado recibido para permitir su uso directo en aserciones o retornos.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	private boolean registrar(boolean result, String success, String failure, String screenshot) {
		String estadoPaso = result ? "PASSED" : "FAILED";
		String detalleResultado = result ? success : "Motivo: " + failure;
		System.out.println("[RESULTADO PASO] [" + estadoPaso + "] - " + detalleResultado);
		testCase.addBoldTextEvidenceCurrentStep(
				"Resultado paso: [" + estadoPaso + "] - " + detalleResultado);
		testCase.addScreenShotCurrentStep(screenshot);
		return result;
	}

	/**
	 * Registra un error controlado de las acciones de Recepcion de Transferencias y Devoluciones.
	 * Deja evidencia del mensaje y una captura de error para facilitar el diagnostico del fallo.
	 *
	 * @param message descripcion del error capturado durante la ejecucion.
	 * @return false para indicar que la validacion o accion no fue exitosa.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	private boolean error(String message) {
		System.out.println("[RESULTADO PASO] [FAILED] - Motivo: " + message);
		testCase.addBoldTextEvidenceCurrentStep(
				"Resultado paso: [FAILED] - Motivo: " + message);
		try { testCase.addScreenShotCurrentStep("Error_Recepcion_CEDIS"); } catch (Exception ignored) { }
		return false;
	}

	/**
	 * Valida que la seccion Filtros de Busqueda Rapida este visible.
	 *
	 * @return true cuando la seccion se encuentra visible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaSeccionFiltrosRapidosVisible() {
		try {
			return registrarConResaltado(u.isDisplayed(elements.seccionFiltrosRapidos),
					"La seccion de filtros rapidos es visible",
					"La seccion de filtros rapidos no se encuentra visible",
					"Seccion_Filtros_Rapidos", elements.tituloFiltrosRapidos);
		} catch (Exception exception) {
			return error("No fue posible validar la seccion de filtros rapidos: " + exception.getMessage());
		}
	}

	/**
	 * Valida la presencia y visibilidad del campo Transferencia dentro de filtros rapidos.
	 *
	 * @return true cuando el campo Transferencia esta presente y visible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaCampoTransferenciaVisible() {
		try {
			return registrarConResaltado(u.isDisplayed(elements.inputTransferencia),
					"El campo Transferencia esta presente y visible en filtros rapidos",
					"El campo Transferencia no esta presente o visible en filtros rapidos",
					"Campo_Transferencia_Visible", elements.campoTransferencia);
		} catch (Exception exception) {
			return error("No fue posible validar el campo Transferencia: " + exception.getMessage());
		}
	}

	/**
	 * Valida que Transferencia y Canastilla esten contiguos en la misma linea visual.
	 *
	 * @return true cuando ambos campos comparten la misma linea y no se superponen.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaCamposRapidosContiguos() {
		try {
			WebElement canastilla = u.getDriver().findElement(elements.inputCanastilla);
			WebElement transferencia = u.getDriver().findElement(elements.inputTransferencia);
			int centroYCanastilla = canastilla.getLocation().getY() + canastilla.getSize().getHeight() / 2;
			int centroYTransferencia = transferencia.getLocation().getY() + transferencia.getSize().getHeight() / 2;
			boolean mismaLinea = Math.abs(centroYCanastilla - centroYTransferencia)
					<= Math.max(canastilla.getSize().getHeight(), transferencia.getSize().getHeight());
			boolean elementosDistintos = !canastilla.equals(transferencia)
					&& canastilla.getLocation().getX() != transferencia.getLocation().getX();
			return registrarConResaltado(mismaLinea && elementosDistintos,
					"Transferencia se encuentra a un lado de Escanea o teclea la canastilla",
					"Transferencia y Canastilla no estan contiguos en la misma linea visual",
					"Campos_Rapidos_Contiguos", elements.campoCanastilla, elements.campoTransferencia);
		} catch (Exception exception) {
			return error("No fue posible validar la ubicacion de los campos rapidos: " + exception.getMessage());
		}
	}

	/**
	 * Valida que el campo nuevo muestre exactamente la etiqueta Transferencia.
	 *
	 * @return true cuando la etiqueta es visible y esta correctamente escrita.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaEtiquetaTransferencia() {
		try {
			WebElement etiqueta = u.getDriver().findElement(elements.etiquetaTransferencia);
			boolean resultadoPaso = etiqueta.isDisplayed()
					&& "Transferencia".equals(etiqueta.getText().trim());
			return registrarConResaltado(resultadoPaso,
					"El campo muestra la etiqueta Transferencia de forma legible y correcta",
					"La etiqueta del campo no coincide exactamente con Transferencia. Actual: " + etiqueta.getText(),
					"Etiqueta_Transferencia", elements.etiquetaTransferencia);
		} catch (Exception exception) {
			return error("No fue posible validar la etiqueta Transferencia: " + exception.getMessage());
		}
	}

	/**
	 * Posiciona el foco en el campo Transferencia y valida el elemento activo.
	 *
	 * @return true cuando el campo recibe el foco.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean enfocaCampoTransferencia() {
		try {
			WebElement input = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.inputTransferencia));
			input.click();
			return registrarConResaltado(input.equals(u.getDriver().switchTo().activeElement()),
					"El campo Transferencia recibe el foco",
					"El foco no permanece en el campo Transferencia",
					"Foco_Campo_Transferencia", elements.inputTransferencia);
		} catch (Exception exception) {
			return error("No fue posible enfocar el campo Transferencia: " + exception.getMessage());
		}
	}

	/**
	 * Captura un valor en Transferencia y valida que se conserve exactamente.
	 *
	 * @param valor valor alfanumerico que se debe capturar.
	 * @return true cuando el campo conserva exactamente el valor recibido.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean capturaYValidaTransferencia(String valor) {
		try {
			WebElement input = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.inputTransferencia));
			input.sendKeys(Keys.chord(Keys.CONTROL, "a"), valor);
			String valorActual = input.getAttribute("value");
			return registrarConResaltado(valor.equals(valorActual),
					"El campo Transferencia acepta y muestra el valor " + valor,
					"El campo Transferencia no conserva el valor esperado. Esperado: " + valor
							+ " | Actual: " + valorActual,
					"Transferencia_" + valor, elements.inputTransferencia);
		} catch (Exception exception) {
			return error("No fue posible capturar Transferencia: " + exception.getMessage());
		}
	}

	/**
	 * Acciona Limpiar filtros de la seccion rapida y espera que ambos campos queden vacios.
	 *
	 * @return true cuando Transferencia y Canastilla quedan vacios.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean limpiaFiltrosRapidos() {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.botonLimpiarRapido)).click();
			boolean resultadoPaso = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20)).until(driver ->
					valorCampo(elements.inputTransferencia).isEmpty()
							&& valorCampo(elements.inputCanastilla).isEmpty()
							&& !elementoDeshabilitado(driver.findElement(elements.inputTransferencia))
							&& !elementoDeshabilitado(driver.findElement(elements.inputCanastilla)));
			return registrarConResaltado(resultadoPaso,
					"La accion Limpiar filtros rapidos restaura y habilita ambos campos",
					"Los campos rapidos no quedaron vacios y habilitados despues de limpiar",
					"Limpiar_Filtros_Rapidos", elements.inputCanastilla, elements.inputTransferencia);
		} catch (Exception exception) {
			return error("No fue posible limpiar los filtros rapidos: " + exception.getMessage());
		}
	}

	/**
	 * Valida que el campo Transferencia se encuentre vacio.
	 *
	 * @return true cuando el valor del campo es vacio.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaTransferenciaVacia() {
		try {
			String valorActual = valorCampo(elements.inputTransferencia);
			return registrarConResaltado(valorActual.isEmpty(),
					"El campo Transferencia se encuentra completamente vacio",
					"El campo Transferencia conserva el valor: " + valorActual,
					"Transferencia_Vacia", elements.inputTransferencia);
		} catch (Exception exception) {
			return error("No fue posible validar que Transferencia este vacia: " + exception.getMessage());
		}
	}

	/**
	 * Valida que el campo Canastilla se encuentre vacio.
	 *
	 * @return true cuando el valor del campo es vacio.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaCanastillaVacia() {
		try {
			String valorActual = valorCampo(elements.inputCanastilla);
			return registrarConResaltado(valorActual.isEmpty(),
					"El campo Escanea o teclea la canastilla queda vacio",
					"El campo Canastilla conserva el valor: " + valorActual,
					"Canastilla_Vacia", elements.inputCanastilla);
		} catch (Exception exception) {
			return error("No fue posible validar que Canastilla este vacia: " + exception.getMessage());
		}
	}

	/**
	 * Valida que no aparezcan errores de formato para el valor alfanumerico capturado.
	 *
	 * @return true cuando no existen mensajes de error visibles asociados a Transferencia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaSinErroresTransferencia() {
		try {
			boolean resultadoPaso = u.getDriver().findElements(elements.erroresTransferencia).stream()
					.noneMatch(WebElement::isDisplayed);
			return registrarConResaltado(resultadoPaso,
					"No se muestran errores ni caracteres bloqueados para valores alfanumericos",
					"Se muestra un error de validacion para un valor alfanumerico permitido",
					"Transferencia_Sin_Errores", elements.inputTransferencia);
		} catch (Exception exception) {
			return error("No fue posible validar errores del campo Transferencia: " + exception.getMessage());
		}
	}

	/**
	 * Consulta el rango historico, obtiene una transferencia valida y restablece los filtros.
	 *
	 * @param fechaInicio fecha inicial en formato dd/MM/yyyy.
	 * @param fechaFin fecha final en formato dd/MM/yyyy.
	 * @return true cuando se obtiene y documenta al menos una transferencia valida.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean descubreTransferenciaValida(String fechaInicio, String fechaFin) {
		try {
			if (!capturaRangoFechas(fechaInicio, fechaFin) || !consultaHistoricaConResultados()) {
				return registrar(false, "", "No fue posible consultar transferencias historicas",
						"Transferencia_Valida_No_Disponible");
			}
			Set<String> transferencias = transferenciasVisibles();
			transferenciaA = transferencias.isEmpty() ? null : transferencias.iterator().next();
			boolean resultadoPaso = transferenciaA != null && !transferenciaA.isEmpty();
			boolean registrado = registrar(resultadoPaso,
					"Numero de transferencia valido documentado: " + transferenciaA,
					"El grid historico no contiene una transferencia valida",
					"Transferencia_Valida_Documentada");
			if (registrado) {
				u.getDriver().findElement(elements.botonLimpiarHistorico).click();
			}
			return registrado;
		} catch (Exception exception) {
			return error("No fue posible descubrir una transferencia valida: " + exception.getMessage());
		}
	}

	private boolean ultimoModalFueVisible;
	private boolean ultimoModalCubrePantalla;
	private String resumenFiltroPlaza = "No validado";
	private String resumenFiltroTienda = "No validado";
	private String resumenSelectorRuta = "No validado";
	private String resumenInputRuta = "No validado";
	private String resumenGridRecepcion = "No validado";

	/**
	 * Ingresa a Recepcion y Devolucion desde el modulo CEDIS y valida la carga inicial de la pantalla.
	 * Registra T1 y T2, acepta carga inmediata sin modal perceptible, exige que el modal bloquee pantalla si aparece y valida filtros sin No items found.
	 * Usa el usuario CEDIS 6666666 cuando el script no envia usuario explicito.
	 *
	 * @param tiempoMaximoSegundos tiempo maximo permitido para que la pantalla quede disponible.
	 * @return true cuando Recepcion queda disponible dentro del tiempo maximo y los filtros cargan correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	public boolean ingresarRecepcionYValidarCargaInicial(int tiempoMaximoSegundos) {
		return ingresarRecepcionYValidarCargaInicial(tiempoMaximoSegundos, "6666666");
	}

	/**
	 * Ingresa a Recepcion y Devolucion desde el modulo CEDIS y valida la carga inicial requerida para filtros Plaza y Tienda.
	 * No interactua con Selector de Ruta ni con el campo Ruta, porque el flujo de Plaza/Tienda no requiere esos controles.
	 *
	 * @param tiempoMaximoSegundos tiempo maximo permitido para que la pantalla quede disponible.
	 * @return true cuando Recepcion queda disponible y los filtros Plaza/Tienda cargan sin No items found.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	public boolean ingresarRecepcionYValidarCargaInicialPlazaTienda(int tiempoMaximoSegundos) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime t1 = LocalDateTime.now();
			String textoT1 = t1.format(formatter);
			long inicioCarga = System.currentTimeMillis();
			testCase.addBoldTextEvidenceCurrentStep("T1 inicio de carga: " + textoT1);

			new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.tarjetaRecepcionYDevolucion)).click();

			boolean modalValido = validarModalInicialOpcional(inicioCarga);
			boolean pantallaDisponible = esperarPantallaDisponiblePlazaTienda(inicioCarga, tiempoMaximoSegundos);
			LocalDateTime t2 = LocalDateTime.now();
			String textoT2 = t2.format(formatter);
			double tiempoCarga = (System.currentTimeMillis() - inicioCarga) / 1000.0;
			String textoTiempoCarga = String.format("%.2f", tiempoCarga);
			testCase.addBoldTextEvidenceCurrentStep("T2 fin de carga: " + textoT2 + " | T_carga: " + textoTiempoCarga + " segundos");

			if (!modalValido) {
				return registrar(false, "", "El modal Espere un momento fue visible, pero no cubrio la pantalla completa durante la carga", "Overlay_Espere_Un_Momento_No_Cubre_Pantalla");
			}

			if (!pantallaDisponible || tiempoCarga > tiempoMaximoSegundos) {
				return registrar(false, "", "Recepcion no quedo disponible dentro de " + tiempoMaximoSegundos + " segundos. T_carga: " + textoTiempoCarga, "Carga_Inicial_Recepcion_Fuera_Tiempo");
			}

			boolean plaza = abrirNgSelectConOpciones(elements.filtroPlaza, "Plaza", inicioCarga);
			boolean tienda = abrirNgSelectConOpciones(elements.filtroTienda, "Tienda", inicioCarga);
			boolean resultado = plaza && tienda;
			String detalleModal = ultimoModalFueVisible ? "El modal Espere un momento fue visible durante la carga y cubrio la pantalla" : "La carga fue inmediata y el modal no fue perceptible";
			return registrar(resultado, detalleModal + "; Recepcion quedo disponible en " + textoTiempoCarga + " segundos y Plaza/Tienda cargaron correctamente",
					"Recepcion cargo dentro del tiempo, pero Plaza o Tienda no mostraron opciones validas", "Carga_Inicial_Recepcion_Plaza_Tienda");
		} catch (Exception exception) {
			return error("No fue posible validar la carga inicial de Recepcion para Plaza/Tienda: " + exception.getMessage());
		}
	}

	/**
	 * Captura en el filtro rapido la transferencia descubierta previamente.
	 *
	 * @return true cuando el campo conserva la Transferencia A.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean capturaTransferenciaA() {
		try {
			if (transferenciaA == null || transferenciaA.isEmpty()) {
				return registrar(false, "", "No existe una Transferencia A documentada",
						"Transferencia_A_No_Disponible");
			}
			return capturaYValidaTransferencia(transferenciaA);
		} catch (Exception exception) {
			return error("No fue posible capturar la Transferencia A: " + exception.getMessage());
		}
	}

	/**
	 * Ejecuta la consulta rapida por Transferencia A y conserva un snapshot del grid.
	 *
	 * @return true cuando la consulta muestra al menos una fila.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean consultaTransferenciaConResultados() {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.botonConsultarRapido)).click();
			esperarFinCarga();
			List<WebElement> filas = new WebDriverWait(u.getDriver(), Duration.ofSeconds(70))
					.until(ExpectedConditions.numberOfElementsToBeMoreThan(elements.gridFilas, 0));
			ultimoSnapshotGrid = snapshotGrid();
			return registrarConResaltado(!filas.isEmpty(),
					"El sistema ejecuta la consulta por transferencia y muestra resultados",
					"La consulta por transferencia no mostro registros",
					"Consulta_Por_Transferencia", elements.areaGrid);
		} catch (Exception exception) {
			return error("No fue posible consultar por Transferencia: " + exception.getMessage());
		}
	}

	/**
	 * Valida que el grid contenga al menos un registro.
	 *
	 * @return true cuando existe al menos una fila visible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaGridConRegistros() {
		try {
			int filas = u.getDriver().findElements(elements.gridFilas).size();
			return registrarConResaltado(filas > 0,
					"El grid contiene " + filas + " registros de la transferencia consultada",
					"El grid no contiene registros",
					"Grid_Con_Registros", elements.areaGrid);
		} catch (Exception exception) {
			return error("No fue posible validar los registros del grid: " + exception.getMessage());
		}
	}

	/**
	 * Valida que todas las celdas visibles de Transferencia coincidan con Transferencia A.
	 *
	 * @return true cuando el cien por ciento de los registros corresponde a Transferencia A.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaGridSoloTransferenciaA() {
		try {
			return validaGridSoloTransferencia(transferenciaA, "A");
		} catch (Exception exception) {
			return error("No fue posible validar el grid para Transferencia A: " + exception.getMessage());
		}
	}

	/**
	 * Captura un valor alfanumerico inexistente para preparar la consulta negativa.
	 *
	 * @return true cuando el campo conserva el valor inexistente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean capturaTransferenciaInexistente() {
		try {
			return capturaYValidaTransferencia("0000000000");
		} catch (Exception exception) {
			return error("No fue posible capturar la transferencia inexistente: " + exception.getMessage());
		}
	}

	/**
	 * Ejecuta una consulta negativa por transferencia y espera el mensaje sin resultados.
	 *
	 * @return true cuando la consulta termina sin filas y muestra el mensaje esperado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean consultaTransferenciaSinResultados() {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.botonConsultarRapido)).click();
			esperarFinCarga();
			WebElement mensaje = new WebDriverWait(u.getDriver(), Duration.ofSeconds(70))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.mensajeSinResultados));
			boolean resultadoPaso = mensaje.isDisplayed()
					&& u.getDriver().findElements(elements.gridFilas).isEmpty();
			return registrarConResaltado(resultadoPaso,
					"La consulta se ejecuta sin registros y muestra el aviso correspondiente",
					"La consulta negativa mostro filas o no presento el aviso esperado",
					"Consulta_Transferencia_Sin_Resultados", elements.mensajeSinResultados);
		} catch (Exception exception) {
			return error("No fue posible ejecutar la consulta sin resultados: " + exception.getMessage());
		}
	}

	/**
	 * Valida el texto exacto definido para una consulta sin resultados.
	 *
	 * @return true cuando el mensaje coincide exactamente, ignorando solo acentos de despliegue.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaMensajeSinResultadosExacto() {
		try {
			String actual = u.getDriver().findElement(elements.mensajeSinResultados).getText().trim();
			String esperado = "No se encontro informacion con el filtro seleccionado.";
			boolean resultadoPaso = normalizar(actual).equals(normalizar(esperado));
			return registrarConResaltado(resultadoPaso,
					"El mensaje es exactamente: No se encontro informacion con el filtro seleccionado.",
					"El mensaje no coincide. Esperado: " + esperado + " | Actual: " + actual,
					"Mensaje_Sin_Resultados_Exacto", elements.mensajeSinResultados);
		} catch (Exception exception) {
			return error("No fue posible validar el mensaje sin resultados: " + exception.getMessage());
		}
	}

	/**
	 * Obtiene el valor actual de un campo de texto.
	 *
	 * @param locator localizador del campo.
	 * @return valor sin espacios o cadena vacia cuando no tiene contenido.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private String valorCampo(org.openqa.selenium.By locator) {
		String valor = u.getDriver().findElement(locator).getAttribute("value");
		return valor == null ? "" : valor.trim();
	}

	/**
	 * Obtiene las transferencias distintas visibles en la columna del grid.
	 *
	 * @return conjunto ordenado de transferencias no vacias.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private Set<String> transferenciasVisibles() {
		Set<String> transferencias = new LinkedHashSet<String>();
		for (WebElement celda : u.getDriver().findElements(elements.celdasTransferenciaGrid)) {
			String valor = celda.getText().trim();
			if (!valor.isEmpty()) {
				transferencias.add(valor);
			}
		}
		return transferencias;
	}

	/**
	 * Valida todas las celdas de Transferencia contra el valor esperado.
	 *
	 * @param transferenciaEsperada numero de transferencia esperado.
	 * @param identificador identificador A o B para la evidencia.
	 * @return true cuando existen celdas y todas coinciden exactamente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private boolean validaGridSoloTransferencia(String transferenciaEsperada, String identificador) {
		GridCompletoSnapshot snapshot = recopilarGridCompleto("Transferencia_" + identificador);
		boolean totalCoherente = snapshot.totalReportado == snapshot.transferencias.size();
		boolean resultadoPaso = transferenciaEsperada != null && !snapshot.transferencias.isEmpty()
				&& totalCoherente && snapshot.transferencias.stream()
						.allMatch(transferenciaEsperada::equals);
		return registrarConResaltado(resultadoPaso,
				"El 100% de " + snapshot.transferencias.size() + " registros y " + snapshot.paginas
						+ " paginas pertenece a la Transferencia " + identificador + ": "
						+ transferenciaEsperada,
				"El grid contiene transferencias distintas o no se proceso el total. Esperada: "
						+ transferenciaEsperada + " | Actuales: " + snapshot.transferencias
						+ " | Procesadas: " + snapshot.transferencias.size()
						+ " | Total paginador: " + snapshot.totalReportado,
				"Grid_Completo_Solo_Transferencia_" + identificador,
				elements.celdasTransferenciaGrid, elements.contadorGrid);
	}

	/**
	 * Captura el texto completo de cada fila visible para comparar que el grid no cambie.
	 *
	 * @return lista con el texto normalizado de las filas visibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private List<String> snapshotGrid() {
		List<String> snapshot = new ArrayList<String>();
		for (WebElement fila : u.getDriver().findElements(elements.gridFilas)) {
			snapshot.add(fila.getText().replaceAll("\\s+", " ").trim());
		}
		return snapshot;
	}

	/**
	 * Espera que los indicadores de carga de la pantalla desaparezcan.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private void esperarFinCarga() {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(70))
					.until(ExpectedConditions.invisibilityOfElementLocated(elements.indicadorCarga));
		} catch (Exception ignored) {
			System.out.println("[WARN] No se detecto un indicador de carga visible antes de capturar evidencia.");
		}
	}

	/**
	 * Normaliza acentos y mayusculas para comparar mensajes funcionalmente equivalentes.
	 *
	 * @param texto texto que se debe normalizar.
	 * @return texto sin acentos, en minusculas y sin espacios laterales.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private String normalizar(String texto) {
		return java.text.Normalizer.normalize(texto == null ? "" : texto, java.text.Normalizer.Form.NFD)
				.replaceAll("\\p{M}", "").toLowerCase().trim();
	}

	/**
	 * Descubre una transferencia valida mediante el historico y la captura en filtros rapidos.
	 *
	 * @param fechaInicio fecha inicial en formato dd/MM/yyyy.
	 * @param fechaFin fecha final en formato dd/MM/yyyy.
	 * @return true cuando el filtro rapido conserva la transferencia descubierta.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean descubreYCapturaTransferenciaValida(String fechaInicio, String fechaFin) {
		try {
			boolean descubierta = descubreTransferenciaValida(fechaInicio, fechaFin);
			boolean capturada = descubierta && capturaTransferenciaA();
			return registrarConResaltado(capturada,
					"El campo Transferencia muestra el numero valido " + transferenciaA,
					"No fue posible descubrir y capturar una transferencia valida",
					"Transferencia_Valida_Capturada", elements.inputTransferencia);
		} catch (Exception exception) {
			return error("No fue posible preparar la transferencia valida: " + exception.getMessage());
		}
	}

	/**
	 * Valida el estado visual deshabilitado de los controles historicos.
	 *
	 * @return true cuando la seccion o sus controles editables estan deshabilitados.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaFiltrosHistoricosDeshabilitados() {
		try {
			WebElement plaza = u.getDriver().findElement(elements.filtroPlaza);
			WebElement tienda = u.getDriver().findElement(elements.filtroTienda);
			WebElement selectorRuta = u.getDriver().findElement(elements.selectorRuta);
			WebElement ruta = u.getDriver().findElement(elements.campoRuta);
			WebElement rangoFechas = u.getDriver().findElement(elements.campoRangoFechas);
			boolean plazaDeshabilitada = controlAngularDeshabilitado(plaza);
			boolean tiendaDeshabilitada = controlAngularDeshabilitado(tienda);
			boolean selectorRutaDeshabilitado = controlAngularDeshabilitado(selectorRuta);
			boolean rutaDeshabilitada = controlAngularDeshabilitado(ruta);
			boolean rangoFechasDeshabilitado = controlAngularDeshabilitado(rangoFechas);
			boolean resultadoPaso = plazaDeshabilitada && tiendaDeshabilitada
					&& selectorRutaDeshabilitado && rutaDeshabilitada && rangoFechasDeshabilitado;
			String detalleEstados = "Plaza=" + plazaDeshabilitada + " | Tienda=" + tiendaDeshabilitada
					+ " | Selector Ruta=" + selectorRutaDeshabilitado + " | Ruta=" + rutaDeshabilitada
					+ " | Rango Fechas=" + rangoFechasDeshabilitado;
			return registrarConResaltado(resultadoPaso,
					"Los filtros historicos aparecen deshabilitados. " + detalleEstados,
					"Uno o mas filtros historicos permanecen activos. " + detalleEstados,
					"Filtros_Historicos_Deshabilitados", elements.filtroPlaza, elements.filtroTienda,
					elements.selectorRuta, elements.campoRuta, elements.campoRangoFechas);
		} catch (Exception exception) {
			return error("No fue posible validar el bloqueo de filtros historicos: " + exception.getMessage());
		}
	}

	/**
	 * Valida que Consultar y Limpiar filtros historicos esten deshabilitados.
	 *
	 * @return true cuando ambos botones presentan estado deshabilitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaBotonesHistoricosDeshabilitados() {
		try {
			WebElement consultar = u.getDriver().findElement(elements.botonConsultarHistoricoCualquierEstado);
			WebElement limpiar = u.getDriver().findElement(elements.botonLimpiarHistorico);
			boolean resultadoPaso = elementoDeshabilitado(consultar) && elementoDeshabilitado(limpiar);
			return registrarConResaltado(resultadoPaso,
					"Consultar y Limpiar filtros historicos aparecen deshabilitados",
					"Uno o ambos botones historicos permanecen habilitados",
					"Botones_Historicos_Deshabilitados", elements.botonConsultarHistoricoCualquierEstado,
					elements.botonLimpiarHistorico);
		} catch (Exception exception) {
			return error("No fue posible validar los botones historicos: " + exception.getMessage());
		}
	}

	/**
	 * Intenta accionar Consultar historico bloqueado y valida el tooltip y la permanencia del grid.
	 *
	 * @return true cuando aparece el tooltip exacto y el grid no cambia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean intentaConsultaHistoricaBloqueadaYValidaTooltip() {
		try {
			List<String> snapshotAnterior = snapshotGrid();
			WebElement boton = u.getDriver().findElement(elements.botonConsultarHistoricoCualquierEstado);
			new Actions(u.getDriver()).moveToElement(boton).click().perform();
			WebElement tooltip = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.tooltipBloqueo));
			String esperado = "Para usar los filtros historicos, limpia primero los filtros rapidos.";
			boolean resultadoPaso = normalizar(tooltip.getText()).equals(normalizar(esperado))
					&& snapshotAnterior.equals(snapshotGrid());
			return registrarConResaltado(resultadoPaso,
					"Se muestra el tooltip exacto y no se ejecuta la consulta historica: " + esperado,
					"Tooltip inesperado o el grid cambio. Actual: " + tooltip.getText(),
					"Tooltip_Bloqueo_Historicos", elements.botonConsultarHistoricoCualquierEstado,
					elements.tooltipBloqueo);
		} catch (Exception exception) {
			return error("No fue posible validar el tooltip de filtros historicos: " + exception.getMessage());
		}
	}

	/**
	 * Retira el cursor del boton bloqueado y valida que el tooltip desaparezca.
	 *
	 * @return true cuando no queda visible ningun tooltip de bloqueo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaTooltipBloqueoDesaparece() {
		try {
			WebElement destino = u.getDriver().findElement(elements.tituloPantalla);
			new Actions(u.getDriver()).moveToElement(destino).perform();
			boolean resultadoPaso = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(ExpectedConditions.invisibilityOfElementLocated(elements.tooltipBloqueo));
			return registrarConResaltado(resultadoPaso,
					"El tooltip se oculta al retirar el cursor",
					"El tooltip de bloqueo permanece visible",
					"Tooltip_Bloqueo_Oculto", elements.tituloPantalla);
		} catch (Exception exception) {
			return error("No fue posible validar que el tooltip desaparezca: " + exception.getMessage());
		}
	}

	/**
	 * Valida que el grid mantenga el ultimo snapshot registrado.
	 *
	 * @return true cuando las filas y sus valores no se modificaron.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaGridSinCambios() {
		try {
			List<String> actual = snapshotGrid();
			boolean resultadoPaso = !ultimoSnapshotGrid.isEmpty() && ultimoSnapshotGrid.equals(actual);
			return registrarConResaltado(resultadoPaso,
					"El grid mantiene los mismos " + actual.size() + " registros de la consulta activa",
					"El grid cambio despues de intentar usar la seccion bloqueada",
					"Grid_Sin_Cambios", elements.areaGrid);
		} catch (Exception exception) {
			return error("No fue posible comparar el estado del grid: " + exception.getMessage());
		}
	}

	/**
	 * Captura y valida solo la fecha inicial de los filtros historicos.
	 *
	 * @param fecha fecha inicial en formato dd/MM/yyyy.
	 * @return true cuando el campo conserva la fecha recibida.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean capturaFechaInicioHistorica(String fecha) {
		try {
			return capturaFecha(elements.fechaInicio, fecha, "Inicio");
		} catch (Exception exception) {
			return error("No fue posible capturar la fecha inicial: " + exception.getMessage());
		}
	}

	/**
	 * Captura y valida solo la fecha final de los filtros historicos.
	 *
	 * @param fecha fecha final en formato dd/MM/yyyy.
	 * @return true cuando el campo conserva la fecha recibida.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean capturaFechaFinHistorica(String fecha) {
		try {
			return capturaFecha(elements.fechaFin, fecha, "Fin");
		} catch (Exception exception) {
			return error("No fue posible capturar la fecha final: " + exception.getMessage());
		}
	}

	/**
	 * Valida el estado deshabilitado de los campos de filtros rapidos.
	 *
	 * @return true cuando Canastilla y Transferencia estan deshabilitados.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaFiltrosRapidosDeshabilitados() {
		try {
			WebElement canastilla = u.getDriver().findElement(elements.inputCanastilla);
			WebElement transferencia = u.getDriver().findElement(elements.inputTransferencia);
			boolean canastillaDeshabilitada = controlAngularDeshabilitado(canastilla);
			boolean transferenciaDeshabilitada = controlAngularDeshabilitado(transferencia);
			boolean resultadoPaso = canastillaDeshabilitada && transferenciaDeshabilitada;
			String detalleEstados = "Canastilla=" + canastillaDeshabilitada
					+ " | Transferencia=" + transferenciaDeshabilitada;
			return registrarConResaltado(resultadoPaso,
					"Los campos rapidos aparecen deshabilitados. " + detalleEstados,
					"Uno o mas campos rapidos permanecen activos. " + detalleEstados,
					"Filtros_Rapidos_Deshabilitados", elements.inputCanastilla,
					elements.inputTransferencia);
		} catch (Exception exception) {
			return error("No fue posible validar el bloqueo de filtros rapidos: " + exception.getMessage());
		}
	}

	/**
	 * Valida que Consultar y Limpiar filtros rapidos esten deshabilitados.
	 *
	 * @return true cuando ambos botones presentan estado deshabilitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaBotonesRapidosDeshabilitados() {
		try {
			WebElement consultar = u.getDriver().findElement(elements.botonConsultarRapidoCualquierEstado);
			WebElement limpiar = u.getDriver().findElement(elements.botonLimpiarRapido);
			boolean consultarDeshabilitado = controlAngularDeshabilitado(consultar);
			boolean limpiarDeshabilitado = controlAngularDeshabilitado(limpiar);
			boolean resultadoPaso = consultarDeshabilitado && limpiarDeshabilitado;
			String detalleEstados = "Consultar=" + consultarDeshabilitado
					+ " | Limpiar=" + limpiarDeshabilitado;
			return registrarConResaltado(resultadoPaso,
					"Consultar y Limpiar filtros rapidos aparecen deshabilitados. " + detalleEstados,
					"Uno o ambos botones rapidos permanecen habilitados. " + detalleEstados,
					"Botones_Rapidos_Deshabilitados", elements.botonConsultarRapidoCualquierEstado,
					elements.botonLimpiarRapido);
		} catch (Exception exception) {
			return error("No fue posible validar los botones rapidos: " + exception.getMessage());
		}
	}

	/**
	 * Intenta accionar Consultar rapido bloqueado y valida el tooltip exacto.
	 *
	 * @return true cuando aparece el tooltip exacto de bloqueo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean intentaConsultaRapidaBloqueadaYValidaTooltip() {
		try {
			List<String> snapshotAnterior = snapshotGrid();
			WebElement boton = u.getDriver().findElement(elements.botonConsultarRapidoCualquierEstado);
			new Actions(u.getDriver()).moveToElement(boton).click().perform();
			WebElement tooltip = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.tooltipBloqueo));
			String esperado = "Para usar los filtros rapidos, limpia primero los filtros historicos.";
			boolean resultadoPaso = !snapshotAnterior.isEmpty()
					&& normalizar(tooltip.getText()).equals(normalizar(esperado))
					&& snapshotAnterior.equals(snapshotGrid());
			return registrarConResaltado(resultadoPaso,
					"Se muestra el tooltip exacto y el grid historico permanece sin cambios: " + esperado,
					"Tooltip inesperado o el grid historico cambio. Actual: " + tooltip.getText(),
					"Tooltip_Bloqueo_Rapidos", elements.botonConsultarRapidoCualquierEstado,
					elements.tooltipBloqueo, elements.areaGrid);
		} catch (Exception exception) {
			return error("No fue posible validar el tooltip de filtros rapidos: " + exception.getMessage());
		}
	}

	/**
	 * Valida que Consultar historico regrese al estado inicial despues de limpiar filtros rapidos.
	 * Comprueba que los campos de fecha y el calendario recuperen interaccion; mientras el rango este
	 * vacio, acepta que Consultar permanezca deshabilitado por la validacion normal del formulario.
	 *
	 * @return true cuando se retira el bloqueo cruzado y Consultar solo depende del rango de fechas.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaEstadoInicialConsultarHistorico() {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement consultar = wait.until(ExpectedConditions.visibilityOfElementLocated(
					elements.botonConsultarHistoricoCualquierEstado));
			WebElement fechaInicio = u.getDriver().findElement(elements.fechaInicio);
			WebElement fechaFin = u.getDriver().findElement(elements.fechaFin);
			WebElement calendario = wait.until(ExpectedConditions.elementToBeClickable(
					elements.botonCalendarioHistorico));
			boolean rangoVacio = valorCampo(elements.fechaInicio).isEmpty()
					&& valorCampo(elements.fechaFin).isEmpty();
			boolean fechasInteractivas = fechaInicio.isEnabled() && fechaFin.isEnabled()
					&& calendario.isEnabled()
					&& !"none".equalsIgnoreCase(calendario.getCssValue("pointer-events"));
			boolean consultarEnEstadoInicial = elementoDeshabilitado(consultar) && rangoVacio;
			boolean sinTooltipBloqueo = u.getDriver().findElements(elements.tooltipBloqueo).stream()
					.noneMatch(WebElement::isDisplayed);
			boolean resultadoPaso = fechasInteractivas && consultarEnEstadoInicial && sinTooltipBloqueo;
			String detalleEstado = "Fechas interactivas=" + fechasInteractivas
					+ " | Rango vacio=" + rangoVacio
					+ " | Consultar condicionado por rango=" + consultarEnEstadoInicial
					+ " | Sin tooltip de bloqueo=" + sinTooltipBloqueo;
			return registrarConResaltado(resultadoPaso,
					"La seccion historica regresa al estado inicial; Consultar depende unicamente del rango de fechas. "
							+ detalleEstado,
					"La seccion historica conserva un bloqueo ajeno al rango de fechas. "
							+ detalleEstado,
					"Consultar_Historico_Estado_Inicial", elements.fechaInicio, elements.fechaFin,
					elements.botonCalendarioHistorico, elements.botonConsultarHistoricoCualquierEstado);
		} catch (Exception exception) {
			return error("No fue posible validar el estado inicial de Consultar historico despues de limpiar: "
					+ exception.getMessage());
		}
	}

	/**
	 * Valida que Limpiar filtros historicos vuelva a estar habilitado.
	 *
	 * @return true cuando el boton no presenta estado disabled.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaLimpiarHistoricoHabilitado() {
		try {
			WebElement boton = u.getDriver().findElement(elements.botonLimpiarHistorico);
			return registrarConResaltado(!elementoDeshabilitado(boton),
					"El boton Limpiar filtros historicos esta habilitado nuevamente",
					"El boton Limpiar filtros historicos permanece deshabilitado",
					"Limpiar_Historico_Habilitado", elements.botonLimpiarHistorico);
		} catch (Exception exception) {
			return error("No fue posible validar Limpiar filtros historicos: " + exception.getMessage());
		}
	}

	/**
	 * Valida que no se muestren tooltips de bloqueo entre las secciones.
	 *
	 * @return true cuando no existe ningun tooltip de bloqueo visible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaSinMensajesBloqueo() {
		try {
			boolean resultadoPaso = u.getDriver().findElements(elements.tooltipBloqueo).stream()
					.noneMatch(WebElement::isDisplayed);
			return registrarConResaltado(resultadoPaso,
					"No se muestran mensajes de bloqueo entre filtros",
					"Permanece visible un mensaje de bloqueo entre filtros",
					"Sin_Mensajes_Bloqueo", elements.seccionFiltrosRapidos,
					elements.seccionFiltrosHistoricos);
		} catch (Exception exception) {
			return error("No fue posible validar la ausencia de mensajes de bloqueo: " + exception.getMessage());
		}
	}

	/**
	 * Valida la carga inicial de las dos secciones y el area del grid sin errores visibles.
	 *
	 * @return true cuando filtros rapidos, historicos y grid estan disponibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaCargaInicialCompleta() {
		try {
			String source = normalizar(u.getDriver().getPageSource());
			boolean sinError = !source.contains("internal server error") && !source.contains("http error 500")
					&& !source.contains("pagina no encontrada");
			boolean resultadoPaso = u.isDisplayed(elements.seccionFiltrosRapidos)
					&& u.isDisplayed(elements.seccionFiltrosHistoricos)
					&& u.isDisplayed(elements.areaGrid) && sinError;
			return registrarConResaltado(resultadoPaso,
					"Ambas secciones y el area del grid cargan sin errores",
					"La pantalla no cargo ambas secciones, el grid o presenta un error",
					"Carga_Inicial_Recepcion", elements.seccionFiltrosRapidos,
					elements.seccionFiltrosHistoricos, elements.areaGrid);
		} catch (Exception exception) {
			return error("No fue posible validar la carga inicial: " + exception.getMessage());
		}
	}

	/**
	 * Obtiene una canastilla valida desde un FUE historico y valida su independencia de Transferencia.
	 *
	 * @param fechaInicio fecha inicial para localizar datos historicos.
	 * @param fechaFin fecha final para localizar datos historicos.
	 * @return true cuando Canastilla acepta el dato y Transferencia permanece vacia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean preparaCanastillaValidaYValidaIndependencia(String fechaInicio, String fechaFin) {
		try {
			boolean consulta = capturaRangoFechas(fechaInicio, fechaFin) && consultaHistoricaConResultados();
			boolean fueAbierto = consulta && abrePrimerFueYConservaCanastilla();
			boolean canastillaCapturada = fueAbierto && limpiaFiltrosYCapturaCanastilla();
			boolean resultadoPaso = canastillaCapturada && valorCampo(elements.inputTransferencia).isEmpty()
					&& !valorCampo(elements.inputCanastilla).isEmpty();
			return registrarConResaltado(resultadoPaso,
					"Canastilla acepta entrada y funciona de forma independiente a Transferencia",
					"Canastilla y Transferencia no funcionan de forma independiente",
					"Canastilla_Independiente_Transferencia", elements.inputCanastilla,
					elements.inputTransferencia);
		} catch (Exception exception) {
			return error("No fue posible preparar una canastilla valida: " + exception.getMessage());
		}
	}

	/**
	 * Valida que el contador del grid sea coherente con las filas visibles.
	 *
	 * @return true cuando el total informado es mayor o igual a las filas visibles y ambas cantidades son positivas.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaContadorGridCoherente() {
		try {
			int filasVisibles = u.getDriver().findElements(elements.gridFilas).size();
			int total = filasVisibles;
			List<WebElement> contadores = u.getDriver().findElements(elements.contadorGrid);
			if (!contadores.isEmpty()) {
				Matcher matcher = Pattern.compile("(?:de|of)\\s+(\\d+)", Pattern.CASE_INSENSITIVE)
						.matcher(contadores.get(0).getText());
				if (matcher.find()) {
					total = Integer.parseInt(matcher.group(1));
				}
			}
			boolean resultadoPaso = filasVisibles > 0 && total >= filasVisibles;
			return registrarConResaltado(resultadoPaso,
					"El contador documenta " + total + " registros y es coherente con " + filasVisibles + " filas visibles",
					"Contador incoherente. Total: " + total + " | Filas visibles: " + filasVisibles,
					"Contador_Grid_Coherente", elements.contadorGrid, elements.areaGrid);
		} catch (Exception exception) {
			return error("No fue posible validar el contador del grid: " + exception.getMessage());
		}
	}

	/**
	 * Ingresa a Recepcion y Devolucion desde el modulo CEDIS y valida la carga inicial de la pantalla.
	 * Registra T1 y T2, acepta carga inmediata sin modal perceptible, exige que el modal bloquee pantalla si aparece y valida filtros sin No items found.
	 *
	 * @param tiempoMaximoSegundos tiempo maximo permitido para que la pantalla quede disponible.
	 * @param user usuario usado para obtener PCSUPPLIERID y consultar el total esperado del grid en BD.
	 * @return true cuando Recepcion queda disponible dentro del tiempo maximo y los filtros cargan correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	public boolean ingresarRecepcionYValidarCargaInicial(int tiempoMaximoSegundos, String user) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime t1 = LocalDateTime.now();
			String textoT1 = t1.format(formatter);
			long inicioCarga = System.currentTimeMillis();
			reiniciarResumenCargaInicial();
			testCase.addBoldTextEvidenceCurrentStep("T1 inicio de carga: " + textoT1);

			new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.tarjetaRecepcionYDevolucion)).click();

			boolean modalValido = validarModalInicialOpcional(inicioCarga);
			boolean pantallaDisponible = esperarPantallaDisponible(inicioCarga, tiempoMaximoSegundos);
			LocalDateTime t2 = LocalDateTime.now();
			String textoT2 = t2.format(formatter);
			double tiempoCarga = (System.currentTimeMillis() - inicioCarga) / 1000.0;
			String textoTiempoCarga = String.format("%.2f", tiempoCarga);
			testCase.addBoldTextEvidenceCurrentStep("T2 fin de carga: " + textoT2 + " | T_carga: " + textoTiempoCarga + " segundos");

			if (!modalValido) {
				return registrar(false, "", "El modal Espere un momento fue visible, pero no cubrio la pantalla completa durante la carga", "Overlay_Espere_Un_Momento_No_Cubre_Pantalla");
			}

			if (!pantallaDisponible || tiempoCarga > tiempoMaximoSegundos) {
				return registrar(false, "", "Recepcion no quedo disponible dentro de " + tiempoMaximoSegundos + " segundos. T_carga: " + String.format("%.2f", tiempoCarga), "Carga_Inicial_Recepcion_Fuera_Tiempo");
			}

			boolean filtrosCorrectos = validaFiltrosCargaInicialSinNoItems(inicioCarga, user);
			agregarTablaResumenCargaInicial(textoT1, textoT2, textoTiempoCarga, tiempoMaximoSegundos, pantallaDisponible, filtrosCorrectos);
			String detalleModal = ultimoModalFueVisible ? "El modal Espere un momento fue visible durante la carga y cubrio la pantalla" : "La carga fue inmediata y el modal no fue perceptible";
			return registrar(filtrosCorrectos, detalleModal + "; Recepcion quedo disponible en " + String.format("%.2f", tiempoCarga) + " segundos y los filtros cargaron correctamente",
					"Recepcion cargo dentro del tiempo, pero uno o mas filtros no mostraron opciones validas", "Carga_Inicial_Recepcion_CEDIS");
		} catch (Exception exception) {
			return error("No fue posible validar la carga inicial de Recepcion: " + exception.getMessage());
		}
	}

	/**
	 * Abre el primer detalle o comprobante disponible del grid.
	 *
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean abrePrimerDetalleGrid() {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.primerBotonDetalleGrid)).click();
			WebElement dialogo = new WebDriverWait(u.getDriver(), Duration.ofSeconds(40))
					.until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.xpath(
							"//mat-dialog-container | //*[@role='dialog']")));
			By dialogoDetalle = By.xpath("//mat-dialog-container | //*[@role='dialog']");
			return registrarConResaltado(dialogo.isDisplayed(),
					"El sistema abre correctamente el detalle del primer comprobante",
					"No se mostro el detalle del primer comprobante",
					"Detalle_Primer_Comprobante", dialogoDetalle);
		} catch (Exception exception) {
			return error("No fue posible abrir el primer detalle del grid: " + exception.getMessage());
		}
	}

	/**
	 * Consulta el historico y documenta dos transferencias distintas disponibles.
	 *
	 * @param fechaInicio fecha inicial en formato dd/MM/yyyy.
	 * @param fechaFin fecha final en formato dd/MM/yyyy.
	 * @return true cuando se obtienen al menos dos numeros distintos.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean descubreDosTransferencias(String fechaInicio, String fechaFin) {
		try {
			boolean consulta = capturaRangoFechas(fechaInicio, fechaFin) && consultaHistoricaConResultados();
			Set<String> transferencias = consulta ? transferenciasVisibles() : new LinkedHashSet<String>();
			java.util.Iterator<String> iterator = transferencias.iterator();
			transferenciaA = iterator.hasNext() ? iterator.next() : null;
			transferenciaB = iterator.hasNext() ? iterator.next() : null;
			boolean resultadoPaso = transferenciaA != null && transferenciaB != null
					&& !transferenciaA.equals(transferenciaB);
			return registrarConResaltado(resultadoPaso,
					"Transferencias documentadas: A=" + transferenciaA + " | B=" + transferenciaB,
					"No se encontraron dos transferencias distintas. Valores: " + transferencias,
					"Dos_Transferencias_Documentadas", elements.celdasTransferenciaGrid);
		} catch (Exception exception) {
			return error("No fue posible descubrir dos transferencias: " + exception.getMessage());
		}
	}

	/**
	 * Limpia los filtros historicos y valida que ambas secciones queden disponibles.
	 *
	 * @return true cuando los campos historicos quedan vacios y Transferencia esta habilitada.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean limpiaFiltrosHistoricos() {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.botonLimpiarHistorico)).click();
			boolean resultadoPaso = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20)).until(driver ->
					valorCampo(elements.fechaInicio).isEmpty() && valorCampo(elements.fechaFin).isEmpty()
							&& !elementoDeshabilitado(driver.findElement(elements.inputTransferencia)));
			return registrarConResaltado(resultadoPaso,
					"La pantalla regresa al estado inicial con ambas secciones habilitadas",
					"Los filtros historicos no se limpiaron o filtros rapidos siguen bloqueados",
					"Limpiar_Filtros_Historicos", elements.seccionFiltrosHistoricos,
					elements.seccionFiltrosRapidos);
		} catch (Exception exception) {
			return error("No fue posible limpiar los filtros historicos: " + exception.getMessage());
		}
	}

	/**
	 * Documenta la cantidad de registros mostrados para Transferencia A.
	 *
	 * @return true cuando se registra una cantidad positiva.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean documentaConteoTransferenciaA() {
		try {
			GridCompletoSnapshot snapshot = recopilarGridCompleto("Conteo_Transferencia_A");
			conteoTransferenciaA = snapshot.transferencias.size();
			boolean resultadoPaso = conteoTransferenciaA > 0
					&& conteoTransferenciaA == snapshot.totalReportado;
			return registrarConResaltado(resultadoPaso,
					"N_A queda documentado con " + conteoTransferenciaA
							+ " registros en " + snapshot.paginas + " paginas",
					"Conteo incompleto. Procesados: " + conteoTransferenciaA
							+ " | Total paginador: " + snapshot.totalReportado,
					"Conteo_Total_Transferencia_A", elements.contadorGrid, elements.areaGrid);
		} catch (Exception exception) {
			return error("No fue posible documentar el conteo de Transferencia A: " + exception.getMessage());
		}
	}

	/**
	 * Limpia filtros rapidos, consulta Transferencia B y valida orden de fecha descendente.
	 *
	 * @return true cuando Transferencia B muestra resultados ordenados de reciente a antiguo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean consultaTransferenciaBYValidaOrden() {
		try {
			if (transferenciaB == null || transferenciaB.isEmpty() || !limpiaFiltrosRapidos()) {
				return registrar(false, "", "No existe Transferencia B o no fue posible limpiar filtros rapidos",
						"Transferencia_B_No_Disponible");
			}
			boolean capturada = capturaYValidaTransferencia(transferenciaB);
			boolean consultada = capturada && consultaTransferenciaConResultados();
			GridCompletoSnapshot snapshot = consultada
					? recopilarGridCompleto("Orden_Transferencia_B") : new GridCompletoSnapshot();
			boolean ordenada = consultada && snapshot.totalReportado == snapshot.fechas.size()
					&& fechasEnOrdenDescendente(snapshot.fechas);
			return registrarConResaltado(ordenada,
					"El grid completo muestra " + snapshot.fechas.size()
							+ " registros de Transferencia B de la fecha mas reciente a la mas antigua",
					"La consulta de Transferencia B no tiene resultados, esta incompleta o no conserva orden descendente. "
							+ "Fechas: " + snapshot.fechas + " | Total: " + snapshot.totalReportado,
					"Transferencia_B_Orden_Global_Descendente", elements.celdasFechaGrid,
					elements.contadorGrid);
		} catch (Exception exception) {
			return error("No fue posible consultar Transferencia B: " + exception.getMessage());
		}
	}

	/**
	 * Valida que todas las filas correspondan a Transferencia B y documenta su cantidad.
	 *
	 * @return true cuando no se mezcla Transferencia A y existe al menos un registro de B.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaGridSoloTransferenciaB() {
		try {
			GridCompletoSnapshot snapshot = recopilarGridCompleto("Transferencia_B_Final");
			int conteoB = snapshot.transferencias.size();
			boolean soloTransferenciaB = transferenciaB != null && conteoB > 0
					&& conteoB == snapshot.totalReportado
					&& snapshot.transferencias.stream().allMatch(transferenciaB::equals);
			boolean sinTransferenciaA = transferenciaA != null && !transferenciaA.equals(transferenciaB)
					&& snapshot.transferencias.stream().noneMatch(transferenciaA::equals);
			return registrarConResaltado(soloTransferenciaB && sinTransferenciaA,
					"El 100% de N_B=" + conteoB + " registros pertenece a Transferencia B; "
							+ "no contiene Transferencia A y N_A=" + conteoTransferenciaA,
					"El grid mezcla transferencias, contiene Transferencia A o el conteo esta incompleto. "
							+ "Valores: " + snapshot.transferencias + " | N_B=" + conteoB
							+ " | Total paginador=" + snapshot.totalReportado + " | N_A="
							+ conteoTransferenciaA,
					"Grid_Completo_Solo_Transferencia_B_Conteo", elements.celdasTransferenciaGrid,
					elements.contadorGrid);
		} catch (Exception exception) {
			return error("No fue posible validar el grid de Transferencia B: " + exception.getMessage());
		}
	}

	/**
	 * Intenta capturar la fecha inicial mientras filtros historicos estan bloqueados.
	 *
	 * @return true cuando el campo no acepta el valor o Consultar historico permanece deshabilitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean intentaCapturarFechaHistoricaBloqueada() {
		try {
			WebElement fecha = u.getDriver().findElement(elements.fechaInicio);
			String anterior = fecha.getAttribute("value");
			if (!elementoDeshabilitado(fecha)) {
				fecha.sendKeys(Keys.chord(Keys.CONTROL, "a"), "01/03/2026");
			}
			WebElement consultar = u.getDriver().findElement(elements.botonConsultarHistoricoCualquierEstado);
			boolean resultadoPaso = elementoDeshabilitado(fecha) || elementoDeshabilitado(consultar);
			return registrarConResaltado(resultadoPaso,
					"Fecha historica no permite consulta mientras filtros rapidos estan activos",
					"El filtro historico y su boton Consultar quedaron activos simultaneamente",
					"Fecha_Historica_Bloqueada", elements.fechaInicio,
					elements.botonConsultarHistoricoCualquierEstado);
		} catch (Exception exception) {
			return error("No fue posible intentar la captura de fecha bloqueada: " + exception.getMessage());
		}
	}

	/**
	 * Captura una fecha en el campo indicado y valida el valor final.
	 *
	 * @param locator localizador del campo de fecha.
	 * @param fecha valor esperado en formato dd/MM/yyyy.
	 * @param tipo nombre Inicio o Fin para la evidencia.
	 * @return true cuando el campo conserva la fecha.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private boolean capturaFecha(org.openqa.selenium.By locator, String fecha, String tipo) {
		WebElement input = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
				.until(ExpectedConditions.elementToBeClickable(locator));
		LocalDate fechaEsperada = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("d/M/yyyy"));
		String fechaCaptura = fechaEsperada.format(DateTimeFormatter.ofPattern("d/M/yyyy"));
		input.click();
		input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE, fechaCaptura);
		((JavascriptExecutor) u.getDriver()).executeScript(
				"arguments[0].dispatchEvent(new Event('input', {bubbles:true}));"
				+ "arguments[0].dispatchEvent(new Event('change', {bubbles:true}));"
				+ "arguments[0].blur();", input);
		input.sendKeys(Keys.TAB);
		String actual = input.getAttribute("value");
		boolean fechaCorrecta = fechaEsperada.equals(
				LocalDate.parse(actual, DateTimeFormatter.ofPattern("d/M/yyyy")));
		boolean consultarHabilitado = !"Fin".equals(tipo) || new WebDriverWait(u.getDriver(),
				Duration.ofSeconds(20)).until(driver -> !elementoDeshabilitado(
						driver.findElement(elements.botonConsultarHistoricoCualquierEstado)));
		boolean resultadoPaso = fechaCorrecta && consultarHabilitado;
		return registrarConResaltado(resultadoPaso,
				"El campo Fecha " + tipo + " muestra " + actual
						+ ("Fin".equals(tipo) ? " y habilita Consultar" : ""),
				"Fecha " + tipo + " no sincronizada. Esperada: " + fechaCaptura + " | Actual: " + actual
						+ " | Consultar habilitado: " + consultarHabilitado,
				"Fecha_" + tipo + "_Historica", locator);
	}

	/**
	 * Determina si un elemento presenta estado deshabilitado nativo o accesible.
	 *
	 * @param elemento control que se debe revisar.
	 * @return true cuando el control no esta habilitado o declara estado disabled.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private boolean elementoDeshabilitado(WebElement elemento) {
		String ariaDisabled = elemento.getAttribute("aria-disabled");
		String disabled = elemento.getAttribute("disabled");
		String clases = elemento.getAttribute("class");
		return !elemento.isEnabled() || "true".equalsIgnoreCase(ariaDisabled) || disabled != null
				|| (clases != null && clases.toLowerCase().contains("disabled"));
	}

	/**
	 * Determina si un control Angular esta deshabilitado en el nodo localizado o en su wrapper funcional.
	 * Reconoce estados nativos, ARIA, clases Material/ng-select y bloqueo mediante pointer-events.
	 *
	 * @param elemento control visible o nodo interno que se debe validar.
	 * @return true cuando el control o su wrapper inmediato presenta un estado de bloqueo verificable.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private boolean controlAngularDeshabilitado(WebElement elemento) {
		if (elementoDeshabilitado(elemento) || estadoVisualDeshabilitado(elemento)
				|| "none".equalsIgnoreCase(elemento.getCssValue("pointer-events"))) {
			return true;
		}
		List<WebElement> wrappers = elemento.findElements(By.xpath(
				"ancestor-or-self::*[self::mat-form-field or self::ng-select or self::mat-select "
				+ "or contains(concat(' ', normalize-space(@class), ' '), ' action-buttons ') "
				+ "or contains(concat(' ', normalize-space(@class), ' '), ' cedis-filters-form-02 ')]"));
		for (WebElement wrapper : wrappers) {
			if (elementoDeshabilitado(wrapper) || estadoVisualDeshabilitado(wrapper)
					|| "none".equalsIgnoreCase(wrapper.getCssValue("pointer-events"))) {
				return true;
			}
		}
		List<WebElement> estadosInternos = elemento.findElements(By.xpath(
				".//*[@disabled or @aria-disabled='true' or contains(translate(@class,'DISABLED','disabled'),'disabled')]"));
		return !estadosInternos.isEmpty();
	}

	/**
	 * Determina si una seccion presenta estilo visual de bloqueo.
	 *
	 * @param seccion contenedor que se debe revisar.
	 * @return true cuando clase, aria-disabled u opacidad indican bloqueo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private boolean estadoVisualDeshabilitado(WebElement seccion) {
		String clases = seccion.getAttribute("class");
		String ariaDisabled = seccion.getAttribute("aria-disabled");
		String opacity = seccion.getCssValue("opacity");
		return "true".equalsIgnoreCase(ariaDisabled)
				|| (clases != null && (clases.toLowerCase().contains("disabled")
						|| clases.toLowerCase().contains("inactive")))
				|| (opacity != null && !opacity.isEmpty() && Double.parseDouble(opacity) < 1.0d);
	}

	/**
	 * Valida que las fechas visibles del grid esten ordenadas de reciente a antigua.
	 *
	 * @return true cuando cada fecha es igual o anterior a la precedente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private boolean fechasGridEnOrdenDescendente() {
		List<WebElement> celdas = u.getDriver().findElements(elements.celdasFechaGrid);
		if (celdas.isEmpty()) {
			return false;
		}
		LocalDateTime anterior = null;
		for (WebElement celda : celdas) {
			LocalDateTime actual = parseFecha(celda.getText().trim());
			if (actual == null || (anterior != null && actual.isAfter(anterior))) {
				return false;
			}
			anterior = actual;
		}
		return true;
	}

	/**
	 * Convierte los formatos comunes de fecha del grid a LocalDateTime.
	 *
	 * @param texto fecha visible en el grid.
	 * @return fecha convertida o null cuando el formato no es reconocido.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private LocalDateTime parseFecha(String texto) {
		String[] formatosFechaHora = { "dd/MM/yyyy HH:mm:ss", "dd/MM/yyyy HH:mm", "dd-MM-yyyy HH:mm:ss",
				"dd-MM-yyyy HH:mm", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm" };
		for (String formato : formatosFechaHora) {
			try {
				return LocalDateTime.parse(texto, DateTimeFormatter.ofPattern(formato));
			} catch (DateTimeParseException ignored) {
				// Se intenta el siguiente formato conocido del grid.
			}
		}
		String[] formatosFecha = { "dd/MM/yyyy", "dd-MM-yyyy", "yyyy-MM-dd" };
		for (String formato : formatosFecha) {
			try {
				return LocalDate.parse(texto, DateTimeFormatter.ofPattern(formato)).atStartOfDay();
			} catch (DateTimeParseException ignored) {
				// Se intenta el siguiente formato conocido del grid.
			}
		}
		return null;
	}

	/**
	 * Registra el resultado de una validacion visual con los elementos evaluados resaltados.
	 * La captura se genera antes de retirar el resaltado para facilitar la lectura de la evidencia.
	 *
	 * @param result resultado calculado por la validacion.
	 * @param success mensaje que se registra cuando la validacion es exitosa.
	 * @param failure mensaje que se registra cuando la validacion falla.
	 * @param screenshot nombre de la captura asociada al paso.
	 * @param locators elementos que deben aparecer resaltados en la evidencia.
	 * @return el mismo resultado recibido.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	private boolean registrarConResaltado(boolean result, String success, String failure, String screenshot,
			By... locators) {
		List<By> locatorsResaltados = new ArrayList<>();
		try {
			List<WebElement> elementosVisibles = new ArrayList<WebElement>();
			for (By locator : locators) {
				WebElement elementoVisible = u.getDriver().findElements(locator).stream()
						.filter(WebElement::isDisplayed).findFirst().orElse(null);
				if (elementoVisible != null) {
					elementosVisibles.add(elementoVisible);
				}
			}
			if (!elementosVisibles.isEmpty()) {
				WebElement elementoReferencia = elementosVisibles.get(elementosVisibles.size() / 2);
				((JavascriptExecutor) u.getDriver()).executeScript(
						"arguments[0].scrollIntoView({block:'center', inline:'center'});", elementoReferencia);
				u.hardWait(1);
			}
			for (By locator : locators) {
				if (u.getDriver().findElements(locator).stream().anyMatch(WebElement::isDisplayed)) {
					u.highLight(locator);
					locatorsResaltados.add(locator);
				}
			}
			return registrar(result, success, failure, screenshot);
		} finally {
			for (By locator : locatorsResaltados) {
				try {
					u.unhighLight(locator);
				} catch (Exception exception) {
					System.out.println("[WARN] No fue posible retirar el resaltado: " + exception.getMessage());
				}
			}
		}
	}

	/**
	 * Valida individualmente la carga inicial de la pantalla Recepcion de Transferencias y Devoluciones.
	 * Comprueba las secciones rapida e historica, sus controles visibles y ambos botones Consultar deshabilitados.
	 *
	 * @param numeroPaso numero de paso que se registra en consola y evidencia.
	 * @return true cuando la pantalla y todos los controles requeridos estan visibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaCargaInicialFiltrosRapidosEHistoricos(int numeroPaso) {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(40))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.tituloPantalla));
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(40))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.tituloFiltrosHistoricos));

			String[] nombresControles = { "Pantalla Recepcion", "Filtros rapidos", "Filtros historicos",
					"Plaza", "Tienda", "Selector de Ruta", "Ruta", "Rango de Fechas",
					"Limpiar Filtros historicos", "Consultar historico deshabilitado",
					"Consultar rapido deshabilitado" };
			By[] locatorsControles = { elements.tituloPantalla, elements.tituloFiltrosRapidos,
					elements.tituloFiltrosHistoricos, elements.filtroPlaza, elements.filtroTienda,
					elements.selectorRuta, elements.campoRuta, elements.campoRangoFechas,
					elements.botonLimpiarHistorico, elements.botonConsultarHistoricoDeshabilitado,
					elements.botonConsultarRapidoDeshabilitado };
			List<String> controlesFaltantes = new ArrayList<String>();
			for (int indice = 0; indice < locatorsControles.length; indice++) {
				boolean controlVisible = u.getDriver().findElements(locatorsControles[indice]).stream()
						.anyMatch(WebElement::isDisplayed);
				String estadoControl = controlVisible ? "PASSED" : "FAILED";
				String evidenciaControl = nombresControles[indice] + " visible";
				System.out.println("[VALIDACION CARGA INICIAL RECEPCION] " + estadoControl + " - "
						+ evidenciaControl);
				testCase.addTextEvidenceCurrentStep("Validacion individual: " + estadoControl + " - "
						+ evidenciaControl);
				if (!controlVisible) {
					controlesFaltantes.add(nombresControles[indice]);
				}
			}
			boolean resultadoPaso = controlesFaltantes.isEmpty();
			boolean resultadoValidacion = registrarConResaltado(resultadoPaso,
					"Se visualizan la pantalla, filtros rapidos y todos los filtros historicos",
					"Faltan controles de la pantalla: " + controlesFaltantes,
					"Carga_Inicial_Filtros_Recepcion", elements.tituloPantalla,
					elements.tituloFiltrosRapidos, elements.tituloFiltrosHistoricos,
					elements.filtroPlaza, elements.filtroTienda, elements.selectorRuta,
					elements.campoRuta, elements.campoRangoFechas, elements.botonLimpiarHistorico,
					elements.botonConsultarHistoricoDeshabilitado,
					elements.botonConsultarRapidoDeshabilitado);

			if (resultadoValidacion && u.getDriver().findElements(elements.areaGrid).stream().anyMatch(WebElement::isDisplayed)) {
				WebElement areaGrid = u.getDriver().findElements(elements.areaGrid).stream()
						.filter(WebElement::isDisplayed).findFirst().orElse(null);
				if (areaGrid != null) {
					((JavascriptExecutor) u.getDriver()).executeScript(
							"arguments[0].scrollIntoView({block:'center', inline:'center'});", areaGrid);
					u.highLight(elements.areaGrid);
					try {
						testCase.addTextEvidenceCurrentStep("Evidencia de grid en estado inicial de Recepcion.");
						testCase.addScreenShotCurrentStep("Carga_Inicial_Recepcion_Grid");
					} finally {
						u.unhighLight(elements.areaGrid);
					}
				}
			}
			return resultadoValidacion;
		} catch (Exception exception) {
			return error("No fue posible validar la carga inicial de Recepcion: "
					+ exception.getMessage());
		}
	}

	/**
	 * Obtiene desde BD una transferencia valida asociada al PCSUPPLIERID del usuario CEDIS.
	 * Conserva el valor en Transferencia A para reutilizarlo en la captura y validacion del filtro rapido.
	 *
	 * @param usuario usuario CEDIS utilizado para resolver el supplier asociado.
	 * @return true cuando la consulta retorna y documenta una transferencia no vacia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean descubreTransferenciaValidaPorSupplier(String usuario) {
		try {
			CometaBdValidationHelper bdValidation = new CometaBdValidationHelper(testCase);
			transferenciaA = bdValidation.obtenerTransferenciaValidaPorSupplier(usuario);
			boolean resultadoPaso = transferenciaA != null && !transferenciaA.isEmpty();
			return registrar(resultadoPaso,
					"Numero de transferencia valido documentado desde BD: " + transferenciaA,
					"No se encontro una transferencia valida asociada al PCSUPPLIERID del usuario " + usuario,
					"Transferencia_Valida_BD_Documentada");
		} catch (Exception exception) {
			return error("No fue posible descubrir una transferencia valida por supplier: "
					+ exception.getMessage());
		}
	}

	/**
	 * Obtiene desde BD una transferencia valida global para un perfil con alcance ADMIN.
	 * Conserva el valor en Transferencia A para reutilizarlo en la captura y validacion del filtro rapido.
	 *
	 * @return true cuando la consulta retorna y documenta una transferencia no vacia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean descubreTransferenciaValidaGlobal() {
		try {
			CometaBdValidationHelper bdValidation = new CometaBdValidationHelper(testCase);
			transferenciaA = bdValidation.obtenerTransferenciaValidaGlobal();
			boolean resultadoPaso = transferenciaA != null && !transferenciaA.isEmpty();
			return registrar(resultadoPaso,
					"Numero de transferencia global documentado desde BD para ADMIN: " + transferenciaA,
					"No se encontro una transferencia valida con alcance global para ADMIN",
					"Transferencia_Valida_Global_BD");
		} catch (Exception exception) {
			return error("No fue posible descubrir una transferencia valida global: "
					+ exception.getMessage());
		}
	}

	/**
	 * Valida el estado del area de resultados despues de una consulta negativa sin volver a ejecutarla.
	 * Comprueba que no existan filas en el grid y que el aviso sin informacion permanezca visible.
	 *
	 * @return true cuando el grid no contiene registros y muestra el aviso correspondiente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean validaGridSinRegistrosConAviso() {
		try {
			WebElement mensaje = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.mensajeSinResultados));
			int filasActuales = u.getDriver().findElements(elements.gridFilas).size();
			boolean resultadoPaso = mensaje.isDisplayed() && filasActuales == 0;
			return registrarConResaltado(resultadoPaso,
					"El grid permanece sin registros y presenta el aviso de consulta sin informacion",
					"Estado inesperado del grid. Filas visibles: " + filasActuales
							+ " | Aviso visible: " + mensaje.isDisplayed(),
					"Grid_Sin_Registros_Con_Aviso", elements.mensajeSinResultados);
		} catch (Exception exception) {
			return error("No fue posible validar el estado del grid sin registros: "
					+ exception.getMessage());
		}
	}

	/**
	 * Obtiene desde BD y captura la fecha inicial de un rango reciente con datos para el usuario.
	 * El alcance se resuelve mediante PCPERFIL y PCSUPPLIERID.
	 *
	 * @param usuario usuario utilizado para determinar el alcance de datos.
	 * @return true cuando la fecha inicial dinamica queda capturada.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean capturaFechaInicioHistoricaConDatos(String usuario) {
		try {
			List<String> rango = new CometaBdValidationHelper(testCase)
					.obtenerRangoHistoricoConDatos(usuario, 1);
			if (rango.size() != 2) {
				return error("No fue posible obtener un rango historico con datos para " + usuario);
			}
			fechaInicioHistoricaBD = rango.get(0);
			fechaFinHistoricaBD = rango.get(1);
			return capturaFechaInicioHistorica(fechaInicioHistoricaBD);
		} catch (Exception exception) {
			return error("No fue posible preparar la fecha inicial dinamica: " + exception.getMessage());
		}
	}

	/**
	 * Captura la fecha final del rango dinamico obtenido previamente desde BD.
	 *
	 * @return true cuando la fecha final dinamica queda capturada.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean capturaFechaFinHistoricaConDatos() {
		try {
			if (fechaFinHistoricaBD == null || fechaFinHistoricaBD.isEmpty()) {
				return error("No existe una fecha final dinamica documentada desde BD");
			}
			return capturaFechaFinHistorica(fechaFinHistoricaBD);
		} catch (Exception exception) {
			return error("No fue posible capturar la fecha final dinamica: " + exception.getMessage());
		}
	}

	/**
	 * Obtiene desde BD y selecciona en el calendario Material un rango historico con datos para el usuario.
	 * Usa el mes que contiene la fecha localizada para sincronizar correctamente los FormControl de Angular.
	 *
	 * @param usuario usuario utilizado para determinar el alcance de datos.
	 * @return true cuando ambas fechas dinamicas quedan capturadas.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean capturaRangoHistoricoConDatos(String usuario) {
		try {
			usuarioContextoActual = usuario;
			boolean fechaInicioSeleccionada = seleccionaFechaInicioHistoricaConDatosDesdeCalendario(usuario);
			return fechaInicioSeleccionada && seleccionaFechaFinHistoricaConDatosDesdeCalendario();
		} catch (Exception exception) {
			return error("No fue posible seleccionar el rango historico dinamico desde el calendario: "
					+ exception.getMessage());
		}
	}

	/**
	 * Obtiene desde BD una canastilla consultable segun el alcance del perfil y la captura en filtros rapidos.
	 * Conserva Transferencia vacia para validar que ambos campos funcionan de forma independiente.
	 *
	 * @param usuario usuario utilizado para determinar el alcance de datos.
	 * @return true cuando Canastilla acepta el dato y Transferencia permanece vacia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean preparaCanastillaValidaConDatos(String usuario) {
		try {
			ultimaCanastilla = new CometaBdValidationHelper(testCase)
					.obtenerCanastillaValidaSegunPerfil(usuario);
			if (ultimaCanastilla == null || ultimaCanastilla.isEmpty()) {
				return error("No se encontro una canastilla consultable para el alcance del usuario " + usuario);
			}

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement canastilla = wait.until(ExpectedConditions.elementToBeClickable(elements.inputCanastilla));
			WebElement transferencia = wait.until(ExpectedConditions.elementToBeClickable(elements.inputTransferencia));
			canastilla.sendKeys(Keys.CONTROL, "a");
			canastilla.sendKeys(Keys.DELETE);
			transferencia.sendKeys(Keys.CONTROL, "a");
			transferencia.sendKeys(Keys.DELETE);
			canastilla.sendKeys(ultimaCanastilla);

			boolean resultadoPaso = ultimaCanastilla.equals(valorCampo(elements.inputCanastilla))
					&& valorCampo(elements.inputTransferencia).isEmpty();
			return registrarConResaltado(resultadoPaso,
					"Canastilla " + ultimaCanastilla
							+ " acepta entrada y funciona de forma independiente a Transferencia",
					"Canastilla no conserva el valor obtenido desde BD o Transferencia no permanece vacia",
					"Canastilla_Independiente_Transferencia_Datos_Dinamicos",
					elements.inputCanastilla, elements.inputTransferencia);
		} catch (Exception exception) {
			return error("No fue posible preparar Canastilla con datos dinamicos desde BD: "
					+ exception.getMessage());
		}
	}

	/**
	 * Obtiene desde BD un mes con dos transferencias distintas para el perfil y las documenta en el grid.
	 * Conserva Transferencia A y B para las consultas rapidas de los pasos posteriores.
	 *
	 * @param usuario usuario utilizado para determinar el alcance de datos.
	 * @param perfilEsperado perfil CEDIS o ADMIN requerido por el caso.
	 * @return true cuando se documentan Transferencia A y Transferencia B distintas.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-22
	 */
	public boolean descubreDosTransferenciasConDatos(String usuario, String perfilEsperado) {
		try {
			List<String> datos = new CometaBdValidationHelper(testCase)
					.obtenerDosTransferenciasSegunPerfil(usuario, perfilEsperado);
			if (datos.size() != 4) {
				return error("No fue posible obtener desde BD dos transferencias para el perfil "
						+ perfilEsperado + " y el usuario " + usuario);
			}

			fechaInicioHistoricaBD = datos.get(0);
			fechaFinHistoricaBD = datos.get(1);
			transferenciaA = datos.get(2);
			transferenciaB = datos.get(3);
			DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d/M/yyyy");
			LocalDate fechaInicio = LocalDate.parse(fechaInicioHistoricaBD, formatoFecha);
			LocalDate fechaFin = LocalDate.parse(fechaFinHistoricaBD, formatoFecha);
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.elementToBeClickable(elements.botonCalendarioHistorico)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioHistorico));
			navegarCalendarioEntreMeses(wait, YearMonth.now(), YearMonth.from(fechaInicio));
			seleccionarDiaCalendarioVisible(wait, fechaInicio.getDayOfMonth());
			navegarCalendarioEntreMeses(wait, YearMonth.from(fechaInicio), YearMonth.from(fechaFin));
			seleccionarDiaCalendarioVisible(wait, fechaFin.getDayOfMonth());
			try {
				new WebDriverWait(u.getDriver(), Duration.ofSeconds(3))
						.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendarioHistorico));
			} catch (Exception calendarioVisible) {
				new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendarioHistorico));
			}
			String fechaInicioVisible = valorCampo(elements.fechaInicio);
			String fechaFinVisible = valorCampo(elements.fechaFin);
			boolean rangoSincronizado = fechaVisibleCoincide(fechaInicioVisible, fechaInicio)
					&& fechaVisibleCoincide(fechaFinVisible, fechaFin)
					&& !elementoDeshabilitado(u.getDriver().findElement(
							elements.botonConsultarHistoricoCualquierEstado));
			if (!rangoSincronizado) {
				return registrarConResaltado(false, "",
						"El calendario cerro, pero el rango no quedo sincronizado. Inicio esperado: "
								+ fechaInicioHistoricaBD + " | Inicio actual: " + fechaInicioVisible
								+ " | Fin esperado: " + fechaFinHistoricaBD + " | Fin actual: "
								+ fechaFinVisible,
						"Rango_Historico_No_Sincronizado", elements.fechaInicio, elements.fechaFin,
						elements.botonConsultarHistoricoCualquierEstado);
			}

			boolean consulta = consultaHistoricaConResultados();
			GridCompletoSnapshot snapshot = consulta
					? recopilarGridCompleto("Historico_Transferencias_A_B")
					: new GridCompletoSnapshot();
			boolean resultadoPaso = consulta && transferenciaA != null && transferenciaB != null
					&& !transferenciaA.equals(transferenciaB)
					&& snapshot.transferencias.contains(transferenciaA)
					&& snapshot.transferencias.contains(transferenciaB)
					&& snapshot.transferencias.size() == snapshot.totalReportado;
			return registrarConResaltado(resultadoPaso,
					"Rango dinamico " + fechaInicioHistoricaBD + " a " + fechaFinHistoricaBD
							+ " consultado | Transferencia A=" + transferenciaA
							+ " | Transferencia B=" + transferenciaB
							+ " | Registros historicos procesados=" + snapshot.transferencias.size(),
					"No fue posible consultar el rango completo o A/B no aparecen en el grid. "
							+ "A=" + transferenciaA + " | B=" + transferenciaB
							+ " | Transferencias visibles=" + snapshot.transferencias,
					"Dos_Transferencias_BD_Documentadas", elements.areaGrid,
					elements.celdasTransferenciaGrid);
		} catch (Exception exception) {
			return error("No fue posible descubrir dos transferencias con perfil y datos dinamicos: "
					+ exception.getMessage());
		}
	}

	/**
	 * Abre el calendario historico y selecciona el dia anterior como fecha inicial.
	 * La interaccion se realiza sobre Angular Material para actualizar el FormControl sin captura por teclado.
	 *
	 * @return true cuando Fecha Inicio muestra el dia anterior y el calendario permanece abierto.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	public boolean seleccionaFechaInicioHistoricaAyer() {
		try {
			LocalDate fechaInicio = LocalDate.now().minusDays(1);
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.elementToBeClickable(elements.botonCalendarioHistorico)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioHistorico));
			if (fechaInicio.getMonthValue() != LocalDate.now().getMonthValue()
					|| fechaInicio.getYear() != LocalDate.now().getYear()) {
				By botonMesAnterior = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-previous-button')]");
				wait.until(ExpectedConditions.elementToBeClickable(botonMesAnterior)).click();
			}
			seleccionarDiaCalendarioVisible(wait, fechaInicio.getDayOfMonth());
			String actual = valorCampo(elements.fechaInicio);
			boolean resultadoPaso = fechaVisibleCoincide(actual, fechaInicio)
					&& u.getDriver().findElement(elements.calendarioHistorico).isDisplayed();
			return registrar(resultadoPaso,
					"Fecha Inicio seleccionada desde el calendario: " + actual,
					"Fecha Inicio inesperada. Esperada: " + fechaInicio.format(
							DateTimeFormatter.ofPattern("d/M/yyyy")) + " | Actual: " + actual,
					"Fecha_Inicio_Calendario_Historico");
		} catch (Exception exception) {
			return error("No fue posible seleccionar ayer como Fecha Inicio: " + exception.getMessage());
		}
	}

	/**
	 * Selecciona el dia actual como fecha final del rango historico abierto.
	 * La seleccion nativa del datepicker debe cerrar el calendario y habilitar Consultar.
	 *
	 * @return true cuando Fecha Fin muestra hoy y Consultar queda habilitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	public boolean seleccionaFechaFinHistoricaHoy() {
		try {
			LocalDate fechaInicio = LocalDate.now().minusDays(1);
			LocalDate fechaFin = LocalDate.now();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioHistorico));
			if (fechaInicio.getMonthValue() != fechaFin.getMonthValue()
					|| fechaInicio.getYear() != fechaFin.getYear()) {
				By botonMesSiguiente = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-next-button')]");
				wait.until(ExpectedConditions.elementToBeClickable(botonMesSiguiente)).click();
			}
			seleccionarDiaCalendarioVisible(wait, fechaFin.getDayOfMonth());
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendarioHistorico));
			String actual = valorCampo(elements.fechaFin);
			boolean consultarHabilitado = wait.until(driver -> !elementoDeshabilitado(
					driver.findElement(elements.botonConsultarHistoricoCualquierEstado)));
			boolean resultadoPaso = fechaVisibleCoincide(actual, fechaFin) && consultarHabilitado;
			return registrar(resultadoPaso,
					"Fecha Fin seleccionada desde el calendario: " + actual + " y Consultar habilitado",
					"Fecha Fin inesperada o Consultar deshabilitado. Esperada: " + fechaFin.format(
							DateTimeFormatter.ofPattern("d/M/yyyy")) + " | Actual: " + actual
							+ " | Consultar habilitado: " + consultarHabilitado,
					"Fecha_Fin_Calendario_Historico");
		} catch (Exception exception) {
			return error("No fue posible seleccionar hoy como Fecha Fin: " + exception.getMessage());
		}
	}

	/**
	 * Ejecuta la consulta historica para activar las reglas de exclusion de CA06.
	 * La validacion de controles rapidos se realiza en el siguiente paso del script.
	 *
	 * @return true cuando el boton Consultar historico acepta la accion.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	public boolean ejecutaConsultaHistoricaParaCA06() {
		try {
			WebElement botonConsultar = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(
							elements.botonConsultarHistoricoCualquierEstado));
			botonConsultar.click();
			esperarFinCarga();
			return registrarConResaltado(true,
					"La consulta historica se ejecuta para activar las reglas de CA06",
					"No fue posible ejecutar la consulta historica",
					"Consulta_Historica_CA06",
					elements.botonConsultarHistoricoCualquierEstado);
		} catch (Exception exception) {
			return error("No fue posible ejecutar la consulta historica para CA06: "
					+ exception.getMessage());
		}
	}

	/**
	 * Selecciona un dia habilitado del mes visible en el calendario Material.
	 *
	 * @param wait espera explicita asociada al calendario abierto.
	 * @param dia dia del mes que debe seleccionarse.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private void seleccionarDiaCalendarioVisible(WebDriverWait wait, int dia) {
		By botonDia = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-month-view//button["
				+ "contains(@class,'mat-calendar-body-cell') "
				+ "and not(contains(@class,'mat-calendar-body-disabled')) "
				+ "and .//span[normalize-space()='" + dia + "']]");
		WebElement elementoDia = wait.until(ExpectedConditions.elementToBeClickable(botonDia));
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", elementoDia);
	}

	/**
	 * Compara una fecha visible de Material contra una fecha esperada aceptando dia y mes sin ceros.
	 *
	 * @param valorVisible valor recuperado del input de fecha.
	 * @param fechaEsperada fecha que debe representar el valor visible.
	 * @return true cuando ambos valores representan el mismo dia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private boolean fechaVisibleCoincide(String valorVisible, LocalDate fechaEsperada) {
		try {
			return fechaEsperada.equals(LocalDate.parse(valorVisible,
					DateTimeFormatter.ofPattern("d/M/yyyy")));
		} catch (DateTimeParseException exception) {
			return false;
		}
	}

	/**
	 * Valida la pantalla de Recepcion y la presencia de las secciones rapida e historica.
	 * No inspecciona los controles internos, que corresponden al siguiente paso de la matriz.
	 *
	 * @param numeroPaso numero de paso registrado en consola y evidencia.
	 * @return true cuando la pantalla y ambas secciones estan visibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	public boolean validaPantallaYSeccionesRecepcion(int numeroPaso) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(40));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.tituloPantalla));
			boolean resultadoPaso = u.isDisplayed(elements.tituloPantalla)
					&& u.isDisplayed(elements.tituloFiltrosRapidos)
					&& u.isDisplayed(elements.tituloFiltrosHistoricos);
			return registrarConResaltado(resultadoPaso,
					"Paso " + numeroPaso + ": se visualizan la pantalla y las secciones de filtros rapidos e historicos",
					"Paso " + numeroPaso + ": falta la pantalla o una de las secciones de filtros",
					"Pantalla_Secciones_Recepcion", elements.tituloPantalla,
					elements.tituloFiltrosRapidos, elements.tituloFiltrosHistoricos);
		} catch (Exception exception) {
			return error("Paso " + numeroPaso + ": no fue posible validar la pantalla y sus secciones: "
					+ exception.getMessage());
		}
	}

	/**
	 * Valida si el modal Espere un momento se muestra inmediatamente despues del clic y confirma que cubra la pantalla si aparece.
	 * La matriz permite que el modal no sea perceptible cuando la carga termina de forma inmediata.
	 * @return true cuando el modal no aparece por carga inmediata o cuando aparece cubriendo la pantalla.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean validarModalInicialOpcional(long inicioCarga) {
		try {
			WebElement overlay = new WebDriverWait(u.getDriver(), Duration.ofSeconds(2))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.overlayEspereUnMomento));
			ultimoModalFueVisible = true;
			boolean cubrePantalla = overlayCubreVista(overlay);
			ultimoModalCubrePantalla = cubrePantalla;
			testCase.addBoldTextEvidenceCurrentStep("Modal 'Espere un momento' visible durante la carga inicial. Cubre pantalla: " + cubrePantalla);
			testCase.addScreenShotCurrentStep("Overlay_Espere_Un_Momento_Recepcion");
			return cubrePantalla;
		} catch (TimeoutException timeoutException) {
			ultimoModalFueVisible = false;
			ultimoModalCubrePantalla = true;
			testCase.addBoldTextEvidenceCurrentStep("La carga fue inmediata; el modal 'Espere un momento' no fue perceptible dentro de la ventana de observacion inicial.");
			return true;
		}
	}

	/**
	 * Espera a que desaparezca el modal y a que los controles principales de Recepcion queden interactivos dentro del tiempo restante.
	 *
	 * @param inicioCarga marca de tiempo en milisegundos registrada antes del clic en Recepcion.
	 * @param tiempoMaximoSegundos tiempo maximo permitido por la matriz para la carga inicial.
	 * @return true cuando la pantalla queda visible e interactiva dentro del tiempo esperado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean esperarPantallaDisponible(long inicioCarga, int tiempoMaximoSegundos) {
		try {
			long segundosTranscurridos = (System.currentTimeMillis() - inicioCarga) / 1000;
			long segundosRestantes = Math.max(1, tiempoMaximoSegundos - segundosTranscurridos);
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(segundosRestantes));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayEspereUnMomento));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.tituloPantalla));
			wait.until(ExpectedConditions.elementToBeClickable(elements.filtroPlaza));
			wait.until(ExpectedConditions.elementToBeClickable(elements.filtroTienda));
			wait.until(ExpectedConditions.elementToBeClickable(elements.selectorRutaControl));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.inputRuta));
			return true;
		} catch (Exception exception) {
			testCase.addBoldTextEvidenceCurrentStep("La pantalla no quedo disponible dentro del tiempo maximo: " + exception.getMessage());
			return false;
		}
	}
	/**
	 * Obtiene desde BD una fecha con datos para el perfil y selecciona el primer dia de ese mes.
	 * Para el mes actual prepara un rango mes-a-la-fecha; para un mes anterior prepara el mes completo.
	 *
	 * @param usuario usuario utilizado para resolver el alcance CEDIS o ADMIN.
	 * @return true cuando la fecha inicial dinamica queda seleccionada desde el datepicker.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	public boolean seleccionaFechaInicioHistoricaConDatosDesdeCalendario(String usuario) {
		try {
			List<String> rango = new CometaBdValidationHelper(testCase)
					.obtenerRangoHistoricoConDatos(usuario, 1);
			if (rango.size() != 2) {
				return error("No fue posible obtener un rango historico con datos para " + usuario);
			}
			DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("d/M/yyyy");
			LocalDate fechaConDatos = LocalDate.parse(rango.get(1), formatoFecha);
			YearMonth mesConDatos = YearMonth.from(fechaConDatos);
			LocalDate fechaInicio = mesConDatos.atDay(1);
			LocalDate fechaFin = mesConDatos.equals(YearMonth.now())
					? LocalDate.now() : mesConDatos.atEndOfMonth();
			fechaInicioHistoricaBD = fechaInicio.format(formatoFecha);
			fechaFinHistoricaBD = fechaFin.format(formatoFecha);
			testCase.addBoldTextEvidenceCurrentStep("Rango historico ampliado: "
					+ fechaInicioHistoricaBD + " a " + fechaFinHistoricaBD
					+ " | Fecha con datos localizada en BD: " + fechaConDatos.format(formatoFecha));
			System.out.println("[RANGO HISTORICO AMPLIADO] " + fechaInicioHistoricaBD + " a "
					+ fechaFinHistoricaBD + " | Fecha BD: " + fechaConDatos.format(formatoFecha));
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.elementToBeClickable(elements.botonCalendarioHistorico)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioHistorico));
			navegarCalendarioEntreMeses(wait, YearMonth.now(), YearMonth.from(fechaInicio));
			seleccionarDiaCalendarioVisible(wait, fechaInicio.getDayOfMonth());
			String actual = valorCampo(elements.fechaInicio);
			boolean resultadoPaso = fechaVisibleCoincide(actual, fechaInicio)
					&& u.getDriver().findElement(elements.calendarioHistorico).isDisplayed();
			return registrar(resultadoPaso,
					"Fecha Inicio con datos seleccionada desde el calendario: " + actual,
					"Fecha Inicio inesperada. Esperada: " + fechaInicioHistoricaBD + " | Actual: " + actual,
					"Fecha_Inicio_Dinamica_Calendario");
		} catch (Exception exception) {
			return error("No fue posible seleccionar la Fecha Inicio dinamica: " + exception.getMessage());
		}
	}

	/**
	 * Espera a que Recepcion quede disponible para interactuar con Plaza y Tienda sin tocar Selector de Ruta.
	 *
	 * @param inicioCarga marca de tiempo en milisegundos registrada antes del clic en Recepcion.
	 * @param tiempoMaximoSegundos tiempo maximo permitido por la matriz para la carga inicial.
	 * @return true cuando titulo, Plaza y Tienda quedan interactivos dentro del tiempo esperado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean esperarPantallaDisponiblePlazaTienda(long inicioCarga, int tiempoMaximoSegundos) {
		try {
			long segundosTranscurridos = (System.currentTimeMillis() - inicioCarga) / 1000;
			long segundosRestantes = Math.max(1, tiempoMaximoSegundos - segundosTranscurridos);
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(segundosRestantes));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayEspereUnMomento));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.tituloPantalla));
			wait.until(ExpectedConditions.elementToBeClickable(elements.filtroPlaza));
			wait.until(ExpectedConditions.elementToBeClickable(elements.filtroTienda));
			return true;
		} catch (Exception exception) {
			testCase.addBoldTextEvidenceCurrentStep("La pantalla no quedo disponible para Plaza/Tienda dentro del tiempo maximo: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona desde el calendario el final del mes preparado a partir de una fecha con datos de BD.
	 * Para el mes actual selecciona hoy; para un mes anterior selecciona su ultimo dia.
	 * Valida que el datepicker cierre y que Consultar historico quede habilitado.
	 *
	 * @return true cuando la fecha final queda seleccionada y Consultar se habilita.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	public boolean seleccionaFechaFinHistoricaConDatosDesdeCalendario() {
		try {
			if (fechaInicioHistoricaBD == null || fechaFinHistoricaBD == null) {
				return error("No existe un rango historico dinamico preparado desde BD");
			}
			LocalDate fechaInicio = LocalDate.parse(fechaInicioHistoricaBD,
					DateTimeFormatter.ofPattern("d/M/yyyy"));
			LocalDate fechaFin = LocalDate.parse(fechaFinHistoricaBD,
					DateTimeFormatter.ofPattern("d/M/yyyy"));
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioHistorico));
			navegarCalendarioEntreMeses(wait, YearMonth.from(fechaInicio), YearMonth.from(fechaFin));
			seleccionarDiaCalendarioVisible(wait, fechaFin.getDayOfMonth());
			try {
				new WebDriverWait(u.getDriver(), Duration.ofSeconds(3))
						.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendarioHistorico));
			} catch (Exception calendarioVisible) {
				new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendarioHistorico));
			}
			String actual = valorCampo(elements.fechaFin);
			boolean consultarHabilitado = wait.until(driver -> !elementoDeshabilitado(
					driver.findElement(elements.botonConsultarHistoricoCualquierEstado)));
			boolean resultadoPaso = fechaVisibleCoincide(actual, fechaFin) && consultarHabilitado;
			return registrar(resultadoPaso,
					"Fecha Fin con datos seleccionada desde el calendario: " + actual
							+ " y Consultar habilitado",
					"Fecha Fin inesperada o Consultar deshabilitado. Esperada: " + fechaFinHistoricaBD
							+ " | Actual: " + actual + " | Consultar habilitado: " + consultarHabilitado,
					"Fecha_Fin_Dinamica_Calendario");
		} catch (Exception exception) {
			return error("No fue posible seleccionar la Fecha Fin dinamica: " + exception.getMessage());
		}
	}

	/**
	 * Valida que Plaza, Tienda, Selector de Ruta y Ruta queden disponibles y sin el mensaje No items found.
	 *
	 * @return true cuando todos los filtros requeridos cargan correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean validaFiltrosCargaInicialSinNoItems(long inicioCarga, String user) {
		boolean plaza = abrirNgSelectConOpciones(elements.filtroPlaza, "Plaza", inicioCarga);
		boolean tienda = abrirNgSelectConOpciones(elements.filtroTienda, "Tienda", inicioCarga);
		boolean selectorRuta = abrirSelectorRutaConOpciones(inicioCarga);
		esperarInputRutaHabilitado(inicioCarga);
		WebElement inputRuta = u.getDriver().findElement(elements.inputRuta);
		boolean rutaVisible = inputRuta.isDisplayed();
		boolean rutaHabilitada = inputRuta.isEnabled();
		boolean ruta = rutaVisible && rutaHabilitada;
		resumenInputRuta = "Visible: " + rutaVisible + " | Habilitado: " + rutaHabilitada;
		if (!ruta) { testCase.addBoldTextEvidenceCurrentStep("El campo Ruta no esta visible o habilitado"); }
		actualizarResumenGridDesdeBD(user, inicioCarga);
		return plaza && tienda && selectorRuta && ruta;
	}

	/**
	 * Abre un control ng-select y valida que contenga opciones reales sin mostrar No items found.
	 *
	 * @param filtro selector del control ng-select que se validara.
	 * @param nombreFiltro nombre funcional del filtro para la evidencia.
	 * @return true cuando el filtro despliega opciones y no muestra No items found.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean abrirNgSelectConOpciones(By filtro, String nombreFiltro, long inicioCarga) {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(filtro)).click();
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(10)).until(driver ->
					!driver.findElements(elements.opcionNgSelectVisible).isEmpty() || noItemsFoundVisible());
			boolean tieneOpciones = !u.getDriver().findElements(elements.opcionNgSelectVisible).isEmpty();
			boolean sinNoItemsFound = !noItemsFoundVisible();
			String resumenFiltro = "Opciones disponibles: " + tieneOpciones + " | Sin No items found: " + sinNoItemsFound;
			if ("Plaza".equals(nombreFiltro)) {
				resumenFiltroPlaza = resumenFiltro;
			} else if ("Tienda".equals(nombreFiltro)) {
				resumenFiltroTienda = resumenFiltro;
			}
			u.getDriver().switchTo().activeElement().sendKeys(Keys.ESCAPE);
			testCase.addBoldTextEvidenceCurrentStep("Filtro " + nombreFiltro + ": opciones disponibles=" + tieneOpciones + " | sin No items found=" + sinNoItemsFound);
			return tieneOpciones && sinNoItemsFound;
		} catch (Exception exception) {
			testCase.addBoldTextEvidenceCurrentStep("No fue posible validar el filtro " + nombreFiltro + ": " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Navega el calendario Material desde un mes conocido hasta el mes objetivo.
	 *
	 * @param mesOrigen mes actualmente visible en el calendario.
	 * @param mesObjetivo mes que debe quedar visible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private void navegarCalendarioEntreMeses(WebDriverWait wait, YearMonth mesOrigen,
			YearMonth mesObjetivo) {
		int diferenciaMeses = (mesObjetivo.getYear() - mesOrigen.getYear()) * 12
				+ mesObjetivo.getMonthValue() - mesOrigen.getMonthValue();
		By botonAnterior = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-previous-button')]");
		By botonSiguiente = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-next-button')]");
		By botonNavegacion = diferenciaMeses < 0 ? botonAnterior : botonSiguiente;
		for (int indice = 0; indice < Math.abs(diferenciaMeses); indice++) {
			wait.until(ExpectedConditions.elementToBeClickable(botonNavegacion)).click();
			u.hardWait(1);
		}
	}

	/**
	 * Abre Selector de Ruta y valida que muestre las opciones PMO y Dinamica sin No items found.
	 *
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean abrirSelectorRutaConOpciones(long inicioCarga) {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(elements.selectorRutaControl)).click();
			boolean muestraPMO = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(elements.opcionSelectorRutaPMO)).isDisplayed();
			WebElement opcionDinamica = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(elements.opcionSelectorRutaDinamica));
			boolean muestraDinamica = opcionDinamica.isDisplayed();
			boolean sinNoItemsFound = !noItemsFoundVisible();
			resumenSelectorRuta = "PMO: " + muestraPMO + " | Dinamica: " + muestraDinamica + " | Sin No items found: " + sinNoItemsFound;
			if (muestraDinamica) {
				opcionDinamica.click();
			} else {
				u.getDriver().switchTo().activeElement().sendKeys(Keys.ESCAPE);
			}
			testCase.addBoldTextEvidenceCurrentStep("Selector de Ruta: PMO=" + muestraPMO + " | Dinamica=" + muestraDinamica + " | sin No items found=" + sinNoItemsFound);
			return muestraPMO && muestraDinamica && sinNoItemsFound;
		} catch (Exception exception) {
			testCase.addBoldTextEvidenceCurrentStep("No fue posible validar Selector de Ruta: " + exception.getMessage());
			return false;
		}
	}
	/**
	 * Recorre todas las paginas del grid y conserva transferencias y fechas en el orden mostrado.
	 * Adjunta una captura por pagina y compara posteriormente las filas procesadas con el total del paginador.
	 * @param evidenciaPrefijo prefijo usado para identificar las capturas de cada pagina.
	 * @return snapshot con valores, total reportado y cantidad de paginas procesadas.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private GridCompletoSnapshot recopilarGridCompleto(String evidenciaPrefijo) {
		GridCompletoSnapshot snapshot = new GridCompletoSnapshot();
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
		posicionarPrimeraPaginaGrid(wait);
		int pagina = 1;
		while (pagina <= 100) {
			wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(elements.gridFilas, 0));
			esperarFinCarga();
			List<WebElement> transferenciasPagina = u.getDriver()
					.findElements(elements.celdasTransferenciaGrid);
			List<WebElement> fechasPagina = u.getDriver().findElements(elements.celdasFechaGrid);
			for (WebElement transferencia : transferenciasPagina) {
				snapshot.transferencias.add(transferencia.getText().trim());
			}
			for (WebElement fecha : fechasPagina) {
				snapshot.fechas.add(fecha.getText().trim());
			}
			snapshot.paginas = pagina;
			testCase.addBoldTextEvidenceCurrentStep("Pagina " + pagina + " procesada | Registros: "
					+ transferenciasPagina.size() + " | Transferencias: "
					+ textosElementos(transferenciasPagina));
			registrarConResaltado(true,
					"Pagina " + pagina + " del grid procesada correctamente",
					"No fue posible procesar la pagina " + pagina,
					evidenciaPrefijo + "_Pagina_" + pagina,
					elements.celdasTransferenciaGrid, elements.celdasFechaGrid,
					elements.contadorGrid);

			List<WebElement> botonesSiguiente = u.getDriver()
					.findElements(elements.botonSiguientePaginaGrid);
			if (botonesSiguiente.isEmpty() || elementoDeshabilitado(botonesSiguiente.get(0))) {
				break;
			}
			String contadorAnterior = textoContadorGrid();
			botonesSiguiente.get(0).click();
			esperarFinCarga();
			wait.until(driver -> !textoContadorGrid().equals(contadorAnterior));
			pagina++;
		}
		snapshot.totalReportado = obtenerTotalContadorGrid();
		if (snapshot.totalReportado < 0) {
			snapshot.totalReportado = snapshot.transferencias.size();
		}
		return snapshot;
	}

	/**
	 * Regresa el paginador del grid a la primera pagina cuando el control esta disponible.
	 *
	 * @param wait espera explicita usada para confirmar el cambio de pagina.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private void posicionarPrimeraPaginaGrid(WebDriverWait wait) {
		List<WebElement> botonesPrimero = u.getDriver().findElements(elements.botonPrimeraPaginaGrid);
		if (!botonesPrimero.isEmpty() && !elementoDeshabilitado(botonesPrimero.get(0))) {
			String contadorAnterior = textoContadorGrid();
			botonesPrimero.get(0).click();
			esperarFinCarga();
			wait.until(driver -> !textoContadorGrid().equals(contadorAnterior));
			return;
		}
		for (int pagina = 0; pagina < 100; pagina++) {
			List<WebElement> botonesAnterior = u.getDriver()
					.findElements(elements.botonAnteriorPaginaGrid);
			if (botonesAnterior.isEmpty() || elementoDeshabilitado(botonesAnterior.get(0))) {
				return;
			}
			String contadorAnterior = textoContadorGrid();
			botonesAnterior.get(0).click();
			esperarFinCarga();
			wait.until(driver -> !textoContadorGrid().equals(contadorAnterior));
		}
	}

	/**
	 * Revisa si existe visible el mensaje No items found en algun desplegable activo.
	 *
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean noItemsFoundVisible() {
		return u.getDriver().findElements(elements.mensajeNoItemsFound).stream().anyMatch(WebElement::isDisplayed);
	}

	/**
	 * Espera brevemente a que el campo Ruta quede habilitado despues de seleccionar la opcion Dinamica.
	 *
	 * @param inicioCarga marca de tiempo en milisegundos registrada antes del clic en Recepcion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private void esperarInputRutaHabilitado(long inicioCarga) {
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(elements.inputRuta));
		} catch (Exception exception) {
			testCase.addBoldTextEvidenceCurrentStep("El campo Ruta no quedo habilitado despues de seleccionar Dinamica: " + exception.getMessage());
		}
	}

	/**
	 * Valida mediante JavaScript si el modal o su contenedor de overlay cubre visualmente la pantalla.
	 *
	 * @param overlay elemento visible que contiene el texto Espere un momento.
	 * @return true cuando existe backdrop activo o el contenedor ocupa la mayor parte del viewport.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean overlayCubreVista(WebElement overlay) {
		try {
			Object result = ((JavascriptExecutor) u.getDriver()).executeScript(
					"const node = arguments[0].closest('mat-dialog-container, .cdk-overlay-pane, .cdk-overlay-container, .cdk-global-overlay-wrapper') || arguments[0]; const r = node.getBoundingClientRect(); const backdrop = document.querySelector('.cdk-overlay-backdrop.cdk-overlay-backdrop-showing, .modal-backdrop.show'); return !!backdrop || (r.width >= window.innerWidth * 0.6 && r.height >= window.innerHeight * 0.6);", overlay);
			return Boolean.TRUE.equals(result);
		} catch (Exception exception) {
			return true;
		}
	}

	/**
	 * Reinicia los valores acumulados para generar la tabla de resumen de la carga inicial.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private void reiniciarResumenCargaInicial() {
		ultimoModalFueVisible = false;
		ultimoModalCubrePantalla = false;
		resumenFiltroPlaza = "No validado";
		resumenFiltroTienda = "No validado";
		resumenSelectorRuta = "No validado";
		resumenInputRuta = "No validado";
		resumenGridRecepcion = "No validado";
	}

	/**
	 * Agrega al reporte una tabla descriptiva con tiempos, modal, filtros y grid de Recepcion.
	 *
	 * @param textoT1 fecha y hora de inicio de carga.
	 * @param textoT2 fecha y hora de fin de carga.
	 * @param textoTiempoCarga duracion total de la carga en segundos.
	 * @param tiempoMaximoSegundos tiempo maximo esperado por la matriz.
	 * @param pantallaDisponible true cuando la pantalla quedo disponible dentro del tiempo esperado.
	 * @param filtrosCorrectos true cuando los filtros requeridos cargaron correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private void agregarTablaResumenCargaInicial(String textoT1, String textoT2, String textoTiempoCarga,
			int tiempoMaximoSegundos, boolean pantallaDisponible, boolean filtrosCorrectos) {
		String estadoModal = ultimoModalFueVisible
				? "Visible | Cubre pantalla: " + ultimoModalCubrePantalla
				: "No perceptible por carga inmediata";
		testCase.addBoldTextEvidenceCurrentStep("Resumen de carga inicial Recepcion de Transferencias y Devoluciones");
		agregarFilaResumen("T1 inicio", textoT1);
		agregarFilaResumen("T2 fin", textoT2);
		agregarFilaResumen("T_carga", textoTiempoCarga + " segundos / Maximo " + tiempoMaximoSegundos + " segundos");
		agregarFilaResumen("Pantalla disponible", String.valueOf(pantallaDisponible));
		agregarFilaResumen("Modal", estadoModal);
		agregarFilaResumen("Filtro Plaza", resumenFiltroPlaza);
		agregarFilaResumen("Filtro Tienda", resumenFiltroTienda);
		agregarFilaResumen("Selector Ruta", resumenSelectorRuta);
		agregarFilaResumen("Campo Ruta", resumenInputRuta);
		agregarFilaResumen("Grid BD", resumenGridRecepcion);
	}

	/**
	 * Agrega una fila de resumen en formato estable para PDF.
	 *
	 * @param nombre nombre de la validacion.
	 * @param valor resultado de la validacion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private void agregarFilaResumen(String nombre, String valor) {
		testCase.addTextEvidenceCurrentStep(nombre + ": " + limpiarTextoResumen(valor));
	}

	/**
	 * Limpia saltos de linea para que cada fila del resumen se mantenga legible en PDF.
	 *
	 * @param texto texto original.
	 * @return texto en una sola linea.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private String limpiarTextoResumen(String texto) {
		return texto == null ? "" : texto.replace("\r", " ").replace("\n", " ").trim();
	}

	/**
	 * Actualiza el resumen de grid con el total de registros consultado desde BD.
	 * Para ADMIN consulta el total global; para CEDIS filtra por PCSUPPLIERID.
	 *
	 * @param user usuario con el que se determina si el conteo es global o por PCSUPPLIERID.
	 * @param inicioCarga marca de tiempo en milisegundos registrada antes del clic en Recepcion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private void actualizarResumenGridDesdeBD(String user, long inicioCarga) {
		if (user == null || user.trim().isEmpty()) {
			resumenGridRecepcion = "No validado en BD: usuario no recibido";
			return;
		}

		try {
			if ("5555555".equals(user.trim())) {
				long totalRegistros = obtenerTotalRegistrosGridRtdGlobal();
				resumenGridRecepcion = "Perfil ADMIN | Total registros BD: " + totalRegistros;
				return;
			}

			String supplierId = obtenerSupplierIdUsuario(user);
			long totalRegistros = obtenerTotalRegistrosGridRtdPorSupplierId(supplierId);
			resumenGridRecepcion = "SupplierId: " + supplierId + " | Total registros BD: " + totalRegistros;
		} catch (Exception exception) {
			resumenGridRecepcion = "No validado en BD: " + exception.getMessage();
		}
	}
	/**
	 * Obtiene el texto visible del contador del grid.
	 *
	 * @return texto del paginador o cadena vacia cuando no existe.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private String textoContadorGrid() {
		List<WebElement> contadores = u.getDriver().findElements(elements.contadorGrid);
		return contadores.isEmpty() ? "" : contadores.get(0).getText().trim();
	}

	/**
	 * Extrae el total del texto de paginacion Material, por ejemplo "1 - 5 de 12".
	 *
	 * @return total reportado o -1 cuando el formato no contiene un total reconocible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private int obtenerTotalContadorGrid() {
		Matcher matcher = Pattern.compile("(?:de|of)\\s+(\\d+)", Pattern.CASE_INSENSITIVE)
				.matcher(textoContadorGrid());
		return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
	}

	/**
	 * Convierte una lista de elementos en sus textos visibles para evidencia.
	 *
	 * @param elementos elementos de la pagina que se deben documentar.
	 * @return lista con textos sin espacios laterales.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private List<String> textosElementos(List<WebElement> elementos) {
		List<String> textos = new ArrayList<String>();
		for (WebElement elemento : elementos) {
			textos.add(elemento.getText().trim());
		}
		return textos;
	}

	/**
	 * Valida orden descendente sobre todas las fechas recopiladas, incluidas fronteras entre paginas.
	 *
	 * @param fechas textos de fecha en el orden global mostrado por el grid.
	 * @return true cuando cada fecha es igual o anterior a la precedente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private boolean fechasEnOrdenDescendente(List<String> fechas) {
		if (fechas.isEmpty()) {
			return false;
		}
		LocalDateTime anterior = null;
		for (String fecha : fechas) {
			LocalDateTime actual = parseFecha(fecha);
			if (actual == null || (anterior != null && actual.isAfter(anterior))) {
				return false;
			}
			anterior = actual;
		}
		return true;
	}

	/**
	 * Datos recopilados al recorrer todas las paginas del grid.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	private static class GridCompletoSnapshot {
		private final List<String> transferencias = new ArrayList<String>();
		private final List<String> fechas = new ArrayList<String>();
		private int totalReportado;
		private int paginas;
	}

	/**
	 * Obtiene desde BD una transferencia proyectada consultable con alcance ADMIN global.
	 * Conserva el valor como Transferencia A para reutilizarlo en la consulta de filtros rapidos.
	 *
	 * @return true cuando BD devuelve y documenta una transferencia proyectada no vacia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-23
	 */
	public boolean descubreTransferenciaProyectadaGlobal() {
		try {
			transferenciaA = new CometaBdValidationHelper(testCase)
					.obtenerTransferenciaProyectadaGlobal();
			boolean resultadoPaso = transferenciaA != null && !transferenciaA.isEmpty();
			return registrar(resultadoPaso,
					"Numero de transferencia proyectada documentado para ADMIN: " + transferenciaA,
					"No se encontro una transferencia proyectada consultable con alcance ADMIN global",
					"Transferencia_Proyectada_Global_BD");
		} catch (Exception exception) {
			return error("No fue posible descubrir una transferencia proyectada global: "
					+ exception.getMessage());
		}
	}

	/**
	 * Obtiene el supplierId del usuario desde xxcm_user_keys usando la key PCSUPPLIERID.
	 *
	 * @param user usuario de Portal COMETA.
	 * @return supplierId configurado para el usuario.
	 * @throws Exception si no existe supplierId o no se puede consultar BD.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private String obtenerSupplierIdUsuario(String user) throws Exception {
		String sql = "select key_value "
				+ "from xxcm_user_keys "
				+ "where short_name = 'PCSUPPLIERID' "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) "
				+ "limit 1";

		try (Connection connection = abrirConexionCometa();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, user);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String supplierId = resultSet.getString("key_value");
					if (supplierId != null && !supplierId.trim().isEmpty()) {
						return supplierId.trim();
					}
				}
			}
		}

		throw new Exception("No se obtuvo PCSUPPLIERID para el usuario " + user);
	}

	/**
	 * Obtiene el total de registros del grid RTD desde BD para el supplierId indicado.
	 *
	 * @param supplierId proveedor_id que se usa como filtro de la consulta.
	 * @return total de registros encontrados en xxcm_cedis_rtd_proyeccion_grid.
	 * @throws Exception si no se puede ejecutar el conteo.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private long obtenerTotalRegistrosGridRtdPorSupplierId(String supplierId) throws Exception {
		String sql = "select count(*) as total_registros "
				+ "from xxcm_cedis_rtd_proyeccion_grid a1 "
				+ "inner join xxcm_cedis_rtd_proyeccion a2 on a1.id = a2.id "
				+ "where a2.proveedor_id = ?";

		try (Connection connection = abrirConexionCometa();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, Long.parseLong(supplierId));
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getLong("total_registros");
				}
			}
		}

		throw new Exception("La consulta COUNT no retorno filas para supplierId " + supplierId);
	}

	/**
	 * Obtiene el total global de registros del grid RTD desde BD para perfil ADMIN.
	 * ADMIN puede visualizar todos los registros, por lo que no se aplica filtro por proveedor_id.
	 *
	 * @return total de registros encontrados en xxcm_cedis_rtd_proyeccion_grid.
	 * @throws Exception si no se puede ejecutar el conteo.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private long obtenerTotalRegistrosGridRtdGlobal() throws Exception {
		String sql = "select count(*) as total_registros "
				+ "from xxcm_cedis_rtd_proyeccion_grid a1 "
				+ "inner join xxcm_cedis_rtd_proyeccion a2 on a1.id = a2.id";

		try (Connection connection = abrirConexionCometa();
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next()) {
				return resultSet.getLong("total_registros");
			}
		}

		throw new Exception("La consulta COUNT global no retorno filas");
	}

	/**
	 * Abre conexion JDBC a PostgreSQL Cometa con las variables globales del framework.
	 *
	 * @return conexion JDBC activa.
	 * @throws SQLException si no se puede establecer conexion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private Connection abrirConexionCometa() throws SQLException {
		return DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa,
				GlobalVariables.DB_PASSWORD_Cometa);
	}

	/**
	 * Valida que el mensaje No items found aparezca en el filtro Tienda solo despues de que la carga inicial finalizo.
	 * Selecciona una Plaza disponible, escribe un valor inexistente en Tienda y confirma que no exista overlay activo al mostrar el mensaje.
	 *
	 * @param valorInexistente valor que se escribira en el filtro Tienda para forzar ausencia de resultados.
	 * @return true si No items found aparece con la carga finalizada y sin overlay activo.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	public boolean validarNoItemsFoundTiendaPostCarga(String valorInexistente) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayEspereUnMomento));

			WebElement plaza = wait.until(ExpectedConditions.elementToBeClickable(elements.filtroPlaza));
			plaza.click();
			WebElement opcionPlaza = wait.until(ExpectedConditions.elementToBeClickable(elements.primeraOpcionPlaza));
			String plazaSeleccionada = opcionPlaza.getText() == null ? "" : opcionPlaza.getText().trim().replaceAll("\\s+", " ");
			opcionPlaza.click();
			testCase.addTextEvidenceCurrentStep("Plaza seleccionada para provocar busqueda sin resultados en Tienda: " + plazaSeleccionada);

			WebElement tienda = wait.until(ExpectedConditions.elementToBeClickable(elements.filtroTienda));
			tienda.click();
			WebElement inputTienda = obtenerInputFiltroRecepcion(elements.filtroTienda);
			inputTienda.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			inputTienda.sendKeys(valorInexistente);

			boolean noItemsVisible = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(driver -> driver.findElements(elements.mensajeNoItemsFound).stream().anyMatch(WebElement::isDisplayed));
			boolean overlayCerrado = u.getDriver().findElements(elements.overlayEspereUnMomento).stream().noneMatch(WebElement::isDisplayed);
			testCase.addTextEvidenceCurrentStep("Valor inexistente escrito en Tienda: " + valorInexistente);
			testCase.addTextEvidenceCurrentStep("No items found visible: " + noItemsVisible + " | Overlay cerrado: " + overlayCerrado);
			try { testCase.addScreenShotCurrentStep("No_Items_Found_Tienda_Post_Carga"); } catch (Exception ignore) { }
			return noItemsVisible && overlayCerrado;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al validar No items found en Tienda post carga: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene el input visible de un filtro ng-select de Recepcion.
	 *
	 * @param filtroBy localizador del filtro donde se buscara el input.
	 * @return input visible del filtro o del panel activo.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private WebElement obtenerInputFiltroRecepcion(By filtroBy) {
		WebElement filtro = u.getDriver().findElement(filtroBy);
		for (WebElement input : filtro.findElements(By.xpath(".//input"))) {
			if (input.isDisplayed()) {
				return input;
			}
		}
		for (WebElement input : u.getDriver().findElements(By.xpath("//ng-dropdown-panel//input"))) {
			if (input.isDisplayed()) {
				return input;
			}
		}
		throw new org.openqa.selenium.NoSuchElementException("No se encontro input activo para el filtro de Recepcion.");
	}

	/**
	 * Obtiene el contexto de paso actual para el log funcional del Object Model.
	 * Intenta leer el numero del paso desde el framework y, si no existe, usa un contador local secuencial.
	 *
	 * @return texto con formato Paso N para mostrar en consola y evidencia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-27
	 */
	private String obtenerContextoPasoActual() {
		Integer numeroPasoFramework = obtenerNumeroPasoFramework();
		if (numeroPasoFramework != null && numeroPasoFramework.intValue() > 0) {
			contadorPasoFallback = numeroPasoFramework.intValue();
			return "Paso " + numeroPasoFramework;
		}
		contadorPasoFallback++;
		return "Paso " + contadorPasoFallback;
	}

	/**
	 * Intenta recuperar por reflexion el numero de paso actual mantenido por el framework.
	 *
	 * @return numero de paso detectado o null cuando no existe API/campo compatible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-27
	 */
	private Integer obtenerNumeroPasoFramework() {
		try {
			String[] nombresMetodo = { "getCurrentStepNumber", "getCurrentStep", "getStepNumber",
					"getCurrentStepIndex", "currentStep", "getPasoActual", "getNumeroPasoActual" };
			for (String nombreMetodo : nombresMetodo) {
				Integer numero = invocarMetodoNumero(testCase, nombreMetodo);
				if (numero != null && numero.intValue() > 0) {
					return numero;
				}
			}
			String[] nombresCampo = { "currentStepNumber", "currentStep", "stepNumber", "stepIndex",
					"pasoActual", "numeroPasoActual" };
			for (String nombreCampo : nombresCampo) {
				Integer numero = leerCampoNumero(testCase, nombreCampo);
				if (numero != null && numero.intValue() > 0) {
					return numero;
				}
			}
		} catch (Exception ignored) {
			// Fallback controlado al contador local cuando el framework no expone el paso.
		}
		return null;
	}

	/**
	 * Invoca un metodo sin parametros y convierte su resultado a numero de paso.
	 *
	 * @param instancia objeto donde se busca el metodo.
	 * @param nombreMetodo nombre del metodo candidato.
	 * @return numero de paso detectado o null cuando no aplica.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-27
	 */
	private Integer invocarMetodoNumero(Object instancia, String nombreMetodo) {
		try {
			Method metodo = instancia.getClass().getMethod(nombreMetodo);
			Object valor = metodo.invoke(instancia);
			return convertirANumeroPaso(valor);
		} catch (Exception ignored) {
			return null;
		}
	}

	/**
	 * Lee un campo por reflexion y convierte su valor a numero de paso.
	 *
	 * @param instancia objeto donde se busca el campo.
	 * @param nombreCampo nombre del campo candidato.
	 * @return numero de paso detectado o null cuando no aplica.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-27
	 */
	private Integer leerCampoNumero(Object instancia, String nombreCampo) {
		Class<?> tipo = instancia.getClass();
		while (tipo != null) {
			try {
				Field campo = tipo.getDeclaredField(nombreCampo);
				campo.setAccessible(true);
				Object valor = campo.get(instancia);
				return convertirANumeroPaso(valor);
			} catch (Exception ignored) {
				tipo = tipo.getSuperclass();
			}
		}
		return null;
	}

	/**
	 * Convierte diferentes tipos de valor al numero de paso entero.
	 *
	 * @param valor valor devuelto por el framework.
	 * @return numero de paso cuando se puede interpretar, de lo contrario null.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-27
	 */
	private Integer convertirANumeroPaso(Object valor) {
		if (valor == null) {
			return null;
		}
		if (valor instanceof Number) {
			return Integer.valueOf(((Number) valor).intValue());
		}
		if (valor instanceof String) {
			Matcher matcher = Pattern.compile("(\\\\d+)").matcher((String) valor);
			if (matcher.find()) {
				return Integer.valueOf(Integer.parseInt(matcher.group(1)));
			}
		}
		return null;
	}

	/**
	 * Selecciona en UI una Plaza con informacion historica obtenida desde BD.
	 * Usa el alcance del perfil del usuario para resolver la Plaza y luego la busca
	 * en el ng-select de Filtros de Busqueda Historica con reintentos controlados.
	 *
	 * @param usuario usuario usado para resolver la Plaza con datos desde BD.
	 * @return true cuando la Plaza obtenida desde BD queda seleccionada en el filtro.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-27
	 */
	public boolean seleccionaPlazaConDatosDesdeBD(String usuario) {
		try {
			usuarioContextoActual = usuario;
			String plazaBd = new CometaBdValidationHelper(testCase).obtenerPlazaHistoricaConDatos(usuario);
			if (plazaBd == null || plazaBd.trim().isEmpty()) {
				return error("No fue posible obtener desde BD una Plaza con informacion para el usuario " + usuario);
			}

			esperarFinCarga();
			By backdropActivo = By.cssSelector(".cdk-overlay-backdrop.cdk-overlay-backdrop-showing");
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(25));
			Exception ultimoError = null;
			boolean plazaSeleccionada = false;
			String plazaSeleccionadaUi = "";

			for (int intento = 1; intento <= 3 && !plazaSeleccionada; intento++) {
				try {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(backdropActivo));
					WebElement filtroPlaza = wait.until(ExpectedConditions.elementToBeClickable(elements.filtroPlaza));
					((JavascriptExecutor) u.getDriver()).executeScript(
							"arguments[0].scrollIntoView({block:'center', inline:'center'});", filtroPlaza);
					u.hardWait(1);
					filtroPlaza.click();

					WebElement inputPlaza = obtenerInputFiltroRecepcion(elements.filtroPlaza);
					inputPlaza.sendKeys(Keys.chord(Keys.CONTROL, "a"));
					inputPlaza.sendKeys(Keys.DELETE);
					inputPlaza.sendKeys(plazaBd);

					wait.until(driver -> !driver.findElements(elements.opcionNgSelectVisible).isEmpty()
							|| noItemsFoundVisible());

					List<WebElement> opciones = u.getDriver().findElements(elements.opcionNgSelectVisible);
					if (opciones.isEmpty()) {
						throw new Exception("No se encontraron opciones visibles de Plaza tras buscar: " + plazaBd);
					}

					WebElement opcionCoincidente = null;
					String plazaBdNormalizada = normalizar(plazaBd);
					for (WebElement opcion : opciones) {
						String textoOpcion = opcion.getText() == null ? "" : opcion.getText().trim();
						if (normalizar(textoOpcion).contains(plazaBdNormalizada)) {
							opcionCoincidente = opcion;
							plazaSeleccionadaUi = textoOpcion;
							break;
						}
					}

					if (opcionCoincidente == null) {
						throw new Exception("Ninguna opcion visible coincide con la Plaza de BD: " + plazaBd);
					}

					((JavascriptExecutor) u.getDriver()).executeScript(
							"arguments[0].scrollIntoView({block:'nearest', inline:'nearest'});", opcionCoincidente);
					u.hardWait(1);
					opcionCoincidente.click();
					wait.until(ExpectedConditions.invisibilityOfElementLocated(backdropActivo));
					plazaSeleccionada = true;
				} catch (Exception exception) {
					ultimoError = exception;
					new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
					u.hardWait(1);
				}
			}

			if (!plazaSeleccionada) {
				String detalle = ultimoError == null ? "sin detalle" : normalizarTextoDiagnostico(ultimoError.getMessage());
				return error("No fue posible seleccionar en UI la Plaza obtenida desde BD ('" + plazaBd
						+ "'). Detalle: " + detalle);
			}

			return registrar(plazaSeleccionada,
					"El filtro Plaza selecciona la opcion con datos obtenida desde BD: " + plazaSeleccionadaUi,
					"No fue posible seleccionar la Plaza obtenida desde BD", "Plaza_BD_Seleccionada_Recepcion");
		} catch (Exception exception) {
			return error("No fue posible seleccionar Plaza con datos desde BD: " + exception.getMessage());
		}
	}

	/**
	 * Ejecuta la consulta historica y aplica una recuperacion automatica cuando no hay filas.
	 * Primero consulta con los filtros actuales; si no hay datos, limpia filtros historicos,
	 * recaptura el rango dinamico desde BD para el usuario y vuelve a consultar.
	 *
	 * @param usuario usuario usado para reconstruir el rango dinamico desde BD.
	 * @return true cuando la consulta historica muestra filas en alguno de los intentos.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-27
	 */
	public boolean consultaHistoricaConResultadosConRecuperacion(String usuario) {
		try {
			boolean consultaInicial = consultaHistoricaConResultados();
			if (consultaInicial) {
				return registrar(true,
						"La consulta historica mostro resultados en el primer intento",
						"",
						"Consulta_Historica_Recuperacion_OK_PrimerIntento");
			}

			testCase.addBoldTextEvidenceCurrentStep(
					"Reintento automatico: sin resultados con filtros actuales. Se limpiaran filtros historicos, se recapturara rango dinamico y se consultara nuevamente.");
			limpiaFiltrosHistoricos();
			boolean rangoRecapturado = capturaRangoHistoricoConDatos(usuario);
			boolean consultaReprocesada = rangoRecapturado && consultaHistoricaConResultados();
			boolean resultadoPaso = consultaReprocesada;
			String motivoFallo = rangoRecapturado
					? "La consulta historica sigue sin mostrar registros incluso despues del reintento automatico"
					: "No fue posible recapturar el rango dinamico desde BD para el reintento";

			return registrar(resultadoPaso,
					"La consulta historica mostro registros despues del reintento automatico",
					motivoFallo,
					"Consulta_Historica_Recuperacion_Resultado");} catch (Exception exception) {
					    return error("No fue posible ejecutar la consulta historica con recuperacion automatica: "
					            + exception.getMessage());
					}
	}
	/**
	 * Valida que el boton Liquidacion de Ruta esté a la izquierda y Devoluciones Especiales a la derecha.
	 *
	 * @return true si el orden de los botones es correcto (Liquidacion izquierda, Devoluciones derecha).
	 *
	 * @author Mauricio Gurrola
	 * @date Agosto 2026
	 */
	public boolean validarOrdenBotonesSuperiores() {
		try {
			WebElement liquidacion = u.getDriver().findElement(elements.botonLiquidacionRuta);
			WebElement devoluciones = u.getDriver().findElement(elements.botonDevolucionesEspeciales);
			
			// Obtener posiciones X de ambos botones (cast a Number por si JS devuelve Long o Double)
			double posicionXLiquidacion = ((Number) ((JavascriptExecutor) u.getDriver())
					.executeScript("return arguments[0].getBoundingClientRect().left;", liquidacion)).doubleValue();
			double posicionXDevoluciones = ((Number) ((JavascriptExecutor) u.getDriver())
					.executeScript("return arguments[0].getBoundingClientRect().left;", devoluciones)).doubleValue();
			
			// Validar que Liquidacion esté a la izquierda de Devoluciones
			boolean resultOrden = posicionXLiquidacion < posicionXDevoluciones;
			
			return registrarConResaltado(resultOrden,
					"El boton Liquidacion de Ruta se ubica a la izquierda y el boton Devoluciones Especiales a la derecha",
					"El orden de los botones no es el esperado: Liquidacion debe estar a la izquierda", 
					"Orden_Botones_Verificado",
					elements.botonLiquidacionRuta, elements.botonDevolucionesEspeciales);
		} catch (Exception exception) { 
			return error("No fue posible validar el orden de los botones: " + exception.getMessage()); 
		}
	}

	/**
	 * Valida que el boton Devoluciones Especiales tenga el mismo diseno visual que Liquidacion de Ruta.
	 * Compara propiedades CSS como backgroundColor, padding y fontSize.
	 *
	 * @return true si ambos botones tienen estilos visuales similares.
	 *
	 * @author Mauricio Gurrola
	 * @date Agosto 2026
	 */
	public boolean validarEstiloBotonesSuperiores() {
		try {
			WebElement liquidacion = u.getDriver().findElement(elements.botonLiquidacionRuta);
			WebElement devoluciones = u.getDriver().findElement(elements.botonDevolucionesEspeciales);
			
			// Obtener estilos CSS de ambos botones
			String estiloLiquidacion = liquidacion.getAttribute("style");
			String estiloDevoluciones = devoluciones.getAttribute("style");
			
			// Comparar clases CSS (atributo class)
			String claseLiquidacion = liquidacion.getAttribute("class");
			String claseDevoluciones = devoluciones.getAttribute("class");
			
			// Validar que ambos botones tengan altura similar (cast a Number por si JS devuelve Long o Double)
			double altoLiquidacion = ((Number) ((JavascriptExecutor) u.getDriver())
					.executeScript("return arguments[0].getBoundingClientRect().height;", liquidacion)).doubleValue();
			double altoDevoluciones = ((Number) ((JavascriptExecutor) u.getDriver())
					.executeScript("return arguments[0].getBoundingClientRect().height;", devoluciones)).doubleValue();
			
			// Permitir pequeña diferencia por redondeo (5px de tolerancia)
			boolean altosComparables = Math.abs(altoLiquidacion - altoDevoluciones) < 5;
			
			// Validar que ambos sean elementos button (mismo tipo)
			boolean sonBotones = "button".equalsIgnoreCase(liquidacion.getTagName()) 
					&& "button".equalsIgnoreCase(devoluciones.getTagName());
			
			boolean resultEstilo = altosComparables && sonBotones;
			
			return registrarConResaltado(resultEstilo,
					"Ambos botones presentan el mismo diseno visual, tamano y comportamiento",
					"Los botones no tienen el mismo diseno visual o no son elementos del mismo tipo",
					"Estilo_Botones_Verificado",
					elements.botonLiquidacionRuta, elements.botonDevolucionesEspeciales);
		} catch (Exception exception) { 
			return error("No fue posible validar el estilo de los botones: " + exception.getMessage()); 
		}
	}

	/**
	 * Navega a la pantalla Recepcion de Transferencias y Devoluciones y espera que quede disponible
	 * para interactuar. Genera evidencia minima: solo screenshot de confirmacion de pantalla cargada,
	 * sin registrar tiempos T1/T2 ni detalle de filtros.
	 *
	 * @param tiempoMaximoSegundos tiempo maximo en segundos para que la pantalla quede disponible.
	 * @return true si la pantalla cargo y el boton Devoluciones Especiales quedo interactivo.
	 *
	 * @author Alicia Ceja
	 * @date Agosto 2026
	 */
	public boolean ingresarRecepcionYEsperarDisponible(int tiempoMaximoSegundos) {
		try {
			long inicioCarga = System.currentTimeMillis();
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.tarjetaRecepcionYDevolucion)).click();

			long segundosTranscurridos = (System.currentTimeMillis() - inicioCarga) / 1000;
			long segundosRestantes = Math.max(1, tiempoMaximoSegundos - segundosTranscurridos);
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(segundosRestantes));
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayEspereUnMomento));
			} catch (Exception ignore) {
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.tituloPantalla));
			wait.until(ExpectedConditions.elementToBeClickable(elements.botonDevolucionesEspeciales));

			boolean resultadoPaso = true;
			System.out.println("[RESULTADO PASO] PASSED - Recepcion de Transferencias y Devoluciones disponible");
			u.highLight(elements.tituloPantalla);
			try {
				testCase.addTextEvidenceCurrentStep("La pantalla Recepcion de Transferencias y Devoluciones cargo correctamente.");
				testCase.addScreenShotCurrentStep(u, "Recepcion_Transferencias_Devoluciones_Disponible");
			} finally {
				u.unhighLight(elements.tituloPantalla);
			}
			return resultadoPaso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Recepcion de Transferencias y Devoluciones no disponible | Motivo: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al esperar disponibilidad de Recepcion de Transferencias: " + exception.getMessage());
			return false;
		}
	}

}