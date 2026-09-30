package WEB.cometa.om;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Duration;
import java.time.YearMonth;
import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.util.GlobalVariables;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CometaNuevaIdentidadVisualPage {

	SeleniumUtil u;
	TestCase testCase;
	CometaNuevaIdentidadVisualElements elements = new CometaNuevaIdentidadVisualElements();

	public CometaNuevaIdentidadVisualPage(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Valida que exista un elemento visible de logotipo dentro de la solapa superior del portal COMETA.
	 * La busqueda prioriza atributos o textos asociados a la nueva identidad Plataforma COMETA.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el logotipo de la nueva identidad se localiza visible en la solapa superior.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-16
	 */
	public boolean validarLogoNuevaIdentidadVisualVisible(boolean screenshot) {
		try {
			esperarCargaSolapaSuperior();
			WebElement logo = localizarLogoNuevaIdentidadVisual();

			if (logo == null || !logo.isDisplayed() || !esperarRenderLogoNuevaIdentidadVisual(logo)) {
				testCase.addCodeEvidenceCurrentStep("No se encontro un logotipo visible de Plataforma COMETA en la solapa superior. Diagnostico: " + obtenerDiagnosticoSolapaSuperior());
				return false;
			}

			if (screenshot) {
				registrarEvidenciaLogo("Se localiza visible el logotipo de la nueva identidad visual en la solapa superior del portal COMETA. " + obtenerDiagnosticoLogo(logo),
						"Logo_Plataforma_COMETA_Visible");
			}

			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar visibilidad del logotipo Plataforma COMETA: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el texto accesible o los atributos del logotipo correspondan a Plataforma COMETA.
	 * Si el texto esta embebido en imagen, tambien acepta imagen de COMETA con proporcion oficial esperada.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la solapa superior identifica la nueva marca Plataforma COMETA.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-16
	 */
	public boolean validarTextoOImagenPlataformaCometa(boolean screenshot) {
		try {
			esperarCargaSolapaSuperior();
			WebElement logo = localizarLogoNuevaIdentidadVisual();

			if (logo == null) {
				testCase.addCodeEvidenceCurrentStep("No fue posible localizar el elemento candidato al logotipo. Diagnostico: " + obtenerDiagnosticoSolapaSuperior());
				return false;
			}
			if (!esperarRenderLogoNuevaIdentidadVisual(logo)) {
				testCase.addCodeEvidenceCurrentStep("El logotipo fue localizado pero la imagen no termino de renderizar. " + obtenerDiagnosticoLogo(logo));
				return false;
			}

			Boolean identidadCorrecta = (Boolean) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const el = arguments[0];"
							+ "const normalizar = value => (value || '').toString().normalize('NFD').replace(/[\\u0300-\\u036f]/g,'').toLowerCase();"
							+ "const texto = normalizar([el.innerText, el.getAttribute('alt'), el.getAttribute('title'), el.getAttribute('aria-label'), el.getAttribute('src')].join(' '));"
							+ "const rect = el.getBoundingClientRect();"
							+ "const ratio = rect.height > 0 ? rect.width / rect.height : 0;"
							+ "const contieneMarca = texto.includes('plataforma') && texto.includes('cometa');"
							+ "const imagenCometaConProporcionOficial = texto.includes('cometa') && ratio >= 3.5 && ratio <= 6.0 && rect.width >= 120 && rect.height >= 20;"
							+ "return contieneMarca || imagenCometaConProporcionOficial;",
					logo);

			if (screenshot) {
				registrarEvidenciaLogo("Se valida que la solapa superior muestre la identidad visual Plataforma COMETA. " + obtenerDiagnosticoLogo(logo),
						"Identidad_Visual_Plataforma_COMETA");
			}

			return Boolean.TRUE.equals(identidadCorrecta);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar texto o imagen Plataforma COMETA: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el logotipo de Plataforma COMETA conserve una proporcion visual consistente con la referencia oficial.
	 * La referencia esperada es cercana a 261 x 56 px, con relacion de aspecto aproximada de 4.66.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el logotipo esta visible, con dimensiones positivas y relacion de aspecto dentro del rango aceptado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-16
	 */
	public boolean validarProporcionLogoPlataformaCometa(boolean screenshot) {
		try {
			esperarCargaSolapaSuperior();
			WebElement logo = localizarLogoNuevaIdentidadVisual();

			if (logo == null) {
				testCase.addCodeEvidenceCurrentStep("No fue posible localizar el logotipo para validar proporcion. Diagnostico: " + obtenerDiagnosticoSolapaSuperior());
				return false;
			}
			if (!esperarRenderLogoNuevaIdentidadVisual(logo)) {
				testCase.addCodeEvidenceCurrentStep("El logotipo fue localizado pero la imagen no termino de renderizar para validar proporcion. " + obtenerDiagnosticoLogo(logo));
				return false;
			}

			Boolean proporcionCorrecta = (Boolean) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const rect = arguments[0].getBoundingClientRect();"
							+ "const style = getComputedStyle(arguments[0]);"
							+ "const ratio = rect.height > 0 ? rect.width / rect.height : 0;"
							+ "return rect.width >= 120 && rect.height >= 20 && ratio >= 3.5 && ratio <= 6.0 && style.visibility !== 'hidden' && style.display !== 'none';",
					logo);

			if (screenshot) {
				registrarEvidenciaLogo("El logotipo Plataforma COMETA conserva proporciones consistentes con la referencia oficial. " + obtenerDiagnosticoLogo(logo),
						"Proporcion_Logo_Plataforma_COMETA");
			}

			return Boolean.TRUE.equals(proporcionCorrecta);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar proporcion del logotipo Plataforma COMETA: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Espera a que la solapa superior del portal este presente y sin overlays de carga visibles.
	 * Este paso evita tomar evidencia mientras la pantalla aun se encuentra consultando o cargando.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-16
	 */
	private void esperarCargaSolapaSuperior() {
		WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(35));
		wait.until(ExpectedConditions.presenceOfElementLocated(elements.solapaSuperiorPortalCometa));
		try {
			new WebDriverWait(u.getDriver(), Duration.ofSeconds(8)).until(ExpectedConditions.invisibilityOfElementLocated(elements.overlaysCarga));
		} catch (Exception e) {
			System.out.println("No se detecto overlay de carga visible en solapa superior: " + e.getMessage());
		}
	}

	/**
	 * Localiza el candidato de logotipo en la solapa superior usando texto, atributos y proporcion visual.
	 * Prioriza el selector observado en DevTools: header .top-bar img.logo con src portal-cometa-logo.
	 *
	 * @return elemento WebElement candidato al logotipo o null si no existe un candidato visible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-16
	 */
	private WebElement localizarLogoNuevaIdentidadVisual() {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(elements.logoNuevaIdentidadVisual));
			if (logo != null && logo.isDisplayed()) {
				return logo;
			}
		} catch (Exception e) {
			System.out.println("No se localizo el logo por selector directo img.logo: " + e.getMessage());
		}

		Object candidato = ((JavascriptExecutor) u.getDriver()).executeScript(
				"const normalizar = value => (value || '').toString().normalize('NFD').replace(/[\\u0300-\\u036f]/g,'').toLowerCase();"
						+ "const visible = el => { const rect = el.getBoundingClientRect(); const style = getComputedStyle(el); return rect.width > 0 && rect.height > 0 && style.display !== 'none' && style.visibility !== 'hidden' && style.opacity !== '0'; };"
						+ "const roots = Array.from(document.querySelectorAll('header .top-bar, app-header, header, nav, mat-toolbar, .navbar')).filter(visible);"
						+ "const root = roots.length ? roots[0] : document.body;"
						+ "const candidatos = Array.from(root.querySelectorAll('img, svg, a, span, div')).filter(visible).map(el => {"
						+ " const rect = el.getBoundingClientRect();"
						+ " const texto = normalizar([el.innerText, el.getAttribute('alt'), el.getAttribute('title'), el.getAttribute('aria-label'), el.getAttribute('src'), el.getAttribute('href'), el.className].join(' '));"
						+ " const tag = el.tagName.toLowerCase();"
						+ " const contienePlataforma = texto.includes('plataforma');"
						+ " const contieneCometa = texto.includes('cometa');"
						+ " const esLogoHeader = texto.includes('logo') && texto.includes('portal-cometa-logo');"
						+ " const esImagen = tag === 'img' || tag === 'svg' || el.querySelector('img, svg') !== null;"
						+ " const ratio = rect.height > 0 ? rect.width / rect.height : 0;"
						+ " const proporcionOficial = ratio >= 3.5 && ratio <= 6.0 && rect.width >= 120 && rect.height >= 20;"
						+ " const score = (esLogoHeader ? 8 : 0) + (contienePlataforma ? 5 : 0) + (contieneCometa ? 4 : 0) + (esImagen ? 2 : 0) + (proporcionOficial ? 2 : 0);"
						+ " return { el, score };"
						+ "}).filter(item => item.score >= 6).sort((a, b) => b.score - a.score);"
						+ "return candidatos.length ? candidatos[0].el : null;");

		if (candidato instanceof WebElement) {
			return (WebElement) candidato;
		}

		return null;
	}

	/**
	 * Espera a que el archivo de imagen del logotipo este cargado y pintado con dimensiones visibles.
	 * Esta espera evita capturar evidencia cuando Selenium ya ve el elemento pero el navegador aun no termina de renderizar la imagen.
	 *
	 * @param logo elemento img.logo localizado en la solapa superior.
	 * @return true si la imagen esta completa, con dimensiones naturales y visibles en pantalla.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-16
	 */
	private boolean esperarRenderLogoNuevaIdentidadVisual(WebElement logo) {
		long tiempoFin = System.currentTimeMillis() + 15000;
		while (System.currentTimeMillis() < tiempoFin) {
			Boolean renderizado = (Boolean) ((JavascriptExecutor) u.getDriver()).executeScript(
					"const el = arguments[0];"
							+ "const rect = el.getBoundingClientRect();"
							+ "const style = getComputedStyle(el);"
							+ "const naturalWidth = el.naturalWidth || rect.width;"
							+ "const naturalHeight = el.naturalHeight || rect.height;"
							+ "return el.complete !== false && naturalWidth > 0 && naturalHeight > 0 && rect.width >= 120 && rect.height >= 20 && style.visibility !== 'hidden' && style.display !== 'none' && style.opacity !== '0';",
					logo);

			if (Boolean.TRUE.equals(renderizado)) {
				return true;
			}

			u.hardWait(1);
		}

		return false;
	}

	/**
	 * Obtiene informacion tecnica del logotipo encontrado para anexarla a la evidencia.
	 * Incluye dimensiones visibles, relacion de aspecto y atributos relevantes disponibles en el DOM.
	 *
	 * @param logo elemento candidato al logotipo de Plataforma COMETA.
	 * @return descripcion diagnostica del logotipo localizado.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-16
	 */
	private String obtenerDiagnosticoLogo(WebElement logo) {
		Object diagnostico = ((JavascriptExecutor) u.getDriver()).executeScript(
				"const el = arguments[0];"
						+ "const rect = el.getBoundingClientRect();"
						+ "const ratio = rect.height > 0 ? (rect.width / rect.height).toFixed(2) : '0';"
						+ "return 'Dimensiones visibles: ' + Math.round(rect.width) + 'x' + Math.round(rect.height) + ' px | Ratio: ' + ratio + ' | alt: ' + (el.getAttribute('alt') || '') + ' | title: ' + (el.getAttribute('title') || '') + ' | aria-label: ' + (el.getAttribute('aria-label') || '') + ' | src: ' + (el.getAttribute('src') || '');",
				logo);

		return diagnostico == null ? "Sin diagnostico disponible" : diagnostico.toString();
	}

	/**
	 * Obtiene informacion de apoyo de la solapa superior cuando no se encuentra el logotipo esperado.
	 * Registra texto visible y cantidad de imagenes disponibles para facilitar el ajuste del selector si cambia el DOM.
	 *
	 * @return descripcion diagnostica de la solapa superior.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-16
	 */
	private String obtenerDiagnosticoSolapaSuperior() {
		Object diagnostico = ((JavascriptExecutor) u.getDriver()).executeScript(
				"const root = document.querySelector('header .top-bar, app-header, header, nav, mat-toolbar, .navbar') || document.body;"
						+ "const texto = (root.innerText || '').replace(/\\s+/g, ' ').trim();"
						+ "const imagenes = Array.from(root.querySelectorAll('img')).map(img => '[' + (img.getAttribute('alt') || '') + ' | ' + (img.getAttribute('src') || '') + ']').join(' ');"
						+ "return 'Texto visible: ' + texto + ' | Imagenes: ' + imagenes; ");

		return diagnostico == null ? "Sin diagnostico disponible" : diagnostico.toString();
	}

	/**
	 * Prepara el logotipo en el viewport, lo resalta y captura evidencia visual del encabezado.
	 * El metodo fuerza el scroll al inicio para que la solapa superior y la imagen queden dentro de la captura.
	 *
	 * @param mensajeEvidencia texto descriptivo para la evidencia.
	 * @param nombreCaptura nombre del screenshot que se anexara al reporte.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-16
	 */
	private void registrarEvidenciaLogo(String mensajeEvidencia, String nombreCaptura) {
		try {
			prepararLogoParaEvidencia();
			u.highLight(elements.logoNuevaIdentidadVisual);
			u.hardWait(1);
			testCase.addTextEvidenceCurrentStep(mensajeEvidencia);
			testCase.addScreenShotCurrentStep(u, nombreCaptura);
		} finally {
			u.unhighLight(elements.logoNuevaIdentidadVisual);
		}
	}

	/**
	 * Ubica el viewport en la parte superior del portal y centra visualmente el logotipo antes de capturar pantalla.
	 * Tambien espera brevemente para permitir que el navegador pinte el resaltado aplicado por SeleniumUtil.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-16
	 */
	private void prepararLogoParaEvidencia() {
		u.scrollToElement(elements.logoNuevaIdentidadVisual);
		u.hardWait(1);
	}

	/**
	 * Da clic en la opcion FAQS CEDIS desde la navegacion superior del portal COMETA.
	 * Se usa para validar que la identidad visual se mantenga tambien en la pantalla de FAQS CEDIS.
	 *
	 * @param screenshot true para registrar evidencia visual del boton antes del clic, false en caso contrario.
	 * @return true si el boton FAQS CEDIS se localiza y se ejecuta el clic correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-16
	 */
	public boolean clicFaqsCedis(boolean screenshot) {
		try {
			u.waitWebElement(elements.btnFaqsCedis, 30, 5);
			if (u.isDisplayed(elements.btnFaqsCedis)) {
				u.highLight(elements.btnFaqsCedis);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se visualiza la opcion FAQS CEDIS en la navegacion superior del portal COMETA.");
					testCase.addScreenShotCurrentStep(u, "Opcion_FAQS_CEDIS");
				}
				u.unhighLight(elements.btnFaqsCedis);
				u.click(elements.btnFaqsCedis);
				u.waitForLoadPage();
				u.hardWait(1);
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al ingresar a FAQS CEDIS: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Da clic en la opcion FAQS Directos desde la navegacion superior del portal COMETA.
	 * Se usa para validar que la identidad visual se mantenga tambien en la pantalla de FAQS Directos.
	 *
	 * @param screenshot true para registrar evidencia visual del boton antes del clic, false en caso contrario.
	 * @return true si el boton FAQS Directos se localiza y se ejecuta el clic correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	public boolean clicFaqsDirectos(boolean screenshot) {
		try {
			u.waitWebElement(elements.btnFaqsDirectos, 30, 5);
			if (u.isDisplayed(elements.btnFaqsDirectos)) {
				u.highLight(elements.btnFaqsDirectos);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se visualiza la opcion FAQS Directos en la navegacion superior del portal COMETA.");
					testCase.addScreenShotCurrentStep(u, "Opcion_FAQS_Directos");
				}
				u.unhighLight(elements.btnFaqsDirectos);
				u.click(elements.btnFaqsDirectos);
				u.waitForLoadPage();
				u.hardWait(1);
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al ingresar a FAQS Directos: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Activa el grid de Recepcion CEDIS despues de ejecutar una consulta.
	 * Se usa cuando el portal tarda en pintar los resultados y requiere interaccion sobre el grid antes de accionar botones de fila.
	 *
	 * @param screenshot true para registrar evidencia visual del grid, false en caso contrario.
	 * @return true si el grid de resultados queda visible y se pudo interactuar con el.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	public boolean activarGridRecepcionCedis(boolean screenshot) {
		try {
			boolean gridVisible = false;
			for (int intento = 1; intento <= 90; intento++) {
				try {
					if (u.isDisplayed(elements.gridRecepcionCedis)) {
						gridVisible = true;
						break;
					}
				} catch (Exception ignore) {
				}
				u.hardWait(1);
			}

			if (!gridVisible) {
				testCase.addTextEvidenceCurrentStep("El grid de Recepcion CEDIS no se visualizo dentro del tiempo esperado despues de consultar.");
				return false;
			}

			if (u.isDisplayed(elements.gridRecepcionCedis)) {
				u.scrollToElement(elements.gridRecepcionCedis);
				u.highLight(elements.gridRecepcionCedis);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se visualiza el grid de Recepcion CEDIS despues de la consulta.");
					testCase.addScreenShotCurrentStep(u, "Grid_Recepcion_CEDIS_Consultado");
				}
				u.click(elements.gridRecepcionCedis);
				u.hardWait(2);
				u.unhighLight(elements.gridRecepcionCedis);
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al activar el grid de Recepcion CEDIS: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Da clic en el boton FUE del primer registro del grid de Recepcion CEDIS.
	 * Esta accion abre la vista preliminar FUE como alternativa al boton Formato de Verificacion.
	 *
	 * @param screenshot true para registrar evidencia visual del boton FUE, false en caso contrario.
	 * @return true si el boton FUE se localiza y se ejecuta el clic correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	public boolean clicFuePrimerRegistroCedis(boolean screenshot) {
		try {
			u.waitWebElement(elements.btnFuePrimerRegistroCedis, 45, 5);
			if (u.isDisplayed(elements.btnFuePrimerRegistroCedis)) {
				u.scrollToElement(elements.btnFuePrimerRegistroCedis);
				u.highLight(elements.btnFuePrimerRegistroCedis);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se selecciona el boton FUE del primer registro de Recepcion CEDIS.");
					testCase.addScreenShotCurrentStep(u, "Boton_FUE_Primer_Registro_CEDIS");
				}
				u.unhighLight(elements.btnFuePrimerRegistroCedis);
				u.click(elements.btnFuePrimerRegistroCedis);
				u.hardWait(5);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se muestra la vista preliminar FUE del registro seleccionado.");
					testCase.addScreenShotCurrentStep(u, "Vista_Preliminar_FUE_CEDIS");
				}
				u.waitWebElement(elements.btnCerrarModalFue, 30, 5);
				u.highLight(elements.btnCerrarModalFue);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se visualiza el icono X para cerrar la vista preliminar FUE.");
					testCase.addScreenShotCurrentStep(u, "Cerrar_Vista_Preliminar_FUE_CEDIS");
				}
				u.unhighLight(elements.btnCerrarModalFue);
				u.click(elements.btnCerrarModalFue);
				u.hardWait(1);
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al abrir FUE del primer registro CEDIS: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Cierra una vista preliminar abierta desde un registro de grid usando el icono X del modal.
	 * Aplica para modales basados en app-detalle como Comprobantes de Compra y otros detalles de grid.
	 *
	 * @param screenshot true para registrar evidencia visual del icono X antes del cierre, false en caso contrario.
	 * @return true si el icono X se localiza y se ejecuta el cierre correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	public boolean cerrarVistaPreliminarGrid(boolean screenshot) {
		try {
			u.waitWebElement(elements.btnCerrarModalGrid, 30, 5);
			if (u.isDisplayed(elements.btnCerrarModalGrid)) {
				u.highLight(elements.btnCerrarModalGrid);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se visualiza el icono X para cerrar la vista preliminar del grid.");
					testCase.addScreenShotCurrentStep(u, "Cerrar_Vista_Preliminar_Grid");
				}
				u.unhighLight(elements.btnCerrarModalGrid);
				u.click(elements.btnCerrarModalGrid);
				u.hardWait(1);
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al cerrar la vista preliminar del grid: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que la pantalla Devoluciones de Directos quede disponible sin depender del id dinamico del datepicker.
	 * Confirma el titulo de la pantalla y el bloque de filtros principales antes de continuar con la consulta.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la pantalla Devoluciones esta visible con sus filtros.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	public boolean validarPantallaDevolucionesDirectos(boolean screenshot) {
		try {
			u.waitWebElement(elements.tituloDevolucionesDirectos, 30, 5);
			u.waitWebElement(elements.filtrosDevolucionesDirectos, 30, 5);
			if (u.isDisplayed(elements.tituloDevolucionesDirectos) && u.isDisplayed(elements.filtrosDevolucionesDirectos)) {
				u.highLight(elements.tituloDevolucionesDirectos);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se muestra la pantalla Devoluciones de Directos con el bloque de filtros disponible.");
					testCase.addScreenShotCurrentStep(u, "Pantalla_Devoluciones_Directos");
				}
				u.unhighLight(elements.tituloDevolucionesDirectos);
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar pantalla Devoluciones de Directos: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Da clic en la primera fila visible del grid de Devoluciones Directos y valida que se abra una vista preliminar.
	 * Se usa cuando el caso solo requiere comprobar que la navegacion y apertura del detalle funcionen.
	 *
	 * @param screenshot true para registrar evidencia visual de la fila y del detalle, false en caso contrario.
	 * @return true si se localiza la primera fila y se ejecuta el clic correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	public boolean abrirPrimerRegistroGridDevoluciones(boolean screenshot) {
		try {
			u.waitWebElement(elements.primeraFilaGridDevoluciones, 60, 5);
			if (u.isDisplayed(elements.primeraFilaGridDevoluciones)) {
				u.scrollToElement(elements.primeraFilaGridDevoluciones);
				u.highLight(elements.primeraFilaGridDevoluciones);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se selecciona la primera fila visible del grid de Devoluciones.");
					testCase.addScreenShotCurrentStep(u, "Fila_Grid_Devoluciones");
				}
				u.unhighLight(elements.primeraFilaGridDevoluciones);
				u.click(elements.primeraFilaGridDevoluciones);
				u.hardWait(5);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se muestra la vista preliminar de Devoluciones del registro seleccionado.");
					testCase.addScreenShotCurrentStep(u, "Vista_Preliminar_Devoluciones");
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al abrir primer registro del grid de Devoluciones: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que la pantalla Cambios Fisicos de Directos quede disponible sin depender del id dinamico del datepicker.
	 * Confirma el titulo de la pantalla y el bloque de filtros principales antes de continuar con la consulta.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la pantalla Cambios Fisicos esta visible con sus filtros.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	public boolean validarPantallaCambiosFisicosDirectos(boolean screenshot) {
		try {
			u.waitWebElement(elements.tituloCambiosFisicosDirectos, 30, 5);
			u.waitWebElement(elements.filtrosCambiosFisicosDirectos, 30, 5);
			if (u.isDisplayed(elements.tituloCambiosFisicosDirectos) && u.isDisplayed(elements.filtrosCambiosFisicosDirectos)) {
				u.highLight(elements.tituloCambiosFisicosDirectos);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Se muestra la pantalla Cambios Fisicos de Directos con el bloque de filtros disponible.");
					testCase.addScreenShotCurrentStep(u, "Pantalla_Cambios_Fisicos_Directos");
				}
				u.unhighLight(elements.tituloCambiosFisicosDirectos);
				return true;
			}
			return false;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar pantalla Cambios Fisicos de Directos: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Consulta Devoluciones Directos usando la fecha mas reciente disponible en xxcm_dv.
	 * La seleccion de fecha se realiza sobre los dias visibles del calendario para evitar fallas al navegar por anio.
	 *
	 * @param usuario usuario con el que se obtiene perfil y datos de consulta desde BD.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se obtiene fecha, se selecciona el rango, se ejecuta la consulta y se visualiza el grid.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	public boolean busquedaFiltroFechaDevolucionesDirectos(String usuario, boolean screenshot) {
		try {
			LocalDate fechaConvert = obtenerFechaDevolucionesDirectos(usuario, screenshot);
			if (fechaConvert == null) {
				return false;
			}

			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.elementToBeClickable(elements.btnCalendarioDevolucionesDirectos)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.contenedorDatepicker));
			navegarMesVisibleDatepicker(driver, wait, fechaConvert);

			int diaInicio = fechaConvert.minusDays(1).getDayOfMonth();
			int diaFinal = fechaConvert.getDayOfMonth();
			seleccionarDiaVisibleDatepicker(driver, wait, diaInicio);
			u.hardWait(1);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.contenedorDatepicker));
			seleccionarDiaVisibleDatepicker(driver, wait, diaFinal);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.contenedorDatepicker));

			u.waitWebElement(elements.btnConsultarDevolucionesDirectos, 30, 5);
			WebElement botonConsultar = wait.until(ExpectedConditions.elementToBeClickable(elements.btnConsultarDevolucionesDirectos));
			u.highLight(elements.btnConsultarDevolucionesDirectos);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se selecciona rango de fechas en Devoluciones Directos: " + fechaConvert.minusDays(1) + " a " + fechaConvert);
				testCase.addScreenShotCurrentStep(u, "Consulta_Devoluciones_Directos_Fecha");
			}
			u.unhighLight(elements.btnConsultarDevolucionesDirectos);
			try {
				u.click(elements.btnConsultarDevolucionesDirectos);
			} catch (Exception clickInterceptado) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", botonConsultar);
			}
			u.hardWait(8);
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al consultar Devoluciones Directos por fecha: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Consulta Cambios Fisicos Directos usando la fecha mas reciente disponible en xxcm_cf.
	 * La seleccion de fecha se realiza sobre los dias visibles del calendario para evitar dependencia de fechas fijas.
	 *
	 * @param usuario usuario con el que se obtiene perfil y datos de consulta desde BD.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se obtiene fecha, se selecciona el rango, se ejecuta la consulta y se visualiza el grid.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	public boolean busquedaFiltroFechaCambiosFisicosDirectos(String usuario, boolean screenshot) {
		try {
			LocalDate fechaConvert = obtenerFechaCambiosFisicosDirectos(usuario, screenshot);
			if (fechaConvert == null) {
				return false;
			}

			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.elementToBeClickable(elements.btnCalendarioCambiosFisicosDirectos)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.contenedorDatepicker));
			navegarMesVisibleDatepicker(driver, wait, fechaConvert);

			int diaInicio = fechaConvert.minusDays(1).getDayOfMonth();
			int diaFinal = fechaConvert.getDayOfMonth();
			seleccionarDiaVisibleDatepicker(driver, wait, diaInicio);
			u.hardWait(1);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.contenedorDatepicker));
			seleccionarDiaVisibleDatepicker(driver, wait, diaFinal);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.contenedorDatepicker));

			u.waitWebElement(elements.btnConsultarCambiosFisicosDirectos, 30, 5);
			WebElement botonConsultar = wait.until(ExpectedConditions.elementToBeClickable(elements.btnConsultarCambiosFisicosDirectos));
			u.highLight(elements.btnConsultarCambiosFisicosDirectos);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se selecciona rango de fechas en Cambios Fisicos Directos: " + fechaConvert.minusDays(1) + " a " + fechaConvert);
				testCase.addScreenShotCurrentStep(u, "Consulta_Cambios_Fisicos_Directos_Fecha");
			}
			u.unhighLight(elements.btnConsultarCambiosFisicosDirectos);
			try {
				u.click(elements.btnConsultarCambiosFisicosDirectos);
			} catch (Exception clickInterceptado) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", botonConsultar);
			}
			u.hardWait(8);
			return activarGridCambiosFisicosDirectos(screenshot);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al consultar Cambios Fisicos Directos por fecha: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Espera y activa el grid de Cambios Fisicos Directos despues de ejecutar una consulta.
	 *
	 * @param screenshot true para registrar evidencia visual del grid, false en caso contrario.
	 * @return true si el grid muestra al menos una fila visible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	private boolean activarGridCambiosFisicosDirectos(boolean screenshot) {
		try {
			boolean gridVisible = false;
			for (int intento = 1; intento <= 90; intento++) {
				try {
					if (u.isDisplayed(elements.primeraFilaGridCambiosFisicos)) {
						gridVisible = true;
						break;
					}
				} catch (Exception ignore) {
				}
				u.hardWait(1);
			}

			if (!gridVisible) {
				testCase.addTextEvidenceCurrentStep("El grid de Cambios Fisicos Directos no se visualizo dentro del tiempo esperado despues de consultar.");
				return false;
			}

			u.scrollToElement(elements.primeraFilaGridCambiosFisicos);
			u.highLight(elements.primeraFilaGridCambiosFisicos);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se visualiza el grid de Cambios Fisicos Directos despues de la consulta.");
				testCase.addScreenShotCurrentStep(u, "Grid_Cambios_Fisicos_Directos_Consultado");
			}
			u.unhighLight(elements.primeraFilaGridCambiosFisicos);
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al activar el grid de Cambios Fisicos Directos: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Espera y activa el grid de Devoluciones Directos despues de ejecutar una consulta.
	 *
	 * @param screenshot true para registrar evidencia visual del grid, false en caso contrario.
	 * @return true si el grid muestra al menos una fila visible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	private boolean activarGridDevolucionesDirectos(boolean screenshot) {
		try {
			boolean gridVisible = false;
			for (int intento = 1; intento <= 90; intento++) {
				try {
					if (u.isDisplayed(elements.primeraFilaGridDevoluciones)) {
						gridVisible = true;
						break;
					}
				} catch (Exception ignore) {
				}
				u.hardWait(1);
			}

			if (!gridVisible) {
				testCase.addTextEvidenceCurrentStep("El grid de Devoluciones Directos no se visualizo dentro del tiempo esperado despues de consultar.");
				return false;
			}

			u.scrollToElement(elements.primeraFilaGridDevoluciones);
			u.highLight(elements.primeraFilaGridDevoluciones);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se visualiza el grid de Devoluciones Directos despues de la consulta.");
				testCase.addScreenShotCurrentStep(u, "Grid_Devoluciones_Directos_Consultado");
			}
			u.unhighLight(elements.primeraFilaGridDevoluciones);
			return true;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al activar el grid de Devoluciones Directos: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Consulta Recepcion CEDIS usando la fecha mas reciente disponible en xxcm_historico_cedis_view.
	 * La seleccion de fecha se realiza sobre el mes visible del calendario para evitar fallas al navegar por anio.
	 *
	 * @param usuario usuario con el que se obtiene perfil y datos de consulta desde BD.
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si se obtiene fecha, se selecciona el rango y se ejecuta la consulta correctamente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	public boolean busquedaFiltroFechaRecepcionCedis(String usuario, boolean screenshot) {
		try {
			LocalDate fechaConvert = obtenerFechaRegistroRecepcionCedis(usuario, screenshot);
			if (fechaConvert == null) {
				return false;
			}

			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.elementToBeClickable(elements.btnCalendarioRecepcionCedis)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.contenedorDatepicker));

			int diaInicio = fechaConvert.minusDays(1).getDayOfMonth();
			int diaFinal = fechaConvert.getDayOfMonth();
			seleccionarDiaVisibleDatepicker(driver, wait, diaInicio);
			u.hardWait(1);
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.contenedorDatepicker));
			seleccionarDiaVisibleDatepicker(driver, wait, diaFinal);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(elements.contenedorDatepicker));

			u.waitWebElement(elements.btnConsultarRecepcionCedis, 30, 5);
			u.scrollToElement(elements.btnConsultarRecepcionCedis);
			u.highLight(elements.btnConsultarRecepcionCedis);
			if (screenshot) {
				testCase.addTextEvidenceCurrentStep("Se selecciona rango de fechas en Recepcion CEDIS: " + fechaConvert.minusDays(1) + " a " + fechaConvert);
				testCase.addScreenShotCurrentStep(u, "Consulta_Recepcion_CEDIS_Fecha");
			}
			u.unhighLight(elements.btnConsultarRecepcionCedis);
			u.click(elements.btnConsultarRecepcionCedis);
			u.hardWait(8);
			return activarGridRecepcionCedis(screenshot);
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al consultar Recepcion CEDIS por fecha: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Obtiene la fecha_registro mas reciente para Recepcion CEDIS segun el perfil del usuario.
	 * Para ADMIN y OXXO_OS toma la fecha global mas reciente; para CEDIS filtra por supplier_id.
	 *
	 * @param usuario usuario en prueba.
	 * @param screenshot true para registrar evidencia de la fecha obtenida.
	 * @return fecha obtenida desde BD o null si no existe informacion suficiente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	private LocalDate obtenerFechaRegistroRecepcionCedis(String usuario, boolean screenshot) {
		String queryPerfil = "select key_value from xxcm_user_keys where short_name = 'PCPERFIL' and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) limit 1";
		String querySupplierId = "select key_value from xxcm_user_keys where short_name = 'PCSUPPLIERID' and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) limit 1";
		String queryFechaAdmin = "select fecha_registro from xxcm_historico_cedis_view where fecha_registro is not null order by fecha_registro desc limit 1";
		String queryFechaCedis = "select fecha_registro from xxcm_historico_cedis_view where supplier_id = ? and fecha_registro is not null order by fecha_registro desc limit 1";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {
			String perfil = "CEDIS";
			try (PreparedStatement psPerfil = connection.prepareStatement(queryPerfil)) {
				psPerfil.setString(1, usuario);
				try (ResultSet rsPerfil = psPerfil.executeQuery()) {
					if (rsPerfil.next() && rsPerfil.getString("key_value") != null && !rsPerfil.getString("key_value").trim().isEmpty()) {
						perfil = rsPerfil.getString("key_value");
					}
				}
			}

			if (screenshot) {
				testCase.addBoldTextEvidenceCurrentStep("Usuario con perfil: " + perfil);
			}
			System.out.println("Usuario con perfil: " + perfil);

			boolean esAdmin = "ADMIN".equalsIgnoreCase(perfil) || "OXXO_OS".equalsIgnoreCase(perfil);
			if (esAdmin) {
				return obtenerFechaRegistro(connection, queryFechaAdmin, screenshot, "Fecha obtenida de xxcm_historico_cedis_view: ");
			}

			Long supplierId = null;
			try (PreparedStatement psSupplier = connection.prepareStatement(querySupplierId)) {
				psSupplier.setString(1, usuario);
				try (ResultSet rsSupplier = psSupplier.executeQuery()) {
					if (rsSupplier.next() && rsSupplier.getString("key_value") != null && !rsSupplier.getString("key_value").trim().isEmpty()) {
						supplierId = Long.valueOf(rsSupplier.getString("key_value").trim());
					}
				}
			}

			if (supplierId == null) {
				testCase.addTextEvidenceCurrentStep("No se obtuvo SupplierId para el usuario: " + usuario);
				return null;
			}

			try (PreparedStatement psFecha = connection.prepareStatement(queryFechaCedis)) {
				psFecha.setLong(1, supplierId);
				try (ResultSet rsFecha = psFecha.executeQuery()) {
					return leerFechaRegistro(rsFecha, screenshot, "Fecha obtenida de xxcm_historico_cedis_view para supplier_id " + supplierId + ": ");
				}
			}
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al obtener fecha de Recepcion CEDIS: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Obtiene la fecha mas reciente de Devoluciones Directos desde xxcm_dv segun el perfil del usuario.
	 * Para ADMIN y OXXO_OS toma la fecha global mas reciente; para otros perfiles filtra por plaza o RFC.
	 *
	 * @param usuario usuario en prueba.
	 * @param screenshot true para registrar evidencia de la fecha obtenida.
	 * @return fecha obtenida desde BD o null si no existe informacion suficiente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	private LocalDate obtenerFechaDevolucionesDirectos(String usuario, boolean screenshot) {
		String queryPerfil = "select key_value from xxcm_user_keys where short_name = 'PCPERFIL' and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) limit 1";
		String queryKey = "select key_value from xxcm_user_keys where short_name in ('PCRFC', 'PCSUPPLIERID', 'PCPLAZA') and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) limit 1";
		String queryFechaAdmin = "select fecha from xxcm_dv order by fecha desc limit 1";
		String queryFechaPlaza = "select fecha from xxcm_dv where cr_desc_plaza = ? order by fecha desc limit 1";
		String queryFechaRfc = "select fecha from xxcm_dv where proveedor_rfc = ? order by fecha desc limit 1";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {
			String perfil = "";
			try (PreparedStatement psPerfil = connection.prepareStatement(queryPerfil)) {
				psPerfil.setString(1, usuario);
				try (ResultSet rsPerfil = psPerfil.executeQuery()) {
					if (rsPerfil.next() && rsPerfil.getString("key_value") != null) {
						perfil = rsPerfil.getString("key_value");
					}
				}
			}

			if (screenshot) {
				testCase.addBoldTextEvidenceCurrentStep("Usuario con perfil: " + perfil);
			}
			System.out.println("Usuario con perfil: " + perfil);

			boolean esAdmin = "ADMIN".equalsIgnoreCase(perfil) || "OXXO_OS".equalsIgnoreCase(perfil);
			if (esAdmin) {
				return obtenerFechaDevoluciones(connection, queryFechaAdmin, null, screenshot, "Existen datos en la tabla xxcm_dv. Fecha: ");
			}

			String keyValue = null;
			try (PreparedStatement psKey = connection.prepareStatement(queryKey)) {
				psKey.setString(1, usuario);
				try (ResultSet rsKey = psKey.executeQuery()) {
					if (rsKey.next()) {
						keyValue = rsKey.getString("key_value");
					}
				}
			}

			if (keyValue == null || keyValue.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No se obtuvo key de perfil para consultar Devoluciones Directos del usuario: " + usuario);
				return null;
			}

			String queryFecha = "OXXO_PLAZA".equalsIgnoreCase(perfil) ? queryFechaPlaza : queryFechaRfc;
			return obtenerFechaDevoluciones(connection, queryFecha, keyValue.trim(), screenshot, "Existen datos en la tabla xxcm_dv. Fecha: ");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al obtener fecha de Devoluciones Directos: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Obtiene la fecha mas reciente de Cambios Fisicos Directos desde xxcm_cf segun el perfil del usuario.
	 * Para ADMIN y OXXO_OS toma la fecha global mas reciente; para otros perfiles filtra por plaza o RFC.
	 *
	 * @param usuario usuario en prueba.
	 * @param screenshot true para registrar evidencia de la fecha obtenida.
	 * @return fecha obtenida desde BD o null si no existe informacion suficiente.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	private LocalDate obtenerFechaCambiosFisicosDirectos(String usuario, boolean screenshot) {
		String queryPerfil = "select key_value from xxcm_user_keys where short_name = 'PCPERFIL' and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) limit 1";
		String queryKey = "select key_value from xxcm_user_keys where short_name in ('PCRFC', 'PCSUPPLIERID', 'PCPLAZA') and usuario_id = (select usuario_id from xxcm_usuarios where usuario = ?) limit 1";
		String queryFechaAdmin = "select fecha from xxcm_cf order by fecha desc limit 1";
		String queryFechaPlaza = "select fecha from xxcm_cf where cr_desc_plaza = ? order by fecha desc limit 1";
		String queryFechaRfc = "select fecha from xxcm_cf where proveedor_rfc = ? order by fecha desc limit 1";

		try (Connection connection = DriverManager.getConnection(GlobalVariables.DB_URL_Cometa, GlobalVariables.DB_USER_Cometa, GlobalVariables.DB_PASSWORD_Cometa)) {
			String perfil = "";
			try (PreparedStatement psPerfil = connection.prepareStatement(queryPerfil)) {
				psPerfil.setString(1, usuario);
				try (ResultSet rsPerfil = psPerfil.executeQuery()) {
					if (rsPerfil.next() && rsPerfil.getString("key_value") != null) {
						perfil = rsPerfil.getString("key_value");
					}
				}
			}

			if (screenshot) {
				testCase.addBoldTextEvidenceCurrentStep("Usuario con perfil: " + perfil);
			}
			System.out.println("Usuario con perfil: " + perfil);

			boolean esAdmin = "ADMIN".equalsIgnoreCase(perfil) || "OXXO_OS".equalsIgnoreCase(perfil);
			if (esAdmin) {
				return obtenerFechaDevoluciones(connection, queryFechaAdmin, null, screenshot, "Existen datos en la tabla xxcm_cf. Fecha: ");
			}

			String keyValue = null;
			try (PreparedStatement psKey = connection.prepareStatement(queryKey)) {
				psKey.setString(1, usuario);
				try (ResultSet rsKey = psKey.executeQuery()) {
					if (rsKey.next()) {
						keyValue = rsKey.getString("key_value");
					}
				}
			}

			if (keyValue == null || keyValue.trim().isEmpty()) {
				testCase.addTextEvidenceCurrentStep("No se obtuvo key de perfil para consultar Cambios Fisicos Directos del usuario: " + usuario);
				return null;
			}

			String queryFecha = "OXXO_PLAZA".equalsIgnoreCase(perfil) ? queryFechaPlaza : queryFechaRfc;
			return obtenerFechaDevoluciones(connection, queryFecha, keyValue.trim(), screenshot, "Existen datos en la tabla xxcm_cf. Fecha: ");
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al obtener fecha de Cambios Fisicos Directos: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Ejecuta consulta de fecha sobre xxcm_dv y convierte el resultado a LocalDate.
	 *
	 * @param connection conexion activa a BD Cometa.
	 * @param query consulta SQL a ejecutar.
	 * @param parametro valor opcional para consultas parametrizadas.
	 * @param screenshot true para registrar evidencia.
	 * @param mensaje prefijo del mensaje de evidencia.
	 * @return fecha convertida o null si no hay registros.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	private LocalDate obtenerFechaDevoluciones(Connection connection, String query, String parametro, boolean screenshot, String mensaje) throws Exception {
		try (PreparedStatement psFecha = connection.prepareStatement(query)) {
			if (parametro != null) {
				psFecha.setString(1, parametro);
			}
			try (ResultSet rsFecha = psFecha.executeQuery()) {
				if (!rsFecha.next()) {
					return null;
				}

				Timestamp ts = rsFecha.getTimestamp("fecha");
				if (ts == null) {
					return null;
				}

				LocalDate fechaConvert = ts.toLocalDateTime().toLocalDate();
				if (screenshot) {
					testCase.addBoldTextEvidenceCurrentStep(mensaje + fechaConvert);
				}
				System.out.println(mensaje + fechaConvert);
				return fechaConvert;
			}
		}
	}

	/**
	 * Ejecuta una consulta de fecha sin parametros y obtiene fecha_registro.
	 *
	 * @param connection conexion activa a BD Cometa.
	 * @param query consulta SQL a ejecutar.
	 * @param screenshot true para registrar evidencia.
	 * @param mensaje prefijo del mensaje de evidencia.
	 * @return fecha obtenida o null si no hay registros.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	private LocalDate obtenerFechaRegistro(Connection connection, String query, boolean screenshot, String mensaje) throws Exception {
		try (PreparedStatement psFecha = connection.prepareStatement(query);
				ResultSet rsFecha = psFecha.executeQuery()) {
			return leerFechaRegistro(rsFecha, screenshot, mensaje);
		}
	}

	/**
	 * Lee fecha_registro desde un ResultSet y la convierte a LocalDate.
	 *
	 * @param rsFecha resultado de consulta posicionado antes del primer registro.
	 * @param screenshot true para registrar evidencia.
	 * @param mensaje prefijo del mensaje de evidencia.
	 * @return fecha convertida o null si no hay registro valido.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	private LocalDate leerFechaRegistro(ResultSet rsFecha, boolean screenshot, String mensaje) throws Exception {
		if (!rsFecha.next()) {
			return null;
		}

		Timestamp ts = rsFecha.getTimestamp("fecha_registro");
		if (ts == null) {
			return null;
		}

		LocalDate fechaConvert = ts.toLocalDateTime().toLocalDate();
		if (screenshot) {
			testCase.addBoldTextEvidenceCurrentStep(mensaje + fechaConvert);
		}
		System.out.println(mensaje + fechaConvert);
		return fechaConvert;
	}

	/**
	 * Selecciona un dia visible y habilitado del datepicker abierto.
	 *
	 * @param driver WebDriver activo.
	 * @param wait espera explicita para el calendario.
	 * @param dia dia del mes a seleccionar.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	private void seleccionarDiaVisibleDatepicker(WebDriver driver, WebDriverWait wait, int dia) {
		By btnDia = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//mat-month-view//button[contains(@class,'mat-calendar-body-cell') and not(contains(@class,'mat-calendar-body-disabled')) and .//span[normalize-space()='" + dia + "']]");
		WebElement diaElement = wait.until(ExpectedConditions.elementToBeClickable(btnDia));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", diaElement);
	}

	/**
	 * Navega el datepicker abierto hasta el mes de la fecha objetivo usando los botones anterior/siguiente.
	 * Parte del mes actual visible por defecto en el calendario del portal.
	 *
	 * @param driver WebDriver activo.
	 * @param wait espera explicita para el calendario.
	 * @param fechaObjetivo fecha cuyo mes debe quedar visible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-07-17
	 */
	private void navegarMesVisibleDatepicker(WebDriver driver, WebDriverWait wait, LocalDate fechaObjetivo) {
		YearMonth mesActual = YearMonth.now();
		YearMonth mesObjetivo = YearMonth.from(fechaObjetivo);
		int mesesDiferencia = (mesObjetivo.getYear() - mesActual.getYear()) * 12 + (mesObjetivo.getMonthValue() - mesActual.getMonthValue());
		By btnAnterior = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-previous-button')]");
		By btnSiguiente = By.xpath("//div[contains(@class,'cdk-overlay-pane')]//button[contains(@class,'mat-calendar-next-button')]");

		if (mesesDiferencia < 0) {
			for (int i = 0; i < Math.abs(mesesDiferencia); i++) {
				wait.until(ExpectedConditions.elementToBeClickable(btnAnterior)).click();
				u.hardWait(1);
			}
		} else if (mesesDiferencia > 0) {
			for (int i = 0; i < mesesDiferencia; i++) {
				wait.until(ExpectedConditions.elementToBeClickable(btnSiguiente)).click();
				u.hardWait(1);
			}
		}
	}

	/**
	 * Valida que los encabezados del grid de Cambios Fisicos Pendientes sean visibles tras ejecutar la consulta.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si al menos un encabezado de columna es visible en el grid.
	 *
	 * @author Michelle Ceja
	 * @date 2026-08-06
	 */
	public boolean validarVistaEncabezadosGridCambiosFisicosPendientes(boolean screenshot) {
		try {
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.encabezadosGridCFPendientes));
			List<WebElement> headers = driver.findElements(elements.encabezadosGridCFPendientes);
			boolean visible = headers != null && !headers.isEmpty();
			if (visible) {
				u.scrollToElement(elements.encabezadosGridCFPendientes);
				u.highLight(elements.encabezadosGridCFPendientes);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("El grid de Cambios Fisicos Pendientes muestra " + headers.size() + " encabezados de columnas visibles.");
					testCase.addScreenShotCurrentStep(u, "Encabezados_Grid_CFPendientes_Visibles");
				}
				u.unhighLight(elements.encabezadosGridCFPendientes);
			}
			return visible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar encabezados del grid Cambios Fisicos Pendientes: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el encabezado 'Precio' sea visible en el grid de Cambios Fisicos Pendientes.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si el encabezado 'Precio' existe y es visible en el grid.
	 *
	 * @author Michelle Ceja
	 * @date 2026-08-06
	 */
	public boolean validarEncabezadoPrecioGridCambiosFisicosPendientes(boolean screenshot) {
		try {
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.encabezadoPrecioGridCFPendientes));
			WebElement headerPrecio = driver.findElement(elements.encabezadoPrecioGridCFPendientes);
			boolean visible = headerPrecio != null && headerPrecio.isDisplayed()
					&& headerPrecio.getText().trim().equalsIgnoreCase("Precio");
			if (visible) {
				u.scrollToElement(elements.encabezadoPrecioGridCFPendientes);
				u.highLight(elements.encabezadoPrecioGridCFPendientes);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("El encabezado 'Precio' se visualiza en el grid de Cambios Fisicos Pendientes.");
					testCase.addScreenShotCurrentStep(u, "Encabezado_Precio_Grid_CFPendientes");
				}
				u.unhighLight(elements.encabezadoPrecioGridCFPendientes);
			} else {
				testCase.addTextEvidenceCurrentStep("El encabezado 'Precio' no se encontro en el grid de Cambios Fisicos Pendientes.");
			}
			return visible;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar encabezado Precio en grid Cambios Fisicos Pendientes: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que la columna 'Precio' este ubicada inmediatamente a la derecha de la columna 'Costo' en el grid de Cambios Fisicos Pendientes.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si la posicion de Precio es exactamente la posicion de Costo + 1.
	 *
	 * @author Michelle Ceja
	 * @date 2026-08-06
	 */
	public boolean validarPrecioInmediatamenteDespuesDeCosto(boolean screenshot) {
		try {
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.encabezadosGridCFPendientes));
			List<WebElement> headers = driver.findElements(elements.encabezadosGridCFPendientes);
			int posCosto = -1;
			int posPrecio = -1;
			for (int i = 0; i < headers.size(); i++) {
				String texto = headers.get(i).getText().trim();
				if (texto.equalsIgnoreCase("Costo")) posCosto = i;
				if (texto.equalsIgnoreCase("Precio")) posPrecio = i;
			}
			boolean resultado = posCosto >= 0 && posPrecio >= 0 && (posPrecio == posCosto + 1);
			if (resultado) {
				u.scrollToElement(elements.encabezadoPrecioGridCFPendientes);
				u.highLight(elements.encabezadoPrecioGridCFPendientes);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("La columna 'Precio' (posicion " + (posPrecio + 1) + ") esta inmediatamente despues de 'Costo' (posicion " + (posCosto + 1) + ").");
					testCase.addScreenShotCurrentStep(u, "Precio_InmediatamenteDespues_Costo");
				}
				u.unhighLight(elements.encabezadoPrecioGridCFPendientes);
			} else {
				testCase.addTextEvidenceCurrentStep("Posicion Costo=" + (posCosto + 1) + " | Posicion Precio=" + (posPrecio + 1) + ". La columna Precio NO esta inmediatamente despues de Costo.");
			}
			return resultado;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar posicion de columna Precio respecto a Costo: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que la columna Precio del grid de Cambios Fisicos Pendientes contenga al menos un valor visible no vacio.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si al menos una celda de la columna Precio tiene contenido visible.
	 *
	 * @author Michelle Ceja
	 * @date 2026-08-06
	 */
	public boolean validarValoresPrecioVisiblesEnGrid(boolean screenshot) {
		try {
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.celdasPrecioGridCFPendientes));
			List<WebElement> celdas = driver.findElements(elements.celdasPrecioGridCFPendientes);
			boolean conValores = celdas != null && !celdas.isEmpty()
					&& celdas.stream().anyMatch(c -> !c.getText().trim().isEmpty());
			if (conValores) {
				u.scrollToElement(elements.encabezadoPrecioGridCFPendientes);
				u.highLight(elements.celdasPrecioGridCFPendientes);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("La columna Precio muestra " + celdas.size() + " valores visibles en el grid.");
					testCase.addScreenShotCurrentStep(u, "Valores_Precio_Visibles_Grid");
				}
				u.unhighLight(elements.celdasPrecioGridCFPendientes);
			} else {
				testCase.addTextEvidenceCurrentStep("No se identificaron valores en la columna Precio del grid.");
			}
			return conValores;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar valores de columna Precio en grid: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que todos los valores no vacios de la columna Precio en el grid incluyan el simbolo $.
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si todos los valores visibles de la columna Precio contienen el simbolo $.
	 *
	 * @author Michelle Ceja
	 * @date 2026-08-06
	 */
	public boolean validarSimboloPesosEnColumnaPrecioGrid(boolean screenshot) {
		try {
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.celdasPrecioGridCFPendientes));
			List<WebElement> celdas = driver.findElements(elements.celdasPrecioGridCFPendientes);
			boolean conSimbolo = celdas != null && celdas.stream()
					.map(c -> c.getText().trim())
					.filter(t -> !t.isEmpty())
					.allMatch(t -> t.contains("$"));
			if (conSimbolo) {
				u.scrollToElement(elements.encabezadoPrecioGridCFPendientes);
				u.highLight(elements.celdasPrecioGridCFPendientes);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Todos los valores visibles de la columna Precio incluyen el simbolo $.");
					testCase.addScreenShotCurrentStep(u, "Simbolo_Pesos_Columna_Precio");
				}
				u.unhighLight(elements.celdasPrecioGridCFPendientes);
			} else {
				testCase.addTextEvidenceCurrentStep("Al menos un valor de la columna Precio no incluye el simbolo $.");
			}
			return conSimbolo;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar simbolo $ en columna Precio: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Valida que todos los valores no vacios de la columna Precio en el grid cumplan el formato monetario ($#,###.##).
	 *
	 * @param screenshot true para registrar evidencia visual, false en caso contrario.
	 * @return true si todos los valores visibles de la columna Precio coinciden con el patron $[digitos,].##.
	 *
	 * @author Michelle Ceja
	 * @date 2026-08-06
	 */
	public boolean validarFormatoDecimalColumnaPrecioGrid(boolean screenshot) {
		try {
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.visibilityOfElementLocated(elements.celdasPrecioGridCFPendientes));
			List<WebElement> celdas = driver.findElements(elements.celdasPrecioGridCFPendientes);
			// acepta espacio opcional entre $ y digitos: "$ 35.00" o "$35.00"
			Pattern patronMonetario = Pattern.compile("^\\$\\s*[\\d,]+\\.\\d{2}$");
			boolean formatoCorrecto = celdas != null && celdas.stream()
					.map(c -> c.getText().trim())
					.filter(t -> !t.isEmpty())
					.allMatch(t -> patronMonetario.matcher(t).matches());
			if (formatoCorrecto) {
				u.scrollToElement(elements.encabezadoPrecioGridCFPendientes);
				u.highLight(elements.celdasPrecioGridCFPendientes);
				if (screenshot) {
					testCase.addTextEvidenceCurrentStep("Todos los valores visibles de la columna Precio cumplen el formato monetario ($#,###.##).");
					testCase.addScreenShotCurrentStep(u, "Formato_Decimal_Columna_Precio");
				}
				u.unhighLight(elements.celdasPrecioGridCFPendientes);
			} else {
				testCase.addTextEvidenceCurrentStep("Al menos un valor de la columna Precio no cumple el formato monetario ($#,###.##).");
			}
			return formatoCorrecto;
		} catch (Exception e) {
			testCase.addCodeEvidenceCurrentStep("Error al validar formato decimal en columna Precio: " + e.getMessage());
			return false;
		}
	}
}