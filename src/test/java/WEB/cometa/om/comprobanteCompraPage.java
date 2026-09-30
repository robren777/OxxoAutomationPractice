package WEB.cometa.om;

import java.awt.Desktop;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

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

public class comprobanteCompraPage {

	SeleniumUtil u;
	TestCase testCase;
	comprobantesCompraElements elements = new comprobantesCompraElements();
	private long inicioUltimaDescargaExcelMs = 0L;

	public comprobanteCompraPage(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Valida que la pantalla Comprobantes de Compra quede disponible despues de ingresar desde Directos.
	 * Los filtros, fechas, consulta y grid se reutilizan desde PortalCometaMVP4.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el titulo de la pantalla esta visible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-14
	 */
	public boolean validarPantallaComprobantesCompra(boolean screenshot) {
		try {
			esperarCargaComprobantesCompra();

			boolean pantallaDisponible = validarElementoVisibleDiagnostico("Titulo Comprobantes de Compra", elements.tituloComprobantesCompra, 30);
			if (!pantallaDisponible) {
				registrarDiagnosticoPantallaComprobantes(screenshot);
				return false;
			}

			if (screenshot) {
				u.highLight(elements.tituloComprobantesCompra);
				testCase.addTextEvidenceCurrentStep("Se muestra la pantalla Comprobantes de Compra con el grid visible.");
				testCase.addScreenShotCurrentStep(u, "Pantalla_Comprobantes_Compra");
				u.unhighLight(elements.tituloComprobantesCompra);
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error al validar pantalla Comprobantes de Compra: " + e.getMessage());
			registrarDiagnosticoPantallaComprobantes(screenshot);
			return false;
		}
	}

	/**
	 * Valida que el modal Comprobante de compra muestre titulo, vista preliminar y botones de descarga PDF y Excel.
	 * Esta validacion confirma que el modal abrio correctamente antes de revisar la condicion visual corregida.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si los elementos principales del modal estan visibles.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-14
	 */
	public boolean validarModalComprobanteCompraAbierto(boolean screenshot) {
		try {
			esperarCargaComprobantesCompra();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.modalComprobanteCompra));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.tituloModalComprobanteCompra));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonDescargaPdfModal));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonDescargaExcelModal));

			if (screenshot) {
				u.highLight(elements.tituloModalComprobanteCompra);
				testCase.addTextEvidenceCurrentStep("Se abre el modal Comprobante de compra con vista preliminar y opciones de descarga PDF y Excel.");
				testCase.addScreenShotCurrentStep(u, "Modal_Comprobante_Compra_Abierto");
				u.unhighLight(elements.tituloModalComprobanteCompra);
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar apertura de modal Comprobante de compra: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el contenido del modal no presente un bloque blanco vacio asociado al defecto CA01.
	 * Se permite el fondo propio del PDF y se detectan contenedores blancos vacios fuera de la vista renderizada.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si no se detecta rectangulo blanco en el contenido del modal.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-14
	 */
	public boolean validarContenidoModalSinRectanguloBlanco(boolean screenshot) {
		try {
			esperarCargaComprobantesCompra();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			WebElement contenido = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.contenidoModalComprobanteCompra));
			Boolean sinRectanguloBlanco = (Boolean) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const root = arguments[0];" +
					"const esBlanco = value => ['rgb(255,255,255)','rgba(255,255,255,1)','white'].includes((value || '').replace(/\\s/g,'').toLowerCase());" +
					"const sospechosos = Array.from(root.querySelectorAll('*')).filter(el => {" +
					" const tag = el.tagName.toLowerCase();" +
					" if (['iframe','embed','object','canvas','img','table','thead','tbody','tr','td','th'].includes(tag)) return false;" +
					" if (el.querySelector('iframe,embed,object,canvas,img,table,button,mat-icon,svg')) return false;" +
					" const texto = (el.innerText || '').trim();" +
					" if (texto.length > 0) return false;" +
					" const r = el.getBoundingClientRect();" +
					" if (r.width < 120 || r.height < 30) return false;" +
					" const s = getComputedStyle(el);" +
					" return esBlanco(s.backgroundColor) && s.visibility !== 'hidden' && s.display !== 'none';" +
					"});" +
					"return sospechosos.length === 0;", contenido);

			if (screenshot) {
				u.highLight(elements.contenidoModalComprobanteCompra);
				testCase.addTextEvidenceCurrentStep("El area de contenido del modal no presenta rectangulo blanco adicional visible.");
				testCase.addScreenShotCurrentStep(u, "Modal_Sin_Rectangulo_Blanco");
				u.unhighLight(elements.contenidoModalComprobanteCompra);
			}
			return Boolean.TRUE.equals(sinRectanguloBlanco);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar contenido del modal sin rectangulo blanco: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Desplaza el scroll interno del modal hasta su parte inferior y valida que el footer sea visible.
	 * El metodo conserva la evidencia del estado final para revisar la zona inferior del modal.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el footer del modal queda visible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-14
	 */
	public boolean desplazarAlFooterModalYValidarVisible(boolean screenshot) {
		try {
			esperarCargaComprobantesCompra();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.modalComprobanteCompra));
			u.scrollToElement(elements.footerModalComprobanteCompra);
			WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.footerModalComprobanteCompra));

			if (screenshot) {
				u.highLight(elements.footerModalComprobanteCompra);
				testCase.addTextEvidenceCurrentStep("El footer del modal Comprobante de compra es visible en la parte inferior.");
				testCase.addScreenShotCurrentStep(u, "Footer_Modal_Comprobante_Compra");
				u.unhighLight(elements.footerModalComprobanteCompra);
			}
			return footer.isDisplayed();
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al desplazar y validar footer del modal: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el footer del modal no muestre una franja o barra blanca adicional asociada al defecto CA02.
	 * Se revisan hijos vacios con apariencia de barra blanca dentro de la zona de footer.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si no existe franja blanca adicional en el footer.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-14
	 */
	public boolean validarFooterModalSinFranjaBlanca(boolean screenshot) {
		try {
			esperarCargaComprobantesCompra();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.footerModalComprobanteCompra));
			Boolean sinFranjaBlanca = (Boolean) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const footer = arguments[0];" +
					"const footerRect = footer.getBoundingClientRect();" +
					"const esBlanco = value => ['rgb(255,255,255)','rgba(255,255,255,1)','white'].includes((value || '').replace(/\\s/g,'').toLowerCase());" +
					"const barras = Array.from(footer.querySelectorAll('*')).filter(el => {" +
					" const r = el.getBoundingClientRect();" +
					" if (r.width < footerRect.width * 0.70 || r.height < 4 || r.height > 32) return false;" +
					" if ((el.innerText || '').trim().length > 0) return false;" +
					" if (el.querySelector('button,mat-icon,svg,img')) return false;" +
					" const s = getComputedStyle(el);" +
					" return esBlanco(s.backgroundColor) && s.visibility !== 'hidden' && s.display !== 'none';" +
					"});" +
					"return barras.length === 0;", footer);

			if (screenshot) {
				u.highLight(elements.footerModalComprobanteCompra);
				testCase.addTextEvidenceCurrentStep("El footer del modal no muestra franja o barra blanca adicional.");
				testCase.addScreenShotCurrentStep(u, "Footer_Sin_Franja_Blanca");
				u.unhighLight(elements.footerModalComprobanteCompra);
			}
			return Boolean.TRUE.equals(sinFranjaBlanca);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar footer del modal sin franja blanca: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Cierra el modal Comprobante de compra con el boton X.
	 * Espera a que el modal desaparezca para dejar la pantalla en estado estable al finalizar la prueba.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el modal se cierra correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-14
	 */
	public boolean cerrarModalComprobanteCompra(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement botonCerrar = wait.until(ExpectedConditions.elementToBeClickable(elements.botonCerrarModal));
			if (screenshot) {
				u.highLight(elements.botonCerrarModal);
				testCase.addTextEvidenceCurrentStep("Se cierra el modal Comprobante de compra desde el boton X.");
				testCase.addScreenShotCurrentStep(u, "Cerrar_Modal_Comprobante_Compra");
				u.unhighLight(elements.botonCerrarModal);
			}
			botonCerrar.click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.modalComprobanteCompra));
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al cerrar modal Comprobante de compra: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida un elemento de la pantalla de Comprobantes de Compra y registra diagnostico detallado.
	 * El log incluye conteo en DOM antes/despues, visibilidad, texto visible y locator utilizado.
	 *
	 * @param nombre nombre funcional del elemento que se valida.
	 * @param locator locator Selenium del elemento.
	 * @param timeoutSegundos tiempo maximo de espera para visibilidad.
	 * @return true si el elemento queda visible, false en caso contrario.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-14
	 */
	private boolean validarElementoVisibleDiagnostico(String nombre, By locator, int timeoutSegundos) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(timeoutSegundos));
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Registra evidencia de diagnostico cuando la pantalla de Comprobantes de Compra no carga como se espera.
	 * Incluye URL, titulo y resumen de componentes Angular visibles.
	 *
	 * @param screenshot true para registrar captura visual de la pantalla actual, false en caso contrario.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-14
	 */
	private void registrarDiagnosticoPantallaComprobantes(boolean screenshot) {
		try {
			if (screenshot) {
				testCase.addScreenShotCurrentStep(u, "Diagnostico_Pantalla_Comprobantes_Compra");
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Obtiene un resumen compacto del DOM actual para diagnosticar la pantalla Comprobantes de Compra.
	 * El resumen reporta componentes Comprobantes y encabezados visibles de la pantalla.
	 *
	 * @return resumen textual del DOM relacionado con Comprobantes de Compra.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-14
	 */
	private String obtenerResumenDomComprobantes() {
		try {
			Object resumen = ((JavascriptExecutor) u.getDriver()).executeScript(
					"const comps = Array.from(document.querySelectorAll('app-comprobantes-de-compra, app-directos-comprobantes-de-compra'));" +
					"const root = comps[0] || document.body;" +
					"const headers = Array.from(root.querySelectorAll('h1,h2,h3,h4,span.custom-header-title')).map((el, i) => {" +
					" const rect = el.getBoundingClientRect();" +
					" const text = (el.innerText || '').trim().replace(/\\s+/g, ' ').slice(0, 120);" +
					" return (i + 1) + '){visible=' + (rect.width > 0 && rect.height > 0) + ', text=' + text + '}';" +
					"});" +
					"return 'componentes=' + comps.length + ' | headers=' + headers.length + ' [' + headers.join(' || ') + ']';");
			return String.valueOf(resumen);
		} catch (Exception e) {
			return "No se pudo obtener resumen DOM: " + e.getMessage();
		}
	}

	/**
	 * Espera a que desaparezcan overlays de carga de Comprobantes de Compra antes de tomar evidencia o validar elementos.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-14
	 */
	private void esperarCargaComprobantesCompra() {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(60));
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.overlaysCarga));
		} catch (Exception ignore) {
		}
	}

	/**
	 * Valida que el boton Siguiente del paginador del modal Comprobante de compra este visible y habilitado.
	 * Esta validacion confirma que existen comprobantes adicionales para navegar desde la vista preliminar.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el boton Siguiente esta disponible para navegar.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	public boolean validarBotonSiguienteModalHabilitado(boolean screenshot) {
		try {
			esperarCargaComprobantesCompra();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			scrollAlPaginadorModal();
			WebElement botonSiguiente = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonSiguienteModal));

			if (screenshot) {
				u.highLight(elements.botonSiguienteModal);
				testCase.addTextEvidenceCurrentStep("El boton Siguiente del modal Comprobante de compra esta visible. Habilitado: " + botonHabilitado(botonSiguiente));
				testCase.addScreenShotCurrentStep(u, "Boton_Siguiente_Modal_Comprobante_Compra");
				u.unhighLight(elements.botonSiguienteModal);
			}

			return botonSiguiente.isDisplayed() && botonHabilitado(botonSiguiente);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar boton Siguiente del modal: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Navega hacia adelante en el modal Comprobante de compra y valida que cada comprobante cargue sin artefactos blancos.
	 * Despues de cada clic espera que el contador del paginador cambie y revisa contenido y footer.
	 *
	 * @param cantidadAvances cantidad maxima de clics sobre Siguiente.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si todos los avances ejecutados cargan sin rectangulo blanco ni franja blanca.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	public boolean navegarSiguienteModalYValidarSinArtefactos(int cantidadAvances, boolean screenshot) {
		try {
			int avances = Math.max(1, cantidadAvances);
			int avancesRealizados = 0;
			for (int i = 1; i <= avances; i++) {
				if (!botonPaginacionDisponible(elements.botonSiguienteModal)) {
					testCase.addTextEvidenceCurrentStep("No hay mas comprobantes disponibles para navegar con Siguiente. Contador: " + obtenerContadorPaginacionModal());
					break;
				}

				if (!clicPaginacionModal(elements.botonSiguienteModal, "Siguiente")) {
					return false;
				}
				avancesRealizados++;

				if (!validarModalActualSinArtefactos("Siguiente_" + avancesRealizados, screenshot)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al navegar con boton Siguiente del modal: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Navega hacia atras en el modal Comprobante de compra y valida que el comprobante cargado no muestre artefactos blancos.
	 * La validacion confirma que el boton Anterior conserva la correccion visual al retroceder.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el comprobante anterior carga sin rectangulo blanco ni franja blanca.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	public boolean navegarAnteriorModalYValidarSinArtefactos(boolean screenshot) {
		try {
			if (!botonPaginacionDisponible(elements.botonAnteriorModal)) {
				testCase.addTextEvidenceCurrentStep("El boton Anterior no esta disponible para la posicion actual. Contador: " + obtenerContadorPaginacionModal());
				return true;
			}

			if (!clicPaginacionModal(elements.botonAnteriorModal, "Anterior")) {
				return false;
			}

			return validarModalActualSinArtefactos("Anterior", screenshot);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al navegar con boton Anterior del modal: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Navega al ultimo comprobante disponible desde el modal y valida que no aparezcan artefactos blancos.
	 * Si el boton Ultima pagina no esta disponible, la validacion no falla y deja evidencia textual de la omision.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el ultimo comprobante se valida o si la navegacion a ultimo no esta disponible en el modal.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	public boolean navegarUltimoModalYValidarSinArtefactos(boolean screenshot) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10));
			scrollAlPaginadorModal();
			WebElement botonUltimo = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.botonUltimoModal));
			if (!botonHabilitado(botonUltimo)) {
				testCase.addTextEvidenceCurrentStep("El boton Ultimo registro no se encuentra habilitado porque la consulta solo tiene una pagina o ya se encuentra en el ultimo comprobante. Contador: " + obtenerContadorPaginacionModal());
				return true;
			}

			if (!clicPaginacionModal(elements.botonUltimoModal, "Ultimo")) {
				return false;
			}

			return validarModalActualSinArtefactos("Ultimo", screenshot);
		} catch (Exception e) {
			testCase.addTextEvidenceCurrentStep("El boton Ultimo registro no esta disponible en el modal para esta ejecucion; la validacion continua cuando la consulta tiene una sola pagina. Contador: " + obtenerContadorPaginacionModal());
			return true;
		}
	}

	/**
	 * Da clic en un boton de paginacion del modal Comprobante de compra y espera el cambio de contador.
	 * El metodo evita registrar logs tecnicos en evidencia y solo controla la navegacion del modal.
	 *
	 * @param boton locator del boton de paginacion.
	 * @param nombreBoton nombre funcional del boton para mensajes de error.
	 * @return true si el clic se ejecuta y la pantalla queda estable.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	private boolean clicPaginacionModal(By boton, String nombreBoton) {
		try {
			esperarCargaComprobantesCompra();
			String contadorAntes = obtenerContadorPaginacionModal();
			scrollAlPaginadorModal();
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			WebElement botonPaginacion = wait.until(ExpectedConditions.visibilityOfElementLocated(boton));

			if (!botonHabilitado(botonPaginacion)) {
				testCase.addTextEvidenceCurrentStep("El boton " + nombreBoton + " del modal no esta habilitado para la navegacion actual.");
				return false;
			}

			try {
				new Actions(u.getDriver()).moveToElement(botonPaginacion).pause(Duration.ofMillis(150)).click().perform();
			} catch (Exception clickActions) {
			}

			esperarCargaComprobantesCompra();
			if (!esperarCambioContadorModal(contadorAntes, 4)) {
				scrollAlPaginadorModal();
				botonPaginacion = wait.until(ExpectedConditions.visibilityOfElementLocated(boton));
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].dispatchEvent(new MouseEvent('click', {bubbles:true, cancelable:true, view:window}));", botonPaginacion);
				esperarCargaComprobantesCompra();

				if (!esperarCambioContadorModal(contadorAntes, 8)) {
					return false;
				}
			}

			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al hacer clic en boton " + nombreBoton + " del modal: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida el comprobante actualmente cargado en el modal sin rectangulo blanco ni franja blanca.
	 * Agrega evidencia funcional con el contador de paginacion visible para rastrear la posicion revisada.
	 *
	 * @param etiquetaEvidencia etiqueta funcional para nombrar la captura.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el comprobante actual no muestra artefactos blancos.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	private boolean validarModalActualSinArtefactos(String etiquetaEvidencia, boolean screenshot) {
		String contador = obtenerContadorPaginacionModal();
		testCase.addTextEvidenceCurrentStep("Comprobante validado en navegacion " + etiquetaEvidencia + ". Contador: " + contador);

		boolean contenidoSinRectangulo = validarContenidoModalSinRectanguloBlanco(false);
		boolean footerVisible = desplazarAlFooterModalYValidarVisible(false);
		boolean footerSinFranja = validarFooterModalSinFranjaBlanca(false);

		if (screenshot) {
			testCase.addScreenShotCurrentStep(u, "Modal_Sin_Artefactos_" + etiquetaEvidencia);
		}

		return contenidoSinRectangulo && footerVisible && footerSinFranja;
	}

	/**
	 * Obtiene el contador visible del paginador del modal Comprobante de compra.
	 * Si no puede leerlo, devuelve una cadena controlada para no interrumpir la validacion principal.
	 *
	 * @return texto del contador del paginador o valor no disponible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	private String obtenerContadorPaginacionModal() {
		try {
			scrollAlPaginadorModal();
			WebElement contador = u.getDriver().findElement(elements.contadorPaginacionModal);
			return contador.getText() == null ? "No disponible" : contador.getText().trim().replaceAll("\\s+", " ");
		} catch (Exception e) {
			return "No disponible";
		}
	}

	/**
	 * Determina si un boton del modal esta habilitado considerando atributos y clases Angular Material.
	 *
	 * @param boton boton del paginador a evaluar.
	 * @return true si el boton esta habilitado visual y funcionalmente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	private boolean botonHabilitado(WebElement boton) {
		if (boton == null) {
			return false;
		}

		String disabled = boton.getAttribute("disabled");
		String ariaDisabled = boton.getAttribute("aria-disabled");
		return boton.isDisplayed()
				&& boton.isEnabled()
				&& disabled == null
				&& !"true".equalsIgnoreCase(ariaDisabled);
	}

	/**
	 * Desplaza el contenido del modal hasta el paginador para que los botones de navegacion sean interactuables.
	 * El paginador se encuentra al final del componente app-detalle del modal Comprobante de compra.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	private void scrollAlPaginadorModal() {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			wait.until(ExpectedConditions.presenceOfElementLocated(elements.paginadorModalComprobanteCompra));
			u.scrollToElement(elements.paginadorModalComprobanteCompra);
		} catch (Exception ignore) {
		}
	}

	/**
	 * Valida si un boton de paginacion del modal esta visible y habilitado despues de desplazar al paginador.
	 *
	 * @param boton locator del boton de paginacion.
	 * @return true si el boton puede usarse en la posicion actual del modal.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	private boolean botonPaginacionDisponible(By boton) {
		try {
			scrollAlPaginadorModal();
			WebElement botonPaginacion = u.getDriver().findElement(boton);
			return botonHabilitado(botonPaginacion);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Espera a que el contador del paginador del modal cambie despues de una accion de navegacion.
	 * Si el contador inicial no esta disponible, espera a que el contenido del modal permanezca visible.
	 *
	 * @param contadorAntes texto del contador antes del clic.
	 * @param timeoutSegundos tiempo maximo de espera.
	 * @return true si detecta cambio de contador o contenido estable cuando no hay contador.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	private boolean esperarCambioContadorModal(String contadorAntes, int timeoutSegundos) {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(timeoutSegundos));
			if (!"No disponible".equalsIgnoreCase(contadorAntes)) {
				wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(elements.contadorPaginacionModal, contadorAntes)));
				return true;
			}

			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.contenidoModalComprobanteCompra));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Registra el contador visible del paginador del modal Comprobante de compra.
	 * Conserva evidencia funcional de la posicion actual y total de comprobantes en el modal.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el contador se puede leer desde el modal.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	public boolean registrarContadorPaginacionModal(boolean screenshot) {
		try {
			scrollAlPaginadorModal();
			String contador = obtenerContadorPaginacionModal();
			testCase.addTextEvidenceCurrentStep("Contador de paginacion del modal: " + contador);
			if (screenshot) {
				testCase.addScreenShotCurrentStep(u, "Contador_Paginacion_Modal_Comprobante_Compra");
			}
			return !"No disponible".equalsIgnoreCase(contador);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al registrar contador de paginacion del modal: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Navega a la primera pagina del modal Comprobante de compra y registra el contador resultante.
	 * Si el boton Primera pagina esta deshabilitado porque ya se esta en el primer registro, la validacion se considera correcta.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si queda en la primera pagina o si ya estaba en ella.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	public boolean navegarPrimeroModalYValidarContador(boolean screenshot) {
		try {
			if (botonPaginacionDisponible(elements.botonPrimeroModal)) {
				if (!clicPaginacionModal(elements.botonPrimeroModal, "Primero")) {
					return false;
				}
			} else {
				testCase.addTextEvidenceCurrentStep("El boton Primera pagina no esta habilitado porque el modal ya se encuentra en el primer comprobante. Contador: " + obtenerContadorPaginacionModal());
			}

			return registrarContadorPaginacionModal(screenshot);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al navegar a primera pagina del modal: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que los botones Primera pagina y Anterior esten deshabilitados al estar en el primer registro del modal.
	 * Esta validacion confirma que no se puede navegar antes del limite inicial.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si ambos botones estan deshabilitados.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	public boolean validarBotonesPrimeroYAnteriorDeshabilitados(boolean screenshot) {
		try {
			scrollAlPaginadorModal();
			WebElement botonPrimero = u.getDriver().findElement(elements.botonPrimeroModal);
			WebElement botonAnterior = u.getDriver().findElement(elements.botonAnteriorModal);
			boolean primeroDeshabilitado = !botonHabilitado(botonPrimero);
			boolean anteriorDeshabilitado = !botonHabilitado(botonAnterior);

			testCase.addTextEvidenceCurrentStep("Boton Primera pagina deshabilitado: " + primeroDeshabilitado
					+ " | Boton Anterior deshabilitado: " + anteriorDeshabilitado
					+ " | Contador: " + obtenerContadorPaginacionModal());
			if (screenshot) {
				testCase.addScreenShotCurrentStep(u, "Botones_Inicio_Deshabilitados_Modal");
			}

			return primeroDeshabilitado && anteriorDeshabilitado;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar botones Primera pagina y Anterior deshabilitados: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el boton Siguiente este deshabilitado al estar en el ultimo registro del modal.
	 * Esta validacion confirma que no se puede navegar despues del limite final.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el boton Siguiente esta deshabilitado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	public boolean validarBotonSiguienteDeshabilitado(boolean screenshot) {
		try {
			scrollAlPaginadorModal();
			WebElement botonSiguiente = u.getDriver().findElement(elements.botonSiguienteModal);
			boolean siguienteDeshabilitado = !botonHabilitado(botonSiguiente);
			testCase.addTextEvidenceCurrentStep("Boton Siguiente deshabilitado: " + siguienteDeshabilitado
					+ " | Contador: " + obtenerContadorPaginacionModal());
			if (screenshot) {
				testCase.addScreenShotCurrentStep(u, "Boton_Siguiente_Deshabilitado_Modal");
			}

			return siguienteDeshabilitado;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar boton Siguiente deshabilitado: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Da clic en el boton PDF del modal Comprobante de compra y espera que termine la preparacion de descarga.
	 * La evidencia muestra el boton usado para iniciar la descarga individual del comprobante visible.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el clic en PDF se ejecuta correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	public boolean descargarPdfModal(boolean screenshot) {
		long inicioDescarga = System.currentTimeMillis() - 2000;
		boolean clicOk = clicDescargaModal(elements.botonDescargaPdfModal, "PDF", screenshot);
		if (!clicOk) return false;
		return esperarArchivoPdfDescargado(inicioDescarga);
	}

	/**
	 * Abre el PDF mas reciente descargado en una ventana nueva, toma evidencia y cierra
	 * la ventana navegando primero a about:blank para evitar el timeout del visor PDF de Edge.
	 *
	 * @param screenshot true para registrar evidencia visual del PDF abierto.
	 * @return true si el PDF se abre y se cierra correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-08-06
	 */
	public boolean abrirPdfDescargadoModal(boolean screenshot) {
		String ventanaOriginal = u.getDriver().getWindowHandle();
		String ventanaPdf = null;
		try {
			File carpeta = new File(System.getProperty("user.home"), "Downloads");
			File pdf = obtenerPdfMasReciente(carpeta);
			if (pdf == null || !pdf.exists()) {
				testCase.addCodeEvidenceCurrentStep("No se encontro archivo PDF en Downloads para abrir evidencia.");
				return false;
			}

			u.getDriver().switchTo().newWindow(org.openqa.selenium.WindowType.WINDOW);
			ventanaPdf = u.getDriver().getWindowHandle();
			u.getDriver().get(pdf.toURI().toString());
			u.hardWait(6);

			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se abre el PDF descargado correctamente: " + pdf.getName());
				testCase.addScreenShotCurrentStep(u, "PDF_Descargado_Modal_Comprobante_Compra");
			}

			// Navegar a about:blank descarga el visor PDF antes de close() para evitar timeout en Edge
			u.getDriver().get("about:blank");
			u.hardWait(1);
			u.getDriver().close();
			u.getDriver().switchTo().window(ventanaOriginal);
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al abrir PDF descargado: " + e.getMessage());
			try {
				if (ventanaPdf != null) {
					u.getDriver().switchTo().window(ventanaOriginal);
				}
			} catch (Exception ignored) {}
			return false;
		}
	}

	/**
	 * Da clic en el boton Excel del modal Comprobante de compra y espera que termine la preparacion de descarga.
	 * La evidencia muestra el boton usado para iniciar la descarga individual del comprobante visible.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el clic en Excel se ejecuta correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	public boolean descargarExcelModal(boolean screenshot) {
		inicioUltimaDescargaExcelMs = System.currentTimeMillis() - 2000;
		return clicDescargaModal(elements.botonDescargaExcelModal, "Excel", screenshot);
	}

	/**
	 * Ejecuta el clic en un boton de descarga del modal Comprobante de compra y espera que desaparezcan overlays de carga.
	 *
	 * @param botonDescarga locator del boton de descarga.
	 * @param tipoDescarga nombre funcional de la descarga.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el clic se ejecuta correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	private boolean clicDescargaModal(By botonDescarga, String tipoDescarga, boolean screenshot) {
		try {
			esperarCargaComprobantesCompra();
			u.scrollToElement(botonDescarga);
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			WebElement boton = wait.until(ExpectedConditions.visibilityOfElementLocated(botonDescarga));

			if (screenshot) {
				u.highLight(botonDescarga);
				testCase.addTextEvidenceCurrentStep("Se da clic en el boton de descarga " + tipoDescarga + " del comprobante visible en el modal.");
				testCase.addScreenShotCurrentStep(u, "Descarga_" + tipoDescarga + "_Modal_Comprobante_Compra");
				u.unhighLight(botonDescarga);
			}

			try {
				new Actions(u.getDriver()).moveToElement(boton).pause(Duration.ofMillis(150)).click().perform();
			} catch (Exception clickActions) {
				u.click(botonDescarga);
			}

			esperarCargaComprobantesCompra();
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al descargar " + tipoDescarga + " desde el modal: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Abre el archivo Excel mas reciente descargado desde el modal Comprobante de compra y registra evidencia visual.
	 * Busca especificamente archivos .xlsx o .xls posteriores al clic de descarga para evitar abrir un PDF descargado previamente.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se encuentra y abre el archivo Excel descargado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	public boolean abrirExcelDescargadoModal(boolean screenshot) {
		try {
			File archivoExcel = esperarArchivoExcelDescargado();
			if (archivoExcel == null || !archivoExcel.exists()) {
				testCase.addTextEvidenceCurrentStep("No se encontro archivo Excel descargado para abrir evidencia.");
				return false;
			}

			if (!Desktop.isDesktopSupported()) {
				testCase.addTextEvidenceCurrentStep("No se puede abrir el archivo Excel porque Desktop no esta soportado en el equipo.");
				return false;
			}

			Desktop.getDesktop().open(archivoExcel);
			u.hardWait(8);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se abre archivo Excel descargado correctamente: " + archivoExcel.getName());
				testCase.addScreenShotCurrentStep("Excel_Descargado_Modal_Comprobante_Compra");
			}

			Runtime.getRuntime().exec("cmd /c taskkill /f /im excel.exe");
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al abrir Excel descargado desde el modal: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Espera hasta encontrar el archivo Excel mas reciente en Downloads posterior al inicio de descarga del modal.
	 * Omite archivos temporales de descarga y solo considera extensiones .xlsx y .xls.
	 *
	 * @return archivo Excel descargado o null si no se encuentra dentro del tiempo esperado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	private File esperarArchivoExcelDescargado() {
		File carpetaDescargas = new File(System.getProperty("user.home"), "Downloads");
		long inicioBusqueda = inicioUltimaDescargaExcelMs > 0L ? inicioUltimaDescargaExcelMs : System.currentTimeMillis() - 60000;
		long fin = System.currentTimeMillis() + 30000;

		while (System.currentTimeMillis() < fin) {
			File archivoExcel = obtenerExcelMasReciente(carpetaDescargas, inicioBusqueda);
			if (archivoExcel != null && archivoExcel.exists() && archivoExcel.length() > 0) {
				long tamanoInicial = archivoExcel.length();
				esperarBreve(600);
				if (archivoExcel.exists() && archivoExcel.length() == tamanoInicial) {
					return archivoExcel;
				}
			}

			esperarBreve(500);
		}

		return null;
	}

	/**
	 * Obtiene el Excel mas reciente en una carpeta considerando fecha minima de modificacion.
	 *
	 * @param carpetaDescargas carpeta Downloads donde se buscan archivos.
	 * @param inicioBusqueda timestamp minimo de modificacion permitido.
	 * @return archivo Excel mas reciente o null si no existe candidato.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	private File obtenerExcelMasReciente(File carpetaDescargas, long inicioBusqueda) {
		if (carpetaDescargas == null || !carpetaDescargas.exists()) {
			return null;
		}

		File[] archivos = carpetaDescargas.listFiles();
		if (archivos == null) {
			return null;
		}

		File mejorArchivo = null;
		long mejorModificacion = Long.MIN_VALUE;
		for (File archivo : archivos) {
			if (archivo == null || !archivo.isFile()) {
				continue;
			}

			String nombre = archivo.getName().toLowerCase();
			if (!(nombre.endsWith(".xlsx") || nombre.endsWith(".xls")) || nombre.endsWith(".crdownload")) {
				continue;
			}

			long modificado = archivo.lastModified();
			if (modificado >= inicioBusqueda && modificado > mejorModificacion) {
				mejorArchivo = archivo;
				mejorModificacion = modificado;
			}
		}

		return mejorArchivo;
	}

	/**
	 * Pausa brevemente la ejecucion conservando el estado de interrupcion del hilo.
	 *
	 * @param milisegundos tiempo de espera en milisegundos.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-15
	 */
	/** Espera a que el PDF mas reciente en Downloads tenga tamano estable (descarga completa). */
	private boolean esperarArchivoPdfDescargado(long inicioDescarga) {
		File carpeta = new File(System.getProperty("user.home"), "Downloads");
		long fin = System.currentTimeMillis() + 60000;
		while (System.currentTimeMillis() < fin) {
			File[] archivos = carpeta.listFiles();
			if (archivos != null) {
				File mejor = null;
				long mejorTs = Long.MIN_VALUE;
				for (File f : archivos) {
					if (f == null || !f.isFile()) continue;
					String nombre = f.getName().toLowerCase();
					if (!nombre.endsWith(".pdf")) continue;
					if (nombre.endsWith(".crdownload") || nombre.endsWith(".tmp")) continue;
					long ts = f.lastModified();
					if (ts >= inicioDescarga && ts > mejorTs) {
						mejorTs = ts;
						mejor = f;
					}
				}
				if (mejor != null && mejor.length() > 0) {
					long tam1 = mejor.length();
					esperarBreve(1000);
					if (mejor.exists() && mejor.length() == tam1) {
						System.out.println("[PDF-Descarga] Archivo listo: " + mejor.getName());
						return true;
					}
				}
			}
			esperarBreve(500);
		}
		System.out.println("[PDF-Descarga] Tiempo de espera agotado para descarga del PDF.");
		return false;
	}

	/** Retorna el PDF con fecha de modificacion mas reciente en la carpeta indicada, o null si no existe. */
	private File obtenerPdfMasReciente(File carpeta) {
		if (carpeta == null || !carpeta.exists()) return null;
		File[] archivos = carpeta.listFiles();
		if (archivos == null) return null;
		File mejor = null;
		long mejorTs = Long.MIN_VALUE;
		for (File f : archivos) {
			if (f == null || !f.isFile()) continue;
			String nombre = f.getName().toLowerCase();
			if (!nombre.endsWith(".pdf") || nombre.endsWith(".crdownload") || nombre.endsWith(".tmp")) continue;
			if (f.lastModified() > mejorTs) {
				mejorTs = f.lastModified();
				mejor = f;
			}
		}
		return mejor;
	}

	private void esperarBreve(long milisegundos) {
		try {
			Thread.sleep(milisegundos);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	/**
	 * Aplica filtros de Plaza y Fecha en Comprobantes de Compra garantizando que la fecha
	 * seleccionada tenga mas de {@code minimoRegistros} registros en BD, condicion necesaria
	 * para validar la navegacion entre comprobantes en el modal (botones Siguiente/Anterior).
	 * La logica de perfil y seleccion de plaza replica el flujo canonico de MVP4, con el
	 * unico cambio en la clausula HAVING del SQL para exigir un minimo de registros.
	 *
	 * @param usuario         usuario de Portal COMETA con el que se consultan las llaves en BD.
	 * @param minimoRegistros cantidad minima de registros que debe tener la fecha seleccionada
	 *                        (se usa {@code count(*) > minimoRegistros}).
	 * @param screenshot      true para registrar evidencia visual en cada sub-paso.
	 * @return true si se selecciono plaza y fecha con mas de {@code minimoRegistros} registros
	 *         y la consulta finalizo correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-08-06
	 */
	public boolean busquedaFiltroPlazaFechaDirectosConMasRegistros(
			String usuario, int minimoRegistros, boolean screenshot) throws Exception {
		try {
			String dbUrl      = GlobalVariables.DB_URL_Cometa;
			String dbUser     = GlobalVariables.DB_USER_Cometa;
			String dbPassword = GlobalVariables.DB_PASSWORD_Cometa;

			String sqlPerfil = "select key_value from xxcm_user_keys where short_name = 'PCPERFIL' "
					+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = '"
					+ usuario + "');";

			String sqlLlaves = "select short_name, key_value from xxcm_user_keys "
					+ "where short_name in ('PCRFC', 'PCSUPPLIERID', 'PCPLAZA') "
					+ "and usuario_id = (select usuario_id from xxcm_usuarios where usuario = '"
					+ usuario + "');";

			String fechaBD = null;
			String plazaSeleccionada = null;

			By filtroPlaza   = elements.filtroPlaza;
			By primeraOpcion = By.xpath("(//div[@role='option' and contains(@class,'ng-option')])[1]");

			if (!u.isDisplayed(filtroPlaza)) {
				return false;
			}

			u.highLight(filtroPlaza);
			u.mouseOverAndClick(filtroPlaza);
			u.unhighLight(filtroPlaza);
			u.waitWebElement(primeraOpcion, 120, 10);

			List<WebElement> opcionesDropdown = u.getDriver()
					.findElements(By.xpath("//div[@role='option' and contains(@class,'ng-option')]"));

			LinkedHashSet<String> plazasDropdown = new LinkedHashSet<String>();
			for (WebElement opcion : opcionesDropdown) {
				String texto = opcion.getText();
				if (texto != null) {
					texto = texto.trim().replaceAll("\\s+", " ");
					if (!texto.isEmpty()) {
						plazasDropdown.add(texto);
					}
				}
			}

			if (plazasDropdown.isEmpty()) {
				System.out.println("[CFNavModal] No se encontraron plazas en el dropdown.");
				return false;
			}

			try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
					Statement stmt = conn.createStatement();
					ResultSet rsPerfil = stmt.executeQuery(sqlPerfil)) {

				if (!rsPerfil.next()) {
					System.out.println("[CFNavModal] No se obtuvo informacion del perfil.");
					return false;
				}

				String perfil = rsPerfil.getString("key_value");
				System.out.println("[CFNavModal] Perfil: " + perfil + " | minimoRegistros: " + minimoRegistros);

				String valorLlave = null;

				if (!"ADMIN".equalsIgnoreCase(perfil) && !"OXXO_OS".equalsIgnoreCase(perfil)) {
					try (ResultSet rsLlaves = stmt.executeQuery(sqlLlaves)) {
						while (rsLlaves.next()) {
							String shortName = rsLlaves.getString("short_name");
							String keyValue  = rsLlaves.getString("key_value");
							if ("OXXO_PLAZA".equalsIgnoreCase(perfil)
									&& "PCPLAZA".equalsIgnoreCase(shortName)) {
								valorLlave = keyValue;
								break;
							}
							if (!"OXXO_PLAZA".equalsIgnoreCase(perfil)
									&& ("PCRFC".equalsIgnoreCase(shortName) || "PCSUPPLIERID".equalsIgnoreCase(shortName))
									&& keyValue != null && !keyValue.trim().isEmpty()) {
								valorLlave = keyValue;
								break;
							}
						}
					}
					if (valorLlave == null || valorLlave.trim().isEmpty()) {
						System.out.println("[CFNavModal] No se obtuvo llave del usuario para perfil: " + perfil);
						return false;
					}
				}

				for (String plazaDropdown : plazasDropdown) {
					String plazaEsc = plazaDropdown.replace("'", "''");
					String sqlFecha;

					if ("ADMIN".equalsIgnoreCase(perfil) || "OXXO_OS".equalsIgnoreCase(perfil)) {
						sqlFecha = "select fecha from xxcm_cc "
								+ "where plaza = '" + plazaEsc + "' "
								+ "group by fecha "
								+ "having count(*) > " + minimoRegistros + " "
								+ "order by fecha desc limit 1";
					} else if ("OXXO_PLAZA".equalsIgnoreCase(perfil)) {
						String llaveEsc = valorLlave.replace("'", "''");
						sqlFecha = "select fecha from xxcm_cc "
								+ "where cr_desc_plaza = '" + llaveEsc + "' "
								+ "and plaza = '" + plazaEsc + "' "
								+ "group by fecha "
								+ "having count(*) > " + minimoRegistros + " "
								+ "order by fecha desc limit 1";
					} else {
						String llaveEsc = valorLlave.replace("'", "''");
						sqlFecha = "select fecha from xxcm_cc "
								+ "where proveedor_rfc = '" + llaveEsc + "' "
								+ "and plaza = '" + plazaEsc + "' "
								+ "group by fecha "
								+ "having count(*) > " + minimoRegistros + " "
								+ "order by fecha desc limit 1";
					}

					try (ResultSet rsFecha = stmt.executeQuery(sqlFecha)) {
						if (rsFecha.next()) {
							fechaBD = rsFecha.getString("fecha");
							plazaSeleccionada = plazaDropdown;
							System.out.println("[CFNavModal] Plaza: " + plazaSeleccionada);
							System.out.println("[CFNavModal] Fecha con >" + minimoRegistros + " registros: " + fechaBD);
							break;
						}
					}
				}

				if (plazaSeleccionada == null || fechaBD == null) {
					System.out.println("[CFNavModal] Ninguna plaza tuvo fecha con >" + minimoRegistros + " registros en BD.");
					return false;
				}
			}

			By opcionPlaza = By.xpath(
					"//div[@role='option' and contains(@class,'ng-option') and normalize-space()=\""
					+ plazaSeleccionada + "\"]");
			u.highLight(opcionPlaza);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep(
					"Se selecciona la plaza '" + plazaSeleccionada
					+ "' con fecha que tiene mas de " + minimoRegistros + " registros: " + fechaBD);
				testCase.addScreenShotCurrentStep(u, "Seleccion_Plaza_MasRegistros");
			}
			u.click(opcionPlaza);
			u.mouseOverAndClick(filtroPlaza);
			u.getDriver().findElement(By.tagName("body")).click();

			LocalDate fechaConvert;
			try {
				fechaConvert = LocalDateTime.parse(fechaBD,
						DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toLocalDate();
			} catch (Exception ex) {
				fechaConvert = LocalDate.parse(fechaBD, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			}

			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

			wait.until(ExpectedConditions.elementToBeClickable(elements.botonCalendario)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.calendario));

			DateTimeFormatter periodoCorto = new DateTimeFormatterBuilder()
					.parseCaseInsensitive().appendPattern("MMM yyyy").toFormatter(Locale.ENGLISH);
			DateTimeFormatter periodoLargo = new DateTimeFormatterBuilder()
					.parseCaseInsensitive().appendPattern("MMMM yyyy").toFormatter(Locale.ENGLISH);

			YearMonth periodoObjetivo = YearMonth.from(fechaConvert);
			YearMonth periodoVisible  = null;

			for (int intento = 0; intento < 240; intento++) {
				String textoPeriodo = wait
						.until(ExpectedConditions.visibilityOfElementLocated(elements.botonPeriodo))
						.getText().trim().replaceAll("\\s+", " ");

				String textoPeriodoNorm = textoPeriodo.toUpperCase()
						.replace("ENERO",      "JANUARY") .replace("ENE", "JAN")
						.replace("FEBRERO",    "FEBRUARY")
						.replace("MARZO",      "MARCH")
						.replace("ABRIL",      "APRIL")   .replace("ABR", "APR")
						.replace("MAYO",       "MAY")
						.replace("JUNIO",      "JUNE")
						.replace("JULIO",      "JULY")
						.replace("AGOSTO",     "AUGUST")  .replace("AGO", "AUG")
						.replace("SEPTIEMBRE", "SEPTEMBER")
						.replace("OCTUBRE",    "OCTOBER")
						.replace("NOVIEMBRE",  "NOVEMBER")
						.replace("DICIEMBRE",  "DECEMBER").replace("DIC", "DEC");

				try {
					periodoVisible = YearMonth.parse(textoPeriodoNorm, periodoCorto);
				} catch (Exception ex) {
					periodoVisible = YearMonth.parse(textoPeriodoNorm, periodoLargo);
				}

				if (periodoVisible.equals(periodoObjetivo)) break;

				By botonNavCalendario = periodoVisible.isAfter(periodoObjetivo)
						? elements.botonAnterior : elements.botonSiguiente;
				wait.until(ExpectedConditions.elementToBeClickable(botonNavCalendario)).click();
			}

			if (periodoVisible == null || !periodoVisible.equals(periodoObjetivo)) {
				throw new Exception("[CFNavModal] No fue posible posicionar el calendario en: " + periodoObjetivo);
			}

			By diaSeleccionado = By.xpath(
					"//div[contains(@class,'cdk-overlay-pane')]//mat-month-view"
					+ "//button[not(contains(@class,'mat-calendar-body-disabled'))]"
					+ "[.//span[normalize-space()='" + fechaConvert.getDayOfMonth() + "']]");

			wait.until(ExpectedConditions.elementToBeClickable(diaSeleccionado)).click();
			if (!driver.findElements(elements.calendario).isEmpty()
					&& driver.findElement(elements.calendario).isDisplayed()) {
				wait.until(ExpectedConditions.elementToBeClickable(diaSeleccionado)).click();
			}
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.calendario));

			u.mouseOver(elements.botonConsultar);
			if (u.isDisplayed(elements.botonConsultar)) {
				u.highLight(elements.botonConsultar);
				u.hardWait(5);
				u.click(elements.botonConsultar);
				new Actions(driver).sendKeys(Keys.SPACE).perform();

				try {
					u.waitWebElement(elements.overlaysCarga, 2, 1);
					if (screenshot) {
						testCase.addTextEvidenceCurrentStep("Se muestra overlay de carga: \"Espere un momento\"");
						testCase.addScreenShotCurrentStep(u, "Overlay_EspereUnMomento_NavModal");
					}
				} catch (Exception ignorar) {}

				WebDriverWait waitOverlay = new WebDriverWait(driver, Duration.ofSeconds(360));
				waitOverlay.until(d -> {
					try {
						JavascriptExecutor js = (JavascriptExecutor) d;
						String script =
								"const overlays = document.querySelectorAll('.cdk-global-overlay-wrapper');"
								+ "for (const overlay of overlays) {"
								+ "  const text = overlay.innerText || '';"
								+ "  if (text.includes('Espere un momento')) return false;"
								+ "}"
								+ "return true;";
						return Boolean.TRUE.equals(js.executeScript(script));
					} catch (Exception ex) {
						return false;
					}
				});

				u.unhighLight(elements.botonConsultar);
				By primeraFila = By.xpath("(//tr[starts-with(@class,'mat-mdc-row') and contains(@role,'row')])[1]");
				u.waitWebElement(primeraFila, 60, 5);
				u.scrollToElement(primeraFila);
				u.hardWait(2);

				if (screenshot) {
					testCase.addBoldTextEvidenceCurrentStep(
						"Busqueda completada con fecha que tiene mas de " + minimoRegistros + " registros.");
					testCase.addScreenShotCurrentStep(u, "Grid_ConMasRegistros_NavModal");
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep(
				"Error en busquedaFiltroPlazaFechaDirectosConMasRegistros: " + e.getMessage());
			throw new Exception("Error " + e.getMessage());
		}
	}

}
