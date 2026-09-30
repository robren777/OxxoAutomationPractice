package RDM.util.bIPublisher;

import org.openqa.selenium.By;

import RDM.util.GlobalVariables;
import utils.selenium.SeleniumUtil;

public class BiPublisherInicio {
	//Login to BIPUBLISHER as a main user
	/**
	 * Hace login en la pantalla de Bi publisher
	 *
	 * @param u Driver
	 * 
	 * @author Franciscot2@hexaware.com
	 */
	public void loginBiPublisher(SeleniumUtil u) {

		try{
		u.getDriver().findElement(By.id("id")).sendKeys(GlobalVariables.USER_BI_PUBLISHER);
		u.getDriver().findElement(By.id("passwd")).sendKeys(GlobalVariables.PASSWORD_BI_PUBLISHER);
		u.hardWait(10);
		u.getDriver().findElement(By.name("SUBMIT_BUTTON")).click();
		}catch(Exception e){
			throw new AssertionError("No se pudo iniciar sesion en BI PUBLISHER");
		}
	}
}
