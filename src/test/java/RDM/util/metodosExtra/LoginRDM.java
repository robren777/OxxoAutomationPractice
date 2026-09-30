package RDM.util.metodosExtra;


import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class LoginRDM {

	SeleniumUtil u;
	TestCase testCase;
	String url;
	public LoginRDM(SeleniumUtil u, TestCase testCase, String url) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.url = url;
	}

	/**
	 * Método para obtener url de Portal de aplicaciones
	 * @return url Regresa la url de RDM
	 * @author María Dueñas
	 */
	public String getUrl() {
		return url;
	}
	
	
	
	
	
}
