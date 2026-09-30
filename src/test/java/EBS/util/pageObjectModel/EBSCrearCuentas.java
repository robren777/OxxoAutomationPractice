package EBS.util.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EBSCrearCuentas {
	SeleniumUtil u;
	TestCase testCase;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public EBSCrearCuentas(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	@FindBy(xpath = "(//input[@id='lovFormaPago'])[1]")
	public WebElement formaPagoElement;
	By formaPago = By.xpath("(//input[@id='lovFormaPago'])[1]");

	@FindBy(xpath = "//input[@id='lovCuentas']")
	public WebElement cuentaElement;
	By cuenta = By.xpath("//input[@id='lovCuentas']");

	@FindBy(xpath = "//input[@id='lovAuxiliar']")
	public WebElement auxiliarElement;
	By auxiliar = By.xpath("//input[@id='lovAuxiliar']");

	@FindBy(xpath = "//input[@id='txtNombreCuenta']")
	public WebElement nombreElement;
	By nombre = By.xpath("//input[@id='txtNombreCuenta']");

	@FindBy(xpath = "//input[@id='txtFechaDesde']")
	public WebElement fechaDesdeElement;
	By fechaDesde = By.xpath("//input[@id='txtFechaDesde']");

	@FindBy(xpath = "//input[@id='txtFechaHasta']")
	public WebElement fechaHastaElement;
	By fechaHasta = By.xpath("//input[@id='txtFechaHasta']");

	@FindBy(xpath = "(//button[@id='Aplicar'])[1]")
	public WebElement aplicarElement;
	By aplicar = By.xpath("(//button[@id='Aplicar'])[1]");

	@FindBy(xpath = "//div[text()='Cuenta creada correctamente!']")
	public WebElement msjCuentaCreadaElement;
	By msjCuentaCreada = By.xpath("//div[text()='Cuenta creada correctamente!']");

	@FindBy(xpath = "//input[@id='cbEstado']")
	public WebElement checkboxEstadoElement;
	By checkboxEstado = By.xpath("//input[@id='cbEstado']");
}
