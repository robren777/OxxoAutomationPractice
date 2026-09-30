package EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class TaxReportingPOM {

	SeleniumUtil u;
	TestCase testCase;
	WebElement value;
	String textValue;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public TaxReportingPOM(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By actImpuestoTrans = By.xpath("//img[@title='Actualizar Impuesto de Transacción']/parent::a");
	By clasImpuesto = By.xpath("//table[@id='SupSiteTaxDetailsRN.ZxSuppSiteTaxDetTableRN:Content']//tr/td[8]//input");

	/**
	 * Da click en el campo Actualizar Impuesto de Transaccion
	 * 
	 * @author edwin.ramirez@neoris.com
	 * 
	 */
	public void actualizarImpuestoTrans() {
		u.highLight(actImpuestoTrans);
		u.click(actImpuestoTrans);
		u.waitForLoadPage();
	}

	/**
	 * Obtiene el texto del Campo Clasificacion de Impuestos
	 * 
	 * @author edwin.ramirez@neoris.com
	 * @return String El texto del campo "Clasificacion Impuestos"
	 */
	public String getTextClasImpuestos() {
		u.highLight(clasImpuesto);
		value = u.getDriver().findElement(clasImpuesto);
		textValue = value.getAttribute("value");
		System.out.println(textValue);
		return textValue;
	}
}
