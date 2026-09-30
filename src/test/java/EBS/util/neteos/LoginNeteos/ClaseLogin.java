package EBS.util.neteos.LoginNeteos;


import EBS.util.neteos.ModelosLoginNeteos.ModeloLoginEBS;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Clase que utiliza los metodos creados en EBSLogin para iniciar sesion
 *
 * @author JesusH@hexaware.com
 * @version 1.0
 * @see EBSLogin
 * @since 5/23/2022
 */
public class ClaseLogin {

	SeleniumUtil u;
	TestCase testCase;
	ModeloLoginEBS inicio;
	String url;
	String userName;
	String password;
	/*
	 * Generacion del constructor
	 */

	/**
	 * Herramienta para iniciar sesion
	 *
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @param url      Sitio a visitar
	 * @param userName Nombre de usuario
	 * @param password Contraseña del usuario
	 * @author JesusH@hexaware.com
	 * @since 5/23/2022
	 */
	public ClaseLogin(SeleniumUtil u, TestCase testCase, String url, String userName, String password) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.url = url;
		this.userName = userName;
		this.password = password;
		inicio = new ModeloLoginEBS(u, testCase, url, userName, password);
	}

	/**
	 * Metodo para iniciar sesion en la plataforma de EBS
	 *
	 * @author JesusH@hexaware.com
	 * @since 5/23/2022
	 */

	public void login() {
		inicio.navigate(url);
		inicio.setLanguage();
		inicio.setCredentials();
		testCase.addScreenShotCurrentStep(u, "Login");
		inicio.clickLogIn();

	}

	/**
	 * Metodo para iniciar sesion en la plataforma EBS internacional
	 *
	 * @author JesusH@hexaware.com
	 */
	public void loginInternacional() {
		inicio.navigate(url);
		inicio.setCredentials();
		testCase.addScreenShotCurrentStep(u, "Login");
		inicio.clickLogIn();

	}

	/**
	 * Metodo para iniciar sesion en la plataforma de EBS Colombia
	 *
	 * @author MisraimH@hexaware.com
	 * @since 7/27/2022
	 */
	public void loginColombia() {
		inicio.navigate(url);
		inicio.setLanguageEBSColombia();
		inicio.setCredentials();
		testCase.addScreenShotCurrentStep(u, "Login");
		inicio.clickLogInEBSColombia();

	}
	
	/**
	 * Metodo para iniciar sesion en la plataforma EBS IMMEX
	 *
	 * @author JessicaR@hexaware.com
	 */
	public void loginIMMEX() {
		inicio.navigate(url);
		inicio.setCredentials();
		testCase.addScreenShotCurrentStep(u, "Login");
		inicio.clickLogIn();

	}

}