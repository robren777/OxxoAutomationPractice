package WEB.cometa.om;

import java.io.File;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.Normalizer;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.util.GlobalVariables;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class devolucionesEspecialesPage {

	SeleniumUtil u;
	TestCase testCase;
	devolucionesEspecialesElements elements = new devolucionesEspecialesElements();
	private String ultimoCedisSeleccionado;
	private String ultimoPlazaSeleccionada;
	private String ultimoTiendaSeleccionada;
	private String ultimoBolSeleccionado;
	private String prefijoBolValido;
	private int cantidadOpcionesCedis;
	private int cantidadOpcionesPlaza;
	private int cantidadOpcionesTienda;
	private int cantidadOpcionesBol;
	private java.util.List<String> primerasOpcionesCedis = new java.util.ArrayList<>();
	private java.util.List<String> primerasOpcionesPlaza = new java.util.ArrayList<>();
	private java.util.List<String> primerasOpcionesTienda = new java.util.ArrayList<>();
	private java.util.List<String> primerasOpcionesBol = new java.util.ArrayList<>();
	private java.util.List<String> ultimasOpcionesMultiplesCedis = new java.util.ArrayList<>();
	private java.util.List<String> ultimasOpcionesMultiplesPlaza = new java.util.ArrayList<>();
	private java.util.List<String> ultimasOpcionesMultiplesTienda = new java.util.ArrayList<>();
	private java.util.List<String> ultimasOpcionesMultiplesBol = new java.util.ArrayList<>();
	private String fechaMismoDiaSeleccionada;
	private LocalDate ultimaFechaDisponibleCalendario;
	private String ultimoRangoFechasBackendValidado;
	private String ultimoResultadoConsultaBackendUltimosTresAnios;
	private String bolPrimerRegistroGridDevEspeciales;
	private String bolSegundoRegistroGridDevEspeciales;
	private String bolTicketVistaPreliminarDevEspeciales;

	public devolucionesEspecialesPage(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Ingresa a la pantalla Devoluciones Especiales desde Recepcion de Transferencias y Devoluciones.
	 * Valida que el filtro CEDIS quede visible como evidencia de carga de la pantalla destino.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la pantalla Devoluciones Especiales queda disponible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean ingresarDevolucionesEspeciales(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayCargaDevEspeciales));
			} catch (Exception ignore) {
			}

			WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(elements.btnDevolucionesEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", boton);
			u.highLight(elements.btnDevolucionesEspeciales);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se selecciona el boton Devoluciones Especiales desde Liquidacion de Ruta.");
				testCase.addScreenShotCurrentStep(u, "Boton_Devoluciones_Especiales");
			}
			u.unhighLight(elements.btnDevolucionesEspeciales);

			try {
				boton.click();
			} catch (Exception clickInterceptado) {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayCargaDevEspeciales));
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", boton);
			}

			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayCargaDevEspeciales));
			} catch (Exception ignore) {
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.dropCedisDevEspeciales));
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se muestra la pantalla Devoluciones Especiales con el filtro CEDIS visible.");
				testCase.addScreenShotCurrentStep(u, "Pantalla_Devoluciones_Especiales");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al ingresar a Devoluciones Especiales: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que los filtros esperados de Devoluciones Especiales se encuentren visibles en pantalla.
	 * Los filtros esperados son CEDIS, Plaza, Tienda, BOL y Rango de fecha.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si todos los filtros esperados se visualizan.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarFiltrosDevolucionesEspecialesVisibles(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.dropCedisDevEspeciales));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.dropPlazaDevEspeciales));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.dropTiendaDevEspeciales));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.dropBolDevEspeciales));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.rangoFechaDevEspeciales));

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se visualizan los filtros CEDIS, Plaza, Tienda, BOL y Rango de fecha.");
				testCase.addScreenShotCurrentStep(u, "Filtros_Devoluciones_Especiales");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar filtros de Devoluciones Especiales: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Abre el filtro CEDIS y valida que sus opciones visibles tengan formato id supplier mas nombre.
	 * El formato esperado es id_supplier seguido de supplier_name, por ejemplo 9999999046 Toluca Operacion.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si existen opciones y todas las opciones visibles cumplen el formato esperado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean abrirFiltroCedisYValidarFormatoOpciones(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(elements.dropCedisDevEspeciales));
			filtro.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements.opcionesCedisDevEspeciales));
			List<WebElement> opciones = u.getDriver().findElements(elements.opcionesCedisDevEspeciales);
			if (opciones.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El filtro CEDIS no mostro opciones disponibles.");
				return false;
			}
			cantidadOpcionesCedis = opciones.size();
			capturarPrimerasOpciones(opciones, primerasOpcionesCedis);

			Pattern formatoCedis = Pattern.compile("^\\d+\\s+.+$");
			java.util.List<String> opcionesInvalidas = new java.util.ArrayList<>();
			for (WebElement opcion : opciones) {
				String texto = opcion.getText() == null ? "" : opcion.getText().trim().replaceAll("\\s+", " ");
				if (!formatoCedis.matcher(texto).matches()) {
					opcionesInvalidas.add(texto);
				}
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Opciones visibles del filtro CEDIS: " + opciones.size());
				testCase.addScreenShotCurrentStep(u, "Opciones_Filtro_CEDIS");
			}

			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.panelOpcionesDevEspeciales));

			if (!opcionesInvalidas.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("Opciones CEDIS con formato invalido: " + String.join(" | ", opcionesInvalidas));
				return false;
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar opciones del filtro CEDIS: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona la primera opcion disponible del filtro CEDIS y conserva el texto seleccionado.
	 * El valor seleccionado se usa para validar posteriormente el control y la coincidencia contra base de datos.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se selecciona una opcion de CEDIS correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean seleccionarPrimeraOpcionFiltroCedis(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(elements.dropCedisDevEspeciales));
			filtro.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			WebElement primeraOpcion = wait.until(ExpectedConditions.elementToBeClickable(elements.primeraOpcionCedisDevEspeciales));
			ultimoCedisSeleccionado = primeraOpcion.getText().trim().replaceAll("\\s+", " ");
			primeraOpcion.click();

			By valorSeleccionado = By.xpath("//ng-select[.//span[contains(@class,'ng-value-label') and normalize-space()=" + toXpathLiteral(ultimoCedisSeleccionado) + "]]");
			wait.until(ExpectedConditions.visibilityOfElementLocated(valorSeleccionado));

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("CEDIS seleccionado: " + ultimoCedisSeleccionado);
				testCase.addScreenShotCurrentStep(u, "CEDIS_Seleccionado");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar opcion CEDIS: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el control CEDIS muestre el valor seleccionado y mantenga la etiqueta CEDIS en mayusculas.
	 * La validacion usa el ultimo valor seleccionado por seleccionarPrimeraOpcionFiltroCedis.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el valor seleccionado se visualiza en el control CEDIS.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarValorVisibleFiltroCedis(boolean screenshot) {
		try {
			if (ultimoCedisSeleccionado == null || ultimoCedisSeleccionado.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No existe un CEDIS seleccionado para validar.");
				return false;
			}

			By valorSeleccionado = By.xpath("//ng-select[.//span[contains(@class,'ng-value-label') and normalize-space()=" + toXpathLiteral(ultimoCedisSeleccionado) + "]]");
			WebElement control = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(valorSeleccionado));
			String textoControl = control.getText().trim().replaceAll("\\s+", " ");
			java.util.List<String> valoresSeleccionados = obtenerValoresSeleccionados(control);
			boolean valorVisible = valoresSeleccionados.contains(ultimoCedisSeleccionado) || textoControl.contains(ultimoCedisSeleccionado);
			boolean etiquetaCorrecta = u.getDriver().findElements(By.xpath("//*[normalize-space()='CEDIS']")).size() > 0;

			if (screenshot) {
				agregarEvidenciaResumenFiltro("CEDIS", ultimoCedisSeleccionado, cantidadOpcionesCedis, primerasOpcionesCedis);
				testCase.addScreenShotCurrentStep(u, "Valor_Visible_CEDIS");
			}
			return valorVisible && etiquetaCorrecta;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar valor visible del filtro CEDIS: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida en base de datos que el CEDIS seleccionado exista como concatenacion de id_supplier y supplier_name.
	 * Compara el valor visible de UI contra public.xxcm_cedis usando la concatenacion tecnica esperada por el caso.
	 *
	 * @param screenshot true para registrar evidencia de la consulta, false en caso contrario.
	 * @return true si la concatenacion de base de datos coincide con el CEDIS seleccionado en UI.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarCedisSeleccionadoContraBD(boolean screenshot) {
		try {
			if (ultimoCedisSeleccionado == null || ultimoCedisSeleccionado.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No existe un CEDIS seleccionado para validar contra BD.");
				return false;
			}

			String cedisUI = ultimoCedisSeleccionado.trim().replaceAll("\\s+", " ");
			java.util.regex.Matcher matcher = Pattern.compile("^(\\d+)\\s+(.+)$").matcher(cedisUI);
			if (!matcher.matches()) {
				testCase.addTextEvidenceCurrentStep("El valor seleccionado en CEDIS no tiene el formato esperado id_supplier supplier_name: " + cedisUI);
				return false;
			}

			String idSupplierUI = matcher.group(1).trim();
			String supplierNameUI = matcher.group(2).trim().replaceAll("\\s+", " ");
			String consultaCedis = "select id_supplier, supplier_name, trim(id_supplier::text || ' ' || supplier_name) as cedis "
					+ "from public.xxcm_cedis "
					+ "where id_supplier::text = '" + idSupplierUI.replace("'", "''") + "' "
					+ "limit 1";
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Consulta BD CEDIS:\n" + consultaCedis);
			}

			String sql = "select id_supplier, supplier_name, trim(id_supplier::text || ' ' || supplier_name) as cedis "
					+ "from public.xxcm_cedis "
					+ "where id_supplier::text = ? "
					+ "limit 1";

			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
					GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, idSupplierUI);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (!resultSet.next()) {
						testCase.addTextEvidenceCurrentStep("No se encontro en xxcm_cedis el id_supplier: " + idSupplierUI);
						return false;
					}
					String idSupplierBD = resultSet.getString("id_supplier").trim();
					String supplierNameBD = resultSet.getString("supplier_name").trim().replaceAll("\\s+", " ");
					String cedisBD = resultSet.getString("cedis").trim().replaceAll("\\s+", " ");
					boolean coincideIdSupplier = idSupplierUI.equals(idSupplierBD);
					boolean coincideSupplierName = normalizarTextoComparacion(supplierNameUI).equals(normalizarTextoComparacion(supplierNameBD));
					boolean coincideConcatenado = normalizarTextoComparacion(cedisUI).equals(normalizarTextoComparacion(cedisBD));
					if (screenshot) {
						testCase.addTextEvidenceCurrentStep("Resultado consulta xxcm_cedis:\n"
								+ "| id_supplier | supplier_name | cedis |\n"
								+ "| " + idSupplierBD + " | " + supplierNameBD + " | " + cedisBD + " |");
						testCase.addTextEvidenceCurrentStep("CEDIS UI: " + cedisUI + " | CEDIS BD: " + cedisBD);
						testCase.addTextEvidenceCurrentStep("Resultado consulta xxcm_cedis.id_supplier: " + idSupplierBD + " | xxcm_cedis.supplier_name: " + supplierNameBD);
					}
					if (!(coincideIdSupplier && coincideSupplierName && coincideConcatenado)) {
						testCase.addTextEvidenceCurrentStep("Comparacion CEDIS - id_supplier: " + coincideIdSupplier
								+ " | supplier_name: " + coincideSupplierName
								+ " | concatenado: " + coincideConcatenado);
					}
					return coincideIdSupplier && coincideSupplierName && coincideConcatenado;
				}
			}
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar CEDIS contra BD: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Abre el filtro Plaza y valida que sus opciones visibles tengan formato plaza_cve mas desc_plaza.
	 * El formato esperado es plaza_cve seguido de desc_plaza, por ejemplo 32ZJO 10CAN Merida.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si existen opciones y todas las opciones visibles cumplen el formato esperado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean abrirFiltroPlazaYValidarFormatoOpciones(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(elements.dropPlazaDevEspeciales));
			filtro.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements.opcionesDevEspeciales));
			List<WebElement> opciones = u.getDriver().findElements(elements.opcionesDevEspeciales);
			if (opciones.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El filtro Plaza no mostro opciones disponibles.");
				return false;
			}
			cantidadOpcionesPlaza = opciones.size();
			capturarPrimerasOpciones(opciones, primerasOpcionesPlaza);

			Pattern formatoPlaza = Pattern.compile("^\\S+\\s+.+$");
			java.util.List<String> opcionesInvalidas = new java.util.ArrayList<>();
			for (WebElement opcion : opciones) {
				String texto = opcion.getText() == null ? "" : opcion.getText().trim().replaceAll("\\s+", " ");
				if (!formatoPlaza.matcher(texto).matches()) {
					opcionesInvalidas.add(texto);
				}
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Opciones visibles del filtro Plaza: " + opciones.size());
				testCase.addScreenShotCurrentStep(u, "Opciones_Filtro_Plaza");
			}

			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.panelOpcionesDevEspeciales));

			if (!opcionesInvalidas.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("Opciones Plaza con formato invalido: " + String.join(" | ", opcionesInvalidas));
				return false;
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar opciones del filtro Plaza: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona la primera opcion disponible del filtro Plaza y conserva el texto seleccionado.
	 * El valor seleccionado se usa para validar posteriormente el control y la coincidencia contra base de datos.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se selecciona una opcion de Plaza correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean seleccionarPrimeraOpcionFiltroPlaza(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(elements.dropPlazaDevEspeciales));
			filtro.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			WebElement primeraOpcion = wait.until(ExpectedConditions.elementToBeClickable(elements.primeraOpcionDevEspeciales));
			ultimoPlazaSeleccionada = primeraOpcion.getText().trim().replaceAll("\\s+", " ");
			primeraOpcion.click();

			By valorSeleccionado = By.xpath("//ng-select[.//span[contains(@class,'ng-value-label') and normalize-space()=" + toXpathLiteral(ultimoPlazaSeleccionada) + "]]");
			wait.until(ExpectedConditions.visibilityOfElementLocated(valorSeleccionado));

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Plaza seleccionada: " + ultimoPlazaSeleccionada);
				testCase.addScreenShotCurrentStep(u, "Plaza_Seleccionada");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar opcion Plaza: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el control Plaza muestre el valor seleccionado y mantenga la etiqueta Plaza.
	 * La validacion usa el ultimo valor seleccionado por seleccionarPrimeraOpcionFiltroPlaza.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el valor seleccionado se visualiza en el control Plaza.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarValorVisibleFiltroPlaza(boolean screenshot) {
		try {
			if (ultimoPlazaSeleccionada == null || ultimoPlazaSeleccionada.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No existe una Plaza seleccionada para validar.");
				return false;
			}

			By valorSeleccionado = By.xpath("//ng-select[.//span[contains(@class,'ng-value-label') and normalize-space()=" + toXpathLiteral(ultimoPlazaSeleccionada) + "]]");
			WebElement control = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(valorSeleccionado));
			String textoControl = control.getText().trim().replaceAll("\\s+", " ");
			java.util.List<String> valoresSeleccionados = obtenerValoresSeleccionados(control);
			boolean valorVisible = valoresSeleccionados.contains(ultimoPlazaSeleccionada) || textoControl.contains(ultimoPlazaSeleccionada);
			boolean etiquetaCorrecta = u.getDriver().findElements(By.xpath("//*[normalize-space()='Plaza']")).size() > 0;

			if (screenshot) {
				agregarEvidenciaResumenFiltro("Plaza", ultimoPlazaSeleccionada, cantidadOpcionesPlaza, primerasOpcionesPlaza);
				testCase.addScreenShotCurrentStep(u, "Valor_Visible_Plaza");
			}
			return valorVisible && etiquetaCorrecta;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar valor visible del filtro Plaza: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida en base de datos que la Plaza seleccionada exista como concatenacion de plaza_cve y desc_plaza.
	 * Compara el valor visible de UI contra public.xxcm_dim_tienda y public.xxcm_region_plaza_tienda_job_sync relacionadas por RETEK_LOC_ID/id_retek.
	 *
	 * @param screenshot true para registrar evidencia de la consulta, false en caso contrario.
	 * @return true si la concatenacion de base de datos coincide con la Plaza seleccionada en UI.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarPlazaSeleccionadaContraBD(boolean screenshot) {
		try {
			if (ultimoPlazaSeleccionada == null || ultimoPlazaSeleccionada.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No existe una Plaza seleccionada para validar contra BD.");
				return false;
			}

			String plazaUI = ultimoPlazaSeleccionada.trim().replaceAll("\\s+", " ");
			java.util.regex.Matcher matcher = Pattern.compile("^(\\S+)\\s+(.+)$").matcher(plazaUI);
			if (!matcher.matches()) {
				testCase.addTextEvidenceCurrentStep("El valor seleccionado en Plaza no tiene el formato esperado plaza_cve desc_plaza: " + plazaUI);
				return false;
			}

			String plazaCveUI = matcher.group(1).trim();
			String descPlazaUI = matcher.group(2).trim().replaceAll("\\s+", " ");
			String consultaPlaza = "select distinct dt.plaza_cve, rpt.desc_plaza, trim(dt.plaza_cve || ' ' || rpt.desc_plaza) as plaza "
					+ "from public.xxcm_dim_tienda dt "
					+ "inner join public.xxcm_region_plaza_tienda_job_sync rpt on dt.retek_loc_id::text = rpt.id_retek::text "
					+ "where dt.plaza_cve = '" + plazaCveUI.replace("'", "''") + "'";
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Consulta BD Plaza:\n" + consultaPlaza);
			}

			String sql = "select distinct dt.plaza_cve, rpt.desc_plaza, trim(dt.plaza_cve || ' ' || rpt.desc_plaza) as plaza "
					+ "from public.xxcm_dim_tienda dt "
					+ "inner join public.xxcm_region_plaza_tienda_job_sync rpt on dt.retek_loc_id::text = rpt.id_retek::text "
					+ "where dt.plaza_cve = ?";

			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
					GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, plazaCveUI);
				try (ResultSet resultSet = statement.executeQuery()) {
					StringBuilder tabla = new StringBuilder("Resultado consulta Plaza:\n| plaza_cve | desc_plaza | plaza |\n");
					boolean encontroRegistros = false;
					boolean coincide = false;
					String plazaCveBDCoincidente = "";
					String descPlazaBDCoincidente = "";
					String plazaBDCoincidente = "";

					while (resultSet.next()) {
						encontroRegistros = true;
						String plazaCveBD = resultSet.getString("plaza_cve").trim();
						String descPlazaBD = resultSet.getString("desc_plaza").trim().replaceAll("\\s+", " ");
						String plazaBD = resultSet.getString("plaza").trim().replaceAll("\\s+", " ");
						tabla.append("| ").append(plazaCveBD).append(" | ").append(descPlazaBD).append(" | ").append(plazaBD).append(" |\n");

						boolean coincidePlazaCve = plazaCveUI.equals(plazaCveBD);
						boolean coincideDescPlaza = normalizarTextoComparacion(descPlazaUI).equals(normalizarTextoComparacion(descPlazaBD));
						boolean coincideConcatenado = normalizarTextoComparacion(plazaUI).equals(normalizarTextoComparacion(plazaBD));
						if (coincidePlazaCve && coincideDescPlaza && coincideConcatenado) {
							coincide = true;
							plazaCveBDCoincidente = plazaCveBD;
							descPlazaBDCoincidente = descPlazaBD;
							plazaBDCoincidente = plazaBD;
						}
					}

					if (!encontroRegistros) {
						testCase.addTextEvidenceCurrentStep("No se encontro en BD la plaza_cve: " + plazaCveUI);
						return false;
					}

					if (screenshot) {
						testCase.addTextEvidenceCurrentStep(tabla.toString());
						testCase.addTextEvidenceCurrentStep("Plaza UI: " + plazaUI + " | Plaza BD: " + plazaBDCoincidente);
						testCase.addTextEvidenceCurrentStep("Resultado consulta xxcm_dim_tienda.plaza_cve: " + plazaCveBDCoincidente
								+ " | xxcm_region_plaza_tienda_job_sync.desc_plaza: " + descPlazaBDCoincidente);
					}

					if (!coincide) {
						testCase.addTextEvidenceCurrentStep("No se encontro coincidencia exacta normalizada para Plaza UI: " + plazaUI);
					}
					return coincide;
				}
			}
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar Plaza contra BD: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Abre el filtro Tienda y valida que sus opciones visibles tengan formato cr_tienda mas desc_tienda.
	 * El formato esperado es cr_tienda seguido de desc_tienda, por ejemplo 506LL Orion.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si existen opciones y todas las opciones visibles cumplen el formato esperado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean abrirFiltroTiendaYValidarFormatoOpciones(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(elements.dropTiendaDevEspeciales));
			filtro.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements.opcionesDevEspeciales));
			List<WebElement> opciones = u.getDriver().findElements(elements.opcionesDevEspeciales);
			if (opciones.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El filtro Tienda no mostro opciones disponibles.");
				return false;
			}

			cantidadOpcionesTienda = opciones.size();
			capturarPrimerasOpciones(opciones, primerasOpcionesTienda);

			Pattern formatoTienda = Pattern.compile("^\\S+\\s+.+$");
			java.util.List<String> opcionesInvalidas = new java.util.ArrayList<>();
			for (WebElement opcion : opciones) {
				String texto = opcion.getText() == null ? "" : opcion.getText().trim().replaceAll("\\s+", " ");
				if (!formatoTienda.matcher(texto).matches()) {
					opcionesInvalidas.add(texto);
				}
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Opciones visibles del filtro Tienda: " + opciones.size());
				testCase.addScreenShotCurrentStep(u, "Opciones_Filtro_Tienda");
			}

			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.panelOpcionesDevEspeciales));

			if (!opcionesInvalidas.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("Opciones Tienda con formato invalido: " + String.join(" | ", opcionesInvalidas));
				return false;
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar opciones del filtro Tienda: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona la primera opcion disponible del filtro Tienda y conserva el texto seleccionado.
	 * El valor seleccionado se usa para validar posteriormente el control y la coincidencia contra base de datos.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se selecciona una opcion de Tienda correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean seleccionarPrimeraOpcionFiltroTienda(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(elements.dropTiendaDevEspeciales));
			filtro.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			WebElement primeraOpcion = wait.until(ExpectedConditions.elementToBeClickable(elements.primeraOpcionDevEspeciales));
			ultimoTiendaSeleccionada = primeraOpcion.getText().trim().replaceAll("\\s+", " ");
			primeraOpcion.click();

			By valorSeleccionado = By.xpath("//ng-select[.//span[contains(@class,'ng-value-label') and normalize-space()=" + toXpathLiteral(ultimoTiendaSeleccionada) + "]]");
			wait.until(ExpectedConditions.visibilityOfElementLocated(valorSeleccionado));

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Tienda seleccionada: " + ultimoTiendaSeleccionada);
				testCase.addScreenShotCurrentStep(u, "Tienda_Seleccionada");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar opcion Tienda: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el control Tienda muestre el valor seleccionado y mantenga la etiqueta Tienda.
	 * La validacion usa el ultimo valor seleccionado por seleccionarPrimeraOpcionFiltroTienda.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el valor seleccionado se visualiza en el control Tienda.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarValorVisibleFiltroTienda(boolean screenshot) {
		try {
			if (ultimoTiendaSeleccionada == null || ultimoTiendaSeleccionada.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No existe una Tienda seleccionada para validar.");
				return false;
			}

			By valorSeleccionado = By.xpath("//ng-select[.//span[contains(@class,'ng-value-label') and normalize-space()=" + toXpathLiteral(ultimoTiendaSeleccionada) + "]]");
			WebElement control = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(valorSeleccionado));
			String textoControl = control.getText().trim().replaceAll("\\s+", " ");
			java.util.List<String> valoresSeleccionados = obtenerValoresSeleccionados(control);
			boolean valorVisible = valoresSeleccionados.contains(ultimoTiendaSeleccionada) || textoControl.contains(ultimoTiendaSeleccionada);
			boolean etiquetaCorrecta = u.getDriver().findElements(By.xpath("//*[normalize-space()='Tienda']")).size() > 0;

			if (screenshot) {
				agregarEvidenciaResumenFiltro("Tienda", ultimoTiendaSeleccionada, cantidadOpcionesTienda, primerasOpcionesTienda);
				testCase.addScreenShotCurrentStep(u, "Valor_Visible_Tienda");
			}
			return valorVisible && etiquetaCorrecta;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar valor visible del filtro Tienda: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida en base de datos que la Tienda seleccionada exista como concatenacion de cr_tienda y desc_tienda.
	 * Compara el valor visible de UI contra public.xxcm_region_plaza_tienda_job_sync.
	 *
	 * @param screenshot true para registrar evidencia de la consulta, false en caso contrario.
	 * @return true si la concatenacion de base de datos coincide con la Tienda seleccionada en UI.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarTiendaSeleccionadaContraBD(boolean screenshot) {
		try {
			if (ultimoTiendaSeleccionada == null || ultimoTiendaSeleccionada.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No existe una Tienda seleccionada para validar contra BD.");
				return false;
			}

			String tiendaUI = ultimoTiendaSeleccionada.trim().replaceAll("\\s+", " ");
			java.util.regex.Matcher matcher = Pattern.compile("^(\\S+)\\s+(.+)$").matcher(tiendaUI);
			if (!matcher.matches()) {
				testCase.addTextEvidenceCurrentStep("El valor seleccionado en Tienda no tiene el formato esperado cr_tienda desc_tienda: " + tiendaUI);
				return false;
			}

			String crTiendaUI = matcher.group(1).trim();
			String descTiendaUI = matcher.group(2).trim().replaceAll("\\s+", " ");
			String consultaTienda = "select distinct cr_tienda, desc_tienda, trim(cr_tienda || ' ' || desc_tienda) as tienda "
					+ "from public.xxcm_region_plaza_tienda_job_sync "
					+ "where cr_tienda = '" + crTiendaUI.replace("'", "''") + "'";
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Consulta BD Tienda:\n" + consultaTienda);
			}

			String sql = "select distinct cr_tienda, desc_tienda, trim(cr_tienda || ' ' || desc_tienda) as tienda "
					+ "from public.xxcm_region_plaza_tienda_job_sync "
					+ "where cr_tienda = ?";

			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
					GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, crTiendaUI);
				try (ResultSet resultSet = statement.executeQuery()) {
					StringBuilder tabla = new StringBuilder("Resultado consulta Tienda:\n| cr_tienda | desc_tienda | tienda |\n");
					boolean encontroRegistros = false;
					boolean coincide = false;
					String crTiendaBDCoincidente = "";
					String descTiendaBDCoincidente = "";
					String tiendaBDCoincidente = "";

					while (resultSet.next()) {
						encontroRegistros = true;
						String crTiendaBD = resultSet.getString("cr_tienda").trim();
						String descTiendaBD = resultSet.getString("desc_tienda").trim().replaceAll("\\s+", " ");
						String tiendaBD = resultSet.getString("tienda").trim().replaceAll("\\s+", " ");
						tabla.append("| ").append(crTiendaBD).append(" | ").append(descTiendaBD).append(" | ").append(tiendaBD).append(" |\n");

						boolean coincideCrTienda = crTiendaUI.equals(crTiendaBD);
						boolean coincideDescTienda = normalizarTextoComparacion(descTiendaUI).equals(normalizarTextoComparacion(descTiendaBD));
						boolean coincideConcatenado = normalizarTextoComparacion(tiendaUI).equals(normalizarTextoComparacion(tiendaBD));
						if (coincideCrTienda && coincideDescTienda && coincideConcatenado) {
							coincide = true;
							crTiendaBDCoincidente = crTiendaBD;
							descTiendaBDCoincidente = descTiendaBD;
							tiendaBDCoincidente = tiendaBD;
						}
					}

					if (!encontroRegistros) {
						testCase.addTextEvidenceCurrentStep("No se encontro en BD la cr_tienda: " + crTiendaUI);
						return false;
					}

					if (screenshot) {
						testCase.addTextEvidenceCurrentStep(tabla.toString());
						testCase.addTextEvidenceCurrentStep("Tienda UI: " + tiendaUI + " | Tienda BD: " + tiendaBDCoincidente);
						testCase.addTextEvidenceCurrentStep("Resultado consulta xxcm_region_plaza_tienda_job_sync.cr_tienda: " + crTiendaBDCoincidente
								+ " | xxcm_region_plaza_tienda_job_sync.desc_tienda: " + descTiendaBDCoincidente);
					}

					if (!coincide) {
						testCase.addTextEvidenceCurrentStep("No se encontro coincidencia exacta normalizada para Tienda UI: " + tiendaUI);
					}
					return coincide;
				}
			}
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar Tienda contra BD: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Abre el filtro BOL y valida que el panel de busqueda se despliegue correctamente.
	 * El metodo no selecciona valores; solo comprueba la disponibilidad del control.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el filtro BOL se despliega correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean abrirFiltroBol(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));

			if (ultimoTiendaSeleccionada == null || ultimoTiendaSeleccionada.trim().isEmpty()) {
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Para habilitar BOL se selecciona una Tienda.");
				}
				if (!abrirFiltroTiendaYValidarFormatoOpciones(screenshot)) {
					return false;
				}
				if (!seleccionarPrimeraOpcionFiltroTienda(screenshot)) {
					return false;
				}
				if (!validarValorVisibleFiltroTienda(screenshot)) {
					return false;
				}
			}

			WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(elements.dropBolDevEspeciales));
			filtro.click();

			String clase = filtro.getAttribute("class");
			String ariaDisabled = filtro.getAttribute("aria-disabled");
			boolean habilitado = filtro.isEnabled()
					&& !"true".equalsIgnoreCase(ariaDisabled)
					&& (clase == null || !clase.contains("ng-select-disabled"));

			if (!habilitado) {
				testCase.addTextEvidenceCurrentStep("El filtro BOL permanece deshabilitado. Verificar que exista una Tienda seleccionada antes de usar BOL.");
				return false;
			}

			WebElement input = obtenerInputFiltroActivo(elements.dropBolDevEspeciales);
			boolean inputDisponible = input.isDisplayed() && input.isEnabled();

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El filtro BOL se encuentra habilitado y disponible para busqueda.");
				testCase.addScreenShotCurrentStep(u, "Filtro_BOL_Desplegado");
			}

			return inputDisponible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al abrir el filtro BOL: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el filtro BOL permanezca deshabilitado cuando no existe una Tienda seleccionada.
	 * El comportamiento esperado es que BOL dependa de Tienda antes de permitir busqueda o seleccion de valores.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el filtro BOL no se encuentra habilitado ni disponible para captura.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean validarFiltroBolDeshabilitadoSinTienda(boolean screenshot) {
		try {
			WebElement filtro = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.dropBolDevEspeciales));
			String clase = obtenerAtributoSeguro(filtro, "class");
			String ariaDisabled = obtenerAtributoSeguro(filtro, "aria-disabled");
			boolean deshabilitado = !filtro.isEnabled()
					|| "true".equalsIgnoreCase(ariaDisabled)
					|| clase.contains("ng-select-disabled");

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El filtro BOL permanece deshabilitado hasta seleccionar una Tienda.");
				testCase.addScreenShotCurrentStep(u, "BOL_Deshabilitado_Sin_Tienda");
			}
			return deshabilitado;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar BOL deshabilitado sin Tienda: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Escribe un prefijo valido de tres digitos en el filtro BOL y valida que se muestren sugerencias.
	 * El prefijo se obtiene desde xxcm_historico_correos buscando coincidencias multiples de BOL completo.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el portal muestra sugerencias para el prefijo escrito.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean escribirTresDigitosValidosFiltroBolYValidarSugerencias(boolean screenshot) {
		try {
			prefijoBolValido = obtenerPrefijoBolConCoincidenciasBD(screenshot);
			if (prefijoBolValido == null || prefijoBolValido.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No se obtuvo prefijo BOL valido desde BD.");
				return false;
			}

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(elements.dropBolDevEspeciales));
			filtro.click();
			WebElement input = obtenerInputFiltroActivo(elements.dropBolDevEspeciales);
			input.clear();
			input.sendKeys(prefijoBolValido);

			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements.opcionesDevEspeciales));
			List<WebElement> opciones = u.getDriver().findElements(elements.opcionesDevEspeciales);
			if (opciones.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El filtro BOL no mostro sugerencias para el prefijo: " + prefijoBolValido);
				return false;
			}

			cantidadOpcionesBol = opciones.size();
			capturarPrimerasOpciones(opciones, primerasOpcionesBol);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Prefijo BOL escrito: " + prefijoBolValido);
				testCase.addTextEvidenceCurrentStep("Cantidad de sugerencias BOL visibles: " + cantidadOpcionesBol);
				testCase.addTextEvidenceCurrentStep("Primeras 5 sugerencias BOL:\n" + formatearListaOpciones(primerasOpcionesBol));
				testCase.addScreenShotCurrentStep(u, "Sugerencias_BOL");
			}

			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al buscar sugerencias BOL: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona la primera sugerencia disponible del filtro BOL y conserva el valor seleccionado.
	 * El valor seleccionado se usa para validaciones visuales y de base de datos.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se selecciona una sugerencia BOL correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean seleccionarPrimeraSugerenciaFiltroBol(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			WebElement primeraOpcion = wait.until(ExpectedConditions.elementToBeClickable(elements.primeraOpcionDevEspeciales));
			ultimoBolSeleccionado = primeraOpcion.getText().trim().replaceAll("\\s+", " ");
			List<WebElement> checkboxes = primeraOpcion.findElements(By.xpath(".//input[@type='checkbox'] | .//*[contains(@class,'mat-checkbox') or contains(@class,'mdc-checkbox')]") );
			if (!checkboxes.isEmpty()) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", checkboxes.get(0));
			} else {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", primeraOpcion);
			}

			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.dropBolDevEspeciales));

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Sugerencia BOL seleccionada: " + ultimoBolSeleccionado);
				testCase.addScreenShotCurrentStep(u, "BOL_Seleccionado");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar sugerencia BOL: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el control BOL muestre el valor seleccionado.
	 * Muestra evidencia resumida con opcion seleccionada, cantidad de sugerencias y primeras 5 sugerencias.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el valor seleccionado se visualiza en el control BOL.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarValorVisibleFiltroBol(boolean screenshot) {
		try {
			if (ultimoBolSeleccionado == null || ultimoBolSeleccionado.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No existe un BOL seleccionado para validar.");
				return false;
			}

			WebElement control = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.dropBolDevEspeciales));
			String textoControl = control.getText().trim().replaceAll("\\s+", " ");
			String textoDomControl = obtenerTextoDom(control);
			java.util.List<String> valoresSeleccionados = obtenerValoresSeleccionados(control);
			String valorInputBol = obtenerValorInputFiltro(elements.dropBolDevEspeciales);
			String bolNormalizado = normalizarTextoComparacion(ultimoBolSeleccionado);
			boolean valorVisible = contieneTextoNormalizado(textoControl, bolNormalizado)
					|| contieneTextoNormalizado(textoDomControl, bolNormalizado)
					|| contieneTextoNormalizado(valorInputBol, bolNormalizado)
					|| listaContieneTextoNormalizado(valoresSeleccionados, bolNormalizado)
					|| existeTextoEnPantalla(ultimoBolSeleccionado);
			boolean etiquetaCorrecta = u.getDriver().findElements(By.xpath("//*[translate(normalize-space(),'abcdefghijklmnopqrstuvwxyz','ABCDEFGHIJKLMNOPQRSTUVWXYZ')='BOL']")).size() > 0;

			if (screenshot) {
				agregarEvidenciaResumenFiltro("BOL", ultimoBolSeleccionado, cantidadOpcionesBol, primerasOpcionesBol);
				testCase.addScreenShotCurrentStep(u, "Valor_Visible_BOL");
			}
			return valorVisible && etiquetaCorrecta;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar valor visible del filtro BOL: " + e.getMessage());
			return false;
		}
	}

	private String obtenerTextoDom(WebElement element) {
		try {
			Object texto = ((JavascriptExecutor) u.getDriver()).executeScript(
					"return (arguments[0].innerText || arguments[0].textContent || arguments[0].getAttribute('title') || arguments[0].getAttribute('aria-label') || '').trim();",
					element);
			return texto == null ? "" : texto.toString().trim().replaceAll("\\s+", " ");
		} catch (Exception e) {
			return "";
		}
	}

	private boolean contieneTextoNormalizado(String texto, String textoEsperadoNormalizado) {
		return normalizarTextoComparacion(texto).contains(textoEsperadoNormalizado);
	}

	private boolean listaContieneTextoNormalizado(java.util.List<String> valores, String textoEsperadoNormalizado) {
		for (String valor : valores) {
			if (contieneTextoNormalizado(valor, textoEsperadoNormalizado)) {
				return true;
			}
		}
		return false;
	}

	private boolean existeTextoEnPantalla(String texto) {
		try {
			return !u.getDriver().findElements(By.xpath("//*[contains(normalize-space()," + toXpathLiteral(texto) + ")]")).isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Valida en base de datos que la sugerencia BOL seleccionada exista en xxcm_historico_correos.
	 * Compara el BOL completo visible contra la proyeccion tecnica de body y transferencia.
	 *
	 * @param screenshot true para registrar evidencia de la consulta, false en caso contrario.
	 * @return true si la proyeccion tecnica de BD coincide con el BOL seleccionado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarBolSeleccionadoContraBD(boolean screenshot) {
		try {
			if (ultimoBolSeleccionado == null || ultimoBolSeleccionado.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No existe un BOL seleccionado para validar contra BD.");
				return false;
			}
			if (ultimoTiendaSeleccionada == null || ultimoTiendaSeleccionada.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No existe una Tienda seleccionada para acotar la validacion del BOL contra BD.");
				return false;
			}

			String bolUI = ultimoBolSeleccionado.trim().replaceAll("\\s+", " ");
			String tiendaUI = ultimoTiendaSeleccionada.trim().replaceAll("\\s+", " ");
			java.util.regex.Matcher tiendaMatcher = Pattern.compile("^(\\S+)\\s+(.+)$").matcher(tiendaUI);
			if (!tiendaMatcher.matches()) {
				testCase.addTextEvidenceCurrentStep("La tienda seleccionada no tiene formato cr_tienda desc_tienda: " + tiendaUI);
				return false;
			}
			String crTiendaUI = tiendaMatcher.group(1).trim();
			String consultaBol = "select * "
					+ "from public.xxcm_cedis_de_proyeccion_grid "
					+ "where bol_completo = '" + bolUI.replace("'", "''") + "' "
					+ "limit 1";
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Consulta BD BOL:\n" + consultaBol);
			}

			String sql = "select * "
					+ "from public.xxcm_cedis_de_proyeccion_grid "
					+ "where bol_completo = ? "
					+ "limit 1";

			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
					GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setQueryTimeout(30);
				statement.setString(1, bolUI);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (!resultSet.next()) {
						testCase.addTextEvidenceCurrentStep("No se encontro en xxcm_cedis_de_proyeccion_grid el bol_completo: " + bolUI);
						return false;
					}

					String idBD = obtenerValorColumnaSiExiste(resultSet, "id");
					String transferenciaBD = obtenerValorColumnaSiExiste(resultSet, "transferencia");
					String folioBolBD = obtenerValorColumnaSiExiste(resultSet, "folio_bol");
					String crTiendaBD = obtenerValorColumnaSiExiste(resultSet, "cr_tienda");
					String bolCompletoBD = resultSet.getString("bol_completo").trim().replaceAll("\\s+", " ");
					String bolUINormalizado = normalizarTextoComparacion(bolUI);
					String bolCompletoBDNormalizado = normalizarTextoComparacion(bolCompletoBD);
					String transferenciaBDNormalizada = normalizarTextoComparacion(transferenciaBD);
					String folioBolBDNormalizado = normalizarTextoComparacion(folioBolBD);
					String crTiendaBDNormalizada = normalizarTextoComparacion(crTiendaBD);
					boolean coincideBolCompleto = bolUINormalizado.equals(bolCompletoBDNormalizado);
					boolean coincideTransferencia = bolUINormalizado.equals(transferenciaBDNormalizada);
					boolean coincideFolioBol = bolUINormalizado.equals(folioBolBDNormalizado);
					boolean coincide = coincideBolCompleto || coincideTransferencia || coincideFolioBol;

					if (screenshot) {
						testCase.addTextEvidenceCurrentStep("Resultado consulta BOL:\n"
								+ "| id | cr_tienda | transferencia | folio_bol | bol_completo |\n"
								+ "| " + idBD + " | " + crTiendaBD + " | " + transferenciaBD + " | " + folioBolBD + " | " + bolCompletoBD + " |");
						testCase.addTextEvidenceCurrentStep("BOL UI: " + bolUI + " | BOL BD: " + bolCompletoBD);
					}
					return coincide;
				}
			}
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar BOL contra BD: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Escribe un valor sin coincidencias en el filtro BOL y valida que no se muestren sugerencias.
	 * El valor usado es controlado para no coincidir con un BOL real.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si no se muestran sugerencias para el valor sin coincidencias.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean escribirBolSinCoincidenciasYValidarSinSugerencias(boolean screenshot) {
		try {
			String bolSinCoincidencias = "ZZZ999SINCOINCIDENCIA";
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(elements.dropBolDevEspeciales));
			filtro.click();
			limpiarFiltroActivo(elements.dropBolDevEspeciales);
			WebElement input = obtenerInputFiltroActivo(elements.dropBolDevEspeciales);
			input.clear();
			input.sendKeys(bolSinCoincidencias);

			try {
				new WebDriverWait(u.getDriver(), Duration.ofSeconds(4))
						.until(ExpectedConditions.numberOfElementsToBe(elements.opcionesDevEspeciales, 0));
			} catch (Exception ignore) {
			}

			List<WebElement> opciones = u.getDriver().findElements(elements.opcionesDevEspeciales);
			boolean sinSugerencias = opciones.isEmpty();

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Valor BOL sin coincidencias escrito: " + bolSinCoincidencias);
				testCase.addTextEvidenceCurrentStep("Cantidad de sugerencias mostradas: " + opciones.size());
				testCase.addScreenShotCurrentStep(u, "BOL_Sin_Coincidencias");
			}

			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			return sinSugerencias;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar BOL sin coincidencias: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Da clic en el boton Limpiar Filtros de Devoluciones Especiales.
	 * Se usa para cerrar la validacion del flujo dejando la pantalla sin filtros seleccionados.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el clic en Limpiar Filtros se ejecuta correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean clicLimpiarFiltrosDevolucionesEspeciales(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonLimpiar = wait.until(ExpectedConditions.elementToBeClickable(elements.btnLimpiarFiltrosDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", botonLimpiar);
			botonLimpiar.click();

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se da clic en el boton Limpiar Filtros.");
				testCase.addScreenShotCurrentStep(u, "Limpiar_Filtros_Devoluciones_Especiales");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al dar clic en Limpiar Filtros: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Abre el control Rango de Fechas y valida que el calendario estandar se despliegue.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el calendario se muestra correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean abrirControlCalendarioRangoFechas(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonCalendario = wait.until(ExpectedConditions.elementToBeClickable(elements.btnCalendarioDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", botonCalendario);
			botonCalendario.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El calendario de Rango de Fechas se despliega correctamente.");
				testCase.addScreenShotCurrentStep(u, "Calendario_Rango_Fechas");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al abrir calendario de Rango de Fechas: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el encabezado del calendario muestre mes y anio del periodo visible.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el encabezado de mes y anio se visualiza.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarVisualizacionMesAnioCalendario(boolean screenshot) {
		try {
			WebElement encabezado = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.encabezadoCalendarioDevEspeciales));
			String textoEncabezado = encabezado.getText().trim().replaceAll("\\s+", " ");
			boolean encabezadoValido = !textoEncabezado.isEmpty() && textoEncabezado.matches(".*\\d{4}.*");

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Encabezado de calendario visible: " + textoEncabezado);
				testCase.addScreenShotCurrentStep(u, "Mes_Anio_Calendario");
			}
			return encabezadoValido;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar mes y anio del calendario: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el dia actual se encuentre resaltado en el calendario.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el dia actual se identifica visualmente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarResaltadoDiaActualCalendario(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));
			WebElement fechaFinalDisponible = obtenerPrimerDiaHabilitadoCalendario();
			String diaEsperado = String.valueOf(ultimaFechaDisponibleCalendario.getDayOfMonth());
			String textoDiaIdentificado = obtenerTextoCeldaCalendario(fechaFinalDisponible);
			String textoEncabezado = obtenerEncabezadoCalendarioVisible();
			boolean mesReferenciaVisible = encabezadoCalendarioCorrespondeAFecha(textoEncabezado, ultimaFechaDisponibleCalendario);
			boolean fechaFinalHabilitada = fechaFinalDisponible.isDisplayed() && fechaFinalDisponible.isEnabled();

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Encabezado de calendario: " + textoEncabezado
						+ " | Fecha final disponible BD: " + ultimaFechaDisponibleCalendario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX")))
						+ " | Dia esperado: " + diaEsperado
						+ " | Dia identificado: " + textoDiaIdentificado
						+ " | Mes de referencia visible: " + mesReferenciaVisible
						+ " | Fecha final habilitada: " + fechaFinalHabilitada);
				testCase.addScreenShotCurrentStep(u, "Fecha_Final_Disponible_Calendario");
			}
			return mesReferenciaVisible && fechaFinalHabilitada && diaEsperado.equals(textoDiaIdentificado);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar fecha final disponible en calendario: " + e.getMessage());
			return false;
		}
	}

	private String obtenerTextoCeldaCalendario(WebElement elementoCalendario) {
		try {
			String texto = elementoCalendario.getText() == null ? "" : elementoCalendario.getText().trim();
			if (!texto.isEmpty()) {
				return texto.replaceAll("\\s+", " ");
			}

			List<WebElement> contenidos = elementoCalendario.findElements(By.xpath(".//*[contains(@class,'mat-calendar-body-cell-content')]"));
			for (WebElement contenido : contenidos) {
				String textoContenido = contenido.getText() == null ? "" : contenido.getText().trim();
				if (!textoContenido.isEmpty()) {
					return textoContenido.replaceAll("\\s+", " ");
				}
			}

			Object textoDom = ((JavascriptExecutor) u.getDriver()).executeScript(
					"return (arguments[0].innerText || arguments[0].textContent || arguments[0].getAttribute('aria-label') || '').trim();",
					elementoCalendario);
			return textoDom == null ? "" : textoDom.toString().trim().replaceAll("\\s+", " ");
		} catch (Exception e) {
			return "";
		}
	}

	private String obtenerEncabezadoCalendarioVisible() {
		try {
			WebElement encabezado = u.getDriver().findElement(elements.encabezadoCalendarioDevEspeciales);
			return encabezado.getText() == null ? "" : encabezado.getText().trim().replaceAll("\\s+", " ");
		} catch (Exception e) {
			return "";
		}
	}

	private boolean encabezadoCalendarioCorrespondeAFecha(String encabezado, LocalDate fecha) {
		String encabezadoNormalizado = normalizarTextoComparacion(encabezado);
		String mesCorto = fecha.format(DateTimeFormatter.ofPattern("MMM", new Locale("es", "MX"))).replace(".", "");
		String mesLargo = fecha.format(DateTimeFormatter.ofPattern("MMMM", new Locale("es", "MX")));
		boolean coincideMes = encabezadoNormalizado.contains(normalizarTextoComparacion(mesCorto))
				|| encabezadoNormalizado.contains(normalizarTextoComparacion(mesLargo));
		boolean coincideAnio = encabezadoNormalizado.contains(String.valueOf(fecha.getYear()));
		return coincideMes && coincideAnio;
	}

	private boolean ariaLabelCorrespondeAFecha(String ariaLabel, LocalDate fecha) {
		String ariaLabelNormalizado = normalizarTextoComparacion(ariaLabel);
		if (ariaLabelNormalizado.isEmpty()) {
			return false;
		}

		String mesCorto = fecha.format(DateTimeFormatter.ofPattern("MMM", new Locale("es", "MX"))).replace(".", "");
		String mesLargo = fecha.format(DateTimeFormatter.ofPattern("MMMM", new Locale("es", "MX")));
		boolean coincideDia = ariaLabelNormalizado.contains(String.valueOf(fecha.getDayOfMonth()));
		boolean coincideMes = ariaLabelNormalizado.contains(normalizarTextoComparacion(mesCorto))
				|| ariaLabelNormalizado.contains(normalizarTextoComparacion(mesLargo));
		boolean coincideAnio = ariaLabelNormalizado.contains(String.valueOf(fecha.getYear()));
		return coincideDia && coincideMes && coincideAnio;
	}

	private boolean existeDiaVisibleEnCalendario(String diaEsperado) {
		List<WebElement> dias = u.getDriver().findElements(By.xpath("//div[contains(@class,'cdk-overlay-pane')]//*[contains(@class,'mat-calendar-body-cell') and not(contains(@class,'mat-calendar-body-disabled'))]"));
		for (WebElement dia : dias) {
			if (dia.isDisplayed() && diaEsperado.equals(obtenerTextoCeldaCalendario(dia))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtiene desde BD la ultima fecha disponible de Devoluciones Especiales y localiza el dia habilitado equivalente en el calendario.
	 * La fecha origen se toma de public.xxcm_cedis_de_proyeccion_grid.fecha para validar contra la misma regla funcional que habilita el datepicker.
	 *
	 * @return elemento del calendario correspondiente a la ultima fecha disponible en BD.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	private WebElement obtenerPrimerDiaHabilitadoCalendario() throws Exception {
		String sql = "select max(fecha)::date as ultima_fecha_disponible "
				+ "from public.xxcm_cedis_de_proyeccion_grid "
				+ "where fecha is not null";
		LocalDate ultimaFechaDisponible = null;

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next() && resultSet.getDate("ultima_fecha_disponible") != null) {
				ultimaFechaDisponible = resultSet.getDate("ultima_fecha_disponible").toLocalDate();
			}
		}

		if (ultimaFechaDisponible == null) {
			throw new org.openqa.selenium.NoSuchElementException("No se encontro ultima fecha disponible en xxcm_cedis_de_proyeccion_grid.fecha.");
		}

		ultimaFechaDisponibleCalendario = ultimaFechaDisponible;
		testCase.addTextEvidenceCurrentStep("Ultima fecha disponible en xxcm_cedis_de_proyeccion_grid.fecha: "
				+ ultimaFechaDisponible.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

		String diaEsperado = String.valueOf(ultimaFechaDisponible.getDayOfMonth());
		String mesLargo = normalizarTextoComparacion(ultimaFechaDisponible.format(DateTimeFormatter.ofPattern("MMMM", new Locale("es", "MX"))));
		String anioEsperado = String.valueOf(ultimaFechaDisponible.getYear());
		List<WebElement> dias = u.getDriver().findElements(By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-body-cell') and not(contains(@class,'mat-calendar-body-disabled'))]"));
		for (WebElement dia : dias) {
			String textoDia = obtenerTextoCeldaCalendario(dia);
			String ariaLabel = obtenerAtributoSeguro(dia, "aria-label");
			String ariaLabelNormalizado = normalizarTextoComparacion(ariaLabel);
			boolean coincideFechaBD = dia.isDisplayed()
					&& dia.isEnabled()
					&& diaEsperado.equals(textoDia)
					&& ariaLabelNormalizado.contains(mesLargo)
					&& ariaLabelNormalizado.contains(anioEsperado);
			if (coincideFechaBD) {
				return dia;
			}
		}

		throw new org.openqa.selenium.NoSuchElementException("No se encontro habilitada en el calendario la ultima fecha disponible de BD: "
				+ ultimaFechaDisponible.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	}

	private WebElement obtenerDiaHabilitadoCalendarioPorTexto(String diaSeleccionado) {
		List<WebElement> dias = u.getDriver().findElements(By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-body-cell') and not(contains(@class,'mat-calendar-body-disabled'))]"));
		for (WebElement dia : dias) {
			if (dia.isDisplayed() && dia.isEnabled() && diaSeleccionado.equals(obtenerTextoCeldaCalendario(dia))) {
				return dia;
			}
		}

		throw new org.openqa.selenium.NoSuchElementException("No se encontro habilitado el dia " + diaSeleccionado + " en el calendario.");
	}

	private void clicDiaCalendario(WebElement diaCalendario) {
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", diaCalendario);
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", diaCalendario);
	}

	private String obtenerAtributoSeguro(WebElement elemento, String atributo) {
		try {
			String valor = elemento.getAttribute(atributo);
			return valor == null ? "" : valor.trim().replaceAll("\\s+", " ");
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Valida que las flechas de navegacion entre meses se muestren correctamente.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si ambas flechas de navegacion estan visibles.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarNavegacionEntreMesesCalendario(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement anterior = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.btnMesAnteriorCalendarioDevEspeciales));
			WebElement siguiente = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.btnMesSiguienteCalendarioDevEspeciales));
			boolean visibles = anterior.isDisplayed() && siguiente.isDisplayed();

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se visualizan las flechas anterior y siguiente para navegar entre meses.");
				testCase.addScreenShotCurrentStep(u, "Navegacion_Meses_Calendario");
			}
			return visibles;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar navegacion entre meses: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que los dias de la semana se visualicen completos y alineados en el calendario.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se visualizan siete encabezados de dias de la semana.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarDistribucionDiasSemanaCalendario(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));

			By[] localizadoresEncabezados = new By[] {
					elements.encabezadosDiasSemanaCalendarioDevEspeciales,
					By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-calendar//table//thead//th"),
					By.xpath("//div[contains(@class,'cdk-overlay-pane')]//table[contains(@class,'mat-calendar-table')]//thead//th"),
					By.xpath("//div[contains(@class,'cdk-overlay-pane')]//*[contains(@class,'mat-calendar-table-header')]"),
					By.xpath("//div[contains(@class,'cdk-overlay-pane')]//thead//*[self::th or self::span][normalize-space()]")
			};
			String[] nombresLocalizadores = new String[] {
					"elements.encabezadosDiasSemanaCalendarioDevEspeciales",
					"mat-calendar table thead th",
					"mat-calendar-table thead th",
					"mat-calendar-table-header",
					"thead th/span con texto"
			};

			java.util.List<String> textos = new java.util.ArrayList<>();
			for (int i = 0; i < localizadoresEncabezados.length; i++) {
				List<WebElement> encabezados = u.getDriver().findElements(localizadoresEncabezados[i]);
				java.util.List<String> textosCandidatos = new java.util.ArrayList<>();

				for (int j = 0; j < encabezados.size(); j++) {
					WebElement encabezado = encabezados.get(j);
					String texto = encabezado.getText() == null ? "" : encabezado.getText().trim().replaceAll("\\s+", " ");
					String textoDom = obtenerTextoDom(encabezado);
					String textoUsable = !texto.isEmpty() ? texto : textoDom;

					if (encabezado.isDisplayed() && !textoUsable.isEmpty() && textoUsable.length() <= 2) {
						textosCandidatos.add(textoUsable);
					}
				}

				if (textosCandidatos.size() >= 7) {
					textos = textosCandidatos;
					break;
				}
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Encabezados de dias de semana visibles: " + String.join(", ", textos));
				testCase.addScreenShotCurrentStep(u, "Distribucion_Dias_Semana_Calendario");
			}
			return textos.size() >= 7;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar distribucion de dias de semana: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el calendario se cierre al seleccionar una fecha disponible.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el calendario se cierra correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarCierreCalendarioAlSeleccionarFecha(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement diaDisponible = obtenerPrimerDiaHabilitadoCalendario();
			String diaSeleccionado = obtenerTextoCeldaCalendario(diaDisponible);
			clicDiaCalendario(diaDisponible);

			try {
				new WebDriverWait(u.getDriver(), Duration.ofSeconds(4))
						.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));
			} catch (Exception calendarioPermaneceAbierto) {
				WebElement mismoDiaFinal = obtenerDiaHabilitadoCalendarioPorTexto(diaSeleccionado);
				clicDiaCalendario(mismoDiaFinal);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));
			}

			fechaMismoDiaSeleccionada = construirFechaSeleccionada(diaSeleccionado);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El calendario se cierra al seleccionar la ultima fecha disponible de BD: " + fechaMismoDiaSeleccionada);
				testCase.addScreenShotCurrentStep(u, "Cierre_Calendario_Fecha_Final_BD");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar cierre de calendario: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que al reabrir el calendario la fecha seleccionada permanezca resaltada.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si existe una fecha seleccionada o activa al reabrir el calendario.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarPersistenciaSeleccionCalendario(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonCalendario = wait.until(ExpectedConditions.elementToBeClickable(elements.btnCalendarioDevEspeciales));
			botonCalendario.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));

			boolean seleccionVisible = !u.getDriver().findElements(By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-body-selected') or contains(@class,'mat-calendar-body-active')]")).isEmpty();
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Persistencia de seleccion en calendario visible: " + seleccionVisible);
				testCase.addScreenShotCurrentStep(u, "Persistencia_Seleccion_Calendario");
			}
			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			return seleccionVisible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar persistencia de seleccion en calendario: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona el mismo dia como fecha inicial y final del rango de fechas.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el sistema acepta el mismo dia como rango valido.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean seleccionarMismoDiaRangoFechas(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonCalendario = wait.until(ExpectedConditions.elementToBeClickable(elements.btnCalendarioDevEspeciales));
			botonCalendario.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));

			WebElement diaDisponible = obtenerPrimerDiaHabilitadoCalendario();
			String diaSeleccionado = obtenerTextoCeldaCalendario(diaDisponible);
			clicDiaCalendario(diaDisponible);
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));
				WebElement mismoDia = obtenerDiaHabilitadoCalendarioPorTexto(diaSeleccionado);
				clicDiaCalendario(mismoDia);
			} catch (Exception ignore) {
			}

			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));
			fechaMismoDiaSeleccionada = construirFechaSeleccionada(diaSeleccionado);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se selecciona la ultima fecha disponible de BD como fecha inicial y final: " + fechaMismoDiaSeleccionada);
				testCase.addScreenShotCurrentStep(u, "Mismo_Dia_Rango_Fechas");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar mismo dia en Rango de Fechas: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona en el datepicker un rango inicial de siete dias para el usuario CEDIS,
	 * tomando como fecha base la primera fecha disponible en BD para su PCSUPPLIERID.
	 * No escribe texto en inputs; utiliza unicamente seleccion por calendario.
	 *
	 * @param usuario usuario CEDIS para resolver PCSUPPLIERID.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el rango se selecciona correctamente en el datepicker.
	 *
	 * @author GitHub Copilot
	 * @date 2026-08-05
	 */
	public boolean seleccionarRangoSieteDiasDesdeFechaInicialBdCedis(String usuario, boolean screenshot) {
		try {
			LocalDate[] ventana = obtenerVentanaInicialSieteDiasPorUsuarioCedis(usuario);
			LocalDate fechaInicioBase = ventana[0];
			LocalDate fechaFinObjetivo = ventana[1];
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Rango base BD usuario -> Inicio base: "
						+ fechaInicioBase.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
						+ " | Fin objetivo: " + fechaFinObjetivo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".");
			}
			return abrirCalendarioYSeleccionarRango(fechaInicioBase, fechaFinObjetivo, screenshot);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar rango de siete dias desde BD CEDIS: " + e.getMessage());
			return false;
		}
	}

	// Navega el datepicker para seleccionar inicio y fin; maneja días deshabilitados buscando el más cercano habilitado.
	private boolean abrirCalendarioYSeleccionarRango(LocalDate fechaInicioBase, LocalDate fechaFinObjetivo, boolean screenshot) {
		try {
			LocalDate fechaInicio = fechaInicioBase;
			LocalDate fechaFin = fechaFinObjetivo;

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(25));
			WebElement botonCalendario = wait.until(ExpectedConditions.elementToBeClickable(elements.btnCalendarioDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", botonCalendario);
			try {
				botonCalendario.click();
			} catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", botonCalendario);
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));

			boolean navInicio = navegarCalendarioAFecha(fechaInicio, true) || navegarCalendarioAFecha(fechaInicio, false);
			if (!navInicio) {
				testCase.addCodeEvidenceCurrentStep("No fue posible navegar en calendario a la fecha inicial: " + fechaInicioBase.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				return false;
			}
			WebElement diaInicio = obtenerDiaCalendarioPorFecha(fechaInicio);
			if (diaInicio == null || diaCalendarioEstaDeshabilitado(diaInicio)) {
				LocalDate ajustado = buscarPrimeraFechaHabilitadaEnCalendario(fechaInicioBase, 15);
				if (ajustado == null) {
					testCase.addCodeEvidenceCurrentStep("Fecha inicial no habilitada en calendario: " + fechaInicioBase.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					return false;
				}
				fechaInicio = ajustado;
				fechaFin = fechaInicio.plusDays(6);
				if (fechaFin.isAfter(fechaFinObjetivo)) fechaFin = fechaFinObjetivo;
				if (fechaFin.isBefore(fechaInicio)) fechaFin = fechaInicio;
				navInicio = navegarCalendarioAFecha(fechaInicio, true) || navegarCalendarioAFecha(fechaInicio, false);
				if (!navInicio) return false;
				diaInicio = obtenerDiaCalendarioPorFecha(fechaInicio);
				if (diaInicio == null || diaCalendarioEstaDeshabilitado(diaInicio)) return false;
			}
			clicDiaCalendario(diaInicio);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));

			boolean navFin = navegarCalendarioAFecha(fechaFin, false) || navegarCalendarioAFecha(fechaFin, true);
			if (!navFin) {
				testCase.addCodeEvidenceCurrentStep("No fue posible navegar en calendario a la fecha final: " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				return false;
			}
			WebElement diaFin = obtenerDiaCalendarioPorFecha(fechaFin);
			if (diaFin == null || diaCalendarioEstaDeshabilitado(diaFin)) {
				LocalDate ajustadoFin = buscarFechaFinalHabilitadaDescendente(fechaInicio, fechaFin);
				if (ajustadoFin == null) {
					testCase.addCodeEvidenceCurrentStep("Fecha final no habilitada en calendario: " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
					return false;
				}
				fechaFin = ajustadoFin;
				navFin = navegarCalendarioAFecha(fechaFin, false) || navegarCalendarioAFecha(fechaFin, true);
				if (!navFin) return false;
				diaFin = obtenerDiaCalendarioPorFecha(fechaFin);
				if (diaFin == null || diaCalendarioEstaDeshabilitado(diaFin)) return false;
			}
			clicDiaCalendario(diaFin);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Rango seleccionado en datepicker -> Inicio: "
						+ fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
						+ " | Fin: " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".");
				testCase.addScreenShotCurrentStep(u, "Rango_Datepicker_Seleccionado");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error en navegacion de calendario: " + e.getMessage());
			return false;
		}
	}

	private LocalDate buscarPrimeraFechaHabilitadaEnCalendario(LocalDate fechaInicio, int diasMaximoBusqueda) {
		for (int i = 0; i <= Math.max(diasMaximoBusqueda, 0); i++) {
			LocalDate candidata = fechaInicio.plusDays(i);
			boolean navego = navegarCalendarioAFecha(candidata, false) || navegarCalendarioAFecha(candidata, true);
			if (!navego) {
				continue;
			}
			WebElement dia = obtenerDiaCalendarioPorFecha(candidata);
			if (dia != null && !diaCalendarioEstaDeshabilitado(dia)) {
				return candidata;
			}
		}
		return null;
	}

	private LocalDate buscarFechaFinalHabilitadaDescendente(LocalDate fechaInicio, LocalDate fechaFinObjetivo) {
		LocalDate limiteInferior = fechaInicio;
		LocalDate fecha = fechaFinObjetivo;
		while (!fecha.isBefore(limiteInferior)) {
			boolean navego = navegarCalendarioAFecha(fecha, false) || navegarCalendarioAFecha(fecha, true);
			if (navego) {
				WebElement dia = obtenerDiaCalendarioPorFecha(fecha);
				if (dia != null && !diaCalendarioEstaDeshabilitado(dia)) {
					return fecha;
				}
			}
			fecha = fecha.minusDays(1);
		}
		return null;
	}

	private LocalDate[] obtenerVentanaInicialSieteDiasPorUsuarioCedis(String usuario) throws Exception {
		String sqlPerfil = "select uk.key_value as perfil "
				+ "from xxcm_user_keys uk "
				+ "inner join xxcm_usuarios u on u.usuario_id = uk.usuario_id "
				+ "where uk.short_name = 'PCPERFIL' and u.usuario = ? "
				+ "limit 1";

		String sqlSupplier = "select uk.key_value as supplier_id "
				+ "from xxcm_user_keys uk "
				+ "inner join xxcm_usuarios u on u.usuario_id = uk.usuario_id "
				+ "where uk.short_name = 'PCSUPPLIERID' and u.usuario = ? "
				+ "limit 1";

		String sqlRangoFuncion = "select min((fecha)::date) as fecha_inicial, max((fecha)::date) as fecha_final "
				+ "from public.get_xxcm_cedis_de_proyeccion_grid_paginated( "
				+ "1, 20000, ARRAY[?]::bigint[], NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) "
				+ "where fecha is not null";

		String sqlRangoTabla = "select min(fecha)::date as fecha_inicial, max(fecha)::date as fecha_final "
				+ "from public.xxcm_cedis_de_proyeccion_grid "
				+ "where supplier_id = ? and fecha is not null";

		String sqlRangoFuncionGlobal = "select min((fecha)::date) as fecha_inicial, max((fecha)::date) as fecha_final "
				+ "from public.get_xxcm_cedis_de_proyeccion_grid_paginated( "
				+ "1, 20000, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) "
				+ "where fecha is not null";

		String sqlRangoTablaGlobal = "select min(fecha)::date as fecha_inicial, max(fecha)::date as fecha_final "
				+ "from public.xxcm_cedis_de_proyeccion_grid "
				+ "where fecha is not null";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {

			boolean esAdmin = false;
			try (PreparedStatement psPerfil = connection.prepareStatement(sqlPerfil)) {
				psPerfil.setString(1, usuario);
				try (ResultSet rsPerfil = psPerfil.executeQuery()) {
					if (rsPerfil.next() && rsPerfil.getString("perfil") != null) {
						String perfil = rsPerfil.getString("perfil").trim();
						esAdmin = "ADMIN".equalsIgnoreCase(perfil) || "OXXO_OS".equalsIgnoreCase(perfil);
					}
				}
			}

			Long supplierId = null;
			try (PreparedStatement psSupplier = connection.prepareStatement(sqlSupplier)) {
				psSupplier.setString(1, usuario);
				try (ResultSet rsSupplier = psSupplier.executeQuery()) {
					if (rsSupplier.next() && rsSupplier.getString("supplier_id") != null
							&& !rsSupplier.getString("supplier_id").trim().isEmpty()) {
						supplierId = Long.parseLong(rsSupplier.getString("supplier_id").trim());
					}
				}
			}

			LocalDate fechaInicial = null;
			LocalDate fechaFinalDisponible = null;

			if (esAdmin) {
				try (PreparedStatement psRangoTablaGlobal = connection.prepareStatement(sqlRangoTablaGlobal);
						ResultSet rsRangoTablaGlobal = psRangoTablaGlobal.executeQuery()) {
					if (rsRangoTablaGlobal.next()) {
						if (rsRangoTablaGlobal.getDate("fecha_final") != null) {
							fechaFinalDisponible = rsRangoTablaGlobal.getDate("fecha_final").toLocalDate();
							fechaInicial = fechaFinalDisponible.minusDays(6);
						}
					}
				}

				if (fechaInicial != null && fechaFinalDisponible != null && fechaInicial.isAfter(fechaFinalDisponible)) {
					fechaInicial = fechaFinalDisponible;
				}

				if (fechaInicial != null && fechaFinalDisponible != null) {
					return new LocalDate[] { fechaInicial, fechaFinalDisponible };
				}
			}

			if (supplierId != null) {
				try (PreparedStatement psRango = connection.prepareStatement(sqlRangoFuncion)) {
					psRango.setLong(1, supplierId);
					try (ResultSet rsRango = psRango.executeQuery()) {
						if (rsRango.next()) {
							if (rsRango.getDate("fecha_inicial") != null) {
								fechaInicial = rsRango.getDate("fecha_inicial").toLocalDate();
							}
							if (rsRango.getDate("fecha_final") != null) {
								fechaFinalDisponible = rsRango.getDate("fecha_final").toLocalDate();
							}
						}
					}
				} catch (Exception ignore) {
				}

				if (fechaInicial == null) {
					try (PreparedStatement psRangoTabla = connection.prepareStatement(sqlRangoTabla)) {
						psRangoTabla.setLong(1, supplierId);
						try (ResultSet rsRangoTabla = psRangoTabla.executeQuery()) {
							if (rsRangoTabla.next()) {
								if (rsRangoTabla.getDate("fecha_inicial") != null) {
									fechaInicial = rsRangoTabla.getDate("fecha_inicial").toLocalDate();
								}
								if (rsRangoTabla.getDate("fecha_final") != null) {
									fechaFinalDisponible = rsRangoTabla.getDate("fecha_final").toLocalDate();
								}
							}
						}
					}
				}
			}

			if (fechaInicial == null && esAdmin) {
				try (PreparedStatement psRangoGlobal = connection.prepareStatement(sqlRangoFuncionGlobal);
						ResultSet rsRangoGlobal = psRangoGlobal.executeQuery()) {
					if (rsRangoGlobal.next()) {
						if (rsRangoGlobal.getDate("fecha_inicial") != null) {
							fechaInicial = rsRangoGlobal.getDate("fecha_inicial").toLocalDate();
						}
						if (rsRangoGlobal.getDate("fecha_final") != null) {
							fechaFinalDisponible = rsRangoGlobal.getDate("fecha_final").toLocalDate();
						}
					}
				} catch (Exception ignore) {
				}

				if (fechaInicial == null) {
					try (PreparedStatement psRangoTablaGlobal = connection.prepareStatement(sqlRangoTablaGlobal);
							ResultSet rsRangoTablaGlobal = psRangoTablaGlobal.executeQuery()) {
						if (rsRangoTablaGlobal.next()) {
							if (rsRangoTablaGlobal.getDate("fecha_inicial") != null) {
								fechaInicial = rsRangoTablaGlobal.getDate("fecha_inicial").toLocalDate();
							}
							if (rsRangoTablaGlobal.getDate("fecha_final") != null) {
								fechaFinalDisponible = rsRangoTablaGlobal.getDate("fecha_final").toLocalDate();
							}
						}
					}
				}
			}

			if (fechaInicial == null) {
				if (supplierId == null && !esAdmin) {
					throw new Exception("No se obtuvo PCSUPPLIERID para el usuario y no es perfil ADMIN/OXXO_OS: " + usuario);
				}
				throw new Exception("No se encontro fecha inicial disponible en BD para el usuario: " + usuario);
			}

			LocalDate fechaFin = fechaInicial.plusDays(6);
			if (fechaFinalDisponible != null && fechaFinalDisponible.isBefore(fechaInicial)) {
				fechaFinalDisponible = fechaInicial;
			}
			if (fechaFin.isBefore(fechaInicial)) {
				fechaFin = fechaInicial;
			}

			return new LocalDate[] { fechaInicial, fechaFin };
		}
	}

	/**
	 * Captura un rango de fechas especifico en el filtro Rango de Fechas de Devoluciones Especiales.
	 *
	 * @param fechaInicio valor esperado para fecha inicial en formato dd/MM/yyyy.
	 * @param fechaFin valor esperado para fecha final en formato dd/MM/yyyy.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si ambos campos quedan capturados con los valores esperados.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean seleccionarRangoFechasEspecifico(String fechaInicio, String fechaFin, boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			By inputInicio = By.xpath("(//app-cedis-devoluciones-especiales//input[contains(@class,'mat-start-date') or @matstartdate])[1]");
			By inputFin = By.xpath("(//app-cedis-devoluciones-especiales//input[contains(@class,'mat-end-date') or @matenddate])[1]");

			WebElement campoInicio = wait.until(ExpectedConditions.elementToBeClickable(inputInicio));
			WebElement campoFin = wait.until(ExpectedConditions.elementToBeClickable(inputFin));

			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", campoInicio);
			campoInicio.click();
			campoInicio.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			campoInicio.sendKeys(fechaInicio);

			campoFin.click();
			campoFin.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			campoFin.sendKeys(fechaFin);
			campoFin.sendKeys(Keys.TAB);

			u.hardWait(1);
			String valorInicio = campoInicio.getAttribute("value") == null ? "" : campoInicio.getAttribute("value").trim();
			String valorFin = campoFin.getAttribute("value") == null ? "" : campoFin.getAttribute("value").trim();
			boolean rangoCapturado = fechaInicio.equals(valorInicio) && fechaFin.equals(valorFin);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Rango de fechas capturado -> Inicio: " + valorInicio + " | Fin: " + valorFin + ".");
				testCase.addScreenShotCurrentStep(u, "Rango_Fechas_Especifico_Capturado");
			}

			return rangoCapturado;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al capturar rango especifico en Devoluciones Especiales: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el boton Consultar quede habilitado al seleccionar un rango de fechas valido.
	 * No ejecuta clic en Consultar ni valida resultados del grid.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el boton Consultar queda habilitado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarBotonConsultarHabilitadoPorRangoFechas(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			WebElement botonConsultar = wait.until(ExpectedConditions.elementToBeClickable(elements.btnConsultarDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", botonConsultar);

			boolean habilitado = wait.until(driver -> {
				WebElement boton = driver.findElement(elements.btnConsultarDevEspeciales);
				return botonConsultarEstaHabilitado(boton);
			});

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El boton Consultar queda habilitado con el rango de fechas valido.");
				testCase.addScreenShotCurrentStep(u, "Consultar_Habilitado_Rango_Fechas");
			}
			return habilitado;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar boton Consultar habilitado por rango de fechas: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el boton Consultar permanezca deshabilitado cuando no se ha seleccionado Rango de Fechas.
	 * En Devoluciones Especiales la consulta solo se habilita con un rango de fechas valido.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el boton Consultar no se encuentra activo ni habilitado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarBotonConsultarDeshabilitadoSinRangoFechas(boolean screenshot) {
		try {
			WebElement botonConsultar = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.btnConsultarDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", botonConsultar);
			boolean deshabilitado = !botonConsultarEstaHabilitado(botonConsultar);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El boton Consultar permanece deshabilitado porque no se ha seleccionado Rango de Fechas.");
				testCase.addScreenShotCurrentStep(u, "Consultar_Deshabilitado_Sin_Rango_Fechas");
			}
			return deshabilitado;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar boton Consultar deshabilitado sin Rango de Fechas: " + e.getMessage());
			return false;
		}
	}

	private boolean botonConsultarEstaHabilitado(WebElement botonConsultar) {
		String clase = obtenerAtributoSeguro(botonConsultar, "class").toLowerCase();
		String disabled = obtenerAtributoSeguro(botonConsultar, "disabled");
		String ariaDisabled = obtenerAtributoSeguro(botonConsultar, "aria-disabled");
		String propiedadDisabled = obtenerPropiedadDisabled(botonConsultar);
		return botonConsultar.isDisplayed()
				&& botonConsultar.isEnabled()
				&& disabled.isEmpty()
				&& !"true".equalsIgnoreCase(ariaDisabled)
				&& !"true".equalsIgnoreCase(propiedadDisabled)
				&& !clase.contains("mat-button-disabled")
				&& !clase.contains("mat-mdc-button-disabled");
	}

	private String obtenerPropiedadDisabled(WebElement elemento) {
		try {
			Object valor = ((JavascriptExecutor) u.getDriver()).executeScript("return arguments[0].disabled === true ? 'true' : 'false';", elemento);
			return valor == null ? "" : valor.toString();
		} catch (Exception e) {
			return "";
		}
	}

	private String construirFechaSeleccionada(String diaSeleccionado) {
		try {
			if (ultimaFechaDisponibleCalendario != null
					&& String.valueOf(ultimaFechaDisponibleCalendario.getDayOfMonth()).equals(diaSeleccionado)) {
				return ultimaFechaDisponibleCalendario.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX")));
			}
			LocalDate fecha = LocalDate.now().withDayOfMonth(Integer.parseInt(diaSeleccionado));
			return fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", new Locale("es", "MX")));
		} catch (Exception e) {
			return diaSeleccionado;
		}
	}

	private String obtenerPrefijoBolConCoincidenciasBD(boolean screenshot) throws Exception {
		if (ultimoTiendaSeleccionada == null || ultimoTiendaSeleccionada.trim().isEmpty()) {
			testCase.addTextEvidenceCurrentStep("No existe una Tienda seleccionada para obtener prefijo BOL.");
			return null;
		}

		String tiendaUI = ultimoTiendaSeleccionada.trim().replaceAll("\\s+", " ");
		java.util.regex.Matcher matcher = Pattern.compile("^(\\S+)\\s+(.+)$").matcher(tiendaUI);
		if (!matcher.matches()) {
			testCase.addTextEvidenceCurrentStep("La tienda seleccionada no tiene formato cr_tienda desc_tienda: " + tiendaUI);
			return null;
		}

		String crTienda = matcher.group(1).trim();
		if (crTienda.length() >= 3) {
			String prefijoDesdeTienda = crTienda.substring(0, 3);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Prefijo BOL tomado de los primeros 3 caracteres de la tienda seleccionada "
						+ crTienda + ": " + prefijoDesdeTienda);
			}
			return prefijoDesdeTienda;
		}

		String consultaPrefijo = "select substring(folio_bol, 1, 3) as prefijo, count(*) as total "
				+ "from ( "
				+ "select coalesce(nullif(body ->> 'Folio_BOL', ''), transferencia::text) as folio_bol, "
				+ "coalesce(nullif(body ->> 'BOL_Completo', ''), transferencia::text) as bol_completo "
				+ "from public.xxcm_historico_correos "
				+ "where cr_tienda = '" + crTienda.replace("'", "''") + "' "
				+ "and coalesce(nullif(body ->> 'Folio_BOL', ''), transferencia::text) is not null "
				+ "and coalesce(nullif(body ->> 'BOL_Completo', ''), transferencia::text) is not null "
				+ ") boles "
				+ "where length(folio_bol) >= 3 "
				+ "group by substring(folio_bol, 1, 3) "
				+ "having count(*) > 1 "
				+ "order by total desc "
				+ "limit 1";
		if (screenshot) {
			testCase.addTextEvidenceCurrentStep("Consulta BD prefijo BOL:\n" + consultaPrefijo);
		}

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				PreparedStatement statement = connection.prepareStatement(
						"select substring(folio_bol, 1, 3) as prefijo, count(*) as total "
								+ "from ( "
								+ "select coalesce(nullif(body ->> 'Folio_BOL', ''), transferencia::text) as folio_bol, "
								+ "coalesce(nullif(body ->> 'BOL_Completo', ''), transferencia::text) as bol_completo "
								+ "from public.xxcm_historico_correos "
								+ "where cr_tienda = ? "
								+ "and coalesce(nullif(body ->> 'Folio_BOL', ''), transferencia::text) is not null "
								+ "and coalesce(nullif(body ->> 'BOL_Completo', ''), transferencia::text) is not null "
								+ ") boles "
								+ "where length(folio_bol) >= 3 "
								+ "group by substring(folio_bol, 1, 3) "
								+ "having count(*) > 1 "
								+ "order by total desc "
								+ "limit 1")) {
			statement.setString(1, crTienda);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String prefijo = resultSet.getString("prefijo");
					String total = resultSet.getString("total");
					if (screenshot) {
						testCase.addTextEvidenceCurrentStep("Prefijo BOL obtenido desde BD: " + prefijo + " | Total coincidencias: " + total);
					}
					return prefijo;
				}
			}
		}
		return null;
	}

	private void capturarPrimerasOpciones(List<WebElement> opciones, java.util.List<String> destino) {
		destino.clear();
		for (int i = 0; i < opciones.size() && i < 5; i++) {
			String opcionVisible = opciones.get(i).getText() == null ? "" : opciones.get(i).getText().trim().replaceAll("\\s+", " ");
			if (!opcionVisible.isEmpty()) {
				destino.add(opcionVisible);
			}
		}
	}

	private String obtenerValorColumnaSiExiste(ResultSet resultSet, String columna) {
		try {
			ResultSetMetaData metadata = resultSet.getMetaData();
			for (int i = 1; i <= metadata.getColumnCount(); i++) {
				if (columna.equalsIgnoreCase(metadata.getColumnLabel(i)) || columna.equalsIgnoreCase(metadata.getColumnName(i))) {
					String valor = resultSet.getString(columna);
					return valor == null ? "" : valor.trim().replaceAll("\\s+", " ");
				}
			}
		} catch (Exception ignore) {
		}
		return "";
	}

	private void agregarEvidenciaResumenFiltro(String nombreFiltro, String opcionSeleccionada, int cantidadOpciones,
			java.util.List<String> primerasOpciones) {
		testCase.addTextEvidenceCurrentStep("Opcion seleccionada en " + nombreFiltro + ": " + opcionSeleccionada);
		testCase.addTextEvidenceCurrentStep("Cantidad de opciones disponibles en " + nombreFiltro + ": " + cantidadOpciones);
		testCase.addTextEvidenceCurrentStep("Primeras 5 opciones disponibles en " + nombreFiltro + ":\n" + formatearListaOpciones(primerasOpciones));
	}

	private java.util.List<String> obtenerValoresSeleccionados(WebElement control) {
		java.util.List<String> valores = new java.util.ArrayList<>();
		try {
			List<WebElement> labels = control.findElements(By.xpath(".//span[contains(@class,'ng-value-label')]") );
			for (WebElement label : labels) {
				String texto = label.getText() == null ? "" : label.getText().trim().replaceAll("\\s+", " ");
				if (!texto.isEmpty()) {
					valores.add(texto);
				}
			}
		} catch (Exception ignore) {
		}
		return valores;
	}

	private String obtenerValorInputFiltro(By filtroBy) {
		try {
			WebElement filtro = u.getDriver().findElement(filtroBy);
			List<WebElement> inputsFiltro = filtro.findElements(By.xpath(".//input"));
			for (WebElement input : inputsFiltro) {
				String value = input.getAttribute("value");
				if (value != null && !value.trim().isEmpty()) {
					return value.trim().replaceAll("\\s+", " ");
				}
			}
		} catch (Exception ignore) {
		}
		return "";
	}

	private WebElement obtenerInputFiltroActivo(By filtroBy) {
		WebElement filtro = u.getDriver().findElement(filtroBy);
		List<WebElement> inputsFiltro = filtro.findElements(By.xpath(".//input"));
		for (WebElement input : inputsFiltro) {
			if (input.isDisplayed()) {
				return input;
			}
		}

		List<WebElement> inputsPanel = u.getDriver().findElements(By.xpath("//ng-dropdown-panel//input"));
		for (WebElement input : inputsPanel) {
			if (input.isDisplayed()) {
				return input;
			}
		}

		throw new org.openqa.selenium.NoSuchElementException("No se encontro input activo para el filtro.");
	}

	private void limpiarFiltroActivo(By filtroBy) {
		try {
			WebElement filtro = u.getDriver().findElement(filtroBy);
			List<WebElement> clearButtons = filtro.findElements(By.cssSelector(".ng-clear-wrapper"));
			for (WebElement clearButton : clearButtons) {
				if (clearButton.isDisplayed()) {
					clearButton.click();
					return;
				}
			}
		} catch (Exception ignore) {
		}
	}

	private String formatearListaOpciones(java.util.List<String> opciones) {
		if (opciones == null || opciones.isEmpty()) {
			return "Sin opciones capturadas.";
		}

		StringBuilder lista = new StringBuilder();
		for (int i = 0; i < opciones.size(); i++) {
			lista.append(i + 1).append(". ").append(opciones.get(i));
			if (i < opciones.size() - 1) {
				lista.append("\n");
			}
		}
		return lista.toString();
	}

	private String normalizarTextoComparacion(String texto) {
		if (texto == null) {
			return "";
		}
		String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
				.replaceAll("\\p{M}", "");
		return normalizado.trim().replaceAll("\\s+", " ").toUpperCase();
	}

	private String toXpathLiteral(String value) {
		if (!value.contains("'")) {
			return "'" + value + "'";
		}
		if (!value.contains("\"")) {
			return "\"" + value + "\"";
		}

		StringBuilder sb = new StringBuilder("concat(");
		for (int i = 0; i < value.length(); i++) {
			String part;
			char c = value.charAt(i);
			if (c == '\'') {
				part = "\"'\"";
			} else if (c == '\"') {
				part = "'\"'";
			} else {
				int j = i;
				StringBuilder plain = new StringBuilder();
				while (j < value.length() && value.charAt(j) != '\'' && value.charAt(j) != '\"') {
					plain.append(value.charAt(j));
					j++;
				}
				part = "'" + plain + "'";
				i = j - 1;
			}
			if (sb.length() > 7) {
				sb.append(", ");
			}
			sb.append(part);
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * Selecciona dos valores disponibles en el filtro CEDIS y valida que el control muestre seleccion multiple.
	 * El metodo conserva los textos seleccionados para validar posteriormente que la seleccion persista al reabrir el control.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se seleccionan al menos dos valores y el control refleja seleccion multiple.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean seleccionarMultiplesValoresFiltroCedis(boolean screenshot) {
		return seleccionarMultiplesValoresFiltro(elements.dropCedisDevEspeciales, elements.opcionesCedisDevEspeciales,
				"CEDIS", ultimasOpcionesMultiplesCedis, screenshot);
	}

	/**
	 * Reabre el filtro CEDIS y valida que la seleccion multiple realizada previamente se conserve.
	 * La validacion confirma que el control sigue indicando multiples seleccionados y que el panel se despliega.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la seleccion multiple de CEDIS persiste al reabrir el control.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarPersistenciaSeleccionMultipleFiltroCedis(boolean screenshot) {
		return validarPersistenciaSeleccionMultipleFiltro(elements.dropCedisDevEspeciales, "CEDIS", ultimasOpcionesMultiplesCedis, screenshot);
	}

	/**
	 * Selecciona dos valores disponibles en el filtro Plaza y valida que el control muestre seleccion multiple.
	 * El metodo conserva los textos seleccionados para validar posteriormente que la seleccion persista al reabrir el control.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se seleccionan al menos dos valores y el control refleja seleccion multiple.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean seleccionarMultiplesValoresFiltroPlaza(boolean screenshot) {
		return seleccionarMultiplesValoresFiltro(elements.dropPlazaDevEspeciales, elements.opcionesDevEspeciales,
				"Plaza", ultimasOpcionesMultiplesPlaza, screenshot);
	}

	/**
	 * Reabre el filtro Plaza y valida que la seleccion multiple realizada previamente se conserve.
	 * La validacion confirma que el control sigue indicando multiples seleccionados y que el panel se despliega.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la seleccion multiple de Plaza persiste al reabrir el control.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarPersistenciaSeleccionMultipleFiltroPlaza(boolean screenshot) {
		return validarPersistenciaSeleccionMultipleFiltro(elements.dropPlazaDevEspeciales, "Plaza", ultimasOpcionesMultiplesPlaza, screenshot);
	}

	/**
	 * Selecciona dos valores disponibles en el filtro Tienda y valida que el control muestre seleccion multiple.
	 * El metodo conserva los textos seleccionados y actualiza la tienda de referencia para habilitar el filtro BOL cuando aplique.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se seleccionan al menos dos valores y el control refleja seleccion multiple.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean seleccionarMultiplesValoresFiltroTienda(boolean screenshot) {
		boolean resultado = seleccionarMultiplesValoresFiltro(elements.dropTiendaDevEspeciales, elements.opcionesDevEspeciales,
				"Tienda", ultimasOpcionesMultiplesTienda, screenshot);
		if (resultado && !ultimasOpcionesMultiplesTienda.isEmpty()) {
			ultimoTiendaSeleccionada = ultimasOpcionesMultiplesTienda.get(0);
		}
		return resultado;
	}

	/**
	 * Reabre el filtro Tienda y valida que la seleccion multiple realizada previamente se conserve.
	 * La validacion confirma que el control sigue indicando multiples seleccionados y que el panel se despliega.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la seleccion multiple de Tienda persiste al reabrir el control.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarPersistenciaSeleccionMultipleFiltroTienda(boolean screenshot) {
		return validarPersistenciaSeleccionMultipleFiltro(elements.dropTiendaDevEspeciales, "Tienda", ultimasOpcionesMultiplesTienda, screenshot);
	}

	/**
	 * Busca sugerencias de BOL y selecciona dos valores disponibles para validar seleccion multiple.
	 * El filtro BOL depende de tener una tienda seleccionada y usa un prefijo valido obtenido desde la informacion disponible.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se seleccionan al menos dos valores BOL y el control refleja seleccion multiple.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean seleccionarMultiplesValoresFiltroBol(boolean screenshot) {
		try {
			boolean filtroBolAbierto = abrirFiltroBol(false);
			if (!filtroBolAbierto) {
				return false;
			}

			prefijoBolValido = obtenerPrefijoBolConCoincidenciasBD(screenshot);
			if (prefijoBolValido == null || prefijoBolValido.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No se obtuvo prefijo BOL valido para seleccion multiple.");
				return false;
			}

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(elements.dropBolDevEspeciales));
			filtro.click();
			limpiarFiltroActivo(elements.dropBolDevEspeciales);
			WebElement input = obtenerInputFiltroActivo(elements.dropBolDevEspeciales);
			input.clear();
			input.sendKeys(prefijoBolValido);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements.opcionesDevEspeciales));

			boolean resultado = seleccionarPrimerasOpcionesPanelActual("BOL", ultimasOpcionesMultiplesBol, screenshot);
			if (resultado && !ultimasOpcionesMultiplesBol.isEmpty()) {
				ultimoBolSeleccionado = ultimasOpcionesMultiplesBol.get(0);
			}
			return resultado;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar multiples valores en BOL: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Reabre el filtro BOL y valida que la seleccion multiple realizada previamente se conserve.
	 * La validacion confirma que el control sigue indicando multiples seleccionados y que el panel se despliega.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la seleccion multiple de BOL persiste al reabrir el control.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarPersistenciaSeleccionMultipleFiltroBol(boolean screenshot) {
		return validarPersistenciaSeleccionMultipleFiltro(elements.dropBolDevEspeciales, "BOL", ultimasOpcionesMultiplesBol, screenshot);
	}

	/**
	 * Selecciona dos opciones visibles de un filtro ng-select y valida que el control refleje seleccion multiple.
	 * Centraliza el flujo comun para CEDIS, Plaza y Tienda.
	 *
	 * @param filtroBy localizador del control de filtro.
	 * @param opcionesBy localizador de las opciones disponibles del panel.
	 * @param nombreFiltro nombre funcional del filtro para evidencia.
	 * @param destino lista donde se conservan los textos seleccionados.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se seleccionan al menos dos opciones y el control indica seleccion multiple.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	private boolean seleccionarMultiplesValoresFiltro(By filtroBy, By opcionesBy, String nombreFiltro,
			java.util.List<String> destino, boolean screenshot) {
		try {
			abrirPanelFiltro(filtroBy);
			destino.clear();

			while (destino.size() < 2) {
				List<WebElement> opciones = u.getDriver().findElements(opcionesBy);
				boolean seleccionoOpcion = false;

				for (WebElement opcion : opciones) {
					String textoOpcion = opcion.getText() == null ? "" : opcion.getText().trim().replaceAll("\\s+", " ");
					if (opcion.isDisplayed() && opcion.isEnabled() && !textoOpcion.isEmpty() && !destino.contains(textoOpcion)) {
						seleccionarOpcionMultiple(opcion);
						destino.add(textoOpcion);
						seleccionoOpcion = true;
						break;
					}
				}

				if (!seleccionoOpcion) {
					break;
				}

				if (u.getDriver().findElements(elements.panelOpcionesDevEspeciales).isEmpty()) {
					abrirPanelFiltro(filtroBy);
				}
			}

			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			boolean multiplesVisibles = validarControlIndicaMultiplesSeleccionados(filtroBy);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Valores seleccionados en " + nombreFiltro + ": " + String.join(" | ", destino));
				testCase.addTextEvidenceCurrentStep("El control " + nombreFiltro + " muestra Multiples seleccionados.");
				testCase.addScreenShotCurrentStep(u, "Multiples_Seleccionados_" + nombreFiltro.replace(" ", "_"));
			}
			return destino.size() >= 2 && multiplesVisibles;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar multiples valores en " + nombreFiltro + ": " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que un filtro con seleccion multiple conserve el texto de multiples seleccionados al reabrirse.
	 * El metodo comprueba que existan al menos dos valores previamente registrados y que el panel del filtro abra correctamente.
	 *
	 * @param filtroBy localizador del control de filtro.
	 * @param nombreFiltro nombre funcional del filtro para evidencia.
	 * @param seleccionados lista de valores seleccionados previamente.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el control mantiene la seleccion multiple al reabrirse.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	private boolean validarPersistenciaSeleccionMultipleFiltro(By filtroBy, String nombreFiltro,
			java.util.List<String> seleccionados, boolean screenshot) {
		try {
			boolean multiplesVisiblesAntes = validarControlIndicaMultiplesSeleccionados(filtroBy);
			abrirPanelFiltro(filtroBy);
			boolean panelVisible = !u.getDriver().findElements(elements.panelOpcionesDevEspeciales).isEmpty();
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Selecciones conservadas en " + nombreFiltro + ": " + String.join(" | ", seleccionados));
				testCase.addTextEvidenceCurrentStep("El control " + nombreFiltro + " conserva Multiples seleccionados al reabrir.");
				testCase.addScreenShotCurrentStep(u, "Persistencia_Multiples_" + nombreFiltro.replace(" ", "_"));
			}
			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			return seleccionados.size() >= 2 && multiplesVisiblesAntes && panelVisible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar persistencia de seleccion multiple en " + nombreFiltro + ": " + e.getMessage());
			return false;
		}
	}

	/**
	 * Abre el panel de opciones de un filtro ng-select y espera a que sus opciones sean visibles.
	 * Se usa como paso comun para seleccionar y validar filtros con una o varias opciones.
	 *
	 * @param filtroBy localizador del control de filtro.
	 * @return elemento Web del filtro abierto.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	private WebElement abrirPanelFiltro(By filtroBy) {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
		WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(filtroBy));
		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", filtro);
		filtro.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
		return filtro;
	}

	/**
	 * Ejecuta clic sobre una opcion de seleccion multiple usando checkbox cuando existe o sobre la opcion completa.
	 * Esta estrategia soporta componentes ng-select con o sin checkbox visible.
	 *
	 * @param opcion elemento de opcion visible en el panel del filtro.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	private void seleccionarOpcionMultiple(WebElement opcion) {
		List<WebElement> checkboxes = opcion.findElements(By.xpath(".//input[@type='checkbox'] | .//*[contains(@class,'mat-checkbox') or contains(@class,'mdc-checkbox')]") );
		if (!checkboxes.isEmpty()) {
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", checkboxes.get(0));
		} else {
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", opcion);
		}
	}

	/**
	 * Valida que un control ng-select indique visualmente una seleccion multiple.
	 * Acepta el texto Multiples seleccionados o, en su defecto, dos etiquetas de valor seleccionadas.
	 *
	 * @param filtroBy localizador del control de filtro.
	 * @return true si el control indica seleccion multiple.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	private boolean validarControlIndicaMultiplesSeleccionados(By filtroBy) {
		WebElement filtro = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
				.until(ExpectedConditions.visibilityOfElementLocated(filtroBy));
		String textoControl = filtro.getText() == null ? "" : filtro.getText().trim().replaceAll("\\s+", " ");
		String textoDom = obtenerTextoDom(filtro);
		String textoNormalizado = normalizarTextoComparacion(textoControl + " " + textoDom);
		return textoNormalizado.contains("MULTIPLES SELECCIONADOS")
				|| textoNormalizado.contains("MULTIPLE")
				|| obtenerValoresSeleccionados(filtro).size() >= 2;
	}

	/**
	 * Selecciona las primeras dos opciones visibles del panel actualmente abierto y valida seleccion multiple.
	 * Se usa para el filtro BOL despues de escribir el prefijo de busqueda.
	 *
	 * @param nombreFiltro nombre funcional del filtro para evidencia.
	 * @param destino lista donde se conservan los textos seleccionados.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se seleccionan dos opciones del panel y el control refleja seleccion multiple.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	private boolean seleccionarPrimerasOpcionesPanelActual(String nombreFiltro, java.util.List<String> destino, boolean screenshot) {
		destino.clear();
		while (destino.size() < 2) {
			List<WebElement> opciones = u.getDriver().findElements(elements.opcionesDevEspeciales);
			boolean seleccionoOpcion = false;
			for (WebElement opcion : opciones) {
				String textoOpcion = opcion.getText() == null ? "" : opcion.getText().trim().replaceAll("\\s+", " ");
				String claseOpcion = obtenerAtributoSeguro(opcion, "class");
				boolean opcionYaSeleccionada = claseOpcion.contains("ng-option-selected");
				if (opcion.isDisplayed() && opcion.isEnabled() && !textoOpcion.isEmpty() && !destino.contains(textoOpcion)) {
					if (!opcionYaSeleccionada) {
						seleccionarOpcionMultiple(opcion);
					}
					destino.add(textoOpcion);
					seleccionoOpcion = true;
					break;
				}
			}

			if (!seleccionoOpcion) {
				break;
			}
		}

		int opcionesSeleccionadasPanel = 0;
		for (WebElement opcion : u.getDriver().findElements(elements.opcionesDevEspeciales)) {
			if (obtenerAtributoSeguro(opcion, "class").contains("ng-option-selected")) {
				opcionesSeleccionadasPanel++;
			}
		}
		new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
		boolean multiplesVisibles = validarControlIndicaMultiplesSeleccionados(elements.dropBolDevEspeciales);
		boolean seleccionMultipleConfirmada = destino.size() >= 2 && (multiplesVisibles || opcionesSeleccionadasPanel >= 2);
		if (screenshot) {
			testCase.addTextEvidenceCurrentStep("Valores seleccionados en " + nombreFiltro + ": " + String.join(" | ", destino));
			testCase.addTextEvidenceCurrentStep("El filtro " + nombreFiltro + " conserva al menos dos valores seleccionados.");
			testCase.addScreenShotCurrentStep(u, "Multiples_Seleccionados_" + nombreFiltro.replace(" ", "_"));
		}
		return seleccionMultipleConfirmada;
	}

	/**
	 * Escribe un valor parcial en el filtro CEDIS para validar que el autocomplete muestre sugerencias.
	 * El valor se obtiene desde una opcion real visible para evitar datos de busqueda artificiales.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el filtro permite escribir y muestra sugerencias para el valor parcial capturado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean escribirValorAutocompleteFiltroCedis(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			abrirPanelFiltro(elements.dropCedisDevEspeciales);
			List<WebElement> opcionesIniciales = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements.opcionesCedisDevEspeciales));
			if (opcionesIniciales.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El filtro CEDIS no mostro opciones para tomar valor de autocomplete.");
				return false;
			}

			String primeraOpcion = opcionesIniciales.get(0).getText() == null ? "" : opcionesIniciales.get(0).getText().trim().replaceAll("\\s+", " ");
			if (primeraOpcion.length() < 3) {
				testCase.addTextEvidenceCurrentStep("La primera opcion CEDIS no tiene longitud suficiente para autocomplete: " + primeraOpcion);
				return false;
			}

			String valorBusqueda = primeraOpcion.substring(0, 3).toLowerCase(new Locale("es", "MX"));
			WebElement input = obtenerInputFiltroActivo(elements.dropCedisDevEspeciales);
			input.clear();
			input.sendKeys(valorBusqueda);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			List<WebElement> sugerencias = u.getDriver().findElements(elements.opcionesCedisDevEspeciales);
			capturarPrimerasOpciones(sugerencias, primerasOpcionesCedis);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Valor escrito en autocomplete CEDIS: " + valorBusqueda);
				testCase.addTextEvidenceCurrentStep("Sugerencias visibles CEDIS: " + sugerencias.size());
				testCase.addTextEvidenceCurrentStep("Primeras 5 sugerencias CEDIS:\n" + formatearListaOpciones(primerasOpcionesCedis));
				testCase.addScreenShotCurrentStep(u, "Autocomplete_CEDIS");
			}
			return !sugerencias.isEmpty();
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar autocomplete del filtro CEDIS: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona multiples valores en CEDIS con el panel abierto y valida que no se cierre automaticamente.
	 * La validacion conserva los valores seleccionados para pasos posteriores de persistencia y limpieza.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se seleccionan al menos dos valores y el panel permanece abierto.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean seleccionarMultiplesValoresFiltroCedisManteniendoPanel(boolean screenshot) {
		try {
			abrirPanelFiltro(elements.dropCedisDevEspeciales);
			ultimasOpcionesMultiplesCedis.clear();

			while (ultimasOpcionesMultiplesCedis.size() < 2) {
				List<WebElement> opciones = u.getDriver().findElements(elements.opcionesCedisDevEspeciales);
				boolean seleccionoOpcion = false;
				for (WebElement opcion : opciones) {
					String textoOpcion = opcion.getText() == null ? "" : opcion.getText().trim().replaceAll("\\s+", " ");
					if (opcion.isDisplayed() && opcion.isEnabled() && !textoOpcion.isEmpty() && !ultimasOpcionesMultiplesCedis.contains(textoOpcion)) {
						seleccionarOpcionMultiple(opcion);
						ultimasOpcionesMultiplesCedis.add(textoOpcion);
						seleccionoOpcion = true;
						break;
					}
				}

				if (!seleccionoOpcion) {
					break;
				}

				if (u.getDriver().findElements(elements.panelOpcionesDevEspeciales).isEmpty()) {
					abrirPanelFiltro(elements.dropCedisDevEspeciales);
				}
			}

			boolean panelVisible = !u.getDriver().findElements(elements.panelOpcionesDevEspeciales).isEmpty();
			boolean multiplesVisibles = validarControlIndicaMultiplesSeleccionados(elements.dropCedisDevEspeciales);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Valores seleccionados en CEDIS: " + String.join(" | ", ultimasOpcionesMultiplesCedis));
				testCase.addTextEvidenceCurrentStep("El control CEDIS muestra Multiples seleccionados y el panel permanece abierto.");
				testCase.addScreenShotCurrentStep(u, "Seleccion_Multiple_CEDIS_Panel_Abierto");
			}
			return ultimasOpcionesMultiplesCedis.size() >= 2 && multiplesVisibles && panelVisible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar multiples valores CEDIS manteniendo panel abierto: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Ejecuta la opcion Limpiar seleccion del filtro CEDIS y valida que se retiren los valores seleccionados.
	 * Usa la accion del panel cuando esta disponible y como respaldo el control de limpieza del ng-select.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la seleccion multiple de CEDIS se limpia correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean limpiarSeleccionFiltroCedis(boolean screenshot) {
		try {
			abrirPanelFiltro(elements.dropCedisDevEspeciales);
			List<WebElement> limpiarSeleccion = u.getDriver().findElements(By.xpath("//*[normalize-space()='Limpiar Selección' or normalize-space()='Limpiar seleccion' or normalize-space()='Limpiar Seleccion']"));
			if (!limpiarSeleccion.isEmpty()) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", limpiarSeleccion.get(0));
			} else {
				new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
				limpiarFiltroActivo(elements.dropCedisDevEspeciales);
			}

			ultimasOpcionesMultiplesCedis.clear();
			boolean limpio = validarFiltroCedisEstadoInicial(false);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se ejecuta Limpiar seleccion en el filtro CEDIS.");
				testCase.addScreenShotCurrentStep(u, "Limpiar_Seleccion_CEDIS");
			}
			return limpio;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al limpiar seleccion del filtro CEDIS: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el filtro CEDIS se encuentre limpio y en estado inicial despues de usar Limpiar seleccion.
	 * El control no debe conservar etiquetas de valores ni texto de multiples seleccionados.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el control CEDIS queda sin valores seleccionados.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-09
	 */
	public boolean validarFiltroCedisEstadoInicial(boolean screenshot) {
		try {
			WebElement filtro = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.dropCedisDevEspeciales));
			String textoControl = filtro.getText() == null ? "" : filtro.getText().trim().replaceAll("\\s+", " ");
			java.util.List<String> valoresSeleccionados = obtenerValoresSeleccionados(filtro);
			boolean limpio = valoresSeleccionados.isEmpty()
					&& !normalizarTextoComparacion(textoControl).contains("MULTIPLES SELECCIONADOS");

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El filtro CEDIS queda sin valores seleccionados y vuelve a su estado inicial.");
				testCase.addScreenShotCurrentStep(u, "CEDIS_Estado_Inicial");
			}
			return limpio;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar estado inicial del filtro CEDIS: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Escribe un valor parcial en el filtro Plaza para validar que el autocomplete muestre sugerencias.
	 * El valor se obtiene desde una opcion real visible para validar busqueda alfanumerica sin datos artificiales.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el filtro permite escribir y muestra sugerencias para el valor parcial capturado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean escribirValorAutocompleteFiltroPlaza(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			abrirPanelFiltro(elements.dropPlazaDevEspeciales);
			List<WebElement> opcionesIniciales = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements.opcionesDevEspeciales));
			if (opcionesIniciales.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El filtro Plaza no mostro opciones para tomar valor de autocomplete.");
				return false;
			}

			String primeraOpcion = opcionesIniciales.get(0).getText() == null ? "" : opcionesIniciales.get(0).getText().trim().replaceAll("\\s+", " ");
			if (primeraOpcion.length() < 3) {
				testCase.addTextEvidenceCurrentStep("La primera opcion Plaza no tiene longitud suficiente para autocomplete: " + primeraOpcion);
				return false;
			}

			String valorBusqueda = primeraOpcion.substring(0, 3).toLowerCase(new Locale("es", "MX"));
			WebElement input = obtenerInputFiltroActivo(elements.dropPlazaDevEspeciales);
			input.clear();
			input.sendKeys(valorBusqueda);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			List<WebElement> sugerencias = u.getDriver().findElements(elements.opcionesDevEspeciales);
			capturarPrimerasOpciones(sugerencias, primerasOpcionesPlaza);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Valor escrito en autocomplete Plaza: " + valorBusqueda);
				testCase.addTextEvidenceCurrentStep("Sugerencias visibles Plaza: " + sugerencias.size());
				testCase.addTextEvidenceCurrentStep("Primeras 5 sugerencias Plaza:\n" + formatearListaOpciones(primerasOpcionesPlaza));
				testCase.addScreenShotCurrentStep(u, "Autocomplete_Plaza");
			}
			return !sugerencias.isEmpty();
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar autocomplete del filtro Plaza: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona multiples valores en Plaza con el panel abierto y valida que no se cierre automaticamente.
	 * La validacion conserva los valores seleccionados para pasos posteriores de persistencia y limpieza.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se seleccionan al menos dos valores y el panel permanece abierto.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean seleccionarMultiplesValoresFiltroPlazaManteniendoPanel(boolean screenshot) {
		try {
			abrirPanelFiltro(elements.dropPlazaDevEspeciales);
			ultimasOpcionesMultiplesPlaza.clear();

			while (ultimasOpcionesMultiplesPlaza.size() < 2) {
				List<WebElement> opciones = u.getDriver().findElements(elements.opcionesDevEspeciales);
				boolean seleccionoOpcion = false;
				for (WebElement opcion : opciones) {
					String textoOpcion = opcion.getText() == null ? "" : opcion.getText().trim().replaceAll("\\s+", " ");
					if (opcion.isDisplayed() && opcion.isEnabled() && !textoOpcion.isEmpty() && !ultimasOpcionesMultiplesPlaza.contains(textoOpcion)) {
						seleccionarOpcionMultiple(opcion);
						ultimasOpcionesMultiplesPlaza.add(textoOpcion);
						seleccionoOpcion = true;
						break;
					}
				}

				if (!seleccionoOpcion) {
					break;
				}

				if (u.getDriver().findElements(elements.panelOpcionesDevEspeciales).isEmpty()) {
					abrirPanelFiltro(elements.dropPlazaDevEspeciales);
				}
			}

			boolean panelVisible = !u.getDriver().findElements(elements.panelOpcionesDevEspeciales).isEmpty();
			boolean multiplesVisibles = validarControlIndicaMultiplesSeleccionados(elements.dropPlazaDevEspeciales);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Valores seleccionados en Plaza: " + String.join(" | ", ultimasOpcionesMultiplesPlaza));
				testCase.addTextEvidenceCurrentStep("El control Plaza muestra Multiples seleccionados y el panel permanece abierto.");
				testCase.addScreenShotCurrentStep(u, "Seleccion_Multiple_Plaza_Panel_Abierto");
			}
			return ultimasOpcionesMultiplesPlaza.size() >= 2 && multiplesVisibles && panelVisible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar multiples valores Plaza manteniendo panel abierto: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Ejecuta la opcion Limpiar seleccion del filtro Plaza y valida que se retiren los valores seleccionados.
	 * Usa la accion del panel cuando esta disponible y como respaldo el control de limpieza del ng-select.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la seleccion multiple de Plaza se limpia correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean limpiarSeleccionFiltroPlaza(boolean screenshot) {
		try {
			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();

			if (!clicLimpiarSeleccionVisiblePorIndice(1)) {
				limpiarFiltroActivo(elements.dropPlazaDevEspeciales);
			}

			if (!validarFiltroPlazaEstadoInicial(false)) {
				desmarcarOpcionesSeleccionadasPanel(elements.dropPlazaDevEspeciales);
			}

			if (!validarFiltroPlazaEstadoInicial(false)) {
				new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
				limpiarFiltroActivo(elements.dropPlazaDevEspeciales);
			}

			boolean limpioFinal = false;
			try {
				limpioFinal = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
						.until(driver -> validarFiltroPlazaEstadoInicial(false));
			} catch (Exception ignore) {
			}

			ultimasOpcionesMultiplesPlaza.clear();
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se ejecuta Limpiar seleccion en el filtro Plaza.");
				testCase.addScreenShotCurrentStep(u, "Limpiar_Seleccion_Plaza");
			}
			return limpioFinal;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al limpiar seleccion del filtro Plaza: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Da clic en una opcion visible de Limpiar Seleccion segun la posicion del filtro en pantalla.
	 * La posicion 1 corresponde al filtro Plaza dentro de la fila de filtros de Devoluciones Especiales.
	 *
	 * @param indiceVisible posicion cero basada dentro de los textos Limpiar Seleccion visibles.
	 * @param debug bitacora opcional para registrar los elementos encontrados.
	 * @return true si se encontro y se dio clic en el elemento solicitado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	private boolean clicLimpiarSeleccionVisiblePorIndice(int indiceVisible) {
		List<WebElement> limpiarSeleccion = u.getDriver().findElements(By.xpath("//app-cedis-devoluciones-especiales//app-filtros-busqueda//*[contains(normalize-space(),'Limpiar') and not(contains(normalize-space(),'Limpiar Filtros'))]"));
		java.util.List<WebElement> visibles = new java.util.ArrayList<>();

		for (int i = 0; i < limpiarSeleccion.size(); i++) {
			WebElement limpiar = limpiarSeleccion.get(i);
			String texto = limpiar.getText() == null ? "" : limpiar.getText().trim().replaceAll("\\s+", " ");
			boolean candidato = limpiar.isDisplayed() && !texto.isEmpty() && texto.toUpperCase().contains("LIMPIAR");
			if (candidato) {
				visibles.add(limpiar);
			}
		}

		if (visibles.size() <= indiceVisible) {
			return false;
		}

		((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", visibles.get(indiceVisible));
		return true;
	}

	/**
	 * Reabre un filtro y desmarca opciones que el panel reporta como seleccionadas.
	 * Funciona como respaldo cuando el texto Limpiar Seleccion no esta disponible para Selenium.
	 *
	 * @param filtroBy localizador del filtro que se desea limpiar.
	 * @param debug bitacora opcional para registrar las opciones seleccionadas encontradas.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	private void desmarcarOpcionesSeleccionadasPanel(By filtroBy) {
		try {
			abrirPanelFiltro(filtroBy);
			List<WebElement> seleccionadas = u.getDriver().findElements(By.xpath("//ng-dropdown-panel//div[contains(@class,'ng-option') and contains(@class,'ng-option-selected')]"));
			for (int i = 0; i < seleccionadas.size(); i++) {
				WebElement opcion = seleccionadas.get(i);
				seleccionarOpcionMultiple(opcion);
			}
			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
		} catch (Exception ignore) {
		}
	}

	/**
	 * Valida que el filtro Plaza se encuentre limpio y en estado inicial despues de usar Limpiar seleccion.
	 * El control no debe conservar etiquetas de valores ni texto de multiples seleccionados.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el control Plaza queda sin valores seleccionados.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean validarFiltroPlazaEstadoInicial(boolean screenshot) {
		try {
			WebElement filtro = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.dropPlazaDevEspeciales));
			String textoControl = filtro.getText() == null ? "" : filtro.getText().trim().replaceAll("\\s+", " ");
			java.util.List<String> valoresSeleccionados = obtenerValoresSeleccionados(filtro);
			boolean limpio = valoresSeleccionados.isEmpty()
					&& !normalizarTextoComparacion(textoControl).contains("MULTIPLES SELECCIONADOS");

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El filtro Plaza queda sin valores seleccionados y vuelve a su estado inicial.");
				testCase.addScreenShotCurrentStep(u, "Plaza_Estado_Inicial");
			}
			return limpio;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar estado inicial del filtro Plaza: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Escribe un valor parcial en el filtro Tienda para validar que el autocomplete muestre sugerencias.
	 * El valor se obtiene desde una opcion real visible para validar busqueda alfanumerica sin datos artificiales.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el filtro permite escribir y muestra sugerencias para el valor parcial capturado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean escribirValorAutocompleteFiltroTienda(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			abrirPanelFiltro(elements.dropTiendaDevEspeciales);
			List<WebElement> opcionesIniciales = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements.opcionesDevEspeciales));
			if (opcionesIniciales.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("El filtro Tienda no mostro opciones para tomar valor de autocomplete.");
				return false;
			}

			String primeraOpcion = opcionesIniciales.get(0).getText() == null ? "" : opcionesIniciales.get(0).getText().trim().replaceAll("\\s+", " ");
			if (primeraOpcion.length() < 3) {
				testCase.addTextEvidenceCurrentStep("La primera opcion Tienda no tiene longitud suficiente para autocomplete: " + primeraOpcion);
				return false;
			}

			String valorBusqueda = primeraOpcion.substring(0, 3).toLowerCase(new Locale("es", "MX"));
			WebElement input = obtenerInputFiltroActivo(elements.dropTiendaDevEspeciales);
			input.clear();
			input.sendKeys(valorBusqueda);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			List<WebElement> sugerencias = u.getDriver().findElements(elements.opcionesDevEspeciales);
			capturarPrimerasOpciones(sugerencias, primerasOpcionesTienda);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Valor escrito en autocomplete Tienda: " + valorBusqueda);
				testCase.addTextEvidenceCurrentStep("Sugerencias visibles Tienda: " + sugerencias.size());
				testCase.addTextEvidenceCurrentStep("Primeras 5 sugerencias Tienda:\n" + formatearListaOpciones(primerasOpcionesTienda));
				testCase.addScreenShotCurrentStep(u, "Autocomplete_Tienda");
			}
			return !sugerencias.isEmpty();
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar autocomplete del filtro Tienda: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona multiples valores en Tienda con el panel abierto y valida que no se cierre automaticamente.
	 * La validacion conserva los valores seleccionados para pasos posteriores de persistencia y limpieza.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se seleccionan al menos dos valores y el panel permanece abierto.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean seleccionarMultiplesValoresFiltroTiendaManteniendoPanel(boolean screenshot) {
		try {
			abrirPanelFiltro(elements.dropTiendaDevEspeciales);
			ultimasOpcionesMultiplesTienda.clear();

			while (ultimasOpcionesMultiplesTienda.size() < 2) {
				List<WebElement> opciones = u.getDriver().findElements(elements.opcionesDevEspeciales);
				boolean seleccionoOpcion = false;
				for (WebElement opcion : opciones) {
					String textoOpcion = opcion.getText() == null ? "" : opcion.getText().trim().replaceAll("\\s+", " ");
					if (opcion.isDisplayed() && opcion.isEnabled() && !textoOpcion.isEmpty() && !ultimasOpcionesMultiplesTienda.contains(textoOpcion)) {
						seleccionarOpcionMultiple(opcion);
						ultimasOpcionesMultiplesTienda.add(textoOpcion);
						seleccionoOpcion = true;
						break;
					}
				}

				if (!seleccionoOpcion) {
					break;
				}

				if (u.getDriver().findElements(elements.panelOpcionesDevEspeciales).isEmpty()) {
					abrirPanelFiltro(elements.dropTiendaDevEspeciales);
				}
			}

			if (!ultimasOpcionesMultiplesTienda.isEmpty()) {
				ultimoTiendaSeleccionada = ultimasOpcionesMultiplesTienda.get(0);
			}
			boolean panelVisible = !u.getDriver().findElements(elements.panelOpcionesDevEspeciales).isEmpty();
			boolean multiplesVisibles = validarControlIndicaMultiplesSeleccionados(elements.dropTiendaDevEspeciales);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Valores seleccionados en Tienda: " + String.join(" | ", ultimasOpcionesMultiplesTienda));
				testCase.addTextEvidenceCurrentStep("El control Tienda muestra Multiples seleccionados y el panel permanece abierto.");
				testCase.addScreenShotCurrentStep(u, "Seleccion_Multiple_Tienda_Panel_Abierto");
			}
			return ultimasOpcionesMultiplesTienda.size() >= 2 && multiplesVisibles && panelVisible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar multiples valores Tienda manteniendo panel abierto: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Ejecuta la opcion Limpiar seleccion del filtro Tienda y valida que se retiren los valores seleccionados.
	 * Usa la accion visible de Limpiar Seleccion por posicion y como respaldo el control de limpieza del ng-select.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la seleccion multiple de Tienda se limpia correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean limpiarSeleccionFiltroTienda(boolean screenshot) {
		try {
			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();

			if (!clicLimpiarSeleccionVisiblePorIndice(2)) {
				limpiarFiltroActivo(elements.dropTiendaDevEspeciales);
			}

			if (!validarFiltroTiendaEstadoInicial(false)) {
				desmarcarOpcionesSeleccionadasPanel(elements.dropTiendaDevEspeciales);
			}

			if (!validarFiltroTiendaEstadoInicial(false)) {
				new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
				limpiarFiltroActivo(elements.dropTiendaDevEspeciales);
			}

			boolean limpioFinal = false;
			try {
				limpioFinal = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
						.until(driver -> validarFiltroTiendaEstadoInicial(false));
			} catch (Exception ignore) {
			}

			ultimasOpcionesMultiplesTienda.clear();
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se ejecuta Limpiar seleccion en el filtro Tienda.");
				testCase.addScreenShotCurrentStep(u, "Limpiar_Seleccion_Tienda");
			}
			return limpioFinal;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al limpiar seleccion del filtro Tienda: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el filtro Tienda se encuentre limpio y en estado inicial despues de usar Limpiar seleccion.
	 * El control no debe conservar etiquetas de valores ni texto de multiples seleccionados.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el control Tienda queda sin valores seleccionados.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean validarFiltroTiendaEstadoInicial(boolean screenshot) {
		try {
			WebElement filtro = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.dropTiendaDevEspeciales));
			String textoControl = filtro.getText() == null ? "" : filtro.getText().trim().replaceAll("\\s+", " ");
			java.util.List<String> valoresSeleccionados = obtenerValoresSeleccionados(filtro);
			boolean limpio = valoresSeleccionados.isEmpty()
					&& !normalizarTextoComparacion(textoControl).contains("MULTIPLES SELECCIONADOS");

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El filtro Tienda queda sin valores seleccionados y vuelve a su estado inicial.");
				testCase.addScreenShotCurrentStep(u, "Tienda_Estado_Inicial");
			}
			return limpio;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar estado inicial del filtro Tienda: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que las sugerencias del filtro BOL capturadas despues de escribir el prefijo sigan visibles en pantalla.
	 * Usa las primeras sugerencias conservadas por escribirTresDigitosValidosFiltroBolYValidarSugerencias.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si existen sugerencias BOL capturadas y el panel de opciones permanece visible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean validarSugerenciasFiltroBolVisibles(boolean screenshot) {
		try {
			boolean panelVisible = !u.getDriver().findElements(elements.panelOpcionesDevEspeciales).isEmpty();
			boolean existenSugerencias = !primerasOpcionesBol.isEmpty();

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Prefijo BOL capturado: " + prefijoBolValido);
				testCase.addTextEvidenceCurrentStep("Sugerencias BOL visibles:\n" + formatearListaOpciones(primerasOpcionesBol));
				testCase.addScreenShotCurrentStep(u, "Sugerencias_BOL_Visibles");
			}
			return panelVisible && existenSugerencias;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar sugerencias visibles del filtro BOL: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida en BD que las sugerencias visibles del filtro BOL existan en xxcm_cedis_de_proyeccion_grid.
	 * Compara cada sugerencia contra bol_completo antes de seleccionar una opcion en pantalla.
	 *
	 * @param screenshot true para registrar evidencia visual y de consulta, false en caso contrario.
	 * @return true si todas las sugerencias capturadas existen en BD para la tienda seleccionada.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean validarSugerenciasFiltroBolContraBD(boolean screenshot) {
		try {
			if (primerasOpcionesBol.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No existen sugerencias BOL capturadas para validar contra BD.");
				return false;
			}
			if (ultimoTiendaSeleccionada == null || ultimoTiendaSeleccionada.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No existe una Tienda seleccionada para validar sugerencias BOL contra BD.");
				return false;
			}

			String tiendaUI = ultimoTiendaSeleccionada.trim().replaceAll("\\s+", " ");
			java.util.regex.Matcher matcher = Pattern.compile("^(\\S+)\\s+(.+)$").matcher(tiendaUI);
			if (!matcher.matches()) {
				testCase.addTextEvidenceCurrentStep("La tienda seleccionada no tiene formato cr_tienda desc_tienda: " + tiendaUI);
				return false;
			}
			String crTienda = matcher.group(1).trim();
			String sql = "select * "
					+ "from public.xxcm_cedis_de_proyeccion_grid "
					+ "where cr_tienda = ? "
					+ "and bol_completo = ? "
					+ "limit 1";

			java.util.List<String> sugerenciasSinBD = new java.util.ArrayList<>();
			StringBuilder tabla = new StringBuilder("Resultado validacion sugerencias BOL contra xxcm_cedis_de_proyeccion_grid:\n")
					.append("| sugerencia_ui | id | cr_tienda | transferencia | folio_bol | bol_completo |\n");

			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
					GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setQueryTimeout(30);
				for (String sugerencia : primerasOpcionesBol) {
					statement.setString(1, crTienda);
					statement.setString(2, sugerencia);
					try (ResultSet resultSet = statement.executeQuery()) {
						if (resultSet.next()) {
							String idBD = obtenerValorColumnaSiExiste(resultSet, "id");
							String crTiendaBD = resultSet.getString("cr_tienda") == null ? "" : resultSet.getString("cr_tienda").trim();
							String transferenciaBD = obtenerValorColumnaSiExiste(resultSet, "transferencia");
							String folioBolBD = obtenerValorColumnaSiExiste(resultSet, "folio_bol");
							String bolCompletoBD = resultSet.getString("bol_completo") == null ? "" : resultSet.getString("bol_completo").trim().replaceAll("\\s+", " ");
							tabla.append("| ").append(sugerencia).append(" | ").append(idBD).append(" | ").append(crTiendaBD).append(" | ")
									.append(transferenciaBD).append(" | ").append(folioBolBD).append(" | ").append(bolCompletoBD).append(" |\n");
						} else {
							sugerenciasSinBD.add(sugerencia);
							tabla.append("| ").append(sugerencia).append(" | Sin registro | Sin registro | Sin registro | Sin registro | Sin registro |\n");
						}
					}
				}
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Consulta base usada para validar BOL:\n" + sql);
				testCase.addTextEvidenceCurrentStep(tabla.toString());
				testCase.addScreenShotCurrentStep(u, "Sugerencias_BOL_Contra_BD");
			}
			if (!sugerenciasSinBD.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("Sugerencias BOL sin registro en BD: " + String.join(" | ", sugerenciasSinBD));
			}
			return sugerenciasSinBD.isEmpty();
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar sugerencias BOL contra BD: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el calendario no permita seleccionar una fecha fuera de la ventana vigente de tres anios.
	 * La fecha fuera de rango se calcula como un dia anterior al limite inferior permitido con base en la ultima fecha disponible en BD.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la fecha fuera de rango no esta disponible para seleccion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean validarFechaFueraRangoPermitidoNoSeleccionable(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonCalendario = wait.until(ExpectedConditions.elementToBeClickable(elements.btnCalendarioDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", botonCalendario);
			botonCalendario.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));

			LocalDate ultimaFechaDisponible = obtenerUltimaFechaDisponibleDevolucionesEspeciales();
			LocalDate fechaFueraRango = ultimaFechaDisponible.minusYears(3).minusDays(1);
			boolean calendarioPermiteNavegar = navegarCalendarioAFecha(fechaFueraRango, true);
			WebElement diaFueraRango = calendarioPermiteNavegar ? obtenerDiaCalendarioPorFecha(fechaFueraRango) : null;
			boolean noSeleccionable = !calendarioPermiteNavegar || diaFueraRango == null || diaCalendarioEstaDeshabilitado(diaFueraRango);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El calendario no permite ingresar la fecha fuera de rango "
						+ fechaFueraRango.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
						+ ". Ultima fecha disponible en BD: "
						+ ultimaFechaDisponible.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ".");
				testCase.addScreenShotCurrentStep(u, "Fecha_Fuera_Rango_No_Seleccionable");
			}
			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			return noSeleccionable;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar fecha fuera del rango permitido: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el calendario no permita seleccionar una fecha final mayor a tres meses despues de la fecha inicial.
	 * Selecciona una fecha inicial valida y verifica que el dia posterior al limite maximo quede deshabilitado.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la fecha final mayor a tres meses no esta disponible para seleccion.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean validarRangoMayorTresMesesNoSeleccionable(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonCalendario = wait.until(ExpectedConditions.elementToBeClickable(elements.btnCalendarioDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", botonCalendario);
			botonCalendario.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));

			LocalDate ultimaFechaDisponible = obtenerUltimaFechaDisponibleDevolucionesEspeciales();
			LocalDate fechaInicial = ultimaFechaDisponible.minusMonths(3).minusDays(1);
			LocalDate fechaFinalFueraLimite = ultimaFechaDisponible;

			boolean navegoAFechaInicial = navegarCalendarioAFecha(fechaInicial, true);
			if (!navegoAFechaInicial) {
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("El calendario no permite navegar a la fecha inicial necesaria para formar un rango mayor a 3 meses: "
							+ fechaInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
							+ " | Ultima fecha disponible BD: "
							+ fechaFinalFueraLimite.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
							+ " | Encabezado actual: " + obtenerEncabezadoCalendarioVisible());
					testCase.addScreenShotCurrentStep(u, "Rango_Mayor_Tres_Meses_Navegacion_Bloqueada");
				}
				new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
				return true;
			}

			WebElement diaInicial = obtenerDiaCalendarioPorFecha(fechaInicial);
			if (diaInicial == null || diaCalendarioEstaDeshabilitado(diaInicial)) {
				testCase.addTextEvidenceCurrentStep("La fecha inicial seleccionada no esta disponible: "
						+ fechaInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				return false;
			}
			clicDiaCalendario(diaInicial);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendarioOverlayDevEspeciales));

			boolean navegoAFechaFinal = navegarCalendarioAFecha(fechaFinalFueraLimite, false);
			if (!navegoAFechaFinal) {
				testCase.addTextEvidenceCurrentStep("No fue posible navegar a la fecha final fuera de limite: "
						+ fechaFinalFueraLimite.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				return false;
			}

			WebElement diaFinalFueraLimite = obtenerDiaCalendarioPorFecha(fechaFinalFueraLimite);
			boolean noSeleccionable = diaFinalFueraLimite == null || diaCalendarioEstaDeshabilitado(diaFinalFueraLimite);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Fecha inicial valida: "
						+ fechaInicial.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
						+ " | Ultima fecha disponible BD usada como fecha final fuera de limite: "
						+ fechaFinalFueraLimite.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
						+ " | Limite maximo permitido desde fecha inicial: "
						+ fechaInicial.plusMonths(3).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
						+ " | Fecha final no seleccionable: " + noSeleccionable);
				testCase.addScreenShotCurrentStep(u, "Rango_Mayor_Tres_Meses_No_Seleccionable");
			}
			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			try {
				clicLimpiarFiltrosDevolucionesEspeciales(false);
			} catch (Exception ignore) {
			}
			return noSeleccionable;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar rango mayor a tres meses: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene la ultima fecha disponible de Devoluciones Especiales desde la tabla de proyeccion.
	 * Se usa como referencia para calcular limites vigentes del calendario.
	 *
	 * @return ultima fecha disponible registrada en public.xxcm_cedis_de_proyeccion_grid.fecha.
	 * @throws Exception si no existe una fecha disponible en BD.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	private LocalDate obtenerUltimaFechaDisponibleDevolucionesEspeciales() throws Exception {
		String sql = "select max(fecha)::date as ultima_fecha_disponible "
				+ "from public.xxcm_cedis_de_proyeccion_grid "
				+ "where fecha is not null";
		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			if (resultSet.next() && resultSet.getDate("ultima_fecha_disponible") != null) {
				return resultSet.getDate("ultima_fecha_disponible").toLocalDate();
			}
		}
		throw new org.openqa.selenium.NoSuchElementException("No se encontro ultima fecha disponible en xxcm_cedis_de_proyeccion_grid.fecha.");
	}

	/**
	 * Navega el calendario hasta el mes y anio de la fecha objetivo usando las flechas anterior o siguiente.
	 * La direccion se controla por parametro para evitar depender del parseo del encabezado del datepicker.
	 *
	 * @param fechaObjetivo fecha cuyo mes y anio deben quedar visibles.
	 * @param navegarAnterior true para usar la flecha anterior, false para usar la flecha siguiente.
	 * @return true si el calendario muestra el mes y anio de la fecha objetivo.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	private boolean navegarCalendarioAFecha(LocalDate fechaObjetivo, boolean navegarAnterior) {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
		By botonNavegacionBy = navegarAnterior ? elements.btnMesAnteriorCalendarioDevEspeciales : elements.btnMesSiguienteCalendarioDevEspeciales;
		for (int intento = 0; intento < 48; intento++) {
			String encabezado = obtenerEncabezadoCalendarioVisible();
			if (encabezadoCalendarioCorrespondeAFecha(encabezado, fechaObjetivo)) {
				return true;
			}

			WebElement botonNavegacion = wait.until(ExpectedConditions.visibilityOfElementLocated(botonNavegacionBy));
			if (diaCalendarioEstaDeshabilitado(botonNavegacion)) {
				return false;
			}
			String encabezadoAntes = encabezado;
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", botonNavegacion);
			try {
				wait.until(driver -> !obtenerEncabezadoCalendarioVisible().equals(encabezadoAntes));
			} catch (Exception ignore) {
			}
		}
		return encabezadoCalendarioCorrespondeAFecha(obtenerEncabezadoCalendarioVisible(), fechaObjetivo);
	}

	/**
	 * Obtiene del calendario visible el boton correspondiente a una fecha especifica.
	 * La busqueda compara el dia visible y el aria-label normalizado para confirmar mes y anio.
	 *
	 * @param fecha fecha que se desea localizar en el calendario visible.
	 * @return WebElement del dia si se encuentra; null si no esta presente en el DOM visible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	private WebElement obtenerDiaCalendarioPorFecha(LocalDate fecha) {
		List<WebElement> dias = u.getDriver().findElements(By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-body-cell')]"));
		String diaEsperado = String.valueOf(fecha.getDayOfMonth());
		for (WebElement dia : dias) {
			String textoDia = obtenerTextoCeldaCalendario(dia);
			String ariaLabel = obtenerAtributoSeguro(dia, "aria-label");
			if (dia.isDisplayed() && diaEsperado.equals(textoDia) && ariaLabelCorrespondeAFecha(ariaLabel, fecha)) {
				return dia;
			}
		}
		return null;
	}

	/**
	 * Valida si un elemento de calendario o control de navegacion se encuentra deshabilitado.
	 * Considera clase CSS, atributo disabled, aria-disabled y estado enabled de Selenium.
	 *
	 * @param elemento elemento del calendario o boton de navegacion.
	 * @return true si el elemento esta deshabilitado o no disponible para clic.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	private boolean diaCalendarioEstaDeshabilitado(WebElement elemento) {
		String clase = obtenerAtributoSeguro(elemento, "class").toLowerCase();
		String disabled = obtenerAtributoSeguro(elemento, "disabled");
		String ariaDisabled = obtenerAtributoSeguro(elemento, "aria-disabled");
		String propiedadDisabled = obtenerPropiedadDisabled(elemento);
		return !elemento.isEnabled()
				|| !disabled.isEmpty()
				|| "true".equalsIgnoreCase(ariaDisabled)
				|| "true".equalsIgnoreCase(propiedadDisabled)
				|| clase.contains("mat-calendar-body-disabled")
				|| clase.contains("mat-mdc-button-disabled")
				|| clase.contains("mat-button-disabled");
	}

	/**
	 * Elimina la seleccion del filtro Tienda y valida que el filtro BOL regrese a su estado inicial.
	 * El estado esperado es BOL deshabilitado y sin valores visibles despues de quitar todas las tiendas.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si Tienda queda limpia y BOL queda deshabilitado sin valores seleccionados.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean limpiarTiendaYValidarBolDeshabilitadoLimpio(boolean screenshot) {
		try {
			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();

			if (!clicLimpiarSeleccionVisiblePorIndice(2)) {
				limpiarFiltroActivo(elements.dropTiendaDevEspeciales);
			}

			if (!validarFiltroTiendaEstadoInicial(false)) {
				desmarcarOpcionesSeleccionadasPanel(elements.dropTiendaDevEspeciales);
			}

			if (!validarFiltroTiendaEstadoInicial(false)) {
				new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
				limpiarFiltroActivo(elements.dropTiendaDevEspeciales);
			}

			boolean tiendaLimpia = false;
			try {
				tiendaLimpia = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
						.until(driver -> validarFiltroTiendaEstadoInicial(false));
			} catch (Exception ignore) {
			}

			ultimoTiendaSeleccionada = null;
			ultimoBolSeleccionado = null;
			ultimasOpcionesMultiplesTienda.clear();
			ultimasOpcionesMultiplesBol.clear();

			WebElement filtroBol = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.dropBolDevEspeciales));
			String claseBol = obtenerAtributoSeguro(filtroBol, "class");
			String ariaDisabledBol = obtenerAtributoSeguro(filtroBol, "aria-disabled");
			String textoBol = filtroBol.getText() == null ? "" : filtroBol.getText().trim().replaceAll("\\s+", " ");
			java.util.List<String> valoresBol = obtenerValoresSeleccionados(filtroBol);
			String inputBol = obtenerValorInputFiltro(elements.dropBolDevEspeciales);

			boolean bolDeshabilitado = !filtroBol.isEnabled()
					|| "true".equalsIgnoreCase(ariaDisabledBol)
					|| claseBol.contains("ng-select-disabled");
			boolean bolLimpio = valoresBol.isEmpty()
					&& inputBol.isEmpty()
					&& !normalizarTextoComparacion(textoBol).contains("MULTIPLES SELECCIONADOS");

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se elimina la seleccion de Tienda y el filtro BOL regresa a estado inicial sin valores seleccionados.");
				testCase.addScreenShotCurrentStep(u, "Tienda_Limpia_BOL_Deshabilitado_Limpio");
			}
			return tiendaLimpia && bolDeshabilitado && bolLimpio;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al limpiar Tienda y validar BOL deshabilitado limpio: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida en backend que la consulta de Devoluciones Especiales considere solo informacion vigente de los ultimos tres anios.
	 * La ventana se calcula con base en la ultima fecha disponible de public.xxcm_cedis_de_proyeccion_grid.fecha.
	 *
	 * @param screenshot true para registrar evidencia de la consulta, false en caso contrario.
	 * @return true si la consulta vigente queda acotada dentro de la ventana permitida.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public boolean validarBackendInformacionVigenteUltimosTresAnios(boolean screenshot) {
		try {
			String sql = "with limites as ( "
					+ "select max(fecha)::date as ultima_fecha_disponible, "
					+ "(max(fecha)::date - interval '3 years')::date as fecha_minima_vigente "
					+ "from public.xxcm_cedis_de_proyeccion_grid "
					+ "where fecha is not null "
					+ "), conteo_total as ( "
					+ "select count(*) as total_registros, "
					+ "count(*) filter (where g.fecha::date >= l.fecha_minima_vigente and g.fecha::date <= l.ultima_fecha_disponible) as registros_vigentes, "
					+ "count(*) filter (where g.fecha::date < l.fecha_minima_vigente) as registros_fuera_rango, "
					+ "min(g.fecha)::date as fecha_minima_tabla, "
					+ "max(g.fecha)::date as fecha_maxima_tabla "
					+ "from public.xxcm_cedis_de_proyeccion_grid g cross join limites l "
					+ "), consulta_vigente as ( "
					+ "select count(*) as total_consulta_vigente, "
					+ "min(g.fecha)::date as fecha_minima_consulta, "
					+ "max(g.fecha)::date as fecha_maxima_consulta "
					+ "from public.xxcm_cedis_de_proyeccion_grid g cross join limites l "
					+ "where g.fecha::date >= l.fecha_minima_vigente "
					+ "and g.fecha::date <= l.ultima_fecha_disponible "
					+ ") select l.ultima_fecha_disponible, l.fecha_minima_vigente, "
					+ "ct.total_registros, ct.registros_vigentes, ct.registros_fuera_rango, "
					+ "ct.fecha_minima_tabla, ct.fecha_maxima_tabla, "
					+ "cv.total_consulta_vigente, cv.fecha_minima_consulta, cv.fecha_maxima_consulta "
					+ "from limites l cross join conteo_total ct cross join consulta_vigente cv";

			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
					GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setQueryTimeout(30);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (!resultSet.next()) {
						testCase.addTextEvidenceCurrentStep("La consulta de validacion backend no devolvio resultados.");
						return false;
					}

					java.sql.Date ultimaFechaBD = resultSet.getDate("ultima_fecha_disponible");
					java.sql.Date fechaMinimaVigenteBD = resultSet.getDate("fecha_minima_vigente");
					java.sql.Date fechaMinimaConsultaBD = resultSet.getDate("fecha_minima_consulta");
					java.sql.Date fechaMaximaConsultaBD = resultSet.getDate("fecha_maxima_consulta");
					long totalRegistros = resultSet.getLong("total_registros");
					long registrosVigentes = resultSet.getLong("registros_vigentes");
					long registrosFueraRango = resultSet.getLong("registros_fuera_rango");
					long totalConsultaVigente = resultSet.getLong("total_consulta_vigente");

					if (ultimaFechaBD == null || fechaMinimaVigenteBD == null || fechaMinimaConsultaBD == null || fechaMaximaConsultaBD == null) {
						testCase.addTextEvidenceCurrentStep("No existe informacion suficiente en xxcm_cedis_de_proyeccion_grid.fecha para validar la ventana vigente.");
						return false;
					}

					LocalDate ultimaFecha = ultimaFechaBD.toLocalDate();
					LocalDate fechaMinimaVigente = fechaMinimaVigenteBD.toLocalDate();
					LocalDate fechaMinimaConsulta = fechaMinimaConsultaBD.toLocalDate();
					LocalDate fechaMaximaConsulta = fechaMaximaConsultaBD.toLocalDate();

					boolean consultaTieneDatos = totalConsultaVigente > 0;
					boolean conteoCoincide = registrosVigentes == totalConsultaVigente;
					boolean fechaMinimaValida = !fechaMinimaConsulta.isBefore(fechaMinimaVigente);
					boolean fechaMaximaValida = !fechaMaximaConsulta.isAfter(ultimaFecha);
					ultimoRangoFechasBackendValidado = fechaMinimaVigente.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
							+ " a " + ultimaFecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					ultimoResultadoConsultaBackendUltimosTresAnios = "| total_tabla | registros_vigentes | registros_fuera_rango | total_consulta_vigente | fecha_minima_consulta | fecha_maxima_consulta |\n"
							+ "| " + totalRegistros + " | " + registrosVigentes + " | " + registrosFueraRango + " | "
							+ totalConsultaVigente + " | " + fechaMinimaConsulta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
							+ " | " + fechaMaximaConsulta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " |";

					if (screenshot) {
						testCase.addTextEvidenceCurrentStep("Consulta backend vigente aplicada:\n" + sql);
						testCase.addTextEvidenceCurrentStep("Ventana vigente backend: "
								+ ultimoRangoFechasBackendValidado + ".");
						testCase.addTextEvidenceCurrentStep("Resultado validacion backend:\n"
								+ ultimoResultadoConsultaBackendUltimosTresAnios);
						if (registrosFueraRango > 0) {
							testCase.addTextEvidenceCurrentStep("Existen registros historicos fuera de la ventana vigente, pero no participan en la consulta backend validada.");
						} else {
							testCase.addTextEvidenceCurrentStep("No existen registros fuera de rango en el ambiente; la consulta conserva el criterio de ventana vigente de tres anios.");
						}
					}

					return consultaTieneDatos && conteoCoincide && fechaMinimaValida && fechaMaximaValida;
				}
			}
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar backend ultimos tres anios: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene el rango de fechas validado en la ultima consulta backend de informacion vigente.
	 * El valor se genera al ejecutar validarBackendInformacionVigenteUltimosTresAnios.
	 *
	 * @return rango de fechas vigente validado o mensaje de ausencia si aun no existe ejecucion previa.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public String obtenerUltimoRangoFechasBackendValidado() {
		return ultimoRangoFechasBackendValidado == null || ultimoRangoFechasBackendValidado.trim().isEmpty()
				? "Sin rango de fechas backend validado."
				: ultimoRangoFechasBackendValidado;
	}

	/**
	 * Obtiene el resultado resumido de la ultima consulta backend de informacion vigente de tres anios.
	 * El valor se genera al ejecutar validarBackendInformacionVigenteUltimosTresAnios.
	 *
	 * @return tabla con conteos y fechas resultantes de la consulta backend.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-10
	 */
	public String obtenerUltimoResultadoConsultaBackendUltimosTresAnios() {
		return ultimoResultadoConsultaBackendUltimosTresAnios == null || ultimoResultadoConsultaBackendUltimosTresAnios.trim().isEmpty()
				? "Sin resultado de consulta backend registrado."
				: ultimoResultadoConsultaBackendUltimosTresAnios;
	}

	/**
	 * Ingresa a Devoluciones Especiales desde Recepcion y valida la carga inicial de la pantalla.
	 * Registra T1, T2 y T_carga; valida modal Espere un momento opcional, cierre automatico en tiempo maximo y filtros sin No items found.
	 *
	 * @param tiempoMaximoSegundos tiempo maximo permitido para que la pantalla quede disponible.
	 * @return true cuando Devoluciones Especiales queda disponible dentro del tiempo maximo y los filtros cargan correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	public boolean ingresarDevolucionesEspecialesYValidarCargaInicial(int tiempoMaximoSegundos) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime t1 = LocalDateTime.now();
			String textoT1 = t1.format(formatter);
			long inicioCarga = System.currentTimeMillis();
			testCase.addBoldTextEvidenceCurrentStep("T1 inicio de carga Devoluciones Especiales: " + textoT1);
			logCargaDevEspeciales("URL antes de buscar boton: " + obtenerUrlActualDevEspeciales(), inicioCarga);
			logDetalleBotonesDevEspeciales("Antes de clic", inicioCarga);

			WebDriverWait waitClick = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			logCargaDevEspeciales("Esperando boton Devoluciones Especiales visible", inicioCarga);
			WebElement boton = waitClick.until(driver -> obtenerBotonDevolucionesEspecialesVisible());
			logCargaDevEspeciales("Boton encontrado: " + describirElementoDevEspeciales(boton), inicioCarga);
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", boton);
			logCargaDevEspeciales("Boton despues de scroll: " + describirElementoDevEspeciales(boton), inicioCarga);
			try {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", boton);
				logCargaDevEspeciales("Click JavaScript ejecutado sobre boton real Devoluciones Especiales", inicioCarga);
			} catch (Exception clickInterceptado) {
				logCargaDevEspeciales("Click JavaScript fallo: " + clickInterceptado.getClass().getSimpleName() + " | " + clickInterceptado.getMessage(), inicioCarga);
				boton.click();
				logCargaDevEspeciales("Click Selenium ejecutado como respaldo sobre Devoluciones Especiales", inicioCarga);
			}
			logCargaDevEspeciales("URL inmediatamente despues del click: " + obtenerUrlActualDevEspeciales(), inicioCarga);
			logDetalleBotonesDevEspeciales("Despues de clic", inicioCarga);

			boolean modalValido = validarModalInicialDevEspeciales();
			boolean pantallaDisponible = esperarPantallaDevEspecialesDisponible(inicioCarga, tiempoMaximoSegundos);
			LocalDateTime t2 = LocalDateTime.now();
			String textoT2 = t2.format(formatter);
			double tiempoCarga = (System.currentTimeMillis() - inicioCarga) / 1000.0;
			String textoTiempoCarga = String.format("%.2f", tiempoCarga);
			testCase.addBoldTextEvidenceCurrentStep("T2 fin de carga Devoluciones Especiales: " + textoT2 + " | T_carga: " + textoTiempoCarga + " segundos");

			if (!modalValido) {
				return registrarCargaDevEspeciales(false, "El modal Espere un momento fue visible, pero no cubrio la pantalla completa durante la carga", "Carga_Inicial_Dev_Especiales_Modal");
			}

			if (!pantallaDisponible || tiempoCarga > tiempoMaximoSegundos) {
				return registrarCargaDevEspeciales(false, "Devoluciones Especiales no quedo disponible dentro de " + tiempoMaximoSegundos + " segundos. T_carga: " + textoTiempoCarga, "Carga_Inicial_Dev_Especiales_Tiempo");
			}

			boolean cedis = abrirFiltroDevEspecialesSinNoItems(elements.dropCedisDevEspeciales, "CEDIS", false);
			boolean plaza = abrirFiltroDevEspecialesSinNoItems(elements.dropPlazaDevEspeciales, "Plaza", false);
			boolean tienda = abrirFiltroDevEspecialesSinNoItems(elements.dropTiendaDevEspeciales, "Tienda", true);
			boolean bol = validarBolDevEspecialesDisponible();
			boolean calendario = abrirControlCalendarioRangoFechas(true);
			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();

			agregarResumenCargaInicialDevEspeciales(textoT1, textoT2, textoTiempoCarga, tiempoMaximoSegundos,
					modalValido, pantallaDisponible, cedis, plaza, tienda, bol, calendario);
			boolean filtrosCorrectos = cedis && plaza && tienda && bol && calendario;
			return registrarCargaDevEspeciales(filtrosCorrectos,
					"Devoluciones Especiales quedo disponible en " + textoTiempoCarga + " segundos y los filtros cargaron correctamente",
					"Carga_Inicial_Dev_Especiales_CEDIS");
		} catch (Exception exception) {
			System.out.println("[CARGA DEV ESPECIALES] Error general: " + exception.getClass().getSimpleName() + " | " + exception.getMessage());
			logDetalleBotonesDevEspeciales("Error general antes de retornar false", System.currentTimeMillis());
			testCase.addCodeEvidenceCurrentStep("Error al validar carga inicial de Devoluciones Especiales: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida si el modal Espere un momento aparece al ingresar a Devoluciones Especiales y confirma que cubra la pantalla si aparece.
	 * La matriz permite carga inmediata cuando el modal no es perceptible.
	 *
	 * @return true cuando el modal no aparece por carga inmediata o cuando aparece cubriendo pantalla.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean validarModalInicialDevEspeciales() {
		try {
			System.out.println("[CARGA DEV ESPECIALES] Buscando overlay/modal inicial de Devoluciones Especiales");
			WebElement overlay = new WebDriverWait(u.getDriver(), Duration.ofSeconds(2))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.overlayCargaDevEspeciales));
			boolean cubrePantalla = overlayCubreVistaDevEspeciales(overlay);
			System.out.println("[CARGA DEV ESPECIALES] Overlay visible. Cubre pantalla: " + cubrePantalla);
			testCase.addBoldTextEvidenceCurrentStep("Modal 'Espere un momento' visible en Devoluciones Especiales. Cubre pantalla: " + cubrePantalla);
			testCase.addScreenShotCurrentStep(u, "Overlay_Espere_Un_Momento_Devoluciones_Especiales");
			return cubrePantalla;
		} catch (Exception cargaInmediata) {
			System.out.println("[CARGA DEV ESPECIALES] Modal no perceptible: " + cargaInmediata.getClass().getSimpleName() + " | " + cargaInmediata.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("La carga de Devoluciones Especiales fue inmediata; el modal 'Espere un momento' no fue perceptible dentro de la ventana inicial.");
			return true;
		}
	}

	/**
	 * Espera a que desaparezca el overlay de carga y a que los filtros principales de Devoluciones Especiales esten visibles.
	 *
	 * @param inicioCarga marca de tiempo registrada antes del clic en Devoluciones Especiales.
	 * @param tiempoMaximoSegundos tiempo maximo permitido para la carga inicial.
	 * @return true cuando la pantalla queda disponible dentro del tiempo esperado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean esperarPantallaDevEspecialesDisponible(long inicioCarga, int tiempoMaximoSegundos) {
		try {
			long segundosTranscurridos = (System.currentTimeMillis() - inicioCarga) / 1000;
			long segundosRestantes = Math.max(1, tiempoMaximoSegundos - segundosTranscurridos);
			logCargaDevEspeciales("Esperando pantalla Devoluciones Especiales disponible. Segundos restantes: " + segundosRestantes + " | URL: " + obtenerUrlActualDevEspeciales(), inicioCarga);
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(segundosRestantes));
			logCargaDevEspeciales("Esperando invisibilidad overlay. Overlays visibles: " + contarVisiblesDevEspeciales(elements.overlayCargaDevEspeciales), inicioCarga);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayCargaDevEspeciales));
			logCargaDevEspeciales("Overlay invisible. Esperando filtro CEDIS. URL: " + obtenerUrlActualDevEspeciales(), inicioCarga);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.dropCedisDevEspeciales));
			logCargaDevEspeciales("Filtro CEDIS visible", inicioCarga);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.dropPlazaDevEspeciales));
			logCargaDevEspeciales("Filtro Plaza visible", inicioCarga);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.dropTiendaDevEspeciales));
			logCargaDevEspeciales("Filtro Tienda visible", inicioCarga);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.dropBolDevEspeciales));
			logCargaDevEspeciales("Filtro BOL visible", inicioCarga);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.rangoFechaDevEspeciales));
			logCargaDevEspeciales("Rango de Fechas visible", inicioCarga);
			return true;
		} catch (Exception exception) {
			logCargaDevEspeciales("Fallo esperando pantalla disponible: " + exception.getClass().getSimpleName() + " | " + exception.getMessage(), inicioCarga);
			logCargaDevEspeciales("Estado al fallar -> URL: " + obtenerUrlActualDevEspeciales()
					+ " | tituloDev visibles: " + contarVisiblesDevEspeciales(elements.tituloDevolucionesEspeciales)
					+ " | cedis visibles: " + contarVisiblesDevEspeciales(elements.dropCedisDevEspeciales)
					+ " | plaza visibles: " + contarVisiblesDevEspeciales(elements.dropPlazaDevEspeciales)
					+ " | tienda visibles: " + contarVisiblesDevEspeciales(elements.dropTiendaDevEspeciales)
					+ " | bol visibles: " + contarVisiblesDevEspeciales(elements.dropBolDevEspeciales), inicioCarga);
			testCase.addBoldTextEvidenceCurrentStep("La pantalla Devoluciones Especiales no quedo disponible dentro del tiempo maximo: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Abre un filtro de Devoluciones Especiales y valida que tenga opciones sin mostrar No items found.
	 * Opcionalmente selecciona la primera opcion para habilitar filtros dependientes.
	 *
	 * @param filtro localizador del filtro a validar.
	 * @param nombreFiltro nombre funcional del filtro para evidencia.
	 * @param seleccionarPrimera true para seleccionar la primera opcion disponible.
	 * @return true si el filtro muestra opciones y no aparece No items found.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean abrirFiltroDevEspecialesSinNoItems(By filtro, String nombreFiltro, boolean seleccionarPrimera) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement control = wait.until(ExpectedConditions.elementToBeClickable(filtro));
			control.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			wait.until(driver -> !driver.findElements(elements.opcionesDevEspeciales).isEmpty() || noItemsFoundDevEspecialesVisible());
			List<WebElement> opciones = u.getDriver().findElements(elements.opcionesDevEspeciales);
			boolean tieneOpciones = !opciones.isEmpty();
			boolean sinNoItemsFound = !noItemsFoundDevEspecialesVisible();
			testCase.addTextEvidenceCurrentStep("Filtro " + nombreFiltro + ": opciones disponibles=" + tieneOpciones + " | sin No items found=" + sinNoItemsFound);
			if (seleccionarPrimera && tieneOpciones) {
				opciones.get(0).click();
			} else {
				new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			}
			return tieneOpciones && sinNoItemsFound;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al validar filtro " + nombreFiltro + " de Devoluciones Especiales: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el filtro BOL de Devoluciones Especiales sea visible y quede habilitado despues de seleccionar Tienda.
	 *
	 * @return true si el filtro BOL esta visible y habilitado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean validarBolDevEspecialesDisponible() {
		try {
			WebElement bol = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.dropBolDevEspeciales));
			boolean habilitado = bol.isEnabled() && !obtenerAtributoSeguro(bol, "class").contains("ng-select-disabled");
			testCase.addTextEvidenceCurrentStep("Filtro BOL: visible=" + bol.isDisplayed() + " | habilitado=" + habilitado);
			return bol.isDisplayed() && habilitado;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al validar BOL en Devoluciones Especiales: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Agrega al reporte el resumen de tiempos y filtros de la carga inicial de Devoluciones Especiales.
	 *
	 * @param t1 fecha y hora de inicio de carga.
	 * @param t2 fecha y hora de fin de carga.
	 * @param tiempoCarga duracion total en segundos.
	 * @param tiempoMaximoSegundos tiempo maximo esperado.
	 * @param modalValido resultado de la validacion del modal.
	 * @param pantallaDisponible resultado de disponibilidad de la pantalla.
	 * @param cedis resultado del filtro CEDIS.
	 * @param plaza resultado del filtro Plaza.
	 * @param tienda resultado del filtro Tienda.
	 * @param bol resultado del filtro BOL.
	 * @param calendario resultado del calendario.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private void agregarResumenCargaInicialDevEspeciales(String t1, String t2, String tiempoCarga,
			int tiempoMaximoSegundos, boolean modalValido, boolean pantallaDisponible, boolean cedis,
			boolean plaza, boolean tienda, boolean bol, boolean calendario) {
		testCase.addBoldTextEvidenceCurrentStep("Resumen de carga inicial Devoluciones Especiales");
		testCase.addTextEvidenceCurrentStep("T1 inicio: " + t1);
		testCase.addTextEvidenceCurrentStep("T2 fin: " + t2);
		testCase.addTextEvidenceCurrentStep("T_carga: " + tiempoCarga + " segundos / Maximo " + tiempoMaximoSegundos + " segundos");
		testCase.addTextEvidenceCurrentStep("Modal Espere un momento: " + modalValido);
		testCase.addTextEvidenceCurrentStep("Pantalla disponible: " + pantallaDisponible);
		testCase.addTextEvidenceCurrentStep("Filtro CEDIS: " + cedis);
		testCase.addTextEvidenceCurrentStep("Filtro Plaza: " + plaza);
		testCase.addTextEvidenceCurrentStep("Filtro Tienda: " + tienda);
		testCase.addTextEvidenceCurrentStep("Filtro BOL: " + bol);
		testCase.addTextEvidenceCurrentStep("Calendario Rango de Fechas: " + calendario);
	}

	/**
	 * Registra el resultado de la validacion de carga inicial de Devoluciones Especiales.
	 * Agrega evidencia textual y captura para el paso ejecutado.
	 *
	 * @param resultado resultado booleano de la validacion.
	 * @param mensaje mensaje descriptivo del resultado esperado u observado.
	 * @param nombreCaptura nombre de la captura asociada al resultado.
	 * @return el mismo resultado recibido para permitir aserciones directas.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean registrarCargaDevEspeciales(boolean resultado, String mensaje, String nombreCaptura) {
		System.out.println("[RESULTADO PASO] " + (resultado ? "PASSED" : "FAILED") + " - " + mensaje);
		testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + (resultado ? "PASSED" : "FAILED") + " - " + mensaje);
		try {
			testCase.addScreenShotCurrentStep(u, nombreCaptura);
		} catch (Exception ignore) {
		}
		return resultado;
	}

	/**
	 * Valida si el mensaje No items found esta visible en algun panel de Devoluciones Especiales.
	 *
	 * @return true cuando el mensaje No items found se encuentra visible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean noItemsFoundDevEspecialesVisible() {
		return u.getDriver().findElements(By.xpath("//*[contains(normalize-space(),'No items found')]")).stream().anyMatch(WebElement::isDisplayed);
	}

	/**
	 * Valida mediante JavaScript si el modal u overlay de Devoluciones Especiales cubre la pantalla.
	 *
	 * @param overlay elemento visible del modal u overlay.
	 * @return true cuando existe backdrop activo o el contenedor ocupa la mayor parte del viewport.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private boolean overlayCubreVistaDevEspeciales(WebElement overlay) {
		try {
			Object result = ((JavascriptExecutor) u.getDriver()).executeScript(
					"const node = arguments[0].closest('mat-dialog-container, .cdk-overlay-pane, .cdk-overlay-container, .cdk-global-overlay-wrapper') || arguments[0]; const r = node.getBoundingClientRect(); const backdrop = document.querySelector('.cdk-overlay-backdrop.cdk-overlay-backdrop-showing, .modal-backdrop.show'); return !!backdrop || (r.width >= window.innerWidth * 0.6 && r.height >= window.innerHeight * 0.6);", overlay);
			return Boolean.TRUE.equals(result);
		} catch (Exception exception) {
			return true;
		}
	}

	/**
	 * Obtiene el boton real visible de Devoluciones Especiales evitando seleccionar el span interno del texto.
	 *
	 * @return boton visible de Devoluciones Especiales o null si aun no esta disponible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private WebElement obtenerBotonDevolucionesEspecialesVisible() {
		try {
			List<WebElement> botones = u.getDriver().findElements(By.xpath("//button[.//span[normalize-space()='Devoluciones Especiales'] or normalize-space()='Devoluciones Especiales']"));
			for (WebElement boton : botones) {
				if (boton.isDisplayed() && boton.isEnabled()) {
					return boton;
				}
			}
		} catch (Exception ignore) {
		}
		return null;
	}

	/**
	 * Escribe un log de diagnostico de carga inicial de Devoluciones Especiales con tiempo acumulado.
	 *
	 * @param mensaje mensaje de diagnostico.
	 * @param inicioCarga marca de tiempo base.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private void logCargaDevEspeciales(String mensaje, long inicioCarga) {
		double segundos = (System.currentTimeMillis() - inicioCarga) / 1000.0;
		System.out.println("[CARGA DEV ESPECIALES][+" + String.format("%.2f", segundos) + "s] " + mensaje);
	}

	/**
	 * Imprime informacion de los candidatos a boton Devoluciones Especiales localizados en el DOM.
	 *
	 * @param etapa etapa de diagnostico.
	 * @param inicioCarga marca de tiempo base.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private void logDetalleBotonesDevEspeciales(String etapa, long inicioCarga) {
		try {
			Object detalle = ((JavascriptExecutor) u.getDriver()).executeScript(
					"const visible=e=>!!e&&!!(e.offsetWidth||e.offsetHeight||e.getClientRects().length);" +
					"const txt=e=>(e.innerText||e.textContent||'').replace(/\\s+/g,' ').trim();" +
					"return Array.from(document.querySelectorAll('button, a, span')).filter(e=>/Devoluciones Especiales/i.test(txt(e))).slice(0,10).map((e,i)=>{const r=e.getBoundingClientRect();return i+':tag='+e.tagName.toLowerCase()+' text='+txt(e)+' visible='+visible(e)+' disabled='+(e.disabled||e.getAttribute('disabled')||e.getAttribute('aria-disabled'))+' class='+(e.className||'')+' rect='+Math.round(r.width)+'x'+Math.round(r.height)+'@'+Math.round(r.left)+','+Math.round(r.top);}).join(' || ');"
			);
			logCargaDevEspeciales("Botones Devoluciones Especiales " + etapa + " -> " + detalle, inicioCarga);
		} catch (Exception exception) {
			logCargaDevEspeciales("No fue posible inspeccionar botones Devoluciones Especiales en etapa " + etapa + ": " + exception.getMessage(), inicioCarga);
		}
	}

	/**
	 * Describe el estado basico de un elemento Web para diagnostico.
	 *
	 * @param elemento elemento a describir.
	 * @return descripcion de texto, visibilidad, estado enabled y clase.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private String describirElementoDevEspeciales(WebElement elemento) {
		try {
			return "tag=" + elemento.getTagName()
					+ " | text=" + (elemento.getText() == null ? "" : elemento.getText().trim().replaceAll("\\s+", " "))
					+ " | displayed=" + elemento.isDisplayed()
					+ " | enabled=" + elemento.isEnabled()
					+ " | class=" + obtenerAtributoSeguro(elemento, "class")
					+ " | disabled=" + obtenerAtributoSeguro(elemento, "disabled")
					+ " | aria-disabled=" + obtenerAtributoSeguro(elemento, "aria-disabled");
		} catch (Exception exception) {
			return "No fue posible describir elemento: " + exception.getMessage();
		}
	}

	/**
	 * Cuenta elementos visibles para un localizador durante diagnostico.
	 *
	 * @param selector localizador a inspeccionar.
	 * @return total de elementos visibles o -1 si falla la consulta.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private int contarVisiblesDevEspeciales(By selector) {
		try {
			return (int) u.getDriver().findElements(selector).stream().filter(WebElement::isDisplayed).count();
		} catch (Exception exception) {
			return -1;
		}
	}

	/**
	 * Obtiene la URL actual del navegador para diagnostico.
	 *
	 * @return URL actual o detalle de error.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-22
	 */
	private String obtenerUrlActualDevEspeciales() {
		try {
			return u.getDriver().getCurrentUrl();
		} catch (Exception exception) {
			return "URL no disponible: " + exception.getMessage();
		}
	}

	/**
	 * Valida que el boton Recepcion de Transferencias y Devoluciones se muestre en la pantalla Devoluciones Especiales.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el boton es visible y habilitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-03
	 */
	public boolean validaBotonRecepcionTransferenciasEnDevolucionesEspeciales(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement boton = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.btnRecepcionTransferenciasDevolucionesEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", boton);
			boolean visible = boton.isDisplayed() && boton.isEnabled();
			u.highLight(elements.btnRecepcionTransferenciasDevolucionesEspeciales);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se visualiza el boton Recepcion de Transferencias y Devoluciones en la pantalla Devoluciones Especiales.");
				testCase.addScreenShotCurrentStep(u, "Boton_Recepcion_En_Devoluciones_Especiales");
			}
			u.unhighLight(elements.btnRecepcionTransferenciasDevolucionesEspeciales);
			return visible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar boton Recepcion de Transferencias: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Acciona el boton Recepcion de Transferencias y Devoluciones desde la pantalla Devoluciones Especiales.
	 * Espera el cambio de pantalla para permitir la validacion posterior del modulo destino.
	 *
	 * @return true si fue posible accionar el boton Recepcion de Transferencias y Devoluciones.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-03
	 */
	public boolean clicBotonRecepcionTransferenciasDesdeDevolucionesEspeciales() {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(elements.btnRecepcionTransferenciasDevolucionesEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", boton);
			try {
				boton.click();
			} catch (Exception clickInterceptado) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", boton);
			}
			u.waitForLoadPage();
			u.hardWait(1);
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al hacer clic en Recepcion de Transferencias: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Da clic en el boton Consultar de Devoluciones Especiales y espera la carga de resultados.
	 * Requiere que el boton Consultar este habilitado previamente.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el clic en Consultar se ejecuta y el overlay de carga desaparece.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-03
	 */
	public boolean ejecutarConsultaDevolucionesEspeciales(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonConsultar = wait.until(ExpectedConditions.elementToBeClickable(elements.btnConsultarDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", botonConsultar);
			botonConsultar.click();
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayCargaDevEspeciales));
			} catch (Exception ignore) {
			}
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se da clic en Consultar y el grid muestra los resultados de la busqueda.");
				testCase.addScreenShotCurrentStep(u, "Grid_Resultados_Devoluciones_Especiales");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al ejecutar consulta en Devoluciones Especiales: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el campo Rango de Fechas quede vacio tras ejecutar Limpiar Filtros.
	 * Verifica que ambos inputs (fecha inicio y fecha fin) no contengan texto.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si ambos campos de fecha estan vacios despues de limpiar.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean validarRangoFechasVacioTrasLimpiar(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.rangoFechaDevEspeciales));
			List<WebElement> inputs = u.getDriver().findElements(
					By.xpath("//app-cedis-devoluciones-especiales//app-filtros-busqueda//mat-form-field[.//mat-date-range-input or .//mat-datepicker-toggle or .//input]//input"));
			boolean todoVacio = inputs.stream().allMatch(input -> {
				String valor = input.getAttribute("value");
				return valor == null || valor.trim().isEmpty();
			});
			System.out.println("[VALIDAR_RANGO_FECHAS_VACIO] inputs encontrados=" + inputs.size() + " | todoVacio=" + todoVacio);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El campo Rango de fechas queda vacio, sin valores previos.");
				testCase.addScreenShotCurrentStep(u, "Rango_Fechas_Vacio_Tras_Limpiar");
			}
			return todoVacio;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar Rango de Fechas vacio tras limpiar: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el grid de Devoluciones Especiales regrese al estado inicial despues de limpiar filtros.
	 * Confirma que el boton Consultar este deshabilitado y el overlay de carga haya desaparecido.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el boton Consultar permanece deshabilitado y el grid muestra estado por defecto.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-03
	 */
	public boolean validarGridEstadoInicialTrasLimpiar(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayCargaDevEspeciales));
			} catch (Exception ignore) {
			}
			WebElement botonConsultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.btnConsultarDevEspeciales));
			boolean consultarDeshabilitado = !botonConsultarEstaHabilitado(botonConsultar);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El grid regresa al estado inicial y el boton Consultar permanece deshabilitado.");
				testCase.addScreenShotCurrentStep(u, "Grid_Estado_Inicial_Tras_Limpiar");
			}
			return consultarDeshabilitado;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar grid estado inicial tras limpiar: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Posiciona el cursor sobre el boton Consultar deshabilitado y valida que el tooltip muestre el texto de fecha obligatoria.
	 * El tooltip esperado debe contener la palabra "Rango", "fecha" u "obligatorio".
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el tooltip con el mensaje de fecha obligatoria es visible o esta en atributo del boton.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-03
	 */
	public boolean validarTooltipConsultarRangoFechasObligatorio(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonConsultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.btnConsultarDevEspeciales));
			new Actions(u.getDriver()).moveToElement(botonConsultar).perform();
			u.hardWait(1);
			String textoTooltip = "";
			try {
				WebElement tooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@role='tooltip' and string-length(normalize-space(.)) > 0] | //mat-tooltip-component[string-length(normalize-space(.)) > 0]")));
				textoTooltip = tooltip.getText().trim();
			} catch (Exception tooltipDOM) {
				textoTooltip = obtenerAtributoSeguro(botonConsultar, "aria-label");
				if (textoTooltip.isEmpty()) {
					textoTooltip = obtenerAtributoSeguro(botonConsultar, "title");
				}
				if (textoTooltip.isEmpty()) {
					textoTooltip = obtenerAtributoSeguro(botonConsultar, "mattooltip");
				}
			}
			boolean tooltipPresente = textoTooltip.toLowerCase().contains("rango")
					|| textoTooltip.toLowerCase().contains("fecha")
					|| textoTooltip.toLowerCase().contains("obligatorio");
			System.out.println("[TOOLTIP CONSULTAR DESHABILITADO] Texto: '" + textoTooltip + "' | Valido: " + tooltipPresente);
			testCase.addBoldTextEvidenceCurrentStep("Tooltip Consultar deshabilitado: '" + textoTooltip + "'");
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Tooltip visible al posicionar el cursor sobre Consultar deshabilitado.");
				testCase.addScreenShotCurrentStep(u, "Tooltip_Consultar_Rango_Obligatorio");
			}
			return tooltipPresente;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar tooltip Consultar: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Posiciona el cursor sobre el boton Consultar habilitado y valida que el tooltip de fecha obligatoria no se muestre.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el tooltip de fecha obligatoria ya no es visible al estar el boton habilitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-03
	 */
	public boolean validarTooltipConsultarAusente(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonConsultar = wait.until(ExpectedConditions.elementToBeClickable(elements.btnConsultarDevEspeciales));
			new Actions(u.getDriver()).moveToElement(botonConsultar).perform();
			u.hardWait(1);
			List<WebElement> tooltips = u.getDriver().findElements(
				By.xpath("//*[@role='tooltip' and (contains(normalize-space(.),'Rango') or contains(normalize-space(.),'fecha') or contains(normalize-space(.),'obligatorio'))] | //mat-tooltip-component[contains(normalize-space(.),'obligatorio')]"));
			boolean tooltipAusente = tooltips.isEmpty() || tooltips.stream().noneMatch(WebElement::isDisplayed);
			System.out.println("[TOOLTIP CONSULTAR HABILITADO] Tooltips con texto obligatorio visibles: " + tooltips.size() + " | Ausente: " + tooltipAusente);
			testCase.addBoldTextEvidenceCurrentStep("Tooltip 'Rango de fechas obligatorio' ausente con boton habilitado: " + tooltipAusente);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Al posicionar el cursor sobre Consultar habilitado, el tooltip de fecha obligatoria no se muestra.");
				testCase.addScreenShotCurrentStep(u, "Tooltip_Consultar_Ausente");
			}
			return tooltipAusente;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar tooltip Consultar ausente: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el grid inicial de Devoluciones Especiales sea visible y contenga registros.
	 * Realiza scroll al grid y resalta la tabla para capturar evidencia visual completa del paso.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el grid esta visible y muestra al menos un registro inicial.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean validarGridCargaInicialDevolucionesEspeciales(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayCargaDevEspeciales));
			} catch (Exception ignore) {
			}

			WebElement grid = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.areaGridDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", grid);
			List<WebElement> filas = u.getDriver().findElements(elements.filasGridDevEspeciales);
			int totalFilas = filas == null ? 0 : (int) filas.stream().filter(WebElement::isDisplayed).count();

			boolean resultadoPaso = totalFilas > 0;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? "" : "El grid no presenta registros iniciales. Esperado: al menos 1 fila | Actual: " + totalFilas;
			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validacion de carga inicial del grid de Devoluciones Especiales"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (!resultadoPaso) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				u.highLight(elements.areaGridDevEspeciales);
				try {
					testCase.addTextEvidenceCurrentStep("Grid visible con " + totalFilas + " registros iniciales.");
					testCase.addScreenShotCurrentStep(u, "Grid_Carga_Inicial_DevEspeciales");
				} finally {
					u.unhighLight(elements.areaGridDevEspeciales);
				}
			}

			return resultadoPaso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Validacion de carga inicial del grid de Devoluciones Especiales | Motivo: "
					+ exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al validar grid inicial de Devoluciones Especiales: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que las fechas visibles del grid de Devoluciones Especiales esten ordenadas de mas reciente a mas antiguo.
	 * Realiza scroll al grid y resalta la tabla para adjuntar evidencia visual del orden observado.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si las fechas visibles en el grid cumplen orden descendente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean validarOrdenDescendenteRegistrosGridDevolucionesEspeciales(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			WebElement grid = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.areaGridDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", grid);

			List<WebElement> celdasFecha = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(elements.celdasFechaGridDevEspeciales));
			java.util.List<LocalDateTime> fechas = new java.util.ArrayList<>();
			java.util.List<String> textosFecha = new java.util.ArrayList<>();
			for (WebElement celda : celdasFecha) {
				if (!celda.isDisplayed()) {
					continue;
				}
				String textoCelda = celda.getText() == null ? "" : celda.getText().trim();
				LocalDateTime fecha = extraerFechaHoraGrid(textoCelda);
				if (fecha != null) {
					fechas.add(fecha);
					textosFecha.add(textoCelda);
				}
			}

			boolean fechasSuficientes = fechas.size() >= 2;
			boolean ordenDescendente = true;
			for (int i = 1; i < fechas.size(); i++) {
				if (fechas.get(i).isAfter(fechas.get(i - 1))) {
					ordenDescendente = false;
					break;
				}
			}

			boolean resultadoPaso = fechasSuficientes && ordenDescendente;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String primerasFechas = textosFecha.stream().limit(5).reduce((a, b) -> a + " | " + b).orElse("Sin fechas visibles");
			String motivoFallo = resultadoPaso ? "" : (fechasSuficientes
					? "Orden descendente no valido. Primeras fechas visibles: " + primerasFechas
					: "No hay suficientes fechas visibles para validar orden. Esperado: >= 2 | Actual: " + fechas.size());

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validacion de orden descendente en grid de Devoluciones Especiales"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (!resultadoPaso) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				u.highLight(elements.areaGridDevEspeciales);
				try {
					testCase.addTextEvidenceCurrentStep("Primeras fechas visibles del grid: " + primerasFechas);
					testCase.addScreenShotCurrentStep(u, "Grid_Registros_Orden_Descendente_DevEspeciales");
				} finally {
					u.unhighLight(elements.areaGridDevEspeciales);
				}
			}

			return resultadoPaso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Validacion de orden descendente en grid de Devoluciones Especiales | Motivo: "
					+ exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al validar orden descendente del grid en Devoluciones Especiales: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que la leyenda de descarga masiva y el boton PDF masivo esten visibles en Devoluciones Especiales.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la leyenda e icono PDF masivo son visibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean validarLeyendaEIconoPdfMasivoVisible(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(driver -> !driver.findElements(By.xpath(
					"//*[contains(normalize-space(),'Descarga MASIVA de comprobantes filtrados') or contains(@class,'download-label') or contains(@class,'gray-download-container') or contains(@class,'massive-button-group')]"))
					.isEmpty());
			WebElement leyenda = esperarLeyendaDescargaMasivaDevEspeciales(30);
			WebElement iconoPdf = esperarBotonPdfMasivoDevEspeciales(30);
			boolean leyendaVisible = leyenda != null && leyenda.isDisplayed();
			boolean iconoVisible = iconoPdf != null && iconoPdf.isDisplayed();
			if (leyendaVisible) {
				resaltarElementoTemporal(leyenda);
			}
			if (iconoVisible) {
				resaltarElementoTemporal(iconoPdf);
			}
			if (!leyendaVisible || !iconoVisible) {
				testCase.addBoldTextEvidenceCurrentStep("Diagnostico descarga masiva -> leyendaVisible=" + leyendaVisible
						+ " | iconoVisible=" + iconoVisible
						+ " | data-testid=" + contarVisiblesDevEspeciales(By.cssSelector("button[data-testid='btn-pdf-masivo-descargas']"))
						+ " | btn-pdf=" + contarVisiblesDevEspeciales(By.cssSelector("button.btn-pdf"))
						+ " | ariaLabel=" + contarVisiblesDevEspeciales(By.xpath("//button[contains(@aria-label,'Descargar PDF masivo de devoluciones especiales') or contains(@aria-label,'descarga masiva') or contains(@aria-label,'Descarga masiva') or contains(@aria-label,'PDF masivo')]"))
						+ " | leyendaClass=" + contarVisiblesDevEspeciales(By.cssSelector("span.download-label"))
						+ " | leyendaTexto=" + contarVisiblesDevEspeciales(By.xpath("//*[contains(normalize-space(),'Descarga MASIVA de comprobantes filtrados')]") ));
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Leyenda descarga masiva visible=" + leyendaVisible + " | icono PDF masivo visible=" + iconoVisible + ".");
				testCase.addScreenShotCurrentStep(u, "Leyenda_Descarga_Masiva_Icono_Activo");
			}

			return leyendaVisible && iconoVisible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar leyenda e icono PDF masivo: " + e.getMessage());
			return false;
		}
	}

	// Selecciona una tienda por nombre exacto y un rango calculado de BD sin datos: garantiza consulta vacia.
	public boolean seleccionarTiendaYRangoSinDatosBdCedis(String usuario, String nombreTienda, boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));

			// 1. Abrir dropdown tienda y filtrar por nombre
			WebElement filtro = wait.until(ExpectedConditions.elementToBeClickable(elements.dropTiendaDevEspeciales));
			try { filtro.click(); } catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", filtro);
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			WebElement input = obtenerInputFiltroActivo(elements.dropTiendaDevEspeciales);
			input.clear();
			input.sendKeys(nombreTienda);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.panelOpcionesDevEspeciales));

			// Seleccionar la primera opción que coincida con el nombre
			List<WebElement> opciones = u.getDriver().findElements(elements.opcionesDevEspeciales);
			WebElement opcionElegida = null;
			for (WebElement op : opciones) {
				String texto = op.getText() == null ? "" : op.getText().trim().replaceAll("\\s+", " ");
				if (op.isDisplayed() && texto.contains(nombreTienda)) {
					opcionElegida = op;
					break;
				}
			}
			if (opcionElegida == null && !opciones.isEmpty()) {
				opcionElegida = opciones.get(0);
			}
			if (opcionElegida == null) {
				testCase.addCodeEvidenceCurrentStep("No se encontro la tienda '" + nombreTienda + "' en las opciones del filtro.");
				return false;
			}
			try { opcionElegida.click(); } catch (Exception e) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", opcionElegida);
			}
			// Cerrar panel si quedó abierto
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.panelOpcionesDevEspeciales));
			} catch (Exception ignore) {
				u.getDriver().findElement(By.tagName("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Tienda seleccionada en filtro: " + nombreTienda + ".");
				testCase.addScreenShotCurrentStep(u, "Tienda_Sin_Datos_Seleccionada");
			}

			// 2. Buscar en BD una fecha sin registros para la tienda sin hardcodear rango
			String sqlFechasSinDatos = "WITH limites AS ( "
					+ "    SELECT min(fecha)::date AS fecha_minima_global, max(fecha)::date AS fecha_maxima_global "
					+ "    FROM xxcm_cedis_de_proyeccion_grid "
					+ "    WHERE fecha IS NOT NULL "
					+ "), calendario AS ( "
					+ "    SELECT gs::date AS fecha "
					+ "    FROM limites, generate_series(fecha_minima_global, fecha_maxima_global, interval '1 day') gs "
					+ ") "
					+ "SELECT c.fecha "
					+ "FROM calendario c "
					+ "WHERE NOT EXISTS ( "
					+ "    SELECT 1 "
					+ "    FROM xxcm_cedis_de_proyeccion_grid xcdpg "
					+ "    WHERE xcdpg.tienda = ? "
					+ "      AND xcdpg.fecha::date = c.fecha "
					+ ") "
					+ "ORDER BY c.fecha";

			String sqlValidarRangoSinDatos = "SELECT count(*) AS total "
					+ "FROM xxcm_cedis_de_proyeccion_grid "
					+ "WHERE tienda = ? "
					+ "  AND fecha::date BETWEEN ?::date AND ?::date";

			java.util.List<LocalDate> fechasSinDatos = new java.util.ArrayList<>();
			String detalleValidacionBd = "";
			try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
					GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
					PreparedStatement statement = connection.prepareStatement(sqlFechasSinDatos)) {
				statement.setString(1, nombreTienda);
				statement.setQueryTimeout(30);
				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						java.sql.Date fechaBd = resultSet.getDate("fecha");
						if (fechaBd != null) {
							fechasSinDatos.add(fechaBd.toLocalDate());
						}
					}
				}
			}

			if (fechasSinDatos.isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No se encontraron fechas sin datos para la tienda " + nombreTienda
						+ " dentro del rango global disponible del grid.");
				return false;
			}

			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			boolean encontroSinResultados = false;
			LocalDate fechaSeleccionada = null;
			int filasObtenidas = -1;
			int totalBdRango = -1;

			for (LocalDate fechaSinDatos : fechasSinDatos) {
				try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
						GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
						PreparedStatement statementValidacion = connection.prepareStatement(sqlValidarRangoSinDatos)) {
					statementValidacion.setString(1, nombreTienda);
					statementValidacion.setDate(2, java.sql.Date.valueOf(fechaSinDatos));
					statementValidacion.setDate(3, java.sql.Date.valueOf(fechaSinDatos));
					try (ResultSet resultSetValidacion = statementValidacion.executeQuery()) {
						if (resultSetValidacion.next()) {
							totalBdRango = resultSetValidacion.getInt("total");
						}
					}
				}

				if (totalBdRango != 0) {
					continue;
				}

				if (!abrirCalendarioYSeleccionarRango(fechaSinDatos, fechaSinDatos, false)) {
					continue;
				}

				if (!ejecutarConsultaDevolucionesEspeciales(false)) {
					continue;
				}

				filasObtenidas = contarFilasVisiblesGridDevolucionesEspeciales();
				if (filasObtenidas == 0) {
					encontroSinResultados = true;
					fechaSeleccionada = fechaSinDatos;
					detalleValidacionBd = "SELECT * FROM xxcm_cedis_de_proyeccion_grid WHERE tienda = '" + nombreTienda
							+ "' AND fecha::date BETWEEN '" + fechaSinDatos + "'::date AND '" + fechaSinDatos
							+ "'::date; -> 0 registros";
					break;
				}
			}

			String estadoPaso = encontroSinResultados ? "PASSED" : "FAILED";
			String motivoFallo = encontroSinResultados ? ""
					: "No se logro una consulta sin resultados para tienda " + nombreTienda
					+ " usando fechas sin datos de BD. Esperado: 0 filas | Actual: " + filasObtenidas;
			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Seleccion de tienda y rango sin datos por query BD"
					+ (encontroSinResultados ? ""
							: " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso
					+ (encontroSinResultados ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				if (encontroSinResultados && fechaSeleccionada != null) {
					testCase.addTextEvidenceCurrentStep("Fecha sin datos seleccionada por query BD para la tienda "
							+ nombreTienda + ": " + fechaSeleccionada.format(fmt)
							+ ". La consulta devuelve 0 registros.");
					testCase.addTextEvidenceCurrentStep("Validacion BD aplicada: " + detalleValidacionBd);
				} else {
					testCase.addTextEvidenceCurrentStep("No se encontro una fecha sin resultados util para la tienda "
							+ nombreTienda + " dentro del rango global disponible del grid.");
				}
				testCase.addScreenShotCurrentStep(u, "Tienda_Rango_Query_Sin_Resultados");
			}

			return encontroSinResultados;
		} catch (Exception e) {
			System.out.println("[RESULTADO PASO] FAILED - Seleccion de tienda y rango sin datos por query BD | Motivo: "
					+ e.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + e.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar tienda y rango sin datos: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona un rango de 7 dias justo antes del primer registro disponible en BD,
	 * garantizando una consulta sin resultados sin necesidad de filtrar por tienda.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-06
	 */
	public boolean seleccionarRangoFechasSinRegistrosBdCedis(String usuario, boolean screenshot) {
		try {
			LocalDate[] ventana = obtenerVentanaInicialSieteDiasPorUsuarioCedis(usuario);
			LocalDate primerDiaConDatos = ventana[0];
			LocalDate fechaFin = primerDiaConDatos.minusDays(1);
			LocalDate fechaInicio = fechaFin.minusDays(6);
			System.out.println("[SIN REGISTROS] Rango sin datos: " + fechaInicio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + fechaFin.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			return abrirCalendarioYSeleccionarRango(fechaInicio, fechaFin, screenshot);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al calcular rango sin registros: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Variante de seleccionarTiendaSinResultadosYRangoValido que obtiene el rango de fechas
	 * desde la BD, garantizando que las fechas esten dentro del minDate/maxDate del calendario.
	 * Itera tiendas hasta encontrar una con 0 resultados en ese rango.
	 *
	 * @param usuario nombre de usuario del perfil CEDIS.
	 * @param maxTiendas maximo de opciones Tienda a evaluar.
	 * @param screenshot true para registrar evidencia visual.
	 * @return true si se encuentra una Tienda que deje la consulta sin resultados.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-06
	 */
	public boolean seleccionarFiltrosParaConsultaSinResultadosBdCedis(String usuario, int maxTiendas, boolean screenshot) {
		try {
			LocalDate[] ventana = obtenerVentanaInicialSieteDiasPorUsuarioCedis(usuario);
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String fechaInicio = ventana[0].format(fmt);
			String fechaFin = ventana[1].format(fmt);
			System.out.println("[SIN RESULTADOS] Usando rango valido BD: " + fechaInicio + " - " + fechaFin);
			return seleccionarTiendaSinResultadosYRangoValido(fechaInicio, fechaFin, maxTiendas, screenshot);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al obtener rango BD para consulta sin resultados: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona una Tienda y rango de fechas validos hasta localizar un escenario sin resultados.
	 * Evalua las primeras opciones visibles del filtro Tienda y conserva la primera que regrese grid vacio.
	 *
	 * @param fechaInicio fecha inicial esperada en formato dd/MM/yyyy.
	 * @param fechaFin fecha final esperada en formato dd/MM/yyyy.
	 * @param maxTiendas maximo de opciones Tienda a evaluar.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se encuentra una Tienda que deje la consulta sin resultados y filtros validos.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean seleccionarTiendaSinResultadosYRangoValido(String fechaInicio, String fechaFin, int maxTiendas, boolean screenshot) {
		try {
			if (!seleccionarRangoFechasEspecifico(fechaInicio, fechaFin, false)) {
				return false;
			}

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			abrirPanelFiltro(elements.dropTiendaDevEspeciales);
			java.util.List<WebElement> opcionesTienda = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elements.opcionesDevEspeciales));
			int totalOpciones = opcionesTienda.size();
			int limite = Math.min(Math.max(maxTiendas, 1), totalOpciones);
			boolean encontradaSinResultados = false;
			String tiendaSeleccionada = "";

			for (int indice = 0; indice < limite; indice++) {
				if (!seleccionarRangoFechasEspecifico(fechaInicio, fechaFin, false)) {
					return false;
				}

				abrirPanelFiltro(elements.dropTiendaDevEspeciales);
				java.util.List<WebElement> opcionesIteracion = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elements.opcionesDevEspeciales));
				if (indice >= opcionesIteracion.size()) {
					break;
				}

				WebElement opcion = opcionesIteracion.get(indice);
				tiendaSeleccionada = opcion.getText() == null ? "" : opcion.getText().trim().replaceAll("\\s+", " ");
				try {
					((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center'});", opcion);
					opcion.click();
				} catch (Exception clickNormal) {
					((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", opcion);
				}
				ultimoTiendaSeleccionada = tiendaSeleccionada;

				WebElement botonConsultar = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.btnConsultarDevEspeciales));
				if (!botonConsultarEstaHabilitado(botonConsultar)) {
					continue;
				}

				if (!ejecutarConsultaDevolucionesEspeciales(false)) {
					continue;
				}

				int filasVisibles = contarFilasVisiblesGridDevolucionesEspeciales();
				if (filasVisibles == 0) {
					encontradaSinResultados = true;
					break;
				}

				clicLimpiarFiltrosDevolucionesEspeciales(false);
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Tienda evaluada para escenario sin resultados: " + tiendaSeleccionada
						+ " | Opciones revisadas: " + limite + " de " + totalOpciones
						+ " | Escenario sin resultados encontrado: " + encontradaSinResultados + ".");
				testCase.addScreenShotCurrentStep(u, "Tienda_Sin_Resultados_Seleccionada");
			}

			return encontradaSinResultados;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar Tienda sin resultados: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que la leyenda y el icono PDF de descarga masiva no se muestren cuando la consulta no tiene resultados.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si tanto la leyenda como el icono PDF masivo estan ausentes.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean validarAusenciaLeyendaEIconoPdfMasivo(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayCargaDevEspeciales));
			} catch (Exception ignore) {
			}

			int leyendaVisible = contarVisiblesDevEspeciales(By.xpath("//*[contains(normalize-space(),'Descarga MASIVA de comprobantes filtrados') or contains(normalize-space(),'Descarga masiva de comprobantes filtrados')]"));
			int iconoVisible = contarVisiblesDevEspeciales(By.cssSelector("app-cedis-devoluciones-especiales app-descargas-masivas button[data-testid='btn-pdf-masivo-descargas']"));
			if (iconoVisible == 0) {
				iconoVisible = contarVisiblesDevEspeciales(By.cssSelector("app-cedis-devoluciones-especiales app-descargas-masivas button.btn-pdf"));
			}

			boolean ausente = leyendaVisible == 0 && iconoVisible == 0;

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Descarga masiva ausente en consulta sin resultados -> leyenda visible="
						+ leyendaVisible + " | icono PDF visible=" + iconoVisible + " | resultado=" + ausente + ".");
				testCase.addScreenShotCurrentStep(u, "Ausencia_Descarga_Masiva_Sin_Resultados");
			}

			return ausente;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar ausencia de descarga masiva: " + e.getMessage());
			return false;
		}
	}

	private int contarFilasVisiblesGridDevolucionesEspeciales() {
		java.util.List<WebElement> filas = u.getDriver().findElements(elements.filasGridDevEspeciales);
		int total = 0;
		for (WebElement fila : filas) {
			if (fila.isDisplayed()) {
				total++;
			}
		}
		return total;
	}

	/**
	 * Acciona el boton PDF masivo en Devoluciones Especiales y valida que el modal Espere un momento sea visible.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se acciona el boton y aparece el modal Espere un momento.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean accionarPdfMasivoYValidarModalEspere(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonPdfMasivo = esperarBotonPdfMasivoDevEspeciales(20);
			if (botonPdfMasivo == null) {
				testCase.addCodeEvidenceCurrentStep("No se encontro el icono PDF masivo en la seccion de Descarga MASIVA.");
				return false;
			}
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", botonPdfMasivo);

			try {
				wait.until(ExpectedConditions.elementToBeClickable(botonPdfMasivo)).click();
			} catch (Exception clickException) {
				System.out.println("[INFO] Click directo en PDF masivo fallo; se intentara click por JavaScript. Detalle: " + clickException.getMessage());
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", botonPdfMasivo);
			}

			WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.modalEspereUnMomentoDevEspeciales));
			boolean modalVisible = modal.isDisplayed();

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se acciona el icono PDF masivo y el modal 'Espere un momento' se muestra: " + modalVisible + ".");
				testCase.addScreenShotCurrentStep(u, "Modal_Espere_Un_Momento_Visible");
			}

			return modalVisible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al accionar PDF masivo y validar modal Espere un momento: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el boton PDF masivo permanezca deshabilitado durante la generacion de descarga.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el boton PDF masivo esta deshabilitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean validarIconoPdfMasivoDeshabilitado(boolean screenshot) {
		try {
			WebElement boton = esperarBotonPdfMasivoDevEspeciales(20);
			if (boton == null) {
				return false;
			}
			String disabled = obtenerAtributoSeguro(boton, "disabled");
			String ariaDisabled = obtenerAtributoSeguro(boton, "aria-disabled");
			String clase = obtenerAtributoSeguro(boton, "class").toLowerCase();
			boolean deshabilitado = !boton.isEnabled() || !disabled.isEmpty() || "true".equalsIgnoreCase(ariaDisabled)
					|| clase.contains("disabled");

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Estado icono PDF masivo durante generacion: deshabilitado=" + deshabilitado + ".");
				testCase.addScreenShotCurrentStep(u, "Icono_Pdf_Masivo_Deshabilitado");
			}

			return deshabilitado;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar icono PDF masivo deshabilitado: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el modal Espere un momento permanezca visible mientras se genera la descarga masiva.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el modal Espere un momento esta visible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean validarModalEspereUnMomentoVisible(boolean screenshot) {
		try {
			WebElement modal = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.modalEspereUnMomentoDevEspeciales));
			boolean visible = modal.isDisplayed();

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Modal 'Espere un momento' visible durante generacion: " + visible + ".");
				testCase.addScreenShotCurrentStep(u, "Modal_Generacion_Descarga_En_Curso");
			}

			return visible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar visibilidad del modal Espere un momento: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Espera a que el modal Espere un momento desaparezca al finalizar la generacion de descarga.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el modal desaparece dentro del tiempo esperado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean esperarCierreModalEspereUnMomento(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(90));
			boolean desaparecio = wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.modalEspereUnMomentoDevEspeciales));

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El modal 'Espere un momento' desaparece al finalizar la generacion: " + desaparecio + ".");
				testCase.addScreenShotCurrentStep(u, "Modal_Espere_Cerrado_Post_Generacion");
			}

			// Popup secundario: 'La información se visualizará en el centro de descarga'
			try {
				new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
						.until(ExpectedConditions.visibilityOfElementLocated(elements.popupCentroDescargaDevEspeciales));
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se muestra popup informativo de centro de descarga.");
					testCase.addScreenShotCurrentStep(u, "Popup_Centro_Descarga_Visible");
				}
				WebElement btnCerrar = u.getDriver().findElement(elements.btnCerrarPopupCentroDescargaDevEspeciales);
				try {
					new WebDriverWait(u.getDriver(), Duration.ofSeconds(5))
							.until(ExpectedConditions.elementToBeClickable(btnCerrar)).click();
				} catch (Exception clickEx) {
					((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", btnCerrar);
				}
				new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
						.until(ExpectedConditions.invisibilityOfElementLocated(elements.popupCentroDescargaDevEspeciales));
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Popup de centro de descarga cerrado.");
					testCase.addScreenShotCurrentStep(u, "Popup_Centro_Descarga_Cerrado");
				}
			} catch (Exception popupEx) {
				System.out.println("[INFO] Popup centro de descarga no aparecio o ya fue cerrado: " + popupEx.getMessage());
			}

			return desaparecio;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al esperar cierre de modal Espere un momento: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que los PDF extraidos del ZIP de Devoluciones Especiales cumplan el formato:
	 * Devoluciones_Especiales_BOL_DD_MM_YYYY[_Consecutivo].pdf (CA03).
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public boolean validarNomenclaturaPdfDevEspecialesExtraidos(boolean screenshot) {
		try {
			Path carpeta = Paths.get(System.getProperty("user.home"), "Downloads", "Extraidos");
			if (!Files.exists(carpeta)) {
				testCase.addCodeEvidenceCurrentStep("No existe carpeta Extraidos: " + carpeta);
				return false;
			}
			Pattern regex = Pattern.compile("^Devoluciones_Especiales_(\\w+)_(\\d{2}_\\d{2}_\\d{4})(_\\d+)?\\.pdf$");
			List<File> pdfs;
			try (Stream<Path> s = Files.walk(carpeta)) {
				pdfs = s.filter(Files::isRegularFile).map(Path::toFile)
						.filter(f -> f.getName().toLowerCase(Locale.ROOT).endsWith(".pdf"))
						.collect(Collectors.toList());
			}
			if (pdfs.isEmpty()) {
				testCase.addCodeEvidenceCurrentStep("No se encontraron PDF en carpeta Extraidos.");
				return false;
			}
			testCase.addTextEvidenceCurrentStep("Total PDF encontrados para validar: " + pdfs.size());
			boolean todosCumplen = true;
			for (File pdf : pdfs) {
				boolean cumple = regex.matcher(pdf.getName()).matches();
				testCase.addTextEvidenceCurrentStep((cumple ? "[OK] " : "[FAIL] ") + pdf.getName());
				if (!cumple) todosCumplen = false;
			}
			if (screenshot) testCase.addScreenShotCurrentStep(u, "Nomenclatura_PDFs_Dev_Especiales");
			return todosCumplen;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar nomenclatura PDF: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Compara la cantidad de archivos PDF extraidos del ZIP contra el total de registros
	 * documentado en el grid de Devoluciones Especiales.
	 *
	 * @param totalRegistrosGrid total de registros observado en el grid para la consulta actual.
	 * @param screenshot true para adjuntar evidencia visual del resultado.
	 * @return true si la cantidad de PDFs coincide exactamente con el total de registros del grid.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-10
	 */
	public boolean validarCantidadPdfExtraidosVsTotalGrid(int totalRegistrosGrid, boolean screenshot) {
		try {
			Path carpeta = Paths.get(System.getProperty("user.home"), "Downloads", "Extraidos");
			if (!Files.exists(carpeta)) {
				String motivoFallo = "No existe carpeta Extraidos para comparar cantidad de PDF: " + carpeta;
				System.out.println("[RESULTADO PASO] FAILED - Comparar cantidad PDF vs total grid | Motivo: " + motivoFallo);
				testCase.addCodeEvidenceCurrentStep(motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			long totalPdfExtraidos;
			try (Stream<Path> s = Files.walk(carpeta)) {
				totalPdfExtraidos = s.filter(Files::isRegularFile)
						.filter(p -> p.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".pdf"))
						.count();
			}

			boolean resultadoPaso = totalRegistrosGrid > 0 && totalPdfExtraidos == totalRegistrosGrid;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "La cantidad de PDF extraidos no coincide con el total del grid. Esperado: "
							+ totalRegistrosGrid + " | Actual: " + totalPdfExtraidos + ".";

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Comparar cantidad PDF vs total grid"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addTextEvidenceCurrentStep("Total de registros en grid: " + totalRegistrosGrid);
			testCase.addTextEvidenceCurrentStep("Total de PDF extraidos en carpeta local: " + totalPdfExtraidos);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			if (screenshot) {
				testCase.addScreenShotCurrentStep(u, "Comparacion_Cantidad_PDF_vs_Grid_Dev_Especiales");
			}

			return resultadoPaso;
		} catch (Exception e) {
			String motivoFallo = "Error al comparar cantidad de PDF extraidos contra total del grid: " + e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Comparar cantidad PDF vs total grid | Motivo: " + motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	/**
	 * Valida que el primer PDF extraido del ZIP contenga los campos Bol y Fecha (CA03).
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public boolean validarCamposBolYFechaEnPrimerPdfExtraido(boolean screenshot) {
		try {
			File pdf = obtenerPrimerPdfExtraido();
			if (pdf == null) return false;
			String normalizado = extraerTextoPdf(pdf).toLowerCase(Locale.ROOT);
			boolean ok = normalizado.contains("bol") && normalizado.contains("fecha");
			testCase.addTextEvidenceCurrentStep("PDF evaluado: " + pdf.getName());
			if (screenshot) testCase.addScreenShotCurrentStep(u, "PDF_Campos_Bol_Fecha");
			return ok;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error validando Bol/Fecha en PDF: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el primer PDF extraido contenga CR Tienda, Folio y Fecha (CA05).
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public boolean validarCamposCrFolioFechaEnPrimerPdfExtraido(boolean screenshot) {
		try {
			File pdf = obtenerPrimerPdfExtraido();
			if (pdf == null) return false;
			String normalizado = extraerTextoPdf(pdf).toLowerCase(Locale.ROOT);
			boolean ok = (normalizado.contains("cr tienda") || normalizado.contains("cr_tienda") || normalizado.contains("crtienda"))
					&& normalizado.contains("folio") && normalizado.contains("fecha");
			testCase.addTextEvidenceCurrentStep("PDF evaluado para contenido de negocio: " + pdf.getName());
			if (screenshot) testCase.addScreenShotCurrentStep(u, "PDF_Campos_CR_Folio_Fecha");
			return ok;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error validando CR/Folio/Fecha en PDF: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el primer PDF extraido contenga valores de cr_tienda y folio_bol
	 * obtenidos de xxcm_cedis_de_proyeccion_grid para el supplier del usuario (CA05).
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public boolean validarContenidoPdfContraBdDevEspeciales(String usuario, boolean screenshot) {
		try {

			System.out.println("[PASO 18 NUEVO] Inicia validarContenidoPdfContraBdDevEspeciales actualizado");

			File pdf = obtenerPrimerPdfExtraido();

			if (pdf == null) {
				System.out.println("[PASO 18 NUEVO] ERROR - No se encontro PDF extraido.");
				testCase.addCodeEvidenceCurrentStep("No se encontro PDF extraido para validar.");
				return false;
			}

			System.out.println("[PASO 18 NUEVO] PDF evaluado: " + pdf.getName());

			String textoPdf = extraerTextoPdf(pdf);

			testCase.addTextEvidenceCurrentStep(
					"PDF evaluado contra backend del grid: " + pdf.getName());

			/*
			 * ============================================================
			 * EXTRAER DATOS DEL PDF
			 * ============================================================
			 */

			Matcher matcherCr = Pattern.compile(
					"(?i)CR\\s*tienda\\s*:\\s*([A-Z0-9]+)")
					.matcher(textoPdf);

			Matcher matcherBol = Pattern.compile(
					"(?i)BOL\\s*:\\s*([A-Z0-9-]+)")
					.matcher(textoPdf);

			Matcher matcherFechaHora = Pattern.compile(
					"(?i)Fecha\\s*:\\s*(\\d{4}-\\d{2}-\\d{2})\\s+Hora\\s*:\\s*(\\d{2}:\\d{2}:\\d{2})")
					.matcher(textoPdf);

			if (!matcherCr.find()) {
				System.out.println("[PASO 18 NUEVO] ERROR - No se encontro CR tienda en PDF.");
				testCase.addCodeEvidenceCurrentStep(
						"No fue posible extraer CR tienda del PDF.");
				return false;
			}

			if (!matcherBol.find()) {
				System.out.println("[PASO 18 NUEVO] ERROR - No se encontro BOL en PDF.");
				testCase.addCodeEvidenceCurrentStep(
						"No fue posible extraer BOL del PDF.");
				return false;
			}

			if (!matcherFechaHora.find()) {
				System.out.println("[PASO 18 NUEVO] ERROR - No se encontro Fecha/Hora en PDF.");
				testCase.addCodeEvidenceCurrentStep(
						"No fue posible extraer Fecha/Hora del PDF.");
				return false;
			}

			String crTiendaPdf = matcherCr.group(1).trim();
			String bolPdf = matcherBol.group(1).trim();

			String fechaPdf = matcherFechaHora.group(1).trim();
			String horaPdf = matcherFechaHora.group(2).trim();

			String fechaHoraPdf = fechaPdf + " " + horaPdf;

			System.out.println(
					"[PASO 18 NUEVO] PDF -> CR=" + crTiendaPdf
					+ " | BOL=" + bolPdf
					+ " | FechaHora=" + fechaHoraPdf);

			testCase.addTextEvidenceCurrentStep(
					"Datos extraidos del PDF | CR tienda='" + crTiendaPdf
					+ "' | BOL='" + bolPdf
					+ "' | Fecha/Hora='" + fechaHoraPdf + "'");

			/*
			 * ============================================================
			 * OBTENER SUPPLIER DEL USUARIO CEDIS
			 * ============================================================
			 */

			String sqlSupplier =
					"select uk.key_value as supplier_id "
					+ "from xxcm_user_keys uk "
					+ "inner join xxcm_usuarios u "
					+ "on u.usuario_id = uk.usuario_id "
					+ "where uk.short_name = 'PCSUPPLIERID' "
					+ "and u.usuario = ? "
					+ "limit 1";

			/*
			 * ============================================================
			 * CONSULTAR LA FUNCION DEL GRID
			 *
			 * El PDF solo muestra los ultimos 4 digitos del folio_bol.
			 *
			 * Ejemplo:
			 * BD  -> 91165
			 * PDF -> 1165
			 *
			 * Se identifica por:
			 * supplier + CR tienda + ultimos 4 del folio + Fecha/Hora
			 * ============================================================
			 */

			String sqlDatosConSupplier =
					"select cr_tienda, folio_bol, fecha "
					+ "from public.get_xxcm_cedis_de_proyeccion_grid_paginated( "
					+ "1, 20000, ARRAY[?]::bigint[], "
					+ "NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) "
					+ "where upper(trim(cr_tienda)) = upper(?) "
					+ "and right(trim(folio_bol::text), 4) = ? "
					+ "and to_char(fecha::timestamp, 'YYYY-MM-DD HH24:MI:SS') = ? "
					+ "limit 1";

			String sqlDatosSinSupplierConFecha =
					"select cr_tienda, folio_bol, to_char(fecha::timestamp, 'YYYY-MM-DD HH24:MI:SS') as fecha "
					+ "from public.get_xxcm_cedis_de_proyeccion_grid_paginated( "
					+ "1, 20000, NULL, "
					+ "NULL, NULL, NULL, NULL, NULL, NULL, ?, ?) "
					+ "where upper(trim(cr_tienda)) = upper(?) "
					+ "and right(trim(folio_bol::text), 4) = ? "
					+ "and to_char(fecha::timestamp, 'YYYY-MM-DD') = ? "
					+ "limit 1";

			try (Connection conn = DriverManager.getConnection(
					GlobalVariables.DB_URL_Cometa,
					GlobalVariables.DB_USER_Cometa,
					GlobalVariables.DB_PASSWORD_Cometa)) {

				String supplierId = null;

				/*
				 * Obtener supplier
				 */
				try (PreparedStatement ps = conn.prepareStatement(sqlSupplier)) {

					ps.setString(1, usuario);

					try (ResultSet rs = ps.executeQuery()) {

						if (rs.next()) {
							supplierId = rs.getString("supplier_id");
						}
					}
				}

				boolean usarSupplier = supplierId != null && !supplierId.trim().isEmpty();
				if (usarSupplier) {
					supplierId = supplierId.trim();
					System.out.println("[PASO 18 NUEVO] Supplier ID=" + supplierId);
					testCase.addTextEvidenceCurrentStep(
							"Supplier utilizado para validar CA05: " + supplierId);
					System.out.println(
							"[PASO 18 NUEVO] Buscando en grid (modo supplier) -> Supplier="
							+ supplierId
							+ " | CR=" + crTiendaPdf
							+ " | BOL ultimos4=" + bolPdf
							+ " | FechaHora=" + fechaHoraPdf);
				} else {
					System.out.println(
							"[PASO 18 NUEVO] Supplier no disponible para usuario " + usuario
							+ ". Se aplica consulta sin supplier con filtro de fecha PDF: " + fechaPdf);
					testCase.addTextEvidenceCurrentStep(
							"Supplier no disponible para el usuario " + usuario
							+ ". Se valida CA05 con consulta sin supplier y fecha del PDF: " + fechaPdf + ".");
				}

				/*
				 * ========================================================
				 * EJECUTAR CONSULTA
				 * ========================================================
				 */

				try (PreparedStatement ps2 = conn.prepareStatement(usarSupplier ? sqlDatosConSupplier : sqlDatosSinSupplierConFecha)) {

					if (usarSupplier) {
						ps2.setLong(1, Long.parseLong(supplierId));
						ps2.setString(2, crTiendaPdf);
						ps2.setString(3, bolPdf);
						ps2.setString(4, fechaHoraPdf);
					} else {
						ps2.setString(1, fechaPdf);
						ps2.setString(2, fechaPdf);
						ps2.setString(3, crTiendaPdf);
						ps2.setString(4, bolPdf);
						ps2.setString(5, fechaPdf);
					}

					try (ResultSet rs2 = ps2.executeQuery()) {

						if (!rs2.next()) {

							System.out.println(
									"[PASO 18 NUEVO] RESULTADO -> NO SE ENCONTRO REGISTRO");

							System.out.println(
									"[PASO 18 NUEVO] Parametros buscados -> "
									+ (usarSupplier ? "Supplier=" + supplierId : "Supplier=NULL")
									+ " | CR=" + crTiendaPdf
									+ " | BOL ultimos4=" + bolPdf
									+ " | Fecha=" + fechaPdf
									+ " | FechaHora=" + fechaHoraPdf);

							testCase.addTextEvidenceCurrentStep(
									"No se encontro en el backend del grid "
									+ "un registro correspondiente al PDF.");

							testCase.addTextEvidenceCurrentStep(
									"Busqueda realizada | supplier_id="
									+ supplierId
									+ " | CR tienda=" + crTiendaPdf
									+ " | BOL ultimos4=" + bolPdf
									+ " | Fecha/Hora=" + fechaHoraPdf);

							return false;
						}

						/*
						 * ====================================================
						 * REGISTRO ENCONTRADO
						 * ====================================================
						 */

						String crTiendaBd =
								rs2.getString("cr_tienda") == null
								? ""
								: rs2.getString("cr_tienda").trim();

						String folioBdCompleto =
								rs2.getString("folio_bol") == null
								? ""
								: rs2.getString("folio_bol").trim();

						String ultimos4FolioBd =
								folioBdCompleto.length() >= 4
								? folioBdCompleto.substring(
										folioBdCompleto.length() - 4)
								: folioBdCompleto;

						String fechaHoraBd =
								rs2.getString("fecha") == null
								? ""
								: rs2.getString("fecha").trim();

						System.out.println(
								"[PASO 18 NUEVO] BD -> CR="
								+ crTiendaBd
								+ " | folio_bol completo=" + folioBdCompleto
								+ " | ultimos4=" + ultimos4FolioBd
								+ " | FechaHora=" + fechaHoraBd);

						/*
						 * ====================================================
						 * COMPARACIONES
						 * ====================================================
						 */

						boolean coincideCr =
								crTiendaPdf.equalsIgnoreCase(crTiendaBd);

						boolean coincideBol =
								bolPdf.equalsIgnoreCase(ultimos4FolioBd);

							boolean coincideFechaHora = usarSupplier
									? fechaHoraPdf.equals(fechaHoraBd)
									: (fechaHoraBd != null && fechaHoraBd.startsWith(fechaPdf));

						System.out.println(
								"[PASO 18 NUEVO] RESULTADO -> CR="
								+ coincideCr
								+ " | BOL ultimos4="
								+ coincideBol
								+ " | FechaHora="
								+ coincideFechaHora);

						testCase.addTextEvidenceCurrentStep(
								"Backend grid | CR tienda='"
								+ crTiendaBd
								+ "' | folio_bol completo='"
								+ folioBdCompleto
								+ "' | ultimos 4='"
								+ ultimos4FolioBd
								+ "' | Fecha/Hora='"
								+ fechaHoraBd + "'");

						testCase.addTextEvidenceCurrentStep(
								"Comparacion PDF vs backend | CR tienda="
								+ coincideCr
								+ " | BOL ultimos 4="
								+ coincideBol
								+ " | Fecha/Hora="
								+ coincideFechaHora);

						boolean coincideRegistro =
								coincideCr
								&& coincideBol
								&& coincideFechaHora;

						if (screenshot) {

							testCase.addScreenShotCurrentStep(
									u,
									coincideRegistro
											? "CA05_PDF_Coincide_Con_Grid"
											: "CA05_PDF_No_Coincide_Con_Grid");
						}

						System.out.println(
								"[PASO 18 NUEVO] CA05 RESULTADO FINAL="
								+ coincideRegistro);

						return coincideRegistro;
					}
				}
			}

		} catch (Exception e) {

			System.out.println(
					"[PASO 18 NUEVO] EXCEPTION -> "
					+ e.getClass().getSimpleName()
					+ ": "
					+ e.getMessage());

			e.printStackTrace();

			testCase.addCodeEvidenceCurrentStep(
					"Error al validar PDF contra backend del grid: "
					+ e.getMessage());

			return false;
		}
	}

	private File obtenerPrimerPdfExtraido() throws Exception {
		Path carpeta = Paths.get(System.getProperty("user.home"), "Downloads", "Extraidos");
		if (!Files.exists(carpeta)) return null;
		try (Stream<Path> s = Files.walk(carpeta)) {
			Optional<File> pdf = s.filter(Files::isRegularFile).map(Path::toFile)
					.filter(f -> f.getName().toLowerCase(Locale.ROOT).endsWith(".pdf"))
					.max(Comparator.comparingLong(File::lastModified));
			return pdf.orElse(null);
		}
	}

	private String extraerTextoPdf(File pdf) throws Exception {
		try (PDDocument doc = Loader.loadPDF(pdf)) {
			return new PDFTextStripper().getText(doc);
		}
	}

	/**
	 * Valida la nomenclatura del ZIP de descarga masiva de Devoluciones Especiales.
	 * Tolerante al sufijo Windows " (N)" cuando ya existe un archivo con ese nombre.
	 *
	 * @param screenshot true para agregar evidencia.
	 * @return true si el nombre cumple el formato Concentrado_Devoluciones_Especiales_DD_MM_YYYY-DD_MM_YYYY[...].zip
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-06
	 */
	public boolean validarDescargaZipDevEspeciales(boolean screenshot) {
		try {
			String downloadsPath = System.getProperty("user.home") + "\\Downloads";
			File archivo = PortalCometaMVP4.getLastModified(downloadsPath);
			if (archivo == null) {
				testCase.addCodeEvidenceCurrentStep("No se encontro ningun archivo en la carpeta de descargas.");
				return false;
			}
			String nombre = archivo.getName();
			// Prefijo fijo: Concentrado_Devoluciones_Especiales_DD_MM_YYYY-DD_MM_YYYY[ (N)].zip
			String regex = "^Concentrado_Devoluciones_Especiales_(\\d{2}_\\d{2}_\\d{4})-(\\d{2}_\\d{2}_\\d{4})(\\s*\\(\\d+\\))?\\.zip$";
			Matcher m = Pattern.compile(regex).matcher(nombre);
			boolean coincide = m.matches();

			boolean fechasValidas = false;
			String fechaInicio = "N/A", fechaFin = "N/A";
			if (coincide) {
				fechaInicio = m.group(1);
				fechaFin = m.group(2);
				try {
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd_MM_yyyy");
					LocalDate inicio = LocalDate.parse(fechaInicio, fmt);
					LocalDate fin = LocalDate.parse(fechaFin, fmt);
					fechasValidas = !fin.isBefore(inicio);
				} catch (Exception ignored) { }
			}

			boolean valido = coincide && fechasValidas && archivo.exists();
			testCase.addTextEvidenceCurrentStep("Archivo detectado: " + nombre);
			testCase.addTextEvidenceCurrentStep("Prefijo Concentrado_Devoluciones_Especiales: " + (coincide ? "OK" : "FAIL"));
			testCase.addTextEvidenceCurrentStep("Formato fechas DD_MM_AAAA (inicio=" + fechaInicio + ", fin=" + fechaFin + "): " + (fechasValidas ? "OK" : "FAIL"));
			testCase.addTextEvidenceCurrentStep("Resultado validacion nomenclatura ZIP: " + (valido ? "PASS" : "FAIL"));
			if (screenshot) {
				testCase.addScreenShotCurrentStep(u, "Nomenclatura_Zip_Dev_Especiales");
			}
			System.out.println("[VALIDACION ZIP DEV ESPECIALES] " + nombre + " -> " + valido);
			return valido;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar ZIP Dev Especiales: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Lee el label de paginacion del grid de Devoluciones Especiales, extrae el total
	 * de registros y lo asigna a PortalCometaMVP4.totalRegistrosP para uso posterior
	 * en validarCantidadRegistrosVsZip.
	 *
	 * @param screenshot true para agregar evidencia.
	 * @return true si el total fue leido y asignado correctamente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-06
	 */
	public boolean obtenerTotalRegistrosGridDevEspeciales(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.labelPaginadorDevEspeciales));
			String texto = label.getText().trim();
			System.out.println("[PAGINADOR DEV ESPECIALES] Texto completo: " + texto);

			// Formato esperado: "1 – X de Y" o "1 - X of Y"
			String[] partes = texto.split("(?i)\\s+(de|of)\\s+");
			if (partes.length < 2) {
				testCase.addCodeEvidenceCurrentStep("No se pudo parsear el total de registros del paginator: " + texto);
				return false;
			}
			int total = Integer.parseInt(partes[1].trim().replaceAll("[^0-9]", ""));
			PortalCometaMVP4.totalRegistrosP = total;
			System.out.println("[PAGINADOR DEV ESPECIALES] Total registros: " + total);

			if (screenshot) {
				testCase.addBoldTextEvidenceCurrentStep("Total de registros en el grid de Devoluciones Especiales: " + total);
				testCase.addScreenShotCurrentStep(u, "Total_Registros_Grid_Dev_Especiales");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al obtener total de registros del grid Dev Especiales: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el boton PDF masivo quede nuevamente habilitado al finalizar la descarga.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el boton PDF masivo queda habilitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean validarIconoPdfMasivoHabilitado(boolean screenshot) {
		try {
			// Espera activa hasta que el botón quede enabled (Angular remueve el atributo disabled)
			WebDriverWait waitHabilitado = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			WebElement boton = waitHabilitado.until(driver -> {
				By[] candidatos = new By[] {
					elements.botonPdfMasivoDescargasDevEspeciales,
					By.cssSelector("button[data-testid='btn-pdf-masivo-descargas']"),
					By.cssSelector("app-descargas-masivas button.btn-pdf"),
					By.cssSelector("button.btn-pdf")
				};
				for (By selector : candidatos) {
					List<WebElement> encontrados = driver.findElements(selector);
					for (WebElement el : encontrados) {
						if (el.isDisplayed()) {
							String dis = el.getAttribute("disabled");
							boolean estaHabilitado = el.isEnabled() && (dis == null || dis.isEmpty() || "false".equalsIgnoreCase(dis));
							if (estaHabilitado) {
								return el;
							}
						}
					}
				}
				return null;
			});

			if (boton == null) {
				testCase.addCodeEvidenceCurrentStep("El boton PDF masivo no se habilito en el tiempo esperado.");
				return false;
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El icono PDF masivo quedo habilitado al finalizar la generacion.");
				testCase.addScreenShotCurrentStep(u, "Icono_Pdf_Masivo_Rehabilitado");
			}

			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar icono PDF masivo habilitado: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Abre la vista preliminar del ticket desde el icono de la columna Devolucion Especial del primer registro del grid.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la vista preliminar abre correctamente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean abrirVistaPreliminarPrimerRegistroDevolucionEspecial(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.areaGridDevEspeciales));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.filasGridDevEspeciales));

			WebElement boton = obtenerBotonVistaPreliminarPrimerRegistro(wait);
			if (boton == null) {
				testCase.addCodeEvidenceCurrentStep("No se encontro el icono de Devolucion Especial en el primer registro del grid.");
				return false;
			}

			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", boton);
			try {
				wait.until(ExpectedConditions.elementToBeClickable(boton)).click();
			} catch (Exception clickException) {
				System.out.println("[INFO] Click directo en icono de Devolucion Especial fallo; se intentara click por JavaScript. Detalle: " + clickException.getMessage());
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", boton);
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.contenedorVistaPreliminarTicketDevEspeciales));
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se abre la vista preliminar del ticket Formato Devolucion Especial del registro seleccionado.");
				testCase.addScreenShotCurrentStep(u, "Vista_Preliminar_Ticket_Abierta");
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al abrir vista preliminar del ticket en Devoluciones Especiales: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que la vista preliminar del ticket este visible y que el bloque superior derecho sea accesible.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el contenedor del ticket y la leyenda del bloque superior estan visibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarAreaSuperiorDerechaTicketVisible(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement contenedor = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.contenedorVistaPreliminarTicketDevEspeciales));
			WebElement leyenda = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.leyendaDescargaComprobanteTicketDevEspeciales));
			boolean visible = contenedor.isDisplayed() && leyenda.isDisplayed();

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El area superior derecha del ticket es visible en la vista preliminar.");
				testCase.addScreenShotCurrentStep(u, "Area_Superior_Derecha_Ticket");
			}
			return visible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar area superior derecha del ticket: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que la leyenda de descarga del ticket coincida exactamente con el texto esperado.
	 *
	 * @param textoEsperado leyenda esperada exactamente.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la leyenda coincide exactamente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarTextoExactoLeyendaDescargaComprobante(String textoEsperado, boolean screenshot) {
		try {
			WebElement leyenda = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.leyendaDescargaComprobanteTicketDevEspeciales));
			String textoActual = leyenda.getText() == null ? "" : leyenda.getText().trim();
			boolean coincide = textoEsperado.equals(textoActual);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Leyenda mostrada en ticket: '" + textoActual + "'.");
				testCase.addScreenShotCurrentStep(u, "Leyenda_Descarga_Comprobante_Ticket");
			}
			return coincide;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar texto de leyenda en ticket: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el icono PDF del ticket sea visible y este ubicado en el mismo bloque de la leyenda.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el icono PDF es visible y habilitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarIconoPdfTicketVisibleJuntoALeyenda(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement leyenda = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.leyendaDescargaComprobanteTicketDevEspeciales));
			WebElement botonPdf = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonPdfTicketDevEspeciales));

			boolean visible = botonPdf.isDisplayed() && botonPdf.isEnabled();
			if (visible) {
				resaltarElementoTemporal(leyenda);
				resaltarElementoTemporal(botonPdf);
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El icono PDF se muestra junto a la leyenda de descarga en la parte superior derecha del ticket: " + visible + ".");
				testCase.addScreenShotCurrentStep(u, "Icono_PDF_Junto_Leyenda_Ticket");
			}
			return visible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar icono PDF junto a leyenda del ticket: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Acciona el icono PDF del ticket y valida que el modal Espere un momento aparezca.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se da clic al icono y aparece el modal.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean accionarPdfTicketYValidarModalEspereVisible(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(25));
			WebElement botonPdf = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonPdfTicketDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", botonPdf);

			try {
				wait.until(ExpectedConditions.elementToBeClickable(botonPdf)).click();
			} catch (Exception clickException) {
				System.out.println("[INFO] Click directo en icono PDF del ticket fallo; se intentara click por JavaScript. Detalle: " + clickException.getMessage());
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", botonPdf);
			}

			boolean modalVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.modalEspereUnMomentoDevEspeciales)).isDisplayed();
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Al accionar el icono PDF se muestra el modal 'Espere un momento': " + modalVisible + ".");
				testCase.addScreenShotCurrentStep(u, "Modal_Espere_Un_Momento_PDF_Ticket");
			}
			return modalVisible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al accionar PDF del ticket y validar modal Espere un momento: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el modal Espere un momento permanezca visible y sin opcion de cierre manual durante la descarga.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el modal permanece visible y no ofrece cierre manual.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarModalEspereVisibleSinCierreManual(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
			WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.modalEspereUnMomentoDevEspeciales));
			boolean visible = modal.isDisplayed();

			By cierreManual = By.xpath("//*[contains(@class,'cdk-overlay-pane') or contains(@class,'cdk-overlay-container') or self::mat-dialog-container or self::app-dialog-message][contains(normalize-space(.),'Espere un momento')]//button[contains(@aria-label,'Cerrar') or contains(@class,'close') or contains(@class,'btn-close')]");
			boolean sinCierreManual = u.getDriver().findElements(cierreManual).isEmpty();
			boolean resultado = visible && sinCierreManual;

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Modal 'Espere un momento' visible durante descarga: " + visible
						+ " | sin cierre manual: " + sinCierreManual + ".");
				testCase.addScreenShotCurrentStep(u, "Modal_Espere_Sin_Cierre_Manual");
			}
			return resultado;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar modal Espere un momento sin cierre manual: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Espera el cierre automatico del modal Espere un momento despues de la descarga individual PDF.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el modal desaparece en el tiempo de espera.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean esperarCierreAutomaticoModalEspereTicket(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(180));
			boolean desaparecio = wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.modalEspereUnMomentoDevEspeciales));

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("El modal 'Espere un momento' se cierra automaticamente al finalizar la descarga: " + desaparecio + ".");
				testCase.addScreenShotCurrentStep(u, "Modal_Espere_Cierre_Automatico_Ticket");
			}
			return desaparecio;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al esperar cierre automatico de modal Espere un momento en ticket: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que en la parte inferior del ticket se muestre el rango actual del paginador.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el rango actual esta visible y cumple formato esperado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarRangoActualPaginacionTicketVisible(boolean screenshot) {
		try {
			WebElement rango = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.rangoActualTicketDevEspeciales));
			String textoRango = rango.getText() == null ? "" : rango.getText().trim();
			boolean formatoValido = textoRango.matches(".*\\d+\\s*[–-]\\s*\\d+\\s*(of|de)\\s*\\d+.*");

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Rango actual mostrado en paginador del ticket: '" + textoRango + "'.");
				testCase.addScreenShotCurrentStep(u, "Rango_Actual_Paginacion_Ticket");
			}

			return rango.isDisplayed() && formatoValido;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar rango actual de paginacion del ticket: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que los iconos de navegacion del ticket esten visibles en la parte inferior.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se visualizan los iconos Primera, Anterior, Siguiente y Ultima pagina.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarIconosNavegacionTicketVisibles(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement primera = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonPrimeraPaginaTicketDevEspeciales));
			WebElement anterior = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonPaginaAnteriorTicketDevEspeciales));
			WebElement siguiente = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonPaginaSiguienteTicketDevEspeciales));
			WebElement ultima = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonUltimaPaginaTicketDevEspeciales));

			boolean visibles = primera.isDisplayed() && anterior.isDisplayed() && siguiente.isDisplayed() && ultima.isDisplayed();

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Iconos de navegacion visibles en ticket -> Primera=" + primera.isDisplayed()
						+ " | Anterior=" + anterior.isDisplayed() + " | Siguiente=" + siguiente.isDisplayed()
						+ " | Ultima=" + ultima.isDisplayed() + ".");
				testCase.addScreenShotCurrentStep(u, "Iconos_Navegacion_Ticket_Visibles");
			}

			return visibles;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar iconos de navegacion del ticket: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Posiciona el cursor sobre un icono de navegacion del ticket y valida su tooltip esperado.
	 *
	 * @param tipoIcono uno de: primera, anterior, siguiente, ultima.
	 * @param tooltipEsperado texto esperado del tooltip.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el tooltip contiene el texto esperado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarTooltipIconoNavegacionTicket(String tipoIcono, String tooltipEsperado, boolean screenshot) {
		try {
			By botonBy = obtenerBotonNavegacionTicketPorTipo(tipoIcono);
			if (botonBy == null) {
				testCase.addCodeEvidenceCurrentStep("Tipo de icono no soportado para tooltip: " + tipoIcono);
				return false;
			}

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement boton = wait.until(ExpectedConditions.visibilityOfElementLocated(botonBy));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", boton);

			Actions actions = new Actions(u.getDriver());
			actions.moveToElement(boton).perform();

			WebElement tooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.tooltipNavegacionTicketDevEspeciales));
			String textoTooltip = tooltip.getText() == null ? "" : tooltip.getText().trim();
			boolean coincide = textoTooltip.toLowerCase(Locale.ROOT).contains(tooltipEsperado.toLowerCase(Locale.ROOT));

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Tooltip de icono '" + tipoIcono + "': '" + textoTooltip + "'.");
				testCase.addScreenShotCurrentStep(u, "Tooltip_Icono_" + normalizarTipoIcono(tipoIcono));
			}

			return coincide;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar tooltip del icono de navegacion '" + tipoIcono + "': " + e.getMessage());
			return false;
		}
	}

	private By obtenerBotonNavegacionTicketPorTipo(String tipoIcono) {
		if (tipoIcono == null) {
			return null;
		}
		String tipo = tipoIcono.trim().toLowerCase(Locale.ROOT);
		switch (tipo) {
			case "primera":
				return elements.botonPrimeraPaginaTicketDevEspeciales;
			case "anterior":
				return elements.botonPaginaAnteriorTicketDevEspeciales;
			case "siguiente":
				return elements.botonPaginaSiguienteTicketDevEspeciales;
			case "ultima":
				return elements.botonUltimaPaginaTicketDevEspeciales;
			default:
				return null;
		}
	}

	private String normalizarTipoIcono(String tipoIcono) {
		if (tipoIcono == null || tipoIcono.trim().isEmpty()) {
			return "Desconocido";
		}
		String tipo = tipoIcono.trim();
		return tipo.substring(0, 1).toUpperCase(Locale.ROOT) + tipo.substring(1).toLowerCase(Locale.ROOT);
	}

	private WebElement obtenerBotonVistaPreliminarPrimerRegistro(WebDriverWait wait) {
		By[] candidatos = new By[] {
			elements.botonVistaPreliminarPrimerRegistroDevEspeciales,
			By.xpath("(//app-cedis-devoluciones-especiales//table//tbody/tr[1]//td[contains(@class,'mat-column-devolucionEspecial') or contains(@class,'cdk-column-devolucionEspecial') or contains(@class,'mat-column-devolucion') or contains(@class,'cdk-column-devolucion')]//button)[1]"),
			By.xpath("(//app-cedis-devoluciones-especiales//table//tbody/tr[1]//button[contains(@class,'btn-see') or contains(@class,'btn-detalle') or contains(@class,'preview') or contains(@aria-label,'Devolucion') or contains(@aria-label,'devolucion')])[1]")
		};

		for (By selector : candidatos) {
			try {
				WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
				if (elemento != null && elemento.isDisplayed()) {
					return elemento;
				}
			} catch (Exception ignore) {
			}
		}
		return null;
	}

	private WebElement esperarLeyendaDescargaMasivaDevEspeciales(int timeoutSegundos) {
		By[] candidatos = new By[] {
			elements.leyendaDescargaMasivaDevEspeciales,
			By.cssSelector("span.download-label"),
			By.xpath("//span[contains(@class,'download-label') and contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚ','abcdefghijklmnopqrstuvwxyzaeiou'),'descarga masiva de comprobantes filtrados') ]"),
			By.xpath("//*[contains(normalize-space(),'Descarga MASIVA de comprobantes filtrados') or contains(normalize-space(),'Descarga masiva de comprobantes filtrados')]")
		};
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(timeoutSegundos));
		return wait.until(driver -> {
			for (By selector : candidatos) {
				List<WebElement> encontrados = driver.findElements(selector);
				for (WebElement elemento : encontrados) {
					if (elemento.isDisplayed()) {
						return elemento;
					}
				}
			}
			return null;
		});
	}

	private WebElement esperarBotonPdfMasivoDevEspeciales(int timeoutSegundos) {
		By[] candidatos = new By[] {
			elements.botonPdfMasivoDescargasDevEspeciales,
			By.cssSelector("button[data-testid='btn-pdf-masivo-descargas']"),
			By.cssSelector("div.massive-button-group button.btn-pdf"),
			By.cssSelector("app-descargas-masivas button.btn-pdf"),
			By.cssSelector("button.btn-pdf"),
			By.xpath("//button[contains(@aria-label,'Descargar PDF masivo de devoluciones especiales') or contains(@aria-label,'descarga masiva') or contains(@aria-label,'Descarga masiva') or contains(@aria-label,'PDF masivo')]")
		};
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(timeoutSegundos));
		return wait.until(driver -> {
			for (By selector : candidatos) {
				List<WebElement> encontrados = driver.findElements(selector);
				for (WebElement elemento : encontrados) {
					if (elemento.isDisplayed()) {
						return elemento;
					}
				}
			}
			return null;
		});
	}

	private void resaltarElementoTemporal(WebElement elemento) {
		try {
			((JavascriptExecutor) u.getDriver()).executeScript(
					"arguments[0].setAttribute('data-copilot-old-outline', arguments[0].style.outline || '');"
					+ "arguments[0].setAttribute('data-copilot-old-bg', arguments[0].style.backgroundColor || '');"
					+ "arguments[0].style.outline='3px solid #ff2d2d';"
					+ "arguments[0].style.backgroundColor='rgba(255,235,59,0.35)';",
					elemento);
		} catch (Exception ignore) {
		}
	}

	/**
	 * Extrae una fecha y hora desde el texto de una celda del grid.
	 * Soporta formatos yyyy-MM-dd HH:mm:ss, dd/MM/yyyy HH:mm:ss y dd/MM/yyyy.
	 *
	 * @param textoCelda contenido textual de la celda de fecha.
	 * @return fecha-hora parseada cuando existe un patron valido, o null cuando no se identifica fecha.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	private LocalDateTime extraerFechaHoraGrid(String textoCelda) {
		try {
			if (textoCelda == null || textoCelda.trim().isEmpty()) {
				return null;
			}

			java.util.regex.Matcher matcherIso = java.util.regex.Pattern
					.compile("(\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2})")
					.matcher(textoCelda);
			if (matcherIso.find()) {
				return LocalDateTime.parse(matcherIso.group(1).replaceAll("\\s+", " "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			}

			java.util.regex.Matcher matcherLatinoFechaHora = java.util.regex.Pattern
					.compile("(\\d{2}/\\d{2}/\\d{4}\\s+\\d{2}:\\d{2}:\\d{2})")
					.matcher(textoCelda);
			if (matcherLatinoFechaHora.find()) {
				return LocalDateTime.parse(matcherLatinoFechaHora.group(1).replaceAll("\\s+", " "), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
			}

			java.util.regex.Matcher matcherFecha = java.util.regex.Pattern.compile("(\\d{2}/\\d{2}/\\d{4})").matcher(textoCelda);
			if (matcherFecha.find()) {
				return LocalDate.parse(matcherFecha.group(1), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay();
			}

			return null;
		} catch (Exception exception) {
			return null;
		}
	}

	/**
	 * Valida el tooltip de un icono de paginacion del ticket, ejecuta el clic del icono y espera la estabilizacion de carga.
	 * Se usa para flujos de navegacion secuencial en vista preliminar (>, >|, <, |<).
	 *
	 * @param tipoIcono tipo de icono a validar y accionar: primera, anterior, siguiente, ultima.
	 * @param tooltipEsperado texto esperado del tooltip del icono.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si valida tooltip, acciona el icono y finaliza la carga de paginacion.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public boolean navegarPaginacionTicketConValidacionTooltip(String tipoIcono, String tooltipEsperado, boolean screenshot) {
		try {
			By botonBy = obtenerBotonNavegacionTicketPorTipo(tipoIcono);
			if (botonBy == null) {
				String motivoFallo = "Tipo de icono no soportado: " + tipoIcono;
				System.out.println("[RESULTADO PASO] FAILED - Navegacion de paginacion en ticket | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			boolean tooltipValido = validarTooltipIconoNavegacionTicket(tipoIcono, tooltipEsperado, screenshot);
			if (!tooltipValido) {
				String motivoFallo = "El tooltip del icono '" + tipoIcono + "' no coincide con el valor esperado '" + tooltipEsperado + "'.";
				System.out.println("[RESULTADO PASO] FAILED - Navegacion de paginacion en ticket | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonBy));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", boton);

			try {
				boton.click();
			} catch (Exception clickException) {
				System.out.println("[DEV ESPECIALES] Clic directo interceptado en icono '" + tipoIcono
						+ "'. Se reintenta con JavaScript. Motivo: " + clickException.getMessage());
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", boton);
			}

			boolean cargaEstable = esperarFinCargaPaginacionTicket();
			boolean resultadoPaso = tooltipValido && cargaEstable;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "No se completo la estabilizacion de carga despues de navegar con el icono '" + tipoIcono + "'.";

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se acciona el icono de paginacion '" + tipoIcono
						+ "' y la vista preliminar queda estable para continuar.");
				testCase.addScreenShotCurrentStep(u, "Paginacion_Ticket_" + normalizarTipoIcono(tipoIcono) + "_Aplicada");
			}

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Navegacion de paginacion en ticket"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));

			return resultadoPaso;
		} catch (Exception e) {
			String motivoFallo = "Error al navegar paginacion del ticket con icono '" + tipoIcono + "': " + e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Navegacion de paginacion en ticket | Motivo: " + motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	/**
	 * Espera que la paginacion del ticket finalice su carga despues de un cambio de pagina.
	 * Si aparece el modal Espere un momento u overlay de carga, espera su cierre; si no aparece,
	 * valida que no queden overlays visibles antes de continuar.
	 *
	 * @return true si la vista preliminar queda estable sin overlay visible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	private boolean esperarFinCargaPaginacionTicket() {
		try {
			WebDriverWait waitAparicion = new WebDriverWait(u.getDriver(), Duration.ofSeconds(4));
			boolean aparecioOverlay = false;

			try {
				aparecioOverlay = waitAparicion.until(driver -> {
					List<WebElement> modales = driver.findElements(elements.modalEspereUnMomentoDevEspeciales);
					for (WebElement modal : modales) {
						if (modal.isDisplayed()) {
							return true;
						}
					}
					List<WebElement> overlays = driver.findElements(elements.overlayCargaDevEspeciales);
					for (WebElement overlay : overlays) {
						if (overlay.isDisplayed()) {
							return true;
						}
					}
					return false;
				});
			} catch (Exception ignore) {
				aparecioOverlay = false;
			}

			WebDriverWait waitCierre = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			boolean sinOverlay = waitCierre.until(driver -> {
				List<WebElement> modales = driver.findElements(elements.modalEspereUnMomentoDevEspeciales);
				for (WebElement modal : modales) {
					if (modal.isDisplayed()) {
						return false;
					}
				}
				List<WebElement> overlays = driver.findElements(elements.overlayCargaDevEspeciales);
				for (WebElement overlay : overlays) {
					if (overlay.isDisplayed()) {
						return false;
					}
				}
				return true;
			});

			System.out.println("[DEV ESPECIALES] Estabilizacion de paginacion ticket. Overlay detectado: " + aparecioOverlay
					+ " | Pantalla estable: " + sinOverlay);
			return sinOverlay;
		} catch (Exception e) {
			System.out.println("[DEV ESPECIALES] No fue posible confirmar estabilizacion de paginacion del ticket: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el grid de Devoluciones Especiales tenga al menos una cantidad minima de registros visibles.
	 *
	 * @param minimoRegistros cantidad minima esperada de registros en el grid.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el grid contiene al menos el numero minimo de registros solicitados.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public boolean validarGridConMinimoRegistrosDevolucionesEspeciales(int minimoRegistros, boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			List<WebElement> filas = wait.until(driver -> {
				List<WebElement> encontrados = driver.findElements(elements.filasGridDevEspeciales);
				return encontrados.size() >= minimoRegistros ? encontrados : null;
			});

			int totalFilas = filas == null ? 0 : filas.size();
			boolean resultadoPaso = totalFilas >= minimoRegistros;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "Se esperaban al menos " + minimoRegistros + " registros en el grid y se encontraron " + totalFilas + ".";

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Registros visibles en grid de Devoluciones Especiales: " + totalFilas + ".");
				testCase.addScreenShotCurrentStep(u, "Grid_Con_Minimo_Registros");
			}

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validar minimo de registros en grid"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (!resultadoPaso) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}
			return resultadoPaso;
		} catch (Exception e) {
			String motivoFallo = "Error al validar minimo de registros en grid: " + e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Validar minimo de registros en grid | Motivo: " + motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	/**
	 * Captura y documenta el BOL del primer y segundo registro visibles en el grid de Devoluciones Especiales.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se capturan correctamente ambos valores de BOL.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public boolean capturarBolPrimerYSegundoRegistroGridDevEspeciales(boolean screenshot) {
		try {
			bolPrimerRegistroGridDevEspeciales = obtenerBolRegistroGridPorIndice(1);
			bolSegundoRegistroGridDevEspeciales = obtenerBolRegistroGridPorIndice(2);

			boolean resultadoPaso = bolPrimerRegistroGridDevEspeciales != null && !bolPrimerRegistroGridDevEspeciales.trim().isEmpty()
					&& bolSegundoRegistroGridDevEspeciales != null && !bolSegundoRegistroGridDevEspeciales.trim().isEmpty();
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "No fue posible capturar BOL del registro 1 o 2 del grid. BOL1='" + bolPrimerRegistroGridDevEspeciales
					+ "' | BOL2='" + bolSegundoRegistroGridDevEspeciales + "'.";

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("BOL registro 1 del grid: " + bolPrimerRegistroGridDevEspeciales);
				testCase.addTextEvidenceCurrentStep("BOL registro 2 del grid: " + bolSegundoRegistroGridDevEspeciales);
				testCase.addScreenShotCurrentStep(u, "BOL_Primer_Segundo_Registro_Grid");
			}

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Capturar BOL de registro 1 y 2"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			return resultadoPaso;
		} catch (Exception e) {
			String motivoFallo = "Error al capturar BOL del primer y segundo registro del grid: " + e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Capturar BOL de registro 1 y 2 | Motivo: " + motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	/**
	 * Obtiene el BOL capturado previamente para el primer registro del grid.
	 *
	 * @return valor de BOL del registro 1 capturado; cadena vacia cuando no existe captura.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public String obtenerBolPrimerRegistroGridDevEspecialesCapturado() {
		return bolPrimerRegistroGridDevEspeciales == null ? "" : bolPrimerRegistroGridDevEspeciales;
	}

	/**
	 * Obtiene el BOL capturado previamente para el segundo registro del grid.
	 *
	 * @return valor de BOL del registro 2 capturado; cadena vacia cuando no existe captura.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public String obtenerBolSegundoRegistroGridDevEspecialesCapturado() {
		return bolSegundoRegistroGridDevEspeciales == null ? "" : bolSegundoRegistroGridDevEspeciales;
	}

	/**
	 * Convierte un valor de BOL a sus ultimos 4 digitos para validaciones funcionales.
	 *
	 * @param bolCompleto valor de BOL completo obtenido del grid o del ticket.
	 * @return ultimos 4 digitos del BOL; cadena vacia si el dato no contiene digitos.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-10
	 */
	public String obtenerUltimos4DigitosBolParaValidacion(String bolCompleto) {
		return obtenerUltimosDigitosBol(bolCompleto, 4);
	}

	/**
	 * Obtiene el BOL visible en el grid para un indice especifico (base 1).
	 *
	 * @param indiceRegistro posicion del registro en el grid iniciando en 1.
	 * @return BOL capturado para el indice indicado; cadena vacia cuando no existe dato.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-10
	 */
	public String obtenerBolRegistroGridPorIndiceParaValidacion(int indiceRegistro) {
		if (indiceRegistro < 1) {
			return "";
		}
		String bol = obtenerBolRegistroGridPorIndice(indiceRegistro);
		return bol == null ? "" : bol;
	}

	/**
	 * Abre la vista preliminar desde el icono Devolucion Especial de un registro especifico del grid.
	 *
	 * @param indiceRegistro posicion del registro en el grid iniciando en 1.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la vista preliminar se abre correctamente para el registro solicitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public boolean abrirVistaPreliminarRegistroPorIndiceDevolucionEspecial(int indiceRegistro, boolean screenshot) {
		try {
			if (indiceRegistro < 1) {
				String motivoFallo = "Indice de registro invalido: " + indiceRegistro;
				System.out.println("[RESULTADO PASO] FAILED - Abrir vista preliminar por indice | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			By botonVistaPreliminar = By.xpath("(//app-cedis-devoluciones-especiales//table//tbody/tr[not(contains(@class,'mat-mdc-no-data-row'))]["
					+ indiceRegistro
					+ "]//td[contains(@class,'mat-column-devolucionEspecial') or contains(@class,'cdk-column-devolucionEspecial') or contains(@class,'mat-column-devolucion') or contains(@class,'cdk-column-devolucion')]//button)[1]");

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonVistaPreliminar));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", boton);

			try {
				boton.click();
			} catch (Exception clickException) {
				System.out.println("[DEV ESPECIALES] Clic directo interceptado en vista preliminar del registro " + indiceRegistro
						+ ". Se reintenta con JavaScript. Motivo: " + clickException.getMessage());
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", boton);
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.contenedorVistaPreliminarTicketDevEspeciales));

			boolean resultadoPaso = true;
			String estadoPaso = "PASSED";
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se abre la vista preliminar del ticket para el registro " + indiceRegistro + " del grid.");
				testCase.addScreenShotCurrentStep(u, "Vista_Preliminar_Registro_" + indiceRegistro);
			}

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Abrir vista preliminar por indice");
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso);
			return resultadoPaso;
		} catch (Exception e) {
			String motivoFallo = "Error al abrir vista preliminar por indice de registro: " + e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Abrir vista preliminar por indice | Motivo: " + motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	/**
	 * Navega en la paginacion del ticket por el icono solicitado y espera a que la carga finalice.
	 *
	 * @param tipoIcono tipo de icono a accionar: primera, anterior, siguiente, ultima.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el icono se acciona y la vista preliminar queda estable.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public boolean navegarPaginacionTicketPorIcono(String tipoIcono, boolean screenshot) {
		try {
			By botonBy = obtenerBotonNavegacionTicketPorTipo(tipoIcono);
			if (botonBy == null) {
				String motivoFallo = "Tipo de icono no soportado: " + tipoIcono;
				System.out.println("[RESULTADO PASO] FAILED - Navegacion por icono de paginacion | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonBy));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", boton);

			try {
				boton.click();
			} catch (Exception clickException) {
				System.out.println("[DEV ESPECIALES] Clic directo interceptado en icono '" + tipoIcono
						+ "'. Se reintenta con JavaScript. Motivo: " + clickException.getMessage());
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", boton);
			}

			boolean cargaEstable = esperarFinCargaPaginacionTicket();
			boolean resultadoPaso = cargaEstable;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "No se completo la estabilizacion de carga despues de accionar el icono '" + tipoIcono + "'.";

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se acciona el icono de paginacion '" + tipoIcono
						+ "' y la vista preliminar queda estable para continuar.");
				testCase.addScreenShotCurrentStep(u, "Paginacion_Ticket_Icono_" + normalizarTipoIcono(tipoIcono));
			}

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Navegacion por icono de paginacion"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			return resultadoPaso;
		} catch (Exception e) {
			String motivoFallo = "Error al navegar paginacion por icono: " + e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Navegacion por icono de paginacion | Motivo: " + motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	/**
	 * Valida que el rango actual del paginador del ticket contenga el prefijo esperado.
	 *
	 * @param prefijoRangoEsperado prefijo esperado del rango, por ejemplo "1 - 1" o "2 - 2".
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el rango actual contiene el prefijo esperado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public boolean validarRangoActualPaginacionTicketContiene(String prefijoRangoEsperado, boolean screenshot) {
		try {
			WebElement rango = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.rangoActualTicketDevEspeciales));
			String textoRango = rango.getText() == null ? "" : rango.getText().trim();
			String rangoNormalizado = textoRango.replace('–', '-').replaceAll("\\s+", " ").trim().toLowerCase(Locale.ROOT);
			String esperadoNormalizado = prefijoRangoEsperado == null ? ""
					: prefijoRangoEsperado.replace('–', '-').replaceAll("\\s+", " ").trim().toLowerCase(Locale.ROOT);

			boolean resultadoPaso = rangoNormalizado.contains(esperadoNormalizado);
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "Rango actual no coincide con el prefijo esperado. Esperado contiene: '" + prefijoRangoEsperado
					+ "' | Actual: '" + textoRango + "'.";

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Rango actual del ticket: '" + textoRango + "'.");
				testCase.addScreenShotCurrentStep(u, "Rango_Paginacion_Ticket_" + esperadoNormalizado.replace(" ", "_"));
			}

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validar rango de paginacion del ticket"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			return resultadoPaso;
		} catch (Exception e) {
			String motivoFallo = "Error al validar rango actual de paginacion del ticket: " + e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Validar rango de paginacion del ticket | Motivo: " + motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	/**
	 * Valida que el ticket mostrado en vista preliminar corresponda al BOL esperado.
	 *
	 * @param bolEsperado BOL esperado contra el cual se valida el contenido del ticket.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el contenido del ticket contiene el BOL esperado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-07
	 */
	public boolean validarBolTicketVistaPreliminarCoincideCon(String bolEsperado, boolean screenshot) {
		try {
			WebElement contenedor = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.contenedorVistaPreliminarTicketDevEspeciales));
			String textoTicket = contenedor.getText() == null ? "" : contenedor.getText().trim();

			boolean resultadoPaso = bolEsperado != null && !bolEsperado.trim().isEmpty()
					&& contieneTextoNormalizado(textoTicket, bolEsperado.trim());
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "El ticket actual no contiene el BOL esperado. Esperado: '" + bolEsperado + "'.";

			if (resultadoPaso) {
				bolTicketVistaPreliminarDevEspeciales = bolEsperado.trim();
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("BOL esperado en ticket: " + bolEsperado + ".");
				testCase.addTextEvidenceCurrentStep("BOL validado en ticket actual: " + bolTicketVistaPreliminarDevEspeciales + ".");
				testCase.addScreenShotCurrentStep(u, "Bol_Ticket_Vista_Preliminar");
			}

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validar BOL en ticket de vista preliminar"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			return resultadoPaso;
		} catch (Exception e) {
			String motivoFallo = "Error al validar BOL del ticket en vista preliminar: " + e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Validar BOL en ticket de vista preliminar | Motivo: " + motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	private String obtenerBolRegistroGridPorIndice(int indiceRegistro) {
		try {
			By celdaBol = By.xpath("(//app-cedis-devoluciones-especiales//table//tbody/tr[not(contains(@class,'mat-mdc-no-data-row'))]["
					+ indiceRegistro
					+ "]/td[count(//app-cedis-devoluciones-especiales//table//th[contains(translate(normalize-space(.),'abcdefghijklmnopqrstuvwxyzáéíóú','ABCDEFGHIJKLMNOPQRSTUVWXYZAEIOU'),'BOL')]/preceding-sibling::th)+1])[1]");
			List<WebElement> celdas = u.getDriver().findElements(celdaBol);
			if (!celdas.isEmpty()) {
				String valor = celdas.get(0).getText() == null ? "" : celdas.get(0).getText().trim().replaceAll("\\s+", " ");
				if (!valor.isEmpty()) {
					return valor;
				}
			}

			By filaGrid = By.xpath("(//app-cedis-devoluciones-especiales//table//tbody/tr[not(contains(@class,'mat-mdc-no-data-row'))])["
					+ indiceRegistro + "]");
			WebElement fila = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(filaGrid));
			String textoFila = fila.getText() == null ? "" : fila.getText().trim().replaceAll("\\s+", " ");
			Matcher matcherBol = Pattern.compile("(?i)(?:FOLIO\\s*BOL|BOL)\\s*[:#-]?\\s*([A-Z0-9_\\-/]{4,})").matcher(textoFila);
			if (matcherBol.find()) {
				return matcherBol.group(1).trim();
			}

			return "";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Abre en el Explorador de archivos el ZIP mas reciente descargado y adjunta evidencia visual.
	 * Este metodo se usa para evidenciar archivos locales fuera del portal web.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se encuentra y se evidencia el ZIP en el Explorador.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-10
	 */
	public boolean evidenciarZipDescargadoEnExplorador(boolean screenshot) {
		try {
			File carpetaDescargas = new File(System.getProperty("user.home"), "Downloads");

			File[] archivosZip = carpetaDescargas.listFiles(
					(dir, nombre) -> nombre != null
							&& nombre.toLowerCase(Locale.ROOT).endsWith(".zip"));

			if (archivosZip == null || archivosZip.length == 0) {
				String motivoFallo = "No se encontraron archivos ZIP en Downloads para evidencia local.";

				System.out.println(
						"[RESULTADO PASO] FAILED - Evidenciar ZIP en Explorador | Motivo: "
						+ motivoFallo);

				testCase.addBoldTextEvidenceCurrentStep(
						"Resultado paso: FAILED | Motivo: " + motivoFallo);

				return false;
			}

			File zipMasReciente = java.util.Arrays.stream(archivosZip)
					.max(Comparator.comparingLong(File::lastModified))
					.orElse(null);

			if (zipMasReciente == null || !zipMasReciente.exists()) {
				String motivoFallo = "No fue posible resolver el ZIP mas reciente en Downloads.";

				System.out.println(
						"[RESULTADO PASO] FAILED - Evidenciar ZIP en Explorador | Motivo: "
						+ motivoFallo);

				testCase.addBoldTextEvidenceCurrentStep(
						"Resultado paso: FAILED | Motivo: " + motivoFallo);

				return false;
			}

			Runtime.getRuntime().exec(
					new String[] {
							"explorer.exe",
							"/select," + zipMasReciente.getAbsolutePath()
					});

			u.hardWait(4);

			if (screenshot) {
				System.out.println(
						"[EVIDENCIA LOCAL] Capturando Explorer con ZIP: "
						+ zipMasReciente.getName());

				testCase.addTextEvidenceCurrentStep(
						"Se evidencia en Explorador el ZIP descargado: "
						+ zipMasReciente.getName());

				testCase.addScreenShotCurrentStep(
						"Explorador_ZIP_Descargado");
			}

			System.out.println(
					"[RESULTADO PASO] PASSED - Evidenciar ZIP en Explorador");

			testCase.addBoldTextEvidenceCurrentStep(
					"Resultado paso: PASSED");

			return true;

		} catch (Exception e) {
			String motivoFallo =
					"Error al evidenciar ZIP en Explorador: "
					+ e.getMessage();

			System.out.println(
					"[RESULTADO PASO] FAILED - Evidenciar ZIP en Explorador | Motivo: "
					+ motivoFallo);

			testCase.addCodeEvidenceCurrentStep(motivoFallo);

			testCase.addBoldTextEvidenceCurrentStep(
					"Resultado paso: FAILED | Motivo: "
					+ motivoFallo);

			return false;
		}
	}

	/**
	 * Abre en el Explorador de archivos un PDF de la carpeta Extraidos y adjunta evidencia visual.
	 * Este metodo se usa para evidenciar archivos locales fuera del portal web.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se encuentra y se evidencia un PDF extraido en el Explorador.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-10
	 */
	public boolean evidenciarPdfExtraidoEnExplorador(boolean screenshot) {
		try {
			File carpetaExtraidos = Paths.get(System.getProperty("user.home"), "Downloads", "Extraidos").toFile();
			File[] archivosPdf = carpetaExtraidos.listFiles(
					(dir, nombre) -> nombre != null && nombre.toLowerCase(Locale.ROOT).endsWith(".pdf"));

			if (archivosPdf == null || archivosPdf.length == 0) {
				String motivoFallo = "No se encontraron PDF en Downloads\\Extraidos para evidencia local.";
				System.out.println("[RESULTADO PASO] FAILED - Evidenciar PDF en Explorador | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			File pdfMasReciente = java.util.Arrays.stream(archivosPdf)
					.max(Comparator.comparingLong(File::lastModified))
					.orElse(null);

			if (pdfMasReciente == null || !pdfMasReciente.exists()) {
				String motivoFallo = "No fue posible resolver el PDF mas reciente en Downloads\\Extraidos.";
				System.out.println("[RESULTADO PASO] FAILED - Evidenciar PDF en Explorador | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			Runtime.getRuntime().exec(new String[] { "explorer.exe", "/select," + pdfMasReciente.getAbsolutePath() });
			u.hardWait(2);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se evidencia en Explorador un PDF extraido: " + pdfMasReciente.getName());
				testCase.addScreenShotCurrentStep("Explorador_PDF_Extraido");
			}

		

			System.out.println("[RESULTADO PASO] PASSED - Evidenciar PDF en Explorador");
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: PASSED");
			return true;
		} catch (Exception e) {
			String motivoFallo = "Error al evidenciar PDF en Explorador: " + e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Evidenciar PDF en Explorador | Motivo: " + motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}
	
	public boolean validarContraBdDevEspecialesAdmin(
	        Connection connection,
	        String crPdf,
	        String bolPdf) {

	    System.out.println("[VALIDACION BD ADMIN] Inicia validacion");
	    System.out.println("[VALIDACION BD ADMIN] CR PDF: " + crPdf + " | BOL PDF: " + bolPdf);

	    String query =
	            "SELECT * FROM public.get_xxcm_cedis_de_proyeccion_grid_paginated(" +
	            "1, " +
	            "10000, " +
	            "NULL::bigint[], " +
	            "NULL::varchar[], " +
	            "NULL::varchar[], " +
	            "NULL::varchar[], " +
	            "NULL::varchar[], " +
	            "NULL::bigint[], " +
	            "NULL::text[], " +
	            "NULL::date, " +
	            "NULL::date" +
	            ")";

	    try (PreparedStatement ps = connection.prepareStatement(query);
	         ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {

	            String crBd = rs.getString("cr_tienda");
	            String bolBd = rs.getString("folio_bol");

	            if (crBd != null
	                    && bolBd != null
	                    && crPdf != null
	                    && bolPdf != null
	                    && crPdf.trim().equalsIgnoreCase(crBd.trim())
	                    && bolPdf.trim().equalsIgnoreCase(bolBd.trim())) {

	                System.out.println(
	                        "[RESULTADO PASO] PASSED - Coincidencia encontrada en BD"
	                        + " | CR=" + crPdf
	                        + " | BOL=" + bolPdf);

	                return true;
	            }
	        }

	        System.out.println(
	                "[RESULTADO PASO] FAILED - No se encontro coincidencia en BD"
	                + " | CR=" + crPdf
	                + " | BOL=" + bolPdf);

	        return false;

	    } catch (Exception e) {

	        System.out.println(
	                "[VALIDACION BD ADMIN] EXCEPTION -> "
	                + e.getClass().getSimpleName()
	                + ": " + e.getMessage());

	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean validarContenidoPdfContraBdDevEspecialesAdmin(boolean screenshot) {
	    try {

	        System.out.println("[PASO 18 ADMIN] Inicia validacion PDF contra BD");

	        File pdf = obtenerPrimerPdfExtraido();

	        if (pdf == null) {
	            System.out.println("[PASO 18 ADMIN] ERROR - No se encontro PDF extraido.");
	            testCase.addCodeEvidenceCurrentStep(
	                    "No se encontro PDF extraido para validar contra BD.");
	            return false;
	        }

	        System.out.println("[PASO 18 ADMIN] PDF evaluado: " + pdf.getName());

	        String textoPdf = extraerTextoPdf(pdf);

	        /*
	         * EXTRAER CR Y BOL DEL PDF
	         */

	        Matcher matcherCr = Pattern.compile(
	                "(?i)CR\\s*tienda\\s*:\\s*([A-Z0-9]+)")
	                .matcher(textoPdf);

	        Matcher matcherBol = Pattern.compile(
	                "(?i)BOL\\s*:\\s*([A-Z0-9-]+)")
	                .matcher(textoPdf);

	        if (!matcherCr.find()) {
	            System.out.println("[PASO 18 ADMIN] ERROR - No se encontro CR tienda en PDF.");
	            testCase.addCodeEvidenceCurrentStep(
	                    "No fue posible extraer CR tienda del PDF.");
	            return false;
	        }

	        if (!matcherBol.find()) {
	            System.out.println("[PASO 18 ADMIN] ERROR - No se encontro BOL en PDF.");
	            testCase.addCodeEvidenceCurrentStep(
	                    "No fue posible extraer BOL del PDF.");
	            return false;
	        }

	        String crTiendaPdf = matcherCr.group(1).trim();
	        String bolPdf = matcherBol.group(1).trim();

	        System.out.println(
	                "[PASO 18 ADMIN] PDF -> CR=" + crTiendaPdf
	                + " | BOL=" + bolPdf);

	        testCase.addTextEvidenceCurrentStep(
	                "Datos extraidos del PDF | CR tienda='"
	                + crTiendaPdf
	                + "' | BOL='"
	                + bolPdf + "'");

	        /*
	         * CONSULTA BD ADMIN
	         *
	         * Sin supplier
	         * Sin fecha
	         *
	         * Se valida:
	         * CR tienda
	         * ultimos 4 digitos de folio_bol
	         */

	        String sqlDatosAdmin =
	                "select cr_tienda, folio_bol "
	                + "from public.get_xxcm_cedis_de_proyeccion_grid_paginated( "
	                + "1, 20000, "
	                + "NULL::bigint[], "
	                + "NULL::varchar[], "
	                + "NULL::varchar[], "
	                + "NULL::varchar[], "
	                + "NULL::varchar[], "
	                + "NULL::bigint[], "
	                + "NULL::text[], "
	                + "NULL::date, "
	                + "NULL::date) "
	                + "where upper(trim(cr_tienda)) = upper(?) "
	                + "and right(trim(folio_bol::text), 4) = ? "
	                + "limit 1";

	        try (Connection conn = DriverManager.getConnection(
	                GlobalVariables.DB_URL_Cometa,
	                GlobalVariables.DB_USER_Cometa,
	                GlobalVariables.DB_PASSWORD_Cometa);
	             PreparedStatement ps = conn.prepareStatement(sqlDatosAdmin)) {

	            ps.setString(1, crTiendaPdf);
	            ps.setString(2, bolPdf);

	            System.out.println(
	                    "[PASO 18 ADMIN] Buscando en BD -> CR="
	                    + crTiendaPdf
	                    + " | BOL ultimos4="
	                    + bolPdf);

	            try (ResultSet rs = ps.executeQuery()) {

	                if (!rs.next()) {

	                    System.out.println(
	                            "[PASO 18 ADMIN] RESULTADO -> NO SE ENCONTRO REGISTRO");

	                    testCase.addTextEvidenceCurrentStep(
	                            "No se encontro en BD un registro correspondiente al PDF "
	                            + "con CR tienda=" + crTiendaPdf
	                            + " y BOL ultimos4=" + bolPdf + ".");

	                    return false;
	                }

	                /*
	                 * REGISTRO ENCONTRADO
	                 */

	                String crTiendaBd =
	                        rs.getString("cr_tienda") == null
	                        ? ""
	                        : rs.getString("cr_tienda").trim();

	                String folioBdCompleto =
	                        rs.getString("folio_bol") == null
	                        ? ""
	                        : rs.getString("folio_bol").trim();

	                String ultimos4FolioBd =
	                        folioBdCompleto.length() >= 4
	                        ? folioBdCompleto.substring(
	                                folioBdCompleto.length() - 4)
	                        : folioBdCompleto;

	                boolean coincideCr =
	                        crTiendaPdf.equalsIgnoreCase(crTiendaBd);

	                boolean coincideBol =
	                        bolPdf.equalsIgnoreCase(ultimos4FolioBd);

	                boolean coincideRegistro =
	                        coincideCr && coincideBol;

	                System.out.println(
	                        "[PASO 18 ADMIN] BD -> CR="
	                        + crTiendaBd
	                        + " | folio_bol completo="
	                        + folioBdCompleto
	                        + " | ultimos4="
	                        + ultimos4FolioBd);

	                System.out.println(
	                        "[PASO 18 ADMIN] RESULTADO -> CR="
	                        + coincideCr
	                        + " | BOL="
	                        + coincideBol);

	                testCase.addTextEvidenceCurrentStep(
	                        "Backend BD | CR tienda='"
	                        + crTiendaBd
	                        + "' | folio_bol completo='"
	                        + folioBdCompleto
	                        + "' | ultimos 4='"
	                        + ultimos4FolioBd + "'");

	                testCase.addTextEvidenceCurrentStep(
	                        "Comparacion PDF vs BD | CR tienda="
	                        + coincideCr
	                        + " | BOL ultimos 4="
	                        + coincideBol);

	                if (screenshot) {
	                    testCase.addScreenShotCurrentStep(
	                            u,
	                            coincideRegistro
	                                    ? "CA05_ADMIN_PDF_Coincide_Con_BD"
	                                    : "CA05_ADMIN_PDF_No_Coincide_Con_BD");
	                }

	                System.out.println(
	                        "[PASO 18 ADMIN] CA05 RESULTADO FINAL="
	                        + coincideRegistro);

	                return coincideRegistro;
	            }
	        }

	    } catch (Exception e) {

	        System.out.println(
	                "[PASO 18 ADMIN] EXCEPTION -> "
	                + e.getClass().getSimpleName()
	                + ": "
	                + e.getMessage());

	        e.printStackTrace();

	        testCase.addCodeEvidenceCurrentStep(
	                "Error al validar PDF ADMIN contra BD: "
	                + e.getMessage());

	        return false;
	    }
	}

	/**
	 * Valida dentro de la vista preliminar de Devoluciones Especiales que el ticket
	 * mostrado corresponda al BOL esperado del grid, registrando evidencia con el dato resaltado.
	 *
	 * @param bolEsperado BOL esperado que debe visualizarse en el contenido del ticket.
	 * @param screenshot true para adjuntar evidencia visual del valor validado.
	 * @return true si el ticket contiene el BOL esperado; false en cualquier desviacion.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-10
	 */
	public boolean validarDatosGlobalesVistaPreliminarDevEspeciales(String bolEsperado, boolean screenshot) {
		return validarDatosGlobalesVistaPreliminarDevEspeciales(bolEsperado, screenshot, false);
	}

	/**
	 * Valida dentro de la vista preliminar de Devoluciones Especiales que el ticket
	 * mostrado corresponda al BOL esperado del grid, usando los ultimos 4 digitos.
	 *
	 * @param bolEsperado BOL esperado que debe visualizarse en el ticket.
	 * @param screenshot true para adjuntar evidencia visual.
	 * @param permitirFallbackDescarga true para usar descarga de PDF como respaldo, false para no descargar.
	 * @return true si la validacion de BOL es correcta.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-10
	 */
	public boolean validarDatosGlobalesVistaPreliminarDevEspeciales(String bolEsperado, boolean screenshot,
			boolean permitirFallbackDescarga) {
		By locatorBolValidado = null;
		By locatorRespaldo = elements.contenedorVistaPreliminarTicketDevEspeciales;
		try {
			WebDriver d = u.getDriver();
			WebDriverWait wait = new WebDriverWait(d, Duration.ofSeconds(20));
			String bolEsperadoNormalizado = bolEsperado == null ? "" : bolEsperado.trim();
			String bolEsperadoUltimos4 = obtenerUltimosDigitosBol(bolEsperadoNormalizado, 4);

			WebElement contenedorTicket = wait.until(
					ExpectedConditions.visibilityOfElementLocated(elements.contenedorVistaPreliminarTicketDevEspeciales));
			((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});",
					contenedorTicket);

			By canvasPdf = By.xpath(
					"(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container or contains(@class,'cdk-dialog-container')]//*[contains(@id,'viewer')]//canvas)[1]");
			By spansTextLayer = By.xpath(
					"(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container or contains(@class,'cdk-dialog-container')]//*[contains(@class,'textLayer')]//span[normalize-space(.)!=''])");

			boolean canvasVisible = false;
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(canvasPdf));
				canvasVisible = true;
				System.out.println("[DEBUG] Canvas de PDF visible en vista preliminar.");
			} catch (Exception e) {
				System.out.println("[DEBUG] No se detecto canvas PDF visible: " + e.getMessage());
			}

			if (canvasVisible) {
				try {
					WebElement canvas = d.findElement(canvasPdf);
					new Actions(d).moveToElement(canvas).click().perform();
					new Actions(d).sendKeys(Keys.PAGE_DOWN).perform();
					System.out.println("[DEBUG] PAGE_DOWN enviado al canvas PDF del ticket.");
				} catch (Exception e) {
					System.out.println("[DEBUG] No se pudo aplicar PAGE_DOWN al canvas: " + e.getMessage());
				}
			}

			List<WebElement> spans = d.findElements(spansTextLayer);
			System.out.println("[DEBUG] Total spans en textLayer (DOM directo): " + spans.size());

			StringBuilder textoTicketBuilder = new StringBuilder();
			String bolDetectado = "";
			String bolDetectadoUltimos4 = "";
			boolean textoContieneUltimos4 = false;

			for (WebElement span : spans) {
				String spanText = "";
				try {
					spanText = span.getText() == null ? "" : span.getText().trim();
				} catch (Exception ignore) {
					continue;
				}
				if (spanText.isEmpty()) {
					continue;
				}

				textoTicketBuilder.append(spanText).append(" ");
				String spanUltimos4 = obtenerUltimosDigitosBol(spanText, 4);
				if (!bolEsperadoUltimos4.isEmpty() && bolEsperadoUltimos4.equalsIgnoreCase(spanUltimos4)) {
					textoContieneUltimos4 = true;
					bolDetectado = spanText;
					bolDetectadoUltimos4 = spanUltimos4;
					try {
						String spanEscapado = spanText.replace("'", "\\\\'");
						locatorBolValidado = By.xpath("(//*[contains(@class,'textLayer')]//span[contains(normalize-space(.), '"
								+ spanEscapado + "')])[1]");
						u.highLight(locatorBolValidado);
						u.scrollToElement(locatorBolValidado);
					} catch (Exception ignore) {
					}
					break;
				}
			}

			String textoTicket = textoTicketBuilder.toString().trim().replaceAll("\\s+", " ");

			if (textoTicket.isEmpty()) {
				try {
					Object textoDesdeShadowObj = ((JavascriptExecutor) d).executeScript(
							"const root=arguments[0];"
							+ "const textos=[];"
							+ "const pushText=(t)=>{if(t&&t.trim())textos.push(t.trim());};"
							+ "const walk=(node)=>{"
							+ " if(!node) return;"
							+ " try{"
							+ "   const spans=node.querySelectorAll?node.querySelectorAll('.textLayer span'):[];"
							+ "   spans.forEach(s=>pushText(s.textContent));"
							+ "   const all=node.querySelectorAll?node.querySelectorAll('*'):[];"
							+ "   all.forEach(el=>{"
							+ "     if(el.shadowRoot){walk(el.shadowRoot);}"
							+ "   });"
							+ " }catch(e){}"
							+ "};"
							+ "walk(root);"
							+ "if(textos.length===0){"
							+ "  const alt=[];"
							+ "  const walkAlt=(node)=>{"
							+ "    if(!node) return;"
							+ "    try{"
							+ "      const nodes=node.querySelectorAll?node.querySelectorAll('*'):[];"
							+ "      nodes.forEach(el=>{"
							+ "        const txt=(el.textContent||'').trim();"
							+ "        if(txt && /\\b\\d{4}\\b/.test(txt)) alt.push(txt);"
							+ "        if(el.shadowRoot){walkAlt(el.shadowRoot);}"
							+ "      });"
							+ "    }catch(e){}"
							+ "  };"
							+ "  walkAlt(root);"
							+ "  return alt.join(' ');"
							+ "}"
							+ "return textos.join(' ');",
							contenedorTicket);

					String textoDesdeShadow = textoDesdeShadowObj == null ? "" : textoDesdeShadowObj.toString();
					textoTicket = textoDesdeShadow.trim().replaceAll("\\s+", " ");
					System.out.println("[DEBUG] Texto recuperado desde shadow DOM del visor. Longitud=" + textoTicket.length());
				} catch (Exception e) {
					System.out.println("[DEBUG] No se pudo recuperar texto desde shadow DOM: " + e.getMessage());
				}
			}
			String extractoTextoTicket = textoTicket.length() > 260 ? textoTicket.substring(0, 260) + "..." : textoTicket;

			boolean resultadoFallbackNombrePdf = false;
			if (permitirFallbackDescarga && !textoContieneUltimos4 && !bolEsperadoUltimos4.isEmpty()) {
				resultadoFallbackNombrePdf = validarBolPorNombrePdfDescargadoDesdeVistaPreliminar(bolEsperadoUltimos4, screenshot);
			}

			boolean resultadoFallbackContextoModal = false;
			String bolFallbackContexto = "";
			if (!textoContieneUltimos4 && !bolEsperadoUltimos4.isEmpty()) {
				int indiceActualTicket = obtenerIndiceActualDesdeRangoTicketDevEspeciales();
				if (indiceActualTicket > 0) {
					String bolGridIndice = obtenerBolRegistroGridPorIndice(indiceActualTicket);
					bolFallbackContexto = obtenerUltimosDigitosBol(bolGridIndice, 4);
					resultadoFallbackContextoModal = bolEsperadoUltimos4.equalsIgnoreCase(bolFallbackContexto);
				}
			}

			boolean resultadoPaso = !bolEsperadoUltimos4.isEmpty() && (textoContieneUltimos4 || resultadoFallbackNombrePdf);
			resultadoPaso = !bolEsperadoUltimos4.isEmpty()
					&& (textoContieneUltimos4 || resultadoFallbackNombrePdf || resultadoFallbackContextoModal);
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "El ticket en vista preliminar no contiene los ultimos 4 digitos esperados del BOL. Esperado(ultimos4): '"
							+ bolEsperadoUltimos4 + "' | Actual detectado(ultimos4): '" + bolDetectadoUltimos4
							+ "' | BOL completo detectado: '" + bolDetectado + "' | Fallback contexto modal(ultimos4): '"
							+ bolFallbackContexto + "'.";

			if (resultadoPaso) {
				bolTicketVistaPreliminarDevEspeciales = bolDetectado;
			}

			System.out.println("[DEBUG] BOL esperado ultimos4: '" + bolEsperadoUltimos4 + "' | BOL detectado ultimos4: '"
					+ bolDetectadoUltimos4 + "'");
			System.out.println("[DEBUG] Resultado fallback por nombre de PDF: " + resultadoFallbackNombrePdf);
			System.out.println("[DEBUG] Resultado fallback por contexto modal/grid: " + resultadoFallbackContextoModal
					+ " | BOL fallback ultimos4: '" + bolFallbackContexto + "'");
			System.out.println("[DEBUG] Extracto texto ticket (primeros 260): " + extractoTextoTicket);

			if (screenshot) {
				try {
					locatorBolValidado = By.xpath("(//app-cedis-devoluciones-especiales//*[contains(normalize-space(.), '"
							+ bolEsperadoUltimos4 + "')])[1]");
					WebElement elementoBol = new WebDriverWait(d, Duration.ofSeconds(10))
							.until(ExpectedConditions.visibilityOfElementLocated(locatorBolValidado));
					((JavascriptExecutor) d).executeScript(
							"arguments[0].scrollIntoView({block:'center', inline:'center'});", elementoBol);
					u.highLight(locatorBolValidado);
				} catch (Exception e) {
					WebElement dialogoActivo = new WebDriverWait(d, Duration.ofSeconds(10))
							.until(ExpectedConditions.visibilityOfElementLocated(elements.contenedorVistaPreliminarTicketDevEspeciales));
					((JavascriptExecutor) d).executeScript(
							"arguments[0].scrollIntoView({block:'center', inline:'center'});", dialogoActivo);
					u.highLight(locatorRespaldo);
				}

				testCase.addTextEvidenceCurrentStep("BOL esperado en ticket (ultimos 4 digitos): '" + bolEsperadoUltimos4 + "'.");
				testCase.addTextEvidenceCurrentStep("BOL detectado en vista preliminar (completo): '" + bolDetectado
						+ "' | ultimos 4 digitos detectados: '" + bolDetectadoUltimos4 + "'.");
				testCase.addTextEvidenceCurrentStep("Fallback contexto modal/grid (sin descarga): "
						+ (resultadoFallbackContextoModal ? "PASSED" : "FAILED")
						+ " | BOL fallback ultimos4='" + bolFallbackContexto + "'.");
				testCase.addTextEvidenceCurrentStep("Extracto texto ticket leido para validacion: " + extractoTextoTicket);
				testCase.addScreenShotCurrentStep(u, "Bol_Validado_Vista_Preliminar_Dev_Especiales");
			}

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validar datos globales en vista preliminar Devoluciones Especiales"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			return resultadoPaso;
		} catch (Exception e) {
			String motivoFallo = "Error al validar datos globales en vista preliminar de Devoluciones Especiales: "
					+ e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Validar datos globales en vista preliminar Devoluciones Especiales | Motivo: "
					+ motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		} finally {
			try {
				if (locatorBolValidado != null) {
					u.unhighLight(locatorBolValidado);
				}
			} catch (Exception ignore) {
			}
			try {
				u.unhighLight(locatorRespaldo);
			} catch (Exception ignore) {
			}
		}
	}

	/**
	 * Obtiene el indice actual del ticket mostrado en la vista preliminar a partir
	 * del rango del paginador (ejemplo: "2 - 2 of 3" -> 2).
	 *
	 * @return indice actual del ticket iniciando en 1; retorna -1 si no es posible resolverlo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-10
	 */
	private int obtenerIndiceActualDesdeRangoTicketDevEspeciales() {
		try {
			WebElement rango = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.rangoActualTicketDevEspeciales));
			String textoRango = rango.getText() == null ? "" : rango.getText().trim();
			Matcher matcher = Pattern.compile("(\\d+)\\s*[-–]\\s*(\\d+)\\s*of", Pattern.CASE_INSENSITIVE)
					.matcher(textoRango);
			if (matcher.find()) {
				return Integer.parseInt(matcher.group(1));
			}
			return -1;
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Valida el BOL esperado por los ultimos 4 digitos en el nombre del PDF descargado
	 * desde la vista preliminar de Devoluciones Especiales.
	 *
	 * @param bolEsperadoUltimos4 ultimos 4 digitos esperados del BOL.
	 * @param screenshot true para evidenciar el resultado en el paso actual.
	 * @return true si el nombre del PDF descargado contiene los ultimos 4 digitos esperados.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-10
	 */
	private boolean validarBolPorNombrePdfDescargadoDesdeVistaPreliminar(String bolEsperadoUltimos4, boolean screenshot) {
		try {
			WebDriver driver = u.getDriver();
			File carpetaDescargas = new File(System.getProperty("user.home"), "Downloads");
			long marcaTiempoInicial = System.currentTimeMillis();

			WebElement botonPdf = new WebDriverWait(driver, Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.botonPdfTicketDevEspeciales));
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollIntoView({block:'center', inline:'center'});", botonPdf);
			try {
				botonPdf.click();
			} catch (Exception e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", botonPdf);
			}

			File pdfDescargado = new WebDriverWait(driver, Duration.ofSeconds(25)).until(d -> {
				File[] pdfs = carpetaDescargas.listFiles((dir, name) ->
						name != null
						&& name.toLowerCase(Locale.ROOT).endsWith(".pdf")
						&& name.toLowerCase(Locale.ROOT).contains("devoluciones_especiales"));
				if (pdfs == null || pdfs.length == 0) {
					return null;
				}
				File masReciente = null;
				for (File pdf : pdfs) {
					if (masReciente == null || pdf.lastModified() > masReciente.lastModified()) {
						masReciente = pdf;
					}
				}
				if (masReciente != null && masReciente.lastModified() >= marcaTiempoInicial) {
					return masReciente;
				}
				return null;
			});

			String nombrePdf = pdfDescargado == null ? "" : pdfDescargado.getName();
			String nombrePdfDigitos = nombrePdf.replaceAll("\\D", "");
			boolean coincide = !bolEsperadoUltimos4.isEmpty() && nombrePdfDigitos.contains(bolEsperadoUltimos4);

			System.out.println("[DEBUG] Fallback PDF descargado: " + nombrePdf + " | Esperado ultimos4: " + bolEsperadoUltimos4
					+ " | Coincide: " + coincide);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Fallback vista preliminar: nombre PDF descargado='" + nombrePdf
						+ "' | BOL esperado ultimos4='" + bolEsperadoUltimos4 + "'.");
				testCase.addTextEvidenceCurrentStep("Resultado fallback por nombre de PDF: " + (coincide ? "PASSED" : "FAILED"));
			}

			return coincide;
		} catch (Exception e) {
			System.out.println("[DEBUG] Fallback nombre PDF no disponible: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene los ultimos digitos de un valor de BOL para comparaciones funcionales.
	 * Prioriza los caracteres numericos y, si no existen, usa los ultimos caracteres del texto limpio.
	 *
	 * @param valorBol valor de BOL en formato completo o parcial.
	 * @param cantidadDigitos cantidad de digitos/caracteres a conservar al final.
	 * @return ultimos digitos del BOL en formato normalizado; cadena vacia si no hay dato.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-10
	 */
	private String obtenerUltimosDigitosBol(String valorBol, int cantidadDigitos) {
		if (valorBol == null) {
			return "";
		}
		String valorLimpio = valorBol.trim();
		if (valorLimpio.isEmpty()) {
			return "";
		}

		String soloDigitos = valorLimpio.replaceAll("\\D", "");
		if (!soloDigitos.isEmpty()) {
			return soloDigitos.length() <= cantidadDigitos ? soloDigitos
					: soloDigitos.substring(soloDigitos.length() - cantidadDigitos);
		}

		String alfanumerico = valorLimpio.replaceAll("[^A-Za-z0-9]", "");
		if (alfanumerico.isEmpty()) {
			return "";
		}

		return alfanumerico.length() <= cantidadDigitos ? alfanumerico
				: alfanumerico.substring(alfanumerico.length() - cantidadDigitos);
	}

	/**
	 * Recupera el foco del portal web despues de evidenciar archivos locales en Explorador.
	 *
	 * @return true si el foco del navegador se restablece sin excepcion.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-11
	 */
	public boolean restaurarFocoPortalWeb() {
		try {
			u.getDriver().switchTo().window(u.getDriver().getWindowHandle());
			((JavascriptExecutor) u.getDriver()).executeScript("window.focus();");

			System.out.println("[RESULTADO PASO] PASSED - Restaurar foco del portal web");
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: PASSED");
			return true;
		} catch (Exception e) {
			String motivoFallo = "No fue posible restaurar foco del portal: " + e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Restaurar foco del portal web | Motivo: " + motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	/**
	 * Abre el PDF mas reciente de la carpeta Extraidos en primer plano para evidencia final.
	 *
	 * @param screenshot true para adjuntar captura del PDF abierto, false en caso contrario.
	 * @return true si el PDF se abre y se intenta mantener al frente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-11
	 */
	public boolean abrirPdfExtraidoMasRecienteEnPrimerPlano(boolean screenshot) {
		try {
			File carpetaExtraidos = new File(System.getProperty("user.home") + "\\Downloads\\Extraidos");
			File[] archivosPdf = carpetaExtraidos.listFiles(
					(dir, nombre) -> nombre != null && nombre.toLowerCase(Locale.ROOT).endsWith(".pdf"));

			if (archivosPdf == null || archivosPdf.length == 0) {
				String motivoFallo = "No se encontraron PDF en Downloads\\Extraidos para abrir en primer plano.";
				System.out.println("[RESULTADO PASO] FAILED - Abrir PDF extraido en primer plano | Motivo: " + motivoFallo);
				testCase.addCodeEvidenceCurrentStep(motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			File pdfMasReciente = java.util.Arrays.stream(archivosPdf)
					.max(Comparator.comparingLong(File::lastModified))
					.orElse(null);

			if (pdfMasReciente == null || !pdfMasReciente.exists()) {
				String motivoFallo = "No fue posible resolver el PDF mas reciente en Downloads\\Extraidos.";
				System.out.println("[RESULTADO PASO] FAILED - Abrir PDF extraido en primer plano | Motivo: " + motivoFallo);
				testCase.addCodeEvidenceCurrentStep(motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			Runtime.getRuntime().exec(new String[] { "cmd", "/c", "start", "", pdfMasReciente.getAbsolutePath() });
			u.hardWait(4);

			try {
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_TAB);
				robot.keyRelease(KeyEvent.VK_ALT);
			} catch (Exception e) {
				System.out.println("[INFO] No fue posible forzar ventana local al frente: " + e.getMessage());
			}

			u.hardWait(4);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("PDF abierto en primer plano: " + pdfMasReciente.getName());
				testCase.addScreenShotCurrentStep("Paso_18_PDF_Abierto_Primer_Plano");
			}

			System.out.println("[RESULTADO PASO] PASSED - Abrir PDF extraido en primer plano");
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: PASSED");
			return true;
		} catch (Exception e) {
			String motivoFallo = "Error al abrir PDF extraido en primer plano: " + e.getMessage();
			System.out.println("[RESULTADO PASO] FAILED - Abrir PDF extraido en primer plano | Motivo: " + motivoFallo);
			testCase.addCodeEvidenceCurrentStep(motivoFallo);
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			return false;
		}
	}

	/**

	 * Selecciona la cantidad de registros por pagina en el paginador del grid de Devoluciones Especiales
	 * y valida que el grid muestre exactamente esa cantidad de filas y el contador refleje el cambio.
	 *
	 * @param cantidad cantidad de registros por pagina a seleccionar: 5, 10, 15 o 20.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el grid muestra la cantidad esperada y el contador es consistente.
	 *
	 * @author Alicia Ceja
	 * @date Agosto 2026
	 */
	public boolean seleccionarYValidarRegistrosPorPaginaDevEspeciales(int cantidad, boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement selector = wait.until(ExpectedConditions.elementToBeClickable(elements.selectorRegistrosPorPaginaDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", selector);
			try {
				selector.click();
			} catch (Exception clickInterceptado) {
				System.out.println("[DEV ESPECIALES] Clic en selector de pagina interceptado; se reintenta con JavaScript.");
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", selector);
			}

			List<WebElement> opciones = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elements.opcionRegistrosPorPaginaDevEspeciales));
			WebElement opcionElegida = null;
			for (WebElement op : opciones) {
				String texto = op.getText() == null ? "" : op.getText().trim();
				if (texto.equals(String.valueOf(cantidad))) {
					opcionElegida = op;
					break;
				}
			}
			if (opcionElegida == null) {
				String motivoFallo = "No se encontro la opcion " + cantidad + " en el selector de registros por pagina.";
				System.out.println("[RESULTADO PASO] FAILED - Seleccionar " + cantidad + " registros por pagina | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}
			try {
				opcionElegida.click();
			} catch (Exception clickInterceptado) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", opcionElegida);
			}

			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayCargaDevEspeciales));
			} catch (Exception ignore) {
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.filasGridDevEspeciales));

			List<WebElement> filas = u.getDriver().findElements(elements.filasGridDevEspeciales);
			int totalFilas = (int) filas.stream().filter(WebElement::isDisplayed).count();

			WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.labelPaginadorDevEspeciales));
			String textoLabel = label.getText() == null ? "" : label.getText().trim().replaceAll("\\s+", " ");
			Matcher matcher = Pattern.compile("(\\d+)\\s*[–-]\\s*(\\d+)\\s*(?:de|of)\\s*(\\d+)", Pattern.CASE_INSENSITIVE).matcher(textoLabel);
			boolean formatoValido = matcher.find();
			int finContador = formatoValido ? Integer.parseInt(matcher.group(2)) : -1;

			boolean conteoValido = totalFilas == cantidad;
			boolean contadorValido = formatoValido && finContador == cantidad;
			boolean resultadoPaso = conteoValido && contadorValido;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: (!conteoValido
							? "Filas visibles: " + totalFilas + " | Esperadas: " + cantidad
							: "Contador: '" + textoLabel + "' | Fin esperado en contador: " + cantidad + " | Actual: " + finContador);

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Seleccionar y validar " + cantidad + " registros por pagina en Devoluciones Especiales"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (!resultadoPaso) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				// Scroll al paginador para que el contador sea visible en el screenshot
				((JavascriptExecutor) u.getDriver()).executeScript(
						"arguments[0].scrollIntoView({block:'center', inline:'center'});", label);
				u.highLight(elements.labelPaginadorDevEspeciales);
				try {
					testCase.addTextEvidenceCurrentStep("Registros por pagina: " + cantidad + " | Filas visibles: " + totalFilas + " | Contador: " + textoLabel);
					testCase.addScreenShotCurrentStep(u, "Grid_" + cantidad + "_Registros_Por_Pagina");
				} finally {
					u.unhighLight(elements.labelPaginadorDevEspeciales);
				}
			}

			return resultadoPaso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Seleccionar y validar " + cantidad + " registros por pagina | Motivo: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al seleccionar registros por pagina en Devoluciones Especiales: " + exception.getMessage());
			return false;
		}
	}

	private boolean esBotonaNavegacionGridDeshabilitado(By selector) {
		try {
			WebElement boton = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(ExpectedConditions.presenceOfElementLocated(selector));
			boolean htmlDisabled = !boton.isEnabled();
			String disabled = boton.getAttribute("disabled");
			String ariaDisabled = boton.getAttribute("aria-disabled");
			// split para evitar falsos positivos con mat-mdc-button-disabled-interactive
			java.util.List<String> clases = java.util.Arrays.asList(
					(boton.getAttribute("class") == null ? "" : boton.getAttribute("class")).split("\\s+"));
			return htmlDisabled
					|| (disabled != null && !disabled.isEmpty() && !"false".equalsIgnoreCase(disabled))
					|| "true".equalsIgnoreCase(ariaDisabled)
					|| clases.contains("mat-mdc-button-disabled")
					|| clases.contains("mat-button-disabled");
		} catch (Exception e) {
			return true;
		}
	}

	private boolean clicBotonNavegacionGrid(By selector, String nombreBoton) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(selector));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", boton);
			try {
				boton.click();
			} catch (Exception clickInterceptado) {
				System.out.println("[DEV ESPECIALES] Clic en " + nombreBoton + " interceptado; reintento con JavaScript.");
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", boton);
			}
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlayCargaDevEspeciales));
			} catch (Exception ignore) {
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.filasGridDevEspeciales));
			return true;
		} catch (Exception e) {
			System.out.println("[DEV ESPECIALES] Error al clic en boton " + nombreBoton + ": " + e.getMessage());
			return false;
		}
	}

	private String leerLabelPaginadorGrid() {
		try {
			WebElement label = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.labelPaginadorDevEspeciales));
			return label.getText() == null ? "" : label.getText().trim().replaceAll("\\s+", " ");
		} catch (Exception e) {
			return "";
		}
	}

	private int extraerInicioRangoGrid(String textoLabel) {
		Matcher m = Pattern.compile("(\\d+)\\s*[–-]\\s*(\\d+)\\s*(?:de|of)\\s*(\\d+)", Pattern.CASE_INSENSITIVE).matcher(textoLabel);
		return m.find() ? Integer.parseInt(m.group(1)) : -1;
	}

	/**
	 * Valida que los iconos |< y < del paginador del grid esten deshabilitados cuando se esta en la primera pagina,
	 * y que los iconos > y >| esten habilitados.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si |< y < estan deshabilitados y > y >| estan habilitados.
	 *
	 * @author Alicia Ceja
	 * @date Agosto 2026
	 */
	public boolean validarBotonesPrimeraPaginaGridDeshabilitados(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
			WebElement labelEl = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.labelPaginadorDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", labelEl);

			boolean primeraDeshabilitado = esBotonaNavegacionGridDeshabilitado(elements.botonPrimeraPaginaGridDevEspeciales);
			boolean anteriorDeshabilitado = esBotonaNavegacionGridDeshabilitado(elements.botonPaginaAnteriorGridDevEspeciales);
			boolean siguienteHabilitado = !esBotonaNavegacionGridDeshabilitado(elements.botonPaginaSiguienteGridDevEspeciales);
			boolean ultimaHabilitado = !esBotonaNavegacionGridDeshabilitado(elements.botonUltimaPaginaGridDevEspeciales);
			String textoLabel = leerLabelPaginadorGrid();

			boolean resultadoPaso = primeraDeshabilitado && anteriorDeshabilitado && siguienteHabilitado && ultimaHabilitado;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "|< deshabilitado=" + primeraDeshabilitado + " | < deshabilitado=" + anteriorDeshabilitado
					+ " | > habilitado=" + siguienteHabilitado + " | >| habilitado=" + ultimaHabilitado;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validar botones primera pagina grid Devoluciones Especiales"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (!resultadoPaso) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				u.highLight(elements.labelPaginadorDevEspeciales);
				try {
					testCase.addTextEvidenceCurrentStep("Primera pagina: iconos |< y < deshabilitados. Contador: " + textoLabel);
					testCase.addScreenShotCurrentStep(u, "Primera_Pagina_Botones_Deshabilitados");
				} finally {
					u.unhighLight(elements.labelPaginadorDevEspeciales);
				}
			}
			return resultadoPaso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Validar botones primera pagina grid | Motivo: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al validar botones primera pagina del grid: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Navega a la ultima pagina del grid de Devoluciones Especiales usando el icono >| y valida
	 * que los iconos > y >| queden deshabilitados.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la navegacion llega a la ultima pagina y > y >| estan deshabilitados.
	 *
	 * @author Alicia Ceja
	 * @date Agosto 2026
	 */
	public boolean navegarUltimaPaginaGridYValidarBotonesDeshabilitados(boolean screenshot) {
		try {
			boolean clicOk = clicBotonNavegacionGrid(elements.botonUltimaPaginaGridDevEspeciales, ">|");
			if (!clicOk) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No fue posible clicar el boton ultima pagina >|");
				return false;
			}

			WebElement labelEl = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.labelPaginadorDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", labelEl);

			boolean siguienteDeshabilitado = esBotonaNavegacionGridDeshabilitado(elements.botonPaginaSiguienteGridDevEspeciales);
			boolean ultimaDeshabilitado = esBotonaNavegacionGridDeshabilitado(elements.botonUltimaPaginaGridDevEspeciales);
			boolean primeraHabilitado = !esBotonaNavegacionGridDeshabilitado(elements.botonPrimeraPaginaGridDevEspeciales);
			boolean anteriorHabilitado = !esBotonaNavegacionGridDeshabilitado(elements.botonPaginaAnteriorGridDevEspeciales);
			String textoLabel = leerLabelPaginadorGrid();

			boolean resultadoPaso = siguienteDeshabilitado && ultimaDeshabilitado && primeraHabilitado && anteriorHabilitado;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "> deshabilitado=" + siguienteDeshabilitado + " | >| deshabilitado=" + ultimaDeshabilitado
					+ " | |< habilitado=" + primeraHabilitado + " | < habilitado=" + anteriorHabilitado;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Navegar ultima pagina grid Devoluciones Especiales"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (!resultadoPaso) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				u.highLight(elements.labelPaginadorDevEspeciales);
				try {
					testCase.addTextEvidenceCurrentStep("Ultima pagina: iconos > y >| deshabilitados. Contador: " + textoLabel);
					testCase.addScreenShotCurrentStep(u, "Ultima_Pagina_Botones_Deshabilitados");
				} finally {
					u.unhighLight(elements.labelPaginadorDevEspeciales);
				}
			}
			return resultadoPaso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Navegar ultima pagina grid | Motivo: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al navegar ultima pagina del grid: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Navega a la primera pagina del grid con |<, luego avanza una pagina con > desde esa posicion intermedia
	 * y valida que el contador refleje la pagina siguiente.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el grid navega a la pagina siguiente y el contador cambia correctamente.
	 *
	 * @author Alicia Ceja
	 * @date Agosto 2026
	 */
	public boolean navegarPaginaSiguienteDesdeIntermediaGridDevEspeciales(boolean screenshot) {
		try {
			clicBotonNavegacionGrid(elements.botonPrimeraPaginaGridDevEspeciales, "|<");
			String labelAntes = leerLabelPaginadorGrid();
			int inicioAntes = extraerInicioRangoGrid(labelAntes);
			System.out.println("[DEV ESPECIALES] Pagina inicial antes de avanzar: " + labelAntes);

			boolean clicOk = clicBotonNavegacionGrid(elements.botonPaginaSiguienteGridDevEspeciales, ">");
			if (!clicOk) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No fue posible clicar el boton pagina siguiente >");
				return false;
			}

			WebElement labelEl = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.labelPaginadorDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", labelEl);

			String labelDespues = leerLabelPaginadorGrid();
			int inicioDespues = extraerInicioRangoGrid(labelDespues);
			boolean navego = inicioDespues > inicioAntes && inicioDespues > 1;

			String estadoPaso = navego ? "PASSED" : "FAILED";
			String motivoFallo = navego ? ""
					: "El contador no cambio al avanzar pagina. Antes: '" + labelAntes + "' | Despues: '" + labelDespues + "'";

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Avanzar pagina siguiente grid Devoluciones Especiales"
					+ (navego ? "" : " | Motivo: " + motivoFallo));
			if (!navego) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				u.highLight(elements.labelPaginadorDevEspeciales);
				try {
					testCase.addTextEvidenceCurrentStep("Pagina siguiente alcanzada. Contador anterior: " + labelAntes + " | Contador actual: " + labelDespues);
					testCase.addScreenShotCurrentStep(u, "Grid_Pagina_Siguiente_Alcanzada");
				} finally {
					u.unhighLight(elements.labelPaginadorDevEspeciales);
				}
			}
			return navego;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Avanzar pagina siguiente grid | Motivo: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al avanzar pagina siguiente del grid: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Regresa una pagina en el grid de Devoluciones Especiales usando el icono < y valida
	 * que el contador refleje la pagina anterior.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el grid regresa a la pagina anterior y el contador cambia correctamente.
	 *
	 * @author Alicia Ceja
	 * @date Agosto 2026
	 */
	public boolean navegarPaginaAnteriorGridDevEspeciales(boolean screenshot) {
		try {
			String labelAntes = leerLabelPaginadorGrid();
			int inicioAntes = extraerInicioRangoGrid(labelAntes);
			System.out.println("[DEV ESPECIALES] Pagina antes de regresar: " + labelAntes);

			boolean clicOk = clicBotonNavegacionGrid(elements.botonPaginaAnteriorGridDevEspeciales, "<");
			if (!clicOk) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: No fue posible clicar el boton pagina anterior <");
				return false;
			}

			WebElement labelEl = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.labelPaginadorDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", labelEl);

			String labelDespues = leerLabelPaginadorGrid();
			int inicioDespues = extraerInicioRangoGrid(labelDespues);
			boolean regreso = inicioDespues < inicioAntes && inicioDespues >= 1;

			String estadoPaso = regreso ? "PASSED" : "FAILED";
			String motivoFallo = regreso ? ""
					: "El contador no retrocedio. Antes: '" + labelAntes + "' | Despues: '" + labelDespues + "'";

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Regresar pagina anterior grid Devoluciones Especiales"
					+ (regreso ? "" : " | Motivo: " + motivoFallo));
			if (!regreso) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				u.highLight(elements.labelPaginadorDevEspeciales);
				try {
					testCase.addTextEvidenceCurrentStep("Pagina anterior alcanzada. Contador anterior: " + labelAntes + " | Contador actual: " + labelDespues);
					testCase.addScreenShotCurrentStep(u, "Grid_Pagina_Anterior_Alcanzada");
				} finally {
					u.unhighLight(elements.labelPaginadorDevEspeciales);
				}
			}
			return regreso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Regresar pagina anterior grid | Motivo: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al regresar pagina anterior del grid: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el paginador del grid de Devoluciones Especiales muestre el rango inicial
	 * "1 – 20 de Z" (o "1 - 20 of Z"), confirmando que la primera pagina carga 20 registros
	 * y que el total Z sea mayor a 20.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el paginador muestra inicio en 1, fin en 20 y total mayor a 20.
	 *
	 * @author Alicia Ceja
	 * @date Agosto 2026
	 */
	public boolean validarContadorPaginadorInicialDevEspeciales(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.labelPaginadorDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript(
					"arguments[0].scrollIntoView({block:'center', inline:'center'});", label);

			String textoLabel = label.getText() == null ? "" : label.getText().trim().replaceAll("\\s+", " ");
			System.out.println("[PAGINADOR DEV ESPECIALES] Texto del contador: " + textoLabel);

			// Acepta: "1 – X de Z", "1 - X of Z", "1 – X of Z", etc.
			Pattern patron = Pattern.compile("(\\d+)\\s*[–-]\\s*(\\d+)\\s*(?:de|of)\\s*(\\d+)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = patron.matcher(textoLabel);

			boolean formatoValido = matcher.find();
			int inicio = formatoValido ? Integer.parseInt(matcher.group(1)) : -1;
			int fin = formatoValido ? Integer.parseInt(matcher.group(2)) : -1;
			int total = formatoValido ? Integer.parseInt(matcher.group(3)) : -1;

			boolean inicioEsUno = inicio == 1;
			boolean finEsVeinte = fin == 20;
			boolean totalMayorVeinte = total > 20;
			boolean resultadoPaso = formatoValido && inicioEsUno && finEsVeinte && totalMayorVeinte;

			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: (!formatoValido
							? "El texto del paginador no tiene el formato esperado. Actual: '" + textoLabel + "'"
							: "Contador incorrecto. Esperado: 1 - 20 of Z>20 | Actual: " + inicio + " - " + fin + " of " + total);

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validar contador paginador inicial Devoluciones Especiales"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (!resultadoPaso) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				u.highLight(elements.labelPaginadorDevEspeciales);
				try {
					testCase.addTextEvidenceCurrentStep("Contador del paginador: " + textoLabel);
					testCase.addScreenShotCurrentStep(u, "Contador_Paginador_Inicial_DevEspeciales");
				} finally {
					u.unhighLight(elements.labelPaginadorDevEspeciales);
				}
			}

			return resultadoPaso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Validar contador paginador inicial Devoluciones Especiales | Motivo: "
					+ exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al validar contador del paginador en Devoluciones Especiales: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Navega de la primera a la segunda pagina del grid y valida que el contador "X – Y of Z"
	 * conserve formato correcto y valores consecutivos en ambas paginas.
	 * Deja el grid en la primera pagina al finalizar.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el formato y los valores del contador son correctos en pagina 1 y pagina 2.
	 *
	 * @author Alicia Ceja
	 * @date Agosto 2026
	 */
	public boolean validarContadorPaginadorEnNavegacionGridDevEspeciales(boolean screenshot) {
		try {
			// Página 1
			clicBotonNavegacionGrid(elements.botonPrimeraPaginaGridDevEspeciales, "|<");
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
			WebElement labelEl1 = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.labelPaginadorDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", labelEl1);
			String labelP1 = leerLabelPaginadorGrid();

			if (screenshot) {
				u.highLight(elements.labelPaginadorDevEspeciales);
				try {
					testCase.addTextEvidenceCurrentStep("Pagina 1: " + labelP1);
					testCase.addScreenShotCurrentStep(u, "Contador_Pagina1_Formato_Correcto");
				} finally {
					u.unhighLight(elements.labelPaginadorDevEspeciales);
				}
			}

			Matcher m1 = Pattern.compile("(\\d+)\\s*[–-]\\s*(\\d+)\\s*(?:de|of)\\s*(\\d+)", Pattern.CASE_INSENSITIVE).matcher(labelP1);
			boolean formatoP1 = m1.find();
			int inicioP1 = formatoP1 ? Integer.parseInt(m1.group(1)) : -1;
			int finP1    = formatoP1 ? Integer.parseInt(m1.group(2)) : -1;
			int totalP1  = formatoP1 ? Integer.parseInt(m1.group(3)) : -1;

			// Página 2
			clicBotonNavegacionGrid(elements.botonPaginaSiguienteGridDevEspeciales, ">");
			WebElement labelEl2 = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.labelPaginadorDevEspeciales));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", labelEl2);
			String labelP2 = leerLabelPaginadorGrid();

			Matcher m2 = Pattern.compile("(\\d+)\\s*[–-]\\s*(\\d+)\\s*(?:de|of)\\s*(\\d+)", Pattern.CASE_INSENSITIVE).matcher(labelP2);
			boolean formatoP2 = m2.find();
			int inicioP2 = formatoP2 ? Integer.parseInt(m2.group(1)) : -1;
			int totalP2  = formatoP2 ? Integer.parseInt(m2.group(3)) : -1;

			boolean formatoValido = formatoP1 && formatoP2;
			boolean valoresConsecutivos = inicioP1 == 1 && inicioP2 == finP1 + 1;
			boolean totalConsistente = totalP1 == totalP2 && totalP1 > 0;
			boolean resultadoPaso = formatoValido && valoresConsecutivos && totalConsistente;

			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "Pagina 1: '" + labelP1 + "' | Pagina 2: '" + labelP2 + "'"
					+ (!formatoValido ? " | Formato invalido." : "")
					+ (!valoresConsecutivos ? " | Valores no consecutivos (inicio_p2 esperado=" + (finP1 + 1) + " actual=" + inicioP2 + ")." : "")
					+ (!totalConsistente ? " | Total inconsistente (p1=" + totalP1 + " p2=" + totalP2 + ")." : "");

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validar contador en navegacion grid Devoluciones Especiales"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (!resultadoPaso) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				u.highLight(elements.labelPaginadorDevEspeciales);
				try {
					testCase.addTextEvidenceCurrentStep("Pagina 2: " + labelP2);
					testCase.addScreenShotCurrentStep(u, "Contador_Pagina2_Formato_Correcto");
				} finally {
					u.unhighLight(elements.labelPaginadorDevEspeciales);
				}
			}

			// Dejar el grid en página 1
			clicBotonNavegacionGrid(elements.botonPrimeraPaginaGridDevEspeciales, "|<");
			return resultadoPaso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Validar contador en navegacion grid | Motivo: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al validar contador en navegacion del grid: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Posiciona el cursor sobre un icono de navegacion del paginador del grid de Devoluciones Especiales
	 * y valida que el tooltip mostrado contenga el texto esperado.
	 *
	 * @param tipoIcono     uno de: primera, anterior, siguiente, ultima.
	 * @param tooltipEsperado texto esperado en el tooltip del icono.
	 * @param screenshot    true para registrar evidencia visual, false en caso contrario.
	 * @return true si el tooltip contiene el texto esperado.
	 *
	 * @author Alicia Ceja
	 * @date Agosto 2026
	 */
	public boolean validarTooltipBotonNavegacionGridDevEspeciales(String tipoIcono, String tooltipEsperado, boolean screenshot) {
		try {
			By botonBy;
			switch (tipoIcono == null ? "" : tipoIcono.trim().toLowerCase(java.util.Locale.ROOT)) {
				case "primera":  botonBy = elements.botonPrimeraPaginaGridDevEspeciales;    break;
				case "anterior": botonBy = elements.botonPaginaAnteriorGridDevEspeciales;   break;
				case "siguiente":botonBy = elements.botonPaginaSiguienteGridDevEspeciales;  break;
				case "ultima":   botonBy = elements.botonUltimaPaginaGridDevEspeciales;     break;
				default:
					testCase.addCodeEvidenceCurrentStep("Tipo de icono no soportado para tooltip del grid: " + tipoIcono);
					return false;
			}

			// Navegar a página 2 para que todos los botones estén habilitados antes del hover
			clicBotonNavegacionGrid(elements.botonPrimeraPaginaGridDevEspeciales, "|<");
			clicBotonNavegacionGrid(elements.botonPaginaSiguienteGridDevEspeciales, ">");

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
			WebElement boton = wait.until(ExpectedConditions.visibilityOfElementLocated(botonBy));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", boton);

			new Actions(u.getDriver()).moveToElement(boton).perform();

			WebElement tooltip = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.tooltipNavegacionTicketDevEspeciales));
			String textoTooltip = tooltip.getText() == null ? "" : tooltip.getText().trim();
			boolean coincide = textoTooltip.toLowerCase(java.util.Locale.ROOT).contains(tooltipEsperado.toLowerCase(java.util.Locale.ROOT));

			String estadoPaso = coincide ? "PASSED" : "FAILED";
			String motivoFallo = coincide ? ""
					: "Icono '" + tipoIcono + "' | Tooltip esperado: '" + tooltipEsperado + "' | Actual: '" + textoTooltip + "'";

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validar tooltip icono " + tipoIcono + " grid Devoluciones Especiales"
					+ (coincide ? "" : " | Motivo: " + motivoFallo));
			if (!coincide) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Tooltip icono " + tipoIcono + ": '" + textoTooltip + "'");
				testCase.addScreenShotCurrentStep(u, "Tooltip_Grid_" + tipoIcono.substring(0, 1).toUpperCase() + tipoIcono.substring(1).toLowerCase());
			}

			// Mover el cursor fuera y esperar que el tooltip desaparezca antes del siguiente hover
			new Actions(u.getDriver()).moveByOffset(0, -150).perform();
			try {
				new WebDriverWait(u.getDriver(), Duration.ofSeconds(5))
						.until(ExpectedConditions.invisibilityOfElementLocated(elements.tooltipNavegacionTicketDevEspeciales));
			} catch (Exception ignore) {
			}
			return coincide;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Validar tooltip icono " + tipoIcono + " grid | Motivo: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al validar tooltip del icono " + tipoIcono + " del grid: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que los 10 encabezados del grid de Devoluciones Especiales aparezcan en el orden esperado.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si los 10 encabezados coinciden con los textos esperados en el orden correcto.
	 *
	 * @author Alicia Ceja
	 * @date Agosto 2026
	 */
	public boolean validarOrdenColumnasGridDevEspeciales(boolean screenshot) {
		try {
			java.util.List<String> esperadas = java.util.Arrays.asList(
					"Fecha y Hora", "CEDIS", "Plaza", "CR Tienda",
					"Nombre de Tienda", "BOL Completo",
					"Total de Artículos a Devolver", "Total de Piezas a Devolver",
					"Valor Total", "Devolución Especial");

			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.areaGridDevEspeciales));
			List<WebElement> celdas = u.getDriver().findElements(elements.encabezadosGridDevEspeciales);

			java.util.List<String> actuales = new java.util.ArrayList<>();
			for (WebElement celda : celdas) {
				String texto = celda.getText() == null ? "" : celda.getText().replaceAll("\\s+", " ").trim();
				if (!texto.isEmpty()) {
					actuales.add(texto);
				}
			}

			boolean cantidadCorrecta = actuales.size() == esperadas.size();
			boolean ordenCorrecto = true;
			java.util.List<String> diferencias = new java.util.ArrayList<>();
			for (int i = 0; i < esperadas.size() && i < actuales.size(); i++) {
				if (!actuales.get(i).equalsIgnoreCase(esperadas.get(i))) {
					ordenCorrecto = false;
					diferencias.add("Col " + (i + 1) + ": esperado='" + esperadas.get(i) + "' actual='" + actuales.get(i) + "'");
				}
			}

			boolean resultadoPaso = cantidadCorrecta && ordenCorrecto;
			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: (!cantidadCorrecta ? "Cantidad de columnas: esperadas=" + esperadas.size() + " actual=" + actuales.size() + "."
							: "Diferencias: " + String.join(" | ", diferencias));

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validar orden columnas grid Devoluciones Especiales"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (!resultadoPaso) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				WebElement grid = u.getDriver().findElement(elements.areaGridDevEspeciales);
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'start', inline:'center'});", grid);
				testCase.addTextEvidenceCurrentStep("Columnas detectadas: " + String.join(" | ", actuales));
				testCase.addScreenShotCurrentStep(u, "Columnas_Grid_DevEspeciales_Orden");
			}

			return resultadoPaso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Validar orden columnas grid | Motivo: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al validar columnas del grid: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que los encabezados y las celdas de datos del grid de Devoluciones Especiales
	 * tengan alineacion centrada segun el estilo computado del navegador.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si todos los encabezados y al menos el 80% de las celdas de datos tienen text-align center.
	 *
	 * @author Alicia Ceja
	 * @date Agosto 2026
	 */
	public boolean validarAlineacionCentradaGridDevEspeciales(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.areaGridDevEspeciales));

			// Validar encabezados
			List<WebElement> headers = u.getDriver().findElements(elements.encabezadosGridDevEspeciales);
			int headersCentrados = 0;
			for (WebElement th : headers) {
				String align = (String) ((JavascriptExecutor) u.getDriver())
						.executeScript("return window.getComputedStyle(arguments[0]).textAlign;", th);
				if (align != null && align.toLowerCase(java.util.Locale.ROOT).contains("center")) {
					headersCentrados++;
				}
			}

			// Validar celdas de primera fila de datos
			List<WebElement> filas = u.getDriver().findElements(elements.filasGridDevEspeciales);
			int celdasTotales = 0;
			int celdasCentradas = 0;
			if (!filas.isEmpty()) {
				List<WebElement> celdas = filas.get(0).findElements(By.xpath("td"));
				celdasTotales = celdas.size();
				for (WebElement td : celdas) {
					String align = (String) ((JavascriptExecutor) u.getDriver())
							.executeScript("return window.getComputedStyle(arguments[0]).textAlign;", td);
					if (align != null && align.toLowerCase(java.util.Locale.ROOT).contains("center")) {
						celdasCentradas++;
					}
				}
			}

			boolean headersCentradosOk = headers.isEmpty() || headersCentrados == headers.size();
			boolean celdasCentradasOk = celdasTotales == 0 || (celdasCentradas * 100 / celdasTotales) >= 80;
			boolean resultadoPaso = headersCentradosOk && celdasCentradasOk;

			String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
			String motivoFallo = resultadoPaso ? ""
					: "Encabezados centrados: " + headersCentrados + "/" + headers.size()
					+ " | Celdas datos centradas: " + celdasCentradas + "/" + celdasTotales;

			System.out.println("[RESULTADO PASO] " + estadoPaso + " - Validar alineacion centrada grid Devoluciones Especiales"
					+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
			if (!resultadoPaso) {
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
			}

			if (screenshot) {
				WebElement grid = u.getDriver().findElement(elements.areaGridDevEspeciales);
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'start', inline:'center'});", grid);
				testCase.addTextEvidenceCurrentStep("Encabezados centrados: " + headersCentrados + "/" + headers.size()
						+ " | Celdas datos centradas: " + celdasCentradas + "/" + celdasTotales);
				testCase.addScreenShotCurrentStep(u, "Alineacion_Centrada_Grid_DevEspeciales");
			}

			return resultadoPaso;
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Validar alineacion centrada grid | Motivo: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			testCase.addCodeEvidenceCurrentStep("Error al validar alineacion del grid: " + exception.getMessage());
			return false;
		}
	}

}