package WM.scriptsApoyo.om;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class TPS {

	SeleniumUtil u;
	TestCase testCase;
	int numeroEvidencia = 0;
	
	/****************** LOCATORS ***************************/
	By wmCode = By.xpath("//TPEDoc//response//wmCode");
	By folio = By.xpath("//TPEDoc//header");

	public TPS(SeleniumUtil u, TestCase testCase) {
		super();
		
		this.u = u;
		this.testCase = testCase;
	}

	/*
	 * Abre los enlaces en el navegador seleccionado.
	 * 
	 * @param link Enlace a abrir
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public void openLink(String link) {
		try {
			u.get(link);
			screenshot();
		}catch(Exception e) {
			screenshot();
		}
	}
	
	/*
	 * Obtiene la fecha actual en un formato yyyMMdd y devuelve un string con dicho valor
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public String getDate() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		System.out.println("Actual Date: " + currentDateTime.format(formatter));
		return currentDateTime.format(formatter);
	}
	
	/*
	 * Obtiene la fecha y la hora actual con un formato yyyMMddHHmmss y devuelve un string con dicho valor
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public String getFullDate() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		
		System.out.println("Actual Full Date: " + currentDateTime.format(formatter));
		return currentDateTime.format(formatter);
	}
	
	/*
	 * Toma un screenshot de lo que se este mostrando en el momento en la pantalla del navegador
	 * Nombra a la captura de pantalla con un numero haciendo que no se repita el nombre en las imagenes guardadas
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public void screenshot() {
		String filename = "evidence" + numeroEvidencia;
		u.hardWait(1);
		testCase.addScreenShotCurrentStep(filename);
		u.hardWait(1);
		numeroEvidencia++;
	}
	
	/*
	 * Obtiene el valor del campo WM CODE en el XML y lo devuelve como un String
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public String getWMCode() {
		u.waitWebElement(wmCode, 50, 1);
		WebElement element = u.getDriver().findElement(wmCode);
		
		System.out.println("WM CODE: " + element.getAttribute("value"));
		return element.getAttribute("value");
	}
	
	/*
	 * Obtiene el valor del campo Folio en el XML y lo devuelve como un String
	 * 
	 * @author 1000068915 - Jorge Hernandez
	 */
	public String getFolio() {
		u.waitWebElement(folio, 50, 1);
		WebElement element = u.getDriver().findElement(folio);
		
		System.out.println("Folio: " + element.getAttribute("folio"));
		return element.getAttribute("folio");
	}
	


}
