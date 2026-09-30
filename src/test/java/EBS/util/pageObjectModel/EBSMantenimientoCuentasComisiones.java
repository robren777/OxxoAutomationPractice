package EBS.util.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EBSMantenimientoCuentasComisiones {
	SeleniumUtil u;
	TestCase testCase;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public EBSMantenimientoCuentasComisiones(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	@FindBy(xpath = "//button[@id='CrearCuentaBtn']")
	public WebElement btnCrearElement;
	By btnCrear = By.xpath("//button[@id='CrearCuentaBtn']");
	@FindBy(xpath = "//button[@id='CrearComBtn']")
	public WebElement btnCrearComElement;
	By btnCrearCom = By.xpath("//button[@id='CrearComBtn']");
	@FindBy(xpath = "//table[@id='CuentasContablesRN:header']//span[text()='Forma Pago']")
	public WebElement formaPagoElement;
	By formaPago = By.xpath("//table[@id='CuentasContablesRN:header']//span[text()='Forma Pago']");
	@FindBy(xpath = "//table[@id='CuentasContablesRN:header']//span[text()='Nombre']")
	public WebElement nombreElement;
	By nombre = By.xpath("//table[@id='CuentasContablesRN:header']//span[text()='Nombre']");
	@FindBy(xpath = "//table[@id='CuentasContablesRN:header']//span[text()='Cuenta']")
	public WebElement cuentaElement;
	By cuenta = By.xpath("//table[@id='CuentasContablesRN:header']//span[text()='Cuenta']");
	@FindBy(xpath = "//table[@id='CuentasContablesRN:header']//span[text()='Auxiliar']")
	public WebElement auxiliarElement;
	By auxiliar = By.xpath("//table[@id='CuentasContablesRN:header']//span[text()='Auxiliar']");
	@FindBy(xpath = "//table[@id='CuentasContablesRN:header']//span[text()='Tipo']")
	public WebElement tipoElement;
	By tipo = By.xpath("//table[@id='CuentasContablesRN:header']//span[text()='Tipo']");
	@FindBy(xpath = "//table[@id='CuentasContablesRN:header']//span[text()='Nivel']")
	public WebElement nivelElement;
	By nivel = By.xpath("//table[@id='CuentasContablesRN:header']//span[text()='Nivel']");
	@FindBy(xpath = "//table[@id='CuentasContablesRN:header']//span[text()='Fecha Desde']")
	public WebElement fechaDesdeElement;
	By fechaDesde = By.xpath("//table[@id='CuentasContablesRN:header']//span[text()='Fecha Desde']");
	@FindBy(xpath = "//table[@id='CuentasContablesRN:header']//span[text()='Fecha Hasta']")
	public WebElement fechaHastaElement;
	By fechaHasta = By.xpath("//table[@id='CuentasContablesRN:header']//span[text()='Fecha Hasta']");
	@FindBy(xpath = "//table[@id='CuentasContablesRN:header']//span[text()='Estado']")
	public WebElement estadoElement;
	By estado = By.xpath("//table[@id='CuentasContablesRN:header']//span[text()='Estado']");
	@FindBy(xpath = "//a[@id='ComisionesLink']")
	public WebElement btnComisionesElement;
	By btnComisiones = By.xpath("//a[@id='ComisionesLink']");
	
	@FindBy(xpath = "//table[@id='ComisionesRN:header']//span[text()='Forma Pago']")
	public WebElement formaPagoComElement;
	By formaPagoCom = By.xpath("//table[@id='ComisionesRN:header']//span[text()='Forma Pago']");
	@FindBy(xpath = "//table[@id='ComisionesRN:header']//span[text()='Metodo Pago']")
	public WebElement metodoPagoComElement;
	By metodoPagoCom = By.xpath("//table[@id='ComisionesRN:header']//span[text()='Metodo Pago']");
	@FindBy(xpath = "//table[@id='ComisionesRN:header']//span[text()='Comision']")
	public WebElement comisionElement;
	By comision = By.xpath("//table[@id='ComisionesRN:header']//span[text()='Comision']");
	@FindBy(xpath = "//table[@id='ComisionesRN:header']//span[text()='Tipo']")
	public WebElement tipoComElement;
	By tipoCom = By.xpath("//table[@id='ComisionesRN:header']//span[text()='Tipo']");
	@FindBy(xpath = "//table[@id='ComisionesRN:header']//span[text()='Tasa Iva']")
	public WebElement tasaElement;
	By tasa = By.xpath("//table[@id='ComisionesRN:header']//span[text()='Tasa Iva']");
	@FindBy(xpath = "//table[@id='ComisionesRN:header']//span[text()='Fecha Desde']")
	public WebElement fechaDesdeComElement;
	By fechaDesdeCom = By.xpath("//table[@id='ComisionesRN:header']//span[text()='Fecha Desde']");
	@FindBy(xpath = "//table[@id='ComisionesRN:header']//span[text()='Fecha Hasta']")
	public WebElement fechaHastaComElement;
	By fechaHastaCom = By.xpath("//table[@id='ComisionesRN:header']//span[text()='Fecha Hasta']");
	@FindBy(xpath = "//table[@id='ComisionesRN:header']//span[text()='Estado']")
	public WebElement estadoComElement;
	By estadoCom = By.xpath("//table[@id='ComisionesRN:header']//span[text()='Estado']");
}
