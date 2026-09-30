package RMS.BimodalRMS.om;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class PortalRMS {
	
	SeleniumUtil u;
	TestCase testCase;
	
	By inputUsuarioRMS = By.id("pt1:r1:0:login_it1::content");
	By inputContrasenaRMS = By.id("pt1:r1:0:login_it2::content");
	By btnConexion = By.xpath("//span[text()='Conexión']");
	By lblMerchan = By.xpath("//h1[text()='Merchandising']");

	public PortalRMS(SeleniumUtil u, TestCase testCase) {
		this.u = u;
		this.testCase = testCase;
	}
	
	
	public Boolean elementExists(By by, long timeout) {
    	Duration dur = Duration.ofSeconds(timeout);
    	try {
			WebDriverWait wait = new WebDriverWait(u.getDriver(),dur);
			//wait.ignoring(Exception.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			return false;
		}
	return true;
    }
	
	
	public void goToRMS(String url) {
	
		u.get(url);
		u.waitForLoadPage();
		testCase.addScreenShotCurrentStep("test");
	
	}
	
	public void typeUser(String user) {
		if(this.elementExists(inputUsuarioRMS, 10)) {
			u.sendKeys(inputUsuarioRMS, user);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("test");
		}
	}
	
	public void typePass(String pass) {
		if(this.elementExists(inputContrasenaRMS, 10)) {
			u.sendKeys(inputContrasenaRMS, pass);
			u.hardWait(1);
			testCase.addScreenShotCurrentStep("test");
		}
	}
	
	public void clickbtnConexion() {
		if(this.elementExists(btnConexion, 10)) {
			u.click(btnConexion);
			u.hardWait(15);
			testCase.addScreenShotCurrentStep("test");
		}
	}
	
	public Boolean validateOpenPortal() {
		
		if(this.elementExists(lblMerchan, 25)) {
			testCase.addTextEvidenceCurrentStep("Correcto, palabra Merchandising encontrada, entró al Portal exitosamente");
			testCase.addScreenShotCurrentStep("test");
			return true;
		}else {
			return false;
		}
		
	}

}
