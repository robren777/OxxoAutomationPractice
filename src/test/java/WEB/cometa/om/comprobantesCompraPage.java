package WEB.cometa.om;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;
import WEB.util.GlobalVariables;

public class comprobantesCompraPage {

	private final SeleniumUtil u;
	private final TestCase testCase;
	private final comprobantesCompraElements elements;

	/**
	 * Inicializa las acciones especificas de la pantalla Comprobantes de Compra.
	 *
	 * @param u utilidad Selenium asociada al navegador activo.
	 * @param testCase caso de prueba usado para registrar evidencia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public comprobantesCompraPage(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
		this.elements = new comprobantesCompraElements();
	}

	/**
	 * Abre el calendario de Comprobantes de Compra y selecciona la fecha inicial exacta.
	 * Mantiene abierto el calendario para que la fecha final se seleccione en el paso siguiente.
	 *
	 * @param fechaIso fecha inicial en formato yyyy-MM-dd.
	 * @return true cuando la fecha inicial queda seleccionada.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean seleccionarFechaInicio(String fechaIso) {
		try {
			LocalDate fecha = LocalDate.parse(fechaIso, DateTimeFormatter.ISO_LOCAL_DATE);
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.elementToBeClickable(elements.botonCalendario)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendario));
			posicionarMes(wait, fecha);
			seleccionarDia(wait, fecha.getDayOfMonth());
			String fechaEsperada = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			String fechaActual = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.inputFechaInicio))
					.getAttribute("value");
			boolean resultadoPaso = !driver.findElements(elements.calendario).isEmpty()
					&& coincideFechaUi(fecha, fechaActual);
			registrarResultado(resultadoPaso, "Fecha Inicio seleccionada: " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
					"Fecha Inicio esperada: " + fechaEsperada + " | Actual: " + fechaActual
							+ " | Calendario disponible: " + !driver.findElements(elements.calendario).isEmpty());
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible seleccionar la Fecha Inicio " + fechaIso + ": " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona la fecha final exacta en el calendario abierto de Comprobantes de Compra.
	 *
	 * @param fechaIso fecha final en formato yyyy-MM-dd.
	 * @return true cuando el rango queda completo y el calendario se cierra.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean seleccionarFechaFin(String fechaIso) {
		try {
			LocalDate fecha = LocalDate.parse(fechaIso, DateTimeFormatter.ISO_LOCAL_DATE);
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendario));
			posicionarMes(wait, fecha);
			seleccionarDia(wait, fecha.getDayOfMonth());
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendario));
			String fechaEsperada = fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			String fechaActual = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.inputFechaFin))
					.getAttribute("value");
			boolean resultadoPaso = driver.findElements(elements.calendario).isEmpty()
					&& coincideFechaUi(fecha, fechaActual)
					&& wait.until(ExpectedConditions.elementToBeClickable(elements.botonConsultarHabilitado)).isEnabled();
			registrarResultado(resultadoPaso, "Fecha Fin seleccionada y rango habilitado: " + fecha.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
					"Fecha Fin esperada: " + fechaEsperada + " | Actual: " + fechaActual
							+ " | El calendario no cerro o el boton Consultar no quedo habilitado");
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible seleccionar la Fecha Fin " + fechaIso + ": " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el grid de Comprobantes de Compra tenga al menos un registro visible.
	 *
	 * @return true cuando existe una fila de datos en el grid.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean validarGridConRegistros() {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
			boolean resultadoPaso = wait.until(driver -> !driver.findElements(elements.filasGrid).isEmpty());
			registrarResultado(resultadoPaso, "El grid de Comprobantes de Compra muestra registros correctamente cargados",
					"El grid de Comprobantes de Compra no muestra registros");
			if (resultadoPaso) {
				testCase.addScreenShotCurrentStep(u, "Grid de Comprobantes de Compra con registros");
			}
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible validar registros en el grid: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Navega el calendario abierto hasta el mes y anio de la fecha solicitada.
	 *
	 * @param wait espera explicita del calendario.
	 * @param fecha fecha cuyo mes debe quedar visible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	private void posicionarMes(WebDriverWait wait, LocalDate fecha) {
		YearMonth objetivo = YearMonth.from(fecha);
		for (int intento = 0; intento < 120; intento++) {
			String texto = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonPeriodo)).getText().trim()
					.replaceAll("\\s+", " ");
			YearMonth visible = convertirPeriodoCalendario(texto);
			if (visible.equals(objetivo)) {
				return;
			}
			By botonNavegacion = visible.isAfter(objetivo) ? elements.botonAnterior : elements.botonSiguiente;
			wait.until(ExpectedConditions.elementToBeClickable(botonNavegacion)).click();
		}
		throw new IllegalStateException("No fue posible posicionar el calendario en " + objetivo);
	}

	/**
	 * Selecciona un dia habilitado del mes visible en el calendario.
	 *
	 * @param wait espera explicita del calendario.
	 * @param dia numero de dia que debe seleccionarse.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	private void seleccionarDia(WebDriverWait wait, int dia) {
		String diaEsperado = String.valueOf(dia);
		org.openqa.selenium.WebElement botonDia = wait.until(driver -> driver.findElements(elements.diasHabilitados)
				.stream()
				.filter(elemento -> elemento.getText().trim().equals(diaEsperado))
				.findFirst()
				.orElse(null));
		wait.until(ExpectedConditions.elementToBeClickable(botonDia)).click();
	}

	/**
	 * Registra en consola y evidencia el resultado de un paso de Comprobantes de Compra.
	 *
	 * @param resultadoPaso resultado booleano del paso.
	 * @param mensajeExito mensaje que se registra cuando el paso es exitoso.
	 * @param motivoFallo motivo que se registra cuando el paso falla.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	private void registrarResultado(boolean resultadoPaso, String mensajeExito, String motivoFallo) {
		String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
		String evidencia = resultadoPaso ? mensajeExito : motivoFallo;
		System.out.println("[RESULTADO PASO] " + estadoPaso + " - " + evidencia);
		testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso + " - " + evidencia);
	}

	/**
	 * Registra una excepcion controlada como resultado FAILED del paso actual.
	 *
	 * @param mensajeError detalle de la excepcion o condicion no esperada.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	private void registrarError(String mensajeError) {
		System.out.println("[RESULTADO PASO] FAILED - " + mensajeError);
		testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED - " + mensajeError);
	}

	/**
	 * Valida que la pantalla Comprobantes de Compra muestre el titulo, los filtros
	 * de busqueda y el boton Consultar disponibles para interaccion.
	 *
	 * @return true cuando la pantalla y todos sus controles principales estan visibles.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean validarPantallaComprobantesCompra() {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
			boolean resultadoPaso = wait.until(driver -> estaVisible(elements.tituloComprobantesCompra)
					&& estaVisible(elements.filtroProveedor)
					&& estaVisible(elements.filtroFuenteAbasto)
					&& estaVisible(elements.filtroZona)
					&& estaVisible(elements.filtroRegion)
					&& estaVisible(elements.filtroPlaza)
					&& estaVisible(elements.filtroTienda)
					&& estaVisible(elements.filtroRangoFechas)
					&& estaVisible(elements.botonConsultar));
			registrarResultado(resultadoPaso,
					"Comprobantes de Compra muestra el titulo, los filtros y el boton Consultar",
					"Comprobantes de Compra no muestra todos los controles principales");
			if (resultadoPaso) {
				testCase.addScreenShotCurrentStep(u, "Pantalla Comprobantes de Compra disponible");
			}
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible validar la pantalla Comprobantes de Compra: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el filtro de Fuente de Abasto muestre la etiqueta esperada en Comprobantes de Compra.
	 *
	 * @param screenshot true para adjuntar captura del paso.
	 * @return true si la etiqueta contiene "Fuente de Abasto" y no se muestra solo "FA".
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-11
	 */
	public boolean validarEtiquetaFuenteAbastoEnComprobantesCompra(boolean screenshot) {
		try {
			By etiquetaFuenteAbasto = By.xpath("(//app-comprobantes-de-compra | //app-directos-comprobantes-de-compra)"
					+ "//ng-select[contains(@bindlabel,'fuenteDeAbasto')]"
					+ "//*[self::div[contains(@class,'ng-placeholder')] or self::mat-label][1]");
			WebElement etiqueta = u.waitWebElement(etiquetaFuenteAbasto, 20, 2);
			String texto = etiqueta.getText() == null ? "" : etiqueta.getText().trim();
			String textoNormalizado = texto.toUpperCase(Locale.ROOT)
					.replace("Á", "A")
					.replace("É", "E")
					.replace("Í", "I")
					.replace("Ó", "O")
					.replace("Ú", "U")
					.replaceAll("\\s+", " ")
					.trim();
			boolean resultadoPaso = !textoNormalizado.isEmpty()
					&& textoNormalizado.contains("FUENTE DE ABASTO")
					&& !"FA".equalsIgnoreCase(textoNormalizado);

			registrarResultado(resultadoPaso,
					"Etiqueta detectada en filtro Fuente de Abasto: '" + texto + "'",
					"La etiqueta del filtro Fuente de Abasto no cumple lo esperado. Valor detectado: '" + texto + "'");
			if (screenshot) {
				testCase.addScreenShotCurrentStep(u, "Etiqueta_Fuente_Abasto_Comprobantes_Compra");
			}
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible validar la etiqueta Fuente de Abasto en Comprobantes de Compra: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Determina si al menos un elemento asociado al localizador se encuentra visible.
	 *
	 * @param localizador selector del control que debe validarse.
	 * @return true cuando existe un elemento visible para el selector.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	private boolean estaVisible(By localizador) {
		return u.getDriver().findElements(localizador).stream().anyMatch(elemento -> elemento.isDisplayed());
	}

	/**
	 * Convierte el periodo mostrado por Angular Material a anio y mes.
	 * Acepta nombres cortos o largos en espanol y conserva ingles como respaldo.
	 *
	 * @param textoPeriodo texto visible en el encabezado del calendario.
	 * @return periodo convertido a YearMonth.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	private YearMonth convertirPeriodoCalendario(String textoPeriodo) {
		String textoNormalizado = textoPeriodo.toLowerCase(new Locale("es", "MX"))
				.replace(" de ", " ")
				.replace(".", "")
				.trim();
		Locale[] locales = { new Locale("es", "MX"), Locale.ENGLISH };
		String[] patrones = { "MMM yyyy", "MMMM yyyy" };
		for (Locale locale : locales) {
			for (String patron : patrones) {
				try {
					DateTimeFormatter formato = new DateTimeFormatterBuilder().parseCaseInsensitive()
							.appendPattern(patron).toFormatter(locale);
					return YearMonth.parse(textoNormalizado, formato);
				} catch (Exception exception) {
				}
			}
		}
		throw new IllegalArgumentException("Periodo de calendario no reconocido: " + textoPeriodo);
	}

	/**
	 * Obtiene desde xxcm_cc la fecha mas reciente con registros de Comprobantes de Compra
	 * y un volumen manejable para ejecutar la consulta automatizada.
	 *
	 * @return fecha con informacion en formato yyyy-MM-dd o null cuando no existe.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public String obtenerFechaConInformacionDesdeBD() {
		String sql = "select date(fecha)::text as fecha "
				+ "from public.xxcm_cc "
				+ "group by date(fecha) "
				+ "having count(*) between 1 and 999 "
				+ "order by date(fecha) desc "
				+ "limit 1";
		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa);
				PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {
			String fechaConInformacion = resultSet.next() ? resultSet.getString("fecha") : null;
			boolean resultadoPaso = fechaConInformacion != null && !fechaConInformacion.trim().isEmpty();
			registrarResultado(resultadoPaso,
					"Fecha con informacion obtenida desde xxcm_cc: " + fechaConInformacion,
					"La consulta a xxcm_cc no encontro una fecha con registros entre 1 y 999");
			return resultadoPaso ? fechaConInformacion : null;
		} catch (Exception exception) {
			registrarError("No fue posible obtener una fecha con informacion desde xxcm_cc: " + exception.getMessage());
			return null;
		}
	}

	/**
	 * Obtiene desde BD la fecha con mayor volumen de registros para un usuario segun su perfil,
	 * aplicando el mismo alcance funcional de Comprobantes de Compra (PCPLAZA, PCRFC o PCSUPPLIERID).
	 *
	 * @param usuario usuario de Portal COMETA para resolver perfil y llaves de alcance.
	 * @param minimoRegistros total minimo requerido para considerar valida la fecha.
	 * @return fecha en formato yyyy-MM-dd cuando existe una fecha que cumple el minimo; null en otro caso.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-11
	 */
	public String obtenerFechaConInformacionDesdeBDPorPerfilConMinimoRegistros(String usuario, int minimoRegistros) {
		String sqlPerfil = "select key_value "
				+ "from xxcm_user_keys "
				+ "where short_name = 'PCPERFIL' "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) "
				+ "limit 1";
		String sqlKeys = "select short_name, key_value "
				+ "from xxcm_user_keys "
				+ "where short_name in ('PCRFC', 'PCSUPPLIERID', 'PCPLAZA') "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?)";
		String sqlBase = "select c.fecha::date::text as fecha, count(*) as total_registros "
				+ "from xxcm_cc c "
				+ "left join xxcm_region_plaza_tienda_job_sync p on p.id_retek = c.id_retek ";
		String sqlFinal = "c.fecha is not null "
				+ "group by c.fecha::date "
				+ "having count(*) >= ? "
				+ "order by count(*) desc, c.fecha::date desc "
				+ "limit 1";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {
			String perfil = "";
			try (PreparedStatement statementPerfil = connection.prepareStatement(sqlPerfil)) {
				statementPerfil.setString(1, usuario);
				try (ResultSet resultSetPerfil = statementPerfil.executeQuery()) {
					if (resultSetPerfil.next()) {
						perfil = resultSetPerfil.getString("key_value") == null ? "" : resultSetPerfil.getString("key_value").trim();
					}
				}
			}

			String pcrfc = null;
			String pcSupplierId = null;
			String pcPlaza = null;
			try (PreparedStatement statementKeys = connection.prepareStatement(sqlKeys)) {
				statementKeys.setString(1, usuario);
				try (ResultSet resultSetKeys = statementKeys.executeQuery()) {
					while (resultSetKeys.next()) {
						String shortName = resultSetKeys.getString("short_name");
						String keyValue = resultSetKeys.getString("key_value");
						if ("PCRFC".equalsIgnoreCase(shortName)) {
							pcrfc = keyValue;
						} else if ("PCSUPPLIERID".equalsIgnoreCase(shortName)) {
							pcSupplierId = keyValue;
						} else if ("PCPLAZA".equalsIgnoreCase(shortName)) {
							pcPlaza = keyValue;
						}
					}
				}
			}

			String perfilNormalizado = perfil.toUpperCase(Locale.ROOT);
			String whereAlcance = "where ";
			String filtroAplicado = "GLOBAL";
			String valorFiltro = "SIN FILTRO";
			boolean aplicaFiltro = false;
			if (!"ADMIN".equals(perfilNormalizado) && !"OXXO_OS".equals(perfilNormalizado)) {
				if ("OXXO_PLAZA".equals(perfilNormalizado) && pcPlaza != null && !pcPlaza.trim().isEmpty()) {
					whereAlcance += "((p.cr_plaza::text || ' '::text) || p.desc_plaza::text) = ? and ";
					filtroAplicado = "PCPLAZA";
					valorFiltro = pcPlaza.trim();
					aplicaFiltro = true;
				} else if (pcrfc != null && !pcrfc.trim().isEmpty()) {
					whereAlcance += "c.proveedor_rfc = ? and ";
					filtroAplicado = "PCRFC";
					valorFiltro = pcrfc.trim();
					aplicaFiltro = true;
				} else if (pcSupplierId != null && !pcSupplierId.trim().isEmpty()) {
					whereAlcance += "c.proveedor_id::text = ? and ";
					filtroAplicado = "PCSUPPLIERID";
					valorFiltro = pcSupplierId.trim();
					aplicaFiltro = true;
				}
			}

			String sql = sqlBase + (aplicaFiltro ? whereAlcance : "where ") + sqlFinal;
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				int indiceParametro = 1;
				if (aplicaFiltro) {
					statement.setString(indiceParametro++, valorFiltro);
				}
				statement.setInt(indiceParametro, minimoRegistros);

				try (ResultSet resultSet = statement.executeQuery()) {
					String fechaConInformacion = null;
					int totalRegistros = 0;
					if (resultSet.next()) {
						fechaConInformacion = resultSet.getString("fecha");
						totalRegistros = resultSet.getInt("total_registros");
					}

					boolean resultadoPaso = fechaConInformacion != null && !fechaConInformacion.trim().isEmpty();
					registrarResultado(resultadoPaso,
							"Fecha por perfil con volumen suficiente obtenida desde BD: " + fechaConInformacion
									+ " | Usuario: " + usuario
									+ " | Perfil: " + perfil
									+ " | Filtro aplicado: " + filtroAplicado
									+ " | Valor filtro: " + valorFiltro
									+ " | Total registros: " + totalRegistros,
							"No se encontro una fecha para el usuario " + usuario
									+ " con al menos " + minimoRegistros + " registros en Comprobantes de Compra");
					return resultadoPaso ? fechaConInformacion : null;
				}
			}
		} catch (Exception exception) {
			registrarError("No fue posible obtener fecha por perfil con minimo de registros desde BD: " + exception.getMessage());
			return null;
		}
	}

	/**
	 * Compara una fecha esperada contra el valor mostrado por Angular Material.
	 * Acepta valores con o sin cero inicial en dia y mes.
	 *
	 * @param fechaEsperada fecha seleccionada desde el calendario.
	 * @param valorUi valor visible en el input de rango de fechas.
	 * @return true cuando ambos valores representan la misma fecha.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	private boolean coincideFechaUi(LocalDate fechaEsperada, String valorUi) {
		if (valorUi == null || valorUi.trim().isEmpty()) {
			return false;
		}
		try {
			LocalDate fechaUi = LocalDate.parse(valorUi.trim(), DateTimeFormatter.ofPattern("d/M/uuuu"));
			return fechaEsperada.equals(fechaUi);
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * Obtiene el total de registros mostrado en el paginador de Comprobantes de Compra.
	 * El total confirma que la consulta tiene volumen suficiente para enviar la descarga al Centro de Descargas.
	 *
	 * @param screenshot true si se requiere agregar evidencia visual del paginador.
	 * @return total de registros mostrado por el grid de Comprobantes de Compra.
	 * @throws Exception si no es posible leer o convertir el total del paginador.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-24
	 */
	public int obtenerTotalRegistrosComprobantesCompraActual(boolean screenshot) throws Exception {
		try {
			By paginadorComprobantes = By.xpath("(//app-comprobantes-de-compra | //app-directos-comprobantes-de-compra)//mat-paginator//div[contains(@class,'mat-mdc-paginator-range-label')] | //mat-paginator//div[contains(@class,'mat-mdc-paginator-range-label')]");
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(paginadorComprobantes));
			u.scrollToElement(paginadorComprobantes);
			u.highLight(paginadorComprobantes);

			String textoPaginador = u.getText(paginadorComprobantes);
			int totalRegistros = obtenerTotalRegistrosDesdePaginador(textoPaginador);

			if (screenshot) {
				testCase.addBoldTextEvidenceCurrentStep("Total de registros en Comprobantes de Compra: " + totalRegistros);
				testCase.addTextEvidenceCurrentStep("Texto del paginador: " + textoPaginador);
				testCase.addScreenShotCurrentStep(u, "Grid Comprobantes Compra Total Registros");
			}

			u.unhighLight(paginadorComprobantes);
			return totalRegistros;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al obtener total de registros de Comprobantes de Compra: " + exception.getMessage());
			throw new Exception("Error al obtener total de registros de Comprobantes de Compra: " + exception.getMessage());
		}
	}

	/**
	 * Monitorea en Centro de Descargas la solicitud mas reciente de Comprobantes de Compra hasta que su estatus sea Terminado.
	 * Consulta el DOM periodicamente mientras el portal actualiza el estatus automaticamente.
	 *
	 * @param fechaHoraSolicitud fecha y hora T1 registrada inmediatamente antes de solicitar la descarga.
	 * @param tiempoMaximoSegundos tiempo maximo de espera para que el estatus cambie a Terminado.
	 * @param fechaInicio fecha inicial usada para generar el documento esperado.
	 * @param fechaFin fecha final usada para generar el documento esperado.
	 * @param screenshot true si se requiere agregar evidencia visual del registro terminado.
	 * @return segundos transcurridos entre T1 y T2; retorna -1 si no termina dentro del tiempo maximo o queda Fallido.
	 * @throws Exception si ocurre un error durante la lectura del Centro de Descargas.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-24
	 */
	public long esperarUltimaDescargaComprobantesCompraTerminada(LocalDateTime fechaHoraSolicitud, String fechaInicio, String fechaFin, int tiempoMaximoSegundos, boolean screenshot) throws Exception {
		return esperarUltimaDescargaComprobantesCompraTerminada(fechaHoraSolicitud, fechaInicio, fechaFin, tiempoMaximoSegundos, screenshot, "xlsx");
	}

	/**
	 * Monitorea en Centro de Descargas la solicitud mas reciente de Comprobantes de Compra hasta que su estatus sea Terminado,
	 * permitiendo indicar la extension del documento esperado (por ejemplo "xlsx" para Excel o "zip" para el concentrado masivo de PDF).
	 * Complementa a {@link #esperarUltimaDescargaComprobantesCompraTerminada(LocalDateTime, String, String, int, boolean)},
	 * la cual conserva su contrato original delegando aqui con extension "xlsx" para no afectar a sus consumidores existentes.
	 *
	 * @param fechaHoraSolicitud fecha y hora T1 registrada inmediatamente antes de solicitar la descarga.
	 * @param fechaInicio fecha inicial usada para generar el documento esperado.
	 * @param fechaFin fecha final usada para generar el documento esperado.
	 * @param tiempoMaximoSegundos tiempo maximo de espera para que el estatus cambie a Terminado.
	 * @param screenshot true si se requiere agregar evidencia visual del registro terminado.
	 * @param extensionArchivoEsperado extension del archivo generado sin punto, por ejemplo "xlsx" o "zip".
	 * @return segundos transcurridos entre T1 y T2; retorna -1 si no termina dentro del tiempo maximo o queda Fallido.
	 * @throws Exception si ocurre un error durante la lectura del Centro de Descargas.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	public long esperarUltimaDescargaComprobantesCompraTerminada(LocalDateTime fechaHoraSolicitud, String fechaInicio, String fechaFin, int tiempoMaximoSegundos, boolean screenshot, String extensionArchivoEsperado) throws Exception {
		try {
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			DateTimeFormatter formatoTimestamp = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String hoy = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			String documentoEsperado = ("Concentrado_OC_" + fechaInicio + "_" + fechaFin + "." + extensionArchivoEsperado).toLowerCase(Locale.ROOT);
			long inicioMonitoreo = System.currentTimeMillis();
			String ultimoDetalle = "Sin registros evaluados";
			boolean evidenciaEnProcesoCapturada = false;

			By tabla = By.xpath("//table[contains(@class,'mat-mdc-table')]");
			By filas = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr");
			By btnFirst = By.xpath("//mat-paginator//button[contains(@class,'mat-mdc-paginator-navigation-first')]");
			By rangeLbl = By.xpath("//mat-paginator//div[contains(@class,'mat-mdc-paginator-range-label')]");

			while (((System.currentTimeMillis() - inicioMonitoreo) / 1000) <= tiempoMaximoSegundos) {
				u.waitWebElement(tabla, 30, 5);

				if (!driver.findElements(btnFirst).isEmpty()) {
					WebElement first = driver.findElement(btnFirst);
					String ariaDisabled = first.getAttribute("aria-disabled");
					if (first.isEnabled() && !"true".equalsIgnoreCase(ariaDisabled)) {
						String rangoAntes = obtenerTextoSeguro(driver, rangeLbl);
						try {
							first.click();
						} catch (Exception clickNormal) {
							((JavascriptExecutor) driver).executeScript("arguments[0].click();", first);
						}
						esperarRefrescoTabla(wait, rangeLbl, rangoAntes);
					}
				}

				List<WebElement> filasCentroDescargas = driver.findElements(filas);
				if (filasCentroDescargas.isEmpty()) {
					ultimoDetalle = "Centro de Descargas sin filas visibles";
				} else {
					int i = 1;
					By fila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]");
					By fechaFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-fecha')]//span");
					By moduloFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-modulo')]//span");
					By documentoFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-nombreDocumento')]//span");
					By estatusFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-estatus')]");
					By estatusTerminadoFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-estatus')]//*[contains(@class,'icon-check-circle') or contains(@class,'icon-success') or self::mat-icon[contains(normalize-space(.),'check_circle') or contains(@fonticon,'check_circle')] or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'terminado') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'finalizado')]");
					By estatusFallidoFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-estatus')]//*[contains(@class,'icon-error') or contains(translate(normalize-space(.),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'fallido')]");
					By iconosEstatusFila = By.xpath("//table[contains(@class,'mat-mdc-table')]//tbody//tr[" + i + "]//td[contains(@class,'mat-column-estatus')]//*[self::mat-icon or contains(@class,'icon-')]");

					String fecha = obtenerTextoSeguro(driver, fechaFila).trim().replaceAll("[^0-9/\\-]", "");
					String modulo = obtenerTextoSeguro(driver, moduloFila).trim();
					String documento = obtenerTextoSeguro(driver, documentoFila).trim();
					String estatus = obtenerTextoSeguro(driver, estatusFila).trim().replaceAll("\\s+", " ");

					ultimoDetalle = "Fila: " + i + " | Fecha: " + fecha + " | Modulo: " + modulo + " | Documento: " + documento + " | Estatus: " + estatus;
					System.out.println("[CENTRO DESCARGAS 191] " + ultimoDetalle);

					if (!hoy.equals(fecha) || !modulo.toLowerCase(Locale.ROOT).contains("comprobante")
							|| !documento.toLowerCase(Locale.ROOT).contains(documentoEsperado)) {
						testCase.addTextEvidenceCurrentStep("La primera fila de Centro de Descargas no corresponde a la solicitud actual esperada. Esperado: " + documentoEsperado + " | Lectura: " + ultimoDetalle);
						return -1L;
					}

					String htmlEstatus = "";
					try {
						htmlEstatus = driver.findElement(estatusFila).getAttribute("innerHTML");
					} catch (Exception ignore) {
					}
					StringBuilder detalleIconosEstatus = new StringBuilder();
					for (WebElement iconoEstatus : driver.findElements(iconosEstatusFila)) {
						detalleIconosEstatus.append(" ").append(iconoEstatus.getText())
								.append(" ").append(iconoEstatus.getAttribute("class"))
								.append(" ").append(iconoEstatus.getAttribute("fonticon"));
					}
					String estatusNormalizado = (estatus + " " + htmlEstatus + " " + detalleIconosEstatus.toString()).toLowerCase(Locale.ROOT);

					if (!driver.findElements(estatusFallidoFila).isEmpty() || estatusNormalizado.contains("fallido")) {
						testCase.addTextEvidenceCurrentStep("La solicitud de Comprobantes de Compra quedo en estatus Fallido. " + ultimoDetalle);
						return -1L;
					}

					if (screenshot && !evidenciaEnProcesoCapturada && (estatusNormalizado.contains("en proceso")
							|| estatusNormalizado.contains("icon-pending") || estatusNormalizado.contains("pending"))) {
						testCase.addTextEvidenceCurrentStep("Registro de Centro de Descargas en proceso antes del cambio a Terminado. " + ultimoDetalle);
						u.highLight(fila);
						testCase.addScreenShotCurrentStep(u, "Centro Descargas primer registro en proceso");
						u.unhighLight(fila);
						evidenciaEnProcesoCapturada = true;
					}

					if (!driver.findElements(estatusTerminadoFila).isEmpty() || estatusNormalizado.contains("check_circle")
							|| estatusNormalizado.contains("icon-check-circle") || estatusNormalizado.contains("terminado")
							|| estatusNormalizado.contains("finalizado")) {
						LocalDateTime fechaHoraTerminado = LocalDateTime.now();
						long tiempoGeneracion = ChronoUnit.SECONDS.between(fechaHoraSolicitud, fechaHoraTerminado);

						if (screenshot) {
							testCase.addBoldTextEvidenceCurrentStep("T1 inicio generacion: " + fechaHoraSolicitud.format(formatoTimestamp));
							testCase.addBoldTextEvidenceCurrentStep("T2 finalizacion Terminado: " + fechaHoraTerminado.format(formatoTimestamp));
							testCase.addBoldTextEvidenceCurrentStep("T_gen calculado: " + tiempoGeneracion + " segundos");
							testCase.addTextEvidenceCurrentStep("Registro terminado validado en Centro de Descargas. " + ultimoDetalle);
							u.highLight(fila);
							testCase.addScreenShotCurrentStep(u, "Centro Descargas primer registro terminado");
							u.unhighLight(fila);
						}

						return tiempoGeneracion;
					}
				}

				long segundosTranscurridos = (System.currentTimeMillis() - inicioMonitoreo) / 1000;
				if (segundosTranscurridos >= tiempoMaximoSegundos) {
					break;
				}

				u.hardWait(5);
			}

			testCase.addTextEvidenceCurrentStep("La solicitud esperada " + documentoEsperado + " no llego a Terminado dentro del tiempo maximo. Ultima lectura: " + ultimoDetalle);
			return -1L;
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al monitorear Centro de Descargas para Comprobantes de Compra: " + exception.getMessage());
			throw new Exception("Error al monitorear Centro de Descargas para Comprobantes de Compra: " + exception.getMessage());
		}
	}

	/**
	 * Extrae el total numerico del texto mostrado por el paginador de Comprobantes de Compra.
	 * Soporta textos como "1 - 10 de 1461" o "1-10 of 1461".
	 *
	 * @param textoPaginador texto completo mostrado por el paginador.
	 * @return total de registros extraido del ultimo numero del texto.
	 * @throws Exception si el texto no contiene un total interpretable.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-24
	 */
	private int obtenerTotalRegistrosDesdePaginador(String textoPaginador) throws Exception {
		if (textoPaginador == null || textoPaginador.trim().isEmpty()) {
			throw new Exception("El texto del paginador esta vacio");
		}

		String[] numeros = textoPaginador.replaceAll("[^0-9]+", " ").trim().split(" ");
		if (numeros.length == 0 || numeros[numeros.length - 1].trim().isEmpty()) {
			throw new Exception("No se encontro total numerico en el paginador: " + textoPaginador);
		}

		return Integer.parseInt(numeros[numeros.length - 1]);
	}

	/**
	 * Obtiene el texto de un elemento de forma segura para evitar que el monitoreo falle por celdas vacias o no presentes.
	 *
	 * @param driver instancia WebDriver activa.
	 * @param locator selector del elemento que se desea leer.
	 * @return texto del elemento o cadena vacia si no se puede leer.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-24
	 */
	private String obtenerTextoSeguro(WebDriver driver, By locator) {
		try {
			return driver.findElement(locator).getText();
		} catch (Exception exception) {
			return "";
		}
	}

	/**
	 * Espera que el paginador de Centro de Descargas refleje un cambio despues de navegar o refrescar la tabla.
	 * Si el rango previo no esta disponible, solo espera a que el label sea visible.
	 *
	 * @param wait espera explicita usada para sincronizar la tabla.
	 * @param rangeLbl selector del label de rango del paginador.
	 * @param oldRange valor previo del rango, si existe.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-24
	 */
	private void esperarRefrescoTabla(WebDriverWait wait, By rangeLbl, String oldRange) {
		try {
			if (oldRange == null) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(rangeLbl));
			} else {
				wait.until(driver -> {
					try {
						String nuevo = driver.findElement(rangeLbl).getText();
						return nuevo != null && !nuevo.trim().isEmpty() && !nuevo.equals(oldRange);
					} catch (Exception exception) {
						return false;
					}
				});
			}
		} catch (Exception ignored) {
		}
	}

	/**
	 * Obtiene un rango mensual de fechas desde BD usando fecha_registro del CTE que alimenta el grid de Comprobantes de Compra.
	 * Aplica el alcance configurado para el usuario con PCPLAZA, PCRFC o PCSUPPLIERID cuando el perfil no es global.
	 *
	 * @param usuario usuario de Portal COMETA usado para resolver el perfil y las llaves de alcance.
	 * @return mapa con fechaInicio, fechaFin, totalRegistros, perfil, filtroAplicado y valorFiltro obtenidos desde BD.
	 * @throws Exception si no existe un rango con registros o si falla la consulta.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-24
	 */
	public HashMap<String, String> obtenerRangoFechasComprobantesCompraDesdeBD(String usuario) throws Exception {
		String sqlPerfil = "select key_value "
				+ "from xxcm_user_keys "
				+ "where short_name = 'PCPERFIL' "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) "
				+ "limit 1";
		String sqlKeys = "select short_name, key_value "
				+ "from xxcm_user_keys "
				+ "where short_name in ('PCRFC', 'PCSUPPLIERID', 'PCPLAZA') "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?)";
		String sqlBase = "WITH cte_data AS( "
				+ "select "
				+ "c.id, "
				+ "c.transferencia, "
				+ "h.body, h.estatus, "
				+ "c.id_retek, "
				+ "h.id_retek_assigned_at, "
				+ "c.cr_tienda, "
				+ "p.desc_tienda, "
				+ "(p.cr_plaza::text || ' '::text) || p.desc_plaza::text AS cr_desc_plaza, "
				+ "c.tienda, "
				+ "c.cr_plaza, "
				+ "p.desc_plaza, "
				+ "c.plaza, "
				+ "c.region, "
				+ "c.zona, "
				+ "c.proveedor_rfc rfc, "
				+ "c.proveedor_id supplier, "
				+ "c.proveedor supplier_name, "
				+ "c.fuente_de_abasto, "
				+ "h.fecha_registro, "
				+ "h.updated_at "
				+ "from xxcm_cc c "
				+ "inner join xxcm_historico_correos h on h.id = c.id "
				+ "inner join xxcm_region_plaza_tienda_job_sync p on p.id_retek = c.id_retek and h.id_retek = p.id_retek ";
		String sqlRango = "), cte_rango AS( "
				+ "select "
				+ "date_trunc('month', fecha_registro)::date as mes, "
				+ "min(fecha_registro::date)::text as fecha_inicio, "
				+ "max(fecha_registro::date)::text as fecha_fin, "
				+ "count(*) as total_registros "
				+ "from cte_data "
				+ "where fecha_registro is not null "
				+ "group by date_trunc('month', fecha_registro)::date "
				+ "order by max(fecha_registro::date) desc "
				+ "limit 1 "
				+ ") "
				+ "select fecha_inicio, fecha_fin, total_registros from cte_rango";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {
			String perfil = "";
			try (PreparedStatement statementPerfil = connection.prepareStatement(sqlPerfil)) {
				statementPerfil.setString(1, usuario);
				try (ResultSet resultSetPerfil = statementPerfil.executeQuery()) {
					if (resultSetPerfil.next()) {
						perfil = resultSetPerfil.getString("key_value") == null ? "" : resultSetPerfil.getString("key_value").trim();
					}
				}
			}

			String pcrfc = null;
			String pcSupplierId = null;
			String pcPlaza = null;
			try (PreparedStatement statementKeys = connection.prepareStatement(sqlKeys)) {
				statementKeys.setString(1, usuario);
				try (ResultSet resultSetKeys = statementKeys.executeQuery()) {
					while (resultSetKeys.next()) {
						String shortName = resultSetKeys.getString("short_name");
						String keyValue = resultSetKeys.getString("key_value");
						if ("PCRFC".equalsIgnoreCase(shortName)) {
							pcrfc = keyValue;
						} else if ("PCSUPPLIERID".equalsIgnoreCase(shortName)) {
							pcSupplierId = keyValue;
						} else if ("PCPLAZA".equalsIgnoreCase(shortName)) {
							pcPlaza = keyValue;
						}
					}
				}
			}

			String perfilNormalizado = perfil.toUpperCase(Locale.ROOT);
			String whereAlcance = "";
			String filtroAplicado = "GLOBAL";
			String valorFiltro = "SIN FILTRO";
			if (!"ADMIN".equals(perfilNormalizado) && !"OXXO_OS".equals(perfilNormalizado)) {
				if ("OXXO_PLAZA".equals(perfilNormalizado) && pcPlaza != null && !pcPlaza.trim().isEmpty()) {
					whereAlcance = "where ((p.cr_plaza::text || ' '::text) || p.desc_plaza::text) = ? ";
					filtroAplicado = "PCPLAZA";
					valorFiltro = pcPlaza.trim();
				} else if (pcrfc != null && !pcrfc.trim().isEmpty()) {
					whereAlcance = "where c.proveedor_rfc = ? ";
					filtroAplicado = "PCRFC";
					valorFiltro = pcrfc.trim();
				} else if (pcSupplierId != null && !pcSupplierId.trim().isEmpty()) {
					whereAlcance = "where c.proveedor_id::text = ? ";
					filtroAplicado = "PCSUPPLIERID";
					valorFiltro = pcSupplierId.trim();
				}
			}

			String sql = sqlBase + whereAlcance + sqlRango;
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				if (!whereAlcance.isEmpty()) {
					statement.setString(1, valorFiltro);
				}
				try (ResultSet resultSet = statement.executeQuery()) {
					if (!resultSet.next()) {
						throw new Exception("No existe un rango mensual de fecha_registro con registros en Comprobantes de Compra para el usuario " + usuario);
					}

					HashMap<String, String> rangoFechas = new HashMap<>();
					rangoFechas.put("fechaInicio", resultSet.getString("fecha_inicio"));
					rangoFechas.put("fechaFin", resultSet.getString("fecha_fin"));
					rangoFechas.put("totalRegistros", resultSet.getString("total_registros"));
					rangoFechas.put("perfil", perfil);
					rangoFechas.put("filtroAplicado", filtroAplicado);
					rangoFechas.put("valorFiltro", valorFiltro);

					testCase.addBoldTextEvidenceCurrentStep("Rango obtenido desde BD para Comprobantes de Compra");
					testCase.addTextEvidenceCurrentStep("Usuario: " + usuario
							+ " | Perfil: " + perfil
							+ " | Filtro aplicado: " + filtroAplicado
							+ " | Valor filtro: " + valorFiltro
							+ " | Fecha Inicio: " + rangoFechas.get("fechaInicio")
							+ " | Fecha Fin: " + rangoFechas.get("fechaFin")
							+ " | Total registros BD: " + rangoFechas.get("totalRegistros"));
					System.out.println("[BD COMPROBANTES COMPRA] Usuario: " + usuario
							+ " | Perfil: " + perfil
							+ " | Filtro aplicado: " + filtroAplicado
							+ " | Valor filtro: " + valorFiltro
							+ " | Fecha Inicio: " + rangoFechas.get("fechaInicio")
							+ " | Fecha Fin: " + rangoFechas.get("fechaFin")
							+ " | Total registros BD: " + rangoFechas.get("totalRegistros"));

					return rangoFechas;
				}
			}
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al obtener rango de fechas de Comprobantes de Compra desde BD: " + exception.getMessage());
			throw new Exception("Error al obtener rango de fechas de Comprobantes de Compra desde BD: " + exception.getMessage());
		}
	}

	/**
	 * Obtiene un rango de fechas inteligente para Comprobantes de Compra por perfil, acumulando
	 * registros desde la fecha mas reciente hacia atras hasta alcanzar un minimo requerido.
	 * Con esto se evita fallar cuando el mes mas reciente tiene pocos datos, pero el historico
	 * inmediato si cumple el volumen necesario para la comparacion.
	 *
	 * @param usuario usuario de Portal COMETA usado para resolver perfil y llaves de alcance.
	 * @param minimoRegistrosRequerido total minimo de registros requerido para el rango.
	 * @return mapa con fechaInicio, fechaFin, totalRegistros, perfil, filtroAplicado, valorFiltro y cumpleMinimo.
	 * @throws Exception si no existe informacion para el usuario o si falla la consulta.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-11
	 */
	public HashMap<String, String> obtenerRangoFechasComprobantesCompraDesdeBDConMinimoRegistros(String usuario, int minimoRegistrosRequerido) throws Exception {
		String sqlPerfil = "select key_value "
				+ "from xxcm_user_keys "
				+ "where short_name = 'PCPERFIL' "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) "
				+ "limit 1";
		String sqlKeys = "select short_name, key_value "
				+ "from xxcm_user_keys "
				+ "where short_name in ('PCRFC', 'PCSUPPLIERID', 'PCPLAZA') "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?)";
		String sqlBase = "WITH cte_data AS( "
				+ "select "
				+ "c.id, "
				+ "c.proveedor_rfc, "
				+ "c.proveedor_id, "
				+ "(p.cr_plaza::text || ' '::text) || p.desc_plaza::text AS cr_desc_plaza, "
				+ "coalesce(c.fecha, h.fecha_registro) as fecha_base "
				+ "from xxcm_cc c "
				+ "inner join xxcm_historico_correos h on h.id = c.id "
				+ "left join xxcm_region_plaza_tienda_job_sync p on p.id_retek = c.id_retek and h.id_retek = p.id_retek ";
		String sqlRango = "), cte_diario AS( "
				+ "select fecha_base::date as fecha, count(*) as registros_dia "
				+ "from cte_data "
				+ "where fecha_base is not null "
				+ "group by fecha_base::date "
				+ "), cte_acumulado AS( "
				+ "select "
				+ "fecha, "
				+ "registros_dia, "
				+ "sum(registros_dia) over(order by fecha desc rows between unbounded preceding and current row) as total_acumulado "
				+ "from cte_diario "
				+ "), cte_inicio AS( "
				+ "select "
				+ "fecha as fecha_inicio, "
				+ "total_acumulado as total_registros "
				+ "from cte_acumulado "
				+ "where total_acumulado >= ? "
				+ "order by fecha desc "
				+ "limit 1 "
				+ "), cte_total AS( "
				+ "select coalesce(sum(registros_dia), 0) as total_disponible from cte_diario "
				+ "), cte_fin AS( "
				+ "select max(fecha) as fecha_fin from cte_diario "
				+ ") "
				+ "select "
				+ "i.fecha_inicio::text as fecha_inicio, "
				+ "f.fecha_fin::text as fecha_fin, "
				+ "i.total_registros::text as total_registros, "
				+ "t.total_disponible::text as total_disponible "
				+ "from cte_inicio i "
				+ "cross join cte_fin f "
				+ "cross join cte_total t";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {
			String perfil = "";
			try (PreparedStatement statementPerfil = connection.prepareStatement(sqlPerfil)) {
				statementPerfil.setString(1, usuario);
				try (ResultSet resultSetPerfil = statementPerfil.executeQuery()) {
					if (resultSetPerfil.next()) {
						perfil = resultSetPerfil.getString("key_value") == null ? "" : resultSetPerfil.getString("key_value").trim();
					}
				}
			}

			String pcrfc = null;
			String pcSupplierId = null;
			String pcPlaza = null;
			try (PreparedStatement statementKeys = connection.prepareStatement(sqlKeys)) {
				statementKeys.setString(1, usuario);
				try (ResultSet resultSetKeys = statementKeys.executeQuery()) {
					while (resultSetKeys.next()) {
						String shortName = resultSetKeys.getString("short_name");
						String keyValue = resultSetKeys.getString("key_value");
						if ("PCRFC".equalsIgnoreCase(shortName)) {
							pcrfc = keyValue;
						} else if ("PCSUPPLIERID".equalsIgnoreCase(shortName)) {
							pcSupplierId = keyValue;
						} else if ("PCPLAZA".equalsIgnoreCase(shortName)) {
							pcPlaza = keyValue;
						}
					}
				}
			}

			String perfilNormalizado = perfil.toUpperCase(Locale.ROOT);
			String whereAlcance = "";
			String filtroAplicado = "GLOBAL";
			String valorFiltro = "SIN FILTRO";
			if (!"ADMIN".equals(perfilNormalizado) && !"OXXO_OS".equals(perfilNormalizado)) {
				if ("OXXO_PLAZA".equals(perfilNormalizado) && pcPlaza != null && !pcPlaza.trim().isEmpty()) {
					whereAlcance = "where ((p.cr_plaza::text || ' '::text) || p.desc_plaza::text) = ? ";
					filtroAplicado = "PCPLAZA";
					valorFiltro = pcPlaza.trim();
				} else if (pcrfc != null && !pcrfc.trim().isEmpty()) {
					whereAlcance = "where c.proveedor_rfc = ? ";
					filtroAplicado = "PCRFC";
					valorFiltro = pcrfc.trim();
				} else if (pcSupplierId != null && !pcSupplierId.trim().isEmpty()) {
					whereAlcance = "where c.proveedor_id::text = ? ";
					filtroAplicado = "PCSUPPLIERID";
					valorFiltro = pcSupplierId.trim();
				}
			}

			String sql = sqlBase + whereAlcance + sqlRango;
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				int indiceParametro = 1;
				if (!whereAlcance.isEmpty()) {
					statement.setString(indiceParametro++, valorFiltro);
				}
				statement.setInt(indiceParametro, minimoRegistrosRequerido);

				try (ResultSet resultSet = statement.executeQuery()) {
					if (!resultSet.next()) {
						throw new Exception("No existe un rango acumulado con al menos " + minimoRegistrosRequerido
								+ " registros para el usuario " + usuario + ".");
					}

					HashMap<String, String> rangoFechas = new HashMap<>();
					rangoFechas.put("fechaInicio", resultSet.getString("fecha_inicio"));
					rangoFechas.put("fechaFin", resultSet.getString("fecha_fin"));
					rangoFechas.put("totalRegistros", resultSet.getString("total_registros"));
					rangoFechas.put("totalDisponible", resultSet.getString("total_disponible"));
					rangoFechas.put("perfil", perfil);
					rangoFechas.put("filtroAplicado", filtroAplicado);
					rangoFechas.put("valorFiltro", valorFiltro);
					rangoFechas.put("cumpleMinimo", "true");

					testCase.addBoldTextEvidenceCurrentStep("Rango inteligente obtenido desde BD para Comprobantes de Compra");
					testCase.addTextEvidenceCurrentStep("Usuario: " + usuario
							+ " | Perfil: " + perfil
							+ " | Filtro aplicado: " + filtroAplicado
							+ " | Valor filtro: " + valorFiltro
							+ " | Fecha Inicio: " + rangoFechas.get("fechaInicio")
							+ " | Fecha Fin: " + rangoFechas.get("fechaFin")
							+ " | Total acumulado: " + rangoFechas.get("totalRegistros")
							+ " | Total disponible historico: " + rangoFechas.get("totalDisponible")
							+ " | Minimo requerido: " + minimoRegistrosRequerido);
					System.out.println("[BD COMPROBANTES COMPRA RANGO INTELIGENTE] Usuario: " + usuario
							+ " | Perfil: " + perfil
							+ " | Filtro aplicado: " + filtroAplicado
							+ " | Valor filtro: " + valorFiltro
							+ " | Fecha Inicio: " + rangoFechas.get("fechaInicio")
							+ " | Fecha Fin: " + rangoFechas.get("fechaFin")
							+ " | Total acumulado: " + rangoFechas.get("totalRegistros")
							+ " | Total disponible historico: " + rangoFechas.get("totalDisponible")
							+ " | Minimo requerido: " + minimoRegistrosRequerido);

					return rangoFechas;
				}
			}
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al obtener rango inteligente de Comprobantes de Compra desde BD: " + exception.getMessage());
			throw new Exception("Error al obtener rango inteligente de Comprobantes de Compra desde BD: " + exception.getMessage());
		}
	}

	/**
	 * Obtiene un rango de exactamente 7 dias (hoy y los 6 dias anteriores) para Comprobantes de Compra,
	 * respetando la restriccion de la descarga masiva PDF que solo habilita fechas dentro de los ultimos 7 dias.
	 * A diferencia de {@link #obtenerRangoFechasComprobantesCompraDesdeBD(String)} (que obtiene el mes mas reciente
	 * con datos, sin limite de 7 dias), este metodo fija el rango a los ultimos 7 dias para que la descarga Excel
	 * y la descarga PDF trabajen sobre exactamente el mismo volumen de registros y la comparacion de tiempos sea valida.
	 * Ademas valida contra {@code minimoRegistrosRequerido} para que el caso no se ejecute con un volumen insuficiente.
	 *
	 * @param usuario usuario de Portal COMETA usado para resolver el perfil y las llaves de alcance.
	 * @param minimoRegistrosRequerido total minimo de registros que debe tener el rango de 7 dias para considerarse valido.
	 * @return mapa con fechaInicio, fechaFin, totalRegistros, perfil, filtroAplicado, valorFiltro y cumpleMinimo ("true"/"false").
	 * @throws Exception si falla la consulta a BD.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-28
	 */
	public HashMap<String, String> obtenerRangoFechasComprobantesCompraUltimos7DiasDesdeBD(String usuario, int minimoRegistrosRequerido) throws Exception {
		String sqlPerfil = "select key_value "
				+ "from xxcm_user_keys "
				+ "where short_name = 'PCPERFIL' "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) "
				+ "limit 1";
		String sqlKeys = "select short_name, key_value "
				+ "from xxcm_user_keys "
				+ "where short_name in ('PCRFC', 'PCSUPPLIERID', 'PCPLAZA') "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?)";
		String sqlBase = "WITH cte_data AS( "
				+ "select "
				+ "c.id, "
				+ "c.transferencia, "
				+ "h.body, h.estatus, "
				+ "c.id_retek, "
				+ "h.id_retek_assigned_at, "
				+ "c.cr_tienda, "
				+ "p.desc_tienda, "
				+ "(p.cr_plaza::text || ' '::text) || p.desc_plaza::text AS cr_desc_plaza, "
				+ "c.tienda, "
				+ "c.cr_plaza, "
				+ "p.desc_plaza, "
				+ "c.plaza, "
				+ "c.region, "
				+ "c.zona, "
				+ "c.proveedor_rfc rfc, "
				+ "c.proveedor_id supplier, "
				+ "c.proveedor supplier_name, "
				+ "c.fuente_de_abasto, "
				+ "h.fecha_registro, "
				+ "h.updated_at "
				+ "from xxcm_cc c "
				+ "inner join xxcm_historico_correos h on h.id = c.id "
				+ "inner join xxcm_region_plaza_tienda_job_sync p on p.id_retek = c.id_retek and h.id_retek = p.id_retek ";
		String sqlConteo = ") select count(*) as total_registros "
				+ "from cte_data "
				+ "where fecha_registro::date between ? and ?";

		LocalDate fechaFin = LocalDate.now();
		LocalDate fechaInicio = fechaFin.minusDays(6);

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {
			String perfil = "";
			try (PreparedStatement statementPerfil = connection.prepareStatement(sqlPerfil)) {
				statementPerfil.setString(1, usuario);
				try (ResultSet resultSetPerfil = statementPerfil.executeQuery()) {
					if (resultSetPerfil.next()) {
						perfil = resultSetPerfil.getString("key_value") == null ? "" : resultSetPerfil.getString("key_value").trim();
					}
				}
			}

			String pcrfc = null;
			String pcSupplierId = null;
			String pcPlaza = null;
			try (PreparedStatement statementKeys = connection.prepareStatement(sqlKeys)) {
				statementKeys.setString(1, usuario);
				try (ResultSet resultSetKeys = statementKeys.executeQuery()) {
					while (resultSetKeys.next()) {
						String shortName = resultSetKeys.getString("short_name");
						String keyValue = resultSetKeys.getString("key_value");
						if ("PCRFC".equalsIgnoreCase(shortName)) {
							pcrfc = keyValue;
						} else if ("PCSUPPLIERID".equalsIgnoreCase(shortName)) {
							pcSupplierId = keyValue;
						} else if ("PCPLAZA".equalsIgnoreCase(shortName)) {
							pcPlaza = keyValue;
						}
					}
				}
			}

			String perfilNormalizado = perfil.toUpperCase(Locale.ROOT);
			String whereAlcance = "";
			String filtroAplicado = "GLOBAL";
			String valorFiltro = "SIN FILTRO";
			if (!"ADMIN".equals(perfilNormalizado) && !"OXXO_OS".equals(perfilNormalizado)) {
				if ("OXXO_PLAZA".equals(perfilNormalizado) && pcPlaza != null && !pcPlaza.trim().isEmpty()) {
					whereAlcance = "where ((p.cr_plaza::text || ' '::text) || p.desc_plaza::text) = ? ";
					filtroAplicado = "PCPLAZA";
					valorFiltro = pcPlaza.trim();
				} else if (pcrfc != null && !pcrfc.trim().isEmpty()) {
					whereAlcance = "where c.proveedor_rfc = ? ";
					filtroAplicado = "PCRFC";
					valorFiltro = pcrfc.trim();
				} else if (pcSupplierId != null && !pcSupplierId.trim().isEmpty()) {
					whereAlcance = "where c.proveedor_id::text = ? ";
					filtroAplicado = "PCSUPPLIERID";
					valorFiltro = pcSupplierId.trim();
				}
			}

			String sql = sqlBase + whereAlcance + sqlConteo;
			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				int indiceParametro = 1;
				if (!whereAlcance.isEmpty()) {
					statement.setString(indiceParametro++, valorFiltro);
				}
				statement.setObject(indiceParametro++, fechaInicio);
				statement.setObject(indiceParametro, fechaFin);

				try (ResultSet resultSet = statement.executeQuery()) {
					int totalRegistros = resultSet.next() ? resultSet.getInt("total_registros") : 0;
					boolean cumpleMinimo = totalRegistros >= minimoRegistrosRequerido;

					HashMap<String, String> rangoFechas = new HashMap<>();
					rangoFechas.put("fechaInicio", fechaInicio.toString());
					rangoFechas.put("fechaFin", fechaFin.toString());
					rangoFechas.put("totalRegistros", String.valueOf(totalRegistros));
					rangoFechas.put("perfil", perfil);
					rangoFechas.put("filtroAplicado", filtroAplicado);
					rangoFechas.put("valorFiltro", valorFiltro);
					rangoFechas.put("cumpleMinimo", String.valueOf(cumpleMinimo));

					testCase.addBoldTextEvidenceCurrentStep("Rango de 7 dias obtenido desde BD para Comprobantes de Compra (Excel y PDF con mismo volumen)");
					testCase.addTextEvidenceCurrentStep("Usuario: " + usuario
							+ " | Perfil: " + perfil
							+ " | Filtro aplicado: " + filtroAplicado
							+ " | Valor filtro: " + valorFiltro
							+ " | Fecha Inicio: " + fechaInicio
							+ " | Fecha Fin: " + fechaFin
							+ " | Total registros BD: " + totalRegistros
							+ " | Minimo requerido: " + minimoRegistrosRequerido
							+ " | Cumple minimo: " + cumpleMinimo);
					System.out.println("[BD COMPROBANTES COMPRA 7 DIAS] Usuario: " + usuario
							+ " | Perfil: " + perfil
							+ " | Filtro aplicado: " + filtroAplicado
							+ " | Valor filtro: " + valorFiltro
							+ " | Fecha Inicio: " + fechaInicio
							+ " | Fecha Fin: " + fechaFin
							+ " | Total registros BD: " + totalRegistros
							+ " | Minimo requerido: " + minimoRegistrosRequerido
							+ " | Cumple minimo: " + cumpleMinimo);

					return rangoFechas;
				}
			}
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep("Error al obtener rango de 7 dias de Comprobantes de Compra desde BD: " + exception.getMessage());
			throw new Exception("Error al obtener rango de 7 dias de Comprobantes de Compra desde BD: " + exception.getMessage());
		}
	}

	/**
	 * Regresa a la pantalla Comprobantes de Compra usando el menu desplegable "Directos" del nav superior.
	 * Fuera de la pantalla inicial de Portal Cometa, "Directos" se renderiza como un boton con mat-menu-trigger
	 * (no como el enlace directo que usa {@code clicBotonCometaDirectos}), por lo que es necesario desplegar el
	 * menu y seleccionar la opcion "Comprobantes de Compra" dentro del panel. Util para volver desde Centro de Descargas
	 * u otras pantallas donde el nav ya muestra el menu contextual de Directos.
	 *
	 * @param screenshot true si se requiere agregar evidencia visual del menu desplegado y de la pantalla resultante.
	 * @return true cuando se logra desplegar el menu Directos y navegar a Comprobantes de Compra.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-28
	 */
	public boolean regresarAComprobantesCompraDesdeMenuDirectos(boolean screenshot) {
		try {
			By botonDirectosMenuNav = By.xpath("//header//button[contains(@class,'mat-mdc-menu-trigger')][.//span[contains(normalize-space(),'Directos')]]");
			By opcionComprobantesCompraMenuNav = By.xpath("//div[contains(@class,'mat-mdc-menu-panel')]//button[contains(@class,'mat-mdc-menu-item')][.//span[contains(normalize-space(),'Comprobantes de Compra')]]");
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			WebElement botonDirectos = wait.until(ExpectedConditions.elementToBeClickable(botonDirectosMenuNav));
			u.scrollToElement(botonDirectosMenuNav);
			u.highLight(botonDirectosMenuNav);
			botonDirectos.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(opcionComprobantesCompraMenuNav));
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Menu Directos desplegado con la opcion Comprobantes de Compra visible");
				testCase.addScreenShotCurrentStep(u, "Menu Directos desplegado desde nav superior");
			}
			u.unhighLight(botonDirectosMenuNav);

			WebElement opcionComprobantes = wait.until(ExpectedConditions.elementToBeClickable(opcionComprobantesCompraMenuNav));
			u.highLight(opcionComprobantesCompraMenuNav);
			opcionComprobantes.click();
			// El clic cierra y elimina del DOM el panel mat-menu-panel, por lo que el elemento resaltado
			// deja de existir de inmediato: no se debe intentar unhighLight sobre opcionComprobantesCompraMenuNav aqui.
			// El cdk-overlay-backdrop del menu Directos tarda en desvanecerse (animacion Angular Material) y,
			// si no se espera su desaparicion, intercepta el siguiente clic en Comprobantes de Compra
			// (ej. boton de calendario) con un "element click intercepted".
			By cdkOverlayBackdrop = By.cssSelector("div.cdk-overlay-backdrop");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(cdkOverlayBackdrop));
			// Navegacion Angular SPA (sin recarga completa de pagina): se valida la pantalla destino
			// esperando el boton de calendario de Comprobantes de Compra en lugar de u.waitForLoadPage().
			wait.until(ExpectedConditions.elementToBeClickable(elements.botonCalendario));

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se regreso a la pantalla Comprobantes de Compra desde el menu Directos");
				testCase.addScreenShotCurrentStep(u, "Pantalla Comprobantes de Compra tras regresar desde el menu Directos");
			}

			return true;
		} catch (Exception exception) {
			registrarError("No fue posible regresar a Comprobantes de Compra desde el menu Directos: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Captura evidencia visual del boton Excel de descarga masiva en Comprobantes de Compra antes de solicitar la generacion.
	 * Permite documentar el punto exacto previo al registro de T1 sin ejecutar el clic desde este metodo.
	 *
	 * @param screenshot true si se requiere agregar evidencia visual del boton.
	 * @return true cuando el boton Excel masivo esta visible y se captura la evidencia.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-24
	 */
	public boolean capturarBotonDescargaMasivaExcel(boolean screenshot) {
		try {
			By botonDescargaMasivaExcel = By.xpath("//app-descargas-de-comprobantes//div[contains(@class,'download-row')][.//span[contains(normalize-space(),'Descargar de manera masiva los comprobantes filtrados')]]//button[contains(concat(' ', normalize-space(@class), ' '), ' btn-excel ') and not(@disabled)]");
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(botonDescargaMasivaExcel));
			u.scrollToElement(botonDescargaMasivaExcel);
			u.highLight(botonDescargaMasivaExcel);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Boton Excel de descarga masiva disponible antes de registrar T1");
				testCase.addScreenShotCurrentStep(u, "Boton Excel descarga masiva antes de T1");
			}

			u.unhighLight(botonDescargaMasivaExcel);
			return true;
		} catch (Exception exception) {
			registrarError("No fue posible capturar el boton Excel de descarga masiva: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Captura evidencia visual del boton PDF de descarga masiva en Comprobantes de Compra antes de solicitar la generacion.
	 * Permite documentar el punto exacto previo al registro de T1 sin ejecutar el clic desde este metodo.
	 * Complementa a {@link #capturarBotonDescargaMasivaExcel(boolean)} reutilizando el mismo patron de evidencia.
	 *
	 * @param screenshot true si se requiere agregar evidencia visual del boton.
	 * @return true cuando el boton PDF masivo esta visible y se captura la evidencia.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-28
	 */
	public boolean capturarBotonDescargaMasivaPdf(boolean screenshot) {
		try {
			By botonDescargaMasivaPdf = By.xpath("//app-descargas-de-comprobantes//div[contains(@class,'download-row')][.//span[contains(normalize-space(),'Descargar de manera masiva los comprobantes filtrados')]]//button[contains(concat(' ', normalize-space(@class), ' '), ' btn-pdf ') and not(@disabled)]");
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(botonDescargaMasivaPdf));
			u.scrollToElement(botonDescargaMasivaPdf);
			u.highLight(botonDescargaMasivaPdf);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Boton PDF de descarga masiva disponible antes de registrar T1");
				testCase.addScreenShotCurrentStep(u, "Boton PDF descarga masiva antes de T1");
			}

			u.unhighLight(botonDescargaMasivaPdf);
			return true;
		} catch (Exception exception) {
			registrarError("No fue posible capturar el boton PDF de descarga masiva: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona un rango completo de fechas sin registrar evidencia intermedia por cada campo.
	 * Esta variante permite que el script controle una sola evidencia consolidada del rango seleccionado.
	 *
	 * @param fechaInicioIso fecha inicial en formato yyyy-MM-dd.
	 * @param fechaFinIso fecha final en formato yyyy-MM-dd.
	 * @return true cuando ambas fechas quedan seleccionadas y el boton Consultar queda habilitado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-24
	 */
	public boolean seleccionarRangoFechasSinEvidenciaIntermedia(String fechaInicioIso, String fechaFinIso) {
		try {
			LocalDate fechaInicio = LocalDate.parse(fechaInicioIso, DateTimeFormatter.ISO_LOCAL_DATE);
			LocalDate fechaFin = LocalDate.parse(fechaFinIso, DateTimeFormatter.ISO_LOCAL_DATE);
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			WebElement botonCalendarioElemento = wait.until(ExpectedConditions.elementToBeClickable(elements.botonCalendario));
			try {
				botonCalendarioElemento.click();
			} catch (Exception clickInterceptado) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", botonCalendarioElemento);
			}
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendario));
			posicionarMes(wait, fechaInicio);
			seleccionarDia(wait, fechaInicio.getDayOfMonth());

			String fechaInicioActual = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.inputFechaInicio))
					.getAttribute("value");
			boolean fechaInicioSeleccionada = !driver.findElements(elements.calendario).isEmpty()
					&& coincideFechaUi(fechaInicio, fechaInicioActual);

			posicionarMes(wait, fechaFin);
			seleccionarDia(wait, fechaFin.getDayOfMonth());
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendario));

			String fechaFinActual = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.inputFechaFin))
					.getAttribute("value");
			boolean fechaFinSeleccionada = driver.findElements(elements.calendario).isEmpty()
					&& coincideFechaUi(fechaFin, fechaFinActual)
					&& wait.until(ExpectedConditions.elementToBeClickable(elements.botonConsultarHabilitado)).isEnabled();

			return fechaInicioSeleccionada && fechaFinSeleccionada;
		} catch (Exception exception) {
			registrarError("No fue posible seleccionar el rango de fechas sin evidencia intermedia: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que una columna de Excel individual se ubique inmediatamente despues de la columna base indicada.
	 *
	 * @param columnaBase nombre de la columna de referencia.
	 * @param columnaObjetivo nombre de la columna que debe quedar contigua a la base.
	 * @param screenshot true para adjuntar captura de evidencia del paso.
	 * @return true cuando la columna objetivo se encuentra justo despues de la columna base.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarColumnaInmediataDespuesEnExcelIndividual(String columnaBase, String columnaObjetivo, boolean screenshot) {
		try {
			File archivo = obtenerUltimoExcelDescargado();
			if (archivo == null || !archivo.exists()) {
				registrarResultado(false,
						"Se localizo archivo Excel individual para validar orden de columnas",
						"No se encontro archivo Excel individual en Descargas para validar orden de columnas");
				return false;
			}

			try (FileInputStream fis = new FileInputStream(archivo);
					Workbook workbook = WorkbookFactory.create(fis)) {
				Sheet sheet = workbook.getSheetAt(0);
				Row encabezados = sheet.getRow(0);
				if (encabezados == null) {
					registrarResultado(false,
							"Se encontro fila de encabezados en Excel individual",
							"El Excel individual no contiene fila de encabezados para validar orden de columnas");
					return false;
				}

				int indiceBase = buscarIndiceColumnaExcel(encabezados, columnaBase);
				int indiceObjetivo = buscarIndiceColumnaExcel(encabezados, columnaObjetivo);
				boolean resultadoPaso = indiceBase >= 0 && indiceObjetivo == (indiceBase + 1);

				registrarResultado(resultadoPaso,
						"Orden de columnas validado: " + columnaObjetivo + " se muestra despues de " + columnaBase,
						"Orden de columnas incorrecto. Base: " + columnaBase + "(" + indiceBase + ")"
								+ " | Objetivo: " + columnaObjetivo + "(" + indiceObjetivo + ")");
				testCase.addTextEvidenceCurrentStep("Detalle orden de columnas: Base " + columnaBase + "(" + indiceBase
						+ ") | Objetivo " + columnaObjetivo + "(" + indiceObjetivo + ")");
				if (screenshot) {
					testCase.addScreenShotCurrentStep(u, "Orden_" + normalizarEtiquetaExcel(columnaObjetivo));
				}

				return resultadoPaso;
			}
		} catch (Exception exception) {
			registrarError("No fue posible validar el orden de columnas en Excel individual: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que una columna de Excel individual tenga al menos un dato no vacio.
	 *
	 * @param nombreColumna nombre exacto de la columna a validar.
	 * @param screenshot true para adjuntar captura de evidencia del paso.
	 * @return true cuando existe por lo menos un valor no vacio para la columna.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarDatoNoVacioColumnaExcelIndividual(String nombreColumna, boolean screenshot) {
		try {
			File archivo = obtenerUltimoExcelDescargado();
			if (archivo == null || !archivo.exists()) {
				registrarResultado(false,
						"Se localizo archivo Excel individual para validar datos de la columna " + nombreColumna,
						"No se encontro archivo Excel individual en Descargas para validar la columna " + nombreColumna);
				return false;
			}

			try (FileInputStream fis = new FileInputStream(archivo);
					Workbook workbook = WorkbookFactory.create(fis)) {
				Sheet sheet = workbook.getSheetAt(0);
				Row encabezados = sheet.getRow(0);
				if (encabezados == null) {
					registrarResultado(false,
							"Se encontro fila de encabezados para validar columna " + nombreColumna,
							"El Excel individual no contiene encabezados para validar la columna " + nombreColumna);
					return false;
				}

				int indiceColumna = buscarIndiceColumnaExcel(encabezados, nombreColumna);
				if (indiceColumna < 0) {
					registrarResultado(false,
							"La columna " + nombreColumna + " existe en encabezados",
							"No se encontro la columna " + nombreColumna + " en el Excel individual");
					return false;
				}

				DataFormatter formatter = new DataFormatter(Locale.ROOT);
				String valorReferencia = "";
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}
					Cell cell = row.getCell(indiceColumna);
					String texto = cell == null ? "" : formatter.formatCellValue(cell).trim();
					if (!texto.isEmpty()) {
						valorReferencia = texto;
						break;
					}
				}

				boolean resultadoPaso = !valorReferencia.isEmpty();
				registrarResultado(resultadoPaso,
						"La columna " + nombreColumna + " contiene datos en Excel individual",
						"La columna " + nombreColumna + " no contiene datos validos en Excel individual");
				testCase.addTextEvidenceCurrentStep("Valor de referencia en " + nombreColumna + ": " + valorReferencia);
				if (screenshot) {
					testCase.addScreenShotCurrentStep(u, "Dato_" + normalizarEtiquetaExcel(nombreColumna));
				}

				return resultadoPaso;
			}
		} catch (Exception exception) {
			registrarError("No fue posible validar datos de la columna " + nombreColumna + " en Excel individual: "
					+ exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que las columnas Observaciones Tienda y Observaciones Proveedor tengan datos en Excel individual.
	 *
	 * @param screenshot true para adjuntar captura de evidencia del paso.
	 * @return true cuando ambas columnas tienen valores no vacios.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarObservacionesTiendaYProveedorExcelIndividual(boolean screenshot) {
		try {
			File archivo = obtenerUltimoExcelDescargado();
			if (archivo == null || !archivo.exists()) {
				registrarResultado(false,
						"Se localizo archivo Excel individual para validar observaciones",
						"No se encontro archivo Excel individual en Descargas para validar observaciones");
				return false;
			}

			try (FileInputStream fis = new FileInputStream(archivo);
					Workbook workbook = WorkbookFactory.create(fis)) {
				Sheet sheet = workbook.getSheetAt(0);
				Row encabezados = sheet.getRow(0);
				if (encabezados == null) {
					registrarResultado(false,
							"Se encontro fila de encabezados para validar observaciones",
							"El Excel individual no contiene encabezados para validar observaciones");
					return false;
				}

				int colTienda = buscarIndiceColumnaExcel(encabezados, "Observaciones Tienda");
				int colProveedor = buscarIndiceColumnaExcel(encabezados, "Observaciones Proveedor");
				if (colTienda < 0 || colProveedor < 0) {
					registrarResultado(false,
							"Se encontraron las columnas de observaciones en el Excel individual",
							"No se encontraron columnas de observaciones (Tienda/Proveedor) en el Excel individual");
					return false;
				}

				DataFormatter formatter = new DataFormatter(Locale.ROOT);
				String valorTienda = "";
				String valorProveedor = "";

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}
					if (valorTienda.isEmpty()) {
						Cell cTienda = row.getCell(colTienda);
						valorTienda = cTienda == null ? "" : formatter.formatCellValue(cTienda).trim();
					}
					if (valorProveedor.isEmpty()) {
						Cell cProveedor = row.getCell(colProveedor);
						valorProveedor = cProveedor == null ? "" : formatter.formatCellValue(cProveedor).trim();
					}
					if (!valorTienda.isEmpty() && !valorProveedor.isEmpty()) {
						break;
					}
				}

				boolean resultadoPaso = !valorTienda.isEmpty() && !valorProveedor.isEmpty();
				registrarResultado(resultadoPaso,
						"Las columnas Observaciones Tienda y Observaciones Proveedor contienen datos en Excel individual",
						"Falta informacion en observaciones. Tienda: '" + valorTienda + "' | Proveedor: '" + valorProveedor + "'");
				testCase.addTextEvidenceCurrentStep(
						"Observaciones en Excel -> Tienda: '" + valorTienda + "' | Proveedor: '" + valorProveedor + "'.");
				if (screenshot) {
					testCase.addScreenShotCurrentStep(u, "Observaciones_Excel_Individual");
				}

				return resultadoPaso;
			}
		} catch (Exception exception) {
			registrarError("No fue posible validar observaciones en Excel individual: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida el formato de encabezados de las columnas nuevas en Excel individual.
	 *
	 * @param screenshot true para adjuntar captura de evidencia del paso.
	 * @return true cuando todos los encabezados nuevos usan Arial 14 en negritas.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarFormatoEncabezadosNuevosExcelIndividual(boolean screenshot) {
		try {
			File archivo = obtenerUltimoExcelDescargado();
			if (archivo == null || !archivo.exists()) {
				registrarResultado(false,
						"Se localizo archivo Excel individual para validar formato de encabezados",
						"No se encontro archivo Excel individual en Descargas para validar formato de encabezados");
				return false;
			}

			String[] columnas = { "OC EDI", "Observaciones Tienda", "Observaciones Proveedor", "Codigo de activacion",
					"ID Dispositivo" };

			try (FileInputStream fis = new FileInputStream(archivo);
					Workbook workbook = WorkbookFactory.create(fis)) {
				Sheet sheet = workbook.getSheetAt(0);
				Row encabezados = sheet.getRow(0);
				if (encabezados == null) {
					registrarResultado(false,
							"Se encontro fila de encabezados para validar formato",
							"El Excel individual no contiene encabezados para validar formato de columnas nuevas");
					return false;
				}

				boolean resultadoPaso = true;
				StringBuilder detalle = new StringBuilder();

				for (String columna : columnas) {
					int indice = buscarIndiceColumnaExcel(encabezados, columna);
					if (indice < 0) {
						resultadoPaso = false;
						detalle.append("No se encontro encabezado ").append(columna).append(". ");
						continue;
					}

					Cell cell = encabezados.getCell(indice);
					if (cell == null) {
						resultadoPaso = false;
						detalle.append("Celda de encabezado vacia para ").append(columna).append(". ");
						continue;
					}

					Font font = workbook.getFontAt(cell.getCellStyle().getFontIndex());
					boolean fuenteOk = "Arial".equalsIgnoreCase(font.getFontName());
					boolean tamanioOk = font.getFontHeightInPoints() == 14;
					boolean negritaOk = font.getBold();

					if (!(fuenteOk && tamanioOk && negritaOk)) {
						resultadoPaso = false;
						detalle.append("Formato no valido en encabezado ").append(columna)
								.append(" (Fuente: ").append(font.getFontName())
								.append(", Tam: ").append(font.getFontHeightInPoints())
								.append(", Negrita: ").append(font.getBold()).append("). ");
					}
				}

				String detalleResultado = detalle.length() == 0 ? "Todos los encabezados cumplen formato esperado."
						: detalle.toString();
				registrarResultado(resultadoPaso,
						"Los encabezados nuevos del Excel individual conservan el formato esperado (Arial 14 negritas)",
						"Los encabezados nuevos del Excel individual no cumplen formato esperado: " + detalleResultado);
				testCase.addTextEvidenceCurrentStep("Detalle de formato en encabezados: " + detalleResultado);
				if (screenshot) {
					testCase.addScreenShotCurrentStep(u, "Formato_Encabezados_Nuevos");
				}

				return resultadoPaso;
			}
		} catch (Exception exception) {
			registrarError("No fue posible validar formato de encabezados nuevos en Excel individual: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida el formato del contenido para las columnas nuevas en Excel individual.
	 *
	 * @param screenshot true para adjuntar captura de evidencia del paso.
	 * @return true cuando el contenido de las columnas nuevas usa fuente Arial en tamano 12.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	public boolean validarFormatoContenidoColumnasNuevasExcelIndividual(boolean screenshot) {
		try {
			File archivo = obtenerUltimoExcelDescargado();
			if (archivo == null || !archivo.exists()) {
				registrarResultado(false,
						"Se localizo archivo Excel individual para validar formato de contenido",
						"No se encontro archivo Excel individual en Descargas para validar formato de contenido");
				return false;
			}

			String[] columnas = { "OC EDI", "Observaciones Tienda", "Observaciones Proveedor", "Codigo de activacion",
					"ID Dispositivo" };

			try (FileInputStream fis = new FileInputStream(archivo);
					Workbook workbook = WorkbookFactory.create(fis)) {
				Sheet sheet = workbook.getSheetAt(0);
				Row encabezados = sheet.getRow(0);
				if (encabezados == null) {
					registrarResultado(false,
							"Se encontro fila de encabezados para validar formato de contenido",
							"El Excel individual no contiene encabezados para validar formato de contenido");
					return false;
				}

				DataFormatter formatter = new DataFormatter(Locale.ROOT);
				boolean resultadoPaso = true;
				StringBuilder detalle = new StringBuilder();

				for (String columna : columnas) {
					int indice = buscarIndiceColumnaExcel(encabezados, columna);
					if (indice < 0) {
						resultadoPaso = false;
						detalle.append("No se encontro columna ").append(columna).append(". ");
						continue;
					}

					Cell celdaDato = null;
					for (int i = 1; i <= sheet.getLastRowNum(); i++) {
						Row row = sheet.getRow(i);
						if (row == null) {
							continue;
						}
						Cell cell = row.getCell(indice);
						String valor = cell == null ? "" : formatter.formatCellValue(cell).trim();
						if (!valor.isEmpty()) {
							celdaDato = cell;
							break;
						}
					}

					if (celdaDato == null) {
						resultadoPaso = false;
						detalle.append("Sin datos para validar formato en ").append(columna).append(". ");
						continue;
					}

					Font font = workbook.getFontAt(celdaDato.getCellStyle().getFontIndex());
					boolean fuenteOk = "Arial".equalsIgnoreCase(font.getFontName());
					boolean tamanioOk = font.getFontHeightInPoints() == 12;
					if (!(fuenteOk && tamanioOk)) {
						resultadoPaso = false;
						detalle.append("Formato no valido en contenido de ").append(columna)
								.append(" (Fuente: ").append(font.getFontName())
								.append(", Tam: ").append(font.getFontHeightInPoints()).append("). ");
					}
				}

				String detalleResultado = detalle.length() == 0 ? "Todas las columnas cumplen formato esperado."
						: detalle.toString();
				registrarResultado(resultadoPaso,
						"El contenido de columnas nuevas en Excel individual conserva formato esperado (Arial 12)",
						"El contenido de columnas nuevas en Excel individual no cumple formato esperado: " + detalleResultado);
				testCase.addTextEvidenceCurrentStep("Detalle de formato en contenido: " + detalleResultado);
				if (screenshot) {
					testCase.addScreenShotCurrentStep(u, "Formato_Contenido_Columnas_Nuevas");
				}

				return resultadoPaso;
			}
		} catch (Exception exception) {
			registrarError(
					"No fue posible validar formato de contenido de columnas nuevas en Excel individual: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene el archivo mas reciente del directorio de descargas para validar evidencia en Excel.
	 *
	 * @return archivo Excel mas reciente o null cuando no existe archivo descargado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	private File obtenerUltimoExcelDescargado() {
		return PortalCometaMVP4.getLastModified(System.getProperty("user.home") + "\\Downloads");
	}

	/**
	 * Lee el valor de Observaciones Proveedor desde la vista preliminar abierta de
	 * Comprobantes de Compra.
	 *
	 * @param screenshot true para adjuntar evidencia visual del valor leido.
	 * @return valor encontrado en la vista preliminar o cadena vacia si no fue posible leerlo.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-11
	 */
	public String leerObservacionesProveedorDeVistaPreliminar(boolean screenshot) {
		try {
			WebDriver driver = u.getDriver();
			By modalVisible = By.xpath("(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container][.//span[contains(normalize-space(.),'Descarga Comprobante en Formato')]])[1]");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(modalVisible));

			By contenedorScrollModal = By.xpath("(//app-detalle//div[contains(@class,'modal-scroll-content')] | //*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//*[contains(@class,'modal-scroll-content')])[1]");
			By tituloObservacionesProveedor = By.xpath("(//app-detalle//mat-card-title[contains(@class,'evidencia-header') and contains(normalize-space(.),'OBSERVACIONES DEL PROVEEDOR')] | //*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//mat-card-title[contains(@class,'evidencia-header') and contains(normalize-space(.),'OBSERVACIONES DEL PROVEEDOR')])[1]");
			By valorObservacionesProveedor = By.xpath("(//app-detalle//mat-card[.//mat-card-title[contains(@class,'evidencia-header') and contains(normalize-space(.),'OBSERVACIONES DEL PROVEEDOR')]]//div[contains(@class,'inputs')]//p | //*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//mat-card[.//mat-card-title[contains(@class,'evidencia-header') and contains(normalize-space(.),'OBSERVACIONES DEL PROVEEDOR')]]//div[contains(@class,'inputs')]//p)[1]");

			WebElement contenedor = wait.until(ExpectedConditions.visibilityOfElementLocated(contenedorScrollModal));
			WebElement titulo = wait.until(ExpectedConditions.presenceOfElementLocated(tituloObservacionesProveedor));

			// El bloque de observaciones esta al final del modal; se desplaza el contenedor interno para volver visible el campo.
			((JavascriptExecutor) driver).executeScript(
					"arguments[0].scrollTop = 0; arguments[1].scrollIntoView({block:'center', inline:'nearest'});",
					contenedor, titulo);

			WebElement valorElemento = wait.until(ExpectedConditions.visibilityOfElementLocated(valorObservacionesProveedor));
			String valor = valorElemento.getText() == null ? "" : valorElemento.getText().trim();

			if (valor.isEmpty()) {
				valor = leerCampoDeModalComprobante("Observaciones Proveedor");
				if (valor.isEmpty()) {
					valor = leerCampoDeModalComprobante("Observaciones del Proveedor");
				}
			}

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Valor leido en vista preliminar para Observaciones Proveedor: [" + valor + "]");
				testCase.addScreenShotCurrentStep(u, "Vista_Preliminar_Observaciones_Proveedor");
			}

			if (valor.isEmpty()) {
				registrarResultado(false,
						"Se leyo Observaciones Proveedor desde vista preliminar",
						"No fue posible leer Observaciones Proveedor desde la vista preliminar del comprobante");
			} else {
				registrarResultado(true,
						"Se leyo Observaciones Proveedor desde vista preliminar: " + valor,
						"No fue posible leer Observaciones Proveedor desde vista preliminar");
			}

			return valor;
		} catch (Exception exception) {
			registrarError("No fue posible leer Observaciones Proveedor en vista preliminar: " + exception.getMessage());
			return "";
		}
	}

	/**
	 * Valida que la columna OC EDI tenga el mismo valor que la columna Orden de Compra
	 * en el Excel individual descargado.
	 *
	 * @param screenshot true para adjuntar evidencia visual del paso.
	 * @return true cuando existe al menos una fila valida y en todas las filas con Orden de Compra
	 *         informado, el valor de OC EDI coincide exactamente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-11
	 */
	public boolean validarOcEdiCoincideConOrdenCompraExcelIndividual(boolean screenshot) {
		try {
			File archivo = obtenerUltimoExcelDescargado();
			if (archivo == null || !archivo.exists()) {
				registrarResultado(false,
						"Se localizo archivo Excel individual para comparar OC EDI vs Orden de Compra",
						"No se encontro archivo Excel individual para comparar OC EDI vs Orden de Compra");
				return false;
			}

			try (FileInputStream fis = new FileInputStream(archivo);
					Workbook workbook = WorkbookFactory.create(fis)) {
				Sheet sheet = workbook.getSheetAt(0);
				Row encabezados = sheet.getRow(0);
				if (encabezados == null) {
					registrarResultado(false,
							"Se encontro fila de encabezados para comparar OC EDI vs Orden de Compra",
							"El Excel individual no contiene encabezados para comparar OC EDI vs Orden de Compra");
					return false;
				}

				int indiceOcEdi = buscarIndiceColumnaExcel(encabezados, "OC EDI");
				int indiceOrdenCompra = buscarIndiceColumnaExcel(encabezados, "Orden de Compra");
				if (indiceOcEdi < 0 || indiceOrdenCompra < 0) {
					registrarResultado(false,
							"Existen columnas OC EDI y Orden de Compra en Excel individual",
							"No se localizaron las columnas requeridas para comparar OC EDI vs Orden de Compra");
					return false;
				}

				DataFormatter formatter = new DataFormatter(Locale.ROOT);
				boolean hayFilaComparable = false;
				boolean todoCoincide = true;
				String primerDetalle = "";

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}

					String valorOrden = "";
					Cell celdaOrden = row.getCell(indiceOrdenCompra);
					if (celdaOrden != null) {
						valorOrden = formatter.formatCellValue(celdaOrden).trim();
					}

					if (valorOrden.isEmpty()) {
						continue;
					}

					hayFilaComparable = true;
					String valorOcEdi = "";
					Cell celdaOcEdi = row.getCell(indiceOcEdi);
					if (celdaOcEdi != null) {
						valorOcEdi = formatter.formatCellValue(celdaOcEdi).trim();
					}

					boolean coincide = valorOrden.equals(valorOcEdi);
					if (!coincide) {
						todoCoincide = false;
						if (primerDetalle.isEmpty()) {
							primerDetalle = "Fila " + (i + 1) + " | Orden de Compra: '" + valorOrden
									+ "' | OC EDI: '" + valorOcEdi + "'";
						}
					}
				}

				boolean resultadoPaso = hayFilaComparable && todoCoincide;
				String detalle = resultadoPaso ? "OC EDI coincide con Orden de Compra en todas las filas comparables"
						: (hayFilaComparable
								? "Se detecto diferencia entre OC EDI y Orden de Compra. " + primerDetalle
								: "No se encontraron filas con valor en Orden de Compra para comparar");

				registrarResultado(resultadoPaso,
						"Comparacion OC EDI vs Orden de Compra validada en Excel individual",
						detalle);
				testCase.addTextEvidenceCurrentStep("Detalle comparacion OC EDI vs Orden de Compra: " + detalle);
				if (screenshot) {
					testCase.addScreenShotCurrentStep(u, "Comparacion_OC_EDI_vs_Orden_Compra");
				}

				return resultadoPaso;
			}
		} catch (Exception exception) {
			registrarError("No fue posible comparar OC EDI vs Orden de Compra en Excel individual: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Compara el valor esperado de una columna contra el valor informado en el Excel individual.
	 * La comparacion se hace sobre la primera fila con dato no vacio en la columna indicada.
	 *
	 * @param nombreColumna nombre de la columna a validar.
	 * @param valorEsperado valor esperado proveniente de vista preliminar o criterio funcional.
	 * @param screenshot true para adjuntar evidencia visual del paso.
	 * @return true cuando el valor de Excel coincide con el valor esperado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-11
	 */
	public boolean validarValorColumnaExcelIndividualContraEsperado(String nombreColumna, String valorEsperado,
			boolean screenshot) {
		try {
			File archivo = obtenerUltimoExcelDescargado();
			if (archivo == null || !archivo.exists()) {
				registrarResultado(false,
						"Se localizo archivo Excel individual para comparar valor esperado de " + nombreColumna,
						"No se encontro archivo Excel individual para comparar valor esperado de " + nombreColumna);
				return false;
			}

			try (FileInputStream fis = new FileInputStream(archivo);
					Workbook workbook = WorkbookFactory.create(fis)) {
				Sheet sheet = workbook.getSheetAt(0);
				Row encabezados = sheet.getRow(0);
				if (encabezados == null) {
					registrarResultado(false,
							"Se encontro fila de encabezados para comparar " + nombreColumna,
							"El Excel individual no contiene encabezados para comparar " + nombreColumna);
					return false;
				}

				int indiceColumna = buscarIndiceColumnaExcel(encabezados, nombreColumna);
				if (indiceColumna < 0) {
					registrarResultado(false,
							"La columna " + nombreColumna + " existe en encabezados de Excel individual",
							"No se encontro la columna " + nombreColumna + " en el Excel individual");
					return false;
				}

				DataFormatter formatter = new DataFormatter(Locale.ROOT);
				String valorExcel = "";
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}
					Cell cell = row.getCell(indiceColumna);
					String texto = cell == null ? "" : formatter.formatCellValue(cell).trim();
					if (!texto.isEmpty()) {
						valorExcel = texto;
						break;
					}
				}

				String esperadoNormalizado = valorEsperado == null ? "" : valorEsperado.trim();
				boolean resultadoPaso = !valorExcel.isEmpty() && !esperadoNormalizado.isEmpty()
						&& valorExcel.equalsIgnoreCase(esperadoNormalizado);
				String detalle = "Esperado: '" + esperadoNormalizado + "' | Excel: '" + valorExcel + "'";

				registrarResultado(resultadoPaso,
						"La columna " + nombreColumna + " coincide con el valor esperado de vista preliminar",
						"La columna " + nombreColumna + " no coincide con el valor esperado. " + detalle);
				testCase.addTextEvidenceCurrentStep("Detalle comparacion de " + nombreColumna + ": " + detalle);
				if (screenshot) {
					testCase.addScreenShotCurrentStep(u, "Comparacion_" + normalizarEtiquetaExcel(nombreColumna));
				}

				return resultadoPaso;
			}
		} catch (Exception exception) {
			registrarError("No fue posible comparar valor esperado de " + nombreColumna
					+ " en Excel individual: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Lee el valor de un campo dentro de la vista preliminar de Comprobantes de Compra
	 * utilizando el texto de su etiqueta.
	 */
	private String leerCampoDeModalComprobante(String etiquetaCampo) {
		WebDriver driver = u.getDriver();
		String[] xpaths = {
				"(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//*[normalize-space(text())='"
						+ etiquetaCampo + "']/following::p[1])[1]",
				"(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//*[contains(normalize-space(text()),'"
						+ etiquetaCampo + "')]/following::p[1])[1]",
				"(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//*[normalize-space(text())='"
						+ etiquetaCampo + "']/following::span[1])[1]",
				"(//*[contains(@class,'mat-mdc-dialog-container') or self::mat-dialog-container]//*[contains(normalize-space(text()),'"
						+ etiquetaCampo + "')]/following-sibling::*[1])[1]" };

		for (String xpath : xpaths) {
			try {
				List<WebElement> elementos = driver.findElements(By.xpath(xpath));
				if (elementos.isEmpty()) {
					continue;
				}

				String texto = elementos.get(0).getText();
				if (texto == null) {
					continue;
				}

				texto = texto.trim();
				if (!texto.isEmpty() && !texto.equalsIgnoreCase(etiquetaCampo)) {
					return texto;
				}
			} catch (Exception ignored) {
				// Continuar con el siguiente patron
			}
		}

		return "";
	}

	/**
	 * Busca el indice de una columna en la fila de encabezados de Excel.
	 *
	 * @param encabezados fila de encabezados del archivo Excel.
	 * @param nombreColumna nombre de columna a localizar.
	 * @return indice de la columna o -1 si no existe.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	private int buscarIndiceColumnaExcel(Row encabezados, String nombreColumna) {
		DataFormatter formatter = new DataFormatter(Locale.ROOT);
		for (int i = 0; i < encabezados.getLastCellNum(); i++) {
			Cell cell = encabezados.getCell(i);
			String nombre = cell == null ? "" : formatter.formatCellValue(cell).trim();
			if (nombreColumna.equalsIgnoreCase(nombre)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Normaliza etiquetas para nombres de evidencia sin espacios ni acentos.
	 *
	 * @param texto texto original.
	 * @return texto normalizado para usar en nombre de captura.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-05
	 */
	private String normalizarEtiquetaExcel(String texto) {
		return texto.replace(" ", "_").replace("ó", "o").replace("í", "i").replace("á", "a")
				.replace("é", "e").replace("ú", "u").replace("Ó", "O").replace("Í", "I").replace("Á", "A")
				.replace("É", "E").replace("Ú", "U");
	}

	/**
	 * Valida que todas las celdas no vacias de la columna Fecha del Resumen Excel de Comprobantes
	 * de Compra cumplan el formato dd-MM-yyyy sin componente de hora. No compara contra el grid;
	 * la validacion es exclusivamente sobre el contenido del archivo descargado (CA02).
	 *
	 * @param screenshot true si se requiere adjuntar captura de evidencia del paso.
	 * @return true cuando todas las celdas de la columna Fecha contienen valores con formato dd-MM-yyyy.
	 *
	 * @author Mauricio Gurrola
	 * @date Agosto 2026
	 */
	public boolean validarFormatoFechaResumenExcel(boolean screenshot) {
		try {
			File archivo = obtenerUltimoExcelDescargado();
			if (archivo == null || !archivo.exists()) {
				String motivoFallo = "No se encontro archivo Resumen Excel en Descargas para validar formato de Fecha";
				System.out.println("[RESULTADO PASO] FAILED - " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			try (FileInputStream fis = new FileInputStream(archivo);
					Workbook workbook = WorkbookFactory.create(fis)) {
				Sheet sheet = workbook.getSheetAt(0);
				Row encabezados = sheet.getRow(0);
				if (encabezados == null) {
					String motivoFallo = "El Resumen Excel no contiene fila de encabezados. Archivo: " + archivo.getName();
					System.out.println("[RESULTADO PASO] FAILED - " + motivoFallo);
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
					return false;
				}

				int colFecha = buscarIndiceColumnaExcel(encabezados, "Fecha");
				if (colFecha < 0) {
					String motivoFallo = "Columna 'Fecha' no encontrada en el Resumen Excel. Archivo: " + archivo.getName();
					System.out.println("[RESULTADO PASO] FAILED - " + motivoFallo);
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
					return false;
				}

				DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				DataFormatter formatter = new DataFormatter(Locale.ROOT);
				int totalRevisadas = 0;
				int totalInvalidas = 0;
				StringBuilder invalidas = new StringBuilder();

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}
					Cell cell = row.getCell(colFecha);
					String valor = cell == null ? "" : formatter.formatCellValue(cell).trim();
					if (valor.isEmpty()) {
						continue;
					}
					totalRevisadas++;
					try {
						LocalDate.parse(valor, fmt);
					} catch (Exception parseEx) {
						totalInvalidas++;
						if (invalidas.length() < 300) {
							invalidas.append("[Fila ").append(i + 1).append(": '").append(valor).append("'] ");
						}
					}
				}

				boolean resultadoPaso = totalRevisadas > 0 && totalInvalidas == 0;
				String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
				String motivoFallo = resultadoPaso ? ""
						: totalRevisadas == 0 ? "La columna Fecha no contiene valores para revisar"
								: "Celdas con formato invalido (dd-MM-yyyy): " + totalInvalidas + " de " + totalRevisadas
										+ ". Ejemplos: " + invalidas;
				System.out.println("[RESULTADO PASO] " + estadoPaso
						+ " - Formato dd-MM-yyyy en columna Fecha del Resumen Excel"
						+ " | Revisadas: " + totalRevisadas + " | Invalidas: " + totalInvalidas
						+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso
						+ " - Columna Fecha en Resumen Excel | Filas revisadas: " + totalRevisadas
						+ " | Formato invalido: " + totalInvalidas
						+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
				testCase.addTextEvidenceCurrentStep(
						"CA02 - Todas las celdas de la columna Fecha del Resumen Excel cumplen formato dd-MM-yyyy sin componente de hora.");
				// La imagen del archivo es la misma del paso anterior (Resumen_Excel_Comprobantes_Descargado)
				testCase.addTextEvidenceCurrentStep(
						"Imagen del archivo: ver captura Resumen_Excel_Comprobantes_Descargado del paso anterior.");

				return resultadoPaso;
			}
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Error al validar formato Fecha en Resumen Excel: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Verifica que el archivo Resumen Excel de Comprobantes de Compra descargado sea legible y contenga
	 * al menos una fila de datos y la columna obligatoria Fecha. No valida el contenido de las celdas;
	 * la comparacion de fechas contra el grid queda a cargo de {@code ValidarFechasExcelVsGridResumen}.
	 *
	 * @param screenshot true si se requiere adjuntar captura de evidencia del paso.
	 * @return true cuando el archivo existe, es legible y contiene la columna Fecha con al menos un dato.
	 *
	 * @author Mauricio Gurrola
	 * @date Agosto 2026
	 */
	public boolean validarResumenExcelDescargado(boolean screenshot) {
		try {
			File archivo = obtenerUltimoExcelDescargado();
			if (archivo == null || !archivo.exists()) {
				String motivoFallo = "No se encontro archivo Resumen Excel en Descargas. Esperado: Resumen_OC_*.xlsx | Actual: ninguno";
				System.out.println("[RESULTADO PASO] FAILED - Resumen Excel no localizado | Motivo: " + motivoFallo);
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
				return false;
			}

			try (FileInputStream fis = new FileInputStream(archivo);
					Workbook workbook = WorkbookFactory.create(fis)) {
				Sheet sheet = workbook.getSheetAt(0);
				Row encabezados = sheet.getRow(0);

				if (encabezados == null) {
					String motivoFallo = "El archivo no contiene fila de encabezados. Archivo: " + archivo.getName();
					System.out.println("[RESULTADO PASO] FAILED - Resumen Excel sin encabezados | Motivo: " + motivoFallo);
					testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + motivoFallo);
					return false;
				}

				int colFecha = buscarIndiceColumnaExcel(encabezados, "Fecha");
				boolean tieneFecha = colFecha >= 0;
				boolean tieneFilas = sheet.getLastRowNum() >= 1;
				boolean resultadoPaso = tieneFecha && tieneFilas;

				String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
				String motivoFallo = resultadoPaso ? ""
						: (!tieneFecha ? "Columna 'Fecha' no encontrada en encabezados." : "")
								+ (!tieneFilas ? " El archivo no contiene filas de datos." : "");
				System.out.println("[RESULTADO PASO] " + estadoPaso + " - Resumen Excel de Comprobantes verificado. Archivo: "
						+ archivo.getName() + " | Filas: " + sheet.getLastRowNum()
						+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
				testCase.addBoldTextEvidenceCurrentStep("Resultado paso: " + estadoPaso
						+ " - Archivo: " + archivo.getName()
						+ " | Filas de datos: " + sheet.getLastRowNum()
						+ (resultadoPaso ? "" : " | Motivo: " + motivoFallo));
				testCase.addTextEvidenceCurrentStep("El Resumen Excel de Comprobantes de Compra quedo descargado, es legible y contiene la columna Fecha.");

				if (screenshot) {
					testCase.addScreenShotCurrentStep(u, "Resumen_Excel_Comprobantes_Descargado");
				}

				return resultadoPaso;
			}
		} catch (Exception exception) {
			System.out.println("[RESULTADO PASO] FAILED - Error al procesar Resumen Excel: " + exception.getMessage());
			testCase.addBoldTextEvidenceCurrentStep("Resultado paso: FAILED | Motivo: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene un rango de fechas para Comprobantes de Compra garantizando datos utiles para
	 * columnas nuevas del Excel individual: Order No (OC EDI) y Codigo de activacion/ID Dispositivo.
	 * El rango se calcula de la fecha mas reciente hacia atras hasta cumplir el minimo de registros,
	 * aplicando el mismo alcance funcional del perfil del usuario.
	 *
	 * @param usuario usuario de Portal COMETA para resolver alcance por perfil.
	 * @param minimoRegistrosRequerido total minimo de registros requerido para ejecutar la prueba.
	 * @return mapa con fechaInicio, fechaFin, totalRegistros, perfil, filtroAplicado, valorFiltro y cumpleMinimo.
	 * @throws Exception cuando no se localiza un rango con informacion valida o falla la consulta.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-11
	 */
	public HashMap<String, String> obtenerRangoFechasComprobantesCompraConDatosColumnasNuevas(
			String usuario, int minimoRegistrosRequerido) throws Exception {
		String sqlPerfil = "select key_value "
				+ "from xxcm_user_keys "
				+ "where short_name = 'PCPERFIL' "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) "
				+ "limit 1";
		String sqlKeys = "select short_name, key_value "
				+ "from xxcm_user_keys "
				+ "where short_name in ('PCRFC', 'PCSUPPLIERID', 'PCPLAZA') "
				+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?)";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa,
				GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {

			if (!existeColumna(connection, "xxcm_cnv_dto", "order_no")) {
				throw new Exception("No se encontro la columna order_no en la tabla xxcm_cnv_dto");
			}

			String columnaSerieXdap = existeColumna(connection, "xxcm_directos_actividad_proveedor", "serie_celular")
					? "serie_celular"
					: (existeColumna(connection, "xxcm_directos_actividad_proveedor", "numero_serie_celular")
							? "numero_serie_celular"
							: null);
			String columnaOrdenXdap = existeColumna(connection, "xxcm_directos_actividad_proveedor", "order_no")
					? "order_no"
					: (existeColumna(connection, "xxcm_directos_actividad_proveedor", "orden_no")
							? "orden_no"
							: null);

			String perfil = "";
			try (PreparedStatement statementPerfil = connection.prepareStatement(sqlPerfil)) {
				statementPerfil.setString(1, usuario);
				try (ResultSet resultSetPerfil = statementPerfil.executeQuery()) {
					if (resultSetPerfil.next()) {
						perfil = resultSetPerfil.getString("key_value") == null
								? ""
								: resultSetPerfil.getString("key_value").trim();
					}
				}
			}

			String pcrfc = null;
			String pcSupplierId = null;
			String pcPlaza = null;
			try (PreparedStatement statementKeys = connection.prepareStatement(sqlKeys)) {
				statementKeys.setString(1, usuario);
				try (ResultSet resultSetKeys = statementKeys.executeQuery()) {
					while (resultSetKeys.next()) {
						String shortName = resultSetKeys.getString("short_name");
						String keyValue = resultSetKeys.getString("key_value");
						if ("PCRFC".equalsIgnoreCase(shortName)) {
							pcrfc = keyValue;
						} else if ("PCSUPPLIERID".equalsIgnoreCase(shortName)) {
							pcSupplierId = keyValue;
						} else if ("PCPLAZA".equalsIgnoreCase(shortName)) {
							pcPlaza = keyValue;
						}
					}
				}
			}

			String perfilNormalizado = perfil.toUpperCase(Locale.ROOT);
			String filtroAplicado = "GLOBAL";
			String valorFiltro = "SIN FILTRO";
			String filtroAlcance = "";
			if (!"ADMIN".equals(perfilNormalizado) && !"OXXO_OS".equals(perfilNormalizado)) {
				if ("OXXO_PLAZA".equals(perfilNormalizado) && pcPlaza != null && !pcPlaza.trim().isEmpty()) {
					filtroAlcance = " and ((p.cr_plaza::text || ' '::text) || p.desc_plaza::text) = ? ";
					filtroAplicado = "PCPLAZA";
					valorFiltro = pcPlaza.trim();
				} else if (pcrfc != null && !pcrfc.trim().isEmpty()) {
					filtroAlcance = " and c.proveedor_rfc = ? ";
					filtroAplicado = "PCRFC";
					valorFiltro = pcrfc.trim();
				} else if (pcSupplierId != null && !pcSupplierId.trim().isEmpty()) {
					filtroAlcance = " and c.proveedor_id::text = ? ";
					filtroAplicado = "PCSUPPLIERID";
					valorFiltro = pcSupplierId.trim();
				}
			}

			String existsXdap = "exists (select 1 from xxcm_directos_actividad_proveedor xdap "
					+ "where xdap.codigo_activacion is not null "
					+ (columnaSerieXdap == null ? "" : "and xdap." + columnaSerieXdap + " is not null ")
					+ (columnaOrdenXdap == null ? ""
							: "and trim(xdap." + columnaOrdenXdap + "::text) = trim(d.orden_de_compra::text) ")
					+ ")";

			String sql = "WITH cte_data AS( "
					+ "select c.id, c.proveedor_rfc, c.proveedor_id, "
					+ "(p.cr_plaza::text || ' '::text) || p.desc_plaza::text AS cr_desc_plaza, "
					+ "coalesce(c.fecha, h.fecha_registro) as fecha_base, d.orden_de_compra "
					+ "from xxcm_cc c "
					+ "inner join xxcm_historico_correos h on h.id = c.id "
					+ "left join xxcm_region_plaza_tienda_job_sync p on p.id_retek = c.id_retek and h.id_retek = p.id_retek "
					+ "left join xxcm_cc_detalle d on d.id = c.id "
					+ "where coalesce(c.fecha, h.fecha_registro) is not null "
					+ filtroAlcance
					+ "and d.orden_de_compra is not null "
					+ "and exists (select 1 from xxcm_cnv_dto xcd "
					+ "where trim(xcd.order_no::text) = trim(d.orden_de_compra::text)) "
					+ "and " + existsXdap
					+ "), cte_diario AS( "
					+ "select fecha_base::date as fecha, count(*) as registros_dia "
					+ "from cte_data "
					+ "group by fecha_base::date "
					+ "), cte_acumulado AS( "
					+ "select fecha, registros_dia, "
					+ "sum(registros_dia) over(order by fecha desc rows between unbounded preceding and current row) as total_acumulado "
					+ "from cte_diario "
					+ "), cte_inicio AS( "
					+ "select fecha as fecha_inicio, total_acumulado as total_registros "
					+ "from cte_acumulado "
					+ "where total_acumulado >= ? "
					+ "order by fecha desc "
					+ "limit 1 "
					+ "), cte_total AS( "
					+ "select coalesce(sum(registros_dia), 0) as total_disponible from cte_diario "
					+ "), cte_fin AS( "
					+ "select max(fecha) as fecha_fin from cte_diario "
					+ ") "
					+ "select i.fecha_inicio::text as fecha_inicio, f.fecha_fin::text as fecha_fin, "
					+ "i.total_registros::text as total_registros, t.total_disponible::text as total_disponible "
					+ "from cte_inicio i "
					+ "cross join cte_fin f "
					+ "cross join cte_total t";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				int indiceParametro = 1;
				if (!filtroAlcance.isEmpty()) {
					statement.setString(indiceParametro++, valorFiltro);
				}
				statement.setInt(indiceParametro, minimoRegistrosRequerido);

				try (ResultSet resultSet = statement.executeQuery()) {
					if (!resultSet.next()) {
						throw new Exception("No existe rango con datos de Order No y Codigo de activacion/ID Dispositivo para el usuario "
								+ usuario + " con minimo " + minimoRegistrosRequerido + " registros.");
					}

					HashMap<String, String> rangoFechas = new HashMap<>();
					rangoFechas.put("fechaInicio", resultSet.getString("fecha_inicio"));
					rangoFechas.put("fechaFin", resultSet.getString("fecha_fin"));
					rangoFechas.put("totalRegistros", resultSet.getString("total_registros"));
					rangoFechas.put("totalDisponible", resultSet.getString("total_disponible"));
					rangoFechas.put("perfil", perfil);
					rangoFechas.put("filtroAplicado", filtroAplicado);
					rangoFechas.put("valorFiltro", valorFiltro);
					rangoFechas.put("cumpleMinimo", "true");

					testCase.addBoldTextEvidenceCurrentStep(
							"Rango BD con datos de columnas nuevas (OC EDI, Codigo de activacion, ID Dispositivo)");
					testCase.addTextEvidenceCurrentStep("Usuario: " + usuario
							+ " | Perfil: " + perfil
							+ " | Filtro aplicado: " + filtroAplicado
							+ " | Valor filtro: " + valorFiltro
							+ " | Fecha Inicio: " + rangoFechas.get("fechaInicio")
							+ " | Fecha Fin: " + rangoFechas.get("fechaFin")
							+ " | Total acumulado: " + rangoFechas.get("totalRegistros")
							+ " | Total disponible historico: " + rangoFechas.get("totalDisponible")
							+ " | Minimo requerido: " + minimoRegistrosRequerido
							+ " | Columna serie usada en xdap: " + (columnaSerieXdap == null ? "NO DISPONIBLE" : columnaSerieXdap)
							+ " | Columna order usada en xdap: " + (columnaOrdenXdap == null ? "NO DISPONIBLE" : columnaOrdenXdap));
					System.out.println("[BD COMPROBANTES COMPRA RANGO COLUMNAS NUEVAS] Usuario: " + usuario
							+ " | Perfil: " + perfil
							+ " | Filtro aplicado: " + filtroAplicado
							+ " | Valor filtro: " + valorFiltro
							+ " | Fecha Inicio: " + rangoFechas.get("fechaInicio")
							+ " | Fecha Fin: " + rangoFechas.get("fechaFin")
							+ " | Total acumulado: " + rangoFechas.get("totalRegistros")
							+ " | Total disponible historico: " + rangoFechas.get("totalDisponible")
							+ " | Minimo requerido: " + minimoRegistrosRequerido
							+ " | Serie xdap: " + (columnaSerieXdap == null ? "NO DISPONIBLE" : columnaSerieXdap)
							+ " | Order xdap: " + (columnaOrdenXdap == null ? "NO DISPONIBLE" : columnaOrdenXdap));

					return rangoFechas;
				}
			}
		} catch (Exception exception) {
			testCase.addCodeEvidenceCurrentStep(
					"Error al obtener rango de Comprobantes con datos para columnas nuevas: " + exception.getMessage());
			throw new Exception(
					"Error al obtener rango de Comprobantes con datos para columnas nuevas: " + exception.getMessage());
		}
	}

	/**
	 * Valida si una columna existe en una tabla de PostgreSQL.
	 *
	 * @param connection conexion activa a la BD.
	 * @param nombreTabla nombre de la tabla a consultar.
	 * @param nombreColumna nombre de la columna a validar.
	 * @return true cuando la columna existe en la tabla indicada.
	 * @throws Exception si ocurre un error de consulta.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-11
	 */
	private boolean existeColumna(Connection connection, String nombreTabla, String nombreColumna) throws Exception {
		String sql = "select 1 from information_schema.columns "
				+ "where table_schema = 'public' and table_name = ? and column_name = ? limit 1";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setString(1, nombreTabla);
			ps.setString(2, nombreColumna);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}

	/**
	 * Valida que OC EDI y Orden de Compra esten informados y sean diferentes en el
	 * Excel individual descargado.
	 *
	 * @param screenshot true para adjuntar evidencia visual del paso.
	 * @return true cuando existe al menos una fila comparable y se cumple que OC EDI
	 *         es diferente a Orden de Compra.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-11
	 */
	public boolean validarOcEdiDiferenteDeOrdenCompraExcelIndividual(boolean screenshot) {
		try {
			File archivo = obtenerUltimoExcelDescargado();
			if (archivo == null || !archivo.exists()) {
				registrarResultado(false,
						"Se localizo archivo Excel individual para validar OC EDI vs Orden de Compra",
						"No se encontro archivo Excel individual para validar OC EDI vs Orden de Compra");
				return false;
			}

			try (FileInputStream fis = new FileInputStream(archivo);
					Workbook workbook = WorkbookFactory.create(fis)) {
				Sheet sheet = workbook.getSheetAt(0);
				Row encabezados = sheet.getRow(0);
				if (encabezados == null) {
					registrarResultado(false,
							"Se encontro fila de encabezados para validar OC EDI vs Orden de Compra",
							"El Excel individual no contiene encabezados para validar OC EDI vs Orden de Compra");
					return false;
				}

				int indiceOcEdi = buscarIndiceColumnaExcel(encabezados, "OC EDI");
				int indiceOrdenCompra = buscarIndiceColumnaExcel(encabezados, "Orden de Compra");
				if (indiceOcEdi < 0 || indiceOrdenCompra < 0) {
					registrarResultado(false,
							"Existen columnas OC EDI y Orden de Compra en Excel individual",
							"No se localizaron las columnas OC EDI y Orden de Compra para validar criterio");
					return false;
				}

				DataFormatter formatter = new DataFormatter(Locale.ROOT);
				boolean hayFilaComparable = false;
				boolean reglaCumplida = true;
				String primerDetalle = "";

				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					if (row == null) {
						continue;
					}

					String valorOrden = "";
					Cell celdaOrden = row.getCell(indiceOrdenCompra);
					if (celdaOrden != null) {
						valorOrden = formatter.formatCellValue(celdaOrden).trim();
					}

					String valorOcEdi = "";
					Cell celdaOcEdi = row.getCell(indiceOcEdi);
					if (celdaOcEdi != null) {
						valorOcEdi = formatter.formatCellValue(celdaOcEdi).trim();
					}

					if (valorOrden.isEmpty() && valorOcEdi.isEmpty()) {
						continue;
					}

					hayFilaComparable = true;
					boolean cumpleFila = !valorOrden.isEmpty() && !valorOcEdi.isEmpty()
							&& !valorOcEdi.equalsIgnoreCase(valorOrden);
					if (!cumpleFila) {
						reglaCumplida = false;
						if (primerDetalle.isEmpty()) {
							primerDetalle = "Fila " + (i + 1)
									+ " | Orden de Compra: '" + valorOrden
									+ "' | OC EDI: '" + valorOcEdi + "'";
						}
					}
				}

				boolean resultadoPaso = hayFilaComparable && reglaCumplida;
				String detalle = resultadoPaso
						? "OC EDI y Orden de Compra informados y diferentes en las filas comparables"
						: (hayFilaComparable
								? "No se cumple la regla OC EDI != Orden de Compra en al menos una fila. " + primerDetalle
								: "No se encontraron filas comparables para validar OC EDI y Orden de Compra");

				registrarResultado(resultadoPaso,
						"Validacion OC EDI diferente de Orden de Compra completada en Excel individual",
						detalle);
				testCase.addTextEvidenceCurrentStep("Detalle validacion OC EDI vs Orden de Compra: " + detalle);
				if (screenshot) {
					testCase.addScreenShotCurrentStep(u, "Validacion_OC_EDI_Diferente_Orden_Compra");
				}

				return resultadoPaso;
			}
		} catch (Exception exception) {
			registrarError("No fue posible validar que OC EDI sea diferente de Orden de Compra: " + exception.getMessage());
			return false;
		}
	}
}