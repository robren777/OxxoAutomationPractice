package EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class ActualizarOrganizacionPOM {

	SeleniumUtil u;
	TestCase testCase;
	

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public ActualizarOrganizacionPOM(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By tipo = By.xpath("//input[@id='Type']");
	By btnGrabar = By.xpath("//button[@id='saveBtn']//span[@class='xq'][normalize-space()='G']");
	By confirmacion = By.xpath("//tbody/tr/td[3]/table/tbody/tr/td/h1");

	public void sendTipo(String tipoOrganizacion) {
		u.highLight(tipo);
		u.clear(tipo);
		u.hardWait(3);
		u.click(tipo);
		u.sendKeys(tipo, tipoOrganizacion);
	}

	public void clickGrabar() {
		u.highLight(btnGrabar);
		u.click(btnGrabar);
	}

	/**
	 * Se toma el mensaje de confirmación
	 * 
	 * @author JessicaR@hexaware.com
	 * @return confirmacion el mensaje de confirmacion
	 */
	public String getTextConfirmacion() {

		u.highLight(confirmacion);
		return u.getText(confirmacion);
	}

}
