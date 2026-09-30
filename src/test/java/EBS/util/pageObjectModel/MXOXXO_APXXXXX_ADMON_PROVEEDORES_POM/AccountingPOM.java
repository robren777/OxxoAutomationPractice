package EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class AccountingPOM {

	SeleniumUtil u;
	TestCase testCase;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author JessicaR@hexaware.com
	 */
	public AccountingPOM(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By btnAnticipo = By.xpath(" //a[@title = 'Anticipo']");
	By anticipo = By.xpath(" //input[@title = 'Anticipo']");

	public void clickAnticipo() {
		u.highLight(btnAnticipo);
		u.click(btnAnticipo);
		u.waitForLoadPage();
	}

	/**
	 * Casilla de la que se va a tomar el texto
	 * @author JessicaR@hexaware.com
	 * @param Field
	 * @return
	 */
	public By field(String Field) {
		switch (Field) {
		case "Anticipo":
			return anticipo;

		}
		return null;
	}

	/**
	 * Método que toma el texto de la casilla
	 * 
	 * @author JessicaR@hexaware.com
	 * @param field
	 * @return valor seleccionado
	 */
	public String getTextField(String field) {

		By obj = field(field);
		u.highLight(obj);
		return u.getDriver().findElement(obj).getAttribute("Value");

	}

}
