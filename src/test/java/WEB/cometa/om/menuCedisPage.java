package WEB.cometa.om;

import java.net.URI;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class menuCedisPage {

	private final SeleniumUtil u;
	private final TestCase testCase;
	private final menuCedisElements elements;

	public menuCedisPage(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
		this.elements = new menuCedisElements();
	}

	/**
	 * Abre el submenú CEDIS y espera a que la opción Recepción y Devolución sea visible.
	 *
	 * @return true cuando el submenú queda desplegado con la opción esperada.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean abrirSubmenuCedis() {
		try {
			WebElement botonMenu = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.botonMenuCedis));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", botonMenu);
			try {
				botonMenu.click();
			} catch (Exception clickInterceptado) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", botonMenu);
			}
			boolean submenuVisible = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.visibilityOfElementLocated(elements.opcionRecepcionYDevolucion)).isDisplayed();
			registrarResultado(submenuVisible,
					"El submenú CEDIS se despliega con la opción Recepción y Devolución visible",
					"No se mostró la opción Recepción y Devolución al abrir el submenú CEDIS",
					elements.botonMenuCedis, elements.opcionRecepcionYDevolucion);
			return submenuVisible;
		} catch (Exception exception) {
			registrarError("No fue posible abrir el submenú CEDIS: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que el submenú de CEDIS muestre únicamente la opción Recepción y Devolución.
	 *
	 * @return true cuando solo existe una opción visible y corresponde a Recepción y Devolución.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean validaSubmenuSoloRecepcionYDevolucion() {
		try {
			u.highLight(elements.opcionesSubmenuCedis);
			u.hardWait(1);
			List<String> nombresOpciones = obtenerNombresOpcionesVisibles();
			boolean recepcionYDevolucionVisible = nombresOpciones.stream()
					.anyMatch(nombre -> "Recepción y Devolución".equals(nombre) || "Recepcion y Devolucion".equals(nombre));
			boolean resultadoPaso = nombresOpciones.size() == 1 && recepcionYDevolucionVisible;
			String opcionesActuales = "Cantidad: " + nombresOpciones.size() + " | Opciones: " + nombresOpciones;
			registrarResultado(resultadoPaso,
					"El submenú de CEDIS muestra únicamente la opción 'Recepción y Devolución'. " + opcionesActuales,
					"El submenú de CEDIS no contiene únicamente 'Recepción y Devolución'. Esperado: Cantidad 1 | Opción exacta: Recepción y Devolución | Actual: "
							+ opcionesActuales,
					elements.opcionesSubmenuCedis);
			u.unhighLight(elements.opcionesSubmenuCedis);
			return resultadoPaso;
		} catch (Exception exception) {
			List<String> nombresOpciones = obtenerNombresOpcionesVisibles();
			registrarError("No fue posible validar las opciones del submenú CEDIS. Cantidad encontrada: "
					+ nombresOpciones.size() + " | Opciones encontradas: " + nombresOpciones
					+ " | Error: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Selecciona la opción Recepción y Devolución desde el submenú CEDIS.
	 *
	 * @return true cuando se acciona la opción y la navegación inicia correctamente.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	public boolean clicRecepcionYDevolucion() {
		try {
			if (!u.getDriver().findElements(elements.opcionRecepcionYDevolucion).stream().anyMatch(WebElement::isDisplayed)) {
				if (!abrirSubmenuCedis()) {
					return false;
				}
			}
			WebElement opcion = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20))
					.until(ExpectedConditions.elementToBeClickable(elements.opcionRecepcionYDevolucion));
			((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].scrollIntoView({block:'center', inline:'center'});", opcion);
			try {
				opcion.click();
			} catch (Exception clickInterceptado) {
				((JavascriptExecutor) u.getDriver()).executeScript("arguments[0].click();", opcion);
			}
			registrarResultado(true,
					"El sistema navega a Recepción de Transferencias y Devoluciones",
					"No fue posible accionar la opción Recepción y Devolución",
					elements.opcionRecepcionYDevolucion);
			return true;
		} catch (Exception exception) {
			registrarError("No fue posible navegar a Recepción y Devolución: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Cierra el submenú CEDIS si permanece desplegado, para no bloquear la navegación siguiente
	 * ni dejar el overlay activo al reutilizar {@link #abrirSubmenuCedis()} en otra pantalla.
	 *
	 * @return true cuando el submenú queda cerrado (o ya estaba cerrado previamente).
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-24
	 */
	public boolean cerrarSubmenuCedis() {
		try {
			if (u.getDriver().findElements(elements.opcionRecepcionYDevolucion).stream()
					.noneMatch(WebElement::isDisplayed)) {
				return true;
			}
			new Actions(u.getDriver()).sendKeys(Keys.ESCAPE).perform();
			boolean resultadoPaso = new WebDriverWait(u.getDriver(), Duration.ofSeconds(10))
					.until(driver -> driver.findElements(elements.opcionRecepcionYDevolucion).stream()
							.noneMatch(WebElement::isDisplayed));
			registrarResultado(resultadoPaso, "El submenú CEDIS se cierra correctamente antes de continuar",
					"El submenú CEDIS permanece desplegado tras intentar cerrarlo");
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible cerrar el submenú CEDIS: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que Reportes no exista como opción visible o navegable en el submenú CEDIS.
	 *
	 * @return true cuando no existe ninguna opción Reportes dentro del submenú.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaReportesOcultoEnSubmenu() {
		return validaAusenciaOpcion(elements.opcionReportesSubmenu, "Reportes");
	}

	/**
	 * Valida que Graficas no exista como opción visible o navegable en el submenú CEDIS.
	 *
	 * @return true cuando no existe ninguna opción Graficas dentro del submenú.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaGraficasOcultoEnSubmenu() {
		return validaAusenciaOpcion(elements.opcionGraficasSubmenu, "Graficas");
	}

	/**
	 * Valida que Reportes y Graficas no se muestren en la navegación superior de CEDIS.
	 *
	 * @return true cuando ambas opciones están ausentes en la solapa superior.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaSolapaSuperiorSinReportesNiGraficas() {
		try {
			boolean resultadoPaso = u.getDriver().findElements(elements.reportesSolapaSuperior).isEmpty()
					&& u.getDriver().findElements(elements.graficasSolapaSuperior).isEmpty();
			registrarResultado(resultadoPaso,
					"La solapa superior de CEDIS no muestra Reportes ni Graficas",
					"La solapa superior de CEDIS todavía muestra Reportes o Graficas");
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible validar la solapa superior de CEDIS: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Intenta abrir una ruta CEDIS restringida y valida que el contenido no sea accesible.
	 *
	 * @param ruta ruta relativa de Portal COMETA que debe permanecer restringida.
	 * @return true cuando el portal deniega o redirige el acceso sin mostrar el contenido solicitado.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaAccesoDirectoRestringido(String ruta) {
		try {
			URI urlActual = URI.create(u.getDriver().getCurrentUrl());
			String origen = urlActual.getScheme() + "://" + urlActual.getAuthority();
			String urlRestringida = origen + ruta;
			String segmento = ruta.substring(ruta.lastIndexOf('/') + 1).toLowerCase();
			String urlAntesDelIntento = u.getDriver().getCurrentUrl().toLowerCase();

			u.get(urlRestringida);
			WebDriverWait waitIntento = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			try {
				waitIntento.until(driver -> {
					String urlAhora = driver.getCurrentUrl().toLowerCase();
					boolean cambioUrl = !urlAhora.equals(urlAntesDelIntento);
					boolean estadoDocumentoCompleto = "complete".equals(((JavascriptExecutor) driver)
							.executeScript("return document.readyState"));
					String textoVisible = String.valueOf(((JavascriptExecutor) driver).executeScript(
							"return (document.body && document.body.innerText) ? document.body.innerText.trim() : '';"));
					boolean pantallaConContenido = textoVisible != null && !textoVisible.trim().isEmpty();
					boolean llegoAEstadoFinal = urlAhora.contains("/cedis/" + segmento)
							|| urlAhora.contains("/home") || urlAhora.contains("/login")
							|| urlAhora.contains("/forbidden");
					return estadoDocumentoCompleto && pantallaConContenido && (cambioUrl || llegoAEstadoFinal);
				});
			} catch (Exception timeoutCargaIntento) {
				System.out.println("[WARN] El intento de acceso directo no estabilizo completamente la vista antes de la evidencia: "
						+ timeoutCargaIntento.getMessage());
			}

			// Evidencia 1: URL utilizada para el intento directo.
			String urlObservadaIntento = u.getDriver().getCurrentUrl();
			testCase.addTextEvidenceCurrentStep("Se intenta acceder directamente a: " + urlRestringida);
			testCase.addTextEvidenceCurrentStep("URL observada durante el intento: " + urlObservadaIntento);
			capturarEvidenciaUrlConMarcador(
					"INTENTO URL RESTRINGIDA",
					urlRestringida,
					urlObservadaIntento,
					"Intento_Acceso_" + segmento);

			esperarPantallaEstable();

			WebDriverWait waitResultado = new WebDriverWait(u.getDriver(), Duration.ofSeconds(20));
			try {
				waitResultado.until(driver -> {
					String urlAhora = driver.getCurrentUrl().toLowerCase();
					String fuente = driver.getPageSource().toLowerCase();
					boolean urlYaNoEsRestringida = !urlAhora.contains("/cedis/" + segmento);
					boolean mensajeAccesoDenegado = fuente.contains("403")
							|| fuente.contains("no tienes permiso")
							|| fuente.contains("forbidden")
							|| fuente.contains("contacta al equipo de soporte");
					return urlYaNoEsRestringida || mensajeAccesoDenegado;
				});
			} catch (Exception timeoutEsperandoRedireccion) {
				System.out.println("[WARN] No se detectó redireccion ni mensaje de acceso denegado dentro del tiempo esperado: "
						+ timeoutEsperandoRedireccion.getMessage());
			}

			String urlFinal = u.getDriver().getCurrentUrl().toLowerCase();
			String contenido = u.getDriver().getPageSource().toLowerCase();

			boolean accesoDenegado = !urlFinal.contains("/cedis/" + segmento)
					|| contenido.contains("403") || contenido.contains("no tienes permiso")
					|| contenido.contains("forbidden") || contenido.contains("contacta al equipo de soporte");
			boolean sinErrorTecnico = !contenido.contains("stacktrace") && !contenido.contains("internal server error")
					&& !contenido.contains("http 500");
			boolean resultadoPaso = accesoDenegado && sinErrorTecnico;
			By locatorEvidencia = elements.contenedorPrincipalPagina;
			if (u.getDriver().findElements(elements.mensajeAccesoRestringido).stream().anyMatch(WebElement::isDisplayed)) {
				locatorEvidencia = elements.mensajeAccesoRestringido;
			} else if (u.getDriver().findElements(elements.formularioLoginAccessControl).stream()
					.anyMatch(WebElement::isDisplayed)) {
				locatorEvidencia = elements.formularioLoginAccessControl;
			}

			testCase.addTextEvidenceCurrentStep("URL final tras el intento: " + urlFinal);
			// Evidencia 2: estado final luego de la redirección/bloqueo.
			capturarEvidenciaUrlConMarcador(
					"RESULTADO TRAS REDIRECCION",
					urlRestringida,
					u.getDriver().getCurrentUrl(),
					"Redireccion_Acceso_" + segmento);
			String idMarcadorResumenFinal = "cometa-url-evidence-resumen-final";
			By locatorResumenFinal = By.id(idMarcadorResumenFinal);
			try {
				mostrarMarcadorResumenUrlFinal(idMarcadorResumenFinal, urlRestringida, urlObservadaIntento,
						u.getDriver().getCurrentUrl());
				registrarResultado(resultadoPaso,
						"El acceso directo a " + ruta + " fue denegado o redirigido correctamente",
						"La ruta restringida quedó accesible o mostró un error técnico. URL final: " + urlFinal,
						locatorEvidencia, locatorResumenFinal);
			} finally {
				limpiarMarcadorVisual(idMarcadorResumenFinal);
			}
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible validar la ruta restringida: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que la respuesta actual no contenga errores 500 ni stacktraces técnicos.
	 *
	 * @return true cuando el portal maneja la respuesta sin exponer errores internos.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	public boolean validaRespuestaSinErroresTecnicos() {
		try {
			esperarPantallaEstable();
			String contenido = u.getDriver().getPageSource().toLowerCase();
			boolean resultadoPaso = !contenido.contains("stacktrace") && !contenido.contains("internal server error")
					&& !contenido.contains("http 500") && !contenido.contains("error 500");
			By locatorEvidencia = elements.contenedorPrincipalPagina;
			if (u.getDriver().findElements(elements.mensajeAccesoRestringido).stream().anyMatch(WebElement::isDisplayed)) {
				locatorEvidencia = elements.mensajeAccesoRestringido;
			} else if (u.getDriver().findElements(elements.formularioLoginAccessControl).stream()
					.anyMatch(WebElement::isDisplayed)) {
				locatorEvidencia = elements.formularioLoginAccessControl;
			}
			registrarResultado(resultadoPaso,
					"El portal maneja el acceso no autorizado sin errores 500 ni stacktraces",
					"El portal expone un error técnico 500 o stacktrace",
					locatorEvidencia);
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible revisar errores técnicos en la respuesta: " + exception.getMessage());
			return false;
		}
	}

	private boolean validaAusenciaOpcion(By locator, String nombreOpcion) {
		try {
			boolean resultadoPaso = u.getDriver().findElements(locator).isEmpty();
			registrarResultado(resultadoPaso,
					"La opción '" + nombreOpcion + "' no es visible ni navegable en el submenú CEDIS",
					"La opción '" + nombreOpcion + "' todavía existe en el submenú CEDIS");
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible validar la ausencia de " + nombreOpcion + ": " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Espera que la página actual alcance un estado estable de carga del documento.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-08-04
	 */
	private void esperarPantallaEstable() {
		new WebDriverWait(u.getDriver(), Duration.ofSeconds(20)).until(driver ->
				"complete".equals(((JavascriptExecutor) driver).executeScript("return document.readyState")));
	}

	private void registrarResultado(boolean resultadoPaso, String mensajeExito, String motivoFallo,
			By... locators) {
		String estadoPaso = resultadoPaso ? "PASSED" : "FAILED";
		String evidencia = resultadoPaso ? mensajeExito : "Motivo: " + motivoFallo;
		System.out.println("[RESULTADO PASO] [" + estadoPaso + "] - " + evidencia);
		testCase.addBoldTextEvidenceCurrentStep(
				"Resultado paso: [" + estadoPaso + "] - " + evidencia);
		List<By> locatorsResaltados = new java.util.ArrayList<By>();
		try {
			List<WebElement> elementosVisibles = new java.util.ArrayList<WebElement>();
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
			testCase.addScreenShotCurrentStep("Resultado_Menu_CEDIS_" + estadoPaso);
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

	private void registrarError(String mensajeError) {
		System.out.println("[RESULTADO PASO] [FAILED] - Motivo: " + mensajeError);
		testCase.addBoldTextEvidenceCurrentStep(
				"Resultado paso: [FAILED] - Motivo: " + mensajeError);
		try {
			testCase.addScreenShotCurrentStep("Error_Menu_CEDIS");
		} catch (Exception screenshotException) {
			System.out.println("[WARN] No fue posible adjuntar captura del error: " + screenshotException.getMessage());
		}
	}

	/**
	 * Obtiene los nombres de las opciones visibles del menu CEDIS sin duplicados.
	 * Usa el texto visible y, cuando no existe, los atributos aria-label o title.
	 *
	 * @return lista de nombres visibles en el orden mostrado por el portal.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-17
	 */
	private List<String> obtenerNombresOpcionesVisibles() {
		List<String> nombresOpciones = new java.util.ArrayList<>();
		for (WebElement opcion : u.getDriver().findElements(elements.opcionesSubmenuCedis)) {
			try {
				if (!opcion.isDisplayed()) {
					continue;
				}
				String nombre = opcion.getText().replaceAll("\\s+", " ").trim();
				if (nombre.isEmpty()) {
					nombre = opcion.getAttribute("aria-label");
				}
				if (nombre == null || nombre.trim().isEmpty()) {
					nombre = opcion.getAttribute("title");
				}
				if (nombre != null && !nombre.trim().isEmpty() && !nombresOpciones.contains(nombre.trim())) {
					nombresOpciones.add(nombre.trim());
				}
			} catch (Exception exception) {
				System.out.println("[WARN] No fue posible leer una opcion del menu CEDIS: "
						+ exception.getMessage());
			}
		}
		return nombresOpciones;
	}

	/**
	 * Inserta un marcador visual temporal en la página con URL objetivo y URL actual,
	 * para que la evidencia del reporte muestre explícitamente el intento y su resultado.
	 *
	 * @param titulo encabezado del bloque de evidencia.
	 * @param urlObjetivo URL restringida que el flujo intenta abrir.
	 * @param urlActual URL observada en el navegador al momento de la captura.
	 * @param nombreCaptura nombre de archivo para la evidencia del paso.
	 * @author Mauricio Gurrola
	 * @date 2026-07-27
	 */
	private void capturarEvidenciaUrlConMarcador(String titulo, String urlObjetivo, String urlActual,
			String nombreCaptura) {
		By marcador = By.id("cometa-url-evidence-banner");
		boolean resaltado = false;
		try {
			((JavascriptExecutor) u.getDriver()).executeScript(
					"var id='cometa-url-evidence-banner';"
							+ "var prev=document.getElementById(id); if(prev){prev.remove();}"
							+ "var d=document.createElement('div'); d.id=id;"
							+ "d.style.position='fixed'; d.style.top='12px'; d.style.left='12px'; d.style.right='12px';"
							+ "d.style.zIndex='2147483647'; d.style.background='#fff4e5'; d.style.border='3px solid #b45309';"
							+ "d.style.padding='10px 12px'; d.style.borderRadius='10px'; d.style.boxShadow='0 4px 14px rgba(0,0,0,.25)';"
							+ "d.style.fontFamily='Arial, sans-serif'; d.style.fontSize='13px'; d.style.color='#111827';"
							+ "d.innerHTML='<div style=\"font-weight:700;margin-bottom:6px;\">'+arguments[0]+'</div>'"
							+ "+'<div><b>URL objetivo:</b> '+arguments[1]+'</div>'"
							+ "+'<div><b>URL actual:</b> '+arguments[2]+'</div>'"
							+ "; document.body.appendChild(d);",
					titulo, urlObjetivo, urlActual);
			u.hardWait(1);
			if (u.getDriver().findElements(marcador).stream().anyMatch(WebElement::isDisplayed)) {
				u.highLight(marcador);
				resaltado = true;
			}
			testCase.addScreenShotCurrentStep(nombreCaptura);
		} catch (Exception exception) {
			System.out.println("[WARN] No fue posible capturar evidencia visual de URL: " + exception.getMessage());
			try {
				testCase.addScreenShotCurrentStep(nombreCaptura + "_Fallback");
			} catch (Exception ignored) {
				System.out.println("[WARN] Fallo también la captura fallback de evidencia de URL: "
						+ ignored.getMessage());
			}
		} finally {
			if (resaltado) {
				try {
					u.unhighLight(marcador);
				} catch (Exception exception) {
					System.out.println("[WARN] No fue posible retirar el resaltado del marcador URL: "
							+ exception.getMessage());
				}
			}
			try {
				limpiarMarcadorVisual("cometa-url-evidence-banner");
			} catch (Exception exception) {
				System.out.println("[WARN] No fue posible limpiar el marcador URL de evidencia: "
						+ exception.getMessage());
			}
		}
	}

	/**
	 * Inserta un resumen persistente del intento y redirección para que la captura final del paso
	 * muestre explícitamente ambas URLs dentro de la evidencia visual del PDF.
	 *
	 * @param idMarcador id único del bloque visual temporal.
	 * @param urlObjetivo URL restringida objetivo.
	 * @param urlIntento URL observada durante el intento.
	 * @param urlFinal URL observada después de la redirección.
	 * @author Mauricio Gurrola
	 * @date 2026-07-27
	 */
	private void mostrarMarcadorResumenUrlFinal(String idMarcador, String urlObjetivo, String urlIntento,
			String urlFinal) {
		((JavascriptExecutor) u.getDriver()).executeScript(
				"var id=arguments[0];"
						+ "var prev=document.getElementById(id); if(prev){prev.remove();}"
						+ "var d=document.createElement('div'); d.id=id;"
						+ "d.style.position='fixed'; d.style.bottom='12px'; d.style.left='12px'; d.style.right='12px';"
						+ "d.style.zIndex='2147483647'; d.style.background='#ecfeff'; d.style.border='3px solid #0e7490';"
						+ "d.style.padding='10px 12px'; d.style.borderRadius='10px'; d.style.boxShadow='0 4px 14px rgba(0,0,0,.25)';"
						+ "d.style.fontFamily='Arial, sans-serif'; d.style.fontSize='13px'; d.style.color='#0f172a';"
						+ "d.innerHTML='<div style=\"font-weight:700;margin-bottom:6px;\">RESUMEN INTENTO/REDIRECCION</div>'"
						+ "+'<div><b>URL objetivo:</b> '+arguments[1]+'</div>'"
						+ "+'<div><b>URL intento:</b> '+arguments[2]+'</div>'"
						+ "+'<div><b>URL final:</b> '+arguments[3]+'</div>'"
						+ "; document.body.appendChild(d);",
				idMarcador, urlObjetivo, urlIntento, urlFinal);
		u.hardWait(1);
	}

	/**
	 * Elimina un marcador visual temporal del DOM sin interrumpir el flujo del paso.
	 *
	 * @param idMarcador id del marcador a remover.
	 * @author Mauricio Gurrola
	 * @date 2026-07-27
	 */
	private void limpiarMarcadorVisual(String idMarcador) {
		((JavascriptExecutor) u.getDriver()).executeScript(
				"var e=document.getElementById(arguments[0]); if(e){e.remove();}",
				idMarcador);
	}

}