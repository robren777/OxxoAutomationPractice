package WEB.seguridadSADEL.om;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import WEB.updateNomina.om.UpdateNomina;
import utils.selenium.SeleniumUtil;
import modelo.TestCase;

public class NominaPage {
    private final SeleniumUtil u;
    private final TestCase testCase;
    //Timbrados
    By pestañaTimbrados = By.id("tab4");
    By selectAño = By.id("idAnio");
    By selectMes = By.id("idMes");
    By checkboxTimbrado = By.id("idTimbrado_0");
    By inputEmail = By.id("email_0");
    By buscarConceptos = By.xpath("//img[@alt='Buscar Conceptos']");
    By selectConcepto = By.id("concepto");
    By btnEnviar = By.id("Image34");

    /**
    * Constructor de la clase NominaPage.
    * Inicializa las utilidades de Selenium y el caso de prueba asociado.
    * @author Mario A. De León Rdz.
    * @param u Instancia de SeleniumUtil para la interacción con el navegador.
    * @param testCase Instancia de TestCase para el registro de pasos y screenshots.
    * @date 10/Junio/2025
    */
    public NominaPage(SeleniumUtil u, TestCase testCase) {
        this.u = u;
        this.testCase = testCase;
        new UpdateNomina(u, testCase);
    }

    /**
     * Selecciona la pestaña de Timbrados en la pantalla principal de Nómina.<br>
     * Debe ejecutarse para visualizar las opciones de configuración de envío de recibos.
     * @author Mario A. De León Rdz.
     * @date 10/Junio/2025
     */
	public boolean seleccionarPestañaTimbrados() {
		boolean validacion = false;
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(pestañaTimbrados)).click();
			testCase.addScreenShotCurrentStep(u, "pestañaTimbrados");
			validacion = true;
		} catch (Exception e) {
			System.out.println(" -> " + e);
			e.printStackTrace();
		}
		return validacion;
	}

    /**
     * Ingresa los valores de Año y Mes en los campos correspondientes de la sección de Timbrado.
     * @author Mario A. De León Rdz.
     * @param año Año a consultar en el filtro.
     * @param mes Mes a consultar en el filtro.
     * @date 07/Julio/2026
     */
	public boolean llenarCamposTimbrado(String año, String mes) {
		boolean validacion = false;
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(selectAño)).click();
			u.sendKeys(selectAño, año);
			u.sendKeys(selectMes, mes);
			testCase.addScreenShotCurrentStep(u, "");
			validacion = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return validacion;
	}

    /**
     * Selecciona el primer checkbox individual de la columna Timbrado en la tabla de resultados.
     * @author Mario A. De León Rdz.
     * @date 07/Julio/2026
     */
	public boolean seleccionarCheckboxEmpleado() {
		boolean validacion = false;
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(checkboxTimbrado)).click();
			testCase.addScreenShotCurrentStep(u, "");
			validacion = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return validacion;
	}

	/**
	 * Selecciona todos los checkboxes de la columna "Timbrado" disponibles
	 * en la tabla de empleados.<br>
	 * Debe ejecutarse después de haber cargado la lista de empleados en la pantalla.
	 * @author Mario A. De León Rdz.
	 * @date 16/Junio/2026
	 */
	public boolean seleccionarCheckboxesEmpleados() {
		boolean validacion = false;
	    try {
	        WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));

	        By checkboxesTimbrado = By.cssSelector("input[type='checkbox']");
	        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkboxesTimbrado));
	        List<WebElement> checkboxes = u.getDriver().findElements(checkboxesTimbrado);

	        for (WebElement checkbox : checkboxes) {
	            if (!checkbox.isSelected()) {
	                checkbox.click();
	            }
	        }

	        testCase.addScreenShotCurrentStep(u, "Checkboxes de empleados seleccionados");
	        validacion = true;
	    } catch (Exception e) {
	        System.out.println(" -> " + e);
	        e.printStackTrace();
	    }
	    return validacion;
	}

    /**
     * Hace click en el botón de la lupa para buscar los conceptos asociados al empleado.
     * @author Mario A. De León Rdz.
     * @date 07/Julio/2026
     */
	public boolean clickBuscar() {
		boolean validacion = false;
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(buscarConceptos)).click();
			testCase.addScreenShotCurrentStep(u, "");
			validacion = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return validacion;
	}

    /**
     * Ingresa el correo electrónico especificado en el campo de texto individual del primer registro.
     * @author Mario A. De León Rdz.
     * @param email Correo electrónico a capturar.
     * @date 07/Julio/2026
     */
	public boolean llenarCampoEmail(String email) {
		boolean validacion = false;
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(inputEmail));
			u.sendKeys(inputEmail, email);
			testCase.addScreenShotCurrentStep(u, "");
			validacion = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return validacion;
	}
	
	/**
	 * Hace click en todos los íconos de Editar visibles en la pantalla y
	 * escribe el correo proporcionado en todos los campos de tipo texto
	 * de la página, limpiando primero cualquier valor previo.<br>
	 * Debe ejecutarse después de haber cargado la lista de empleados/conceptos a editar.
	 * @author Mario A. De León Rdz.
	 * @param email Correo electrónico a capturar en cada input.
	 * @date 16/Junio/2026
	 */
	public boolean llenarCampoEmailEnTodos(String email) {
		boolean validacion = false;
	    try {
	        WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));

	        // Espera a que la página termine de cargar por completo antes de buscar elementos
	        wait.until(driver -> ((JavascriptExecutor) driver)
	                .executeScript("return document.readyState").equals("complete"));

	        // FASE 1: Click en todos los íconos de Editar disponibles
	        By editarIcons = By.xpath("//img[@alt='Editar']");
	        List<WebElement> iconosEditar = u.getDriver().findElements(editarIcons);
	        int total = iconosEditar.size();

	        for (int i = 0; i < total; i++) {
	            List<WebElement> iconosActuales = u.getDriver().findElements(editarIcons);
	            iconosActuales.get(i).click();
	        }

	        // FASE 2: Capturar TODOS los inputs de tipo texto de la página, sin importar id/class
	        By todosLosInputs = By.cssSelector("input[type='text']");
	        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(todosLosInputs));
	        List<WebElement> camposTexto = u.getDriver().findElements(todosLosInputs);

	        for (WebElement campo : camposTexto) {
	            campo.clear();
	            campo.sendKeys(email);
	        }

	        testCase.addScreenShotCurrentStep(u, "Email capturado en todos los campos de texto");
	        validacion = true;

	    } catch (Exception e) {
	        System.out.println(" -> " + e);
	        e.printStackTrace();
	    }
	    return validacion;
	}

    /**
     * Hace click en el botón de Enviar para procesar la información de los timbrados seleccionados.
     * @author Mario A. De León Rdz.
     * @date 07/Julio/2026
     */
	public boolean clickBtnEnviar() {
		boolean validacion = false;
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(btnEnviar)).click();
			testCase.addScreenShotCurrentStep(u, "");
			validacion = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return validacion;
	}
	
	/**
	 * Valida que no haya aparecido ninguna alerta tras realizar una acción.
	 * Si se detecta una alerta inesperada, captura su texto, toma evidencia y retorna false.
	 * Si el tiempo de espera expira (TimeoutException), significa que no hay alerta y es exitoso.
	 * @author Mario A. De León Rdz.
	 * @date 16/Junio/2026
	 */
	public boolean validarSinAlertaPresente() {
		boolean validacion = false;
	        WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(5));
	        try {
	            wait.until(ExpectedConditions.alertIsPresent());

	            // Si llegamos aquí, sí apareció una alerta: la capturamos y retornamos false
	            Alert alert = u.getDriver().switchTo().alert();
	            String mensajeAlerta = alert.getText();
	            System.out.println(mensajeAlerta);

	            testCase.addScreenShotCurrentStep("alerta_inesperada");
	            testCase.addTextEvidenceCurrentStep("ERROR Se mostró una alerta inesperada: " + mensajeAlerta);

	            alert.accept();
	            // validacion se queda en false porque apareció una alerta que no debía
	        } catch (TimeoutException e) {
	            // No apareció ninguna alerta: comportamiento esperado y exitoso
	            testCase.addScreenShotCurrentStep("sin_alerta");
	            testCase.addTextEvidenceCurrentStep("EXITOSO correo enviado");
	            validacion = true;
	        }
	    return validacion;
	}
	
    /**
     * Escribe el valor "Todos" en el campo desplegable o de texto de Concepto.
     * @author Mario A. De León Rdz.
     * @date 07/Julio/2026
     */
	public boolean seleccionarConcepto() {
		boolean validacion = false;
		try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(), Duration.ofSeconds(30));
			wait.until(ExpectedConditions.elementToBeClickable(selectConcepto));
			u.sendKeys(selectConcepto, "Todos");
			testCase.addScreenShotCurrentStep(u, "");
			validacion = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return validacion;
	}
}