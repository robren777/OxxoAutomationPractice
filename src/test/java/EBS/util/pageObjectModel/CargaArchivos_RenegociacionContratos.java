package EBS.util.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CargaArchivos_RenegociacionContratos {
	SeleniumUtil u;
	TestCase testCase;

	/**
	 * Cargar Archivos Renegociacion de Contratos
	 * 
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public CargaArchivos_RenegociacionContratos(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	By cargarArchivoRenegociacion = By.xpath("//span[@id='UploadRN']//table//tr//input");
	@FindBy(xpath = "//span[@id='UploadRN']//table//tr//input")
	public WebElement cargarArchivoRenegociacionElement;

	By ejecutar = By.xpath("//button[normalize-space()='Ejecutar']");

	/**
	 * Método que sirve para seleccionar los archivos a cargar desde la carpeta en
	 * donde se encuentran guardados
	 *
	 * @param nombreArchivo
	 * @author edwin.ramirz@neoris.com
	 */
	public void selectFileCurrentProject(String nombreArchivo) {

		String rutaRecursos = System.getProperty("user.dir");
		String rutafinal = rutaRecursos + "\\Config\\Input\\resources" + nombreArchivo;

		WebElement uploadElement = u.getDriver().findElement(cargarArchivoRenegociacion);
		System.out.println(rutafinal);

		uploadElement.sendKeys(rutafinal);

	}

	/**
	 * Hace un remarcado y clic en la opción de "Ejecutar"
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickEjecutar() {
		u.highLight(ejecutar);
		u.click(ejecutar);

	}

}
