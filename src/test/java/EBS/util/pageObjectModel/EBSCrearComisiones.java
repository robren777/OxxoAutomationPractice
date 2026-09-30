package EBS.util.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EBSCrearComisiones {
	SeleniumUtil u;
	TestCase testCase;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public EBSCrearComisiones(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	@FindBy(xpath = "//input[@id='lovFormaPagoCom']")
	public WebElement formaPagoElement;
	By formaPago = By.xpath("//input[@id='lovFormaPagoCom']");

	@FindBy(xpath = "//select[@id='metodoPagoChoice']")
	public WebElement metodoPagoElement;
	By metodoPago = By.xpath("//select[@id='metodoPagoChoice']");

	@FindBy(xpath = "//input[@id='txtComisionTextInput']")
	public WebElement comisionElement;
	By comision = By.xpath("//input[@id='txtComisionTextInput']");

	@FindBy(xpath = "//input[@id='txtFechaDesde']")
	public WebElement fechaDesdeElement;
	By fechaDesde = By.xpath("//input[@id='txtFechaDesde']");

	@FindBy(xpath = "//input[@id='txtFechaHasta']")
	public WebElement fechaHastaElement;
	By fechaHasta = By.xpath("//input[@id='txtFechaHasta']");

	@FindBy(xpath = "//input[@id='cbEstadoChoice']")
	public WebElement estadoElement;
	By estado = By.xpath("//input[@id='cbEstadoChoice']");

	@FindBy(xpath = "//button[@id='Aplicar']")
	public WebElement aplicarElement;
	By aplicar = By.xpath("//button[@id='Aplicar']");

	@FindBy(xpath = "//table[@id='FwkErrorBeanId']//div[contains(text(),'Comision')]")
	public WebElement msjComisionCreadaElement;
	By msjComisionCreada = By.xpath("//table[@id='FwkErrorBeanId']//div[contains(text(),'Comision')]");

	@FindBy(xpath = "//div[contains(text(),'Ya existe una comisión')]")
	public WebElement msjComisionDuplicadaElement;
	By msjComisionDuplicada = By.xpath("//div[contains(text(),'Ya existe una comisión')]");

	public void selectMetodoPago() {
		Select option = new Select(u.getDriver().findElement(metodoPago));
		option.selectByVisibleText("Tarjeta Credito");
	}
}
