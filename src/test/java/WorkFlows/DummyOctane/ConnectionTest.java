package WorkFlows.DummyOctane;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ConnectionTest extends BaseExecution {
	private SeleniumUtil u;
	
	@Test(dataProvider = "data-provider")
	public void ATC_TestConnection(HashMap<String, String> data) throws Exception {
		u = new SeleniumUtil(new FirefoxTest(), true);
		u.hardWait(2);
		u.getDriver().manage().deleteAllCookies();
		u.get("https://www.google.com/");
		addStep("Paso 1 prueba");
		addStep("Paso 2 prueba");
		testCase.addBoldTextEvidenceCurrentStep("EvidenciaCrrentStep");
		assertFalse(false);
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		
	}
	
	@AfterTest
	public void afterTest() {
		u.getDriver().quit();
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Prerequisitos";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Descripcion del caso de prueba";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Nombre del automatizador";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_TestConnection";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
