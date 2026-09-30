package EBS.util.ServiceNow;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import EBS.util.GlobalVariables;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCION: Clase que incluye los metodos para validaciones en la pantalla
 * de gestion de porveedores
 *
 * @date 01/Febrero/2025
 */

public class ServiceNow {

	private static SeleniumUtil u;
	private static TestCase testCase;

	private enum EstadoPantalla {
		NORMAL, RECUPERACION
	}

	private void log(String mensaje) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("hh:mm a");
		String hora = LocalTime.now().format(fmt);
		System.out.println("[" + hora + "] " + mensaje);
	}

	private String formatoTiempoRestante(long millis) {
		long totalSegundos = millis / 1000;
		long minutos = totalSegundos / 60;
		long segundos = totalSegundos % 60;
		return String.format("%02d:%02ds", minutos, segundos);
	}

	// INICIO DE SESION
	private By usuarioInput = By.xpath("//*[@id=\"user_name\"]");
	private By contrasenaInput = By.xpath("//*[@id=\"user_password\"]");
	private By botonInicio = By.xpath("//*[@id=\"sysverb_login\"]");

	@FindBy(xpath = "//input[@type='file']")
	public WebElement uploadInputElement;

	public ServiceNow(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Método para ingresar a service now qa
	 * 
	 * @author jesusm3@hexaware.com
	 * @param usuario    : obtiene usuario para portal de proveedores
	 * @param contrasena : obtiene la contrasena de el usuario
	 * 
	 * @return returna valor boleano para validacion en script principal
	 * @date 15/Dic/2025
	 * 
	 */
	public boolean loginServiceNow(String usuario, String contrasena) {

		// Navegar a la URL del portal
		u.get(GlobalVariables.HOST_ServiceNowQA);
		u.waitForLoadPage();
		u.highLight(usuarioInput);
		u.sendKeys(usuarioInput, usuario);
		u.highLight(contrasenaInput);
		u.sendKeys(contrasenaInput, contrasena);
		testCase.addScreenShotCurrentStep(" ");
		u.highLight(botonInicio);
		u.click(botonInicio);
		u.waitForLoadPage();
		return true;

	}

	/**
	 * Buscar y retornar un elemento dentro del Shadow DOM usando un selector CSS.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param driver Instancia de WebDriver para ejecutar JavaScript.
	 * @param cssSelector Selector CSS para buscar el elemento.
	 * @return WebElement encontrado dentro del Shadow DOM o null si no se encuentra.
	 * @date 16/02/2026
	 */
	public WebElement buscarDentroDeShadowDoom(WebDriver driver, String cssSelector) {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		return (WebElement) js.executeScript("function deepQuery(root, selector) {" + "  root = root || document;"
				+ "  let el = root.querySelector(selector);" + "  if (el) return el;"
				+ "  let nodes = root.querySelectorAll('*');" + "  for (let node of nodes) {"
				+ "    if (node.shadowRoot) {" + "      let found = deepQuery(node.shadowRoot, selector);"
				+ "      if (found) return found;" + "    }" + "  }" + "  return null;" + "}"
				+ "return deepQuery(document, arguments[0]);", cssSelector);
	}

	/**
	 * Realizar integración y selección de opción parcial en interfaz web con Selenium.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param opcionParcial Texto parcial para filtrar y seleccionar la opción.
	 * @return true si la opción fue seleccionada correctamente; false en caso de error.
	 * @date 16/02/2026
	 */
	public boolean datosIntegracion(String opcionParcial) {
		WebDriver driver = u.getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		System.out.println("Entrando a Integración v3");

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// 1️⃣ Esperar y click en "Todos"
			System.out.println("🔹 Esperando botón 'Todos'...");
			WebElement todos = wait.until(d -> buscarDentroDeShadowDoom(d, "div[role='menuitem'][aria-label='todos']"));
			if (todos == null)
				throw new RuntimeException("No se encontró el botón 'Todos'");
			js.executeScript("arguments[0].click();", todos);
			System.out.println("✅ Botón 'Todos' clickeado");

			// 2️⃣ Esperar el input filtro y escribir la opción
			System.out.println("🔹 Esperando input '#filter'...");
			WebElement filtro = wait.until(d -> buscarDentroDeShadowDoom(d, "#filter"));
			if (filtro == null)
				throw new RuntimeException("No se encontró el input '#filter'");
			filtro.clear();
			Thread.sleep(300);
			filtro.sendKeys(opcionParcial);
			Thread.sleep(300);
			System.out.println("✅ Filtro ingresado: '" + opcionParcial + "'");

			// 3️⃣ Esperar opción <mark> usando WebDriverWait
			System.out.println("🔹 Esperando opción <mark>...");
			WebElement opcion = wait.until(d -> buscarDentroDeShadowDoom(d, "span.label mark"));
			if (opcion == null)
				throw new RuntimeException("No se encontró la opción con <mark>");

			// 4️⃣ Click en la opción usando JS
			js.executeScript("arguments[0].click();", opcion);
			System.out.println("✅ Opción seleccionada correctamente");

			return true;

		} catch (Exception e) {
			System.out.println("❌ Error en datosIntegracion_v3: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Filtrar registros en la interfaz usando criterios específicos dentro de Shadow DOM.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param filtroNombre Nombre del filtro que se desea aplicar.
	 * @param comparativo Operador comparativo para el filtro (por ejemplo, igual, contiene).
	 * @param valor Valor con el que se compara el campo filtrado.
	 * @return true si el filtro se aplicó correctamente; false en caso de error.
	 * @date 16/02/2026
	 */
	@SuppressWarnings("unchecked")
	public boolean filtrarRegistro(String filtroNombre, String comparativo, String valor) {
		WebDriver driver = u.getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		System.out.println("🔹 Entrando a filtrarRegistro");
		System.out.println("🔹 Parámetros recibidos: filtro='" + filtroNombre + "', comparativo='" + comparativo
				+ "', valor='" + valor + "'");

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// 1️⃣ Buscar el iframe dentro de shadow DOM
			System.out.println("🔹 Buscando iframe dentro del shadow DOM...");
			WebElement iframe = buscarDentroDeShadowDoom(driver, "iframe#gsft_main");
			if (iframe == null)
				throw new RuntimeException("No se encontró el iframe gsft_main dentro de shadow DOM");

			System.out.println("✅ Iframe encontrado");
			driver.switchTo().frame(iframe);

			// 2️⃣ Buscar y click en el botón de filtro
			System.out.println("🔹 Buscando botón de filtro...");
			WebElement botonFiltro = wait
					.until(d -> buscarDentroDeShadowDoom(d, "button#u_datos_maf_filter_toggle_image"));

			System.out.println("✅ Botón de filtro encontrado");
			js.executeScript("arguments[0].click();", botonFiltro);

			// 3️⃣ Seleccionar el filtro principal
			System.out.println("🔹 Abriendo selector de filtros...");
			WebElement select2Choice = wait.until(d -> buscarDentroDeShadowDoom(d, "span.select2-chosen"));

			new Actions(driver).moveToElement(select2Choice).click().perform();
			System.out.println("✅ Selector de filtros abierto");

			// 4️⃣ Verificar que el dropdown apareció
			WebElement dropContainer = wait.until(
					d -> (WebElement) js.executeScript("return document.querySelector('div.select2-drop-active');"));

			System.out.println("✅ Dropdown visible");

			// 5️⃣ Buscar y seleccionar el filtro solicitado
			WebElement select2Option = null;
			long end = System.currentTimeMillis() + 10000;

			while (System.currentTimeMillis() < end) {
				List<WebElement> opciones = (List<WebElement>) js.executeScript("let c = arguments[0];"
						+ "if (!c) return [];" + "return Array.from(c.querySelectorAll('li.select2-result'));",
						dropContainer);

				System.out.println("🔹 Opciones encontradas: " + opciones.size());
				for (WebElement opcion : opciones) {
					String texto = opcion.getText().trim();
					System.out.println(" - " + texto);

					if (texto.equals(filtroNombre)) {
						select2Option = opcion;
						break;
					}
				}

				if (select2Option != null)
					break;
				Thread.sleep(300);
			}

			if (select2Option == null)
				throw new RuntimeException("No se encontró el filtro '" + filtroNombre + "'");

			new Actions(driver).moveToElement(select2Option).click().perform();
			System.out.println("✅ Filtro seleccionado: '" + filtroNombre + "'");

			// 6️⃣ Seleccionar el comparativo
			System.out.println("🔹 Seleccionando comparativo para '" + filtroNombre + "': " + comparativo);

			WebElement comparativoSelect = wait
					.until(d -> buscarDentroDeShadowDoom(d, "select.filerTableSelect.condOperator"));

			new Actions(driver).moveToElement(comparativoSelect).click().perform();
			Thread.sleep(300);

			boolean comparativoSeleccionado = false;
			for (WebElement opcion : comparativoSelect.findElements(By.tagName("option"))) {
				if (opcion.getText().trim().equalsIgnoreCase(comparativo)) {
					opcion.click();
					comparativoSeleccionado = true;
					System.out.println("✅ Comparativo seleccionado: " + opcion.getText().trim());
					break;
				}
			}

			if (!comparativoSeleccionado)
				throw new RuntimeException("No se encontró el comparativo '" + comparativo + "'");

			// 7️⃣ Ingresar el valor
			System.out.println("🔹 Ingresando valor para '" + filtroNombre + "': '" + valor + "'");

			try {
				WebElement valorInput = wait.until(d -> buscarDentroDeShadowDoom(d, "input.filter-reference-input"));

				System.out.println("✅ Input tipo reference detectado");
				new Actions(driver).moveToElement(valorInput).click().sendKeys(valor).sendKeys(Keys.TAB).perform();

			} catch (TimeoutException e) {
				WebElement valorInput = wait.until(d -> buscarDentroDeShadowDoom(d, "input.filerTableInput"));

				System.out.println("✅ Input tipo texto detectado");
				valorInput.clear();
				valorInput.sendKeys(valor);
			}

			Thread.sleep(200);
			System.out.println("✅ Valor aplicado correctamente para '" + filtroNombre + "'");

			// 8️⃣ Ejecutar filtro
			System.out.println("🔹 Ejecutando filtro...");
			WebElement botonEjecutar = wait
					.until(d -> buscarDentroDeShadowDoom(d, "button#test_filter_action_toolbar_run"));

			js.executeScript("arguments[0].click();", botonEjecutar);
			System.out.println("✅ Filtro ejecutado");

			// 9️⃣ Salir del iframe
			driver.switchTo().defaultContent();
			System.out.println("🔹 Saliendo de filtrarRegistro");

			return true;

		} catch (Exception e) {
			System.out.println("❌ Error en filtrarRegistro (" + filtroNombre + "): " + e.getMessage());
			return false;
		}
	}

	/**
	 * Filtrar archivos en EBS mediante interfaz web con criterios especificados.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param filtroNombre Nombre del filtro o campo por el cual se realiza la búsqueda.
	 * @param comparativo Operador comparativo utilizado para el filtro (ejemplo: igual, contiene).
	 * @param valor Valor que debe coincidir según el filtro aplicado.
	 * @return true si el filtro fue aplicado exitosamente; false en caso de error.
	 * @date 16/02/2026
	 */
	@SuppressWarnings("unchecked")
	public boolean filtrarArchivosEBS(String filtroNombre, String comparativo, String valor) {
		WebDriver driver = u.getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		System.out.println("🔹 Entrando a filtrarArchivosEBS");
		System.out.println("🔹 Parámetros: filtro='" + filtroNombre + "', comparativo='" + comparativo + "', valor='"
				+ valor + "'");

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

			// 1️⃣ Iframe dentro de shadow DOM
			System.out.println("🔹 Buscando iframe...");
			WebElement iframe = buscarDentroDeShadowDoom(driver, "iframe#gsft_main");
			if (iframe == null)
				throw new RuntimeException("No se encontró iframe gsft_main");

			driver.switchTo().frame(iframe);
			System.out.println("✅ Iframe encontrado");

			// 2️⃣ Botón de filtro
			System.out.println("🔹 Buscando botón de filtro...");
			WebElement botonFiltro = wait
					.until(d -> buscarDentroDeShadowDoom(d, "button#u_archivos_ebs_filter_toggle_image"));
			js.executeScript("arguments[0].click();", botonFiltro);
			System.out.println("✅ Botón de filtro clickeado");

			// 3️⃣ Abrir selector de campo
			System.out.println("🔹 Abriendo selector de campo...");
			WebElement selectCampo = wait.until(d -> buscarDentroDeShadowDoom(d, "span.select2-chosen"));
			new Actions(driver).moveToElement(selectCampo).click().perform();

			// 4️⃣ Esperar dropdown activo
			WebElement dropContainer = wait.until(
					d -> (WebElement) js.executeScript("return document.querySelector('div.select2-drop-active');"));
			System.out.println("✅ Dropdown visible");

			// 5️⃣ Seleccionar campo (Nombre de archivo)
			WebElement opcionCampo = null;
			long endCampo = System.currentTimeMillis() + 10000;

			while (System.currentTimeMillis() < endCampo) {
				List<WebElement> opciones = (List<WebElement>) js.executeScript(
						"return Array.from(arguments[0].querySelectorAll('li.select2-result'));", dropContainer);

				for (WebElement op : opciones) {
					if (op.getText().trim().equalsIgnoreCase(filtroNombre)) {
						opcionCampo = op;
						break;
					}
				}
				if (opcionCampo != null)
					break;
			}

			if (opcionCampo == null)
				throw new RuntimeException("No se encontró el campo '" + filtroNombre + "'");

			new Actions(driver).moveToElement(opcionCampo).click().perform();
			System.out.println("✅ Campo seleccionado: " + filtroNombre);

			// 🔥 6️⃣ ESPERAR RE-RENDER DEL COMPARATIVO
			System.out.println("🔹 Esperando comparativo regenerado...");

			WebElement comparativoSelect = wait.until(d -> {
				WebElement el = buscarDentroDeShadowDoom(d, "select.condOperator");
				if (el == null)
					return null;

				String cls = el.getAttribute("class");
				if (cls != null && cls.contains("select2-offscreen"))
					return null;

				return el;
			});

			js.executeScript("arguments[0].scrollIntoView({block:'center'});", comparativoSelect);
			js.executeScript("arguments[0].click();", comparativoSelect);

			boolean comparativoOk = false;
			List<WebElement> ops = comparativoSelect.findElements(By.tagName("option"));

			for (WebElement op : ops) {
				if (op.getText().trim().equalsIgnoreCase(comparativo)) {
					js.executeScript("arguments[0].selected = true;", op);
					comparativoOk = true;
					break;
				}
			}

			if (!comparativoOk)
				throw new RuntimeException("No se encontró comparativo '" + comparativo + "'");

			js.executeScript("arguments[0].dispatchEvent(new Event('change'));", comparativoSelect);
			System.out.println("✅ Comparativo seleccionado: " + comparativo);

			// 7️⃣ Input de valor
			System.out.println("🔹 Ingresando valor...");
			WebElement valorInput = wait.until(d -> buscarDentroDeShadowDoom(d, "input.filerTableInput"));

			new Actions(driver).moveToElement(valorInput).click().sendKeys(valor).sendKeys(Keys.TAB).perform();

			System.out.println("✅ Valor ingresado: " + valor);

			// 8️⃣ Ejecutar filtro
			System.out.println("🔹 Ejecutando filtro...");
			WebElement botonEjecutar = wait
					.until(d -> buscarDentroDeShadowDoom(d, "button#test_filter_action_toolbar_run"));
			js.executeScript("arguments[0].click();", botonEjecutar);

			System.out.println("✅ Filtro ejecutado correctamente");

			driver.switchTo().defaultContent();
			return true;

		} catch (Exception e) {
			System.out.println("❌ Error en filtrarArchivosEBS: " + e.getMessage());
			driver.switchTo().defaultContent();
			return false;
		}
	}

	/**
	 * Validar si existen registros o si la lista está vacía en la interfaz dentro de Shadow DOM.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @return true si no hay registros disponibles; false si hay registros o en caso de error.
	 * @date 16/02/2026
	 */
	public boolean validarRegistros() {
		WebDriver driver = u.getDriver();

		System.out.println("🔹 Entrando a validarRegistros");

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(8));

			// 1️⃣ Localizar iframe dentro del shadow DOM (con reintentos)
			System.out.println("🔹 Buscando iframe gsft_main...");

			WebElement iframe = null;
			int intentos = 0;

			while (iframe == null && intentos < 5) {
				iframe = buscarDentroDeShadowDoom(driver, "iframe#gsft_main");

				if (iframe == null) {
					intentos++;
					System.out.println("⏳ Iframe no disponible (intento " + intentos + "/5)");
					Thread.sleep(400);
				}
			}

			if (iframe == null)
				throw new RuntimeException("No se encontró iframe gsft_main tras múltiples intentos");

			driver.switchTo().frame(iframe);
			System.out.println("✅ Iframe encontrado");

			// 2️⃣ Buscar mensaje de "No hay registros"
			System.out.println("🔹 Validando mensaje de lista vacía...");

			WebElement mensajeVacio = wait.until(d -> {
				WebElement el = buscarDentroDeShadowDoom(d, "div.list2_empty-state-list");
				if (el == null)
					return null;

				String texto = el.getText();
				if (texto != null && texto.contains("No hay ningún registro")) {
					return el;
				}
				return null;
			});

			if (mensajeVacio != null) {
				System.out.println("⚠️ No hay registros aún");
				return true; // SIN registros
			}

			return false; // HAY registros

		} catch (TimeoutException te) {
			// No apareció el mensaje → hay registros
			System.out.println("✅ Hay registros disponibles (mensaje no presente)");
			return false;

		} catch (Exception e) {
			System.out.println("❌ Error en validarRegistros: " + e.getMessage());
			return false;

		} finally {
			driver.switchTo().defaultContent();
			System.out.println("🔹 Saliendo de validarRegistros");
		}
	}

	
	/**
	 * Esperar activamente la aparición de un archivo CSV con refresco periódico de la página.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @return true si el CSV aparece dentro del tiempo límite; false si no aparece o hay error.
	 * @date 16/02/2026
	 */
	public boolean esperarCSVConRefresh() {
		WebDriver driver = u.getDriver();

		System.out.println("🔹 Iniciando espera activa de CSV (máx 15 minutos)");

		long tiempoMaximo = System.currentTimeMillis() + (15 * 60 * 1000); // 15 minutos
		int intento = 1;

		try {
			while (System.currentTimeMillis() < tiempoMaximo) {

				System.out.println("🔁 Intento #" + intento);

				// 1️⃣ Validar si el CSV ya apareció
				boolean sinRegistros = validarRegistros();

				if (!sinRegistros) {
					System.out.println("✅ CSV disponible, saliendo de espera");
					return true;
				}

				// 2️⃣ Micro-movimiento del mouse (anti-sleep)
				System.out.println("🖱️ Moviendo mouse para evitar sleep");
				moverMouseReal();

				// 3️⃣ Esperar 15 segundos antes de refrescar
				System.out.println("⏳ Esperando 15 segundos antes de refrescar...");
				Thread.sleep(15000);

				// 4️⃣ Refrescar página
				System.out.println("🔄 Refrescando página");
				driver.navigate().refresh();

				intento++;
			}

			System.out.println("⏰ Tiempo máximo alcanzado (15 minutos). CSV no disponible");
			return false;

		} catch (Exception e) {
			System.out.println("❌ Error en esperarCSVConRefresh: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Mover el cursor del mouse de forma aleatoria para evitar que el sistema entre en estado de inactividad.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @date 16/02/2026
	 */
	private void moverMouseReal() {
	    try {
	        Robot robot = new Robot();
	        Random rand = new Random();
	 
	        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
	        Point punto = pointerInfo.getLocation();
	 
	        int dx = rand.nextInt(10) - 5;
	        int dy = rand.nextInt(10) - 5;
	 
	        robot.mouseMove(punto.x + dx, punto.y + dy);
	 
	        System.out.println("🖱️ Mouse REAL movido");
	 
	    } catch (Exception e) {
	        System.out.println("⚠️ No se pudo mover mouse real: " + e.getMessage());
	    }
	}

	/**
	 * Monitorear activamente la carga de la pantalla verificando la presencia del botón de filtro.
	 * Realiza hasta 3 refrescos de página con espera y movimientos de mouse para evitar inactividad.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @return true si la pantalla está lista (botón detectado); false si no se detecta tras los refrescos.
	 * @date 16/02/2026
	 */
	public boolean esperarPantallaLista() {
		WebDriver driver = u.getDriver();

		System.out.println("🟡 Iniciando monitoreo de pantalla lista (botón de filtro)");

		int maxRefresh = 3;
		int refreshActual = 1;

		while (refreshActual <= maxRefresh) {

			System.out.println("🔎 Ciclo de monitoreo #" + refreshActual + " de " + maxRefresh);
			long tiempoLimite = System.currentTimeMillis() + (60 * 1000); // 1 minuto

			while (System.currentTimeMillis() < tiempoLimite) {

				try {
					System.out.println("🔍 Buscando iframe gsft_main...");
					WebElement iframe = buscarDentroDeShadowDoom(driver, "iframe#gsft_main");

					if (iframe == null) {
						System.out.println("❌ Iframe gsft_main NO encontrado aún");
					} else {
						System.out.println("✅ Iframe gsft_main encontrado");
						driver.switchTo().frame(iframe);

						System.out.println("🔍 Buscando botón de filtro...");
						WebElement botonFiltro = buscarDentroDeShadowDoom(driver,
								"button#u_archivos_ebs_filter_toggle_image");

						driver.switchTo().defaultContent();

						if (botonFiltro != null) {
							System.out.println("✅ Botón de filtro DETECTADO. Pantalla lista");
							return true;
						} else {
							System.out.println("⏳ Botón de filtro aún NO presente");
						}
					}

				} catch (Exception e) {
					System.out.println("⚠️ Excepción durante monitoreo: " + e.getMessage());
					driver.switchTo().defaultContent();
				}

				// Anti-sleep
				System.out.println("🖱️ Moviendo mouse (pantalla aún no lista)");
				moverMouseReal();

				try {
					System.out.println("⏲️ Esperando 5 segundos para reintentar...");
					Thread.sleep(5000);
				} catch (InterruptedException ignored) {
				}
			}

			System.out.println("🔄 Botón no apareció en 60s. Refrescando página...");
			driver.navigate().refresh();
			refreshActual++;
		}

		System.out.println("⛔ Pantalla NO logró cargarse tras " + maxRefresh + " refresh");
		return false;
	}

	
	/**
	 * Esperar activamente la aparición de un archivo CSV con validación y refresco de pantalla.
	 * Incluye verificación previa de que la pantalla esté lista antes de iniciar el monitoreo.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @return true si el CSV aparece dentro del tiempo límite; false si no aparece o hay error.
	 * @date 16/02/2026
	 */
	public boolean esperarCSVConRefresh2() {
		WebDriver driver = u.getDriver();

		System.out.println("🟢 Iniciando espera activa de CSV (máx 15 minutos)");

		// 1️⃣ Asegurar pantalla lista
		if (!esperarPantallaLista()) {
			System.out.println("⛔ Pantalla no lista. Abortando espera de CSV");
			return false;
		}

		long tiempoMaximo = System.currentTimeMillis() + (15 * 60 * 1000);
		int ciclo = 1;

		try {
			while (System.currentTimeMillis() < tiempoMaximo) {

				System.out.println("🔁 Validación CSV ciclo #" + ciclo);

				boolean sinRegistros = validarRegistros();

				if (!sinRegistros) {
					System.out.println("🎉 CSV disponible detectado");
					return true;
				}

				System.out.println("⚠️ CSV aún no disponible");

				System.out.println("🖱️ Moviendo mouse");
				moverMouseReal();

				System.out.println("⏳ Esperando 15 segundos...");
				Thread.sleep(15000);

				System.out.println("🔄 Refrescando página");
				driver.navigate().refresh();

				// Re-validar pantalla lista después del refresh
				if (!esperarPantallaLista()) {
					System.out.println("⚠️ Pantalla no lista tras refresh, continuando ciclo");
				}

				ciclo++;
			}

			System.out.println("⏰ Tiempo máximo alcanzado. CSV no apareció");
			return false;

		} catch (Exception e) {
			System.out.println("❌ Error en esperarCSVConRefresh: " + e.getMessage());
			return false;
		}
	}

	private boolean estaPantallaLista() {
		WebDriver driver = u.getDriver();

		try {
			WebElement iframe = buscarDentroDeShadowDoom(driver, "iframe#gsft_main");
			if (iframe == null) {
				System.out.println("[READY] ⏳ Iframe aún no disponible");
				return false;
			}

			driver.switchTo().frame(iframe);

			// Buscar cualquier botón cuyo ID termine con _filter_toggle_image
			List<WebElement> botones = driver.findElements(By.cssSelector("button[id$='_filter_toggle_image']"));

			driver.switchTo().defaultContent();

			if (botones != null && !botones.isEmpty()) {
				System.out.println("[READY] ✅ Botón de filtro detectado: " + botones.size() + " encontrado(s)");
				return true;
			}

			System.out.println("[READY] ⏳ Ningún botón de filtro detectado");
			return false;

		} catch (NoSuchElementException | StaleElementReferenceException e) {
			driver.switchTo().defaultContent();
			System.out.println("[READY] ⏳ DOM re-renderizando (referencia inválida)");
			return false;
		} catch (Exception e) {
			driver.switchTo().defaultContent();
			System.out.println("[READY] ❌ Error inesperado validando pantalla lista: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Verificar si la pantalla está lista comprobando la presencia de botón de filtro dentro del iframe.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @return true si se detecta el botón de filtro; false si no o ante error.
	 * @date 16/02/2026
	 */
	public boolean esperarCSVConRefresh3() {
		WebDriver driver = u.getDriver();

		System.out.println("🟢 Iniciando espera de CSV (timeout global: 15 minutos)");

		long finGlobal = System.currentTimeMillis() + (15 * 60 * 1000);
		EstadoPantalla estado = EstadoPantalla.NORMAL;

		int cicloCSV = 1;

		while (System.currentTimeMillis() < finGlobal) {

			System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
			System.out.println("⏱️ Tiempo restante: " + ((finGlobal - System.currentTimeMillis()) / 1000) + "s");
			System.out.println("📌 Estado actual: " + estado);
			System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

			try {

				/*
				 * ========================= 🟢 ESTADO NORMAL =========================
				 */
				if (estado == EstadoPantalla.NORMAL) {

					System.out.println("[CSV] 🔁 Ciclo #" + cicloCSV);

					boolean sinRegistros = validarRegistros();

					if (!sinRegistros) {
						System.out.println("[CSV] 🎉 CSV DISPONIBLE - Fin exitoso");
						return true;
					}

					System.out.println("[CSV] ⚠️ CSV aún no disponible");

					// Anti-sleep
					moverMouseReal();

					System.out.println("[CSV] ⏳ Esperando 15 segundos...");
					Thread.sleep(15000);

					// Antes de refrescar, validamos que la pantalla siga sana
					if (estaPantallaLista()) {
						System.out.println("[CSV] 🔄 Refrescando página");
						driver.navigate().refresh();
					} else {
						System.out.println("[CSV] 🛑 Pantalla rota detectada → entrando en RECUPERACION");
						estado = EstadoPantalla.RECUPERACION;
					}

					cicloCSV++;
				}

				/*
				 * ========================= 🛑 ESTADO RECUPERACION =========================
				 */
				else {

					System.out.println("[RECUPERACION] 🔍 Monitoreando botón de filtro (cada 5s)");

					if (estaPantallaLista()) {
						System.out.println("[RECUPERACION] ✅ Pantalla restablecida → regresando a NORMAL");
						estado = EstadoPantalla.NORMAL;
						continue;
					}

					moverMouseReal();

					System.out.println("[RECUPERACION] ⏲️ Esperando 5 segundos...");
					Thread.sleep(5000);
				}

			} catch (Exception e) {
				System.out.println("❌ Error en ciclo principal: " + e.getMessage());
			}
		}

		System.out.println("⛔ Timeout global alcanzado (15 minutos). CSV no apareció");
		return false;
	}

	/**
	 * Esperar activamente la aparición de registros en pantalla, con manejo de estados normal y de recuperación.
	 * Realiza movimientos de mouse y refrescos de página, controlando tiempos y recuperaciones por pantalla rota.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @return true si los registros aparecen dentro del tiempo límite; false si no aparecen.
	 * @date 16/02/2026
	 */
	public boolean esperarRegistros() {
		WebDriver driver = u.getDriver();

		log("🟢 Iniciando espera de registros (timeout global: 15 minutos)");

		long finGlobal = System.currentTimeMillis() + (15 * 60 * 1000);
		EstadoPantalla estado = EstadoPantalla.NORMAL;
		int cicloValidacion = 1;

		while (System.currentTimeMillis() < finGlobal) {

			long restante = finGlobal - System.currentTimeMillis();

			log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
			log("⏱️ Tiempo restante: " + formatoTiempoRestante(restante));
			log("📌 Estado actual: " + estado);
			log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

			try {

				if (estado == EstadoPantalla.NORMAL) {

					log("[REGISTROS] 🔁 Ciclo #" + cicloValidacion);

					boolean sinRegistros = validarRegistros(); // misma lógica, mismo método

					if (!sinRegistros) {
						log("[REGISTROS] 🎉 REGISTROS DISPONIBLES - Fin exitoso");
						return true;
					}

					log("[REGISTROS] ⚠️ Registros aún no disponibles");

					moverMouseReal();
					log("[REGISTROS] 🖱️ Movimiento de mouse realizado");

					log("[REGISTROS] ⏳ Esperando 15 segundos...");
					Thread.sleep(15000);

					if (estaPantallaLista()) {
						log("[REGISTROS] 🔄 Refrescando página");
						driver.navigate().refresh();
					} else {
						log("[REGISTROS] 🛑 Pantalla rota detectada → entrando en RECUPERACION");
						estado = EstadoPantalla.RECUPERACION;
					}

					cicloValidacion++;
				} else {
					log("[RECUPERACION] 🔍 Monitoreando botón de filtro (cada 5s)");

					if (estaPantallaLista()) {
						log("[RECUPERACION] ✅ Pantalla restablecida → regresando a NORMAL");
						estado = EstadoPantalla.NORMAL;
						continue;
					}

					moverMouseReal();
					log("[RECUPERACION] 🖱️ Movimiento de mouse realizado");

					log("[RECUPERACION] ⏲️ Esperando 5 segundos...");
					Thread.sleep(5000);
				}

			} catch (Exception e) {
				log("❌ Error en ciclo principal (controlado)");
			}
		}

		log("⛔ Timeout global alcanzado (15 minutos). No aparecieron registros");
		return false;
	}

	/**
	 * Validar si un registro está en estado procesado, esperando si es necesario.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param valor Valor asociado al registro que se desea validar.
	 * @return true si el registro ya está procesado o alcanza ese estado tras espera; false en otro caso.
	 * @date 16/02/2026
	 */
	public boolean validarEstadoRegistroProcesado(String valor) {

		System.out.println("🔹 Validando estado del registro para valor: " + valor);

		// 1️⃣ Primera validación inmediata
		boolean procesado = leerEstadoRegistroProcesado(valor);

		if (procesado) {
			System.out.println("🎉 Registro YA se encuentra en estado PROCESADO");
			return true;
		}

		// 2️⃣ Si no está procesado, esperar hasta que lo esté
		System.out.println("⏳ Registro NO procesado aún → iniciando espera");
		return esperarRegistroHastaProcesado(valor);
	}

	public boolean esperarRegistroHastaProcesado(String valor) {
		WebDriver driver = u.getDriver();

		log("🟢 Iniciando espera de procesamiento del registro: " + valor);

		long finGlobal = System.currentTimeMillis() + (15 * 60 * 1000);
		int ciclo = 1;

		while (System.currentTimeMillis() < finGlobal) {

			long restante = finGlobal - System.currentTimeMillis();

			log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
			log("⏱️ Tiempo restante: " + formatoTiempoRestante(restante));
			log("🔁 Ciclo #" + ciclo);
			log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

			try {

				if (leerEstadoRegistroProcesado(valor)) {
					log("🎉 Registro en estado PROCESADO → Fin exitoso");
					return true;
				}

				log("⚠️ Registro aún NO procesado");

				moverMouseReal();
				log("🖱️ Movimiento de mouse realizado");

				log("⏳ Esperando 15 segundos...");
				Thread.sleep(15000);

				if (estaPantallaLista()) {
					log("🔄 Refrescando página");
					driver.navigate().refresh();
				} else {
					log("🛑 Pantalla no lista, esperando recuperación...");
					Thread.sleep(5000);
				}

				ciclo++;

			} catch (Exception e) {
				log("❌ Error controlado en espera de procesamiento");
			}
		}

		log("⛔ Timeout global alcanzado. Registro NO llegó a PROCESADO");
		return false;
	}

	
	/**
	 * Esperar activamente hasta que un registro alcance el estado procesado con tiempo límite y recuperación.
	 * Incluye movimientos de mouse, espera con ciclos, y refresco de página según estado de pantalla.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param valor Valor del registro que se monitorea.
	 * @return true si el registro alcanza estado procesado dentro del tiempo; false si no.
	 * @date 16/02/2026
	 */
	private boolean leerEstadoRegistroProcesado(String valor) {
		WebDriver driver = u.getDriver();

		try {
			WebElement iframe = buscarDentroDeShadowDoom(driver, "iframe#gsft_main");
			if (iframe == null)
				throw new RuntimeException("No se encontró iframe gsft_main");

			driver.switchTo().frame(iframe);

			List<WebElement> filas = driver.findElements(By.cssSelector("tr.list_row"));

			for (WebElement fila : filas) {

				WebElement nombreArchivo = fila.findElement(By.cssSelector("td a.linked.formlink"));

				String textoArchivo = nombreArchivo.getText().trim();

				if (textoArchivo.contains("ebs_sn_validacion_fiscal_" + valor)) {

					List<WebElement> columnas = fila.findElements(By.tagName("td"));
					//La columna -2 es la penúltima columna, y es la que contiene el estado. Si se modifica la vista, ajustar en esta parte.
					String estado = columnas.get(columnas.size() - 2).getText().trim();

					System.out.println("📄 Archivo encontrado: " + textoArchivo);
					System.out.println("📌 Estado actual: " + estado);

					return estado.equalsIgnoreCase("Procesado");
				}
			}

			System.out.println("⏳ Registro aún no presente en la lista");
			return false;

		} catch (Exception e) {
			System.out.println("❌ Error leyendo estado del registro: " + e.getMessage());
			return false;

		} finally {
			driver.switchTo().defaultContent();
		}
	}

	/**
	 * Validar el estado de una factura MAF por folio, esperando si el estado no está definido.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param folioEntrada Folio de la factura MAF a validar.
	 * @return true si la factura tiene estado válido; false si no o tras espera.
	 * @date 16/02/2026
	 */
	public boolean validarEstadoFacturaMAF(String folioEntrada) {

		log("🔹 Validando Estatus de Factura MAF | Folio: " + folioEntrada);

		Boolean estado = leerEstadoFacturaMAF(folioEntrada);

		if (estado != null) {
			return estado;
		}

		log("⏳ Estatus aún no definido → iniciando espera MAF");
		return esperarFacturaMAFHastaFinal(folioEntrada);
	}

	
	/**
	 * Esperar activamente hasta que una factura MAF alcance un estado válido o final dentro del tiempo límite.
	 * Realiza movimientos de mouse, espera en ciclos y refresco de página según la disponibilidad de la pantalla.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param folioEntrada Folio de la factura MAF que se monitorea.
	 * @return true si la factura tiene estado válido dentro del tiempo; false si no.
	 * @date 16/02/2026
	 */
	public boolean esperarFacturaMAFHastaFinal(String folioEntrada) {

		WebDriver driver = u.getDriver();

		log("🟢 Iniciando espera MAF para folio: " + folioEntrada);

		long finGlobal = System.currentTimeMillis() + (15 * 60 * 1000);
		int ciclo = 1;

		while (System.currentTimeMillis() < finGlobal) {

			long restante = finGlobal - System.currentTimeMillis();

			log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
			log("⏱️ Tiempo restante: " + formatoTiempoRestante(restante));
			log("🔁 Ciclo #" + ciclo);
			log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

			try {

				Boolean estado = leerEstadoFacturaMAF(folioEntrada);

				if (estado != null) {
					return estado;
				}

				log("⚠️ Estatus vacío o no disponible aún");

				moverMouseReal();
				log("🖱️ Movimiento de mouse realizado");

				log("⏳ Esperando 15 segundos...");
				Thread.sleep(15000);

				if (estaPantallaLista()) {
					log("🔄 Refrescando página MAF");
					driver.navigate().refresh();
				} else {
					log("🛑 Pantalla no lista, esperando recuperación...");
					Thread.sleep(5000);
				}

				ciclo++;

			} catch (Exception e) {
				log("❌ Error controlado durante espera MAF");
			}
		}

		log("⛔ Timeout alcanzado en MAF. Factura no validada");
		return false;
	}

	
	/**
	 * Leer el estado actual de una factura MAF dentro de la tabla en la interfaz.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param folioEntrada Folio de la factura MAF a consultar.
	 * @return true si la factura está validada, false si está errónea, o null si el estado no está definido o error.
	 * @date 16/02/2026
	 */
	private Boolean leerEstadoFacturaMAF(String folioEntrada) {

		WebDriver driver = u.getDriver();

		try {
			WebElement iframe = buscarDentroDeShadowDoom(driver, "iframe#gsft_main");
			if (iframe == null)
				throw new RuntimeException("No se encontró iframe gsft_main");

			driver.switchTo().frame(iframe);

			List<WebElement> filas = driver.findElements(By.cssSelector("#u_datos_maf_table tbody tr.list_row"));

			if (filas.isEmpty()) {
				log("⏳ Tabla MAF sin registros aún");
				return null;
			}

			// Tomamos la PRIMER fila (ya viene ordenada DESC)
			WebElement fila = filas.get(0);
			List<WebElement> columnas = fila.findElements(By.tagName("td"));

			// Última columna = Estatus de Factura
			String estatus = columnas.get(columnas.size() - 2).getText().trim();

			log("📄 Estatus de Factura encontrado: [" + estatus + "]");

			if (estatus.isEmpty()) {
				return null;
			}

			if (estatus.equalsIgnoreCase("Validada")) {
				log("✅ Factura VALIDADA");
				return true;
			}

			if (estatus.equalsIgnoreCase("Errónea")) {
				log("❌ Factura ERRONEA");
				return false;
			}

			log("⚠️ Estatus no esperado: " + estatus);
			return null;

		} catch (Exception e) {
			log("❌ Error leyendo estatus MAF: " + e.getMessage());
			return null;

		} finally {
			driver.switchTo().defaultContent();
		}
	}

	/**
	 * Abrir el primer registro de la tabla dentro del iframe de la interfaz web.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @date 16/02/2026
	 */
	public void abrirPrimerRegistroDeTabla() {

		WebDriver driver = u.getDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		System.out.println("🔹 Abriendo primer registro de la tabla");

		try {
			// 1️⃣ Entrar al iframe
			WebElement iframe = wait.until(d -> buscarDentroDeShadowDoom(d, "iframe#gsft_main"));
			if (iframe == null)
				throw new RuntimeException("No se encontró iframe gsft_main");

			driver.switchTo().frame(iframe);
			System.out.println("✅ Iframe gsft_main activo");

			// 2️⃣ Esperar primera fila
			WebElement primeraFila = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr.list_row")));

			// 3️⃣ Tomar el ID (primer link formlink de la fila)
			WebElement linkId = primeraFila.findElement(By.cssSelector("td a.linked.formlink"));

			String idTexto = linkId.getText().trim();
			System.out.println("📄 ID encontrado: " + idTexto);

			// 4️⃣ Click
			linkId.click();
			System.out.println("✅ Click realizado sobre el ID");

		} catch (Exception e) {
			System.out.println("❌ Error al abrir primer registro: " + e.getMessage());
			throw e;

		} finally {
			driver.switchTo().defaultContent();
		}
	}

	/**
	 * Filtrar facturas en la interfaz web usando criterios específicos dentro del Shadow DOM.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param filtroNombre Nombre del campo o filtro a aplicar.
	 * @param comparativo Operador comparativo para el filtro (ejemplo: igual, contiene).
	 * @param valor Valor que se va a usar para filtrar.
	 * @return true si el filtro se aplicó correctamente; false en caso de error.
	 * @date 16/02/2026
	 */
	@SuppressWarnings("unchecked")
	public boolean filtrarFacturas(String filtroNombre, String comparativo, String valor) {
		WebDriver driver = u.getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		System.out.println("🔹 Entrando a filtrarFacturas");
		System.out.println("🔹 Parámetros: filtro='" + filtroNombre + "', comparativo='" + comparativo + "', valor='"
				+ valor + "'");

		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

			// 1️⃣ Iframe dentro de shadow DOM
			System.out.println("🔹 Buscando iframe...");
			WebElement iframe = buscarDentroDeShadowDoom(driver, "iframe#gsft_main");
			if (iframe == null)
				throw new RuntimeException("No se encontró iframe gsft_main");

			driver.switchTo().frame(iframe);
			System.out.println("✅ Iframe encontrado");

			// 2️⃣ Botón de filtro
			System.out.println("🔹 Buscando botón de filtro...");
			WebElement botonFiltro = wait
					.until(d -> buscarDentroDeShadowDoom(d, "button#u_folios_de_facturas_filter_toggle_image"));
			js.executeScript("arguments[0].click();", botonFiltro);
			System.out.println("✅ Botón de filtro clickeado");

			// 3️⃣ Abrir selector de campo
			System.out.println("🔹 Abriendo selector de campo...");
			WebElement selectCampo = wait.until(d -> buscarDentroDeShadowDoom(d, "span.select2-chosen"));
			new Actions(driver).moveToElement(selectCampo).click().perform();

			// 4️⃣ Esperar dropdown activo
			WebElement dropContainer = wait.until(
					d -> (WebElement) js.executeScript("return document.querySelector('div.select2-drop-active');"));
			System.out.println("✅ Dropdown visible");

			// 5️⃣ Seleccionar campo
			WebElement opcionCampo = null;
			long endCampo = System.currentTimeMillis() + 10000;

			while (System.currentTimeMillis() < endCampo) {
				List<WebElement> opciones = (List<WebElement>) js.executeScript(
						"return Array.from(arguments[0].querySelectorAll('li.select2-result'));", dropContainer);

				for (WebElement op : opciones) {
					if (op.getText().trim().equalsIgnoreCase(filtroNombre)) {
						opcionCampo = op;
						break;
					}
				}
				if (opcionCampo != null)
					break;
			}

			if (opcionCampo == null)
				throw new RuntimeException("No se encontró el campo '" + filtroNombre + "'");

			new Actions(driver).moveToElement(opcionCampo).click().perform();
			System.out.println("✅ Campo seleccionado: " + filtroNombre);

			// 6️⃣ Seleccionar comparativo
			System.out.println("🔹 Esperando comparativo...");
			WebElement comparativoSelect = wait
					.until(d -> buscarDentroDeShadowDoom(d, "select.filerTableSelect.condOperator"));

			js.executeScript("arguments[0].scrollIntoView({block:'center'});", comparativoSelect);
			js.executeScript("arguments[0].click();", comparativoSelect);

			boolean comparativoOk = false;
			List<WebElement> ops = comparativoSelect.findElements(By.tagName("option"));
			for (WebElement op : ops) {
				if (op.getText().trim().equalsIgnoreCase(comparativo)) {
					js.executeScript("arguments[0].selected = true;", op);
					comparativoOk = true;
					break;
				}
			}

			if (!comparativoOk)
				throw new RuntimeException("No se encontró comparativo '" + comparativo + "'");

			js.executeScript("arguments[0].dispatchEvent(new Event('change'));", comparativoSelect);
			System.out.println("✅ Comparativo seleccionado: " + comparativo);

			// 7️⃣ Input de valor
			System.out.println("🔹 Ingresando valor...");
			WebElement valorInput = wait.until(d -> buscarDentroDeShadowDoom(d, "input.filerTableInput"));

			new Actions(driver).moveToElement(valorInput).click().sendKeys(valor).sendKeys(Keys.TAB).perform();

			System.out.println("✅ Valor ingresado: " + valor);

			// 8️⃣ Ejecutar filtro
			System.out.println("🔹 Ejecutando filtro...");
			WebElement botonEjecutar = wait
					.until(d -> buscarDentroDeShadowDoom(d, "button#test_filter_action_toolbar_run"));
			js.executeScript("arguments[0].click();", botonEjecutar);

			System.out.println("✅ Filtro ejecutado correctamente");

			driver.switchTo().defaultContent();
			return true;

		} catch (Exception e) {
			System.out.println("❌ Error en filtrarFacturas: " + e.getMessage());
			driver.switchTo().defaultContent();
			return false;
		}
	}

	/**
	 * Realizar scroll hasta el fondo del formulario MAF, enfocando el botón de actualización.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @date 16/02/2026
	 */
	public void scrollearHastaFondoFormularioMAF() {
		WebDriver driver = u.getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		System.out.println("🔹 Entrando a scrollearHastaFondoFormularioMAF");

		try {
			WebElement iframe = buscarDentroDeShadowDoom(driver, "iframe#gsft_main");
			if (iframe == null) {
				System.out.println("❌ Iframe gsft_main no encontrado");
				return;
			}

			driver.switchTo().frame(iframe);
			System.out.println("✅ Iframe detectado");

			// Buscar el botón de referencia al final del formulario
			WebElement botonFinal = driver.findElement(By.id("sysverb_update_bottom"));

			// Scroll hasta el botón
			js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'end'});", botonFinal);
			System.out.println("✅ Scroll hasta el fondo realizado sobre el botón 'Actualizar'");

			// Esperar un momento para que el scroll termine visualmente
			Thread.sleep(500);

			driver.switchTo().defaultContent();
		} catch (Exception e) {
			driver.switchTo().defaultContent();
			System.out.println("❌ Error scrolleando hasta fondo: " + e.getMessage());
		}
	}

	
	/**
	 * Hacer clic en la pestaña 'Factura' dentro del iframe de la interfaz web.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @return true si el clic fue exitoso; false si no se encontró el botón o hubo error.
	 * @date 16/02/2026
	 */
	public boolean clickBotonFactura() {
		WebDriver driver = u.getDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;

		System.out.println("🔹 Entrando a clickBotonFactura");

		try {
			// 1️⃣ Buscar iframe dentro del shadow DOM
			WebElement iframe = buscarDentroDeShadowDoom(driver, "iframe#gsft_main");
			if (iframe == null) {
				System.out.println("❌ Iframe gsft_main no encontrado");
				return false;
			}

			driver.switchTo().frame(iframe);
			System.out.println("✅ Iframe detectado");

			// 2️⃣ Buscar el span que representa la pestaña 'Factura'
			List<WebElement> tabs = driver.findElements(By.cssSelector("span.tab_caption_text"));
			WebElement facturaTab = null;

			for (WebElement tab : tabs) {
				if (tab.getText().trim().equalsIgnoreCase("Factura")) {
					facturaTab = tab;
					break;
				}
			}

			if (facturaTab == null) {
				System.out.println("❌ No se encontró el botón 'Factura'");
				driver.switchTo().defaultContent();
				return false;
			}

			// 3️⃣ Scroll hasta el elemento (por si no está visible)
			js.executeScript("arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", facturaTab);

			// 4️⃣ Click sobre el botón
			js.executeScript("arguments[0].click();", facturaTab);
			System.out.println("✅ Botón 'Factura' clickeado");

			driver.switchTo().defaultContent();
			return true;

		} catch (Exception e) {
			driver.switchTo().defaultContent();
			System.out.println("❌ Error clickeando 'Factura': " + e.getMessage());
			return false;
		}
	}

	/**
     * Extraer datos de una fila específica de un archivo Excel en la hoja "Insumos QA Diverza".
     * @author Jesus Moreno jesusm3@hexaware.com
     * @param ruta Ruta del archivo Excel.
     * @param fila Número de fila (base 0) de la cual extraer los datos.
     * @return Mapa con los encabezados como claves y los valores extraídos de la fila.
     * @date 16/02/2026
     */
	public Map<String, String> extraerDatosExcel(String ruta, int fila) {

		Map<String, String> datos = new LinkedHashMap<>();

		File archivo = new File(ruta);

		// 1. Verificar que exista el archivo
		if (!archivo.exists()) {
			throw new RuntimeException("El archivo no existe: " + ruta);
		}

		// El contador siempre llega empezando en 0, el header está en 0
		int filaReal = fila + 1;

		String[] headers = { "Factura Diverza", "Invoice Num", "Receipt Num", "Subtotal", "Orden de Compra",
				"Tipo de Orden de Compra", "Moneda", "Numeros de artículo", "Tipos de artículo", "KIT/Grupo",
				"Proveedor" };

		try (FileInputStream fis = new FileInputStream(archivo); Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheet("Insumos QA Diverza");

			if (sheet == null) {
				throw new RuntimeException("No se encontró la hoja 'Insumos QA Diverza'");
			}

			Row row = sheet.getRow(filaReal);

			if (row == null) {
				throw new RuntimeException("La fila " + filaReal + " no existe en la hoja 'Insumos QA Diverza'");
			}

			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			DataFormatter formatter = new DataFormatter();

			// 2. Extraer cada columna
			for (int i = 0; i < headers.length; i++) {

				Cell cell = row.getCell(i);
				String valor = "";

				if (cell != null) {
					valor = formatter.formatCellValue(cell, evaluator);
				}

				datos.put(headers[i], valor);
			}

			// Se eliminó la impresión para evitar llenar la consola

		} catch (Exception e) {
			throw new RuntimeException("Error al leer el archivo Excel", e);
		}

		// 4. Retornar valores
		return datos;
	}

	/**
	 * Esperar activamente la aparición de un archivo SnowFE con tiempo límite y monitoreo de estado.
	 * Incluye movimientos de mouse y ciclos de espera para evitar inactividad.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param driver Instancia de WebDriver para interacción si es necesaria.
	 * @param host Dirección del servidor SFTP.
	 * @param port Puerto para conexión SFTP.
	 * @param user Usuario para autenticación SFTP.
	 * @param password Contraseña para autenticación SFTP.
	 * @param ruta Ruta en servidor donde se busca el archivo.
	 * @param receiptNum Identificador para formar el nombre del archivo.
	 * @return true si el archivo se encuentra antes del timeout; false si no.
	 * @date 16/02/2026
	 */
	public boolean esperarArchivoSnowFE(WebDriver driver, String host, int port, String user, String password,
			String ruta, String receiptNum) {
		log("🟢 Iniciando espera del archivo SnowFE (timeout global: 15 minutos)");

		long finGlobal = System.currentTimeMillis() + (15 * 60 * 1000);
		EstadoPantalla estado = EstadoPantalla.NORMAL;
		int cicloValidacion = 1;

		while (System.currentTimeMillis() < finGlobal) {
			long restante = finGlobal - System.currentTimeMillis();

			log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
			log("⏱️ Tiempo restante: " + formatoTiempoRestante(restante));
			log("📌 Estado actual: " + estado);
			log("📂 Buscando en ruta: /" + ruta);
			log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

			try {
				if (estado == EstadoPantalla.NORMAL) {
					log("[SnowFE] 🔁 Ciclo #" + cicloValidacion);

					boolean archivoEncontrado = validarCSVSnowFE(host, port, user, password, ruta, receiptNum);

					if (archivoEncontrado) {
						log("[SnowFE] 🎉 Archivo encontrado - Fin exitoso");
						return true;
					}

					log("[SnowFE] ⚠️ Archivo aún no disponible");

					moverMouseReal();
					log("[SnowFE] 🖱️ Movimiento de mouse realizado");

					log("[SnowFE] ⏳ Esperando 15 segundos...");
					Thread.sleep(15000);

					// Aquí puedes agregar lógica para refrescar o validar pantalla si usas UI

					cicloValidacion++;
				} else {
					log("[RECUPERACION] 🔍 Monitoreando estado (cada 5s)");

					// Aquí puedes agregar lógica para detectar si la pantalla está lista

					moverMouseReal();
					log("[RECUPERACION] 🖱️ Movimiento de mouse realizado");

					log("[RECUPERACION] ⏲️ Esperando 5 segundos...");
					Thread.sleep(5000);
				}
			} catch (Exception e) {
				log("❌ Error en ciclo principal (controlado): " + e.getMessage());
			}
		}

		log("⛔ Timeout global alcanzado (15 minutos). No se encontró el archivo");
		return false;
	}

	/**
	 * Validar la existencia de un archivo CSV SnowFE en el servidor SFTP.
	 * 
	 * @author Jesus Moreno jesusm3@hexaware.com
	 * @param host Dirección del servidor SFTP.
	 * @param port Puerto de conexión SFTP.
	 * @param user Usuario para autenticación.
	 * @param password Contraseña para autenticación.
	 * @param ruta Ruta en el servidor donde se busca el archivo.
	 * @param receiptNum Identificador para formar el nombre del archivo CSV.
	 * @return true si el archivo existe; false si no o en caso de error.
	 * @date 16/02/2026
	 */
	public boolean validarCSVSnowFE(String host, int port, String user, String password, String ruta, String receiptNum) {

		String docName = "sn_ebs_fe_" + receiptNum + ".csv";
		String path = "/" + ruta + "/" + docName;
		Session session = null;
		ChannelSftp channelSftp = null;

		try {
			System.out.println("Procesando Receipt Num: " + receiptNum);

			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			session.connect();

			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();

			try {
				channelSftp.stat(path);
				return true;
			} catch (Exception e) {
				return false;
			}

		} catch (Exception e) {
			System.out.println("Error en conexión SFTP o búsqueda: " + e.getMessage());
			return false;

		} finally {
			if (channelSftp != null && channelSftp.isConnected()) {
				channelSftp.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
	}

}
