package WorkFlows.SipreOXXO;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.ultimus.pomUltimus;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_SIPRE_002_Acceso_Ultimus_Category extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_SIPRE_002_Acceso_Ultimus_Category_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Workflows Library: Sipre Oxxo");

		/**
		 * Variables
		 * 
		 */

		/**
		 * 
		 * Utilerias
		 */
		u = new SeleniumUtil(new FirefoxTest(), true);
		// String windowUltimus = u.getDriver().getWindowHandle();
		// pomSipreOXXO util = new pomSipreOXXO(u,testCase);
		pomUltimus ultimus = new pomUltimus(u);

		// paso 1
		addStep("Ingresar a Ultimus ");
		ultimus.loginUltimus(GlobalVariables.userSipreOXXOUltimusCategory,
				GlobalVariables.passSipreOXXOUltimusCategory);
		testCase.addScreenShotCurrentStep("Login");

		// paso 5
		addStep("Seleccionar la opción 'Iniciar' del menú de Vistas");
		ultimus.seleccionarVista("Iniciar");

		// paso 6
		addStep("Ingresar a SIPRE");
		ultimus.openSipreOXXO(GlobalVariables.userPortalSipreOXXO, GlobalVariables.passPortalSipreOXXO);
		testCase.addScreenShotCurrentStep("SIPRE");

	}

	@AfterMethod
	public void afterMethod() {
		u.getDriver().quit();
	}

	@Override
	public void beforeTest() {
		// TODO Auto-generated method stub

	}

	@Override
	public String setPrerequisites() {
		// TODO Auto-generated method stub
		return "- Tener Acceso Dominio Oxxo" + "\n- Contar con Orgchart de sipre"
				+ "\n- Contar con usuarios configurados al orgchart"
				+ "\n- Contar con JFG de perfil adecuado en Ultimus o Pega" + "\n- Contar con Acceso a Control -M"
				+ "\n- Solicitar el Holdeo de los JOBS a ejecutar" + "\n- Contar con Acceso al esquema de XXWF_SIPRE2"
				+ "\n- CONEXION FCIAS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se pueda ingresar a la aplicación de SIPRE OXXO para el perfil Category";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_SIPRE_002_Acceso_Ultimus_Category_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
