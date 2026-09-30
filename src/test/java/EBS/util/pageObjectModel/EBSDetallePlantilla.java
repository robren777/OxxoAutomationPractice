package EBS.util.pageObjectModel;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class EBSDetallePlantilla {

	SeleniumUtil u;
	TestCase testCase;

	// Generacion del constructor

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author edwin.ramirez@neoris.com
	 */
	public EBSDetallePlantilla(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By tipoPersona = By.xpath("//input[@id='lovTipoPersona']");
	By tipoTasa = By.xpath("//input[@id='lovTipoBeneficio']");
	By aplicar = By.xpath("//button[@id='btnAplicar']");

	public void sendTipoPersona(String tipoPersonaText) {
		u.waitForLoadPage();
		u.sendKeys(tipoPersona, tipoPersonaText);
	}

	public void sendTipoTasa(String tipoTasaText) {
		u.sendKeys(tipoTasa, tipoTasaText);
	}

	public void clickAplicar() {
		u.click(aplicar);
	}

	/**
	 * Va al input del tipo de tasa y regresa el valor contenido
	 *
	 * @return String del tipo de tasa que tiene el campo
	 */
	public String getTipoTasa() {
		u.highLight(tipoTasa);
		return u.getDriver().findElement(tipoTasa).getAttribute("value").toLowerCase();
	}

	/**
	 * Va al input del tipo de persona y regresa el valor contenido
	 *
	 * @return String del tipo de persona que tiene el campo
	 */
	public String getTipoPersona() {
		u.highLight(tipoPersona);
		return u.getDriver().findElement(tipoPersona).getAttribute("value").toLowerCase();
	}

}
