package EBS.GenericTests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class GenericTools {

	SeleniumUtil u;
	TestCase testCase;
	GenericPOM genericPom;

	public GenericTools(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		genericPom = new GenericPOM(u, testCase);
	}

	public void llenarCampos(String tipo, String nivel, String tipoTasa, String tipoPersona) {
		genericPom.selectTipo(tipo);
		genericPom.selectNivel(nivel);
		genericPom.selectTipoTasa(tipoPersona);
		genericPom.selectTipoPersona(tipoPersona);
		/*
		 * genericPom.tipoElement.sendKeys(tipo);
		 * genericPom.tipoElement.sendKeys(nivel);
		 * genericPom.tipoDeTasaElement.sendKeys(tipoTasa);
		 * genericPom.tipoDePersonaElement.sendKeys(tipoPersona);
		 */
	}

	public void realizarBusqueda() {
		genericPom.btnIrElement.click();
	}

	public boolean genericClick(WebDriver driver, WebElement elementToBeClicked) {
		try {
			elementToBeClicked.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void crearPlantilla() {
		genericPom.btnCrearElement.click();
	}

}
