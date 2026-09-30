package EBS.util.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EBSExtraccion {

	SeleniumUtil u;
	TestCase testCase;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez
	 */
	public EBSExtraccion(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	By botonConfirmar = By.xpath("//div//button[@title= 'Confirmar']");
	@FindBy(xpath = "//div//button[@title= 'Confirmar']")
	public WebElement botonConfirmarElement;

	By validarArchivo = By.xpath("//button[@id='btnValida']");
	@FindBy(xpath = "//button[@id='btnValida']]")
	public WebElement validarArchivoElement;

	By seleccionarArchivo = By.xpath("//input[@id='msgArchivoCarga_oafileUpload']");
	@FindBy(xpath = "//input[@id='msgArchivoCarga_oafileUpload']")
	public WebElement seleccionarArchivoElement;

	By botonSiguiente = By.xpath("//div//button[@title= 'Siguiente']");
	@FindBy(xpath = "//div//button[@title= 'Siguiente']")
	public WebElement botonSiguienteElement;

	By botonBuscar = By.xpath("//button[@id='btnBuscar']");
	@FindBy(xpath = "//button[@id='btnBuscar']']")
	public WebElement botonBuscarElement;

	By compania = By.xpath("//input[@id='lovCiaOrig']");
	@FindBy(xpath = "//input[@id='lovCiaOrig']")
	public WebElement companiaElement;

	By estadoFinanciero = By.xpath("//input[@id='lovEdoOrig']");
	@FindBy(xpath = "//input[@id='lovEdoOrig']")
	public WebElement estadoFinancieroElement;

	By crSuperior = By.xpath("//input[@id='lovCrSuperiorOrig']");
	@FindBy(xpath = "//input[@id='lovCrSuperiorOrig']")
	public WebElement crSuperiorElement;

	By btnExportar = By.xpath("//button[@id='btnExport']");
	@FindBy(xpath = "//button[@id='btnExport']")
	public WebElement btnExportarElement;

	By buscarEstadoF = By.xpath("//table[@id='ColumnaOrigenRN']//input[@id='lovEdoOrig']/following-sibling::a");
	By frameset = By.xpath("//frameset[@title='Content']");
	@FindBy(xpath = "//frameset[@title='Content']")
	public WebElement framesetElement;

	By frame = By.xpath("//frameset[@title='Content']/frame");
	@FindBy(xpath = "//frameset[@title='Content']/frame")
	public WebElement frameElement;

	By frame1 = By.xpath("//iframe[@id='_pprIFrame']");
	@FindBy(xpath = "//iframe[@id='_pprIFrame']")
	public WebElement frame1lement;

	By busqueda = By.xpath("//input[@title='Término de Búsqueda']");
	@FindBy(xpath = "//input[@title='Término de Búsqueda']")
	public WebElement busquedaElement;

	By busquedaFecha = By.xpath("//input[@id='txtFechaBi']");
	@FindBy(xpath = "//input[@id='txtFechaBi']")
	public WebElement busquedaFechaElement;

	By btnIr = By.xpath("//input[@title='Término de Búsqueda']/following-sibling::button");
	By seleccionarRadio = By.xpath("//form[@id='_LOVResFrm']//input[@type='radio']");
	By btnSeleccionar = By.xpath("//span[@id='CrEdoFinLovRN']//button[text()='Seleccionar']");

	public void clickBotonBuscar() {

		u.click(botonBuscar);
	}

	public void clickValidarArchivo() {

		u.click(validarArchivo);
	}

	/**
	 * Hace un remarcado y click para seleccionar un archivo
	 *
	 * @param ruta
	 * @author ricardo.palacios@neoris.com
	 */
	public void seleccionarArchivo(String ruta) {
		WebElement uploadElement = u.getDriver().findElement(seleccionarArchivo);
		// enter the file path onto the file-selection input field
		uploadElement.sendKeys(ruta);
	}

	public void clickBotonSiguiente() {

		u.click(botonSiguiente);
	}

	public void clickBotonConfirmar() {

		u.click(botonConfirmar);
	}

	public String seleccionarArchivoSinSendkeys(String fileName) {
		String currentProject = System.getProperty("user.dir"); // Obtiene la ruta del Proyecto actual
		// Se junta la ruta del proyecto,la ubicacion de resources y el nombre del
		// archivo
		String srcFilePath = currentProject + "\\Config\\Input\\resources" + fileName;
		System.out.println(srcFilePath);
		return srcFilePath;

	}

	public void SetFecha(String fecha) {

		busquedaFechaElement.sendKeys(fecha);
	}

	public void setEstadoFinanciero(String estadoF) {
		// Store the ID of the original window
		String originalWindow = u.getDriver().getWindowHandle();
		// Check we don't have other windows open already
		assert u.getDriver().getWindowHandles().size() == 1;
		// Click the link which opens in a new window
		u.click(buscarEstadoF);
		// Wait for the new window or tab
		u.hardWait(3);
		// Loop through until we find a new window handle
		for (String windowHandle : u.getDriver().getWindowHandles()) {
			if (!originalWindow.contentEquals(windowHandle)) {
				u.getDriver().switchTo().window(windowHandle);
				break;
			}
		}
		u.getDriver().switchTo().frame(frameElement);
		u.hardWait(2);
		busquedaElement.sendKeys(estadoF);
		u.click(btnIr);
		u.click(seleccionarRadio);
		u.click(btnSeleccionar);
		u.getDriver().switchTo().window(originalWindow);
	}
}
