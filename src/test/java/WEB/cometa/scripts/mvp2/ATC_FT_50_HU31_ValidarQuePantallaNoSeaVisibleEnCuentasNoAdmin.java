package WEB.cometa.scripts.mvp2;


import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WEB.cometa.om.PortalCometa;
import modelo.BaseExecution;
import utils.selenium.ChromeTest;
import utils.selenium.SeleniumUtil;

/**
 * Validar que la pantalla no sea visible si no se tiene cuenta admin 
 * MTC-FT-50-HU31 - Validar que pantalla no sea visible en cuentas no admin
 * @author Jose Zarate - 1000072516
 * @date Octubre 2024
*/
public class ATC_FT_50_HU31_ValidarQuePantallaNoSeaVisibleEnCuentasNoAdmin extends BaseExecution {
	
	SeleniumUtil u;
	PortalCometa pom;
	 
	@Test(dataProvider = "data-provider")
	public void ATC_FT_50_HU31_ValidarQuePantallaNoSeaVisibleEnCuentasNoAdmin_test (HashMap<String, String> data) throws Exception {
		
		testCase.setProject_Name("Portal Web: Cometa");
		
		String user = data.get("user");
		String password = data.get("password");
		u = new SeleniumUtil(new ChromeTest(), true);
		pom = new PortalCometa(u, testCase);
		
		/*
		 *                                             PASO 1
		 ******************************************************************************************/
		addStep("Ingresar a la liga del portal web"); 
		u.get(data.get("Url"));
		pom.PrivatePortal();
		pom.validarAccesoLogin();
		
		/*
		 *                                             PASO 2
		 ******************************************************************************************/
		addStep("Ingresar las credenciales correspondientes para el inicio de sesion");
		pom.IniciarSesion(user, password);
		
		/*
		 *                                             PASO 3
		 ******************************************************************************************/
		addStep("Validar que no se muestre la pantalla de Ticket Pre Inventario");
		pom.validarOpcionCometa();
		u.getDriver().quit();
			
	}
	
	@AfterMethod
	public void afterMethod() {
		 // u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub
		return;
		
	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "Contar con credenciales que no sean admin";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que la pantalla no sea visible si no se tiene cuenta admin";
	}

	@Override
	public String setTestDesigner() {
		return"Jose Zarate";
	}

	@Override
	public String setTestFullName() {
		return "";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}
}
