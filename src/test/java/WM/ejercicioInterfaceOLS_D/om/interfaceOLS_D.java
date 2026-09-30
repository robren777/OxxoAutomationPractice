package WM.ejercicioInterfaceOLS_D.om;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class interfaceOLS_D {

	SeleniumUtil u;
	TestCase testCase;


	By code = By.xpath("//TPEDoc//response//wmCode");
	By folio = By.xpath("//TPEDoc//header");
	String codeObtenido ="";
	String folioObtenido ="";
	
	String adDate = "";
	String pvDate = "";
	

	/**
	 * Constructor
	 * 
	 ********************************************************************************************/
	public interfaceOLS_D(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}

	/**
	 * Metodo obtener el codigo del request
	 * @author Andrea Cano
	 */
	public String obtenerCode() {

		try {
			u.waitWebElement(code, 35, 1);
			WebElement element = u.getDriver().findElement(code);
			codeObtenido = element.getAttribute("value");
			System.out.println("code obtenido: " + codeObtenido);
			return codeObtenido;
		} catch (Exception e) {
			System.out.println(" -> " + e);
            throw new AssertionError("Fallo al obtener el codigo");
		}
	}
	
	/**
	 * Metodo obtener folio del request
	 * @author Andrea Cano
	 */
	public String obtenerFolio() {
		try {
		u.waitWebElement(folio, 35, 1);
		WebElement element = u.getDriver().findElement(folio);		
		folioObtenido =  element.getAttribute("folio");
		System.out.println("Folio obtenido: " + folioObtenido);
		return folioObtenido;
		} catch (Exception e) {
			System.out.println(" -> " + e);
            throw new AssertionError("Fallo al obtener el folio");
		}
	}
	
	
}
