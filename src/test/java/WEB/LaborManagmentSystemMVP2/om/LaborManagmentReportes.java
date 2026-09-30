package WEB.LaborManagmentSystemMVP2.om;

import java.io.File;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

//import org.eclipse.persistence.queries.SQLResult;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class LaborManagmentReportes {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra m;

	/**
	 * Localizadores
	 ******************************************************************************************/
	By menureporte = By.xpath("//a[normalize-space()='Reportes']");
	By pantallareporte = By.xpath("//h3[normalize-space()='Generar reporte']");
	By seleccionarreporte = By.xpath("//select[contains(@class, 'ng-untouched') or contains(@class, 'ng-dirty')]");
	By campotipodereporte = By.xpath("//select[@class='form-select ng-untouched ng-pristine ng-valid']");
	By campotiporeportelimpiado = By.xpath("//select[@class='form-select ng-valid ng-dirty ng-touched']");
	By campoano = By.xpath("//select[@formcontrolname='year']");
	By campocedis = By.xpath("//select[translate(@formcontrolname,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='cedis']");
	By botonlimpiar = By.xpath("//button[normalize-space()='Limpiar']");
	By camposemana = By.xpath("//select[@formcontrolname='week']");
	By botondescargar = By.xpath("//button[normalize-space()='Descargar']");
	By mensajeemergente = By.xpath("//div[@class='toast-body']");
	By loadingOverlay = By.cssSelector(".loading-overlay");

	/**
	 * Constructor
	 * 
	 ******************************************************************************************/
	public LaborManagmentReportes(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		m = new MetodosExtra(u, testCase);
	}

	/**
	 * Metodos Generales
	 * 
	 ******************************************************************************************/

	/**
	 * Método para ingresar a la pantalla "Generar reporte" desde el menú principal.
	 *
	 * @author Carlos Martínez Ortega
	 * @return true si la pantalla de "Generar reporte" se muestra de manera correcta.
	 *         false si ocurre alguno de los siguientes casos:
	 *         - en caso de error
	 *         - si la pantalla no se carga
	 * @date 26/Septiembre/2025
	 */
	public boolean opcionmenureporte() {
		boolean validacion = false;
		try {
			u.highLight(menureporte);
			u.click(menureporte);

			// Esperar a que desaparezca el overlay de carga
	        WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));

	        // Por alguna razón se cambia al primer menú y toca volver a seleccionar tabuladores
	        u.click(menureporte);

	        // Volvemos a esperar a que desaparezca el overlay por seguridad
	        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingOverlay));
			
			u.waitWebElement(pantallareporte, 30, 5);
			testCase.addScreenShotCurrentStep(u, "Se ingresa a la pantalla de Generar reporte de manera correcta.");

			if (u.isDisplayed(pantallareporte)) {
				validacion = true;
			}
		} catch (Exception e) {
			Assert.fail("No cargo la pantalla de Generar reporte");
		}
		return validacion;
	}

	/**
	 * Método para validar la correcta carga de la pantalla "Generar reporte".
	 *
	 * @author Carlos Martínez Ortega
	 * @return true si la pantalla de "Generar reporte" se muestra de manera correcta.
	 *         false en caso de que no cargue la pantalla
	 * @date 26/Septiembre/2025
	 */
	public boolean cargapantallagenerarreporte() {
		boolean validacion = false;
		try {
			u.unhighLight(menureporte);
			u.waitWebElement(campotipodereporte, 30, 5);
			testCase.addScreenShotCurrentStep(u, "La pantalla de Generar reporte se muestra de manera correcta.");

			if (u.isDisplayed(campotipodereporte)) {
				validacion = true;
			}
		} catch (Exception e) {
			// El elemento no existe o no es visible, validación sigue en false
			Assert.fail("No cargo la pantalla de Generar Reporte");
		}
		return validacion;
	}

	/**
	 * Método para seleccionar un tipo de reporte dentro de la pantalla "Generar reporte".
	 *
	 * @author Carlos Martínez Ortega
	 * @param Reporte Nombre del reporte a seleccionar en el combo/lista desplegable.
	 * @return true si los campos Año, Semana, CEDIS y Limpiar se habilitan correctamente.
	 *         false si alguno de los campos no se habilita o ocurre un error en la validación.
	 * @date 27/Septiembre/2025
	 */
	public boolean seleccionarreporte(String Reporte) {
		boolean validacion = false;
		try {
			u.waitWebElement(seleccionarreporte, 30, 5);
			u.highLight(seleccionarreporte);

			WebElement dropdown = u.getDriver().findElement(seleccionarreporte);
	        Select select = new Select(dropdown);
	        select.selectByVisibleText(Reporte);

			u.waitWebElement(botondescargar, 30, 5);

			// Validar que se habiliten los campos
			boolean anoEnabled = u.getDriver().findElement(campoano).isEnabled();
			boolean semanaEnabled = u.getDriver().findElement(camposemana).isEnabled();
			boolean cedisEnabled = u.getDriver().findElement(campocedis).isEnabled();
			boolean limpiarEnabled = u.getDriver().findElement(botonlimpiar).isEnabled();

			if (anoEnabled && semanaEnabled && cedisEnabled && limpiarEnabled) {
				testCase.addScreenShotCurrentStep(u,
						"Al seleccionar el reporte se habilitan Año, Semana, CEDIS y el botón Limpiar correctamente");
				validacion = true;
			} else {
				Assert.fail(String.format("Alguno de los campos no se habilitó -> Año:%s Semana:%s CEDIS:%s Limpiar:%s",
						anoEnabled, semanaEnabled, cedisEnabled, limpiarEnabled));
			}

		} catch (Exception e) {
			Assert.fail("Error al seleccionar el reporte y validar campos: " + e.getMessage());
		}
		return validacion;
	}

	/**
	 * Método para validar las opciones disponibles en el campo "Año" del formulario de reportes.
	 *
	 * @author Carlos Martínez Ortega
	 * @return true si todas las opciones del campo "Año" están dentro del rango válido
	 *         (año actual ± 2).
	 *         false si ocurre un error al obtener las opciones o se encuentra un año fuera de rango.
	 * @date 26/Septiembre/2025
	 */
	public boolean validarOpcionesAno() {
		boolean validacion = false;
		try {
			u.highLight(campoano);
			u.click(campoano);
			// Se agrega hardwait para que se alcance a cargar el campo y se muestre en el
			// screenshot
			u.hardWait(1);
			testCase.addScreenShotCurrentStep(u, "Validando las opciones disponibles en el campo Año");

			// Obtener todas las opciones del select
			List<WebElement> opciones = u.getDriver()
					.findElements(By.xpath("//select[@formcontrolname='year']/option"));

			// Año actual
			int anoActual = LocalDate.now().getYear();
			int limiteInferior = anoActual - 2;
			int limiteSuperior = anoActual + 2;

			boolean hayAnoInvalido = false;

			for (WebElement opcion : opciones) {
				String valor = opcion.getAttribute("value").trim();
				if (!valor.isEmpty()) {
					int anio = Integer.parseInt(valor);

					// Validar límites
					if (anio < limiteInferior || anio > limiteSuperior) {
						hayAnoInvalido = true;
						testCase.addTextEvidenceCurrentStep("Se encontró un año fuera de rango permitido: " + anio
								+ " (válido entre " + limiteInferior + " y " + limiteSuperior + ")");
					}
				}
			}

			if (hayAnoInvalido) {
				Assert.fail("El campo Año contiene valores fuera del rango permitido (" + limiteInferior + " - "
						+ limiteSuperior + ")");
			} else {
				validacion = true;
				testCase.addTextEvidenceCurrentStep("No permite seleccionar de 2 años hacia atrás de manera correcta");
			}

		} catch (Exception e) {
			Assert.fail("Error validando las opciones del campo Año: " + e.getMessage());
		}
		return validacion;
	}

	/**
	 * Método para validar que el botón "Generar/Descargar" se habilite al seleccionar
	 * los valores de Año, Semana y CEDIS en la pantalla de reportes.
	 * 
	 * @author Carlos Martínez Ortega
	 * @param CEDIS  Nombre del centro de distribución a seleccionar en el combo de CEDIS.
	 * @param Semana Número de la semana a seleccionar en el combo de semanas.
	 * @param Ano    Año a seleccionar en el combo de años.
	 * @return true si el botón "Descargar" se habilita correctamente tras seleccionar las opciones.
	 *         false si ocurre un error en el flujo o si el botón permanece deshabilitado.
	 * @date 26/Septiembre/2025
	 */
	public boolean habilitarelbotongenerar(String CEDIS, String Semana, String Ano) {
		boolean validacion = false;
		try {
			u.highLight(campocedis);
			
			WebElement dropdown = u.getDriver().findElement(campocedis);
	        Select select = new Select(dropdown);
	        select.selectByVisibleText(CEDIS);
	        
	        WebElement dropdown2 = u.getDriver().findElement(campoano);
	        Select select2 = new Select(dropdown2);
	        select2.selectByVisibleText(Ano);
	        
	        u.highLight(camposemana);
	        WebElement dropdown3 = u.getDriver().findElement(camposemana);
	        Select select3 = new Select(dropdown3);
	        select3.selectByVisibleText(Semana);
			
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(15));
			wait.until(driver -> driver.findElement(botondescargar).isEnabled());

			// Validar que esté habilitado
			boolean botonEnabled = u.getDriver().findElement(botondescargar).isEnabled();

			if (botonEnabled) {
				testCase.addScreenShotCurrentStep(u, "El botón Descargar se habilita de manera correcta");
				validacion = true;
			} else {
				Assert.fail("El botón Descargar está visible pero no habilitado.");
			}
		} catch (Exception e) {
			Assert.fail("El botón generar no se habilita ");
		}
		return validacion;
	}

	/**
	 * Método para validar que al presionar el botón "Limpiar", los campos del formulario
	 * se reinicien y el botón "Descargar" quede deshabilitado.
	 *
	 * @author Carlos Martínez Ortega
	 * @return true si el botón "Descargar" queda deshabilitado tras limpiar los campos.
	 *         false si el botón permanece habilitado o si ocurre un error durante la validación.
	 * @date 26/Septiembre/2025
	 */
	public boolean borrarloscampos() {
	    boolean validacion = false;
	    try {
	        u.click(botonlimpiar);
	        
	        //Se agrega este hardwait para dar tiempo suficiente a que se actualice la pantalla
	        u.hardWait(1);
	        WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
	        wait.until(driver -> !driver.findElement(botondescargar).isEnabled());

	        boolean descargarDeshabilitado = !u.getDriver().findElement(botondescargar).isEnabled();

	        if (descargarDeshabilitado) {
	            validacion = true;
	            testCase.addScreenShotCurrentStep(u, "El botón de generar se habilita de manera correcta");
	        } else {
	            Assert.fail("El botón Descargar no quedó en el estado esperado después de limpiar");
	        }

	    } catch (Exception e) {
	        Assert.fail("Error al validar el borrado de campos: " + e.getMessage());
	    }
	    return validacion;
	}

	/**
	 * Método para generar un archivo de reporte desde la pantalla de reportes.
	 *
	 * @author Carlos Martínez Ortega
	 * @param Reporte Nombre del reporte que se debe seleccionar.
	 * @param CEDIS   CEDIS a seleccionar.
	 * @param Ano     Año a seleccionar.
	 * @param Semana  Semana a seleccionar.
	 * @return true si el archivo se descarga y se muestra el mensaje de confirmación.
	 *         false si no se visualiza el mensaje o no ocurre la descarga.
	 * @date 26/Septiembre/2025
	 */
	public boolean generararchivo(String Reporte, String CEDIS, String Ano, String Semana) {
		boolean validacion = false;
		try {

			WebElement dropdown = u.getDriver().findElement(seleccionarreporte);
	        Select select = new Select(dropdown);
	        select.selectByVisibleText(Reporte);
	        
	        u.highLight(campocedis);
	        WebElement dropdown1 = u.getDriver().findElement(campocedis);
	        Select select1 = new Select(dropdown1);
	        select1.selectByVisibleText(CEDIS);
	        
	        u.highLight(campoano);
	        WebElement dropdown2 = u.getDriver().findElement(campoano);
	        Select select2 = new Select(dropdown2);
	        select2.selectByVisibleText(Ano);
	        
	        u.highLight(camposemana);
	        WebElement dropdown3 = u.getDriver().findElement(camposemana);
	        Select select3 = new Select(dropdown3);
	        select3.selectByVisibleText(Semana);
	        
			u.waitWebElement(botondescargar, 15, 2);
			u.click(botondescargar);
			u.waitWebElement(mensajeemergente, 15, 2);
			testCase.addScreenShotCurrentStep(u,
					"El mensaje se mostro en pantalla y se descargo el archivo de manera correcta");
			if (u.isDisplayed(mensajeemergente)) {
				validacion = true;
			}
		} catch (Exception e) {
			Assert.fail("El mensaje no se mostro y no se descargo el archivo");
		}
		return validacion;
	}

	/**
	 * Método para validar la descarga correcta de un archivo de publicidad en formato Excel.
	 *
	 * @author Carlos Martínez Ortega
	 * @param Nombreesperado Nombre completo del archivo esperado (incluyendo extensión).
	 * @return true si el archivo descargado coincide con el nombre y extensión esperados.
	 *         false si el archivo no existe o no cumple con la validación.
	 * @date 26/Septiembre/2025
	 */
	public boolean archivosxml(String Nombreesperado, String rutaBase, String carpetaDescargas) {
		boolean validacion = false;
		try {
			// Ruta de descargas armada con variables
			String rutaDescargas = System.getProperty(rutaBase) + "\\" + carpetaDescargas;

			// Nombre esperado del archivo descargado
			// String Nombreesperado = "Reporte de publicación.xlsx";

			File carpeta = new File(rutaDescargas);
			File[] archivos = carpeta.listFiles();

			if (archivos != null) {
				for (File archivo : archivos) {
					// Validamos nombre y extensión
					if (archivo.getName().equals(Nombreesperado) && archivo.getName().endsWith(".xlsx")) {
						System.out.println("Archivo encontrado: " + archivo.getAbsolutePath());
						testCase.addScreenShotCurrentStep(u, "El formato del archivo es el correcto");
						validacion = true;
						break;
					}
				}
			}

			if (!validacion) {
				Assert.fail("El formato del archivo es incorrecto");
			}
		} catch (Exception e) {
			Assert.fail("El formato del archivo es incorrecto" + e.getMessage());
		}
		return validacion;
	}
}