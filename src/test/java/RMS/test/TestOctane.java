package RMS.test;

import java.util.HashMap;

import org.testng.annotations.Test;

import config.Config;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class TestOctane extends BaseExecution {

	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void TestOctane2(HashMap<String, String> data) throws Exception {
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		addStep("prueba inicial");
		addStep("prueba1");
		System.out.println("password: " + Config.configurationFile.octane_password());
		// CM.logOn();
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "TestOctane2";
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
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Prerequisitos";
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

}
