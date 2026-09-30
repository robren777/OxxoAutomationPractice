package EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AddressBookPOM {

	SeleniumUtil u;
	TestCase testCase;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public AddressBookPOM(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By btnCrear = By.xpath("//button[@id='Create']");

	public void clickCrear() {
		u.highLight(btnCrear);
		u.click(btnCrear);
		u.waitForLoadPage();
	}

}
