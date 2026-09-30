package WM.integrationServer.scripts;

import java.util.HashMap;

import org.testng.annotations.Test;

import WM.integrationServer.om.PakageManagment;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class scriptsIS  extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void test(HashMap<String, String> data) throws Exception {
	//utileria 
	SeleniumUtil u = new SeleniumUtil(new ChromeTest(),true);
	PakageManagment pok = new PakageManagment(u, testCase);
	 
	String user = data.get("user");
	String ps = PasswordUtil.decryptPassword( data.get("ps"));
	String server = data.get("server");
	
	// cargar el integration server 
	
	addStep("Se iniciala la carga de integration Server");
	
	String contra =   "http://"+user+":"+ps+"@"+server+":5555";


	u.get(contra);
	
//	pok.clickManagment();
//	
////	pok.selectInterface(data.get(""));
//	pok.selectInterface("FEMSA_PR35");
//	pok.clickBrowserService();
////	pok.selectService(data.get(""));
//	pok.selectService("PR35.Pub:run");
//	pok.clickTestRun();
//	pok.clickSelectNoInput();
	
	pok.runIntefaceWM("FEMSA_PR35","PR35.Pub:run", null);
	
	
	}
	
	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return null;
	}

	@Override
	public String setTestDesigner() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public void beforeTest() {
		
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
}
