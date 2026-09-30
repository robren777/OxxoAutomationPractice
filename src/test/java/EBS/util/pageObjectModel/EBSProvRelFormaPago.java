package EBS.util.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EBSProvRelFormaPago {
	SeleniumUtil u;
	TestCase testCase;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez
	 */
	public EBSProvRelFormaPago(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}

	By rfc = By.xpath("//input[@id='lovRFC']");
	By botonIr = By.xpath("//button[normalize-space()='Ir']");
	By descripcion = By.xpath("//span[@id='advTblResultadosRN:msgDescFormaPago:0']");

	@FindBy(xpath = "//input[@id='lovRFC']")
	public WebElement rfcElement;

	@FindBy(xpath = "//input[@id='lovFrmPago']")
	public WebElement formaPagoElement;

	@FindBy(xpath = "//button[normalize-space()='Ir']")
	public WebElement btnIrElement;

	/**
	 * Envia los parametros del RFC
	 * 
	 * @author JessicaR@hexaware.com
	 * @return
	 */
	public By ingresarRFC() {
		return rfc;
	}

	/**
	 * Da clic en el botón "ir"
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickIr() {
		u.click(botonIr);
		u.waitForLoadPage();
	}

	/**
	 * Copia el texto de la celda descripcion
	 * 
	 * @author JessicaR@hexaware.com
	 * @return
	 */
	public String getTextDescripcion() {

		u.highLight(descripcion);
		return u.getText(descripcion);
	}

}
