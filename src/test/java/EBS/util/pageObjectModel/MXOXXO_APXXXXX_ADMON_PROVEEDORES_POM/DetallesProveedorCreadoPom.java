package EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class DetallesProveedorCreadoPom {

	SeleniumUtil u;
	TestCase testCase;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public DetallesProveedorCreadoPom(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By organization = By.xpath("//a[@id='POS_HT_SP_B_ORG']");
	By addressBook = By.xpath("//a[@id='POS_HT_SP_B_ADDR_BK']");
	By taxDetails = By.xpath("//a[@id='POS_HT_SP_B_TAX_DT']");
	By taxReporting = By.xpath("//a[@id='POS_HT_SP_B_TAX_RPT']");

	public void clickOrganization() {
		u.highLight(organization);
		u.click(organization);
		u.waitForLoadPage();
	}

	public void clickAddressBook() {
		u.highLight(addressBook);
		u.click(addressBook);
		u.waitForLoadPage();
	}

	public void clickTaxDetails() {
		u.highLight(taxDetails);
		u.click(taxDetails);
		u.waitForLoadPage();
	}

	public void clickTaxReporting() {
		u.highLight(taxReporting);
		u.click(taxReporting);
		u.waitForLoadPage();
	}

}
