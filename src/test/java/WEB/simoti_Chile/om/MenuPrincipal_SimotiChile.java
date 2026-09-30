package WEB.simoti_Chile.om;

import org.openqa.selenium.By;
import WEB.om.MetodosExtra;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MenuPrincipal_SimotiChile {

	SeleniumUtil u;
	TestCase testCase;
	MetodosExtra me;
	
	
	/*
	 * Localizadores Menu Integridad de datos
	 ********************************************************************************************/
	By xmlLoadingMsj= By.xpath("//div[@id='j_idt12'][@aria-hidden='false']");
	
	By xmlIntegridadDatos = By.xpath("//a[contains(text(), 'Integridad de Datos')]");
	
	//Opciones menu
	
	By xmlIndicadoresTotales = By.xpath("//a[contains(text(), 'Indicadores por  Totales')]");
	
	/*
	 * Constructor
	 ********************************************************************************************/
	public MenuPrincipal_SimotiChile(SeleniumUtil u, TestCase testCase) {
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
	 * @author Maria Due�as
	 */
	public void clickIntegridadDatos() {
		u.highLight(xmlIntegridadDatos);
		testCase.addScreenShotCurrentStep(u, "IntegridadDatos");
		u.unhighLight(xmlIntegridadDatos);
		u.mouseOver(xmlIntegridadDatos);
		testCase.addScreenShotCurrentStep(u, "clickIntegridadDatos");
	}
	
	/**
	 * Metodo para dar click en Indicadoires Totales
	 * @author Maria Due�as
	 */
	public void clickIndicadoresTotales() {
		u.highLight(xmlIndicadoresTotales);
		testCase.addScreenShotCurrentStep(u, "IndicadoresTotales");
		u.unhighLight(xmlIndicadoresTotales);
		u.click(xmlIndicadoresTotales);
		testCase.addScreenShotCurrentStep(u, "clickIndicadoresTotales");
	}
	
	
	/**
	 * Metodo para esperar a que ya no se muestre el componente de carga
	 * @author Andrea Contreras
	 * @param N/A
	 * @return N/A
	 */
	public void waitLoading() {
		boolean existe = false;
		
		do {
			existe = me.existElement(xmlLoadingMsj);	
		}while(existe);
				
	}
	/**
	 * Metodo para dar click en 
	 * @author Maria Due�as
	 
	public void click() {
		u.highLight(xml);
		testCase.addScreenShotCurrentStep(u, "");
		u.unhighLight();
		u.mouseOverAndClick();
		testCase.addScreenShotCurrentStep(u, "click");
	}*/
}
