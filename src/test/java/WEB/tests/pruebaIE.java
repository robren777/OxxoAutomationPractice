package WEB.tests;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.om.PortalApps;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class pruebaIE extends BaseExecution {
	

	@Test(dataProvider = "data-provider")

	public void pruebaIE_test(HashMap<String, String> data) throws Exception {
		SeleniumUtil u;
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		
		
		/**
		 *                                             PASO 1
		 ******************************************************************************************/
		
		addStep("Ingresar al portal");
		
		u = new SeleniumUtil(new IETest(),true);
		PortalApps pa = new PortalApps(u, testCase);	

		u.get(pa.getUrl());
		
		u.waitForLoadPage();
		u.hardWait(5);
		pa.proceedToPortalIE();
		
		//pa.cicloLogin(user, password);
		
	}
	
	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ConsultaUsuarios_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@Override
	public String setPrerequisites() {
		return null;
	}
	
	@AfterMethod
	public void afterMethod() {
//		u.getDriver().quit();
	}

}
