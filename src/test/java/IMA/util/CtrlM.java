package IMA.util;

import org.openqa.selenium.By;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CtrlM {
	private SeleniumUtil u;
	TestCase testCase;
	
	public CtrlM(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
	}
	
	public void Login() {
		u.get("http://10.184.56.194:18080/ControlM/");
		u.highLight(By.id("login-user-name"));
		u.sendKeys(By.id("login-user-name"), "cdvalros");
		u.highLight(By.id("login-user-password"));
		u.sendKeys(By.id("login-user-password"),  "Xe4qP8$d");
		//testCase.addScreenShotCurrentStep(u, "Pagina de identificaci�n de CTRL-M");
		u.highLight(By.id("login-submit-button"));
		u.click(By.id("login-submit-button"));
		
		checkPassExpire();
	}
	
	public void checkPassExpire() {
		try {
			u.highLight(By.xpath("//*[.='Your password is about to expire.']"));
			u.highLight(By.className("d-button_primary"));
			//testCase.addScreenShotCurrentStep(u, "La contrase�a esta por expirar, se da click en Ok");
			u.click(By.className("d-button_primary"));
		}catch(Exception ex) {
			
		}
	}
	
	public void SelectFoldersandJobs() {
		u.highLight(By.id("wcmLoadJobs"));
		//testCase.addScreenShotCurrentStep(u, "Abrimos la carpeta de folders and jobs");
		u.click(By.id("wcmLoadJobs"));
		
		// No se puede seguir el flujo, el texto se muestra como dibujos canvas y no son reconocidos por selenium. busca alternativas, maybe use a js injection
	}
	
	
	
}
