package WEB.I123057_PagoQRspin_MVP3.scripts;

import java.util.HashMap;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import start.Start;
import utils.password.PasswordUtil;


public class EncriptarPassword extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void EncriptarPassword_test (HashMap <String, String> data) throws Exception {
		
		//String pssDecrypted = PasswordUtil.decryptPassword(data.get("token"));
		//System.out.println(pssDecrypted);
		Start.generateEncryptPassword();
		Thread.sleep(10000);
		
	}
	
	@Override
	public void beforeTest(){
		// TODO Auto-generated method stub

	}
	
	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Gerardo Lopez";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "EncriptarPassword";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Se realiza encriptacion de password necesario";
	}
}
