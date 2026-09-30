package EBS.activoFijo.folioDeEntrada;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.sikuli.script.Pattern;
import org.testng.annotations.Test;

import EBS.util.GlobalVariables;
import EBS.util.EBSDeskApp.pomSimetrik;
import EBS.util.FolioPagoMVP2.EBSChrome;
import EBS.util.ServiceNow.ServiceNow;
import EBS.util.appletsMethods.MetodosSikuli;
import EBS.util.pageObjectModel.EBSLogin;
import EBS.util.pageObjectModel.EBSPaginaInicial;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class test extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void test_test(HashMap<String, String> data) throws Exception {

		addStep("sd");
		
		String queryEBSCargaMasiva = data.get("queryEBSCargaMasiva");
		
		String hostActFijo = GlobalVariables.SFTP_ACT_FIJO_HOST;
		int portActFijo = GlobalVariables.SFTP_ACT_FIJO_PORT;
		String userActFijo = GlobalVariables.SFTP_ACT_FIJO_USER;
		String pwdActFijo = GlobalVariables.SFTP_ACT_FIJO_PWD;
		
		System.out.println("host:" + hostActFijo);
		System.out.println("port:" + portActFijo);
		System.out.println("user:" + userActFijo);
		System.out.println("pwd:" + pwdActFijo);
		
		System.out.println("Prueba auth en github v4");
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "JesusM3@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "test_test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Construido.";
	}

}