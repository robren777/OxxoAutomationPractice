package WorkFlows.Panp3;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.Panp3.PomPanp;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class MTC_FT_001_AccesoDeUsuariosAPortalPEGA extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_AccesoDeUsuariosAPortalPEGA(HashMap<String, String> data) {
		/*
		 * variables
		 */
		//String userUltimus = GlobalVariables.ULTIMUS_PANP3_USER_MERCANCIAS;
		String userPega = GlobalVariables.PEGA_USER;
		
		SeleniumUtil u = new SeleniumUtil(new FirefoxTest(), true);
		//u.get(data.get("urlUltimus"));
		u.get(data.get("urlPega"));
		
		PomPanp pom = new PomPanp(u, userPega, testCase);
		
		try {
			///////////////////////////////////////////////////////////
			pom.accessPega(this);
			//u.close();
		}catch(Exception ex) {;
			testCase.addScreenShotCurrentStep(u, ex.getMessage());
			assertTrue(false, ex.getMessage());
		}
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
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Construido. Validar el log in correcto desde la plataforma PEGA al aplicativo.";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Jose Antonio Zarate";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_001_AccesoDeUsuariosAPortalPEGA";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}