package WEB.cometa.om;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class recargaNavegacionPage {

	private static final String ERROR_IIS = "the resource you are looking for has been removed, had its name changed, or is temporarily unavailable";

	private final SeleniumUtil u;
	private final TestCase testCase;

	/**
	 * Inicializa las acciones y validaciones compartidas para recargas de navegacion.
	 *
	 * @param u utilidad Selenium asociada al navegador activo.
	 * @param testCase caso de prueba usado para registrar evidencia.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public recargaNavegacionPage(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Captura la URL completa de la pantalla actual para compararla despues de la recarga.
	 *
	 * @return URL completa actual o null cuando no puede obtenerse.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public String capturarUrlActual() {
		try {
			String urlActual = u.getDriver().getCurrentUrl();
			boolean resultadoPaso = urlActual != null && !urlActual.trim().isEmpty();
			registrarResultado(resultadoPaso, "URL documentada antes del refresco: " + urlActual,
					"No fue posible documentar la URL de la pantalla actual");
			if (resultadoPaso) {
				testCase.addScreenShotCurrentStep("URL_Antes_Refresco");
			}
			return resultadoPaso ? urlActual : null;
		} catch (Exception exception) {
			registrarError("No fue posible capturar la URL actual: " + exception.getMessage());
			return null;
		}
	}

	/**
	 * Envia la tecla F5 al navegador y valida que el documento anterior sea reemplazado.
	 *
	 * @return true cuando F5 provoca el reemplazo del DOM anterior.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean refrescarConF5() {
		try {
			WebDriver driver = u.getDriver();
			WebElement bodyAntes = driver.findElement(By.tagName("body"));
			((JavascriptExecutor) driver).executeScript("window.focus();");
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_F5);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			boolean resultadoPaso = wait.until(ExpectedConditions.stalenessOf(bodyAntes));
			registrarResultado(resultadoPaso, "F5 reemplazo el documento anterior e inicio una recarga real",
					"F5 fue enviado, pero el documento anterior no fue reemplazado");
			if (resultadoPaso) {
				testCase.addScreenShotCurrentStep("F5_Recarga_Iniciada");
			}
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible ejecutar la recarga con F5: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Ejecuta el comando WebDriver equivalente al boton Refresh del navegador y espera la carga.
	 *
	 * @return true cuando la recarga termina con readyState complete.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean refrescarConBotonNavegador() {
		try {
			WebDriver driver = u.getDriver();
			WebElement bodyAntes = driver.findElement(By.tagName("body"));
			driver.navigate().refresh();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			boolean resultadoPaso = wait.until(ExpectedConditions.stalenessOf(bodyAntes));
			registrarResultado(resultadoPaso,
					"Refresh reemplazo el documento anterior e inicio una recarga real",
					"Refresh fue enviado, pero el documento anterior no fue reemplazado");
			if (resultadoPaso) {
				testCase.addScreenShotCurrentStep("Refresh_Recarga_Iniciada");
			}
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible ejecutar Refresh: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Espera a que el documento termine de cargar, incluso si la respuesta recibida es una
	 * pagina de error, y confirma que el navegador registro una navegacion de tipo reload.
	 * La presencia del contenido Angular se valida en un paso posterior.
	 *
	 * @return true cuando readyState es complete, el body esta disponible y la navegacion fue reload.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean esperarCargaCompleta() {
		try {
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(currentDriver -> "complete".equals(
					((JavascriptExecutor) currentDriver).executeScript("return document.readyState")));
			wait.until(currentDriver -> !currentDriver.findElements(By.tagName("body")).isEmpty());
			u.waitForLoadPage();
			Object tipoNavegacion = ((JavascriptExecutor) driver).executeScript(
					"return performance.getEntriesByType('navigation').length > 0 "
					+ "? performance.getEntriesByType('navigation')[0].type : ''; ");
			boolean resultadoPaso = "reload".equals(String.valueOf(tipoNavegacion));
			registrarResultado(resultadoPaso, "La recarga termino y el navegador confirmo el tipo de navegacion: reload",
					"La pagina termino de cargar, pero el navegador reporto el tipo de navegacion: " + tipoNavegacion);
			if (resultadoPaso) {
				testCase.addScreenShotCurrentStep("Recarga_Completada_Tipo_Reload");
			}
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("La pagina no termino de cargar: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que la respuesta recargada no contenga el mensaje historico de error IIS.
	 * Espera un segundo antes de capturar la evidencia para mostrar la pantalla estable.
	 *
	 * @return true cuando el texto de error IIS no aparece en la pagina.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean validarAusenciaErrorIis() {
		try {
			WebDriver driver = u.getDriver();
			String contenidoPagina = driver.getPageSource().toLowerCase();
			boolean resultadoPaso = !contenidoPagina.contains(ERROR_IIS);
			registrarResultado(resultadoPaso, "La pagina recargada no muestra el mensaje de error IIS",
					"La pagina recargada muestra el mensaje de error IIS");
			if (resultadoPaso) {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
				wait.until(currentDriver -> "complete".equals(
						((JavascriptExecutor) currentDriver).executeScript("return document.readyState")));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
				testCase.addTextEvidenceCurrentStep(
						"Evidencia visual: pantalla recargada visible y sin mensaje de error IIS.");
				testCase.addScreenShotCurrentStep("Evidencia_CA01_Pagina_Recargada_Sin_Error_IIS");
			}
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible validar el contenido contra el error IIS: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Compara la URL posterior al refresco contra la URL documentada antes de recargar.
	 *
	 * @param urlAntes URL completa capturada antes de refrescar.
	 * @return true cuando ambas URL son identicas.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean validarUrlSinCambio(String urlAntes) {
		try {
			String urlDespues = u.getDriver().getCurrentUrl();
			boolean resultadoPaso = urlAntes != null && urlAntes.equals(urlDespues);
			registrarResultado(resultadoPaso, "La URL permanece identica despues del refresco: " + urlDespues,
					"La URL cambio despues del refresco. Esperada: " + urlAntes + " | Actual: " + urlDespues);
			if (resultadoPaso) {
				testCase.addScreenShotCurrentStep("URL_Sin_Cambio_Despues_Refresco");
			}
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible comparar la URL posterior al refresco: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Valida que la pantalla recargada conserve contenido funcional esperado.
	 * Espera la renderizacion del contenido Angular, ignora mayusculas y requiere todos
	 * los textos proporcionados.
	 *
	 * @param textosEsperados textos de negocio que deben permanecer visibles en la pantalla.
	 * @return true cuando todos los textos estan presentes en el contenido visible.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean validarContenidoVisible(String... textosEsperados) {
		try {
			WebDriver driver = u.getDriver();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			boolean resultadoPaso;
			try {
				resultadoPaso = wait.until(currentDriver -> {
					String contenidoActual = currentDriver.findElement(By.tagName("body")).getText().toLowerCase();
					for (String textoEsperado : textosEsperados) {
						if (textoEsperado != null && !contenidoActual.contains(textoEsperado.toLowerCase())) {
							return false;
						}
					}
					return true;
				});
			} catch (TimeoutException exception) {
				resultadoPaso = false;
			}

			String textoVisible = driver.findElement(By.tagName("body")).getText().toLowerCase();
			String textoFaltante = null;
			for (String textoEsperado : textosEsperados) {
				if (textoEsperado != null && !textoVisible.contains(textoEsperado.toLowerCase())) {
					textoFaltante = textoEsperado;
					break;
				}
			}
			registrarResultado(resultadoPaso, "La pantalla muestra el contenido esperado",
					"No se encontro el texto esperado en la pantalla: " + textoFaltante);
			if (resultadoPaso) {
				testCase.addScreenShotCurrentStep("Contenido_Esperado_Visible_Pantalla");
			}
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible validar el contenido visible de la pantalla: " + exception.getMessage());
			return false;
		}
	}

	/**
	 * Registra en consola y evidencia el resultado de una accion o validacion de recarga.
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
	 * Valida que la URL documentada corresponda a la pantalla de negocio esperada.
	 * Acepta rutas alternativas para pantallas cuyo enrutamiento puede usar guion o guion bajo.
	 *
	 * @param urlCapturada URL completa obtenida antes del refresco.
	 * @param fragmentosEsperados fragmentos de ruta aceptados para identificar la pantalla.
	 * @return true cuando la URL contiene al menos uno de los fragmentos esperados.
	 *
	 * @author Mauricio Gurrola
	 * @date 2026-07-16
	 */
	public boolean validarUrlCorrespondePantalla(String urlCapturada, String... fragmentosEsperados) {
		try {
			String urlNormalizada = urlCapturada == null ? "" : urlCapturada.toLowerCase();
			String fragmentoEncontrado = null;
			for (String fragmentoEsperado : fragmentosEsperados) {
				if (fragmentoEsperado != null && urlNormalizada.contains(fragmentoEsperado.toLowerCase())) {
					fragmentoEncontrado = fragmentoEsperado;
					break;
				}
			}
			boolean resultadoPaso = fragmentoEncontrado != null;
			registrarResultado(resultadoPaso,
					"La URL documentada corresponde a la pantalla esperada: " + urlCapturada,
					"La URL no corresponde a la pantalla esperada. Fragmentos aceptados: "
							+ java.util.Arrays.toString(fragmentosEsperados) + " | URL actual: " + urlCapturada);
			if (resultadoPaso) {
				testCase.addScreenShotCurrentStep("URL_Corresponde_Pantalla_Esperada");
			}
			return resultadoPaso;
		} catch (Exception exception) {
			registrarError("No fue posible validar la ruta de la pantalla: " + exception.getMessage());
			return false;
		}
	}
}