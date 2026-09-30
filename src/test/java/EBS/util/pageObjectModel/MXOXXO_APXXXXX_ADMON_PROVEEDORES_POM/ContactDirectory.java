package EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM;

import java.util.Locale;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class ContactDirectory {

	SeleniumUtil u;
	TestCase testCase;

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author JessicaR@hexaware.com
	 */
	public ContactDirectory(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	By btnCrear = By.xpath("//button[@id='CreateBtn']");
	By btnAplicar = By.xpath("//button[@id= 'applyBtn']");
	By primerNombre = By.xpath("//input[@id= 'FirstName']");
	By apellido = By.xpath("//input[@id= 'LastName']");
	By cargo = By.xpath("//input[@id= 'JobTitle']");
	By correoElectronico = By.xpath("//input[@id= 'Email']");

	
	/**
	 * Método que da click y hace un remarcado en el botón de Crear y espera a que
	 * la página termine de cargar
	 * 
	 * @author JessicaR@hexaware.com
	 */
	public void clickCrear() {
		u.highLight(btnCrear);
		u.click(btnCrear);
		u.waitForLoadPage();
	}

	public void clickAplicar() {
		u.highLight(btnAplicar);
		u.click(btnAplicar);
		u.waitForLoadPage();
	}
	
	public By campo(String Campo) {
		switch (Campo.toLowerCase(Locale.ROOT)) {
		case "primer nombre":
			return primerNombre;
		case "apellido":
			return apellido;
		case "cargo":
			return cargo;
		case "correo electronico":
			return correoElectronico;
		}

		return null;
	}
	
	
	public void sendInformacion(String campo, String informacion) {
        By obj = campo(campo);
        u.highLight(obj);
        u.getDriver().findElement(obj).sendKeys(informacion);
    }

}