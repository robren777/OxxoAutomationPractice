package WorkFlows.SipreOXXO;

import java.util.HashMap;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import WorkFlows.util.GlobalVariables;
import WorkFlows.util.SipreOXXO.pomSipreOXXO;
import modelo.BaseExecution;
import utils.selenium.FirefoxTest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_SIPRE_006_Acceso_PEGA_Admin extends BaseExecution {
	SeleniumUtil u;

	@Test(dataProvider = "data-provider")
	public void ATC_FT_SIPRE_006_Acceso_PEGA_Admin_Test(HashMap<String, String> data) throws Exception {
		testCase.setProject_Name("Framework WorkFlows --> SipreOXXO");

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
		pomSipreOXXO util = new pomSipreOXXO(u, testCase);
		// pomUltimus ultimus = new pomUltimus (u);

		// paso 1
		addStep("Abrir navegador");

		// paso 2
		addStep("Ingresar a la liga http://10.185.176.11:7011/prweb/PRWebLDAP1");

		// paso 3
		addStep("Ingresar las credenciales de Usuario Plaza" + "Usuario:" + "Contraseña:");

		// paso 4
		addStep("Dar click al botón Iniciar Sesión");
		util.loginPega(GlobalVariables.userSipreOXXOAdminPEGA, GlobalVariables.passSipreOXXOUAdminPEGA);
		testCase.addScreenShotCurrentStep("Login");

		// paso 5
		addStep("Seleccionar la opción y dar click Mostrar Aplicaciones WEB");

		// paso 6
		addStep("Seleccionar la opción Sistema De Precios");
		util.selectMenuSistemaPrecios();
		testCase.addScreenShotCurrentStep("Sistema De Precios");

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
		return "Tener Acceso Dominio Oxxo" + "Contar con Orgchart de sipre"
				+ "Contar con usuarios configurados al orgchart" + "Contar con JFG de perfil adecuado en Ultimus o Pega"
				+ "Contar con Acceso a Control -M" + "Solicitar el Holdeo de los JOBS a ejecutar"
				+ "Contar con Acceso al esquema de XXWF_SIPRE2" + "CONEXION FCIAS";
	}

	@Override
	public String setTestDescription() {
		// TODO Auto-generated method stub
		return "Validar que se pueda ingresar a la aplicación de SIPRE OXXO para el perfil Administrador";
	}

	@Override
	public String setTestDesigner() {
		// TODO Auto-generated method stub
		return "Misraim Hernandez Monsivais";
	}

	@Override
	public String setTestFullName() {
		// TODO Auto-generated method stub
		return "ATC_FT_SIPRE_006_Acceso_PEGA_Admin_Test";
	}

	@Override
	public String setTestInstanceID() {
		// TODO Auto-generated method stub
		return null;
	}

}
