package EBS.util.pageObjectModel.ebsIMMEX;

import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Clase con los locators y acciones de la pagina de "EBS inicio"
 *
 * @author JessicaR@hexaware.com
 * @version 1.0
 * @since 12/30/2022
 */

public class EBSPaginaInicialIMMEX {
	
	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	// Generacion del constructor

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author JessicaR@hexaware.com
	 */
	public EBSPaginaInicialIMMEX(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);
	}
	
	/**
	 * Responsabilidades 
	 */
	
	/**
	 * Carpetas
	 */
	
	/**
	 * Formularios
	 */
	
	/**
	 * Applets
	 */
	
	/**
	 * Elementos generales
	 */
	

}
