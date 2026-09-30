package WEB.portal_APEX.pom;

import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class LoginApex {
	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	// Localizadores de ventana de "Conexi�n no privada"
	By xmlMensaje = By.xpath("//*[@id=\"main-message\"]/h1");
	By xmlAdvanced = By.xpath("//*[@id=\"details-button\"]");
	By xmlProceed = By.xpath("//*[@id=\"proceed-link\"]");

	// Localizadores de inicio de sesion
	By usuario = By.xpath("//*[@id='P9999_USERNAME']");
	By contrasenia = By.xpath("//*[@id='P9999_PASSWORD']");
	By iniciar_sesion = By.xpath("//*[@id='B33256097125031388']/span");

	public LoginApex(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	

	/**
	 * Hace el proceso para continuar de la pagina no segura
	 *
	 * @author Enrique Castilleros
	 * @date 30/Octubre/2024
	 */
	public void proceedToPortal() {
		Boolean bandera = false;
		try {
			u.getDriver().findElement(xmlMensaje);
			bandera = true;
		} catch (Exception e) {
			bandera = false;
		}

		if (bandera) {
			u.click(xmlAdvanced);
			u.click(xmlProceed);
		}

	}


	/**
	 * Inicia sesion en APEX
	 *
	 * @author Enrique Castilleros
	 * @date 30/Octubre/2024
	 */
	public void login_apex(String user, String password) {
		u.sendKeys(usuario, user);
		u.sendKeys(contrasenia, password);
		u.click(iniciar_sesion);
		testCase.addScreenShotCurrentStep("nombre de la imagen");

	}
}
