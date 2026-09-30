package WEB.cartaPorte.objectModelPortalCP;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class MenuPortal {
	
	SeleniumUtil u;
	TestCase testCase;
	private static final Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);

	 /* Localizadores menú Portal
	 * 
	 * */
	
	By cartaPorteMenu= By.xpath("/html/body/div/div/form/div/div/div/h1[1]");
	By opcionCartaPorte= By.xpath("//a[text()='Portal Carta Porte']");
	
	public MenuPortal(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
	}
	

	/**
	 * Metodo para obtener el texto desplegado en la Pantalla de Inicio
	 * 
	 * @param user-- usuario de acceso
	 * @param pass-- password de acceso
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public String accesoMenuPortal(String user, String pass) {
		try {
			 return u.getDriver().findElement(cartaPorteMenu).getText();
		} catch (Exception e) {
			logger.error("Error" + e);
			System.out.println("Error" + e);
			return "";
		}
	}
	
	/**
	 * Metodo para seleccionar la opcion de carta porte, posterior al inicio de sesion
	 * 
	 * 
	 * @author nellyoliviab@hexaware.com
	 */
	public void seleccionarOpcionCartaPorte() {
		WebDriverWait wait= new WebDriverWait(u.getDriver(), Duration.ofSeconds(300));
		wait.until(ExpectedConditions.visibilityOfElementLocated(opcionCartaPorte));
		//esperarAqueSeMuestreElemento(opcionCartaPorte);
		u.highLight(opcionCartaPorte);
		testCase.addScreenShotCurrentStep("SeleccionMenu");
		u.getDriver().findElement(opcionCartaPorte).click();
	}
	
	/**
	 * Espera hasta que un elemento específico se muestre en la página.
	 * Este método entra en un bucle continuo hasta que el elemento web indicado sea visible en la página.
	 * @param elementoWeb Localizador del elemento web que se espera que se muestre.
	 * @author nellyoliviab@hexaware.com
	 * @date 21/Agosto/2024
	 */
	public void esperarAqueSeMuestreElemento(By elementoWeb) {
		boolean continuar=false;
		do {
			if(u.getDriver().findElement(elementoWeb).isDisplayed()) {
				continuar=true;
			}
		}while(!continuar);
	}

}
