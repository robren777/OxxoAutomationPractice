package EBS.util.pageObjectModel.MXOXXO_APXXXXX_CONSULTAS;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class OrganizationPOM {
	SeleniumUtil u;
	TestCase testCase;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public OrganizationPOM(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By tipoValue = By.xpath("//span[@id='Type']");
	By personaValue = By
			.xpath("//span[normalize-space()='Persona']/parent::td/following-sibling::td/following-sibling::td");

	public String getTipoText() {
		u.highLight(tipoValue);
		return u.getText(tipoValue);
	}

	public String getPersonaText() {
		u.highLight(personaValue);
		return u.getText(personaValue);
	}
}
