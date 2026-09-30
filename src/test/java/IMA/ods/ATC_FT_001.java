package IMA.ods;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import IMA.util.IMATools;
import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001 extends BaseExecution {
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_001_test(HashMap<String, String> data) throws Exception {
		
		//VARIABLES
		
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		
		//localizadores
		By xmlUser = By.xpath("//*[@id=\"Uid\"]");
		By xmlPassword = By.xpath("//*[@id=\"Pwd\"]");
		By xmlLogginButton = By.xpath("//*[@id=\"3054\"]");
		By xmlFinanzasQABtn = By.xpath("//*[@id=\"projects_ProjectsStyle\"]/table/tbody/tr[3]/td[1]/div/table/tbody/tr/td[2]/a");
		By xmlAdoptaUnaTinedaBtn = By.xpath("//*[@id=\"projects_ProjectsStyle\"]/table/tbody/tr[1]/td[1]/div/table/tbody/tr/td[2]/a");
		By xmlResultados = By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr/td[1]/table/tbody/tr/td[2]/span/a");
		By xmlExportarResultados = By.xpath("//*[@id=\"FolderIcons\"]/tbody/tr/td/div/table/tbody/tr/td[2]/div[6]/span[3]/a");
		
		
		// paso 1 - navigate
		addStep("Abrir navegador");
		SeleniumUtil u = new SeleniumUtil(new ChromeTest(), true);
		IMATools ima = new IMATools(u,testCase);
		//u.get("http://Hexaware30:Comercio.21@10.185.176.16/MicroStrategy/asp/Main.aspx");
		ima.Login();
		
		
		//paso 2 - credenciales
		u.highLight(xmlUser);
		u.sendKeys(xmlUser,user);
		u.unhighLight(xmlUser);
		testCase.addScreenShotCurrentStep(u, "inputs");
		
		u.highLight(xmlPassword);
		u.sendKeys(xmlPassword,password);
		System.out.println("pass desencriptada: "+password);
		u.unhighLight(xmlPassword);
		testCase.addScreenShotCurrentStep(u, "inputs");
		
		u.highLight(xmlLogginButton);
		testCase.addScreenShotCurrentStep(u, "inputs");
		u.click(xmlLogginButton);
	//	u.waitForLoadPage();
		
		
		//paso 3 - elegir carpeta
		u.highLight(xmlAdoptaUnaTinedaBtn);
		u.click(xmlAdoptaUnaTinedaBtn);
		testCase.addScreenShotCurrentStep(u, "inputs");
		u.waitForLoadPage();
		u.highLight(xmlResultados);
		u.click(xmlResultados);
		
		//paso 4 - exportar resultados
		u.highLight(xmlExportarResultados);
		u.click(xmlExportarResultados);
		
		
	}
	
	@Override
	public void beforeTest() {
	}
	
	@Override
	public String setTestFullName() {
		return "ATC_FT_001_test";
	}

	@Override
	public String setTestDescription() {
		return "Login";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "EQUIPO AUTOMATIZACION IMA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void afterTest() {
		// TODO Auto-generated method stub
	}
	
	
	public void baseAfterClass() {
		// TODO Auto-generated method stub
	}
	
	
	
}
