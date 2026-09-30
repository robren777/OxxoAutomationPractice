package EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class TaxDetailsPOM {

	SeleniumUtil u;
	TestCase testCase;
	WebElement value;
	String textValue;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public TaxDetailsPOM(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By checkboxRetencionImpuesto = By.xpath("//input[@id='allwTaxWthld']");
	By checkboxRetencionImpuestosLower = By.xpath("//table[@id='TaxRN:Content']/tbody/tr/td[3]/span/input");
	By labelGRIP = By.xpath(
			"//input[@id='allwTaxWthld']/parent::span/parent::td/parent::tr/following-sibling::tr/following-sibling::tr//span[text()='Grupo de Retenciones de Impuestos de Pago']");
	By grabarBtn = By.xpath("//button[@id='saveBtn']");

	By actImpuestoTrans = By.xpath("//img[@title='Actualizar Impuesto de Transacción']/parent::a");
	By btnCancelar = By.xpath("//button[@id='cancelBtn']");
	By GrupoRetImpPago = By.xpath(
			"//td[@id='p_SwanPageLayout']//table[@id='TaxRN:Content']/tbody/tr/td[5]//input[@title='Grupo de Retenciones de Impuestos de Pago']");

	/**
	 * Marca los dos checkbox "Retencion de Impuestos" y los activa
	 * 
	 * @author edwin.ramirez@neoris.com
	 */
	public void markRetencionImpuesto() {
		u.highLight(checkboxRetencionImpuesto);
		u.click(checkboxRetencionImpuesto);
		u.hardWait(2);
		u.highLight(checkboxRetencionImpuestosLower);
		u.click(checkboxRetencionImpuestosLower);
		u.hardWait(2);
		u.waitForLoadPage();
	}

	/**
	 * Obtiene el texto del campo "Retencion Impuestos de Pago"
	 * 
	 * @author edwin.ramirez@neoris.com
	 * @return El texto del campo "Retencion Impuestos de Pago"
	 */
	public String getTextGrupoRetImpPago() {
		u.scroll();
		u.waitWebElement(GrupoRetImpPago, 10, 500);
		value = u.getDriver().findElement(GrupoRetImpPago);
		textValue = value.getAttribute("value");
		System.out.println(textValue);
		return textValue;
	}

	public void clickGrabar() {
		u.highLight(grabarBtn);
		u.click(grabarBtn);
		u.waitForLoadPage();
	}

	public void clickCancelar() {
		u.highLight(btnCancelar);
		u.click(btnCancelar);
		u.waitForLoadPage();
	}

	public void clickActualizarImpuestoTrans() {
		u.highLight(actImpuestoTrans);
		u.click(actImpuestoTrans);
	}

}
