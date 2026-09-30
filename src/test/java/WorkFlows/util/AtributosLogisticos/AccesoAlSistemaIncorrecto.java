package WorkFlows.util.AtributosLogisticos;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import modelo.BaseExecution;
import utils.password.PasswordUtil;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

public class AccesoAlSistemaIncorrecto extends BaseExecution{
	SeleniumUtil u;
	@Test(dataProvider = "data-provider")
	
	public void ATC_FT_007_AccessoSistemaIncorrecto(HashMap<String, String> data) throws Exception {
		/*
		 * Variables
		 ******************************************************************************************/
		String user = data.get("User");
		String password = PasswordUtil.decryptPassword(data.get("Password"));
		String mngAlerta = "Usuario y/o Contrase�a Invalidos";
		
		
		//									PASO 1
		
		addStep("Ingresar al portal con el usuario y contrase�a incorrecta");
		u = new SeleniumUtil(new ChromeTest(), true);
		PortalApps pa = new PortalApps(u, testCase, user, password);
		u.hardWait(3);
		
		pa.proceedToPortal();
		
		
		//		                            PASO 2
		
		addStep("Validar alerta de 'Usuario y/o contrase�a invalidos' y no permite el ingreso al usuario");
		String alerta = "";
		boolean validacion;
		try {
			alerta = u.getText(pa.getxmlUserPassInv());
			System.out.print("-" + alerta + "-");
			validacion = true;
    	}catch (Exception e) {
    		validacion = false;
    	}
		
		
		if (alerta.equals(mngAlerta)) {
			testCase.addTextEvidenceCurrentStep("Muestra la alerta '" + alerta + "' y no permite acceso al usuario");			
			System.out.println("Muestra la alerta y no permite acceso");
		}else {
			testCase.addTextEvidenceCurrentStep("No muestra la alerta '" + mngAlerta + "'");
		}
		
		assertTrue(validacion, "No Muestra la alerta");
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setTestDescription() {
		return "Construido.  Validar que se muestre  la alerta de \"Usuario y/o contrase�a invalidos\" y no permite el ingreso al usuario";
	}

	@Override
	public String setTestDesigner() {
		return "AutomationQA";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_007_AccessoSistemaIncorrecto";
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
		u.getDriver().quit();
	}
}
