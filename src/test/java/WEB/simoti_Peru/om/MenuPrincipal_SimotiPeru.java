package WEB.simoti_Peru.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MenuPrincipal_SimotiPeru {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	
	/*
	 * Localizadores Menu Integridad de datos
	 ********************************************************************************************/
	
	By xmlIntegridadDatos = By.xpath("//a[contains(text(), 'Integridad de Datos')]");
	
	//Opciones menu
	
	By xmlIndicadoresTotales = By.xpath("//a[contains(text(), 'Indicadores por  Totales')]");
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public MenuPrincipal_SimotiPeru(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		this.me = new MetodosExtra(u, testCase);
	}
	
	/*
	 * Metodos Menu Integridad de datos
	 ********************************************************************************************/
	
	/**
	 * Metodo para dar click en Integridad de Datos
	 * @author Herbe Cauich
	 */
	public void clickIntegridadDatos() {
		u.highLight(xmlIntegridadDatos);
		testCase.addScreenShotCurrentStep(u, "IntegridadDatos");
		u.unhighLight(xmlIntegridadDatos);
		u.mouseOver(xmlIntegridadDatos);
		testCase.addScreenShotCurrentStep(u, "clickIntegridadDatos");
	}
	
	/**
	 * Metodo para dar click en Integridad de Datos
	 * @author Herbe Cauich
	 */
	public void clickIndicadoresTotales() {
		u.highLight(xmlIndicadoresTotales);
		testCase.addScreenShotCurrentStep(u, "IndicadoresTotales");
		u.unhighLight(xmlIndicadoresTotales);
		u.click(xmlIndicadoresTotales);
		testCase.addScreenShotCurrentStep(u, "clickIndicadoresTotales");
	}
	
	
}
