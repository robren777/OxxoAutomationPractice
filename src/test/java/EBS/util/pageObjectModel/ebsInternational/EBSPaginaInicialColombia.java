package EBS.util.pageObjectModel.ebsInternational;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Clase con los locators y acciones de la pagina de "EBS inicio Colombia"
 * 
 * @author MisraimH@hexaware.com
 * @since 7/27/2022
 */
public class EBSPaginaInicialColombia {

	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	// Generacion del constructor

	/**
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 * @author JesusH@hexaware.com
	 */
	public EBSPaginaInicialColombia(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		PageFactory.initElements(u.getDriver(), this);

	}

	/*
	 * Responsabilidades
	 */
	By COOXXOGLXXXXXSuperUsuario = By.xpath("//*[@id='AppsNavLink']");

	/*
	 * Carpetas
	 */
	By AsientosImportRun = By.xpath("//*[@id='N89']");
	/*
	 * Formularios
	 */

	/*
	 * EBS Internacional-Colombia Header
	 */

	/**
	 * Metodo utilizado para checar cual es la responsabilidad que se va a probar
	 *
	 * @param Responsabilidad Texto que nos indica que responsabilidad es a la que
	 *                        se desea acceder para realizar las pruebas
	 * @return Un WebElement dependiendo del texto que recibe
	 * @author JesusH@hexaware.com
	 */
	public By responsabilidad(String Responsabilidad) {
		switch (Responsabilidad) {

		case "COOXXO-GLXXXXX-Super Usuario":
			return COOXXOGLXXXXXSuperUsuario;

		}
		return null;
	}

	/**
	 * Realiza un click en la responsabilidad especificada
	 *
	 * @param responsabilidad Nombre de la responsabilidad a probar
	 * @author JesusH@hexaware.com
	 */
	public void clicEnResponsabilidad(String responsabilidad) {
		u.waitForLoadPage();
		try {
			By obj = responsabilidad(responsabilidad);
			u.highLight(obj);
			u.click(obj);
			u.screenShotFull("click en la responsabilidad: " + responsabilidad);
			System.out.println("click en la responsabilidad: " + responsabilidad);
		} catch (Exception e) {
			logger.error("Error Responsabilidad no encontrada " + e);
			System.out.println("Error Responsabilidad no encontrada " + e);
		}

	}

	/**
	 * Realiza un click en el icono 'Run' contenido en 'Asientos Import'
	 * 
	 * @author MisraimH@hexaware.com
	 */
	public void clicAsientosImportRun() {
		u.waitForLoadPage();
		try {
			By obj = AsientosImportRun; // AsientosImportRun Contiene el selector para el icono 'Run' contenido en
										// 'Asientos Import'
			u.highLight(obj);
			u.click(obj);
			u.screenShotFull("click en el icono 'Run' contenido en 'Asientos - Import'");
			System.out.println("click en el icono 'Run' contenido en 'Asientos - Import'");
		} catch (Exception e) {
			logger.error("Error AsientosImportRun no encontrada " + e);
			System.out.println("Error AsientosImportRun no encontrada " + e);
		}

	}

}