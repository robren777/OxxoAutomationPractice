package WEB.portal_APEX.pom;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.selenium.SeleniumUtil;

public class BasePage {

	private final Duration TIMEOUT = Duration.ofSeconds(60);
	private Wait<WebDriver> wait;

	/**
	 * Espera a que un elemento se encuentre visible despu�s de cierto tiempo.
	 *
	 * @author Edwin Chi
	 * @return Retorna el WebElement encontrado despu�s de la espera
	 * @param u  es la instancie de SeleniumUtil que viene de la p�gina
	 * @param by es el localizador que viene de la p�gina
	 * @date 29/Octubre/2024
	 */
	protected WebElement obtenerElementoVisible(SeleniumUtil u, By by) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), TIMEOUT);
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return element;

		} catch (NoSuchElementException | TimeoutException e) {
			throw new Exception("El elemento no carg� o no existe: " + e);
		}
	}

	/**
	 * Espera a que un elemento se encuentre invisible despu�s de cierto tiempo.
	 * Revisa cada 500 milisegundos si el elemento se ha ocultado hasta llegar al
	 * tiempo m�ximo establecido en el TIMEOUT.
	 *
	 * @author Edwin Chi
	 * @return Falso o Verdero seg�n el elmento es encontrado o no
	 * @param u  es la instancie de SeleniumUtil que viene de la p�gina
	 * @param by es el localizador que viene de la p�gina
	 * @date 29/Octubre/2024
	 */
	protected boolean esperarElementoInvisible(SeleniumUtil u, By by) throws Exception {
		try {
			u.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
			wait = new FluentWait<WebDriver>(u.getDriver()).withTimeout(TIMEOUT).pollingEvery(Duration.ofMillis(500))
					.ignoring(NoSuchElementException.class);

			return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));

		} catch (NoSuchElementException | TimeoutException e) {
			throw new Exception("El elemento no desapareci� en pantalla: " + e);
		} finally {
			u.getDriver().manage().timeouts().implicitlyWait(TIMEOUT);
		}
	}

	/**
	 * Espera a que todos los elementos del localizador se encuentren visibles
	 * despu�s de cierto tiempo.
	 *
	 * @author Edwin Chi
	 * @return Lista de objetos del tipo WebElement con los resultados encontrados
	 * @param u  es la instancie de SeleniumUtil que viene de la p�gina
	 * @param by es el localizador que viene de la p�gina
	 * @date 29/Octubre/2024
	 */
	protected List<WebElement> getAllVisibleElements(SeleniumUtil u, By by) throws Exception {
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), TIMEOUT);
			return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));

		} catch (NoSuchElementException | TimeoutException e) {
			throw new Exception("El elemento no carg� o no existe: " + e);
		}
	}

	/**
	 * Presiona una tecla simulando una acci�n en el teclado. �til para escribir
	 * alg�n texto en pantalla sin especificar un localizador.
	 *
	 * @author Edwin Chi
	 * @param u    es la instancie de SeleniumUtil que viene de la p�gina
	 * @param keys cadena texto a escribir usando el teclado
	 * @date 29/Octubre/2024
	 */
	protected void pressKeys(SeleniumUtil u, String keys) {
		Actions action = new Actions(u.getDriver());
		action.sendKeys(keys).perform();
	}

	/**
	 * Presiona una tecla simulando una acci�n en el teclado. �til para presionar
	 * teclas especiales como CONTROL, ALT, SHIFT etc.
	 *
	 * @author Edwin Chi
	 * @param u    es la instancie de SeleniumUtil que viene de la p�gina
	 * @param keys tecla a presionar
	 * @date 29/Octubre/2024
	 */
	protected void pressKeys(SeleniumUtil u, Keys keys) {
		Actions action = new Actions(u.getDriver());
		action.sendKeys(keys).perform();
	}

	/**
	 * Espera a que un elemento se encuentre visible despu�s de cierto tiempo. Esta
	 * espera realiza tres intentos antes de arrojar una exception.
	 *
	 * @author Enrique Baruch
	 * @return Retorna el WebElement encontrado despu�s de la espera
	 * @param u  es la instancie de SeleniumUtil que viene de la p�gina
	 * @param by es el localizador que viene de la p�gina
	 * @date 29/Octubre/2024
	 */
	public WebElement obtenerElementoVisibleConIntentos(SeleniumUtil u, By by) throws Exception {
		for (int intentos = 0; intentos < 3; intentos++) {
			try {
				WebDriverWait wait = new WebDriverWait(u.getDriver(), TIMEOUT);
				WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
				return element; // Retorna el elemento si se encuentra visible
			} catch (NoSuchElementException | TimeoutException e) {
				if (intentos < 2) { // Si no es el �ltimo intento
					u.getDriver().navigate().refresh(); // Refresca la p�gina
					Thread.sleep(2000); // Espera un poco para permitir que la p�gina cargue
				} else {
					throw new Exception("El elemento no carg� o no existe despu�s de 3 intentos: " + e);
				}
			}
		}
		throw new Exception("Se alcanz� el n�mero m�ximo de reintentos y el elemento no se encontr�.");
	}

	/**
	 * Retorna la estructura de la fecha que necesita el input de fecha 01-sep.-2024
	 *
	 * @author Enrique Baruch
	 * @return retorna la fecha formateada 01-sep.-2024
	 * @param fecha Es el parametro de la fecha actual de la maquina
	 * @date 31/Octubre/2024
	 */

	public static String obtenerFechaFormateada(LocalDate fecha) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM.-yyyy", new Locale("es", "ES")); // Formato en
																											// espa�ol
		String fechaFormateada = fecha.format(formatter); // Formatear la fecha // espa�ol
		// Reemplazar "sept" por "sep"
		fechaFormateada = fechaFormateada.replace("..", ".");
		
		return fechaFormateada;
	}

	/**
	 * Borra todo lo que se encuentre en la carpeta de descargas
	 *
	 * @author Enrique Baruch
	 * @return retorna la fecha formateada 01-sep.-2024
	 * @param fecha Es el parametro de la fecha actual de la maquina
	 * @date 31/Octubre/2024
	 */
	public static void limpiarCarpeta(File carpeta) {
		File[] archivos = carpeta.listFiles(file -> file.isFile());

		if (archivos != null) {
			for (File archivo : archivos) {
				if (archivo.delete()) {
					System.out.println("Archivo eliminado: " + archivo.getName());
				} else {
					System.out.println("Error al eliminar: " + archivo.getName());
				}
			}
		} else {
			System.out.println("No se encontraron archivos en la carpeta.");
		}
	}

	/**
	 * Buscara el unico archivo xlsx y lo abrira
	 *
	 * @author Enrique Baruch
	 * @return retorna la fecha formateada 01-sep.-2024
	 * @param fecha Es el parametro de la fecha actual de la maquina
	 * @date 31/Octubre/2024
	 */

	public static void abrirPrimerXlsx(File carpeta) {
		File[] archivosXlsx = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".xlsx"));

		if (archivosXlsx != null && archivosXlsx.length > 0) {
			File archivoXlsx = archivosXlsx[0];
			try {
				Desktop desktop = Desktop.getDesktop();

				if (desktop.isSupported(Desktop.Action.OPEN)) {
					desktop.open(archivoXlsx);
					System.out.println("Archivo .xlsx abierto: " + archivoXlsx.getName());
				} else {
					System.out.println("La acci�n OPEN no es soportada en este sistema.");
				}
			} catch (IOException e) {
				System.out.println("Error al abrir el archivo: " + e.getMessage());
			}
		} else {
			System.out.println("No se encontraron archivos .xlsx en la carpeta de descargas.");
		}
	}

	/**
	 * Se cerrara el archivo de excel.
	 *
	 * @author Enrique Baruch
	 * @date 31/Octubre/2024
	 */
	public static void cerrarArchivosExcel() {
		String command = "powershell.exe Stop-Process -Name excel,scalc -Force";
		try {
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
			System.out.println("Todos los archivos de Excel han sido cerrados.");
		} catch (IOException | InterruptedException e) {
			System.out.println("Error al cerrar archivos de Excel: " + e.getMessage());
		}
	}
}
