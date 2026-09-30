package WEB.portal_APEX.pom;

import org.openqa.selenium.By;

import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Inicio {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;

	private final By pronosticoCedis = By.xpath("//span[normalize-space()='Pron�stico CEDIS']");
	private final By expansionDeTiendas = By.xpath("//span[normalize-space()='Expansi�n de Tiendas']");
	private final By admTipoSurtido = By.xpath("//span[normalize-space()='Adm Tipo Surtido']");
	private final By salirButton = By.xpath("//span[normalize-space()='Salir']/parent::a");

	public Inicio(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}

	/**
	 * Hace click en Pronostico CEDIS
	 *
	 * @author Enrique Castilleros
	 * @date 30/Octubre/2024
	 */
	public boolean clickProsticoCEDIS() {
		try {
			testCase.addScreenShotCurrentStep("nombre de la imagen");
			u.click(pronosticoCedis);
			return true;
		} catch (Exception e) {
			testCase.addBoldTextEvidenceCurrentStep(
					"Error al intentar hacer clic en Pron�stico CEDIS: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Hace click en Expansion de tiendas
	 *
	 * @author Enrique Castilleros
	 * @date 30/Octubre/2024
	 */
	public void clickExpansionDeTiendas() {
		u.click(expansionDeTiendas);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Hace click en Admin tipo surtido
	 *
	 * @author Enrique Castilleros
	 * @date 30/Octubre/2024
	 */
	public void clickAdmTipoSurtido() {
		u.click(admTipoSurtido);
		testCase.addScreenShotCurrentStep("nombre de la imagen");
	}

	/**
	 * Hace click en el bot�n Salir para finalizar la sesi�n
	 *
	 * @author Edwin Chi
	 * @date 08/Noviembre/2024
	 */
	public void cerrarSesion(){
		u.click(salirButton);
	}

}
